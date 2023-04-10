/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.util.Optional;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.util.Duration;

/**
 *
 * @author Panther
 */
public class Alerts {
    public static Optional<ButtonType> showAlert(String titulo, String mensaje, Alert.AlertType tipoAlerta){
        Alert alerta = new Alert(tipoAlerta);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        return alerta.showAndWait();
    }
    
    public static Optional<ButtonType> showTemporalAlert(String title, String message, Alert.AlertType alertType, int time) {
        Alert alert = new Alert(alertType, message);
        alert.setHeaderText(null);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(time), event -> alert.hide()));
        timeline.play();
        return alert.showAndWait();
    }
    
}
