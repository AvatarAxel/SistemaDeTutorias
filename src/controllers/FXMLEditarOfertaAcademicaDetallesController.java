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
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import util.AlertManager;
import util.WindowManager;

/**
 * FXML Controller class
 *
 * @author Panther
 */
public class FXMLEditarOfertaAcademicaDetallesController implements Initializable {

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
        validateLengthNombre();
    }

    private void loadFields() {
        tfNrc.setText(experienciaEducativa.getNrc());
        tfNrc.setEditable(false);
        tfNombre.setText(experienciaEducativa.getNombre());
        cbProgramaEducativo.setItems(listProgramasEducativos);

        listModalidad.add("Virtual");
        listModalidad.add("Presencial");
        listModalidad.add("Hibrida");

        cbModalidad.setItems(listModalidad);
        cbModalidad.getSelectionModel().select(experienciaEducativa.getModalidad());
        //.setText(experienciaEducativa.getModalidad());

        listSeccion.add("1");
        listSeccion.add("2");
        listSeccion.add("3");

        cbSeccion.setItems(listSeccion);
        //.setText(experienciaEducativa.getSeccion());
        cbSeccion.getSelectionModel().select(experienciaEducativa.getSeccion());
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

    private void validateLengthNombre() {
        tfNombre.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 50) {
                tfNombre.setText(oldValue);
            }
        });

    }

    @FXML
    private void clicCancel(ActionEvent event) {
        WindowManager.closeWindow(tfNrc.getScene().getWindow());
    }

    @FXML
    private void clicSave(ActionEvent event) {
        ExperienciaEducativaDAO experienciaEducativaDAO = new ExperienciaEducativaDAO();

        experienciaEducativa.setNrc(tfNrc.getText());
        experienciaEducativa.setNombre(tfNombre.getText());
        experienciaEducativa.setProgramaEducativo(cbProgramaEducativo.getSelectionModel().getSelectedItem().getNombre());
        experienciaEducativa.setClave(cbProgramaEducativo.getSelectionModel().getSelectedItem().getClave());
        experienciaEducativa.setModalidad(cbModalidad.getSelectionModel().getSelectedItem());
        experienciaEducativa.setSeccion(cbSeccion.getSelectionModel().getSelectedItem());

        try {
            experienciaEducativaDAO.updateAcademicOffer(experienciaEducativa);
        } catch (SQLException sqle) {
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);
        }
        WindowManager.closeWindow(tfNrc.getScene().getWindow());
    }

    @FXML
    private void validateInputNombre(KeyEvent event) {
        final String ALLOWED_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\\\\\]^_`{|}~]";
        tfNombre.setTextFormatter(new TextFormatter<String>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[" + ALLOWED_CHARACTERS + " ]*")) {
                return change;
            }
            return null;
        }));

    }
}
