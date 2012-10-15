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

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBO;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.ext.SBasePlugin;
import org.sbml.jsbml.ext.layout.BoundingBox;
import org.sbml.jsbml.ext.layout.CompartmentGlyph;
import org.sbml.jsbml.ext.layout.ExtendedLayoutModel;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.ext.layout.NamedSBaseGlyph;
import org.sbml.jsbml.ext.layout.ReactionGlyph;
import org.sbml.jsbml.ext.layout.SpeciesGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceGlyph;
import org.sbml.jsbml.ext.layout.SpeciesReferenceRole;
import org.sbml.jsbml.ext.layout.TextGlyph;

/**
 * @navhas - - - LayoutBuilder
 * @navhas - - - LayoutAlgorithm
 * 
 * @param <P> Type of the product.
 * 
 * @author Mirjam Gutekunst
 * @version $Rev: 142 $
 */
public class LayoutDirector<P> implements Runnable {
	
	public static final String LAYOUT_LINK = "LAYOUT_LINK";
	public static final String COMPARTMENT_LINK = "COMPARTMENT_LINK";
	private LayoutBuilder<P> builder;
	private Model model;
	private int layoutIndex;
	private LayoutAlgorithm algorithm;
	
	
	/**
	 * @param inputFile
	 * @param outputFile
	 * @throws XMLStreamException
	 * @throws IOException
	 */
	public LayoutDirector(File inputFile, LayoutBuilder<P> builder, LayoutAlgorithm algorithm)
			throws XMLStreamException, IOException {
		
		this(SBMLReader.read(inputFile), builder, algorithm);
	}
	
	/**
	 * @param doc
	 * @param builder
	 */
	public LayoutDirector(SBMLDocument doc, LayoutBuilder<P> builder,
											LayoutAlgorithm algorithm) {
		this.model = doc.getModel();
		this.builder = builder;
		this.algorithm = algorithm;
		this.layoutIndex = 0;
	}
	
	/**
	 * @return the layoutIndex
	 */
	public int getLayoutIndex() {
		return layoutIndex;
	}
	
	/**
	 * @param layoutIndex the layoutIndex to be set
	 */
	public void setLayoutIndex(int layoutIndex) {
		
		if (layoutIndex < 0) {
			throw new IndexOutOfBoundsException(MessageFormat.format(
					"{0,number,integer} < 0", layoutIndex));
		}
		
		SBasePlugin extension = model.getExtension(LayoutConstants
				.getNamespaceURI(model.getLevel(), model.getVersion()));
		
		if ((extension != null) &&
				(layoutIndex >= ((ExtendedLayoutModel) extension).getLayoutCount())) {
			throw new IndexOutOfBoundsException(MessageFormat.format(
					"{0,number,integer} > {1,number,ingeger}", layoutIndex,
					((ExtendedLayoutModel) extension).getLayoutCount()));
		}
		
		this.layoutIndex = layoutIndex;
	}
	
