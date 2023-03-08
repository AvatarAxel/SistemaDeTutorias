/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package sistemadetutorias;

import dataaccess.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author michikato
 */
public class SistemaDeTutorias extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/FXMLLogin.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setResizable(false);
        stage.getIcons().add(new Image("images/icon.png"));
        stage.setTitle("Inicio de Sesión");
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();
        
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection=dataBaseConnection.getConnection();
        System.out.println("S" + connection.toString());
        /*String query="SELECT * from usuario;";
        PreparedStatement statement=connection.prepareStatement(query);
        ResultSet resultSet=statement.executeQuery();
        System.out.println(resultSet.getString("nombre"));*/
      /*  if (!resultSet.next()){
                throw new SQLException("No se encontraron experiencias");
           
        }else{System.out.println("Hay algo");    }*/
        
      
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
