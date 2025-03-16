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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;
import uk.ac.leeds.ccg.v3d.geometry.V3D_AABB;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Point;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Rectangle;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Vector;

/**
 * Test class for V3D_AABB.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_AABBTest extends V3D_Test {

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
     * Test of toString method, of class V3D_AABB.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        int oom = -3;
        V3D_AABB instance = new V3D_AABB(oom, pP0P0P0);
        String expResult = "V3D_AABB(xMin=0, xMax=0, yMin=0, yMax=0"
                + ", zMin=0, zMax=0)";
        String result = instance.toString();
        //System.out.println(result);
        assertTrue(result.equalsIgnoreCase(expResult));
    }

    /**
     * Test of envelop method, of class V3D_AABB.
     */
    @Test
    public void testAABB() {
        System.out.println("envelope");
        int oom = -3;
        V3D_AABB e1 = new V3D_AABB(oom, pP0P0P0);
        V3D_AABB instance = new V3D_AABB(oom, pP1P1P1);
        V3D_AABB expResult = new V3D_AABB(oom, pP0P0P0, pP1P1P1);
        V3D_AABB result = instance.union(e1, oom);
        assertTrue(result.equals(expResult, oom));
    }

    /**
     * Test of intersects method, of class V3D_AABB.
     */
    @Test
    public void testIsIntersectedBy_V3D_AABB() {
        System.out.println("intersects");
        int oom = -3;
        V3D_AABB instance = new V3D_AABB(oom, pP0P0P0);
        V3D_AABB en = new V3D_AABB(oom, pP0P0P0, pP1P1P1);
        assertTrue(instance.intersects(en, oom));
        // Test 2
        instance = new V3D_AABB(oom, pN1N1N1, pP0P0P0);
        assertTrue(instance.intersects(en, oom));
        // Test 3
        en = new V3D_AABB(oom, pN2N2N2, pP2P2P2);
        assertTrue(instance.intersects(en, oom));
        // Test 4
        en = new V3D_AABB(oom, pP0P0P0, pP1P1P1);
        instance = new V3D_AABB(oom, pN1N1N1, pN1P0P0);
        assertFalse(instance.intersects(en, oom));
    }

    /**
     * Test of intersects method, of class V3D_AABB.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point() {
        System.out.println("intersects");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_AABB instance = new V3D_AABB(oom, pN1N1N1, pP1P1P1);
        // Test 1 the centre
        assertTrue(instance.intersects(pP0P0P0, oom, rm));
        // Test 2 to 9 the corners
        // Test 2
        assertTrue(instance.intersects(pP1P1P1, oom, rm));
        // Test 3
        assertTrue(instance.intersects(pN1N1N1, oom, rm));
        // Test 4
        assertTrue(instance.intersects(pN1N1P1, oom, rm));
        // Test 5
        assertTrue(instance.intersects(pN1P1N1, oom, rm));
        // Test 6
        assertTrue(instance.intersects(pP1N1N1, oom, rm));
        // Test 7
        assertTrue(instance.intersects(pP1P1N1, oom, rm));
        // Test 8
        assertTrue(instance.intersects(pP1N1P1, oom, rm));
        // Test 9
        assertTrue(instance.intersects(pN1P1P1, oom, rm));
        // Test 10 to 21 edge mid points
        // Test 10
        assertTrue(instance.intersects(pN1N1P0, oom, rm));
        // Test 11
        assertTrue(instance.intersects(pN1P0N1, oom, rm));
        // Test 12
        assertTrue(instance.intersects(pP0N1N1, oom, rm));
        // Test 13
        assertTrue(instance.intersects(pP1P1P0, oom, rm));
        // Test 14
        assertTrue(instance.intersects(pP1P0P1, oom, rm));
        // Test 15
        assertTrue(instance.intersects(pP0P1P1, oom, rm));
        // Test 16
        assertTrue(instance.intersects(pP0N1P1, oom, rm));
        // Test 17
        assertTrue(instance.intersects(pN1P1P0, oom, rm));
        // Test 18
        assertTrue(instance.intersects(pP1P0N1, oom, rm));
        // Test 19
        assertTrue(instance.intersects(pP0P1N1, oom, rm));
        // Test 20
        assertTrue(instance.intersects(pP1N1P0, oom, rm));
        // Test 21
        assertTrue(instance.intersects(pN1P0P1, oom, rm));
        // Test 22 to 28 face mid points
        // Test 22
        assertTrue(instance.intersects(pP0P0P1, oom, rm));
        // Test 23
        assertTrue(instance.intersects(pP0P1P0, oom, rm));
        // Test 24
        assertTrue(instance.intersects(pP1P0P0, oom, rm));
        // Test 25
        assertTrue(instance.intersects(pP0P0N1, oom, rm));
        // Test 26
        assertTrue(instance.intersects(pP0N1P0, oom, rm));
        // Test 27
        assertTrue(instance.intersects(pN1P0P0, oom, rm));
    }

    /**
     * Test of equals method, of class V3D_AABB.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        int oom = -3;
        V3D_AABB instance = new V3D_AABB(oom, pP0P0P0, pP1P1P1);
        V3D_AABB en = new V3D_AABB(oom, pP0P0P0, pP1P1P1);
        assertTrue(instance.equals(en, oom));
        // Test 2
        en = new V3D_AABB(oom, pP1P1P1, pP0P0P0);
        assertTrue(instance.equals(en, oom));
        // Test 3
        en = new V3D_AABB(oom, pP1N1P1, pP0P0P0);
        assertFalse(instance.equals(en, oom));
    }

    /**
     * Test of getIntersect method, of class V3D_AABB.
     */
    @Test
    public void testGetIntersection_V3D_AABB() {
        System.out.println("getIntersect");
        int oom = -3;
        V3D_AABB en;
        V3D_AABB instance;
        V3D_AABB expResult;
        V3D_AABB result;
        // Test 1
        en = new V3D_AABB(oom, pN1N1N1, pP1P1P1);
        instance = new V3D_AABB(oom, pP0P0P0, pP1P1P1);
        expResult = new V3D_AABB(oom, pP0P0P0, pP1P1P1);
        result = instance.getIntersect(en, oom);
        assertTrue(result.equals(expResult, oom));
        // Test 2
        en = new V3D_AABB(oom, pN1N1N1, pP0P0P0);
        instance = new V3D_AABB(oom, pP0P0P0, pP1P1P1);
        expResult = new V3D_AABB(oom, pP0P0P0);
        result = instance.getIntersect(en, oom);
        assertTrue(result.equals(expResult, oom));
        // Test 3
        en = new V3D_AABB(oom, pN1N1N1, pP0P0P0);
        instance = new V3D_AABB(oom, pP0P0P0, pP1P1P1);
        expResult = new V3D_AABB(oom, pP0P0P0);
        result = instance.getIntersect(en, oom);
        assertTrue(result.equals(expResult, oom));
        // Test 4
        en = new V3D_AABB(oom, pN1N1N1, pP0P0P1);
        instance = new V3D_AABB(oom, pP0P0N1, pP1P1P1);
        expResult = new V3D_AABB(oom, pP0P0N1, pP0P0P1);
        result = instance.getIntersect(en, oom);
        assertTrue(result.equals(expResult, oom));
    }

