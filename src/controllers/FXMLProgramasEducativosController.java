/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import BussinessLogic.ProgramaEducativoDAO;
import Domain.ProgramaEducativo;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import util.AlertManager;
import util.WindowManager;

/**
 * FXML Controller class
 *
 * @author Propietario
 */
public class FXMLProgramasEducativosController implements Initializable {

    @FXML
    private TableView<ProgramaEducativo> tbProgramasEducativos;
    @FXML
    private TableColumn ColumnClave;
    @FXML
    private TableColumn ColumnNombre;
    @FXML
    private Label lbInstruccion;
    @FXML
    private TextField tfClave;
    @FXML
    private TextField tfNombre;
    @FXML
    private Button btCancelar;
    @FXML
    private Button btRegistrar;
    @FXML
    private Button btModificar;
    @FXML
    private Button btEliminar;
    @FXML
    private Button btGuardar;
    @FXML
    private Button btRegresar;
    
    private int sceneType;
    private ObservableList<ProgramaEducativo> listProgramaEducativo;
    private boolean[] validationTextFields = {false, false};    
    private Pattern validateCharacter = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$");
    private Pattern validateCharacterClave = Pattern.compile("^[0-9]{5}$");    
    @FXML
    private Label labelInvalidateClave;
    @FXML
    private Label labelInvalidateNombre;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        sceneType=0;
        configureScene(sceneType);
        configureTableProgramasEducativos();
        loadProgramasEducativos();
        selectProgramaEducativo();
        
    }    
    
   
    
    private void selectProgramaEducativo(){
        tbProgramasEducativos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ProgramaEducativo>() {
            @Override
            public void changed(ObservableValue<? extends ProgramaEducativo> observable, ProgramaEducativo oldValue, ProgramaEducativo newValue) {
                if (newValue != null) {
                    sceneType=1;
                    configureScene(sceneType);
                } else {
                    sceneType=0;
                    configureScene(sceneType);
                }
            }
        });    
    }
    
    private ProgramaEducativo getTablaProgramasEducativos() {
        ProgramaEducativo programaEducativoSeleccioada = null;
        if (tbProgramasEducativos != null) {
            List<ProgramaEducativo> tabla = tbProgramasEducativos.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                programaEducativoSeleccioada = tabla.get(0);
            }
        }
        return programaEducativoSeleccioada;
    }    

    private void configureTableProgramasEducativos() {      
        Label noticeLoadingTable = new Label("Cargando información, espere un momento...");
        tbProgramasEducativos.setPlaceholder(noticeLoadingTable);
        ColumnClave.setCellValueFactory(new PropertyValueFactory("clave"));
        ColumnNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        listProgramaEducativo = FXCollections.observableArrayList();
        tbProgramasEducativos.setEditable(false);          
    }    
    
    private void loadProgramasEducativos() {
        try {
            ProgramaEducativoDAO programaEducativoDAO = new ProgramaEducativoDAO();
            ArrayList<ProgramaEducativo> listProgramasEducativosRecived = new ArrayList<>();
            listProgramasEducativosRecived = programaEducativoDAO.getProgramasEducativos();
            if(!listProgramasEducativosRecived.isEmpty()){
                listProgramaEducativo.clear();
                listProgramaEducativo.addAll(listProgramasEducativosRecived);
                tbProgramasEducativos.setItems(listProgramaEducativo);            
            }else{
                Label noticeLoadingTable = new Label("No hay información correspondiente.");
                tbProgramasEducativos.setPlaceholder(noticeLoadingTable);           
            }

        } catch (SQLException sqle) {
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, intentelo más tarde", Alert.AlertType.ERROR);
        }
    }

    private void configureScene(int scene){
        ProgramaEducativo item = new ProgramaEducativo();
        item = getTablaProgramasEducativos();         
        switch (scene) {
            case 0: //Normal
                tbProgramasEducativos.setDisable(false);                
                tfClave.setEditable(false);
                tfClave.setDisable(true);
                tfClave.setPromptText("");                                                
                tfClave.setText("");                
                tfNombre.setEditable(false);
                tfNombre.setDisable(true);    
                tfNombre.setPromptText("");                                                
                tfNombre.setText("");                
                lbInstruccion.setVisible(false);
                lbInstruccion.setText("");
                labelInvalidateNombre.setText("");
                labelInvalidateClave.setText("");
                btCancelar.setVisible(true);
                btCancelar.setDisable(false);
                btRegistrar.setVisible(true);
                btRegistrar.setDisable(false);                
                btModificar.setVisible(true);
                btModificar.setDisable(true);
                btEliminar.setVisible(true);
                btEliminar.setDisable(true);
                btGuardar.setVisible(false);
                btGuardar.setDisable(true);
                btRegresar.setVisible(false);
                btRegresar.setDisable(true);
                                validationTextFields[0] = false;
                validationTextFields[1] = false;   
                break;
            case 1: //Consultar
                tbProgramasEducativos.setDisable(false);                
                tfClave.setEditable(false);
                tfClave.setDisable(true);
                tfClave.setPromptText("");                                
                tfClave.setText(item.getClave());                
                tfNombre.setEditable(false);
                tfNombre.setDisable(true);     
                tfNombre.setPromptText("");                                
                tfNombre.setText(item.getNombre());                                
                lbInstruccion.setVisible(true);
                lbInstruccion.setText("Consulta de Programa Educativo");
                labelInvalidateNombre.setText("");
                labelInvalidateClave.setText("");                
                btCancelar.setVisible(true);
                btCancelar.setDisable(false);
                btRegistrar.setVisible(true);
                btRegistrar.setDisable(false);                
                btModificar.setVisible(true);
                btModificar.setDisable(false);
                btEliminar.setVisible(true);
                btEliminar.setDisable(false);
                btGuardar.setVisible(false);
                btGuardar.setDisable(true);
                btRegresar.setVisible(false);
                btRegresar.setDisable(true); 
                validationTextFields[0] = false;
                validationTextFields[1] = false;                   
                break;
            case 2: //Registrar
                tbProgramasEducativos.setDisable(true);
                tfClave.setEditable(true);
                tfClave.setDisable(false);
                tfClave.setText("");
                tfClave.setPromptText("Eje. 11111");
                tfNombre.setEditable(true);
                tfNombre.setDisable(false);   
                tfNombre.setText("");     
                tfNombre.setPromptText("Eje. Tecnologias de Informacion");
                lbInstruccion.setVisible(true);
                lbInstruccion.setText("Registro de Nuevo Programa Educativo");
                labelInvalidateNombre.setText("");
                labelInvalidateClave.setText("");                
                btCancelar.setVisible(true);
                btCancelar.setDisable(false);
                btRegistrar.setVisible(false);
                btRegistrar.setDisable(true);                
                btModificar.setVisible(false);
                btModificar.setDisable(true);
                btEliminar.setVisible(false);
                btEliminar.setDisable(true);
                btGuardar.setVisible(true);
                btGuardar.setDisable(true);
                btRegresar.setVisible(true);
                btRegresar.setDisable(false); 
                validationTextFields[0] = false;
                validationTextFields[1] = false;                 
                break;
            case 3: //Modificar     
                tbProgramasEducativos.setDisable(true);                
                tfClave.setEditable(false);
                tfClave.setDisable(true);
                tfClave.setPromptText("");                
                tfClave.setText(item.getClave());                                
                tfNombre.setEditable(true);
                tfNombre.setDisable(false); 
                tfNombre.setPromptText("");                
                tfNombre.setText(item.getNombre());                                                
                lbInstruccion.setVisible(true);
                lbInstruccion.setText("Modificacion de Programa Educativo");
                labelInvalidateNombre.setText("");
                labelInvalidateClave.setText("");                
                btCancelar.setVisible(true);
                btCancelar.setDisable(false);
                btRegistrar.setVisible(false);
                btRegistrar.setDisable(true);                
                btModificar.setVisible(false);
                btModificar.setDisable(true);
                btEliminar.setVisible(false);
                btEliminar.setDisable(true);
                btGuardar.setVisible(true);
                btGuardar.setDisable(false);
                btRegresar.setVisible(true);
                btRegresar.setDisable(false);
                validationTextFields[0] = true;
                validationTextFields[1] = false;                
                break;
            default:
                break;
        }    
    
    
    
    }
    
    private void closeWindow() {
        Stage escenario = (Stage) lbInstruccion.getScene().getWindow();
        escenario.close();
        WindowManager.NavigateToWindow(lbInstruccion.getScene().getWindow(), "/GUI/FXMLMainMenu.fxml", "Menú");
    }    
    
    @FXML
    private void clicButtonCancelar(ActionEvent event) {
        switch (sceneType) {
            case 2: //Registrar
            case 3: //Modificar
                Optional<ButtonType> answer;                
                answer = AlertManager.showAlert("AVISO",
                        "NO se guardarán los cambios. \n\n¿Desea continuar?", Alert.AlertType.CONFIRMATION); 
                if (answer.get() == ButtonType.OK) {
                    closeWindow();
                }                    
                break;
            default:
                closeWindow();                        
                break;
         }                
    }

    @FXML
    private void clicButtonRegistrar(ActionEvent event) {
        sceneType=2;
        configureScene(sceneType);
    }

    @FXML
    private void clicButtonModificar(ActionEvent event) {
        sceneType=3;
        configureScene(sceneType);        
    }

    @FXML
    private void clicButtonEliminar(ActionEvent event) {
        ProgramaEducativo item = new ProgramaEducativo();
        item.setClave(tfClave.getText());
        item.setNombre(tfNombre.getText());       
        try {
            Optional<ButtonType> answer;
            answer = AlertManager.showAlert("AVISO", "Solo se eliminira el programa educativo seleccionado si no hay reportes relacionados.\n" +
                                    "¿Desea continuar con la eliminación?", Alert.AlertType.CONFIRMATION);            
            if (answer.get() == ButtonType.OK) {
                ProgramaEducativoDAO programaEducativoDAO = new ProgramaEducativoDAO();
                int result = programaEducativoDAO.validateProgramaEducativo(item);
                if(result>0){
                    sceneType=1;
                    configureScene(sceneType);                  
                    AlertManager.showTemporalAlert("AVISO", "Este Programa Educativo Tiene registros", 2);            
                }else if(result==0){
                    if(programaEducativoDAO.deleteProgramaEducativo(item)){
                            sceneType=0;
                            configureScene(sceneType);                      
                            Label noticeLoadingTable = new Label("Cargando información, espere un momento...");
                            tbProgramasEducativos.setPlaceholder(noticeLoadingTable);                    
                            loadProgramasEducativos();                    
                            AlertManager.showTemporalAlert("AVISO", "Se Elimino el Programa Educativo", 2);                                       
                    }
                }
            }                
        } catch (SQLException ex) {
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, intentelo mas tarde", Alert.AlertType.ERROR);
        }              
    }

    @FXML
    private void clicButtonGuardar(ActionEvent event) {
        ProgramaEducativo item = new ProgramaEducativo();
        item.setClave(tfClave.getText());
        item.setNombre(tfNombre.getText());       
        try {
            ProgramaEducativoDAO programaEducativoDAO = new ProgramaEducativoDAO();
            switch (sceneType) {
                case 2: //Registrar
                    if(programaEducativoDAO.setProgramaEducativoRegister(item)){
                        Label noticeLoadingTable = new Label("Cargando información, espere un momento...");
                        tbProgramasEducativos.setPlaceholder(noticeLoadingTable);
                        loadProgramasEducativos();
                        AlertManager.showTemporalAlert("AVISO", "Se registro el nuevo Programa Educativo", 2);                        
                    }
                    break;
                case 3: //Modificar     
                    if(programaEducativoDAO.updateProgramaEducativo(item)){
                        Label noticeLoadingTable = new Label("Cargando información, espere un momento...");
                        tbProgramasEducativos.setPlaceholder(noticeLoadingTable);
                        loadProgramasEducativos();
                        AlertManager.showTemporalAlert("AVISO", "Se Modifico el nuevo Programa Educativo", 2);                        
                    }
                    break;
                default:
                    break;
            }
            sceneType=0;
            configureScene(sceneType);             
        } catch (SQLException ex) {
            AlertManager.showAlert("Error", "No hay conexión con la base de datos, intentelo mas tarde", Alert.AlertType.ERROR);
        }        
    
    }

    @FXML
    private void clicButtonRegresar(ActionEvent event) {
        if(sceneType == 2){
            sceneType=0;
            configureScene(sceneType);           
        }else{
            sceneType=1;
            configureScene(sceneType);         
        }        
    }

    @FXML
    private void validateInputsClave(KeyEvent event) {
        Matcher matcher = validateCharacterClave.matcher(tfClave.getText());
        if (!matcher.matches()) {
            labelInvalidateClave.setText("Datos invalidos");
            validationTextFields[0] = false;
        } else {
            labelInvalidateClave.setText("");
            validationTextFields[0] = true;
        }
        enableButton();        
    }

    @FXML
    private void validateLengthClave(KeyEvent event) {
        tfClave.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 5) {
                return change;
            } else {
                return null;
            }
        }));
        if (tfClave.getText().length() > 5) {
            tfClave.setText("");
        }        
    }

    @FXML
    private void validateInputsNombre(KeyEvent event) {
        Matcher matcher = validateCharacter.matcher(tfNombre.getText());
        if (!matcher.matches()) {
            labelInvalidateNombre.setText("Datos invalidos");
            validationTextFields[1] = false;
        } else {
            labelInvalidateNombre.setText("");
            validationTextFields[1] = true;
        }                           
        enableButton();        
    }

    @FXML
    private void validateLengthNombre(KeyEvent event) {
        tfNombre.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 30) {                
                return change;
            } else {                
                return null;
            }
        }));        
        if (tfNombre.getText().length() > 30) {
            tfNombre.setText("");
        }        
    }
    
    private void enableButton() {
        if (!validationTextFields[0] || !validationTextFields[1]) {
            btGuardar.setDisable(true);
        } else {
            btGuardar.setDisable(false);            
        }
    }    
    
}
