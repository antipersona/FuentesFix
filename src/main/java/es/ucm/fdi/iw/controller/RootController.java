package es.ucm.fdi.iw.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *  Non-authenticated requests only.
 */
@Controller
public class RootController {

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
        System.out.println("index");
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

    @GetMapping("/fuente")
    public String fuente(Model model) {
        System.out.println("fuente");
        return "fuente";
    }
}
