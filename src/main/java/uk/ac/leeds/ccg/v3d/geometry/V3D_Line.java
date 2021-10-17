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
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Objects;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.math.matrices.Math_Matrix_BR;
import uk.ac.leeds.ccg.v3d.geometrics.V3D_Geometrics;

/**
 * 3D representation of an infinite length line. The line passes through the
 * point {@link #p} and is travelling in the direction {@link #v} through point
 * {@link #q}. The "*" denotes a point in 3D and the line is shown with a line
 * of "e" symbols in the following depiction: {@code
 *                                       z                e
 *                          y           -                e
 *                          +          /                * p=<x0,y0,z0>
 *                          |         /                e
 *                          |        /                e
 *                          |    z0-/                e
 *                          |      /                e
 *                          |     /               e
 *                          |    /               e
 *                          |   /               e
 *                       y0-|  /               e
 *                          | /               e
 *                          |/         x1    e
 * x - ---------------------|-----------/---e---/---- + x
 *                         /|              e   x0
 *                        / |-y1          e
 *                       /  |           e
 *                      /   |          e
 *                  z1-/    |         e
 *                    /     |        e
 *                   /      |       * q=<x1,y1,z1>
 *                  /       |      e
 *                 /        |     e
 *                +         |    e
 *               z          -   e
 *                          y
 * }
 * <ul>
 * <li>Vector Form
 * <ul>
 * <li>{@code (x,y,z) = (p.x,p.y,p.z) + t(v.getDX(),v.getDY(),v.getDZ())}</li>
 * </ul>
 * <li>Parametric Form (where t describes a particular point on the line)
 * <ul>
 * <li>{@code x = p.x + t(v.getDX())}</li>
 * <li>{@code y = p.y + t(v.getDY())}</li>
 * <li>{@code z = p.z + t(v.getDZ())}</li>
 * </ul>
 * <li>Symmetric Form (assume {@code v.getDX()}, {@code v.getDY()}, and {@code v.getDZ()} are
 * all nonzero)
 * <ul>
 * <li>{@code (x−p.x)/v.getDX() = (y−p.y)/v.getDY() = (z−p.z)/v.getDZ()}</li>
 * </ul></li>
 * </ul>
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Line extends V3D_Geometry {

    private static final long serialVersionUID = 1L;

    /**
     * A point defining the line.
     */
    public final V3D_Point p;

    /**
     * A point defining the line.
     */
    public final V3D_Point q;

    /**
     * The direction vector from {@link #p} in the direction of {@link #q}.
     */
    public final V3D_Vector v;

    /**
     * {@code p} should not be equal to {@code q}. If unsure use
     * {@link #V3D_Line(V3D_Point, V3D_Point, boolean)}.
     *
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     * @param oom Arbitrary, but used to initialise {@link #v}.
     */
    public V3D_Line(V3D_Point p, V3D_Point q, int oom) {
        this.p = p;
        this.q = q;
        v = new V3D_Vector(q.x.subtract(p.x), q.y.subtract(p.y),
                q.z.subtract(p.z), oom);
    }

    /**
     * {@code p} should not be equal to {@code q}. If unsure use
     * {@link #V3D_Line(V3D_Point, V3D_Point, boolean)}.
     *
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     * @param check Ignored. It is here to distinguish with
     * {@link #V3D_Line(V3D_Point, V3D_Point)}.
     * @throws RuntimeException if {@code p.equals(q)}.
     */
    public V3D_Line(V3D_Point p, V3D_Point q, int oom, boolean check) {
        if (p.equals(q)) {
            throw new RuntimeException("Points " + p + " and " + q
                    + " are the same and so do not define a line.");
        }
        this.p = p;
        this.q = q;
        v = new V3D_Vector(q.x.subtract(p.x), q.y.subtract(p.y),
                q.z.subtract(p.z), oom);
    }

    /**
     * {@code v} should not be the zero vector {@code <0,0,0>}. If unsure use
     * {@link #V3D_Line(V3D_Point, V3D_Point, boolean)}.
     *
     * @param p What {@link #p} is set to.
     * @param v What {@link #v} is set to.
     * @throws RuntimeException if {@code v.isZeroVector()}.
     */
    public V3D_Line(V3D_Point p, V3D_Vector v) {
        if (v.isZeroVector()) {
            throw new RuntimeException("Vector " + v + " is the zero vector "
                    + "and so cannot define a line.");
        }
        this.p = p;
        this.v = v;
        q = p.apply(v);
    }

    /**
     * Checks to ensure v is not the zero vector {@code <0,0,0>}.
     *
     * @param p What {@link #p} is set to.
     * @param v What {@link #v} is set to.
     * @param check Ignored. It is here to distinguish with
     * {@link #V3D_Line(V3D_Point, V3D_Vector)}.
     */
    public V3D_Line(V3D_Point p, V3D_Vector v, boolean check) {
        this.p = p;
        this.v = v;
        q = p.apply(v);
    }

    /**
     * Create a new instance from {@code l}
     *
     * @param l Line to create from.
     */
    public V3D_Line(V3D_Line l) {
        this.p = l.p;
        this.q = l.q;
        v = new V3D_Vector(q.x.subtract(p.x), q.y.subtract(p.y),
                q.z.subtract(p.z), l.v.oom);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(p=" + p.toString()
                + ", q=" + q.toString() + ", v=" + v.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V3D_Line) {
            return equals((V3D_Line) o, 0);
        }
        return false;
    }

    /**
     * @param l The line to test if it is the same as {@code this}.
     * @return {@code true} iff {@code l} is the same as {@code this}.
     */
    public boolean equals(V3D_Line l, int oom) {
        return isIntersectedBy(l.p, oom) && isIntersectedBy(l.q, oom);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.p);
        hash = 17 * hash + Objects.hashCode(this.v);
        return hash;
    }

    /**
     * @param pt A point to test for intersection.
     * @return {@code true} if p is on the line.
     */
    public boolean isIntersectedBy(V3D_Point pt, int oom) {
        V3D_Vector ppt = new V3D_Vector(pt.x.subtract(p.x),
                pt.y.subtract(p.y), pt.z.subtract(p.z), oom);
        V3D_Vector cp = v.getCrossProduct(ppt, oom);
        return cp.getDX().isZero() && cp.getDY().isZero() && cp.getDZ().isZero();
    }

    /**
     * @param l The line to test this with to see if they are parallel.
     * @return {@code true} If this and {@code l} are parallel.
     */
    public boolean isParallel(V3D_Line l) {
        return v.isScalarMultiple(l.v);
    }

    /**
     * This computes the intersection and tests if it is {@code null}
     *
     * @param l The line to test if it intersects with {@code this}.
     * @return {@code true} If {@code this} and {@code l} intersect.
     */
    public boolean isIntersectedBy(V3D_Line l, int oom) {
        return getIntersection(l, oom) != null;
    }

    /**
     * Intersects {@code this} with {@code l}. If they are equivalent then
     * return {@code this}.
     *
     * @param l The line to get the intersection with {@code this}.
     * @return The intersection between {@code this} and {@code l}.
     */
    public V3D_Geometry getIntersection(V3D_Line l, int oom) {
        // Special case of parallel lines.
        if (isParallel(l)) {
            if (p.isIntersectedBy(l, oom)) {
                // If lines are coincident return this.
                return this;
            } else {
                return null;
            }
        }
        V3D_Vector plp = new V3D_Vector(p, l.p, v.oom);
        V3D_Vector lqlp = new V3D_Vector(l.q, l.p, v.oom);
        if (lqlp.getMagnitudeSquared().compareTo(Math_BigRational.ZERO) == 0) {
            if (isIntersectedBy(l.p, oom)) {
                return l.p;
            }
        }
        V3D_Vector qp = new V3D_Vector(q, p, v.oom);
        if (qp.getMagnitudeSquared().compareTo(Math_BigRational.ZERO) == 0) {
            if (l.isIntersectedBy(p, oom)) {
                return p;
            }
        }
        Math_BigRational a = (plp.getDX().multiply(lqlp.getDX())).add(plp.getDY()
                .multiply(lqlp.getDY())).add(plp.getDZ().multiply(lqlp.getDZ()));
        Math_BigRational b = (lqlp.getDX().multiply(qp.getDX())).add(lqlp.getDY()
                .multiply(qp.getDY())).add(lqlp.getDZ().multiply(qp.getDZ()));
        Math_BigRational c = (plp.getDX().multiply(qp.getDX())).add(plp.getDY()
                .multiply(qp.getDY())).add(plp.getDZ().multiply(qp.getDZ()));
        Math_BigRational d = (lqlp.getDX().multiply(lqlp.getDX())).add(lqlp.getDY()
                .multiply(lqlp.getDY())).add(lqlp.getDZ().multiply(lqlp.getDZ()));
        Math_BigRational e = (qp.getDX().multiply(qp.getDX())).add(qp.getDY()
                .multiply(qp.getDY())).add(qp.getDZ().multiply(qp.getDZ()));
        Math_BigRational den = (e.multiply(d)).subtract(b.multiply(b));
        Math_BigRational num = (a.multiply(b)).subtract(c.multiply(d));
        if (den.compareTo(Math_BigRational.ZERO) == 0) {
            if (num.compareTo(Math_BigRational.ZERO) == 0) {
                Math_BigRational x;
                Math_BigRational y;
                Math_BigRational z;
                Math_BigRational lamda;
                Math_BigRational mu;
                if (v.getDX().compareTo(Math_BigRational.ZERO) == 0) {
                    x = p.x;
                    if (l.v.getDX().compareTo(Math_BigRational.ZERO) == 0) {
                        if (v.getDY().compareTo(Math_BigRational.ZERO) == 0) {
                            y = p.y;
                            if (l.v.getDY().compareTo(Math_BigRational.ZERO) == 0) {
                                z = p.z;
                            } else {
                                if (v.getDZ().compareTo(Math_BigRational.ZERO) == 0) {
                                    z = p.z;
                                } else {
                                    if (l.v.getDZ().compareTo(Math_BigRational.ZERO) == 0) {
                                        z = l.p.z;
                                    } else {
                                        mu = (p.y.subtract(l.p.y)).divide(l.v.getDY());
                                        z = l.p.z.add(l.v.getDZ().multiply(mu));
                                    }
                                }
                            }
                        } else {
                            if (l.v.getDY().compareTo(Math_BigRational.ZERO) == 0) {
                                y = l.p.y;
                                if (v.getDZ().compareTo(Math_BigRational.ZERO) == 0) {
                                    z = p.z;
                                } else {
                                    if (l.v.getDZ().compareTo(Math_BigRational.ZERO) == 0) {
                                        z = l.p.z;
                                    } else {
                                        lamda = (l.p.y.subtract(p.y)).divide(v.getDY());
                                        z = p.z.add(v.getDZ().multiply(lamda));
                                    }
                                }
                                //x = p.x;            
                                //p.x + v.getDX() * lamda = l.p.x + l.v.getDX() * mu
                                //p.y + v.getDY() * lamda = l.p.y + l.v.getDY() * mu
                                //p.z + v.getDZ() * lamda = l.p.z + l.v.getDZ() * mu

                            } else {
                                if (v.getDZ().compareTo(Math_BigRational.ZERO) == 0) {
                                    z = p.z;
                                    mu = (p.z.subtract(l.p.z)).divide(l.v.getDY());
                                    y = l.p.y.add(l.v.getDY().multiply(mu));
                                } else {
                                    if (l.v.getDZ().compareTo(Math_BigRational.ZERO) == 0) {
                                        z = l.p.z;
                                        lamda = (l.p.z.subtract(p.z)).divide(v.getDY());
                                        y = p.y.add(v.getDY().multiply(lamda));
                                    } else {
                                        // There are 2 ways to calculate lamda. One way should work! - If not try calculating mu.
//                                        mu = ((p.y.add(v.getDY().multiply(lamda))).subtract(l.p.y)).divide(l.v.getDY());
//                                        lamda = ((l.p.z.subtract(p.z)).divide(v.getDZ())).add(l.v.getDZ().multiply(mu));
//                                        lamda = ((l.p.z.subtract(p.z)).divide(v.getDZ())).add(l.v.getDZ().multiply(((p.y.add(v.getDY().multiply(lamda))).subtract(l.p.y)).divide(l.v.getDY())));
//                                        l = ((bz-az)/adz) + (bdz*(ady*(l-by)/bdy))
//                                        l = ((bz-az)/adz) + bdz*ady*l/bdy - bdz*ady*by/bdy
//                                        l - bdz*ady*l/bdy = ((bz-az)/adz) - bdz*ady*by/bdy
//                                        l (1 - bdz*ady/bdy) = ((bz-az)/adz) - bdz*ady*by/bdy
//                                        l = (((bz-az)/adz) - bdz*ady*by/bdy)/(1 - bdz*ady/bdy)
                                        Math_BigRational den2 = Math_BigRational.ONE.subtract(l.v.getDZ().multiply(v.getDY().divide(l.v.getDY())));
                                        if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                            lamda = (((l.p.z.subtract(p.z)).divide(v.getDZ())).subtract(l.v.getDZ().multiply(v.getDY().multiply(l.p.y.divide(l.v.getDY()))))).divide(den2);
                                            z = p.z.add(v.getDZ().multiply(lamda));
                                            y = p.y.add(v.getDY().multiply(lamda));
                                        } else {
                                            den2 = Math_BigRational.ONE.subtract(l.v.getDY().multiply(v.getDZ().divide(l.v.getDZ())));
                                            if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                                lamda = (((l.p.y.subtract(p.y)).divide(v.getDY())).subtract(l.v.getDY().multiply(v.getDZ().multiply(l.p.z.divide(l.v.getDZ()))))).divide(den2);
                                                z = p.z.add(v.getDZ().multiply(lamda));
                                                y = p.y.add(v.getDY().multiply(lamda));
                                            } else {
                                                // This should not happen!
                                                z = null;
                                                y = null;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        mu = (p.x.subtract(l.p.x)).divide(l.v.getDX());
                        if (v.getDY().compareTo(Math_BigRational.ZERO) == 0) {
                            if (l.v.getDY().compareTo(Math_BigRational.ZERO) == 0) {
                                y = p.y;
                                z = p.z;
                            } else {
                                if (v.getDZ().compareTo(Math_BigRational.ZERO) == 0) {
                                    y = l.p.y.add(l.v.getDY().multiply(mu));
                                } else {
                                    y = p.y.add(v.getDY().multiply(mu));
                                }
                                if (l.v.getDZ().compareTo(Math_BigRational.ZERO) == 0) {
                                    z = p.z;
                                } else {
                                    z = l.p.z.add(l.v.getDZ().multiply(mu));
                                }
                            }
                        } else {
                            lamda = ((l.p.y.add(l.v.getDY().multiply(mu)))
                                    .subtract(p.x)).divide(v.getDY());
                            if (v.getDZ().compareTo(Math_BigRational.ZERO) == 0) {
                                z = p.z;
                            } else {
                                z = p.z.add(v.getDZ().multiply(lamda));
                            }
                            if (l.v.getDY().compareTo(Math_BigRational.ZERO) == 0) {
                                y = p.y;
                            } else {
                                y = l.p.y.add(l.v.getDY().multiply(mu));
                            }
                        }
                    }
                } else {
                    if (l.v.getDX().compareTo(Math_BigRational.ZERO) == 0) {
                        lamda = l.p.x.subtract(p.x).divide(v.getDX());
                        x = l.p.x;
                        if (v.getDY().compareTo(Math_BigRational.ZERO) == 0) {
                            mu = (p.y.subtract(l.p.y)).divide(l.v.getDY());
                            y = p.y;
                            if (v.getDZ().compareTo(Math_BigRational.ZERO) == 0) {
                                z = p.z;
                            } else {
                                if (l.v.getDZ().compareTo(Math_BigRational.ZERO) == 0) {
                                    z = l.p.z;
                                } else {
                                    z = l.p.z.add(l.v.getDZ().multiply(mu));
                                }
                            }
                        } else {
                            if (v.getDY().compareTo(Math_BigRational.ZERO) == 0) {
                                y = p.y;
                                if (l.v.getDY().compareTo(Math_BigRational.ZERO) == 0) {
                                    if (v.getDZ().compareTo(Math_BigRational.ZERO) == 0) {
                                        z = p.z;
                                    } else {
                                        if (l.v.getDZ().compareTo(Math_BigRational.ZERO) == 0) {
                                            z = l.p.z;
                                        } else {
                                            mu = ((p.z.add(v.getDZ().multiply(lamda))).subtract(l.p.z)).divide(l.v.getDZ());
                                            z = l.p.z.add(l.v.getDZ().multiply(mu));
                                        }
                                    }
                                } else {
                                    if (v.getDZ().compareTo(Math_BigRational.ZERO) == 0) {
                                        z = p.z;
                                    } else {
                                        if (l.v.getDZ().compareTo(Math_BigRational.ZERO) == 0) {
                                            z = l.p.z;
                                        } else {
                                            mu = (p.z.subtract(l.p.z)).divide(l.v.getDZ());
                                            z = l.p.z.add(l.v.getDZ().multiply(mu));
                                        }
                                    }
                                }
                            } else {
                                if (l.v.getDY().compareTo(Math_BigRational.ZERO) == 0) {
                                    y = l.p.y;
                                    if (v.getDZ().compareTo(Math_BigRational.ZERO) == 0) {
                                        z = p.z;
                                    } else {
                                        if (l.v.getDZ().compareTo(Math_BigRational.ZERO) == 0) {
                                            z = l.p.z;
                                        } else {
                                            mu = ((p.z.add(v.getDZ().multiply(lamda))).subtract(l.p.z)).divide(l.v.getDZ());
                                            z = l.p.z.add(l.v.getDZ().multiply(mu));
                                        }
                                    }
                                } else {
                                    y = p.y.add(v.getDY().multiply(lamda));
                                    if (v.getDZ().compareTo(Math_BigRational.ZERO) == 0) {
                                        z = p.z;
                                    } else {
                                        if (l.v.getDZ().compareTo(Math_BigRational.ZERO) == 0) {
                                            z = l.p.z;
                                        } else {
                                            mu = ((p.z.add(v.getDZ().multiply(lamda))).subtract(l.p.z)).divide(l.v.getDZ());
                                            z = l.p.z.add(l.v.getDZ().multiply(mu));
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        // v.getDX() > 0 && l.v.getDX() > 0
                        if (v.getDY().compareTo(Math_BigRational.ZERO) == 0) {
                            y = p.y;
                            if (l.v.getDY().compareTo(Math_BigRational.ZERO) == 0) {
                                if (v.getDZ().compareTo(Math_BigRational.ZERO) == 0) {
                                    z = p.z;
                                    x = p.x;
                                } else {
                                    if (l.v.getDZ().compareTo(Math_BigRational.ZERO) == 0) {
                                        z = l.p.z;
                                        lamda = (l.p.z.subtract(p.z)).divide(v.getDZ());
                                        x = p.x.add(v.getDX().multiply(lamda));
                                    } else {
                                        // There are 2 ways to calculate lamda. One way should work! - If not try calculating mu.
//                                        mu = ((p.x.add(v.getDX().multiply(lamda))).subtract(l.p.x)).divide(l.v.getDX());
//                                        lamda = ((l.p.z.subtract(p.z)).divide(v.getDZ())).add(l.v.getDZ().multiply(mu));
//                                        lamda = ((l.p.z.subtract(p.z)).divide(v.getDZ())).add(l.v.getDZ().multiply(((p.x.add(v.getDX().multiply(lamda))).subtract(l.p.x)).divide(l.v.getDX())));
//                                        l = ((bz-az)/adz) + (bdz*(adx*(l-bx)/bdx))
//                                        l = ((bz-az)/adz) + bdz*adx*l/bdx - bdz*adx*bx/bdx
//                                        l - bdz*adx*l/bdx = ((bz-az)/adz) - bdz*adx*bx/bdx
//                                        l (1 - bdz*adx/bdx) = ((bz-az)/adz) - bdz*adx*bx/bdx
//                                        l = (((bz-az)/adz) - bdz*adx*bx/bdx)/(1 - bdz*adx/bdx)
                                        Math_BigRational den2 = Math_BigRational.ONE.subtract(l.v.getDZ().multiply(v.getDX().divide(l.v.getDX())));
                                        if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                            lamda = (((l.p.z.subtract(p.z)).divide(v.getDZ())).subtract(l.v.getDZ().multiply(v.getDX().multiply(l.p.x.divide(l.v.getDX()))))).divide(den2);
                                            z = p.z.add(v.getDZ().multiply(lamda));
                                            x = p.x.add(v.getDX().multiply(lamda));
                                        } else {
                                            den2 = Math_BigRational.ONE.subtract(l.v.getDX().multiply(v.getDZ().divide(l.v.getDZ())));
                                            if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                                lamda = (((l.p.x.subtract(p.x)).divide(v.getDX())).subtract(l.v.getDX().multiply(v.getDZ().multiply(l.p.z.divide(l.v.getDZ()))))).divide(den2);
                                                z = p.z.add(v.getDZ().multiply(lamda));
                                                x = p.x.add(v.getDX().multiply(lamda));
                                            } else {
                                                // This should not happen!
                                                z = null;
                                                x = null;
                                            }
                                        }
                                    }
                                }
                            } else {
                                mu = p.y.subtract(l.p.y).divide(l.v.getDY());
                                x = l.p.x.add(l.v.getDX().multiply(mu));
                                z = l.p.z.add(l.v.getDZ().multiply(mu));
                            }
                        } else {
                            // v.getDX() > 0 && l.v.getDX() > 0 && v.getDY() > 0
                            if (v.getDZ().compareTo(Math_BigRational.ZERO) == 0) {
                                z = p.z;
                                if (l.v.getDZ().compareTo(Math_BigRational.ZERO) == 0) {
                                    // There are 2 ways to calculate lamda. One way should work! - If not try calculating mu.
//                                    mu = ((p.x.add(v.getDX().multiply(lamda))).subtract(l.p.x)).divide(l.v.getDX());
//                                    lamda = ((l.p.y.subtract(p.y)).divide(v.getDY())).add(l.v.getDY().multiply(mu));
//                                    lamda = ((l.p.y.subtract(p.y)).divide(v.getDY())).add(l.v.getDY().multiply(((p.x.add(v.getDX().multiply(lamda))).subtract(l.p.x)).divide(l.v.getDX())));
//                                    l = ((by - ay) / ady) + (bdy * (adx * (l - bx) / bdx))
//                                    l = ((by - ay) / ady) + bdy * adx * l / bdx - bdy * adx * bx / bdx
//                                    l - bdy * adx * l / bdx = ((by - ay) / ady) - bdy * adx * bx / bdx
//                                    l(1 - bdy * adx / bdx) = ((by - ay) / ady) - bdy * adx * bx / bdx
//                                    l = (((by-ay)/ady) - bdy*adx*bx/bdx)/(1 - bdy*adx/bdx)
                                    Math_BigRational den2 = Math_BigRational.ONE.subtract(l.v.getDY().multiply(v.getDX().divide(l.v.getDX())));
                                    if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                        lamda = (((l.p.y.subtract(p.y)).divide(v.getDY())).subtract(l.v.getDY().multiply(v.getDX().multiply(l.p.x.divide(l.v.getDX()))))).divide(den2);
                                        y = p.y.add(v.getDY().multiply(lamda));
                                        x = p.x.add(v.getDX().multiply(lamda));
                                    } else {
                                        den2 = Math_BigRational.ONE.subtract(l.v.getDX().multiply(v.getDY().divide(l.v.getDY())));
                                        if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                            lamda = (((l.p.x.subtract(p.x)).divide(v.getDX())).subtract(l.v.getDX().multiply(v.getDY().multiply(l.p.y.divide(l.v.getDY()))))).divide(den2);
                                            y = p.y.add(v.getDY().multiply(lamda));
                                            x = p.x.add(v.getDX().multiply(lamda));
                                        } else {
                                            // This should not happen!
                                            y = null;
                                            x = null;
                                        }
                                    }
                                } else {
                                    mu = (p.z.subtract(l.p.z)).divide(l.v.getDZ());
                                    y = l.p.y.add(l.v.getDY().multiply(mu));
                                    x = l.p.x.add(l.v.getDX().multiply(mu));
                                }
                            } else {
                                if (l.v.getDZ().compareTo(Math_BigRational.ZERO) == 0) {
                                    z = l.p.z;
                                    lamda = (l.p.z.subtract(p.z)).divide(v.getDZ());
                                    y = p.y.add(v.getDY().multiply(lamda));
                                    x = p.x.add(v.getDX().multiply(lamda));
                                } else {
                                    // There are 6 ways to calculate lamda. One way should work! - If not try calculating mu.
                                    Math_BigRational den2 = Math_BigRational.ONE.subtract(l.v.getDY().multiply(v.getDX().divide(l.v.getDX())));
                                    if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                        lamda = (((l.p.y.subtract(p.y)).divide(v.getDY())).subtract(l.v.getDY().multiply(v.getDX().multiply(l.p.x.divide(l.v.getDX()))))).divide(den2);
                                        x = p.x.add(v.getDX().multiply(lamda));
                                        y = p.y.add(v.getDY().multiply(lamda));
                                        z = p.z.add(v.getDZ().multiply(lamda));
//                                        x = q.x.add(v.getDX().multiply(lamda));
//                                        y = q.y.add(v.getDY().multiply(lamda));
//                                        z = q.z.add(v.getDZ().multiply(lamda));
                                    } else {
                                        den2 = Math_BigRational.ONE.subtract(l.v.getDY().multiply(v.getDZ().divide(l.v.getDZ())));
                                        if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                            lamda = (((l.p.y.subtract(p.y)).divide(v.getDY())).subtract(l.v.getDY().multiply(v.getDZ().multiply(l.p.z.divide(l.v.getDZ()))))).divide(den2);
                                            x = p.x.add(v.getDX().multiply(lamda));
                                            y = p.y.add(v.getDY().multiply(lamda));
                                            z = p.z.add(v.getDZ().multiply(lamda));
                                        } else {
                                            den2 = Math_BigRational.ONE.subtract(l.v.getDZ().multiply(v.getDX().divide(l.v.getDX())));
                                            if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                                lamda = (((l.p.z.subtract(p.z)).divide(v.getDZ())).subtract(l.v.getDZ().multiply(v.getDX().multiply(l.p.x.divide(l.v.getDX()))))).divide(den2);
                                                x = p.x.add(v.getDX().multiply(lamda));
                                                y = p.y.add(v.getDY().multiply(lamda));
                                                z = p.z.add(v.getDZ().multiply(lamda));
                                            } else {
                                                den2 = Math_BigRational.ONE.subtract(l.v.getDZ().multiply(v.getDY().divide(l.v.getDY())));
                                                if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                                    lamda = (((l.p.z.subtract(p.z)).divide(v.getDZ())).subtract(l.v.getDZ().multiply(v.getDY().multiply(l.p.y.divide(l.v.getDY()))))).divide(den2);
                                                    x = p.x.add(v.getDX().multiply(lamda));
                                                    y = p.y.add(v.getDY().multiply(lamda));
                                                    z = p.z.add(v.getDZ().multiply(lamda));
                                                } else {
                                                    den2 = Math_BigRational.ONE.subtract(l.v.getDX().multiply(v.getDX().divide(l.v.getDY())));
                                                    if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                                        lamda = (((l.p.x.subtract(p.x)).divide(v.getDX())).subtract(l.v.getDX().multiply(v.getDY().multiply(l.p.y.divide(l.v.getDY()))))).divide(den2);
                                                        x = p.x.add(v.getDX().multiply(lamda));
                                                        y = p.y.add(v.getDY().multiply(lamda));
                                                        z = p.z.add(v.getDZ().multiply(lamda));
                                                    } else {
                                                        den2 = Math_BigRational.ONE.subtract(l.v.getDX().multiply(v.getDX().divide(l.v.getDZ())));
                                                        if (den2.compareTo(Math_BigRational.ZERO) != 0) {
                                                            lamda = (((l.p.x.subtract(p.x)).divide(v.getDX())).subtract(l.v.getDX().multiply(v.getDZ().multiply(l.p.z.divide(l.v.getDZ()))))).divide(den2);
                                                            x = p.x.add(v.getDX().multiply(lamda));
                                                            y = p.y.add(v.getDY().multiply(lamda));
                                                            z = p.z.add(v.getDZ().multiply(lamda));
                                                        } else {
                                                            // This should not happen!
                                                            x = null;
                                                            y = null;
                                                            z = null;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                //p.x + v.getDX() * lamda = l.p.x + l.v.getDX() * mu
                //p.y + v.getDY() * lamda = l.p.y + l.v.getDY() * mu
                //p.z + v.getDZ() * lamda = l.p.z + l.v.getDZ() * mu
                return new V3D_Point(x, y, z);
            }
            return null;
        }
        Math_BigRational mua = num.divide(den);
        Math_BigRational mub = (a.add(b.multiply(mua))).divide(d).negate();
        V3D_Point pi = new V3D_Point(
                (p.x.add(mua.multiply(qp.getDX()))),
                (p.y.add(mua.multiply(qp.getDY()))),
                (p.z.add(mua.multiply(qp.getDZ()))));
        // If point p is on both lines then return this as the intersection.
        if (isIntersectedBy(pi, oom) && l.isIntersectedBy(pi, oom)) {
            return pi;
        }
        V3D_Point qi = new V3D_Point(
                (l.p.x.add(mub.multiply(lqlp.getDX()))),
                (l.p.y.add(mub.multiply(lqlp.getDY()))),
                (l.p.z.add(mub.multiply(lqlp.getDZ()))));
        // If point q is on both lines then return this as the intersection.
        if (isIntersectedBy(qi, oom) && l.isIntersectedBy(qi, oom)) {
            return qi;
        }
        /**
         * The only time when pi and qi should be different is when the lines do
         * not intersect. In this case pi and qi are meant to be the end points
         * of the shortest line between the two lines.
         */
        if (pi.equals(qi)) {
            return pi;
        } else {
            return null;
        }
        //return new V3D_Line(pi, qi);
    }

    /**
     * @param r The ray to test if it intersects with {@code this}.
     * @return {@code true} If {@code this} and {@code r} intersect.
     */
    public boolean isIntersectedBy(V3D_Ray r, int oom) {
        return r.isIntersectedBy(this, oom);
    }

    /**
     * Intersects {@code this} with {@code r}.
     *
     * @param r The ray to get intersection with {@code this}.
     * @return The intersection between {@code this} and {@code r}.
     */
    public V3D_Geometry getIntersection(V3D_Ray r, int oom) {
        return r.getIntersection(this, oom);
    }

    /**
     * @param pt A point for which the shortest line segment to this is
     * returned.
     * @return The line segment having the shortest distance between {@code pt}
     * and {@code this}.
     */
    public V3D_LineSegment getLineOfIntersection(V3D_Point pt, int oom) {
        if (isIntersectedBy(pt, oom)) {
            return new V3D_LineSegment(pt, pt, v.oom);
        }
        return new V3D_LineSegment(pt, getPointOfIntersection(pt, oom), oom);
    }

    /**
     * Adapted from:
     * <ahref="https://math.stackexchange.com/questions/1521128/given-a-line-and-a-point-in-3d-how-to-find-the-closest-point-on-the-line">https://math.stackexchange.com/questions/1521128/given-a-line-and-a-point-in-3d-how-to-find-the-closest-point-on-the-line</a>
     * @param pt The point projected onto this.
     * @return A point on {@code this} which is the shortest distance from
     * {@code pt}.
     */
    public V3D_Point getPointOfIntersection(V3D_Point pt, int oom) {
        if (this.isIntersectedBy(pt, oom)) {
            return pt;
        }
        V3D_Vector a = new V3D_Vector(pt, p, oom);
        //V3D_Vector a = new V3D_Vector(p, pt);
        Math_BigRational adb = a.getDotProduct(v);
        Math_BigRational vdv = v.getDotProduct(v);
        return p.apply(v.multiply(adb.divide(vdv)).reverse());
    }

    /**
     * Get the line of intersection (the shortest line) between two lines. The
     * two lines must not intersect or be parallel for this to work. Part
     * adapted from
     * <a href="http://paulbourke.net/geometry/pointlineplane/">http://paulbourke.net/geometry/pointlineplane/</a>.
     *
     * @param l The line to get the line of intersection with.
     * @return The line of intersection between {@code this} and {@code l}.
     */
    public V3D_Geometry getLineOfIntersection(V3D_Line l) {
        if (isParallel(l)) {
            return null;
        }
        V3D_Vector A = new V3D_Vector(p, l.p, v.oom);
        V3D_Vector B = v.reverse();
        V3D_Vector C = l.v.reverse();
        
        Math_BigRational AdB = A.getDotProduct(B);
        Math_BigRational AdC = A.getDotProduct(C);
        Math_BigRational CdB = C.getDotProduct(B);
        Math_BigRational BdB = B.getDotProduct(B);
        Math_BigRational CdC = C.getDotProduct(C);
        
        Math_BigRational ma = (AdC.multiply(CdB)).subtract(AdB.multiply(CdC))
                .divide((BdB.multiply(CdC)).subtract(CdB.multiply(CdB)));
        Math_BigRational mb = ((ma.multiply(CdB)).add(AdC)).divide(CdC);
        
        V3D_Point tpi = p.apply(B.multiply(ma));
        
        V3D_Point lpi = l.p.apply(C.multiply(mb.negate()));
        
        return new V3D_LineSegment(tpi, lpi, v.oom);
        
        
//        // p13
//        V3D_Vector plp = new V3D_Vector(p, l.p);
//        // p43
//        V3D_Vector lqlp = l.v.reverse();//new V3D_Vector(l.q, l.p);
//        if (lqlp.getMagnitudeSquared().compareTo(Math_BigRational.ZERO) == 0) {
//            return null;
//        }
//        // p21
//        V3D_Vector qp = v.reverse();//new V3D_Vector(q, p);
//        if (qp.getMagnitudeSquared().compareTo(Math_BigRational.ZERO) == 0) {
//            return null;
//        }
//        // p1343
//        Math_BigRational a = (plp.getDX().multiply(lqlp.getDX())).add(plp.getDY()
//                .multiply(lqlp.getDY())).add(plp.getDZ().multiply(lqlp.getDZ()));
//        Math_BigRational b = (lqlp.getDX().multiply(qp.getDX())).add(lqlp.getDY()
//                .multiply(qp.getDY())).add(lqlp.getDZ().multiply(qp.getDZ()));
//        Math_BigRational c = (plp.getDX().multiply(qp.getDX())).add(plp.getDY()
//                .multiply(qp.getDY())).add(plp.getDZ().multiply(qp.getDZ()));
//        Math_BigRational d = (lqlp.getDX().multiply(lqlp.getDX())).add(lqlp.getDY()
//                .multiply(lqlp.getDY())).add(lqlp.getDZ().multiply(lqlp.getDZ()));
//        Math_BigRational e = (qp.getDX().multiply(qp.getDX())).add(qp.getDY()
//                .multiply(qp.getDY())).add(qp.getDZ().multiply(qp.getDZ()));
//        Math_BigRational den = (e.multiply(d)).subtract(b.multiply(b));
//        if (den.compareTo(Math_BigRational.ZERO) == 0) {
//            return null;
//        }
//        Math_BigRational num = (a.multiply(b)).subtract(c.multiply(d));
//        // dmnop = (xm - xn)(xo - xp) + (ym - yn)(yo - yp) + (zm - zn)(zo - zp)
//        // mua = ( d1343 d4321 - d1321 d4343 ) / ( d2121 d4343 - d4321 d4321 )
//        Math_BigRational mua = num.divide(den);
//        // mub = ( d1343 + mua d4321 ) / d4343
//        Math_BigRational mub = (a.add(b.multiply(mua))).divide(d);
//        V3D_Point pi = new V3D_Point(
//                (p.x.add(mua.multiply(qp.getDX()))),
//                (p.y.add(mua.multiply(qp.getDY()))),
//                (p.z.add(mua.multiply(qp.getDZ()))));
//        V3D_Point qi = new V3D_Point(
//                (l.p.x.add(mub.multiply(lqlp.getDX()))),
//                (l.p.y.add(mub.multiply(lqlp.getDY()))),
//                (l.p.z.add(mub.multiply(lqlp.getDZ()))));
//        if (pi.equals(qi)) {
//            return pi;
//        }
//        return new V3D_LineSegment(pi, qi);
    }

    /**
     * @param v The vector to apply.
     * @return a new line.
     */
    @Override
    public V3D_Line apply(V3D_Vector v) {
        return new V3D_Line(p.apply(v), q.apply(v), Math.min(v.oom, this.v.oom));
    }

    /**
     * <a href="https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line">https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line</a>
     * Weisstein, Eric W. "Point-Line Distance--3-Dimensional." From MathWorld--A Wolfram Web Resource. https://mathworld.wolfram.com/Point-LineDistance3-Dimensional.html
     *
     * @param p A point for which the minimum distance from {@code this} is
     * returned.
     *
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The minimum distance between this and {@code p}.
     */
    @Override
    public BigDecimal getDistance(V3D_Point p, int oom) {
        if (isIntersectedBy(p, oom)) {
            return BigDecimal.ZERO;
        }
        V3D_Vector pp = new V3D_Vector(p, this.p, oom);
        V3D_Vector qp = new V3D_Vector(p, this.q, oom);
        Math_BigRationalSqrt num = (pp.getCrossProduct(qp, oom)).getMagnitude();
        Math_BigRationalSqrt den = v.getMagnitude();
        Math_BigRational res = num.divide(den).getSqrt(oom);
        int precision = Math_BigDecimal.getOrderOfMagnitudeOfMostSignificantDigit(res.integerPart().toBigDecimal()) - oom;
        MathContext mc = new MathContext(precision);
        return Math_BigDecimal.round(res.toBigDecimal(mc), oom);
//        /**
//         * Calculate the direction vector of the line connecting the closest
//         * points by computing the cross product.
//         */
//        V3D_Vector pv = new V3D_Vector(this.p, p, oom);
//        V3D_Vector cp = pv.getCrossProduct(v);
//        
//        Math_BigRational pvm2 = pv.getMagnitudeSquared();
//        Math_BigRational cpm2 = cp.getMagnitudeSquared();
//        if (pvm2.compareTo(Math_BigRational.ZERO) == 0) {
//            if (cpm2.compareTo(Math_BigRational.ZERO) == 0) {
//                return BigDecimal.ONE;
//            }
//        }
//        Math_BigRationalSqrt pvm = new Math_BigRationalSqrt(pvm2, oom);
//        Math_BigRationalSqrt cpm = new Math_BigRationalSqrt(cpm2, oom);
//        BigDecimal result = cpm.divide(pvm).toBigDecimal(oom);
////        return cpm.divide(pvm).toBigDecimal(oom);
////
////        return getDistance(p).toBigDecimal(oom);
////        V3D_Vector pv = new V3D_Vector(this.p, p, v.oom);
//        V3D_Vector vu = v.getUnitVector(oom - 2);
//        return p.getDistance(new V3D_Point(vu.multiply(pv.getDotProduct(vu))
//                .add(new V3D_Vector(this.p, v.oom))), oom);
    }

//    /**
//     * https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line
//     *
//     * @param p A point for which the minimum distance from {@code this} is
//     * returned.
//     * @return The minimum distance between this and {@code p}.
//     */
//    public Math_BigRationalSqrt getDistance(V3D_Point p) {
//        /**
//         * Calculate the direction vector of the line connecting the closest
//         * points by computing the cross product.
//         */
//        V3D_Vector pv = new V3D_Vector(this.p, p);
//        V3D_Vector cp = pv.getCrossProduct(v);
////        Math_BigRational pvm2 = pv.getMagnitudeSquared();
////        Math_BigRational cpm2 = cp.getMagnitudeSquared();
////        if (pvm2.compareTo(Math_BigRational.ZERO) == 0) {
////            if (cpm2.compareTo(Math_BigRational.ZERO) == 0) {
////                return BigDecimal.ONE;
////            }
////        }
////        Math_BigRationalSqrt pvm = new Math_BigRationalSqrt(pvm2);
////        Math_BigRationalSqrt cpm = new Math_BigRationalSqrt(cpm2);
////        return cpm.divide(pvm).toBigDecimal(oom);
//        /**
//         * Calculate the delta from {@link #p} and l.p
//         */
//        V3D_Vector delta = new V3D_Vector(this.p).subtract(pv);
//        Math_BigRationalSqrt dp = new Math_BigRationalSqrt(cp.getDotProduct(delta));
//        if (dp.compareTo(cp.m) == 0) {
//            return Math_BigRationalSqrt.ONE; // Prevent a divide by zero if both are zero.
//        }
//        return dp.divide(cp.m);
//    }
    /**
     * https://en.wikipedia.org/wiki/Skew_lines#Nearest_points
     * https://en.wikipedia.org/wiki/Distance_between_two_parallel_lines
     *
     * @param l A line for which the minimum distance from {@code this} is
     * returned.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The minimum distance between this and {@code l}.
     */
    @Override
    public BigDecimal getDistance(V3D_Line l, int oom) {
        if (isParallel(l)) {
            //q.getDistance(l, scale, rm);
            return p.getDistance(l, oom);
            //return p.getDistance(l, oom);
//            V3D_Vector v2 = new V3D_Vector(l.p, q);
//            return v2.subtract(v.multiply((v2.getDotProduct(v))
//                    .divide(v.getMagnitudeSquared()))).getMagnitude(scale, rm);
        } else {
            /**
             * Calculate the direction vector of the line connecting the closest
             * points by computing the cross product.
             */
            V3D_Vector cp = v.getCrossProduct(l.v, oom);
            /**
             * Calculate the delta from {@link #p} and l.p
             */
            V3D_Vector delta = new V3D_Vector(p, v.oom).subtract(
                    new V3D_Vector(l.p, v.oom));
            //Math_BigRational m = Math_BigRational.valueOf(cp.getMagnitude(oom - 2));
            Math_BigRationalSqrt m = cp.getMagnitude();
            // d = cp.(delta)/m
            Math_BigRational dp = cp.getDotProduct(delta);
            // m should only be zero if the lines are parallel.
            Math_BigRational d = dp.divide(m.getX());
            return Math_BigDecimal.round(d.toBigDecimal(), oom);
        }
    }

    /**
     * @param r A ray for which the minimum distance from {@code this} is
     * returned.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The minimum distance between {@code this} and {@code r}.
     */
    public BigDecimal getDistance(V3D_Ray r, int oom) {
    //public Math_BigRational getDistance(V3D_Ray r) {
        if (isParallel(r)) {
            return p.getDistance(new V3D_Line(r), oom);
        } else {
            if (isIntersectedBy(r, oom)) {
                return BigDecimal.ZERO;
            } else {
                V3D_Line rl = new V3D_Line(r);
                if (isIntersectedBy(rl, oom)) {
                    getLineOfIntersection(r.p, oom).getLength().toBigDecimal(oom);
                }                
                V3D_LineSegment li = (V3D_LineSegment) getLineOfIntersection(r);
                if (li == null) {
                    li = getLineOfIntersection(r.p, oom);
                }
                if (r.isIntersectedBy(li.q, oom)) {
                    return li.getLength().toBigDecimal(oom);
                }
                return r.p.getDistance(this, oom);
            }
        }
    }

    /**
     * @return {@code true} iff this is parallel to the plane defined by x=0.
     */
    public boolean isParallelToX0() {
        return v.getDX().compareTo(Math_BigRational.ZERO) == 0;
    }

    /**
     * @return {@code true} iff this is parallel to the plane defined by y=0.
     */
    public boolean isParallelToY0() {
        return v.getDY().compareTo(Math_BigRational.ZERO) == 0;
    }

    /**
     * @return {@code true} iff this is parallel to the plane defined by z=0.
     */
    public boolean isParallelToZ0() {
        return v.getDZ().compareTo(Math_BigRational.ZERO) == 0;
    }

    @Override
    public boolean isEnvelopeIntersectedBy(V3D_Line l, int oom) {
        if (this.isIntersectedBy(l, oom)) {
            return true;
        }
        if (this.isParallelToX0()) {
            if (this.isParallelToY0()) {
                return false;
            } else if (this.isParallelToZ0()) {
                return false;
            } else {
                return !this.isParallel(l);
            }
        } else {
            if (this.isParallelToY0()) {
                if (this.isParallelToZ0()) {
                    return false;
                } else {
                    return !this.isParallel(l);
                }
            } else {
                if (this.isParallelToZ0()) {
                    return !this.isParallel(l);
                } else {
                    return true;
                }
            }
        }
    }

    /**
     * @return The points that define the plan as a matrix.
     */
    public Math_Matrix_BR getAsMatrix() {
        Math_BigRational[][] m = new Math_BigRational[2][3];
        m[0][0] = p.x;
        m[0][1] = p.y;
        m[0][2] = p.z;
        m[1][0] = q.x;
        m[1][1] = q.y;
        m[1][2] = q.z;
        return new Math_Matrix_BR(m);
    }

    /**
     * An implementation of this exists:
     * https://www.geometrictools.com/Documentation/DistanceLine3Line3.pdf
     * https://www.geometrictools.com/GTE/Mathematics/DistSegmentSegment.h
     *
     * @param l The line segment to return the distance from.
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The distance from {@code this} to {@code l} at the {@code oom}
     * precision.
     */
    @Override
    public BigDecimal getDistance(V3D_LineSegment l, int oom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
