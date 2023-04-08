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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Valeria
 */
public class FXMLGestionarAsignacionesTutorController implements Initializable {

    @FXML
    private TableView<?> tblEstudiantes;
    @FXML
    private TableColumn<?, ?> clm_estudiantes;
    @FXML
    private TableColumn<?, ?> clm_tutor;
    @FXML
    private TableView<?> tbl_tutores;
    @FXML
    private TableColumn<?, ?> clm_estudiantesAcargo;
    @FXML
    private Button btn_asignar;
    @FXML
    private TextField txt_estudiante;
    @FXML
    private TextField txt_tutor;
    @FXML
    private Button btn_searchEstudiante;
    @FXML
    private Button btn_searchTutorByNombre;
    @FXML
    private Label lbl_infoEstudiante;
    @FXML
    private Label lbl_infoTutor;
    @FXML
    private Label lbl_estudianteName;
    @FXML
    private Label lbl_programa;
    @FXML
    private Label lbl_nombreTutor;
    @FXML
    private Label lbl_numeroEstudiantes;
    @FXML
    private TableColumn<?, ?> clm_currentTutor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void updateAsignacion(ActionEvent event) {
    }

    @FXML
    private void searchEstudianteByNombre(ActionEvent event) {
    }

    @FXML
    private void searchTutorByNombre(ActionEvent event) {
    }
    
}
