/*
 * Copyright 2021 Centre for Computational Geography.
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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Geometry;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Line;
import uk.ac.leeds.ccg.v3d.geometry.V3D_LineSegment;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Plane;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Point;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Ray;
import static uk.ac.leeds.ccg.v3d.geometry.test.V3D_Test.oom;
import static uk.ac.leeds.ccg.v3d.geometry.test.V3D_Test.rm;

/**
 * Test class for V3D_Ray.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_RayTest extends V3D_Test {

    public V3D_RayTest() {
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
     * Test of equals method, of class V3D_Ray.
     */
    @Test
    public void testEquals_V3D_Ray_int() {
        System.out.println("equals");
        V3D_Ray r = new V3D_Ray(pP0P0P0, pP1P1P1, oom, rm);
        V3D_Ray instance = new V3D_Ray(pP0P0P0, pP1P1P1, oom, rm);
        assertTrue(instance.equals(r, oom, rm));
        // Test 2
        instance = new V3D_Ray(pP0P0P0, pP2P2P2, oom, rm);
        assertTrue(instance.equals(r, oom, rm));
        // Test 2
        instance = new V3D_Ray(pP1P1P1, pP2P2P2, oom, rm);
        assertFalse(instance.equals(r, oom, rm));
    }

    /**
     * Test of translate method, of class V3D_Ray.
     */
    @Test
    public void testApply() {
        // No test.
    }

    /**
     * Test of intersects method, of class V3D_Ray.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point_int() {
        System.out.println("intersects");
        V3D_Point pt = pP0P0P0;
        V3D_Ray instance = new V3D_Ray(pN1N1N1, pP1P1P1, oom, rm);
        assertTrue(instance.intersects(pt, oom, rm));
        // Test 2
        pt = pP1P1P1;
        instance = new V3D_Ray(pN1N1N1, pP1P1P1, oom, rm);
        assertTrue(instance.intersects(pt, oom, rm));
        // Test 3
        pt = pN2N2N2;
        instance = new V3D_Ray(pN1N1N1, pP1P1P1, oom, rm);
        assertFalse(instance.intersects(pt, oom, rm));
        // Test 4
        pt = pP1P0P0;
        instance = new V3D_Ray(pP0P0P0, pP1P0P0, oom, rm);
        assertTrue(instance.intersects(pt, oom, rm));
        // Test 5
        pt = pP2P0P0;
        assertTrue(instance.intersects(pt, oom, rm));
        // Test 6
        pt = pN2P0P0;
        assertFalse(instance.intersects(pt, oom, rm));
    }

//    /**
//     * Test of intersects method, of class V3D_Ray.
//     */
//    @Test
//    public void testIsIntersectedBy_V3D_Ray_boolean() {
//        System.out.println("intersects");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_Ray r = new V3D_Ray(pP0P0P0, pP1P0P0, oom, rm);
//        V3D_Ray instance = new V3D_Ray(pP0P0P0, pP1P0P0, oom, rm);
//        assertTrue(instance.intersects(r, oom, rm));
//        // Test 2
//        instance = new V3D_Ray(pP0P0P0, pP1P1P1, oom, rm);
//        assertTrue(instance.intersects(r, oom, rm));
//        // Test 3
//        instance = new V3D_Ray(pN1N1N1, pN1N1P0, oom, rm);
//        assertFalse(instance.intersects(r, oom, rm));
//        // Test 4
//        r = new V3D_Ray(pN1N1P0, pP1P1P0, oom, rm);
//        instance = new V3D_Ray(pN1N1N1, pP1P1P1, oom, rm);
//        instance.getIntersect(r, oom, rm);
//        assertTrue(instance.intersects(r, oom, rm));
//    }

