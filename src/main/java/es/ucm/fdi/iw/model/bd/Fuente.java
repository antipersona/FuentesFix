package es.ucm.fdi.iw.model.bd;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Fuente {
    public enum Estado {
        CERRADA_TEMPORALMENT,
        OPERATIVO,
        NO_OPERATIVO
    }

    @Id
    private long id;

    private int cod_barrio;
    private String barrio;
    private String distrito;

    private Estado estado;

    private long gis_x;
    private long gis_y;

    private long latitud;
    private long longitud;

    private String direccion;
    private String direccion_aux;

    private String modelo;
    private String codigo_interno;


    public Fuente(long id, int cod_barrio, String barrio, String distrito, Estado estado, long gis_x, long gis_y,
            long latitud, long longitud, String direccion, String direccion_aux, String modelo, String codigo_interno) {
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
    }


    // lo mismo sobran, estan creados usando una extension asi que
    // no es que sea una perdida de tiempo haberlos creado
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


    public long getGis_x() {
        return gis_x;
    }


    public void setGis_x(long gis_x) {
        this.gis_x = gis_x;
    }


    public long getGis_y() {
        return gis_y;
    }


    public void setGis_y(long gis_y) {
        this.gis_y = gis_y;
    }


    public long getLatitud() {
        return latitud;
    }


    public void setLatitud(long latitud) {
        this.latitud = latitud;
    }


    public long getLongitud() {
        return longitud;
    }


    public void setLongitud(long longitud) {
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

    
}
