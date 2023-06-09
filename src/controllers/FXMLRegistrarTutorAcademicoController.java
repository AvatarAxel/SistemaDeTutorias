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
import Domain.Usuario;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import security.SHA_512;
import singleton.User;
import util.AlertManager;
import util.EmailUtil;
import util.WindowManager;
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
    @FXML
    private TableColumn<?, ?> columApellidoPaterno;
    @FXML
    private TableColumn<?, ?> columApellidoMaterno;
    private ObservableList<Profesor> listProfesores;

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
        ExecutorService executorService;
        executorService = Executors.newFixedThreadPool(1);
        Task loadInformationProfesoresTask = new Task() {
            @Override
            protected Void call() throws Exception {
                try {
                    ArrayList<Profesor> loadedListProfesores = new ArrayList<>();
                    ProfesorDAO profesorDao = new ProfesorDAO();
                    loadedListProfesores = profesorDao.getProfesoresNoUser();
                    listProfesores.clear();
                    listProfesores.addAll(loadedListProfesores);
                    tableProfesor.setItems(listProfesores);
                    Label noticeContentTable = new Label("Sin contenido...");
                    tableProfesor.setPlaceholder(noticeContentTable);
                } catch (SQLException e) {
                    AlertManager.showAlert("Error", "No hay conexión con la base de datos, porfavor intentelo mas tarde", Alert.AlertType.ERROR);
                }
                return null;
            }
        };
        executorService.submit(loadInformationProfesoresTask);
        executorService.shutdown();
    }

    private void configureTableColumns() {
        Label noticeLoadingTable = new Label("Cargando información, espere un momento...");
        tableProfesor.setPlaceholder(noticeLoadingTable);
        columNumeroDePersonal.setCellValueFactory(new PropertyValueFactory("numeroDePersonal"));
        columNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        columApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        columApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        columCorreoInstitucional.setCellValueFactory(new PropertyValueFactory("correoElectronicoInstitucional"));
        listProfesores = FXCollections.observableArrayList();
    }

    @FXML
    private void buttonCancel(ActionEvent event) {
        WindowManager.NavigateToWindow(textFieldSearchProfesores.getScene().getWindow(), "/GUI/FXMLMainMenu.fxml", "Menú");
    }

    @FXML
    private void buttonRegisterAction(ActionEvent event) throws InterruptedException {
        Optional<ButtonType> result = AlertManager.showAlert("Confirmación", "¿Seguro de realizar dicha acción?", Alert.AlertType.CONFIRMATION);
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
            tutorAcademico.setContraseña(new SHA_512().getSHA512(randomPassword));
            boolean resultRegister = tutorAcademicoDao.setTutorRegister(tutorAcademico);
            boolean resultRolAssignment = userDao.setRolUserTutor(tutorAcademico.getNumeroDePersonal(), User.getCurrentUser().getRol().getProgramaEducativo().getClave());
            boolean markRegistration = profesorDao.setTutorUser(tutorAcademico.getNumeroDePersonal());
            if (resultRegister && resultRolAssignment && markRegistration) {
                AlertManager.showTemporalAlert(" ", "Registro realizado con éxito", 2);
                //Habilitar cuando se pueda enviar correo
                //notifyTheNewUser(tutorAcademico.getCorreoElectronicoInstitucional(), randomPassword);                                                                
                System.out.println(randomPassword);
            }
        } catch (SQLException e) {
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, porfavor intentelo mas tarde", Alert.AlertType.ERROR);
            e.printStackTrace();
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
                EmailUtil email = new EmailUtil();
                email.sendEmailNewUserOutlook(tutorEmail, randomPassword);
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
        if (!tableProfesor.getSelectionModel().isEmpty() && tableProfesor.getSelectionModel().getSelectedItem() != null) {
            buttonRegister.setDisable(false);
        }
    }

}
