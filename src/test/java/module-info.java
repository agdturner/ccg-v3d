/*
 * Copyright 2023 Centre for Computational Geography.
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

module uk.ac.leeds.ccg.v3d.test {
    requires uk.ac.leeds.ccg.v3d;
    //requires uk.ac.leeds.ccg.v3d.geometry;
    
    requires org.junit.jupiter.api;
    requires org.junit.jupiter.engine;
    requires org.junit.jupiter.params;

    opens uk.ac.leeds.ccg.v3d.geometry.test to org.junit.platform.commons;
    opens uk.ac.leeds.ccg.v3d.geometry.d.test to org.junit.platform.commons;
}
