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
            String query = ("SELECT * FROM programas_educativos WHERE activo = 1;");
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
    
    
    public int validateToEliminarProgramaEducativo(ProgramaEducativo programaEducativo)throws SQLException{
        int ocurrencias = -1;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if(connection!=null){
            String querySet = "SELECT COUNT(*) AS num_ocurrencias\n" +
                "FROM roles_usuarios_programa_educativo\n" +
                "WHERE clave = ?;";                 
            PreparedStatement statementSet = connection.prepareStatement(querySet);
            statementSet.setString(1, programaEducativo.getClave());                   
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
    
    public int validateToBorrarProgramaEducativo(ProgramaEducativo programaEducativo)throws SQLException{
        int ocurrencias = -1;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if(connection!=null){
            String querySet = "SELECT COUNT(*) AS num_ocurrencias\n" +
                "FROM programas_educativos pe\n" +
                "LEFT JOIN roles_usuarios_programa_educativo rupe ON pe.clave = rupe.clave\n" +
                "LEFT JOIN reportes_de_tutorias_academicas rta ON pe.clave = rta.clave\n" +
                "LEFT JOIN estudiantes e ON pe.clave = e.clave\n" +
                "LEFT JOIN experiencias_periodos_profesores epp ON pe.clave = epp.clave\n" +
                "WHERE pe.clave = ?;";             
            PreparedStatement statementSet = connection.prepareStatement(querySet);
            statementSet.setString(1, programaEducativo.getClave());                 
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
    
    public boolean eraseProgramaEducativo(ProgramaEducativo programaEducativo) throws SQLException{
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();        
        if(connection!=null){
            String subQuery = ("DELETE FROM `sistema_tutorias`.`roles_usuarios_programa_educativo` WHERE `clave` = ? AND `idRol` = 2;");                        
            String query = ("DELETE FROM `sistema_tutorias`.`programas_educativos` WHERE (`clave` = ?);");
            PreparedStatement subStatement = connection.prepareStatement(subQuery);
            subStatement.setString(1, programaEducativo.getClave());              
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, programaEducativo.getClave());
            int resultSubInsert = subStatement.executeUpdate(); 
            if (resultSubInsert > 0) {
                int resultInsert = statement.executeUpdate();
                if (resultInsert > 0) {
                    result = true;
                }
            }            
        }        
        connection.close();
        return result;
    } 
    
    public boolean deleteProgramaEducativo(ProgramaEducativo programaEducativo) throws SQLException{
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if(connection!=null){
            String subQuery = ("DELETE FROM `sistema_tutorias`.`roles_usuarios_programa_educativo` WHERE (`clave` = ?);");                        
            String query = ("UPDATE `sistema_tutorias`.`programas_educativos` SET activo = 0 "+ "WHERE clave = ?");
            PreparedStatement subStatement = connection.prepareStatement(subQuery);
            subStatement.setString(1, programaEducativo.getClave());              
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, programaEducativo.getClave());
            int resultSubInsert = subStatement.executeUpdate(); 
            if (resultSubInsert > 0) {
                int resultInsert = statement.executeUpdate();
                if (resultInsert > 0) {
                    result = true;
                }
            }            
        }        
        connection.close();
        return result;
    }     
    
    public int validateRegistrarProgramaEducativo()throws SQLException{
        int ocurrencias = -1;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if(connection!=null){
            String querySet = "SELECT COUNT(*) AS num_ocurrencias\n" +
                "FROM profesores p\n" +
                "WHERE p.numeroDePersonal NOT IN (\n" +
                "    SELECT r.numeroDePersonal\n" +
                "    FROM roles_usuarios_programa_educativo r\n" +
                "    WHERE r.idRol = 2\n" +
                ");";             
            PreparedStatement statementSet = connection.prepareStatement(querySet);      
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
    
}
