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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import singleton.User;
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
    private ObservableList<PersonalUser> listPersonal;
    private boolean[] validationTextFields = {true, true, true, true, true};
    private Pattern validateCharacter = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$");
    private Pattern validateCharacterEmail = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private PersonalUser personalUserSelected;
    @FXML
    private CheckBox checkBoxAdministrador;

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
        buttonRegister.setVisible(true);
        loadInformationPersonalProfesores();        
        loadInformationPersonalUsuarios();        
    }

    private void configureTableColumns() {
        Label noticeLoadingTable = new Label("Sin coincidencias...");
        tablePersonal.setPlaceholder(noticeLoadingTable);
        columNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        columNumeroDePersonal.setCellValueFactory(new PropertyValueFactory("numeroDePersonal"));
        columApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        columApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        columRol.setCellValueFactory(new PropertyValueFactory("roles"));
        listPersonal = FXCollections.observableArrayList();
    }

    private void loadInformationPersonalProfesores() {
        ProfesorDAO profesorDAO = new ProfesorDAO();
        try {
            ArrayList<Profesor> loadedListProfesor = profesorDAO.getProfesoresNoUser();
            ArrayList<PersonalUser> listUsuarios = new ArrayList<>();
            if (!loadedListProfesor.isEmpty()) {
                for (int i = 0; i < loadedListProfesor.size(); i++) {
                    Personal usuario = new Profesor();
                    usuario.setNombre(loadedListProfesor.get(i).getNombre());
                    usuario.setApellidoPaterno(loadedListProfesor.get(i).getApellidoPaterno());
                    usuario.setApellidoMaterno(loadedListProfesor.get(i).getApellidoMaterno());
                    usuario.setNumeroDePersonal(loadedListProfesor.get(i).getNumeroDePersonal());
                    usuario.setCorreoElectronicoInstitucional(loadedListProfesor.get(i).getCorreoElectronicoInstitucional());
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
        } catch (SQLException sqle) {
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);
        }
    }

    private void loadInformationPersonalUsuarios() {
        UserDAO userDAO = new UserDAO();
        try {
            ArrayList<Usuario> loadedListPersonal = userDAO.getAllUsersByProgramaEducativo(User.getCurrentUser().getRol().getProgramaEducativo().getClave());
            if (!loadedListPersonal.isEmpty()) {
                for (int i = 0; i < loadedListPersonal.size(); i++) {
                    PersonalUser personalUser = new PersonalUser();
                    personalUser.setPersonal(loadedListPersonal.get(i));
                    int numeroDePersonal = loadedListPersonal.get(i).getNumeroDePersonal();
                    personalUser.setRol(userDAO.getAllUserRolesByNumeroDePersonal(numeroDePersonal, User.getCurrentUser().getRol().getProgramaEducativo().getClave()));
                    tablePersonal.getItems().add(personalUser);
                }
                textFieldSearchPersonal.setDisable(false);
            }
        } catch (SQLException sqle) {
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);
        }
    }

    private void deletePersonal() {
        try {
            boolean resulDeleteProfesor = false;
            boolean resultDeleteUsuario = false;
            PersonalUser personalUser = personalUserSelected;
            if (personalUserSelected.getRoles().get(0).getRolName().equals("Profesor")) {
                resulDeleteProfesor = new ProfesorDAO().deleteProfesor(personalUserSelected.getNumeroDePersonal());
                resultDeleteUsuario = true;
            } else {
                resulDeleteProfesor = new ProfesorDAO().deleteProfesor(personalUserSelected.getNumeroDePersonal());
                resultDeleteUsuario = new UserDAO().deleteUsuario(personalUserSelected.getNumeroDePersonal());
            }
            if (resulDeleteProfesor && resultDeleteUsuario) {
                AlertManager.showTemporalAlert(" ", "Acción realizada con éxito", 2);
                listPersonal.remove(personalUser);
            }
        } catch (SQLException e) {
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, porfavor intentelo mas tarde", Alert.AlertType.ERROR);
        } finally {
            clearFields();
            textFieldSearchPersonal.clear();
        }
    }

    @FXML
    private void buttonActionDelete(ActionEvent event) {
        if (personalUserSelected != null) {
            deletePersonal();
        }
    }

    @FXML
    private void buttonActionExit(ActionEvent event) {
        WindowManager.NavigateToWindow(buttonDelete.getScene().getWindow(), "/GUI/FXMLMainMenu.fxml", "Menú");
    }

    @FXML
    private void buttonActionRegister(ActionEvent event) {
        WindowManager.NavigateToWindow(
                labelInvalidateNombre.getScene().getWindow(),
                "/GUI/FXMLRegistrarProfesor.fxml",
                "Registrar Profesor"
        );
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
                    saveRoles(personalUserSelected);
                    deleteRoles(personalUserSelected);
                }
                if (resultUpdateProfesor || resultUpdateUsuario) {
                    AlertManager.showTemporalAlert(" ", "Acción realizada con éxito", 2);
                    updateTable(profesor);
                }
            } catch (SQLException e) {
                AlertManager.showAlert("Error", "No hay conexión con la base de datos, porfavor intentelo mas tarde", Alert.AlertType.ERROR);
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
                    case "Administrador":
                        if (!checkBoxAdministrador.isSelected()) {
                            new UserDAO().deleteRol(usuario.getNumeroDePersonal(), rol);
                            usuario.getRoles().remove(rol);
                        }
                        break;
                    default:
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
            rol.setProgramaEducativo(new ProgramaEducativo(User.getCurrentUser().getRol().getProgramaEducativo().getClave(), User.getCurrentUser().getRol().getProgramaEducativo().getNombre()));
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
            if (checkBoxAdministrador.isSelected()) {
                if (!rolesString.contains("Administrador")) {
                    rol.setIdRol(4);
                    rol.setRolName("Administrador");
                    new UserDAO().registerRol(usuario.getNumeroDePersonal(), rol);
                    usuario.getRoles().add(rol);
                }
            }
        } catch (SQLException e) {
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, porfavor intentelo mas tarde", Alert.AlertType.ERROR);
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
        checkBoxAdministrador.setDisable(false);
        checkBoxJefe.setDisable(false);
        checkBoxCoordinador.setDisable(false);
        uncheckBox();
        for (int i = 0; i < usuario.getRoles().size(); i++) {
            int rol = usuario.getRoles().get(i).getIdRol();
            switch (rol) {
                case 1:
                    checkBoxJefe.setSelected(true);
                    break;
                case 2:
                    checkBoxCoordinador.setSelected(true);
                    break;
                case 4:
                    checkBoxAdministrador.setSelected(true);
                    break;
                default:
                    break;
            }
        }
    }

    private void disableCheckbox() {
        checkBoxAdministrador.setDisable(true);
        checkBoxJefe.setDisable(true);
        checkBoxCoordinador.setDisable(true);
    }

    private void uncheckBox() {
        checkBoxAdministrador.setSelected(false);
        checkBoxJefe.setSelected(false);
        checkBoxCoordinador.setSelected(false);
    }
    
    private void filterTable() {
        FilteredList<PersonalUser> filteredListPersonal = new FilteredList<>(listPersonal, b -> true);
        textFieldSearchPersonal.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredListPersonal.setPredicate(Usuario -> {
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
        SortedList<PersonalUser> sortedListPersonal = new SortedList<>(filteredListPersonal);
        sortedListPersonal.comparatorProperty().bind(tablePersonal.comparatorProperty());
        tablePersonal.setItems(sortedListPersonal);
    }    

}
