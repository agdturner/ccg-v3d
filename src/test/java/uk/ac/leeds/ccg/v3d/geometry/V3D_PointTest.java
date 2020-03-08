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
import org.hamcrest.Matchers;
import static org.hamcrest.MatcherAssert.assertThat;
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
 * V3D_PointTest
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_PointTest extends V3D_Test {

    public V3D_PointTest() throws Exception {
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
     * Test of toString method, of class V3D_Point.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        BigDecimal x = P0;
        BigDecimal y = P0;
        BigDecimal z = P0;
        V3D_Point instance = new V3D_Point(e, x, y, z);
        String expResult = "V3D_Point(x=0, y=0, z=0)";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class V3D_Point.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        V3D_Point instance = P0P0P0;
       BigDecimal x = new BigDecimal("0.000");
       BigDecimal y = new BigDecimal("0.000");
       BigDecimal z = new BigDecimal("0.000");
        Object o = new V3D_Point(e, x, y, z);
        boolean expResult = true;
        boolean result = instance.equals(o);
        assertEquals(expResult, result);
        // Test 2
        x = P1;
        y = P10;
        z = P0;
        instance = new V3D_Point(e, x, y, z);
        x = new BigDecimal("1.000");
        y = new BigDecimal("10.000");
        z = new BigDecimal("0.000");
        o = new V3D_Point(e, x, y, z);
        expResult = true;
        result = instance.equals(o);
        assertEquals(expResult, result);
        // Test 3
        x = BigDecimal.ONE;
        y = BigDecimal.TEN;
        z = BigDecimal.ZERO;
        instance = new V3D_Point(e, x, y, z);
        x = new BigDecimal("0.000");
        y = new BigDecimal("1.000");
        z = new BigDecimal("10.000");
        o = new V3D_Point(e, x, y, z);
        expResult = false;
        result = instance.equals(o);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistance method, of class V3D_Point.
     */
    @Test
    public void testGetDistance() {
        System.out.println("getDistance");
        int scale = 0;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point instance = P0P0P0;
        BigDecimal expResult = BigDecimal.ZERO;
        BigDecimal result = instance.getDistance(P0P0P0, scale, rm);
        assertEquals(expResult, result);
        // Test 2
        scale = 1;
        rm = RoundingMode.HALF_UP;
        instance = new V3D_Point(e, P3, P4, P0);
        expResult = P5;
        result = instance.getDistance(P0P0P0, scale, rm);
        //assertEquals(expResult, result);
        assertThat(expResult, Matchers.comparesEqualTo(result));
    }

    /**
     * Test of getEnvelope3D method, of class V3D_Point.
     */
    @Test
    public void testGetEnvelope3D() {
        System.out.println("getEnvelope3D");        
        V3D_Envelope expResult = new V3D_Envelope(P0P0P0, P1P1P1);
        V3D_Envelope result = P0P0P0.getEnvelope3D();
        result.envelope(P1P1P1.getEnvelope3D());
        assertEquals(expResult, result);
    }

    /**
     * Test of hashCode method, of class V3D_Point.
     */
    @Test
    public void testHashCode() {
        // No test.
    }

    /**
     * Test of apply method, of class V3D_Point.
     */
    @Test
    public void testApply() {
        System.out.println("apply");
        V3D_Vector v = new V3D_Vector(e, P1, P1, P1);
        V3D_Point instance = P0P0P0;
        V3D_Point expResult = P1P1P1;
        V3D_Point result = instance.apply(v);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistance method, of class V3D_Point.
     */
    @Test
    public void testGetDistance_3args_1() {
        System.out.println("getDistance");
        V3D_Point p = P0P0P0;
        int scale = 0;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point instance = P1P0P0;
        BigDecimal expResult = P1;
        BigDecimal result = instance.getDistance(p, scale, rm);
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_Point(e, P3, P4, P0);
        expResult = P5;
        result = instance.getDistance(p, scale, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistance method, of class V3D_Point.
     */
    @Test
    public void testGetDistance_3args_2() {
        System.out.println("getDistance");
        V3D_Line l = new V3D_Line(P0P0P0,P0P0P1);
        int scale = 0;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point instance = P0P1P0;
        BigDecimal expResult = P1;
        BigDecimal result = instance.getDistance(l, scale, rm);
        assertEquals(expResult, result);
        // Test 1
        l = new V3D_Line(P0P0P0, P1N1P0);
        instance = new V3D_Point(e, P3, P4, P0);
        expResult = P5;
        result = instance.getDistance(l, scale, rm);
        assertEquals(expResult, result);
    }

}
