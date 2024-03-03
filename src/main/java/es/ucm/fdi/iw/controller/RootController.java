package es.ucm.fdi.iw.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.databind.ObjectMapper;

import aj.org.objectweb.asm.TypeReference;
import es.ucm.fdi.iw.model.bd.Fuente;
import java.util.*;


/**
 *  Non-authenticated requests only.
 */
@Controller
public class RootController {

    Map<Long, Fuente> fuentes = crearFuentes();

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

    @GetMapping("/")
    public String index(Model model) {
        try {
            // //cargamos json
            // ObjectMapper objectMapper = new ObjectMapper();
            //pasar los datos al html
            List<Fuente> fuenteList = new ArrayList<>(fuentes.values());
            model.addAttribute("fuentes", fuenteList);
        } catch (Exception e) {
            // handle exception
        }
        return "index";
    }

    @GetMapping("/report")
    public String report(Model model) {
        System.out.println("report");
        return "report";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        System.out.println("profile");
        return "profile";
    }

    @GetMapping("/fuente/{id}")
    public String fuente(Model model, @PathVariable long id) {
        model.addAttribute("fuente", fuentes.get(id));
        return "fuente";
    }

    @GetMapping("/prueba_css")
    public String prueba_css(Model model) {
        System.out.println("prueba_css");
        return "prueba_css";
    }

    private Map<Long, Fuente> crearFuentes() {
        Map<Long, Fuente> fuentes = new HashMap<>();
        
        Fuente fuente1 = new Fuente(1, 1, "barrio1", "distrito1", Fuente.Estado.OPERATIVO, 1, 1, 1, 1, "direccion1", "direccion_aux1", "modelo1", "codigo_interno1");
        Fuente fuente2 = new Fuente(2, 2, "barrio2", "distrito2", Fuente.Estado.CERRADA_TEMPORALMENT, 2, 2, 2, 2, "direccion2", "direccion_aux2", "modelo2", "codigo_interno2");
        Fuente fuente3 = new Fuente(3, 3, "barrio3", "distrito3", Fuente.Estado.NO_OPERATIVO, 3, 3, 3, 3, "direccion3", "direccion_aux3", "modelo3", "codigo_interno3");
        Fuente fuente4 = new Fuente(4, 4, "barrio4", "distrito4", Fuente.Estado.OPERATIVO, 4, 4, 4, 4, "direccion4", "direccion_aux4", "modelo4", "codigo_interno4");
        Fuente fuente5 = new Fuente(5, 5, "barrio5", "distrito5", Fuente.Estado.CERRADA_TEMPORALMENT, 5, 5, 5, 5, "direccion5", "direccion_aux5", "modelo5", "codigo_interno5");

        fuentes.put(fuente1.getId(), fuente1);
        fuentes.put(fuente2.getId(), fuente2);
        fuentes.put(fuente3.getId(), fuente3);
        fuentes.put(fuente4.getId(), fuente4);
        fuentes.put(fuente5.getId(), fuente5);

        return fuentes;
    }
    
}
