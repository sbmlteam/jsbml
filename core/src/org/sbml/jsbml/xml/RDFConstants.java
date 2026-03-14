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
 * * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */

package org.sbml.jsbml.xml;

/**
 * Centralized constants for RDF and XHTML keywords used in SBML annotations.
 * * @author Deepak Yadav
 * @since 1.7
 */
public final class RDFConstants {

  /** The RDF namespace prefix. */
  public static final String PREFIX_RDF = "rdf";

  /** The Dublin Core namespace prefix. */
  public static final String PREFIX_DC = "dc";

  /** The Dublin Core Terms namespace prefix. */
  public static final String PREFIX_DCTERMS = "dcterms";

  /** The vCard namespace prefix. */
  public static final String PREFIX_VCARD = "vCard";

  /** The Biology Qualifiers namespace prefix. */
  public static final String PREFIX_BQBIOL = "bqbiol";

  /** The Model Qualifiers namespace prefix. */
  public static final String PREFIX_BQMODEL = "bqmodel";

  /** The RDF 'RDF' element name. */
  public static final String RDF = "RDF";

  /** The RDF 'Description' element name. */
  public static final String DESCRIPTION = "Description";

  /** The RDF 'Bag' element name. */
  public static final String BAG = "Bag";

  /** The RDF 'li' element name. */
  public static final String LI = "li";

  /** The RDF 'about' attribute name. */
  public static final String ABOUT = "about";

  /** The RDF 'resource' attribute name. */
  public static final String RESOURCE = "resource";

  /** The RDF 'parseType' attribute name. */
  public static final String PARSE_TYPE = "parseType";

  /** Private constructor to prevent instantiation. */
  private RDFConstants() {
  }
}