//    /**
//     * Test of intersects method, of class V3D_Ray.
//     */
//    @Test
//    public void testIsIntersectedBy_V3D_LineSegment_boolean() {
//        System.out.println("intersects");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_LineSegment l = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
//        V3D_Ray instance = new V3D_Ray(pP0P0P0, pP1P0P0, oom, rm);
//        assertTrue(instance.intersects(l, oom, rm));
//        // Test 2
//        instance = new V3D_Ray(pP0P0P0, pP1P1P1, oom, rm);
//        assertTrue(instance.intersects(l, oom, rm));
//        // Test 3
//        instance = new V3D_Ray(pN1N1N1, pN1N1P0, oom, rm);
//        assertFalse(instance.intersects(l, oom, rm));
//        // Test 4
//        l = new V3D_LineSegment(pN1N1P0, pP1P1P0, oom, rm);
//        instance = new V3D_Ray(pN1N1N1, pP1P1P1, oom, rm);
//        instance.getIntersect(l, oom, rm);
//        assertTrue(instance.intersects(l, oom, rm));
//    }

//    /**
//     * Test of intersects method, of class V3D_Ray.
//     */
//    @Test
//    public void testIsIntersectedBy_V3D_Line_int() {
//        System.out.println("intersects");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_Line l = new V3D_Line(pP0P0P0, pP1P0P0, oom, rm);
//        V3D_Ray instance = new V3D_Ray(pP0P0P0, pP1P0P0, oom, rm);
//        assertTrue(instance.intersects(l, oom, rm));
//        // Test 2
//        instance = new V3D_Ray(pP0P0P0, pP1P1P1, oom, rm);
//        assertTrue(instance.intersects(l, oom, rm));
//        // Test 3
//        instance = new V3D_Ray(pN1N1N1, pN1N1P0, oom, rm);
//        assertFalse(instance.intersects(l, oom, rm));
//        // Test 4
//        l = new V3D_Line(pN1N1P0, pP1P1P0, oom, rm);
//        instance = new V3D_Ray(pN1N1N1, pP1P1P1, oom, rm);
//        assertTrue(instance.intersects(l, oom, rm));
//        // Test 5
//        l = new V3D_Line(P0P0P0, P0P0P0, P1P0P0, oom, rm);
//        instance = new V3D_Ray(P0P0P0, P0P0P0, P1P0P0, oom, rm);
//        assertTrue(instance.intersects(l, oom, rm));
//        // Test 6
//        l = new V3D_Line(P0P0P0, P0P0P0, P1P0P0, oom, rm);
//        instance = new V3D_Ray(P0P0P0, P0P0P0, P0P0P1, oom, rm);
//        assertTrue(instance.intersects(l, oom, rm));
//        // Test 7
//        l = new V3D_Line(P0P0P0, P0P0P0, P1P0P0, oom, rm);
//        instance = new V3D_Ray(P0P0P0, P0P0N1, P0P0P1, oom, rm);
//        assertTrue(instance.intersects(l, oom, rm));
//        // Test 8
//        l = new V3D_Line(P0P0P0, P0P0P0, P1P0P0, oom, rm);
//        instance = new V3D_Ray(P0P0P0, P0P0P1, P0P0P2, oom, rm);
//        assertFalse(instance.intersects(l, oom, rm));
//    }

    /**
     * Test of getIntersect method, of class V3D_Ray.
     */
    @Test
    public void testGetIntersection_V3D_Line_int() {
        System.out.println("getIntersect");
        V3D_Line l = new V3D_Line(pP0P0P0, pP1P0P0, oom, rm);
        V3D_Ray instance = new V3D_Ray(pP0P0P0, pP1P0P0, oom, rm);
        V3D_Geometry expResult = new V3D_Ray(pP0P0P0, pP1P0P0, oom, rm);
        V3D_Geometry result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Ray) expResult).equals((V3D_Ray) result, oom, rm));
        // Test 2
        instance = new V3D_Ray(pP0P0P0, pP1P1P1, oom, rm);
        result = instance.getIntersect(l, oom, rm);
        expResult = pP0P0P0;
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 3
        instance = new V3D_Ray(pN1N1N1, pN1N1P0, oom, rm);
        result = instance.getIntersect(l, oom, rm);
        assertNull(result);
        // Test 4
        l = new V3D_Line(pN1N1P0, pP1P1P0, oom, rm);
        instance = new V3D_Ray(pN1N1N1, pP1P1P1, oom, rm);
        result = instance.getIntersect(l, oom, rm);
        expResult = pP0P0P0;
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 5
        l = new V3D_Line(pP0P0P0, pP1P0P0, oom, rm);
        instance = new V3D_Ray(pN1P0P0, pP1P0P0, oom, rm);
        result = instance.getIntersect(l, oom, rm);
        expResult = new V3D_Ray(pN1P0P0, pP1P0P0, oom, rm);
        assertTrue(((V3D_Ray) expResult).equals((V3D_Ray) result, oom, rm));
    }

    /**
     * Test of getIntersect method, of class V3D_Ray.
     */
    @Test
    public void testGetIntersection_3args_2() {
        System.out.println("getIntersect");
        V3D_LineSegment l;
        V3D_Ray instance;
        V3D_Geometry expResult;
        V3D_Geometry result;
        // Test 1
        l = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        instance = new V3D_Ray(env, P0P0P0, P1P0P0, P2P0P0);
        result = instance.getIntersect(l, oom, rm);
        expResult = pP1P0P0;
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 2
        instance = new V3D_Ray(pP0P0P0, pP1P1P1, oom, rm);
        result = instance.getIntersect(l, oom, rm);
        expResult = pP0P0P0;
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 3
        instance = new V3D_Ray(pN1N1N1, pN1N1P0, oom, rm);
        result = instance.getIntersect(l, oom, rm);
        assertNull(result);
        // Test 4
        l = new V3D_LineSegment(pN1N1P0, pP1P1P0, oom, rm);
        instance = new V3D_Ray(pN1N1N1, pP1P1P1, oom, rm);
        result = instance.getIntersect(l, oom, rm);
        expResult = pP0P0P0;
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 5
        l = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        instance = new V3D_Ray(pN1P0P0, pP1P0P0, oom, rm);
        result = instance.getIntersect(l, oom, rm);
        expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection(
                (V3D_LineSegment) result, oom, rm));
    }

    /**
     * Test of getIntersect method, of class V3D_Ray.
     */
    @Test
    public void testGetIntersection_V3D_Plane_int() {
        System.out.println("getIntersect");
        V3D_Ray instance;
        V3D_Plane p;
        V3D_Geometry expResult;
        V3D_Geometry result;
        // Test 1-3 axis with orthoganol plane through origin.
        // Test 1
        instance = new V3D_Ray(pN2P0P0, pN1P0P0, oom, rm);
        p = V3D_Plane.X0;
//        pv = new V3D_Plane(new V3D_Environment(),
//            V3D_Vector.ZERO, V3D_Vector.ZERO, V3D_Vector.J, V3D_Vector.K);
        //expResult = new V3D_Point(P0P0P0);
        expResult = pP0P0P0;
        result = instance.getIntersect(p, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 2
        instance = new V3D_Ray(env, P0P0P0, N1P0P0, N2P0P0);
        p = V3D_Plane.X0;
        assertNull(instance.getIntersect(p, oom, rm));
        
        // Test 1
        instance = new V3D_Ray(pP0P0P0, pP1P0P0, oom, rm);
        p = V3D_Plane.X0;
        expResult = pP0P0P0;
        result = instance.getIntersect(p, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 2
        p = V3D_Plane.Y0;
        expResult = new V3D_Ray(pP0P0P0, pP1P0P0, oom, rm);
        result = instance.getIntersect(p, oom, rm);
        assertTrue(((V3D_Ray) expResult).equals((V3D_Ray) result, oom, rm));
        // Test 3
        p = V3D_Plane.Z0;
        expResult = new V3D_Ray(pP0P0P0, pP1P0P0, oom, rm);
        result = instance.getIntersect(p, oom, rm);
        assertTrue(((V3D_Ray) expResult).equals((V3D_Ray) result, oom, rm));
        // Test 4
        instance = new V3D_Ray(pN2P0P0, pN1P0P0, oom, rm);
        p = V3D_Plane.X0;
        expResult = pP0P0P0;
        result = instance.getIntersect(p, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 5
        instance = new V3D_Ray(pN1P0P0, pN2P0P0, oom, rm);
        p = V3D_Plane.X0;
        result = instance.getIntersect(p, oom, rm);
        assertNull(result);
    }

    /**
     * Test of getIntersect method, of class V3D_Ray.
     */
    @Test
    public void testGetIntersection_V3D_Ray_int() {
        System.out.println("getIntersect");
        V3D_Ray r;
        V3D_Ray instance;
        V3D_Geometry expResult;
        V3D_Geometry result;
        // Test 1: Collinear Pointing the same way
        r = new V3D_Ray(pP0P0P0, pP1P0P0, oom, rm);
        instance = new V3D_Ray(pP1P0P0, pP2P0P0, oom, rm);
        result = instance.getIntersect(r, oom, rm);
        expResult = new V3D_Ray(pP1P0P0, pP2P0P0, oom, rm);
        assertTrue(((V3D_Ray) expResult).equals((V3D_Ray) result, oom, rm));
        // Test 2: Collinear Pointing the same way 
        r = new V3D_Ray(pN2P0P0, pN1P0P0, oom, rm);
        instance = new V3D_Ray(pP1P0P0, pP2P0P0, oom, rm);
        result = instance.getIntersect(r, oom, rm);
        expResult = new V3D_Ray(pP1P0P0, pP2P0P0, oom, rm);
        assertTrue(((V3D_Ray) expResult).equals((V3D_Ray) result, oom, rm));
        /**
         * The rays may point along the same line. If they point in the same
         * direction, then they intersect and the start of the ray is the start
         * point of the ray that intersects both rays. If they point in opposite
         * directions, then they do not intersect unless the points they start
         * at intersect with the other ray and in this instance, the
         * intersection is the line segment between them.
         */
        // Test 3: Collinear pointing opposite ways overlapping in a line segment.
        r = new V3D_Ray(pP0P0P0, pP1P0P0, oom, rm);
        instance = new V3D_Ray(pP1P0P0, pP0P0P0, oom, rm);
        expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        result = instance.getIntersect(r, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equals((V3D_LineSegment) result, oom, rm));
        // Test 4: Collinear pointing opposite ways overlapping at a point.
        r = new V3D_Ray(pP0P0P0, pP1P0P0, oom, rm);
        instance = new V3D_Ray(pP0P0P0, pN1P0P0, oom, rm);
        expResult = pP0P0P0;
        result = instance.getIntersect(r, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 4: Collinear pointing opposite ways not overlapping.
        r = new V3D_Ray(pP1P0P0, pP2P0P0, oom, rm);
        instance = new V3D_Ray(pP0P0P0, pN1P0P0, oom, rm);
        result = instance.getIntersect(r, oom, rm);
        assertNull(result);
        // Test 5: Intersecting at a point.
        r = new V3D_Ray(pN2P0P0, pN1P0P0, oom, rm);
        instance = new V3D_Ray(pP1P0P0, pP1P1P1, oom, rm);
        result = instance.getIntersect(r, oom, rm);
        expResult = pP1P0P0;
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 6: Not intersecting.
        r = new V3D_Ray(pP1P0P0, pP2P0P0, oom, rm);
        instance = new V3D_Ray(pP0P0P0, pP1P1P1, oom, rm);
        result = instance.getIntersect(r, oom, rm);
        assertNull(result);
    }

    /**
     * Test of getIntersect method, of class V3D_Ray.
     */
    @Test
    public void testGetIntersection_V3D_LineSegment_int() {
        System.out.println("getIntersect");
        V3D_LineSegment l;
        V3D_Ray instance;
        V3D_Geometry expResult;
        V3D_Geometry result;
        // Test 1
        l = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        instance = new V3D_Ray(pP0P0P0, pP1P0P0, oom, rm);
        expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
        // Test 2
        l = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        instance = new V3D_Ray(pP0P0P0, pN1P0P0, oom, rm);
        expResult = pP0P0P0;
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 3
        l = new V3D_LineSegment(pP0P0P0, pP2P0P0, oom, rm);
        instance = new V3D_Ray(pP1P0P0, pP2P0P0, oom, rm);
        expResult = new V3D_LineSegment(pP1P0P0, pP2P0P0, oom, rm);
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_LineSegment) expResult).equalsIgnoreDirection((V3D_LineSegment) result, oom, rm));
    }
}
