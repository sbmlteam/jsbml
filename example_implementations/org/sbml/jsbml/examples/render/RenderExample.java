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
package org.sbml.jsbml.examples.render;

import java.io.File;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.examples.latex.BasicLayoutAlgorithm;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.ext.layout.LayoutModelPlugin;
import org.sbml.jsbml.ext.render.LocalRenderInformation;
import org.sbml.jsbml.ext.render.RenderLayoutPlugin;
import org.sbml.jsbml.ext.render.director.LayoutDirector;

/**
 * Main class for a simple example using the
 * {@link org.sbml.jsbml.ext.render.director} classes to generate a
 * LocalRenderInformation (from the render-plugin):<br>
 * 
 * Code-wise, this is very similar to the LaTeX-example, so the explanatory
 * comments will focus on render-specific code.<br>
 * 
 * To view the rendered result.xml-file, use e.g. COPASI
 *
 * @author DavidVetter
 */
public class RenderExample {

  public static void main(String[] args) {
    File file = new File("example_implementations/org/sbml/jsbml/examples/layout_spec_example.xml");
    System.out.println("Reading file " + file);
    try {
      /**
       * To work on the same document, read it once:
       * The layout-director will then modify the layout (side-effects) that is
       * also later retrieved from the document as ly
       */
      SBMLDocument doc = SBMLReader.read(file);
      
      /**
       * Use the same LayoutAlgorithm as in the LaTeX-example
       */
      LayoutDirector<LocalRenderInformation> director = new LayoutDirector<LocalRenderInformation>(doc,
        new RenderLayoutBuilder(), new BasicLayoutAlgorithm());
      director.run();
      
      System.out.println("Layout-Information-document:\n");
      System.out.println(director.getProduct());
      
      /**
       * Write the generated LocalLayoutInformation into the result-file.
       */
      Layout ly = ((LayoutModelPlugin) doc.getModel().getExtension(LayoutConstants.getNamespaceURI(doc.getLevel(), doc.getVersion()))).getLayout(0);
      RenderLayoutPlugin plugin = new RenderLayoutPlugin(ly); 
      ly.addPlugin(LayoutConstants.getNamespaceURI(doc.getLevel(), doc.getVersion()), plugin);
      plugin.addLocalRenderInformation(director.getProduct());
      SBMLWriter.write(doc, new File("example_implementations/org/sbml/jsbml/examples/rendered_result.xml"), "Render-example", "1");
    } catch (XMLStreamException | IOException e) {
      e.printStackTrace();
    }
  }
}
