/*
 * ---------------------------------------------------------------------
 * This file is part of the SysBio API library.
 *
 * Copyright (C) 2009-2018 by the University of Tuebingen, Germany.
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

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.ModifierSpeciesReference;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBO;
import org.sbml.jsbml.SimpleSpeciesReference;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.ext.layout.CompartmentGlyph;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.ext.layout.LayoutModelPlugin;
import org.sbml.jsbml.ext.layout.ReactionGlyph;
import org.sbml.jsbml.ext.layout.SpeciesGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceRole;
import org.sbml.jsbml.ext.layout.TextGlyph;
import org.sbml.jsbml.util.SBMLtools;

/**
 * This class creates a SBML layout for a model without layout information. It
 * adds glyphs for every species including text labels, reaction and
 * compartment.
 * 
 * @author Jan Rudolph
 * @since 1.4
 */
public class GlyphCreator {
  
  /**
   * 
   */
  public static final String LAYOUT_LINK = "GLYPH";
  
  /**
   * A {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger(GlyphCreator.class.getName());
  
  /**
   * The model for which a layout is to be created.
   */
  private Model model;
  
  /**
   * 
   * @param model
   */
  public GlyphCreator(Model model) {
    this.model = model;
  }
  
  /**
   * compartment, species, reactions, textglyph
   * 
   *  TODO
   *  <ul>
   *  <li> do not create one glyph per species, instead create exactly one glyph
   *    for each species that acts as a main substrate and product and create
   *    multiple glyphs for all species that act as side-substrates or
   *    side products
   *  <li> this produces a far more useful graph
   *  </ul>
   */
  public void create() {
    logger.info("Initializing layout");
    SBMLDocument doc = model.getSBMLDocument();
    String namespace = LayoutConstants.getNamespaceURI(model.getLevel(), model.getVersion());
    LayoutModelPlugin extLayout = new LayoutModelPlugin(model);
    model.addExtension(namespace, extLayout);
    Layout layout = extLayout.createLayout(SBMLtools.nextId(model));
    layout.setName("auto_layout");
    doc.addNamespace(LayoutConstants.shortLabel, "xmlns", namespace);
    doc.getSBMLDocumentAttributes().put(LayoutConstants.shortLabel + ":required", "false");
    
    int degreeThreshold = 3;
    
    // TODO possibly implement logic to detect the outermost compartment
    if (model.isSetListOfCompartments()) {
      for (Compartment c : model.getListOfCompartments()) {
        logger.info("Processing compartment " + c.getId());
        CompartmentGlyph compartmentGlyph = layout.createCompartmentGlyph(SBMLtools.nextId(model), c.getId());
        TextGlyph textGlyph = layout.createTextGlyph(SBMLtools.nextId(model));
        textGlyph.setOriginOfText(c.getId());
        textGlyph.setGraphicalObject(compartmentGlyph.getId());
      }
    }
    
    Map<String, Set<String>> speciesToReactions = new HashMap<String, Set<String>>();
    Map<String, Set<String>> reactionToReactants = new HashMap<String, Set<String>>();
    Map<String, Set<String>> reactionToProducts = new HashMap<String, Set<String>>();
    Map<String, Set<String>> reactionToModifiers = new HashMap<String, Set<String>>();
    if (model.isSetListOfReactions()) {
      for (Reaction r : model.getListOfReactions()) {
        logger.info("Linking species to reaction " + r.getId());
        if (r.isSetListOfModifiers()) {
          for (ModifierSpeciesReference ref : r.getListOfModifiers()) {
            linkReferenceToReaction(ref, r, speciesToReactions);
            linkSpeciesToReaction(ref, r, reactionToModifiers);
          }
        }
        if (r.isSetListOfReactants()) {
          for (SimpleSpeciesReference ref : r.getListOfReactants()) {
            linkReferenceToReaction(ref, r, speciesToReactions);
            linkSpeciesToReaction(ref, r, reactionToReactants);
          }
        }
        if (r.isSetListOfProducts()) {
          for (SimpleSpeciesReference ref : r.getListOfProducts()) {
            linkReferenceToReaction(ref, r, speciesToReactions);
            linkSpeciesToReaction(ref, r, reactionToProducts);
          }
        }
      }
    }
    
    Map<String, List<String>> species2glyph = new HashMap<String, List<String>>();
    Map<String, Integer> sGlyphDegree = new HashMap<String, Integer>();
    if (model.isSetListOfSpecies()) {
      for (Species s : model.getListOfSpecies()) {
        logger.info("Processing species " + s.getId());
        SpeciesGlyph sGlyph = createSpeciesGlyph(layout, s, doc);
        List<String> list = new LinkedList<String>();
        list.add(sGlyph.getId());
        species2glyph.put(s.getId(), list);
        sGlyphDegree.put(sGlyph.getId(), Integer.valueOf(0));
      }
    }
    
    if (model.isSetListOfReactions()) {
      for (Reaction r : model.getListOfReactions()) {
        logger.info("Processing reaction " + r.getId());
        ReactionGlyph rGlyph = layout.createReactionGlyph(SBMLtools.nextId(model), r.getId());
        
        if (r.isSetListOfModifiers()) {
          for (ModifierSpeciesReference ref : r.getListOfModifiers()) {
            createSpeciesReferenceGlyph(doc, rGlyph, ref,
              createOrGetGlyph(ref, species2glyph, sGlyphDegree, degreeThreshold, doc, layout),
              determineRole(ref.getSBOTerm()));
          }
        }
        
        if (r.isSetListOfProducts()) {
          for (SimpleSpeciesReference ref : r.getListOfProducts()) {
            createSpeciesReferenceGlyph(doc, rGlyph, ref,
              createOrGetGlyph(ref, species2glyph, sGlyphDegree, degreeThreshold, doc, layout),
              SpeciesReferenceRole.PRODUCT);
          }
        } else {
          createEmptySetReactionParticipant(layout, rGlyph, SpeciesReferenceRole.PRODUCT, doc);
        }
        
        if (r.isSetListOfReactants()) {
          for (SimpleSpeciesReference ref : r.getListOfReactants()) {
            createSpeciesReferenceGlyph(doc, rGlyph, ref,
              createOrGetGlyph(ref, species2glyph, sGlyphDegree, degreeThreshold, doc, layout),
              SpeciesReferenceRole.SUBSTRATE);
          }
        } else {
          createEmptySetReactionParticipant(layout, rGlyph, SpeciesReferenceRole.SUBSTRATE, doc);
        }
        
      }
    }
  }
  
