/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import BussinessLogic.ProblematicaAcademicaDAO;
import BussinessLogic.SolucionAProblematicaDAO;
import Domain.ProblematicaAcademica;
import Domain.SolucionAProblematica;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
import org.apache.commons.lang3.StringUtils;
import util.Alerts;
import util.Navigator;

/**
 * FXML Controller class
 *
 * @author Panther
 */
public class FXMLRegistrarSolucionAProblematicaController implements Initializable {

    @FXML
    private TableColumn colDescripcion;
    @FXML
    private TableColumn colProfesor;
    @FXML
    private TableColumn colGravedad;
    @FXML
    private TableColumn colNumeroAlumnos;
    @FXML
    private TextArea txtSolucion;
    @FXML
    private TableView<ProblematicaAcademica> tbProblematicas;
    
    private ObservableList<ProblematicaAcademica> listProblematicas;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        configureTableColumns();
        //loadInformation();
        loadInformationTest();
        selectedItem();
    }    

    private void configureTableColumns() {
        colDescripcion.setCellValueFactory (new PropertyValueFactory ("descripcion"));
        colProfesor.setCellValueFactory (new PropertyValueFactory ("profesor"));
        colGravedad.setCellValueFactory (new PropertyValueFactory ("gravedad"));
        colNumeroAlumnos.setCellValueFactory (new PropertyValueFactory ("numeroAlumnos"));
          
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
    
    private void loadInformationTest(){
        ArrayList<ProblematicaAcademica> loadedProblematicas = new ArrayList<>();
        
        loadedProblematicas.add(new ProblematicaAcademica(0, "A", 0, new SolucionAProblematica(0, "A")));
        loadedProblematicas.add(new ProblematicaAcademica(1, "A", 0, new SolucionAProblematica(0, "A")));
        loadedProblematicas.add(new ProblematicaAcademica(2, "A", 0, new SolucionAProblematica(0, "A")));
        loadedProblematicas.add(new ProblematicaAcademica(3, "A", 0, new SolucionAProblematica(0, "A")));
        loadedProblematicas.add(new ProblematicaAcademica(4, "A", 0, new SolucionAProblematica(0, "A")));
        loadedProblematicas.add(new ProblematicaAcademica(5, "A", 0, new SolucionAProblematica(0, "A")));
        loadedProblematicas.add(new ProblematicaAcademica(6, "A", 0, new SolucionAProblematica(0, "A")));
        
        listProblematicas.clear();
        listProblematicas.addAll(loadedProblematicas);
        tbProblematicas.setItems(listProblematicas);
    }
    
    private void selectedItem(){
        tbProblematicas.getSelectionModel().selectedItemProperty().addListener(
            (ObservableValue<? extends ProblematicaAcademica> observable, ProblematicaAcademica oldValue, ProblematicaAcademica newValue) -> {
                if(oldValue != null){
                    listProblematicas.get(listProblematicas.indexOf(oldValue)).getSolucion().setDescripcion(txtSolucion.getText());
                    //System.out.println("SoluciónViejo: "+ listProblematicas.get(listProblematicas.indexOf(oldValue)).getSolucion().getDescripcion());
                }
                if (newValue != null) {
                    //System.out.println("SoluciónNuevo: "+newValue.getSolucion().getDescripcion());
                    txtSolucion.setText(newValue.getSolucion().getDescripcion());
                    tbProblematicas.refresh();
                }
            });
    }
    
    @FXML
    private void clicCancel(ActionEvent event) {
        Navigator.NavigateToWindow(tbProblematicas.getScene().getWindow(), "/GUI/FXMLMainMenu.fxml", "Menú");
    }

    @FXML
    private void clicSave(ActionEvent event) {
        ObservableList<ProblematicaAcademica> problem = tbProblematicas.getItems();
        SolucionAProblematicaDAO solucionAProblematica = new SolucionAProblematicaDAO();
        for (ProblematicaAcademica problematicaAcademica : problem) {
            if(!StringUtils.isBlank(problematicaAcademica.getSolucion().getDescripcion())){
                try {
                    solucionAProblematica.insertSolucionAPRoblematica(problematicaAcademica.getIdProblematica(), problematicaAcademica.getSolucion().getDescripcion());
                } catch (SQLException sqle) {
                }
                System.out.println("Problem: " + problematicaAcademica.getSolucion().getDescripcion());
            }
        }
    }
    
}
