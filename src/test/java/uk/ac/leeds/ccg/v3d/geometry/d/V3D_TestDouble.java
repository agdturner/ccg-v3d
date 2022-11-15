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

/**
 * V3D_Test
 *
 * @author Andy Turner
 * @version 1.0
 */
public abstract class V3D_TestDouble {
    
    // P2xx
    public static final V3D_VectorDouble P2P2P2 = new V3D_VectorDouble(2.0d, 2.0d, 2.0d);
    public static final V3D_VectorDouble P2P2P1 = new V3D_VectorDouble(2.0d, 2.0d, 1.0d);
    public static final V3D_VectorDouble P2P2P0 = new V3D_VectorDouble(2.0d, 2.0d, 0.0d);
    public static final V3D_VectorDouble P2P2N1 = new V3D_VectorDouble(2.0d, 2.0d, -1.0d);
    public static final V3D_VectorDouble P2P2N2 = new V3D_VectorDouble(2.0d, 2.0d, -2.0d);
    public static final V3D_VectorDouble P2P1P2 = new V3D_VectorDouble(2.0d, 1.0d, 2.0d);
    public static final V3D_VectorDouble P2P1P1 = new V3D_VectorDouble(2.0d, 1.0d, 1.0d);
    public static final V3D_VectorDouble P2P1P0 = new V3D_VectorDouble(2.0d, 1.0d, 0.0d);
    public static final V3D_VectorDouble P2P1N1 = new V3D_VectorDouble(2.0d, 1.0d, -1.0d);
    public static final V3D_VectorDouble P2P1N2 = new V3D_VectorDouble(2.0d, 1.0d, -2.0d);
    public static final V3D_VectorDouble P2P0P2 = new V3D_VectorDouble(2.0d, 0.0d, 2.0d);
    public static final V3D_VectorDouble P2P0P1 = new V3D_VectorDouble(2.0d, 0.0d, 1.0d);
    public static final V3D_VectorDouble P2P0P0 = new V3D_VectorDouble(2.0d, 0.0d, 0.0d);
    public static final V3D_VectorDouble P2P0N1 = new V3D_VectorDouble(2.0d, 0.0d, -1.0d);
    public static final V3D_VectorDouble P2P0N2 = new V3D_VectorDouble(2.0d, 0.0d, -2.0d);
    public static final V3D_VectorDouble P2N1P2 = new V3D_VectorDouble(2.0d, -1.0d, 2.0d);
    public static final V3D_VectorDouble P2N1P1 = new V3D_VectorDouble(2.0d, -1.0d, 1.0d);
    public static final V3D_VectorDouble P2N1P0 = new V3D_VectorDouble(2.0d, -1.0d, 0.0d);
    public static final V3D_VectorDouble P2N1N1 = new V3D_VectorDouble(2.0d, -1.0d, -1.0d);
    public static final V3D_VectorDouble P2N1N2 = new V3D_VectorDouble(2.0d, -1.0d, -2.0d);
    public static final V3D_VectorDouble P2N2P2 = new V3D_VectorDouble(2.0d, -2.0d, 2.0d);
    public static final V3D_VectorDouble P2N2P1 = new V3D_VectorDouble(2.0d, -2.0d, 1.0d);
    public static final V3D_VectorDouble P2N2P0 = new V3D_VectorDouble(2.0d, -2.0d, 0.0d);
    public static final V3D_VectorDouble P2N2N1 = new V3D_VectorDouble(2.0d, -2.0d, -1.0d);
    public static final V3D_VectorDouble P2N2N2 = new V3D_VectorDouble(2.0d, -2.0d, -2.0d);
    // P1xx
    public static final V3D_VectorDouble P1P2P2 = new V3D_VectorDouble(1.0d, 2.0d, 2.0d);
    public static final V3D_VectorDouble P1P2P1 = new V3D_VectorDouble(1.0d, 2.0d, 1.0d);
    public static final V3D_VectorDouble P1P2P0 = new V3D_VectorDouble(1.0d, 2.0d, 0.0d);
    public static final V3D_VectorDouble P1P2N1 = new V3D_VectorDouble(1.0d, 2.0d, -1.0d);
    public static final V3D_VectorDouble P1P2N2 = new V3D_VectorDouble(1.0d, 2.0d, -2.0d);
    public static final V3D_VectorDouble P1P1P2 = new V3D_VectorDouble(1.0d, 1.0d, 2.0d);
    public static final V3D_VectorDouble P1P1P1 = new V3D_VectorDouble(1.0d, 1.0d, 1.0d);
    public static final V3D_VectorDouble P1P1P0 = new V3D_VectorDouble(1.0d, 1.0d, 0.0d);
    public static final V3D_VectorDouble P1P1N1 = new V3D_VectorDouble(1.0d, 1.0d, -1.0d);
    public static final V3D_VectorDouble P1P1N2 = new V3D_VectorDouble(1.0d, 1.0d, -2.0d);
    public static final V3D_VectorDouble P1P0P2 = new V3D_VectorDouble(1.0d, 0.0d, 2.0d);
    public static final V3D_VectorDouble P1P0P1 = new V3D_VectorDouble(1.0d, 0.0d, 1.0d);
    public static final V3D_VectorDouble P1P0P0 = new V3D_VectorDouble(1.0d, 0.0d, 0.0d);
    public static final V3D_VectorDouble P1P0N1 = new V3D_VectorDouble(1.0d, 0.0d, -1.0d);
    public static final V3D_VectorDouble P1P0N2 = new V3D_VectorDouble(1.0d, 0.0d, -2.0d);
    public static final V3D_VectorDouble P1N1P2 = new V3D_VectorDouble(1.0d, -1.0d, 2.0d);
    public static final V3D_VectorDouble P1N1P1 = new V3D_VectorDouble(1.0d, -1.0d, 1.0d);
    public static final V3D_VectorDouble P1N1P0 = new V3D_VectorDouble(1.0d, -1.0d, 0.0d);
    public static final V3D_VectorDouble P1N1N1 = new V3D_VectorDouble(1.0d, -1.0d, -1.0d);
    public static final V3D_VectorDouble P1N1N2 = new V3D_VectorDouble(1.0d, -1.0d, -2.0d);
    public static final V3D_VectorDouble P1N2P2 = new V3D_VectorDouble(1.0d, -2.0d, 2.0d);
    public static final V3D_VectorDouble P1N2P1 = new V3D_VectorDouble(1.0d, -2.0d, 1.0d);
    public static final V3D_VectorDouble P1N2P0 = new V3D_VectorDouble(1.0d, -2.0d, 0.0d);
    public static final V3D_VectorDouble P1N2N1 = new V3D_VectorDouble(1.0d, -2.0d, -1.0d);
    public static final V3D_VectorDouble P1N2N2 = new V3D_VectorDouble(1.0d, -2.0d, -2.0d);
    // P0xx
    public static final V3D_VectorDouble P0P2P2 = new V3D_VectorDouble(0.0d, 2.0d, 2.0d);
    public static final V3D_VectorDouble P0P2P1 = new V3D_VectorDouble(0.0d, 2.0d, 1.0d);
    public static final V3D_VectorDouble P0P2P0 = new V3D_VectorDouble(0.0d, 2.0d, 0.0d);
    public static final V3D_VectorDouble P0P2N1 = new V3D_VectorDouble(0.0d, 2.0d, -1.0d);
    public static final V3D_VectorDouble P0P2N2 = new V3D_VectorDouble(0.0d, 2.0d, -2.0d);
    public static final V3D_VectorDouble P0P1P2 = new V3D_VectorDouble(0.0d, 1.0d, 2.0d);
    public static final V3D_VectorDouble P0P1P1 = new V3D_VectorDouble(0.0d, 1.0d, 1.0d);
    public static final V3D_VectorDouble P0P1P0 = new V3D_VectorDouble(0.0d, 1.0d, 0.0d);
    public static final V3D_VectorDouble P0P1N1 = new V3D_VectorDouble(0.0d, 1.0d, -1.0d);
    public static final V3D_VectorDouble P0P1N2 = new V3D_VectorDouble(0.0d, 1.0d, -2.0d);
    public static final V3D_VectorDouble P0P0P2 = new V3D_VectorDouble(0.0d, 0.0d, 2.0d);
    public static final V3D_VectorDouble P0P0P1 = new V3D_VectorDouble(0.0d, 0.0d, 1.0d);
    // ORIGIN
    public static final V3D_VectorDouble P0P0P0 = new V3D_VectorDouble(0.0d, 0.0d, 0.0d);
    public static final V3D_VectorDouble P0P0N1 = new V3D_VectorDouble(0.0d, 0.0d, -1.0d);
    public static final V3D_VectorDouble P0P0N2 = new V3D_VectorDouble(0.0d, 0.0d, -2.0d);
    public static final V3D_VectorDouble P0N1P2 = new V3D_VectorDouble(0.0d, -1.0d, 2.0d);
    public static final V3D_VectorDouble P0N1P1 = new V3D_VectorDouble(0.0d, -1.0d, 1.0d);
    public static final V3D_VectorDouble P0N1P0 = new V3D_VectorDouble(0.0d, -1.0d, 0.0d);
    public static final V3D_VectorDouble P0N1N1 = new V3D_VectorDouble(0.0d, -1.0d, -1.0d);
    public static final V3D_VectorDouble P0N1N2 = new V3D_VectorDouble(0.0d, -1.0d, -2.0d);
    public static final V3D_VectorDouble P0N2P2 = new V3D_VectorDouble(0.0d, -2.0d, 2.0d);
    public static final V3D_VectorDouble P0N2P1 = new V3D_VectorDouble(0.0d, -2.0d, 1.0d);
    public static final V3D_VectorDouble P0N2P0 = new V3D_VectorDouble(0.0d, -2.0d, 0.0d);
    public static final V3D_VectorDouble P0N2N1 = new V3D_VectorDouble(0.0d, -2.0d, -1.0d);
    public static final V3D_VectorDouble P0N2N2 = new V3D_VectorDouble(0.0d, -2.0d, -2.0d);
    // N1xx
    public static final V3D_VectorDouble N1P2P2 = new V3D_VectorDouble(-1.0d, 2.0d, 2.0d);
    public static final V3D_VectorDouble N1P2P1 = new V3D_VectorDouble(-1.0d, 2.0d, 1.0d);
    public static final V3D_VectorDouble N1P2P0 = new V3D_VectorDouble(-1.0d, 2.0d, 0.0d);
    public static final V3D_VectorDouble N1P2N1 = new V3D_VectorDouble(-1.0d, 2.0d, -1.0d);
    public static final V3D_VectorDouble N1P2N2 = new V3D_VectorDouble(-1.0d, 2.0d, -2.0d);
    public static final V3D_VectorDouble N1P1P2 = new V3D_VectorDouble(-1.0d, 1.0d, 2.0d);
    public static final V3D_VectorDouble N1P1P1 = new V3D_VectorDouble(-1.0d, 1.0d, 1.0d);
    public static final V3D_VectorDouble N1P1P0 = new V3D_VectorDouble(-1.0d, 1.0d, 0.0d);
    public static final V3D_VectorDouble N1P1N1 = new V3D_VectorDouble(-1.0d, 1.0d, -1.0d);
    public static final V3D_VectorDouble N1P1N2 = new V3D_VectorDouble(-1.0d, 1.0d, -2.0d);
    public static final V3D_VectorDouble N1P0P2 = new V3D_VectorDouble(-1.0d, 0.0d, 2.0d);
    public static final V3D_VectorDouble N1P0P1 = new V3D_VectorDouble(-1.0d, 0.0d, 1.0d);
    public static final V3D_VectorDouble N1P0P0 = new V3D_VectorDouble(-1.0d, 0.0d, 0.0d);
    public static final V3D_VectorDouble N1P0N1 = new V3D_VectorDouble(-1.0d, 0.0d, -1.0d);
    public static final V3D_VectorDouble N1P0N2 = new V3D_VectorDouble(-1.0d, 0.0d, -2.0d);
    public static final V3D_VectorDouble N1N1P2 = new V3D_VectorDouble(-1.0d, -1.0d, 2.0d);
    public static final V3D_VectorDouble N1N1P1 = new V3D_VectorDouble(-1.0d, -1.0d, 1.0d);
    public static final V3D_VectorDouble N1N1P0 = new V3D_VectorDouble(-1.0d, -1.0d, 0.0d);
    public static final V3D_VectorDouble N1N1N1 = new V3D_VectorDouble(-1.0d, -1.0d, -1.0d);
    public static final V3D_VectorDouble N1N1N2 = new V3D_VectorDouble(-1.0d, -1.0d, -2.0d);
    public static final V3D_VectorDouble N1N2P2 = new V3D_VectorDouble(-1.0d, -2.0d, 2.0d);
    public static final V3D_VectorDouble N1N2P1 = new V3D_VectorDouble(-1.0d, -2.0d, 1.0d);
    public static final V3D_VectorDouble N1N2P0 = new V3D_VectorDouble(-1.0d, -2.0d, 0.0d);
    public static final V3D_VectorDouble N1N2N1 = new V3D_VectorDouble(-1.0d, -2.0d, -1.0d);
    public static final V3D_VectorDouble N1N2N2 = new V3D_VectorDouble(-1.0d, -2.0d, -2.0d);
   // N2xx
    public static final V3D_VectorDouble N2P2P2 = new V3D_VectorDouble(-2.0d, 2.0d, 2.0d);
    public static final V3D_VectorDouble N2P2P1 = new V3D_VectorDouble(-2.0d, 2.0d, 1.0d);
    public static final V3D_VectorDouble N2P2P0 = new V3D_VectorDouble(-2.0d, 2.0d, 0.0d);
    public static final V3D_VectorDouble N2P2N1 = new V3D_VectorDouble(-2.0d, 2.0d, -1.0d);
    public static final V3D_VectorDouble N2P2N2 = new V3D_VectorDouble(-2.0d, 2.0d, -2.0d);
    public static final V3D_VectorDouble N2P1P2 = new V3D_VectorDouble(-2.0d, 1.0d, 2.0d);
    public static final V3D_VectorDouble N2P1P1 = new V3D_VectorDouble(-2.0d, 1.0d, 1.0d);
    public static final V3D_VectorDouble N2P1P0 = new V3D_VectorDouble(-2.0d, 1.0d, 0.0d);
    public static final V3D_VectorDouble N2P1N1 = new V3D_VectorDouble(-2.0d, 1.0d, -1.0d);
    public static final V3D_VectorDouble N2P1N2 = new V3D_VectorDouble(-2.0d, 1.0d, -2.0d);
    public static final V3D_VectorDouble N2P0P2 = new V3D_VectorDouble(-2.0d, 0.0d, 2.0d);
    public static final V3D_VectorDouble N2P0P1 = new V3D_VectorDouble(-2.0d, 0.0d, 1.0d);
    public static final V3D_VectorDouble N2P0P0 = new V3D_VectorDouble(-2.0d, 0.0d, 0.0d);
    public static final V3D_VectorDouble N2P0N1 = new V3D_VectorDouble(-2.0d, 0.0d, -1.0d);
    public static final V3D_VectorDouble N2P0N2 = new V3D_VectorDouble(-2.0d, 0.0d, -2.0d);
    public static final V3D_VectorDouble N2N1P2 = new V3D_VectorDouble(-2.0d, -1.0d, 2.0d);
    public static final V3D_VectorDouble N2N1P1 = new V3D_VectorDouble(-2.0d, -1.0d, 1.0d);
    public static final V3D_VectorDouble N2N1P0 = new V3D_VectorDouble(-2.0d, -1.0d, 0.0d);
    public static final V3D_VectorDouble N2N1N1 = new V3D_VectorDouble(-2.0d, -1.0d, -1.0d);
    public static final V3D_VectorDouble N2N1N2 = new V3D_VectorDouble(-2.0d, -1.0d, -2.0d);
    public static final V3D_VectorDouble N2N2P2 = new V3D_VectorDouble(-2.0d, -2.0d, 2.0d);
    public static final V3D_VectorDouble N2N2P1 = new V3D_VectorDouble(-2.0d, -2.0d, 1.0d);
    public static final V3D_VectorDouble N2N2P0 = new V3D_VectorDouble(-2.0d, -2.0d, 0.0d);
    public static final V3D_VectorDouble N2N2N1 = new V3D_VectorDouble(-2.0d, -2.0d, -1.0d);
    public static final V3D_VectorDouble N2N2N2 = new V3D_VectorDouble(-2.0d, -2.0d, -2.0d);

