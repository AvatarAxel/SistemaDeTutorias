package BussinessLogic;

import Domain.ExperienciaEducativa;
import Domain.Profesor;
import dataaccess.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Panther
 */
public class ProfesorDAO implements IProfesorDAO {

    public boolean validateExistProfesor(int numeroPersonal) throws SQLException {
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if (connection != null) {
            String query = ("SELECT COUNT(*) FROM profesores p WHERE p.numeroDePersonal= ?");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, numeroPersonal);
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
  public boolean validateExistCorreoProfesor(String correo) throws SQLException {
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if (connection != null) {
            String query = ("SELECT COUNT(*) FROM profesores p WHERE p.correoElectronicoInstitucional= ?");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, correo);
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
    public boolean setProfesorRegister(Profesor profesor) throws SQLException {
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if (connection != null) {
            String query = ("INSERT INTO `sistema_tutorias`.`profesores` \n"
                    + "(`numeroDePersonal`, `nombre`, `apellidoPaterno`, `apellidoMaterno`, `correoElectronicoInstitucional`,"
                    + " `esRegistrado`, `esUsuario`) "
                    + " VALUES (?, ?, ?, ?, ?, ?,?);");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, profesor.getNumeroDePersonal());
            statement.setString(2, profesor.getNombre());
            statement.setString(3, profesor.getApellidoPaterno());
            statement.setString(4, profesor.getApellidoMaterno());
            statement.setString(5, profesor.getCorreoElectronicoInstitucional());

            statement.setInt(6, 1);
            statement.setInt(7, 0);
            int resultInsert = statement.executeUpdate();
            if (resultInsert > 0) {
                result = true;
            }
        }
        connection.close();
        return result;
    }

    @Override
    public Profesor getProfesor(int numeroDePersonal) throws SQLException {
        Profesor profesor = new Profesor();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if (connection != null) {
            String query = ("SELECT * FROM profesores WHERE numeroDePersonal = ?");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, numeroDePersonal);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                profesor.setNumeroDePersonal(resultSet.getInt("numeroDePersonal"));
                profesor.setNombre(resultSet.getString("nombre"));
                profesor.setApellidoPaterno(resultSet.getString("apellidoPaterno"));
                profesor.setApellidoMaterno(resultSet.getString("apellidoMaterno"));
                profesor.setCorreoElectronicoInstitucional(resultSet.getString("correoElectronicoInstitucional"));
            }
        }