//    /**
//     * Test of getIntersect method, of class V3D_AABB.
//     */
//    @Test
//    public void testGetIntersection_V3D_Line_int() {
//        System.out.println("getIntersect");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_Line li = new V3D_Line(pP0P0P0, pP0P0P1, oom, rm);
//        V3D_AABB instance = new V3D_AABB(oom, rm, pN1N1N1, pP1P1P1);
//        V3D_Geometry expResult = new V3D_LineSegment(pP0P0N1, pP0P0P1, oom, rm);
//        V3D_Geometry result = instance.getIntersect(li, oom, rm);
//        assertTrue(((V3D_LineSegment) result).equalsIgnoreDirection((V3D_LineSegment) expResult, oom, rm));
//        // Test 2
//        li = new V3D_Line(pP0P0P0, pP0P1P0, oom, rm);
//        expResult = new V3D_LineSegment(pP0N1P0, pP0P1P0, oom, rm);
//        result = instance.getIntersect(li, oom, rm);
//        assertTrue(((V3D_LineSegment) result).equalsIgnoreDirection((V3D_LineSegment) expResult, oom, rm));
//        // Test 3
//        li = new V3D_Line(pP0P0P0, pP1P0P0, oom, rm);
//        expResult = new V3D_LineSegment(pN1P0P0, pP1P0P0, oom, rm);
//        result = instance.getIntersect(li, oom, rm);
//        assertTrue(((V3D_LineSegment) result).equalsIgnoreDirection((V3D_LineSegment) expResult, oom, rm));
//        // Test 4
//        li = new V3D_Line(pP1P1P1, pP0P2P1, oom, rm);
//        expResult = pP1P1P1;
//        result = instance.getIntersect(li, oom, rm);
//        assertTrue(((V3D_Point) result).equals((V3D_Point) expResult, oom, rm));
//        // Test 5
//        li = new V3D_Line(pP1P1P1, pP0P2P1, oom, rm);
//        expResult = pP1P1P1;
//        result = instance.getIntersect(li, oom, rm);
//        assertTrue(((V3D_Point) result).equals((V3D_Point) expResult, oom, rm));
//        // Test 6 Intersection of a corner at a point
//        li = new V3D_Line(pP1P1P1, pP0P2P1, oom, rm);
//        expResult = pP1P1P1;
//        result = instance.getIntersect(li, oom, rm);
//        assertTrue(((V3D_Point) result).equals((V3D_Point) expResult, oom, rm));
//        // Test 6 Intersection of an edge at a point
//        li = new V3D_Line(pP1P1P0, pP0P2P2, oom, rm);
//        expResult = pP1P1P0;
//        result = instance.getIntersect(li, oom, rm);
//        assertTrue(((V3D_Point) result).equals((V3D_Point) expResult, oom, rm));
//        // To do: add some expResult = null test cases
//    }

    /**
     * Test of union method, of class V3D_AABB.
     */
    @Test
    public void testUnion() {
        System.out.println("union");
        int oom = -3;
        V3D_AABB en = new V3D_AABB(oom, pN2N2N2, pP1P1P1);
        V3D_AABB instance = new V3D_AABB(oom, pN1N1N1, pP2P2P2);
        V3D_AABB expResult = new V3D_AABB(oom, pN2N2N2, pP2P2P2);
        V3D_AABB result = instance.union(en, oom);
        assertTrue(result.equals(expResult, oom));
    }

    /**
     * Test of contains method, of class V3D_AABB.
     */
    @Test
    public void testContaineds() {
        System.out.println("contains");
        int oom = -3;
        V3D_AABB en = new V3D_AABB(oom, pN2N2N2, pP2P2P2);
        V3D_AABB instance = new V3D_AABB(oom, pN1N1N1, pP1P1P1);
        assertTrue(en.contains(instance, oom));
        // Test 2
        instance = new V3D_AABB(oom, pN2N2N2, pP2P2P2);
        assertTrue(en.contains(instance, oom));
        // Test 3
        en = new V3D_AABB(oom, pN1N1N1, pP2P2P2);
        assertFalse(en.contains(instance, oom));
    }

