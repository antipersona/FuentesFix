package es.ucm.fdi.iw.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Fuente {

    public enum Estado {
        OPERATIVA, CERRADA_TEMPORALMENTE
    }

    private long id;
    private String descripcion;
    private String nombre;
    private String direccion;
    private String direccion_aux;
    private int lat;
    private int lon;
    private Estado estado;
    private int nValoraciones;
    private List<Valoracion> valoraciones = new ArrayList<>();
    private List<Reporte> reportes = new ArrayList<>(); 

    public Fuente(int id, String descripcion, String nombre, String direccion, String direccion_aux, int lat, int lon, Estado estado) {
        this.id = id;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.direccion = direccion;
        this.direccion_aux = direccion_aux;
        this.lat = lat;
        this.lon = lon;
        this.estado = estado;
        this.nValoraciones = 0;
    }

    public String toJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            return "{}";
        }
    }

    public int getnValoraciones(){
        return nValoraciones;
    }

}
