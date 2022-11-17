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
package uk.ac.leeds.ccg.v3d.geometry;

import java.math.RoundingMode;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Disabled;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * Test class for V3D_Envelope.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_EnvelopeTest extends V3D_Test {

    private static final long serialVersionUID = 1L;

    public V3D_EnvelopeTest() {
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
     * Test of toString method, of class V3D_Envelope.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Envelope instance = new V3D_Envelope(oom, rm, pP0P0P0);
        String expResult = "V3D_Envelope(xMin=0, xMax=0, yMin=0, yMax=0"
                + ", zMin=0, zMax=0)";
        String result = instance.toString();
        //System.out.println(result);
        assertTrue(result.equalsIgnoreCase(expResult));
    }

    /**
     * Test of envelop method, of class V3D_Envelope.
     */
    @Test
    public void testEnvelope() {
        System.out.println("envelope");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Envelope e1 = new V3D_Envelope(oom, rm, pP0P0P0);
        V3D_Envelope instance = new V3D_Envelope(oom, rm, pP1P1P1);
        V3D_Envelope expResult = new V3D_Envelope(oom, rm, pP0P0P0, pP1P1P1);
        V3D_Envelope result = instance.union(e1, oom, rm);
        assertTrue(result.equals(expResult, oom, rm));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Envelope.
     */
    @Test
    public void testIsIntersectedBy_V3D_Envelope() {
        System.out.println("isIntersectedBy");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Envelope instance = new V3D_Envelope(oom, rm, pP0P0P0, pP0P0P0);
        V3D_Envelope en = new V3D_Envelope(oom, rm, pP0P0P0, pP1P1P1);
        assertTrue(instance.isIntersectedBy(en, oom, rm));
        // Test 2
        instance = new V3D_Envelope(oom, rm, pN1N1N1, pP0P0P0);
        assertTrue(instance.isIntersectedBy(en, oom, rm));
        // Test 3
        en = new V3D_Envelope(oom, rm, pN2N2N2, pP2P2P2);
        assertTrue(instance.isIntersectedBy(en, oom, rm));
        // Test 4
        en = new V3D_Envelope(oom, rm, pP0P0P0, pP1P1P1);
        instance = new V3D_Envelope(oom, rm, pN1N1N1, pN1P0P0);
        assertFalse(instance.isIntersectedBy(en, oom, rm));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Envelope.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point() {
        System.out.println("isIntersectedBy");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Envelope instance = new V3D_Envelope(oom, rm, pN1N1N1, pP1P1P1);
        // Test 1 the centre
        assertTrue(instance.isIntersectedBy(pP0P0P0, oom, rm));
        // Test 2 to 9 the corners
        // Test 2
        assertTrue(instance.isIntersectedBy(pP1P1P1, oom, rm));
        // Test 3
        assertTrue(instance.isIntersectedBy(pN1N1N1, oom, rm));
        // Test 4
        assertTrue(instance.isIntersectedBy(pN1N1P1, oom, rm));
        // Test 5
        assertTrue(instance.isIntersectedBy(pN1P1N1, oom, rm));
        // Test 6
        assertTrue(instance.isIntersectedBy(pP1N1N1, oom, rm));
        // Test 7
        assertTrue(instance.isIntersectedBy(pP1P1N1, oom, rm));
        // Test 8
        assertTrue(instance.isIntersectedBy(pP1N1P1, oom, rm));
        // Test 9
        assertTrue(instance.isIntersectedBy(pN1P1P1, oom, rm));
        // Test 10 to 21 edge mid points
        // Test 10
        assertTrue(instance.isIntersectedBy(pN1N1P0, oom, rm));
        // Test 11
        assertTrue(instance.isIntersectedBy(pN1P0N1, oom, rm));
        // Test 12
        assertTrue(instance.isIntersectedBy(pP0N1N1, oom, rm));
        // Test 13
        assertTrue(instance.isIntersectedBy(pP1P1P0, oom, rm));
        // Test 14
        assertTrue(instance.isIntersectedBy(pP1P0P1, oom, rm));
        // Test 15
        assertTrue(instance.isIntersectedBy(pP0P1P1, oom, rm));
        // Test 16
        assertTrue(instance.isIntersectedBy(pP0N1P1, oom, rm));
        // Test 17
        assertTrue(instance.isIntersectedBy(pN1P1P0, oom, rm));
        // Test 18
        assertTrue(instance.isIntersectedBy(pP1P0N1, oom, rm));
        // Test 19
        assertTrue(instance.isIntersectedBy(pP0P1N1, oom, rm));
        // Test 20
        assertTrue(instance.isIntersectedBy(pP1N1P0, oom, rm));
        // Test 21
        assertTrue(instance.isIntersectedBy(pN1P0P1, oom, rm));
        // Test 22 to 28 face mid points
        // Test 22
        assertTrue(instance.isIntersectedBy(pP0P0P1, oom, rm));
        // Test 23
        assertTrue(instance.isIntersectedBy(pP0P1P0, oom, rm));
        // Test 24
        assertTrue(instance.isIntersectedBy(pP1P0P0, oom, rm));
        // Test 25
        assertTrue(instance.isIntersectedBy(pP0P0N1, oom, rm));
        // Test 26
        assertTrue(instance.isIntersectedBy(pP0N1P0, oom, rm));
        // Test 27
        assertTrue(instance.isIntersectedBy(pN1P0P0, oom, rm));
    }

    /**
     * Test of equals method, of class V3D_Envelope.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Envelope instance = new V3D_Envelope(oom, rm, pP0P0P0, pP1P1P1);
        V3D_Envelope en = new V3D_Envelope(oom, rm, pP0P0P0, pP1P1P1);
        assertTrue(instance.equals(en, oom, rm));
        // Test 2
        en = new V3D_Envelope(oom, rm, pP1P1P1, pP0P0P0);
        assertTrue(instance.equals(en, oom, rm));
        // Test 3
        en = new V3D_Envelope(oom, rm, pP1N1P1, pP0P0P0);
        assertFalse(instance.equals(en, oom, rm));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Envelope. No need for a full
     * set of test here as this is covered by
     * {@link #testIsIntersectedBy_V3D_Point()}
     */
    @Test
    public void testIsIntersectedBy_3args() {
        System.out.println("isIntersectedBy");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Envelope instance = new V3D_Envelope(oom, rm, pN1N1N1, pP1P1P1);
        assertTrue(instance.isIntersectedBy(P0, P0, P0, oom, rm));
    }

    /**
     * Test of getIntersection method, of class V3D_Envelope.
     */
    @Test
    public void testGetIntersection_V3D_Envelope() {
        System.out.println("getIntersection");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Envelope en;
        V3D_Envelope instance;
        V3D_Envelope expResult;
        V3D_Envelope result;
        // Test 1
        en = new V3D_Envelope(oom, rm, pN1N1N1, pP1P1P1);
        instance = new V3D_Envelope(oom, rm, pP0P0P0, pP1P1P1);
        expResult = new V3D_Envelope(oom, rm, pP0P0P0, pP1P1P1);
        result = instance.getIntersection(en, oom, rm);
        assertTrue(result.equals(expResult, oom, rm));
        // Test 2
        en = new V3D_Envelope(oom, rm, pN1N1N1, pP0P0P0);
        instance = new V3D_Envelope(oom, rm, pP0P0P0, pP1P1P1);
        expResult = new V3D_Envelope(oom, rm, pP0P0P0);
        result = instance.getIntersection(en, oom, rm);
        assertTrue(result.equals(expResult, oom, rm));
        // Test 3
        en = new V3D_Envelope(oom, rm, pN1N1N1, pP0P0P0);
        instance = new V3D_Envelope(oom, rm, pP0P0P0, pP1P1P1);
        expResult = new V3D_Envelope(oom, rm, pP0P0P0);
        result = instance.getIntersection(en, oom, rm);
        assertTrue(result.equals(expResult, oom, rm));
        // Test 4
        en = new V3D_Envelope(oom, rm, pN1N1N1, pP0P0P1);
        instance = new V3D_Envelope(oom, rm, pP0P0N1, pP1P1P1);
        expResult = new V3D_Envelope(oom, rm, pP0P0N1, pP0P0P1);
        result = instance.getIntersection(en, oom, rm);
        assertTrue(result.equals(expResult, oom, rm));
    }

//    /**
//     * Test of getIntersection method, of class V3D_Envelope.
//     */
//    @Test
//    public void testGetIntersection_V3D_Line_int() {
//        System.out.println("getIntersection");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_Line li = new V3D_Line(pP0P0P0, pP0P0P1, oom, rm);
//        V3D_Envelope instance = new V3D_Envelope(oom, rm, pN1N1N1, pP1P1P1);
//        V3D_Geometry expResult = new V3D_LineSegment(pP0P0N1, pP0P0P1, oom, rm);
//        V3D_Geometry result = instance.getIntersection(li, oom, rm);
//        assertTrue(((V3D_LineSegment) result).equalsIgnoreDirection((V3D_LineSegment) expResult, oom, rm));
//        // Test 2
//        li = new V3D_Line(pP0P0P0, pP0P1P0, oom, rm);
//        expResult = new V3D_LineSegment(pP0N1P0, pP0P1P0, oom, rm);
//        result = instance.getIntersection(li, oom, rm);
//        assertTrue(((V3D_LineSegment) result).equalsIgnoreDirection((V3D_LineSegment) expResult, oom, rm));
//        // Test 3
//        li = new V3D_Line(pP0P0P0, pP1P0P0, oom, rm);
//        expResult = new V3D_LineSegment(pN1P0P0, pP1P0P0, oom, rm);
//        result = instance.getIntersection(li, oom, rm);
//        assertTrue(((V3D_LineSegment) result).equalsIgnoreDirection((V3D_LineSegment) expResult, oom, rm));
//        // Test 4
//        li = new V3D_Line(pP1P1P1, pP0P2P1, oom, rm);
//        expResult = pP1P1P1;
//        result = instance.getIntersection(li, oom, rm);
//        assertTrue(((V3D_Point) result).equals((V3D_Point) expResult, oom, rm));
//        // Test 5
//        li = new V3D_Line(pP1P1P1, pP0P2P1, oom, rm);
//        expResult = pP1P1P1;
//        result = instance.getIntersection(li, oom, rm);
//        assertTrue(((V3D_Point) result).equals((V3D_Point) expResult, oom, rm));
//        // Test 6 Intersection of a corner at a point
//        li = new V3D_Line(pP1P1P1, pP0P2P1, oom, rm);
//        expResult = pP1P1P1;
//        result = instance.getIntersection(li, oom, rm);
//        assertTrue(((V3D_Point) result).equals((V3D_Point) expResult, oom, rm));
//        // Test 6 Intersection of an edge at a point
//        li = new V3D_Line(pP1P1P0, pP0P2P2, oom, rm);
//        expResult = pP1P1P0;
//        result = instance.getIntersection(li, oom, rm);
//        assertTrue(((V3D_Point) result).equals((V3D_Point) expResult, oom, rm));
//        // To do: add some expResult = null test cases
//    }

    /**
     * Test of union method, of class V3D_Envelope.
     */
    @Test
    public void testUnion() {
        System.out.println("union");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Envelope en = new V3D_Envelope(oom, rm, pN2N2N2, pP1P1P1);
        V3D_Envelope instance = new V3D_Envelope(oom, rm, pN1N1N1, pP2P2P2);
        V3D_Envelope expResult = new V3D_Envelope(oom, rm, pN2N2N2, pP2P2P2);
        V3D_Envelope result = instance.union(en, oom, rm);
        assertTrue(result.equals(expResult, oom, rm));
    }

    /**
     * Test of isContainedBy method, of class V3D_Envelope.
     */
    @Test
    public void testIsContainedBy() {
        System.out.println("isContainedBy");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Envelope en = new V3D_Envelope(oom, rm, pN2N2N2, pP2P2P2);
        V3D_Envelope instance = new V3D_Envelope(oom, rm, pN1N1N1, pP1P1P1);
        assertTrue(instance.isContainedBy(en, oom, rm));
        // Test 2
        instance = new V3D_Envelope(oom, rm, pN2N2N2, pP2P2P2);
        assertTrue(instance.isContainedBy(en, oom, rm));
        // Test 3
        en = new V3D_Envelope(oom, rm, pN1N1N1, pP2P2P2);
        assertFalse(instance.isContainedBy(en, oom, rm));
    }

//    /**
//     * Test of getIntersection method, of class V3D_Envelope.
//     */
//    @Test
//    public void testGetIntersection_V3D_LineSegment_int() {
//        System.out.println("getIntersection");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_LineSegment li = new V3D_LineSegment(pN2N2N2, pP0P0P0, oom, rm);
//        V3D_Envelope instance = new V3D_Envelope(oom, rm, pN1N1N1, pP1P1P1);
//        V3D_Geometry expResult = new V3D_LineSegment(pN1N1N1, pP0P0P0, oom, rm);
//        V3D_Geometry result = instance.getIntersection(li, oom, rm);
//        assertTrue(((V3D_LineSegment) result).equalsIgnoreDirection((V3D_LineSegment) expResult, oom, rm));
//        // Test 2
//        li = new V3D_LineSegment(pP0P0P0, pP0P1P0, oom, rm);
//        expResult = new V3D_LineSegment(pP0P0P0, pP0P1P0, oom, rm);
//        result = instance.getIntersection(li, oom, rm);
//        assertTrue(((V3D_LineSegment) result).equalsIgnoreDirection((V3D_LineSegment) expResult, oom, rm));
//        // Test 3
//        li = new V3D_LineSegment(pP0N1P0, pP0P1P0, oom, rm);
//        expResult = new V3D_LineSegment(pP0N1P0, pP0P1P0, oom, rm);
//        result = instance.getIntersection(li, oom, rm);
//        assertTrue(((V3D_LineSegment) result).equalsIgnoreDirection((V3D_LineSegment) expResult, oom, rm));
//        // Test 4
//        li = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
//        expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
//        result = instance.getIntersection(li, oom, rm);
//        assertTrue(((V3D_LineSegment) result).equalsIgnoreDirection((V3D_LineSegment) expResult, oom, rm));
//        // Test 5
//        li = new V3D_LineSegment(pN1P0P0, pP1P0P0, oom, rm);
//        expResult = new V3D_LineSegment(pN1P0P0, pP1P0P0, oom, rm);
//        result = instance.getIntersection(li, oom, rm);
//        assertTrue(((V3D_LineSegment) result).equalsIgnoreDirection((V3D_LineSegment) expResult, oom, rm));
//        // Test 6
//        li = new V3D_LineSegment(pN2P0P0, pP1P0P0, oom, rm);
//        expResult = new V3D_LineSegment(pN1P0P0, pP1P0P0, oom, rm);
//        result = instance.getIntersection(li, oom, rm);
//        assertTrue(((V3D_LineSegment) result).equalsIgnoreDirection((V3D_LineSegment) expResult, oom, rm));
//        // Test 7
//        li = new V3D_LineSegment(pP1P1P1, pP0P2P1, oom, rm);
//        expResult = pP1P1P1;
//        result = instance.getIntersection(li, oom, rm);
//        assertTrue(((V3D_Point) result).equals((V3D_Point) expResult, oom, rm));
//        // Test 8
//        li = new V3D_LineSegment(pP1P1P1, pP0P2P1, oom, rm);
//        expResult = pP1P1P1;
//        result = instance.getIntersection(li, oom, rm);
//        assertTrue(((V3D_Point) result).equals((V3D_Point) expResult, oom, rm));
//        // Test 9 Intersection of a corner at a point
//        li = new V3D_LineSegment(pP1P1P1, pP0P2P1, oom, rm);
//        expResult = pP1P1P1;
//        result = instance.getIntersection(li, oom, rm);
//        assertTrue(((V3D_Point) result).equals((V3D_Point) expResult, oom, rm));
//        // Test 10 Intersection of an edge at a point
//        li = new V3D_LineSegment(pP1P1P0, pP2P2P0, oom, rm);
//        expResult = pP1P1P0;
//        result = instance.getIntersection(li, oom, rm);
//        assertTrue(((V3D_Point) result).equals((V3D_Point) expResult, oom, rm));
//        // To do: add some expResult = null test cases.
//    }

//    /**
//     * Test of getEnvelope method, of class V3D_Envelope.
//     */
//    @Test
//    public void testGetEnvelope() {
//        System.out.println("getEnvelope");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_Envelope instance = new V3D_Envelope(oom, rm, pP0P0P0);
//        V3D_Envelope expResult = new V3D_Envelope(oom, rm, pP0P0P0);
//        V3D_Envelope result = instance.getEnvelope(oom, rm);
//        assertTrue(result.equals(expResult, oom, rm));
//    }

    /**
     * Test of getXMin method, of class V3D_Envelope.
     */
    @Test
    public void testGetXMin() {
        System.out.println("getxMin");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Envelope instance = new V3D_Envelope(oom, rm, pP0N1N1, pP0N1P0, pN2N2N2);
        Math_BigRational expResult = N2;
        Math_BigRational result = instance.getXMin(oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getXMax method, of class V3D_Envelope.
     */
    @Test
    public void testGetXMax() {
        System.out.println("getxMax");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Envelope instance = new V3D_Envelope(oom, rm, pP0N1N1, pP0N1P0, pN2N2N2);
        Math_BigRational expResult = P0;
        Math_BigRational result = instance.getXMax(oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getYMin method, of class V3D_Envelope.
     */
    @Test
    public void testGetYMin() {
        System.out.println("getyMin");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Envelope instance = new V3D_Envelope(oom, rm, pP0N1N1, pP0N1P0, pN2N2N2);
        Math_BigRational expResult = N2;
        Math_BigRational result = instance.getYMin(oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getYMax method, of class V3D_Envelope.
     */
    @Test
    public void testGetYMax() {
        System.out.println("getyMax");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Envelope instance = new V3D_Envelope(oom, rm, pP0N1N1, pP0N1P0, pN2N2N2);
        Math_BigRational expResult = N1;
        Math_BigRational result = instance.getYMax(oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getZMin method, of class V3D_Envelope.
     */
    @Test
    public void testGetZMin() {
        System.out.println("getzMin");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Envelope instance = new V3D_Envelope(oom, rm, pP0N1N1, pP0N1P0, pN2N2N2);
        Math_BigRational expResult = N2;
        Math_BigRational result = instance.getZMin(oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getZMax method, of class V3D_Envelope.
     */
    @Test
    public void testGetZMax() {
        System.out.println("getzMax");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Envelope instance = new V3D_Envelope(oom, rm, pP0N1N1, pP0N1P0, pN2N2N2);
        Math_BigRational expResult = P0;
        Math_BigRational result = instance.getZMax(oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

//    /**
//     * Test of isIntersectedBy method, of class V3D_Envelope.
//     */
//    @Test
//    public void testIsIntersectedBy_V3D_Line_int() {
//        System.out.println("isIntersectedBy");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_Line li = new V3D_Line(pP0P0P0, pP0P0P1, oom, rm);
//        V3D_Envelope instance = new V3D_Envelope(oom, rm, pN1N1N1, pP1P1P1);
//        assertTrue(instance.isIntersectedBy(li, oom, rm));
//        // Test 2
//        li = new V3D_Line(pP0P0P0, pP0P1P0, oom, rm);
//        assertTrue(instance.isIntersectedBy(li, oom, rm));
//        // Test 3
//        li = new V3D_Line(pP0P0P0, pP1P0P0, oom, rm);
//        assertTrue(instance.isIntersectedBy(li, oom, rm));
//        // Test 4
//        li = new V3D_Line(pP1P1P1, pP0P2P1, oom, rm);
//        assertTrue(instance.isIntersectedBy(li, oom, rm));
//        // Test 5
//        li = new V3D_Line(pP1P1P1, pP0P2P1, oom, rm);
//        assertTrue(instance.isIntersectedBy(li, oom, rm));
//        // Test 6 Intersection of a corner at a point
//        li = new V3D_Line(pP1P1P1, pP0P2P1, oom, rm);
//        assertTrue(instance.isIntersectedBy(li, oom, rm));
//        // Test 6 Intersection of an edge at a point
//        li = new V3D_Line(pP1P1P0, pP0P2P2, oom, rm);
//        assertTrue(instance.isIntersectedBy(li, oom, rm));
//        // Test 7 Internal
//        instance = new V3D_Envelope(oom, rm, pN2N2N2, pP2P2P2);
//        li = new V3D_Line(pP0P0P0, pP0P1P0, oom, rm);
//        assertTrue(instance.isIntersectedBy(li, oom, rm));
//        // To do: add some false test cases.
//        li = V3D_Line.X_AXIS;
//        instance = new V3D_Envelope(oom, rm, pP0P0P0);
//        assertTrue(instance.isIntersectedBy(li, oom, rm));
//    }

    /**
     * Test of isIntersectedBy method, of class V3D_Envelope.
     */
    @Test
    public void testIsIntersectedBy_3args_1() {
        System.out.println("isIntersectedBy");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        Math_BigRational x;
        Math_BigRational y;
        Math_BigRational z;
        V3D_Envelope instance;
        // Test 1
        x = P0;
        y = P0;
        z = P0;
        instance = new V3D_Envelope(oom, rm, pP0P0P0);
        assertTrue(instance.isIntersectedBy(x, y, z, oom, rm));
        // Test 2
        instance = new V3D_Envelope(oom, rm, pP1P0P0);
        assertFalse(instance.isIntersectedBy(x, y, z, oom, rm));
    }

//    /**
//     * Test of isIntersectedBy method, of class V3D_Envelope.
//     */
//    @Test
//    public void testIsIntersectedBy_V3D_LineSegment_int() {
//        System.out.println("isIntersectedBy");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_LineSegment li = new V3D_LineSegment(pN2N2N2, pP0P0P0, oom, rm);
//        V3D_Envelope instance = new V3D_Envelope(oom, rm, pN1N1N1, pP1P1P1);
//        assertTrue(instance.isIntersectedBy(li, oom, rm));
//        // Test 2
//        li = new V3D_LineSegment(pP0P0P0, pP0P1P0, oom, rm);
//        assertTrue(instance.isIntersectedBy(li, oom, rm));
//        // Test 3
//        li = new V3D_LineSegment(pP0N1P0, pP0P1P0, oom, rm);
//        assertTrue(instance.isIntersectedBy(li, oom, rm));
//        // Test 4
//        li = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
//        assertTrue(instance.isIntersectedBy(li, oom, rm));
//        // Test 5
//        li = new V3D_LineSegment(pN1P0P0, pP1P0P0, oom, rm);
//        assertTrue(instance.isIntersectedBy(li, oom, rm));
//        // Test 6
//        li = new V3D_LineSegment(pN2P0P0, pP1P0P0, oom, rm);
//        assertTrue(instance.isIntersectedBy(li, oom, rm));
//        // Test 7
//        li = new V3D_LineSegment(pP1P1P1, pP0P2P1, oom, rm);
//        assertTrue(instance.isIntersectedBy(li, oom, rm));
//        // Test 8
//        li = new V3D_LineSegment(pP1P1P1, pP0P2P1, oom, rm);
//        assertTrue(instance.isIntersectedBy(li, oom, rm));
//        // Test 9 Intersection of a corner at a point
//        li = new V3D_LineSegment(pP1P1P1, pP0P2P1, oom, rm);
//        assertTrue(instance.isIntersectedBy(li, oom, rm));
//        // Test 10 Intersection of an edge at a point
//        li = new V3D_LineSegment(pP1P1P0, pP0P2P2, oom, rm);
//        assertTrue(instance.isIntersectedBy(li, oom, rm));
//        // To do: add some false test cases.
//    }

//    /**
//     * Test of isEnvelopeIntersectedBy method, of class V3D_Envelope.
//     */
//    @Test
//    public void testIsEnvelopeIntersectedBy() {
//        System.out.println("isEnvelopeIntersectedBy");
//        V3D_Line l = new V3D_Line(pP0P0P0, pP1P0P0);
//        V3D_Envelope instance = new V3D_Envelope(pN1N1N1, pP1P1P1);
//        assertTrue(instance.isEnvelopeIntersectedBy(l, oom, rm));
//        // Test 2
//        l = new V3D_Line(pN1N1N1, pN1N1P0);
//        instance = new V3D_Envelope(pP0P0P0, pP1P1P1);
//        assertFalse(instance.isEnvelopeIntersectedBy(l, oom, rm));
//    }
    /**
     * Test of translate method, of class V3D_Envelope.
     */
    @Test
    public void testTranslate() {
        System.out.println("translate");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Vector v = P1P1P1;
        V3D_Envelope instance = new V3D_Envelope(oom, rm, pP0P0P0, pP1P1P1);
        V3D_Envelope expResult = new V3D_Envelope(oom, rm, pP1P1P1, pP2P2P2);
        instance.translate(v, oom, rm);
        assertTrue(expResult.equals(instance, oom, rm));
        // Test 2
        v = N1N1N1;
        instance = new V3D_Envelope(oom, rm, pP0P0P0, pP1P1P1);
        expResult = new V3D_Envelope(oom, rm, pN1N1N1, pP0P0P0);
        instance.translate(v, oom, rm);
        assertTrue(expResult.equals(instance, oom, rm));
    }

//    /**
//     * Test of getDistance method, of class V3D_Envelope.
//     */
//    @Test
//    public void testGetDistance_V3D_Point_int() {
//        System.out.println("getDistance");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_Point p = pP0P0P0;
//        V3D_Envelope instance = new V3D_Envelope(oom, rm, pN1N1N1, pP1P1P1);
//        BigDecimal expResult = new Math_BigRationalSqrt(0, oom, rm).toBigDecimal(oom, rm);
//        BigDecimal result = instance.getDistance(p, oom, rm);
//        assertTrue(expResult.compareTo(result) == 0);
//        // Test
//        instance = new V3D_Envelope(oom, rm, pN1N1N1, pP1P1P1);
//        // Corners
//        // Test 2
//        result = instance.getDistance(pN2N2N2, oom, rm);
//        expResult = new Math_BigRationalSqrt(3, oom, rm).toBigDecimal(oom, rm);
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
//        expResult = new Math_BigRationalSqrt(2, oom, rm).toBigDecimal(oom, rm);
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
//        expResult = new Math_BigRationalSqrt(1, oom, rm).toBigDecimal(oom, rm);
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

    /**
     * Test of isIntersectedBy method, of class V3D_Envelope.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point_int() {
        System.out.println("isIntersectedBy");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point p = V3D_Point.ORIGIN;
        V3D_Envelope instance = new V3D_Envelope(oom, rm, pP0P0P0);
        assertTrue(instance.isIntersectedBy(p, oom, rm));
        // Test 2
        instance = new V3D_Envelope(oom, rm, pP1P0P0);
        assertFalse(instance.isIntersectedBy(p, oom, rm));
    }

//    /**
//     * Test of isIntersectedBy method, of class V3D_Envelope.
//     */
//    @Test
//    public void testIsIntersectedBy_V3D_Triangle_int() {
//        System.out.println("isIntersectedBy");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_Triangle t;
//        V3D_Envelope instance;
//        // Test 1
//        t = new V3D_Triangle(pN2P2P0, pN2N2P0, pP2P0P0, oom, rm);
//        instance = new V3D_Envelope(oom, rm, pN1N1N1, pP1P1P1);
//        assertTrue(instance.isIntersectedBy(t, oom, rm));
//        // Test 2
//        t = new V3D_Triangle(new V3D_Point(N10, P2, P0), new V3D_Point(N10, N2, P0), pP2P0P0, oom, rm);
//        instance = new V3D_Envelope(oom, rm, pN1N1N1, pP1P1P1);
//        assertTrue(instance.isIntersectedBy(t, oom, rm));
//        // Test 2
//        t = new V3D_Triangle(new V3D_Point(N10, P10, P0), new V3D_Point(N10, N10, P0), new V3D_Point(P10, P0, P0), oom, rm);
//        instance = new V3D_Envelope(oom, rm, pN1N1N1, pP1P1P1);
//        assertTrue(instance.isIntersectedBy(t, oom, rm));
//    }

//    /**
//     * Test of isIntersectedBy method, of class V3D_Envelope.
//     */
//    @Test
//    public void testIsIntersectedBy_V3D_Plane_int() {
//        System.out.println("isIntersectedBy");
//        int oom = -3;
//        RoundingMode rm = RoundingMode.HALF_UP;
//        V3D_Plane p = V3D_Plane.X0;
//        V3D_Envelope instance = new V3D_Envelope(oom, rm, pP0P0P0);
//        assertTrue(instance.isIntersectedBy(p, oom, rm));
//        // Test 2
//        instance = new V3D_Envelope(oom, rm, pN1N1N1, pP1P1P1);
//        assertTrue(instance.isIntersectedBy(p, oom, rm));
//        // Test 3
//        p = new V3D_Plane(pN2P2P0, pP2N2P0, pP0P0P2, oom, rm);
//        assertTrue(instance.isIntersectedBy(p, oom, rm));
//    }

    /**
     * Test of getViewport method, of class V3D_Envelope.
     */
    @Test
    public void testGetViewport() {
        System.out.println("getViewport");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point pt;
        V3D_Vector v;
        V3D_Envelope instance;
        V3D_Rectangle expResult;
        V3D_Rectangle result;
        instance = new V3D_Envelope(oom, rm, pN1N1N1, pP1P1P1);
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
        expResult = new V3D_Rectangle(pN1P1N1, pP1P1N1, pP1N1N1, pN1N1N1, oom, rm);
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
        
        
        // Test front left edge.
        pt = pN2P0N2;
        Math_BigRational N1_5 = Math_BigRational.valueOf(3, 2).negate();
        Math_BigRational N0_5 = Math_BigRational.valueOf(1, 2).negate();
        // Test 25
        v = V3D_Vector.I.add(V3D_Vector.K.reverse(), oom, rm);
        result = instance.getViewport(pt, v, oom, rm);
        expResult = new V3D_Rectangle(
                new V3D_Point(N1_5, N1, N0_5),
                new V3D_Point(N1_5, P1, N0_5),
                new V3D_Point(N0_5, P1, N1_5),
                new V3D_Point(N0_5, N1, N1_5), oom, rm);
        //expResult = new V3D_Rectangle(pN2N1P0, pN2P1P0, pP0P1N2, pP0N1N2, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 26
        //v = v.getCrossProduct(new V3D_Vector(pN2P0N2, oom, rm), oom, rm);
        v = V3D_Vector.J;
        result = instance.getViewport(pt, v, oom, rm);
        expResult = new V3D_Rectangle(
                new V3D_Point(N0_5, N1, N1_5),
                new V3D_Point(N1_5, N1, N0_5),
                new V3D_Point(N1_5, P1, N0_5),
                new V3D_Point(N0_5, P1, N1_5), oom, rm);
        //expResult = new V3D_Rectangle(pP0N1N2, pN2N1P0, pN2P1P0, pP0P1N2, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 27
        v = V3D_Vector.I.add(V3D_Vector.K.reverse(), oom, rm).reverse();
        result = instance.getViewport(pt, v, oom, rm);
        expResult = new V3D_Rectangle(
                new V3D_Point(N0_5, P1, N1_5), 
                new V3D_Point(N0_5, N1, N1_5),
                new V3D_Point(N1_5, N1, N0_5),
                new V3D_Point(N1_5, P1, N0_5),oom, rm);
        //expResult = new V3D_Rectangle(pP0P1N2, pP0N1N2, pN2N1P0, pN2P1P0, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 28
        v = V3D_Vector.J.reverse();
        result = instance.getViewport(pt, v, oom, rm);
        expResult = new V3D_Rectangle(
                new V3D_Point(N0_5, P1, N1_5),
                new V3D_Point(N1_5, P1, N0_5),
                new V3D_Point(N1_5, N1, N0_5),
                new V3D_Point(N0_5, N1, N1_5), oom, rm);
        //expResult = new V3D_Rectangle(pN2P1P0, pP0P1N2, pP0N1N2, pN2N1P0, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
//        // Test front left lower corner.
//        pt = pN2N2N2;
//        // Test 29
//        v = V3D_Vector.I.add(V3D_Vector.K, oom, rm);
//        result = instance.getViewport(pt, v, oom, rm);
//        expResult = new V3D_Rectangle(
//                new V3D_Point(N1_5, N1, N0_5),
//                new V3D_Point(N1_5, P1, N0_5),
//                new V3D_Point(N0_5, P1, N1_5),
//                new V3D_Point(N0_5, N1, N1_5), oom, rm);
//        assertTrue(expResult.equals(result, oom, rm));
//        // Test 30
//        v = V3D_Vector.J;
//        result = instance.getViewport(pt, v, oom, rm);
//        expResult = new V3D_Rectangle(
//                new V3D_Point(N1_5, N1, N0_5),
//                new V3D_Point(N0_5, N1, N1_5),
//                new V3D_Point(N0_5, P1, N1_5),
//                new V3D_Point(N1_5, P1, N0_5), oom, rm);
//        assertTrue(expResult.equals(result, oom, rm));
//        // Test 31
//        v = V3D_Vector.I.add(V3D_Vector.K.reverse(), oom, rm).reverse();
//        result = instance.getViewport(pt, v, oom, rm);
//        expResult = new V3D_Rectangle(
//                new V3D_Point(N0_5, N1, N1_5),
//                new V3D_Point(N0_5, P1, N1_5),
//                new V3D_Point(N1_5, P1, N0_5),
//                new V3D_Point(N1_5, N1, N0_5), oom, rm);
//        assertTrue(expResult.equals(result, oom, rm));
//        // Test 32
//        v = V3D_Vector.J.reverse();
//        result = instance.getViewport(pt, v, oom, rm);
//        expResult = new V3D_Rectangle(
//                new V3D_Point(N0_5, P1, N1_5),
//                new V3D_Point(N1_5, P1, N0_5),
//                new V3D_Point(N1_5, N1, N0_5),
//                new V3D_Point(N0_5, N1, N1_5), oom, rm);
//        assertTrue(expResult.equals(result, oom, rm));
    }
}
