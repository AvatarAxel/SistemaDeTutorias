/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import BussinessLogic.ProfesorDAO;
import BussinessLogic.TutorAcademicoDAO;
import BussinessLogic.UserDAO;
import Domain.Profesor;
import Domain.ProgramaEducativo;
import Domain.Rol;
import Domain.TutorAcademico;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.beans.binding.Bindings;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import security.SHA_512;
import util.AlertManager;
import util.EmailUtil;
import util.Random;
import util.WindowManager;

/**
 * FXML Controller class
 *
 * @author Propietario
 */
public class FXMLAsignarPersonalProgramaEducativoController implements Initializable {

    @FXML
    private TableView<Profesor> tablePersonal;
    @FXML
    private TableColumn columNumeroDePersonal;
    @FXML
    private TableColumn columNombre;
    @FXML
    private TableColumn columApellidoPaterno;
    @FXML
    private TableColumn columApellidoMaterno;
    @FXML
    private RadioButton rbUsuarioRegistrado;
    @FXML
    private RadioButton rbUsuarioNuevo;
    @FXML
    private TextField textFieldSearchPersonal;
    @FXML
    private Label lbInstrucciones;
    @FXML
    private Button btAsignar;
    
    private ToggleGroup toggleGroup = new ToggleGroup();

    private ObservableList<Profesor> listPersonal;
    private ArrayList<ArrayList<Profesor>> Profesores = new ArrayList<ArrayList<Profesor>>();   
    private ObservableList<Profesor> listProfesorEsUsuario;
    private ObservableList<Profesor> listProfesorNoEsUsuario;
    private ProgramaEducativo programaEducativo;



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btAsignar.disableProperty().bind(Bindings.isEmpty(tablePersonal.getSelectionModel().getSelectedItems()));        
        configureTableColumns();
        loadProfesores();        
    }    
    
    public void loadWindow(ProgramaEducativo programaEducativo){
        this.programaEducativo = new ProgramaEducativo();
        this.programaEducativo = programaEducativo;
        lbInstrucciones.setText("Selecicone al nuevo coordinador del Programa Educativo: "+programaEducativo.getNombre());
    }

    private void configureTableColumns() {
        Label noticeLoadingTable = new Label("Cargando información, espere un momento...");
        tablePersonal.setPlaceholder(noticeLoadingTable);
        columNumeroDePersonal.setCellValueFactory(new PropertyValueFactory("numeroDePersonal"));        
        columNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        columApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        columApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        listPersonal = FXCollections.observableArrayList();
        listProfesorNoEsUsuario = FXCollections.observableArrayList();
        listProfesorEsUsuario = FXCollections.observableArrayList();
    }    

    private void loadProfesores(){
        ProfesorDAO profesorDAO  = new ProfesorDAO();   
        try {
            Profesores = profesorDAO.getRegistradosYNoRegistrados();
            ArrayList<Profesor> profesoresRegistrados = new ArrayList<>();
            ArrayList<Profesor> profesoresNORegistrados = new ArrayList<>();            
            profesoresRegistrados = Profesores.get(0);
            profesoresNORegistrados = Profesores.get(1); 
            if(profesoresRegistrados.isEmpty() && profesoresNORegistrados.isEmpty()){
                Label noticeLoadingTable = new Label("No Hay Usuarios Disponibles");
                tablePersonal.setPlaceholder(noticeLoadingTable);               
                rbUsuarioRegistrado.setSelected(false);
                rbUsuarioNuevo.setSelected(false);
                textFieldSearchPersonal.setText("No hay Usuarios Disponibles");
                textFieldSearchPersonal.setDisable(false);
            }else{
                if (!profesoresRegistrados.isEmpty()) {
                    for (Profesor profesor : profesoresRegistrados) {
                        listProfesorEsUsuario.add(profesor);
                    }               
                }
                if (!profesoresNORegistrados.isEmpty()) {
                    for (Profesor profesor : profesoresNORegistrados) {
                        listProfesorNoEsUsuario.add(profesor);
                    }
                }
                setProfesoresTale();             
            }  
        } catch (SQLException ex) {
            AlertManager.showAlert("Error LOAD", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);                    
            ex.printStackTrace();
        }
    }
    
    private void setProfesoresTale(){
        int numTotalProfesoresUsuarios = listProfesorEsUsuario.size();
        int numTotalProfesoresNoUsuarios = listProfesorNoEsUsuario.size();
        if(numTotalProfesoresUsuarios != 0 && numTotalProfesoresNoUsuarios != 0){
            rbUsuarioRegistrado.setToggleGroup(toggleGroup);
            rbUsuarioNuevo.setToggleGroup(toggleGroup);
            rbUsuarioRegistrado.setSelected(true);
            ObservableList<Profesor> liempieza = FXCollections.observableArrayList();
            liempieza.clear();
            tablePersonal.setItems(liempieza);
            tablePersonal.setItems(listProfesorEsUsuario); 
        }else if(numTotalProfesoresUsuarios != 0 && numTotalProfesoresNoUsuarios == 0){
            rbUsuarioRegistrado.setSelected(true);
            rbUsuarioNuevo.setDisable(true);
            rbUsuarioRegistrado.setDisable(true);                        
            ObservableList<Profesor> liempieza = FXCollections.observableArrayList();
            liempieza.clear();
            tablePersonal.setItems(liempieza);
            tablePersonal.setItems(listProfesorEsUsuario);        
        }else if(numTotalProfesoresUsuarios == 0 && numTotalProfesoresNoUsuarios != 0){
            rbUsuarioNuevo.setSelected(true);
            rbUsuarioRegistrado.setDisable(true); 
            rbUsuarioNuevo.setDisable(true);                        
            ObservableList<Profesor> liempieza = FXCollections.observableArrayList();
            liempieza.clear();
            tablePersonal.setItems(liempieza);
            tablePersonal.setItems(listProfesorNoEsUsuario);           
        }     
    }
    
    private void filterTable() {
        FilteredList<Profesor> filteredProfesor;
        if (rbUsuarioRegistrado.isSelected()) {
            filteredProfesor = new FilteredList<>(listProfesorEsUsuario, b -> true);
        } else if (rbUsuarioNuevo.isSelected()) {
            filteredProfesor = new FilteredList<>(listProfesorNoEsUsuario, b -> true);
        }else{
            filteredProfesor = new FilteredList<>(listPersonal, b -> true);
        }
        textFieldSearchPersonal.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredProfesor.setPredicate(Usuario -> {
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
        SortedList<Profesor> sortedListPersonal = new SortedList<>(filteredProfesor);
        sortedListPersonal.comparatorProperty().bind(tablePersonal.comparatorProperty());
        tablePersonal.setItems(sortedListPersonal);
    }
    
    private void notifyTheNewUser(String tutorEmail, String randomPassword) {        
        ExecutorService executorService;
        executorService = Executors.newFixedThreadPool(1);
        Task sendEmailTask = new Task() {
            @Override
            protected Void call() throws Exception {
                EmailUtil email = new EmailUtil();
                email.sendEmailNewUser(tutorEmail, randomPassword);
                return null;
            }
        };
        executorService.submit(sendEmailTask);
        executorService.shutdown();
    }  
    
    private void closeWindow() {
        WindowManager.closeWindow(lbInstrucciones.getScene().getWindow());
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
    private void filterTableUser(ActionEvent event) {
        if(!listProfesorEsUsuario.isEmpty()){
            ObservableList<Profesor> liempieza = FXCollections.observableArrayList();
            liempieza.clear();
            tablePersonal.setItems(liempieza);
            tablePersonal.setItems(listProfesorEsUsuario);        
        }else{
            Label noticeLoadingTable = new Label("No Hay Información Disponibles");
            tablePersonal.setPlaceholder(noticeLoadingTable);            
        }
    }

    @FXML
    private void filterTableNotUser(ActionEvent event) {
        if(!listProfesorNoEsUsuario.isEmpty()){
            ObservableList<Profesor> liempieza = FXCollections.observableArrayList();
            liempieza.clear();
            tablePersonal.setItems(liempieza);
            tablePersonal.setItems(listProfesorNoEsUsuario);     
        }else{
            Label noticeLoadingTable = new Label("No Hay Información Disponibles");
            tablePersonal.setPlaceholder(noticeLoadingTable);            
        }             
    }

    @FXML
    private void clicButtonAsignar(ActionEvent event) {
        try {
            Optional<ButtonType> answer;
            answer = AlertManager.showAlert("AVISO", "Se asignará como coordinador a: \n" +
                tablePersonal.getSelectionModel().getSelectedItem().getNombreCompleto() +"\n" +
                "Al Programa educativo:\n" +
                "("+programaEducativo.getClave()+") "+programaEducativo.getNombre()+"\n" +
                "¿Desea continuar?", Alert.AlertType.CONFIRMATION);            
            if (answer.get() == ButtonType.OK) {
                Rol rol = new Rol();
                rol.setProgramaEducativo(programaEducativo);  
                rol.setIdRol(2);
                rol.setRolName("Coordinador");
                if(rbUsuarioNuevo.isSelected()){
                    String randomPassword = new Random().passwordGenerator();      
                    ProfesorDAO profesorDao = new ProfesorDAO();
                    TutorAcademicoDAO tutorAcademicoDao = new TutorAcademicoDAO();
                    TutorAcademico tutorAcademico = new TutorAcademico();            
                    tutorAcademico.setNumeroDePersonal(tablePersonal.getSelectionModel().getSelectedItem().getNumeroDePersonal());            
                    tutorAcademico.setNombre(tablePersonal.getSelectionModel().getSelectedItem().getNombre());
                    tutorAcademico.setApellidoPaterno(tablePersonal.getSelectionModel().getSelectedItem().getApellidoPaterno());
                    tutorAcademico.setApellidoMaterno(tablePersonal.getSelectionModel().getSelectedItem().getApellidoMaterno());
                    tutorAcademico.setCorreoElectronicoInstitucional(tablePersonal.getSelectionModel().getSelectedItem().getCorreoElectronicoInstitucional());
                    tutorAcademico.setContraseña(new SHA_512().getSHA512(randomPassword));
                    boolean resultRegister = tutorAcademicoDao.setTutorRegister(tutorAcademico);
                    boolean markRegistration = profesorDao.setTutorUser(tablePersonal.getSelectionModel().getSelectedItem().getNumeroDePersonal());
                    if (resultRegister && markRegistration) {
                        notifyTheNewUser(tutorAcademico.getCorreoElectronicoInstitucional(), randomPassword);                                                                
                    }                    
                }
                boolean resultRolAssignment = new UserDAO().registerRol(tablePersonal.getSelectionModel().getSelectedItem().getNumeroDePersonal(), rol);                                
                AlertManager.showTemporalAlert("AVISO", "Asignación realizado con éxito", 2);
                closeWindow();
            }                         
        } catch (SQLException e) {
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, porfavor intentelo mas tarde HETTTT", Alert.AlertType.ERROR);
            e.printStackTrace();
        }            
    }
        
}
