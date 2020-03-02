/*
 * Copyright 2020 Andy Turner, University of Leeds.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
 * V3D_LineSegmentTest
 * 
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_LineSegmentTest extends V3D_Test {
    
    public V3D_LineSegmentTest() throws Exception {
        super(new V3D_Environment(new Generic_Environment(
                new Generic_Defaults())));
    }
    
    @BeforeAll
    public static void setUpClass() {
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
        V3D_Point start = new V3D_Point(e, P0, P0, P0);
        V3D_Point end = new V3D_Point(e, P1, P1, P1);
        V3D_LineSegment instance = new V3D_LineSegment(start, end);
        String expResult = "V3D_LineSegment(start=V3D_Point(x=0, y=0, z=0), "
                + "end=V3D_Point(x=1, y=1, z=1))";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

//    /**
//     * Test of getLength method, of class V3D_LineSegment.
//     */
//    @Test
//    public void testGetLength() {
//        System.out.println("getLength");
//        int scale = 0;
//        RoundingMode rm = null;
//        V3D_LineSegment instance = null;
//        BigDecimal expResult = null;
//        BigDecimal result = instance.getLength(scale, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getEnvelope3D method, of class V3D_LineSegment.
//     */
//    @Test
//    public void testGetEnvelope3D() {
//        System.out.println("getEnvelope3D");
//        V3D_LineSegment instance = null;
//        V3D_Envelope expResult = null;
//        V3D_Envelope result = instance.getEnvelope3D();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDotProduct method, of class V3D_LineSegment.
//     */
//    @Test
//    public void testGetDotProduct() {
//        System.out.println("getDotProduct");
//        V3D_LineSegment l = null;
//        V3D_LineSegment instance = null;
//        BigDecimal expResult = null;
//        BigDecimal result = instance.getDotProduct(l);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getCrossProduct method, of class V3D_LineSegment.
//     */
//    @Test
//    public void testGetCrossProduct() {
//        System.out.println("getCrossProduct");
//        V3D_LineSegment l = null;
//        V3D_LineSegment instance = null;
//        V3D_LineSegment expResult = null;
//        V3D_LineSegment result = instance.getCrossProduct(l);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getIntersects method, of class V3D_LineSegment.
//     */
//    @Test
//    public void testGetIntersects_8args() {
//        System.out.println("getIntersects");
//        BigDecimal xMin = null;
//        BigDecimal yMin = null;
//        BigDecimal xMax = null;
//        BigDecimal yMax = null;
//        BigDecimal zMin = null;
//        BigDecimal zMax = null;
//        BigDecimal t = null;
//        int scale = 0;
//        V3D_LineSegment instance = null;
//        boolean expResult = false;
//        boolean result = instance.getIntersects(xMin, yMin, xMax, yMax, zMin, zMax, t, scale);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getIntersects method, of class V3D_LineSegment.
//     */
//    @Test
//    public void testGetIntersects_3args() {
//        System.out.println("getIntersects");
//        V3D_Point p = null;
//        BigDecimal t = null;
//        int scale = 0;
//        V3D_LineSegment instance = null;
//        boolean expResult = false;
//        boolean result = instance.getIntersects(p, t, scale);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
