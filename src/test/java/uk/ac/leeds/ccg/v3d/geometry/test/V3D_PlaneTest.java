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
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.matrices.Math_Matrix_BR;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Geometry;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Line;
import uk.ac.leeds.ccg.v3d.geometry.V3D_LineSegment;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Plane;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Point;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Ray;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Vector;

/**
 * Test of V3D_Plane class.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_PlaneTest extends V3D_Test {

    public V3D_PlaneTest() {
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

    //@Test
    public void run() {
        testToString();
//        testIsOnPlane_V3D_Point();
//        testIsOnPlane_V3D_Point();
//        testIsOnPlane_V3D_LineSegment();
//        testEquals();
//        testIsIntersectedBy_V3D_Point();
//        testIsIntersectedBy_V3D_Line();
//        testIsIntersectedBy_V3D_Plane();
//        testGetNormalVector();
//        testIsParallel_V3D_Plane();
//        testIsParallel_V3D_Line();
//
//        testGetIntersection_V3D_Plane();
    }

    /**
     * Test of toString method, of class V3D_Plane.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        V3D_Plane instance = new V3D_Plane(pP0P0P0, pP1P1P1, pP1P0P0, oom, rm);
        String expResult = """
                           V3D_Plane(
                            offset=V3D_Vector(dx=0, dy=0, dz=0),
                            p= V3D_Vector(dx=0, dy=0, dz=0),
                            n= V3D_Vector(dx=0, dy=1, dz=-1))""";
        String result = instance.toString();
        //System.out.println(result);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of toString method, of class V3D_Plane.
     */
    @Test
    public void testGetEquation() {
        System.out.println("getEquation");
        V3D_Plane instance;
        String expResult;
        String result;
        // Test 1
        instance = new V3D_Plane(pP0P0P0, pP1P1P1, pP1P0P0, oom, rm);
        expResult = "0 * x + 1 * y + -1 * z + 0 = 0";
        result = instance.getEquationString(oom, rm);
        assertTrue(expResult.equalsIgnoreCase(result));
        // Test 2
        instance = new V3D_Plane(env, P1N2P1, new V3D_Vector(P4, N2, N2),
                new V3D_Vector(P4, P1, P4), oom, rm);
        expResult = "9 * x + -18 * y + 9 * z + -54 = 0";
        result = instance.getEquationString(oom, rm);
        assertTrue(expResult.equalsIgnoreCase(result));
    }

    /**
     * Test of isOnPlane method, of class V3D_Plane.
     */
    @Test
    public void testIsOnPlane_V3D_LineSegment() {
        System.out.println("isOnPlane");
        // Test 1 to 9 lines segments in line with axes
        V3D_LineSegment l = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        V3D_Plane instance = V3D_Plane.X0;
        assertFalse(instance.isOnPlane(l.l, oom, rm));
        // Test 2
        instance = V3D_Plane.Y0;
        assertTrue(instance.isOnPlane(l.l, oom, rm));
        // Test 3
        instance = V3D_Plane.Z0;
        assertTrue(instance.isOnPlane(l.l, oom, rm));
        // Test 4
        l = new V3D_LineSegment(pP0P0P0, pP0P1P0, oom, rm);
        instance = V3D_Plane.X0;
        assertTrue(instance.isOnPlane(l.l, oom, rm));
        // Test 5
        instance = V3D_Plane.Y0;
        assertFalse(instance.isOnPlane(l.l, oom, rm));
        // Test 6
        instance = V3D_Plane.Z0;
        assertTrue(instance.isOnPlane(l.l, oom, rm));
        // Test 7
        l = new V3D_LineSegment(pP0P0P0, pP0P0P1, oom, rm);
        instance = V3D_Plane.X0;
        assertTrue(instance.isOnPlane(l.l, oom, rm));
        // Test 8
        instance = V3D_Plane.Y0;
        assertTrue(instance.isOnPlane(l.l, oom, rm));
        // Test 9
        instance = V3D_Plane.Z0;
        assertFalse(instance.isOnPlane(l.l, oom, rm));
    }

