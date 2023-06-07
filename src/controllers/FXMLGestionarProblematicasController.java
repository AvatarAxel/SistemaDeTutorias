package controllers;

import BussinessLogic.ExperienciaEducativaDAO;
import util.AlertManager;
import BussinessLogic.ProblematicaAcademicaDAO;
import BussinessLogic.ProfesorDAO;
import Domain.ExperienciaEducativa;
import Domain.ProblematicaAcademica;
import Domain.Profesor;
import Domain.SolucionAProblematica;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import singleton.User;


/**
 * FXML Controller class
 *
 * @author Valeria
 */
public class FXMLGestionarProblematicasController implements Initializable {

    @FXML
    private TableView<ProblematicaAcademica> tblProblematicas;
    @FXML
    private TableColumn clm_IdProblematica;
    @FXML
    private TableColumn clm_Experiencia;
    @FXML
    private TableColumn clm_Profesor;
    @FXML
    private Button btn_Delete;
    @FXML
    private Label lbl_Profesor;
    @FXML
    private Label lbl_EE;
    @FXML
    private ComboBox<ExperienciaEducativa> cmb_Profesor;
    @FXML
    private Label lbl_Header;
    @FXML
    private Label lbl_description;
    private TextArea txt_;
    @FXML
    private TextField txt_Title;
    @FXML
    private Label lbl_NumReportados;
    @FXML
    private TableColumn clm_Hd;
    @FXML
    private Spinner<Integer> spn_numReportados;
    @FXML
    private Button btn_Update;
    @FXML
    private Button btn_more;
    @FXML
    private Button btn_add;
    @FXML
    private TableColumn clm_numReportes;

    @FXML
    private ComboBox<String> cmb_EE;
    @FXML
    private TextArea txt_descrip;

    SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30);
    ;
    
     
    private AlertManager alerts = new AlertManager();
    String experienciaEducativaSeleccionada = "";
    ExperienciaEducativa profesorSeleccionado;
    ArrayList<ExperienciaEducativa> nombresProfesoresNrc = new ArrayList<ExperienciaEducativa>();
    ArrayList<String> nombreExperiencias = new ArrayList<String>();
    ObservableList<ProblematicaAcademica> problematicaAcademicaObservableList = FXCollections.observableArrayList();
    int idReporteTutoria = 0;

    private final ListChangeListener<ProblematicaAcademica> selectedProblematica = new ListChangeListener<ProblematicaAcademica>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends ProblematicaAcademica> c) {
            ProblematicaAcademica item = new ProblematicaAcademica();
            item = getTablaProblematica();
            if (item != null) {
                enableFields();
                btn_add.setVisible(false);
                showProblematica();
            } else {

                disableFields();

                btn_Update.setVisible(true);
                btn_add.setVisible(false);
                btn_Delete.setVisible(false);

            }

        }
    };
    @FXML
    private Button btn_Close;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

     /*   this.initializeTable();
        final ObservableList<ProblematicaAcademica> problematicaReportes = tblProblematicas.getSelectionModel().getSelectedItems();
        problematicaReportes.addListener(selectedProblematica);
        initializeQuerys();
        disableFields();
        btn_add.setVisible(false);
        btn_Delete.setVisible(false);
        btn_Update.setVisible(false); */

    }

    private void loadCmbExperiencias() {

        ObservableList<String> experienciaEducativaObservableList = FXCollections.observableArrayList();

        for (String experienciaEducativa : nombreExperiencias) {
            experienciaEducativaObservableList.add(experienciaEducativa);
        }

        cmb_EE.setItems(experienciaEducativaObservableList);
        cmb_EE.valueProperty().addListener((ov, valorAntiguo, valorNuevo) -> {
            experienciaEducativaSeleccionada = (String) valorNuevo;
            if (experienciaEducativaSeleccionada != null) {
                ArrayList<ExperienciaEducativa> filterList;
                filterList = (ArrayList<ExperienciaEducativa>) nombresProfesoresNrc.stream().filter(a -> experienciaEducativaSeleccionada.equals(a.getNombre())).collect(Collectors.toList());
                loadCmbProfesores(filterList);
            }

        });
    }

    private void loadCmbProfesores(ArrayList<ExperienciaEducativa> nombresProfesoresNrc) {

        ObservableList<ExperienciaEducativa> profesoresObservableList = FXCollections.observableArrayList();

        for (ExperienciaEducativa profesor : nombresProfesoresNrc) {
            profesoresObservableList.add(new ExperienciaEducativa(profesor.getNrc(), profesor.getProfesorNombre(), profesor.getNombre()));
        }

        cmb_Profesor.setItems(profesoresObservableList);
        cmb_Profesor.valueProperty().addListener((ov, valorAntiguo, valorNuevo) -> {
            profesorSeleccionado = (ExperienciaEducativa) valorNuevo;

        });
    }

    @FXML
    private void addProblematica(ActionEvent event) {

        int validateData = validateData();
        if (validateData > 0) {
            ProblematicaAcademicaDAO problematicaDAO = new ProblematicaAcademicaDAO();
            ProblematicaAcademica problematica = new ProblematicaAcademica();
            problematica = convertDataToProblematica();
            int result = 0;
            try {
                result = problematicaDAO.insertProblematica(problematica);
                updateDataTable();
                clear();
                btn_add.setVisible(true);
                valueFactory.setValue(0);
                spn_numReportados.setValueFactory(valueFactory);

            } catch (SQLException ex) {
                alerts.showAlertErrorConexionDB();
            }
            if (result > 0) {
                alerts.showAlertSuccesfulRegister();
            }

        }

    }

    @FXML
    private void updateProblematica(ActionEvent event) {

        int validateData = validateData();
        if (validateData > 0) {
            ProblematicaAcademicaDAO problematicaDAO = new ProblematicaAcademicaDAO();
            ProblematicaAcademica problematica = new ProblematicaAcademica();
            problematica = convertDataToProblematica();
            problematica.setIdProblematica(this.getTablaProblematica().getIdProblematica());
            int result = 0;
            try {
                result = problematicaDAO.updatetProblematica(problematica);
                updateDataTable();
                clear();

            } catch (SQLException ex) {
                alerts.showAlertErrorConexionDB();
            }
            if (result > 0) {
                alerts.showAlertSuccesfulUpdate();
            }

        }

    }

    @FXML
    private void deleteProblematica(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Confirmación");
        alert.setHeaderText(null);

        alert.setContentText("¿Esta seguro de eliminar esta Problemática?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            int idProblematica = this.getTablaProblematica().getIdProblematica();
            int result = 0;
            ProblematicaAcademicaDAO problematicaDAO = new ProblematicaAcademicaDAO();

            try {
                result = problematicaDAO.deleteProblematica(idProblematica);
                clear();
                updateDataTable();

            } catch (SQLException ex) {
                alerts.showAlertErrorConexionDB();
            }
            if (result > 0) {
                alerts.showAlertSuccesfulDelete();
            }
        } else if (action.get() == ButtonType.CANCEL) {
            alert.close();
        }

    }

    private void modifyEnviromentOn(ActionEvent event) {
        if (!tblProblematicas.getSelectionModel().isEmpty()) {
            btn_Update.setVisible(true);
            btn_Delete.setVisible(true);
            btn_add.setVisible(false);
            enableFields();
        } else {

            JOptionPane.showMessageDialog(null, "Aún no ha seleccionado una Problemática");
        }

    }

    private void clear() {

        tblProblematicas.getSelectionModel().clearSelection();
        btn_add.setVisible(false);
        btn_Delete.setVisible(false);
        btn_Update.setVisible(false);

        spn_numReportados.getEditor().clear();

        cmb_Profesor.getSelectionModel().clearSelection();
        cmb_EE.getSelectionModel().clearSelection();

        txt_Title.clear();
        txt_descrip.clear();

    }

    private int validateData() {
        int validate = 0;
        String experiencia = "";
        ExperienciaEducativa profesor = new ExperienciaEducativa();
        int reportados = 0;
        String titulo = "";
        String descripcion = "";
        String acceptable = "^[A-Za-z0-9 _]*[A-Za-z0-9][A-Za-z0-9 _]*$";
        experiencia = experienciaEducativaSeleccionada;
        profesor = profesorSeleccionado;
        reportados = spn_numReportados.getValue();
        titulo = txt_Title.getText();
        descripcion = txt_descrip.getText();

        if ((profesor != null && experiencia != null) && ((titulo != null && descripcion != null) && (reportados > 0))) {
            if (!titulo.chars().allMatch(Character::isWhitespace) && !descripcion.chars().allMatch(Character::isWhitespace) && titulo.matches(acceptable) && !descripcion.matches("[0-9]+")) {
                validate = 1;

            } else {
                alerts.showAlertInvalidInputs();

            }
        } else {
            alerts.showAlertEmptyFields();
        }

        return validate;

    }

    private void disableFields() {

        cmb_EE.setDisable(true);
        cmb_Profesor.setDisable(true);
        spn_numReportados.setDisable(true);
        txt_Title.setDisable(true);
        txt_descrip.setDisable(true);

    }

    private void enableFields() {

        cmb_EE.setDisable(false);
        cmb_Profesor.setDisable(false);
        spn_numReportados.setDisable(false);
        txt_Title.setDisable(false);
        txt_descrip.setDisable(false);

    }

    private ProblematicaAcademica convertDataToProblematica() {
        int validate = 0;
        String experiencia = "";
        String profesor = "";
        int reportados = 0;
        String titulo = "";
        String descripcion = "";
        String nrc;

        experiencia = experienciaEducativaSeleccionada;
        profesor = profesorSeleccionado.getProfesorNombre();
        nrc = profesorSeleccionado.getNrc();
        reportados = spn_numReportados.getValue();
        titulo = txt_Title.getText();
        descripcion = txt_descrip.getText();

        ProblematicaAcademica problematica = new ProblematicaAcademica();
        problematica.setExperienciaEducativa(new ExperienciaEducativa());
        problematica.setProfesor(new Profesor());
        problematica.setExperienciaName(experiencia);
        problematica.setProfesorName(profesor);
        problematica.setNumeroDeEstudiantesAfectados(reportados);
        problematica.setTitulo(titulo);
        problematica.setDescripcion(descripcion);
        problematica.setNrc(nrc);
        problematica.setIdReporteTutoria(idReporteTutoria);
        problematica.setSolucion(new SolucionAProblematica(0, ""));

        return problematica;
    }

    private void showProblematica() {
        ProblematicaAcademica currentProblematica = new ProblematicaAcademica();
        currentProblematica = this.getTablaProblematica();
        btn_Delete.setVisible(true);
        btn_Update.setVisible(true);
        btn_add.setVisible(false);

        String experiencia = currentProblematica.getExperienciaEducativaName();
        autoSelectExperiencia(experiencia, currentProblematica.getNrc());

        int reportados = currentProblematica.getNumeroDeEstudiantesAfectados();
        String titulo = currentProblematica.getTitulo();
        String descripcion = currentProblematica.getDescripcion();
        valueFactory.setValue(reportados);
        spn_numReportados.setValueFactory(valueFactory);
        txt_Title.setText(titulo);
        txt_descrip.setText(descripcion);

    }

    private void autoSelectExperiencia(String experienciaName, String nrc) {
        ObservableList<String> experieciasObservableList = FXCollections.observableArrayList();
        this.loadCmbExperiencias();
        experieciasObservableList = cmb_EE.getItems();

        for (String experiencia : experieciasObservableList) {
            if (experiencia.equals(experienciaName)) {
                cmb_EE.setValue(experiencia);
                autoSelectProfesores(nrc);
                break;
            }
        }

    }

    private void autoSelectProfesores(String nrc) {
        ObservableList<ExperienciaEducativa> experieciasObservableList = FXCollections.observableArrayList();

        experieciasObservableList = cmb_Profesor.getItems();

        for (ExperienciaEducativa experiencia : experieciasObservableList) {
            if (experiencia.getNrc().equals(nrc)) {
                cmb_Profesor.setValue(experiencia);
                break;
            }
        }
    }

    private void initializeTable() {

        clm_Experiencia.setCellValueFactory(new PropertyValueFactory<ProblematicaAcademica, String>("experienciaEducativaName"));
        clm_Profesor.setCellValueFactory(new PropertyValueFactory<ProblematicaAcademica, String>("profesorName"));
        clm_Hd.setCellValueFactory(new PropertyValueFactory<ProblematicaAcademica, String>("titulo"));
        clm_IdProblematica.setCellValueFactory(new PropertyValueFactory<ProblematicaAcademica, String>("idProblematica"));
        clm_numReportes.setCellValueFactory(new PropertyValueFactory<ProblematicaAcademica, String>("numeroDeEstudiantesAfectados"));

        loadDataTable();

    }

    private void loadDataTable() {

        ProblematicaAcademicaDAO problematicasDAO = new ProblematicaAcademicaDAO();
        ArrayList<ProblematicaAcademica> problematicas = new ArrayList<ProblematicaAcademica>();
        try {
            problematicas = problematicasDAO.getProblematicasByReporte(idReporteTutoria);
            if (!problematicas.isEmpty()) {
                for (ProblematicaAcademica problematica : problematicas) {
                    problematicaAcademicaObservableList.add(problematica);
                }
            } else {
                alerts.showAlertNotProblematicasFound();

            }

        } catch (SQLException ex) {
            alerts.showAlertErrorConexionDB();
        }

        tblProblematicas.setItems(problematicaAcademicaObservableList);

    }

    private void updateDataTable() {

        problematicaAcademicaObservableList.removeAll(problematicaAcademicaObservableList);
        loadDataTable();

    }

    private void initializeQuerys() {
        ExperienciaEducativaDAO experieniciasDAO = new ExperienciaEducativaDAO();

        ProfesorDAO profesorDAO = new ProfesorDAO();

        try {
            nombreExperiencias = experieniciasDAO.consultExperienciasName(User.getCurrentUser().getRol().getProgramaEducativo().getClave());
            nombresProfesoresNrc = profesorDAO.consultProfesoresNames();
        } catch (SQLException ex) {
            alerts.showAlertErrorConexionDB();
 
        }

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
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
        stageAlert.getIcons().add(new Image("images/icon.png"));

        alert.setTitle("Confirmación");
        alert.setHeaderText(null);

        alert.setContentText("¿Esta seguro de cerrar esta ventana?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            stage.close();
        } else if (action.get() == ButtonType.CANCEL) {
            alert.close();
        }
    }

    @FXML
    private void clearFields(ActionEvent event) {
        clear();
        enableFields();
        loadCmbExperiencias();
        btn_add.setVisible(true);
        valueFactory.setValue(0);
        spn_numReportados.setValueFactory(valueFactory);
    }

    void receiveParameters(int idReporteTutoria) {
        this.idReporteTutoria=idReporteTutoria;
        this.initializeTable();
        final ObservableList<ProblematicaAcademica> problematicaReportes = tblProblematicas.getSelectionModel().getSelectedItems();
        problematicaReportes.addListener(selectedProblematica);
        initializeQuerys();
        disableFields();
        btn_add.setVisible(false);
        btn_Delete.setVisible(false);
        btn_Update.setVisible(false);

        
    }

}
