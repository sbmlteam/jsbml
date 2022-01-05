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
import org.sbml.jsbml.ext.render.Ellipse;
import org.sbml.jsbml.ext.render.LocalStyle;
import org.sbml.jsbml.ext.render.RelAbsVector;
import org.sbml.jsbml.ext.render.RenderGroup;
import org.sbml.jsbml.ext.render.director.AssociationNode;

/**
 * Drawing expert for rendering an AssociationNode (SBGN: solid black bullet)
 * 
 * @author DavidVetter
 */
public class RenderAssociationNode extends RenderSBGNProcessNode
  implements AssociationNode<LocalStyle> {

  public RenderAssociationNode(double strokeWidth, String stroke, String fill,
    double nodeSize) {
    super(strokeWidth, stroke, fill, nodeSize);
  }


  @Override
  public LocalStyle draw(double x, double y, double z, double width,
    double height, double depth, double rotationAngle, Point rotationCenter) {
    RenderGroup group = new RenderGroup();
    // This applies to the r.g.'s curve too.
    group.setStroke(getStroke());
    group.setStrokeWidth(getLineWidth());
    
    Ellipse circle = group.createEllipse();
    /**
     * For these attributes, the BoundingBox is needed, irrespective of whether
     * the reactionGlyph's curve is set
     */
    circle.setCx(new RelAbsVector(width/2));
    circle.setCy(new RelAbsVector(height/2));
    circle.setRx(new RelAbsVector(getNodeSize()/2));
    
    circle.setFill(getStroke());
    circle.setStroke(getStroke());
    
    return new LocalStyle(group);
  }
}
