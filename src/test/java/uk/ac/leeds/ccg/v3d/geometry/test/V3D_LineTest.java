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
import java.math.BigInteger;
import java.math.RoundingMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.matrices.Math_Matrix_BR;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Geometry;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Line;
import uk.ac.leeds.ccg.v3d.geometry.V3D_LineSegment;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Point;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Ray;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Vector;

/**
 * Test class for V3D_Line.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_LineTest extends V3D_Test {

    public V3D_LineTest() {
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
     * Test of toString method, of class V3D_Line.
     */
    @Test
    public void testToString_0args() {
        System.out.println("toString");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line instance = new V3D_Line(V3D_Test.pP0P0P0, V3D_Test.pP1P0P0, oom, rm);
        String expResult = """
                           V3D_Line
                           (
                            offset=V3D_Vector(dx=0, dy=0, dz=0),
                            p=V3D_Point(offset=V3D_Vector(dx=0, dy=0, dz=0), rel=V3D_Vector(dx=0, dy=0, dz=0)),
                            v= V3D_Vector(dx=1, dy=0, dz=0)
                           )""";
        String result = instance.toString();
        //System.out.println(result);
        assertTrue(expResult.equalsIgnoreCase(result));
    }

    /**
     * Test of intersects method, of class V3D_Line.
     */
    @Test
    public void testIsIntersects_V3D_Point_int_RoundingMode() {
        System.out.println("isIntersectedBy");
        V3D_Point pt = V3D_Test.pP0P0P0;
        int oom = -1;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Environment env = new V3D_Environment(oom, rm);
        
        V3D_Line instance = new V3D_Line(V3D_Test.pN1N1N1, V3D_Test.pP1P1P1, oom, rm);
        assertTrue(instance.intersects(pt, oom, rm));
        // Test 2
        pt = new V3D_Point(env, V3D_Test.P0_1E2, V3D_Test.P0_1E2, V3D_Test.P0_1E2);
        assertTrue(instance.intersects(pt, oom, rm));
        // Test 3 works as the rounding puts pt on the line.
        pt = new V3D_Point(env, V3D_Test.P0_1E12, V3D_Test.P0_1E12, V3D_Test.P0_1E12);
        assertTrue(instance.intersects(pt, oom, rm));
        // Test 4 works as the rounding puts pt on the line.
        pt = new V3D_Point(env, V3D_Test.N0_1E12, V3D_Test.N0_1E12, V3D_Test.N0_1E12);
        assertTrue(instance.intersects(pt, oom, rm));
        // Test 5 works as the rounding puts pt on the line.
        BigRational a = V3D_Test.P0_1E2.add(V3D_Test.P1E12);
        pt = new V3D_Point(env, a, a, a);
        assertTrue(instance.intersects(pt, oom, rm));
        // Test 6 works as the rounding puts pt on the line.
        a = V3D_Test.N0_1E2.add(V3D_Test.N1E12);
        pt = new V3D_Point(env, a, a, a);
        assertTrue(instance.intersects(pt, oom, rm));
        // Test 7
        instance = new V3D_Line(V3D_Test.pP0N1N1, V3D_Test.pP2P1P1, oom, rm);
        pt = new V3D_Point(env, V3D_Test.N1, V3D_Test.N2, V3D_Test.N2);
        assertTrue(instance.intersects(pt, oom, rm));
        // Test 8 fails as the rounding does not put pt on the line.
        a = V3D_Test.N0_1E2.add(V3D_Test.N1E12);
        pt = new V3D_Point(env, a, a, a);
        assertFalse(instance.intersects(pt, oom, rm));
        pt = new V3D_Point(env, a.add(BigInteger.ONE), a, a);
        assertTrue(instance.intersects(pt, oom, rm));
        // Test 9
        a = V3D_Test.N0_1E12.add(V3D_Test.N1E12);
        oom = -2; // In this case oom = -2 is sufficient.
        pt = new V3D_Point(env, a, a, a);
        assertFalse(instance.intersects(pt, oom, rm));
        pt = new V3D_Point(env, a.add(BigInteger.ONE), a, a);
        assertTrue(instance.intersects(pt, oom, rm));
    }

    /**
     * Test of isParallel method, of class V3D_Line.
     */
    @Test
    public void testIsParallel_V3D_Line_int_RoundingMode() {
        System.out.println("isParallel");
        int oom = -1;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line l = new V3D_Line(pP0P0P0, V3D_Vector.I);
        V3D_Line instance = l;
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 2
        l = new V3D_Line(pP0P0P0, V3D_Vector.J);
        assertFalse(instance.isParallel(l, oom, rm));
        // Test 3
        l = new V3D_Line(pP0P0P0, V3D_Vector.K);
        assertFalse(instance.isParallel(l, oom, rm));
        // Test 4
        l = new V3D_Line(pP0P0P0, V3D_Vector.I);
        instance = new V3D_Line(V3D_Test.pP0P1P0, V3D_Test.pP1P1P0, oom, rm);
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 5
        instance = new V3D_Line(V3D_Test.pP0P1P0, V3D_Test.pP1P1P0, oom, rm);
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 6
        instance = new V3D_Line(V3D_Test.pP0P0P1, V3D_Test.pP1P0P1, oom, rm);
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 7
        instance = new V3D_Line(V3D_Test.pP1P0P1, V3D_Test.pP0P0P1, oom, rm);
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 8
        instance = new V3D_Line(V3D_Test.pP1P0P1, V3D_Test.pP0P1P1, oom, rm);
        assertFalse(instance.isParallel(l, oom, rm));
        // Test 9
        l = new V3D_Line(pP0P0P0, V3D_Vector.J);
        instance = new V3D_Line(V3D_Test.pP0P0P1, V3D_Test.pP0P1P1, oom, rm);
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 9
        instance = new V3D_Line(V3D_Test.pP1P0P0, V3D_Test.pP1P1P0, oom, rm);
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 10
        instance = new V3D_Line(V3D_Test.pP1P0P1, V3D_Test.pP1P1P1, oom, rm);
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 11
        instance = new V3D_Line(V3D_Test.pP1P0P1, V3D_Test.pP1P1P0, oom, rm);
        assertFalse(instance.isParallel(l, oom, rm));
        // Test 12
        l = new V3D_Line(pP0P0P0, V3D_Vector.K);
        instance = new V3D_Line(V3D_Test.pP1P0P0, V3D_Test.pP1P0P1, oom, rm);
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 9
        instance = new V3D_Line(V3D_Test.pP0P1P0, V3D_Test.pP0P1P1, oom, rm);
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 10
        instance = new V3D_Line(V3D_Test.pP1P1P0, V3D_Test.pP1P1P1, oom, rm);
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 11
        instance = new V3D_Line(V3D_Test.pP1P0P1, V3D_Test.pP1P1P0, oom, rm);
        assertFalse(instance.isParallel(l, oom, rm));
        // Test 12
        l = new V3D_Line(V3D_Test.pP1P1P1, V3D_Test.pN1N1N1, oom, rm);
        instance = new V3D_Line(V3D_Test.pP1N1P1, V3D_Test.pN1P1N1, oom, rm);
        assertFalse(instance.isParallel(l, oom, rm));
        // Test 13
        BigRational a = V3D_Test.P0_1E12.add(V3D_Test.P1E12);
        BigRational b = V3D_Test.N0_1E12.add(V3D_Test.N1E12);
        BigRational a1 = V3D_Test.P0_1E12.add(V3D_Test.P1E12).add(1);
        BigRational b1 = V3D_Test.N0_1E12.add(V3D_Test.N1E12).add(1);
        V3D_Environment env = new V3D_Environment(oom, rm);
        
        l = new V3D_Line(new V3D_Point(env, a, a, a), new V3D_Point(env, b, b, b), oom, rm);
        instance = new V3D_Line(new V3D_Point(env, a1, a, a), new V3D_Point(env, b1, b, b), oom, rm);
        assertTrue(instance.isParallel(l, oom, rm)); // Right answer, but for the wrong reason!
        // Test 14
        a = V3D_Test.P0_1E12.add(V3D_Test.P1E12);
        b = V3D_Test.N0_1E12.add(V3D_Test.N1E12);
        a1 = V3D_Test.P0_1E12.add(V3D_Test.P1E12).add(10);
        b1 = V3D_Test.N0_1E12.add(V3D_Test.N1E12).add(10);
        l = new V3D_Line(new V3D_Point(env, a, a, a), new V3D_Point(env, b, b, b), oom, rm);
        instance = new V3D_Line(new V3D_Point(env, a1, a, a),
                new V3D_Point(env, b1, b, b), oom, rm);
        assertTrue(instance.isParallel(l, oom, rm)); // Right answer, but for the wrong reason!
    }

    /**
     * Test of getIntersect method, of class V3D_Line.
     */
    @Test
    public void testGetIntersection_V3D_Line_int_RoundingMode() {
        System.out.println("getIntersect");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line l;
        V3D_Line instance;
        V3D_Geometry expResult;
        V3D_Geometry result;
        // Test 1
        //l = new V3D_Line(N1N1N1, P1P1P1);
        l = new V3D_Line(V3D_Test.pP1P1P1, V3D_Test.pN1N1N1, oom, rm);
        //instance = new V3D_Line(N1P1N1, P1N1P1);
        instance = new V3D_Line(V3D_Test.pP1N1P1, V3D_Test.pN1P1N1, oom, rm);
        expResult = V3D_Test.pP0P0P0;
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 2
        l = new V3D_Line(V3D_Test.pN1N1N1, V3D_Test.pP1P1P1, oom, rm);
        instance = new V3D_Line(V3D_Test.pP1P1P0, V3D_Test.pP1P1P2, oom, rm);
        expResult = V3D_Test.pP1P1P1;
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 3
        expResult = V3D_Test.pP0P0P0;
        instance = new V3D_Line(V3D_Test.pN1N1P0, V3D_Test.pP1P1P0, oom, rm);
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 4
        V3D_Environment env = new V3D_Environment(oom, rm);
        
        l = new V3D_Line(V3D_Test.pN1N1N1, V3D_Test.pP1P1P1, oom, rm);
        instance = new V3D_Line(env, new V3D_Vector(V3D_Test.P3, V3D_Test.P1, V3D_Test.P1),
                new V3D_Vector(V3D_Test.P1, V3D_Test.P3, V3D_Test.P3), oom, rm);
        expResult = V3D_Test.pP2P2P2;
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 5
        l = new V3D_Line(V3D_Test.pN1N1P0, V3D_Test.pP1P1P0, oom, rm);
        instance = new V3D_Line(env, new V3D_Vector(V3D_Test.P3, V3D_Test.P3, V3D_Test.P0),
                new V3D_Vector(V3D_Test.P3, V3D_Test.P3, V3D_Test.N1), oom, rm);
        expResult = new V3D_Point(env, V3D_Test.P3, V3D_Test.P3, V3D_Test.P0);
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 6
        l = new V3D_Line(V3D_Test.pN1N1N1, V3D_Test.pP1P1P1, oom, rm);
        instance = new V3D_Line(V3D_Test.pP1N1N1, V3D_Test.pN1P1P1, oom, rm);
        expResult = V3D_Test.pP0P0P0;
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 7
        l = new V3D_Line(V3D_Test.pP0P0P0, V3D_Test.pP1P1P1, oom, rm);
        instance = new V3D_Line(V3D_Test.pP1N1N1, V3D_Test.pN1P1P1, oom, rm);
        expResult = V3D_Test.pP0P0P0;
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 8
        l = new V3D_Line(V3D_Test.pN1N1N1, V3D_Test.pP0P0P0, oom, rm);
        instance = new V3D_Line(V3D_Test.pP1N1N1, V3D_Test.pN1P1P1, oom, rm);
        expResult = V3D_Test.pP0P0P0;
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 9
        l = new V3D_Line(V3D_Test.pN2N2N2, V3D_Test.pN1N1N1, oom, rm);
        instance = new V3D_Line(V3D_Test.pP1N1N1, V3D_Test.pP0P0P0, oom, rm);
        expResult = V3D_Test.pP0P0P0;
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 10
        l = new V3D_Line(V3D_Test.pN2N2N2, V3D_Test.pN1N1N1, oom, rm);
        instance = new V3D_Line(V3D_Test.pP0P0P0, V3D_Test.pN1P1P1, oom, rm);
        expResult = V3D_Test.pP0P0P0;
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 11
        l = new V3D_Line(V3D_Test.pN1N1N1, V3D_Test.pP1P1P1, oom, rm);
        expResult = new V3D_Line(V3D_Test.pN1N1N1, V3D_Test.pP1P1P1, oom, rm);
        instance = new V3D_Line(env, new V3D_Vector(V3D_Test.N3, V3D_Test.N3, V3D_Test.N3),
                new V3D_Vector(V3D_Test.N4, V3D_Test.N4, V3D_Test.N4), oom, rm);
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 12 to 14
        // v.dx = 0, v.dy != 0, v.dz !=0
        // Test 11
        l = new V3D_Line(V3D_Test.pN1N1N1, V3D_Test.pP1P1P1, oom, rm);
        expResult = V3D_Test.pP0P0P0;
        instance = new V3D_Line(V3D_Test.pP0P0P0, V3D_Test.pP0P1P1, oom, rm);
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 15
        l = new V3D_Line(V3D_Test.pP0N1N1, V3D_Test.pP2P1P1, oom, rm);
        expResult = V3D_Test.pP1P0P0;
        instance = new V3D_Line(V3D_Test.pP1P0P0, V3D_Test.pP1P1P1, oom, rm);
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 16
        l = new V3D_Line(env, V3D_Test.P0N1P1, new V3D_Vector(V3D_Test.P2, V3D_Test.P1, V3D_Test.P3), oom, rm);
        expResult = V3D_Test.pP1P0P2;
        instance = new V3D_Line(env, new V3D_Vector(V3D_Test.P1, V3D_Test.P0, V3D_Test.P2),
                new V3D_Vector(V3D_Test.P1, V3D_Test.P1, V3D_Test.P3), oom, rm);
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 17 to 18
        // v.dx != 0, v.dy = 0, v.dz = 0
        // Test 17
        l = new V3D_Line(V3D_Test.pN1N1N1, V3D_Test.pP1P1P1, oom, rm);
        expResult = V3D_Test.pP0P0P0;
        instance = new V3D_Line(V3D_Test.pP0P0P0, V3D_Test.pP1P0P0, oom, rm);
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 18
        l = new V3D_Line(V3D_Test.pP0N1N1, V3D_Test.pP2P1P1, oom, rm);
        expResult = V3D_Test.pP1P0P0;
        instance = new V3D_Line(V3D_Test.pP1P0P0, V3D_Test.pP2P0P0, oom, rm);
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 19
        l = new V3D_Line(V3D_Test.pP0N1P0, V3D_Test.pP2P1P2, oom, rm);
        expResult = V3D_Test.pP1P0P1;
        instance = new V3D_Line(V3D_Test.pP1P0P1, V3D_Test.pP2P0P1, oom, rm);
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 20 to 21
        // v.dx != 0, v.dy = 0, v.dz != 0
        // Test 20
        l = new V3D_Line(V3D_Test.pN1N1N1, V3D_Test.pP1P1P1, oom, rm);
        expResult = V3D_Test.pP0P0P0;
        instance = new V3D_Line(V3D_Test.pP0P0P0, V3D_Test.pP1P0P1, oom, rm);
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 21
        l = new V3D_Line(V3D_Test.pP0N1N1, V3D_Test.pP2P1P1, oom, rm);
        expResult = V3D_Test.pP1P0P0;
        instance = new V3D_Line(V3D_Test.pP1P0P0, V3D_Test.pP2P0P1, oom, rm);
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 22
        l = new V3D_Line(env, V3D_Test.P0P1N1, new V3D_Vector(V3D_Test.P2, V3D_Test.P3, V3D_Test.P1), oom, rm);
        expResult = V3D_Test.pP1P2P0;
        instance = new V3D_Line(V3D_Test.pP1P2P0, V3D_Test.pP2P2P1, oom, rm);
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 23
        oom = -1;
        rm = RoundingMode.HALF_UP;
        l = new V3D_Line(V3D_Test.pP0P0P0, V3D_Test.pP1P1P1, oom, rm);
        instance = new V3D_Line(V3D_Test.pP0P0P0, V3D_Test.pP1P1P1, oom, rm);
        expResult = new V3D_Line(V3D_Test.pP0P0P0, V3D_Test.pP1P1P1, oom, rm);
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 24
        instance = new V3D_Line(V3D_Test.pP1P1P1, V3D_Test.pP0P0P0, oom, rm);
        expResult = new V3D_Line(V3D_Test.pP0P0P0, V3D_Test.pP1P1P1, oom, rm);
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Line) expResult).equals((V3D_Line) result, oom, rm));
        // Test 25
        //instance = new V3D_Line(P0P1P0, P0N1P0, oom, rm);
        instance = new V3D_Line(V3D_Test.pP0N1P0, V3D_Test.pP0P1P0, oom, rm);
        l = new V3D_Line(V3D_Test.pP1P1P1, V3D_Test.pP0P0P0, oom, rm);
        expResult = V3D_Test.pP0P0P0;
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 26
        instance = new V3D_Line(V3D_Test.pN1P1P1, V3D_Test.pP1N1P1, oom, rm);
        l = new V3D_Line(V3D_Test.pP0P2P1, V3D_Test.pP1P1P1, oom, rm);
        //expResult = null;
        result = instance.getIntersect(l, oom, rm);
        //System.out.println(result);
        assertNull(result);
        // Test 27
        l = new V3D_Line(V3D_Test.pN1N1N1, V3D_Test.pP1P1P1, oom, rm);
        instance = new V3D_Line(V3D_Test.pN1P1P1, V3D_Test.pP1N1N1, oom, rm);
        expResult = V3D_Test.pP0P0P0;
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 28
        oom = -3;
        l = new V3D_Line(V3D_Test.pN1N1N1, V3D_Test.pP1P1P1, oom, rm);
        instance = new V3D_Line(V3D_Test.pN1P1P1, V3D_Test.pP1N1N1, oom, rm);
        expResult = V3D_Test.pP0P0P0;
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
        // Test 29
        l = new V3D_Line(new V3D_Point(env, V3D_Test.N1.add(V3D_Test.P0_1),
                V3D_Test.N1.add(V3D_Test.P0_1), V3D_Test.N1),
                new V3D_Point(env, P1.add(P0_1), P1.add(P0_1), P1), oom, rm);
        instance = new V3D_Line(new V3D_Point(env, N1.add(P0_1), P1.add(P0_1), P1),
                new V3D_Point(env, P1.add(P0_1), N1.add(P0_1), N1), oom, rm);
        expResult = new V3D_Point(env, P0.add(P0_1), P0.add(P0_1), P0);
        result = instance.getIntersect(l, oom, rm);
        assertTrue(((V3D_Point) expResult).equals((V3D_Point) result, oom, rm));
    }

    /**
     * Test of equals method, of class V3D_Line.
     */
    @Test
    public void testEquals_V3D_Line_int_RoundingMode() {
        System.out.println("equals");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line l = new V3D_Line(V3D_Test.pP0P0P0, V3D_Test.pP1P1P1, oom, rm);
        V3D_Line instance = new V3D_Line(V3D_Test.pP0P0P0, V3D_Test.pP1P1P1, oom, rm);
        assertTrue(instance.equals(l, oom, rm));
        // Test 2
        instance = new V3D_Line(V3D_Test.pP1P1P1, V3D_Test.pP0P0P0, oom, rm);
        assertTrue(instance.equals(l, oom, rm));
        // Test 3
        l = new V3D_Line(pP0P0P0, V3D_Vector.I);
        instance = l;
        assertTrue(instance.equals(l, oom, rm));
        // Test 4
        instance = new V3D_Line(pP0P0P0, V3D_Vector.J);
        assertFalse(instance.equals(l, oom, rm));
    }

    /**
     * Test of isParallelToX0 method, of class V3D_Line.
     */
    @Test
    public void testIsParallelToX0() {
        System.out.println("isParallelToX0");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line instance = new V3D_Line(pP1P0P0, pP1P1P0, oom, rm);
        assertTrue(instance.isParallelToX0(oom, rm));
        // Test 2
        instance = new V3D_Line(pP0P0P0, pP0P1P0, oom, rm);
        assertTrue(instance.isParallelToX0(oom, rm));
        // Test 3
        instance = new V3D_Line(pP0P0P1, pP1P1P0, oom, rm);
        assertFalse(instance.isParallelToX0(oom, rm));
    }

    /**
     * Test of isParallelToY0 method, of class V3D_Line.
     */
    @Test
    public void testIsParallelToY0() {
        System.out.println("isParallelToY0");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line instance = new V3D_Line(pP0P1P1, pP0P1N1, oom, rm);
        assertTrue(instance.isParallelToY0(oom, rm));
        // Test 2
        instance = new V3D_Line(pP0P0P0, pP0P0P1, oom, rm);
        assertTrue(instance.isParallelToX0(oom, rm));
        // Test 3
        instance = new V3D_Line(pP0P0P1, pP1P1P0, oom, rm);
        assertFalse(instance.isParallelToX0(oom, rm));
    }

    /**
     * Test of isParallelToZ0 method, of class V3D_Line.
     */
    @Test
    public void testIsParallelToZ0() {
        System.out.println("isParallelToZ0");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line instance = new V3D_Line(pP0P0P1, pP0P1P1, oom, rm);
        assertTrue(instance.isParallelToZ0(oom, rm));
        // Test 2
        instance = new V3D_Line(pP0P0P0, pP1P0P0, oom, rm);
        assertTrue(instance.isParallelToZ0(oom, rm));
        // Test 3
        instance = new V3D_Line(pP0P0P1, pP1P1P0, oom, rm);
        assertFalse(instance.isParallelToZ0(oom, rm));
    }

    /**
     * Test of getAsMatrix method, of class V3D_Line.
     */
    @Test
    public void testGetAsMatrix() {
        System.out.println("getAsMatrix");
        int oom = -1;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line instance = new V3D_Line(pP0P0P0, V3D_Vector.I);
        BigRational[][] m = new BigRational[2][3];
        m[0][0] = BigRational.ZERO;
        m[0][1] = BigRational.ZERO;
        m[0][2] = BigRational.ZERO;
        m[1][0] = BigRational.ONE;
        m[1][1] = BigRational.ZERO;
        m[1][2] = BigRational.ZERO;
        Math_Matrix_BR expResult = new Math_Matrix_BR(m);
        Math_Matrix_BR result = instance.getAsMatrix(oom, rm);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getDistance method, of class V3D_Line.
     */
    @Test
    public void testGetDistance_V3D_Point_int_RoundingMode() {
        System.out.println("getDistance");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point pt;
        V3D_Line instance;
        BigRational expResult;
        BigRational result;
        // Test 1
        pt = pP0P0P0;
        instance = new V3D_Line(pP1P0P0, pP1P1P0, oom, rm);
        expResult = BigRational.ONE;
        result = instance.getDistance(pt, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 2
        instance = new V3D_Line(pP0P1P0, pP1P1P0, oom, rm);
        result = instance.getDistance(pt, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 3
        pt = pP1P1P1;
        instance = new V3D_Line(pP0P0P0, pP1P1P0, oom, rm);
        result = instance.getDistance(pt, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 4
        oom = - 4;
        pt = pP0P1P0;
        instance = new V3D_Line(pP0P0P0, pP1P1P0, oom, rm);
        int ooms = Math_BigRationalSqrt.getOOM(BigRational.TWO, oom);
        if (ooms > 0) {
            ooms = 0;
        }
        expResult = BigRational.valueOf(new Math_BigRationalSqrt(
                BigRational.TWO, oom, rm).toBigDecimal(oom, rm)).divide(2);
        result = instance.getDistance(pt, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 5 https://math.stackexchange.com/a/1658288/756049
        pt = pP1P1P1;
        oom = -3;
        V3D_Environment env = new V3D_Environment(oom, rm);
        
        BigRational third = BigRational.valueOf(1, 3);
        instance = new V3D_Line(env, new V3D_Vector(N2, N4, P5), new V3D_Vector(N1, N2, P3), oom, rm);
        V3D_Point p2 = new V3D_Point(env, third, BigRational.valueOf(2, 3), third);
        expResult = p2.getDistance(pt, oom, rm);
        result = instance.getDistance(pt, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 6
        instance = new V3D_Line(pP0P0P0, pP0P0P1, oom, rm);
        pt = pP0P1P0;
        expResult = P1;
        result = instance.getDistance(pt, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 2
        instance = new V3D_Line(pP0P0P0, pP0P0P1, oom, rm);
        pt = new V3D_Point(env, P3, P4, P0);
        expResult = P5;
        result = instance.getDistance(pt, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 3
        instance = new V3D_Line(pP0P0P1, pP0P0P0, oom, rm);
        pt = new V3D_Point(env, P3, P4, P0);
        expResult = P5;
        result = instance.getDistance(pt, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 4
        instance = new V3D_Line(pP0P0P0, pP0P0P1, oom, rm);
        pt = new V3D_Point(env, P4, P3, P0);
        expResult = P5;
        result = instance.getDistance(pt, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 3
        instance = new V3D_Line(pP0P0P0, pP0P0P1, oom, rm);
        pt = new V3D_Point(env, P4, P3, P10);
        expResult = P5;
        result = instance.getDistance(pt, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getLineOfIntersect method, of class V3D_Line.
     */
    @Test
    public void testGetLineOfIntersection_V3D_Point_int_RoundingMode() {
        System.out.println("getLineOfIntersection");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point pt;
        V3D_Line instance;
        V3D_LineSegment expResult;
        V3D_Geometry result;
        // Test 1
        pt = pP0P0P0;
        instance = new V3D_Line(pP1P0P0, pP1P1P0, oom, rm);
        expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        result = instance.getLineOfIntersect(pt, oom, rm);
        assertTrue(expResult.equals((V3D_LineSegment) result, oom, rm));
        // Test 2
        instance = new V3D_Line(pP1N1P0, pP1P1P0, oom, rm);
        expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        //result = instance.getLineOfIntersect(pt, oom, rm);
        //System.out.println(result);
        result = instance.getLineOfIntersect(pt, oom, rm);
        assertTrue(expResult.equals((V3D_LineSegment) result, oom, rm));
    }

    /**
     * Test of getPointOfIntersection method, of class V3D_Line. No test: Test
     * covered by {@link #testGetLineOfIntersection_V3D_Point()}
     */
    @Test
    public void testGetPointOfIntersection() {
        System.out.println("getPointOfIntersection");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point pt = pP2P0P0;
        V3D_Line instance = new V3D_Line(pP0P0P0, pP0P2P2, oom, rm);
        V3D_Point expResult = pP0P0P0;
        V3D_Point result = instance.getPointOfIntersect(pt, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 2
        pt = pN2P0P0;
        instance = new V3D_Line(pP0P0P0, pP0P2P2, oom, rm);
        expResult = pP0P0P0;
        result = instance.getPointOfIntersect(pt, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 3
        pt = pN2P2P0;
        instance = new V3D_Line(pP0P0P0, pP0P2P2, oom, rm);
        expResult = pP0P1P1;
        result = instance.getPointOfIntersect(pt, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 4
        pt = pN2N2P0;
        instance = new V3D_Line(pP0P0P0, pP0P2P2, oom, rm);
        expResult = pP0N1N1;
        result = instance.getPointOfIntersect(pt, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
    }

    /**
     * Test of getLineOfIntersect method, of class V3D_Line.
     */
    @Test
    public void testGetLineOfIntersection_V3D_Line_int() {
        System.out.println("getLineOfIntersection");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line l0 = new V3D_Line(pP1P0P0, pP1P1P0, oom, rm);
        V3D_Line l1 = new V3D_Line(pP0P0P0, pP0P0P1, oom, rm);
        V3D_LineSegment expResult = new V3D_LineSegment(pP0P0P0, pP1P0P0, oom, rm);
        V3D_LineSegment result = l0.getLineOfIntersection(l1, oom, rm);
        assertTrue(expResult.equalsIgnoreDirection(result, oom, rm));
        // Test 2
        l1 = new V3D_Line(pP0P0P0, pP0P1P0, oom, rm);
        result = l0.getLineOfIntersection(l1, oom, rm);
        assertNull(result);
    }

    /**
     * Test of getDistance method, of class V3D_Line.
     */
    @Test
    public void testGetDistance_V3D_Line_int() {
        System.out.println("getDistance");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;        
        V3D_Environment env = new V3D_Environment(oom, rm);
        V3D_Line l;
        V3D_Line instance;
        BigRational expResult;
        BigRational result;
        // Test 1 
        // https://math.stackexchange.com/questions/2213165/find-shortest-distance-between-lines-in-3d
        //l = new V3D_Line(new V3D_Vector(P2, P6, N9), oom, rm, new V3D_Vector(P3, P4, N4));
        l = new V3D_Line(env, new V3D_Vector(P2, P6, N9), new V3D_Vector(P3, P4, N4));
        //instance = new V3D_Line(new V3D_Vector(N1, N2, P3), oom, rm, new V3D_Vector(P2, N6, P1));
        instance = new V3D_Line(env, new V3D_Vector(N1, N2, P3), new V3D_Vector(P2, N6, P1));
        expResult = BigRational.valueOf("4.7");
        result = instance.getDistance(l, -1, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 2
        l = new V3D_Line(pP0P0P0, pP1P1P0, oom, rm);
        oom = -4;
        instance = new V3D_Line(env, P1N1P0, P2P0P0, oom, rm);
        expResult = new Math_BigRationalSqrt(BigRational.TWO, oom, rm).getSqrt(oom, rm);
        result = instance.getDistance(l, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of toString method, of class V3D_Line.
     */
    @Test
    public void testToString_String() {
        System.out.println("toString");
        String pad = "";
        V3D_Line instance = V3D_Line.X_AXIS;
        String expResult = """
                           V3D_Line
                           (
                            offset=V3D_Vector
                            (
                             dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),
                             dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),
                             dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)
                            )
                            ,
                            p=V3D_Point
                            (
                             offset=V3D_Vector
                             (
                              dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),
                              dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),
                              dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)
                             )
                             ,
                             rel=V3D_Vector
                             (
                              dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),
                              dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),
                              dz=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)
                             )
                            )
                            ,
                            v=V3D_Vector
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
     * Test of toStringFields method, of class V3D_Line.
     */
    @Test
    public void testToStringSimple() {
        System.out.println("toStringSimple");
        String pad = "";
        V3D_Line instance = V3D_Line.X_AXIS;
        String expResult = """
                           V3D_Line
                           (
                            offset=V3D_Vector(dx=0, dy=0, dz=0),
                            p=V3D_Point(offset=V3D_Vector(dx=0, dy=0, dz=0), rel=V3D_Vector(dx=0, dy=0, dz=0)),
                            v= V3D_Vector(dx=1, dy=0, dz=0)
                           )""";
        String result = instance.toStringSimple(pad);
        //System.out.println(result);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getP method, of class V3D_Line.
     */
    @Test
    public void testGetP() {
        System.out.println("getP");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line instance = new V3D_Line(pP0P0P0, pP1P0P0, oom, rm);
        V3D_Point expResult = pP0P0P0;
        V3D_Point result = instance.getP();
        assertTrue(expResult.equals(result, oom, rm));
    }

    /**
     * Test of rotate method, of class V3D_Line.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        V3D_Ray axis = new V3D_Ray(pP0P0P0, V3D_Vector.I);
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        Math_BigDecimal bd = new Math_BigDecimal();
        int oomt = oom - 2;
        BigRational Pi = BigRational.valueOf(
                bd.getPi(oomt, RoundingMode.HALF_UP));
        BigRational theta = Pi.divide(2);
        V3D_Line instance = new V3D_Line(pP0P0P0, pP1P0P0, oom, rm);
        V3D_Line expResult = new V3D_Line(pP0P0P0, pP1P0P0, oom, rm);
        V3D_Line result = instance.rotate(axis, axis.l.v, bd, theta, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 2
        axis = new V3D_Ray(pP0P0P0, V3D_Vector.J);
        theta = Pi.divide(2);
        instance = new V3D_Line(pP0P0P0, pP1P0P0, oom, rm);
        //expResult = new V3D_Line(pP0P0P0, pP0P0P1, oom, rm);
        expResult = new V3D_Line(pP0P0P0, pP0P0N1, oom, rm);
        result = instance.rotate(axis, axis.l.v, bd, theta, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 3
        V3D_Environment env = new V3D_Environment(oom, rm);
        theta = Pi.divide(2);
        instance = new V3D_Line(env, new V3D_Vector(0, 0, 0), new V3D_Vector(5, 0, 0), oom, rm);
        //expResult = new V3D_Line(new V3D_Vector(0, 0, 0), new V3D_Vector(0, 0, 5), oom, rm);
        expResult = new V3D_Line(env, new V3D_Vector(0, 0, 0), new V3D_Vector(0, 0, -5), oom, rm);
        result = instance.rotate(axis, axis.l.v, bd, theta, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 4
        theta = Pi;
        instance = new V3D_Line(env, new V3D_Vector(3, 2, 0), new V3D_Vector(5, 0, 0), oom, rm);
        expResult = new V3D_Line(env, new V3D_Vector(-3, 2, 0), new V3D_Vector(-5, 0, 0), oom, rm);
        result = instance.rotate(axis, axis.l.v, bd, theta, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
    }

    /**
     * Test of isCollinear method, of class V3D_Line.
     */
    @Test
    public void testIsCollinear_int_RoundingMode_V3D_Line_V3D_PointArr() {
        System.out.println("isCollinear");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Line l;
        V3D_Point[] points = new V3D_Point[2];
        // Test 1
        l = new V3D_Line(pN1N1N1, pP1P1P1, oom, rm);
        points[0] = pP2P2P2;
        points[1] = pN2N2N2;
        assertTrue(V3D_Line.isCollinear(oom, rm, l, points));
        // Test 2
        points[1] = pN2N2N1;
        assertFalse(V3D_Line.isCollinear(oom, rm, l, points));
        // Test 3
        points[0] = pN1N2N1;
        assertFalse(V3D_Line.isCollinear(oom, rm, l, points));
    }

    /**
     * Test of isCollinear method, of class V3D_Line.
     */
    @Test
    public void testIsCollinear_int_RoundingMode_V3D_PointArr() {
        System.out.println("isCollinear");
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Point[] points = new V3D_Point[3];
        points[0] = pP2P2P2;
        points[1] = pP2P2P1;
        points[2] = pP2P2P0;
        assertTrue(V3D_Line.isCollinear(oom, rm, points));
        points[2] = pP2P2N1;
        assertTrue(V3D_Line.isCollinear(oom, rm, points));
        points[2] = pP2P2N2;
        assertTrue(V3D_Line.isCollinear(oom, rm, points));
        // P2P1*
        points[0] = pP2P1P2;
        points[1] = pP2P1P1;
        points[2] = pP2P1P0;
        assertTrue(V3D_Line.isCollinear(oom, rm, points));
        points[2] = pP2P1N1;
        assertTrue(V3D_Line.isCollinear(oom, rm, points));
        points[2] = pP2P1N2;
        assertTrue(V3D_Line.isCollinear(oom, rm, points));
        // P2P0*
        points[0] = pP2P0P2;
        points[1] = pP2P0P1;
        points[2] = pP2P0P0;
        assertTrue(V3D_Line.isCollinear(oom, rm, points));
        points[2] = pP2P0N1;
        assertTrue(V3D_Line.isCollinear(oom, rm, points));
        points[2] = pP2P0N2;
        assertTrue(V3D_Line.isCollinear(oom, rm, points));
        // P2N1*
        points[0] = pP2N1P2;
        points[1] = pP2N1P1;
        points[2] = pP2N1P0;
        assertTrue(V3D_Line.isCollinear(oom, rm, points));
        points[2] = pP2N1N1;
        assertTrue(V3D_Line.isCollinear(oom, rm, points));
        points[2] = pP2N1N2;
        assertTrue(V3D_Line.isCollinear(oom, rm, points));
        // P2N2*
        points[0] = pP2N2P2;
        points[1] = pP2N2P1;
        points[2] = pP2N2P0;
        assertTrue(V3D_Line.isCollinear(oom, rm, points));
        points[2] = pP2N2N1;
        assertTrue(V3D_Line.isCollinear(oom, rm, points));
        points[2] = pP2N2N2;
        // Others
        points = new V3D_Point[3];
        points[0] = pP2P2P2;
        points[1] = pN2N2N2;
        points[2] = pN1N1N1;
        assertTrue(V3D_Line.isCollinear(oom, rm, points));
        points[1] = pP1P1P0;
        assertFalse(V3D_Line.isCollinear(oom, rm, points));
        points = new V3D_Point[3];
        points[0] = pP2P2P2;
        points[1] = pN2N2N2;
        points[2] = pP1P1P1;
        assertTrue(V3D_Line.isCollinear(oom, rm, points));
    }

}
