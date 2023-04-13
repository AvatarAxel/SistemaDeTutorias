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
    
    public ArrayList<ProblematicaAcademica> getProblematicasByPrograma(String clave) throws SQLException;
    
    public ArrayList<ProblematicaAcademica> getProblematicasByReporte(int idReporte) throws SQLException;

    public int insertProblematica(ProblematicaAcademica problematica) throws SQLException;
    
    public int updatetProblematica(ProblematicaAcademica problematica) throws SQLException;
    
    public int deleteProblematica(int idProblematicaAcademica) throws SQLException;



    
}
