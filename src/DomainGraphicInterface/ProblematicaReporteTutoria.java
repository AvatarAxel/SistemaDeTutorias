/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DomainGraphicInterface;

import Domain.ExperienciaEducativa;
import Domain.ProblematicaAcademica;
import Domain.Profesor;
import Domain.ReporteDeTutoriaAcademica;
import Domain.SolucionAProblematica;
import Domain.TutoriaAcademica;
import java.time.LocalDate;

/**
 *
 * @author Panther
 */
public class ProblematicaReporteTutoria {
    private ProblematicaAcademica problematica;
    private ReporteDeTutoriaAcademica reporte;
    private TutoriaAcademica tutoria;

    public ProblematicaReporteTutoria() {
        this.problematica = new ProblematicaAcademica();
        this.reporte = new ReporteDeTutoriaAcademica();
        this.tutoria = new TutoriaAcademica();
    }

    public ProblematicaAcademica getProblematica() {
        return problematica;
    }

    public void setProblematica(ProblematicaAcademica problematica) {
        this.problematica = problematica;
    }

    public ReporteDeTutoriaAcademica getReporte() {
        return reporte;
    }

    public void setReporte(ReporteDeTutoriaAcademica reporte) {
        this.reporte = reporte;
    }

    public TutoriaAcademica getTutoria() {
        return tutoria;
    }

    public void setTutoria(TutoriaAcademica tutoria) {
        this.tutoria = tutoria;
    }
    
    //Especial Getters & Setters
    
    public String getDescripcion(){
        return this.problematica.getDescripcion();
    }
    
    public int getNumeroDeEstudiantesAfectados(){
        return this.problematica.getNumeroDeEstudiantesAfectados();
    }
    
    public SolucionAProblematica getSolucionAProblematica(){
        return this.problematica.getSolucion();
    }
    
    public int getIdPeriodo(){
        return this.tutoria.getPeriodoEscolar().getIdPeriodoEscolar();
    }
}
