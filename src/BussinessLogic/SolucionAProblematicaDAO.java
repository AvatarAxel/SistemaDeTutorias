/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BussinessLogic;

import Domain.Profesor;
import Domain.SolucionAProblematica;
import dataaccess.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Panther
 */
public class SolucionAProblematicaDAO implements ISolucionAProblematicaDAO {

    @Override
    public int insertSolucionAProblematica(int idProblematica, String solucion) throws SQLException {
        int respuesta = 0;
        SolucionAProblematica profesor = new SolucionAProblematica();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        
        if(connection != null){
            String query = ("INSERT INTO soluciones_a_problematicas_academicas (descripcion) VALUES (?);");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idProblematica);
            int filasAfectadas = statement.executeUpdate();
            respuesta = 1;
        }
        return respuesta;
    }

    @Override
    public int updateSolucionAProblematica(int idSolucion, String solucion) throws SQLException {
        int respuesta = 0;
        SolucionAProblematica profesor = new SolucionAProblematica();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        
        if(connection != null){
            String query = ("UPDATE soluciones_a_problematicas_academicas SET descripcion = ? WHERE (idSolucion = ?);");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, solucion);
            statement.setInt(2, idSolucion);
            int filasAfectadas = statement.executeUpdate();
            respuesta = 1;
        }
        return respuesta;
    }
    
}
