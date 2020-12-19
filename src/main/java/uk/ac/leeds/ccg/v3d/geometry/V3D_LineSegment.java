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

import ch.obermuhlner.math.big.BigRational;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Class for a line segment in 3D represented by two Point3D instances. Two
 * instances are regarded as equal i they are represented by the same points
 * irrespective as which is assigned as {@link #p} and which is assigned as
 * {@link #q}.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_LineSegment extends V3D_Line implements V3D_FiniteGeometry {

    private static final long serialVersionUID = 1L;

    /**
     * For storing the envelope of this.
     */
    protected V3D_Envelope en;

    /**
     * For storing the unit vector. Only if the direction aligns with an axis is
     * this precise. Otherwise the precision is given by
     * {@link #unitVectorScale}.
     */
    public V3D_Vector unitVector;

    /**
     * For storing the scale at which the {@link #unitVector} is precise given
     * {@link #unitVectorRoundingMode}.
     */
    public int unitVectorScale;

    /**
     * Used for calculating the {@link #unitVector} if necessary.
     */
    public RoundingMode unitVectorRoundingMode;

    /**
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     */
    public V3D_LineSegment(V3D_Point p, V3D_Point q) {
        super(p, q);
    }

    /**
     * @param l Vector_LineSegment3D
     */
    public V3D_LineSegment(V3D_Line l) {
        super(l);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V3D_LineSegment) {
            V3D_LineSegment l = (V3D_LineSegment) o;
            if (l.p.equals(p) && l.q.equals(q)) {
                return true;
            }
            if (l.p.equals(q) && l.q.equals(p)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param l The line segment to test if it is the same as {@code this}.
     * @return {@code true} iff {@code l} is the same as {@code this}.
     */
    public boolean equals(V3D_LineSegment l) {
        return p.equals(l.p) && q.equals(l.q);
    }
    
    /**
     * @param l The line segment to test if it is equal to {@code this}.
     * @return {@code true} iff {@code this} is equal to {@code l} ignoring the
     * direction of {@link #v}.
     */
    public boolean equalsIgnoreDirection(V3D_LineSegment l) {
        if (equals(l)) {
            return true;
        } else {
            return p.equals(l.q) && q.equals(l.p);
        }
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.p);
        hash = 31 * hash + Objects.hashCode(this.q);
        return hash;
    }
    
    /**
     * @return {@code true} iff this line segment is effectively a point. 
     */
    public boolean isPoint() {
        return p.equals(q);
    }

    /**
     * For getting the unit vector of this line to {@code scale} precision using
     * {@code rm} if necessary.
     *
     * @param scale The scale for the precision of the result.
     * @param rm The RoundingMode for any rounding.
     * @return The unit vector of this line to {@code scale} precision using
     * {@code rm} if necessary.
     */
    public V3D_Vector getUnitVector(int scale, RoundingMode rm) {
        if (unitVector == null) {
            return initUnitVector(scale, rm);
        } else {
            if (scale > unitVectorScale) {
                return initUnitVector(scale, rm);
            } else {
                if (unitVectorRoundingMode.equals(rm)) {
                    return new V3D_Vector(unitVector.dx, unitVector.dy,
                            unitVector.dz);
                } else {
                    if (scale < unitVectorScale) {
                        return new V3D_Vector(unitVector.dx, unitVector.dy,
                                unitVector.dz);
                    } else {
                        return initUnitVector(scale, rm);
                    }
                }
            }
        }
    }

    /**
     * For initialising and returning the unit vector of this line to
     * {@code scale} precision using {@code rm} if necessary.
     *
     * @param scale The scale for the precision of the result.
     * @param rm The RoundingMode for any rounding.
     * @return The unit vector of this line to {@code scale} precision using
     * {@code rm} if necessary.
     */
    public V3D_Vector initUnitVector(int scale, RoundingMode rm) {
        if (unitVector == null) {
            BigRational distance = BigRational.valueOf(getLength(scale + 2, rm));
            unitVector = new V3D_Vector(v.dx.divide(distance),
                    v.dy.divide(distance), v.dz.divide(distance));
        }
        return unitVector;
    }

    /**
     * @param s The scaler value to multiply each coordinate of this by.
     * @return this multiplied by scalar
     */
    public V3D_LineSegment multiply(BigRational s) {
        return new V3D_LineSegment(
                new V3D_Point(p.x.multiply(s), p.y.multiply(s), p.z.multiply(s)),
                new V3D_Point(q.x.multiply(s), q.y.multiply(s), q.z.multiply(s)));
    }

    /**
     * @param scale The scale for the precision of the result.
     * @param rm The RoundingMode for any rounding.
     * @return The length of this as a BigDecimal
     */
    public BigDecimal getLength(int scale, RoundingMode rm) {
        return p.getDistance(q, scale, rm);
    }

    /**
     * @return {@code new V3D_Envelope(start, end)}
     */
    @Override
    public V3D_Envelope getEnvelope() {
        if (en == null) {
            en = new V3D_Envelope(p, q);
        }
        return en;
    }

    /**
     * @param p A point to test for intersection within the specified tolerance.
     * @return true if p is within t of this given scale.
     */
    @Override
    public boolean isIntersectedBy(V3D_Point p) {
        boolean ei = getEnvelope().isIntersectedBy(p.getEnvelope());
        if (ei) {
            return super.isIntersectedBy(p);
        }
        return false;
    }

    /**
     * @param l A line segment to indicate intersection with this.
     * @return {@code true} iff {@code l} intersects with {@code this}.
     */
    @Override
    public boolean isIntersectedBy(V3D_LineSegment l, boolean b) {
        boolean ei = getEnvelope().isIntersectedBy(l.getEnvelope());
        if (ei) {
            return super.isIntersectedBy(l);
        }
        return false;
    }

    /**
     * @param l A line to test for intersection within the specified tolerance.
     * @return true if p is within t of this given scale.
     */
    @Override
    public boolean isIntersectedBy(V3D_Line l) {
        V3D_Geometry i = super.getIntersection(l);
        if (i == null) {
            return false;
        }
        if (i instanceof V3D_Point) {
            return isIntersectedBy((V3D_Point) i);
        } else {
            return true;
        }
    }

    /**
     * Intersects {@code this} with {@code l}. If they are equivalent then
     * return {@code this}. If they overlap in a line return the part that
     * overlaps (the order of points is not defined). If they intersect at a
     * point, the point is returned. {@code null} is returned if the two line
     * segments do not intersect.
     *
     * @param l The line to get intersection with this.
     * @return The intersection between {@code this} and {@code l}.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_Line l) {
        V3D_Geometry i = super.getIntersection(l);
        if (i == null) {
            return i;
        }
        if (i instanceof V3D_Point) {
            if (isIntersectedBy((V3D_Point) i)) {
                return i;
            } else {
                return null;
            }
        } else {
            return this;
        }
    }
    
    /**
     * Intersects {@code this} with {@code l}. If they are equivalent then
     * return {@code this}. If they overlap in a line return the part that
     * overlaps (the order of points is not defined). If they intersect at a
     * point, the point is returned. {@code null} is returned if the two line
     * segments do not intersect.
     *
     * @param l The line to get intersection with this.
     * @param b To distinguish this method from
     * {@link #getIntersection(uk.ac.leeds.ccg.v3d.geometry.V3D_Line)}.
     * @return The intersection between {@code this} and {@code l}.
     */
    @Override
    public V3D_Geometry getIntersection(V3D_LineSegment l, boolean b) {
        return getIntersection(this, l);
    }

    /**
     * A utility method for calculating and returning the intersection between 
     * {@code l0} and {@code l1}
     * @param l0 Line to intersect with {@code l1}.
     * @param l1 Line to intersect with {@code l0}.
     * @return The intersection between {@code l0} and {@code l1}.
     */
    public static V3D_Geometry getIntersection(V3D_LineSegment l0, V3D_LineSegment l1) {
        V3D_Envelope ren = l0.getEnvelope().getIntersection(l1.getEnvelope());
        if (ren == null) {
            return null;
        }
        V3D_Geometry li = V3D_Line.getIntersection(l0, l1);
        if (li == null) {
            return null;
        }
        if (li instanceof V3D_Point) {
            return li;
        }
        /**
         * Check the type of intersection. {@code
         * 1)   p ---------- q
         *         l.p ---------- l.q
         * 2)   p ------------------------ q
         *         l.p ---------- l.q
         * 3)        p ---------- q
         *    l.p ------------------------ l.q
         * 4)        p ---------- q
         *    l.p ---------- l.q
         * 5)   q ---------- p
         *         l.p ---------- l.q
         * 6)   q ------------------------ p
         *         l.p ---------- l.q
         * 7)        q ---------- p
         *    l.p ------------------------ l.q
         * 8)        q ---------- p
         *    l.p ---------- l.q
         * 9)   p ---------- q
         *         l.q ---------- l.p
         * 10)   p ------------------------ q
         *         l.q ---------- l.p
         * 11)       p ---------- q
         *    l.q ------------------------ l.p
         * 12)       p ---------- q
         *    l.q ---------- l.p
         * 13)  q ---------- p
         *         l.q ---------- l.p
         * 14)  q ------------------------ p
         *         l.q ---------- l.p
         * 15)       q ---------- p
         *    l.q ------------------------ l.p
         * 16)       q ---------- p
         *    l.q ---------- l.p
         * }
         */
        if (l0.isIntersectedBy(l1.p)) {
            // Cases 1, 2, 5, 6, 14, 16
            if (l0.isIntersectedBy(l1.q)) {
                // Cases 2, 6, 14
                /**
                 * The line segments are effectively the same although the start
                 * and end points may be opposite.
                 */
                return l1;
            } else {
                // Cases 1, 5, 16
                if (l1.isIntersectedBy(l0.p)) {
                    // Cases 5
                    return new V3D_LineSegment(l1.p, l0.p);
                } else {
                    // Cases 1, 16
                    return new V3D_LineSegment(l1.p, l0.q);
                }
            }
        } else {
            // Cases 3, 4, 7, 8, 9, 10, 11, 12, 13, 15
            if (l0.isIntersectedBy(l1.q)) {
                // Cases 4, 8, 9, 10, 11
                if (l1.isIntersectedBy(l0.p)) {
                    // Cases 4, 11, 13
                    if (l1.isIntersectedBy(l0.q)) {
                        // Cases 11
                        return l0;
                    } else {
                        // Cases 4, 13
                        return new V3D_LineSegment(l0.p, l1.q);
                    }
                } else {
                    // Cases 8, 9, 10
                    if (l1.isIntersectedBy(l0.q)) {
                        // Cases 8, 9
                        return new V3D_LineSegment(l0.q, l1.q);
                    } else {
                        // Cases 10                      
                        return l1;
                    }
                }
            } else {
                // Cases 3, 7, 12, 15
                if (l1.isIntersectedBy(l0.p)) {
                    // Cases 3, 12, 15
                    if (l1.isIntersectedBy(l0.q)) {
                        // Cases 3, 15
                        return l0;
                    } else {
                        // Cases 12                 
                        return new V3D_LineSegment(l0.p, l1.p);
                    }
                } else {
                    // Cases 7
                    return l0;
                }
            }
        }
    }
    
    @Override
    public boolean isEnvelopeIntersectedBy(V3D_Line l) {
        return getEnvelope().isIntersectedBy(l);
    }
}
