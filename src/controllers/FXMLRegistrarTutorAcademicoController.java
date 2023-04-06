/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import BussinessLogic.ProfesorDAO;
import BussinessLogic.TutorAcademicoDAO;
import BussinessLogic.UserDAO;
import Domain.Profesor;
import Domain.TutorAcademico;
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
import util.AlertManager;
import util.Email;
import util.WindowManager;
import util.Random;

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
                buttonRegister.setDisable(false);
            } else {
                AlertManager.showAlert("Aviso", "No hay registros", Alert.AlertType.INFORMATION);
            }
        } catch (SQLException e) {
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, porfavor intentelo mas tarde", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void buttonCancel(ActionEvent event) {
        WindowManager.NavigateToWindow(labelApellidoPaterno.getScene().getWindow(), "/GUI/FXMLMainMenu.fxml", "Menú");
    }

    @FXML
    private void buttonRegisterAction(ActionEvent event) {
        Optional<ButtonType> result = AlertManager.showAlert("Confirmación", "¿Seguro de realizar dicha acción?", Alert.AlertType.CONFIRMATION);
        if (result.get() == ButtonType.OK) {
            registerTutor();
        }
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
    
    private void registerTutor() {
        Random randomPassword = new Random();
        TutorAcademico tutorAcademico = new TutorAcademico();
        tutorAcademico.setNombre(labelNombre.getText());
        tutorAcademico.setApellidoPaterno(labelApellidoMaterno.getText());
        tutorAcademico.setApellidoMaterno(labelApellidoMaterno.getText());
        tutorAcademico.setContraseña(randomPassword.passwordGenerator());
        tutorAcademico.setCorreoElectronicoInstitucional(labelCorreoInstitucional.getText());
        tutorAcademico.setNumeroDePersonal(parseInt(labelNumeroDePersonal.getText()));
        try {
            TutorAcademicoDAO tutorAcademicoDao = new TutorAcademicoDAO();            
            UserDAO userDao = new UserDAO();
            boolean result = tutorAcademicoDao.setTutorRegister(tutorAcademico);
            boolean resultAssignment = userDao.setRolUserTutor(parseInt(labelNumeroDePersonal.getText()));
            if (result && resultAssignment) {
                ProfesorDAO profesorDao = new ProfesorDAO();
                if (profesorDao.setTutorRegister(parseInt(labelNumeroDePersonal.getText()))) {                    
                    Email emailService = new Email();
                    emailService.sendEmailNewUser(tutorAcademico.getContraseña(), tutorAcademico.getContraseña());
                    AlertManager.showAlert("Aviso", "Registro realizado con éxito", Alert.AlertType.INFORMATION);
                }
            }
        } catch (SQLException e) {
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, porfavor intentelo mas tarde", Alert.AlertType.ERROR);
        }
        clearLabels();
        textFieldSearchProfesores.setText("");
        buttonRegister.setDisable(true);
        buttonSearch.setDisable(true);
        validateFieldSearchField = false;
    }
    
}
