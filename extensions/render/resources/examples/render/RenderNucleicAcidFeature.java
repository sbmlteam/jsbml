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

import org.sbml.jsbml.ext.render.LocalStyle;
import org.sbml.jsbml.ext.render.Polygon;
import org.sbml.jsbml.ext.render.RenderGroup;
import org.sbml.jsbml.ext.render.director.NucleicAcidFeature;


public class RenderNucleicAcidFeature extends NucleicAcidFeature<LocalStyle> {
  private String stroke, fill, clone;
  private double borderRadius;
  
  /**
   * 
   * @param strokeWidth
   * @param stroke
   * @param fill
   * @param clone
   * @param borderRadius the 'radius' (absolute measure) of the rounded corners (bottom left and right)
   */
  public RenderNucleicAcidFeature(double strokeWidth, String stroke, String fill, String clone, double borderRadius) {
    super();
    setLineWidth(strokeWidth);
    this.stroke = stroke;
    this.fill = fill;
    this.clone = clone;
    this.borderRadius = borderRadius;
  }
  
  @Override
  public LocalStyle draw(double x, double y, double z, double width, double height,
    double depth) {
    RenderGroup naFeature = new RenderGroup();
    
    Polygon background = naFeature.createPolygon();
    RenderLayoutBuilder.addRenderPoint(background, 0, height - borderRadius);
    RenderLayoutBuilder.addRenderPoint(background, 0, 0);
    RenderLayoutBuilder.addRenderPoint(background, width, 0);    
    RenderLayoutBuilder.addRenderPoint(background, width, height - borderRadius);
    // Bottom right corner:
    RenderLayoutBuilder.addRenderCubicBezier(background, 
      width, height - (borderRadius/2), width - (borderRadius/2), height, width - borderRadius, height);
    
    RenderLayoutBuilder.addRenderPoint(background, borderRadius, height);
    // Bottom left corner:
    RenderLayoutBuilder.addRenderCubicBezier(background, 
      borderRadius / 2, height, 0, height - (borderRadius/2), 0, height - borderRadius);
    
    background.setStroke(stroke);
    background.setStrokeWidth(0);
    background.setFill(fill);
    
    if(hasCloneMarker()) {
      Polygon cloneMarker = naFeature.createPolygon();
      RenderLayoutBuilder.addRenderPoint(cloneMarker, 0, height - borderRadius);
      RenderLayoutBuilder.addRenderPoint(cloneMarker, 0, 0.7*height);
      RenderLayoutBuilder.addRenderPoint(cloneMarker, width, 0.7*height);      
      RenderLayoutBuilder.addRenderPoint(cloneMarker, width, height - borderRadius);
      // Bottom right corner:
      RenderLayoutBuilder.addRenderCubicBezier(cloneMarker, 
        width, height - (borderRadius/2), width - (borderRadius/2), height, width - borderRadius, height);
      
      RenderLayoutBuilder.addRenderPoint(cloneMarker, borderRadius, height);
      // Bottom left corner:
      RenderLayoutBuilder.addRenderCubicBezier(cloneMarker, 
        borderRadius / 2, height, 0, height - (borderRadius/2), 0, height - borderRadius);
      
      cloneMarker.setStroke(clone);
      cloneMarker.setStrokeWidth(0);
      cloneMarker.setFill(clone);
    }
    
    Polygon naFeaturePoly = background.clone();
    naFeaturePoly.setStroke(stroke);
    naFeaturePoly.setStrokeWidth(getLineWidth());
    naFeaturePoly.unsetFill();
    naFeature.addElement(naFeaturePoly);
    
    return new LocalStyle(naFeature);
  }
}
