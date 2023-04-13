/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import BussinessLogic.ReporteTutoriaAcademicaDAO;
import BussinessLogic.UserDAO;
import Domain.Estudiante;
import Domain.ReporteTutoriaAcademica;
import Domain.Usuario;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import util.Alerts;
import util.Navigator;

/**
 * FXML Controller class
 *
 * @author Propietario
 */
public class FXMLReportesTutoriasAcademicasController implements Initializable {

    @FXML
    private Label lbInstruccion1;
    @FXML
    private ComboBox<Usuario> cbTutorAcademico;
    @FXML
    private Label lbInstruccion2;
    @FXML
    private TableView<ReporteTutoriaAcademica> tbReportesTutoriasAcademicas;
    @FXML
    private TableColumn ColumnTutoriaAcademica;
    @FXML
    private TableColumn ColumnPeriodoEscolar;
    @FXML
    private Button btConsult;
    
    private ObservableList<Usuario> tutoresAcademicos;
    private ObservableList<ReporteTutoriaAcademica> listaReportesTutoriasAcademicas;
    private Usuario tutorAcademico;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tutorAcademico = new Usuario();
        tutorAcademico.setNombre("PRUEBA");
        tutorAcademico.setProgramaEducativo("Ingenieria de Software");
        //tutorAcademico.setNumeroPersonal(10001);
        tutorAcademico.setNumeroPersonal(70881);   
        btConsult.disableProperty().bind(Bindings.isEmpty(tbReportesTutoriasAcademicas.getSelectionModel().getSelectedItems()));
        loadTutoresAcademicos();
        configureColumnTable();
        selectTutorAcademico();
        
    }    
    public void loadTutoresAcademicos() {
        //14203
        UserDAO userDAO = new UserDAO();
        try{
            ArrayList<Usuario> resultadoConsulta = userDAO.getUsuarioTutoresporProgramaEducativo(14203);
            tutoresAcademicos = FXCollections.observableArrayList();            
            tutoresAcademicos.addAll(resultadoConsulta);
            tutoresAcademicos.add(tutorAcademico);
            cbTutorAcademico.setItems(tutoresAcademicos);        
        }catch(SQLException sqle){
            Alerts.showAlert("Error LOAD", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);        
            Alerts.showAlert("Error LOAD", sqle.toString(), Alert.AlertType.ERROR);        
            
        }
    }
    
    private void configureColumnTable() {
        ColumnTutoriaAcademica.setCellValueFactory(new PropertyValueFactory("fechaInicio"+" - "+"fechaFin"));
        ColumnPeriodoEscolar.setCellValueFactory(new PropertyValueFactory("fechaInicio"+" - "+"fechaFin"));
        listaReportesTutoriasAcademicas = FXCollections.observableArrayList();
    }    
    
    public void selectTutorAcademico() {
        cbTutorAcademico.valueProperty().addListener(new ChangeListener<Usuario>() {
            @Override
            public void changed(ObservableValue<? extends Usuario> observable, Usuario oldValue, Usuario newValue) {
                try {
                    tbReportesTutoriasAcademicas.getItems().clear();
                    loadReportesTutoriasAcademicasByTutorAcademico(tutorAcademico);
                } catch (Exception e) {
                    System.out.println(e.getClass());
                }
            }
        });
    }    
    
    public void loadReportesTutoriasAcademicasByTutorAcademico(Usuario usuario) {
        ReporteTutoriaAcademicaDAO reporteTutoriaAcademicaDAO = new ReporteTutoriaAcademicaDAO();
        try{
            //ArrayList<Usuario> resultadoConsulta = userDAO.getUsuarioTutoresporProgramaEducativo(14203);
            ArrayList<ReporteTutoriaAcademica> resultadoConsulta = ReporteTutoriaAcademicaDAO.getReportesTutoriasAcademicasByTutorByProgramaEducativo(tutorAcademico,14203);
            if(resultadoConsulta != null){
                listaReportesTutoriasAcademicas.clear();
                listaReportesTutoriasAcademicas.addAll(resultadoConsulta);
                tbReportesTutoriasAcademicas.setItems(listaReportesTutoriasAcademicas);             
            }else{
            Alerts.showAlert("LOAD", "NO hay reportes", Alert.AlertType.ERROR);                    
            }      
        }catch(SQLException sqle){
            Alerts.showAlert("Error LOAD", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);        
            Alerts.showAlert("Error LOAD", sqle.toString(), Alert.AlertType.ERROR);        
            
        }
        /*ReporteTutoriaAcademicaDAO reporteTutoriaAcademicaDAO = new ReporteTutoriaAcademicaDAO();
        ArrayList<ReporteTutoriaAcademica> resultadoConsulta = ReporteTutoriasAcademicasDAO.obtenerReportesTutoriasAcademicas(tutorAcademico);
        if (resultadoConsulta != null) {
            listaReportesTutoriasAcademicas.clear();
            listaReportesTutoriasAcademicas.addAll(resultadoConsulta);
            tableViewReportesTutoriasAcademicas.setItems(listaReportesTutoriasAcademicas);
        } else {
            Utilidades.mostrarAlerta("ERROR",
                    "No hay conexión con la base de datos. \n\nPor favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
        }*/
    }    
    
    
    @FXML
    private void clicButtonCancel(ActionEvent event) {
        closeWindow(); 
    }

    @FXML
    private void clicButtonConsult(ActionEvent event) {
        Stage escenario = (Stage) lbInstruccion1.getScene().getWindow();
        escenario.close();        
        Navigator.NavigateToWindow(lbInstruccion1.getScene().getWindow(), "/GUI/FXMLConsultarReporteTutoriaAcademica.fxml", "Consultar Reporte de Tutorías Académicas");
    }
    
    private void closeWindow() {
        Stage escenario = (Stage) lbInstruccion1.getScene().getWindow();
        escenario.close();
        Navigator.NavigateToWindow(lbInstruccion1.getScene().getWindow(), "/GUI/FXMLMainMenu.fxml", "Menú");
    }     
}
