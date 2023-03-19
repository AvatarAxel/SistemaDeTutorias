/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package BussinessLogic;

import Domain.ProblematicaAcademica;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Panther
 */
public interface IProblematicaAcademicaDAO {
    
    public ArrayList<ProblematicaAcademica> getProblematicasSinSolucion() throws SQLException;
    
    public ArrayList<ProblematicaAcademica> getProblematicas() throws SQLException;
}
