/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BussinessLogic;

import Domain.ProblematicaAcademica;
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
    public ArrayList<ProblematicaAcademica> getProblematicas() throws SQLException {
        ArrayList<ProblematicaAcademica> listProblematicasAcademicas = new ArrayList<>();
        
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        
        
        if(connection != null){
            String consulta ="SELECT * FROM problematicas_academicas WHERE idSolucion = ?";
            PreparedStatement statement = connection.prepareStatement(consulta);
            statement.setInt(1, 0);
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
    
}
