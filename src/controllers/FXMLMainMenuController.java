/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import BussinessLogic.ReporteDeTutoriaAcademicaDAO;
import BussinessLogic.TutoriaAcademicaDAO;
import Domain.ReporteDeTutoriaAcademica;
import Domain.TutoriaAcademica;
import Domain.Usuario;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.AlertManager;
import util.WindowManager;

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
    
    private TutoriaAcademica tutoriaAcademica;
    private ReporteDeTutoriaAcademica reporteTutoriaAcademica;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
            Usuario prueba = new Usuario();
            //prueba.setNumeroPersonal(87305);   
            prueba.setNumeroPersonal(10001);        
            
        try{
            TutoriaAcademicaDAO tutoriaAcademicaDAO =  new TutoriaAcademicaDAO();
            tutoriaAcademica = tutoriaAcademicaDAO.getCurrentlyTutoriaAcademica(); 
            if(tutoriaAcademica != null){
                ReporteDeTutoriaAcademicaDAO reporteDeTutoriaAcademicaDao = new ReporteDeTutoriaAcademicaDAO();
                reporteTutoriaAcademica = reporteDeTutoriaAcademicaDao.getCurrentlyReporteDeTutorias(tutoriaAcademica.getIdTutoriaAcademica(),prueba.getNumeroPersonal());               
                if(reporteTutoriaAcademica != null){
                    miCreateTutorialReport.setText("Editar");
                }else{
                    miCreateTutorialReport.setText("Crear");
                }         
            }else{
                    miCreateTutorialReport.setText("Sin Actividades Pendientes");
                    miCreateTutorialReport.setDisable(true);        
            }              
        } catch (SQLException sqle) {
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void menuCreateGeneralReport(ActionEvent event) {
    }

    @FXML
    private void menuReadGeneralReport(ActionEvent event) {
        WindowManager.NavigateToWindow(
                mbMainMenu.getScene().getWindow(),
                "/GUI/FXMLConsultarReporteGeneralDeTutoriasAcademicas.fxml",
                "Reportes Generales de tutorias"
        );        
    }

    @FXML
    private void menuCreateTutorialReport(ActionEvent event) throws SQLException  {
        boolean editableType = false;
        if(miCreateTutorialReport.getText() == "Editar"){
            editableType= true;
        }        
        try {
            Stage escenario = (Stage) mbMainMenu.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/FXMLReporteTutoriaAcademica.fxml"));
            Parent root = loader.load();       
            Scene esceneReporteGeneral = new Scene(root); 
            escenario.setScene(esceneReporteGeneral);
            escenario.setTitle(miCreateTutorialReport.getText()+" Reporte de Tutoría");
            escenario.show();
            /*WindowManager.NavigateToWindow(
                    mbMainMenu.getScene().getWindow(),
                    "/GUI/FXMLReporteTutoriaAcademica.fxml",
                    miCreateTutorialReport.getText()+" Reporte de Tutoría"
            );
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/FXMLReporteTutoriaAcademica.fxml"));
            Parent root = loader.load();*/              
            FXMLReporteTutoriaAcademicaController controllerReporteTutoriaAcademica = loader.getController();
            controllerReporteTutoriaAcademica.configureScene(tutoriaAcademica,reporteTutoriaAcademica,editableType);            
        } catch (IOException ex) {
            ex.printStackTrace();
        }        
    }

    @FXML
    private void menuReadTutorialReport(ActionEvent event) {

        WindowManager.NavigateToWindow(mbMainMenu.getScene().getWindow(), "/GUI/FXMLReportesTutoriasAcademicas.fxml", "Reportes de Tutorías Académicas");                
    }

    @FXML
    private void menuReadProblematic(ActionEvent event) {
        /*WindowManager.NavigateToWindow(
                mbMainMenu.getScene().getWindow(),
                "/GUI/FXMLConsultarProblematicasAcademicas.fxml",
                "Consultar Solución Problematicas Academicas"
        );*/
        WindowManager.NavigateToWindow(
                mbMainMenu.getScene().getWindow(),
                "/GUI/FXMLConsultarSolucionAProblematicaAcademica.fxml",
                "Consultar Solución a Problematica Academica"
        );
    }
    
    @FXML
    private void menuUpdateSolution(ActionEvent event) {
        WindowManager.NavigateToWindow(
                mbMainMenu.getScene().getWindow(),
                "/GUI/FXMLModificarSolucionAProblematica.fxml",
                "Modificar Solución a Problematica Academica"
        );
    }

    @FXML
    private void menuSolutionProblematic(ActionEvent event) {
        WindowManager.NavigateToWindow(
                mbMainMenu.getScene().getWindow(),
                "/GUI/FXMLRegistrarSolucionAProblematica.fxml",
                "Registrar Solución a Problematica Academica"
        );
    }

    @FXML
    private void menuUploadOffer(ActionEvent event) {
        WindowManager.NavigateToWindow(
                mbMainMenu.getScene().getWindow(),
                "/GUI/FXMLRegistrarOfertaAcademica.fxml",
                "Registrar Oferta Académica"
        );
    }

    @FXML
    private void menuReadOffer(ActionEvent event) {
    }

    @FXML
    private void menuRegistrarEstudiantesAction(ActionEvent event) {
        WindowManager.NavigateToWindow(
                mbMainMenu.getScene().getWindow(),
                "/GUI/FXMLRegistrarEstudiantes.fxml",
                "Registrar Estudiantes"
        );
    }

    @FXML
    private void menuRegistrarTutorAction(ActionEvent event) {
        WindowManager.NavigateToWindow(
                mbMainMenu.getScene().getWindow(),
                "/GUI/FXMLRegistrarTutorAcademico.fxml",
                "Registrar Tutor"
        );
    }

    @FXML
    private void menuUpdateOffer(ActionEvent event) {
        WindowManager.NavigateToWindow(
                mbMainMenu.getScene().getWindow(),
                "/GUI/FXMLEditarOfertaAcademica.fxml",
                "Editar Oferta Académica"
        );
    }

    @FXML
    private void menuImportarEstudiantesAction(ActionEvent event) {
        
        WindowManager.NavigateToWindow(
                mbMainMenu.getScene().getWindow(),
                "/GUI/FXMLImportarEstudiantes.fxml",
                "Importar Estudiantes"
        );        
    }
    
}
