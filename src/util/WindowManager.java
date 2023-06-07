/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author Panther
 */
public class WindowManager {
    public static void NavigateToWindow(Window window, String windowPath, String windowTitle){
        WindowManager navigator = new WindowManager();
        navigator.goToWindow(window, windowPath, windowTitle);
    }
    
    public static void closeWindow(Window window){
        Stage currentStage = (Stage) window;
        currentStage.close();
    }

    /*public static void NavigateToFloatingWindow(Window window, Object obj, String... arguments){
        WindowManager navigator = new WindowManager();
        navigator.showFloatingWindow(window, obj, arguments[0], arguments[1], arguments[2]);
    }*/
    //<-- Private Methods -->
    private void goToWindow(Window window, String windowPath, String windowTitle){
        try{
            Stage primaryStage = (Stage) window;
            Scene menuScene = new Scene(FXMLLoader.load(getClass().getResource(windowPath)));
            primaryStage.setScene(menuScene);
            primaryStage.setTitle(windowTitle);
            primaryStage.centerOnScreen();
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            AlertManager.showAlert("Error", "No se puede cargar la ventana", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
    
    /*private void showFloatingWindow(Window window, Object obj, String... arguments){
        
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(arguments[0]));//Ventana
            Parent root = loader.load();
            
            Object controladorFormulario = loader.getController();//Controlador
            
            executeMethod(arguments[2], controladorFormulario, obj);
            
            Stage primaryStage = (Stage) window;
            Scene floatingScene = new Scene(root);
            primaryStage.setScene(floatingScene);
            primaryStage.setTitle(arguments[1]);            
            primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.showAndWait();
            
            primaryStage.show();
        } catch (IOException e) {
            AlertManager.showAlert("Error", "No se puede cargar la ventana", Alert.AlertType.ERROR);
        }
    }
    
    private void executeMethod(String nombreMetodo, Object objeto, Object... argumentos) {
        try {
            
            Class<?> claseObjeto = objeto.getClass();
            Class<?>[] tiposArgumentos = new Class<?>[argumentos.length];
            for (int i = 0; i < argumentos.length; i++) {
                tiposArgumentos[i] = argumentos[i].getClass();
            }
            
            Method metodo = claseObjeto.getMethod(nombreMetodo, tiposArgumentos);
            metodo.invoke(objeto, argumentos);
            
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            Logger.getLogger(WindowManager.class.getName()).log(Level.SEVERE, null, e);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(WindowManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
    
}
