/*
 * $Id$
 * $URL$
 * ---------------------------------------------------------------------
 * This file is part of the SysBio API library.
 *
 * Copyright (C) 2009-2012 by the University of Tuebingen, Germany.
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
import java.util.UUID;

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
import org.sbml.jsbml.ext.layout.ExtendedLayoutModel;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.ext.layout.ReactionGlyph;
import org.sbml.jsbml.ext.layout.SpeciesGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceRole;
import org.sbml.jsbml.ext.layout.TextGlyph;

/**
 * This class is supposed to be called to prepare a model without
 * Layout information for auto layout by LayoutDirector by creating
 * glyphs missing any coordinates.
 * @author Jan Rudolph
 * @version $Rev$
 */
public class GlyphCreator {
	private Model model;
	
	public GlyphCreator(Model model) {
		this.model = model;
	}
	
	/**
	 * compartment, species, reactions, textglph 
	 */
	public void create() {
		Random rand = new Random();
		ExtendedLayoutModel extLayout = new ExtendedLayoutModel(model);
		model.addExtension(LayoutConstants.getNamespaceURI(model.getLevel(), model.getVersion()), extLayout);
		Layout layout = extLayout.createLayout(nextId());
		
		for (Species s : model.getListOfSpecies()) {
			SpeciesGlyph speciesGlyph = layout.createSpeciesGlyph(genId(rand, s), s.getId());
			s.putUserObject("GLYPH", speciesGlyph.getId());
			TextGlyph textGlyph = layout.createTextGlyph(nextId());
			textGlyph.setOriginOfText(s.getId());
			textGlyph.setGraphicalObject(speciesGlyph.getId());
		}

		for (int index = 0; index < model.getCompartmentCount(); index++) {
			// starting at index 1 to ignore assumed default compartment at position 0
			Compartment c = model.getListOfCompartments().get(index);
			CompartmentGlyph compartmentGlyph = layout.createCompartmentGlyph(nextId());
			TextGlyph textGlyph = layout.createTextGlyph(nextId());
			textGlyph.setOriginOfText(c.getId());
			textGlyph.setGraphicalObject(compartmentGlyph.getId());
		}
		
		for (Reaction r : model.getListOfReactions()) {
			ReactionGlyph rGlyph = layout.createReactionGlyph(nextId());
			for(ModifierSpeciesReference ref : r.getListOfModifiers()) {
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
			for(SpeciesReference ref : r.getListOfProducts()) {
				createSpeciesReferenceGlyph(rand, rGlyph, ref, SpeciesReferenceRole.PRODUCT);
			}
			for(SpeciesReference ref : r.getListOfReactants()) {
				createSpeciesReferenceGlyph(rand, rGlyph, ref, SpeciesReferenceRole.SUBSTRATE);
			}
		}
	}

	public String nextId() {
	  String idOne;
	  do {
	    idOne = UUID.randomUUID().toString();
	    if (Character.isDigit(idOne.charAt(0))) {
	      // Add an underscore at the beginning of the new id only if necessary.
	      idOne = '_' + idOne;
	    }
	  } while (model.findNamedSBase(idOne) != null);
	  return idOne;
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
