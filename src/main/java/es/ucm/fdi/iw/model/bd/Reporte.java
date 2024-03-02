package es.ucm.fdi.iw.model.bd;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Reporte {
    
    public enum Tipo {
        BAJA_PRESIÓN,
        FUGA,
        OLOR,
        COLOR,
        SABOR,
        SUCIEDAD,
        OTRO,

    }

    public enum Estado {
        PENDIENTE,
        EN_PROCESO,
        SOLUCIONADO
    }
    
    @Id
    private long id;
    
    // TODO hacer el join con la tabla de usuarios
    private long f_id;

    // TODO hacer el join con la tabla de fuentes
    private long u_id;

    private Tipo tipo;
    private String comentario;
    //a lo mejor un archivo adjunto


    // parte del funcionario
    private Estado estado;
    private String comentario_reporte;
    private int prioridad;


    public Reporte(long id, long f_id, long u_id, Tipo tipo, String comentario) {
        this.id = id;
        this.f_id = f_id;
        this.u_id = u_id;
        this.tipo = tipo;
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


    public Tipo getTipo() {
        return tipo;
    }


    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }


    public String getComentario() {
        return comentario;
    }


    public void setComentario(String comentario) {
        this.comentario = comentario;
    }


    public Estado getEstado() {
        return estado;
    }


    public void setEstado(Estado estado) {
        this.estado = estado;
    }


    public String getComentario_reporte() {
        return comentario_reporte;
    }


    public void setComentario_reporte(String comentario_reporte) {
        this.comentario_reporte = comentario_reporte;
    }


    public int getPrioridad() {
        return prioridad;
    }


    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

       



}
