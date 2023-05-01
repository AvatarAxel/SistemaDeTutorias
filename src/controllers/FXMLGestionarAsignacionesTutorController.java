package controllers;

import BussinessLogic.EstudianteDAO;
import BussinessLogic.ProfesorDAO;
import BussinessLogic.TutorAcademicoDAO;
import Domain.Estudiante;
import Domain.TutorAcademico;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
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
    ArrayList<Estudiante> estudiantes = new ArrayList<Estudiante>();
    ArrayList<TutorAcademico> tutores = new ArrayList<TutorAcademico>();
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
    @FXML
    private Button closeWindow;
    @FXML
    private CheckBox cmb_WithTutor;
    @FXML
    private CheckBox cmb_WithoutTutor;

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

    private void loadDataTableEstudiantes() {

        if (!estudiantes.isEmpty()) {
            for (Estudiante estudiante : estudiantes) {
                estudiantesObservableList.add(estudiante);
            }
        } else {
            //alerts.showAlertNotProblematicasFound();

        }
        tblEstudiantes.setItems(estudiantesObservableList);

    }

    private void loadDataTableTutores() {

        if (!tutores.isEmpty()) {
            for (TutorAcademico tutor : tutores) {
                tutoresObservableList.add(tutor);
            }
        } else {
            // alerts.showAlertNotProblematicasFound();

        }
        tbl_tutores.setItems(tutoresObservableList);

    }

    private ObservableList<Estudiante> searchEstudiantesWithTutores() {
        ObservableList<Estudiante> estudiantesWithTutor = FXCollections.observableArrayList();
        if (!estudiantes.isEmpty()) {
            for (Estudiante estudiante : estudiantes) {
                if (estudiante.getTutorName() != null) {
                    estudiantesWithTutor.add(estudiante);
                }
            }
        }
        tblEstudiantes.setItems(estudiantesWithTutor);

        return estudiantesWithTutor;
    }

    private ObservableList<Estudiante> searchEstudiantesWithoutTutores() {
        ObservableList<Estudiante> estudiantesWithoutTutor = FXCollections.observableArrayList();
        if (!estudiantes.isEmpty()) {
            for (Estudiante estudiante : estudiantes) {
                if (estudiante.getTutorName() == null) {
                    estudiantesWithoutTutor.add(estudiante);
                }
            }
        }
        tblEstudiantes.setItems(estudiantesWithoutTutor);

        return estudiantesWithoutTutor;
    }

    private void initializeQuerys() {
        EstudianteDAO estudianteDAO = new EstudianteDAO();

        TutorAcademicoDAO tutorDAO = new TutorAcademicoDAO();

        try {
            estudiantes = estudianteDAO.getEstudiantesByPrograma("14203");
            tutores = tutorDAO.getAllTutores();
            if (!estudiantes.isEmpty() || !tutores.isEmpty()) {
                loadDataTableEstudiantes();
                loadDataTableTutores();
            } else {
                alerts.showAlertNotRegisterFound();

            }
        } catch (SQLException ex) {

            alerts.showAlertErrorConexionDB();
            ex.printStackTrace();

        }

    }

    private void updateQuerys() {
        EstudianteDAO estudianteDAO = new EstudianteDAO();

        TutorAcademicoDAO tutorDAO = new TutorAcademicoDAO();
        try {
            estudiantes = estudianteDAO.getEstudiantesByPrograma("14203");
            tutores = tutorDAO.getAllTutores();
            if (cmb_WithTutor.isSelected()) {
                this.searchEstudiantesWithTutores();
            } else if (cmb_WithoutTutor.isSelected()) {
                this.searchEstudiantesWithoutTutores();

            } else {
                this.loadDataTableEstudiantes();
            }
            this.loadDataTableTutores();

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
            final Node source = (Node) event.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
            stageAlert.getIcons().add(new Image("images/icon.png"));

            alert.setTitle("Confirmación");
            alert.setHeaderText(null);

            alert.setContentText("¿Esta seguro de asignar?");
            Optional<ButtonType> action = alert.showAndWait();

            if (action.get() == ButtonType.OK) {
                for (Estudiante estudiante : estudiantesSelected) {
                    String matricula = estudiante.getMatricula();
                    if (tutor.getNumeroEstudiantes() >= 30) {
                        alerts.showAlertManyTutorados();
                    }
                    try {

                        estudiantedao.updateAsignacion(matricula, tutor.getNumeroDePersonal());

                    } catch (SQLException ex) {
                        alerts.showAlertErrorConexionDB();
                    }

                }
                updateTables();
                alerts.showAlertSuccesfulUpdate();
            } else if (action.get() == ButtonType.CANCEL) {
                alert.close();
            }

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
        updateQuerys();

    }

    private void filterTableTutor() {
        FilteredList<TutorAcademico> filteredTutor = new FilteredList<>(tutoresObservableList, b -> true);
        txt_tutor.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredTutor.setPredicate(tutor -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String inputText = newValue.toLowerCase();
                if (tutor.getNombre().toLowerCase().indexOf(inputText) != -1) {
                    return true;
                } else if (tutor.getApellidoPaterno().toLowerCase().indexOf(inputText) != -1) {
                    return true;

                } else if (tutor.getApellidoMaterno().toLowerCase().indexOf(inputText) != -1) {
                    return true;

                } else {
                    return false;
                }
            });
        });
        SortedList<TutorAcademico> sortedListTutor = new SortedList<>(filteredTutor);
        sortedListTutor.comparatorProperty().bind(tbl_tutores.comparatorProperty());
        tbl_tutores.setItems(sortedListTutor);
    }

    private void filterTableEstudiantes() {
        FilteredList<Estudiante> filteredEstudiante = new FilteredList<>(estudiantesObservableList, b -> true);
        txt_estudiante.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredEstudiante.setPredicate(estudiante -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String inputText = newValue.toLowerCase();
                if (estudiante.getNombre().toLowerCase().indexOf(inputText) != -1) {
                    return true;
                } else if (estudiante.getApellidoPaterno().toLowerCase().indexOf(inputText) != -1) {
                    return true;

                } else if (estudiante.getApellidoMaterno().toLowerCase().indexOf(inputText) != -1) {
                    return true;

                } else {
                    return false;
                }
            });
        });
        SortedList<Estudiante> sortedListEstudianter = new SortedList<>(filteredEstudiante);
        sortedListEstudianter.comparatorProperty().bind(tblEstudiantes.comparatorProperty());
        tblEstudiantes.setItems(sortedListEstudianter);
    }

    @FXML
    private void validateLengthNombreTutor(KeyEvent event) {
        filterTableTutor();
        txt_tutor.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 30) {
                return change;
            } else {
                return null;
            }
        }));
        if (txt_tutor.getText().length() > 30) {
            txt_tutor.setText("");
        }

    }

    @FXML
    private void validateLengthNombresEstudiantes(KeyEvent event) {
        filterTableEstudiantes();
        txt_estudiante.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 30) {
                return change;
            } else {
                return null;
            }
        }));
        if (txt_estudiante.getText().length() > 30) {
            txt_estudiante.setText("");
        }
    }

    @FXML
    private void filterTableWitthTutor(ActionEvent event) {
        searchEstudiantesWithTutores();


        /*  cmb_WithTutor.selectedProperty().addListener(
        (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
         if (new_val){
          cmb_WithoutTutor.setDisable(true);

         }
      }); */
        if (cmb_WithTutor.isSelected()) {
            cmb_WithoutTutor.setDisable(true);

        } else {
            this.loadDataTableEstudiantes();
            cmb_WithoutTutor.setDisable(false);

        }

    }

    @FXML
    private void filterTableWithoutTutor(ActionEvent event) {
        searchEstudiantesWithoutTutores();
        if (cmb_WithoutTutor.isSelected()) {
            cmb_WithTutor.setDisable(true);

        } else {
            this.loadDataTableEstudiantes();
            cmb_WithTutor.setDisable(false);

        }
    }

}
