/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2018 jointly by the following organizations:
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
 * <p>
 * Provides classes for the render package, see the specification at <a
 * href="http://sbml.org/Community/Wiki/SBML_Level_3_Proposals/Rendering"
 * >sbml.org</a>. The rendering extension itself extends the layout extension.
 * Note that this extension can therefore not be used without having also a
 * layout defined in an SBML file.
 * </p>
 * <p>
 * Note that the rendering extension has been defined before the release of
 * SBML Level 3. Hence it is possible to write the extension in the annotation
 * section of the layout extension.
 * </p>
 * <p>
 * The first version of this package was implemented as part of the software
 * engineering class at the University of Tuebingen, Germany, in the summer
 * semester 2012, supervised by Johannes Eichner and Andreas Dr&#228;ger.
 * </p>
 * <p><img alt="JSBML" height="54" src="http://sbml.org/images/7/79/xJsbml-logo-54px.png.pagespeed.ic.am7oEUtfpP.png">
 * 
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Eugen Netz
 * @author Jan Rudolph
 * @author Johannes Eichner
 * @author Andreas Dr&#228;ger
 */
package org.sbml.jsbml.ext.render;
