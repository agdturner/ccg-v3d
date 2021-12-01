---
title: 'Development of a Modularised Java library for 3D Euclidean geometry'
tags:
  - Java
  - Euclidean geometry
  - 3D
  - arbitrary precision
  - order of magnitude
authors:
  - name: Andy Turner^[corresponding author]
    orcid: 0000-0002-6098-6313
    affiliation: 1
affiliations:
 - name: CCG, School of Geography, University of Leeds
 - index: 1
date: 30 October 2021
bibliography: paper.bib
---

# Summary

[ccg-v3d](https://github.com/agdturner/ccg-v3d) is a three-dimensional ([3D](https://en.wikipedia.org/wiki/Euclidean_space)) [Euclidean geometry](https://en.wikipedia.org/wiki/Euclidean_geometry) library. It is pure modularised Java, based on [openJDK 17](https://openjdk.java.net/projects/jdk/17/). The only additional dependency is [@ccg-math] - a stand alone mathematics library.

Point locations in space are defined using 3D [cartesian](https://en.wikipedia.org/wiki/Cartesian_coordinate_system) coordinates with [orthogonal](https://en.wikipedia.org/wiki/Orthogonality) X, Y and Z axes that meet at the origin - a point <x,y,z> where x=y=z=0. The coordinate system is "right handed", so if X increases to the right of this page and Y increases towards the top of this page, then Z increases out from the page, (see [orientation](https://en.wikipedia.org/wiki/Orientation_(vector_space)) for details of handedness, essentially there are two choices - a "left handed" system would have the Z direction of increase reversed). Right handed is more commonly used in both physics and geography.

Coordinates are either stored as [Math_BigRational](https://github.com/agdturner/ccg-math/blob/master/src/main/java/uk/ac/leeds/ccg/math/number/Math_BigRational.java) numbers - a subset of [rational numbers](https://en.wikipedia.org/wiki/Rational_number) with java.math.BigDecimal numerators and denominators, or [Math_BigRationalSqrt](https://github.com/agdturner/ccg-math/blob/master/src/main/java/uk/ac/leeds/ccg/math/number/Math_BigRationalSqrt.java) - numbers that also represent square roots (calculated to a given [Order of Magnitude](https://en.wikipedia.org/wiki/Order_of_magnitude) precision as required).

More light weight geometries are in the [uk.ac.leeds.ccg.v3d.geometry.light](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/light/) package. These use [V3D_V](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/light/V3D_V.java) which have three Math_BigRational components (nominally x, y and z) and extend [V3D_VGeometry](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/light/V3D_VGeometry.java) - an abstract class that holds a V3D_V "offset" which can be thought of as the translation of the [centroid](https://en.wikipedia.org/wiki/Centroid) of the geometry from the origin.
- [V3D_VPoint](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/light/V3D_VPoint.java) - is for representing points. A V3D_V instance gives the location of the point relative to the offset.
- [V3D_VLine](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/light/V3D_VLine.java) - is for representing finite lines. V3D_V instances p and q give the location of the ends of the line relative to the offset (and can be the same).
- [V3D_VTriangle](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/light/V3D_VTriangle.java) - is for representing triangles. V3D_V instances p, q and r give the location of each corner of the triangle relative to the offset (two or more of them can be the same).
- [V3D_VTetrahedron](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/light/V3D_VTetrahedron.java) - is for representing [tetrahedron](https://en.wikipedia.org/wiki/Tetrahedron). V3D_V instances p, q, r and s give the location of each corner of the tetrahedron relative to the offset (two or more can be the same).

Heavier geometries use offsets and relative locations stored as [Math_BigRationalSqrt](https://github.com/agdturner/ccg-math/blob/master/src/main/java/uk/ac/leeds/ccg/math/number/Math_BigRationalSqrt.java) numbers. This allows for example a point to be located at x=y=z=sqrt(2). For these geometries there are methods which test for intersection, that return the geometry of the intersection, and that calculate the minimum distance. The methods that test if two geometries intersect involve no rounding. Calculating geometries of intersection may involve rounding as coordinates are only a subset of [algebraic number](https://en.wikipedia.org/wiki/Algebraic_number)s. Finite geometries store the [Order of Magnitude](https://en.wikipedia.org/wiki/Order_of_magnitude) used to calculate magnitudes to be clear about the precision.

For any finite ccg-v3d geometry, a [V3D_Envelope](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Envelope.java) - a [rectangular cuboid](https://en.wikipedia.org/wiki/Rectangular_cubiod) aligned with the axes can be computed. Such envelopes assist in the computation of intersections or tests for intersection by quickly ruling out intersection between geometries that are in different parts of the X, Y, Z domain.

[V3D_Geometry](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Geometry.java) is an abstract class with a V3D_Vector offset, and an [Order of Magnitude](https://en.wikipedia.org/wiki/Order_of_magnitude#Uses) (OOM). Similar to V3D_V, this allows geometries to be readily translated and rotated. V3D_Geometry is extended to define the following finite and infinite geometries:
- [V3D_Point](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Point.java) - a point geometry where a V3D_Vector gives the position relative to the offset.
- [V3D_Line](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Line.java) - an infinite line geometry that passes through two defined points.
- [V3D_Ray](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Ray.java) - an infinite line geometry that extends from a point in one direction through another point.
- [V3D_Plane](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Plane.java) - an infinite [plane geometry](https://en.wikipedia.org/wiki/Plane_(geometry)). The plane is defined either by 3 points (p, q and r) that are not [collinear](https://en.wikipedia.org/wiki/Collinearity) or coincident, or by a normal vector and a point (where all points orthogonal to the normal vector at the point are on the plane).
- [V3D_LineSegment](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_LineSegment.java) - represents a single continuous finite part of a line. Line segments are considered equal irrespective of the order of the end points. The centroid of a line segment can be computed and stored.
- [V3D_Triangle](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Triangle.java) - a triangular part of a plane. The three coplanar points {p, q and r} are the corners of the triangle. Triangle sides can be stored as line segments. The centroid of a triangle can be computed and stored.
- [V3D_Tetrahedron](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Tetrahedron.java) - represents a [tetrahedron](https://en.wikipedia.org/wiki/Tetrahedron). Any selection of three points are not coplanar with the fourth. A tetrahedron surface can be thought of as compising of four triangles. The [centroid](https://en.wikipedia.org/wiki/Centroid#Of_a_tetrahedron_and_n-dimensional_simplex) and the [volume](https://en.wikipedia.org/wiki/Tetrahedron#Volume) can be computed and stored.

Geometry collections of the same types of geometry are supported, including:
- [V3D_Points](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Points.java) - for collections of points.
- [V3D_LineSegments](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_LineSegments.java) - for collections of collinear line segments. 
- [V3D_Triangles](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Triangles.java) - for collections of coplanar triangles. 
- [V3D_Tetrahedrons](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Tetrahedrons.java) - for collections of tetrahedrons.

# Statement of need
Everything is moving and changing at rates that vary with scale. Exploring patterns of change in different things in regions across ranges of spatial and temporal scales using data at defined spatial and temporal resolutions is a common scientific activity. 3D models and maps are also important in telecommunications, transportation, architecture and engineering.

Earth moves through the cosmos along with the [Sun](https://en.wikipedia.org/wiki/Sun), the [Moon](https://en.wikipedia.org/wiki/Moon) and the rest of the solar system. The solar system has many parts. It is estimated that there are approximately a million astroids in the [astroid belt](https://en.wikipedia.org/wiki/Asteroid_belt) with a diameter of more than 1km, and perhaps as many as a hundred thousand objects in the [kuiper belt](https://en.wikipedia.org/wiki/Kuiper_belt) with a diameter of 100km or more. Given the likelihood that this epoch of life on Earth might end as a consequence of a collision between Earth and one of these objects, it seems imperative to be tracking and mapping these and trying to model and predict their motions and identify concerning trajectories.

Earth's [axial tilt](https://en.wikipedia.org/wiki/Axial_tilt) varies in an [axial precession](https://en.wikipedia.org/wiki/Axial_precession) which effects seasons and tides. Along with changes in [orbital eccentricity](https://en.wikipedia.org/wiki/Orbital_eccentricity), this results in periodic cycles of climate called [Milankovitch cycles](https://en.wikipedia.org/wiki/Milankovitch_cycles). A hundred years after these were recognised, there is still much to be done to understand the effects and the impacts on our future.

Earth's atmosphere expands as it heats in the day and shrinks as it cools in the night. On the surface, rock and ice form and move in complex ways, but also under the influence of predictable and seasonal patterns. The seasons are a consequence of the tilt of Earth's axis of rotation, but they vary in intensity along with orbital eccentricity. Developing a better understanding of changing ice masses on Earth could be particularly important for the future of many people. Whilst fluids often lack structure and have fuzzier boundaries and less clear geometrical delineations, much of the world is made of more solid more slowly changing parts which have clearer shapes and volumes that can be usefully mapped with sets of point, with surfaces represented using triangular irregular networks, and volumes represented as collections of tetrahedron.

Are current technology and models built based on [floating point](https://en.wikipedia.org/wiki/Floating_point) numbers providing sufficient accuracy?

# Future development
Ideally, a collective effort is wanted to develop the library sustainably. Issue reporting and tracking, feature requests and a roadmap for developing the functionality is wanted. Perhpas though rather than create a new community, what might best happen is to migrate the functionality to [Apache Commons Geometry](https://commons.apache.org/proper/commons-geometry/) (see also: [Apache Commons Geometry GitHub Repository](https://github.com/apache/commons-geometry)). This parallel development appears to be developing similar arbitrary precision geometrical functionality.

# Acknowledgements
The [University of Leeds](http://www.leeds.ac.uk) supported the development of the software. Externally funded research grants supported the development of some of the dependencies. Thank you Eric for the [@big-math] library, in particular [BigRational](https://github.com/eobermuhlner/big-math/blob/master/ch.obermuhlner.math.big/src/main/java/ch/obermuhlner/math/big/BigRational.java) upon which Math_BigRational is almost entirely based.

# References
