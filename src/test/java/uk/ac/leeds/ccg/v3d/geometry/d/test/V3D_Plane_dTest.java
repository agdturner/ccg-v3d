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
import uk.ac.leeds.ccg.math.matrices.Math_Matrix_Double;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Geometry_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Line_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_LineSegment_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Plane_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Point_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Ray_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Vector_d;

/**
 * Test of V3D_Plane_d class.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Plane_dTest extends V3D_Test_d {

    public V3D_Plane_dTest() {
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
//        testIsOnPlane_V3D_Point_d();
//        testIsOnPlane_V3D_Point_d();
//        testIsOnPlane_V3D_LineSegment_d();
//        testEquals();
//        testIsIntersectedBy_V3D_Point_d();
//        testIsIntersectedBy_V3D_Line_d();
//        testIsIntersectedBy_V3D_Plane_d();
//        testGetNormalVector();
//        testIsParallel_V3D_Plane_d();
//        testIsParallel_V3D_Line_d();
//
//        testGetIntersection_V3D_Plane_d();
    }

    /**
     * Test of toString method, of class V3D_Plane_d.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        V3D_Plane_d instance = new V3D_Plane_d(pP0P0P0, pP1P1P1, pP1P0P0);
        String expResult = """
                           V3D_Plane_d(
                            offset=V3D_Vector_d(dx=0.0, dy=0.0, dz=0.0),
                            pv=V3D_Vector_d(dx=0.0, dy=0.0, dz=0.0),
                            n=V3D_Vector_d(dx=0.0, dy=1.0, dz=-1.0))""";
        String result = instance.toString();
        //System.out.println(result);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of toString method, of class V3D_Plane_d.
     */
    @Test
    public void testGetEquation() {
        System.out.println("getEquation");
        V3D_Plane_d instance;
        String expResult;
        String result;
        // Test 1
        instance = new V3D_Plane_d(pP0P0P0, pP1P1P1, pP1P0P0);
        expResult = "y=z";
        result = instance.getEquationString();
        assertTrue(expResult.equalsIgnoreCase(result));
        // Test 2
        instance = new V3D_Plane_d(env, P1N2P1, new V3D_Vector_d(4d, -2d, -2d),
                new V3D_Vector_d(4d, 1d, 4d));
        expResult = "9.0(x)+9.0(z)=18.0(y)+54.0";
        result = instance.getEquationString();
        assertTrue(expResult.equalsIgnoreCase(result));
    }

    /**
     * Test of isOnPlane method, of class V3D_Plane_d.
     */
    @Test
    public void testIsOnPlane_V3D_LineSegment_d_double() {
        System.out.println("isOnPlane");
        double epsilon = 0.00000001d;
        // Test 1 to 9 lines segments in line with axes
        V3D_LineSegment_d l = new V3D_LineSegment_d(pP0P0P0, pP1P0P0);
        V3D_Plane_d instance = V3D_Plane_d.X0;
        assertFalse(instance.isOnPlane(l.l));
        // Test 2
        instance = V3D_Plane_d.Y0;
        assertTrue(instance.isOnPlane(l.l));
        // Test 3
        instance = V3D_Plane_d.Z0;
        assertTrue(instance.isOnPlane(l.l));
        // Test 4
        l = new V3D_LineSegment_d(pP0P0P0, pP0P1P0);
        instance = V3D_Plane_d.X0;
        assertTrue(instance.isOnPlane(l.l));
        // Test 5
        instance = V3D_Plane_d.Y0;
        assertFalse(instance.isOnPlane(l.l));
        // Test 6
        instance = V3D_Plane_d.Z0;
        assertTrue(instance.isOnPlane(l.l));
        // Test 7
        l = new V3D_LineSegment_d(pP0P0P0, pP0P0P1);
        instance = V3D_Plane_d.X0;
        assertTrue(instance.isOnPlane(l.l));
        // Test 8
        instance = V3D_Plane_d.Y0;
        assertTrue(instance.isOnPlane(l.l));
        // Test 9
        instance = V3D_Plane_d.Z0;
        assertFalse(instance.isOnPlane(l.l));
        
        // Test 10
        V3D_Point_d p = new V3D_Point_d(pP0P0P0);
        p.translate(new V3D_Vector_d(epsilon, 0d, 0d));
        l = new V3D_LineSegment_d(p, pP0P0P1);
        instance = V3D_Plane_d.X0;
        assertTrue(instance.isOnPlane(l.l, epsilon));
        // Test 11
        instance = V3D_Plane_d.Y0;
        assertTrue(instance.isOnPlane(l.l, epsilon));
        // Test 12
        instance = V3D_Plane_d.Z0;
        assertFalse(instance.isOnPlane(l.l, epsilon));
    }
    
    /**
     * Test of isOnPlane method, of class V3D_Plane_d.
     */
    @Test
    public void testIsOnPlane_V3D_LineSegment_d() {
        System.out.println("isOnPlane");
        // Test 1 to 9 lines segments in line with axes
        V3D_LineSegment_d l = new V3D_LineSegment_d(pP0P0P0, pP1P0P0);
        V3D_Plane_d instance = V3D_Plane_d.X0;
        assertFalse(instance.isOnPlane(l.l));
        // Test 2
        instance = V3D_Plane_d.Y0;
        assertTrue(instance.isOnPlane(l.l));
        // Test 3
        instance = V3D_Plane_d.Z0;
        assertTrue(instance.isOnPlane(l.l));
        // Test 4
        l = new V3D_LineSegment_d(pP0P0P0, pP0P1P0);
        instance = V3D_Plane_d.X0;
        assertTrue(instance.isOnPlane(l.l));
        // Test 5
        instance = V3D_Plane_d.Y0;
        assertFalse(instance.isOnPlane(l.l));
        // Test 6
        instance = V3D_Plane_d.Z0;
        assertTrue(instance.isOnPlane(l.l));
        // Test 7
        l = new V3D_LineSegment_d(pP0P0P0, pP0P0P1);
        instance = V3D_Plane_d.X0;
        assertTrue(instance.isOnPlane(l.l));
        // Test 8
        instance = V3D_Plane_d.Y0;
        assertTrue(instance.isOnPlane(l.l));
        // Test 9
        instance = V3D_Plane_d.Z0;
        assertFalse(instance.isOnPlane(l.l));
        // Test 10
        double epsilon = 0.00000001d;
        V3D_Point_d p = new V3D_Point_d(pP0P0P0);
        p.translate(new V3D_Vector_d(epsilon, 0d, 0d));
        l = new V3D_LineSegment_d(p, pP0P0P1);
        instance = V3D_Plane_d.X0;
        assertFalse(instance.isOnPlane(l.l));
        // Test 11
        instance = V3D_Plane_d.Y0;
        assertTrue(instance.isOnPlane(l.l));
        // Test 12
        instance = V3D_Plane_d.Z0;
        assertFalse(instance.isOnPlane(l.l));
    }

    /**
     * Test of intersects method, of class V3D_Plane_d.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point_d_int() {
        System.out.println("intersects");
        double epsilon = 0.0000000001d;
        V3D_Plane_d instance;
        // x=0
        instance = new V3D_Plane_d(env, P0P0P0, P0P1P0, P0P0P1);
        // P2
        assertFalse(instance.intersects(epsilon, pP2P2P2));
        assertFalse(instance.intersects(epsilon, pP2P2P1));
        assertFalse(instance.intersects(epsilon, pP2P2P0));
        assertFalse(instance.intersects(epsilon, pP2P2N1));
        assertFalse(instance.intersects(epsilon, pP2P2N2));
        assertFalse(instance.intersects(epsilon, pP2P1P2));
        assertFalse(instance.intersects(epsilon, pP2P1P1));
        assertFalse(instance.intersects(epsilon, pP2P1P0));
        assertFalse(instance.intersects(epsilon, pP2P1N1));
        assertFalse(instance.intersects(epsilon, pP2P1N2));
        assertFalse(instance.intersects(epsilon, pP2P0P2));
        assertFalse(instance.intersects(epsilon, pP2P0P1));
        assertFalse(instance.intersects(epsilon, pP2P0P0));
        assertFalse(instance.intersects(epsilon, pP2P0N1));
        assertFalse(instance.intersects(epsilon, pP2P0N2));
        assertFalse(instance.intersects(epsilon, pP2N1P2));
        assertFalse(instance.intersects(epsilon, pP2N1P1));
        assertFalse(instance.intersects(epsilon, pP2N1P0));
        assertFalse(instance.intersects(epsilon, pP2N1N1));
        assertFalse(instance.intersects(epsilon, pP2N1N2));
        assertFalse(instance.intersects(epsilon, pP2N2P2));
        assertFalse(instance.intersects(epsilon, pP2N2P1));
        assertFalse(instance.intersects(epsilon, pP2N2P0));
        assertFalse(instance.intersects(epsilon, pP2N2N1));
        assertFalse(instance.intersects(epsilon, pP2N2N2));
        // P1
        assertFalse(instance.intersects(epsilon, pP1P2P2));
        assertFalse(instance.intersects(epsilon, pP1P2P1));
        assertFalse(instance.intersects(epsilon, pP1P2P0));
        assertFalse(instance.intersects(epsilon, pP1P2N1));
        assertFalse(instance.intersects(epsilon, pP1P2N2));
        assertFalse(instance.intersects(epsilon, pP1P1P2));
        assertFalse(instance.intersects(epsilon, pP1P1P1));
        assertFalse(instance.intersects(epsilon, pP1P1P0));
        assertFalse(instance.intersects(epsilon, pP1P1N1));
        assertFalse(instance.intersects(epsilon, pP1P1N2));
        assertFalse(instance.intersects(epsilon, pP1P0P2));
        assertFalse(instance.intersects(epsilon, pP1P0P1));
        assertFalse(instance.intersects(epsilon, pP1P0P0));
        assertFalse(instance.intersects(epsilon, pP1P0N1));
        assertFalse(instance.intersects(epsilon, pP1P0N2));
        assertFalse(instance.intersects(epsilon, pP1N1P2));
        assertFalse(instance.intersects(epsilon, pP1N1P1));
        assertFalse(instance.intersects(epsilon, pP1N1P0));
        assertFalse(instance.intersects(epsilon, pP1N1N1));
        assertFalse(instance.intersects(epsilon, pP1N1N2));
        assertFalse(instance.intersects(epsilon, pP1N2P2));
        assertFalse(instance.intersects(epsilon, pP1N2P1));
        assertFalse(instance.intersects(epsilon, pP1N2P0));
        assertFalse(instance.intersects(epsilon, pP1N2N1));
        assertFalse(instance.intersects(epsilon, pP1N2N2));
        // P0
        assertTrue(instance.intersects(epsilon, pP0P2P2));
        assertTrue(instance.intersects(epsilon, pP0P2P1));
        assertTrue(instance.intersects(epsilon, pP0P2P0));
        assertTrue(instance.intersects(epsilon, pP0P2N1));
        assertTrue(instance.intersects(epsilon, pP0P2N2));
        assertTrue(instance.intersects(epsilon, pP0P1P2));
        assertTrue(instance.intersects(epsilon, pP0P1P1));
        assertTrue(instance.intersects(epsilon, pP0P1P0));
        assertTrue(instance.intersects(epsilon, pP0P1N1));
        assertTrue(instance.intersects(epsilon, pP0P1N2));
        assertTrue(instance.intersects(epsilon, pP0P0P2));
        assertTrue(instance.intersects(epsilon, pP0P0P1));
        assertTrue(instance.intersects(epsilon, pP0P0P0));
        assertTrue(instance.intersects(epsilon, pP0P0N1));
        assertTrue(instance.intersects(epsilon, pP0P0N2));
        assertTrue(instance.intersects(epsilon, pP0N1P2));
        assertTrue(instance.intersects(epsilon, pP0N1P1));
        assertTrue(instance.intersects(epsilon, pP0N1P0));
        assertTrue(instance.intersects(epsilon, pP0N1N1));
        assertTrue(instance.intersects(epsilon, pP0N1N2));
        assertTrue(instance.intersects(epsilon, pP0N2P2));
        assertTrue(instance.intersects(epsilon, pP0N2P1));
        assertTrue(instance.intersects(epsilon, pP0N2P0));
        assertTrue(instance.intersects(epsilon, pP0N2N1));
        assertTrue(instance.intersects(epsilon, pP0N2N2));
        // N1
        assertFalse(instance.intersects(epsilon, pN1P2P2));
        assertFalse(instance.intersects(epsilon, pN1P2P1));
        assertFalse(instance.intersects(epsilon, pN1P2P0));
        assertFalse(instance.intersects(epsilon, pN1P2N1));
        assertFalse(instance.intersects(epsilon, pN1P2N2));
        assertFalse(instance.intersects(epsilon, pN1P1P2));
        assertFalse(instance.intersects(epsilon, pN1P1P1));
        assertFalse(instance.intersects(epsilon, pN1P1P0));
        assertFalse(instance.intersects(epsilon, pN1P1N1));
        assertFalse(instance.intersects(epsilon, pN1P1N2));
        assertFalse(instance.intersects(epsilon, pN1P0P2));
        assertFalse(instance.intersects(epsilon, pN1P0P1));
        assertFalse(instance.intersects(epsilon, pN1P0P0));
        assertFalse(instance.intersects(epsilon, pN1P0N1));
        assertFalse(instance.intersects(epsilon, pN1P0N2));
        assertFalse(instance.intersects(epsilon, pN1N1P2));
        assertFalse(instance.intersects(epsilon, pN1N1P1));
        assertFalse(instance.intersects(epsilon, pN1N1P0));
        assertFalse(instance.intersects(epsilon, pN1N1N1));
        assertFalse(instance.intersects(epsilon, pN1N1N2));
        assertFalse(instance.intersects(epsilon, pN1N2P2));
        assertFalse(instance.intersects(epsilon, pN1N2P1));
        assertFalse(instance.intersects(epsilon, pN1N2P0));
        assertFalse(instance.intersects(epsilon, pN1N2N1));
        assertFalse(instance.intersects(epsilon, pN1N2N2));
        // N2
        assertFalse(instance.intersects(epsilon, pN2P2P2));
        assertFalse(instance.intersects(epsilon, pN2P2P1));
        assertFalse(instance.intersects(epsilon, pN2P2P0));
        assertFalse(instance.intersects(epsilon, pN2P2N1));
        assertFalse(instance.intersects(epsilon, pN2P2N2));
        assertFalse(instance.intersects(epsilon, pN2P1P2));
        assertFalse(instance.intersects(epsilon, pN2P1P1));
        assertFalse(instance.intersects(epsilon, pN2P1P0));
        assertFalse(instance.intersects(epsilon, pN2P1N1));
        assertFalse(instance.intersects(epsilon, pN2P1N2));
        assertFalse(instance.intersects(epsilon, pN2P0P2));
        assertFalse(instance.intersects(epsilon, pN2P0P1));
        assertFalse(instance.intersects(epsilon, pN2P0P0));
        assertFalse(instance.intersects(epsilon, pN2P0N1));
        assertFalse(instance.intersects(epsilon, pN2P0N2));
        assertFalse(instance.intersects(epsilon, pN2N1P2));
        assertFalse(instance.intersects(epsilon, pN2N1P1));
        assertFalse(instance.intersects(epsilon, pN2N1P0));
        assertFalse(instance.intersects(epsilon, pN2N1N1));
        assertFalse(instance.intersects(epsilon, pN2N1N2));
        assertFalse(instance.intersects(epsilon, pN2N2P2));
        assertFalse(instance.intersects(epsilon, pN2N2P1));
        assertFalse(instance.intersects(epsilon, pN2N2P0));
        assertFalse(instance.intersects(epsilon, pN2N2N1));
        assertFalse(instance.intersects(epsilon, pN2N2N2));

        // y=0
        instance = new V3D_Plane_d(env, P1P0P0, P0P0P0, P0P0P1);
        // P2
        assertFalse(instance.intersects(epsilon, pP2P2P2));
        assertFalse(instance.intersects(epsilon, pP2P2P1));
        assertFalse(instance.intersects(epsilon, pP2P2P0));
        assertFalse(instance.intersects(epsilon, pP2P2N1));
        assertFalse(instance.intersects(epsilon, pP2P2N2));
        assertFalse(instance.intersects(epsilon, pP2P1P2));
        assertFalse(instance.intersects(epsilon, pP2P1P1));
        assertFalse(instance.intersects(epsilon, pP2P1P0));
        assertFalse(instance.intersects(epsilon, pP2P1N1));
        assertFalse(instance.intersects(epsilon, pP2P1N2));
        assertTrue(instance.intersects(epsilon, pP2P0P2));
        assertTrue(instance.intersects(epsilon, pP2P0P1));
        assertTrue(instance.intersects(epsilon, pP2P0P0));
        assertTrue(instance.intersects(epsilon, pP2P0N1));
        assertTrue(instance.intersects(epsilon, pP2P0N2));
        assertFalse(instance.intersects(epsilon, pP2N1P2));
        assertFalse(instance.intersects(epsilon, pP2N1P1));
        assertFalse(instance.intersects(epsilon, pP2N1P0));
        assertFalse(instance.intersects(epsilon, pP2N1N1));
        assertFalse(instance.intersects(epsilon, pP2N1N2));
        assertFalse(instance.intersects(epsilon, pP2N2P2));
        assertFalse(instance.intersects(epsilon, pP2N2P1));
        assertFalse(instance.intersects(epsilon, pP2N2P0));
        assertFalse(instance.intersects(epsilon, pP2N2N1));
        assertFalse(instance.intersects(epsilon, pP2N2N2));
        // P1
        assertFalse(instance.intersects(epsilon, pP1P2P2));
        assertFalse(instance.intersects(epsilon, pP1P2P1));
        assertFalse(instance.intersects(epsilon, pP1P2P0));
        assertFalse(instance.intersects(epsilon, pP1P2N1));
        assertFalse(instance.intersects(epsilon, pP1P2N2));
        assertFalse(instance.intersects(epsilon, pP1P1P2));
        assertFalse(instance.intersects(epsilon, pP1P1P1));
        assertFalse(instance.intersects(epsilon, pP1P1P0));
        assertFalse(instance.intersects(epsilon, pP1P1N1));
        assertFalse(instance.intersects(epsilon, pP1P1N2));
        assertTrue(instance.intersects(epsilon, pP1P0P2));
        assertTrue(instance.intersects(epsilon, pP1P0P1));
        assertTrue(instance.intersects(epsilon, pP1P0P0));
        assertTrue(instance.intersects(epsilon, pP1P0N1));
        assertTrue(instance.intersects(epsilon, pP1P0N2));
        assertFalse(instance.intersects(epsilon, pP1N1P2));
        assertFalse(instance.intersects(epsilon, pP1N1P1));
        assertFalse(instance.intersects(epsilon, pP1N1P0));
        assertFalse(instance.intersects(epsilon, pP1N1N1));
        assertFalse(instance.intersects(epsilon, pP1N1N2));
        assertFalse(instance.intersects(epsilon, pP1N2P2));
        assertFalse(instance.intersects(epsilon, pP1N2P1));
        assertFalse(instance.intersects(epsilon, pP1N2P0));
        assertFalse(instance.intersects(epsilon, pP1N2N1));
        assertFalse(instance.intersects(epsilon, pP1N2N2));
        // P0
        assertFalse(instance.intersects(epsilon, pP0P2P2));
        assertFalse(instance.intersects(epsilon, pP0P2P1));
        assertFalse(instance.intersects(epsilon, pP0P2P0));
        assertFalse(instance.intersects(epsilon, pP0P2N1));
        assertFalse(instance.intersects(epsilon, pP0P2N2));
        assertFalse(instance.intersects(epsilon, pP0P1P2));
        assertFalse(instance.intersects(epsilon, pP0P1P1));
        assertFalse(instance.intersects(epsilon, pP0P1P0));
        assertFalse(instance.intersects(epsilon, pP0P1N1));
        assertFalse(instance.intersects(epsilon, pP0P1N2));
        assertTrue(instance.intersects(epsilon, pP0P0P2));
        assertTrue(instance.intersects(epsilon, pP0P0P1));
        assertTrue(instance.intersects(epsilon, pP0P0P0));
        assertTrue(instance.intersects(epsilon, pP0P0N1));
        assertTrue(instance.intersects(epsilon, pP0P0N2));
        assertFalse(instance.intersects(epsilon, pP0N1P2));
        assertFalse(instance.intersects(epsilon, pP0N1P1));
        assertFalse(instance.intersects(epsilon, pP0N1P0));
        assertFalse(instance.intersects(epsilon, pP0N1N1));
        assertFalse(instance.intersects(epsilon, pP0N1N2));
        assertFalse(instance.intersects(epsilon, pP0N2P2));
        assertFalse(instance.intersects(epsilon, pP0N2P1));
        assertFalse(instance.intersects(epsilon, pP0N2P0));
        assertFalse(instance.intersects(epsilon, pP0N2N1));
        assertFalse(instance.intersects(epsilon, pP0N2N2));
        // N1
        assertFalse(instance.intersects(epsilon, pN1P2P2));
        assertFalse(instance.intersects(epsilon, pN1P2P1));
        assertFalse(instance.intersects(epsilon, pN1P2P0));
        assertFalse(instance.intersects(epsilon, pN1P2N1));
        assertFalse(instance.intersects(epsilon, pN1P2N2));
        assertFalse(instance.intersects(epsilon, pN1P1P2));
        assertFalse(instance.intersects(epsilon, pN1P1P1));
        assertFalse(instance.intersects(epsilon, pN1P1P0));
        assertFalse(instance.intersects(epsilon, pN1P1N1));
        assertFalse(instance.intersects(epsilon, pN1P1N2));
        assertTrue(instance.intersects(epsilon, pN1P0P2));
        assertTrue(instance.intersects(epsilon, pN1P0P1));
        assertTrue(instance.intersects(epsilon, pN1P0P0));
        assertTrue(instance.intersects(epsilon, pN1P0N1));
        assertTrue(instance.intersects(epsilon, pN1P0N2));
        assertFalse(instance.intersects(epsilon, pN1N1P2));
        assertFalse(instance.intersects(epsilon, pN1N1P1));
        assertFalse(instance.intersects(epsilon, pN1N1P0));
        assertFalse(instance.intersects(epsilon, pN1N1N1));
        assertFalse(instance.intersects(epsilon, pN1N1N2));
        assertFalse(instance.intersects(epsilon, pN1N2P2));
        assertFalse(instance.intersects(epsilon, pN1N2P1));
        assertFalse(instance.intersects(epsilon, pN1N2P0));
        assertFalse(instance.intersects(epsilon, pN1N2N1));
        assertFalse(instance.intersects(epsilon, pN1N2N2));
        // N2
        assertFalse(instance.intersects(epsilon, pN2P2P2));
        assertFalse(instance.intersects(epsilon, pN2P2P1));
        assertFalse(instance.intersects(epsilon, pN2P2P0));
        assertFalse(instance.intersects(epsilon, pN2P2N1));
        assertFalse(instance.intersects(epsilon, pN2P2N2));
        assertFalse(instance.intersects(epsilon, pN2P1P2));
        assertFalse(instance.intersects(epsilon, pN2P1P1));
        assertFalse(instance.intersects(epsilon, pN2P1P0));
        assertFalse(instance.intersects(epsilon, pN2P1N1));
        assertFalse(instance.intersects(epsilon, pN2P1N2));
        assertTrue(instance.intersects(epsilon, pN2P0P2));
        assertTrue(instance.intersects(epsilon, pN2P0P1));
        assertTrue(instance.intersects(epsilon, pN2P0P0));
        assertTrue(instance.intersects(epsilon, pN2P0N1));
        assertTrue(instance.intersects(epsilon, pN2P0N2));
        assertFalse(instance.intersects(epsilon, pN2N1P2));
        assertFalse(instance.intersects(epsilon, pN2N1P1));
        assertFalse(instance.intersects(epsilon, pN2N1P0));
        assertFalse(instance.intersects(epsilon, pN2N1N1));
        assertFalse(instance.intersects(epsilon, pN2N1N2));
        assertFalse(instance.intersects(epsilon, pN2N2P2));
        assertFalse(instance.intersects(epsilon, pN2N2P1));
        assertFalse(instance.intersects(epsilon, pN2N2P0));
        assertFalse(instance.intersects(epsilon, pN2N2N1));
        assertFalse(instance.intersects(epsilon, pN2N2N2));
        assertFalse(instance.intersects(epsilon, pN2P2P2));
        assertFalse(instance.intersects(epsilon, pN2P2P1));

        // z=0
        instance = new V3D_Plane_d(env, P0P0P0, P0P1P0, P1P0P0);
        // P2
        assertFalse(instance.intersects(epsilon, pP2P2P2));
        assertFalse(instance.intersects(epsilon, pP2P2P1));
        assertTrue(instance.intersects(epsilon, pP2P2P0));
        assertFalse(instance.intersects(epsilon, pP2P2N1));
        assertFalse(instance.intersects(epsilon, pP2P2N2));
        assertFalse(instance.intersects(epsilon, pP2P1P2));
        assertFalse(instance.intersects(epsilon, pP2P1P1));
        assertTrue(instance.intersects(epsilon, pP2P1P0));
        assertFalse(instance.intersects(epsilon, pP2P1N1));
        assertFalse(instance.intersects(epsilon, pP2P1N2));
        assertFalse(instance.intersects(epsilon, pP2P0P2));
        assertFalse(instance.intersects(epsilon, pP2P0P1));
        assertTrue(instance.intersects(epsilon, pP2P0P0));
        assertFalse(instance.intersects(epsilon, pP2P0N1));
        assertFalse(instance.intersects(epsilon, pP2P0N2));
        assertFalse(instance.intersects(epsilon, pP2N1P2));
        assertFalse(instance.intersects(epsilon, pP2N1P1));
        assertTrue(instance.intersects(epsilon, pP2N1P0));
        assertFalse(instance.intersects(epsilon, pP2N1N1));
        assertFalse(instance.intersects(epsilon, pP2N1N2));
        assertFalse(instance.intersects(epsilon, pP2N2P2));
        assertFalse(instance.intersects(epsilon, pP2N2P1));
        assertTrue(instance.intersects(epsilon, pP2N2P0));
        assertFalse(instance.intersects(epsilon, pP2N2N1));
        assertFalse(instance.intersects(epsilon, pP2N2N2));
        // P1
        assertFalse(instance.intersects(epsilon, pP1P2P2));
        assertFalse(instance.intersects(epsilon, pP1P2P1));
        assertTrue(instance.intersects(epsilon, pP1P2P0));
        assertFalse(instance.intersects(epsilon, pP1P2N1));
        assertFalse(instance.intersects(epsilon, pP1P2N2));
        assertFalse(instance.intersects(epsilon, pP1P1P2));
        assertFalse(instance.intersects(epsilon, pP1P1P1));
        assertTrue(instance.intersects(epsilon, pP1P1P0));
        assertFalse(instance.intersects(epsilon, pP1P1N1));
        assertFalse(instance.intersects(epsilon, pP1P1N2));
        assertFalse(instance.intersects(epsilon, pP1P0P2));
        assertFalse(instance.intersects(epsilon, pP1P0P1));
        assertTrue(instance.intersects(epsilon, pP1P0P0));
        assertFalse(instance.intersects(epsilon, pP1P0N1));
        assertFalse(instance.intersects(epsilon, pP1P0N2));
        assertFalse(instance.intersects(epsilon, pP1N1P2));
        assertFalse(instance.intersects(epsilon, pP1N1P1));
        assertTrue(instance.intersects(epsilon, pP1N1P0));
        assertFalse(instance.intersects(epsilon, pP1N1N1));
        assertFalse(instance.intersects(epsilon, pP1N1N2));
        assertFalse(instance.intersects(epsilon, pP1N2P2));
        assertFalse(instance.intersects(epsilon, pP1N2P1));
        assertTrue(instance.intersects(epsilon, pP1N2P0));
        assertFalse(instance.intersects(epsilon, pP1N2N1));
        assertFalse(instance.intersects(epsilon, pP1N2N2));
        // P0
        assertFalse(instance.intersects(epsilon, pP0P2P2));
        assertFalse(instance.intersects(epsilon, pP0P2P1));
        assertTrue(instance.intersects(epsilon, pP0P2P0));
        assertFalse(instance.intersects(epsilon, pP0P2N1));
        assertFalse(instance.intersects(epsilon, pP0P2N2));
        assertFalse(instance.intersects(epsilon, pP0P1P2));
        assertFalse(instance.intersects(epsilon, pP0P1P1));
        assertTrue(instance.intersects(epsilon, pP0P1P0));
        assertFalse(instance.intersects(epsilon, pP0P1N1));
        assertFalse(instance.intersects(epsilon, pP0P1N2));
        assertFalse(instance.intersects(epsilon, pP0P0P2));
        assertFalse(instance.intersects(epsilon, pP0P0P1));
        assertTrue(instance.intersects(epsilon, pP0P0P0));
        assertFalse(instance.intersects(epsilon, pP0P0N1));
        assertFalse(instance.intersects(epsilon, pP0P0N2));
        assertFalse(instance.intersects(epsilon, pP0N1P2));
        assertFalse(instance.intersects(epsilon, pP0N1P1));
        assertTrue(instance.intersects(epsilon, pP0N1P0));
        assertFalse(instance.intersects(epsilon, pP0N1N1));
        assertFalse(instance.intersects(epsilon, pP0N1N2));
        assertFalse(instance.intersects(epsilon, pP0N2P2));
        assertFalse(instance.intersects(epsilon, pP0N2P1));
        assertTrue(instance.intersects(epsilon, pP0N2P0));
        assertFalse(instance.intersects(epsilon, pP0N2N1));
        assertFalse(instance.intersects(epsilon, pP0N2N2));
        // N1
        assertFalse(instance.intersects(epsilon, pN1P2P2));
        assertFalse(instance.intersects(epsilon, pN1P2P1));
        assertTrue(instance.intersects(epsilon, pN1P2P0));
        assertFalse(instance.intersects(epsilon, pN1P2N1));
        assertFalse(instance.intersects(epsilon, pN1P2N2));
        assertFalse(instance.intersects(epsilon, pN1P1P2));
        assertFalse(instance.intersects(epsilon, pN1P1P1));
        assertTrue(instance.intersects(epsilon, pN1P1P0));
        assertFalse(instance.intersects(epsilon, pN1P1N1));
        assertFalse(instance.intersects(epsilon, pN1P1N2));
        assertFalse(instance.intersects(epsilon, pN1P0P2));
        assertFalse(instance.intersects(epsilon, pN1P0P1));
        assertTrue(instance.intersects(epsilon, pN1P0P0));
        assertFalse(instance.intersects(epsilon, pN1P0N1));
        assertFalse(instance.intersects(epsilon, pN1P0N2));
        assertFalse(instance.intersects(epsilon, pN1N1P2));
        assertFalse(instance.intersects(epsilon, pN1N1P1));
        assertTrue(instance.intersects(epsilon, pN1N1P0));
        assertFalse(instance.intersects(epsilon, pN1N1N1));
        assertFalse(instance.intersects(epsilon, pN1N1N2));
        assertFalse(instance.intersects(epsilon, pN1N2P2));
        assertFalse(instance.intersects(epsilon, pN1N2P1));
        assertTrue(instance.intersects(epsilon, pN1N2P0));
        assertFalse(instance.intersects(epsilon, pN1N2N1));
        assertFalse(instance.intersects(epsilon, pN1N2N2));
        // N2
        assertFalse(instance.intersects(epsilon, pN2P2P2));
        assertFalse(instance.intersects(epsilon, pN2P2P1));
        assertTrue(instance.intersects(epsilon, pN2P2P0));
        assertFalse(instance.intersects(epsilon, pN2P2N1));
        assertFalse(instance.intersects(epsilon, pN2P2N2));
        assertFalse(instance.intersects(epsilon, pN2P1P2));
        assertFalse(instance.intersects(epsilon, pN2P1P1));
        assertTrue(instance.intersects(epsilon, pN2P1P0));
        assertFalse(instance.intersects(epsilon, pN2P1N1));
        assertFalse(instance.intersects(epsilon, pN2P1N2));
        assertFalse(instance.intersects(epsilon, pN2P0P2));
        assertFalse(instance.intersects(epsilon, pN2P0P1));
        assertTrue(instance.intersects(epsilon, pN2P0P0));
        assertFalse(instance.intersects(epsilon, pN2P0N1));
        assertFalse(instance.intersects(epsilon, pN2P0N2));
        assertFalse(instance.intersects(epsilon, pN2N1P2));
        assertFalse(instance.intersects(epsilon, pN2N1P1));
        assertTrue(instance.intersects(epsilon, pN2N1P0));
        assertFalse(instance.intersects(epsilon, pN2N1N1));
        assertFalse(instance.intersects(epsilon, pN2N1N2));
        assertFalse(instance.intersects(epsilon, pN2N2P2));
        assertFalse(instance.intersects(epsilon, pN2N2P1));
        assertTrue(instance.intersects(epsilon, pN2N2P0));
        assertFalse(instance.intersects(epsilon, pN2N2N1));
        assertFalse(instance.intersects(epsilon, pN2N2N2));

        // x=y
        instance = new V3D_Plane_d(env, P0P0P0, P1P1P0, P0P0P1);
        // P2
        assertTrue(instance.intersects(epsilon, pP2P2P2));
        assertTrue(instance.intersects(epsilon, pP2P2P1));
        assertTrue(instance.intersects(epsilon, pP2P2P0));
        assertTrue(instance.intersects(epsilon, pP2P2N1));
        assertTrue(instance.intersects(epsilon, pP2P2N2));
        assertFalse(instance.intersects(epsilon, pP2P1P2));
        assertFalse(instance.intersects(epsilon, pP2P1P1));
        assertFalse(instance.intersects(epsilon, pP2P1P0));
        assertFalse(instance.intersects(epsilon, pP2P1N1));
        assertFalse(instance.intersects(epsilon, pP2P1N2));
        assertFalse(instance.intersects(epsilon, pP2P0P2));
        assertFalse(instance.intersects(epsilon, pP2P0P1));
        assertFalse(instance.intersects(epsilon, pP2P0P0));
        assertFalse(instance.intersects(epsilon, pP2P0N1));
        assertFalse(instance.intersects(epsilon, pP2P0N2));
        assertFalse(instance.intersects(epsilon, pP2N1P2));
        assertFalse(instance.intersects(epsilon, pP2N1P1));
        assertFalse(instance.intersects(epsilon, pP2N1P0));
        assertFalse(instance.intersects(epsilon, pP2N1N1));
        assertFalse(instance.intersects(epsilon, pP2N1N2));
        assertFalse(instance.intersects(epsilon, pP2N2P2));
        assertFalse(instance.intersects(epsilon, pP2N2P1));
        assertFalse(instance.intersects(epsilon, pP2N2P0));
        assertFalse(instance.intersects(epsilon, pP2N2N1));
        assertFalse(instance.intersects(epsilon, pP2N2N2));
        // P1
        assertFalse(instance.intersects(epsilon, pP1P2P2));
        assertFalse(instance.intersects(epsilon, pP1P2P1));
        assertFalse(instance.intersects(epsilon, pP1P2P0));
        assertFalse(instance.intersects(epsilon, pP1P2N1));
        assertFalse(instance.intersects(epsilon, pP1P2N2));
        assertTrue(instance.intersects(epsilon, pP1P1P2));
        assertTrue(instance.intersects(epsilon, pP1P1P1));
        assertTrue(instance.intersects(epsilon, pP1P1P0));
        assertTrue(instance.intersects(epsilon, pP1P1N1));
        assertTrue(instance.intersects(epsilon, pP1P1N2));
        assertFalse(instance.intersects(epsilon, pP1P0P2));
        assertFalse(instance.intersects(epsilon, pP1P0P1));
        assertFalse(instance.intersects(epsilon, pP1P0P0));
        assertFalse(instance.intersects(epsilon, pP1P0N1));
        assertFalse(instance.intersects(epsilon, pP1P0N2));
        assertFalse(instance.intersects(epsilon, pP1N1P2));
        assertFalse(instance.intersects(epsilon, pP1N1P1));
        assertFalse(instance.intersects(epsilon, pP1N1P0));
        assertFalse(instance.intersects(epsilon, pP1N1N1));
        assertFalse(instance.intersects(epsilon, pP1N1N2));
        assertFalse(instance.intersects(epsilon, pP1N2P2));
        assertFalse(instance.intersects(epsilon, pP1N2P1));
        assertFalse(instance.intersects(epsilon, pP1N2P0));
        assertFalse(instance.intersects(epsilon, pP1N2N1));
        assertFalse(instance.intersects(epsilon, pP1N2N2));
        // P0
        assertFalse(instance.intersects(epsilon, pP0P2P2));
        assertFalse(instance.intersects(epsilon, pP0P2P1));
        assertFalse(instance.intersects(epsilon, pP0P2P0));
        assertFalse(instance.intersects(epsilon, pP0P2N1));
        assertFalse(instance.intersects(epsilon, pP0P2N2));
        assertFalse(instance.intersects(epsilon, pP0P1P2));
        assertFalse(instance.intersects(epsilon, pP0P1P1));
        assertFalse(instance.intersects(epsilon, pP0P1P0));
        assertFalse(instance.intersects(epsilon, pP0P1N1));
        assertFalse(instance.intersects(epsilon, pP0P1N2));
        assertTrue(instance.intersects(epsilon, pP0P0P2));
        assertTrue(instance.intersects(epsilon, pP0P0P1));
        assertTrue(instance.intersects(epsilon, pP0P0P0));
        assertTrue(instance.intersects(epsilon, pP0P0N1));
        assertTrue(instance.intersects(epsilon, pP0P0N2));
        assertFalse(instance.intersects(epsilon, pP0N1P2));
        assertFalse(instance.intersects(epsilon, pP0N1P1));
        assertFalse(instance.intersects(epsilon, pP0N1P0));
        assertFalse(instance.intersects(epsilon, pP0N1N1));
        assertFalse(instance.intersects(epsilon, pP0N1N2));
        assertFalse(instance.intersects(epsilon, pP0N2P2));
        assertFalse(instance.intersects(epsilon, pP0N2P1));
        assertFalse(instance.intersects(epsilon, pP0N2P0));
        assertFalse(instance.intersects(epsilon, pP0N2N1));
        assertFalse(instance.intersects(epsilon, pP0N2N2));
        // N1
        assertFalse(instance.intersects(epsilon, pN1P2P2));
        assertFalse(instance.intersects(epsilon, pN1P2P1));
        assertFalse(instance.intersects(epsilon, pN1P2P0));
        assertFalse(instance.intersects(epsilon, pN1P2N1));
        assertFalse(instance.intersects(epsilon, pN1P2N2));
        assertFalse(instance.intersects(epsilon, pN1P1P2));
        assertFalse(instance.intersects(epsilon, pN1P1P1));
        assertFalse(instance.intersects(epsilon, pN1P1P0));
        assertFalse(instance.intersects(epsilon, pN1P1N1));
        assertFalse(instance.intersects(epsilon, pN1P1N2));
        assertFalse(instance.intersects(epsilon, pN1P0P2));
        assertFalse(instance.intersects(epsilon, pN1P0P1));
        assertFalse(instance.intersects(epsilon, pN1P0P0));
        assertFalse(instance.intersects(epsilon, pN1P0N1));
        assertFalse(instance.intersects(epsilon, pN1P0N2));
        assertTrue(instance.intersects(epsilon, pN1N1P2));
        assertTrue(instance.intersects(epsilon, pN1N1P1));
        assertTrue(instance.intersects(epsilon, pN1N1P0));
        assertTrue(instance.intersects(epsilon, pN1N1N1));
        assertTrue(instance.intersects(epsilon, pN1N1N2));
        assertFalse(instance.intersects(epsilon, pN1N2P2));
        assertFalse(instance.intersects(epsilon, pN1N2P1));
        assertFalse(instance.intersects(epsilon, pN1N2P0));
        assertFalse(instance.intersects(epsilon, pN1N2N1));
        assertFalse(instance.intersects(epsilon, pN1N2N2));
        // N2
        assertFalse(instance.intersects(epsilon, pN2P2P2));
        assertFalse(instance.intersects(epsilon, pN2P2P1));
        assertFalse(instance.intersects(epsilon, pN2P2P0));
        assertFalse(instance.intersects(epsilon, pN2P2N1));
        assertFalse(instance.intersects(epsilon, pN2P2N2));
        assertFalse(instance.intersects(epsilon, pN2P1P2));
        assertFalse(instance.intersects(epsilon, pN2P1P1));
        assertFalse(instance.intersects(epsilon, pN2P1P0));
        assertFalse(instance.intersects(epsilon, pN2P1N1));
        assertFalse(instance.intersects(epsilon, pN2P1N2));
        assertFalse(instance.intersects(epsilon, pN2P0P2));
        assertFalse(instance.intersects(epsilon, pN2P0P1));
        assertFalse(instance.intersects(epsilon, pN2P0P0));
        assertFalse(instance.intersects(epsilon, pN2P0N1));
        assertFalse(instance.intersects(epsilon, pN2P0N2));
        assertFalse(instance.intersects(epsilon, pN2N1P2));
        assertFalse(instance.intersects(epsilon, pN2N1P1));
        assertFalse(instance.intersects(epsilon, pN2N1P0));
        assertFalse(instance.intersects(epsilon, pN2N1N1));
        assertFalse(instance.intersects(epsilon, pN2N1N2));
        assertTrue(instance.intersects(epsilon, pN2N2P2));
        assertTrue(instance.intersects(epsilon, pN2N2P1));
        assertTrue(instance.intersects(epsilon, pN2N2P0));
        assertTrue(instance.intersects(epsilon, pN2N2N1));
        assertTrue(instance.intersects(epsilon, pN2N2N2));

        // x=-y
        instance = new V3D_Plane_d(env, P0P0P0, N1P1P0, P0P0P1);
        // P2
        assertFalse(instance.intersects(epsilon, pP2P2P2));
        assertFalse(instance.intersects(epsilon, pP2P2P1));
        assertFalse(instance.intersects(epsilon, pP2P2P0));
        assertFalse(instance.intersects(epsilon, pP2P2N1));
        assertFalse(instance.intersects(epsilon, pP2P2N2));
        assertFalse(instance.intersects(epsilon, pP2P1P2));
        assertFalse(instance.intersects(epsilon, pP2P1P1));
        assertFalse(instance.intersects(epsilon, pP2P1P0));
        assertFalse(instance.intersects(epsilon, pP2P1N1));
        assertFalse(instance.intersects(epsilon, pP2P1N2));
        assertFalse(instance.intersects(epsilon, pP2P0P2));
        assertFalse(instance.intersects(epsilon, pP2P0P1));
        assertFalse(instance.intersects(epsilon, pP2P0P0));
        assertFalse(instance.intersects(epsilon, pP2P0N1));
        assertFalse(instance.intersects(epsilon, pP2P0N2));
        assertFalse(instance.intersects(epsilon, pP2N1P2));
        assertFalse(instance.intersects(epsilon, pP2N1P1));
        assertFalse(instance.intersects(epsilon, pP2N1P0));
        assertFalse(instance.intersects(epsilon, pP2N1N1));
        assertFalse(instance.intersects(epsilon, pP2N1N2));
        assertTrue(instance.intersects(epsilon, pP2N2P2));
        assertTrue(instance.intersects(epsilon, pP2N2P1));
        assertTrue(instance.intersects(epsilon, pP2N2P0));
        assertTrue(instance.intersects(epsilon, pP2N2N1));
        assertTrue(instance.intersects(epsilon, pP2N2N2));
        // P1
        assertFalse(instance.intersects(epsilon, pP1P2P2));
        assertFalse(instance.intersects(epsilon, pP1P2P1));
        assertFalse(instance.intersects(epsilon, pP1P2P0));
        assertFalse(instance.intersects(epsilon, pP1P2N1));
        assertFalse(instance.intersects(epsilon, pP1P2N2));
        assertFalse(instance.intersects(epsilon, pP1P1P2));
        assertFalse(instance.intersects(epsilon, pP1P1P1));
        assertFalse(instance.intersects(epsilon, pP1P1P0));
        assertFalse(instance.intersects(epsilon, pP1P1N1));
        assertFalse(instance.intersects(epsilon, pP1P1N2));
        assertFalse(instance.intersects(epsilon, pP1P0P2));
        assertFalse(instance.intersects(epsilon, pP1P0P1));
        assertFalse(instance.intersects(epsilon, pP1P0P0));
        assertFalse(instance.intersects(epsilon, pP1P0N1));
        assertFalse(instance.intersects(epsilon, pP1P0N2));
        assertTrue(instance.intersects(epsilon, pP1N1P2));
        assertTrue(instance.intersects(epsilon, pP1N1P1));
        assertTrue(instance.intersects(epsilon, pP1N1P0));
        assertTrue(instance.intersects(epsilon, pP1N1N1));
        assertTrue(instance.intersects(epsilon, pP1N1N2));
        assertFalse(instance.intersects(epsilon, pP1N2P2));
        assertFalse(instance.intersects(epsilon, pP1N2P1));
        assertFalse(instance.intersects(epsilon, pP1N2P0));
        assertFalse(instance.intersects(epsilon, pP1N2N1));
        assertFalse(instance.intersects(epsilon, pP1N2N2));
        // P0
        assertFalse(instance.intersects(epsilon, pP0P2P2));
        assertFalse(instance.intersects(epsilon, pP0P2P1));
        assertFalse(instance.intersects(epsilon, pP0P2P0));
        assertFalse(instance.intersects(epsilon, pP0P2N1));
        assertFalse(instance.intersects(epsilon, pP0P2N2));
        assertFalse(instance.intersects(epsilon, pP0P1P2));
        assertFalse(instance.intersects(epsilon, pP0P1P1));
        assertFalse(instance.intersects(epsilon, pP0P1P0));
        assertFalse(instance.intersects(epsilon, pP0P1N1));
        assertFalse(instance.intersects(epsilon, pP0P1N2));
        assertTrue(instance.intersects(epsilon, pP0P0P2));
        assertTrue(instance.intersects(epsilon, pP0P0P1));
        assertTrue(instance.intersects(epsilon, pP0P0P0));
        assertTrue(instance.intersects(epsilon, pP0P0N1));
        assertTrue(instance.intersects(epsilon, pP0P0N2));
        assertFalse(instance.intersects(epsilon, pP0N1P2));
        assertFalse(instance.intersects(epsilon, pP0N1P1));
        assertFalse(instance.intersects(epsilon, pP0N1P0));
        assertFalse(instance.intersects(epsilon, pP0N1N1));
        assertFalse(instance.intersects(epsilon, pP0N1N2));
        assertFalse(instance.intersects(epsilon, pP0N2P2));
        assertFalse(instance.intersects(epsilon, pP0N2P1));
        assertFalse(instance.intersects(epsilon, pP0N2P0));
        assertFalse(instance.intersects(epsilon, pP0N2N1));
        assertFalse(instance.intersects(epsilon, pP0N2N2));
        // N1
        assertFalse(instance.intersects(epsilon, pN1P2P2));
        assertFalse(instance.intersects(epsilon, pN1P2P1));
        assertFalse(instance.intersects(epsilon, pN1P2P0));
        assertFalse(instance.intersects(epsilon, pN1P2N1));
        assertFalse(instance.intersects(epsilon, pN1P2N2));
        assertTrue(instance.intersects(epsilon, pN1P1P2));
        assertTrue(instance.intersects(epsilon, pN1P1P1));
        assertTrue(instance.intersects(epsilon, pN1P1P0));
        assertTrue(instance.intersects(epsilon, pN1P1N1));
        assertTrue(instance.intersects(epsilon, pN1P1N2));
        assertFalse(instance.intersects(epsilon, pN1P0P2));
        assertFalse(instance.intersects(epsilon, pN1P0P1));
        assertFalse(instance.intersects(epsilon, pN1P0P0));
        assertFalse(instance.intersects(epsilon, pN1P0N1));
        assertFalse(instance.intersects(epsilon, pN1P0N2));
        assertFalse(instance.intersects(epsilon, pN1N1P2));
        assertFalse(instance.intersects(epsilon, pN1N1P1));
        assertFalse(instance.intersects(epsilon, pN1N1P0));
        assertFalse(instance.intersects(epsilon, pN1N1N1));
        assertFalse(instance.intersects(epsilon, pN1N1N2));
        assertFalse(instance.intersects(epsilon, pN1N2P2));
        assertFalse(instance.intersects(epsilon, pN1N2P1));
        assertFalse(instance.intersects(epsilon, pN1N2P0));
        assertFalse(instance.intersects(epsilon, pN1N2N1));
        assertFalse(instance.intersects(epsilon, pN1N2N2));
        // N2
        assertTrue(instance.intersects(epsilon, pN2P2P2));
        assertTrue(instance.intersects(epsilon, pN2P2P1));
        assertTrue(instance.intersects(epsilon, pN2P2P0));
        assertTrue(instance.intersects(epsilon, pN2P2N1));
        assertTrue(instance.intersects(epsilon, pN2P2N2));
        assertFalse(instance.intersects(epsilon, pN2P1P2));
        assertFalse(instance.intersects(epsilon, pN2P1P1));
        assertFalse(instance.intersects(epsilon, pN2P1P0));
        assertFalse(instance.intersects(epsilon, pN2P1N1));
        assertFalse(instance.intersects(epsilon, pN2P1N2));
        assertFalse(instance.intersects(epsilon, pN2P0P2));
        assertFalse(instance.intersects(epsilon, pN2P0P1));
        assertFalse(instance.intersects(epsilon, pN2P0P0));
        assertFalse(instance.intersects(epsilon, pN2P0N1));
        assertFalse(instance.intersects(epsilon, pN2P0N2));
        assertFalse(instance.intersects(epsilon, pN2N1P2));
        assertFalse(instance.intersects(epsilon, pN2N1P1));
        assertFalse(instance.intersects(epsilon, pN2N1P0));
        assertFalse(instance.intersects(epsilon, pN2N1N1));
        assertFalse(instance.intersects(epsilon, pN2N1N2));
        assertFalse(instance.intersects(epsilon, pN2N2P2));
        assertFalse(instance.intersects(epsilon, pN2N2P1));
        assertFalse(instance.intersects(epsilon, pN2N2P0));
        assertFalse(instance.intersects(epsilon, pN2N2N1));
        assertFalse(instance.intersects(epsilon, pN2N2N2));

        // x=z
        instance = new V3D_Plane_d(env, P0P0P0, P0P1P0, P1P0P1);
        // P2
        assertTrue(instance.intersects(epsilon, pP2P2P2));
        assertFalse(instance.intersects(epsilon, pP2P2P1));
        assertFalse(instance.intersects(epsilon, pP2P2P0));
        assertFalse(instance.intersects(epsilon, pP2P2N1));
        assertFalse(instance.intersects(epsilon, pP2P2N2));
        assertTrue(instance.intersects(epsilon, pP2P1P2));
        assertFalse(instance.intersects(epsilon, pP2P1P1));
        assertFalse(instance.intersects(epsilon, pP2P1P0));
        assertFalse(instance.intersects(epsilon, pP2P1N1));
        assertFalse(instance.intersects(epsilon, pP2P1N2));
        assertTrue(instance.intersects(epsilon, pP2P0P2));
        assertFalse(instance.intersects(epsilon, pP2P0P1));
        assertFalse(instance.intersects(epsilon, pP2P0P0));
        assertFalse(instance.intersects(epsilon, pP2P0N1));
        assertFalse(instance.intersects(epsilon, pP2P0N2));
        assertTrue(instance.intersects(epsilon, pP2N1P2));
        assertFalse(instance.intersects(epsilon, pP2N1P1));
        assertFalse(instance.intersects(epsilon, pP2N1P0));
        assertFalse(instance.intersects(epsilon, pP2N1N1));
        assertFalse(instance.intersects(epsilon, pP2N1N2));
        assertTrue(instance.intersects(epsilon, pP2N2P2));
        assertFalse(instance.intersects(epsilon, pP2N2P1));
        assertFalse(instance.intersects(epsilon, pP2N2P0));
        assertFalse(instance.intersects(epsilon, pP2N2N1));
        assertFalse(instance.intersects(epsilon, pP2N2N2));
        // P1
        assertFalse(instance.intersects(epsilon, pP1P2P2));
        assertTrue(instance.intersects(epsilon, pP1P2P1));
        assertFalse(instance.intersects(epsilon, pP1P2P0));
        assertFalse(instance.intersects(epsilon, pP1P2N1));
        assertFalse(instance.intersects(epsilon, pP1P2N2));
        assertFalse(instance.intersects(epsilon, pP1P1P2));
        assertTrue(instance.intersects(epsilon, pP1P1P1));
        assertFalse(instance.intersects(epsilon, pP1P1P0));
        assertFalse(instance.intersects(epsilon, pP1P1N1));
        assertFalse(instance.intersects(epsilon, pP1P1N2));
        assertFalse(instance.intersects(epsilon, pP1P0P2));
        assertTrue(instance.intersects(epsilon, pP1P0P1));
        assertFalse(instance.intersects(epsilon, pP1P0P0));
        assertFalse(instance.intersects(epsilon, pP1P0N1));
        assertFalse(instance.intersects(epsilon, pP1P0N2));
        assertFalse(instance.intersects(epsilon, pP1N1P2));
        assertTrue(instance.intersects(epsilon, pP1N1P1));
        assertFalse(instance.intersects(epsilon, pP1N1P0));
        assertFalse(instance.intersects(epsilon, pP1N1N1));
        assertFalse(instance.intersects(epsilon, pP1N1N2));
        assertFalse(instance.intersects(epsilon, pP1N2P2));
        assertTrue(instance.intersects(epsilon, pP1N2P1));
        assertFalse(instance.intersects(epsilon, pP1N2P0));
        assertFalse(instance.intersects(epsilon, pP1N2N1));
        assertFalse(instance.intersects(epsilon, pP1N2N2));
        // P0
        assertFalse(instance.intersects(epsilon, pP0P2P2));
        assertFalse(instance.intersects(epsilon, pP0P2P1));
        assertTrue(instance.intersects(epsilon, pP0P2P0));
        assertFalse(instance.intersects(epsilon, pP0P2N1));
        assertFalse(instance.intersects(epsilon, pP0P2N2));
        assertFalse(instance.intersects(epsilon, pP0P1P2));
        assertFalse(instance.intersects(epsilon, pP0P1P1));
        assertTrue(instance.intersects(epsilon, pP0P1P0));
        assertFalse(instance.intersects(epsilon, pP0P1N1));
        assertFalse(instance.intersects(epsilon, pP0P1N2));
        assertFalse(instance.intersects(epsilon, pP0P0P2));
        assertFalse(instance.intersects(epsilon, pP0P0P1));
        assertTrue(instance.intersects(epsilon, pP0P0P0));
        assertFalse(instance.intersects(epsilon, pP0P0N1));
        assertFalse(instance.intersects(epsilon, pP0P0N2));
        assertFalse(instance.intersects(epsilon, pP0N1P2));
        assertFalse(instance.intersects(epsilon, pP0N1P1));
        assertTrue(instance.intersects(epsilon, pP0N1P0));
        assertFalse(instance.intersects(epsilon, pP0N1N1));
        assertFalse(instance.intersects(epsilon, pP0N1N2));
        assertFalse(instance.intersects(epsilon, pP0N2P2));
        assertFalse(instance.intersects(epsilon, pP0N2P1));
        assertTrue(instance.intersects(epsilon, pP0N2P0));
        assertFalse(instance.intersects(epsilon, pP0N2N1));
        assertFalse(instance.intersects(epsilon, pP0N2N2));
        // N1
        assertFalse(instance.intersects(epsilon, pN1P2P2));
        assertFalse(instance.intersects(epsilon, pN1P2P1));
        assertFalse(instance.intersects(epsilon, pN1P2P0));
        assertTrue(instance.intersects(epsilon, pN1P2N1));
        assertFalse(instance.intersects(epsilon, pN1P2N2));
        assertFalse(instance.intersects(epsilon, pN1P1P2));
        assertFalse(instance.intersects(epsilon, pN1P1P1));
        assertFalse(instance.intersects(epsilon, pN1P1P0));
        assertTrue(instance.intersects(epsilon, pN1P1N1));
        assertFalse(instance.intersects(epsilon, pN1P1N2));
        assertFalse(instance.intersects(epsilon, pN1P0P2));
        assertFalse(instance.intersects(epsilon, pN1P0P1));
        assertFalse(instance.intersects(epsilon, pN1P0P0));
        assertTrue(instance.intersects(epsilon, pN1P0N1));
        assertFalse(instance.intersects(epsilon, pN1P0N2));
        assertFalse(instance.intersects(epsilon, pN1N1P2));
        assertFalse(instance.intersects(epsilon, pN1N1P1));
        assertFalse(instance.intersects(epsilon, pN1N1P0));
        assertTrue(instance.intersects(epsilon, pN1N1N1));
        assertFalse(instance.intersects(epsilon, pN1N1N2));
        assertFalse(instance.intersects(epsilon, pN1N2P2));
        assertFalse(instance.intersects(epsilon, pN1N2P1));
        assertFalse(instance.intersects(epsilon, pN1N2P0));
        assertTrue(instance.intersects(epsilon, pN1N2N1));
        assertFalse(instance.intersects(epsilon, pN1N2N2));
        // N2
        assertFalse(instance.intersects(epsilon, pN2P2P2));
        assertFalse(instance.intersects(epsilon, pN2P2P1));
        assertFalse(instance.intersects(epsilon, pN2P2P0));
        assertFalse(instance.intersects(epsilon, pN2P2N1));
        assertTrue(instance.intersects(epsilon, pN2P2N2));
        assertFalse(instance.intersects(epsilon, pN2P1P2));
        assertFalse(instance.intersects(epsilon, pN2P1P1));
        assertFalse(instance.intersects(epsilon, pN2P1P0));
        assertFalse(instance.intersects(epsilon, pN2P1N1));
        assertTrue(instance.intersects(epsilon, pN2P1N2));
        assertFalse(instance.intersects(epsilon, pN2P0P2));
        assertFalse(instance.intersects(epsilon, pN2P0P1));
        assertFalse(instance.intersects(epsilon, pN2P0P0));
        assertFalse(instance.intersects(epsilon, pN2P0N1));
        assertTrue(instance.intersects(epsilon, pN2P0N2));
        assertFalse(instance.intersects(epsilon, pN2N1P2));
        assertFalse(instance.intersects(epsilon, pN2N1P1));
        assertFalse(instance.intersects(epsilon, pN2N1P0));
        assertFalse(instance.intersects(epsilon, pN2N1N1));
        assertTrue(instance.intersects(epsilon, pN2N1N2));
        assertFalse(instance.intersects(epsilon, pN2N2P2));
        assertFalse(instance.intersects(epsilon, pN2N2P1));
        assertFalse(instance.intersects(epsilon, pN2N2P0));
        assertFalse(instance.intersects(epsilon, pN2N2N1));
        assertTrue(instance.intersects(epsilon, pN2N2N2));

        // x=-z
        instance = new V3D_Plane_d(env, P0P0P0, P0P1P0, N1P0P1);
        // P2
        assertFalse(instance.intersects(epsilon, pP2P2P2));
        assertFalse(instance.intersects(epsilon, pP2P2P1));
        assertFalse(instance.intersects(epsilon, pP2P2P0));
        assertFalse(instance.intersects(epsilon, pP2P2N1));
        assertTrue(instance.intersects(epsilon, pP2P2N2));
        assertFalse(instance.intersects(epsilon, pP2P1P2));
        assertFalse(instance.intersects(epsilon, pP2P1P1));
        assertFalse(instance.intersects(epsilon, pP2P1P0));
        assertFalse(instance.intersects(epsilon, pP2P1N1));
        assertTrue(instance.intersects(epsilon, pP2P1N2));
        assertFalse(instance.intersects(epsilon, pP2P0P2));
        assertFalse(instance.intersects(epsilon, pP2P0P1));
        assertFalse(instance.intersects(epsilon, pP2P0P0));
        assertFalse(instance.intersects(epsilon, pP2P0N1));
        assertTrue(instance.intersects(epsilon, pP2P0N2));
        assertFalse(instance.intersects(epsilon, pP2N1P2));
        assertFalse(instance.intersects(epsilon, pP2N1P1));
        assertFalse(instance.intersects(epsilon, pP2N1P0));
        assertFalse(instance.intersects(epsilon, pP2N1N1));
        assertTrue(instance.intersects(epsilon, pP2N1N2));
        assertFalse(instance.intersects(epsilon, pP2N2P2));
        assertFalse(instance.intersects(epsilon, pP2N2P1));
        assertFalse(instance.intersects(epsilon, pP2N2P0));
        assertFalse(instance.intersects(epsilon, pP2N2N1));
        assertTrue(instance.intersects(epsilon, pP2N2N2));
        // P1
        assertFalse(instance.intersects(epsilon, pP1P2P2));
        assertFalse(instance.intersects(epsilon, pP1P2P1));
        assertFalse(instance.intersects(epsilon, pP1P2P0));
        assertTrue(instance.intersects(epsilon, pP1P2N1));
        assertFalse(instance.intersects(epsilon, pP1P2N2));
        assertFalse(instance.intersects(epsilon, pP1P1P2));
        assertFalse(instance.intersects(epsilon, pP1P1P1));
        assertFalse(instance.intersects(epsilon, pP1P1P0));
        assertTrue(instance.intersects(epsilon, pP1P1N1));
        assertFalse(instance.intersects(epsilon, pP1P1N2));
        assertFalse(instance.intersects(epsilon, pP1P0P2));
        assertFalse(instance.intersects(epsilon, pP1P0P1));
        assertFalse(instance.intersects(epsilon, pP1P0P0));
        assertTrue(instance.intersects(epsilon, pP1P0N1));
        assertFalse(instance.intersects(epsilon, pP1P0N2));
        assertFalse(instance.intersects(epsilon, pP1N1P2));
        assertFalse(instance.intersects(epsilon, pP1N1P1));
        assertFalse(instance.intersects(epsilon, pP1N1P0));
        assertTrue(instance.intersects(epsilon, pP1N1N1));
        assertFalse(instance.intersects(epsilon, pP1N1N2));
        assertFalse(instance.intersects(epsilon, pP1N2P2));
        assertFalse(instance.intersects(epsilon, pP1N2P1));
        assertFalse(instance.intersects(epsilon, pP1N2P0));
        assertTrue(instance.intersects(epsilon, pP1N2N1));
        assertFalse(instance.intersects(epsilon, pP1N2N2));
        // P0
        assertFalse(instance.intersects(epsilon, pP0P2P2));
        assertFalse(instance.intersects(epsilon, pP0P2P1));
        assertTrue(instance.intersects(epsilon, pP0P2P0));
        assertFalse(instance.intersects(epsilon, pP0P2N1));
        assertFalse(instance.intersects(epsilon, pP0P2N2));
        assertFalse(instance.intersects(epsilon, pP0P1P2));
        assertFalse(instance.intersects(epsilon, pP0P1P1));
        assertTrue(instance.intersects(epsilon, pP0P1P0));
        assertFalse(instance.intersects(epsilon, pP0P1N1));
        assertFalse(instance.intersects(epsilon, pP0P1N2));
        assertFalse(instance.intersects(epsilon, pP0P0P2));
        assertFalse(instance.intersects(epsilon, pP0P0P1));
        assertTrue(instance.intersects(epsilon, pP0P0P0));
        assertFalse(instance.intersects(epsilon, pP0P0N1));
        assertFalse(instance.intersects(epsilon, pP0P0N2));
        assertFalse(instance.intersects(epsilon, pP0N1P2));
        assertFalse(instance.intersects(epsilon, pP0N1P1));
        assertTrue(instance.intersects(epsilon, pP0N1P0));
        assertFalse(instance.intersects(epsilon, pP0N1N1));
        assertFalse(instance.intersects(epsilon, pP0N1N2));
        assertFalse(instance.intersects(epsilon, pP0N2P2));
        assertFalse(instance.intersects(epsilon, pP0N2P1));
        assertTrue(instance.intersects(epsilon, pP0N2P0));
        assertFalse(instance.intersects(epsilon, pP0N2N1));
        assertFalse(instance.intersects(epsilon, pP0N2N2));
        // N1
        assertFalse(instance.intersects(epsilon, pN1P2P2));
        assertTrue(instance.intersects(epsilon, pN1P2P1));
        assertFalse(instance.intersects(epsilon, pN1P2P0));
        assertFalse(instance.intersects(epsilon, pN1P2N1));
        assertFalse(instance.intersects(epsilon, pN1P2N2));
        assertFalse(instance.intersects(epsilon, pN1P1P2));
        assertTrue(instance.intersects(epsilon, pN1P1P1));
        assertFalse(instance.intersects(epsilon, pN1P1P0));
        assertFalse(instance.intersects(epsilon, pN1P1N1));
        assertFalse(instance.intersects(epsilon, pN1P1N2));
        assertFalse(instance.intersects(epsilon, pN1P0P2));
        assertTrue(instance.intersects(epsilon, pN1P0P1));
        assertFalse(instance.intersects(epsilon, pN1P0P0));
        assertFalse(instance.intersects(epsilon, pN1P0N1));
        assertFalse(instance.intersects(epsilon, pN1P0N2));
        assertFalse(instance.intersects(epsilon, pN1N1P2));
        assertTrue(instance.intersects(epsilon, pN1N1P1));
        assertFalse(instance.intersects(epsilon, pN1N1P0));
        assertFalse(instance.intersects(epsilon, pN1N1N1));
        assertFalse(instance.intersects(epsilon, pN1N1N2));
        assertFalse(instance.intersects(epsilon, pN1N2P2));
        assertTrue(instance.intersects(epsilon, pN1N2P1));
        assertFalse(instance.intersects(epsilon, pN1N2P0));
        assertFalse(instance.intersects(epsilon, pN1N2N1));
        assertFalse(instance.intersects(epsilon, pN1N2N2));
        // N2
        assertTrue(instance.intersects(epsilon, pN2P2P2));
        assertFalse(instance.intersects(epsilon, pN2P2P1));
        assertFalse(instance.intersects(epsilon, pN2P2P0));
        assertFalse(instance.intersects(epsilon, pN2P2N1));
        assertFalse(instance.intersects(epsilon, pN2P2N2));
        assertTrue(instance.intersects(epsilon, pN2P1P2));
        assertFalse(instance.intersects(epsilon, pN2P1P1));
        assertFalse(instance.intersects(epsilon, pN2P1P0));
        assertFalse(instance.intersects(epsilon, pN2P1N1));
        assertFalse(instance.intersects(epsilon, pN2P1N2));
        assertTrue(instance.intersects(epsilon, pN2P0P2));
        assertFalse(instance.intersects(epsilon, pN2P0P1));
        assertFalse(instance.intersects(epsilon, pN2P0P0));
        assertFalse(instance.intersects(epsilon, pN2P0N1));
        assertFalse(instance.intersects(epsilon, pN2P0N2));
        assertTrue(instance.intersects(epsilon, pN2N1P2));
        assertFalse(instance.intersects(epsilon, pN2N1P1));
        assertFalse(instance.intersects(epsilon, pN2N1P0));
        assertFalse(instance.intersects(epsilon, pN2N1N1));
        assertFalse(instance.intersects(epsilon, pN2N1N2));
        assertTrue(instance.intersects(epsilon, pN2N2P2));
        assertFalse(instance.intersects(epsilon, pN2N2P1));
        assertFalse(instance.intersects(epsilon, pN2N2P0));
        assertFalse(instance.intersects(epsilon, pN2N2N1));
        assertFalse(instance.intersects(epsilon, pN2N2N2));

        // y=z
        instance = new V3D_Plane_d(env, P1P0P0, P0P0P0, P0P1P1);
        // P2
        assertTrue(instance.intersects(epsilon, pP2P2P2));
        assertFalse(instance.intersects(epsilon, pP2P2P1));
        assertFalse(instance.intersects(epsilon, pP2P2P0));
        assertFalse(instance.intersects(epsilon, pP2P2N1));
        assertFalse(instance.intersects(epsilon, pP2P2N2));
        assertFalse(instance.intersects(epsilon, pP2P1P2));
        assertTrue(instance.intersects(epsilon, pP2P1P1));
        assertFalse(instance.intersects(epsilon, pP2P1P0));
        assertFalse(instance.intersects(epsilon, pP2P1N1));
        assertFalse(instance.intersects(epsilon, pP2P1N2));
        assertFalse(instance.intersects(epsilon, pP2P0P2));
        assertFalse(instance.intersects(epsilon, pP2P0P1));
        assertTrue(instance.intersects(epsilon, pP2P0P0));
        assertFalse(instance.intersects(epsilon, pP2P0N1));
        assertFalse(instance.intersects(epsilon, pP2P0N2));
        assertFalse(instance.intersects(epsilon, pP2N1P2));
        assertFalse(instance.intersects(epsilon, pP2N1P1));
        assertFalse(instance.intersects(epsilon, pP2N1P0));
        assertTrue(instance.intersects(epsilon, pP2N1N1));
        assertFalse(instance.intersects(epsilon, pP2N1N2));
        assertFalse(instance.intersects(epsilon, pP2N2P2));
        assertFalse(instance.intersects(epsilon, pP2N2P1));
        assertFalse(instance.intersects(epsilon, pP2N2P0));
        assertFalse(instance.intersects(epsilon, pP2N2N1));
        assertTrue(instance.intersects(epsilon, pP2N2N2));
        // P1
        assertTrue(instance.intersects(epsilon, pP1P2P2));
        assertFalse(instance.intersects(epsilon, pP1P2P1));
        assertFalse(instance.intersects(epsilon, pP1P2P0));
        assertFalse(instance.intersects(epsilon, pP1P2N1));
        assertFalse(instance.intersects(epsilon, pP1P2N2));
        assertFalse(instance.intersects(epsilon, pP1P1P2));
        assertTrue(instance.intersects(epsilon, pP1P1P1));
        assertFalse(instance.intersects(epsilon, pP1P1P0));
        assertFalse(instance.intersects(epsilon, pP1P1N1));
        assertFalse(instance.intersects(epsilon, pP1P1N2));
        assertFalse(instance.intersects(epsilon, pP1P0P2));
        assertFalse(instance.intersects(epsilon, pP1P0P1));
        assertTrue(instance.intersects(epsilon, pP1P0P0));
        assertFalse(instance.intersects(epsilon, pP1P0N1));
        assertFalse(instance.intersects(epsilon, pP1P0N2));
        assertFalse(instance.intersects(epsilon, pP1N1P2));
        assertFalse(instance.intersects(epsilon, pP1N1P1));
        assertFalse(instance.intersects(epsilon, pP1N1P0));
        assertTrue(instance.intersects(epsilon, pP1N1N1));
        assertFalse(instance.intersects(epsilon, pP1N1N2));
        assertFalse(instance.intersects(epsilon, pP1N2P2));
        assertFalse(instance.intersects(epsilon, pP1N2P1));
        assertFalse(instance.intersects(epsilon, pP1N2P0));
        assertFalse(instance.intersects(epsilon, pP1N2N1));
        assertTrue(instance.intersects(epsilon, pP1N2N2));
        // P0
        assertTrue(instance.intersects(epsilon, pP0P2P2));
        assertFalse(instance.intersects(epsilon, pP0P2P1));
        assertFalse(instance.intersects(epsilon, pP0P2P0));
        assertFalse(instance.intersects(epsilon, pP0P2N1));
        assertFalse(instance.intersects(epsilon, pP0P2N2));
        assertFalse(instance.intersects(epsilon, pP0P1P2));
        assertTrue(instance.intersects(epsilon, pP0P1P1));
        assertFalse(instance.intersects(epsilon, pP0P1P0));
        assertFalse(instance.intersects(epsilon, pP0P1N1));
        assertFalse(instance.intersects(epsilon, pP0P1N2));
        assertFalse(instance.intersects(epsilon, pP0P0P2));
        assertFalse(instance.intersects(epsilon, pP0P0P1));
        assertTrue(instance.intersects(epsilon, pP0P0P0));
        assertFalse(instance.intersects(epsilon, pP0P0N1));
        assertFalse(instance.intersects(epsilon, pP0P0N2));
        assertFalse(instance.intersects(epsilon, pP0N1P2));
        assertFalse(instance.intersects(epsilon, pP0N1P1));
        assertFalse(instance.intersects(epsilon, pP0N1P0));
        assertTrue(instance.intersects(epsilon, pP0N1N1));
        assertFalse(instance.intersects(epsilon, pP0N1N2));
        assertFalse(instance.intersects(epsilon, pP0N2P2));
        assertFalse(instance.intersects(epsilon, pP0N2P1));
        assertFalse(instance.intersects(epsilon, pP0N2P0));
        assertFalse(instance.intersects(epsilon, pP0N2N1));
        assertTrue(instance.intersects(epsilon, pP0N2N2));
        // N1
        assertTrue(instance.intersects(epsilon, pN1P2P2));
        assertFalse(instance.intersects(epsilon, pN1P2P1));
        assertFalse(instance.intersects(epsilon, pN1P2P0));
        assertFalse(instance.intersects(epsilon, pN1P2N1));
        assertFalse(instance.intersects(epsilon, pN1P2N2));
        assertFalse(instance.intersects(epsilon, pN1P1P2));
        assertTrue(instance.intersects(epsilon, pN1P1P1));
        assertFalse(instance.intersects(epsilon, pN1P1P0));
        assertFalse(instance.intersects(epsilon, pN1P1N1));
        assertFalse(instance.intersects(epsilon, pN1P1N2));
        assertFalse(instance.intersects(epsilon, pN1P0P2));
        assertFalse(instance.intersects(epsilon, pN1P0P1));
        assertTrue(instance.intersects(epsilon, pN1P0P0));
        assertFalse(instance.intersects(epsilon, pN1P0N1));
        assertFalse(instance.intersects(epsilon, pN1P0N2));
        assertFalse(instance.intersects(epsilon, pN1N1P2));
        assertFalse(instance.intersects(epsilon, pN1N1P1));
        assertFalse(instance.intersects(epsilon, pN1N1P0));
        assertTrue(instance.intersects(epsilon, pN1N1N1));
        assertFalse(instance.intersects(epsilon, pN1N1N2));
        assertFalse(instance.intersects(epsilon, pN1N2P2));
        assertFalse(instance.intersects(epsilon, pN1N2P1));
        assertFalse(instance.intersects(epsilon, pN1N2P0));
        assertFalse(instance.intersects(epsilon, pN1N2N1));
        assertTrue(instance.intersects(epsilon, pN1N2N2));
        // N2
        assertTrue(instance.intersects(epsilon, pN2P2P2));
        assertFalse(instance.intersects(epsilon, pN2P2P1));
        assertFalse(instance.intersects(epsilon, pN2P2P0));
        assertFalse(instance.intersects(epsilon, pN2P2N1));
        assertFalse(instance.intersects(epsilon, pN2P2N2));
        assertFalse(instance.intersects(epsilon, pN2P1P2));
        assertTrue(instance.intersects(epsilon, pN2P1P1));
        assertFalse(instance.intersects(epsilon, pN2P1P0));
        assertFalse(instance.intersects(epsilon, pN2P1N1));
        assertFalse(instance.intersects(epsilon, pN2P1N2));
        assertFalse(instance.intersects(epsilon, pN2P0P2));
        assertFalse(instance.intersects(epsilon, pN2P0P1));
        assertTrue(instance.intersects(epsilon, pN2P0P0));
        assertFalse(instance.intersects(epsilon, pN2P0N1));
        assertFalse(instance.intersects(epsilon, pN2P0N2));
        assertFalse(instance.intersects(epsilon, pN2N1P2));
        assertFalse(instance.intersects(epsilon, pN2N1P1));
        assertFalse(instance.intersects(epsilon, pN2N1P0));
        assertTrue(instance.intersects(epsilon, pN2N1N1));
        assertFalse(instance.intersects(epsilon, pN2N1N2));
        assertFalse(instance.intersects(epsilon, pN2N2P2));
        assertFalse(instance.intersects(epsilon, pN2N2P1));
        assertFalse(instance.intersects(epsilon, pN2N2P0));
        assertFalse(instance.intersects(epsilon, pN2N2N1));
        assertTrue(instance.intersects(epsilon, pN2N2N2));

        // x=-z
        instance = new V3D_Plane_d(env, P1P0P0, P0P0P0, P0N1P1);
        // P2
        assertFalse(instance.intersects(epsilon, pP2P2P2));
        assertFalse(instance.intersects(epsilon, pP2P2P1));
        assertFalse(instance.intersects(epsilon, pP2P2P0));
        assertFalse(instance.intersects(epsilon, pP2P2N1));
        assertTrue(instance.intersects(epsilon, pP2P2N2));
        assertFalse(instance.intersects(epsilon, pP2P1P2));
        assertFalse(instance.intersects(epsilon, pP2P1P1));
        assertFalse(instance.intersects(epsilon, pP2P1P0));
        assertTrue(instance.intersects(epsilon, pP2P1N1));
        assertFalse(instance.intersects(epsilon, pP2P1N2));
        assertFalse(instance.intersects(epsilon, pP2P0P2));
        assertFalse(instance.intersects(epsilon, pP2P0P1));
        assertTrue(instance.intersects(epsilon, pP2P0P0));
        assertFalse(instance.intersects(epsilon, pP2P0N1));
        assertFalse(instance.intersects(epsilon, pP2P0N2));
        assertFalse(instance.intersects(epsilon, pP2N1P2));
        assertTrue(instance.intersects(epsilon, pP2N1P1));
        assertFalse(instance.intersects(epsilon, pP2N1P0));
        assertFalse(instance.intersects(epsilon, pP2N1N1));
        assertFalse(instance.intersects(epsilon, pP2N1N2));
        assertTrue(instance.intersects(epsilon, pP2N2P2));
        assertFalse(instance.intersects(epsilon, pP2N2P1));
        assertFalse(instance.intersects(epsilon, pP2N2P0));
        assertFalse(instance.intersects(epsilon, pP2N2N1));
        assertFalse(instance.intersects(epsilon, pP2N2N2));
        // P1
        assertFalse(instance.intersects(epsilon, pP1P2P2));
        assertFalse(instance.intersects(epsilon, pP1P2P1));
        assertFalse(instance.intersects(epsilon, pP1P2P0));
        assertFalse(instance.intersects(epsilon, pP1P2N1));
        assertTrue(instance.intersects(epsilon, pP1P2N2));
        assertFalse(instance.intersects(epsilon, pP1P1P2));
        assertFalse(instance.intersects(epsilon, pP1P1P1));
        assertFalse(instance.intersects(epsilon, pP1P1P0));
        assertTrue(instance.intersects(epsilon, pP1P1N1));
        assertFalse(instance.intersects(epsilon, pP1P1N2));
        assertFalse(instance.intersects(epsilon, pP1P0P2));
        assertFalse(instance.intersects(epsilon, pP1P0P1));
        assertTrue(instance.intersects(epsilon, pP1P0P0));
        assertFalse(instance.intersects(epsilon, pP1P0N1));
        assertFalse(instance.intersects(epsilon, pP1P0N2));
        assertFalse(instance.intersects(epsilon, pP1N1P2));
        assertTrue(instance.intersects(epsilon, pP1N1P1));
        assertFalse(instance.intersects(epsilon, pP1N1P0));
        assertFalse(instance.intersects(epsilon, pP1N1N1));
        assertFalse(instance.intersects(epsilon, pP1N1N2));
        assertTrue(instance.intersects(epsilon, pP1N2P2));
        assertFalse(instance.intersects(epsilon, pP1N2P1));
        assertFalse(instance.intersects(epsilon, pP1N2P0));
        assertFalse(instance.intersects(epsilon, pP1N2N1));
        assertFalse(instance.intersects(epsilon, pP1N2N2));
        // P0
        assertFalse(instance.intersects(epsilon, pP0P2P2));
        assertFalse(instance.intersects(epsilon, pP0P2P1));
        assertFalse(instance.intersects(epsilon, pP0P2P0));
        assertFalse(instance.intersects(epsilon, pP0P2N1));
        assertTrue(instance.intersects(epsilon, pP0P2N2));
        assertFalse(instance.intersects(epsilon, pP0P1P2));
        assertFalse(instance.intersects(epsilon, pP0P1P1));
        assertFalse(instance.intersects(epsilon, pP0P1P0));
        assertTrue(instance.intersects(epsilon, pP0P1N1));
        assertFalse(instance.intersects(epsilon, pP0P1N2));
        assertFalse(instance.intersects(epsilon, pP0P0P2));
        assertFalse(instance.intersects(epsilon, pP0P0P1));
        assertTrue(instance.intersects(epsilon, pP0P0P0));
        assertFalse(instance.intersects(epsilon, pP0P0N1));
        assertFalse(instance.intersects(epsilon, pP0P0N2));
        assertFalse(instance.intersects(epsilon, pP0N1P2));
        assertTrue(instance.intersects(epsilon, pP0N1P1));
        assertFalse(instance.intersects(epsilon, pP0N1P0));
        assertFalse(instance.intersects(epsilon, pP0N1N1));
        assertFalse(instance.intersects(epsilon, pP0N1N2));
        assertTrue(instance.intersects(epsilon, pP0N2P2));
        assertFalse(instance.intersects(epsilon, pP0N2P1));
        assertFalse(instance.intersects(epsilon, pP0N2P0));
        assertFalse(instance.intersects(epsilon, pP0N2N1));
        assertFalse(instance.intersects(epsilon, pP0N2N2));
        // N1
        assertFalse(instance.intersects(epsilon, pN1P2P2));
        assertFalse(instance.intersects(epsilon, pN1P2P1));
        assertFalse(instance.intersects(epsilon, pN1P2P0));
        assertFalse(instance.intersects(epsilon, pN1P2N1));
        assertTrue(instance.intersects(epsilon, pN1P2N2));
        assertFalse(instance.intersects(epsilon, pN1P1P2));
        assertFalse(instance.intersects(epsilon, pN1P1P1));
        assertFalse(instance.intersects(epsilon, pN1P1P0));
        assertTrue(instance.intersects(epsilon, pN1P1N1));
        assertFalse(instance.intersects(epsilon, pN1P1N2));
        assertFalse(instance.intersects(epsilon, pN1P0P2));
        assertFalse(instance.intersects(epsilon, pN1P0P1));
        assertTrue(instance.intersects(epsilon, pN1P0P0));
        assertFalse(instance.intersects(epsilon, pN1P0N1));
        assertFalse(instance.intersects(epsilon, pN1P0N2));
        assertFalse(instance.intersects(epsilon, pN1N1P2));
        assertTrue(instance.intersects(epsilon, pN1N1P1));
        assertFalse(instance.intersects(epsilon, pN1N1P0));
        assertFalse(instance.intersects(epsilon, pN1N1N1));
        assertFalse(instance.intersects(epsilon, pN1N1N2));
        assertTrue(instance.intersects(epsilon, pN1N2P2));
        assertFalse(instance.intersects(epsilon, pN1N2P1));
        assertFalse(instance.intersects(epsilon, pN1N2P0));
        assertFalse(instance.intersects(epsilon, pN1N2N1));
        assertFalse(instance.intersects(epsilon, pN1N2N2));
        // N2
        assertFalse(instance.intersects(epsilon, pN2P2P2));
        assertFalse(instance.intersects(epsilon, pN2P2P1));
        assertFalse(instance.intersects(epsilon, pN2P2P0));
        assertFalse(instance.intersects(epsilon, pN2P2N1));
        assertTrue(instance.intersects(epsilon, pN2P2N2));
        assertFalse(instance.intersects(epsilon, pN2P1P2));
        assertFalse(instance.intersects(epsilon, pN2P1P1));
        assertFalse(instance.intersects(epsilon, pN2P1P0));
        assertTrue(instance.intersects(epsilon, pN2P1N1));
        assertFalse(instance.intersects(epsilon, pN2P1N2));
        assertFalse(instance.intersects(epsilon, pN2P0P2));
        assertFalse(instance.intersects(epsilon, pN2P0P1));
        assertTrue(instance.intersects(epsilon, pN2P0P0));
        assertFalse(instance.intersects(epsilon, pN2P0N1));
        assertFalse(instance.intersects(epsilon, pN2P0N2));
        assertFalse(instance.intersects(epsilon, pN2N1P2));
        assertTrue(instance.intersects(epsilon, pN2N1P1));
        assertFalse(instance.intersects(epsilon, pN2N1P0));
        assertFalse(instance.intersects(epsilon, pN2N1N1));
        assertFalse(instance.intersects(epsilon, pN2N1N2));
        assertTrue(instance.intersects(epsilon, pN2N2P2));
        assertFalse(instance.intersects(epsilon, pN2N2P1));
        assertFalse(instance.intersects(epsilon, pN2N2P0));
        assertFalse(instance.intersects(epsilon, pN2N2N1));
        assertFalse(instance.intersects(epsilon, pN2N2N2));
        // x=y-z
        instance = new V3D_Plane_d(env, P0P0P0, P1P1P0, N1P0P1);
        // P2
        assertFalse(instance.intersects(epsilon, pP2P2P2));
        assertFalse(instance.intersects(epsilon, pP2P2P1));
        assertTrue(instance.intersects(epsilon, pP2P2P0));
        assertFalse(instance.intersects(epsilon, pP2P2N1));
        assertFalse(instance.intersects(epsilon, pP2P2N2));
        assertFalse(instance.intersects(epsilon, pP2P1P2));
        assertFalse(instance.intersects(epsilon, pP2P1P1));
        assertFalse(instance.intersects(epsilon, pP2P1P0));
        assertTrue(instance.intersects(epsilon, pP2P1N1));
        assertFalse(instance.intersects(epsilon, pP2P1N2));
        assertFalse(instance.intersects(epsilon, pP2P0P2));
        assertFalse(instance.intersects(epsilon, pP2P0P1));
        assertFalse(instance.intersects(epsilon, pP2P0P0));
        assertFalse(instance.intersects(epsilon, pP2P0N1));
        assertTrue(instance.intersects(epsilon, pP2P0N2));
        assertFalse(instance.intersects(epsilon, pP2N1P2));
        assertFalse(instance.intersects(epsilon, pP2N1P1));
        assertFalse(instance.intersects(epsilon, pP2N1P0));
        assertFalse(instance.intersects(epsilon, pP2N1N1));
        assertFalse(instance.intersects(epsilon, pP2N1N2));
        assertFalse(instance.intersects(epsilon, pP2N2P2));
        assertFalse(instance.intersects(epsilon, pP2N2P1));
        assertFalse(instance.intersects(epsilon, pP2N2P0));
        assertFalse(instance.intersects(epsilon, pP2N2N1));
        assertFalse(instance.intersects(epsilon, pP2N2N2));
        // P1
        assertFalse(instance.intersects(epsilon, pP1P2P2));
        assertTrue(instance.intersects(epsilon, pP1P2P1));
        assertFalse(instance.intersects(epsilon, pP1P2P0));
        assertFalse(instance.intersects(epsilon, pP1P2N1));
        assertFalse(instance.intersects(epsilon, pP1P2N2));
        assertFalse(instance.intersects(epsilon, pP1P1P2));
        assertFalse(instance.intersects(epsilon, pP1P1P1));
        assertTrue(instance.intersects(epsilon, pP1P1P0));
        assertFalse(instance.intersects(epsilon, pP1P1N1));
        assertFalse(instance.intersects(epsilon, pP1P1N2));
        assertFalse(instance.intersects(epsilon, pP1P0P2));
        assertFalse(instance.intersects(epsilon, pP1P0P1));
        assertFalse(instance.intersects(epsilon, pP1P0P0));
        assertTrue(instance.intersects(epsilon, pP1P0N1));
        assertFalse(instance.intersects(epsilon, pP1P0N2));
        assertFalse(instance.intersects(epsilon, pP1N1P2));
        assertFalse(instance.intersects(epsilon, pP1N1P1));
        assertFalse(instance.intersects(epsilon, pP1N1P0));
        assertFalse(instance.intersects(epsilon, pP1N1N1));
        assertTrue(instance.intersects(epsilon, pP1N1N2));
        assertFalse(instance.intersects(epsilon, pP1N2P2));
        assertFalse(instance.intersects(epsilon, pP1N2P1));
        assertFalse(instance.intersects(epsilon, pP1N2P0));
        assertFalse(instance.intersects(epsilon, pP1N2N1));
        assertFalse(instance.intersects(epsilon, pP1N2N2));
        // P0
        assertTrue(instance.intersects(epsilon, pP0P2P2));
        assertFalse(instance.intersects(epsilon, pP0P2P1));
        assertFalse(instance.intersects(epsilon, pP0P2P0));
        assertFalse(instance.intersects(epsilon, pP0P2N1));
        assertFalse(instance.intersects(epsilon, pP0P2N2));
        assertFalse(instance.intersects(epsilon, pP0P1P2));
        assertTrue(instance.intersects(epsilon, pP0P1P1));
        assertFalse(instance.intersects(epsilon, pP0P1P0));
        assertFalse(instance.intersects(epsilon, pP0P1N1));
        assertFalse(instance.intersects(epsilon, pP0P1N2));
        assertFalse(instance.intersects(epsilon, pP0P0P2));
        assertFalse(instance.intersects(epsilon, pP0P0P1));
        assertTrue(instance.intersects(epsilon, pP0P0P0));
        assertFalse(instance.intersects(epsilon, pP0P0N1));
        assertFalse(instance.intersects(epsilon, pP0P0N2));
        assertFalse(instance.intersects(epsilon, pP0N1P2));
        assertFalse(instance.intersects(epsilon, pP0N1P1));
        assertFalse(instance.intersects(epsilon, pP0N1P0));
        assertTrue(instance.intersects(epsilon, pP0N1N1));
        assertFalse(instance.intersects(epsilon, pP0N1N2));
        assertFalse(instance.intersects(epsilon, pP0N2P2));
        assertFalse(instance.intersects(epsilon, pP0N2P1));
        assertFalse(instance.intersects(epsilon, pP0N2P0));
        assertFalse(instance.intersects(epsilon, pP0N2N1));
        assertTrue(instance.intersects(epsilon, pP0N2N2));
        // N1
        assertFalse(instance.intersects(epsilon, pN1P2P2));
        assertFalse(instance.intersects(epsilon, pN1P2P1));
        assertFalse(instance.intersects(epsilon, pN1P2P0));
        assertFalse(instance.intersects(epsilon, pN1P2N1));
        assertFalse(instance.intersects(epsilon, pN1P2N2));
        assertTrue(instance.intersects(epsilon, pN1P1P2));
        assertFalse(instance.intersects(epsilon, pN1P1P1));
        assertFalse(instance.intersects(epsilon, pN1P1P0));
        assertFalse(instance.intersects(epsilon, pN1P1N1));
        assertFalse(instance.intersects(epsilon, pN1P1N2));
        assertFalse(instance.intersects(epsilon, pN1P0P2));
        assertTrue(instance.intersects(epsilon, pN1P0P1));
        assertFalse(instance.intersects(epsilon, pN1P0P0));
        assertFalse(instance.intersects(epsilon, pN1P0N1));
        assertFalse(instance.intersects(epsilon, pN1P0N2));
        assertFalse(instance.intersects(epsilon, pN1N1P2));
        assertFalse(instance.intersects(epsilon, pN1N1P1));
        assertTrue(instance.intersects(epsilon, pN1N1P0));
        assertFalse(instance.intersects(epsilon, pN1N1N1));
        assertFalse(instance.intersects(epsilon, pN1N1N2));
        assertFalse(instance.intersects(epsilon, pN1N2P2));
        assertFalse(instance.intersects(epsilon, pN1N2P1));
        assertFalse(instance.intersects(epsilon, pN1N2P0));
        assertTrue(instance.intersects(epsilon, pN1N2N1));
        assertFalse(instance.intersects(epsilon, pN1N2N2));
        // N2
        assertFalse(instance.intersects(epsilon, pN2P2P2));
        assertFalse(instance.intersects(epsilon, pN2P2P1));
        assertFalse(instance.intersects(epsilon, pN2P2P0));
        assertFalse(instance.intersects(epsilon, pN2P2N1));
        assertFalse(instance.intersects(epsilon, pN2P2N2));
        assertFalse(instance.intersects(epsilon, pN2P1P2));
        assertFalse(instance.intersects(epsilon, pN2P1P1));
        assertFalse(instance.intersects(epsilon, pN2P1P0));
        assertFalse(instance.intersects(epsilon, pN2P1N1));
        assertFalse(instance.intersects(epsilon, pN2P1N2));
        assertTrue(instance.intersects(epsilon, pN2P0P2));
        assertFalse(instance.intersects(epsilon, pN2P0P1));
        assertFalse(instance.intersects(epsilon, pN2P0P0));
        assertFalse(instance.intersects(epsilon, pN2P0N1));
        assertFalse(instance.intersects(epsilon, pN2P0N2));
        assertFalse(instance.intersects(epsilon, pN2N1P2));
        assertTrue(instance.intersects(epsilon, pN2N1P1));
        assertFalse(instance.intersects(epsilon, pN2N1P0));
        assertFalse(instance.intersects(epsilon, pN2N1N1));
        assertFalse(instance.intersects(epsilon, pN2N1N2));
        assertFalse(instance.intersects(epsilon, pN2N2P2));
        assertFalse(instance.intersects(epsilon, pN2N2P1));
        assertTrue(instance.intersects(epsilon, pN2N2P0));
        assertFalse(instance.intersects(epsilon, pN2N2N1));
        assertFalse(instance.intersects(epsilon, pN2N2N2));
        // x=z-y
        instance = new V3D_Plane_d(env, P1P0P1, P0P1P1, P0P0P0);
        // P2
        assertFalse(instance.intersects(epsilon, pP2P2P2));
        assertFalse(instance.intersects(epsilon, pP2P2P1));
        assertFalse(instance.intersects(epsilon, pP2P2P0));
        assertFalse(instance.intersects(epsilon, pP2P2N1));
        assertFalse(instance.intersects(epsilon, pP2P2N2));
        assertFalse(instance.intersects(epsilon, pP2P1P2));
        assertFalse(instance.intersects(epsilon, pP2P1P1));
        assertFalse(instance.intersects(epsilon, pP2P1P0));
        assertFalse(instance.intersects(epsilon, pP2P1N1));
        assertFalse(instance.intersects(epsilon, pP2P1N2));
        assertTrue(instance.intersects(epsilon, pP2P0P2));
        assertFalse(instance.intersects(epsilon, pP2P0P1));
        assertFalse(instance.intersects(epsilon, pP2P0P0));
        assertFalse(instance.intersects(epsilon, pP2P0N1));
        assertFalse(instance.intersects(epsilon, pP2P0N2));
        assertFalse(instance.intersects(epsilon, pP2N1P2));
        assertTrue(instance.intersects(epsilon, pP2N1P1));
        assertFalse(instance.intersects(epsilon, pP2N1P0));
        assertFalse(instance.intersects(epsilon, pP2N1N1));
        assertFalse(instance.intersects(epsilon, pP2N1N2));
        assertFalse(instance.intersects(epsilon, pP2N2P2));
        assertFalse(instance.intersects(epsilon, pP2N2P1));
        assertTrue(instance.intersects(epsilon, pP2N2P0));
        assertFalse(instance.intersects(epsilon, pP2N2N1));
        assertFalse(instance.intersects(epsilon, pP2N2N2));
        // P1
        assertFalse(instance.intersects(epsilon, pP1P2P2));
        assertFalse(instance.intersects(epsilon, pP1P2P1));
        assertFalse(instance.intersects(epsilon, pP1P2P0));
        assertFalse(instance.intersects(epsilon, pP1P2N1));
        assertFalse(instance.intersects(epsilon, pP1P2N2));
        assertTrue(instance.intersects(epsilon, pP1P1P2));
        assertFalse(instance.intersects(epsilon, pP1P1P1));
        assertFalse(instance.intersects(epsilon, pP1P1P0));
        assertFalse(instance.intersects(epsilon, pP1P1N1));
        assertFalse(instance.intersects(epsilon, pP1P1N2));
        assertFalse(instance.intersects(epsilon, pP1P0P2));
        assertTrue(instance.intersects(epsilon, pP1P0P1));
        assertFalse(instance.intersects(epsilon, pP1P0P0));
        assertFalse(instance.intersects(epsilon, pP1P0N1));
        assertFalse(instance.intersects(epsilon, pP1P0N2));
        assertFalse(instance.intersects(epsilon, pP1N1P2));
        assertFalse(instance.intersects(epsilon, pP1N1P1));
        assertTrue(instance.intersects(epsilon, pP1N1P0));
        assertFalse(instance.intersects(epsilon, pP1N1N1));
        assertFalse(instance.intersects(epsilon, pP1N1N2));
        assertFalse(instance.intersects(epsilon, pP1N2P2));
        assertFalse(instance.intersects(epsilon, pP1N2P1));
        assertFalse(instance.intersects(epsilon, pP1N2P0));
        assertTrue(instance.intersects(epsilon, pP1N2N1));
        assertFalse(instance.intersects(epsilon, pP1N2N2));
        // P0
        assertTrue(instance.intersects(epsilon, pP0P2P2));
        assertFalse(instance.intersects(epsilon, pP0P2P1));
        assertFalse(instance.intersects(epsilon, pP0P2P0));
        assertFalse(instance.intersects(epsilon, pP0P2N1));
        assertFalse(instance.intersects(epsilon, pP0P2N2));
        assertFalse(instance.intersects(epsilon, pP0P1P2));
        assertTrue(instance.intersects(epsilon, pP0P1P1));
        assertFalse(instance.intersects(epsilon, pP0P1P0));
        assertFalse(instance.intersects(epsilon, pP0P1N1));
        assertFalse(instance.intersects(epsilon, pP0P1N2));
        assertFalse(instance.intersects(epsilon, pP0P0P2));
        assertFalse(instance.intersects(epsilon, pP0P0P1));
        assertTrue(instance.intersects(epsilon, pP0P0P0));
        assertFalse(instance.intersects(epsilon, pP0P0N1));
        assertFalse(instance.intersects(epsilon, pP0P0N2));
        assertFalse(instance.intersects(epsilon, pP0N1P2));
        assertFalse(instance.intersects(epsilon, pP0N1P1));
        assertFalse(instance.intersects(epsilon, pP0N1P0));
        assertTrue(instance.intersects(epsilon, pP0N1N1));
        assertFalse(instance.intersects(epsilon, pP0N1N2));
        assertFalse(instance.intersects(epsilon, pP0N2P2));
        assertFalse(instance.intersects(epsilon, pP0N2P1));
        assertFalse(instance.intersects(epsilon, pP0N2P0));
        assertFalse(instance.intersects(epsilon, pP0N2N1));
        assertTrue(instance.intersects(epsilon, pP0N2N2));
        // N1
        assertFalse(instance.intersects(epsilon, pN1P2P2));
        assertTrue(instance.intersects(epsilon, pN1P2P1));
        assertFalse(instance.intersects(epsilon, pN1P2P0));
        assertFalse(instance.intersects(epsilon, pN1P2N1));
        assertFalse(instance.intersects(epsilon, pN1P2N2));
        assertFalse(instance.intersects(epsilon, pN1P1P2));
        assertFalse(instance.intersects(epsilon, pN1P1P1));
        assertTrue(instance.intersects(epsilon, pN1P1P0));
        assertFalse(instance.intersects(epsilon, pN1P1N1));
        assertFalse(instance.intersects(epsilon, pN1P1N2));
        assertFalse(instance.intersects(epsilon, pN1P0P2));
        assertFalse(instance.intersects(epsilon, pN1P0P1));
        assertFalse(instance.intersects(epsilon, pN1P0P0));
        assertTrue(instance.intersects(epsilon, pN1P0N1));
        assertFalse(instance.intersects(epsilon, pN1P0N2));
        assertFalse(instance.intersects(epsilon, pN1N1P2));
        assertFalse(instance.intersects(epsilon, pN1N1P1));
        assertFalse(instance.intersects(epsilon, pN1N1P0));
        assertFalse(instance.intersects(epsilon, pN1N1N1));
        assertTrue(instance.intersects(epsilon, pN1N1N2));
        assertFalse(instance.intersects(epsilon, pN1N2P2));
        assertFalse(instance.intersects(epsilon, pN1N2P1));
        assertFalse(instance.intersects(epsilon, pN1N2P0));
        assertFalse(instance.intersects(epsilon, pN1N2N1));
        assertFalse(instance.intersects(epsilon, pN1N2N2));
        // N2
        assertFalse(instance.intersects(epsilon, pN2P2P2));
        assertFalse(instance.intersects(epsilon, pN2P2P1));
        assertTrue(instance.intersects(epsilon, pN2P2P0));
        assertFalse(instance.intersects(epsilon, pN2P2N1));
        assertFalse(instance.intersects(epsilon, pN2P2N2));
        assertFalse(instance.intersects(epsilon, pN2P1P2));
        assertFalse(instance.intersects(epsilon, pN2P1P1));
        assertFalse(instance.intersects(epsilon, pN2P1P0));
        assertTrue(instance.intersects(epsilon, pN2P1N1));
        assertFalse(instance.intersects(epsilon, pN2P1N2));
        assertFalse(instance.intersects(epsilon, pN2P0P2));
        assertFalse(instance.intersects(epsilon, pN2P0P1));
        assertFalse(instance.intersects(epsilon, pN2P0P0));
        assertFalse(instance.intersects(epsilon, pN2P0N1));
        assertTrue(instance.intersects(epsilon, pN2P0N2));
        assertFalse(instance.intersects(epsilon, pN2N1P2));
        assertFalse(instance.intersects(epsilon, pN2N1P1));
        assertFalse(instance.intersects(epsilon, pN2N1P0));
        assertFalse(instance.intersects(epsilon, pN2N1N1));
        assertFalse(instance.intersects(epsilon, pN2N1N2));
        assertFalse(instance.intersects(epsilon, pN2N2P2));
        assertFalse(instance.intersects(epsilon, pN2N2P1));
        assertFalse(instance.intersects(epsilon, pN2N2P0));
        assertFalse(instance.intersects(epsilon, pN2N2N1));
        assertFalse(instance.intersects(epsilon, pN2N2N2));
        // y=x-z
        instance = new V3D_Plane_d(env, P1P1P0, P0P0P0, P0N1P1);
        // P2
        assertFalse(instance.intersects(epsilon, pP2P2P2));
        assertFalse(instance.intersects(epsilon, pP2P2P1));
        assertTrue(instance.intersects(epsilon, pP2P2P0));
        assertFalse(instance.intersects(epsilon, pP2P2N1));
        assertFalse(instance.intersects(epsilon, pP2P2N2));
        assertFalse(instance.intersects(epsilon, pP2P1P2));
        assertTrue(instance.intersects(epsilon, pP2P1P1));
        assertFalse(instance.intersects(epsilon, pP2P1P0));
        assertFalse(instance.intersects(epsilon, pP2P1N1));
        assertFalse(instance.intersects(epsilon, pP2P1N2));
        assertTrue(instance.intersects(epsilon, pP2P0P2));
        assertFalse(instance.intersects(epsilon, pP2P0P1));
        assertFalse(instance.intersects(epsilon, pP2P0P0));
        assertFalse(instance.intersects(epsilon, pP2P0N1));
        assertFalse(instance.intersects(epsilon, pP2P0N2));
        assertFalse(instance.intersects(epsilon, pP2N1P2));
        assertFalse(instance.intersects(epsilon, pP2N1P1));
        assertFalse(instance.intersects(epsilon, pP2N1P0));
        assertFalse(instance.intersects(epsilon, pP2N1N1));
        assertFalse(instance.intersects(epsilon, pP2N1N2));
        assertFalse(instance.intersects(epsilon, pP2N2P2));
        assertFalse(instance.intersects(epsilon, pP2N2P1));
        assertFalse(instance.intersects(epsilon, pP2N2P0));
        assertFalse(instance.intersects(epsilon, pP2N2N1));
        assertFalse(instance.intersects(epsilon, pP2N2N2));
        // P1
        assertFalse(instance.intersects(epsilon, pP1P2P2));
        assertFalse(instance.intersects(epsilon, pP1P2P1));
        assertFalse(instance.intersects(epsilon, pP1P2P0));
        assertTrue(instance.intersects(epsilon, pP1P2N1));
        assertFalse(instance.intersects(epsilon, pP1P2N2));
        assertFalse(instance.intersects(epsilon, pP1P1P2));
        assertFalse(instance.intersects(epsilon, pP1P1P1));
        assertTrue(instance.intersects(epsilon, pP1P1P0));
        assertFalse(instance.intersects(epsilon, pP1P1N1));
        assertFalse(instance.intersects(epsilon, pP1P1N2));
        assertFalse(instance.intersects(epsilon, pP1P0P2));
        assertTrue(instance.intersects(epsilon, pP1P0P1));
        assertFalse(instance.intersects(epsilon, pP1P0P0));
        assertFalse(instance.intersects(epsilon, pP1P0N1));
        assertFalse(instance.intersects(epsilon, pP1P0N2));
        assertTrue(instance.intersects(epsilon, pP1N1P2));
        assertFalse(instance.intersects(epsilon, pP1N1P1));
        assertFalse(instance.intersects(epsilon, pP1N1P0));
        assertFalse(instance.intersects(epsilon, pP1N1N1));
        assertFalse(instance.intersects(epsilon, pP1N1N2));
        assertFalse(instance.intersects(epsilon, pP1N2P2));
        assertFalse(instance.intersects(epsilon, pP1N2P1));
        assertFalse(instance.intersects(epsilon, pP1N2P0));
        assertFalse(instance.intersects(epsilon, pP1N2N1));
        assertFalse(instance.intersects(epsilon, pP1N2N2));
        // P0
        assertFalse(instance.intersects(epsilon, pP0P2P2));
        assertFalse(instance.intersects(epsilon, pP0P2P1));
        assertFalse(instance.intersects(epsilon, pP0P2P0));
        assertFalse(instance.intersects(epsilon, pP0P2N1));
        assertTrue(instance.intersects(epsilon, pP0P2N2));
        assertFalse(instance.intersects(epsilon, pP0P1P2));
        assertFalse(instance.intersects(epsilon, pP0P1P1));
        assertFalse(instance.intersects(epsilon, pP0P1P0));
        assertTrue(instance.intersects(epsilon, pP0P1N1));
        assertFalse(instance.intersects(epsilon, pP0P1N2));
        assertFalse(instance.intersects(epsilon, pP0P0P2));
        assertFalse(instance.intersects(epsilon, pP0P0P1));
        assertTrue(instance.intersects(epsilon, pP0P0P0));
        assertFalse(instance.intersects(epsilon, pP0P0N1));
        assertFalse(instance.intersects(epsilon, pP0P0N2));
        assertFalse(instance.intersects(epsilon, pP0N1P2));
        assertTrue(instance.intersects(epsilon, pP0N1P1));
        assertFalse(instance.intersects(epsilon, pP0N1P0));
        assertFalse(instance.intersects(epsilon, pP0N1N1));
        assertFalse(instance.intersects(epsilon, pP0N1N2));
        assertTrue(instance.intersects(epsilon, pP0N2P2));
        assertFalse(instance.intersects(epsilon, pP0N2P1));
        assertFalse(instance.intersects(epsilon, pP0N2P0));
        assertFalse(instance.intersects(epsilon, pP0N2N1));
        assertFalse(instance.intersects(epsilon, pP0N2N2));
        // N1
        assertFalse(instance.intersects(epsilon, pN1P2P2));
        assertFalse(instance.intersects(epsilon, pN1P2P1));
        assertFalse(instance.intersects(epsilon, pN1P2P0));
        assertFalse(instance.intersects(epsilon, pN1P2N1));
        assertFalse(instance.intersects(epsilon, pN1P2N2));
        assertFalse(instance.intersects(epsilon, pN1P1P2));
        assertFalse(instance.intersects(epsilon, pN1P1P1));
        assertFalse(instance.intersects(epsilon, pN1P1P0));
        assertFalse(instance.intersects(epsilon, pN1P1N1));
        assertTrue(instance.intersects(epsilon, pN1P1N2));
        assertFalse(instance.intersects(epsilon, pN1P0P2));
        assertFalse(instance.intersects(epsilon, pN1P0P1));
        assertFalse(instance.intersects(epsilon, pN1P0P0));
        assertTrue(instance.intersects(epsilon, pN1P0N1));
        assertFalse(instance.intersects(epsilon, pN1P0N2));
        assertFalse(instance.intersects(epsilon, pN1N1P2));
        assertFalse(instance.intersects(epsilon, pN1N1P1));
        assertTrue(instance.intersects(epsilon, pN1N1P0));
        assertFalse(instance.intersects(epsilon, pN1N1N1));
        assertFalse(instance.intersects(epsilon, pN1N1N2));
        assertFalse(instance.intersects(epsilon, pN1N2P2));
        assertTrue(instance.intersects(epsilon, pN1N2P1));
        assertFalse(instance.intersects(epsilon, pN1N2P0));
        assertFalse(instance.intersects(epsilon, pN1N2N1));
        assertFalse(instance.intersects(epsilon, pN1N2N2));
        // N2
        assertFalse(instance.intersects(epsilon, pN2P2P2));
        assertFalse(instance.intersects(epsilon, pN2P2P1));
        assertFalse(instance.intersects(epsilon, pN2P2P0));
        assertFalse(instance.intersects(epsilon, pN2P2N1));
        assertFalse(instance.intersects(epsilon, pN2P2N2));
        assertFalse(instance.intersects(epsilon, pN2P1P2));
        assertFalse(instance.intersects(epsilon, pN2P1P1));
        assertFalse(instance.intersects(epsilon, pN2P1P0));
        assertFalse(instance.intersects(epsilon, pN2P1N1));
        assertFalse(instance.intersects(epsilon, pN2P1N2));
        assertFalse(instance.intersects(epsilon, pN2P0P2));
        assertFalse(instance.intersects(epsilon, pN2P0P1));
        assertFalse(instance.intersects(epsilon, pN2P0P0));
        assertFalse(instance.intersects(epsilon, pN2P0N1));
        assertTrue(instance.intersects(epsilon, pN2P0N2));
        assertFalse(instance.intersects(epsilon, pN2N1P2));
        assertFalse(instance.intersects(epsilon, pN2N1P1));
        assertFalse(instance.intersects(epsilon, pN2N1P0));
        assertTrue(instance.intersects(epsilon, pN2N1N1));
        assertFalse(instance.intersects(epsilon, pN2N1N2));
        assertFalse(instance.intersects(epsilon, pN2N2P2));
        assertFalse(instance.intersects(epsilon, pN2N2P1));
        assertTrue(instance.intersects(epsilon, pN2N2P0));
        assertFalse(instance.intersects(epsilon, pN2N2N1));
        assertFalse(instance.intersects(epsilon, pN2N2N2));
        // Test 2 from https://math.stackexchange.com/questions/2686606/equation-of-a-plane-passing-through-3-points        
        V3D_Vector_d n = new V3D_Vector_d(1, -2, 1);
        V3D_Point_d p = pP1N2P1;
        instance = new V3D_Plane_d(p, n);
        assertTrue(instance.intersects(epsilon, new V3D_Point_d(env, 4, -2, -2)));
        assertTrue(instance.intersects(epsilon, new V3D_Point_d(env, 4, 1, 4)));
        n = new V3D_Vector_d(1, -2, 1);
        p = new V3D_Point_d(env, 4, -2, -2);
        instance = new V3D_Plane_d(p, n);
        assertTrue(instance.intersects(epsilon, new V3D_Point_d(env, 1, -2, 1)));
        assertTrue(instance.intersects(epsilon, new V3D_Point_d(env, 4, 1, 4)));
        // Test 3
        n = new V3D_Vector_d(1, -2, 1);
        p = pP0P0P0;
        instance = new V3D_Plane_d(p, n);
        assertTrue(instance.intersects(epsilon, new V3D_Point_d(env, 3, 0, -3)));
        assertTrue(instance.intersects(epsilon, new V3D_Point_d(env, 3, 3, 3)));
        n = new V3D_Vector_d(1, -2, 1);
        p = new V3D_Point_d(env, 3, 0, -3);
        instance = new V3D_Plane_d(p, n);
        assertTrue(instance.intersects(epsilon, new V3D_Point_d(env, 0, 0, 0)));
        assertTrue(instance.intersects(epsilon, new V3D_Point_d(env, 3, 3, 3)));
    }

    /**
     * Test of getNormalVector method, of class V3D_Plane_d.
     */
    @Test
    public void testGetNormalVector() {
        System.out.println("getNormalVector");
        // Z = 0
        V3D_Plane_d instance = new V3D_Plane_d(env, P0P0P0, P1P0P0, P0P1P0);
        //V3D_Plane_d instance = new V3D_Plane_d(env, P0P0P0, P0P1P0, P1P0P0);
        //V3D_Vector_d expResult = P0P0N1;
        V3D_Vector_d expResult = P0P0P1;
        V3D_Vector_d result = instance.getN();
        assertTrue(expResult.equals(result));
        // Z = -1
        instance = new V3D_Plane_d(env, P0P0N1, P1P0N1, P0P1N1);
        //expResult = P0P0N1;
        expResult = P0P0P1;
        result = instance.getN();
        assertTrue(expResult.equals(result));
        // Z = 1
        instance = new V3D_Plane_d(env, P0P0P1, P1P0P1, P0P1P1);
        expResult = P0P0P1;
        result = instance.getN();
        assertTrue(expResult.equals(result));
        // Z = 1
        instance = new V3D_Plane_d(env, P1P0P1, P0P1P1, P0P0P1);
        expResult = P0P0P1;
        result = instance.getN();
        assertTrue(expResult.equals(result));
        // Z = 1
        instance = new V3D_Plane_d(env, P0P1P1, P0P0P1, P1P0P1);
        //expResult = new V3D_Vector_d(P0P0N1);
        expResult = P0P0P1;
        result = instance.getN();
        assertTrue(expResult.equals(result));
        // Y = 0
        instance = new V3D_Plane_d(env, P0P0P0, P0P1P0, P0P0N1);
        //expResult = new V3D_Vector_d(P1P0P0);
        expResult = N1P0P0;
        result = instance.getN();
        assertTrue(expResult.equals(result));
        // X = 0
        instance = new V3D_Plane_d(env, P0P0P0, P1P0P0, P0P0N1);
        //expResult = new V3D_Vector_d(P0N1P0);
        expResult = P0P1P0;
        result = instance.getN();
        assertTrue(expResult.equals(result));
        // Y = 0
        instance = new V3D_Plane_d(env, P0P0P0, P1P0P0, N1P0P1);
        expResult = P0N1P0;
        result = instance.getN();
        assertTrue(expResult.equals(result));
        // 
        instance = new V3D_Plane_d(env, P0P1P0, P1P1P1, P1P0P0);
        //expResult = new V3D_Vector_d(N1N1P1);
        expResult = P1P1N1;
        result = instance.getN();
        assertTrue(expResult.equals(result));
        // X = 0
        instance = new V3D_Plane_d(env, P0P0P0, P0P1P1, P0N1P0);
        //expResult = new V3D_Vector_d(N1P0P0);
        expResult = P1P0P0;
        result = instance.getN();
        assertTrue(expResult.equals(result));
        // 
        instance = new V3D_Plane_d(env, P0P0P0, P1P1P1, P0N1N1);
        //expResult = new V3D_Vector_d(P0N1P1);
        expResult = P0P1N1;
        result = instance.getN();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of isParallel method, of class V3D_Plane_d.
     */
    @Test
    public void testIsParallel_V3D_Plane_d_int() {
        System.out.println("isParallel");
        V3D_Plane_d p = new V3D_Plane_d(env, P1P1P0, P1N1P0, N1P1P0);
        V3D_Plane_d instance = new V3D_Plane_d(env, P1P1P1, P1N1P1, N1P1P1);
        assertTrue(instance.isParallel(p));
        // Test 2
        instance = new V3D_Plane_d(env, P1P1N1, P1N1N1, N1P1N1);
        assertTrue(instance.isParallel(p));
        // Test 3
        instance = new V3D_Plane_d(env, P1P1N1, P1N1N1, N1P1N1);
        assertTrue(instance.isParallel(p));
        // Test 4
        p = V3D_Plane_d.X0;
        instance = V3D_Plane_d.Y0;
        assertFalse(instance.isParallel(p));
        // Test 5
        p = V3D_Plane_d.X0;
        instance = V3D_Plane_d.Z0;
        assertFalse(instance.isParallel(p));
        // Test 6
        p = V3D_Plane_d.Y0;
        instance = V3D_Plane_d.Z0;
        assertFalse(instance.isParallel(p));
        // Test 7
        p = new V3D_Plane_d(env, P0P0P0, P0P1P0, P1P1P1);
        instance = new V3D_Plane_d(env, P1P0P0, P1P1P0, P2P1P1);
        assertTrue(instance.isParallel(p));
        // Test 8
        instance = new V3D_Plane_d(env, P1N1P0, P1P0P0, P2P0P1);
        assertTrue(instance.isParallel(p));
        // Test 9
        instance = new V3D_Plane_d(env, P1P0P0, P1P1P0, P1P1P1);
        assertFalse(instance.isParallel(p));
    }

//    /**
//     * Test of isParallel method, of class V3D_Plane_d.
//     */
//    @Test
//    public void testIsParallel_V3D_Line_d_double() {
//        System.out.println("isParallel");
//        double epsilon = 1d / 10000000d;
//        V3D_Line_d l = V3D_Line_d.X_AXIS;
//        V3D_Plane_d instance = V3D_Plane_d.Y0;
//        assertTrue(instance.isParallel(l));
//        // Test 2
//        instance = V3D_Plane_d.Z0;
//        assertTrue(instance.isParallel(l));
//        // Test 3
//        instance = V3D_Plane_d.X0;
//        assertFalse(instance.isParallel(l));
//        // Test 4
//        l = V3D_Line_d.Y_AXIS;
//        instance = V3D_Plane_d.X0;
//        assertTrue(instance.isParallel(l));
//        // Test 5
//        instance = V3D_Plane_d.Y0;
//        assertFalse(instance.isParallel(l));
//        // Test 6
//        instance = V3D_Plane_d.Z0;
//        assertTrue(instance.isParallel(l));
//        // Test 7
//        l = V3D_Line_d.Z_AXIS;
//        instance = V3D_Plane_d.X0;
//        assertTrue(instance.isParallel(l));
//        // Test 8
//        instance = V3D_Plane_d.Y0;
//        assertTrue(instance.isParallel(l));
//        // Test 9
//        instance = V3D_Plane_d.Z0;
//        assertFalse(instance.isParallel(l));
//    }
    
    /**
     * Test of isParallel method, of class V3D_Plane_d.
     */
    @Test
    public void testIsParallel_V3D_Line_d() {
        System.out.println("isParallel");
        V3D_Line_d l = V3D_Line_d.X_AXIS;
        V3D_Plane_d instance = V3D_Plane_d.Y0;
        assertTrue(instance.isParallel(l));
        // Test 2
        instance = V3D_Plane_d.Z0;
        assertTrue(instance.isParallel(l));
        // Test 3
        instance = V3D_Plane_d.X0;
        assertFalse(instance.isParallel(l));
        // Test 4
        l = V3D_Line_d.Y_AXIS;
        instance = V3D_Plane_d.X0;
        assertTrue(instance.isParallel(l));
        // Test 5
        instance = V3D_Plane_d.Y0;
        assertFalse(instance.isParallel(l));
        // Test 6
        instance = V3D_Plane_d.Z0;
        assertTrue(instance.isParallel(l));
        // Test 7
        l = V3D_Line_d.Z_AXIS;
        instance = V3D_Plane_d.X0;
        assertTrue(instance.isParallel(l));
        // Test 8
        instance = V3D_Plane_d.Y0;
        assertTrue(instance.isParallel(l));
        // Test 9
        instance = V3D_Plane_d.Z0;
        assertFalse(instance.isParallel(l));
    }

    /**
     * Test of getIntersect method, of class V3D_Plane_d. The following
     * can be used for creating test cases:
     * http://www.ambrsoft.com/TrigoCalc/Plan3D/Plane3D_.htm
     */
    @Test
    public void testGetIntersection_V3D_Plane_d() {
        System.out.println("getIntersect");
        double epsilon = 1d / 10000000d;
        V3D_Plane_d pl;
        V3D_Plane_d instance;
        V3D_Geometry_d expResult;
        V3D_Geometry_d result;
//        /**
//         * The following is from:
//         * https://www.microsoft.com/en-us/research/wp-content/uploads/2016/02/note.doc
//         * Two Planar patches.
//         */
//        pl = new V3D_Plane_d(
//                new V3D_Point_d(8d / 3d, -2d / 3d, 0d),
//                new V3D_Vector_d(2d, 8d, 0d));
//        instance = new V3D_Plane_d(
//                new V3D_Point_d(8d / 3d, 0d, -2d / 3d),
//                new V3D_Vector_d(2d, 0d, 8d));
//        // The vector of the line
//        V3D_Vector_d v = new V3D_Vector_d(1d / 4d, -1d / 16d, -1d / 16d);
//        // A point on both planes
//        V3D_Point_d pt = new V3D_Point_d(68d / 27d, -17d / 27d, -17d / 27d);
//        assertTrue(V3D_Plane_d.isCoplanar(pl, pt));
//        assertTrue(V3D_Plane_d.isCoplanar(instance, pt));
//        expResult = new V3D_Line_d(pt, v);
//        result = instance.getIntersect(pl);
//        assertTrue(((V3D_Line_d) result).v.isScalarMultiple(v));
//        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        /**
         * The following is from:
         * https://www.microsoft.com/en-us/research/wp-content/uploads/2016/02/note.doc
         * Simple.
         */
        pl = new V3D_Plane_d(new V3D_Point_d(env, 7, 11, 0), new V3D_Vector_d(0, 0, 3));
        instance = new V3D_Plane_d(new V3D_Point_d(env, 1, 0, 0), new V3D_Vector_d(5, 5, 0));
        // A point on both planes
        V3D_Point_d p = new V3D_Point_d(env, 0.5d, 0.5d, 0d);
        assertTrue(V3D_Plane_d.isCoplanar(epsilon, pl, p));
        assertTrue(V3D_Plane_d.isCoplanar(epsilon, instance, p));
        // A second point on both planes
        V3D_Point_d q = new V3D_Point_d(env, 1d, 0d, 0d);
        assertTrue(V3D_Plane_d.isCoplanar(epsilon, pl, q));
        assertTrue(V3D_Plane_d.isCoplanar(epsilon, instance, q));
        expResult = new V3D_Line_d(p, q);
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        
        // Test V3D_Plane_d.X0
        pl = V3D_Plane_d.X0;
        // Test 1
        instance = V3D_Plane_d.X0;
        expResult = V3D_Plane_d.X0;
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Plane_d) expResult).equalsIgnoreOrientation(
                (V3D_Plane_d) result));
        // Test 2
        instance = V3D_Plane_d.Y0;
        expResult = V3D_Line_d.Z_AXIS;
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 3
        instance = V3D_Plane_d.Z0;
        expResult = V3D_Line_d.Y_AXIS;
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));

        pl = new V3D_Plane_d(V3D_Plane_d.X0);
        pl.translate(P1P0P0); // X = 1 Plane
        // Test 1
        instance = V3D_Plane_d.X0;
        assertNull(instance.getIntersect(pl));
        // Test 2
        instance = V3D_Plane_d.Y0;
        expResult = new V3D_Line_d(V3D_Line_d.Z_AXIS);
        expResult.translate(P1P0P0);
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 3
        instance = V3D_Plane_d.Z0;
        expResult = new V3D_Line_d(V3D_Line_d.Y_AXIS);
        expResult.translate(P1P0P0);
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));

        // Test V3D_Plane_d.Y0
        pl = V3D_Plane_d.Y0;
        // Test 1
        instance = V3D_Plane_d.X0;
        expResult = V3D_Line_d.Z_AXIS;
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 2
        instance = V3D_Plane_d.Y0;
        expResult = V3D_Plane_d.Y0;
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Plane_d) expResult).equals((V3D_Plane_d) result));
        // Test 3
        instance = V3D_Plane_d.Z0;
        expResult = V3D_Line_d.X_AXIS;
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));

        // Test V3D_Plane_d.Z0
        pl = V3D_Plane_d.Z0;
        // Test 1
        instance = V3D_Plane_d.X0;
        expResult = V3D_Line_d.Y_AXIS;
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 2
        instance = V3D_Plane_d.Y0;
        expResult = V3D_Line_d.X_AXIS;
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 3
        instance = V3D_Plane_d.Z0;
        expResult = V3D_Plane_d.Z0;
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Plane_d) expResult).equals((V3D_Plane_d) result));
        pl = V3D_Plane_d.X0;
        // Test 4
        instance = new V3D_Plane_d(pN1P1N1, pP0P1P0, pP1P1N1); // y=1
        expResult = new V3D_Line_d(pP0P1P0, pP0P1P1);    // x=0, y=1
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 5
        instance = new V3D_Plane_d(pP1P0P1, pP0N1P1, pP0P0P1); // z=1
        expResult = new V3D_Line_d(pP0P1P1, pP0P0P1);    // x=0, z=1
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 6 to 9
        pl = V3D_Plane_d.Y0;
        // Test 6
        instance = V3D_Plane_d.X0;
        expResult = new V3D_Line_d(pP0P0N1, pP0P0P1);          // x=0, y=0
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 7
        instance = V3D_Plane_d.Z0;
        expResult = new V3D_Line_d(pP0P0P0, pP1P0P0);          // y=0, z=0
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 8
        instance = new V3D_Plane_d(pP1P0P0, pP1P1P0, pP1P0P1);       // x=1
        expResult = new V3D_Line_d(pP1P0N1, pP1P0P1);          // x=1, y=0
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 9
        instance = new V3D_Plane_d(pP0P1P1, pP1P1P1, pP0P0P1);       // z=1
        expResult = new V3D_Line_d(pP0P0P1, pP1P0P1);          // y=0, z=1
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 10 to 13
        pl = V3D_Plane_d.Z0;
        // Test 10
        instance = new V3D_Plane_d(env, P0P0P0, V3D_Vector_d.J, V3D_Vector_d.K); // x=0
        expResult = new V3D_Line_d(pP0N1P0, pP0P1P0);          // x=0, z=0
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 11
        instance = new V3D_Plane_d(env, V3D_Vector_d.I, P0P0P0, V3D_Vector_d.K); // y=0
        expResult = new V3D_Line_d(pN1P0P0, pP1P0P0);          // y=0, z=0
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 12
        instance = new V3D_Plane_d(pP1P0P0, pP1P1P1, pP1P0P1); // x=1
        expResult = new V3D_Line_d(pP1N1P0, pP1P1P0);    // x=1, z=0
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 13
        instance = new V3D_Plane_d(pP1P1P1, pP0P1P0, pP1P1P0); // y=1
        expResult = new V3D_Line_d(pN1P1P0, pP1P1P0);    // y=1, z=0
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 14 to 15
        pl = new V3D_Plane_d(pP1P0P0, pP1P1P1, pP1P0P1); // x=1
        // Test 14
        instance = new V3D_Plane_d(pN1P1N1, pP0P1P0, pP1P1N1); // y=1
        expResult = new V3D_Line_d(pP1P1P0, pP1P1P1);    // x=1, y=1
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 15
        instance = new V3D_Plane_d(pP1P0P1, pP0N1P1, pP0P0P1); // z=1
        expResult = new V3D_Line_d(pP1P1P1, pP1P0P1);    // x=1, z=1
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 16 to 17
        pl = new V3D_Plane_d(pN1P1N1, pP0P1P0, pP1P1N1); // y=1
        // Test 16
        instance = new V3D_Plane_d(pP1P0P0, pP1P1P1, pP1P0P1); // x=1
        expResult = new V3D_Line_d(pP1P1P0, pP1P1P1);    // x=1, y=1
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 17
        instance = new V3D_Plane_d(pP1P0P1, pP0N1P1, pP0P0P1); // z=1
        expResult = new V3D_Line_d(pP1P1P1, pP0P1P1);    // y=1, z=1
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 18 to 19
        pl = new V3D_Plane_d(pP1P0P1, pP0N1P1, pP0P0P1); // z=1
        // Test 18
        instance = new V3D_Plane_d(pP1P0P0, pP1P1P1, pP1P0P1); // x=1
        expResult = new V3D_Line_d(pP1P0P1, pP1P1P1);    // x=1, z=1
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 19
        instance = new V3D_Plane_d(pN1P1N1, pP0P1P0, pP1P1N1); // y=1
        expResult = new V3D_Line_d(pP1P1P1, pP0P1P1);    // y=1, z=1
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 20 to 21
        pl = new V3D_Plane_d(pN1P0P0, pN1P1P1, pN1P0P1); // x=-1
        // Test 20
        instance = new V3D_Plane_d(pN1P1N1, pP0P1P0, pP1P1N1); // y=1
        expResult = new V3D_Line_d(pN1P1P0, pN1P1P1);    // x=-1, y=1
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 21
        instance = new V3D_Plane_d(pP1P0P1, pP0N1P1, pP0P0P1); // z=1
        expResult = new V3D_Line_d(pN1P1P1, pN1P0P1);    // x=-1, z=1
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 22 to 23
        pl = new V3D_Plane_d(pN1N1N1, pP0N1P0, pP1N1N1); // y=-1
        // Test 22
        instance = new V3D_Plane_d(pP1P0P0, pP1P1P1, pP1P0P1); // x=1
        expResult = new V3D_Line_d(pP1N1P0, pP1N1P1);    // x=1, y=-1
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 23
        instance = new V3D_Plane_d(pP1P0P1, pP0N1P1, pP0P0P1); // z=1
        expResult = new V3D_Line_d(pP1N1P1, pP0N1P1);    // y=-1, z=1
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 24 to 25
        pl = new V3D_Plane_d(pP1P0N1, pP0N1N1, pP0P0N1); // z=-1
        // Test 24
        instance = new V3D_Plane_d(pP1P0P0, pP1P1P1, pP1P0P1); // x=1
        expResult = new V3D_Line_d(pP1P0N1, pP1P1N1);    // x=1, z=-1
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 25
        instance = new V3D_Plane_d(pN1P1N1, pP0P1P0, pP1P1N1); // y=1
        expResult = new V3D_Line_d(pP1P1N1, pP0P1N1);    // y=1, z=-1
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 26 to 27
        pl = new V3D_Plane_d(pN1P0P0, pN1P1P1, pN1P0P1); // x=-1
        // Test 26
        instance = new V3D_Plane_d(pN1N1N1, pP0N1P0, pP1N1N1); // y=-1
        expResult = new V3D_Line_d(pN1N1P0, pN1N1P1);    // x=-1, y=-1
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 27
        instance = new V3D_Plane_d(pP1P0N1, pP0N1N1, pP0P0N1); // z=-1
        expResult = new V3D_Line_d(pN1P1N1, pN1P0N1);    // x=-1, z=-1
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 28 to 29
        pl = new V3D_Plane_d(pN1N1N1, pP0N1P0, pP1N1N1); // y=-1
        // Test 28
        instance = new V3D_Plane_d(pN1P0P0, pN1P1P1, pN1P0P1); // x=-1
        expResult = new V3D_Line_d(pN1N1P0, pN1N1P1);    // x=-1, y=-1
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 29
        instance = new V3D_Plane_d(pP1P0N1, pP0N1N1, pP0P0N1); // z=-1
        expResult = new V3D_Line_d(pP1N1N1, pP0N1N1);    // y=-1, z=-1
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 30 to 31
        pl = new V3D_Plane_d(pP1P0N1, pP0N1N1, pP0P0N1); // z=-1
        // Test 30
        instance = new V3D_Plane_d(pN1P0P0, pN1P1P1, pN1P0P1); // x=-1
        expResult = new V3D_Line_d(pN1P0N1, pN1P1N1);    // x=-1, z=-1
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 31
        instance = new V3D_Plane_d(pN1N1N1, pP0N1P0, pP1N1N1); // y=-1
        expResult = new V3D_Line_d(pP1N1N1, pP0N1N1);    // y=-1, z=-1
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));

        // Test 32 to ?
        pl = new V3D_Plane_d(pN1P0P0, pN1P1P1, pN1P0P1); // x=-1
        // Test 32
        instance = new V3D_Plane_d(pN1N2N1, pP0N2P0, pP1N2N1); // y=-2
        expResult = new V3D_Line_d(pN1N2P0, pN1N2P1);    // x=-1, y=-2
        result = instance.getIntersect(pl);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
    }

    /**
     * Test of getIntersect method, of class V3D_Plane_d. The following
     * can be used for creating test cases:
     * http://www.ambrsoft.com/TrigoCalc/Plan3D/Plane3D_.htm
     */
    @Test
    public void testGetIntersection_V3D_Plane_d_double() {
        System.out.println("getIntersect");
        double epsilon = 1d / 10000000d;
        V3D_Plane_d pl;
        V3D_Plane_d instance;
        V3D_Geometry_d expResult;
        V3D_Geometry_d result;
        /**
         * The following is from:
         * https://www.microsoft.com/en-us/research/wp-content/uploads/2016/02/note.doc
         * Two Planar patches.
         */
        pl = new V3D_Plane_d(
                new V3D_Point_d(env, 8d / 3d, -2d / 3d, 0d),
                new V3D_Vector_d(2d, 8d, 0d));
        instance = new V3D_Plane_d(
                new V3D_Point_d(env, 8d / 3d, 0d, -2d / 3d),
                new V3D_Vector_d(2d, 0d, 8d));
        expResult = new V3D_Line_d( 
                new V3D_Point_d(env, 68d / 27d, -17d / 27d, -17d / 27d),
                new V3D_Point_d(env, 1d / 4d, -1d / 16d, -1d / 16d));
        result = instance.getIntersect(pl, epsilon);
//        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
//        /**
//         * The following is from:
//         * https://www.microsoft.com/en-us/research/wp-content/uploads/2016/02/note.doc
//         * Simple. Something is not right with this test which had worked at
//         * some stage... :(
//         */
//        oom = -10;
//        pl = new V3D_Plane_d(new V3D_Point_d(7, 11, 0), new V3D_Vector_d(0, 0, 3));
//        instance = new V3D_Plane_d(new V3D_Point_d(1, 0, 0), new V3D_Vector_d(5, 5, 0));
//        Math_BigRational half = P1.divide(2);
//        V3D_Point_d p2 = new V3D_Point_d(half, half, Math_BigRational.ZERO);
//        //V3D_Point_d p2 = new V3D_Point_d(0.5d, 0.5d, 0);
//        assertTrue(V3D_Plane_d.isCoplanar(pl, p2));
//        V3D_Vector_d v2 = new V3D_Vector_d(-15, 15, 0);
//        //assertTrue(V3D_Geometrics.isCoplanar(pl, p2.translate(v2)));
//        assertTrue(V3D_Plane_d.isCoplanar(pl, new V3D_Point_d(p2.offset, p2.rel.add(v2))));
//        //assertTrue(V3D_Geometrics.isCoplanar(pl, new V3D_Point_d(p2.offset.add(v2), p2.rel)));
//
//        //expResult = new V3D_Line_d(p2, v2);
//        expResult = new V3D_Line_d(p2.getVector(), v2);
//        //expResult = new V3D_Line_d(p2.offset, p2.rel, v2);
//
//        result = instance.getIntersect(pl, epsilon);
//        //System.out.println(result);
//        //System.out.println(expResult);
//        result = instance.getIntersect(pl, epsilon);
//        //assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
//        //assertTrue(expResult.equals(result));
//        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test V3D_Plane_d.X0
        pl = V3D_Plane_d.X0;
        // Test 1
        instance = V3D_Plane_d.X0;
        expResult = V3D_Plane_d.X0;
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Plane_d) expResult).equalsIgnoreOrientation(
                (V3D_Plane_d) result, epsilon));
        // Test 2
        instance = V3D_Plane_d.Y0;
        expResult = V3D_Line_d.Z_AXIS;
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 3
        instance = V3D_Plane_d.Z0;
        expResult = V3D_Line_d.Y_AXIS;
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));

        pl = new V3D_Plane_d(V3D_Plane_d.X0);
        pl.translate(P1P0P0); // X = 1 Plane
        // Test 1
        instance = V3D_Plane_d.X0;
        assertNull(instance.getIntersect(pl, epsilon));
        // Test 2
        instance = V3D_Plane_d.Y0;
        expResult = new V3D_Line_d(V3D_Line_d.Z_AXIS);
        expResult.translate(P1P0P0);
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 3
        instance = V3D_Plane_d.Z0;
        expResult = new V3D_Line_d(V3D_Line_d.Y_AXIS);
        expResult.translate(P1P0P0);
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));

        // Test V3D_Plane_d.Y0
        pl = V3D_Plane_d.Y0;
        // Test 1
        instance = V3D_Plane_d.X0;
        expResult = V3D_Line_d.Z_AXIS;
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 2
        instance = V3D_Plane_d.Y0;
        expResult = V3D_Plane_d.Y0;
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Plane_d) expResult).equals((V3D_Plane_d) result));
        // Test 3
        instance = V3D_Plane_d.Z0;
        expResult = V3D_Line_d.X_AXIS;
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));

        // Test V3D_Plane_d.Z0
        pl = V3D_Plane_d.Z0;
        // Test 1
        instance = V3D_Plane_d.X0;
        expResult = V3D_Line_d.Y_AXIS;
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 2
        instance = V3D_Plane_d.Y0;
        expResult = V3D_Line_d.X_AXIS;
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 3
        instance = V3D_Plane_d.Z0;
        expResult = V3D_Plane_d.Z0;
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Plane_d) expResult).equals((V3D_Plane_d) result));
        pl = V3D_Plane_d.X0;
        // Test 4
        instance = new V3D_Plane_d(pN1P1N1, pP0P1P0, pP1P1N1); // y=1
        expResult = new V3D_Line_d(pP0P1P0, pP0P1P1);    // x=0, y=1
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 5
        instance = new V3D_Plane_d(pP1P0P1, pP0N1P1, pP0P0P1); // z=1
        expResult = new V3D_Line_d(pP0P1P1, pP0P0P1);    // x=0, z=1
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 6 to 9
        pl = V3D_Plane_d.Y0;
        // Test 6
        instance = V3D_Plane_d.X0;
        expResult = new V3D_Line_d(pP0P0N1, pP0P0P1);          // x=0, y=0
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 7
        instance = V3D_Plane_d.Z0;
        expResult = new V3D_Line_d(pP0P0P0, pP1P0P0);          // y=0, z=0
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 8
        instance = new V3D_Plane_d(pP1P0P0, pP1P1P0, pP1P0P1);       // x=1
        expResult = new V3D_Line_d(pP1P0N1, pP1P0P1);          // x=1, y=0
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 9
        instance = new V3D_Plane_d(pP0P1P1, pP1P1P1, pP0P0P1);       // z=1
        expResult = new V3D_Line_d(pP0P0P1, pP1P0P1);          // y=0, z=1
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 10 to 13
        pl = V3D_Plane_d.Z0;
        // Test 10
        instance = new V3D_Plane_d(env, P0P0P0, V3D_Vector_d.J, V3D_Vector_d.K); // x=0
        expResult = new V3D_Line_d(pP0N1P0, pP0P1P0);          // x=0, z=0
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 11
        instance = new V3D_Plane_d(env, V3D_Vector_d.I, P0P0P0, V3D_Vector_d.K); // y=0
        expResult = new V3D_Line_d(pN1P0P0, pP1P0P0);          // y=0, z=0
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 12
        instance = new V3D_Plane_d(pP1P0P0, pP1P1P1, pP1P0P1); // x=1
        expResult = new V3D_Line_d(pP1N1P0, pP1P1P0);    // x=1, z=0
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 13
        instance = new V3D_Plane_d(pP1P1P1, pP0P1P0, pP1P1P0); // y=1
        expResult = new V3D_Line_d(pN1P1P0, pP1P1P0);    // y=1, z=0
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 14 to 15
        pl = new V3D_Plane_d(pP1P0P0, pP1P1P1, pP1P0P1); // x=1
        // Test 14
        instance = new V3D_Plane_d(pN1P1N1, pP0P1P0, pP1P1N1); // y=1
        expResult = new V3D_Line_d(pP1P1P0, pP1P1P1);    // x=1, y=1
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 15
        instance = new V3D_Plane_d(pP1P0P1, pP0N1P1, pP0P0P1); // z=1
        expResult = new V3D_Line_d(pP1P1P1, pP1P0P1);    // x=1, z=1
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 16 to 17
        pl = new V3D_Plane_d(pN1P1N1, pP0P1P0, pP1P1N1); // y=1
        // Test 16
        instance = new V3D_Plane_d(pP1P0P0, pP1P1P1, pP1P0P1); // x=1
        expResult = new V3D_Line_d(pP1P1P0, pP1P1P1);    // x=1, y=1
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 17
        instance = new V3D_Plane_d(pP1P0P1, pP0N1P1, pP0P0P1); // z=1
        expResult = new V3D_Line_d(pP1P1P1, pP0P1P1);    // y=1, z=1
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 18 to 19
        pl = new V3D_Plane_d(pP1P0P1, pP0N1P1, pP0P0P1); // z=1
        // Test 18
        instance = new V3D_Plane_d(pP1P0P0, pP1P1P1, pP1P0P1); // x=1
        expResult = new V3D_Line_d(pP1P0P1, pP1P1P1);    // x=1, z=1
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 19
        instance = new V3D_Plane_d(pN1P1N1, pP0P1P0, pP1P1N1); // y=1
        expResult = new V3D_Line_d(pP1P1P1, pP0P1P1);    // y=1, z=1
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 20 to 21
        pl = new V3D_Plane_d(pN1P0P0, pN1P1P1, pN1P0P1); // x=-1
        // Test 20
        instance = new V3D_Plane_d(pN1P1N1, pP0P1P0, pP1P1N1); // y=1
        expResult = new V3D_Line_d(pN1P1P0, pN1P1P1);    // x=-1, y=1
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 21
        instance = new V3D_Plane_d(pP1P0P1, pP0N1P1, pP0P0P1); // z=1
        expResult = new V3D_Line_d(pN1P1P1, pN1P0P1);    // x=-1, z=1
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 22 to 23
        pl = new V3D_Plane_d(pN1N1N1, pP0N1P0, pP1N1N1); // y=-1
        // Test 22
        instance = new V3D_Plane_d(pP1P0P0, pP1P1P1, pP1P0P1); // x=1
        expResult = new V3D_Line_d(pP1N1P0, pP1N1P1);    // x=1, y=-1
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 23
        instance = new V3D_Plane_d(pP1P0P1, pP0N1P1, pP0P0P1); // z=1
        expResult = new V3D_Line_d(pP1N1P1, pP0N1P1);    // y=-1, z=1
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 24 to 25
        pl = new V3D_Plane_d(pP1P0N1, pP0N1N1, pP0P0N1); // z=-1
        // Test 24
        instance = new V3D_Plane_d(pP1P0P0, pP1P1P1, pP1P0P1); // x=1
        expResult = new V3D_Line_d(pP1P0N1, pP1P1N1);    // x=1, z=-1
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 25
        instance = new V3D_Plane_d(pN1P1N1, pP0P1P0, pP1P1N1); // y=1
        expResult = new V3D_Line_d(pP1P1N1, pP0P1N1);    // y=1, z=-1
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 26 to 27
        pl = new V3D_Plane_d(pN1P0P0, pN1P1P1, pN1P0P1); // x=-1
        // Test 26
        instance = new V3D_Plane_d(pN1N1N1, pP0N1P0, pP1N1N1); // y=-1
        expResult = new V3D_Line_d(pN1N1P0, pN1N1P1);    // x=-1, y=-1
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 27
        instance = new V3D_Plane_d(pP1P0N1, pP0N1N1, pP0P0N1); // z=-1
        expResult = new V3D_Line_d(pN1P1N1, pN1P0N1);    // x=-1, z=-1
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 28 to 29
        pl = new V3D_Plane_d(pN1N1N1, pP0N1P0, pP1N1N1); // y=-1
        // Test 28
        instance = new V3D_Plane_d(pN1P0P0, pN1P1P1, pN1P0P1); // x=-1
        expResult = new V3D_Line_d(pN1N1P0, pN1N1P1);    // x=-1, y=-1
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 29
        instance = new V3D_Plane_d(pP1P0N1, pP0N1N1, pP0P0N1); // z=-1
        expResult = new V3D_Line_d(pP1N1N1, pP0N1N1);    // y=-1, z=-1
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 30 to 31
        pl = new V3D_Plane_d(pP1P0N1, pP0N1N1, pP0P0N1); // z=-1
        // Test 30
        instance = new V3D_Plane_d(pN1P0P0, pN1P1P1, pN1P0P1); // x=-1
        expResult = new V3D_Line_d(pN1P0N1, pN1P1N1);    // x=-1, z=-1
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 31
        instance = new V3D_Plane_d(pN1N1N1, pP0N1P0, pP1N1N1); // y=-1
        expResult = new V3D_Line_d(pP1N1N1, pP0N1N1);    // y=-1, z=-1
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));

        // Test 32 to ?
        pl = new V3D_Plane_d(pN1P0P0, pN1P1P1, pN1P0P1); // x=-1
        // Test 32
        instance = new V3D_Plane_d(pN1N2N1, pP0N2P0, pP1N2N1); // y=-2
        expResult = new V3D_Line_d(pN1N2P0, pN1N2P1);    // x=-1, y=-2
        result = instance.getIntersect(pl, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
    }
    
    /**
     * Test of getIntersect method, of class V3D_Plane_d.
     */
    @Test
    public void testGetIntersection_V3D_Plane_d_V3D_Plane_d() {
        double epsilon = 1d / 10000000d;
        V3D_Plane_d instance;
        V3D_Plane_d pl1;
        V3D_Plane_d pl2;
        // Test 1
        instance = V3D_Plane_d.X0;
        pl1 = V3D_Plane_d.Y0;
        pl2 = V3D_Plane_d.Z0;
        V3D_Point_d expResult = V3D_Point_d.ORIGIN;
        V3D_Point_d result = (V3D_Point_d) instance.getIntersect(pl1, pl2, epsilon);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V3D_Plane_d(V3D_Plane_d.X0);
        pl1 = V3D_Plane_d.Y0;
        pl2 = V3D_Plane_d.Z0;
        instance.translate(V3D_Vector_d.I); // X = 1 Plane
        expResult = new V3D_Point_d(V3D_Point_d.ORIGIN);
        expResult.translate(V3D_Vector_d.I);
        result = (V3D_Point_d) instance.getIntersect(pl1, pl2, epsilon);
        assertTrue(expResult.equals(result));
        // Test 3
        instance = new V3D_Plane_d(V3D_Plane_d.X0);
        pl1 = V3D_Plane_d.Y0;
        pl2 = V3D_Plane_d.Z0;
        instance.translate(V3D_Vector_d.J);
        expResult = new V3D_Point_d(pP0P0P0);
        result = (V3D_Point_d) instance.getIntersect(pl1, pl2, epsilon);
        assertTrue(expResult.equals(result));
        // Test 4
        instance = new V3D_Plane_d(V3D_Plane_d.X0);
        pl1 = V3D_Plane_d.Y0;
        pl2 = V3D_Plane_d.Z0;
        instance.translate(V3D_Vector_d.K);
        expResult = new V3D_Point_d(pP0P0P0);
        result = (V3D_Point_d) instance.getIntersect(pl1, pl2, epsilon);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getIntersect method, of class V3D_Plane_d.
     */
    @Test
    public void testGetIntersection_V3D_Line_d() {
        System.out.println("getIntersect");
        V3D_Line_d l;
        V3D_Plane_d instance;
        V3D_Geometry_d expResult;
        V3D_Geometry_d result;
        // Test 1-3 axis with orthoganol plane through origin.
        // Test 1
        l = V3D_Line_d.X_AXIS;
        instance = V3D_Plane_d.X0;
        expResult = pP0P0P0;
        result = instance.getIntersect(l);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 2
        l = V3D_Line_d.Y_AXIS;
        instance = V3D_Plane_d.Y0;
        expResult = pP0P0P0;
        result = instance.getIntersect(l);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 3
        l = V3D_Line_d.Z_AXIS;
        instance = V3D_Plane_d.Z0;
        expResult = pP0P0P0;
        result = instance.getIntersect(l);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 4-6 axis with orthoganol plane not through origin.
        // Test 4
        l = V3D_Line_d.X_AXIS;
        instance = new V3D_Plane_d(V3D_Plane_d.X0);
        instance.translate(P1P0P0);
        expResult = pP1P0P0;
        result = instance.getIntersect(l);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 5
        l = V3D_Line_d.Y_AXIS;
        instance = new V3D_Plane_d(V3D_Plane_d.Y0);
        instance.translate(P0P1P0);
        expResult = pP0P1P0;
        result = instance.getIntersect(l);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 6
        l = V3D_Line_d.Z_AXIS;
        instance = new V3D_Plane_d(V3D_Plane_d.Z0);
        instance.translate(P0P0P1);
        expResult = pP0P0P1;
        result = instance.getIntersect(l);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 7
        // line
        // x = t, y = 2 + 3t, z = t
        // points (0, 2, 0), (1, 5, 1) 
        l = new V3D_Line_d(pP0P2P0, new V3D_Point_d(env, 1d, 5d, 1d));
        // plane
        // 2x + y − 4z = 4
        // points (0, 0, -1), (0, 4, 0), (2, 0, 0)
        instance = new V3D_Plane_d(env, P0P0N1, new V3D_Vector_d(0d, 4d, 0d), P2P0P0);
        // (2, 8, 2)
        expResult = new V3D_Point_d(env, 2d, 8d, 2d);
        result = instance.getIntersect(l);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 9
        // line
        // x = t, y = 2 + 3t, z = t
        // points (0, 2, 0), (1, 5, 1) 
        l = new V3D_Line_d(pP0P2P0, new V3D_Point_d(env, 1d, 5d, 1d));
        // plane
        // 2x + y − 4z = 4
        // points (0, 0, -1), (0, 4, 0), (2, 0, 0)
        instance = new V3D_Plane_d(env, new V3D_Vector_d(0d, 0d, -1d),
                new V3D_Vector_d(0d, 4d, 0d), new V3D_Vector_d(2d, 0d, 0d));
        // (2, 8, 2)
        expResult = new V3D_Point_d(env, 2d, 8d, 2d);
        result = instance.getIntersect(l);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 10
        // line
        // x = 0, y = 0, z = t
        // points (0, 0, 0), (0, 0, 1) 
        l = new V3D_Line_d(pP0P0P0, pP0P0P1);
        // plane
        // points (0, 0, -1), (0, 4, 0), (2, 0, 0)
        instance = new V3D_Plane_d(env, P0P0P2, P1P0P2, P0P1P2);
        expResult = pP0P0P2;
        result = instance.getIntersect(l);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 11
        l = V3D_Line_d.X_AXIS;
        instance = V3D_Plane_d.X0;
        expResult = V3D_Point_d.ORIGIN;
        result = instance.getIntersect(l);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 12
        l = V3D_Line_d.X_AXIS;
        instance = V3D_Plane_d.Y0;
        expResult = V3D_Line_d.X_AXIS;
        result = instance.getIntersect(l);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 13
        l = V3D_Line_d.X_AXIS;
        instance = V3D_Plane_d.Z0;
        expResult = V3D_Line_d.X_AXIS;
        result = instance.getIntersect(l);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 14
        l = V3D_Line_d.Y_AXIS;
        instance = V3D_Plane_d.X0;
        expResult = V3D_Line_d.Y_AXIS;
        result = instance.getIntersect(l);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 15
        l = V3D_Line_d.Y_AXIS;
        instance = V3D_Plane_d.Y0;
        expResult = V3D_Point_d.ORIGIN;
        result = instance.getIntersect(l);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 16
        l = V3D_Line_d.Y_AXIS;
        instance = V3D_Plane_d.Z0;
        expResult = V3D_Line_d.Y_AXIS;
        result = instance.getIntersect(l);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 17
        l = V3D_Line_d.Z_AXIS;
        instance = V3D_Plane_d.X0;
        expResult = V3D_Line_d.Z_AXIS;
        result = instance.getIntersect(l);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 18
        l = V3D_Line_d.Z_AXIS;
        instance = V3D_Plane_d.Y0;
        expResult = V3D_Line_d.Z_AXIS;
        result = instance.getIntersect(l);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 19
        l = V3D_Line_d.Z_AXIS;
        instance = V3D_Plane_d.Z0;
        expResult = V3D_Point_d.ORIGIN;
        result = instance.getIntersect(l);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
    }

    /**
     * Test of getIntersect method, of class V3D_Plane_d.
     */
    @Test
    public void testGetIntersection_V3D_Line_d_double() {
        System.out.println("getIntersect");
        double epsilon = 1d / 10000000d;
        V3D_Line_d l;
        V3D_Plane_d instance;
        V3D_Geometry_d expResult;
        V3D_Geometry_d result;
        // Test 1-3 axis with orthoganol plane through origin.
        // Test 1
        l = V3D_Line_d.X_AXIS;
        instance = V3D_Plane_d.X0;
        expResult = pP0P0P0;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 2
        l = V3D_Line_d.Y_AXIS;
        instance = V3D_Plane_d.Y0;
        expResult = pP0P0P0;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 3
        l = V3D_Line_d.Z_AXIS;
        instance = V3D_Plane_d.Z0;
        expResult = pP0P0P0;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 4-6 axis with orthoganol plane not through origin.
        // Test 4
        l = V3D_Line_d.X_AXIS;
        instance = new V3D_Plane_d(V3D_Plane_d.X0);
        instance.translate(P1P0P0);
        expResult = pP1P0P0;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 5
        l = V3D_Line_d.Y_AXIS;
        instance = new V3D_Plane_d(V3D_Plane_d.Y0);
        instance.translate(P0P1P0);
        expResult = pP0P1P0;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 6
        l = V3D_Line_d.Z_AXIS;
        instance = new V3D_Plane_d(V3D_Plane_d.Z0);
        instance.translate(P0P0P1);
        expResult = pP0P0P1;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 7
        // line
        // x = t, y = 2 + 3t, z = t
        // points (0, 2, 0), (1, 5, 1) 
        l = new V3D_Line_d(pP0P2P0, new V3D_Point_d(env, 1d, 5d, 1d));
        // plane
        // 2x + y − 4z = 4
        // points (0, 0, -1), (0, 4, 0), (2, 0, 0)
        instance = new V3D_Plane_d(env, P0P0N1, new V3D_Vector_d(0d, 4d, 0d), P2P0P0);
        // (2, 8, 2)
        expResult = new V3D_Point_d(env, 2d, 8d, 2d);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 9
        // line
        // x = t, y = 2 + 3t, z = t
        // points (0, 2, 0), (1, 5, 1) 
        l = new V3D_Line_d(pP0P2P0, new V3D_Point_d(env, 1d, 5d, 1d));
        // plane
        // 2x + y − 4z = 4
        // points (0, 0, -1), (0, 4, 0), (2, 0, 0)
        instance = new V3D_Plane_d(env, new V3D_Vector_d(0d, 0d, -1d),
                new V3D_Vector_d(0d, 4d, 0d), new V3D_Vector_d(2d, 0d, 0d));
        // (2, 8, 2)
        expResult = new V3D_Point_d(env, 2d, 8d, 2d);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 10
        // line
        // x = 0, y = 0, z = t
        // points (0, 0, 0), (0, 0, 1) 
        l = new V3D_Line_d(pP0P0P0, pP0P0P1);
        // plane
        // points (0, 0, -1), (0, 4, 0), (2, 0, 0)
        instance = new V3D_Plane_d(env, P0P0P2, P1P0P2, P0P1P2);
        expResult = pP0P0P2;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 11
        l = V3D_Line_d.X_AXIS;
        instance = V3D_Plane_d.X0;
        expResult = V3D_Point_d.ORIGIN;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 12
        l = V3D_Line_d.X_AXIS;
        instance = V3D_Plane_d.Y0;
        expResult = V3D_Line_d.X_AXIS;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 13
        l = V3D_Line_d.X_AXIS;
        instance = V3D_Plane_d.Z0;
        expResult = V3D_Line_d.X_AXIS;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 14
        l = V3D_Line_d.Y_AXIS;
        instance = V3D_Plane_d.X0;
        expResult = V3D_Line_d.Y_AXIS;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 15
        l = V3D_Line_d.Y_AXIS;
        instance = V3D_Plane_d.Y0;
        expResult = V3D_Point_d.ORIGIN;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 16
        l = V3D_Line_d.Y_AXIS;
        instance = V3D_Plane_d.Z0;
        expResult = V3D_Line_d.Y_AXIS;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 17
        l = V3D_Line_d.Z_AXIS;
        instance = V3D_Plane_d.X0;
        expResult = V3D_Line_d.Z_AXIS;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 18
        l = V3D_Line_d.Z_AXIS;
        instance = V3D_Plane_d.Y0;
        expResult = V3D_Line_d.Z_AXIS;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Line_d) expResult).equals((V3D_Line_d) result));
        // Test 19
        l = V3D_Line_d.Z_AXIS;
        instance = V3D_Plane_d.Z0;
        expResult = V3D_Point_d.ORIGIN;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
    }

    /**
     * Test of isOnPlane method, of class V3D_Plane_d.
     */
    @Test
    public void testIsOnPlane() {
        System.out.println("isOnPlane");
        V3D_Line_d l = new V3D_Line_d(pP0P0P0, pP1P0P0);
        V3D_Plane_d instance = new V3D_Plane_d(env, P0P0P0, P1P0P0, P1P1P0);
        assertTrue(instance.isOnPlane(l));
        // Test 2
        l = new V3D_Line_d(pP0P0P0, pP1P1P0);
        assertTrue(instance.isOnPlane(l));
    }

    /**
     * Test of getIntersect method, of class V3D_Plane_d.
     */
    @Test
    public void testGetIntersection_V3D_Ray_d() {
        System.out.println("getIntersect");
        double epsilon = 1d / 10000000d;
        V3D_Ray_d instance;
        V3D_Plane_d p;
        V3D_Geometry_d expResult;
        V3D_Geometry_d result;
        // Test 8
        // https://stackoverflow.com/questions/15102738/ray-plane-intersection-inaccurate-results-rounding-errors
        V3D_Vector_d n = new V3D_Vector_d(0, 1, 0);
        p = new V3D_Plane_d(pP0P0P0, n);
        double x1 = 20.818802d;
        double y1 = 27.240383;
        double z1 = 15.124892;
        double x2 = 21.947229;
        double y2 = -66.788452;
        double z2 = -18.894285;
        instance = new V3D_Ray_d(
                new V3D_Point_d(env, x1, y1, z1), 
                new V3D_Point_d(env, x2, y2, z2));
        expResult = new V3D_Point_d(env, 21.145710056103653, 0d, 5.269453390930867d);
        //expResult = new V3D_Point_d(env, 21.145710d, 0.000002d, 5.269455d);
        result = p.getIntersect(instance, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result, epsilon));
    }

    /**
     * Test of getIntersect method, of class V3D_Plane_d.
     */
    @Test
    public void testGetIntersection_V3D_LineSegment_d() {
        System.out.println("getIntersect");
        double epsilon = 1d / 10000000d;
        V3D_LineSegment_d l;
        V3D_Plane_d instance;
        V3D_Geometry_d expResult;
        V3D_Geometry_d result;
        // Test 1-3 part of axis with orthoganol plane through origin.
        // Test 1
        l = new V3D_LineSegment_d(pN1P0P0, pP1P0P0);
        instance = V3D_Plane_d.X0;
        expResult = pP0P0P0;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 2
        l = new V3D_LineSegment_d(pP0N1P0, pP0P1P0);
        instance = V3D_Plane_d.Y0;
        expResult = pP0P0P0;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 3
        l = new V3D_LineSegment_d(pP0P0N1, pP0P0P1);
        instance = V3D_Plane_d.Z0;
        expResult = pP0P0P0;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 4-6 part of axis with orthoganol plane not through origin.
        // Test 4
        l = new V3D_LineSegment_d(pN1P0P0, pP1P0P0);
        instance = new V3D_Plane_d(V3D_Plane_d.X0);
        instance.translate(P1P0P0);
        expResult = pP1P0P0;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 5
        l = new V3D_LineSegment_d(pP0N1P0, pP0P1P0);
        instance = new V3D_Plane_d(V3D_Plane_d.Y0);
        instance.translate(P0P1P0);
        expResult = pP0P1P0;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 6
        l = new V3D_LineSegment_d(pP0P0N1, pP0P0P1);
        instance = new V3D_Plane_d(V3D_Plane_d.Z0);
        instance.translate(P0P0P1);
        expResult = pP0P0P1;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 7
        l = new V3D_LineSegment_d(pP0P2P0, new V3D_Point_d(env, 1d, 5d, 1d));
        instance = new V3D_Plane_d(env, P0P0N1, new V3D_Vector_d(0d, 4d, 0d),
                new V3D_Vector_d(2d, 0d, 0d));
        assertNull(instance.getIntersect(l, epsilon));
        // Test 8
        epsilon = 1d / 10000d;
        l = new V3D_LineSegment_d(pP0P2P0, new V3D_Point_d(env, 2d, 8d, 2d));
        result = instance.getIntersect(l, epsilon);
        expResult = new V3D_Point_d(env, 2d, 8d, 2d);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 9
        // line
        // x = t, y = 2 + 3t, z = t
        // points (0, 2, 0), (1, 5, 1) 
        l = new V3D_LineSegment_d(pP0P2P0, new V3D_Point_d(env, 1d, 5d, 1d));
        // plane
        // 2x + y − 4z = 4
        // points (0, 0, -1), (0, 4, 0), (2, 0, 0)
        instance = new V3D_Plane_d(env, P0P0N1, new V3D_Vector_d(0d, 4d, 0d), P2P0P0);
        // (2, 8, 2)
        expResult = new V3D_Point_d(env, 2d, 8d, 2d);
        result = instance.getIntersect(l, epsilon);
        assertNotEquals(expResult, result);
        l = new V3D_LineSegment_d(pP0P2P0, new V3D_Point_d(env, 2d, 8d, 2d));
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 10
        // line
        // x = 0, y = 0, z = t
        // points (0, 0, 0), (0, 0, 1) 
        l = new V3D_LineSegment_d(pP0P0P0, pP0P0P1);
        // plane
        // points (0, 0, -1), (0, 4, 0), (2, 0, 0)
        instance = new V3D_Plane_d(env, P0P0P2, P1P0P2, P0P1P2);
        result = instance.getIntersect(l, epsilon);
        assertNull(result);
        l = new V3D_LineSegment_d(pP0P0P0, new V3D_Point_d(env, 0d, 0d, 4d));
        expResult = pP0P0P2;
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals((V3D_Point_d) result));
        // Test 11
        l = new V3D_LineSegment_d(new V3D_Point_d(env, 4d, 4d, 0d), 
                new V3D_Point_d(env, 5d, 5d, 0d));
        instance = new V3D_Plane_d(new V3D_Point_d(env, 6d, 6d, 0d), 
                new V3D_Vector_d(30d, 30d, 0d));
        result = instance.getIntersect(l, epsilon);
        assertNull(result);
    }

    /**
     * Test of isEnvelopeIntersectedBy method, of class V3D_Plane_d.
     */
    @Test
    public void testIsEnvelopeIntersectedBy() {
        System.out.println("isEnvelopeIntersectedBy");
        // No test.
    }

    /**
     * Test of getAsMatrix method, of class V3D_Plane_d.
     */
    @Test
    public void testGetAsMatrix() {
        System.out.println("getAsMatrix");
        V3D_Plane_d instance = V3D_Plane_d.X0;
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
        Math_Matrix_Double result = instance.getAsMatrix();
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
     * Test of getDistanceSquared method, of class V3D_Plane_d.
     */
    @Test
    public void testGetDistanceSquared() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V3D_Plane_d p = V3D_Plane_d.X0;
        V3D_Plane_d instance = V3D_Plane_d.X0;
        double expResult = 0d;
        double result = instance.getDistanceSquared(p, epsilon);
        assertTrue(expResult == result);
        // Test 2
        V3D_Vector_d v = V3D_Vector_d.I;
        instance = new V3D_Plane_d(V3D_Plane_d.X0);
        instance.translate(v);
        expResult = 1d;
        result = instance.getDistanceSquared(p, epsilon);
        assertTrue(expResult == result);
        // Test 3
        v = V3D_Vector_d.J;
        instance = new V3D_Plane_d(V3D_Plane_d.X0);
        instance.translate(v);
        expResult = 0d;
        result = instance.getDistanceSquared(p, epsilon);
        assertTrue(expResult == result);
        // Test 4
        v = V3D_Vector_d.K;
        instance = new V3D_Plane_d(V3D_Plane_d.X0);
        instance.translate(v);
        expResult = 0d;
        result = instance.getDistanceSquared(p, epsilon);
        assertTrue(expResult == result);
    }

    /**
     * Test of isCoincident method, of class V3D_Plane_d.
     */
    @Test
    public void testIsCoincident() {
        // No test as covered by other tests.
    }

    /**
     * Test of getDistance method, of class V3D_Plane_d.
     */
    @Test
    public void testGetDistance_V3D_Point_d() {
        System.out.println("getDistance");
        V3D_Point_d p = new V3D_Point_d(env, 5d, 0d, 0d);
        V3D_Plane_d instance = new V3D_Plane_d(env, P0P0P0, P0P0P1, P0P1P1);
        double expResult = 5d;
        double result = instance.getDistance(p);
        assertTrue(expResult == result);
        // Test 2
        p = new V3D_Point_d(env, 5d, 10d, 0d);
        result = instance.getDistance(p);
        assertTrue(expResult == result);
    }

    /**
     * Test of getDistance method, of class V3D_Plane_d.
     */
    @Test
    public void testGetDistance_V3D_LineSegment_d() {
        System.out.println("getDistance");
        V3D_LineSegment_d l = new V3D_LineSegment_d(env, 
                new V3D_Vector_d(10d, 1d, 1d),
                new V3D_Vector_d(100d, 1d, 1d));
        V3D_Plane_d instance = V3D_Plane_d.X0;
        double expResult = 10d;
        double epsilon = 1d / 1000000d;
        double result = instance.getDistance(l, epsilon);
        assertTrue(expResult == result);
    }

    /**
     * Test of toString method, of class V3D_Plane_d.
     */
    @Test
    public void testToString_String() {
        System.out.println("toString");
        String pad = "";
        V3D_Plane_d instance = V3D_Plane_d.X0;
        String expResult = """
                           V3D_Plane_d
                           (
                            offset=V3D_Vector_d(dx=0.0, dy=0.0, dz=0.0),
                            pv=V3D_Vector_d(dx=0.0, dy=0.0, dz=0.0),
                            n=V3D_Vector_d(dx=1.0, dy=0.0, dz=0.0)
                           )""";
        String result = instance.toString(pad);
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getPAsVector method, of class V3D_Plane_d.
     */
    @Test
    public void testGetPV() {
        System.out.println("getPV");
        V3D_Plane_d instance = V3D_Plane_d.X0;
        assertTrue(instance.getPV().getDotProduct(instance.getN()) == 0d);
        // Test 2
        instance = V3D_Plane_d.Y0;
        assertTrue(instance.getPV().getDotProduct(instance.getN()) == 0d);
        // Test 3
        instance = V3D_Plane_d.Z0;
        assertTrue(instance.getPV().getDotProduct(instance.getN()) == 0d);
    }

    /**
     * Test of getP method, of class V3D_Plane_d.
     */
    @Test
    public void testGetP() {
        System.out.println("getP");
        V3D_Plane_d instance = V3D_Plane_d.X0;
        V3D_Point_d expResult = V3D_Point_d.ORIGIN;
        V3D_Point_d result = instance.getP();
        assertTrue((expResult).equals(result));
    }

    /**
     * Test of rotate method, of class V3D_Plane_d.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        double epsilon = 1d / 10000000d;
        double Pi = Math.PI;
        V3D_Ray_d xaxis = new V3D_Ray_d(pP0P0P0, V3D_Vector_d.I);
        V3D_Ray_d yaxis = new V3D_Ray_d(pP0P0P0, V3D_Vector_d.J);
        V3D_Ray_d zaxis = new V3D_Ray_d(pP0P0P0, V3D_Vector_d.K);
        // Test 1;
        double theta = Pi / 2d;
        V3D_Plane_d instance = new V3D_Plane_d(V3D_Plane_d.X0);
        instance = instance.rotate(xaxis, xaxis.l.v, theta, epsilon);
        assertTrue(V3D_Plane_d.X0.equalsIgnoreOrientation(instance, epsilon));
        // Test 2
        instance = new V3D_Plane_d(V3D_Plane_d.X0);
        instance = instance.rotate(yaxis, yaxis.l.v, theta, epsilon);
        assertTrue(V3D_Plane_d.Z0.equalsIgnoreOrientation(instance, epsilon));
        // Test 3
        instance = new V3D_Plane_d(V3D_Plane_d.X0);
        instance = instance.rotate(zaxis, zaxis.l.v, theta, epsilon);
        assertTrue(V3D_Plane_d.Y0.equalsIgnoreOrientation(instance, epsilon));
        // Test 4
        theta = Pi;
        instance = new V3D_Plane_d(env, P1P0P0, P0P0P0, P0P2P0, P0P2P2);
        instance = instance.rotate(xaxis, xaxis.l.v, theta, epsilon);
        assertTrue(new V3D_Plane_d(env, P1P0P0, P0P0P0, P0P2P0, P0P2P2).equalsIgnoreOrientation(instance, epsilon));
        // Test 5
        theta = Pi;
        instance = new V3D_Plane_d(env, P1P0P0, P0P0P0, P0P2P0, P0P2P2);
        instance = instance.rotate(yaxis, yaxis.l.v, theta, epsilon);
        V3D_Plane_d expResult = new V3D_Plane_d(env, N1P0P0, P0P0P0, P0P2P0, P0P2N2);
        assertTrue(expResult.equalsIgnoreOrientation(instance, epsilon));
        // Test 6
        theta = Pi;
        instance = new V3D_Plane_d(env, P1P0P0, P0P0P0, P0P2P0, P0P2P2);
        instance = instance.rotate(zaxis, zaxis.l.v, theta, epsilon);
        expResult = new V3D_Plane_d(env, N1P0P0, P0P0P0, P0P2P0, P0P2P2);
        assertTrue(expResult.equalsIgnoreOrientation(instance, epsilon));
    }

    /**
     * Test constructor method, of class V3D_Plane_d.
     */
    @Test
    public void testConstructor() {
        System.out.println("testConstructors");
        double epsilon = 1d / 10000000d;
        V3D_Plane_d pl0 = V3D_Plane_d.X0;
        V3D_Plane_d pl1 = new V3D_Plane_d(env, V3D_Vector_d.I, pl0,
                epsilon);
        V3D_Plane_d pl2 = new V3D_Plane_d(env, V3D_Vector_d.I, N1P0P0,
                N1P1P0, N1P0P1);
        assertTrue(pl0.equalsIgnoreOrientation(pl1, epsilon));
        assertTrue(pl0.equalsIgnoreOrientation(pl2, epsilon));
        assertTrue(pl1.equalsIgnoreOrientation(pl2, epsilon));
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Plane_d.
     */
    @Test
    public void testGetDistanceSquared_V3D_Point_d_int() {
        System.out.println("getDistanceSquared");
        V3D_Point_d pt;
        V3D_Plane_d instance;
        double expResult;
        double result;
        double epsilon = 1d / 10000000d;
        // Test 1
        pt = pP1P0P0;
        instance = V3D_Plane_d.X0;
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
     * Test of getDistanceSquared method, of class V3D_Plane_d covered by
     * {@link #testGetDistanceSquared_V3D_Point_d_int()}.
     */
    @Test
    public void testGetDistanceSquared_3args() {
        System.out.println("getDistanceSquared");
    }

    /**
     * Test of getDistance method, of class V3D_Plane_d covered by
     * {@link #testGetDistanceSquared_V3D_Plane_d_int()}.
     */
    @Test
    public void testGetDistance_V3D_Plane_d_int() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Plane_d.
     */
    @Test
    public void testGetDistanceSquared_V3D_Plane_d_int() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V3D_Plane_d pl;
        V3D_Plane_d instance;
        double expResult;
        double result;
        // Test 1
        pl = V3D_Plane_d.X0;
        instance = V3D_Plane_d.Y0;
        expResult = 0d;
        result = instance.getDistanceSquared(pl, epsilon);
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_Plane_d(V3D_Plane_d.X0);
        instance.translate(P1P0P0);
        expResult = 1d;
        result = instance.getDistanceSquared(pl, epsilon);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Plane_d.
     */
    @Test
    public void testGetDistanceSquared_V3D_Line_d() {
        System.out.println("getDistanceSquared");
        V3D_Line_d l;
        V3D_Plane_d instance;
        double expResult;
        double result;
        // Test 1
        l = V3D_Line_d.X_AXIS;
        instance = V3D_Plane_d.Y0;
        expResult = 0d;
        result = instance.getDistanceSquared(l);
        assertEquals(expResult, result);
        // Test 2
        double epsilon = 1d / 10000000d;
        instance = new V3D_Plane_d(env, P0P1P0, V3D_Plane_d.Y0, epsilon);
        expResult = 0d;
        result = instance.getDistanceSquared(l);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Plane_d.
     */
    @Test
    public void testGetDistanceSquared_V3D_LineSegment_d_int() {
        System.out.println("getDistanceSquared");
        V3D_LineSegment_d l;
        V3D_Plane_d instance;
        double expResult;
        double result;
        double epsilon = 1d / 10000000d;
        // Test 1
        l = new V3D_LineSegment_d(pP0P0P0, pP1P0P0);
        instance = V3D_Plane_d.Y0;
        expResult = 0d;
        result = instance.getDistanceSquared(l, epsilon);
        assertEquals(expResult, result);
        // Test 2
        instance = new V3D_Plane_d(env, P0P1P0, V3D_Plane_d.Y0, epsilon);
        expResult = 0d;
        result = instance.getDistanceSquared(l, epsilon);
        assertEquals(expResult, result);
    }

    /**
     * Test of getPointOfProjectedIntersection method, of class V3D_Plane_d.
     */
    @Test
    public void testGetPointOfProjectedIntersection() {
        System.out.println("getPointOfProjectedIntersection");
        double epsilon = 1d / 10000000d;
        V3D_Point_d pt;
        V3D_Plane_d instance;
        V3D_Point_d expResult;
        V3D_Point_d result;
        // Test 1
        pt = pP1P0P0;
        instance = V3D_Plane_d.X0;
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
        V3D_Plane_d p;
        V3D_Point_d[] points;
        // Test 1
        p = new V3D_Plane_d(pP0P0P0, pP1P0P0, pP0P1P0);
        points = new V3D_Point_d[3];
        points[0] = pP2P2P0;
        points[1] = pN2P2P0;
        points[2] = pP2N2P0;
        assertTrue(V3D_Plane_d.isCoplanar(epsilon, p, points));
        // Test 2
        points[2] = pP2N2P1;
        assertFalse(V3D_Plane_d.isCoplanar(epsilon, p, points));
        // Test 3
        p = new V3D_Plane_d(pP0P0P0, pP0P1P0, pP0P0P1);
        points[0] = pP0P2P2;
        points[1] = pP0N2P2;
        points[2] = pP0P2N2;
        assertTrue(V3D_Plane_d.isCoplanar(epsilon, p, points));
        // Test 4
        points[2] = pP2N2P1;
        assertFalse(V3D_Plane_d.isCoplanar(epsilon, p, points));
    }

    /**
     * Test of isCoplanar method, of class V3D_Geometrics.
     */
    @Test
    public void testIsCoplanar_int_V3D_Point_dArr() {
        System.out.println("isCoplanar");
        double epsilon = 1d / 10000000d;
        V3D_Point_d[] points = new V3D_Point_d[6];
        points[0] = pP0P0P0;
        points[1] = pP0P1P0;
        points[2] = pP0P0P1;
        points[3] = pP0P2P2;
        points[4] = pP0N2P2;
        points[5] = pP0P2N2;
        assertTrue(V3D_Plane_d.isCoplanar(epsilon, points));
        // Test 2
        points[2] = pP2N2P1;
        assertFalse(V3D_Plane_d.isCoplanar(epsilon, points));
    }
}
