/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

/**
 *
 * @author Panther
 */
public class ProblematicaAcademica {
    private int idProblematica;
    private String descripcion;
    private int numeroDeEstudiantesAfectados;
    private SolucionAProblematica solucion = new SolucionAProblematica();
    private Profesor profesor;
    private ExperienciaEducativa experienciaEducativa;

    public ProblematicaAcademica() {
    }

    public ProblematicaAcademica(int idProblematica, String descripcion, int numeroDeEstudiantesAfectados, SolucionAProblematica solucion, Profesor profesor, ExperienciaEducativa experienciaEducativa) {
        this.idProblematica = idProblematica;
        this.descripcion = descripcion;
        this.numeroDeEstudiantesAfectados = numeroDeEstudiantesAfectados;
        this.solucion = solucion;
        this.profesor = profesor;
        this.experienciaEducativa = experienciaEducativa;
    }

    public ProblematicaAcademica(SolucionAProblematica solucion) {
        this.solucion = solucion;
    }
    

    public int getIdProblematica() {
        return idProblematica;
    }

    public void setIdProblematica(int idProblematica) {
        this.idProblematica = idProblematica;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNumeroDeEstudiantesAfectados() {
        return numeroDeEstudiantesAfectados;
    }

    public void setNumeroDeEstudiantesAfectados(int numeroDeEstudiantesAfectados) {
        this.numeroDeEstudiantesAfectados = numeroDeEstudiantesAfectados;
    }

    public SolucionAProblematica getSolucion() {
        return solucion;
    }

    public void setSolucion(SolucionAProblematica solucion) {
        this.solucion = solucion;
    }
    
    public String getDescripcionSolucion(){
        return this.solucion.getDescripcion();
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public ExperienciaEducativa getExperienciaEducativa() {
        return experienciaEducativa;
    }

    public void setExperienciaEducativa(ExperienciaEducativa experienciaEducativa) {
        this.experienciaEducativa = experienciaEducativa;
    }
    

    @Override
    public String toString() {
        return "ProblematicaAcademica{" + "idProblematica=" + idProblematica + ", descripcion=" + descripcion + ", numeroDeEstudiantesAfectados=" + numeroDeEstudiantesAfectados + ", solucion=" + solucion + '}';
    }
    
    public String getNombreCompletoProfesor(){
        return profesor.getNombre()+" "+profesor.getApellidoPaterno()+" "+profesor.getApellidoMaterno();
    }
    
    public String getExperienciaEducativaAndNRC(){
        return experienciaEducativa.getNombre()+" "+experienciaEducativa.getNrc();
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
        if (object!= null && object instanceof ProblematicaAcademica) {
            ProblematicaAcademica other = (ProblematicaAcademica) object;
            isEquals=this.getDescripcion().equals(other.getDescripcion()) &&
                    this.getNumeroDeEstudiantesAfectados()== other.getNumeroDeEstudiantesAfectados() &&
                    this.getIdProblematica() == other.getIdProblematica() &&
                    this.getDescripcionSolucion().equals(other.getDescripcionSolucion());
        }
        return isEquals;
    }
}
