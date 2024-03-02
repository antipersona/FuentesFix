package es.ucm.fdi.iw.model.bd;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Valoracion {
    @Id
    private long id;
    
    private long f_id;
    private long u_id;

    private int puntuacion;
    private String comentario;
    // lo mismo viene bien guardar tambien el nombnre del usuario

    public Valoracion(long id, long f_id, long u_id, int puntuacion, String comentario) {
        this.id = id;
        this.f_id = f_id;
        this.u_id = u_id;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getF_id() {
        return f_id;
    }

    public void setF_id(long f_id) {
        this.f_id = f_id;
    }

    public long getU_id() {
        return u_id;
    }

    public void setU_id(long u_id) {
        this.u_id = u_id;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }



}
