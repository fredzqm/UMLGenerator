# FAD Reverse Java UML builder


[![build status](https://ada.csse.rose-hulman.edu/zhangq2/Fad/badges/master/build.svg)](https://ada.csse.rose-hulman.edu/zhangq2/Fad/commits/master)

[![coverage report](https://ada.csse.rose-hulman.edu/zhangq2/Fad/badges/master/coverage.svg)](https://ada.csse.rose-hulman.edu/zhangq2/Fad/commits/master)

We use continuous integration to test our program 

To build this project from command line (note that it also computes test coverage):
**./gradlew build**


To run this project from command line:
**./gradlew run**

If you run the main method in the class application.Main, use the following arguments:

Usage: Command Line Parser
                class1 class2 ... classN [(-e|--executable) <path>] (-d|--directory) <outputDirectory> (-o|--outputfile) <outputfile> [(-x|--extension) <extension>] [(-f|--filters) <filters>] [(-n|--nodesep) <nodeseparationvalue>] [-r|--recursive] [-k|--direction]

  class1 class2 ... classN
        desc: space separated list of the name of the classes you want the UMLfor
        (default: java.lang.String)

  [(-e|--executable) <path>]
        desc: the name of the executable path for graphviz on your machine
        (default: dot)

  (-d|--directory) <outputDirectory>
        desc: the name of the directory which you want output to go to
        (default: output)

  (-o|--outputfile) <outputfile>
        desc: the name of the output file
        (default: output)

  [(-x|--extension) <extension>]
        desc: the name extension of the output file without the dot
        (default: png)

  [(-f|--filters) <filters>]
        desc: use this flag if you want to filter out certain modifiers
        if public, you filter out protected and private
        if protected, you filter out private
        if empty or private, you filter out nothing
        (default: private)

  [(-n|--nodesep) <nodeseparationvalue>]
        desc: the node seperation value, which is greater than 0
        (default: 1)

  [-r|--recursive]
        desc: use this flag if you want to recursively create the UML with all superclasses

  [-k|--direction]
        desc: use this flag if you want the UML to be outputed Top down, otherwise it will be outputed Bottom up


## Member Contributions:

Adam:  	Managed the creation and updates to the UML, pair programmed the initial model, coded the CommandLineParser and Configuration, Performed Debugging, wrote the README

Fred:   I implemented the model package of the program. I help David identify a good design for generator and break his one class generator into multiple classes with strategy patthern. I come up with the IFilter idea, so iterable can be process with one line and we can customize logic into generator external.

David: 	, Performed Debugging


