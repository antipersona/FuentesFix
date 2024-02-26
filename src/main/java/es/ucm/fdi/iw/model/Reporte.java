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

public class Reporte {

    public enum Tipo {
        OPERATIVA, CERRADA_TEMPORALMENTE
    }

    private long id;
    private String texto;
    private Tipo tipo;
    private long uId;
    private LocalDateTime date;

    public Reporte(long id, String texto, Tipo tipo, long uId) {
        this.id = id;
        this.texto = texto;
        this.tipo = tipo;
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
