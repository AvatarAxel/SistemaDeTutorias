
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Valeria
 */
public class FXMLGestionarProblematicasController implements Initializable {

    @FXML
    private TableView<?> tblProblematicas;
    @FXML
    private TableColumn<?, ?> clm_IdProblematica;
    @FXML
    private TableColumn<?, ?> clm_Experiencia;
    @FXML
    private TableColumn<?, ?> clm_Profesor;
    @FXML
    private Button btn_Delete;
    @FXML
    private Label lbl_Profesor;
    @FXML
    private Label lbl_EE;
    @FXML
    private ComboBox<?> cbx_EE;
    @FXML
    private ComboBox<?> cmb_Profesor;
    @FXML
    private Label lbl_Header;
    @FXML
    private Label lbl_description;
    @FXML
    private TextArea txt_;
    @FXML
    private TextField txt_Title;
    @FXML
    private Label lbl_NumReportados;
    @FXML
    private Button btn_Save;
    @FXML
    private Button btn_Cancel;
    @FXML
    private TableColumn<?, ?> clm_Hd;
    @FXML
    private Spinner<?> spn_numReportados;
    @FXML
    private Button btn_Update;
    @FXML
    private Button btn_more;
    @FXML
    private Button btn_add;
    @FXML
    private TableColumn<?, ?> clm_Descrip;
    @FXML
    private TableColumn<?, ?> clm_numReportes;
    @FXML
    private Button btn_ModifyE;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    @FXML
    private void saveChanges(ActionEvent event) {
        
        
    }

    @FXML
    private void cancelChanges(ActionEvent event) {
    }


    @FXML
    private void addProblematica(ActionEvent event) {
    }

    @FXML
    private void updateProblematica(ActionEvent event) {
    }

    @FXML
    private void modifyEnviromentOn(ActionEvent event) {
    }

    @FXML
    private void deleteEnviromentOn(ActionEvent event) {
    }

    @FXML
    private void cleanFields(ActionEvent event) {
    }
    
}

