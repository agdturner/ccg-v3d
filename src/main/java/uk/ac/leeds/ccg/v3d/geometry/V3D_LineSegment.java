/*
 * Copyright 2019 Andy Turner, University of Leeds.
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
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Class for a line segment in 3D represented by two Point3D instances, one is
 * designated a start point and the other an end point. In this way a line
 * segment explicitly has a direction. Two instances are regarded as equal if
 * their start and end points are the same.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_LineSegment extends V3D_Geometry
        implements V3D_FiniteGeometry {

    public V3D_Point start;
    public V3D_Point end;

    /**
     * @param start What {@link #start} is set to.
     * @param end What {@link #end} is set to.
     */
    public V3D_LineSegment(V3D_Point start, V3D_Point end) {
        super(start.e);
        this.start = new V3D_Point(start);
        this.end = new V3D_Point(end);
    }

    /**
     * @param l Vector_LineSegment3D
     */
    public V3D_LineSegment(V3D_LineSegment l) {
        super(l.e);
        this.start = l.start;
        this.end = l.end;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(start=" + start.toString()
                + ", end=" + end.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V3D_LineSegment) {
            V3D_LineSegment l = (V3D_LineSegment) o;
            if (hashCode() == l.hashCode()) {
                if (l.start.equals(start)) {
                    if (l.end.equals(end)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.start);
        hash = 31 * hash + Objects.hashCode(this.end);
        return hash;
    }

    /**
     * @param scale The scale for the precision of the result.
     * @param rm The RoundingMode for any rounding.
     * @return The length of this as a BigDecimal
     */
    public BigDecimal getLength(int scale, RoundingMode rm) {
        return start.getDistance(end, scale, rm);
    }

    /**
     * @return {@code new V3D_Envelope(start, end)}
     */
    @Override
    public V3D_Envelope getEnvelope3D() {
        return new V3D_Envelope(start, end);
    }

    /**
     * Calculate and return the
     * <A href="https://en.wikipedia.org/wiki/Dot_product">dot product</A>.
     *
     * @param l V3D_LineSegment
     * @return dot product
     */
    public BigDecimal getDotProduct(V3D_LineSegment l) {
        return (l.end.x.multiply(this.end.x)).add(l.end.y.multiply(this.end.y))
                .add(l.end.z.multiply(this.end.z));
    }

    /**
     * Treat this as the first vector and {@code l} as the second vector. So the
     * resulting vector starts at {@link #start} and is in the direction given
     * by the right hand rule.
     *
     * @param l V3D_LineSegment
     * @return BigDecimal
     */
    public V3D_LineSegment getCrossProduct(V3D_LineSegment l) {
        BigDecimal ax = end.x.subtract(start.x);
        BigDecimal ay = end.y.subtract(start.y);
        BigDecimal az = end.z.subtract(start.z);
        BigDecimal bx = l.end.x.subtract(l.start.x);
        BigDecimal by = l.end.y.subtract(l.start.y);
        BigDecimal bz = l.end.z.subtract(l.start.z);
        BigDecimal dx = ay.multiply(bz).subtract(bz.multiply(ay));
        BigDecimal dy = az.multiply(bx).subtract(bx.multiply(az));
        BigDecimal dz = ax.multiply(by).subtract(by.multiply(ax));
        V3D_Point newEnd = new V3D_Point(e, start.x.add(dx),
                start.y.add(dy), start.z.add(dz));
        return new V3D_LineSegment(start, newEnd);
    }

    public boolean getIntersects(BigDecimal xMin, BigDecimal yMin,
            BigDecimal xMax, BigDecimal yMax, BigDecimal zMin,
            BigDecimal zMax, BigDecimal t, int scale) {
        return false; // @Todo
    }

    /**
     *
     * @param p A point to test for intersection within the specified tolerance.
     * @param t The tolerance.
     * @param scale The scale.
     * @return true if p is within t of this given scale.
     */
    public boolean getIntersects(V3D_Point p, BigDecimal t, int scale) {
        return false;
    }
}
