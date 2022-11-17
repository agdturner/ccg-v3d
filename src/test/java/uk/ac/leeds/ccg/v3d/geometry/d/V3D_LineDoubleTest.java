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
package uk.ac.leeds.ccg.v3d.geometry.d;

import java.math.RoundingMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.math.arithmetic.Math_Double;
import uk.ac.leeds.ccg.math.matrices.Math_Matrix_Double;

/**
 * Test class for V3D_LineDouble.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_LineDoubleTest extends V3D_DoubleTest {

    public V3D_LineDoubleTest() {
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
     * Test of toString method, of class V3D_LineDouble.
     */
    @Test
    public void testToString_0args() {
        System.out.println("toString");
        V3D_LineDouble instance = new V3D_LineDouble(pP0P0P0, pP1P0P0);
        String expResult = "V3D_LineDouble\n"
                + "(\n"
                + " offset=V3D_VectorDouble(dx=0.0, dy=0.0, dz=0.0),\n"
                + " p=V3D_PointDouble(offset=V3D_VectorDouble(dx=0.0, dy=0.0, dz=0.0),"
                + " rel=V3D_VectorDouble(dx=0.0, dy=0.0, dz=0.0)),\n"
                + " v= V3D_VectorDouble(dx=1.0, dy=0.0, dz=0.0)\n"
                + ")";
        String result = instance.toString();
        //System.out.println(result);
        assertTrue(expResult.equalsIgnoreCase(result));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_LineDouble.
     */
    @Test
    public void testIsIntersectedBy_V3D_PointDouble_int_RoundingMode() {
        System.out.println("isIntersectedBy");
        double epsilon = 1d / 10000000d;
        V3D_PointDouble pt = pP0P0P0;
        V3D_LineDouble instance = new V3D_LineDouble(pN1N1N1, pP1P1P1);
        assertTrue(instance.isIntersectedBy(pt, epsilon));
        // Test 2
        pt = new V3D_PointDouble(P0_1E2, P0_1E2, P0_1E2);
        assertTrue(instance.isIntersectedBy(pt, epsilon));
        // Test 3 works as the rounding puts pt on the line.
        pt = new V3D_PointDouble(P0_1E12, P0_1E12, P0_1E12);
        assertTrue(instance.isIntersectedBy(pt, epsilon));
        // Test 4 works as the rounding puts pt on the line.
        pt = new V3D_PointDouble(N0_1E12, N0_1E12, N0_1E12);
        assertTrue(instance.isIntersectedBy(pt, epsilon));
        // Test 5 works as the rounding puts pt on the line.
        double a = P0_1E2 + P1E12;
        pt = new V3D_PointDouble(a, a, a);
        assertTrue(instance.isIntersectedBy(pt, epsilon));
        // Test 6 works as the rounding puts pt on the line.
        a = N0_1E2 + N1E12;
        pt = new V3D_PointDouble(a, a, a);
        assertTrue(instance.isIntersectedBy(pt, epsilon));
        // Test 7
        instance = new V3D_LineDouble(pP0N1N1, pP2P1P1);
        pt = new V3D_PointDouble(-1d, -2d, -2d);
        assertTrue(instance.isIntersectedBy(pt, epsilon));
        // Test 8 fails as the rounding does not put pt on the line.
        a = N0_1E2 + N1E12;
        pt = new V3D_PointDouble(a, a, a);
        assertFalse(instance.isIntersectedBy(pt, epsilon));
        pt = new V3D_PointDouble(a + 1d, a, a);
        assertTrue(instance.isIntersectedBy(pt, epsilon));
//        // Test 9 Results seem somewhat contrary. It would not matter if these 
//        // results came out differently. The point is that oom is not set 
//        // to be sufficiently sensitive, so we get intersections reported that 
//        // are not wanted.  
//        a = N0_1E12+(N1E12);
//        pt = new V3D_PointDouble(a, a, a);
//        assertTrue(instance.isIntersectedBy(pt, epsilon)); // True as rounding in the cross product calculation is too general.
//        pt = new V3D_PointDouble(a+(BigInteger.ONE), a, a);
//        assertTrue(instance.isIntersectedBy(pt, epsilon)); // True as rounding in the cross product calculation is too general, but it should be true anyway!
        // Test 10 This is like test 9, but the oom is set appropriately so the
        // coordinate and crossproduct rounding work fine.
        a = N0_1E12 + N1E12;
        pt = new V3D_PointDouble(a, a, a);
        assertFalse(instance.isIntersectedBy(pt, epsilon));
        pt = new V3D_PointDouble(a + 1d, a, a);
        assertTrue(instance.isIntersectedBy(pt, epsilon));
    }

    /**
     * Test of isParallel method, of class V3D_LineDouble.
     */
    @Test
    public void testIsParallel() {
        System.out.println("isParallel");
        double epsilon = 1d / 10000000d;
        V3D_LineDouble l = V3D_LineDouble.X_AXIS;
        V3D_LineDouble instance = V3D_LineDouble.X_AXIS;
        assertTrue(instance.isParallel(l, epsilon));
        // Test 2
        instance = V3D_LineDouble.Y_AXIS;
        assertFalse(instance.isParallel(l, epsilon));
        // Test 3
        instance = V3D_LineDouble.Z_AXIS;
        assertFalse(instance.isParallel(l, epsilon));
        // Test 4
        instance = new V3D_LineDouble(pP0P1P0, pP1P1P0);
        assertTrue(instance.isParallel(l, epsilon));
        // Test 5
        instance = new V3D_LineDouble(pP0P1P0, pP1P1P0);
        assertTrue(instance.isParallel(l, epsilon));
        // Test 6
        instance = new V3D_LineDouble(pP0P0P1, pP1P0P1);
        assertTrue(instance.isParallel(l, epsilon));
        // Test 7
        instance = new V3D_LineDouble(pP1P0P1, pP0P0P1);
        assertTrue(instance.isParallel(l, epsilon));
        // Test 8
        instance = new V3D_LineDouble(pP1P0P1, pP0P1P1);
        assertFalse(instance.isParallel(l, epsilon));
        // Test 9
        l = V3D_LineDouble.Y_AXIS;
        instance = new V3D_LineDouble(pP0P0P1, pP0P1P1);
        assertTrue(instance.isParallel(l, epsilon));
        // Test 9
        instance = new V3D_LineDouble(pP1P0P0, pP1P1P0);
        assertTrue(instance.isParallel(l, epsilon));
        // Test 10
        instance = new V3D_LineDouble(pP1P0P1, pP1P1P1);
        assertTrue(instance.isParallel(l, epsilon));
        // Test 11
        instance = new V3D_LineDouble(pP1P0P1, pP1P1P0);
        assertFalse(instance.isParallel(l, epsilon));
        // Test 12
        l = V3D_LineDouble.Z_AXIS;
        instance = new V3D_LineDouble(pP1P0P0, pP1P0P1);
        assertTrue(instance.isParallel(l, epsilon));
        // Test 9
        instance = new V3D_LineDouble(pP0P1P0, pP0P1P1);
        assertTrue(instance.isParallel(l, epsilon));
        // Test 10
        instance = new V3D_LineDouble(pP1P1P0, pP1P1P1);
        assertTrue(instance.isParallel(l, epsilon));
        // Test 11
        instance = new V3D_LineDouble(pP1P0P1, pP1P1P0);
        assertFalse(instance.isParallel(l, epsilon));
        // Test 12
        l = new V3D_LineDouble(pP1P1P1, pN1N1N1);
        instance = new V3D_LineDouble(pP1N1P1, pN1P1N1);
        assertFalse(instance.isParallel(l, epsilon));
        // Test 13
        double a = P0_1E12 + P1E12;
        double b = N0_1E12 + N1E12;
        double a1 = P0_1E12 + P1E12 + 1d;
        double b1 = N0_1E12 + N1E12 + 1d;
        l = new V3D_LineDouble(new V3D_PointDouble(a, a, a), new V3D_PointDouble(b, b, b));
        instance = new V3D_LineDouble(new V3D_PointDouble(a1, a, a), new V3D_PointDouble(b1, b, b));
        epsilon = 1d / 100000d;
        assertTrue(instance.isParallel(l, epsilon));
        // Test 14
        a = P0_1E12 + P1E12;
        b = N0_1E12 + N1E12;
        a1 = P0_1E12 + P1E12 + 10d;
        b1 = N0_1E12 + N1E12 + 10d;
        l = new V3D_LineDouble(new V3D_PointDouble(a, a, a), new V3D_PointDouble(b, b, b));
        instance = new V3D_LineDouble(new V3D_PointDouble(a1, a, a),
                new V3D_PointDouble(b1, b, b));
        assertTrue(instance.isParallel(l, epsilon));
    }

    /**
     * Test of getIntersection method, of class V3D_LineDouble.
     */
    @Test
    public void testGetIntersection() {
        System.out.println("getIntersection");
        double epsilon = 1d / 10000000d;
        V3D_LineDouble l;
        V3D_LineDouble instance;
        V3D_GeometryDouble expResult;
        V3D_GeometryDouble result;
        // Test 1
        //l = new V3D_LineDouble(N1N1N1, P1P1P1);
        l = new V3D_LineDouble(pP1P1P1, pN1N1N1);
        //instance = new V3D_LineDouble(N1P1N1, P1N1P1);
        instance = new V3D_LineDouble(pP1N1P1, pN1P1N1);
        expResult = pP0P0P0;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 2
        l = new V3D_LineDouble(pN1N1N1, pP1P1P1);
        instance = new V3D_LineDouble(pP1P1P0, pP1P1P2);
        expResult = pP1P1P1;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 3
        expResult = pP0P0P0;
        instance = new V3D_LineDouble(pN1N1P0, pP1P1P0);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 4
        l = new V3D_LineDouble(pN1N1N1, pP1P1P1);
        instance = new V3D_LineDouble(new V3D_VectorDouble(3d, 1d, 1d), new V3D_VectorDouble(1d, 3d, 3d));
        expResult = pP2P2P2;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 5
        l = new V3D_LineDouble(pN1N1P0, pP1P1P0);
        instance = new V3D_LineDouble(new V3D_VectorDouble(3d, 3d, 0d), new V3D_VectorDouble(3d, 3d, -1d));
        expResult = new V3D_PointDouble(3d, 3d, 0d);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 6
        l = new V3D_LineDouble(pN1N1N1, pP1P1P1);
        instance = new V3D_LineDouble(pP1N1N1, pN1P1P1);
        expResult = pP0P0P0;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 7
        l = new V3D_LineDouble(pP0P0P0, pP1P1P1);
        instance = new V3D_LineDouble(pP1N1N1, pN1P1P1);
        expResult = pP0P0P0;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 8
        l = new V3D_LineDouble(pN1N1N1, pP0P0P0);
        instance = new V3D_LineDouble(pP1N1N1, pN1P1P1);
        expResult = pP0P0P0;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 9
        l = new V3D_LineDouble(pN2N2N2, pN1N1N1);
        instance = new V3D_LineDouble(pP1N1N1, pP0P0P0);
        expResult = pP0P0P0;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 10
        l = new V3D_LineDouble(pN2N2N2, pN1N1N1);
        instance = new V3D_LineDouble(pP0P0P0, pN1P1P1);
        expResult = pP0P0P0;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 12 to 14
        // v.dx = 0, v.dy != 0, v.dz !=0
        // Test 11
        l = new V3D_LineDouble(pN1N1N1, pP1P1P1);
        expResult = pP0P0P0;
        instance = new V3D_LineDouble(pP0P0P0, pP0P1P1);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 15
        l = new V3D_LineDouble(pP0N1N1, pP2P1P1);
        expResult = pP1P0P0;
        instance = new V3D_LineDouble(pP1P0P0, pP1P1P1);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 16
        l = new V3D_LineDouble(P0N1P1, new V3D_VectorDouble(2d, 1d, 3d));
        expResult = pP1P0P2;
        instance = new V3D_LineDouble(new V3D_VectorDouble(1d, 0d, 2d),
                new V3D_VectorDouble(1d, 1d, 3d));
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 17 to 18
        // v.dx != 0, v.dy = 0, v.dz = 0
        // Test 17
        l = new V3D_LineDouble(pN1N1N1, pP1P1P1);
        expResult = pP0P0P0;
        instance = new V3D_LineDouble(pP0P0P0, pP1P0P0);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 18
        l = new V3D_LineDouble(pP0N1N1, pP2P1P1);
        expResult = pP1P0P0;
        instance = new V3D_LineDouble(pP1P0P0, pP2P0P0);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 19
        l = new V3D_LineDouble(pP0N1P0, pP2P1P2);
        expResult = pP1P0P1;
        instance = new V3D_LineDouble(pP1P0P1, pP2P0P1);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 20 to 21
        // v.dx != 0, v.dy = 0, v.dz != 0
        // Test 20
        l = new V3D_LineDouble(pN1N1N1, pP1P1P1);
        expResult = pP0P0P0;
        instance = new V3D_LineDouble(pP0P0P0, pP1P0P1);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 21
        l = new V3D_LineDouble(pP0N1N1, pP2P1P1);
        expResult = pP1P0P0;
        instance = new V3D_LineDouble(pP1P0P0, pP2P0P1);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 22
        l = new V3D_LineDouble(P0P1N1, new V3D_VectorDouble(2d, 3d, 1d));
        expResult = pP1P2P0;
        instance = new V3D_LineDouble(pP1P2P0, pP2P2P1);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
    }

    /**
     * Test of equals method, of class V3D_LineDouble.
     */
    @Test
    public void testEquals_V3D_LineDouble() {
        System.out.println("equals");
        double epsilon = 1d / 10000000d;
        V3D_LineDouble l = new V3D_LineDouble(pP0P0P0, pP1P1P1);
        V3D_LineDouble instance = new V3D_LineDouble(pP0P0P0, pP1P1P1);
        assertTrue(instance.equals(l, epsilon));
        // Test 2
        instance = new V3D_LineDouble(pP1P1P1, pP0P0P0);
        assertTrue(instance.equals(l, epsilon));
        // Test 3
        l = V3D_LineDouble.X_AXIS;
        instance = V3D_LineDouble.X_AXIS;
        assertTrue(instance.equals(l, epsilon));
        // Test 4
        instance = V3D_LineDouble.Y_AXIS;
        assertFalse(instance.equals(l, epsilon));
    }

    /**
     * Test of getIntersection method, of class V3D_LineDouble. A useful tool
     * for creating test cases:
     * https://www.mathepower.com/en/lineintersection.php
     */
    @Test
    public void testGetIntersection_V3D_LineDouble_int() {
        System.out.println("getIntersection");
        double epsilon = 1d / 10000000d;
        V3D_LineDouble l = new V3D_LineDouble(pP0P0P0, pP1P1P1);
        V3D_LineDouble instance = new V3D_LineDouble(pP0P0P0, pP1P1P1);
        V3D_GeometryDouble expResult = new V3D_LineDouble(pP0P0P0, pP1P1P1);
        V3D_GeometryDouble result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));
        // Test 2
        instance = new V3D_LineDouble(pP1P1P1, pP0P0P0);
        expResult = new V3D_LineDouble(pP0P0P0, pP1P1P1);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));
        // Test 3
        //instance = new V3D_LineDouble(P0P1P0, P0N1P0);
        instance = new V3D_LineDouble(pP0N1P0, pP0P1P0);
        l = new V3D_LineDouble(pP1P1P1, pP0P0P0);
        expResult = pP0P0P0;
        epsilon = 1d / 100000000000d;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result, epsilon));
        // Test 4
        instance = new V3D_LineDouble(pN1P1P1, pP1N1P1);
        l = new V3D_LineDouble(pP0P2P1, pP1P1P1);
        //expResult = null;
        result = instance.getIntersection(l, epsilon);
        //System.out.println(result);
        assertNull(result);
        // Test 5
        l = new V3D_LineDouble(pN1N1N1, pP1P1P1);
        instance = new V3D_LineDouble(pN1P1P1, pP1N1N1);
        expResult = pP0P0P0;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result, epsilon));
        // Test 6
        l = new V3D_LineDouble(pN1N1N1, pP1P1P1);
        instance = new V3D_LineDouble(pN1P1P1, pP1N1N1);
        expResult = pP0P0P0;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result, epsilon));
        // Test 7
        l = new V3D_LineDouble(new V3D_PointDouble(-1d + (0.1d), -1d + (0.1d), -1d),
                new V3D_PointDouble(1d + (0.1d), 1d + (0.1d), 1d));
        instance = new V3D_LineDouble(new V3D_PointDouble(-1d + (0.1d), 1d + (0.1d), 1d),
                new V3D_PointDouble(1d + (0.1d), -1d + (0.1d), -1d));
        expResult = new V3D_PointDouble(0d + (0.1d), 0d + (0.1d), 0d);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result, epsilon));
    }

    /**
     * Test of isParallelToX0 method, of class V3D_LineDouble.
     */
    @Test
    public void testIsParallelToX0() {
        System.out.println("isParallelToX0");
        //double epsilon = 1d / 10000000d;
        V3D_LineDouble instance = new V3D_LineDouble(pP1P0P0, pP1P1P0);
        assertTrue(instance.isParallelToX0());
        // Test 1
        instance = new V3D_LineDouble(pP0P0P0, pP0P1P0);
        assertTrue(instance.isParallelToX0());
        // Test 2
        instance = new V3D_LineDouble(pP0P0P1, pP1P1P0);
        assertFalse(instance.isParallelToX0());
    }

    /**
     * Test of isParallelToY0 method, of class V3D_LineDouble.
     */
    @Test
    public void testIsParallelToY0() {
        System.out.println("isParallelToY0");
        V3D_LineDouble instance = new V3D_LineDouble(pP0P1P1, pP0P1N1);
        assertTrue(instance.isParallelToY0());
    }

    /**
     * Test of isParallelToZ0 method, of class V3D_LineDouble.
     */
    @Test
    public void testIsParallelToZ0() {
        System.out.println("isParallelToZ0");
        V3D_LineDouble instance = new V3D_LineDouble(pP0P0P1, pP0P1P1);
        assertTrue(instance.isParallelToZ0());
    }

    /**
     * Test of getAsMatrix method, of class V3D_LineDouble.
     */
    @Test
    public void testGetAsMatrix() {
        System.out.println("getAsMatrix");
        V3D_LineDouble instance = V3D_LineDouble.X_AXIS;
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
     * Test of getDistance method, of class V3D_LineDouble.
     */
    @Test
    public void testGetDistance_V3D_PointDouble() {
        System.out.println("getDistance");
        double epsilon = 1d / 10000000d;
        V3D_PointDouble pt;
        V3D_LineDouble instance;
        double expResult;
        double result;
        // Test 1
        pt = pP0P0P0;
        instance = new V3D_LineDouble(pP1P0P0, pP1P1P0);
        expResult = 1d;
        result = instance.getDistance(pt, epsilon);
        assertTrue(expResult == result);
        // Test 2
        instance = new V3D_LineDouble(pP0P1P0, pP1P1P0);
        result = instance.getDistance(pt, epsilon);
        assertTrue(expResult == result);
        // Test 3
        pt = pP1P1P1;
        instance = new V3D_LineDouble(pP0P0P0, pP1P1P0);
        result = instance.getDistance(pt, epsilon);
        assertTrue(expResult == result);
        // Test 4
        pt = pP0P1P0;
        instance = new V3D_LineDouble(pP0P0P0, pP1P1P0);
        expResult = Math.sqrt(2d) / 2d;
        result = instance.getDistance(pt, epsilon);
        assertTrue(expResult == result);
        // Test 5 https://math.stackexchange.com/a/1658288/756049
        pt = pP1P1P1;
        double third = 1d / 3d;
        instance = new V3D_LineDouble(new V3D_VectorDouble(-2d, -4d, 5d), new V3D_VectorDouble(-1d, -2d, 3d));
        V3D_PointDouble p2 = new V3D_PointDouble(third, 2d / 3d, third);
        expResult = p2.getDistance(pt);
        result = instance.getDistance(pt, epsilon);
        assertTrue(expResult == result);
        // Test 6
        instance = new V3D_LineDouble(pP0P0P0, pP0P0P1);
        pt = pP0P1P0;
        expResult = 1d;
        result = instance.getDistance(pt, epsilon);
        assertTrue(expResult == result);
        // Test 2
        instance = new V3D_LineDouble(pP0P0P0, pP0P0P1);
        pt = new V3D_PointDouble(3d, 4d, 0d);
        expResult = 5d;
        result = instance.getDistance(pt, epsilon);
        assertTrue(expResult == result);
        // Test 3
        instance = new V3D_LineDouble(pP0P0P1, pP0P0P0);
        pt = new V3D_PointDouble(3d, 4d, 0d);
        expResult = 5d;
        result = instance.getDistance(pt, epsilon);
        assertTrue(expResult == result);
        // Test 4
        instance = new V3D_LineDouble(pP0P0P0, pP0P0P1);
        pt = new V3D_PointDouble(4d, 3d, 0d);
        expResult = 5d;
        result = instance.getDistance(pt, epsilon);
        assertTrue(expResult == result);
        // Test 3
        instance = new V3D_LineDouble(pP0P0P0, pP0P0P1);
        pt = new V3D_PointDouble(4d, 3d, 10d);
        expResult = 5d;
        result = instance.getDistance(pt, epsilon);
        assertTrue(expResult == result);
    }

    /**
     * Test of getLineOfIntersection method, of class V3D_LineDouble.
     */
    @Test
    public void testGetLineOfIntersection_V3D_PointDouble_int() {
        System.out.println("getLineOfIntersection");
        double epsilon = 1d / 10000000d;
        V3D_PointDouble pt;
        V3D_LineDouble instance;
        V3D_LineSegmentDouble expResult;
        V3D_GeometryDouble result;
        // Test 1
        pt = pP0P0P0;
        instance = new V3D_LineDouble(pP1P0P0, pP1P1P0);
        expResult = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
        result = instance.getLineOfIntersection(pt, epsilon);
        assertTrue(expResult.equals((V3D_LineSegmentDouble) result, epsilon));
        // Test 2
        instance = new V3D_LineDouble(pP1N1P0, pP1P1P0);
        expResult = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
        //result = instance.getLineOfIntersection(pt, epsilon);
        //System.out.println(result);
        result = instance.getLineOfIntersection(pt, epsilon);
        assertTrue(expResult.equals((V3D_LineSegmentDouble) result, epsilon));
    }

    /**
     * Test of getPointOfIntersection method, of class V3D_LineDouble. No test:
     * Test covered by {@link #testGetLineOfIntersection_V3D_PointDouble()}
     */
    @Test
    public void testGetPointOfIntersection() {
        System.out.println("getPointOfIntersection");
        double epsilon = 1d / 10000000d;
        V3D_PointDouble pt = pP2P0P0;
        V3D_LineDouble instance = new V3D_LineDouble(pP0P0P0, pP0P2P2);
        V3D_PointDouble expResult = pP0P0P0;
        V3D_PointDouble result = instance.getPointOfIntersection(pt, epsilon);
        assertTrue(expResult.equals(result));
        // Test 2
        // ...
    }

    /**
     * Test of getLineOfIntersection method, of class V3D_LineDouble.
     */
    @Test
    public void testGetLineOfIntersection_V3D_LineDouble_int() {
        System.out.println("getLineOfIntersection");
        double epsilon = 1d / 10000000d;
        V3D_LineDouble l0 = new V3D_LineDouble(pP1P0P0, pP1P1P0);
        V3D_LineDouble l1 = new V3D_LineDouble(pP0P0P0, pP0P0P1);
        V3D_LineSegmentDouble expResult = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
        V3D_LineSegmentDouble result = l0.getLineOfIntersection(l1, epsilon);
        assertTrue(expResult.equalsIgnoreDirection(result, epsilon));
        // Test 2
        l1 = new V3D_LineDouble(pP0P0P0, pP0P1P0);
        result = l0.getLineOfIntersection(l1, epsilon);
        assertNull(result);
    }

    /**
     * Test of getDistance method, of class V3D_LineDouble.
     */
    @Test
    public void testGetDistance_V3D_LineDouble_double() {
        System.out.println("getDistance");
        double epsilon = 1d / 100000000d;
        V3D_LineDouble l;
        V3D_LineDouble instance;
        double expResult;
        double result;
        // Test 1 
        // https://math.stackexchange.com/questions/2213165/find-shortest-distance-between-lines-in-3d
        l = new V3D_LineDouble(new V3D_VectorDouble(2d, 6d, -9d), new V3D_VectorDouble(3d, 4d, -4d), true);
        instance = new V3D_LineDouble(new V3D_VectorDouble(-1d, -2d, 3d), new V3D_VectorDouble(2d, -6d, 1d), true);
        expResult = 4.74020116673185d;
        result = instance.getDistance(l, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 2
        l = new V3D_LineDouble(pP0P0P0, pP1P1P0);
        instance = new V3D_LineDouble(pP1N1P0, pP2P0P0);
        expResult = Math.sqrt(2d);
        result = instance.getDistance(l, epsilon);
        assertTrue(expResult == result);
    }

    /**
     * Test of toString method, of class V3D_LineDouble.
     */
    @Test
    public void testToString_String() {
        System.out.println("toString");
        String pad = "";
        V3D_LineDouble instance = V3D_LineDouble.X_AXIS;
        String expResult = "V3D_LineDouble\n"
                + "(\n"
                + " offset=V3D_VectorDouble\n"
                + " (\n"
                + "  dx=0.0,\n"
                + "  dy=0.0,\n"
                + "  dz=0.0\n"
                + " )\n"
                + " ,\n"
                + " p=V3D_PointDouble\n"
                + " (\n"
                + "  offset=V3D_VectorDouble\n"
                + "  (\n"
                + "   dx=0.0,\n"
                + "   dy=0.0,\n"
                + "   dz=0.0\n"
                + "  )\n"
                + "  ,\n"
                + "  rel=V3D_VectorDouble\n"
                + "  (\n"
                + "   dx=0.0,\n"
                + "   dy=0.0,\n"
                + "   dz=0.0\n"
                + "  )\n"
                + " )\n"
                + " ,\n"
                + " v=V3D_VectorDouble\n"
                + " (\n"
                + "  dx=1.0,\n"
                + "  dy=0.0,\n"
                + "  dz=0.0\n"
                + " )\n"
                + ")";
        String result = instance.toString(pad);
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of toStringFields method, of class V3D_LineDouble.
     */
    @Test
    public void testToStringFields() {
        System.out.println("toStringFields");
        String pad = "";
        V3D_LineDouble instance = V3D_LineDouble.X_AXIS;
        String expResult = "offset=V3D_VectorDouble\n"
                + "(\n"
                + " dx=0.0,\n"
                + " dy=0.0,\n"
                + " dz=0.0\n"
                + ")\n"
                + ",\n"
                + "p=V3D_PointDouble\n"
                + "(\n"
                + " offset=V3D_VectorDouble\n"
                + " (\n"
                + "  dx=0.0,\n"
                + "  dy=0.0,\n"
                + "  dz=0.0\n"
                + " )\n"
                + " ,\n"
                + " rel=V3D_VectorDouble\n"
                + " (\n"
                + "  dx=0.0,\n"
                + "  dy=0.0,\n"
                + "  dz=0.0\n"
                + " )\n"
                + ")\n"
                + ",\n"
                + "v=V3D_VectorDouble\n"
                + "(\n"
                + " dx=1.0,\n"
                + " dy=0.0,\n"
                + " dz=0.0\n"
                + ")";
        String result = instance.toStringFields(pad);
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getP method, of class V3D_LineDouble.
     */
    @Test
    public void testGetP_int() {
        System.out.println("getP");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_LineDouble instance = new V3D_LineDouble(pP0P0P0, pP1P0P0);
        V3D_PointDouble expResult = pP0P0P0;
        V3D_PointDouble result = instance.getP();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of rotate method, of class V3D_LineDouble.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        double epsilon = 1d / 10000000d;
        V3D_LineDouble axis = V3D_LineDouble.X_AXIS;
        double Pi = Math.PI;
        double theta = Pi / 2d;
        V3D_LineDouble instance = new V3D_LineDouble(pP0P0P0, pP1P0P0);
        V3D_LineDouble expResult = new V3D_LineDouble(pP0P0P0, pP1P0P0);
        V3D_LineDouble result = instance.rotate(axis, theta, epsilon);
        assertTrue(expResult.equals(result, epsilon));
        // Test 2
        axis = new V3D_LineDouble(pP0P0P0, V3D_VectorDouble.J);
        instance = new V3D_LineDouble(pP0P0P0, pP1P0P0);
        expResult = new V3D_LineDouble(pP0P0P0, pP0P0P1);
        result = instance.rotate(axis, theta, epsilon);
        assertTrue(expResult.equals(result, epsilon));
        // Test 3
        instance = new V3D_LineDouble(new V3D_VectorDouble(0, 0, 0), new V3D_VectorDouble(5, 0, 0));
        expResult = new V3D_LineDouble(new V3D_VectorDouble(0, 0, 0), new V3D_VectorDouble(0, 0, 5));
        result = instance.rotate(axis, theta, epsilon);
        assertTrue(expResult.equals(result, epsilon));
        // Test 4
        theta = Pi;
        instance = new V3D_LineDouble(new V3D_VectorDouble(3, 2, 0), new V3D_VectorDouble(5, 0, 0));
        expResult = new V3D_LineDouble(new V3D_VectorDouble(-3, 2, 0), new V3D_VectorDouble(-5, 0, 0));
        result = instance.rotate(axis, theta, epsilon);
        assertTrue(expResult.equals(result, epsilon));
    }

    /**
     * Test of isCoincident method, of class V3D_Geometrics.
     */
    @Test
    public void testIsCoincident() {
        System.out.println("isCoincident");
        V3D_PointDouble[] points = new V3D_PointDouble[2];
        points[0] = pP0P0P0;
        points[1] = pP0P0P0;
        assertTrue(V3D_PointDouble.isCoincident(points));
        points[1] = pP0P0P1;
        assertFalse(V3D_PointDouble.isCoincident(points));
        points[0] = pP0P0P1;
        assertTrue(V3D_PointDouble.isCoincident(points));
    }

    /**
     * Test of isCollinear method, of class V3D_Geometrics.
     */
    @Test
    public void testIsCollinear_V3D_LineDouble_V3D_PointDoubleArr() {
        System.out.println("isCollinear");
        double epsilon = 1d / 10000000d;
        V3D_LineDouble l;
        V3D_PointDouble[] points = new V3D_PointDouble[2];
        // Test 1
        l = new V3D_LineDouble(pN1N1N1, pP1P1P1);
        points[0] = pP2P2P2;
        points[1] = pN2N2N2;
        assertTrue(V3D_LineDouble.isCollinear(l, epsilon, points));
        // Test 2
        points[1] = pN2N2N1;
        assertFalse(V3D_LineDouble.isCollinear(l, epsilon, points));
        // Test 3
        points[0] = pN1N2N1;
        assertFalse(V3D_LineDouble.isCollinear(l, epsilon, points));
    }

    /**
     * Test of isCollinear method, of class V3D_Geometrics.
     */
    @Test
    public void testIsCollinear_V3D_PointDoubleArr() {
        System.out.println("isCollinear");
        double epsilon = 1d / 10000000d;
        V3D_PointDouble[] points = new V3D_PointDouble[3];
        points[0] = pP2P2P2;
        points[1] = pP2P2P1;
        points[2] = pP2P2P0;
        assertTrue(V3D_LineDouble.isCollinear(epsilon, points));
        points[2] = pP2P2N1;
        assertTrue(V3D_LineDouble.isCollinear(epsilon, points));
        points[2] = pP2P2N2;
        assertTrue(V3D_LineDouble.isCollinear(epsilon, points));
        // P2P1*
        points[0] = pP2P1P2;
        points[1] = pP2P1P1;
        points[2] = pP2P1P0;
        assertTrue(V3D_LineDouble.isCollinear(epsilon, points));
        points[2] = pP2P1N1;
        assertTrue(V3D_LineDouble.isCollinear(epsilon, points));
        points[2] = pP2P1N2;
        assertTrue(V3D_LineDouble.isCollinear(epsilon, points));
        // P2P0*
        points[0] = pP2P0P2;
        points[1] = pP2P0P1;
        points[2] = pP2P0P0;
        assertTrue(V3D_LineDouble.isCollinear(epsilon, points));
        points[2] = pP2P0N1;
        assertTrue(V3D_LineDouble.isCollinear(epsilon, points));
        points[2] = pP2P0N2;
        assertTrue(V3D_LineDouble.isCollinear(epsilon, points));
        // P2N1*
        points[0] = pP2N1P2;
        points[1] = pP2N1P1;
        points[2] = pP2N1P0;
        assertTrue(V3D_LineDouble.isCollinear(epsilon, points));
        points[2] = pP2N1N1;
        assertTrue(V3D_LineDouble.isCollinear(epsilon, points));
        points[2] = pP2N1N2;
        assertTrue(V3D_LineDouble.isCollinear(epsilon, points));
        // P2N2*
        points[0] = pP2N2P2;
        points[1] = pP2N2P1;
        points[2] = pP2N2P0;
        assertTrue(V3D_LineDouble.isCollinear(epsilon, points));
        points[2] = pP2N2N1;
        assertTrue(V3D_LineDouble.isCollinear(epsilon, points));
        points[2] = pP2N2N2;
        // Others
        points = new V3D_PointDouble[3];
        points[0] = pP2P2P2;
        points[1] = pN2N2N2;
        points[2] = pN1N1N1;
        assertTrue(V3D_LineDouble.isCollinear(epsilon, points));
        points[1] = pP1P1P0;
        assertFalse(V3D_LineDouble.isCollinear(epsilon, points));
        points = new V3D_PointDouble[3];
        points[0] = pP2P2P2;
        points[1] = pN2N2N2;
        points[2] = pP1P1P1;
        assertTrue(V3D_LineDouble.isCollinear(epsilon, points));
    }

}
