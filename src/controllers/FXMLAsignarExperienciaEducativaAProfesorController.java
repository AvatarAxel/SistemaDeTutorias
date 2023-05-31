/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import BussinessLogic.ExperienciaEducativaDAO;
import BussinessLogic.ProfesorDAO;
import BussinessLogic.ReporteDeTutoriaAcademicaDAO;
import Domain.ExperienciaEducativa;
import Domain.ProblematicaAcademica;
import Domain.Profesor;
import Domain.ReporteDeTutoriaAcademica;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import singleton.User;
import util.AlertManager;

/**
 * FXML Controller class
 *
 * @author michikato
 */
public class FXMLAsignarExperienciaEducativaAProfesorController implements Initializable {

    @FXML
    private TableView<ExperienciaEducativa> tableExperienciasEducativas;
    @FXML
    private TableColumn<?, ?> checkBoxEsSeleccionado;
    @FXML
    private TableColumn<?, ?> columNrc;
    @FXML
    private TableColumn<?, ?> columNombre;
    @FXML
    private TableColumn<?, ?> columNumeroDePersonal;
    @FXML
    private TableColumn<?, ?> columApellidoPaterno;
    @FXML
    private TableColumn<?, ?> columApellidoMaterno;
    @FXML
    private TableView<Profesor> tableProfesor;
    @FXML
    private TableColumn<?, ?> columFullNombre;
    @FXML
    private TableColumn<?, ?> columExperienciaEducativa;
    private ObservableList<ExperienciaEducativa> listExperienciasEducativas;
    private ObservableList<Profesor> listProfesor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configureTableExperienciaEducativa();
        configureTableProfesores();
        loadInformationProfesores();
        loadInformationExperienciasEducativas();
    }

    private void configureTableExperienciaEducativa() {
        checkBoxEsSeleccionado.setCellValueFactory(new PropertyValueFactory("esSeleccionado"));
        columFullNombre.setCellValueFactory(new PropertyValueFactory("NombreCompletoProfesor"));
        columNrc.setCellValueFactory(new PropertyValueFactory("nrc"));
        columExperienciaEducativa.setCellValueFactory(new PropertyValueFactory("nombre"));
        listExperienciasEducativas = FXCollections.observableArrayList();
    }

    private void configureTableProfesores() {
        columNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        columApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        columApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        columNumeroDePersonal.setCellValueFactory(new PropertyValueFactory("numeroDePersonal"));
        listProfesor = FXCollections.observableArrayList();
    }

    private void loadInformationExperienciasEducativas() {
        ExperienciaEducativaDAO experienciaEducativaDAO = new ExperienciaEducativaDAO();
        try {
            ArrayList<ExperienciaEducativa> loadedExperienciasEducativas = experienciaEducativaDAO.getAllCurrentExperienciaEducativaByPeriodo("sd", 0);
            listExperienciasEducativas.clear();
            listExperienciasEducativas.addAll(loadedExperienciasEducativas);
            tableExperienciasEducativas.setItems(listExperienciasEducativas);
        } catch (SQLException sqle) {
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);
        }
    }
    
    private void loadInformationProfesores() {
        ProfesorDAO ProfesorDAO = new ProfesorDAO();
        try {
            ArrayList<Profesor> loadedProfesores = ProfesorDAO.getAllProfesores();
            listProfesor.clear();
            listProfesor.addAll(loadedProfesores);
            tableProfesor.setItems(listProfesor);
        } catch (SQLException sqle) {
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);
        }
    }

}
