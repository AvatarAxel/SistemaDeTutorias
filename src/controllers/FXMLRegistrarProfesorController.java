package controllers;

import BussinessLogic.ProfesorDAO;
import Domain.Profesor;
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
import util.AlertManager;
import util.WindowManager;

/**
 * FXML Controller class
 *
 * @author Valeria
 */
public class FXMLRegistrarProfesorController implements Initializable {

    @FXML
    private Button buttonRegister;
    @FXML
    private TextField textNombre;
    @FXML
    private TextField textApellidoPaterno;
    @FXML
    private TextField textApellidoMaterno;
    private TextField textMatricula;
    @FXML
    private Label labelInvalidateNombre;
    @FXML
    private Label labelInvalidateApellidoPaterno;
    @FXML
    private Label labelInvalidateApellidoMaterno;
    private Label labelInvalidateMatricula;
    private boolean[] validationTextFields = {false, false, false, false, false};
    private Pattern validateCharacter = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$");
    private Pattern validateCorreo = Pattern.compile("^(?=.*[a-zA-Z])[a-zA-Z\\d]+$");

    private Pattern validateCharacterNumeroPersona = Pattern.compile("^[1-9][0-9]*$");

    @FXML
    private TextField textNumeroDePersonal;
    @FXML
    private TextField textCorreo;
    @FXML
    private Label labelInvalidateNumPersonal;
    @FXML
    private Label labelInvalidateCorreo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        buttonRegister.setDisable(true);
    }

    @FXML
    private void buttonSave(ActionEvent event) {
        registerProfesor();
        cleanItems();
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
    private void buttonCancel(ActionEvent event) {
        WindowManager.NavigateToWindow(labelInvalidateApellidoPaterno.getScene().getWindow(), "/GUI/FXMLMainMenu.fxml", "Menú");
    }

    private void enableButton() {
        if (!validationTextFields[0] || !validationTextFields[1]
                || !validationTextFields[2] || !validationTextFields[3] || !validationTextFields[4]) {
            buttonRegister.setDisable(true);
        } else {
            buttonRegister.setDisable(false);
        }
    }

    @FXML
    private void validateInputsNumeroPersonal(KeyEvent event) {
        Matcher matcher = validateCharacterNumeroPersona.matcher(textNumeroDePersonal.getText());
        if (!matcher.matches()) {
            labelInvalidateNumPersonal.setText("Datos invalidos");
            validationTextFields[3] = false;
        } else {
            labelInvalidateNumPersonal.setText("");
            validationTextFields[3] = true;
        }
        enableButton();
    }

    @FXML
    private void validateLengthNumeroPersonal(KeyEvent event) {
        textNumeroDePersonal.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 5) {
                return change;
            } else {
                return null;
            }
        }));
        if (textNumeroDePersonal.getText().length() > 5) {
            textNumeroDePersonal.setText("");
        }
    }

    @FXML
    private void validateInputsCorreo(KeyEvent event) {
        Matcher matcher = validateCorreo.matcher(textCorreo.getText());
        if (!matcher.matches()) {
            labelInvalidateCorreo.setText("Datos invalidos");
            validationTextFields[4] = false;
        } else {
            labelInvalidateCorreo.setText("");
            validationTextFields[4] = true;
        }
        enableButton();

    }

    @FXML
    private void validateLengthCorreo(KeyEvent event) {
        textCorreo.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 20) {
                return change;
            } else {
                return null;
            }
        }));
        if (textCorreo.getText().length() > 20) {
            textCorreo.setText("");
        }
    }

    private void cleanItems() {
        textNombre.setText("");
        textApellidoPaterno.setText("");
        textApellidoMaterno.setText("");
        textNumeroDePersonal.setText("");
        textCorreo.setText("");
        labelInvalidateNombre.setText("");
        labelInvalidateApellidoPaterno.setText("");
        labelInvalidateApellidoMaterno.setText("");
        labelInvalidateNumPersonal.setText("");
        labelInvalidateCorreo.setText("");
        validationTextFields[0] = false;
        validationTextFields[2] = false;
        validationTextFields[3] = false;
        validationTextFields[4] = false;
        buttonRegister.setDisable(true);
    }

    private void registerProfesor() {
        Profesor profesor = new Profesor();
        try {
            ProfesorDAO profesorDAO = new ProfesorDAO();
            boolean profesorExists = profesorDAO.validateExistProfesor(Integer.parseInt(textNumeroDePersonal.getText()));
            boolean correoExists = profesorDAO.validateExistCorreoProfesor(textCorreo.getText() + "@universidad.com");

            if (!profesorExists && !correoExists) {
                profesor.setNumeroDePersonal(Integer.parseInt(textNumeroDePersonal.getText()));
                profesor.setNombre(textNombre.getText());
                profesor.setApellidoPaterno(textApellidoPaterno.getText());
                profesor.setApellidoMaterno(textApellidoMaterno.getText());
                profesor.setCorreoElectronicoInstitucional(textCorreo.getText() + "@universidad.com");
                if (profesorDAO.setProfesorRegister(profesor)) {
                    AlertManager.showTemporalAlert(" ", "Registro realizado con éxito", 2);
                }
            } else {
                AlertManager.showAlert("Información", "Registro ya existente", Alert.AlertType.INFORMATION);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, porfavor intentelo mas tarde", Alert.AlertType.ERROR);
        }

    }

}
