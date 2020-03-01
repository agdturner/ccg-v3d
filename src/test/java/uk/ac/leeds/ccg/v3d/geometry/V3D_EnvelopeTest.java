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
        V3D_Envelope instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of envelope method, of class V3D_Envelope.
     */
    @Test
    public void testEnvelope() {
        System.out.println("envelope");
        V3D_Envelope e = null;
        V3D_Envelope instance = null;
        V3D_Envelope expResult = null;
        V3D_Envelope result = instance.envelope(e);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIntersects method, of class V3D_Envelope.
     */
    @Test
    public void testGetIntersects_V3D_Envelope() {
        System.out.println("getIntersects");
        V3D_Envelope e = null;
        V3D_Envelope instance = null;
        boolean expResult = false;
        boolean result = instance.getIntersects(e);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIntersectsFailFast method, of class V3D_Envelope.
     */
    @Test
    public void testGetIntersectsFailFast() {
        System.out.println("getIntersectsFailFast");
        V3D_LineSegment l = null;
        V3D_Envelope instance = null;
        int expResult = 0;
        int result = instance.getIntersectsFailFast(l);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIntersects method, of class V3D_Envelope.
     */
    @Test
    public void testGetIntersects_3args_1() {
        System.out.println("getIntersects");
        V3D_LineSegment l = null;
        BigDecimal t = null;
        int scale = 0;
        V3D_Envelope instance = null;
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
        V3D_Point p = null;
        V3D_Envelope instance = null;
        boolean expResult = false;
        boolean result = instance.getIntersects(p);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIntersects method, of class V3D_Envelope.
     */
    @Test
    public void testGetIntersects_3args_2() {
        System.out.println("getIntersects");
        BigDecimal x = null;
        BigDecimal y = null;
        BigDecimal z = null;
        V3D_Envelope instance = null;
        boolean expResult = false;
        boolean result = instance.getIntersects(x, y, z);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEnvelope3D method, of class V3D_Envelope.
     */
    @Test
    public void testGetEnvelope3D() {
        System.out.println("getEnvelope3D");
        V3D_Envelope instance = null;
        V3D_Envelope expResult = null;
        V3D_Envelope result = instance.getEnvelope3D();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class V3D_Envelope.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object o = null;
        V3D_Envelope instance = null;
        boolean expResult = false;
        boolean result = instance.equals(o);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class V3D_Envelope.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        V3D_Envelope instance = null;
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
