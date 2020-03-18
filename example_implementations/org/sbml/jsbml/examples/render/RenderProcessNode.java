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

import org.sbml.jsbml.ext.layout.Point;
import org.sbml.jsbml.ext.render.LocalStyle;
import org.sbml.jsbml.ext.render.Polygon;
import org.sbml.jsbml.ext.render.RenderGroup;
import org.sbml.jsbml.ext.render.director.ProcessNode;

/**
 * Drawing expert for rendering a Process Node (SBGN: square)
 * 
 * @author DavidVetter
 */
public class RenderProcessNode extends RenderSBGNProcessNode implements ProcessNode<LocalStyle> {

  /**
   * 
   * @param strokeWidth
   * @param stroke
   * @param fill
   * @param nodeSize side-length of the process-node's square
   */
  public RenderProcessNode(double strokeWidth, String stroke, String fill, double nodeSize) {
    super(strokeWidth, stroke, fill, nodeSize);
  }

  @Override
  public LocalStyle draw(double x, double y, double z, double width,
    double height, double depth, double rotationAngle, Point rotationCenter) {
    RenderGroup group = new RenderGroup();
    // This applies to the r.g.'s curve too.
    group.setStroke(getStroke());
    group.setStrokeWidth(getLineWidth());
    double rotationRadians = Math.toRadians(rotationAngle);

    Polygon square = group.createPolygon();
    
    x = -getNodeSize() / 2;
    y = -getNodeSize() / 2;
    addRotatedRenderPoint(square, x, y, rotationRadians, width / 2, height / 2);
    
    y = getNodeSize() / 2;
    addRotatedRenderPoint(square, x, y, rotationRadians, width / 2, height / 2);
    
    x = getNodeSize() / 2;
    addRotatedRenderPoint(square, x, y, rotationRadians, width / 2, height / 2);
    
    y = -getNodeSize() / 2;
    addRotatedRenderPoint(square, x, y, rotationRadians, width / 2, height / 2);
    square.setFill(getFill());
    return new LocalStyle(group);
  }
}
