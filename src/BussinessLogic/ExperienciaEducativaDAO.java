/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BussinessLogic;

import Domain.ExperienciaEducativa;
import dataaccess.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Panther
 */
public class ExperienciaEducativaDAO implements IExperiencaEducativaDAO {

    @Override
    public int uploadAcademicOffer(ExperienciaEducativa experienciaEduvcativa) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        
        int result = 0;

        if(connection != null){
            String query = ("INSERT INTO problematicaacademica "
                        + "(nrc, nombre, seccion, modalidad) "
                        + "VALUES (?, ?, ?, ?)");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, experienciaEduvcativa.getNrc());
            statement.setString(2, experienciaEduvcativa.getNombre());
            statement.setString(3, experienciaEduvcativa.getSeccion());
            statement.setString(4, experienciaEduvcativa.getModalidad());
            
            int affectedRows = statement.executeUpdate();
            result = (affectedRows == 1) ? 1 : 0;
            connection.close();
        }
        
        return result;
    }
    
}
