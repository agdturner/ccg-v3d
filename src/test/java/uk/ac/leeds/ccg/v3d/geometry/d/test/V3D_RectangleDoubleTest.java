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
package uk.ac.leeds.ccg.v3d.geometry.d.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.math.arithmetic.Math_Double;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_EnvelopeDouble;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_GeometryDouble;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_LineDouble;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_LineSegmentDouble;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_PlaneDouble;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_PointDouble;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_RayDouble;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_RectangleDouble;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_TriangleDouble;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_VectorDouble;

/**
 * Test of V3D_RectangleDouble class.
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class V3D_RectangleDoubleTest extends V3D_DoubleTest {

    public V3D_RectangleDoubleTest() {
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
     * Test of getEnvelope method, of class V3D_RectangleDouble.
     */
    @Test
    public void testGetEnvelope() {
        System.out.println("getEnvelope");
        /*
         * q ----------- r
         * |             |
         * |             |
         * |             |
         * pv ----------- s
         */
        V3D_RectangleDouble instance;
        V3D_EnvelopeDouble expResult;
        V3D_EnvelopeDouble result;
        instance = new V3D_RectangleDouble(pN1P1P0, pP1P1P0, pP1N1P0, pN1N1P0);
        expResult = new V3D_EnvelopeDouble(pN1N1P0, pP1P1P0);
        result = instance.getEnvelope();
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_RectangleDouble(pN1P1P1, pP1P1P0, pP1N1P0, pN1N1P1);
        expResult = new V3D_EnvelopeDouble(pN1N1P0, pP1P1P1);
        result = instance.getEnvelope();
        assertTrue(expResult.equals(result));
        // Test 3
        instance = new V3D_RectangleDouble(pN1P1P1, pP1P1N1, pP1N1N1, pN1N1P1);
        expResult = new V3D_EnvelopeDouble(pN1N1N1, pP1P1P1);
        result = instance.getEnvelope();
        assertTrue(expResult.equals(result));
        // Test 4
        instance = new V3D_RectangleDouble(pN1N1N1, pP1N1N1, pP1P1N1, pN1P1N1);
        expResult = new V3D_EnvelopeDouble(pN1N1N1, pP1P1N1);
        result = instance.getEnvelope();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_RectangleDouble.
     */
    @Test
    public void testIsIntersectedBy_V3D_PointDouble_int() {
        System.out.println("isIntersectedBy");
        double epsilon = 1d / 10000000d;
        V3D_PointDouble pt = pP0P0P0;
        V3D_RectangleDouble instance = new V3D_RectangleDouble(pN1P1P0, pP1P1P0, pP1N1P0, pN1N1P0);
        assertTrue(instance.isIntersectedBy(pt, epsilon));
        // Test 2
        instance = new V3D_RectangleDouble(pN1P0P0, pP0P1P0, pP1P0P0, pP0N1P0);
        assertTrue(instance.isIntersectedBy(pt, epsilon));
        // Test 3
        double half = 1d / 2d;
        pt = new V3D_PointDouble(half, half, 0d);
        assertTrue(instance.isIntersectedBy(pt, epsilon));
        // Test 4
        double halfpe = half + epsilon;
        double halfne = half - epsilon;
        pt = new V3D_PointDouble(halfpe, half, 0d);
        assertFalse(instance.isIntersectedBy(pt, epsilon));
        // Test 5
        pt = new V3D_PointDouble(-halfpe, half, 0d);
        assertFalse(instance.isIntersectedBy(pt, epsilon));
        // Test 6
        pt = new V3D_PointDouble(half, halfpe, 0d);
        assertFalse(instance.isIntersectedBy(pt, epsilon));
        // Test 7
        pt = new V3D_PointDouble(half, -halfpe, 0d);
        assertFalse(instance.isIntersectedBy(pt, epsilon));
        // Test 8
        pt = new V3D_PointDouble(halfne, half, 0d);
        assertTrue(instance.isIntersectedBy(pt, epsilon));
        // Test 9
        pt = new V3D_PointDouble(-halfne, half, 0d);
        assertTrue(instance.isIntersectedBy(pt, epsilon));
        // Test 10
        pt = new V3D_PointDouble(half, halfne, 0d);
        assertTrue(instance.isIntersectedBy(pt, epsilon));
        // Test 11
        pt = new V3D_PointDouble(half, -halfne, 0d);
        assertTrue(instance.isIntersectedBy(pt, epsilon));
    }

    /**
     * Test of getIntersection method, of class V3D_RectangleDouble.
     */
    @Test
    public void testGetIntersection() {
        System.out.println("getIntersection");
        double epsilon = 1d / 10000000d;
        V3D_LineDouble l = new V3D_LineDouble(pP0P0N1, pP0P0P1);
        V3D_RectangleDouble instance;
        V3D_GeometryDouble expResult;
        V3D_GeometryDouble result;
        instance = new V3D_RectangleDouble(pN1P1P0, pP1P1P0, pP1N1P0, pN1N1P0);
        expResult = pP0P0P0;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result, epsilon));
    }

    /**
     * Test of toString method, of class V3D_RectangleDouble.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        V3D_RectangleDouble instance = new V3D_RectangleDouble(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        String expResult = """
                           V3D_RectangleDouble(
                           offset=V3D_VectorDouble(dx=0.0, dy=0.0, dz=0.0),
                           p=V3D_VectorDouble(dx=0.0, dy=0.0, dz=0.0),
                           q=V3D_VectorDouble(dx=0.0, dy=1.0, dz=0.0),
                           r=V3D_VectorDouble(dx=1.0, dy=1.0, dz=0.0),
                           s=V3D_VectorDouble(dx=1.0, dy=0.0, dz=0.0))""";
        String result = instance.toString();
        //System.out.println(result);
        assertTrue(expResult.equalsIgnoreCase(result));
    }

    /**
     * Test of translate method, of class V3D_RectangleDouble.
     */
    @Test
    public void testApply() {
        // No test.
    }

    /**
     * Test of getIntersection method, of class V3D_RectangleDouble.
     */
    @Test
    public void testGetIntersection_V3D_LineSegmentDouble_double() {
        System.out.println("getIntersection");
        double epsilon = 1d / 10000000d;
        V3D_LineSegmentDouble l = new V3D_LineSegmentDouble(pN1N1P0, pP2P2P0);
        V3D_RectangleDouble instance = new V3D_RectangleDouble(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        V3D_GeometryDouble result = instance.getIntersection(l, epsilon);
        V3D_GeometryDouble expResult = new V3D_LineSegmentDouble(pP0P0P0, pP1P1P0);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection((V3D_LineSegmentDouble) result, epsilon));
        // Test 2
        l = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
        instance = new V3D_RectangleDouble(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0);
        expResult = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection((V3D_LineSegmentDouble) result, epsilon));
        // Test 3
        l = new V3D_LineSegmentDouble(pP1N1P0, pP1P2P0);
        instance = new V3D_RectangleDouble(pP0P0P0, pP0P1P0, pP2P1P0, pP2P0P0);
        expResult = new V3D_LineSegmentDouble(pP1P1P0, pP1P0P0);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection((V3D_LineSegmentDouble) result, epsilon));
    }

    /**
     * Test of getPerimeter method, of class V3D_RectangleDouble.
     */
    @Test
    public void testGetPerimeter() {
        System.out.println("getPerimeter");
        double epsilon = 1d / 10000000d;
        V3D_RectangleDouble instance = new V3D_RectangleDouble(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        double expResult = 4d;
        double result = instance.getPerimeter();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getArea method, of class V3D_RectangleDouble.
     */
    @Test
    public void testGetArea() {
        System.out.println("getArea");
        double epsilon = 1d / 10000000d;
        V3D_RectangleDouble instance = new V3D_RectangleDouble(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        double expResult = 1d;
        double result = instance.getArea();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistance method, of class V3D_RectangleDouble.
     */
    @Test
    public void testGetDistance() {
        System.out.println("getDistance");
        double epsilon = 1d / 10000000d;
        V3D_PointDouble p = pN1N1P0;
        V3D_RectangleDouble instance = new V3D_RectangleDouble(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        double expResult = Math.sqrt(2d);
        double result = instance.getDistance(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

//    /**
//     * Test of toStringFields method, of class V3D_RectangleDouble.
//     */
//    @Test
//    public void testToStringFields() {
//        System.out.println("toStringFields");
//        String pad = "";
//        V3D_RectangleDouble instance = new V3D_RectangleDouble(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0);
//        String expResult = """
//                           
//                           offset=V3D_VectorDouble
//                           (
//                            dx=0.0,
//                            dy=0.0,
//                            dz=0.0
//                           ),
//                           p=V3D_VectorDouble
//                           (
//                            dx=0.0,
//                            dy=0.0,
//                            dz=0.0
//                           ),
//                           q=V3D_VectorDouble
//                           (
//                            dx=1.0,
//                            dy=0.0,
//                            dz=0.0
//                           ),
//                           r=V3D_VectorDouble
//                           (
//                            dx=1.0,
//                            dy=1.0,
//                            dz=0.0
//                           ),
//                           s=V3D_VectorDouble
//                           (
//                            dx=0.0,
//                            dy=1.0,
//                            dz=0.0
//                           )""";
//        String result = instance.toStringFields(pad);
//        System.out.println(result);
//        assertEquals(expResult, result);
//    }

//    /**
//     * Test of getSV method, of class V3D_RectangleDouble.
//     */
//    @Test
//    public void testGetSV() {
//        System.out.println("getSV");
//        double epsilon = 1d / 10000000d;
//        V3D_RectangleDouble instance = new V3D_RectangleDouble(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0);
//        V3D_VectorDouble expResult = P0P1P0;
//        V3D_VectorDouble result = instance.s;
//        assertTrue(expResult.equals(result, epsilon));
//    }

    /**
     * Test of getS method, of class V3D_RectangleDouble.
     */
    @Test
    public void testGetS() {
        System.out.println("getS");
        double epsilon = 1d / 10000000d;
        V3D_RectangleDouble instance = new V3D_RectangleDouble(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0);
        V3D_PointDouble expResult = pP0P1P0;
        V3D_PointDouble result = instance.getS();
        assertTrue(expResult.equals(result, epsilon));
    }

//    /**
//     * Test of getRS method, of class V3D_RectangleDouble.
//     */
//    @Test
//    public void testGetRS() {
//        System.out.println("getRS");
//        double epsilon = 1d / 10000000d;
//        V3D_RectangleDouble instance = new V3D_RectangleDouble(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0);
//        V3D_LineSegmentDouble expResult = new V3D_LineSegmentDouble(pP1P1P0, pP0P1P0);
//        V3D_LineSegmentDouble result = instance.getRS();
//        assertTrue(expResult.equals(result, epsilon));
//    }

//    /**
//     * Test of getSP method, of class V3D_RectangleDouble.
//     */
//    @Test
//    public void testGetSP() {
//        System.out.println("getSP");
//        double epsilon = 1d / 10000000d;
//        V3D_RectangleDouble instance = new V3D_RectangleDouble(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0);
//        V3D_LineSegmentDouble expResult = new V3D_LineSegmentDouble(pP0P1P0, pP0P0P0);
//        V3D_LineSegmentDouble result = instance.getSP();
//        assertTrue(expResult.equals(result, epsilon));
//    }

    @Test
    public void testGetIntersection_V3D_Line_double() {
        System.out.println("getIntersection");
        double epsilon = 1d / 10000000d;
        V3D_LineDouble l = new V3D_LineDouble(pP0P0P0, pP1P0P0);
        V3D_RectangleDouble instance = new V3D_RectangleDouble(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0);
        V3D_GeometryDouble expResult = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
        //V3D_GeometryDouble expResult = new V3D_LineSegmentDouble(P0P0P0, P1P0P0, P1P1P0);
        V3D_GeometryDouble result = instance.getIntersection(l, epsilon);
        //System.out.println(result);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equals((V3D_LineSegmentDouble) result, epsilon));
    }

    /**
     * Test of getRSP method, of class V3D_RectangleDouble.
     */
    @Test
    public void testGetRSP() {
        System.out.println("getRSP");
        // No test
    }

    /**
     * Test of getPQR method, of class V3D_RectangleDouble.
     */
    @Test
    public void testGetPQR() {
        System.out.println("getPQR");
        // No test
    }

    /**
     * Test of isIntersectedBy0 method, of class V3D_RectangleDouble.
     */
    @Test
    public void testIsIntersectedBy0() {
        System.out.println("isIntersectedBy0");
        // No test
    }

    /**
     * Test of join method, of class V3D_RectangleDouble covered by
     * {@link #testGetIntersection_V3D_LineSegmentDouble_int()} and
     * {@link #testGetIntersection_V3D_Line_int()}.
     */
    @Test
    public void testJoin() {
        System.out.println("join");
        // No test.
    }

    /**
     * Test of getDistance method, of class V3D_RectangleDouble.
     */
    @Test
    public void testGetDistance_V3D_PointDouble_int() {
        System.out.println("getDistance");
        V3D_PointDouble p = pP0P0P0;
        double epsilon = 1d / 10000000d;
        V3D_RectangleDouble instance = new V3D_RectangleDouble(pP0P0P0, pP0P1P0, pP2P1P0, pP2P0P0);
        double expResult = 0d;
        double result = instance.getDistance(p, epsilon);
        assertEquals(expResult, result);
        // Test 2
        p = pN1P0P0;
        expResult = 1d;
        result = instance.getDistance(p, epsilon);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistanceSquared method, of class V3D_RectangleDouble.
     */
    @Test
    public void testGetDistanceSquared_V3D_PointDouble_int() {
        System.out.println("getDistanceSquared");
        V3D_PointDouble p = pP0P0P0;
        double epsilon = 1d / 10000000d;
        V3D_RectangleDouble instance = new V3D_RectangleDouble(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        double expResult = 0d;
        double result = instance.getDistanceSquared(p, epsilon);
        assertEquals(expResult, result);
        // Test 2
        p = pN1N1P0;
        expResult = 2d;
        result = instance.getDistanceSquared(p, epsilon);
        assertEquals(expResult, result);
    }

    /**
     * Test of setOffset method, of class V3D_RectangleDouble.
     */
    @Test
    public void testSetOffset() {
        System.out.println("setOffset");
        // No test.
//        V3D_VectorDouble offset = P1P1P1;
//        V3D_RectangleDouble instance = new V3D_RectangleDouble(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
//        instance.setOffset(offset);
//        V3D_RectangleDouble instance2 = new V3D_RectangleDouble(pP1P1P1, pP1P2P1, pP2P2P1, pP2P1P1);
//        assertEquals(instance, instance2);
    }

    /**
     * Test of translate method, of class V3D_RectangleDouble.
     */
    @Test
    public void testTranslate() {
        System.out.println("translate");
        double epsilon = 1d / 10000000d;
        V3D_VectorDouble v = P1P1P1;
        V3D_RectangleDouble instance = new V3D_RectangleDouble(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        instance.translate(v);
        V3D_RectangleDouble instance2 = new V3D_RectangleDouble(pP1P1P1, pP1P2P1, pP2P2P1, pP2P1P1);
        assertTrue(instance.equals(instance2, epsilon));
    }

    /**
     * Test of rotate method, of class V3D_RectangleDouble.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        double epsilon = 1d / 10000000d;
        V3D_RayDouble xAxis = new V3D_RayDouble(V3D_DoubleTest.pP0P0P0, V3D_DoubleTest.pP1P0P0);
        V3D_RayDouble axis = xAxis;
        double theta = 0d;
        V3D_RectangleDouble instance = new V3D_RectangleDouble(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        instance.rotate(axis, theta, epsilon);
        V3D_RectangleDouble instance2 = new V3D_RectangleDouble(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        assertTrue(instance.equals(instance2, epsilon));
    }

    /**
     * Test of getIntersection method, of class V3D_RectangleDouble.
     */
    @Test
    public void testGetIntersection_V3D_PlaneDouble_int() {
        System.out.println("getIntersection");
        double epsilon = 1d / 10000000d;
        V3D_PlaneDouble p = new V3D_PlaneDouble(pP0P0P0, pP0P1P0, pP1P1P0);
        V3D_RectangleDouble instance = new V3D_RectangleDouble(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        V3D_GeometryDouble expResult = new V3D_RectangleDouble(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        V3D_GeometryDouble result = instance.getIntersection(p, epsilon);
        assertTrue(((V3D_RectangleDouble) expResult).equals((V3D_RectangleDouble) result, epsilon));
    }

    /**
     * Test of getIntersection method, of class V3D_RectangleDouble.
     */
    @Test
    public void testGetIntersection_V3D_TriangleDouble_double() {
        System.out.println("getIntersection");
        double epsilon = 1d / 10000000d;
        V3D_TriangleDouble t = new V3D_TriangleDouble(pP0P0P0, pP0P1P0, pP1P1P0);
        V3D_RectangleDouble instance = new V3D_RectangleDouble(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        V3D_GeometryDouble expResult = new V3D_TriangleDouble(pP0P0P0, pP0P1P0, pP1P1P0);
        V3D_GeometryDouble result = instance.getIntersection(t, epsilon);
        assertTrue(((V3D_TriangleDouble) expResult).equals((V3D_TriangleDouble) result, epsilon));
        // Test 2
         t = new V3D_TriangleDouble(pP2P2P0, new V3D_PointDouble(4d,4d,0d), 
                 new V3D_PointDouble(2d, 4d, 5d));
         instance = new V3D_RectangleDouble(
                 new V3D_PointDouble(0d,0d,0d),
                 new V3D_PointDouble(0d,6d,0d),
                 new V3D_PointDouble(6d,6d,0d),
                 new V3D_PointDouble(6d,0d,0d));
         expResult = new V3D_LineSegmentDouble(pP2P2P0, new V3D_PointDouble(4d,4d,0d));
         result = instance.getIntersection(t, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection((V3D_LineSegmentDouble) result, epsilon));
        // Test 3
         t = new V3D_TriangleDouble(new V3D_PointDouble(3d,3d, 0d), new V3D_PointDouble(4d,4d,0d), 
                 new V3D_PointDouble(2d, 4d, 5d));
         instance = new V3D_RectangleDouble(
                 new V3D_PointDouble(0d,0d,0d),
                 new V3D_PointDouble(0d,6d,0d),
                 new V3D_PointDouble(6d,6d,0d),
                 new V3D_PointDouble(6d,0d,0d));
         expResult = new V3D_LineSegmentDouble(new V3D_PointDouble(3d,3d, 0d), new V3D_PointDouble(4d,4d,0d));
         result = instance.getIntersection(t, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection((V3D_LineSegmentDouble) result, epsilon));
        // Test 4
         t = new V3D_TriangleDouble(new V3D_PointDouble(4d,4d, 0d), new V3D_PointDouble(5d,5d,0d), 
                 new V3D_PointDouble(2d, 4d, 5d));
         instance = new V3D_RectangleDouble(
                 new V3D_PointDouble(0d,0d,0d),
                 new V3D_PointDouble(0d,6d,0d),
                 new V3D_PointDouble(6d,6d,0d),
                 new V3D_PointDouble(6d,0d,0d));
         expResult = new V3D_LineSegmentDouble(new V3D_PointDouble(4d,4d, 0d), new V3D_PointDouble(5d,5d,0d));
         result = instance.getIntersection(t, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection((V3D_LineSegmentDouble) result, epsilon));
        // Test 5
         t = new V3D_TriangleDouble(new V3D_PointDouble(7d,7d, 0d), new V3D_PointDouble(8d,8d,0d), 
                 new V3D_PointDouble(2d, 4d, 5d));
         instance = new V3D_RectangleDouble(
                 new V3D_PointDouble(0d,0d,0d),
                 new V3D_PointDouble(0d,6d,0d),
                 new V3D_PointDouble(6d,6d,0d),
                 new V3D_PointDouble(6d,0d,0d));
        result =  instance.getIntersection(t, epsilon);
        assertNull(result);
        // Test 6
         t = new V3D_TriangleDouble(new V3D_PointDouble(0d,1d,-1d), new V3D_PointDouble(0d,5d,-1d), 
                 new V3D_PointDouble(0d, 2d, 1d));
         instance = new V3D_RectangleDouble(
                 new V3D_PointDouble(0d,0d,0d),
                 new V3D_PointDouble(0d,6d,0d),
                 new V3D_PointDouble(6d,6d,0d),
                 new V3D_PointDouble(6d,0d,0d));
         expResult = new V3D_LineSegmentDouble(new V3D_PointDouble(0d,1.5d,0d), new V3D_PointDouble(0d,3.5d,0d));
         result = instance.getIntersection(t, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection((V3D_LineSegmentDouble) result, epsilon));
        // Test 7
         t = new V3D_TriangleDouble(new V3D_PointDouble(0d,2d,-2d), new V3D_PointDouble(0d,5d,-2d), 
                 new V3D_PointDouble(0d, 2d, 2d));
         instance = new V3D_RectangleDouble(
                 new V3D_PointDouble(0d,0d,0d),
                 new V3D_PointDouble(0d,6d,0d),
                 new V3D_PointDouble(6d,6d,0d),
                 new V3D_PointDouble(6d,0d,0d));
         expResult = new V3D_LineSegmentDouble(new V3D_PointDouble(0d,1.5d,0d), new V3D_PointDouble(0d,3.5d,0d));
         result = instance.getIntersection(t, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection((V3D_LineSegmentDouble) result, epsilon));
        // Test 8
         t = new V3D_TriangleDouble(new V3D_PointDouble(0d,2d,-2000d), new V3D_PointDouble(0d,5d,-2000d), 
                 new V3D_PointDouble(0d, 2d, 2000d));
         instance = new V3D_RectangleDouble(
                 new V3D_PointDouble(0d,0d,0d),
                 new V3D_PointDouble(0d,6d,0d),
                 new V3D_PointDouble(6d,6d,0d),
                 new V3D_PointDouble(6d,0d,0d));
         expResult = new V3D_LineSegmentDouble(new V3D_PointDouble(0d,1.5d,0d), new V3D_PointDouble(0d,3.5d,0d));
         result = instance.getIntersection(t, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection((V3D_LineSegmentDouble) result, epsilon));
        // Test 9
         t = new V3D_TriangleDouble(new V3D_PointDouble(0d,2d,-2000000d), new V3D_PointDouble(0d,5d,-2000000d), 
                 new V3D_PointDouble(0d, 2d, 2000000d));
         instance = new V3D_RectangleDouble(
                 new V3D_PointDouble(0d,0d,0d),
                 new V3D_PointDouble(0d,6d,0d),
                 new V3D_PointDouble(6d,6d,0d),
                 new V3D_PointDouble(6d,0d,0d));
         expResult = new V3D_LineSegmentDouble(new V3D_PointDouble(0d,1.5d,0d), new V3D_PointDouble(0d,3.5d,0d));
         result = instance.getIntersection(t, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection((V3D_LineSegmentDouble) result, epsilon));
        // Test 10
         t = new V3D_TriangleDouble(new V3D_PointDouble(0d,1d,-2d), new V3D_PointDouble(0d,5d,-2d), 
                 new V3D_PointDouble(0d, 3d, 2d));
         instance = new V3D_RectangleDouble(
                 new V3D_PointDouble(0d,0d,0d),
                 new V3D_PointDouble(0d,6d,0d),
                 new V3D_PointDouble(6d,6d,0d),
                 new V3D_PointDouble(6d,0d,0d));
         expResult = new V3D_LineSegmentDouble(new V3D_PointDouble(0d,1.5d,0d), new V3D_PointDouble(0d,3.5d,0d));
         result = instance.getIntersection(t, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection((V3D_LineSegmentDouble) result, epsilon));
    }

    /**
     * Test of getIntersection method, of class V3D_RectangleDouble.
     */
    @Test
    public void testGetIntersection_V3D_Tetrahedron_int() {
//        System.out.println("getIntersection");
//        V3D_Tetrahedron t = new V3D_Tetrahedron(pP0P0P0, pP0P1P0, pP1P1P0, pP0P0P1);
//        int oom = -3;
//        V3D_RectangleDouble instance = new V3D_RectangleDouble(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
//        V3D_GeometryDouble expResult = new V3D_TriangleDouble(pP0P0P0, pP0P1P0, pP1P1P0);
//        V3D_GeometryDouble result = instance.getIntersection(t, oom);
//        assertEquals(expResult, result);
    }

    /**
     * Test of getDistance method, of class V3D_RectangleDouble.
     */
    @Test
    public void testGetDistance_V3D_Line_int() {
        System.out.println("getDistance");
        double epsilon = 1d / 10000000d;
        V3D_LineDouble l = new V3D_LineDouble(pP0N1P1, pP1N1P1);
        V3D_RectangleDouble instance = new V3D_RectangleDouble(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        double expResult = 1d;
        double result = instance.getDistance(l, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistanceSquared method, of class V3D_RectangleDouble.
     */
    @Test
    public void testGetDistanceSquared_V3D_Line_int() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V3D_LineDouble l = new V3D_LineDouble(pP0N1P1, pP1N1P1);
        V3D_RectangleDouble instance = new V3D_RectangleDouble(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        double expResult = 1d;
        double result = instance.getDistanceSquared(l, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistance method, of class V3D_RectangleDouble.
     */
    @Test
    public void testGetDistance_V3D_LineSegmentDouble_int() {
        System.out.println("getDistance");
        double epsilon = 1d / 10000000d;
        V3D_LineSegmentDouble l = new V3D_LineSegmentDouble(pP0N1P0, pP1N1P0);
        V3D_RectangleDouble instance = new V3D_RectangleDouble(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        double expResult = 1d;
        double result = instance.getDistance(l, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistanceSquared method, of class V3D_RectangleDouble.
     */
    @Test
    public void testGetDistanceSquared_V3D_LineSegmentDouble_int() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V3D_LineSegmentDouble l = new V3D_LineSegmentDouble(pP0N1P0, pP1N1P0);
        V3D_RectangleDouble instance = new V3D_RectangleDouble(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        double expResult = 1d;
        double result = instance.getDistanceSquared(l, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistance method, of class V3D_RectangleDouble.
     */
    @Test
    public void testGetDistance_V3D_PlaneDouble_int() {
        System.out.println("getDistance");
        double epsilon = 1d / 10000000d;
        V3D_PlaneDouble p = new V3D_PlaneDouble(pP0N1P0, pP1N1P0, pP0N1P1);
        V3D_RectangleDouble instance = new V3D_RectangleDouble(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        double expResult = 1d;
        double result = instance.getDistance(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistanceSquared method, of class V3D_RectangleDouble.
     */
    @Test
    public void testGetDistanceSquared_V3D_PlaneDouble_int() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V3D_PlaneDouble p = new V3D_PlaneDouble(pP0N1P0, pP1N1P0, pP0N1P1);
        V3D_RectangleDouble instance = new V3D_RectangleDouble(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        double expResult = 1d;
        double result = instance.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistance method, of class V3D_RectangleDouble.
     */
    @Test
    public void testGetDistance_V3D_TriangleDouble_int() {
        System.out.println("getDistance");
        double epsilon = 1d / 10000000d;
        V3D_TriangleDouble t = new V3D_TriangleDouble(pP0N1P0, pP1N1P0, pP0N1P1);
        V3D_RectangleDouble instance = new V3D_RectangleDouble(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        double expResult = 1d;
        double result = instance.getDistance(t, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 2
        t = new V3D_TriangleDouble(pP0N1P0, pP1N1P0, pP1P1P0);
        expResult = 0d;
        result = instance.getDistance(t, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistanceSquared method, of class V3D_RectangleDouble.
     */
    @Test
    public void testGetDistanceSquared_V3D_TriangleDouble_int() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V3D_TriangleDouble t = new V3D_TriangleDouble(pP0P0P0, pP0P1P0, pP1P1P0);
        V3D_RectangleDouble instance = new V3D_RectangleDouble(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        double expResult = 0d;
        double result = instance.getDistanceSquared(t, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of equals method, of class V3D_RectangleDouble.
     */
    @Test
    public void testEquals_V3D_RectangleDouble_int_RoundingMode() {
        System.out.println("equals");
        double epsilon = 1d / 10000000d;
        V3D_RectangleDouble r = new V3D_RectangleDouble(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        V3D_RectangleDouble instance = new V3D_RectangleDouble(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        assertTrue(instance.equals(r, epsilon));
        // Test 2
        instance = new V3D_RectangleDouble(pP0P1P0, pP1P1P0, pP1P0P0, pP0P0P0);
        assertTrue(instance.equals(r, epsilon));
        // Test 3
        instance = new V3D_RectangleDouble(pP1P1P0, pP1P0P0, pP0P0P0, pP0P1P0);
        assertTrue(instance.equals(r, epsilon));
        // Test 4
        instance = new V3D_RectangleDouble(pP1P0P0, pP0P0P0, pP0P1P0, pP1P1P0);
        assertTrue(instance.equals(r, epsilon));
        // Test 5
        instance = new V3D_RectangleDouble(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0);
        assertTrue(instance.equals(r, epsilon));
        // Test 6
        instance = new V3D_RectangleDouble(pP1P0P0, pP1P1P0, pP0P1P0, pP0P0P0);
        assertTrue(instance.equals(r, epsilon));
        // Test 7
        instance = new V3D_RectangleDouble(pP1P1P0, pP0P1P0, pP0P0P0, pP1P0P0);
        assertTrue(instance.equals(r, epsilon));
        // Test 8
        instance = new V3D_RectangleDouble(pP0P1P0, pP0P0P0, pP1P0P0, pP1P1P0);
        assertTrue(instance.equals(r, epsilon));
    }

    /**
     * Test of isRectangle method, of class V3D_RectangleDouble.
     */
    @Test
    public void testIsRectangle() {
        System.out.println("isRectangle");
        double epsilon = 1d / 10000000d;
        V3D_PointDouble p = pP0P0P0;
        V3D_PointDouble q = pP1P0P0;
        V3D_PointDouble r = pP1P1P0;
        V3D_PointDouble s = pP0P1P0;
        assertTrue(V3D_RectangleDouble.isRectangle(p, q, r, s, epsilon));
        // Test 2
        assertTrue(V3D_RectangleDouble.isRectangle(p, s, r, q, epsilon));
        // Test 2
        p = pN1P0P0;
        assertFalse(V3D_RectangleDouble.isRectangle(p, q, r, s, epsilon));
    }
}
