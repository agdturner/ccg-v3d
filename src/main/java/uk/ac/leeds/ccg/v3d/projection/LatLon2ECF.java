/*
 * Copyright 2025 Centre for Computational Geography.
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
package uk.ac.leeds.ccg.v3d.projection;

import ch.obermuhlner.math.big.BigRational;
import java.math.RoundingMode;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigInteger;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigRational;
import uk.ac.leeds.ccg.math.geometry.Math_AngleBigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v3d.core.V3D_Environment;
import uk.ac.leeds.ccg.v3d.geometry.V3D_Point;
import uk.ac.leeds.ccg.v3d.geometry.d.V3D_Point_d;

/**
 * A class for conversion between Lat Lon and ECF.
 * https://en.wikipedia.org/wiki/Geographic_coordinate_conversion#From_geodetic_to_ECEF_coordinates
 * From https://en.wikipedia.org/wiki/World_Geodetic_System#WGS_84 (WGS 84)
 * Earth has an approximate radius at the equator re = 6378137.0 metres, and at
 * the poles rp = 6356752.314245 metres The flattening or aspect ratio =
 * 298.257223563 = re / (re - rp).
 *
 * @author Andy Turner
 */
public class LatLon2ECF {

    /**
     * 
     * @param lat The latitude in degrees from -90 to 90.
     * @param lon The longitude in degrees from -180 to 180. 
     * @param h The height above sea level in metres.
     * @return A point in ECF.
     */
    public static V3D_Point getPoint(V3D_Environment env, BigRational lat, 
            BigRational lon, BigRational h, int oom, RoundingMode rm) {
        BigRational re = BigRational.valueOf("6378137");
        BigRational rp = BigRational.valueOf("6356752.314245");
        int oomn6 = oom - 8;
        int oomn8 = oom - 8;
        //BigRational f = 1d / 298.257223563;
        BigRational rp2dre2 = (rp.multiply(rp)).divide(re.multiply(re));
        BigRational e2 = BigRational.ONE.subtract(rp2dre2);
        Math_AngleBigRational abr = new Math_AngleBigRational();
        BigRational latr = abr.toRadians(lat, oomn8, rm);
        BigRational lonr = abr.toRadians(lon, oomn8, rm);
        Math_BigInteger bi = new Math_BigInteger();
        BigRational slatr = Math_BigRational.sin(latr, bi, oomn8, rm);
        BigRational clatr = Math_BigRational.cos(latr, bi, oomn8, rm);
        BigRational c2latr = Math_BigRational.cos(BigRational.TWO.multiply(latr), bi, oomn8, rm);
        BigRational sin2latr = (BigRational.ONE.subtract(c2latr)).divide(2);
        BigRational slonr = Math_BigRational.sin(lonr, bi, oomn8, rm);
        BigRational clonr = Math_BigRational.cos(lonr, bi, oomn8, rm);
        //BigRational Np = re / Math.sqrt(1d - (e2 * slatr * slatr));
        BigRational Np = re.divide(new Math_BigRationalSqrt(BigRational.ONE.subtract(
                (e2.multiply(sin2latr))), oomn6, rm).getSqrt(oomn6, rm));
        BigRational Nph = Np.add(h);
        BigRational x = Nph.multiply(clatr).multiply(clonr);
        BigRational y = Nph.multiply(clatr).multiply(slonr);
        BigRational z = ((BigRational.ONE.subtract(e2)).multiply(Nph)).multiply(slatr);
        //BigRational z = (Nph - (e2 * Nph)) * slatr;
        return new V3D_Point(env, x,y,z);
    }
    
    public static void main(String[] args) {
        int oom = -5;
        RoundingMode rm = RoundingMode.HALF_UP;
        V3D_Environment env = new V3D_Environment(oom, rm);
        BigRational lat;
        BigRational lon;
        BigRational height = BigRational.ZERO;
//        lat = 0;
//        lon = 0;
//        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
//        lon = 90;
//        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
//        lon = 180;
//        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
//        lon = 270;
//        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
        lat = BigRational.valueOf(45);
        lon = BigRational.ZERO;
        System.out.println("lat=" + lat.toPlainString() + ", lon=" + lon.toPlainString() + " height=" + height.toPlainString() + " -> XYZ=" + getPoint(env, lat, lon, height, oom, rm).toString());
//        lon = 90;
//        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
//        lon = 180;
//        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
//        lon = 270;
//        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
//        lat = -45;
//        lon = 0;
//        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
//        lon = 90;
//        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
//        lon = 180;
//        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
//        lon = 270;
//        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
//        lat = 90;
//        lon = 0;
//        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
//        lon = 90;
//        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
//        lon = 180;
//        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
//        lon = 270;
//        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
//        lat = -90;
//        lon = 0;
//        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
//        lon = 90;
//        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
//        lon = 180;
//        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
//        lon = 270;
//        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
        lat = BigRational.valueOf(40);
        lon = BigRational.valueOf(70);
        System.out.println("lat=" + lat.toPlainString() + ", lon=" + lon.toPlainString() + " height=" + height.toPlainString() + " -> XYZ=" + getPoint(env, lat, lon, height, oom, rm).toString());
        lon = BigRational.valueOf(-110);
        System.out.println("lat=" + lat.toPlainString() + ", lon=" + lon.toPlainString() + " height=" + height.toPlainString() + " -> XYZ=" + getPoint(env, lat, lon, height, oom, rm).toString());
        lat = BigRational.valueOf(-140);
        lon = BigRational.valueOf(-110);
        System.out.println("lat=" + lat.toPlainString() + ", lon=" + lon.toPlainString() + " height=" + height.toPlainString() + " -> XYZ=" + getPoint(env, lat, lon, height, oom, rm).toString());
        lon = BigRational.valueOf(70);
        System.out.println("lat=" + lat.toPlainString() + ", lon=" + lon.toPlainString() + " height=" + height.toPlainString() + " -> XYZ=" + getPoint(env, lat, lon, height, oom, rm).toString());
    }
}
