/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import java.util.List;

/**
 *
 * @author Panther
 */
public class JefeDeCarrera extends Usuario{

    public JefeDeCarrera() {
    }

    public JefeDeCarrera(int idRol, String contraseña, ProgramaEducativo programaEducativo, int numeroDePersonal, String nombre, String apellidoPaterno, String apellidoMaterno, String correoElectronicoInstitucional, List<Rol> listRoles) {
        super(idRol, contraseña, programaEducativo, numeroDePersonal, nombre, apellidoPaterno, apellidoMaterno, correoElectronicoInstitucional, listRoles);
    }

}
