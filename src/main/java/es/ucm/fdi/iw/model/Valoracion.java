package es.ucm.fdi.iw.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Valoracion {

    private long id;
    private String texto;
    private short puntuacion;
    private long uId;
    private LocalDateTime date;

    public Valoracion(long id, String texto, short puntuacion, long uId) {
        this.id = id;
        this.texto = texto;
        this.puntuacion = puntuacion;
        this.uId = uId;
    }

    public String toJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            return "{}";
        }
    }

}
