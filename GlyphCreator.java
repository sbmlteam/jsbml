/*
 * $Id$
 * $URL$
 * ---------------------------------------------------------------------
 * This file is part of the SysBio API library.
 *
 * Copyright (C) 2009-2013 by the University of Tuebingen, Germany.
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

import java.util.Random;

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.ModifierSpeciesReference;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBO;
import org.sbml.jsbml.SimpleSpeciesReference;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.ext.layout.CompartmentGlyph;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.ext.layout.LayoutModelPlugin;
import org.sbml.jsbml.ext.layout.ReactionGlyph;
import org.sbml.jsbml.ext.layout.SpeciesGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceRole;
import org.sbml.jsbml.ext.layout.TextGlyph;

import de.zbit.sbml.util.SBMLtools;

/**
 * This class creates a SBML layout for a model without layout information. It
 * adds glyphs for every species including text labels, reaction and
 * compartment.
 * 
 * @author Jan Rudolph
 * @version $Rev$
 */
public class GlyphCreator {
  
  /**
   * 
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
	 *    multiple glyphs for all species that act as sidesubstrates or
	 *    side products
	 *  <li> this produces a far more useful graph
	 *  </ul>
	 */
	public void create() {
		Random rand = new Random();
		LayoutModelPlugin extLayout = new LayoutModelPlugin(model);
		model.addExtension(LayoutConstants.getNamespaceURI(model.getLevel(), model.getVersion()), extLayout);
		Layout layout = extLayout.createLayout(SBMLtools.nextId(model));
		layout.setName("auto_layout");
		
		if (model.isSetListOfSpecies()) {
			for (Species s : model.getListOfSpecies()) {
				SpeciesGlyph speciesGlyph = layout.createSpeciesGlyph(genId(rand, s), s.getId());
				s.putUserObject("GLYPH", speciesGlyph.getId());
				// do not label source or sink glyphs
				if (SBO.isChildOf(s.getSBOTerm(), SBO.getEmptySet())) {
					continue;
				}
				TextGlyph textGlyph = layout.createTextGlyph(SBMLtools.nextId(model));
				textGlyph.setOriginOfText(s.getId());
				textGlyph.setGraphicalObject(speciesGlyph.getId());
			}
		}

		// TODO possibly implement logic to detect the outmost compartment
		if (model.isSetListOfCompartments()) {
			for (Compartment c : model.getListOfCompartments()) {
				CompartmentGlyph compartmentGlyph = layout.createCompartmentGlyph(SBMLtools.nextId(model), c.getId());
				TextGlyph textGlyph = layout.createTextGlyph(SBMLtools.nextId(model));
				textGlyph.setOriginOfText(c.getId());
				textGlyph.setGraphicalObject(compartmentGlyph.getId());
			}
		}
		
		if (model.isSetListOfReactions()) {
			for (Reaction r : model.getListOfReactions()) {
				ReactionGlyph rGlyph = layout.createReactionGlyph(SBMLtools.nextId(model), r.getId());
				if (r.isSetListOfModifiers()) {
					for (ModifierSpeciesReference ref : r.getListOfModifiers()) {
						SpeciesReferenceRole modifier = SpeciesReferenceRole.MODIFIER;
						if (ref.isSetSBOTerm()) {
							if (SBO.isInhibitor(ref.getSBOTerm())) {
								modifier = SpeciesReferenceRole.INHIBITOR;
							} else if (SBO.isStimulator(ref.getSBOTerm())) {
								modifier = SpeciesReferenceRole.ACTIVATOR;
							}
						}
						createSpeciesReferenceGlyph(rand, rGlyph, ref, modifier);
					}
				}
				if (r.isSetListOfProducts()) {
					for (SpeciesReference ref : r.getListOfProducts()) {
						createSpeciesReferenceGlyph(rand, rGlyph, ref, SpeciesReferenceRole.PRODUCT);
					}
				}
				if (r.isSetListOfReactants()) {
					for (SpeciesReference ref : r.getListOfReactants()) {
						createSpeciesReferenceGlyph(rand, rGlyph, ref, SpeciesReferenceRole.SUBSTRATE);
					}
				}
			}
		}
	}

	/**
	 * @param rand
	 * @param rGlyph
	 * @param ref
	 */
	private void createSpeciesReferenceGlyph(Random rand, ReactionGlyph rGlyph,
		SimpleSpeciesReference ref, SpeciesReferenceRole role) {
		String speciesGlyphId = (String) ref.getSpeciesInstance().getUserObject("GLYPH");
		SpeciesReferenceGlyph sRG = rGlyph.createSpeciesReferenceGlyph(genId(rand, ref.getSpeciesInstance()), speciesGlyphId);
		sRG.setRole(role);
	}

	/**
	 * @param rand
	 * @param ref
	 * @return
	 */
	private String genId(Random rand, NamedSBase ref) {
		return ref.getId() + "_glyph" + rand.nextInt(100000);
	}

}
