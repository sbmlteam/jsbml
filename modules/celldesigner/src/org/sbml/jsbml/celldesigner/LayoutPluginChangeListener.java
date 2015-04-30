/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2015 jointly by the following organizations:
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

import jp.sbi.celldesigner.plugin.PluginCompartment;
import jp.sbi.celldesigner.plugin.PluginListOf;
import jp.sbi.celldesigner.plugin.PluginModel;
import jp.sbi.celldesigner.plugin.PluginReaction;
import jp.sbi.celldesigner.plugin.PluginSpeciesAlias;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.ext.layout.CompartmentGlyph;
import org.sbml.jsbml.ext.layout.GraphicalObject;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.ReactionGlyph;
import org.sbml.jsbml.ext.layout.SpeciesGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceRole;
import org.sbml.jsbml.ext.layout.TextGlyph;


/**
 * @author Ibrahim Vazirabad
 * @version $Rev$
 * @since 1.0
 * @date Sep 10, 2014
 */
public class LayoutPluginChangeListener {

  /**
   * @param glyph
   * @param plugModel
   */
  public static void addOrChangeGlyphInfoToCellDesigner(GraphicalObject glyph, PluginModel plugModel)
  {
    if (glyph instanceof ReactionGlyph)
    {
      String cellDesignerID = glyph.getId().substring(glyph.getId().indexOf("_")+1);
      PluginReaction pReaction = plugModel.getReaction(cellDesignerID);
      ReactionGlyph rGlyph = (ReactionGlyph)glyph;

      if (pReaction != null)
      {
        ListOf<SpeciesReferenceGlyph> list = rGlyph.getListOfSpeciesReferenceGlyphs();
        for (SpeciesReferenceGlyph sr: list)
        {
          SpeciesReferenceRole role = sr.getSpeciesReferenceRole();
        }
      }
    }
    //finished
    else if (glyph instanceof SpeciesGlyph)
    {
      SpeciesGlyph sGlyph = (SpeciesGlyph)glyph;
      String speciesID = sGlyph.getReference().substring(sGlyph.getReference().indexOf("_")+1); //refers to species;
      if (speciesID!=null && plugModel.getSpecies(speciesID)!=null)
      {
        PluginSpeciesAlias pAlias = plugModel.getSpecies(speciesID).getSpeciesAlias(0);
        if (pAlias != null)
        {
          pAlias.setFramePosition(sGlyph.getBoundingBox().getPosition().getX(),
            sGlyph.getBoundingBox().getPosition().getY());
          pAlias.setFrameSize(sGlyph.getBoundingBox().getDimensions().getWidth(),
            sGlyph.getBoundingBox().getDimensions().getHeight());
        }
      }

    }
    //finished
    else if (glyph instanceof CompartmentGlyph)
    {
      CompartmentGlyph cGlyph = (CompartmentGlyph)glyph;
      String cellDesignerID = cGlyph.getReference(); //refers to compartment;
      if (cellDesignerID!=null)
      {
        PluginCompartment pCompartment = plugModel.getCompartment(cellDesignerID);
        if (pCompartment != null)
        {
          pCompartment.setX(cGlyph.getBoundingBox().getPosition().getX());
          pCompartment.setY(cGlyph.getBoundingBox().getPosition().getY());
          pCompartment.setHeight(cGlyph.getBoundingBox().getDimensions().getHeight());
          pCompartment.setWidth(cGlyph.getBoundingBox().getDimensions().getWidth());
        }
      }
    }
  }

  /**
   * @param glyph
   * @param plugModel
   */
  public static void removeGlyphInfoFromCellDesigner(GraphicalObject glyph, PluginModel plugModel)
  {
    Layout layout = (Layout) glyph.getModel().getExtension("layout");
    if (glyph instanceof ReactionGlyph)
    {
      String cellDesignerID = glyph.getId().substring(glyph.getId().indexOf("_")+1);
      PluginReaction pReaction = plugModel.getReaction(cellDesignerID);
      ReactionGlyph rGlyph = (ReactionGlyph) glyph;
      if (pReaction != null)
      {

      }
    }
    //finished
    else if (glyph instanceof SpeciesGlyph)
    {
      SpeciesGlyph sGlyph = (SpeciesGlyph)glyph;
      String speciesID = sGlyph.getReference().substring(sGlyph.getReference().indexOf("_")+1); //refers to species;
      String speciesAliasID = sGlyph.getId().substring(sGlyph.getId().indexOf("_")+1); //refers to speciesAlias;
      if (speciesID!=null)
      {
        //get and remove all PluginSpeciesAliases for a particular species.
        PluginListOf list = plugModel.getListOfAllSpeciesAlias(speciesID);
        if (list!=null) {
          for (int i = 0; i<list.size(); i++)
          {
            list.remove(0);
          }
        }
      }
      //remove TextGlyphs
      ListOf<TextGlyph> list = layout.getListOfTextGlyphs();
      for (int i = 0; i<list.size(); i++)
      {
        TextGlyph tGlyph = list.get(i);
        if (tGlyph!=null) {
          if (tGlyph.getId().substring(tGlyph.getId().indexOf("_")+1).equals(speciesAliasID))
          {
            list.remove(i);
            i--;
          }
        }
      }
    }
    //finished
    else if (glyph instanceof CompartmentGlyph)
    {
      CompartmentGlyph cGlyph = (CompartmentGlyph)glyph;
      String cellDesignerID = cGlyph.getReference(); //refers to compartment;
      if (cellDesignerID!=null)
      {
        PluginListOf listOfCompartments = plugModel.getListOfCompartments();
        for (int i = 0; i<listOfCompartments.size(); i++)
        {
          PluginCompartment pCompartment = (PluginCompartment)listOfCompartments.get(i);
          if (pCompartment.getId().equals(cellDesignerID))
          {
            listOfCompartments.remove(i);
            i--;
          }
        }
        //remove TextGlyphs
        ListOf<TextGlyph> listOfTextGlyphs = layout.getListOfTextGlyphs();
        for (int i = 0; i<listOfTextGlyphs.size(); i++)
        {
          TextGlyph tGlyph = listOfTextGlyphs.get(i);
          if (tGlyph.getId().substring(tGlyph.getId().indexOf("_")+1).equals(cellDesignerID))
          {
            listOfTextGlyphs.remove(i);
            i--;
          }
        }
      }
    }
  }
}
