package BussinessLogic;

import Domain.ExperienciaEducativa;
import Domain.Profesor;
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

    public boolean setProfesorRegister(Profesor profesor) throws SQLException {
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if (connection != null) {
            String query = ("INSERT INTO `sistema_tutorias`.`profesores` \n" +
            "(`numeroDePersonal`, `nombre`, `apellidoPaterno`, `apellidoMaterno`, `correoElectronicoInstitucional`,"
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

    @Override
    public ArrayList<ExperienciaEducativa> consultProfesoresNames() throws SQLException {
        ArrayList<ExperienciaEducativa> profesoresNames = new ArrayList<ExperienciaEducativa>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = "select CONCAT(p.nombre,' ', p.apellidoPaterno,' ', p.apellidoMaterno) as profesorname, ee.nrc, ee.nombre from profesores p inner join experiencias_educativas ee on \n"
                + "p.numeroDePersonal=ee.numeroDePersonal";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String profesorName;
            String nrc;
            String nombre;
            do {
                profesorName = resultSet.getString("profesorname");
                nrc = resultSet.getString("nrc");
                nombre = resultSet.getString("nombre");
                ExperienciaEducativa experienciaProfesor = new ExperienciaEducativa(nrc, profesorName, nombre);
                profesoresNames.add(experienciaProfesor);

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

    public boolean deleteProfesor(int numeroDePersonal, int esUsuario) throws SQLException {
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if (connection != null) {
            String query = ("UPDATE `sistema_tutorias`.`profesores` SET `esRegistrado` = 0, `esUsuario` = ? WHERE (`numeroDePersonal` = ?);");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, esUsuario);
            statement.setInt(2, numeroDePersonal);
            int resultInsert = statement.executeUpdate();
            if (resultInsert > 0) {
                result = true;
            }
        }
        connection.close();
        return result;
    }

}
