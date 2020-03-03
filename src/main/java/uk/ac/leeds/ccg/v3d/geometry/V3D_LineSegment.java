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
import uk.ac.leeds.ccg.math.Math_BigDecimal;

/**
 * Class for a line segment in 3D represented by two Point3D instances, one is
 * designated a start point and the other an end point. In this way a line
 * segment explicitly has a direction. Two instances are regarded as equal if
 * their start and end points are the same.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_LineSegment extends V3D_Line
        implements V3D_FiniteGeometry {

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
     * @param start What {@link #p} is set to.
     * @param end What {@link #q} is set to.
     */
    public V3D_LineSegment(V3D_Point start, V3D_Point end) {
        super(start, end);
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
            if (hashCode() == l.hashCode()) {
                if (l.p.equals(p)) {
                    if (l.q.equals(q)) {
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
        hash = 31 * hash + Objects.hashCode(this.p);
        hash = 31 * hash + Objects.hashCode(this.q);
        return hash;
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
                    return new V3D_Vector(unitVector.dx.setScale(scale),
                            unitVector.dy.setScale(scale),
                            unitVector.dz.setScale(scale));
                } else {
                    if (scale < unitVectorScale) {
                        return new V3D_Vector(unitVector.dx.setScale(scale),
                                unitVector.dy.setScale(scale),
                                unitVector.dz.setScale(scale));
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
            BigDecimal distance = getLength(scale + 2, rm);
            unitVector = new V3D_Vector(
            Math_BigDecimal.divideRoundIfNecessary(pq.dx, distance, scale, rm),
            Math_BigDecimal.divideRoundIfNecessary(pq.dy, distance, scale, rm),
            Math_BigDecimal.divideRoundIfNecessary(pq.dz, distance, scale, rm));
        }
        return unitVector;
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
    public V3D_Envelope getEnvelope3D() {
        return new V3D_Envelope(p, q);
    }

    /**
     * @param en The envelope to test.
     * @param scale The scale for the precision of the test.
     * @return {@code true} if this intersects with {@code en}.
     * @throws java.lang.Exception
     */
    public boolean getIntersects(V3D_Envelope en, int scale) throws Exception {
        return en.getIntersects(this, scale);
    }

    /**
     * @param p A point to test for intersection within the specified tolerance.
     * @param scale The scale.
     * @return true if p is within t of this given scale.
     */
    public boolean getIntersects(V3D_Point p, int scale) {
        return false;
    }
}
