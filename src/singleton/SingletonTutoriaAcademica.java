/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package singleton;

import java.sql.Date;

/**
 *
 * @author michikato
 */
public class SingletonTutoriaAcademica {
    private int numeroDeSesion;
    private Date fechaInicio;
    private Date fechaFin;
    private int idTutoriaAcademica;
    
    private static SingletonTutoriaAcademica singletonTutoriaAcademica;    

    public SingletonTutoriaAcademica() {
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
    
    public static SingletonTutoriaAcademica getCurrentTutoriaAcademica() {
        return singletonTutoriaAcademica;
    }
     
}
