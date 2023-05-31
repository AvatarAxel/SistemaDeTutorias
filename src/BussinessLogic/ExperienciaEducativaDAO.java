/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BussinessLogic;

import Domain.ExperienciaEducativa;
import Domain.Profesor;
import dataaccess.DataBaseConnection;
import singleton.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.control.CheckBox;

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

        if (connection != null) {
            if (experienciaEducativa != null) {
                String query = ("CALL insertarExperiencias(?, ?, ?, ?, ?, ?)");
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, experienciaEducativa.getNrc());
                statement.setString(2, experienciaEducativa.getSeccion());
                statement.setString(3, experienciaEducativa.getModalidad());
                statement.setString(4, experienciaEducativa.getClave());
                statement.setString(5, experienciaEducativa.getNombre());
                statement.setInt(6, User.getCurrentUser().getPeriodoActual().getIdPeriodoEscolar());

                int affectedRows = statement.executeUpdate();
                result = (affectedRows >= 1) ? 1 : 0;
            } else {
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
        String query = "select distinct nombre from experiencias_educativas";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String experiencia;
            do {
                experiencia = resultSet.getString("Nombre");
                experienciasNames.add(experiencia);

            } while (resultSet.next());
        }
        return experienciasNames;

    }

    public ArrayList<ExperienciaEducativa> getExperienciasEducativas() throws SQLException {
        ArrayList<ExperienciaEducativa> listExperienciaEducativas = new ArrayList<>();

        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if (connection != null) {
            String query = ("SELECT experiencias_educativas.nrc, experiencias_educativas.nombre, \n"
                    + "experiencias_periodos_profesores.seccion, \n"
                    + "experiencias_periodos_profesores.modalidad, experiencias_periodos_profesores.clave, \n"
                    + "programas_educativos.nombre AS programaEducativo \n"
                    + "FROM experiencias_educativas \n"
                    + "INNER JOIN experiencias_periodos_profesores \n"
                    + "ON experiencias_periodos_profesores.nrc = experiencias_educativas.nrc \n"
                    + "INNER JOIN programas_educativos \n"
                    + "ON programas_educativos.clave = experiencias_periodos_profesores.clave \n"
                    + "WHERE experiencias_periodos_profesores.idPeriodoEscolar = ?;");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, User.getCurrentUser().getPeriodoActual().getIdPeriodoEscolar());
            System.out.println("SSSS"+ User.getCurrentUser().getPeriodoActual().getIdPeriodoEscolar());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ExperienciaEducativa experienciaEducativaTemp = new ExperienciaEducativa();
                experienciaEducativaTemp.setNrc(resultSet.getString("nrc"));
                experienciaEducativaTemp.setNombre(resultSet.getString("nombre"));
                experienciaEducativaTemp.setSeccion(resultSet.getString("seccion"));
                experienciaEducativaTemp.setProgramaEducativo(resultSet.getString("programaEducativo"));
                experienciaEducativaTemp.setModalidad(resultSet.getString("modalidad"));
                experienciaEducativaTemp.setClave(resultSet.getString("clave"));
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

        if (connection != null) {
            if (experienciaEducativa != null) {
                String query = ("CALL actualizarExperiencias(?, ?, ?, ?, ?, ?, ?)");
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, experienciaEducativa.getNrc());
                statement.setString(2, experienciaEducativa.getNombre());
                statement.setString(3, experienciaEducativa.getSeccion());
                statement.setString(4, experienciaEducativa.getModalidad());
                statement.setString(5, experienciaEducativa.getClave());
                statement.setString(6, experienciaEducativa.getClave());
                statement.setInt(7, User.getCurrentUser().getPeriodoActual().getIdPeriodoEscolar());

                int affectedRows = statement.executeUpdate();
                result = (affectedRows >= 1) ? 1 : 0;
            } else {
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

        if (connection != null) {
            String query = ("SELECT EXISTS(SELECT nrc FROM experiencias_periodos_profesores WHERE nrc = ? AND idPeriodoEscolar = ?) AS existeNrc");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nrc);
            statement.setInt(2, User.getCurrentUser().getPeriodoActual().getIdPeriodoEscolar());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = (resultSet.getBoolean("existeNrc"));
            }

            connection.close();
        }

        return result;
    }

    public ArrayList<ExperienciaEducativa> getAllCurrentExperienciaEducativaByPeriodo(String clave, int idPeriodoEscolar) throws SQLException {
        ArrayList<ExperienciaEducativa> listExperienciasEducativas = new ArrayList<>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if (connection != null) {
            String query = ("SELECT epp.*, ee.nombre AS nombre_experiencia, p.nombre, p.apellidoPaterno, p.apellidoMaterno\n"
                    + "FROM experiencias_periodos_profesores AS epp\n"
                    + "LEFT JOIN experiencias_educativas AS ee ON epp.nrc = ee.nrc\n"
                    + "LEFT JOIN profesores AS p ON epp.numeroDePersonal = p.numeroDePersonal;");
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ExperienciaEducativa experienciaEducativa = new ExperienciaEducativa();
                Profesor profesor = new Profesor();
                experienciaEducativa.setNombre(resultSet.getString("nombre_experiencia"));
                if (resultSet.getString("nombre") != null) {
                    profesor.setNombre(resultSet.getString("nombre"));
                    profesor.setApellidoPaterno(resultSet.getString("apellidoPaterno"));
                    profesor.setApellidoMaterno(resultSet.getString("apellidoMaterno"));
                    profesor.setNumeroDePersonal(resultSet.getInt("numeroDePersonal"));
                } else {
                    profesor.setNombre("Sin Asignar");
                    profesor.setApellidoPaterno("");
                    profesor.setApellidoMaterno("");
                }
                experienciaEducativa.setProfesor(profesor);
                experienciaEducativa.setNrc(resultSet.getString("nrc"));
                experienciaEducativa.setEsSeleccionado(new CheckBox());                
                listExperienciasEducativas.add(experienciaEducativa);
            }
        }
        connection.close();
        return listExperienciasEducativas;
    }
}
