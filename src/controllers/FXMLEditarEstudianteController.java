/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import BussinessLogic.EstudianteDAO;
import Domain.Estudiante;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import util.AlertManager;

/**
 * FXML Controller class
 *
 * @author michikato
 */
public class FXMLEditarEstudianteController implements Initializable{

    @FXML
    private Button buttonRegister;
    @FXML
    private TextField textNombre;
    @FXML
    private TextField textApellidoPaterno;
    @FXML
    private TextField textApellidoMaterno;
    @FXML
    private TextField textMatricula;
    @FXML
    private Label labelInvalidateNombre;
    @FXML
    private Label labelInvalidateApellidoPaterno;
    @FXML
    private Label labelInvalidateApellidoMaterno;
    @FXML
    private Label labelInvalidateMatricula;
    private Estudiante estudiante;
    private boolean[] validationTextFields = {true, true, true, true, true};
    private Pattern validateCharacter = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$");
    private Pattern validateCharacterMatricula = Pattern.compile("^[sS]\\d{8}$");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textMatricula.setDisable(true);
    }

    private void updateEstudiante() {
        try {
            estudiante.setApellidoMaterno(textApellidoMaterno.getText());
            estudiante.setApellidoPaterno(textApellidoPaterno.getText());
            estudiante.setNombre(textNombre.getText());            
            boolean result = new EstudianteDAO().updateEstudiante(estudiante);
            if (result) {
                AlertManager.showTemporalAlert(" ", "Se actualizo con éxito", 2);
            }
        } catch (SQLException e) {
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, porfavor intentelo mas tarde", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void buttonSave(ActionEvent event) {
        updateEstudiante();
        closeWindow();
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
    private void validateLengthApellidoMaterno(KeyEvent event) {
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
    private void validateInputsMatricula(KeyEvent event) {
        Matcher matcher = validateCharacterMatricula.matcher(textMatricula.getText());
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
    private void validateLengthMatricula(KeyEvent event) {
        textMatricula.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 9) {
                return change;
            } else {
                return null;
            }
        }));
        if (textMatricula.getText().length() > 9) {
            textMatricula.setText("");
        }
    }

    @FXML
    private void buttonCancel(ActionEvent event) {
        closeWindow();
    }

    private void closeWindow() {
        Stage escenario = (Stage) textApellidoMaterno.getScene().getWindow();
        escenario.close();
    }

    private void enableButton() {
        if (!validationTextFields[0] || !validationTextFields[1]
                || !validationTextFields[2] || !validationTextFields[3]) {
            buttonRegister.setDisable(true);
        } else {
            buttonRegister.setDisable(false);
        }
    }

    public void reciveEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
        textNombre.setText(estudiante.getNombre());
        textApellidoPaterno.setText(estudiante.getApellidoPaterno());
        textApellidoMaterno.setText(estudiante.getApellidoMaterno());
        textMatricula.setText(estudiante.getMatricula());
    }

}
