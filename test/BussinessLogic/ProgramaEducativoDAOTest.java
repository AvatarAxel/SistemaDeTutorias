/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package BussinessLogic;

import Domain.ProgramaEducativo;
import java.util.ArrayList;
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
public class ProgramaEducativoDAOTest {
    
    public ProgramaEducativoDAOTest() {
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
     * Test of getProgramasEducativos method, of class ProgramaEducativoDAO.
     */
    @Test
    public void testGetProgramasEducativos() throws Exception {
        System.out.println("getProgramasEducativos");
        ProgramaEducativoDAO instance = new ProgramaEducativoDAO();
        ArrayList<ProgramaEducativo> expResult = null;
        ArrayList<ProgramaEducativo> result = instance.getProgramasEducativos();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
