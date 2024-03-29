/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import BussinessLogic.EstudianteDAO;
import BussinessLogic.ProblematicaAcademicaDAO;
import BussinessLogic.ReporteDeTutoriaAcademicaDAO;
import Domain.Estudiante;
import Domain.ReporteDeTutoriaAcademica;
import Domain.TutoriaAcademica;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import singleton.User;
import util.AlertManager;
import util.WindowManager;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.Modality;
import util.ExceptionCodes;


/**
 * FXML Controller class
 *
 * @author Propietario
 */
    public class FXMLReporteTutoriaAcademicaController implements Initializable {

    @FXML
    private TextField tfPeriodoEscolar;
    @FXML
    private TextField tfFechaTutoria;
    @FXML
    private TextField tfNumeroSesionTutoria;
    @FXML
    private TextArea tfComentarioGeneral;
    @FXML
    private Label lbProgramaEducativo;
    @FXML
    private TextField tfFechaLimiteEntrega;
    @FXML
    private TableView<Estudiante> tbEstudiantes;
    @FXML
    private TableColumn columnMatricula;
    @FXML
    private TableColumn columnNombreCompleto;
    @FXML
    private TableColumn<Estudiante,Boolean> columnEsAsistente;
    @FXML
    private TableColumn<Estudiante,Boolean> columnEnRiesgo;
    @FXML
    private TextField tfNumEstudiantesAsistentes;
    @FXML
    private TextField tfNumEstudiantesEnRiesgo;
    @FXML
    private Button btProblematicaAcademica;
    
    private ReporteDeTutoriaAcademica reporteTutoriaAcademica;
    private TutoriaAcademica tutoriaAcademica;
    
    private ObservableList<Estudiante> listEstudiantes;
    private boolean editableTypeReport;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configureTableEstudiantes();        
        loadPlaceHolder();
        btProblematicaAcademica.setText("");
    }    
    public void configureScene(TutoriaAcademica tutoriaAcademicaRecived, ReporteDeTutoriaAcademica reporteTutoriaAcademicaRecived, boolean editableType ) throws SQLException {
        editableTypeReport = editableType;
        tutoriaAcademica = tutoriaAcademicaRecived;
        try {
            if(editableType){
                btProblematicaAcademica.setText("Editar problemática académica");
                tfComentarioGeneral.setText("Cargando datos...");              
            }else{
                btProblematicaAcademica.setText("Agregar problemática académica");                
                ReporteDeTutoriaAcademicaDAO ReporteDeTutoriaAcademicaDao = new ReporteDeTutoriaAcademicaDAO();
                if(!ReporteDeTutoriaAcademicaDao.setReporteDeTutorias(tfComentarioGeneral.getText(),tutoriaAcademica.getIdTutoriaAcademica() , User.getCurrentUser().getNumeroDePersonal(),User.getCurrentUser().getRol().getProgramaEducativo().getClave())){            
                    AlertManager.showAlert("Error", "No se pu", Alert.AlertType.ERROR);
                }        
            }
            configureValuesReporteDeTutoria();
            loadEstudiantes(editableType);
        } catch (SQLException sqle) {
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);
        }           
    }
    
    private void loadPlaceHolder(){
        tfFechaTutoria.setText("Cargando datos..."); 
        tfFechaLimiteEntrega.setText("Cargando datos..."); 
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
        columnEsAsistente.setCellValueFactory(new PropertyValueFactory<Estudiante, Boolean>("checkBoxEsAsistente"));
        columnEnRiesgo.setCellValueFactory(new PropertyValueFactory<Estudiante, Boolean>("checkBoxEnRiesgo"));
    }    
    private void configureValuesReporteDeTutoria(){
        ReporteDeTutoriaAcademicaDAO ReporteDeTutoriaAcademicaDao = new ReporteDeTutoriaAcademicaDAO();
        try {
            tfFechaTutoria.setText(tutoriaAcademica.getFechasTutoriaAcademica());
            tfFechaLimiteEntrega.setText(tutoriaAcademica.getFechaLimiteTutoriaAcademica());
            tfNumeroSesionTutoria.setText(String.valueOf(tutoriaAcademica.getNumeroDeSesion()));
            lbProgramaEducativo.setText(User.getCurrentUser().getRol().getProgramaEducativo().getNombre());
            tfPeriodoEscolar.setText(tutoriaAcademica.getFechasPeriodoEscolar());
            ReporteDeTutoriaAcademica loadedReporteDeTutoriaAcademica = ReporteDeTutoriaAcademicaDao.getReporteDeTutoriaByTutor(tutoriaAcademica.getIdTutoriaAcademica(),User.getCurrentUser().getNumeroDePersonal(),User.getCurrentUser().getRol().getProgramaEducativo().getClave());
            reporteTutoriaAcademica = loadedReporteDeTutoriaAcademica;
            if(reporteTutoriaAcademica.getComentariosGenerales().equals("")){
                tfComentarioGeneral.setText("");
                tfComentarioGeneral.setPromptText("Ingrese un comentario general (Opcional)");
            }else{
                tfComentarioGeneral.setText(reporteTutoriaAcademica.getComentariosGenerales());            
            }
        } catch (SQLException sqle) {
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);
        }    
    }
    private void loadEstudiantes(boolean editableType) {
        EstudianteDAO estudianteDAO = new EstudianteDAO();
        ArrayList<Estudiante> listEstudiantesRecived =  new ArrayList<Estudiante>();
        try{
            if(editableType){
                listEstudiantesRecived = estudianteDAO.obtenerEstudiantesPorReporteTutoriaAcademica(reporteTutoriaAcademica.getIdReporteTutoria());             
            }else{
                listEstudiantesRecived = estudianteDAO.getEstudiantesPorTutorAcademicoAndProgramaEducativo(User.getCurrentUser().getNumeroDePersonal(), Integer.parseInt(User.getCurrentUser().getRol().getProgramaEducativo().getClave()));          
            }
            if(listEstudiantesRecived !=null){
                listEstudiantes.clear();                    
                listEstudiantes.addAll(listEstudiantesRecived);
                tbEstudiantes.setItems(listEstudiantes);             
                tfNumEstudiantesAsistentes.setText(String.valueOf(countNumEstudiantesAsistentes()));
                tfNumEstudiantesEnRiesgo.setText(String.valueOf(countEstudiantesEnRiesgo()));   
                actualizarNumEstudiantesAsistentes();
                actualizarNumEstudiantesEnRiesgo();                   
            }else{
                Label noticeLoadingTable = new Label("No hay información correspondiente.");
                tbEstudiantes.setPlaceholder(noticeLoadingTable);           
            }
            
        }catch(SQLException sqle){
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);        
        }
    }     
    private void actualizarNumEstudiantesAsistentes() {
        for (Estudiante estudiante : listEstudiantes) {
            estudiante.getCheckBoxEsAsistente().selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    tfNumEstudiantesAsistentes.setText(String.valueOf(Integer.parseInt(tfNumEstudiantesAsistentes.getText()) + 1));
                } else {
                    tfNumEstudiantesAsistentes.setText(String.valueOf(Integer.parseInt(tfNumEstudiantesAsistentes.getText()) - 1));
                }
            });
        }
    }
    private void actualizarNumEstudiantesEnRiesgo() {
        for (Estudiante estudiante : listEstudiantes) {
            estudiante.getCheckBoxEnRiesgo().selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    tfNumEstudiantesEnRiesgo.setText(String.valueOf(Integer.parseInt(tfNumEstudiantesEnRiesgo.getText()) + 1));
                } else {
                    tfNumEstudiantesEnRiesgo.setText(String.valueOf(Integer.parseInt(tfNumEstudiantesEnRiesgo.getText()) - 1));
                }
            });
        }
    }     

    
    private int countNumEstudiantesAsistentes(){
        int numEstudiantesAsistentes = 0;
        for (int i = 0; i < listEstudiantes.size(); i++) {
            tbEstudiantes.getSelectionModel().select(i);
            Estudiante estudiante = tbEstudiantes.getSelectionModel().getSelectedItem();
            if (estudiante.getCheckBoxEsAsistente().isSelected()) {
                numEstudiantesAsistentes++;
                estudiante.setEsAsistente(true);
            }else{
                estudiante.setEsAsistente(false);   
            }       
        }
        return numEstudiantesAsistentes;
    }
    private int countEstudiantesEnRiesgo(){
        int numEstudiantesEnRiesgo = 0;
        for (int i = 0; i < listEstudiantes.size(); i++) {
            tbEstudiantes.getSelectionModel().select(i);
            Estudiante estudiante = tbEstudiantes.getSelectionModel().getSelectedItem();
            if (estudiante.getCheckBoxEnRiesgo().isSelected()) {
                numEstudiantesEnRiesgo++;
                estudiante.setEnRiesgo(true);
            }else{
                estudiante.setEnRiesgo(false);
            }        
        }
        return numEstudiantesEnRiesgo;
    }        
    private void assignEstudiantes() throws SQLException{
        countNumEstudiantesAsistentes();        
        countEstudiantesEnRiesgo();
        EstudianteDAO estudianteDAO =  new EstudianteDAO();
        try{
            if(editableTypeReport){
                for (int i = 0; i < listEstudiantes.size(); i++) {
                    if(!estudianteDAO.updateAttendanceList(listEstudiantes.get(i), reporteTutoriaAcademica.getIdReporteTutoria())){
                        AlertManager.showAlert("Error", "Ocurrio un problema al guradar la información.", Alert.AlertType.ERROR);        
                    }
                }        
            }else{
                for (int i = 0; i < listEstudiantes.size(); i++) {
                    if(!estudianteDAO.assignToReporteDeTutoriaAcademica(listEstudiantes.get(i), reporteTutoriaAcademica.getIdReporteTutoria())){
                        AlertManager.showAlert("Error", "Ocurrio un problema al guradar la información.", Alert.AlertType.ERROR);        
                    }
                }                                
            }            
        }catch(SQLException sqle){
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);        
        }        
  
    }
    private void saveChanges()throws SQLException {
        try {
            ReporteDeTutoriaAcademicaDAO ReporteDeTutoriaAcademicaDao = new ReporteDeTutoriaAcademicaDAO();
            if(ReporteDeTutoriaAcademicaDao.updateReporteDeTutorias(tfComentarioGeneral.getText(),tutoriaAcademica.getIdTutoriaAcademica() ,User.getCurrentUser().getNumeroDePersonal(),User.getCurrentUser().getRol().getProgramaEducativo().getClave())){            
                    AlertManager.showTemporalAlert("AVISO", "La información se registró correctamente en el sistema", 2);
            }             
        } catch (SQLException sqle) {
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);
        }     
    }
    
    
    private void closeWindow() {
        Stage escenario = (Stage) lbProgramaEducativo.getScene().getWindow();
        escenario.close();
        WindowManager.NavigateToWindow(lbProgramaEducativo.getScene().getWindow(), "/GUI/FXMLMainMenu.fxml", "Menú");        
    }    
    
    @FXML
    private void clicButtonProblematicaAcademica(ActionEvent event) {
          try {
         FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/GUI/FXMLGestionarProblematicas.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Gestión de Problemáticas");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            FXMLGestionarProblematicasController controlador = (FXMLGestionarProblematicasController) fxmlLoader.getController();
            controlador.receiveParameters(reporteTutoriaAcademica.getIdReporteTutoria());
            stage.show();
        } catch (IOException ex) {
            AlertManager.showAlert("Error", "Intentelo más tarde", Alert.AlertType.ERROR);
        }
    }
    @FXML
    private void clicButtonsRegister(ActionEvent event) throws SQLException {
        Optional<ButtonType> answer = AlertManager.showAlert("AVISO",
                "Una vez enviado el Reporte de Tutorías solo lo podrá modificar antes de la fecha límite de la entrega. \n\n¿Desea continuar?", Alert.AlertType.CONFIRMATION);
        if (answer.get() == ButtonType.OK) {
            assignEstudiantes();
            saveChanges();
            closeWindow();
        }
    }    
    @FXML
    private void clicButtonCancel(ActionEvent event) throws SQLException {
        try {
            Optional<ButtonType> answer;
            if(editableTypeReport){
                answer = AlertManager.showAlert("AVISO",
                        "NO se guardarán los cambios. \n\n¿Desea continuar?", Alert.AlertType.CONFIRMATION);
            }else{
                answer = AlertManager.showAlert("AVISO",
                        "¿Desea Salir? \n\n No se guardará ningun cambio.", Alert.AlertType.CONFIRMATION);            
            }
            if(!editableTypeReport){
                ReporteDeTutoriaAcademicaDAO reporteDeTutoriaAcademicoDAO = new ReporteDeTutoriaAcademicaDAO();
                ProblematicaAcademicaDAO problematicaAcademicaDAO = new ProblematicaAcademicaDAO();
                if(problematicaAcademicaDAO.deleteProblematicafromEspecificReporte(reporteTutoriaAcademica.getIdReporteTutoria()) != ExceptionCodes.SUCCESSFUL_OPERATION){            
                    SQLException sqle =  new SQLException();
                }
                if(!reporteDeTutoriaAcademicoDAO.deleteReporteDeTutorias(reporteTutoriaAcademica.getIdReporteTutoria())){            
                    SQLException sqle =  new SQLException();
                }            
            }
            if (answer.get() == ButtonType.OK) {
                editableTypeReport = false;                
                closeWindow();
            }    
        } catch (SQLException sqle) {
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);
        }        
    }    
    
}

