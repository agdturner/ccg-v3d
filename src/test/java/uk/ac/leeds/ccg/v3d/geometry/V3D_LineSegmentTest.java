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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.generic.io.Generic_Defaults;
import uk.ac.leeds.ccg.math.Math_BigDecimal;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * V3D_LineSegmentTest
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_LineSegmentTest extends V3D_Test {

    private static final long serialVersionUID = 1L;

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
        String expResult = "V3D_LineSegment(p=V3D_Point(x=0, y=0, z=0), "
                + "q=V3D_Point(x=1, y=1, z=1), v=V3D_Vector(dx=1, dy=1, dz=1))";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLength method, of class V3D_LineSegment.
     */
    @Test
    public void testGetLength() {
        System.out.println("getLength");
        int scale = 2;
        RoundingMode rm = RoundingMode.HALF_EVEN;
        V3D_LineSegment instance = new V3D_LineSegment(P0P0P0, P1P1P0);
        BigDecimal expResult = Math_BigDecimal.sqrt(P2.toBigDecimal(), scale, rm);
        BigDecimal result = instance.getLength(scale, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of getEnvelope3D method, of class V3D_LineSegment.
     */
    @Test
    public void testGetEnvelope3D() {
        System.out.println("getEnvelope3D");
        V3D_LineSegment instance = new V3D_LineSegment(P0P0P0, P1P1P0);
        V3D_Envelope expResult = new V3D_Envelope(e, P0P0P0, P1P1P0);
        V3D_Envelope result = instance.getEnvelope3D();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class V3D_LineSegment.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object o = new V3D_LineSegment(P0P0P0, P1P1P0);
        V3D_LineSegment instance = new V3D_LineSegment(P0P0P0, P1P1P0);
        boolean expResult = true;
        boolean result = instance.equals(o);
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_LineSegment(P1P1P0, P0P0P0);
        expResult = true;
        result = instance.equals(o);
        assertEquals(expResult, result);

    }

    /**
     * Test of hashCode method, of class V3D_LineSegment.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        assertTrue(true); // Non test
    }

    /**
     * Test of getUnitVector method, of class V3D_LineSegment.
     */
    @Test
    public void testGetUnitVector() {
        System.out.println("getUnitVector");
        int scale = 1;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_LineSegment instance = new V3D_LineSegment(P0P0P0, P1P1P0);
        BigRational magnitude = BigRational.valueOf(Math_BigDecimal.sqrt(
                P2.toBigDecimal(), scale + 2, rm));
        V3D_Vector expResult = new V3D_Vector(e, instance.v.dx.divide(magnitude),
                instance.v.dy.divide(magnitude),
                instance.v.dz.divide(magnitude));
        V3D_Vector result = instance.getUnitVector(scale, rm);
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_LineSegment(P0P0P0, P1P1P1);
        magnitude = BigRational.valueOf(Math_BigDecimal.sqrt(P3.toBigDecimal(), scale + 2, rm));
        expResult = new V3D_Vector(e, instance.v.dx.divide(magnitude),
                instance.v.dy.divide(magnitude), 
                instance.v.dz.divide(magnitude));
        result = instance.getUnitVector(scale, rm);
        assertEquals(expResult, result);

    }

    /**
     * Test of initUnitVector method, of class V3D_LineSegment.
     */
    @Test
    public void testInitUnitVector() {
        System.out.println("initUnitVector");
        int scale = 1;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_LineSegment instance = new V3D_LineSegment(P0P0P0, P1P1P0);
        BigRational magnitude = BigRational.valueOf(Math_BigDecimal.sqrt(
                P2.toBigDecimal(), scale + 2, rm));
        V3D_Vector expResult = new V3D_Vector(e, 
                instance.v.dx.divide(magnitude),
                instance.v.dy.divide(magnitude),
                instance.v.dz.divide(magnitude));
        V3D_Vector result = instance.initUnitVector(scale, rm);
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_LineSegment(P0P0P0, P1P1P1);
        magnitude = BigRational.valueOf(Math_BigDecimal.sqrt(P3.toBigDecimal(), 
                scale + 2, rm));
        expResult = new V3D_Vector(e, 
                instance.v.dx.divide(magnitude),
                instance.v.dy.divide(magnitude),
                instance.v.dz.divide(magnitude));
        result = instance.initUnitVector(scale, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_LineSegment.
     */
    @Test
    public void testIsIntersectedBy_V3D_Envelope_int() {
        System.out.println("isIntersectedBy");
        V3D_Envelope en = new V3D_Envelope(e, P0P0P0, P1P1P1);
        int scale = 0;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_LineSegment instance = new V3D_LineSegment(P0P0P0, N1N1N1);
        boolean expResult = true;
        boolean result = instance.isIntersectedBy(en, scale, rm);
        assertEquals(expResult, result);
        // Test 2
        en = new V3D_Envelope(e, N1P0P0, P1P1P1);
        instance = new V3D_LineSegment(P0P0P0, N1N1N1);
        expResult = true;
        result = instance.isIntersectedBy(en, scale, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_LineSegment.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point_int() {
        System.out.println("isIntersectedBy");
        V3D_Point p = P0P0P0;
        V3D_LineSegment instance = new V3D_LineSegment(N1N1N1, P1P1P1);
        boolean expResult = true;
        boolean result = instance.isIntersectedBy(p);
        assertEquals(expResult, result);
        // Test2
        p = P1P1P1;
        instance = new V3D_LineSegment(N1N1N1, P1P1P1);
        expResult = true;
        result = instance.isIntersectedBy(p);
        assertEquals(expResult, result);
    }

}
