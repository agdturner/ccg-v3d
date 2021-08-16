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

import uk.ac.leeds.ccg.v3d.V3D_Test;
import ch.obermuhlner.math.big.BigRational;
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
import uk.ac.leeds.ccg.math.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * V3D_PointTest
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_PointTest extends V3D_Test {

    private static final long serialVersionUID = 1L;

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
        BigRational x = e.P0;
        BigRational y = e.P0;
        BigRational z = e.P0;
        V3D_Point instance = new V3D_Point(x, y, z);
        String expResult = "V3D_Point(x=0, y=0, z=0)";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class V3D_Point.
     */
    @Test
    public void testEquals_Object() {
        System.out.println("equals");
        V3D_Point instance = P0P0P0;
        BigRational x = BigRational.valueOf(new BigDecimal("0.000"));
        BigRational y = BigRational.valueOf(new BigDecimal("0.000"));
        BigRational z = BigRational.valueOf(new BigDecimal("0.000"));
        Object o = new V3D_Point(x, y, z);
        boolean expResult = true;
        boolean result = instance.equals(o);
        assertEquals(expResult, result);
        // Test 2
        x = P1;
        y = P10;
        z = P0;
        instance = new V3D_Point(x, y, z);
        x = BigRational.valueOf(new BigDecimal("1.000"));
        y = BigRational.valueOf(new BigDecimal("10.000"));
        z = BigRational.valueOf(new BigDecimal("0.000"));
        o = new V3D_Point(x, y, z);
        expResult = true;
        result = instance.equals(o);
        assertEquals(expResult, result);
        // Test 3
        x = BigRational.ONE;
        y = BigRational.TEN;
        z = BigRational.ZERO;
        instance = new V3D_Point(x, y, z);
        x = BigRational.valueOf(new BigDecimal("0.000"));
        y = BigRational.valueOf(new BigDecimal("1.000"));
        z = BigRational.valueOf(new BigDecimal("10.000"));
        o = new V3D_Point(x, y, z);
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
        int mps = 0;
        V3D_Point instance = P0P0P0;
        BigDecimal expResult = BigDecimal.ZERO;
        BigDecimal result = instance.getDistance(P0P0P0, mps);
        assertEquals(expResult, result);
        // Test 2
        mps = 1;
        instance = new V3D_Point(P3, P4, P0);
        expResult = P5.toBigDecimal();
        result = instance.getDistance(P0P0P0, mps);
        //assertEquals(expResult, result);
        assertThat(expResult, Matchers.comparesEqualTo(result));
    }

    /**
     * Test of getEnvelope method, of class V3D_Point.
     */
    @Test
    public void testGetEnvelope() {
        System.out.println("getEnvelope");
        V3D_Envelope expResult = new V3D_Envelope(P0P0P0, P1P1P1);
        V3D_Envelope result = P0P0P0.getEnvelope();
        result = result.union(P1P1P1.getEnvelope());
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
        V3D_Vector v = new V3D_Vector(P1, P1, P1);
        V3D_Point instance = P0P0P0;
        V3D_Point expResult = P1P1P1;
        V3D_Point result = instance.apply(v);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class V3D_Point.
     */
    @Test
    public void testEquals_V3D_Point() {
        System.out.println("equals");
        V3D_Point instance = P0P0P0;
        BigRational x = BigRational.valueOf(new BigDecimal("0.000"));
        BigRational y = BigRational.valueOf(new BigDecimal("0.000"));
        BigRational z = BigRational.valueOf(new BigDecimal("0.000"));
        V3D_Point p = new V3D_Point(x, y, z);
        boolean expResult = true;
        boolean result = instance.equals(p);
        assertEquals(expResult, result);
        // Test 2
        x = P1;
        y = P10;
        z = P0;
        instance = new V3D_Point(x, y, z);
        x = BigRational.valueOf(new BigDecimal("1.000"));
        y = BigRational.valueOf(new BigDecimal("10.000"));
        z = BigRational.valueOf(new BigDecimal("0.000"));
        p = new V3D_Point(x, y, z);
        expResult = true;
        result = instance.equals(p);
        assertEquals(expResult, result);
        // Test 3
        x = BigRational.ONE;
        y = BigRational.TEN;
        z = BigRational.ZERO;
        instance = new V3D_Point(x, y, z);
        x = BigRational.valueOf(new BigDecimal("0.000"));
        y = BigRational.valueOf(new BigDecimal("1.000"));
        z = BigRational.valueOf(new BigDecimal("10.000"));
        p = new V3D_Point(x, y, z);
        expResult = false;
        result = instance.equals(p);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistance method, of class V3D_Point.
     */
    @Test
    public void testGetDistance_V3D_Point() {
        System.out.println("getDistance");
        V3D_Point p = V3D_Point.ORIGIN;
        V3D_Point instance = V3D_Point.ORIGIN;
        Math_BigRationalSqrt expResult = Math_BigRationalSqrt.ZERO;
        Math_BigRationalSqrt result = instance.getDistance(p);
        assertEquals(expResult, result);
        // Test 2
         instance = P1P0P0;
         expResult = Math_BigRationalSqrt.ONE;
         result = instance.getDistance(p);
        assertEquals(expResult, result);
        // Test 3
         instance = P1P1P0;
         expResult = new Math_BigRationalSqrt(2);
         result = instance.getDistance(p);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistance method, of class V3D_Point.
     */
    @Test
    public void testGetDistance_V3D_Point_int() {
        System.out.println("getDistance");
        V3D_Point p = P0P0P0;
        int mps = 0;
        V3D_Point instance = P1P0P0;
        BigDecimal expResult = P1.toBigDecimal();
        BigDecimal result = instance.getDistance(p, mps);
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_Point(P3, P4, P0);
        expResult = P5.toBigDecimal();
        result = instance.getDistance(p, mps);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Point.
     */
    @Test
    public void testGetDistanceSquared() {
        System.out.println("getDistanceSquared");
        V3D_Point instance = P0P0P0;
        BigRational expResult = BigRational.ZERO;
        BigRational result = instance.getDistanceSquared(P0P0P0);
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_Point(P3, P4, P0);
        expResult = BigRational.valueOf(25);
        result = instance.getDistanceSquared(P0P0P0);
        //assertEquals(expResult, result);
        assertThat(expResult, Matchers.comparesEqualTo(result));
    }

    /**
     * Test of getDistance method, of class V3D_Point.
     */
    @Test
    public void testGetDistance_V3D_Line_int() {
        System.out.println("getDistance");
        V3D_Line l = new V3D_Line(P0P0P0, P0P0P1);
        int mps = 0;
        V3D_Point instance = P0P1P0;
        BigDecimal expResult = P1.toBigDecimal();
        BigDecimal result = instance.getDistance(l, mps);
        assertEquals(expResult, result);
        // Test 2
        l = new V3D_Line(P0P0P0, P0P0P1);
        instance = new V3D_Point(P3, P4, P0);
        expResult = P5.toBigDecimal();
        result = instance.getDistance(l, mps);
        assertEquals(expResult, result);
        // Test 3
        l = new V3D_Line(P0P0P1, P0P0P0);
        instance = new V3D_Point(P3, P4, P0);
        expResult = P5.toBigDecimal();
        result = instance.getDistance(l, mps);
        assertEquals(expResult, result);
        // Test 4
        l = new V3D_Line(P0P0P0, P0P0P1);
        instance = new V3D_Point(P4, P3, P0);
        expResult = P5.toBigDecimal();
        result = instance.getDistance(l, mps);
        assertEquals(expResult, result);
        // Test 3
        l = new V3D_Line(P0P0P0, P0P0P1);
        instance = new V3D_Point(P4, P3, P10);
        expResult = P5.toBigDecimal();
        result = instance.getDistance(l, mps);
        assertEquals(expResult, result);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Point.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point() {
        System.out.println("isIntersectedBy");
        V3D_Point p = V3D_Point.ORIGIN;
        V3D_Point instance = V3D_Point.ORIGIN;
        assertTrue(instance.isIntersectedBy(p));
        // Test 2
        instance = P0N1N1;
        assertFalse(instance.isIntersectedBy(p));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Point.
     */
    @Test
    public void testIsIntersectedBy_V3D_Line() {
        System.out.println("isIntersectedBy");
        // No test as this is tested in V3D_LineTest.
    }

    /**
     * Test of getIntersection method, of class V3D_Point.
     */
    @Test
    public void testGetIntersection_V3D_Line() {
        System.out.println("getIntersection");
        // No test as this is tested in V3D_LineTest.
    }

    /**
     * Test of getIntersection method, of class V3D_Point.
     */
    @Test
    public void testGetIntersection_V3D_LineSegment_boolean() {
        System.out.println("getIntersection");
        // No test as this is tested in V3D_LineSegmentTest.
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Point.
     */
    @Test
    public void testIsIntersectedBy_V3D_LineSegment_boolean() {
        System.out.println("isIntersectedBy");
        // No test as this is tested in V3D_LineSegmentTest.
    }

    /**
     * Test of isEnvelopeIntersectedBy method, of class V3D_Point.
     */
    @Test
    public void testIsEnvelopeIntersectedBy() {
        System.out.println("isEnvelopeIntersectedBy");
        // No test as this is tested in V3D_EnvelopeTest.
    }

    /**
     * Test of getDistance method, of class V3D_Point.
     */
    @Test
    public void testGetDistance_V3D_Plane() {
        System.out.println("getDistance");
        // No test as this is tested in V3D_PlaneTest.
    }

    /**
     * Test of getDistance method, of class V3D_Point.
     */
    @Test
    public void testGetDistance_V3D_Plane_int() {
        System.out.println("getDistance");
        // No test as this is tested in V3D_PlaneTest.
    }

}
