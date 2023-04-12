/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import BussinessLogic.ExperienciaEducativaDAO;
import Domain.ExperienciaEducativa;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.AlertManager;
import util.WindowManager;

/**
 * FXML Controller class
 *
 * @author Panther
 */
public class FXMLEditarOfertaAcademicaController implements Initializable {

    @FXML
    private TableView<ExperienciaEducativa> tbExperienciasEducativas;
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
    
    private ExperienciaEducativa experienciaEducativa;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        configureTableColumns();
        loadTable();
    }    

    private void configureTableColumns() {
        colNrc.setCellValueFactory (new PropertyValueFactory ("nrc"));
        colNombre.setCellValueFactory (new PropertyValueFactory ("nombre"));
        colProgramaEducativo.setCellValueFactory (new PropertyValueFactory ("programaEducativo"));
        colSeccion.setCellValueFactory (new PropertyValueFactory ("seccion"));
        colModalidad.setCellValueFactory (new PropertyValueFactory ("modalidad"));
        listExperienciasEducativas = FXCollections.observableArrayList();
    }
    
    private void loadTable(){
        ExperienciaEducativaDAO experienciasEducativas = new ExperienciaEducativaDAO();
        
        try{
            ArrayList<ExperienciaEducativa> loadedProblematicas = experienciasEducativas.getExperienciasEducativas();
            
            listExperienciasEducativas.clear();
            listExperienciasEducativas.addAll(loadedProblematicas);
            tbExperienciasEducativas.setItems(listExperienciasEducativas);
        }catch(SQLException sqle){
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);
        }
        
        
    }
    
    private void selectedValue(){
        int selectedRow = tbExperienciasEducativas.getSelectionModel().getSelectedIndex();
        if(selectedRow >= 0){
            experienciaEducativa = listExperienciasEducativas.get(selectedRow);
            //System.out.println("Error: "+experienciaEducativa.toString());
            //navigateToExperienciaEducativaDetails();
            WindowManager.NavigateToFloatingWindow(
                    tbExperienciasEducativas.getScene().getWindow(), 
                    experienciaEducativa,
                    "/GUI/FXMLEditarOfertaAcademicaDetalles.fxml",
                    "Titulo",
                    "passExperienciaEducativa");
            loadTable();
        }else{
            AlertManager.showAlert("Experiencia Educativa no seleccionada",
            "Debes seleccionar una Experiencia Educativa para continuar", 
            Alert.AlertType.WARNING);
        }
    }
    
    private void navigateToExperienciaEducativaDetails(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/FXMLEditarOfertaAcademicaDetalles.fxml"));
            Parent root = loader.load();
            FXMLEditarOfertaAcademicaDetallesController controladorFormulario = loader.getController();
            controladorFormulario.passExperienciaEducativa(experienciaEducativa);
            Scene primaryScene = new Scene(root);
            Stage floatingStage = new Stage();
            floatingStage.setScene(primaryScene);
            floatingStage.initModality(Modality.APPLICATION_MODAL);
            floatingStage.showAndWait();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void clicExit(ActionEvent event) {
        WindowManager.NavigateToWindow(tbExperienciasEducativas.getScene().getWindow(), "/GUI/FXMLMainMenu.fxml", "Menú");
    }

    @FXML
    private void clicExperienciaEducativa(MouseEvent event) {
        if(event.getClickCount() == 2){
            selectedValue();
        }
    }
    
}
