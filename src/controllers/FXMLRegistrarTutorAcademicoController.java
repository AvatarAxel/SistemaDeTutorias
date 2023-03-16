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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author michikato
 */
public class FXMLRegistrarTutorAcademicoController implements Initializable {

    @FXML
    private TableView<?> tableProfesores;
    @FXML
    private TableColumn<?, ?> columNamesProfesores;
    @FXML
    private TableColumn<?, ?> columNumeroPersonal;
    @FXML
    private TextField searchProfesores;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void buttonSave(ActionEvent event) {
    }

    @FXML
    private void buttonSearch(ActionEvent event) {
    }
    
}
