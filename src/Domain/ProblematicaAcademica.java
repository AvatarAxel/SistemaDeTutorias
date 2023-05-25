/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author Panther
 */
public class ProblematicaAcademica {

    private int idProblematica;
    private String nrc;
    private String experienciaE;
    private String nombreProfesor;
    private String titulo;
    private String descripcion;
    private int numeroDeEstudiantesAfectados;
    private int idReporteTutoria;
   private String fechaTutoria;

    private SolucionAProblematica solucion;
    private Profesor profesor;
    private ExperienciaEducativa experienciaEducativa;
    private PeriodoEscolar periodoEscolar;

    public ProblematicaAcademica() {
    }

    public int getIdReporteTutoria() {
        return idReporteTutoria;
    }

    public void setIdReporteTutoria(int idReporteTutoria) {
        this.idReporteTutoria = idReporteTutoria;
    }

    public ProblematicaAcademica(int idProblematica, String descripcion, int numeroDeEstudiantesAfectados, SolucionAProblematica solucion) {
        this.idProblematica = idProblematica;
        this.descripcion = descripcion;
        this.numeroDeEstudiantesAfectados = numeroDeEstudiantesAfectados;
        this.solucion = solucion;
    }

    public ProblematicaAcademica(SolucionAProblematica solucion) {
        this.solucion = solucion;
    }

    public ProblematicaAcademica(int idProblematica, String nrc, String ExperienciaE, String nombreProfesor, String titulo, String descripcion, int numeroDeEstudiantesAfectados) {
        this.idProblematica = idProblematica;
        this.nrc = nrc;
        this.experienciaE = ExperienciaE;
        this.nombreProfesor = nombreProfesor;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.numeroDeEstudiantesAfectados = numeroDeEstudiantesAfectados;
    }

    public ProblematicaAcademica(String nrc, String ExperienciaE, String nombreProfesor, String titulo, String descripcion, int numeroDeEstudiantesAfectados) {
        this.nrc = nrc;
        this.experienciaE = ExperienciaE;
        this.nombreProfesor = nombreProfesor;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.numeroDeEstudiantesAfectados = numeroDeEstudiantesAfectados;
    }

    public int getIdProblematica() {
        return idProblematica;
    }

    public void setIdProblematica(int idProblematica) {
        this.idProblematica = idProblematica;
    }

    public String getNrc() {
        return nrc;
    }

    public void setNrc(String nrc) {
        this.nrc = nrc;
    }

    public String getExperienciaE() {
        return experienciaE;
    }

    public void setExperienciaE(String experienciaE) {
        this.experienciaE = experienciaE;
    }

    public String getNombreProfesor() {
        return nombreProfesor;
    }

    public void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = nombreProfesor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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

    public String getProfesorName() {
        return this.profesor.getNombre();
    }

    public void setNumeroDeEstudiantesAfectados(int numeroDeEstudiantesAfectados) {
        this.numeroDeEstudiantesAfectados = numeroDeEstudiantesAfectados;
    }

    public String getFechaTutoria() {
        return fechaTutoria;
    }

    public void setFechaTutoria(String fechaTutoria) {
        this.fechaTutoria = fechaTutoria;
    }

    public SolucionAProblematica getSolucion() {
        return solucion;
    }

    public void setSolucion(SolucionAProblematica solucion) {
        this.solucion = solucion;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public void setExperienciaName(String experienciaName) {
        this.experienciaEducativa.setNombre(experienciaName);
    }

    public void setProfesorName(String profesorName) {
        this.profesor.setNombre(profesorName);
    }

    public ExperienciaEducativa getExperienciaEducativa() {
        return experienciaEducativa;
    }

    public void setExperienciaEducativa(ExperienciaEducativa experienciaEducativa) {
        this.experienciaEducativa = experienciaEducativa;
    }

    public String getDescripcionSolucion() {
        return this.solucion.getDescripcion();
    }

    @Override
    public String toString() {
        return "ProblematicaAcademica{" + "idProblematica=" + idProblematica + ", descripcion=" + descripcion + ", numeroDeEstudiantesAfectados=" + numeroDeEstudiantesAfectados + ", solucion=" + solucion + '}';
    }

    public String getNombreCompletoProfesor() {
        return profesor.getNombre() + " " + profesor.getApellidoPaterno() + " " + profesor.getApellidoMaterno();
    }

    public String getExperienciaEducativaAndNRC() {
        return experienciaEducativa.getNombre() + " " + experienciaEducativa.getNrc();
    }

    public String getExperienciaEducativaName() {
        return experienciaEducativa.getNombre();
    }

    public PeriodoEscolar getPeriodoEscolar() {
        return periodoEscolar;
    }

    public void setPeriodoEscolar(PeriodoEscolar periodoEscolar) {
        this.periodoEscolar = periodoEscolar;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        boolean isEquals = false;
        if (object == this) {
            isEquals = true;
        }
        if (object != null && object instanceof ProblematicaAcademica) {
            ProblematicaAcademica other = (ProblematicaAcademica) object;
            isEquals = this.getDescripcion().equals(other.getDescripcion())
                    && this.getNumeroDeEstudiantesAfectados() == other.getNumeroDeEstudiantesAfectados()
                    && this.getIdProblematica() == other.getIdProblematica()
                    && this.getDescripcionSolucion().equals(other.getDescripcionSolucion());
        }
        return isEquals;
    }

}
