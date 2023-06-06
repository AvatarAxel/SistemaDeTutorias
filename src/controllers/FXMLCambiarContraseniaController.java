/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import BussinessLogic.UserDAO;
import Domain.TutoriaAcademica;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.AlertManager;
import util.WindowManager;

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
    @FXML
    private Label labelNewPassword;
    @FXML
    private Label labelConfirmNewPassword;
    private Pattern validateCharacterEmail = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    @FXML
    private Label labelInvalidateCorreo;
    private boolean resultValidation = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buttonAceptar.setVisible(false);
        textFieldContrasenia.setVisible(false);
        textFieldConfirmarConstrasenia.setVisible(false);
        labelConfirmNewPassword.setVisible(false);
        labelNewPassword.setVisible(false);
        buttonEnviarCodigo.setDisable(true);
    }

    @FXML
    private void buttonActionEnviarCodigo(ActionEvent event) {
        try {
            boolean result = new UserDAO().existCorreo(textFieldCorreo.getText());
            if (result) {
                goingToVerificationCode(new util.Random().verificationCodeGenerator());
                if (resultValidation) {
                    buttonAceptar.setVisible(true);
                    textFieldContrasenia.setVisible(true);
                    textFieldConfirmarConstrasenia.setVisible(true);
                    labelConfirmNewPassword.setVisible(true);
                    labelNewPassword.setVisible(true);
                    buttonEnviarCodigo.setDisable(false);
                }
            } else {
                labelInvalidateCorreo.setText("Correo no encontrado, porfavor vuelva a ingresarlo");
            }
        } catch (SQLException ex) {
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, porfavor intentelo mas tarde", Alert.AlertType.ERROR);
        }
    }

    private void goingToVerificationCode(String verificationCode) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/FXMLValidarCodigoDeVerificacion.fxml"));
            Parent root = loader.load();
            FXMLValidarCodigoDeVerificacionController controllerValidarCodigo = loader.getController();
            controllerValidarCodigo.receiveData(resultValidation, verificationCode);
            Scene sceneValidarCodigo = new Scene(root);
            Stage escenarioValidarCodigo = new Stage();
            escenarioValidarCodigo.setScene(sceneValidarCodigo);
            escenarioValidarCodigo.initModality(Modality.APPLICATION_MODAL);
            escenarioValidarCodigo.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }  

    @FXML
    private void buttonActionCancelar(ActionEvent event) {
        WindowManager.NavigateToWindow(
                textFieldContrasenia.getScene().getWindow(),
                "/GUI/FXMLLogin.fxml",
                "Login"
        );
    }

    @FXML
    private void buttonActionAceptar(ActionEvent event) {
    }

    @FXML
    private void validateInputsCorreo(KeyEvent event) {
        Matcher matcher = validateCharacterEmail.matcher(textFieldCorreo.getText());
        if (!matcher.matches()) {
            labelInvalidateCorreo.setText("Datos invalidos");
            buttonEnviarCodigo.setDisable(true);
        } else {
            labelInvalidateCorreo.setText("");
            buttonEnviarCodigo.setDisable(false);
        }
    }

    @FXML
    private void validateLengthCorreo(KeyEvent event) {
        textFieldCorreo.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 25) {
                return change;
            } else {
                return null;
            }
        }));
        if (textFieldCorreo.getText().length() > 25) {
            textFieldCorreo.setText("");
        }
    }

    @FXML
    private void validateInputPassword(KeyEvent event) {
        
    }

    @FXML
    private void validateLengthPassword(KeyEvent event) {
        textFieldCorreo.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 25) {
                return change;
            } else {
                return null;
            }
        }));
        if (textFieldCorreo.getText().length() > 25) {
            textFieldCorreo.setText("");
        }
    }

    @FXML
    private void validateInputConfirmPassword(KeyEvent event) {
    }

    @FXML
    private void validateLengthConfirmPassword(KeyEvent event) {
        textFieldCorreo.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 25) {
                return change;
            } else {
                return null;
            }
        }));
        if (textFieldCorreo.getText().length() > 25) {
            textFieldCorreo.setText("");
        }
    }

}
