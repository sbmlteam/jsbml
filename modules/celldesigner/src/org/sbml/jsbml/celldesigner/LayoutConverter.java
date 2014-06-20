/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2014  jointly by the following organizations:
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

import java.util.List;

import jp.sbi.celldesigner.plugin.PluginCompartment;
import jp.sbi.celldesigner.plugin.PluginReaction;
import jp.sbi.celldesigner.plugin.PluginSpeciesAlias;

import org.sbml.jsbml.ext.layout.CompartmentGlyph;
import org.sbml.jsbml.ext.layout.ReactionGlyph;
import org.sbml.jsbml.ext.layout.SpeciesGlyph;
import org.sbml.jsbml.ext.layout.TextGlyph;


/**
 * @author Ibrahim Vazirabad
 * @version $Rev$
 * @since 1.0
 * @date Jun 19, 2014
 */
public class LayoutConverter {

  private static List<CompartmentGlyph> cGlyphList;
  private static List<TextGlyph> tGlyphList;
  private static List<ReactionGlyph> rGlyphList;
  private static List<SpeciesGlyph> sGlyphList;

  public static List<CompartmentGlyph> extractLayout(PluginCompartment pCompartment)
  {
    CompartmentGlyph cGlyph=new CompartmentGlyph();
    cGlyph.getBoundingBox().getDimensions().setHeight(pCompartment.getHeight());
    cGlyph.getBoundingBox().getDimensions().setWidth(pCompartment.getWidth());
    cGlyph.getBoundingBox().getPosition().setX(pCompartment.getX());
    cGlyph.getBoundingBox().getPosition().setY(pCompartment.getY());
    cGlyphList.add(cGlyph);
    TextGlyph tGlyph=new TextGlyph();
    tGlyph.getBoundingBox().getPosition().setX(pCompartment.getNamePositionX());
    tGlyph.getBoundingBox().getPosition().setY(pCompartment.getNamePositionY());
    tGlyph.setOriginOfText(pCompartment.getId());
    return cGlyphList;
  }

  public static List<SpeciesGlyph> extractLayout(PluginSpeciesAlias pSpecies)
  {
    SpeciesGlyph sGlyph=new SpeciesGlyph();
    sGlyph.getBoundingBox().getDimensions().setHeight(pSpecies.getHeight());
    sGlyph.getBoundingBox().getDimensions().setWidth(pSpecies.getWidth());
    sGlyph.getBoundingBox().getPosition().setX(pSpecies.getX());
    sGlyph.getBoundingBox().getPosition().setY(pSpecies.getY());
    sGlyphList.add(sGlyph);
    TextGlyph tGlyph=new TextGlyph();
    tGlyph.setOriginOfText(pSpecies.getSpecies().getId());
    return sGlyphList;
  }

  public static List<ReactionGlyph> extractLayout(PluginReaction pReaction)
  {
    ReactionGlyph rGlyph=new ReactionGlyph();
    rGlyphList.add(rGlyph);
    return rGlyphList;
  }
  }
