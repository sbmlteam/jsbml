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
package org.sbml.jsbml.examples.render;

import org.sbml.jsbml.ext.layout.Point;
import org.sbml.jsbml.ext.render.HTextAnchor;
import org.sbml.jsbml.ext.render.LocalStyle;
import org.sbml.jsbml.ext.render.Polygon;
import org.sbml.jsbml.ext.render.RelAbsVector;
import org.sbml.jsbml.ext.render.RenderGroup;
import org.sbml.jsbml.ext.render.Text;
import org.sbml.jsbml.ext.render.VTextAnchor;
import org.sbml.jsbml.ext.render.director.UncertainProcessNode;

/**
 * Drawing expert for uncertain processes (SBGN: rectangle with questionmark)
 * 
 * The questionmark will here be rotated along with the rectangle.
 * 
 * @author DavidVetter
 */
public class RenderUncertainProcessNode extends RenderSBGNProcessNode
  implements UncertainProcessNode<LocalStyle> {

  public RenderUncertainProcessNode(double strokeWidth, String stroke,
    String fill, double nodeSize) {
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
    double cos = Math.cos(rotationRadians);
    double sin = Math.sin(rotationRadians);
    
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
    
    Text questionmark = group.createText();
    double breadth = 13; // based on font-size, found manually
    double fontHeight = 20; // based on font-size, found manually 
    questionmark.setFontSize(new RelAbsVector(10));
    questionmark.setFontFamily("monospace");
    questionmark.setTextAnchor(HTextAnchor.START);
    questionmark.setVTextAnchor(VTextAnchor.TOP);
    questionmark.setTransform(new Double[] {new Double(cos), new Double(sin),
      new Double(-sin), new Double(cos),
      new Double(0.5 * (width - breadth * cos + fontHeight * sin)),
      new Double(0.5 * (height - breadth * sin - fontHeight * cos))});
    
    questionmark.setX(new RelAbsVector(0));
    questionmark.setY(new RelAbsVector(0));
    questionmark.setText("?");
    
    return new LocalStyle(group);
  }
}
