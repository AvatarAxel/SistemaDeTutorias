/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Panther
 */
public class Alerts {

    private Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
    private Alert alertError = new Alert(Alert.AlertType.ERROR);
    private Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
    private Alert alertWarning = new Alert(Alert.AlertType.WARNING);
    
     Stage stageInformation = (Stage) alertInformation.getDialogPane().getScene().getWindow();
     Stage stageError = (Stage) alertError.getDialogPane().getScene().getWindow();
     Stage stageConfirmation = (Stage) alertConfirmation.getDialogPane().getScene().getWindow();
     Stage stageWarning = (Stage) alertWarning.getDialogPane().getScene().getWindow();


       // stage.getIcons().add(new Image("images/icon.png"));
    
    public static Optional<ButtonType> showAlert(String titulo, String mensaje, Alert.AlertType tipoAlerta) {
        Alert alerta = new Alert(tipoAlerta);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        return alerta.showAndWait();
    }

    public void showAlertErrorConexionDB() {
        stageError.getIcons().add(new Image("images/icon.png"));
        alertError.setTitle("Atención");
        alertError.setHeaderText("Perdida de conexión con la Base de Datos");
        alertError.setContentText("Por favor intente mas tarde.");
        alertError.showAndWait();
    }

    public void showAlertSuccesfulRegister() {
        stageInformation.getIcons().add(new Image("images/icon.png"));
        alertInformation.setTitle("Proceso completado");
        alertInformation.setHeaderText("Registro exitoso");
        alertInformation.setContentText("Se ha registrado correctamente");
        alertInformation.showAndWait();
    }
    
     public void showAlertSuccesfulUpdate() {
        
        stageInformation.getIcons().add(new Image("images/icon.png"));
        alertInformation.setTitle("Proceso completado");
        alertInformation.setHeaderText("Actualización exitosa");
        alertInformation.setContentText("Se ha modificado correctamente");
        alertInformation.showAndWait();
    }
     
      public void showAlertSuccesfulDelete() {
        stageInformation.getIcons().add(new Image("images/icon.png"));
        alertInformation.setTitle("Proceso completado");
        alertInformation.setHeaderText("Petición exitosa");
        alertInformation.setContentText("Se ha elimindado correctamente");
        alertInformation.showAndWait();
    }
     public void showAlertNotProblematicasFound() {
        stageInformation.getIcons().add(new Image("images/icon.png"));
        alertInformation.setTitle("Información");
        alertInformation.setHeaderText("Registros");
        alertInformation.setContentText("Aún no se han registrado problemáticas");
        alertInformation.showAndWait();
    }
     
     public void showAlertEmptyFields() {
        stageWarning.getIcons().add(new Image("images/icon.png"));
        alertWarning.setTitle("Advertencia");
        alertWarning.setHeaderText("Campos Vacíos");
        alertWarning.setContentText("Por favor,revise nuevamente.");
        alertWarning.showAndWait();
    }
       public void showAlertInvalidInputs() {
        stageWarning.getIcons().add(new Image("images/icon.png"));
        alertWarning.setTitle("Advertencia");
        alertWarning.setHeaderText("Ha ingresado datos inválidos");
        alertWarning.setContentText("Por favor,revise nuevamente.");
        alertWarning.showAndWait();
    }
    
      public void showAlertManyTutorados() {
        stageWarning.getIcons().add(new Image("images/icon.png"));
        alertWarning.setTitle("Advertencia");
        alertWarning.setHeaderText("Excedio la asignación de estudiantes");
        alertWarning.setContentText("Menos de 30 estudiantes");
        alertWarning.showAndWait();
    }
      
        public void showAlertIncorrectLogin() {
        stageWarning.getIcons().add(new Image("images/icon.png"));
        alertWarning.setTitle("Advertencia");
        alertWarning.setHeaderText("Usuario o Contraseña incorrecta");
        alertWarning.setContentText("Por favor, revise nuevamente");
        alertWarning.showAndWait();
    }
    
  
}