	/**
	 * algorithm to go through all reactions of the model and get all
	 * compartments, reactants, products and modifiers and parse them into 
	 * TikZ-code.
	 * 
	 * @param layout the layout to be used
	 */
	@SuppressWarnings("unchecked")
	private void buildLayout(Layout layout) {
		
		algorithm.setLayout(layout);
		
		if (!layout.isSetDimensions()) {
			layout.setDimensions(algorithm.createLayoutDimension());
		}
		
		//write the head of the LaTeX-document
		builder.builderStart(layout);
		
		ListOf<CompartmentGlyph> compartmentGlyphList = layout.getListOfCompartmentGlyphs();
		createLayoutLinks(compartmentGlyphList);
		
		List<CompartmentGlyph> sortedCompartmentGlyphList = getSortedCompartmentGlyphList();
		CompartmentGlyph previousCompartmentGlyph = null;
		for (int i = 0; i < sortedCompartmentGlyphList.size(); i++) {
			CompartmentGlyph compartmentGlyph = sortedCompartmentGlyphList.get(i);
			if (previousCompartmentGlyph != null &&
					previousCompartmentGlyph.isSetNamedSBase() &&
					compartmentGlyph.isSetNamedSBase()) {
				Compartment previousCompartment = (Compartment) previousCompartmentGlyph.getNamedSBaseInstance();
				if (previousCompartment.getUserObject(COMPARTMENT_LINK) instanceof List<?>) {
					List<Compartment> containedCompartments =
							(List<Compartment>) previousCompartment.getUserObject(COMPARTMENT_LINK);
					if (!containedCompartments.contains(compartmentGlyph.getNamedSBaseInstance())) {
						previousCompartment = null;
					}
				}
			}
			
			// if(layout.containsGlyph(compartment))
			//
			// CompartmentGlyph compartmentGlyph = layout.getCompartmentGlyph(compartment.getId());
			//
			// layout.findCompartmentGlyphs(compartment.getId());
			
			if (!compartmentGlyph.isSetBoundingBox()) {
				compartmentGlyph.setBoundingBox(algorithm.createGlyphBoundingBox(previousCompartmentGlyph, null));
//				compartmentGlyph.setBoundingBox(algorithm.createCompartmentGlyphBoundingBox(previousCompartmentGlyph));
			} else {
				BoundingBox boundingBox = compartmentGlyph.getBoundingBox();
				if (!boundingBox.isSetDimensions()) {
					boundingBox.setDimensions(algorithm.createCompartmentGlyphDimension(previousCompartmentGlyph));
				}
				if (!boundingBox.isSetPosition()) {
					boundingBox.setPosition(algorithm.createCompartmentGlyphPosition(previousCompartmentGlyph));
				}
				compartmentGlyph.setBoundingBox(boundingBox);
			}
			
			builder.buildCompartment(compartmentGlyph);
			
			previousCompartmentGlyph = compartmentGlyph;
		}
		
		ListOf<ReactionGlyph> reactionGlyphList = layout.getListOfReactionGlyphs();
		createLayoutLinks(reactionGlyphList);
		
		for (ReactionGlyph rg : reactionGlyphList) {
			if (!rg.isSetBoundingBox()) {
				rg.setBoundingBox(algorithm.createGlyphBoundingBox(rg, null));
//				rg.setBoundingBox(algorithm.createReactionGlyphBoundingBox(rg));
			} else {
				BoundingBox boundingBox = rg.getBoundingBox();
				if (!boundingBox.isSetDimensions()) {
					boundingBox.setDimensions(algorithm.createReactionGlyphDimension(rg));
				}
				if (!boundingBox.isSetPosition()) {
					boundingBox.setPosition(algorithm.createReactionGlyphPosition(rg));
				}
				rg.setBoundingBox(boundingBox);
			}
						
			ListOf<SpeciesReferenceGlyph> speciesReferenceGlyphList = rg.getListOfSpeciesReferenceGlyphs();
			
			ListOf<SpeciesReferenceGlyph> substrateProductList = new ListOf<SpeciesReferenceGlyph>();
			ListOf<SpeciesReferenceGlyph> otherList = new ListOf<SpeciesReferenceGlyph>();
			
			for(SpeciesReferenceGlyph speciesReferenceGlyph : speciesReferenceGlyphList){
				if(speciesReferenceGlyph.isSetSpeciesReferenceRole() &&
				  (speciesReferenceGlyph.getSpeciesReferenceRole().equals(SpeciesReferenceRole.PRODUCT) ||
				   speciesReferenceGlyph.getSpeciesReferenceRole().equals(SpeciesReferenceRole.SUBSTRATE))){
					substrateProductList.add(speciesReferenceGlyph);
				} else {
					otherList.add(speciesReferenceGlyph);
				}
			}
			
			for(SpeciesReferenceGlyph substrateProductSRG : substrateProductList){
				if (!substrateProductSRG.isSetCurve()) {
					substrateProductSRG.setCurve(algorithm.createCurve(rg, substrateProductSRG));
				}
			}
			
			for(SpeciesReferenceGlyph otherSRG : otherList){
				if (!otherSRG.isSetCurve()) {
					otherSRG.setCurve(algorithm.createCurve(rg, otherSRG));
				}
			}
			
			for (SpeciesReferenceGlyph srg : speciesReferenceGlyphList) {
				SpeciesReference speciesReference = (SpeciesReference) srg.getNamedSBaseInstance();
				if (speciesReference == null || !speciesReference.isSetSBOTerm()) {
					if (!srg.isSetSpeciesReferenceRole()) {
						// sets consumption (straight line as default)
						srg.setSBOTerm(394);
					}
				} else {
					srg.setSBOTerm(speciesReference.getSBOTerm());
				}
				
				if (srg.isSetSpeciesGlyph()) {
					SpeciesGlyph speciesGlyph = srg.getSpeciesGlyphInstance();
					if (!speciesGlyph.isSetBoundingBox()) {
						speciesGlyph.setBoundingBox(algorithm.createGlyphBoundingBox(speciesGlyph,srg));
//						speciesGlyph.setBoundingBox(algorithm.createSpeciesGlyphBoundingBox(speciesGlyph,srg));
					} else {
						BoundingBox boundingBox = speciesGlyph.getBoundingBox();
						if (!boundingBox.isSetDimensions()) {
							boundingBox.setDimensions(algorithm.createSpeciesGlyphDimension());
						}
						if (!boundingBox.isSetPosition()) {
							boundingBox.setPosition(algorithm.createSpeciesGlyphPosition(speciesGlyph,srg));
						}
					}
				}
				
				builder.buildConnectingArc(srg);
			}
			
			double rgRotationAngle = algorithm.calculateReactionGlyphRotationAngle(rg);
			builder.buildProcessNode(rg,rgRotationAngle);
			
		}
		
		ListOf<SpeciesGlyph> speciesGlyphList = layout.getListOfSpeciesGlyphs();
		createLayoutLinks(speciesGlyphList);
		
		for (SpeciesGlyph sg : speciesGlyphList) {
			
			boolean cloneMarker = false;
			
			if (sg.isSetNamedSBase()) {
				NamedSBase species = sg.getNamedSBaseInstance();
				
				if (!species.isSetSBOTerm()) {
					sg.setSBOTerm(SBO.getUnknownMolecule());
				} else {
					sg.setSBOTerm(species.getSBOTerm());
				}
				
				List<NamedSBaseGlyph> glyphList = new ArrayList<NamedSBaseGlyph>();
				if (species.getUserObject(LAYOUT_LINK) instanceof List<?>) {
					glyphList = (List<NamedSBaseGlyph>) species.getUserObject(LAYOUT_LINK);
				}
				if (glyphList.size() > 1) {
					cloneMarker = true;
				}
				
			}
			
			if (!sg.isSetBoundingBox()) {
				sg.setBoundingBox(algorithm.createGlyphBoundingBox(sg, null));
//				sg.setBoundingBox(algorithm.createSpeciesGlyphBoundingBox(sg));
			} else {
				BoundingBox boundingBox = sg.getBoundingBox();
				if (!boundingBox.isSetDimensions()) {
					boundingBox.setDimensions(algorithm.createSpeciesGlyphDimension());
				}
				if (!boundingBox.isSetPosition()) {
					boundingBox.setPosition(algorithm.createSpeciesGlyphPosition(sg));
				}
				sg.setBoundingBox(boundingBox);
			}
			
			builder.buildEntityPoolNode(sg, cloneMarker);
			
		}
		
		ListOf<TextGlyph> textGlyphList = layout.getListOfTextGlyphs();
		for (TextGlyph textGlyph : textGlyphList) {
			if (!textGlyph.isSetBoundingBox()) {
				textGlyph.setBoundingBox(algorithm.createGlyphBoundingBox(textGlyph, null));
//				textGlyph.setBoundingBox(algorithm.createTextGlyphBoundingBox(textGlyph));
			} else {
				BoundingBox boundingBox = textGlyph.getBoundingBox();
				if (!boundingBox.isSetDimensions()) {
					boundingBox.setDimensions(algorithm.createTextGlyphDimension(textGlyph));
				}
				if (!boundingBox.isSetPosition()) {
					boundingBox.setPosition(algorithm.createTextGlyphPosition(textGlyph));
				}
				textGlyph.setBoundingBox(boundingBox);
			}
			
			builder.buildTextGlyph(textGlyph);
		}
		builder.builderEnd();
	}
	
