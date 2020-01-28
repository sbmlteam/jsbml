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
package examples.latex;

import org.sbml.jsbml.ext.layout.Point;
import org.sbml.jsbml.ext.render.director.DissociationNode;

/**
 * Class for rendering a Dissociation node (SBGN: two concentric circles)
 * @author DavidVetter
 */
public class LaTeXDissociationNode extends LaTeXSBGNProcessNode
  implements DissociationNode<String> {

  public LaTeXDissociationNode(double lineWidth, double processNodeSize) {
    super(lineWidth, processNodeSize);
  }


  @Override
  public String draw(double x, double y, double z, double width, double height,
    double depth, double rotationAngle, Point rotationCenter) {
    /** Like AssociationNode: Circular, thus rotation is irrelevant. */
    return draw(x, y, z, width, height, depth);
  }


  @Override
  public String draw(double x, double y, double z, double width, double height,
    double depth) {
    return String.format(
      "\\draw[line width=%s, draw=black, fill=white] (%spt, %spt) ellipse (%spt and %spt);%s\\draw[line width=%s, draw=black, fill=white] (%spt, %spt) ellipse (%spt and %spt);%s",
      getLineWidth(), x + width / 2, y + width / 2, width / 2, height / 2,
      System.lineSeparator(), getLineWidth(), x + width / 2, y + width / 2, width / 4,
      height / 4, System.lineSeparator());
  }
}
