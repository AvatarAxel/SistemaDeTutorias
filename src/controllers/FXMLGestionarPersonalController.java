/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import BussinessLogic.ProfesorDAO;
import BussinessLogic.UserDAO;
import Domain.Profesor;
import Domain.Rol;
import Domain.Usuario;
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
import javafx.fxml.Initializable;
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
import util.AlertManager;
import util.WindowManager;

/**
 * FXML Controller class
 *
 * @author michikato
 */
public class FXMLGestionarPersonalController implements Initializable {

    @FXML
    private TableView<Usuario> tablePersonal;
    @FXML
    private TableColumn<?, ?> columNumeroDePersonal;
    @FXML
    private TableColumn<?, ?> columNombre;
    @FXML
    private TableColumn<?, ?> columApellidoPaterno;
    @FXML
    private TableColumn<?, ?> columApellidoMaterno;
    @FXML
    private TextField textFieldSearchPersonal;
    private ObservableList<Usuario> listPersonal;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonEdit;
    @FXML
    private Button buttonRegister;
    @FXML
    private TableColumn<?, ?> columRol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configureTableColumns();
        buttonDelete.setDisable(true);
        buttonEdit.setDisable(true);
        loadInformationPersonalUsuarios();
    }

    private void configureTableColumns() {
        Label noticeLoadingTable = new Label("Cargando información, espere un momento...");
        tablePersonal.setPlaceholder(noticeLoadingTable);
        columNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        columNumeroDePersonal.setCellValueFactory(new PropertyValueFactory("numeroPersonal"));
        columApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        columApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        columRol.setCellValueFactory(new PropertyValueFactory("roles"));
        listPersonal = FXCollections.observableArrayList();
    }

    private void loadInformationPersonalProfesores() {
        ExecutorService executorService;
        executorService = Executors.newFixedThreadPool(1);
        Task loadInformationPersonalTask = new Task() {
            @Override
            protected Void call() throws Exception {
                ProfesorDAO profesorDAO = new ProfesorDAO();
                try {
                    ArrayList<Profesor> loadedProfesor = profesorDAO.getProfesoresUnregistered();
                    ArrayList<Usuario> listUsuarios = new ArrayList<>();
                    if (!loadedProfesor.isEmpty()) {
                        for (int i = 0; i < loadedProfesor.size(); i++) {
                            Usuario usuario = new Usuario();
                            usuario.setNombre(loadedProfesor.get(i).getNombre());
                            usuario.setApellidoPaterno(loadedProfesor.get(i).getApellidoPaterno());
                            usuario.setApellidoMaterno(loadedProfesor.get(i).getApellidoMaterno());
                            usuario.setNumeroDePersonal(loadedProfesor.get(i).getNumeroDePersonal());
                            ArrayList<Rol> listRoles = new ArrayList<>();
                            listRoles.add(new Rol(0, "Profesor"));
                            //usuario.setRoles(listRoles);
                            listUsuarios.add(usuario);
                        }
                        listPersonal.addAll(listUsuarios);
                        tablePersonal.setItems(listPersonal);
                    } else {
                        Label noticeContent = new Label("Sin contenido...");
                        tablePersonal.setPlaceholder(noticeContent);
                    }
                } catch (SQLException sqle) {
                    AlertManager.showAlert("Error", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);
                }
                return null;
            }
        };
        executorService.submit(loadInformationPersonalTask);
        executorService.shutdown();
    }

    private void loadInformationPersonalUsuarios() {
        ExecutorService executorService;
        executorService = Executors.newFixedThreadPool(1);
        Task loadInformationPersonalUsuarioTask = new Task() {
            @Override
            protected Void call() throws Exception {
                UserDAO userDAO = new UserDAO();
                try {
                    ArrayList<Usuario> loadedPersonal = userDAO.getAllUsersByProgramaEducativo(14203);
                    if (!loadedPersonal.isEmpty()) {
                        for (int i = 0; i < loadedPersonal.size(); i++) {
                            int numeroDePersonal = loadedPersonal.get(i).getNumeroDePersonal();
                            //loadedPersonal.get(i).setRoles(userDAO.getAllUserRolesByNumeroDePersonal(numeroDePersonal));
                            System.out.println(numeroDePersonal);
                        }
                        listPersonal.addAll(loadedPersonal);
                        tablePersonal.setItems(listPersonal);
                    } else {
                        Label noticeContent = new Label("Sin contenido...");
                        tablePersonal.setPlaceholder(noticeContent);
                    }
                } catch (SQLException sqle) {
                    AlertManager.showAlert("Error", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);
                }
                return null;
            }
        };
        executorService.submit(loadInformationPersonalUsuarioTask);
        executorService.shutdown();
    }

    @FXML
    private void buttonActionDelete(ActionEvent event) {
    }

    @FXML
    private void buttonActionEdit(ActionEvent event) {
    }

    @FXML
    private void buttonActionExit(ActionEvent event) {
        WindowManager.NavigateToWindow(buttonDelete.getScene().getWindow(), "/GUI/FXMLMainMenu.fxml", "Menú");
    }

    @FXML
    private void buttonActionRegister(ActionEvent event) {
        //TODO
    }

    private void filterTable() {
        FilteredList<Usuario> filteredPersonal = new FilteredList<>(listPersonal, b -> true);
        textFieldSearchPersonal.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredPersonal.setPredicate(Usuario -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String inputText = newValue.toLowerCase();
                if (Usuario.getNombre().toLowerCase().indexOf(inputText) != -1) {
                    return true;
                } else if (Usuario.getApellidoPaterno().toLowerCase().indexOf(inputText) != -1) {
                    return true;
                } else if (String.valueOf(Usuario.getNumeroDePersonal()).indexOf(inputText) != -1) {
                    return true;
                } else if (Usuario.getApellidoMaterno().toLowerCase().indexOf(inputText) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<Usuario> sortedListPersonal = new SortedList<>(filteredPersonal);
        sortedListPersonal.comparatorProperty().bind(tablePersonal.comparatorProperty());
        tablePersonal.setItems(sortedListPersonal);
    }

    @FXML
    private void validateLengthInput(KeyEvent event) {
        filterTable();
        textFieldSearchPersonal.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 30) {
                return change;
            } else {
                return null;
            }
        }));
        if (textFieldSearchPersonal.getText().length() > 30) {
            textFieldSearchPersonal.clear();
        }
    }

    @FXML
    private void selectPersonal(MouseEvent event) {
        if (!tablePersonal.getSelectionModel().isEmpty()
                && tablePersonal.getSelectionModel().getSelectedItem() != null) {
            buttonDelete.setDisable(false);
            buttonEdit.setDisable(false);
        }
    }

}
