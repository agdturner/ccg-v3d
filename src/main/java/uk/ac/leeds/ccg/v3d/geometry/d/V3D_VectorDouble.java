/*
 * Copyright 2022 Andy Turner, University of Leeds.
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

import java.io.Serializable;
import java.util.Objects;
import uk.ac.leeds.ccg.math.arithmetic.Math_Double;
import uk.ac.leeds.ccg.math.number.Math_Quaternion_Double;
import uk.ac.leeds.ccg.v3d.geometry.d.light.V3D_VDouble;

/**
 * A vector.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_VectorDouble implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The change in x.
     */
    public final double dx;

    /**
     * The change in y.
     */
    public final double dy;

    /**
     * The change in z.
     */
    public final double dz;

    /**
     * For storing the magnitude.
     */
    protected double m;

    /**
     * The zero vector {@code <0,0,0>} where:
     * {@link #dx} = {@link #dy} = {@link #dz} = 0.
     */
    public static final V3D_VectorDouble ZERO = new V3D_VectorDouble(0, 0, 0);

    /**
     * The I vector {@code <1,0,0>}.
     */
    public static final V3D_VectorDouble I = new V3D_VectorDouble(1, 0, 0);

    /**
     * The J vector {@code <0,1,0>}.
     */
    public static final V3D_VectorDouble J = new V3D_VectorDouble(0, 1, 0);

    /**
     * The K vector {@code <0,0,1>}.
     */
    public static final V3D_VectorDouble K = new V3D_VectorDouble(0, 0, 1);

    /**
     * The IJ vector {@code <1,1,0>}.
     */
    public static final V3D_VectorDouble IJ = new V3D_VectorDouble(1, 1, 0);

    /**
     * The IK vector {@code <1,0,1>}.
     */
    public static final V3D_VectorDouble IK = new V3D_VectorDouble(1, 0, 1);

    /**
     * The JK vector {@code <0,1,1>}.
     */
    public static final V3D_VectorDouble JK = new V3D_VectorDouble(0, 1, 1);

    /**
     * The InJ vector {@code <1,-1,0>}.
     */
    public static final V3D_VectorDouble InJ = new V3D_VectorDouble(1, -1, 0);

    /**
     * The InK vector {@code <1,0,-1>}.
     */
    public static final V3D_VectorDouble InK = new V3D_VectorDouble(1, 0, -1);

    /**
     * The JnK vector {@code <0,1,-1>}.
     */
    public static final V3D_VectorDouble JnK = new V3D_VectorDouble(0, 1, -1);

    /**
     * The nIJ vector {@code <-1,1,0>}.
     */
    public static final V3D_VectorDouble nIJ = new V3D_VectorDouble(-1, 1, 0);

    /**
     * The nInJ vector {@code <-1,-1,0>}.
     */
    public static final V3D_VectorDouble nInJ = new V3D_VectorDouble(-1, -1, 0);

    /**
     * The nInK vector {@code <-1,0,-1>}.
     */
    public static final V3D_VectorDouble nInK = new V3D_VectorDouble(-1, 0, -1);

    /**
     * The nJnK vector {@code <-1,0,-1>}.
     */
    public static final V3D_VectorDouble nJnK = new V3D_VectorDouble(-1, 0, -1);

    /**
     * The IJK vector {@code <1,1,1>} where:
     */
    public static final V3D_VectorDouble IJK = new V3D_VectorDouble(1, 1, 1);

    /**
     * The IJnK vector {@code <1,1,-1>} where:
     */
    public static final V3D_VectorDouble IJnK = new V3D_VectorDouble(1, 1, -1);

    /**
     * The InJK vector {@code <1,-1,1>} where:
     */
    public static final V3D_VectorDouble InJK = new V3D_VectorDouble(1, -1, 1);

    /**
     * The nIJK vector {@code <-1,1,1>} where:
     */
    public static final V3D_VectorDouble nIJK = new V3D_VectorDouble(-1, 1, 1);

    /**
     * The InJnK vector {@code <1,-1,-1>} where:
     */
    public static final V3D_VectorDouble InJnK = new V3D_VectorDouble(1, -1, -1);

    /**
     * The nInJK vector {@code <-1,-1,1>} where:
     */
    public static final V3D_VectorDouble nInJK = new V3D_VectorDouble(-1, -1, 1);

    /**
     * The nIJnK vector {@code <-1,1,-1>} where:
     */
    public static final V3D_VectorDouble nIJnK = new V3D_VectorDouble(-1, 1, -1);

    /**
     * The nInJnK vector {@code <-1,-1,-1>} where:
     */
    public static final V3D_VectorDouble nInJnK = new V3D_VectorDouble(-1, -1, -1);

    /**
     * Create a new instance.
     *
     * @param v Used to initialise this. A deep copy of all components is made
     * so that {@code this} is completely independent of {@code v}.
     */
    public V3D_VectorDouble(V3D_VectorDouble v) {
        this.dx = v.dx;
        this.dy = v.dy;
        this.dz = v.dz;
    }

    /**
     * Create a new instance.
     *
     * @param dx What {@link #dx} is set to.
     * @param dy What {@link #dy} is set to.
     * @param dz What {@link #dz} is set to.
     */
    public V3D_VectorDouble(double dx, double dy, double dz) {
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }

    /**
     * @param dx Used to initialise {@link #dx}.
     * @param dy Used to initialise {@link #dy}.
     * @param dz Used to initialise {@link #dz}.
     * @param m What {@link #m} is set to.
     */
    public V3D_VectorDouble(double dx, double dy, double dz, double m) {
        this(dx, dy, dz);
        this.m = m;
    }

    /**
     * Creates a vector from {@code p} to {@code q}.
     *
     * @param p the point where the vector starts.
     * @param q the point where the vector ends.
     */
    public V3D_VectorDouble(V3D_PointDouble p, V3D_PointDouble q) {
        this(q.getX() - p.getX(), q.getY() - p.getY(), q.getZ() - p.getZ());
    }

    /**
     * Creates a vector from the Origin to {@code p} to {@code q}.
     *
     * @param p the point where the vector starts.
     */
    public V3D_VectorDouble(V3D_PointDouble p) {
        this(p.getVector());
    }

    /**
     * Creates a vector from {@code v}.
     *
     * @param v the light Vector.
     */
    public V3D_VectorDouble(V3D_VDouble v) {
        this(v.x, v.y, v.z);
    }

    @Override
    public String toString() {
        //return toString("");
        return toStringSimple("");
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of this.
     */
    public String toString(String pad) {
        return this.getClass().getSimpleName() + "\n"
                + pad + "(\n"
                + toStringFields(pad + " ") + "\n"
                + pad + ")";
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of this.
     */
    public String toStringSimple(String pad) {
        return pad + this.getClass().getSimpleName()
                + "(" + toStringFieldsSimple("") + ")";
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of the fields.
     */
    protected String toStringFields(String pad) {
        return pad + "dx=" + dx + ",\n"
                + pad + "dy=" + dy + ",\n"
                + pad + "dz=" + dz;
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of the fields.
     */
    protected String toStringFieldsSimple(String pad) {
        return pad + "dx=" + dx
                + ", dy=" + dy
                + ", dz=" + dz;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj instanceof V3D_VectorDouble v) {
            return equals(v);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.dx);
        hash = 89 * hash + Objects.hashCode(this.dy);
        hash = 89 * hash + Objects.hashCode(this.dz);
        return hash;
    }

    /**
     * Indicates if {@code this} and {@code v} are equal.
     *
     * @param v The vector to test for equality with {@code this}.
     * @return {@code true} iff {@code this} is the same as {@code v}.
     */
    public boolean equals(V3D_VectorDouble v) {
        /**
         * The hashcode cannot be used to speed things up as there is the case
         * of -0.0 == 0.0!
         */
        //if (hashCode() == v.hashCode()) {
        return dx == v.dx && dy == v.dy && dz == v.dz;
        //}
        //return false;
    }

    /**
     * @param points The points to test if they are coincident.
     * @return {@code true} iff all the points are coincident.
     */
    public static boolean equals(V3D_VectorDouble... v) {
        V3D_VectorDouble v0 = v[0];
        for (V3D_VectorDouble v1 : v) {
            if (!v1.equals(v0)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Indicates if {@code this} and {@code v} are equal within the specified
     * tolerance. A tolerance of zero would mean that the vectors must be
     * exactly identical. Otherwise each of the coordinates is allowed to be up
     * to tolerance different and the vectors would still be considered equal.
     *
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @param v The vector to test for equality with {@code this}.
     * @return {@code true} iff {@code this} is the same as {@code v}.
     */
    public boolean equals(double epsilon, V3D_VectorDouble v) {
        return Math_Double.equals(dx, v.dx, epsilon)
                && Math_Double.equals(dy, v.dy, epsilon)
                && Math_Double.equals(dz, v.dz, epsilon);
    }

    /**
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @param vs The vectors to test for equality.
     * @return {@code true} iff all vs are equal within epsilon.
     */
    public static boolean equals(double epsilon, V3D_VectorDouble... vs) {
        if (vs.length < 2) {
            return true;
        }
        V3D_VectorDouble v0 = vs[0];
        for (V3D_VectorDouble v : vs) {
            if (!v.equals(v0)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Indicates if {@code this} is the reverse of {@code v}.
     *
     * @param v The vector to compare with {@code this}.
     * @return {@code true} iff {@code this} is the reverse of {@code v}.
     */
    public boolean isReverse(V3D_VectorDouble v) {
        return equals(v.reverse());
    }

    /**
     * Indicates if {@code this} is the reverse of {@code v}.
     *
     * @param v The vector to compare with {@code this}.
     * @param epsilon The tolerance within which two vectors are considered
     * equal.
     * @return {@code true} iff {@code this} is the reverse of {@code v}.
     */
    public boolean isReverse(V3D_VectorDouble v, double epsilon) {
        return equals(epsilon, v.reverse());
    }

    /**
     * @return {@code true} if {@code this.equals(ZERO)}
     */
    public boolean isZero() {
        return this.equals(ZERO);
    }

    /**
     * @return {@code true} if {@code this.equals(ZERO, epsilon)}
     * @param epsilon
     */
    public boolean isZero(double epsilon) {
        return equals(epsilon, ZERO);
    }

    /**
     * @param s The scalar value to multiply this by.
     * @return Scaled vector.
     */
    public V3D_VectorDouble multiply(double s) {
        return new V3D_VectorDouble(dx * s, dy * s, dz * s);
    }

    /**
     * @param s The scalar value to divide this by.
     * @return Scaled vector.
     */
    public V3D_VectorDouble divide(double s) {
        return new V3D_VectorDouble(dx / s, dy / s, dz / s);
    }

    /**
     * Add/apply/translate.
     *
     * @param v The vector to add.
     * @return A new vector which is {@code this} add {@code v}.
     */
    public V3D_VectorDouble add(V3D_VectorDouble v) {
        return new V3D_VectorDouble(dx + v.dx, dy + v.dy, dz + v.dz);
    }

    /**
     * @param v The vector to subtract.
     * @return A new vector which is {@code this} minus {@code v}.
     */
    public V3D_VectorDouble subtract(V3D_VectorDouble v) {
        return new V3D_VectorDouble(dx - v.dx, dy - v.dy, dz - v.dz);
    }

    /**
     * @return A new vector which is the opposite to {@code this}.
     */
    public V3D_VectorDouble reverse() {
        return new V3D_VectorDouble(-dx, -dy, -dz);
    }

    /**
     * Calculate and return the
     * <A href="https://en.wikipedia.org/wiki/Dot_product">dot product</A>.
     *
     * @param v The other vector to compose the cross product from.
     * @return The dot product.
     */
    public double getDotProduct(V3D_VectorDouble v) {
        return dx * v.dx + dy * v.dy + dz * v.dz;
    }

    /**
     * Test if this is orthogonal to {@code v}.
     *
     * @param v The vector to test for orthogonality with.
     * @return {@code true} if this and {@code v} are orthogonal.
     */
    public boolean isOrthogonal(V3D_VectorDouble v) {
//        // Special case
//        if (isScalarMultiple(v)) {
//            return false;
//        }

//        if (getCrossProduct(v).isZero()) {
//            return true;
//        }
        return getDotProduct(v) == 0d;
    }

    /**
     * Test if this is orthogonal to {@code v}.
     *
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @param v The vector to test for orthogonality with.
     * @return {@code true} if this and {@code v} are orthogonal.
     */
    public boolean isOrthogonal(double epsilon, V3D_VectorDouble v) {
        // Special case
        if (isScalarMultiple(epsilon, v)) {
            return false;
        }

//        if (getCrossProduct(v).isZero()) {
//            return true;
//        }
        return Math_Double.equals(getDotProduct(v), 0d, epsilon);
    }

//    /**
//     * Test if this is orthogonal to {@code v}.
//     *
//     * @param v The vector to test for orthogonality with.
//     * @param epsilon The tolerance within which two vectors are regarded as
//     * equal.
//     * @return {@code true} if this and {@code v} are orthogonal.
//     */
//    public boolean isOrthogonal(V3D_VectorDouble v, double epsilon) {
////        // Special case
////        if (isScalarMultiple(v, epsilon)) {
////            return false;
////        }
//        //return getDotProduct(v) == 0d;
//        double dp = getDotProduct(v);
//        return Math_Double.equals(dp, 0d, epsilon);
//        //return Math.abs(getDotProduct(v)) < epsilon;
//    }
    /**
     * @return The magnitude of m.
     */
    public double getMagnitudeSquared() {
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * @return The magnitude of m.
     */
    public double getMagnitude() {
        return Math.sqrt(getMagnitudeSquared());
    }

    /**
     * Test if {@code v} is a scalar multiple of {@code this}.
     *
     * @param v The vector to test if it is a scalar multiple of {@code this}.
     * @return {@code true} if {@code this} and {@code v} are scalar multiples.
     */
    public boolean isScalarMultiple(V3D_VectorDouble v) {
        if (equals(v)) {
            return true;
        } else {
            // Special cases
            boolean isZero = isZero();
            if (isZero) {
                /**
                 * Can't multiply the zero vector by a scalar to get a non-zero
                 * vector.
                 */
                return v.isZero();
            }
            if (v.isZero()) {
                /**
                 * Already tested that this is not equal to v, so the scalar is
                 * zero.
                 */
                return true;
            }
            /**
             * General case: A little complicated as there is a need to deal
             * with zero vector components and cases where the vectors point in
             * different directions.
             */
            if (Math.abs(v.dx) == Math.abs(dx)) {
                // |dx| = |v.dx|
                if (v.dx == 0d) {
                    // dx = v.dx = 0d
                    if (Math.abs(v.dy) == Math.abs(dy)) {
                        if (v.dy == 0d) {
                            return true;
                        } else {
                            if (Math.abs(v.dz) == Math.abs(dz)) {
//                                    if (v.dz == 0d) { 
//                                        // This should not happen as it is already tested for. Commented code left for clarity.
//                                        return true;
//                                    } else {
                                double scalar = v.dy / dy;
                                double dzs = dz * scalar;
                                double epsilon = Math_Double.getTolerance(v.dy, dy, scalar, v.dz, dz, dzs);
                                return Math_Double.equals(v.dz, dzs, epsilon);
//                                    }
                            } else {
                                return false;
                            }
                        }
                    } else {
                        if (v.dy == 0d) {
                            return v.dz == 0d;
                        } else {
                            if (Math.abs(v.dz) == Math.abs(dz)) {
                                if (v.dz == 0d) {
                                    return true;
                                } else {
                                    double scalar = v.dy / dy;
                                    double dzs = dz * scalar;
                                    double epsilon = Math_Double.getTolerance(v.dy, dy, scalar, v.dz, dz, dzs);
                                    return Math_Double.equals(v.dz, dzs, epsilon);
                                }
                            } else {
                                double scalar = v.dy / dy;
                                double dzs = dz * scalar;
                                double epsilon = Math_Double.getTolerance(v.dy, dy, scalar, v.dz, dz, dzs);
                                return Math_Double.equals(v.dz, dzs, epsilon);
                            }
                        }
                    }
                } else {
                    // |dx| = |v.dx| != 0d
                    if (Math.abs(v.dy) == Math.abs(dy)) {
                        if (v.dy == 0d) {
                            if (Math.abs(v.dz) == Math.abs(dz)) {
                                if (v.dz == 0d) {
                                    return true;
                                } else {
                                    double scalar = v.dx / dx;
                                    double dzs = dz * scalar;
                                    double epsilon = Math_Double.getTolerance(v.dy, dy, scalar, v.dz, dz, dzs);
                                    return Math_Double.equals(v.dz, dzs, epsilon);
                                }
                            } else {
                                double scalar = v.dx / dx;
                                double dzs = dz * scalar;
                                double epsilon = Math_Double.getTolerance(v.dy, dy, scalar, v.dz, dz, dzs);
                                return Math_Double.equals(v.dz, dzs, epsilon);
                            }
                        } else {
                            double scalar = v.dx / dx;
                            double dys = dy * scalar;
                            double epsilon = Math_Double.getTolerance(v.dx, dx, scalar, v.dy, dy, dys);
                            if (Math_Double.equals(v.dy, dy * scalar, epsilon)) {
                                if (Math_Double.equals(Math.abs(v.dz), Math.abs(dz), epsilon)) {
                                    if (Math_Double.equals(v.dz, 0d, epsilon)) {
                                        return true;
                                    } else {
                                        double dzs = dz * scalar;
                                        epsilon = Math_Double.getTolerance(v.dx, dx, scalar, v.dy, dy, dys, v.dz, dz, dzs);
                                        return Math_Double.equals(v.dz, dzs, epsilon);
                                    }
                                } else {
                                    double dzs = dz * scalar;
                                    epsilon = Math_Double.getTolerance(v.dx, dx, scalar, v.dy, dy, dys, v.dz, dz, dzs);
                                    return Math_Double.equals(v.dz, dzs, epsilon);
                                }
                            } else {
                                return false;
                            }
                        }
                    } else {
                        // |dx| = |v.dx| != 0d, |dy| != |v.dy|
                        double scalar = v.dx / dx;
                        double dys = dy * scalar;
                        double epsilon = Math_Double.getTolerance(v.dx, dx, scalar, v.dy, dy, dys);
                        if (Math_Double.equals(v.dy, dys, epsilon)) {
                            double dzs = dz * scalar;
                            epsilon = Math_Double.getTolerance(v.dx, dx, scalar, v.dy, dy, dys, v.dz, dz, dzs);
                            return Math_Double.equals(v.dz, dzs, epsilon);
                        } else {
                            return false;
                        }
                    }
                }
            } else {
                // |dx| != |v.dx|
                if (dx == 0d) {
                    return isZero;
                } else {
                    double scalar = v.dx / dx;
                    double dys = dy * scalar;
                    double epsilon = Math_Double.getTolerance(v.dx, dx, scalar, v.dy, dy, dys);
                    if (Math_Double.equals(v.dy, dys, epsilon)) {
                        double dzs = dz * scalar;
                        epsilon = Math_Double.getTolerance(v.dx, dx, scalar, v.dy, dy, dys, v.dz, dz, dzs);
                        return Math_Double.equals(v.dz, dzs, epsilon);
                    } else {
                        return false;
                    }
                }
            }
        }
    }

    /**
     * Test if {@code v} is a scalar multiple of {@code this}.
     *
     * @param epsilon The tolerance within which two vectors are regarded as
     * equal.
     * @param v The vector to test if it is a scalar multiple of {@code this}.
     * @return {@code true} if {@code this} and {@code v} are scalar multiples.
     */
    public boolean isScalarMultiple(double epsilon, V3D_VectorDouble v) {
        if (equals(epsilon, v)) {
            return true;
        } else {
            // Special cases
            boolean isZero = isZero(epsilon);
            boolean visZero = v.isZero(epsilon);
            if (isZero) {
                /**
                 * Can't multiply the zero vector by a scalar to get a non-zero
                 * vector.
                 */
                return visZero;
            }
            if (visZero) {
                /**
                 * Already tested that this is not equal to v, so the scalar is
                 * zero.
                 */
                return true;
            }
            /**
             * General case: A little complicated as there is a need to deal
             * with zero vector components and cases where the vectors point in
             * different directions.
             */
            if (Math_Double.equals(Math.abs(v.dx), Math.abs(dx), epsilon)) {
                // |dx| = |v.dx|
                if (Math_Double.equals(dx, 0d, epsilon)) {
                    // dx = v.dx = 0d
                    if (Math_Double.equals(Math.abs(v.dy), Math.abs(dy), epsilon)) {
                        if (Math_Double.equals(v.dy, 0d, epsilon)) {
                            return true;
                        } else {
                            if (Math_Double.equals(Math.abs(v.dz), Math.abs(dz), epsilon)) {
//                                    if (Math_Double.equals(v.dz, 0d, epsilon)) { 
//                                        // This should not happen as it is already tested for. Commented code left for clarity.
//                                        return true;
//                                    } else {
                                double scalar = v.dy / dy;
                                return Math_Double.equals(v.dz, dz * scalar, epsilon);
//                                    }
                            } else {
                                return false;
                            }
                        }
                    } else {
                        if (Math_Double.equals(v.dy, 0d, epsilon)) {
                            return Math_Double.equals(v.dz, 0d, epsilon);
                        } else {
                            if (Math_Double.equals(Math.abs(v.dz), Math.abs(dz), epsilon)) {
                                if (Math_Double.equals(v.dz, 0d, epsilon)) {
                                    return true;
                                } else {
                                    double scalar = v.dy / dy;
                                    return Math_Double.equals(v.dz, dz * scalar, epsilon);
                                }
                            } else {
                                double scalar = v.dy / dy;
                                return Math_Double.equals(v.dz, dz * scalar, epsilon);
                            }
                        }
                    }
                } else {
                    // |dx| = |v.dx| != 0d
                    if (Math_Double.equals(Math.abs(v.dy), Math.abs(dy), epsilon)) {
                        if (Math_Double.equals(v.dy, 0d, epsilon)) {
                            if (Math_Double.equals(Math.abs(v.dz), Math.abs(dz), epsilon)) {
                                if (Math_Double.equals(v.dz, 0d, epsilon)) {
                                    return true;
                                } else {
                                    double scalar = v.dx / dx;
                                    return Math_Double.equals(v.dz, dz * scalar, epsilon);
                                }
                            } else {
                                double scalar = v.dx / dx;
                                return Math_Double.equals(v.dz, dz * scalar, epsilon);
                            }
                        } else {
                            double scalar = v.dx / dx;
                            if (Math_Double.equals(v.dy, dy * scalar, epsilon)) {
                                if (Math_Double.equals(Math.abs(v.dz), Math.abs(dz), epsilon)) {
                                    if (Math_Double.equals(v.dz, 0d, epsilon)) {
                                        return true;
                                    } else {
                                        return Math_Double.equals(v.dz, dz * scalar, epsilon);
                                    }
                                } else {
                                    return Math_Double.equals(v.dz, dz * scalar, epsilon);
                                }
                            } else {
                                return false;
                            }
                        }
                    } else {
                        // |dx| = |v.dx| != 0d, |dy| != |v.dy|
                        double scalar = v.dx / dx;
                        if (Math_Double.equals(v.dy, dy * scalar, epsilon)) {
                            return Math_Double.equals(v.dz, dz * scalar, epsilon);
                        } else {
                            return false;
                        }
                    }
                }
            } else {
                // |dx| != |v.dx|
                if (Math_Double.equals(dx, 0d, epsilon)) {
                    return isZero;
                } else {
                    double scalar = v.dx / dx;
                    if (Math_Double.equals(v.dy, dy * scalar, epsilon)) {
                        return Math_Double.equals(v.dz, dz * scalar, epsilon);
                    } else {
                        return false;
                    }
                }
            }
        }
    }

    /**
     * Compute and return the angle (in radians) between {@code #this} and
     * {@code v}. The algorithm is to:
     * <ol>
     * <li>Find the dot product of the vectors.</li>
     * <li>Divide the dot product with the magnitude of the first vector.</li>
     * <li>the second vector.</li>
     * </ol>
     *
     * @param v The vector to find the angle between.
     * @return The angle in radians between {@code this} and {@code v}.
     */
    public double getAngle(V3D_VectorDouble v) {
        double dp = getDotProduct(v);
        double mag = getMagnitude();
        double vmag = v.getMagnitude();
        return Math.acos(dp / (mag * vmag));
    }

    /**
     * Calculate and return {@code #this} rotated using the parameters. (see
     * Doug (https://math.stackexchange.com/users/102399/doug), How do you
     * rotate a vector by a unit quaternion?, URL (version: 2019-06-12):
     * https://math.stackexchange.com/q/535223)
     *
     * @param uv The rotation unit vector.
     * @param theta The angle of rotation.
     * @return The vector which is {@code #this} rotated using the parameters.
     */
    public V3D_VectorDouble rotate(V3D_VectorDouble uv, double theta) {
        theta = V3D_AngleDouble.normalise(theta);
        if (theta == 0d) {
            return new V3D_VectorDouble(this);
        } else {
            double thetaDiv2 = theta / 2.0d;
            double sinThetaDiv2 = Math.sin(thetaDiv2);
            double w = Math.cos(thetaDiv2);
            double x = sinThetaDiv2 * uv.dx;
            double y = sinThetaDiv2 * uv.dy;
            double z = sinThetaDiv2 * uv.dz;
            Math_Quaternion_Double r = new Math_Quaternion_Double(w, x, y, z);
            // R'=rR
            Math_Quaternion_Double rR = new Math_Quaternion_Double(w, -x, -y, -z);
            Math_Quaternion_Double p = new Math_Quaternion_Double(0, dx, dy, dz);
            //Math_Quaternion_Double p = new Math_Quaternion_Double(0, x, y, z);
            // P'=pP
            Math_Quaternion_Double pP = r.multiply(p).multiply(rR);
            return new V3D_VectorDouble(pP.x, pP.y, pP.z);
        }
    }

    /**
     * Calculate and return the
     * <A href="https://en.wikipedia.org/wiki/Cross_product">cross product</A>.
     * Treat this as the first vector and {@code v} as the second vector. The
     * resulting vector is in the direction given by the right hand rule.
     *
     * @param v The other vector to compose the cross product from.
     * @return The cross product as a vector.
     */
    public V3D_VectorDouble getCrossProduct(V3D_VectorDouble v) {
        return new V3D_VectorDouble(
                dy * v.dz - dz * v.dy,
                dz * v.dx - dx * v.dz,
                dx * v.dy - dy * v.dx);
    }

    /**
     * Scales by {@link #m} to give a unit vector with length 1. Six further
     * orders of magnitude are used to produce the result.
     *
     * @return this scaled by {@link #m}.
     */
    public V3D_VectorDouble getUnitVector() {
        return divide(getMagnitude());
    }

    /**
     * The unit vector direction is given as being towards the point.
     *
     * @param pt The point controlling the direction of the unit vector.
     * @return this scaled by {@link #m}.
     */
    public V3D_VectorDouble getUnitVector(V3D_PointDouble pt) {
        double direction = getDotProduct(pt.getVector()) / getDotProduct(this);
        V3D_VectorDouble r = getUnitVector();
        if (direction < 0) {
            r = r.reverse();
        }
        return r;
    }

    /**
     * @return The direction of the vector:
     * <Table>
     * <caption>Directions</caption>
     * <thead>
     * <tr><td>ID</td><td>Description (P is positive, N is Negative)</td></tr>
     * </thead>
     * <tbody>
     * <tr><td>0</td><td>0dx, 0dy, 0dz</td></tr>
     * <tr><td>1</td><td>Pdx, Pdy, Pdz</td></tr>
     * <tr><td>2</td><td>Pdx, Pdy, Ndz</td></tr>
     * <tr><td>3</td><td>Pdx, Ndy, Pdz</td></tr>
     * <tr><td>4</td><td>Pdx, Ndy, Ndz</td></tr>
     * <tr><td>5</td><td>Ndx, Pdy, Pdz</td></tr>
     * <tr><td>6</td><td>Ndx, Pdy, Ndz</td></tr>
     * <tr><td>7</td><td>Ndx, Ndy, Pdz</td></tr>
     * <tr><td>8</td><td>Ndx, Ndy, Ndz</td></tr>
     * </tbody>
     * </Table>
     */
    public int getDirection() {
        if (dx >= 0d) {
            if (dy >= 0d) {
                if (dz >= 0d) {
                    if (isZero()) {
                        return 0;
                    } else {
                        return 1;
                    }
                } else {
                    return 2;
                }
            } else {
                if (dz >= 0d) {
                    return 3;
                } else {
                    return 4;
                }
            }
        } else {
            if (dy >= 0d) {
                if (dz >= 0d) {
                    return 5;
                } else {
                    return 6;
                }
            } else {
                if (dz >= 0d) {
                    return 7;
                } else {
                    return 8;
                }
            }
        }
    }
}
