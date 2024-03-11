package es.ucm.fdi.iw.model;
import es.ucm.fdi.iw.model.bd.Fuente;
import es.ucm.fdi.iw.model.bd.Valoracion;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.intuit.karate.Http;

public class ValoracionTest {

    @Test
    public void testFuenteReceivesValoracion() {
        // Create a new User
        User usuario = new User();


        // Create a new Fuente
        Fuente fuente = new Fuente();

        // Create a new Valoracion
        Valoracion valoracion = new Valoracion(fuente, usuario, 5, 5, 5, 5, "Comentario");

        // Set the Fuente for the Valoracion
        valoracion.setFuente(fuente);

        // Assert that the Fuente in the Valoracion is the same as the created Fuente
        Assertions.assertEquals(fuente, valoracion.getFuente());
    }
}
