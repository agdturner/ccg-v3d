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
package uk.ac.leeds.ccg.vector.projection;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import uk.ac.leeds.ccg.math.Math_BigDecimal;

/**
 * Adapted from http://www.movable-type.co.uk/scripts/latlong-gridref.html.
 * Ordnance Survey Grid Reference functions (c) Chris Veness 2005-2012
 * http://www.movable-type.co.uk/scripts/gridref.js
 *
 * @author geoagdt
 */
public class Vector_OSGBtoLatLon {

    private BigDecimal pi;

    public BigDecimal getPi() {
        if (pi != null) {
            pi = new Math_BigDecimal().getPi();
        }
        return pi;
    }

    public Vector_OSGBtoLatLon() {
    }

    /**
     * Converts WGS84 latitude/longitude coordinate to an Ordnance Survey
     * easting/northing coordinate
     *
     * @param lat latitude to be converted
     * @param lon longitude to be converted
     * @return double[2] r: where r[0] is easting, r[1] is northing.
     */
    public static double[] latlon2osgb(double lat, double lon) {
        double[] result = new double[2];
        lat = toRadians(lat);
        lon = toRadians(lon);
        double a = 6377563.396;
        double b = 6356256.910;            // Airy 1830 major & minor semi-axes
        double F0 = 0.9996012717;          // NatGrid scale factor on central meridian
        //System.out.println("F0 " + F0);
        double lat0 = toRadians(49);
        double lon0 = toRadians(-2);       // NatGrid true origin is 49ºN,2ºW
        double N0 = -100000;
        double E0 = 400000;                // northing & easting of true origin, metres
        double e2 = 1 - (b * b) / (a * a); // eccentricity squared
        //System.out.println("Eccentricity squared " + e2);
        double n = (a - b) / (a + b);
        double n2 = n * n;
        double n3 = n * n * n;

        double cosLat = Math.cos(lat);
        double sinLat = Math.sin(lat);
        double nu = a * F0 / Math.sqrt(1 - e2 * sinLat * sinLat); // transverse radius of curvature
        //System.out.println("Transverse radius of curvature " + nu);
        double rho = a * F0 * (1 - e2) / Math.pow(1 - e2 * sinLat * sinLat, 1.5); // meridional radius of curvature
        double eta2 = nu / rho - 1;

        double Ma = (1 + n + (5 / 4) * n2 + (5 / 4) * n3) * (lat - lat0);
        double Mb = (3 * n + 3 * n * n + (21D / 8D) * n3) * Math.sin(lat - lat0) * Math.cos(lat + lat0);
        double Mc = ((15D / 8D) * n2 + (15D / 8D) * n3) * Math.sin(2D * (lat - lat0)) * Math.cos(2D * (lat + lat0));
        double Md = (35D / 24D) * n3 * Math.sin(3 * (lat - lat0)) * Math.cos(3D * (lat + lat0));
        double M = b * F0 * (Ma - Mb + Mc - Md); // meridional arc

        double cos3lat = cosLat * cosLat * cosLat;
        double cos5lat = cos3lat * cosLat * cosLat;
        double tan2lat = Math.tan(lat) * Math.tan(lat);
        double tan4lat = tan2lat * tan2lat;

        double I = M + N0;
        double II = (nu / 2D) * sinLat * cosLat;
        double III = (nu / 24D) * sinLat * cos3lat * (5 - tan2lat + 9 * eta2);
        double IIIA = (nu / 720D) * sinLat * cos5lat * (61 - 58 * tan2lat + tan4lat);
        double IV = nu * cosLat;
        double V = (nu / 6D) * cos3lat * (nu / rho - tan2lat);
        double VI = (nu / 120D) * cos5lat * (5 - 18 * tan2lat + tan4lat + 14 * eta2 - 58 * tan2lat * eta2);

        double dLon = lon - lon0;
        double dLon2 = dLon * dLon;
        double dLon3 = dLon2 * dLon;
        double dLon4 = dLon3 * dLon;
        double dLon5 = dLon4 * dLon;
        double dLon6 = dLon5 * dLon;

        double N = I + II * dLon2 + III * dLon4 + IIIA * dLon6;
        double E = E0 + IV * dLon + V * dLon3 + VI * dLon5;
        //System.out.println("E " + E + ", N " + N);
        result[0] = E;
        result[1] = N;
        return result;
        //return new OsGridRef(E, N);
    }

