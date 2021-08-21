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

import ch.obermuhlner.math.big.BigRational;
import java.math.BigDecimal;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Disabled;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.generic.io.Generic_Defaults;
import uk.ac.leeds.ccg.math.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Geometry;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Line;
import uk.ac.leeds.ccg.v3d.geometry.V3D_LineSegment;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Point;
import uk.ac.leeds.ccg.v3d.V3D_Test;

/**
 * V3D_EnvelopeTest
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_EnvelopeTest extends V3D_Test {

    private static final long serialVersionUID = 1L;

    public V3D_EnvelopeTest() throws Exception {
        super(new V3D_Environment(new Generic_Environment(
                new Generic_Defaults())));
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
     * A master controller for all tests.
     */
    //@Test
    public void testAll() {
        testToString();
        testEnvelope();
        testIsContainedBy();
        testIsIntersectedBy_V3D_Envelope();
        testIsIntersectedBy_V3D_Point();
        testIsIntersectedBy_3args();
        testGetEnvelope3D();
        testEquals_Object();
        testGetIntersection_V3D_Envelope();
        testGetIntersection_V3D_Line();
        testUnion();
        testIsContainedBy();
        testGetIntersection_V3D_LineSegment_boolean();
        testGetEnvelope();
    }

    /**
     * Test of toString method, of class V3D_Envelope.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        V3D_Envelope instance = new V3D_Envelope(P0P0P0);
        String expResult = "V3D_Envelope(xMin=0, xMax=0, yMin=0, yMax=0"
                + ", zMin=0, zMax=0)";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of envelop method, of class V3D_Envelope.
     */
    @Test
    public void testEnvelope() {
        System.out.println("envelope");
        V3D_Envelope e1 = new V3D_Envelope(P0P0P0, P0P0P0);
        V3D_Envelope instance = new V3D_Envelope(P1P1P1, P1P1P1);
        V3D_Envelope expResult = new V3D_Envelope(P0P0P0, P1P1P1);
        V3D_Envelope result = instance.union(e1);
        assertEquals(expResult, result);
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Envelope.
     */
    @Test
    public void testIsIntersectedBy_V3D_Envelope() {
        System.out.println("isIntersectedBy");
        V3D_Envelope instance = new V3D_Envelope(P0P0P0, P0P0P0);
        V3D_Envelope e = new V3D_Envelope(P0P0P0, P1P1P1);
        assertTrue(instance.isIntersectedBy(e));
        // Test 2
        instance = new V3D_Envelope(N1N1N1, P0P0P0);
        assertTrue(instance.isIntersectedBy(e));
        // Test 3
        instance = new V3D_Envelope(N1N1N1, N1P0P0);
        assertFalse(instance.isIntersectedBy(e));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Envelope.
     */
    @Test
    public void testIsIntersectedBy_V3D_Point() {
        System.out.println("isIntersectedBy");
        V3D_Envelope instance = new V3D_Envelope(N1N1N1, P1P1P1);
        // Test 1 the centre
        assertTrue(instance.isIntersectedBy(P0P0P0));
        // Test 2 to 9 the corners
        // Test 2
        assertTrue(instance.isIntersectedBy(P1P1P1));
        // Test 3
        assertTrue(instance.isIntersectedBy(N1N1N1));
        // Test 4
        assertTrue(instance.isIntersectedBy(N1N1P1));
        // Test 5
        assertTrue(instance.isIntersectedBy(N1P1N1));
        // Test 6
        assertTrue(instance.isIntersectedBy(P1N1N1));
        // Test 7
        assertTrue(instance.isIntersectedBy(P1P1N1));
        // Test 8
        assertTrue(instance.isIntersectedBy(P1N1P1));
        // Test 9
        assertTrue(instance.isIntersectedBy(N1P1P1));
        // Test 10 to 21 edge mid points
        // Test 10
        assertTrue(instance.isIntersectedBy(N1N1P0));
        // Test 11
        assertTrue(instance.isIntersectedBy(N1P0N1));
        // Test 12
        assertTrue(instance.isIntersectedBy(P0N1N1));
        // Test 13
        assertTrue(instance.isIntersectedBy(P1P1P0));
        // Test 14
        assertTrue(instance.isIntersectedBy(P1P0P1));
        // Test 15
        assertTrue(instance.isIntersectedBy(P0P1P1));
        // Test 16
        assertTrue(instance.isIntersectedBy(P0N1P1));
        // Test 17
        assertTrue(instance.isIntersectedBy(N1P1P0));
        // Test 18
        assertTrue(instance.isIntersectedBy(P1P0N1));
        // Test 19
        assertTrue(instance.isIntersectedBy(P0P1N1));
        // Test 20
        assertTrue(instance.isIntersectedBy(P1N1P0));
        // Test 21
        assertTrue(instance.isIntersectedBy(N1P0P1));
        // Test 22 to 28 face mid points
        // Test 22
        assertTrue(instance.isIntersectedBy(P0P0P1));
        // Test 23
        assertTrue(instance.isIntersectedBy(P0P1P0));
        // Test 24
        assertTrue(instance.isIntersectedBy(P1P0P0));
        // Test 25
        assertTrue(instance.isIntersectedBy(P0P0N1));
        // Test 26
        assertTrue(instance.isIntersectedBy(P0N1P0));
        // Test 27
        assertTrue(instance.isIntersectedBy(N1P0P0));
    }

    /**
     * Test of getEnvelope method, of class V3D_Envelope.
     */
    @Test
    public void testGetEnvelope3D() {
        System.out.println("getEnvelope3D");
        V3D_Point p0 = P0P0P0;
        V3D_Point p1 = P1P1P1;
        V3D_Envelope instance = new V3D_Envelope(p0, p1);
        V3D_Envelope expResult = new V3D_Envelope(p0, p1);
        V3D_Envelope result = instance.getEnvelope();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class V3D_Envelope.
     */
    @Test
    public void testEquals_Object() {
        System.out.println("equals");
        V3D_Envelope instance = new V3D_Envelope(P0P0P0, P1P1P1);
        Object o = new V3D_Envelope(P0P0P0, P1P1P1);
        assertTrue(instance.equals(o));
        // Test 2
        o = new V3D_Envelope(P1P1P1, P0P0P0);
        assertTrue(instance.equals(o));
        // Test 3
        o = new V3D_Envelope(P1N1P1, P0P0P0);
        assertFalse(instance.equals(o));
    }
    
    /**
     * Test of equals method, of class V3D_Envelope.
     * Test covered by {@link #testEquals_Object()}.
     */
    @Test
    public void testEquals_V3D_Geometry() {
    }

    /**
     * Test of equals method, of class V3D_Envelope.
     * Test covered by {@link #testEquals_Object()}.
     */
    @Test
    public void testEquals_V3D_Envelope() {
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Envelope.
     */
    @Test
    public void testIsIntersectedBy_3args() {
        System.out.println("isIntersectedBy");
        V3D_Envelope instance = new V3D_Envelope(N1N1N1, P1P1P1);
        assertTrue(instance.isIntersectedBy(P0, P0, P0));
        /**
         * No need for a full set of test here as this is covered by
         * testIsIntersectedBy_V3D_Point()
         */
    }

    /**
     * Test of hashCode method, of class V3D_Envelope.
     */
    @Test
    @Disabled
    public void testHashCode() {
        System.out.println("hashCode");
        // No test.
    }

    /**
     * Test of getIntersection method, of class V3D_Envelope.
     */
    @Test
    public void testGetIntersection_V3D_Envelope() {
        System.out.println("getIntersection");
        V3D_Envelope en;
        V3D_Envelope instance;
        V3D_Envelope expResult;
        V3D_Envelope result;
        // Test 1
        en = new V3D_Envelope(N1N1N1, P1P1P1);
        instance = new V3D_Envelope(P0P0P0, P1P1P1);
        expResult = new V3D_Envelope(P0P0P0, P1P1P1);
        result = instance.getIntersection(en);
        assertEquals(expResult, result);
        // Test 2
        en = new V3D_Envelope(N1N1N1, P0P0P0);
        instance = new V3D_Envelope(P0P0P0, P1P1P1);
        expResult = new V3D_Envelope(P0P0P0);
        result = instance.getIntersection(en);
        assertEquals(expResult, result);
        // Test 3
        en = new V3D_Envelope(N1N1N1, P0P0P0);
        instance = new V3D_Envelope(P0P0P0, P1P1P1);
        expResult = new V3D_Envelope(P0P0P0);
        result = instance.getIntersection(en);
        assertEquals(expResult, result);
        // Test 4
        en = new V3D_Envelope(N1N1N1, P0P0P1);
        instance = new V3D_Envelope(P0P0N1, P1P1P1);
        expResult = new V3D_Envelope(P0P0N1, P0P0P1);
        result = instance.getIntersection(en);
        assertEquals(expResult, result);
    }

    /**
     * Test of getIntersection method, of class V3D_Envelope.
     */
    @Test
    public void testGetIntersection_V3D_Line() {
        System.out.println("getIntersection");
        V3D_Line li = new V3D_Line(P0P0P0, P0P0P1);
        V3D_Envelope instance = new V3D_Envelope(N1N1N1, P1P1P1);
        V3D_Geometry expResult = new V3D_LineSegment(P0P0N1, P0P0P1);
        V3D_Geometry result = instance.getIntersection(li);
        assertEquals(expResult, result);
        // Test 2
        li = new V3D_Line(P0P0P0, P0P1P0);
        expResult = new V3D_LineSegment(P0N1P0, P0P1P0);
        result = instance.getIntersection(li);
        assertEquals(expResult, result);
        // Test 3
        li = new V3D_Line(P0P0P0, P1P0P0);
        expResult = new V3D_LineSegment(N1P0P0, P1P0P0);
        result = instance.getIntersection(li);
        assertEquals(expResult, result);
        // Test 4
        li = new V3D_Line(P1P1P1, new V3D_Point(P0, P2, P1));
        expResult = P1P1P1;
        result = instance.getIntersection(li);
        assertEquals(expResult, result);
        // Test 5
        li = new V3D_Line(P1P1P1, new V3D_Point(P0, P2, P1));
        expResult = P1P1P1;
        result = instance.getIntersection(li);
        assertEquals(expResult, result);
        // Test 6 Intersection of a corner at a point
        li = new V3D_Line(P1P1P1, new V3D_Point(P0, P2, P1));
        expResult = P1P1P1;
        result = instance.getIntersection(li);
        assertEquals(expResult, result);
        // Test 6 Intersection of an edge at a point
        li = new V3D_Line(P1P1P0, new V3D_Point(P0, P2, P2));
        expResult = P1P1P0;
        result = instance.getIntersection(li);
        assertEquals(expResult, result);
        // To do: add some expResult = null test cases
    }

    /**
     * Test of union method, of class V3D_Envelope.
     */
    @Test
    public void testUnion() {
        System.out.println("union");
        V3D_Envelope en = new V3D_Envelope(N2N2N2, P1P1P1);
        V3D_Envelope instance = new V3D_Envelope(N1N1N1, P2P2P2);
        V3D_Envelope expResult = new V3D_Envelope(N2N2N2, P2P2P2);
        V3D_Envelope result = instance.union(en);
        assertEquals(expResult, result);
    }

    /**
     * Test of isContainedBy method, of class V3D_Envelope.
     */
    @Test
    public void testIsContainedBy() {
        System.out.println("isContainedBy");
        V3D_Envelope en = new V3D_Envelope(N2N2N2, P2P2P2);
        V3D_Envelope instance = new V3D_Envelope(N1N1N1, P1P1P1);
        assertTrue(instance.isContainedBy(en));
        // Test 2
        instance = new V3D_Envelope(N2N2N2, P2P2P2);
        assertTrue(instance.isContainedBy(en));
        // Test 3
        en = new V3D_Envelope(N1N1N1, P2P2P2);
        assertFalse(instance.isContainedBy(en));
    }

    /**
     * Test of getIntersection method, of class V3D_Envelope.
     */
    @Test
    public void testGetIntersection_V3D_LineSegment_boolean() {
        System.out.println("getIntersection");
        V3D_LineSegment li = new V3D_LineSegment(N2N2N2, P0P0P0);
        V3D_Envelope instance = new V3D_Envelope(N1N1N1, P1P1P1);
        V3D_Geometry expResult = new V3D_LineSegment(N1N1N1, P0P0P0);
        V3D_Geometry result = instance.getIntersection(li, true);
        assertEquals(expResult, result);
        // Test 2
        li = new V3D_LineSegment(P0P0P0, P0P1P0);
        expResult = new V3D_LineSegment(P0P0P0, P0P1P0);
        result = instance.getIntersection(li, true);
        assertEquals(expResult, result);
        // Test 3
        li = new V3D_LineSegment(P0N1P0, P0P1P0);
        expResult = new V3D_LineSegment(P0N1P0, P0P1P0);
        result = instance.getIntersection(li, true);
        assertEquals(expResult, result);
        // Test 4
        li = new V3D_LineSegment(P0P0P0, P1P0P0);
        expResult = new V3D_LineSegment(P0P0P0, P1P0P0);
        result = instance.getIntersection(li, true);
        assertEquals(expResult, result);
        // Test 5
        li = new V3D_LineSegment(N1P0P0, P1P0P0);
        expResult = new V3D_LineSegment(N1P0P0, P1P0P0);
        result = instance.getIntersection(li, true);
        assertEquals(expResult, result);
        // Test 6
        li = new V3D_LineSegment(new V3D_Point(N2, P0, P0), P1P0P0);
        expResult = new V3D_LineSegment(N1P0P0, P1P0P0);
        result = instance.getIntersection(li, true);
        assertEquals(expResult, result);
        // Test 7
        li = new V3D_LineSegment(P1P1P1, new V3D_Point(P0, P2, P1));
        expResult = P1P1P1;
        result = instance.getIntersection(li, true);
        assertEquals(expResult, result);
        // Test 8
        li = new V3D_LineSegment(P1P1P1, new V3D_Point(P0, P2, P1));
        expResult = P1P1P1;
        result = instance.getIntersection(li, true);
        assertEquals(expResult, result);
        // Test 9 Intersection of a corner at a point
        li = new V3D_LineSegment(P1P1P1, new V3D_Point(P0, P2, P1));
        expResult = P1P1P1;
        result = instance.getIntersection(li, true);
        assertEquals(expResult, result);
        // Test 10 Intersection of an edge at a point
        li = new V3D_LineSegment(P1P1P0, new V3D_Point(P2, P2, P0));
        expResult = P1P1P0;
        result = instance.getIntersection(li, true);
        assertEquals(expResult, result);
        // To do: add some expResult = null test cases.
    }

    /**
     * Test of getEnvelope method, of class V3D_Envelope.
     */
    @Test
    public void testGetEnvelope() {
        System.out.println("getEnvelope");
        V3D_Envelope instance = new V3D_Envelope(P0P0P0);
        V3D_Envelope expResult = new V3D_Envelope(P0P0P0);
        V3D_Envelope result = instance.getEnvelope();
        assertEquals(expResult, result);
    }

    /**
     * Test of getxMin method, of class V3D_Envelope.
     */
    @Test
    public void testGetxMin() {
        System.out.println("getxMin");
        V3D_Envelope instance = new V3D_Envelope(P0N1N1, P0N1P0, N2N2N2);
        BigRational expResult = N2;
        BigRational result = instance.getxMin();
        assertThat(expResult, Matchers.comparesEqualTo(result));
    }

    /**
     * Test of getxMax method, of class V3D_Envelope.
     */
    @Test
    public void testGetxMax() {
        System.out.println("getxMax");
        V3D_Envelope instance = new V3D_Envelope(P0N1N1, P0N1P0, N2N2N2);
        BigRational expResult = P0;
        BigRational result = instance.getxMax();
        assertThat(expResult, Matchers.comparesEqualTo(result));
    }

    /**
     * Test of getyMin method, of class V3D_Envelope.
     */
    @Test
    public void testGetyMin() {
        System.out.println("getyMin");
        V3D_Envelope instance = new V3D_Envelope(P0N1N1, P0N1P0, N2N2N2);
        BigRational expResult = N2;
        BigRational result = instance.getyMin();
        assertThat(expResult, Matchers.comparesEqualTo(result));
    }

    /**
     * Test of getyMax method, of class V3D_Envelope.
     */
    @Test
    public void testGetyMax() {
        System.out.println("getyMax");
        V3D_Envelope instance = new V3D_Envelope(P0N1N1, P0N1P0, N2N2N2);
        BigRational expResult = N1;
        BigRational result = instance.getyMax();
        assertThat(expResult, Matchers.comparesEqualTo(result));
    }

    /**
     * Test of getzMin method, of class V3D_Envelope.
     */
    @Test
    public void testGetzMin() {
        System.out.println("getzMin");
        V3D_Envelope instance = new V3D_Envelope(P0N1N1, P0N1P0, N2N2N2);
        BigRational expResult = N2;
        BigRational result = instance.getzMin();
        assertThat(expResult, Matchers.comparesEqualTo(result));
    }

    /**
     * Test of getzMax method, of class V3D_Envelope.
     */
    @Test
    public void testGetzMax() {
        System.out.println("getzMax");
        V3D_Envelope instance = new V3D_Envelope(P0N1N1, P0N1P0, N2N2N2);
        BigRational expResult = P0;
        BigRational result = instance.getzMax();
        assertThat(expResult, Matchers.comparesEqualTo(result));
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Envelope.
     */
    @Test
    public void testIsIntersectedBy_V3D_Line() {
        System.out.println("isIntersectedBy");
        V3D_Line li = new V3D_Line(P0P0P0, P0P0P1);
        V3D_Envelope instance = new V3D_Envelope(N1N1N1, P1P1P1);
        assertTrue(instance.isIntersectedBy(li));
        // Test 2
        li = new V3D_Line(P0P0P0, P0P1P0);
        assertTrue(instance.isIntersectedBy(li));
        // Test 3
        li = new V3D_Line(P0P0P0, P1P0P0);
        assertTrue(instance.isIntersectedBy(li));
        // Test 4
        li = new V3D_Line(P1P1P1, new V3D_Point(P0, P2, P1));
        assertTrue(instance.isIntersectedBy(li));
        // Test 5
        li = new V3D_Line(P1P1P1, new V3D_Point(P0, P2, P1));
        assertTrue(instance.isIntersectedBy(li));
        // Test 6 Intersection of a corner at a point
        li = new V3D_Line(P1P1P1, new V3D_Point(P0, P2, P1));
        assertTrue(instance.isIntersectedBy(li));
        // Test 6 Intersection of an edge at a point
        li = new V3D_Line(P1P1P0, new V3D_Point(P0, P2, P2));
        assertTrue(instance.isIntersectedBy(li));
        // To do: add some false test cases.
    }

    /**
     * Test of isIntersectedBy method, of class V3D_Envelope.
     */
    @Test
    public void testIsIntersectedBy_V3D_LineSegment_boolean() {
        System.out.println("isIntersectedBy");
        V3D_LineSegment li = new V3D_LineSegment(N2N2N2, P0P0P0);
        V3D_Envelope instance = new V3D_Envelope(N1N1N1, P1P1P1);
        assertTrue(instance.isIntersectedBy(li));
        // Test 2
        li = new V3D_LineSegment(P0P0P0, P0P1P0);
        assertTrue(instance.isIntersectedBy(li, true));
        // Test 3
        li = new V3D_LineSegment(P0N1P0, P0P1P0);
        assertTrue(instance.isIntersectedBy(li, true));
        // Test 4
        li = new V3D_LineSegment(P0P0P0, P1P0P0);
        assertTrue(instance.isIntersectedBy(li, true));
        // Test 5
        li = new V3D_LineSegment(N1P0P0, P1P0P0);
        assertTrue(instance.isIntersectedBy(li, true));
        // Test 6
        li = new V3D_LineSegment(new V3D_Point(N2, P0, P0), P1P0P0);
        assertTrue(instance.isIntersectedBy(li, true));
        // Test 7
        li = new V3D_LineSegment(P1P1P1, new V3D_Point(P0, P2, P1));
        assertTrue(instance.isIntersectedBy(li, true));
        // Test 8
        li = new V3D_LineSegment(P1P1P1, new V3D_Point(P0, P2, P1));
        assertTrue(instance.isIntersectedBy(li, true));
        // Test 9 Intersection of a corner at a point
        li = new V3D_LineSegment(P1P1P1, new V3D_Point(P0, P2, P1));
        assertTrue(instance.isIntersectedBy(li, true));
        // Test 10 Intersection of an edge at a point
        li = new V3D_LineSegment(P1P1P0, new V3D_Point(P0, P2, P2));
        assertTrue(instance.isIntersectedBy(li, true));
        // To do: add some false test cases.
    }

    /**
     * Test of isEnvelopeIntersectedBy method, of class V3D_Envelope.
     */
    @Test
    public void testIsEnvelopeIntersectedBy() {
        System.out.println("isEnvelopeIntersectedBy");
        V3D_Line l = new V3D_Line(P0P0P0, P1P0P0);
        V3D_Envelope instance = new V3D_Envelope(N1N1N1, P1P1P1);
        assertTrue(instance.isEnvelopeIntersectedBy(l));
        // Test 2
        l = new V3D_Line(N1N1N1, N1N1P0);
        instance = new V3D_Envelope(P0P0P0, P1P1P1);
        assertFalse(instance.isEnvelopeIntersectedBy(l));
    }

    /**
     * Test of apply method, of class V3D_Envelope.
     */
    @Test
    public void testApply() {
        System.out.println("apply");
        V3D_Vector v = new V3D_Vector(1, 1, 1);
        V3D_Envelope instance = new V3D_Envelope(P0P0P0, P1P1P1);
        V3D_Envelope expResult = new V3D_Envelope(P1P1P1, P2P2P2);
        V3D_Envelope result = instance.apply(v);
        assertEquals(expResult, result);
        // Test 2
        v = new V3D_Vector(-1, -1, -1);
        instance = new V3D_Envelope(P0P0P0, P1P1P1);
        expResult = new V3D_Envelope(N1N1N1, P0P0P0);
        result = instance.apply(v);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistance method, of class V3D_Envelope.
     */
    @Test
    public void testGetDistance() {
        System.out.println("getDistance");
        int oom = -1;
        V3D_Point p = P0P0P0;
        V3D_Envelope instance = new V3D_Envelope(N1N1N1, P1P1P1);
        BigDecimal expResult = new Math_BigRationalSqrt(0).toBigDecimal(oom);
        BigDecimal result = instance.getDistance(p, oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test
        instance = new V3D_Envelope(N1N1N1, P1P1P1);
        // Corners
        // Test 2
        result = instance.getDistance(N2N2N2, oom);
        expResult = new Math_BigRationalSqrt(3).toBigDecimal(oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 3
        result = instance.getDistance(N2N2P2, oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 4
        result = instance.getDistance(N2P2N2, oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 5
        result = instance.getDistance(N2P2P2, oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 6
        result = instance.getDistance(P2N2N2, oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 7
        result = instance.getDistance(P2N2P2, oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 8
        result = instance.getDistance(P2P2N2, oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 9
        result = instance.getDistance(P2P2P2, oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Edges
        // Test 10
        expResult = new Math_BigRationalSqrt(2).toBigDecimal(oom);
        result = instance.getDistance(N2N2P0, oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 11
        result = instance.getDistance(N2P2P0, oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 12
        result = instance.getDistance(N2P0N2, oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 13
        result = instance.getDistance(N2P0P2, oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 14
        result = instance.getDistance(P0N2P2, oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 15
        result = instance.getDistance(P0P2N2, oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 16
        result = instance.getDistance(P2P0N2, oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 17
        result = instance.getDistance(P2N2P0, oom);
        assertTrue(expResult.compareTo(result) == 0);
        // 6 planes
        // Test 18
        expResult = new Math_BigRationalSqrt(1).toBigDecimal(oom);
        result = instance.getDistance(N2P0P0, oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 19
        result = instance.getDistance(P0N2P0, oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 20
        result = instance.getDistance(P0P0N2, oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 21
        result = instance.getDistance(P2P0P0, oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 22
        result = instance.getDistance(P0P2P0, oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 23
        result = instance.getDistance(P0P0P2, oom);
        assertTrue(expResult.compareTo(result) == 0);
    }


}
