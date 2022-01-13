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
        int oom = -1;
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
        instance = new V3D_Rectangle(P0P0P0, N1P1P0, P1P1P0, P1N1P0, N1N1P0, oom);
        expResult = new V3D_Envelope(oom, pN1N1P0, pP1P1P0);
        result = instance.getEnvelope(oom);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_Rectangle(P0P0P0, N1P1P1, P1P1P0, P1N1P0, N1N1P1, oom);
        expResult = new V3D_Envelope(oom, pN1N1P0, pP1P1P1);
        result = instance.getEnvelope(oom);
        assertTrue(expResult.equals(result));
        // Test 3
        instance = new V3D_Rectangle(P0P0P0, N1P1P1, P1P1N1, P1N1N1, N1N1P1, oom);
        expResult = new V3D_Envelope(oom, pN1N1N1, pP1P1P1);
        result = instance.getEnvelope(oom);
        assertTrue(expResult.equals(result));
        // Test 4
        instance = new V3D_Rectangle(P0P0P0, N1N1N1, P1N1N1, P1P1N1, N1P1N1, oom);
        expResult = new V3D_Envelope(oom, pN1N1N1, pP1P1N1);
        result = instance.getEnvelope(oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Rectangle.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point_int() {
        System.out.println("isIntersectedBy");
        int oom = -1;
        V3D_Point pt = pP0P0P0;
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, N1P1P0, P1P1P0, P1N1P0, N1N1P0, oom);
        assertTrue(instance.isIntersectedBy(pt, oom));
        // Test 2
        instance = new V3D_Rectangle(P0P0P0, N1P0P0, P0P1P0, P1P0P0, P0N1P0, oom);
        assertTrue(instance.isIntersectedBy(pt, oom));
        // Test 3
        Math_BigRational half = Math_BigRational.ONE.divide(2);
        pt = new V3D_Point(half, half, P0);
        assertTrue(instance.isIntersectedBy(pt, oom));
        // Test 3
        oom = -12;
        pt = new V3D_Point(half.add(Math_BigRational.valueOf("0.00000000001")), half, P0);
        assertFalse(instance.isIntersectedBy(pt, oom));
    }

    /**
     * Test of getIntersection method, of class V3D_Rectangle.
     */
    @Test
    public void testGetIntersection() {
        System.out.println("getIntersection");
        int oom = -1;
        V3D_Line l = new V3D_Line(P0P0N1, P0P0P1, oom);
        V3D_Rectangle instance;
        V3D_Geometry expResult;
        V3D_Geometry result;
        instance = new V3D_Rectangle(P0P0P0, N1P1P0, P1P1P0, P1N1P0, N1N1P0, oom);
        expResult = pP0P0P0;
        result = instance.getIntersection(l, oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of toString method, of class V3D_Rectangle.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        int oom = V3D_Environment.DEFAULT_OOM;
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, P0P0P0, P0P1P0, P1P1P0, P1P0P0, oom);
        String expResult = "V3D_Rectangle\n"
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
        int oom = -1;
        V3D_Line l = new V3D_Line(N1N1N1, P2P2P2, oom);
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, P0P0P0, P0P1P0, P1P1P0, P1P0P0, oom);
        assertTrue(instance.isIntersectedBy(l, oom));
        //Test 2
        assertFalse(instance.isIntersectedBy(new V3D_Line(N1N1N1, N1N1P1, oom), oom));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Rectangle.
     */
    @Test
    public void testIsIntersectedBy_V3D_LineSegment_boolean() {
        System.out.println("isIntersectedBy");
        int oom = -1;
        V3D_LineSegment l = new V3D_LineSegment(N1N1P0, P1P1P0, oom);
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, P0P0P0, P0P1P0, P1P1P0, P1P0P0, oom);
        assertTrue(instance.isIntersectedBy(l, oom));
        //Test 2
        assertFalse(instance.isIntersectedBy(new V3D_Line(P0N1P1, P0N1N1, oom), oom));
    }

    /**
     * Test of getIntersection method, of class V3D_Rectangle.
     */
    @Test
    public void testGetIntersection_V3D_LineSegment_boolean() {
        System.out.println("getIntersection");
        int oom = -1;
        V3D_LineSegment l = new V3D_LineSegment(N1N1P0, P2P2P0, oom);
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, P0P0P0, P0P1P0, P1P1P0, P1P0P0, oom);
        V3D_Geometry result = instance.getIntersection(l, oom, true);
        V3D_Geometry expResult = new V3D_LineSegment(P0P0P0, P1P1P0, oom);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of isEnvelopeIntersectedBy method, of class V3D_Rectangle.
     */
    @Test
    public void testIsEnvelopeIntersectedBy() {
        System.out.println("isEnvelopeIntersectedBy");
        int oom = -1;
        V3D_Line l = new V3D_LineSegment(N1N1P0, P2P2P0, oom);
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, P0P0P0, P0P1P0, P1P1P0, P1P0P0, oom);
        assertTrue(instance.isEnvelopeIntersectedBy(l, oom));
    }

    /**
     * Test of getPerimeter method, of class V3D_Rectangle.
     */
    @Test
    public void testGetPerimeter() {
        System.out.println("getPerimeter");
        int oom = -1;
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, P0P0P0, P0P1P0, P1P1P0, P1P0P0, oom);
        BigDecimal expResult = BigDecimal.valueOf(4);
        BigDecimal result = instance.getPerimeter(oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getArea method, of class V3D_Rectangle.
     */
    @Test
    public void testGetArea() {
        System.out.println("getArea");
        int oom = 0;
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, P0P0P0, P0P1P0, P1P1P0, P1P0P0, oom);
        BigDecimal expResult = BigDecimal.valueOf(1);
        BigDecimal result = instance.getArea(oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getDistance method, of class V3D_Rectangle.
     */
    @Test
    public void testGetDistance() {
        System.out.println("getDistance");
        int oom = -1;
        V3D_Point p = new V3D_Point(N1N1P0);
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, P0P0P0, P0P1P0, P1P1P0, P1P0P0, oom);
        BigDecimal expResult = new Math_BigRationalSqrt(2, oom).toBigDecimal(oom);
        BigDecimal result = instance.getDistance(p, oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of toStringFields method, of class V3D_Rectangle.
     */
    @Test
    public void testToStringFields() {
        System.out.println("toStringFields");
        String pad = "";
        int oom = -3;
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, P0P0P0, P1P0P0, P1P1P0, P0P1P0, oom);
        String expResult = "offset=V3D_Vector\n"
                + "(\n"
                + " dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + " dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
                + " dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
                + ")\n"
                + ",\n"
                + "oom=-3\n"
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
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, P0P0P0, P1P0P0, P1P1P0, P0P1P0, -3);
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
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, P0P0P0, P1P0P0, P1P1P0, P0P1P0, -3);
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
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, P0P0P0, P1P0P0, P1P1P0, P0P1P0, -3);
        V3D_LineSegment expResult = new V3D_LineSegment(P0P0P0, P1P1P0, P0P1P0, -3);
        V3D_LineSegment result = instance.getRS();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSP method, of class V3D_Rectangle.
     */
    @Test
    public void testGetSP() {
        System.out.println("getSP");
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, P0P0P0, P1P0P0, P1P1P0, P0P1P0, -3);
        V3D_LineSegment expResult = new V3D_LineSegment(P0P0P0, P0P1P0, P0P0P0, -3);
        V3D_LineSegment result = instance.getSP();
        assertEquals(expResult, result);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Rectangle.
     */
    @Test
    public void testIsIntersectedBy_V3D_Line_int() {
        System.out.println("isIntersectedBy");
        int oom = -3;
        V3D_Line l = new V3D_Line(P0P0P0, P0P0P0, P1P0P0, oom);
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, P0P0P0, P1P0P0, P1P1P0, P0P1P0, oom);
        assertTrue(instance.isIntersectedBy(l, oom));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Rectangle.
     */
    @Test
    public void testIsIntersectedBy_3args() {
        System.out.println("isIntersectedBy");
        int oom = -3;
        boolean b = true;
        V3D_LineSegment l = new V3D_LineSegment(P0P0P0, P0P0P0, P1P0P0, oom);
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, P0P0P0, P1P0P0, P1P1P0, P0P1P0, oom);
        assertTrue(instance.isIntersectedBy(l, oom, b));
    }

    /**
     * Test of getIntersection method, of class V3D_Rectangle.
     */
    @Test
    public void testGetIntersection_V3D_Line_int() {
        System.out.println("getIntersection");
        int oom = -3;
        V3D_Line l = new V3D_Line(P0P0P0, P0P0P0, P1P0P0, oom);
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, P0P0P0, P1P0P0, P1P1P0, P0P1P0, oom);
        V3D_Geometry expResult = new V3D_LineSegment(P0P0P0, P0P0P0, P1P0P0, oom);
        //V3D_Geometry expResult = new V3D_LineSegment(P0P0P0, P1P0P0, P1P1P0, oom);
        V3D_Geometry result = instance.getIntersection(l, oom);
        System.out.println(result);
        result = instance.getIntersection(l, oom);
        assertEquals(expResult, result);
    }

    /**
     * Test of getIntersection method, of class V3D_Rectangle.
     */
    @Test
    public void testGetIntersection_3args() {
        System.out.println("getIntersection");
        int oom = -3;
        V3D_LineSegment l = new V3D_LineSegment(P0P0P0, P0P0P0, P1P0P0, oom);
        boolean b = false;
        V3D_Rectangle instance = new V3D_Rectangle(P0P0P0, P0P0P0, P1P0P0, P1P1P0, P0P1P0, oom);
        V3D_Geometry expResult = new V3D_LineSegment(P0P0P0, P0P0P0, P1P0P0, oom);
        V3D_Geometry result = instance.getIntersection(l, oom, b);
        System.out.println(result);
        result = instance.getIntersection(l, oom, b);
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
}
