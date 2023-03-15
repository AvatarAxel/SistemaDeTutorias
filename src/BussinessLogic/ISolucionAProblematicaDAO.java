/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package BussinessLogic;

import java.sql.SQLException;

/**
 *
 * @author Panther
 */
public interface ISolucionAProblematicaDAO {
    
    public int insertSolucionAProblematica(int idProblematica, String solucion) throws SQLException;
    
    public int updateSolucionAProblematica(int idProblematica, String solucion) throws SQLException;
    
}
