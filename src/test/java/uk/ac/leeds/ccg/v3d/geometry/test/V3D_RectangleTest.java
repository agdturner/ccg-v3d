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
package uk.ac.leeds.ccg.v3d.geometry.test;

import ch.obermuhlner.math.big.BigRational;
import java.math.RoundingMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.math.geometry.Math_Angle;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Envelope;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Geometry;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Line;
import uk.ac.leeds.ccg.v3d.geometry.V3D_LineSegment;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Plane;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Point;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Ray;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Rectangle;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Triangle;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Vector;

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
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        /*
         * q ----------- r
         * |             |
         * |             |
         * |             |
         * pv ----------- s
         */
        V3D_Rectangle instance;
        V3D_Envelope expResult;
        V3D_Envelope result;
        instance = new V3D_Rectangle(pN1P1P0, pP1P1P0, pP1N1P0, pN1N1P0, oom, rm);
        expResult = new V3D_Envelope(oom, pN1N1P0, pP1P1P0);
        result = instance.getEnvelope(oom);
        assertTrue(expResult.equals(result, oom));
        // Test 2
        instance = new V3D_Rectangle(pN1P1P1, pP1P1P0, pP1N1P0, pN1N1P1, oom, rm);
        expResult = new V3D_Envelope(oom, pN1N1P0, pP1P1P1);
        result = instance.getEnvelope(oom);
        assertTrue(expResult.equals(result, oom));
        // Test 3
        instance = new V3D_Rectangle(pN1P1P1, pP1P1N1, pP1N1N1, pN1N1P1, oom, rm);
        expResult = new V3D_Envelope(oom, pN1N1N1, pP1P1P1);
        result = instance.getEnvelope(oom);
        assertTrue(expResult.equals(result, oom));
        // Test 4
        instance = new V3D_Rectangle(pN1N1N1, pP1N1N1, pP1P1N1, pN1P1N1, oom, rm);
        expResult = new V3D_Envelope(oom, pN1N1N1, pP1P1N1);
        result = instance.getEnvelope(oom);
        assertTrue(expResult.equals(result, oom));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Rectangle.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point_int() {
        System.out.println("isIntersectedBy");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point pt = pP0P0P0;
        V3D_Rectangle instance = new V3D_Rectangle(pN1P1P0, pP1P1P0, pP1N1P0, pN1N1P0, oom, rm);
        assertTrue(instance.isIntersectedBy(pt, oom, rm));
        // Test 2
        instance = new V3D_Rectangle(pN1P0P0, pP0P1P0, pP1P0P0, pP0N1P0, oom, rm);
        assertTrue(instance.isIntersectedBy(pt, oom, rm));
        // Test 3
        BigRational half = BigRational.ONE.divide(2);
        pt = new V3D_Point(half, half, P0);
        assertTrue(instance.isIntersectedBy(pt, oom, rm));
        // Test 4
        BigRational epsilon = BigRational.valueOf("0.00000000001");
        BigRational halfpe = half.add(epsilon);
        BigRational halfne = half.subtract(epsilon);
        oom = -12;
        pt = new V3D_Point(halfpe, half, P0);
        assertFalse(instance.isIntersectedBy(pt, oom, rm));
        // Test 5
        oom = -12;
        pt = new V3D_Point(halfpe.negate(), half, P0);
        assertFalse(instance.isIntersectedBy(pt, oom, rm));
        // Test 6
        oom = -12;
        pt = new V3D_Point(half, halfpe, P0);
        assertFalse(instance.isIntersectedBy(pt, oom, rm));
        // Test 7
        oom = -12;
        pt = new V3D_Point(half, halfpe.negate(), P0);
        assertFalse(instance.isIntersectedBy(pt, oom, rm));
        // Test 8
        oom = -12;
        pt = new V3D_Point(halfne, half, P0);
        assertTrue(instance.isIntersectedBy(pt, oom, rm));
        // Test 9
        oom = -12;
        pt = new V3D_Point(halfne.negate(), half, P0);
        assertTrue(instance.isIntersectedBy(pt, oom, rm));
        // Test 10
        oom = -12;
        pt = new V3D_Point(half, halfne, P0);
        assertTrue(instance.isIntersectedBy(pt, oom, rm));
        // Test 11
        oom = -12;
        pt = new V3D_Point(half, halfne.negate(), P0);
        assertTrue(instance.isIntersectedBy(pt, oom, rm));
    }

    /**
     * Test of getIntersection method, of class V3D_Rectangle.
     */
    @Test
    public void testGetIntersection() {
        System.out.println("getIntersection");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line l = new V3D_Line(pP0P0N1, pP0P0P1, oom, rm);
        V3D_Rectangle instance;
        V3D_Geometry expResult;
        V3D_Geometry result;
        instance = new V3D_Rectangle(pN1P1P0, pP1P1P0, pP1N1P0, pN1N1P0, oom, rm);
        expResult = pP0P0P0;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
    }

    /**
     * Test of toString method, of class V3D_Rectangle.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0, oom, rm);
        String expResult = """
                           V3D_Rectangle(
                           offset=V3D_Vector(dx=0, dy=0, dz=0),
                           pqr=V3D_Triangle(
                            pl=( V3D_Plane(
                             offset=V3D_Vector(dx=0, dy=0, dz=0),
                             p=  V3D_Vector(dx=0, dy=0, dz=0),
                             n=  V3D_Vector(dx=0, dy=0, dz=-1))),
                            offset=(V3D_Vector(dx=0, dy=0, dz=0)),
                            p=(V3D_Vector(dx=0, dy=0, dz=0)),
                            q=(V3D_Vector(dx=0, dy=1, dz=0)),
                            r=(V3D_Vector(dx=1, dy=1, dz=0))),
                           rsp=V3D_Triangle(
                            pl=( V3D_Plane(
                             offset=V3D_Vector(dx=0, dy=0, dz=0),
                             p=  V3D_Vector(dx=0, dy=0, dz=0),
                             n=  V3D_Vector(dx=0, dy=0, dz=-1))),
                            offset=(V3D_Vector(dx=0, dy=0, dz=0)),
                            p=(V3D_Vector(dx=1, dy=1, dz=0)),
                            q=(V3D_Vector(dx=1, dy=0, dz=0)),
                            r=(V3D_Vector(dx=0, dy=0, dz=0)))
                           )""";
        String result = instance.toString();
        //System.out.println(result);
        assertTrue(expResult.equalsIgnoreCase(result));
    }

    /**
     * Test of translate method, of class V3D_Rectangle.
     */
    @Test
    public void testApply() {
        // No test.
    }

