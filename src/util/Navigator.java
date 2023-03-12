/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author Panther
 */
public class Navigator {
    public static void NavigateToWindow(Window window, String windowPath, String windowTitle){
        Navigator navigator = new Navigator();
        navigator.goToWindow(window, windowPath, windowTitle);
    }
    
    public static void closeWindow(Window window){
        Stage currentStage = (Stage) window;
        currentStage.close();
    }
    
    //<-- Private Methods -->
    private void goToWindow(Window window, String windowPath, String windowTitle){
        try{
            Stage primaryStage = (Stage) window;
            Scene menuScene = new Scene(FXMLLoader.load(getClass().getResource(windowPath)));
            primaryStage.setScene(menuScene);
            primaryStage.setTitle(windowTitle);
            primaryStage.show();
        } catch (IOException e) {
            Alerts.showAlert("Error", "No se puede cargar la ventana", Alert.AlertType.ERROR);
        }
    }
    
}
