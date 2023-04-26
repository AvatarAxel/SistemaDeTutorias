/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

/**
 *
 * @author michikato
 */
public class TutorAcademico {
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correoElectronicoInstitucional;
    private int numeroDePersonal;
    private String contraseña;
    private int claveProgramaEducativo;
    private int idRol;
    

    public TutorAcademico() {
    }

    public TutorAcademico(String nombre, String apellidoPaterno, String apellidoMaterno, String correoElectronicoInstitucional, int numeroDePersonal, String contraseña) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correoElectronicoInstitucional = correoElectronicoInstitucional;
        this.numeroDePersonal = numeroDePersonal;
        this.contraseña = contraseña;
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

    public int getNumeroDePersonal() {
        return numeroDePersonal;
    }
    
    public String getContraseña() {
        return contraseña;
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

    public void setNumeroDePersonal(int numeroDePersonal) {
        this.numeroDePersonal = numeroDePersonal;
    }
    
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public int getClaveProgramaEducativo() {
        return claveProgramaEducativo;
    }

    public void setClaveProgramaEducativo(int claveProgramaEducativo) {
        this.claveProgramaEducativo = claveProgramaEducativo;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }
    
    
    
    @Override
    public String toString() {
        return nombre+" "+apellidoPaterno+" "+apellidoMaterno;
    }
    
}
