/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import BussinessLogic.ExperienciaEducativaDAO;
import Domain.ExperienciaEducativa;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import util.AlertManager;
import util.WindowManager;

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
        colProgramaEducativo.setCellValueFactory (new PropertyValueFactory ("clave"));
        listExperienciasEducativas = FXCollections.observableArrayList();
    }

    private void loadTable(File file) throws IOException{
        ArrayList<ExperienciaEducativa> loadedExperienciasEducativas = new ArrayList<>();
        
        if (file != null) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String experienciaEducativa;
            while ((experienciaEducativa = bufferedReader.readLine()) != null) {
                String[] atributes = experienciaEducativa.split(",");
                loadedExperienciasEducativas.add(new ExperienciaEducativa(atributes[0], atributes[1], atributes[2], atributes[3],atributes[4]));
            }
        }
        loadedExperienciasEducativas.remove(0);
        listExperienciasEducativas.clear();
        listExperienciasEducativas.addAll(loadedExperienciasEducativas);
        tbExperiencias.setItems(listExperienciasEducativas);
    }
     
    
    @FXML
    private void clicExit(ActionEvent event) {
        Optional<ButtonType> optionResult = AlertManager.showAlert("Confirmación", "¿Seguro de realizar dicha acción?", Alert.AlertType.CONFIRMATION);

        if(optionResult.get() == ButtonType.OK){
            WindowManager.NavigateToWindow(tbExperiencias.getScene().getWindow(), "/GUI/FXMLMainMenu.fxml", "Menú");
        }
    }

    @FXML
    private void clicSave(ActionEvent event) {
        ArrayList<ExperienciaEducativa> listedExperienciasEducativas = new ArrayList<>();
        listedExperienciasEducativas.addAll(listExperienciasEducativas);
        ExperienciaEducativaDAO experienciaEducativaDAO = new ExperienciaEducativaDAO();
        
        try {
            for (int i = 0; i < listedExperienciasEducativas.size(); i++) {
                if(!experienciaEducativaDAO.existNrc(listedExperienciasEducativas.get(i).getNrc())){
                    experienciaEducativaDAO.uploadAcademicOffer(listedExperienciasEducativas.get(i));
                    tbExperiencias.getItems().remove(listedExperienciasEducativas.get(i));
                    tbExperiencias.refresh();
                }
            }
            if(!tbExperiencias.getItems().isEmpty()){
                AlertManager.showAlert("Advertencia", "Se detectaron NRC repetidos, por lo tanto, esos registros fueron descartados, favor de cambiarlos", Alert.AlertType.WARNING);
            }else{
                WindowManager.NavigateToWindow(
                        tbExperiencias.getScene().getWindow(),
                        "/GUI/FXMLMainMenu.fxml",
                        "Menú");
            }
        } catch (SQLException ex) {
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, intentelo mas tarde", Alert.AlertType.ERROR);
        }
        
    }

    @FXML
    private void clicUpload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Archivo");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Archivos CSV", "*.csv"));
        File file = fileChooser.showOpenDialog(tbExperiencias.getScene().getWindow());
        if(file != null){
            
            try {
                loadTable(file);
            } catch (IOException e) {
                AlertManager.showAlert("Error", "No se pudo cargar la información del archivo seleccionado", Alert.AlertType.ERROR);
            }
            
        }
    }
    
}
