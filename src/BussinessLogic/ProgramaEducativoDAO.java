/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BussinessLogic;

import Domain.ExperienciaEducativa;
import Domain.ProgramaEducativo;
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
public class ProgramaEducativoDAO implements IProgramaEducativoDAO {

    @Override
    public ArrayList<ProgramaEducativo> getProgramasEducativos() throws SQLException {
        ArrayList<ProgramaEducativo> listProgramasEducativos = new ArrayList<>();
        
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if(connection != null){
            String query = ("SELECT * FROM programas_educativos;");
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                ProgramaEducativo programaEducativoTemp = new ProgramaEducativo();
                programaEducativoTemp.setClave(resultSet.getString("clave"));
                programaEducativoTemp.setNombre(resultSet.getString("nombre"));
                listProgramasEducativos.add(programaEducativoTemp);
            }
            connection.close();
        }
        
        return listProgramasEducativos;
    }
    
    public boolean setProgramaEducativoRegister(ProgramaEducativo programaEducativo) throws SQLException {
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if(connection!=null){
            String query = ("INSERT INTO programas_educativos (`clave`, `nombre`) VALUES (?, ?);");            
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, programaEducativo.getClave());
            statement.setString(2, programaEducativo.getNombre());
            int resultInsert = statement.executeUpdate();            
            if(resultInsert>0){
                result = true;
            }
        }
        connection.close();
        return result;
    }    
    
    public boolean updateProgramaEducativo(ProgramaEducativo programaEducativo)throws SQLException{
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if(connection!=null){
            String querySet = "UPDATE programas_educativos\n"
                        + "SET nombre = ?"+ "WHERE clave = ?";             
            PreparedStatement statementSet = connection.prepareStatement(querySet);
            statementSet.setString(1, programaEducativo.getNombre());
            statementSet.setString(2, programaEducativo.getClave());            
            int resultInsert = statementSet.executeUpdate();
            if(resultInsert>0){
                result = true;
            }            
        }        
        connection.close();
        return result;    
    }    
    
    
    public int validateProgramaEducativo(ProgramaEducativo programaEducativo)throws SQLException{
        int ocurrencias = -1;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if(connection!=null){
            String querySet = "SELECT COUNT(*) AS num_ocurrencias\n" +
            "FROM programas_educativos\n" +
            "WHERE clave = ?\n" +
            "   AND (\n" +
            "      EXISTS (SELECT * FROM estudiantes WHERE clave = ?)\n" +
            "      OR EXISTS (SELECT * FROM experiencias_periodos_profesores WHERE clave = ?)\n" +
            "      OR EXISTS (SELECT * FROM reportes_de_tutorias_academicas WHERE clave = ?)\n" +
            "      OR EXISTS (SELECT * FROM roles_usuarios_programa_educativo WHERE clave = ?)\n" +
            "   );";             
            PreparedStatement statementSet = connection.prepareStatement(querySet);
            statementSet.setString(1, programaEducativo.getClave());            
            statementSet.setString(2, programaEducativo.getClave());    
            statementSet.setString(3, programaEducativo.getClave());            
            statementSet.setString(4, programaEducativo.getClave());      
            statementSet.setString(5, programaEducativo.getClave());      
            ResultSet resultSet = statementSet.executeQuery();
            resultSet.next();
            int resultInsert = resultSet.getInt("num_ocurrencias");
            if(resultInsert>=0){
                ocurrencias = resultInsert;
            }            
        }        
        connection.close();
        return ocurrencias;    
    }     
    
    public boolean deleteProgramaEducativo(ProgramaEducativo programaEducativo) throws SQLException{
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();        
        if(connection!=null){
            String query = ("DELETE FROM `sistema_tutorias`.`programas_educativos` WHERE (`clave` = ?);");            
          PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, programaEducativo.getClave());
            int resultInsert = statement.executeUpdate();
            if (resultInsert > 0) {
                result = true;
            }
        }        
        connection.close();
        return result;
    }    
}
