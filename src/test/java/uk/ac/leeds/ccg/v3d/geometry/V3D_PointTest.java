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
import org.junit.jupiter.api.Disabled;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.V3D_Test;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * Test of V3D_Point class.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_PointTest extends V3D_Test {

    public V3D_PointTest(){}

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
        Math_BigRational x = P0;
        Math_BigRational y = P0;
        Math_BigRational z = P0;
        V3D_Point instance = new V3D_Point(x, y, z);
        String expResult = "V3D_Point\n"
                + "(\n"
                + " pos=V3D_Vector\n"
                + " (\n"
                + "  dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  m=Math_BigRationalSqrt(x=0, sqrtx=0, oom=-3)\n"
                + " )\n"
                + " ,\n"
                + " offset=V3D_Vector\n"
                + " (\n"
                + "  dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  m=Math_BigRationalSqrt(x=0, sqrtx=0, oom=-3)\n"
                + " )\n"
                + ")";
        String result = instance.toString();
        //System.out.println(result);
        assertTrue(expResult.equalsIgnoreCase(result));
    }

    /**
     * Test of equals method, of class V3D_Point.
     */
    @Test
    public void testEquals_Object() {
        System.out.println("equals");
        V3D_Point instance = P0P0P0;
        Math_BigRational x = Math_BigRational.valueOf(new BigDecimal("0.000"));
        Math_BigRational y = Math_BigRational.valueOf(new BigDecimal("0.000"));
        Math_BigRational z = Math_BigRational.valueOf(new BigDecimal("0.000"));
        Object o = new V3D_Point(x, y, z);
        boolean expResult = true;
        boolean result = instance.equals(o);
        assertEquals(expResult, result);
        // Test 2
        x = P1;
        y = P10;
        z = P0;
        instance = new V3D_Point(x, y, z);
        x = Math_BigRational.valueOf(new BigDecimal("1.000"));
        y = Math_BigRational.valueOf(new BigDecimal("10.000"));
        z = Math_BigRational.valueOf(new BigDecimal("0.000"));
        o = new V3D_Point(x, y, z);
        expResult = true;
        result = instance.equals(o);
        assertEquals(expResult, result);
        // Test 3
        x = Math_BigRational.ONE;
        y = Math_BigRational.TEN;
        z = Math_BigRational.ZERO;
        instance = new V3D_Point(x, y, z);
        x = Math_BigRational.valueOf(new BigDecimal("0.000"));
        y = Math_BigRational.valueOf(new BigDecimal("1.000"));
        z = Math_BigRational.valueOf(new BigDecimal("10.000"));
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
        int oom = 0;
        V3D_Point instance = P0P0P0;
        BigDecimal expResult = BigDecimal.ZERO;
        BigDecimal result = instance.getDistance(P0P0P0, oom);
        assertEquals(expResult, result);
        // Test 2
        oom = -1;
        instance = new V3D_Point(P3, P4, P0);
        expResult = P5.toBigDecimal(oom);
        result = instance.getDistance(P0P0P0, oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getEnvelope method, of class V3D_Point.
     */
    @Test
    public void testGetEnvelope() {
        System.out.println("getEnvelope");
        int oom = -1;
        V3D_Envelope expResult = new V3D_Envelope(oom, P0P0P0, P1P1P1);
        V3D_Envelope result = P0P0P0.getEnvelope(oom);
        result = result.union(P1P1P1.getEnvelope(oom));
        assertEquals(expResult, result);
    }

    /**
     * Test of hashCode method, of class V3D_Point.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        V3D_Envelope e = P0P0P0.getEnvelope(V3D_Environment.DEFAULT_OOM);
        int result = e.hashCode();
        int expResult = 1820997234;
        //System.out.println(result);
        assertTrue(result == expResult);
    }

    /**
     * Test of apply method, of class V3D_Point.
     */
    @Test
    public void testApply() {
        System.out.println("apply");
        int oom = -1;
        V3D_Vector v = new V3D_Vector(P1, P1, P1, oom);
        V3D_Point instance = P0P0P0;
        V3D_Point expResult = P1P1P1;
        V3D_Point result = instance.apply(v, oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class V3D_Point.
     */
    @Test
    public void testEquals_V3D_Point() {
        System.out.println("equals");
        V3D_Point instance = P0P0P0;
        Math_BigRational x = Math_BigRational.valueOf(new BigDecimal("0.000"));
        Math_BigRational y = Math_BigRational.valueOf(new BigDecimal("0.000"));
        Math_BigRational z = Math_BigRational.valueOf(new BigDecimal("0.000"));
        V3D_Point p = new V3D_Point(x, y, z);
        boolean expResult = true;
        boolean result = instance.equals(p);
        assertEquals(expResult, result);
        // Test 2
        x = P1;
        y = P10;
        z = P0;
        instance = new V3D_Point(x, y, z);
        x = Math_BigRational.valueOf(new BigDecimal("1.000"));
        y = Math_BigRational.valueOf(new BigDecimal("10.000"));
        z = Math_BigRational.valueOf(new BigDecimal("0.000"));
        p = new V3D_Point(x, y, z);
        expResult = true;
        result = instance.equals(p);
        assertEquals(expResult, result);
        // Test 3
        x = Math_BigRational.ONE;
        y = Math_BigRational.TEN;
        z = Math_BigRational.ZERO;
        instance = new V3D_Point(x, y, z);
        x = Math_BigRational.valueOf(new BigDecimal("0.000"));
        y = Math_BigRational.valueOf(new BigDecimal("1.000"));
        z = Math_BigRational.valueOf(new BigDecimal("10.000"));
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
        int oom = -1;
        V3D_Point p = V3D_Point.ORIGIN;
        V3D_Point instance = V3D_Point.ORIGIN;
        Math_BigRationalSqrt expResult = Math_BigRationalSqrt.ZERO;
        Math_BigRationalSqrt result = instance.getDistance(oom, p);
        assertEquals(expResult, result);
        // Test 2
        instance = P1P0P0;
        expResult = Math_BigRationalSqrt.ONE;
        result = instance.getDistance(oom, p);
        assertEquals(expResult, result);
        // Test 3
        instance = P1P1P0;
        expResult = new Math_BigRationalSqrt(2, oom);
        result = instance.getDistance(oom, p);
        assertEquals(expResult, result);
        // Test 4
        instance = new V3D_Point(P3, P4, P0);
        expResult = new Math_BigRationalSqrt(25, oom);
        result = instance.getDistance(oom, p);
        assertEquals(expResult, result);
        // Test 5
        instance = new V3D_Point(P0, P3, P4);
        result = instance.getDistance(oom, p);
        assertEquals(expResult, result);
        // Test 6
        instance = new V3D_Point(P3, P0, P4);
        result = instance.getDistance(oom, p);
        assertEquals(expResult, result);
        // Test 7
        instance = new V3D_Point(N3, N4, P0);
        result = instance.getDistance(oom, p);
        assertEquals(expResult, result);
        // Test 8
        instance = new V3D_Point(P0, N3, N4);
        result = instance.getDistance(oom, p);
        assertEquals(expResult, result);
        // Test 9
        instance = new V3D_Point(N3, P0, N4);
        result = instance.getDistance(oom, p);
        assertEquals(expResult, result);
        // Test 10
        instance = new V3D_Point(N3, P4, P0);
        result = instance.getDistance(oom, p);
        assertEquals(expResult, result);
        // Test 11
        instance = new V3D_Point(P0, P3, N4);
        result = instance.getDistance(oom, p);
        assertEquals(expResult, result);
        // Test 12
        instance = new V3D_Point(P3, P0, N4);
        result = instance.getDistance(oom, p);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getDistance method, of class V3D_Point.
     */
    @Test
    public void testGetDistance_V3D_Point_int() {
        System.out.println("getDistance");
        V3D_Point p = P0P0P0;
        int oom = 0;
        V3D_Point instance = P1P0P0;
        BigDecimal expResult = P1.toBigDecimal(oom);
        BigDecimal result = instance.getDistance(p, oom);
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_Point(P3, P4, P0);
        expResult = P5.toBigDecimal(oom);
        result = instance.getDistance(p, oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Point.
     */
    @Test
    public void testGetDistanceSquared() {
        System.out.println("getDistanceSquared");
        V3D_Point instance = P0P0P0;
        int oom = -1;
        Math_BigRational expResult = Math_BigRational.ZERO;
        Math_BigRational result = instance.getDistanceSquared(P0P0P0, oom);
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_Point(P3, P4, P0);
        expResult = Math_BigRational.valueOf(25);
        result = instance.getDistanceSquared(P0P0P0, oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getDistance method, of class V3D_Point.
     */
    @Test
    public void testGetDistance_V3D_Line_int() {
        System.out.println("getDistance");
        int oom = -1;
        V3D_Line l = new V3D_Line(P0P0P0, P0P0P1, oom);
        V3D_Point instance = P0P1P0;
        BigDecimal expResult = P1.toBigDecimal(oom);
        BigDecimal result = instance.getDistance(l, oom);
        assertEquals(expResult, result);
        // Test 2
        l = new V3D_Line(P0P0P0, P0P0P1, oom);
        instance = new V3D_Point(P3, P4, P0);
        expResult = P5.toBigDecimal(oom);
        result = instance.getDistance(l, oom);
        assertEquals(expResult, result);
        // Test 3
        l = new V3D_Line(P0P0P1, P0P0P0, oom);
        instance = new V3D_Point(P3, P4, P0);
        expResult = P5.toBigDecimal(oom);
        result = instance.getDistance(l, oom);
        assertEquals(expResult, result);
        // Test 4
        l = new V3D_Line(P0P0P0, P0P0P1, oom);
        instance = new V3D_Point(P4, P3, P0);
        expResult = P5.toBigDecimal(oom);
        result = instance.getDistance(l, oom);
        assertEquals(expResult, result);
        // Test 3
        l = new V3D_Line(P0P0P0, P0P0P1, oom);
        instance = new V3D_Point(P4, P3, P10);
        expResult = P5.toBigDecimal(oom);
        result = instance.getDistance(l, oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Point.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point() {
        System.out.println("isIntersectedBy");
        int oom = -2;
        V3D_Point p = V3D_Point.ORIGIN;
        V3D_Point instance = V3D_Point.ORIGIN;
        assertTrue(instance.isIntersectedBy(p, oom));
        // Test 2
        instance = P0N1N1;
        assertFalse(instance.isIntersectedBy(p, oom));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Point.
     */
    @Test
    //@Disabled
    public void testIsIntersectedBy_V3D_Line() {
        System.out.println("isIntersectedBy");
        // No test as this is tested in V3D_LineTest.
    }

    /**
     * Test of getIntersection method, of class V3D_Point.
     */
    @Test
    //@Disabled
    public void testGetIntersection_V3D_Line() {
        System.out.println("getIntersection");
        // No test as this is tested in V3D_LineTest.
    }

    /**
     * Test of getIntersection method, of class V3D_Point.
     */
    @Test
    //@Disabled
    public void testGetIntersection_V3D_LineSegment_boolean() {
        System.out.println("getIntersection");
        // No test as this is tested in V3D_LineSegmentTest.
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Point.
     */
    @Test
    //@Disabled
    public void testIsIntersectedBy_V3D_LineSegment_boolean() {
        System.out.println("isIntersectedBy");
        // No test as this is tested in V3D_LineSegmentTest.
    }

    /**
     * Test of isEnvelopeIntersectedBy method, of class V3D_Point.
     */
    @Test
    //@Disabled
    public void testIsEnvelopeIntersectedBy() {
        System.out.println("isEnvelopeIntersectedBy");
        // No test as this is tested in V3D_EnvelopeTest.
    }

    /**
     * Test of getDistance method, of class V3D_Point.
     */
    @Test
    //@Disabled
    public void testGetDistance_V3D_Plane() {
        System.out.println("getDistance");
        // No test as this is tested in V3D_PlaneTest.
    }

    /**
     * Test of getDistance method, of class V3D_Point.
     */
    @Test
    //@Disabled
    public void testGetDistance_V3D_Plane_int() {
        System.out.println("getDistance");
        // No test as this is tested in V3D_PlaneTest.
    }

    /**
     * Test of getDistance method, of class V3D_Point.
     */
    @Test
    public void testGetDistance_V3D_LineSegment_int() {
        System.out.println("getDistance");
        int oom = -1;
        V3D_LineSegment l = new V3D_LineSegment(P0P0P0, P2P0P0, oom);
        V3D_Point instance = P1P1P0;
        BigDecimal expResult = BigDecimal.ONE;
        BigDecimal result = instance.getDistance(l, oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 2
        instance = N1N1P0;
        result = instance.getDistance(l, oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 3
        instance = P2P2P0;
        expResult = BigDecimal.valueOf(2);
        result = instance.getDistance(l, oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 4
        instance = P2P2P0;
        l = new V3D_LineSegment(P0P0P0, P1P0P0, oom);
        expResult = new Math_BigRationalSqrt(5, oom).toBigDecimal(oom);
        result = instance.getDistance(l, oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

}
