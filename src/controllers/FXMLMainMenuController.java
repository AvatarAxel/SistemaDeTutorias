
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
        WindowManager.NavigateToWindow(
                mbMainMenu.getScene().getWindow(),
                "/GUI/FXMLConsultarReporteGeneralDeTutoriasAcademicas.fxml",
                "Reportes Generales de tutorias"
        );        
    }

    @FXML
    private void menuCreateTutorialReport(ActionEvent event) {
        WindowManager.NavigateToWindow(mbMainMenu.getScene().getWindow(), "/GUI/FXMLReporteTutoriaAcademica.fxml", "Llenar Reporte de Tutorías Académicas");
    }

    @FXML
    private void menuReadTutorialReport(ActionEvent event) {
        WindowManager.NavigateToWindow(mbMainMenu.getScene().getWindow(), "/GUI/FXMLReportesTutoriasAcademicas.fxml", "Reportes de Tutorías Académicas");        
        //Navigator.NavigateToWindow(mbMainMenu.getScene().getWindow(), "/GUI/FXMLConsultarReporteTutoriaAcademica.fxml", "Consultar Reporte de Tutorías Académicas");                
    }

    @FXML
    private void menuReadProblematic(ActionEvent event) {
        WindowManager.NavigateToWindow(
                mbMainMenu.getScene().getWindow(),
                "/GUI/FXMLConsultarProblematicasAcademicas.fxml",
                "Consultar Solución Problematicas Academicas"
        );
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
    private void menuGestionarProblematicas(ActionEvent event) {
        WindowManager.NavigateToWindow(
                mbMainMenu.getScene().getWindow(),
                "/GUI/FXMLGestionarProblematicas.fxml",
                "Gestionar Problemáticas"
        );
    }

    private void menuConsultarProblematica(ActionEvent event) {
       
    }

    @FXML
    private void menuAsignaciones(ActionEvent event) {
          WindowManager.NavigateToWindow(
                mbMainMenu.getScene().getWindow(),
                "/GUI/FXMLGestionarAsignacionesTutor.fxml",
                "Gestionar Asiganciones"
        );
    }

    @FXML
    private void menuConsultarProblematicas(ActionEvent event) {
           WindowManager.NavigateToWindow(
                mbMainMenu.getScene().getWindow(),
                "/GUI/FXMLConsultarProblematicasAcademicas.fxml",
                "Consultar Problemáticas"
        );
    }

    @FXML
    private void menuRegistrarFechas(ActionEvent event) {
        
         WindowManager.NavigateToWindow(
                mbMainMenu.getScene().getWindow(),
                "/GUI/FXMLRegistrarFechasTutorias.fxml",
                "Gestionar Problemáticas"
        );
    }
    
}
