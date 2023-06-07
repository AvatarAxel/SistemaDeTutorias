/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package BussinessLogic;

import Domain.ExperienciaEducativa;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Panther
 */
public interface IExperiencaEducativaDAO {
    
    public int uploadAcademicOffer(ExperienciaEducativa experienciaEducativa) throws SQLException;
    
    public ExperienciaEducativa consultExperiencias()throws SQLException;
    
    public int updateAcademicOffer(ExperienciaEducativa experienciaEducativa) throws SQLException;

    public ArrayList<String> consultExperienciasName(String clave)throws SQLException;
    
    public boolean existNrc(String nrc) throws SQLException;
}
