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
package uk.ac.leeds.ccg.v3d.geometry.d.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Geometry_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Line_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_LineSegment_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Plane_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Point_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Ray_d;

/**
 * Test class for V3D_Ray_d.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Ray_dTest extends V3D_Test_d {

    public V3D_Ray_dTest() {
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
     * Test of equals method, of class V3D_Ray_d.
     */
    @Test
    public void testEquals_V3D_Ray_d_int() {
        System.out.println("equals");
        double epsilon = 1d / 10000000d;
        V3D_Ray_d r = new V3D_Ray_d(pP0P0P0, pP1P1P1);
        V3D_Ray_d instance = new V3D_Ray_d(pP0P0P0, pP1P1P1);
        assertTrue(instance.equals(r, epsilon));
        // Test 2
        instance = new V3D_Ray_d(pP0P0P0, pP2P2P2);
        assertTrue(instance.equals(r, epsilon));
        // Test 2
        instance = new V3D_Ray_d(pP1P1P1, pP2P2P2);
        assertFalse(instance.equals(r, epsilon));
    }

    /**
     * Test of translate method, of class V3D_Ray_d.
     */
    @Test
    public void testApply() {
        // No test.
    }

    /**
     * Test of intersects method, of class V3D_Ray_d.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point_d_int() {
        System.out.println("isIntersectedBy");
        double epsilon = 1d / 10000000d;
        V3D_Point_d pt = pP0P0P0;
        V3D_Ray_d instance = new V3D_Ray_d(pN1N1N1, pP1P1P1);
        assertTrue(instance.intersects(pt, epsilon));
        // Test 2
        pt = pP1P1P1;
        instance = new V3D_Ray_d(pN1N1N1, pP1P1P1);
        assertTrue(instance.intersects(pt, epsilon));
        // Test 3
        pt = pN2N2N2;
        instance = new V3D_Ray_d(pN1N1N1, pP1P1P1);
        assertFalse(instance.intersects(pt, epsilon));
        // Test 4
        pt = pP1P0P0;
        instance = new V3D_Ray_d(pP0P0P0, pP1P0P0);
        assertTrue(instance.intersects(pt, epsilon));
        // Test 5
        pt = pP2P0P0;
        assertTrue(instance.intersects(pt, epsilon));
        // Test 6
        pt = pN2P0P0;
        assertFalse(instance.intersects(pt, epsilon));
    }

    /**
     * Test of getIntersect method, of class V3D_Ray_d.
     */
    @Test
    public void testGetIntersection_V3D_Line_int() {
        System.out.println("getIntersect");
        double epsilon = 1d / 10000000d;
        V3D_Line_d l = new V3D_Line_d(pP0P0P0, pP1P0P0);
        V3D_Ray_d instance = new V3D_Ray_d(pP0P0P0, pP1P0P0);
        V3D_Geometry_d expResult = new V3D_Ray_d(pP0P0P0, pP1P0P0);
        V3D_Geometry_d result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Ray_d) expResult).equals((V3D_Ray_d) result, epsilon));
        // Test 2
        instance = new V3D_Ray_d(pP0P0P0, pP1P1P1);
        result = instance.getIntersect(l, epsilon);
        expResult = pP0P0P0;
        assertTrue(((V3D_Point_d) expResult).equals( 
                (V3D_Point_d) result, epsilon));
        // Test 3
        instance = new V3D_Ray_d(pN1N1N1, pN1N1P0);
        result = instance.getIntersect(l, epsilon);
        assertNull(result);
        // Test 4
        l = new V3D_Line_d(pN1N1P0, pP1P1P0);
        instance = new V3D_Ray_d(pN1N1N1, pP1P1P1);
        result = instance.getIntersect(l, epsilon);
        expResult = pP0P0P0;
        assertTrue(((V3D_Point_d) expResult).equals( 
                (V3D_Point_d) result, epsilon));
        // Test 5
        l = new V3D_Line_d(pP0P0P0, pP1P0P0);
        instance = new V3D_Ray_d(pN1P0P0, pP1P0P0);
        result = instance.getIntersect(l, epsilon);
        expResult = new V3D_Ray_d(pN1P0P0, pP1P0P0);
        assertTrue(((V3D_Ray_d) expResult).equals((V3D_Ray_d) result, epsilon));
    }

    /**
     * Test of getIntersect method, of class V3D_Ray_d.
     */
    @Test
    public void testGetIntersection_3args_2() {
        System.out.println("getIntersect");
        double epsilon = 1d / 10000000d;
        V3D_LineSegment_d l;
        V3D_Ray_d instance;
        V3D_Geometry_d expResult;
        V3D_Geometry_d result;
        // Test 1
        l = new V3D_LineSegment_d(env, P0P0P0, P0P0P0, P1P0P0);
        instance = new V3D_Ray_d(env, P0P0P0, P1P0P0, P2P0P0);
        result = instance.getIntersect(l, epsilon);
        expResult = pP1P0P0;
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 2
        instance = new V3D_Ray_d(pP0P0P0, pP1P1P1);
        result = instance.getIntersect(l, epsilon);
        expResult = pP0P0P0;
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 3
        instance = new V3D_Ray_d(pN1N1N1, pN1N1P0);
        result = instance.getIntersect(l, epsilon);
        assertNull(result);
        // Test 4
        l = new V3D_LineSegment_d(pN1N1P0, pP1P1P0);
        instance = new V3D_Ray_d(pN1N1N1, pP1P1P1);
        result = instance.getIntersect(l, epsilon);
        expResult = pP0P0P0;
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 5
        l = new V3D_LineSegment_d(pP0P0P0, pP1P0P0);
        instance = new V3D_Ray_d(pN1P0P0, pP1P0P0);
        result = instance.getIntersect(l, epsilon);
        expResult = new V3D_LineSegment_d(pP0P0P0, pP1P0P0);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
    }

    /**
     * Test of getIntersect method, of class V3D_Ray_d.
     */
    @Test
    public void testGetIntersection_V3D_Plane_d_int() {
        System.out.println("getIntersect");
        double epsilon = 1d / 10000000d;
        V3D_Ray_d instance;
        V3D_Plane_d p;
        V3D_Geometry_d expResult;
        V3D_Geometry_d result;
        // Test 1-3 axis with orthoganol plane through origin.
        // Test 1
        instance = new V3D_Ray_d(pN2P0P0, pN1P0P0);
        p = V3D_Plane_d.X0;
//        p = new V3D_Plane_d(new V3D_Environment(),
//            V3D_Vector.ZERO, V3D_Vector.ZERO, V3D_Vector.J, V3D_Vector.K);
        //expResult = new V3D_Point_d(P0P0P0);
        expResult = pP0P0P0;
        result = instance.getIntersect(p, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 2
        instance = new V3D_Ray_d(pN1P0P0, pN2P0P0);
        p = V3D_Plane_d.X0;
        assertNull(instance.getIntersect(p, epsilon));
        
        // Test 1
        instance = new V3D_Ray_d(pP0P0P0, pP1P0P0);
        p = V3D_Plane_d.X0;
        expResult = pP0P0P0;
        result = instance.getIntersect(p, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals( 
                (V3D_Point_d) result, epsilon));
        // Test 2
        p = V3D_Plane_d.Y0;
        expResult = new V3D_Ray_d(pP0P0P0, pP1P0P0);
        result = instance.getIntersect(p, epsilon);
        assertTrue(((V3D_Ray_d) expResult).equals((V3D_Ray_d) result, epsilon));
        // Test 3
        p = V3D_Plane_d.Z0;
        expResult = new V3D_Ray_d(pP0P0P0, pP1P0P0);
        result = instance.getIntersect(p, epsilon);
        assertTrue(((V3D_Ray_d) expResult).equals((V3D_Ray_d) result, epsilon));
        // Test 4
        instance = new V3D_Ray_d(pN2P0P0, pN1P0P0);
        p = V3D_Plane_d.X0;
        expResult = pP0P0P0;
        result = instance.getIntersect(p, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals( 
                (V3D_Point_d) result, epsilon));
        // Test 5
        instance = new V3D_Ray_d(pN1P0P0, pN2P0P0);
        p = V3D_Plane_d.X0;
        result = instance.getIntersect(p, epsilon);
        assertNull(result);
    }

    /**
     * Test of getIntersect method, of class V3D_Ray_d.
     */
    @Test
    public void testGetIntersection_V3D_Ray_d_int() {
        System.out.println("getIntersect");
        double epsilon = 1d / 10000000d;
        V3D_Ray_d r;
        V3D_Ray_d instance;
        V3D_Geometry_d expResult;
        V3D_Geometry_d result;
        // Test 1: Collinear Pointing the same way
        r = new V3D_Ray_d(pP0P0P0, pP1P0P0);
        instance = new V3D_Ray_d(pP1P0P0, pP2P0P0);
        result = instance.getIntersect(r, epsilon);
        expResult = new V3D_Ray_d(pP1P0P0, pP2P0P0);
        assertTrue(((V3D_Ray_d) expResult).equals((V3D_Ray_d) result, epsilon));
        // Test 2: Collinear Pointing the same way 
        r = new V3D_Ray_d(pN2P0P0, pN1P0P0);
        instance = new V3D_Ray_d(pP1P0P0, pP2P0P0);
        result = instance.getIntersect(r, epsilon);
        expResult = new V3D_Ray_d(pP1P0P0, pP2P0P0);
        assertTrue(((V3D_Ray_d) expResult).equals((V3D_Ray_d) result, epsilon));
        /**
         * The rays may point along the same line. If they point in the same
         * direction, then they intersect and the start of the ray is the start
         * point of the ray that intersects both rays. If they point in opposite
         * directions, then they do not intersect unless the points they start
         * at intersect with the other ray and in this instance, the
         * intersection is the line segment between them.
         */
        // Test 3: Collinear pointing opposite ways overlapping in a line segment.
        r = new V3D_Ray_d(pP0P0P0, pP1P0P0);
        instance = new V3D_Ray_d(pP1P0P0, pP0P0P0);
        expResult = new V3D_LineSegment_d(pP0P0P0, pP1P0P0);
        result = instance.getIntersect(r, epsilon);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(epsilon, 
                (V3D_LineSegment_d) result));
        // Test 4: Collinear pointing opposite ways overlapping at a point.
        r = new V3D_Ray_d(pP0P0P0, pP1P0P0);
        instance = new V3D_Ray_d(pP0P0P0, pN1P0P0);
        expResult = pP0P0P0;
        result = instance.getIntersect(r, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals( 
                (V3D_Point_d) result, epsilon));
        // Test 4: Collinear pointing opposite ways not overlapping.
        r = new V3D_Ray_d(pP1P0P0, pP2P0P0);
        instance = new V3D_Ray_d(pP0P0P0, pN1P0P0);
        result = instance.getIntersect(r, epsilon);
        assertNull(result);
        // Test 5: Intersecting at a point.
        r = new V3D_Ray_d(pN2P0P0, pN1P0P0);
        instance = new V3D_Ray_d(pP1P0P0, pP1P1P1);
        result = instance.getIntersect(r, epsilon);
        expResult = pP1P0P0;
        assertTrue(((V3D_Point_d) expResult).equals( 
                (V3D_Point_d) result, epsilon));
        // Test 6: Not intersecting.
        r = new V3D_Ray_d(pP1P0P0, pP2P0P0);
        instance = new V3D_Ray_d(pP0P0P0, pP1P1P1);
        result = instance.getIntersect(r, epsilon);
        assertNull(result);
    }

    /**
     * Test of getIntersect method, of class V3D_Ray_d.
     */
    @Test
    public void testGetIntersection_V3D_LineSegment_d_int() {
        System.out.println("getIntersect");
        double epsilon = 1d / 10000000d;
        V3D_LineSegment_d l;
        V3D_Ray_d instance;
        V3D_Geometry_d expResult;
        V3D_Geometry_d result;
        // Test 1
        l = new V3D_LineSegment_d(pP0P0P0, pP1P0P0);
        instance = new V3D_Ray_d(pP0P0P0, pP1P0P0);
        expResult = new V3D_LineSegment_d(pP0P0P0, pP1P0P0);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
        // Test 2
        l = new V3D_LineSegment_d(pP0P0P0, pP1P0P0);
        instance = new V3D_Ray_d(pP0P0P0, pN1P0P0);
        expResult = pP0P0P0;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 3
        l = new V3D_LineSegment_d(pP0P0P0, pP2P0P0);
        instance = new V3D_Ray_d(pP1P0P0, pP2P0P0);
        expResult = new V3D_LineSegment_d(pP1P0P0, pP2P0P0);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
    }
}