  /**
   * 
   * @param ref
   * @param species2glyph
   * @param sGlyphDegree
   * @param degreeThreshold
   * @param rand
   * @param layout
   * @return
   */
  private String createOrGetGlyph(SimpleSpeciesReference ref,
    Map<String, List<String>> species2glyph, Map<String, Integer> sGlyphDegree,
    int degreeThreshold, SBMLDocument doc, Layout layout) {
    SpeciesGlyph sGlyph;
    List<String> listOfSpeciesGlyphs = species2glyph.get(ref.getSpecies());
    String glyphId = listOfSpeciesGlyphs.get(listOfSpeciesGlyphs.size() - 1);
    if (sGlyphDegree.get(glyphId).intValue() >= degreeThreshold) {
      sGlyph = createSpeciesGlyph(layout, ref.getSpeciesInstance(), doc);
      glyphId = sGlyph.getId();
      listOfSpeciesGlyphs.add(glyphId);
      sGlyphDegree.put(glyphId, Integer.valueOf(1));
    } else {
      sGlyph = (SpeciesGlyph) model.findNamedSBase(glyphId);
      sGlyphDegree.put(glyphId, Integer.valueOf(sGlyphDegree.get(glyphId).intValue() + 1));
    }
    return glyphId;
  }
  
  /**
   * 
   * @param sboTerm
   * @return
   */
  private SpeciesReferenceRole determineRole(int sboTerm) {
    SpeciesReferenceRole modifier = SpeciesReferenceRole.MODIFIER;
    if (sboTerm > -1) {
      if (SBO.isInhibitor(sboTerm)) {
        modifier = SpeciesReferenceRole.INHIBITOR;
      } else if (SBO.isStimulator(sboTerm)) {
        modifier = SpeciesReferenceRole.ACTIVATOR;
      }
    }
    return modifier;
  }
  
