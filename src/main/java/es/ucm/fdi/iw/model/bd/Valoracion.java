package es.ucm.fdi.iw.model.bd;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Valoracion {
    @Id
    private long id;
    
    @ManyToOne
    private Fuente fuente;

    @ManyToOne
    private Usuario usuario;

    private int puntuacion;
    private String comentario;

    public Valoracion(long id, Fuente fuente, Usuario usuario, int puntuacion, String comentario) {
        this.id = id;
        this.fuente = fuente;
        this.usuario = usuario;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
    }
}