//    /**
//     * Test of isIntersectedBy method, of class V3D_Rectangle.
//     */
//    @Test
//    public void testIsIntersectedBy_V3D_Line() {
//        System.out.println("isIntersectedBy");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_Line l = new V3D_Line(pN1N1N1, pP2P2P2, oom, rm);
//        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0, oom, rm);
//        assertTrue(instance.isIntersectedBy(l, oom, rm));
//        //Test 2
//        assertFalse(instance.isIntersectedBy(new V3D_Line(pN1N1N1, pN1N1P1, oom, rm), oom, rm));
//    }
//
//    /**
//     * Test of isIntersectedBy method, of class V3D_Rectangle.
//     */
//    @Test
//    public void testIsIntersectedBy_V3D_LineSegment_boolean() {
//        System.out.println("isIntersectedBy");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_LineSegment l = new V3D_LineSegment(pN1N1P0, pP1P1P0, oom, rm);
//        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0, oom, rm);
//        assertTrue(instance.isIntersectedBy(l, oom, rm));
//        //Test 2
//        assertFalse(instance.isIntersectedBy(new V3D_Line(pP0N1P1, pP0N1N1, oom, rm), oom, rm));
//    }
    /**
     * Test of getIntersection method, of class V3D_Rectangle.
     */
    @Test
    public void testGetIntersection_V3D_LineSegment_boolean() {
        System.out.println("getIntersection");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_LineSegment l = new V3D_LineSegment(pN1N1P0, pP2P2P0, oom, rm);
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0, oom, rm);
        V3D_Geometry result = instance.getIntersection(l, oom, rm);
        V3D_Geometry expResult = new V3D_LineSegment(pP0P0P0, pP1P1P0, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
    }

