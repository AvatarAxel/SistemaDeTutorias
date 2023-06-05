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
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author michikato
 */
public class FXMLCambiarContraseniaController implements Initializable {

    @FXML
    private TextField textFieldCorreo;
    @FXML
    private Button buttonEnviarCodigo;
    @FXML
    private PasswordField textFieldContrasenia;
    @FXML
    private PasswordField textFieldConfirmarConstrasenia;
    @FXML
    private Button buttonAceptar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void buttonActionEnviarCodigo(ActionEvent event) {
    }

    @FXML
    private void buttonActionCancelar(ActionEvent event) {
    }

    @FXML
    private void buttonActionAceptar(ActionEvent event) {
    }
    
}
