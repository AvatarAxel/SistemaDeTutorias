/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BussinessLogic;

import Domain.ReporteDeTutoriaAcademica;
import Domain.TutorAcademico;
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
public class ReporteDeTutoriaAcademicaDAO {
    
    public ArrayList<ReporteDeTutoriaAcademica> getReportesDeTutorias(int idTutoriaAcademica) throws SQLException {
        ArrayList <ReporteDeTutoriaAcademica> listReporteDeTutoriaAcademica = new ArrayList<>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if (connection != null) {
            String query = ("SELECT r.idReporteTutoria, r.comentariosGenerales,\n"
                    + "       u.nombre, u.apellidoPaterno, u.apellidoMaterno\n"
                    + "FROM reportes_de_tutorias_academicas r\n"
                    + "INNER JOIN usuarios u ON r.numeroDePersonal = u.numeroDePersonal\n"
                    + "WHERE r.idTutoriaAcademica = ?;");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idTutoriaAcademica);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ReporteDeTutoriaAcademica  reporteDeTutoriaAcademica = new ReporteDeTutoriaAcademica();
                TutorAcademico tutorAcademico = new TutorAcademico();
                reporteDeTutoriaAcademica.setComentariosGenerales(resultSet.getString("comentariosGenerales"));
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
}
