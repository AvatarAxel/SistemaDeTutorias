/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

/**
 *
 * @author Panther
 */
public class Coordinador extends Usuario{

    public Coordinador() {
    }

    public Coordinador(int idRol, String contraseña, int numeroDePersonal, String nombre, String apellidoPaterno, String apellidoMaterno, String correoElectronicoInstitucional) {
        super(idRol, contraseña, numeroDePersonal, nombre, apellidoPaterno, apellidoMaterno, correoElectronicoInstitucional);
    }
    
}
