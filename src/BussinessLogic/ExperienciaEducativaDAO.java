/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BussinessLogic;

import Domain.ExperienciaEducativa;
import dataaccess.DataBaseConnection;
import singleton.User;

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

    public ArrayList<ExperienciaEducativa> getExperienciasEducativas() throws SQLException {
        ArrayList<ExperienciaEducativa> listExperienciaEducativas = new ArrayList<>();
        
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if(connection != null){
            String query = ("SELECT experiencias_educativas.nrc, experiencias_educativas.nombre, "
            + "experiencias_educativas.seccion, "
            + "experiencias_educativas.modalidad, experiencias_educativas.Clave, "
            + "programas_educativos.nombre AS programaEducativo "
            + "FROM experiencias_educativas "
            + "INNER JOIN programas_educativos "
            + "ON experiencias_educativas.Clave = programas_educativos.clave "
            + "INNER JOIN experiencias_periodos "
            + "ON experiencias_periodos.nrc = experiencias_educativas.nrc "
            + "WHERE experiencias_periodos.idPeriodoEscolar = ?;");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, User.getCurrentUser().getPeriodoActual().getIdPeriodoEscolar());
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                ExperienciaEducativa experienciaEducativaTemp = new ExperienciaEducativa();
                experienciaEducativaTemp.setNrc(resultSet.getString("nrc"));
                experienciaEducativaTemp.setNombre(resultSet.getString("nombre"));
                experienciaEducativaTemp.setSeccion(resultSet.getString("seccion"));
                experienciaEducativaTemp.setProgramaEducativo(resultSet.getString("programaEducativo"));
                experienciaEducativaTemp.setModalidad(resultSet.getString("modalidad"));
                experienciaEducativaTemp.setClave(resultSet.getString("Clave"));
                listExperienciaEducativas.add(experienciaEducativaTemp);
            }
            connection.close();
        }
        
        return listExperienciaEducativas;
    }

    @Override
    public int updateAcademicOffer(ExperienciaEducativa experienciaEducativa) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        
        int result = 0;

        if(connection != null){
            if(experienciaEducativa != null){
                String query = ("UPDATE experiencias_educativas "
                        + "SET nombre = ?, seccion = ?, modalidad = ?, Clave = ?"
                        + "WHERE (`nrc` = ?);");
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, experienciaEducativa.getNombre());
                statement.setString(2, experienciaEducativa.getSeccion());
                statement.setString(3, experienciaEducativa.getModalidad());
                statement.setString(4, experienciaEducativa.getClave());
                statement.setString(5, experienciaEducativa.getNrc());

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
    public ExperienciaEducativa consultExperiencias() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean existNrc(String nrc) throws SQLException {
        boolean result = false;
        
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if(connection != null){
            String query = ("SELECT EXISTS(SELECT nrc FROM sistema_tutorias.experiencias_educativas WHERE nrc = ?) AS existeNrc");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nrc);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                result = (resultSet.getBoolean("existeNrc"));
            }
            
            connection.close();
        }
        
        return result;
    }

    @Override
    public int updateExerienciasPeriodos(int periodo, String nrc) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        
        int result = 0;

        if(connection != null){
            String query = ("INSERT INTO experiencias_periodos (nrc, idPeriodoEscolar) "
            + "VALUES (?,?);");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nrc);
            statement.setInt(2, periodo);

            int affectedRows = statement.executeUpdate();
            result = (affectedRows == 1) ? 1 : 0;
            connection.close();
        }
        
        return result;
    }
    
}