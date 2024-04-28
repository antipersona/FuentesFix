package es.ucm.fdi.iw.controller;

import javax.servlet.http.HttpSession;
import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

import es.ucm.fdi.iw.model.bd.Fuente;
import es.ucm.fdi.iw.model.bd.Reporte;
import es.ucm.fdi.iw.model.User;

/**
 *  Site administration.
 *
 *  Access to this end-point is authenticated - see SecurityConfig
 */
@Controller
@RequestMapping("funcionario")
public class FuncionarioController {

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

	private static final Logger log = LogManager.getLogger(FuncionarioController.class);

    @Autowired
	private EntityManager entityManager;

	@GetMapping("{id}")
    public String index(@PathVariable long id, Model model, HttpSession session) {
        User target = entityManager.find(User.class, id);
        List<Fuente> lf = entityManager.createQuery("SELECT f FROM Fuente f").getResultList();
        
        model.addAttribute("fuentes", lf);
        model.addAttribute("user", target);
        return "funcionario";
    }

        @GetMapping("/listFuente")
        public String showFuentes(Model model) {
        System.out.println("listFuente");
        List<Fuente> lf = entityManager.createQuery("SELECT f FROM Fuente f").getResultList();
        List<Reporte> rp = entityManager.createQuery("SELECT r FROM Reporte r").getResultList();
        model.addAttribute("reportes", rp);
        model.addAttribute("fuentes", lf);

        return "listFuente";
    }

    
}
