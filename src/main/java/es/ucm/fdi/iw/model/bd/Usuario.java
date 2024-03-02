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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getCod_postal() {
        return cod_postal;
    }

    public void setCod_postal(int cod_postal) {
        this.cod_postal = cod_postal;
    }   
}
