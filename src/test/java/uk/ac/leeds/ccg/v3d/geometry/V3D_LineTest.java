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

import java.math.BigDecimal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.generic.io.Generic_Defaults;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;

/**
 * V3D_LineTest
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_LineTest extends V3D_Test {

    public V3D_LineTest() throws Exception {
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
     * Test of toString method, of class V3D_Line.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        V3D_Line instance = getLine(P0P0P0, P1P0P0);
        String expResult = "V3D_Line(p=V3D_Point(x=0, y=0, z=0), "
                + "q=V3D_Point(x=1, y=0, z=0))";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Basic method to get a line defined by {@code p} and {@code q}.
     *
     * @param p A point.
     * @param q A point.
     * @return A line or null if the points {@code p} and {@code q} are
     * coincident.
     */
    public V3D_Line getLine(V3D_Point p, V3D_Point q) {
        try {
            return new V3D_Line(p, q);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
        return null;
    }

    /**
     * Test of equals method, of class V3D_Line.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object o = getLine(P0P0P0, P1P0P0);
        V3D_Line instance = getLine(P0P0P0, P1P0P0);
        boolean expResult = true;
        boolean result = instance.equals(o);
        assertEquals(expResult, result);
        // Test 2
        instance = getLine(P0P0P0, N1P0P0);
        expResult = true;
        result = instance.equals(o);
        assertEquals(expResult, result);
        // Test 2
        instance = getLine(P0P0P0, N1P1P0);
        expResult = false;
        result = instance.equals(o);
        assertEquals(expResult, result);
    }

    /**
     * Test of getIntersects method, of class V3D_Line.
     */
    @Test
    public void testIsOnLine() {
        System.out.println("isOnLine");
        V3D_Line instance = getLine(P0P0P0, P1P0P0);
        boolean expResult = true;
        boolean result = instance.getIntersects(N1P0P0);
        assertEquals(expResult, result);
        // Test 2
        instance = getLine(P0P0P0, P1P0P0);
        expResult = false;
        result = instance.getIntersects(N1P1P0);
        assertEquals(expResult, result);
    }

}
