/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import BussinessLogic.TutoriaAcademicaDAO;
import Domain.TutoriaAcademica;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.Alerts;
import util.Navigator;

/**
 * FXML Controller class
 *
 * @author michikato
 */
public class FXMLConsultarReporteGeneralDeTutoriasAcademicasController implements Initializable {

    @FXML
    private Label labelProgramaEducativo;
    @FXML
    private Button buttonConsultar;
    @FXML
    private TableView<TutoriaAcademica> tableTutoriasAcademicas;
    @FXML
    private TableColumn<?, ?> columNumeroDeSesion;
    private ObservableList<TutoriaAcademica> listTutoriasAcademicas;    
    @FXML
    private TableColumn<?, ?> columFechaIncio;
    @FXML
    private TableColumn<?, ?> columFechaFin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configureTableColumns();
        loadInformation();
        labelProgramaEducativo.setText("Ingenieria de Software");
    }

    @FXML
    private void buttonConsultarAction(ActionEvent event) {
        int validateRowSelected = tableTutoriasAcademicas.getSelectionModel().getSelectedIndex();
        if (validateRowSelected != -1) {        
            goingToReporteGeneral(tableTutoriasAcademicas.getSelectionModel().getSelectedItem());            
        }
    }

    @FXML
    private void buttonCancelarAction(ActionEvent event) {
        Navigator.NavigateToWindow(labelProgramaEducativo.getScene().getWindow(), "/GUI/FXMLMainMenu.fxml", "Menú");
    }

    private void configureTableColumns() {
        columFechaIncio.setCellValueFactory(new PropertyValueFactory("fechaInicio"));
        columFechaFin.setCellValueFactory(new PropertyValueFactory("fechaFin"));
        columNumeroDeSesion.setCellValueFactory(new PropertyValueFactory("numeroDeSesion"));
        listTutoriasAcademicas = FXCollections.observableArrayList();
    }

    private void loadInformation() {
        TutoriaAcademicaDAO tutoriaAcademicaDao = new TutoriaAcademicaDAO();
        try {
            ArrayList<TutoriaAcademica> loadedTutoriasAcademicas = tutoriaAcademicaDao.getTutoriasAcademicas("14203");
            listTutoriasAcademicas.clear();
            listTutoriasAcademicas.addAll(loadedTutoriasAcademicas);
            tableTutoriasAcademicas.setItems(listTutoriasAcademicas);
        } catch (SQLException sqle) {
            Alerts.showAlert("Error", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);
        }
    }
    
    private void goingToReporteGeneral(TutoriaAcademica tutoriaAcademica){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/FXMLReporteGeneral.fxml"));
            Parent root = loader.load();
            FXMLReporteGeneralController controllerReporteGeneral = loader.getController();
            controllerReporteGeneral.receiveTutoriaAcademica(tutoriaAcademica); 
            Scene esceneReporteGeneral = new Scene(root); 
            Stage escenarioReporteGeneral = new Stage();
            escenarioReporteGeneral.setScene(esceneReporteGeneral);
            escenarioReporteGeneral.initModality(Modality.APPLICATION_MODAL);      
            escenarioReporteGeneral.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
          
    }    
    
}
