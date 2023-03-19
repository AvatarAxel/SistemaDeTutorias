/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BussinessLogic;

import Domain.Estudiante;
import dataaccess.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.control.Alert;

/**
 *
 * @author Propietario
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
    
    public static ArrayList<Estudiante> obtenerEstudiantesPorTutorAcademico(int numeroPersonal) throws SQLException {
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
}