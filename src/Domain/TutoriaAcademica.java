/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import java.sql.Date;

/**
 *
 * @author michikato
 */
public class TutoriaAcademica {
    private int numeroDeSesion;
    private java.sql.Date fechaInicio;
    private java.sql.Date fechaFin;
    private java.sql.Date fechaCierreEntregaReporte;
    private int idTutoriaAcademica;
    private PeriodoEscolar periodoEscolar;

    public TutoriaAcademica() {
    }

    public TutoriaAcademica(int numeroDeSesion, Date fechaInicio, Date fechaFin, int idTutoriaAcademica, PeriodoEscolar periodoEscolar) {
        this.numeroDeSesion = numeroDeSesion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idTutoriaAcademica = idTutoriaAcademica;
        this.periodoEscolar = periodoEscolar;
    }

    public int getNumeroDeSesion() {
        return numeroDeSesion;
    }

    public void setNumeroDeSesion(int numeroDeSesion) {
        this.numeroDeSesion = numeroDeSesion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaCierreEntregaReporte() {
        return fechaCierreEntregaReporte;
    }

    public void setFechaCierreEntregaReporte(Date fechaCierreEntregaReporte) {
        this.fechaCierreEntregaReporte = fechaCierreEntregaReporte;
    }
    
    public int getIdTutoriaAcademica() {
        return idTutoriaAcademica;
    }

    public void setIdTutoriaAcademica(int idTutoriaAcademica) {
        this.idTutoriaAcademica = idTutoriaAcademica;
    }

    public PeriodoEscolar getPeriodoEscolar() {
        return periodoEscolar;
    }

    public void setPeriodoEscolar(PeriodoEscolar periodoEscolar) {
        this.periodoEscolar = periodoEscolar;
    }
    
    public String getFechasPeriodoEscolar(){
        String fechasPeriodoEscolar = util.DateLatinAmerica.DateConvertToPeriod(periodoEscolar.getFechaInicio())
                + " - " + util.DateLatinAmerica.DateConvertToPeriod(periodoEscolar.getFechaFin());
        return fechasPeriodoEscolar;
    }

}
