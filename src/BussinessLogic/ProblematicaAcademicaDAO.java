
package BussinessLogic;

import Domain.ProblematicaAcademica;
import Domain.SolucionAProblematica;
import dataaccess.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Panther
 */
public class ProblematicaAcademicaDAO implements IProblematicaAcademicaDAO {

    @Override
    public ArrayList<ProblematicaAcademica> getProblematicasSinSolucion() throws SQLException {
        ArrayList<ProblematicaAcademica> listProblematicasAcademicas = new ArrayList<>();
        
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        
        
        if(connection != null){
            String consulta ="SELECT * FROM problematicas_academicas\n" +
                "WHERE problematicas_academicas.idProblematica NOT IN \n" +
                "  (SELECT soluciones_a_problematicas_academicas.idProblematica\n" +
                "  FROM soluciones_a_problematicas_academicas)";
            PreparedStatement statement = connection.prepareStatement(consulta);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                ProblematicaAcademica problematicaAcademicaTemp = new ProblematicaAcademica();
                problematicaAcademicaTemp.setIdProblematica(resultSet.getInt("idProblematica"));
                problematicaAcademicaTemp.setNumeroDeEstudiantesAfectados(resultSet.getInt("numeroDeEstudiantesAfectados"));
                problematicaAcademicaTemp.setDescripcion(resultSet.getString("descripcion"));
                problematicaAcademicaTemp.setSolucion(new SolucionAProblematica(0, ""));
                listProblematicasAcademicas.add(problematicaAcademicaTemp);
            }
            connection.close();
        }
        
        return listProblematicasAcademicas;
    }
    
    @Override
    public ArrayList<ProblematicaAcademica> getProblematicas() throws SQLException {
        ArrayList<ProblematicaAcademica> listProblematicasAcademicas = new ArrayList<>();
        
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        
        
        if(connection != null){
            String consulta ="SELECT problematicas_academicas.idProblematica,\n" +
                "problematicas_academicas.descripcion,\n" +
                "titulo,numeroDeEstudiantesAfectados,\n" +
                "idReporteTutoria,\n" +
                "soluciones_a_problematicas_academicas.idSolucion,\n" +
                "soluciones_a_problematicas_academicas.descripcion AS descripcion_solucion\n" +
                "FROM problematicas_academicas INNER JOIN soluciones_a_problematicas_academicas\n" +
                "ON problematicas_academicas.idProblematica = soluciones_a_problematicas_academicas.idProblematica";
            PreparedStatement statement = connection.prepareStatement(consulta);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                ProblematicaAcademica problematicaAcademicaTemp = new ProblematicaAcademica();
                problematicaAcademicaTemp.setIdProblematica(resultSet.getInt("idProblematica"));
                problematicaAcademicaTemp.setNumeroDeEstudiantesAfectados(resultSet.getInt("numeroDeEstudiantesAfectados"));
                problematicaAcademicaTemp.setDescripcion(resultSet.getString("descripcion"));
                problematicaAcademicaTemp.setSolucion(new SolucionAProblematica(resultSet.getInt("idSolucion"), resultSet.getString("descripcion_solucion")));
                listProblematicasAcademicas.add(problematicaAcademicaTemp);
            }
            connection.close();
        }
        
        return listProblematicasAcademicas;
    }

    @Override
    public ArrayList<ProblematicaAcademica> getProblematicasByPrograma(int idPrograma) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<ProblematicaAcademica> getProblematicasByReporte(int idReporte) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int insertProblematica(ProblematicaAcademica problematica) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int updatetProblematica(ProblematicaAcademica problematica) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int deleteProblematica(ProblematicaAcademica problematica) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
