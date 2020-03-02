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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * V3D_EnvelopeTest
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_EnvelopeTest {

    public static V3D_Environment e;

    public V3D_EnvelopeTest() {
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
        BigDecimal x = BigDecimal.ZERO;
        BigDecimal y = BigDecimal.ZERO;
        BigDecimal z = BigDecimal.ZERO;
        V3D_Envelope instance = new V3D_Envelope(e, x, y, z);
        String expResult = "V3D_Envelope(xMin=0, xMax=0,yMin=0, yMax=0,zMin=0, zMax=0)";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of envelope method, of class V3D_Envelope.
     */
    @Test
    public void testEnvelope() {
        System.out.println("envelope");
        BigDecimal x = BigDecimal.ZERO;
        BigDecimal y = BigDecimal.ZERO;
        BigDecimal z = BigDecimal.ZERO;
        V3D_Point p1 = new V3D_Point(e, x, y, z);
        V3D_Envelope e1 = new V3D_Envelope(e, x, y, z);
         x = BigDecimal.ONE;
         y = BigDecimal.ONE;
         z = BigDecimal.ONE;
        V3D_Point p2 = new V3D_Point(e, x, y, z);
        V3D_Envelope instance = new V3D_Envelope(e, x, y, z);
        V3D_Envelope expResult = new V3D_Envelope(p1, p2);
        V3D_Envelope result = instance.envelope(e1);
        assertEquals(expResult, result);
    }

    /**
     * Test of getIntersects method, of class V3D_Envelope.
     */
    @Test
    public void testGetIntersects_V3D_Envelope() {
        System.out.println("getIntersects");
        BigDecimal x = BigDecimal.ZERO;
        BigDecimal y = BigDecimal.ZERO;
        BigDecimal z = BigDecimal.ZERO;
        V3D_Point p1 = new V3D_Point(e, x, y, z);
         x = BigDecimal.ONE;
         y = BigDecimal.ONE;
         z = BigDecimal.ONE;
        V3D_Point p2 = new V3D_Point(e, x, y, z);
        V3D_Envelope instance = new V3D_Envelope(e, x, y, z);
        V3D_Envelope en = new V3D_Envelope(p1, p2);
        boolean expResult = true;
        boolean result = instance.getIntersects(en);
        assertEquals(expResult, result);
    }

    /**
     * Test of getIntersectsFailFast method, of class V3D_Envelope.
     */
    @Test
    public void testGetIntersectsFailFast() {
        System.out.println("getIntersectsFailFast");
        BigDecimal x = BigDecimal.ZERO;
        BigDecimal y = BigDecimal.ZERO;
        BigDecimal z = BigDecimal.ZERO;
        V3D_Point p1 = new V3D_Point(e, x, y, z);
         x = BigDecimal.ONE;
         y = BigDecimal.ONE;
         z = BigDecimal.ONE;
        V3D_Point p2 = new V3D_Point(e, x, y, z);
        V3D_Envelope instance = new V3D_Envelope(e, x, y, z);
        V3D_Envelope en = new V3D_Envelope(p1, p2);
        V3D_LineSegment l = new V3D_LineSegment(p1, p2);
        int expResult = 1;
        int result = instance.getIntersectsFailFast(l);
        assertEquals(expResult, result);
    }

    /**
     * Test of getIntersects method, of class V3D_Envelope.
     */
    @Test
    public void testGetIntersects_3args_1() {
        System.out.println("getIntersects");
        BigDecimal x = BigDecimal.ZERO;
        BigDecimal y = BigDecimal.ZERO;
        BigDecimal z = BigDecimal.ZERO;
        V3D_Point p1 = new V3D_Point(e, x, y, z);
         x = BigDecimal.ONE;
         y = BigDecimal.ONE;
         z = BigDecimal.ONE;
        V3D_Point p2 = new V3D_Point(e, x, y, z);
        V3D_Envelope instance = new V3D_Envelope(e, x, y, z);
        V3D_LineSegment l = new V3D_LineSegment(p1, p2);
        BigDecimal t = null;
        int scale = 0;
        boolean expResult = false;
        boolean result = instance.getIntersects(l, t, scale);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIntersects method, of class V3D_Envelope.
     */
    @Test
    public void testGetIntersects_V3D_Point() {
        System.out.println("getIntersects");
        BigDecimal x = BigDecimal.ZERO;
        BigDecimal y = BigDecimal.ZERO;
        BigDecimal z = BigDecimal.ZERO;
        V3D_Point p1 = new V3D_Point(e, x, y, z);
         x = BigDecimal.ONE;
         y = BigDecimal.ONE;
         z = BigDecimal.ONE;
        V3D_Point p2 = new V3D_Point(e, x, y, z);
        V3D_Envelope instance = new V3D_Envelope(p1, p2);
        boolean expResult = true;
        boolean result = instance.getIntersects(p1);
        assertEquals(expResult, result);
    }

    /**
     * Test of getIntersects method, of class V3D_Envelope.
     */
    @Test
    public void testGetIntersects_3args_2() {
        BigDecimal x = BigDecimal.ZERO;
        BigDecimal y = BigDecimal.ZERO;
        BigDecimal z = BigDecimal.ZERO;
        V3D_Point p1 = new V3D_Point(e, x, y, z);
         x = BigDecimal.ONE;
         y = BigDecimal.ONE;
         z = BigDecimal.ONE;
        V3D_Point p2 = new V3D_Point(e, x, y, z);
        V3D_Envelope instance = new V3D_Envelope(p1, p2);
        boolean expResult = true;
        boolean result = instance.getIntersects(x, y, z);
        assertEquals(expResult, result);
    }

    /**
     * Test of getEnvelope3D method, of class V3D_Envelope.
     */
    @Test
    public void testGetEnvelope3D() {
        System.out.println("getEnvelope3D");
        BigDecimal x = BigDecimal.ZERO;
        BigDecimal y = BigDecimal.ZERO;
        BigDecimal z = BigDecimal.ZERO;
        V3D_Point p1 = new V3D_Point(e, x, y, z);
         x = BigDecimal.ONE;
         y = BigDecimal.ONE;
         z = BigDecimal.ONE;
        V3D_Point p2 = new V3D_Point(e, x, y, z);
        V3D_Envelope instance = new V3D_Envelope(p1, p2);
        V3D_Envelope expResult = new V3D_Envelope(p1, p2);
        V3D_Envelope result = instance.getEnvelope3D();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class V3D_Envelope.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        BigDecimal x = BigDecimal.ZERO;
        BigDecimal y = BigDecimal.ZERO;
        BigDecimal z = BigDecimal.ZERO;
        V3D_Point p1 = new V3D_Point(e, x, y, z);
         x = BigDecimal.ONE;
         y = BigDecimal.ONE;
         z = BigDecimal.ONE;
        V3D_Point p2 = new V3D_Point(e, x, y, z);
        V3D_Envelope instance = new V3D_Envelope(p1, p2);
        Object o = new V3D_Envelope(p1, p2);
        boolean expResult = true;
        boolean result = instance.equals(o);
        assertEquals(expResult, result);
    }

}
