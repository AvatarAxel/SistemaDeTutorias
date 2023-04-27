/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package BussinessLogic;

import Domain.Rol;
import Domain.Usuario;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Valea
 */
public interface IUserDAO {
    
    public Usuario getUser(String correo, String contrasena) throws SQLException;
    
    public ArrayList<Rol> getUserRoles(int numeroDePersonal) throws SQLException;
    
}
