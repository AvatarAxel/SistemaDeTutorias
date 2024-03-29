
package Domain;

import javafx.scene.control.CheckBox;

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
    private String profesorNombre;
    private String programaEducativo;
    private Profesor profesor;
    private CheckBox esSeleccionado;
    private int idexperiencia_periodo_profesor;
    
    public ExperienciaEducativa() {
    }

    public ExperienciaEducativa(String nrc, String nombre, String seccion, String modalidad, String clave, Profesor profesor, CheckBox esSeleccionado, int idexperiencia_periodo_profesor) {
        this.nrc = nrc;
        this.nombre = nombre;
        this.seccion = seccion;
        this.modalidad = modalidad;
        this.clave = clave;
        this.profesor = profesor;
        this.esSeleccionado = esSeleccionado;
        this.idexperiencia_periodo_profesor = idexperiencia_periodo_profesor;
        this.esSeleccionado = new CheckBox();
    }

    public ExperienciaEducativa(String nrc, String profesorNombre,String nombre) {
        this.nrc = nrc;
        this.profesorNombre = profesorNombre;
        this.nombre = nombre;
    }
    
    

    public ExperienciaEducativa(String nrc, String nombre, String seccion, String modalidad, String clave) {
        this.nrc = nrc;
        this.nombre = nombre;
        this.seccion = seccion;
        this.modalidad = modalidad;
        this.clave = clave;
        this.esSeleccionado = new CheckBox();
    }

    public String getProfesorNombre() {
        return profesorNombre;
    }

    public void setProfesorNombre(String profesorNombre) {
        this.profesorNombre = profesorNombre;
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

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public String getNombreCompletoProfesor() {
        return profesor.getNombre() + " " + profesor.getApellidoPaterno() + " " + profesor.getApellidoMaterno();
    }    

    public CheckBox getEsSeleccionado() {
        return esSeleccionado;
    }

    public void setEsSeleccionado(CheckBox esSeleccionado) {
        this.esSeleccionado = esSeleccionado;
    }

    public int getIdexperiencia_periodo_profesor() {
        return idexperiencia_periodo_profesor;
    }

    public void setIdexperiencia_periodo_profesor(int idexperiencia_periodo_profesor) {
        this.idexperiencia_periodo_profesor = idexperiencia_periodo_profesor;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    /*@Override
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
    }*/

    @Override
    public String toString() {
        return   getProfesorNombre() + " - nrc: " + nrc;
    }
            
}