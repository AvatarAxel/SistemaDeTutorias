/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import BussinessLogic.EstudianteDAO;
import Domain.Estudiante;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.AlertManager;
import util.WindowManager;

/**
 * FXML Controller class
 *
 * @author michikato
 */
public class FXMLConsultarEstudianteController implements Initializable {

    @FXML
    private TextField textFieldSearchEstudiantes;
    @FXML
    private TableView<Estudiante> tableEstudiantes;
    @FXML
    private TableColumn<?, ?> columMatricula;
    @FXML
    private TableColumn<?, ?> columNombre;
    @FXML
    private TableColumn<?, ?> columApellidoPaterno;
    @FXML
    private TableColumn<?, ?> columApellidoMaterno;
    @FXML
    private Button buttonRegister;
    @FXML
    private Button buttonEdit;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonExit;
    private ObservableList<Estudiante> listEstudiantes;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configureTableColumns();
        loadInformationEstudiantes();
        buttonDelete.setDisable(true);
        buttonEdit.setDisable(true);
    }

    private void configureTableColumns() {
        Label noticeLoadingTable = new Label("Cargando información, espere un momento...");
        tableEstudiantes.setPlaceholder(noticeLoadingTable);
        columMatricula.setCellValueFactory(new PropertyValueFactory("matricula"));
        columNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        columApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        columApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        listEstudiantes = FXCollections.observableArrayList();
    }

    private void loadInformationEstudiantes() {
        ExecutorService executorService;
        executorService = Executors.newFixedThreadPool(1);
        Task loadInformationEstudiantesTask = new Task() {
            @Override
            protected Void call() throws Exception {
                try {
                    ArrayList<Estudiante> loadedListEstudiantes = new ArrayList<>();
                    EstudianteDAO estudianteDAO = new EstudianteDAO();
                    loadedListEstudiantes = estudianteDAO.getAllEstudiantes();
                    listEstudiantes.clear();
                    listEstudiantes.addAll(loadedListEstudiantes);
                    tableEstudiantes.setItems(listEstudiantes);                    
                    Label noticeContentTable = new Label("Sin contenido...");
                    tableEstudiantes.setPlaceholder(noticeContentTable);
                } catch (SQLException e) {
                    AlertManager.showAlert("Error", "No hay conexión con la base de datos, porfavor intentelo mas tarde", Alert.AlertType.ERROR);
                }
                return null;
            }
        };
        executorService.submit(loadInformationEstudiantesTask);
        executorService.shutdown();
    }

    @FXML
    private void validateLengthMatricula(KeyEvent event) {
        filterTable();
        textFieldSearchEstudiantes.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 20) {
                return change;
            } else {
                return null;
            }
        }));
        if (textFieldSearchEstudiantes.getText().length() > 20) {
            textFieldSearchEstudiantes.setText("");
        }
    }

    @FXML
    private void selectEstudiante(MouseEvent event) {
        if (!tableEstudiantes.getSelectionModel().isEmpty()) {
            buttonEdit.setDisable(false);
            buttonDelete.setDisable(false);
        }
    }

    @FXML
    private void buttonActionRegister(ActionEvent event) {
        WindowManager.NavigateToWindow(
                buttonEdit.getScene().getWindow(),
                "/GUI/FXMLRegistrarEstudiantes.fxml",
                "Registrar Estudiantes"
        );
    }

    @FXML
    private void buttonActionEdit(ActionEvent event) {
        if (!tableEstudiantes.getSelectionModel().isEmpty()) {
            Estudiante estudiante = tableEstudiantes.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/FXMLEditarEstudiante.fxml"));
                Parent root = loader.load();
                FXMLEditarEstudianteController controllerEditarEstudiante = loader.getController();
                controllerEditarEstudiante.reciveEstudiante(estudiante);
                Scene esceneEditarEstudiante = new Scene(root);
                Stage escenarioEditarEstudiante = new Stage();
                escenarioEditarEstudiante.setScene(esceneEditarEstudiante);
                escenarioEditarEstudiante.initModality(Modality.APPLICATION_MODAL);
                escenarioEditarEstudiante.show();
                tableEstudiantes.getSelectionModel().clearSelection();  
                textFieldSearchEstudiantes.clear();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    private void buttonActionDelete(ActionEvent event) {
        Estudiante estudiante = tableEstudiantes.getSelectionModel().getSelectedItem();
        if (estudiante != null) {
            try {
                boolean result = new EstudianteDAO().deleteEstudiante(estudiante.getMatricula());
                if (result) {
                    AlertManager.showTemporalAlert(" ", "Acción realizada con éxito", 2);
                }
            } catch (SQLException e) {
                AlertManager.showAlert("Error", "No hay conexión con la base de datos, porfavor intentelo mas tarde", Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
        buttonDelete.setDisable(true);
        buttonEdit.setDisable(true);
        tableEstudiantes.getSelectionModel().clearSelection();
        listEstudiantes.remove(estudiante);
        tableEstudiantes.setItems(listEstudiantes);
        textFieldSearchEstudiantes.clear();
    }

    @FXML
    private void buttonActionExit(ActionEvent event) {
        WindowManager.NavigateToWindow(buttonDelete.getScene().getWindow(), "/GUI/FXMLMainMenu.fxml", "Menú");
    }

    private void filterTable() {
        FilteredList<Estudiante> filteredEstudiantes = new FilteredList<>(listEstudiantes, b -> true);
        textFieldSearchEstudiantes.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredEstudiantes.setPredicate(profesor -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String inputText = newValue.toLowerCase();
                if (profesor.getNombre().toLowerCase().indexOf(inputText) != -1) {
                    return true;
                } else if (profesor.getApellidoPaterno().toLowerCase().indexOf(inputText) != -1) {
                    return true;
                } else if (String.valueOf(profesor.getMatricula()).indexOf(inputText) != -1) {
                    return true;
                } else if (profesor.getApellidoMaterno().toLowerCase().indexOf(inputText) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<Estudiante> sortedListEstudiante = new SortedList<>(filteredEstudiantes);
        sortedListEstudiante.comparatorProperty().bind(tableEstudiantes.comparatorProperty());
        tableEstudiantes.setItems(sortedListEstudiante);
    }

}
