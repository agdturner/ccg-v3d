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
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_ConvexHullCoplanarDouble;
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
 * Test of V3D_TriangleDouble class.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_TriangleDoubleTest extends V3D_TestDouble {

    public V3D_TriangleDoubleTest() {
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
     * Test of isIntersectedBy method, of class V3D_TriangleDouble.
     */
    @Test
    public void testIsAligned_V3D_PointDouble_double() {
        System.out.println("isAligned");
        double epsilon = 1d / 10000000d;
        V3D_PointDouble pt = pP0N1P0;
        V3D_TriangleDouble instance = new V3D_TriangleDouble(pP0P2P0, pP0N2P0, pP2P0P0);
        assertTrue(instance.isAligned(pt, epsilon));
        pt = pN1P0P0;
        assertFalse(instance.isAligned(pt, epsilon));
        instance = new V3D_TriangleDouble(pP0P0P0, pP0P1P0, pP1P1P0);
        pt = pP0P2P2;
        assertFalse(instance.isAligned(pt, epsilon));
        
    }

    /**
     * Test of isIntersectedBy method, of class V3D_TriangleDouble.
     */
    @Test
    public void testIsAligned_V3D_LineSegmentDouble_double() {
        System.out.println("isAligned");
        double epsilon = 1d / 10000000d;
        V3D_LineSegmentDouble ls;
        V3D_TriangleDouble instance;
        instance = new V3D_TriangleDouble(pP0P0P0, pP0P1P0, pP1P1P0);
        ls = new V3D_LineSegmentDouble(pP0P2P2, new V3D_PointDouble(0d, 3d, 3d));
        assertFalse(instance.isAligned(ls, epsilon));
        
    }

    /**
     * Test of isIntersectedBy method, of class V3D_TriangleDouble.
     */
    @Test
    public void testIsIntersectedBy() {
        System.out.println("isIntersectedBy");
        double epsilon = 1d / 10000000d;
        V3D_PointDouble pt = pP0P0P0;
        V3D_TriangleDouble instance = new V3D_TriangleDouble(pP0P0P0, pP1P0P0, pP1P1P0);
        assertTrue(instance.isIntersectedBy(pt, epsilon));
        // Test 2
        pt = pN1N1P0;
        assertFalse(instance.isIntersectedBy(pt, epsilon));
        // Test 3
        pt = new V3D_PointDouble(1d / 2d, 0d, 0d);
        assertTrue(instance.isIntersectedBy(pt, epsilon));
    }

    /**
     * Test of getEnvelope method, of class V3D_TriangleDouble.
     */
    @Test
    public void testGetEnvelope() {
        System.out.println("getEnvelope");
        V3D_TriangleDouble instance = new V3D_TriangleDouble(pP0P0P0, pP0P1P0, pP1P0P0);
        V3D_EnvelopeDouble expResult = new V3D_EnvelopeDouble(pP0P0P0, pP0P1P0, pP1P0P0);
        V3D_EnvelopeDouble result = instance.getEnvelope();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getArea method, of class V3D_TriangleDouble.
     */
    @Test
    public void testGetArea() {
        System.out.println("getArea");
        double epsilon = 1d / 10000000d;
        V3D_TriangleDouble instance = new V3D_TriangleDouble(pP0P0P0, pP0P1P0, pP1P0P0);
        double expResult = 1d / 2d;
        double result = instance.getArea();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of translate method, of class V3D_TriangleDouble.
     */
    @Test
    public void testApply() {
        // No test.
    }

    /**
     * Test of getPerimeter method, of class V3D_TriangleDouble.
     */
    @Test
    public void testGetPerimeter() {
        System.out.println("getPerimeter");
        double epsilon = 1d / 10000000d;
        V3D_TriangleDouble instance = new V3D_TriangleDouble(pP0P0P0, pP0P1P0, pP1P0P0);
        double expResult = 2d + Math.sqrt(2);
        double result = instance.getPerimeter();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        expResult = 2d + Math.sqrt(2);
        result = instance.getPerimeter();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getIntersection method, of class V3D_TriangleDouble.
     */
    @Test
    public void testGetIntersection_V3D_LineDouble() {
        System.out.println("getIntersection");
        double epsilon = 1d / 10000000d;
        V3D_LineDouble l = new V3D_LineDouble(pP1N1P0, pP1P2P0);
        V3D_TriangleDouble instance = new V3D_TriangleDouble(pP0P0P0, pP1P1P0, pP2P0P0);
        V3D_GeometryDouble expResult = new V3D_LineSegmentDouble(pP1P0P0, pP1P1P0);
        V3D_GeometryDouble result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection(epsilon, (V3D_LineSegmentDouble) result));
        System.out.println("getIntersection");
        // Test 2
        instance = new V3D_TriangleDouble(P0P0P0, P1P0P0, P1P2P0, P2P0P0);
        l = new V3D_LineDouble(pP1P0P1, pP1P0N1);
        result = instance.getIntersection(l, epsilon);
        expResult = pP1P0P0;
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 3
        l = new V3D_LineDouble(pP1P0P0, pP2P0P0);
        expResult = new V3D_LineSegmentDouble(pP1P0P0, pP2P0P0);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection(epsilon, (V3D_LineSegmentDouble) result));
        // Test 4
        l = new V3D_LineDouble(pP1P0P0, pP2P0P0);
        expResult = new V3D_LineSegmentDouble(pP1P0P0, pP2P0P0);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection(epsilon, (V3D_LineSegmentDouble) result));
    }

    /**
     * Test of getCentroid method, of class V3D_TriangleDouble.
     */
    @Test
    public void testGetCentroid() {
        System.out.println("getCentroid");
        V3D_TriangleDouble instance;
        V3D_PointDouble expResult;
        V3D_PointDouble result;
        // Test
        instance = new V3D_TriangleDouble(pP0P0P0, pP1P0P0, pP1P1P0);
        expResult = new V3D_PointDouble(2d / 3d, 1d / 3d, 0d);
        result = instance.getCentroid();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of toString method, of class V3D_TriangleDouble.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        V3D_TriangleDouble instance = new V3D_TriangleDouble(P0P0P0, P1P0P0, P0P1P0, P0P0P1);
        String expResult = """
                           V3D_TriangleDouble(
                            pl=( V3D_PlaneDouble(
                             offset=V3D_VectorDouble(dx=0.0, dy=0.0, dz=0.0),
                             p=  V3D_VectorDouble(dx=1.0, dy=0.0, dz=0.0),
                             n=  V3D_VectorDouble(dx=1.0, dy=1.0, dz=1.0))),
                            offset=(V3D_VectorDouble(dx=0.0, dy=0.0, dz=0.0)),
                            p=(V3D_VectorDouble(dx=1.0, dy=0.0, dz=0.0)),
                            q=(V3D_VectorDouble(dx=0.0, dy=1.0, dz=0.0)),
                            r=(V3D_VectorDouble(dx=0.0, dy=0.0, dz=1.0)))""";
        String result = instance.toString();
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_TriangleDouble.
     */
    @Test
    public void testIsIntersectedBy_V3D_PointDouble() {
        System.out.println("isIntersectedBy");
        double epsilon = 1d / 10000000d;
        V3D_PointDouble pt;
        V3D_TriangleDouble instance;
        instance = new V3D_TriangleDouble(P0P0P0, P1P0P0, P0P1P0, P0P0P1);
        assertTrue(instance.isIntersectedBy(pP1P0P0, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P1P0, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P0P1, epsilon));
        pt = new V3D_PointDouble(P1P0P0, P0P0P0);
        assertTrue(instance.isIntersectedBy(pt, epsilon));
        // Test 5
        pt = new V3D_PointDouble(P0P1P0, P0P0P0);
        assertTrue(instance.isIntersectedBy(pt, epsilon));
        // Test 6
        pt = new V3D_PointDouble(P0P0P1, P0P0P0);
        assertTrue(instance.isIntersectedBy(pt, epsilon));
        // Test 7
        pt = new V3D_PointDouble(P1P0P0, P1P0P0);
        assertFalse(instance.isIntersectedBy(pt, epsilon));
        // Test 8
        pt = new V3D_PointDouble(P0P1P0, P0P1P0);
        assertFalse(instance.isIntersectedBy(pt, epsilon));
        // Test 9
        pt = new V3D_PointDouble(P0P0P1, P0P0P1);
        assertFalse(instance.isIntersectedBy(pt, epsilon));
        // Test 10
        instance = new V3D_TriangleDouble(P0P0P0, P1P0P0, P1P2P0, P2P0P0);
        pt = new V3D_PointDouble(P0P0P0, new V3D_VectorDouble(1d, 3d, 0d));
        assertFalse(instance.isIntersectedBy(pt, epsilon));
        assertTrue(instance.isIntersectedBy(pP1P2P0, epsilon));
        assertTrue(instance.isIntersectedBy(pP1P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P0P0, epsilon));
        pt = new V3D_PointDouble(P0P0P0, new V3D_VectorDouble(3d / 2d, 1d, 0d));
        assertTrue(instance.isIntersectedBy(pt, epsilon));
    }

    /**
     * Test of getIntersection method, of class V3D_TriangleDouble.
     */
    @Test
    public void testGetIntersection_V3D_LineSegmentDouble() {
        System.out.println("getIntersection");
        double epsilon = 1d / 10000000d;
        V3D_LineSegmentDouble l;
        V3D_TriangleDouble instance;
        V3D_GeometryDouble expResult;
        V3D_GeometryDouble result;
        // Test 1
        instance = new V3D_TriangleDouble(pP1P0P0, pP1P2P0, pP2P0P0);
        l = new V3D_LineSegmentDouble(pP1P0P1, pP1P0N1);
        expResult = pP1P0P0;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 2
        l = new V3D_LineSegmentDouble(pP1P0P0, pP2P0P0);
        expResult = new V3D_LineSegmentDouble(pP1P0P0, pP2P0P0);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegmentDouble) result));
        // Test 3
        l = new V3D_LineSegmentDouble(pP1P0P0, pP2P0P0);
        expResult = new V3D_LineSegmentDouble(pP1P0P0, pP2P0P0);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegmentDouble) result));
        // Test 4
        l = new V3D_LineSegmentDouble(pP2N2P0, pP2P1P0);
        instance = new V3D_TriangleDouble(P0P0P0, P2P2P0,
                new V3D_VectorDouble(4d, 0d, 0d));
        expResult = new V3D_LineSegmentDouble(pP2P0P0, pP2P1P0);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegmentDouble) result));
        // Test 5
        l = new V3D_LineSegmentDouble(pP0P0P0, pP0P1P0);
        instance = new V3D_TriangleDouble(pN2N2P0, pP0P2P0, pP2N2P0);
        expResult = new V3D_LineSegmentDouble(pP0P0P0, pP0P1P0);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegmentDouble) result));
        // Test 6
        l = new V3D_LineSegmentDouble(new V3D_PointDouble(4d, -2d, 0d), pP2P0P0);
        instance = new V3D_TriangleDouble(pP0P0P0, new V3D_PointDouble(4d, 0d, 0d), new V3D_PointDouble(2d, -4d, 0d));
        expResult = new V3D_LineSegmentDouble(pP2P0P0, new V3D_PointDouble(10d/3d, -4d/3d, 0d));
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegmentDouble) result));
    }

    /**
     * Test of equals method, of class V3D_TriangleDouble.
     */
    @Test
    public void testEquals_V3D_TriangleDouble() {
        System.out.println("equals");
        double epsilon = 1d / 10000000d;
        V3D_TriangleDouble t = new V3D_TriangleDouble(pP1P0P0, pP1P2P0, pP2P0P0);
        V3D_TriangleDouble instance = new V3D_TriangleDouble(P1P0P0, P0P0P0, P0P2P0, P1P0P0);
        boolean result = instance.equals(t, epsilon);
        assertTrue(result);
        // Test 2
        instance = new V3D_TriangleDouble(P1P0P0, P0P2P0, P0P0P0, P1P0P0);
        result = instance.equals(t, epsilon);
        assertTrue(result);
        // Test 3
        instance = new V3D_TriangleDouble(P1P0P0, P0P2P0, P1P0P0, P0P0P0);
        result = instance.equals(t, epsilon);
        assertTrue(result);
        // Test 4
        instance = new V3D_TriangleDouble(P1P0P0, P1P0P0, P0P2P0, P0P0P0);
        result = instance.equals(t, epsilon);
        assertTrue(result);
        // Test 4
        instance = new V3D_TriangleDouble(P1P0P0, P1P0P0, P0P2P0, P0P0P0);
        t = new V3D_TriangleDouble(P0P0P0, instance);
        result = instance.equals(t, epsilon);
        assertTrue(result);
        
        
    }

    /**
     * Test of rotate method, of class V3D_TriangleDouble.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        double epsilon = 1d / 10000000d;
        double theta;
        V3D_TriangleDouble instance;
        V3D_TriangleDouble expResult;
        double Pi = Math.PI;
        // Test 1
        instance = new V3D_TriangleDouble(pP1P0P0, pP0P1P0, pP1P1P0);
        V3D_RayDouble xaxis = new V3D_RayDouble(pP0P0P0, pP1P0P0);
        theta = Pi;
        instance = instance.rotate(xaxis, xaxis.l.v, theta, epsilon);
        expResult = new V3D_TriangleDouble(pP1P0P0, pP0N1P0, pP1N1P0);
        assertTrue(expResult.equals(instance, epsilon));
        assertTrue(expResult.pl.equals(instance.pl, epsilon));
        // Test 2
        instance = new V3D_TriangleDouble(pP1P0P0, pP0P1P0, pP1P1P0);
        theta = Pi / 2d;
        instance = instance.rotate(xaxis, xaxis.l.v, theta, epsilon);
        expResult = new V3D_TriangleDouble(pP1P0P0, pP0P0P1, pP1P0P1);
        assertTrue(expResult.equals(instance, epsilon));
        assertTrue(expResult.pl.equals(instance.pl, epsilon));
        // Test 3
        instance = new V3D_TriangleDouble(pP2P0P0, pP0P2P0, pP2P2P0);
        theta = Pi;
        instance = instance.rotate(xaxis, xaxis.l.v,  theta, epsilon);
        expResult = new V3D_TriangleDouble(pP2P0P0, pP0N2P0, pP2N2P0);
        assertTrue(expResult.equals(instance, epsilon));
        assertTrue(expResult.pl.equals(instance.pl, epsilon));
        // Test 4
        instance = new V3D_TriangleDouble(pP2P0P0, pP0P2P0, pP2P2P0);
        theta = Pi / 2d;
        instance = instance.rotate(xaxis, xaxis.l.v, theta, epsilon);
        expResult = new V3D_TriangleDouble(pP2P0P0, pP0P0P2, pP2P0P2);
        assertTrue(expResult.equals(instance, epsilon));
        assertTrue(expResult.pl.equals(instance.pl, epsilon));
        // Test 5
        instance = new V3D_TriangleDouble(pP1P0P0, pP0P1P0, pP1P1P0);
        instance.translate(P1P0P0);
        theta = Pi;
        instance = instance.rotate(xaxis, xaxis.l.v, theta, epsilon);
        expResult = new V3D_TriangleDouble(pP2P0P0, pP1N1P0, pP2N1P0);
        assertTrue(expResult.equals(instance, epsilon));
        assertTrue(expResult.pl.equals(instance.pl, epsilon));
        
        // Test 6
        instance = new V3D_TriangleDouble(pP1P0P0, pP0P1P0, pP1P1P0);
        V3D_RayDouble yaxis = new V3D_RayDouble(pP0P0P0, pP0P1P0);
        theta = Pi;
        instance = instance.rotate(yaxis, yaxis.l.v, theta, epsilon);
        expResult = new V3D_TriangleDouble(pN1P0P0, pP0P1P0, pN1P1P0);
        assertTrue(expResult.equals(instance, epsilon));
        assertTrue(expResult.pl.equals(instance.pl, epsilon));
        // Test 7
        instance = new V3D_TriangleDouble(pP1P0P0, pP0P1P0, pP1P1P0);
        theta = Pi / 2d;
        instance = instance.rotate(yaxis, yaxis.l.v, theta, epsilon);
        expResult = new V3D_TriangleDouble(pP0P0N1, pP0P1P0, pP0P1N1);
        assertTrue(expResult.equals(instance, epsilon));
        assertTrue(expResult.pl.equals(instance.pl, epsilon));
        // Test 8
        instance = new V3D_TriangleDouble(pP2P0P0, pP0P2P0, pP2P2P0);
        theta = Pi;
        instance = instance.rotate(yaxis, yaxis.l.v, theta, epsilon);
        expResult = new V3D_TriangleDouble(pN2P0P0, pP0P2P0, pN2P2P0);
        assertTrue(expResult.equals(instance, epsilon));
        assertTrue(expResult.pl.equals(instance.pl, epsilon));
        // Test 9
        instance = new V3D_TriangleDouble(pP2P0P0, pP0P2P0, pP2P2P0);
        theta = Pi / 2d;
        instance = instance.rotate(yaxis, yaxis.l.v, theta, epsilon);
        expResult = new V3D_TriangleDouble(pP0P0N2, pP0P2P0, pP0P2N2);
        assertTrue(expResult.equals(instance, epsilon));
        assertTrue(expResult.pl.equals(instance.pl, epsilon));
        // Test 10
        instance = new V3D_TriangleDouble(pP1P0P0, pP0P1P0, pP1P1P0);
        instance.translate(P1P0P0);
        theta = Pi;
        instance = instance.rotate(yaxis, yaxis.l.v, theta, epsilon);
        expResult = new V3D_TriangleDouble(pN2P0P0, pN1P1P0, pN2P1P0);
        assertTrue(expResult.equals(instance, epsilon));
        assertTrue(expResult.pl.equals(instance.pl, epsilon));
        // Test 11
        instance = new V3D_TriangleDouble(pP1P0P0, pP0P1P0, pP1P1P0);
        V3D_RayDouble zaxis = new V3D_RayDouble(pP0P0P0, pP0P0P1);
        theta = Pi;
        instance = instance.rotate(zaxis, zaxis.l.v, theta, epsilon);
        expResult = new V3D_TriangleDouble(pN1P0P0, pP0N1P0, pN1N1P0);
        assertTrue(expResult.equals(instance, epsilon));
        assertTrue(expResult.pl.equals(instance.pl, epsilon));
        // Test 12
        instance = new V3D_TriangleDouble(pP1P0P0, pP0P1P0, pP1P1P0);
        theta = Pi / 2d;
        instance = instance.rotate(zaxis, zaxis.l.v, theta, epsilon);
        expResult = new V3D_TriangleDouble(pP0P1P0, pN1P0P0, pN1P1P0);
        assertTrue(expResult.equals(instance, epsilon));
        assertTrue(expResult.pl.equals(instance.pl, epsilon));
        // Test 13
        instance = new V3D_TriangleDouble(pP2P0P0, pP0P2P0, pP2P2P0);
        theta = Pi;
        instance = instance.rotate(zaxis, zaxis.l.v, theta, epsilon);
        expResult = new V3D_TriangleDouble(pN2P0P0, pP0N2P0, pN2N2P0);
        assertTrue(expResult.equals(instance, epsilon));
        assertTrue(expResult.pl.equals(instance.pl, epsilon));
        instance = new V3D_TriangleDouble(pP2P0P0, pP0P2P0, pP2P2P0);
        theta = Pi / 2d;
        instance = instance.rotate(zaxis, zaxis.l.v, theta, epsilon);
        expResult = new V3D_TriangleDouble(pP0P2P0, pN2P0P0, pN2P2P0);
        assertTrue(expResult.equals(instance, epsilon));
        assertTrue(expResult.pl.equals(instance.pl, epsilon));
        // Test 15
        instance = new V3D_TriangleDouble(pP1P0P0, pP0P1P0, pP1P1P0);
        instance.translate(P1P0P0);
        theta = Pi;
        instance = instance.rotate(zaxis, zaxis.l.v, theta, epsilon);
        expResult = new V3D_TriangleDouble(pN2P0P0, pN1N1P0, pN2N1P0);
        assertTrue(expResult.equals(instance, epsilon));
        assertTrue(expResult.pl.equals(instance.pl, epsilon));
    }

    /**
     * Test of getIntersection method, of class V3D_TriangleDouble.
     */
    @Test
    public void testGetIntersection_V3D_PlaneDouble() {
        System.out.println("getIntersection");
        double epsilon = 1d / 10000000d;
        V3D_TriangleDouble t;
        V3D_PlaneDouble p;
        V3D_GeometryDouble expResult;
        V3D_GeometryDouble result;
        t = new V3D_TriangleDouble(pP0P0P0, pP1P0P0, pP0P1P0);
        // Test 1
        p = V3D_PlaneDouble.X0;
        expResult = new V3D_LineSegmentDouble(pP0P0P0, pP0P1P0);
        result = t.getIntersection(p, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegmentDouble) result));
        // Test 2
        p = V3D_PlaneDouble.Y0;
        expResult = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
        result = t.getIntersection(p, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegmentDouble) result));
        // Test 3
        p = V3D_PlaneDouble.Z0;
        expResult = t;
        result = t.getIntersection(p, epsilon);
        assertTrue(((V3D_TriangleDouble) expResult).equals((V3D_TriangleDouble) result));
        // Test 4
        t = new V3D_TriangleDouble(pN1P0P0, pP1P0P0, pP0P1P0);
        p = V3D_PlaneDouble.X0;
        expResult = new V3D_LineSegmentDouble(pP0P0P0, pP0P1P0);
        result = t.getIntersection(p, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegmentDouble) result));
        // Test 5
        t = new V3D_TriangleDouble(pN2N2N2, pN2N2P2, pP2P0P0);
        p = V3D_PlaneDouble.X0;
        expResult = new V3D_LineSegmentDouble(pP0N1P1, pP0N1N1);
        result = t.getIntersection(p, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegmentDouble) result));
        // Test 6
        t = new V3D_TriangleDouble(P0P0P0, P1P0P0, P0P1P0, P1P1P0);
        p = V3D_PlaneDouble.X0;
        expResult = pP0P1P0;
        result = t.getIntersection(p, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 7
        p = new V3D_PlaneDouble(V3D_PlaneDouble.X0);
        p.translate(P1P0P0);
        expResult = new V3D_LineSegmentDouble(pP1P0P0, pP1P1P0);
        result = t.getIntersection(p, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegmentDouble) result));
        // Test 8
        p = new V3D_PlaneDouble(V3D_PlaneDouble.Z0);
        expResult = new V3D_TriangleDouble(t);
        result = t.getIntersection(p, epsilon);
        assertTrue(((V3D_TriangleDouble) expResult).equals((V3D_TriangleDouble) result, epsilon));
    }

    /**
     * Test of getIntersection method, of class V3D_TriangleDouble.
     */
    @Test
    public void testGetIntersection_V3D_RayDouble() {
        System.out.println("getIntersection");
        double epsilon = 1d / 10000000d;
        V3D_TriangleDouble t;
        V3D_RayDouble r;
        V3D_GeometryDouble expResult;
        V3D_GeometryDouble result;
        // Test 1
        t = new V3D_TriangleDouble(pP0P1P0, pP1P0P0, pP1P1P0);
        r = new V3D_RayDouble(pP0P0P0, pP1P0P0);
        result = t.getIntersection(r, epsilon);
        expResult = pP1P0P0;
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 2
        t = new V3D_TriangleDouble(pP0N2P0, pP0P2P0, pP2P0P0);
        r = new V3D_RayDouble(pP1P0P0, pP2P0P0);
        result = t.getIntersection(r, epsilon);
        expResult = new V3D_LineSegmentDouble(pP1P0P0, pP2P0P0);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegmentDouble) result));
        // Test 3
        r = new V3D_RayDouble(pN1P0P0, pP2P0P0);
        result = t.getIntersection(r, epsilon);
        expResult = new V3D_LineSegmentDouble(pP0P0P0, pP2P0P0);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegmentDouble) result));
        // Test 4
        r = new V3D_RayDouble(pN2P0N2, pP0P0P0);
        result = t.getIntersection(r, epsilon);
        expResult = pP0P0P0;
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 5
        r = new V3D_RayDouble(pN2P0N2, pP0N1P0);
        expResult = pP0N1P0;
        result = t.getIntersection(r, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 6
        r = new V3D_RayDouble(pN2P0N2, pN1P0P0);
        assertNull(t.getIntersection(r, epsilon));
        // Test 7
        t = new V3D_TriangleDouble(pP0N2P0, pP0P2P0, pP2P0P0);
        r = new V3D_RayDouble(pN2N2N2, pN1N1N1);
        expResult = pP0P0P0;
        result = t.getIntersection(r, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 8
        t = new V3D_TriangleDouble(pP0N2P0, pP0P2P0, pP2P2P1);
        r = new V3D_RayDouble(pN2N2N2, pN1N1N1);
        expResult = pP0P0P0;
        result = t.getIntersection(r, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 9
        t = new V3D_TriangleDouble(pN1N2P0, pN1P2P0, pP2P2P0);
        r = new V3D_RayDouble(pN2N2N2, pN1N1N1);
        expResult = pP0P0P0;
        result = t.getIntersection(r, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 10
        t = new V3D_TriangleDouble(pN1N2P0, pN1P2P0, pP2P2N1);
        r = new V3D_RayDouble(pN2N2N2, pN1N1N1);
        double nq = -1d / 4d;
        expResult = new V3D_PointDouble(nq, nq, nq);
        result = t.getIntersection(r, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));

        // Box test.
        V3D_VectorDouble offset = V3D_VectorDouble.ZERO;
        V3D_PointDouble[] points = new V3D_PointDouble[8];
        V3D_PointDouble centroid = V3D_PointDouble.ORIGIN;
        long multiplier = 100;
        V3D_PointDouble lbf = new V3D_PointDouble(offset, new V3D_VectorDouble(-1 * multiplier, -1 * multiplier, -1 * multiplier));
        V3D_PointDouble lba = new V3D_PointDouble(offset, new V3D_VectorDouble(-1 * multiplier, -1 * multiplier, 1 * multiplier));
        V3D_PointDouble ltf = new V3D_PointDouble(offset, new V3D_VectorDouble(-1 * multiplier, 1 * multiplier, -1 * multiplier));
        V3D_PointDouble lta = new V3D_PointDouble(offset, new V3D_VectorDouble(-1 * multiplier, 1 * multiplier, 1 * multiplier));
        V3D_PointDouble rbf = new V3D_PointDouble(offset, new V3D_VectorDouble(1 * multiplier, -1 * multiplier, -1 * multiplier));
        V3D_PointDouble rba = new V3D_PointDouble(offset, new V3D_VectorDouble(1 * multiplier, -1 * multiplier, 1 * multiplier));
        V3D_PointDouble rtf = new V3D_PointDouble(offset, new V3D_VectorDouble(1 * multiplier, 1 * multiplier, -1 * multiplier));
        V3D_PointDouble rta = new V3D_PointDouble(offset, new V3D_VectorDouble(1 * multiplier, 1 * multiplier, 1 * multiplier));
        // BLUE
        V3D_TriangleDouble b1 = new V3D_TriangleDouble(lbf, ltf, rtf);
        V3D_TriangleDouble b2 = new V3D_TriangleDouble(lbf, rbf, rtf);
        // RED
        V3D_TriangleDouble r1 = new V3D_TriangleDouble(lbf, ltf, lta);
        V3D_TriangleDouble r2 = new V3D_TriangleDouble(lbf, lba, lta);
        // YELLOW
        V3D_TriangleDouble y1 = new V3D_TriangleDouble(lba, lta, rta);
        V3D_TriangleDouble y2 = new V3D_TriangleDouble(lba, rba, rta);
        // GREEN
        V3D_TriangleDouble g1 = new V3D_TriangleDouble(rbf, rtf, rta);
        V3D_TriangleDouble g2 = new V3D_TriangleDouble(rbf, rta, rba);
        // ORANGE
        V3D_TriangleDouble o1 = new V3D_TriangleDouble(ltf, lta, rta);
        V3D_TriangleDouble o2 = new V3D_TriangleDouble(rtf, ltf, rta);
        // PINK
        V3D_TriangleDouble p1 = new V3D_TriangleDouble(lbf, rbf, rba);
        V3D_TriangleDouble p2 = new V3D_TriangleDouble(lbf, lba, rba);
        points[0] = lbf;
        points[1] = lba;
        points[2] = ltf;
        points[3] = lta;
        points[4] = rbf;
        points[5] = rba;
        points[6] = rtf;
        points[7] = rta;
        V3D_EnvelopeDouble envelope = new V3D_EnvelopeDouble(points);
        int width = 100;
        double radius = envelope.getPoints()[0].getDistance(centroid);
        V3D_VectorDouble direction = new V3D_VectorDouble(0, 0, 1).getUnitVector();
        V3D_PointDouble pt = new V3D_PointDouble(centroid);
        pt.translate(direction.multiply(radius * 2d));
        V3D_PlaneDouble pl = new V3D_PlaneDouble(pt, new V3D_VectorDouble(pt, envelope.getCentroid()));
        V3D_VectorDouble pv = pl.getPV();
        double zoomFactor = 1.0d;
        V3D_RectangleDouble screen = envelope.getViewport3(pt, pv, zoomFactor, epsilon);
        V3D_TriangleDouble pqr = screen.getPQR();
        double screenWidth = pqr.getPQ().getLength();
        double screenHeight = screenWidth;
        double pixelSize = screenWidth / (double) width;
        V3D_VectorDouble vd = new V3D_VectorDouble(pqr.getQR().l.v).divide(
                (double) width);
        V3D_VectorDouble v2d = new V3D_VectorDouble(pqr.getPQ().l.v).divide(
                (double) width);
        int row = width / 3;
        int col = width / 2;
        r = getRay(row, col, screen, pt, vd, v2d, epsilon);

//        expResult = new V3D_PointDouble(nq, nq, nq);
//        result = b1.getIntersection(r);
//        result = b2.getIntersection(r);
//        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
    }

    /**
     * For getting a ray from the camera focal point through the centre of the
     * screen pixel with ID id.
     *
     * @param id The ID of the screen pixel.
     * @return The ray from the camera focal point through the centre of the
     * screen pixel.
     */
    protected V3D_RayDouble getRay(int row, int col, V3D_RectangleDouble screen,
            V3D_PointDouble pt, V3D_VectorDouble vd, V3D_VectorDouble v2d,
            double epsilon) {
        V3D_VectorDouble rv = v2d.multiply((double) row);
        V3D_VectorDouble cv = vd.multiply((double) col);
        V3D_PointDouble rcpt = new V3D_PointDouble(screen.getP());
        rcpt.translate(rv.add(cv));
        return new V3D_RayDouble(pt, rcpt);
    }

    /**
     * Test of getIntersection method, of class V3D_TriangleDouble.
     *
     * Look for some examples here:
     * https://math.stackexchange.com/questions/1220102/how-do-i-find-the-intersection-of-two-3d-triangles
     */
    @Test
    public void testGetIntersection_V3D_TriangleDouble() {
        System.out.println("getIntersection");
        double epsilon = 1d / 10000000d;
        V3D_TriangleDouble t = new V3D_TriangleDouble(pP0P0P0, pP0P1P0, pP1P0P0);
        V3D_TriangleDouble instance = new V3D_TriangleDouble(pP0P0P0, pP0P1P0, pP1P0P0);
        V3D_GeometryDouble expResult = new V3D_TriangleDouble(pP0P0P0, pP0P1P0, pP1P0P0);
        V3D_GeometryDouble result = instance.getIntersection(t, epsilon);
        assertTrue(((V3D_TriangleDouble) expResult).equals((V3D_TriangleDouble) result, epsilon));
        // Test 2
        t = new V3D_TriangleDouble(pN1N1P0, pP0P2P0, new V3D_PointDouble(3d, -1d, 0d));
        instance = new V3D_TriangleDouble(pP0P0P0, pP0P1P0, pP1P0P0);
        expResult = new V3D_TriangleDouble(pP0P0P0, pP0P1P0, pP1P0P0);
        result = instance.getIntersection(t, epsilon);
        assertTrue(((V3D_TriangleDouble) expResult).equals((V3D_TriangleDouble) result, epsilon));
        // Test 3
        t = new V3D_TriangleDouble(pP0P0P0, pP0P1P0, pP1P0P0);
        instance = new V3D_TriangleDouble(pN1N1P0, pP0P2P0, new V3D_PointDouble(3d, -1d, 0d));
        expResult = new V3D_TriangleDouble(pP0P0P0, pP0P1P0, pP1P0P0);
        result = instance.getIntersection(t, epsilon);
        assertTrue(((V3D_TriangleDouble) expResult).equals((V3D_TriangleDouble) result, epsilon));
        // Test 4
        t = new V3D_TriangleDouble(pP0P0P0, pP2P0P0, pP2P2P0);
        instance = new V3D_TriangleDouble(pP1P0P0, new V3D_PointDouble(3d, 0d, 0d), new V3D_PointDouble(3d, 2d, 0d));
        expResult = new V3D_TriangleDouble(pP1P0P0, pP2P0P0, pP2P1P0);
        result = instance.getIntersection(t, epsilon);
        assertTrue(((V3D_TriangleDouble) expResult).equals((V3D_TriangleDouble) result, epsilon));
        // Test 5: From  https://stackoverflow.com/a/29563443/1998054
        t = new V3D_TriangleDouble(new V3D_PointDouble(-21, -72, 63),
                new V3D_PointDouble(-78, 99, 40),
                new V3D_PointDouble(-19, -78, -83));
        instance = new V3D_TriangleDouble(new V3D_PointDouble(96, 77, -51),
                new V3D_PointDouble(-95, -1, -16),
                new V3D_PointDouble(9, 5, -21));
        // This expected result is not given in the answer on stack overflow.
        expResult = new V3D_LineSegmentDouble(
                new V3D_PointDouble(-34.630630630630634, -31.108108108108105, -5.95495495495496),
                new V3D_PointDouble(-48.45827629341561, 10.37482888024681, -21.586983320506448));
        result = instance.getIntersection(t, epsilon);
        //System.out.println(result);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection(epsilon, (V3D_LineSegmentDouble) result));
        // Test 6: From https://web.mst.edu/~chaman/home/pubs/2015WimoTriangleTrianglePublished.pdf
        t = new V3D_TriangleDouble(new V3D_PointDouble(0, 0, 0),
                new V3D_PointDouble(6, 0, 0),
                new V3D_PointDouble(0, 6, 0));
        instance = new V3D_TriangleDouble(new V3D_PointDouble(0, 3, 3),
                new V3D_PointDouble(0, 3, -3),
                new V3D_PointDouble(-3, 3, 3));
        // This expected result is not given in the answer on stack overflow.
        expResult = new V3D_PointDouble(0, 3, 0);
        result = instance.getIntersection(t, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals(epsilon, (V3D_PointDouble) result));
        // Test 7: From https://web.mst.edu/~chaman/home/pubs/2015WimoTriangleTrianglePublished.pdf
        t = new V3D_TriangleDouble(new V3D_PointDouble(0, 6, 0),
                new V3D_PointDouble(6, 0, 0),
                new V3D_PointDouble(0, 0, 0));
        instance = new V3D_TriangleDouble(new V3D_PointDouble(1, 3, 0),
                new V3D_PointDouble(3, 1, 0),
                new V3D_PointDouble(2, 2, 4));
        result = instance.getIntersection(t, epsilon);
        expResult = new V3D_LineSegmentDouble(new V3D_PointDouble(1, 3, 0),
                new V3D_PointDouble(3, 1, 0));
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegmentDouble) result));
        // Test 8: From https://math.stackexchange.com/questions/1220102/how-do-i-find-the-intersection-of-two-3d-triangles
        t = new V3D_TriangleDouble(new V3D_PointDouble(6, 8, 3),
                new V3D_PointDouble(6, 8, -2),
                new V3D_PointDouble(6, -4, -2));
        instance = new V3D_TriangleDouble(new V3D_PointDouble(0, 5, 0),
                new V3D_PointDouble(0, 0, 0),
                new V3D_PointDouble(8, 0, 0));
        result = instance.getIntersection(t, epsilon);
        expResult = new V3D_LineSegmentDouble(new V3D_PointDouble(6, 1.25d, 0),
                new V3D_PointDouble(6, 0.8d, 0));
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegmentDouble) result));
        // Test 9: 4 sides
        t = new V3D_TriangleDouble(new V3D_PointDouble(2, -3, 0), 
                new V3D_PointDouble(6, 1, 0), new V3D_PointDouble(2, 5, 0));
        instance = new V3D_TriangleDouble(pP1P0P0, new V3D_PointDouble(3, 0, 0), 
                new V3D_PointDouble(3, 2, 0));
        System.out.println(pP1P0P0.toString());
        expResult = new V3D_ConvexHullCoplanarDouble(epsilon,
                new V3D_TriangleDouble(pP2P0P0, new V3D_PointDouble(3, 0, 0),
                        pP2P1P0),
                new V3D_TriangleDouble(
                        new V3D_PointDouble(3, 0, 0),
                        new V3D_PointDouble(3, 2, 0),
                        pP2P1P0));
        result = instance.getIntersection(t, epsilon);
        //System.out.println(result);
        assertTrue(((V3D_ConvexHullCoplanarDouble) expResult).equals((V3D_ConvexHullCoplanarDouble) result, epsilon));
        // Test 10: 5 sides
        epsilon = 1d / 10000000000d;
        t = new V3D_TriangleDouble(pP0P0P0, new V3D_PointDouble(4, 0, 0), 
                new V3D_PointDouble(2, -4, 0));
        instance = new V3D_TriangleDouble(pP0N2P0, pP2P0P0, 
                new V3D_PointDouble(4, -2, 0));
        V3D_PointDouble m = new V3D_PointDouble(2d/3d, -4d/3d, 0);
        V3D_PointDouble n = new V3D_PointDouble(10d/3d, -4d/3d, 0);
        expResult = new V3D_ConvexHullCoplanarDouble(epsilon,
                new V3D_TriangleDouble(pP2P0P0, m, n),
                //new V3D_TriangleDouble(m, n, pP1N2P0),
                new V3D_TriangleDouble(m, n, new V3D_PointDouble(3, -2, 0)),
                new V3D_TriangleDouble(pP1N2P0,
                        new V3D_PointDouble(3, -2, 0),
                        n));
        result = instance.getIntersection(t, epsilon);
        //System.out.println(((V3D_ConvexHullCoplanarDouble) expResult).toStringSimple());
        //System.out.println(((V3D_ConvexHullCoplanarDouble) result).toStringSimple());
        assertTrue(((V3D_ConvexHullCoplanarDouble) expResult).equals((V3D_ConvexHullCoplanarDouble) result, epsilon));
        // Test 11: 6 sides
        t = new V3D_TriangleDouble(pP0P0P0, new V3D_PointDouble(6, 0, 0), 
                new V3D_PointDouble(3, -3, 0));
        instance = new V3D_TriangleDouble(pP0N2P0, new V3D_PointDouble(3, 1, 0),
                new V3D_PointDouble(6, -2, 0));
        expResult = new V3D_ConvexHullCoplanarDouble(epsilon,
                new V3D_TriangleDouble(pP2P0P0, new V3D_PointDouble(4, 0, 0), pP1N1P0),
                new V3D_TriangleDouble(pP1N1P0, pP2N2P0, new V3D_PointDouble(4, -2, 0)),
                new V3D_TriangleDouble(
                        new V3D_PointDouble(4, 0, 0),
                        new V3D_PointDouble(4, -2, 0),
                        new V3D_PointDouble(5, -1, 0)),
                new V3D_TriangleDouble(new V3D_PointDouble(4, 0, 0), pP1N1P0,
                        new V3D_PointDouble(4, -2, 0)));
        result = instance.getIntersection(t, epsilon);
        //System.out.println(result);
        assertTrue(((V3D_ConvexHullCoplanarDouble) expResult).equals((V3D_ConvexHullCoplanarDouble) result, epsilon));