        return profesor;
    }

    public ArrayList<Profesor> getProfesoresNoUser() throws SQLException {
        ArrayList<Profesor> listProfesores = new ArrayList<>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if (connection != null) {
            String query = ("SELECT * FROM profesores WHERE esRegistrado = 1 and esUsuario = 0");
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Profesor profesor = new Profesor();
                profesor.setNumeroDePersonal(resultSet.getInt("numeroDePersonal"));
                profesor.setNombre(resultSet.getString("nombre"));
                profesor.setApellidoPaterno(resultSet.getString("apellidoPaterno"));
                profesor.setApellidoMaterno(resultSet.getString("apellidoMaterno"));
                profesor.setCorreoElectronicoInstitucional(resultSet.getString("correoElectronicoInstitucional"));
                listProfesores.add(profesor);
            }
        }
        connection.close();
        return listProfesores;
    }

    public boolean setTutorUser(int numeroDePersonal) throws SQLException {
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if (connection != null) {
            String query = ("UPDATE `profesores` SET `esUsuario` = '1' WHERE (`numeroDePersonal` = ?);");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, numeroDePersonal);
            int resultInsert = statement.executeUpdate();
            if (resultInsert > 0) {
                result = true;
            }
        }
        connection.close();
        return result;
    }

    public ArrayList<ExperienciaEducativa> consultProfesoresNames(String clave, int idPeriodo) throws SQLException {
        ArrayList<ExperienciaEducativa> profesoresNames = new ArrayList<ExperienciaEducativa>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = "SELECT p.nombre, p.apellidoPaterno, p.apellidoMaterno, ee.nrc, ee.nombre as experiencia, epp.idexperiencia_periodo_profesor \n"
                + "from experiencias_periodos_profesores epp\n"
                + "INNER JOIN profesores p on  p.numeroDePersonal=epp.numeroDePersonal\n"
                + "inner join experiencias_educativas ee on  epp.nrc=ee.nrc\n"
                + "WHERE epp.clave=? AND epp.idPeriodoEscolar=?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, clave);
        statement.setInt(2, idPeriodo);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String nrc;
            String experiencia;
            String profesorName;
            String apellidoMaterno;
            String apellidoPaterno;
            int idexperienciaprofesor = 0;
            do {

                nrc = resultSet.getString("nrc");
                experiencia = resultSet.getString("experiencia");
                profesorName = resultSet.getString("nombre");
                apellidoMaterno = resultSet.getString("apellidoMaterno");
                apellidoPaterno = resultSet.getString("apellidoPaterno");
                idexperienciaprofesor = resultSet.getInt("idexperiencia_periodo_profesor");

                Profesor profesor = new Profesor();
                profesor.setNombre(profesorName);
                profesor.setApellidoPaterno(apellidoPaterno);
                profesor.setApellidoMaterno(apellidoMaterno);
                ExperienciaEducativa experienciaEducativa = new ExperienciaEducativa();
                experienciaEducativa.setProfesorNombre(profesorName + " " +apellidoPaterno + " " + apellidoPaterno);
                experienciaEducativa.setNombre(experiencia);
                experienciaEducativa.setNrc(nrc);
                experienciaEducativa.setIdexperiencia_periodo_profesor(idexperienciaprofesor);
                experienciaEducativa.setProfesor(profesor);
                //  ExperienciaEducativa experienciaProfesor = new ExperienciaEducativa(nrc, profesorName, nombre);
                profesoresNames.add(experienciaEducativa);

            } while (resultSet.next());
        }
        return profesoresNames;
    }

    public Profesor getProfesorNoUser(int numeroDePersonal) throws SQLException {
        Profesor profesor = new Profesor();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if (connection != null) {
            String query = ("SELECT * FROM profesores WHERE numeroDePersonal = ? and esRegistrado = 1 and esUsuario = 0");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, numeroDePersonal);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                profesor.setNombre(resultSet.getString("nombre"));
                profesor.setApellidoPaterno(resultSet.getString("apellidoPaterno"));
                profesor.setApellidoMaterno(resultSet.getString("apellidoMaterno"));
                profesor.setCorreoElectronicoInstitucional(resultSet.getString("correoElectronicoInstitucional"));
                profesor.setNumeroDePersonal(resultSet.getInt("numeroDePersonal"));
            }
        }
        connection.close();
        return profesor;
    }

    public boolean updateProfesor(Profesor profesor) throws SQLException {
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if (connection != null) {
            String query = ("UPDATE `sistema_tutorias`.`profesores` SET `nombre` = ?, `apellidoPaterno` = ?, `apellidoMaterno` = ?, `correoElectronicoInstitucional` = ? WHERE (`numeroDePersonal` = ?);");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, profesor.getNombre());
            statement.setString(2, profesor.getApellidoPaterno());
            statement.setString(3, profesor.getApellidoMaterno());
            statement.setString(4, profesor.getCorreoElectronicoInstitucional());
            statement.setInt(5, profesor.getNumeroDePersonal());
            int resultInsert = statement.executeUpdate();
            if (resultInsert > 0) {
                result = true;
            }
        }
        connection.close();
        return result;
    }

    public boolean deleteProfesor(int numeroDePersonal) throws SQLException {
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if (connection != null) {
            String query = ("UPDATE `sistema_tutorias`.`profesores` SET `esRegistrado` = 0 WHERE (`numeroDePersonal` = ?);");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, numeroDePersonal);
            int resultInsert = statement.executeUpdate();
            if (resultInsert > 0) {
                result = true;
            }
        }
        connection.close();
        return result;
    }

    public ArrayList<Profesor> getAllProfesores() throws SQLException {
        ArrayList<Profesor> listProfesores = new ArrayList<>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if (connection != null) {
            String query = ("SELECT * FROM profesores WHERE esRegistrado = 1");
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Profesor profesor = new Profesor();
                profesor.setNumeroDePersonal(resultSet.getInt("numeroDePersonal"));
                profesor.setNombre(resultSet.getString("nombre"));
                profesor.setApellidoPaterno(resultSet.getString("apellidoPaterno"));
                profesor.setApellidoMaterno(resultSet.getString("apellidoMaterno"));
                profesor.setCorreoElectronicoInstitucional(resultSet.getString("correoElectronicoInstitucional"));
                listProfesores.add(profesor);
            }
        }
        connection.close();
        return listProfesores;
    }    
    
    
    public ArrayList<ArrayList<Profesor>> getRegistradosYNoRegistrados() throws SQLException {
        ArrayList<ArrayList<Profesor>> profesores =new ArrayList<>();
        ArrayList<Profesor> profesoresRegistrados = new ArrayList<>();
        ArrayList<Profesor> profesoresNoRegistrados = new ArrayList<>();        
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if (connection != null) {
            String consulta = "SELECT p.numeroDePersonal, p.nombre, p.apellidoPaterno, p.apellidoMaterno, p.esUsuario, p.correoElectronicoInstitucional\n" +
            "FROM profesores p\n" +
            "WHERE p.numeroDePersonal NOT IN (\n" +
            "    SELECT r.numeroDePersonal\n" +
            "    FROM roles_usuarios_programa_educativo r\n" +
            "    WHERE r.idRol = 2)";
            PreparedStatement configurarConsulta = connection.prepareStatement(consulta);
            ResultSet resultado = configurarConsulta.executeQuery();
            while (resultado.next()) {
                Profesor profesor = new Profesor();
                profesor.setNumeroDePersonal(resultado.getInt("numeroDePersonal"));                
                profesor.setNombre(resultado.getString("nombre"));
                profesor.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                profesor.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                profesor.setCorreoElectronicoInstitucional(resultado.getString("correoElectronicoInstitucional"));                
                if(resultado.getInt("esUsuario")== 1){
                    profesoresRegistrados.add(profesor);
                }else{
                    profesoresNoRegistrados.add(profesor);
                }
            }
            profesores.add(profesoresRegistrados);
            profesores.add(profesoresNoRegistrados);
            connection.close();
        }
        return profesores;
    }    
    
    //**************************************
    public boolean setCoordiadorUser(int numeroDePersonal) throws SQLException {
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if (connection != null) {
            String query = ("UPDATE `profesores` SET `esUsuario` = '1' WHERE (`numeroDePersonal` = ?);");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, numeroDePersonal);
            int resultInsert = statement.executeUpdate();
            if (resultInsert > 0) {
                result = true;
            }
        }
        connection.close();
        return result;
    }    

}
