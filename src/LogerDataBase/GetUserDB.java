/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LogerDataBase;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Valea
 */
public class GetUserDB {
    
  public UserDB getUser(){
        UserDB usuarioDB = new UserDB();
        String user = null;
        String password = null;
            try{
                FileReader fr= new FileReader("resources/Usuarios.txt");
                BufferedReader br= new BufferedReader(fr);
                try{
                    user = br.readLine();
                    password = br.readLine();
                    usuarioDB.setUser(user);
                    usuarioDB.setPassword(password);
                    br.close();
                }catch(IOException e1){
                    System.out.println("Error en la lectura");
                }
            }catch(FileNotFoundException ex){
                System.out.println("No existe el archivo");
            }
        return usuarioDB;
    }
}
