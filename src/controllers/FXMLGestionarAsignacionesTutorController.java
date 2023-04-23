package controllers;

import BussinessLogic.EstudianteDAO;
import BussinessLogic.ProfesorDAO;
import BussinessLogic.TutorAcademicoDAO;
import Domain.Estudiante;
import Domain.Tutor;
import Domain.TutorAcademico;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import util.AlertManager;

/**
 * FXML Controller class
 *
 * @author Valeria
 */
public class FXMLGestionarAsignacionesTutorController implements Initializable {

    @FXML
    private TableView<Estudiante> tblEstudiantes;
    @FXML
    private TableColumn clm_estudiantes;
    @FXML
    private TableColumn clm_tutor;
    @FXML
    private TableView<TutorAcademico> tbl_tutores;
    @FXML
    private TableColumn clm_estudiantesAcargo;
    @FXML
    private Button btn_asignar;
    @FXML
    private TextField txt_estudiante;
    @FXML
    private TextField txt_tutor;
    @FXML
    private TableColumn clm_currentTutor;

    ObservableList<Estudiante> estudiantesObservableList = FXCollections.observableArrayList();
    ObservableList<TutorAcademico> tutoresObservableList = FXCollections.observableArrayList();
    private final ListChangeListener<TutorAcademico> selectedTutor = new ListChangeListener<TutorAcademico>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends TutorAcademico> c) {
            TutorAcademico item = new TutorAcademico();
            item = getTablaTutor();
            if (item != null) {
                // JOptionPane.showMessageDialog(null, item.getNumeroDePersonal());

            } else {

            }

        }
    };

    private AlertManager alerts = new AlertManager();
    @FXML
    private TableColumn clm_checkbox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeTableEstudiantes();
        initializeTableTutores();
        this.initializeQuerys();
    }

    private void initializeTableEstudiantes() {

        clm_estudiantes.setCellValueFactory(new PropertyValueFactory<Estudiante, String>("nombreCompleto"));
        clm_checkbox.setCellValueFactory(new PropertyValueFactory<Estudiante, String>("CheckBoxEnSeleccion"));
        clm_currentTutor.setCellValueFactory(new PropertyValueFactory<Estudiante, String>("TutorName"));


    }

    private void initializeTableTutores() {

        clm_tutor.setCellValueFactory(new PropertyValueFactory<TutorAcademico, String>("nombreCompleto"));
        clm_estudiantesAcargo.setCellValueFactory(new PropertyValueFactory<TutorAcademico, String>("NumeroEstudiantes"));
        final ObservableList<TutorAcademico> problematicaReportes = tbl_tutores.getSelectionModel().getSelectedItems();
        problematicaReportes.addListener(selectedTutor);

    }

    private void loadDataTableEstudiantes(ArrayList<Estudiante> estudiantes) {

        if (!estudiantes.isEmpty()) {
            for (Estudiante estudiante : estudiantes) {
                estudiantesObservableList.add(estudiante);
            }
        } else {
            //alerts.showAlertNotProblematicasFound();

        }
        tblEstudiantes.setItems(estudiantesObservableList);

    }

    private void loadDataTableTutores(ArrayList<TutorAcademico> tutores) {

        if (!tutores.isEmpty()) {
            for (TutorAcademico tutor : tutores) {
                tutoresObservableList.add(tutor);
            }
        } else {
            // alerts.showAlertNotProblematicasFound();

        }
        tbl_tutores.setItems(tutoresObservableList);

    }

    private void initializeQuerys() {
        EstudianteDAO estudianteDAO = new EstudianteDAO();

        TutorAcademicoDAO tutorDAO = new TutorAcademicoDAO();

        ArrayList<Estudiante> estudiantes = new ArrayList<Estudiante>();
        ArrayList<TutorAcademico> tutores = new ArrayList<TutorAcademico>();

        try {
            estudiantes = estudianteDAO.getEstudiantesByPrograma("14203");
            tutores = tutorDAO.getAllTutores();
            if (!estudiantes.isEmpty() || !tutores.isEmpty()) {
                loadDataTableEstudiantes(estudiantes);
                loadDataTableTutores(tutores);
            }else{
             alerts.showAlertNotRegisterFound();
            }
        } catch (SQLException ex) {

            alerts.showAlertErrorConexionDB();

        }

    }

    @FXML
    private void updateAsignacion(ActionEvent event) {
        EstudianteDAO estudiantedao = new EstudianteDAO();
        ObservableList<Estudiante> estudiantesSelected = getEstudiantesSelected();
        TutorAcademico tutor = getTablaTutor();

        if (!estudiantesSelected.isEmpty() && tutor != null) {
            for (Estudiante estudiante : estudiantesSelected) {
                String matricula = estudiante.getMatricula();
                try {
                    estudiantedao.updateAsignacion(matricula, tutor.getNumeroDePersonal());

                } catch (SQLException ex) {
                    alerts.showAlertErrorConexionDB();
                }

            }
            updateTables();
            alerts.showAlertSuccesfulUpdate();
           
        } else {
            alerts.showAlertEmptySelectionTutor();
        }

    }

    private ObservableList<Estudiante> getEstudiantesSelected() {
        ObservableList<Estudiante> estudiantesSelected = FXCollections.observableArrayList();

        for (Estudiante estudiante : estudiantesObservableList) {
            if (estudiante.getCheckBoxEnSeleccion().isSelected()) {
                estudiantesSelected.add(estudiante);

            }

        }
        return estudiantesSelected;

    }

    public TutorAcademico getTablaTutor() {
        TutorAcademico tutorSeleccionado = null;
        if (tbl_tutores != null) {
            List<TutorAcademico> tabla = tbl_tutores.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                tutorSeleccionado = tabla.get(0);
            }
        }
        return tutorSeleccionado;
    }

    private void updateTables() {
        estudiantesObservableList.removeAll(estudiantesObservableList);
        tutoresObservableList.removeAll(tutoresObservableList);
        initializeQuerys();

    }

}