//    /**
//     * Test of isEnvelopeIntersectedBy method, of class V3D_Rectangle.
//     */
//    @Test
//    public void testIsEnvelopeIntersectedBy() {
//        System.out.println("isEnvelopeIntersectedBy");
//        V3D_Line l = new V3D_LineSegment(pN1N1P0, pP2P2P0);
//        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
//        assertTrue(instance.isEnvelopeIntersectedBy(l, oom));
//    }
    /**
     * Test of getPerimeter method, of class V3D_Rectangle.
     */
    @Test
    public void testGetPerimeter() {
        System.out.println("getPerimeter");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0, oom, rm);
        BigRational expResult = BigRational.valueOf(4);
        BigRational result = instance.getPerimeter(oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getArea method, of class V3D_Rectangle.
     */
    @Test
    public void testGetArea() {
        System.out.println("getArea");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0, oom, rm);
        BigRational expResult = BigRational.ONE;
        BigRational result = instance.getArea(oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getDistance method, of class V3D_Rectangle.
     */
    @Test
    public void testGetDistance() {
        System.out.println("getDistance");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point p = pN1N1P0;
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0, oom, rm);
        BigRational expResult = new Math_BigRationalSqrt(2, oom, rm).getSqrt(oom, rm);
        BigRational result = instance.getDistance(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

//    /**
//     * Test of toStringFields method, of class V3D_Rectangle.
//     */
//    @Test
//    public void testToStringFields() {
//        System.out.println("toStringFields");
//        String pad = "";
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0, oom, rm);
//        String expResult = """
//                           
//                           offset=V3D_Vector
//                           (
//                            dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),
//                            dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),
//                            dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)
//                           ),
//                           p=V3D_Vector
//                           (
//                            dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),
//                            dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),
//                            dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)
//                           ),
//                           q=V3D_Vector
//                           (
//                            dx=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),
//                            dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),
//                            dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)
//                           ),
//                           r=V3D_Vector
//                           (
//                            dx=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),
//                            dy=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),
//                            dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)
//                           ),
//                           s=V3D_Vector
//                           (
//                            dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),
//                            dy=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),
//                            dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)
//                           )""";
//        String result = instance.toStringFields(pad);
//        //System.out.println(result);
//        assertEquals(expResult, result);
//    }

//    /**
//     * Test of getSV method, of class V3D_Rectangle.
//     */
//    @Test
//    public void testGetSV() {
//        System.out.println("getSV");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0, oom, rm);
//        V3D_Vector expResult = P0P1P0;
//        V3D_Vector result = instance.s;
//        assertTrue(expResult.equals(result, oom, rm));
//    }

    /**
     * Test of getS method, of class V3D_Rectangle.
     */
    @Test
    public void testGetS() {
        System.out.println("getS");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0, oom, rm);
        V3D_Point expResult = pP0P1P0;
        V3D_Point result = instance.getS();
        assertTrue(expResult.equals(result, oom, rm));
    }

//    /**
//     * Test of getRS method, of class V3D_Rectangle.
//     */
//    @Test
//    public void testGetRS() {
//        System.out.println("getRS");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0, oom, rm);
//        V3D_LineSegment expResult = new V3D_LineSegment(pP1P1P0, pP0P1P0, oom, rm);
//        V3D_LineSegment result = instance.getRS(oom, rm);
//        assertTrue(expResult.equals(result, oom, rm));
//    }

//    /**
//     * Test of getSP method, of class V3D_Rectangle.
//     */
//    @Test
//    public void testGetSP() {
//        System.out.println("getSP");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0, oom, rm);
//        V3D_LineSegment expResult = new V3D_LineSegment(pP0P1P0, pP0P0P0, oom, rm);
//        V3D_LineSegment result = instance.getSP(oom, rm);
//        assertTrue(expResult.equals(result, oom, rm));
//    }

//    /**
//     * Test of isIntersectedBy method, of class V3D_Rectangle.
//     */
//    @Test
//    public void testIsIntersectedBy_V3D_Line_int() {
//        System.out.println("isIntersectedBy");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_Line l = new V3D_Line(pP0P0P0, pP1P0P0, oom, rm);
//        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0, oom, rm);
//        assertTrue(instance.isIntersectedBy(l, oom, rm));
//    }
//
//    /**
//     * Test of isIntersectedBy method, of class V3D_Rectangle.
//     */
//    @Test
//    public void testIsIntersectedBy_3args() {
//        System.out.println("isIntersectedBy");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_LineSegment l = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
//        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0, oom, rm);
//        assertTrue(instance.isIntersectedBy(l, oom, rm));
//    }
    /**
     * Test of getIntersection method, of class V3D_Rectangle.
     */
    @Test
    public void testGetIntersection_V3D_Line_int() {
        System.out.println("getIntersection");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line l = new V3D_Line(pP0P0P0, pP1P0P0, oom, rm);
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0, oom, rm);
        V3D_Geometry expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        //V3D_Geometry expResult = new V3D_LineSegment(P0P0P0, P1P0P0, P1P1P0, oom, rm);
        V3D_Geometry result = instance.getIntersection(l, oom, rm);
        //System.out.println(result);
        //result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equals((V3D_LineSegment) result, oom, rm));
    }

    /**
     * Test of getIntersection method, of class V3D_Rectangle.
     */
    @Test
    public void testGetIntersection_3args() {
        System.out.println("getIntersection");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_LineSegment l = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0, oom, rm);
        V3D_Geometry expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        V3D_Geometry result;
        //result = instance.getIntersection(l, oom, rm);
        //System.out.println(result);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
    }

    /**
     * Test of getRSP method, of class V3D_Rectangle.
     */
    @Test
    public void testGetRSP() {
        System.out.println("getRSP");
        // No test
    }

    /**
     * Test of getPQR method, of class V3D_Rectangle.
     */
    @Test
    public void testGetPQR() {
        System.out.println("getPQR");
        // No test
    }

    /**
     * Test of isIntersectedBy0 method, of class V3D_Rectangle.
     */
    @Test
    public void testIsIntersectedBy0() {
        System.out.println("isIntersectedBy0");
        // No test
    }

