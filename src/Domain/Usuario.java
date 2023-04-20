
package Domain;

import java.util.ArrayList;

/**
 *
 * @author Vale
 */
public class Usuario {
    
    int numeroPersonal;
    private String nombre; 
    private String apellidoPaterno;
    private String apellidoMaterno; 
    private String programaEducativo;
    private ArrayList<Rol> roles;
    private String correo;

    public Usuario() {
    }

    public Usuario(int numeroPersonal, String nombre, String apellidoPaterno, String apellidoMaterno, String programaEducativo, ArrayList<Rol> roles, String correo) {
        this.numeroPersonal = numeroPersonal;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.programaEducativo = programaEducativo;
        this.roles = roles;
        this.correo = correo;
    }

    public int getNumeroPersonal() {
        return numeroPersonal;
    }

    public void setNumeroPersonal(int numeroPersonal) {
        this.numeroPersonal = numeroPersonal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getProgramaEducativo() {
        return programaEducativo;
    }

    public void setProgramaEducativo(String programaEducativo) {
        this.programaEducativo = programaEducativo;
    }

    public ArrayList<Rol> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<Rol> roles) {
        this.roles = roles;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
 
    @Override
    public String toString() {
        return nombre + " " + apellidoPaterno + " " + apellidoMaterno;
    }    
}
