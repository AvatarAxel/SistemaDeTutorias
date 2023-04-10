/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import BussinessLogic.ProfesorDAO;
import BussinessLogic.TutorAcademicoDAO;
import BussinessLogic.UserDAO;
import Domain.Profesor;
import Domain.TutorAcademico;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import util.Alerts;
import util.Email;
import util.Navigator;
import util.Random;

/**
 * FXML Controller class
 *
 * @author michikato
 */
public class FXMLRegistrarTutorAcademicoController implements Initializable {

    @FXML
    private TextField textFieldSearchProfesores;
    @FXML
    private Button buttonRegister;
    @FXML
    private TableView<Profesor> tableProfesor;
    @FXML
    private TableColumn<?, ?> columNumeroDePersonal;
    @FXML
    private TableColumn<?, ?> columNombre;
    @FXML
    private TableColumn<?, ?> columCorreoInstitucional;
    private ObservableList<Profesor> listProfesores;
    @FXML
    private TableColumn<?, ?> columApellidoPaterno;
    @FXML
    private TableColumn<?, ?> columApellidoMaterno;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configureTableColumns();
        loadInformationProfesores();
        buttonRegister.setDisable(true);
    }

    private void loadInformationProfesores() {
        try {
            ArrayList<Profesor> loadedListProfesores = new ArrayList<>();
            ProfesorDAO profesorDao = new ProfesorDAO();
            loadedListProfesores = profesorDao.getProfesoresUnregistered();
            listProfesores.clear();
            listProfesores.addAll(loadedListProfesores);
            tableProfesor.setItems(listProfesores);
        } catch (SQLException e) {
            Alerts.showAlert("Error", "No hay conexión con la base de datos, porfavor intentelo mas tarde", Alert.AlertType.ERROR);
        }
    }

    private void configureTableColumns() {
        columNumeroDePersonal.setCellValueFactory(new PropertyValueFactory("numeroDePersonal"));
        columNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        columApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        columApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        columCorreoInstitucional.setCellValueFactory(new PropertyValueFactory("correoElectronicoInstitucional"));
        listProfesores = FXCollections.observableArrayList();
    }

    @FXML
    private void buttonCancel(ActionEvent event) {
        Navigator.NavigateToWindow(textFieldSearchProfesores.getScene().getWindow(), "/GUI/FXMLMainMenu.fxml", "Menú");
    }

    @FXML
    private void buttonRegisterAction(ActionEvent event) throws InterruptedException {
        Optional<ButtonType> result = Alerts.showAlert("Confirmación", "¿Seguro de realizar dicha acción?", Alert.AlertType.CONFIRMATION);
        if (result.get() == ButtonType.OK) {            
            registerTutor();            
        }
    }

    @FXML
    private void validateLengthNumeroDePersonal(KeyEvent event) {
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

    private void registerTutor() {  
        Profesor profesor = tableProfesor.getSelectionModel().getSelectedItem();
        try {
            TutorAcademicoDAO tutorAcademicoDao = new TutorAcademicoDAO();
            UserDAO userDao = new UserDAO();
            ProfesorDAO profesorDao = new ProfesorDAO();
            TutorAcademico tutorAcademico = new TutorAcademico();            
            String randomPassword = new Random().passwordGenerator();            
            tutorAcademico.setNombre(profesor.getNombre());
            tutorAcademico.setApellidoPaterno(profesor.getApellidoPaterno());
            tutorAcademico.setApellidoMaterno(profesor.getApellidoMaterno());
            tutorAcademico.setCorreoElectronicoInstitucional(profesor.getCorreoElectronicoInstitucional());
            tutorAcademico.setNumeroDePersonal(profesor.getNumeroDePersonal());
            tutorAcademico.setContraseña(randomPassword);
            boolean resultRegister = tutorAcademicoDao.setTutorRegister(tutorAcademico);
            boolean resultRolAssignment = userDao.setRolUserTutor(tutorAcademico.getNumeroDePersonal());
            boolean markRegistration = profesorDao.setTutorRegister(tutorAcademico.getNumeroDePersonal());
            if (resultRegister && resultRolAssignment && markRegistration) {
                Alerts.showTemporalAlert(" ", "Registro realizado con éxito", Alert.AlertType.INFORMATION, 2);
                notifyTheNewUser(tutorAcademico.getCorreoElectronicoInstitucional(), randomPassword);                                                                
            }
        } catch (SQLException e) {
            Alerts.showAlert("Error", "No hay conexión con la base de datos, porfavor intentelo mas tarde", Alert.AlertType.ERROR);
        }
        buttonRegister.setDisable(true);
        tableProfesor.getSelectionModel().clearSelection();
        listProfesores.remove(profesor);
        tableProfesor.setItems(listProfesores);
        textFieldSearchProfesores.clear();        
    }
    
    private void notifyTheNewUser(String tutorEmail, String randomPassword) {        
        ExecutorService executorService;
        executorService = Executors.newFixedThreadPool(1);
        Task sendEmailTask = new Task() {
            @Override
            protected Void call() throws Exception {
                Email email = new Email();
                email.sendEmailNewUser(tutorEmail, randomPassword);
                return null;
            }
        };
        executorService.submit(sendEmailTask);
        executorService.shutdown();
    }

    private void filterTable() {
        FilteredList<Profesor> filteredProfesor = new FilteredList<>(listProfesores, b -> true);
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
    private void selectProfesor(MouseEvent event) {
        if(!tableProfesor.getSelectionModel().isEmpty()){            
            buttonRegister.setDisable(false);
        }
    }

}
