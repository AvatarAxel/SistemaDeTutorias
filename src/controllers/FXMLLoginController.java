/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.StringUtils;
import util.Alerts;
import util.Navigator;

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
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
   
    public boolean AreFieldsValid(){
        lbEmail.setText("");
        lbPassword.setText("");
        
        boolean isValid = true;
        String MESSAGE = "Campo obligatorio";
        
        if(StringUtils.isBlank(tfEmail.getText())){
            isValid = false;
            lbEmail.setText(MESSAGE);
        }
        
        if(StringUtils.isBlank(pfPassword.getText())){
            isValid = false;
            lbPassword.setText(MESSAGE);
        }
        
        return isValid;
    }
    
    private void doLogin(String username, String password){
        //Conexión con la base de datos para iniciar sesión
        if(true){//Reemplazar el true por el resultado de la consulta
            Navigator.NavigateToWindow(tfEmail.getScene().getWindow(), "/GUI/FXMLMainMenu.fxml", "Menú");
        }else{
            Alerts.showAlert("Prueba", "Prueba " + username + " " + password, Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void clicLogin(ActionEvent event) {
        if(AreFieldsValid()){
            doLogin(tfEmail.getText(), pfPassword.getText());
        }
    }
}
