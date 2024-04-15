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
import org.springframework.security.crypto.password.PasswordEncoder;


import es.ucm.fdi.iw.model.User;
import es.ucm.fdi.iw.model.bd.Fuente;
import es.ucm.fdi.iw.model.bd.Valoracion;
import es.ucm.fdi.iw.model.bd.Fuente.Estado;

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

    private static final Logger log = LogManager.getLogger(RootController.class);

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
        newUser.setEmail(mail); 
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

    @GetMapping("/report/{id}")
    public String report(Model model, @PathVariable long id) {
        model.addAttribute("fuente", entityManager.find(Fuente.class, id));
        return "report";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        return "profile";
    }

    @GetMapping("/fuente/{id}")
    public String fuente(Model model, @PathVariable long id) {
        model.addAttribute("fuente", entityManager.find(Fuente.class, id));
        model.addAttribute("valoracion", new Valoracion());

        Fuente fuente = entityManager.find(Fuente.class, id);
        List<Valoracion> valoraciones = fuente.getValoraciones();
        model.addAttribute("valoraciones", valoraciones);

        return "fuente";
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
