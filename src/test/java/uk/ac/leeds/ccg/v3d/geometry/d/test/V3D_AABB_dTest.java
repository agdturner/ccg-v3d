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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_AABB_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Point_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Rectangle_d;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Vector_d;

/**
 * Test class for V3D_AABB_d.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_AABB_dTest extends V3D_Test_d {

    public V3D_AABB_dTest() {
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
     * Test of toString method, of class V3D_AABB_d.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        V3D_AABB_d instance = new V3D_AABB_d(pP0P0P0);
        String expResult = "V3D_AABB_d(xMin=0.0, xMax=0.0, yMin=0.0,"
                + " yMax=0.0, zMin=0.0, zMax=0.0)";
        String result = instance.toString();
        //System.out.println(result);
        assertTrue(result.equalsIgnoreCase(expResult));
    }

    /**
     * Test of envelop method, of class V3D_AABB_d.
     */
    @Test
    public void testEnvelope() {
        System.out.println("envelope");
        V3D_AABB_d e1 = new V3D_AABB_d(pP0P0P0);
        V3D_AABB_d instance = new V3D_AABB_d(pP1P1P1);
        V3D_AABB_d expResult = new V3D_AABB_d(pP0P0P0, pP1P1P1);
        V3D_AABB_d result = instance.union(e1);
        assertTrue(result.equals(expResult));
    }

    /**
     * Test of intersects method, of class V3D_AABB_d.
     */
    @Test
    public void testIsIntersectedBy_V3D_AABB_d() {
        System.out.println("intersects");
        V3D_AABB_d instance = new V3D_AABB_d(pP0P0P0);
        V3D_AABB_d en = new V3D_AABB_d(pP0P0P0, pP1P1P1);
        assertTrue(instance.intersects(en));
        // Test 2
        instance = new V3D_AABB_d(pN1N1N1, pP0P0P0);
        assertTrue(instance.intersects(en));
        // Test 3
        en = new V3D_AABB_d(pN2N2N2, pP2P2P2);
        assertTrue(instance.intersects(en));
        // Test 4
        en = new V3D_AABB_d(pP0P0P0, pP1P1P1);
        instance = new V3D_AABB_d(pN1N1N1, pN1P0P0);
        assertFalse(instance.intersects(en));
        // Test 5
        en = new V3D_AABB_d(pP0P0P0, pP1P1P1);
        instance = new V3D_AABB_d(pN1P0P0, pP0P1P1);
        assertTrue(instance.intersects(en));
    }

    /**
     * Test of intersects method, of class V3D_AABB_d.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point_d() {
        System.out.println("intersects");
        V3D_AABB_d instance = new V3D_AABB_d(pN1N1N1, pP1P1P1);
        // Test 1 the centre
        assertTrue(instance.intersects(pP0P0P0));
        // Test 2 to 9 the corners
        // Test 2
        assertTrue(instance.intersects(pP1P1P1));
        // Test 3
        assertTrue(instance.intersects(pN1N1N1));
        // Test 4
        assertTrue(instance.intersects(pN1N1P1));
        // Test 5
        assertTrue(instance.intersects(pN1P1N1));
        // Test 6
        assertTrue(instance.intersects(pP1N1N1));
        // Test 7
        assertTrue(instance.intersects(pP1P1N1));
        // Test 8
        assertTrue(instance.intersects(pP1N1P1));
        // Test 9
        assertTrue(instance.intersects(pN1P1P1));
        // Test 10 to 21 edge mid points
        // Test 10
        assertTrue(instance.intersects(pN1N1P0));
        // Test 11
        assertTrue(instance.intersects(pN1P0N1));
        // Test 12
        assertTrue(instance.intersects(pP0N1N1));
        // Test 13
        assertTrue(instance.intersects(pP1P1P0));
        // Test 14
        assertTrue(instance.intersects(pP1P0P1));
        // Test 15
        assertTrue(instance.intersects(pP0P1P1));
        // Test 16
        assertTrue(instance.intersects(pP0N1P1));
        // Test 17
        assertTrue(instance.intersects(pN1P1P0));
        // Test 18
        assertTrue(instance.intersects(pP1P0N1));
        // Test 19
        assertTrue(instance.intersects(pP0P1N1));
        // Test 20
        assertTrue(instance.intersects(pP1N1P0));
        // Test 21
        assertTrue(instance.intersects(pN1P0P1));
        // Test 22 to 28 face mid points
        // Test 22
        assertTrue(instance.intersects(pP0P0P1));
        // Test 23
        assertTrue(instance.intersects(pP0P1P0));
        // Test 24
        assertTrue(instance.intersects(pP1P0P0));
        // Test 25
        assertTrue(instance.intersects(pP0P0N1));
        // Test 26
        assertTrue(instance.intersects(pP0N1P0));
        // Test 27
        assertTrue(instance.intersects(pN1P0P0));
    }

    /**
     * Test of equals method, of class V3D_AABB_d.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        V3D_AABB_d instance = new V3D_AABB_d(pP0P0P0, pP1P1P1);
        V3D_AABB_d en = new V3D_AABB_d(pP0P0P0, pP1P1P1);
        assertTrue(instance.equals(en));
        // Test 2
        en = new V3D_AABB_d(pP1P1P1, pP0P0P0);
        assertTrue(instance.equals(en));
        // Test 3
        en = new V3D_AABB_d(pP1N1P1, pP0P0P0);
        assertFalse(instance.equals(en));
    }

    /**
     * Test of intersects method, of class V3D_AABB_d. No need for a full
     * set of test here as this is covered by
     * {@link #testIsIntersectedBy_V3D_Point_d()}
     */
    @Test
    public void testIsIntersectedBy_3args() {
        System.out.println("intersects");
        V3D_AABB_d instance = new V3D_AABB_d(pN1N1N1, pP1P1P1);
        assertTrue(instance.intersects(0d, 0d, 0d));
        instance = new V3D_AABB_d(pP1P0P0);
        assertFalse(instance.intersects(0d, 0d, 0d));
    }

    /**
     * Test of getIntersect method, of class V3D_AABB_d.
     */
    @Test
    public void testGetIntersection_V3D_AABB_d() {
        System.out.println("getIntersect");
        V3D_AABB_d en;
        V3D_AABB_d instance;
        V3D_AABB_d expResult;
        V3D_AABB_d result;
        // Test 1
        en = new V3D_AABB_d(pN1N1N1, pP1P1P1);
        instance = new V3D_AABB_d(pP0P0P0, pP1P1P1);
        expResult = new V3D_AABB_d(pP0P0P0, pP1P1P1);
        result = instance.getIntersect(en);
        assertTrue(result.equals(expResult));
        // Test 2
        en = new V3D_AABB_d(pN1N1N1, pP0P0P0);
        instance = new V3D_AABB_d(pP0P0P0, pP1P1P1);
        expResult = new V3D_AABB_d(pP0P0P0);
        result = instance.getIntersect(en);
        assertTrue(result.equals(expResult));
        // Test 3
        en = new V3D_AABB_d(pN1N1N1, pP0P0P0);
        instance = new V3D_AABB_d(pP0P0P0, pP1P1P1);
        expResult = new V3D_AABB_d(pP0P0P0);
        result = instance.getIntersect(en);
        assertTrue(result.equals(expResult));
        // Test 4
        en = new V3D_AABB_d(pN1N1N1, pP0P0P1);
        instance = new V3D_AABB_d(pP0P0N1, pP1P1P1);
        expResult = new V3D_AABB_d(pP0P0N1, pP0P0P1);
        result = instance.getIntersect(en);
        assertTrue(result.equals(expResult));
    }

    /**
     * Test of union method, of class V3D_AABB_d.
     */
    @Test
    public void testUnion() {
        System.out.println("union");
        V3D_AABB_d en = new V3D_AABB_d(pN2N2N2, pP1P1P1);
        V3D_AABB_d instance = new V3D_AABB_d(pN1N1N1, pP2P2P2);
        V3D_AABB_d expResult = new V3D_AABB_d(pN2N2N2, pP2P2P2);
        V3D_AABB_d result = instance.union(en);
        assertTrue(result.equals(expResult));
    }

    /**
     * Test of contains method, of class V3D_AABB_d.
     */
    @Test
    public void testIsContainedBy() {
        System.out.println("contains");
        V3D_AABB_d en = new V3D_AABB_d(pN2N2N2, pP2P2P2);
        V3D_AABB_d instance = new V3D_AABB_d(pN1N1N1, pP1P1P1);
        assertTrue(en.contains(instance));
        // Test 2
        instance = new V3D_AABB_d(pN2N2N2, pP2P2P2);
        assertTrue(en.contains(instance));
        // Test 3
        en = new V3D_AABB_d(pN1N1N1, pP2P2P2);
        assertFalse(en.contains(instance));
    }

    /**
     * Test of getXMin method, of class V3D_AABB_d.
     */
    @Test
    public void testGetXMin() {
        System.out.println("getxMin");
        V3D_AABB_d instance = new V3D_AABB_d(pP0N1N1, pP0N1P0, pN2N2N2);
        double expResult = -2d;
        double result = instance.getXMin();
        assertTrue(expResult == result);
    }

    /**
     * Test of getXMax method, of class V3D_AABB_d.
     */
    @Test
    public void testGetXMax() {
        System.out.println("getxMax");
        V3D_AABB_d instance = new V3D_AABB_d(pP0N1N1, pP0N1P0, pN2N2N2);
        double expResult = 0d;
        double result = instance.getXMax();
        assertTrue(expResult == result);
    }

    /**
     * Test of getYMin method, of class V3D_AABB_d.
     */
    @Test
    public void testGetYMin() {
        System.out.println("getyMin");
        V3D_AABB_d instance = new V3D_AABB_d(pP0N1N1, pP0N1P0, pN2N2N2);
        double expResult = -2d;
        double result = instance.getYMin();
        assertTrue(expResult == result);
    }

    /**
     * Test of getYMax method, of class V3D_AABB_d.
     */
    @Test
    public void testGetYMax() {
        System.out.println("getyMax");
        V3D_AABB_d instance = new V3D_AABB_d(pP0N1N1, pP0N1P0, pN2N2N2);
        double expResult = -1d;
        double result = instance.getYMax();
        assertTrue(expResult == result);
    }

    /**
     * Test of getZMin method, of class V3D_AABB_d.
     */
    @Test
    public void testGetZMin() {
        System.out.println("getzMin");
        V3D_AABB_d instance = new V3D_AABB_d(pP0N1N1, pP0N1P0, pN2N2N2);
        double expResult = -2d;
        double result = instance.getZMin();
        assertTrue(expResult == result);
    }

    /**
     * Test of getZMax method, of class V3D_AABB_d.
     */
    @Test
    public void testGetZMax() {
        System.out.println("getzMax");
        V3D_AABB_d instance = new V3D_AABB_d(pP0N1N1, pP0N1P0, pN2N2N2);
        double expResult = 0d;
        double result = instance.getZMax();
        assertTrue(expResult == result);
    }

    /**
     * Test of translate method, of class V3D_AABB_d.
     */
    @Test
    public void testTranslate() {
        System.out.println("translate");
        V3D_Vector_d v = P1P1P1;
        V3D_AABB_d instance = new V3D_AABB_d(pP0P0P0, pP1P1P1);
        V3D_AABB_d expResult = new V3D_AABB_d(pP1P1P1, pP2P2P2);
        instance.translate(v);
        assertTrue(expResult.equals(instance));
        // Test 2
        v = N1N1N1;
        instance = new V3D_AABB_d(pP0P0P0, pP1P1P1);
        expResult = new V3D_AABB_d(pN1N1N1, pP0P0P0);
        instance.translate(v);
        assertTrue(expResult.equals(instance));
    }

    /**
     * Test of getViewport method, of class V3D_AABB_d.
     */
    @Test
    public void testGetViewport() {
        System.out.println("getViewport");
        double epsilon = 1d / 100000d;
        V3D_Point_d pt;
        V3D_Vector_d v;
        V3D_AABB_d instance;
        V3D_Rectangle_d expResult;
        V3D_Rectangle_d result;
        instance = new V3D_AABB_d(pN1N1N1, pP1P1P1);
        // Test front face square on.
        pt = pP0P0N2;
        // Test 1
        v = V3D_Vector_d.I;
        result = instance.getViewport(pt, v, epsilon);
        expResult = new V3D_Rectangle_d(pN1N1N1, pN1P1N1, pP1P1N1, pP1N1N1);
        assertTrue(expResult.equals(result, epsilon));
        // Test 2
        v = V3D_Vector_d.I.reverse();
        result = instance.getViewport(pt, v, epsilon);
        expResult = new V3D_Rectangle_d(pP1P1N1, pP1N1N1, pN1N1N1, pN1P1N1);
        assertTrue(expResult.equals(result, epsilon));
        // Test 3
        v = V3D_Vector_d.J;
        result = instance.getViewport(pt, v, epsilon);
        expResult = new V3D_Rectangle_d(pP1N1N1, pN1N1N1, pN1P1N1, pP1P1N1);
        assertTrue(expResult.equals(result, epsilon));
        // Test 4
        v = V3D_Vector_d.J.reverse();
        result = instance.getViewport(pt, v, epsilon);
        expResult = new V3D_Rectangle_d(pN1P1N1, pP1P1N1, pP1N1N1, pN1N1N1);
        assertTrue(expResult.equals(result, epsilon));
        // Test left face square on.
        pt = pN2P0P0;
        // Test 5
        v = V3D_Vector_d.K;
        result = instance.getViewport(pt, v, epsilon);
        expResult = new V3D_Rectangle_d(pN1P1N1, pN1N1N1, pN1N1P1, pN1P1P1);
        assertTrue(expResult.equals(result, epsilon));
        // Test 6
        v = V3D_Vector_d.K.reverse();
        result = instance.getViewport(pt, v, epsilon);
        expResult = new V3D_Rectangle_d(pN1N1P1, pN1P1P1, pN1P1N1, pN1N1N1);
        assertTrue(expResult.equals(result, epsilon));
        // Test 7
        v = V3D_Vector_d.J;
        result = instance.getViewport(pt, v, epsilon);
        expResult = new V3D_Rectangle_d(pN1N1N1, pN1N1P1, pN1P1P1, pN1P1N1);
        assertTrue(expResult.equals(result, epsilon));
        // Test 8
        v = V3D_Vector_d.J.reverse();
        result = instance.getViewport(pt, v, epsilon);
        expResult = new V3D_Rectangle_d(pN1P1N1, pN1N1N1, pN1N1P1, pN1P1P1);
        assertTrue(expResult.equals(result, epsilon));
        // Test bottom face square on.
        pt = pP0N2P0;
        // Test 9
        v = V3D_Vector_d.I;
        result = instance.getViewport(pt, v, epsilon);
        expResult = new V3D_Rectangle_d(pN1N1N1, pN1N1P1, pP1N1P1, pP1N1N1);
        assertTrue(expResult.equals(result, epsilon));
        // Test 10
        v = V3D_Vector_d.I.reverse();
        result = instance.getViewport(pt, v, epsilon);
        expResult = new V3D_Rectangle_d(pN1N1N1, pN1N1P1, pP1N1P1, pP1N1N1);
        assertTrue(expResult.equals(result, epsilon));
        // Test 11
        v = V3D_Vector_d.K;
        result = instance.getViewport(pt, v, epsilon);
        expResult = new V3D_Rectangle_d(pN1N1N1, pP1N1N1, pP1N1P1, pN1N1P1);
        assertTrue(expResult.equals(result, epsilon));
        // Test 12
        v = V3D_Vector_d.K.reverse();
        result = instance.getViewport(pt, v, epsilon);
        expResult = new V3D_Rectangle_d(pP1N1P1, pN1N1P1, pN1N1N1, pP1N1N1);
        assertTrue(expResult.equals(result, epsilon));
        // Test back face square on.
        pt = pP0P0P2;
        // Test 13
        v = V3D_Vector_d.I.reverse();
        result = instance.getViewport(pt, v, epsilon);
        expResult = new V3D_Rectangle_d(pN1N1P1, pN1P1P1, pP1P1P1, pP1N1P1);
        assertTrue(expResult.equals(result, epsilon));
        // Test 14
        v = V3D_Vector_d.I;
        result = instance.getViewport(pt, v, epsilon);
        expResult = new V3D_Rectangle_d(pP1P1P1, pP1N1P1, pN1N1P1, pN1P1P1);
        assertTrue(expResult.equals(result, epsilon));
        // Test 15
        v = V3D_Vector_d.J;
        result = instance.getViewport(pt, v, epsilon);
        expResult = new V3D_Rectangle_d(pP1N1P1, pN1N1P1, pN1P1P1, pP1P1P1);
        assertTrue(expResult.equals(result, epsilon));
        // Test 16
        v = V3D_Vector_d.J.reverse();
        result = instance.getViewport(pt, v, epsilon);
        expResult = new V3D_Rectangle_d(pN1P1P1, pP1P1P1, pP1N1P1, pN1N1P1);
        assertTrue(expResult.equals(result, epsilon));
        // Test RIGHT face square on.
        pt = pP2P0P0;
        // Test 17
        v = V3D_Vector_d.K;
        result = instance.getViewport(pt, v, epsilon);
        expResult = new V3D_Rectangle_d(pP1P1N1, pP1N1N1, pP1N1P1, pP1P1P1);
        assertTrue(expResult.equals(result, epsilon));
        // Test 18
        v = V3D_Vector_d.K.reverse();
        result = instance.getViewport(pt, v, epsilon);
        expResult = new V3D_Rectangle_d(pP1N1P1, pP1P1P1, pP1P1N1, pP1N1N1);
        assertTrue(expResult.equals(result, epsilon));
        // Test 19
        v = V3D_Vector_d.J;
        result = instance.getViewport(pt, v, epsilon);
        expResult = new V3D_Rectangle_d(pP1N1N1, pP1N1P1, pP1P1P1, pP1P1N1);
        assertTrue(expResult.equals(result, epsilon));
        // Test 20
        v = V3D_Vector_d.J.reverse();
        result = instance.getViewport(pt, v, epsilon);
        expResult = new V3D_Rectangle_d(pP1P1N1, pP1N1N1, pP1N1P1, pP1P1P1);
        assertTrue(expResult.equals(result, epsilon));
        // Test top face square on.
        pt = pP0P2P0;
        // Test 21
        v = V3D_Vector_d.I;
        result = instance.getViewport(pt, v, epsilon);
        expResult = new V3D_Rectangle_d(pN1P1N1, pN1P1P1, pP1P1P1, pP1P1N1);
        assertTrue(expResult.equals(result, epsilon));
        // Test 22
        v = V3D_Vector_d.I.reverse();
        result = instance.getViewport(pt, v, epsilon);
        expResult = new V3D_Rectangle_d(pN1P1N1, pN1P1P1, pP1P1P1, pP1P1N1);
        assertTrue(expResult.equals(result, epsilon));
        // Test 23
        v = V3D_Vector_d.K;
        result = instance.getViewport(pt, v, epsilon);
        expResult = new V3D_Rectangle_d(pN1P1N1, pP1P1N1, pP1P1P1, pN1P1P1);
        assertTrue(expResult.equals(result, epsilon));
        // Test 24
        v = V3D_Vector_d.K.reverse();
        result = instance.getViewport(pt, v, epsilon);
        expResult = new V3D_Rectangle_d(pP1P1P1, pN1P1P1, pN1P1N1, pP1P1N1);
        assertTrue(expResult.equals(result, epsilon));
        
        
//        // Test front left edge.
//        pt = pN2P0N2;
//        double N1_5 = -3d / 2d;
//        double N0_5 = -1d / 2d;
//        // Test 25
//        v = V3D_Vector_d.I.add(V3D_Vector_d.K.reverse());
//        result = instance.getViewport(pt, v, epsilon);
//        expResult = new V3D_Rectangle_d(
//                new V3D_Point_d(env, N1_5, -1d, N0_5),
//                new V3D_Point_d(env, N1_5, 1d, N0_5),
//                new V3D_Point_d(env, N0_5, 1d, N1_5),
//                new V3D_Point_d(env, N0_5, -1d, N1_5));
//        //expResult = new V3D_Rectangle_d(pN2N1P0, pN2P1P0, pP0P1N2, pP0N1N2);
//        //assertTrue(new V3D_Rectangle_d(pN2N1P0, pN2P1P0, pP0P1N2, pP0N1N2).equals(result, epsilon));
//        assertTrue(expResult.equals(result, epsilon));
//        // Test 26
//        //v = v.getCrossProduct(new V3D_Vector_d(pN2P0N2));
//        v = V3D_Vector_d.J;
//        result = instance.getViewport(pt, v, epsilon);
//        expResult = new V3D_Rectangle_d(
//                new V3D_Point_d(env, N0_5, -1d, N1_5),
//                new V3D_Point_d(env, N1_5, -1d, N0_5),
//                new V3D_Point_d(env, N1_5, 1d, N0_5),
//                new V3D_Point_d(env, N0_5, 1d, N1_5));
//        //expResult = new V3D_Rectangle_d(pP0N1N2, pN2N1P0, pN2P1P0, pP0P1N2);
//        assertTrue(expResult.equals(result, epsilon));
//        // Test 27
//        v = V3D_Vector_d.I.add(V3D_Vector_d.K.reverse()).reverse();
//        result = instance.getViewport(pt, v, epsilon);
//        expResult = new V3D_Rectangle_d(
//                new V3D_Point_d(env, N0_5, 1d, N1_5), 
//                new V3D_Point_d(env, N0_5, -1d, N1_5),
//                new V3D_Point_d(env, N1_5, -1d, N0_5),
//                new V3D_Point_d(env, N1_5, 1d, N0_5));
//        //expResult = new V3D_Rectangle_d(pP0P1N2, pP0N1N2, pN2N1P0, pN2P1P0);
//        assertTrue(expResult.equals(result, epsilon));
//        // Test 28
//        v = V3D_Vector_d.J.reverse();
//        result = instance.getViewport(pt, v, epsilon);
//        expResult = new V3D_Rectangle_d(
//                new V3D_Point_d(env, N0_5, 1d, N1_5),
//                new V3D_Point_d(env, N1_5, 1d, N0_5),
//                new V3D_Point_d(env, N1_5, -1d, N0_5),
//                new V3D_Point_d(env, N0_5, -1d, N1_5));
//        //expResult = new V3D_Rectangle_d(pN2P1P0, pP0P1N2, pP0N1N2, pN2N1P0);
//        assertTrue(expResult.equals(result, epsilon));
//        // Test front left lower corner.
//        pt = pN2N2N2;
//        // Test 29
//        v = V3D_Vector_d.I.add(V3D_Vector_d.K);
//        result = instance.getViewport(pt, v, epsilon);
//        expResult = new V3D_Rectangle_d(
//                new V3D_Point_d(env, N1_5, N1, N0_5),
//                new V3D_Point_d(env, N1_5, P1, N0_5),
//                new V3D_Point_d(env, N0_5, P1, N1_5),
//                new V3D_Point_d(env, N0_5, N1, N1_5));
//        assertTrue(expResult.equals(result));
//        // Test 30
//        v = V3D_Vector_d.J;
//        result = instance.getViewport(pt, v, epsilon);
//        expResult = new V3D_Rectangle_d(
//                new V3D_Point_d(env, N1_5, N1, N0_5),
//                new V3D_Point_d(env, N0_5, N1, N1_5),
//                new V3D_Point_d(env, N0_5, P1, N1_5),
//                new V3D_Point_d(env, N1_5, P1, N0_5));
//        assertTrue(expResult.equals(result));
//        // Test 31
//        v = V3D_Vector_d.I.add(V3D_Vector_d.K.reverse()).reverse();
//        result = instance.getViewport(pt, v, epsilon);
//        expResult = new V3D_Rectangle_d(
//                new V3D_Point_d(env, N0_5, N1, N1_5),
//                new V3D_Point_d(env, N0_5, P1, N1_5),
//                new V3D_Point_d(env, N1_5, P1, N0_5),
//                new V3D_Point_d(env, N1_5, N1, N0_5));
//        assertTrue(expResult.equals(result));
//        // Test 32
//        v = V3D_Vector_d.J.reverse();
//        result = instance.getViewport(pt, v, epsilon);
//        expResult = new V3D_Rectangle_d(
//                new V3D_Point_d(env, N0_5, P1, N1_5),
//                new V3D_Point_d(env, N1_5, P1, N0_5),
//                new V3D_Point_d(env, N1_5, N1, N0_5),
//                new V3D_Point_d(env, N0_5, N1, N1_5));
//        assertTrue(expResult.equals(result));
    }
}
