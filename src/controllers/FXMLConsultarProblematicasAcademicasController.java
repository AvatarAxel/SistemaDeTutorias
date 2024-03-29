package controllers;

import BussinessLogic.PeriodoEscolarDAO;
import BussinessLogic.ProblematicaAcademicaDAO;
import Domain.PeriodoEscolar;
import Domain.ProblematicaAcademica;
import java.net.URL;
import java.sql.SQLException;

import java.util.ArrayList;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
//import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javax.swing.JOptionPane;
import util.AlertManager;
import singleton.User;
import util.WindowManager;

/**
 * FXML Controller class
 *
 * @author Valeria
 */
public class FXMLConsultarProblematicasAcademicasController implements Initializable {

    @FXML
    private TableView<ProblematicaAcademica> tblProblematicas;
    @FXML
    private TableColumn clm_IdProblematica;
    @FXML
    private TableColumn clm_Hd;
    @FXML
    private TableColumn clm_Experiencia;
    @FXML
    private TableColumn clm_Profesor;
    @FXML
    private TableColumn clm_numReportes;
    @FXML
    private TableColumn clm_fecha;
    @FXML
    private Button btn_detail;
    @FXML
    private TextField txt_Profesor;
    @FXML
    private Label lbl_idProblematica;
    @FXML
    private Label lbl_titulo;
    @FXML
    private Label lbl_profesor;
    @FXML
    private Label lbl_descrip;
    @FXML
    private Label lbl_fecha;
    @FXML
    private Label lbl_afectados;
    @FXML
    private Label lbl_ee;
    @FXML
    private Button btn_back;
    @FXML
    private Label lbl_idProblematicaData;
    @FXML
    private Label lbl_tituloData;
    @FXML
    private Label lbl_eeData;
    @FXML
    private Label lbl_descripData;
    @FXML
    private Label lbl_profesorData;
    @FXML
    private Label lbl_fechaData;
    @FXML
    private Label lbl_afectadosData;

    private AlertManager alerts = new AlertManager();
    private ObservableList<PeriodoEscolar> listPeriodosEscolares;
    ArrayList<ProblematicaAcademica> problematicas = new ArrayList<ProblematicaAcademica>();
    private ObservableList<ProblematicaAcademica> listProblematicas;

    ObservableList<ProblematicaAcademica> problematicaAcademicaObservableList = FXCollections.observableArrayList();
    ObservableList<ProblematicaAcademica> problematicaAcademicaPeriodoObservableList = FXCollections.observableArrayList();

