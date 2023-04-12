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
    private String clave;
    private String programaEducativo;
    
    public ExperienciaEducativa() {
    }

    public ExperienciaEducativa(String nrc, String nombre, String seccion, String modalidad, String clave) {
        this.nrc = nrc;
        this.nombre = nombre;
        this.seccion = seccion;
        this.modalidad = modalidad;
        this.clave = clave;
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

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getProgramaEducativo() {
        return programaEducativo;
    }

    public void setProgramaEducativo(String programaEducativo) {
        this.programaEducativo = programaEducativo;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        boolean isEquals=false;
        if (object == this) {
            isEquals=true;
        }
        if (object!= null && object instanceof ExperienciaEducativa) {
            ExperienciaEducativa other = (ExperienciaEducativa) object;
            isEquals=this.getClave().equals(other.getClave()) &&
                    this.getModalidad().equals(other.getModalidad()) &&
                    this.getNrc().equals(other.getNrc()) &&
                    this.getSeccion().equals(other.getSeccion()) &&
                    this.getNombre().equals(other.getNombre());
        }
        return isEquals;
    }

    @Override
    public String toString() {
        return "ExperienciaEducativa{" + "nrc=" + nrc + ", nombre=" + nombre + ", seccion=" + seccion + ", modalidad=" + modalidad + ", clave=" + clave + ", programaEducativo=" + programaEducativo + '}';
    }
                           
}
