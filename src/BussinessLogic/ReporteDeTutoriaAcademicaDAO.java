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
}
