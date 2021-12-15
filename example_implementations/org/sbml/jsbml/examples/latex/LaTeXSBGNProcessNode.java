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

import org.sbml.jsbml.ext.layout.Curve;
import org.sbml.jsbml.ext.layout.CurveSegment;
import org.sbml.jsbml.ext.layout.Point;
import org.sbml.jsbml.ext.render.director.SBGNProcessNode;


/**
 * Abstract class that collects generic behaviour for the various
 * ProcessNode-Classes:
 * Here, the curve-attribute of a {@link ReactionGlyph} is understood to specify only
 * the "whiskers" by which the central square or circle is connected to its
 * substrates and products. The square/circle need be implemented by the
 * subclasses and is the only difference between them
 * 
 * @author DavidVetter
 */
public abstract class LaTeXSBGNProcessNode implements SBGNProcessNode<String> {
  private Point substratePort, productPort;
  private double lineWidth;
  private double processNodeSize;
  
  /**
   * @param lineWidth
   * @param processNodeSize
   *        the size of the process-square/circle in case the curve-attribute is
   *        set
   */
  public LaTeXSBGNProcessNode(double lineWidth, double processNodeSize) {
    this.lineWidth = lineWidth;
    this.processNodeSize = processNodeSize;
  }

  @Override
  public String draw(Curve curve, double rotationAngle, Point rotationCenter) {
    StringBuffer result = new StringBuffer();
    for (CurveSegment cs : curve.getListOfCurveSegments()) {
      result.append(drawCurveSegment(cs, rotationAngle, rotationCenter));
    }
    /** Add the square/circle after the curve to render it on top of it */
    result.append(draw(rotationCenter.getX() - getProcessNodeSize() / 2,
      rotationCenter.getY() - getProcessNodeSize() / 2,
      rotationCenter.getZ() - getProcessNodeSize() / 2, getProcessNodeSize(),
      getProcessNodeSize(), getProcessNodeSize(), rotationAngle, rotationCenter));
    return result.toString();
  }

  @Override
  public String drawCurveSegment(CurveSegment segment, double rotationAngle,
    Point rotationCenter) {
    // TODO What to use the rotation-angle for?
    // TODO: this could be cubic bezier!
    return String.format("\\draw[line width=%s] (%spt, %spt) -- (%spt, %spt);%s",
      lineWidth, segment.getStart().getX(), segment.getStart().getY(),
      segment.getEnd().getX(), segment.getEnd().getY(), System.lineSeparator());
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
   * @return the size of the process-node element (square or circle), if a curve is set
   */
  public double getProcessNodeSize() {
    return processNodeSize;
  }
  
  /**
   * @param size the size of the process-node element (square or circle), if a curve is set
   */
  public void setProcessNodeSize(double size) {
    processNodeSize = size;
  }
}
