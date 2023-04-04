package controllers;

import BussinessLogic.ExperienciaEducativaDAO;
import BussinessLogic.ProfesorDAO;
import Domain.ExperienciaEducativa;
import Domain.ProblematicaAcademica;
import Domain.SolucionAProblematica;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

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
    private Button btn_ModifyE;

    @FXML
    private ComboBox<String> cmb_EE;
    @FXML
    private TextArea txt_descrip;

    SpinnerValueFactory<Integer> valueFactory;
    String experienciaEducativaSeleccionada = "";
    ExperienciaEducativa profesorSeleccionado;
    private ObservableList<ProblematicaAcademica> problematicas;
    ArrayList<ExperienciaEducativa> nombresProfesoresNrc = new ArrayList<ExperienciaEducativa>();
    ArrayList<String> nombreExperiencias = new ArrayList<String>();
    int idReporteTutoria = 0;

    /**
     * Initializes the controller class.
     */
    private final ListChangeListener<ProblematicaAcademica> selectedProblematica = new ListChangeListener<ProblematicaAcademica>() {
        @Override
        public void onChanged(ListChangeListener.Change<? extends ProblematicaAcademica> c) {
            //mostrarDatosProblematica();
            
            ProblematicaAcademica item = new ProblematicaAcademica();
            item = getTablaProblematica();
            if (item!=null){
            disableFields();
            btn_Update.setVisible(false);
            btn_add.setVisible(false);
            showProblematica(item);
            }

        }
    };

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.initializeTable();
        final ObservableList<ProblematicaAcademica> problematicaReportes = tblProblematicas.getSelectionModel().getSelectedItems();
        problematicaReportes.addListener(selectedProblematica);
        initializeQuerys();
        disableFields();
        btn_add.setVisible(false);
        btn_Delete.setVisible(false);
        btn_Update.setVisible(false);

    }

    private void loadCmbExperiencias() {

        ObservableList<String> experienciaEducativaObservableList = FXCollections.observableArrayList();

        for (String experienciaEducativa : nombreExperiencias) {
            experienciaEducativaObservableList.add(experienciaEducativa);
        }

        cmb_EE.setItems(experienciaEducativaObservableList);
        cmb_EE.valueProperty().addListener((ov, valorAntiguo, valorNuevo) -> {
            experienciaEducativaSeleccionada = (String) valorNuevo;
            if (experienciaEducativaSeleccionada!=null){
            ArrayList<ExperienciaEducativa> filterList;
            filterList = (ArrayList<ExperienciaEducativa>) nombresProfesoresNrc.stream().filter(a -> experienciaEducativaSeleccionada.equals(a.getNombre())).collect(Collectors.toList());
           // System.out.println(filterList);
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

    private void cancelChanges(ActionEvent event) {
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void addProblematica(ActionEvent event) {
        
        
        int validateData=validateData();
        if (validateData>0){
            
            ProblematicaAcademica problematica = convertDataToProblematica();
            tblProblematicas.getItems().add(problematica);
            clean();
        }
       

    }

    @FXML
    private void updateProblematica(ActionEvent event) {
    }

    @FXML
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

    @FXML
    private void deleteEnviromentOn(ActionEvent event) {
    }

    @FXML
    private void cleanFields(ActionEvent event) {

        enableFields();
        clean();
        loadCmbExperiencias();

    }

    private void clean(){
    
        tblProblematicas.getSelectionModel().clearSelection();
        btn_add.setVisible(true);
        btn_Delete.setVisible(false);
        btn_Update.setVisible(false);
        valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20);
        valueFactory.setValue(0);
        spn_numReportados.setValueFactory(valueFactory);
        cmb_Profesor.getSelectionModel().clearSelection();
        cmb_EE.getSelectionModel().clearSelection();
       
        txt_Title.clear();
        txt_descrip.clear();
        spn_numReportados.getEditor().clear();
 
    }
    private int validateData() {
        int validate = 0;
        String experiencia = "";
        ExperienciaEducativa profesor = new ExperienciaEducativa();
        int reportados = 0;
        String titulo = "";
        String descripcion = "";
        

        experiencia = experienciaEducativaSeleccionada;
        profesor = profesorSeleccionado;
        reportados = spn_numReportados.getValue();
        titulo = txt_Title.getText();
        descripcion = txt_descrip.getText();

        if ( (profesor != null && experiencia != null) && (titulo != null && descripcion != null && reportados > 0 )) {
            if (!titulo.chars().allMatch(Character::isWhitespace) && !descripcion.chars().allMatch(Character::isWhitespace) && !titulo.matches("[0-9]+") && !descripcion.matches("[0-9]+")){
            validate = 1;
            
            }else{
             JOptionPane.showMessageDialog(null, "Datos incompletos o inválidos. Revise nuevamente, por favor.");
            
            }
        } else {
            JOptionPane.showMessageDialog(null, "Datos incompletos o inválidos. Revise nuevamente, por favor.");
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
        problematica.setIdProblematica(1);
        problematica.setExperienciaE(experiencia);
        problematica.setProfesor(profesor);
        problematica.setNumeroDeEstudiantesAfectados(reportados);
        problematica.setTitulo(titulo);
        problematica.setDescripcion(descripcion);
        problematica.setNrc(nrc);
        problematica.setIdReporteTutoria(idReporteTutoria);
        problematica.setSolucion(new SolucionAProblematica(0, ""));

        return problematica;
    }

    private void showProblematica(ProblematicaAcademica problematicaItem) {

        btn_Delete.setVisible(true);
        btn_add.setVisible(false);
        btn_Update.setVisible(false);

        String experiencia = problematicaItem.getExperienciaE();
        autoSelectExperiencia(experiencia, problematicaItem.getNrc());

        int reportados = problematicaItem.getNumeroDeEstudiantesAfectados();
        String titulo = problematicaItem.getTitulo();
        String descripcion = problematicaItem.getDescripcion();
        valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20);
        valueFactory.setValue(reportados);
        spn_numReportados.setValueFactory(valueFactory);
        txt_Title.setText(titulo);
        txt_descrip.setText(descripcion);

        // JOptionPane.showMessageDialog(null,problematicaItem.getTitulo());
    }

    private void autoSelectExperiencia(String experienciaName, String nrc) {
        ObservableList<String> experieciasObservableList = FXCollections.observableArrayList();
        this.loadCmbExperiencias();
        experieciasObservableList = cmb_EE.getItems();

        for (String experiencia : experieciasObservableList) {
            if (experiencia.equals(experienciaName)) {
                cmb_EE.setValue(experiencia);
                autoSelectProfesores(nrc);
            }
        }

    }

    private void autoSelectProfesores(String nrc) {
        ObservableList<ExperienciaEducativa> experieciasObservableList = FXCollections.observableArrayList();

        experieciasObservableList = cmb_Profesor.getItems();

        for (ExperienciaEducativa experiencia : experieciasObservableList) {
            if (experiencia.getNrc().equals(nrc)) {
                cmb_Profesor.setValue(experiencia);
            }
        }
    }

    private void initializeTable() {

        clm_Experiencia.setCellValueFactory(new PropertyValueFactory<ProblematicaAcademica, String>("ExperienciaE"));
        clm_Profesor.setCellValueFactory(new PropertyValueFactory<ProblematicaAcademica, String>("Profesor"));
        clm_Hd.setCellValueFactory(new PropertyValueFactory<ProblematicaAcademica, String>("titulo"));
        clm_IdProblematica.setCellValueFactory(new PropertyValueFactory<ProblematicaAcademica, String>("idProblematica"));
        clm_numReportes.setCellValueFactory(new PropertyValueFactory<ProblematicaAcademica, String>("numeroDeEstudiantesAfectados"));

        problematicas = FXCollections.observableArrayList();
        tblProblematicas.setItems(problematicas);

    }

    private void initializeQuerys() {
        ExperienciaEducativaDAO experieniciasDAO = new ExperienciaEducativaDAO();

        ProfesorDAO profesorDAO = new ProfesorDAO();

        try {
            nombreExperiencias = experieniciasDAO.consultExperienciasName();
            nombresProfesoresNrc = profesorDAO.consultProfesoresNames();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "SQL ERROR");
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

}
