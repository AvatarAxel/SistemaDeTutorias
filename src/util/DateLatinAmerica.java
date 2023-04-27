/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 *
 * @author michikato
 */
public class DateLatinAmerica {

    static final HashMap<String, String> MONTH = new HashMap<String, String>() {
        {
            put("01", "Enero");
            put("02", "Febrero");
            put("03", "Marzo");
            put("04", "Abril");
            put("05", "Mayo");
            put("06", "Junio");
            put("07", "Julio");
            put("08", "Agosto");
            put("09", "Septiembre");
            put("10", "Octubre");
            put("11", "Noviembre");
            put("12", "Diciembre");
        }
    };

    public static String DateConvertToPeriod(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = simpleDateFormat.format(date);
        String month = MONTH.get(formattedDate.substring(3, 5));
        String year = formattedDate.substring(6, 10);
        return month + " " + year;
    }

    public static String DateWithDays(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = simpleDateFormat.format(date);
        String day = formattedDate.substring(0, 2);
        String month = MONTH.get(formattedDate.substring(3, 5));
        String year = formattedDate.substring(6, 10);
        return day + " " + month + " " + year;
    }

}
