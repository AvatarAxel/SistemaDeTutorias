/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

/**
 *
 * @author Panther
 */
public class ExperienciaEducativa {
    private String nrc;
    private String nombre;
    private String seccion;
    private String modalidad;
    private String salon;
    private String descripcion;
    private String programaEducativo;

    public ExperienciaEducativa() {
    }
    
    public ExperienciaEducativa(String nrc, String nombre, String seccion, String modalidad) {
        this.nrc = nrc;
        this.nombre = nombre;
        this.seccion = seccion;
        this.modalidad = modalidad;
    }

    public ExperienciaEducativa(String nrc, String nombre, String seccion, String modalidad, String salon, String descripcion, String programaEducativo) {
        this.nrc = nrc;
        this.nombre = nombre;
        this.seccion = seccion;
        this.modalidad = modalidad;
        this.salon = salon;
        this.descripcion = descripcion;
        this.programaEducativo = programaEducativo;
    }

    public String getNrc() {
        return nrc;
    }

    public void setNrc(String nrc) {
        this.nrc = nrc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getSalon() {
        return salon;
    }

    public void setSalon(String salon) {
        this.salon = salon;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getProgramaEducativo() {
        return programaEducativo;
    }

    public void setProgramaEducativo(String programaEducativo) {
        this.programaEducativo = programaEducativo;
    }

    @Override
    public String toString() {
        return "ExperienciaEducativa{" + "nrc=" + nrc + ", nombre=" + nombre + ", seccion=" + seccion + ", modalidad=" + modalidad + ", salon=" + salon + ", descripcion=" + descripcion + ", programaEducativo=" + programaEducativo + '}';
    }
    
}