    String clavePrograma = User.getCurrentUser().getRol().getProgramaEducativo().getClave();
    @FXML
    private Button btn_closeWinodw;
    @FXML
    private ComboBox<PeriodoEscolar> cmb_Periodo;
    @FXML
    private Label lbl_Periodo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.initializeTable();
        final ObservableList<ProblematicaAcademica> problematicaReportes = tblProblematicas.getSelectionModel().getSelectedItems();
        problematicaReportes.addListener(selectedProblematica);
        this.selectedItem();

    }

    @FXML
    private void openProblematicaSelected(ActionEvent event) {

        ProblematicaAcademica currentProblematica = this.getTablaProblematica();
        if (currentProblematica != null) {
            hideTable();
            showDetailProblematica();

        } else {

            JOptionPane.showMessageDialog(null, "Aún no ha seleccionado una Problemática");

        }

    }

    @FXML
    private void backTable(ActionEvent event) {
        hideDetailProblematica();
        showTable();
    }

    /* @FXML
    private void searchByFecha(ActionEvent event) {
        LocalDate keyword = txt_date.getValue();
        if (keyword.equals(null)) {
            tblProblematicas.setItems(problematicaAcademicaObservableList);
        } else {
            ObservableList<ProblematicaAcademica> filteredData = FXCollections.observableArrayList();
            FilteredList<ProblematicaAcademica> filteredItems = new FilteredList<>(problematicaAcademicaObservableList);

// bind predicate based on datepicker choices
            filteredItems.predicateProperty().bind(Bindings.createObjectBinding(() -> {
                LocalDate minDate = txt_date.getValue();
                LocalDate maxDate = txt_date.getValue();

                // get final values != null
                final LocalDate finalMin = minDate == null ? LocalDate.MIN : minDate;
                final LocalDate finalMax = maxDate == null ? LocalDate.MAX : maxDate;

                // values for openDate need to be in the interval [finalMin, finalMax]
                return ti -> !finalMin.isAfter(ti.getFechaFin()) && !finalMax.isBefore(ti.getFechaFin());
            },
                    txt_date.valueProperty(),
                    txt_date.valueProperty()));

            tblProblematicas.setItems(filteredItems);
        }
    }*/
    private final ListChangeListener<ProblematicaAcademica> selectedProblematica = new ListChangeListener<ProblematicaAcademica>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends ProblematicaAcademica> c) {
            ProblematicaAcademica item = new ProblematicaAcademica();
            item = getTablaProblematica();
            if (item != null) {

            } else {

            }

        }
    };

    private void initializeTable() {

        clm_Experiencia.setCellValueFactory(new PropertyValueFactory<ProblematicaAcademica, String>("ExperienciaEducativaName"));
        clm_Profesor.setCellValueFactory(new PropertyValueFactory<ProblematicaAcademica, String>("NombreCompletoProfesor"));
        clm_Hd.setCellValueFactory(new PropertyValueFactory<ProblematicaAcademica, String>("titulo"));
        clm_IdProblematica.setCellValueFactory(new PropertyValueFactory<ProblematicaAcademica, String>("idProblematica"));
        clm_numReportes.setCellValueFactory(new PropertyValueFactory<ProblematicaAcademica, String>("numeroDeEstudiantesAfectados"));
        clm_fecha.setCellValueFactory(new PropertyValueFactory<ProblematicaAcademica, Date>("FechaTutoria"));
        listPeriodosEscolares = FXCollections.observableArrayList();
        listProblematicas = FXCollections.observableArrayList();

        loadDataTable();

    }

    private void loadDataTable() {

        ProblematicaAcademicaDAO problematicasDAO = new ProblematicaAcademicaDAO();
        PeriodoEscolarDAO periodoEscolarDAO = new PeriodoEscolarDAO();

        try {
            // condicón si es tutor o no 
            // coordinador 2
            // tutor 3
             if(User.getCurrentUser().getRol().getIdRol()==2){ 
             
                         problematicas = problematicasDAO.getProblematicasByPrograma(clavePrograma);

             } else if  (User.getCurrentUser().getRol().getIdRol()==3){
                        problematicas = problematicasDAO.getProblematicasByProgramaByTutor(clavePrograma,User.getCurrentUser().getNumeroDePersonal());

            }
            ArrayList<PeriodoEscolar> loadedPeriodos = periodoEscolarDAO.getAllPeriodos();

            listPeriodosEscolares.addAll(loadedPeriodos);

            if (!problematicas.isEmpty()) {
                for (ProblematicaAcademica problematica : problematicas) {
                    problematicaAcademicaObservableList.add(problematica);

                }
                cmb_Periodo.setItems(listPeriodosEscolares);
            } else {
                alerts.showAlertNotProblematicasFound();

            }

        } catch (SQLException ex) {
            alerts.showAlertErrorConexionDB();
            ex.printStackTrace();
        }

        tblProblematicas.setItems(problematicaAcademicaObservableList);

    }

    public ProblematicaAcademica getTablaProblematica() {
        ProblematicaAcademica problematicaSeleccionada = null;
        if (tblProblematicas != null) {
            List<ProblematicaAcademica> tabla = tblProblematicas.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                problematicaSeleccionada = tabla.get(0);
            }
        }
        return problematicaSeleccionada;
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        WindowManager.NavigateToWindow(tblProblematicas.getScene().getWindow(), "/GUI/FXMLMainMenu.fxml", "Menú");

    }

    private void hideTable() {
        boolean result = false;
        tblProblematicas.setVisible(result);
        //   txt_Experiencia.setVisible(result);
        txt_Profesor.setVisible(result);
//        txt_date.setVisible(result);
        lbl_Periodo.setVisible(result);
        btn_detail.setVisible(result);
        cmb_Periodo.setVisible(result);

    }

    private void hideDetailProblematica() {
        boolean result = false;
        btn_back.setVisible(result);
        lbl_idProblematica.setVisible(result);
        lbl_idProblematicaData.setVisible(result);
        lbl_titulo.setVisible(result);
        lbl_tituloData.setVisible(result);
        lbl_profesor.setVisible(result);
        lbl_profesorData.setVisible(result);
        lbl_descrip.setVisible(result);
        lbl_descripData.setVisible(result);
        lbl_fecha.setVisible(result);
        lbl_fechaData.setVisible(result);
        lbl_afectados.setVisible(result);
        lbl_afectadosData.setVisible(result);
        lbl_ee.setVisible(result);
        lbl_eeData.setVisible(result);
        tblProblematicas.getSelectionModel().clearSelection();

    }

    private void showDetailProblematica() {
        boolean result = true;
        btn_back.setVisible(result);
        lbl_idProblematica.setVisible(result);
        lbl_idProblematicaData.setVisible(result);
        lbl_titulo.setVisible(result);
        lbl_tituloData.setVisible(result);
        lbl_profesor.setVisible(result);
        lbl_profesorData.setVisible(result);
        lbl_descrip.setVisible(result);
        lbl_descripData.setVisible(result);
        lbl_fecha.setVisible(result);
        lbl_fechaData.setVisible(result);
        lbl_afectados.setVisible(result);
        lbl_afectadosData.setVisible(result);
        lbl_ee.setVisible(result);
        lbl_eeData.setVisible(result);
        showProblematica();

    }

    private void showTable() {
        boolean result = true;
        tblProblematicas.setVisible(result);
        txt_Profesor.setVisible(result);
        lbl_Periodo.setVisible(result);
        cmb_Periodo.setVisible(result);
        btn_detail.setVisible(result);

    }

    private void showProblematica() {
        ProblematicaAcademica currentProblematica = new ProblematicaAcademica();
        currentProblematica = this.getTablaProblematica();
        String idProblematica = String.valueOf(currentProblematica.getIdProblematica());
        String afectados = String.valueOf(currentProblematica.getNumeroDeEstudiantesAfectados());
        String titulo = currentProblematica.getTitulo();
        String experiencia = currentProblematica.getExperienciaEducativa().getNombre();
        String profesor = currentProblematica.getNombreCompletoProfesor();
        String descripcion = currentProblematica.getDescripcion();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaTutoria = currentProblematica.getFechaTutoria();

        lbl_idProblematicaData.setText(idProblematica);
        lbl_afectadosData.setText(afectados);
        lbl_tituloData.setText(titulo);
        lbl_eeData.setText(experiencia);
        lbl_profesorData.setText(profesor);
        lbl_descripData.setText(descripcion);
        lbl_fechaData.setText(fechaTutoria);

    }

    @FXML
    private void searchProblematicas(KeyEvent event) {
        filterTableProblematica();
        txt_Profesor.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 30) {
                return change;
            } else {
                return null;
            }
        }));
        if (txt_Profesor.getText().length() > 30) {
            txt_Profesor.setText("");
        }

    }

    private void filterTableProblematica() {
        FilteredList<ProblematicaAcademica> filteredProblematica = new FilteredList<>(problematicaAcademicaObservableList, b -> true);
        txt_Profesor.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredProblematica.setPredicate(problematica -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String inputText = newValue.toLowerCase();
                if (problematica.getTitulo().toLowerCase().contains(inputText)) {
                    return true;
                } else if (problematica.getExperienciaEducativaName().toLowerCase().contains(inputText)) {
                    return true;

                } else {
                    return false;
                }
            });
        });
        SortedList<ProblematicaAcademica> sortedListProblematica = new SortedList<>(filteredProblematica);
        sortedListProblematica.comparatorProperty().bind(tblProblematicas.comparatorProperty());
        tblProblematicas.setItems(sortedListProblematica);
    }

    private void selectedItem() {
        cmb_Periodo.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends PeriodoEscolar> observable, PeriodoEscolar oldValue, PeriodoEscolar newValue) -> {
            if (oldValue != null) {
            }
            if (newValue != null) {
                Label noticeLabel = new Label("No hay datos");
                tblProblematicas.setPlaceholder(noticeLabel);
                listProblematicas.clear();
                for (ProblematicaAcademica problema : problematicaAcademicaObservableList) {
                    if (problema.getPeriodoEscolar().getIdPeriodoEscolar() == newValue.getIdPeriodoEscolar()) {
                        listProblematicas.add(problema);
                    }
                }
                tblProblematicas.setItems(listProblematicas);
            }

        });
    }

}
