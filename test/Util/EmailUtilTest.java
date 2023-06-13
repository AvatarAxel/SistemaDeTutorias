/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import java.net.URL;
import java.util.ResourceBundle;
import org.junit.Test;
import static org.junit.Assert.*;
import util.EmailUtil;

/**
 *
 * @author michikato
 */
public class EmailUtilTest {

    @Test
    public void testEmail() {
        EmailUtil emailUtil = new EmailUtil();
        String emailDestiner = "zs20015691@estudiantes.uv.mx";
        String newUserPassword = "temporalPassword";
        emailUtil.sendEmailNewUserOutlook(emailDestiner, newUserPassword);
    }
    
        @Test
    public void testEmailChangePassword() {
        EmailUtil emailUtil = new EmailUtil();
        String emailDestiner = "zs20015691@estudiantes.uv.mx";
        String newUserPassword = "temporalPassword";
        emailUtil.sendEmailChangePasswordOutlook(emailDestiner, newUserPassword);
    }
    
    //new EmailUtil().sendEmailChangePasswordOutlook(textFieldCorreo.getText(), verificationCode);
}
