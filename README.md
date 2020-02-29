# vector

https://github.com/agdturner/agdt-java-generic-vector

A Java library for 2D vector data processing. The coordinates for each point are stored as BigDecimals - numbers with a specified decimal place precision.

Many vector data handling libraries will use floating point numbers for storing locations. This library uses Java BigDecimal numbers so essentialy all coordinates have the same precision. As such these are similar to a form of raster data. 

## Usages
1. The library was originally developed for a project that developed a traffic simulation where the traffic could readily be counted and constrained on each section of the network based on the prorerties of that sections of network and the speed and density of traffic on it.
2. For generating line based visualisations of movements across space.

More example usages are wanted. If you use this library please add to this list.

## Code status and development roadmap
This code is actively being developed.
For a 1.0.0 release, the plan is:
1. To develop some more usage examples to showcase what the library can do.
2. To produce more unit tests to test the core functionality and capabilities of the library.
3. To update the source code documentation.
For a 2.0.0 perhaps move to 3D.

## Dependencies
Please see the pom.xml for details.
1. Grids
https://github.com/agdturner/agdt-java-generic-grids
A Java library for 2D square celled spatial raster data processing. 

## Contributions
Please raise issues and submit pull requests in the usual way. Contributions will be acknowledged.

## Acknowledgements
The development has been supported by numerous research grants and the University of Leeds. 

## LICENCE
Please see the standard Apache 2.0 open source LICENCE.
