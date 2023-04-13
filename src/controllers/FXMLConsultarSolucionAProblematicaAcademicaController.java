/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import BussinessLogic.ProblematicaAcademicaDAO;
import Domain.ProblematicaAcademica;
import java.net.URL;
import java.sql.SQLException;
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
import util.AlertManager;
import util.WindowManager;

/**
 * FXML Controller class
 *
 * @author Panther
 */
public class FXMLConsultarSolucionAProblematicaAcademicaController implements Initializable {

    @FXML
    private TableView<ProblematicaAcademica> tbProblematicas;
    @FXML
    private TableColumn colDescripcion;
    @FXML
    private TableColumn colNumeroAlumnos;
    @FXML
    private TableColumn colSolucion;

    private ObservableList<ProblematicaAcademica> listProblematicas;
    
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
        colDescripcion.setCellValueFactory (new PropertyValueFactory ("descripcion"));
        colNumeroAlumnos.setCellValueFactory (new PropertyValueFactory ("numeroDeEstudiantesAfectados"));
        colSolucion.setCellValueFactory (new PropertyValueFactory ("solucion"));
        listProblematicas = FXCollections.observableArrayList();
    }
    
    private void loadTable(){
        ProblematicaAcademicaDAO problematicaAcademicaDAO = new ProblematicaAcademicaDAO();
        try{
            ArrayList<ProblematicaAcademica> loadedProblematicas = problematicaAcademicaDAO.getProblematicas();
            listProblematicas.clear();
            listProblematicas.addAll(loadedProblematicas);
            tbProblematicas.setItems(listProblematicas);
        }catch(SQLException sqle){
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);
        }
        
        
    }
    
    @FXML
    private void clicSalir(ActionEvent event) {
        WindowManager.NavigateToWindow(tbProblematicas.getScene().getWindow(), "/GUI/FXMLMainMenu.fxml", "Menú");
    }

    
}
