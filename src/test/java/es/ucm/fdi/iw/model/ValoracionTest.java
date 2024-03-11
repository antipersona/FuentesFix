package es.ucm.fdi.iw.model;
import es.ucm.fdi.iw.model.bd.Fuente;
import es.ucm.fdi.iw.model.bd.Valoracion;

import java.time.Duration;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.intuit.karate.Http;
@SpringBootTest
class ValoracionTest {

    @Test
    public void testFuenteReceivesValoracion() { // Test comprueba que la fuente recibe la valoración
        User usuario = new User();
        Fuente fuente = new Fuente();
        Valoracion valoracion = new Valoracion(fuente, usuario, 5, 5, 5, 5, "Comentario");
        valoracion.setFuente(fuente);
        Assertions.assertEquals(fuente, valoracion.getFuente());
    }

    @Test
    public void testAverageRatings() {
        User usuario = new User();
        Fuente fuente = new Fuente();
        
        Valoracion valoracion1 = new Valoracion(fuente, usuario, 5, 5, 5, 5, "Comentario 1");
        Valoracion valoracion2 = new Valoracion(fuente, usuario, 4, 3, 2, 1, "Comentario 2");
        Valoracion valoracion3 = new Valoracion(fuente, usuario, 2, 3, 4, 5, "Comentario 3");

        valoracion1.setFuente(fuente);
        valoracion2.setFuente(fuente);
        valoracion3.setFuente(fuente);

        fuente.añadirValoracion(valoracion1);
        fuente.añadirValoracion(valoracion2);
        fuente.añadirValoracion(valoracion3);
        
        double expectedGeneralRating = (5 + 4 + 2) / 3.0;
        double expectedCaudalRating = (5 + 3 + 3) / 3.0;
        double expectedSaborRating = (5 + 2 + 4) / 3.0;
        double expectedTemperaturaRating = (5 + 1 + 5) / 3.0;
        
        Assertions.assertEquals(expectedGeneralRating, fuente.getMediaValoracionGeneral(), 0.01);
        Assertions.assertEquals(expectedCaudalRating, fuente.getMediaValoracionCaudal(), 0.01);
        Assertions.assertEquals(expectedSaborRating, fuente.getMediaValoracionSabor(), 0.01);
        Assertions.assertEquals(expectedTemperaturaRating, fuente.getMediaValoracionTemperatura(), 0.01);
    }
}
