/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BussinessLogic;

import Domain.ExperienciaEducativa;
import Domain.ProblematicaAcademica;
import Domain.Profesor;
import Domain.SolucionAProblematica;
import dataaccess.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Panther
 */
public class ProblematicaAcademicaDAO implements IProblematicaAcademicaDAO {

    @Override
    public ArrayList<ProblematicaAcademica> getProblematicasSinSolucion() throws SQLException {
        ArrayList<ProblematicaAcademica> listProblematicasAcademicas = new ArrayList<>();
        
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        
        
        if(connection != null){
            String consulta ="SELECT * FROM problematicas_academicas\n" +
                "WHERE problematicas_academicas.idProblematica NOT IN \n" +
                "  (SELECT soluciones_a_problematicas_academicas.idProblematica\n" +
                "  FROM soluciones_a_problematicas_academicas)";
            PreparedStatement statement = connection.prepareStatement(consulta);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                ProblematicaAcademica problematicaAcademicaTemp = new ProblematicaAcademica();
                problematicaAcademicaTemp.setIdProblematica(resultSet.getInt("idProblematica"));
                problematicaAcademicaTemp.setNumeroDeEstudiantesAfectados(resultSet.getInt("numeroDeEstudiantesAfectados"));
                problematicaAcademicaTemp.setDescripcion(resultSet.getString("descripcion"));
                problematicaAcademicaTemp.setSolucion(new SolucionAProblematica(0, ""));
                listProblematicasAcademicas.add(problematicaAcademicaTemp);
            }
            connection.close();
        }
        
        return listProblematicasAcademicas;
    }
    
    @Override
    public ArrayList<ProblematicaAcademica> getProblematicas() throws SQLException {
        ArrayList<ProblematicaAcademica> listProblematicasAcademicas = new ArrayList<>();
        
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        
        
        if(connection != null){
            String consulta ="SELECT problematicas_academicas.idProblematica,\n" +
                "problematicas_academicas.descripcion,\n" +
                "titulo,numeroDeEstudiantesAfectados,\n" +
                "idReporteTutoria,\n" +
                "soluciones_a_problematicas_academicas.idSolucion,\n" +
                "soluciones_a_problematicas_academicas.descripcion AS descripcion_solucion\n" +
                "FROM problematicas_academicas INNER JOIN soluciones_a_problematicas_academicas\n" +
                "ON problematicas_academicas.idProblematica = soluciones_a_problematicas_academicas.idProblematica";
            PreparedStatement statement = connection.prepareStatement(consulta);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                ProblematicaAcademica problematicaAcademicaTemp = new ProblematicaAcademica();
                problematicaAcademicaTemp.setIdProblematica(resultSet.getInt("idProblematica"));
                problematicaAcademicaTemp.setNumeroDeEstudiantesAfectados(resultSet.getInt("numeroDeEstudiantesAfectados"));
                problematicaAcademicaTemp.setDescripcion(resultSet.getString("descripcion"));
                problematicaAcademicaTemp.setSolucion(new SolucionAProblematica(resultSet.getInt("idSolucion"), resultSet.getString("descripcion_solucion")));
                listProblematicasAcademicas.add(problematicaAcademicaTemp);
            }
            connection.close();
        }
        
        return listProblematicasAcademicas;
    }
    
    public ArrayList<ProblematicaAcademica> getProblematicasByReporte(int idReporteTutoria) throws SQLException {
        ArrayList <ProblematicaAcademica> listProblematicaAcademica = new ArrayList<>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if (connection != null) {
            String query = ("SELECT p.idProblematica, p.descripcion, p.numeroDeEstudiantesAfectados, \n"
                    + "    e.nrc, e.nombre as nombreExperiencia, \n"
                    + "    pr.nombre, pr.apellidoPaterno, pr.apellidoMaterno\n"
                    + "FROM problematicas_academicas p\n"
                    + "INNER JOIN experiencias_educativas e ON p.nrc = e.nrc\n"
                    + "INNER JOIN profesores pr ON e.numeroDePersonal = pr.numeroDePersonal\n"
                    + "WHERE p.idReporteTutoria = ?;");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idReporteTutoria);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ProblematicaAcademica  problematicaAcademica = new ProblematicaAcademica();
                Profesor profesor = new Profesor();                
                ExperienciaEducativa experienciaEducativa = new ExperienciaEducativa();
                experienciaEducativa.setNombre(resultSet.getString("nombreExperiencia"));
                experienciaEducativa.setNrc(resultSet.getString("nrc"));
                profesor.setNombre(resultSet.getString("nombre"));
                profesor.setApellidoPaterno(resultSet.getString("apellidoPaterno"));
                profesor.setApellidoMaterno(resultSet.getString("apellidoMaterno"));
                problematicaAcademica.setProfesor(profesor);
                problematicaAcademica.setExperienciaEducativa(experienciaEducativa);
                problematicaAcademica.setSolucion(new SolucionAProblematica(0,""));
                problematicaAcademica.setDescripcion(resultSet.getString("descripcion"));
                problematicaAcademica.setNumeroDeEstudiantesAfectados(resultSet.getInt("numeroDeEstudiantesAfectados"));                
                listProblematicaAcademica.add(problematicaAcademica);
            }
        }
        connection.close();
        return listProblematicaAcademica;
    }        
    
}
