package BussinessLogic;

import Domain.ExperienciaEducativa;
import Domain.PeriodoEscolar;
import Domain.ProblematicaAcademica;
import Domain.Profesor;
import Domain.SolucionAProblematica;
import util.ExceptionCodes;
import Domain.TutoriaAcademica;
import DomainGraphicInterface.ProblematicaReporteTutoria;
import dataaccess.DataBaseConnection;  
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.time.LocalDate;
//import java.util.Date;

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

        if (connection != null) {
            String consulta = "SELECT * FROM problematicas_academicas\n"
                    + "WHERE problematicas_academicas.idProblematica NOT IN \n"
                    + "  (SELECT soluciones_a_problematicas_academicas.idProblematica\n"
                    + "  FROM soluciones_a_problematicas_academicas)";
            PreparedStatement statement = connection.prepareStatement(consulta);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
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

        if (connection != null) {
            String consulta = "SELECT problematicas_academicas.idProblematica,\n"
                    + "problematicas_academicas.descripcion,\n"
                    + "titulo,numeroDeEstudiantesAfectados,\n"
                    + "idReporteTutoria,\n"
                    + "soluciones_a_problematicas_academicas.idSolucion,\n"
                    + "soluciones_a_problematicas_academicas.descripcion AS descripcion_solucion\n"
                    + "FROM problematicas_academicas INNER JOIN soluciones_a_problematicas_academicas\n"
                    + "ON problematicas_academicas.idProblematica = soluciones_a_problematicas_academicas.idProblematica";
            PreparedStatement statement = connection.prepareStatement(consulta);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
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

    @Override
    public ArrayList<ProblematicaAcademica> getProblematicasByReporte(int idReporte) throws SQLException {
        ArrayList<ProblematicaAcademica> problematicas = new ArrayList<ProblematicaAcademica>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = "select pa.*, ee.nombre as experiencia, concat(p.nombre, \" \",p.apellidoPaterno, \" \",p.apellidoMaterno) as profesor from problematicas_academicas \n"
                + "pa inner join experiencias_educativas ee on pa.nrc=ee.nrc \n"
                + "inner join profesores p on ee.numeroDePersonal=p.numeroDePersonal where pa.idReporteTutoria=?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idReporte);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int idProblematica = 0;
            String descripcion;
            String titulo;
            int numeroAfectados = 0;
            int idReporteTutoria = 0;
            String nrc;
            String experiencia;
            String profesor;
            do {
                idProblematica = resultSet.getInt("idProblematica");
                descripcion = resultSet.getString("descripcion");
                titulo = resultSet.getString("titulo");
                numeroAfectados = resultSet.getInt("numeroDeEstudiantesAfectados");
                idReporteTutoria = resultSet.getInt("idReporteTutoria");
                nrc = resultSet.getString("nrc");
                experiencia = resultSet.getString("experiencia");
                profesor = resultSet.getString("profesor");

                ProblematicaAcademica problematica = new ProblematicaAcademica();
                problematica.setIdProblematica(idProblematica);
                problematica.setDescripcion(descripcion);
                problematica.setTitulo(titulo);
                problematica.setNumeroDeEstudiantesAfectados(numeroAfectados);
                problematica.setIdReporteTutoria(idReporteTutoria);
                problematica.setNrc(nrc);
                problematica.setExperienciaEducativa(new ExperienciaEducativa());
                problematica.setProfesor(new Profesor());

                problematica.setExperienciaName(experiencia);
                problematica.setProfesorName(profesor);
                problematica.setSolucion(new SolucionAProblematica(0, ""));
                problematicas.add(problematica);

            } while (resultSet.next());
        }
        return problematicas;
    }

    @Override
    public int insertProblematica(ProblematicaAcademica problematica) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        int result = 0;
        Connection connection = dataBaseConnection.getConnection();
        String descripcion = problematica.getDescripcion();
        String titulo = problematica.getTitulo();
        int numeroAfectados = problematica.getNumeroDeEstudiantesAfectados();
        int idReporte = problematica.getIdReporteTutoria();
        String nrc = problematica.getNrc();
        String query;
        query = ("INSERT INTO `sistema_tutorias`.`problematicas_academicas` (`descripcion`, `titulo`, `numeroDeEstudiantesAfectados`, `idReporteTutoria`, `nrc`) VALUES (?,?, ?, ?, ?);");
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, descripcion);
        statement.setString(2, titulo);
        statement.setInt(3, numeroAfectados);
        statement.setInt(4, idReporte);
        statement.setString(5, nrc);

        result = statement.executeUpdate();
        return result;
    }

    @Override
    public int updatetProblematica(ProblematicaAcademica problematica) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        int result = 0;
        Connection connection = dataBaseConnection.getConnection();
        int idProblematica = problematica.getIdProblematica();
        String descripcion = problematica.getDescripcion();
        String titulo = problematica.getTitulo();
        int numeroAfectados = problematica.getNumeroDeEstudiantesAfectados();
        String nrc = problematica.getNrc();
        String query;
        query = ("UPDATE problematicas_academicas SET titulo = ?, descripcion = ?, numeroDeEstudiantesAfectados=?, nrc=? WHERE idProblematica=?;");
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, titulo);
        statement.setString(2, descripcion);
        statement.setInt(3, numeroAfectados);
        statement.setString(4, nrc);
        statement.setInt(5, idProblematica);

        result = statement.executeUpdate();
        return result;

    }

    @Override
    public int deleteProblematica(int idProblematicaAcademica) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        int result = 0;
        Connection connection = dataBaseConnection.getConnection();
        String query;
        query = ("DELETE FROM `sistema_tutorias`.`problematicas_academicas` WHERE (`idProblematica` = ?);");
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idProblematicaAcademica);

        result = statement.executeUpdate();

        return result;

    }
    
    
    public int deleteProblematicafromEspecificReporte(int idReporteTutoria) throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        boolean result = false;
        int resultCode;
        Connection connection = dataBaseConnection.getConnection();   
        try{
            String query;
            query = ("DELETE FROM `sistema_tutorias`.`problematicas_academicas` WHERE (`idReporteTutoria` = ?);");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idReporteTutoria);
            
            int resultInsert = statement.executeUpdate();            
            if(resultInsert>=0){
                resultCode = ExceptionCodes.SUCCESSFUL_OPERATION;
            }else{
                resultCode = ExceptionCodes.FAILED_OPERATION;
            }        
        }catch (SQLException sql){
            resultCode = ExceptionCodes.CONNECTION_ERROR_DATABASE;
        }    
        return resultCode;
    }

    @Override
    public ArrayList<ProblematicaAcademica> getProblematicasByPrograma(String clave) throws SQLException {
        ArrayList<ProblematicaAcademica> problematicas = new ArrayList<ProblematicaAcademica>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = "select pa.*, ee.nombre as experiencia, concat(p.nombre, \" \",p.apellidoPaterno, \" \",p.apellidoMaterno) as profesor, ta.fechaFin as fechaTutoria, pe.fechaInicio, pe.fechaFin, pe.idPeriodoEscolar from problematicas_academicas \n"
                + "pa inner join experiencias_educativas ee on pa.nrc=ee.nrc \n"
                + "inner join profesores p on ee.numeroDePersonal=p.numeroDePersonal \n"
                + "inner join reportes_de_tutorias_academicas rta on pa.idReporteTutoria = rta.idReporteTutoria\n"
                + "inner join tutorias_academicas ta on rta.idTutoriaAcademica=ta.idTutoriaAcademica\n"
                + "inner join periodos_escolares pe on pe.idPeriodoEscolar= ta.idPeriodoEscolar\n"
                + "where rta.clave=?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, clave);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int idProblematica = 0;
            String descripcion;
            String titulo;
            int numeroAfectados = 0;
            int idReporteTutoria = 0;
            String nrc;
            String experiencia;
            String profesor;
            String  fecha;
            do {
                idProblematica = resultSet.getInt("idProblematica");
                descripcion = resultSet.getString("descripcion");
                titulo = resultSet.getString("titulo");
                numeroAfectados = resultSet.getInt("numeroDeEstudiantesAfectados");
                idReporteTutoria = resultSet.getInt("idReporteTutoria");
                nrc = resultSet.getString("nrc");
                experiencia = resultSet.getString("experiencia");
                profesor = resultSet.getString("profesor");
                fecha = resultSet.getDate("fechaTutoria").toString() ;

                ProblematicaAcademica problematica = new ProblematicaAcademica();
                Profesor profesorDomain = new Profesor();
                ExperienciaEducativa experienciaEducativa = new ExperienciaEducativa();
                experienciaEducativa.setNombre(experiencia);
                experienciaEducativa.setNrc(nrc);
                PeriodoEscolar periodoEscolar = new PeriodoEscolar();
                periodoEscolar.setIdPeriodoEscolar(resultSet.getInt("idPeriodoEscolar"));
                periodoEscolar.setFechaInicio(resultSet.getDate("fechaInicio"));

                periodoEscolar.setFechaInicio(resultSet.getDate("fechaFin"));
                problematica.setIdProblematica(idProblematica);
                problematica.setDescripcion(descripcion);
                problematica.setTitulo(titulo);
                problematica.setNumeroDeEstudiantesAfectados(numeroAfectados);
                problematica.setIdReporteTutoria(idReporteTutoria);
                problematica.setNrc(nrc);
                problematica.setExperienciaEducativa(experienciaEducativa);
                problematica.setPeriodoEscolar(periodoEscolar);

                problematica.setProfesor(profesorDomain);

                problematica.setExperienciaName(experiencia);
                problematica.setProfesorName(profesor);
                problematica.setFechaTutoria(fecha);
                problematica.setSolucion(new SolucionAProblematica(0, ""));

                problematicas.add(problematica);

            } while (resultSet.next());
        }
        return problematicas;
    }

    public ArrayList<ProblematicaAcademica> getAllProblematicasByReporte(int idReporteTutoria) throws SQLException {
        ArrayList<ProblematicaAcademica> listProblematicaAcademica = new ArrayList<>();
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
                ProblematicaAcademica problematicaAcademica = new ProblematicaAcademica();
                Profesor profesor = new Profesor();
                ExperienciaEducativa experienciaEducativa = new ExperienciaEducativa();
                experienciaEducativa.setNombre(resultSet.getString("nombreExperiencia"));
                experienciaEducativa.setNrc(resultSet.getString("nrc"));
                profesor.setNombre(resultSet.getString("nombre"));
                profesor.setApellidoPaterno(resultSet.getString("apellidoPaterno"));
                profesor.setApellidoMaterno(resultSet.getString("apellidoMaterno"));
                problematicaAcademica.setProfesor(profesor);
                problematicaAcademica.setExperienciaEducativa(experienciaEducativa);
                problematicaAcademica.setSolucion(new SolucionAProblematica(0, ""));
                problematicaAcademica.setDescripcion(resultSet.getString("descripcion"));
                problematicaAcademica.setNumeroDeEstudiantesAfectados(resultSet.getInt("numeroDeEstudiantesAfectados"));
                listProblematicaAcademica.add(problematicaAcademica);
            }
        }
        connection.close();
        return listProblematicaAcademica;
    }

    @Override
    public ArrayList<ProblematicaReporteTutoria> getAllProblematicas() throws SQLException {
        ArrayList<ProblematicaReporteTutoria> listProblematicasAcademicas = new ArrayList<>();

        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if (connection != null) {
            String consulta = "SELECT problematicas_academicas.idProblematica,\n"
                    + "problematicas_academicas.descripcion,\n"
                    + "titulo,numeroDeEstudiantesAfectados,\n"
                    + "problematicas_academicas.idReporteTutoria,\n"
                    + "soluciones_a_problematicas_academicas.idSolucion,\n"
                    + "soluciones_a_problematicas_academicas.descripcion AS descripcion_solucion,\n"
                    + "tutorias_academicas.idPeriodoEscolar\n"
                    + "FROM problematicas_academicas \n"
                    + "INNER JOIN soluciones_a_problematicas_academicas\n"
                    + "ON problematicas_academicas.idProblematica = soluciones_a_problematicas_academicas.idProblematica\n"
                    + "INNER JOIN reportes_de_tutorias_academicas\n"
                    + "ON reportes_de_tutorias_academicas.idReporteTutoria = problematicas_academicas.idReporteTutoria\n"
                    + "INNER JOIN tutorias_academicas\n"
                    + "ON tutorias_academicas.idTutoriaAcademica = reportes_de_tutorias_academicas.idTutoriaAcademica;";
            PreparedStatement statement = connection.prepareStatement(consulta);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ProblematicaReporteTutoria problematicaAcademicaTemp = new ProblematicaReporteTutoria();
                problematicaAcademicaTemp.getProblematica().setIdProblematica(resultSet.getInt("idProblematica"));
                problematicaAcademicaTemp.getProblematica().setNumeroDeEstudiantesAfectados(resultSet.getInt("numeroDeEstudiantesAfectados"));
                problematicaAcademicaTemp.getProblematica().setDescripcion(resultSet.getString("descripcion"));
                problematicaAcademicaTemp.getProblematica().setIdReporteTutoria(resultSet.getInt("idReporteTutoria"));
                problematicaAcademicaTemp.getProblematica().setSolucion(new SolucionAProblematica(
                        resultSet.getInt("idSolucion"),
                        resultSet.getString("descripcion_solucion")
                ));
                problematicaAcademicaTemp.setTutoria(new TutoriaAcademica(
                        0, null, null, 0,
                        new PeriodoEscolar(
                                "", null, null,
                                resultSet.getInt("idPeriodoEscolar")
                        )
                ));
                listProblematicasAcademicas.add(problematicaAcademicaTemp);
            }
            connection.close();
        }

        return listProblematicasAcademicas;
    }
}
