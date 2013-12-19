/* 
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.spatial;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.0
 * @date 17.10.2013
 */
public class SpatialConstants {

	/**
	 * The namespace URI of this parser.
	 */
	public static final String namespaceURI = "http://www.sbml.org/sbml/level3/version1/spatial/version1";
	public static final String shortLabel = "spatial";

	public static final List<String> namespaces;

	static {
		namespaces = new ArrayList<String>();
		namespaces.add(namespaceURI);
	}

	public static final String domain1 = "domain1";
	public static final String domain2 = "domain2";
	public static final String geometry = "geometry";
  public static final String packageName = "Spatial";
  /**
   * @param level
   * @param version
   * @return
   */
  public static String getNamespaceURI(int level, int version) {
    return namespaceURI;
  }
	
}
