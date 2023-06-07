package BussinessLogic;

import Domain.PeriodoEscolar;
import Domain.TutoriaAcademica;
import dataaccess.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author michikato
 */
public class TutoriaAcademicaDAO implements ITutoriaAcademicaDAO {

    public ArrayList<TutoriaAcademica> getTutoriasAcademicas() throws SQLException {
        ArrayList<TutoriaAcademica> listTutoriasAcademicas = new ArrayList<>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if (connection != null) {
            String query = ("SELECT * FROM tutorias_academicas");
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                TutoriaAcademica tutoriaAcademica = new TutoriaAcademica();
                tutoriaAcademica.setIdTutoriaAcademica(resultSet.getInt("idTutoriaAcademica"));
                tutoriaAcademica.setNumeroDeSesion(resultSet.getInt("numeroDeSesion"));
                tutoriaAcademica.setFechaInicio(resultSet.getDate("fechaInicio"));
                tutoriaAcademica.setFechaFin(resultSet.getDate("fechaFin"));
                listTutoriasAcademicas.add(tutoriaAcademica);
            }
        }
        connection.close();
        return listTutoriasAcademicas;
    }

    public ArrayList<TutoriaAcademica> getTutoriasAcademicasByTutorAcademico(int numeroPersonal, String clave) throws SQLException {
        ArrayList<TutoriaAcademica> listTutoriasAcademicas = new ArrayList<>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if (connection != null) {
            String query = ("SELECT * FROM tutorias_academicas ta\n"
                    + "INNER JOIN reportes_de_tutorias_academicas rta ON rta.idTutoriaAcademica = ta.idTutoriaAcademica\n"
                    + "WHERE rta.numeroDePersonal = ? AND rta.clave= ?;");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, numeroPersonal);
            statement.setString(2, clave);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                TutoriaAcademica tutoriaAcademica = new TutoriaAcademica();
                tutoriaAcademica.setIdTutoriaAcademica(resultSet.getInt("idTutoriaAcademica"));
                tutoriaAcademica.setNumeroDeSesion(resultSet.getInt("numeroDeSesion"));
                tutoriaAcademica.setFechaInicio(resultSet.getDate("fechaInicio"));
                tutoriaAcademica.setFechaFin(resultSet.getDate("fechaFin"));
                listTutoriasAcademicas.add(tutoriaAcademica);
            }
        }
        connection.close();
        return listTutoriasAcademicas;
    }

    public TutoriaAcademica getCurrentlyTutoriaAcademica() throws SQLException {
        TutoriaAcademica tutoriaAcademica = new TutoriaAcademica();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if (connection != null) {
            String query = "SELECT * FROM periodos_escolares pe\n"
                    + "WHERE (NOW() BETWEEN pe.fechaInicio AND pe.fechaFin);";
            PreparedStatement statementPeriodoEscolar = connection.prepareStatement(query);
            ResultSet resultPeriodoEscolar = statementPeriodoEscolar.executeQuery();
            if (resultPeriodoEscolar.next()) {
                PeriodoEscolar periodoEscolar = new PeriodoEscolar();
                periodoEscolar.setIdPeriodoEscolar(resultPeriodoEscolar.getInt("idPeriodoEscolar"));
                periodoEscolar.setFechaInicio(resultPeriodoEscolar.getDate("fechaInicio"));
                periodoEscolar.setFechaFin(resultPeriodoEscolar.getDate("fechaFin"));
                String queryTutoriaAcademica = "SELECT idTutoriaAcademica, numeroDeSesion, fechaInicio, fechaFin,"
                        + "idPeriodoEscolar, DATE_ADD(fechaFin, INTERVAL 5 DAY) AS fechaLimite\n"
                        + "FROM tutorias_academicas\n"
                        + "WHERE NOW() BETWEEN fechaInicio AND DATE_ADD(fechaFin, INTERVAL 7 DAY);";
                PreparedStatement statementTutoriaAcademica = connection.prepareStatement(queryTutoriaAcademica);
                ResultSet resultTutoriaAcademica = statementTutoriaAcademica.executeQuery();
                if (resultTutoriaAcademica.next()) {
                    tutoriaAcademica.setIdTutoriaAcademica(resultTutoriaAcademica.getInt("idTutoriaAcademica"));
                    tutoriaAcademica.setNumeroDeSesion(resultTutoriaAcademica.getInt("numeroDeSesion"));
                    tutoriaAcademica.setFechaInicio(resultTutoriaAcademica.getDate("fechaInicio"));
                    tutoriaAcademica.setFechaFin(resultTutoriaAcademica.getDate("fechaFin"));
                    tutoriaAcademica.setFechaCierreEntregaReporte(resultTutoriaAcademica.getDate("fechaLimite"));
                    tutoriaAcademica.setPeriodoEscolar(periodoEscolar);
                } else {
                    tutoriaAcademica = null;
                }
            } else {
                tutoriaAcademica = null;
            }
            connection.close();
        }
        return tutoriaAcademica;
    }

    public TutoriaAcademica getCurrentTutoriaAcademica(String claveProgramaEducativo) throws SQLException {
        TutoriaAcademica tutoriaAcademica = new TutoriaAcademica();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if (connection != null) {
            String query = "SELECT * FROM periodos_escolares pe "
                    + "where"
                    + " pe.idPeriodoEscolar=(Select MAX(pe.idPeriodoEscolar) from periodos_escolares);";
            PreparedStatement statementPeriodoEscolar = connection.prepareStatement(query);
            ResultSet resultPeriodoEscolar = statementPeriodoEscolar.executeQuery();
            if (resultPeriodoEscolar.next()) {
                PeriodoEscolar periodoEscolar = new PeriodoEscolar();
                periodoEscolar.setIdPeriodoEscolar(resultPeriodoEscolar.getInt("idPeriodoEscolar"));
                periodoEscolar.setFechaInicio(resultPeriodoEscolar.getDate("fechaInicio"));
                periodoEscolar.setFechaFin(resultPeriodoEscolar.getDate("fechaFin"));
                String queryTutoriaAcademica = "select * from tutorias_academicas where idtutoriaacademica =(select max(idtutoriaacademica) "
                        + "from tutorias_academicas where clave=?) AND clave=?;";
                PreparedStatement statementTutoriaAcademica = connection.prepareStatement(queryTutoriaAcademica);
                statementTutoriaAcademica.setString(1, claveProgramaEducativo);
                statementTutoriaAcademica.setString(2, claveProgramaEducativo);

                ResultSet resultTutoriaAcademica = statementTutoriaAcademica.executeQuery();
                if (resultTutoriaAcademica.next()) {
                    tutoriaAcademica.setIdTutoriaAcademica(resultTutoriaAcademica.getInt("idTutoriaAcademica"));
                    tutoriaAcademica.setNumeroDeSesion(resultTutoriaAcademica.getInt("numeroDeSesion"));
                    tutoriaAcademica.setFechaInicio(resultTutoriaAcademica.getDate("fechaInicio"));
                    tutoriaAcademica.setFechaFin(resultTutoriaAcademica.getDate("fechaFin"));
                    //tutoriaAcademica.setFechaCierreEntregaReporte(resultTutoriaAcademica.getDate("fechaLimite"));
                    tutoriaAcademica.setPeriodoEscolar(periodoEscolar);
                } else {
                    tutoriaAcademica = null;
                }
            } else {
                tutoriaAcademica = null;
            }
            connection.close();
        }
        return tutoriaAcademica;
    }

    public int addTutoriaAcademica(TutoriaAcademica tutoria) throws SQLException {
        int result = 0;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();

        Connection connection = dataBaseConnection.getConnection();
        String query;
        query = ("INSERT INTO `sistema_tutorias`.`tutorias_academicas` "
                + "(`numeroDeSesion`, `fechaInicio`, `fechaFin`, `idPeriodoEscolar`) "
                + "VALUES (?, ?, ?, ?);");
        if (connection != null) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, tutoria.getNumeroDeSesion());
            statement.setDate(2, tutoria.getFechaInicio());
            statement.setDate(3, tutoria.getFechaFin());
            statement.setInt(4, tutoria.getPeriodoEscolar().getIdPeriodoEscolar());

            result = statement.executeUpdate();
        }
        dataBaseConnection.closeConection();

        return result;

    }

    public int getNumeroSesion(int idPeriodoEscolar, String clave) throws SQLException {
        int numeroSesion = 0;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();

        Connection connection = dataBaseConnection.getConnection();
        String query;

        query = ("select MAX(numeroDeSesion) as numeroDeSesion"
                + " from tutorias_academicas "
                + "where idPeriodoEscolar=? and clave=?;");

        if (connection != null) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idPeriodoEscolar);
            statement.setString(2, clave);
            numeroSesion = statement.executeUpdate();

        }

        return numeroSesion;

    }

    @Override
    public ArrayList<TutoriaAcademica> getTutoriasAcademicasByPeriodo(int idPeriodo, String clave) throws SQLException {
        ArrayList<TutoriaAcademica> tutoriasAcademicas = new ArrayList<>();

        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if (connection != null) {
            String query = ("SELECT TA.idTutoriaAcademica, TA.fechaInicio, TA.fechaFin, TA.numeroDeSesion, TA.idPeriodoEscolar \n"
                    + "FROM tutorias_academicas TA \n"
                    + "INNER JOIN periodos_escolares PE ON TA.idPeriodoEscolar = PE.idPeriodoEscolar \n"
                    + "WHERE PE.idPeriodoEscolar = ?;");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idPeriodo);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                TutoriaAcademica tutoriaAcademica = new TutoriaAcademica();
                tutoriaAcademica.setIdTutoriaAcademica(resultSet.getInt("idTutoriaAcademica"));
                tutoriaAcademica.setFechaInicio(resultSet.getDate("fechaInicio"));
                tutoriaAcademica.setFechaFin(resultSet.getDate("fechaFin"));
                tutoriaAcademica.setNumeroDeSesion(resultSet.getInt("numeroDeSesion"));
                tutoriaAcademica.setPeriodoEscolar(new PeriodoEscolar(
                        String.valueOf(resultSet.getInt("idPeriodoEscolar")),
                        resultSet.getDate("fechaInicio"),
                        resultSet.getDate("fechaFin"),
                        idPeriodo));
                tutoriasAcademicas.add(tutoriaAcademica);
            }
            dataBaseConnection.closeConection();
        }
        return tutoriasAcademicas;
    }

    @Override
    public boolean updateFechasTutoriaAcademica(TutoriaAcademica tutoriaAcademica) throws SQLException {
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if (connection != null) {
            String query = "UPDATE tutorias_academicas "
                    + "SET fechaInicio = ?, fechaFin = ? WHERE idTutoriaAcademica = ?;";
            PreparedStatement statementSet = connection.prepareStatement(query);
            statementSet.setDate(1, tutoriaAcademica.getFechaInicio());
            statementSet.setDate(2, tutoriaAcademica.getFechaFin());
            statementSet.setInt(3, tutoriaAcademica.getIdTutoriaAcademica());
            int filasAfectadas = statementSet.executeUpdate();
            result = (filasAfectadas == 1);
        }
        dataBaseConnection.closeConection();
        return result;
    }

    public TutoriaAcademica getLastTutoriaAcademica() throws SQLException {
        TutoriaAcademica tutoriaAcademica = new TutoriaAcademica();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String queryPeriodo = "SELECT * FROM periodos_escolares pe "
                + "where"
                + " pe.idPeriodoEscolar=(Select MAX(pe.idPeriodoEscolar) from periodos_escolares);";
        PreparedStatement statementPeriodoEscolar = connection.prepareStatement(queryPeriodo);
        ResultSet resultPeriodoEscolar = statementPeriodoEscolar.executeQuery();
        if (resultPeriodoEscolar.next()) {
            PeriodoEscolar periodoEscolar = new PeriodoEscolar();
            periodoEscolar.setIdPeriodoEscolar(resultPeriodoEscolar.getInt("idPeriodoEscolar"));
            periodoEscolar.setFechaInicio(resultPeriodoEscolar.getDate("fechaInicio"));
            periodoEscolar.setFechaFin(resultPeriodoEscolar.getDate("fechaFin"));
            String query = ("SELECT * FROM tutorias_academicas TA WHERE TA.fechaFin=(Select max(fechaFin)"
                    + " from tutorias_academicas where idPeriodoEscolar=?) ;");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, periodoEscolar.getIdPeriodoEscolar());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                periodoEscolar.setIdPeriodoEscolar(resultSet.getInt("idPeriodoEscolar"));
                tutoriaAcademica.setIdTutoriaAcademica(resultSet.getInt("idTutoriaAcademica"));
                tutoriaAcademica.setNumeroDeSesion(resultSet.getInt("numeroDeSesion"));
                tutoriaAcademica.setFechaInicio(resultSet.getDate("fechaInicio"));
                tutoriaAcademica.setFechaFin(resultSet.getDate("fechaFin"));
                tutoriaAcademica.setPeriodoEscolar(periodoEscolar);
            }

            dataBaseConnection.closeConection();

        }
        return tutoriaAcademica;

    }
}