    /**
     * Converts WGS84 latitude/longitude coordinate to an Ordnance Survey
     * easting/northing coordinate
     * http://en.wikipedia.org/wiki/World_Geodetic_System
     * http://en.wikipedia.org/wiki/Ordnance_Survey_National_Grid
     *
     * @param lat latitude to be converted
     * @param lon longitude to be converted
     * @param dp The number of decimal places the result must be correct to.
     * @param rm The Rounding Mode to use for calculations.
     * @return BigDecimal[2] r: where r[0] is easting, r[1] is northing.
     */
    public BigDecimal[] latlon2osgb(BigDecimal lat, BigDecimal lon, int dp, 
            RoundingMode rm) {
        //int doubleDecimalPlaces = decimalPlaces * 2;
        //int tripleDecimalPlaces = decimalPlaces * 3;
        // Higher number of decmal places
        int hdp = Math.max(dp * 10, 100);

        /**
         * http://en.wikipedia.org/wiki/World_Geodetic_System#Main_parameters
         * The coordinate origin of WGS 84 is meant to be located at the Earth's
         * center of mass; the error is believed to be less than 2 cm. The WGS
         * 84 meridian of zero longitude is the IERS Reference Meridian, 5.31
         * arc seconds or 102.5 metres (336.3 ft) east of the Greenwich meridian
         * at the latitude of the Royal Observatory.
         *
         * The WGS 84 datum surface is an oblate spheroid (ellipsoid) with major
         * (equatorial) radius a = 6378137 m at the equator and flattening f =
         * 1/298.257223563. The polar semi-minor axis b then equals a times
         * (1−f), or 6356752.3142 m.
         *
         * Presently WGS 84 uses the EGM96 (Earth Gravitational Model 1996)
         * geoid, revised in 2004. This geoid defines the nominal sea level
         * surface by means of a spherical harmonics series of degree 360 (which
         * provides about 100 km horizontal resolution). The deviations of the
         * EGM96 geoid from the WGS 84 reference ellipsoid range from about −105
         * m to about +85 m. EGM96 differs from the original WGS 84 geoid,
         * referred to as EGM84.
         */
        /**
         * http://en.wikipedia.org/wiki/Ordnance_Survey_National_Grid#Datum_shift_between_OSGB_36_and_WGS_84
         * The difference between the coordinates on different datums varies
         * from place to place. The longitude and latitude positions on OSGB 36
         * are the same as for WGS 84 at a point in the Atlantic Ocean well to
         * the west of Great Britain. In Cornwall, the WGS 84 longitude lines
         * are about 70 metres east of their OSGB 36 equivalents, this value
         * rising gradually to about 120 m east on the east coast of East
         * Anglia. The WGS 84 latitude lines are about 70 m south of the OSGB 36
         * lines in South Cornwall, the difference diminishing to zero in the
         * Scottish Borders, and then increasing to about 50 m north on the
         * north coast of Scotland. (If the lines are further east, then the
         * longitude value of any given point is further west. Similarly, if the
         * lines are further south, the values will give the point a more
         * northerly latitude.) The smallest datum shift is on the west coast of
         * Scotland and the greatest in Kent.
         *
         * But Great Britain has not shrunk by 100+ metres; a point near Land's
         * End now computes to be 27.6 metres closer to a point near Duncansby
         * Head than it did under OSGB36.
         *
         * http://en.wikipedia.org/wiki/Ordnance_Survey_National_Grid#Summary_parameters_of_the_coordinate_system
         * Datum: OSGB36 Map projection: Transverse Mercator True Origin: 49°N,
         * 2°W False Origin: 400 km west, 100 km north of True Origin Scale
         * Factor: 0.9996012717 (or exactly log10(0.9998268) - 1)????? EPSG
         * Code: EPSG:27700 Ellipsoid: Airy 1830 The defining Airy dimensions
         * are a = 20923713 "feet", b = 20853810 "feet". In the Retriangulation
         * the base-10 logarithm of the number of metres in a "foot" was set at
         * (0.48401603 − 1) exactly and the Airy metric dimensions are
         * calculated from that. The flattening is exactly 69903 divided by
         * 20923713. Semi-major axis a: 6377563.396 m Semi-minor axis b:
         * 6356256.909 m Flattening (derived constant): 1/299.3249646
         *
         * http://www.ordnancesurvey.co.uk/docs/support/guide-coordinate-systems-great-britain.pdf
         */
        BigDecimal[] result = new BigDecimal[2];
        lat = toRadians(lat, hdp, rm);
        lon = toRadians(lon, hdp, rm);
        /*
         * Airy 1830 major semi-axis
         */
        BigDecimal a = new BigDecimal("6377563.396");
        /*
         * Airy 1830 minor semi-axis
         */
        //BigDecimal b = new BigDecimal("6356256.910"); 
        BigDecimal b = new BigDecimal("6356256.909");
        /*
         * NatGrid scale factor on central meridian
         */
        BigDecimal F0 = new BigDecimal("0.9996012717");
        //System.out.println("F0 " + F0);
        // NatGrid true origin is 49ºN, 2ºW
        BigDecimal lat0 = toRadians(new BigDecimal("49"), hdp, rm);
        BigDecimal lon0 = toRadians(new BigDecimal("-2"), hdp, rm);
        // northing & easting of true origin, metres
        BigDecimal N0 = new BigDecimal("-100000");
        BigDecimal E0 = new BigDecimal("400000");
        //double e2 = 1 - (b * b) / (a * a); // eccentricity squared
        BigDecimal b2 = b.multiply(b);
        BigDecimal a2 = a.multiply(a);
        BigDecimal b2overa2 = Math_BigDecimal.divideRoundIfNecessary(
                b2, a2, hdp, rm);
        // eccentricity squared
        BigDecimal e2 = BigDecimal.ONE.subtract(b2overa2);
        //System.out.println("Eccentricity squared " + e2);
        BigDecimal asubtractb = a.subtract(b);
        BigDecimal aaddb = a.add(b);
        //double n = (a - b) / (a + b);
        BigDecimal n = Math_BigDecimal.divideRoundIfNecessary(
                asubtractb, aaddb, hdp, rm);
        //System.out.println("n " + n);
        //double n2 = n * n;
        BigDecimal n2 = n.multiply(n);
        //System.out.println("n2 " + n2);
        //double n3 = n * n * n;
        BigDecimal n3 = n2.multiply(n);
        //System.out.println("n3 " + n3);

        Math_BigDecimal bd = new Math_BigDecimal(1000);
        //double cosLat = Math.cos(lat);
        BigDecimal cosLat = Math_BigDecimal.cos(lat, bd, hdp, rm);
        //double sinLat = Math.sin(lat);
        BigDecimal sinLat = Math_BigDecimal.sin(lat, bd, hdp, rm);
        //double nu = a * F0 / Math.sqrt(1 - e2 * sinLat * sinLat); // transverse radius of curvature
        BigDecimal nunum = a.multiply(F0);
        BigDecimal nubit = BigDecimal.ONE.subtract(e2.multiply(sinLat.multiply(sinLat)));
        BigDecimal nuden = Math_BigDecimal.sqrt(nubit, hdp, rm);
        // transverse radius of curvature
        BigDecimal nu = Math_BigDecimal.divideRoundIfNecessary(
                nunum, nuden, hdp, rm);
        //System.out.println("Transverse radius of curvature " + nu);
        //double rho = a * F0 * (1 - e2) / Math.pow(1 - e2 * sinLat * sinLat, 1.5); // meridional radius of curvature
        BigDecimal rhonum = nunum.multiply(BigDecimal.ONE.subtract(e2));
//        BigDecimal rhoden2 = Math_BigDecimal.power(
//                nubit,
//                new BigDecimal("1.5"),
//                tripleDecimalPlaces,
//                aRoundingMode);
        BigDecimal rhoden = nuden.multiply(nubit);
        BigDecimal rho = Math_BigDecimal.divideRoundIfNecessary(
                rhonum, rhoden, hdp, rm); // meridional radius of curvature
        //double eta2 = nu / rho - 1;
        BigDecimal eta2 = Math_BigDecimal.divideRoundIfNecessary(
                nu, rho, hdp, rm).subtract(BigDecimal.ONE);
        //double Ma = (1 + n + (5 / 4) * n2 + (5 / 4) * n3) * (lat - lat0);
        BigDecimal three = BigDecimal.valueOf(3);
        BigDecimal four = BigDecimal.valueOf(4);
        BigDecimal five = BigDecimal.valueOf(5);
        BigDecimal fivebyfour = Math_BigDecimal.divideRoundIfNecessary(
                five, four, hdp, rm);
        BigDecimal latSubtractLat0 = lat.subtract(lat0);
        BigDecimal Ma = (BigDecimal.ONE.add(n.add(fivebyfour.multiply(n2)).add(fivebyfour.multiply(n3)))).multiply(latSubtractLat0);
        //double Mb = (3 * n + 3 * n * n + (21D / 8D) * n3) * Math.sin(lat - lat0) * Math.cos(lat + lat0);
        BigDecimal twentyOneOverEight = Math_BigDecimal.divideRoundIfNecessary(
                BigDecimal.valueOf(21), BigDecimal.valueOf(8), hdp, rm);
        BigDecimal sinLatSubtractLat0 = Math_BigDecimal.sin(latSubtractLat0, bd, hdp, rm);
        BigDecimal latAddLat0 = lat.add(lat0);
        BigDecimal cosLatAddLat0 = Math_BigDecimal.cos(latAddLat0, bd, hdp, rm);
        BigDecimal Mb = (((three.multiply(n)).add((three.multiply(n2)))).add(twentyOneOverEight.multiply(n3))).multiply(sinLatSubtractLat0.multiply(cosLatAddLat0));
        //double Mc = ((15D / 8D) * n2 + (15D / 8D) * n3) * Math.sin(2D * (lat - lat0)) * Math.cos(2D * (lat + lat0));
        BigDecimal fifteenOverEight = Math_BigDecimal.divideRoundIfNecessary(
                BigDecimal.valueOf(15), BigDecimal.valueOf(8), hdp, rm);
        BigDecimal sin2LatSubtractLat0 = Math_BigDecimal.sin(
                BigDecimal.valueOf(2).multiply(latSubtractLat0), bd, hdp, rm);
        BigDecimal cos2LatAddLat0 = Math_BigDecimal.cos(
                BigDecimal.valueOf(2).multiply(latAddLat0), bd, hdp, rm);
        BigDecimal Mc = ((fifteenOverEight.multiply(n2)).add(fifteenOverEight.multiply(n3))).multiply(sin2LatSubtractLat0).multiply(cos2LatAddLat0);
        //double Md = (35D / 24D) * n3 * Math.sin(3 * (lat - lat0)) * Math.cos(3D * (lat + lat0));
        BigDecimal thirtyFiveOverTwentyFour = Math_BigDecimal.divideRoundIfNecessary(
                BigDecimal.valueOf(35), BigDecimal.valueOf(24), hdp, rm);
        BigDecimal sin3LatSubtractLat0 = Math_BigDecimal.sin(
                BigDecimal.valueOf(3).multiply(latSubtractLat0), bd, hdp, rm);
        BigDecimal cos3LatAddLat0 = Math_BigDecimal.cos(
                BigDecimal.valueOf(3).multiply(latAddLat0), bd, hdp, rm);
        BigDecimal Md = thirtyFiveOverTwentyFour.multiply(n3).multiply(sin3LatSubtractLat0).multiply(cos3LatAddLat0);
        //double M = b * F0 * (Ma - Mb + Mc - Md); // meridional arc
        BigDecimal M = b.multiply(F0).multiply(Ma.subtract(Mb).add(Mc).subtract(Md)); // meridional arc
        //double cos3lat = cosLat * cosLat * cosLat;
        BigDecimal cos3lat = cosLat.multiply(cosLat).multiply(cosLat);
        //double cos5lat = cos3lat * cosLat * cosLat;
        BigDecimal cos5lat = cos3lat.multiply(cosLat).multiply(cosLat);
        //double tan2lat = Math.tan(lat) * Math.tan(lat);
        BigDecimal tanLat = Math_BigDecimal.tan(
                lat,
                bd,
                hdp,
                rm);
        BigDecimal tan2lat = tanLat.multiply(tanLat);
        //double tan4lat = tan2lat * tan2lat;
        BigDecimal tan4lat = tan2lat.multiply(tan2lat);

        //double I = M + N0;
        BigDecimal I = M.add(N0);
        //double II = (nu / 2D) * sinLat * cosLat;
        BigDecimal nuBy2 = Math_BigDecimal.divideRoundIfNecessary(nu,
                BigDecimal.valueOf(2), hdp, rm);
        BigDecimal II = nuBy2.multiply(sinLat).multiply(cosLat);
        //double III = (nu / 24D) * sinLat * cos3lat * (5 - tan2lat + 9 * eta2);
        BigDecimal nuBy24 = Math_BigDecimal.divideRoundIfNecessary(nu,
                BigDecimal.valueOf(24), hdp, rm);
        BigDecimal III = nuBy24.multiply(sinLat).multiply(cos3lat).multiply(BigDecimal.valueOf(5).subtract(tan2lat).add(BigDecimal.valueOf(9).multiply(eta2)));
        //double IIIA = (nu / 720D) * sinLat * cos5lat * (61 - 58 * tan2lat + tan4lat);
        BigDecimal nuBy720 = Math_BigDecimal.divideRoundIfNecessary(nu,
                BigDecimal.valueOf(720), hdp, rm);
        BigDecimal IIIA = nuBy720.multiply(sinLat).multiply(cos5lat).multiply(BigDecimal.valueOf(61).subtract((BigDecimal.valueOf(58).multiply(tan2lat)).add(tan4lat)));
        //double IV = nu * cosLat;
        BigDecimal IV = nu.multiply(cosLat);
        //double V = (nu / 6D) * cos3lat * (nu / rho - tan2lat);
        BigDecimal nuBy6 = Math_BigDecimal.divideRoundIfNecessary(
                nu, BigDecimal.valueOf(6), hdp, rm);
        BigDecimal nuByRhoSubtractTan2Lat = Math_BigDecimal.divideRoundIfNecessary(
                nu, rho.subtract(tan2lat), hdp, rm);
        BigDecimal V = nuBy6.multiply(cos3lat).multiply(nuByRhoSubtractTan2Lat);
        //double VI = (nu / 120D) * cos5lat * (5 - 18 * tan2lat + tan4lat + 14 * eta2 - 58 * tan2lat * eta2);
        BigDecimal nuBy120 = Math_BigDecimal.divideRoundIfNecessary(
                nu, BigDecimal.valueOf(120), hdp, rm);
        BigDecimal VI = nuBy120.multiply(cos5lat).multiply(BigDecimal.valueOf(5).subtract(BigDecimal.valueOf(18).multiply(tan2lat)).add(tan4lat).add(BigDecimal.valueOf(14).multiply(eta2)).subtract(BigDecimal.valueOf(58).multiply(tan2lat).multiply(eta2)));

        //double dLon = lon - lon0;
        BigDecimal dLon = lon.subtract(lon0);
        //double dLon2 = dLon * dLon;
        BigDecimal dLon2 = dLon.multiply(dLon);
        //double dLon3 = dLon2 * dLon;
        BigDecimal dLon3 = dLon2.multiply(dLon);
        //double dLon4 = dLon3 * dLon;
        BigDecimal dLon4 = dLon3.multiply(dLon);
        //double dLon5 = dLon4 * dLon;
        BigDecimal dLon5 = dLon4.multiply(dLon);
        //double dLon6 = dLon5 * dLon;
        BigDecimal dLon6 = dLon5.multiply(dLon);

        //double N = I + II * dLon2 + III * dLon4 + IIIA * dLon6;
        BigDecimal N = I.add(II.multiply(dLon2)).add(III.multiply(dLon4)).add(IIIA.multiply(dLon6));
        //double E = E0 + IV * dLon + V * dLon3 + VI * dLon5;
        BigDecimal E = E0.add(IV.multiply(dLon)).add(V.multiply(dLon3)).add(VI.multiply(dLon5));
        //System.out.println("E " + E + ", N " + N);
        result[0] = Math_BigDecimal.roundIfNecessary(E, dp, rm);
        result[1] = Math_BigDecimal.roundIfNecessary(N, dp, rm);
        return result;
        //return new OsGridRef(E, N);
    }

