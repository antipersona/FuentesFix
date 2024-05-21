package es.ucm.fdi.iw.controller;

import javax.servlet.http.HttpSession;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import es.ucm.fdi.iw.model.bd.Fuente;
import es.ucm.fdi.iw.model.bd.Reporte;
import es.ucm.fdi.iw.model.bd.Valoracion;
import es.ucm.fdi.iw.model.User;
import es.ucm.fdi.iw.model.User.Role;

/**
 *  Site administration.
 *
 *  Access to this end-point is authenticated - see SecurityConfig
 */
@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
	private PasswordEncoder passwordEncoder;

    @ModelAttribute
    private void isLogged(Model model, HttpSession session) {
        User u = (User) session.getAttribute("u");
        if (u != null) {
            // model.addAttribute("user", u);
            model.addAttribute("logged", true);
        } else {
            model.addAttribute("logged", false);
        }
    }

	private static final Logger log = LogManager.getLogger(AdminController.class);

    @Autowired
	private EntityManager entityManager;

	@GetMapping("{id}")
    public String index(@PathVariable long id, Model model, HttpSession session) {
        User target = entityManager.find(User.class, id);
        model.addAttribute("user", target);
        return "admin";
    }

    @GetMapping("addEntity")
    public String index2(Model model, HttpSession session) {
        User target = (User) session.getAttribute("u");
        model.addAttribute("user", target);
        return "addEntity";
    }

    @PostMapping("addEntity")
    @Transactional
    public String addFuncionario(Model model, HttpSession session, String Email, String firstName, String lastName, String username, String password) {

        if (!entityManager.createNamedQuery("User.byUsername", User.class)
                .setParameter("username", username)
                .getResultList().isEmpty()) {
            model.addAttribute("errors", "Nombre de usuario ya existe");
            return "addEntity";
        }
        // crear e insertar un nuevo funcionario con datos del formulario
        User newUser = new User();
        newUser.setUsername(username); 
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        //newUser.setEmail(Email); 
        newUser.setPassword(passwordEncoder.encode(password)); // assuming you're using passwordEncoder for password hashing
        newUser.setEnabled(true); // assuming newly registered users are enabled by default
        newUser.setRoles(Role.FUNCIONARIO + "," + Role.USER); 

        entityManager.persist(newUser); // to add the new user in the database

        User target = (User) session.getAttribute("u");
        model.addAttribute("user", target);
        
        return "addEntity";
    }

    @GetMapping("ultimoReport")
    public String showFuentes(Model model, HttpSession session) {
        
        List<Valoracion> vf = entityManager.createQuery("SELECT v FROM Valoracion v ORDER BY v.id DESC").getResultList();
        List<Reporte> rp = entityManager.createQuery("SELECT r FROM Reporte r ORDER BY r.id DESC").getResultList();        
        User target = (User) session.getAttribute("u");
        model.addAttribute("user", target);
        model.addAttribute("reportes", rp);
        model.addAttribute("valoraciones", vf);

        return "ultimoReport";
    }

    @PutMapping("ultimoReport")
    @Transactional
    public ResponseEntity<Map<String, String>> moderateComment(@RequestBody Map<String, Object> requestBody){

        Long reporteId = ((Number) requestBody.get("reporteId")).longValue();
        String content = (String) requestBody.get("newComment");

        String sql = "UPDATE REPORTE SET COMENTARIO = ? WHERE ID = ?";
        entityManager.createNativeQuery(sql)
                    .setParameter(1, content)
                    .setParameter(2, reporteId)
                    .executeUpdate();

        Reporte report = entityManager.find(Reporte.class, reporteId);
        report.setComentario(content);

        Map<String, String> jsonResponse = new HashMap<>();
        jsonResponse.put("message", "Success");
        
        return ResponseEntity.ok(jsonResponse);
    }

    @GetMapping("gestionarApp")
    public String gestionApp(Model model, HttpSession session) {
        
        List<Fuente> vf = entityManager.createQuery("SELECT v FROM Fuente v ").getResultList();
        List<User> rp = entityManager.createQuery("SELECT r FROM User r ").getResultList();  
        
        vf.sort((a, b) -> b.getReportes().size() - a.getReportes().size());
        vf.sort((x, y) -> y.getReportes().size() - x.getReportes().size());
        User target = (User) session.getAttribute("u");
        model.addAttribute("user", target);
        model.addAttribute("usuarios", rp);
        model.addAttribute("fuentes", vf);

        return "gestionarApp";
    }

    @PostMapping("gestionarApp")
    @Transactional
    public String handleDelete(Model model, HttpSession session, @RequestParam String action, @RequestParam long id) {
        if ("deleteFuente".equalsIgnoreCase(action)) {
            return borrarFuente(model, session, id);
        } else if ("deleteUsuario".equalsIgnoreCase(action)) {
            return borrarUsuario(model, session, id);
        }
        
        // Default redirect if action is not recognized
        return "redirect:/admin/gestionarApp";
    }

    
    @Transactional
    public String borrarFuente(Model model, HttpSession session, @RequestParam long id){

        
        //tried to do it in the database with sql ON DELETE CASCADE but change are not push in the DB
        String deleteValoracionesSql = "DELETE FROM VALORACION WHERE FUENTE_ID = ?";
        entityManager.createNativeQuery(deleteValoracionesSql)
                    .setParameter(1, id)
                    .executeUpdate();

        String deleteReportesSql = "DELETE FROM REPORTE WHERE FUENTE_ID = ?";
        entityManager.createNativeQuery(deleteReportesSql)
                        .setParameter(1, id)
                        .executeUpdate();

        String sql = "Delete Fuente WHERE ID = ?";
        entityManager.createNativeQuery(sql)
                    .setParameter(1, id)
                    .executeUpdate();

        List<Fuente> vf = entityManager.createQuery("SELECT v FROM Fuente v ").getResultList();
        List<User> rp = entityManager.createQuery("SELECT r FROM User r ").getResultList();  
        
        vf.sort((a, b) -> b.getReportes().size() - a.getReportes().size());
        vf.sort((x, y) -> y.getReportes().size() - x.getReportes().size());

        User target = (User) session.getAttribute("u");
        model.addAttribute("user", target);
        model.addAttribute("usuarios", rp);
        model.addAttribute("fuentes", vf);

        return "gestionarApp";
    }

    
    @Transactional
    public String borrarUsuario(Model model, HttpSession session, @RequestParam long id){


        System.out.println("ID should be 2, it is in reality: " + id);
        //tried to do it in the database with sql ON DELETE CASCADE but change are not push in the DB
        String deleteValoracionesSql = "DELETE FROM VALORACION WHERE USUARIO_ID = ?";
        entityManager.createNativeQuery(deleteValoracionesSql)
                    .setParameter(1, id)
                    .executeUpdate();

        String deleteReportesSql = "DELETE FROM REPORTE WHERE AUTHOR_ID = ?";
        entityManager.createNativeQuery(deleteReportesSql)
                        .setParameter(1, id)
                        .executeUpdate();

        String sql = "Delete IWUSER WHERE ID = ?";
        entityManager.createNativeQuery(sql)
                    .setParameter(1, id)
                    .executeUpdate();

        List<Fuente> vf = entityManager.createQuery("SELECT v FROM Fuente v ").getResultList();
        List<User> rp = entityManager.createQuery("SELECT r FROM User r ").getResultList();  
        
        vf.sort((a, b) -> b.getReportes().size() - a.getReportes().size());
        vf.sort((x, y) -> y.getReportes().size() - x.getReportes().size());

        User target = (User) session.getAttribute("u");
        model.addAttribute("user", target);
        model.addAttribute("usuarios", rp);
        model.addAttribute("fuentes", vf);

        return "gestionarApp";
    }
}
