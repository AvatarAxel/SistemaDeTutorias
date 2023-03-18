/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BussinessLogic;

import Domain.Profesor;
import com.mysql.cj.util.Util;
import dataaccess.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import util.Alerts;

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
            
            profesor.setNumeroDePersonal(resultSet.getInt("numeroDePersonal"));
            profesor.setNombre(resultSet.getString("nombre"));
            profesor.setApellidoPaterno("apellidoPaterno");
            profesor.setApellidoMaterno("apellidoMaterno");
            profesor.setCorreoElectronicoInstitucional(resultSet.getString("correoElectronicoInstitucional"));
        }

        return profesor;
    }
    
    public Profesor getProfesorUnregistered(int numeroDePersonal) throws SQLException {
        Profesor profesor = new Profesor();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if (connection != null) {
            String query = ("SELECT * FROM profesores WHERE numeroDePersonal = ? and esRegistrado = 0");
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
    
}
