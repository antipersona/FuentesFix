package es.ucm.fdi.iw.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;


import es.ucm.fdi.iw.model.User;
import es.ucm.fdi.iw.model.bd.Reporte;
import es.ucm.fdi.iw.model.bd.Fuente;
import es.ucm.fdi.iw.model.bd.Valoracion;
import es.ucm.fdi.iw.model.bd.Fuente.Estado;
import es.ucm.fdi.iw.model.bd.Reporte.EstadoReport;

import java.util.*;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.io.FileReader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Non-authenticated requests only.
 */
@Controller
public class RootController {

    @Autowired
    private EntityManager entityManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

    

    @ModelAttribute
    private void isLogged(Model model, HttpSession session) {
        User user = (User) session.getAttribute("u");
        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("logged", true);
        } else {
            model.addAttribute("logged", false);
        }
    }

    private static final Logger log = LogManager.getLogger(RootController.class);

    @GetMapping("/login")
    public String login(Model model) {
        System.out.println("login");
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        System.out.println("register");
        return "register";
    }

    @GetMapping("/logout") //works like this but not with PostMapping why?
    public String logout(Model model, HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/register")
    @Transactional
    public String register(Model model, String mail, String name, String surname, String username, String password) {
        System.out.println("register");

        if (!entityManager.createNamedQuery("User.byUsername", User.class)
                .setParameter("username", username)
                .getResultList().isEmpty()) {
            model.addAttribute("errors", "Nombre de usuario ya existe");
            return "register";
        }
        // crear e insertar un nuevo usuario con datos del formulario
        User newUser = new User();
        newUser.setUsername(username); // all fct to get or set a attribut defined in User class are creating 
        newUser.setFirstName(surname);//by lombok (@Data has to be noted at the begining of the class)
        newUser.setLastName(name);
        //newUser.setEmail(mail); 
        newUser.setPassword(passwordEncoder.encode(password)); // assuming you're using passwordEncoder for password hashing
        newUser.setEnabled(true); // assuming newly registered users are enabled by default
        newUser.setRoles("USER"); // assuming newly registered users have the "USER" role by default

        entityManager.persist(newUser); // to add the new user in the database
        
        return "redirect:/";
    }

    @GetMapping("/")
    public String index(Model model) {
        System.out.println("index");
        List<Fuente> lf = entityManager.createQuery("SELECT f FROM Fuente f").getResultList();
        model.addAttribute("fuentes", lf);

        return "index";
    }

    @GetMapping("/myRepairs")
    public String showMyRepairsPage(Model model, HttpSession session) {
        User u = entityManager.find(User.class, ((User) session.getAttribute("u")).getId());
        model.addAttribute("reportes", u.getHandlingReports());

        return "myRepairs"; // Assuming "repairs" is the name of your Thymeleaf template
    }
    @GetMapping("/myRepairs/{id}")
    @Transactional
    public String showMyRepairsPage(@PathVariable long id, Model model, HttpSession session){

        /*
        entityManager.createQuery("DELETE FROM Reporte r WHERE r.id = :id")
                 .setParameter("id", id)
                 .executeUpdate();
        */

        User u = entityManager.find(User.class, ((User) session.getAttribute("u")).getId());
        model.addAttribute("reportes", u.getHandlingReports());
        return "myRepairs";
    }
    

    
    @GetMapping("/report/{id}")
    public String report(Model model, @PathVariable long id) {
        model.addAttribute("fuente", entityManager.find(Fuente.class, id));
        return "report";
    }

    @GetMapping("/listFuente")
    public String showFuentes(Model model) {
        System.out.println("listFuente");
        
        List<Fuente> lf = entityManager.createQuery(
            "SELECT f FROM Fuente f", 
            Fuente.class)
                .getResultList();

        lf.sort((a, b) -> b.getReportes().size() - a.getReportes().size());
        model.addAttribute("sortedList", lf);
        return "listFuente";
    }

    @PostMapping("/user/{id}")
    @Transactional
    public String setName(/*HttpServletResponse response,*/
            String newname, @PathVariable long id, 
			Model model, HttpSession session){

                User user = (User) session.getAttribute("u");
                user.setUsername(newname);
                
                entityManager.createQuery("UPDATE FROM IWUSER u SET u.username = :newname WHERE u.id = :id")
                        .setParameter("newname", newname)
                        .setParameter("id", id)
                        .executeUpdate();;
                
                
                model.addAttribute("user", user);
                session.setAttribute("u", user);

                return "redirect:/user/" + id;

            }


//do not work idk where or what is the error
//i probably correct it, i don't remember what was the previous comment for 
    @PostMapping("/report/{id}")
    @Transactional
    public String report(Model model, @PathVariable long id, HttpSession session, String comentario, String tipo) {
        User u = entityManager.find(User.class, ((User) session.getAttribute("u")).getId());

        //maybe the type doesn't fit with the one declare in the sql database
        Reporte newReport = new Reporte();
        newReport.setFuente(entityManager.find(Fuente.class, id));
        newReport.setEstado(EstadoReport.PENDIENTE);
        newReport.setAuthor(u);//userID
        newReport.setTipo(tipo);
        newReport.setComentario(comentario); 
        entityManager.persist(newReport);

        u.getAuthoredReports().add(newReport);

        return "redirect:/fuente/{id}";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        return "profile";
    }

    @Transactional
    @PostMapping("/updateReporte")
    public String updateReporte(@RequestParam("reporteId") long reporteId, @RequestParam("funcionarioId") long funcionarioId) {
        
        int updatedRows = entityManager.createQuery(
            "UPDATE Reporte r SET r.func_id = :funcionarioId, r.estado = 'EN_PROCESO' WHERE r.id = :reporteId")
            .setParameter("funcionarioId", funcionarioId)
            .setParameter("reporteId", reporteId)
            .executeUpdate();
        
        return "Reporte updated successfully";
    }

    @GetMapping("/fuente/{id}")
    public String fuente(Model model, @PathVariable long id, HttpSession session) {
        model.addAttribute("fuente", entityManager.find(Fuente.class, id));
        model.addAttribute("valoracion", new Valoracion());
        User user = (User) session.getAttribute("u");
        if (user != null){
            long userID = user.getId();
            model.addAttribute("user_id", userID);
        }
        

        Fuente fuente = entityManager.find(Fuente.class, id);
        model.addAttribute("valoraciones", fuente.getValoraciones());
        model.addAttribute("reportes", fuente.getReportes());
        

        return "fuente";
    }

    @Transactional
    @PutMapping("/fuente/{id}")
    public ResponseEntity<String> handlePostRequest(@PathVariable("id") long id,  @RequestBody Map<String, Long> requestBody) {
        // Handle the PUT request
        Long reporteId = requestBody.get("reporteId");
        Long funcionarioId = requestBody.get("funcionarioId");

        String sql = "UPDATE REPORTE SET ESTADO = 'EN_PROCESO', FUNC_ID = ? WHERE ID = ?";
        int rowsAffected = entityManager.createNativeQuery(sql)
                                    .setParameter(1, funcionarioId)
                                    .setParameter(2, reporteId)
                                    .executeUpdate();

        return ResponseEntity.ok("Success");
    }

    @Transactional
    @PostMapping("/fuente/{id}")
    public String fuentePost(Model model, @PathVariable long id, HttpSession session, Valoracion valoracion) {
        Fuente fuente = entityManager.find(Fuente.class, id);
        valoracion.setFuente(fuente);
        User user = (User) session.getAttribute("u");

        if (user == null) {
            return "redirect:/login";
        }

        valoracion.setUsuario(user);
        
        Valoracion mergedValoracion = entityManager.merge(valoracion);
        // user.añadirValoracion(mergedValoracion);
        fuente.añadirValoracion(mergedValoracion);
        
        entityManager.persist(mergedValoracion);
        return "redirect:/fuente/" + id;
    }










    @GetMapping("/prueba_css")
    public String prueba_css(Model model) {
        System.out.println("prueba_css");
        return "prueba_css";
    }

    @GetMapping("/load")
    @Transactional
    @ResponseBody
    public String load() {
        try {
            // Lee el archivo JSON
            FileReader fileReader = new FileReader("src/main/resources/static/jsons/response.json");

            // Parsea el archivo JSON
            JSONTokener jsonTokener = new JSONTokener(fileReader);
            JSONArray jsonArray = new JSONArray(jsonTokener);

            // Recorre el JSONArray y crea objetos con cada elemento
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                try {
                    long id = jsonObject.getLong("ID");
                    int cod_barrio = jsonObject.getInt("COD_BARRIO");
                    String barrio = jsonObject.getString("BARRIO");
                    String distrito = jsonObject.getString("DISTRITO");
                    String estado = jsonObject.getString("ESTADO");
                    double gis_x = Double.parseDouble(jsonObject.getString("COORD_GIS_X"));
                    double gis_y = Double.parseDouble(jsonObject.getString("COORD_GIS_Y"));
                    double latitud = jsonObject.getDouble("LATITUD");
                    double longitud = jsonObject.getDouble("LONGITUD");
                    String direccion = jsonObject.getString("NOM_VIA") + " " + jsonObject.getString("NUM_VIA");
                    String direccion_aux = jsonObject.getString("DIRECCION_AUX");
                    String modelo = jsonObject.getString("MODELO");
                    String codigo_interno = jsonObject.getString("CODIGO_INTERNO");

                    Fuente fuente = new Fuente(0, cod_barrio, barrio, distrito, Estado.valueOf(estado), gis_x, gis_y,
                            latitud, longitud, direccion, direccion_aux, modelo, codigo_interno);
                    entityManager.persist(fuente);

                } catch (JSONException e) {
                    // Handle the exception
                    e.printStackTrace();
                }

            }

            // Cierra el FileReader
            fileReader.close();

        } catch (Exception e) {
            System.out.println("Error al cargar el archivo JSON");
            e.printStackTrace();
        }
        return "ok.";
    }
}
