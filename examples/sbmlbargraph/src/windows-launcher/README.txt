		 SBML Bar Graph configuration for launch4j
	Some miscellaneous notes about the files in this directory.

		      2012-03-31 <mhucka@caltech.edu>

The configuration file for launch4j in this directory has been tested with
launch4j version 3.0.2.  (http://launch4j.sourceforge.net/)

The procedure is not automated at this time.  The following are the steps
to producing a .exe for the Java SBML Bar Graph application.  There are
videos in the ../../doc/ subdirectory explaining the steps (see the note
further below).

1. Create a "Runnable JAR file" for SBML Bar Graph.  The Ant build.xml file
   at the top level contains a target called "create-runnable-jar" exactly
   for this purpose.  To use it, cd to the top level of the SBML Bar Graph
   source tree and run "ant create-runnable-jar".

2. Start launch4j.

3. Import the file "SBMLBarGraph launch4j configuration.xml" in this
   directory into launch4j using the blue folder icon in the launch4j menu
   bar.  (Confusingly, launch4j will first ask you "Discard changes?", even
   though you haven't done anything yet.  Click "yes".)

4. Click the launch4j "gear" icon in the menu bar.  This will cause it to
   generate the file SBMLBarGraph.exe in the current directory.

The video "how to create a Windows standalone app using launch4j.mov" in
the ../../doc/ subdirectory demonstrates steps 2-4 above for running
launch4j.
