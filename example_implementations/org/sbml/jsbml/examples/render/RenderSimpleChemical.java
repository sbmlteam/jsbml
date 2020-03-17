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

import org.sbml.jsbml.ext.render.Ellipse;
import org.sbml.jsbml.ext.render.LocalStyle;
import org.sbml.jsbml.ext.render.Polygon;
import org.sbml.jsbml.ext.render.RelAbsVector;
import org.sbml.jsbml.ext.render.RenderGroup;
import org.sbml.jsbml.ext.render.director.SimpleChemical;

/**
 * Drawing expert for a simple chemical
 * {@link org.sbml.jsbml.ext.layout.SpeciesGlyph} (SBGN: circle)
 * 
 * @author DavidVetter
 */
public class RenderSimpleChemical extends SimpleChemical<LocalStyle> {
  private String stroke, fill, clone;
  
  public RenderSimpleChemical(double strokeWidth, String stroke, String fill, String clone) {
    super();
    setLineWidth(strokeWidth);
    this.stroke = stroke;
    this.fill = fill;
    this.clone = clone;
  }
  
  @Override
  public LocalStyle draw(double x, double y, double z, double width, double height,
    double depth) {
    RenderGroup chemical = new RenderGroup();
    double radius = Math.min(width, height) / 2;
    
    Ellipse background = chemical.createEllipse();
    background.setCx(new RelAbsVector(0, 50d));
    background.setCy(new RelAbsVector(0, 50d));
    background.setRx(new RelAbsVector(radius));
    background.setStroke(stroke);
    background.setStrokeWidth(0);
    background.setFill(fill);
    
    if(hasCloneMarker()) {
      Polygon cloneMarker = chemical.createPolygon();
      /**
       * The factor before radius is cos(asin(2*(0.7-0.5))), where the clone
       * marker starts at 70% (from the top) of the height.
       * (All the numbers are either basic trigonometry, or manually appromated)
       */
      RenderLayoutBuilder.addRenderPoint(cloneMarker, width/2 - 0.9165151*radius, 0.7*height);
      
      /**
       * Add the base-points on tangents to the circle: Where exactly along
       * those tangents is decided by baseStrength (which is manually
       * approximated)
       */
      double baseStrength = radius/3; // manually approximated
      RenderLayoutBuilder.addRenderCubicBezier(cloneMarker, 
        width/2 - 0.9165151*radius + baseStrength, 0.7*height + 2.291288*baseStrength, 
        width/2 + 0.9165151*radius - baseStrength, 0.7*height + 2.291288*baseStrength, 
        width/2 + 0.9165151*radius, 0.7*height);
      cloneMarker.setStroke(clone);
      cloneMarker.setStrokeWidth(0);
      cloneMarker.setFill(clone);
    }
    Ellipse chemicalEllipse = background.clone();
    chemicalEllipse.setStroke(stroke);
    chemicalEllipse.setStrokeWidth(getLineWidth());
    chemicalEllipse.unsetFill();
    chemical.addElement(chemicalEllipse);
    
    return new LocalStyle(chemical);
  }
}
