package es.ucm.fdi.iw.model.bd;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Reporte")
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

    @ManyToOne // un reporte tiene una fuente, una fuente puede tener varios reportes
    private Fuente fuente;

    @ManyToOne // un reporte tiene un usuario, un usuario puede tener varios reportes
    private Usuario usuario;

    private Tipo tipo;
    private String comentario;
    // a lo mejor un archivo adjunto

    // parte del funcionario
    private Estado estado;
    private String comentario_reporte;
    private int prioridad;

    public Reporte(long id, Fuente fuente, Usuario usuario, Tipo tipo, String comentario) {
        this.id = id;
        this.fuente = fuente;
        this.usuario = usuario;
        this.tipo = tipo;
        this.comentario = comentario;
    }

}
