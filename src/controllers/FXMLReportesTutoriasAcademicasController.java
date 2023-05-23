/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import BussinessLogic.PeriodoEscolarDAO;
import BussinessLogic.TutorAcademicoDAO;
import BussinessLogic.TutoriaAcademicaDAO;
import Domain.PeriodoEscolar;
import Domain.ReporteTutoriaAcademica;
import Domain.TutoriaAcademica;
import Domain.Usuario;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import singleton.User;
import util.AlertManager;
import util.WindowManager;


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
    private TableView<TutoriaAcademica> tbReportesTutoriasAcademicas;
    @FXML
    private TableColumn ColumNumeroDeSesion;    
    @FXML
    private TableColumn ColumnPeriodoEscolar;
    @FXML
    private Button btConsult;
    
    private ObservableList<TutoriaAcademica> listTutoriasAcademicas;        
    private ObservableList<Usuario> tutoresAcademicos;
    private ObservableList<ReporteTutoriaAcademica> listaReportesTutoriasAcademicas;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btConsult.disableProperty().bind(Bindings.isEmpty(tbReportesTutoriasAcademicas.getSelectionModel().getSelectedItems()));
        loadTutoresAcademicos();
        configureTableColumns();                
        selectTutorAcademico();
        
    }

    private void configureTableColumns() {
        Label noticeLoadingTable = new Label("Seleccione un Tutor Académico");
        tbReportesTutoriasAcademicas.setPlaceholder(noticeLoadingTable);     
        ColumnPeriodoEscolar.setCellValueFactory(new PropertyValueFactory("FechasPeriodoEscolar"));
        ColumNumeroDeSesion.setCellValueFactory(new PropertyValueFactory("numeroDeSesion"));
        listTutoriasAcademicas = FXCollections.observableArrayList();
    }    
    private void loadTutoresAcademicos() {
        //14203
        TutorAcademicoDAO tutorAcademicoDAO = new TutorAcademicoDAO();
        try{
            ArrayList<Usuario> resultadoConsulta = tutorAcademicoDAO.getTutoresWithReportesbyProgramaEducativo(Integer.parseInt(User.getCurrentUser().getRol().getProgramaEducativo().getClave()));
            tutoresAcademicos = FXCollections.observableArrayList();            
            tutoresAcademicos.addAll(resultadoConsulta);
            cbTutorAcademico.setItems(tutoresAcademicos);        
        }catch(SQLException sqle){
            AlertManager.showAlert("Error LOAD", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);                    
        }
    }
    private void selectTutorAcademico() {
        cbTutorAcademico.valueProperty().addListener(new ChangeListener<Usuario>() {
            @Override
            public void changed(ObservableValue<? extends Usuario> observable, Usuario oldValue, Usuario newValue) {
                try {
                    tbReportesTutoriasAcademicas.getItems().clear();
                    Label noticeLoadingTable = new Label("Cargando información, espere un momento...");
                    tbReportesTutoriasAcademicas.setPlaceholder(noticeLoadingTable);                    
                    loadReportesTutoriasAcademicasByTutorAcademico(newValue);
                } catch (Exception e) {
                    System.out.println(e.getClass());
                }
            }
        });
    }    
    
    private void loadReportesTutoriasAcademicasByTutorAcademico(Usuario usuario) {
        TutoriaAcademicaDAO tutoriaAcademicaDao = new TutoriaAcademicaDAO();
        try {
            ArrayList<TutoriaAcademica> loadedTutoriasAcademicas = tutoriaAcademicaDao.getTutoriasAcademicasByTutorAcademico(usuario.getNumeroDePersonal(),usuario.getProgramaEducativo().getClave());
            if(loadedTutoriasAcademicas != null){
                listTutoriasAcademicas.clear();
                listTutoriasAcademicas.addAll(loadedTutoriasAcademicas);
                loadInformationPeriodoEscolar();                        
                tbReportesTutoriasAcademicas.setItems(listTutoriasAcademicas);               
            }else{
                AlertManager.showAlert("Error", "No hay reportes", Alert.AlertType.ERROR);                    
            }    
        } catch (SQLException sqle) {
            System.out.println(sqle);
            AlertManager.showAlert("Error", " loadReportesTutoriasAcademicasByTutorAcademico No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);
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
            AlertManager.showAlert("Error", " loadInformationPeriodoEscolar No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);
        }
    }      
    

    private void goingToReporteTutoriaAcademica(TutoriaAcademica tutoriaAcademica, Usuario tutorAcademico){
        try {            
            Stage escenario = (Stage) tbReportesTutoriasAcademicas.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/FXMLConsultarReporteTutoriaAcademica.fxml"));
            Parent root = loader.load();       
            Scene esceneReporteGeneral = new Scene(root); 
            escenario.setScene(esceneReporteGeneral);
            escenario.setTitle("Reporte de Tutoría del "+tutoriaAcademica.getFechasTutoriaAcademica());
            escenario.show();  
            FXMLConsultarReporteTutoriaAcademicaController controllerReporteTutoriaAcademica = loader.getController();
            controllerReporteTutoriaAcademica.receiveTutoriaAcademicaAndTutor(tutoriaAcademica,tutorAcademico);             
        } catch (IOException ex) {
            ex.printStackTrace();
        }
          
    }    
    private void closeWindow() {
        Stage escenario = (Stage) lbInstruccion1.getScene().getWindow();
        escenario.close();
        WindowManager.NavigateToWindow(lbInstruccion1.getScene().getWindow(), "/GUI/FXMLMainMenu.fxml", "Menú");
    }     
    
    @FXML
    private void clicButtonConsult(ActionEvent event) {
        int validateRowSelected = tbReportesTutoriasAcademicas.getSelectionModel().getSelectedIndex();
        if (validateRowSelected != -1) {        
            goingToReporteTutoriaAcademica(tbReportesTutoriasAcademicas.getSelectionModel().getSelectedItem(),cbTutorAcademico.getSelectionModel().getSelectedItem());            
        }
    }
    @FXML
    private void clicButtonCancel(ActionEvent event) {
        closeWindow(); 
    }
    
}
