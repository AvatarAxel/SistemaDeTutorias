
package Domain;

import java.util.ArrayList;

/**
 *
 * @author Vale
 */
public class Usuario extends Personal{

    private int idRol;
    private String contraseña;

    public Usuario() {
    }   

    public Usuario(int idRol, String contraseña, int numeroDePersonal, String nombre, String apellidoPaterno, String apellidoMaterno, String correoElectronicoInstitucional) {
        super(numeroDePersonal, nombre, apellidoPaterno, apellidoMaterno, correoElectronicoInstitucional);
        this.idRol = idRol;
        this.contraseña = contraseña;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    
}
