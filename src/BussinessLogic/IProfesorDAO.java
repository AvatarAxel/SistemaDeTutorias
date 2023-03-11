/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package BussinessLogic;

import Domain.Profesor;
import java.sql.SQLException;

/**
 *
 * @author Panther
 */
public interface IProfesorDAO {
    
    public Profesor getProfesor(int numeroDePersonal) throws SQLException;
    
}
