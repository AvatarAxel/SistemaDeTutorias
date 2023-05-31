/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import BussinessLogic.EstudianteDAO;
import Domain.Estudiante;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import singleton.User;
import util.AlertManager;
import util.WindowManager;

/**
 * FXML Controller class
 *
 * @author Propietario
 */
public class FXMLImportarEstudiantesController implements Initializable {

    @FXML
    private TableView<Estudiante> tbEstudiantes;
    @FXML
    private TableColumn columnMatricula;
    @FXML
    private TableColumn columnNombre;
    @FXML
    private TableColumn columnApeliidoPaterno;
    @FXML
    private TableColumn columnApeliidoMaterno;
    @FXML
    private Button btSave;    

    private ObservableList<Estudiante> listEstudiantes;
    @FXML
    private Label lbProgramaEducativo;


    @Override
    public void initialize(URL url, ResourceBundle rb) {  
        configureTableEstudiantes();
        lbProgramaEducativo.setText(User.getCurrentUser().getRol().getProgramaEducativo().getNombre());
    }    

    
    private void configureTableEstudiantes() {
        Label noticeLoadingTable = new Label("Suba un archivo pulsando el botón 'Subir' ");
        tbEstudiantes.setPlaceholder(noticeLoadingTable);          
        columnMatricula.setCellValueFactory (new PropertyValueFactory ("matricula"));
        columnNombre.setCellValueFactory (new PropertyValueFactory ("nombre"));
        columnApeliidoPaterno.setCellValueFactory (new PropertyValueFactory ("apellidoPaterno"));
        columnApeliidoMaterno.setCellValueFactory (new PropertyValueFactory ("apellidoMaterno"));
        listEstudiantes = FXCollections.observableArrayList();
    }    
    private void loadTable(File file) throws IOException{
        ArrayList<Estudiante> loadedCleanEstudiantes = new ArrayList<>();
        ArrayList<Estudiante> loadedRawEstudiantes = new ArrayList<>();        
        if (file != null) {
            Label noticeLoadingTable = new Label("Cargando Archivo...");
            tbEstudiantes.setPlaceholder(noticeLoadingTable); 
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            EstudianteDAO estudianteDAO = new EstudianteDAO();
            String estudiante;
            boolean estudianteExists;
                while ((estudiante = bufferedReader.readLine()) != null) {
                    Estudiante loadedEstudiante =  new Estudiante();                
                    String[] atributes = estudiante.split(",");
                    loadedEstudiante.setMatricula(atributes[0]);
                    loadedEstudiante.setNombre(atributes[1]);
                    loadedEstudiante.setApellidoPaterno(atributes[2]);
                    loadedEstudiante.setApellidoMaterno(atributes[3]);
                    loadedRawEstudiantes.add(loadedEstudiante);
                }
                loadedRawEstudiantes.remove(0);                                   
                for (Estudiante e : loadedRawEstudiantes) {
                    boolean existeMatricula = false;
                    for (Estudiante s : loadedCleanEstudiantes) {
                        if (s.getMatricula().equals(e.getMatricula())) {
                            existeMatricula = true;
                            break;
                        }
                    }
                    if (!existeMatricula) {
                        loadedCleanEstudiantes.add(e);
                    }else{
                        AlertManager.showTemporalAlert("Aviso", "¡En el Arhcivo ya se repite la matricula: " + e.getMatricula()+" !\nSe Conservara el pirmer elemento, pero se le a conseja que revise el documento que intenta subir.", 5);                        
                    }
                }                 
        }
        loadedCleanEstudiantes.remove(0);
        btSave.setDisable(false);
        listEstudiantes.clear();
        listEstudiantes.addAll(loadedCleanEstudiantes);
        tbEstudiantes.setItems(listEstudiantes);
    }    
    

    @FXML
    private void clicSave(ActionEvent event) {
        ArrayList<Estudiante> listedEstudiantes = new ArrayList<>();
        listedEstudiantes.addAll(listEstudiantes);
        EstudianteDAO estudianteDAO = new EstudianteDAO();
        try {
            for (int i = 0; i < listedEstudiantes.size(); i++) {
                if(!estudianteDAO.validateExistEstudiante(listedEstudiantes.get(i).getMatricula())){
                    if(!estudianteDAO.setEstudianteRegister(listedEstudiantes.get(i),Integer.parseInt(User.getCurrentUser().getRol().getProgramaEducativo().getClave()))){
                        AlertManager.showTemporalAlert("Aviso", "¡Dato duplicado en la base de datos!", 2);
                    }                    
                }else{
                    AlertManager.showTemporalAlert("Aviso", "¡Registro con la Matricula: "+listedEstudiantes.get(i).getMatricula()+" YA Existe en el sistema!", 2);
                }
                if(listedEstudiantes.size()-1 == i){
                    AlertManager.showTemporalAlert("Confirmacíon", "La información se registró correctamente en el sistema", 2);
                    WindowManager.NavigateToWindow(tbEstudiantes.getScene().getWindow(), "/GUI/FXMLMainMenu.fxml", "Menú");        
                }
            }
        } catch (SQLException ex) {
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, intentelo mas tarde", Alert.AlertType.ERROR);
        }
    }
    @FXML
    private void clicUpload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Archivo");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos CSV", "*.csv"));
        File file = fileChooser.showOpenDialog(tbEstudiantes.getScene().getWindow());
        if(file != null){
            try {
                loadTable(file);
            } catch (IOException e) {
                AlertManager.showAlert("Error", "No se pudo cargar la información del archivo seleccionado", Alert.AlertType.ERROR);
            }
            
        }        
    }
    @FXML
    private void clicExit(ActionEvent event) {
        Optional<ButtonType> optionResult = AlertManager.showAlert("Confirmación", "¿Seguro de realizar dicha acción?", Alert.AlertType.CONFIRMATION);
        if(optionResult.get() == ButtonType.OK){
            WindowManager.NavigateToWindow(tbEstudiantes.getScene().getWindow(), "/GUI/FXMLMainMenu.fxml", "Menú");
        }        
    }    
    
}
