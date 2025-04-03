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
import uk.ac.leeds.ccg.math.matrices.Math_Matrix_Double;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Geometry_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Line_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_LineSegment_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Point_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Ray_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Vector_d;

/**
 * Test class for V3D_Line_d.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Line_dTest extends V3D_Test_d {

    public V3D_Line_dTest() {
        super();
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
     * Test of toString method, of class V3D_Line_d.
     */
    @Test
    public void testToString_0args() {
        System.out.println("toString");
        V3D_Line_d instance = new V3D_Line_d(pP0P0P0, pP1P0P0);
        String expResult = """
                           V3D_Line_d
                           (
                            offset=V3D_Vector_d(dx=0.0, dy=0.0, dz=0.0),
                            p=V3D_Point_d(offset=V3D_Vector_d(dx=0.0, dy=0.0, dz=0.0), rel=V3D_Vector_d(dx=0.0, dy=0.0, dz=0.0)),
                            v= V3D_Vector_d(dx=1.0, dy=0.0, dz=0.0)
                           )""";
        String result = instance.toString();
        //System.out.println(result);
        assertTrue(expResult.equalsIgnoreCase(result));
    }

    /**
     * Test of intersects method, of class V3D_Line_d.
     */
    @Test
    public void testIsIntersectedBy_double_V3D_Point_d() {
        System.out.println("intersects");
        double epsilon = 1d / 10000000d;
        V3D_Point_d pt = pP0P0P0;
        V3D_Line_d instance = new V3D_Line_d(pN1N1N1, pP1P1P1);
        assertTrue(instance.intersects(pt, epsilon));
        // Test 2
        pt = new V3D_Point_d(env, P0_1E2, P0_1E2, P0_1E2);
        assertTrue(instance.intersects(pt, epsilon));
        // Test 3 works as the rounding puts pt on the line.
        pt = new V3D_Point_d(env, P0_1E12, P0_1E12, P0_1E12);
        assertTrue(instance.intersects(pt, epsilon));
        // Test 4 works as the rounding puts pt on the line.
        pt = new V3D_Point_d(env, N0_1E12, N0_1E12, N0_1E12);
        assertTrue(instance.intersects(pt, epsilon));
        // Test 5 works as the rounding puts pt on the line.
        double a = P0_1E2 + P1E12;
        pt = new V3D_Point_d(env, a, a, a);
        assertTrue(instance.intersects(pt, epsilon));
        // Test 6 works as the rounding puts pt on the line.
        a = N0_1E2 + N1E12;
        pt = new V3D_Point_d(env, a, a, a);
        assertTrue(instance.intersects(pt, epsilon));
        // Test 7
        instance = new V3D_Line_d(pP0N1N1, pP2P1P1);
        pt = new V3D_Point_d(env, -1d, -2d, -2d);
        assertTrue(instance.intersects(pt, epsilon));
        // Test 8
        a = N0_1E2 + N1E12;
        pt = new V3D_Point_d(env, a, a, a);
        assertFalse(instance.intersects(pt, epsilon));
        pt = new V3D_Point_d(env, a + 1d, a, a);
        assertTrue(instance.intersects(pt, epsilon));
        // Test 9
        a = N0_1E12 + N1E12;
        pt = new V3D_Point_d(env, a, a, a);
        assertFalse(instance.intersects(pt, epsilon));
        pt = new V3D_Point_d(env, a + 1d, a, a);
        assertTrue(instance.intersects(pt, epsilon));
    }

    /**
     * Test of isParallel method, of class V3D_Line_d.
     */
    @Test
    public void testIsParallel_V3D_Line_d_double() {
        System.out.println("isParallel");
        double epsilon = 1d / 10000000d;
        V3D_Line_d l = V3D_Line_d.X_AXIS;
        V3D_Line_d instance = V3D_Line_d.X_AXIS;
        assertTrue(instance.isParallel(l, epsilon));
        // Test 2
        instance = V3D_Line_d.Y_AXIS;
        assertFalse(instance.isParallel(l, epsilon));
        // Test 3
        instance = V3D_Line_d.Z_AXIS;
        assertFalse(instance.isParallel(l, epsilon));
        // Test 4
        instance = new V3D_Line_d(pP0P1P0, pP1P1P0);
        assertTrue(instance.isParallel(l, epsilon));
        // Test 5
        instance = new V3D_Line_d(pP0P1P0, pP1P1P0);
        assertTrue(instance.isParallel(l, epsilon));
        // Test 6
        instance = new V3D_Line_d(pP0P0P1, pP1P0P1);
        assertTrue(instance.isParallel(l, epsilon));
        // Test 7
        instance = new V3D_Line_d(pP1P0P1, pP0P0P1);
        assertTrue(instance.isParallel(l, epsilon));
        // Test 8
        instance = new V3D_Line_d(pP1P0P1, pP0P1P1);
        assertFalse(instance.isParallel(l, epsilon));
        // Test 9
        l = V3D_Line_d.Y_AXIS;
        instance = new V3D_Line_d(pP0P0P1, pP0P1P1);
        assertTrue(instance.isParallel(l, epsilon));
        // Test 9
        instance = new V3D_Line_d(pP1P0P0, pP1P1P0);
        assertTrue(instance.isParallel(l, epsilon));
        // Test 10
        instance = new V3D_Line_d(pP1P0P1, pP1P1P1);
        assertTrue(instance.isParallel(l, epsilon));
        // Test 11
        instance = new V3D_Line_d(pP1P0P1, pP1P1P0);
        assertFalse(instance.isParallel(l, epsilon));
        // Test 12
        l = V3D_Line_d.Z_AXIS;
        instance = new V3D_Line_d(pP1P0P0, pP1P0P1);
        assertTrue(instance.isParallel(l, epsilon));
        // Test 9
        instance = new V3D_Line_d(pP0P1P0, pP0P1P1);
        assertTrue(instance.isParallel(l, epsilon));
        // Test 10
        instance = new V3D_Line_d(pP1P1P0, pP1P1P1);
        assertTrue(instance.isParallel(l, epsilon));
        // Test 11
        instance = new V3D_Line_d(pP1P0P1, pP1P1P0);
        assertFalse(instance.isParallel(l, epsilon));
        // Test 12
        l = new V3D_Line_d(pP1P1P1, pN1N1N1);
        instance = new V3D_Line_d(pP1N1P1, pN1P1N1);
        assertFalse(instance.isParallel(l, epsilon));
        // Test 13
        double a = P0_1E12 + P1E12;
        double b = N0_1E12 + N1E12;
        double a1 = P0_1E12 + P1E12 + 1d;
        double b1 = N0_1E12 + N1E12 + 1d;
        l = new V3D_Line_d(new V3D_Point_d(env, a, a, a), new V3D_Point_d(env, b, b, b));
        instance = new V3D_Line_d(new V3D_Point_d(env, a1, a, a), new V3D_Point_d(env, b1, b, b));
        epsilon = 1d / 100000d;
        assertTrue(instance.isParallel(l, epsilon));
        // Test 14
        a = P0_1E12 + P1E12;
        b = N0_1E12 + N1E12;
        a1 = P0_1E12 + P1E12 + 10d;
        b1 = N0_1E12 + N1E12 + 10d;
        l = new V3D_Line_d(new V3D_Point_d(env, a, a, a), new V3D_Point_d(env, b, b, b));
        instance = new V3D_Line_d(new V3D_Point_d(env, a1, a, a),
                new V3D_Point_d(env, b1, b, b));
        assertTrue(instance.isParallel(l, epsilon));
    }

    /**
     * Test of getIntersect method, of class V3D_Line_d.
     */
    @Test
    public void testGetIntersect_V3D_Line_d_double() {
        System.out.println("getIntersect");
        double epsilon = 1d / 10000000d;
        V3D_Line_d l;
        V3D_Line_d instance;
        V3D_Geometry_d expResult;
        V3D_Geometry_d result;
        // Test 1
        //l = new V3D_Line_d(N1N1N1, P1P1P1);
        l = new V3D_Line_d(pP1P1P1, pN1N1N1);
        //instance = new V3D_Line_d(N1P1N1, P1N1P1);
        instance = new V3D_Line_d(pP1N1P1, pN1P1N1);
        expResult = pP0P0P0;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 2
        l = new V3D_Line_d(pN1N1N1, pP1P1P1);
        instance = new V3D_Line_d(pP1P1P0, pP1P1P2);
        expResult = pP1P1P1;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 3
        expResult = pP0P0P0;
        instance = new V3D_Line_d(pN1N1P0, pP1P1P0);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 4
        l = new V3D_Line_d(pN1N1N1, pP1P1P1);
        instance = new V3D_Line_d(new V3D_Point_d(env, 3d, 1d, 1d), 
                new V3D_Point_d(env, 1d, 3d, 3d));
        expResult = pP2P2P2;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 5
        l = new V3D_Line_d(pN1N1P0, pP1P1P0);
        instance = new V3D_Line_d(new V3D_Point_d(env, 3d, 3d, 0d), 
                new V3D_Point_d(env, 3d, 3d, -1d));
        expResult = new V3D_Point_d(env, 3d, 3d, 0d);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 6
        l = new V3D_Line_d(pN1N1N1, pP1P1P1);
        instance = new V3D_Line_d(pP1N1N1, pN1P1P1);
        expResult = pP0P0P0;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 7
        l = new V3D_Line_d(pP0P0P0, pP1P1P1);
        instance = new V3D_Line_d(pP1N1N1, pN1P1P1);
        expResult = pP0P0P0;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 8
        l = new V3D_Line_d(pN1N1N1, pP0P0P0);
        instance = new V3D_Line_d(pP1N1N1, pN1P1P1);
        expResult = pP0P0P0;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 9
        l = new V3D_Line_d(pN2N2N2, pN1N1N1);
        instance = new V3D_Line_d(pP1N1N1, pP0P0P0);
        expResult = pP0P0P0;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 10
        l = new V3D_Line_d(pN2N2N2, pN1N1N1);
        instance = new V3D_Line_d(pP0P0P0, pN1P1P1);
        expResult = pP0P0P0;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 12 to 14
        // v.dx = 0, v.dy != 0, v.dz !=0
        // Test 11
        l = new V3D_Line_d(pN1N1N1, pP1P1P1);
        expResult = pP0P0P0;
        instance = new V3D_Line_d(pP0P0P0, pP0P1P1);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 15
        l = new V3D_Line_d(pP0N1N1, pP2P1P1);
        expResult = pP1P0P0;
        instance = new V3D_Line_d(pP1P0P0, pP1P1P1);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 16
        l = new V3D_Line_d(pP0N1P1, new V3D_Point_d(env, 2d, 1d, 3d));
        expResult = pP1P0P2;
        instance = new V3D_Line_d(new V3D_Point_d(env, 1d, 0d, 2d),
                new V3D_Point_d(env, 1d, 1d, 3d));
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 17 to 18
        // v.dx != 0, v.dy = 0, v.dz = 0
        // Test 17
        l = new V3D_Line_d(pN1N1N1, pP1P1P1);
        expResult = pP0P0P0;
        instance = new V3D_Line_d(pP0P0P0, pP1P0P0);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 18
        l = new V3D_Line_d(pP0N1N1, pP2P1P1);
        expResult = pP1P0P0;
        instance = new V3D_Line_d(pP1P0P0, pP2P0P0);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 19
        l = new V3D_Line_d(pP0N1P0, pP2P1P2);
        expResult = pP1P0P1;
        instance = new V3D_Line_d(pP1P0P1, pP2P0P1);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 20 to 21
        // v.dx != 0, v.dy = 0, v.dz != 0
        // Test 20
        l = new V3D_Line_d(pN1N1N1, pP1P1P1);
        expResult = pP0P0P0;
        instance = new V3D_Line_d(pP0P0P0, pP1P0P1);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 21
        l = new V3D_Line_d(pP0N1N1, pP2P1P1);
        expResult = pP1P0P0;
        instance = new V3D_Line_d(pP1P0P0, pP2P0P1);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 22
        l = new V3D_Line_d(pP0P1N1, new V3D_Point_d(env, 2d, 3d, 1d));
        expResult = pP1P2P0;
        instance = new V3D_Line_d(pP1P2P0, pP2P2P1);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 23
        epsilon = 1d / 10000000d;
        l = new V3D_Line_d(pP0P0P0, pP1P1P1);
        instance = new V3D_Line_d(pP0P0P0, pP1P1P1);
        expResult = new V3D_Line_d(pP0P0P0, pP1P1P1);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals(epsilon,
                (V3D_Line_d) result));
        // Test 24
        instance = new V3D_Line_d(pP1P1P1, pP0P0P0);
        expResult = new V3D_Line_d(pP0P0P0, pP1P1P1);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals(epsilon,
                (V3D_Line_d) result));
        // Test 25
        //instance = new V3D_Line_d(P0P1P0, P0N1P0);
        instance = new V3D_Line_d(pP0N1P0, pP0P1P0);
        l = new V3D_Line_d(pP1P1P1, pP0P0P0);
        expResult = pP0P0P0;
        epsilon = 1d / 100000000000d;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals(
                (V3D_Point_d) result, epsilon));
        // Test 26
        instance = new V3D_Line_d(pN1P1P1, pP1N1P1);
        l = new V3D_Line_d(pP0P2P1, pP1P1P1);
        //expResult = null;
        result = instance.getIntersect(l, epsilon);
        //System.out.println(result);
        assertNull(result);
        // Test 27
        l = new V3D_Line_d(pN1N1N1, pP1P1P1);
        instance = new V3D_Line_d(pN1P1P1, pP1N1N1);
        expResult = pP0P0P0;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals(
                (V3D_Point_d) result, epsilon));
        // Test 28
        l = new V3D_Line_d(pN1N1N1, pP1P1P1);
        instance = new V3D_Line_d(pN1P1P1, pP1N1N1);
        expResult = pP0P0P0;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals(
                (V3D_Point_d) result, epsilon));
        // Test 29
        l = new V3D_Line_d(new V3D_Point_d(env, -1d + (0.1d), -1d + (0.1d), -1d),
                new V3D_Point_d(env, 1d + (0.1d), 1d + (0.1d), 1d));
        instance = new V3D_Line_d(new V3D_Point_d(env, -1d + (0.1d), 1d + (0.1d), 1d),
                new V3D_Point_d(env, 1d + (0.1d), -1d + (0.1d), -1d));
        expResult = new V3D_Point_d(env, 0d + (0.1d), 0d + (0.1d), 0d);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals(
                (V3D_Point_d) result, epsilon));
        // Test 30
        epsilon = 1d / 100000d;
        l = new V3D_Line_d(new V3D_Point_d(env, -100d, -100d, 0d),
                new V3D_Point_d(env, 100d, 100d, 0d));
        instance = new V3D_Line_d(new V3D_Point_d(env, -100d, -100d, epsilon),
                new V3D_Point_d(env, 100d, 100d, epsilon));
        expResult = new V3D_Line_d(new V3D_Point_d(env, -100d, -100d, 0d),
                new V3D_Point_d(env, 100d, 100d, 0d));