	/**
	 * helping method to connect a component with its corresponding glyph
	 * 
	 * @param listOfNamedSBaseGlyphs
	 */
	@SuppressWarnings("unchecked")
	private void createLayoutLinks(ListOf<? extends NamedSBaseGlyph> listOfNamedSBaseGlyphs) {
		for (NamedSBaseGlyph glyph : listOfNamedSBaseGlyphs) {
			if (glyph.isSetNamedSBase()) {
				NamedSBase correspondingSBase = glyph.getNamedSBaseInstance();
				List<NamedSBaseGlyph> listOfGlyphs = new ArrayList<NamedSBaseGlyph>();
				if (correspondingSBase.getUserObject(LAYOUT_LINK) instanceof List<?>) {
					listOfGlyphs = (List<NamedSBaseGlyph>) correspondingSBase.getUserObject(LAYOUT_LINK);
				}
				listOfGlyphs.add(glyph);
				correspondingSBase.putUserObject(LAYOUT_LINK, listOfGlyphs);
			}
		}
	}
	
	/*
	 * method that builds the layout and thus starts the actual drawing of the
	 * components when there is a layout information in this model.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		SBasePlugin extension = model.getExtension(LayoutConstants
				.getNamespaceURI(model.getLevel(), model.getVersion()));
		if (extension != null) {
			ExtendedLayoutModel layoutModel = (ExtendedLayoutModel) extension;
			if (layoutModel.getLayoutCount() > layoutIndex) {
				buildLayout(layoutModel.getLayout(layoutIndex));
			}
		} else {
			//TODO: throw exception or a logger answer
		}
	}
	
	/**
	 * @param builder the builder to be set
	 */
	public void setBuilder(LayoutBuilder<P> builder) {
		this.builder = builder;
	}
	
