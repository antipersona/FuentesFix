package es.ucm.fdi.iw.model.bd;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import es.ucm.fdi.iw.model.User;

@Entity
public class Valoracion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen")
    @SequenceGenerator(name = "gen", sequenceName = "gen")
    private long id;
    
    @ManyToOne
    private Fuente fuente;

    @ManyToOne
    private User usuario;

    private int puntuacionGeneral;
    private int puntuacionCaudal;
    private int puntuacionSabor;
    private int puntuacionTemperatura;
    private String comentario;

    LocalDateTime fecha;

    public Valoracion() {
        fecha = LocalDateTime.now();
    }

    public Valoracion(Fuente fuente, User usuario, int puntuacionGeneral, int puntuacionCaudal, int puntuacionSabor, int puntuacionTemperatura, String comentario) {
        this.fuente = fuente;
        this.usuario = usuario;
        this.puntuacionGeneral = puntuacionGeneral;
        this.puntuacionCaudal = puntuacionCaudal;
        this.puntuacionSabor = puntuacionSabor;
        this.puntuacionTemperatura = puntuacionTemperatura;
        this.comentario = comentario;
        fecha = LocalDateTime.now();
    }

    public int getPuntuacion(){
        return this.puntuacionGeneral;
    }

    public void setFuente(Fuente fuente) {
        this.fuente = fuente;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public Fuente getFuente() {
        return fuente;
    }



    public User getUsuario() {
        return usuario;
    }



    public int getPuntuacionGeneral() {
        return puntuacionGeneral;
    }



    public void setPuntuacionGeneral(int puntuacionGeneral) {
        this.puntuacionGeneral = puntuacionGeneral;
    }



    public int getPuntuacionCaudal() {
        return puntuacionCaudal;
    }



    public void setPuntuacionCaudal(int puntuacionCaudal) {
        this.puntuacionCaudal = puntuacionCaudal;
    }



    public int getPuntuacionSabor() {
        return puntuacionSabor;
    }



    public void setPuntuacionSabor(int puntuacionSabor) {
        this.puntuacionSabor = puntuacionSabor;
    }



    public int getPuntuacionTemperatura() {
        return puntuacionTemperatura;
    }



    public void setPuntuacionTemperatura(int puntuacionTemperatura) {
        this.puntuacionTemperatura = puntuacionTemperatura;
    }



    public String getComentario() {
        return comentario;
    }



    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getFecha() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm");
        String str_fecha = fecha.format(formatter);
        return str_fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }   
}
