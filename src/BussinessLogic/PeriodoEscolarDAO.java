/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BussinessLogic;

import Domain.PeriodoEscolar;
import dataaccess.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author michikato
 */
public class PeriodoEscolarDAO implements IPeriodoEscolarDAO {

    public PeriodoEscolar getPeriodoEscolar(int idTutoriaAcademica) throws SQLException {
        PeriodoEscolar periodoEscolar = new PeriodoEscolar();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if (connection != null) {
            String query = ("SELECT pe.fechaInicio, pe.fechaFin, pe.idPeriodoEscolar\n"
                    + "FROM tutorias_academicas AS ta\n"
                    + "JOIN periodos_escolares AS pe\n"
                    + "ON ta.idPeriodoEscolar = pe.idPeriodoEscolar\n"
                    + "WHERE ta.idTutoriaAcademica = ?;");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idTutoriaAcademica);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                periodoEscolar.setIdPeriodoEscolar(resultSet.getInt("idPeriodoEscolar"));
                periodoEscolar.setFechaInicio(resultSet.getDate("fechaInicio"));
                periodoEscolar.setFechaFin(resultSet.getDate("fechaFin"));
            }
        }
        connection.close();
        return periodoEscolar;
    }

    public PeriodoEscolar getCurrentPeriodoEscolar() throws SQLException {
        PeriodoEscolar periodoEscolar = new PeriodoEscolar();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if (connection != null) {
            String query = ("SELECT * FROM periodos_escolares pe "
                    + "where"
                    + " pe.idPeriodoEscolar=(Select MAX(pe.idPeriodoEscolar) from periodos_escolares);");
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                periodoEscolar.setIdPeriodoEscolar(resultSet.getInt("idPeriodoEscolar"));
                periodoEscolar.setFechaInicio(resultSet.getDate("fechaInicio"));
                periodoEscolar.setFechaFin(resultSet.getDate("fechaFin"));
            }
        }
        connection.close();
        return periodoEscolar;
    }

    @Override
    public PeriodoEscolar getCurrentPeriodo() throws SQLException {
        PeriodoEscolar periodoEscolar = new PeriodoEscolar();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if (connection != null) {
            String query = ("SELECT idPeriodoEscolar, fechaInicio, fechaFin FROM periodos_escolares\n"
                    + "WHERE fechaFin = (SELECT MAX(fechaFin) FROM periodos_escolares) \n"
                    + "AND fechaInicio <= NOW();");
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                periodoEscolar.setIdPeriodoEscolar(resultSet.getInt("idPeriodoEscolar"));
                periodoEscolar.setFechaInicio(resultSet.getDate("fechaInicio"));
                periodoEscolar.setFechaFin(resultSet.getDate("fechaFin"));
            }
        }
        connection.close();
        return periodoEscolar;
    }

}
