/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import java.util.Objects;

/**
 *
 * @author Panther
 */
public class ProgramaEducativo {
    private String clave;
    private String nombre;

    public ProgramaEducativo() {
    }

    public ProgramaEducativo(String clave, String nombre) {
        this.clave = clave;
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        boolean isEquals=false;
        if (object == this) {
            isEquals=true;
        }
        if (object!= null && object instanceof ProgramaEducativo) {
            ProgramaEducativo other = (ProgramaEducativo) object;
            isEquals=this.getClave().equals(other.getClave()) &&
                    this.getNombre().equals(other.getNombre());
        }
        return isEquals;
    }

    
    @Override
    public String toString() {
        return nombre;
    }
    
}
