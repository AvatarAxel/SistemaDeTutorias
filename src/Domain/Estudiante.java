/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import javafx.scene.control.CheckBox;

/**
 *
 * @author michikato
 */
public class Estudiante {
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correoElectronicoInstitucional;
    private String Matricula;
    private boolean esAsistente;
    private boolean enRiesgo;
    private CheckBox checkBoxEsAsistente;
    private CheckBox checkBoxEnRiesgo;
    private CheckBox checkBoxEnSeleccion;
    private String tutorName;

   


    public Estudiante() {
        this.checkBoxEsAsistente = new CheckBox();
        this.checkBoxEnRiesgo = new CheckBox();
    }

    public Estudiante(String nombre, String apellidoPaterno, String apellidoMaterno, String Matricula) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.Matricula = Matricula;
    }
        
    public String getNombre() {
        return nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public String getCorreoElectronicoInstitucional() {
        return correoElectronicoInstitucional;
    }

    public String getMatricula() {
        return Matricula;
    }
    public String getNombreCompleto(){
    return this.nombre +" " +this.apellidoPaterno+" "+this.apellidoMaterno;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public void setCorreoElectronicoInstitucional(String correoElectronicoInstitucional) {
        this.correoElectronicoInstitucional = correoElectronicoInstitucional;
    }

    public void setMatricula(String Matricula) {
        this.Matricula = Matricula;
    }
    
        public boolean esAsistente() {
        return esAsistente;
    }

    public void setEsAsistente(boolean esAsistente) {
        this.esAsistente = esAsistente;
    }

    public boolean enRiesgo() {
        return enRiesgo;
    }

    public void setEnRiesgo(boolean enRiesgo) {
        this.enRiesgo = enRiesgo;
    }

    public CheckBox getCheckBoxEsAsistente() {
        return checkBoxEsAsistente;
    }

    public void setCheckBoxEsAsistente(CheckBox checkBoxEsAsistente) {
        this.checkBoxEsAsistente = checkBoxEsAsistente;
    }

    public CheckBox getCheckBoxEnRiesgo() {
        return checkBoxEnRiesgo;
    }

    public void setCheckBoxEnRiesgo(CheckBox checkBoxEnRiesgo) {
        this.checkBoxEnRiesgo = checkBoxEnRiesgo;
    }

    public CheckBox getCheckBoxEnSeleccion() {
        return checkBoxEnSeleccion;
    }

    public void setCheckBoxEnSeleccion(CheckBox checkBoxEnSelection) {
        this.checkBoxEnSeleccion = checkBoxEnSelection;
    }
     public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }
    
}
