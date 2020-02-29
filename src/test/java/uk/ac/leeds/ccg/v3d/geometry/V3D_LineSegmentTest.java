/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.ccg.v3d.geometry;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.generic.io.Generic_Defaults;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 *
 * @author geoagdt
 */
public class V3D_LineSegmentTest {
    
    public static V3D_Environment e;
    
    public V3D_LineSegmentTest() {
    }
    
    @BeforeAll
    public static void setUpClass() throws Exception {
        e = new V3D_Environment(new Generic_Environment(new Generic_Defaults()));
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    /**
     * Test of toString method, of class V3D_LineSegment.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        V3D_Point start = new V3D_Point(e, BigDecimal.valueOf(0),
                        BigDecimal.valueOf(0), BigDecimal.valueOf(0));
        V3D_Point end = new V3D_Point(e, BigDecimal.valueOf(1),
                        BigDecimal.valueOf(1), BigDecimal.valueOf(1));
        V3D_LineSegment instance = new V3D_LineSegment(start, end);
        String expResult = "Vector_LineSegment3D()";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLength method, of class V3D_LineSegment.
     */
    @Test
    public void testGetLength() {
        System.out.println("getLength");
        int scale = 0;
        RoundingMode rm = null;
        V3D_LineSegment instance = null;
        BigDecimal expResult = null;
        BigDecimal result = instance.getLength(scale, rm);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEnvelope3D method, of class V3D_LineSegment.
     */
    @Test
    public void testGetEnvelope3D() {
        System.out.println("getEnvelope3D");
        V3D_LineSegment instance = null;
        V3D_Envelope expResult = null;
        V3D_Envelope result = instance.getEnvelope3D();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDotProduct method, of class V3D_LineSegment.
     */
    @Test
    public void testGetDotProduct() {
        System.out.println("getDotProduct");
        V3D_LineSegment l = null;
        V3D_LineSegment instance = null;
        BigDecimal expResult = null;
        BigDecimal result = instance.getDotProduct(l);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCrossProduct method, of class V3D_LineSegment.
     */
    @Test
    public void testGetCrossProduct() {
        System.out.println("getCrossProduct");
        V3D_LineSegment l = null;
        V3D_LineSegment instance = null;
        V3D_LineSegment expResult = null;
        V3D_LineSegment result = instance.getCrossProduct(l);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIntersects method, of class V3D_LineSegment.
     */
    @Test
    public void testGetIntersects_8args() {
        System.out.println("getIntersects");
        BigDecimal xMin = null;
        BigDecimal yMin = null;
        BigDecimal xMax = null;
        BigDecimal yMax = null;
        BigDecimal zMin = null;
        BigDecimal zMax = null;
        BigDecimal t = null;
        int scale = 0;
        V3D_LineSegment instance = null;
        boolean expResult = false;
        boolean result = instance.getIntersects(xMin, yMin, xMax, yMax, zMin, zMax, t, scale);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIntersects method, of class V3D_LineSegment.
     */
    @Test
    public void testGetIntersects_3args() {
        System.out.println("getIntersects");
        V3D_Point p = null;
        BigDecimal t = null;
        int scale = 0;
        V3D_LineSegment instance = null;
        boolean expResult = false;
        boolean result = instance.getIntersects(p, t, scale);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
