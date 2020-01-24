/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2018 jointly by the following organizations:
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
package examples;

import java.io.File;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;
import org.sbml.jsbml.ext.render.director.LayoutDirector;

/**
 * Main class for a very simple example using the
 * {@link org.sbml.jsbml.ext.render.director} classes to generate a LaTeX-String:
 * 
 * Explanatory comments are provided that should make it easier to create a more robust implementation
 * catered towards any specific needs. 
 * 
 * @author David Vetter
 */
public class SimpleExample {
  // TODO: provide explanations for the implementation and point at sbml2tikz
  public static void main(String[] args) {
    
    /**
     * We will work with an example-file based on the complete example given in
     * the layout-specification (v1, r1), that contains all SBGN-elements
     * supported by the render.director:
     */
    File file = new File("extensions/render/resources/examples/layout_spec_example.xml");
    // File file = new File("extensions/render/resources/examples/unlaidout_spec_example.xml");
    
    /**
     * Notice:
     * The LayoutDirector cannot handle files w/o any Layout information: it
     * needs a
     * layout with dimensions set, and glyphs specified (but potentially lacking
     * bounding boxes etc).
     * This is because one might want to only add layout-information for a
     * subpart of the entire model -- this subpart is then specified by the
     * preexisting glyphs. If however all species, reaction etc are to be laid
     * out, use the {@link GlyphCreator} class before calling the LayoutDirector
     */
    System.out.println("Reading file " + file);
    try {
      /**
       * The LayoutDirector will in this example produce a String that can be
       * read (and type-set) as a LaTeX-document. The implementation here is
       * quite basic, and more serves to give an overview. A complete
       * implementation is provided by the SBML2LaTeX tool
       * (https://github.com/draeger-lab/SBML2LaTeX). The implementation here
       * also does not make use of the available tikz-library sbgntikz
       * (https://github.com/Adrienrougny/sbgntikz), which does implement the
       * separation between TextGlyphs (labels) and SpeciesGlyphs (~nodes)
       * present in SBML directly
       * Apart from the read-in file, the Director then gets a LayoutBuilder and
       * a LayoutAlgorithm.
       * In this example, the LayoutAlgorithm will only position the TextGlyphs
       * (of which some are
       * not yet positioned).
       * Further explanations are provided in the BasicLayoutBuilder and
       * BasicLayoutAlgorithm classes, as well as in the LaTeXSBGNArc and
       * LaTeXSBGNProcessNode classes.
       */
      LayoutDirector<String> director = new LayoutDirector<String>(file,
        new BasicLayoutBuilder(1, 1.5, 10), new BasicLayoutAlgorithm());
      director.run();
      System.out.println("LaTeX-document:\n");
      System.out.println(director.getProduct());
    } catch (XMLStreamException | IOException e) {
      e.printStackTrace();
    }
  }
}
