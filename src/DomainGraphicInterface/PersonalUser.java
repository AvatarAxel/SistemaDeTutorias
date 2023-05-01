/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DomainGraphicInterface;

import Domain.Personal;
import Domain.Rol;
import java.util.ArrayList;

/**
 *
 * @author michikato
 */
public class PersonalUser {
    private Personal personal;    
    private ArrayList<Rol> roles;

    public PersonalUser() {
        this.roles = new ArrayList<>();        
    }
        

    public PersonalUser(Personal personal, ArrayList<Rol> rol) {
        this.personal = personal;
        this.roles = rol;
    }
    
    public int getNumeroDePersonal() {
        return this.personal.getNumeroDePersonal();
    }

    public void setNumeroDePersonal(int numeroDePersonal) {
        this.personal.setNumeroDePersonal(numeroDePersonal);
    }

    public String getNombre() {
        return personal.getNombre();
    }

    public void setNombre(String nombre) {
        this.personal.setNombre(nombre);
    }

    public String getApellidoPaterno() {
        return personal.getApellidoPaterno();
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.personal.setApellidoPaterno(apellidoPaterno);
    }

    public String getApellidoMaterno() {
        return personal.getApellidoMaterno();
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.personal.setApellidoMaterno(apellidoMaterno);
    }

    public String getCorreoElectronicoInstitucional() {
        return personal.getCorreoElectronicoInstitucional();
    }

    public void setCorreoElectronicoInstitucional(String correoElectronicoInstitucional) {
        this.personal.setCorreoElectronicoInstitucional(correoElectronicoInstitucional);
    }

    public Personal getPersonal() {
        return personal;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }

    public ArrayList<Rol> getRoles() {
        return roles;
    }

    public void setRol(ArrayList<Rol> roles) {
        this.roles = roles;
    }
        
}
