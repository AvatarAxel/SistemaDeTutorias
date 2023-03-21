/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BussinessLogic;

import Domain.PeriodoEscolar;
import Domain.Profesor;
import dataaccess.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author michikato
 */
public class PeriodoEscolarDAO {

    public PeriodoEscolar getPeriodoEscolar(int idTutoriaAcademica) throws SQLException {
        PeriodoEscolar periodoEscolar = new PeriodoEscolar();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if (connection != null) {
            String query = ("SELECT pe.fechaInicio, pe.fechaFin\n"
                    + "FROM tutorias_academicas AS ta\n"
                    + "JOIN periodos_escolares AS pe\n"
                    + "ON ta.idPeriodoEscolar = pe.idPeriodoEscolar\n"
                    + "WHERE ta.idTutoriaAcademica = ?;");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idTutoriaAcademica);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                periodoEscolar.setFechaInicio(resultSet.getDate("fechaInicio"));
                periodoEscolar.setFechaFin(resultSet.getDate("fechaFin"));
            }
        }
        connection.close();
        return periodoEscolar;
    }
}
