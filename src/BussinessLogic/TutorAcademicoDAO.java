/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BussinessLogic;

import Domain.Profesor;
import Domain.TutorAcademico;
import Domain.Usuario;
import Domain.TutoriaAcademica;
import dataaccess.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author michikato
 */
public class TutorAcademicoDAO {

    public boolean setTutorRegister(TutorAcademico tutorAcademico) throws SQLException {
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if (connection != null) {
            String query = ("INSERT INTO usuarios (`numeroDePersonal`, `nombre`, `apellidoPaterno`, `apellidoMaterno`, `contrasenia`, `correoElectronicoInstitucional`) VALUES (?, ?, ?, ?, ?, ?);");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, tutorAcademico.getNumeroDePersonal());
            statement.setString(2, tutorAcademico.getNombre());
            statement.setString(3, tutorAcademico.getApellidoPaterno());
            statement.setString(4, tutorAcademico.getApellidoMaterno());
            statement.setString(5, tutorAcademico.getContraseña());
            statement.setString(6, tutorAcademico.getCorreoElectronicoInstitucional());
            int resultInsert = statement.executeUpdate();
            if (resultInsert > 0) {
                result = true;
            }
        }
        connection.close();
        return result;
    }
/*
    public static ArrayList<Usuario> getTutoresWithReportesbyProgramaEducativo(int clave) throws SQLException {
    ArrayList<Usuario> tutores = new ArrayList<>();
    DataBaseConnection dataBaseConnection = new DataBaseConnection();
    Connection connection = dataBaseConnection.getConnection();        
        if (connection != null) {
            String consulta = "SELECT u.nombre, u.apellidoPaterno, u.apellidoMaterno, u.numeroDePersonal FROM usuarios u\n" +
            "INNER JOIN programas_educativos_usuarios pu ON u.numeroDePersonal = pu.numeroDePersonal\n" +
            "INNER JOIN programas_educativos p ON p.clave = pu.clave\n" +
            "INNER JOIN roles_usuarios ru ON u.numeroDePersonal = ru.numeroDePersonal\n" +
            "INNER JOIN roles r ON r.idRol = ru.idRol\n" +
            "WHERE r.idRol= 3 AND p.clave =?";
            PreparedStatement configurarConsulta = connection.prepareStatement(consulta);
            configurarConsulta.setInt(1, clave);
            ResultSet resultado = configurarConsulta.executeQuery();
            while (resultado.next()) {
                Usuario usuario = new Usuario();
                usuario.setNombre(resultado.getString("nombre"));
                usuario.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                usuario.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                usuario.setNumeroPersonal(resultado.getInt("numeroDePersonal"));
                tutores.add(usuario);
            }
            connection.close();
        }
        return tutores;
    }
    */
    public static ArrayList<Usuario> getTutoresWithReportesbyProgramaEducativo(int clave) throws SQLException {
    ArrayList<Usuario> tutores = new ArrayList<>();
    DataBaseConnection dataBaseConnection = new DataBaseConnection();
    Connection connection = dataBaseConnection.getConnection();        
        if (connection != null) {
            String consulta = "SELECT DISTINCT u.nombre, u.apellidoPaterno, u.apellidoMaterno, u.numeroDePersonal\n" +
                "FROM usuarios u\n" +
                "JOIN roles_usuarios_programa_educativo rupe ON u.numeroDePersonal = rupe.numeroDePersonal\n" +
                "JOIN roles r ON rupe.idRol = r.idRol\n" +
                "JOIN programas_educativos p ON rupe.clave = p.clave\n" +
                "JOIN reportes_de_tutorias_academicas rt ON u.numeroDePersonal = rt.numeroDePersonal\n" +
                "WHERE r.idRol = 3 AND p.clave = ?";
            PreparedStatement configurarConsulta = connection.prepareStatement(consulta);
            configurarConsulta.setInt(1, clave);
            ResultSet resultado = configurarConsulta.executeQuery();
            while (resultado.next()) {
                Usuario usuario = new Usuario();
                usuario.setNombre(resultado.getString("nombre"));
                usuario.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                usuario.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                usuario.setNumeroDePersonal(resultado.getInt("numeroDePersonal"));
                tutores.add(usuario);
            }
            connection.close();
        }
        return tutores;
    } 

    public ArrayList<TutorAcademico> getAllTutores() throws SQLException {

        ArrayList<TutorAcademico> tutores = new ArrayList<TutorAcademico>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if (connection != null) {
            String query = ("select u.numeroDePersonal,u.nombre,u.apellidoPaterno,u.apellidoMaterno ,COUNT(e.matricula) AS numeroestudiantes from usuarios u\n" +
                            "inner join roles_usuarios_programa_educativo ru on ru.numeroDePersonal=u.numeroDePersonal\n" +
                            "inner join roles r on r.idRol=ru.idRol\n" +
                            "left join estudiantes e on e.numeroDePersonal=u.numeroDePersonal\n" +
                            "where r.idrol=3\n" +
                            "group by u.numeroDePersonal;");
           PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int resultQuery = resultSet.getInt(1);
            if (resultSet.next()) {
                String nombre;
                String apellidoMaterno;
                String apellidoPaterno;
                int numeroDePersonal;
                int numeroestudiantes;

                do {
                    nombre = resultSet.getString("nombre");
                    apellidoMaterno = resultSet.getString("apellidoMaterno");
                    apellidoPaterno = resultSet.getString("apellidoPaterno");
                    numeroDePersonal = resultSet.getInt("numeroDePersonal");
                    numeroestudiantes = resultSet.getInt("numeroestudiantes");
                    TutorAcademico tutor = new TutorAcademico();
                    tutor.setNombre(nombre);
                    tutor.setApellidoPaterno(apellidoPaterno);
                    tutor.setApellidoMaterno(apellidoMaterno);
                    tutor.setNumeroDePersonal(numeroDePersonal);
                    tutor.setNumeroEstudiantes(numeroestudiantes);
                    
                    tutores.add(tutor);

                } while (resultSet.next());
            }
        }
        connection.close();

        return tutores;
    }
    
}
