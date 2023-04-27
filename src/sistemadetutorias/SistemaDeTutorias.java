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
    /* public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/FXMLGestionarProblematicas.fxml"));
      // Parent root = FXMLLoader.load(getClass().getResource("/GUI/FXMLConsultarProblematicasAcademicas.fxml"));
        Scene scene = new Scene(root);  
        stage.setResizable(true);
        stage.getIcons().add(new Image("images/icon.png"));
       // stage.setTitle("Gestión de Problemáticas Académicas");
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();
    }*/
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/FXMLConsultarReporteGeneralDeTutoriasAcademicas.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setResizable(true);
        stage.getIcons().add(new Image("images/icon.png"));
        stage.setTitle("Inicio de Sesión");
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
