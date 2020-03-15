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
package uk.ac.leeds.ccg.v3d.geometry.envelope;

import ch.obermuhlner.math.big.BigRational;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.generic.io.Generic_Defaults;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Geometry;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Line;
import uk.ac.leeds.ccg.v3d.geometry.V3D_LineSegment;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Point;
import uk.ac.leeds.ccg.v3d.test.V3D_Test;

/**
 * V3D_EnvelopeTest
 * 
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_EnvelopeTest extends V3D_Test {
    
    private static final long serialVersionUID = 1L;

    public V3D_EnvelopeTest() throws Exception {
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
     * Test of toString method, of class V3D_Envelope.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        V3D_Envelope instance = new V3D_Envelope(e, P0P0P0, P0P0P0);
        String expResult = "V3D_Envelope(xMin=0, xMax=0, yMin=0, yMax=0,zMin=0,"
                + " zMax=0)";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of envelop method, of class V3D_Envelope.
     */
    @Test
    public void testEnvelope() {
        System.out.println("envelope");
        V3D_Envelope e1 = new V3D_Envelope(e, P0P0P0, P0P0P0);
        V3D_Envelope instance = new V3D_Envelope(e, P1P1P1, P1P1P1);
        V3D_Envelope expResult = new V3D_Envelope(e, P0P0P0, P1P1P1);
        V3D_Envelope result = instance.union(e1);
        assertEquals(expResult, result);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Envelope.
     */
    @Test
    public void testIsIntersectedBy_V3D_Envelope() {
        System.out.println("isIntersectedBy");
        V3D_Envelope instance = new V3D_Envelope(e, P0P0P0, P0P0P0);
        V3D_Envelope e000111 = new V3D_Envelope(e, P0P0P0, P1P1P1);
        boolean expResult = true;
        boolean result = instance.isIntersectedBy(e000111);
        assertEquals(expResult, result);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Envelope.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point() {
        System.out.println("isIntersectedBy");
        V3D_Envelope instance = new V3D_Envelope(e, P0P0P0, P1P1P1);
        boolean expResult = true;
        boolean result = instance.isIntersectedBy(P0P0P0);
        assertEquals(expResult, result);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Envelope.
     */
    @Test
    public void testIsIntersectedBy_3args_2() {
        V3D_Envelope instance = new V3D_Envelope(e, P0P0P0, P1P1P1);
        boolean expResult = true;
        boolean result = instance.isIntersectedBy(P0, P0, P0);
        assertEquals(expResult, result);
    }

    /**
     * Test of getEnvelope method, of class V3D_Envelope.
     */
    @Test
    public void testGetEnvelope3D() {
        System.out.println("getEnvelope3D");
        V3D_Point p0 = P0P0P0;
        V3D_Point p1 = P1P1P1;
        V3D_Envelope instance = new V3D_Envelope(e, p0, p1);
        V3D_Envelope expResult = new V3D_Envelope(e, p0, p1);
        V3D_Envelope result = instance.getEnvelope();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class V3D_Envelope.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        V3D_Envelope instance = new V3D_Envelope(e, P0P0P0, P1P1P1);
        Object o = new V3D_Envelope(e, P0P0P0, P1P1P1);
        boolean expResult = true;
        boolean result = instance.equals(o);
        assertEquals(expResult, result);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Envelope.
     */
    @Test
    public void testIsIntersectedBy_3args() {
        System.out.println("isIntersectedBy");
        V3D_Envelope instance = new V3D_Envelope(e, N1N1N1, P1P1P1);
        boolean expResult = true;
        boolean result = instance.isIntersectedBy(P0, P0, P0);
        assertEquals(expResult, result);
    }

    /**
     * Test of hashCode method, of class V3D_Envelope.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        assertTrue(true); // None test.
    }

    /**
     * Test of getIntersection method, of class V3D_Envelope.
     */
    @Test
    public void testGetIntersection_V3D_Envelope() {
        System.out.println("getIntersection");
        V3D_Envelope en;
        V3D_Envelope instance;
        V3D_Envelope expResult;
        V3D_Envelope result;
        // Test 1
        en = new V3D_Envelope(e, N1N1N1, P1P1P1);
        instance = new V3D_Envelope(e, P0P0P0, P1P1P1);
        expResult = new V3D_Envelope(e, P0P0P0, P1P1P1);
        result = instance.getIntersection(en);
        assertEquals(expResult, result);
        // Test 2
        en = new V3D_Envelope(e, N1N1N1, P0P0P0);
        instance = new V3D_Envelope(e, P0P0P0, P1P1P1);
        expResult = new V3D_Envelope(e, P0P0P0);
        result = instance.getIntersection(en);
        assertEquals(expResult, result);
        // Test 3
        en = new V3D_Envelope(e, N1N1N1, P0P0P0);
        instance = new V3D_Envelope(e, P0P0P0, P1P1P1);
        expResult = new V3D_Envelope(e, P0P0P0);
        result = instance.getIntersection(en);
        assertEquals(expResult, result);
        // Test 4
        en = new V3D_Envelope(e, N1N1N1, P0P0P1);
        instance = new V3D_Envelope(e, P0P0N1, P1P1P1);
        expResult = new V3D_Envelope(e, P0P0N1, P0P0P1);
        result = instance.getIntersection(en);
        assertEquals(expResult, result);
    }

    /**
     * Test of getIntersection method, of class V3D_Envelope.
     */
    @Test
    public void testGetIntersection_V3D_Line() {
        System.out.println("getIntersection");
        V3D_Line li = new V3D_Line(P0P0P0, P0P0P1);
        V3D_Envelope instance = new V3D_Envelope(e, N1N1N1, P1P1P1);
        V3D_Geometry expResult = new V3D_LineSegment(P0P0N1, P0P0P1);
        V3D_Geometry result = instance.getIntersection(li);
        assertEquals(expResult, result);
        // Test 2
        
        
    }

    /**
     * Test of union method, of class V3D_Envelope.
     */
    @Test
    public void testUnion() {
        System.out.println("union");
        V3D_Envelope e = null;
        V3D_Envelope instance = null;
        V3D_Envelope expResult = null;
        V3D_Envelope result = instance.union(e);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isContainedBy method, of class V3D_Envelope.
     */
    @Test
    public void testIsContainedBy() {
        System.out.println("isContainedBy");
        V3D_Envelope e = null;
        V3D_Envelope instance = null;
        boolean expResult = false;
        boolean result = instance.isContainedBy(e);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIntersection method, of class V3D_Envelope.
     */
    @Test
    public void testGetIntersection_V3D_LineSegment_boolean() {
        System.out.println("getIntersection");
        V3D_LineSegment l = null;
        boolean flag = false;
        V3D_Envelope instance = null;
        V3D_Geometry expResult = null;
        V3D_Geometry result = instance.getIntersection(l, flag);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEnvelope method, of class V3D_Envelope.
     */
    @Test
    public void testGetEnvelope() {
        System.out.println("getEnvelope");
        V3D_Envelope instance = null;
        V3D_Envelope expResult = null;
        V3D_Envelope result = instance.getEnvelope();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getxMin method, of class V3D_Envelope.
     */
    @Test
    public void testGetxMin() {
        System.out.println("getxMin");
        V3D_Envelope instance = null;
        BigRational expResult = null;
        BigRational result = instance.getxMin();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getxMax method, of class V3D_Envelope.
     */
    @Test
    public void testGetxMax() {
        System.out.println("getxMax");
        V3D_Envelope instance = null;
        BigRational expResult = null;
        BigRational result = instance.getxMax();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getyMin method, of class V3D_Envelope.
     */
    @Test
    public void testGetyMin() {
        System.out.println("getyMin");
        V3D_Envelope instance = null;
        BigRational expResult = null;
        BigRational result = instance.getyMin();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getyMax method, of class V3D_Envelope.
     */
    @Test
    public void testGetyMax() {
        System.out.println("getyMax");
        V3D_Envelope instance = null;
        BigRational expResult = null;
        BigRational result = instance.getyMax();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getzMin method, of class V3D_Envelope.
     */
    @Test
    public void testGetzMin() {
        System.out.println("getzMin");
        V3D_Envelope instance = null;
        BigRational expResult = null;
        BigRational result = instance.getzMin();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getzMax method, of class V3D_Envelope.
     */
    @Test
    public void testGetzMax() {
        System.out.println("getzMax");
        V3D_Envelope instance = null;
        BigRational expResult = null;
        BigRational result = instance.getzMax();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