//    /**
//     * Test of intersects method, of class V3D_Plane.
//     */
//    @Test
//    public void testIsIntersectedBy_V3D_Plane_int() {
//        System.out.println("intersects");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_Plane pl;
//        V3D_Plane instance;
//        // x=1
//        pl = new V3D_Plane(pP1P0P0, pP1P1P0, pP1P0P1, oom, rm);
//        instance = new V3D_Plane(pP1P0P0, pP1P1P0, pP1P0P1, oom, rm); // x=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(pP1P1P0, pP0P1P0, pP0P1P1, oom, rm); // y=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(pP1P0P1, pP0P1P1, pP0P0P1, oom, rm); // z=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, V3D_Vector.J, V3D_Vector.K, oom, rm); // x=0
//        assertFalse(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(V3D_Vector.I, P0P0P0, V3D_Vector.K, oom, rm); // y=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(V3D_Vector.I, V3D_Vector.J, P0P0P0, oom, rm); // z=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom, rm); // x=-1
//        assertFalse(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom, rm); // y=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom, rm); // z=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom, rm); // x=y
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom, rm); // x=y+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom, rm); // x=y-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom, rm); // x=z
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom, rm); // x=z+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom, rm); // x=z-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom, rm); // y=z
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom, rm); // y=z+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom, rm); // y=z-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        // y=1
//        pl = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom, rm);
//        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom, rm); // x=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom, rm); // y=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom, rm); // z=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, V3D_Vector.J, V3D_Vector.K, oom, rm); // x=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(V3D_Vector.I, P0P0P0, V3D_Vector.K, oom, rm); // y=0
//        assertFalse(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(V3D_Vector.I, V3D_Vector.J, P0P0P0, oom, rm); // z=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom, rm); // x=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom, rm); // y=-1
//        assertFalse(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom, rm); // z=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom, rm); // x=y
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom, rm); // x=y+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom, rm); // x=y-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom, rm); // x=z
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom, rm); // x=z+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom, rm); // x=z-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom, rm); // y=z
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom, rm); // y=z+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom, rm); // y=z-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        // z=1
//        pl = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom, rm);
//        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom, rm); // x=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom, rm); // y=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom, rm); // z=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, V3D_Vector.J, V3D_Vector.K, oom, rm); // x=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(V3D_Vector.I, P0P0P0, V3D_Vector.K, oom, rm); // y=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(V3D_Vector.I, V3D_Vector.J, P0P0P0, oom, rm); // z=0
//        assertFalse(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom, rm); // x=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom, rm); // y=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom, rm); // z=-1
//        assertFalse(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom, rm); // x=y
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom, rm); // x=y+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom, rm); // x=y-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom, rm); // x=z
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom, rm); // x=z+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom, rm); // x=z-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom, rm); // y=z
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom, rm); // y=z+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom, rm); // y=z-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        // x=0
//        pl = new V3D_Plane(P0P0P0, V3D_Vector.J, V3D_Vector.K, oom, rm);
//        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom, rm); // x=1
//        assertFalse(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom, rm); // y=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom, rm); // z=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, V3D_Vector.J, V3D_Vector.K, oom, rm); // x=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(V3D_Vector.I, P0P0P0, V3D_Vector.K, oom, rm); // y=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(V3D_Vector.I, V3D_Vector.J, P0P0P0, oom, rm); // z=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom, rm); // x=-1
//        assertFalse(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom, rm); // y=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom, rm); // z=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom, rm); // x=y
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom, rm); // x=y+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom, rm); // x=y-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom, rm); // x=z
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom, rm); // x=z+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom, rm); // x=z-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom, rm); // y=z
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom, rm); // y=z+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom, rm); // y=z-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        // y=0
//        pl = new V3D_Plane(V3D_Vector.I, P0P0P0, V3D_Vector.K, oom, rm);
//        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom, rm); // x=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom, rm); // y=1
//        assertFalse(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom, rm); // z=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, V3D_Vector.J, V3D_Vector.K, oom, rm); // x=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(V3D_Vector.I, P0P0P0, V3D_Vector.K, oom, rm); // y=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(V3D_Vector.I, V3D_Vector.J, P0P0P0, oom, rm); // z=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom, rm); // x=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom, rm); // y=-1
//        assertFalse(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom, rm); // z=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom, rm); // x=y
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom, rm); // x=y+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom, rm); // x=y-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom, rm); // x=z
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom, rm); // x=z+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom, rm); // x=z-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom, rm); // y=z
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom, rm); // y=z+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom, rm); // y=z-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        // z=0
//        pl = new V3D_Plane(V3D_Vector.I, V3D_Vector.J, P0P0P0, oom, rm);
//        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom, rm); // x=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom, rm); // y=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom, rm); // z=1
//        assertFalse(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, V3D_Vector.J, V3D_Vector.K, oom, rm); // x=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(V3D_Vector.I, P0P0P0, V3D_Vector.K, oom, rm); // y=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(V3D_Vector.I, V3D_Vector.J, P0P0P0, oom, rm); // z=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom, rm); // x=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom, rm); // y=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom, rm); // z=-1
//        assertFalse(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom, rm); // x=y
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom, rm); // x=y+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom, rm); // x=y-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom, rm); // x=z
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom, rm); // x=z+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom, rm); // x=z-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom, rm); // y=z
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom, rm); // y=z+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom, rm); // y=z-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        // x=-1
//        pl = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom, rm);
//        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom, rm); // x=1
//        assertFalse(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom, rm); // y=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom, rm); // z=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, V3D_Vector.J, V3D_Vector.K, oom, rm); // x=0
//        assertFalse(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(V3D_Vector.I, P0P0P0, V3D_Vector.K, oom, rm); // y=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(V3D_Vector.I, V3D_Vector.J, P0P0P0, oom, rm); // z=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom, rm); // x=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom, rm); // y=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom, rm); // z=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom, rm); // x=y
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom, rm); // x=y+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom, rm); // x=y-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom, rm); // x=z
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom, rm); // x=z+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom, rm); // x=z-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom, rm); // y=z
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom, rm); // y=z+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom, rm); // y=z-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        // y=-1
//        pl = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom, rm);
//        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom, rm); // x=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom, rm); // y=1
//        assertFalse(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom, rm); // z=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, V3D_Vector.J, V3D_Vector.K, oom, rm); // x=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(V3D_Vector.I, P0P0P0, V3D_Vector.K, oom, rm); // y=0
//        assertFalse(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(V3D_Vector.I, V3D_Vector.J, P0P0P0, oom, rm); // z=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom, rm); // x=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom, rm); // y=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom, rm); // z=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom, rm); // x=y
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom, rm); // x=y+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom, rm); // x=y-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom, rm); // x=z
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom, rm); // x=z+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom, rm); // x=z-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom, rm); // y=z
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom, rm); // y=z+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom, rm); // y=z-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        // z=-1
//        pl = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom, rm);
//        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom, rm); // x=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom, rm); // y=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom, rm); // z=1
//        assertFalse(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, V3D_Vector.J, V3D_Vector.K, oom, rm); // x=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(V3D_Vector.I, P0P0P0, V3D_Vector.K, oom, rm); // y=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(V3D_Vector.I, V3D_Vector.J, P0P0P0, oom, rm); // z=0
//        assertFalse(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom, rm); // x=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom, rm); // y=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom, rm); // z=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom, rm); // x=y
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom, rm); // x=y+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom, rm); // x=y-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom, rm); // x=z
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom, rm); // x=z+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom, rm); // x=z-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom, rm); // y=z
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom, rm); // y=z+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom, rm); // y=z-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        // x=y
//        pl = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom, rm);
//        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom, rm); // x=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom, rm); // y=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom, rm); // z=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, V3D_Vector.J, V3D_Vector.K, oom, rm); // x=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(V3D_Vector.I, P0P0P0, V3D_Vector.K, oom, rm); // y=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(V3D_Vector.I, V3D_Vector.J, P0P0P0, oom, rm); // z=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom, rm); // x=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom, rm); // y=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom, rm); // z=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom, rm); // x=y
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom, rm); // x=y+1
//        assertFalse(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom, rm); // x=y-1
//        assertFalse(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom, rm); // x=z
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom, rm); // x=z+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom, rm); // x=z-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom, rm); // y=z
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom, rm); // y=z+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom, rm); // y=z-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        // x=y+1
//        pl = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom, rm);
//        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom, rm); // x=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom, rm); // y=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom, rm); // z=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, V3D_Vector.J, V3D_Vector.K, oom, rm); // x=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(V3D_Vector.I, P0P0P0, V3D_Vector.K, oom, rm); // y=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(V3D_Vector.I, V3D_Vector.J, P0P0P0, oom, rm); // z=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom, rm); // x=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom, rm); // y=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom, rm); // z=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom, rm); // x=y
//        assertFalse(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom, rm); // x=y+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom, rm); // x=y-1
//        assertFalse(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom, rm); // x=z
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom, rm); // x=z+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom, rm); // x=z-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom, rm); // y=z
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom, rm); // y=z+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom, rm); // y=z-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        // x=y-1
//        pl = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom, rm);
//        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom, rm); // x=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom, rm); // y=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom, rm); // z=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, V3D_Vector.J, V3D_Vector.K, oom, rm); // x=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(V3D_Vector.I, P0P0P0, V3D_Vector.K, oom, rm); // y=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(V3D_Vector.I, V3D_Vector.J, P0P0P0, oom, rm); // z=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom, rm); // x=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom, rm); // y=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom, rm); // z=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom, rm); // x=y
//        assertFalse(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom, rm); // x=y+1
//        assertFalse(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom, rm); // x=y-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom, rm); // x=z
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom, rm); // x=z+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom, rm); // x=z-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom, rm); // y=z
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom, rm); // y=z+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom, rm); // y=z-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        // x=z
//        pl = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom, rm);
//        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom, rm); // x=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom, rm); // y=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom, rm); // z=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, V3D_Vector.J, V3D_Vector.K, oom, rm); // x=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(V3D_Vector.I, P0P0P0, V3D_Vector.K, oom, rm); // y=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(V3D_Vector.I, V3D_Vector.J, P0P0P0, oom, rm); // z=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom, rm); // x=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom, rm); // y=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom, rm); // z=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom, rm); // x=y
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom, rm); // x=y+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom, rm); // x=y-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom, rm); // x=z
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom, rm); // x=z+1
//        assertFalse(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom, rm); // x=z-1
//        assertFalse(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom, rm); // y=z
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom, rm); // y=z+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom, rm); // y=z-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        // x=z+1
//        pl = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom, rm);
//        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom, rm); // x=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom, rm); // y=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom, rm); // z=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, V3D_Vector.J, V3D_Vector.K, oom, rm); // x=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(V3D_Vector.I, P0P0P0, V3D_Vector.K, oom, rm); // y=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(V3D_Vector.I, V3D_Vector.J, P0P0P0, oom, rm); // z=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom, rm); // x=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom, rm); // y=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom, rm); // z=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom, rm); // x=y
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom, rm); // x=y+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom, rm); // x=y-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom, rm); // x=z
//        assertFalse(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom, rm); // x=z+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom, rm); // x=z-1
//        assertFalse(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom, rm); // y=z
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom, rm); // y=z+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom, rm); // y=z-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        // x=z-1
//        pl = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom, rm);
//        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom, rm); // x=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom, rm); // y=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom, rm); // z=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, V3D_Vector.J, V3D_Vector.K, oom, rm); // x=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(V3D_Vector.I, P0P0P0, V3D_Vector.K, oom, rm); // y=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(V3D_Vector.I, V3D_Vector.J, P0P0P0, oom, rm); // z=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom, rm); // x=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom, rm); // y=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom, rm); // z=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom, rm); // x=y
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom, rm); // x=y+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom, rm); // x=y-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom, rm); // x=z
//        assertFalse(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom, rm); // x=z+1
//        assertFalse(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom, rm); // x=z-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom, rm); // y=z
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom, rm); // y=z+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom, rm); // y=z-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        // y=z
//        pl = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom, rm);
//        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom, rm); // x=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom, rm); // y=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom, rm); // z=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, V3D_Vector.J, V3D_Vector.K, oom, rm); // x=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(V3D_Vector.I, P0P0P0, V3D_Vector.K, oom, rm); // y=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(V3D_Vector.I, V3D_Vector.J, P0P0P0, oom, rm); // z=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom, rm); // x=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom, rm); // y=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom, rm); // z=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom, rm); // x=y
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom, rm); // x=y+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom, rm); // x=y-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom, rm); // x=z
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom, rm); // x=z+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom, rm); // x=z-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom, rm); // y=z
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom, rm); // y=z+1
//        assertFalse(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom, rm); // y=z-1
//        assertFalse(instance.intersects(pl, oom, rm));
//        // y=z+1
//        pl = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom, rm);
//        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom, rm); // x=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom, rm); // y=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom, rm); // z=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, V3D_Vector.J, V3D_Vector.K, oom, rm); // x=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(V3D_Vector.I, P0P0P0, V3D_Vector.K, oom, rm); // y=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(V3D_Vector.I, V3D_Vector.J, P0P0P0, oom, rm); // z=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom, rm); // x=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom, rm); // y=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom, rm); // z=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom, rm); // x=y
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom, rm); // x=y+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom, rm); // x=y-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom, rm); // x=z
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom, rm); // x=z+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom, rm); // x=z-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom, rm); // y=z
//        assertFalse(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom, rm); // y=z+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom, rm); // y=z-1
//        assertFalse(instance.intersects(pl, oom, rm));
//        // y=z-1
//        pl = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom, rm);
//        instance = new V3D_Plane(P1P0P0, P1P1P0, P1P0P1, oom, rm); // x=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P1P1, oom, rm); // y=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P1, P0P1P1, P0P0P1, oom, rm); // z=1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, V3D_Vector.J, V3D_Vector.K, oom, rm); // x=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(V3D_Vector.I, P0P0P0, V3D_Vector.K, oom, rm); // y=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(V3D_Vector.I, V3D_Vector.J, P0P0P0, oom, rm); // z=0
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, N1P1P0, N1P0P1, oom, rm); // x=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1N1P0, P0N1P0, P0N1P1, oom, rm); // y=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0N1, P0P1N1, P0P0N1, oom, rm); // z=-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, P1P1P0, P1P1P1, oom, rm); // x=y
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P0N1P0, P0N1P1, oom, rm); // x=y+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, P0P1P0, N1P0P1, oom, rm); // x=y-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P0P0P0, P0P1P0, P1P0P1, oom, rm); // x=z
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P1P1P0, P2P0P1, oom, rm); // x=z+1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(N1P0P0, N1P1P0, P0P0P1, oom, rm); // x=z-1
//        assertTrue(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P0P0, P0P1P1, P1P1P1, oom, rm); // y=z
//        assertFalse(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1P1P0, P0P1P0, P0P2P1, oom, rm); // y=z+1
//        assertFalse(instance.intersects(pl, oom, rm));
//        instance = new V3D_Plane(P1N1P0, P0P1P2, P0P0P1, oom, rm); // y=z-1
//        assertTrue(instance.intersects(pl, oom, rm));
//    }
    /**
     * Test of intersects method, of class V3D_Plane.
     */
    @Test
    public void testIntersects_V3D_Point_int() {
        System.out.println("intersects");
        V3D_Plane instance;
        // x=0
        instance = new V3D_Plane(env, P0P0P0, P0P1P0, P0P0P1, oom, rm);
        // P2
        assertFalse(instance.intersects(pP2P2P2, oom, rm));
        assertFalse(instance.intersects(pP2P2P1, oom, rm));
        assertFalse(instance.intersects(pP2P2P0, oom, rm));
        assertFalse(instance.intersects(pP2P2N1, oom, rm));
        assertFalse(instance.intersects(pP2P2N2, oom, rm));
        assertFalse(instance.intersects(pP2P1P2, oom, rm));
        assertFalse(instance.intersects(pP2P1P1, oom, rm));
        assertFalse(instance.intersects(pP2P1P0, oom, rm));
        assertFalse(instance.intersects(pP2P1N1, oom, rm));
        assertFalse(instance.intersects(pP2P1N2, oom, rm));
        assertFalse(instance.intersects(pP2P0P2, oom, rm));
        assertFalse(instance.intersects(pP2P0P1, oom, rm));
        assertFalse(instance.intersects(pP2P0P0, oom, rm));
        assertFalse(instance.intersects(pP2P0N1, oom, rm));
        assertFalse(instance.intersects(pP2P0N2, oom, rm));
        assertFalse(instance.intersects(pP2N1P2, oom, rm));
        assertFalse(instance.intersects(pP2N1P1, oom, rm));
        assertFalse(instance.intersects(pP2N1P0, oom, rm));
        assertFalse(instance.intersects(pP2N1N1, oom, rm));
        assertFalse(instance.intersects(pP2N1N2, oom, rm));
        assertFalse(instance.intersects(pP2N2P2, oom, rm));
        assertFalse(instance.intersects(pP2N2P1, oom, rm));
        assertFalse(instance.intersects(pP2N2P0, oom, rm));
        assertFalse(instance.intersects(pP2N2N1, oom, rm));
        assertFalse(instance.intersects(pP2N2N2, oom, rm));
        // P1
        assertFalse(instance.intersects(pP1P2P2, oom, rm));
        assertFalse(instance.intersects(pP1P2P1, oom, rm));
        assertFalse(instance.intersects(pP1P2P0, oom, rm));
        assertFalse(instance.intersects(pP1P2N1, oom, rm));
        assertFalse(instance.intersects(pP1P2N2, oom, rm));
        assertFalse(instance.intersects(pP1P1P2, oom, rm));
        assertFalse(instance.intersects(pP1P1P1, oom, rm));
        assertFalse(instance.intersects(pP1P1P0, oom, rm));
        assertFalse(instance.intersects(pP1P1N1, oom, rm));
        assertFalse(instance.intersects(pP1P1N2, oom, rm));
        assertFalse(instance.intersects(pP1P0P2, oom, rm));
        assertFalse(instance.intersects(pP1P0P1, oom, rm));
        assertFalse(instance.intersects(pP1P0P0, oom, rm));
        assertFalse(instance.intersects(pP1P0N1, oom, rm));
        assertFalse(instance.intersects(pP1P0N2, oom, rm));
        assertFalse(instance.intersects(pP1N1P2, oom, rm));
        assertFalse(instance.intersects(pP1N1P1, oom, rm));
        assertFalse(instance.intersects(pP1N1P0, oom, rm));
        assertFalse(instance.intersects(pP1N1N1, oom, rm));
        assertFalse(instance.intersects(pP1N1N2, oom, rm));
        assertFalse(instance.intersects(pP1N2P2, oom, rm));
        assertFalse(instance.intersects(pP1N2P1, oom, rm));
        assertFalse(instance.intersects(pP1N2P0, oom, rm));
        assertFalse(instance.intersects(pP1N2N1, oom, rm));
        assertFalse(instance.intersects(pP1N2N2, oom, rm));
        // P0
        assertTrue(instance.intersects(pP0P2P2, oom, rm));
        assertTrue(instance.intersects(pP0P2P1, oom, rm));
        assertTrue(instance.intersects(pP0P2P0, oom, rm));
        assertTrue(instance.intersects(pP0P2N1, oom, rm));
        assertTrue(instance.intersects(pP0P2N2, oom, rm));
        assertTrue(instance.intersects(pP0P1P2, oom, rm));
        assertTrue(instance.intersects(pP0P1P1, oom, rm));
        assertTrue(instance.intersects(pP0P1P0, oom, rm));
        assertTrue(instance.intersects(pP0P1N1, oom, rm));
        assertTrue(instance.intersects(pP0P1N2, oom, rm));
        assertTrue(instance.intersects(pP0P0P2, oom, rm));
        assertTrue(instance.intersects(pP0P0P1, oom, rm));
        assertTrue(instance.intersects(pP0P0P0, oom, rm));
        assertTrue(instance.intersects(pP0P0N1, oom, rm));
        assertTrue(instance.intersects(pP0P0N2, oom, rm));
        assertTrue(instance.intersects(pP0N1P2, oom, rm));
        assertTrue(instance.intersects(pP0N1P1, oom, rm));
        assertTrue(instance.intersects(pP0N1P0, oom, rm));
        assertTrue(instance.intersects(pP0N1N1, oom, rm));
        assertTrue(instance.intersects(pP0N1N2, oom, rm));
        assertTrue(instance.intersects(pP0N2P2, oom, rm));
        assertTrue(instance.intersects(pP0N2P1, oom, rm));
        assertTrue(instance.intersects(pP0N2P0, oom, rm));
        assertTrue(instance.intersects(pP0N2N1, oom, rm));
        assertTrue(instance.intersects(pP0N2N2, oom, rm));
        // N1
        assertFalse(instance.intersects(pN1P2P2, oom, rm));
        assertFalse(instance.intersects(pN1P2P1, oom, rm));
        assertFalse(instance.intersects(pN1P2P0, oom, rm));
        assertFalse(instance.intersects(pN1P2N1, oom, rm));
        assertFalse(instance.intersects(pN1P2N2, oom, rm));
        assertFalse(instance.intersects(pN1P1P2, oom, rm));
        assertFalse(instance.intersects(pN1P1P1, oom, rm));
        assertFalse(instance.intersects(pN1P1P0, oom, rm));
        assertFalse(instance.intersects(pN1P1N1, oom, rm));
        assertFalse(instance.intersects(pN1P1N2, oom, rm));
        assertFalse(instance.intersects(pN1P0P2, oom, rm));
        assertFalse(instance.intersects(pN1P0P1, oom, rm));
        assertFalse(instance.intersects(pN1P0P0, oom, rm));
        assertFalse(instance.intersects(pN1P0N1, oom, rm));
        assertFalse(instance.intersects(pN1P0N2, oom, rm));
        assertFalse(instance.intersects(pN1N1P2, oom, rm));
        assertFalse(instance.intersects(pN1N1P1, oom, rm));
        assertFalse(instance.intersects(pN1N1P0, oom, rm));
        assertFalse(instance.intersects(pN1N1N1, oom, rm));
        assertFalse(instance.intersects(pN1N1N2, oom, rm));
        assertFalse(instance.intersects(pN1N2P2, oom, rm));
        assertFalse(instance.intersects(pN1N2P1, oom, rm));
        assertFalse(instance.intersects(pN1N2P0, oom, rm));
        assertFalse(instance.intersects(pN1N2N1, oom, rm));
        assertFalse(instance.intersects(pN1N2N2, oom, rm));
        // N2
        assertFalse(instance.intersects(pN2P2P2, oom, rm));
        assertFalse(instance.intersects(pN2P2P1, oom, rm));
        assertFalse(instance.intersects(pN2P2P0, oom, rm));
        assertFalse(instance.intersects(pN2P2N1, oom, rm));
        assertFalse(instance.intersects(pN2P2N2, oom, rm));
        assertFalse(instance.intersects(pN2P1P2, oom, rm));
        assertFalse(instance.intersects(pN2P1P1, oom, rm));
        assertFalse(instance.intersects(pN2P1P0, oom, rm));
        assertFalse(instance.intersects(pN2P1N1, oom, rm));
        assertFalse(instance.intersects(pN2P1N2, oom, rm));
        assertFalse(instance.intersects(pN2P0P2, oom, rm));
        assertFalse(instance.intersects(pN2P0P1, oom, rm));
        assertFalse(instance.intersects(pN2P0P0, oom, rm));
        assertFalse(instance.intersects(pN2P0N1, oom, rm));
        assertFalse(instance.intersects(pN2P0N2, oom, rm));
        assertFalse(instance.intersects(pN2N1P2, oom, rm));
        assertFalse(instance.intersects(pN2N1P1, oom, rm));
        assertFalse(instance.intersects(pN2N1P0, oom, rm));
        assertFalse(instance.intersects(pN2N1N1, oom, rm));
        assertFalse(instance.intersects(pN2N1N2, oom, rm));
        assertFalse(instance.intersects(pN2N2P2, oom, rm));
        assertFalse(instance.intersects(pN2N2P1, oom, rm));
        assertFalse(instance.intersects(pN2N2P0, oom, rm));
        assertFalse(instance.intersects(pN2N2N1, oom, rm));
        assertFalse(instance.intersects(pN2N2N2, oom, rm));

        // y=0
        instance = new V3D_Plane(env, P1P0P0, P0P0P0, P0P0P1, oom, rm);
        // P2
        assertFalse(instance.intersects(pP2P2P2, oom, rm));
        assertFalse(instance.intersects(pP2P2P1, oom, rm));
        assertFalse(instance.intersects(pP2P2P0, oom, rm));
        assertFalse(instance.intersects(pP2P2N1, oom, rm));
        assertFalse(instance.intersects(pP2P2N2, oom, rm));
        assertFalse(instance.intersects(pP2P1P2, oom, rm));
        assertFalse(instance.intersects(pP2P1P1, oom, rm));
        assertFalse(instance.intersects(pP2P1P0, oom, rm));
        assertFalse(instance.intersects(pP2P1N1, oom, rm));
        assertFalse(instance.intersects(pP2P1N2, oom, rm));
        assertTrue(instance.intersects(pP2P0P2, oom, rm));
        assertTrue(instance.intersects(pP2P0P1, oom, rm));
        assertTrue(instance.intersects(pP2P0P0, oom, rm));
        assertTrue(instance.intersects(pP2P0N1, oom, rm));
        assertTrue(instance.intersects(pP2P0N2, oom, rm));
        assertFalse(instance.intersects(pP2N1P2, oom, rm));
        assertFalse(instance.intersects(pP2N1P1, oom, rm));
        assertFalse(instance.intersects(pP2N1P0, oom, rm));
        assertFalse(instance.intersects(pP2N1N1, oom, rm));
        assertFalse(instance.intersects(pP2N1N2, oom, rm));
        assertFalse(instance.intersects(pP2N2P2, oom, rm));
        assertFalse(instance.intersects(pP2N2P1, oom, rm));
        assertFalse(instance.intersects(pP2N2P0, oom, rm));
        assertFalse(instance.intersects(pP2N2N1, oom, rm));
        assertFalse(instance.intersects(pP2N2N2, oom, rm));
        // P1
        assertFalse(instance.intersects(pP1P2P2, oom, rm));
        assertFalse(instance.intersects(pP1P2P1, oom, rm));
        assertFalse(instance.intersects(pP1P2P0, oom, rm));
        assertFalse(instance.intersects(pP1P2N1, oom, rm));
        assertFalse(instance.intersects(pP1P2N2, oom, rm));
        assertFalse(instance.intersects(pP1P1P2, oom, rm));
        assertFalse(instance.intersects(pP1P1P1, oom, rm));
        assertFalse(instance.intersects(pP1P1P0, oom, rm));
        assertFalse(instance.intersects(pP1P1N1, oom, rm));
        assertFalse(instance.intersects(pP1P1N2, oom, rm));
        assertTrue(instance.intersects(pP1P0P2, oom, rm));
        assertTrue(instance.intersects(pP1P0P1, oom, rm));
        assertTrue(instance.intersects(pP1P0P0, oom, rm));
        assertTrue(instance.intersects(pP1P0N1, oom, rm));
        assertTrue(instance.intersects(pP1P0N2, oom, rm));
        assertFalse(instance.intersects(pP1N1P2, oom, rm));
        assertFalse(instance.intersects(pP1N1P1, oom, rm));
        assertFalse(instance.intersects(pP1N1P0, oom, rm));
        assertFalse(instance.intersects(pP1N1N1, oom, rm));
        assertFalse(instance.intersects(pP1N1N2, oom, rm));
        assertFalse(instance.intersects(pP1N2P2, oom, rm));
        assertFalse(instance.intersects(pP1N2P1, oom, rm));
        assertFalse(instance.intersects(pP1N2P0, oom, rm));
        assertFalse(instance.intersects(pP1N2N1, oom, rm));
        assertFalse(instance.intersects(pP1N2N2, oom, rm));
        // P0
        assertFalse(instance.intersects(pP0P2P2, oom, rm));
        assertFalse(instance.intersects(pP0P2P1, oom, rm));
        assertFalse(instance.intersects(pP0P2P0, oom, rm));
        assertFalse(instance.intersects(pP0P2N1, oom, rm));
        assertFalse(instance.intersects(pP0P2N2, oom, rm));
        assertFalse(instance.intersects(pP0P1P2, oom, rm));
        assertFalse(instance.intersects(pP0P1P1, oom, rm));
        assertFalse(instance.intersects(pP0P1P0, oom, rm));
        assertFalse(instance.intersects(pP0P1N1, oom, rm));
        assertFalse(instance.intersects(pP0P1N2, oom, rm));
        assertTrue(instance.intersects(pP0P0P2, oom, rm));
        assertTrue(instance.intersects(pP0P0P1, oom, rm));
        assertTrue(instance.intersects(pP0P0P0, oom, rm));
        assertTrue(instance.intersects(pP0P0N1, oom, rm));
        assertTrue(instance.intersects(pP0P0N2, oom, rm));
        assertFalse(instance.intersects(pP0N1P2, oom, rm));
        assertFalse(instance.intersects(pP0N1P1, oom, rm));
        assertFalse(instance.intersects(pP0N1P0, oom, rm));
        assertFalse(instance.intersects(pP0N1N1, oom, rm));
        assertFalse(instance.intersects(pP0N1N2, oom, rm));
        assertFalse(instance.intersects(pP0N2P2, oom, rm));
        assertFalse(instance.intersects(pP0N2P1, oom, rm));
        assertFalse(instance.intersects(pP0N2P0, oom, rm));
        assertFalse(instance.intersects(pP0N2N1, oom, rm));
        assertFalse(instance.intersects(pP0N2N2, oom, rm));
        // N1
        assertFalse(instance.intersects(pN1P2P2, oom, rm));
        assertFalse(instance.intersects(pN1P2P1, oom, rm));
        assertFalse(instance.intersects(pN1P2P0, oom, rm));
        assertFalse(instance.intersects(pN1P2N1, oom, rm));
        assertFalse(instance.intersects(pN1P2N2, oom, rm));
        assertFalse(instance.intersects(pN1P1P2, oom, rm));
        assertFalse(instance.intersects(pN1P1P1, oom, rm));
        assertFalse(instance.intersects(pN1P1P0, oom, rm));
        assertFalse(instance.intersects(pN1P1N1, oom, rm));
        assertFalse(instance.intersects(pN1P1N2, oom, rm));
        assertTrue(instance.intersects(pN1P0P2, oom, rm));
        assertTrue(instance.intersects(pN1P0P1, oom, rm));
        assertTrue(instance.intersects(pN1P0P0, oom, rm));
        assertTrue(instance.intersects(pN1P0N1, oom, rm));
        assertTrue(instance.intersects(pN1P0N2, oom, rm));
        assertFalse(instance.intersects(pN1N1P2, oom, rm));
        assertFalse(instance.intersects(pN1N1P1, oom, rm));
        assertFalse(instance.intersects(pN1N1P0, oom, rm));
        assertFalse(instance.intersects(pN1N1N1, oom, rm));
        assertFalse(instance.intersects(pN1N1N2, oom, rm));
        assertFalse(instance.intersects(pN1N2P2, oom, rm));
        assertFalse(instance.intersects(pN1N2P1, oom, rm));
        assertFalse(instance.intersects(pN1N2P0, oom, rm));
        assertFalse(instance.intersects(pN1N2N1, oom, rm));
        assertFalse(instance.intersects(pN1N2N2, oom, rm));
        // N2
        assertFalse(instance.intersects(pN2P2P2, oom, rm));
        assertFalse(instance.intersects(pN2P2P1, oom, rm));
        assertFalse(instance.intersects(pN2P2P0, oom, rm));
        assertFalse(instance.intersects(pN2P2N1, oom, rm));
        assertFalse(instance.intersects(pN2P2N2, oom, rm));
        assertFalse(instance.intersects(pN2P1P2, oom, rm));
        assertFalse(instance.intersects(pN2P1P1, oom, rm));
        assertFalse(instance.intersects(pN2P1P0, oom, rm));
        assertFalse(instance.intersects(pN2P1N1, oom, rm));
        assertFalse(instance.intersects(pN2P1N2, oom, rm));
        assertTrue(instance.intersects(pN2P0P2, oom, rm));
        assertTrue(instance.intersects(pN2P0P1, oom, rm));
        assertTrue(instance.intersects(pN2P0P0, oom, rm));
        assertTrue(instance.intersects(pN2P0N1, oom, rm));
        assertTrue(instance.intersects(pN2P0N2, oom, rm));
        assertFalse(instance.intersects(pN2N1P2, oom, rm));
        assertFalse(instance.intersects(pN2N1P1, oom, rm));
        assertFalse(instance.intersects(pN2N1P0, oom, rm));
        assertFalse(instance.intersects(pN2N1N1, oom, rm));
        assertFalse(instance.intersects(pN2N1N2, oom, rm));
        assertFalse(instance.intersects(pN2N2P2, oom, rm));
        assertFalse(instance.intersects(pN2N2P1, oom, rm));
        assertFalse(instance.intersects(pN2N2P0, oom, rm));
        assertFalse(instance.intersects(pN2N2N1, oom, rm));
        assertFalse(instance.intersects(pN2N2N2, oom, rm));
        assertFalse(instance.intersects(pN2P2P2, oom, rm));
        assertFalse(instance.intersects(pN2P2P1, oom, rm));

        // z=0
        instance = new V3D_Plane(env, P0P0P0, P0P1P0, P1P0P0, oom, rm);
        // P2
        assertFalse(instance.intersects(pP2P2P2, oom, rm));
        assertFalse(instance.intersects(pP2P2P1, oom, rm));
        assertTrue(instance.intersects(pP2P2P0, oom, rm));
        assertFalse(instance.intersects(pP2P2N1, oom, rm));
        assertFalse(instance.intersects(pP2P2N2, oom, rm));
        assertFalse(instance.intersects(pP2P1P2, oom, rm));
        assertFalse(instance.intersects(pP2P1P1, oom, rm));
        assertTrue(instance.intersects(pP2P1P0, oom, rm));
        assertFalse(instance.intersects(pP2P1N1, oom, rm));
        assertFalse(instance.intersects(pP2P1N2, oom, rm));
        assertFalse(instance.intersects(pP2P0P2, oom, rm));
        assertFalse(instance.intersects(pP2P0P1, oom, rm));
        assertTrue(instance.intersects(pP2P0P0, oom, rm));
        assertFalse(instance.intersects(pP2P0N1, oom, rm));
        assertFalse(instance.intersects(pP2P0N2, oom, rm));
        assertFalse(instance.intersects(pP2N1P2, oom, rm));
        assertFalse(instance.intersects(pP2N1P1, oom, rm));
        assertTrue(instance.intersects(pP2N1P0, oom, rm));
        assertFalse(instance.intersects(pP2N1N1, oom, rm));
        assertFalse(instance.intersects(pP2N1N2, oom, rm));
        assertFalse(instance.intersects(pP2N2P2, oom, rm));
        assertFalse(instance.intersects(pP2N2P1, oom, rm));
        assertTrue(instance.intersects(pP2N2P0, oom, rm));
        assertFalse(instance.intersects(pP2N2N1, oom, rm));
        assertFalse(instance.intersects(pP2N2N2, oom, rm));
        // P1
        assertFalse(instance.intersects(pP1P2P2, oom, rm));
        assertFalse(instance.intersects(pP1P2P1, oom, rm));
        assertTrue(instance.intersects(pP1P2P0, oom, rm));
        assertFalse(instance.intersects(pP1P2N1, oom, rm));
        assertFalse(instance.intersects(pP1P2N2, oom, rm));
        assertFalse(instance.intersects(pP1P1P2, oom, rm));
        assertFalse(instance.intersects(pP1P1P1, oom, rm));
        assertTrue(instance.intersects(pP1P1P0, oom, rm));
        assertFalse(instance.intersects(pP1P1N1, oom, rm));
        assertFalse(instance.intersects(pP1P1N2, oom, rm));
        assertFalse(instance.intersects(pP1P0P2, oom, rm));
        assertFalse(instance.intersects(pP1P0P1, oom, rm));
        assertTrue(instance.intersects(pP1P0P0, oom, rm));
        assertFalse(instance.intersects(pP1P0N1, oom, rm));
        assertFalse(instance.intersects(pP1P0N2, oom, rm));
        assertFalse(instance.intersects(pP1N1P2, oom, rm));
        assertFalse(instance.intersects(pP1N1P1, oom, rm));
        assertTrue(instance.intersects(pP1N1P0, oom, rm));
        assertFalse(instance.intersects(pP1N1N1, oom, rm));
        assertFalse(instance.intersects(pP1N1N2, oom, rm));
        assertFalse(instance.intersects(pP1N2P2, oom, rm));
        assertFalse(instance.intersects(pP1N2P1, oom, rm));
        assertTrue(instance.intersects(pP1N2P0, oom, rm));
        assertFalse(instance.intersects(pP1N2N1, oom, rm));
        assertFalse(instance.intersects(pP1N2N2, oom, rm));
        // P0
        assertFalse(instance.intersects(pP0P2P2, oom, rm));
        assertFalse(instance.intersects(pP0P2P1, oom, rm));
        assertTrue(instance.intersects(pP0P2P0, oom, rm));
        assertFalse(instance.intersects(pP0P2N1, oom, rm));
        assertFalse(instance.intersects(pP0P2N2, oom, rm));
        assertFalse(instance.intersects(pP0P1P2, oom, rm));
        assertFalse(instance.intersects(pP0P1P1, oom, rm));
        assertTrue(instance.intersects(pP0P1P0, oom, rm));
        assertFalse(instance.intersects(pP0P1N1, oom, rm));
        assertFalse(instance.intersects(pP0P1N2, oom, rm));
        assertFalse(instance.intersects(pP0P0P2, oom, rm));
        assertFalse(instance.intersects(pP0P0P1, oom, rm));
        assertTrue(instance.intersects(pP0P0P0, oom, rm));
        assertFalse(instance.intersects(pP0P0N1, oom, rm));
        assertFalse(instance.intersects(pP0P0N2, oom, rm));
        assertFalse(instance.intersects(pP0N1P2, oom, rm));
        assertFalse(instance.intersects(pP0N1P1, oom, rm));
        assertTrue(instance.intersects(pP0N1P0, oom, rm));
        assertFalse(instance.intersects(pP0N1N1, oom, rm));
        assertFalse(instance.intersects(pP0N1N2, oom, rm));
        assertFalse(instance.intersects(pP0N2P2, oom, rm));
        assertFalse(instance.intersects(pP0N2P1, oom, rm));
        assertTrue(instance.intersects(pP0N2P0, oom, rm));
        assertFalse(instance.intersects(pP0N2N1, oom, rm));
        assertFalse(instance.intersects(pP0N2N2, oom, rm));
        // N1
        assertFalse(instance.intersects(pN1P2P2, oom, rm));
        assertFalse(instance.intersects(pN1P2P1, oom, rm));
        assertTrue(instance.intersects(pN1P2P0, oom, rm));
        assertFalse(instance.intersects(pN1P2N1, oom, rm));
        assertFalse(instance.intersects(pN1P2N2, oom, rm));
        assertFalse(instance.intersects(pN1P1P2, oom, rm));
        assertFalse(instance.intersects(pN1P1P1, oom, rm));
        assertTrue(instance.intersects(pN1P1P0, oom, rm));
        assertFalse(instance.intersects(pN1P1N1, oom, rm));
        assertFalse(instance.intersects(pN1P1N2, oom, rm));
        assertFalse(instance.intersects(pN1P0P2, oom, rm));
        assertFalse(instance.intersects(pN1P0P1, oom, rm));
        assertTrue(instance.intersects(pN1P0P0, oom, rm));
        assertFalse(instance.intersects(pN1P0N1, oom, rm));
        assertFalse(instance.intersects(pN1P0N2, oom, rm));
        assertFalse(instance.intersects(pN1N1P2, oom, rm));
        assertFalse(instance.intersects(pN1N1P1, oom, rm));
        assertTrue(instance.intersects(pN1N1P0, oom, rm));
        assertFalse(instance.intersects(pN1N1N1, oom, rm));
        assertFalse(instance.intersects(pN1N1N2, oom, rm));
        assertFalse(instance.intersects(pN1N2P2, oom, rm));
        assertFalse(instance.intersects(pN1N2P1, oom, rm));
        assertTrue(instance.intersects(pN1N2P0, oom, rm));
        assertFalse(instance.intersects(pN1N2N1, oom, rm));
        assertFalse(instance.intersects(pN1N2N2, oom, rm));
        // N2
        assertFalse(instance.intersects(pN2P2P2, oom, rm));
        assertFalse(instance.intersects(pN2P2P1, oom, rm));
        assertTrue(instance.intersects(pN2P2P0, oom, rm));
        assertFalse(instance.intersects(pN2P2N1, oom, rm));
        assertFalse(instance.intersects(pN2P2N2, oom, rm));
        assertFalse(instance.intersects(pN2P1P2, oom, rm));
        assertFalse(instance.intersects(pN2P1P1, oom, rm));
        assertTrue(instance.intersects(pN2P1P0, oom, rm));
        assertFalse(instance.intersects(pN2P1N1, oom, rm));
        assertFalse(instance.intersects(pN2P1N2, oom, rm));
        assertFalse(instance.intersects(pN2P0P2, oom, rm));
        assertFalse(instance.intersects(pN2P0P1, oom, rm));
        assertTrue(instance.intersects(pN2P0P0, oom, rm));
        assertFalse(instance.intersects(pN2P0N1, oom, rm));
        assertFalse(instance.intersects(pN2P0N2, oom, rm));
        assertFalse(instance.intersects(pN2N1P2, oom, rm));
        assertFalse(instance.intersects(pN2N1P1, oom, rm));
        assertTrue(instance.intersects(pN2N1P0, oom, rm));
        assertFalse(instance.intersects(pN2N1N1, oom, rm));
        assertFalse(instance.intersects(pN2N1N2, oom, rm));
        assertFalse(instance.intersects(pN2N2P2, oom, rm));
        assertFalse(instance.intersects(pN2N2P1, oom, rm));
        assertTrue(instance.intersects(pN2N2P0, oom, rm));
        assertFalse(instance.intersects(pN2N2N1, oom, rm));
        assertFalse(instance.intersects(pN2N2N2, oom, rm));

        // x=y
        instance = new V3D_Plane(env, P0P0P0, P1P1P0, P0P0P1, oom, rm);
        // P2
        assertTrue(instance.intersects(pP2P2P2, oom, rm));
        assertTrue(instance.intersects(pP2P2P1, oom, rm));
        assertTrue(instance.intersects(pP2P2P0, oom, rm));
        assertTrue(instance.intersects(pP2P2N1, oom, rm));
        assertTrue(instance.intersects(pP2P2N2, oom, rm));
        assertFalse(instance.intersects(pP2P1P2, oom, rm));
        assertFalse(instance.intersects(pP2P1P1, oom, rm));
        assertFalse(instance.intersects(pP2P1P0, oom, rm));
        assertFalse(instance.intersects(pP2P1N1, oom, rm));
        assertFalse(instance.intersects(pP2P1N2, oom, rm));
        assertFalse(instance.intersects(pP2P0P2, oom, rm));
        assertFalse(instance.intersects(pP2P0P1, oom, rm));
        assertFalse(instance.intersects(pP2P0P0, oom, rm));
        assertFalse(instance.intersects(pP2P0N1, oom, rm));
        assertFalse(instance.intersects(pP2P0N2, oom, rm));
        assertFalse(instance.intersects(pP2N1P2, oom, rm));
        assertFalse(instance.intersects(pP2N1P1, oom, rm));
        assertFalse(instance.intersects(pP2N1P0, oom, rm));
        assertFalse(instance.intersects(pP2N1N1, oom, rm));
        assertFalse(instance.intersects(pP2N1N2, oom, rm));
        assertFalse(instance.intersects(pP2N2P2, oom, rm));
        assertFalse(instance.intersects(pP2N2P1, oom, rm));
        assertFalse(instance.intersects(pP2N2P0, oom, rm));
        assertFalse(instance.intersects(pP2N2N1, oom, rm));
        assertFalse(instance.intersects(pP2N2N2, oom, rm));
        // P1
        assertFalse(instance.intersects(pP1P2P2, oom, rm));
        assertFalse(instance.intersects(pP1P2P1, oom, rm));
        assertFalse(instance.intersects(pP1P2P0, oom, rm));
        assertFalse(instance.intersects(pP1P2N1, oom, rm));
        assertFalse(instance.intersects(pP1P2N2, oom, rm));
        assertTrue(instance.intersects(pP1P1P2, oom, rm));
        assertTrue(instance.intersects(pP1P1P1, oom, rm));
        assertTrue(instance.intersects(pP1P1P0, oom, rm));
        assertTrue(instance.intersects(pP1P1N1, oom, rm));
        assertTrue(instance.intersects(pP1P1N2, oom, rm));
        assertFalse(instance.intersects(pP1P0P2, oom, rm));
        assertFalse(instance.intersects(pP1P0P1, oom, rm));
        assertFalse(instance.intersects(pP1P0P0, oom, rm));
        assertFalse(instance.intersects(pP1P0N1, oom, rm));
        assertFalse(instance.intersects(pP1P0N2, oom, rm));
        assertFalse(instance.intersects(pP1N1P2, oom, rm));
        assertFalse(instance.intersects(pP1N1P1, oom, rm));
        assertFalse(instance.intersects(pP1N1P0, oom, rm));
        assertFalse(instance.intersects(pP1N1N1, oom, rm));
        assertFalse(instance.intersects(pP1N1N2, oom, rm));
        assertFalse(instance.intersects(pP1N2P2, oom, rm));
        assertFalse(instance.intersects(pP1N2P1, oom, rm));
        assertFalse(instance.intersects(pP1N2P0, oom, rm));
        assertFalse(instance.intersects(pP1N2N1, oom, rm));
        assertFalse(instance.intersects(pP1N2N2, oom, rm));
        // P0
        assertFalse(instance.intersects(pP0P2P2, oom, rm));
        assertFalse(instance.intersects(pP0P2P1, oom, rm));
        assertFalse(instance.intersects(pP0P2P0, oom, rm));
        assertFalse(instance.intersects(pP0P2N1, oom, rm));
        assertFalse(instance.intersects(pP0P2N2, oom, rm));
        assertFalse(instance.intersects(pP0P1P2, oom, rm));
        assertFalse(instance.intersects(pP0P1P1, oom, rm));
        assertFalse(instance.intersects(pP0P1P0, oom, rm));
        assertFalse(instance.intersects(pP0P1N1, oom, rm));
        assertFalse(instance.intersects(pP0P1N2, oom, rm));
        assertTrue(instance.intersects(pP0P0P2, oom, rm));
        assertTrue(instance.intersects(pP0P0P1, oom, rm));
        assertTrue(instance.intersects(pP0P0P0, oom, rm));
        assertTrue(instance.intersects(pP0P0N1, oom, rm));
        assertTrue(instance.intersects(pP0P0N2, oom, rm));
        assertFalse(instance.intersects(pP0N1P2, oom, rm));
        assertFalse(instance.intersects(pP0N1P1, oom, rm));
        assertFalse(instance.intersects(pP0N1P0, oom, rm));
        assertFalse(instance.intersects(pP0N1N1, oom, rm));
        assertFalse(instance.intersects(pP0N1N2, oom, rm));
        assertFalse(instance.intersects(pP0N2P2, oom, rm));
        assertFalse(instance.intersects(pP0N2P1, oom, rm));
        assertFalse(instance.intersects(pP0N2P0, oom, rm));
        assertFalse(instance.intersects(pP0N2N1, oom, rm));
        assertFalse(instance.intersects(pP0N2N2, oom, rm));
        // N1
        assertFalse(instance.intersects(pN1P2P2, oom, rm));
        assertFalse(instance.intersects(pN1P2P1, oom, rm));
        assertFalse(instance.intersects(pN1P2P0, oom, rm));
        assertFalse(instance.intersects(pN1P2N1, oom, rm));
        assertFalse(instance.intersects(pN1P2N2, oom, rm));
        assertFalse(instance.intersects(pN1P1P2, oom, rm));
        assertFalse(instance.intersects(pN1P1P1, oom, rm));
        assertFalse(instance.intersects(pN1P1P0, oom, rm));
        assertFalse(instance.intersects(pN1P1N1, oom, rm));
        assertFalse(instance.intersects(pN1P1N2, oom, rm));
        assertFalse(instance.intersects(pN1P0P2, oom, rm));
        assertFalse(instance.intersects(pN1P0P1, oom, rm));
        assertFalse(instance.intersects(pN1P0P0, oom, rm));
        assertFalse(instance.intersects(pN1P0N1, oom, rm));
        assertFalse(instance.intersects(pN1P0N2, oom, rm));
        assertTrue(instance.intersects(pN1N1P2, oom, rm));
        assertTrue(instance.intersects(pN1N1P1, oom, rm));
        assertTrue(instance.intersects(pN1N1P0, oom, rm));
        assertTrue(instance.intersects(pN1N1N1, oom, rm));
        assertTrue(instance.intersects(pN1N1N2, oom, rm));
        assertFalse(instance.intersects(pN1N2P2, oom, rm));
        assertFalse(instance.intersects(pN1N2P1, oom, rm));
        assertFalse(instance.intersects(pN1N2P0, oom, rm));
        assertFalse(instance.intersects(pN1N2N1, oom, rm));
        assertFalse(instance.intersects(pN1N2N2, oom, rm));
        // N2
        assertFalse(instance.intersects(pN2P2P2, oom, rm));
        assertFalse(instance.intersects(pN2P2P1, oom, rm));
        assertFalse(instance.intersects(pN2P2P0, oom, rm));
        assertFalse(instance.intersects(pN2P2N1, oom, rm));
        assertFalse(instance.intersects(pN2P2N2, oom, rm));
        assertFalse(instance.intersects(pN2P1P2, oom, rm));
        assertFalse(instance.intersects(pN2P1P1, oom, rm));
        assertFalse(instance.intersects(pN2P1P0, oom, rm));
        assertFalse(instance.intersects(pN2P1N1, oom, rm));
        assertFalse(instance.intersects(pN2P1N2, oom, rm));
        assertFalse(instance.intersects(pN2P0P2, oom, rm));
        assertFalse(instance.intersects(pN2P0P1, oom, rm));
        assertFalse(instance.intersects(pN2P0P0, oom, rm));
        assertFalse(instance.intersects(pN2P0N1, oom, rm));
        assertFalse(instance.intersects(pN2P0N2, oom, rm));
        assertFalse(instance.intersects(pN2N1P2, oom, rm));
        assertFalse(instance.intersects(pN2N1P1, oom, rm));
        assertFalse(instance.intersects(pN2N1P0, oom, rm));
        assertFalse(instance.intersects(pN2N1N1, oom, rm));
        assertFalse(instance.intersects(pN2N1N2, oom, rm));
        assertTrue(instance.intersects(pN2N2P2, oom, rm));
        assertTrue(instance.intersects(pN2N2P1, oom, rm));
        assertTrue(instance.intersects(pN2N2P0, oom, rm));
        assertTrue(instance.intersects(pN2N2N1, oom, rm));
        assertTrue(instance.intersects(pN2N2N2, oom, rm));

        // x=-y
        instance = new V3D_Plane(env, P0P0P0, N1P1P0, P0P0P1, oom, rm);
        // P2
        assertFalse(instance.intersects(pP2P2P2, oom, rm));
        assertFalse(instance.intersects(pP2P2P1, oom, rm));
        assertFalse(instance.intersects(pP2P2P0, oom, rm));
        assertFalse(instance.intersects(pP2P2N1, oom, rm));
        assertFalse(instance.intersects(pP2P2N2, oom, rm));
        assertFalse(instance.intersects(pP2P1P2, oom, rm));
        assertFalse(instance.intersects(pP2P1P1, oom, rm));
        assertFalse(instance.intersects(pP2P1P0, oom, rm));
        assertFalse(instance.intersects(pP2P1N1, oom, rm));
        assertFalse(instance.intersects(pP2P1N2, oom, rm));
        assertFalse(instance.intersects(pP2P0P2, oom, rm));
        assertFalse(instance.intersects(pP2P0P1, oom, rm));
        assertFalse(instance.intersects(pP2P0P0, oom, rm));
        assertFalse(instance.intersects(pP2P0N1, oom, rm));
        assertFalse(instance.intersects(pP2P0N2, oom, rm));
        assertFalse(instance.intersects(pP2N1P2, oom, rm));
        assertFalse(instance.intersects(pP2N1P1, oom, rm));
        assertFalse(instance.intersects(pP2N1P0, oom, rm));
        assertFalse(instance.intersects(pP2N1N1, oom, rm));
        assertFalse(instance.intersects(pP2N1N2, oom, rm));
        assertTrue(instance.intersects(pP2N2P2, oom, rm));
        assertTrue(instance.intersects(pP2N2P1, oom, rm));
        assertTrue(instance.intersects(pP2N2P0, oom, rm));
        assertTrue(instance.intersects(pP2N2N1, oom, rm));
        assertTrue(instance.intersects(pP2N2N2, oom, rm));
        // P1
        assertFalse(instance.intersects(pP1P2P2, oom, rm));
        assertFalse(instance.intersects(pP1P2P1, oom, rm));
        assertFalse(instance.intersects(pP1P2P0, oom, rm));
        assertFalse(instance.intersects(pP1P2N1, oom, rm));
        assertFalse(instance.intersects(pP1P2N2, oom, rm));
        assertFalse(instance.intersects(pP1P1P2, oom, rm));
        assertFalse(instance.intersects(pP1P1P1, oom, rm));
        assertFalse(instance.intersects(pP1P1P0, oom, rm));
        assertFalse(instance.intersects(pP1P1N1, oom, rm));
        assertFalse(instance.intersects(pP1P1N2, oom, rm));
        assertFalse(instance.intersects(pP1P0P2, oom, rm));
        assertFalse(instance.intersects(pP1P0P1, oom, rm));
        assertFalse(instance.intersects(pP1P0P0, oom, rm));
        assertFalse(instance.intersects(pP1P0N1, oom, rm));
        assertFalse(instance.intersects(pP1P0N2, oom, rm));
        assertTrue(instance.intersects(pP1N1P2, oom, rm));
        assertTrue(instance.intersects(pP1N1P1, oom, rm));
        assertTrue(instance.intersects(pP1N1P0, oom, rm));
        assertTrue(instance.intersects(pP1N1N1, oom, rm));
        assertTrue(instance.intersects(pP1N1N2, oom, rm));
        assertFalse(instance.intersects(pP1N2P2, oom, rm));
        assertFalse(instance.intersects(pP1N2P1, oom, rm));
        assertFalse(instance.intersects(pP1N2P0, oom, rm));
        assertFalse(instance.intersects(pP1N2N1, oom, rm));
        assertFalse(instance.intersects(pP1N2N2, oom, rm));
        // P0
        assertFalse(instance.intersects(pP0P2P2, oom, rm));
        assertFalse(instance.intersects(pP0P2P1, oom, rm));
        assertFalse(instance.intersects(pP0P2P0, oom, rm));
        assertFalse(instance.intersects(pP0P2N1, oom, rm));
        assertFalse(instance.intersects(pP0P2N2, oom, rm));
        assertFalse(instance.intersects(pP0P1P2, oom, rm));
        assertFalse(instance.intersects(pP0P1P1, oom, rm));
        assertFalse(instance.intersects(pP0P1P0, oom, rm));
        assertFalse(instance.intersects(pP0P1N1, oom, rm));
        assertFalse(instance.intersects(pP0P1N2, oom, rm));
        assertTrue(instance.intersects(pP0P0P2, oom, rm));
        assertTrue(instance.intersects(pP0P0P1, oom, rm));
        assertTrue(instance.intersects(pP0P0P0, oom, rm));
        assertTrue(instance.intersects(pP0P0N1, oom, rm));
        assertTrue(instance.intersects(pP0P0N2, oom, rm));
        assertFalse(instance.intersects(pP0N1P2, oom, rm));
        assertFalse(instance.intersects(pP0N1P1, oom, rm));
        assertFalse(instance.intersects(pP0N1P0, oom, rm));
        assertFalse(instance.intersects(pP0N1N1, oom, rm));
        assertFalse(instance.intersects(pP0N1N2, oom, rm));
        assertFalse(instance.intersects(pP0N2P2, oom, rm));
        assertFalse(instance.intersects(pP0N2P1, oom, rm));
        assertFalse(instance.intersects(pP0N2P0, oom, rm));
        assertFalse(instance.intersects(pP0N2N1, oom, rm));
        assertFalse(instance.intersects(pP0N2N2, oom, rm));
        // N1
        assertFalse(instance.intersects(pN1P2P2, oom, rm));
        assertFalse(instance.intersects(pN1P2P1, oom, rm));
        assertFalse(instance.intersects(pN1P2P0, oom, rm));
        assertFalse(instance.intersects(pN1P2N1, oom, rm));
        assertFalse(instance.intersects(pN1P2N2, oom, rm));
        assertTrue(instance.intersects(pN1P1P2, oom, rm));
        assertTrue(instance.intersects(pN1P1P1, oom, rm));
        assertTrue(instance.intersects(pN1P1P0, oom, rm));
        assertTrue(instance.intersects(pN1P1N1, oom, rm));
        assertTrue(instance.intersects(pN1P1N2, oom, rm));
        assertFalse(instance.intersects(pN1P0P2, oom, rm));
        assertFalse(instance.intersects(pN1P0P1, oom, rm));
        assertFalse(instance.intersects(pN1P0P0, oom, rm));
        assertFalse(instance.intersects(pN1P0N1, oom, rm));
        assertFalse(instance.intersects(pN1P0N2, oom, rm));
        assertFalse(instance.intersects(pN1N1P2, oom, rm));
        assertFalse(instance.intersects(pN1N1P1, oom, rm));
        assertFalse(instance.intersects(pN1N1P0, oom, rm));
        assertFalse(instance.intersects(pN1N1N1, oom, rm));
        assertFalse(instance.intersects(pN1N1N2, oom, rm));
        assertFalse(instance.intersects(pN1N2P2, oom, rm));
        assertFalse(instance.intersects(pN1N2P1, oom, rm));
        assertFalse(instance.intersects(pN1N2P0, oom, rm));
        assertFalse(instance.intersects(pN1N2N1, oom, rm));
        assertFalse(instance.intersects(pN1N2N2, oom, rm));
        // N2
        assertTrue(instance.intersects(pN2P2P2, oom, rm));
        assertTrue(instance.intersects(pN2P2P1, oom, rm));
        assertTrue(instance.intersects(pN2P2P0, oom, rm));
        assertTrue(instance.intersects(pN2P2N1, oom, rm));
        assertTrue(instance.intersects(pN2P2N2, oom, rm));
        assertFalse(instance.intersects(pN2P1P2, oom, rm));
        assertFalse(instance.intersects(pN2P1P1, oom, rm));
        assertFalse(instance.intersects(pN2P1P0, oom, rm));
        assertFalse(instance.intersects(pN2P1N1, oom, rm));
        assertFalse(instance.intersects(pN2P1N2, oom, rm));
        assertFalse(instance.intersects(pN2P0P2, oom, rm));
        assertFalse(instance.intersects(pN2P0P1, oom, rm));
        assertFalse(instance.intersects(pN2P0P0, oom, rm));
        assertFalse(instance.intersects(pN2P0N1, oom, rm));
        assertFalse(instance.intersects(pN2P0N2, oom, rm));
        assertFalse(instance.intersects(pN2N1P2, oom, rm));
        assertFalse(instance.intersects(pN2N1P1, oom, rm));
        assertFalse(instance.intersects(pN2N1P0, oom, rm));
        assertFalse(instance.intersects(pN2N1N1, oom, rm));
        assertFalse(instance.intersects(pN2N1N2, oom, rm));
        assertFalse(instance.intersects(pN2N2P2, oom, rm));
        assertFalse(instance.intersects(pN2N2P1, oom, rm));
        assertFalse(instance.intersects(pN2N2P0, oom, rm));
        assertFalse(instance.intersects(pN2N2N1, oom, rm));
        assertFalse(instance.intersects(pN2N2N2, oom, rm));

        // x=z
        instance = new V3D_Plane(env, P0P0P0, P0P1P0, P1P0P1, oom, rm);
        // P2
        assertTrue(instance.intersects(pP2P2P2, oom, rm));
        assertFalse(instance.intersects(pP2P2P1, oom, rm));
        assertFalse(instance.intersects(pP2P2P0, oom, rm));
        assertFalse(instance.intersects(pP2P2N1, oom, rm));
        assertFalse(instance.intersects(pP2P2N2, oom, rm));
        assertTrue(instance.intersects(pP2P1P2, oom, rm));
        assertFalse(instance.intersects(pP2P1P1, oom, rm));
        assertFalse(instance.intersects(pP2P1P0, oom, rm));
        assertFalse(instance.intersects(pP2P1N1, oom, rm));
        assertFalse(instance.intersects(pP2P1N2, oom, rm));
        assertTrue(instance.intersects(pP2P0P2, oom, rm));
        assertFalse(instance.intersects(pP2P0P1, oom, rm));
        assertFalse(instance.intersects(pP2P0P0, oom, rm));
        assertFalse(instance.intersects(pP2P0N1, oom, rm));
        assertFalse(instance.intersects(pP2P0N2, oom, rm));
        assertTrue(instance.intersects(pP2N1P2, oom, rm));
        assertFalse(instance.intersects(pP2N1P1, oom, rm));
        assertFalse(instance.intersects(pP2N1P0, oom, rm));
        assertFalse(instance.intersects(pP2N1N1, oom, rm));
        assertFalse(instance.intersects(pP2N1N2, oom, rm));
        assertTrue(instance.intersects(pP2N2P2, oom, rm));
        assertFalse(instance.intersects(pP2N2P1, oom, rm));
        assertFalse(instance.intersects(pP2N2P0, oom, rm));
        assertFalse(instance.intersects(pP2N2N1, oom, rm));
        assertFalse(instance.intersects(pP2N2N2, oom, rm));
        // P1
        assertFalse(instance.intersects(pP1P2P2, oom, rm));
        assertTrue(instance.intersects(pP1P2P1, oom, rm));
        assertFalse(instance.intersects(pP1P2P0, oom, rm));
        assertFalse(instance.intersects(pP1P2N1, oom, rm));
        assertFalse(instance.intersects(pP1P2N2, oom, rm));
        assertFalse(instance.intersects(pP1P1P2, oom, rm));
        assertTrue(instance.intersects(pP1P1P1, oom, rm));
        assertFalse(instance.intersects(pP1P1P0, oom, rm));
        assertFalse(instance.intersects(pP1P1N1, oom, rm));
        assertFalse(instance.intersects(pP1P1N2, oom, rm));
        assertFalse(instance.intersects(pP1P0P2, oom, rm));
        assertTrue(instance.intersects(pP1P0P1, oom, rm));
        assertFalse(instance.intersects(pP1P0P0, oom, rm));
        assertFalse(instance.intersects(pP1P0N1, oom, rm));
        assertFalse(instance.intersects(pP1P0N2, oom, rm));
        assertFalse(instance.intersects(pP1N1P2, oom, rm));
        assertTrue(instance.intersects(pP1N1P1, oom, rm));
        assertFalse(instance.intersects(pP1N1P0, oom, rm));
        assertFalse(instance.intersects(pP1N1N1, oom, rm));
        assertFalse(instance.intersects(pP1N1N2, oom, rm));
        assertFalse(instance.intersects(pP1N2P2, oom, rm));
        assertTrue(instance.intersects(pP1N2P1, oom, rm));
        assertFalse(instance.intersects(pP1N2P0, oom, rm));
        assertFalse(instance.intersects(pP1N2N1, oom, rm));
        assertFalse(instance.intersects(pP1N2N2, oom, rm));
        // P0
        assertFalse(instance.intersects(pP0P2P2, oom, rm));
        assertFalse(instance.intersects(pP0P2P1, oom, rm));
        assertTrue(instance.intersects(pP0P2P0, oom, rm));
        assertFalse(instance.intersects(pP0P2N1, oom, rm));
        assertFalse(instance.intersects(pP0P2N2, oom, rm));
        assertFalse(instance.intersects(pP0P1P2, oom, rm));
        assertFalse(instance.intersects(pP0P1P1, oom, rm));
        assertTrue(instance.intersects(pP0P1P0, oom, rm));
        assertFalse(instance.intersects(pP0P1N1, oom, rm));
        assertFalse(instance.intersects(pP0P1N2, oom, rm));
        assertFalse(instance.intersects(pP0P0P2, oom, rm));
        assertFalse(instance.intersects(pP0P0P1, oom, rm));
        assertTrue(instance.intersects(pP0P0P0, oom, rm));
        assertFalse(instance.intersects(pP0P0N1, oom, rm));
        assertFalse(instance.intersects(pP0P0N2, oom, rm));
        assertFalse(instance.intersects(pP0N1P2, oom, rm));
        assertFalse(instance.intersects(pP0N1P1, oom, rm));
        assertTrue(instance.intersects(pP0N1P0, oom, rm));
        assertFalse(instance.intersects(pP0N1N1, oom, rm));
        assertFalse(instance.intersects(pP0N1N2, oom, rm));
        assertFalse(instance.intersects(pP0N2P2, oom, rm));
        assertFalse(instance.intersects(pP0N2P1, oom, rm));
        assertTrue(instance.intersects(pP0N2P0, oom, rm));
        assertFalse(instance.intersects(pP0N2N1, oom, rm));
        assertFalse(instance.intersects(pP0N2N2, oom, rm));
        // N1
        assertFalse(instance.intersects(pN1P2P2, oom, rm));
        assertFalse(instance.intersects(pN1P2P1, oom, rm));
        assertFalse(instance.intersects(pN1P2P0, oom, rm));
        assertTrue(instance.intersects(pN1P2N1, oom, rm));
        assertFalse(instance.intersects(pN1P2N2, oom, rm));
        assertFalse(instance.intersects(pN1P1P2, oom, rm));
        assertFalse(instance.intersects(pN1P1P1, oom, rm));
        assertFalse(instance.intersects(pN1P1P0, oom, rm));
        assertTrue(instance.intersects(pN1P1N1, oom, rm));
        assertFalse(instance.intersects(pN1P1N2, oom, rm));
        assertFalse(instance.intersects(pN1P0P2, oom, rm));
        assertFalse(instance.intersects(pN1P0P1, oom, rm));
        assertFalse(instance.intersects(pN1P0P0, oom, rm));
        assertTrue(instance.intersects(pN1P0N1, oom, rm));
        assertFalse(instance.intersects(pN1P0N2, oom, rm));
        assertFalse(instance.intersects(pN1N1P2, oom, rm));
        assertFalse(instance.intersects(pN1N1P1, oom, rm));
        assertFalse(instance.intersects(pN1N1P0, oom, rm));
        assertTrue(instance.intersects(pN1N1N1, oom, rm));
        assertFalse(instance.intersects(pN1N1N2, oom, rm));
        assertFalse(instance.intersects(pN1N2P2, oom, rm));
        assertFalse(instance.intersects(pN1N2P1, oom, rm));
        assertFalse(instance.intersects(pN1N2P0, oom, rm));
        assertTrue(instance.intersects(pN1N2N1, oom, rm));
        assertFalse(instance.intersects(pN1N2N2, oom, rm));
        // N2
        assertFalse(instance.intersects(pN2P2P2, oom, rm));
        assertFalse(instance.intersects(pN2P2P1, oom, rm));
        assertFalse(instance.intersects(pN2P2P0, oom, rm));
        assertFalse(instance.intersects(pN2P2N1, oom, rm));
        assertTrue(instance.intersects(pN2P2N2, oom, rm));
        assertFalse(instance.intersects(pN2P1P2, oom, rm));
        assertFalse(instance.intersects(pN2P1P1, oom, rm));
        assertFalse(instance.intersects(pN2P1P0, oom, rm));
        assertFalse(instance.intersects(pN2P1N1, oom, rm));
        assertTrue(instance.intersects(pN2P1N2, oom, rm));
        assertFalse(instance.intersects(pN2P0P2, oom, rm));
        assertFalse(instance.intersects(pN2P0P1, oom, rm));
        assertFalse(instance.intersects(pN2P0P0, oom, rm));
        assertFalse(instance.intersects(pN2P0N1, oom, rm));
        assertTrue(instance.intersects(pN2P0N2, oom, rm));
        assertFalse(instance.intersects(pN2N1P2, oom, rm));
        assertFalse(instance.intersects(pN2N1P1, oom, rm));
        assertFalse(instance.intersects(pN2N1P0, oom, rm));
        assertFalse(instance.intersects(pN2N1N1, oom, rm));
        assertTrue(instance.intersects(pN2N1N2, oom, rm));
        assertFalse(instance.intersects(pN2N2P2, oom, rm));
        assertFalse(instance.intersects(pN2N2P1, oom, rm));
        assertFalse(instance.intersects(pN2N2P0, oom, rm));
        assertFalse(instance.intersects(pN2N2N1, oom, rm));
        assertTrue(instance.intersects(pN2N2N2, oom, rm));

        // x=-z
        instance = new V3D_Plane(env, P0P0P0, P0P1P0, N1P0P1, oom, rm);
        // P2
        assertFalse(instance.intersects(pP2P2P2, oom, rm));
        assertFalse(instance.intersects(pP2P2P1, oom, rm));
        assertFalse(instance.intersects(pP2P2P0, oom, rm));
        assertFalse(instance.intersects(pP2P2N1, oom, rm));
        assertTrue(instance.intersects(pP2P2N2, oom, rm));
        assertFalse(instance.intersects(pP2P1P2, oom, rm));
        assertFalse(instance.intersects(pP2P1P1, oom, rm));
        assertFalse(instance.intersects(pP2P1P0, oom, rm));
        assertFalse(instance.intersects(pP2P1N1, oom, rm));
        assertTrue(instance.intersects(pP2P1N2, oom, rm));
        assertFalse(instance.intersects(pP2P0P2, oom, rm));
        assertFalse(instance.intersects(pP2P0P1, oom, rm));
        assertFalse(instance.intersects(pP2P0P0, oom, rm));
        assertFalse(instance.intersects(pP2P0N1, oom, rm));
        assertTrue(instance.intersects(pP2P0N2, oom, rm));
        assertFalse(instance.intersects(pP2N1P2, oom, rm));
        assertFalse(instance.intersects(pP2N1P1, oom, rm));
        assertFalse(instance.intersects(pP2N1P0, oom, rm));
        assertFalse(instance.intersects(pP2N1N1, oom, rm));
        assertTrue(instance.intersects(pP2N1N2, oom, rm));
        assertFalse(instance.intersects(pP2N2P2, oom, rm));
        assertFalse(instance.intersects(pP2N2P1, oom, rm));
        assertFalse(instance.intersects(pP2N2P0, oom, rm));
        assertFalse(instance.intersects(pP2N2N1, oom, rm));
        assertTrue(instance.intersects(pP2N2N2, oom, rm));
        // P1
        assertFalse(instance.intersects(pP1P2P2, oom, rm));
        assertFalse(instance.intersects(pP1P2P1, oom, rm));
        assertFalse(instance.intersects(pP1P2P0, oom, rm));
        assertTrue(instance.intersects(pP1P2N1, oom, rm));
        assertFalse(instance.intersects(pP1P2N2, oom, rm));
        assertFalse(instance.intersects(pP1P1P2, oom, rm));
        assertFalse(instance.intersects(pP1P1P1, oom, rm));
        assertFalse(instance.intersects(pP1P1P0, oom, rm));
        assertTrue(instance.intersects(pP1P1N1, oom, rm));
        assertFalse(instance.intersects(pP1P1N2, oom, rm));
        assertFalse(instance.intersects(pP1P0P2, oom, rm));
        assertFalse(instance.intersects(pP1P0P1, oom, rm));
        assertFalse(instance.intersects(pP1P0P0, oom, rm));
        assertTrue(instance.intersects(pP1P0N1, oom, rm));
        assertFalse(instance.intersects(pP1P0N2, oom, rm));
        assertFalse(instance.intersects(pP1N1P2, oom, rm));
        assertFalse(instance.intersects(pP1N1P1, oom, rm));
        assertFalse(instance.intersects(pP1N1P0, oom, rm));
        assertTrue(instance.intersects(pP1N1N1, oom, rm));
        assertFalse(instance.intersects(pP1N1N2, oom, rm));
        assertFalse(instance.intersects(pP1N2P2, oom, rm));
        assertFalse(instance.intersects(pP1N2P1, oom, rm));
        assertFalse(instance.intersects(pP1N2P0, oom, rm));
        assertTrue(instance.intersects(pP1N2N1, oom, rm));
        assertFalse(instance.intersects(pP1N2N2, oom, rm));
        // P0
        assertFalse(instance.intersects(pP0P2P2, oom, rm));
        assertFalse(instance.intersects(pP0P2P1, oom, rm));
        assertTrue(instance.intersects(pP0P2P0, oom, rm));
        assertFalse(instance.intersects(pP0P2N1, oom, rm));
        assertFalse(instance.intersects(pP0P2N2, oom, rm));
        assertFalse(instance.intersects(pP0P1P2, oom, rm));
        assertFalse(instance.intersects(pP0P1P1, oom, rm));
        assertTrue(instance.intersects(pP0P1P0, oom, rm));
        assertFalse(instance.intersects(pP0P1N1, oom, rm));
        assertFalse(instance.intersects(pP0P1N2, oom, rm));
        assertFalse(instance.intersects(pP0P0P2, oom, rm));
        assertFalse(instance.intersects(pP0P0P1, oom, rm));
        assertTrue(instance.intersects(pP0P0P0, oom, rm));
        assertFalse(instance.intersects(pP0P0N1, oom, rm));
        assertFalse(instance.intersects(pP0P0N2, oom, rm));
        assertFalse(instance.intersects(pP0N1P2, oom, rm));
        assertFalse(instance.intersects(pP0N1P1, oom, rm));
        assertTrue(instance.intersects(pP0N1P0, oom, rm));
        assertFalse(instance.intersects(pP0N1N1, oom, rm));
        assertFalse(instance.intersects(pP0N1N2, oom, rm));
        assertFalse(instance.intersects(pP0N2P2, oom, rm));
        assertFalse(instance.intersects(pP0N2P1, oom, rm));
        assertTrue(instance.intersects(pP0N2P0, oom, rm));
        assertFalse(instance.intersects(pP0N2N1, oom, rm));
        assertFalse(instance.intersects(pP0N2N2, oom, rm));
        // N1
        assertFalse(instance.intersects(pN1P2P2, oom, rm));
        assertTrue(instance.intersects(pN1P2P1, oom, rm));
        assertFalse(instance.intersects(pN1P2P0, oom, rm));
        assertFalse(instance.intersects(pN1P2N1, oom, rm));
        assertFalse(instance.intersects(pN1P2N2, oom, rm));
        assertFalse(instance.intersects(pN1P1P2, oom, rm));
        assertTrue(instance.intersects(pN1P1P1, oom, rm));
        assertFalse(instance.intersects(pN1P1P0, oom, rm));
        assertFalse(instance.intersects(pN1P1N1, oom, rm));
        assertFalse(instance.intersects(pN1P1N2, oom, rm));
        assertFalse(instance.intersects(pN1P0P2, oom, rm));
        assertTrue(instance.intersects(pN1P0P1, oom, rm));
        assertFalse(instance.intersects(pN1P0P0, oom, rm));
        assertFalse(instance.intersects(pN1P0N1, oom, rm));
        assertFalse(instance.intersects(pN1P0N2, oom, rm));
        assertFalse(instance.intersects(pN1N1P2, oom, rm));
        assertTrue(instance.intersects(pN1N1P1, oom, rm));
        assertFalse(instance.intersects(pN1N1P0, oom, rm));
        assertFalse(instance.intersects(pN1N1N1, oom, rm));
        assertFalse(instance.intersects(pN1N1N2, oom, rm));
        assertFalse(instance.intersects(pN1N2P2, oom, rm));
        assertTrue(instance.intersects(pN1N2P1, oom, rm));
        assertFalse(instance.intersects(pN1N2P0, oom, rm));
        assertFalse(instance.intersects(pN1N2N1, oom, rm));
        assertFalse(instance.intersects(pN1N2N2, oom, rm));
        // N2
        assertTrue(instance.intersects(pN2P2P2, oom, rm));
        assertFalse(instance.intersects(pN2P2P1, oom, rm));
        assertFalse(instance.intersects(pN2P2P0, oom, rm));
        assertFalse(instance.intersects(pN2P2N1, oom, rm));
        assertFalse(instance.intersects(pN2P2N2, oom, rm));
        assertTrue(instance.intersects(pN2P1P2, oom, rm));
        assertFalse(instance.intersects(pN2P1P1, oom, rm));
        assertFalse(instance.intersects(pN2P1P0, oom, rm));
        assertFalse(instance.intersects(pN2P1N1, oom, rm));
        assertFalse(instance.intersects(pN2P1N2, oom, rm));
        assertTrue(instance.intersects(pN2P0P2, oom, rm));
        assertFalse(instance.intersects(pN2P0P1, oom, rm));
        assertFalse(instance.intersects(pN2P0P0, oom, rm));
        assertFalse(instance.intersects(pN2P0N1, oom, rm));
        assertFalse(instance.intersects(pN2P0N2, oom, rm));
        assertTrue(instance.intersects(pN2N1P2, oom, rm));
        assertFalse(instance.intersects(pN2N1P1, oom, rm));
        assertFalse(instance.intersects(pN2N1P0, oom, rm));
        assertFalse(instance.intersects(pN2N1N1, oom, rm));
        assertFalse(instance.intersects(pN2N1N2, oom, rm));
        assertTrue(instance.intersects(pN2N2P2, oom, rm));
        assertFalse(instance.intersects(pN2N2P1, oom, rm));
        assertFalse(instance.intersects(pN2N2P0, oom, rm));
        assertFalse(instance.intersects(pN2N2N1, oom, rm));
        assertFalse(instance.intersects(pN2N2N2, oom, rm));

        // y=z
        instance = new V3D_Plane(env, P1P0P0, P0P0P0, P0P1P1, oom, rm);
        // P2
        assertTrue(instance.intersects(pP2P2P2, oom, rm));
        assertFalse(instance.intersects(pP2P2P1, oom, rm));
        assertFalse(instance.intersects(pP2P2P0, oom, rm));
        assertFalse(instance.intersects(pP2P2N1, oom, rm));
        assertFalse(instance.intersects(pP2P2N2, oom, rm));
        assertFalse(instance.intersects(pP2P1P2, oom, rm));
        assertTrue(instance.intersects(pP2P1P1, oom, rm));
        assertFalse(instance.intersects(pP2P1P0, oom, rm));
        assertFalse(instance.intersects(pP2P1N1, oom, rm));
        assertFalse(instance.intersects(pP2P1N2, oom, rm));
        assertFalse(instance.intersects(pP2P0P2, oom, rm));
        assertFalse(instance.intersects(pP2P0P1, oom, rm));
        assertTrue(instance.intersects(pP2P0P0, oom, rm));
        assertFalse(instance.intersects(pP2P0N1, oom, rm));
        assertFalse(instance.intersects(pP2P0N2, oom, rm));
        assertFalse(instance.intersects(pP2N1P2, oom, rm));
        assertFalse(instance.intersects(pP2N1P1, oom, rm));
        assertFalse(instance.intersects(pP2N1P0, oom, rm));
        assertTrue(instance.intersects(pP2N1N1, oom, rm));
        assertFalse(instance.intersects(pP2N1N2, oom, rm));
        assertFalse(instance.intersects(pP2N2P2, oom, rm));
        assertFalse(instance.intersects(pP2N2P1, oom, rm));
        assertFalse(instance.intersects(pP2N2P0, oom, rm));
        assertFalse(instance.intersects(pP2N2N1, oom, rm));
        assertTrue(instance.intersects(pP2N2N2, oom, rm));
        // P1
        assertTrue(instance.intersects(pP1P2P2, oom, rm));
        assertFalse(instance.intersects(pP1P2P1, oom, rm));
        assertFalse(instance.intersects(pP1P2P0, oom, rm));
        assertFalse(instance.intersects(pP1P2N1, oom, rm));
        assertFalse(instance.intersects(pP1P2N2, oom, rm));
        assertFalse(instance.intersects(pP1P1P2, oom, rm));
        assertTrue(instance.intersects(pP1P1P1, oom, rm));
        assertFalse(instance.intersects(pP1P1P0, oom, rm));
        assertFalse(instance.intersects(pP1P1N1, oom, rm));
        assertFalse(instance.intersects(pP1P1N2, oom, rm));
        assertFalse(instance.intersects(pP1P0P2, oom, rm));
        assertFalse(instance.intersects(pP1P0P1, oom, rm));
        assertTrue(instance.intersects(pP1P0P0, oom, rm));
        assertFalse(instance.intersects(pP1P0N1, oom, rm));
        assertFalse(instance.intersects(pP1P0N2, oom, rm));
        assertFalse(instance.intersects(pP1N1P2, oom, rm));
        assertFalse(instance.intersects(pP1N1P1, oom, rm));
        assertFalse(instance.intersects(pP1N1P0, oom, rm));
        assertTrue(instance.intersects(pP1N1N1, oom, rm));
        assertFalse(instance.intersects(pP1N1N2, oom, rm));
        assertFalse(instance.intersects(pP1N2P2, oom, rm));
        assertFalse(instance.intersects(pP1N2P1, oom, rm));
        assertFalse(instance.intersects(pP1N2P0, oom, rm));
        assertFalse(instance.intersects(pP1N2N1, oom, rm));
        assertTrue(instance.intersects(pP1N2N2, oom, rm));
        // P0
        assertTrue(instance.intersects(pP0P2P2, oom, rm));
        assertFalse(instance.intersects(pP0P2P1, oom, rm));
        assertFalse(instance.intersects(pP0P2P0, oom, rm));
        assertFalse(instance.intersects(pP0P2N1, oom, rm));
        assertFalse(instance.intersects(pP0P2N2, oom, rm));
        assertFalse(instance.intersects(pP0P1P2, oom, rm));
        assertTrue(instance.intersects(pP0P1P1, oom, rm));
        assertFalse(instance.intersects(pP0P1P0, oom, rm));
        assertFalse(instance.intersects(pP0P1N1, oom, rm));
        assertFalse(instance.intersects(pP0P1N2, oom, rm));
        assertFalse(instance.intersects(pP0P0P2, oom, rm));
        assertFalse(instance.intersects(pP0P0P1, oom, rm));
        assertTrue(instance.intersects(pP0P0P0, oom, rm));
        assertFalse(instance.intersects(pP0P0N1, oom, rm));
        assertFalse(instance.intersects(pP0P0N2, oom, rm));
        assertFalse(instance.intersects(pP0N1P2, oom, rm));
        assertFalse(instance.intersects(pP0N1P1, oom, rm));
        assertFalse(instance.intersects(pP0N1P0, oom, rm));
        assertTrue(instance.intersects(pP0N1N1, oom, rm));
        assertFalse(instance.intersects(pP0N1N2, oom, rm));
        assertFalse(instance.intersects(pP0N2P2, oom, rm));
        assertFalse(instance.intersects(pP0N2P1, oom, rm));
        assertFalse(instance.intersects(pP0N2P0, oom, rm));
        assertFalse(instance.intersects(pP0N2N1, oom, rm));
        assertTrue(instance.intersects(pP0N2N2, oom, rm));
        // N1
        assertTrue(instance.intersects(pN1P2P2, oom, rm));
        assertFalse(instance.intersects(pN1P2P1, oom, rm));
        assertFalse(instance.intersects(pN1P2P0, oom, rm));
        assertFalse(instance.intersects(pN1P2N1, oom, rm));
        assertFalse(instance.intersects(pN1P2N2, oom, rm));
        assertFalse(instance.intersects(pN1P1P2, oom, rm));
        assertTrue(instance.intersects(pN1P1P1, oom, rm));
        assertFalse(instance.intersects(pN1P1P0, oom, rm));
        assertFalse(instance.intersects(pN1P1N1, oom, rm));
        assertFalse(instance.intersects(pN1P1N2, oom, rm));
        assertFalse(instance.intersects(pN1P0P2, oom, rm));
        assertFalse(instance.intersects(pN1P0P1, oom, rm));
        assertTrue(instance.intersects(pN1P0P0, oom, rm));
        assertFalse(instance.intersects(pN1P0N1, oom, rm));
        assertFalse(instance.intersects(pN1P0N2, oom, rm));
        assertFalse(instance.intersects(pN1N1P2, oom, rm));
        assertFalse(instance.intersects(pN1N1P1, oom, rm));
        assertFalse(instance.intersects(pN1N1P0, oom, rm));
        assertTrue(instance.intersects(pN1N1N1, oom, rm));
        assertFalse(instance.intersects(pN1N1N2, oom, rm));
        assertFalse(instance.intersects(pN1N2P2, oom, rm));
        assertFalse(instance.intersects(pN1N2P1, oom, rm));
        assertFalse(instance.intersects(pN1N2P0, oom, rm));
        assertFalse(instance.intersects(pN1N2N1, oom, rm));
        assertTrue(instance.intersects(pN1N2N2, oom, rm));
        // N2
        assertTrue(instance.intersects(pN2P2P2, oom, rm));
        assertFalse(instance.intersects(pN2P2P1, oom, rm));
        assertFalse(instance.intersects(pN2P2P0, oom, rm));
        assertFalse(instance.intersects(pN2P2N1, oom, rm));
        assertFalse(instance.intersects(pN2P2N2, oom, rm));
        assertFalse(instance.intersects(pN2P1P2, oom, rm));
        assertTrue(instance.intersects(pN2P1P1, oom, rm));
        assertFalse(instance.intersects(pN2P1P0, oom, rm));
        assertFalse(instance.intersects(pN2P1N1, oom, rm));
        assertFalse(instance.intersects(pN2P1N2, oom, rm));
        assertFalse(instance.intersects(pN2P0P2, oom, rm));
        assertFalse(instance.intersects(pN2P0P1, oom, rm));
        assertTrue(instance.intersects(pN2P0P0, oom, rm));
        assertFalse(instance.intersects(pN2P0N1, oom, rm));
        assertFalse(instance.intersects(pN2P0N2, oom, rm));
        assertFalse(instance.intersects(pN2N1P2, oom, rm));
        assertFalse(instance.intersects(pN2N1P1, oom, rm));
        assertFalse(instance.intersects(pN2N1P0, oom, rm));
        assertTrue(instance.intersects(pN2N1N1, oom, rm));
        assertFalse(instance.intersects(pN2N1N2, oom, rm));
        assertFalse(instance.intersects(pN2N2P2, oom, rm));
        assertFalse(instance.intersects(pN2N2P1, oom, rm));
        assertFalse(instance.intersects(pN2N2P0, oom, rm));
        assertFalse(instance.intersects(pN2N2N1, oom, rm));
        assertTrue(instance.intersects(pN2N2N2, oom, rm));

        // x=-z
        instance = new V3D_Plane(env, P1P0P0, P0P0P0, P0N1P1, oom, rm);
        // P2
        assertFalse(instance.intersects(pP2P2P2, oom, rm));
        assertFalse(instance.intersects(pP2P2P1, oom, rm));
        assertFalse(instance.intersects(pP2P2P0, oom, rm));
        assertFalse(instance.intersects(pP2P2N1, oom, rm));
        assertTrue(instance.intersects(pP2P2N2, oom, rm));
        assertFalse(instance.intersects(pP2P1P2, oom, rm));
        assertFalse(instance.intersects(pP2P1P1, oom, rm));
        assertFalse(instance.intersects(pP2P1P0, oom, rm));
        assertTrue(instance.intersects(pP2P1N1, oom, rm));
        assertFalse(instance.intersects(pP2P1N2, oom, rm));
        assertFalse(instance.intersects(pP2P0P2, oom, rm));
        assertFalse(instance.intersects(pP2P0P1, oom, rm));
        assertTrue(instance.intersects(pP2P0P0, oom, rm));
        assertFalse(instance.intersects(pP2P0N1, oom, rm));
        assertFalse(instance.intersects(pP2P0N2, oom, rm));
        assertFalse(instance.intersects(pP2N1P2, oom, rm));
        assertTrue(instance.intersects(pP2N1P1, oom, rm));
        assertFalse(instance.intersects(pP2N1P0, oom, rm));
        assertFalse(instance.intersects(pP2N1N1, oom, rm));
        assertFalse(instance.intersects(pP2N1N2, oom, rm));
        assertTrue(instance.intersects(pP2N2P2, oom, rm));
        assertFalse(instance.intersects(pP2N2P1, oom, rm));
        assertFalse(instance.intersects(pP2N2P0, oom, rm));
        assertFalse(instance.intersects(pP2N2N1, oom, rm));
        assertFalse(instance.intersects(pP2N2N2, oom, rm));
        // P1
        assertFalse(instance.intersects(pP1P2P2, oom, rm));
        assertFalse(instance.intersects(pP1P2P1, oom, rm));
        assertFalse(instance.intersects(pP1P2P0, oom, rm));
        assertFalse(instance.intersects(pP1P2N1, oom, rm));
        assertTrue(instance.intersects(pP1P2N2, oom, rm));
        assertFalse(instance.intersects(pP1P1P2, oom, rm));
        assertFalse(instance.intersects(pP1P1P1, oom, rm));
        assertFalse(instance.intersects(pP1P1P0, oom, rm));
        assertTrue(instance.intersects(pP1P1N1, oom, rm));
        assertFalse(instance.intersects(pP1P1N2, oom, rm));
        assertFalse(instance.intersects(pP1P0P2, oom, rm));
        assertFalse(instance.intersects(pP1P0P1, oom, rm));
        assertTrue(instance.intersects(pP1P0P0, oom, rm));
        assertFalse(instance.intersects(pP1P0N1, oom, rm));
        assertFalse(instance.intersects(pP1P0N2, oom, rm));
        assertFalse(instance.intersects(pP1N1P2, oom, rm));
        assertTrue(instance.intersects(pP1N1P1, oom, rm));
        assertFalse(instance.intersects(pP1N1P0, oom, rm));
        assertFalse(instance.intersects(pP1N1N1, oom, rm));
        assertFalse(instance.intersects(pP1N1N2, oom, rm));
        assertTrue(instance.intersects(pP1N2P2, oom, rm));
        assertFalse(instance.intersects(pP1N2P1, oom, rm));
        assertFalse(instance.intersects(pP1N2P0, oom, rm));
        assertFalse(instance.intersects(pP1N2N1, oom, rm));
        assertFalse(instance.intersects(pP1N2N2, oom, rm));
        // P0
        assertFalse(instance.intersects(pP0P2P2, oom, rm));
        assertFalse(instance.intersects(pP0P2P1, oom, rm));
        assertFalse(instance.intersects(pP0P2P0, oom, rm));
        assertFalse(instance.intersects(pP0P2N1, oom, rm));
        assertTrue(instance.intersects(pP0P2N2, oom, rm));
        assertFalse(instance.intersects(pP0P1P2, oom, rm));
        assertFalse(instance.intersects(pP0P1P1, oom, rm));
        assertFalse(instance.intersects(pP0P1P0, oom, rm));
        assertTrue(instance.intersects(pP0P1N1, oom, rm));
        assertFalse(instance.intersects(pP0P1N2, oom, rm));
        assertFalse(instance.intersects(pP0P0P2, oom, rm));
        assertFalse(instance.intersects(pP0P0P1, oom, rm));
        assertTrue(instance.intersects(pP0P0P0, oom, rm));
        assertFalse(instance.intersects(pP0P0N1, oom, rm));
        assertFalse(instance.intersects(pP0P0N2, oom, rm));
        assertFalse(instance.intersects(pP0N1P2, oom, rm));
        assertTrue(instance.intersects(pP0N1P1, oom, rm));
        assertFalse(instance.intersects(pP0N1P0, oom, rm));
        assertFalse(instance.intersects(pP0N1N1, oom, rm));
        assertFalse(instance.intersects(pP0N1N2, oom, rm));
        assertTrue(instance.intersects(pP0N2P2, oom, rm));
        assertFalse(instance.intersects(pP0N2P1, oom, rm));
        assertFalse(instance.intersects(pP0N2P0, oom, rm));
        assertFalse(instance.intersects(pP0N2N1, oom, rm));
        assertFalse(instance.intersects(pP0N2N2, oom, rm));
        // N1
        assertFalse(instance.intersects(pN1P2P2, oom, rm));
        assertFalse(instance.intersects(pN1P2P1, oom, rm));
        assertFalse(instance.intersects(pN1P2P0, oom, rm));
        assertFalse(instance.intersects(pN1P2N1, oom, rm));
        assertTrue(instance.intersects(pN1P2N2, oom, rm));
        assertFalse(instance.intersects(pN1P1P2, oom, rm));
        assertFalse(instance.intersects(pN1P1P1, oom, rm));
        assertFalse(instance.intersects(pN1P1P0, oom, rm));
        assertTrue(instance.intersects(pN1P1N1, oom, rm));
        assertFalse(instance.intersects(pN1P1N2, oom, rm));
        assertFalse(instance.intersects(pN1P0P2, oom, rm));
        assertFalse(instance.intersects(pN1P0P1, oom, rm));
        assertTrue(instance.intersects(pN1P0P0, oom, rm));
        assertFalse(instance.intersects(pN1P0N1, oom, rm));
        assertFalse(instance.intersects(pN1P0N2, oom, rm));
        assertFalse(instance.intersects(pN1N1P2, oom, rm));
        assertTrue(instance.intersects(pN1N1P1, oom, rm));
        assertFalse(instance.intersects(pN1N1P0, oom, rm));
        assertFalse(instance.intersects(pN1N1N1, oom, rm));
        assertFalse(instance.intersects(pN1N1N2, oom, rm));
        assertTrue(instance.intersects(pN1N2P2, oom, rm));
        assertFalse(instance.intersects(pN1N2P1, oom, rm));
        assertFalse(instance.intersects(pN1N2P0, oom, rm));
        assertFalse(instance.intersects(pN1N2N1, oom, rm));
        assertFalse(instance.intersects(pN1N2N2, oom, rm));
        // N2
        assertFalse(instance.intersects(pN2P2P2, oom, rm));
        assertFalse(instance.intersects(pN2P2P1, oom, rm));
        assertFalse(instance.intersects(pN2P2P0, oom, rm));
        assertFalse(instance.intersects(pN2P2N1, oom, rm));
        assertTrue(instance.intersects(pN2P2N2, oom, rm));
        assertFalse(instance.intersects(pN2P1P2, oom, rm));
        assertFalse(instance.intersects(pN2P1P1, oom, rm));
        assertFalse(instance.intersects(pN2P1P0, oom, rm));
        assertTrue(instance.intersects(pN2P1N1, oom, rm));
        assertFalse(instance.intersects(pN2P1N2, oom, rm));
        assertFalse(instance.intersects(pN2P0P2, oom, rm));
        assertFalse(instance.intersects(pN2P0P1, oom, rm));
        assertTrue(instance.intersects(pN2P0P0, oom, rm));
        assertFalse(instance.intersects(pN2P0N1, oom, rm));
        assertFalse(instance.intersects(pN2P0N2, oom, rm));
        assertFalse(instance.intersects(pN2N1P2, oom, rm));
        assertTrue(instance.intersects(pN2N1P1, oom, rm));
        assertFalse(instance.intersects(pN2N1P0, oom, rm));
        assertFalse(instance.intersects(pN2N1N1, oom, rm));
        assertFalse(instance.intersects(pN2N1N2, oom, rm));
        assertTrue(instance.intersects(pN2N2P2, oom, rm));
        assertFalse(instance.intersects(pN2N2P1, oom, rm));
        assertFalse(instance.intersects(pN2N2P0, oom, rm));
        assertFalse(instance.intersects(pN2N2N1, oom, rm));
        assertFalse(instance.intersects(pN2N2N2, oom, rm));
        // x=y-z
        instance = new V3D_Plane(env, P0P0P0, P1P1P0, N1P0P1, oom, rm);
        // P2
        assertFalse(instance.intersects(pP2P2P2, oom, rm));
        assertFalse(instance.intersects(pP2P2P1, oom, rm));
        assertTrue(instance.intersects(pP2P2P0, oom, rm));
        assertFalse(instance.intersects(pP2P2N1, oom, rm));
        assertFalse(instance.intersects(pP2P2N2, oom, rm));
        assertFalse(instance.intersects(pP2P1P2, oom, rm));
        assertFalse(instance.intersects(pP2P1P1, oom, rm));
        assertFalse(instance.intersects(pP2P1P0, oom, rm));
        assertTrue(instance.intersects(pP2P1N1, oom, rm));
        assertFalse(instance.intersects(pP2P1N2, oom, rm));
        assertFalse(instance.intersects(pP2P0P2, oom, rm));
        assertFalse(instance.intersects(pP2P0P1, oom, rm));
        assertFalse(instance.intersects(pP2P0P0, oom, rm));
        assertFalse(instance.intersects(pP2P0N1, oom, rm));
        assertTrue(instance.intersects(pP2P0N2, oom, rm));
        assertFalse(instance.intersects(pP2N1P2, oom, rm));
        assertFalse(instance.intersects(pP2N1P1, oom, rm));
        assertFalse(instance.intersects(pP2N1P0, oom, rm));
        assertFalse(instance.intersects(pP2N1N1, oom, rm));
        assertFalse(instance.intersects(pP2N1N2, oom, rm));
        assertFalse(instance.intersects(pP2N2P2, oom, rm));
        assertFalse(instance.intersects(pP2N2P1, oom, rm));
        assertFalse(instance.intersects(pP2N2P0, oom, rm));
        assertFalse(instance.intersects(pP2N2N1, oom, rm));
        assertFalse(instance.intersects(pP2N2N2, oom, rm));
        // P1
        assertFalse(instance.intersects(pP1P2P2, oom, rm));
        assertTrue(instance.intersects(pP1P2P1, oom, rm));
        assertFalse(instance.intersects(pP1P2P0, oom, rm));
        assertFalse(instance.intersects(pP1P2N1, oom, rm));
        assertFalse(instance.intersects(pP1P2N2, oom, rm));
        assertFalse(instance.intersects(pP1P1P2, oom, rm));
        assertFalse(instance.intersects(pP1P1P1, oom, rm));
        assertTrue(instance.intersects(pP1P1P0, oom, rm));
        assertFalse(instance.intersects(pP1P1N1, oom, rm));
        assertFalse(instance.intersects(pP1P1N2, oom, rm));
        assertFalse(instance.intersects(pP1P0P2, oom, rm));
        assertFalse(instance.intersects(pP1P0P1, oom, rm));
        assertFalse(instance.intersects(pP1P0P0, oom, rm));
        assertTrue(instance.intersects(pP1P0N1, oom, rm));
        assertFalse(instance.intersects(pP1P0N2, oom, rm));
        assertFalse(instance.intersects(pP1N1P2, oom, rm));
        assertFalse(instance.intersects(pP1N1P1, oom, rm));
        assertFalse(instance.intersects(pP1N1P0, oom, rm));
        assertFalse(instance.intersects(pP1N1N1, oom, rm));
        assertTrue(instance.intersects(pP1N1N2, oom, rm));
        assertFalse(instance.intersects(pP1N2P2, oom, rm));
        assertFalse(instance.intersects(pP1N2P1, oom, rm));
        assertFalse(instance.intersects(pP1N2P0, oom, rm));
        assertFalse(instance.intersects(pP1N2N1, oom, rm));
        assertFalse(instance.intersects(pP1N2N2, oom, rm));
        // P0
        assertTrue(instance.intersects(pP0P2P2, oom, rm));
        assertFalse(instance.intersects(pP0P2P1, oom, rm));
        assertFalse(instance.intersects(pP0P2P0, oom, rm));
        assertFalse(instance.intersects(pP0P2N1, oom, rm));
        assertFalse(instance.intersects(pP0P2N2, oom, rm));
        assertFalse(instance.intersects(pP0P1P2, oom, rm));
        assertTrue(instance.intersects(pP0P1P1, oom, rm));
        assertFalse(instance.intersects(pP0P1P0, oom, rm));
        assertFalse(instance.intersects(pP0P1N1, oom, rm));
        assertFalse(instance.intersects(pP0P1N2, oom, rm));
        assertFalse(instance.intersects(pP0P0P2, oom, rm));
        assertFalse(instance.intersects(pP0P0P1, oom, rm));
        assertTrue(instance.intersects(pP0P0P0, oom, rm));
        assertFalse(instance.intersects(pP0P0N1, oom, rm));
        assertFalse(instance.intersects(pP0P0N2, oom, rm));
        assertFalse(instance.intersects(pP0N1P2, oom, rm));
        assertFalse(instance.intersects(pP0N1P1, oom, rm));
        assertFalse(instance.intersects(pP0N1P0, oom, rm));
        assertTrue(instance.intersects(pP0N1N1, oom, rm));
        assertFalse(instance.intersects(pP0N1N2, oom, rm));
        assertFalse(instance.intersects(pP0N2P2, oom, rm));
        assertFalse(instance.intersects(pP0N2P1, oom, rm));
        assertFalse(instance.intersects(pP0N2P0, oom, rm));
        assertFalse(instance.intersects(pP0N2N1, oom, rm));
        assertTrue(instance.intersects(pP0N2N2, oom, rm));
        // N1
        assertFalse(instance.intersects(pN1P2P2, oom, rm));
        assertFalse(instance.intersects(pN1P2P1, oom, rm));
        assertFalse(instance.intersects(pN1P2P0, oom, rm));
        assertFalse(instance.intersects(pN1P2N1, oom, rm));
        assertFalse(instance.intersects(pN1P2N2, oom, rm));
        assertTrue(instance.intersects(pN1P1P2, oom, rm));
        assertFalse(instance.intersects(pN1P1P1, oom, rm));
        assertFalse(instance.intersects(pN1P1P0, oom, rm));
        assertFalse(instance.intersects(pN1P1N1, oom, rm));
        assertFalse(instance.intersects(pN1P1N2, oom, rm));
        assertFalse(instance.intersects(pN1P0P2, oom, rm));
        assertTrue(instance.intersects(pN1P0P1, oom, rm));
        assertFalse(instance.intersects(pN1P0P0, oom, rm));
        assertFalse(instance.intersects(pN1P0N1, oom, rm));
        assertFalse(instance.intersects(pN1P0N2, oom, rm));
        assertFalse(instance.intersects(pN1N1P2, oom, rm));
        assertFalse(instance.intersects(pN1N1P1, oom, rm));
        assertTrue(instance.intersects(pN1N1P0, oom, rm));
        assertFalse(instance.intersects(pN1N1N1, oom, rm));
        assertFalse(instance.intersects(pN1N1N2, oom, rm));
        assertFalse(instance.intersects(pN1N2P2, oom, rm));
        assertFalse(instance.intersects(pN1N2P1, oom, rm));
        assertFalse(instance.intersects(pN1N2P0, oom, rm));
        assertTrue(instance.intersects(pN1N2N1, oom, rm));
        assertFalse(instance.intersects(pN1N2N2, oom, rm));
        // N2
        assertFalse(instance.intersects(pN2P2P2, oom, rm));
        assertFalse(instance.intersects(pN2P2P1, oom, rm));
        assertFalse(instance.intersects(pN2P2P0, oom, rm));
        assertFalse(instance.intersects(pN2P2N1, oom, rm));
        assertFalse(instance.intersects(pN2P2N2, oom, rm));
        assertFalse(instance.intersects(pN2P1P2, oom, rm));
        assertFalse(instance.intersects(pN2P1P1, oom, rm));
        assertFalse(instance.intersects(pN2P1P0, oom, rm));
        assertFalse(instance.intersects(pN2P1N1, oom, rm));
        assertFalse(instance.intersects(pN2P1N2, oom, rm));
        assertTrue(instance.intersects(pN2P0P2, oom, rm));
        assertFalse(instance.intersects(pN2P0P1, oom, rm));
        assertFalse(instance.intersects(pN2P0P0, oom, rm));
        assertFalse(instance.intersects(pN2P0N1, oom, rm));
        assertFalse(instance.intersects(pN2P0N2, oom, rm));
        assertFalse(instance.intersects(pN2N1P2, oom, rm));
        assertTrue(instance.intersects(pN2N1P1, oom, rm));
        assertFalse(instance.intersects(pN2N1P0, oom, rm));
        assertFalse(instance.intersects(pN2N1N1, oom, rm));
        assertFalse(instance.intersects(pN2N1N2, oom, rm));
        assertFalse(instance.intersects(pN2N2P2, oom, rm));
        assertFalse(instance.intersects(pN2N2P1, oom, rm));
        assertTrue(instance.intersects(pN2N2P0, oom, rm));
        assertFalse(instance.intersects(pN2N2N1, oom, rm));
        assertFalse(instance.intersects(pN2N2N2, oom, rm));
        // x=z-y
        instance = new V3D_Plane(env, P1P0P1, P0P1P1, P0P0P0, oom, rm);
        // P2
        assertFalse(instance.intersects(pP2P2P2, oom, rm));
        assertFalse(instance.intersects(pP2P2P1, oom, rm));
        assertFalse(instance.intersects(pP2P2P0, oom, rm));
        assertFalse(instance.intersects(pP2P2N1, oom, rm));
        assertFalse(instance.intersects(pP2P2N2, oom, rm));
        assertFalse(instance.intersects(pP2P1P2, oom, rm));
        assertFalse(instance.intersects(pP2P1P1, oom, rm));
        assertFalse(instance.intersects(pP2P1P0, oom, rm));
        assertFalse(instance.intersects(pP2P1N1, oom, rm));
        assertFalse(instance.intersects(pP2P1N2, oom, rm));
        assertTrue(instance.intersects(pP2P0P2, oom, rm));
        assertFalse(instance.intersects(pP2P0P1, oom, rm));
        assertFalse(instance.intersects(pP2P0P0, oom, rm));
        assertFalse(instance.intersects(pP2P0N1, oom, rm));
        assertFalse(instance.intersects(pP2P0N2, oom, rm));
        assertFalse(instance.intersects(pP2N1P2, oom, rm));
        assertTrue(instance.intersects(pP2N1P1, oom, rm));
        assertFalse(instance.intersects(pP2N1P0, oom, rm));
        assertFalse(instance.intersects(pP2N1N1, oom, rm));
        assertFalse(instance.intersects(pP2N1N2, oom, rm));
        assertFalse(instance.intersects(pP2N2P2, oom, rm));
        assertFalse(instance.intersects(pP2N2P1, oom, rm));
        assertTrue(instance.intersects(pP2N2P0, oom, rm));
        assertFalse(instance.intersects(pP2N2N1, oom, rm));
        assertFalse(instance.intersects(pP2N2N2, oom, rm));
        // P1
        assertFalse(instance.intersects(pP1P2P2, oom, rm));
        assertFalse(instance.intersects(pP1P2P1, oom, rm));
        assertFalse(instance.intersects(pP1P2P0, oom, rm));
        assertFalse(instance.intersects(pP1P2N1, oom, rm));
        assertFalse(instance.intersects(pP1P2N2, oom, rm));
        assertTrue(instance.intersects(pP1P1P2, oom, rm));
        assertFalse(instance.intersects(pP1P1P1, oom, rm));
        assertFalse(instance.intersects(pP1P1P0, oom, rm));
        assertFalse(instance.intersects(pP1P1N1, oom, rm));
        assertFalse(instance.intersects(pP1P1N2, oom, rm));
        assertFalse(instance.intersects(pP1P0P2, oom, rm));
        assertTrue(instance.intersects(pP1P0P1, oom, rm));
        assertFalse(instance.intersects(pP1P0P0, oom, rm));
        assertFalse(instance.intersects(pP1P0N1, oom, rm));
        assertFalse(instance.intersects(pP1P0N2, oom, rm));
        assertFalse(instance.intersects(pP1N1P2, oom, rm));
        assertFalse(instance.intersects(pP1N1P1, oom, rm));
        assertTrue(instance.intersects(pP1N1P0, oom, rm));
        assertFalse(instance.intersects(pP1N1N1, oom, rm));
        assertFalse(instance.intersects(pP1N1N2, oom, rm));
        assertFalse(instance.intersects(pP1N2P2, oom, rm));
        assertFalse(instance.intersects(pP1N2P1, oom, rm));
        assertFalse(instance.intersects(pP1N2P0, oom, rm));
        assertTrue(instance.intersects(pP1N2N1, oom, rm));
        assertFalse(instance.intersects(pP1N2N2, oom, rm));
        // P0
        assertTrue(instance.intersects(pP0P2P2, oom, rm));
        assertFalse(instance.intersects(pP0P2P1, oom, rm));
        assertFalse(instance.intersects(pP0P2P0, oom, rm));
        assertFalse(instance.intersects(pP0P2N1, oom, rm));
        assertFalse(instance.intersects(pP0P2N2, oom, rm));
        assertFalse(instance.intersects(pP0P1P2, oom, rm));
        assertTrue(instance.intersects(pP0P1P1, oom, rm));
        assertFalse(instance.intersects(pP0P1P0, oom, rm));
        assertFalse(instance.intersects(pP0P1N1, oom, rm));
        assertFalse(instance.intersects(pP0P1N2, oom, rm));
        assertFalse(instance.intersects(pP0P0P2, oom, rm));
        assertFalse(instance.intersects(pP0P0P1, oom, rm));
        assertTrue(instance.intersects(pP0P0P0, oom, rm));
        assertFalse(instance.intersects(pP0P0N1, oom, rm));
        assertFalse(instance.intersects(pP0P0N2, oom, rm));
        assertFalse(instance.intersects(pP0N1P2, oom, rm));
        assertFalse(instance.intersects(pP0N1P1, oom, rm));
        assertFalse(instance.intersects(pP0N1P0, oom, rm));
        assertTrue(instance.intersects(pP0N1N1, oom, rm));
        assertFalse(instance.intersects(pP0N1N2, oom, rm));
        assertFalse(instance.intersects(pP0N2P2, oom, rm));
        assertFalse(instance.intersects(pP0N2P1, oom, rm));
        assertFalse(instance.intersects(pP0N2P0, oom, rm));
        assertFalse(instance.intersects(pP0N2N1, oom, rm));
        assertTrue(instance.intersects(pP0N2N2, oom, rm));
        // N1
        assertFalse(instance.intersects(pN1P2P2, oom, rm));
        assertTrue(instance.intersects(pN1P2P1, oom, rm));
        assertFalse(instance.intersects(pN1P2P0, oom, rm));
        assertFalse(instance.intersects(pN1P2N1, oom, rm));
        assertFalse(instance.intersects(pN1P2N2, oom, rm));
        assertFalse(instance.intersects(pN1P1P2, oom, rm));
        assertFalse(instance.intersects(pN1P1P1, oom, rm));
        assertTrue(instance.intersects(pN1P1P0, oom, rm));
        assertFalse(instance.intersects(pN1P1N1, oom, rm));
        assertFalse(instance.intersects(pN1P1N2, oom, rm));
        assertFalse(instance.intersects(pN1P0P2, oom, rm));
        assertFalse(instance.intersects(pN1P0P1, oom, rm));
        assertFalse(instance.intersects(pN1P0P0, oom, rm));
        assertTrue(instance.intersects(pN1P0N1, oom, rm));
        assertFalse(instance.intersects(pN1P0N2, oom, rm));
        assertFalse(instance.intersects(pN1N1P2, oom, rm));
        assertFalse(instance.intersects(pN1N1P1, oom, rm));
        assertFalse(instance.intersects(pN1N1P0, oom, rm));
        assertFalse(instance.intersects(pN1N1N1, oom, rm));
        assertTrue(instance.intersects(pN1N1N2, oom, rm));
        assertFalse(instance.intersects(pN1N2P2, oom, rm));
        assertFalse(instance.intersects(pN1N2P1, oom, rm));
        assertFalse(instance.intersects(pN1N2P0, oom, rm));
        assertFalse(instance.intersects(pN1N2N1, oom, rm));
        assertFalse(instance.intersects(pN1N2N2, oom, rm));
        // N2
        assertFalse(instance.intersects(pN2P2P2, oom, rm));
        assertFalse(instance.intersects(pN2P2P1, oom, rm));
        assertTrue(instance.intersects(pN2P2P0, oom, rm));
        assertFalse(instance.intersects(pN2P2N1, oom, rm));
        assertFalse(instance.intersects(pN2P2N2, oom, rm));
        assertFalse(instance.intersects(pN2P1P2, oom, rm));
        assertFalse(instance.intersects(pN2P1P1, oom, rm));
        assertFalse(instance.intersects(pN2P1P0, oom, rm));
        assertTrue(instance.intersects(pN2P1N1, oom, rm));
        assertFalse(instance.intersects(pN2P1N2, oom, rm));
        assertFalse(instance.intersects(pN2P0P2, oom, rm));
        assertFalse(instance.intersects(pN2P0P1, oom, rm));
        assertFalse(instance.intersects(pN2P0P0, oom, rm));
        assertFalse(instance.intersects(pN2P0N1, oom, rm));
        assertTrue(instance.intersects(pN2P0N2, oom, rm));
        assertFalse(instance.intersects(pN2N1P2, oom, rm));
        assertFalse(instance.intersects(pN2N1P1, oom, rm));
        assertFalse(instance.intersects(pN2N1P0, oom, rm));
        assertFalse(instance.intersects(pN2N1N1, oom, rm));
        assertFalse(instance.intersects(pN2N1N2, oom, rm));
        assertFalse(instance.intersects(pN2N2P2, oom, rm));
        assertFalse(instance.intersects(pN2N2P1, oom, rm));
        assertFalse(instance.intersects(pN2N2P0, oom, rm));
        assertFalse(instance.intersects(pN2N2N1, oom, rm));
        assertFalse(instance.intersects(pN2N2N2, oom, rm));
        // y=x-z
        instance = new V3D_Plane(env, P1P1P0, P0P0P0, P0N1P1, oom, rm);
        // P2
        assertFalse(instance.intersects(pP2P2P2, oom, rm));
        assertFalse(instance.intersects(pP2P2P1, oom, rm));
        assertTrue(instance.intersects(pP2P2P0, oom, rm));
        assertFalse(instance.intersects(pP2P2N1, oom, rm));
        assertFalse(instance.intersects(pP2P2N2, oom, rm));
        assertFalse(instance.intersects(pP2P1P2, oom, rm));
        assertTrue(instance.intersects(pP2P1P1, oom, rm));
        assertFalse(instance.intersects(pP2P1P0, oom, rm));
        assertFalse(instance.intersects(pP2P1N1, oom, rm));
        assertFalse(instance.intersects(pP2P1N2, oom, rm));
        assertTrue(instance.intersects(pP2P0P2, oom, rm));
        assertFalse(instance.intersects(pP2P0P1, oom, rm));
        assertFalse(instance.intersects(pP2P0P0, oom, rm));
        assertFalse(instance.intersects(pP2P0N1, oom, rm));
        assertFalse(instance.intersects(pP2P0N2, oom, rm));
        assertFalse(instance.intersects(pP2N1P2, oom, rm));
        assertFalse(instance.intersects(pP2N1P1, oom, rm));
        assertFalse(instance.intersects(pP2N1P0, oom, rm));
        assertFalse(instance.intersects(pP2N1N1, oom, rm));
        assertFalse(instance.intersects(pP2N1N2, oom, rm));
        assertFalse(instance.intersects(pP2N2P2, oom, rm));
        assertFalse(instance.intersects(pP2N2P1, oom, rm));
        assertFalse(instance.intersects(pP2N2P0, oom, rm));
        assertFalse(instance.intersects(pP2N2N1, oom, rm));
        assertFalse(instance.intersects(pP2N2N2, oom, rm));
        // P1
        assertFalse(instance.intersects(pP1P2P2, oom, rm));
        assertFalse(instance.intersects(pP1P2P1, oom, rm));
        assertFalse(instance.intersects(pP1P2P0, oom, rm));
        assertTrue(instance.intersects(pP1P2N1, oom, rm));
        assertFalse(instance.intersects(pP1P2N2, oom, rm));
        assertFalse(instance.intersects(pP1P1P2, oom, rm));
        assertFalse(instance.intersects(pP1P1P1, oom, rm));
        assertTrue(instance.intersects(pP1P1P0, oom, rm));
        assertFalse(instance.intersects(pP1P1N1, oom, rm));
        assertFalse(instance.intersects(pP1P1N2, oom, rm));
        assertFalse(instance.intersects(pP1P0P2, oom, rm));
        assertTrue(instance.intersects(pP1P0P1, oom, rm));
        assertFalse(instance.intersects(pP1P0P0, oom, rm));
        assertFalse(instance.intersects(pP1P0N1, oom, rm));
        assertFalse(instance.intersects(pP1P0N2, oom, rm));
        assertTrue(instance.intersects(pP1N1P2, oom, rm));
        assertFalse(instance.intersects(pP1N1P1, oom, rm));
        assertFalse(instance.intersects(pP1N1P0, oom, rm));
        assertFalse(instance.intersects(pP1N1N1, oom, rm));
        assertFalse(instance.intersects(pP1N1N2, oom, rm));
        assertFalse(instance.intersects(pP1N2P2, oom, rm));
        assertFalse(instance.intersects(pP1N2P1, oom, rm));
        assertFalse(instance.intersects(pP1N2P0, oom, rm));
        assertFalse(instance.intersects(pP1N2N1, oom, rm));
        assertFalse(instance.intersects(pP1N2N2, oom, rm));
        // P0
        assertFalse(instance.intersects(pP0P2P2, oom, rm));
        assertFalse(instance.intersects(pP0P2P1, oom, rm));
        assertFalse(instance.intersects(pP0P2P0, oom, rm));
        assertFalse(instance.intersects(pP0P2N1, oom, rm));
        assertTrue(instance.intersects(pP0P2N2, oom, rm));
        assertFalse(instance.intersects(pP0P1P2, oom, rm));
        assertFalse(instance.intersects(pP0P1P1, oom, rm));
        assertFalse(instance.intersects(pP0P1P0, oom, rm));
        assertTrue(instance.intersects(pP0P1N1, oom, rm));
        assertFalse(instance.intersects(pP0P1N2, oom, rm));
        assertFalse(instance.intersects(pP0P0P2, oom, rm));
        assertFalse(instance.intersects(pP0P0P1, oom, rm));
        assertTrue(instance.intersects(pP0P0P0, oom, rm));
        assertFalse(instance.intersects(pP0P0N1, oom, rm));
        assertFalse(instance.intersects(pP0P0N2, oom, rm));
        assertFalse(instance.intersects(pP0N1P2, oom, rm));
        assertTrue(instance.intersects(pP0N1P1, oom, rm));
        assertFalse(instance.intersects(pP0N1P0, oom, rm));
        assertFalse(instance.intersects(pP0N1N1, oom, rm));
        assertFalse(instance.intersects(pP0N1N2, oom, rm));
        assertTrue(instance.intersects(pP0N2P2, oom, rm));
        assertFalse(instance.intersects(pP0N2P1, oom, rm));
        assertFalse(instance.intersects(pP0N2P0, oom, rm));
        assertFalse(instance.intersects(pP0N2N1, oom, rm));
        assertFalse(instance.intersects(pP0N2N2, oom, rm));
        // N1
        assertFalse(instance.intersects(pN1P2P2, oom, rm));
        assertFalse(instance.intersects(pN1P2P1, oom, rm));
        assertFalse(instance.intersects(pN1P2P0, oom, rm));
        assertFalse(instance.intersects(pN1P2N1, oom, rm));
        assertFalse(instance.intersects(pN1P2N2, oom, rm));
        assertFalse(instance.intersects(pN1P1P2, oom, rm));
        assertFalse(instance.intersects(pN1P1P1, oom, rm));
        assertFalse(instance.intersects(pN1P1P0, oom, rm));
        assertFalse(instance.intersects(pN1P1N1, oom, rm));
        assertTrue(instance.intersects(pN1P1N2, oom, rm));
        assertFalse(instance.intersects(pN1P0P2, oom, rm));
        assertFalse(instance.intersects(pN1P0P1, oom, rm));
        assertFalse(instance.intersects(pN1P0P0, oom, rm));
        assertTrue(instance.intersects(pN1P0N1, oom, rm));
        assertFalse(instance.intersects(pN1P0N2, oom, rm));
        assertFalse(instance.intersects(pN1N1P2, oom, rm));
        assertFalse(instance.intersects(pN1N1P1, oom, rm));
        assertTrue(instance.intersects(pN1N1P0, oom, rm));
        assertFalse(instance.intersects(pN1N1N1, oom, rm));
        assertFalse(instance.intersects(pN1N1N2, oom, rm));
        assertFalse(instance.intersects(pN1N2P2, oom, rm));
        assertTrue(instance.intersects(pN1N2P1, oom, rm));
        assertFalse(instance.intersects(pN1N2P0, oom, rm));
        assertFalse(instance.intersects(pN1N2N1, oom, rm));
        assertFalse(instance.intersects(pN1N2N2, oom, rm));
        // N2
        assertFalse(instance.intersects(pN2P2P2, oom, rm));
        assertFalse(instance.intersects(pN2P2P1, oom, rm));
        assertFalse(instance.intersects(pN2P2P0, oom, rm));
        assertFalse(instance.intersects(pN2P2N1, oom, rm));
        assertFalse(instance.intersects(pN2P2N2, oom, rm));
        assertFalse(instance.intersects(pN2P1P2, oom, rm));
        assertFalse(instance.intersects(pN2P1P1, oom, rm));
        assertFalse(instance.intersects(pN2P1P0, oom, rm));
        assertFalse(instance.intersects(pN2P1N1, oom, rm));
        assertFalse(instance.intersects(pN2P1N2, oom, rm));
        assertFalse(instance.intersects(pN2P0P2, oom, rm));
        assertFalse(instance.intersects(pN2P0P1, oom, rm));
        assertFalse(instance.intersects(pN2P0P0, oom, rm));
        assertFalse(instance.intersects(pN2P0N1, oom, rm));
        assertTrue(instance.intersects(pN2P0N2, oom, rm));
        assertFalse(instance.intersects(pN2N1P2, oom, rm));
        assertFalse(instance.intersects(pN2N1P1, oom, rm));
        assertFalse(instance.intersects(pN2N1P0, oom, rm));
        assertTrue(instance.intersects(pN2N1N1, oom, rm));
        assertFalse(instance.intersects(pN2N1N2, oom, rm));
        assertFalse(instance.intersects(pN2N2P2, oom, rm));
        assertFalse(instance.intersects(pN2N2P1, oom, rm));
        assertTrue(instance.intersects(pN2N2P0, oom, rm));
        assertFalse(instance.intersects(pN2N2N1, oom, rm));
        assertFalse(instance.intersects(pN2N2N2, oom, rm));
        // Test 2 from https://math.stackexchange.com/questions/2686606/equation-of-a-plane-passing-through-3-points        
        V3D_Vector n = new V3D_Vector(1, -2, 1);
        V3D_Point p = pP1N2P1;
        instance = new V3D_Plane(p, n);
        assertTrue(instance.intersects(new V3D_Point(env, 4, -2, -2), oom, rm));
        assertTrue(instance.intersects(new V3D_Point(env, 4, 1, 4), oom, rm));
        n = new V3D_Vector(1, -2, 1);
        p = new V3D_Point(env, 4, -2, -2);
        instance = new V3D_Plane(p, n);
        assertTrue(instance.intersects(new V3D_Point(env, 1, -2, 1), oom, rm));
        assertTrue(instance.intersects(new V3D_Point(env, 4, 1, 4), oom, rm));
        // Test 3
        n = new V3D_Vector(1, -2, 1);
        p = pP0P0P0;
        instance = new V3D_Plane(p, n);
        assertTrue(instance.intersects(new V3D_Point(env, 3, 0, -3), oom, rm));
        assertTrue(instance.intersects(new V3D_Point(env, 3, 3, 3), oom, rm));
        n = new V3D_Vector(1, -2, 1);
        p = new V3D_Point(env, 3, 0, -3);
        instance = new V3D_Plane(p, n);
        assertTrue(instance.intersects(new V3D_Point(env, 0, 0, 0), oom, rm));
        assertTrue(instance.intersects(new V3D_Point(env, 3, 3, 3), oom, rm));
    }

    /**
     * Test of getNormalVector method, of class V3D_Plane.
     */
    @Test
    public void testGetNormalVector() {
        System.out.println("getNormalVector");
        // Z = 0
        V3D_Plane instance = new V3D_Plane(env, P0P0P0, P1P0P0, P0P1P0, oom, rm);
        V3D_Vector expResult = P0P0P1;
        V3D_Vector result = instance.getN();
        assertTrue(expResult.equals(result));
        // Z = -1
        instance = new V3D_Plane(env, P0P0N1, P1P0N1, P0P1N1, oom, rm);
        //expResult = P0P0N1;
        expResult = P0P0P1;
        result = instance.getN();
        assertTrue(expResult.equals(result));
        // Z = 1
        instance = new V3D_Plane(env, P0P0P1, P1P0P1, P0P1P1, oom, rm);
        //expResult = P0P0N1;
        expResult = P0P0P1;
        result = instance.getN();
        assertTrue(expResult.equals(result));
        // Z = 1
        instance = new V3D_Plane(env, P1P0P1, P0P1P1, P0P0P1, oom, rm);
        //expResult = P0P0N1;
        expResult = P0P0P1;
        result = instance.getN();
        assertTrue(expResult.equals(result));
        // Z = 1
        instance = new V3D_Plane(env, P0P1P1, P0P0P1, P1P0P1, oom, rm);
        //expResult = new V3D_Vector(P0P0N1);
        expResult = P0P0P1;
        result = instance.getN();
        assertTrue(expResult.equals(result));
        // Y = 0
        instance = new V3D_Plane(env, P0P0P0, P0P1P0, P0P0N1, oom, rm);
        //expResult = new V3D_Vector(P1P0P0, oom, rm);
        expResult = N1P0P0;
        result = instance.getN();
        assertTrue(expResult.equals(result));
        // X = 0
        instance = new V3D_Plane(env, P0P0P0, P1P0P0, P0P0N1, oom, rm);
        //expResult = new V3D_Vector(P0N1P0);
        expResult = P0P1P0;
        result = instance.getN();
        assertTrue(expResult.equals(result));
        // Y = 0
        instance = new V3D_Plane(env, P0P0P0, P1P0P0, N1P0P1, oom, rm);
        expResult = P0N1P0;
        result = instance.getN();
        assertTrue(expResult.equals(result));
        // 
        instance = new V3D_Plane(env, P0P1P0, P1P1P1, P1P0P0, oom, rm);
        //expResult = new V3D_Vector(N1N1P1);
        expResult = P1P1N1;
        result = instance.getN();
        assertTrue(expResult.equals(result));
        // X = 0
        instance = new V3D_Plane(env, P0P0P0, P0P1P1, P0N1P0, oom, rm);
        //expResult = new V3D_Vector(N1P0P0);
        expResult = P1P0P0;
        result = instance.getN();
        assertTrue(expResult.equals(result));
        // 
        instance = new V3D_Plane(env, P0P0P0, P1P1P1, P0N1N1, oom, rm);
        //expResult = new V3D_Vector(P0N1P1);
        expResult = P0P1N1;
        result = instance.getN();
        assertTrue(expResult.equals(result));
    }

