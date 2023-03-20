/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import BussinessLogic.ProblematicaAcademicaDAO;
import BussinessLogic.ReporteDeTutoriaAcademicaDAO;
import Domain.ProblematicaAcademica;
import Domain.ReporteDeTutoriaAcademica;
import Domain.TutoriaAcademica;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import util.Alerts;

/**
 * FXML Controller class
 *
 * @author michikato
 */
public class FXMLReporteGeneralController implements Initializable {

    @FXML
    private TableColumn<?, ?> columExperienciaEducativa;
    @FXML
    private TableColumn<?, ?> columProfesores;
    @FXML
    private TableColumn<?, ?> columProblematicaAcademicas;
    @FXML
    private TableColumn<?, ?> columCantidadAlumnos;
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
    private TableColumn<?, ?> columTutores;
    @FXML
    private TableColumn<?, ?> columComentarios;
    private ObservableList<ProblematicaAcademica> listProblematicasAcademicas;
    private ObservableList<ReporteDeTutoriaAcademica> listReportesDeTutoria;
    @FXML
    private TableView<ProblematicaAcademica> tableProblematicasTutorias;
    @FXML
    private TableView<ReporteDeTutoriaAcademica> tableReportes;
    private TutoriaAcademica tutoriaAcademica;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configureTableComentariosGenerales();
        configureTableProblematicas();
    }    

    @FXML
    private void buttonDescargarPDF(ActionEvent event) {
        System.out.println(" Descargado ....");
    }

    @FXML
    private void buttonSalir(ActionEvent event) {
        closeWindow();
    }
    
    private void configureTableProblematicas() {        
        columExperienciaEducativa.setCellValueFactory(new PropertyValueFactory("ExperienciaEducativaAndNRC"));
        columProfesores.setCellValueFactory(new PropertyValueFactory("nombreCompletoProfesor"));
        columProblematicaAcademicas.setCellValueFactory(new PropertyValueFactory("Descripcion"));
        columCantidadAlumnos.setCellValueFactory(new PropertyValueFactory("NumeroDeEstudiantesAfectados"));
        listProblematicasAcademicas = FXCollections.observableArrayList();
    }
    
    private void configureTableComentariosGenerales() {
        columTutores.setCellValueFactory(new PropertyValueFactory("tutor"));
        columComentarios.setCellValueFactory(new PropertyValueFactory("comentariosGenerales"));                
        listReportesDeTutoria = FXCollections.observableArrayList();
    }
    
    private void loadInformationComentariosGenerales() {
        ReporteDeTutoriaAcademicaDAO ReporteDeTutoriaAcademicaDao = new ReporteDeTutoriaAcademicaDAO();
        try {
            ArrayList<ReporteDeTutoriaAcademica> loadedReporteDeTutoriaAcademica = ReporteDeTutoriaAcademicaDao.getReportesDeTutorias(tutoriaAcademica.getIdTutoriaAcademica());
            listReportesDeTutoria.clear();
            listReportesDeTutoria.addAll(loadedReporteDeTutoriaAcademica);
            tableReportes.setItems(listReportesDeTutoria);
        } catch (SQLException sqle) {
            Alerts.showAlert("Error", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);
        }
    }
    
    private void loadInformationProblematicas() {
        try {
            ProblematicaAcademicaDAO problematicaAcademicaDao = new ProblematicaAcademicaDAO();
            ArrayList<ProblematicaAcademica> listProblematicasRecived = new ArrayList<>();
            for (int i = 0; i < listReportesDeTutoria.size(); i++) {
                listProblematicasRecived = problematicaAcademicaDao.getProblematicasByReporte(listReportesDeTutoria.get(i).getIdReporteTutoria());                
                listProblematicasAcademicas.addAll(listProblematicasRecived);
            }
            tableProblematicasTutorias.setItems(listProblematicasAcademicas);
        } catch (SQLException sqle) {
            Alerts.showAlert("Error", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);
        }
    }

    public void receiveTutoriaAcademica(TutoriaAcademica tutoriaAcademicaRecived) {
        tutoriaAcademica = tutoriaAcademicaRecived;
        configureScene();
    }
    
    public void configureScene(){
        labelFecha.setText(tutoriaAcademica.getFechaInicio()+" - "+tutoriaAcademica.getFechaFin());        
        labelNumeroSesion.setText(" - "+tutoriaAcademica.getNumeroDeSesion());
        labelProgramaEducativo.setText("Ingenieria de software");
        loadInformationComentariosGenerales();
        loadInformationProblematicas();
    }
    
    private void closeWindow(){
        Stage escenario = (Stage) labelNumeroSesion.getScene().getWindow();
        escenario.close();
    }    

}
