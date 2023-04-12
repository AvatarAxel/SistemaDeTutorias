/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BussinessLogic;

import Domain.ExperienciaEducativa;
import Domain.ProgramaEducativo;
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
public class ProgramaEducativoDAO implements IProgramaEducativoDAO {

    @Override
    public ArrayList<ProgramaEducativo> getProgramasEducativos() throws SQLException {
        ArrayList<ProgramaEducativo> listProgramasEducativos = new ArrayList<>();
        
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if(connection != null){
            String query = ("SELECT * FROM programas_educativos;");
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                ProgramaEducativo programaEducativoTemp = new ProgramaEducativo();
                programaEducativoTemp.setClave(resultSet.getString("clave"));
                programaEducativoTemp.setNombre(resultSet.getString("nombre"));
                listProgramasEducativos.add(programaEducativoTemp);
            }
            connection.close();
        }
        
        return listProgramasEducativos;
    }
    
}
