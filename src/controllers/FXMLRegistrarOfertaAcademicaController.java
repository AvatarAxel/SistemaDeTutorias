/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import Domain.ExperienciaEducativa;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import util.Alerts;
import util.Navigator;

/**
 * FXML Controller class
 *
 * @author Panther
 */
public class FXMLRegistrarOfertaAcademicaController implements Initializable {

    @FXML
    private TableView<ExperienciaEducativa> tbExperiencias;
    @FXML
    private TableColumn colNrc;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colProgramaEducativo;
    @FXML
    private TableColumn colSeccion;
    @FXML
    private TableColumn colModalidad;
    @FXML
    private TableColumn colSalon;
    @FXML
    private TableColumn colDescripción;
            
    private ObservableList<ExperienciaEducativa> listExperienciasEducativas;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        configureTableColumns();
    }    
    
    private void configureTableColumns() {
        colNrc.setCellValueFactory (new PropertyValueFactory ("nrc"));
        colNombre.setCellValueFactory (new PropertyValueFactory ("nombre"));
        colSeccion.setCellValueFactory (new PropertyValueFactory ("seccion"));
        colModalidad.setCellValueFactory (new PropertyValueFactory ("modalidad"));
        
        //<-- Estas columnas no existen en la base de datos -->
        colSalon.setCellValueFactory (new PropertyValueFactory ("nrc"));
        colDescripción.setCellValueFactory (new PropertyValueFactory ("nrc"));
        colProgramaEducativo.setCellValueFactory (new PropertyValueFactory ("nrc"));
        
        listExperienciasEducativas = FXCollections.observableArrayList();
    }

    private void loadInformation(File file) throws IOException{
        ArrayList<ExperienciaEducativa> loadedExperienciasEducativas = new ArrayList<>();
        
        if (file != null) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String experienciaEducativa;
            while ((experienciaEducativa = bufferedReader.readLine()) != null) {
                String[] atributes = experienciaEducativa.split(",");
                loadedExperienciasEducativas.add(new ExperienciaEducativa(atributes[0], atributes[1], atributes[2], atributes[3]));
            }
        }
        
        listExperienciasEducativas.clear();
        listExperienciasEducativas.addAll(loadedExperienciasEducativas);
        tbExperiencias.setItems(listExperienciasEducativas);
    }
     
    
    @FXML
    private void clicExit(ActionEvent event) {
        Navigator.NavigateToWindow(tbExperiencias.getScene().getWindow(), "/GUI/FXMLMainMenu.fxml", "Menú");
    }

    @FXML
    private void clicSave(ActionEvent event) {
        Navigator.NavigateToWindow(tbExperiencias.getScene().getWindow(), "/GUI/FXMLMainMenu.fxml", "Menú");
        //TODO: Save into the Database
    }

    @FXML
    private void clicUpload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Archivo");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Archivos CSV", "*.csv"));
        File file = fileChooser.showOpenDialog(tbExperiencias.getScene().getWindow());
        if(file != null){
            
            try {
                loadInformation(file);
            } catch (IOException e) {
                Alerts.showAlert("Error", "No se pudo cargar la información del archivo seleccionado", Alert.AlertType.ERROR);
            }
            
        }
    }
    
}
