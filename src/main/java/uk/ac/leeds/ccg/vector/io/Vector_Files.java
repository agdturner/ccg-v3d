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
package uk.ac.leeds.ccg.vector.io;

import java.io.IOException;
import java.nio.file.Paths;
import uk.ac.leeds.ccg.generic.io.Generic_Defaults;
import uk.ac.leeds.ccg.generic.io.Generic_Files;
import uk.ac.leeds.ccg.grids.io.Grids_Files;
import uk.ac.leeds.ccg.vector.core.Vector_Strings;

/**
 * Vector Files.
 * 
 * @author Andy Turner
 * @version 1.0.0
 */
public class Vector_Files extends Generic_Files {

    protected final Grids_Files gf;

    /**
     * Creates a new instance.
     *
     * @throws IOException If encountered.
     */
    public Vector_Files() throws IOException {
        this(new Generic_Defaults(Paths.get(System.getProperty("user.home"),
                Vector_Strings.s_vector)));
    }

    /**
     * Creates a new instance using {@code d}.
     *
     * @param d This contains details of the directory which will be used.
     * @throws IOException If encountered.
     */
    public Vector_Files(Generic_Defaults d) throws IOException {
        super(d);
        gf = new Grids_Files(new Generic_Defaults(Paths.get(
                d.getDir().toString(), Vector_Strings.s_vector)));
    }

}
