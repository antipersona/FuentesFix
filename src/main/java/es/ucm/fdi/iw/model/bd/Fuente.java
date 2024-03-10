package es.ucm.fdi.iw.model.bd;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.util.*;

@Entity
@Table(name = "Fuente")
public class Fuente {
    public enum Estado {
        CERRADA_TEMPORALMENT,
        OPERATIVO,
        NO_OPERATIVO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen")
    @SequenceGenerator(name = "gen", sequenceName = "gen")
    private long id;
    private int cod_barrio;
    private String barrio;
    private String distrito;

    private Estado estado;

    private double gis_x;
    private double gis_y;

    private double latitud;
    private double longitud;

    private String direccion;
    private String direccion_aux;

    private String modelo;
    private String codigo_interno;

    private String imagen = "/img/fuentes/default.jpeg";

    private int mediaValoracion = 0;
    private int numValoraciones = 0;
    private int sumaValoraciones = 0;// encantamiento eficiencia 4 de calculo de medias
    private int sabor = -1;
    private int temperatura = -1;
    private int olor = -1;

    @OneToMany
    private List<Valoracion> valoraciones;

    public Fuente(long id, int cod_barrio, String barrio, String distrito, Estado estado, double gis_x, double gis_y,
            double latitud,
            double longitud, String direccion, String direccion_aux, String modelo, String codigo_interno) {
        this.id = id;
        this.cod_barrio = cod_barrio;
        this.barrio = barrio;
        this.distrito = distrito;
        this.estado = estado;
        this.gis_x = gis_x;
        this.gis_y = gis_y;
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccion = direccion;
        this.direccion_aux = direccion_aux;
        this.modelo = modelo;
        this.codigo_interno = codigo_interno;
        this.valoraciones = new ArrayList<>();
    }

    public Fuente() {
    }

    public void añadirValoracion(Valoracion v) {
        this.valoraciones.add(v);
        actualizarMediaValoraciones(v.getPuntuacion());
    }

    private void actualizarMediaValoraciones(int val) {
        numValoraciones++;
        sumaValoraciones += val;
        mediaValoracion = sumaValoraciones / numValoraciones;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCod_barrio() {
        return cod_barrio;
    }

    public void setCod_barrio(int cod_barrio) {
        this.cod_barrio = cod_barrio;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public double getGis_x() {
        return gis_x;
    }

    public void setGis_x(double gis_x) {
        this.gis_x = gis_x;
    }

    public double getGis_y() {
        return gis_y;
    }

    public void setGis_y(double gis_y) {
        this.gis_y = gis_y;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDireccion_aux() {
        return direccion_aux;
    }

    public void setDireccion_aux(String direccion_aux) {
        this.direccion_aux = direccion_aux;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCodigo_interno() {
        return codigo_interno;
    }

    public void setCodigo_interno(String codigo_interno) {
        this.codigo_interno = codigo_interno;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getMediaValoracion() {
        return mediaValoracion;
    }

    public void setMediaValoracion(int mediaValoracion) {
        this.mediaValoracion = mediaValoracion;
    }

    public int getNumValoraciones() {
        return numValoraciones;
    }

    public void setNumValoraciones(int numValoraciones) {
        this.numValoraciones = numValoraciones;
    }

    public int getSabor() {
        return sabor;
    }

    public void setSabor(int sabor) {
        this.sabor = sabor;
    }

    public int getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    public int getOlor() {
        return olor;
    }

    public void setOlor(int olor) {
        this.olor = olor;
    }

    public int getSumaValoraciones() {
        return sumaValoraciones;
    }

    public void setSumaValoraciones(int sumaValoraciones) {
        this.sumaValoraciones = sumaValoraciones;
    }

    public List<Valoracion> getValoraciones() {
        return valoraciones;
    }

    public void setValoraciones(List<Valoracion> valoraciones) {
        this.valoraciones = valoraciones;
    }
}
