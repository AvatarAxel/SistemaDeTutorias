
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
            String query = ("INSERT INTO estudiantes (`matricula`, `nombre`, `apellidoPaterno`, `apellidoMaterno`, `clave`, `esInscrito`)\n"
                    + "VALUES (?, ?, ?, ?, ?, ?)\n"
                    + "ON DUPLICATE KEY UPDATE\n"
                    + "    nombre = VALUES(nombre),\n"
                    + "    apellidoPaterno = VALUES(apellidoPaterno),\n"
                    + "    apellidoMaterno = VALUES(apellidoMaterno),\n"
                    + "    clave = VALUES(clave),\n"
                    + "    esInscrito = VALUES(esInscrito);");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, estudiante.getMatricula());
            statement.setString(2, estudiante.getNombre());
            statement.setString(3, estudiante.getApellidoPaterno());
            statement.setString(4, estudiante.getApellidoMaterno());
            statement.setInt(5, clave);
            statement.setInt(6, 1);
            int resultInsert = statement.executeUpdate();            
            if(resultInsert>0){
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
    
    public ArrayList<Estudiante> getEstudiantesPorTutorAcademicoAndProgramaEducativo(int numeroPersonal, int clave) throws SQLException {
        ArrayList<Estudiante> estudiantes = new ArrayList<>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if (connection != null) {
            String consulta = "SELECT * FROM estudiantes WHERE numeroDePersonal = ? AND clave = ?;";
            PreparedStatement configurarConsulta = connection.prepareStatement(consulta);
            configurarConsulta.setInt(1, numeroPersonal);
            configurarConsulta.setInt(2, clave);            
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
            }
            connection.close();        
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

    public ArrayList<Estudiante> getAllEstudiantesInscribedByProgramaEducativo(String clave) throws SQLException {
        ArrayList<Estudiante> listEstudiantes = new ArrayList<>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if (connection != null) {
            String query = "SELECT * FROM estudiantes WHERE esInscrito = 1 and clave = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, clave);
            ResultSet resultQuery = preparedStatement.executeQuery();
            while (resultQuery.next()) {
                Estudiante estudiante = new Estudiante();
                estudiante.setMatricula(resultQuery.getString("matricula"));
                estudiante.setNombre(resultQuery.getString("nombre"));
                estudiante.setApellidoPaterno(resultQuery.getString("apellidoPaterno"));
                estudiante.setApellidoMaterno(resultQuery.getString("apellidoMaterno"));
                listEstudiantes.add(estudiante);
            }
            connection.close();
        }
        return listEstudiantes;
    }
    
    public boolean deleteEstudiante(String matricula) throws SQLException{
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();        
        if(connection!=null){
            String query = ("UPDATE `sistema_tutorias`.`estudiantes` SET `esInscrito` = '0' WHERE (`matricula` = ?);");            
          PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, matricula);
            int resultInsert = statement.executeUpdate();
            if (resultInsert > 0) {
                result = true;
            }
        }        
        connection.close();
        return result;
    }    
    
    public boolean updateEstudiante(Estudiante estudiante) throws SQLException{
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();        
        if(connection!=null){
            String query = ("UPDATE `sistema_tutorias`.`estudiantes` SET `nombre` = ?, `apellidoPaterno` = ?, `apellidoMaterno` = ? WHERE (`matricula` = ?);");            
          PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, estudiante.getNombre());
            statement.setString(2, estudiante.getApellidoPaterno());
            statement.setString(3, estudiante.getApellidoMaterno());
            statement.setString(4, estudiante.getMatricula());
            int resultInsert = statement.executeUpdate();
            if (resultInsert > 0) {
                result = true;
            }
        }        
        connection.close();
        return result;
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

    public boolean validateExistEstudianteNoInscrito(String matricula) throws SQLException {
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if (connection != null) {
            String query = ("SELECT COUNT(*) FROM estudiantes WHERE matricula = ? AND esInscrito = 0;");
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
    
}