  /**
   * 
   * @param ref
   * @param r
   * @param reactionToSpecies
   */
  private void linkSpeciesToReaction(SimpleSpeciesReference ref, Reaction r,
    Map<String, Set<String>> reactionToSpecies) {
    if (!reactionToSpecies.containsKey(r.getId())) {
      reactionToSpecies.put(r.getId(), new HashSet<String>());
    }
    reactionToSpecies.get(r.getId()).add(ref.getId());
  }
  
  /**
   * 
   * @param ref
   * @param r
   * @param speciesToReactions
   */
  private void linkReferenceToReaction(SimpleSpeciesReference ref, Reaction r, Map<String, Set<String>> speciesToReactions) {
    if (!speciesToReactions.containsKey(ref.getSpecies())) {
      speciesToReactions.put(ref.getSpecies(), new HashSet<String>());
    }
    speciesToReactions.get(ref.getSpecies()).add(r.getId());
  }
  
  /**
   * 
   * @param layout
   * @param rGlyph
   * @param role
   * @param doc
   */
  private void createEmptySetReactionParticipant(Layout layout, ReactionGlyph rGlyph, SpeciesReferenceRole role, SBMLDocument doc) {
    SpeciesGlyph glyph = createSpeciesGlyph(layout, null, doc);
    SpeciesReferenceGlyph sRG = rGlyph.createSpeciesReferenceGlyph(genId(doc, null), glyph.getId());
    sRG.setRole(role);
  }
  
  /**
   * 
   * @param layout
   * @param s
   * @param doc
   * @return
   */
  private SpeciesGlyph createSpeciesGlyph(Layout layout, Species s, SBMLDocument doc) {
    SpeciesGlyph speciesGlyph = layout.createSpeciesGlyph(genId(doc, s), s != null ? s.getId() : null);
    if (s != null) {
      if (s.getUserObject(LAYOUT_LINK) == null) {
        s.putUserObject(LAYOUT_LINK, new LinkedList<String>());
      }
      //((List<String>) s.getUserObject(LAYOUT_LINK)).add(speciesGlyph.getId());
      // do not label source or sink glyphs
      if (!SBO.isChildOf(s.getSBOTerm(), SBO.getEmptySet())) {
        TextGlyph textGlyph = layout.createTextGlyph(SBMLtools.nextId(model));
        textGlyph.setOriginOfText(s.getId());
        textGlyph.setGraphicalObject(speciesGlyph.getId());
      }
    } else {
      SBMLtools.setSBOTerm(speciesGlyph, SBO.getEmptySet());
    }
    return speciesGlyph;
  }
  
  /**
   * 
   * @param doc
   * @param rGlyph
   * @param ref
   * @param speciesGlyphId
   * @param role
   */
  private void createSpeciesReferenceGlyph(SBMLDocument doc, ReactionGlyph rGlyph,
    SimpleSpeciesReference ref, String speciesGlyphId, SpeciesReferenceRole role) {
    SpeciesReferenceGlyph sRG = rGlyph.createSpeciesReferenceGlyph(genId(doc, ref.getSpeciesInstance()), speciesGlyphId);
    sRG.setRole(role);
  }
  
  /**
   * 
   * @param doc
   * @param ref
   * @return
   */
  private String genId(SBMLDocument doc, NamedSBase ref) {
    return SBMLtools.nameToSId(((ref != null) ? ref.getId() : "empty") + "_glyph_", doc);
  }
  
}
