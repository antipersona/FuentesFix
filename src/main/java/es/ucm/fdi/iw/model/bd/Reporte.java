package es.ucm.fdi.iw.model.bd;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.ucm.fdi.iw.model.User;



@Entity
@Data
@NoArgsConstructor
@Table(name = "Reporte")
public class Reporte {

    // public enum Tipo {
    //     BAJA_PRESIÓN,
    //     FUGA,
    //     OLOR,
    //     COLOR,
    //     SABOR,
    //     SUCIEDAD,
    //     OTRO,

    // }

    public enum EstadoReport {
        PENDIENTE,
        EN_PROCESO,
        SOLUCIONADO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen")
    @SequenceGenerator(name = "gen", sequenceName = "gen")
	private long id;

    //@ManyToOne only if Fuente instead of long, un reporte tiene una fuente, una fuente puede tener varios reportes
    private long fuente_id; 

    //@ManyToOne o,ly if User instead of long, un reporte tiene un usuario, un usuario puede tener varios reportes
    private long usuario_id; 

    private String tipo;
    private String comentario;
    // a lo mejor un archivo adjunto

    // parte del funcionario
    @Enumerated(EnumType.ORDINAL)
    private EstadoReport estado;
    
    private String comentario_reporte;
    private int prioridad;

}
