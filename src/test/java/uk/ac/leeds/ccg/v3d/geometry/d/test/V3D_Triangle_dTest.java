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
import uk.ac.leeds.ccg.v3d.core.d.V3D_Environment_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_ConvexArea_d;
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
 * Test of V3D_Triangle_d class.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Triangle_dTest extends V3D_Test_d {

    public V3D_Triangle_dTest() {
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
     * Test of intersects method, of class V3D_Triangle_d.
     */
    @Test
    public void testIsAligned_V3D_Point_d_double() {
        System.out.println("isAligned");
        double epsilon = 1d / 10000000d;
        V3D_Point_d pt = pP0N1P0;
        V3D_Triangle_d instance = new V3D_Triangle_d(pP0P2P0, pP0N2P0, pP2P0P0);
        assertTrue(instance.intersectsCoplanar(pt, epsilon));
        pt = pN1P0P0;
        assertFalse(instance.intersectsCoplanar(pt, epsilon));
        instance = new V3D_Triangle_d(pP0P0P0, pP0P1P0, pP1P1P0);
        pt = pP0P2P2;
        assertFalse(instance.intersectsCoplanar(pt, epsilon));
        
    }

    /**
     * Test of getAABB method, of class V3D_Triangle_d.
     */
    @Test
    public void testGetAABB() {
        System.out.println("getAABB");
        V3D_Triangle_d instance = new V3D_Triangle_d(pP0P0P0, pP0P1P0, pP1P0P0);
        V3D_AABB_d expResult = new V3D_AABB_d(pP0P0P0, pP0P1P0, pP1P0P0);
        V3D_AABB_d result = instance.getAABB();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getArea method, of class V3D_Triangle_d.
     */
    @Test
    public void testGetArea() {
        System.out.println("getArea");
        double epsilon = 1d / 10000000d;
        V3D_Triangle_d instance = new V3D_Triangle_d(pP0P0P0, pP0P1P0, pP1P0P0);
        double expResult = 1d / 2d;
        double result = instance.getArea();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of translate method, of class V3D_Triangle_d.
     */
    @Test
    public void testApply() {
        // No test.
    }

    /**
     * Test of getPerimeter method, of class V3D_Triangle_d.
     */
    @Test
    public void testGetPerimeter() {
        System.out.println("getPerimeter");
        double epsilon = 1d / 10000000d;
        V3D_Triangle_d instance = new V3D_Triangle_d(pP0P0P0, pP0P1P0, pP1P0P0);
        double expResult = 2d + Math.sqrt(2);
        double result = instance.getPerimeter();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        expResult = 2d + Math.sqrt(2);
        result = instance.getPerimeter();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getIntersect method, of class V3D_Triangle_d.
     */
    @Test
    public void testGetIntersect_V3D_Line_d() {
        System.out.println("getIntersect");
        double epsilon = 1d / 10000000d;
        V3D_Line_d l = new V3D_Line_d(pP1N1P0, pP1P2P0);
        V3D_Triangle_d instance = new V3D_Triangle_d(pP0P0P0, pP1P1P0, pP2P0P0);
        V3D_Geometry_d expResult = new V3D_LineSegment_d(pP1P0P0, pP1P1P0);
        V3D_Geometry_d result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(epsilon, (V3D_LineSegment_d) result));
        System.out.println("getIntersect");
        // Test 2
        instance = new V3D_Triangle_d(env, P0P0P0, P1P0P0, P1P2P0, P2P0P0);
        l = new V3D_Line_d(pP1P0P1, pP1P0N1);
        result = instance.getIntersect(l, epsilon);
        expResult = pP1P0P0;
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 3
        l = new V3D_Line_d(pP1P0P0, pP2P0P0);
        expResult = new V3D_LineSegment_d(pP1P0P0, pP2P0P0);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(epsilon, (V3D_LineSegment_d) result));
        // Test 4
        l = new V3D_Line_d(pP1P0P0, pP2P0P0);
        expResult = new V3D_LineSegment_d(pP1P0P0, pP2P0P0);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(epsilon, (V3D_LineSegment_d) result));
    }

    /**
     * Test of getCentroid method, of class V3D_Triangle_d.
     */
    @Test
    public void testGetCentroid() {
        System.out.println("getCentroid");
        V3D_Triangle_d instance;
        V3D_Point_d expResult;
        V3D_Point_d result;
        // Test
        instance = new V3D_Triangle_d(pP0P0P0, pP1P0P0, pP1P1P0);
        expResult = new V3D_Point_d(env, 2d / 3d, 1d / 3d, 0d);
        result = instance.getCentroid();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of toString method, of class V3D_Triangle_d.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        V3D_Triangle_d instance = new V3D_Triangle_d(env, P0P0P0, P1P0P0, P0P1P0, P0P0P1);
        String expResult = """
                           V3D_Triangle_d(
                            pl=(V3D_Plane_d(
                             offset=V3D_Vector_d(dx=0.0, dy=0.0, dz=0.0),
                             pv=V3D_Vector_d(dx=1.0, dy=0.0, dz=0.0),
                             n=V3D_Vector_d(dx=1.0, dy=1.0, dz=1.0))),
                            offset=(V3D_Vector_d(dx=0.0, dy=0.0, dz=0.0)),
                            p=(V3D_Vector_d(dx=1.0, dy=0.0, dz=0.0)),
                            q=(V3D_Vector_d(dx=0.0, dy=1.0, dz=0.0)),
                            r=(V3D_Vector_d(dx=0.0, dy=0.0, dz=1.0)))""";
        String result = instance.toString();
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of intersects method, of class V3D_Triangle_d.
     */
    @Test
    public void testIntersectsPoint_d() {
        System.out.println("intersects");
        double epsilon = 1d / 10000000d;
        V3D_Point_d pt;
        V3D_Triangle_d instance;
        instance = new V3D_Triangle_d(env, P0P0P0, P1P0P0, P0P1P0, P0P0P1);
        assertTrue(instance.intersects(pP1P0P0, epsilon));
        assertTrue(instance.intersects(pP0P1P0, epsilon));
        assertTrue(instance.intersects(pP0P0P1, epsilon));
        pt = new V3D_Point_d(env, P1P0P0, P0P0P0);
        assertTrue(instance.intersects(pt, epsilon));
        // Test 5
        pt = new V3D_Point_d(env, P0P1P0, P0P0P0);
        assertTrue(instance.intersects(pt, epsilon));
        // Test 6
        pt = new V3D_Point_d(env, P0P0P1, P0P0P0);
        assertTrue(instance.intersects(pt, epsilon));
        // Test 7
        pt = new V3D_Point_d(env, P1P0P0, P1P0P0);
        assertFalse(instance.intersects(pt, epsilon));
        // Test 8
        pt = new V3D_Point_d(env, P0P1P0, P0P1P0);
        assertFalse(instance.intersects(pt, epsilon));
        // Test 9
        pt = new V3D_Point_d(env, P0P0P1, P0P0P1);
        assertFalse(instance.intersects(pt, epsilon));
        // Test 10
        instance = new V3D_Triangle_d(env, P0P0P0, P1P0P0, P1P2P0, P2P0P0);
        pt = new V3D_Point_d(env, P0P0P0, new V3D_Vector_d(1d, 3d, 0d));
        assertFalse(instance.intersects(pt, epsilon));
        assertTrue(instance.intersects(pP1P2P0, epsilon));
        assertTrue(instance.intersects(pP1P1P0, epsilon));
        assertFalse(instance.intersects(pP0P0P0, epsilon));
        pt = new V3D_Point_d(env, P0P0P0, new V3D_Vector_d(3d / 2d, 1d, 0d));
        assertTrue(instance.intersects(pt, epsilon));
    }
    
    /**
     * Test of contains method, of class V3D_Triangle.
     */
    @Test
    public void testContainsPoint() {
        System.out.println("contains point");
        int oom = -3;
        double epsilon = 0.000001d;
        V3D_Environment_d env = new V3D_Environment_d();
        V3D_Point_d pt = pP1P1P1;
        V3D_Triangle_d instance = new V3D_Triangle_d(pP0P0P0, pP1P0P0, pP1P1P0);
        assertFalse(instance.contains(pt, epsilon));
        // Test 2
        pt = pN1N1P0;
        assertFalse(instance.contains(pt, epsilon));
        // Test 3
        pt = new V3D_Point_d(env, 0.5d, 0.5d, 0d);
        assertFalse(instance.contains(pt, epsilon));
        pt = new V3D_Point_d(env, 0.75d, 0.5d, 0d);
        assertTrue(instance.contains(pt, epsilon));
        assertFalse(instance.contains(pP0P0P0, epsilon));
        assertFalse(instance.contains(pP1P0P0, epsilon));
        assertFalse(instance.contains(pP1P1P0, epsilon));
        pt = new V3D_Point_d(env, 0.5d, 0d, 0d);
        assertFalse(instance.contains(pt, epsilon));
    }
    
    /**
     * Test of contains method, of class V3D_Triangle.
     */
    @Test
    public void testContainsLineSegement() {
        System.out.println("contains line segment");
        double epsilon = 0.000001d;
        V3D_Environment_d env = new V3D_Environment_d();
        V3D_LineSegment_d l = new V3D_LineSegment_d(pP0P0P0, pP1P0P0);
        V3D_Triangle_d instance = new V3D_Triangle_d(pP0P0P0, pP1P0P0, pP1P1P0);
        assertFalse(instance.contains(l, epsilon));
        V3D_Point_d a = new V3D_Point_d(env, 0.5d, 0.5d, 0d);
        V3D_Point_d b = new V3D_Point_d(env, 0.75d, 0.5d, 0d);
        l = new V3D_LineSegment_d(a, b);
        assertFalse(instance.contains(l, epsilon));
        a = new V3D_Point_d(env, 0.75d, 0.25d, 0d);
        l = new V3D_LineSegment_d(a, b);
        assertTrue(instance.contains(l, epsilon));
    }
    
    /**
     * Test of contains method, of class V3D_Triangle.
     */
    @Test
    public void testContainsTriangle() {
        System.out.println("contains triangle");
        double epsilon = 0.000001d;
        V3D_Environment_d env = new V3D_Environment_d();
        V3D_Triangle_d t = new V3D_Triangle_d(pP0P0P0, pP1P0P0, pP1P1P0);
        V3D_Triangle_d instance = new V3D_Triangle_d(pP0P0P0, pP1P0P0, pP1P1P0);
        assertFalse(instance.contains(t, epsilon));
        V3D_Point_d a = new V3D_Point_d(env, 0.5d, 0.5d, 0d);
        V3D_Point_d b = new V3D_Point_d(env, 0.75d, 0.5d, 0d);
        V3D_Point_d c = new V3D_Point_d(env, 0.5d, 0.25d, 0d);
        t = new V3D_Triangle_d(a, b, c);
        assertFalse(instance.contains(t, epsilon));
        a = new V3D_Point_d(env, 0.75d, 0.25d, 0d);
        t = new V3D_Triangle_d(a, b, c);
        assertTrue(instance.contains(t, epsilon));
    }

    /**
     * Test of getIntersect method, of class V3D_Triangle_d.
     */
    @Test
    public void testGetIntersect_V3D_LineSegment_d() {
        System.out.println("getIntersect");
        double epsilon = 1d / 10000000d;
        V3D_LineSegment_d l;
        V3D_Triangle_d instance;
        V3D_Geometry_d expResult;
        V3D_Geometry_d result;
        // Test 1
        instance = new V3D_Triangle_d(pP1P0P0, pP1P2P0, pP2P0P0);
        l = new V3D_LineSegment_d(pP1P0P1, pP1P0N1);
        expResult = pP1P0P0;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 2
        l = new V3D_LineSegment_d(pP1P0P0, pP2P0P0);
        expResult = new V3D_LineSegment_d(pP1P0P0, pP2P0P0);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
        // Test 3
        l = new V3D_LineSegment_d(pP1P0P0, pP2P0P0);
        expResult = new V3D_LineSegment_d(pP1P0P0, pP2P0P0);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
        // Test 4
        l = new V3D_LineSegment_d(pP2N2P0, pP2P1P0);
        instance = new V3D_Triangle_d(env, P0P0P0, P2P2P0,
                new V3D_Vector_d(4d, 0d, 0d));
        expResult = new V3D_LineSegment_d(pP2P0P0, pP2P1P0);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
        // Test 5
        l = new V3D_LineSegment_d(pP0P0P0, pP0P1P0);
        instance = new V3D_Triangle_d(pN2N2P0, pP0P2P0, pP2N2P0);
        expResult = new V3D_LineSegment_d(pP0P0P0, pP0P1P0);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
        // Test 6
        l = new V3D_LineSegment_d(new V3D_Point_d(env, 4d, -2d, 0d), pP2P0P0);
        instance = new V3D_Triangle_d(pP0P0P0, new V3D_Point_d(env, 4d, 0d, 0d), new V3D_Point_d(env, 2d, -4d, 0d));
        expResult = new V3D_LineSegment_d(pP2P0P0, new V3D_Point_d(env, 10d/3d, -4d/3d, 0d));
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
    }

    /**
     * Test of equals method, of class V3D_Triangle_d.
     */
    @Test
    public void testEquals_V3D_Triangle_d() {
        System.out.println("equals");
        double epsilon = 1d / 10000000d;
        V3D_Triangle_d t = new V3D_Triangle_d(pP1P0P0, pP1P2P0, pP2P0P0);
        V3D_Triangle_d instance = new V3D_Triangle_d(env, P1P0P0, P0P0P0, P0P2P0, P1P0P0);
        boolean result = instance.equals(t, epsilon);
        assertTrue(result);
        // Test 2
        instance = new V3D_Triangle_d(env, P1P0P0, P0P2P0, P0P0P0, P1P0P0);
        result = instance.equals(t, epsilon);
        assertTrue(result);
        // Test 3
        instance = new V3D_Triangle_d(env, P1P0P0, P0P2P0, P1P0P0, P0P0P0);
        result = instance.equals(t, epsilon);
        assertTrue(result);
        // Test 4
        instance = new V3D_Triangle_d(env, P1P0P0, P1P0P0, P0P2P0, P0P0P0);
        result = instance.equals(t, epsilon);
        assertTrue(result);
    }

    /**
     * Test of rotate method, of class V3D_Triangle_d.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        double epsilon = 1d / 10000000d;
        double theta;
        V3D_Triangle_d instance;
        V3D_Triangle_d expResult;
        double Pi = Math.PI;
        // Test 1
        instance = new V3D_Triangle_d(pP1P0P0, pP0P1P0, pP1P1P0);
        V3D_Ray_d xaxis = new V3D_Ray_d(pP0P0P0, pP1P0P0);
        theta = Pi;
        instance = instance.rotate(xaxis, xaxis.l.v, theta, epsilon);
        expResult = new V3D_Triangle_d(pP1P0P0, pP0N1P0, pP1N1P0);
        assertTrue(expResult.equals(instance, epsilon));
        assertTrue(expResult.pl.equals(instance.pl, epsilon));
        // Test 2
        instance = new V3D_Triangle_d(pP1P0P0, pP0P1P0, pP1P1P0);
        theta = Pi / 2d;
        instance = instance.rotate(xaxis, xaxis.l.v, theta, epsilon);
        expResult = new V3D_Triangle_d(pP1P0P0, pP0P0P1, pP1P0P1);
        assertTrue(expResult.equals(instance, epsilon));
        assertTrue(expResult.pl.equals(instance.pl, epsilon));
        // Test 3
        instance = new V3D_Triangle_d(pP2P0P0, pP0P2P0, pP2P2P0);
        theta = Pi;
        instance = instance.rotate(xaxis, xaxis.l.v,  theta, epsilon);
        expResult = new V3D_Triangle_d(pP2P0P0, pP0N2P0, pP2N2P0);
        assertTrue(expResult.equals(instance, epsilon));
        assertTrue(expResult.pl.equals(instance.pl, epsilon));
        // Test 4
        instance = new V3D_Triangle_d(pP2P0P0, pP0P2P0, pP2P2P0);
        theta = Pi / 2d;
        instance = instance.rotate(xaxis, xaxis.l.v, theta, epsilon);
        expResult = new V3D_Triangle_d(pP2P0P0, pP0P0P2, pP2P0P2);
        assertTrue(expResult.equals(instance, epsilon));
        assertTrue(expResult.pl.equals(instance.pl, epsilon));
        // Test 5
        instance = new V3D_Triangle_d(pP1P0P0, pP0P1P0, pP1P1P0);
        instance.translate(P1P0P0);
        theta = Pi;
        instance = instance.rotate(xaxis, xaxis.l.v, theta, epsilon);
        expResult = new V3D_Triangle_d(pP2P0P0, pP1N1P0, pP2N1P0);
        assertTrue(expResult.equals(instance, epsilon));
        assertTrue(expResult.pl.equals(instance.pl, epsilon));
        
        // Test 6
        instance = new V3D_Triangle_d(pP1P0P0, pP0P1P0, pP1P1P0);
        V3D_Ray_d yaxis = new V3D_Ray_d(pP0P0P0, pP0P1P0);
        theta = Pi;
        instance = instance.rotate(yaxis, yaxis.l.v, theta, epsilon);
        expResult = new V3D_Triangle_d(pN1P0P0, pP0P1P0, pN1P1P0);
        assertTrue(expResult.equals(instance, epsilon));
        assertTrue(expResult.pl.equals(instance.pl, epsilon));
        // Test 7
        instance = new V3D_Triangle_d(pP1P0P0, pP0P1P0, pP1P1P0);
        theta = Pi / 2d;
        instance = instance.rotate(yaxis, yaxis.l.v, theta, epsilon);
        expResult = new V3D_Triangle_d(pP0P0N1, pP0P1P0, pP0P1N1);
        assertTrue(expResult.equals(instance, epsilon));
        assertTrue(expResult.pl.equals(instance.pl, epsilon));
        // Test 8
        instance = new V3D_Triangle_d(pP2P0P0, pP0P2P0, pP2P2P0);
        theta = Pi;
        instance = instance.rotate(yaxis, yaxis.l.v, theta, epsilon);
        expResult = new V3D_Triangle_d(pN2P0P0, pP0P2P0, pN2P2P0);
        assertTrue(expResult.equals(instance, epsilon));
        assertTrue(expResult.pl.equals(instance.pl, epsilon));
        // Test 9
        instance = new V3D_Triangle_d(pP2P0P0, pP0P2P0, pP2P2P0);
        theta = Pi / 2d;
        instance = instance.rotate(yaxis, yaxis.l.v, theta, epsilon);
        expResult = new V3D_Triangle_d(pP0P0N2, pP0P2P0, pP0P2N2);
        assertTrue(expResult.equals(instance, epsilon));
        assertTrue(expResult.pl.equals(instance.pl, epsilon));
        // Test 10
        instance = new V3D_Triangle_d(pP1P0P0, pP0P1P0, pP1P1P0);
        instance.translate(P1P0P0);
        theta = Pi;
        instance = instance.rotate(yaxis, yaxis.l.v, theta, epsilon);
        expResult = new V3D_Triangle_d(pN2P0P0, pN1P1P0, pN2P1P0);
        assertTrue(expResult.equals(instance, epsilon));
        assertTrue(expResult.pl.equals(instance.pl, epsilon));
        // Test 11
        instance = new V3D_Triangle_d(pP1P0P0, pP0P1P0, pP1P1P0);
        V3D_Ray_d zaxis = new V3D_Ray_d(pP0P0P0, pP0P0P1);
        theta = Pi;
        instance = instance.rotate(zaxis, zaxis.l.v, theta, epsilon);
        expResult = new V3D_Triangle_d(pN1P0P0, pP0N1P0, pN1N1P0);
        assertTrue(expResult.equals(instance, epsilon));
        assertTrue(expResult.pl.equals(instance.pl, epsilon));
        // Test 12
        instance = new V3D_Triangle_d(pP1P0P0, pP0P1P0, pP1P1P0);
        theta = Pi / 2d;
        instance = instance.rotate(zaxis, zaxis.l.v, theta, epsilon);
        expResult = new V3D_Triangle_d(pP0P1P0, pN1P0P0, pN1P1P0);
        assertTrue(expResult.equals(instance, epsilon));
        assertTrue(expResult.pl.equals(instance.pl, epsilon));
        // Test 13
        instance = new V3D_Triangle_d(pP2P0P0, pP0P2P0, pP2P2P0);
        theta = Pi;
        instance = instance.rotate(zaxis, zaxis.l.v, theta, epsilon);
        expResult = new V3D_Triangle_d(pN2P0P0, pP0N2P0, pN2N2P0);
        assertTrue(expResult.equals(instance, epsilon));
        assertTrue(expResult.pl.equals(instance.pl, epsilon));
        instance = new V3D_Triangle_d(pP2P0P0, pP0P2P0, pP2P2P0);
        theta = Pi / 2d;
        instance = instance.rotate(zaxis, zaxis.l.v, theta, epsilon);
        expResult = new V3D_Triangle_d(pP0P2P0, pN2P0P0, pN2P2P0);
        assertTrue(expResult.equals(instance, epsilon));
        assertTrue(expResult.pl.equals(instance.pl, epsilon));
        // Test 15
        instance = new V3D_Triangle_d(pP1P0P0, pP0P1P0, pP1P1P0);
        instance.translate(P1P0P0);
        theta = Pi;
        instance = instance.rotate(zaxis, zaxis.l.v, theta, epsilon);
        expResult = new V3D_Triangle_d(pN2P0P0, pN1N1P0, pN2N1P0);
        assertTrue(expResult.equals(instance, epsilon));
        assertTrue(expResult.pl.equals(instance.pl, epsilon));
    }

    /**
     * Test of getIntersect method, of class V3D_Triangle_d.
     */
    @Test
    public void testGetIntersect_V3D_Plane_d() {
        System.out.println("getIntersect");
        double epsilon = 1d / 10000000d;
        V3D_Triangle_d t;
        V3D_Plane_d p;
        V3D_Geometry_d expResult;
        V3D_Geometry_d result;
        t = new V3D_Triangle_d(pP0P0P0, pP1P0P0, pP0P1P0);
        // Test 1
        p = V3D_Plane_d.X0;
        expResult = new V3D_LineSegment_d(pP0P0P0, pP0P1P0);
        result = t.getIntersect(p, epsilon);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
        // Test 2
        p = V3D_Plane_d.Y0;
        expResult = new V3D_LineSegment_d(pP0P0P0, pP1P0P0);
        result = t.getIntersect(p, epsilon);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
        // Test 3
        p = V3D_Plane_d.Z0;
        expResult = t;
        result = t.getIntersect(p, epsilon);
        assertTrue(((V3D_Triangle_d) expResult).equals((V3D_Triangle_d) result));
        // Test 4
        t = new V3D_Triangle_d(pN1P0P0, pP1P0P0, pP0P1P0);
        p = V3D_Plane_d.X0;
        expResult = new V3D_LineSegment_d(pP0P0P0, pP0P1P0);
        result = t.getIntersect(p, epsilon);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
        // Test 5
        t = new V3D_Triangle_d(pN2N2N2, pN2N2P2, pP2P0P0);
        p = V3D_Plane_d.X0;
        expResult = new V3D_LineSegment_d(pP0N1P1, pP0N1N1);
        result = t.getIntersect(p, epsilon);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
        // Test 6
        t = new V3D_Triangle_d(env, P0P0P0, P1P0P0, P0P1P0, P1P1P0);
        p = V3D_Plane_d.X0;
        expResult = pP0P1P0;
        result = t.getIntersect(p, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 7
        p = new V3D_Plane_d(V3D_Plane_d.X0);
        p.translate(P1P0P0);
        expResult = new V3D_LineSegment_d(pP1P0P0, pP1P1P0);
        result = t.getIntersect(p, epsilon);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
        // Test 8
        p = new V3D_Plane_d(V3D_Plane_d.Z0);
        expResult = new V3D_Triangle_d(t);
        result = t.getIntersect(p, epsilon);
        assertTrue(((V3D_Triangle_d) expResult).equals((V3D_Triangle_d) result, epsilon));
    }

    /**
     * Test of getIntersect method, of class V3D_Triangle_d.
     */
    @Test
    public void testGetIntersect_V3D_Ray_d() {
        System.out.println("getIntersect");
        double epsilon = 1d / 10000000d;
        V3D_Triangle_d t;
        V3D_Ray_d r;
        V3D_Geometry_d expResult;
        V3D_Geometry_d result;
        // Test 1
        t = new V3D_Triangle_d(pP0P1P0, pP1P0P0, pP1P1P0);
        r = new V3D_Ray_d(pP0P0P0, pP1P0P0);
        result = t.getIntersect(r, epsilon);
        expResult = pP1P0P0;
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 2
        t = new V3D_Triangle_d(pP0N2P0, pP0P2P0, pP2P0P0);
        r = new V3D_Ray_d(pP1P0P0, pP2P0P0);
        result = t.getIntersect(r, epsilon);
        expResult = new V3D_LineSegment_d(pP1P0P0, pP2P0P0);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
        // Test 3
        r = new V3D_Ray_d(pN1P0P0, pP2P0P0);
        result = t.getIntersect(r, epsilon);
        expResult = new V3D_LineSegment_d(pP0P0P0, pP2P0P0);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
        // Test 4
        r = new V3D_Ray_d(pN2P0N2, pP0P0P0);
        result = t.getIntersect(r, epsilon);
        expResult = pP0P0P0;
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 5
        r = new V3D_Ray_d(pN2P0N2, pP0N1P0);
        expResult = pP0N1P0;
        result = t.getIntersect(r, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 6
        r = new V3D_Ray_d(pN2P0N2, pN1P0P0);
        assertNull(t.getIntersect(r, epsilon));
        // Test 7
        t = new V3D_Triangle_d(pP0N2P0, pP0P2P0, pP2P0P0);
        r = new V3D_Ray_d(pN2N2N2, pN1N1N1);
        expResult = pP0P0P0;
        result = t.getIntersect(r, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 8
        t = new V3D_Triangle_d(pP0N2P0, pP0P2P0, pP2P2P1);
        r = new V3D_Ray_d(pN2N2N2, pN1N1N1);
        expResult = pP0P0P0;
        result = t.getIntersect(r, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 9
        t = new V3D_Triangle_d(pN1N2P0, pN1P2P0, pP2P2P0);
        r = new V3D_Ray_d(pN2N2N2, pN1N1N1);
        expResult = pP0P0P0;
        result = t.getIntersect(r, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 10
        t = new V3D_Triangle_d(pN1N2P0, pN1P2P0, pP2P2N1);
        r = new V3D_Ray_d(pN2N2N2, pN1N1N1);
        double nq = -1d / 4d;
        expResult = new V3D_Point_d(env, nq, nq, nq);
        result = t.getIntersect(r, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));

        // Box test.
        V3D_Vector_d offset = V3D_Vector_d.ZERO;
        V3D_Point_d[] points = new V3D_Point_d[8];
        V3D_Point_d centroid = V3D_Point_d.ORIGIN;
        long multiplier = 100;
        V3D_Point_d lbf = new V3D_Point_d(env, offset, new V3D_Vector_d(-1 * multiplier, -1 * multiplier, -1 * multiplier));
        V3D_Point_d lba = new V3D_Point_d(env, offset, new V3D_Vector_d(-1 * multiplier, -1 * multiplier, 1 * multiplier));
        V3D_Point_d ltf = new V3D_Point_d(env, offset, new V3D_Vector_d(-1 * multiplier, 1 * multiplier, -1 * multiplier));
        V3D_Point_d lta = new V3D_Point_d(env, offset, new V3D_Vector_d(-1 * multiplier, 1 * multiplier, 1 * multiplier));
        V3D_Point_d rbf = new V3D_Point_d(env, offset, new V3D_Vector_d(1 * multiplier, -1 * multiplier, -1 * multiplier));
        V3D_Point_d rba = new V3D_Point_d(env, offset, new V3D_Vector_d(1 * multiplier, -1 * multiplier, 1 * multiplier));
        V3D_Point_d rtf = new V3D_Point_d(env, offset, new V3D_Vector_d(1 * multiplier, 1 * multiplier, -1 * multiplier));
        V3D_Point_d rta = new V3D_Point_d(env, offset, new V3D_Vector_d(1 * multiplier, 1 * multiplier, 1 * multiplier));
        // BLUE
        V3D_Triangle_d b1 = new V3D_Triangle_d(lbf, ltf, rtf);
        V3D_Triangle_d b2 = new V3D_Triangle_d(lbf, rbf, rtf);
        // RED
        V3D_Triangle_d r1 = new V3D_Triangle_d(lbf, ltf, lta);
        V3D_Triangle_d r2 = new V3D_Triangle_d(lbf, lba, lta);
        // YELLOW
        V3D_Triangle_d y1 = new V3D_Triangle_d(lba, lta, rta);
        V3D_Triangle_d y2 = new V3D_Triangle_d(lba, rba, rta);
        // GREEN
        V3D_Triangle_d g1 = new V3D_Triangle_d(rbf, rtf, rta);
        V3D_Triangle_d g2 = new V3D_Triangle_d(rbf, rta, rba);
        // ORANGE
        V3D_Triangle_d o1 = new V3D_Triangle_d(ltf, lta, rta);
        V3D_Triangle_d o2 = new V3D_Triangle_d(rtf, ltf, rta);
        // PINK
        V3D_Triangle_d p1 = new V3D_Triangle_d(lbf, rbf, rba);
        V3D_Triangle_d p2 = new V3D_Triangle_d(lbf, lba, rba);
        points[0] = lbf;
        points[1] = lba;
        points[2] = ltf;
        points[3] = lta;
        points[4] = rbf;
        points[5] = rba;
        points[6] = rtf;
        points[7] = rta;
        V3D_AABB_d envelope = new V3D_AABB_d(points);
        int width = 100;
        double radius = envelope.getPointsArray()[0].getDistance(centroid);
        V3D_Vector_d direction = new V3D_Vector_d(0, 0, 1).getUnitVector();
        V3D_Point_d pt = new V3D_Point_d(centroid);
        pt.translate(direction.multiply(radius * 2d));
        V3D_Plane_d pl = new V3D_Plane_d(pt, new V3D_Vector_d(pt, envelope.getCentroid()));
        V3D_Vector_d pv = pl.getPV();
        double zoomFactor = 1.0d;
        V3D_Rectangle_d screen = envelope.getViewport3(pt, pv, zoomFactor, epsilon);
        V3D_Triangle_d pqr = screen.getPQR();
        double screenWidth = pqr.getPQ().getLength();
        double screenHeight = screenWidth;
        double pixelSize = screenWidth / (double) width;
        V3D_Vector_d vd = new V3D_Vector_d(pqr.getQR().l.v).divide(
                (double) width);
        V3D_Vector_d v2d = new V3D_Vector_d(pqr.getPQ().l.v).divide(
                (double) width);
        int row = width / 3;
        int col = width / 2;
        r = getRay(row, col, screen, pt, vd, v2d, epsilon);

//        expResult = new V3D_Point_d(nq, nq, nq);
//        result = b1.getIntersect(r);
//        result = b2.getIntersect(r);
//        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
    }

    /**
     * For getting a ray from the camera focal point through the centre of the
     * screen pixel with ID id.
     *
     * @param id The ID of the screen pixel.
     * @return The ray from the camera focal point through the centre of the
     * screen pixel.
     */
    protected V3D_Ray_d getRay(int row, int col, V3D_Rectangle_d screen,
            V3D_Point_d pt, V3D_Vector_d vd, V3D_Vector_d v2d,
            double epsilon) {
        V3D_Vector_d rv = v2d.multiply((double) row);
        V3D_Vector_d cv = vd.multiply((double) col);
        V3D_Point_d rcpt = new V3D_Point_d(screen.getP());
        rcpt.translate(rv.add(cv));
        return new V3D_Ray_d(pt, rcpt);
    }

    /**
     * Test of getIntersect method, of class V3D_Triangle_d.
     *
     * Look for some examples here:
     * https://math.stackexchange.com/questions/1220102/how-do-i-find-the-intersection-of-two-3d-triangles
     */
    @Test
    public void testGetIntersect_V3D_Triangle_d() {
        System.out.println("getIntersect");
        double epsilon = 1d / 10000000d;
        V3D_Triangle_d t = new V3D_Triangle_d(pP0P0P0, pP0P1P0, pP1P0P0);
        V3D_Triangle_d instance = new V3D_Triangle_d(pP0P0P0, pP0P1P0, pP1P0P0);
        V3D_Geometry_d expResult = new V3D_Triangle_d(pP0P0P0, pP0P1P0, pP1P0P0);
        V3D_Geometry_d result = instance.getIntersect(t, epsilon);
        assertTrue(((V3D_Triangle_d) expResult).equals((V3D_Triangle_d) result, epsilon));
        // Test 2
        t = new V3D_Triangle_d(pN1N1P0, pP0P2P0, new V3D_Point_d(env, 3d, -1d, 0d));
        instance = new V3D_Triangle_d(pP0P0P0, pP0P1P0, pP1P0P0);
        expResult = new V3D_Triangle_d(pP0P0P0, pP0P1P0, pP1P0P0);
        result = instance.getIntersect(t, epsilon);
        assertTrue(((V3D_Triangle_d) expResult).equals((V3D_Triangle_d) result, epsilon));
        // Test 3
        t = new V3D_Triangle_d(pP0P0P0, pP0P1P0, pP1P0P0);
        instance = new V3D_Triangle_d(pN1N1P0, pP0P2P0, new V3D_Point_d(env, 3d, -1d, 0d));
        expResult = new V3D_Triangle_d(pP0P0P0, pP0P1P0, pP1P0P0);
        result = instance.getIntersect(t, epsilon);
        assertTrue(((V3D_Triangle_d) expResult).equals((V3D_Triangle_d) result, epsilon));
        // Test 4
        t = new V3D_Triangle_d(pP0P0P0, pP2P0P0, pP2P2P0);
        instance = new V3D_Triangle_d(pP1P0P0, new V3D_Point_d(env, 3d, 0d, 0d), new V3D_Point_d(env, 3d, 2d, 0d));
        expResult = new V3D_Triangle_d(pP1P0P0, pP2P0P0, pP2P1P0);
        result = instance.getIntersect(t, epsilon);
        assertTrue(((V3D_Triangle_d) expResult).equals((V3D_Triangle_d) result, epsilon));
        // Test 5: From  https://stackoverflow.com/a/29563443/1998054
        t = new V3D_Triangle_d(new V3D_Point_d(env, -21, -72, 63),
                new V3D_Point_d(env, -78, 99, 40),
                new V3D_Point_d(env, -19, -78, -83));
        instance = new V3D_Triangle_d(new V3D_Point_d(env, 96, 77, -51),
                new V3D_Point_d(env, -95, -1, -16),
                new V3D_Point_d(env, 9, 5, -21));
        // This expected result is not given in the answer on stack overflow.
        expResult = new V3D_LineSegment_d(
                new V3D_Point_d(env, -34.630630630630634, -31.108108108108105, -5.95495495495496),
                new V3D_Point_d(env, -48.45827629341561, 10.37482888024681, -21.586983320506448));
        result = instance.getIntersect(t, epsilon);
        //System.out.println(result);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(epsilon, (V3D_LineSegment_d) result));
        // Test 6: From https://web.mst.edu/~chaman/home/pubs/2015WimoTriangleTrianglePublished.pdf
        t = new V3D_Triangle_d(new V3D_Point_d(env, 0, 0, 0),
                new V3D_Point_d(env, 6, 0, 0),
                new V3D_Point_d(env, 0, 6, 0));
        instance = new V3D_Triangle_d(new V3D_Point_d(env, 0, 3, 3),
                new V3D_Point_d(env, 0, 3, -3),
                new V3D_Point_d(env, -3, 3, 3));
        // This expected result is not given in the answer on stack overflow.
        expResult = new V3D_Point_d(env, 0, 3, 0);
        result = instance.getIntersect(t, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result, epsilon));
        // Test 7: From https://web.mst.edu/~chaman/home/pubs/2015WimoTriangleTrianglePublished.pdf
        t = new V3D_Triangle_d(new V3D_Point_d(env, 0, 6, 0),
                new V3D_Point_d(env, 6, 0, 0),
                new V3D_Point_d(env, 0, 0, 0));
        instance = new V3D_Triangle_d(new V3D_Point_d(env, 1, 3, 0),
                new V3D_Point_d(env, 3, 1, 0),
                new V3D_Point_d(env, 2, 2, 4));
        result = instance.getIntersect(t, epsilon);
        expResult = new V3D_LineSegment_d(new V3D_Point_d(env, 1, 3, 0),
                new V3D_Point_d(env, 3, 1, 0));
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
        // Test 8: From https://math.stackexchange.com/questions/1220102/how-do-i-find-the-intersection-of-two-3d-triangles
        t = new V3D_Triangle_d(new V3D_Point_d(env, 6, 8, 3),
                new V3D_Point_d(env, 6, 8, -2),
                new V3D_Point_d(env, 6, -4, -2));
        instance = new V3D_Triangle_d(new V3D_Point_d(env, 0, 5, 0),
                new V3D_Point_d(env, 0, 0, 0),
                new V3D_Point_d(env, 8, 0, 0));
        result = instance.getIntersect(t, epsilon);
        expResult = new V3D_LineSegment_d(new V3D_Point_d(env, 6, 1.25d, 0),
                new V3D_Point_d(env, 6, 0.8d, 0));
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
        // Test 9: 4 sides
        t = new V3D_Triangle_d(new V3D_Point_d(env, 2, -3, 0), 
                new V3D_Point_d(env, 6, 1, 0), new V3D_Point_d(env, 2, 5, 0));
        instance = new V3D_Triangle_d(pP1P0P0, new V3D_Point_d(env, 3, 0, 0), 
                new V3D_Point_d(env, 3, 2, 0));
        System.out.println(pP1P0P0.toString());
        expResult = new V3D_ConvexArea_d(epsilon,
                new V3D_Triangle_d(pP2P0P0, new V3D_Point_d(env, 3, 0, 0),
                        pP2P1P0),
                new V3D_Triangle_d(
                        new V3D_Point_d(env, 3, 0, 0),
                        new V3D_Point_d(env, 3, 2, 0),
                        pP2P1P0));
        result = instance.getIntersect(t, epsilon);
        //System.out.println(result);
        assertTrue(((V3D_ConvexArea_d) expResult).equals((V3D_ConvexArea_d) result, epsilon));
        // Test 10: 5 sides
        epsilon = 1d / 10000000000d;
        t = new V3D_Triangle_d(pP0P0P0, new V3D_Point_d(env, 4, 0, 0), 
                new V3D_Point_d(env, 2, -4, 0));
        instance = new V3D_Triangle_d(pP0N2P0, pP2P0P0, 
                new V3D_Point_d(env, 4, -2, 0));
        V3D_Point_d m = new V3D_Point_d(env, 2d/3d, -4d/3d, 0);
        V3D_Point_d n = new V3D_Point_d(env, 10d/3d, -4d/3d, 0);
        expResult = new V3D_ConvexArea_d(epsilon,
                new V3D_Triangle_d(pP2P0P0, m, n),
                //new V3D_Triangle_d(m, n, pP1N2P0),
                new V3D_Triangle_d(m, n, new V3D_Point_d(env, 3, -2, 0)),
                new V3D_Triangle_d(pP1N2P0,
                        new V3D_Point_d(env, 3, -2, 0),
                        n));
        result = instance.getIntersect(t, epsilon);
        //System.out.println(((V3D_ConvexArea_d) expResult).toStringSimple());
        //System.out.println(((V3D_ConvexArea_d) result).toStringSimple());
        assertTrue(((V3D_ConvexArea_d) expResult).equals((V3D_ConvexArea_d) result, epsilon));
//        // Test 11: 6 sides
//        t = new V3D_Triangle_d(pP0P0P0, new V3D_Point_d(6, 0, 0), 
//                new V3D_Point_d(3, -3, 0));
//        instance = new V3D_Triangle_d(pP0N2P0, new V3D_Point_d(3, 1, 0),
//                new V3D_Point_d(6, -2, 0));
//        expResult = new V3D_ConvexArea_d(epsilon,
//                new V3D_Triangle_d(pP2P0P0, new V3D_Point_d(4, 0, 0), pP1N1P0),
//                new V3D_Triangle_d(pP1N1P0, pP2N2P0, new V3D_Point_d(4, -2, 0)),
//                new V3D_Triangle_d(
//                        new V3D_Point_d(4, 0, 0),
//                        new V3D_Point_d(4, -2, 0),
//                        new V3D_Point_d(5, -1, 0)),
//                new V3D_Triangle_d(new V3D_Point_d(4, 0, 0), pP1N1P0,
//                        new V3D_Point_d(4, -2, 0)));
//        result = instance.getIntersect(t, epsilon);
//        //System.out.println(result);
//        assertTrue(((V3D_ConvexArea_d) expResult).equals((V3D_ConvexArea_d) result, epsilon));
//        // Test 12: 6 sides
//        t = new V3D_Triangle_d(new V3D_Point_d(6, 0, 0), pP0P0P0, new V3D_Point_d(3, -3, 0));
//        instance = new V3D_Triangle_d(pP0N2P0, new V3D_Point_d(3, 1, 0), new V3D_Point_d(6, -2, 0));
//        expResult = new V3D_ConvexArea_d(epsilon,
//                new V3D_Triangle_d(pP2P0P0, new V3D_Point_d(4, 0, 0), pP1N1P0),
//                new V3D_Triangle_d(pP1N1P0, pP2N2P0, new V3D_Point_d(4, 2, 0)),
//                new V3D_Triangle_d(
//                        new V3D_Point_d(4, 0, 0),
//                        new V3D_Point_d(4, -2, 0),
//                        new V3D_Point_d(5, -1, 0)),
//                new V3D_Triangle_d(new V3D_Point_d(4, 0, 0), pP1N1P0,
//                        new V3D_Point_d(4, 2, 0)));
//        result = instance.getIntersect(t, epsilon);
//        //System.out.println(result);
//        assertTrue(((V3D_ConvexArea_d) expResult).equals((V3D_ConvexArea_d) result, epsilon));

    }

    /**
     * Test of translate method, of class V3D_Triangle_d.
     */
    @Test
    public void testTranslate() {
        System.out.println("translate");
        double epsilon = 1d / 10000000d;
        V3D_Triangle_d instance = new V3D_Triangle_d(env, P0P0P0, P1P0P0, P0P1P0, P1P1P0);
        instance.translate(V3D_Vector_d.I);
        V3D_Triangle_d expResult = new V3D_Triangle_d(env, P0P0P0, P2P0P0, P1P1P0, P2P1P0);
        assertTrue(expResult.equals(instance, epsilon));
        // Test 2
        instance = new V3D_Triangle_d(env, P0P0P0, P1P0P0, P0P1P0, P1P1P0);
        instance.translate(V3D_Vector_d.IJK);
        expResult = new V3D_Triangle_d(env, P1P1P1, P1P0P0, P0P1P0, P1P1P0);
        assertTrue(expResult.equals(instance, epsilon));
    }

    /**
     * Test of getGeometry method, of class V3D_Triangle_d.
     */
    @Test
    public void testGetGeometry_3args_1() {
        System.out.println("getGeometry");
        double epsilon = 1d / 10000000d;
        V3D_Point_d p;
        V3D_Point_d q;
        V3D_Point_d r;
        V3D_Geometry_d expResult;
        V3D_Geometry_d result;
        // Test 1
        p = new V3D_Point_d(pP0P0P0);
        q = new V3D_Point_d(pP0P0P0);
        r = new V3D_Point_d(pP0P0P0);
        expResult = new V3D_Point_d(pP0P0P0);
        result = V3D_Triangle_d.getGeometry(p, q, r, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 2
        p = new V3D_Point_d(pP1P0P0);
        q = new V3D_Point_d(pP0P0P0);
        r = new V3D_Point_d(pP0P0P0);
        expResult = new V3D_LineSegment_d(pP0P0P0, pP1P0P0);
        result = V3D_Triangle_d.getGeometry(p, q, r, epsilon);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
        // Test 2
        p = new V3D_Point_d(pP1P0P0);
        q = new V3D_Point_d(pP0P1P0);
        r = new V3D_Point_d(pP0P0P0);
        expResult = new V3D_Triangle_d(pP0P0P0, pP1P0P0, pP0P1P0);
        result = V3D_Triangle_d.getGeometry(p, q, r, epsilon);
        assertTrue(((V3D_Triangle_d) expResult).equals((V3D_Triangle_d) result, epsilon));
    }

//    /**
//     * Test of getGeometry method, of class V3D_Triangle_d.
//     */
//    @Test
//    public void testGetGeometry_4args() {
//        System.out.println("getGeometry");
//        double epsilon = 1d / 10000000d;
//        V3D_LineSegment_d l1;
//        V3D_LineSegment_d l2;
//        V3D_LineSegment_d l3;
//        V3D_Geometry_d expResult;
//        V3D_Geometry_d result;
//        // Test 1
//        l1 = new V3D_LineSegment_d(pP0P0P0, pP1P0P0);
//        l2 = new V3D_LineSegment_d(pP1P0P0, pP1P1P0);
//        l3 = new V3D_LineSegment_d(pP1P1P0, pP0P0P0);
//        expResult = new V3D_Triangle_d(pP0P0P0, pP1P0P0, pP1P1P0);
//        result = V3D_Triangle_d.getGeometry(l1, l2, l3, epsilon);
//        assertTrue(((V3D_Triangle_d) expResult).equals((V3D_Triangle_d) result, epsilon));
//        // Test 2
//        l1 = new V3D_LineSegment_d(pP0P0P0, pP1P0P0);
//        l2 = new V3D_LineSegment_d(pP1P1P0, pP0P2P0);
//        l3 = new V3D_LineSegment_d(pN1P2P0, pN1P1P0);
////        expResult = new V3D_Polygon(
////                new V3D_Triangle_d(pP0P0P0, pP1P0P0, pP1P1P0),
////                new V3D_Triangle_d(pP1P1P0, pP0P2P0, pN1P2P0),
////                new V3D_Triangle_d(pP0P0P0, pN1P1P0, pN1P2P0),
////                new V3D_Triangle_d(pP0P0P0, pP1P1P0, pN1P2P0)
////        );
////        result = V3D_Triangle_d.getGeometry(l1, l2, l3);
////        assertEquals(expResult, result);
//
////        assertTrue(expResult.equals(result));
////        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
////        assertTrue(((V3D_Triangle_d) expResult).equals((V3D_Triangle_d) result));
////        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreOrientation((V3D_LineSegment_d) result));
//    }

//    /**
//     * Test of getGeometry method, of class V3D_Triangle_d.
//     */
//    @Test
//    public void testGetGeometry_V3D_LineSegment_d_V3D_LineSegment_d() {
//        System.out.println("getGeometry");
//        double epsilon = 1d / 10000000d;
//        V3D_LineSegment_d l1;
//        V3D_LineSegment_d l2;
//        V3D_Geometry_d expResult;
//        V3D_Geometry_d result;
//        // Test 1
//        l1 = new V3D_LineSegment_d(pP0P0P0, pP1P0P0);
//        l2 = new V3D_LineSegment_d(pP0P0P0, pP0P1P0);
//        result = V3D_Triangle_d.getGeometry(l1, l2, epsilon);
//        expResult = new V3D_Triangle_d(pP0P0P0, pP1P0P0, pP0P1P0);
//        assertTrue(((V3D_Triangle_d) expResult).equals((V3D_Triangle_d) result, epsilon));
//    }

    /**
     * Test of getOpposite method, of class V3D_Triangle_d.
     */
    @Test
    public void testGetOpposite() {
        System.out.println("getOpposite");
        double epsilon = 1d / 10000000d;
        V3D_LineSegment_d l = new V3D_LineSegment_d(pP0P0P0, pP1P0P0);
        V3D_Triangle_d instance = new V3D_Triangle_d(pP0P0P0, pP1P0P0, pP0P1P0);
        V3D_Point_d expResult = pP0P1P0;
        V3D_Point_d result = instance.getOpposite(l, epsilon);
        assertTrue(expResult.equals(result, epsilon));
    }

    /**
     * Test of getDistance method, of class V3D_Triangle_d covered by
     * {@link #testGetDistanceSquared_V3D_Point_d()}.
     */
    @Test
    public void testGetDistance_V3D_Point_d() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Triangle_d.
     */
    @Test
    public void testGetDistanceSquared_V3D_Point_d() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V3D_Point_d p;
        V3D_Triangle_d t;
        double expResult;
        // Test 1
        p = pP0P0P0;
        t = new V3D_Triangle_d(pP0P0P0, pP1P0P0, pP0P1P0);
        expResult = 0d;
        double result = t.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 2
        p = pP1P1P0;
        t = new V3D_Triangle_d(pP0P0P0, pP1P0P0, pP0P1P0);
        expResult = 1d / 2d;
        result = t.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 3
        p = pN1N1P1;
        t = new V3D_Triangle_d(pN2N2P0, pP2N2P0, pN2P2P0);
        expResult = 1d;
        result = t.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 3
        p = new V3D_Point_d(env, -1, -1, 10);
        t = new V3D_Triangle_d(pN2N2P0, pP2N2P0, pN2P2P0);
        expResult = 100d;
        result = t.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 4
        p = new V3D_Point_d(env, -2, -2, 10);
        t = new V3D_Triangle_d(pN2N2P0, pP2N2P0, pN2P2P0);
        expResult = 100d;
        result = t.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 5
        p = new V3D_Point_d(env, -3, -3, 0);
        t = new V3D_Triangle_d(pN2N2P0, pP2N2P0, pN2P2P0);
        expResult = 2d;
        result = t.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 6
        p = new V3D_Point_d(env, -3, -3, 1);
        t = new V3D_Triangle_d(pN2N2P0, pP2N2P0, pN2P2P0);
        expResult = 3d;
        result = t.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 7
        p = new V3D_Point_d(env, -3, -3, 10);
        t = new V3D_Triangle_d(pN2N2P0, pP2N2P0, pN2P2P0);
        expResult = 102d;
        result = t.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 8
        p = new V3D_Point_d(env, 0, 0, 0);
        t = new V3D_Triangle_d(pN1P0P1, pP1P1N1, pP2N2N2);
        expResult = 0d;
        result = t.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 9
        p = new V3D_Point_d(env, 0, 0, 0);
        t = new V3D_Triangle_d(pN1P0P1, pP1P1N1, pP2N2N2);
        p.translate(t.pl.getN().getUnitVector().multiply(10));
        expResult = 100d;
        result = t.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistance method, of class V3D_Triangle_d covered by
     * {@link #testGetDistanceSquared_V3D_Line_d()}.
     */
    @Test
    public void testGetDistance_V3D_Line_d() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Triangle_d.
     */
    @Test
    public void testGetDistanceSquared_V3D_Line_d() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V3D_Line_d l;
        double expResult;
        double result;
        V3D_Triangle_d instance;
        // Test 1
        l = new V3D_Line_d(pP0P0P0, pP1P0P0);
        instance = new V3D_Triangle_d(pP0P0P0, pP1P0P0, pP0P1P0);
        expResult = 0d;
        result = instance.getDistanceSquared(l, epsilon);
        assertEquals(expResult, result);
        // Test 2
        l = new V3D_Line_d(pP0P0P1, pP1P0P1);
        instance = new V3D_Triangle_d(pN2N2P0, pP2N2P0, pN2P2P0);
        expResult = 1d;
        result = instance.getDistanceSquared(l, epsilon);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistance method, of class V3D_Triangle_d covered by
     * {@link #testGetDistanceSquared_V3D_LineSegment_d()}.
     */
    @Test
    public void testGetDistance_V3D_LineSegment_d() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Triangle_d.
     */
    @Test
    public void testGetDistanceSquared_V3D_LineSegment_d() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V3D_LineSegment_d l;
        V3D_Triangle_d instance;
        double expResult;
        double result;
        // Test 1
        l = new V3D_LineSegment_d(pP0P0P0, pP1P0P0);
        instance = new V3D_Triangle_d(pP0P0P0, pP1P0P0, pP0P1P0);
        expResult = 0d;
        result = instance.getDistanceSquared(l, epsilon);
        assertEquals(expResult, result);
        // Test 2
        l = new V3D_LineSegment_d(pP0P0P1, pP1P0P1);
        instance = new V3D_Triangle_d(pN2N2P0, pP2N2P0, pN2P2P0);
        expResult = 1d;
        result = instance.getDistanceSquared(l, epsilon);
        assertEquals(expResult, result);
        // Test 3
        l = new V3D_LineSegment_d(pP0P0P0, pP1P0P0);
        instance = new V3D_Triangle_d(pN1P0P0, pN1P1P0, pN1P0P1);
        expResult = 1d;
        result = instance.getDistanceSquared(l, epsilon);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistance method, of class V3D_Triangle_d covered by
     * {@link #testGetDistanceSquared_V3D_Plane_d()}.
     */
    @Test
    public void testGetDistance_V3D_Plane_d() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Triangle_d.
     */
    @Test
    public void testGetDistanceSquared_V3D_Plane_d() {
        System.out.println("getDistanceSquared");
        V3D_Plane_d pl;
        V3D_Triangle_d instance;
        double expResult;
        double result;
        double epsilon = 1d / 10000000d;
        // Test 1
        pl = new V3D_Plane_d(pP0P0P0, pP1P0P0, pP0P1P0);
        instance = new V3D_Triangle_d(pP0P0P0, pP1P0P0, pP0P1P0);
        expResult = 0d;
        result = instance.getDistanceSquared(pl, epsilon);
        assertEquals(expResult, result);
        // Test 2
        pl = new V3D_Plane_d(pP0P0P1, pP1P0P1, pP0P1P1);
        instance = new V3D_Triangle_d(pP0P0P0, pP1P0P0, pP0P1P0);
        expResult = 1d;
        result = instance.getDistanceSquared(pl, epsilon);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistance method, of class V3D_Triangle_d covered by
     * {@link #testGetDistanceSquared_V3D_Triangle_d()}.
     */
    @Test
    public void testGetDistance_V3D_Triangle_d() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Triangle_d.
     */
    @Test
    public void testGetDistanceSquared_V3D_Triangle_d() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V3D_Triangle_d t;
        V3D_Triangle_d instance;
        double expResult;
        double result;
        // Test 1
        t = new V3D_Triangle_d(pP0P0P0, pP1P0P0, pP0P1P0);
        instance = new V3D_Triangle_d(pP0P0P0, pP1P0P0, pP0P1P0);
        expResult = 0d;
        result = instance.getDistanceSquared(t, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 2
        t = new V3D_Triangle_d(pP0P0P0, pP1P0P0, pP0P1P0);
        instance = new V3D_Triangle_d(pP0P0P1, pP1P0P1, pP0P1P1);
        expResult = 1d;
        result = instance.getDistanceSquared(t, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 3
        t = new V3D_Triangle_d(pN2N2P0, pP2N2P0, pN2P2P0);
        instance = new V3D_Triangle_d(pP0P0P0, pP1P0P0, pP0P1P0);
        expResult = 0d;
        result = instance.getDistanceSquared(t, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 4
        t = new V3D_Triangle_d(pN2N2P0, pP2N2P0, pN2P2P0);
        instance = new V3D_Triangle_d(pP0P0P1, pP1P0P1, pP0P1P1);
        expResult = 1d;
        result = instance.getDistanceSquared(t, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 5
        t = new V3D_Triangle_d(pP0P0P0, pP1P0P0, pP0P1P0);
        instance = new V3D_Triangle_d(pN1P0P0, pN1P0P1, pN1P1P0);
        expResult = 1d;
        result = instance.getDistanceSquared(t, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistance method, of class V3D_Triangle_d covered by
     * {@link #testGetDistanceSquared_V3D_Tetrahedron()}.
     */
    @Test
    public void testGetDistance_V3D_Tetrahedron() {
        System.out.println("getDistance");
    }
}
