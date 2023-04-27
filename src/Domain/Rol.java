/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import java.util.Objects;

/**
 *
 * @author panther
 */
public class Rol {
    private int idRol;
    private String rolName;
    private ProgramaEducativo programaEducativo;

    public Rol() {
    }
    
    public Rol(int idRol, String rolName) {
        this.idRol = idRol;
        this.rolName = rolName;
    }

    public Rol(int idRol, String rolName, ProgramaEducativo programaEducativo) {
        this.idRol = idRol;
        this.rolName = rolName;
        this.programaEducativo = programaEducativo;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getRolName() {
        return rolName;
    }

    public void setRolName(String rolName) {
        this.rolName = rolName;
    }

    public ProgramaEducativo getProgramaEducativo() {
        return programaEducativo;
    }

    public void setProgramaEducativo(ProgramaEducativo programaEducativo) {
        this.programaEducativo = programaEducativo;
    }

    @Override
    public String toString() {
        return rolName;
    }

}
