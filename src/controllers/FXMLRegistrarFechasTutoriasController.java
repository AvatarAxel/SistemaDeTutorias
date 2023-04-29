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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javax.swing.JOptionPane;
import singleton.User;
import util.AlertManager;

/**
 * FXML Controller class
 *
 * @author Valeria
 */
public class FXMLRegistrarFechasTutoriasController implements Initializable {

    @FXML
    private ComboBox<?> cmb_numSesion;
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
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            PeriodoEscolarDAO PeriodoEscolarDAO = new PeriodoEscolarDAO();
            PeriodoEscolar periodoEscolar=PeriodoEscolarDAO.getCurrentPeriodoEscolar();
            IdPeriodoEscolar = periodoEscolar.getIdPeriodoEscolar();
            //JOptionPane.showMessageDialog(null, periodoEscolar.getIdPeriodoEscolar() );
            lbl_periodo.setText(periodoEscolar.getFechasPeridoEscolar());
            

        } catch (SQLException ex) {
            alerts.showAlertErrorConexionDB();
        }
       
    }

    @FXML
    private void saveDates(ActionEvent event) {

        java.sql.Date startDate = java.sql.Date.valueOf(datepicker_startDate.getValue());
        java.sql.Date endDate = java.sql.Date.valueOf(datepicker_enddate.getValue());
        String clave = "14203";
        int peridoEscolar = 1;
        /*get PeriodoEscolar*/
        int numeroSesion = 2;
        TutoriaAcademica tutoria = new TutoriaAcademica();
        PeriodoEscolar periodo = new PeriodoEscolar();
        tutoria.setFechaInicio(startDate);
        tutoria.setFechaFin(endDate);
        tutoria.setNumeroDeSesion(numeroSesion);
        periodo.setIdPeriodoEscolar(peridoEscolar);
        periodo.setClave(clave);
        tutoria.setPeriodoEscolar(periodo);
        TutoriaAcademicaDAO tutoriadao = new TutoriaAcademicaDAO();
        try {
            int result = tutoriadao.addTutoriaAcademica(tutoria);
            if (result == 1) {
                alerts.showAlertSuccesfulRegister();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    private int validateDates(java.sql.Date startDate, java.sql.Date endDate) {
        int result = 0;

        return result;

    }

    private void loadComboBoxSesiones() {

    }

    private int getNumeroSesionActual() {
        int numeroSesionActual = 0;
        TutoriaAcademicaDAO tutoriaDAO = new TutoriaAcademicaDAO();
        try {
            numeroSesionActual= tutoriaDAO.getNumeroSesion(IdPeriodoEscolar,User.getCurrentUser().getRol().getProgramaEducativo().getClave() );
        } catch (SQLException ex) {
            alerts.showAlertErrorConexionDB();
        }
        

        return numeroSesionActual;

    }
    
    private void loadcmbSesiones(){
    int numeroSesionActual = this.getNumeroSesionActual();
    
    
    }

    private int validateData() {
        int result = 0;
        java.sql.Date startDate = java.sql.Date.valueOf(datepicker_startDate.getValue());
        java.sql.Date endDate = java.sql.Date.valueOf(datepicker_enddate.getValue());

        if (startDate.after(endDate) && !startDate.equals(endDate)) {
            

        }

        return result;

    }

}
