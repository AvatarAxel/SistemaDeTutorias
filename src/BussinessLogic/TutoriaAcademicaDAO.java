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
        ArrayList <TutoriaAcademica> listTutoriasAcademicas = new ArrayList<>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if (connection != null) {
            String query = ("SELECT * FROM tutorias_academicas WHERE clave = ?;");            
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, clave);
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
    
    public TutoriaAcademica getCurrentlyTutoriaAcademica()throws SQLException  {
        TutoriaAcademica tutoriaAcademica = new TutoriaAcademica();
        //tutoriaAcademica = null;
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
                String queryTutoriaAcademica = "SELECT * FROM tutorias_academicas ta\n" +
                        "WHERE (NOW() BETWEEN ta.fechaInicio AND ta.fechaFin);";
                PreparedStatement statementTutoriaAcademica = connection.prepareStatement(queryTutoriaAcademica);
                ResultSet resultTutoriaAcademica = statementTutoriaAcademica.executeQuery();
                if(resultTutoriaAcademica.next()){
                    tutoriaAcademica.setIdTutoriaAcademica(resultTutoriaAcademica.getInt("idTutoriaAcademica"));
                    tutoriaAcademica.setNumeroDeSesion(resultTutoriaAcademica.getInt("numeroDeSesion"));
                    tutoriaAcademica.setFechaInicio(resultTutoriaAcademica.getDate("fechaInicio"));
                    tutoriaAcademica.setFechaFin(resultTutoriaAcademica.getDate("fechaFin"));  
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
    
}
