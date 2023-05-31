/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import BussinessLogic.PeriodoEscolarDAO;
import BussinessLogic.ExperienciaEducativaDAO;
import Domain.ExperienciaEducativa;
import Domain.PeriodoEscolar;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
public class FXMLConsultarOfertaAcademicaController implements Initializable {

    @FXML
    private ComboBox<PeriodoEscolar> cbPeriodoEscolar;
    @FXML
    private Label lbInstruccion2;
    @FXML
    private Label lbProgramaEducativo;
    @FXML
    private TableView<ExperienciaEducativa> tbExperienciasEducativas;
    @FXML
    private TableColumn ColumnNRC;
    @FXML
    private TableColumn ColumnNombre;
    @FXML
    private TableColumn ColumnSeccion;
    @FXML
    private TableColumn ColumnModalidad;
    @FXML
    private TableColumn ColumnProfesor;
    
    private ObservableList<ExperienciaEducativa> listExperienciaEducativa;        
    private ObservableList<PeriodoEscolar> periodosEscolares;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lbProgramaEducativo.setText(User.getCurrentUser().getRol().getProgramaEducativo().getNombre());
        loadPeriodosEscolares();
        configureTableColumns();  
        selectPeriodoEscolar();
    }    

    private void loadPeriodosEscolares() {
        //14203
        PeriodoEscolarDAO periodoEscolarDAO = new PeriodoEscolarDAO();
        try{
            ArrayList<PeriodoEscolar> resultadoConsulta = periodoEscolarDAO.getAllPeriodos();
            periodosEscolares = FXCollections.observableArrayList();            
            periodosEscolares.addAll(resultadoConsulta);
            cbPeriodoEscolar.setItems(periodosEscolares);        
        }catch(SQLException sqle){
            AlertManager.showAlert("Error LOAD", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);                    
        }
    } 
    
    private void configureTableColumns() {
        Label noticeLoadingTable = new Label("Seleccione un Periodo Escolar");
        tbExperienciasEducativas.setPlaceholder(noticeLoadingTable);     
        ColumnNRC.setCellValueFactory(new PropertyValueFactory("nrc"));
        ColumnNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        ColumnSeccion.setCellValueFactory(new PropertyValueFactory("seccion"));
        ColumnModalidad.setCellValueFactory(new PropertyValueFactory("modalidad"));
        ColumnProfesor.setCellValueFactory(new PropertyValueFactory("profesorNombre"));
        listExperienciaEducativa = FXCollections.observableArrayList();
    }    

    private void selectPeriodoEscolar() {
        cbPeriodoEscolar.valueProperty().addListener(new ChangeListener<PeriodoEscolar>() {
            @Override
            public void changed(ObservableValue<? extends PeriodoEscolar> observable, PeriodoEscolar oldValue, PeriodoEscolar newValue) {
                try {
                    tbExperienciasEducativas.getItems().clear();
                    Label noticeLoadingTable = new Label("Cargando información, espere un momento...");
                    tbExperienciasEducativas.setPlaceholder(noticeLoadingTable);                    
                    loadExperienciasEducativas(newValue);
                } catch (Exception e) {
                    AlertManager.showAlert("Error", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);
                }
            }
        });
    }     
    
    private void loadExperienciasEducativas(PeriodoEscolar periodoEscolar) {
        ExperienciaEducativaDAO experienciaEducativaDAO = new ExperienciaEducativaDAO();
        try {
            ArrayList<ExperienciaEducativa> loadedExperienciaEducativa = experienciaEducativaDAO.getExperienciasEducativasByPeriodoEscoalar(periodoEscolar.getIdPeriodoEscolar());
            if(!loadedExperienciaEducativa.isEmpty()){
                listExperienciaEducativa.clear();
                listExperienciaEducativa.addAll(loadedExperienciaEducativa);                     
                tbExperienciasEducativas.setItems(listExperienciaEducativa);               
            }else{
                Label noticeLoadingTable = new Label("No hay Informacion relacionada");
                tbExperienciasEducativas.setPlaceholder(noticeLoadingTable);     
            }    
        } catch (SQLException sqle) {
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);
        }
    }    
    
    
    private void closeWindow() {
        Stage escenario = (Stage) lbInstruccion2.getScene().getWindow();
        escenario.close();
        WindowManager.NavigateToWindow(lbInstruccion2.getScene().getWindow(), "/GUI/FXMLMainMenu.fxml", "Menú");
    }      
    
    @FXML
    private void clicButtonCancel(ActionEvent event) {
        closeWindow();         
    }
    
}
