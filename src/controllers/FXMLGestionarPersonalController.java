/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import BussinessLogic.ProfesorDAO;
import BussinessLogic.UserDAO;
import Domain.Estudiante;
import Domain.Personal;
import Domain.Profesor;
import Domain.ProgramaEducativo;
import Domain.Rol;
import Domain.Usuario;
import DomainGraphicInterface.PersonalUser;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
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
import javafx.scene.control.CheckBox;
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
    private TableView<PersonalUser> tablePersonal;
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
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonRegister;
    @FXML
    private TableColumn<Estudiante, String> columRol;
    @FXML
    private Button buttonSave;
    @FXML
    private TextField textNombre;
    @FXML
    private TextField textApellidoPaterno;
    @FXML
    private TextField textApellidoMaterno;
    @FXML
    private TextField textCorreo;
    @FXML
    private CheckBox checkBoxCoordinador;
    @FXML
    private CheckBox checkBoxJefe;
    @FXML
    private Label labelInvalidateNombre;
    @FXML
    private Label labelInvalidateApellidoPaterno;
    @FXML
    private Label labelInvalidateApellidoMaterno;
    @FXML
    private Label labelInvalidateCorreo;
    @FXML
    private CheckBox checkBoxTutor;
    private ObservableList<PersonalUser> listPersonal;
    private boolean[] validationTextFields = {true, true, true, true, true};
    private Pattern validateCharacter = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$");
    private Pattern validateCharacterEmail = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private PersonalUser personalUserSelected;
    private int deleteProfesor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configureTableColumns();
        configureScene();
    }

    private void configureScene() {
        disableCheckbox();
        buttonDelete.setDisable(true);
        buttonSave.setDisable(true);
        textFieldSearchPersonal.setDisable(true);
        textApellidoPaterno.setDisable(true);
        textApellidoMaterno.setDisable(true);
        textCorreo.setDisable(true);
        textNombre.setDisable(true);
        buttonRegister.setVisible(false);
        checkBoxTutor.setVisible(false);
        loadInformationPersonalUsuarios();
        loadInformationPersonalProfesores();        
    }

    private void configureTableColumns() {
        Label noticeLoadingTable = new Label("Cargando información, espere un momento...");
        tablePersonal.setPlaceholder(noticeLoadingTable);
        columNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        columNumeroDePersonal.setCellValueFactory(new PropertyValueFactory("numeroDePersonal"));
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
                    ArrayList<Profesor> loadedProfesor = profesorDAO.getProfesoresNoUser();
                    ArrayList<PersonalUser> listUsuarios = new ArrayList<>();
                    if (!loadedProfesor.isEmpty()) {
                        for (int i = 0; i < loadedProfesor.size(); i++) {
                            Personal usuario = new Profesor();
                            usuario.setNombre(loadedProfesor.get(i).getNombre());
                            usuario.setApellidoPaterno(loadedProfesor.get(i).getApellidoPaterno());
                            usuario.setApellidoMaterno(loadedProfesor.get(i).getApellidoMaterno());
                            usuario.setNumeroDePersonal(loadedProfesor.get(i).getNumeroDePersonal());
                            usuario.setCorreoElectronicoInstitucional(loadedProfesor.get(i).getCorreoElectronicoInstitucional());
                            PersonalUser personalUser = new PersonalUser();
                            ArrayList<Rol> listRoles = new ArrayList<>();
                            listRoles.add(new Rol(0, "Profesor"));
                            personalUser.setRol(listRoles);
                            personalUser.setPersonal(usuario);
                            listUsuarios.add(personalUser);
                        }
                        listPersonal.addAll(listUsuarios);
                        tablePersonal.setItems(listPersonal);
                    }
                    Platform.runLater(() -> {
                        Label noticeLoadingTable = new Label("Sin contenido...");
                        tablePersonal.setPlaceholder(noticeLoadingTable);
                    });
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
                    ArrayList<Usuario> loadedPersonal = userDAO.getAllUsersByProgramaEducativo("14203");
                    if (!loadedPersonal.isEmpty()) {
                        for (int i = 0; i < loadedPersonal.size(); i++) {
                            PersonalUser personalUser = new PersonalUser();
                            personalUser.setPersonal(loadedPersonal.get(i));
                            int numeroDePersonal = loadedPersonal.get(i).getNumeroDePersonal();
                            personalUser.setRol(userDAO.getAllUserRolesByNumeroDePersonal(numeroDePersonal, "14203"));
                            tablePersonal.getItems().add(personalUser);
                        }
                        textFieldSearchPersonal.setDisable(false);
                    }
                    Platform.runLater(() -> {
                        Label noticeLoadingTable = new Label("Sin contenido...");
                        tablePersonal.setPlaceholder(noticeLoadingTable);
                    });
                } catch (SQLException sqle) {
                    AlertManager.showAlert("Error", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);
                }
                return null;
            }
        };
        executorService.submit(loadInformationPersonalUsuarioTask);
        executorService.shutdown();
    }

    private void deletePersonal(int estatusPersonal) {
        try {
            boolean resulDeleteProfesor = false;
            boolean resultDeleteUsuario = false;
            if (personalUserSelected.getRoles().get(0).getRolName().equals("Profesor")) {
                resulDeleteProfesor = new ProfesorDAO().deleteProfesor(personalUserSelected.getNumeroDePersonal(), 0);
                resultDeleteUsuario = true;
            } else {
                resulDeleteProfesor = new ProfesorDAO().deleteProfesor(personalUserSelected.getNumeroDePersonal(), deleteProfesor);
                resultDeleteUsuario = new UserDAO().deleteUsuario(personalUserSelected.getNumeroDePersonal());
            }
            if (resulDeleteProfesor && resultDeleteUsuario) {
                AlertManager.showTemporalAlert(" ", "Acción realizada con éxito", 2);
                tablePersonal.getSelectionModel().clearSelection();
                tablePersonal.getItems().remove(personalUserSelected);
            }
        } catch (SQLException e) {
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, porfavor intentelo mas tarde", Alert.AlertType.ERROR);
            e.printStackTrace();
        } finally {
            clearFields();
            textFieldSearchPersonal.clear();
        }
    }    

    @FXML
    private void buttonActionDelete(ActionEvent event) {
        if (personalUserSelected != null) {
            AlertManager.showTemporalAlert("Aviso", "Estimado usuario, estamos trabajando en ello, vuelva pronto", 3);
        }
    }

    @FXML
    private void buttonActionExit(ActionEvent event) {
        WindowManager.NavigateToWindow(buttonDelete.getScene().getWindow(), "/GUI/FXMLMainMenu.fxml", "Menú");
    }

    @FXML
    private void buttonActionRegister(ActionEvent event) {
        //TODO
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
        clearFields();
        disableCheckbox();
        uncheckBox();
        if (!tablePersonal.getSelectionModel().isEmpty() && tablePersonal.getSelectionModel().getSelectedItem() != null) {
            personalUserSelected = tablePersonal.getSelectionModel().getSelectedItem();
            buttonDelete.setDisable(false);
            buttonSave.setDisable(false);
            textApellidoPaterno.setDisable(false);
            textApellidoMaterno.setDisable(false);
            textCorreo.setDisable(false);
            textNombre.setDisable(false);
            textNombre.setText(tablePersonal.getSelectionModel().getSelectedItem().getNombre());
            textApellidoPaterno.setText(tablePersonal.getSelectionModel().getSelectedItem().getApellidoPaterno());
            textApellidoMaterno.setText(tablePersonal.getSelectionModel().getSelectedItem().getApellidoMaterno());
            textCorreo.setText(tablePersonal.getSelectionModel().getSelectedItem().getCorreoElectronicoInstitucional());
            if (!personalUserSelected.getRoles().get(0).getRolName().equals("Profesor")) {
                loadCheckBox(tablePersonal.getSelectionModel().getSelectedItem());
            }
        }
    }

    @FXML
    private void validateInputsNombre(KeyEvent event) {
        Matcher matcher = validateCharacter.matcher(textNombre.getText());
        if (!matcher.matches()) {
            labelInvalidateNombre.setText("Datos invalidos");
            validationTextFields[0] = false;
        } else {
            labelInvalidateNombre.setText("");
            validationTextFields[0] = true;
        }
        enableButton();
    }

    @FXML
    private void validateLengthNombre(KeyEvent event) {
        textNombre.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 30) {
                return change;
            } else {
                return null;
            }
        }));
        if (textNombre.getText().length() > 30) {
            textNombre.clear();
        }
    }

    @FXML
    private void validateInputsApellidoPaterno(KeyEvent event) {
        Matcher matcher = validateCharacter.matcher(textApellidoPaterno.getText());
        if (!matcher.matches()) {
            labelInvalidateApellidoPaterno.setText("Datos invalidos");
            validationTextFields[1] = false;
        } else {
            labelInvalidateApellidoPaterno.setText("");
            validationTextFields[1] = true;
        }
        enableButton();
    }

    @FXML
    private void validateLengthApellidoPaterno(KeyEvent event) {
        textApellidoPaterno.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 20) {
                return change;
            } else {
                return null;
            }
        }));
        if (textApellidoPaterno.getText().length() > 20) {
            textApellidoPaterno.clear();
        }
    }

    @FXML
    private void validateInputsApellidoMaterno(KeyEvent event) {
        Matcher matcher = validateCharacter.matcher(textApellidoMaterno.getText());
        if (!matcher.matches()) {
            labelInvalidateApellidoMaterno.setText("Datos invalidos");
            validationTextFields[2] = false;
        } else {
            labelInvalidateApellidoMaterno.setText("");
            validationTextFields[2] = true;
        }
        enableButton();
    }

    @FXML
    private void validateLengthApellidoMaterno(KeyEvent event) {
        textApellidoMaterno.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 20) {
                return change;
            } else {
                return null;
            }
        }));
        if (textApellidoMaterno.getText().length() > 20) {
            textApellidoMaterno.setText("");
        }
    }

    @FXML
    private void validateInputsCorreo(KeyEvent event) {
        Matcher matcher = validateCharacterEmail.matcher(textCorreo.getText());
        if (!matcher.matches()) {
            labelInvalidateCorreo.setText("Datos invalidos");
            validationTextFields[3] = false;
        } else {
            labelInvalidateCorreo.setText("");
            validationTextFields[3] = true;
        }
        enableButton();
    }

    @FXML
    private void validateLengthCorreo(KeyEvent event) {
        textCorreo.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 45) {
                return change;
            } else {
                return null;
            }
        }));
        if (textCorreo.getText().length() > 45) {
            textCorreo.setText("");
        }
    }

    @FXML
    private void buttonActionSave(ActionEvent event) {
        if (personalUserSelected != null) {
            try {
                Profesor profesor = new Profesor();
                profesor.setNombre(textNombre.getText());
                profesor.setApellidoPaterno(textApellidoPaterno.getText());
                profesor.setApellidoMaterno(textApellidoMaterno.getText());
                profesor.setCorreoElectronicoInstitucional(textCorreo.getText());
                profesor.setNumeroDePersonal(personalUserSelected.getNumeroDePersonal());
                boolean resultUpdateUsuario = false;
                boolean resultUpdateProfesor = new ProfesorDAO().updateProfesor(profesor);
                if (!personalUserSelected.getRoles().get(0).getRolName().equals("Profesor")) {
                    Usuario usuario = new Usuario();
                    usuario.setNombre(textNombre.getText());
                    usuario.setApellidoPaterno(textApellidoPaterno.getText());
                    usuario.setApellidoMaterno(textApellidoMaterno.getText());
                    usuario.setCorreoElectronicoInstitucional(textCorreo.getText());
                    usuario.setNumeroDePersonal(personalUserSelected.getNumeroDePersonal());
                    resultUpdateUsuario = new UserDAO().updateUsuario(usuario);
                    deleteRoles(personalUserSelected);
                    saveRoles(personalUserSelected);
                }
                if (resultUpdateProfesor || resultUpdateUsuario) {
                    AlertManager.showTemporalAlert(" ", "Acción realizada con éxito", 2);
                    updateTable(profesor);
                }
            } catch (SQLException e) {
                AlertManager.showAlert("Error", "No hay conexión con la base de datos, porfavor intentelo mas tarde", Alert.AlertType.ERROR);
                e.printStackTrace();
            } finally {
                clearFields();
                textFieldSearchPersonal.clear();
            }
        }
    }

    public void updateTable(Personal personal) {     
        personalUserSelected.setNombre(personal.getNombre());
        personalUserSelected.setApellidoPaterno(personal.getApellidoPaterno());
        personalUserSelected.setApellidoMaterno(personal.getApellidoMaterno());
        personalUserSelected.setCorreoElectronicoInstitucional(personal.getCorreoElectronicoInstitucional());
    }

    private void deleteRoles(PersonalUser usuario) {
        try {
            for (int i = 0; i < usuario.getRoles().size(); i++) {
                Rol rol = usuario.getRoles().get(i);
                switch (rol.getRolName()) {
                    case "Jefe de Carrera":
                        if (!checkBoxJefe.isSelected()) {
                            new UserDAO().deleteRol(usuario.getNumeroDePersonal(), rol);
                            usuario.getRoles().remove(rol);
                        }
                        break;
                    case "Coordinador":
                        if (!checkBoxCoordinador.isSelected()) {
                            new UserDAO().deleteRol(usuario.getNumeroDePersonal(), rol);
                            usuario.getRoles().remove(rol);
                        }
                        break;
                    case "Tutor":
                        if (!checkBoxTutor.isSelected()) {
                            new UserDAO().deleteRol(usuario.getNumeroDePersonal(), rol);
                            usuario.getRoles().remove(rol);
                        }
                        break;
                    default:
                        uncheckBox();
                        break;
                }
            }
        } catch (SQLException e) {
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, porfavor intentelo mas tarde", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void saveRoles(PersonalUser usuario) {
        try {
            ArrayList<String> rolesString = new ArrayList<>();
            Rol rol = new Rol();
            rol.setProgramaEducativo(new ProgramaEducativo("14203", ""));
            for (int i = 0; i < usuario.getRoles().size(); i++) {
                rolesString.add(usuario.getRoles().get(i).getRolName());
            }
            if (checkBoxJefe.isSelected()) {
                if (!rolesString.contains("Jefe de Carrera")) {
                    rol.setIdRol(1);
                    rol.setRolName("Jefe de Carrera");
                    new UserDAO().registerRol(usuario.getNumeroDePersonal(), rol);
                    usuario.getRoles().add(rol);
                }
            }
            if (checkBoxCoordinador.isSelected()) {
                if (!rolesString.contains("Coordinador")) {
                    rol.setIdRol(2);
                    rol.setRolName("Coordinador");
                    new UserDAO().registerRol(usuario.getNumeroDePersonal(), rol);
                    usuario.getRoles().add(rol);
                }
            }
            if (checkBoxTutor.isSelected()) {
                if (!rolesString.contains("Tutor")) {
                    rol.setIdRol(3);
                    rol.setRolName("Tutor");
                    new UserDAO().registerRol(usuario.getNumeroDePersonal(), rol);
                    usuario.getRoles().add(rol);
                }
            }
        } catch (SQLException e) {
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, porfavor intentelo mas tarde", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void enableButton() {
        if (!validationTextFields[0] || !validationTextFields[1] || !validationTextFields[2] || !validationTextFields[3]) {
            buttonSave.setDisable(true);            
        } else {
            buttonSave.setDisable(false);
        }
    }

    private void clearFields() {
        textNombre.clear();
        textApellidoPaterno.clear();
        textApellidoMaterno.clear();
        textCorreo.clear();
        textCorreo.setDisable(true);
        textApellidoPaterno.setDisable(true);
        textApellidoMaterno.setDisable(true);
        textNombre.setDisable(true);
        labelInvalidateNombre.setText("");
        labelInvalidateApellidoPaterno.setText("");
        labelInvalidateApellidoMaterno.setText("");
        labelInvalidateCorreo.setText("");
        buttonSave.setDisable(true);
        buttonDelete.setDisable(true);
        uncheckBox();
        disableCheckbox();
    }
    
    private void loadCheckBox(PersonalUser usuario) {
        checkBoxTutor.setDisable(false);
        checkBoxJefe.setDisable(false);
        checkBoxCoordinador.setDisable(false);
        uncheckBox();
        for (int i = 0; i < usuario.getRoles().size(); i++) {
            String rol = usuario.getRoles().get(i).getRolName();
            switch (rol) {
                case "Jefe de Carrera":
                    checkBoxJefe.setSelected(true);
                    break;
                case "Coordinador":
                    checkBoxCoordinador.setSelected(true);
                    break;
                case "Tutor":
                    checkBoxTutor.setSelected(true);
                    break;
                default:
                    uncheckBox();
                    break;
            }
        }
    }

    private void disableCheckbox() {
        checkBoxTutor.setDisable(true);
        checkBoxJefe.setDisable(true);
        checkBoxCoordinador.setDisable(true);
    }

    private void uncheckBox() {
        checkBoxTutor.setSelected(false);
        checkBoxJefe.setSelected(false);
        checkBoxCoordinador.setSelected(false);
    }
    
    private void filterTable() {
        FilteredList<PersonalUser> filteredPersonal = new FilteredList<>(listPersonal, b -> true);
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
        SortedList<PersonalUser> sortedListPersonal = new SortedList<>(filteredPersonal);
        sortedListPersonal.comparatorProperty().bind(tablePersonal.comparatorProperty());
        tablePersonal.setItems(sortedListPersonal);
    }    

}
