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

import uk.ac.leeds.ccg.v3d.test.V3D_Test;
import uk.ac.leeds.ccg.v3d.geometry.envelope.V3D_Envelope;
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
 * V3D_TriangleTest
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_TriangleTest extends V3D_Test {

    private static final long serialVersionUID = 1L;

    public V3D_TriangleTest() throws Exception {
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
     * Test of getEnvelope method, of class V3D_Triangle.
     */
    @Test
    public void testGetEnvelope3D() {
        System.out.println("getEnvelope3D");
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P1P0P0, P1P1P0);
        V3D_Envelope expResult = new V3D_Envelope(e, P0P0P0, P1P0P0, P1P1P0);
        V3D_Envelope result = instance.getEnvelope();
        assertEquals(expResult, result);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Triangle.
     */
    @Test
    public void testIsIntersectedBy() {
        System.out.println("isIntersectedBy");
        V3D_Point pt = P0P0P0;
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P1P0P0, P1P1P0);
        boolean expResult = true;
        boolean result = instance.isIntersectedBy(pt);
        assertEquals(expResult, result);
        // Test 2
        pt = N1N1P0;
        expResult = false;
        result = instance.isIntersectedBy(pt);
        assertEquals(expResult, result);
    }

    /**
     * Test of getIntersection method, of class V3D_Triangle.
     */
    @Test
    public void testGetIntersection() {
        System.out.println("getIntersection");
        V3D_Line li = new V3D_Line(P0P0P0, P0P0P1);
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P0P0P1, P0P1P0);
        V3D_Geometry expResult = li;
        V3D_Geometry result = instance.getIntersection(li);
        assertEquals(expResult, result);
        // Test 2
        li = new V3D_Line(P0P0P0, P0P1P0);
        instance = new V3D_Triangle(P0P0P0, P0P0P1, P0P1P0);
        expResult = li;
        result = instance.getIntersection(li);
        assertEquals(expResult, result);
        // Test 3
        li = new V3D_Line(P0P0P0, P1P0P0);
        instance = new V3D_Triangle(P0P0P0, P1P0P0, P1P1P0);
        expResult = li;
        result = instance.getIntersection(li);
        assertEquals(expResult, result);
        // Test 4
        li = new V3D_Line(P0P0P0, P1P1P1);
        instance = new V3D_Triangle(P0P0P0, P1P0P0, P1P1P0);
        expResult = P0P0P0;
        result = instance.getIntersection(li);
        assertEquals(expResult, result);
        // Test 5
        li = new V3D_Line(P0P0P0, P1P1P1);
        instance = new V3D_Triangle(P0P0P0, P1P0P0, P1P1P0);
        expResult = P0P0P0;
        result = instance.getIntersection(li);
        assertEquals(expResult, result);

    }

}
