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
package uk.ac.leeds.ccg.v3d.projection.d;

import uk.ac.leeds.ccg.math.geometry.Math_AngleDouble;
import uk.ac.leeds.ccg.v3d.core.d.V3D_Environment_d;
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
public class LatLon2ECF_d {

    /**
     * 
     * @param lat The latitude in degrees from -90 to 90.
     * @param lon The longitude in degrees from -180 to 180. 
     * @param h The height above sea level in metres.
     * @return A point in ECF.
     */
    public static V3D_Point_d getPoint(V3D_Environment_d env, double lat, 
            double lon, double h) {
        double re = 6378137.0;
        double rp = 6356752.314245;
        //double f = 1d / 298.257223563;
        double rp2dre2 = (rp * rp) / (re * re);
        double e2 = 1d - rp2dre2;
        double latr = Math_AngleDouble.toRadians(lat);
        double lonr = Math_AngleDouble.toRadians(lon);
        double slatr = Math.sin(latr);
        double clatr = Math.cos(latr);
        double c2latr = Math.cos(2d * latr);
        double sin2latr = (1d - c2latr) / 2d;
        double slonr = Math.sin(lonr);
        double clonr = Math.cos(lonr);
        //double Np = re / Math.sqrt(1d - (e2 * slatr * slatr));
        double Np = re / Math.sqrt(1d - (e2 * sin2latr));
        double Nph = Np + h;
        double x = Nph * clatr * clonr;
        double y = Nph * clatr * slonr;
        double z = ((1d - e2) * Nph) * slatr;
        //double z = (Nph - (e2 * Nph)) * slatr;
        return new V3D_Point_d(env, x,y,z);
    }
    
    public static void main(String[] args) {
        V3D_Environment_d env = new V3D_Environment_d();
        double lat;
        double lon;
        double height = 0;
//        lat = 0;
//        lon = 0;
//        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
//        lon = 90;
//        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
//        lon = 180;
//        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
//        lon = 270;
//        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
        lat = 45;
        lon = 0;
        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
        lon = 90;
        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
        lon = 180;
        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
        lon = 270;
        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
        lat = -45;
        lon = 0;
        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
        lon = 90;
        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
        lon = 180;
        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
        lon = 270;
        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
        lat = 90;
        lon = 0;
        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
        lon = 90;
        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
        lon = 180;
        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
        lon = 270;
        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
        lat = -90;
        lon = 0;
        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
        lon = 90;
        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
        lon = 180;
        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
        lon = 270;
        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());
        lat = 40;
        lon = 70;
        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());       
        lat = 40;
        lon = -110;
        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());       
        lat = -140;
        lon = -110;
        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());       
        lat = -140;
        lon = 70;
        System.out.println("lat=" + lat + ", lon=" + lon + " height=" + height + " -> XYZ=" + getPoint(env, lat, lon, height).toString());       
    }
}
