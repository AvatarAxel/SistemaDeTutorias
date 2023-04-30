package controllers;

import BussinessLogic.PeriodoEscolarDAO;
import BussinessLogic.TutoriaAcademicaDAO;
import Domain.PeriodoEscolar;
import Domain.TutoriaAcademica;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import singleton.User;
import util.AlertManager;
import util.WindowManager;

/**
 * FXML Controller class
 *
 * @author Valeria
 */
public class FXMLRegistrarFechasTutoriasController implements Initializable {

    @FXML
    private ComboBox cmb_numSesion;
    @FXML
    private DatePicker datepicker_startDate;
    @FXML
    private DatePicker datepicker_enddate;
    @FXML
    private Button btn_save;
    private AlertManager alerts = new AlertManager();
    @FXML
    private Label lbl_periodo;

    int IdPeriodoEscolar;
    TutoriaAcademica tutoria = new TutoriaAcademica();
    PeriodoEscolar periodoEscolar = new PeriodoEscolar();
    @FXML
    private Button btn_close;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            PeriodoEscolarDAO PeriodoEscolarDAO = new PeriodoEscolarDAO();
            periodoEscolar = PeriodoEscolarDAO.getCurrentPeriodoEscolar();

            lbl_periodo.setText(periodoEscolar.getFechasPeridoEscolar());
            loadComboBoxSesiones();

        } catch (SQLException ex) {
            alerts.showAlertErrorConexionDB();
        }

    }

    @FXML
    private void saveDates(ActionEvent event) {
        int validate = this.validateDates();
        if (validate == 1) {
            java.sql.Date startDate = java.sql.Date.valueOf(datepicker_startDate.getValue());
            java.sql.Date endDate = java.sql.Date.valueOf(datepicker_enddate.getValue());
            int numeroSesion = (int) cmb_numSesion.getValue();
            TutoriaAcademica tutoria = new TutoriaAcademica();
            PeriodoEscolar periodo = new PeriodoEscolar();
            tutoria.setFechaInicio(startDate);
            tutoria.setFechaFin(endDate);
            tutoria.setNumeroDeSesion(numeroSesion);
            periodo.setIdPeriodoEscolar(periodoEscolar.getIdPeriodoEscolar());
            periodo.setClave(User.getCurrentUser().getRol().getProgramaEducativo().getClave());
            tutoria.setPeriodoEscolar(periodo);
            TutoriaAcademicaDAO tutoriadao = new TutoriaAcademicaDAO();
            try {
                int result = tutoriadao.addTutoriaAcademica(tutoria);
                if (result == 1) {
                    alerts.showAlertSuccesfulRegister();
                    disableItems();
                    
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

    private void loadComboBoxSesiones() {
        ObservableList<Integer> firstRegister = FXCollections.observableArrayList();
        cmb_numSesion.setItems(firstRegister);

        TutoriaAcademicaDAO tutoriaDAO = new TutoriaAcademicaDAO();
        try {
            tutoria = tutoriaDAO.getCurrentTutoriaAcademica(User.getCurrentUser().getRol().getProgramaEducativo().getClave());
            JOptionPane.showMessageDialog(null, tutoria.getNumeroDeSesion());

            if (tutoria != null) {
                if (tutoria.getNumeroDeSesion() >= 3) {

                    alerts.showAlertRegisterCompletedSesiones();

                } else if (tutoria.getNumeroDeSesion() == 2) {
                    cmb_numSesion.getItems().addAll(3);

                } else if (tutoria.getNumeroDeSesion() == 1) {
                    cmb_numSesion.getItems().addAll(2);
                }

            } else if (tutoria==null) {
                cmb_numSesion.getItems().addAll(1);

            }
            cmb_numSesion.setItems(firstRegister);
        } catch (SQLException ex) {
            alerts.showAlertErrorConexionDB();
            ex.printStackTrace();
        }

    }

    private int getNumeroSesionActual() {
        int numeroSesionActual = 0;
        TutoriaAcademicaDAO tutoriaDAO = new TutoriaAcademicaDAO();
        try {
            numeroSesionActual = tutoriaDAO.getNumeroSesion(IdPeriodoEscolar, User.getCurrentUser().getRol().getProgramaEducativo().getClave());
        } catch (SQLException ex) {
            alerts.showAlertErrorConexionDB();
            disableItems();
        }

        return numeroSesionActual;

    }

    private int validateDates() {
        int result = 0;

        if (cmb_numSesion.getValue() != null && datepicker_startDate.getValue() != null && datepicker_enddate.getValue()!= null) {
            java.sql.Date startDate = java.sql.Date.valueOf(datepicker_startDate.getValue());
            java.sql.Date endDate = java.sql.Date.valueOf(datepicker_enddate.getValue());
            if (startDate.before(endDate) && !startDate.equals(endDate) && endDate.after(startDate)) {

                if (startDate.after(periodoEscolar.getFechaInicio()) && endDate.before(periodoEscolar.getFechaFin())) {

                    if (tutoria != null) {
                        if (startDate.after(tutoria.getFechaFin())) {
                            result = 1;
                        } else {
                            // mensaje de fechas ya registradas en otra sesión
                            JOptionPane.showMessageDialog(null, "Fechas ya registradas dentro de otra Sesión");
                        }

                    } else if (tutoria == null) {
                        result = 1;
                    }
                } else {
                    // mensahe de fechas fuera del periodo
                    JOptionPane.showMessageDialog(null, "Fechas ya registradas fuera del Periodo Actual");

                }
            } else {
                //mensaje de fechas inválidas 
                JOptionPane.showMessageDialog(null, "Fechas inválidas");

            }
        } else {
            JOptionPane.showMessageDialog(null, "Campos Vacíos");

        }
        return result;

    }

    @FXML
    private void enableDatesPickers(ActionEvent event) {
        datepicker_startDate.setDisable(false);

        datepicker_enddate.setDisable(false);
    }
    
     private void disableItems() {
        datepicker_startDate.setDisable(true);

        datepicker_enddate.setDisable(true);
        cmb_numSesion.setDisable(true);
    }

    @FXML
    private void closeWindow(ActionEvent event) {
          
        Stage escenario = (Stage) lbl_periodo.getScene().getWindow();
        escenario.close();
        WindowManager.NavigateToWindow(lbl_periodo.getScene().getWindow(), "/GUI/FXMLMainMenu.fxml", "Menú");        
    
    }

}
