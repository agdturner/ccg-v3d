# agdt-java-vector3D

https://github.com/agdturner/agdt-java-vector3D

## Current Version
Developed and tested on Java 11 using Maven.
https://mvnrepository.com/artifact/io.github.agdturner/agdt-java-vector3D
```
<!-- https://mvnrepository.com/artifact/io.github.agdturner/agdt-java-vector3D -->
<dependency>
    <groupId>io.github.agdturner</groupId>
    <artifactId>agdt-java-vector3D</artifactId>
    <version>0.1</version>
</dependency>
```

## General description
A Java library for 3D spatial vector data processing. The coordinates for each point are stored as BigRational numbers. An attempt is being made not to round unless absolutely necessary.

Mostly what is implemented so far is intersection:
* point-plane, point-line, point-line_segement, point-rectangle
* line-line, line-plane
* line_segment-line line_segment-line_segment, line_segemnt-plane, line_segment-rectangle
* plane-plane

## Code status and development roadmap
A 0.1 release is available on Maven Central. The next phase of development is to add functionality for calculating the minimum distances between points and lines, lines and lines, lines and line_segments, lines and planes and line_segments and line_segments. Not sure when I will get around to this, but maybe soon... 

## History
The library began development in March 2020 and is actively being developed. 

## Known uses
As yet it is not being used, but I plan to use it to construct a 3D model of Earth :)

## Dependencies
The library relies heavily on the Big Math
```
        <!-- https://mvnrepository.com/artifact/io.github.agdturner/agdt-java-generic -->
        <dependency>
            <groupId>io.github.agdturner</groupId>
            <artifactId>agdt-java-generic</artifactId>
            <version>1.2</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/io.github.agdturner/agdt-java-math -->
        <dependency>
            <groupId>io.github.agdturner</groupId>
            <artifactId>agdt-java-math</artifactId>
            <version>1.3</version>
        </dependency>
        <!-- https://search.maven.org/artifact/ch.obermuhlner/big-math -->
        <dependency>
            <groupId>ch.obermuhlner</groupId>
            <artifactId>big-math</artifactId>
            <version>2.3.0</version>
        </dependency>
```

## Contributions
- Please report issues in the usual way.
- Please liaise with the developer with regards feature requests and about contributing to development.

## Acknowledgements and feedback
* Massive thanks to Eric for the BigMath library that has saved me a lot of work :)
* If you find this code useful, please let me know and refer to the resources used in the usual ways.

## LICENCE
Please see the standard Apache 2.0 open source LICENCE.
