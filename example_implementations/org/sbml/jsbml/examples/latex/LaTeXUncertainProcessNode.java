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

import org.sbml.jsbml.ext.layout.Point;
import org.sbml.jsbml.ext.render.director.UncertainProcessNode;


/**
 * Class for rendering a Process node (SBGN: square with question-mark)
 * @author DavidVetter
 */
public class LaTeXUncertainProcessNode extends LaTeXSBGNProcessNode
  implements UncertainProcessNode<String> {

  public LaTeXUncertainProcessNode(double lineWidth, double processNodeSize) {
    super(lineWidth, processNodeSize);
  }


  @Override
  public String draw(double x, double y, double z, double width, double height,
    double depth, double rotationAngle, Point rotationCenter) {
    StringBuffer result = new StringBuffer();
    result.append(String.format(
      "\\draw[line width=%s, fill=white, draw=black, rotate around={%s:(%spt,%spt)}] (%spt, %spt) rectangle ++(%spt, %spt);%s",
      getLineWidth(), rotationAngle, rotationCenter.getX(),
      rotationCenter.getY(), x, y, width, height, System.lineSeparator()));
    result.append(String.format("\\node[rotate=%s] (uncertain%s%s) at (%spt, %spt) {?};", -rotationAngle, x, y, x + width/2, y + height/2));
    return result.toString();
  }


  @Override
  public String draw(double x, double y, double z, double width, double height,
    double depth) {
    StringBuffer result = new StringBuffer();
    result.append(String.format(
      "\\draw[line width=%s, fill=white, draw=black] (%spt, %spt) rectangle ++(%spt, %spt);%s",
      getLineWidth(), x, y, width, height, System.lineSeparator()));
    result.append(String.format("\\node[] (uncertain%s%s) at (%spt, %spt) {?};", x, y, x + width/2, y + height/2));
    return result.toString();
  }
}
