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
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Propietario
 */
public class FXMLConsultarReporteTutoriaAcademicaController implements Initializable {

    @FXML
    private TextField tfPeriodoEscolar;
    @FXML
    private TextField tfFechaTutoria;
    @FXML
    private TextField tfNumeroSesionTutoria;
    @FXML
    private TextArea tfComentarioGeneral;
    @FXML
    private TextField tfFechaLimiteEntrega;
    @FXML
    private TableView<?> tbEstudiantes;
    @FXML
    private TableColumn<?, ?> columnMatricula;
    @FXML
    private TableColumn<?, ?> columnNombreCompleto;
    @FXML
    private TableColumn<?, ?> columnEsAsistente;
    @FXML
    private TableColumn<?, ?> columnEnRiesgo;
    @FXML
    private TextField tfNumEstudiantesAsistentes;
    @FXML
    private TextField tfNumEstudiantesEnRiesgo;
    @FXML
    private Label lbProgramaEducativo;
    @FXML
    private TableView<?> tbProblematicasAcademicas;
    @FXML
    private TableColumn<?, ?> columnExperienciaEducativa;
    @FXML
    private TableColumn<?, ?> columnProfesor;
    @FXML
    private TableColumn<?, ?> columnTitulo;
    @FXML
    private TableColumn<?, ?> columnNumeroEstudiantes;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicButtonCancel(ActionEvent event) {
    }

    @FXML
    private void clicButtonDownload(ActionEvent event) {
    }
    
}
