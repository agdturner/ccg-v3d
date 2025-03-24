/*
 * Copyright 2025 Centre for Computational Geography.
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

import java.util.HashMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_ConvexArea_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_FiniteGeometry_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Plane_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Point_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Ray_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Vector_d;

/**
 *
 * @author Andy Turner
 */
public class V3D_ConvexArea_dTest extends V3D_Test_d {
    
    public V3D_ConvexArea_dTest() {
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

//    /**
//     * Test of getPoints method, of class V3D_ConvexArea_d.
//     */
//    @Test
//    public void testGetPoints() {
//        System.out.println("getPoints");
//        V3D_ConvexArea_d instance = null;
//        HashMap<Integer, V3D_Point_d> expResult = null;
//        HashMap<Integer, V3D_Point_d> result = instance.getPoints();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getPointsArray method, of class V3D_ConvexArea_d.
//     */
//    @Test
//    public void testGetPointsArray() {
//        System.out.println("getPointsArray");
//        V3D_ConvexArea_d instance = null;
//        V3D_Point_d[] expResult = null;
//        V3D_Point_d[] result = instance.getPointsArray();
//        assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getEdges method, of class V3D_ConvexArea_d.
//     */
//    @Test
//    public void testGetEdges() {
//        System.out.println("getEdges");
//        V3D_ConvexArea_d instance = null;
//        HashMap<Integer, V3D_LineSegment_d> expResult = null;
//        HashMap<Integer, V3D_LineSegment_d> result = instance.getEdges();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of toString method, of class V3D_ConvexArea.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        double epsilon = 0.00000001d;
        V3D_ConvexArea_d instance = new V3D_ConvexArea_d(epsilon, V3D_Vector_d.K,
                pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        String expResult = """
                           uk.ac.leeds.ccg.v3d.geometry.d.V3D_ConvexArea_d(
                            points (
                             (0, V3D_Point_d(offset=V3D_Vector_d(dx=0.0, dy=0.0, dz=0.0), rel=V3D_Vector_d(dx=0.0, dy=0.0, dz=0.0))),
                             (1, V3D_Point_d(offset=V3D_Vector_d(dx=0.0, dy=0.0, dz=0.0), rel=V3D_Vector_d(dx=1.0, dy=0.0, dz=0.0))),
                             (2, V3D_Point_d(offset=V3D_Vector_d(dx=0.0, dy=0.0, dz=0.0), rel=V3D_Vector_d(dx=1.0, dy=1.0, dz=0.0))),
                             (3, V3D_Point_d(offset=V3D_Vector_d(dx=0.0, dy=0.0, dz=0.0), rel=V3D_Vector_d(dx=0.0, dy=1.0, dz=0.0)))
                            )
                           )""";
        String result = instance.toString();
        //System.out.println(result);
        assertTrue(expResult.equalsIgnoreCase(result));
    }

//    /**
//     * Test of toStringSimple method, of class V3D_ConvexArea_d.
//     */
//    @Test
//    public void testToStringSimple() {
//        System.out.println("toStringSimple");
//        V3D_ConvexArea_d instance = null;
//        String expResult = "";
//        String result = instance.toStringSimple();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of equals method, of class V3D_ConvexArea_d.
//     */
//    @Test
//    public void testEquals() {
//        System.out.println("equals");
//        V3D_ConvexArea_d c = null;
//        double epsilon = 0.0;
//        V3D_ConvexArea_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.equals(c, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getAABB method, of class V3D_ConvexArea_d.
//     */
//    @Test
//    public void testGetAABB() {
//        System.out.println("getAABB");
//        V3D_ConvexArea_d instance = null;
//        V3D_AABB_d expResult = null;
//        V3D_AABB_d result = instance.getAABB();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of simplify method, of class V3D_ConvexArea_d.
//     */
//    @Test
//    public void testSimplify() {
//        System.out.println("simplify");
//        double epsilon = 0.0;
//        V3D_ConvexArea_d instance = null;
//        V3D_FiniteGeometry_d expResult = null;
//        V3D_FiniteGeometry_d result = instance.simplify(epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects method, of class V3D_ConvexArea_d.
//     */
//    @Test
//    public void testIntersects_V3D_Point_d_double() {
//        System.out.println("intersects");
//        V3D_Point_d pt = null;
//        double epsilon = 0.0;
//        V3D_ConvexArea_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects(pt, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of isAligned method, of class V3D_ConvexArea_d.
//     */
//    @Test
//    public void testIsAligned() {
//        System.out.println("isAligned");
//        V3D_Point_d pt = null;
//        double epsilon = 0.0;
//        V3D_ConvexArea_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.isAligned(pt, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects0 method, of class V3D_ConvexArea_d.
//     */
//    @Test
//    public void testIntersects0() {
//        System.out.println("intersects0");
//        V3D_Point_d pt = null;
//        double epsilon = 0.0;
//        V3D_ConvexArea_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects0(pt, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getArea method, of class V3D_ConvexArea_d.
//     */
//    @Test
//    public void testGetArea() {
//        System.out.println("getArea");
//        V3D_ConvexArea_d instance = null;
//        double expResult = 0.0;
//        double result = instance.getArea();
//        assertEquals(expResult, result, 0);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getPerimeter method, of class V3D_ConvexArea_d.
//     */
//    @Test
//    public void testGetPerimeter() {
//        System.out.println("getPerimeter");
//        V3D_ConvexArea_d instance = null;
//        double expResult = 0.0;
//        double result = instance.getPerimeter();
//        assertEquals(expResult, result, 0);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of getIntersect method, of class V3D_ConvexArea_d.
     */
    @Test
    public void testGetIntersectRay() {
        System.out.println("getIntersect");
        V3D_Ray_d r;
        double epsilon = 0.0000001d;
        V3D_ConvexArea_d instance;
        V3D_FiniteGeometry_d result;
        V3D_FiniteGeometry_d expResult;
        V3D_Point_d[] pts;
        
        // Test 1;
        r = new V3D_Ray_d(pP0P0P1, pP0P0P0);
        pts = new V3D_Point_d[8];
        pts[0] = new V3D_Point_d(env, -30d, -30d, 0d);
        pts[1] = new V3D_Point_d(env, -40d, 0d, 0d);
        pts[2] = new V3D_Point_d(env, -30d, 30d, 0d);
        pts[3] = new V3D_Point_d(env, 0d, 40d, 0d);
        pts[4] = new V3D_Point_d(env, 30d, 30d, 0d);
        pts[5] = new V3D_Point_d(env, 40d, 0d, 0d);
        pts[6] = new V3D_Point_d(env, 30d, -30d, 0d);
        pts[7] = new V3D_Point_d(env, 0d, -40d, 0d);
        instance = new V3D_ConvexArea_d(epsilon, V3D_Plane_d.Z0.getN(), pts);
        result = instance.getIntersect(r, epsilon);
        expResult = pP0P0P0;
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 2;
        r = new V3D_Ray_d(new V3D_Point_d(env, 0d, 35d, 1d),
                new V3D_Point_d(env, 0d, 35d, 0d));
        instance = new V3D_ConvexArea_d(epsilon, V3D_Plane_d.Z0.getN(), pts);
        result = instance.getIntersect(r, epsilon);
        expResult = new V3D_Point_d(env, 0d, 35d, 0d);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 2;
        r = new V3D_Ray_d(new V3D_Point_d(env, -30d, 43d, 1d),
                new V3D_Point_d(env, -30d, 43d, 0d));
        instance = new V3D_ConvexArea_d(epsilon, V3D_Plane_d.Z0.getN(), pts);
        result = instance.getIntersect(r, epsilon);
        expResult = null;
        assertNull(result);
    }

//    /**
//     * Test of getIntersect method, of class V3D_ConvexArea_d.
//     */
//    @Test
//    public void testGetIntersect_V3D_Triangle_d_double() {
//        System.out.println("getIntersect");
//        V3D_Triangle_d t = null;
//        double epsilon = 0.0;
//        V3D_ConvexArea_d instance = null;
//        V3D_FiniteGeometry_d expResult = null;
//        V3D_FiniteGeometry_d result = instance.getIntersect(t, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of rotate method, of class V3D_ConvexArea_d.
//     */
//    @Test
//    public void testRotate() {
//        System.out.println("rotate");
//        V3D_Ray_d ray = null;
//        V3D_Vector_d uv = null;
//        double theta = 0.0;
//        double epsilon = 0.0;
//        V3D_ConvexArea_d instance = null;
//        V3D_ConvexArea_d expResult = null;
//        V3D_ConvexArea_d result = instance.rotate(ray, uv, theta, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of rotateN method, of class V3D_ConvexArea_d.
//     */
//    @Test
//    public void testRotateN() {
//        System.out.println("rotateN");
//        V3D_Ray_d ray = null;
//        V3D_Vector_d uv = null;
//        double theta = 0.0;
//        double epsilon = 0.0;
//        V3D_ConvexArea_d instance = null;
//        V3D_ConvexArea_d expResult = null;
//        V3D_ConvexArea_d result = instance.rotateN(ray, uv, theta, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects method, of class V3D_ConvexArea_d.
//     */
//    @Test
//    public void testIntersects_V3D_AABB_d_double() {
//        System.out.println("intersects");
//        V3D_AABB_d aabb = null;
//        double epsilon = 0.0;
//        V3D_ConvexArea_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects(aabb, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects method, of class V3D_ConvexArea_d.
//     */
//    @Test
//    public void testIntersects_V3D_Line_d_double() {
//        System.out.println("intersects");
//        V3D_Line_d l = null;
//        double epsilon = 0.0;
//        V3D_ConvexArea_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects(l, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects method, of class V3D_ConvexArea_d.
//     */
//    @Test
//    public void testIntersects_V3D_LineSegment_d_double() {
//        System.out.println("intersects");
//        V3D_LineSegment_d l = null;
//        double epsilon = 0.0;
//        V3D_ConvexArea_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects(l, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of isTriangle method, of class V3D_ConvexArea_d.
//     */
//    @Test
//    public void testIsTriangle() {
//        System.out.println("isTriangle");
//        V3D_ConvexArea_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.isTriangle();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of isRectangle method, of class V3D_ConvexArea_d.
//     */
//    @Test
//    public void testIsRectangle() {
//        System.out.println("isRectangle");
//        double epsilon = 0.0;
//        V3D_ConvexArea_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.isRectangle(epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of clip method, of class V3D_ConvexArea_d.
//     */
//    @Test
//    public void testClip_3args_1() {
//        System.out.println("clip");
//        V3D_Plane_d pl = null;
//        V3D_Point_d p = null;
//        double epsilon = 0.0;
//        V3D_ConvexArea_d instance = null;
//        V3D_FiniteGeometry_d expResult = null;
//        V3D_FiniteGeometry_d result = instance.clip(pl, p, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of clip method, of class V3D_ConvexArea_d.
//     */
//    @Test
//    public void testClip_3args_2() {
//        System.out.println("clip");
//        V3D_Triangle_d t = null;
//        V3D_Point_d pt = null;
//        double epsilon = 0.0;
//        V3D_ConvexArea_d instance = null;
//        V3D_FiniteGeometry_d expResult = null;
//        V3D_FiniteGeometry_d result = instance.clip(t, pt, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getGeometry method, of class V3D_ConvexArea_d.
//     */
//    @Test
//    public void testGetGeometry() {
//        System.out.println("getGeometry");
//        double epsilon = 0.0;
//        V3D_Point_d[] pts = null;
//        V3D_FiniteGeometry_d expResult = null;
//        V3D_FiniteGeometry_d result = V3D_ConvexArea_d.getGeometry(epsilon, pts);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