	/**
	 * @return the builder
	 */
	public LayoutBuilder<P> getBuilder() {
		return builder;
	}
	
	/**
	 * @param algorithm the algorithm to be set
	 */
	public void setAlgorithm(LayoutAlgorithm algorithm) {
		this.algorithm = algorithm;
	}
	
	/**
	 * @return the algorithm
	 */
	public LayoutAlgorithm getAlgorithm() {
		return algorithm;
	}
	
	/**
	 * @return the product
	 */
	public P getProduct() {
		if (builder.isProductReady()) {
			return builder.getProduct();
		}
		return null;
	}
	
	/**
	 * method determines the order of the compartments of the model, from
	 * outside to inside
	 * 
	 * @return a LinkedList<Compartment> with the compartments sorted from outside to inside
	 */
	@SuppressWarnings("unchecked")
	private List<CompartmentGlyph> getSortedCompartmentGlyphList() {
		createCompartmentLinks();
		List<CompartmentGlyph> sortedGlyphList = new ArrayList<CompartmentGlyph>();
		
		for (Compartment compartment : model.getListOfCompartments()) {
			List<CompartmentGlyph> compartmentGlyphList = new ArrayList<CompartmentGlyph>();
			if (compartment.getUserObject(LAYOUT_LINK) instanceof List<?>) {
				compartmentGlyphList = (List<CompartmentGlyph>) compartment.getUserObject(LAYOUT_LINK);
			}
			if (!compartment.isSetOutside()) {
				sortedGlyphList.addAll(compartmentGlyphList);
				sortedGlyphList.addAll(getContainedCompartmentGlyphs(compartment));
			}
		}
		
		return sortedGlyphList;
	}
	
	/**
	 * method that gets the contained compartments of a compartment from its
	 * user object with the key COMPARTMENT_LINK
	 * 
	 * @param compartment
	 * @return a LinkedList<Compartments> with the compartments the given
	 *         compartment contains
	 */
	@SuppressWarnings("unchecked")
	private LinkedList<CompartmentGlyph> getContainedCompartmentGlyphs(Compartment compartment) {
		LinkedList<CompartmentGlyph> containedList = new LinkedList<CompartmentGlyph>();
		Object userObject = compartment.getUserObject(COMPARTMENT_LINK);
		if (userObject instanceof LinkedList<?>) {
			LinkedList<CompartmentGlyph> directlyContainedCompartmentGlyphs = (LinkedList<CompartmentGlyph>) userObject;
			containedList.addAll(directlyContainedCompartmentGlyphs);
			for (CompartmentGlyph containedCompartmentGlyph : directlyContainedCompartmentGlyphs) {
				containedList.addAll(getContainedCompartmentGlyphs(
						(Compartment)containedCompartmentGlyph.getNamedSBaseInstance()));
			}
		}
		return containedList;
	}
	
	/**
	 * method sets a list of the compartments that a compartment contains as
	 * user object with the key COMPARTMENT_LINK, helping method to sort the
	 * compartments from outside to inside
	 */
	@SuppressWarnings("unchecked")
	private void createCompartmentLinks() {
		ListOf<Compartment> compartmentList = model.getListOfCompartments();
		for (Compartment compartment : compartmentList) {
			if (compartment.isSetOutsideInstance()) {
				Compartment outside = compartment.getOutsideInstance();
				LinkedList<Compartment> userObject = new LinkedList<Compartment>();
				if (outside.getUserObject(COMPARTMENT_LINK) instanceof LinkedList<?>) {
					userObject = (LinkedList<Compartment>) outside.getUserObject(COMPARTMENT_LINK);
				}
				userObject.add(compartment);
				outside.putUserObject(COMPARTMENT_LINK, userObject);
			}
		}
	}
	
}