//        expResult = new V3D_Line_d(new V3D_Point_d(env, -100d, -100d, epsilon),
//                new V3D_Point_d(env, 100d, 100d, epsilon));
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals(epsilon,
                (V3D_Line_d) result));
        // Test 31
        epsilon = 1d / 100000d;
        l = new V3D_Line_d(new V3D_Point_d(env, -100d, -100d, 0d),
                new V3D_Point_d(env, 100d, 100d, 0d));
        instance = new V3D_Line_d(new V3D_Point_d(env, -100d, -100d, epsilon * 2d),
                new V3D_Point_d(env, 100d, 100d, epsilon * 2d));
        result = instance.getIntersect(l, epsilon);
        assertNull(result);
        // Test 32
        epsilon = 1d / 100000d;
        l = new V3D_Line_d(new V3D_Point_d(env, -100d, -100d, 0d),
                new V3D_Point_d(env, 100d, 100d, 0d));
        instance = new V3D_Line_d(new V3D_Point_d(env, -100d, 100d, epsilon),
                new V3D_Point_d(env, 100d, -100d, epsilon));
        expResult = pP0P0P0;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals(
                (V3D_Point_d) result, epsilon));
        // Test 33
        epsilon = 1d / 100000d;
        l = new V3D_Line_d(new V3D_Point_d(env, -100d, -100d, 0d),
                new V3D_Point_d(env, 100d, 100d, 0d));
        instance = new V3D_Line_d(new V3D_Point_d(env, -100d, 100d, epsilon * 2d),
                new V3D_Point_d(env, 100d, -100d, epsilon * 2d));
        result = instance.getIntersect(l, epsilon);
        assertNull(result);
    }

    /**
     * Test of equals method, of class V3D_Line_d.
     */
    @Test
    public void testEquals_double_V3D_Line_d() {
        System.out.println("equals");
        double epsilon = 1d / 10000000d;
        V3D_Line_d l = new V3D_Line_d(pP0P0P0, pP1P1P1);
        V3D_Line_d instance = new V3D_Line_d(pP0P0P0, pP1P1P1);
        assertTrue(instance.equals(epsilon, l));
        // Test 2
        instance = new V3D_Line_d(pP1P1P1, pP0P0P0);
        assertTrue(instance.equals(epsilon, l));
        // Test 3
        l = V3D_Line_d.X_AXIS;
        instance = V3D_Line_d.X_AXIS;
        assertTrue(instance.equals(epsilon, l));
        // Test 4
        instance = V3D_Line_d.Y_AXIS;
        assertFalse(instance.equals(epsilon, l));
    }

    /**
     * Test of isParallelToX0 method, of class V3D_Line_d.
     */
    @Test
    public void testIsParallelToX0() {
        System.out.println("isParallelToX0");
        //double epsilon = 1d / 10000000d;
        V3D_Line_d instance = new V3D_Line_d(pP1P0P0, pP1P1P0);
        assertTrue(instance.isParallelToX0());
        // Test 2
        instance = new V3D_Line_d(pP0P0P0, pP0P1P0);
        assertTrue(instance.isParallelToX0());
        // Test 3
        instance = new V3D_Line_d(pP0P0P1, pP1P1P0);
        assertFalse(instance.isParallelToX0());
        // Test 4
        instance = new V3D_Line_d(pP2N2N1, pP2N1P0);
        assertTrue(instance.isParallelToX0());
    }

    /**
     * Test of isParallelToY0 method, of class V3D_Line_d.
     */
    @Test
    public void testIsParallelToY0() {
        System.out.println("isParallelToY0");
        V3D_Line_d instance = new V3D_Line_d(pP0P1P1, pP0P1N1);
        assertTrue(instance.isParallelToY0());
        // Test 2
        instance = new V3D_Line_d(pP0P0P0, pP0P0P1);
        assertTrue(instance.isParallelToY0());
        // Test 3
        instance = new V3D_Line_d(pP0P0P1, pP1P1P0);
        assertFalse(instance.isParallelToY0());
    }

    /**
     * Test of isParallelToZ0 method, of class V3D_Line_d.
     */
    @Test
    public void testIsParallelToZ0() {
        System.out.println("isParallelToZ0");
        V3D_Line_d instance = new V3D_Line_d(pP0P0P1, pP0P1P1);
        assertTrue(instance.isParallelToZ0());
        // Test 2
        instance = new V3D_Line_d(pP0P0P0, pP1P0P0);
        assertTrue(instance.isParallelToZ0());
        // Test 3
        instance = new V3D_Line_d(pP0P0P1, pP1P1P0);
        assertFalse(instance.isParallelToZ0());
        // Test 4
        instance = new V3D_Line_d(pN1P1P1, pP1N1P0);
        assertFalse(instance.isParallelToZ0());
    }

    /**
     * Test of getAsMatrix method, of class V3D_Line_d.
     */
    @Test
    public void testGetAsMatrix() {
        System.out.println("getAsMatrix");
        V3D_Line_d instance = V3D_Line_d.X_AXIS;
        double[][] m = new double[2][3];
        m[0][0] = 0d;
        m[0][1] = 0d;
        m[0][2] = 0d;
        m[1][0] = 1d;
        m[1][1] = 0d;
        m[1][2] = 0d;
        Math_Matrix_Double expResult = new Math_Matrix_Double(m);
        Math_Matrix_Double result = instance.getAsMatrix();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getDistance method, of class V3D_Line_d.
     */
    @Test
    public void testGetDistance_V3D_Point_d_double() {
        System.out.println("getDistance");
        double epsilon = 1d / 10000000d;
        V3D_Point_d pt;
        V3D_Line_d instance;
        double expResult;
        double result;
        // Test 1
        pt = pP0P0P0;
        instance = new V3D_Line_d(pP1P0P0, pP1P1P0);
        expResult = 1d;
        result = instance.getDistance(pt, epsilon);
        assertTrue(expResult == result);
        // Test 2
        instance = new V3D_Line_d(pP0P1P0, pP1P1P0);
        result = instance.getDistance(pt, epsilon);
        assertTrue(expResult == result);
        // Test 3
        pt = pP1P1P1;
        instance = new V3D_Line_d(pP0P0P0, pP1P1P0);
        result = instance.getDistance(pt, epsilon);
        assertTrue(expResult == result);
        // Test 4
        pt = pP0P1P0;
        instance = new V3D_Line_d(pP0P0P0, pP1P1P0);
        expResult = Math.sqrt(2d) / 2d;
        result = instance.getDistance(pt, epsilon);
        assertTrue(expResult == result);
        // Test 5 https://math.stackexchange.com/a/1658288/756049
        pt = pP1P1P1;
        double third = 1d / 3d;
        instance = new V3D_Line_d(new V3D_Point_d(env, -2d, -4d, 5d), 
                new V3D_Point_d(env, -1d, -2d, 3d));
        V3D_Point_d p2 = new V3D_Point_d(env, third, 2d / 3d, third);
        expResult = p2.getDistance(pt);
        result = instance.getDistance(pt, epsilon);
        assertTrue(expResult == result);
        // Test 6
        instance = new V3D_Line_d(pP0P0P0, pP0P0P1);
        pt = pP0P1P0;
        expResult = 1d;
        result = instance.getDistance(pt, epsilon);
        assertTrue(expResult == result);
        // Test 2
        instance = new V3D_Line_d(pP0P0P0, pP0P0P1);
        pt = new V3D_Point_d(env, 3d, 4d, 0d);
        expResult = 5d;
        result = instance.getDistance(pt, epsilon);
        assertTrue(expResult == result);
        // Test 3
        instance = new V3D_Line_d(pP0P0P1, pP0P0P0);
        pt = new V3D_Point_d(env, 3d, 4d, 0d);
        expResult = 5d;
        result = instance.getDistance(pt, epsilon);
        assertTrue(expResult == result);
        // Test 4
        instance = new V3D_Line_d(pP0P0P0, pP0P0P1);
        pt = new V3D_Point_d(env, 4d, 3d, 0d);
        expResult = 5d;
        result = instance.getDistance(pt, epsilon);
        assertTrue(expResult == result);
        // Test 3
        instance = new V3D_Line_d(pP0P0P0, pP0P0P1);
        pt = new V3D_Point_d(env, 4d, 3d, 10d);
        expResult = 5d;
        result = instance.getDistance(pt, epsilon);
        assertTrue(expResult == result);
    }

    /**
     * Test of getLineOfIntersect method, of class V3D_Line_d.
     */
    @Test
    public void testGetLineOfIntersection_V3D_Point_d_double() {
        System.out.println("getLineOfIntersection");
        double epsilon = 1d / 10000000d;
        V3D_Point_d pt;
        V3D_Line_d instance;
        V3D_LineSegment_d expResult;
        V3D_Geometry_d result;
        // Test 1
        pt = pP0P0P0;
        instance = new V3D_Line_d(pP1P0P0, pP1P1P0);
        expResult = new V3D_LineSegment_d(pP0P0P0, pP1P0P0);
        result = instance.getLineOfIntersect(pt, epsilon);
        assertTrue(expResult.equalsIgnoreDirection(epsilon, (V3D_LineSegment_d) result));
        // Test 2
        instance = new V3D_Line_d(pP1N1P0, pP1P1P0);
        expResult = new V3D_LineSegment_d(pP0P0P0, pP1P0P0);
        //result = instance.getLineOfIntersect(pt, epsilon);
        //System.out.println(result);
        result = instance.getLineOfIntersect(pt, epsilon);
        assertTrue(expResult.equalsIgnoreDirection(epsilon, (V3D_LineSegment_d) result));
    }

    /**
     * Test of getPointOfIntersection method, of class V3D_Line_d. No test:
     * Test covered by {@link #testGetLineOfIntersection_V3D_Point_d()}
     */
    @Test
    public void testGetPointOfIntersection() {
        System.out.println("getPointOfIntersection");
        double epsilon = 1d / 10000000d;
        V3D_Point_d pt = pP2P0P0;
        V3D_Line_d instance = new V3D_Line_d(pP0P0P0, pP0P2P2);
        V3D_Point_d expResult = pP0P0P0;
        V3D_Point_d result = instance.getPointOfIntersect(pt, epsilon);
        assertTrue(expResult.equals(result, epsilon));
        // Test 2
         pt = pN2P0P0;
         instance = new V3D_Line_d(pP0P0P0, pP0P2P2);
         expResult = pP0P0P0;
         result = instance.getPointOfIntersect(pt, epsilon);
        assertTrue(expResult.equals(result, epsilon));
        // Test 3
         pt = pN2P2P0;
         instance = new V3D_Line_d(pP0P0P0, pP0P2P2);
         expResult = pP0P1P1;
         result = instance.getPointOfIntersect(pt, epsilon);
        assertTrue(expResult.equals(result, epsilon));
        // Test 4
         pt = pN2N2P0;
         instance = new V3D_Line_d(pP0P0P0, pP0P2P2);
         expResult = pP0N1N1;
         result = instance.getPointOfIntersect(pt, epsilon);
        assertTrue(expResult.equals(result, epsilon));
    }

    /**
     * Test of getLineOfIntersect method, of class V3D_Line_d.
     */
    @Test
    public void testGetLineOfIntersection_V3D_Line_d_double() {
        System.out.println("getLineOfIntersection");
        double epsilon = 1d / 10000000d;
        V3D_Line_d l0 = new V3D_Line_d(pP1P0P0, pP1P1P0);
        V3D_Line_d l1 = new V3D_Line_d(pP0P0P0, pP0P0P1);
        V3D_LineSegment_d expResult = new V3D_LineSegment_d(pP0P0P0, pP1P0P0);
        V3D_LineSegment_d result = l0.getLineOfIntersect(l1, epsilon);
        assertTrue(expResult.equalsIgnoreDirection(epsilon, result));
        // Test 2
        l1 = new V3D_Line_d(pP0P0P0, pP0P1P0);
        result = l0.getLineOfIntersect(l1, epsilon);
        assertNull(result);
    }

    /**
     * Test of getDistance method, of class V3D_Line_d.
     */
    @Test
    public void testGetDistance_V3D_Line_d_double() {
        System.out.println("getDistance");
        double epsilon = 1d / 100000000d;
        V3D_Line_d l;
        V3D_Line_d instance;
        double expResult;
        double result;
        // Test 1 
        // https://math.stackexchange.com/questions/2213165/find-shortest-distance-between-lines-in-3d
        l = new V3D_Line_d(env, new V3D_Vector_d(2d, 6d, -9d), new V3D_Vector_d(3d, 4d, -4d));
        instance = new V3D_Line_d(env, new V3D_Vector_d(-1d, -2d, 3d), new V3D_Vector_d(2d, -6d, 1d));
        expResult = 4.74020116673185d;
        result = instance.getDistance(l, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 2
        l = new V3D_Line_d(pP0P0P0, pP1P1P0);
        instance = new V3D_Line_d(pP1N1P0, pP2P0P0);
        expResult = Math.sqrt(2d);
        result = instance.getDistance(l, epsilon);
        assertTrue(expResult == result);
    }

    /**
     * Test of toString method, of class V3D_Line_d.
     */
    @Test
    public void testToString_String() {
        System.out.println("toString");
        String pad = "";
        V3D_Line_d instance = V3D_Line_d.X_AXIS;
        String expResult = """
                           V3D_Line_d
                           (
                            offset=V3D_Vector_d
                            (
                             dx=0.0,
                             dy=0.0,
                             dz=0.0
                            )
                            ,
                            p=V3D_Point_d
                            (
                             offset=V3D_Vector_d
                             (
                              dx=0.0,
                              dy=0.0,
                              dz=0.0
                             )
                             ,
                             rel=V3D_Vector_d
                             (
                              dx=0.0,
                              dy=0.0,
                              dz=0.0
                             )
                            )
                            ,
                            v=V3D_Vector_d
                            (
                             dx=1.0,
                             dy=0.0,
                             dz=0.0
                            )
                           )""";
        String result = instance.toString(pad);
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of toStringFields method, of class V3D_Line_d.
     */
    @Test
    public void testToStringSimple() {
        System.out.println("toStringSimple");
        String pad = "";
        V3D_Line_d instance = V3D_Line_d.X_AXIS;
        String expResult = """
                           V3D_Line_d
                           (
                            offset=V3D_Vector_d(dx=0.0, dy=0.0, dz=0.0),
                            p=V3D_Point_d(offset=V3D_Vector_d(dx=0.0, dy=0.0, dz=0.0), rel=V3D_Vector_d(dx=0.0, dy=0.0, dz=0.0)),
                            v= V3D_Vector_d(dx=1.0, dy=0.0, dz=0.0)
                           )""";
        String result = instance.toStringSimple(pad);
        //System.out.println(result);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getP method, of class V3D_Line_d.
     */
    @Test
    public void testGetP() {
        System.out.println("getP");
        V3D_Line_d instance = new V3D_Line_d(pP0P0P0, pP1P0P0);
        V3D_Point_d expResult = pP0P0P0;
        V3D_Point_d result = instance.getP();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of rotate method, of class V3D_Line_d.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        double epsilon = 1d / 10000000d;
        V3D_Ray_d xaxis = new V3D_Ray_d(pP0P0P0, pP1P0P0);
        double Pi = Math.PI;
        double theta = Pi / 2d;
        V3D_Line_d instance = new V3D_Line_d(pP0P0P0, pP1P0P0);
        V3D_Line_d expResult = new V3D_Line_d(pP0P0P0, pP1P0P0);
        V3D_Line_d result = instance.rotate(xaxis, xaxis.l.v, theta, epsilon);
        assertTrue(expResult.equals(epsilon, result));
        // Test 2
        V3D_Ray_d yaxis = new V3D_Ray_d(pP0P0P0, pP0P1P0);
        instance = new V3D_Line_d(pP0P0P0, pP1P0P0);
        expResult = new V3D_Line_d(pP0P0P0, pP0P0N1);
        result = instance.rotate(yaxis, yaxis.l.v, theta, epsilon);
        assertTrue(expResult.equals(epsilon, result));
        // Test 3
        instance = new V3D_Line_d(env, new V3D_Vector_d(0, 0, 0), new V3D_Vector_d(5, 0, 0));
        expResult = new V3D_Line_d(env, new V3D_Vector_d(0, 0, 0), new V3D_Vector_d(0, 0, -5));
        result = instance.rotate(yaxis, yaxis.l.v, theta, epsilon);
        assertTrue(expResult.equals(epsilon, result));
        // Test 4
        theta = Pi;
        instance = new V3D_Line_d(env, new V3D_Vector_d(3, 2, 0), new V3D_Vector_d(5, 0, 0));
        expResult = new V3D_Line_d(env, new V3D_Vector_d(-3, 2, 0), new V3D_Vector_d(-5, 0, 0));
        result = instance.rotate(yaxis, yaxis.l.v, theta, epsilon);
        assertTrue(expResult.equals(epsilon, result));
    }

    /**
     * Test of isCollinear method, of class V3D_Line_d.
     */
    @Test
    public void testIsCollinear_double_V3D_Line_d_V3D_Point_dArr() {
        System.out.println("isCollinear");
        double epsilon = 1d / 10000000d;
        V3D_Line_d l;
        V3D_Point_d[] points = new V3D_Point_d[2];
        // Test 1
        l = new V3D_Line_d(pN1N1N1, pP1P1P1);
        points[0] = pP2P2P2;
        points[1] = pN2N2N2;
        assertTrue(V3D_Line_d.isCollinear(epsilon, l, points));
        // Test 2
        points[1] = pN2N2N1;
        assertFalse(V3D_Line_d.isCollinear(epsilon, l, points));
        // Test 3
        points[0] = pN1N2N1;
        assertFalse(V3D_Line_d.isCollinear(epsilon, l, points));
    }

    /**
     * Test of isCollinear method, of class V3D_Line_d.
     */
    @Test
    public void testIsCollinear_double_V3D_Point_dArr() {
        System.out.println("isCollinear");
        double epsilon = 1d / 10000000d;
        V3D_Point_d[] points = new V3D_Point_d[3];
        points[0] = pP2P2P2;
        points[1] = pP2P2P1;
        points[2] = pP2P2P0;
        assertTrue(V3D_Line_d.isCollinear(epsilon, points));
        points[2] = pP2P2N1;
        assertTrue(V3D_Line_d.isCollinear(epsilon, points));
        points[2] = pP2P2N2;
        assertTrue(V3D_Line_d.isCollinear(epsilon, points));
        // P2P1*
        points[0] = pP2P1P2;
        points[1] = pP2P1P1;
        points[2] = pP2P1P0;
        assertTrue(V3D_Line_d.isCollinear(epsilon, points));
        points[2] = pP2P1N1;
        assertTrue(V3D_Line_d.isCollinear(epsilon, points));
        points[2] = pP2P1N2;
        assertTrue(V3D_Line_d.isCollinear(epsilon, points));
        // P2P0*
        points[0] = pP2P0P2;
        points[1] = pP2P0P1;
        points[2] = pP2P0P0;
        assertTrue(V3D_Line_d.isCollinear(epsilon, points));
        points[2] = pP2P0N1;
        assertTrue(V3D_Line_d.isCollinear(epsilon, points));
        points[2] = pP2P0N2;
        assertTrue(V3D_Line_d.isCollinear(epsilon, points));
        // P2N1*
        points[0] = pP2N1P2;
        points[1] = pP2N1P1;
        points[2] = pP2N1P0;
        assertTrue(V3D_Line_d.isCollinear(epsilon, points));
        points[2] = pP2N1N1;
        assertTrue(V3D_Line_d.isCollinear(epsilon, points));
        points[2] = pP2N1N2;
        assertTrue(V3D_Line_d.isCollinear(epsilon, points));
        // P2N2*
        points[0] = pP2N2P2;
        points[1] = pP2N2P1;
        points[2] = pP2N2P0;
        assertTrue(V3D_Line_d.isCollinear(epsilon, points));
        points[2] = pP2N2N1;
        assertTrue(V3D_Line_d.isCollinear(epsilon, points));
        points[2] = pP2N2N2;
        // Others
        points = new V3D_Point_d[3];
        points[0] = pP2P2P2;
        points[1] = pN2N2N2;
        points[2] = pN1N1N1;
        assertTrue(V3D_Line_d.isCollinear(epsilon, points));
        points[1] = pP1P1P0;
        assertFalse(V3D_Line_d.isCollinear(epsilon, points));
        points = new V3D_Point_d[3];
        points[0] = pP2P2P2;
        points[1] = pN2N2N2;
        points[2] = pP1P1P1;
        assertTrue(V3D_Line_d.isCollinear(epsilon, points));
    }

}
