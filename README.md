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
### Milestone 01:
- **Fred:**
     - Performed Debugging
     - Discuss Design Decisions
     - Implemented Models.
     - Implemented Displayer.
     - Implemented IFilter.
     - Assisted in Generator Design
     - Refactored Code.
     - Code Review.
     - Wrote test code.
- **Adam:**
     - Managed the creation and updates to the UML
     - Discuss Design Decisions
     - Coded the CommandLineParser and Configuration
     - Performed Debugging
     - Created the README.
     - Wrote test code.
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
     - Wrote test code.
  
### Milestone 02:  
- **Fred:**  
     - Designed the overall decorator pattern, break down generator into classParserAnalyzer,
     	and RelationParserAnalyzer, so that all fields are open to extension
     - Refactored code out of Generator into Relationship.
     - Implemented basic class parsing analyzer from previous generator code.
     - Figured out generics parsing with ASM, parse all the generic signature in JVM.
     - Refactor TypeModel to use decorator pattern, so all generic parameters and types can be represented.
     - Refactored Model to return cardinality information.
     - Add the InstructionModel, so that analyzers can analyzer the details of each java bytecode.
     - Resolved bugs discovered in Model.
     - Wrote test.
     - Code reviewed.
- **Adam:**  
     - Updated UML
     - Discovered bugs in output inconsistency with Milestone requirements.
     - Added JSON file support.
     - Wrote Python to get qualified class names.
- **David:**  
     - Added bijective arrow Relationship support in GraphViz.
     - Added GraphViz cardinality support in Relationship.
     - Assisted in Relation and Generator refactor.
     - Refactored Configuration to be more extensible.
     - Wrote test.
     - Code reviewed.
     - Cleaned code to for readability.
     - Fixed and improved Python script from Adam.
     - Debug GraphViz generator and Model integrations.
     - Wrote ExplorerRunner to display generated output.
     - Added missing JavaDocs in all packages when possible.
     - Resolved bugs discovered in Generator.
### Milestone 03:  
- **Fred:**  
     - Refactor the configuration, so that all configurations are stored as a Map from string to string.
     - Make Configuration a factory of any Configurable, which defines its default configuration
     - Rewrote the commandline parser so it takes config jsons and can override specific configuration
     - Implement the SingletonAnalyzer
- **Adam:**   
- **David:**  
     - Implement the FavorCompositionAnalyzer
     - Worked with Adam to implement Bidirectional Analyzer
     - Worked with Fred on CommandLine Parser.
     - Discussed about the new config JSON format.
     - Refactored code to meet style standard and added javadoc.
