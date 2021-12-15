/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2022 jointly by the following organizations:
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
package org.sbml.jsbml.examples.latex;

import org.sbml.jsbml.ext.layout.CurveSegment;
import org.sbml.jsbml.ext.render.director.Inhibition;

/**
 * Class for drawing an Inhibition-arc (specified by a
 * {@link org.sbml.jsbml.ext.layout.ReactionGlyph}'s
 * {@link org.sbml.jsbml.ext.layout.SpeciesReferenceGlyph}):<br>
 * SBGN arrow-head: orthogonal line 
 * 
 * @author David Vetter
 */
public class LaTeXInhibition extends LaTeXSBGNArc
  implements Inhibition<String> {

  private double arrowScale;
  
  public LaTeXInhibition(double arrowScale) {
    this.arrowScale = arrowScale;
  }

  @Override
  public String drawHead(CurveSegment curveSegment, double lineWidth) {
    return String.format("\t\\draw[line width=%s, arrows={-|[scale=%s]}] %s;",
      lineWidth, arrowScale, coordinatesForCurveSegment(curveSegment));
  }
}
