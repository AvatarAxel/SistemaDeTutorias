/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package BussinessLogic;

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
public class SolucionAProblematicaDAOTest {
    
    public SolucionAProblematicaDAOTest() {
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
     * Test of insertSolucionAProblematica method, of class SolucionAProblematicaDAO.
     */
    @Test
    public void testInsertSolucionAProblematicaSuccess() throws Exception {
        System.out.println("insertSolucionAProblematicaSuccess");
        int idProblematica = 8;
        String solucion = "Pokémon";
        SolucionAProblematicaDAO instance = new SolucionAProblematicaDAO();
        int expResult = 1;
        int result = instance.insertSolucionAProblematica(idProblematica, solucion);
        assertEquals(expResult, result);
    }
    /**
     * Test of updateSolucionAProblematica method, of class SolucionAProblematicaDAO.
     */
    @Test
    public void testUpdateSolucionAProblematicaSuccess() throws Exception {
        System.out.println("updateSolucionAProblematicaSuccess");
        int idSolucion = 1;
        String solucion = "Pokémon";
        SolucionAProblematicaDAO instance = new SolucionAProblematicaDAO();
        int expResult = 1;
        int result = instance.updateSolucionAProblematica(idSolucion, solucion);
        assertEquals(expResult, result);
    }
}
