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

import java.io.Serializable;

/**
 * For 3D Euclidean geometrical objects. The three dimensions have are
 * orthogonal axes X, Y, and Z that meet at the origin point {@code<0, 0, 0>}
 * where {@code x=0, y=0 and z=0} the following depicts the origin and
 * dimensions. {@code
 *                                       z
 *                          y           -
 *                          +          /
 *                          |         /
 *                          |        /
 *                          |       /
 *                          |      /
 *                          |     /
 *                          |    /
 *                          |   /
 *                          |  /
 *                          | /
 *                          |/
 * x - ---------------------|------------------------ + x
 *                         /|
 *                        / |
 *                       /  |
 *                      /   |
 *                     /    |
 *                    /     |
 *                   /      |
 *                  /       |
 *                 +        -
 *                z         y
 * }
 *
 * @author Andy Turner
 * @version 1.0
 */
public abstract class V3D_VGeometry implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The offset used to get the original geometry relative to {@link V3D_V#ZERO}.
     */
    public V3D_V offset;

    /**
     * For storing the centroid.
     */
    public V3D_V centroid;

    /**
     * Creates a new V3D_Geometry.
     * 
     * @param offset What {@link #offset} is set to.
     */
    public V3D_VGeometry(V3D_V offset) {
        this.offset = offset;
    }

    /**
     * @param v The vector to apply.
     */
    public abstract void apply(V3D_V v);
    
    /**
     * For getting the centroid of the geometry.
     */
    public abstract V3D_V getCentroid();
    
    
    
}
