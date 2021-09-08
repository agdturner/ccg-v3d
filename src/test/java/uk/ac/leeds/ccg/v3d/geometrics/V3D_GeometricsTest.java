/*
 * Copyright 2020 Centre for Computational Geography.
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
package uk.ac.leeds.ccg.v3d.geometrics;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Point;
import uk.ac.leeds.ccg.v3d.V3D_Test;

/**
 * @author Andy Turner
 * @version 1.0.0
 */
public class V3D_GeometricsTest extends V3D_Test {
    
    public V3D_GeometricsTest(){}
    
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
     * Test of isCollinear method, of class V3D_Geometrics.
     */
    @Test
    public void testIsCollinear() {
        System.out.println("isCollinear");
        int oom = -1;
        V3D_Point[] points = new V3D_Point[3];
        points[0] = P0P0P0;
        points[1] = P1P0P0;
        points[2] = N1P0P0;
        assertTrue(V3D_Geometrics.isCollinear(oom, points));
        // Test 2
        points[2] = P0P0P0;
        assertTrue(V3D_Geometrics.isCollinear(oom, points));
        // Test 3
        points[2] = P1P1P0;
        assertFalse(V3D_Geometrics.isCollinear(oom, points));
        // Test 4
        points[2] = P1P0P1;
        assertFalse(V3D_Geometrics.isCollinear(oom, points));
        // Test 5
        points[1] = N1N1N1;
        points[2] = P1P1P1;
        assertTrue(V3D_Geometrics.isCollinear(oom, points));
        // Test 6
        points[2] = P1P1P0;
        assertFalse(V3D_Geometrics.isCollinear(oom, points));
    }
    
}