    // P2xx
    public static final V3D_PointDouble pP2P2P2 = new V3D_PointDouble(2.0d, 2.0d, 2.0d);
    public static final V3D_PointDouble pP2P2P1 = new V3D_PointDouble(2.0d, 2.0d, 1.0d);
    public static final V3D_PointDouble pP2P2P0 = new V3D_PointDouble(2.0d, 2.0d, 0.0d);
    public static final V3D_PointDouble pP2P2N1 = new V3D_PointDouble(2.0d, 2.0d, -1.0d);
    public static final V3D_PointDouble pP2P2N2 = new V3D_PointDouble(2.0d, 2.0d, -2.0d);
    public static final V3D_PointDouble pP2P1P2 = new V3D_PointDouble(2.0d, 1.0d, 2.0d);
    public static final V3D_PointDouble pP2P1P1 = new V3D_PointDouble(2.0d, 1.0d, 1.0d);
    public static final V3D_PointDouble pP2P1P0 = new V3D_PointDouble(2.0d, 1.0d, 0.0d);
    public static final V3D_PointDouble pP2P1N1 = new V3D_PointDouble(2.0d, 1.0d, -1.0d);
    public static final V3D_PointDouble pP2P1N2 = new V3D_PointDouble(2.0d, 1.0d, -2.0d);
    public static final V3D_PointDouble pP2P0P2 = new V3D_PointDouble(2.0d, 0.0d, 2.0d);
    public static final V3D_PointDouble pP2P0P1 = new V3D_PointDouble(2.0d, 0.0d, 1.0d);
    public static final V3D_PointDouble pP2P0P0 = new V3D_PointDouble(2.0d, 0.0d, 0.0d);
    public static final V3D_PointDouble pP2P0N1 = new V3D_PointDouble(2.0d, 0.0d, -1.0d);
    public static final V3D_PointDouble pP2P0N2 = new V3D_PointDouble(2.0d, 0.0d, -2.0d);
    public static final V3D_PointDouble pP2N1P2 = new V3D_PointDouble(2.0d, -1.0d, 2.0d);
    public static final V3D_PointDouble pP2N1P1 = new V3D_PointDouble(2.0d, -1.0d, 1.0d);
    public static final V3D_PointDouble pP2N1P0 = new V3D_PointDouble(2.0d, -1.0d, 0.0d);
    public static final V3D_PointDouble pP2N1N1 = new V3D_PointDouble(2.0d, -1.0d, -1.0d);
    public static final V3D_PointDouble pP2N1N2 = new V3D_PointDouble(2.0d, -1.0d, -2.0d);
    public static final V3D_PointDouble pP2N2P2 = new V3D_PointDouble(2.0d, -2.0d, 2.0d);
    public static final V3D_PointDouble pP2N2P1 = new V3D_PointDouble(2.0d, -2.0d, 1.0d);
    public static final V3D_PointDouble pP2N2P0 = new V3D_PointDouble(2.0d, -2.0d, 0.0d);
    public static final V3D_PointDouble pP2N2N1 = new V3D_PointDouble(2.0d, -2.0d, -1.0d);
    public static final V3D_PointDouble pP2N2N2 = new V3D_PointDouble(2.0d, -2.0d, -2.0d);
    // P1xx
    public static final V3D_PointDouble pP1P2P2 = new V3D_PointDouble(1.0d, 2.0d, 2.0d);
    public static final V3D_PointDouble pP1P2P1 = new V3D_PointDouble(1.0d, 2.0d, 1.0d);
    public static final V3D_PointDouble pP1P2P0 = new V3D_PointDouble(1.0d, 2.0d, 0.0d);
    public static final V3D_PointDouble pP1P2N1 = new V3D_PointDouble(1.0d, 2.0d, -1.0d);
    public static final V3D_PointDouble pP1P2N2 = new V3D_PointDouble(1.0d, 2.0d, -2.0d);
    public static final V3D_PointDouble pP1P1P2 = new V3D_PointDouble(1.0d, 1.0d, 2.0d);
    public static final V3D_PointDouble pP1P1P1 = new V3D_PointDouble(1.0d, 1.0d, 1.0d);
    public static final V3D_PointDouble pP1P1P0 = new V3D_PointDouble(1.0d, 1.0d, 0.0d);
    public static final V3D_PointDouble pP1P1N1 = new V3D_PointDouble(1.0d, 1.0d, -1.0d);
    public static final V3D_PointDouble pP1P1N2 = new V3D_PointDouble(1.0d, 1.0d, -2.0d);
    public static final V3D_PointDouble pP1P0P2 = new V3D_PointDouble(1.0d, 0.0d, 2.0d);
    public static final V3D_PointDouble pP1P0P1 = new V3D_PointDouble(1.0d, 0.0d, 1.0d);
    public static final V3D_PointDouble pP1P0P0 = new V3D_PointDouble(1.0d, 0.0d, 0.0d);
    public static final V3D_PointDouble pP1P0N1 = new V3D_PointDouble(1.0d, 0.0d, -1.0d);
    public static final V3D_PointDouble pP1P0N2 = new V3D_PointDouble(1.0d, 0.0d, -2.0d);
    public static final V3D_PointDouble pP1N1P2 = new V3D_PointDouble(1.0d, -1.0d, 2.0d);
    public static final V3D_PointDouble pP1N1P1 = new V3D_PointDouble(1.0d, -1.0d, 1.0d);
    public static final V3D_PointDouble pP1N1P0 = new V3D_PointDouble(1.0d, -1.0d, 0.0d);
    public static final V3D_PointDouble pP1N1N1 = new V3D_PointDouble(1.0d, -1.0d, -1.0d);
    public static final V3D_PointDouble pP1N1N2 = new V3D_PointDouble(1.0d, -1.0d, -2.0d);
    public static final V3D_PointDouble pP1N2P2 = new V3D_PointDouble(1.0d, -2.0d, 2.0d);
    public static final V3D_PointDouble pP1N2P1 = new V3D_PointDouble(1.0d, -2.0d, 1.0d);
    public static final V3D_PointDouble pP1N2P0 = new V3D_PointDouble(1.0d, -2.0d, 0.0d);
    public static final V3D_PointDouble pP1N2N1 = new V3D_PointDouble(1.0d, -2.0d, -1.0d);
    public static final V3D_PointDouble pP1N2N2 = new V3D_PointDouble(1.0d, -2.0d, -2.0d);
    // P0xx
    public static final V3D_PointDouble pP0P2P2 = new V3D_PointDouble(0.0d, 2.0d, 2.0d);
    public static final V3D_PointDouble pP0P2P1 = new V3D_PointDouble(0.0d, 2.0d, 1.0d);
    public static final V3D_PointDouble pP0P2P0 = new V3D_PointDouble(0.0d, 2.0d, 0.0d);
    public static final V3D_PointDouble pP0P2N1 = new V3D_PointDouble(0.0d, 2.0d, -1.0d);
    public static final V3D_PointDouble pP0P2N2 = new V3D_PointDouble(0.0d, 2.0d, -2.0d);
    public static final V3D_PointDouble pP0P1P2 = new V3D_PointDouble(0.0d, 1.0d, 2.0d);
    public static final V3D_PointDouble pP0P1P1 = new V3D_PointDouble(0.0d, 1.0d, 1.0d);
    public static final V3D_PointDouble pP0P1P0 = new V3D_PointDouble(0.0d, 1.0d, 0.0d);
    public static final V3D_PointDouble pP0P1N1 = new V3D_PointDouble(0.0d, 1.0d, -1.0d);
    public static final V3D_PointDouble pP0P1N2 = new V3D_PointDouble(0.0d, 1.0d, -2.0d);
    public static final V3D_PointDouble pP0P0P2 = new V3D_PointDouble(0.0d, 0.0d, 2.0d);
    public static final V3D_PointDouble pP0P0P1 = new V3D_PointDouble(0.0d, 0.0d, 1.0d);
    // ORIGIN
    public static final V3D_PointDouble pP0P0P0 = new V3D_PointDouble(0.0d, 0.0d, 0.0d);
    public static final V3D_PointDouble pP0P0N1 = new V3D_PointDouble(0.0d, 0.0d, -1.0d);
    public static final V3D_PointDouble pP0P0N2 = new V3D_PointDouble(0.0d, 0.0d, -2.0d);
    public static final V3D_PointDouble pP0N1P2 = new V3D_PointDouble(0.0d, -1.0d, 2.0d);
    public static final V3D_PointDouble pP0N1P1 = new V3D_PointDouble(0.0d, -1.0d, 1.0d);
    public static final V3D_PointDouble pP0N1P0 = new V3D_PointDouble(0.0d, -1.0d, 0.0d);
    public static final V3D_PointDouble pP0N1N1 = new V3D_PointDouble(0.0d, -1.0d, -1.0d);
    public static final V3D_PointDouble pP0N1N2 = new V3D_PointDouble(0.0d, -1.0d, -2.0d);
    public static final V3D_PointDouble pP0N2P2 = new V3D_PointDouble(0.0d, -2.0d, 2.0d);
    public static final V3D_PointDouble pP0N2P1 = new V3D_PointDouble(0.0d, -2.0d, 1.0d);
    public static final V3D_PointDouble pP0N2P0 = new V3D_PointDouble(0.0d, -2.0d, 0.0d);
    public static final V3D_PointDouble pP0N2N1 = new V3D_PointDouble(0.0d, -2.0d, -1.0d);
    public static final V3D_PointDouble pP0N2N2 = new V3D_PointDouble(0.0d, -2.0d, -2.0d);
    // N1xx
    public static final V3D_PointDouble pN1P2P2 = new V3D_PointDouble(-1.0d, 2.0d, 2.0d);
    public static final V3D_PointDouble pN1P2P1 = new V3D_PointDouble(-1.0d, 2.0d, 1.0d);
    public static final V3D_PointDouble pN1P2P0 = new V3D_PointDouble(-1.0d, 2.0d, 0.0d);
    public static final V3D_PointDouble pN1P2N1 = new V3D_PointDouble(-1.0d, 2.0d, -1.0d);
    public static final V3D_PointDouble pN1P2N2 = new V3D_PointDouble(-1.0d, 2.0d, -2.0d);
    public static final V3D_PointDouble pN1P1P2 = new V3D_PointDouble(-1.0d, 1.0d, 2.0d);
    public static final V3D_PointDouble pN1P1P1 = new V3D_PointDouble(-1.0d, 1.0d, 1.0d);
    public static final V3D_PointDouble pN1P1P0 = new V3D_PointDouble(-1.0d, 1.0d, 0.0d);
    public static final V3D_PointDouble pN1P1N1 = new V3D_PointDouble(-1.0d, 1.0d, -1.0d);
    public static final V3D_PointDouble pN1P1N2 = new V3D_PointDouble(-1.0d, 1.0d, -2.0d);
    public static final V3D_PointDouble pN1P0P2 = new V3D_PointDouble(-1.0d, 0.0d, 2.0d);
    public static final V3D_PointDouble pN1P0P1 = new V3D_PointDouble(-1.0d, 0.0d, 1.0d);
    public static final V3D_PointDouble pN1P0P0 = new V3D_PointDouble(-1.0d, 0.0d, 0.0d);
    public static final V3D_PointDouble pN1P0N1 = new V3D_PointDouble(-1.0d, 0.0d, -1.0d);
    public static final V3D_PointDouble pN1P0N2 = new V3D_PointDouble(-1.0d, 0.0d, -2.0d);
    public static final V3D_PointDouble pN1N1P2 = new V3D_PointDouble(-1.0d, -1.0d, 2.0d);
    public static final V3D_PointDouble pN1N1P1 = new V3D_PointDouble(-1.0d, -1.0d, 1.0d);
    public static final V3D_PointDouble pN1N1P0 = new V3D_PointDouble(-1.0d, -1.0d, 0.0d);
    public static final V3D_PointDouble pN1N1N1 = new V3D_PointDouble(-1.0d, -1.0d, -1.0d);
    public static final V3D_PointDouble pN1N1N2 = new V3D_PointDouble(-1.0d, -1.0d, -2.0d);
    public static final V3D_PointDouble pN1N2P2 = new V3D_PointDouble(-1.0d, -2.0d, 2.0d);
    public static final V3D_PointDouble pN1N2P1 = new V3D_PointDouble(-1.0d, -2.0d, 1.0d);
    public static final V3D_PointDouble pN1N2P0 = new V3D_PointDouble(-1.0d, -2.0d, 0.0d);
    public static final V3D_PointDouble pN1N2N1 = new V3D_PointDouble(-1.0d, -2.0d, -1.0d);
    public static final V3D_PointDouble pN1N2N2 = new V3D_PointDouble(-1.0d, -2.0d, -2.0d);
    // N2xx
    public static final V3D_PointDouble pN2P2P2 = new V3D_PointDouble(-2.0d, 2.0d, 2.0d);
    public static final V3D_PointDouble pN2P2P1 = new V3D_PointDouble(-2.0d, 2.0d, 1.0d);
    public static final V3D_PointDouble pN2P2P0 = new V3D_PointDouble(-2.0d, 2.0d, 0.0d);
    public static final V3D_PointDouble pN2P2N1 = new V3D_PointDouble(-2.0d, 2.0d, -1.0d);
    public static final V3D_PointDouble pN2P2N2 = new V3D_PointDouble(-2.0d, 2.0d, -2.0d);
    public static final V3D_PointDouble pN2P1P2 = new V3D_PointDouble(-2.0d, 1.0d, 2.0d);
    public static final V3D_PointDouble pN2P1P1 = new V3D_PointDouble(-2.0d, 1.0d, 1.0d);
    public static final V3D_PointDouble pN2P1P0 = new V3D_PointDouble(-2.0d, 1.0d, 0.0d);
    public static final V3D_PointDouble pN2P1N1 = new V3D_PointDouble(-2.0d, 1.0d, -1.0d);
    public static final V3D_PointDouble pN2P1N2 = new V3D_PointDouble(-2.0d, 1.0d, -2.0d);
    public static final V3D_PointDouble pN2P0P2 = new V3D_PointDouble(-2.0d, 0.0d, 2.0d);
    public static final V3D_PointDouble pN2P0P1 = new V3D_PointDouble(-2.0d, 0.0d, 1.0d);
    public static final V3D_PointDouble pN2P0P0 = new V3D_PointDouble(-2.0d, 0.0d, 0.0d);
    public static final V3D_PointDouble pN2P0N1 = new V3D_PointDouble(-2.0d, 0.0d, -1.0d);
    public static final V3D_PointDouble pN2P0N2 = new V3D_PointDouble(-2.0d, 0.0d, -2.0d);
    public static final V3D_PointDouble pN2N1P2 = new V3D_PointDouble(-2.0d, -1.0d, 2.0d);
    public static final V3D_PointDouble pN2N1P1 = new V3D_PointDouble(-2.0d, -1.0d, 1.0d);
    public static final V3D_PointDouble pN2N1P0 = new V3D_PointDouble(-2.0d, -1.0d, 0.0d);
    public static final V3D_PointDouble pN2N1N1 = new V3D_PointDouble(-2.0d, -1.0d, -1.0d);
    public static final V3D_PointDouble pN2N1N2 = new V3D_PointDouble(-2.0d, -1.0d, -2.0d);
    public static final V3D_PointDouble pN2N2P2 = new V3D_PointDouble(-2.0d, -2.0d, 2.0d);
    public static final V3D_PointDouble pN2N2P1 = new V3D_PointDouble(-2.0d, -2.0d, 1.0d);
    public static final V3D_PointDouble pN2N2P0 = new V3D_PointDouble(-2.0d, -2.0d, 0.0d);
    public static final V3D_PointDouble pN2N2N1 = new V3D_PointDouble(-2.0d, -2.0d, -1.0d);
    public static final V3D_PointDouble pN2N2N2 = new V3D_PointDouble(-2.0d, -2.0d, -2.0d);
    public V3D_TestDouble() {}
}
