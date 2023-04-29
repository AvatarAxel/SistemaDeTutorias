/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BussinessLogic;

import Domain.PeriodoEscolar;
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
    
    public ArrayList<TutoriaAcademica> getTutoriasAcademicasByTutorAcademico(int numeroPersonal, int clave) throws SQLException {
        ArrayList <TutoriaAcademica> listTutoriasAcademicas = new ArrayList<>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if (connection != null) {
            String query = ("SELECT * FROM tutorias_academicas ta\n" +
            "INNER JOIN reportes_de_tutorias_academicas rta ON rta.idTutoriaAcademica = ta.idTutoriaAcademica\n" +
            "WHERE rta.numeroDePersonal = ? AND ta.clave = ?;");            
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, numeroPersonal);
            statement.setInt(2, clave);            
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                TutoriaAcademica  tutoriaAcademica = new TutoriaAcademica();                
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
    
    public TutoriaAcademica getCurrentlyTutoriaAcademica(String claveProgramaEducativo)throws SQLException  {
        TutoriaAcademica tutoriaAcademica = new TutoriaAcademica();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if (connection != null) {
            String query = "SELECT * FROM periodos_escolares pe\n"
                    + "WHERE (NOW() BETWEEN pe.fechaInicio AND pe.fechaFin);";
            PreparedStatement statementPeriodoEscolar = connection.prepareStatement(query);
            ResultSet resultPeriodoEscolar = statementPeriodoEscolar.executeQuery();
            if (resultPeriodoEscolar.next()) {
                PeriodoEscolar periodoEscolar = new PeriodoEscolar();
                periodoEscolar.setIdPeriodoEscolar(resultPeriodoEscolar.getInt("idPeriodoEscolar"));
                periodoEscolar.setFechaInicio(resultPeriodoEscolar.getDate("fechaInicio"));
                periodoEscolar.setFechaFin(resultPeriodoEscolar.getDate("fechaFin"));
                String queryTutoriaAcademica = "SELECT idTutoriaAcademica, numeroDeSesion, fechaInicio, fechaFin,"
                    + "clave, idPeriodoEscolar, DATE_ADD(fechaFin, INTERVAL 7 DAY) AS fechaLimite\n" +
                    "FROM tutorias_academicas\n" +
                    "WHERE NOW() BETWEEN fechaInicio AND DATE_ADD(fechaFin, INTERVAL 7 DAY)\n" +
                    "AND clave = ?;";
                PreparedStatement statementTutoriaAcademica = connection.prepareStatement(queryTutoriaAcademica);
                statementTutoriaAcademica.setString(1,claveProgramaEducativo );
                ResultSet resultTutoriaAcademica = statementTutoriaAcademica.executeQuery();
                if(resultTutoriaAcademica.next()){
                    tutoriaAcademica.setIdTutoriaAcademica(resultTutoriaAcademica.getInt("idTutoriaAcademica"));
                    tutoriaAcademica.setNumeroDeSesion(resultTutoriaAcademica.getInt("numeroDeSesion"));
                    tutoriaAcademica.setFechaInicio(resultTutoriaAcademica.getDate("fechaInicio"));
                    tutoriaAcademica.setFechaFin(resultTutoriaAcademica.getDate("fechaFin"));
                    tutoriaAcademica.setFechaCierreEntregaReporte(resultTutoriaAcademica.getDate("fechaLimite"));
                    tutoriaAcademica.setPeriodoEscolar(periodoEscolar);
                }else{
                    tutoriaAcademica = null;                
                }
            }else{
                tutoriaAcademica = null;            
            }
            connection.close();
        }
        return tutoriaAcademica;
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
