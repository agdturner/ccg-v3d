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

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author geoagdt
 */
public class Vector_OSBGtoLatLonTest {

    public Vector_OSBGtoLatLonTest() {
    }

    @BeforeAll
    public static void setUpClass() throws Exception {
    }

    @AfterAll
    public static void tearDownClass() throws Exception {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }
    
//    @Test
//    public void run() {
//        //double lat = 53.807882;
//        //double lon = -1.557103;
//        //run1(lat,lon);
//        double easting = 429162;
//        double northing = 434735;
//        run2(easting,northing);
//    }
        
//    public void run1(
//            double lat,
//            double lon) {
//        System.out.println("lat " + lat + ", lon " + lon);
//        double[] eastingNorthing = latlon2osgb(lat, lon);
//        System.out.println(
//                "easting " + eastingNorthing[0] + ", "
//                + "northing " + eastingNorthing[1]);
//        double[] latLon = osgb2latlon(eastingNorthing[0], eastingNorthing[1]);
//        System.out.println("lat " + latLon[0] + ", lon " + latLon[1]);
//
//        RoundingMode aRoundingMode = RoundingMode.HALF_UP;
//        //RoundingMode aRoundingMode = RoundingMode.HALF_EVEN;
//        //RoundingMode aRoundingMode = RoundingMode.HALF_DOWN;
//        //RoundingMode aRoundingMode = RoundingMode.DOWN;
//
//        BigDecimal latBigDecimal = BigDecimal.valueOf(lat);
//        BigDecimal lonBigDecimal = BigDecimal.valueOf(lon);
//        System.out.println("latBigDecimal " + latBigDecimal + ", lonBigDecimal " + lonBigDecimal);
//        //int decimalPlaces = 10;
//        //for (int decimalPlaces = 10; decimalPlaces < 100; decimalPlaces += 10) {
//        for (int decimalPlaces = 4; decimalPlaces < 32; decimalPlaces += 4) {
//            System.out.println("decimalPlaces " + decimalPlaces);
//            BigDecimal[] eastingNorthingBigDecimal = latlon2osgb(
//                    latBigDecimal,
//                    lonBigDecimal,
//                    decimalPlaces,
//                    aRoundingMode);
//            System.out.println(
//                    "easting " + eastingNorthingBigDecimal[0] + ", "
//                    + "northing " + eastingNorthingBigDecimal[1]);
//            BigDecimal[] latLonBigDecimal = osgb2latlon(
//                    eastingNorthingBigDecimal[0],
//                    eastingNorthingBigDecimal[1],
//                    decimalPlaces,
//                    aRoundingMode);
//            System.out.println("lat " + latLonBigDecimal[0] + ", lon " + latLonBigDecimal[1]);
//
//        }
//    }
//
//    public void run2(
//            double easting,
//            double northing) {
//        System.out.println(
//                    "easting " + easting + ", "
//                    + "northing " + northing);
//        double[] latLon = osgb2latlon(easting, northing);
//        System.out.println(
//                    "lat " + latLon[0] + ", "
//                    + "lon " + latLon[1]);
//            
//        RoundingMode aRoundingMode = RoundingMode.HALF_UP;
//        //RoundingMode aRoundingMode = RoundingMode.HALF_EVEN;
//        //RoundingMode aRoundingMode = RoundingMode.HALF_DOWN;
//        //RoundingMode aRoundingMode = RoundingMode.DOWN;
//
//        BigDecimal[] eastingNorthingBigDecimal = new BigDecimal[2];
//        eastingNorthingBigDecimal[0] = BigDecimal.valueOf(easting);
//        eastingNorthingBigDecimal[1] = BigDecimal.valueOf(northing);
//        
//        /**
//         * http://www.ordnancesurvey.co.uk/gps/transformation
//         * 53.808111 lat
//         * -1.558647 lon
//         * 429162.016 easting
//         * 434735.009 northing
//         * 53.808111 lat
//         * -1.558647 lon
//        */
//        for (int decimalPlaces = 4; decimalPlaces < 32; decimalPlaces += 4) {
//            System.out.println("decimalPlaces " + decimalPlaces);
//            BigDecimal[] latLonBigDecimal = osgb2latlon(
//                    eastingNorthingBigDecimal[0],
//                    eastingNorthingBigDecimal[1],
//                    decimalPlaces,
//                    aRoundingMode);
//            System.out.println("lat " + latLonBigDecimal[0] + ", lon " + latLonBigDecimal[1]);
//            BigDecimal[] eastingNorthingBigDecimal2 = latlon2osgb(
//                    latLonBigDecimal[0],
//                    latLonBigDecimal[1],
//                    decimalPlaces,
//                    aRoundingMode);
//            System.out.println(
//                    "easting " + eastingNorthingBigDecimal2[0] + ", "
//                    + "northing " + eastingNorthingBigDecimal2[1]);
//
//        }
//    }
    

}
