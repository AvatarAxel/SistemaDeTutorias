/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import util.Alerts;

/**
 * FXML Controller class
 *
 * @author michikato
 */
public class FXMLRegistrarTutorAcademicoController implements Initializable {

    @FXML
    private Label labelNombre;
    @FXML
    private Label labelApellidoPaterno;
    @FXML
    private Label labelApellidoMaterno;
    @FXML
    private Label labelNumeroDePersonal;
    @FXML
    
    private TextField textFieldSearchProfesores;
    private boolean validateFieldSearchField = false;    
    private Pattern validateCharacterNumeroPersonal = Pattern.compile("^[1-9]$");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void buttonSearch(ActionEvent event) {
        if (!validateFieldSearchField) {
            Alerts.showAlert("No", "No se puede registrar", Alert.AlertType.INFORMATION);
        } else {
            Alerts.showAlert("Si", "Si se puede registrar", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void buttonCancel(ActionEvent event) {
        Optional<ButtonType> repuestaDialogo = Alerts.showAlert("Apoco si paa", "Tons", Alert.AlertType.CONFIRMATION);
        if (repuestaDialogo.get() == ButtonType.OK) {
            closeWindow();
        }
    }

    @FXML
    private void buttonRegister(ActionEvent event) {
    }

    @FXML
    private void validateInsputsNumeroDePersonal(KeyEvent event) {
        Matcher matcher = validateCharacterNumeroPersonal.matcher(textFieldSearchProfesores.getText());
        if (!matcher.matches()) {
            labelNumeroDePersonal.setText("Datos invalidos");
            validateFieldSearchField = false;
        } else {
            labelNumeroDePersonal.setText("");
            validateFieldSearchField = true;
        }
    }

    @FXML
    private void validateLengthNumeroDePersonal(KeyEvent event) {
                textFieldSearchProfesores.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 5) {
                return change;
            } else {
                return null;
            }
        }));      
    }    
    
    private void closeWindow() {
        Stage escenario = (Stage) labelApellidoMaterno.getScene().getWindow();
        escenario.close();
    }
}
