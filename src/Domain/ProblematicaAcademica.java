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

    public ProblematicaAcademica() {
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
}
