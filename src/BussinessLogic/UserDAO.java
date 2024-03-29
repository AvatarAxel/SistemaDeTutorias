package BussinessLogic;

import Domain.ProgramaEducativo;
import Domain.Rol;
import Domain.Usuario;
import dataaccess.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import security.SHA_512;

/**
 *
 * @author Valea
 */
public class UserDAO implements IUserDAO {
    @Override
    public Usuario getUser(String correo, String contrasena) throws SQLException {
        Usuario user = new Usuario();
        SHA_512 sha512 = new SHA_512();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if (connection != null) {
            String query = ("SELECT U.correoElectronicoInstitucional, U.Nombre, U.ApellidoPaterno, U.ApellidoMaterno, U.numeroDePersonal "
                    + "FROM usuarios U "
                    + "WHERE U.correoElectronicoInstitucional = ? "
                    + "AND U.contrasenia = ? and esRegistrado = 1;");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, correo);
            statement.setString(2, sha512.getSHA512(contrasena));

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                user.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                user.setCorreoElectronicoInstitucional(resultSet.getString("correoElectronicoInstitucional"));
                user.setNombre(resultSet.getString("Nombre"));
                user.setNumeroDePersonal(resultSet.getInt("numeroDePersonal"));
            } else {
                user = null;
            }
            dataBaseConnection.closeConection();
        }
        return user;
    }

    @Override
    public ArrayList<Rol> getUserRoles(int numeroDePersonal) throws SQLException {
        ArrayList<Rol> roles = new ArrayList<>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = ("SELECT DISTINCT UR.IdRol, R.nombre AS nombreRol, UR.clave, PE.nombre AS nombrePrograma FROM usuarios U\n" +
        "INNER JOIN roles_usuarios_programa_educativo UR ON UR.numeroDePersonal = ? \n" +
        "INNER JOIN programas_educativos PE ON PE.clave = UR.clave \n" +
        "INNER JOIN roles R ON R.idRol = UR.idRol\n" +
        "WHERE PE.activo = 1;");
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, numeroDePersonal);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Rol rolTmp = new Rol();
            rolTmp.setIdRol(resultSet.getInt("IdRol"));
            rolTmp.setRolName(resultSet.getString("nombreRol"));
            rolTmp.setProgramaEducativo(new ProgramaEducativo(resultSet.getString("clave"), 
                    resultSet.getString("nombrePrograma")));
            roles.add(rolTmp);
        }

        dataBaseConnection.closeConection();

        return roles;
    }

    public static ArrayList<Usuario> getUsuarioTutoresporProgramaEducativos(int clave) throws SQLException {
        ArrayList<Usuario> tutores = new ArrayList<>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if (connection != null) {
            String consulta = "SELECT u.nombre, u.apellidoPaterno, u.apellidoMaterno, u.numeroDePersonal FROM usuarios u\n" +
            "INNER JOIN roles_usuarios_programa_educativo rup ON rup.numeroDePersonal = u.numeroDePersonal\n" +
            "INNER JOIN programas_educativos p ON p.clave = rup.clave\n" +
            "INNER JOIN reportes_de_tutorias_academicas rta ON rta.numeroDePersonal = rup.numeroDePersonal\n" +
            "WHERE rup.idRol= 3 AND rup.clave =?";
            PreparedStatement configurarConsulta = connection.prepareStatement(consulta);
            configurarConsulta.setInt(1, clave);
            ResultSet resultado = configurarConsulta.executeQuery();
            while (resultado.next()) {
                Usuario usuario = new Usuario();
                usuario.setNombre(resultado.getString("nombre"));
                usuario.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                usuario.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                usuario.setNumeroDePersonal(resultado.getInt("numeroDePersonal"));
                tutores.add(usuario);
            }
            connection.close();
        }
        return tutores;
    }

    public boolean setRolUserTutor(int numeroDePersonal, String clave) throws SQLException {
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if (connection != null) {
            String query = ("INSERT INTO roles_usuarios_programa_educativo (`numeroDePersonal`, `idRol`, `clave`) VALUES (?, '3', ?);");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, numeroDePersonal);
            statement.setString(2, clave);
            int resultInsert = statement.executeUpdate();
            if (resultInsert > 0) {
                result = true;
            }
        }
        connection.close();
        return result;
    }

    public ArrayList<Usuario> getAllUsersByProgramaEducativo(String clave) throws SQLException {
        ArrayList<Usuario> listUsuario = new ArrayList<>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if (connection != null) {
            String query = "SELECT DISTINCT u.numeroDePersonal, u.nombre, u.apellidoPaterno, u.apellidoMaterno, u.correoElectronicoInstitucional\n"
                    + "FROM usuarios u\n"
                    + "INNER JOIN roles_usuarios_programa_educativo r ON u.numeroDePersonal = r.numeroDePersonal\n"
                    + "WHERE r.clave = ? AND u.esRegistrado = 1;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, clave);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setNombre(resultSet.getString("nombre"));
                usuario.setApellidoPaterno(resultSet.getString("apellidoPaterno"));
                usuario.setApellidoMaterno(resultSet.getString("apellidoMaterno"));
                usuario.setNumeroDePersonal(resultSet.getInt("numeroDePersonal"));
                usuario.setCorreoElectronicoInstitucional(resultSet.getString("correoElectronicoInstitucional"));
                listUsuario.add(usuario);
            }
            connection.close();
        }
        return listUsuario;
    }

    public ArrayList<Rol> getAllUserRolesByNumeroDePersonal(int numeroDePersonal, String clave) throws SQLException {
        ArrayList<Rol> roles = new ArrayList<>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = ("SELECT r.idRol, r.nombre\n" +
                    "FROM roles_usuarios_programa_educativo up\n" +
                    "INNER JOIN roles r ON up.idRol = r.idRol\n" +
                    "INNER JOIN programas_educativos pe ON up.clave = pe.clave\n" +
                    "WHERE up.numeroDePersonal = ?\n" +
                    "AND up.clave = ?\n" +
                    "AND pe.activo = 1;");
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, numeroDePersonal);
        statement.setString(2, clave);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            ProgramaEducativo programaEducativo = new ProgramaEducativo();
            programaEducativo.setClave(clave);
            roles.add(new Rol(resultSet.getInt("IdRol"), resultSet.getString("nombre"), programaEducativo));
        }
        dataBaseConnection.closeConection();
        return roles;
    }

    public boolean updateUsuario(Usuario usuario) throws SQLException {
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if (connection != null) {
            String query = ("UPDATE `sistema_tutorias`.`usuarios` SET `nombre` = ?, `apellidoPaterno` = ?, `apellidoMaterno` = ?, `correoElectronicoInstitucional` = ? WHERE (`numeroDePersonal` = ?);");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getApellidoPaterno());
            statement.setString(3, usuario.getApellidoMaterno());
            statement.setString(4, usuario.getCorreoElectronicoInstitucional());
            statement.setInt(5, usuario.getNumeroDePersonal());
            int resultInsert = statement.executeUpdate();
            if (resultInsert > 0) {
                result = true;
            }
        }
        connection.close();
        return result;
    }
    
    public boolean deleteRol(int numeroDePersonal, Rol rol) throws SQLException {
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if (connection != null) {
            String query = ("DELETE FROM roles_usuarios_programa_educativo WHERE numeroDePersonal = ? AND idRol = ? AND clave = ?;");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, numeroDePersonal);
            statement.setInt(2, rol.getIdRol());
            statement.setString(3, rol.getProgramaEducativo().getClave());            
            int resultInsert = statement.executeUpdate();
            if (resultInsert > 0) {
                result = true;
            }
        }
        connection.close();
        return result;
    }    
    
    public boolean registerRol(int numeroDePersonal, Rol rol) throws SQLException {
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if (connection != null) {
            String query = ("INSERT INTO roles_usuarios_programa_educativo (numeroDePersonal, clave, idRol) VALUES (?, ?, ?);");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, numeroDePersonal);                                               
            statement.setString(2, rol.getProgramaEducativo().getClave());  
            statement.setInt(3, rol.getIdRol());
            int resultInsert = statement.executeUpdate();
            if (resultInsert > 0) {
                result = true;
            }
        }
        connection.close();
        return result;
    }     
    
    public boolean deleteUsuario(int numeroDePersonal) throws SQLException {
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if (connection != null) {
            String query = ("UPDATE `sistema_tutorias`.`usuarios` SET `esRegistrado` = 0 WHERE (`numeroDePersonal` = ?);");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, numeroDePersonal);
            int resultInsert = statement.executeUpdate();
            if (resultInsert > 0) {
                result = true;
            }
        }
        connection.close();
        return result;
    }

    public boolean existCorreo(String correoElectronicoInstitucional) throws SQLException {
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if (connection != null) {
            String query = "SELECT * FROM usuarios\n"
                    + "WHERE correoElectronicoInstitucional = ?\n"
                    + "AND esRegistrado = 1;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, correoElectronicoInstitucional);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {
                    result = true;
                }
            }
            resultSet.close();
            statement.close();
        }
        connection.close();
        return result;
    }
    
    public boolean updatePassword(String contrasenia, String correoElectronicoInstitucional) throws SQLException {
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if (connection != null) {
            String query = ("UPDATE `sistema_tutorias`.`usuarios` SET `contrasenia` = ? WHERE (`correoElectronicoInstitucional` = ?);");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, contrasenia);
            statement.setString(2, correoElectronicoInstitucional);
            int resultInsert = statement.executeUpdate();
            if (resultInsert > 0) {
                result = true;
            }
        }
        connection.close();
        return result;
    }    

}
