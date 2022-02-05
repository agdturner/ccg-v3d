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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.V3D_Test;

/**
 * Test of V3D_Triangle class.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_TriangleTest extends V3D_Test {

    public V3D_TriangleTest() {
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
        V3D_Triangle instance = new V3D_Triangle(pP0P0P0, pP1P0P0, pP1P1P0);
        V3D_Envelope expResult = new V3D_Envelope(e, pP0P0P0, pP1P0P0, pP1P1P0);
        V3D_Envelope result = instance.getEnvelope();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Triangle.
     */
    @Test
    public void testIsIntersectedBy() {
        System.out.println("isIntersectedBy");
        V3D_Point pt = pP0P0P0;
        V3D_Triangle instance = new V3D_Triangle(pP0P0P0, pP1P0P0, pP1P1P0);
        assertTrue(instance.isIntersectedBy(pt, e.oom));
        // Test 2
        pt = pN1N1P0;
        assertFalse(instance.isIntersectedBy(pt, e.oom));
        // Test 3
        pt = new V3D_Point(e, Math_BigRational.ONE.divide(2), P0, P0);
        assertTrue(instance.isIntersectedBy(pt, e.oom));
    }

    /**
     * Test of getEnvelope method, of class V3D_Triangle.
     */
    @Test
    public void testGetEnvelope() {
        System.out.println("getEnvelope");
        V3D_Triangle instance = new V3D_Triangle(pP0P0P0, pP0P1P0, pP1P0P0);
        V3D_Envelope expResult = new V3D_Envelope(e, pP0P0P0, pP0P1P0, pP1P0P0);
        V3D_Envelope result = instance.getEnvelope();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getArea method, of class V3D_Triangle.
     */
    @Test
    public void testGetArea() {
        System.out.println("getArea");
        V3D_Triangle instance = new V3D_Triangle(pP0P0P0, pP0P1P0, pP1P0P0);
        BigDecimal expResult = new BigDecimal("0.5");
        BigDecimal result = instance.getArea(e.oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of apply method, of class V3D_Triangle.
     */
    @Test
    public void testApply() {
        // No test.
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Triangle.
     */
    @Test
    public void testIsIntersectedBy_V3D_Line_int() {
        System.out.println("isIntersectedBy");
        V3D_Line l = new V3D_Line(pP0P0P0, pP2P2P2);
        V3D_Triangle instance = new V3D_Triangle(pP0P0P0, pP0P1P0, pP1P0P0);
        assertTrue(instance.isIntersectedBy(l, e.oom));
        // Test 2
        l = new V3D_Line(pP1P1P1, pP1P1P0);
        assertFalse(instance.isIntersectedBy(l, e.oom));
    }
    
    /**
     * Test of isIntersectedBy method, of class V3D_Triangle.
     */
    @Test
    public void testIsIntersectedBy_V3D_LineSegment_int() {
        System.out.println("isIntersectedBy");
        V3D_LineSegment l;
        V3D_Triangle instance;
        // Test 1
        instance = new V3D_Triangle(e, P0P0P0, P1P0P0, P1P2P0, P2P0P0);
        l = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        assertTrue(instance.isIntersectedBy(l, e.oom));
        // Test 2
        l = new V3D_LineSegment(e, P0P0P0, P0P0P0,
                new V3D_Vector(Math_BigRational.valueOf(0.5d), P0, P0));
        assertFalse(instance.isIntersectedBy(l, e.oom));
        // Test 3
        l = new V3D_LineSegment(pP1P0P1, pP1P0P0);
        assertTrue(instance.isIntersectedBy(l, e.oom));
        // Test 4
        l = new V3D_LineSegment(pP1P0P1, pP1P0N1);
        assertTrue(instance.isIntersectedBy(l, e.oom));
        // Test 5
        l = new V3D_LineSegment(e, P0P0P0, P1P0P1,
                new V3D_Vector(P1, P0, Math_BigRational.valueOf(0.5d)));
        assertFalse(instance.isIntersectedBy(l, e.oom));
        // Test 6
        l = new V3D_LineSegment(pP0P0P0, pP0P1P0);
        instance = new V3D_Triangle(pN2N2P0, pP0P2P0, pP2N2P0);
        assertTrue(instance.isIntersectedBy(l, e.oom));
        // Test 7
        l = new V3D_LineSegment(pP0P0P0, pP2P2P2);
        instance = new V3D_Triangle(pP0P0P0, pP0P1P0, pP1P0P0);
        assertTrue(instance.isIntersectedBy(l, e.oom));
        // Test 8
        l = new V3D_LineSegment(pP1P1P1, pP1P1P0);
        assertFalse(instance.isIntersectedBy(l, e.oom));
    }

    /**
     * Test of getPerimeter method, of class V3D_Triangle.
     */
    @Test
    public void testGetPerimeter() {
        System.out.println("getPerimeter");
        V3D_Triangle instance = new V3D_Triangle(pP0P0P0, pP0P1P0, pP1P0P0);
        BigDecimal expResult = BigDecimal.valueOf(2).add(new Math_BigRationalSqrt(2, e.oom).toBigDecimal(e.oom));
        BigDecimal result = instance.getPerimeter(e.oom);
        assertTrue(expResult.compareTo(result) == 0);
        expResult = BigDecimal.valueOf(2).add(new Math_BigRationalSqrt(2, e.oom).toBigDecimal(e.oom));
        result = instance.getPerimeter(e.oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getIntersection method, of class V3D_Triangle.
     */
    @Test
    public void testGetIntersection_V3D_Line() {
        System.out.println("getIntersection");
        V3D_Line l = new V3D_Line(pP1N1P0, pP1P2P0);
        V3D_Triangle instance = new V3D_Triangle(pP0P0P0, pP1P1P0, pP2P0P0);
        V3D_Geometry expResult = new V3D_LineSegment(pP1P0P0, pP1P1P0);
        V3D_Geometry result = instance.getIntersection(l, e.oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of isEnvelopeIntersectedBy method, of class V3D_Triangle.
     */
    @Test
    public void testIsEnvelopeIntersectedBy() {
        System.out.println("isEnvelopeIntersectedBy");
        V3D_Line l = new V3D_Line(pP1N1P0, pP1P2P0);
        V3D_Triangle instance = new V3D_Triangle(pP0P0P0, pP1P1P0, pP2P0P0);
        assertTrue(instance.isEnvelopeIntersectedBy(l, e.oom));
    }

    /**
     * Test of getCentroid method, of class V3D_Triangle.
     */
    @Test
    public void testGetCentroid() {
        System.out.println("getCentroid");
        V3D_Triangle instance;
        V3D_Point expResult;
        V3D_Point result;
        // Test
        //oom = -3; //-2 fails!
        instance = new V3D_Triangle(pP0P0P0, pP1P0P0, pP1P1P0);
        expResult = new V3D_Point(e, Math_BigRational.valueOf(2, 3),
                Math_BigRational.valueOf(1, 3), Math_BigRational.ZERO);
        result = instance.getCentroid(e.oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of toString method, of class V3D_Triangle.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        V3D_Triangle instance = new V3D_Triangle(e, P0P0P0, P1P0P0, P0P1P0, P0P0P1);
        String expResult = "V3D_Triangle\n"
                + "(\n"
                + " oom=-3\n"
                + " ,\n"
                + " offset=V3D_Vector\n"
                + " (\n"
                + "  dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + " )\n"
                + " ,\n"
                + " p=V3D_Vector\n"
                + " (\n"
                + "  dx=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
                + "  dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + " )\n"
                + " ,\n"
                + " q=V3D_Vector\n"
                + " (\n"
                + "  dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dy=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + " )\n"
                + " ,\n"
                + " r=V3D_Vector\n"
                + " (\n"
                + "  dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0)\n"
                + " )\n"
                + ")";
        String result = instance.toString();
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Triangle.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point_int() {
        System.out.println("isIntersectedBy");
        V3D_Point pt;
        V3D_Triangle instance;
        instance = new V3D_Triangle(e, P0P0P0, P1P0P0, P0P1P0, P0P0P1);
        assertTrue(instance.isIntersectedBy(pP1P0P0, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P1P0, e.oom));
        assertTrue(instance.isIntersectedBy(pP0P0P1, e.oom));
        pt = new V3D_Point(e, P1P0P0, P0P0P0);
        assertTrue(instance.isIntersectedBy(pt, e.oom));
        // Test 5
        pt = new V3D_Point(e, P0P1P0, P0P0P0);
        assertTrue(instance.isIntersectedBy(pt, e.oom));
        // Test 6
        pt = new V3D_Point(e, P0P0P1, P0P0P0);
        assertTrue(instance.isIntersectedBy(pt, e.oom));
        // Test 7
        pt = new V3D_Point(e, P1P0P0, P1P0P0);
        assertFalse(instance.isIntersectedBy(pt, e.oom));
        // Test 8
        pt = new V3D_Point(e, P0P1P0, P0P1P0);
        assertFalse(instance.isIntersectedBy(pt, e.oom));
        // Test 9
        pt = new V3D_Point(e, P0P0P1, P0P0P1);
        assertFalse(instance.isIntersectedBy(pt, e.oom));
        // Test 10
        instance = new V3D_Triangle(e, P0P0P0, P1P0P0, P1P2P0, P2P0P0);
        pt = new V3D_Point(e, P0P0P0, new V3D_Vector(P1, P3, P0));
        assertFalse(instance.isIntersectedBy(pt, e.oom));
        assertTrue(instance.isIntersectedBy(pP1P2P0, e.oom));
        assertTrue(instance.isIntersectedBy(pP1P1P0, e.oom));
        assertFalse(instance.isIntersectedBy(pP0P0P0, e.oom));
        pt = new V3D_Point(e, P0P0P0, new V3D_Vector(Math_BigRational.valueOf(1.5d), P1, P0));
        assertTrue(instance.isIntersectedBy(pt, e.oom));
    }

    /**
     * Test of isIntersectedBy0 method, of class V3D_Triangle covered by
     * {@link #testIsIntersectedBy_V3D_Point_int()}.
     */
    @Test
    public void testIsIntersectedBy0() {
        System.out.println("isIntersectedBy0");
//        V3D_Point pt = null;
//        int oom = 0;
//        V3D_Triangle instance = null;
//        boolean expResult = false;
//        boolean result = instance.isIntersectedBy0(pt, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    

    /**
     * Test of getIntersection method, of class V3D_Triangle.
     */
    @Test
    public void testGetIntersection_V3D_Line_int() {
        System.out.println("getIntersection");
        V3D_Line l;
        V3D_Triangle instance;
        V3D_Geometry expResult;
        V3D_Geometry result;
        // Test 1
        instance = new V3D_Triangle(e, P0P0P0, P1P0P0, P1P2P0, P2P0P0);
        l = new V3D_Line(pP1P0P1, pP1P0N1);
        expResult = pP1P0P0;
        result = instance.getIntersection(l, e.oom);
        //System.out.println(result);
        //System.out.println(expResult);
        assertEquals(expResult, result);
        // Test 2
        l = new V3D_Line(pP1P0P0, pP2P0P0);
        expResult = new V3D_LineSegment(pP1P0P0, pP2P0P0);
        result = instance.getIntersection(l, e.oom);
        assertEquals(expResult, result);
        // Test 3
        l = new V3D_Line(pP1P0P0, pP2P0P0);
        expResult = new V3D_LineSegment(pP1P0P0, pP2P0P0);
        result = instance.getIntersection(l, e.oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of getIntersection method, of class V3D_Triangle.
     */
    @Test
    public void testGetIntersection_V3D_LineSegment_int() {
        System.out.println("getIntersection");
        V3D_LineSegment l;
        V3D_Triangle instance;
        V3D_Geometry expResult;
        V3D_Geometry result;
        // Test 1
        instance = new V3D_Triangle(pP1P0P0, pP1P2P0, pP2P0P0);
        l = new V3D_LineSegment(pP1P0P1, pP1P0N1);
        expResult = pP1P0P0;
        result = instance.getIntersection(l, e.oom);
        //System.out.println(result);
        //System.out.println(expResult);
        assertEquals(expResult, result);
        // Test 2
        l = new V3D_LineSegment(pP1P0P0, pP2P0P0);
        expResult = new V3D_LineSegment(pP1P0P0, pP2P0P0);
        result = instance.getIntersection(l, e.oom);
        assertEquals(expResult, result);
        // Test 3
        l = new V3D_LineSegment(pP1P0P0, pP2P0P0);
        expResult = new V3D_LineSegment(pP1P0P0, pP2P0P0);
        result = instance.getIntersection(l, e.oom);
        assertEquals(expResult, result);
        // Test 4
        l = new V3D_LineSegment(pP2N2P0, pP2P1P0);
        instance = new V3D_Triangle(e, P0P0P0, P2P2P0,
                new V3D_Vector(P4, P0, P0));
        expResult = new V3D_LineSegment(pP2P0P0, pP2P1P0);
        result = instance.getIntersection(l, e.oom);
        //System.out.println(result);
        //System.out.println(expResult);
        assertTrue(expResult.equals(result));
        // Test 5
        l = new V3D_LineSegment(pP0P0P0, pP0P1P0);
        instance = new V3D_Triangle(pN2N2P0, pP0P2P0, pP2N2P0);
        expResult = new V3D_LineSegment(pP0P0P0, pP0P1P0);
        result = instance.getIntersection(l, e.oom);
        //System.out.println(result);
        //System.out.println(expResult);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of equals method, of class V3D_Triangle.
     */
    @Test
    public void testEquals_V3D_Triangle_boolean() {
        System.out.println("equals");
        V3D_Triangle t = new V3D_Triangle(pP1P0P0, pP1P2P0, pP2P0P0);
        V3D_Triangle instance = new V3D_Triangle(e, P1P0P0, P0P0P0, P0P2P0, P1P0P0);
        boolean result = instance.equals(t);
        assertTrue(result);
        // Test 2
        instance = new V3D_Triangle(e, P1P0P0, P0P2P0, P0P0P0, P1P0P0);
        result = instance.equals(t);
        assertTrue(result);
        // Test 3
        instance = new V3D_Triangle(e, P1P0P0, P0P2P0, P1P0P0, P0P0P0);
        result = instance.equals(t);
        assertTrue(result);
        // Test 4
        instance = new V3D_Triangle(e, P1P0P0, P1P0P0, P0P2P0, P0P0P0);
        result = instance.equals(t);
        assertTrue(result);
    }

    /**
     * Test of rotate method, of class V3D_Triangle.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        V3D_Vector axisOfRotation;
        Math_BigRational theta;
        V3D_Triangle instance;
        V3D_Triangle expResult;
        int oomt = e.oom - 2;
        Math_BigRational Pi = Math_BigRational.valueOf(
                new Math_BigDecimal().getPi(oomt, RoundingMode.HALF_UP));
        // Test 1
        instance = new V3D_Triangle(pP1P0P0, pP0P1P0, pP1P1P0);
        axisOfRotation = V3D_Vector.I;
        theta = Pi;
        instance.rotate(axisOfRotation, theta);
        expResult = new V3D_Triangle(pP1P0P0, pP0N1P0, pP1N1P0);
        //System.out.println(instance);
        assertEquals(expResult, instance);
        // Test 2
        instance = new V3D_Triangle(pP1P0P0, pP0P1P0, pP1P1P0);
        theta = Pi.divide(2);
        instance.rotate(axisOfRotation, theta);
        expResult = new V3D_Triangle(pP1P0P0, pP0P0P1, pP1P0P1);
        //System.out.println(instance);
        assertEquals(expResult, instance);
        // Test 3
        instance = new V3D_Triangle(pP2P0P0, pP0P2P0, pP2P2P0);
        theta = Pi;
        instance.rotate(axisOfRotation, theta);
        expResult = new V3D_Triangle(pP2P0P0, pP0N2P0, pP2N2P0);
        //System.out.println(instance);
        assertEquals(expResult, instance);
        // Test 4
        instance = new V3D_Triangle(pP2P0P0, pP0P2P0, pP2P2P0);
        theta = Pi.divide(2);
        instance.rotate(axisOfRotation, theta);
        expResult = new V3D_Triangle(pP2P0P0, pP0P0P2, pP2P0P2);
        //System.out.println(instance);
        assertEquals(expResult, instance);
        // Test 5
        instance = new V3D_Triangle(pP1P0P0, pP0P1P0, pP1P1P0);
        instance.setOffset(P1P0P0);
        axisOfRotation = V3D_Vector.I;
        theta = Pi;
        instance.rotate(axisOfRotation, theta);
        expResult = new V3D_Triangle(pP1P0P0, pP0N1P0, pP1N1P0);
        //System.out.println(instance);
        assertEquals(expResult, instance);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Triangle.
     */
    @Test
    public void testIsIntersectedBy_V3D_Triangle_int() {
        System.out.println("isIntersectedBy");
        V3D_Triangle t;
        V3D_Triangle instance;
        // Test 1
        t = new V3D_Triangle(pP0P0P0, pP0P1P0, pP1P0P0);
        instance = new V3D_Triangle(pP1P0P0, pP0P1P0, pP1P1P0);
        assertTrue(instance.isIntersectedBy(t, e.oom));
        // Test 2
        t = new V3D_Triangle(pN1P0P0, pP0P1P0, pP1P0P0);
        instance = new V3D_Triangle(pN2P0P0, pP0P2P0, pP2P0P0);
        assertTrue(instance.isIntersectedBy(t, e.oom));
    }

    /**
     * Test of checkSide method, of class V3D_Triangle.
     */
    @Test
    @Disabled
    public void testCheckSide() {
        System.out.println("checkSide");
//        V3D_Triangle t = new V3D_Triangle(pN2P0P0, pP0P2P0, pP2P0P0);
//        V3D_Vector n = t.getN(e.oom);
//        V3D_Triangle instance = new V3D_Triangle(pN2P1P0, pP0P2P0, pP2P1P0);
//        V3D_Vector v = instance.getRPV();
//        assertTrue(instance.checkSide(t, n, v, e.oom));
//        // Test 2
//         t = new V3D_Triangle(pN2N2P0, pP0P0P0, pP2N2P0);
//         n = t.getN(e.oom);
//         instance = new V3D_Triangle(pN2P2P0, pP0P1P0, pP2P2P0);
//         v = instance.getRPV();
//        assertFalse(instance.checkSide(t, n, v, e.oom));
    }

    /**
     * Test of equals method, of class V3D_Triangle.
     */
    @Test
    public void testEquals_Object() {
        System.out.println("equals");
        Object o = new V3D_Triangle(pP0P0P0, pP0P1P0, pP1P0P0);
        V3D_Triangle instance = new V3D_Triangle(pP0P0P0, pP0P1P0, pP1P0P0);
        assertTrue(instance.equals(o));
        // Test 2
        o = new V3D_Triangle(pP0P0P0, pP0P1P0, pP2P0P0);
        assertFalse(instance.equals(o));
        // Test 3
        o = new V3D_Plane(pP0P0P0, pP0P1P0, pP1P0P0);
        assertFalse(instance.equals(o));
    }

    /**
     * Test of hashCode method, of class V3D_Triangle.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        V3D_Triangle instance = new V3D_Triangle(pP0P0P0, pP0P1P0, pP1P0P0);
        int expResult = 679;
        int result = instance.hashCode();
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getIntersection method, of class V3D_Triangle.
     */
    @Test
    public void testGetIntersection_V3D_Plane_int() {
        System.out.println("getIntersection");
        V3D_Triangle instance;
        V3D_Plane p;
        V3D_Geometry expResult;
        V3D_Geometry result;
        instance = new V3D_Triangle(pP0P0P0, pP1P0P0, pP0P1P0);
        // Test 1
        p = V3D_Plane.X0;
        expResult = new V3D_LineSegment(pP0P0P0, pP0P1P0);
        result = instance.getIntersection(p, e.oom);
        assertEquals(expResult, result);
        // Test 2
        p = V3D_Plane.Y0;
        expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        result = instance.getIntersection(p, e.oom);
        assertEquals(expResult, result);
        // Test 3
        p = V3D_Plane.Z0;
        expResult = instance;
        result = instance.getIntersection(p, e.oom);
        assertEquals(expResult, result);
        // Test 4
        instance = new V3D_Triangle(pN1P0P0, pP1P0P0, pP0P1P0);
        p = V3D_Plane.X0;
        expResult = new V3D_LineSegment(pP0P0P0, pP0P1P0);
        result = instance.getIntersection(p, e.oom);
        assertEquals(expResult, result);
        // Test 5
        instance = new V3D_Triangle(pN2N2N2, pN2N2P2, pP2P0P0);
        p = V3D_Plane.X0;
        expResult = new V3D_LineSegment(pP0N1P1, pP0N1N1);
        result = instance.getIntersection(p, e.oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of getIntersection method, of class V3D_Triangle.
     *
     * Look for some examples here:
     * https://math.stackexchange.com/questions/1220102/how-do-i-find-the-intersection-of-two-3d-triangles
     */
    @Test
    public void testGetIntersection_V3D_Triangle_int() {
        System.out.println("getIntersection");
        V3D_Triangle t = new V3D_Triangle(pP0P0P0, pP0P1P0, pP1P0P0);
        V3D_Triangle instance = new V3D_Triangle(pP0P0P0, pP0P1P0, pP1P0P0);
        V3D_Geometry expResult = new V3D_Triangle(pP0P0P0, pP0P1P0, pP1P0P0);
        V3D_Geometry result = instance.getIntersection(t, e.oom);
        assertEquals(expResult, result);
        // Test 2
        t = new V3D_Triangle(pN1N1P0, pP0P2P0, new V3D_Point(e, N3, N1, P0));
        instance = new V3D_Triangle(pP0P0P0, pP0P1P0, pP1P0P0);
        expResult = new V3D_Triangle(pP0P0P0, pP0P1P0, pP1P0P0);
        result = instance.getIntersection(t, e.oom);
        assertEquals(expResult, result);
        // Test 3
        t = new V3D_Triangle(pP0P0P0, pP0P1P0, pP1P0P0);
        instance = new V3D_Triangle(pN1N1P0, pP0P2P0, new V3D_Point(e, N3, N1, P0));
        expResult = new V3D_Triangle(pP0P0P0, pP0P1P0, pP1P0P0);
        result = instance.getIntersection(t, e.oom);
        assertEquals(expResult, result);
        // Test 4
        t = new V3D_Triangle(pP0P0P0, pP2P0P0, pP2P2P0);
        instance = new V3D_Triangle(pP1P0P0, new V3D_Point(e, P3, P0, P0), new V3D_Point(e, P3, P2, P0));
        expResult = new V3D_Triangle(pP1P0P0, pP2P0P0, pP2P1P0);
        result = instance.getIntersection(t, e.oom);
        assertEquals(expResult, result);
        // Test 5: 4 sides
        t = new V3D_Triangle(new V3D_Point(e, P2, N3, P0), new V3D_Point(e, P6, P1, P0), new V3D_Point(e, P2, P5, P0));
        instance = new V3D_Triangle(pP1P0P0, new V3D_Point(e, P3, P0, P0), new V3D_Point(e, P3, P2, P0));
        expResult = new V3D_TrianglesCoplanar(
                new V3D_Triangle(pP2P0P0, new V3D_Point(e, P3, P0, P0),
                        pP2P1P0),
                new V3D_Triangle(
                        new V3D_Point(e, P3, P0, P0),
                        new V3D_Point(e, P3, P2, P0),
                        pP2P1P0));
        result = instance.getIntersection(t, e.oom);
        //System.out.println(result);
        assertEquals(expResult, result);
        // Test 6: 5 sides
        t = new V3D_Triangle(pP0P0P0, new V3D_Point(e, P4, P0, P0), new V3D_Point(e, P2, N4, P0));
        instance = new V3D_Triangle(pP0N2P0, pP2P0P0, new V3D_Point(e, P4, N2, P0));
        Math_BigRational N4_3 = Math_BigRational.valueOf(-4).divide(3);
        V3D_Point m = new V3D_Point(e, Math_BigRational.TWO.divide(3), N4_3, P0);
        V3D_Point n = new V3D_Point(e, Math_BigRational.TEN.divide(3), N4_3, P0);
        expResult = new V3D_TrianglesCoplanar(
                new V3D_Triangle(pP2P0P0, m, n),
                //new V3D_Triangle(m, n, pP1N2P0),
                new V3D_Triangle(m, n, new V3D_Point(e, P3, N2, P0)),
                new V3D_Triangle(pP1N2P0,
                        new V3D_Point(e, P3, N2, P0),
                        n));
        result = instance.getIntersection(t, e.oom);
        //System.out.println(result);
        //boolean equals = expResult.equals(result);
        assertEquals(expResult, result);
        // Test 6a: 6 sides
        t = new V3D_Triangle(pP0P0P0, new V3D_Point(e, P6, P0, P0), new V3D_Point(e, P3, N3, P0));
        instance = new V3D_Triangle(pP0N2P0, new V3D_Point(e, P3, P1, P0), new V3D_Point(e, P6, N2, P0));
        expResult = new V3D_TrianglesCoplanar(
                new V3D_Triangle(pP2P0P0, new V3D_Point(e, P4, P0, P0), pP1N1P0),
                new V3D_Triangle(pP1N1P0, pP2N2P0, new V3D_Point(e, P4, N2, P0)),
                new V3D_Triangle(
                        new V3D_Point(e, P4, P0, P0), 
                        new V3D_Point(e, P4, N2, P0), 
                        new V3D_Point(e, P5, N1, P0)),
                new V3D_Triangle(new V3D_Point(e, P4, P0, P0), pP1N1P0,
                        new V3D_Point(e, P4, N2, P0)));
        result = instance.getIntersection(t, e.oom);
        //System.out.println(result);
        //boolean equals = expResult.equals(result);
        assertEquals(expResult, result);
        // Test 6b: 6 sides
        t = new V3D_Triangle(new V3D_Point(e, P6, P0, P0), pP0P0P0, new V3D_Point(e, P3, N3, P0));
        instance = new V3D_Triangle(pP0N2P0, new V3D_Point(e, P3, P1, P0), new V3D_Point(e, P6, N2, P0));
        expResult = new V3D_TrianglesCoplanar(
                new V3D_Triangle(pP2P0P0, new V3D_Point(e, P4, P0, P0), pP1N1P0),
                new V3D_Triangle(pP1N1P0, pP2N2P0, new V3D_Point(e, P4, N2, P0)),
                new V3D_Triangle(
                        new V3D_Point(e, P4, P0, P0), 
                        new V3D_Point(e, P4, N2, P0), 
                        new V3D_Point(e, P5, N1, P0)),
                new V3D_Triangle(new V3D_Point(e, P4, P0, P0), pP1N1P0,
                        new V3D_Point(e, P4, N2, P0)));
        result = instance.getIntersection(t, e.oom);
        //System.out.println(result);
        //boolean equals = expResult.equals(result);
        assertEquals(expResult, result);

    }

    /**
     * Test of getGeometry method, of class V3D_Triangle.
     */
    @Test
    public void testGetGeometry_V3D_Vector_V3D_Vector() {
        System.out.println("getGeometry");
        V3D_Vector v1;
        V3D_Vector v2;
        V3D_Geometry expResult;
        V3D_Geometry result;
        // Test 1
        v1 = P0P0P0;
        v2 = P0P0P0;
        expResult = pP0P0P0;
        result = V3D_Triangle.getGeometry(e, v1, v2);
        assertEquals(expResult, result);
        // Test 2
        v2 = P1P0P0;
        expResult = new V3D_LineSegment(e, P0P0P0,P0P0P0,P1P0P0);
        result = V3D_Triangle.getGeometry(e, v1, v2);
        assertEquals(expResult, result);
    }

    /**
     * Test of translate method, of class V3D_Triangle.
     */
    @Test
    public void testTranslate() {
        System.out.println("translate");
        V3D_Vector v = P1P0P0;
        V3D_Triangle instance = new V3D_Triangle(e, P0P0P0, P1P0P0, P0P1P0, P1P1P0);
        instance.translate(v);
        V3D_Triangle expResult = new V3D_Triangle(e, P0P0P0, P2P0P0, P1P1P0, P2P1P0);
        assertTrue(expResult.equals(instance));
        // Test 2
        expResult = new V3D_Triangle(e, P1P0P0, P1P0P0, P0P1P0, P1P1P0);
        System.out.println(instance);
        System.out.println(expResult);
        boolean te = expResult.equals(instance);
        assertTrue(expResult.equals(instance));
    }

//    /**
//     * Test of getGeometry method, of class V3D_Triangle.
//     */
//    @Test
//    public void testGetGeometry_3args_1() {
//        System.out.println("getGeometry");
//        V3D_Point p = null;
//        V3D_Point q = null;
//        V3D_Point r = null;
//        V3D_Geometry expResult = null;
//        V3D_Geometry result;
//        // Test 1
//        result = V3D_Triangle.getGeometry(p, q, r);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getGeometry method, of class V3D_Triangle.
//     */
//    @Test
//    public void testGetGeometry_3args_2() {
//        System.out.println("getGeometry");
//        V3D_LineSegment l1 = null;
//        V3D_LineSegment l2 = null;
//        V3D_LineSegment l3 = null;
//        V3D_Triangle instance = null;
//        V3D_Geometry expResult = null;
//        V3D_Geometry result = instance.getGeometry(l1, l2, l3);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getGeometry method, of class V3D_Triangle.
//     */
//    @Test
//    public void testGetGeometry_V3D_LineSegment_V3D_LineSegment() {
//        System.out.println("getGeometry");
//        V3D_LineSegment l1 = null;
//        V3D_LineSegment l2 = null;
//        V3D_Triangle instance = null;
//        V3D_Geometry expResult = null;
//        V3D_Geometry result = instance.getGeometry(l1, l2);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getOpposite method, of class V3D_Triangle.
//     */
//    @Test
//    public void testGetOpposite() {
//        System.out.println("getOpposite");
//        V3D_LineSegment l = null;
//        V3D_Triangle instance = null;
//        V3D_Point expResult = null;
//        V3D_Point result = instance.getOpposite(l);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of isIntersectedBy method, of class V3D_Triangle.
//     */
//    @Test
//    public void testIsIntersectedBy_V3D_Plane_int() {
//        System.out.println("isIntersectedBy");
//        V3D_Plane pl = null;
//        int oom = 0;
//        V3D_Triangle instance = null;
//        boolean expResult = false;
//        boolean result = instance.isIntersectedBy(pl, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of isIntersectedBy method, of class V3D_Triangle.
//     */
//    @Test
//    public void testIsIntersectedBy_V3D_Tetrahedron_int() {
//        System.out.println("isIntersectedBy");
//        V3D_Tetrahedron t = null;
//        int oom = 0;
//        V3D_Triangle instance = null;
//        boolean expResult = false;
//        boolean result = instance.isIntersectedBy(t, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getIntersection method, of class V3D_Triangle.
//     */
//    @Test
//    public void testGetIntersection_V3D_Tetrahedron_int() {
//        System.out.println("getIntersection");
//        V3D_Tetrahedron t = null;
//        int oom = 0;
//        V3D_Triangle instance = null;
//        V3D_Geometry expResult = null;
//        V3D_Geometry result = instance.getIntersection(t, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDistance method, of class V3D_Triangle.
//     */
//    @Test
//    public void testGetDistance_V3D_Point_int() {
//        System.out.println("getDistance");
//        V3D_Point p = null;
//        int oom = 0;
//        V3D_Triangle instance = null;
//        BigDecimal expResult = null;
//        BigDecimal result = instance.getDistance(p, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDistanceSquared method, of class V3D_Triangle.
//     */
//    @Test
//    public void testGetDistanceSquared_V3D_Point_int() {
//        System.out.println("getDistanceSquared");
//        V3D_Point p = null;
//        int oom = 0;
//        V3D_Triangle instance = null;
//        Math_BigRational expResult = null;
//        Math_BigRational result = instance.getDistanceSquared(p, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDistance method, of class V3D_Triangle.
//     */
//    @Test
//    public void testGetDistance_V3D_Line_int() {
//        System.out.println("getDistance");
//        V3D_Line l = null;
//        int oom = 0;
//        V3D_Triangle instance = null;
//        BigDecimal expResult = null;
//        BigDecimal result = instance.getDistance(l, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDistanceSquared method, of class V3D_Triangle.
//     */
//    @Test
//    public void testGetDistanceSquared_V3D_Line_int() {
//        System.out.println("getDistanceSquared");
//        V3D_Line l = null;
//        int oom = 0;
//        V3D_Triangle instance = null;
//        Math_BigRational expResult = null;
//        Math_BigRational result = instance.getDistanceSquared(l, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDistance method, of class V3D_Triangle.
//     */
//    @Test
//    public void testGetDistance_V3D_LineSegment_int() {
//        System.out.println("getDistance");
//        V3D_LineSegment l = null;
//        int oom = 0;
//        V3D_Triangle instance = null;
//        BigDecimal expResult = null;
//        BigDecimal result = instance.getDistance(l, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDistanceSquared method, of class V3D_Triangle.
//     */
//    @Test
//    public void testGetDistanceSquared_V3D_LineSegment_int() {
//        System.out.println("getDistanceSquared");
//        V3D_LineSegment l = null;
//        int oom = 0;
//        V3D_Triangle instance = null;
//        Math_BigRational expResult = null;
//        Math_BigRational result = instance.getDistanceSquared(l, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDistance method, of class V3D_Triangle.
//     */
//    @Test
//    public void testGetDistance_V3D_Plane_int() {
//        System.out.println("getDistance");
//        V3D_Plane pl = null;
//        int oom = 0;
//        V3D_Triangle instance = null;
//        BigDecimal expResult = null;
//        BigDecimal result = instance.getDistance(pl, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDistanceSquared method, of class V3D_Triangle.
//     */
//    @Test
//    public void testGetDistanceSquared_V3D_Plane_int() {
//        System.out.println("getDistanceSquared");
//        V3D_Plane pl = null;
//        int oom = 0;
//        V3D_Triangle instance = null;
//        Math_BigRational expResult = null;
//        Math_BigRational result = instance.getDistanceSquared(pl, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDistance method, of class V3D_Triangle.
//     */
//    @Test
//    public void testGetDistance_V3D_Triangle_int() {
//        System.out.println("getDistance");
//        V3D_Triangle t = null;
//        int oom = 0;
//        V3D_Triangle instance = null;
//        BigDecimal expResult = null;
//        BigDecimal result = instance.getDistance(t, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDistanceSquared method, of class V3D_Triangle.
//     */
//    @Test
//    public void testGetDistanceSquared_V3D_Triangle_int() {
//        System.out.println("getDistanceSquared");
//        V3D_Triangle t = null;
//        int oom = 0;
//        V3D_Triangle instance = null;
//        Math_BigRational expResult = null;
//        Math_BigRational result = instance.getDistanceSquared(t, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDistance method, of class V3D_Triangle.
//     */
//    @Test
//    public void testGetDistance_V3D_Tetrahedron_int() {
//        System.out.println("getDistance");
//        V3D_Tetrahedron t = null;
//        int oom = 0;
//        V3D_Triangle instance = null;
//        BigDecimal expResult = null;
//        BigDecimal result = instance.getDistance(t, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDistanceSquared method, of class V3D_Triangle.
//     */
//    @Test
//    public void testGetDistanceSquared_V3D_Tetrahedron_int() {
//        System.out.println("getDistanceSquared");
//        V3D_Tetrahedron t = null;
//        int oom = 0;
//        V3D_Triangle instance = null;
//        Math_BigRational expResult = null;
//        Math_BigRational result = instance.getDistanceSquared(t, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
}
