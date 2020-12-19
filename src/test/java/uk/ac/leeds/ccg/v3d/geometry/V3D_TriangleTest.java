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
import java.math.BigDecimal;
import java.math.RoundingMode;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import uk.ac.leeds.ccg.v3d.test.V3D_Test;
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
        V3D_Envelope expResult = new V3D_Envelope(P0P0P0, P1P0P0, P1P1P0);
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
        assertTrue(instance.isIntersectedBy(pt));
        // Test 2
        pt = N1N1P0;
        assertFalse(instance.isIntersectedBy(pt));
        // Test 3
        pt = new V3D_Point(BigRational.ONE.divide(2), P0, P0);
        assertTrue(instance.isIntersectedBy(pt));
    }

    /**
     * Test of getEnvelope method, of class V3D_Triangle.
     */
    @Test
    public void testGetEnvelope() {
        System.out.println("getEnvelope");
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P0P1P0, P1P0P0);
        V3D_Envelope expResult = new V3D_Envelope(P0P0P0, P0P1P0, P1P0P0);
        V3D_Envelope result = instance.getEnvelope();
        assertEquals(expResult, result);
    }

    /**
     * Test of getArea method, of class V3D_Triangle.
     */
    @Test
    public void testGetArea() {
        System.out.println("getArea");
        int scale = 1;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P0P1P0, P1P0P0);
        BigDecimal expResult = new BigDecimal("0.5");
        BigDecimal result = instance.getArea(scale, rm);
        assertThat(expResult, Matchers.comparesEqualTo(result));
    }

}
