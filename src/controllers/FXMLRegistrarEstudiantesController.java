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
import javafx.scene.control.Button;
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
public class FXMLRegistrarEstudiantesController implements Initializable {

    @FXML
    private TextField textNombre;
    @FXML
    private TextField textApellidoPaterno;
    @FXML
    private TextField textApellidoMaterno;
    @FXML
    private TextField textMatricula;
    
    private int maxLengthNombreOrApellidos = 30;
    private boolean[] validationTextFields = {false, false, false, false, false};
    
    private Pattern validateCharacter = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$");
    private Pattern validateCharacterMatricula = Pattern.compile("^[sS]\\d{8}$");
    @FXML
    private Button buttonGuardar;
    @FXML
    private Label labelInvalidateNombre;
    @FXML
    private Label labelInvalidateApellidoPaterno;
    @FXML
    private Label labelInvalidateApellidoMaterno;
    @FXML
    private Label labelInvalidateMatricula;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void buttonSave(ActionEvent event) {
        if (!validationTextFields[0] || !validationTextFields[1]
                || !validationTextFields[2] || !validationTextFields[3]) {
            Alerts.showAlert("NO", "No se puede registrar", Alert.AlertType.INFORMATION);
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

    private void closeWindow() {
        Stage escenario = (Stage) textMatricula.getScene().getWindow();
        escenario.close();
    }

    @FXML
    private void validateLengthNombre(KeyEvent event) {
        textNombre.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 30) {                
                return change;
            } else {                
                return null;
            }
        }));
    }

    @FXML
    private void validateLengthApellidoPaterno(KeyEvent event) {
        textApellidoPaterno.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 20) {
                return change;
            } else {
                return null;
            }
        }));        
    }

    @FXML
    private void validateLengthApellidoMaterno(KeyEvent event) {
        textApellidoMaterno.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 20) {
                return change;
            } else {
                return null;
            }
        }));        
    }

    @FXML
    private void validateLengthMatricula(KeyEvent event) {
        textMatricula.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 9) {
                return change;
            } else {
                return null;
            }
        }));
    }

    @FXML
    private void validateInputsNombre(KeyEvent event) {
        Matcher matcher = validateCharacter.matcher(textNombre.getText());
        if (!matcher.matches()) {
            labelInvalidateNombre.setText("Datos invalidos");
            validationTextFields[0] = false;
        } else {
            labelInvalidateNombre.setText("");
            validationTextFields[0] = true;
        }           
    }

    @FXML
    private void validateInputsApellidoPaterno(KeyEvent event) {
        Matcher matcher = validateCharacter.matcher(textApellidoPaterno.getText());
        if (!matcher.matches()) {
            labelInvalidateApellidoPaterno.setText("Datos invalidos");
            validationTextFields[1] = false;
        } else {
            labelInvalidateApellidoPaterno.setText("");
            validationTextFields[1] = true;
        }        
    }

    @FXML
    private void validateInputsApellidoMaterno(KeyEvent event) {
        Matcher matcher = validateCharacter.matcher(textApellidoMaterno.getText());
        if (!matcher.matches()) {
            labelInvalidateApellidoMaterno.setText("Datos invalidos");
            validationTextFields[2] = false;
        } else {
            labelInvalidateApellidoMaterno.setText("");
            validationTextFields[2] = true;
        }        
    }

    @FXML
    private void validateInputsMatricula(KeyEvent event) {
        Matcher matcher = validateCharacterMatricula.matcher(textMatricula.getText());
        if (!matcher.matches()) {
            labelInvalidateMatricula.setText("Datos invalidos");
            validationTextFields[3] = false;
        } else {
            labelInvalidateMatricula.setText("");
            validationTextFields[3] = true;
        }
    }
    
}
