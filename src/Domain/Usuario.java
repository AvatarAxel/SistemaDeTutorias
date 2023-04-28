
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
    private List<Rol> listRoles;

    public Usuario() {
    }   

    public Usuario(int idRol, String contraseña, ProgramaEducativo programaEducativo, int numeroDePersonal, String nombre, String apellidoPaterno, String apellidoMaterno, String correoElectronicoInstitucional, List<Rol> listRoles) {
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

    public List<Rol> getListRoles() {
        return listRoles;
    }

    public void setListRoles(List<Rol> listRoles) {
        this.listRoles = listRoles;
    }
    
}
