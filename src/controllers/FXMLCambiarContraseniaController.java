/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import BussinessLogic.UserDAO;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import security.SHA_512;
import util.AlertManager;
import util.WindowManager;
import util.EmailUtil;

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
    @FXML
    private Label labelCorreo;
    @FXML
    private Label labelMessage;
    @FXML
    private TextField textFieldCodigo;
    private boolean[] validationPasword = {false, false};
    @FXML
    private Button buttonVerificar;
    private String verificationCode;
    private Pattern validateCharacterPassword = Pattern.compile(".{9,}");
    @FXML
    private Label errorPassword;

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
        labelMessage.setVisible(false);
        buttonVerificar.setVisible(false);
        textFieldCodigo.setVisible(false);
        errorPassword.setVisible(false);
    }

    @FXML
    private void buttonActionEnviarCodigo(ActionEvent event) {
        try {
            boolean result = new UserDAO().existCorreo(textFieldCorreo.getText());
            if (result) {     
                verificationCode = new util.Random().verificationCodeGenerator();
                System.out.println(verificationCode);
                //Habilitar cuando se pueda enviar correo
                //new EmailUtil().sendEmailChangePasswordOutlook(textFieldCorreo.getText(), verificationCode);
                showValidationCode();
            } else {
                labelInvalidateCorreo.setText("Correo no encontrado, porfavor vuelva a ingresarlo");
            }
        } catch (SQLException ex) {
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, porfavor intentelo mas tarde", Alert.AlertType.ERROR);
        }
    }

    private void showValidationCode() {
        buttonEnviarCodigo.setVisible(false);
        textFieldCorreo.setVisible(false);
        labelCorreo.setVisible(false);
        labelMessage.setVisible(true);
        buttonVerificar.setVisible(true);
        textFieldCodigo.setVisible(true);
    }

    @FXML
    private void buttonActionCancelar(ActionEvent event) {
        closeWindow();
    }

    private void closeWindow() {
        WindowManager.NavigateToWindow(
                textFieldContrasenia.getScene().getWindow(),
                "/GUI/FXMLLogin.fxml",
                "Login"
        );
    }

    @FXML
    private void buttonActionAceptar(ActionEvent event) {
        if (!textFieldConfirmarConstrasenia.getText().equals(textFieldContrasenia.getText())) {
            errorPassword.setVisible(true);
        } else {
            try {
                errorPassword.setVisible(false);
                boolean result = new UserDAO().updatePassword(new SHA_512().getSHA512(textFieldContrasenia.getText()), textFieldCorreo.getText());
                if (result) {
                    AlertManager.showTemporalAlert(" ", "Acción realizada con éxito", 2);
                    closeWindow();
                }
            } catch (SQLException e) {
                AlertManager.showAlert("Error", "No hay conexión con la base de datos, porfavor intentelo mas tarde", Alert.AlertType.ERROR);
            }
        }
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
        errorPassword.setVisible(false);
        Matcher matcher = validateCharacterPassword.matcher(textFieldContrasenia.getText());
        if (!matcher.matches()) {
            validationPasword[0] = false;
        } else {
            validationPasword[0] = true;
        }
        enableButton();
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
        errorPassword.setVisible(false);
        Matcher matcher = validateCharacterPassword.matcher(textFieldConfirmarConstrasenia.getText());
        if (!matcher.matches()) {
            validationPasword[1] = false;
        } else {
            validationPasword[1] = true;
        }
        enableButton();
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
        if(verificationCode.equals(textFieldCodigo.getText())){
            showChangePassword();
        }
    }

    private void showChangePassword() {
        buttonAceptar.setVisible(true);
        buttonAceptar.setDisable(true);
        textFieldContrasenia.setVisible(true);
        textFieldConfirmarConstrasenia.setVisible(true);
        labelConfirmNewPassword.setVisible(true);
        labelNewPassword.setVisible(true);
        labelMessage.setVisible(false);
        buttonVerificar.setVisible(false);
        textFieldCodigo.setVisible(false);
    }

    private void enableButton() {
        if (!validationPasword[0] || !validationPasword[1]) {
            buttonAceptar.setDisable(true);
        } else {
            buttonAceptar.setDisable(false);
        }
    }
}
