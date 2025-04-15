/*
 * Copyright 2022 Centre for Computational Geography.
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
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_AABB_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Geometry_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Line_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_LineSegment_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Plane_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Point_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Ray_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Rectangle_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Tetrahedron_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Triangle_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Vector_d;

/**
 *
 * @author Andy Turner
 */
public class V3D_Tetrahedron_dTest extends V3D_Test_d {

    public V3D_Tetrahedron_dTest() {
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
     * Test of toString method, of class V3D_Tetrahedron_d.
     */
    @Test
    public void testToString_0args() {
        System.out.println("toString");
        V3D_Tetrahedron_d instance = new V3D_Tetrahedron_d(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1, 0d);
        String expResult = """
                           V3D_Tetrahedron_d
                           (
                            p= V3D_Vector_d(dx=0.0, dy=0.0, dz=0.0),
                            q= V3D_Vector_d(dx=1.0, dy=0.0, dz=0.0),
                            r= V3D_Vector_d(dx=0.0, dy=1.0, dz=0.0),
                            s= V3D_Vector_d(dx=0.0, dy=1.0, dz=1.0)
                           )""";
        String result = instance.toString();
        //System.out.println(result);
        assertTrue(expResult.equalsIgnoreCase(result));
    }

    /**
     * Test of toString method, of class V3D_Tetrahedron_d.
     */
    @Test
    public void testToString_String() {
        System.out.println("toString");
        String pad = "";
        V3D_Tetrahedron_d instance = new V3D_Tetrahedron_d(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1, 0d);
        String expResult = """
                           V3D_Tetrahedron_d
                           (
                            p= V3D_Vector_d(dx=0.0, dy=0.0, dz=0.0),
                            q= V3D_Vector_d(dx=1.0, dy=0.0, dz=0.0),
                            r= V3D_Vector_d(dx=0.0, dy=1.0, dz=0.0),
                            s= V3D_Vector_d(dx=0.0, dy=1.0, dz=1.0)
                           )""";
        String result = instance.toString(pad);
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAABB method, of class V3D_Tetrahedron_d.
     */
    @Test
    public void testGetAABB() {
        System.out.println("getAABB");
        V3D_Tetrahedron_d instance = new V3D_Tetrahedron_d(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1, 0d);
        V3D_AABB_d expResult = new V3D_AABB_d(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1);
        V3D_AABB_d result = instance.getAABB();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getP method, of class V3D_Tetrahedron_d.
     */
    @Test
    public void testGetP() {
        System.out.println("getP");
        V3D_Tetrahedron_d instance = new V3D_Tetrahedron_d(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1, 0d);
        V3D_Point_d expResult = pP0P0P0;
        V3D_Point_d result = instance.getP();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getQ method, of class V3D_Tetrahedron_d.
     */
    @Test
    public void testGetQ() {
        System.out.println("getQ");
        V3D_Tetrahedron_d instance = new V3D_Tetrahedron_d(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1, 0d);
        V3D_Point_d expResult = pP1P0P0;
        V3D_Point_d result = instance.getQ();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getR method, of class V3D_Tetrahedron_d.
     */
    @Test
    public void testGetR() {
        System.out.println("getR");
        V3D_Tetrahedron_d instance = new V3D_Tetrahedron_d(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1, 0d);
        V3D_Point_d expResult = pP0P1P0;
        V3D_Point_d result = instance.getR();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getS method, of class V3D_Tetrahedron_d.
     */
    @Test
    public void testGetS() {
        System.out.println("getS");
        V3D_Tetrahedron_d instance = new V3D_Tetrahedron_d(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1, 0d);
        V3D_Point_d expResult = pP0P1P1;
        V3D_Point_d result = instance.getS();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getPqr method, of class V3D_Tetrahedron_d.
     */
    @Test
    public void testGetPqr() {
        System.out.println("getPqr");
        double epsilon = 1d / 10000000d;
        V3D_Tetrahedron_d instance = new V3D_Tetrahedron_d(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1, epsilon);
        V3D_Triangle_d expResult = new V3D_Triangle_d(pP0P0P0, pP1P0P0, pP0P1P0);
        V3D_Triangle_d result = instance.getPqr();
        assertTrue(expResult.equals(result, epsilon));
    }

    /**
     * Test of getQsr method, of class V3D_Tetrahedron_d.
     */
    @Test
    public void testGetQsr() {
        System.out.println("getQsr");
        double epsilon = 1d / 10000000d;
        V3D_Tetrahedron_d instance = new V3D_Tetrahedron_d(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1, epsilon);
        V3D_Triangle_d expResult = new V3D_Triangle_d(pP1P0P0, pP0P1P0, pP0P1P1);
        V3D_Triangle_d result = instance.getQsr();
        assertTrue(expResult.equals(result, epsilon));
    }

    /**
     * Test of getSpr method, of class V3D_Tetrahedron_d.
     */
    @Test
    public void testGetSpr() {
        System.out.println("getSpr");
        double epsilon = 1d / 10000000d;
        V3D_Tetrahedron_d instance = new V3D_Tetrahedron_d(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1, epsilon);
        V3D_Triangle_d expResult = new V3D_Triangle_d(pP0P0P0, pP0P1P0, pP0P1P1);
        V3D_Triangle_d result = instance.getSpr();
        assertTrue(expResult.equals(result, epsilon));
    }

    /**
     * Test of getPsq method, of class V3D_Tetrahedron_d.
     */
    @Test
    public void testGetPsq() {
        System.out.println("getPsq");
        double epsilon = 1d / 10000000d;
        V3D_Tetrahedron_d instance = new V3D_Tetrahedron_d(pP0P0P0, pP1P0P0,
                pP0P1P0, pP0P1P1, epsilon);
        V3D_Triangle_d expResult = new V3D_Triangle_d(pP0P0P0, pP1P0P0, pP0P1P1);
        V3D_Triangle_d result = instance.getPsq();
        assertTrue(expResult.equals(result, epsilon));
    }

    /**
     * Test of getArea method, of class V3D_Tetrahedron_d.
     * https://keisan.casio.com/exec/system/1329962711
     */
    @Test
    public void testGetArea() {
        System.out.println("getArea");
        double epsilon = 1d / 10000000d;
        V3D_Tetrahedron_d instance = new V3D_Tetrahedron_d(pP0P0P0,
                pP1P0P0, pP0P1P0, pP0P0P1, epsilon);
        double expResult = (Math.sqrt(3d / 2d) * Math.sqrt(2d) / 2d) + 3d / 2d;
        double result = instance.getArea();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getVolume method, of class V3D_Tetrahedron_d.
     * https://keisan.casio.com/exec/system/1329962711
     */
    @Test
    public void testGetVolume() {
        System.out.println("getVolume");
        double epsilon = 1d / 10000000d;
        V3D_Tetrahedron_d instance = new V3D_Tetrahedron_d(pP0P0P0,
                pP1P0P0, pP0P1P0, pP0P0P1, epsilon);
        double expResult = 1d / 6d;
        double result = instance.getVolume();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getCentroid method, of class V3D_Tetrahedron_d.
     */
    @Test
    public void testGetCentroid() {
        System.out.println("getCentroid");
        double epsilon = 1d / 10000000d;
        V3D_Tetrahedron_d instance = new V3D_Tetrahedron_d(
                pN2N2N2, pP2N2N2, pP0P2N2,
                new V3D_Point_d(env, 0d, 0d, 4d), epsilon);
        V3D_Point_d expResult = new V3D_Point_d(env, P0P0P0,
                new V3D_Vector_d(0d, -1d / 2d, -1d / 2d));
        V3D_Point_d result = instance.getCentroid();
        assertTrue(expResult.equals(result, epsilon));
    }

    /**
     * Test of intersects method, of class V3D_Tetrahedron_d.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point_d_int() {
        System.out.println("intersects");
        double epsilon = 1d / 10000000d;
        V3D_Point_d pt;
        V3D_Tetrahedron_d instance;
        // Test 1
        pt = pP0P0P0;
        //instance = new V3D_Tetrahedron_d(pP0P0P0, pP1P0P0, pP0P1P0, pP0P0P1);
        instance = new V3D_Tetrahedron_d(pP0P0P0, pP1P0P0, pP0P1P0, pP0P0P1, epsilon);
        assertTrue(instance.intersects(pt, epsilon));
        // Test 2
        pt = new V3D_Point_d(env, 0.5, 0.5, 0.5);
        instance = new V3D_Tetrahedron_d(pP0P0P0, pP2P0P0, pP0P2P0, pP0P0P2, epsilon);
        assertTrue(instance.intersects(pt, epsilon));
        // Test 3
        pt = new V3D_Point_d(env, -0.5, -0.5, -0.5);
        assertFalse(instance.intersects(pt, epsilon));
        // Test 4
        pt = new V3D_Point_d(env, -0.5, 0, 0);
        assertFalse(instance.intersects(pt, epsilon));
        // Test 5
        instance = new V3D_Tetrahedron_d(
                new V3D_Point_d(env, 0, 0, -75),
                new V3D_Point_d(env, -50, -37.5, 10),
                new V3D_Point_d(env, 50, 37.5, 10),
                new V3D_Point_d(env, -50, 37.5, 10), epsilon);
        //pt = new V3D_Point_d(env, -24, -19, 0);
        //pt = new V3D_Point_d(env, 0, 0, -74);
        //pt = new V3D_Point_d(env, 1, 1, 0);
        pt = new V3D_Point_d(env, -11, 11, 0);
        assertTrue(instance.intersects(pt, epsilon));
    }

    /**
     * Test of getIntersect method, of class V3D_Tetrahedron_d.
     */
    @Test
    public void testGetIntersection_V3D_Line_int() {
        System.out.println("getIntersect");
        double epsilon = 1d / 10000000d;
        V3D_Line_d l;
        V3D_Tetrahedron_d instance;
        V3D_Geometry_d expResult;
        V3D_Geometry_d result;
        // Test 1
        l = new V3D_Line_d(pP0P0P0, pP1P0P0);
        instance = new V3D_Tetrahedron_d(pP0P0P0, pP1P0P0, pP0P1P0, pP0P0P1, epsilon);
        expResult = new V3D_LineSegment_d(pP0P0P0, pP1P0P0);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
        // Test 2
        instance = new V3D_Tetrahedron_d(pN2N2P0, pP2N2P0, pN2P2P0, pP0P0P2, epsilon);
        l = new V3D_Line_d(pN2N2P0, new V3D_Triangle_d(env, P0P0P0, P2N2P0, N2P2P0, P0P0P2).getCentroid());
        expResult = new V3D_LineSegment_d(pN2N2P0, new V3D_Triangle_d(env, P0P0P0, P2N2P0, N2P2P0, P0P0P2).getCentroid());
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
    }

    /**
     * Test of getIntersect method, of class V3D_Tetrahedron_d.
     */
    @Test
    public void testGetIntersection_V3D_LineSegment_d_int() {
        System.out.println("getIntersect");
        double epsilon = 1d / 10000000d;
        V3D_LineSegment_d l;
        V3D_Tetrahedron_d instance;
        V3D_Geometry_d expResult;
        V3D_Geometry_d result;
        // Test 1
        l = new V3D_LineSegment_d(pP0P0P0, pP1P0P0);
        instance = new V3D_Tetrahedron_d(pP0P0P0, pP1P0P0, pP0P1P0, pP0P0P1, epsilon);
        expResult = new V3D_LineSegment_d(pP0P0P0, pP1P0P0);
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
        // Test 2
        instance = new V3D_Tetrahedron_d(pN2N2P0, pP2N2P0, pN2P2P0, pP0P0P2, epsilon);
        l = new V3D_LineSegment_d(pN2N2P0, new V3D_Triangle_d(env, P0P0P0, P2N2P0, N2P2P0, P0P0P2).getCentroid());
        expResult = new V3D_LineSegment_d(pN2N2P0, new V3D_Triangle_d(env, P0P0P0, P2N2P0, N2P2P0, P0P0P2).getCentroid());
        result = instance.getIntersect(l, epsilon);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
//        // Test 3
//        instance = new V3D_Tetrahedron_d(P0P0P0, N2N2P0, P2N2P0, N2P2P0, P0P0P2);
//        l = new V3D_LineSegment_d(P0P0P0, N2N2P0, new V3D_Triangle_d(P0P0P0, P2N2P0, N2P2P0, P0P0P2).getCentroid().rel.multiply(P2).add(P2P2P0));
//        expResult = new V3D_LineSegment_d(pN2N2P0, new V3D_Triangle_d(P0P0P0, P2N2P0, N2P2P0, P0P0P2).getCentroid());
//        result = instance.getIntersect(l);
//        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreOrientation((V3D_LineSegment_d) result));
    }

    /**
     * Test of getIntersect method, of class V3D_Tetrahedron_d.
     */
    @Test
    public void testGetIntersection_V3D_Plane_d_int() {
        System.out.println("getIntersect");
        double epsilon = 1d / 10000000d;
        V3D_Plane_d p;
        V3D_Tetrahedron_d instance;
        V3D_Geometry_d expResult;
        V3D_Geometry_d result;
        // Test 1
        p = V3D_Plane_d.X0;
        p.env = env;
        instance = new V3D_Tetrahedron_d(pN2N2P0, pP2N2P0, pN2P2P0, pP0P0P2, epsilon);
        expResult = new V3D_Triangle_d(pP0P0P0, pP0P0P2, pP0N2P0);
        result = instance.getIntersect(p, epsilon);
        assertTrue(((V3D_Triangle_d) expResult).equals((V3D_Triangle_d) result, epsilon));
        // Test 2
        p = new V3D_Plane_d(V3D_Plane_d.X0);
        p.translate(P1P0P0);
        instance = new V3D_Tetrahedron_d(pN2N2P0, pP2N2P0, pN2P2P0, pP0P0P2, epsilon);
        expResult = new V3D_Triangle_d(env, P0P0P0, P1N2P0, P1N1P0, P1N1P1);
        result = instance.getIntersect(p, epsilon);
        assertTrue(((V3D_Triangle_d) expResult).equals((V3D_Triangle_d) result, epsilon));
        // Test 3
        p = new V3D_Plane_d(V3D_Plane_d.X0);
        p.translate(P2P0P0);
        instance = new V3D_Tetrahedron_d(pN2N2P0, pP2N2P0, pN2P2P0, pP0P0P2, epsilon);
        expResult = new V3D_Point_d(env, P0P0P0, P2N2P0);
        result = instance.getIntersect(p, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals( 
                (V3D_Point_d) result, epsilon));
        // Test 4
        p = new V3D_Plane_d(V3D_Plane_d.X0);
        p.translate(N2P0P0);
        instance = new V3D_Tetrahedron_d(pN2N2P0, pP2N2P0, pN2P2P0, pP0P0P2, epsilon);
        expResult = new V3D_LineSegment_d(pN2P2P0, pN2N2P0);
        result = instance.getIntersect(p, epsilon);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
    }

    /**
     * Test of getIntersect method, of class V3D_Tetrahedron_d.
     */
    @Test
    public void testGetIntersection_V3D_Triangle_d_double() {
        System.out.println("getIntersect");
        double epsilon = 1d / 10000000d;
        V3D_Triangle_d t;
        V3D_Tetrahedron_d instance;
        V3D_Geometry_d expResult;
        V3D_Geometry_d result;
        // Test 1
        t = new V3D_Triangle_d(pP0N2P0, pP0P2P0, pP0P0P2);
        instance = new V3D_Tetrahedron_d(pN2N2P0, pP2N2P0, pN2P2P0, pP0P0P2, epsilon);
        expResult = new V3D_Triangle_d(pP0P0P0, pP0P0P2, pP0N2P0);
        result = instance.getIntersect(t, epsilon);
        assertTrue(((V3D_Triangle_d) expResult).equals((V3D_Triangle_d) result, epsilon));
//        // Test 2
//        t = new V3D_Triangle_d(pP1N2P0, pP1P2P0, pP1P0P2);
//        instance = new V3D_Tetrahedron_d(pN2N2P0, pP2N2P0, pN2P2P0, pP0P0P2);
//        expResult = new V3D_Triangle_d(pP1N2P0, pP1N1P0, pP1N1P1);
//        result = instance.getIntersect(t);
//        assertTrue(((V3D_Triangle_d) expResult).equals((V3D_Triangle_d) result, epsilon));
//        // Test 3
//        t = new V3D_Triangle_d(pP1N1P0, pP1P1P0, pP1P0P1);
//        instance = new V3D_Tetrahedron_d(pN2N2P0, pP2N2P0, pN2P2P0, pP0P0P2);
//        expResult = new V3D_Triangle_d(pP1N1P0, pP1P1P0, pP1P0P1);
//        result = instance.getIntersect(t);
//        System.out.println("expResult " + expResult);
//        System.out.println("result " + result);
//        assertTrue(((V3D_Triangle_d) expResult).equals((V3D_Triangle_d) result, epsilon));
        // Test 4
        t = new V3D_Triangle_d(pN1N1P0, pP1N1P0, pN1P1P0);
        instance = new V3D_Tetrahedron_d(pN2N2P0, pP2N2P0, pN2P2P0, pP0P0P2, epsilon);
        expResult = new V3D_Triangle_d(pN1N1P0, pP1N1P0, pN1P1P0);
        result = instance.getIntersect(t, epsilon);
        assertTrue(((V3D_Triangle_d) expResult).equals((V3D_Triangle_d) result, epsilon));
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Triangle_d.
     */
    @Test
    public void testGetDistanceSquared_V3D_Triangle_d() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V3D_Tetrahedron_d t;
        V3D_Triangle_d tr;
        double expResult;
        double result;
        // Test 1
        t = new V3D_Tetrahedron_d(pP0P0P0, pP1P0P0, pP0P1P0, pP0P0P1, epsilon);
        tr = new V3D_Triangle_d(pP0P0P0, pP1P0P0, pP0P1P0);
        expResult = 0d;
        result = t.getDistanceSquared(tr, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 2
        tr = new V3D_Triangle_d(pN1P1P0, pN1P0P0, pN1P0P1);
        expResult = 1d;
        result = t.getDistanceSquared(tr, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistance method, of class V3D_Rectangle_d.
     */
    @Test
    public void testGetDistance_V3D_Rectangle_d() {
        System.out.println("getDistance");
        double epsilon = 1d / 10000000d;
        V3D_Tetrahedron_d t = new V3D_Tetrahedron_d(pP0P0P0, pP0P1P0, pP1P1P0, pP0P0P1, epsilon);
        V3D_Rectangle_d r = new V3D_Rectangle_d(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        double expResult = 0d;
        double result = t.getDistance(r, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Rectangle_d.
     */
    @Test
    public void testGetDistanceSquared_V3D_Rectangle_d() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V3D_Tetrahedron_d t = new V3D_Tetrahedron_d(pP0P0P0, pP0P1P0, pP1P1P0, pP0P0P1, epsilon);
        V3D_Rectangle_d r = new V3D_Rectangle_d(pP0P0P0, pP0P1P0, pP1P1P0, pP1P0P0);
        double expResult = 0d;
        double result = t.getDistance(r, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistance method, of class V3D_Tetrahedron_d covered by
 {@link #testGetDistanceSquared_V3D_Point_d_int()}.
     */
    @Test
    public void testGetDistance_V3D_Point_d_int() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistanceSquared method, of class V3D_Tetrahedron_d.
     */
    @Test
    public void testGetDistanceSquared_V3D_Point_d_int() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V3D_Point_d p;
        V3D_Tetrahedron_d instance;
        double expResult;
        double result;
        // Test 1
        instance = new V3D_Tetrahedron_d(pN2N2P0, pP2N2P0, pN2P2P0, pP0P0P2, epsilon);
        p = pP0P0P0;
        expResult = 0d;
        result = instance.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 2
        instance = new V3D_Tetrahedron_d(pN2N2P0, pP2N2P0, pN2P2P0, pP0P0P2, epsilon);
        p = pN2N2N2;
        expResult = 4d;
        result = instance.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getIntersect method, of class V3D_Tetrahedron_d.
     */
    @Test
    public void testGetIntersection_V3D_Ray_d() {
        System.out.println("getIntersect");
        double epsilon = 1d / 10000000d;
        V3D_Tetrahedron_d t;
        V3D_Ray_d r;
        V3D_Geometry_d expResult;
        V3D_Geometry_d result;
        // Test 1
        t = new V3D_Tetrahedron_d(pN2N2P0, pP2N2P0, pN2P2P0, pP0P0P2, epsilon);
        r = new V3D_Ray_d(pN1P0P0, pP0P0P0);
        expResult = new V3D_LineSegment_d(pN1P0P0, pP0P0P0);
        result = t.getIntersect(r, epsilon);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
        // Test 2
        t = new V3D_Tetrahedron_d(pN2N2P0, pP2N2P0, pN2P2P0, pP0P0P2, epsilon);
        r = new V3D_Ray_d(pN1P1P0, pP0P1P0);
        expResult = pN1P1P0;
        result = t.getIntersect(r, epsilon);
        assertTrue(((V3D_Point_d) expResult).equals(
                (V3D_Point_d) result, epsilon));
        // Test 3
        t = new V3D_Tetrahedron_d(pN2N2P0, pP2N2P0, pN2P2P0, pP0P0P2, epsilon);
        r = new V3D_Ray_d(pN1P0P1, pP0P0P1);
        expResult = new V3D_LineSegment_d(pN1P0P1, pP0P0P1);
        result = t.getIntersect(r, epsilon);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
        // Test 4
        t = new V3D_Tetrahedron_d(pN2N2P0, pP2N2P0, pN2P2P0, pP0P0P2, epsilon);
        V3D_Point_d pNHP0P1 = new V3D_Point_d(env, -1d / 2d, 0d, 1d);
        r = new V3D_Ray_d(pNHP0P1, pP0P0P1);
        expResult = new V3D_LineSegment_d(pNHP0P1, pP0P0P1);
        result = t.getIntersect(r, epsilon);
        assertTrue(((V3D_LineSegment_d) expResult).equalsIgnoreDirection(
                epsilon, (V3D_LineSegment_d) result));
    }

}
