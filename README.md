# UMLify: Team FAD CSSE 374 Project
[![build status](https://ada.csse.rose-hulman.edu/zhangq2/Fad/badges/master/build.svg)](https://ada.csse.rose-hulman.edu/zhangq2/Fad/commits/master)
[![coverage report](https://ada.csse.rose-hulman.edu/zhangq2/Fad/badges/master/coverage.svg)](https://ada.csse.rose-hulman.edu/zhangq2/Fad/commits/master)

## Description:
This project takes a set of classes and draws the UML diagram for those set of classes.

## Command Line Usage:
### Usage:
`class1 class2 ... classN [(-e|--executable) <path>] (-d|--directory) <outputDirectory> (-o|--outputfile) <outputfile> [(-x|--extension) <extension>] [(-f|--filters) <filters>] [(-n|--nodesep) <nodeseparationvalue>] [-r|--recursive] [-k|--direction]`

### Arguments:
  - class1 class2 ... classN
     - **Description:** space separated list of the name of the classes you want the UMLfor
     - **Default**: java.lang.String)

  - [(-e|--executable) <path>]
      - **Description:** the name of the executable path for graphviz on your machine
      - **Default:** dot

  - (-d|--directory) <outputDirectory>
      - **Description:**the name of the directory which you want output to go to
      - **Default:** output

  - (-o|--outputfile) <outputfile>
      - **Description:**the name of the output file
      - **Default:** output

  - [(-x|--extension) <extension>]
      - **Description:**the name extension of the output file without the dot
      - **Default:** svg

  - [(-f|--filters) <filters>]
      - **Description:**use this flag if you want to filter out certain modifiers
        if public, you filter out protected and private
        if protected, you filter out private
        if empty or private, you filter out nothing
      - **Default:** private

  - [(-n|--nodesep) <nodeseparationvalue>]
      - **Description:**the node seperation value, which is greater than 0
      - **Default:** 1

  - [-r|--recursive]
      - **Description:**use this flag if you want to recursively create the UML with all superclasses

  - [-k|--direction]
      - **Description:** use this flag if you want the UML to be outputed Top down, otherwise it will be outputed Bottom up

## Member Contributions:
- **Fred:**
  - Performed Debugging
  - Discuss Design Decisions
  - Implemented Models.
  - Implemented Displayer.
  - Implemented IFilter.
  - Assisted in Generator Design
  - Refactored Code.
  - Code Review.
- **Adam:**
  - Managed the creation and updates to the UML
  - Discuss Design Decisions
  - Coded the CommandLineParser and Configuration
  - Performed Debugging
  - Created the README.
- **David:**
  - Performed Debugging
  - Discuss Design Decisions
  - Added Continuous Integration.
  - Implemented Generator.
  - Implemented Runner.
  - Integrated GraphViz.
  - Setup Project structure.
  - Created Generator Interfaces.
  - Formatted README with MarkDown syntax.
  - Refactored Code.
  - Code Review.
