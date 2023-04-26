/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BussinessLogic;

import Domain.Estudiante;
import Domain.ReporteDeTutoriaAcademica;
import dataaccess.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import util.AlertManager;

/**
 *
 * @author michikato
 */
public class EstudianteDAO {

    public boolean setEstudianteRegister(Estudiante estudiante, int clave) throws SQLException{
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        
        if(connection!=null){
            String query = ("INSERT INTO estudiantes (`matricula`, `nombre`, `apellidoPaterno`, `apellidoMaterno`, `clave`) VALUES (?, ?, ?, ?, ?);");            
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, estudiante.getMatricula());
            statement.setString(2, estudiante.getNombre());
            statement.setString(3, estudiante.getApellidoPaterno());
            statement.setString(4, estudiante.getApellidoMaterno());
            statement.setInt(5, clave);
            int resultInsert = statement.executeUpdate();            
            if(resultInsert>0){
                result = true;
            }
        }        
        connection.close();
        return result;
    }
    
    public boolean validateExistEstudiante(String matricula) throws SQLException{
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        
        if(connection!=null){
            String query = ("SELECT COUNT(*) FROM estudiantes WHERE matricula = ?");            
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, matricula); 
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int resultQuery = resultSet.getInt(1);
            if(resultQuery > 0){
                result = true;
            }
        }        
        connection.close();
        return result;
    }
    
    public ArrayList<Estudiante> obtenerEstudiantesPorTutorAcademico(int numeroPersonal) throws SQLException {
        ArrayList<Estudiante> estudiantes = new ArrayList<>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();        
        if (connection != null) {
            String consulta = "SELECT * FROM estudiantes WHERE numeroDePersonal = ?";
            PreparedStatement configurarConsulta = connection.prepareStatement(consulta);
            configurarConsulta.setInt(1, numeroPersonal);
            ResultSet resultado = configurarConsulta.executeQuery();
            while (resultado.next()) {
                Estudiante estudiante = new Estudiante();
                estudiante.setMatricula(resultado.getString("matricula"));
                estudiante.setNombre(resultado.getString("nombre"));
                estudiante.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                estudiante.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                estudiantes.add(estudiante);
            }
            connection.close();
        }
        return estudiantes;
    }
    
    public ArrayList<Estudiante> obtenerEstudiantesPorReporteTutoriaAcademica(int idReporteTutoria) throws SQLException{
        ArrayList<Estudiante> estudiantes = new ArrayList<>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection(); 
        if (connection != null) {
            String query = "SELECT e.matricula, e.nombre, e.apellidoPaterno, e.apellidoMaterno, la.esAsistente, la.enRiesgo FROM estudiantes e\n" +
            "INNER JOIN lista_de_asistencias la ON e.matricula = la.matricula\n" +
            "WHERE la.idReporteTutoria = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idReporteTutoria);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet != null){
                while (resultSet.next()) {
                   Estudiante estudiante = new Estudiante();
                   estudiante.setMatricula(resultSet.getString("matricula"));
                   estudiante.setNombre(resultSet.getString("nombre"));
                   estudiante.setApellidoPaterno(resultSet.getString("apellidoPaterno"));
                   estudiante.setApellidoMaterno(resultSet.getString("apellidoMaterno"));
                   estudiante.setEsAsistente(resultSet.getBoolean("esAsistente"));
                   estudiante.setEnRiesgo(resultSet.getBoolean("enRiesgo"));
                   if (estudiante.esAsistente()) {
                       CheckBox checkBox = new CheckBox();
                       checkBox.setSelected(true);
                       estudiante.setCheckBoxEsAsistente(checkBox);
                   }
                   if (estudiante.enRiesgo()) {
                       CheckBox checkBox = new CheckBox();
                       checkBox.setSelected(true);
                       estudiante.setCheckBoxEnRiesgo(checkBox);
                   }
                   estudiantes.add(estudiante);
               }
               if(estudiantes.size() == 0){
                    estudiantes = null;               
               }
            }else{
                estudiantes = null;
            }

            connection.close();        
        } else {
            estudiantes = null;            
        }
        return estudiantes;    
    }
    
    public boolean assignToReporteDeTutoriaAcademica(Estudiante estudiante, int idReporteTutoria) throws SQLException{
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if(connection!=null){
            String queryGet = "INSERT INTO lista_de_asistencias\n"
                        + "(esAsistente, enRiesgo, idReporteTutoria, matricula)\n"
                        + "VALUES (?, ?, ?, ?)";
            PreparedStatement statementSet = connection.prepareStatement(queryGet);
            statementSet.setInt(1, estudiante.esAsistente() ? 1 : 0);
            statementSet.setInt(2, estudiante.enRiesgo() ? 1 : 0);
            statementSet.setInt(3, idReporteTutoria);            
            statementSet.setString(4, estudiante.getMatricula());
            int resultInsert = statementSet.executeUpdate();
            if(resultInsert>0){
                result = true;
            }            
        }        
        connection.close();
        return result;
    }    
    
    public boolean updateAttendanceList(Estudiante estudiante, int idReporteTutoria)throws SQLException{
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if(connection!=null){
            String queryGet = "INSERT INTO lista_de_asistencias\n"
                        + "(esAsistente, enRiesgo, idReporteTutoria, matricula)\n"
                        + "VALUES (?, ?, ?, ?)";
            
            String querySet = "UPDATE lista_de_asistencias\n"
                        + "SET esAsistente = ? , enRiesgo =? " + "WHERE idReporteTutoria = ? AND matricula= ?";             
            PreparedStatement statementSet = connection.prepareStatement(querySet);
            statementSet.setInt(1, estudiante.esAsistente() ? 1 : 0);
            statementSet.setInt(2, estudiante.enRiesgo() ? 1 : 0);
            statementSet.setInt(3, idReporteTutoria);
            statementSet.setString(4, estudiante.getMatricula());
            int resultInsert = statementSet.executeUpdate();
            if(resultInsert>0){
                result = true;
            }            
        }        
        connection.close();
        return result;    
    }
    
    public int getAllEstudiantesWithTutor(int clave) throws SQLException {
        int allEstudiantes = 0;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if (connection != null) {
            String query = ("SELECT COUNT(*) AS TotalEstudiantes\n"
                    + "FROM estudiantes\n"
                    + "WHERE clave = ? AND numeroDePersonal IS NOT NULL;");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, clave);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            allEstudiantes = resultSet.getInt(1);
        }
        connection.close();
        return allEstudiantes;
    }
}
