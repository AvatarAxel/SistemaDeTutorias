/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package BussinessLogic;

import Domain.ProblematicaAcademica;
import Domain.SolucionAProblematica;
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
public class ProblematicaAcademicaDAOTest {
    
    public ProblematicaAcademicaDAOTest() {
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
     * Test of getProblematicasSinSolucion method, of class ProblematicaAcademicaDAO.
     */
    @Test
    public void testGetProblematicasSinSolucionSuccess() throws Exception {
        System.out.println("getProblematicasSinSolucionSuccess");
        ProblematicaAcademicaDAO instance = new ProblematicaAcademicaDAO();
        ArrayList<ProblematicaAcademica> expResult = new ArrayList<>();
        expResult.add(new ProblematicaAcademica(8, "Progressive", 8, new SolucionAProblematica(0,"")));
        expResult.add(new ProblematicaAcademica(9, "tangible", 9, new SolucionAProblematica(0,"")));
        ArrayList<ProblematicaAcademica> result = instance.getProblematicasSinSolucion();
        assertEquals(expResult, result);
    }
    /**
     * Test of getProblematicasSinSolucion method, of class ProblematicaAcademicaDAO.
     */
    @Test
    public void testGetProblematicasSinSolucionFail() throws Exception {
        System.out.println("getProblematicasSinSolucionFail");
        ProblematicaAcademicaDAO instance = new ProblematicaAcademicaDAO();
        ArrayList<ProblematicaAcademica> expResult = new ArrayList<>();
        expResult.add(new ProblematicaAcademica(8, "Progressive", 8, new SolucionAProblematica(0,"")));
        expResult.add(new ProblematicaAcademica(9, "tangible", 1, new SolucionAProblematica(0,"")));
        ArrayList<ProblematicaAcademica> result = instance.getProblematicasSinSolucion();
        assertNotEquals(expResult, result);
    }

    /**
     * Test of getProblematicas method, of class ProblematicaAcademicaDAO.
     */
    @Test
    public void testGetProblematicasSuccess() throws Exception {
        System.out.println("getProblematicasSuccess");
        ProblematicaAcademicaDAO instance = new ProblematicaAcademicaDAO();
        ArrayList<ProblematicaAcademica> expResult = new ArrayList<>();
        expResult.add(new ProblematicaAcademica(1,"Quality-focused",1, new SolucionAProblematica(1,"Pokémon")));
        expResult.add(new ProblematicaAcademica(2,"asymmetric",2, new SolucionAProblematica(2,"rtrt")));
        expResult.add(new ProblematicaAcademica(3,"Adaptive",3,new SolucionAProblematica(3,"hghtt")));
        expResult.add(new ProblematicaAcademica(4, "Profit-focused", 4, new SolucionAProblematica(4,"rtrtrt")));
        expResult.add(new ProblematicaAcademica(5, "eco-centric", 5, new SolucionAProblematica(5,"trtr")));
        expResult.add(new ProblematicaAcademica(6, "website", 6, new SolucionAProblematica(6,"rtrt")));
        expResult.add(new ProblematicaAcademica(7, "cohesive", 7, new SolucionAProblematica(7,"fffssfsfggh")));
        ArrayList<ProblematicaAcademica> result = instance.getProblematicas();
        assertEquals(expResult, result);
    }
    /**
     * Test of getProblematicas method, of class ProblematicaAcademicaDAO.
     */
    @Test
    public void testGetProblematicasFail() throws Exception {
        System.out.println("getProblematicasFail");
        ProblematicaAcademicaDAO instance = new ProblematicaAcademicaDAO();
        ArrayList<ProblematicaAcademica> expResult = new ArrayList<>();
        expResult.add(new ProblematicaAcademica(2,"Quality-focused",1, new SolucionAProblematica(1,"Pokémon")));
        expResult.add(new ProblematicaAcademica(2,"asymmetric",2, new SolucionAProblematica(2,"rtrt")));
        expResult.add(new ProblematicaAcademica(3,"Adaptive",3,new SolucionAProblematica(3,"hghtt")));
        expResult.add(new ProblematicaAcademica(4, "Profit-focused", 4, new SolucionAProblematica(4,"rtrtrt")));
        expResult.add(new ProblematicaAcademica(5, "eco-centric", 5, new SolucionAProblematica(5,"trtr")));
        expResult.add(new ProblematicaAcademica(6, "website", 6, new SolucionAProblematica(6,"rtrt")));
        expResult.add(new ProblematicaAcademica(7, "cohesive", 7, new SolucionAProblematica(7,"fffssfsfggh")));
        ArrayList<ProblematicaAcademica> result = instance.getProblematicas();
        assertNotEquals(expResult, result);
    }
    
}
