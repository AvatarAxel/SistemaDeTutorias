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
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import util.Navigator;

/**
 * FXML Controller class
 *
 * @author Panther
 */
public class FXMLMainMenuController implements Initializable {

    @FXML
    private MenuBar mbMainMenu;
    @FXML
    private MenuItem miCreateGeneralReport;
    @FXML
    private MenuItem miCreateTutorialReport;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void menuCreateGeneralReport(ActionEvent event) {
    }

    @FXML
    private void menuReadGeneralReport(ActionEvent event) {
    }

    @FXML
    private void menuCreateTutorialReport(ActionEvent event) {
    }

    @FXML
    private void menuReadTutorialReport(ActionEvent event) {
    }

    @FXML
    private void menuReadProblematic(ActionEvent event) {
    }
    
    @FXML
    private void menuUpdateSolution(ActionEvent event) {
        Navigator.NavigateToWindow(mbMainMenu.getScene().getWindow(), "/GUI/FXMLModificarSolucionAProblematica.fxml", "Modificar Solución a Problematica Academica");
    }

    @FXML
    private void menuSolutionProblematic(ActionEvent event) {
        Navigator.NavigateToWindow(mbMainMenu.getScene().getWindow(), "/GUI/FXMLRegistrarSolucionAProblematica.fxml", "Registrar Solución a Problematica Academica");
    }

    @FXML
    private void menuUploadOffer(ActionEvent event) {
        Navigator.NavigateToWindow(mbMainMenu.getScene().getWindow(), "/GUI/FXMLRegistrarOfertaAcademica.fxml", "Registrar Oferta Académica");
    }

    @FXML
    private void menuReadOffer(ActionEvent event) {
    }
    
}
