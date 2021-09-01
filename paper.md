---
title: 'Development of a Modularised Java library for 3D Euclidean geometry'
tags:
  - Java
  - geometry
  - cartesian
  - arbitrary precision
  - order of magnitude
  - intersection
  - distance
authors:
  - name: Andy G. D. Turner^[corresponding author]
    orcid: 0000-0002-6098-6313
    affiliation: 1
affiliations:
 - name: CCG, School of Geography, University of Leeds
 - index: 1
date: 30 August 2021
#bibliography: paper.bib
---

# Summary

This paper introduces a modularised Java library for three-dimensional ([3D](https://en.wikipedia.org/wiki/Euclidean_space)) [Euclidean geometry](https://en.wikipedia.org/wiki/Euclidean_geometry).

In addition to Java 15, the library is dependent on two mathematics libraries and a general library: `[@Turner:2021]`, `[@Obermühlner:2021]` and `[@agdt-java-generic:2021]`.

Point positions in space are defined using [cartesian](https://en.wikipedia.org/wiki/Cartesian_coordinate_system) coordinates. The axes X, Y and Z meet at the origin point <x,y,z> where x=y=z=0. Cartesian coordinates are currently stored as [BigRational](https://github.com/eobermuhlner/big-math/blob/master/ch.obermuhlner.math.big/src/main/java/ch/obermuhlner/math/big/BigRational.java) numbers - a subset of [rational numbers](https://en.wikipedia.org/wiki/Rational_number). Conceptually, from the origin, the X Axis runs in a positive direction to the right, the Y Axis runs in a positive direction up, and the Z Axis runs in a positive direction in (or forwards). These choices are arbitrary, but this is regarded as being "left handed". This is intuitive in that many 2D graphs have the X axis running positive and towards the right, and the Y axis running positive in an upwards direction on a vertical screen. People look at a screen and move closer to it to zoom in and move backward to zoom out.

The library has reached a level of maturity where it might be useful to others, some of who might want to develop it.

There are methods which test if geometries intersect and methods that return the geometry of the intersection. The methods that test for intersection involve no rounding. To calculate geometries of intersection, sometimes rounding seems necessary, but this might be overcome by allowing coordinates to be stored as [algebraic number](https://en.wikipedia.org/wiki/Algebraic_number)s. There are perhaps usage scenarios that invovle differentiating between intersecting geometries that touch or that touch and pass through each other.

The library does not yet have geometry implementations for any volumes other than a [rectangular cuboid](https://en.wikipedia.org/wiki/Rectangular_cubiod) aligned with the axes. All finite geometries have such a bounding rectangular cuboid which is known as an envelope. It can be quick to test if two envelopes intersect, if they do, then there is a chance that the geometries that have these envelopes also intersect.

It is planned to implement:
1. Some [polyhedra](https://en.wikipedia.org/wiki/Polyhedra) beginning with [tetrahedra](https://en.wikipedia.org/wiki/Tetrahedra) and rectangular cuboids that do not necessarily align with the axes.
2. Distance calculating functionality between all [simplex](https://en.wikipedia.org/wiki/Simplex)s and polyhedra, for which there can be minimum, maximum and average distances. For the latter of these, it might be helpful to define [centroid](https://en.wikipedia.org/wiki/Centroid)s.

Currently, the implemented distance methods return [BigDecimal](https://docs.oracle.com/en/java/javase/15/docs/api/java.base/java/math/BigDecimal.html) numbers and the user supplies an [order of magnitude](https://en.wikipedia.org/wiki/Order_of_magnitude) for the precision of the result. Sometimes a result can be returned precisely, but sometimes rounding is necessary.

Is storing coordinates as rational numbers too limiting?

Is there a use case scenario for [curve](https://en.wikipedia.org/wiki/Curve)d geometry?

# Statement of need

The library provides robust intersection test methods for [simplex](https://en.wikipedia.org/wiki/Simplexes)s in 3D (currently with the exception of [tetrahedra](https://en.wikipedia.org/wiki/Tetrahedra)).

The author intends to use the library to help visualise global scale geographical data and to develop models of [Earth](https://en.wikipedia.org/wiki/Earth) and parts of it.

Geographically, everything is moving and changing. The rates of movement and change vary with scale. Geographers commonly explore patterns of change in different things in regions across ranges of spatial and temporal scales using data at specific spatial and temporal resolutions. The [structure of Earth](https://en.wikipedia.org/wiki/Structure_of_Earth), whilst conceptually well defined, is continually changing as Earth moves relative to the [Sun](https://en.wikipedia.org/wiki/Sun), the [Moon](https://en.wikipedia.org/wiki/Moon) and other [astronomical object](https://en.wikipedia.org/wiki/Astronomical_object)s which can have significant affects. More of Earth's atmosphere, for example, stretches further out towards the Sun as it heats and expands in the day. The orbit of the Earth around the Sun is complicated and the distance from the Earth to the Sun varies through time. [Orbital eccentricity](https://en.wikipedia.org/wiki/Orbital_eccentricity) affects Earth surface processes and probably causes changes deep within the Earth. Earth's solar system has [helicoid](https://en.wikipedia.org/wiki/Helicoid) like motion as it traverses the [Milky Way](https://en.wikipedia.org/wiki/Milky_Way). The varying position of Earth's solar system relative to the [galactic plane](https://en.wikipedia.org/wiki/Galactic_plane) is likely to affect [Earth's magnetic field](https://en.wikipedia.org/wiki/Earth%27s_magnetic_field). Nearby astronomical objects affect pressure on Earth and may alter the [geoid](https://en.wikipedia.org/wiki/Geoid) (sea levels) significantly. How good are our best estimates of sea levels and Earth's magnetic field in the next 50 to 100 years? Are current technology and models built based on [floating point](https://en.wikipedia.org/wiki/Floating_point) numbers providing sufficient accuracy?  Ocean boundaries change, water density changes with pressure and temperature (and state). Ice supported on land melts and adds to the mass. Although the total amount of ice is small relative to the total volume of water in the oceans which again is small relative to the mass of Earth. Application of this code abounds in [Earth Science](https://en.wikipedia.org/wiki/Earth_science)s which was one of the original motivations for developing it.

The library might also be useful in studying things at larger and smaller scales, in particular examining shapes and changes in structure over time. It might have diverse practical application in telecommunications, transportation, architecture, engineering, biology, chemistry and physics.

# References

# Acknowledgements
- The [University of Leeds](http://www.leeds.ac.uk) and externally funded research grants have supported the development of this library or the dependencies.