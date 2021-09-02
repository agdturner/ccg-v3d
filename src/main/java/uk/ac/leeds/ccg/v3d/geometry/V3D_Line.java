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
import java.math.BigDecimal;
import java.util.Objects;
import uk.ac.leeds.ccg.math.Math_BigDecimal;
import uk.ac.leeds.ccg.math.matrices.Math_Matrix_BR;

/**
 * 3D representation of an infinite length line. The line passes through the
 * point {@link #p} and is travelling in the direction {@link #v} through point
 * {@link #q}. The "*" denotes a point in 3D and the line is shown with a line
 * of "e" symbols in the following depiction: {@code
 *                                       z                e
 *                          y           +                e
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
 *  - ----------------------|-----------/---e---/---- + x
 *                         /|              e   x0
 *                        / |-y1          e
 *                       /  |           e
 *                      /   |          e
 *                  z1-/    |         e
 *                    /     |        e
 *                   /      |       * q=<x1,y1,z1>
 *                  /       |      e
 *                 /        |     e
 *                -         |    e
 *                          -   e
 * }
 * <ul>
 * <li>Vector Form
 * <ul>
 * <li>{@code (x,y,z) = (p.x,p.y,p.z) + t(v.dx,v.dy,v.dz)}</li>
 * </ul>
 * <li>Parametric Form (where t describes a particular point on the line)
 * <ul>
 * <li>{@code x = p.x + t(v.dx)}</li>
 * <li>{@code y = p.y + t(v.dy)}</li>
 * <li>{@code z = p.z + t(v.dz)}</li>
 * </ul>
 * <li>Symmetric Form (assume {@code v.dx}, {@code v.dy}, and {@code v.dz} are
 * all nonzero)
 * <ul>
 * <li>{@code (x−p.x)/v.dx = (y−p.y)/v.dy = (z−p.z)/v.dz}</li>
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
     */
    public V3D_Line(V3D_Point p, V3D_Point q) {
        this.p = p;
        this.q = q;
        v = new V3D_Vector(q.x.subtract(p.x), q.y.subtract(p.y),
                q.z.subtract(p.z));
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
    public V3D_Line(V3D_Point p, V3D_Point q, boolean check) {
        if (p.equals(q)) {
            throw new RuntimeException("Points " + p + " and " + q
                    + " are the same and so do not define a line.");
        }
        this.p = p;
        this.q = q;
        v = new V3D_Vector(q.x.subtract(p.x), q.y.subtract(p.y),
                q.z.subtract(p.z));
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
                q.z.subtract(p.z));
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(p=" + p.toString()
                + ", q=" + q.toString() + ", v=" + v.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V3D_Line) {
            return equals((V3D_Line) o);
        }
        return false;
    }

    /**
     * @param l The line to test if it is the same as {@code this}.
     * @return {@code true} iff {@code l} is the same as {@code this}.
     */
    public boolean equals(V3D_Line l) {
        return isIntersectedBy(l.p) && isIntersectedBy(l.q);
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
    public boolean isIntersectedBy(V3D_Point pt) {
        V3D_Vector ppt = new V3D_Vector(pt.x.subtract(p.x),
                pt.y.subtract(p.y), pt.z.subtract(p.z));
        V3D_Vector cp = v.getCrossProduct(ppt);
        return cp.dx.isZero() && cp.dy.isZero() && cp.dz.isZero();
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
    public boolean isIntersectedBy(V3D_Line l) {
        return getIntersection(l) != null;
    }

    /**
     * Intersects {@code this} with {@code l}. If they are equivalent then
     * return {@code this}.
     *
     * @param l The line to get the intersection with {@code this}.
     * @return The intersection between {@code this} and {@code l}.
     */
    public V3D_Geometry getIntersection(V3D_Line l) {
        // Special case of parallel lines.
        if (isParallel(l)) {
            if (p.isIntersectedBy(l)) {
                // If lines are coincident return this.
                return this;
            } else {
                return null;
            }
        }
        V3D_Vector plp = new V3D_Vector(p, l.p);
        V3D_Vector lqlp = new V3D_Vector(l.q, l.p);
        if (lqlp.getMagnitudeSquared().compareTo(BigRational.ZERO) == 0) {
            if (isIntersectedBy(l.p)) {
                return l.p;
            }
        }
        V3D_Vector qp = new V3D_Vector(q, p);
        if (qp.getMagnitudeSquared().compareTo(BigRational.ZERO) == 0) {
            if (l.isIntersectedBy(p)) {
                return p;
            }
        }
        BigRational a = (plp.dx.multiply(lqlp.dx)).add(plp.dy
                .multiply(lqlp.dy)).add(plp.dz.multiply(lqlp.dz));
        BigRational b = (lqlp.dx.multiply(qp.dx)).add(lqlp.dy
                .multiply(qp.dy)).add(lqlp.dz.multiply(qp.dz));
        BigRational c = (plp.dx.multiply(qp.dx)).add(plp.dy
                .multiply(qp.dy)).add(plp.dz.multiply(qp.dz));
        BigRational d = (lqlp.dx.multiply(lqlp.dx)).add(lqlp.dy
                .multiply(lqlp.dy)).add(lqlp.dz.multiply(lqlp.dz));
        BigRational e = (qp.dx.multiply(qp.dx)).add(qp.dy
                .multiply(qp.dy)).add(qp.dz.multiply(qp.dz));
        BigRational den = (e.multiply(d)).subtract(b.multiply(b));
        BigRational num = (a.multiply(b)).subtract(c.multiply(d));
        if (den.compareTo(BigRational.ZERO) == 0) {
            if (num.compareTo(BigRational.ZERO) == 0) {
                BigRational x;
                BigRational y;
                BigRational z;
                BigRational lamda;
                BigRational mu;
                if (v.dx.compareTo(BigRational.ZERO) == 0) {
                    x = p.x;
                    if (l.v.dx.compareTo(BigRational.ZERO) == 0) {
                        if (v.dy.compareTo(BigRational.ZERO) == 0) {
                            y = p.y;
                            if (l.v.dy.compareTo(BigRational.ZERO) == 0) {
                                z = p.z;
                            } else {
                                if (v.dz.compareTo(BigRational.ZERO) == 0) {
                                    z = p.z;
                                } else {
                                    if (l.v.dz.compareTo(BigRational.ZERO) == 0) {
                                        z = l.p.z;
                                    } else {
                                        mu = (p.y.subtract(l.p.y)).divide(l.v.dy);
                                        z = l.p.z.add(l.v.dz.multiply(mu));
                                    }
                                }
                            }
                        } else {
                            if (l.v.dy.compareTo(BigRational.ZERO) == 0) {
                                y = l.p.y;
                                if (v.dz.compareTo(BigRational.ZERO) == 0) {
                                    z = p.z;
                                } else {
                                    if (l.v.dz.compareTo(BigRational.ZERO) == 0) {
                                        z = l.p.z;
                                    } else {
                                        lamda = (l.p.y.subtract(p.y)).divide(v.dy);
                                        z = p.z.add(v.dz.multiply(lamda));
                                    }
                                }
                                //x = p.x;            
                                //p.x + v.dx * lamda = l.p.x + l.v.dx * mu
                                //p.y + v.dy * lamda = l.p.y + l.v.dy * mu
                                //p.z + v.dz * lamda = l.p.z + l.v.dz * mu

                            } else {
                                if (v.dz.compareTo(BigRational.ZERO) == 0) {
                                    z = p.z;
                                    mu = (p.z.subtract(l.p.z)).divide(l.v.dy);
                                    y = l.p.y.add(l.v.dy.multiply(mu));
                                } else {
                                    if (l.v.dz.compareTo(BigRational.ZERO) == 0) {
                                        z = l.p.z;
                                        lamda = (l.p.z.subtract(p.z)).divide(v.dy);
                                        y = p.y.add(v.dy.multiply(lamda));
                                    } else {
                                        // There are 2 ways to calculate lamda. One way should work! - If not try calculating mu.
//                                        mu = ((p.y.add(v.dy.multiply(lamda))).subtract(l.p.y)).divide(l.v.dy);
//                                        lamda = ((l.p.z.subtract(p.z)).divide(v.dz)).add(l.v.dz.multiply(mu));
//                                        lamda = ((l.p.z.subtract(p.z)).divide(v.dz)).add(l.v.dz.multiply(((p.y.add(v.dy.multiply(lamda))).subtract(l.p.y)).divide(l.v.dy)));
//                                        l = ((bz-az)/adz) + (bdz*(ady*(l-by)/bdy))
//                                        l = ((bz-az)/adz) + bdz*ady*l/bdy - bdz*ady*by/bdy
//                                        l - bdz*ady*l/bdy = ((bz-az)/adz) - bdz*ady*by/bdy
//                                        l (1 - bdz*ady/bdy) = ((bz-az)/adz) - bdz*ady*by/bdy
//                                        l = (((bz-az)/adz) - bdz*ady*by/bdy)/(1 - bdz*ady/bdy)
                                        BigRational den2 = BigRational.ONE.subtract(l.v.dz.multiply(v.dy.divide(l.v.dy)));
                                        if (den2.compareTo(BigRational.ZERO) != 0) {
                                            lamda = (((l.p.z.subtract(p.z)).divide(v.dz)).subtract(l.v.dz.multiply(v.dy.multiply(l.p.y.divide(l.v.dy))))).divide(den2);
                                            z = p.z.add(v.dz.multiply(lamda));
                                            y = p.y.add(v.dy.multiply(lamda));
                                        } else {
                                            den2 = BigRational.ONE.subtract(l.v.dy.multiply(v.dz.divide(l.v.dz)));
                                            if (den2.compareTo(BigRational.ZERO) != 0) {
                                                lamda = (((l.p.y.subtract(p.y)).divide(v.dy)).subtract(l.v.dy.multiply(v.dz.multiply(l.p.z.divide(l.v.dz))))).divide(den2);
                                                z = p.z.add(v.dz.multiply(lamda));
                                                y = p.y.add(v.dy.multiply(lamda));
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
                        mu = (p.x.subtract(l.p.x)).divide(l.v.dx);
                        if (v.dy.compareTo(BigRational.ZERO) == 0) {
                            if (l.v.dy.compareTo(BigRational.ZERO) == 0) {
                                y = p.y;
                                z = p.z;
                            } else {
                                if (v.dz.compareTo(BigRational.ZERO) == 0) {
                                    y = l.p.y.add(l.v.dy.multiply(mu));
                                } else {
                                    y = p.y.add(v.dy.multiply(mu));
                                }
                                if (l.v.dz.compareTo(BigRational.ZERO) == 0) {
                                    z = p.z;
                                } else {
                                    z = l.p.z.add(l.v.dz.multiply(mu));
                                }
                            }
                        } else {
                            lamda = ((l.p.y.add(l.v.dy.multiply(mu)))
                                    .subtract(p.x)).divide(v.dy);
                            if (v.dz.compareTo(BigRational.ZERO) == 0) {
                                z = p.z;
                            } else {
                                z = p.z.add(v.dz.multiply(lamda));
                            }
                            if (l.v.dy.compareTo(BigRational.ZERO) == 0) {
                                y = p.y;
                            } else {
                                y = l.p.y.add(l.v.dy.multiply(mu));
                            }
                        }
                    }
                } else {
                    if (l.v.dx.compareTo(BigRational.ZERO) == 0) {
                        lamda = l.p.x.subtract(p.x).divide(v.dx);
                        x = l.p.x;
                        if (v.dy.compareTo(BigRational.ZERO) == 0) {
                            mu = (p.y.subtract(l.p.y)).divide(l.v.dy);
                            y = p.y;
                            if (v.dz.compareTo(BigRational.ZERO) == 0) {
                                z = p.z;
                            } else {
                                if (l.v.dz.compareTo(BigRational.ZERO) == 0) {
                                    z = l.p.z;
                                } else {
                                    z = l.p.z.add(l.v.dz.multiply(mu));
                                }
                            }
                        } else {
                            if (v.dy.compareTo(BigRational.ZERO) == 0) {
                                y = p.y;
                                if (l.v.dy.compareTo(BigRational.ZERO) == 0) {
                                    if (v.dz.compareTo(BigRational.ZERO) == 0) {
                                        z = p.z;
                                    } else {
                                        if (l.v.dz.compareTo(BigRational.ZERO) == 0) {
                                            z = l.p.z;
                                        } else {
                                            mu = ((p.z.add(v.dz.multiply(lamda))).subtract(l.p.z)).divide(l.v.dz);
                                            z = l.p.z.add(l.v.dz.multiply(mu));
                                        }
                                    }
                                } else {
                                    if (v.dz.compareTo(BigRational.ZERO) == 0) {
                                        z = p.z;
                                    } else {
                                        if (l.v.dz.compareTo(BigRational.ZERO) == 0) {
                                            z = l.p.z;
                                        } else {
                                            mu = (p.z.subtract(l.p.z)).divide(l.v.dz);
                                            z = l.p.z.add(l.v.dz.multiply(mu));
                                        }
                                    }
                                }
                            } else {
                                if (l.v.dy.compareTo(BigRational.ZERO) == 0) {
                                    y = l.p.y;
                                    if (v.dz.compareTo(BigRational.ZERO) == 0) {
                                        z = p.z;
                                    } else {
                                        if (l.v.dz.compareTo(BigRational.ZERO) == 0) {
                                            z = l.p.z;
                                        } else {
                                            mu = ((p.z.add(v.dz.multiply(lamda))).subtract(l.p.z)).divide(l.v.dz);
                                            z = l.p.z.add(l.v.dz.multiply(mu));
                                        }
                                    }
                                } else {
                                    y = p.y.add(v.dy.multiply(lamda));
                                    if (v.dz.compareTo(BigRational.ZERO) == 0) {
                                        z = p.z;
                                    } else {
                                        if (l.v.dz.compareTo(BigRational.ZERO) == 0) {
                                            z = l.p.z;
                                        } else {
                                            mu = ((p.z.add(v.dz.multiply(lamda))).subtract(l.p.z)).divide(l.v.dz);
                                            z = l.p.z.add(l.v.dz.multiply(mu));
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        // v.dx > 0 && l.v.dx > 0
                        if (v.dy.compareTo(BigRational.ZERO) == 0) {
                            y = p.y;
                            if (l.v.dy.compareTo(BigRational.ZERO) == 0) {
                                if (v.dz.compareTo(BigRational.ZERO) == 0) {
                                    z = p.z;
                                    x = p.x;
                                } else {
                                    if (l.v.dz.compareTo(BigRational.ZERO) == 0) {
                                        z = l.p.z;
                                        lamda = (l.p.z.subtract(p.z)).divide(v.dz);
                                        x = p.x.add(v.dx.multiply(lamda));
                                    } else {
                                        // There are 2 ways to calculate lamda. One way should work! - If not try calculating mu.
//                                        mu = ((p.x.add(v.dx.multiply(lamda))).subtract(l.p.x)).divide(l.v.dx);
//                                        lamda = ((l.p.z.subtract(p.z)).divide(v.dz)).add(l.v.dz.multiply(mu));
//                                        lamda = ((l.p.z.subtract(p.z)).divide(v.dz)).add(l.v.dz.multiply(((p.x.add(v.dx.multiply(lamda))).subtract(l.p.x)).divide(l.v.dx)));
//                                        l = ((bz-az)/adz) + (bdz*(adx*(l-bx)/bdx))
//                                        l = ((bz-az)/adz) + bdz*adx*l/bdx - bdz*adx*bx/bdx
//                                        l - bdz*adx*l/bdx = ((bz-az)/adz) - bdz*adx*bx/bdx
//                                        l (1 - bdz*adx/bdx) = ((bz-az)/adz) - bdz*adx*bx/bdx
//                                        l = (((bz-az)/adz) - bdz*adx*bx/bdx)/(1 - bdz*adx/bdx)
                                        BigRational den2 = BigRational.ONE.subtract(l.v.dz.multiply(v.dx.divide(l.v.dx)));
                                        if (den2.compareTo(BigRational.ZERO) != 0) {
                                            lamda = (((l.p.z.subtract(p.z)).divide(v.dz)).subtract(l.v.dz.multiply(v.dx.multiply(l.p.x.divide(l.v.dx))))).divide(den2);
                                            z = p.z.add(v.dz.multiply(lamda));
                                            x = p.x.add(v.dx.multiply(lamda));
                                        } else {
                                            den2 = BigRational.ONE.subtract(l.v.dx.multiply(v.dz.divide(l.v.dz)));
                                            if (den2.compareTo(BigRational.ZERO) != 0) {
                                                lamda = (((l.p.x.subtract(p.x)).divide(v.dx)).subtract(l.v.dx.multiply(v.dz.multiply(l.p.z.divide(l.v.dz))))).divide(den2);
                                                z = p.z.add(v.dz.multiply(lamda));
                                                x = p.x.add(v.dx.multiply(lamda));
                                            } else {
                                                // This should not happen!
                                                z = null;
                                                x = null;
                                            }
                                        }
                                    }
                                }
                            } else {
                                mu = p.y.subtract(l.p.y).divide(l.v.dy);
                                x = l.p.x.add(l.v.dx.multiply(mu));
                                z = l.p.z.add(l.v.dz.multiply(mu));
                            }
                        } else {
                            // v.dx > 0 && l.v.dx > 0 && v.dy > 0
                            if (v.dz.compareTo(BigRational.ZERO) == 0) {
                                z = p.z;
                                if (l.v.dz.compareTo(BigRational.ZERO) == 0) {
                                    // There are 2 ways to calculate lamda. One way should work! - If not try calculating mu.
//                                    mu = ((p.x.add(v.dx.multiply(lamda))).subtract(l.p.x)).divide(l.v.dx);
//                                    lamda = ((l.p.y.subtract(p.y)).divide(v.dy)).add(l.v.dy.multiply(mu));
//                                    lamda = ((l.p.y.subtract(p.y)).divide(v.dy)).add(l.v.dy.multiply(((p.x.add(v.dx.multiply(lamda))).subtract(l.p.x)).divide(l.v.dx)));
//                                    l = ((by - ay) / ady) + (bdy * (adx * (l - bx) / bdx))
//                                    l = ((by - ay) / ady) + bdy * adx * l / bdx - bdy * adx * bx / bdx
//                                    l - bdy * adx * l / bdx = ((by - ay) / ady) - bdy * adx * bx / bdx
//                                    l(1 - bdy * adx / bdx) = ((by - ay) / ady) - bdy * adx * bx / bdx
//                                    l = (((by-ay)/ady) - bdy*adx*bx/bdx)/(1 - bdy*adx/bdx)
                                    BigRational den2 = BigRational.ONE.subtract(l.v.dy.multiply(v.dx.divide(l.v.dx)));
                                    if (den2.compareTo(BigRational.ZERO) != 0) {
                                        lamda = (((l.p.y.subtract(p.y)).divide(v.dy)).subtract(l.v.dy.multiply(v.dx.multiply(l.p.x.divide(l.v.dx))))).divide(den2);
                                        y = p.y.add(v.dy.multiply(lamda));
                                        x = p.x.add(v.dx.multiply(lamda));
                                    } else {
                                        den2 = BigRational.ONE.subtract(l.v.dx.multiply(v.dy.divide(l.v.dy)));
                                        if (den2.compareTo(BigRational.ZERO) != 0) {
                                            lamda = (((l.p.x.subtract(p.x)).divide(v.dx)).subtract(l.v.dx.multiply(v.dy.multiply(l.p.y.divide(l.v.dy))))).divide(den2);
                                            y = p.y.add(v.dy.multiply(lamda));
                                            x = p.x.add(v.dx.multiply(lamda));
                                        } else {
                                            // This should not happen!
                                            y = null;
                                            x = null;
                                        }
                                    }
                                } else {
                                    mu = (p.z.subtract(l.p.z)).divide(l.v.dz);
                                    y = l.p.y.add(l.v.dy.multiply(mu));
                                    x = l.p.x.add(l.v.dx.multiply(mu));
                                }
                            } else {
                                if (l.v.dz.compareTo(BigRational.ZERO) == 0) {
                                    z = l.p.z;
                                    lamda = (l.p.z.subtract(p.z)).divide(v.dz);
                                    y = p.y.add(v.dy.multiply(lamda));
                                    x = p.x.add(v.dx.multiply(lamda));
                                } else {
                                    // There are 6 ways to calculate lamda. One way should work! - If not try calculating mu.
                                    BigRational den2 = BigRational.ONE.subtract(l.v.dy.multiply(v.dx.divide(l.v.dx)));
                                    if (den2.compareTo(BigRational.ZERO) != 0) {
                                        lamda = (((l.p.y.subtract(p.y)).divide(v.dy)).subtract(l.v.dy.multiply(v.dx.multiply(l.p.x.divide(l.v.dx))))).divide(den2);
                                        x = p.x.add(v.dx.multiply(lamda));
                                        y = p.y.add(v.dy.multiply(lamda));
                                        z = p.z.add(v.dz.multiply(lamda));
                                    } else {
                                        den2 = BigRational.ONE.subtract(l.v.dy.multiply(v.dz.divide(l.v.dz)));
                                        if (den2.compareTo(BigRational.ZERO) != 0) {
                                            lamda = (((l.p.y.subtract(p.y)).divide(v.dy)).subtract(l.v.dy.multiply(v.dz.multiply(l.p.z.divide(l.v.dz))))).divide(den2);
                                            x = p.x.add(v.dx.multiply(lamda));
                                            y = p.y.add(v.dy.multiply(lamda));
                                            z = p.z.add(v.dz.multiply(lamda));
                                        } else {
                                            den2 = BigRational.ONE.subtract(l.v.dz.multiply(v.dx.divide(l.v.dx)));
                                            if (den2.compareTo(BigRational.ZERO) != 0) {
                                                lamda = (((l.p.z.subtract(p.z)).divide(v.dz)).subtract(l.v.dz.multiply(v.dx.multiply(l.p.x.divide(l.v.dx))))).divide(den2);
                                                x = p.x.add(v.dx.multiply(lamda));
                                                y = p.y.add(v.dy.multiply(lamda));
                                                z = p.z.add(v.dz.multiply(lamda));
                                            } else {
                                                den2 = BigRational.ONE.subtract(l.v.dz.multiply(v.dy.divide(l.v.dy)));
                                                if (den2.compareTo(BigRational.ZERO) != 0) {
                                                    lamda = (((l.p.z.subtract(p.z)).divide(v.dz)).subtract(l.v.dz.multiply(v.dy.multiply(l.p.y.divide(l.v.dy))))).divide(den2);
                                                    x = p.x.add(v.dx.multiply(lamda));
                                                    y = p.y.add(v.dy.multiply(lamda));
                                                    z = p.z.add(v.dz.multiply(lamda));
                                                } else {
                                                    den2 = BigRational.ONE.subtract(l.v.dx.multiply(v.dx.divide(l.v.dy)));
                                                    if (den2.compareTo(BigRational.ZERO) != 0) {
                                                        lamda = (((l.p.x.subtract(p.x)).divide(v.dx)).subtract(l.v.dx.multiply(v.dy.multiply(l.p.y.divide(l.v.dy))))).divide(den2);
                                                        x = p.x.add(v.dx.multiply(lamda));
                                                        y = p.y.add(v.dy.multiply(lamda));
                                                        z = p.z.add(v.dz.multiply(lamda));
                                                    } else {
                                                        den2 = BigRational.ONE.subtract(l.v.dx.multiply(v.dx.divide(l.v.dz)));
                                                        if (den2.compareTo(BigRational.ZERO) != 0) {
                                                            lamda = (((l.p.x.subtract(p.x)).divide(v.dx)).subtract(l.v.dx.multiply(v.dz.multiply(l.p.z.divide(l.v.dz))))).divide(den2);
                                                            x = p.x.add(v.dx.multiply(lamda));
                                                            y = p.y.add(v.dy.multiply(lamda));
                                                            z = p.z.add(v.dz.multiply(lamda));
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
                //p.x + v.dx * lamda = l.p.x + l.v.dx * mu
                //p.y + v.dy * lamda = l.p.y + l.v.dy * mu
                //p.z + v.dz * lamda = l.p.z + l.v.dz * mu
                return new V3D_Point(x, y, z);
            }
            return null;
        }
        BigRational mua = num.divide(den);
        BigRational mub = (a.add(b.multiply(mua))).divide(d).negate();
        V3D_Point pi = new V3D_Point(
                (p.x.add(mua.multiply(qp.dx))),
                (p.y.add(mua.multiply(qp.dy))),
                (p.z.add(mua.multiply(qp.dz))));
        // If point p is on both lines then return this as the intersection.
        if (isIntersectedBy(pi) && l.isIntersectedBy(pi)) {
            return pi;
        }
        V3D_Point qi = new V3D_Point(
                (l.p.x.add(mub.multiply(lqlp.dx))),
                (l.p.y.add(mub.multiply(lqlp.dy))),
                (l.p.z.add(mub.multiply(lqlp.dz))));
        // If point q is on both lines then return this as the intersection.
        if (isIntersectedBy(qi) && l.isIntersectedBy(qi)) {
            return qi;
        }
        /**
         * The only time when p and q should be different is when the lines do
         * not intersect. In this case p and q are meant to be the end points of
         * the shortest line between the tow lines input.
         */
        if (pi.equals(qi)) {
            return pi;
        } else {
            return null;
        }
        //return new V3D_Line(p, q);
    }

    /**
     * @param r The ray to test if it intersects with {@code this}.
     * @return {@code true} If {@code this} and {@code r} intersect.
     */
    public boolean isIntersectedBy(V3D_Ray r) {
        return r.isIntersectedBy(this);
    }

    /**
     * Intersects {@code this} with {@code r}.
     *
     * @param r The ray to get intersection with {@code this}.
     * @return The intersection between {@code this} and {@code r}.
     */
    public V3D_Geometry getIntersection(V3D_Ray r) {
        return r.getIntersection(this);
    }

    /**
     * @param pt A point for which the shortest line segment to this is
     * returned.
     * @return The line segment having the shortest distance between {@code pt}
     * and {@code this}.
     */
    public V3D_LineSegment getLineOfIntersection(V3D_Point pt) {
        if (isIntersectedBy(pt)) {
            return new V3D_LineSegment(pt, pt);
        }
        /**
         * Calculate the direction vector of the line connecting the closest
         * points by computing the cross product.
         */
        V3D_Vector pv = new V3D_Vector(p, pt);
        V3D_Vector cp = pv.getCrossProduct(v);
        /**
         * Create any second point on this line by applying the cross product
         * vector.
         */
        V3D_Line loi = new V3D_Line(pt, pt.apply(cp));
        V3D_Point poi = (V3D_Point) getIntersection(loi);
        return new V3D_LineSegment(pt, poi);
    }

    /**
     * @param pt The point projected onto this.
     * @return A point on {@code this} which is the shortest distance from
     * {@code pt}.
     */
    public V3D_Point getPointOfIntersection(V3D_Point pt) {
        if (this.isIntersectedBy(pt)) {
            return pt;
        }
        return (V3D_Point) getIntersection(getLineOfIntersection(pt));
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
        V3D_Vector plp = new V3D_Vector(p, l.p);
        V3D_Vector lqlp = new V3D_Vector(l.q, l.p);
        if (lqlp.getMagnitudeSquared().compareTo(BigRational.ZERO) == 0) {
            return null;
        }
        V3D_Vector qp = new V3D_Vector(q, p);
        if (qp.getMagnitudeSquared().compareTo(BigRational.ZERO) == 0) {
            return null;
        }
        BigRational a = (plp.dx.multiply(lqlp.dx)).add(plp.dy
                .multiply(lqlp.dy)).add(plp.dz.multiply(lqlp.dz));
        BigRational b = (lqlp.dx.multiply(qp.dx)).add(lqlp.dy
                .multiply(qp.dy)).add(lqlp.dz.multiply(qp.dz));
        BigRational c = (plp.dx.multiply(qp.dx)).add(plp.dy
                .multiply(qp.dy)).add(plp.dz.multiply(qp.dz));
        BigRational d = (lqlp.dx.multiply(lqlp.dx)).add(lqlp.dy
                .multiply(lqlp.dy)).add(lqlp.dz.multiply(lqlp.dz));
        BigRational e = (qp.dx.multiply(qp.dx)).add(qp.dy
                .multiply(qp.dy)).add(qp.dz.multiply(qp.dz));
        BigRational den = (e.multiply(d)).subtract(b.multiply(b));
        if (den.compareTo(BigRational.ZERO) == 0) {
            return null;
        }
        BigRational num = (a.multiply(b)).subtract(c.multiply(d));
        BigRational mua = num.divide(den);
        BigRational mub = (a.add(b.multiply(mua))).divide(d);
        V3D_Point pi = new V3D_Point(
                (p.x.add(mua.multiply(qp.dx))),
                (p.y.add(mua.multiply(qp.dy))),
                (p.z.add(mua.multiply(qp.dz))));
        V3D_Point qi = new V3D_Point(
                (l.p.x.add(mub.multiply(lqlp.dx))),
                (l.p.y.add(mub.multiply(lqlp.dy))),
                (l.p.z.add(mub.multiply(lqlp.dz))));
        if (pi.equals(qi)) {
            return pi;
        }
        return new V3D_Line(pi, qi);
    }

    /**
     * @param v The vector to apply.
     * @return a new line.
     */
    @Override
    public V3D_Line apply(V3D_Vector v) {
        return new V3D_Line(p.apply(v), q.apply(v));
    }

    /**
     * https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line
     *
     * @param p A point for which the minimum distance from {@code this} is
     * returned.
     *
     * @param oom The Order of Magnitude for the precision of the result.
     * @return The minimum distance between this and {@code p}.
     */
    @Override
    public BigDecimal getDistance(V3D_Point p, int oom) {
//        /**
//         * Calculate the direction vector of the line connecting the closest
//         * points by computing the cross product.
//         */
//        V3D_Vector pv = new V3D_Vector(this.p, p);
//        V3D_Vector cp = pv.getCrossProduct(v);
//        
//        BigRational pvm2 = pv.getMagnitudeSquared();
//        BigRational cpm2 = cp.getMagnitudeSquared();
//        if (pvm2.compareTo(BigRational.ZERO) == 0) {
//            if (cpm2.compareTo(BigRational.ZERO) == 0) {
//                return BigDecimal.ONE;
//            }
//        }
//        Math_BigRationalSqrt pvm = new Math_BigRationalSqrt(pvm2);
//        Math_BigRationalSqrt cpm = new Math_BigRationalSqrt(cpm2);
//        return cpm.divide(pvm).toBigDecimal(oom);
//
//        return getDistance(p).toBigDecimal(oom);
        V3D_Vector pv = new V3D_Vector(this.p, p);
        V3D_Vector vu = v.getUnitVector(oom - 2);
        return p.getDistance(new V3D_Point(vu.multiply(pv.getDotProduct(vu))
                .add(new V3D_Vector(this.p))), oom);
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
////        BigRational pvm2 = pv.getMagnitudeSquared();
////        BigRational cpm2 = cp.getMagnitudeSquared();
////        if (pvm2.compareTo(BigRational.ZERO) == 0) {
////            if (cpm2.compareTo(BigRational.ZERO) == 0) {
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
//            V3D_Vector v2 = new V3D_Vector(l.p, q);
//            return v2.subtract(v.multiply((v2.getDotProduct(v))
//                    .divide(v.getMagnitudeSquared()))).getMagnitude(scale, rm);
        } else {
            /**
             * Calculate the direction vector of the line connecting the closest
             * points by computing the cross product.
             */
            V3D_Vector cp = v.getCrossProduct(l.v);
            /**
             * Calculate the delta from {@link #p} and l.p
             */
            V3D_Vector delta = new V3D_Vector(p).subtract(new V3D_Vector(l.p));
            BigRational m = BigRational.valueOf(cp.getMagnitude(oom - 2));
            // d = cp.(delta)/m
            BigRational dp = cp.getDotProduct(delta);
            // m should only be zero if the lines are parallel.
            BigRational d = dp.divide(m);
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
        if (isParallel(r)) {
            return p.getDistance(new V3D_Line(r), oom);
        } else {
            if (isIntersectedBy(r)) {
                return BigDecimal.ZERO;
            } else {
                V3D_LineSegment li = (V3D_LineSegment) getLineOfIntersection(r);
                if (r.isIntersectedBy(li.q)) {
                    return li.getLength(oom);
                }
                return r.p.getDistance(this, oom);
            }
        }
    }

    /**
     * @return {@code true} iff this is parallel to the plane defined by x=0.
     */
    public boolean isParallelToX0() {
        return v.dx.compareTo(BigRational.ZERO) == 0;
    }

    /**
     * @return {@code true} iff this is parallel to the plane defined by y=0.
     */
    public boolean isParallelToY0() {
        return v.dy.compareTo(BigRational.ZERO) == 0;
    }

    /**
     * @return {@code true} iff this is parallel to the plane defined by z=0.
     */
    public boolean isParallelToZ0() {
        return v.dz.compareTo(BigRational.ZERO) == 0;
    }

    @Override
    public boolean isEnvelopeIntersectedBy(V3D_Line l) {
        if (this.isIntersectedBy(l)) {
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
        Math_Matrix_BR res = new Math_Matrix_BR(2, 3);
        BigRational[][] m = res.getM();
        m[0][0] = p.x;
        m[0][1] = p.y;
        m[0][2] = p.z;
        m[1][0] = q.x;
        m[1][1] = q.y;
        m[1][2] = q.z;
        return res;
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
