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
    private Date fechaInicio;
    private Date fechaFin;
    private int idTutoriaAcademica;

    public TutoriaAcademica() {
    }

    public TutoriaAcademica(int numeroDeSesion, Date fechaInicio, Date fechaFin, int idTutoriaAcademica) {
        this.numeroDeSesion = numeroDeSesion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idTutoriaAcademica = idTutoriaAcademica;
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
    
    public int getIdTutoriaAcademica() {
        return idTutoriaAcademica;
    }

    public void setIdTutoriaAcademica(int idTutoriaAcademica) {
        this.idTutoriaAcademica = idTutoriaAcademica;
    }    
        
}
