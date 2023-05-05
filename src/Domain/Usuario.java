
package Domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vale
 */
public class Usuario extends Personal{

    private int idRol;
    private String contraseña;
    private ProgramaEducativo programaEducativo;

    public Usuario() {
    }   

    public Usuario(int idRol, String contraseña, ProgramaEducativo programaEducativo, int numeroDePersonal, String nombre, String apellidoPaterno, String apellidoMaterno, String correoElectronicoInstitucional) {
        super(numeroDePersonal, nombre, apellidoPaterno, apellidoMaterno, correoElectronicoInstitucional);
        this.idRol = idRol;
        this.contraseña = contraseña;
        this.programaEducativo = programaEducativo;
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

    public ProgramaEducativo getProgramaEducativo() {
        return programaEducativo;
    }

    public void setProgramaEducativo(ProgramaEducativo programaEducativo) {
        this.programaEducativo = programaEducativo;
    }

    @Override
    public String toString() {
        return this.getNombre()+" "+this.getApellidoPaterno()+" "+this.getApellidoMaterno();
    }    
}
