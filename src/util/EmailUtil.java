/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author michikato
 */
public class EmailUtil {

    private String apiKey;
    private String emailDirection;

    public void sendEmailNewUser(String emailDestiner, String newUserPassword) {
        Email from = new Email(emailDirection);
        String subject = "Bienvenido al Sistema de tutorias - Universidad Veracruzana";
        Email to = new Email(emailDestiner);
        Content content = new Content("text/plain", "Bienvenido al Sistema de tutorías \n \n \n"
                                        + "TUS CREDENCIALES TEMPORALES\n"
                                        + "Contraseña: " + newUserPassword 
                                        + "\n \n \n Recuerda por seguridad cambiar la contraseña");
        Mail mail = new Mail(from, subject, to, content);
        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void readCredentialsFile() {
        try {
            FileReader fileReader = new FileReader("resources/CredencialesEmail.txt");
            BufferedReader bufferReader = new BufferedReader(fileReader);
            try {
                emailDirection = bufferReader.readLine();
                apiKey = bufferReader.readLine();
                bufferReader.close();
            } catch (IOException e1) {
                System.out.println("Error en la lectura");
            }
        } catch (FileNotFoundException ex) {
            System.out.println("No existe el archivo");
        }
    }

}
