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
import javafx.scene.control.Alert;
import util.AlertManager;
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

    public ArrayList<ExperienciaEducativa> getExperienciasEducativasByPeriodoEscoalar(int idPeriodoEscolar) throws SQLException {
        ArrayList<ExperienciaEducativa> listExperienciaEducativas = new ArrayList<>();
        
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String clave=User.getCurrentUser().getRol().getProgramaEducativo().getClave();
        if(connection != null){
            String query = ("SELECT\n" +
                "  ee.nombre,\n" +
                "  ee.nrc,\n" +
                "  epp.seccion,\n" +
                "  epp.modalidad,\n" +
                "  p.nombre AS nombreProfesor,\n" +
                "  p.apellidoPaterno,\n" +
                "  p.apellidoMaterno\n" +
                "FROM\n" +
                "  experiencias_educativas ee\n" +
                "  JOIN experiencias_periodos_profesores epp ON ee.nrc = epp.nrc\n" +
                "  LEFT JOIN profesores p ON epp.numeroDePersonal = p.numeroDePersonal\n" +
                "WHERE\n" +
                    "  epp.idPeriodoEscolar = ? and epp.clave= ? ORDER BY ee.nombre ASC;");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idPeriodoEscolar);
            statement.setString(2, clave);            
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Profesor profesortemp  = new Profesor();
                ExperienciaEducativa experienciaEducativaTemp = new ExperienciaEducativa();
                experienciaEducativaTemp.setNrc(resultSet.getString("nrc"));
                experienciaEducativaTemp.setNombre(resultSet.getString("nombre"));
                experienciaEducativaTemp.setSeccion(resultSet.getString("seccion"));
                profesortemp.setNombre(resultSet.getString("nombreProfesor"));
                if(profesortemp.getNombre() != null){
                    profesortemp.setApellidoPaterno(resultSet.getString("apellidoPaterno"));
                    profesortemp.setApellidoMaterno(resultSet.getString("apellidoMaterno"));                
                }else{
                    profesortemp.setNombre("SIN");
                    profesortemp.setApellidoPaterno("ASIGNAR");
                    profesortemp.setApellidoMaterno("PROFESOR");                     
                }
                experienciaEducativaTemp.setModalidad(resultSet.getString("modalidad"));
                experienciaEducativaTemp.setProfesorNombre(profesortemp.getNombreCompleto());
                listExperienciaEducativas.add(experienciaEducativaTemp);
            }
            connection.close();
        }
        
        return listExperienciaEducativas;
    }    
    public ArrayList<ExperienciaEducativa> getAllCurrentExperienciaEducativaByPeriodo(String clave, int idPeriodoEscolar) throws SQLException {
        ArrayList<ExperienciaEducativa> listExperienciasEducativas = new ArrayList<>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if (connection != null) {
            System.out.println("clave: " + clave);
            System.out.println("id periodo: " + idPeriodoEscolar);
            String query = ("SELECT epp.*, ee.nombre AS nombre_experiencia, p.nombre, p.apellidoPaterno, p.apellidoMaterno\n"
                    + "FROM experiencias_periodos_profesores AS epp\n"
                    + "LEFT JOIN experiencias_educativas AS ee ON epp.nrc = ee.nrc\n"
                    + "LEFT JOIN profesores AS p ON epp.numeroDePersonal = p.numeroDePersonal\n"
                    + "WHERE epp.clave = ? AND epp.idPeriodoEscolar = ?;");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, clave);
            statement.setInt(2, idPeriodoEscolar);
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
                experienciaEducativa.setIdexperiencia_periodo_profesor(resultSet.getInt("idexperiencia_periodo_profesor"));
                experienciaEducativa.setEsSeleccionado(new CheckBox());
                listExperienciasEducativas.add(experienciaEducativa);
            }
        }
        connection.close();
        return listExperienciasEducativas;
    }

    public boolean assignProfesorToExperienciaEducativa(Profesor profesor, ExperienciaEducativa experienciaEducativa, String clave) throws SQLException {
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if (connection != null) {
            String query = ("UPDATE `sistema_tutorias`.`experiencias_periodos_profesores` SET `numeroDePersonal` = ? WHERE (`idexperiencia_periodo_profesor` = ? and `clave` = ?);");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, profesor.getNumeroDePersonal());
            statement.setInt(2, experienciaEducativa.getIdexperiencia_periodo_profesor());
            statement.setString(3, clave);            
            int resultInsert = statement.executeUpdate();
            if (resultInsert > 0) {
                result = true;
            }
        }
        connection.close();
        return result;
    }
    
}
