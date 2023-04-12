/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.security.SecureRandom;

/**
 *
 * @author michikato
 */
public class Random {    
    private static final String CHARACTERSPASSWORD = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String CHARACTERVERIFICATIONCODE = "0123456789";
    
    public String passwordGenerator(){
        String randomPassword = null;
        SecureRandom random = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder(16);
        for (int i = 0; i < 16; i++) {
            int index = random.nextInt(CHARACTERSPASSWORD.length());
            stringBuilder.append(CHARACTERSPASSWORD.charAt(index));
        }        
        randomPassword = stringBuilder.toString();
        return randomPassword;        
    }
    
    public String verificationCodeGenerator() {
        String verificationCode = null;
        SecureRandom random = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder(5);
        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(CHARACTERVERIFICATIONCODE.length());
            stringBuilder.append(CHARACTERVERIFICATIONCODE.charAt(index));
        }        
        verificationCode = stringBuilder.toString();        
        return verificationCode;
    }
}
