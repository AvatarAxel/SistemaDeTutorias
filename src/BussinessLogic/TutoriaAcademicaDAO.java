/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BussinessLogic;

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
public class TutoriaAcademicaDAO {

    public ArrayList<TutoriaAcademica> getTutoriasAcademicas(String clave) throws SQLException {
        ArrayList<TutoriaAcademica> listTutoriasAcademicas = new ArrayList<>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if (connection != null) {
            String query = ("SELECT * FROM tutorias_academicas WHERE clave = ?;");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, clave);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                TutoriaAcademica tutoriaAcademica = new TutoriaAcademica();
                tutoriaAcademica.setIdTutoriaAcademica(resultSet.getInt("idTutoriaAcademica"));
                tutoriaAcademica.setNumeroDeSesion(resultSet.getInt("numeroDeSesion"));
                tutoriaAcademica.setFechaInicio(resultSet.getDate("fechaInicio"));
                tutoriaAcademica.setFechaFin(resultSet.getDate("fechaFin"));
                listTutoriasAcademicas.add(tutoriaAcademica);
            }
        }
        connection.close();
        return listTutoriasAcademicas;
    }

    public int addTutoriaAcademica(TutoriaAcademica tutoria) throws SQLException {
        int result = 0;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();

        Connection connection = dataBaseConnection.getConnection();
        String query;
        query = ("INSERT INTO `sistema_tutorias`.`tutorias_academicas` "
                + "(`numeroDeSesion`, `fechaInicio`, `fechaFin`, `clave`, `idPeriodoEscolar`,`idTutoriaAcademica`) "
                + "VALUES (?, ?, ?, ?, ?,?);");
        if (connection != null) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, tutoria.getNumeroDeSesion());
            statement.setDate(2, tutoria.getFechaInicio());
            statement.setDate(3, tutoria.getFechaFin());
            statement.setString(4, tutoria.getPeriodoEscolar().getClave());
            statement.setInt(5, tutoria.getPeriodoEscolar().getIdPeriodoEscolar());
            statement.setInt(6, 5);

            result = statement.executeUpdate();
        }
        connection.close();

        return result;

    }

    public int getNumeroSesion(int idPeriodoEscolar, String clave) throws SQLException {
        int numeroSesion = 0;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();

        Connection connection = dataBaseConnection.getConnection();
        String query;

        query = ("select MAX(numeroDeSesion) as numeroDeSesion"
                + " from tutorias_academicas "
                + "where idPeriodoEscolar=? and clave=?;");

        if (connection != null) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idPeriodoEscolar);
            statement.setString(2, clave);
            numeroSesion = statement.executeUpdate();


        }

        return numeroSesion;

    }

}
