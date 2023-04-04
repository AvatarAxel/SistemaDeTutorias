/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BussinessLogic;

import Domain.ExperienciaEducativa;
import Domain.Profesor;
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
public class ProfesorDAO implements IProfesorDAO {

    @Override
    public Profesor getProfesor(int numeroDePersonal) throws SQLException {
        Profesor profesor = new Profesor();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        
        if(connection != null){
            String query = ("SELECT * FROM profesores WHERE numeroDePersonal = ?");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, numeroDePersonal);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                profesor.setNumeroDePersonal(resultSet.getInt("numeroDePersonal"));
                profesor.setNombre(resultSet.getString("nombre"));
                profesor.setApellidoPaterno(resultSet.getString("apellidoPaterno"));
                profesor.setApellidoMaterno(resultSet.getString("apellidoMaterno"));
                profesor.setCorreoElectronicoInstitucional(resultSet.getString("correoElectronicoInstitucional"));
            }
        }
        
        return profesor;
    }

    @Override
    public ArrayList<ExperienciaEducativa> consultProfesoresNames() throws SQLException {
    ArrayList<ExperienciaEducativa> profesoresNames = new ArrayList<ExperienciaEducativa>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query="select CONCAT(p.nombre,' ', p.apellidoPaterno,' ', p.apellidoMaterno) as profesorname, ee.nrc, ee.nombre from profesores p inner join experiencias_educativas ee on \n" +
        "p.numeroDePersonal=ee.numeroDePersonal" ;
        PreparedStatement statement = connection.prepareStatement(query);
        //statement.setString(1, experienciaName);
        ResultSet resultSet=statement.executeQuery();
        if (resultSet.next()){
            String profesorName;
            String nrc;
            String nombre;
            do{
            profesorName=resultSet.getString("profesorname");
            nrc=resultSet.getString("nrc");
            nombre=resultSet.getString("nombre");
            ExperienciaEducativa experienciaProfesor= new ExperienciaEducativa(nrc,profesorName,nombre);
            profesoresNames.add(experienciaProfesor);
            
            }while(resultSet.next());
         }
        return profesoresNames;    
    }
    
}
