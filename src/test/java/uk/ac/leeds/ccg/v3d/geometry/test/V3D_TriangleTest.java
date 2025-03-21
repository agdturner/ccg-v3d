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
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigRational;
import uk.ac.leeds.ccg.math.geometry.Math_AngleBigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;
import uk.ac.leeds.ccg.v3d.geometry.V3D_ConvexArea;
import uk.ac.leeds.ccg.v3d.geometry.V3D_AABB;
import uk.ac.leeds.ccg.v3d.geometry.V3D_ConvexArea;
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
     * Test of intersects method, of class V3D_Triangle.
     */
    @Test
    public void testIsAligned_V3D_Point_int_RoundingMode() {
        System.out.println("intersects");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point pt = pP0N1P0;
        V3D_Triangle instance = new V3D_Triangle(pP0P2P0, pP0N2P0, pP2P0P0, oom, rm);
        assertTrue(instance.intersects(pt, oom, rm));
        pt = pN1P0P0;
        assertFalse(instance.intersects(pt, oom, rm));
    }

    /**
     * Test of getAABB method, of class V3D_Triangle.
     */
    @Test
    public void testGetAABB() {
        System.out.println("getAABB");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Environment env = new V3D_Environment(oom, rm);
        V3D_Triangle instance = new V3D_Triangle(pP0P0P0, pP0P1P0, pP1P0P0, oom, rm);
        V3D_AABB expResult = new V3D_AABB(oom, pP0P0P0, pP0P1P0, pP1P0P0);
        V3D_AABB result = instance.getAABB(oom, rm);
        assertTrue(expResult.equals(result, oom));
    }

    /**
     * Test of getArea method, of class V3D_Triangle.
     */
    @Test
    public void testGetArea() {
        System.out.println("getArea");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Triangle instance = new V3D_Triangle(pP0P0P0, pP0P1P0, pP1P0P0, oom, rm);
        BigRational expResult = BigRational.valueOf("0.5");
        BigRational result = instance.getArea(oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of translate method, of class V3D_Triangle.
     */
    @Test
    public void testApply() {
        // No test.
    }

    /**
     * Test of getPerimeter method, of class V3D_Triangle.
     */
    @Test
    public void testGetPerimeter() {
        System.out.println("getPerimeter");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Triangle instance = new V3D_Triangle(pP0P0P0, pP0P1P0, pP1P0P0, oom, rm);
        BigRational expResult = BigRational.TWO.add(
                new Math_BigRationalSqrt(2, oom, rm).getSqrt(oom, rm));
        BigRational result = instance.getPerimeter(oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        expResult = BigRational.TWO.add(
                new Math_BigRationalSqrt(2, oom, rm).getSqrt(oom, rm));
        result = instance.getPerimeter(oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getIntersect method, of class V3D_Triangle.
     */
    @Test
    public void testGetIntersect_V3D_Line() {
        System.out.println("getIntersect");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Environment env = new V3D_Environment(oom, rm);
        V3D_Line l = new V3D_Line(pP1N1P0, pP1P2P0, oom, rm);
        V3D_Triangle instance = new V3D_Triangle(pP0P0P0, pP1P1P0, pP2P0P0, oom, rm);
        V3D_Geometry expResult = new V3D_LineSegment(pP1P0P0, pP1P1P0, oom, rm);
        V3D_Geometry result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
        System.out.println("getIntersect");
        // Test 2
        instance = new V3D_Triangle(env, P0P0P0, P1P0P0, P1P2P0, P2P0P0);
        l = new V3D_Line(pP1P0P1, pP1P0N1, oom, rm);
        result = instance.getIntersect(l, oom, rm);
        expResult = pP1P0P0;
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 3
        l = new V3D_Line(pP1P0P0, pP2P0P0, oom, rm);
        expResult = new V3D_LineSegment(pP1P0P0, pP2P0P0, oom, rm);
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
        // Test 4
        l = new V3D_Line(pP1P0P0, pP2P0P0, oom, rm);
        expResult = new V3D_LineSegment(pP1P0P0, pP2P0P0, oom, rm);
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
    }

    /**
     * Test of getCentroid method, of class V3D_Triangle.
     */
    @Test
    public void testGetCentroid() {
        System.out.println("getCentroid");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Environment env = new V3D_Environment(oom, rm);
        V3D_Triangle instance;
        V3D_Point expResult;
        V3D_Point result;
        // Test
        //oom = -3; //-2 fails!
        instance = new V3D_Triangle(pP0P0P0, pP1P0P0, pP1P1P0, oom, rm);
        expResult = new V3D_Point(env, BigRational.valueOf(2, 3),
                BigRational.valueOf(1, 3), BigRational.ZERO);
        result = instance.getCentroid(oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
    }

    /**
     * Test of toString method, of class V3D_Triangle.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Environment env = new V3D_Environment(oom, rm);
        V3D_Triangle instance = new V3D_Triangle(env, P0P0P0, P1P0P0, P0P1P0, P0P0P1);
        String expResult = """
                           V3D_Triangle(
                            offset=(V3D_Vector(dx=0, dy=0, dz=0)),
                            p=(V3D_Vector(dx=1, dy=0, dz=0)),
                            q=(V3D_Vector(dx=0, dy=1, dz=0)),
                            r=(V3D_Vector(dx=0, dy=0, dz=1)))""";
        String result = instance.toString();
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of intersects method, of class V3D_Triangle.
     */
    @Test
    public void testIntersectsPoint() {
        System.out.println("intersects point");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Environment env = new V3D_Environment(oom, rm);
        V3D_Point pt;
        V3D_Triangle instance;
        instance = new V3D_Triangle(env, P0P0P0, P1P0P0, P0P1P0, P0P0P1);
        assertTrue(instance.intersects(pP1P0P0, oom, rm));
        assertTrue(instance.intersects(pP0P1P0, oom, rm));
        assertTrue(instance.intersects(pP0P0P1, oom, rm));
        pt = new V3D_Point(env, P1P0P0, P0P0P0);
        assertTrue(instance.intersects(pt, oom, rm));
        // Test 5
        pt = new V3D_Point(env, P0P1P0, P0P0P0);
        assertTrue(instance.intersects(pt, oom, rm));
        // Test 6
        pt = new V3D_Point(env, P0P0P1, P0P0P0);
        assertTrue(instance.intersects(pt, oom, rm));
        // Test 7
        pt = new V3D_Point(env, P1P0P0, P1P0P0);
        assertFalse(instance.intersects(pt, oom, rm));
        // Test 8
        pt = new V3D_Point(env, P0P1P0, P0P1P0);
        assertFalse(instance.intersects(pt, oom, rm));
        // Test 9
        pt = new V3D_Point(env, P0P0P1, P0P0P1);
        assertFalse(instance.intersects(pt, oom, rm));
        // Test 10
        instance = new V3D_Triangle(env, P0P0P0, P1P0P0, P1P2P0, P2P0P0);
        pt = new V3D_Point(env, P0P0P0, new V3D_Vector(P1, P3, P0));
        assertFalse(instance.intersects(pt, oom, rm));
        assertTrue(instance.intersects(pP1P2P0, oom, rm));
        assertTrue(instance.intersects(pP1P1P0, oom, rm));
        assertFalse(instance.intersects(pP0P0P0, oom, rm));
        pt = new V3D_Point(env, P0P0P0, new V3D_Vector(BigRational.valueOf(1.5d), P1, P0));
        assertTrue(instance.intersects(pt, oom, rm));
    }

    /**
     * Test of contains method, of class V3D_Triangle.
     */
    @Test
    public void testContainsPoint() {
        System.out.println("contains point");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Environment env = new V3D_Environment(oom, rm);
        V3D_Point pt = pP1P1P1;
        V3D_Triangle instance = new V3D_Triangle(pP0P0P0, pP1P0P0, pP1P1P0, oom, rm);
        assertFalse(instance.contains(pt, oom, rm));
        // Test 2
        pt = pN1N1P0;
        assertFalse(instance.contains(pt, oom, rm));
        // Test 3
        BigRational P0_5 = BigRational.ONE.divide(2);
        BigRational P0_75 = BigRational.valueOf(3, 4);
        pt = new V3D_Point(env, P0_5, P0_5, P0);
        assertFalse(instance.contains(pt, oom, rm));
        pt = new V3D_Point(env, P0_75, P0_5, P0);
        assertTrue(instance.contains(pt, oom, rm));
        assertFalse(instance.contains(pP0P0P0, oom, rm));
        assertFalse(instance.contains(pP1P0P0, oom, rm));
        assertFalse(instance.contains(pP1P1P0, oom, rm));
        pt = new V3D_Point(env, P0_5, P0, P0);
        assertFalse(instance.contains(pt, oom, rm));
    }
    
    /**
     * Test of contains method, of class V3D_Triangle.
     */
    @Test
    public void testContainsLineSegement() {
        System.out.println("contains line segment");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Environment env = new V3D_Environment(oom, rm);
        V3D_LineSegment l = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        V3D_Triangle instance = new V3D_Triangle(pP0P0P0, pP1P0P0, pP1P1P0, oom, rm);
        assertFalse(instance.contains(l, oom, rm));
        BigRational P0_5 = BigRational.ONE.divide(2);
        BigRational P0_75 = BigRational.valueOf(3, 4);
        BigRational P0_25 = BigRational.valueOf(1, 4);
        V3D_Point a = new V3D_Point(env, P0_5, P0_5, P0);
        V3D_Point b = new V3D_Point(env, P0_75, P0_5, P0);
        l = new V3D_LineSegment(a, b, oom, rm);
        assertFalse(instance.contains(l, oom, rm));
        a = new V3D_Point(env, P0_75, P0_25, P0);
        l = new V3D_LineSegment(a, b, oom, rm);
        assertTrue(instance.contains(l, oom, rm));
    }
    
    /**
     * Test of contains method, of class V3D_Triangle.
     */
    @Test
    public void testContainsTriangle() {
        System.out.println("contains triangle");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Environment env = new V3D_Environment(oom, rm);
        V3D_Triangle t = new V3D_Triangle(pP0P0P0, pP1P0P0, pP1P1P0, oom, rm);
        V3D_Triangle instance = new V3D_Triangle(pP0P0P0, pP1P0P0, pP1P1P0, oom, rm);
        assertFalse(instance.contains(t, oom, rm));
        BigRational P0_5 = BigRational.ONE.divide(2);
        BigRational P0_75 = BigRational.valueOf(3, 4);
        BigRational P0_25 = BigRational.valueOf(1, 4);
        V3D_Point a = new V3D_Point(env, P0_5, P0_5, P0);
        V3D_Point b = new V3D_Point(env, P0_75, P0_5, P0);
        V3D_Point c = new V3D_Point(env, P0_5, P0_25, P0);
        t = new V3D_Triangle(a, b, c, oom, rm);
        assertFalse(instance.contains(t, oom, rm));
        a = new V3D_Point(env, P0_75, P0_25, P0);
        t = new V3D_Triangle(a, b, c, oom, rm);
        assertTrue(instance.contains(t, oom, rm));
    }

    /**
     * Test of intersects0 method, of class V3D_Triangle covered by
     * {@link #testIntersects_V3D_Point()}.
     */
    @Test
    public void testIntersects0() {
        System.out.println("intersects0");
//        V3D_Point pt = null;
//        int oom = 0;
//        V3D_Triangle instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects0(pt, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getIntersect method, of class V3D_Triangle.
     */
    @Test
    public void testGetIntersect_V3D_LineSegment() {
        System.out.println("getIntersect");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Environment env = new V3D_Environment(oom, rm);
        V3D_LineSegment l;
        V3D_Triangle instance;
        V3D_Geometry expResult;
        V3D_Geometry result;
        // Test 1
        instance = new V3D_Triangle(pP1P0P0, pP1P2P0, pP2P0P0, oom, rm);
        l = new V3D_LineSegment(pP1P0P1, pP1P0N1, oom, rm);
        expResult = pP1P0P0;
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 2
        l = new V3D_LineSegment(pP1P0P0, pP2P0P0, oom, rm);
        expResult = new V3D_LineSegment(pP1P0P0, pP2P0P0, oom, rm);
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
        // Test 3
        l = new V3D_LineSegment(pP1P0P0, pP2P0P0, oom, rm);
        expResult = new V3D_LineSegment(pP1P0P0, pP2P0P0, oom, rm);
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
        // Test 4
        l = new V3D_LineSegment(pP2N2P0, pP2P1P0, oom, rm);
        instance = new V3D_Triangle(env, P0P0P0, P2P2P0,
                new V3D_Vector(P4, P0, P0));
        expResult = new V3D_LineSegment(pP2P0P0, pP2P1P0, oom, rm);
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
        // Test 5
        l = new V3D_LineSegment(pP0P0P0, pP0P1P0, oom, rm);
        instance = new V3D_Triangle(pN2N2P0, pP0P2P0, pP2N2P0, oom, rm);
        expResult = new V3D_LineSegment(pP0P0P0, pP0P1P0, oom, rm);
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
    }

    /**
     * Test of equals method, of class V3D_Triangle.
     */
    @Test
    public void testEquals_V3D_Triangle_int_RoundingMode() {
        System.out.println("equals");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Environment env = new V3D_Environment(oom, rm);
        V3D_Triangle t = new V3D_Triangle(pP1P0P0, pP1P2P0, pP2P0P0, oom, rm);
        V3D_Triangle instance = new V3D_Triangle(env, P1P0P0, P0P0P0, P0P2P0, P1P0P0);
        boolean result = instance.equals(t, oom, rm);
        assertTrue(result);
        // Test 2
        instance = new V3D_Triangle(env, P1P0P0, P0P2P0, P0P0P0, P1P0P0);
        result = instance.equals(t, oom, rm);
        assertTrue(result);
        // Test 3
        instance = new V3D_Triangle(env, P1P0P0, P0P2P0, P1P0P0, P0P0P0);
        result = instance.equals(t, oom, rm);
        assertTrue(result);
        // Test 4
        instance = new V3D_Triangle(env, P1P0P0, P1P0P0, P0P2P0, P0P0P0);
        result = instance.equals(t, oom, rm);
        assertTrue(result);
    }

    /**
     * Test of rotate method, of class V3D_Triangle.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        Math_BigDecimal bd = new Math_BigDecimal();
        BigRational theta;
        V3D_Triangle instance;
        V3D_Triangle expResult;
        BigRational Pi;
        Pi = BigRational.valueOf(
                new Math_BigDecimal().getPi(oom, RoundingMode.HALF_UP));
        // Test 1
        instance = new V3D_Triangle(pP1P0P0, pP0P1P0, pP1P1P0, oom, rm);
        V3D_Ray xaxis = new V3D_Ray(pP0P0P0, V3D_Vector.I);
        theta = Pi;
        instance = instance.rotate(xaxis, xaxis.l.v, bd, theta, oom, rm);
        expResult = new V3D_Triangle(pP1P0P0, pP0N1P0, pP1N1P0, oom, rm);
        assertTrue(expResult.equals(instance, oom, rm));
        // Test 2
        instance = new V3D_Triangle(pP1P0P0, pP0P1P0, pP1P1P0, oom, rm);
        theta = Pi.divide(2);
        instance = instance.rotate(xaxis, xaxis.l.v, bd, theta, oom, rm);
        expResult = new V3D_Triangle(pP1P0P0, pP0P0P1, pP1P0P1, oom, rm);
        assertTrue(expResult.equals(instance, oom, rm));
        // Test 3
        oom = -4;
        Pi = BigRational.valueOf(
                new Math_BigDecimal().getPi(oom, RoundingMode.HALF_UP));
        instance = new V3D_Triangle(pP2P0P0, pP0P2P0, pP2P2P0, oom, rm);
        theta = Pi;
        instance = instance.rotate(xaxis, xaxis.l.v, bd, theta, oom, rm);
        expResult = new V3D_Triangle(pP2P0P0, pP0N2P0, pP2N2P0, oom, rm);
        assertTrue(expResult.equals(instance, oom, rm));
        // Test 4
        oom = -3;
        Pi = BigRational.valueOf(
                new Math_BigDecimal().getPi(oom, RoundingMode.HALF_UP));
        instance = new V3D_Triangle(pP2P0P0, pP0P2P0, pP2P2P0, oom, rm);
        theta = Pi.divide(2);
        instance = instance.rotate(xaxis, xaxis.l.v, bd, theta, oom, rm);
        expResult = new V3D_Triangle(pP2P0P0, pP0P0P2, pP2P0P2, oom, rm);
        assertTrue(expResult.equals(instance, oom, rm));
        // Test 5
        instance = new V3D_Triangle(pP1P0P0, pP0P1P0, pP1P1P0, oom, rm);
        instance.translate(P1P0P0, oom, rm);
        theta = Pi;
        instance = instance.rotate(xaxis, xaxis.l.v, bd, theta, oom, rm);
        expResult = new V3D_Triangle(pP2P0P0, pP1N1P0, pP2N1P0, oom, rm);
        assertTrue(expResult.equals(instance, oom, rm));
    }

    /**
     * Test of getIntersect method, of class V3D_Triangle.
     */
    @Test
    public void testGetIntersect_V3D_Plane() {
        System.out.println("getIntersect");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Environment env = new V3D_Environment(oom, rm);
        V3D_Triangle t;
        V3D_Plane p;
        V3D_Geometry expResult;
        V3D_Geometry result;
        t = new V3D_Triangle(pP0P0P0, pP1P0P0, pP0P1P0, oom, rm);
        // Test 1
        p = V3D_Plane.X0;
        expResult = new V3D_LineSegment(pP0P0P0, pP0P1P0, oom, rm);
        result = t.getIntersect(p, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
        // Test 2
        p = V3D_Plane.Y0;
        expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        result = t.getIntersect(p, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
        // Test 3
        p = V3D_Plane.Z0;
        expResult = t;
        result = t.getIntersect(p, oom, rm);
        assertTrue(((V3D_Triangle) expResult).equals((V3D_Triangle) result, oom, rm));
        // Test 4
        t = new V3D_Triangle(pN1P0P0, pP1P0P0, pP0P1P0, oom, rm);
        p = V3D_Plane.X0;
        expResult = new V3D_LineSegment(pP0P0P0, pP0P1P0, oom, rm);
        result = t.getIntersect(p, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
        // Test 5
        t = new V3D_Triangle(pN2N2N2, pN2N2P2, pP2P0P0, oom, rm);
        p = V3D_Plane.X0;
        expResult = new V3D_LineSegment(pP0N1P1, pP0N1N1, oom, rm);
        result = t.getIntersect(p, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
        // Test 6
        t = new V3D_Triangle(env, P0P0P0, P1P0P0, P0P1P0, P1P1P0);
        p = V3D_Plane.X0;
        expResult = pP0P1P0;
        result = t.getIntersect(p, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 7
        p = new V3D_Plane(V3D_Plane.X0);
        p.translate(P1P0P0, oom, rm);
        expResult = new V3D_LineSegment(pP1P0P0, pP1P1P0, oom, rm);
        result = t.getIntersect(p, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equals((V3D_LineSegment) result, oom, rm));
        // Test 8
        p = new V3D_Plane(V3D_Plane.Z0);
        expResult = new V3D_Triangle(t);
        result = t.getIntersect(p, oom, rm);
        assertTrue(((V3D_Triangle) expResult).equals((V3D_Triangle) result, oom, rm));
    }

    /**
     * Test of getIntersect method, of class V3D_Triangle.
     */
    @Test
    public void testGetIntersect_V3D_Ray() {
        System.out.println("getIntersect");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Environment env = new V3D_Environment(oom, rm);
        V3D_Triangle t;
        V3D_Ray r;
        V3D_Geometry expResult;
        V3D_Geometry result;
        // Test 1
        t = new V3D_Triangle(pP0P1P0, pP1P0P0, pP1P1P0, oom, rm);
        r = new V3D_Ray(pP0P0P0, pP1P0P0, oom, rm);
        result = t.getIntersect(r, oom, rm);
        expResult = pP1P0P0;
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 2
        t = new V3D_Triangle(pP0N2P0, pP0P2P0, pP2P0P0, oom, rm);
        r = new V3D_Ray(pP1P0P0, pP2P0P0, oom, rm);
        result = t.getIntersect(r, oom, rm);
        expResult = new V3D_LineSegment(pP1P0P0, pP2P0P0, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
        // Test 3
        r = new V3D_Ray(pN1P0P0, pP2P0P0, oom, rm);
        result = t.getIntersect(r, oom, rm);
        expResult = new V3D_LineSegment(pP0P0P0, pP2P0P0, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
        // Test 4
        r = new V3D_Ray(pN2P0N2, pP0P0P0, oom, rm);
        result = t.getIntersect(r, oom, rm);
        expResult = pP0P0P0;
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 5
        r = new V3D_Ray(pN2P0N2, pP0N1P0, oom, rm);
        expResult = pP0N1P0;
        result = t.getIntersect(r, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 6
        r = new V3D_Ray(pN2P0N2, pN1P0P0, oom, rm);
        assertNull(t.getIntersect(r, oom, rm));
        // Test 7
        t = new V3D_Triangle(pP0N2P0, pP0P2P0, pP2P0P0, oom, rm);
        r = new V3D_Ray(pN2N2N2, pN1N1N1, oom, rm);
        expResult = pP0P0P0;
        result = t.getIntersect(r, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 8
        t = new V3D_Triangle(pP0N2P0, pP0P2P0, pP2P2P1, oom, rm);
        r = new V3D_Ray(pN2N2N2, pN1N1N1, oom, rm);
        expResult = pP0P0P0;
        result = t.getIntersect(r, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 9
        t = new V3D_Triangle(pN1N2P0, pN1P2P0, pP2P2P0, oom, rm);
        r = new V3D_Ray(pN2N2N2, pN1N1N1, oom, rm);
        expResult = pP0P0P0;
        result = t.getIntersect(r, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 10
        t = new V3D_Triangle(pN1N2P0, pN1P2P0, pP2P2N1, oom, rm);
        r = new V3D_Ray(pN2N2N2, pN1N1N1, oom, rm);
        BigRational nq = BigRational.valueOf(-1, 4);
        expResult = new V3D_Point(env, nq, nq, nq);
        result = t.getIntersect(r, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));

        // Box test.
        V3D_Vector offset = V3D_Vector.ZERO;
        V3D_Point[] points = new V3D_Point[8];
        V3D_Point centroid = V3D_Point.ORIGIN;
        centroid.env = env;
        long multiplier = 100;
        V3D_Point lbf = new V3D_Point(env, offset, new V3D_Vector(-1 * multiplier, -1 * multiplier, -1 * multiplier));
        V3D_Point lba = new V3D_Point(env, offset, new V3D_Vector(-1 * multiplier, -1 * multiplier, 1 * multiplier));
        V3D_Point ltf = new V3D_Point(env, offset, new V3D_Vector(-1 * multiplier, 1 * multiplier, -1 * multiplier));
        V3D_Point lta = new V3D_Point(env, offset, new V3D_Vector(-1 * multiplier, 1 * multiplier, 1 * multiplier));
        V3D_Point rbf = new V3D_Point(env, offset, new V3D_Vector(1 * multiplier, -1 * multiplier, -1 * multiplier));
        V3D_Point rba = new V3D_Point(env, offset, new V3D_Vector(1 * multiplier, -1 * multiplier, 1 * multiplier));
        V3D_Point rtf = new V3D_Point(env, offset, new V3D_Vector(1 * multiplier, 1 * multiplier, -1 * multiplier));
        V3D_Point rta = new V3D_Point(env, offset, new V3D_Vector(1 * multiplier, 1 * multiplier, 1 * multiplier));
        // BLUE
        V3D_Triangle b1 = new V3D_Triangle(centroid, lbf, ltf, rtf, oom, rm);
        V3D_Triangle b2 = new V3D_Triangle(centroid, lbf, rbf, rtf, oom, rm);
        // RED
        V3D_Triangle r1 = new V3D_Triangle(centroid, lbf, ltf, lta, oom, rm);
        V3D_Triangle r2 = new V3D_Triangle(centroid, lbf, lba, lta, oom, rm);
        // YELLOW
        V3D_Triangle y1 = new V3D_Triangle(centroid, lba, lta, rta, oom, rm);
        V3D_Triangle y2 = new V3D_Triangle(centroid, lba, rba, rta, oom, rm);
        // GREEN
        V3D_Triangle g1 = new V3D_Triangle(centroid, rbf, rtf, rta, oom, rm);
        V3D_Triangle g2 = new V3D_Triangle(centroid, rbf, rta, rba, oom, rm);
        // ORANGE
        V3D_Triangle o1 = new V3D_Triangle(centroid, ltf, lta, rta, oom, rm);
        V3D_Triangle o2 = new V3D_Triangle(centroid, rtf, ltf, rta, oom, rm);
        // PINK
        V3D_Triangle p1 = new V3D_Triangle(centroid, lbf, rbf, rba, oom, rm);
        V3D_Triangle p2 = new V3D_Triangle(centroid, lbf, lba, rba, oom, rm);
        points[0] = lbf;
        points[1] = lba;
        points[2] = ltf;
        points[3] = lta;
        points[4] = rbf;
        points[5] = rba;
        points[6] = rtf;
        points[7] = rta;
        V3D_AABB envelope = new V3D_AABB(oom, points);
        int width = 100;
        BigRational radius = envelope.getPointsArray()[0]
                .getDistance(centroid, oom, rm);
        V3D_Vector direction = new V3D_Vector(0, 0, 1).getUnitVector(oom, rm);
        V3D_Point pt = new V3D_Point(centroid);
        pt.translate(direction.multiply(radius.multiply(2), oom, rm), oom, rm);
        V3D_Plane pl = new V3D_Plane(pt, new V3D_Vector(pt, envelope.getCentroid(oom, rm), oom, rm));
        V3D_Vector pv = pl.getPV(oom, rm);
        V3D_Rectangle screen = envelope.getViewport3(pt, pv, oom, rm);
        V3D_Triangle pqr = screen.getPQR();
        BigRational screenWidth = pqr.getPQ(oom, rm).getLength(oom, rm).getSqrt(oom, rm);
        BigRational screenHeight = screenWidth;
        BigRational pixelSize = screenWidth.divide(width);
        V3D_Vector vd = new V3D_Vector(pqr.getQR(oom, rm).l.v).divide(
                BigRational.valueOf(width), oom, rm);
        V3D_Vector v2d = new V3D_Vector(pqr.getPQ(oom, rm).l.v).divide(
                BigRational.valueOf(width), oom, rm);
        int row = width / 3;
        int col = width / 2;
        r = getRay(row, col, screen, pt, vd, v2d, oom, rm);

//        expResult = new V3D_Point(nq, nq, nq);
//        result = b1.getIntersect(r, oom, rm);
//        result = b2.getIntersect(r, oom, rm);
//        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
    }

    /**
     * For getting a ray from the camera focal point through the centre of the
     * screen pixel with ID id.
     *
     * @param id The ID of the screen pixel.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The ray from the camera focal point through the centre of the
     * screen pixel.
     */
    protected V3D_Ray getRay(int row, int col, V3D_Rectangle screen, V3D_Point pt, V3D_Vector vd,
            V3D_Vector v2d, int oom, RoundingMode rm) {
        V3D_Vector rv = v2d.multiply(BigRational.valueOf(row), oom, rm);
        V3D_Vector cv = vd.multiply(BigRational.valueOf(col), oom, rm);
        V3D_Point rcpt = new V3D_Point(screen.getP(oom, rm));
        rcpt.translate(rv.add(cv, oom, rm), oom, rm);
        return new V3D_Ray(pt, rcpt, oom, rm);
    }

    /**
     * Test of getIntersect method, of class V3D_Triangle.
     *
     * Look for some examples here:
     * https://math.stackexchange.com/questions/1220102/how-do-i-find-the-intersection-of-two-3d-triangles
     */
    @Test
    public void testGetIntersect_V3D_Triangle() {
        System.out.println("getIntersect");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Environment env = new V3D_Environment(oom, rm);
        V3D_Triangle t = new V3D_Triangle(pP0P0P0, pP0P1P0, pP1P0P0, oom, rm);
        V3D_Triangle instance = new V3D_Triangle(pP0P0P0, pP0P1P0, pP1P0P0, oom, rm);
        V3D_Geometry expResult = new V3D_Triangle(pP0P0P0, pP0P1P0, pP1P0P0, oom, rm);
        V3D_Geometry result = instance.getIntersect(t, oom, rm);
        assertTrue(((V3D_Triangle) expResult).equals((V3D_Triangle) result, oom, rm));
        // Test 2
        t = new V3D_Triangle(pN1N1P0, pP0P2P0, new V3D_Point(env, P3, N1, P0), oom, rm);
        instance = new V3D_Triangle(pP0P0P0, pP0P1P0, pP1P0P0, oom, rm);
        expResult = new V3D_Triangle(pP0P0P0, pP0P1P0, pP1P0P0, oom, rm);
        result = instance.getIntersect(t, oom, rm);
        assertTrue(((V3D_Triangle) expResult).equals((V3D_Triangle) result, oom, rm));
        // Test 3
        t = new V3D_Triangle(pP0P0P0, pP0P1P0, pP1P0P0, oom, rm);
        instance = new V3D_Triangle(pN1N1P0, pP0P2P0, new V3D_Point(env, P3, N1, P0), oom, rm);
        expResult = new V3D_Triangle(pP0P0P0, pP0P1P0, pP1P0P0, oom, rm);
        result = instance.getIntersect(t, oom, rm);
        assertTrue(((V3D_Triangle) expResult).equals((V3D_Triangle) result, oom, rm));
        // Test 4
        t = new V3D_Triangle(pP0P0P0, pP2P0P0, pP2P2P0, oom, rm);
        instance = new V3D_Triangle(
                pP1P0P0, new V3D_Point(env, P3, P0, P0), 
                new V3D_Point(env, P3, P2, P0), oom, rm);
        expResult = new V3D_Triangle(pP1P0P0, pP2P0P0, pP2P1P0, oom, rm);
        result = instance.getIntersect(t, oom, rm);
        assertTrue(((V3D_Triangle) expResult).equals((V3D_Triangle) result, oom, rm));
//        // Test 5: From  https://stackoverflow.com/a/29563443/1998054
//        t = new V3D_Triangle(new V3D_Point(env, -21, -72, 63),
//                new V3D_Point(env, -78, 99, 40),
//                new V3D_Point(env, -19, -78, -83), oom, rm);
//        instance = new V3D_Triangle(new V3D_Point(env, 96, 77, -51),
//                new V3D_Point(env, -95, -1, -16),
//                new V3D_Point(env, 9, 5, -21), oom, rm);
//        // This expected result is not given in the answer on stack overflow.
//        expResult = new V3D_LineSegment(
//                new V3D_Point(env, -34.630630630630634, -31.108108108108105, -5.95495495495496),
//                new V3D_Point(env, -48.45827629341561, 10.37482888024681, -21.586983320506448), oom, rm);
//        result = instance.getIntersect(t, oom, rm);
//        System.out.println(result);
//        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
        // Test 6: From https://web.mst.edu/~chaman/home/pubs/2015WimoTriangleTrianglePublished.pdf
        t = new V3D_Triangle(new V3D_Point(env, 0, 0, 0),
                new V3D_Point(env, 6, 0, 0),
                new V3D_Point(env, 0, 6, 0), oom, rm);
        instance = new V3D_Triangle(new V3D_Point(env, 0, 3, 3),
                new V3D_Point(env, 0, 3, -3),
                new V3D_Point(env, -3, 3, 3), oom, rm);
        // This expected result is not given in the answer on stack overflow.
        expResult = new V3D_Point(env, 0, 3, 0);
        result = instance.getIntersect(t, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 7: From https://web.mst.edu/~chaman/home/pubs/2015WimoTriangleTrianglePublished.pdf
        t = new V3D_Triangle(new V3D_Point(env, 0, 6, 0),
                new V3D_Point(env, 6, 0, 0),
                new V3D_Point(env, 0, 0, 0), oom, rm);
        instance = new V3D_Triangle(new V3D_Point(env, 1, 3, 0),
                new V3D_Point(env, 3, 1, 0),
                new V3D_Point(env, 2, 2, 4), oom, rm);
        result = instance.getIntersect(t, oom, rm);
        expResult = new V3D_LineSegment(new V3D_Point(env, 1, 3, 0),
                new V3D_Point(env, 3, 1, 0), oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
        // Test 8: From https://math.stackexchange.com/questions/1220102/how-do-i-find-the-intersection-of-two-3d-triangles
        t = new V3D_Triangle(new V3D_Point(env, 6, 8, 3), new V3D_Point(env, 6, 8, -2),
                new V3D_Point(env, 6, -4, -2), oom, rm);
        instance = new V3D_Triangle(new V3D_Point(env, 0, 5, 0),
                new V3D_Point(env, 0, 0, 0),
                new V3D_Point(env, 8, 0, 0), oom, rm);
        result = instance.getIntersect(t, oom, rm);
        expResult = new V3D_LineSegment(new V3D_Point(env, 6, 1.25d, 0),
                new V3D_Point(env, 6, 0.8d, 0), oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
        // Test 9: 4 sides
        t = new V3D_Triangle(new V3D_Point(env, P2, N3, P0), new V3D_Point(env, P6, P1, P0), new V3D_Point(env, P2, P5, P0), oom, rm);
        instance = new V3D_Triangle(pP1P0P0, new V3D_Point(env, P3, P0, P0), new V3D_Point(env, P3, P2, P0), oom, rm);
        expResult = new V3D_ConvexArea(oom, rm,
                new V3D_Triangle(pP2P0P0, new V3D_Point(env, P3, P0, P0),
                        pP2P1P0, oom, rm),
                new V3D_Triangle(
                        new V3D_Point(env, P3, P0, P0),
                        new V3D_Point(env, P3, P2, P0),
                        pP2P1P0, oom, rm));
        result = instance.getIntersect(t, oom, rm);
        assertTrue(((V3D_ConvexArea) expResult).equals((V3D_ConvexArea) result, oom, rm));
        // Test 10: 5 sides
        t = new V3D_Triangle(pP0P0P0, new V3D_Point(env, P4, P0, P0), new V3D_Point(env, P2, N4, P0), oom, rm);
        instance = new V3D_Triangle(pP0N2P0, pP2P0P0, new V3D_Point(env, P4, N2, P0), oom, rm);
        BigRational N4_3 = BigRational.valueOf(-4).divide(3);
        V3D_Point m = new V3D_Point(env, BigRational.TWO.divide(3), N4_3, P0);
        V3D_Point n = new V3D_Point(env, BigRational.TEN.divide(3), N4_3, P0);
        expResult = new V3D_ConvexArea(oom, rm,
                new V3D_Triangle(pP2P0P0, m, n, oom, rm),
                //new V3D_Triangle(m, n, pP1N2P0),
                new V3D_Triangle(m, n, new V3D_Point(env, P3, N2, P0), oom, rm),
                new V3D_Triangle(pP1N2P0,
                        new V3D_Point(env, P3, N2, P0),
                        n, oom, rm));
        result = instance.getIntersect(t, oom, rm);
        //System.out.println(((V3D_ConvexArea) expResult).toStringSimple());
        //System.out.println(((V3D_ConvexArea) result).toStringSimple());
        assertTrue(((V3D_ConvexArea) expResult).equals((V3D_ConvexArea) result, oom, rm));
//        // Test 11: 6 sides
//        t = new V3D_Triangle(pP0P0P0, new V3D_Point(P6, P0, P0), new V3D_Point(P3, N3, P0), oom, rm);
//        instance = new V3D_Triangle(pP0N2P0, new V3D_Point(P3, P1, P0), new V3D_Point(P6, N2, P0), oom, rm);
//        expResult = new V3D_ConvexArea(oom, rm,
//                new V3D_Triangle(pP2P0P0, new V3D_Point(P4, P0, P0), pP1N1P0, oom, rm),
//                new V3D_Triangle(pP1N1P0, pP2N2P0, new V3D_Point(P4, N2, P0), oom, rm),
//                new V3D_Triangle(
//                        new V3D_Point(P4, P0, P0),
//                        new V3D_Point(P4, N2, P0),
//                        new V3D_Point(P5, N1, P0), oom, rm),
//                new V3D_Triangle(new V3D_Point(P4, P0, P0), pP1N1P0,
//                        new V3D_Point(P4, N2, P0), oom, rm));
//        result = instance.getIntersect(t, oom, rm);
//        assertTrue(((V3D_ConvexArea) expResult).equals((V3D_ConvexArea) result, oom, rm));
//        // Test 12: 6 sides
//        t = new V3D_Triangle(new V3D_Point(P6, P0, P0), pP0P0P0, new V3D_Point(P3, N3, P0), oom, rm);
//        instance = new V3D_Triangle(pP0N2P0, new V3D_Point(P3, P1, P0), new V3D_Point(P6, N2, P0), oom, rm);
//        expResult = new V3D_ConvexArea(oom, rm,
//                new V3D_Triangle(pP2P0P0, new V3D_Point(P4, P0, P0), pP1N1P0, oom, rm),
//                new V3D_Triangle(pP1N1P0, pP2N2P0, new V3D_Point(P4, N2, P0), oom, rm),
//                new V3D_Triangle(
//                        new V3D_Point(P4, P0, P0),
//                        new V3D_Point(P4, N2, P0),
//                        new V3D_Point(P5, N1, P0), oom, rm),
//                new V3D_Triangle(new V3D_Point(P4, P0, P0), pP1N1P0,
//                        new V3D_Point(P4, N2, P0), oom, rm));
//        result = instance.getIntersect(t, oom, rm);
//        assertTrue(((V3D_ConvexArea) expResult).equals((V3D_ConvexArea) result, oom, rm));
//
//        // Test 13 LineSegment intersection
//        t = new V3D_Triangle(new V3D_Point(P6, P0, P0), pP0P0P0, new V3D_Point(P3, N3, P0), oom, rm);
//        instance = new V3D_Triangle(pP0N2P0, new V3D_Point(P3, P1, P0), new V3D_Point(P6, N2, P0), oom, rm);
//        expResult = new V3D_ConvexArea(oom, rm,
//                new V3D_Triangle(pP2P0P0, new V3D_Point(P4, P0, P0), pP1N1P0, oom, rm),
//                new V3D_Triangle(pP1N1P0, pP2N2P0, new V3D_Point(P4, N2, P0), oom, rm),
//                new V3D_Triangle(
//                        new V3D_Point(P4, P0, P0),
//                        new V3D_Point(P4, N2, P0),
//                        new V3D_Point(P5, N1, P0), oom, rm),
//                new V3D_Triangle(new V3D_Point(P4, P0, P0), pP1N1P0,
//                        new V3D_Point(P4, N2, P0), oom, rm));
//        result = instance.getIntersect(t, oom, rm);
//        assertTrue(((V3D_ConvexArea) expResult).equals((V3D_ConvexArea) result, oom, rm));
    }

    /**
     * Test of getGeometry method, of class V3D_Triangle.
     */
    @Test
    public void testGetGeometry_V3D_Vector_V3D_Vector() {
        System.out.println("getGeometry");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Environment env = new V3D_Environment(oom, rm);
        V3D_Vector v1;
        V3D_Vector v2;
        V3D_Geometry expResult;
        V3D_Geometry result;
        // Test 1
        v1 = P0P0P0;
        v2 = P0P0P0;
        expResult = pP0P0P0;
        result = V3D_Triangle.getGeometry(env, v1, v2, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 2
        v2 = P1P0P0;
        expResult = new V3D_LineSegment(env, P0P0P0, P0P0P0, P1P0P0, oom, rm);
        result = V3D_Triangle.getGeometry(env, v1, v2, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
    }

    /**
     * Test of translate method, of class V3D_Triangle.
     */
    @Test
    public void testTranslate() {
        System.out.println("translate");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Environment env = new V3D_Environment(oom, rm);
        V3D_Vector v = P1P0P0;
        V3D_Triangle instance = new V3D_Triangle(env, P0P0P0, P1P0P0, P0P1P0, P1P1P0);
        instance.translate(v, oom, rm);
        V3D_Triangle expResult = new V3D_Triangle(env, P0P0P0, P2P0P0, P1P1P0, P2P1P0);
        assertTrue(expResult.equals(instance, oom, rm));
        // Test 2
        expResult = new V3D_Triangle(env, P1P0P0, P1P0P0, P0P1P0, P1P1P0);
        //System.out.println(instance);
        //System.out.println(expResult);
        //boolean te = expResult.equals(instance);
        assertTrue(expResult.equals(instance, oom, rm));
    }

    /**
     * Test of getGeometry method, of class V3D_Triangle.
     */
    @Test
    public void testGetGeometry_3args_1() {
        System.out.println("getGeometry");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point p;
        V3D_Point q;
        V3D_Point r;
        V3D_Geometry expResult;
        V3D_Geometry result;
        // Test 1
        p = new V3D_Point(pP0P0P0);
        q = new V3D_Point(pP0P0P0);
        r = new V3D_Point(pP0P0P0);
        expResult = new V3D_Point(pP0P0P0);
        result = V3D_Triangle.getGeometry(p, q, r, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 2
        p = new V3D_Point(pP1P0P0);
        q = new V3D_Point(pP0P0P0);
        r = new V3D_Point(pP0P0P0);
        expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        result = V3D_Triangle.getGeometry(p, q, r, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
        // Test 2
        p = new V3D_Point(pP1P0P0);
        q = new V3D_Point(pP0P1P0);
        r = new V3D_Point(pP0P0P0);
        expResult = new V3D_Triangle(pP0P0P0, pP1P0P0, pP0P1P0, oom, rm);
        result = V3D_Triangle.getGeometry(p, q, r, oom, rm);
        assertTrue(((V3D_Triangle) expResult).equals((V3D_Triangle) result, oom, rm));
    }

//    /**
//     * Test of getGeometry method, of class V3D_Triangle.
//     */
//    @Test
//    public void testGetGeometry_4args() {
//        System.out.println("getGeometry");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_LineSegment l1;
//        V3D_LineSegment l2;
//        V3D_LineSegment l3;
//        V3D_Geometry expResult;
//        V3D_Geometry result;
//        // Test 1
//        l1 = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
//        l2 = new V3D_LineSegment(pP1P0P0, pP1P1P0, oom, rm);
//        l3 = new V3D_LineSegment(pP1P1P0, pP0P0P0, oom, rm);
//        expResult = new V3D_Triangle(pP0P0P0, pP1P0P0, pP1P1P0, oom, rm);
//        result = V3D_Triangle.getGeometry(l1, l2, l3, oom, rm);
//        assertTrue(((V3D_Triangle) expResult).equals((V3D_Triangle) result, oom, rm));
//        // Test 2
//        l1 = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
//        l2 = new V3D_LineSegment(pP1P1P0, pP0P2P0, oom, rm);
//        l3 = new V3D_LineSegment(pN1P2P0, pN1P1P0, oom, rm);
////        expResult = new V3D_Polygon(
////                new V3D_Triangle(pP0P0P0, pP1P0P0, pP1P1P0),
////                new V3D_Triangle(pP1P1P0, pP0P2P0, pN1P2P0),
////                new V3D_Triangle(pP0P0P0, pN1P1P0, pN1P2P0),
////                new V3D_Triangle(pP0P0P0, pP1P1P0, pN1P2P0)
////        );
////        result = V3D_Triangle.getGeometry(l1, l2, l3, oom, rm);
////        assertEquals(expResult, result);
//
////        assertTrue(expResult.equals(result, oom, rm));
////        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
////        assertTrue(((V3D_Triangle) expResult).equals((V3D_Triangle) result, oom, rm));
////        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreOrientation((V3D_LineSegment) result, oom, rm));
//    }
    /**
     * Test of getGeometry method, of class V3D_Triangle.
     */
    @Test
    public void testGetGeometry_3args_2() {
        System.out.println("getGeometry");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Vector v1;
        V3D_Vector v2;
        V3D_Geometry expResult;
        V3D_Geometry result;
        // Test 1
        v1 = P0P0P0;
        v2 = P0P0P0;
        expResult = pP0P0P0;
        result = V3D_Triangle.getGeometry(env, v1, v2, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 2
        v1 = P0P0P0;
        v2 = P1P0P0;
        expResult = new V3D_LineSegment(pP1P0P0, pP0P0P0, oom, rm);
        result = V3D_Triangle.getGeometry(env, v1, v2, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
    }

//    /**
//     * Test of getGeometry method, of class V3D_Triangle.
//     */
//    @Test
//    public void testGetGeometry_V3D_LineSegment_V3D_LineSegment() {
//        System.out.println("getGeometry");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_LineSegment l1;
//        V3D_LineSegment l2;
//        V3D_Geometry expResult;
//        V3D_Geometry result;
//        // Test 1
//        l1 = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
//        l2 = new V3D_LineSegment(pP0P0P0, pP0P1P0, oom, rm);
//        result = V3D_Triangle.getGeometry(l1, l2, oom, rm);
//        expResult = new V3D_Triangle(pP0P0P0, pP1P0P0, pP0P1P0, oom, rm);
//        assertTrue(((V3D_Triangle) expResult).equals((V3D_Triangle) result, oom, rm));
//    }
    /**
     * Test of getOpposite method, of class V3D_Triangle.
     */
    @Test
    public void testGetOpposite() {
        System.out.println("getOpposite");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_LineSegment l = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        V3D_Triangle instance = new V3D_Triangle(pP0P0P0, pP1P0P0, pP0P1P0, oom, rm);
        V3D_Point expResult = pP0P1P0;
        V3D_Point result = instance.getOpposite(l, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
    }

//    /**
//     * Test of intersects method, of class V3D_Triangle.
//     */
//    @Test
//    public void testIntersects_V3D_Plane() {
//        System.out.println("intersects");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_Plane pl;
//        V3D_Triangle instance;
//        // Test 1
//        pl = new V3D_Plane(V3D_Plane.Y0);
//        pl.translate(P0P1P0, oom, rm);
//        instance = new V3D_Triangle(pP0P0P0, pP2P0P0, pP0P2P0, oom, rm);
//        assertTrue(instance.intersects(pl, oom, rm));
//        // Test 2
//        pl = V3D_Plane.X0;
//        instance = new V3D_Triangle(pP0P0P0, pP1P0P0, pP0P1P0, oom, rm);
//        assertTrue(instance.intersects(pl, oom, rm));
//        // Test 3
//        pl = V3D_Plane.Y0;
//        assertTrue(instance.intersects(pl, oom, rm));
//        // Test 4
//        pl = V3D_Plane.Z0;
//        assertTrue(instance.intersects(pl, oom, rm));
//    }
//
//    /**
//     * Test of intersects method, of class V3D_Triangle.
//     */
//    @Test
//    public void testIntersects_V3D_Tetrahedron() {
//        System.out.println("intersects");
//        V3D_Tetrahedron t = null;
//        int oom = 0;
//        V3D_Triangle instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects(t, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getIntersect method, of class V3D_Triangle.
//     */
//    @Test
//    public void testGetIntersect_V3D_Tetrahedron() {
//        System.out.println("getIntersect");
//        V3D_Tetrahedron t = null;
//        int oom = 0;
//        V3D_Triangle instance = null;
//        V3D_Geometry expResult = null;
//        V3D_Geometry result = instance.getIntersect(t, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    /**
     * Test of getDistance method, of class V3D_Triangle covered by
     * {@link #testGetDistanceSquared_V3D_Point()}.
     */
    @Test
    public void testGetDistance_V3D_Point() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Triangle.
     */
    @Test
    public void testGetDistanceSquared_V3D_Point() {
        System.out.println("getDistanceSquared");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point p;
        V3D_Triangle t;
        BigRational expResult;
        // Test 1
        p = pP0P0P0;
        t = new V3D_Triangle(pP0P0P0, pP1P0P0, pP0P1P0, oom, rm);
        expResult = BigRational.ZERO;
        BigRational result = t.getDistanceSquared(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 2
        p = pP1P1P0;
        t = new V3D_Triangle(pP0P0P0, pP1P0P0, pP0P1P0, oom, rm);
        expResult = BigRational.valueOf(1, 2);
        result = t.getDistanceSquared(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 3
        p = pN1N1P1;
        t = new V3D_Triangle(pN2N2P0, pP2N2P0, pN2P2P0, oom, rm);
        expResult = BigRational.ONE;
        result = t.getDistanceSquared(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 3
        p = new V3D_Point(env, -1, -1, 10);
        t = new V3D_Triangle(pN2N2P0, pP2N2P0, pN2P2P0, oom, rm);
        expResult = BigRational.valueOf(100);
        result = t.getDistanceSquared(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 4
        p = new V3D_Point(env, -2, -2, 10);
        t = new V3D_Triangle(pN2N2P0, pP2N2P0, pN2P2P0, oom, rm);
        expResult = BigRational.valueOf(100);
        result = t.getDistanceSquared(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 5
        p = new V3D_Point(env, -3, -3, 0);
        t = new V3D_Triangle(pN2N2P0, pP2N2P0, pN2P2P0, oom, rm);
        expResult = BigRational.valueOf(2);
        result = t.getDistanceSquared(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 6
        p = new V3D_Point(env, -3, -3, 1);
        t = new V3D_Triangle(pN2N2P0, pP2N2P0, pN2P2P0, oom, rm);
        expResult = BigRational.valueOf(3);
        result = t.getDistanceSquared(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 7
        p = new V3D_Point(env, -3, -3, 10);
        t = new V3D_Triangle(pN2N2P0, pP2N2P0, pN2P2P0, oom, rm);
        expResult = BigRational.valueOf(102);
        result = t.getDistanceSquared(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 8
        p = new V3D_Point(env, 0, 0, 0);
        t = new V3D_Triangle(pN1P0P1, pP1P1N1, pP2N2N2, oom, rm);
        expResult = BigRational.valueOf(0);
        result = t.getDistanceSquared(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 9
        p = new V3D_Point(env, 0, 0, 0);
        oom = -3;
        int oomn3 = oom - 3;
        t = new V3D_Triangle(pN1P0P1, pP1P1N1, pP2N2N2, oomn3, rm);
        p.translate(t.getPl(oom, rm).getN().getUnitVector(oomn3, rm).multiply(10, oomn3, rm), oomn3, rm);
        expResult = BigRational.valueOf(100);
        result = Math_BigRational.round(t.getDistanceSquared(p, oom, rm), oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getDistance method, of class V3D_Triangle covered by
     * {@link #testGetDistanceSquared_V3D_Line()}.
     */
    @Test
    public void testGetDistance_V3D_Line() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Triangle.
     */
    @Test
    public void testGetDistanceSquared_V3D_Line() {
        System.out.println("getDistanceSquared");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line l;
        BigRational expResult;
        BigRational result;
        V3D_Triangle instance;
        // Test 1
        l = new V3D_Line(pP0P0P0, pP1P0P0, oom, rm);
        instance = new V3D_Triangle(pP0P0P0, pP1P0P0, pP0P1P0, oom, rm);
        expResult = BigRational.ZERO;
        result = instance.getDistanceSquared(l, oom, rm);
        assertEquals(expResult, result);
        // Test 2
        l = new V3D_Line(pP0P0P1, pP1P0P1, oom, rm);
        instance = new V3D_Triangle(pN2N2P0, pP2N2P0, pN2P2P0, oom, rm);
        expResult = BigRational.ONE;
        result = instance.getDistanceSquared(l, oom, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistance method, of class V3D_Triangle covered by
     * {@link #testGetDistanceSquared_V3D_LineSegment()}.
     */
    @Test
    public void testGetDistance_V3D_LineSegment() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Triangle.
     */
    @Test
    public void testGetDistanceSquared_V3D_LineSegment() {
        System.out.println("getDistanceSquared");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_LineSegment l;
        V3D_Triangle instance;
        BigRational expResult;
        BigRational result;
        // Test 1
        l = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        instance = new V3D_Triangle(pP0P0P0, pP1P0P0, pP0P1P0, oom, rm);
        expResult = BigRational.ZERO;
        result = instance.getDistanceSquared(l, oom, rm);
        assertEquals(expResult, result);
        // Test 2
        l = new V3D_LineSegment(pP0P0P1, pP1P0P1, oom, rm);
        instance = new V3D_Triangle(pN2N2P0, pP2N2P0, pN2P2P0, oom, rm);
        expResult = BigRational.ONE;
        result = instance.getDistanceSquared(l, oom, rm);
        assertEquals(expResult, result);
        // Test 3
        l = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        instance = new V3D_Triangle(pN1P0P0, pN1P1P0, pN1P0P1, oom, rm);
        expResult = BigRational.ONE;
        result = instance.getDistanceSquared(l, oom, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistance method, of class V3D_Triangle covered by
     * {@link #testGetDistanceSquared_V3D_Plane()}.
     */
    @Test
    public void testGetDistance_V3D_Plane() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Triangle.
     */
    @Test
    public void testGetDistanceSquared_V3D_Plane() {
        System.out.println("getDistanceSquared");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Plane pl;
        V3D_Triangle instance;
        BigRational expResult;
        BigRational result;
        // Test 1
        pl = new V3D_Plane(pP0P0P0, pP1P0P0, pP0P1P0, oom, rm);
        instance = new V3D_Triangle(pP0P0P0, pP1P0P0, pP0P1P0, oom, rm);
        expResult = BigRational.ZERO;
        result = instance.getDistanceSquared(pl, oom, rm);
        assertEquals(expResult, result);
        // Test 2
        pl = new V3D_Plane(pP0P0P1, pP1P0P1, pP0P1P1, oom, rm);
        instance = new V3D_Triangle(pP0P0P0, pP1P0P0, pP0P1P0, oom, rm);
        expResult = BigRational.ONE;
        result = instance.getDistanceSquared(pl, oom, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistance method, of class V3D_Triangle covered by
     * {@link #testGetDistanceSquared_V3D_Triangle()}.
     */
    @Test
    public void testGetDistance_V3D_Triangle() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Triangle.
     */
    @Test
    public void testGetDistanceSquared_V3D_Triangle() {
        System.out.println("getDistanceSquared");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Triangle t;
        V3D_Triangle instance;
        BigRational expResult;
        BigRational result;
        // Test 1
        t = new V3D_Triangle(pP0P0P0, pP1P0P0, pP0P1P0, oom, rm);
        instance = new V3D_Triangle(pP0P0P0, pP1P0P0, pP0P1P0, oom, rm);
        expResult = BigRational.ZERO;
        result = instance.getDistanceSquared(t, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 2
        t = new V3D_Triangle(pP0P0P0, pP1P0P0, pP0P1P0, oom, rm);
        instance = new V3D_Triangle(pP0P0P1, pP1P0P1, pP0P1P1, oom, rm);
        expResult = BigRational.ONE;
        result = instance.getDistanceSquared(t, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 3
        t = new V3D_Triangle(pN2N2P0, pP2N2P0, pN2P2P0, oom, rm);
        instance = new V3D_Triangle(pP0P0P0, pP1P0P0, pP0P1P0, oom, rm);
        expResult = BigRational.ZERO;
        result = instance.getDistanceSquared(t, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 4
        t = new V3D_Triangle(pN2N2P0, pP2N2P0, pN2P2P0, oom, rm);
        instance = new V3D_Triangle(pP0P0P1, pP1P0P1, pP0P1P1, oom, rm);
        expResult = BigRational.ONE;
        result = instance.getDistanceSquared(t, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 5
        t = new V3D_Triangle(pP0P0P0, pP1P0P0, pP0P1P0, oom, rm);
        instance = new V3D_Triangle(pN1P0P0, pN1P0P1, pN1P1P0, oom, rm);
        expResult = BigRational.ONE;
        result = instance.getDistanceSquared(t, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getDistance method, of class V3D_Triangle covered by
     * {@link #testGetDistanceSquared_V3D_Tetrahedron()}.
     */
    @Test
    public void testGetDistance_V3D_Tetrahedron() {
        System.out.println("getDistance");
    }
}
