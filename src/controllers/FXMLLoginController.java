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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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

    public boolean AreFieldsValid() {
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

    private void doLogin(String username, String password) {
        try {
            //Conexión con la base de datos para iniciar sesión
            UserDAO userDAO = new UserDAO();
            Usuario usuarioLogin = userDAO.getUserDB(password, username);
            usuarioLogin.setRoles(userDAO.getUserRoles(password, username));

            User.setCurrentUser(new User());

            User.getCurrentUser().setNombre(usuarioLogin.getNombre());
            User.getCurrentUser().setApellidoPaterno(usuarioLogin.getApellidoPaterno());
            User.getCurrentUser().setApellidoMaterno(usuarioLogin.getApellidoMaterno());
            User.getCurrentUser().setCorreo(usuarioLogin.getCorreo());
            User.getCurrentUser().setProgramaEducativo(usuarioLogin.getProgramaEducativo());
            User.getCurrentUser().setRoles(usuarioLogin.getRoles());

            if (User.getCurrentUser() == null) {//Reemplazar el true por el resultado de la consulta
                WindowManager.NavigateToWindow(tfEmail.getScene().getWindow(), "/GUI/FXMLMainMenu.fxml", "Menú");
            } else {
                AlertManager.showAlert("Error", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void clicLogin(ActionEvent event) {
        if (AreFieldsValid()) {
            doLogin(tfEmail.getText(), pfPassword.getText());
        }
    }
}
