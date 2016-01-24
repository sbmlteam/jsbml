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

/**
 * This package contains interfaces and classes to create an implementation
 * to create a graphical representation of the {@link org.sbml.jsbml.ext.layout.Layout}
 * of a SBML document.
 * 
 * <p>
 * There are specific interfaces for all entity pool nodes and for all arcs.
 * 
 * <p>
 * The package also contains the interfaces
 * {@link de.zbit.sbml.layout.LayoutFactory} and
 * {@link de.zbit.sbml.layout.LayoutBuilder}, giving the methods necessary to
 * create a graphical representation of a SBML model.
 * 
 * <h3>SBGN objects</h3>
 * <p>
 * The different SBGN objects are represented in the following class hierarchy:
 * <ul>
 * <li>Interface {@link de.zbit.sbml.layout.SBGNArc}: SBGN arcs, supported:
 * {@link de.zbit.sbml.layout.Catalysis}, {@link de.zbit.sbml.layout.Consumption},
 * {@link de.zbit.sbml.layout.Inhibition}, {@link de.zbit.sbml.layout.Modulation},
 * {@link de.zbit.sbml.layout.NecessaryStimulation}, {@link de.zbit.sbml.layout.Production},
 * {@link de.zbit.sbml.layout.Stimulation}, {@link de.zbit.sbml.layout.ReversibleConsumption}
 * 
 * <li>Interface {@link de.zbit.sbml.layout.SBGNNode}: SBGN entity pool nodes,
 * supported: {@link de.zbit.sbml.layout.Compartment}, {@link de.zbit.sbml.layout.Macromolecule},
 * {@link de.zbit.sbml.layout.NucleicAcidFeature}, {@link de.zbit.sbml.layout.PerturbingAgent},
 * {@link de.zbit.sbml.layout.SimpleChemical}, {@link de.zbit.sbml.layout.SourceSink},
 * {@link de.zbit.sbml.layout.UnspecifiedNode}
 * 
 * <li>Interface {@link de.zbit.sbml.layout.SBGNProcessNode}: SBGN process nodes,
 * supported: {@link de.zbit.sbml.layout.AssociationNode},
 * {@link de.zbit.sbml.layout.DissociationNode}, {@link de.zbit.sbml.layout.OmittedProcessNode},
 * {@link de.zbit.sbml.layout.ProcessNode}, {@link de.zbit.sbml.layout.UncertainProcessNode}
 * </ul>
 * 
 * <p>
 * {@link de.zbit.sbml.layout.LayoutDirector} is used to start and direct the
 * drawing. It uses two components:
 * 
 * <ul>
 * <li>{@link de.zbit.sbml.layout.LayoutAlgorithm}: to determine dimensions and positions of
 * unlayouted elements
 * <li>{@link de.zbit.sbml.layout.LayoutBuilder}: to actually produce the graphical representation
 * of the layout
 * </ul>
 * 
 * <p>
 * The method {@link de.zbit.sbml.layout.LayoutDirector#buildLayout} builds the
 * product. This procedure consists of four steps:
 * 
 * <ol>
 * <li>All glyphs are added to the input of the {@link de.zbit.sbml.layout.LayoutAlgorithm}.
 * <li>The {@link de.zbit.sbml.layout.LayoutAlgorithm} completes all missing information.
 * <li>All glyphs are built with the {@link de.zbit.sbml.layout.LayoutBuilder}.
 * <li>The dimensions of the whole layout are computed.
 * </ol>
 * 
 * <h3>The {@link de.zbit.sbml.layout.SimpleLayoutAlgorithm} Implementation</h3>
 * 
 * The package contains a partial implementation of the {@link de.zbit.sbml.layout.LayoutAlgorithm}.
 * It provides the some features and follows specific conventions not found
 * in any specification:
 * 
 * <ul>
 * <li><strong>{@link org.sbml.jsbml.ext.layout.ReactionGlyph} positioning.</strong> The position of the
 * reaction glyph is the center of two defining points: If there are no curves, the two
 * points are the center of the main substrate and the main product. If there are
 * multiple species marked as substrates and/or products, the first substrate/product found
 * is used. If there are curves for the main substrate and/or product the last points of the
 * curves (i.e. the points directly at the reaction glyph) are used.
 * 
 * <li><strong>{@link org.sbml.jsbml.ext.layout.ReactionGlyph}  rotation.</strong> The rotation of the reaction
 * glyph is determined by the two points used for the positioning. The small extents of the 
 * reaction glyph are located on the line determined by the two points. All rotational angles
 * are given in degrees.
 * 
 * <li><strong>{@link org.sbml.jsbml.ext.layout.ReactionGlyph} dimensions.</strong> Regardless of desired rotation,
 * the dimensions of a reaction glyph have to be defined as if the reaction glyph would be
 * positioned horizontally, i.e. it has always a greater width than height.
 * 
 * <li><strong>Docking points.</strong> The docking points are the points at which the reaction arc connect to
 * the species. As the line between species and reaction glyph is determined by the center of
 * the species, the docking point is the intersection of this line with the border of the
 * species. To facilitate the calculation for various shapes {@link de.zbit.sbml.layout.SimpleLayoutAlgorithm}
 * provides the methods {@link de.zbit.sbml.layout.SimpleLayoutAlgorithm#calculateDockingForEllipseSpecies},
 * {@link de.zbit.sbml.layout.SimpleLayoutAlgorithm#calculateDockingForQuadraticSpecies},
 * {@link de.zbit.sbml.layout.SimpleLayoutAlgorithm#calculateDockingForRoundSpecies}.
 * </ul>
 * 
 * <h3>Design Notes</h3>
 * 
 * <ul>
 * <li>
 * Regarding the order of steps: To create a useful layout, the layouting
 * algorithm has to know about <emph>all</emph> glyphs, i.e. layouted and
 * unlayouted glyphs. A sequential querying of position or dimension (as it was
 * introduced in the original implementation) is not reasonable, because the
 * layout has to be determined from all glpyhs.
 * <li>
 * The current implementation of {@link de.zbit.sbml.layout.LayoutDirector} can read in map of
 * fluxes. This is a special application for the modification of a layout and
 * should not be defined here. The product of {@link de.zbit.sbml.layout.LayoutDirector} is a
 * layout. further modification of the layout should be performed separately.
 * </ul>
 * 
 * 
 * @version $Rev$
 */
package de.zbit.sbml.layout;
