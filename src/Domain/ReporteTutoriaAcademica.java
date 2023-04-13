/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import java.sql.Date;

/**
 *
 * @author Propietario
 */
public class ReporteTutoriaAcademica {
    private int idReporteTutoriasAcademicas;
    private int numeroDeSesion;
    private int numEstudiantesAsistentes;
    private int numEstudiantesEnRiesgo;
    private int idTutoriaAcademica;
    private Date fechaInicioTutoriaAcademica;
    private Date fechaFinTutoriaAcademica;
    private Date fechaInicioPeriodoEscolar;
    private Date fechaFinPeriodoEscolar;
    private String comentarioGeneral;
    private int numeroDePersonal;
    private int idReporteGeneralTutoriasAcademicas;

    public int getIdReporteTutoriasAcademicas() {
        return idReporteTutoriasAcademicas;
    }

    public void setIdReporteTutoriasAcademicas(int idReporteTutoriasAcademicas) {
        this.idReporteTutoriasAcademicas = idReporteTutoriasAcademicas;
    }

    public int getNumeroDeSesion() {
        return numeroDeSesion;
    }

    public void setNumeroDeSesion(int numeroDeSesion) {
        this.numeroDeSesion = numeroDeSesion;
    }

    public int getNumEstudiantesAsistentes() {
        return numEstudiantesAsistentes;
    }

    public void setNumEstudiantesAsistentes(int numEstudiantesAsistentes) {
        this.numEstudiantesAsistentes = numEstudiantesAsistentes;
    }

    public int getNumEstudiantesEnRiesgo() {
        return numEstudiantesEnRiesgo;
    }

    public void setNumEstudiantesEnRiesgo(int numEstudiantesEnRiesgo) {
        this.numEstudiantesEnRiesgo = numEstudiantesEnRiesgo;
    }

    public int getIdTutoriaAcademica() {
        return idTutoriaAcademica;
    }

    public void setIdTutoriaAcademica(int idTutoriaAcademica) {
        this.idTutoriaAcademica = idTutoriaAcademica;
    }

    public Date getFechaInicioTutoriaAcademica() {
        return fechaInicioTutoriaAcademica;
    }

    public void setFechaInicioTutoriaAcademica(Date fechaInicioTutoriaAcademica) {
        this.fechaInicioTutoriaAcademica = fechaInicioTutoriaAcademica;
    }

    public Date getFechaFinTutoriaAcademica() {
        return fechaFinTutoriaAcademica;
    }

    public void setFechaFinTutoriaAcademica(Date fechaFinTutoriaAcademica) {
        this.fechaFinTutoriaAcademica = fechaFinTutoriaAcademica;
    }

    public Date getFechaInicioPeriodoEscolar() {
        return fechaInicioPeriodoEscolar;
    }

    public void setFechaInicioPeriodoEscolar(Date fechaInicioPeriodoEscolar) {
        this.fechaInicioPeriodoEscolar = fechaInicioPeriodoEscolar;
    }

    public Date getFechaFinPeriodoEscolar() {
        return fechaFinPeriodoEscolar;
    }

    public void setFechaFinPeriodoEscolar(Date fechaFinPeriodoEscolar) {
        this.fechaFinPeriodoEscolar = fechaFinPeriodoEscolar;
    }

    public String getComentarioGeneral() {
        return comentarioGeneral;
    }

    public void setComentarioGeneral(String comentarioGeneral) {
        this.comentarioGeneral = comentarioGeneral;
    }

    public int getNumeroDePersonal() {
        return numeroDePersonal;
    }

    public void setNumeroDePersonal(int numeroDePersonal) {
        this.numeroDePersonal = numeroDePersonal;
    }

    public int getIdReporteGeneralTutoriasAcademicas() {
        return idReporteGeneralTutoriasAcademicas;
    }

    public void setIdReporteGeneralTutoriasAcademicas(int idReporteGeneralTutoriasAcademicas) {
        this.idReporteGeneralTutoriasAcademicas = idReporteGeneralTutoriasAcademicas;
    }
    


}
