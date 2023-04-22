/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BussinessLogic;

import Domain.ReporteTutoriaAcademica;
import Domain.Usuario;
import dataaccess.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author Propietario
 */
public class ReporteTutoriaAcademicaDAO {


    public static ArrayList<ReporteTutoriaAcademica> getReportesTutoriasAcademicasByTutorByProgramaEducativo(Usuario tutorAcademico, int PEClave) throws SQLException {
        ArrayList<ReporteTutoriaAcademica> reportesTutoriasAcademicas = new ArrayList<>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();      
        if (connection != null) {
            String consulta = "SELECT rta.idReporteTutoria, ta.idTutoriaAcademica, ta.fechaInicio,\n" +
                    "ta.fechaFin, pe.fechaInicio ,pe.fechaFin\n" +
                    "FROM reportes_de_tutorias_academicas rta\n" +
                    "INNER JOIN usuarios u ON  rta.numeroDePersonal= u.numeroDePersonal\n" +
                    "INNER JOIN  tutorias_academicas ta ON rta.idTutoriaAcademica = ta.idTutoriaAcademica\n" +
                    "INNER JOIN periodos_escolares pe ON ta.idPeriodoEscolar = pe.idPeriodoEscolar\n" +
                    "INNER JOIN programas_educativos p ON ta.clave = p.clave\n" +
                    "WHERE rta.numeroDePersonal = ? AND  p.clave = ?";
            PreparedStatement configurarConsulta = connection.prepareStatement(consulta);
            configurarConsulta.setInt(1, tutorAcademico.getNumeroPersonal());
            configurarConsulta.setInt(2, PEClave);
            ResultSet resultado = configurarConsulta.executeQuery();
            while (resultado.next()) {
                ReporteTutoriaAcademica reporteTutoriasAcademicas = new ReporteTutoriaAcademica();
                reporteTutoriasAcademicas.setIdReporteTutoriasAcademicas(resultado.getInt("idReporteTutoria"));
                reporteTutoriasAcademicas.setIdTutoriaAcademica(resultado.getInt("idTutoriaAcademica"));
                reporteTutoriasAcademicas.setFechaInicioTutoriaAcademica(resultado.getDate("fechaInicio"));
                reporteTutoriasAcademicas.setFechaFinTutoriaAcademica(resultado.getDate("fechaFin"));
                reporteTutoriasAcademicas.setFechaInicioPeriodoEscolar(resultado.getDate("fechaInicio"));
                reporteTutoriasAcademicas.setFechaFinPeriodoEscolar(resultado.getDate("fechaFin"));                    
                reportesTutoriasAcademicas.add(reporteTutoriasAcademicas);
            }
            connection.close();
        }
        return reportesTutoriasAcademicas;
    }
    
}
