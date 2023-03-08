/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import singleton.User;
import util.Alerts;

/**
 * FXML Controller class
 *
 * @author Panther
 */
public class FXMLMainMenuController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void menuCreate(ActionEvent event) {
        Alerts.showAlert("Prueba", "Prueba", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void menuRead(ActionEvent event) {
        Alerts.showAlert("Prueba", "Prueba", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void menuSolution(ActionEvent event) {
        Alerts.showAlert("Prueba", "Prueba", Alert.AlertType.INFORMATION);
    }
    
}
