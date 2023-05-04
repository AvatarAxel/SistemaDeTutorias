/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BussinessLogic;

import Domain.ReporteDeTutoriaAcademica;
import Domain.TutorAcademico;
import dataaccess.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import util.AlertManager;

/**
 *
 * @author michikato
 */
public class ReporteDeTutoriaAcademicaDAO {

    public ArrayList<ReporteDeTutoriaAcademica> getReportesDeTutorias(int idTutoriaAcademica) throws SQLException {
        ArrayList<ReporteDeTutoriaAcademica> listReporteDeTutoriaAcademica = new ArrayList<>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if (connection != null) {
            String query = ("SELECT \n"
                    + "  r.idReporteTutoria,\n"
                    + "  r.comentariosGenerales,\n"
                    + "  u.nombre,\n"
                    + "  u.apellidoPaterno,\n"
                    + "  u.apellidoMaterno,\n"
                    + "  (SELECT COUNT(*) FROM lista_de_asistencias WHERE esAsistente = 1 AND idReporteTutoria = r.idReporteTutoria) as totalAsistentes,\n"
                    + "  (SELECT COUNT(*) FROM lista_de_asistencias WHERE idReporteTutoria = r.idReporteTutoria) as totalRegistrados\n"
                    + "FROM \n"
                    + "  reportes_de_tutorias_academicas r\n"
                    + "  INNER JOIN usuarios u ON r.numeroDePersonal = u.numeroDePersonal\n"
                    + "WHERE \n"
                    + "  r.idTutoriaAcademica = ?;");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idTutoriaAcademica);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ReporteDeTutoriaAcademica reporteDeTutoriaAcademica = new ReporteDeTutoriaAcademica();
                TutorAcademico tutorAcademico = new TutorAcademico();
                reporteDeTutoriaAcademica.setComentariosGenerales(resultSet.getString("comentariosGenerales"));
                reporteDeTutoriaAcademica.setIdReporteTutoria(resultSet.getInt("idReporteTutoria"));
                reporteDeTutoriaAcademica.setNumeroDeAlumnosEnRiesgo(resultSet.getInt("totalRegistrados"));
                reporteDeTutoriaAcademica.setNumeroDeTutoradosQueAsistieron(resultSet.getInt("totalAsistentes"));
                tutorAcademico.setNombre(resultSet.getString("nombre"));
                tutorAcademico.setApellidoPaterno(resultSet.getString("apellidoPaterno"));
                tutorAcademico.setApellidoMaterno(resultSet.getString("apellidoMaterno"));
                reporteDeTutoriaAcademica.setTutor(tutorAcademico);
                listReporteDeTutoriaAcademica.add(reporteDeTutoriaAcademica);
            }
        }
        connection.close();
        return listReporteDeTutoriaAcademica;
    }

    public ReporteDeTutoriaAcademica getReporteDeTutoriaByTutor(int idTutoriaAcademica, int  numeroPersonal) throws SQLException {
        ReporteDeTutoriaAcademica reporteDeTutoriaAcademica = new ReporteDeTutoriaAcademica();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if (connection != null) {
            String query = ("SELECT \n"
                    + "  r.idReporteTutoria,\n"
                    + "  r.comentariosGenerales,\n"
                    + "  u.nombre,\n"
                    + "  u.apellidoPaterno,\n"
                    + "  u.apellidoMaterno,\n"
                    + "  (SELECT COUNT(*) FROM lista_de_asistencias WHERE esAsistente = 1 AND idReporteTutoria = r.idReporteTutoria) as totalAsistentes,\n"
                    + "  (SELECT COUNT(*) FROM lista_de_asistencias WHERE enRiesgo = 1 AND idReporteTutoria = r.idReporteTutoria) as totalEnRiesgo,\n"
                    + "FROM \n"
                    + "  reportes_de_tutorias_academicas r\n"
                    + "  INNER JOIN usuarios u ON r.numeroDePersonal = u.numeroDePersonal\n"
                    + "WHERE \n"
                    + "  r.idTutoriaAcademica = ? AND r.numeroDePersonal=?;");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idTutoriaAcademica);
            statement.setInt(2, numeroPersonal);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                TutorAcademico tutorAcademico = new TutorAcademico();
                reporteDeTutoriaAcademica.setComentariosGenerales(resultSet.getString("comentariosGenerales"));
                reporteDeTutoriaAcademica.setIdReporteTutoria(resultSet.getInt("idReporteTutoria"));
                reporteDeTutoriaAcademica.setNumeroDeAlumnosEnRiesgo(resultSet.getInt("totalEnRiesgo"));
                reporteDeTutoriaAcademica.setNumeroDeTutoradosQueAsistieron(resultSet.getInt("totalAsistentes"));
                tutorAcademico.setNombre(resultSet.getString("nombre"));
                tutorAcademico.setApellidoPaterno(resultSet.getString("apellidoPaterno"));
                tutorAcademico.setApellidoMaterno(resultSet.getString("apellidoMaterno"));
                tutorAcademico.setNumeroDePersonal(numeroPersonal);
                reporteDeTutoriaAcademica.setTutor(tutorAcademico);            
            }else{
                reporteDeTutoriaAcademica = null;
            }
        }
        connection.close();
        return reporteDeTutoriaAcademica;
    }
    
    public ReporteDeTutoriaAcademica getCurrentlyReporteDeTutorias(int idTutoriaAcademica, int  numeroPersonal) throws SQLException{
        ReporteDeTutoriaAcademica reporteDeTutoriaAcademica = new ReporteDeTutoriaAcademica();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if (connection != null) {
            String query = ("SELECT * FROM reportes_de_tutorias_academicas\n" +
                "WHERE idTutoriaAcademica = ? AND numeroDePersonal =?;");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idTutoriaAcademica);
            statement.setInt(2, numeroPersonal);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                TutorAcademico tutorAcademico = new TutorAcademico();
                reporteDeTutoriaAcademica.setIdReporteTutoria(resultSet.getInt("idReporteTutoria"));
                reporteDeTutoriaAcademica.setComentariosGenerales(resultSet.getString("comentariosGenerales"));
                reporteDeTutoriaAcademica.setIdReporteTutoria(resultSet.getInt("idTutoriaAcademica"));
                tutorAcademico.setNumeroDePersonal(resultSet.getInt("numeroDePersonal"));
                reporteDeTutoriaAcademica.setTutor(tutorAcademico);            
            }else{
                reporteDeTutoriaAcademica = null;
            }
        }
        connection.close();
        return reporteDeTutoriaAcademica;    
    }
    
    public boolean setReporteDeTutorias(String comentarioGeneral, int idTutoriaAcademica, int numeroPersonal) throws SQLException{
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if(connection!=null){
            String query = ("INSERT INTO reportes_de_tutorias_academicas (`comentariosGenerales`,`idTutoriaAcademica`, `numeroDePersonal`) VALUES (?, ?, ?);");            
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, comentarioGeneral);
            statement.setInt(2, idTutoriaAcademica);
            statement.setInt(3, numeroPersonal);            
            int resultInsert = statement.executeUpdate();            
            if(resultInsert>0){
                result = true;
            }
        }        
        connection.close();
        return result;
    }
    
    public boolean updateReporteDeTutorias(String comentarioGeneral, int idTutoriaAcademica, int numeroPersonal) throws SQLException {
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if(connection!=null){
            String queryGet = "SELECT *\n" +
                    "FROM reportes_de_tutorias_academicas\n" +
                    "WHERE idTutoriaAcademica = ? AND numeroDePersonal = ?";
            PreparedStatement statementGet = connection.prepareStatement(queryGet);
            statementGet.setInt(1, idTutoriaAcademica);
            statementGet.setInt(2, numeroPersonal);
            ResultSet resultGet = statementGet.executeQuery();            
            if(resultGet.next()){
                int idReporteTutoria = resultGet.getInt("idReporteTutoria");
                String querySet = "UPDATE reportes_de_tutorias_academicas\n"
                                + "SET comentariosGenerales = ? " + "WHERE idReporteTutoria = ?";            
                PreparedStatement statementSet = connection.prepareStatement(querySet);
                statementSet.setString(1, comentarioGeneral);
                statementSet.setInt(2, idReporteTutoria);
                int resultInsert = statementSet.executeUpdate();            
                if(resultInsert>0){
                    result = true;
                }
            }           
        }        
        connection.close();
        return result;    
    }
}
