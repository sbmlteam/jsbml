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

import org.sbml.jsbml.ext.render.Ellipse;
import org.sbml.jsbml.ext.render.LocalStyle;
import org.sbml.jsbml.ext.render.Polygon;
import org.sbml.jsbml.ext.render.RelAbsVector;
import org.sbml.jsbml.ext.render.RenderGroup;
import org.sbml.jsbml.ext.render.director.SourceSink;

/**
 * Drawing expert for a source/sink
 * {@link org.sbml.jsbml.ext.layout.SpeciesGlyph} (SBGN: empty set/diameter-symbol)
 * 
 * @author DavidVetter
 */
public class RenderSourceSink extends SourceSink<LocalStyle> {

  private String stroke, fill;
  
  public RenderSourceSink(double strokeWidth, String stroke, String fill) {
    super();
    setLineWidth(strokeWidth);
    this.stroke = stroke;
    this.fill = fill;
  }
  
  @Override
  public LocalStyle draw(double x, double y, double z, double width,
    double height, double depth) {
    RenderGroup result = new RenderGroup();
    Ellipse circle = result.createEllipse();
    circle.setCx(new RelAbsVector(width/2));
    circle.setCy(new RelAbsVector(height/2));
    circle.setRx(new RelAbsVector(0.45*Math.min(width, height)));
    
    circle.setStroke(stroke);
    circle.setStrokeWidth(getLineWidth());
    circle.setFill(fill);
    
    Polygon diagonal = result.createPolygon();
    if(width > height) {
      RenderLayoutBuilder.addRenderPoint(diagonal, (width+height)/2, 0);
      RenderLayoutBuilder.addRenderPoint(diagonal, (width-height)/2, height);
    } else {
      RenderLayoutBuilder.addRenderPoint(diagonal, width, (height-width)/2);
      RenderLayoutBuilder.addRenderPoint(diagonal, 0, (width+height)/2);
    }
    diagonal.setStroke(stroke);
    diagonal.setStrokeWidth(getLineWidth());
    diagonal.setFill(fill);
    
    return new LocalStyle(result);
  }
}
