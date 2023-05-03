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
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import util.AlertManager;
import util.WindowManager;

/**
 * FXML Controller class
 *
 * @author Panther
 */
public class FXMLRegistrarExperienciaEducativaController implements Initializable {

    private ExperienciaEducativa experienciaEducativa;
    private ObservableList<ProgramaEducativo> listProgramasEducativos = FXCollections.observableArrayList();
    private ObservableList<String> listModalidad = FXCollections.observableArrayList();
    private ObservableList<String> listSeccion = FXCollections.observableArrayList();

    @FXML
    private TextField tfNrc;
    @FXML
    private TextField tfNombre;
    @FXML
    private ComboBox<ProgramaEducativo> cbProgramaEducativo;
    @FXML
    private ComboBox<String> cbModalidad;
    @FXML
    private ComboBox<String> cbSeccion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadComboBox();
        validateLengthNRC();
        validateLengthNombre();
    }

    private void loadComboBox() {
        try {
            ArrayList<ProgramaEducativo> programasEducativos = new ArrayList<>();

            ProgramaEducativoDAO programaEducativoDAO = new ProgramaEducativoDAO();
            programasEducativos = programaEducativoDAO.getProgramasEducativos();
            listProgramasEducativos.addAll(programasEducativos);
            cbProgramaEducativo.setItems(listProgramasEducativos);

            listModalidad.add("Virtual");
            listModalidad.add("Presencial");
            listModalidad.add("Hibrida");

            cbModalidad.setItems(listModalidad);

            listSeccion.add("1");
            listSeccion.add("2");
            listSeccion.add("3");

            cbSeccion.setItems(listSeccion);

        } catch (SQLException ex) {
            Logger.getLogger(FXMLRegistrarExperienciaEducativaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void validateLengthNRC() {
        tfNrc.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 5) {
                tfNrc.setText(oldValue);
            }
        });
        
    }
    
    private void validateLengthNombre() {
        tfNombre.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 50) {
                tfNombre.setText(oldValue);
            }
        });
        
    }
    
    @FXML
    private void clicCancel(ActionEvent event) {
        WindowManager.NavigateToWindow(cbProgramaEducativo.getScene().getWindow(), "/GUI/FXMLMainMenu.fxml", "Menú");
    }

    @FXML
    private void clicSave(ActionEvent event) {
        experienciaEducativa = new ExperienciaEducativa();

        experienciaEducativa.setNombre(tfNombre.getText());
        experienciaEducativa.setNrc(tfNrc.getText());
        experienciaEducativa.setClave(cbProgramaEducativo.getSelectionModel().getSelectedItem().getClave());
        experienciaEducativa.setModalidad(cbModalidad.getSelectionModel().getSelectedItem());
        experienciaEducativa.setSeccion(cbSeccion.getSelectionModel().getSelectedItem());

        ExperienciaEducativaDAO experienciaEducativaDAO = new ExperienciaEducativaDAO();

        try {
            if (!experienciaEducativaDAO.existNrc(tfNrc.getText())) {
                if (experienciaEducativaDAO.uploadAcademicOffer(experienciaEducativa) == 1) {
                    AlertManager.showAlert("Exito", "Registro realizado con éxito", Alert.AlertType.INFORMATION);
                    tfNombre.setText("");
                    tfNrc.setText("");
                    cbProgramaEducativo.getSelectionModel().select(-1);
                    cbModalidad.getSelectionModel().select(-1);
                    cbSeccion.getSelectionModel().select(-1);
                }
            } else {
                AlertManager.showAlert("Advertencia", "El NRC ingresado ya se encuentra en el registro", Alert.AlertType.WARNING);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLRegistrarExperienciaEducativaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void validateInputNRC(KeyEvent event) {
        tfNrc.setTextFormatter(new TextFormatter<String>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        }));
    }


    @FXML
    private void validateInputNombre(KeyEvent event) {
        final String ALLOWED_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\\\\\]^_`{|}~]";
        tfNombre.setTextFormatter(new TextFormatter<String>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[" + ALLOWED_CHARACTERS + "]*")) {
                return change;
            }
            return null;
        }));
    }
}
