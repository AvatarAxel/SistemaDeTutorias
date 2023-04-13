/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package BussinessLogic;

import Domain.Profesor;
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
public class ProfesorDAOTest {
    
    public ProfesorDAOTest() {
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
     * Test of getProfesor method, of class ProfesorDAO.
     */
    @Test
    public void testGetProfesorSuccess() throws Exception {
        System.out.println("getProfesorSuccess");
        int numeroDePersonal = 35694;
        ProfesorDAO instance = new ProfesorDAO();
        Profesor expResult = new Profesor(35694, "Zuzana", "Chedgey", "Bushill", "zbushill14@multiply.com");
        Profesor result = instance.getProfesor(numeroDePersonal);
        System.out.println("Ex: "+expResult.toString());
        System.out.println("Re: "+result.toString());
        assertEquals(expResult, result);
    }
    /**
     * Test of getProfesor method, of class ProfesorDAO.
     */
    @Test
    public void testGetProfesorFail() throws Exception {
        System.out.println("getProfesorFail");
        int numeroDePersonal = 0;
        ProfesorDAO instance = new ProfesorDAO();
        Profesor expResult = new Profesor(35694, "Zuzana", "Chedgey", "Bushill", "zbushill14@multiply.com");
        Profesor result = instance.getProfesor(numeroDePersonal);
        assertNotEquals(expResult, result);
    }
    
}
