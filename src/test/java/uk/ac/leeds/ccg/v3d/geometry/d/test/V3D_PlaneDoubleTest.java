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
import org.junit.jupiter.api.Disabled;
import uk.ac.leeds.ccg.math.arithmetic.Math_Double;
import uk.ac.leeds.ccg.math.matrices.Math_Matrix_Double;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_GeometryDouble;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_LineDouble;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_LineSegmentDouble;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_PlaneDouble;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_PointDouble;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_RayDouble;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_VectorDouble;

/**
 * Test of V3D_PlaneDouble class.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_PlaneDoubleTest extends V3D_DoubleTest {

    public V3D_PlaneDoubleTest() {
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
//        testIsOnPlane_V3D_PointDouble();
//        testIsOnPlane_V3D_PointDouble();
//        testIsOnPlane_V3D_LineSegmentDouble();
//        testEquals();
//        testIsIntersectedBy_V3D_PointDouble();
//        testIsIntersectedBy_V3D_LineDouble();
//        testIsIntersectedBy_V3D_PlaneDouble();
//        testGetNormalVector();
//        testIsParallel_V3D_PlaneDouble();
//        testIsParallel_V3D_LineDouble();
//
//        testGetIntersection_V3D_PlaneDouble();
    }

    /**
     * Test of toString method, of class V3D_PlaneDouble.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        V3D_PlaneDouble instance = new V3D_PlaneDouble(pP0P0P0, pP1P1P1, pP1P0P0);
        String expResult = """
                           V3D_PlaneDouble(
                            offset=V3D_VectorDouble(dx=0.0, dy=0.0, dz=0.0),
                            p= V3D_VectorDouble(dx=0.0, dy=0.0, dz=0.0),
                            n= V3D_VectorDouble(dx=0.0, dy=1.0, dz=-1.0))""";
        String result = instance.toString();
        //System.out.println(result);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of toString method, of class V3D_PlaneDouble.
     */
    @Test
    public void testGetEquation() {
        System.out.println("getEquation");
        V3D_PlaneDouble instance;
        String expResult;
        String result;
        // Test 1
        instance = new V3D_PlaneDouble(pP0P0P0, pP1P1P1, pP1P0P0);
        expResult = "y=z";
        result = instance.getEquationString();
        assertTrue(expResult.equalsIgnoreCase(result));
        // Test 2
        instance = new V3D_PlaneDouble(P1N2P1, new V3D_VectorDouble(4d, -2d, -2d),
                new V3D_VectorDouble(4d, 1d, 4d));
        expResult = "9.0(x)+9.0(z)=18.0(y)+54.0";
        result = instance.getEquationString();
        assertTrue(expResult.equalsIgnoreCase(result));
    }

    /**
     * Test of isOnPlane method, of class V3D_PlaneDouble.
     */
    @Test
    public void testIsOnPlane_V3D_LineSegmentDouble() {
        System.out.println("isOnPlane");
        double epsilon = 0.00000001d;
        // Test 1 to 9 lines segments in line with axes
        V3D_LineSegmentDouble l = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
        V3D_PlaneDouble instance = V3D_PlaneDouble.X0;
        assertFalse(instance.isOnPlane(l.l, epsilon));
        // Test 2
        instance = V3D_PlaneDouble.Y0;
        assertTrue(instance.isOnPlane(l.l, epsilon));
        // Test 3
        instance = V3D_PlaneDouble.Z0;
        assertTrue(instance.isOnPlane(l.l, epsilon));
        // Test 4
        l = new V3D_LineSegmentDouble(pP0P0P0, pP0P1P0);
        instance = V3D_PlaneDouble.X0;
        assertTrue(instance.isOnPlane(l.l, epsilon));
        // Test 5
        instance = V3D_PlaneDouble.Y0;
        assertFalse(instance.isOnPlane(l.l, epsilon));
        // Test 6
        instance = V3D_PlaneDouble.Z0;
        assertTrue(instance.isOnPlane(l.l, epsilon));
        // Test 7
        l = new V3D_LineSegmentDouble(pP0P0P0, pP0P0P1);
        instance = V3D_PlaneDouble.X0;
        assertTrue(instance.isOnPlane(l.l, epsilon));
        // Test 8
        instance = V3D_PlaneDouble.Y0;
        assertTrue(instance.isOnPlane(l.l, epsilon));
        // Test 9
        instance = V3D_PlaneDouble.Z0;
        assertFalse(instance.isOnPlane(l.l, epsilon));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_PlaneDouble.
     */
    @Test
    public void testIsIntersectedBy_V3D_PointDouble_int() {
        System.out.println("intersects");
        double epsilon = 0.0000000001d;
        V3D_PlaneDouble instance;
        // x=0
        instance = new V3D_PlaneDouble(P0P0P0, P0P1P0, P0P0P1);
        // P2
        assertFalse(instance.isIntersectedBy(pP2P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2N2, epsilon));
        // P1
        assertFalse(instance.isIntersectedBy(pP1P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2N2, epsilon));
        // P0
        assertTrue(instance.isIntersectedBy(pP0P2P2, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P2P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P2P0, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P2N1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P2N2, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P1P2, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P1P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P1P0, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P1N1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P1N2, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P0P2, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P0P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P0P0, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P0N1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P0N2, epsilon));
        assertTrue(instance.isIntersectedBy(pP0N1P2, epsilon));
        assertTrue(instance.isIntersectedBy(pP0N1P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0N1P0, epsilon));
        assertTrue(instance.isIntersectedBy(pP0N1N1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0N1N2, epsilon));
        assertTrue(instance.isIntersectedBy(pP0N2P2, epsilon));
        assertTrue(instance.isIntersectedBy(pP0N2P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0N2P0, epsilon));
        assertTrue(instance.isIntersectedBy(pP0N2N1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0N2N2, epsilon));
        // N1
        assertFalse(instance.isIntersectedBy(pN1P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2N2, epsilon));
        // N2
        assertFalse(instance.isIntersectedBy(pN2P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2N2, epsilon));

        // y=0
        instance = new V3D_PlaneDouble(P1P0P0, P0P0P0, P0P0P1);
        // P2
        assertFalse(instance.isIntersectedBy(pP2P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1N2, epsilon));
        assertTrue(instance.isIntersectedBy(pP2P0P2, epsilon));
        assertTrue(instance.isIntersectedBy(pP2P0P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP2P0P0, epsilon));
        assertTrue(instance.isIntersectedBy(pP2P0N1, epsilon));
        assertTrue(instance.isIntersectedBy(pP2P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2N2, epsilon));
        // P1
        assertFalse(instance.isIntersectedBy(pP1P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1N2, epsilon));
        assertTrue(instance.isIntersectedBy(pP1P0P2, epsilon));
        assertTrue(instance.isIntersectedBy(pP1P0P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP1P0P0, epsilon));
        assertTrue(instance.isIntersectedBy(pP1P0N1, epsilon));
        assertTrue(instance.isIntersectedBy(pP1P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2N2, epsilon));
        // P0
        assertFalse(instance.isIntersectedBy(pP0P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1N2, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P0P2, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P0P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P0P0, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P0N1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2N2, epsilon));
        // N1
        assertFalse(instance.isIntersectedBy(pN1P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1N2, epsilon));
        assertTrue(instance.isIntersectedBy(pN1P0P2, epsilon));
        assertTrue(instance.isIntersectedBy(pN1P0P1, epsilon));
        assertTrue(instance.isIntersectedBy(pN1P0P0, epsilon));
        assertTrue(instance.isIntersectedBy(pN1P0N1, epsilon));
        assertTrue(instance.isIntersectedBy(pN1P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2N2, epsilon));
        // N2
        assertFalse(instance.isIntersectedBy(pN2P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1N2, epsilon));
        assertTrue(instance.isIntersectedBy(pN2P0P2, epsilon));
        assertTrue(instance.isIntersectedBy(pN2P0P1, epsilon));
        assertTrue(instance.isIntersectedBy(pN2P0P0, epsilon));
        assertTrue(instance.isIntersectedBy(pN2P0N1, epsilon));
        assertTrue(instance.isIntersectedBy(pN2P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2P1, epsilon));

        // z=0
        instance = new V3D_PlaneDouble(P0P0P0, P0P1P0, P1P0P0);
        // P2
        assertFalse(instance.isIntersectedBy(pP2P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP2P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP2P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP2P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP2N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP2N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2N2, epsilon));
        // P1
        assertFalse(instance.isIntersectedBy(pP1P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP1P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP1P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP1P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP1N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP1N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2N2, epsilon));
        // P0
        assertFalse(instance.isIntersectedBy(pP0P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P0P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2N2, epsilon));
        // N1
        assertFalse(instance.isIntersectedBy(pN1P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2P1, epsilon));
        assertTrue(instance.isIntersectedBy(pN1P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1P1, epsilon));
        assertTrue(instance.isIntersectedBy(pN1P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0P1, epsilon));
        assertTrue(instance.isIntersectedBy(pN1P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1P1, epsilon));
        assertTrue(instance.isIntersectedBy(pN1N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2P1, epsilon));
        assertTrue(instance.isIntersectedBy(pN1N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2N2, epsilon));
        // N2
        assertFalse(instance.isIntersectedBy(pN2P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2P1, epsilon));
        assertTrue(instance.isIntersectedBy(pN2P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1P1, epsilon));
        assertTrue(instance.isIntersectedBy(pN2P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0P1, epsilon));
        assertTrue(instance.isIntersectedBy(pN2P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1P1, epsilon));
        assertTrue(instance.isIntersectedBy(pN2N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2P1, epsilon));
        assertTrue(instance.isIntersectedBy(pN2N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2N2, epsilon));

        // x=y
        instance = new V3D_PlaneDouble(P0P0P0, P1P1P0, P0P0P1);
        // P2
        assertTrue(instance.isIntersectedBy(pP2P2P2, epsilon));
        assertTrue(instance.isIntersectedBy(pP2P2P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP2P2P0, epsilon));
        assertTrue(instance.isIntersectedBy(pP2P2N1, epsilon));
        assertTrue(instance.isIntersectedBy(pP2P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2N2, epsilon));
        // P1
        assertFalse(instance.isIntersectedBy(pP1P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2N2, epsilon));
        assertTrue(instance.isIntersectedBy(pP1P1P2, epsilon));
        assertTrue(instance.isIntersectedBy(pP1P1P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP1P1P0, epsilon));
        assertTrue(instance.isIntersectedBy(pP1P1N1, epsilon));
        assertTrue(instance.isIntersectedBy(pP1P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2N2, epsilon));
        // P0
        assertFalse(instance.isIntersectedBy(pP0P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1N2, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P0P2, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P0P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P0P0, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P0N1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2N2, epsilon));
        // N1
        assertFalse(instance.isIntersectedBy(pN1P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0N2, epsilon));
        assertTrue(instance.isIntersectedBy(pN1N1P2, epsilon));
        assertTrue(instance.isIntersectedBy(pN1N1P1, epsilon));
        assertTrue(instance.isIntersectedBy(pN1N1P0, epsilon));
        assertTrue(instance.isIntersectedBy(pN1N1N1, epsilon));
        assertTrue(instance.isIntersectedBy(pN1N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2N2, epsilon));
        // N2
        assertFalse(instance.isIntersectedBy(pN2P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1N2, epsilon));
        assertTrue(instance.isIntersectedBy(pN2N2P2, epsilon));
        assertTrue(instance.isIntersectedBy(pN2N2P1, epsilon));
        assertTrue(instance.isIntersectedBy(pN2N2P0, epsilon));
        assertTrue(instance.isIntersectedBy(pN2N2N1, epsilon));
        assertTrue(instance.isIntersectedBy(pN2N2N2, epsilon));

        // x=-y
        instance = new V3D_PlaneDouble(P0P0P0, N1P1P0, P0P0P1);
        // P2
        assertFalse(instance.isIntersectedBy(pP2P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1N2, epsilon));
        assertTrue(instance.isIntersectedBy(pP2N2P2, epsilon));
        assertTrue(instance.isIntersectedBy(pP2N2P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP2N2P0, epsilon));
        assertTrue(instance.isIntersectedBy(pP2N2N1, epsilon));
        assertTrue(instance.isIntersectedBy(pP2N2N2, epsilon));
        // P1
        assertFalse(instance.isIntersectedBy(pP1P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0N2, epsilon));
        assertTrue(instance.isIntersectedBy(pP1N1P2, epsilon));
        assertTrue(instance.isIntersectedBy(pP1N1P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP1N1P0, epsilon));
        assertTrue(instance.isIntersectedBy(pP1N1N1, epsilon));
        assertTrue(instance.isIntersectedBy(pP1N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2N2, epsilon));
        // P0
        assertFalse(instance.isIntersectedBy(pP0P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1N2, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P0P2, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P0P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P0P0, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P0N1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2N2, epsilon));
        // N1
        assertFalse(instance.isIntersectedBy(pN1P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2N2, epsilon));
        assertTrue(instance.isIntersectedBy(pN1P1P2, epsilon));
        assertTrue(instance.isIntersectedBy(pN1P1P1, epsilon));
        assertTrue(instance.isIntersectedBy(pN1P1P0, epsilon));
        assertTrue(instance.isIntersectedBy(pN1P1N1, epsilon));
        assertTrue(instance.isIntersectedBy(pN1P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2N2, epsilon));
        // N2
        assertTrue(instance.isIntersectedBy(pN2P2P2, epsilon));
        assertTrue(instance.isIntersectedBy(pN2P2P1, epsilon));
        assertTrue(instance.isIntersectedBy(pN2P2P0, epsilon));
        assertTrue(instance.isIntersectedBy(pN2P2N1, epsilon));
        assertTrue(instance.isIntersectedBy(pN2P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2N2, epsilon));

        // x=z
        instance = new V3D_PlaneDouble(P0P0P0, P0P1P0, P1P0P1);
        // P2
        assertTrue(instance.isIntersectedBy(pP2P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2N2, epsilon));
        assertTrue(instance.isIntersectedBy(pP2P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1N2, epsilon));
        assertTrue(instance.isIntersectedBy(pP2P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0N2, epsilon));
        assertTrue(instance.isIntersectedBy(pP2N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1N2, epsilon));
        assertTrue(instance.isIntersectedBy(pP2N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2N2, epsilon));
        // P1
        assertFalse(instance.isIntersectedBy(pP1P2P2, epsilon));
        assertTrue(instance.isIntersectedBy(pP1P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1P2, epsilon));
        assertTrue(instance.isIntersectedBy(pP1P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0P2, epsilon));
        assertTrue(instance.isIntersectedBy(pP1P0P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1P2, epsilon));
        assertTrue(instance.isIntersectedBy(pP1N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2P2, epsilon));
        assertTrue(instance.isIntersectedBy(pP1N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2N2, epsilon));
        // P0
        assertFalse(instance.isIntersectedBy(pP0P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P0P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2N2, epsilon));
        // N1
        assertFalse(instance.isIntersectedBy(pN1P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2P0, epsilon));
        assertTrue(instance.isIntersectedBy(pN1P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1P0, epsilon));
        assertTrue(instance.isIntersectedBy(pN1P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0P0, epsilon));
        assertTrue(instance.isIntersectedBy(pN1P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1P0, epsilon));
        assertTrue(instance.isIntersectedBy(pN1N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2P0, epsilon));
        assertTrue(instance.isIntersectedBy(pN1N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2N2, epsilon));
        // N2
        assertFalse(instance.isIntersectedBy(pN2P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2N1, epsilon));
        assertTrue(instance.isIntersectedBy(pN2P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1N1, epsilon));
        assertTrue(instance.isIntersectedBy(pN2P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0N1, epsilon));
        assertTrue(instance.isIntersectedBy(pN2P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1N1, epsilon));
        assertTrue(instance.isIntersectedBy(pN2N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2N1, epsilon));
        assertTrue(instance.isIntersectedBy(pN2N2N2, epsilon));

        // x=-z
        instance = new V3D_PlaneDouble(P0P0P0, P0P1P0, N1P0P1);
        // P2
        assertFalse(instance.isIntersectedBy(pP2P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2N1, epsilon));
        assertTrue(instance.isIntersectedBy(pP2P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1N1, epsilon));
        assertTrue(instance.isIntersectedBy(pP2P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0N1, epsilon));
        assertTrue(instance.isIntersectedBy(pP2P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1N1, epsilon));
        assertTrue(instance.isIntersectedBy(pP2N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2N1, epsilon));
        assertTrue(instance.isIntersectedBy(pP2N2N2, epsilon));
        // P1
        assertFalse(instance.isIntersectedBy(pP1P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2P0, epsilon));
        assertTrue(instance.isIntersectedBy(pP1P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1P0, epsilon));
        assertTrue(instance.isIntersectedBy(pP1P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0P0, epsilon));
        assertTrue(instance.isIntersectedBy(pP1P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1P0, epsilon));
        assertTrue(instance.isIntersectedBy(pP1N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2P0, epsilon));
        assertTrue(instance.isIntersectedBy(pP1N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2N2, epsilon));
        // P0
        assertFalse(instance.isIntersectedBy(pP0P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P0P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2N2, epsilon));
        // N1
        assertFalse(instance.isIntersectedBy(pN1P2P2, epsilon));
        assertTrue(instance.isIntersectedBy(pN1P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1P2, epsilon));
        assertTrue(instance.isIntersectedBy(pN1P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0P2, epsilon));
        assertTrue(instance.isIntersectedBy(pN1P0P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1P2, epsilon));
        assertTrue(instance.isIntersectedBy(pN1N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2P2, epsilon));
        assertTrue(instance.isIntersectedBy(pN1N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2N2, epsilon));
        // N2
        assertTrue(instance.isIntersectedBy(pN2P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2N2, epsilon));
        assertTrue(instance.isIntersectedBy(pN2P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1N2, epsilon));
        assertTrue(instance.isIntersectedBy(pN2P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0N2, epsilon));
        assertTrue(instance.isIntersectedBy(pN2N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1N2, epsilon));
        assertTrue(instance.isIntersectedBy(pN2N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2N2, epsilon));

        // y=z
        instance = new V3D_PlaneDouble(P1P0P0, P0P0P0, P0P1P1);
        // P2
        assertTrue(instance.isIntersectedBy(pP2P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1P2, epsilon));
        assertTrue(instance.isIntersectedBy(pP2P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP2P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1P0, epsilon));
        assertTrue(instance.isIntersectedBy(pP2N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2N1, epsilon));
        assertTrue(instance.isIntersectedBy(pP2N2N2, epsilon));
        // P1
        assertTrue(instance.isIntersectedBy(pP1P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1P2, epsilon));
        assertTrue(instance.isIntersectedBy(pP1P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP1P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1P0, epsilon));
        assertTrue(instance.isIntersectedBy(pP1N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2N1, epsilon));
        assertTrue(instance.isIntersectedBy(pP1N2N2, epsilon));
        // P0
        assertTrue(instance.isIntersectedBy(pP0P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1P2, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P0P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1P0, epsilon));
        assertTrue(instance.isIntersectedBy(pP0N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2N1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0N2N2, epsilon));
        // N1
        assertTrue(instance.isIntersectedBy(pN1P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1P2, epsilon));
        assertTrue(instance.isIntersectedBy(pN1P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0P1, epsilon));
        assertTrue(instance.isIntersectedBy(pN1P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1P0, epsilon));
        assertTrue(instance.isIntersectedBy(pN1N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2N1, epsilon));
        assertTrue(instance.isIntersectedBy(pN1N2N2, epsilon));
        // N2
        assertTrue(instance.isIntersectedBy(pN2P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1P2, epsilon));
        assertTrue(instance.isIntersectedBy(pN2P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0P1, epsilon));
        assertTrue(instance.isIntersectedBy(pN2P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1P0, epsilon));
        assertTrue(instance.isIntersectedBy(pN2N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2N1, epsilon));
        assertTrue(instance.isIntersectedBy(pN2N2N2, epsilon));

        // x=-z
        instance = new V3D_PlaneDouble(P1P0P0, P0P0P0, P0N1P1);
        // P2
        assertFalse(instance.isIntersectedBy(pP2P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2N1, epsilon));
        assertTrue(instance.isIntersectedBy(pP2P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1P0, epsilon));
        assertTrue(instance.isIntersectedBy(pP2P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP2P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1P2, epsilon));
        assertTrue(instance.isIntersectedBy(pP2N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1N2, epsilon));
        assertTrue(instance.isIntersectedBy(pP2N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2N2, epsilon));
        // P1
        assertFalse(instance.isIntersectedBy(pP1P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2N1, epsilon));
        assertTrue(instance.isIntersectedBy(pP1P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1P0, epsilon));
        assertTrue(instance.isIntersectedBy(pP1P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP1P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1P2, epsilon));
        assertTrue(instance.isIntersectedBy(pP1N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1N2, epsilon));
        assertTrue(instance.isIntersectedBy(pP1N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2N2, epsilon));
        // P0
        assertFalse(instance.isIntersectedBy(pP0P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2N1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1P0, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P0P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1P2, epsilon));
        assertTrue(instance.isIntersectedBy(pP0N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1N2, epsilon));
        assertTrue(instance.isIntersectedBy(pP0N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2N2, epsilon));
        // N1
        assertFalse(instance.isIntersectedBy(pN1P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2N1, epsilon));
        assertTrue(instance.isIntersectedBy(pN1P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1P0, epsilon));
        assertTrue(instance.isIntersectedBy(pN1P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0P1, epsilon));
        assertTrue(instance.isIntersectedBy(pN1P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1P2, epsilon));
        assertTrue(instance.isIntersectedBy(pN1N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1N2, epsilon));
        assertTrue(instance.isIntersectedBy(pN1N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2N2, epsilon));
        // N2
        assertFalse(instance.isIntersectedBy(pN2P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2N1, epsilon));
        assertTrue(instance.isIntersectedBy(pN2P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1P0, epsilon));
        assertTrue(instance.isIntersectedBy(pN2P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0P1, epsilon));
        assertTrue(instance.isIntersectedBy(pN2P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1P2, epsilon));
        assertTrue(instance.isIntersectedBy(pN2N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1N2, epsilon));
        assertTrue(instance.isIntersectedBy(pN2N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2N2, epsilon));
        // x=y-z
        instance = new V3D_PlaneDouble(P0P0P0, P1P1P0, N1P0P1);
        // P2
        assertFalse(instance.isIntersectedBy(pP2P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP2P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1P0, epsilon));
        assertTrue(instance.isIntersectedBy(pP2P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0N1, epsilon));
        assertTrue(instance.isIntersectedBy(pP2P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2N2, epsilon));
        // P1
        assertFalse(instance.isIntersectedBy(pP1P2P2, epsilon));
        assertTrue(instance.isIntersectedBy(pP1P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP1P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0P0, epsilon));
        assertTrue(instance.isIntersectedBy(pP1P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1N1, epsilon));
        assertTrue(instance.isIntersectedBy(pP1N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2N2, epsilon));
        // P0
        assertTrue(instance.isIntersectedBy(pP0P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1P2, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P0P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1P0, epsilon));
        assertTrue(instance.isIntersectedBy(pP0N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2N1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0N2N2, epsilon));
        // N1
        assertFalse(instance.isIntersectedBy(pN1P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2N2, epsilon));
        assertTrue(instance.isIntersectedBy(pN1P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0P2, epsilon));
        assertTrue(instance.isIntersectedBy(pN1P0P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1P1, epsilon));
        assertTrue(instance.isIntersectedBy(pN1N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2P0, epsilon));
        assertTrue(instance.isIntersectedBy(pN1N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2N2, epsilon));
        // N2
        assertFalse(instance.isIntersectedBy(pN2P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1N2, epsilon));
        assertTrue(instance.isIntersectedBy(pN2P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1P2, epsilon));
        assertTrue(instance.isIntersectedBy(pN2N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2P1, epsilon));
        assertTrue(instance.isIntersectedBy(pN2N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2N2, epsilon));
        // x=z-y
        instance = new V3D_PlaneDouble(P1P0P1, P0P1P1, P0P0P0);
        // P2
        assertFalse(instance.isIntersectedBy(pP2P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1N2, epsilon));
        assertTrue(instance.isIntersectedBy(pP2P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1P2, epsilon));
        assertTrue(instance.isIntersectedBy(pP2N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP2N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2N2, epsilon));
        // P1
        assertFalse(instance.isIntersectedBy(pP1P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2N2, epsilon));
        assertTrue(instance.isIntersectedBy(pP1P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0P2, epsilon));
        assertTrue(instance.isIntersectedBy(pP1P0P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP1N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2P0, epsilon));
        assertTrue(instance.isIntersectedBy(pP1N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2N2, epsilon));
        // P0
        assertTrue(instance.isIntersectedBy(pP0P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1P2, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P0P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1P0, epsilon));
        assertTrue(instance.isIntersectedBy(pP0N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2N1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0N2N2, epsilon));
        // N1
        assertFalse(instance.isIntersectedBy(pN1P2P2, epsilon));
        assertTrue(instance.isIntersectedBy(pN1P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1P1, epsilon));
        assertTrue(instance.isIntersectedBy(pN1P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0P0, epsilon));
        assertTrue(instance.isIntersectedBy(pN1P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1N1, epsilon));
        assertTrue(instance.isIntersectedBy(pN1N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2N2, epsilon));
        // N2
        assertFalse(instance.isIntersectedBy(pN2P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2P1, epsilon));
        assertTrue(instance.isIntersectedBy(pN2P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1P0, epsilon));
        assertTrue(instance.isIntersectedBy(pN2P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0N1, epsilon));
        assertTrue(instance.isIntersectedBy(pN2P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2N2, epsilon));
        // y=x-z
        instance = new V3D_PlaneDouble(P1P1P0, P0P0P0, P0N1P1);
        // P2
        assertFalse(instance.isIntersectedBy(pP2P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP2P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1P2, epsilon));
        assertTrue(instance.isIntersectedBy(pP2P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P1N2, epsilon));
        assertTrue(instance.isIntersectedBy(pP2P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP2N2N2, epsilon));
        // P1
        assertFalse(instance.isIntersectedBy(pP1P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2P0, epsilon));
        assertTrue(instance.isIntersectedBy(pP1P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP1P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0P2, epsilon));
        assertTrue(instance.isIntersectedBy(pP1P0P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1P0N2, epsilon));
        assertTrue(instance.isIntersectedBy(pP1N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP1N2N2, epsilon));
        // P0
        assertFalse(instance.isIntersectedBy(pP0P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P2N1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1P0, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P0P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1P2, epsilon));
        assertTrue(instance.isIntersectedBy(pP0N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N1N2, epsilon));
        assertTrue(instance.isIntersectedBy(pP0N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pP0N2N2, epsilon));
        // N1
        assertFalse(instance.isIntersectedBy(pN1P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P1N1, epsilon));
        assertTrue(instance.isIntersectedBy(pN1P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0P0, epsilon));
        assertTrue(instance.isIntersectedBy(pN1P0N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1P1, epsilon));
        assertTrue(instance.isIntersectedBy(pN1N1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2P2, epsilon));
        assertTrue(instance.isIntersectedBy(pN1N2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1N2N2, epsilon));
        // N2
        assertFalse(instance.isIntersectedBy(pN2P2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P2N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2P0N1, epsilon));
        assertTrue(instance.isIntersectedBy(pN2P0N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1P0, epsilon));
        assertTrue(instance.isIntersectedBy(pN2N1N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N1N2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2P2, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2P1, epsilon));
        assertTrue(instance.isIntersectedBy(pN2N2P0, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2N1, epsilon));
        assertFalse(instance.isIntersectedBy(pN2N2N2, epsilon));
        // Test 2 from https://math.stackexchange.com/questions/2686606/equation-of-a-plane-passing-through-3-points        
        V3D_VectorDouble n = new V3D_VectorDouble(1, -2, 1);
        V3D_PointDouble p = pP1N2P1;
        instance = new V3D_PlaneDouble(p, n);
        assertTrue(instance.isIntersectedBy(new V3D_PointDouble(4, -2, -2), epsilon));
        assertTrue(instance.isIntersectedBy(new V3D_PointDouble(4, 1, 4), epsilon));
        n = new V3D_VectorDouble(1, -2, 1);
        p = new V3D_PointDouble(4, -2, -2);
        instance = new V3D_PlaneDouble(p, n);
        assertTrue(instance.isIntersectedBy(new V3D_PointDouble(1, -2, 1), epsilon));
        assertTrue(instance.isIntersectedBy(new V3D_PointDouble(4, 1, 4), epsilon));
        // Test 3
        n = new V3D_VectorDouble(1, -2, 1);
        p = pP0P0P0;
        instance = new V3D_PlaneDouble(p, n);
        assertTrue(instance.isIntersectedBy(new V3D_PointDouble(3, 0, -3), epsilon));
        assertTrue(instance.isIntersectedBy(new V3D_PointDouble(3, 3, 3), epsilon));
        n = new V3D_VectorDouble(1, -2, 1);
        p = new V3D_PointDouble(3, 0, -3);
        instance = new V3D_PlaneDouble(p, n);
        assertTrue(instance.isIntersectedBy(new V3D_PointDouble(0, 0, 0), epsilon));
        assertTrue(instance.isIntersectedBy(new V3D_PointDouble(3, 3, 3), epsilon));
    }

    /**
     * Test of getNormalVector method, of class V3D_PlaneDouble.
     */
    @Test
    public void testGetNormalVector() {
        System.out.println("getNormalVector");
        double epsilon = 0.00000001d;
        // Z = 0
        V3D_PlaneDouble instance = new V3D_PlaneDouble(P0P0P0, P1P0P0, P0P1P0);
        //V3D_PlaneDouble instance = new V3D_PlaneDouble(P0P0P0, P0P1P0, P1P0P0);
        //V3D_VectorDouble expResult = P0P0N1;
        V3D_VectorDouble expResult = P0P0P1;
        V3D_VectorDouble result = instance.getN();
        assertTrue(expResult.equals(result));
        // Z = -1
        instance = new V3D_PlaneDouble(P0P0N1, P1P0N1, P0P1N1);
        //expResult = P0P0N1;
        expResult = P0P0P1;
        result = instance.getN();
        assertTrue(expResult.equals(result));
        // Z = 1
        instance = new V3D_PlaneDouble(P0P0P1, P1P0P1, P0P1P1);
        expResult = P0P0P1;
        result = instance.getN();
        assertTrue(expResult.equals(result));
        // Z = 1
        instance = new V3D_PlaneDouble(P1P0P1, P0P1P1, P0P0P1);
        expResult = P0P0P1;
        result = instance.getN();
        assertTrue(expResult.equals(result));
        // Z = 1
        instance = new V3D_PlaneDouble(P0P1P1, P0P0P1, P1P0P1);
        //expResult = new V3D_VectorDouble(P0P0N1);
        expResult = P0P0P1;
        result = instance.getN();
        assertTrue(expResult.equals(result));
        // Y = 0
        instance = new V3D_PlaneDouble(P0P0P0, P0P1P0, P0P0N1);
        //expResult = new V3D_VectorDouble(P1P0P0);
        expResult = N1P0P0;
        result = instance.getN();
        assertTrue(expResult.equals(result));
        // X = 0
        instance = new V3D_PlaneDouble(P0P0P0, P1P0P0, P0P0N1);
        //expResult = new V3D_VectorDouble(P0N1P0);
        expResult = P0P1P0;
        result = instance.getN();
        assertTrue(expResult.equals(result));
        // Y = 0
        instance = new V3D_PlaneDouble(P0P0P0, P1P0P0, N1P0P1);
        expResult = P0N1P0;
        result = instance.getN();
        assertTrue(expResult.equals(result));
        // 
        instance = new V3D_PlaneDouble(P0P1P0, P1P1P1, P1P0P0);
        //expResult = new V3D_VectorDouble(N1N1P1);
        expResult = P1P1N1;
        result = instance.getN();
        assertTrue(expResult.equals(result));
        // X = 0
        instance = new V3D_PlaneDouble(P0P0P0, P0P1P1, P0N1P0);
        //expResult = new V3D_VectorDouble(N1P0P0);
        expResult = P1P0P0;
        result = instance.getN();
        assertTrue(expResult.equals(result));
        // 
        instance = new V3D_PlaneDouble(P0P0P0, P1P1P1, P0N1N1);
        //expResult = new V3D_VectorDouble(P0N1P1);
        expResult = P0P1N1;
        result = instance.getN();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of isParallel method, of class V3D_PlaneDouble.
     */
    @Test
    public void testIsParallel_V3D_PlaneDouble_int() {
        System.out.println("isParallel");
        double epsilon = 0.00000001d;
        V3D_PlaneDouble p = new V3D_PlaneDouble(P1P1P0, P1N1P0, N1P1P0);
        V3D_PlaneDouble instance = new V3D_PlaneDouble(P1P1P1, P1N1P1, N1P1P1);
        assertTrue(instance.isParallel(p, epsilon));
        // Test 2
        instance = new V3D_PlaneDouble(P1P1N1, P1N1N1, N1P1N1);
        assertTrue(instance.isParallel(p, epsilon));
        // Test 3
        instance = new V3D_PlaneDouble(P1P1N1, P1N1N1, N1P1N1);
        assertTrue(instance.isParallel(p, epsilon));
        // Test 4
        p = V3D_PlaneDouble.X0;
        instance = V3D_PlaneDouble.Y0;
        assertFalse(instance.isParallel(p, epsilon));
        // Test 5
        p = V3D_PlaneDouble.X0;
        instance = V3D_PlaneDouble.Z0;
        assertFalse(instance.isParallel(p, epsilon));
        // Test 6
        p = V3D_PlaneDouble.Y0;
        instance = V3D_PlaneDouble.Z0;
        assertFalse(instance.isParallel(p, epsilon));
        // Test 7
        p = new V3D_PlaneDouble(P0P0P0, P0P1P0, P1P1P1);
        instance = new V3D_PlaneDouble(P1P0P0, P1P1P0, P2P1P1);
        assertTrue(instance.isParallel(p, epsilon));
        // Test 8
        instance = new V3D_PlaneDouble(P1N1P0, P1P0P0, P2P0P1);
        assertTrue(instance.isParallel(p, epsilon));
        // Test 9
        instance = new V3D_PlaneDouble(P1P0P0, P1P1P0, P1P1P1);
        assertFalse(instance.isParallel(p, epsilon));
    }

    /**
     * Test of isParallel method, of class V3D_PlaneDouble.
     */
    @Test
    public void testIsParallel_V3D_LineDouble_int() {
        System.out.println("isParallel");
        double epsilon = 1d / 10000000d;
        V3D_LineDouble l = V3D_LineDouble.X_AXIS;
        V3D_PlaneDouble instance = V3D_PlaneDouble.Y0;
        assertTrue(instance.isParallel(l, epsilon));
        // Test 2
        instance = V3D_PlaneDouble.Z0;
        assertTrue(instance.isParallel(l, epsilon));
        // Test 3
        instance = V3D_PlaneDouble.X0;
        assertFalse(instance.isParallel(l, epsilon));
        // Test 4
        l = V3D_LineDouble.Y_AXIS;
        instance = V3D_PlaneDouble.X0;
        assertTrue(instance.isParallel(l, epsilon));
        // Test 5
        instance = V3D_PlaneDouble.Y0;
        assertFalse(instance.isParallel(l, epsilon));
        // Test 6
        instance = V3D_PlaneDouble.Z0;
        assertTrue(instance.isParallel(l, epsilon));
        // Test 7
        l = V3D_LineDouble.Z_AXIS;
        instance = V3D_PlaneDouble.X0;
        assertTrue(instance.isParallel(l, epsilon));
        // Test 8
        instance = V3D_PlaneDouble.Y0;
        assertTrue(instance.isParallel(l, epsilon));
        // Test 9
        instance = V3D_PlaneDouble.Z0;
        assertFalse(instance.isParallel(l, epsilon));
    }

    /**
     * Test of getIntersection method, of class V3D_PlaneDouble. The following
     * can be used for creating test cases:
     * http://www.ambrsoft.com/TrigoCalc/Plan3D/Plane3D_.htm
     */
    @Test
    public void testGetIntersection_V3D_PlaneDouble() {
        System.out.println("getIntersection");
        double epsilon = 1d / 10000000d;
        V3D_PlaneDouble pl;
        V3D_PlaneDouble instance;
        V3D_GeometryDouble expResult;
        V3D_GeometryDouble result;
        /**
         * The following is from:
         * https://www.microsoft.com/en-us/research/wp-content/uploads/2016/02/note.doc
         * Two Planar patches.
         */
        pl = new V3D_PlaneDouble(
                new V3D_PointDouble(8d / 3d, -2d / 3d, 0d),
                new V3D_VectorDouble(2d, 8d, 0d));
        //new V3D_VectorDouble(2, 8, 0)N5);
        instance = new V3D_PlaneDouble(
                new V3D_PointDouble(8d / 3d, 0d, -2d / 3d),
                new V3D_VectorDouble(2, 0, 8));
        //new V3D_VectorDouble(2, 0, 8)N5);
        expResult = new V3D_LineDouble(
                new V3D_VectorDouble(68d / 27d, -17d / 27d, -17d / 27d),
                new V3D_VectorDouble(1d / 4d, -1d / 16d, -1d / 16d));
        result = instance.getIntersection(pl, epsilon);
        //assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result));
//        /**
//         * The following is from:
//         * https://www.microsoft.com/en-us/research/wp-content/uploads/2016/02/note.doc
//         * Simple. Something is not right with this test which had worked at
//         * some stage... :(
//         */
//        oom = -10;
//        pl = new V3D_PlaneDouble(new V3D_PointDouble(7, 11, 0), new V3D_VectorDouble(0, 0, 3));
//        instance = new V3D_PlaneDouble(new V3D_PointDouble(1, 0, 0), new V3D_VectorDouble(5, 5, 0));
//        Math_BigRational half = P1.divide(2);
//        V3D_PointDouble p2 = new V3D_PointDouble(half, half, Math_BigRational.ZERO);
//        //V3D_PointDouble p2 = new V3D_PointDouble(0.5d, 0.5d, 0);
//        assertTrue(V3D_PlaneDouble.isCoplanar(pl, p2));
//        V3D_VectorDouble v2 = new V3D_VectorDouble(-15, 15, 0);
//        //assertTrue(V3D_Geometrics.isCoplanar(pl, p2.translate(v2)));
//        assertTrue(V3D_PlaneDouble.isCoplanar(pl, new V3D_PointDouble(p2.offset, p2.rel.add(v2))));
//        //assertTrue(V3D_Geometrics.isCoplanar(pl, new V3D_PointDouble(p2.offset.add(v2), p2.rel)));
//
//        //expResult = new V3D_LineDouble(p2, v2);
//        expResult = new V3D_LineDouble(p2.getVector(), v2);
//        //expResult = new V3D_LineDouble(p2.offset, p2.rel, v2);
//
//        result = instance.getIntersection(pl, epsilon);
//        //System.out.println(result);
//        //System.out.println(expResult);
//        result = instance.getIntersection(pl, epsilon);
//        //assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result));
//        //assertTrue(expResult.equals(result));
//        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result));
        // Test V3D_PlaneDouble.X0
        pl = V3D_PlaneDouble.X0;
        // Test 1
        instance = V3D_PlaneDouble.X0;
        expResult = V3D_PlaneDouble.X0;
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_PlaneDouble) expResult).equalsIgnoreOrientation((V3D_PlaneDouble) result, epsilon));
        // Test 2
        instance = V3D_PlaneDouble.Y0;
        expResult = V3D_LineDouble.Z_AXIS;
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));
        // Test 3
        instance = V3D_PlaneDouble.Z0;
        expResult = V3D_LineDouble.Y_AXIS;
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));

        pl = new V3D_PlaneDouble(V3D_PlaneDouble.X0);
        pl.translate(P1P0P0); // X = 1 Plane
        // Test 1
        instance = V3D_PlaneDouble.X0;
        assertNull(instance.getIntersection(pl, epsilon));
        // Test 2
        instance = V3D_PlaneDouble.Y0;
        expResult = new V3D_LineDouble(V3D_LineDouble.Z_AXIS);
        expResult.translate(P1P0P0);
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));
        // Test 3
        instance = V3D_PlaneDouble.Z0;
        expResult = new V3D_LineDouble(V3D_LineDouble.Y_AXIS);
        expResult.translate(P1P0P0);
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));

        // Test V3D_PlaneDouble.Y0
        pl = V3D_PlaneDouble.Y0;
        // Test 1
        instance = V3D_PlaneDouble.X0;
        expResult = V3D_LineDouble.Z_AXIS;
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));
        // Test 2
        instance = V3D_PlaneDouble.Y0;
        expResult = V3D_PlaneDouble.Y0;
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_PlaneDouble) expResult).equals((V3D_PlaneDouble) result, epsilon));
        // Test 3
        instance = V3D_PlaneDouble.Z0;
        expResult = V3D_LineDouble.X_AXIS;
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));

        // Test V3D_PlaneDouble.Z0
        pl = V3D_PlaneDouble.Z0;
        // Test 1
        instance = V3D_PlaneDouble.X0;
        expResult = V3D_LineDouble.Y_AXIS;
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));
        // Test 2
        instance = V3D_PlaneDouble.Y0;
        expResult = V3D_LineDouble.X_AXIS;
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));
        // Test 3
        instance = V3D_PlaneDouble.Z0;
        expResult = V3D_PlaneDouble.Z0;
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_PlaneDouble) expResult).equals((V3D_PlaneDouble) result, epsilon));
        pl = V3D_PlaneDouble.X0;
        // Test 4
        instance = new V3D_PlaneDouble(pN1P1N1, pP0P1P0, pP1P1N1); // y=1
        expResult = new V3D_LineDouble(pP0P1P0, pP0P1P1);    // x=0, y=1
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));
        // Test 5
        instance = new V3D_PlaneDouble(pP1P0P1, pP0N1P1, pP0P0P1); // z=1
        expResult = new V3D_LineDouble(pP0P1P1, pP0P0P1);    // x=0, z=1
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));
        // Test 6 to 9
        pl = V3D_PlaneDouble.Y0;
        // Test 6
        instance = V3D_PlaneDouble.X0;
        expResult = new V3D_LineDouble(pP0P0N1, pP0P0P1);          // x=0, y=0
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));
        // Test 7
        instance = V3D_PlaneDouble.Z0;
        expResult = new V3D_LineDouble(pP0P0P0, pP1P0P0);          // y=0, z=0
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));
        // Test 8
        instance = new V3D_PlaneDouble(pP1P0P0, pP1P1P0, pP1P0P1);       // x=1
        expResult = new V3D_LineDouble(pP1P0N1, pP1P0P1);          // x=1, y=0
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));
        // Test 9
        instance = new V3D_PlaneDouble(pP0P1P1, pP1P1P1, pP0P0P1);       // z=1
        expResult = new V3D_LineDouble(pP0P0P1, pP1P0P1);          // y=0, z=1
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));
        // Test 10 to 13
        pl = V3D_PlaneDouble.Z0;
        // Test 10
        instance = new V3D_PlaneDouble(P0P0P0, V3D_VectorDouble.J, V3D_VectorDouble.K); // x=0
        expResult = new V3D_LineDouble(pP0N1P0, pP0P1P0);          // x=0, z=0
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));
        // Test 11
        instance = new V3D_PlaneDouble(V3D_VectorDouble.I, P0P0P0, V3D_VectorDouble.K); // y=0
        expResult = new V3D_LineDouble(pN1P0P0, pP1P0P0);          // y=0, z=0
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));
        // Test 12
        instance = new V3D_PlaneDouble(pP1P0P0, pP1P1P1, pP1P0P1); // x=1
        expResult = new V3D_LineDouble(pP1N1P0, pP1P1P0);    // x=1, z=0
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));
        // Test 13
        instance = new V3D_PlaneDouble(pP1P1P1, pP0P1P0, pP1P1P0); // y=1
        expResult = new V3D_LineDouble(pN1P1P0, pP1P1P0);    // y=1, z=0
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));
        // Test 14 to 15
        pl = new V3D_PlaneDouble(pP1P0P0, pP1P1P1, pP1P0P1); // x=1
        // Test 14
        instance = new V3D_PlaneDouble(pN1P1N1, pP0P1P0, pP1P1N1); // y=1
        expResult = new V3D_LineDouble(pP1P1P0, pP1P1P1);    // x=1, y=1
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));
        // Test 15
        instance = new V3D_PlaneDouble(pP1P0P1, pP0N1P1, pP0P0P1); // z=1
        expResult = new V3D_LineDouble(pP1P1P1, pP1P0P1);    // x=1, z=1
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));
        // Test 16 to 17
        pl = new V3D_PlaneDouble(pN1P1N1, pP0P1P0, pP1P1N1); // y=1
        // Test 16
        instance = new V3D_PlaneDouble(pP1P0P0, pP1P1P1, pP1P0P1); // x=1
        expResult = new V3D_LineDouble(pP1P1P0, pP1P1P1);    // x=1, y=1
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));
        // Test 17
        instance = new V3D_PlaneDouble(pP1P0P1, pP0N1P1, pP0P0P1); // z=1
        expResult = new V3D_LineDouble(pP1P1P1, pP0P1P1);    // y=1, z=1
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));
        // Test 18 to 19
        pl = new V3D_PlaneDouble(pP1P0P1, pP0N1P1, pP0P0P1); // z=1
        // Test 18
        instance = new V3D_PlaneDouble(pP1P0P0, pP1P1P1, pP1P0P1); // x=1
        expResult = new V3D_LineDouble(pP1P0P1, pP1P1P1);    // x=1, z=1
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));
        // Test 19
        instance = new V3D_PlaneDouble(pN1P1N1, pP0P1P0, pP1P1N1); // y=1
        expResult = new V3D_LineDouble(pP1P1P1, pP0P1P1);    // y=1, z=1
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));
        // Test 20 to 21
        pl = new V3D_PlaneDouble(pN1P0P0, pN1P1P1, pN1P0P1); // x=-1
        // Test 20
        instance = new V3D_PlaneDouble(pN1P1N1, pP0P1P0, pP1P1N1); // y=1
        expResult = new V3D_LineDouble(pN1P1P0, pN1P1P1);    // x=-1, y=1
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));
        // Test 21
        instance = new V3D_PlaneDouble(pP1P0P1, pP0N1P1, pP0P0P1); // z=1
        expResult = new V3D_LineDouble(pN1P1P1, pN1P0P1);    // x=-1, z=1
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));
        // Test 22 to 23
        pl = new V3D_PlaneDouble(pN1N1N1, pP0N1P0, pP1N1N1); // y=-1
        // Test 22
        instance = new V3D_PlaneDouble(pP1P0P0, pP1P1P1, pP1P0P1); // x=1
        expResult = new V3D_LineDouble(pP1N1P0, pP1N1P1);    // x=1, y=-1
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));
        // Test 23
        instance = new V3D_PlaneDouble(pP1P0P1, pP0N1P1, pP0P0P1); // z=1
        expResult = new V3D_LineDouble(pP1N1P1, pP0N1P1);    // y=-1, z=1
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));
        // Test 24 to 25
        pl = new V3D_PlaneDouble(pP1P0N1, pP0N1N1, pP0P0N1); // z=-1
        // Test 24
        instance = new V3D_PlaneDouble(pP1P0P0, pP1P1P1, pP1P0P1); // x=1
        expResult = new V3D_LineDouble(pP1P0N1, pP1P1N1);    // x=1, z=-1
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));
        // Test 25
        instance = new V3D_PlaneDouble(pN1P1N1, pP0P1P0, pP1P1N1); // y=1
        expResult = new V3D_LineDouble(pP1P1N1, pP0P1N1);    // y=1, z=-1
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));
        // Test 26 to 27
        pl = new V3D_PlaneDouble(pN1P0P0, pN1P1P1, pN1P0P1); // x=-1
        // Test 26
        instance = new V3D_PlaneDouble(pN1N1N1, pP0N1P0, pP1N1N1); // y=-1
        expResult = new V3D_LineDouble(pN1N1P0, pN1N1P1);    // x=-1, y=-1
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));
        // Test 27
        instance = new V3D_PlaneDouble(pP1P0N1, pP0N1N1, pP0P0N1); // z=-1
        expResult = new V3D_LineDouble(pN1P1N1, pN1P0N1);    // x=-1, z=-1
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));
        // Test 28 to 29
        pl = new V3D_PlaneDouble(pN1N1N1, pP0N1P0, pP1N1N1); // y=-1
        // Test 28
        instance = new V3D_PlaneDouble(pN1P0P0, pN1P1P1, pN1P0P1); // x=-1
        expResult = new V3D_LineDouble(pN1N1P0, pN1N1P1);    // x=-1, y=-1
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));
        // Test 29
        instance = new V3D_PlaneDouble(pP1P0N1, pP0N1N1, pP0P0N1); // z=-1
        expResult = new V3D_LineDouble(pP1N1N1, pP0N1N1);    // y=-1, z=-1
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));
        // Test 30 to 31
        pl = new V3D_PlaneDouble(pP1P0N1, pP0N1N1, pP0P0N1); // z=-1
        // Test 30
        instance = new V3D_PlaneDouble(pN1P0P0, pN1P1P1, pN1P0P1); // x=-1
        expResult = new V3D_LineDouble(pN1P0N1, pN1P1N1);    // x=-1, z=-1
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));
        // Test 31
        instance = new V3D_PlaneDouble(pN1N1N1, pP0N1P0, pP1N1N1); // y=-1
        expResult = new V3D_LineDouble(pP1N1N1, pP0N1N1);    // y=-1, z=-1
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));

        // Test 32 to ?
        pl = new V3D_PlaneDouble(pN1P0P0, pN1P1P1, pN1P0P1); // x=-1
        // Test 32
        instance = new V3D_PlaneDouble(pN1N2N1, pP0N2P0, pP1N2N1); // y=-2
        expResult = new V3D_LineDouble(pN1N2P0, pN1N2P1);    // x=-1, y=-2
        result = instance.getIntersection(pl, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result, epsilon));
    }

    /**
     * Test of getIntersection method, of class V3D_PlaneDouble.
     */
    @Test
    public void testGetIntersection_V3D_PlaneDouble_V3D_PlaneDouble() {
        double epsilon = 1d / 10000000d;
        V3D_PlaneDouble instance;
        V3D_PlaneDouble pl1;
        V3D_PlaneDouble pl2;
        // Test 1
        instance = V3D_PlaneDouble.X0;
        pl1 = V3D_PlaneDouble.Y0;
        pl2 = V3D_PlaneDouble.Z0;
        V3D_PointDouble expResult = V3D_PointDouble.ORIGIN;
        V3D_PointDouble result = (V3D_PointDouble) instance.getIntersection(pl1, pl2, epsilon);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_PlaneDouble(V3D_PlaneDouble.X0);
        pl1 = V3D_PlaneDouble.Y0;
        pl2 = V3D_PlaneDouble.Z0;
        instance.translate(V3D_VectorDouble.I); // X = 1 Plane
        expResult = new V3D_PointDouble(V3D_PointDouble.ORIGIN);
        expResult.translate(V3D_VectorDouble.I);
        result = (V3D_PointDouble) instance.getIntersection(pl1, pl2, epsilon);
        assertTrue(expResult.equals(result));
        // Test 3
        instance = new V3D_PlaneDouble(V3D_PlaneDouble.X0);
        pl1 = V3D_PlaneDouble.Y0;
        pl2 = V3D_PlaneDouble.Z0;
        instance.translate(V3D_VectorDouble.J);
        expResult = new V3D_PointDouble(pP0P0P0);
        result = (V3D_PointDouble) instance.getIntersection(pl1, pl2, epsilon);
        assertTrue(expResult.equals(result));
        // Test 4
        instance = new V3D_PlaneDouble(V3D_PlaneDouble.X0);
        pl1 = V3D_PlaneDouble.Y0;
        pl2 = V3D_PlaneDouble.Z0;
        instance.translate(V3D_VectorDouble.K);
        expResult = new V3D_PointDouble(pP0P0P0);
        result = (V3D_PointDouble) instance.getIntersection(pl1, pl2, epsilon);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getIntersection method, of class V3D_PlaneDouble.
     */
    @Test
    public void testGetIntersection_V3D_LineDouble() {
        System.out.println("getIntersection");
        double epsilon = 1d / 10000000d;
        V3D_LineDouble l;
        V3D_PlaneDouble instance;
        V3D_GeometryDouble expResult;
        V3D_GeometryDouble result;
        // Test 1-3 axis with orthoganol plane through origin.
        // Test 1
        l = V3D_LineDouble.X_AXIS;
        instance = V3D_PlaneDouble.X0;
        expResult = pP0P0P0;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 2
        l = V3D_LineDouble.Y_AXIS;
        instance = V3D_PlaneDouble.Y0;
        expResult = pP0P0P0;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 3
        l = V3D_LineDouble.Z_AXIS;
        instance = V3D_PlaneDouble.Z0;
        expResult = pP0P0P0;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 4-6 axis with orthoganol plane not through origin.
        // Test 4
        l = V3D_LineDouble.X_AXIS;
        instance = new V3D_PlaneDouble(V3D_PlaneDouble.X0);
        instance.translate(P1P0P0);
        expResult = pP1P0P0;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 5
        l = V3D_LineDouble.Y_AXIS;
        instance = new V3D_PlaneDouble(V3D_PlaneDouble.Y0);
        instance.translate(P0P1P0);
        expResult = pP0P1P0;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 6
        l = V3D_LineDouble.Z_AXIS;
        instance = new V3D_PlaneDouble(V3D_PlaneDouble.Z0);
        instance.translate(P0P0P1);
        expResult = pP0P0P1;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 7
        // line
        // x = t, y = 2 + 3t, z = t
        // points (0, 2, 0), (1, 5, 1) 
        l = new V3D_LineDouble(V3D_VectorDouble.ZERO, P0P2P0, new V3D_VectorDouble(1d, 5d, 1d));
        // plane
        // 2x + y − 4z = 4
        // points (0, 0, -1), (0, 4, 0), (2, 0, 0)
        instance = new V3D_PlaneDouble(P0P0N1, new V3D_VectorDouble(0d, 4d, 0d), P2P0P0);
        // (2, 8, 2)
        expResult = new V3D_PointDouble(2d, 8d, 2d);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 9
        // line
        // x = t, y = 2 + 3t, z = t
        // points (0, 2, 0), (1, 5, 1) 
        l = new V3D_LineDouble(P0P2P0, new V3D_VectorDouble(1d, 5d, 1d));
        // plane
        // 2x + y − 4z = 4
        // points (0, 0, -1), (0, 4, 0), (2, 0, 0)
        instance = new V3D_PlaneDouble(new V3D_VectorDouble(0d, 0d, -1d),
                new V3D_VectorDouble(0d, 4d, 0d), new V3D_VectorDouble(2d, 0d, 0d));
        // (2, 8, 2)
        expResult = new V3D_PointDouble(2d, 8d, 2d);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 10
        // line
        // x = 0, y = 0, z = t
        // points (0, 0, 0), (0, 0, 1) 
        l = new V3D_LineDouble(pP0P0P0, pP0P0P1);
        // plane
        // points (0, 0, -1), (0, 4, 0), (2, 0, 0)
        instance = new V3D_PlaneDouble(P0P0P2, P1P0P2, P0P1P2);
        expResult = pP0P0P2;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 11
        l = V3D_LineDouble.X_AXIS;
        instance = V3D_PlaneDouble.X0;
        expResult = V3D_PointDouble.ORIGIN;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 12
        l = V3D_LineDouble.X_AXIS;
        instance = V3D_PlaneDouble.Y0;
        expResult = V3D_LineDouble.X_AXIS;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result));
        // Test 13
        l = V3D_LineDouble.X_AXIS;
        instance = V3D_PlaneDouble.Z0;
        expResult = V3D_LineDouble.X_AXIS;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result));
        // Test 14
        l = V3D_LineDouble.Y_AXIS;
        instance = V3D_PlaneDouble.X0;
        expResult = V3D_LineDouble.Y_AXIS;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result));
        // Test 15
        l = V3D_LineDouble.Y_AXIS;
        instance = V3D_PlaneDouble.Y0;
        expResult = V3D_PointDouble.ORIGIN;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 16
        l = V3D_LineDouble.Y_AXIS;
        instance = V3D_PlaneDouble.Z0;
        expResult = V3D_LineDouble.Y_AXIS;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result));
        // Test 17
        l = V3D_LineDouble.Z_AXIS;
        instance = V3D_PlaneDouble.X0;
        expResult = V3D_LineDouble.Z_AXIS;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result));
        // Test 18
        l = V3D_LineDouble.Z_AXIS;
        instance = V3D_PlaneDouble.Y0;
        expResult = V3D_LineDouble.Z_AXIS;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_LineDouble) expResult).equals((V3D_LineDouble) result));
        // Test 19
        l = V3D_LineDouble.Z_AXIS;
        instance = V3D_PlaneDouble.Z0;
        expResult = V3D_PointDouble.ORIGIN;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
    }

    /**
     * Test of isOnPlane method, of class V3D_PlaneDouble.
     */
    @Test
    public void testIsOnPlane() {
        System.out.println("isOnPlane");
        double epsilon = 1d / 10000000d;
        V3D_LineDouble l = new V3D_LineDouble(pP0P0P0, pP1P0P0);
        V3D_PlaneDouble instance = new V3D_PlaneDouble(P0P0P0, P1P0P0, P1P1P0);
        assertTrue(instance.isOnPlane(l, epsilon));
        // Test 2
        l = new V3D_LineDouble(pP0P0P0, pP1P1P0);
        assertTrue(instance.isOnPlane(l, epsilon));
    }

    /**
     * Test of getIntersection method, of class V3D_PlaneDouble.
     */
    @Test
    public void testGetIntersection_V3D_LineSegmentDouble() {
        System.out.println("getIntersection");
        double epsilon = 1d / 10000000d;
        V3D_LineSegmentDouble l;
        V3D_PlaneDouble instance;
        V3D_GeometryDouble expResult;
        V3D_GeometryDouble result;
        // Test 1-3 part of axis with orthoganol plane through origin.
        // Test 1
        l = new V3D_LineSegmentDouble(pN1P0P0, pP1P0P0);
        instance = V3D_PlaneDouble.X0;
        expResult = pP0P0P0;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 2
        l = new V3D_LineSegmentDouble(pP0N1P0, pP0P1P0);
        instance = V3D_PlaneDouble.Y0;
        expResult = pP0P0P0;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 3
        l = new V3D_LineSegmentDouble(pP0P0N1, pP0P0P1);
        instance = V3D_PlaneDouble.Z0;
        expResult = pP0P0P0;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 4-6 part of axis with orthoganol plane not through origin.
        // Test 4
        l = new V3D_LineSegmentDouble(pN1P0P0, pP1P0P0);
        instance = new V3D_PlaneDouble(V3D_PlaneDouble.X0);
        instance.translate(P1P0P0);
        expResult = pP1P0P0;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 5
        l = new V3D_LineSegmentDouble(pP0N1P0, pP0P1P0);
        instance = new V3D_PlaneDouble(V3D_PlaneDouble.Y0);
        instance.translate(P0P1P0);
        expResult = pP0P1P0;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 6
        l = new V3D_LineSegmentDouble(pP0P0N1, pP0P0P1);
        instance = new V3D_PlaneDouble(V3D_PlaneDouble.Z0);
        instance.translate(P0P0P1);
        expResult = pP0P0P1;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 7
        l = new V3D_LineSegmentDouble(P0P2P0, new V3D_VectorDouble(1d, 5d, 1d));
        instance = new V3D_PlaneDouble(P0P0N1, new V3D_VectorDouble(0d, 4d, 0d),
                new V3D_VectorDouble(2d, 0d, 0d));
        assertNull(instance.getIntersection(l, epsilon));
        // Test 8
        epsilon = 1d / 10000d;
        l = new V3D_LineSegmentDouble(P0P2P0, new V3D_VectorDouble(2d, 8d, 2d));
        result = instance.getIntersection(l, epsilon);
        expResult = new V3D_PointDouble(2d, 8d, 2d);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 9
        // line
        // x = t, y = 2 + 3t, z = t
        // points (0, 2, 0), (1, 5, 1) 
        l = new V3D_LineSegmentDouble(P0P2P0, new V3D_VectorDouble(1d, 5d, 1d));
        // plane
        // 2x + y − 4z = 4
        // points (0, 0, -1), (0, 4, 0), (2, 0, 0)
        instance = new V3D_PlaneDouble(P0P0N1, new V3D_VectorDouble(0d, 4d, 0d), P2P0P0);
        // (2, 8, 2)
        expResult = new V3D_PointDouble(2d, 8d, 2d);
        result = instance.getIntersection(l, epsilon);
        assertNotEquals(expResult, result);
        l = new V3D_LineSegmentDouble(P0P2P0, new V3D_VectorDouble(2d, 8d, 2d));
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 10
        // line
        // x = 0, y = 0, z = t
        // points (0, 0, 0), (0, 0, 1) 
        l = new V3D_LineSegmentDouble(pP0P0P0, pP0P0P1);
        // plane
        // points (0, 0, -1), (0, 4, 0), (2, 0, 0)
        instance = new V3D_PlaneDouble(P0P0P2, P1P0P2, P0P1P2);
        result = instance.getIntersection(l, epsilon);
        assertNull(result);
        l = new V3D_LineSegmentDouble(P0P0P0, new V3D_VectorDouble(0d, 0d, 4d));
        expResult = pP0P0P2;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V3D_PointDouble) expResult).equals((V3D_PointDouble) result));
        // Test 11
        l = new V3D_LineSegmentDouble(new V3D_PointDouble(4d, 4d, 0d), new V3D_PointDouble(5d, 5d, 0d));
        instance = new V3D_PlaneDouble(new V3D_PointDouble(6d, 6d, 0d), new V3D_VectorDouble(30d, 30d, 0d));
        result = instance.getIntersection(l, epsilon);
        assertNull(result);
    }

    /**
     * Test of isEnvelopeIntersectedBy method, of class V3D_PlaneDouble.
     */
    @Test
    public void testIsEnvelopeIntersectedBy() {
        System.out.println("isEnvelopeIntersectedBy");
        // No test.
    }

    /**
     * Test of getAsMatrix method, of class V3D_PlaneDouble.
     */
    @Test
    public void testGetAsMatrix() {
        System.out.println("getAsMatrix");
        double epsilon = 1d / 10000000d;
        V3D_PlaneDouble instance = V3D_PlaneDouble.X0;
        double[][] m = new double[3][3];
        m[0][0] = 0d;
        m[0][1] = 0d;
        m[0][2] = 0d;
//        m[1][0] = 0d;
//        m[1][1] = 1d;
//        m[1][2] = 0d;
//        m[2][0] = 0d;
//        m[2][1] = 0d;
//        m[2][2] = -1d;
        m[1][0] = 0d;
        m[1][1] = 0d;
        m[1][2] = -1d;
        m[2][0] = 0d;
        m[2][1] = -1d;
        m[2][2] = 0d;
        Math_Matrix_Double expResult = new Math_Matrix_Double(m);
        Math_Matrix_Double result = instance.getAsMatrix(epsilon);
        assertTrue(expResult.getRows().length == result.getRows().length);
        assertTrue(expResult.getCols().length == result.getCols().length);
        for (int i = 0; i < expResult.getRows().length; i++) {
            for (int j = 0; j < expResult.getCols().length; j++) {
//                if (expResult.getRows()[i][j].compareTo(result.getRows()[i][j]) != 0) {
//                    int debug = 1;
//                }
                assertTrue(expResult.getRows()[i][j] == result.getRows()[i][j]);
            }
        }
    }

    /**
     * Test of getDistanceSquared method, of class V3D_PlaneDouble.
     */
    @Test
    public void testGetDistanceSquared() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V3D_PlaneDouble p = V3D_PlaneDouble.X0;
        V3D_PlaneDouble instance = V3D_PlaneDouble.X0;
        double expResult = 0d;
        double result = instance.getDistanceSquared(p, epsilon);
        assertTrue(expResult == result);
        // Test 2
        V3D_VectorDouble v = V3D_VectorDouble.I;
        instance = new V3D_PlaneDouble(V3D_PlaneDouble.X0);
        instance.translate(v);
        expResult = 1d;
        result = instance.getDistanceSquared(p, epsilon);
        assertTrue(expResult == result);
        // Test 3
        v = V3D_VectorDouble.J;
        instance = new V3D_PlaneDouble(V3D_PlaneDouble.X0);
        instance.translate(v);
        expResult = 0d;
        result = instance.getDistanceSquared(p, epsilon);
        assertTrue(expResult == result);
        // Test 4
        v = V3D_VectorDouble.K;
        instance = new V3D_PlaneDouble(V3D_PlaneDouble.X0);
        instance.translate(v);
        expResult = 0d;
        result = instance.getDistanceSquared(p, epsilon);
        assertTrue(expResult == result);
    }

    /**
     * Test of isCoincident method, of class V3D_PlaneDouble.
     */
    @Test
    public void testIsCoincident() {
        // No test as covered by other tests.
    }

    /**
     * Test of getDistance method, of class V3D_PlaneDouble.
     */
    @Test
    public void testGetDistance_V3D_PointDouble() {
        System.out.println("getDistance");
        double epsilon = 1d / 10000000d;
        V3D_PointDouble p = new V3D_PointDouble(5d, 0d, 0d);
        V3D_PlaneDouble instance = new V3D_PlaneDouble(P0P0P0, P0P0P1, P0P1P1);
        double expResult = 5d;
        double result = instance.getDistance(p);
        assertTrue(expResult == result);
        // Test 2
        p = new V3D_PointDouble(5d, 10d, 0d);
        result = instance.getDistance(p);
        assertTrue(expResult == result);
    }

    /**
     * Test of getDistance method, of class V3D_PlaneDouble.
     */
    @Test
    public void testGetDistance_V3D_LineSegmentDouble() {
        System.out.println("getDistance");
        V3D_LineSegmentDouble l = new V3D_LineSegmentDouble(
                new V3D_VectorDouble(10d, 1d, 1d),
                new V3D_VectorDouble(100d, 1d, 1d));
        V3D_PlaneDouble instance = V3D_PlaneDouble.X0;
        double expResult = 10d;
        double epsilon = 1d / 1000000d;
        double result = instance.getDistance(l, epsilon);
        assertTrue(expResult == result);
    }

    /**
     * Test of toString method, of class V3D_PlaneDouble.
     */
    @Test
    public void testToString_String() {
        System.out.println("toString");
        String pad = "";
        V3D_PlaneDouble instance = V3D_PlaneDouble.X0;
        String expResult = """
                           V3D_PlaneDouble
                           (
                            offset=V3D_VectorDouble
                            (
                             dx=0.0,
                             dy=0.0,
                             dz=0.0
                            )
                            ,
                            p=V3D_VectorDouble
                            (
                             dx=0.0,
                             dy=0.0,
                             dz=0.0
                            )
                            ,
                            n=V3D_VectorDouble
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

//    /**
//     * Test of toStringFields method, of class V3D_PlaneDouble.
//     */
//    @Test
//    public void testToStringFields() {
//        System.out.println("toStringFields");
//        String pad = "";
//        V3D_PlaneDouble instance = V3D_PlaneDouble.X0;
//        String expResult = """
//                           offset=V3D_VectorDouble
//                           (
//                            dx=0.0,
//                            dy=0.0,
//                            dz=0.0
//                           )
//                           ,
//                           p=V3D_VectorDouble
//                           (
//                            dx=0.0,
//                            dy=0.0,
//                            dz=0.0
//                           )
//                           ,
//                           n=V3D_VectorDouble
//                           (
//                            dx=1.0,
//                            dy=0.0,
//                            dz=0.0
//                           )""";
//        String result = instance.toStringFields(pad);
//        //System.out.println(result);
//        assertEquals(expResult, result);
//    }
    /**
     * Test of getPAsVector method, of class V3D_PlaneDouble.
     */
    @Test
    public void testGetPV() {
        System.out.println("getPV");
        double epsilon = 1d / 10000000d;
        V3D_PlaneDouble instance = V3D_PlaneDouble.X0;
        assertTrue(instance.getPV(epsilon).getDotProduct(instance.getN()) == 0d);
        // Test 2
        instance = V3D_PlaneDouble.Y0;
        assertTrue(instance.getPV(epsilon).getDotProduct(instance.getN()) == 0d);
        // Test 3
        instance = V3D_PlaneDouble.Z0;
        assertTrue(instance.getPV(epsilon).getDotProduct(instance.getN()) == 0d);
    }

    /**
     * Test of getP method, of class V3D_PlaneDouble.
     */
    @Test
    public void testGetP() {
        System.out.println("getP");
        V3D_PlaneDouble instance = V3D_PlaneDouble.X0;
        V3D_PointDouble expResult = V3D_PointDouble.ORIGIN;
        V3D_PointDouble result = instance.getP();
        assertTrue((expResult).equals(result));
    }

    /**
     * Test of getQ method, of class V3D_PlaneDouble.
     */
    @Test
    public void testGetQ() {
        System.out.println("getQ");
        double epsilon = 1d / 10000000d;
        V3D_PlaneDouble instance = V3D_PlaneDouble.X0;
        assertTrue(instance.isIntersectedBy(instance.getQ(epsilon), 0d));
    }

    /**
     * Test of getR method, of class V3D_PlaneDouble.
     */
    @Test
    public void testGetR() {
        System.out.println("getR");
        double epsilon = 1d / 10000000d;
        V3D_PlaneDouble instance = V3D_PlaneDouble.X0;
        assertTrue(instance.isIntersectedBy(instance.getR(epsilon), 0d));
    }

    /**
     * Test of getEquationCoefficients method, of class V3D_PlaneDouble.
     */
    @Test
    public void testGetEquation_int_RoundingMode() {
        System.out.println("getEquationCoefficients");
        double epsilon = 1d / 10000000d;
        V3D_PlaneDouble instance = new V3D_PlaneDouble(P0P0P0, P1P1P1, P1P0P0);
        double[] expResult = new double[4];
        expResult[0] = 0d;
        expResult[1] = 1d;
        expResult[2] = -1d;
        expResult[3] = 0d;
        double[] result = instance.getEquation().coeffs;
        for (int i = 0; i < result.length; i++) {
            assertTrue(Math_Double.equals(result[i], expResult[i], epsilon));
        }
    }

    /**
     * Test of rotate method, of class V3D_PlaneDouble.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        double epsilon = 1d / 10000000d;
        double Pi = Math.PI;
        V3D_RayDouble xaxis = new V3D_RayDouble(V3D_DoubleTest.pP0P0P0, V3D_VectorDouble.I);
        V3D_RayDouble yaxis = new V3D_RayDouble(V3D_DoubleTest.pP0P0P0, V3D_VectorDouble.J);
        V3D_RayDouble zaxis = new V3D_RayDouble(V3D_DoubleTest.pP0P0P0, V3D_VectorDouble.K);
        // Test 1;
        double theta = Pi / 2d;
        V3D_PlaneDouble instance = new V3D_PlaneDouble(V3D_PlaneDouble.X0);
        instance = instance.rotate(xaxis, xaxis.l.v, theta, epsilon);
        assertTrue(V3D_PlaneDouble.X0.equalsIgnoreOrientation(instance, epsilon));
        // Test 2
        instance = new V3D_PlaneDouble(V3D_PlaneDouble.X0);
        instance = instance.rotate(yaxis, yaxis.l.v, theta, epsilon);
        assertTrue(V3D_PlaneDouble.Z0.equalsIgnoreOrientation(instance, epsilon));
        // Test 3
        instance = new V3D_PlaneDouble(V3D_PlaneDouble.X0);
        instance = instance.rotate(zaxis, zaxis.l.v, theta, epsilon);
        assertTrue(V3D_PlaneDouble.Y0.equalsIgnoreOrientation(instance, epsilon));
        // Test 4
        theta = Pi;
        instance = new V3D_PlaneDouble(P1P0P0, P0P0P0, P0P2P0, P0P2P2);
        instance = instance.rotate(xaxis, xaxis.l.v, theta, epsilon);
        assertTrue(new V3D_PlaneDouble(P1P0P0, P0P0P0, P0P2P0, P0P2P2).equalsIgnoreOrientation(instance, epsilon));
        // Test 5
        theta = Pi;
        instance = new V3D_PlaneDouble(P1P0P0, P0P0P0, P0P2P0, P0P2P2);
        instance = instance.rotate(yaxis, yaxis.l.v, theta, epsilon);
        V3D_PlaneDouble expResult = new V3D_PlaneDouble(N1P0P0, P0P0P0, P0P2P0, P0P2N2);
        assertTrue(expResult.equalsIgnoreOrientation(instance, epsilon));
        // Test 6
        theta = Pi;
        instance = new V3D_PlaneDouble(P1P0P0, P0P0P0, P0P2P0, P0P2P2);
        instance = instance.rotate(zaxis, zaxis.l.v, theta, epsilon);
        expResult = new V3D_PlaneDouble(N1P0P0, P0P0P0, P0P2P0, P0P2P2);
        assertTrue(expResult.equalsIgnoreOrientation(instance, epsilon));
    }

    /**
     * Test constructor method, of class V3D_PlaneDouble.
     */
    @Test
    public void testConstructor() {
        System.out.println("testConstructors");
        double epsilon = 1d / 10000000d;
        V3D_PlaneDouble pl0 = V3D_PlaneDouble.X0;
        V3D_PlaneDouble pl1 = new V3D_PlaneDouble(V3D_VectorDouble.I, pl0,
                epsilon);
        V3D_PlaneDouble pl2 = new V3D_PlaneDouble(V3D_VectorDouble.I, N1P0P0,
                N1P1P0, N1P0P1);
        assertTrue(pl0.equalsIgnoreOrientation(pl1, epsilon));
        assertTrue(pl0.equalsIgnoreOrientation(pl2, epsilon));
        assertTrue(pl1.equalsIgnoreOrientation(pl2, epsilon));
    }

    /**
     * Test of getDistanceSquared method, of class V3D_PlaneDouble.
     */
    @Test
    public void testGetDistanceSquared_V3D_PointDouble_int() {
        System.out.println("getDistanceSquared");
        V3D_PointDouble pt;
        V3D_PlaneDouble instance;
        double expResult;
        double result;
        double epsilon = 1d / 10000000d;
        // Test 1
        pt = pP1P0P0;
        instance = V3D_PlaneDouble.X0;
        expResult = 1d;
        result = instance.getDistanceSquared(pt, epsilon);
        assertEquals(expResult, result);
        // Test 2
        pt = pP0P0P0;
        expResult = 0d;
        result = instance.getDistanceSquared(pt, epsilon);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistanceSquared method, of class V3D_PlaneDouble covered by
     * {@link #testGetDistanceSquared_V3D_PointDouble_int()}.
     */
    @Test
    public void testGetDistanceSquared_3args() {
        System.out.println("getDistanceSquared");
    }

    /**
     * Test of getDistance method, of class V3D_PlaneDouble covered by
     * {@link #testGetDistanceSquared_V3D_PlaneDouble_int()}.
     */
    @Test
    public void testGetDistance_V3D_PlaneDouble_int() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistanceSquared method, of class V3D_PlaneDouble.
     */
    @Test
    public void testGetDistanceSquared_V3D_PlaneDouble_int() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V3D_PlaneDouble pl;
        V3D_PlaneDouble instance;
        double expResult;
        double result;
        // Test 1
        pl = V3D_PlaneDouble.X0;
        instance = V3D_PlaneDouble.Y0;
        expResult = 0d;
        result = instance.getDistanceSquared(pl, epsilon);
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_PlaneDouble(V3D_PlaneDouble.X0);
        instance.translate(P1P0P0);
        expResult = 1d;
        result = instance.getDistanceSquared(pl, epsilon);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistanceSquared method, of class V3D_PlaneDouble.
     */
    @Test
    public void testGetDistanceSquared_V3D_LineDouble() {
        System.out.println("getDistanceSquared");
        V3D_LineDouble l;
        V3D_PlaneDouble instance;
        double expResult;
        double result;
        // Test 1
        l = V3D_LineDouble.X_AXIS;
        instance = V3D_PlaneDouble.Y0;
        expResult = 0d;
        result = instance.getDistanceSquared(l);
        assertEquals(expResult, result);
        // Test 2
        double epsilon = 1d / 10000000d;
        instance = new V3D_PlaneDouble(P0P1P0, V3D_PlaneDouble.Y0, epsilon);
        expResult = 0d;
        result = instance.getDistanceSquared(l);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistanceSquared method, of class V3D_PlaneDouble.
     */
    @Test
    public void testGetDistanceSquared_V3D_LineSegmentDouble_int() {
        System.out.println("getDistanceSquared");
        V3D_LineSegmentDouble l;
        V3D_PlaneDouble instance;
        double expResult;
        double result;
        double epsilon = 1d / 10000000d;
        // Test 1
        l = new V3D_LineSegmentDouble(pP0P0P0, pP1P0P0);
        instance = V3D_PlaneDouble.Y0;
        expResult = 0d;
        result = instance.getDistanceSquared(l, epsilon);
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_PlaneDouble(P0P1P0, V3D_PlaneDouble.Y0, epsilon);
        expResult = 0d;
        result = instance.getDistanceSquared(l, epsilon);
        assertEquals(expResult, result);
    }

    /**
     * Test of getPointOfProjectedIntersection method, of class V3D_PlaneDouble.
     */
    @Test
    public void testGetPointOfProjectedIntersection() {
        System.out.println("getPointOfProjectedIntersection");
        double epsilon = 1d / 10000000d;
        V3D_PointDouble pt;
        V3D_PlaneDouble instance;
        V3D_PointDouble expResult;
        V3D_PointDouble result;
        // Test 1
        pt = pP1P0P0;
        instance = V3D_PlaneDouble.X0;
        expResult = pP0P0P0;
        result = instance.getPointOfProjectedIntersection(pt, epsilon);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of isCoplanar method, of class V3D_Geometrics.
     */
    @Test
    public void testIsCoplanar_3args() {
        System.out.println("isCoplanar");
        double epsilon = 1d / 10000000d;
        V3D_PlaneDouble p;
        V3D_PointDouble[] points;
        // Test 1
        p = new V3D_PlaneDouble(pP0P0P0, pP1P0P0, pP0P1P0);
        points = new V3D_PointDouble[3];
        points[0] = pP2P2P0;
        points[1] = pN2P2P0;
        points[2] = pP2N2P0;
        assertTrue(V3D_PlaneDouble.isCoplanar(epsilon, p, points));
        // Test 2
        points[2] = pP2N2P1;
        assertFalse(V3D_PlaneDouble.isCoplanar(epsilon, p, points));
        // Test 3
        p = new V3D_PlaneDouble(pP0P0P0, pP0P1P0, pP0P0P1);
        points[0] = pP0P2P2;
        points[1] = pP0N2P2;
        points[2] = pP0P2N2;
        assertTrue(V3D_PlaneDouble.isCoplanar(epsilon, p, points));
        // Test 4
        points[2] = pP2N2P1;
        assertFalse(V3D_PlaneDouble.isCoplanar(epsilon, p, points));
    }

    /**
     * Test of isCoplanar method, of class V3D_Geometrics.
     */
    @Test
    public void testIsCoplanar_int_V3D_PointDoubleArr() {
        System.out.println("isCoplanar");
        double epsilon = 1d / 10000000d;
        V3D_PointDouble[] points = new V3D_PointDouble[6];
        points[0] = pP0P0P0;
        points[1] = pP0P1P0;
        points[2] = pP0P0P1;
        points[3] = pP0P2P2;
        points[4] = pP0N2P2;
        points[5] = pP0P2N2;
        assertTrue(V3D_PlaneDouble.isCoplanar(epsilon, points));
        // Test 2
        points[2] = pP2N2P1;
        assertFalse(V3D_PlaneDouble.isCoplanar(epsilon, points));
    }

    /**
     * Test of getPlane method, of class V3D_Geometrics. No test needed.
     */
    @Test
    @Disabled
    public void testGetPlane() {
        System.out.println("getPlane");
        double epsilon = 1d / 10000000d;
        V3D_PointDouble[] points = null;
        V3D_PlaneDouble expResult = null;
        V3D_PlaneDouble result = V3D_PlaneDouble.getPlane(epsilon, points);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
