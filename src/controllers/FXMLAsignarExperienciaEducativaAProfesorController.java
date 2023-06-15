/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import BussinessLogic.ExperienciaEducativaDAO;
import BussinessLogic.ProfesorDAO;
import Domain.ExperienciaEducativa;
import Domain.Profesor;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import singleton.User;
import util.AlertManager;
import util.WindowManager;

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
    @FXML
    private Button buttonAsignar;
    @FXML
    private TextField textFieldSearchProfesores;
    @FXML
    private ToggleGroup radioButtonGroup;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configureTableExperienciaEducativa();
        configureTableProfesores();
        loadInformationProfesores();
        loadInformationExperienciasEducativas();
        buttonAsignar.setDisable(true);
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
            ArrayList<ExperienciaEducativa> loadedExperienciasEducativas = experienciaEducativaDAO.getAllCurrentExperienciaEducativaByPeriodo(User.getCurrentUser().getRol().getProgramaEducativo().getClave(), User.getCurrentUser().getPeriodoActual().getIdPeriodoEscolar());
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

    @FXML
    private void buttonActionAsignar(ActionEvent event) {
        for (int i = 0; i < tableExperienciasEducativas.getItems().size(); i++) {
            ExperienciaEducativa experienciaEducativa = tableExperienciasEducativas.getItems().get(i);
            if (experienciaEducativa.getEsSeleccionado().isSelected()) {
                try {
                    Profesor profesor = tableProfesor.getSelectionModel().getSelectedItem();
                    boolean result = new ExperienciaEducativaDAO().assignProfesorToExperienciaEducativa(profesor, experienciaEducativa, User.getCurrentUser().getRol().getProgramaEducativo().getClave());
                    if (result) {
                        AlertManager.showTemporalAlert(" ", profesor.getNombreCompleto() + " se pudo asignar exitosamentete a: " + experienciaEducativa.getNombreCompletoProfesor(), 2);
                    } else {
                        AlertManager.showTemporalAlert(" ", experienciaEducativa.getNombre() + " no se pudo asignar al profesor: " + profesor.getNombreCompleto(), 2);
                    }
                } catch (Exception e) {
                    AlertManager.showAlert("Error", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);
                } finally {
                    loadInformationExperienciasEducativas();
                }
            }
        }
        buttonAsignar.setDisable(true);
        tableProfesor.getSelectionModel().clearSelection();
    }

    @FXML
    private void buttonActionCancelar(ActionEvent event) {
        WindowManager.NavigateToWindow(buttonAsignar.getScene().getWindow(), "/GUI/FXMLMainMenu.fxml", "Menú");
    }

    @FXML
    private void selectProfesor(MouseEvent event) {
        if (!tableProfesor.getSelectionModel().isEmpty() && tableProfesor.getSelectionModel().getSelectedItem() != null) {
            buttonAsignar.setDisable(false);
        }
    }

    @FXML
    private void validateLengthProfesor(KeyEvent event) {
        filterTable();
        textFieldSearchProfesores.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 30) {
                return change;
            } else {
                return null;
            }
        }));
        if (textFieldSearchProfesores.getText().length() > 30) {
            textFieldSearchProfesores.setText("");
        }
    }

    private void filterTable() {
        FilteredList<Profesor> filteredProfesor = new FilteredList<>(listProfesor, b -> true);
        textFieldSearchProfesores.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredProfesor.setPredicate(profesor -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String inputText = newValue.toLowerCase();
                if (profesor.getNombre().toLowerCase().indexOf(inputText) != -1) {
                    return true;
                } else if (profesor.getApellidoPaterno().toLowerCase().indexOf(inputText) != -1) {
                    return true;
                } else if (String.valueOf(profesor.getNumeroDePersonal()).indexOf(inputText) != -1) {
                    return true;
                } else if (profesor.getApellidoMaterno().toLowerCase().indexOf(inputText) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<Profesor> sortedListProfesor = new SortedList<>(filteredProfesor);
        sortedListProfesor.comparatorProperty().bind(tableProfesor.comparatorProperty());
        tableProfesor.setItems(sortedListProfesor);
    }

    @FXML
    private void whitProfesorAction(MouseEvent event) {
        ObservableList<ExperienciaEducativa> filteredList = FXCollections.observableArrayList();
        for (ExperienciaEducativa experiencia : listExperienciasEducativas) {
            if (!experiencia.getNombreCompletoProfesor().trim().equals("Sin Asignar")) {
                filteredList.add(experiencia);
            }
        }
        tableExperienciasEducativas.setItems(filteredList);
    }

    @FXML
    private void whitoutProfesorAction(MouseEvent event) {
        ObservableList<ExperienciaEducativa> filteredList = FXCollections.observableArrayList();
        for (ExperienciaEducativa experiencia : listExperienciasEducativas) {
            if (experiencia.getNombreCompletoProfesor().trim().equals("Sin Asignar")) {
                filteredList.add(experiencia);
            }
        }
        tableExperienciasEducativas.setItems(filteredList);
    }

    @FXML
    private void allExperienciasEducativas(MouseEvent event) {
        tableExperienciasEducativas.setItems(listExperienciasEducativas);
    }

}
