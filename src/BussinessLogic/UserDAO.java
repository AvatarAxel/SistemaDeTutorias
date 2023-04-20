package BussinessLogic;

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
    public Usuario getUserDB(String correo, String contrasena) throws SQLException {
        Usuario user = new Usuario();
        SHA_512 sha512 = new SHA_512();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = ("SELECT U.cuentauv, U.contrasena, U.Nombre, U.ApellidoPaterno, U.ApellidoMaterno, UR.IdRol FROM usuarios U "
                + "inner join usuariosroles UR on UR.CuentaUV = U.cuentauv "
                + "where U.cuentauv = ? and U.contrasena = ? ");
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, correo);
        statement.setString(2, sha512.getSHA512(contrasena));
        ResultSet resultSet = statement.executeQuery();
        user.setApellidoMaterno(resultSet.getString(""));
        user.setApellidoPaterno(resultSet.getString(""));
        user.setCorreo(resultSet.getString(""));
        user.setNombre(resultSet.getString(""));
        user.setNumeroPersonal(resultSet.getInt(""));
        user.setProgramaEducativo(resultSet.getString(""));
        
        dataBaseConnection.closeConection();
        
        return user;
    }
    
    public ArrayList<Rol> getUserRoles(String correo, String contrasena) throws SQLException {
        ArrayList<Rol> roles = new ArrayList<>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = ("SELECT UR.IdRol FROM usuarios U "
                + "inner join usuariosroles UR on UR.CuentaUV = U.cuentauv ");
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            roles.add(new Rol(resultSet.getInt(""),resultSet.getString("")));
        }
        
        dataBaseConnection.closeConection();
        
        return roles;
    }

    public static ArrayList<Usuario> getUsuarioTutoresporProgramaEducativo(int clave) throws SQLException {
        ArrayList<Usuario> tutores = new ArrayList<>();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        if (connection != null) {
            String consulta = "SELECT u.nombre, u.apellidoPaterno, u.apellidoMaterno, u.numeroDePersonal FROM usuarios u\n"
                    + "INNER JOIN programas_educativos_usuarios pu ON u.numeroDePersonal = pu.numeroDePersonal\n"
                    + "INNER JOIN programas_educativos p ON p.clave = pu.clave\n"
                    + "INNER JOIN roles_usuarios ru ON u.numeroDePersonal = ru.numeroDePersonal\n"
                    + "INNER JOIN roles r ON r.idRol = ru.idRol\n"
                    + "WHERE r.idRol= 3 AND p.clave =?";
            PreparedStatement configurarConsulta = connection.prepareStatement(consulta);
            configurarConsulta.setInt(1, clave);
            ResultSet resultado = configurarConsulta.executeQuery();
            while (resultado.next()) {
                Usuario usuario = new Usuario();
                usuario.setNombre(resultado.getString("nombre"));
                usuario.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                usuario.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                usuario.setNumeroPersonal(resultado.getInt("numeroDePersonal"));
                tutores.add(usuario);
            }
            connection.close();
        }
        return tutores;
    }

    public boolean setRolUserTutor(int numeroDePersonal) throws SQLException {
        boolean result = false;
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();

        if (connection != null) {
            String query = ("INSERT INTO roles_usuarios (`numeroDePersonal`, `idRol`) VALUES (?, '3');");
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

}
