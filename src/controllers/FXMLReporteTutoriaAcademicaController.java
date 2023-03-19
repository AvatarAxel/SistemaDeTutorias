/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import BussinessLogic.EstudianteDAO;
import Domain.Estudiante;
import Domain.ProblematicaAcademica;
import Domain.Usuario;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import util.Alerts;
import util.Navigator;
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
    
    private Usuario tutorAcademico;
    //private TutoriaAcademica tutoriaAcademica;
    private ArrayList<ProblematicaAcademica> problematicasAcademicas;
    private String comentarioGeneral;
    private ObservableList<Estudiante> estudiantes;
    private IntegerProperty numEnRiesgo= new SimpleIntegerProperty(0);
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tutorAcademico = new Usuario();
        tutorAcademico.setProgramaEducativo("Ingenieria de Software");
        tutorAcademico.setNumeroPersonal(10001);
        lbProgramaEducativo.setText(tutorAcademico.getProgramaEducativo());
        configureTableColumns();
        loadEstudiantes();
    }    

    
    private void configureTableColumns() {
        estudiantes = FXCollections.observableArrayList();
        int numEstudiantesAsistentes = 0, numEstudiantesEnRiesgo = 0;
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
        tfNumEstudiantesAsistentes.setText(String.valueOf(numEstudiantesAsistentes));

        //columnEnRiesgo.setCellValueFactory(new PropertyValueFactory<Estudiante, Boolean>("checkBoxEnRiesgo"));  
        //tfNumEstudiantesAsistentes.setText(String.valueOf(numEstudiantesEnRiesgo));
    columnEnRiesgo.setCellValueFactory(new PropertyValueFactory<>("enRiesgo"));
    //columnEnRiesgo.setCellFactory(CheckBoxTableCell.forTableColumn(columnEnRiesgo));
    columnEnRiesgo.setCellFactory(CheckBoxTableCell.forTableColumn(columnEnRiesgo));  
    columnEnRiesgo.setEditable(true);
    columnEnRiesgo.setOnEditCommit(event -> {
        int estudiantesEnRiesgo = Integer.parseInt(tfNumEstudiantesEnRiesgo.getText());
        if (event.getNewValue()) {
            estudiantesEnRiesgo++;
        } else {
            estudiantesEnRiesgo--;
        }
        tfNumEstudiantesEnRiesgo.setText(Integer.toString(estudiantesEnRiesgo));
    });
    }    
    
    private void loadEstudiantes() {
        EstudianteDAO estudianteDAO = new EstudianteDAO();
        try{
            ArrayList<Estudiante> consultaEstudiantesPorTutorAcademico = estudianteDAO.obtenerEstudiantesPorTutorAcademico(tutorAcademico.getNumeroPersonal());
            estudiantes.clear();
            estudiantes.addAll(consultaEstudiantesPorTutorAcademico);
            tbEstudiantes.setItems(estudiantes);            
        }catch(SQLException sqle){
            Alerts.showAlert("Error", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);        
        }
    }    

    @FXML
    private void clicButtonProblematicaAcademica(ActionEvent event) {
    }

    @FXML
    private void clicButtonCancel(ActionEvent event) {
        closeWindow();            
    }
    
    private void closeWindow() {
        Stage escenario = (Stage) lbProgramaEducativo.getScene().getWindow();
        escenario.close();
        Navigator.NavigateToWindow(lbProgramaEducativo.getScene().getWindow(), "/GUI/FXMLMainMenu.fxml", "Menú");
    }    

    @FXML
    private void clicButtonsRegister(ActionEvent event) {
    }
    
}
