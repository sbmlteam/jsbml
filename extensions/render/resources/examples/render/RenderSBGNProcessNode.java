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
package examples.render;

import org.sbml.jsbml.ext.layout.Curve;
import org.sbml.jsbml.ext.layout.CurveSegment;
import org.sbml.jsbml.ext.layout.Point;
import org.sbml.jsbml.ext.render.LocalStyle;
import org.sbml.jsbml.ext.render.Polygon;
import org.sbml.jsbml.ext.render.director.SBGNProcessNode;

/**
 * Class for generalising over common behaviour of the SBGNProcessNodes and
 * satisfy the interface
 * 
 * @author DavidVetter
 */
public abstract class RenderSBGNProcessNode implements SBGNProcessNode<LocalStyle> {
  private Point substratePort, productPort;
  private double lineWidth, nodeSize;
  private String stroke, fill;
  
  
  public RenderSBGNProcessNode(double strokeWidth, String stroke, String fill, double nodeSize){
    this.stroke = stroke;
    this.fill = fill;
    this.lineWidth = strokeWidth;
    this.nodeSize = nodeSize;
  }

  @Override
  public LocalStyle draw(double x, double y, double z, double width,
    double height, double depth) {
    return draw(x, y, z, width, height, depth, 0, new Point(x + width/2, y + height/2));
  }

  // This method is not used in this implementation
  @Override
  public LocalStyle draw(Curve curve, double rotationAngle,
    Point rotationCenter) {
    return null;
  }

  // This method is not used in this implementation
  @Override
  public LocalStyle drawCurveSegment(CurveSegment segment, double rotationAngle,
    Point rotationCenter) {
    return null;
  }

  @Override
  public void setPointOfContactToSubstrate(Point pointOfContactToSubstrate) {
    substratePort = pointOfContactToSubstrate;
  }

  @Override
  public Point getPointOfContactToSubstrate() {
    return substratePort;
  }

  @Override
  public void setPointOfContactToProduct(Point pointOfContactToProduct) {
    productPort = pointOfContactToProduct;
  }

  @Override
  public Point getPointOfContactToProduct() {
    return productPort;
  }

  @Override
  public double getLineWidth() {
    return lineWidth;
  }

  @Override
  public void setLineWidth(double lineWidth) {
    this.lineWidth = lineWidth;
  }
  
  /**
   * @return the Fill-colour's render-id
   */
  public String getFill() {
    return fill;
  }
  
  /**
   * @return the stroke-colour's render-id
   */
  public String getStroke() {
    return stroke;
  }
  
  /**
   * @return the size of the process-node (diameter or side-length)
   */
  public double getNodeSize() {
    return nodeSize;
  }
  
  /**
   * Adds a point (x,y) after rotating it around (0,0) by the given radians, and translating it by 
   * (centreX, centreY), to the given polygon
   *  
   * @param poly
   * @param x
   * @param y
   * @param radians
   * @param centreX
   * @param centreY
   */
  public static void addRotatedRenderPoint(Polygon poly, double x, double y,
    double radians, double centreX, double centreY) {
    RenderLayoutBuilder.addRenderPoint(poly,
      (Math.cos(radians) * x - Math.sin(radians) * y) + centreX,
      (Math.sin(radians) * x + Math.cos(radians) * y) + centreY);
  }
}
