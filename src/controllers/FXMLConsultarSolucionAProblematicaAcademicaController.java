/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import BussinessLogic.PeriodoEscolarDAO;
import BussinessLogic.ProblematicaAcademicaDAO;
import Domain.PeriodoEscolar;
import Domain.ProblematicaAcademica;
import DomainGraphicInterface.ProblematicaReporteTutoria;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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

    private ObservableList<ProblematicaReporteTutoria> listAllProblematicas;
    private ObservableList<PeriodoEscolar> listPeriodosEscolares;
    private ObservableList<ProblematicaReporteTutoria> listProblematicasa;

    @FXML
    private TableView<ProblematicaReporteTutoria> tbProblematicas;
    @FXML
    private TableColumn colDescripcion;
    @FXML
    private TableColumn colNumeroAlumnos;
    @FXML
    private TableColumn colSolucion;
    @FXML
    private ComboBox<PeriodoEscolar> cbPeriodo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        configureTableColumns();
        loadTable();
        selectedItem();
    }

    private void configureTableColumns() {
        Label noticeLabel = new Label("Seleccione un periodo");
        tbProblematicas.setPlaceholder(noticeLabel);
        colDescripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));
        colNumeroAlumnos.setCellValueFactory(new PropertyValueFactory("numeroDeEstudiantesAfectados"));
        colSolucion.setCellValueFactory(new PropertyValueFactory("solucion"));
        listAllProblematicas = FXCollections.observableArrayList();
        listPeriodosEscolares = FXCollections.observableArrayList();
        listProblematicasa = FXCollections.observableArrayList();
    }

    private void loadTable() {
        ProblematicaAcademicaDAO problematicaAcademicaDAO = new ProblematicaAcademicaDAO();
        PeriodoEscolarDAO periodoEscolarDAO = new PeriodoEscolarDAO();
        try {
            ArrayList<PeriodoEscolar> loadedPeriodos = periodoEscolarDAO.getAllPeriodos();
            ArrayList<ProblematicaReporteTutoria> loadedProblematicas = problematicaAcademicaDAO.getAllProblematicas();

            listPeriodosEscolares.clear();
            listPeriodosEscolares.addAll(loadedPeriodos);
            cbPeriodo.setItems(listPeriodosEscolares);

            listAllProblematicas.clear();
            listAllProblematicas.addAll(loadedProblematicas);
            //tbProblematicas.setItems(listAllProblematicas);
        } catch (SQLException sqle) {
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);
        }

    }

    private void selectedItem() {
        cbPeriodo.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends PeriodoEscolar> observable, PeriodoEscolar oldValue, PeriodoEscolar newValue) -> {
            if (oldValue != null) {
            }
            if (newValue != null) {
                Label noticeLabel = new Label("No hay datos");
                tbProblematicas.setPlaceholder(noticeLabel);
                listProblematicasa.clear();
                for (ProblematicaReporteTutoria problema : listAllProblematicas) {
                    if (problema.getIdPeriodo() == newValue.getIdPeriodoEscolar()) {
                        listProblematicasa.add(problema);
                    }
                }
                tbProblematicas.setItems(listProblematicasa);
            }

        });
    }

    @FXML
    private void clicSalir(ActionEvent event) {
        WindowManager.NavigateToWindow(tbProblematicas.getScene().getWindow(), "/GUI/FXMLMainMenu.fxml", "Menú");
    }

}
