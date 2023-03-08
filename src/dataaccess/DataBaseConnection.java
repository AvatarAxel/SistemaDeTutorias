/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccess;

import LogerDataBase.GetUserDB;
import LogerDataBase.UserDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Valea
 */
public class DataBaseConnection {
     private Connection connection;
   // private final String DB="jdbc:mysql://localhost/sistematutoriasfei";
   //  private final String DB="jdbc:mysql://sistema-tutorias.mysql.database.azure.com:3306/sistematutoriasfei?useSSL=false";
     private final String DB="jdbc:mysql://system-fei-tutor.mysql.database.azure.com:3306/sistema_tutorias";

    private UserDB usuarioDB;
    GetUserDB obtenerUsuarioDB = new GetUserDB();

    public Connection getConnection() throws SQLException{
        connect();
        return connection;
    }

    private void connect() throws SQLException{
        usuarioDB = obtenerUsuarioDB.getUser();
        connection=DriverManager.getConnection(DB,usuarioDB.getUser(),usuarioDB.getPassword());
        System.out.println("C" + usuarioDB.getUser());
        System.out.println("C" + usuarioDB.getPassword());
    }

    public void closeConection(){
        if(connection!=null){
            try {
                if(!connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
