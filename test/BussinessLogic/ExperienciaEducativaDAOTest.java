/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package BussinessLogic;

import Domain.ExperienciaEducativa;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Panther
 */
public class ExperienciaEducativaDAOTest {
    
    public ExperienciaEducativaDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of uploadAcademicOffer method, of class ExperienciaEducativaDAO.
     */
    @Test
    public void testUploadAcademicOfferSuccess() throws Exception {
        System.out.println("uploadAcademicOfferSuccess");
        ExperienciaEducativa experienciaEducativa = new ExperienciaEducativa("12345", "Prueba", "1", "Virtual", "14203");
        ExperienciaEducativaDAO instance = new ExperienciaEducativaDAO();
        int expResult = 1;
        int result = instance.uploadAcademicOffer(experienciaEducativa);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of uploadAcademicOffer method, of class ExperienciaEducativaDAO.
     */
    @Test
    public void testUploadAcademicOfferFail() throws Exception {
        System.out.println("uploadAcademicOfferFail");
        ExperienciaEducativa experienciaEducativa = null;
        ExperienciaEducativaDAO instance = new ExperienciaEducativaDAO();
        int expResult = 0;
        int result = instance.uploadAcademicOffer(experienciaEducativa);
        assertEquals(expResult, result);
    }
}