//        // Test 12: 6 sides
//        t = new V3D_TriangleDouble(new V3D_PointDouble(6, 0, 0), pP0P0P0, new V3D_PointDouble(3, -3, 0));
//        instance = new V3D_TriangleDouble(pP0N2P0, new V3D_PointDouble(3, 1, 0), new V3D_PointDouble(6, -2, 0));
//        expResult = new V3D_ConvexHullCoplanarDouble(epsilon,
//                new V3D_TriangleDouble(pP2P0P0, new V3D_PointDouble(4, 0, 0), pP1N1P0),
//                new V3D_TriangleDouble(pP1N1P0, pP2N2P0, new V3D_PointDouble(4, 2, 0)),
//                new V3D_TriangleDouble(
//                        new V3D_PointDouble(4, 0, 0),
//                        new V3D_PointDouble(4, -2, 0),
//                        new V3D_PointDouble(5, -1, 0)),
//                new V3D_TriangleDouble(new V3D_PointDouble(4, 0, 0), pP1N1P0,
//                        new V3D_PointDouble(4, 2, 0)));
//        result = instance.getIntersection(t, epsilon);
//        //System.out.println(result);
//        assertTrue(((V3D_ConvexHullCoplanarDouble) expResult).equals((V3D_ConvexHullCoplanarDouble) result, epsilon));

    }

    /**
     * Test of translate method, of class V3D_TriangleDouble.
     */
    @Test
    public void testTranslate() {
        System.out.println("translate");
        double epsilon = 1d / 10000000d;
        V3D_TriangleDouble instance = new V3D_TriangleDouble(P0P0P0, P1P0P0, P0P1P0, P1P1P0);
        instance.translate(V3D_VectorDouble.I);
        V3D_TriangleDouble expResult = new V3D_TriangleDouble(P0P0P0, P2P0P0, P1P1P0, P2P1P0);
        assertTrue(expResult.equals(instance, epsilon));
        // Test 2
        instance = new V3D_TriangleDouble(P0P0P0, P1P0P0, P0P1P0, P1P1P0);
        instance.translate(V3D_VectorDouble.IJK);
        expResult = new V3D_TriangleDouble(P1P1P1, P1P0P0, P0P1P0, P1P1P0);
        assertTrue(expResult.equals(instance, epsilon));
    }

    /**
     * Test of getGeometry method, of class V3D_TriangleDouble.
     */
    @Test
    public void testGetGeometry_3args_1() {
        System.out.println("getGeometry");
        double epsilon = 1d / 10000000d;
        V3D_PointDouble p;
        V3D_PointDouble q;
        V3D_PointDouble r;
        V3D_GeometryDouble expResult;
        V3D_GeometryDouble result;
        // Test 1
        p = new V3D_PointDouble(pP0P0P0);
        q = new V3D_PointDouble(pP0P0P0);
        r = new V3D_PointDouble(pP0P0P0);
        expResult = new V3D_PointDouble(pP0P0P0);
        result = V3D_TriangleDouble.getGeometry(p, q, r, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 2
        p = new V3D_PointDouble(pP1P0P0);
        q = new V3D_PointDouble(pP0P0P0);
        r = new V3D_PointDouble(pP0P0P0);
        expResult = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
        result = V3D_TriangleDouble.getGeometry(p, q, r, epsilon);
        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegmentDouble) result));
        // Test 2
        p = new V3D_PointDouble(pP1P0P0);
        q = new V3D_PointDouble(pP0P1P0);
        r = new V3D_PointDouble(pP0P0P0);
        expResult = new V3D_TriangleDouble(pP0P0P0, pP1P0P0, pP0P1P0);
        result = V3D_TriangleDouble.getGeometry(p, q, r, epsilon);
        assertTrue(((V3D_TriangleDouble) expResult).equals((V3D_TriangleDouble) result, epsilon));
    }

