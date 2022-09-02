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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.V3D_Test;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * Test of V3D_Rectangle class.
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class V3D_RectangleTest extends V3D_Test {

    public V3D_RectangleTest() {
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
         * q ----------- r
         * |             |
         * |             |
         * |             |
         * p ----------- s
         */
        V3D_Rectangle instance;
        V3D_Envelope expResult;
        V3D_Envelope result;
        instance = new V3D_Rectangle(pN1P1P0, pP1P1P0, pP1N1P0, pN1N1P0);
        expResult = new V3D_Envelope(e, pN1N1P0, pP1P1P0);
        result = instance.getEnvelope();
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_Rectangle(pN1P1P1, pP1P1P0, pP1N1P0, pN1N1P1);
        expResult = new V3D_Envelope(e, pN1N1P0, pP1P1P1);
        result = instance.getEnvelope();
        assertTrue(expResult.equals(result));
        // Test 3
        instance = new V3D_Rectangle(pN1P1P1, pP1P1N1, pP1N1N1, pN1N1P1);
        expResult = new V3D_Envelope(e, pN1N1N1, pP1P1P1);
        result = instance.getEnvelope();
        assertTrue(expResult.equals(result));
        // Test 4
        instance = new V3D_Rectangle(pN1N1N1, pP1N1N1, pP1P1N1, pN1P1N1);
        expResult = new V3D_Envelope(e, pN1N1N1, pP1P1N1);
        result = instance.getEnvelope();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Rectangle.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point_int() {
        System.out.println("isIntersectedBy");
        V3D_Point pt = pP0P0P0;
        V3D_Rectangle instance = new V3D_Rectangle(pN1P1P0, pP1P1P0, pP1N1P0, pN1N1P0);
        assertTrue(instance.isIntersectedBy(pt, e.oom));
        // Test 2
        instance = new V3D_Rectangle(pN1P0P0, pP0P1P0, pP1P0P0, pP0N1P0);
        assertTrue(instance.isIntersectedBy(pt, e.oom));
        // Test 3
        Math_BigRational half = Math_BigRational.ONE.divide(2);
        pt = new V3D_Point(e, half, half, P0);
        assertTrue(instance.isIntersectedBy(pt, e.oom));
        // Test 4
        Math_BigRational epsilon = Math_BigRational.valueOf("0.00000000001");
        Math_BigRational halfpe = half.add(epsilon);
        Math_BigRational halfne = half.subtract(epsilon);
        e.oom = -12;
        pt = new V3D_Point(e, halfpe, half, P0);
        assertFalse(instance.isIntersectedBy(pt, e.oom));
        // Test 5
        e.oom = -12;
        pt = new V3D_Point(e, halfpe.negate(), half, P0);
        assertFalse(instance.isIntersectedBy(pt, e.oom));
        // Test 6
        e.oom = -12;
        pt = new V3D_Point(e, half, halfpe, P0);
        assertFalse(instance.isIntersectedBy(pt, e.oom));
        // Test 7
        e.oom = -12;
        pt = new V3D_Point(e, half, halfpe.negate(), P0);
        assertFalse(instance.isIntersectedBy(pt, e.oom));
        // Test 8
        e.oom = -12;
        pt = new V3D_Point(e, halfne, half, P0);
        assertTrue(instance.isIntersectedBy(pt, e.oom));
        // Test 9
        e.oom = -12;
        pt = new V3D_Point(e, halfne.negate(), half, P0);
        assertTrue(instance.isIntersectedBy(pt, e.oom));
        // Test 10
        e.oom = -12;
        pt = new V3D_Point(e, half, halfne, P0);
        assertTrue(instance.isIntersectedBy(pt, e.oom));
        // Test 11
        e.oom = -12;
        pt = new V3D_Point(e, half, halfne.negate(), P0);
        assertTrue(instance.isIntersectedBy(pt, e.oom));
    }

    /**
     * Test of getIntersection method, of class V3D_Rectangle.
     */
    @Test
    public void testGetIntersection() {
        System.out.println("getIntersection");
        V3D_Line l = new V3D_Line(pP0P0N1, pP0P0P1);
        V3D_Rectangle instance;
        V3D_Geometry expResult;
        V3D_Geometry result;
        instance = new V3D_Rectangle(pN1P1P0, pP1P1P0, pP1N1P0, pN1N1P0);
        expResult = pP0P0P0;
        result = instance.getIntersection(l, e.oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of toString method, of class V3D_Rectangle.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        String expResult = "V3D_Rectangle\n"
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
                + "  dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
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
                + "  dx=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
                + "  dy=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + " )\n"
                + " ,\n"
                + " s=V3D_Vector\n"
                + " (\n"
                + "  dx=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
                + "  dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + "  dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + " )\n"
                + ")";
        String result = instance.toString();
        //System.out.println(result);
        assertTrue(expResult.equalsIgnoreCase(result));
    }

    /**
     * Test of apply method, of class V3D_Rectangle.
     */
    @Test
    public void testApply() {
        // No test.
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Rectangle.
     */
    @Test
    public void testIsIntersectedBy_V3D_Line() {
        System.out.println("isIntersectedBy");
        V3D_Line l = new V3D_Line(pN1N1N1, pP2P2P2);
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        assertTrue(instance.isIntersectedBy(l, e.oom));
        //Test 2
        assertFalse(instance.isIntersectedBy(new V3D_Line(pN1N1N1, pN1N1P1), e.oom));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Rectangle.
     */
    @Test
    public void testIsIntersectedBy_V3D_LineSegment_boolean() {
        System.out.println("isIntersectedBy");
        V3D_LineSegment l = new V3D_LineSegment(pN1N1P0, pP1P1P0);
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        assertTrue(instance.isIntersectedBy(l, e.oom));
        //Test 2
        assertFalse(instance.isIntersectedBy(new V3D_Line(pP0N1P1, pP0N1N1), e.oom));
    }

    /**
     * Test of getIntersection method, of class V3D_Rectangle.
     */
    @Test
    public void testGetIntersection_V3D_LineSegment_boolean() {
        System.out.println("getIntersection");
        V3D_LineSegment l = new V3D_LineSegment(pN1N1P0, pP2P2P0);
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        V3D_Geometry result = instance.getIntersection(l, e.oom);
        V3D_Geometry expResult = new V3D_LineSegment(pP0P0P0, pP1P1P0);
        assertTrue(expResult.equals(result));
    }

//    /**
//     * Test of isEnvelopeIntersectedBy method, of class V3D_Rectangle.
//     */
//    @Test
//    public void testIsEnvelopeIntersectedBy() {
//        System.out.println("isEnvelopeIntersectedBy");
//        V3D_Line l = new V3D_LineSegment(pN1N1P0, pP2P2P0);
//        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
//        assertTrue(instance.isEnvelopeIntersectedBy(l, e.oom));
//    }

    /**
     * Test of getPerimeter method, of class V3D_Rectangle.
     */
    @Test
    public void testGetPerimeter() {
        System.out.println("getPerimeter");
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        BigDecimal expResult = BigDecimal.valueOf(4);
        BigDecimal result = instance.getPerimeter(e.oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getArea method, of class V3D_Rectangle.
     */
    @Test
    public void testGetArea() {
        System.out.println("getArea");
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        BigDecimal expResult = BigDecimal.valueOf(1);
        BigDecimal result = instance.getArea(e.oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getDistance method, of class V3D_Rectangle.
     */
    @Test
    public void testGetDistance() {
        System.out.println("getDistance");
        V3D_Point p = pN1N1P0;
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        BigDecimal expResult = new Math_BigRationalSqrt(2, e.oom).toBigDecimal(e.oom);
        BigDecimal result = instance.getDistance(p, e.oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of toStringFields method, of class V3D_Rectangle.
     */
    @Test
    public void testToStringFields() {
        System.out.println("toStringFields");
        String pad = "";
        e.oom = -3;
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0);
        String expResult = "oom=-3\n"
                + ",\n"
                + "offset=V3D_Vector\n"
                + "(\n"
                + " dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + " dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + " dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + ")\n"
                + ",\n"
                + "p=V3D_Vector\n"
                + "(\n"
                + " dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + " dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + " dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + ")\n"
                + ",\n"
                + "q=V3D_Vector\n"
                + "(\n"
                + " dx=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
                + " dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + " dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + ")\n"
                + ",\n"
                + "r=V3D_Vector\n"
                + "(\n"
                + " dx=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
                + " dy=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
                + " dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + ")\n"
                + ",\n"
                + "s=V3D_Vector\n"
                + "(\n"
                + " dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + " dy=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
                + " dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + ")";
        String result = instance.toStringFields(pad);
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getSV method, of class V3D_Rectangle.
     */
    @Test
    public void testGetSV() {
        System.out.println("getSV");
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0);
        V3D_Vector expResult = P0P1P0;
        V3D_Vector result = instance.getSV();
        assertEquals(expResult, result);
    }

    /**
     * Test of getS method, of class V3D_Rectangle.
     */
    @Test
    public void testGetS() {
        System.out.println("getS");
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0);
        V3D_Point expResult = pP0P1P0;
        V3D_Point result = instance.getS();
        assertEquals(expResult, result);
    }

    /**
     * Test of getRS method, of class V3D_Rectangle.
     */
    @Test
    public void testGetRS() {
        System.out.println("getRS");
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0);
        V3D_LineSegment expResult = new V3D_LineSegment(pP1P1P0, pP0P1P0);
        V3D_LineSegment result = instance.getRS();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSP method, of class V3D_Rectangle.
     */
    @Test
    public void testGetSP() {
        System.out.println("getSP");
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0);
        V3D_LineSegment expResult = new V3D_LineSegment(pP0P1P0, pP0P0P0);
        V3D_LineSegment result = instance.getSP();
        assertEquals(expResult, result);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Rectangle.
     */
    @Test
    public void testIsIntersectedBy_V3D_Line_int() {
        System.out.println("isIntersectedBy");
        V3D_Line l = new V3D_Line(pP0P0P0, pP1P0P0);
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0);
        assertTrue(instance.isIntersectedBy(l, e.oom));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Rectangle.
     */
    @Test
    public void testIsIntersectedBy_3args() {
        System.out.println("isIntersectedBy");
        V3D_LineSegment l = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0);
        assertTrue(instance.isIntersectedBy(l, e.oom));
    }

    /**
     * Test of getIntersection method, of class V3D_Rectangle.
     */
    @Test
    public void testGetIntersection_V3D_Line_int() {
        System.out.println("getIntersection");
        V3D_Line l = new V3D_Line(pP0P0P0, pP1P0P0);
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0);
        V3D_Geometry expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        //V3D_Geometry expResult = new V3D_LineSegment(P0P0P0, P1P0P0, P1P1P0, oom);
        V3D_Geometry result = instance.getIntersection(l, e.oom);
        //System.out.println(result);
        result = instance.getIntersection(l, e.oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of getIntersection method, of class V3D_Rectangle.
     */
    @Test
    public void testGetIntersection_3args() {
        System.out.println("getIntersection");
        V3D_LineSegment l = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0);
        V3D_Geometry expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0);
        V3D_Geometry result;
        //result = instance.getIntersection(l, e.oom);
        //System.out.println(result);
        result = instance.getIntersection(l, e.oom);
        assertEquals(expResult, result);
    }
    
//    /**
//     * Test of setOffset method, of class V3D_Rectangle.
//     */
//    @Test
//    public void testSetOffset() {
//        System.out.println("setOffset");
//        V3D_Vector offset = null;
//        V3D_Rectangle instance = null;
//        instance.setOffset(offset);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of rotate method, of class V3D_Rectangle.
//     */
//    @Test
//    public void testRotate() {
//        System.out.println("rotate");
//        V3D_Vector axisOfRotation = null;
//        Math_BigRational theta = null;
//        V3D_Rectangle instance = null;
//        instance.rotate(axisOfRotation, theta);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of getRSP method, of class V3D_Rectangle.
     */
    @Test
    public void testGetRSP() {
        System.out.println("getRSP");
        V3D_Rectangle instance = null;
        V3D_Triangle expResult = null;
        V3D_Triangle result = instance.getRSP();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPQR method, of class V3D_Rectangle.
     */
    @Test
    public void testGetPQR() {
        System.out.println("getPQR");
        V3D_Rectangle instance = null;
        V3D_Triangle expResult = null;
        V3D_Triangle result = instance.getPQR();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isIntersectedBy0 method, of class V3D_Rectangle.
     */
    @Test
    public void testIsIntersectedBy0() {
        System.out.println("isIntersectedBy0");
        V3D_Point pt = null;
        int oom = -3;
        V3D_Rectangle instance = null;
        boolean expResult = false;
        boolean result = instance.isIntersectedBy0(pt, oom);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Rectangle.
     */
    @Test
    public void testIsIntersectedBy_V3D_Ray_int() {
        System.out.println("isIntersectedBy");
        V3D_Ray ray = null;
        int oom = -3;
        V3D_Rectangle instance = null;
        boolean expResult = false;
        boolean result = instance.isIntersectedBy(ray, oom);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Rectangle.
     */
    @Test
    public void testIsIntersectedBy_V3D_LineSegment_int() {
        System.out.println("isIntersectedBy");
        V3D_LineSegment l = null;
        int oom = -3;
        V3D_Rectangle instance = null;
        boolean expResult = false;
        boolean result = instance.isIntersectedBy(l, oom);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of join method, of class V3D_Rectangle.
     */
    @Test
    public void testJoin() {
        System.out.println("join");
        V3D_Geometry pointOrLineSegment1 = null;
        V3D_Geometry pointOrLineSegment2 = null;
        V3D_Rectangle instance = null;
        V3D_Geometry expResult = null;
        V3D_Geometry result = instance.join(pointOrLineSegment1, pointOrLineSegment2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIntersection method, of class V3D_Rectangle.
     */
    @Test
    public void testGetIntersection_V3D_LineSegment_int() {
        System.out.println("getIntersection");
        V3D_LineSegment l = new V3D_LineSegment(pP1N1P0, pP1P2P0);
        int oom = -3;
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP2P1P0, pP2P0P0);
        V3D_Geometry expResult = new V3D_LineSegment(pP1P0P0, pP1P1P0);
        V3D_Geometry result = instance.getIntersection(l, oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistance method, of class V3D_Rectangle.
     */
    @Test
    public void testGetDistance_V3D_Point_int() {
        System.out.println("getDistance");
        V3D_Point p = null;
        int oom = -3;
        V3D_Rectangle instance = null;
        BigDecimal expResult = null;
        BigDecimal result = instance.getDistance(p, oom);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Rectangle.
     */
    @Test
    public void testGetDistanceSquared_V3D_Point_int() {
        System.out.println("getDistanceSquared");
        V3D_Point p = null;
        int oom = -3;
        V3D_Rectangle instance = null;
        Math_BigRational expResult = null;
        Math_BigRational result = instance.getDistanceSquared(p, oom);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setOffset method, of class V3D_Rectangle.
     */
    @Test
    public void testSetOffset() {
        System.out.println("setOffset");
        V3D_Vector offset = null;
        V3D_Rectangle instance = null;
        instance.setOffset(offset);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of translate method, of class V3D_Rectangle.
     */
    @Test
    public void testTranslate() {
        System.out.println("translate");
        V3D_Vector v = null;
        V3D_Rectangle instance = null;
        instance.translate(v);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of rotate method, of class V3D_Rectangle.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        V3D_Vector axisOfRotation = null;
        Math_BigRational theta = null;
        V3D_Rectangle instance = null;
        instance.rotate(axisOfRotation, theta);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Rectangle.
     */
    @Test
    public void testIsIntersectedBy_V3D_Plane_int() {
        System.out.println("isIntersectedBy");
        V3D_Plane p = null;
        int oom = -3;
        V3D_Rectangle instance = null;
        boolean expResult = false;
        boolean result = instance.isIntersectedBy(p, oom);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Rectangle.
     */
    @Test
    public void testIsIntersectedBy_V3D_Triangle_int() {
        System.out.println("isIntersectedBy");
        V3D_Triangle t = null;
        int oom = -3;
        V3D_Rectangle instance = null;
        boolean expResult = false;
        boolean result = instance.isIntersectedBy(t, oom);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Rectangle.
     */
    @Test
    public void testIsIntersectedBy_V3D_Tetrahedron_int() {
        System.out.println("isIntersectedBy");
        V3D_Tetrahedron t = null;
        int oom = -3;
        V3D_Rectangle instance = null;
        boolean expResult = false;
        boolean result = instance.isIntersectedBy(t, oom);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIntersection method, of class V3D_Rectangle.
     */
    @Test
    public void testGetIntersection_V3D_Plane_int() {
        System.out.println("getIntersection");
        V3D_Plane p = null;
        int oom = -3;
        V3D_Rectangle instance = null;
        V3D_Geometry expResult = null;
        V3D_Geometry result = instance.getIntersection(p, oom);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIntersection method, of class V3D_Rectangle.
     */
    @Test
    public void testGetIntersection_V3D_Triangle_int() {
        System.out.println("getIntersection");
        V3D_Triangle t = null;
        int oom = -3;
        V3D_Rectangle instance = null;
        V3D_Geometry expResult = null;
        V3D_Geometry result = instance.getIntersection(t, oom);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIntersection method, of class V3D_Rectangle.
     */
    @Test
    public void testGetIntersection_V3D_Tetrahedron_int() {
        System.out.println("getIntersection");
        V3D_Tetrahedron t = null;
        int oom = -3;
        V3D_Rectangle instance = null;
        V3D_Geometry expResult = null;
        V3D_Geometry result = instance.getIntersection(t, oom);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDistance method, of class V3D_Rectangle.
     */
    @Test
    public void testGetDistance_V3D_Line_int() {
        System.out.println("getDistance");
        V3D_Line l = new V3D_Line(pP0N1P0, pP1N1P0);
        int oom = -3;
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        BigDecimal expResult = BigDecimal.ONE;
        BigDecimal result = instance.getDistance(l, oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Rectangle.
     */
    @Test
    public void testGetDistanceSquared_V3D_Line_int() {
        System.out.println("getDistanceSquared");
        V3D_Line l = new V3D_Line(pP0N1P0, pP1N1P0);
        int oom = -3;
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        Math_BigRational expResult = Math_BigRational.ONE;
        Math_BigRational result = instance.getDistanceSquared(l, oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistance method, of class V3D_Rectangle.
     */
    @Test
    public void testGetDistance_V3D_LineSegment_int() {
        System.out.println("getDistance");
        V3D_LineSegment l = new V3D_LineSegment(pP0N1P0, pP1N1P0);
        int oom = -3;
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        BigDecimal expResult = BigDecimal.ONE;
        BigDecimal result = instance.getDistance(l, oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Rectangle.
     */
    @Test
    public void testGetDistanceSquared_V3D_LineSegment_int() {
        System.out.println("getDistanceSquared");
        V3D_LineSegment l = new V3D_LineSegment(pP0N1P0, pP1N1P0);
        int oom = -3;
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        Math_BigRational expResult = Math_BigRational.ONE;
        Math_BigRational result = instance.getDistanceSquared(l, oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistance method, of class V3D_Rectangle.
     */
    @Test
    public void testGetDistance_V3D_Plane_int() {
        System.out.println("getDistance");
        V3D_Plane p = new V3D_Plane(pP0N1P0, pP1N1P0, pP0N1P1);
        int oom = -3;
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        BigDecimal expResult = BigDecimal.ONE;
        BigDecimal result = instance.getDistance(p, oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Rectangle.
     */
    @Test
    public void testGetDistanceSquared_V3D_Plane_int() {
        System.out.println("getDistanceSquared");
        V3D_Plane p = new V3D_Plane(pP0N1P0, pP1N1P0, pP0N1P1);
        int oom = -3;
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        Math_BigRational expResult = Math_BigRational.ONE;
        Math_BigRational result = instance.getDistanceSquared(p, oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistance method, of class V3D_Rectangle.
     */
    @Test
    public void testGetDistance_V3D_Triangle_int() {
        System.out.println("getDistance");
        V3D_Triangle t = new V3D_Triangle(pP0N1P0, pP1N1P0, pP0N1P1);
        int oom = -3;
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        BigDecimal expResult = BigDecimal.ONE;
        BigDecimal result = instance.getDistance(t, oom);
        assertEquals(expResult, result);
        // Test 2
        t = new V3D_Triangle(pP0N1P0, pP1N1P0, pP1P1P0);
        expResult = BigDecimal.ZERO;
        result = instance.getDistance(t, oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Rectangle.
     */
    @Test
    public void testGetDistanceSquared_V3D_Triangle_int() {
        System.out.println("getDistanceSquared");
        V3D_Triangle t = null;
        int oom = -3;
        V3D_Rectangle instance = null;
        Math_BigRational expResult = null;
        Math_BigRational result = instance.getDistanceSquared(t, oom);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDistance method, of class V3D_Rectangle.
     */
    @Test
    public void testGetDistance_V3D_Tetrahedron_int() {
        System.out.println("getDistance");
        V3D_Tetrahedron t = null;
        int oom = -3;
        V3D_Rectangle instance = null;
        BigDecimal expResult = null;
        BigDecimal result = instance.getDistance(t, oom);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Rectangle.
     */
    @Test
    public void testGetDistanceSquared_V3D_Tetrahedron_int() {
        System.out.println("getDistanceSquared");
        V3D_Tetrahedron t = null;
        int oom = -3;
        V3D_Rectangle instance = null;
        Math_BigRational expResult = null;
        Math_BigRational result = instance.getDistanceSquared(t, oom);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class V3D_Rectangle.
     */
    @Test
    public void testEquals_Object() {
        System.out.println("equals");
        Object o = new Object();
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        boolean expResult = false;
        boolean result = instance.equals(o);
        assertEquals(expResult, result);
    }

    /**
     * Test of hashCode method, of class V3D_Rectangle.
     */
    @Test
    public void testHashCode() {
        // No tests.
    }

    /**
     * Test of equals method, of class V3D_Rectangle.
     */
    @Test
    public void testEquals_V3D_Rectangle() {
        System.out.println("equals");
        V3D_Rectangle r = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        boolean expResult = true;
        boolean result = instance.equals(r);
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_Rectangle(pP0P1P0, pP1P1P0, pP1P0P0, pP0P0P0);
        result = instance.equals(r);
        assertEquals(expResult, result);
        // Test 3
        instance = new V3D_Rectangle(pP1P1P0, pP1P0P0, pP0P0P0, pP0P1P0);
        result = instance.equals(r);
        assertEquals(expResult, result);
        // Test 4
        instance = new V3D_Rectangle(pP1P0P0, pP0P0P0, pP0P1P0, pP1P1P0);
        result = instance.equals(r);
        assertEquals(expResult, result);
        // Test 5
        instance = new V3D_Rectangle(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0);
        result = instance.equals(r);
        assertEquals(expResult, result);
        // Test 6
        instance = new V3D_Rectangle(pP1P0P0, pP1P1P0, pP0P1P0, pP0P0P0);
        result = instance.equals(r);
        assertEquals(expResult, result);
        // Test 7
        instance = new V3D_Rectangle(pP1P1P0, pP0P1P0, pP0P0P0, pP1P0P0);
        result = instance.equals(r);
        assertEquals(expResult, result);
        // Test 8
        instance = new V3D_Rectangle(pP0P1P0, pP0P0P0, pP1P0P0, pP1P1P0);
        result = instance.equals(r);
        assertEquals(expResult, result);
    }

    /**
     * Test of toTrianglesCoplanar method, of class V3D_Rectangle.
     */
    @Test
    public void testToTrianglesCoplanar() {
        System.out.println("toTrianglesCoplanar");
        V3D_Rectangle instance = new V3D_Rectangle(pP0P1P0, pP0P0P0, pP1P0P0, pP1P1P0);
        V3D_TrianglesCoplanar expResult = new V3D_TrianglesCoplanar(
                instance.getPQR(), instance.getRSP());
        V3D_TrianglesCoplanar result = instance.toTrianglesCoplanar();
        assertEquals(expResult, result);
    }
}
