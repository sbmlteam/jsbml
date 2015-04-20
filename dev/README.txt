/*
 * $Id: README.txt 2109 2015-01-05 04:50:45Z andreas-draeger $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/dev/README.txt $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2015 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
 
Definitions for writing JSBML classes:

1) Each class must contain the header written in the class java.java 


2) For programmers using eclipse, we defined some style and code templates to facilitate coding. 
   All files are in the folder "dev/eclipse". Here is a short introduction how to integrate the 
   templates in the project properties:
   
    - codetemplates.xml defines headers and some standard JSBML comments.
      It can be included in "Java" -> "Code Style" -> "Code templates". 

    - SBML_Project_Java_style_for_Eclipse_3.6.xml defines the standard code format.
      It can be included in "Java" -> "Code Style" -> "Formatter".

    - JSBML_templates.xml define some code templates for easier coding correct JSBML constructors,
      getters and setters, etc.
      It can be included in "Java" -> "Editor" -> "Templates".
      To use these templates while programming write "JSBML" and press "CTR + tab". Then all 
      available JSBML code templates are listed. Then select the desired template by pressing 
      "enter". If you have several fields to rename use "tab" to rename them all in one go.

3) Setting up Eclipse

  To setup eclipse, in general, you have to add the src, test and resources folder to your eclipse 
  build path. As well as any jar files included in the lib folder.

  Then, you need to do an extra step to configure the annotation processor, as the different parsers 
  in JSBML are registered automatically using java annotation.

  To configure the annotation processor, follow the instructions in the page https://code.google.com/p/spi/wiki/EclipseSettings

  You can run the ParserManager class to check that the list of parsers are not empty and that they contain the parsers you need.

