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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;

/**
 * FXML Controller class
 *
 * @author michikato
 */
public class FXMLConsultarReporteGeneralDeTutoriasAcademicasController implements Initializable {

    @FXML
    private Label labelProgramaEducativo;
    @FXML
    private Label labelPeriodo;
    @FXML
    private Label LabelTotalAlumnosResgistrados;
    @FXML
    private Label labelTotalAlumnos;
    @FXML
    private Label labelNumeroSesion;
    @FXML
    private Label labelFecha;
    @FXML
    private TableColumn<?, ?> columExperienciaEducativa;
    @FXML
    private TableColumn<?, ?> columProfesores;
    @FXML
    private TableColumn<?, ?> columProblematicaAcademicas;
    @FXML
    private TableColumn<?, ?> columCantidadAlumnos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void buttonDescargarPDF(ActionEvent event) {
    }

    @FXML
    private void buttonGuardar(ActionEvent event) {
    }
    
}
