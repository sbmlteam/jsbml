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

import org.sbml.jsbml.ext.render.LocalStyle;
import org.sbml.jsbml.ext.render.Polygon;
import org.sbml.jsbml.ext.render.RenderGroup;
import org.sbml.jsbml.ext.render.director.PerturbingAgent;

/**
 * Drawing expert for a perturbing agent
 * {@link org.sbml.jsbml.ext.layout.SpeciesGlyph} (SBGN: biconcave hexagon)
 * 
 * @author DavidVetter
 */
public class RenderPerturbingAgent extends PerturbingAgent<LocalStyle> {
  private String stroke, fill, clone;
  private double inset;
  
  public RenderPerturbingAgent(double strokeWidth, String stroke, String fill, String clone) {
    super();
    setLineWidth(strokeWidth);
    this.stroke = stroke;
    this.fill = fill;
    this.clone = clone;
    this.inset = 0.29;
  }
  
  /**
   * @param strokeWidth
   * @param stroke
   * @param fill
   * @param clone
   * @param insetProportion
   *        how deep are the sides to be indented (factor to be applied to
   *        height), i.e. insetProportion * height will be the
   *        side-indentation-depth
   *        (Default: 1/(2*sqrt(3)) approx 0.29)
   */
  public RenderPerturbingAgent(double strokeWidth, String stroke, String fill, String clone, double insetProportion) {
    super();
    setLineWidth(strokeWidth);
    this.stroke = stroke;
    this.fill = fill;
    this.clone = clone;
    this.inset = insetProportion;
  }
  
  @Override
  public LocalStyle draw(double x, double y, double z, double width, double height,
    double depth) {
    RenderGroup agent = new RenderGroup();
    
    Polygon background = agent.createPolygon();
    RenderLayoutBuilder.addRenderPoint(background, 0, 0);
    RenderLayoutBuilder.addRenderPoint(background, inset * height,  height / 2);
    RenderLayoutBuilder.addRenderPoint(background, 0,  height);
    RenderLayoutBuilder.addRenderPoint(background, width, height);
    RenderLayoutBuilder.addRenderPoint(background, width - (inset * height),  height / 2);  
    RenderLayoutBuilder.addRenderPoint(background, width, 0);  
    
    background.setStroke(stroke);
    background.setStrokeWidth(0);
    background.setFill(fill);
    
    if(hasCloneMarker()) {
      Polygon cloneMarker = agent.createPolygon();
      RenderLayoutBuilder.addRenderPoint(cloneMarker, 0.6*inset*height, 0.7*height);
      RenderLayoutBuilder.addRenderPoint(cloneMarker, 0, height);
      RenderLayoutBuilder.addRenderPoint(cloneMarker, width, height);
      RenderLayoutBuilder.addRenderPoint(cloneMarker, width - (0.6*inset*height), 0.7*height);
      
      cloneMarker.setStroke(clone);
      cloneMarker.setStrokeWidth(0);
      cloneMarker.setFill(clone);
    }
    Polygon agentPoly = background.clone();
    
    agentPoly.setStroke(stroke);
    agentPoly.setStrokeWidth(getLineWidth());
    agentPoly.unsetFill();
    agent.addElement(agentPoly);
    
    return new LocalStyle(agent);
  }
}
