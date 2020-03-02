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

/**
 * V3D_Vector
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V3D_Vector {

    public BigDecimal dx;
    public BigDecimal dy;
    public BigDecimal dz;

    public V3D_Vector(BigDecimal dx, BigDecimal dy, BigDecimal dz) {
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(dx=" +dx+ ",dy=" +dy+ "dz=" 
                +dz+ ")";
    }

    /**
     * Calculate and return the
     * <A href="https://en.wikipedia.org/wiki/Dot_product">dot product</A>.
     *
     * @param v V3D_Vector
     * @return dot product
     */
    public BigDecimal getDotProduct(V3D_Vector v) {
        return (v.dx.multiply(this.dx)).add(v.dy.multiply(this.dy))
                .add(v.dz.multiply(this.dz));
    }

    /**
     * Calculate and return the
     * <A href="https://en.wikipedia.org/wiki/Cross_product">cross product</A>.
     * Treat this as the first vector and {@code v} as the second vector. The
     * resulting vector is in the direction given by the right hand rule.
     *
     * @param v V3D_Vector
     * @return V3D_Vector
     */
    public V3D_Vector getCrossProduct(V3D_Vector v) {
        return new V3D_Vector(dy.multiply(v.dz).subtract(v.dy.multiply(dz)),
                dz.multiply(v.dx).subtract(v.dz.multiply(dx)),
                dx.multiply(v.dy).subtract(v.dx.multiply(dy)));
    }

}
