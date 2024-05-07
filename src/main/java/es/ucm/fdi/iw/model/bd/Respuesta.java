package es.ucm.fdi.iw.model.bd;

import es.ucm.fdi.iw.model.User;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Respuesta")
public class Respuesta {
    @Id
    private long id;

    @ManyToOne
    private Valoracion valoracion;

    @ManyToOne
    private User usuario;

    private String comentario;

    public Respuesta(long id, Valoracion valoracion, User usuario, String comentario) {
        this.id = id;
        this.valoracion = valoracion;
        this.usuario = usuario;
        this.comentario = comentario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Valoracion getValoracion() {
        return valoracion;
    }

    public void setValoracion(Valoracion valoracion) {
        this.valoracion = valoracion;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

}
