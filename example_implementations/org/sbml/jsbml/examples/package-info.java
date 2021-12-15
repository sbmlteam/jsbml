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
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */

/**
 * This package contains two example-implementations using the classes and
 * interfaces provided by {@link org.sbml.jsbml.ext.render.director}:
 * <ul>
 * <li><b>LaTeXExample</b> is a very basic implementation to produce a
 * LaTeX/tikz-file rendering a given laid-out sbml-file. It does not require
 * knowledge of the render extension of SBML, and is thus newcomer-friendlier.
 * Compare also existing implementations like the SBML2LaTeX tool
 * (https://github.com/draeger-lab/SBML2LaTeX)</li>
 * <li><b>RenderExample</b> is a similarly basic implementation, but uses the
 * render-extension of SBML (at least some prior knowledge of which is
 * recommended) to add a LocalRenderInformation to the layout of a given
 * sbml-file. Such a render-information can be viewed e.g. with COPASI.
 * Additionally, the RenderExample makes use of a more sensible LayoutAlgorithm,
 * thus demonstrating how one could automatically lay out a previously unlaidout
 * SBMLfile</li>
 * </ul>
 * <p>
 * Both examples provide an already set-up main-method, and work on a (mostly)
 * laid-out SBML-file featuring most SBGN-elements (but only one instance of a
 * clone-marker).
 * </p>
 * <p>
 * The purpose of these examples is to make it easier to understand the provided
 * classes and how they could be used for your specific goals, not to provide
 * general/perfectly robust, ready-to-use implementations. To that end,
 * <b>explanatory comments</b> are made on the code, highlighting some
 * design-decisions.
 * </p>
 * 
 * @author David Emanuel Vetter
 */
package org.sbml.jsbml.examples;
