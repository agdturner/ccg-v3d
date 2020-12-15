module uk.ac.leeds.ccg.vector3D {
    /**
     * Requirements.
     */
    requires ch.obermuhlner.math.big;
    requires transitive uk.ac.leeds.ccg.generic;
    requires transitive uk.ac.leeds.ccg.math;
    
    /**
     * Exports.
     */
    exports uk.ac.leeds.ccg.v3d.core;
    exports uk.ac.leeds.ccg.v3d.geometry;
    exports uk.ac.leeds.ccg.v3d.geometry.envelope;
    exports uk.ac.leeds.ccg.v3d.io;
}