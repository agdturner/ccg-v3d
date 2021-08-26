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

Geographically everything is moving and changing and that these changes vary with scale. Geographers often look at the patterns of change in different things in a region across a range of scales and at specific resolutions of data. The dimensions are space-time-attribute and the resolutions relate respectively to: spatial units such as metres and kilometers; temporal unts such as minutes, hours, days, months, years; and attribute detail relating to how many variables help to define the entity of study that is defined within the spatial and temporal framework. The [Structure of Earth](https://en.wikipedia.org/wiki/Structure_of_Earth), whilst conceptually well defined, is continually changing as the Earth moves relative to the [Sun](https://en.wikipedia.org/wiki/Sun), the [Moon](https://en.wikipedia.org/wiki/Moon) and the other planets. The atmosphere for example stretches further out towards the Sun as it heats and expands in the day. The orbit of the Earth around the Sun is also a complicated motion and the distance from the Earth to the Sun is conditional on the orbits of our [Solar System](https://en.wikipedia.org/wiki/Solar_System)'s other [astronomical object](https://en.wikipedia.org/wiki/Astronomical_object)s, perhaps most influentially the giants [Jupiter](https://en.wikipedia.org/wiki/Jupiter) and [Saturn](https://en.wikipedia.org/wiki/Saturn) and the combined effects of Earths near neighbour planets ([Venus](https://en.wikipedia.org/wiki/Venus) and [Mars](https://en.wikipedia.org/wiki/Mars)) and [asteroid](https://en.wikipedia.org/wiki/Asteroid)s. The effects of these [astronomical objects] can to an extent exaggerate or cancel out. Let us add into the mix variation of the [Geoid](https://en.wikipedia.org/wiki/Geoid) over time. This is related to the orbital gravitational changes, but more so to the structure of Earth - the crust of which rises up into significant mountains and dips down into deep trenches. What should be clear from this exposition is that what we might think of as solid and unchanging or simple and easy to measure in a physical sense often isn't. If you are wondering how good model estimates are of sea level change under global warming, let me suggest that it is literally hard to fathom (not least because of the relationship between pressure and temperature and the nature and density of water)! Sea level change is important, but is perhaps not the least of our worries. Anyway, it is thought that there will be useful geographical application of this code in [Earth Science](https://en.wikipedia.org/wiki/Earth_science)s which was one of the original motivations for developing it. It might be useful in studying movements of [Galaxies](https://en.wikipedia.org/wiki/Galaxies).

# Acknowledgements
- The [University of Leeds](http://www.leeds.ac.uk) and externally funded research grants have supported the development of this library or the dependencies.