//    /**
//     * Test of intersects method, of class V3D_Plane.
//     */
//    @Test
//    public void testIsIntersectedBy_V3D_Line_int() {
//        System.out.println("intersects");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_Line l = new V3D_Line(pP0P0P0, pP0P1P0, oom, rm); // Y axis
//        V3D_Plane instance = new V3D_Plane(env, P1P0P1, P0P0P1, N1P0N1, oom, rm); // Y=0 plane
//        assertTrue(instance.intersects(l, oom, rm));
//        // Test 2
//        l = new V3D_Line(pN1N1N1, pP1P1P1, oom, rm);
//        instance = new V3D_Plane(env, P1P1N1, P1N1N1, N1P1N1, oom, rm); // z=-1 plane
//        assertTrue(instance.intersects(l, oom, rm));
//        // Test 2
//        l = new V3D_Line(pP0P0P0, pP1P1P1, oom, rm);
//        instance = new V3D_Plane(env, P1P1N1, P1N1N1, N1P1N1, oom, rm); // z=-2 plane
//        assertTrue(instance.intersects(l, oom, rm));
//    }
    /**
     * Test of isParallel method, of class V3D_Plane.
     */
    @Test
    public void testIsParallel_V3D_Plane_int() {
        System.out.println("isParallel");
        V3D_Plane p = new V3D_Plane(env, P1P1P0, P1N1P0, N1P1P0, oom, rm);
        V3D_Plane instance = new V3D_Plane(env, P1P1P1, P1N1P1, N1P1P1, oom, rm);
        assertTrue(instance.isParallel(p, oom, rm));
        // Test 2
        instance = new V3D_Plane(env, P1P1N1, P1N1N1, N1P1N1, oom, rm);
        assertTrue(instance.isParallel(p, oom, rm));
        // Test 3
        instance = new V3D_Plane(env, P1P1N1, P1N1N1, N1P1N1, oom, rm);
        assertTrue(instance.isParallel(p, oom, rm));
        // Test 4
        p = V3D_Plane.X0;
        instance = V3D_Plane.Y0;
        assertFalse(instance.isParallel(p, oom, rm));
        // Test 5
        p = V3D_Plane.X0;
        instance = V3D_Plane.Z0;
        assertFalse(instance.isParallel(p, oom, rm));
        // Test 6
        p = V3D_Plane.Y0;
        instance = V3D_Plane.Z0;
        assertFalse(instance.isParallel(p, oom, rm));
        // Test 7
        p = new V3D_Plane(env, P0P0P0, P0P1P0, P1P1P1, oom, rm);
        instance = new V3D_Plane(env, P1P0P0, P1P1P0, P2P1P1, oom, rm);
        assertTrue(instance.isParallel(p, oom, rm));
        // Test 8
        instance = new V3D_Plane(env, P1N1P0, P1P0P0, P2P0P1, oom, rm);
        assertTrue(instance.isParallel(p, oom, rm));
        // Test 9
        instance = new V3D_Plane(env, P1P0P0, P1P1P0, P1P1P1, oom, rm);
        assertFalse(instance.isParallel(p, oom, rm));
    }

    /**
     * Test of isParallel method, of class V3D_Plane.
     */
    @Test
    public void testIsParallel_V3D_Line_int() {
        System.out.println("isParallel");
        V3D_Line l = new V3D_Line(pP0P0P0, V3D_Vector.I); 
        V3D_Plane instance = V3D_Plane.Y0;
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 2
        instance = V3D_Plane.Z0;
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 3
        instance = V3D_Plane.X0;
        assertFalse(instance.isParallel(l, oom, rm));
        // Test 4
        l = new V3D_Line(pP0P0P0, V3D_Vector.J); 
        instance = V3D_Plane.X0;
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 5
        instance = V3D_Plane.Y0;
        assertFalse(instance.isParallel(l, oom, rm));
        // Test 6
        instance = V3D_Plane.Z0;
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 7
        l = new V3D_Line(pP0P0P0, V3D_Vector.K); 
        instance = V3D_Plane.X0;
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 8
        instance = V3D_Plane.Y0;
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 9
        instance = V3D_Plane.Z0;
        assertFalse(instance.isParallel(l, oom, rm));
    }

    /**
     * Test of getIntersect method, of class V3D_Plane. The following can be
     * used for creating test cases:
     * http://www.ambrsoft.com/TrigoCalc/Plan3D/Plane3D_.htm
     */
    @Test
    public void testGetIntersection_V3D_Plane() {
        System.out.println("getIntersect");
        V3D_Plane pl;
        V3D_Plane instance;
        V3D_Geometry expResult;
        V3D_Geometry result;
        /**
         * The following is from:
         * https://www.microsoft.com/en-us/research/wp-content/uploads/2016/02/note.doc
         * Two Planar patches.
         */
        int oom = - 10;
        pl = new V3D_Plane(
                new V3D_Point(env,
                        BigRational.valueOf(8).divide(3),
                        BigRational.valueOf(-2).divide(3),
                        BigRational.ZERO),
                new V3D_Vector(2, 8, 0));
        //new V3D_Vector(2, 8, 0), oom, rmN5);
        instance = new V3D_Plane(
                new V3D_Point(env,
                        BigRational.valueOf(8).divide(3),
                        BigRational.ZERO,
                        BigRational.valueOf(-2).divide(3)),
                new V3D_Vector(2, 0, 8));
        //new V3D_Vector(2, 0, 8), oom, rmN5);
        expResult = new V3D_Line(env,
                new V3D_Vector(
                        BigRational.valueOf(68).divide(27),
                        BigRational.valueOf(-17).divide(27),
                        BigRational.valueOf(-17).divide(27)),
                new V3D_Vector(
                        BigRational.valueOf(1).divide(4),
                        BigRational.valueOf(-1).divide(16),
                        BigRational.valueOf(-1).divide(16)));
        result = instance.getIntersect(pl, oom, rm);
        //assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
//        /**
//         * The following is from:
//         * https://www.microsoft.com/en-us/research/wp-content/uploads/2016/02/note.doc
//         * Simple. Something is not right with this test which had worked at
//         * some stage... :(
//         */
//        oom = -10;
//        pl = new V3D_Plane(new V3D_Point(7, 11, 0), new V3D_Vector(0, 0, 3), oom, rm);
//        instance = new V3D_Plane(new V3D_Point(1, 0, 0), new V3D_Vector(5, 5, 0), oom, rm);
//        BigRational half = P1.divide(2);
//        V3D_Point p2 = new V3D_Point(half, half, BigRational.ZERO);
//        //V3D_Point p2 = new V3D_Point(0.5d, 0.5d, 0);
//        assertTrue(V3D_Plane.isCoplanar(oom, rm, pl, p2));
//        V3D_Vector v2 = new V3D_Vector(-15, 15, 0);
//        //assertTrue(V3D_Geometrics.isCoplanar(oom, rm, pl, p2.translate(v2, oom, rm)));
//        assertTrue(V3D_Plane.isCoplanar(oom, rm, pl, new V3D_Point(p2.offset, p2.rel.add(v2, oom, rm))));
//        //assertTrue(V3D_Geometrics.isCoplanar(oom, rm, pl, new V3D_Point(p2.offset.add(v2, oom, rm), p2.rel)));
//
//        //expResult = new V3D_Line(p2, v2, oom, rm);
//        expResult = new V3D_Line(p2.getVector(oom, rm), v2);
//        //expResult = new V3D_Line(p2.offset, p2.rel, v2, oom, rm);
//
//        result = instance.getIntersect(pl, oom, rm);
//        //System.out.println(result);
//        //System.out.println(expResult);
//        result = instance.getIntersect(pl, oom, rm);
//        //assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
//        //assertTrue(expResult.equals(result));
//        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test V3D_Plane.X0
        pl = V3D_Plane.X0;
        // Test 1
        oom = -8;
        instance = V3D_Plane.X0;
        expResult = V3D_Plane.X0;
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Plane) expResult).equalsIgnoreOrientation((V3D_Plane) result, oom, rm));
        // Test 2
        instance = V3D_Plane.Y0;
        expResult= new V3D_Line(pP0P0P0, V3D_Vector.K); 
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 3
        instance = V3D_Plane.Z0;
        expResult= new V3D_Line(pP0P0P0, V3D_Vector.J); 
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));

        pl = new V3D_Plane(V3D_Plane.X0);
        pl.translate(P1P0P0, oom, rm); // X = 1 Plane
        // Test 1
        oom = -8;
        instance = V3D_Plane.X0;
        assertNull(instance.getIntersect(pl, oom, rm));
        // Test 2
        instance = V3D_Plane.Y0;
        expResult= new V3D_Line(pP0P0P0, pP0P0P1, oom, rm);
        //expResult = new V3D_Line(pP0P0P0, V3D_Vector.K); 
        expResult.translate(P1P0P0, oom, rm);
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 3
        instance = V3D_Plane.Z0;
        expResult = new V3D_Line(pP0P0P0, pP0P1P0, oom, rm);
        //expResult= new V3D_Line(pP0P0P0, V3D_Vector.J); 
        expResult.translate(P1P0P0, oom, rm);
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));

        // Test V3D_Plane.Y0
        pl = V3D_Plane.Y0;
        // Test 1
        oom = -8;
        instance = V3D_Plane.X0;
        expResult= new V3D_Line(pP0P0P0, V3D_Vector.K); 
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 2
        instance = V3D_Plane.Y0;
        expResult = V3D_Plane.Y0;
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Plane) expResult).equals((V3D_Plane) result, oom, rm));
        // Test 3
        instance = V3D_Plane.Z0;
        expResult= new V3D_Line(pP0P0P0, V3D_Vector.I); 
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));

        // Test V3D_Plane.Z0
        pl = V3D_Plane.Z0;
        // Test 1
        oom = -8;
        instance = V3D_Plane.X0;
        expResult= new V3D_Line(pP0P0P0, V3D_Vector.J); 
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 2
        instance = V3D_Plane.Y0;
        expResult= new V3D_Line(pP0P0P0, V3D_Vector.I); 
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 3
        instance = V3D_Plane.Z0;
        expResult = V3D_Plane.Z0;
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Plane) expResult).equals((V3D_Plane) result, oom, rm));

        pl = V3D_Plane.X0;
        // Test 4
        instance = new V3D_Plane(pN1P1N1, pP0P1P0, pP1P1N1, oom, rm); // y=1
        expResult = new V3D_Line(pP0P1P0, pP0P1P1, oom, rm);    // x=0, y=1
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 5
        instance = new V3D_Plane(pP1P0P1, pP0N1P1, pP0P0P1, oom, rm); // z=1
        expResult = new V3D_Line(pP0P1P1, pP0P0P1, oom, rm);    // x=0, z=1
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 6 to 9
        pl = V3D_Plane.Y0;
        // Test 6
        instance = V3D_Plane.X0;
        expResult = new V3D_Line(pP0P0N1, pP0P0P1, oom, rm);          // x=0, y=0
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 7
        instance = V3D_Plane.Z0;
        expResult = new V3D_Line(pP0P0P0, pP1P0P0, oom, rm);          // y=0, z=0
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 8
        instance = new V3D_Plane(pP1P0P0, pP1P1P0, pP1P0P1, oom, rm);       // x=1
        expResult = new V3D_Line(pP1P0N1, pP1P0P1, oom, rm);          // x=1, y=0
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 9
        instance = new V3D_Plane(pP0P1P1, pP1P1P1, pP0P0P1, oom, rm);       // z=1
        expResult = new V3D_Line(pP0P0P1, pP1P0P1, oom, rm);          // y=0, z=1
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 10 to 13
        pl = V3D_Plane.Z0;
        // Test 10
        instance = new V3D_Plane(env, P0P0P0, V3D_Vector.J, V3D_Vector.K, oom, rm); // x=0
        expResult = new V3D_Line(pP0N1P0, pP0P1P0, oom, rm);          // x=0, z=0
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 11
        instance = new V3D_Plane(env, V3D_Vector.I, P0P0P0, V3D_Vector.K, oom, rm); // y=0
        expResult = new V3D_Line(pN1P0P0, pP1P0P0, oom, rm);          // y=0, z=0
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 12
        instance = new V3D_Plane(pP1P0P0, pP1P1P1, pP1P0P1, oom, rm); // x=1
        expResult = new V3D_Line(pP1N1P0, pP1P1P0, oom, rm);    // x=1, z=0
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 13
        instance = new V3D_Plane(pP1P1P1, pP0P1P0, pP1P1P0, oom, rm); // y=1
        expResult = new V3D_Line(pN1P1P0, pP1P1P0, oom, rm);    // y=1, z=0
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 14 to 15
        pl = new V3D_Plane(pP1P0P0, pP1P1P1, pP1P0P1, oom, rm); // x=1
        // Test 14
        instance = new V3D_Plane(pN1P1N1, pP0P1P0, pP1P1N1, oom, rm); // y=1
        expResult = new V3D_Line(pP1P1P0, pP1P1P1, oom, rm);    // x=1, y=1
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 15
        instance = new V3D_Plane(pP1P0P1, pP0N1P1, pP0P0P1, oom, rm); // z=1
        expResult = new V3D_Line(pP1P1P1, pP1P0P1, oom, rm);    // x=1, z=1
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 16 to 17
        pl = new V3D_Plane(pN1P1N1, pP0P1P0, pP1P1N1, oom, rm); // y=1
        // Test 16
        instance = new V3D_Plane(pP1P0P0, pP1P1P1, pP1P0P1, oom, rm); // x=1
        expResult = new V3D_Line(pP1P1P0, pP1P1P1, oom, rm);    // x=1, y=1
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 17
        instance = new V3D_Plane(pP1P0P1, pP0N1P1, pP0P0P1, oom, rm); // z=1
        expResult = new V3D_Line(pP1P1P1, pP0P1P1, oom, rm);    // y=1, z=1
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 18 to 19
        pl = new V3D_Plane(pP1P0P1, pP0N1P1, pP0P0P1, oom, rm); // z=1
        // Test 18
        instance = new V3D_Plane(pP1P0P0, pP1P1P1, pP1P0P1, oom, rm); // x=1
        expResult = new V3D_Line(pP1P0P1, pP1P1P1, oom, rm);    // x=1, z=1
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 19
        instance = new V3D_Plane(pN1P1N1, pP0P1P0, pP1P1N1, oom, rm); // y=1
        expResult = new V3D_Line(pP1P1P1, pP0P1P1, oom, rm);    // y=1, z=1
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 20 to 21
        pl = new V3D_Plane(pN1P0P0, pN1P1P1, pN1P0P1, oom, rm); // x=-1
        // Test 20
        instance = new V3D_Plane(pN1P1N1, pP0P1P0, pP1P1N1, oom, rm); // y=1
        expResult = new V3D_Line(pN1P1P0, pN1P1P1, oom, rm);    // x=-1, y=1
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 21
        instance = new V3D_Plane(pP1P0P1, pP0N1P1, pP0P0P1, oom, rm); // z=1
        expResult = new V3D_Line(pN1P1P1, pN1P0P1, oom, rm);    // x=-1, z=1
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 22 to 23
        pl = new V3D_Plane(pN1N1N1, pP0N1P0, pP1N1N1, oom, rm); // y=-1
        // Test 22
        instance = new V3D_Plane(pP1P0P0, pP1P1P1, pP1P0P1, oom, rm); // x=1
        expResult = new V3D_Line(pP1N1P0, pP1N1P1, oom, rm);    // x=1, y=-1
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 23
        instance = new V3D_Plane(pP1P0P1, pP0N1P1, pP0P0P1, oom, rm); // z=1
        expResult = new V3D_Line(pP1N1P1, pP0N1P1, oom, rm);    // y=-1, z=1
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 24 to 25
        pl = new V3D_Plane(pP1P0N1, pP0N1N1, pP0P0N1, oom, rm); // z=-1
        // Test 24
        instance = new V3D_Plane(pP1P0P0, pP1P1P1, pP1P0P1, oom, rm); // x=1
        expResult = new V3D_Line(pP1P0N1, pP1P1N1, oom, rm);    // x=1, z=-1
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 25
        instance = new V3D_Plane(pN1P1N1, pP0P1P0, pP1P1N1, oom, rm); // y=1
        expResult = new V3D_Line(pP1P1N1, pP0P1N1, oom, rm);    // y=1, z=-1
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 26 to 27
        pl = new V3D_Plane(pN1P0P0, pN1P1P1, pN1P0P1, oom, rm); // x=-1
        // Test 26
        instance = new V3D_Plane(pN1N1N1, pP0N1P0, pP1N1N1, oom, rm); // y=-1
        expResult = new V3D_Line(pN1N1P0, pN1N1P1, oom, rm);    // x=-1, y=-1
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 27
        instance = new V3D_Plane(pP1P0N1, pP0N1N1, pP0P0N1, oom, rm); // z=-1
        expResult = new V3D_Line(pN1P1N1, pN1P0N1, oom, rm);    // x=-1, z=-1
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 28 to 29
        pl = new V3D_Plane(pN1N1N1, pP0N1P0, pP1N1N1, oom, rm); // y=-1
        // Test 28
        instance = new V3D_Plane(pN1P0P0, pN1P1P1, pN1P0P1, oom, rm); // x=-1
        expResult = new V3D_Line(pN1N1P0, pN1N1P1, oom, rm);    // x=-1, y=-1
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 29
        instance = new V3D_Plane(pP1P0N1, pP0N1N1, pP0P0N1, oom, rm); // z=-1
        expResult = new V3D_Line(pP1N1N1, pP0N1N1, oom, rm);    // y=-1, z=-1
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 30 to 31
        pl = new V3D_Plane(pP1P0N1, pP0N1N1, pP0P0N1, oom, rm); // z=-1
        // Test 30
        instance = new V3D_Plane(pN1P0P0, pN1P1P1, pN1P0P1, oom, rm); // x=-1
        expResult = new V3D_Line(pN1P0N1, pN1P1N1, oom, rm);    // x=-1, z=-1
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 31
        instance = new V3D_Plane(pN1N1N1, pP0N1P0, pP1N1N1, oom, rm); // y=-1
        expResult = new V3D_Line(pP1N1N1, pP0N1N1, oom, rm);    // y=-1, z=-1
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));

        // Test 32 to ?
        pl = new V3D_Plane(pN1P0P0, pN1P1P1, pN1P0P1, oom, rm); // x=-1
        // Test 32
        instance = new V3D_Plane(pN1N2N1, pP0N2P0, pP1N2N1, oom, rm); // y=-2
        expResult = new V3D_Line(pN1N2P0, pN1N2P1, oom, rm);    // x=-1, y=-2
        result = instance.getIntersect(pl, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
    }

    /**
     * Test of getIntersect method, of class V3D_Plane.
     */
    @Test
    public void testGetIntersection_V3D_Plane_V3D_Plane() {
        V3D_Plane instance;
        V3D_Plane pl1;
        V3D_Plane pl2;
        // Test 1
        instance = V3D_Plane.X0;
        pl1 = V3D_Plane.Y0;
        pl2 = V3D_Plane.Z0;
        V3D_Point expResult = V3D_Point.ORIGIN;
        V3D_Point result = (V3D_Point) instance.getIntersect(pl1, pl2, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 2
        instance = new V3D_Plane(V3D_Plane.X0);
        pl1 = V3D_Plane.Y0;
        pl2 = V3D_Plane.Z0;
        instance.translate(V3D_Vector.I, oom, rm); // X = 1 Plane
        expResult = new V3D_Point(V3D_Point.ORIGIN);
        expResult.translate(V3D_Vector.I, oom, rm);
        result = (V3D_Point) instance.getIntersect(pl1, pl2, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 3
        instance = new V3D_Plane(V3D_Plane.X0);
        pl1 = V3D_Plane.Y0;
        pl2 = V3D_Plane.Z0;
        instance.translate(V3D_Vector.J, oom, rm);
        expResult = new V3D_Point(pP0P0P0);
        result = (V3D_Point) instance.getIntersect(pl1, pl2, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 4
        instance = new V3D_Plane(V3D_Plane.X0);
        pl1 = V3D_Plane.Y0;
        pl2 = V3D_Plane.Z0;
        instance.translate(V3D_Vector.K, oom, rm);
        expResult = new V3D_Point(pP0P0P0);
        result = (V3D_Point) instance.getIntersect(pl1, pl2, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
    }

    /**
     * Test of getIntersect method, of class V3D_Plane.
     */
    @Test
    public void testGetIntersection_V3D_Line() {
        System.out.println("getIntersect");
        V3D_Line l;
        V3D_Plane instance;
        V3D_Geometry expResult;
        V3D_Geometry result;
        // Test 1-3 axis with orthoganol plane through origin.
        // Test 1
        l= new V3D_Line(pP0P0P0, V3D_Vector.I); 
        instance = V3D_Plane.X0;
        expResult = pP0P0P0;
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 2
        l= new V3D_Line(pP0P0P0, V3D_Vector.J); 
        instance = V3D_Plane.Y0;
        expResult = pP0P0P0;
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 3
        l = new V3D_Line(pP0P0P0, V3D_Vector.K); 
        instance = V3D_Plane.Z0;
        expResult = pP0P0P0;
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 4-6 axis with orthoganol plane not through origin.
        // Test 4
        l= new V3D_Line(pP0P0P0, V3D_Vector.I); 
        instance = new V3D_Plane(V3D_Plane.X0);
        instance.translate(P1P0P0, oom, rm);
        expResult = pP1P0P0;
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 5
        l= new V3D_Line(pP0P0P0, V3D_Vector.J); 
        instance = new V3D_Plane(V3D_Plane.Y0);
        instance.translate(P0P1P0, oom, rm);
        expResult = pP0P1P0;
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 6
        l= new V3D_Line(pP0P0P0, V3D_Vector.K); 
        instance = new V3D_Plane(V3D_Plane.Z0);
        instance.translate(P0P0P1, oom, rm);
        expResult = pP0P0P1;
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 7
        // line
        // x = t, y = 2 + 3t, z = t
        // points (0, 2, 0), (1, 5, 1) 
        l = new V3D_Line(pP0P2P0, new V3D_Point(env, P1, P5, P1), oom, rm);
        // plane
        // 2x + y − 4z = 4
        // points (0, 0, -1), (0, 4, 0), (2, 0, 0)
        instance = new V3D_Plane(env, P0P0N1, new V3D_Vector(P0, P4, P0), 
                P2P0P0, oom, rm);
        // (2, 8, 2)
        expResult = new V3D_Point(env, P2, P8, P2);
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 9
        // line
        // x = t, y = 2 + 3t, z = t
        // points (0, 2, 0), (1, 5, 1) 
        l = new V3D_Line(pP0P2P0, new V3D_Point(env, P1, P5, P1), oom, rm);
        // plane
        // 2x + y − 4z = 4
        // points (0, 0, -1), (0, 4, 0), (2, 0, 0)
        instance = new V3D_Plane(env, new V3D_Vector(P0, P0, N1),
                new V3D_Vector(P0, P4, P0), new V3D_Vector(P2, P0, P0), oom, rm);
        // (2, 8, 2)
        expResult = new V3D_Point(env, P2, P8, P2);
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 10
        // line
        // x = 0, y = 0, z = t
        // points (0, 0, 0), (0, 0, 1) 
        l = new V3D_Line(pP0P0P0, pP0P0P1, oom, rm);
        // plane
        // points (0, 0, -1), (0, 4, 0), (2, 0, 0)
        instance = new V3D_Plane(env, P0P0P2, P1P0P2, P0P1P2, oom, rm);
        expResult = pP0P0P2;
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 11
        l = new V3D_Line(pP0P0P0, V3D_Vector.I); 
        instance = V3D_Plane.X0;
        expResult = V3D_Point.ORIGIN;
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 12
        l = new V3D_Line(pP0P0P0, V3D_Vector.I); 
        instance = V3D_Plane.Y0;
        expResult = new V3D_Line(pP0P0P0, V3D_Vector.I); 
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 13
        l = new V3D_Line(pP0P0P0, V3D_Vector.I); 
        instance = V3D_Plane.Z0;
        expResult= new V3D_Line(pP0P0P0, V3D_Vector.I); 
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 14
        l = new V3D_Line(pP0P0P0, V3D_Vector.J); 
        instance = V3D_Plane.X0;
        expResult = new V3D_Line(pP0P0P0, V3D_Vector.J); 
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 15
        l = new V3D_Line(pP0P0P0, V3D_Vector.J); 
        instance = V3D_Plane.Y0;
        expResult = V3D_Point.ORIGIN;
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 16
        l = new V3D_Line(pP0P0P0, V3D_Vector.J); 
        instance = V3D_Plane.Z0;
        expResult = new V3D_Line(pP0P0P0, V3D_Vector.J); 
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 17
        l = new V3D_Line(pP0P0P0, V3D_Vector.J); 
        instance = V3D_Plane.X0;
        expResult = new V3D_Line(pP0P0P0, V3D_Vector.J); 
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 18
        l = new V3D_Line(pP0P0P0, V3D_Vector.K); 
        instance = V3D_Plane.Y0;
        expResult= new V3D_Line(pP0P0P0, V3D_Vector.K); 
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 19
        l= new V3D_Line(pP0P0P0, V3D_Vector.K); 
        instance = V3D_Plane.Z0;
        expResult = V3D_Point.ORIGIN;
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
    }

    /**
     * Test of isOnPlane method, of class V3D_Plane.
     */
    @Test
    public void testIsOnPlane() {
        System.out.println("isOnPlane");
        V3D_Line l = new V3D_Line(pP0P0P0, pP1P0P0, oom, rm);
        V3D_Plane instance = new V3D_Plane(env, P0P0P0, P1P0P0, P1P1P0, oom, rm);
        assertTrue(instance.isOnPlane(l, oom, rm));
        // Test 2
        l = new V3D_Line(pP0P0P0, pP1P1P0, oom, rm);
        assertTrue(instance.isOnPlane(l, oom, rm));
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
//        // Test 1-3 axis with orthoganol plane through origin.
//        // Test 1
//        instance = new V3D_Ray(pN2P0P0, pN1P0P0, oom, rm);
//        p = V3D_Plane.X0;
////        pv = new V3D_Plane(new V3D_Environment(),
////            V3D_Vector.ZERO, V3D_Vector.ZERO, V3D_Vector.J, V3D_Vector.K);
//        //expResult = new V3D_Point(P0P0P0);
//        expResult = pP0P0P0;
//        result = p.getIntersect(instance, oom, rm);
//        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
//        // Test 2
//        instance = new V3D_Ray(env, P0P0P0, N1P0P0, N2P0P0);
//        p = V3D_Plane.X0;
//        result = p.getIntersect(instance, oom, rm);
//        assertNull(p.getIntersect(instance, oom, rm));
//        // Test 3
//        instance = new V3D_Ray(pP0P0P0, pP1P0P0, oom, rm);
//        p = V3D_Plane.X0;
//        expResult = pP0P0P0;
//        result = p.getIntersect(instance, oom, rm);
//        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
//        // Test 4
//        p = V3D_Plane.Y0;
//        expResult = new V3D_Ray(pP0P0P0, pP1P0P0, oom, rm);
//        result = p.getIntersect(instance, oom, rm);
//        assertTrue(((V3D_Ray) expResult).equals((V3D_Ray) result, oom, rm));
//        // Test 5
//        p = V3D_Plane.Z0;
//        expResult = new V3D_Ray(pP0P0P0, pP1P0P0, oom, rm);
//        result = p.getIntersect(instance, oom, rm);
//        assertTrue(((V3D_Ray) expResult).equals((V3D_Ray) result, oom, rm));
//        // Test 6
//        instance = new V3D_Ray(pN2P0P0, pN1P0P0, oom, rm);
//        p = V3D_Plane.X0;
//        expResult = pP0P0P0;
//        result = p.getIntersect(instance, oom, rm);
//        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
//        // Test 7
//        instance = new V3D_Ray(pN1P0P0, pN2P0P0, oom, rm);
//        p = V3D_Plane.X0;
//        result = p.getIntersect(instance, oom, rm);
//        assertNull(result);
        // Test 8
        // https://stackoverflow.com/questions/15102738/ray-plane-intersection-inaccurate-results-rounding-errors
        oom = -6;
        V3D_Vector n = new V3D_Vector(0, 1, 0);
        p = new V3D_Plane(pP0P0P0, n);
        BigRational x1 = BigRational.valueOf("20.818802");
        BigRational y1 = BigRational.valueOf("27.240383");
        BigRational z1 = BigRational.valueOf("15.124892");
        BigRational x2 = BigRational.valueOf("21.947229");
        BigRational y2 = BigRational.valueOf("-66.788452");
        BigRational z2 = BigRational.valueOf("-18.894285");
        instance = new V3D_Ray(
                new V3D_Point(env, x1, y1, z1), 
                new V3D_Point(env, x2, y2, z2), oom, rm);
//        expResult = new V3D_Point(env, 
//                BigRational.valueOf("21.145710"), 
//                BigRational.valueOf("0.000002"), 
//                BigRational.valueOf("5.269455"));
        expResult = new V3D_Point(env, 
                BigRational.valueOf("21.1457100561036516085730510220614772053700335647038486"), 
                BigRational.valueOf("0"), 
                BigRational.valueOf("5.2694533909308671111367060965926037475631810178228838"));
        result = p.getIntersect(instance, oom, rm);
        //System.out.println(result);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
    }
    
    /**
     * Test of getIntersect method, of class V3D_Plane.
     */
    @Test
    public void testGetIntersection_V3D_LineSegment() {
        System.out.println("getIntersect");
        V3D_LineSegment l;
        V3D_Plane instance;
        V3D_Geometry expResult;
        V3D_Geometry result;
        // Test 1-3 part of axis with orthoganol plane through origin.
        // Test 1
        l = new V3D_LineSegment(pN1P0P0, pP1P0P0, oom, rm);
        instance = V3D_Plane.X0;
        expResult = pP0P0P0;
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 2
        l = new V3D_LineSegment(pP0N1P0, pP0P1P0, oom, rm);
        instance = V3D_Plane.Y0;
        expResult = pP0P0P0;
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 3
        l = new V3D_LineSegment(pP0P0N1, pP0P0P1, oom, rm);
        instance = V3D_Plane.Z0;
        expResult = pP0P0P0;
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 4-6 part of axis with orthoganol plane not through origin.
        // Test 4
        l = new V3D_LineSegment(pN1P0P0, pP1P0P0, oom, rm);
        instance = new V3D_Plane(V3D_Plane.X0);
        instance.translate(P1P0P0, oom, rm);
        expResult = pP1P0P0;
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 5
        l = new V3D_LineSegment(pP0N1P0, pP0P1P0, oom, rm);
        instance = new V3D_Plane(V3D_Plane.Y0);
        instance.translate(P0P1P0, oom, rm);
        expResult = pP0P1P0;
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 6
        l = new V3D_LineSegment(pP0P0N1, pP0P0P1, oom, rm);
        instance = new V3D_Plane(V3D_Plane.Z0);
        instance.translate(P0P0P1, oom, rm);
        expResult = pP0P0P1;
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 7
        l = new V3D_LineSegment(pP0P2P0, new V3D_Point(env, P1, P5, P1), oom, rm);
        instance = new V3D_Plane(env, P0P0N1, new V3D_Vector(P0, P4, P0),
                new V3D_Vector(P2, P0, P0), oom, rm);
        assertNull(instance.getIntersect(l, oom, rm));
        // Test 8
        l = new V3D_LineSegment(pP0P2P0, new V3D_Point(env, P2, P8, P2), oom, rm);
        result = instance.getIntersect(l, oom, rm);
        expResult = new V3D_Point(env, P2, P8, P2);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 9
        oom = -3;
        // line
        // x = t, y = 2 + 3t, z = t
        // points (0, 2, 0), (1, 5, 1) 
        l = new V3D_LineSegment(pP0P2P0, new V3D_Point(env, P1, P5, P1), oom, rm);
        // plane
        // 2x + y − 4z = 4
        // points (0, 0, -1), (0, 4, 0), (2, 0, 0)
        instance = new V3D_Plane(env, P0P0N1, new V3D_Vector(P0, P4, P0), 
                P2P0P0, oom, rm);
        // (2, 8, 2)
        expResult = new V3D_Point(env, P2, P8, P2);
        result = instance.getIntersect(l, oom, rm);
        assertNotEquals(expResult, result);
        l = new V3D_LineSegment(pP0P2P0, new V3D_Point(env, P2, P8, P2), oom, rm);
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 10
        // line
        // x = 0, y = 0, z = t
        // points (0, 0, 0), (0, 0, 1) 
        l = new V3D_LineSegment(pP0P0P0, pP0P0P1, oom, rm);
        // plane
        // points (0, 0, -1), (0, 4, 0), (2, 0, 0)
        instance = new V3D_Plane(env, P0P0P2, P1P0P2, P0P1P2, oom, rm);
        result = instance.getIntersect(l, oom, rm);
        assertNull(result);
        l = new V3D_LineSegment(pP0P0P0, new V3D_Point(env, P0, P0, P4), oom, rm);
        expResult = pP0P0P2;
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));

    }

    /**
     * Test of isEnvelopeIntersectedBy method, of class V3D_Plane.
     */
    @Test
    public void testIsEnvelopeIntersectedBy() {
        System.out.println("isEnvelopeIntersectedBy");
        // No test.
    }

    /**
     * Test of getAsMatrix method, of class V3D_Plane.
     */
    @Test
    public void testGetAsMatrix() {
        System.out.println("getAsMatrix");
        V3D_Plane instance = V3D_Plane.X0;
        BigRational[][] m = new BigRational[3][3];
        m[0][0] = P0;
        m[0][1] = P0;
        m[0][2] = P0;
//        m[1][0] = P0;
//        m[1][1] = P1;
//        m[1][2] = P0;
//        m[2][0] = P0;
//        m[2][1] = P0;
//        m[2][2] = N1;
        m[1][0] = P0;
        m[1][1] = P0;
        m[1][2] = N1;
        m[2][0] = P0;
        m[2][1] = N1;
        m[2][2] = P0;
        Math_Matrix_BR expResult = new Math_Matrix_BR(m);
        Math_Matrix_BR result = instance.getAsMatrix(oom, rm);
        assertTrue(expResult.getRows().length == result.getRows().length);
        assertTrue(expResult.getCols().length == result.getCols().length);
        for (int i = 0; i < expResult.getRows().length; i++) {
            for (int j = 0; j < expResult.getCols().length; j++) {
//                if (expResult.getRows()[i][j].compareTo(result.getRows()[i][j]) != 0) {
//                    int debug = 1;
//                }
                assertTrue(expResult.getRows()[i][j].compareTo(result.getRows()[i][j]) == 0);
            }
        }
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Plane.
     */
    @Test
    public void testGetDistanceSquared() {
        System.out.println("getDistanceSquared");
        V3D_Plane p = V3D_Plane.X0;
        V3D_Plane instance = V3D_Plane.X0;
        BigRational expResult = P0;
        BigRational result = instance.getDistanceSquared(p, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 2
        V3D_Vector v = V3D_Vector.I;
        instance = new V3D_Plane(V3D_Plane.X0);
        instance.translate(v, oom, rm);
        expResult = P1;
        result = instance.getDistanceSquared(p, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 3
        v = V3D_Vector.J;
        instance = new V3D_Plane(V3D_Plane.X0);
        instance.translate(v, oom, rm);
        expResult = P0;
        result = instance.getDistanceSquared(p, oom, rm);
        assertTrue(expResult.equals(result));
        // Test 4
        v = V3D_Vector.K;
        instance = new V3D_Plane(V3D_Plane.X0);
        instance.translate(v, oom, rm);
        expResult = P0;
        result = instance.getDistanceSquared(p, oom, rm);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of equals method, of class V3D_Plane.
     */
    @Test
    public void testIsCoincident() {
        // No test as covered by other tests.
    }

    /**
     * Test of getDistance method, of class V3D_Plane.
     */
    @Test
    public void testGetDistance_V3D_Point_int() {
        System.out.println("getDistance");
        V3D_Point p = new V3D_Point(env, P5, P0, P0);
        V3D_Plane instance = new V3D_Plane(env, P0P0P0, P0P0P1, P0P1P1, oom, rm);
        BigRational expResult = BigRational.valueOf(5);
        BigRational result = instance.getDistance(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 2
        p = new V3D_Point(env, P5, P10, P0);
        result = instance.getDistance(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getDistance method, of class V3D_Plane.
     */
    @Test
    public void testGetDistance_V3D_LineSegment_int() {
        System.out.println("getDistance");
        V3D_LineSegment l = new V3D_LineSegment(new V3D_Point(env, P10, P1, P1),
                new V3D_Point(env, P100, P1, P1), oom, rm);
        V3D_Plane instance = V3D_Plane.X0;
        BigDecimal expResult = BigDecimal.TEN;
        BigDecimal result = instance.getDistance(l, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of toString method, of class V3D_Plane.
     */
    @Test
    public void testToString_0args() {
        System.out.println("toString");
        V3D_Plane instance = V3D_Plane.X0;
        String expResult = """
                           V3D_Plane(
                            offset=V3D_Vector(dx=0, dy=0, dz=0),
                            p= V3D_Vector(dx=0, dy=0, dz=0),
                            n= V3D_Vector(dx=1, dy=0, dz=0))""";
        String result = instance.toString();
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class V3D_Plane.
     */
    @Test
    public void testToString_String() {
        System.out.println("toString");
        String pad = "";
        V3D_Plane instance = V3D_Plane.X0;
        String expResult = """
                           V3D_Plane
                           (
                            offset=V3D_Vector
                            (
                             dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),
                             dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),
                             dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)
                            )
                            ,
                            pv=V3D_Vector
                            (
                             dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),
                             dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),
                             dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)
                            )
                            ,
                            n=V3D_Vector
                            (
                             dx=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),
                             dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),
                             dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)
                            )
                           )""";
        String result = instance.toString(pad);
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getPAsVector method, of class V3D_Plane.
     */
    @Test
    public void testGetPV() {
        System.out.println("getPV");
        V3D_Plane instance = V3D_Plane.X0;
        assertTrue(instance.getPV(oom, rm).getDotProduct(instance.getN(), oom, rm).compareTo(BigRational.ZERO) == 0);
        // Test 2
        instance = V3D_Plane.Y0;
        assertTrue(instance.getPV(oom, rm).getDotProduct(instance.getN(), oom, rm).compareTo(BigRational.ZERO) == 0);
        // Test 3
        instance = V3D_Plane.Z0;
        assertTrue(instance.getPV(oom, rm).getDotProduct(instance.getN(), oom, rm).compareTo(BigRational.ZERO) == 0);
    }

    /**
     * Test of getP method, of class V3D_Plane.
     */
    @Test
    public void testGetP() {
        System.out.println("getP");
        V3D_Plane instance = V3D_Plane.X0;
        V3D_Point expResult = V3D_Point.ORIGIN;
        V3D_Point result = instance.getP();
        assertTrue((expResult).equals(result, oom, rm));
    }

    /**
     * Test of getQ method, of class V3D_Plane.
     */
    @Test
    public void testGetQ() {
        System.out.println("getQ");
        V3D_Plane instance = V3D_Plane.X0;
        assertTrue(instance.intersects(instance.getQ(oom, rm), oom, rm));
    }

    /**
     * Test of getR method, of class V3D_Plane.
     */
    @Test
    public void testGetR() {
        System.out.println("getR");
        V3D_Plane instance = V3D_Plane.X0;
        assertTrue(instance.intersects(instance.getR(oom, rm), oom, rm));
    }

    /**
     * Test of getEquationCoefficients method, of class V3D_Plane.
     */
    @Test
    public void testGetEquation_int_RoundingMode() {
        System.out.println("getEquationCoefficients");
        V3D_Plane instance = new V3D_Plane(env, P0P0P0, P1P1P1, P1P0P0, oom, rm);
        BigRational[] expResult = new BigRational[4];
        expResult[0] = P0;
        expResult[1] = P1;
        expResult[2] = N1;
        expResult[3] = P0;
        BigRational[] result = instance.getEquation(oom, rm).coeffs;
//        for (int i = 0; i < result.length; i ++) {
//            System.out.println(i + " " + result[i].toRationalString());
//        }
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of rotate method, of class V3D_Plane.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        int oom = -5;
        Math_BigDecimal bd = new Math_BigDecimal();
        BigRational Pi = BigRational.valueOf(
                bd.getPi(oom, RoundingMode.HALF_UP));
        V3D_Ray xaxis = new V3D_Ray(V3D_Test.pP0P0P0, V3D_Vector.I);
        V3D_Ray yaxis = new V3D_Ray(V3D_Test.pP0P0P0, V3D_Vector.J);
        V3D_Ray zaxis = new V3D_Ray(V3D_Test.pP0P0P0, V3D_Vector.K);
        // Test1
        BigRational theta = Pi.divide(2);
        V3D_Plane instance = new V3D_Plane(V3D_Plane.X0);
        instance = instance.rotate(xaxis, xaxis.l.v, bd, theta, oom, rm);
        assertTrue(V3D_Plane.X0.equalsIgnoreOrientation(instance, oom, rm));
        // Test 2
        instance = new V3D_Plane(V3D_Plane.X0);
        instance = instance.rotate(yaxis, yaxis.l.v, bd, theta, oom, rm);
        assertTrue(V3D_Plane.Z0.equalsIgnoreOrientation(instance, oom, rm));
        // Test 3
        instance = new V3D_Plane(V3D_Plane.X0);
        instance = instance.rotate(zaxis, zaxis.l.v, bd, theta, oom, rm);
        assertTrue(V3D_Plane.Y0.equalsIgnoreOrientation(instance, oom, rm));
        // Test 4
        theta = Pi;
        instance = new V3D_Plane(env, P1P0P0, P0P0P0, P0P2P0, P0P2P2, oom, rm);
        instance = instance.rotate(xaxis, xaxis.l.v, bd, theta, oom, rm);
        assertTrue(new V3D_Plane(env, P1P0P0, P0P0P0, P0P2P0, P0P2P2, oom, rm).equalsIgnoreOrientation(instance, oom, rm));
        // Test 5
        oom = -6;
        Pi = BigRational.valueOf(
                new Math_BigDecimal().getPi(oom - 2, RoundingMode.HALF_UP));
        theta = Pi;
        instance = new V3D_Plane(env, P1P0P0, P0P0P0, P0P2P0, P0P2P2, oom, rm);
        instance = instance.rotate(yaxis, yaxis.l.v, bd, theta, oom, rm);
        V3D_Plane expResult = new V3D_Plane(env, N1P0P0, P0P0P0, P0P2P0, P0P2N2, oom, rm);
        assertTrue(expResult.equalsIgnoreOrientation(instance, oom, rm));
        // Test 6
        theta = Pi;
        instance = new V3D_Plane(env, P1P0P0, P0P0P0, P0P2P0, P0P2P2, oom, rm);
        instance = instance.rotate(zaxis, zaxis.l.v, bd, theta, oom, rm);
        //expResult = new V3D_Plane(N1P0P0, P0P0P0, P0P2P0, P0P2P2, oom, rm);
        expResult = new V3D_Plane(env, N1P0P0, P0P0P0, P0P2P2, P0P2P0, oom, rm);
        assertTrue(expResult.equalsIgnoreOrientation(instance, oom, rm));
    }

//    /**
//     * Test of intersects method, of class V3D_Plane.
//     */
//    @Test
//    public void testIsIntersectedBy_V3D_LineSegment_int() {
//        System.out.println("intersects");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_LineSegment l = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
//        V3D_Plane instance = V3D_Plane.X0;
//        assertTrue(instance.intersects(l, oom, rm));
//        // Test 2
//        instance = V3D_Plane.Y0;
//        assertTrue(instance.intersects(l, oom, rm));
//        // Test 3
//        instance = V3D_Plane.Z0;
//        assertTrue(instance.intersects(l, oom, rm));
//        // Test 4
//        l = new V3D_LineSegment(pP0P0P2, pP1P0P2, oom, rm);
//        instance = V3D_Plane.Z0;
//        assertFalse(instance.intersects(l, oom, rm));
//        // Test 5
//        l = new V3D_LineSegment(pN1P0P0, pP1P0P0, oom, rm);
//        instance = V3D_Plane.X0;
//        assertTrue(instance.intersects(l, oom, rm));
//        // Test 6
//        l = new V3D_LineSegment(pP1P0P0, pP2P0P0, oom, rm);
//        assertFalse(instance.intersects(l, oom, rm));
//    }
    
    /**
     * Test constructor method, of class V3D_PlaneDouble.
     */
    @Test
    public void testConstructor() {
        System.out.println("testConstructors");
        V3D_Plane pl0 = V3D_Plane.X0;
        V3D_Plane pl1 = new V3D_Plane(V3D_Vector.I, pl0, oom, rm);
        V3D_Plane pl2 = new V3D_Plane(env, V3D_Vector.I, N1P0P0, N1P1P0, N1P0P1, oom,
                rm);
        assertTrue(pl0.equalsIgnoreOrientation(pl1, oom, rm));
        assertTrue(pl0.equalsIgnoreOrientation(pl2, oom, rm));
        assertTrue(pl1.equalsIgnoreOrientation(pl2, oom, rm));
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Plane.
     */
    @Test
    public void testGetDistanceSquared_V3D_Point_int() {
        System.out.println("getDistanceSquared");
        V3D_Point pt;
        V3D_Plane instance;
        BigRational expResult;
        BigRational result;
        // Test 1
        pt = pP1P0P0;
        instance = V3D_Plane.X0;
        expResult = P1;
        result = instance.getDistanceSquared(pt, oom, rm);
        assertEquals(expResult, result);
        // Test 2
        pt = pP0P0P0;
        expResult = P0;
        result = instance.getDistanceSquared(pt, oom, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Plane covered by
     * {@link #testGetDistanceSquared_V3D_Point_int()}.
     */
    @Test
    public void testGetDistanceSquared_3args() {
        System.out.println("getDistanceSquared");
    }

    /**
     * Test of getDistance method, of class V3D_Plane covered by
     * {@link #testGetDistanceSquared_V3D_Plane_int()}.
     */
    @Test
    public void testGetDistance_V3D_Plane_int() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Plane.
     */
    @Test
    public void testGetDistanceSquared_V3D_Plane_int() {
        System.out.println("getDistanceSquared");
        V3D_Plane pl;
        V3D_Plane instance;
        BigRational expResult;
        BigRational result;
        // Test 1
        pl = V3D_Plane.X0;
        instance = V3D_Plane.Y0;
        expResult = P0;
        result = instance.getDistanceSquared(pl, oom, rm);
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_Plane(V3D_Plane.X0);
        instance.translate(P1P0P0, oom, rm);
        expResult = P1;
        result = instance.getDistanceSquared(pl, oom, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Plane.
     */
    @Test
    public void testGetDistanceSquared_V3D_Line_int() {
        System.out.println("getDistanceSquared");
        V3D_Line l;
        V3D_Plane instance;
        BigRational expResult;
        BigRational result;
        // Test 1
        l = new V3D_Line(pP0P0P0, V3D_Vector.I);
        instance = V3D_Plane.Y0;
        expResult = P0;
        result = instance.getDistanceSquared(l, oom, rm);
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_Plane(P0P1P0, V3D_Plane.Y0, oom, rm);
        expResult = P0;
        result = instance.getDistanceSquared(l, oom, rm);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Plane.
     */
    @Test
    public void testGetDistanceSquared_V3D_LineSegment_int() {
        System.out.println("getDistanceSquared");
        V3D_LineSegment l;
        V3D_Plane instance;
        BigRational expResult;
        BigRational result;
        // Test 1
        l = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        instance = V3D_Plane.Y0;
        expResult = P0;
        result = instance.getDistanceSquared(l, oom, rm);
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_Plane(P0P1P0, V3D_Plane.Y0, oom, rm);
        expResult = P1;
        result = instance.getDistanceSquared(l, oom, rm);
        assertNotEquals(expResult, result);
    }

    /**
     * Test of getPointOfProjectedIntersect method, of class V3D_Plane.
     */
    @Test
    public void testGetPointOfProjectedIntersection() {
        System.out.println("getPointOfProjectedIntersection");
        V3D_Point pt;
        V3D_Plane instance;
        V3D_Point expResult;
        V3D_Point result;
        // Test 1
        pt = pP1P0P0;
        instance = V3D_Plane.X0;
        expResult = pP0P0P0;
        result = instance.getPointOfProjectedIntersect(pt, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
    }

//    /**
//     * Test of intersects method, of class V3D_Plane.
//     */
//    @Test
//    public void testIsIntersectedBy_V3D_Triangle_int() {
//        System.out.println("intersects");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_Triangle t;
//        V3D_Plane instance;
//        // Test 1
//        t = new V3D_Triangle(pP1P0P0, pP1P1P0, pP0P1P0, oom, rm);
//        instance = V3D_Plane.X0;
//        assertTrue(instance.intersects(t, oom, rm));
//        // Test 2
//        instance = new V3D_Plane(V3D_Plane.X0);
//        instance.translate(P2P0P0, oom, rm);
//        assertFalse(instance.intersects(t, oom, rm));
//    }
//    /**
//     * Test of intersects method, of class V3D_Plane.
//     */
//    @Test
//    public void testIsIntersectedBy_V3D_Tetrahedron_int() {
//        System.out.println("intersects");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_Tetrahedron t;
//        V3D_Plane instance;
//        // Test 1
//        t = new V3D_Tetrahedron(P0P0P0, P1P0P0, P0P1P0, P1P1P0, P1P1P1);
//        instance = V3D_Plane.X0;
//        assertTrue(instance.intersects(t, oom, rm));
//        // Test 1
//        t = new V3D_Tetrahedron(P0P0P0, P1P0P0, P0P1P0, P1P1P0, P1P1P1);
//        instance = new V3D_Plane(V3D_Plane.X0);
//        instance.translate(new V3D_Vector(0.5d, 0, 0), oom, rm);
//        assertTrue(instance.intersects(t, oom, rm));
//    }
//    /**
//     * Test of getIntersect method, of class V3D_Plane.
//     */
//    @Test
//    public void testGetIntersection_V3D_Tetrahedron_int() {
//        System.out.println("getIntersect");
//        V3D_Tetrahedron t = null;
//        int oom = 0;
//        V3D_Plane instance = null;
//        V3D_Geometry expResult = null;
//        V3D_Geometry result = instance.getIntersect(t, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDistance method, of class V3D_Plane.
//     */
//    @Test
//    public void testGetDistance_V3D_Triangle_int() {
//        System.out.println("getDistance");
//        V3D_Triangle t = null;
//        int oom = 0;
//        V3D_Plane instance = null;
//        BigDecimal expResult = null;
//        BigDecimal result = instance.getDistance(t, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDistanceSquared method, of class V3D_Plane.
//     */
//    @Test
//    public void testGetDistanceSquared_V3D_Triangle_int() {
//        System.out.println("getDistanceSquared");
//        V3D_Triangle t = null;
//        int oom = 0;
//        V3D_Plane instance = null;
//        BigRational expResult = null;
//        BigRational result = instance.getDistanceSquared(t, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDistance method, of class V3D_Plane.
//     */
//    @Test
//    public void testGetDistance_V3D_Tetrahedron_int() {
//        System.out.println("getDistance");
//        V3D_Tetrahedron t = null;
//        int oom = 0;
//        V3D_Plane instance = null;
//        BigDecimal expResult = null;
//        BigDecimal result = instance.getDistance(t, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDistanceSquared method, of class V3D_Plane.
//     */
//    @Test
//    public void testGetDistanceSquared_V3D_Tetrahedron_int() {
//        System.out.println("getDistanceSquared");
//        V3D_Tetrahedron t = null;
//        int oom = 0;
//        V3D_Plane instance = null;
//        BigRational expResult = null;
//        BigRational result = instance.getDistanceSquared(t, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    /**
     * Test of isCoplanar method, of class V3D_Geometrics.
     */
    @Test
    public void testIsCoplanar_3args() {
        System.out.println("isCoplanar");
        V3D_Plane p;
        V3D_Point[] points;
        // Test 1
        p = new V3D_Plane(pP0P0P0, pP1P0P0, pP0P1P0, oom, rm);
        points = new V3D_Point[3];
        points[0] = pP2P2P0;
        points[1] = pN2P2P0;
        points[2] = pP2N2P0;
        assertTrue(V3D_Plane.isCoplanar(oom, rm, p, points));
        // Test 2
        points[2] = pP2N2P1;
        assertFalse(V3D_Plane.isCoplanar(oom, rm, p, points));
        // Test 3
        p = new V3D_Plane(pP0P0P0, pP0P1P0, pP0P0P1, oom, rm);
        points[0] = pP0P2P2;
        points[1] = pP0N2P2;
        points[2] = pP0P2N2;
        assertTrue(V3D_Plane.isCoplanar(oom, rm, p, points));
        // Test 4
        points[2] = pP2N2P1;
        assertFalse(V3D_Plane.isCoplanar(oom, rm, p, points));
    }

    /**
     * Test of isCoplanar method, of class V3D_Geometrics.
     */
    @Test
    public void testIsCoplanar_int_V3D_PointArr() {
        System.out.println("isCoplanar");
        V3D_Point[] points = new V3D_Point[6];
        points[0] = pP0P0P0;
        points[1] = pP0P1P0;
        points[2] = pP0P0P1;
        points[3] = pP0P2P2;
        points[4] = pP0N2P2;
        points[5] = pP0P2N2;
        assertTrue(V3D_Plane.isCoplanar(oom, rm, points));
        // Test 2
        points[2] = pP2N2P1;
        assertFalse(V3D_Plane.isCoplanar(oom, rm, points));
    }
}
