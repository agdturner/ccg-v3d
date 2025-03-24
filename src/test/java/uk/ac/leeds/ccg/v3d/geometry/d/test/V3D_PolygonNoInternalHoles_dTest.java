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
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_PolygonNoInternalHoles_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Ray_d;
import static uk.ac.leeds.ccg.v3d.geometry.d.test.V3D_Test_d.env;
import static uk.ac.leeds.ccg.v3d.geometry.d.test.V3D_Test_d.pP0P0P0;
import static uk.ac.leeds.ccg.v3d.geometry.d.test.V3D_Test_d.pP0P0P1;

/**
 *
 * @author Andy Turner
 */
public class V3D_PolygonNoInternalHoles_dTest extends V3D_Test_d {
    
    public V3D_PolygonNoInternalHoles_dTest() {
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
//     * Test of getPerimeter method, of class V3D_PolygonNoInternalHoles_d.
//     */
//    @Test
//    public void testGetPerimeter() {
//        System.out.println("getPerimeter");
//        V3D_PolygonNoInternalHoles_d instance = null;
//        double expResult = 0.0;
//        double result = instance.getPerimeter();
//        assertEquals(expResult, result, 0);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getArea method, of class V3D_PolygonNoInternalHoles_d.
//     */
//    @Test
//    public void testGetArea() {
//        System.out.println("getArea");
//        V3D_PolygonNoInternalHoles_d instance = null;
//        double expResult = 0.0;
//        double result = instance.getArea();
//        assertEquals(expResult, result, 0);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects method, of class V3D_PolygonNoInternalHoles_d.
//     */
//    @Test
//    public void testIntersects_V3D_AABB_d_double() {
//        System.out.println("intersects");
//        V3D_AABB_d aabb = null;
//        double epsilon = 0.0;
//        V3D_PolygonNoInternalHoles_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects(aabb, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of toString method, of class V3D_PolygonNoInternalHoles_d.
//     */
//    @Test
//    public void testToString() {
//        System.out.println("toString");
//        V3D_PolygonNoInternalHoles_d instance = null;
//        String expResult = "";
//        String result = instance.toString();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getEdges method, of class V3D_PolygonNoInternalHoles_d.
//     */
//    @Test
//    public void testGetEdges() {
//        System.out.println("getEdges");
//        V3D_PolygonNoInternalHoles_d instance = null;
//        HashMap<Integer, V3D_LineSegment_d> expResult = null;
//        HashMap<Integer, V3D_LineSegment_d> result = instance.getEdges();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getExternalHoles method, of class V3D_PolygonNoInternalHoles_d.
//     */
//    @Test
//    public void testGetExternalHoles() {
//        System.out.println("getExternalHoles");
//        V3D_PolygonNoInternalHoles_d instance = null;
//        HashMap<Integer, V3D_PolygonNoInternalHoles_d> expResult = null;
//        HashMap<Integer, V3D_PolygonNoInternalHoles_d> result = instance.getExternalHoles();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getPointsArray method, of class V3D_PolygonNoInternalHoles_d.
//     */
//    @Test
//    public void testGetPointsArray() {
//        System.out.println("getPointsArray");
//        V3D_PolygonNoInternalHoles_d instance = null;
//        V3D_Point_d[] expResult = null;
//        V3D_Point_d[] result = instance.getPointsArray();
//        assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getPoints method, of class V3D_PolygonNoInternalHoles_d.
//     */
//    @Test
//    public void testGetPoints() {
//        System.out.println("getPoints");
//        V3D_PolygonNoInternalHoles_d instance = null;
//        HashMap<Integer, V3D_Point_d> expResult = null;
//        HashMap<Integer, V3D_Point_d> result = instance.getPoints();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getAABB method, of class V3D_PolygonNoInternalHoles_d.
//     */
//    @Test
//    public void testGetAABB() {
//        System.out.println("getAABB");
//        V3D_PolygonNoInternalHoles_d instance = null;
//        V3D_AABB_d expResult = null;
//        V3D_AABB_d result = instance.getAABB();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects method, of class V3D_PolygonNoInternalHoles_d.
//     */
//    @Test
//    public void testIntersects_V3D_Point_d_double() {
//        System.out.println("intersects");
//        V3D_Point_d pt = null;
//        double epsilon = 0.0;
//        V3D_PolygonNoInternalHoles_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects(pt, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects0 method, of class V3D_PolygonNoInternalHoles_d.
//     */
//    @Test
//    public void testIntersects0_V3D_Point_d_double() {
//        System.out.println("intersects0");
//        V3D_Point_d pt = null;
//        double epsilon = 0.0;
//        V3D_PolygonNoInternalHoles_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects0(pt, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects00 method, of class V3D_PolygonNoInternalHoles_d.
//     */
//    @Test
//    public void testIntersects00() {
//        System.out.println("intersects00");
//        V3D_Point_d pt = null;
//        double epsilon = 0.0;
//        V3D_PolygonNoInternalHoles_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects00(pt, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects000 method, of class V3D_PolygonNoInternalHoles_d.
//     */
//    @Test
//    public void testIntersects000() {
//        System.out.println("intersects000");
//        V3D_Point_d pt = null;
//        double epsilon = 0.0;
//        V3D_PolygonNoInternalHoles_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects000(pt, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of contains method, of class V3D_PolygonNoInternalHoles_d.
//     */
//    @Test
//    public void testContains_V3D_Point_d_double() {
//        System.out.println("contains");
//        V3D_Point_d pt = null;
//        double epsilon = 0.0;
//        V3D_PolygonNoInternalHoles_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.contains(pt, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of contains method, of class V3D_PolygonNoInternalHoles_d.
//     */
//    @Test
//    public void testContains_V3D_LineSegment_d_double() {
//        System.out.println("contains");
//        V3D_LineSegment_d ls = null;
//        double epsilon = 0.0;
//        V3D_PolygonNoInternalHoles_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.contains(ls, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of contains method, of class V3D_PolygonNoInternalHoles_d.
//     */
//    @Test
//    public void testContains_V3D_Triangle_d_double() {
//        System.out.println("contains");
//        V3D_Triangle_d t = null;
//        double epsilon = 0.0;
//        V3D_PolygonNoInternalHoles_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.contains(t, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of contains method, of class V3D_PolygonNoInternalHoles_d.
//     */
//    @Test
//    public void testContains_V3D_Rectangle_d_double() {
//        System.out.println("contains");
//        V3D_Rectangle_d r = null;
//        double epsilon = 0.0;
//        V3D_PolygonNoInternalHoles_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.contains(r, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of contains method, of class V3D_PolygonNoInternalHoles_d.
//     */
//    @Test
//    public void testContains_V3D_AABB_d_double() {
//        System.out.println("contains");
//        V3D_AABB_d aabb = null;
//        double epsilon = 0.0;
//        V3D_PolygonNoInternalHoles_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.contains(aabb, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of contains method, of class V3D_PolygonNoInternalHoles_d.
//     */
//    @Test
//    public void testContains_V3D_ConvexArea_d_double() {
//        System.out.println("contains");
//        V3D_ConvexArea_d ch = null;
//        double epsilon = 0.0;
//        V3D_PolygonNoInternalHoles_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.contains(ch, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of contains method, of class V3D_PolygonNoInternalHoles_d.
//     */
//    @Test
//    public void testContains_V3D_PolygonNoInternalHoles_d_double() {
//        System.out.println("contains");
//        V3D_PolygonNoInternalHoles_d p = null;
//        double epsilon = 0.0;
//        V3D_PolygonNoInternalHoles_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.contains(p, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects method, of class V3D_PolygonNoInternalHoles_d.
//     */
//    @Test
//    public void testIntersects_V3D_Line_d_double() {
//        System.out.println("intersects");
//        V3D_Line_d l = null;
//        double epsilon = 0.0;
//        V3D_PolygonNoInternalHoles_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects(l, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects method, of class V3D_PolygonNoInternalHoles_d.
//     */
//    @Test
//    public void testIntersects_V3D_LineSegment_d_double() {
//        System.out.println("intersects");
//        V3D_LineSegment_d l = null;
//        double epsilon = 0.0;
//        V3D_PolygonNoInternalHoles_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects(l, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects method, of class V3D_PolygonNoInternalHoles_d.
//     */
//    @Test
//    public void testIntersects_V3D_Ray_d_double() {
//        System.out.println("intersects");
//        V3D_Ray_d r = null;
//        double epsilon = 0.0;
//        V3D_PolygonNoInternalHoles_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects(r, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects method, of class V3D_PolygonNoInternalHoles_d.
//     */
//    @Test
//    public void testIntersects_V3D_Triangle_d_double() {
//        System.out.println("intersects");
//        V3D_Triangle_d t = null;
//        double epsilon = 0.0;
//        V3D_PolygonNoInternalHoles_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects(t, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects0 method, of class V3D_PolygonNoInternalHoles_d.
//     */
//    @Test
//    public void testIntersects0_V3D_Triangle_d_double() {
//        System.out.println("intersects0");
//        V3D_Triangle_d t = null;
//        double epsilon = 0.0;
//        V3D_PolygonNoInternalHoles_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects0(t, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects method, of class V3D_PolygonNoInternalHoles_d.
//     */
//    @Test
//    public void testIntersects_V3D_Rectangle_d_double() {
//        System.out.println("intersects");
//        V3D_Rectangle_d r = null;
//        double epsilon = 0.0;
//        V3D_PolygonNoInternalHoles_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects(r, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects method, of class V3D_PolygonNoInternalHoles_d.
//     */
//    @Test
//    public void testIntersects_V3D_ConvexArea_d_double() {
//        System.out.println("intersects");
//        V3D_ConvexArea_d ch = null;
//        double epsilon = 0.0;
//        V3D_PolygonNoInternalHoles_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects(ch, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects method, of class V3D_PolygonNoInternalHoles_d.
//     */
//    @Test
//    public void testIntersects_V3D_PolygonNoInternalHoles_d_double() {
//        System.out.println("intersects");
//        V3D_PolygonNoInternalHoles_d p = null;
//        double epsilon = 0.0;
//        V3D_PolygonNoInternalHoles_d instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects(p, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of translate method, of class V3D_PolygonNoInternalHoles_d.
//     */
//    @Test
//    public void testTranslate() {
//        System.out.println("translate");
//        V3D_Vector_d v = null;
//        V3D_PolygonNoInternalHoles_d instance = null;
//        instance.translate(v);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of rotate method, of class V3D_PolygonNoInternalHoles_d.
//     */
//    @Test
//    public void testRotate() {
//        System.out.println("rotate");
//        V3D_Ray_d ray = null;
//        V3D_Vector_d uv = null;
//        double theta = 0.0;
//        double epsilon = 0.0;
//        V3D_PolygonNoInternalHoles_d instance = null;
//        V3D_PolygonNoInternalHoles_d expResult = null;
//        V3D_PolygonNoInternalHoles_d result = instance.rotate(ray, uv, theta, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of rotateN method, of class V3D_PolygonNoInternalHoles_d.
//     */
//    @Test
//    public void testRotateN() {
//        System.out.println("rotateN");
//        V3D_Ray_d ray = null;
//        V3D_Vector_d uv = null;
//        double theta = 0.0;
//        double epsilon = 0.0;
//        V3D_PolygonNoInternalHoles_d instance = null;
//        V3D_PolygonNoInternalHoles_d expResult = null;
//        V3D_PolygonNoInternalHoles_d result = instance.rotateN(ray, uv, theta, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addExternalHole method, of class V3D_PolygonNoInternalHoles_d.
//     */
//    @Test
//    public void testAddExternalHole() {
//        System.out.println("addExternalHole");
//        V3D_PolygonNoInternalHoles_d p = null;
//        V3D_PolygonNoInternalHoles_d instance = null;
//        int expResult = 0;
//        int result = instance.addExternalHole(p);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getIntersect method, of class V3D_PolygonNoInternalHoles_d.
//     */
//    @Test
//    public void testGetIntersect_V3D_Line_d_double() {
//        System.out.println("getIntersect");
//        V3D_Line_d l = null;
//        double epsilon = 0.0;
//        V3D_PolygonNoInternalHoles_d instance = null;
//        V3D_FiniteGeometry_d expResult = null;
//        V3D_FiniteGeometry_d result = instance.getIntersect(l, epsilon);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of getIntersect method, of class V3D_ConvexArea_d.
     */
    @Test
    public void testGetIntersect_Ray() {
        System.out.println("getIntersect");
        V3D_Ray_d r;
        double epsilon = 0.0000001d;
        V3D_PolygonNoInternalHoles_d instance;
        V3D_FiniteGeometry_d result;
        V3D_FiniteGeometry_d expResult;
        V3D_Point_d[] pts;
        // Test 1;
        r = new V3D_Ray_d(pP0P0P1, pP0P0P0);
        pts = new V3D_Point_d[8];
        pts[0] = new V3D_Point_d(env, -30d, -30d, 0d);
        pts[1] = new V3D_Point_d(env, -20d, 0d, 0d);
        pts[2] = new V3D_Point_d(env, -30d, 30d, 0d);
        pts[3] = new V3D_Point_d(env, 0d, 20d, 0d);
        pts[4] = new V3D_Point_d(env, 30d, 30d, 0d);
        pts[5] = new V3D_Point_d(env, 20d, 0d, 0d);
        pts[6] = new V3D_Point_d(env, 30d, -30d, 0d);
        pts[7] = new V3D_Point_d(env, 0d, -20d, 0d);
        instance = new V3D_PolygonNoInternalHoles_d(pts, V3D_Plane_d.Z0.getN(), 
                epsilon);
        result = instance.getIntersect(r, epsilon);
        expResult = pP0P0P0;
        V3D_Point_d re = (V3D_Point_d) result;
        V3D_Point_d res = (V3D_Point_d) expResult;
        assertTrue(res.equals(re));
        // Test 2;
        r = new V3D_Ray_d(new V3D_Point_d(env, 0, 2.5d, 1d),
                new V3D_Point_d(env, 0, 25d, 0d));
        pts = new V3D_Point_d[8];
        pts[0] = new V3D_Point_d(env, -30d, -30d, 0d);
        pts[1] = new V3D_Point_d(env, -20d, 0d, 0d);
        pts[2] = new V3D_Point_d(env, -30d, 30d, 0d);
        pts[3] = new V3D_Point_d(env, 0d, 20d, 0d);
        pts[4] = new V3D_Point_d(env, 30d, 30d, 0d);
        pts[5] = new V3D_Point_d(env, 20d, 0d, 0d);
        pts[6] = new V3D_Point_d(env, 30d, -30d, 0d);
        pts[7] = new V3D_Point_d(env, 0d, -20d, 0d);
        instance = new V3D_PolygonNoInternalHoles_d(pts, V3D_Plane_d.Z0.getN(), 
                epsilon);
        result = instance.getIntersect(r, epsilon);
        expResult = null;
        assertNull(result);
    }
    
}
