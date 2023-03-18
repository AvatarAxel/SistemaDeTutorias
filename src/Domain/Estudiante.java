/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

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

    public Estudiante() {
    }

    public Estudiante(String nombre, String apellidoPaterno, String apellidoMaterno, String correoElectronicoInstitucional, String Matricula) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correoElectronicoInstitucional = correoElectronicoInstitucional;
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
}
