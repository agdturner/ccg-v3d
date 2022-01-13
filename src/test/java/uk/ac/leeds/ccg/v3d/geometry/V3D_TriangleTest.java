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
        int oom = -1;
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P1P0P0, P1P1P0, oom);
        V3D_Envelope expResult = new V3D_Envelope(oom, pP0P0P0, pP1P0P0, pP1P1P0);
        V3D_Envelope result = instance.getEnvelope(oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Triangle.
     */
    @Test
    public void testIsIntersectedBy() {
        System.out.println("isIntersectedBy");
        V3D_Point pt = pP0P0P0;
        int oom = -1;
        boolean b = true;
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P1P0P0, P1P1P0, oom);
        assertTrue(instance.isIntersectedBy(pt, oom));
        // Test 2
        pt = pN1N1P0;
        assertFalse(instance.isIntersectedBy(pt, oom, b));
        // Test 3
        pt = new V3D_Point(Math_BigRational.ONE.divide(2), P0, P0);
        assertTrue(instance.isIntersectedBy(pt, oom, b));
    }

    /**
     * Test of getEnvelope method, of class V3D_Triangle.
     */
    @Test
    public void testGetEnvelope() {
        System.out.println("getEnvelope");
        int oom = -1;
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P0P1P0, P1P0P0, oom);
        V3D_Envelope expResult = new V3D_Envelope(oom, pP0P0P0, pP0P1P0, pP1P0P0);
        V3D_Envelope result = instance.getEnvelope(oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getArea method, of class V3D_Triangle.
     */
    @Test
    public void testGetArea() {
        System.out.println("getArea");
        int oom = -1;
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P0P1P0, P1P0P0, oom);
        BigDecimal expResult = new BigDecimal("0.5");
        BigDecimal result = instance.getArea(oom);
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
    public void testIsIntersectedBy_3args_2() {
        System.out.println("isIntersectedBy");
        int oom = -1;
        boolean b = true;
        V3D_Line l = new V3D_Line(P0P0P0, P2P2P2, oom);
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P0P1P0, P1P0P0, oom);
        assertTrue(instance.isIntersectedBy(l, oom, b));
        // Test 2
        l = new V3D_Line(P1P1P1, P1P1P0, oom);
        assertFalse(instance.isIntersectedBy(l, oom, b));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Triangle.
     */
    @Test
    public void testIsIntersectedBy_3args_3() {
        System.out.println("isIntersectedBy");
        int oom = -1;
        boolean b = true;
        V3D_LineSegment l = new V3D_LineSegment(P0P0P0, P2P2P2, oom);
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P0P1P0, P1P0P0, oom);
        assertTrue(instance.isIntersectedBy(l, oom, b));
        // Test 2
        l = new V3D_LineSegment(P1P1P1, P1P1P0, oom);
        assertFalse(instance.isIntersectedBy(l, oom, b));
    }

    /**
     * Test of getPerimeter method, of class V3D_Triangle.
     */
    @Test
    public void testGetPerimeter() {
        System.out.println("getPerimeter");
        int oom = 0;
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P0P1P0, P1P0P0, oom);
        BigDecimal expResult = BigDecimal.valueOf(2).add(new Math_BigRationalSqrt(2, oom).toBigDecimal(oom));
        BigDecimal result = instance.getPerimeter(oom);
        assertTrue(expResult.compareTo(result) == 0);
        oom = -1;
        expResult = BigDecimal.valueOf(2).add(new Math_BigRationalSqrt(2, oom).toBigDecimal(oom));
        result = instance.getPerimeter(oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getIntersection method, of class V3D_Triangle.
     */
    @Test
    public void testGetIntersection_V3D_Line() {
        System.out.println("getIntersection");
        int oom = -1;
        V3D_Line l = new V3D_Line(P1N1P0, P1P2P0, oom);
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P1P1P0, P2P0P0, oom);
        V3D_Geometry expResult = new V3D_LineSegment(P1P0P0, P1P1P0, oom);
        V3D_Geometry result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of isEnvelopeIntersectedBy method, of class V3D_Triangle.
     */
    @Test
    public void testIsEnvelopeIntersectedBy() {
        System.out.println("isEnvelopeIntersectedBy");
        int oom = -1;
        V3D_Line l = new V3D_Line(P1N1P0, P1P2P0, oom);
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P1P1P0, P2P0P0, oom);
        assertTrue(instance.isEnvelopeIntersectedBy(l, oom));
    }

    /**
     * Test of getCentroid method, of class V3D_Triangle.
     */
    @Test
    public void testGetCentroid() {
        System.out.println("getCentroid");
        int oom;
        V3D_Triangle instance;
        V3D_Point expResult;
        V3D_Point result;
        // Test
        oom = -3; //-2 fails!
        instance = new V3D_Triangle(P0P0P0, P1P0P0, P1P1P0, oom);
        expResult = new V3D_Point(Math_BigRational.valueOf(2, 3),
                Math_BigRational.valueOf(1, 3), Math_BigRational.ZERO);
        result = instance.getCentroid(oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of toString method, of class V3D_Triangle.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P1P0P0, P0P1P0, P0P0P1, -3);
        String expResult = "V3D_Triangle\n"
                + "(\n"
                + " offset=V3D_Vector\n"
                + " (\n"
                + "  dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + " )\n"
                + " ,\n"
                + " oom=-3\n"
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
    public void testIsIntersectedByBy_3args_1() {
        System.out.println("isIntersectedBy");
        V3D_Point pt;
        int oom = -3;
        boolean b = true;
        V3D_Triangle instance;
        instance = new V3D_Triangle(P0P0P0, P1P0P0, P0P1P0, P0P0P1, oom);
        // Test 1
        pt = new V3D_Point(P0P0P0, P1P0P0);
        assertTrue(instance.isIntersectedBy(pt, oom, b));
        // Test 2
        pt = new V3D_Point(P0P0P0, P0P1P0);
        assertTrue(instance.isIntersectedBy(pt, oom, b));
        // Test 3
        pt = new V3D_Point(P0P0P0, P0P0P1);
        assertTrue(instance.isIntersectedBy(pt, oom, b));
        // Test 4
        pt = new V3D_Point(P1P0P0, P0P0P0);
        assertTrue(instance.isIntersectedBy(pt, oom, b));
        // Test 5
        pt = new V3D_Point(P0P1P0, P0P0P0);
        assertTrue(instance.isIntersectedBy(pt, oom, b));
        // Test 6
        pt = new V3D_Point(P0P0P1, P0P0P0);
        assertTrue(instance.isIntersectedBy(pt, oom, b));
        // Test 7
        pt = new V3D_Point(P1P0P0, P1P0P0);
        assertFalse(instance.isIntersectedBy(pt, oom, b));
        // Test 8
        pt = new V3D_Point(P0P1P0, P0P1P0);
        assertFalse(instance.isIntersectedBy(pt, oom, b));
        // Test 9
        pt = new V3D_Point(P0P0P1, P0P0P1);
        assertFalse(instance.isIntersectedBy(pt, oom, b));
        // Test 10
        instance = new V3D_Triangle(P0P0P0, P1P0P0, P1P2P0, P2P0P0, oom);
        pt = new V3D_Point(P0P0P0, new V3D_Vector(P1, P3, P0));
        assertFalse(instance.isIntersectedBy(pt, oom, b));
        pt = new V3D_Point(P0P0P0, P1P2P0);
        assertTrue(instance.isIntersectedBy(pt, oom, b));
        pt = new V3D_Point(P0P0P0, P1P1P0);
        assertTrue(instance.isIntersectedBy(pt, oom, b));
        pt = new V3D_Point(P0P0P0, P0P0P0);
        assertFalse(instance.isIntersectedBy(pt, oom, b));
        pt = new V3D_Point(P0P0P0, new V3D_Vector(Math_BigRational.valueOf(1.5d), P1, P0));
        assertTrue(instance.isIntersectedBy(pt, oom, b));
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
     * Test of isIntersectedBy method, of class V3D_Triangle.
     */
    @Test
    public void testIsIntersectedBy_3args() {
        System.out.println("isIntersectedBy");
        V3D_LineSegment l = null;
        int oom = -3;
        boolean b = false;
        V3D_Triangle instance = null;
        // Test 1
        instance = new V3D_Triangle(P0P0P0, P1P0P0, P1P2P0, P2P0P0, oom);
        l = new V3D_LineSegment(P0P0P0, P0P0P0, P1P0P0, oom);
        assertTrue(instance.isIntersectedBy(l, oom, b));
        // Test 2
        l = new V3D_LineSegment(P0P0P0, P0P0P0, new V3D_Vector(Math_BigRational.valueOf(0.5d), P0, P0), oom);
        assertFalse(instance.isIntersectedBy(l, oom, b));
        // Test 3
        l = new V3D_LineSegment(P0P0P0, P1P0P1, P1P0P0, oom);
        assertTrue(instance.isIntersectedBy(l, oom, b));
        // Test 4
        l = new V3D_LineSegment(P0P0P0, P1P0P1, P1P0N1, oom);
        assertTrue(instance.isIntersectedBy(l, oom, b));
        // Test 5
        l = new V3D_LineSegment(P0P0P0, P1P0P1, new V3D_Vector(P1, P0, Math_BigRational.valueOf(0.5d)), oom);
        assertFalse(instance.isIntersectedBy(l, oom, b));
    }

    /**
     * Test of getIntersection method, of class V3D_Triangle.
     */
    @Test
    public void testGetIntersection_V3D_Line_int() {
        System.out.println("getIntersection");
        V3D_Line l = null;
        int oom = -3;
        V3D_Triangle instance = null;
        V3D_Geometry expResult = null;
        V3D_Geometry result = null;
        // Test 1
        instance = new V3D_Triangle(P0P0P0, P1P0P0, P1P2P0, P2P0P0, oom);
        l = new V3D_Line(P0P0P0, P1P0P1, P1P0N1, oom);
        expResult = new V3D_Point(P0P0P0, P1P0P0);
        result = instance.getIntersection(l, oom);
        //System.out.println(result);
        //System.out.println(expResult);
        assertEquals(expResult, result);
        // Test 2
        l = new V3D_Line(P0P0P0, P1P0P0, P2P0P0, oom);
        expResult = new V3D_LineSegment(P0P0P0, P1P0P0, P2P0P0, oom);
        result = instance.getIntersection(l, oom);
        assertEquals(expResult, result);
        // Test 3
        l = new V3D_Line(P0P0P0, P1P0P0, P2P0P0, oom);
        expResult = new V3D_LineSegment(P0P0P0, P1P0P0, P2P0P0, oom);
        result = instance.getIntersection(l, oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of getIntersection method, of class V3D_Triangle.
     */
    @Test
    public void testGetIntersection_3args() {
        System.out.println("getIntersection");
        V3D_LineSegment l = null;
        int oom = -3;
        boolean b = false;
        V3D_Triangle instance = null;
        V3D_Geometry expResult = null;
        V3D_Geometry result = null;
        // Test 1
        instance = new V3D_Triangle(P0P0P0, P1P0P0, P1P2P0, P2P0P0, oom);
        l = new V3D_LineSegment(P0P0P0, P1P0P1, P1P0N1, oom);
        expResult = new V3D_Point(P0P0P0, P1P0P0);
        result = instance.getIntersection(l, oom, b);
        //System.out.println(result);
        //System.out.println(expResult);
        assertEquals(expResult, result);
        // Test 2
        l = new V3D_LineSegment(P0P0P0, P1P0P0, P2P0P0, oom);
        expResult = new V3D_LineSegment(P0P0P0, P1P0P0, P2P0P0, oom);
        result = instance.getIntersection(l, oom, b);
        assertEquals(expResult, result);
        // Test 3
        l = new V3D_LineSegment(P0P0P0, P1P0P0, P2P0P0, oom);
        expResult = new V3D_LineSegment(P0P0P0, P1P0P0, P2P0P0, oom);
        result = instance.getIntersection(l, oom, b);
        assertEquals(expResult, result);
        // Test 4
        l = new V3D_LineSegment(P2N2P0, P2P1P0, oom);
        instance = new V3D_Triangle(P0P0P0, P2P2P0, new V3D_Vector(P4, P0, P0), oom);
        expResult = new V3D_LineSegment(P2P0P0, P2P1P0, oom);
        result = instance.getIntersection(l, oom, b);
        System.out.println(result);
        System.out.println(expResult);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of equals method, of class V3D_Triangle.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        int oom = -3;
        V3D_Triangle t = new V3D_Triangle(P0P0P0, P1P0P0, P1P2P0, P2P0P0, oom);
        V3D_Triangle instance = new V3D_Triangle(P1P0P0, P0P0P0, P0P2P0, P1P0P0, oom);
        assertEquals(t, instance);
        // Test 2
        instance = new V3D_Triangle(P1P0P0, P0P2P0, P0P0P0, P1P0P0, oom);
        assertEquals(t, instance);
        // Test 3
        instance = new V3D_Triangle(P1P0P0, P0P2P0, P1P0P0, P0P0P0, oom);
        assertEquals(t, instance);
        // Test 4
        instance = new V3D_Triangle(P1P0P0, P1P0P0, P0P2P0, P0P0P0, oom);
        assertEquals(t, instance);
    }

    /**
     * Test of getGeometry method, of class V3D_Triangle.
     */
    @Test
    public void testGetGeometry() {
        System.out.println("getGeometry");
        int oom = -3;
        V3D_Vector v1;
        V3D_Vector v2;
        V3D_Triangle instance = new V3D_Triangle(P0P0P0, P1P0P0, P0P1P0, P1P1P0, oom);
        V3D_Geometry expResult;
        V3D_Geometry result;
        // Test 1
        v1 = P0P0P0;
        v2 = P0P0P0;
        result = instance.getGeometry(v1, v2);
        expResult = new V3D_Point(P0P0P0, P0P0P0);
        assertEquals(expResult, result);
        // Test 2
        v1 = P0P0P0;
        v2 = P0P0P0;
        instance = new V3D_Triangle(P0P0P0, P1P0P0, P0P1P0, P1P1P0, oom);
        result = instance.getGeometry(v1, v2);
        expResult = new V3D_Point(P0P0P0, P0P0P0);
        assertEquals(expResult, result);
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
        int oom = -3;
        int oomt = oom -2;
        Math_BigRational Pi = Math_BigRational.valueOf(
                new Math_BigDecimal().getPi(oomt, RoundingMode.HALF_UP));
        // Test 1
        instance = new V3D_Triangle(P0P0P0, P1P0P0, P0P1P0, P1P1P0, oom);
        axisOfRotation = V3D_Vector.I;
        theta = Pi;                
        instance.rotate(axisOfRotation, theta);
        expResult = new V3D_Triangle(P0P0P0, P1P0P0, P0N1P0, P1N1P0, oom);
        //System.out.println(instance);
        assertEquals(expResult, instance);
        // Test 2
        theta = Pi.divide(2);                
        instance.rotate(axisOfRotation, theta);
        expResult = new V3D_Triangle(P0P0P0, P1P0P0, P0P0P1, P1P0P1, oom);
        //System.out.println(instance);
        assertEquals(expResult, instance);
        // Test 3
        instance = new V3D_Triangle(P0P0P0, P2P0P0, P0P2P0, P2P2P0, oom);
        theta = Pi;                
        instance.rotate(axisOfRotation, theta);
        expResult = new V3D_Triangle(P0P0P0, P1N1P0, P0N2P0, P1N2P0, oom);
        System.out.println(instance);
        assertEquals(expResult, instance);
        // Test 4
        theta = Pi.divide(2);                
        instance.rotate(axisOfRotation, theta);
        expResult = new V3D_Triangle(P0P0P0, P1P0P1, P0P0P2, P1P0P2, oom);
        System.out.println(instance);
        assertEquals(expResult, instance);
        // Test 5
        instance = new V3D_Triangle(P0P0P0, P1P0P0, P0P1P0, P1P1P0, oom);
        instance.setOffset(P1P0P0);
        axisOfRotation = V3D_Vector.I;
        theta = Pi;                
        instance.rotate(axisOfRotation, theta);
        expResult = new V3D_Triangle(P0P0P0, P0P1P0, P1P1P0, P1P2P0, oom);
        System.out.println(instance);
        assertEquals(expResult, instance);
    }

}
