package es.ucm.fdi.iw.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.jni.File;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;


import com.fasterxml.jackson.core.type.TypeReference;
import es.ucm.fdi.iw.model.bd.Fuente;
import es.ucm.fdi.iw.model.bd.Fuente.Estado;
import lombok.var;

import java.util.*;

import javax.persistence.criteria.Path;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.core.io.ClassPathResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
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
    private HttpSession session;

    Map<Long, Fuente> fuenteMap;
    List<Fuente> fuenteList;

    private static final Logger log = LogManager.getLogger(RootController.class);

    public RootController() {
        fuenteMap = cargarFuentes();
        fuenteList = new ArrayList<>(fuenteMap.values());
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

    @GetMapping("/")
    public String index(Model model) {
        System.out.println("index");
        model.addAttribute("fuentes", fuenteList);

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
        model.addAttribute("fuente", fuenteMap.get(id));
        return "fuente";
    }

    @GetMapping("/prueba_css")
    public String prueba_css(Model model) {
        System.out.println("prueba_css");
        return "prueba_css";
    }

    @ModelAttribute
    private void isLogged(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean logged = authentication != null && authentication.isAuthenticated();
        model.addAttribute("logged", logged); 
    }


    private Map<Long, Fuente> cargarFuentes(){

        Map<Long, Fuente> fuentes = new HashMap<>();

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

                    // Your code here

                
                
                Fuente fuente = new Fuente(id, cod_barrio, barrio, distrito, Estado.valueOf(estado), gis_x, gis_y,latitud, longitud, direccion, direccion_aux, modelo, codigo_interno);
                
                System.out.println(fuente.getBarrio() + " " + fuente.getDistrito() + " " + fuente.getEstado() + " " + fuente.getDireccion() + " " + fuente.getDireccion_aux() + " " + fuente.getModelo() + " " + fuente.getCodigo_interno() + " " + fuente.getGis_x() + " " + fuente.getGis_y() + " " + fuente.getLatitud() + " " + fuente.getLongitud() + " " + fuente.getId());

                fuentes.put(fuente.getId(), fuente);

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

        return fuentes;
    }
    
    private Map<Long, Fuente> crearFuentes() {
    
        Map<Long, Fuente> fuentes = new HashMap<>();
        
        Fuente fuente1 = new Fuente(3530621, 113, "SAN ISIDRO", "CARABANCHEL", Fuente.Estado.CERRADA_TEMPORALMENT,
            438194030L, 4471508960L, 4039188807L, -372824197, "CALLE OROYA 3",
            "C/V C/ ALGORTA, EN JARDIN PROXIMO A FAROLA 4", "MU-37A", "FUE_11_0128");
        Fuente fuente2 = new Fuente(3531365, 87, "MIRASIERRA", "FUENCARRAL - EL PARDO",
            Fuente.Estado.CERRADA_TEMPORALMENT, 438842224L, 4483134840L, 4049666643L, -372172440,
            "CALLE MARIA DE MAEZTU 148", null, "EGEA", "FUE_08_0147");
        Fuente fuente3 = new Fuente(3531367, 87, "MIRASIERRA", "FUENCARRAL - EL PARDO",
            Fuente.Estado.CERRADA_TEMPORALMENT, 438722367L, 4482907290L, 4049460774L, -372311672,
            "CALLE MARIA DE MAEZTU 120", "JUNTO A JUEGOS MAYORES", "EGEA", "FUE_08_0148");
        Fuente fuente4 = new Fuente(3531372, 87, "MIRASIERRA", "FUENCARRAL - EL PARDO",
            Fuente.Estado.CERRADA_TEMPORALMENT, 438584784L, 4482766800L, 40493332L, -372472655,
            "CALLE SENDA DEL INFANTE 54", "C/V CALLE SENDA DEL INFANTE", "EGEA", "FUE_08_0149");
        Fuente fuente5 = new Fuente(3531377, 84, "PILAR", "FUENCARRAL - EL PARDO", Fuente.Estado.CERRADA_TEMPORALMENT,
            440043598, 448052480L, 4047324192L, -370730113, "AVENIDA EL FERROL 35", "CALLE RIBADAVIA 45", "MU-37A",
            "FUE_08_0152");

        fuentes.put(fuente1.getId(), fuente1);
        fuentes.put(fuente2.getId(), fuente2);
        fuentes.put(fuente3.getId(), fuente3);
        fuentes.put(fuente4.getId(), fuente4);
        fuentes.put(fuente5.getId(), fuente5);
    
        return fuentes;
    }



}