    /**
     * Convert Ordnance Survey easting-northing coordinate to a WGS84
     * latitude-longitude coordinate
     *
     * @param easting The easting to be converted
     * @param northing The northing to be converted
     * @return result double[2] where result[0] is latitude and result[1] is
     * longitude
     */
    public static double[] osgb2latlon(double easting, double northing) {
        return osgb2latlon(easting, northing, false);
    }

    /**
     * Convert Ordnance Survey easting-northing coordinate to a WGS84
     * latitude-longitude coordinate
     *
     * @param easting The easting to be converted
     * @param northing The northing to be converted
     * @param verbose If verbose is true then intermediate calculations are
     * printed to std.out.
     * @return result double[2] where result[0] is latitude and result[1] is
     * longitude
     */
    public static double[] osgb2latlon(double easting, double northing,
            boolean verbose) {
        double[] result = new double[2];
        double a = 6377563.396;
        double b = 6356256.910;            // Airy 1830 major & minor semi-axes
        double F0 = 0.9996012717;          // NatGrid scale factor on central meridian
        double lat0 = toRadians(49);
        double lon0 = toRadians(-2);       // NatGrid true origin is 49ºN,2ºW
        double N0 = -100000;
        double E0 = 400000;                // northing & easting of true origin, metres
        double e2 = 1 - (b * b) / (a * a); // eccentricity squared
        //System.out.println("e2 " + e2);
        double n = (a - b) / (a + b);
        //System.out.println("n " + n);
        double n2 = n * n;
        double n3 = n * n * n;

        double lat = lat0;
        double M = 0;
        do {
            lat = (northing - N0 - M) / (a * F0) + lat;
            //System.out.println("lat " + lat);
            double Ma = (1 + n + (5 / 4) * n2 + (5 / 4) * n3) * (lat - lat0);
            //System.out.println("Ma " + Ma);
            double Mb = (3 * n + 3 * n * n + (21 / 8) * n3) * Math.sin(lat - lat0) * Math.cos(lat + lat0);
            //System.out.println("Mb " + Mb);
            double Mc = ((15 / 8) * n2 + (15 / 8) * n3) * Math.sin(2 * (lat - lat0)) * Math.cos(2 * (lat + lat0));
            //System.out.println("Mc " + Mc);
            double Md = (35 / 24) * n3 * Math.sin(3 * (lat - lat0)) * Math.cos(3 * (lat + lat0));
            //System.out.println("Md " + Md);
            M = b * F0 * (Ma - Mb + Mc - Md);    // meridional arc
            //System.out.println("M " + M);
            //break;
            //System.out.println("northing - N0 - M " + (northing - N0 - M));

        } while (northing - N0 - M >= 0.0001);  // ie until < 0.01mm

        double cosLat = Math.cos(lat);
        double sinLat = Math.sin(lat);
        double sin2Lat = sinLat * sinLat;
        double nu = a * F0 / Math.sqrt(1 - e2 * sin2Lat);                 // transverse radius of curvature
        double rho = a * F0 * (1 - e2) / Math.pow(1 - e2 * sin2Lat, 1.5); // meridional radius of curvature
        double eta2 = nu / rho - 1;

        double tanLat = Math.tan(lat);
        double tan2lat = tanLat * tanLat;
        double tan4lat = tan2lat * tan2lat;
        double tan6lat = tan4lat * tan2lat;
        double secLat = 1 / cosLat;
        double nu3 = nu * nu * nu;
        double nu5 = nu3 * nu * nu;
        double nu7 = nu5 * nu * nu;
        double VII = tanLat / (2 * rho * nu);
        double VIII = tanLat / (24 * rho * nu3) * (5 + 3 * tan2lat + eta2 - 9 * tan2lat * eta2);
        double IX = tanLat / (720 * rho * nu5) * (61 + 90 * tan2lat + 45 * tan4lat);
        double X = secLat / nu;
        double XI = secLat / (6 * nu3) * (nu / rho + 2 * tan2lat);
        double XII = secLat / (120 * nu5) * (5 + 28 * tan2lat + 24 * tan4lat);
        double XIIA = secLat / (5040 * nu7) * (61 + 662 * tan2lat + 1320 * tan4lat + 720 * tan6lat);
        double dE = (easting - E0);
        double dE2 = dE * dE;
        double dE3 = dE2 * dE;
        double dE4 = dE2 * dE2;
        double dE5 = dE3 * dE2;
        double dE6 = dE4 * dE2;
        double dE7 = dE5 * dE2;
        lat = lat - VII * dE2 + VIII * dE4 - IX * dE6;
        double lon = lon0 + X * dE - XI * dE3 + XII * dE5 - XIIA * dE7;
        result[0] = toDegrees(lat);
        result[1] = toDegrees(lon);
        if (verbose) {
            System.out.println("lat " + lat);
            System.out.println("cosLat " + cosLat);
            System.out.println("sinLat " + sinLat);
            System.out.println("sin2Lat " + sin2Lat);
            System.out.println("nu " + nu);
            System.out.println("rho " + rho);
            System.out.println("eta2 " + eta2);
            System.out.println("tanLat " + tanLat);
            System.out.println("tan6lat " + tan6lat);
            System.out.println("VII " + VII);
            System.out.println("VIII " + VIII);
            System.out.println("IX " + IX);
            System.out.println("X " + X);
            System.out.println("XI " + XI);
            System.out.println("XII " + XII);
            System.out.println("XIIA " + XIIA);
        }
        //System.out.println("lat " + lat + ", lon " + lon);
        return result;
    }

