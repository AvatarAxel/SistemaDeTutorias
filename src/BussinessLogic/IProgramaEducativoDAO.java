/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package BussinessLogic;

import Domain.ProgramaEducativo;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Panther
 */
public interface IProgramaEducativoDAO {
    
    public ArrayList<ProgramaEducativo> getProgramasEducativos() throws SQLException;
    
}
