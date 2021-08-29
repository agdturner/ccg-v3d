---
title: 'A Modularised Java library for 3D Eucliean geometry'
tags:
  - Java
  - geometry
  - arbitrary precision
  - order of magnitude
authors:
  - name: Andy G. D. Turner^[corresponding author]
    orcid: 0000-0002-6098-6313
    affiliation: 1
affiliations:
 - name: CCG, School of Geography, University of Leeds
 - index: 1
date: 26 August 2021
#bibliography: paper.bib
---

# Summary

A modularised Java three-dimensional ([3D](https://en.wikipedia.org/wiki/Euclidean_space)) [Euclidean geometry](https://en.wikipedia.org/wiki/Euclidean_geometry) library dependent on two mathematical Java libraries [@Turner:2021], [@Obermühlner:2021]. The spatial dimensions are defined by orthogonal coordinate axes X, Y and Z that meet at the origin point <x,y,z> where the [Cartesian](https://en.wikipedia.org/wiki/Cartesian_coordinate_system) coordinates x=y=z=0. Coordinates are currently stored as [BigRational](https://github.com/eobermuhlner/big-math/blob/master/ch.obermuhlner.math.big/src/main/java/ch/obermuhlner/math/big/BigRational.java) numbers. Conceptually, from the origin, the X Axis runs in a positive direction to the right, the Y Axis runs in a positive direction up, and the Z Axis runs in a positive direction in (or forwards). These choices are arbitrary, but it is thought that conceptually, this is probably most intuitive for most people and is known as a left handed. Most 2D graphs have the X axis running positive and towards the right on a screen and Y axis running positive in an upwards direction on a vertical screen. People have a face and a back and face forward and move forward to zoom in and move backward to zoom out.

The library is maturing, but does not yet deal with [Tetrahedra](https://en.wikipedia.org/wiki/Tetrahedra). The plan is to implement robust methods for: calculating the distance between all the [Simplexes](https://en.wikipedia.org/wiki/Simplexes) and to differentiate between two intersecting geometries touching and passing through each other.

The library has reached a level of maturity where it might be useful to others who might be encouraged to also help develop it. Quite a lot of work has gone into the intersection methods (which involve no rounding) and distance calculations (for which the user has to specify an [Order of Magnitude](https://en.wikipedia.org/wiki/Order_of_magnitude) for the precision of the result).

For some applications, it might be advantageous to change and allow some irrational coordinates (perhaps any algebraic expression, but certainly numbers summarised as multiplications and divisions of rational roots).

# Statement of need

The library provides robust intersection methods for [Simplexes](https://en.wikipedia.org/wiki/Simplexes) in 3D with the exception of [Tetrahedra](https://en.wikipedia.org/wiki/Tetrahedra). The author intends to use the library to help visualise global scale geographical data and to developing 3D models of [Earth](https://en.wikipedia.org/wiki/Earth) and parts of it, but the library may be useful in other scientific applications and perhaps in mathematics.

Geographically everything is moving and changing. These changes vary with scale. Geographers explore patterns of change in different things in regions across ranges of scales using data at specific resolutions (spatial units - such as millimetres, metres, and kilometres; temporal units - such as seconds, minutes, hours, days, months, years). The [structure of Earth](https://en.wikipedia.org/wiki/Structure_of_Earth), whilst conceptually well defined, is continually changing as Earth moves relative to the [Sun](https://en.wikipedia.org/wiki/Sun), the [Moon](https://en.wikipedia.org/wiki/Moon) and other [astronomical object](https://en.wikipedia.org/wiki/Astronomical_object)s. More of Earth's atmosphere, for example, stretches further out towards the Sun as it heats and expands in the day. The orbit of the Earth around the Sun is also complicated and the distance from the Earth to the Sun varies. Earth's solar system also has a varying orbit in the [Milky Way](https://en.wikipedia.org/wiki/Milky_Way). Other [astronomical object](https://en.wikipedia.org/wiki/Astronomical_object)s effect Earth's [Geoid](https://en.wikipedia.org/wiki/Geoid) and may influence [Earth's magnetic field](https://en.wikipedia.org/wiki/Earth%27s_magnetic_field). How good are our best estimates of sea levels and Earth's magnetic field in the next 50 to 100 years? Application of this code abounds in [Earth Science](https://en.wikipedia.org/wiki/Earth_science)s which was one of the original motivations for developing it. It might also be useful in studying things at larger scales, i.e. the positions and movements of other [astronomical object](https://en.wikipedia.org/wiki/Astronomical_object)s, and modelling their shapes and changes in their structures over time. It might also be useful in studying things at smaller spatial scales, including perhaps in chemistry and maybe even quantum physics although at the extremes of scale when a Euclidean geometry is no longer a good way to describe space, theis might be of little use!

# Acknowledgements
- The [University of Leeds](http://www.leeds.ac.uk) and externally funded research grants have supported the development of this library or the dependencies.