//    /**
//     * Test of getIntersect method, of class V3D_AABB.
//     */
//    @Test
//    public void testGetIntersection_V3D_LineSegment_int() {
//        System.out.println("getIntersect");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_LineSegment li = new V3D_LineSegment(pN2N2N2, pP0P0P0, oom, rm);
//        V3D_AABB instance = new V3D_AABB(oom, rm, pN1N1N1, pP1P1P1);
//        V3D_Geometry expResult = new V3D_LineSegment(pN1N1N1, pP0P0P0, oom, rm);
//        V3D_Geometry result = instance.getIntersect(li, oom, rm);
//        assertTrue(((V3D_LineSegment) result).equalsIgnoreDirection((V3D_LineSegment) expResult, oom, rm));
//        // Test 2
//        li = new V3D_LineSegment(pP0P0P0, pP0P1P0, oom, rm);
//        expResult = new V3D_LineSegment(pP0P0P0, pP0P1P0, oom, rm);
//        result = instance.getIntersect(li, oom, rm);
//        assertTrue(((V3D_LineSegment) result).equalsIgnoreDirection((V3D_LineSegment) expResult, oom, rm));
//        // Test 3
//        li = new V3D_LineSegment(pP0N1P0, pP0P1P0, oom, rm);
//        expResult = new V3D_LineSegment(pP0N1P0, pP0P1P0, oom, rm);
//        result = instance.getIntersect(li, oom, rm);
//        assertTrue(((V3D_LineSegment) result).equalsIgnoreDirection((V3D_LineSegment) expResult, oom, rm));
//        // Test 4
//        li = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
//        expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
//        result = instance.getIntersect(li, oom, rm);
//        assertTrue(((V3D_LineSegment) result).equalsIgnoreDirection((V3D_LineSegment) expResult, oom, rm));
//        // Test 5
//        li = new V3D_LineSegment(pN1P0P0, pP1P0P0, oom, rm);
//        expResult = new V3D_LineSegment(pN1P0P0, pP1P0P0, oom, rm);
//        result = instance.getIntersect(li, oom, rm);
//        assertTrue(((V3D_LineSegment) result).equalsIgnoreDirection((V3D_LineSegment) expResult, oom, rm));
//        // Test 6
//        li = new V3D_LineSegment(pN2P0P0, pP1P0P0, oom, rm);
//        expResult = new V3D_LineSegment(pN1P0P0, pP1P0P0, oom, rm);
//        result = instance.getIntersect(li, oom, rm);
//        assertTrue(((V3D_LineSegment) result).equalsIgnoreDirection((V3D_LineSegment) expResult, oom, rm));
//        // Test 7
//        li = new V3D_LineSegment(pP1P1P1, pP0P2P1, oom, rm);
//        expResult = pP1P1P1;
//        result = instance.getIntersect(li, oom, rm);
//        assertTrue(((V3D_Point) result).equals((V3D_Point) expResult, oom, rm));
//        // Test 8
//        li = new V3D_LineSegment(pP1P1P1, pP0P2P1, oom, rm);
//        expResult = pP1P1P1;
//        result = instance.getIntersect(li, oom, rm);
//        assertTrue(((V3D_Point) result).equals((V3D_Point) expResult, oom, rm));
//        // Test 9 Intersection of a corner at a point
//        li = new V3D_LineSegment(pP1P1P1, pP0P2P1, oom, rm);
//        expResult = pP1P1P1;
//        result = instance.getIntersect(li, oom, rm);
//        assertTrue(((V3D_Point) result).equals((V3D_Point) expResult, oom, rm));
//        // Test 10 Intersection of an edge at a point
//        li = new V3D_LineSegment(pP1P1P0, pP2P2P0, oom, rm);
//        expResult = pP1P1P0;
//        result = instance.getIntersect(li, oom, rm);
//        assertTrue(((V3D_Point) result).equals((V3D_Point) expResult, oom, rm));
//        // To do: add some expResult = null test cases.
//    }

