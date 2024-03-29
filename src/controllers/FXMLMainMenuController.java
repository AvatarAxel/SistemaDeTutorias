package controllers;

import BussinessLogic.ReporteDeTutoriaAcademicaDAO;
import Domain.ReporteDeTutoriaAcademica;
import java.io.IOException;
import BussinessLogic.TutoriaAcademicaDAO;
import Domain.ProgramaEducativo;
import Domain.Rol;
import Domain.TutoriaAcademica;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import singleton.User;
import util.AlertManager;
import util.WindowManager;

/**
 * FXML Controller class
 *
 * @author Panther
 */
public class FXMLMainMenuController implements Initializable {

    private ObservableList<Rol> rolesUnicos = FXCollections.observableArrayList();
    private ObservableList<ProgramaEducativo> programasUnicos = FXCollections.observableArrayList();
    private TutoriaAcademica tutoriaAcademica;
    private ReporteDeTutoriaAcademica reporteTutoriaAcademica;

    @FXML
    private MenuBar mbMainMenu;
    @FXML
    private ComboBox<Rol> cbRol;
    @FXML
    private ComboBox<ProgramaEducativo> cbProgramaEducativo;
    @FXML
    private Menu menuReporteGeneral;
    @FXML
    private Menu menuReporteTutorial;
    @FXML
    private MenuItem miCreateTutorialReport;
    @FXML
    private MenuItem miConsultTutorialReport;
    @FXML
    private Menu menuSolucionProblematicas;
    @FXML
    private Menu menuOfertaAcademica;
    @FXML
    private Menu menuAsignaciones;
    @FXML
    private Menu menuProblematicas;
    @FXML
    private Menu menuFechas;
    @FXML
    private Menu menuReportes;
    @FXML
    private Menu menuExperiencias;
    @FXML
    private Menu menuEstudiantes;
    @FXML
    private MenuItem miConsultGeneralReport;
    @FXML
    private MenuItem miConsultOffer;
    @FXML
    private Menu menuProgramaEducativo;
    @FXML
    private Menu menuPersonal;
    @FXML
    private MenuItem miGestionarPersonal;
    @FXML
    private MenuItem miRegistrarProfesor;
    @FXML
    private MenuItem miRegistrarTutor;
    @FXML
    private MenuItem miRegistrarIferta;
    @FXML
    private MenuItem miConsultarSolucion;
    @FXML
    private MenuItem miRegistrarSolucion;
    @FXML
    private MenuItem miEditarSolucion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        grantView();
        loadComboBoxes();
        selectedItem();
        loadTutorAcademicoMenu();
    }

    private void loadTutorAcademicoMenu() {
        if (cbRol.getSelectionModel().getSelectedIndex() > -1) {
            if (cbRol.getSelectionModel().getSelectedItem().getRolName().equals("Tutor")) {
                try {
                    TutoriaAcademicaDAO tutoriaAcademicaDAO = new TutoriaAcademicaDAO();
                    tutoriaAcademica = tutoriaAcademicaDAO.getCurrentlyTutoriaAcademica();
                    if (tutoriaAcademica != null) {
                        ReporteDeTutoriaAcademicaDAO reporteDeTutoriaAcademicaDao = new ReporteDeTutoriaAcademicaDAO();
                        reporteTutoriaAcademica = reporteDeTutoriaAcademicaDao.getCurrentlyReporteDeTutorias(tutoriaAcademica.getIdTutoriaAcademica(), User.getCurrentUser().getNumeroDePersonal(), User.getCurrentUser().getRol().getProgramaEducativo().getClave());
                        if (reporteTutoriaAcademica != null) {
                            miCreateTutorialReport.setText("Editar");
                            miCreateTutorialReport.setDisable(false);
                        } else {
                            miCreateTutorialReport.setText("Crear");
                            miCreateTutorialReport.setDisable(false);
                        }
                    } else {
                        miCreateTutorialReport.setText("Sin Actividades Pendientes");
                        miCreateTutorialReport.setDisable(true);

                    }
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                    AlertManager.showAlert("Error", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);
                }
            }
        }
    }

    private void loadComboBoxes() {
        if (cbRol.getSelectionModel().getSelectedIndex() < 0) {
            cbProgramaEducativo.setDisable(true);
        }

        List<Rol> roles = User.getCurrentUser().getRoles();

        for (int i = 0; i < roles.size(); i++) {
            Rol rol = roles.get(i);

            ProgramaEducativo programa = rol.getProgramaEducativo();

            if (!rolesUnicos.contains(rol)) {
                rolesUnicos.add(rol);
                if (!programasUnicos.contains(rol.getProgramaEducativo())) {
                    programasUnicos.add(programa);
                }
            }

        }

        HashSet<String> uniqueNames = new HashSet<String>();
        ArrayList<Rol> newList = new ArrayList<>();

        for (Rol obj : rolesUnicos) {
            if (!uniqueNames.contains(obj.getRolName())) {
                uniqueNames.add(obj.getRolName());
                newList.add(obj);
            }
        }

        rolesUnicos.clear();
        rolesUnicos.addAll(newList);

        cbRol.setItems(rolesUnicos);
        cbProgramaEducativo.setItems(programasUnicos);

        if (User.getCurrentUser().getRol().getIdRol() != 0) {
            Rol rolAux = null;
            for (Rol r : rolesUnicos) {
                if (r.getRolName().equals(User.getCurrentUser().getRol().getRolName())) {
                    rolAux = r;
                }
            }
            cbRol.getSelectionModel().select(rolAux);
            cbProgramaEducativo.getSelectionModel().select(User.getCurrentUser().getRol().getProgramaEducativo());
            cbProgramaEducativo.setDisable(false);
        }
        grantView();
    }

    private void selectedItem() {
        cbRol.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
            }
            if (newValue != null) {
                programasUnicos.clear();
                for (Rol r : User.getCurrentUser().getRoles()) {
                    if (r.getIdRol() == newValue.getIdRol()) {
                        programasUnicos.add(r.getProgramaEducativo());
                    }
                }
                cbProgramaEducativo.setItems(programasUnicos);
                cbProgramaEducativo.getSelectionModel().select(newValue.getProgramaEducativo());

                cbProgramaEducativo.setDisable(false);

                User.getCurrentUser().setRol(new Rol(newValue.getIdRol(),
                        newValue.getRolName(),
                        cbProgramaEducativo.getSelectionModel().getSelectedItem()));
                grantView();
            }

        });
        cbProgramaEducativo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
            }
            if (newValue != null) {
                User.getCurrentUser().setRol(new Rol(cbRol.getSelectionModel().getSelectedItem().getIdRol(),
                        cbRol.getSelectionModel().getSelectedItem().getRolName(),
                        cbProgramaEducativo.getSelectionModel().getSelectedItem()));
                grantView();
                if (cbRol.getSelectionModel().getSelectedItem().getRolName().equals("Tutor")) {
                    loadTutorAcademicoMenu();
                }
            }
        });
    }

    private void grantView() {
        switch (User.getCurrentUser().getRol().getIdRol()) {
            case 1: //Jefe de Carrera
                menuReporteGeneral.setVisible(true);//True
                menuReporteTutorial.setVisible(true);//True
                miCreateTutorialReport.setVisible(false);
                miConsultTutorialReport.setVisible(true);//True
                menuSolucionProblematicas.setVisible(true);//True
                menuOfertaAcademica.setVisible(false);
                menuAsignaciones.setVisible(false);
                menuProblematicas.setVisible(false);
                menuFechas.setVisible(false);
                menuReportes.setVisible(true);//True
                menuExperiencias.setVisible(false);
                menuEstudiantes.setVisible(false);
                miConsultGeneralReport.setVisible(true);
                miConsultOffer.setVisible(false);
                menuProgramaEducativo.setVisible(false);
                menuPersonal.setVisible(false);
                miGestionarPersonal.setVisible(false);
                miRegistrarProfesor.setVisible(false);
                miRegistrarTutor.setVisible(false);
                miRegistrarIferta.setVisible(false);
                miConsultarSolucion.setVisible(true);//True
                miRegistrarSolucion.setVisible(true);//True
                miEditarSolucion.setVisible(true);//True
                break;
            case 2: //Coordinador de Tutoria
                menuReporteGeneral.setVisible(true);//True
                menuReporteTutorial.setVisible(true);//True
                miCreateTutorialReport.setVisible(false);
                miConsultTutorialReport.setVisible(true);//True
                menuSolucionProblematicas.setVisible(true);//True
                menuOfertaAcademica.setVisible(true);//True
                menuAsignaciones.setVisible(true);//True
                menuProblematicas.setVisible(true);//True
                menuFechas.setVisible(true);//True
                menuReportes.setVisible(true);//True
                menuExperiencias.setVisible(true);//True
                menuEstudiantes.setVisible(true);//True
                miConsultGeneralReport.setVisible(true);//True
                miConsultOffer.setVisible(true);//True
                menuProgramaEducativo.setVisible(false);
                menuPersonal.setVisible(true);//True
                miGestionarPersonal.setVisible(true);//True
                miRegistrarProfesor.setVisible(true);//True
                miRegistrarTutor.setVisible(true);//True
                miRegistrarIferta.setVisible(true);//True
                miConsultarSolucion.setVisible(true);
                miRegistrarSolucion.setVisible(false);
                miEditarSolucion.setVisible(false);
                break;
            case 3: // Tutor Academico
                menuReporteGeneral.setVisible(false);
                menuReporteTutorial.setVisible(true);//True
                miCreateTutorialReport.setVisible(true);//True
                miConsultTutorialReport.setVisible(false);//True
                menuSolucionProblematicas.setVisible(false);
                menuOfertaAcademica.setVisible(false);
                menuAsignaciones.setVisible(false);
                menuProblematicas.setVisible(true);//True
                menuFechas.setVisible(false);
                menuReportes.setVisible(true);//True
                menuExperiencias.setVisible(false);
                menuEstudiantes.setVisible(false);
                miConsultGeneralReport.setVisible(false);
                miConsultOffer.setVisible(false);
                menuProgramaEducativo.setVisible(false);
                menuPersonal.setVisible(false);
                miGestionarPersonal.setVisible(false);
                miRegistrarProfesor.setVisible(false);
                miRegistrarTutor.setVisible(false);
                miRegistrarIferta.setVisible(false);
                miConsultarSolucion.setVisible(false);
                miRegistrarSolucion.setVisible(false);
                miEditarSolucion.setVisible(false);
                break;
            case 4: // Administrador
                menuReporteGeneral.setVisible(false);
                menuReporteTutorial.setVisible(false);
                miCreateTutorialReport.setVisible(false);
                miConsultTutorialReport.setVisible(false);
                menuSolucionProblematicas.setVisible(false);
                menuOfertaAcademica.setVisible(false);
                menuAsignaciones.setVisible(false);
                menuProblematicas.setVisible(false);
                menuFechas.setVisible(false);
                menuReportes.setVisible(false);
                menuExperiencias.setVisible(true);//True
                menuEstudiantes.setVisible(true);//True
                miConsultGeneralReport.setVisible(false);
                miConsultOffer.setVisible(false);//True
                menuProgramaEducativo.setVisible(true);//True
                menuPersonal.setVisible(true);//True
                miGestionarPersonal.setVisible(true);//True
                miRegistrarProfesor.setVisible(true);//True
                miRegistrarTutor.setVisible(true);//True
                miRegistrarIferta.setVisible(true);//True
                miConsultarSolucion.setVisible(false);
                miRegistrarSolucion.setVisible(false);
                miEditarSolucion.setVisible(false);
                break;
            default:
                menuReporteGeneral.setVisible(false);
                menuReporteTutorial.setVisible(false);
                miCreateTutorialReport.setVisible(false);
                miConsultTutorialReport.setVisible(false);
                menuSolucionProblematicas.setVisible(false);
                menuOfertaAcademica.setVisible(false);
                menuAsignaciones.setVisible(false);
                menuProblematicas.setVisible(false);
                menuFechas.setVisible(false);
                menuReportes.setVisible(false);
                menuExperiencias.setVisible(false);
                menuEstudiantes.setVisible(false);
                miConsultGeneralReport.setVisible(false);
                miConsultOffer.setVisible(false);
                menuProgramaEducativo.setVisible(false);
                menuPersonal.setVisible(false);
                miGestionarPersonal.setVisible(false);
                miRegistrarProfesor.setVisible(false);
                miRegistrarTutor.setVisible(false);
                miRegistrarIferta.setVisible(false);
                miConsultarSolucion.setVisible(false);
                miRegistrarSolucion.setVisible(false);
                miEditarSolucion.setVisible(false);
                break;
        }
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
    private void menuCreateTutorialReport(ActionEvent event) throws SQLException {
        boolean editableType;
        if (miCreateTutorialReport.getText() == "Editar") {
            editableType = true;
        } else {
            editableType = false;
        }
        try {
            Stage escenario = (Stage) mbMainMenu.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/FXMLReporteTutoriaAcademica.fxml"));
            Parent root = loader.load();
            Scene esceneReporteGeneral = new Scene(root);
            escenario.setScene(esceneReporteGeneral);
            escenario.setTitle(miCreateTutorialReport.getText() + " Reporte de Tutoría");
            escenario.show();
            FXMLReporteTutoriaAcademicaController controllerReporteTutoriaAcademica = loader.getController();
            controllerReporteTutoriaAcademica.configureScene(tutoriaAcademica, reporteTutoriaAcademica, editableType);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void menuReadTutorialReport(ActionEvent event) {
        WindowManager.NavigateToWindow(
                mbMainMenu.getScene().getWindow(),
                "/GUI/FXMLReportesTutoriasAcademicas.fxml",
                "Reportes de Tutorías Académicas");
    }

    @FXML
    private void menuReadProblematic(ActionEvent event) {
        WindowManager.NavigateToWindow(
                mbMainMenu.getScene().getWindow(),
                "/GUI/FXMLConsultarSolucionAProblematicaAcademica.fxml",
                "Consultar Problematicas Academicas"
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
                "Cargar Oferta Académica"
        );
    }

    @FXML
    private void menuReadOffer(ActionEvent event) {
        WindowManager.NavigateToWindow(
                mbMainMenu.getScene().getWindow(),
                "/GUI/FXMLConsultarOfertaAcademica.fxml",
                "Consultar Oferta Académica"
        );
    }

    @FXML
    private void menuEditOffer(ActionEvent event) {
        WindowManager.NavigateToWindow(
                mbMainMenu.getScene().getWindow(),
                "/GUI/FXMLEditarOfertaAcademica.fxml",
                "Editar Oferta Académica"
        );
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

    @FXML
    private void menuAsignaciones(ActionEvent event) {
        WindowManager.NavigateToWindow(
                mbMainMenu.getScene().getWindow(),
                "/GUI/FXMLGestionarAsignacionesTutor.fxml",
                "Gestionar Asignaciones"
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
                "Registrar Fechas"
        );
    }

    @FXML
    private void menuUpdateFechas(ActionEvent event) {
        WindowManager.NavigateToWindow(
                mbMainMenu.getScene().getWindow(),
                "/GUI/FXMLEditarFechasTutorias.fxml",
                "Editar Fechas"
        );
    }

    @FXML
    private void menuInsertExperiencia(ActionEvent event) {
        WindowManager.NavigateToWindow(
                mbMainMenu.getScene().getWindow(),
                "/GUI/FXMLRegistrarExperienciaEducativa.fxml",
                "Registrar Experiencia Educativa"
        );
    }

    @FXML
    private void menuItemGestionarEstudiantesOnAction(ActionEvent event) {
        WindowManager.NavigateToWindow(
                mbMainMenu.getScene().getWindow(),
                "/GUI/FXMLConsultarEstudiante.fxml",
                "Consular EStudiantes"
        );
    }

    @FXML
    private void menuRegistrarProfesorAction(ActionEvent event) {
        WindowManager.NavigateToWindow(
                mbMainMenu.getScene().getWindow(),
                "/GUI/FXMLRegistrarProfesor.fxml",
                "Registrar Profesor"
        );

    }

    @FXML
    private void clicLogout(ActionEvent event) {
        WindowManager.NavigateToWindow(
                mbMainMenu.getScene().getWindow(),
                "/GUI/FXMLLogin.fxml",
                "Iniciar Sesión"
        );
        User.killCurrentUser();
    }

    @FXML
    private void menuGestionProgramasEducativos(ActionEvent event) {
        WindowManager.NavigateToWindow(
                mbMainMenu.getScene().getWindow(),
                "/GUI/FXMLProgramasEducativos.fxml",
                "Gestion de Programas Educativos"
        );
    }

    @FXML
    private void asignarExperienciaEducativaProfesor(ActionEvent event) {
        WindowManager.NavigateToWindow(
                mbMainMenu.getScene().getWindow(),
                "/GUI/FXMLAsignarExperienciaEducativaAProfesor.fxml",
                "Asignar Experiencia Educativa A Profesor"
        );
    }

    @FXML
    private void menuItemGestionarPersonalOnAction(ActionEvent event) {
        WindowManager.NavigateToWindow(
                mbMainMenu.getScene().getWindow(),
                "/GUI/FXMLGestionarPersonal.fxml",
                "Gestionar Personal"
        );
    }

}
