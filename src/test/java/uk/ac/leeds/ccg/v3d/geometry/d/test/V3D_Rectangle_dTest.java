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
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_AABB_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Geometry_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Line_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_LineSegment_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Plane_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Point_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Ray_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Rectangle_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Triangle_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Vector_d;

/**
 * Test of V3D_Rectangle_d class.
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class V3D_Rectangle_dTest extends V3D_Test_d {

    public V3D_Rectangle_dTest() {
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
     * Test of getAABB method, of class V3D_Rectangle_d.
     */
    @Test
    public void testGetAABB() {
        System.out.println("getAABB");
        /*
         * q ----------- r
         * |             |
         * |             |
         * |             |
         * pv ----------- s
         */
        V3D_Rectangle_d instance;
        V3D_AABB_d expResult;
        V3D_AABB_d result;
        instance = new V3D_Rectangle_d(pN1P1P0, pP1P1P0, pP1N1P0, pN1N1P0);
        expResult = new V3D_AABB_d(pN1N1P0, pP1P1P0);
        result = instance.getAABB();
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_Rectangle_d(pN1P1P1, pP1P1P0, pP1N1P0, pN1N1P1);
        expResult = new V3D_AABB_d(pN1N1P0, pP1P1P1);
        result = instance.getAABB();
        assertTrue(expResult.equals(result));
        // Test 3
        instance = new V3D_Rectangle_d(pN1P1P1, pP1P1N1, pP1N1N1, pN1N1P1);
        expResult = new V3D_AABB_d(pN1N1N1, pP1P1P1);
        result = instance.getAABB();
        assertTrue(expResult.equals(result));
        // Test 4
        instance = new V3D_Rectangle_d(pN1N1N1, pP1N1N1, pP1P1N1, pN1P1N1);
        expResult = new V3D_AABB_d(pN1N1N1, pP1P1N1);
        result = instance.getAABB();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of intersects method, of class V3D_Rectangle_d.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point_d_int() {
        System.out.println("intersects");
        double epsilon = 1d / 10000000d;
        V3D_Point_d pt = pP0P0P0;
        V3D_Rectangle_d instance = new V3D_Rectangle_d(pN1P1P0, pP1P1P0, pP1N1P0, pN1N1P0);
        assertTrue(instance.intersects(pt, epsilon));
        // Test 2
        instance = new V3D_Rectangle_d(pN1P0P0, pP0P1P0, pP1P0P0, pP0N1P0);
        assertTrue(instance.intersects(pt, epsilon));
        // Test 3
        double half = 1d / 2d;
        pt = new V3D_Point_d(env, half, half, 0d);
        assertTrue(instance.intersects(pt, epsilon));
        // Test 4
        double halfpe = half + epsilon;
        double halfne = half - epsilon;
        pt = new V3D_Point_d(env, halfpe, half, 0d);
        assertFalse(instance.intersects(pt, epsilon));
        // Test 5
        pt = new V3D_Point_d(env, -halfpe, half, 0d);
        assertFalse(instance.intersects(pt, epsilon));
        // Test 6
        pt = new V3D_Point_d(env, half, halfpe, 0d);
        assertFalse(instance.intersects(pt, epsilon));
        // Test 7
        pt = new V3D_Point_d(env, half, -halfpe, 0d);
        assertFalse(instance.intersects(pt, epsilon));
        // Test 8
        pt = new V3D_Point_d(env, halfne, half, 0d);
        assertTrue(instance.intersects(pt, epsilon));
        // Test 9
        pt = new V3D_Point_d(env, -halfne, half, 0d);
        assertTrue(instance.intersects(pt, epsilon));
        // Test 10
        pt = new V3D_Point_d(env, half, halfne, 0d);
        assertTrue(instance.intersects(pt, epsilon));
        // Test 11
        pt = new V3D_Point_d(env, half, -halfne, 0d);
        assertTrue(instance.intersects(pt, epsilon));
    }

    /**
     * Test of getIntersect method, of class V3D_Rectangle_d.
     */
    @Test
    public void testGetIntersection() {
        System.out.println("getIntersect");
        double epsilon = 1d / 10000000d;
        V3D_Line_d l = new V3D_Line_d(pP0P0N1, pP0P0P1);
        V3D_Rectangle_d instance;
        V3D_Geometry_d expResult;
        V3D_Geometry_d result;
        instance = new V3D_Rectangle_d(pN1P1P0, pP1P1P0, pP1N1P0, pN1N1P0);
        expResult = pP0P0P0;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals(epsilon, (V3D_Point_d) result));
    }

    /**
     * Test of toString method, of class V3D_Rectangle_d.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        V3D_Rectangle_d instance = new V3D_Rectangle_d(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        String expResult = """
                           V3D_Rectangle_d(
                           offset=V3D_Vector_d(dx=0.0, dy=0.0, dz=0.0),
                           pqr=V3D_Triangle_d(
                            pl=( V3D_Plane_d(
                             offset=V3D_Vector_d(dx=0.0, dy=0.0, dz=0.0),
                             p=  V3D_Vector_d(dx=0.0, dy=0.0, dz=0.0),
                             n=  V3D_Vector_d(dx=0.0, dy=0.0, dz=-1.0))),
                            offset=(V3D_Vector_d(dx=0.0, dy=0.0, dz=0.0)),
                            p=(V3D_Vector_d(dx=0.0, dy=0.0, dz=0.0)),
                            q=(V3D_Vector_d(dx=0.0, dy=1.0, dz=0.0)),
                            r=(V3D_Vector_d(dx=1.0, dy=1.0, dz=0.0))),
                           rsp=V3D_Triangle_d(
                            pl=( V3D_Plane_d(
                             offset=V3D_Vector_d(dx=0.0, dy=0.0, dz=0.0),
                             p=  V3D_Vector_d(dx=0.0, dy=0.0, dz=0.0),
                             n=  V3D_Vector_d(dx=0.0, dy=0.0, dz=-1.0))),
                            offset=(V3D_Vector_d(dx=0.0, dy=0.0, dz=0.0)),
                            p=(V3D_Vector_d(dx=1.0, dy=1.0, dz=0.0)),
                            q=(V3D_Vector_d(dx=1.0, dy=0.0, dz=0.0)),
                            r=(V3D_Vector_d(dx=0.0, dy=0.0, dz=0.0))))""";
        String result = instance.toString();
        //System.out.println(result);
        assertTrue(expResult.equalsIgnoreCase(result));
    }

    /**
     * Test of translate method, of class V3D_Rectangle_d.
     */
    @Test
    public void testApply() {
        // No test.
    }

    /**
     * Test of getIntersect method, of class V3D_Rectangle_d.
     */
    @Test
    public void testGetIntersection_V3D_LineSegment_d_double() {
        System.out.println("getIntersect");
        double epsilon = 1d / 10000000d;
        V3D_LineSegment_d l = new V3D_LineSegment_d(pN1N1P0, pP2P2P0);
        V3D_Rectangle_d instance = new V3D_Rectangle_d(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        V3D_Geometry_d result = instance.getIntersect(l, epsilon);
        V3D_Geometry_d expResult = new V3D_LineSegment_d(pP0P0P0, pP1P1P0);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
        // Test 2
        l = new V3D_LineSegment_d(pP0P0P0, pP1P0P0);
        instance = new V3D_Rectangle_d(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0);
        expResult = new V3D_LineSegment_d(pP0P0P0, pP1P0P0);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
        // Test 3
        l = new V3D_LineSegment_d(pP1N1P0, pP1P2P0);
        instance = new V3D_Rectangle_d(pP0P0P0, pP0P1P0, pP2P1P0, pP2P0P0);
        expResult = new V3D_LineSegment_d(pP1P1P0, pP1P0P0);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
    }

    /**
     * Test of getPerimeter method, of class V3D_Rectangle_d.
     */
    @Test
    public void testGetPerimeter() {
        System.out.println("getPerimeter");
        double epsilon = 1d / 10000000d;
        V3D_Rectangle_d instance = new V3D_Rectangle_d(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        double expResult = 4d;
        double result = instance.getPerimeter();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getArea method, of class V3D_Rectangle_d.
     */
    @Test
    public void testGetArea() {
        System.out.println("getArea");
        double epsilon = 1d / 10000000d;
        V3D_Rectangle_d instance = new V3D_Rectangle_d(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        double expResult = 1d;
        double result = instance.getArea();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistance method, of class V3D_Rectangle_d.
     */
    @Test
    public void testGetDistance() {
        System.out.println("getDistance");
        double epsilon = 1d / 10000000d;
        V3D_Point_d p = pN1N1P0;
        V3D_Rectangle_d instance = new V3D_Rectangle_d(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        double expResult = Math.sqrt(2d);
        double result = instance.getDistance(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

//    /**
//     * Test of toStringFields method, of class V3D_Rectangle_d.
//     */
//    @Test
//    public void testToStringFields() {
//        System.out.println("toStringFields");
//        String pad = "";
//        V3D_Rectangle_d instance = new V3D_Rectangle_d(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0);
//        String expResult = """
//                           
//                           offset=V3D_Vector_d
//                           (
//                            dx=0.0,
//                            dy=0.0,
//                            dz=0.0
//                           ),
//                           p=V3D_Vector_d
//                           (
//                            dx=0.0,
//                            dy=0.0,
//                            dz=0.0
//                           ),
//                           q=V3D_Vector_d
//                           (
//                            dx=1.0,
//                            dy=0.0,
//                            dz=0.0
//                           ),
//                           r=V3D_Vector_d
//                           (
//                            dx=1.0,
//                            dy=1.0,
//                            dz=0.0
//                           ),
//                           s=V3D_Vector_d
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
//     * Test of getSV method, of class V3D_Rectangle_d.
//     */
//    @Test
//    public void testGetSV() {
//        System.out.println("getSV");
//        double epsilon = 1d / 10000000d;
//        V3D_Rectangle_d instance = new V3D_Rectangle_d(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0);
//        V3D_Vector_d expResult = P0P1P0;
//        V3D_Vector_d result = instance.s;
//        assertTrue(expResult.equals(result, epsilon));
//    }

    /**
     * Test of getS method, of class V3D_Rectangle_d.
     */
    @Test
    public void testGetS() {
        System.out.println("getS");
        double epsilon = 1d / 10000000d;
        V3D_Rectangle_d instance = new V3D_Rectangle_d(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0);
        V3D_Point_d expResult = pP0P1P0;
        V3D_Point_d result = instance.getS();
        assertTrue(expResult.equals(epsilon, result));
    }

//    /**
//     * Test of getRS method, of class V3D_Rectangle_d.
//     */
//    @Test
//    public void testGetRS() {
//        System.out.println("getRS");
//        double epsilon = 1d / 10000000d;
//        V3D_Rectangle_d instance = new V3D_Rectangle_d(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0);
//        V3D_LineSegment_d expResult = new V3D_LineSegment_d(pP1P1P0, pP0P1P0);
//        V3D_LineSegment_d result = instance.getRS();
//        assertTrue(expResult.equals(result, epsilon));
//    }

//    /**
//     * Test of getSP method, of class V3D_Rectangle_d.
//     */
//    @Test
//    public void testGetSP() {
//        System.out.println("getSP");
//        double epsilon = 1d / 10000000d;
//        V3D_Rectangle_d instance = new V3D_Rectangle_d(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0);
//        V3D_LineSegment_d expResult = new V3D_LineSegment_d(pP0P1P0, pP0P0P0);
//        V3D_LineSegment_d result = instance.getSP();
//        assertTrue(expResult.equals(result, epsilon));
//    }

    @Test
    public void testGetIntersection_V3D_Line_double() {
        System.out.println("getIntersect");
        double epsilon = 1d / 10000000d;
        V3D_Line_d l = new V3D_Line_d(pP0P0P0, pP1P0P0);
        V3D_Rectangle_d instance = new V3D_Rectangle_d(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0);
        V3D_Geometry_d expResult = new V3D_LineSegment_d(pP0P0P0, pP1P0P0);
        //V3D_Geometry_d expResult = new V3D_LineSegment_d(P0P0P0, P1P0P0, P1P1P0);
        V3D_Geometry_d result = instance.getIntersect(l, epsilon);
        //System.out.println(result);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
    }

    /**
     * Test of getRSP method, of class V3D_Rectangle_d.
     */
    @Test
    public void testGetRSP() {
        System.out.println("getRSP");
        // No test
    }

    /**
     * Test of getPQR method, of class V3D_Rectangle_d.
     */
    @Test
    public void testGetPQR() {
        System.out.println("getPQR");
        // No test
    }

    /**
     * Test of intersects0 method, of class V3D_Rectangle_d.
     */
    @Test
    public void testIsIntersectedBy0() {
        System.out.println("intersects0");
        // No test
    }

    /**
     * Test of join method, of class V3D_Rectangle_d covered by
     * {@link #testGetIntersection_V3D_LineSegment_d_int()} and
     * {@link #testGetIntersection_V3D_Line_int()}.
     */
    @Test
    public void testJoin() {
        System.out.println("join");
        // No test.
    }

    /**
     * Test of getDistance method, of class V3D_Rectangle_d.
     */
    @Test
    public void testGetDistance_V3D_Point_d_int() {
        System.out.println("getDistance");
        V3D_Point_d p = pP0P0P0;
        double epsilon = 1d / 10000000d;
        V3D_Rectangle_d instance = new V3D_Rectangle_d(pP0P0P0, pP0P1P0, pP2P1P0, pP2P0P0);
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
     * Test of getDistanceSquared method, of class V3D_Rectangle_d.
     */
    @Test
    public void testGetDistanceSquared_V3D_Point_d_int() {
        System.out.println("getDistanceSquared");
        V3D_Point_d p = pP0P0P0;
        double epsilon = 1d / 10000000d;
        V3D_Rectangle_d instance = new V3D_Rectangle_d(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
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
     * Test of setOffset method, of class V3D_Rectangle_d.
     */
    @Test
    public void testSetOffset() {
        System.out.println("setOffset");
        // No test.
//        V3D_Vector_d offset = P1P1P1;
//        V3D_Rectangle_d instance = new V3D_Rectangle_d(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
//        instance.setOffset(offset);
//        V3D_Rectangle_d instance2 = new V3D_Rectangle_d(pP1P1P1, pP1P2P1, pP2P2P1, pP2P1P1);
//        assertEquals(instance, instance2);
    }

    /**
     * Test of translate method, of class V3D_Rectangle_d.
     */
    @Test
    public void testTranslate() {
        System.out.println("translate");
        double epsilon = 1d / 10000000d;
        V3D_Vector_d v = P1P1P1;
        V3D_Rectangle_d instance = new V3D_Rectangle_d(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        instance.translate(v);
        V3D_Rectangle_d instance2 = new V3D_Rectangle_d(pP1P1P1, pP1P2P1, pP2P2P1, pP2P1P1);
        assertTrue(instance.equals(instance2, epsilon));
    }

    /**
     * Test of rotate method, of class V3D_Rectangle_d.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        double epsilon = 1d / 10000000d;
        V3D_Ray_d xaxis = new V3D_Ray_d(pP0P0P0, pP1P0P0);
        double theta = 0d;
        V3D_Rectangle_d instance = new V3D_Rectangle_d(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        instance.rotate(xaxis, xaxis.l.v, theta, epsilon);
        V3D_Rectangle_d instance2 = new V3D_Rectangle_d(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        assertTrue(instance.equals(instance2, epsilon));
    }

    /**
     * Test of getIntersect method, of class V3D_Rectangle_d.
     */
    @Test
    public void testGetIntersection_V3D_Plane_d_int() {
        System.out.println("getIntersect");
        double epsilon = 1d / 10000000d;
        V3D_Plane_d p = new V3D_Plane_d(pP0P0P0, pP0P1P0, pP1P1P0);
        V3D_Rectangle_d instance = new V3D_Rectangle_d(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        V3D_Geometry_d expResult = new V3D_Rectangle_d(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        V3D_Geometry_d result = instance.getIntersect(p, epsilon);
        assertTrue(((V3D_Rectangle_d) expResult).equals((V3D_Rectangle_d) result, epsilon));
    }

    /**
     * Test of getIntersect method, of class V3D_Rectangle_d.
     */
    @Test
    public void testGetIntersection_V3D_Triangle_d_double() {
        System.out.println("getIntersect");
        double epsilon = 1d / 10000000d;
        V3D_Triangle_d t = new V3D_Triangle_d(pP0P0P0, pP0P1P0, pP1P1P0);
        V3D_Rectangle_d instance = new V3D_Rectangle_d(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        V3D_Geometry_d expResult = new V3D_Triangle_d(pP0P0P0, pP0P1P0, pP1P1P0);
        V3D_Geometry_d result = instance.getIntersect(t, epsilon);
        assertTrue(((V3D_Triangle_d) expResult).equals((V3D_Triangle_d) result, epsilon));
        // Test 2
         t = new V3D_Triangle_d(pP2P2P0, new V3D_Point_d(env, 4d,4d,0d), 
                 new V3D_Point_d(env, 2d, 4d, 5d));
         instance = new V3D_Rectangle_d(
                 new V3D_Point_d(env, 0d,0d,0d),
                 new V3D_Point_d(env, 0d,6d,0d),
                 new V3D_Point_d(env, 6d,6d,0d),
                 new V3D_Point_d(env, 6d,0d,0d));
         expResult = new V3D_LineSegment_d(pP2P2P0, new V3D_Point_d(env, 4d,4d,0d));
         result = instance.getIntersect(t, epsilon);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
        // Test 3
         t = new V3D_Triangle_d(new V3D_Point_d(env, 3d,3d, 0d), new V3D_Point_d(env, 4d,4d,0d), 
                 new V3D_Point_d(env, 2d, 4d, 5d));
         instance = new V3D_Rectangle_d(
                 new V3D_Point_d(env, 0d,0d,0d),
                 new V3D_Point_d(env, 0d,6d,0d),
                 new V3D_Point_d(env, 6d,6d,0d),
                 new V3D_Point_d(env, 6d,0d,0d));
         expResult = new V3D_LineSegment_d(new V3D_Point_d(env, 3d,3d, 0d), new V3D_Point_d(env, 4d,4d,0d));
         result = instance.getIntersect(t, epsilon);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
        // Test 4
         t = new V3D_Triangle_d(new V3D_Point_d(env, 4d,4d, 0d), new V3D_Point_d(env, 5d,5d,0d), 
                 new V3D_Point_d(env, 2d, 4d, 5d));
         instance = new V3D_Rectangle_d(
                 new V3D_Point_d(env, 0d,0d,0d),
                 new V3D_Point_d(env, 0d,6d,0d),
                 new V3D_Point_d(env, 6d,6d,0d),
                 new V3D_Point_d(env, 6d,0d,0d));
         expResult = new V3D_LineSegment_d(new V3D_Point_d(env, 4d,4d, 0d), new V3D_Point_d(env, 5d,5d,0d));
         result = instance.getIntersect(t, epsilon);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
        // Test 5
         t = new V3D_Triangle_d(new V3D_Point_d(env, 7d,7d, 0d), 
                 new V3D_Point_d(env, 8d,8d,0d), 
                 new V3D_Point_d(env, 2d, 4d, 5d));
         instance = new V3D_Rectangle_d(
                 new V3D_Point_d(env, 0d,0d,0d),
                 new V3D_Point_d(env, 0d,6d,0d),
                 new V3D_Point_d(env, 6d,6d,0d),
                 new V3D_Point_d(env, 6d,0d,0d));
        result =  instance.getIntersect(t, epsilon);
        //assertNull(result);
        // Test 6
         t = new V3D_Triangle_d(new V3D_Point_d(env, 0d,1d,-1d), new V3D_Point_d(env, 0d,5d,-1d), 
                 new V3D_Point_d(env, 0d, 2d, 1d));
         instance = new V3D_Rectangle_d(
                 new V3D_Point_d(env, 0d,0d,0d),
                 new V3D_Point_d(env, 0d,6d,0d),
                 new V3D_Point_d(env, 6d,6d,0d),
                 new V3D_Point_d(env, 6d,0d,0d));
         expResult = new V3D_LineSegment_d(new V3D_Point_d(env, 0d,1.5d,0d), new V3D_Point_d(env, 0d,3.5d,0d));
         result = instance.getIntersect(t, epsilon);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
        // Test 7
         t = new V3D_Triangle_d(new V3D_Point_d(env, 0d,2d,-2d), new V3D_Point_d(env, 0d,5d,-2d), 
                 new V3D_Point_d(env, 0d, 2d, 2d));
         instance = new V3D_Rectangle_d(
                 new V3D_Point_d(env, 0d,0d,0d),
                 new V3D_Point_d(env, 0d,6d,0d),
                 new V3D_Point_d(env, 6d,6d,0d),
                 new V3D_Point_d(env, 6d,0d,0d));
         expResult = new V3D_LineSegment_d(new V3D_Point_d(env, 0d,1.5d,0d), new V3D_Point_d(env, 0d,3.5d,0d));
         result = instance.getIntersect(t, epsilon);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
        // Test 8
         t = new V3D_Triangle_d(new V3D_Point_d(env, 0d,2d,-2000d), new V3D_Point_d(env, 0d,5d,-2000d), 
                 new V3D_Point_d(env, 0d, 2d, 2000d));
         instance = new V3D_Rectangle_d(
                 new V3D_Point_d(env, 0d,0d,0d),
                 new V3D_Point_d(env, 0d,6d,0d),
                 new V3D_Point_d(env, 6d,6d,0d),
                 new V3D_Point_d(env, 6d,0d,0d));
         expResult = new V3D_LineSegment_d(new V3D_Point_d(env, 0d,1.5d,0d), new V3D_Point_d(env, 0d,3.5d,0d));
         result = instance.getIntersect(t, epsilon);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
        // Test 9
         t = new V3D_Triangle_d(new V3D_Point_d(env, 0d,2d,-2000000d), new V3D_Point_d(env, 0d,5d,-2000000d), 
                 new V3D_Point_d(env, 0d, 2d, 2000000d));
         instance = new V3D_Rectangle_d(
                 new V3D_Point_d(env, 0d,0d,0d),
                 new V3D_Point_d(env, 0d,6d,0d),
                 new V3D_Point_d(env, 6d,6d,0d),
                 new V3D_Point_d(env, 6d,0d,0d));
         expResult = new V3D_LineSegment_d(new V3D_Point_d(env, 0d,1.5d,0d), new V3D_Point_d(env, 0d,3.5d,0d));
         result = instance.getIntersect(t, epsilon);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
        // Test 10
         t = new V3D_Triangle_d(new V3D_Point_d(env, 0d,1d,-2d), new V3D_Point_d(env, 0d,5d,-2d), 
                 new V3D_Point_d(env, 0d, 3d, 2d));
         instance = new V3D_Rectangle_d(
                 new V3D_Point_d(env, 0d,0d,0d),
                 new V3D_Point_d(env, 0d,6d,0d),
                 new V3D_Point_d(env, 6d,6d,0d),
                 new V3D_Point_d(env, 6d,0d,0d));
         expResult = new V3D_LineSegment_d(new V3D_Point_d(env, 0d,1.5d,0d), new V3D_Point_d(env, 0d,3.5d,0d));
         result = instance.getIntersect(t, epsilon);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
    }

    /**
     * Test of getIntersect method, of class V3D_Rectangle_d.
     */
    @Test
    public void testGetIntersection_V3D_Tetrahedron_int() {
//        System.out.println("getIntersect");
//        V3D_Tetrahedron t = new V3D_Tetrahedron(pP0P0P0, pP0P1P0, pP1P1P0, pP0P0P1);
//        int oom = -3;
//        V3D_Rectangle_d instance = new V3D_Rectangle_d(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
//        V3D_Geometry_d expResult = new V3D_Triangle_d(pP0P0P0, pP0P1P0, pP1P1P0);
//        V3D_Geometry_d result = instance.getIntersect(t, oom);
//        assertEquals(expResult, result);
    }

    /**
     * Test of getDistance method, of class V3D_Rectangle_d.
     */
    @Test
    public void testGetDistance_V3D_Line_int() {
        System.out.println("getDistance");
        double epsilon = 1d / 10000000d;
        V3D_Line_d l = new V3D_Line_d(pP0N1P1, pP1N1P1);
        V3D_Rectangle_d instance = new V3D_Rectangle_d(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        double expResult = 1d;
        double result = instance.getDistance(l, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Rectangle_d.
     */
    @Test
    public void testGetDistanceSquared_V3D_Line_int() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V3D_Line_d l = new V3D_Line_d(pP0N1P1, pP1N1P1);
        V3D_Rectangle_d instance = new V3D_Rectangle_d(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        double expResult = 1d;
        double result = instance.getDistanceSquared(l, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistance method, of class V3D_Rectangle_d.
     */
    @Test
    public void testGetDistance_V3D_LineSegment_d_int() {
        System.out.println("getDistance");
        double epsilon = 1d / 10000000d;
        V3D_LineSegment_d l = new V3D_LineSegment_d(pP0N1P0, pP1N1P0);
        V3D_Rectangle_d instance = new V3D_Rectangle_d(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        double expResult = 1d;
        double result = instance.getDistance(l, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Rectangle_d.
     */
    @Test
    public void testGetDistanceSquared_V3D_LineSegment_d_int() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V3D_LineSegment_d l = new V3D_LineSegment_d(pP0N1P0, pP1N1P0);
        V3D_Rectangle_d instance = new V3D_Rectangle_d(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        double expResult = 1d;
        double result = instance.getDistanceSquared(l, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistance method, of class V3D_Rectangle_d.
     */
    @Test
    public void testGetDistance_V3D_Plane_d_int() {
        System.out.println("getDistance");
        double epsilon = 1d / 10000000d;
        V3D_Plane_d p = new V3D_Plane_d(pP0N1P0, pP1N1P0, pP0N1P1);
        V3D_Rectangle_d instance = new V3D_Rectangle_d(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        double expResult = 1d;
        double result = instance.getDistance(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Rectangle_d.
     */
    @Test
    public void testGetDistanceSquared_V3D_Plane_d_int() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V3D_Plane_d p = new V3D_Plane_d(pP0N1P0, pP1N1P0, pP0N1P1);
        V3D_Rectangle_d instance = new V3D_Rectangle_d(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        double expResult = 1d;
        double result = instance.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistance method, of class V3D_Rectangle_d.
     */
    @Test
    public void testGetDistance_V3D_Triangle_d_int() {
        System.out.println("getDistance");
        double epsilon = 1d / 10000000d;
        V3D_Triangle_d t = new V3D_Triangle_d(pP0N1P0, pP1N1P0, pP0N1P1);
        V3D_Rectangle_d instance = new V3D_Rectangle_d(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        double expResult = 1d;
        double result = instance.getDistance(t, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 2
        t = new V3D_Triangle_d(pP0N1P0, pP1N1P0, pP1P1P0);
        expResult = 0d;
        result = instance.getDistance(t, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Rectangle_d.
     */
    @Test
    public void testGetDistanceSquared_V3D_Triangle_d_int() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V3D_Triangle_d t = new V3D_Triangle_d(pP0P0P0, pP0P1P0, pP1P1P0);
        V3D_Rectangle_d instance = new V3D_Rectangle_d(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        double expResult = 0d;
        double result = instance.getDistanceSquared(t, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of equals method, of class V3D_Rectangle_d.
     */
    @Test
    public void testEquals_V3D_Rectangle_d_int_RoundingMode() {
        System.out.println("equals");
        double epsilon = 1d / 10000000d;
        V3D_Rectangle_d r = new V3D_Rectangle_d(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        V3D_Rectangle_d instance = new V3D_Rectangle_d(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        assertTrue(instance.equals(r, epsilon));
        // Test 2
        instance = new V3D_Rectangle_d(pP0P1P0, pP1P1P0, pP1P0P0, pP0P0P0);
        assertTrue(instance.equals(r, epsilon));
        // Test 3
        instance = new V3D_Rectangle_d(pP1P1P0, pP1P0P0, pP0P0P0, pP0P1P0);
        assertTrue(instance.equals(r, epsilon));
        // Test 4
        instance = new V3D_Rectangle_d(pP1P0P0, pP0P0P0, pP0P1P0, pP1P1P0);
        assertTrue(instance.equals(r, epsilon));
        // Test 5
        instance = new V3D_Rectangle_d(pP0P0P0, pP1P0P0, pP1P1P0, pP0P1P0);
        assertTrue(instance.equals(r, epsilon));
        // Test 6
        instance = new V3D_Rectangle_d(pP1P0P0, pP1P1P0, pP0P1P0, pP0P0P0);
        assertTrue(instance.equals(r, epsilon));
        // Test 7
        instance = new V3D_Rectangle_d(pP1P1P0, pP0P1P0, pP0P0P0, pP1P0P0);
        assertTrue(instance.equals(r, epsilon));
        // Test 8
        instance = new V3D_Rectangle_d(pP0P1P0, pP0P0P0, pP1P0P0, pP1P1P0);
        assertTrue(instance.equals(r, epsilon));
    }

    /**
     * Test of isRectangle method, of class V3D_Rectangle_d.
     */
    @Test
    public void testIsRectangle() {
        System.out.println("isRectangle");
        double epsilon = 1d / 10000000d;
        V3D_Point_d p = pP0P0P0;
        V3D_Point_d q = pP1P0P0;
        V3D_Point_d r = pP1P1P0;
        V3D_Point_d s = pP0P1P0;
        assertTrue(V3D_Rectangle_d.isRectangle(p, q, r, s, epsilon));
        // Test 2
        assertTrue(V3D_Rectangle_d.isRectangle(p, s, r, q, epsilon));
        // Test 2
        p = pN1P0P0;
        assertFalse(V3D_Rectangle_d.isRectangle(p, q, r, s, epsilon));
    }
}
