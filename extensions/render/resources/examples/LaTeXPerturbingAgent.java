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

import org.sbml.jsbml.ext.render.director.PerturbingAgent;

/**
 * Class for drawing a perturbing agent
 * {@link org.sbml.jsbml.ext.layout.SpeciesGlyph} (SBGN: biconcave Hexagon)
 * 
 * @author DavidVetter
 */
public class LaTeXPerturbingAgent extends PerturbingAgent<String> {

  public LaTeXPerturbingAgent(double lineWidth) {
    super();
    setLineWidth(lineWidth);
  }
  
  @Override
  public String draw(double x, double y, double z, double width, double height,
    double depth) {
    return String.format(
      "\\draw[line width=%s] (%spt, %spt) -- (%spt, %spt) -- (%spt, %spt) -- (%spt, %spt) -- (%spt, %spt) -- (%spt, %spt) -- cycle;",
      getLineWidth(), x, y, x + height / 2, y + height / 2, x, y + height,
      x + width, y + height, x + width - height / 2, y + height / 2, x + width,
      y);
  }
}
