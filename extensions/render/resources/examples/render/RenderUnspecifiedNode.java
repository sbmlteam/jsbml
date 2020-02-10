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

import org.sbml.jsbml.ext.render.Ellipse;
import org.sbml.jsbml.ext.render.LocalStyle;
import org.sbml.jsbml.ext.render.Polygon;
import org.sbml.jsbml.ext.render.RenderGroup;
import org.sbml.jsbml.ext.render.director.UnspecifiedNode;

/**
 * Drawing expert for unspecified node
 * {@link org.sbml.jsbml.ext.layout.SpeciesGlyph} (SBGN: ellipse)
 * 
 * @author DavidVetter
 */
public class RenderUnspecifiedNode extends UnspecifiedNode<LocalStyle> {
private String stroke, fill, clone;
  
  public RenderUnspecifiedNode(double strokeWidth, String stroke, String fill, String clone) {
    super();
    setLineWidth(strokeWidth);
    this.stroke = stroke;
    this.fill = fill;
    this.clone = clone;
  }
  
  @Override
  public LocalStyle draw(double x, double y, double z, double width, double height,
    double depth) {
    RenderGroup node = new RenderGroup();
    
    Ellipse background = node.createEllipse();
    background.setCx(width/2); background.setAbsoluteCx(true);
    background.setCy(height/2); background.setAbsoluteCy(true);
    background.setRx(width/2); background.setAbsoluteRx(true);
    background.setRy(height/2); background.setAbsoluteRy(true);
    
    background.setStroke(stroke);
    background.setStrokeWidth(0);
    background.setFill(fill);
    
    if(hasCloneMarker()) {
      /**
       * Mathematically: unit circle --> stretch coordinates along x-axis by
       * factor width/2 and along y-axis by factor height/2
       * 
       * Compare SimpleChemical
       */
      double radius = 1; 
      double stretchX = width/2;
      double stretchY = height/2;
      
      Polygon cloneMarker = node.createPolygon();
      // The factor before radius is cos(asin(2*(0.7-0.5)))
      RenderLayoutBuilder.addRenderPoint(cloneMarker, stretchX*(1 - 0.9165151*radius), stretchY * (1 + 0.4*radius));
      
      double baseStrength = radius/3; // manually approximated
      RenderLayoutBuilder.addRenderCubicBezier(cloneMarker, 
        stretchX*(1 - 0.9165151*radius + baseStrength), stretchY * (1 + 0.4*radius + 2.291288*baseStrength), 
        stretchX*(1 + 0.9165151*radius - baseStrength), stretchY * (1 + 0.4*radius + 2.291288*baseStrength), 
        stretchX * (1 + 0.9165151*radius), 0.7*height);
      cloneMarker.setStroke(clone);
      cloneMarker.setStrokeWidth(0);
      cloneMarker.setFill(clone);
    }
    Ellipse nodeEllipse = background.clone();
    
    nodeEllipse.setStroke(stroke);
    nodeEllipse.setStrokeWidth(getLineWidth());
    nodeEllipse.unsetFill();
    
    node.addElement(nodeEllipse);
    
    return new LocalStyle(node);
  }
}
