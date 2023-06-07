/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import BussinessLogic.EstudianteDAO;
import BussinessLogic.ProblematicaAcademicaDAO;
import BussinessLogic.ReporteDeTutoriaAcademicaDAO;
import Domain.Estudiante;
import Domain.ProblematicaAcademica;
import Domain.ReporteDeTutoriaAcademica;
import Domain.TutoriaAcademica;
import Domain.Usuario;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import util.AlertManager;
import util.WindowManager;

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
    private TableView<Estudiante> tbEstudiantes;
    @FXML
    private TableColumn columnMatricula;
    @FXML
    private TableColumn columnNombreCompleto;
    @FXML
    private TableColumn columnEsAsistente;
    @FXML
    private TableColumn columnEnRiesgo;
    @FXML
    private TextField tfNumEstudiantesAsistentes;
    @FXML
    private TextField tfNumEstudiantesEnRiesgo;
    @FXML
    private Label lbProgramaEducativo;
    @FXML
    private TableView<ProblematicaAcademica> tbProblematicasAcademicas;
    @FXML
    private TableColumn columnExperienciaEducativa;
    @FXML
    private TableColumn columnProfesor;
    @FXML
    private TableColumn columnTitulo;
    @FXML
    private TableColumn columnNumeroEstudiantes;
    
    private TutoriaAcademica tutoriaAcademica;
    private ReporteDeTutoriaAcademica reporteTutoriaAcademica;
    
    private ObservableList<ProblematicaAcademica> listProblematicasAcademicas;
    private ObservableList<Estudiante> listEstudiantes;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadPlaceHolder();
        configureTableEstudiantes();
        configureTableProblematicas();
    }    

    public void receiveTutoriaAcademicaAndTutor(TutoriaAcademica tutoriaAcademicaRecived, Usuario tutorAcademicoRecived) {
        tutoriaAcademica = tutoriaAcademicaRecived;
        configureScene(tutorAcademicoRecived);
    }    
    public void configureScene(Usuario tutorAcademicoRecived) {
        configureValues(tutorAcademicoRecived);        
        loadEstudiantes();
        loadProblematicas();
    }    
    
    private void loadPlaceHolder(){
        tfComentarioGeneral.setText("Cargando datos...");              
        tfFechaTutoria.setText("Cargando datos...");            
        tfNumeroSesionTutoria.setText("0");
        lbProgramaEducativo.setText("Cargando datos...");
        tfPeriodoEscolar.setText("Cargando datos...");
        tfNumEstudiantesAsistentes.setText("0");
        tfNumEstudiantesEnRiesgo.setText("0");
    }
    private void configureTableEstudiantes() {
        Label noticeLoadingTable = new Label("Cargando información, espere un momento...");
        tbEstudiantes.setPlaceholder(noticeLoadingTable);        
        listEstudiantes = FXCollections.observableArrayList();
        columnMatricula.setCellValueFactory(new PropertyValueFactory("matricula"));
        columnNombreCompleto.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Estudiante, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(
                    TableColumn.CellDataFeatures<Estudiante, String> estudiante) {
                return new SimpleStringProperty(estudiante.getValue().getNombre() + " "
                        + estudiante.getValue().getApellidoPaterno() + " " + estudiante.getValue().getApellidoMaterno());
            }
        });
        columnEsAsistente.setCellValueFactory(new PropertyValueFactory("checkBoxEsAsistente"));
        columnEnRiesgo.setCellValueFactory(new PropertyValueFactory("checkBoxEnRiesgo"));
        tbEstudiantes.setEditable(false);        
    }
    private void configureTableProblematicas() {      
        Label noticeLoadingTable = new Label("Cargando información, espere un momento...");
        tbProblematicasAcademicas.setPlaceholder(noticeLoadingTable);
        columnExperienciaEducativa.setCellValueFactory(new PropertyValueFactory("ExperienciaEducativaAndNRC"));
        columnProfesor.setCellValueFactory(new PropertyValueFactory("nombreCompletoProfesor"));
        columnTitulo.setCellValueFactory(new PropertyValueFactory("Descripcion"));
        columnNumeroEstudiantes.setCellValueFactory(new PropertyValueFactory("NumeroDeEstudiantesAfectados"));
        listProblematicasAcademicas = FXCollections.observableArrayList();
        tbProblematicasAcademicas.setEditable(false);          
    }
    private void disableColumTable(){
        for (Estudiante estudiante : listEstudiantes) {
            CheckBox checkBoxAsistente = new CheckBox();
            checkBoxAsistente.setSelected(estudiante.esAsistente());
            checkBoxAsistente.setDisable(true);
            estudiante.setCheckBoxEsAsistente(checkBoxAsistente);
            CheckBox checkBoxRiesgo = new CheckBox();
            checkBoxRiesgo.setSelected(estudiante.enRiesgo());
            checkBoxRiesgo.setDisable(true);
            estudiante.setCheckBoxEnRiesgo(checkBoxRiesgo);
        }    
    }
    
    private void configureValues(Usuario tutorAcademicoRecived) {
        ReporteDeTutoriaAcademicaDAO ReporteDeTutoriaAcademicaDao = new ReporteDeTutoriaAcademicaDAO();
        try {
            ReporteDeTutoriaAcademica loadedReporteDeTutoriaAcademica = ReporteDeTutoriaAcademicaDao.getReporteDeTutoriaByTutor(tutoriaAcademica.getIdTutoriaAcademica(),tutorAcademicoRecived.getNumeroDePersonal(),tutorAcademicoRecived.getProgramaEducativo().getClave());
            reporteTutoriaAcademica = loadedReporteDeTutoriaAcademica;
            tfComentarioGeneral.setText(reporteTutoriaAcademica.getComentariosGenerales());
            tfFechaTutoria.setText(tutoriaAcademica.getFechasTutoriaAcademica());            
            tfNumeroSesionTutoria.setText(String.valueOf(tutoriaAcademica.getNumeroDeSesion()));
            lbProgramaEducativo.setText("Ingenieria de software");
            tfPeriodoEscolar.setText(tutoriaAcademica.getFechasPeriodoEscolar());
            tfNumEstudiantesEnRiesgo.setText(String.valueOf(reporteTutoriaAcademica.getNumeroDeAlumnosEnRiesgo()));
            tfNumEstudiantesAsistentes.setText(String.valueOf(reporteTutoriaAcademica.getNumeroDeTutoradosQueAsistieron()));            
        } catch (SQLException sqle) {
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);
        }
    }        
    private void loadEstudiantes() {
        try {            
            int alumnosTotalAsistentes = 0, alumnosTotalesRiesgo = 0;
            EstudianteDAO estudianteDAO = new EstudianteDAO();
            ArrayList<Estudiante> listEstudiantesRecived = estudianteDAO.obtenerEstudiantesPorReporteTutoriaAcademica(reporteTutoriaAcademica.getIdReporteTutoria());
            if(!listEstudiantesRecived.isEmpty()){
                listEstudiantes.addAll(listEstudiantesRecived);
                disableColumTable();                
                tbEstudiantes.setItems(listEstudiantes);              
            }else{
                Label noticeLoadingTable = new Label("No hay información correspondiente.");
                tbEstudiantes.setPlaceholder(noticeLoadingTable);           
            }        
        } catch (SQLException sqle) {
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);
        }
    }
    private void loadProblematicas() {
        try {
            ProblematicaAcademicaDAO problematicaAcademicaDao = new ProblematicaAcademicaDAO();
            ArrayList<ProblematicaAcademica> listProblematicasRecived = new ArrayList<>();
            listProblematicasRecived = problematicaAcademicaDao.getAllProblematicasByReporte(reporteTutoriaAcademica.getIdReporteTutoria());            
            if(!listProblematicasRecived.isEmpty()){
                listProblematicasAcademicas.addAll(listProblematicasRecived);
                tbProblematicasAcademicas.setItems(listProblematicasAcademicas);            
            }else{
                Label noticeLoadingTable = new Label("No hay información correspondiente.");
                tbProblematicasAcademicas.setPlaceholder(noticeLoadingTable);           
            }

        } catch (SQLException sqle) {
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);
        }
    }
    
    private void closeWindow() {
        Stage escenario = (Stage) lbProgramaEducativo.getScene().getWindow();
        escenario.close();
        WindowManager.NavigateToWindow(lbProgramaEducativo.getScene().getWindow(), "/GUI/FXMLReportesTutoriasAcademicas.fxml", "Menú");        
    }
    
    @FXML
    private void clicButtonCancel(ActionEvent event) {
        closeWindow();
    }

}
