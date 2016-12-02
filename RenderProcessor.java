/*
 * $Id$
 * $URL$
 * ---------------------------------------------------------------------
 * This file is part of the SysBio API library.
 *
 * Copyright (C) 2009-2016 by the University of Tuebingen, Germany.
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation. A copy of the license
 * agreement is provided in the file named "LICENSE.txt" included with
 * this software distribution and also available online as
 * <http://www.gnu.org/licenses/lgpl-3.0-standalone.html>.
 * ---------------------------------------------------------------------
 */
package de.zbit.sbml.layout;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.SpeciesGlyph;
import org.sbml.jsbml.ext.render.ColorDefinition;
import org.sbml.jsbml.ext.render.LocalRenderInformation;
import org.sbml.jsbml.ext.render.LocalStyle;
import org.sbml.jsbml.ext.render.RenderConstants;
import org.sbml.jsbml.ext.render.RenderGroup;
import org.sbml.jsbml.ext.render.RenderLayoutPlugin;
import org.sbml.jsbml.util.filters.NameFilter;

/**
 * 
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 */
public class RenderProcessor {
  
  /**
   * 
   */
  public static final String RENDER_LINK = "RENDER_LINK";
  
  public RenderProcessor() {
  }
  
  /**
   * 
   * @param layout
   */
  public static void preprocess(Layout layout) {
    Model model = layout.getModel();
    
    RenderLayoutPlugin rlp = (RenderLayoutPlugin) layout.getExtension(RenderConstants.shortLabel);
    if ((rlp != null) && rlp.isSetListOfLocalRenderInformation()) {
      ListOf<LocalRenderInformation> listOfLocalRenderInformation = rlp.getListOfLocalRenderInformation();
      for (LocalRenderInformation lri : listOfLocalRenderInformation) {
        if (lri.isSetListOfLocalStyles() && lri.isSetListOfColorDefinitions()) {
          for (LocalStyle ls : lri.getListOfLocalStyles()) {
            for (String id : ls.getIDList()) {
              SBase sbase = model.getSBaseById(id);
              if (sbase != null) {
                if (sbase.getUserObject(RENDER_LINK) == null) {
                  sbase.putUserObject(RENDER_LINK, new LinkedList<LocalStyle>());
                }
                ((List<LocalStyle>) sbase.getUserObject(RENDER_LINK)).add(ls);
              }
            }
          }
        }
      }
    }
  }
  
  /**
   * 
   * @param sg
   * @return
   */
  public static Color getRenderFillColor(SpeciesGlyph sg) {
    // Now get color information from render
    List<LocalStyle> styles = (List<LocalStyle>) sg.getUserObject(RenderProcessor.RENDER_LINK);
    if (styles != null) {
      for (LocalStyle ls : styles) {
        RenderGroup rg = ls.getGroup();
        if ((rg != null) && rg.isSetFill()) {
          LocalRenderInformation lri = (LocalRenderInformation) ls.getParent().getParent();
          if (lri.isSetListOfColorDefinitions()) {
            ColorDefinition cd = lri.getListOfColorDefinitions().firstHit(new NameFilter(rg.getFill()));
            if (cd != null) {
              return cd.getValue();
            }
          }
        }
      }
    }
    return null;
  }
  
}
