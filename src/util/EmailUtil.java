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
public class EmailUtil {

    private String apiKey;
    private String emailDirection;
    private String password;
    private String emailDirectionOutlook;

    public void sendEmailNewUserTwilio(String emailDestiner, String newUserPassword) {
        readCredentialsFileTwilio();
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

    public void sendEmailChangePasswordTwilio(String emailDestiner, String verificationCode) {
        readCredentialsFileTwilio();
        Email from = new Email(emailDirection);
        String subject = "Codigo de verificación - Sistema de tutorias";
        Email to = new Email(emailDestiner);
        Content content = new Content("text/plain", "Codigo de verificacion \n \n \n"
                + "NO LO COMPARTAS CON NADIE\n"
                + "Codigo de verificación: " + verificationCode);
        Mail mail = new Mail(from, subject, to, content);
        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void sendEmailNewUserOutlook(String emailDestiner, String newUserPassword) {
        readCredentialsFileOutlook();
        Properties emailProperties = new Properties();
        emailProperties = initializingEmailOutlook();

        try {
            Session sesion = Session.getDefaultInstance(emailProperties);
            MimeMessage mensaje = new MimeMessage(sesion);
            mensaje.setFrom(new InternetAddress(emailDirectionOutlook));
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

    public void sendEmailChangePasswordOutlook(String emailDestiner, String verificationCode) {
        readCredentialsFileOutlook();
        Properties emailProperties = new Properties();
        emailProperties = initializingEmailOutlook();

        try {
            Session sesion = Session.getDefaultInstance(emailProperties);
            MimeMessage mensaje = new MimeMessage(sesion);
            mensaje.setFrom(new InternetAddress(emailDirectionOutlook));
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(emailDestiner));
            mensaje.setSubject("Cambio de contraseña");
            mensaje.setText("No compartas con nadie este codigo \n \n \n"
                    + "TUS CODIGO DE VERIFICACION\n"
                    + "Codigo: " + verificationCode);

            Transport transport = sesion.getTransport("smtp");
            transport.connect(emailDirectionOutlook, password);
            transport.sendMessage(mensaje, mensaje.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            System.out.print(e);
        }
    }

    private Properties initializingEmailOutlook() {
        Properties emailProperties = new Properties();
        emailProperties.put("mail.smtp.user", emailDirectionOutlook);
        emailProperties.put("mail.smtp.auth", "true");
        emailProperties.put("mail.smtp.starttls.enable", "true");
        emailProperties.put("mail.smtp.host", "smtp.office365.com");
        emailProperties.put("mail.smtp.ssl.trust", "smtp.office365.com");
        emailProperties.put("mail.smtp.port", "587");
        return emailProperties;
    }

    private void readCredentialsFileTwilio() {
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

    private void readCredentialsFileOutlook() {
        try {
            FileReader fileReader = new FileReader("resources/CredencialesEmailOutlook.txt");
            BufferedReader bufferReader = new BufferedReader(fileReader);
            try {
                emailDirectionOutlook = bufferReader.readLine();
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
