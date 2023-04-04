/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BussinessLogic;

import Domain.ExperienciaEducativa;
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
public class ExperienciaEducativaDAO implements IExperiencaEducativaDAO {

    @Override
    public int uploadAcademicOffer(ExperienciaEducativa experienciaEducativa) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        
        int result = 0;

        if(connection != null){
            if(experienciaEducativa != null){
                String query = ("INSERT INTO experiencias_educativas "
                            + "(nrc, nombre, seccion, modalidad, Clave) "
                            + "VALUES (?, ?, ?, ?, ?)");
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, experienciaEducativa.getNrc());
                statement.setString(2, experienciaEducativa.getNombre());
                statement.setString(3, experienciaEducativa.getSeccion());
                statement.setString(4, experienciaEducativa.getModalidad());
                statement.setString(5, experienciaEducativa.getClave());

                int affectedRows = statement.executeUpdate();
                result = (affectedRows == 1) ? 1 : 0;
            }else{
                result = 0;
            }
            
            connection.close();
        }
        
        return result;
    }

    @Override
    public ArrayList<String> consultExperienciasName() throws SQLException {
        ArrayList<String> experienciasNames = new ArrayList<String>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query="select distinct nombre from experiencias_educativas";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet=statement.executeQuery();
        if (resultSet.next()){
            String experiencia;
            do{
            experiencia=resultSet.getString("Nombre");
            experienciasNames.add(experiencia);
            
            }while(resultSet.next());
         }
        return experienciasNames;
         
    }

  
   
    
}
