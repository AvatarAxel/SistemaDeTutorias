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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import singleton.User;
import util.Alerts;

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
    @FXML
    private Label lbEmail;
    @FXML
    private Label lbPassword;

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
            Scene menuScene = new Scene(FXMLLoader.load(getClass().getResource("/GUI/FXMLMainMenu.fxml")));
            primaryStage.setScene(menuScene);
            primaryStage.setTitle("Menu");
            primaryStage.show();
        } catch (IOException e) {
            Alerts.showAlert("Error", "No se puede cargar el menu", Alert.AlertType.ERROR);
        }
    }
    
    public boolean AreFieldsValid(){
        lbEmail.setText("");
        lbPassword.setText("");
        
        boolean validos = true;
        String MESSAGE = "Campo obligatorio";
        
        if(StringUtils.isBlank(tfEmail.getText())){
            validos = false;
            lbEmail.setText(MESSAGE);
        }
        
        if(StringUtils.isBlank(pfPassword.getText())){
            validos = false;
            lbPassword.setText(MESSAGE);
        }
        
        return validos;
    }
    
    private void doLogin(String username, String password){
        //Conexión con la base de datos para iniciar sesión
        if(true){//Reemplazar el true por el resultado de la consulta
            goMenuWindow();
        }else{
            Alerts.showAlert("Prueba", "Prueba", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void clicLogin(ActionEvent event) {
        if(AreFieldsValid()){
            doLogin(tfEmail.getText(), pfPassword.getText());
        }
    }
}