    /**
     * Convert Ordnance Survey easting-northing coordinate to a WGS84
     * latitude-longitude coordinate
     *
     * @param easting The easting to be converted
     * @param northing The northing to be converted
     * @param dp The number of decimal places the result must be correct to.
     * @param rm The Rounding Mode to use for calculations.
     * @return result double[2] where result[0] is latitude and result[1] is
     * longitude
     */
    public BigDecimal[] osgb2latlon(BigDecimal easting, BigDecimal northing,
            int dp, RoundingMode rm) {
        return osgb2latlon(easting, northing, dp, rm, false);
    }

    /**
     * Convert Ordnance Survey easting-northing coordinate to a WGS84
     * latitude-longitude coordinate
     *
     * @param easting The easting to be converted
     * @param northing The northing to be converted
     * @param dp The number of decimal places the result must be correct to.
     * @param rm The Rounding Mode to use for calculations.
     * @param verbose If verbose is true then intermediate calculations are
     * printed to std.out.
     * @return double[2] r: where r[0] is latitude and r[1] is longitude
     */
    public BigDecimal[] osgb2latlon(BigDecimal easting, BigDecimal northing,
            int dp, RoundingMode rm, boolean verbose) {
        //int doubleDecimalPlaces = decimalPlaces * 2;
        //int tripleDecimalPlaces = decimalPlaces * 3;
        // Higher decimal place precision
        int hdp = Math.max(dp * 10, 100);
        BigDecimal precision = new BigDecimal(BigInteger.ONE, dp);
        BigDecimal[] r = new BigDecimal[2];
        /*
         * Airy 1830 major semi-axis
         */
        BigDecimal a = new BigDecimal("6377563.396");
        /*
         * Airy 1830 minor semi-axis
         */
        //BigDecimal b = new BigDecimal("6356256.910"); 
        BigDecimal b = new BigDecimal("6356256.909");
        /*
         * NatGrid scale factor on central meridian
         */
        BigDecimal F0 = new BigDecimal("0.9996012717");
//        BigDecimal tenPowow = Math_BigDecimal.power(
//                BigDecimal.TEN, 
//                new BigDecimal("0.48401603"),
//                tripleDecimalPlaces, 
//                aRoundingMode);
//        BigDecimal F02 = Math_BigDecimal.divideRoundIfNecessary(
//                tenPowow, 
//                BigDecimal.TEN,
//                tripleDecimalPlaces, 
//                aRoundingMode);
//        BigDecimal F0 = Math_BigDecimal.log(
//                10,
//                new BigDecimal("0.9998268").subtract(BigDecimal.ONE),
//                decimalPlaces,
//                aRoundingMode);
        // NatGrid true origin is 49ºN,2ºW
        BigDecimal lat0 = toRadians(new BigDecimal("49"), hdp, rm);
        //System.out.println("lat0 " + lat0);
        BigDecimal lon0 = toRadians(new BigDecimal("-2"), hdp, rm);
        // northing & easting of true origin, metres
        BigDecimal N0 = new BigDecimal("-100000");
        BigDecimal E0 = new BigDecimal("400000");
        //double e2 = 1 - (b * b) / (a * a); // eccentricity squared
        BigDecimal b2 = b.multiply(b);
        BigDecimal a2 = a.multiply(a);
        BigDecimal b2overa2 = Math_BigDecimal.divideRoundIfNecessary(b2, a2, hdp, rm);
        BigDecimal e2 = BigDecimal.ONE.subtract(b2overa2); // eccentricity squared
        //System.out.println("e2 " + e2);
        //double n = (a - b) / (a + b);
        BigDecimal asubtractb = a.subtract(b);
        BigDecimal aaddb = a.add(b);
        BigDecimal n = Math_BigDecimal.divideRoundIfNecessary(asubtractb, aaddb, hdp, rm);
        //System.out.println("n " + n);
        //double n2 = n * n;
        BigDecimal n2 = n.multiply(n);
        //double n3 = n * n * n;
        BigDecimal n3 = n2.multiply(n);

        Math_BigDecimal bd = new Math_BigDecimal(1000);
        BigDecimal two = BigDecimal.valueOf(2);
        BigDecimal three = BigDecimal.valueOf(3);
        BigDecimal fiveBy4 = Math_BigDecimal.divideRoundIfNecessary(
                BigDecimal.valueOf(5), BigDecimal.valueOf(4), hdp, rm);
        BigDecimal twenty1By8 = Math_BigDecimal.divideRoundIfNecessary(
                BigDecimal.valueOf(21), BigDecimal.valueOf(8), hdp, rm);
        BigDecimal fifteenBy8 = Math_BigDecimal.divideRoundIfNecessary(
                BigDecimal.valueOf(15), BigDecimal.valueOf(8), hdp, rm);
        BigDecimal thirty5Over24 = Math_BigDecimal.divideRoundIfNecessary(
                BigDecimal.valueOf(35), BigDecimal.valueOf(24), hdp, rm);
        BigDecimal lat = new BigDecimal(lat0.toString());
        BigDecimal previousComparitor = new BigDecimal("100");
        BigDecimal M = BigDecimal.ZERO;
        boolean loop;
        do {
            //lat = (northing - N0 - M) / (a * F0) + lat;
            lat = Math_BigDecimal.divideRoundIfNecessary(
                    northing.subtract(N0).subtract(M),
                    (a.multiply(F0)), hdp, rm).add(lat);
            //System.out.println("lat " + lat);
            //double Ma = (1 + n + (5 / 4) * n2 + (5 / 4) * n3) * (lat - lat0);
            BigDecimal latSubtractLat0 = lat.subtract(lat0);
            BigDecimal Ma = (BigDecimal.ONE.add(n).add(fiveBy4.multiply(n2)).add(fiveBy4.multiply(n3))).multiply(latSubtractLat0);
            //System.out.println("Ma " + Ma);
            //double Mb = (3 * n + 3 * n * n + (21 / 8) * n3) * Math.sin(lat - lat0) * Math.cos(lat + lat0);
            BigDecimal sinLatSubtractLat0 = Math_BigDecimal.sin(
                    latSubtractLat0, bd, hdp, rm);
            BigDecimal latAddLat0 = lat.add(lat0);
            BigDecimal cosLatAddLat0 = Math_BigDecimal.cos(latAddLat0, bd, hdp, rm);
            BigDecimal Mb = (three.multiply(n).add(three.multiply(n2)).add(twenty1By8.multiply(n3))).multiply(sinLatSubtractLat0).multiply(cosLatAddLat0);
            //System.out.println("Mb " + Mb);
            //double Mc = ((15 / 8) * n2 + (15 / 8) * n3) * Math.sin(2 * (lat - lat0)) * Math.cos(2 * (lat + lat0));
            BigDecimal sin2LatSubtractLat0 = Math_BigDecimal.sin(
                    two.multiply(latSubtractLat0), bd, hdp, rm);
            BigDecimal cos2LatAddLat0 = Math_BigDecimal.cos(
                    two.multiply(latAddLat0), bd, hdp, rm);
            BigDecimal Mc = ((fifteenBy8.multiply(n2)).add(fifteenBy8.multiply(n3))).multiply(sin2LatSubtractLat0).multiply(cos2LatAddLat0);
            //System.out.println("Mc " + Mc);
            //double Md = (35 / 24) * n3 * Math.sin(3 * (lat - lat0)) * Math.cos(3 * (lat + lat0));
            BigDecimal sin3LatSubtractLat0 = Math_BigDecimal.sin(
                    three.multiply(latSubtractLat0), bd, hdp, rm);
            BigDecimal cos3LatAddLat0 = Math_BigDecimal.cos(
                    three.multiply(latAddLat0), bd, hdp, rm);
            BigDecimal Md = thirty5Over24.multiply(n3).multiply(sin3LatSubtractLat0).multiply(cos3LatAddLat0);
            //System.out.println("Md " + Md);
            //M = b * F0 * (Ma - Mb + Mc - Md);    // meridional arc
            M = b.multiply(F0).multiply(Ma.subtract(Mb).add(Mc).subtract(Md));    // meridional arc
            //System.out.println("M " + M);
            BigDecimal comparitor = northing.subtract(N0).subtract(M);
//            if (comparitor.compareTo(BigDecimal.ZERO) == -1) {
//                comparitor = comparitor.negate();
//            }
            loop = comparitor.compareTo(precision) == 1;
            if (loop) {
                //System.out.println(comparitor + " > " + precision);
                if (previousComparitor.compareTo(comparitor) == 0) {
                    //System.out.println("(previousLat.compareTo(lat) == 0) breaking out of loop");
                    loop = false;
                }
                previousComparitor = new BigDecimal(comparitor.toString());
            } else {
                //System.out.println(comparitor + " <= " + precision);
            }
        } while (loop);  // ie until < 0.01mm

        //double cosLat = Math.cos(lat);
        BigDecimal cosLat = Math_BigDecimal.cos(lat, bd, hdp, rm);
        //double sinLat = Math.sin(lat);
        BigDecimal sinLat = Math_BigDecimal.sin(lat, bd, hdp, rm);
        //double nu = a * F0 / Math.sqrt(1 - e2 * sinLat * sinLat);                 // transverse radius of curvature
        BigDecimal sin2Lat = sinLat.multiply(sinLat);
        BigDecimal splurge = Math_BigDecimal.sqrt(
                BigDecimal.ONE.subtract(e2.multiply(sin2Lat)), hdp, rm);
        BigDecimal aMultiplyF0 = a.multiply(F0);
        // transverse radius of curvature
        BigDecimal nu = Math_BigDecimal.divideRoundIfNecessary(aMultiplyF0,
                splurge, hdp, rm);
        //double rho = a * F0 * (1 - e2) / Math.pow(1 - e2 * sinLat * sinLat, 1.5); // meridional radius of curvature
        //BigDecimal sqrtSplurge = Math_BigDecimal.sqrt(splurge, hdp, rm);
        //BigDecimal denom = splurge.multiply(sqrtSplurge);
        BigDecimal denom = Math_BigDecimal.power(splurge, new BigDecimal(1.5),
                hdp, rm);
        // meridional radius of curvature
        BigDecimal rho = Math_BigDecimal.divideRoundIfNecessary(
                aMultiplyF0.multiply(BigDecimal.ONE.subtract(e2)), denom, hdp, rm);
        //double eta2 = nu / rho - 1;
        BigDecimal eta2 = Math_BigDecimal.divideRoundIfNecessary(nu, rho, hdp,
                rm).subtract(BigDecimal.ONE);
        //double tanLat = Math.tan(lat);
        BigDecimal tanLat = Math_BigDecimal.tan(lat, bd, hdp, rm);
        //double tan2lat = tanLat * tanLat;
        BigDecimal tan2Lat = tanLat.multiply(tanLat);
        //double tan4lat = tan2lat * tan2lat;
        BigDecimal tan4Lat = tan2Lat.multiply(tan2Lat);
        //double tan6lat = tan4lat * tan2lat;
        BigDecimal tan6Lat = tan4Lat.multiply(tan2Lat);
        //double secLat = 1 / cosLat;
        BigDecimal secLat = Math_BigDecimal.divideRoundIfNecessary(
                BigDecimal.ONE, cosLat, hdp, rm);
        //double nu3 = nu * nu * nu;
        BigDecimal nu2 = nu.multiply(nu);
        BigDecimal nu3 = nu.multiply(nu2);
        //double nu5 = nu3 * nu * nu;
        BigDecimal nu5 = nu3.multiply(nu2);
        //double nu7 = nu5 * nu * nu;
        BigDecimal nu7 = nu5.multiply(nu2);
        //double VII = tanLat / (2 * rho * nu);
        BigDecimal VII = Math_BigDecimal.divideRoundIfNecessary(tanLat,
                two.multiply(rho).multiply(nu), hdp, rm);
        //double VIII = tanLat / (24 * rho * nu3) * (5 + 3 * tan2lat + eta2 - 9 * tan2lat * eta2);
        BigDecimal VIII = Math_BigDecimal.divideRoundIfNecessary(tanLat,
                (BigDecimal.valueOf(24).multiply(rho).multiply(nu3)).multiply(BigDecimal.valueOf(5).add(three.multiply(tan2Lat)).add(eta2).subtract(BigDecimal.valueOf(9).multiply(tan2Lat).multiply(eta2))),
                hdp, rm);
        //double IX = tanLat / (720 * rho * nu5) * (61 + 90 * tan2lat + 45 * tan4lat);
        BigDecimal IX = Math_BigDecimal.divideRoundIfNecessary(tanLat,
                (BigDecimal.valueOf(720).multiply(rho).multiply(nu5)).multiply(BigDecimal.valueOf(61).add(BigDecimal.valueOf(90).multiply(tan2Lat)).add(BigDecimal.valueOf(45).multiply(tan4Lat))),
                hdp, rm);
        //double X = secLat / nu;
        BigDecimal X = Math_BigDecimal.divideRoundIfNecessary(secLat, nu, hdp, rm);
        //double XI = secLat / (6 * nu3) * (nu / rho + 2 * tan2lat);
        BigDecimal nuByRho = Math_BigDecimal.divideRoundIfNecessary(nu, rho, hdp, rm);
        BigDecimal XI = Math_BigDecimal.divideRoundIfNecessary(secLat,
                BigDecimal.valueOf(6).multiply(nu3).multiply(nuByRho.add(two.multiply(tan2Lat))),
                hdp, rm);
        //double XII = secLat / (120 * nu5) * (5 + 28 * tan2lat + 24 * tan4lat);
        BigDecimal XII = Math_BigDecimal.divideRoundIfNecessary(secLat,
                BigDecimal.valueOf(120).multiply(nu5).multiply(BigDecimal.valueOf(5).add(BigDecimal.valueOf(28).multiply(tan2Lat).add(BigDecimal.valueOf(24).multiply(tan4Lat)))),
                hdp, rm);
        //double XIIA = secLat / (5040 * nu7) * (61 + 662 * tan2lat + 1320 * tan4lat + 720 * tan6lat);
        BigDecimal XIIA = Math_BigDecimal.divideRoundIfNecessary(secLat,
                BigDecimal.valueOf(5040).multiply(nu7).multiply(BigDecimal.valueOf(61).add(BigDecimal.valueOf(662).multiply(tan2Lat).add(BigDecimal.valueOf(1320).multiply(tan4Lat).add(BigDecimal.valueOf(720).multiply(tan6Lat))))),
                hdp, rm);
        //double dE = (easting - E0);
        BigDecimal dE = easting.subtract(E0);
        //double dE2 = dE * dE;
        BigDecimal dE2 = dE.multiply(dE);
        //double dE3 = dE2 * dE;
        BigDecimal dE3 = dE2.multiply(dE);
        //double dE4 = dE2 * dE2;
        BigDecimal dE4 = dE3.multiply(dE);
        //double dE5 = dE3 * dE2;
        BigDecimal dE5 = dE4.multiply(dE);
        //double dE6 = dE4 * dE2;
        BigDecimal dE6 = dE5.multiply(dE);
        //double dE7 = dE5 * dE2;
        BigDecimal dE7 = dE6.multiply(dE);
        //lat = lat - VII * dE2 + VIII * dE4 - IX * dE6;
        lat = lat.subtract(VII.multiply(dE2)).add(VIII.multiply(dE4)).subtract(IX.multiply(dE6));
        //double lon = lon0 + X * dE - XI * dE3 + XII * dE5 - XIIA * dE7;
        BigDecimal lon = lon0.add(X.multiply(dE)).subtract(XI.multiply(dE3)).add(XII.multiply(dE5)).subtract(XIIA.multiply(dE7));
        r[0] = toDegrees(lat, dp, rm);
        r[1] = toDegrees(lon, dp, rm);
        if (verbose) {
            System.out.println("lat " + lat);
            System.out.println("cosLat " + cosLat);
            System.out.println("sinLat " + sinLat);
            System.out.println("sin2Lat " + sin2Lat);
            System.out.println("nu " + nu);
            System.out.println("rho " + rho);
            System.out.println("eta2 " + eta2);
            System.out.println("tanLat " + tanLat);
            System.out.println("tan6Lat " + tan6Lat);
            System.out.println("VII " + VII);
            System.out.println("VIII " + VIII);
            System.out.println("IX " + IX);
            System.out.println("X " + X);
            System.out.println("XI " + XI);
            System.out.println("XII " + XII);
            System.out.println("XIIA " + XIIA);
        }
        //System.out.println("lat " + lat + ", lon " + lon);
        return r;
    }

