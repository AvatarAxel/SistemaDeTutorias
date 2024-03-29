/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import BussinessLogic.PeriodoEscolarDAO;
import BussinessLogic.TutoriaAcademicaDAO;
import Domain.PeriodoEscolar;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import singleton.User;
import util.AlertManager;
import util.WindowManager;

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
    @FXML
    private TableColumn<?, ?> columPeriodo;
    private ObservableList<TutoriaAcademica> listTutoriasAcademicas;        

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buttonConsultar.setDisable(true);
        configureTableColumns();
        loadInformationTutoriaAcademica();
        labelProgramaEducativo.setText(User.getCurrentUser().getRol().getProgramaEducativo().getNombre());
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
        WindowManager.NavigateToWindow(labelProgramaEducativo.getScene().getWindow(), "/GUI/FXMLMainMenu.fxml", "Menú");
    }

    private void configureTableColumns() {
        columPeriodo.setCellValueFactory(new PropertyValueFactory("FechasPeriodoEscolar"));
        columNumeroDeSesion.setCellValueFactory(new PropertyValueFactory("numeroDeSesion"));
        listTutoriasAcademicas = FXCollections.observableArrayList();
    }

    private void loadInformationTutoriaAcademica() {
        TutoriaAcademicaDAO tutoriaAcademicaDao = new TutoriaAcademicaDAO();
        try {
            ArrayList<TutoriaAcademica> loadedTutoriasAcademicas = tutoriaAcademicaDao.getTutoriasAcademicas();
            listTutoriasAcademicas.clear();
            if (loadedTutoriasAcademicas.isEmpty() || loadedTutoriasAcademicas == null || loadedTutoriasAcademicas.size() <= 0) {
                Label noticeContent = new Label("Sin registros ...");
                tableTutoriasAcademicas.setPlaceholder(noticeContent);
            } else {
                listTutoriasAcademicas.addAll(loadedTutoriasAcademicas);
                loadInformationPeriodoEscolar();
                tableTutoriasAcademicas.setItems(listTutoriasAcademicas);
            }
        } catch (SQLException sqle) {
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);
        }
    }
    
    private void loadInformationPeriodoEscolar() {
        PeriodoEscolarDAO periodoEscolarDAO = new PeriodoEscolarDAO();        
        try {
            for(int i = 0; i<listTutoriasAcademicas.size(); i++){
                PeriodoEscolar periodoEscolar = periodoEscolarDAO.getPeriodoEscolar(listTutoriasAcademicas.get(i).getIdTutoriaAcademica());
                listTutoriasAcademicas.get(i).setPeriodoEscolar(periodoEscolar);                
            }
        } catch (SQLException sqle) {
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);
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

    @FXML
    private void selectPeriodo(MouseEvent event) {
        if (!tableTutoriasAcademicas.getSelectionModel().isEmpty() && tableTutoriasAcademicas.getSelectionModel().getSelectedItem() != null) {
            buttonConsultar.setDisable(false);
        }
    }

}
