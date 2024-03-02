package es.ucm.fdi.iw.model.bd;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Usuario {

    public enum Rol {
        USER,			// ciudadano, valora fuentes y reporta errores
        ADMIN,          // puede modificar las fuentes y moderar comentarios
        FUNCIONARIO,    // Recibe reportes de errores y puede marcarlos como solucionados, ademas les puede poner una prioridad y un tiempo estimado
        //MODERADOR,      // Puede moderar comentarios
    }
    
    @Id
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String mail;

    @Column(nullable = false)
    private String password;

    
    private String firstName;
    private String lastName;

    private int cod_postal;

    // constructor con lo eseencial
    public Usuario(long id, String username, String mail, String password) {
        this.id = id;
        this.username = username;
        this.mail = mail;
        this.password = password;
    }
}
