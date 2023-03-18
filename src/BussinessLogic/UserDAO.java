package BussinessLogic;

import Domain.Usuario;
import dataaccess.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Valea
 */
public class UserDAO implements IUserDAO{

    @Override
    public Usuario getUserDB(String correo, String contrasena) throws SQLException {
        Usuario user= new Usuario();
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        String query = ("SELECT U.cuentauv, U.contrasena, U.Nombre, U.ApellidoPaterno, U.ApellidoMaterno, UR.IdRol FROM usuarios U "
                + "inner join usuariosroles UR on UR.CuentaUV = U.cuentauv "
                + "where U.cuentauv = ? and U.contrasena = ? ");
        PreparedStatement statement = connection.prepareStatement(query);
        //statement.setString(1, cuentauv);
       // statement.setString(2, sha512.getSHA512(contrasena));
        ResultSet resultSet = statement.executeQuery();
        
        return user;
    }
    
    
    
    
}
