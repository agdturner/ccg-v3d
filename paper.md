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

This paper introduces a modularised Java library for three-dimensional ([3D](https://en.wikipedia.org/wiki/Euclidean_space)) [Euclidean geometry](https://en.wikipedia.org/wiki/Euclidean_geometry). In addition to Java 15, the library has two main dependencies, both of them mathematics libraries: `[@Turner:2021]`; `[@Obermühlner:2021]`. Postions in space are defined using [Cartesian](https://en.wikipedia.org/wiki/Cartesian_coordinate_system) coordinates. The axes X, Y and Z meet at the origin point <x,y,z> where x=y=z=0. Coordinates are currently stored as [BigRational](https://github.com/eobermuhlner/big-math/blob/master/ch.obermuhlner.math.big/src/main/java/ch/obermuhlner/math/big/BigRational.java) numbers. Conceptually, from the origin, the X Axis runs in a positive direction to the right, the Y Axis runs in a positive direction up, and the Z Axis runs in a positive direction in (or forwards). These choices are arbitrary, but this is regarded as being "left handed". This is intuitive in that many 2D graphs have the X axis running positive and towards the right, and the Y axis running positive in an upwards direction on a vertical screen. People look at a screen and move closer to it to zoom in and move backward to zoom out.

The library has reached a level of maturity where it might be useful to others who might be encouraged to also help develop it. Quite a lot of work has gone into the intersection methods (which involve no rounding) and distance calculations (for which the user has to specify an [Order of Magnitude](https://en.wikipedia.org/wiki/Order_of_magnitude) for the precision of the result).

The library does not yet deal with [Tetrahedra](https://en.wikipedia.org/wiki/Tetrahedra). The plan is to implement robust methods for: calculating the distance between all the [simplex](https://en.wikipedia.org/wiki/Simplex)s and to differentiate between two intersecting geometries touching and passing through each other.

For some applications, it might be advantageous to allow irrational coordinates. This might be wanted when intersecting, for instance defining the intersection of two skew lines as a point (in some cases it is possible to define the intersection point using rational coordinates, but in other cases it might only be possible to store the intersection accurately using irrational numbers expressed as the multiplications and divisions of rational roots. 

# Statement of need

The library provides robust intersection methods for [Simplexes](https://en.wikipedia.org/wiki/Simplexes) in 3D (currently with the exception of [Tetrahedra](https://en.wikipedia.org/wiki/Tetrahedra)). The author intends to use the library to help visualise global scale geographical data and to develop 3D models of [Earth](https://en.wikipedia.org/wiki/Earth) and parts of it.

Geographically, everything is moving and changing. These changes vary with scale. Geographers commonly explore patterns of change in different things in regions across ranges of spacial and temporal scales using data at specific spatial and temporal resolutions. The [structure of Earth](https://en.wikipedia.org/wiki/Structure_of_Earth), whilst conceptually well defined, is continually changing as Earth moves relative to the [Sun](https://en.wikipedia.org/wiki/Sun), the [Moon](https://en.wikipedia.org/wiki/Moon) and other [astronomical object](https://en.wikipedia.org/wiki/Astronomical_object)s. More of Earth's atmosphere, for example, stretches further out towards the Sun as it heats and expands in the day. The orbit of the Earth around the Sun is complicated and the distance from the Earth to the Sun varies through time. [Orbital eccentricity](https://en.wikipedia.org/wiki/Orbital_eccentricity) affects Earth surface processes and probably causes changes deep within the Earth. Earth's solar system has [helicoid](https://en.wikipedia.org/wiki/Helicoid) like motion as it traverses the [Milky Way](https://en.wikipedia.org/wiki/Milky_Way). The varying position of Earth's solar system relative to centre and plane of the galaxy is thought to have significant effects. Other astronomical objects affect Earth's [geoid](https://en.wikipedia.org/wiki/Geoid) and may influence [Earth's magnetic field](https://en.wikipedia.org/wiki/Earth%27s_magnetic_field). How good are our best estimates of sea levels and Earth's magnetic field in the next 50 to 100 years? Application of this code abounds in [Earth Science](https://en.wikipedia.org/wiki/Earth_science)s which was one of the original motivations for developing it.

The library might also be useful in studying things at larger and smaller scales, in particular examining shapes and changes in structure over time. It might have diverse practical application in telecommunication and transportation, biology, chemistry and physics.

# References

# Acknowledgements
- The [University of Leeds](http://www.leeds.ac.uk) and externally funded research grants have supported the development of this library or the dependencies.
