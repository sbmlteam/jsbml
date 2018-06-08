/*
 * ---------------------------------------------------------------------
 * This file is part of the SysBio API library.
 *
 * Copyright (C) 2009-2017 by the University of Tuebingen, Germany.
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation. A copy of the license
 * agreement is provided in the file named "LICENSE.txt" included with
 * this software distribution and also available online as
 * <http://www.gnu.org/licenses/lgpl-3.0-standalone.html>.
 * ---------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.render.director;

import java.awt.Color;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.layout.CompartmentGlyph;
import org.sbml.jsbml.ext.layout.GraphicalObject;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.ReactionGlyph;
import org.sbml.jsbml.ext.layout.SpeciesGlyph;
import org.sbml.jsbml.ext.layout.TextGlyph;
import org.sbml.jsbml.ext.render.ColorDefinition;
import org.sbml.jsbml.ext.render.LocalRenderInformation;
import org.sbml.jsbml.ext.render.LocalStyle;
import org.sbml.jsbml.ext.render.RenderConstants;
import org.sbml.jsbml.ext.render.RenderGraphicalObjectPlugin;
import org.sbml.jsbml.ext.render.RenderGroup;
import org.sbml.jsbml.ext.render.RenderLayoutPlugin;
import org.sbml.jsbml.util.filters.NameFilter;

/**
 * A currently very preliminary class with inaccurate interpretation of rendering
 * information.
 * 
 * @author Andreas Dr&auml;ger
 * @since 1.4
 */
public class RenderProcessor {
  
  /**
   * 
   */
  public static final String RENDER_LINK = "RENDER_LINK";
  /**
   * A {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger(RenderProcessor.class.getName());
  
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
          Map<String, LocalStyle> roles = new HashMap<String, LocalStyle>();
          for (LocalStyle ls : lri.getListOfLocalStyles()) {
            for (String id : ls.getIDList()) {
              SBase sbase = model.getSBaseById(id);
              // TODO: Here we link local styles to SBases, but when interpreting this we don't check if all SBases belong to the same local render information.
              if (sbase != null) {
                if (sbase.getUserObject(RENDER_LINK) == null) {
                  sbase.putUserObject(RENDER_LINK, new LinkedList<LocalStyle>());
                }
                ((List<LocalStyle>) sbase.getUserObject(RENDER_LINK)).add(ls);
              }
            }
            for (String role : ls.getRoleList()) {
              if (roles.containsKey(role) && (roles.get(role) != ls)) {
                logger.warning(MessageFormat.format(
                  "Role clash between style ''{0}'' and style ''{1}''.",
                  roles.get(role).getId(), ls.getId()));
              } else {
                roles.put(role, ls);
              }
            }
          }
          if (!roles.isEmpty()) {
            for (GraphicalObject go : layout.getListOfAdditionalGraphicalObjects()) {
              linkObjectRoleToStyle(roles, go);
            }
            for (CompartmentGlyph cg : layout.getListOfCompartmentGlyphs()) {
              linkObjectRoleToStyle(roles, cg);
            }
            for (ReactionGlyph rg : layout.getListOfReactionGlyphs()) {
              linkObjectRoleToStyle(roles, rg);
            }
            for (SpeciesGlyph sg : layout.getListOfSpeciesGlyphs()) {
              linkObjectRoleToStyle(roles, sg);
            }
            for (TextGlyph tg : layout.getListOfTextGlyphs()) {
              linkObjectRoleToStyle(roles, tg);
            }
          }
        }
      }
    }
  }
  
  /**
   * @param roles
   * @param go
   */
  public static void linkObjectRoleToStyle(Map<String, LocalStyle> roles,
    GraphicalObject go) {
    RenderGraphicalObjectPlugin rgop = (RenderGraphicalObjectPlugin) go.getExtension(RenderConstants.shortLabel);
    if ((rgop != null) && (rgop.isSetObjectRole())) {
      LocalStyle ls = roles.get(rgop.getObjectRole());
      if (ls != null) {
        if (go.getUserObject(RENDER_LINK) == null) {
          go.putUserObject(RENDER_LINK, new LinkedList<LocalStyle>());
        }
        ((List<LocalStyle>) go.getUserObject(RENDER_LINK)).add(ls);
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
    List<LocalStyle> styles = (List<LocalStyle>) sg.getUserObject(RENDER_LINK);
    if (styles != null) {
      for (int i = styles.size() - 1; i >= 0; i--) {
        LocalStyle ls = styles.get(i);
        RenderGroup rg = ls.getGroup();
        if ((rg != null) && rg.isSetFill()) {
          if (!ls.isSetParent()) {
            // This style has already been deleted from the model.
            styles.remove(i);
          } else {
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
    }
    return null;
  }
  
}
