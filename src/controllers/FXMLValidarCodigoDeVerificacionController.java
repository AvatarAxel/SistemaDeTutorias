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
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import util.WindowManager;

/**
 * FXML Controller class
 *
 * @author michikato
 */
public class FXMLValidarCodigoDeVerificacionController implements Initializable {

    @FXML
    private TextField textFieldCodigo;
    @FXML
    private Button buttonVerificar;
    private boolean resultValidation;
    private String verificationCode;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void validateLengthCodigo(KeyEvent event) {
        textFieldCodigo.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 5) {
                return change;
            } else {
                return null;
            }
        }));
        if (textFieldCodigo.getText().length() > 5) {
            textFieldCodigo.setText("");
        }
    }

    @FXML
    private void buttonVerificarAction(ActionEvent event) {
        if (verificationCode == textFieldCodigo.getText()) {
            resultValidation = true;
            Stage escenario = (Stage) textFieldCodigo.getScene().getWindow();
            escenario.close();
        }
    }

    @FXML
    private void buttonCancelar(ActionEvent event) {
        Stage escenario = (Stage) textFieldCodigo.getScene().getWindow();
        escenario.close();
    }

    public void receiveData(boolean resultValidation, String verificationCode) {
        this.resultValidation = resultValidation;
        this.verificationCode = verificationCode;
    }

}
