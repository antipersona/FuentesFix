package es.ucm.fdi.iw.model.bd;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

import java.util.*;

@Entity
@Table(name = "Fuente")
@Data
public class Fuente {
    public enum Estado {
        CERRADA_TEMPORALMENT,
        OPERATIVO,
        NO_OPERATIVO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen")
    @SequenceGenerator(name = "gen", sequenceName = "gen")
    private long id;

    @OneToMany
    private List<Valoracion> valoraciones;

    @OneToMany
    @JoinColumn(name = "fuente_id")
    private List<Reporte> reportes;

    private int cod_barrio;
    private String barrio;
    private String distrito;

    private Estado estado;

    private double gis_x;
    private double gis_y;

    private double latitud;
    private double longitud;

    private String direccion;
    private String direccion_aux;

    private String modelo;
    private String codigo_interno;

    private String imagen = "/img/fuentes/default.jpeg";

    private float mediaValoracionGeneral = 0;
    private float mediaValoracionCaudal = 0;
    private float mediaValoracionSabor = 0;
    private float mediaValoracionTemperatura = 0;
    private double sumaValoracionesCaudal = 0; // encantamiento eficiencia 4 de calculo de medias
    private double sumaValoracionesSabor = 0;
    private double sumaValoracionesTemperatura = 0;
    private double sumaValoracionesGeneral = 0;
    private int sabor = -1;
    private int temperatura = -1;
    private int olor = -1;

    public Fuente(long id, int cod_barrio, String barrio, String distrito, Estado estado, double gis_x, double gis_y,
            double latitud,
            double longitud, String direccion, String direccion_aux, String modelo, String codigo_interno) {
        this.id = id;
        this.cod_barrio = cod_barrio;
        this.barrio = barrio;
        this.distrito = distrito;
        this.estado = estado;
        this.gis_x = gis_x;
        this.gis_y = gis_y;
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccion = direccion;
        this.direccion_aux = direccion_aux;
        this.modelo = modelo;
        this.codigo_interno = codigo_interno;
        this.valoraciones = new ArrayList<>();
    }

    public Fuente() {
    }

    /**
     * Si alguien valora muchas veces, no tienes forma de detectarlo.
     * 
     * Tampoco puedes detectar si alguien valora muchas cosas y luego desaparece (no puedes "deshacer sus valoraciones")
     * @param v
     */
    public void añadirValoracion(Valoracion v) {
        this.valoraciones.add(v);
        actualizarMediaValoraciones(v);
    }

    public void anadirReporte(Reporte r) {
        this.reportes.add(r);
        
    }

    private void actualizarMediaValoraciones(Valoracion v) {
        int numValoraciones = valoraciones.size();

        // para que no se sumen ceros
        sumaValoracionesCaudal += (v.getPuntuacionCaudal() == 0) ? mediaValoracionCaudal : v.getPuntuacionCaudal();
        mediaValoracionCaudal = (float) sumaValoracionesCaudal / numValoraciones;

        sumaValoracionesSabor += (v.getPuntuacionSabor() == 0) ? mediaValoracionSabor : v.getPuntuacionSabor();
        mediaValoracionSabor = (float) sumaValoracionesSabor / numValoraciones;

        sumaValoracionesTemperatura += (v.getPuntuacionTemperatura() == 0) ? mediaValoracionTemperatura : v.getPuntuacionTemperatura();
        mediaValoracionTemperatura = (float) sumaValoracionesTemperatura / numValoraciones;

        sumaValoracionesGeneral += (v.getPuntuacionGeneral() == 0) ? mediaValoracionGeneral : v.getPuntuacionGeneral();
        mediaValoracionGeneral = (float) sumaValoracionesGeneral / numValoraciones;
    }
}
