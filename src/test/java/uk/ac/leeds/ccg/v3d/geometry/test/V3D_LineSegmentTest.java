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
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Envelope;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Geometry;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Line;
import uk.ac.leeds.ccg.v3d.geometry.V3D_LineSegment;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Point;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Vector;

/**
 * Test of V3D_LineSegment class.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_LineSegmentTest extends V3D_Test {

    public V3D_LineSegmentTest() {
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
//        V3D_LineSegment instance = new V3D_LineSegment(pP0P0P0, pP1P0P0);
//        String expResult = "V3D_LineSegment\n"
//                + "(\n"
//                + " oom=-3\n"
//                + " ,\n"
//                + " offset=V3D_Vector\n"
//                + " (\n"
//                + "  dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
//                + "  dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
//                + "  dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
//                + " )\n"
//                + " ,\n"
//                + " p=V3D_Vector\n"
//                + " (\n"
//                + "  dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
//                + "  dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
//                + "  dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
//                + " )\n"
//                + " ,\n"
//                + " q=V3D_Vector\n"
//                + " (\n"
//                + "  dx=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),\n"
//                + "  dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),\n"
//                + "  dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)\n"
//                + " )\n"
//                + " ,\n"
//                + " v=null\n"
//                + ")";
//        String result = instance.toString();
//        System.out.println(result);
//        assertTrue(expResult.equalsIgnoreCase(result));
    }

    /**
     * Test of getEnvelope method, of class V3D_LineSegment.
     */
    @Test
    public void testGetEnvelope() {
        System.out.println("getEnvelope");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_LineSegment instance = new V3D_LineSegment(pP0P0P0, pP1P1P0, oom, rm);
        V3D_Envelope expResult = new V3D_Envelope(oom, pP0P0P0, pP1P1P0);
        V3D_Envelope result = instance.getEnvelope(oom);
        assertTrue(expResult.equals(result, oom));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_LineSegment.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point() {
        System.out.println("isIntersectedBy");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point p = pP0P0P0;
        V3D_LineSegment instance = new V3D_LineSegment(pN1N1N1, pP1P1P1, oom, rm);
        assertTrue(instance.isIntersectedBy(p, oom, rm));
        // Test2
        p = pP1P1P1;
        instance = new V3D_LineSegment(pN1N1N1, pP1P1P1, oom, rm);
        assertTrue(instance.isIntersectedBy(p, oom, rm));
    }

    /**
     * Test of equals method, of class V3D_LineSegment.
     */
    @Test
    public void testEquals_V3D_LineSegment_int_RoundingMode() {
        System.out.println("equals");
        int oom = -1;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_LineSegment l = new V3D_LineSegment(pP0P0P0, pP1P1P0, oom, rm);
        V3D_LineSegment instance = new V3D_LineSegment(pP0P0P0, pP1P1P0, oom, rm);
        assertTrue(instance.equals(l, oom, rm));
        // Test 2
        instance = new V3D_LineSegment(pP1P1P0, pP0P0P0, oom, rm);
        assertFalse(instance.equals(l, oom, rm));
    }

    /**
     * Test of equalsIgnoreDirection method, of class V3D_LineSegment.
     */
    @Test
    public void testEqualsIgnoreDirection() {
        System.out.println("equalsIgnoreDirection");
        int oom = -1;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_LineSegment l = new V3D_LineSegment(pP0P0P0, pP1P1P0, oom, rm);
        V3D_LineSegment instance = new V3D_LineSegment(pP0P0P0, pP2P2P0, oom, rm);
        assertFalse(instance.equalsIgnoreDirection(l, oom, rm));
        // Test 2
        instance = new V3D_LineSegment(pP1P1P0, pP0P0P0, oom, rm);
        assertTrue(instance.equalsIgnoreDirection(l, oom, rm));
        // Test 3
        instance = new V3D_LineSegment(pP0P0P0, pP1P1P0, oom, rm);
        assertTrue(instance.equalsIgnoreDirection(l, oom, rm));
    }

    /**
     * Test of multiply method, of class V3D_LineSegment.
     */
    @Test
    public void testTranslate() {
        System.out.println("translate");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Vector v = V3D_Vector.I;
        V3D_LineSegment instance = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        V3D_LineSegment expResult = new V3D_LineSegment(pP1P0P0, pP2P0P0, oom, rm);
        instance.translate(v, oom, rm);
        assertTrue(expResult.equalsIgnoreDirection(instance, oom, rm));
        // Test 2
        instance = new V3D_LineSegment(pP0P0P0, pP0P1P0, oom, rm);
        expResult = new V3D_LineSegment(pP1P0P0, pP1P1P0, oom, rm);
        instance.translate(v, oom, rm);
        assertTrue(expResult.equalsIgnoreDirection(instance, oom, rm));
        // Test 3
        instance = new V3D_LineSegment(pP0P0P0, pP0P0P1, oom, rm);
        expResult = new V3D_LineSegment(pP1P0P0, pP1P0P1, oom, rm);
        instance.translate(v, oom, rm);
        assertTrue(expResult.equalsIgnoreDirection(instance, oom, rm));
    }

//    /**
//     * Test of isIntersectedBy method, of class V3D_LineSegment.
//     */
//    @Test
//    public void testIsIntersectedBy_V3D_LineSegment_boolean() {
//        System.out.println("isIntersectedBy");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_LineSegment l = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
//        V3D_LineSegment instance = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
//        assertTrue(instance.isIntersectedBy(l, oom, rm));
//        // Test 2
//        instance = new V3D_LineSegment(pP0P0P0, pP1P1P1, oom, rm);
//        assertTrue(instance.isIntersectedBy(l, oom, rm));
//        // Test 3
//        instance = new V3D_LineSegment(pN1N1N1, pN1N1P0, oom, rm);
//        assertFalse(instance.isIntersectedBy(l, oom, rm));
//        // Test 4
//        l = new V3D_LineSegment(pN1N1P0, pP1P1P0, oom, rm);
//        instance = new V3D_LineSegment(pN1N1N1, pP1P1P1, oom, rm);
//        assertTrue(instance.isIntersectedBy(l, oom, rm));
//    }
//
//    /**
//     * Test of isIntersectedBy method, of class V3D_LineSegment.
//     */
//    @Test
//    public void testIsIntersectedBy_V3D_Line() {
//        System.out.println("isIntersectedBy");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_LineSegment l = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
//        V3D_LineSegment instance = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
//        assertTrue(instance.isIntersectedBy(l, oom, rm));
//        // Test 2
//        instance = new V3D_LineSegment(pP0P0P0, pP1P1P1, oom, rm);
//        assertTrue(instance.isIntersectedBy(l, oom, rm));
//        // Test 3
//        instance = new V3D_LineSegment(pN1N1N1, pN1N1P0, oom, rm);
//        assertFalse(instance.isIntersectedBy(l, oom, rm));
//        // Test 4
//        l = new V3D_LineSegment(pN1N1P0, pP1P1P0, oom, rm);
//        instance = new V3D_LineSegment(pN1N1N1, pP1P1P1, oom, rm);
//        assertTrue(instance.isIntersectedBy(l, oom, rm));
//    }
    /**
     * Test of getIntersection method, of class V3D_LineSegment.
     */
    @Test
    public void testGetIntersection_V3D_Line() {
        System.out.println("getIntersection");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line l = new V3D_Line(pP0P0P0, pP1P0P0, oom, rm);
        V3D_LineSegment instance = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        V3D_Geometry expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        V3D_Geometry result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
        // Test 2
        instance = new V3D_LineSegment(pP0P0P0, pP1P1P1, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        expResult = pP0P0P0;
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 3
        instance = new V3D_LineSegment(pN1N1N1, pN1N1P0, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertNull(result);
        // Test 4
        l = new V3D_Line(pN1N1P0, pP1P1P0, oom, rm);
        instance = new V3D_LineSegment(pN1N1N1, pP1P1P1, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        expResult = pP0P0P0;
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 5
        l = new V3D_Line(pP0P0P0, pP1P0P0, oom, rm);
        instance = new V3D_LineSegment(pN1P0P0, pP1P0P0, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        expResult = new V3D_LineSegment(pN1P0P0, pP1P0P0, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
    }

    /**
     * Test of getIntersection method, of class V3D_LineSegment.
     */
    @Test
    public void testGetIntersection_V3D_LineSegment_int_RoundingMode() {
        System.out.println("getIntersection");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_LineSegment l = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        V3D_LineSegment instance = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        V3D_Geometry expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        V3D_Geometry result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
        // Test 2
        instance = new V3D_LineSegment(pP0P0P0, pP1P1P1, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        expResult = pP0P0P0;
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 3
        instance = new V3D_LineSegment(pN1N1N1, pN1N1P0, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertNull(result);
        // Test 4
        l = new V3D_LineSegment(pN1N1P0, pP1P1P0, oom, rm);
        instance = new V3D_LineSegment(pN1N1N1, pP1P1P1, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        expResult = pP0P0P0;
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 5
        l = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        instance = new V3D_LineSegment(pN1P0P0, pP1P0P0, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
    }

    /**
     * Test of isEnvelopeIntersectedBy method, of class V3D_LineSegment.
     */
    @Test
    public void testIsEnvelopeIntersectedBy() {
        // No test as this is covered by V3D_EnvelopeTest.testIsIntersectedBy_V3D_LineSegment_boolean()
    }

    /**
     * Test of getLength method, of class V3D_LineSegment covered by
     * {@link #testGetLength2()}.
     */
    @Test
    public void testGetLength() {
        System.out.println("getLength");
    }

    /**
     * Test of getLength2 method, of class V3D_LineSegment.
     */
    @Test
    public void testGetLength2() {
        System.out.println("getLength2");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_LineSegment instance;
        BigRational expResult;
        BigRational result;
        // Test 1
        instance = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        expResult = BigRational.ONE;
        result = instance.getLength2(oom, rm);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_LineSegment(pP0P0P0, pP2P0P0, oom, rm);
        expResult = BigRational.valueOf(4);
        result = instance.getLength2(oom, rm);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getDistance method, of class V3D_LineSegment covered by
     * {@link #testGetDistanceSquared_V3D_Point_int()}.
     */
    @Test
    public void testGetDistance_V3D_Point() {
        System.out.println("getDistance");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_LineSegment l = new V3D_LineSegment(pP0P0P0, pP2P0P0, oom, rm);
        V3D_Point instance = pP1P1P0;
        BigRational expResult = BigRational.ONE;
        BigRational result = l.getDistance(instance, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 2
        instance = pN1N1P0;
        result = l.getDistance(instance, oom, rm);
        expResult = new Math_BigRationalSqrt(2, oom, rm).getSqrt(oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 3
        instance = pP2P2P0;
        expResult = BigRational.valueOf(2);
        result = l.getDistance(instance, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 4
        instance = pP2P2P0;
        l = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        expResult = new Math_BigRationalSqrt(5, oom, rm).getSqrt(oom, rm);
        result = l.getDistance(instance, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getDistance method, of class V3D_LineSegment.
     */
    @Test
    public void testGetDistanceSquared_V3D_Point() {
        System.out.println("getDistance");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_LineSegment instance;
        V3D_Point p;
        BigRational expResult;
        BigRational result;
        // Test 1
        instance = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        p = pP0P0P0;
        expResult = BigRational.ZERO;
        result = instance.getDistanceSquared(p, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 2
        p = pP1P0P0;
        expResult = BigRational.ZERO;
        result = instance.getDistanceSquared(p, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 3
        instance = new V3D_LineSegment(pP0P0P0, pP2P0P0, oom, rm);
        p = pP1P0P0;
        expResult = BigRational.ZERO;
        result = instance.getDistanceSquared(p, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 4
        p = pP1P0P1;
        expResult = BigRational.ONE;
        result = instance.getDistanceSquared(p, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 5
        p = pN1P0P1;
        expResult = BigRational.valueOf(2);
        result = instance.getDistanceSquared(p, oom, rm);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getDistance method, of class V3D_LineSegment covered by
     * {@link #testGetDistanceSquared_V3D_LineSegment_int()}.
     */
    @Test
    public void testGetDistance_V3D_LineSegment() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistance method, of class V3D_LineSegment.
     */
    @Test
    public void testGetDistanceSquared_V3D_LineSegment() {
        System.out.println("getDistanceSquared");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_LineSegment l;
        V3D_LineSegment instance;
        BigRational expResult;
        BigRational result;
        // Test 1
        l = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        instance = new V3D_LineSegment(pP0P1P0, pP1P1P0, oom, rm);
        expResult = BigRational.ONE;
        result = instance.getDistanceSquared(l, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 2
        l = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        instance = new V3D_LineSegment(pN1P0P0, pN1P1P0, oom, rm);
        expResult = BigRational.ONE;
        result = instance.getDistanceSquared(l, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 3
        expResult = BigRational.ONE;
        result = l.getDistanceSquared(instance, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 4
        l = new V3D_LineSegment(pP1P0P0, pP0P1P0, oom, rm);
        instance = new V3D_LineSegment(pN1P0P1, pN1P1P0, oom, rm);
        expResult = BigRational.ONE;
        result = l.getDistanceSquared(instance, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 5
        instance = new V3D_LineSegment(pP1P0P0, pP0P1P0, oom, rm);
        l = new V3D_LineSegment(pN1P0P1, pN1P1P0, oom, rm);
        expResult = BigRational.ONE;
        result = l.getDistanceSquared(instance, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 6
        instance = new V3D_LineSegment(pP0P0P0, pP0P1P0, oom, rm);
        l = new V3D_LineSegment(pN1P0P0, pN1P1P0, oom, rm);
        expResult = BigRational.ONE;
        result = l.getDistanceSquared(instance, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 7
        instance = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        l = new V3D_LineSegment(pN1P0P1, pN1P1P0, oom, rm);
        expResult = BigRational.valueOf(3, 2);
        result = l.getDistanceSquared(instance, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        
    }

    /**
     * Test of getMidpoint method, of class V3D_LineSegment.
     */
    @Test
    public void testGetMidpoint() {
        System.out.println("getMidpoint");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_LineSegment instance;
        V3D_Point expResult;
        V3D_Point result;
        // Test 1
        instance = new V3D_LineSegment(pP0P0P0, pP2P0P0, oom, rm);
        expResult = pP1P0P0;
        result = instance.getMidpoint(oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
    }

    /**
     * Test of getLineOfIntersection method, of class V3D_Line.
     */
    @Test
    public void testGetLineOfIntersection_V3D_Line() {
        System.out.println("getLineOfIntersection");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_LineSegment l0 = new V3D_LineSegment(pP1P0P0, pP1P1P0, oom, rm);
        V3D_Line l1 = new V3D_Line(pP0P0P0, pP0P0P1, oom, rm);
        V3D_Geometry expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        V3D_Geometry result = l0.getLineOfIntersection(l1, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
        // Test 2
        l1 = new V3D_Line(pP0P0P0, pP0P1P0, oom, rm);
        expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        result = l0.getLineOfIntersection(l1, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
        // Test 3
        l0 = new V3D_LineSegment(pP1P0P1, pP1P0P2, oom, rm);
        l1 = new V3D_Line(pN1P0P0, pN2P0P0, oom, rm);
        expResult = new V3D_LineSegment(pP1P0P0, pP1P0P1, oom, rm);
        result = l0.getLineOfIntersection(l1, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
//        // Test 4
//        l0 = new V3D_LineSegment(pP1P0P0, pP0P1P0, oom, rm);
//        l1 = new V3D_Line(pN1P0P1, pN1P1P0, oom, rm);
//        expResult = new V3D_LineSegment(pN1P1P0, pP0P1P0, oom, rm);
//        result = l0.getLineOfIntersection(l1, oom, rm);
//        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
    }

    /**
     * Test of getLineOfIntersection method, of class V3D_Line.
     */
    @Test
    public void testGetLineOfIntersection_V3D_LineSegment() {
        System.out.println("getLineOfIntersection");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_LineSegment l0 = new V3D_LineSegment(pP1P0P0, pP1P1P0, oom, rm);
        V3D_LineSegment l1 = new V3D_LineSegment(pP0P0P0, pP0P0P1, oom, rm);
        V3D_LineSegment expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        V3D_LineSegment result = l0.getLineOfIntersection(l1, oom, rm);
        assertTrue(expResult.equalsIgnoreDirection(result, oom, rm));
        // Test 2
        l1 = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        result = l0.getLineOfIntersection(l1, oom, rm);
        assertNull(result);
        // Test 3
        l0 = new V3D_LineSegment(pP1P0P1, pP1P0P2, oom, rm);
        l1 = new V3D_LineSegment(pN1P0P0, pN2P0P0, oom, rm);
        expResult = new V3D_LineSegment(pN1P0P0, pP1P0P1, oom, rm);
        result = l0.getLineOfIntersection(l1, oom, rm);
        assertTrue(expResult.equalsIgnoreDirection(result, oom, rm));
        // Test 4
        l0 = new V3D_LineSegment(pP1P0P0, pP0P1P0, oom, rm);
        l1 = new V3D_LineSegment(pN1P0P1, pN1P1P0, oom, rm);
        expResult = new V3D_LineSegment(pN1P1P0, pP0P1P0, oom, rm);
        result = l0.getLineOfIntersection(l1, oom, rm);
        assertTrue(expResult.equalsIgnoreDirection(result, oom, rm));
    }
}
