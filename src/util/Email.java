/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 *
 * @author michikato
 */
public class Email {
    
    private String password;
    private String emailDirection;
    
    public void sendEmailNewUser(String emailDestiner, String newUserPassword) {
        readCredentialsFile();
        Properties emailProperties = new Properties();
        emailProperties = initializingEmailOutlook();

        try {
            Session sesion = Session.getDefaultInstance(emailProperties);
            MimeMessage mensaje = new MimeMessage(sesion);
            mensaje.setFrom(new InternetAddress(emailDirection));
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(emailDestiner));
            mensaje.setSubject("Bienvenido al Sistema de tutorías");
            mensaje.setText("Bienvenido al Sistema de tutorías \n \n \n"
                    + "TUS CREDENCIALES TEMPORALES\n"
                    + "Contraseña: " + newUserPassword + "\n \n \n Recuerda por seguridad cambiar la contraseña");
            
            Transport transport = sesion.getTransport("smtp");
            transport.connect(emailDirection, password);
            transport.sendMessage(mensaje, mensaje.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {            
            System.out.print(e);
        }
    }
    
    private Properties initializingEmailOutlook() {
        Properties emailProperties = new Properties();
        emailProperties.put("mail.smtp.user", emailDirection);
        emailProperties.put("mail.smtp.auth", "true");
        emailProperties.put("mail.smtp.starttls.enable", "true");
        emailProperties.put("mail.smtp.host", "smtp.office365.com");
        emailProperties.put("mail.smtp.ssl.trust", "smtp.office365.com");
        emailProperties.put("mail.smtp.port", "587");
        return emailProperties;
    }
    
    private void readCredentialsFile() {
        try {
            FileReader fileReader = new FileReader("resources/CredencialesEmail.txt");
            BufferedReader bufferReader = new BufferedReader(fileReader);
            try {
                emailDirection = bufferReader.readLine();
                password = bufferReader.readLine();
                bufferReader.close();
            } catch (IOException e1) {
                System.out.println("Error en la lectura");
            }
        } catch (FileNotFoundException ex) {
            System.out.println("No existe el archivo");
        }
    }
     
}
