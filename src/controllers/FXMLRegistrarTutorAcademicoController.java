/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import BussinessLogic.ProfesorDAO;
import Domain.Profesor;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.sql.SQLException;
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
    private Pattern validateCharacterNumeroPersonal = Pattern.compile("^[0-9]{5}$");
    @FXML
    private Label labelError;
    @FXML
    private Label labelCorreoInstitucional;
    @FXML
    private Button buttonSearch;
    @FXML
    private Button buttonRegister;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buttonSearch.setDisable(true);
        buttonRegister.setDisable(true);
    }    

    @FXML
    private void buttonSearchAction(ActionEvent event) throws SQLException {
        clearLabels();
        loadInformationProfesor();
    }

    private void loadInformationProfesor() {
        try {
            ProfesorDAO profesorDao = new ProfesorDAO();
            Profesor profesor = new Profesor();
            profesor = profesorDao.getProfesorUnregistered(parseInt(textFieldSearchProfesores.getText()));
            if (profesor.getNumeroDePersonal() != 0) {
                labelNombre.setText(profesor.getNombre());
                labelApellidoPaterno.setText(profesor.getApellidoPaterno());
                labelApellidoMaterno.setText(profesor.getApellidoMaterno());
                labelCorreoInstitucional.setText(profesor.getCorreoElectronicoInstitucional());
                labelNumeroDePersonal.setText(profesor.getNumeroDePersonal() + "");
            } else {
                Alerts.showAlert("Aviso", "No hay registros", Alert.AlertType.INFORMATION);
            }
        } catch (SQLException e) {
            Alerts.showAlert("Error", "No hay conexión con la base de datos, porfavor intentelo mas tarde", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void buttonCancel(ActionEvent event) {
        closeWindow();
    }

    @FXML
    private void buttonRegisterAction(ActionEvent event) {
    }

    @FXML
    private void validateInsputsNumeroDePersonal(KeyEvent event) {
        Matcher matcher = validateCharacterNumeroPersonal.matcher(textFieldSearchProfesores.getText());
        if (!matcher.matches()) {
            labelError.setText("Datos invalidos");
            validateFieldSearchField = false;
        } else {
            labelError.setText("");
            validateFieldSearchField = true;            
        }
        buttonSearch.setDisable(!validateFieldSearchField);
        buttonRegister.setDisable(!validateFieldSearchField);
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
        if (textFieldSearchProfesores.getText().length() > 5) {
            textFieldSearchProfesores.setText("");
        }
    }
    
    private void clearLabels() {
        labelNombre.setText("");
        labelApellidoPaterno.setText("");
        labelApellidoMaterno.setText("");
        labelCorreoInstitucional.setText("");
        labelNumeroDePersonal.setText("");
    }
    
    private void closeWindow() {
        Stage escenario = (Stage) labelApellidoMaterno.getScene().getWindow();
        escenario.close();
    }
}
