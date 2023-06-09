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
    
    @Test
    public void testSetProgramaEducativoRegister() throws Exception {
        System.out.println("setProgramasEducativos");
        ProgramaEducativoDAO instance = new ProgramaEducativoDAO();
        ProgramaEducativo programaEducativo  = new  ProgramaEducativo("55555","Prueba Ulises");
        boolean expResult = true;
        boolean result = instance.setProgramaEducativoRegister(programaEducativo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }    
    
    @Test
    public void testUpdateProgramaEducativo() throws Exception {
        System.out.println("updateProgramaEducativo");
        ProgramaEducativoDAO instance = new ProgramaEducativoDAO();
        ProgramaEducativo programaEducativo  = new  ProgramaEducativo("55555","Prueba UpdateProgramaEducativo");
        boolean expResult = true;
        boolean result = instance.updateProgramaEducativo(programaEducativo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }     
    
    @Test
    public void testValidateToEliminarProgramaEducativo() throws Exception {
        System.out.println("validateToEliminarProgramaEducativo");
        ProgramaEducativoDAO instance = new ProgramaEducativoDAO();
        ProgramaEducativo programaEducativo  = new  ProgramaEducativo("55555","Prueba UpdateProgramaEducativo");
        int expResult = 0;
        int result = instance.validateToEliminarProgramaEducativo(programaEducativo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }   
    
    @Test
    public void testValidateToEliminarProgramaEducativo1() throws Exception {
        System.out.println("validateToEliminarProgramaEducativo1");
        ProgramaEducativoDAO instance = new ProgramaEducativoDAO();
        ProgramaEducativo programaEducativo  = new  ProgramaEducativo("55555","Prueba UpdateProgramaEducativo");
        int expResult = 1;
        int result = instance.validateToEliminarProgramaEducativo(programaEducativo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }    
    
    @Test
    public void testValidateToBorrarProgramaEducativo() throws Exception {
        System.out.println("validateToBorrarProgramaEducativo");
        ProgramaEducativoDAO instance = new ProgramaEducativoDAO();
        ProgramaEducativo programaEducativo  = new  ProgramaEducativo("55555","Prueba UpdateProgramaEducativo");
        int expResult = 2;
        int result = instance.validateToBorrarProgramaEducativo(programaEducativo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    } 

    @Test
    public void testDeleteProgramaEducativo() throws Exception {
        System.out.println("deleteProgramaEducativo");
        ProgramaEducativoDAO instance = new ProgramaEducativoDAO();
        ProgramaEducativo programaEducativo  = new  ProgramaEducativo("55555","Prueba UpdateProgramaEducativo");
        boolean expResult = true;
        boolean result = instance.deleteProgramaEducativo(programaEducativo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }    
    
    @Test
    public void testDeleteProgramaEducativo1() throws Exception {
        System.out.println("testDeleteProgramaEducativo1");
        ProgramaEducativoDAO instance = new ProgramaEducativoDAO();
        ProgramaEducativo programaEducativo  = new  ProgramaEducativo("55555","Prueba UpdateProgramaEducativo");
        boolean expResult = false;
        boolean result = instance.deleteProgramaEducativo(programaEducativo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }       
    
    @Test
    public void testEraseProgramaEducativo() throws Exception {
        System.out.println("eraseProgramaEducativo");
        ProgramaEducativoDAO instance = new ProgramaEducativoDAO();
        ProgramaEducativo programaEducativo  = new  ProgramaEducativo("55555","Prueba UpdateProgramaEducativo");
        boolean expResult = false;
        boolean result = instance.eraseProgramaEducativo(programaEducativo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }     
    
    @Test
    public void testEraseProgramaEducativo1() throws Exception {
        System.out.println("eraseProgramaEducativo1");
        ProgramaEducativoDAO instance = new ProgramaEducativoDAO();
        ProgramaEducativo programaEducativo  = new  ProgramaEducativo("55555","Prueba UpdateProgramaEducativo");
        boolean expResult = true;
        boolean result = instance.eraseProgramaEducativo(programaEducativo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }    
    
    @Test
    public void testValidateRegistrarProgramaEducativo() throws Exception {
        System.out.println("validateRegistrarProgramaEducativo");
        ProgramaEducativoDAO instance = new ProgramaEducativoDAO();
        ProgramaEducativo programaEducativo  = new  ProgramaEducativo("55555","Prueba UpdateProgramaEducativo");
        int expResult = 15;
        int result = instance.validateRegistrarProgramaEducativo();
        System.out.println(result);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }       
    
    
    
}
