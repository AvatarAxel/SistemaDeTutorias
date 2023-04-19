/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BussinessLogic;

import Domain.Profesor;
import Domain.TutorAcademico;
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

    public ArrayList<TutorAcademico> getAllTutores() throws SQLException {

        ArrayList<TutorAcademico> tutores = new ArrayList<TutorAcademico>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if (connection != null) {
            String query = ("select u.* from usuarios u\n" +
                            "inner join roles_usuarios ru on ru.numeroDePersonal=u.numeroDePersonal\n" +
                            "inner join roles r on r.idRol=ru.idRol");
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int resultQuery = resultSet.getInt(1);
            if (resultSet.next()) {
                String nombre;
                String apellidoMaterno;
                String apellidoPaterno;
                int numeroDePersonal;

                do {
                    nombre = resultSet.getString("nombre");
                    apellidoMaterno = resultSet.getString("apellidoMaterno");
                    apellidoPaterno = resultSet.getString("apellidoPaterno");
                    numeroDePersonal = resultSet.getInt("numeroDePersonal");
                    TutorAcademico tutor = new TutorAcademico();
                    tutor.setNombre(nombre);
                    tutor.setApellidoPaterno(apellidoPaterno);
                    tutor.setApellidoMaterno(apellidoMaterno);
                    tutor.setNumeroDePersonal(numeroDePersonal);
                    tutores.add(tutor);

                } while (resultSet.next());
            }
        }
        connection.close();

        return tutores;
    }

}
