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
        BigDecimal x = ZERO;
        BigDecimal y = ZERO;
        BigDecimal z = ZERO;
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
        BigDecimal x = ZERO;
        BigDecimal y = ZERO;
        BigDecimal z = ZERO;
        V3D_Point instance = new V3D_Point(e, x, y, z);
        x = new BigDecimal("0.000");
        y = new BigDecimal("0.000");
        z = new BigDecimal("0.000");
        Object o = new V3D_Point(e, x, y, z);
        boolean expResult = true;
        boolean result = instance.equals(o);
        assertEquals(expResult, result);
        // Test 2
        x = ONE;
        y = TEN;
        z = ZERO;
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
     * Test of getIntersects method, of class V3D_Point.
     */
    @Test
    public void testGetIntersects() {
        System.out.println("getIntersects");
        BigDecimal x0 = ZERO;
        BigDecimal y0 = ZERO;
        BigDecimal z0 = ZERO;
        V3D_Point p = new V3D_Point(e, x0, y0, z0);
        BigDecimal x1 = ONE;
        BigDecimal y1 = ONE;
        BigDecimal z1 = ONE;
        V3D_Point q = new V3D_Point(e, x1, y1, z1);        
        V3D_LineSegment l = new V3D_LineSegment(p, q);
        int scale = 0;
        BigDecimal t = new BigDecimal("0.00000000000001");
        V3D_Point instance = new V3D_Point(e, x1, y1, z1);
        boolean expResult = false;
        boolean result = instance.getIntersects(l, t, scale);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistance method, of class V3D_Point.
     */
    @Test
    public void testGetDistance() {
        System.out.println("getDistance");
        BigDecimal x0 = ZERO;
        BigDecimal y0 = ZERO;
        BigDecimal z0 = ZERO;
        V3D_Point p = new V3D_Point(e, x0, y0, z0);
        int scale = 0;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point instance = new V3D_Point(e, x0, y0, z0);
        BigDecimal expResult = BigDecimal.ZERO;
        BigDecimal result = instance.getDistance(p, scale, rm);
        assertEquals(expResult, result);
        // Test 2
        scale = 1;
        rm = RoundingMode.HALF_UP;
        BigDecimal x3 = THREE;
        BigDecimal y4 = FOUR;
        instance = new V3D_Point(e, x3, y4, z0);
        expResult = FIVE;
        result = instance.getDistance(p, scale, rm);
        //assertEquals(expResult, result);
        assertThat(expResult, Matchers.comparesEqualTo(result));
    }

    /**
     * Test of getEnvelope3D method, of class V3D_Point.
     */
    @Test
    public void testGetEnvelope3D() {
        System.out.println("getEnvelope3D");
        BigDecimal x = ZERO;
        BigDecimal y = ZERO;
        BigDecimal z = ZERO;
        V3D_Point p = new V3D_Point(e, x, y, z);
        V3D_Envelope expResult = new V3D_Envelope(p, p);
        V3D_Envelope result = p.getEnvelope3D();
        assertEquals(expResult, result);
    }

}
