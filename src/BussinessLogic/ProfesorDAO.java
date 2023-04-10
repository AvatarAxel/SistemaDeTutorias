/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BussinessLogic;

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

    @Override
    public Profesor getProfesor(int numeroDePersonal) throws SQLException {
        Profesor profesor = new Profesor();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if(connection != null){
            String query = ("SELECT * FROM profesores WHERE numeroDePersonal = ?");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, numeroDePersonal);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                profesor.setNumeroDePersonal(resultSet.getInt("numeroDePersonal"));
                profesor.setNombre(resultSet.getString("nombre"));
                profesor.setApellidoPaterno(resultSet.getString("apellidoPaterno"));
                profesor.setApellidoMaterno(resultSet.getString("apellidoMaterno"));
                profesor.setCorreoElectronicoInstitucional(resultSet.getString("correoElectronicoInstitucional"));
            }
        }

        return profesor;
    }

    public ArrayList<Profesor> getProfesoresUnregistered() throws SQLException {
        ArrayList<Profesor> listProfesores = new ArrayList<>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if (connection != null) {
            String query = ("SELECT * FROM profesores WHERE esRegistrado = 0");
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

    public boolean setTutorRegister(int numeroDePersonal) throws SQLException {
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if (connection != null) {
            String query = ("UPDATE `profesores` SET `esRegistrado` = '1' WHERE (`numeroDePersonal` = ?);");
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
