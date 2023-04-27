/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import BussinessLogic.UserDAO;
import Domain.Usuario;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import org.apache.commons.lang3.StringUtils;
import singleton.User;
import util.AlertManager;
import util.WindowManager;

/**
 * FXML Controller class
 *
 * @author panther
 */
public class FXMLLoginController implements Initializable {
    
    @FXML
    private TextField tfEmail;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private Label lbEmail;
    @FXML
    private Label lbPassword;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public boolean areFieldsValid() {
        lbEmail.setText("");
        lbPassword.setText("");

        boolean isValid = true;
        String MESSAGE = "Campo obligatorio";

        if (StringUtils.isBlank(tfEmail.getText())) {
            isValid = false;
            lbEmail.setText(MESSAGE);
        }

        if (StringUtils.isBlank(pfPassword.getText())) {
            isValid = false;
            lbPassword.setText(MESSAGE);
        }

        return isValid;
    }

    private void doLogin(String username, String password) throws SQLException {
        UserDAO userDAO = new UserDAO();
        Usuario usuarioLogin = userDAO.getUser(username, password);
        if (usuarioLogin != null) {
            //usuarioLogin.setRoles(userDAO.getUserRoles(usuarioLogin.getNumeroDePersonal()));
            
            User.getCurrentUser().initUser();

            User.getCurrentUser().setNombre(usuarioLogin.getNombre());
            User.getCurrentUser().setApellidoPaterno(usuarioLogin.getApellidoPaterno());
            User.getCurrentUser().setApellidoMaterno(usuarioLogin.getApellidoMaterno());
            User.getCurrentUser().setCorreo(usuarioLogin.getCorreoElectronicoInstitucional());
            //User.getCurrentUser().setProgramaEducativo(usuarioLogin.getProgramaEducativo());
            //User.getCurrentUser().setRoles(usuarioLogin.getRoles());
            
            WindowManager.NavigateToWindow(tfEmail.getScene().getWindow(), "/GUI/FXMLMainMenu.fxml", "Menú");
        } else {
            AlertManager.showAlert("Usuario no encontrado", "No se han encontrado coincidencias con las credenciales ingresadas", Alert.AlertType.WARNING);
        }
        WindowManager.NavigateToWindow(tfEmail.getScene().getWindow(), "/GUI/FXMLMainMenu.fxml", "Menú");
    }

    @FXML
    private void clicLogin(ActionEvent event) {
        if (areFieldsValid()) {
            try {
                doLogin(tfEmail.getText(), pfPassword.getText());
            } catch (SQLException ex) {
                Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void validateInputsEmail(KeyEvent event) {
        Matcher matcher = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$").matcher(tfEmail.getText());
        if (!matcher.matches()) {
            lbEmail.setText("Datos invalidos");
        } else {
            lbEmail.setText("");
        }
    }

    @FXML
    private void validateLengthEmail(KeyEvent event) {
        /*tfEmail.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 20) {
                return change;
            } else {
                return null;
            }
        }));
        if (tfEmail.getText().length() > 20) {
            tfEmail.setText("");
        }*/
    }

    @FXML
    private void validateInputPassword(KeyEvent event) {
        final String ALLOWED_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\\\\\]^_`{|}~]";
        pfPassword.setTextFormatter(new TextFormatter<String>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[" + ALLOWED_CHARACTERS + "]*")) {
                return change;
            }
            return null;
        }));
    }

    @FXML
    private void validateLenghtPassword(KeyEvent event) {
        /*pfPassword.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 20) {
                return change;
            } else {
                return null;
            }
        }));
        if (tfEmail.getText().length() > 20) {
            tfEmail.setText("");
        }*/
    }
}
