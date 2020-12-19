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

import ch.obermuhlner.math.big.BigRational;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.generic.io.Generic_Defaults;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;
import uk.ac.leeds.ccg.v3d.test.V3D_Test;

/**
 * V3D_RectangleTest
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_RectangleTest extends V3D_Test {

    private static final long serialVersionUID = 1L;

    public V3D_RectangleTest() throws Exception {
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
     * Test of getEnvelope method, of class V3D_Rectangle.
     */
    @Test
    public void testGetEnvelope() {
        System.out.println("getEnvelope");
        /*
         * p ----------- q
         * |             |
         * |             |
         * |             |
         * s ----------- r
         */
        V3D_Rectangle instance;
        V3D_Envelope expResult;
        V3D_Envelope result;
        instance = new V3D_Rectangle(N1P1P0, P1P1P0, P1N1P0, N1N1P0);
        expResult = new V3D_Envelope(N1N1P0, P1P1P0);
        result = instance.getEnvelope();
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_Rectangle(N1P1P1, P1P1P0, P1N1P0, N1N1P1);
        expResult = new V3D_Envelope(N1N1P0, P1P1P1);
        result = instance.getEnvelope();
        assertEquals(expResult, result);
        // Test 3
        instance = new V3D_Rectangle(N1P1P1, P1P1N1, P1N1N1, N1N1P1);
        expResult = new V3D_Envelope(N1N1N1, P1P1P1);
        result = instance.getEnvelope();
        assertEquals(expResult, result);
        // Test 4
        instance = new V3D_Rectangle(N1N1N1, P1N1N1, P1P1N1, N1P1N1);
        expResult = new V3D_Envelope(N1N1N1, P1P1N1);
        result = instance.getEnvelope();
        assertEquals(expResult, result);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Rectangle.
     */
    @Test
    public void testIsIntersectedBy() {
        System.out.println("isIntersectedBy");
        V3D_Point pt = P0P0P0;
        V3D_Rectangle instance = new V3D_Rectangle(N1P1P0, P1P1P0, P1N1P0, N1N1P0);
        assertTrue(instance.isIntersectedBy(pt));
        // Test 2
        instance = new V3D_Rectangle(N1P0P0, P0P1P0, P1P0P0, P0N1P0);
        assertTrue(instance.isIntersectedBy(pt));
        // Test 3
        BigRational half = BigRational.ONE.divide(2);
        pt = new V3D_Point(half, half, P0);
        assertTrue(instance.isIntersectedBy(pt));
        // Test 3
        pt = new V3D_Point(half.add(BigRational.valueOf("0.00000000001")), half, P0);
        assertFalse(instance.isIntersectedBy(pt));
    }

    /**
     * Test of getIntersection method, of class V3D_Rectangle.
     */
    @Test
    public void testGetIntersection() {
        System.out.println("getIntersection");
        V3D_Line l = new V3D_Line(P0P0N1, P0P0P1);
        V3D_Rectangle instance;
        V3D_Geometry expResult;
        V3D_Geometry result;
        instance = new V3D_Rectangle(N1P1P0, P1P1P0, P1N1P0, N1N1P0);
        expResult = P0P0P0;
        result = instance.getIntersection(l);
        assertEquals(expResult, result);
    }

}
