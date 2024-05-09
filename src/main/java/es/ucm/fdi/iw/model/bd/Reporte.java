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

    @ManyToOne 
    private Fuente fuente; 

    @ManyToOne
    private User author; 

    private String tipo;
    private String comentario;
    // a lo mejor un archivo adjunto

    // parte del funcionario
    @Enumerated(EnumType.ORDINAL)
    private EstadoReport estado;
    
    @ManyToOne
    private User responsible;

    @Override
    public String toString() {
        return id + " " + author + " " + comentario;
    }

}
