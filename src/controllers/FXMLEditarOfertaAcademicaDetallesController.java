/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import BussinessLogic.ExperienciaEducativaDAO;
import BussinessLogic.ProgramaEducativoDAO;
import Domain.ExperienciaEducativa;
import Domain.ProgramaEducativo;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import util.AlertManager;
import util.WindowManager;

/**
 * FXML Controller class
 *
 * @author Panther
 */
public class FXMLEditarOfertaAcademicaDetallesController implements Initializable {

    @FXML
    private TextField tfNrc;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfModalidad;
    @FXML
    private TextField tfSeccion;
    @FXML
    private ComboBox<ProgramaEducativo> cbProgramaEducativo;
    
    private ExperienciaEducativa experienciaEducativa;
    
    private ObservableList<ProgramaEducativo> listProgramasEducativos = FXCollections.observableArrayList();;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    private void loadFields(){
        tfNrc.setText(experienciaEducativa.getNrc());
        tfNrc.setEditable(false);
        tfNombre.setText(experienciaEducativa.getNombre());
        cbProgramaEducativo.setItems(listProgramasEducativos);
        tfModalidad.setText(experienciaEducativa.getModalidad());
        tfSeccion.setText(experienciaEducativa.getSeccion());
    }
    
    public void passExperienciaEducativa(ExperienciaEducativa experienciaEducativa) {
        this.experienciaEducativa = experienciaEducativa;
        
        try {
            ProgramaEducativoDAO programaEducativoDAO = new ProgramaEducativoDAO();
            listProgramasEducativos.addAll(programaEducativoDAO.getProgramasEducativos());
            cbProgramaEducativo.getSelectionModel().select(
                    new ProgramaEducativo(
                            experienciaEducativa.getClave(),
                            experienciaEducativa.getProgramaEducativo()
                    )
            );
            loadFields();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLEditarOfertaAcademicaDetallesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void clicCancel(ActionEvent event) {
        WindowManager.closeWindow(tfModalidad.getScene().getWindow());
    }

    @FXML
    private void clicSave(ActionEvent event) {
        ExperienciaEducativaDAO experienciaEducativaDAO = new ExperienciaEducativaDAO();
        
        experienciaEducativa.setNrc(tfNrc.getText());
        experienciaEducativa.setNombre(tfNombre.getText());
        experienciaEducativa.setProgramaEducativo(cbProgramaEducativo.getSelectionModel().getSelectedItem().getNombre());
        experienciaEducativa.setClave(cbProgramaEducativo.getSelectionModel().getSelectedItem().getClave());
        experienciaEducativa.setModalidad(tfModalidad.getText());
        experienciaEducativa.setSeccion(tfSeccion.getText());
        
        System.out.println("Programa Educativo: " + experienciaEducativa.getProgramaEducativo()+ " Clave: " + experienciaEducativa.getClave());
        try{
            experienciaEducativaDAO.updateAcademicOffer(experienciaEducativa);
        }catch(SQLException sqle){
            sqle.printStackTrace();
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);
        }
        WindowManager.closeWindow(tfModalidad.getScene().getWindow());
    }
    
}
