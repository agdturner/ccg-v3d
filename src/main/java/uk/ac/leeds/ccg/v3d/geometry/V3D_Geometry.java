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
import java.io.Serializable;
import java.math.RoundingMode;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;

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
public abstract class V3D_Geometry implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The offset used to position a geometry object relative to the
     * {@link V3D_Point#ORIGIN}.
     */
    public V3D_Vector offset;

    /**
     * Creates a new instance.
     */
    public V3D_Geometry() {
        this(V3D_Vector.ZERO);
    }

    /**
     * Creates a new instance.
     *
     * @param offset What {@link #offset} is set to.
     */
    public V3D_Geometry(V3D_Vector offset) {
        this.offset = offset;
    }

    /**
     * @param pad The padding.
     * @return A padded description.
     */
    protected String toStringFields(String pad) {
        return pad + "offset=" + offset.toString(pad);
    }

    /**
     * @param pad The padding.
     * @return A padded description.
     */
    protected String toStringFieldsSimple(String pad) {
        return pad + "offset=" + offset.toStringSimple("");
    }

    /**
     * Translate (move relative to the origin).
     *
     * @param v The translation vector.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     */
    public void translate(V3D_Vector v, int oom, RoundingMode rm) {
        offset = offset.add(v, oom, rm);
    }

    /**
     * Returns the geometry rotated about the ray by the angle theta. Options 
     * for rotation include:
     * <ul>
     * <li>Rotation Matrix https://en.wikipedia.org/wiki/Rotation_matrix</li>
     * <li>Quaternions
     * https://en.wikipedia.org/wiki/Quaternions_and_spatial_rotation</li>
     * <li>Rotation_matrix_from_axis_and_angle
     * https://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle</li>
     * </ul>
     * See also:
     * <ul>
     * <li>https://en.wikipedia.org/wiki/3D_rotation_group#Exponential_map</li>
     * <li>https://en.wikipedia.org/wiki/Rodrigues%27_rotation_formula</li>
     * <li>https://en.wikipedia.org/wiki/3D_rotation_group</li>
     * </ul>
     *
     * @param r The axis of rotation.
     * @param theta The angle of rotation around the {@code axisOfRotation} in
     * radians. 
     * @param r The ray defining the axis of rotation about which the geometry
     * is rotated.
     * @param uv The unit vector of r. This is passed in as often there is a 
     * desire to rotate many geometries about a ray and this saves computation 
     * in calculating the unit vector each time.
     * @param bd The Math_BigDecimal.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The rotated geometry.
     */
    public abstract V3D_Geometry rotate(V3D_Ray r, V3D_Vector uv, 
            Math_BigDecimal bd, BigRational theta, int oom, RoundingMode rm);
    
    /**
     * Returns the geometry rotated about the axis of rotation axisOfRotation by
     * the angle theta. In this geometry a positive rotation angle is in the 
     * direction of the fingers of the right hand as the thumb points in the 
     * direction of the axis of rotation.
     * @param ray The ray defining the axis of rotation about which the geometry
     * is rotated.
     * @param uv The unit vector of r. This is passed in as often there is a 
     * desire to rotate many geometries about a ray and this saves computation 
     * in calculating the unit vector each time.
     * @param bd The Math_BigDecimal.
     * @param theta The normal angle of rotation (theta &gt; 0 && theta &lt; 
     * 2Pi.) around the the rotation axis in radians.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The rotated geometry.
     */
    public abstract V3D_Geometry rotateN(V3D_Ray ray,  V3D_Vector uv, 
            Math_BigDecimal bd, BigRational theta, int oom, RoundingMode rm);
}