/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author michikato
 */
public class ReporteDeTutoriaAcademica {
    private String comentariosGenerales;
    private Date fechaDeEntrega;
    private int numeroDeAlumnosEnRiesgo;
    private ArrayList<ProblematicaAcademica> problematicas;
    private TutorAcademico tutorAcademico;
    private int idReporteTutoria;

    public ReporteDeTutoriaAcademica() {
    }

    public ReporteDeTutoriaAcademica(String comentariosGenerales, Date fechaDeEntrega, int numeroDeAlumnosEnRiesgo, ArrayList<ProblematicaAcademica> problematicas, TutorAcademico tutorAcademico, int idReporteTutoria) {
        this.comentariosGenerales = comentariosGenerales;
        this.fechaDeEntrega = fechaDeEntrega;
        this.numeroDeAlumnosEnRiesgo = numeroDeAlumnosEnRiesgo;
        this.problematicas = problematicas;
        this.tutorAcademico = tutorAcademico;
        this.idReporteTutoria = idReporteTutoria;
    }

    public String getComentariosGenerales() {
        return comentariosGenerales;
    }

    public void setComentariosGenerales(String comentariosGenerales) {
        this.comentariosGenerales = comentariosGenerales;
    }

    public Date getFechaDeEntrega() {
        return fechaDeEntrega;
    }

    public void setFechaDeEntrega(Date fechaDeEntrega) {
        this.fechaDeEntrega = fechaDeEntrega;
    }

    public int getNumeroDeAlumnosEnRiesgo() {
        return numeroDeAlumnosEnRiesgo;
    }

    public void setNumeroDeAlumnosEnRiesgo(int numeroDeAlumnosEnRiesgo) {
        this.numeroDeAlumnosEnRiesgo = numeroDeAlumnosEnRiesgo;
    }

    public ArrayList<ProblematicaAcademica> getProblematicas() {
        return problematicas;
    }

    public void setProblematicas(ArrayList<ProblematicaAcademica> problematicas) {
        this.problematicas = problematicas;
    }

    public TutorAcademico getTutor() {
        return tutorAcademico;
    }

    public void setTutor(TutorAcademico tutorAcademico) {
        this.tutorAcademico = tutorAcademico;
    }

    public int getIdReporteTutoria() {
        return idReporteTutoria;
    }

    public void setIdReporteTutoria(int idReporteTutoria) {
        this.idReporteTutoria = idReporteTutoria;
    }
            
}