//    /**
//     * Test of getGeometry method, of class V3D_TriangleDouble.
//     */
//    @Test
//    public void testGetGeometry_4args() {
//        System.out.println("getGeometry");
//        double epsilon = 1d / 10000000d;
//        V3D_LineSegmentDouble l1;
//        V3D_LineSegmentDouble l2;
//        V3D_LineSegmentDouble l3;
//        V3D_GeometryDouble expResult;
//        V3D_GeometryDouble result;
//        // Test 1
//        l1 = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
//        l2 = new V3D_LineSegmentDouble(pP1P0P0, pP1P1P0);
//        l3 = new V3D_LineSegmentDouble(pP1P1P0, pP0P0P0);
//        expResult = new V3D_TriangleDouble(pP0P0P0, pP1P0P0, pP1P1P0);
//        result = V3D_TriangleDouble.getGeometry(l1, l2, l3, epsilon);
//        assertTrue(((V3D_TriangleDouble) expResult).equals((V3D_TriangleDouble) result, epsilon));
//        // Test 2
//        l1 = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
//        l2 = new V3D_LineSegmentDouble(pP1P1P0, pP0P2P0);
//        l3 = new V3D_LineSegmentDouble(pN1P2P0, pN1P1P0);
////        expResult = new V3D_Polygon(
////                new V3D_TriangleDouble(pP0P0P0, pP1P0P0, pP1P1P0),
////                new V3D_TriangleDouble(pP1P1P0, pP0P2P0, pN1P2P0),
////                new V3D_TriangleDouble(pP0P0P0, pN1P1P0, pN1P2P0),
////                new V3D_TriangleDouble(pP0P0P0, pP1P1P0, pN1P2P0)
////        );
////        result = V3D_TriangleDouble.getGeometry(l1, l2, l3);
////        assertEquals(expResult, result);
//
////        assertTrue(expResult.equals(result));
////        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
////        assertTrue(((V3D_TriangleDouble) expResult).equals((V3D_TriangleDouble) result));
////        assertTrue(((V3D_LineSegmentDouble) expResult).equalsIgnoreOrientation((V3D_LineSegmentDouble) result));
//    }

