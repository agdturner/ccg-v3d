---
title: 'agdt-java-vector3D: A Java library for Eucliean geometry'
tags:
  - Java
  - geometry
  - arbitrary precision
  - order of magnitude
authors:
  - name: Andy G.D. Turner^[corresponding author]
    orcid: 0000-0002-6098-6313
    affiliation: 1
affiliations:
 - name: CCG, School of Geography, University of Leeds
 - index: 1
date: 26 August 2021
#bibliography: paper.bib
---

# Summary

A modularised Java three-dimensional ([3D](https://en.wikipedia.org/wiki/Euclidean_space)) [Euclidean geometry](https://en.wikipedia.org/wiki/Euclidean_geometry) library. The dimensions are defined by orthogonal coordinate axes X, Y and Z that meet at the origin point <x,y,z> where the [Cartesian](https://en.wikipedia.org/wiki/Cartesian_coordinate_system) coordinates x=y=z=0. Instances of points in this space are immutable. Coordinates are currently stored as [BigRational](https://github.com/eobermuhlner/big-math/blob/master/ch.obermuhlner.math.big/src/main/java/ch/obermuhlner/math/big/BigRational.java) numbers. Conceptually, from the origin, the X Axis runs in a positive direction to the right, the Y Axis runs in a positive direction up, and the Z Axis runs in a positive direction in or forwards. These choices are arbitrary, but it is thought that conceptually, this is most intuitive for people given the norms of graphs and human perception (humans have a perception of forward and backward, and we face forward and move forward to zoom in and move backward to zoom out).

# Statement of need

`agdt-java-vector3D` provides robust intersection methods for some basic 3D geometry including line segments. The author intends to use the library to help visualise global scale geographical data and to developing 3D models of [Earth](https://en.wikipedia.org/wiki/Earth) and parts of it.

Geographically everything is moving and changing. These changes vary with scale. Geographers explore patterns of change in different things in regions across ranges of scales using data at specific resolutions (spatial units - such as millimetres, metres, and kilometres; temporal units - such as seconds, minutes, hours, days, months, years). The [structure of Earth](https://en.wikipedia.org/wiki/Structure_of_Earth), whilst conceptually well defined, is continually changing as Earth moves relative to the [Sun](https://en.wikipedia.org/wiki/Sun), the [Moon](https://en.wikipedia.org/wiki/Moon) and other [astronomical object](https://en.wikipedia.org/wiki/Astronomical_object)s. More of Earth's atmosphere, for example, stretches further out towards the Sun as it heats and expands in the day. The orbit of the Earth around the Sun is also complicated and the distance from the Earth to the Sun varies. Earth's solar system also has a varying orbit in the [Milky Way](https://en.wikipedia.org/wiki/Milky_Way). Other [astronomical object](https://en.wikipedia.org/wiki/Astronomical_object)s effect Earth's [Geoid](https://en.wikipedia.org/wiki/Geoid) and may influence [Earth's magnetic field](https://en.wikipedia.org/wiki/Earth%27s_magnetic_field). How good are our best estimates of sea levels and Earth's magnetic field in the next 50 to 100 years? Applicatioin of this code abounds in [Earth Science](https://en.wikipedia.org/wiki/Earth_science)s which was one of the original motivations for developing it. It might also be useful in studying the positions and movements of other [astronomical object](https://en.wikipedia.org/wiki/Astronomical_object)s, and modelling their shapes and changes in their structures over time.

# Acknowledgements
- The [University of Leeds](http://www.leeds.ac.uk) and externally funded research grants have supported the development of this library or the dependencies.