//    /**
//     * Test of getAABB method, of class V3D_AABB.
//     */
//    @Test
//    public void testGetAABB() {
//        System.out.println("getAABB");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_AABB instance = new V3D_AABB(oom, rm, pP0P0P0);
//        V3D_AABB expResult = new V3D_AABB(oom, rm, pP0P0P0);
//        V3D_AABB result = instance.getAABB(oom, rm);
//        assertTrue(result.equals(expResult, oom, rm));
//    }

    /**
     * Test of getXMin method, of class V3D_AABB.
     */
    @Test
    public void testGetXMin() {
        System.out.println("getxMin");
        int oom = -3;
        V3D_AABB instance = new V3D_AABB(oom, pP0N1N1, pP0N1P0, pN2N2N2);
        BigRational expResult = N2;
        BigRational result = instance.getXMin(oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getXMax method, of class V3D_AABB.
     */
    @Test
    public void testGetXMax() {
        System.out.println("getxMax");
        int oom = -3;
        V3D_AABB instance = new V3D_AABB(oom, pP0N1N1, pP0N1P0, pN2N2N2);
        BigRational expResult = P0;
        BigRational result = instance.getXMax(oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getYMin method, of class V3D_AABB.
     */
    @Test
    public void testGetYMin() {
        System.out.println("getyMin");
        int oom = -3;
        V3D_AABB instance = new V3D_AABB(oom, pP0N1N1, pP0N1P0, pN2N2N2);
        BigRational expResult = N2;
        BigRational result = instance.getYMin(oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getYMax method, of class V3D_AABB.
     */
    @Test
    public void testGetYMax() {
        System.out.println("getyMax");
        int oom = -3;
        V3D_AABB instance = new V3D_AABB(oom, pP0N1N1, pP0N1P0, pN2N2N2);
        BigRational expResult = N1;
        BigRational result = instance.getYMax(oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getZMin method, of class V3D_AABB.
     */
    @Test
    public void testGetZMin() {
        System.out.println("getzMin");
        int oom = -3;
        V3D_AABB instance = new V3D_AABB(oom, pP0N1N1, pP0N1P0, pN2N2N2);
        BigRational expResult = N2;
        BigRational result = instance.getZMin(oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getZMax method, of class V3D_AABB.
     */
    @Test
    public void testGetZMax() {
        System.out.println("getzMax");
        int oom = -3;
        V3D_AABB instance = new V3D_AABB(oom, pP0N1N1, pP0N1P0, pN2N2N2);
        BigRational expResult = P0;
        BigRational result = instance.getZMax(oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

//    /**
//     * Test of intersects method, of class V3D_AABB.
//     */
//    @Test
//    public void testIsIntersectedBy_V3D_Line_int() {
//        System.out.println("intersects");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_Line li = new V3D_Line(pP0P0P0, pP0P0P1, oom, rm);
//        V3D_AABB instance = new V3D_AABB(oom, rm, pN1N1N1, pP1P1P1);
//        assertTrue(instance.intersects(li, oom, rm));
//        // Test 2
//        li = new V3D_Line(pP0P0P0, pP0P1P0, oom, rm);
//        assertTrue(instance.intersects(li, oom, rm));
//        // Test 3
//        li = new V3D_Line(pP0P0P0, pP1P0P0, oom, rm);
//        assertTrue(instance.intersects(li, oom, rm));
//        // Test 4
//        li = new V3D_Line(pP1P1P1, pP0P2P1, oom, rm);
//        assertTrue(instance.intersects(li, oom, rm));
//        // Test 5
//        li = new V3D_Line(pP1P1P1, pP0P2P1, oom, rm);
//        assertTrue(instance.intersects(li, oom, rm));
//        // Test 6 Intersection of a corner at a point
//        li = new V3D_Line(pP1P1P1, pP0P2P1, oom, rm);
//        assertTrue(instance.intersects(li, oom, rm));
//        // Test 6 Intersection of an edge at a point
//        li = new V3D_Line(pP1P1P0, pP0P2P2, oom, rm);
//        assertTrue(instance.intersects(li, oom, rm));
//        // Test 7 Internal
//        instance = new V3D_AABB(oom, rm, pN2N2N2, pP2P2P2);
//        li = new V3D_Line(pP0P0P0, pP0P1P0, oom, rm);
//        assertTrue(instance.intersects(li, oom, rm));
//        // To do: add some false test cases.
//        li = V3D_Line.X_AXIS;
//        instance = new V3D_AABB(oom, rm, pP0P0P0);
//        assertTrue(instance.intersects(li, oom, rm));
//    }

    /**
     * Test of intersects method, of class V3D_AABB.
     */
    @Test
    public void testIsIntersectedBy_4args() {
        System.out.println("intersects");
        int oom = -3;
        BigRational x;
        BigRational y;
        BigRational z;
        V3D_AABB instance;
        // Test 1
        x = P0;
        y = P0;
        z = P0;
        instance = new V3D_AABB(oom, pP0P0P0);
        assertTrue(instance.intersects(x, y, z, oom));
        // Test 2
        instance = new V3D_AABB(oom, pP1P0P0);
        assertFalse(instance.intersects(x, y, z, oom));
        // Test 3
        instance = new V3D_AABB(oom, pN1N1N1, pP1P1P1);
        assertTrue(instance.intersects(x, y, z, oom));
    }

//    /**
//     * Test of intersects method, of class V3D_AABB.
//     */
//    @Test
//    public void testIsIntersectedBy_V3D_LineSegment_int() {
//        System.out.println("intersects");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_LineSegment li = new V3D_LineSegment(pN2N2N2, pP0P0P0, oom, rm);
//        V3D_AABB instance = new V3D_AABB(oom, rm, pN1N1N1, pP1P1P1);
//        assertTrue(instance.intersects(li, oom, rm));
//        // Test 2
//        li = new V3D_LineSegment(pP0P0P0, pP0P1P0, oom, rm);
//        assertTrue(instance.intersects(li, oom, rm));
//        // Test 3
//        li = new V3D_LineSegment(pP0N1P0, pP0P1P0, oom, rm);
//        assertTrue(instance.intersects(li, oom, rm));
//        // Test 4
//        li = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
//        assertTrue(instance.intersects(li, oom, rm));
//        // Test 5
//        li = new V3D_LineSegment(pN1P0P0, pP1P0P0, oom, rm);
//        assertTrue(instance.intersects(li, oom, rm));
//        // Test 6
//        li = new V3D_LineSegment(pN2P0P0, pP1P0P0, oom, rm);
//        assertTrue(instance.intersects(li, oom, rm));
//        // Test 7
//        li = new V3D_LineSegment(pP1P1P1, pP0P2P1, oom, rm);
//        assertTrue(instance.intersects(li, oom, rm));
//        // Test 8
//        li = new V3D_LineSegment(pP1P1P1, pP0P2P1, oom, rm);
//        assertTrue(instance.intersects(li, oom, rm));
//        // Test 9 Intersection of a corner at a point
//        li = new V3D_LineSegment(pP1P1P1, pP0P2P1, oom, rm);
//        assertTrue(instance.intersects(li, oom, rm));
//        // Test 10 Intersection of an edge at a point
//        li = new V3D_LineSegment(pP1P1P0, pP0P2P2, oom, rm);
//        assertTrue(instance.intersects(li, oom, rm));
//        // To do: add some false test cases.
//    }

//    /**
//     * Test of isAABBIntersectedBy method, of class V3D_AABB.
//     */
//    @Test
//    public void testIsAABBIntersectedBy() {
//        System.out.println("isAABBIntersectedBy");
//        V3D_Line l = new V3D_Line(pP0P0P0, pP1P0P0);
//        V3D_AABB instance = new V3D_AABB(pN1N1N1, pP1P1P1);
//        assertTrue(instance.isAABBIntersectedBy(l, oom, rm));
//        // Test 2
//        l = new V3D_Line(pN1N1N1, pN1N1P0);
//        instance = new V3D_AABB(pP0P0P0, pP1P1P1);
//        assertFalse(instance.isAABBIntersectedBy(l, oom, rm));
//    }
    /**
     * Test of translate method, of class V3D_AABB.
     */
    @Test
    public void testTranslate() {
        System.out.println("translate");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Vector v = P1P1P1;
        V3D_AABB instance = new V3D_AABB(oom, pP0P0P0, pP1P1P1);
        V3D_AABB expResult = new V3D_AABB(oom, pP1P1P1, pP2P2P2);
        instance.translate(v, oom, rm);
        assertTrue(expResult.equals(instance, oom));
        // Test 2
        v = N1N1N1;
        instance = new V3D_AABB(oom, pP0P0P0, pP1P1P1);
        expResult = new V3D_AABB(oom, pN1N1N1, pP0P0P0);
        instance.translate(v, oom, rm);
        assertTrue(expResult.equals(instance, oom));
    }

//    /**
//     * Test of getDistance method, of class V3D_AABB.
//     */
//    @Test
//    public void testGetDistance_V3D_Point_int() {
//        System.out.println("getDistance");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_Point p = pP0P0P0;
//        V3D_AABB instance = new V3D_AABB(oom, rm, pN1N1N1, pP1P1P1);
//        BigDecimal expResult = new BigRationalSqrt(0, oom, rm).toBigDecimal(oom, rm);
//        BigDecimal result = instance.getDistance(p, oom, rm);
//        assertTrue(expResult.compareTo(result) == 0);
//        // Test
//        instance = new V3D_AABB(oom, rm, pN1N1N1, pP1P1P1);
//        // Corners
//        // Test 2
//        result = instance.getDistance(pN2N2N2, oom, rm);
//        expResult = new BigRationalSqrt(3, oom, rm).toBigDecimal(oom, rm);
//        assertTrue(expResult.compareTo(result) == 0);
//        // Test 3
//        result = instance.getDistance(pN2N2P2, oom, rm);
//        assertTrue(expResult.compareTo(result) == 0);
//        // Test 4
//        result = instance.getDistance(pN2P2N2, oom, rm);
//        assertTrue(expResult.compareTo(result) == 0);
//        // Test 5
//        result = instance.getDistance(pN2P2P2, oom, rm);
//        assertTrue(expResult.compareTo(result) == 0);
//        // Test 6
//        result = instance.getDistance(pP2N2N2, oom, rm);
//        assertTrue(expResult.compareTo(result) == 0);
//        // Test 7
//        result = instance.getDistance(pP2N2P2, oom, rm);
//        assertTrue(expResult.compareTo(result) == 0);
//        // Test 8
//        result = instance.getDistance(pP2P2N2, oom, rm);
//        assertTrue(expResult.compareTo(result) == 0);
//        // Test 9
//        result = instance.getDistance(pP2P2P2, oom, rm);
//        assertTrue(expResult.compareTo(result) == 0);
//        // Edges
//        // Test 10
//        expResult = new BigRationalSqrt(2, oom, rm).toBigDecimal(oom, rm);
//        result = instance.getDistance(pN2N2P0, oom, rm);
//        assertTrue(expResult.compareTo(result) == 0);
//        // Test 11
//        result = instance.getDistance(pN2P2P0, oom, rm);
//        assertTrue(expResult.compareTo(result) == 0);
//        // Test 12
//        result = instance.getDistance(pN2P0N2, oom, rm);
//        assertTrue(expResult.compareTo(result) == 0);
//        // Test 13
//        result = instance.getDistance(pN2P0P2, oom, rm);
//        assertTrue(expResult.compareTo(result) == 0);
//        // Test 14
//        result = instance.getDistance(pP0N2P2, oom, rm);
//        assertTrue(expResult.compareTo(result) == 0);
//        // Test 15
//        result = instance.getDistance(pP0P2N2, oom, rm);
//        assertTrue(expResult.compareTo(result) == 0);
//        // Test 16
//        result = instance.getDistance(pP2P0N2, oom, rm);
//        assertTrue(expResult.compareTo(result) == 0);
//        // Test 17
//        result = instance.getDistance(pP2N2P0, oom, rm);
//        assertTrue(expResult.compareTo(result) == 0);
//        // 6 planes
//        // Test 18
//        expResult = new BigRationalSqrt(1, oom, rm).toBigDecimal(oom, rm);
//        result = instance.getDistance(pN2P0P0, oom, rm);
//        assertTrue(expResult.compareTo(result) == 0);
//        // Test 19
//        result = instance.getDistance(pP0N2P0, oom, rm);
//        assertTrue(expResult.compareTo(result) == 0);
//        // Test 20
//        result = instance.getDistance(pP0P0N2, oom, rm);
//        assertTrue(expResult.compareTo(result) == 0);
//        // Test 21
//        result = instance.getDistance(pP2P0P0, oom, rm);
//        assertTrue(expResult.compareTo(result) == 0);
//        // Test 22
//        result = instance.getDistance(pP0P2P0, oom, rm);
//        assertTrue(expResult.compareTo(result) == 0);
//        // Test 23
//        result = instance.getDistance(pP0P0P2, oom, rm);
//        assertTrue(expResult.compareTo(result) == 0);
//    }

//    /**
//     * Test of intersects method, of class V3D_AABB.
//     */
//    @Test
//    public void testIsIntersectedBy_V3D_Triangle_int() {
//        System.out.println("intersects");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_Triangle t;
//        V3D_AABB instance;
//        // Test 1
//        t = new V3D_Triangle(pN2P2P0, pN2N2P0, pP2P0P0, oom, rm);
//        instance = new V3D_AABB(oom, rm, pN1N1N1, pP1P1P1);
//        assertTrue(instance.intersects(t, oom, rm));
//        // Test 2
//        t = new V3D_Triangle(new V3D_Point(N10, P2, P0), new V3D_Point(N10, N2, P0), pP2P0P0, oom, rm);
//        instance = new V3D_AABB(oom, rm, pN1N1N1, pP1P1P1);
//        assertTrue(instance.intersects(t, oom, rm));
//        // Test 2
//        t = new V3D_Triangle(new V3D_Point(N10, P10, P0), new V3D_Point(N10, N10, P0), new V3D_Point(P10, P0, P0), oom, rm);
//        instance = new V3D_AABB(oom, rm, pN1N1N1, pP1P1P1);
//        assertTrue(instance.intersects(t, oom, rm));
//    }

//    /**
//     * Test of intersects method, of class V3D_AABB.
//     */
//    @Test
//    public void testIsIntersectedBy_V3D_Plane_int() {
//        System.out.println("intersects");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_Plane p = V3D_Plane.X0;
//        V3D_AABB instance = new V3D_AABB(oom, rm, pP0P0P0);
//        assertTrue(instance.intersects(p, oom, rm));
//        // Test 2
//        instance = new V3D_AABB(oom, rm, pN1N1N1, pP1P1P1);
//        assertTrue(instance.intersects(p, oom, rm));
//        // Test 3
//        p = new V3D_Plane(pN2P2P0, pP2N2P0, pP0P0P2, oom, rm);
//        assertTrue(instance.intersects(p, oom, rm));
//    }

    /**
     * Test of getViewport method, of class V3D_AABB.
     */
    @Test
    public void testGetViewport() {
        System.out.println("getViewport");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point pt;
        V3D_Vector v;
        V3D_AABB instance;
        V3D_Rectangle expResult;
        V3D_Rectangle result;
        instance = new V3D_AABB(oom, pN1N1N1, pP1P1P1);
        // Test front face square on.
        pt = pP0P0N2;
        // Test 1
        v = V3D_Vector.I;
        result = instance.getViewport(pt, v, oom, rm);
        expResult = new V3D_Rectangle(pN1N1N1, pN1P1N1, pP1P1N1, pP1N1N1, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 2
        v = V3D_Vector.I.reverse();
        result = instance.getViewport(pt, v, oom, rm);
        expResult = new V3D_Rectangle(pP1P1N1, pP1N1N1, pN1N1N1, pN1P1N1, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 3
        v = V3D_Vector.J;
        result = instance.getViewport(pt, v, oom, rm);
        expResult = new V3D_Rectangle(pP1N1N1, pN1N1N1, pN1P1N1, pP1P1N1, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 4
        v = V3D_Vector.J.reverse();
        result = instance.getViewport(pt, v, oom, rm);
        expResult = new V3D_Rectangle(pN1P1N1, pP1P1N1, pP1N1N1,pN1N1N1, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test left face square on.
        pt = pN2P0P0;
        // Test 5
        v = V3D_Vector.K;
        result = instance.getViewport(pt, v, oom, rm);
        expResult = new V3D_Rectangle(pN1P1N1, pN1N1N1, pN1N1P1, pN1P1P1, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 6
        v = V3D_Vector.K.reverse();
        result = instance.getViewport(pt, v, oom, rm);
        expResult = new V3D_Rectangle(pN1N1P1, pN1P1P1, pN1P1N1, pN1N1N1, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 7
        v = V3D_Vector.J;
        result = instance.getViewport(pt, v, oom, rm);
        expResult = new V3D_Rectangle(pN1N1N1, pN1N1P1, pN1P1P1, pN1P1N1, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 8
        v = V3D_Vector.J.reverse();
        result = instance.getViewport(pt, v, oom, rm);
        expResult = new V3D_Rectangle(pN1P1N1, pN1N1N1, pN1N1P1, pN1P1P1, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test bottom face square on.
        pt = pP0N2P0;
        // Test 9
        v = V3D_Vector.I;
        result = instance.getViewport(pt, v, oom, rm);
        expResult = new V3D_Rectangle(pN1N1N1, pN1N1P1, pP1N1P1, pP1N1N1, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 10
        v = V3D_Vector.I.reverse();
        result = instance.getViewport(pt, v, oom, rm);
        expResult = new V3D_Rectangle(pN1N1N1, pN1N1P1, pP1N1P1, pP1N1N1, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 11
        v = V3D_Vector.K;
        result = instance.getViewport(pt, v, oom, rm);
        expResult = new V3D_Rectangle(pN1N1N1, pP1N1N1, pP1N1P1, pN1N1P1, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 12
        v = V3D_Vector.K.reverse();
        result = instance.getViewport(pt, v, oom, rm);
        expResult = new V3D_Rectangle(pP1N1P1, pN1N1P1, pN1N1N1, pP1N1N1, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test back face square on.
        pt = pP0P0P2;
        // Test 13
        v = V3D_Vector.I.reverse();
        result = instance.getViewport(pt, v, oom, rm);
        expResult = new V3D_Rectangle(pN1N1P1, pN1P1P1, pP1P1P1, pP1N1P1, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 14
        v = V3D_Vector.I;
        result = instance.getViewport(pt, v, oom, rm);
        expResult = new V3D_Rectangle(pP1P1P1, pP1N1P1, pN1N1P1, pN1P1P1, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 15
        v = V3D_Vector.J;
        result = instance.getViewport(pt, v, oom, rm);
        expResult = new V3D_Rectangle(pP1N1P1, pN1N1P1, pN1P1P1, pP1P1P1, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 16
        v = V3D_Vector.J.reverse();
        result = instance.getViewport(pt, v, oom, rm);
        expResult = new V3D_Rectangle(pN1P1P1, pP1P1P1, pP1N1P1, pN1N1P1, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test RIGHT face square on.
        pt = pP2P0P0;
        // Test 17
        v = V3D_Vector.K;
        result = instance.getViewport(pt, v, oom, rm);
        expResult = new V3D_Rectangle(pP1P1N1, pP1N1N1, pP1N1P1, pP1P1P1, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 18
        v = V3D_Vector.K.reverse();
        result = instance.getViewport(pt, v, oom, rm);
        expResult = new V3D_Rectangle(pP1N1P1, pP1P1P1, pP1P1N1, pP1N1N1, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 19
        v = V3D_Vector.J;
        result = instance.getViewport(pt, v, oom, rm);
        expResult = new V3D_Rectangle(pP1N1N1, pP1N1P1, pP1P1P1, pP1P1N1, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 20
        v = V3D_Vector.J.reverse();
        result = instance.getViewport(pt, v, oom, rm);
        expResult = new V3D_Rectangle(pP1P1N1, pP1N1N1, pP1N1P1, pP1P1P1, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test top face square on.
        pt = pP0P2P0;
        // Test 21
        v = V3D_Vector.I;
        result = instance.getViewport(pt, v, oom, rm);
        expResult = new V3D_Rectangle(pN1P1N1, pN1P1P1, pP1P1P1, pP1P1N1, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 22
        v = V3D_Vector.I.reverse();
        result = instance.getViewport(pt, v, oom, rm);
        expResult = new V3D_Rectangle(pN1P1N1, pN1P1P1, pP1P1P1, pP1P1N1, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 23
        v = V3D_Vector.K;
        result = instance.getViewport(pt, v, oom, rm);
        expResult = new V3D_Rectangle(pN1P1N1, pP1P1N1, pP1P1P1, pN1P1P1, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 24
        v = V3D_Vector.K.reverse();
        result = instance.getViewport(pt, v, oom, rm);
        expResult = new V3D_Rectangle(pP1P1P1, pN1P1P1, pN1P1N1, pP1P1N1, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        
        V3D_Environment env = new V3D_Environment(oom, rm);
        
        // Test front left edge.
        pt = pN2P0N2;
        BigRational N1_5 = BigRational.valueOf(3, 2).negate();
        BigRational N0_5 = BigRational.valueOf(1, 2).negate();
        // Test 25
        v = V3D_Vector.I.add(V3D_Vector.K.reverse(), oom, rm);
        result = instance.getViewport(pt, v, oom, rm);
        expResult = new V3D_Rectangle(
                new V3D_Point(env, N1_5, N1, N0_5),
                new V3D_Point(env, N1_5, P1, N0_5),
                new V3D_Point(env,N0_5, P1, N1_5),
                new V3D_Point(env,N0_5, N1, N1_5), oom, rm);
        //expResult = new V3D_Rectangle(pN2N1P0, pN2P1P0, pP0P1N2, pP0N1N2, oom, rm);
  //      assertTrue(expResult.equals(result, oom, rm));
        // Test 26
        //v = v.getCrossProduct(new V3D_Vector(pN2P0N2, oom, rm), oom, rm);
        v = V3D_Vector.J;
        result = instance.getViewport(pt, v, oom, rm);
        expResult = new V3D_Rectangle(
                new V3D_Point(env,N0_5, N1, N1_5),
                new V3D_Point(env,N1_5, N1, N0_5),
                new V3D_Point(env,N1_5, P1, N0_5),
                new V3D_Point(env,N0_5, P1, N1_5), oom, rm);
        //expResult = new V3D_Rectangle(pP0N1N2, pN2N1P0, pN2P1P0, pP0P1N2, oom, rm);
 //       assertTrue(expResult.equals(result, oom, rm));
        // Test 27
        v = V3D_Vector.I.add(V3D_Vector.K.reverse(), oom, rm).reverse();
        result = instance.getViewport(pt, v, oom, rm);
        expResult = new V3D_Rectangle(
                new V3D_Point(env,N0_5, P1, N1_5), 
                new V3D_Point(env,N0_5, N1, N1_5),
                new V3D_Point(env,N1_5, N1, N0_5),
                new V3D_Point(env,N1_5, P1, N0_5),oom, rm);
        //expResult = new V3D_Rectangle(pP0P1N2, pP0N1N2, pN2N1P0, pN2P1P0, oom, rm);
//        assertTrue(expResult.equals(result, oom, rm));
        // Test 28
        v = V3D_Vector.J.reverse();
        result = instance.getViewport(pt, v, oom, rm);
        expResult = new V3D_Rectangle(
                new V3D_Point(env,N0_5, P1, N1_5),
                new V3D_Point(env,N1_5, P1, N0_5),
                new V3D_Point(env,N1_5, N1, N0_5),
                new V3D_Point(env,N0_5, N1, N1_5), oom, rm);
        //expResult = new V3D_Rectangle(pN2P1P0, pP0P1N2, pP0N1N2, pN2N1P0, oom, rm);
//        assertTrue(expResult.equals(result, oom, rm));
//        // Test front left lower corner.
//        pt = pN2N2N2;
//        // Test 29
//        v = V3D_Vector.I.add(V3D_Vector.K, oom, rm);
//        result = instance.getViewport(pt, v, oom, rm);
//        expResult = new V3D_Rectangle(
//                new V3D_Point(env,N1_5, N1, N0_5),
//                new V3D_Point(env,N1_5, P1, N0_5),
//                new V3D_Point(env,N0_5, P1, N1_5),
//                new V3D_Point(env,N0_5, N1, N1_5), oom, rm);
//        assertTrue(expResult.equals(result, oom, rm));
//        // Test 30
//        v = V3D_Vector.J;
//        result = instance.getViewport(pt, v, oom, rm);
//        expResult = new V3D_Rectangle(
//                new V3D_Point(env,N1_5, N1, N0_5),
//                new V3D_Point(env,N0_5, N1, N1_5),
//                new V3D_Point(env,N0_5, P1, N1_5),
//                new V3D_Point(env,N1_5, P1, N0_5), oom, rm);
//        assertTrue(expResult.equals(result, oom, rm));
//        // Test 31
//        v = V3D_Vector.I.add(V3D_Vector.K.reverse(), oom, rm).reverse();
//        result = instance.getViewport(pt, v, oom, rm);
//        expResult = new V3D_Rectangle(
//                new V3D_Point(env,N0_5, N1, N1_5),
//                new V3D_Point(env,N0_5, P1, N1_5),
//                new V3D_Point(env,N1_5, P1, N0_5),
//                new V3D_Point(env,N1_5, N1, N0_5), oom, rm);
//        assertTrue(expResult.equals(result, oom, rm));
//        // Test 32
//        v = V3D_Vector.J.reverse();
//        result = instance.getViewport(pt, v, oom, rm);
//        expResult = new V3D_Rectangle(
//                new V3D_Point(env,N0_5, P1, N1_5),
//                new V3D_Point(env,N1_5, P1, N0_5),
//                new V3D_Point(env,N1_5, N1, N0_5),
//                new V3D_Point(env,N0_5, N1, N1_5), oom, rm);
//        assertTrue(expResult.equals(result, oom, rm));
    }
}