//    /**
//     * Test of isIntersectedBy method, of class V3D_Rectangle.
//     */
//    @Test
//    public void testIsIntersectedBy_V3D_Ray_int() {
//        System.out.println("isIntersectedBy");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_Ray ray = new V3D_Ray(pP0P0P0, pP1P0P0, oom, rm);
//        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0, oom, rm);
//        boolean expResult = true;
//        boolean result = instance.isIntersectedBy(ray, oom, rm);
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of isIntersectedBy method, of class V3D_Rectangle.
//     */
//    @Test
//    public void testIsIntersectedBy_V3D_LineSegment_int() {
//        System.out.println("isIntersectedBy");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_LineSegment l = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
//        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0, oom, rm);
//        boolean expResult = true;
//        boolean result = instance.isIntersectedBy(l, oom, rm);
//        assertEquals(expResult, result);
//    }
    /**
     * Test of join method, of class V3D_Rectangle covered by
     * {@link #testGetIntersection_V3D_LineSegment_int()} and
     * {@link #testGetIntersection_V3D_Line_int()}.
     */
    @Test
    public void testJoin() {
        System.out.println("join");
        // No test.
    }

    /**
     * Test of getIntersection method, of class V3D_Rectangle.
     */
    @Test
    public void testGetIntersection_V3D_LineSegment_int() {
        System.out.println("getIntersection");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_LineSegment l = new V3D_LineSegment(pP1N1P0, pP1P2P0, oom, rm);
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP2P1P0, pP2P0P0, oom, rm);
        V3D_Geometry expResult = new V3D_LineSegment(pP1P1P0, pP1P0P0, oom, rm);
        V3D_Geometry result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
    }

    /**
     * Test of getDistance method, of class V3D_Rectangle.
     */
    @Test
    public void testGetDistance_V3D_Point_int() {
        System.out.println("getDistance");
        V3D_Point p = pP0P0P0;
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP2P1P0, pP2P0P0, oom, rm);
        BigRational expResult = BigRational.ZERO;
        BigRational result = instance.getDistance(p, oom, rm);
        assertEquals(expResult, result);
        // Test 2
        p = pN1P0P0;
        expResult = BigRational.ONE;
        result = instance.getDistance(p, oom, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Rectangle.
     */
    @Test
    public void testGetDistanceSquared_V3D_Point_int() {
        System.out.println("getDistanceSquared");
        V3D_Point p = pP0P0P0;
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0, oom, rm);
        BigRational expResult = BigRational.ZERO;
        BigRational result = instance.getDistanceSquared(p, oom, rm);
        assertEquals(expResult, result);
        // Test 2
        p = pN1N1P0;
        expResult = BigRational.TWO;
        result = instance.getDistanceSquared(p, oom, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of setOffset method, of class V3D_Rectangle.
     */
    @Test
    public void testSetOffset() {
        System.out.println("setOffset");
        // No test.
//        V3D_Vector offset = P1P1P1;
//        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
//        instance.setOffset(offset);
//        V3D_Rectangle instance2 = new V3D_Rectangle(pP1P1P1, pP1P2P1, pP2P2P1, pP2P1P1);
//        assertEquals(instance, instance2);
    }

    /**
     * Test of translate method, of class V3D_Rectangle.
     */
    @Test
    public void testTranslate() {
        System.out.println("translate");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Vector v = P1P1P1;
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0, oom, rm);
        instance.translate(v, oom, rm);
        V3D_Rectangle instance2 = new V3D_Rectangle(pP1P1P1, pP1P2P1, pP2P2P1, pP2P1P1, oom, rm);
        assertTrue(instance.equals(instance2, oom, rm));
    }

    /**
     * Test of rotate method, of class V3D_Rectangle.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        Math_Angle ma = new Math_Angle();
        V3D_Ray xaxis = new V3D_Ray(pP0P0P0, V3D_Vector.I);
        BigRational theta = BigRational.ZERO;
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0, oom, rm);
        instance.rotate(xaxis, xaxis.l.v, ma, theta, oom, rm);
        V3D_Rectangle instance2 = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0, oom, rm);
        assertTrue(instance.equals(instance2, oom, rm));
    }

//    /**
//     * Test of isIntersectedBy method, of class V3D_Rectangle.
//     */
//    @Test
//    public void testIsIntersectedBy_V3D_Plane_int() {
//        System.out.println("isIntersectedBy");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_Plane pv = new V3D_Plane(pP0P0P0, pP1P0P0, pP1P1P0, oom, rm);
//        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0, oom, rm);
//        assertTrue(instance.isIntersectedBy(pv, oom, rm));
//    }
//
//    /**
//     * Test of isIntersectedBy method, of class V3D_Rectangle.
//     */
//    @Test
//    public void testIsIntersectedBy_V3D_Triangle_int() {
//        System.out.println("isIntersectedBy");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_Triangle t = new V3D_Triangle(pP0P0P0, pP0P1P0, pP1P1P0, oom, rm);
//        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0, oom, rm);
//        boolean expResult = true;
//        boolean result = instance.isIntersectedBy(t, oom, rm);
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of isIntersectedBy method, of class V3D_Rectangle.
//     */
//    @Test
//    public void testIsIntersectedBy_V3D_Tetrahedron_int() {
//        System.out.println("isIntersectedBy");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_Tetrahedron t = new V3D_Tetrahedron(pP0P0P0, pP0P1P0, pP1P1P0, pP0P0P1, oom, rm);
//        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0, oom, rm);
//        boolean expResult = true;
//        boolean result = instance.isIntersectedBy(t, oom, rm);
//        assertEquals(expResult, result);
//    }
    /**
     * Test of getIntersection method, of class V3D_Rectangle.
     */
    @Test
    public void testGetIntersection_V3D_Plane_int() {
        System.out.println("getIntersection");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Plane p = new V3D_Plane(pP0P0P0, pP0P1P0, pP1P1P0, oom, rm);
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0, oom, rm);
        V3D_Geometry expResult = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0, oom, rm);
        V3D_Geometry result = instance.getIntersection(p, oom, rm);
        assertTrue(((V3D_Rectangle) expResult).equals((V3D_Rectangle) result, oom, rm));
    }

    /**
     * Test of getIntersection method, of class V3D_Rectangle.
     */
    @Test
    public void testGetIntersection_V3D_Triangle_int_RoundingMode() {
        System.out.println("getIntersection");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Triangle t = new V3D_Triangle(pP0P0P0, pP0P1P0, pP1P1P0, oom, rm);
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0, oom, rm);
        V3D_Geometry expResult = new V3D_Triangle(pP0P0P0, pP0P1P0, pP1P1P0, oom, rm);
        V3D_Geometry result = instance.getIntersection(t, oom, rm);
        assertTrue(((V3D_Triangle) expResult).equals((V3D_Triangle) result, oom, rm));
    }

    /**
     * Test of getIntersection method, of class V3D_Rectangle.
     */
    @Test
    public void testGetIntersection_V3D_Tetrahedron_int() {
//        System.out.println("getIntersection");
//        V3D_Tetrahedron t = new V3D_Tetrahedron(pP0P0P0, pP0P1P0, pP1P1P0, pP0P0P1);
//        int oom = -3;
//        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
//        V3D_Geometry expResult = new V3D_Triangle(pP0P0P0, pP0P1P0, pP1P1P0);
//        V3D_Geometry result = instance.getIntersection(t, oom);
//        assertEquals(expResult, result);
    }

    /**
     * Test of getDistance method, of class V3D_Rectangle.
     */
    @Test
    public void testGetDistance_V3D_Line_int() {
        System.out.println("getDistance");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line l = new V3D_Line(pP0N1P1, pP1N1P1, oom, rm);
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0, oom, rm);
        BigRational expResult = BigRational.ONE;
        BigRational result = instance.getDistance(l, oom, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Rectangle.
     */
    @Test
    public void testGetDistanceSquared_V3D_Line_int() {
        System.out.println("getDistanceSquared");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line l = new V3D_Line(pP0N1P1, pP1N1P1, oom, rm);
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0, oom, rm);
        BigRational expResult = BigRational.ONE;
        BigRational result = instance.getDistanceSquared(l, oom, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistance method, of class V3D_Rectangle.
     */
    @Test
    public void testGetDistance_V3D_LineSegment_int() {
        System.out.println("getDistance");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_LineSegment l = new V3D_LineSegment(pP0N1P0, pP1N1P0, oom, rm);
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0, oom, rm);
        BigRational expResult = BigRational.ONE;
        BigRational result = instance.getDistance(l, oom, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Rectangle.
     */
    @Test
    public void testGetDistanceSquared_V3D_LineSegment_int() {
        System.out.println("getDistanceSquared");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_LineSegment l = new V3D_LineSegment(pP0N1P0, pP1N1P0, oom, rm);
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0, oom, rm);
        BigRational expResult = BigRational.ONE;
        BigRational result = instance.getDistanceSquared(l, oom, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistance method, of class V3D_Rectangle.
     */
    @Test
    public void testGetDistance_V3D_Plane_int() {
        System.out.println("getDistance");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Plane p = new V3D_Plane(pP0N1P0, pP1N1P0, pP0N1P1, oom, rm);
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0, oom, rm);
        BigRational expResult = BigRational.ONE;
        BigRational result = instance.getDistance(p, oom, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Rectangle.
     */
    @Test
    public void testGetDistanceSquared_V3D_Plane_int() {
        System.out.println("getDistanceSquared");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Plane p = new V3D_Plane(pP0N1P0, pP1N1P0, pP0N1P1, oom, rm);
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0, oom, rm);
        BigRational expResult = BigRational.ONE;
        BigRational result = instance.getDistanceSquared(p, oom, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistance method, of class V3D_Rectangle.
     */
    @Test
    public void testGetDistance_V3D_Triangle_int() {
        System.out.println("getDistance");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Triangle t = new V3D_Triangle(pP0N1P0, pP1N1P0, pP0N1P1, oom, rm);
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0, oom, rm);
        BigRational expResult = BigRational.ONE;
        BigRational result = instance.getDistance(t, oom, rm);
        assertEquals(expResult, result);
        // Test 2
        t = new V3D_Triangle(pP0N1P0, pP1N1P0, pP1P1P0, oom, rm);
        expResult = BigRational.ZERO;
        result = instance.getDistance(t, oom, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Rectangle.
     */
    @Test
    public void testGetDistanceSquared_V3D_Triangle_int() {
        System.out.println("getDistanceSquared");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Triangle t = new V3D_Triangle(pP0P0P0, pP0P1P0, pP1P1P0, oom, rm);
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0, oom, rm);
        BigRational expResult = BigRational.ZERO;
        BigRational result = instance.getDistanceSquared(t, oom, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class V3D_Rectangle.
     */
    @Test
    public void testEquals_V3D_Rectangle_int_RoundingMode() {
        System.out.println("equals");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Rectangle r = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0, oom, rm);
        V3D_Rectangle instance = new V3D_Rectangle(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0, oom, rm);
        assertTrue(instance.equals(r, oom, rm));
        // Test 2
        instance = new V3D_Rectangle(pP0P1P0, pP1P1P0, pP1P0P0, pP0P0P0, oom, rm);
        assertTrue(instance.equals(r, oom, rm));
        // Test 3
        instance = new V3D_Rectangle(pP1P1P0, pP1P0P0, pP0P0P0, pP0P1P0, oom, rm);
        assertTrue(instance.equals(r, oom, rm));
        // Test 4
        instance = new V3D_Rectangle(pP1P0P0, pP0P0P0, pP0P1P0, pP1P1P0, oom, rm);
        assertTrue(instance.equals(r, oom, rm));
        // Test 5
        instance = new V3D_Rectangle(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0, oom, rm);
        assertTrue(instance.equals(r, oom, rm));
        // Test 6
        instance = new V3D_Rectangle(pP1P0P0, pP1P1P0, pP0P1P0, pP0P0P0, oom, rm);
        assertTrue(instance.equals(r, oom, rm));
        // Test 7
        instance = new V3D_Rectangle(pP1P1P0, pP0P1P0, pP0P0P0, pP1P0P0, oom, rm);
        assertTrue(instance.equals(r, oom, rm));
        // Test 8
        instance = new V3D_Rectangle(pP0P1P0, pP0P0P0, pP1P0P0, pP1P1P0, oom, rm);
        assertTrue(instance.equals(r, oom, rm));
    }

    /**
     * Test of isRectangle method, of class V3D_Rectangle.
     */
    @Test
    public void testIsRectangle() {
        System.out.println("isRectangle");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point p = pP0P0P0;
        V3D_Point q = pP1P0P0;
        V3D_Point r = pP1P1P0;
        V3D_Point s = pP0P1P0;
        boolean expResult = true;
        boolean result = V3D_Rectangle.isRectangle(p, q, r, s, oom, rm);
        assertEquals(expResult, result);
        // Test 2
        expResult = true;
        result = V3D_Rectangle.isRectangle(p, s, r, q, oom, rm);
        assertEquals(expResult, result);
        // Test 2
        p = pN1P0P0;
        expResult = false;
        result = V3D_Rectangle.isRectangle(p, q, r, s, oom, rm);
        assertEquals(expResult, result);
    }
}
