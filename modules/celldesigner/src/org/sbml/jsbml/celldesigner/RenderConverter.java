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
 * 6. Marquette University, Milwaukee, WI, USA
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.celldesigner;

import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.render.ColorDefinition;
import org.sbml.jsbml.ext.render.Ellipse;
import org.sbml.jsbml.ext.render.LocalRenderInformation;
import org.sbml.jsbml.ext.render.LocalStyle;
import org.sbml.jsbml.ext.render.Polygon;
import org.sbml.jsbml.ext.render.Rectangle;
import org.sbml.jsbml.ext.render.RelAbsVector;
import org.sbml.jsbml.ext.render.RenderGroup;

import jp.sbi.celldesigner.plugin.PluginCompartment;
import jp.sbi.celldesigner.plugin.PluginSpeciesAlias;


/**
 * @author Ibrahim Vazirabad
 * @since 1.0
 */
public class RenderConverter {

  /**
   *
   */
  final static double depth  =  1d;
  /**
   *
   */
  final static double z  =  0d;

  /**
   *
   * @param pCompartment
   * @param renderInfo
   * @param layout
   */
  public static void extractRenderInformation(PluginCompartment pCompartment, LocalRenderInformation renderInfo, Layout layout)
  {
    renderInfo.addColorDefinition(new ColorDefinition(pCompartment.getId(), pCompartment.getLineColor()));
    LocalStyle localStyle = renderInfo.getListOfLocalStyles().get("compartmentStyle");

    localStyle.getIDList().add("cGlyph_"+pCompartment.getId());

    RenderGroup group = localStyle.getGroup();
    Rectangle rectangle = new Rectangle();
    rectangle.setX(new RelAbsVector(pCompartment.getX()));
    rectangle.setY(new RelAbsVector(pCompartment.getY()));
    rectangle.setZ(new RelAbsVector(z));
    group.registerChild(rectangle);
    group.setStroke(pCompartment.getId());
    group.setStrokeWidth(pCompartment.getThickness());
  }

  /**
   *
   * @param pSpeciesAlias
   * @param renderInfo
   * @param layout
   */
  public static void extractRenderInformation(PluginSpeciesAlias pSpeciesAlias, LocalRenderInformation renderInfo, Layout layout)
  {
    renderInfo.addColorDefinition(new ColorDefinition(pSpeciesAlias.getAliasID(), pSpeciesAlias.getColor()));
    LocalStyle localStyle = renderInfo.getListOfLocalStyles().get("speciesAliasStyle");

    localStyle.getIDList().add("sGlyph_" + pSpeciesAlias.getAliasID());

    RenderGroup group = localStyle.getGroup();
    String speciesName = pSpeciesAlias.getType();

    if (speciesName.equals("SIMPLE MOLECULE") || speciesName.equals("ION") || speciesName.equals("DRUG"))
    {
      Ellipse ellipse = new Ellipse();
      ellipse.setCx(new RelAbsVector(pSpeciesAlias.getX()));
      ellipse.setCy(new RelAbsVector(pSpeciesAlias.getY()));
      ellipse.setCz(new RelAbsVector(z));
      group.registerChild(ellipse);
    }
    else if (speciesName.equals("GENE") || speciesName.equals("PROTEIN"))
    {
      Rectangle rectangle = new Rectangle();
      rectangle.setX(new RelAbsVector(pSpeciesAlias.getX()));
      rectangle.setY(new RelAbsVector(pSpeciesAlias.getY()));
      rectangle.setZ(new RelAbsVector(z));
      group.registerChild(rectangle);
    }
    else if (speciesName.equals("RNA") || speciesName.equals("PHENOTYPE") || speciesName.equals("RECEPTOR"))
    {
      Polygon polygon = new Polygon();
      polygon.setStroke(pSpeciesAlias.getAliasID());
      group.registerChild(polygon);
    }
  }
}
