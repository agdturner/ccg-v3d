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
package uk.ac.leeds.ccg.v3d.geometry.test;

import java.math.RoundingMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.v3d.geometry.V3D_ConvexArea;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Vector;

/**
 *
 * @author Andy Turner
 */
public class V3D_ConvexAreaTest extends V3D_Test {
    
    public V3D_ConvexAreaTest() {
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
//     * Test of getPoints method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testGetPoints() {
//        System.out.println("getPoints");
//        int oom = 0;
//        RoundingMode rm = null;
//        V3D_ConvexArea instance = null;
//        HashMap<Integer, V3D_Point> expResult = null;
//        HashMap<Integer, V3D_Point> result = instance.getPoints(oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getPointsArray method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testGetPointsArray() {
//        System.out.println("getPointsArray");
//        int oom = 0;
//        RoundingMode rm = null;
//        V3D_ConvexArea instance = null;
//        V3D_Point[] expResult = null;
//        V3D_Point[] result = instance.getPointsArray(oom, rm);
//        assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
    /**
     * Test of toString method, of class V3D_ConvexArea.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_ConvexArea instance = new V3D_ConvexArea(oom, rm, V3D_Vector.K,
                pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        String expResult = """
                           uk.ac.leeds.ccg.v3d.geometry.V3D_ConvexArea(
                            points (
                             (0, V3D_Point(offset=V3D_Vector(dx=0, dy=0, dz=0), rel=V3D_Vector(dx=0, dy=0, dz=0))),
                             (1, V3D_Point(offset=V3D_Vector(dx=0, dy=0, dz=0), rel=V3D_Vector(dx=1, dy=0, dz=0))),
                             (2, V3D_Point(offset=V3D_Vector(dx=0, dy=0, dz=0), rel=V3D_Vector(dx=1, dy=1, dz=0))),
                             (3, V3D_Point(offset=V3D_Vector(dx=0, dy=0, dz=0), rel=V3D_Vector(dx=0, dy=1, dz=0)))
                            )
                           )""";
        String result = instance.toString();
        //System.out.println(result);
        assertTrue(expResult.equalsIgnoreCase(result));
    }
//
//    /**
//     * Test of getEdges method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testGetEdges() {
//        System.out.println("getEdges");
//        int oom = 0;
//        RoundingMode rm = null;
//        V3D_ConvexArea instance = null;
//        HashMap<Integer, V3D_LineSegment> expResult = null;
//        HashMap<Integer, V3D_LineSegment> result = instance.getEdges(oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of toStringSimple method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testToStringSimple() {
//        System.out.println("toStringSimple");
//        V3D_ConvexArea instance = null;
//        String expResult = "";
//        String result = instance.toStringSimple();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of equals method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testEquals() {
//        System.out.println("equals");
//        V3D_ConvexArea c = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V3D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.equals(c, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of initPl method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testInitPl_int_RoundingMode() {
//        System.out.println("initPl");
//        int oom = 0;
//        RoundingMode rm = null;
//        V3D_ConvexArea instance = null;
//        instance.initPl(oom, rm);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of initPl method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testInitPl_3args() {
//        System.out.println("initPl");
//        V3D_Point pt = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V3D_ConvexArea instance = null;
//        instance.initPl(pt, oom, rm);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getAABB method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testGetAABB() {
//        System.out.println("getAABB");
//        int oom = 0;
//        RoundingMode rm = null;
//        V3D_ConvexArea instance = null;
//        V3D_AABB expResult = null;
//        V3D_AABB result = instance.getAABB(oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of simplify method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testSimplify() {
//        System.out.println("simplify");
//        int oom = 0;
//        RoundingMode rm = null;
//        V3D_ConvexArea instance = null;
//        V3D_FiniteGeometry expResult = null;
//        V3D_FiniteGeometry result = instance.simplify(oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of isAligned method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testIsAligned() {
//        System.out.println("isAligned");
//        V3D_Point pt = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V3D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.isAligned(pt, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getArea method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testGetArea() {
//        System.out.println("getArea");
//        int oom = 0;
//        RoundingMode rm = null;
//        V3D_ConvexArea instance = null;
//        BigRational expResult = null;
//        BigRational result = instance.getArea(oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getPerimeter method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testGetPerimeter() {
//        System.out.println("getPerimeter");
//        int oom = 0;
//        RoundingMode rm = null;
//        V3D_ConvexArea instance = null;
//        BigRational expResult = null;
//        BigRational result = instance.getPerimeter(oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getIntersect method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testGetIntersection_3args_1() {
//        System.out.println("getIntersect");
//        V3D_Plane p = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V3D_ConvexArea instance = null;
//        V3D_FiniteGeometry expResult = null;
//        V3D_FiniteGeometry result = instance.getIntersect(p, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getIntersect method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testGetIntersection_3args_2() {
//        System.out.println("getIntersect");
//        V3D_Triangle t = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V3D_ConvexArea instance = null;
//        V3D_FiniteGeometry expResult = null;
//        V3D_FiniteGeometry result = instance.getIntersect(t, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of rotate method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testRotate() {
//        System.out.println("rotate");
//        V3D_Ray ray = null;
//        V3D_Vector uv = null;
//        Math_BigDecimal bd = null;
//        BigRational theta = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V3D_ConvexArea instance = null;
//        V3D_ConvexArea expResult = null;
//        V3D_ConvexArea result = instance.rotate(ray, uv, bd, theta, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of rotateN method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testRotateN() {
//        System.out.println("rotateN");
//        V3D_Ray ray = null;
//        V3D_Vector uv = null;
//        Math_BigDecimal bd = null;
//        BigRational theta = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V3D_ConvexArea instance = null;
//        V3D_ConvexArea expResult = null;
//        V3D_ConvexArea result = instance.rotateN(ray, uv, bd, theta, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of contains method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testContains_3args_1() {
//        System.out.println("contains");
//        V3D_Point pt = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V3D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.contains(pt, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of contains method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testContains_3args_2() {
//        System.out.println("contains");
//        V3D_LineSegment ls = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V3D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.contains(ls, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of contains method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testContains_3args_3() {
//        System.out.println("contains");
//        V3D_Area a = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V3D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.contains(a, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of isTriangle method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testIsTriangle() {
//        System.out.println("isTriangle");
//        V3D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.isTriangle();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of isRectangle method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testIsRectangle() {
//        System.out.println("isRectangle");
//        int oom = 0;
//        RoundingMode rm = null;
//        V3D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.isRectangle(oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of clip method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testClip_4args_1() {
//        System.out.println("clip");
//        V3D_Plane pl = null;
//        V3D_Point p = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V3D_ConvexArea instance = null;
//        V3D_FiniteGeometry expResult = null;
//        V3D_FiniteGeometry result = instance.clip(pl, p, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of clip method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testClip_4args_2() {
//        System.out.println("clip");
//        V3D_Triangle t = null;
//        V3D_Point pt = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V3D_ConvexArea instance = null;
//        V3D_FiniteGeometry expResult = null;
//        V3D_FiniteGeometry result = instance.clip(t, pt, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getGeometry method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testGetGeometry() {
//        System.out.println("getGeometry");
//        int oom = 0;
//        RoundingMode rm = null;
//        V3D_Point[] pts = null;
//        V3D_FiniteGeometry expResult = null;
//        V3D_FiniteGeometry result = V3D_ConvexArea.getGeometry(oom, rm, pts);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testIntersects_3args_1() {
//        System.out.println("intersects");
//        V3D_Point pt = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V3D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects(pt, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects0 method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testIntersects0_3args_1() {
//        System.out.println("intersects0");
//        V3D_Point pt = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V3D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects0(pt, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testIntersects_3args_2() {
//        System.out.println("intersects");
//        V3D_AABB aabb = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V3D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects(aabb, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testIntersects_3args_3() {
//        System.out.println("intersects");
//        V3D_Line l = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V3D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects(l, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects0 method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testIntersects0_3args_2() {
//        System.out.println("intersects0");
//        V3D_Line l = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V3D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects0(l, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testIntersects_3args_4() {
//        System.out.println("intersects");
//        V3D_LineSegment l = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V3D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects(l, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects0 method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testIntersects0_3args_3() {
//        System.out.println("intersects0");
//        V3D_LineSegment l = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V3D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects0(l, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testIntersects_3args_5() {
//        System.out.println("intersects");
//        V3D_Triangle t = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V3D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects(t, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects0 method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testIntersects0_3args_4() {
//        System.out.println("intersects0");
//        V3D_Triangle t = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V3D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects0(t, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testIntersects_3args_6() {
//        System.out.println("intersects");
//        V3D_Rectangle r = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V3D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects(r, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects0 method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testIntersects0_3args_5() {
//        System.out.println("intersects0");
//        V3D_Rectangle r = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V3D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects0(r, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testIntersects_3args_7() {
//        System.out.println("intersects");
//        V3D_ConvexArea ch = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V3D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects(ch, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects0 method, of class V3D_ConvexArea.
//     */
//    @Test
//    public void testIntersects0_3args_6() {
//        System.out.println("intersects0");
//        V3D_ConvexArea ch = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V3D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects0(ch, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
