/*
 * Copyright 2022 Centre for Computational Geography.
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
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;

/**
 *
 * @author Andy Turner
 */
public class V3D_LineSegmentsCollinearTest extends V3D_Test {

    public V3D_LineSegmentsCollinearTest() {
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
     * Test of equals method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testEquals_V3D_LineSegmentsCollinear() {
        System.out.println("equals");
        int oom = -1;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_LineSegmentsCollinear l = new V3D_LineSegmentsCollinear(
                new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm),
                new V3D_LineSegment(pP0P1P0, pP1P1P0, oom, rm));
        V3D_LineSegmentsCollinear instance = new V3D_LineSegmentsCollinear(
                new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm),
                new V3D_LineSegment(pP0P1P0, pP1P1P0, oom, rm));
        boolean result = instance.equals(l, oom, rm);
        assertTrue(result);
        // Test 2
        instance = new V3D_LineSegmentsCollinear(
                new V3D_LineSegment(pP0P1P0, pP1P0P0, oom, rm),
                new V3D_LineSegment(pP0P1P0, pP1P1P0, oom, rm));
        result = instance.equals(l, oom, rm);
        assertFalse(result);
    }

    /**
     * Test of getGeometry method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetUnion() {
        System.out.println("getUnion");
        int oom = -1;
        RoundingMode rm = RoundingMode.HALF_UP;
        // Test 1 touch
        V3D_LineSegment l1 = new V3D_LineSegment(pN2P0P0, pN1P0P0, oom, rm);
        V3D_LineSegment l2 = new V3D_LineSegment(pN1P0P0, pP0P0P0, oom, rm);
        V3D_Geometry expResult = new V3D_LineSegment(pN2P0P0, pP0P0P0, oom, rm);
        V3D_Geometry result = V3D_LineSegmentsCollinear.getGeometry(l1, l2, oom, rm);//getUnion(l1, l2, oom);
        assertTrue(((V3D_LineSegment) expResult).equals((V3D_LineSegment) result, oom, rm));
        result = V3D_LineSegmentsCollinear.getGeometry(l2, l1, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equals((V3D_LineSegment) result, oom, rm));
        // Test 2 overlap
        l2 = new V3D_LineSegment(pN2P0P0, pP0P0P0, oom, rm);
        expResult = new V3D_LineSegment(pN2P0P0, pP0P0P0, oom, rm);
        result = V3D_LineSegmentsCollinear.getGeometry(l1, l2, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equals((V3D_LineSegment) result, oom, rm));
        result = V3D_LineSegmentsCollinear.getGeometry(l2, l1, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equals((V3D_LineSegment) result, oom, rm));
        l1 = new V3D_LineSegment(pN2P0P0, pP2P0P0, oom, rm);
        l2 = new V3D_LineSegment(pN1P0P0, pP1P0P0, oom, rm);
        expResult = new V3D_LineSegment(pN2P0P0, pP2P0P0, oom, rm);
        result = V3D_LineSegmentsCollinear.getGeometry(l1, l2, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equals((V3D_LineSegment) result, oom, rm));
        // No overlap
        l1 = new V3D_LineSegment(pN2P0P0, pN1P0P0, oom, rm);
        l2 = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        expResult = new V3D_LineSegmentsCollinear(l1, l2);
        result = V3D_LineSegmentsCollinear.getGeometry(l1, l2, oom, rm);
        assertTrue(((V3D_LineSegmentsCollinear) expResult).equals((V3D_LineSegmentsCollinear) result, oom, rm));
        result = V3D_LineSegmentsCollinear.getGeometry(l2, l1, oom, rm);
        assertTrue(((V3D_LineSegmentsCollinear) expResult).equals((V3D_LineSegmentsCollinear) result, oom, rm));
    }

    /**
     * Test of getEnvelope method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetEnvelope() {
        // No test.
    }

    /**
     * Test of getDistance method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetDistance_V3D_Point_int() {
        // No test.
    }

    /**
     * Test of getDistanceSquared method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetDistanceSquared_V3D_Point_int() {
        // No test.
    }

    /**
     * Test of getDistance method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetDistance_V3D_Line_int() {
        // No test.
    }

    /**
     * Test of getDistanceSquared method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetDistanceSquared_V3D_Line_int() {
        // No test.
    }

    /**
     * Test of getDistance method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetDistance_V3D_LineSegment_int() {
        // No test.
    }

    /**
     * Test of getDistanceSquared method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetDistanceSquared_V3D_LineSegment_int() {
        // No test.
    }

    /**
     * Test of isIntersectedBy method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point_int() {
        System.out.println("isIntersectedBy");
        // No test yet.
//        V3D_Point pt = null;
//        int oom = 0;
//        V3D_LineSegmentsCollinear instance = null;
//        boolean expResult = false;
//        boolean result = instance.isIntersectedBy(pt, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of isIntersectedBy method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testIsIntersectedBy_V3D_Line_int() {
        System.out.println("isIntersectedBy");
        // No test.
    }

    /**
     * Test of isIntersectedBy method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testIsIntersectedBy_V3D_LineSegment_int() {
        System.out.println("isIntersectedBy");
        // No test.
    }

    /**
     * Test of getIntersection method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetIntersection_V3D_Line_int() {
        System.out.println("getIntersection");
        int oom = -1;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line l = new V3D_Line(pP0P0P0, pP1P0P0, oom, rm);
        V3D_LineSegmentsCollinear instance = new V3D_LineSegmentsCollinear(
                new V3D_LineSegment(pN1P0P0, pP0P0P0, oom, rm),
                new V3D_LineSegment(pP1P0P0, pP2P0P0, oom, rm));
        V3D_Geometry expResult = new V3D_LineSegmentsCollinear(
                new V3D_LineSegment(pN1P0P0, pP0P0P0, oom, rm),
                new V3D_LineSegment(pP1P0P0, pP2P0P0, oom, rm));
        V3D_Geometry result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_LineSegmentsCollinear) expResult).equals((V3D_LineSegmentsCollinear) result, oom, rm));
    }

    /**
     * Test of getIntersection method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetIntersection_V3D_LineSegment_int() {
        System.out.println("getIntersection");
        int oom = -1;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_LineSegment l = new V3D_LineSegment(pN1P0P0, pP0P0P0, oom, rm);
        V3D_LineSegmentsCollinear instance = new V3D_LineSegmentsCollinear(
                new V3D_LineSegment(pN1P0P0, pP0P0P0, oom, rm),
                new V3D_LineSegment(pP1P0P0, pP2P0P0, oom, rm));
        V3D_Geometry expResult = new V3D_LineSegment(pN1P0P0, pP0P0P0, oom, rm);
        V3D_Geometry result = instance.getIntersection(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equals((V3D_LineSegment) result, oom, rm));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testIsIntersectedBy_V3D_Plane_int() {
        // No test.
    }

    /**
     * Test of isIntersectedBy method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testIsIntersectedBy_V3D_Triangle_int() {
        // No test.
    }

    /**
     * Test of isIntersectedBy method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testIsIntersectedBy_V3D_Tetrahedron_int() {
        // No test yet.
    }

    /**
     * Test of simplify method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testSimplify() {
        System.out.println("simplify");
        int oom = -1;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_LineSegmentsCollinear instance = new V3D_LineSegmentsCollinear(
                new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm),
                new V3D_LineSegment(pP1P0P0, pP2P0P0, oom, rm));
        V3D_Geometry expResult = new V3D_LineSegment(pP0P0P0, pP2P0P0, oom, rm);
        V3D_Geometry result = instance.simplify(oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
        // Test 2
        instance = new V3D_LineSegmentsCollinear(
                new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm),
                new V3D_LineSegment(pP1P0P0, pP2P0P0, oom, rm),
                new V3D_LineSegment(pP0P0P0, pP2P0P0, oom, rm));
        expResult = new V3D_LineSegment(pP0P0P0, pP2P0P0, oom, rm);
        result = instance.simplify(oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
    }

    /**
     * Test of simplify0 method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testSimplify0() {
        // No test.
    }

    /**
     * Test of getIntersection method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetIntersection_V3D_Plane_int() {
        System.out.println("getIntersection");
        // No test yet.
//        V3D_Plane p = null;
//        int oom = 0;
//        V3D_LineSegmentsCollinear instance = null;
//        V3D_Geometry expResult = null;
//        V3D_Geometry result = instance.getIntersection(p, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getIntersection method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetIntersection_V3D_Triangle_int() {
        System.out.println("getIntersection");
        // No test yet.
//        V3D_Triangle t = null;
//        int oom = 0;
//        V3D_LineSegmentsCollinear instance = null;
//        V3D_Geometry expResult = null;
//        V3D_Geometry result = instance.getIntersection(t, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getIntersection method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetIntersection_V3D_Tetrahedron_int() {
        System.out.println("getIntersection");
        // No test yet.
//        V3D_Tetrahedron t = null;
//        int oom = 0;
//        V3D_LineSegmentsCollinear instance = null;
//        V3D_Geometry expResult = null;
//        V3D_Geometry result = instance.getIntersection(t, oom);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getDistance method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetDistance_V3D_Plane_int() {
        // No test.
    }

    /**
     * Test of getDistanceSquared method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetDistanceSquared_V3D_Plane_int() {
        // No test.
    }

    /**
     * Test of getDistance method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetDistance_V3D_Triangle_int() {
        // No test.
    }

    /**
     * Test of getDistanceSquared method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetDistanceSquared_V3D_Triangle_int() {
        // No test.
    }

    /**
     * Test of getDistance method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetDistance_V3D_Tetrahedron_int() {
        // No test.
    }

    /**
     * Test of getDistanceSquared method, of class V3D_LineSegmentsCollinear.
     */
    @Test
    public void testGetDistanceSquared_V3D_Tetrahedron_int() {
        // No test.
    }

}
