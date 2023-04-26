/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author michikato
 */
public class FXMLEditarPersonalController implements Initializable {

    @FXML
    private TextField textNombre;
    @FXML
    private TextField textApellidoPaterno;
    @FXML
    private TextField textApellidoMaterno;
    @FXML
    private Label labelInvalidateNombre;
    @FXML
    private Label labelInvalidateApellidoPaterno;
    @FXML
    private Label labelInvalidateApellidoMaterno;
    @FXML
    private Label labelInvalidateMatricula;
    @FXML
    private TextField textCorreoElectronico;
    @FXML
    private CheckBox checkBoxTutor;
    @FXML
    private CheckBox checkBoxJefeDeCarrera;
    @FXML
    private CheckBox checkBoxCordinador;
    @FXML
    private Label labelInvalidateCorreo;
    @FXML
    private TextField textNumeroPersonal;
    @FXML
    private Button buttonSave;
    private boolean[] validationTextFields = {true, true, true, true, true};
    private Pattern validateCharacter = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$");
    private Pattern validateCharacterNumeroPersonal = Pattern.compile("^[0-9]\\d{5}$");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void buttonSave(ActionEvent event) {
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
        enableButton();
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
        if (textNombre.getText().length() > 30) {
            textNombre.setText("");
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
        enableButton();
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
        if (textApellidoPaterno.getText().length() > 20) {
            textApellidoPaterno.setText("");
        }
    }

    @FXML
    private void validateInputsApellidoMaterno(KeyEvent event) {
        textApellidoMaterno.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 20) {
                return change;
            } else {
                return null;
            }
        }));
        if (textApellidoMaterno.getText().length() > 20) {
            textApellidoMaterno.setText("");
        }
    }

    @FXML
    private void validateLengthApellidoMaterno(KeyEvent event) {
        Matcher matcher = validateCharacter.matcher(textApellidoMaterno.getText());
        if (!matcher.matches()) {
            labelInvalidateApellidoMaterno.setText("Datos invalidos");
            validationTextFields[2] = false;
        } else {
            labelInvalidateApellidoMaterno.setText("");
            validationTextFields[2] = true;
        }
        enableButton();
    }


    @FXML
    private void buttonCancel(ActionEvent event) {
        closeWindow();
    }

    @FXML
    private void validateInputsNumeroPersonal(KeyEvent event) {
        Matcher matcher = validateCharacterNumeroPersonal.matcher(textNumeroPersonal.getText());
        if (!matcher.matches()) {
            labelInvalidateMatricula.setText("Datos invalidos");
            validationTextFields[3] = false;
        } else {
            labelInvalidateMatricula.setText("");
            validationTextFields[3] = true;
        }
        enableButton();        
    }

    @FXML
    private void validateLengthNumeroPersonal(KeyEvent event) {
                textNumeroPersonal.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 9) {
                return change;
            } else {
                return null;
            }
        }));
        if (textNumeroPersonal.getText().length() > 9) {
            textNumeroPersonal.clear();
        }
    }
    
    private void closeWindow() {
        Stage escenario = (Stage) labelInvalidateApellidoMaterno.getScene().getWindow();
        escenario.close();
    }    
    
    private void enableButton() {
        if (!validationTextFields[0] || !validationTextFields[1] || !validationTextFields[2] || !validationTextFields[3]) {
            buttonSave.setDisable(true);
        } else {
            buttonSave.setDisable(false);
        }
    }    

}
