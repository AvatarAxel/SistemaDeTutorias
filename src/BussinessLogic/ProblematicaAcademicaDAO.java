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
        String query = "select pa.*, ee.nombre as experiencia, p.nombre as profesorname ,p.apellidoPaterno, p.apellidoMaterno, epp.idexperiencia_periodo_profesor \n"
                + "from problematicas_academicas  pa \n"
                + "INNER JOIN experiencias_periodos_profesores epp ON epp.idexperiencia_periodo_profesor =pa.idexperiencia_periodo_profesor\n"
                + "inner join experiencias_educativas ee on epp.nrc=ee.nrc \n"
                + "inner join profesores p on epp.numeroDePersonal=p.numeroDePersonal\n"
                + " where pa.idReporteTutoria=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idReporte);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int idProblematica = 0;
            String descripcion;
            String titulo;
            int numeroAfectados = 0;
            int idReporteTutoria = 0;
            int idexperienciaprofesor;
            String experiencia;
            String profesorName;
            String apellidoPaterno;
            String apellidoMaterno;

            do {
                idProblematica = resultSet.getInt("idProblematica");
                descripcion = resultSet.getString("descripcion");
                titulo = resultSet.getString("titulo");
                numeroAfectados = resultSet.getInt("numeroDeEstudiantesAfectados");
                idReporteTutoria = resultSet.getInt("idReporteTutoria");
                idexperienciaprofesor = resultSet.getInt("idexperiencia_periodo_profesor");
                experiencia = resultSet.getString("experiencia");
                profesorName = resultSet.getString("profesorname");
                apellidoPaterno = resultSet.getString("apellidoPaterno");
                apellidoMaterno = resultSet.getString("apellidoMaterno");

                ExperienciaEducativa experienciaEducativa = new ExperienciaEducativa();
                experienciaEducativa.setNombre(experiencia);
                Profesor profesor = new Profesor();
                profesor.setNombre(profesorName);
                profesor.setApellidoPaterno(apellidoPaterno);

                ProblematicaAcademica problematica = new ProblematicaAcademica();
                problematica.setIdProblematica(idProblematica);
                problematica.setDescripcion(descripcion);
                problematica.setTitulo(titulo);
                problematica.setNumeroDeEstudiantesAfectados(numeroAfectados);
                problematica.setIdReporteTutoria(idReporteTutoria);
                problematica.setIdexperienciaProfesor(idexperienciaprofesor);
                problematica.setExperienciaEducativa(experienciaEducativa);
                problematica.setProfesor(profesor);
                problematica.setSolucion(new SolucionAProblematica(0, ""));
                problematica.setPeriodoEscolar(new PeriodoEscolar());
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
        int idexperienciaProfesor = problematica.getIdexperienciaProfesor();
        String query;
        query = ("INSERT INTO `sistema_tutorias`.`problematicas_academicas` (`descripcion`, `titulo`, `numeroDeEstudiantesAfectados`, `idReporteTutoria`, `idexperiencia_periodo_profesor`) VALUES (?,?, ?, ?, ?);");
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, descripcion);
        statement.setString(2, titulo);
        statement.setInt(3, numeroAfectados);
        statement.setInt(4, idReporte);
        statement.setInt(5, idexperienciaProfesor);

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
        int idexperienciaProfesor = problematica.getIdexperienciaProfesor();
        String query;
        query = ("UPDATE problematicas_academicas SET titulo = ?, descripcion = ?, numeroDeEstudiantesAfectados=?, idexperiencia_periodo_profesor=? WHERE idProblematica=?;");
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, titulo);
        statement.setString(2, descripcion);
        statement.setInt(3, numeroAfectados);
        statement.setInt(4, idexperienciaProfesor);
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
        try {
            String query;
            query = ("DELETE FROM `sistema_tutorias`.`problematicas_academicas` WHERE (`idReporteTutoria` = ?);");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idReporteTutoria);

            int resultInsert = statement.executeUpdate();
            if (resultInsert >= 0) {
                resultCode = ExceptionCodes.SUCCESSFUL_OPERATION;
            } else {
                resultCode = ExceptionCodes.FAILED_OPERATION;
            }
        } catch (SQLException sql) {
            resultCode = ExceptionCodes.CONNECTION_ERROR_DATABASE;
        }
        return resultCode;
    }

    @Override
    public ArrayList<ProblematicaAcademica> getProblematicasByPrograma(String clave) throws SQLException {
        ArrayList<ProblematicaAcademica> problematicas = new ArrayList<ProblematicaAcademica>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = "select pa.*, ee.nombre as experiencia, p.nombre,p.apellidoPaterno,p.apellidoMaterno, ta.fechaFin as fechaTutoria, pe.fechaInicio, pe.fechaFin, pe.idPeriodoEscolar\n"
                + "from problematicas_academicas pa\n"
                + "inner join experiencias_periodos_profesores epp on pa.idexperiencia_periodo_profesor=epp.idexperiencia_periodo_profesor\n"
                + "inner join experiencias_educativas ee on ee.nrc = epp.nrc\n"
                + "inner join profesores p on epp.numeroDePersonal=p.numeroDePersonal \n"
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
            String experiencia;
            String profesor;
            String fecha;
            do {
                idProblematica = resultSet.getInt("idProblematica");
                descripcion = resultSet.getString("descripcion");
                titulo = resultSet.getString("titulo");
                numeroAfectados = resultSet.getInt("numeroDeEstudiantesAfectados");
                idReporteTutoria = resultSet.getInt("idReporteTutoria");
                experiencia = resultSet.getString("experiencia");
                fecha = resultSet.getDate("fechaTutoria").toString();

                ProblematicaAcademica problematica = new ProblematicaAcademica();
                Profesor profesorDomain = new Profesor();
                profesorDomain.setNombre(resultSet.getString("nombre"));
                profesorDomain.setApellidoPaterno(resultSet.getString("apellidoPaterno"));
                profesorDomain.setApellidoMaterno(resultSet.getString("apellidoMaterno"));

                ExperienciaEducativa experienciaEducativa = new ExperienciaEducativa();
                experienciaEducativa.setNombre(experiencia);

                PeriodoEscolar periodoEscolar = new PeriodoEscolar();
                periodoEscolar.setIdPeriodoEscolar(resultSet.getInt("idPeriodoEscolar"));
                periodoEscolar.setFechaInicio(resultSet.getDate("fechaInicio"));

                periodoEscolar.setFechaFin(resultSet.getDate("fechaFin"));
                problematica.setIdProblematica(idProblematica);
                problematica.setDescripcion(descripcion);
                problematica.setTitulo(titulo);
                problematica.setNumeroDeEstudiantesAfectados(numeroAfectados);
                problematica.setIdReporteTutoria(idReporteTutoria);
                problematica.setIdexperienciaProfesor(resultSet.getInt("idexperiencia_periodo_profesor"));
                problematica.setExperienciaEducativa(experienciaEducativa);
                problematica.setPeriodoEscolar(periodoEscolar);

                problematica.setProfesor(profesorDomain);

                problematica.setExperienciaName(experiencia);
                problematica.setFechaTutoria(fecha);
                problematica.setSolucion(new SolucionAProblematica(0, ""));

                problematicas.add(problematica);

            } while (resultSet.next());
        }
        return problematicas;
    }

    public ArrayList<ProblematicaAcademica> getProblematicasByProgramaByTutor(String clave, int numeroPersonal) throws SQLException {
        ArrayList<ProblematicaAcademica> problematicas = new ArrayList<ProblematicaAcademica>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = "select pa.*, ee.nombre as experiencia, p.nombre,p.apellidoPaterno,p.apellidoMaterno, ta.fechaFin as fechaTutoria, pe.fechaInicio, pe.fechaFin, pe.idPeriodoEscolar\n"
                + "from problematicas_academicas pa\n"
                + "inner join experiencias_periodos_profesores epp on pa.idexperiencia_periodo_profesor=epp.idexperiencia_periodo_profesor\n"
                + "inner join experiencias_educativas ee on ee.nrc = epp.nrc\n"
                + "inner join profesores p on epp.numeroDePersonal=p.numeroDePersonal \n"
                + "inner join reportes_de_tutorias_academicas rta on pa.idReporteTutoria = rta.idReporteTutoria\n"
                + "inner join tutorias_academicas ta on rta.idTutoriaAcademica=ta.idTutoriaAcademica\n"
                + "inner join periodos_escolares pe on pe.idPeriodoEscolar= ta.idPeriodoEscolar\n"
                + "where rta.clave=? and rta.numeroDePersonal=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, clave);
        statement.setInt(2, numeroPersonal);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int idProblematica = 0;
            String descripcion;
            String titulo;
            int numeroAfectados = 0;
            int idReporteTutoria = 0;
            String experiencia;
            String profesor;
            String fecha;
            do {
                idProblematica = resultSet.getInt("idProblematica");
                descripcion = resultSet.getString("descripcion");
                titulo = resultSet.getString("titulo");
                numeroAfectados = resultSet.getInt("numeroDeEstudiantesAfectados");
                idReporteTutoria = resultSet.getInt("idReporteTutoria");
                experiencia = resultSet.getString("experiencia");
                fecha = resultSet.getDate("fechaTutoria").toString();

                ProblematicaAcademica problematica = new ProblematicaAcademica();
                Profesor profesorDomain = new Profesor();
                profesorDomain.setNombre(resultSet.getString("nombre"));
                profesorDomain.setApellidoPaterno(resultSet.getString("apellidoPaterno"));
                profesorDomain.setApellidoMaterno(resultSet.getString("apellidoMaterno"));

                ExperienciaEducativa experienciaEducativa = new ExperienciaEducativa();
                experienciaEducativa.setNombre(experiencia);

                PeriodoEscolar periodoEscolar = new PeriodoEscolar();
                periodoEscolar.setIdPeriodoEscolar(resultSet.getInt("idPeriodoEscolar"));
                periodoEscolar.setFechaInicio(resultSet.getDate("fechaInicio"));

                periodoEscolar.setFechaFin(resultSet.getDate("fechaFin"));
                problematica.setIdProblematica(idProblematica);
                problematica.setDescripcion(descripcion);
                problematica.setTitulo(titulo);
                problematica.setNumeroDeEstudiantesAfectados(numeroAfectados);
                problematica.setIdReporteTutoria(idReporteTutoria);
                problematica.setIdexperienciaProfesor(resultSet.getInt("idexperiencia_periodo_profesor"));
                problematica.setExperienciaEducativa(experienciaEducativa);
                problematica.setPeriodoEscolar(periodoEscolar);

                problematica.setProfesor(profesorDomain);

                problematica.setExperienciaName(experiencia);
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
            String query = ("SELECT EE.nrc, EE.nombre as nombreExperiencia,  P.nombre, P.apellidoPaterno, P.apellidoMaterno, PA.idProblematica, PA.titulo, PA.numeroDeEstudiantesAfectados, PA.descripcion FROM problematicas_academicas PA\n" +
                "INNER JOIN experiencias_periodos_profesores EPP ON PA.idexperiencia_periodo_profesor = EPP.idexperiencia_periodo_profesor\n" +
                "INNER JOIN experiencias_educativas EE ON EPP.nrc = EE.nrc\n" +
                "INNER JOIN profesores P ON EPP.numeroDePersonal = P.numeroDePersonal\n" +
                "WHERE PA.idReporteTutoria = ?;");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idReporteTutoria);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ProblematicaAcademica problematicaAcademica = new ProblematicaAcademica();
                Profesor profesor = new Profesor();
                ExperienciaEducativa experienciaEducativa = new ExperienciaEducativa();
                experienciaEducativa.setNrc(resultSet.getString("nrc"));                
                experienciaEducativa.setNombre(resultSet.getString("nombreExperiencia"));
                profesor.setNombre(resultSet.getString("nombre"));
                profesor.setApellidoPaterno(resultSet.getString("apellidoPaterno"));
                profesor.setApellidoMaterno(resultSet.getString("apellidoMaterno"));
                problematicaAcademica.setProfesor(profesor);
                problematicaAcademica.setExperienciaEducativa(experienciaEducativa);
                problematicaAcademica.setSolucion(new SolucionAProblematica(0, ""));
                problematicaAcademica.setIdProblematica(resultSet.getInt("idProblematica"));
                problematicaAcademica.setDescripcion(resultSet.getString("descripcion"));
                problematicaAcademica.setTitulo(resultSet.getString("titulo"));
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
