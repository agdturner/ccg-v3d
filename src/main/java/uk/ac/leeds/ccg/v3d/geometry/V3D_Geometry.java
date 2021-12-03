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
import java.math.BigDecimal;
import uk.ac.leeds.ccg.math.number.Math_BigRational;

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
     * The offset used to get the geometry relative to the {@link V3D_Point#ORIGIN}.
     */
    public V3D_Vector offset;
    
//    Options for rotation include:
//    Rotation Matrix https://en.wikipedia.org/wiki/Rotation_matrix
//    Quaternions https://en.wikipedia.org/wiki/Quaternions_and_spatial_rotation
//    /**
//     * The axis of rotation of the geometry passing through the offset point 
//     * used to rotate geometries. 
//     */
//    public V3D_Vector axisOfRotation;
//    
//    /**
//     * The angleOfRotation around the axis, the sine and the cosine of which 
//     * help to calculate actual coordinates.
//     */
//    public Math_BigRational theta;
//    Rotation_matrix_from_axis_and_angle https://en.wikipedia.org/wiki/Rotation_matrix#Rotation_matrix_from_axis_and_angle
    
    /**
     * The Order of Magnitude for the precision.
     */
    protected int oom;
    
    /**
     * Creates a new V3D_Geometry.
     * 
     * @param offset What {@link #offset} is set to.
     * @param oom What {@link #oom} is set to.
     */
    public V3D_Geometry(V3D_Vector offset, int oom) {
        this.offset = offset;
        this.oom = oom;
    }
    
    /**
     * @param pad The padding.
     * @return A padded description.
     */
    public String toString(String pad) {
        return pad + "offset=" + offset.toStringFields(pad + " ") + ",\n"
                + "oom=" + oom;
    }
    
    /**
     * @return {@link #oom}
     */
    public int getOom() {
        return oom;
    }

    /**
     * @param oom What {@link #oom} is set to.
     */
    public void setOom(int oom) {
        this.oom = oom;
    }

    /**
     * @param l The line for which intersection with the envelope is indicated.
     * @param oom The Order of Magnitude for the precision.
     * @return {@code true} iff {@code l} intersects with the envelope.
     */
    public abstract boolean isEnvelopeIntersectedBy(V3D_Line l, int oom);

    /**
     * @param v The vector to apply.
     * @param oom The Order of Magnitude for the precision.
     */
    public final void apply(int oom, V3D_Vector v) {
        offset = offset.add(v, oom);
    }
    
//    /**
//     * @param v The vector to apply.
//     * @param oom The Order of Magnitude for the precision.
//     * @return The geometry with v applied.
//     */
//    public abstract V3D_Geometry apply(V3D_Vector v, int oom);
    
    /**
     * Get the distance between this and {@code p}.
     *
     * @param p A point.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The distance from {@code p} to this.
     */
    public abstract BigDecimal getDistance(V3D_Point p, int oom);
    
    /**
     * Get the minimum distance between this and {@code l}.
     *
     * @param l A point.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The minimum distance from {@code l} to this.
     */
    public abstract BigDecimal getDistance(V3D_Line l, int oom);
    
    /**
     * Get the minimum distance between this and {@code l}.
     *
     * @param l A point.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The minimum distance from {@code l} to this.
     */
    public abstract BigDecimal getDistance(V3D_LineSegment l, int oom);
    
}
