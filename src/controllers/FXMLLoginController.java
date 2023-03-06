/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author panther
 */
public class FXMLLoginController implements Initializable {

    @FXML
    private TextField tfEmail;
    @FXML
    private PasswordField pfPassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    private void goMenuWindow(){
        try{
        Stage primaryStage = (Stage) tfEmail.getScene().getWindow();
        Scene menuScene = new Scene(FXMLLoader.load(getClass().getResource("FXMLMenuCUs.fxml")));
        primaryStage.setScene(menuScene);
        primaryStage.setTitle("Menu");
        primaryStage.show();
        } catch (IOException e) {
            //Utilidades.mostrarAlertaConfirmacion("Error", "No se puede cargar el menu", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void clicLogin(ActionEvent event) {
        
    }
}
