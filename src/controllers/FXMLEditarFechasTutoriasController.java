/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import BussinessLogic.PeriodoEscolarDAO;
import BussinessLogic.TutoriaAcademicaDAO;
import Domain.PeriodoEscolar;
import Domain.TutoriaAcademica;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import singleton.User;
import util.AlertManager;
import util.WindowManager;

/**
 * FXML Controller class
 *
 * @author Panther
 */
public class FXMLEditarFechasTutoriasController implements Initializable {

    private ArrayList<TutoriaAcademica> listedTutoriasAcademicas;
    private ObservableList<Integer> listTutoriasAcademicas;

    private TutoriaAcademica tutoriaAcademica;
    private PeriodoEscolar periodoEscolar;

    @FXML
    private DatePicker dpStartDate;
    @FXML
    private Label lbPeriodo;
    @FXML
    private ComboBox<Integer> cbNumSesion;
    @FXML
    private DatePicker dpEndDate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDatesWindow();

    }

    private void loadDatesWindow() {
        listedTutoriasAcademicas = new ArrayList<>();
        listTutoriasAcademicas = FXCollections.observableArrayList();
        periodoEscolar = new PeriodoEscolar();
        loadCurrentPeriodoEscolar();
        loadCombobox();
    }

    private void loadCurrentPeriodoEscolar() {
        try {
            PeriodoEscolarDAO PeriodoEscolarDAO = new PeriodoEscolarDAO();
            periodoEscolar = PeriodoEscolarDAO.getCurrentPeriodo();
            lbPeriodo.setText(periodoEscolar.getFechasPeridoEscolar());
        } catch (SQLException ex) {
            AlertManager.showAlert("Error", "No hay conexión con la base de datos. Inténtelo más tarde", Alert.AlertType.ERROR);
        }
    }

    private void loadCombobox() {
        getTutoriasAcademicas();
        if (!listedTutoriasAcademicas.isEmpty()) {
            for (TutoriaAcademica ta : listedTutoriasAcademicas) {
                listTutoriasAcademicas.add(ta.getNumeroDeSesion());
            }
            cbNumSesion.setItems(listTutoriasAcademicas);

        }
    }

    private void getTutoriasAcademicas() {
        try {
            TutoriaAcademicaDAO tutoriaAcademicaDAO = new TutoriaAcademicaDAO();
            listedTutoriasAcademicas = tutoriaAcademicaDAO.getTutoriasAcademicasByPeriodo(
                    periodoEscolar.getIdPeriodoEscolar(),
                    User.getCurrentUser().getRol().getProgramaEducativo().getClave());
        } catch (SQLException ex) {
            ex.printStackTrace();
            AlertManager.showAlert("Error", "No hay conexión con la base de datos. Inténtelo más tarde", Alert.AlertType.ERROR);
        }
    }

    private boolean isValidDate() {
        boolean isValidDate = false;

        if (dpStartDate.getValue() != null && dpEndDate.getValue() != null) {
            
            java.sql.Date startDate = java.sql.Date.valueOf(dpStartDate.getValue());
            java.sql.Date endDate = java.sql.Date.valueOf(dpEndDate.getValue());

            if (startDate.before(endDate) && endDate.after(startDate) && !startDate.equals(endDate)) {
                if (startDate.after(periodoEscolar.getFechaInicio()) && startDate.before(periodoEscolar.getFechaFin())
                        && endDate.after(periodoEscolar.getFechaInicio()) && endDate.before(periodoEscolar.getFechaFin())) {
                    
                    isValidDate = true;
                    
                } else {
                    
                    isValidDate = false;
                    AlertManager.showAlert("Advertencia", "Fechas fuera del Periodo Actual", Alert.AlertType.WARNING);
                    
                }
            } else {
                
                isValidDate = false;
                AlertManager.showAlert("Advertencia", "Fechas traslapadas, favor de cambiarlas", Alert.AlertType.WARNING);
            }
        }
        return isValidDate;
    }

    @FXML
    private void enableDatePickers(ActionEvent event) {
        int index = cbNumSesion.getItems().indexOf(cbNumSesion.getSelectionModel().getSelectedItem());
        tutoriaAcademica = listedTutoriasAcademicas.get(index);
        dpStartDate.setValue(tutoriaAcademica.getFechaInicio().toLocalDate());
        dpEndDate.setValue(tutoriaAcademica.getFechaFin().toLocalDate());
        dpStartDate.setDisable(false);
        dpEndDate.setDisable(false);
    }

    @FXML
    private void clicSave(ActionEvent event) {
        if (isValidDate()) {
            java.sql.Date startDate = java.sql.Date.valueOf(dpStartDate.getValue());
            java.sql.Date endDate = java.sql.Date.valueOf(dpEndDate.getValue());
            
            tutoriaAcademica.setFechaInicio(startDate);
            tutoriaAcademica.setFechaFin(endDate);
            
            try {
                TutoriaAcademicaDAO tutoriaAcademicaDAO = new TutoriaAcademicaDAO();
                tutoriaAcademicaDAO.updateFechasTutoriaAcademica(tutoriaAcademica);
                AlertManager.showAlert("Información", "Operacion Realizada con Éxito", Alert.AlertType.INFORMATION);
            } catch (SQLException ex) {
                Logger.getLogger(FXMLEditarFechasTutoriasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void clicCancel(ActionEvent event) {
        WindowManager.NavigateToWindow(lbPeriodo.getScene().getWindow(), "/GUI/FXMLMainMenu.fxml", "Menú");
    }
}
