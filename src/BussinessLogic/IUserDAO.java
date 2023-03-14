/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package BussinessLogic;

import Domain.Usuario;
import java.sql.SQLException;

/**
 *
 * @author Valea
 */
public interface IUserDAO {
    
    public Usuario getUserDB(String correo, String contrasena) throws SQLException;
    
}
