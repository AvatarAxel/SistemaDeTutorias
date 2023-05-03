/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package BussinessLogic;

import Domain.TutoriaAcademica;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Panther
 */
public interface ITutoriaAcademicaDAO {
    
    public ArrayList<TutoriaAcademica> getTutoriasAcademicasByPeriodo(int idPeriodo, String clave)throws SQLException;
}