//    /**
//     * Test of getGeometry method, of class V3D_TriangleDouble.
//     */
//    @Test
//    public void testGetGeometry_V3D_LineSegmentDouble_V3D_LineSegmentDouble() {
//        System.out.println("getGeometry");
//        double epsilon = 1d / 10000000d;
//        V3D_LineSegmentDouble l1;
//        V3D_LineSegmentDouble l2;
//        V3D_GeometryDouble expResult;
//        V3D_GeometryDouble result;
//        // Test 1
//        l1 = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
//        l2 = new V3D_LineSegmentDouble(pP0P0P0, pP0P1P0);
//        result = V3D_TriangleDouble.getGeometry(l1, l2, epsilon);
//        expResult = new V3D_TriangleDouble(pP0P0P0, pP1P0P0, pP0P1P0);
//        assertTrue(((V3D_TriangleDouble) expResult).equals((V3D_TriangleDouble) result, epsilon));
//    }

    /**
     * Test of getOpposite method, of class V3D_TriangleDouble.
     */
    @Test
    public void testGetOpposite() {
        System.out.println("getOpposite");
        double epsilon = 1d / 10000000d;
        V3D_LineSegmentDouble l = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
        V3D_TriangleDouble instance = new V3D_TriangleDouble(pP0P0P0, pP1P0P0, pP0P1P0);
        V3D_PointDouble expResult = pP0P1P0;
        V3D_PointDouble result = instance.getOpposite(l, epsilon);
        assertTrue(expResult.equals(epsilon, result));
    }

    /**
     * Test of getDistance method, of class V3D_TriangleDouble covered by
     * {@link #testGetDistanceSquared_V3D_PointDouble()}.
     */
    @Test
    public void testGetDistance_V3D_PointDouble() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistanceSquared method, of class V3D_TriangleDouble.
     */
    @Test
    public void testGetDistanceSquared_V3D_PointDouble() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V3D_PointDouble p;
        V3D_TriangleDouble t;
        double expResult;
        // Test 1
        p = pP0P0P0;
        t = new V3D_TriangleDouble(pP0P0P0, pP1P0P0, pP0P1P0);
        expResult = 0d;
        double result = t.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 2
        p = pP1P1P0;
        t = new V3D_TriangleDouble(pP0P0P0, pP1P0P0, pP0P1P0);
        expResult = 1d / 2d;
        result = t.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 3
        p = pN1N1P1;
        t = new V3D_TriangleDouble(pN2N2P0, pP2N2P0, pN2P2P0);
        expResult = 1d;
        result = t.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 3
        p = new V3D_PointDouble(-1, -1, 10);
        t = new V3D_TriangleDouble(pN2N2P0, pP2N2P0, pN2P2P0);
        expResult = 100d;
        result = t.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 4
        p = new V3D_PointDouble(-2, -2, 10);
        t = new V3D_TriangleDouble(pN2N2P0, pP2N2P0, pN2P2P0);
        expResult = 100d;
        result = t.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 5
        p = new V3D_PointDouble(-3, -3, 0);
        t = new V3D_TriangleDouble(pN2N2P0, pP2N2P0, pN2P2P0);
        expResult = 2d;
        result = t.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 6
        p = new V3D_PointDouble(-3, -3, 1);
        t = new V3D_TriangleDouble(pN2N2P0, pP2N2P0, pN2P2P0);
        expResult = 3d;
        result = t.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 7
        p = new V3D_PointDouble(-3, -3, 10);
        t = new V3D_TriangleDouble(pN2N2P0, pP2N2P0, pN2P2P0);
        expResult = 102d;
        result = t.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 8
        p = new V3D_PointDouble(0, 0, 0);
        t = new V3D_TriangleDouble(pN1P0P1, pP1P1N1, pP2N2N2);
        expResult = 0d;
        result = t.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 9
        p = new V3D_PointDouble(0, 0, 0);
        t = new V3D_TriangleDouble(pN1P0P1, pP1P1N1, pP2N2N2);
        p.translate(t.pl.getN().getUnitVector().multiply(10));
        expResult = 100d;
        result = t.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistance method, of class V3D_TriangleDouble covered by
     * {@link #testGetDistanceSquared_V3D_LineDouble()}.
     */
    @Test
    public void testGetDistance_V3D_LineDouble() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistanceSquared method, of class V3D_TriangleDouble.
     */
    @Test
    public void testGetDistanceSquared_V3D_LineDouble() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V3D_LineDouble l;
        double expResult;
        double result;
        V3D_TriangleDouble instance;
        // Test 1
        l = new V3D_LineDouble(pP0P0P0, pP1P0P0);
        instance = new V3D_TriangleDouble(pP0P0P0, pP1P0P0, pP0P1P0);
        expResult = 0d;
        result = instance.getDistanceSquared(l, epsilon);
        assertEquals(expResult, result);
        // Test 2
        l = new V3D_LineDouble(pP0P0P1, pP1P0P1);
        instance = new V3D_TriangleDouble(pN2N2P0, pP2N2P0, pN2P2P0);
        expResult = 1d;
        result = instance.getDistanceSquared(l, epsilon);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistance method, of class V3D_TriangleDouble covered by
     * {@link #testGetDistanceSquared_V3D_LineSegmentDouble()}.
     */
    @Test
    public void testGetDistance_V3D_LineSegmentDouble() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistanceSquared method, of class V3D_TriangleDouble.
     */
    @Test
    public void testGetDistanceSquared_V3D_LineSegmentDouble() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V3D_LineSegmentDouble l;
        V3D_TriangleDouble instance;
        double expResult;
        double result;
        // Test 1
        l = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
        instance = new V3D_TriangleDouble(pP0P0P0, pP1P0P0, pP0P1P0);
        expResult = 0d;
        result = instance.getDistanceSquared(l, epsilon);
        assertEquals(expResult, result);
        // Test 2
        l = new V3D_LineSegmentDouble(pP0P0P1, pP1P0P1);
        instance = new V3D_TriangleDouble(pN2N2P0, pP2N2P0, pN2P2P0);
        expResult = 1d;
        result = instance.getDistanceSquared(l, epsilon);
        assertEquals(expResult, result);
        // Test 3
        l = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
        instance = new V3D_TriangleDouble(pN1P0P0, pN1P1P0, pN1P0P1);
        expResult = 1d;
        result = instance.getDistanceSquared(l, epsilon);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistance method, of class V3D_TriangleDouble covered by
     * {@link #testGetDistanceSquared_V3D_PlaneDouble()}.
     */
    @Test
    public void testGetDistance_V3D_PlaneDouble() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistanceSquared method, of class V3D_TriangleDouble.
     */
    @Test
    public void testGetDistanceSquared_V3D_PlaneDouble() {
        System.out.println("getDistanceSquared");
        V3D_PlaneDouble pl;
        V3D_TriangleDouble instance;
        double expResult;
        double result;
        double epsilon = 1d / 10000000d;
        // Test 1
        pl = new V3D_PlaneDouble(pP0P0P0, pP1P0P0, pP0P1P0);
        instance = new V3D_TriangleDouble(pP0P0P0, pP1P0P0, pP0P1P0);
        expResult = 0d;
        result = instance.getDistanceSquared(pl, epsilon);
        assertEquals(expResult, result);
        // Test 2
        pl = new V3D_PlaneDouble(pP0P0P1, pP1P0P1, pP0P1P1);
        instance = new V3D_TriangleDouble(pP0P0P0, pP1P0P0, pP0P1P0);
        expResult = 1d;
        result = instance.getDistanceSquared(pl, epsilon);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistance method, of class V3D_TriangleDouble covered by
     * {@link #testGetDistanceSquared_V3D_TriangleDouble()}.
     */
    @Test
    public void testGetDistance_V3D_TriangleDouble() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistanceSquared method, of class V3D_TriangleDouble.
     */
    @Test
    public void testGetDistanceSquared_V3D_TriangleDouble() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V3D_TriangleDouble t;
        V3D_TriangleDouble instance;
        double expResult;
        double result;
        // Test 1
        t = new V3D_TriangleDouble(pP0P0P0, pP1P0P0, pP0P1P0);
        instance = new V3D_TriangleDouble(pP0P0P0, pP1P0P0, pP0P1P0);
        expResult = 0d;
        result = instance.getDistanceSquared(t, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 2
        t = new V3D_TriangleDouble(pP0P0P0, pP1P0P0, pP0P1P0);
        instance = new V3D_TriangleDouble(pP0P0P1, pP1P0P1, pP0P1P1);
        expResult = 1d;
        result = instance.getDistanceSquared(t, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 3
        t = new V3D_TriangleDouble(pN2N2P0, pP2N2P0, pN2P2P0);
        instance = new V3D_TriangleDouble(pP0P0P0, pP1P0P0, pP0P1P0);
        expResult = 0d;
        result = instance.getDistanceSquared(t, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 4
        t = new V3D_TriangleDouble(pN2N2P0, pP2N2P0, pN2P2P0);
        instance = new V3D_TriangleDouble(pP0P0P1, pP1P0P1, pP0P1P1);
        expResult = 1d;
        result = instance.getDistanceSquared(t, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 5
        t = new V3D_TriangleDouble(pP0P0P0, pP1P0P0, pP0P1P0);
        instance = new V3D_TriangleDouble(pN1P0P0, pN1P0P1, pN1P1P0);
        expResult = 1d;
        result = instance.getDistanceSquared(t, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistance method, of class V3D_TriangleDouble covered by
     * {@link #testGetDistanceSquared_V3D_Tetrahedron()}.
     */
    @Test
    public void testGetDistance_V3D_Tetrahedron() {
        System.out.println("getDistance");
    }
}
