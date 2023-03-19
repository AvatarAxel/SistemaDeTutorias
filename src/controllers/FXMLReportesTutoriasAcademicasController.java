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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Propietario
 */
public class FXMLReportesTutoriasAcademicasController implements Initializable {

    @FXML
    private Label lbInstruccion1;
    @FXML
    private ComboBox<?> cbTutorAcademico;
    @FXML
    private Label lbInstruccion2;
    @FXML
    private TableView<?> tbReportesTutoriasAcademicas;
    @FXML
    private TableColumn<?, ?> ColumnTutoriaAcademica;
    @FXML
    private TableColumn<?, ?> ColumnPeriodoEscolar;
    @FXML
    private Button btConsult;

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
    private void clicButtonConsult(ActionEvent event) {
    }
    
}
