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
import javafx.scene.control.CheckBox;

/**
 *
 * @author michikato
 */
public class EstudianteDAO {
    
    public boolean setEstudianteRegister(Estudiante estudiante, int clave) throws SQLException {
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        
        if (connection != null) {
            String query = ("INSERT INTO estudiantes (`matricula`, `nombre`, `apellidoPaterno`, `apellidoMaterno`, `clave`) VALUES (?, ?, ?, ?, ?);");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, estudiante.getMatricula());
            statement.setString(2, estudiante.getNombre());
            statement.setString(3, estudiante.getApellidoPaterno());
            statement.setString(4, estudiante.getApellidoMaterno());
            statement.setInt(5, clave);
            int resultInsert = statement.executeUpdate();
            if (resultInsert > 0) {
                result = true;
            }
        }
        connection.close();
        return result;
    }
    
    public boolean validateExistEstudiante(String matricula) throws SQLException {
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        
        if (connection != null) {
            String query = ("SELECT COUNT(*) FROM estudiantes WHERE matricula = ?");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, matricula);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int resultQuery = resultSet.getInt(1);
            if (resultQuery > 0) {
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
    
    public ArrayList<Estudiante> getEstudiantesByPrograma(String clave) throws SQLException {
        
        ArrayList<Estudiante> estudiantes = new ArrayList<Estudiante>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        
        if (connection != null) {
            String query = ("select e.*, concat(u.nombre,' ',u.apellidoPaterno, ' ',u.apellidoMaterno) as nombreTutor  from estudiantes e \n"
                    + "inner join programas_educativos pe on e.clave=pe.clave\n"
                    + "left join usuarios u on u.numeroDePersonal =e.numeroDePersonal where pe.clave=?;");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, clave);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                String nombre;
                String apellidoMaterno;
                String apellidoPaterno;
                String matricula;
                String nombreTutor;
                CheckBox checkbox;
                
                do {
                    nombre = resultSet.getString("nombre");
                    apellidoMaterno = resultSet.getString("apellidoMaterno");
                    apellidoPaterno = resultSet.getString("apellidoPaterno");
                    matricula = resultSet.getString("matricula");
                    nombreTutor = resultSet.getString("nombreTutor");
                    checkbox = new CheckBox();
                    Estudiante estudiante = new Estudiante();
                    estudiante.setNombre(nombre);
                    estudiante.setApellidoPaterno(apellidoPaterno);
                    estudiante.setApellidoMaterno(apellidoMaterno);
                    estudiante.setMatricula(matricula);
                    estudiante.setCheckBoxEnSeleccion(checkbox);
                    estudiante.setTutorName(nombreTutor);
                    estudiantes.add(estudiante);
                    
                } while (resultSet.next());
            }
        }
        connection.close();
        
        return estudiantes;
    }
    
    public int updateAsignacion(String matricula, int numeroDePersonal) throws SQLException {
        int result = 0;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        
        if (connection != null) {
            String query = ("UPDATE estudiantes set numeroDePersonal=? where matricula=?");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, numeroDePersonal);
            statement.setString(2, matricula);
            int resultSet = statement.executeUpdate();
            result = statement.executeUpdate();
            
        }
        connection.close();
        return result;
        
    }
    
}
