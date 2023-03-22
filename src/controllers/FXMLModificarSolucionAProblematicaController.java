/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import BussinessLogic.ProblematicaAcademicaDAO;
import BussinessLogic.SolucionAProblematicaDAO;
import Domain.ProblematicaAcademica;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import util.Alerts;
import util.Navigator;

/**
 * FXML Controller class
 *
 * @author Panther
 */
public class FXMLModificarSolucionAProblematicaController implements Initializable {

    @FXML
    private TableView<ProblematicaAcademica> tbProblematicas;
    @FXML
    private TableColumn colDescripcion;
    @FXML
    private TableColumn colNumeroAlumnos;
    @FXML
    private TableColumn colSolucion;
    @FXML
    private TextArea txtSolucion;

    private ObservableList<ProblematicaAcademica> listProblematicas;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        configureTableColumns();
        loadInformation();
        selectedItem();
    }

    private void configureTableColumns() {
        colDescripcion.setCellValueFactory (new PropertyValueFactory ("descripcion"));
        colNumeroAlumnos.setCellValueFactory (new PropertyValueFactory ("numeroDeEstudiantesAfectados"));
        colSolucion.setCellValueFactory (new PropertyValueFactory ("solucion"));
        listProblematicas = FXCollections.observableArrayList();
    }
    
    private void loadInformation(){
        ProblematicaAcademicaDAO problematicaAcademicaDAO = new ProblematicaAcademicaDAO();
        
        try{
            ArrayList<ProblematicaAcademica> loadedProblematicas = problematicaAcademicaDAO.getProblematicas();
            
            listProblematicas.clear();
            listProblematicas.addAll(loadedProblematicas);
            tbProblematicas.setItems(listProblematicas);
        }catch(SQLException sqle){
            Alerts.showAlert("Error", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);
        }
        
        
    }
    
    private void selectedItem(){
        
        tbProblematicas.getSelectionModel().selectedItemProperty().addListener(
            (ObservableValue<? extends ProblematicaAcademica> observable, ProblematicaAcademica oldValue, ProblematicaAcademica newValue) -> {
                if(oldValue != null){
                    tbProblematicas.getItems().get(listProblematicas.indexOf(oldValue)).getSolucion().setDescripcion(txtSolucion.getText());
                    //System.out.println("SoluciónViejo: "+ listProblematicas.get(listProblematicas.indexOf(oldValue)).getSolucion().getDescripcion());
                }
                if (newValue != null) {
                    //System.out.println("SoluciónNuevo: "+newValue.getSolucion().getDescripcion());
                    txtSolucion.setText(newValue.getSolucion().getDescripcion());
                    tbProblematicas.refresh();
                }
            }
        );
    }
    
    @FXML
    private void clicCancel(ActionEvent event) {
        Navigator.NavigateToWindow(tbProblematicas.getScene().getWindow(), "/GUI/FXMLMainMenu.fxml", "Menú");
    }

    @FXML
    private void clicSave(ActionEvent event) {
        SolucionAProblematicaDAO solucionAProblematica = new SolucionAProblematicaDAO();
        try {
            for(int i=0; i<listProblematicas.size(); i++){
                solucionAProblematica.updateSolucionAProblematica(listProblematicas.get(i).getSolucion().getIdSolucion(), listProblematicas.get(i).getSolucion().getDescripcion());
            }
            Alerts.showAlert("Finalizado", "Operación realizada con éxito", Alert.AlertType.INFORMATION);
            Navigator.NavigateToWindow(tbProblematicas.getScene().getWindow(), "/GUI/FXMLMainMenu.fxml", "Menú");
        } catch (SQLException ex) {
            Alerts.showAlert("Error", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);
        }
    }
    
}
