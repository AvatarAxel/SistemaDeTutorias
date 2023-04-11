/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author michikato
 */
public class DateLatinAmerica {

    public static String DateConvertToPeriod(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");       
        String formattedDate = simpleDateFormat.format(date);
        String month = new DateLatinAmerica().returnMonth(formattedDate.substring(3,5));
        String year = formattedDate.substring(6,10);
        return month + " " + year;
    }
    
    public String returnMonth(String month) {                        
        switch (month) {
            case "01":
                return "enero";
            case "02":
                return "febrero";
            case "03":
                return "marzo";
            case "04":
                return "abril";
            case "05":
                return "mayo";
            case "06":
                return "junio";
            case "07":
                return "julio";
            case "08":
                return "agosto";
            case "09":
                return "septiembre";
            case "10":
                return "octubre";
            case "11":
                return "noviembre";
            case "12":
                return "diciembre";
            default:
                return "Carne";
        }
    }

}