    /**
     * Calculate at return the x*Math.PI/180.
     *
     * @param x The angle in decimal degrees to convert to radians.
     * @return The angle of x in radians.
     */
    public static double toRadians(double x) {
        return x * Math.PI / 180D;
    }

    /**
     * Calculate at return the x*Pi/180.
     *
     * @param x The angle in decimal degrees to convert to radians.
     * @param dp The number of decimal places the result should be correct to.
     * @param rm The rounding mode to use if necessary.
     * @return The angle of x in radians.
     */
    public BigDecimal toRadians(BigDecimal x, int dp, RoundingMode rm) {
        return Math_BigDecimal.divideRoundIfNecessary(x.multiply(getPi()),
                BigDecimal.valueOf(180L), dp, rm);
    }

    /**
     * Calculate at return the x*180/Pi.
     *
     * @param x The angle in radians to convert to decimal degrees.
     * @return The angle of x in decimal degrees.
     */
    public static double toDegrees(double x) {
        return x * 180D / Math.PI;
    }

    /**
     * Calculate at return the x*180/Pi.
     *
     * @param x The angle in radians to convert to decimal degrees.
     * @param dp The number of decimal places the result should be correct to.
     * @param rm The rounding mode to use if necessary.
     * @return The angle of x in decimal degrees.
     */
    public BigDecimal toDegrees(BigDecimal x, int dp, RoundingMode rm) {
        return Math_BigDecimal.divideRoundIfNecessary(
                x.multiply(BigDecimal.valueOf(180L)), getPi(), dp, rm);
    }

}
