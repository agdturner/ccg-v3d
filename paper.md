---
title: 'A Modularised Java library for 3D Euclidean geometry'
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
   index: 1
date: 30 October 2021
bibliography: paper.bib
---

# Summary

A three-dimensional ([3D](https://en.wikipedia.org/wiki/Euclidean_space)) [Euclidean geometry](https://en.wikipedia.org/wiki/Euclidean_geometry) Java library for modelling 3D changes in geometries over time. The library is modularised, based on [openJDK 17](https://openjdk.java.net/projects/jdk/17/), and the only additional dependency is [ccg-math](https://github.com/agdturner/ccg-math) - a stand alone modularised mathematics library.

Point locations in space are defined using 3D [cartesian](https://en.wikipedia.org/wiki/Cartesian_coordinate_system) coordinates with [orthogonal](https://en.wikipedia.org/wiki/Orthogonality) X, Y and Z axes that meet at the origin - a point <x,y,z> where x=y=z=0. The coordinate system is "right handed", so if X increases to the right of this page and Y increases towards the top of this page, then Z increases out from the page, (see [orientation](https://en.wikipedia.org/wiki/Orientation_(vector_space)) for details of handedness, essentially there are two choices - a "left handed" system would have the Z direction of increase reversed). Right handed is more commonly used in both physics and geography.

Coordinates are either stored as [Math_BigRational](https://github.com/agdturner/ccg-math/blob/master/src/main/java/uk/ac/leeds/ccg/math/number/Math_BigRational.java) numbers - a subset of [rational numbers](https://en.wikipedia.org/wiki/Rational_number) with java.math.BigDecimal numerators and denominators, or [Math_BigRationalSqrt](https://github.com/agdturner/ccg-math/blob/master/src/main/java/uk/ac/leeds/ccg/math/number/Math_BigRationalSqrt.java) - numbers that also represent square roots (calculated to a given [Order of Magnitude](https://en.wikipedia.org/wiki/Order_of_magnitude) precision as required).

More light weight geometries are in the [uk.ac.leeds.ccg.v3d.geometry.light](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/light/) package. These use [V3D_V](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/light/V3D_V.java) which have three Math_BigRational components (nominally x, y and z) and extend [V3D_VGeometry](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/light/V3D_VGeometry.java) - an abstract class that holds a V3D_V "offset" which can be thought of as the translation of the [centroid](https://en.wikipedia.org/wiki/Centroid) of the geometry from the origin.
- [V3D_VPoint](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/light/V3D_VPoint.java) - is for representing points. A V3D_V instance gives the location of the point relative to the offset.
- [V3D_VLine](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/light/V3D_VLine.java) - is for representing finite lines. V3D_V instances p and q give the location of the ends of the line relative to the offset (and can be the same).
- [V3D_VTriangle](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/light/V3D_VTriangle.java) - is for representing triangles. V3D_V instances p, q and r give the location of each corner of the triangle relative to the offset (two or more of them can be the same).
- [V3D_VTetrahedron](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/light/V3D_VTetrahedron.java) - is for representing [tetrahedron](https://en.wikipedia.org/wiki/Tetrahedron). V3D_V instances p, q, r and s give the location of each corner of the tetrahedron relative to the offset (two or more can be the same).

Heavier geometries use offsets and relative locations stored as [Math_BigRationalSqrt](https://github.com/agdturner/ccg-math/blob/master/src/main/java/uk/ac/leeds/ccg/math/number/Math_BigRationalSqrt.java) numbers. This allows for example a point to be located at x=y=z=sqrt(2). For these geometries there are methods which test for intersection, that return the geometry of the intersection, and that calculate the minimum distance between any two geometries. The methods that test if two geometries intersect involve no rounding. Calculating geometries of intersection may involve rounding as coordinates are only a subset of [algebraic number](https://en.wikipedia.org/wiki/Algebraic_number)s. Finite geometries store the [Order of Magnitude](https://en.wikipedia.org/wiki/Order_of_magnitude) used to calculate magnitudes to be clear about the precision in relation to a scale.

For any finite ccg-v3d geometry, a [V3D_Envelope](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Envelope.java) - a [rectangular cuboid](https://en.wikipedia.org/wiki/Rectangular_cubiod) aligned with the axes can be computed. Such envelopes assist in the computation of intersections or tests for intersection by quickly ruling out intersection between geometries that are in different X, Y, Z domains.

[V3D_Geometry](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Geometry.java) is an abstract class with a V3D_Vector offset, and an [Order of Magnitude](https://en.wikipedia.org/wiki/Order_of_magnitude#Uses). Similar to V3D_V, this allows geometries to be readily translated and rotated. V3D_Geometry is extended to define the following finite and infinite geometries:
- [V3D_Point](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Point.java) - a point geometry where a V3D_Vector gives the position relative to the offset.
- [V3D_Line](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Line.java) - an infinite line geometry that passes through two defined points.
- [V3D_Ray](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Ray.java) - an infinite line geometry that extends from a point in one direction through another point.
- [V3D_Plane](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Plane.java) - an infinite [plane geometry](https://en.wikipedia.org/wiki/Plane_(geometry)). The plane is defined either by 3 points (p, q and r) that are not [collinear](https://en.wikipedia.org/wiki/Collinearity) or coincident, or by a normal vector and a point (where all points orthogonal to the normal vector at the point are on the plane).
- [V3D_LineSegment](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_LineSegment.java) - represents a single continuous finite part of a line with non-zero length. Line segments are considered equal irrespective of the order of the end points. The centroid of a line segment can be computed and stored.
- [V3D_Triangle](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Triangle.java) - a triangular part of a plane with a non-zero area. The three coplanar points {p, q and r} are the corners of the triangle. Triangle sides can be stored as line segments. The centroid of a triangle can be computed and stored.
- [V3D_Tetrahedron](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Tetrahedron.java) - represents a [tetrahedron](https://en.wikipedia.org/wiki/Tetrahedron). Any selection of three points are not coplanar with the fourth, so this always defines a volume. A tetrahedron surface can be thought of as compising of four different triangles.

The following geometry collections are supported:
- [V3D_Points](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Points.java) - for collections of points.
- [V3D_LineSegments](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_LineSegments.java) - for collections of collinear line segments. 
- [V3D_TriangleCoplanar](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_TriangleCoplanar.java) - for collections of coplanar triangles.
- [V3D_Tetrahedrons](https://github.com/agdturner/ccg-v3d/blob/master/src/main/java/uk/ac/leeds/ccg/v3d/geometry/V3D_Tetrahedrons.java) - for collections of tetrahedrons. These do not have the constraint of being interconnecting.

# Statement of need
Whilst open source 3D spatial tools exist, those which are known about rely on [floating point](https://en.wikipedia.org/wiki/Floating_point) arithmetic the imprecisions of which result in errors and uncertainties. This software uses more heavyweight numerical representations for coordinates allowing for arbitrary precision accuracy. In a purely geometrical sense, this software approximates shapes using points and straight line segments which connect these. Many things in reality can be represented in this way, particularly around human scales. For generating models of what is happening on or near the surface and within the earth, such representation ofers a way to link data together for a Digital Twin Earth.

Everything is moving and changing at rates that vary with scale. Exploring patterns of change spatial is common to much scientific activity. 3D models and maps are important in telecommunications, transportation, architecture and engineering. This library is currently geometry focussed and attributes of points, line segments, areas and 3D regions are yet to be defined, but the geometrical framework is there to do this. Adding (physical) attributes and modelling motion, energy transfer and the disolution and formation of objects is an exciting further step, as is developing a way to visualise models. The next steps are to work on building some basic models and visualising these. My hope is to develop a basic model of the solar system which inputs data that is known about the masses, orientation and location of the [Sun](https://en.wikipedia.org/wiki/Sun), the [Moon](https://en.wikipedia.org/wiki/Moon) and the rest of the solar system. I also plan to create a basic static model of Earth using available data to distiguish water bodies, ice and rock/soil. On a smaller scale, I would like to work on developing a model of a single glaciated/ice region.

# Future development
Ideally, a collective effort is wanted to develop the library sustainably. Issue reporting and tracking, feature requests and a more detailed roadmap for developing the functionality is wanted. Perhaps rather than creating a new community, what might best happen is to migrate the functionality to [Apache Commons Geometry](https://commons.apache.org/proper/commons-geometry/) (see also: [Apache Commons Geometry GitHub Repository](https://github.com/apache/commons-geometry)). This parallel development appears to be developing similar arbitrary precision geometrical functionality.

# Acknowledgements
The [University of Leeds](http://www.leeds.ac.uk) supported the development of the software. Externally funded research grants supported the development of some of the dependencies. Thank you Eric for the [@big-math] library, in particular [BigRational](https://github.com/eobermuhlner/big-math/blob/master/ch.obermuhlner.math.big/src/main/java/ch/obermuhlner/math/big/BigRational.java) upon which Math_BigRational is almost entirely based.

# References
