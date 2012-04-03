                            SBML Bar Graph

                            Michael Hucka
                  Computing + Mathematical Sciences
             Division of Engineering and Applied Science
                  California Institute of Technology
                      Pasadena, California, USA

    The latest version of the binary distributions can be found at
	     http://sourceforge.net/projects/jsbml/files/

	  The latest version of the sources can be found at
      http://jsbml.svn.sourceforge.net/viewvc/jsbml/trunk/core/

   For more information about JSBML or the SBML Bar Graph, contact:

                            The JSBML Team
                         http://www.sbml.org/
                    mailto:jsbml-team@caltech.edu

	  This code is licensed under the LGPL version 2.1.
	    Please see the file "LICENSE.txt" for details.


   ,--------------------------------------------------------------.
  | Table of contents                                             |
  | 1. Quick start                                                |  
  | 2. More information, and how to build the sources             |
   `--------------------------------------------------------------'


--------------
1. QUICK START
--------------

First, check if there is a ready-to-run executable version of the
program from the download site.  If there is, then download that to
your computer, unpack it, and run it on your system (which you should
be able to do simply by double-clicking the application icon in your
graphical file browser).  The executable requires Java version 1.5 or
greater at run-time to be present on your computer.

If you cannot find a ready-to-run executable, or it does not work for
you, please locate the file "sbmlbargraph.jar", and try executing the
following command in a command shell or terminal window:

   java -jar sbmlbargraph.jar

Once the application starts up, use the File->Open command to open
an SBML file on your computer.  After the application opens the file
and reads it, it will display a bar graph comparing the different
number of SBML components in the file.

Optionally, you can supply the name of the file to be read immediately
upon startup, by providing the "--file" command-line argument together
with the jar file:

   java -jar sbmlbargraph.jar --file PATH-TO-FILE   

To find out about other possible command-line arguments, run the
following command:

   java -jar sbmlbargraph.jar --help


-------------------------------------------------
2. MORE INFORMATION, AND HOW TO BUILD THE SOURCES
-------------------------------------------------

SBML Bar Graph is a simple but full-fledged Java GUI application
demonstrating a simple use of JSBML, the Java library for reading,
writing and manipulating SBML (http://sbml.org).  SBML Bar Graph has
only one purpose: to read an SBML file, count the number of different
elements defined in the SBML model, and draw a bar graph comparing the
numbers of the different elements.

We provide ready-to-run versions of SBML Bar Graph available for
download from the JSBML project area on SourceForge.net.  Once
downloaded and unpacked from their archive, they can be run directly
on Mac OS X and Windows systems.

To build SBML Bar Graph from the source files, we provide an Ant
build.xml file in the top level.  It has the following targets:

  ant clean               -- removes the compiled class files
  ant build               -- build the class files
  ant create-runnable-jar -- create a runnable .jar file

After unpacking the source archive, you should be able to run 
"ant build" followed by "ant create-runnable-jar", and then find
the finished .jar file in the bin/ subdiretory.

To create the Mac application, it is currently necessary to use
Eclipse and its Mac OS application bundle export capability.  We have
only tested Eclipse 3.7 so far.  The video in the file titled
"how to create a Mac standalone app using Eclipse.dov" in the doc/
subdirectory illustrates how to do this.

To create the Windows application, we use launch4j.  See the
README.txt file in the subdirectory src/windows-launcher/ for more
information.


----------------------------------------------------------------------
The following is for [X]Emacs users.  Please leave in place.
Local Variables:
fill-column: 70
End:
----------------------------------------------------------------------
