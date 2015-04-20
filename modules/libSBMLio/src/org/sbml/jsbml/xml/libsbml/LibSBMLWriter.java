/*
 * $Id: LibSBMLWriter.java 2109 2015-01-05 04:50:45Z andreas-draeger $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/modules/libSBMLio/src/org/sbml/jsbml/xml/libsbml/LibSBMLWriter.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2015 jointly by the following organizations:
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
package org.sbml.jsbml.xml.libsbml;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLOutputConverter;

/**
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev: 2109 $
 */
public class LibSBMLWriter implements SBMLOutputConverter<org.sbml.libsbml.Model> {

  /**
   * A {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger(LibSBMLWriter.class);

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBMLWriter#getWriteWarnings()
   */
  @Override
  public List<SBMLException> getWriteWarnings(org.sbml.libsbml.Model model) {
    org.sbml.libsbml.SBMLDocument doc = model.getSBMLDocument();
    doc.checkConsistency();
    List<SBMLException> sb = new LinkedList<SBMLException>();
    for (int i = 0; i < doc.getNumErrors(); i++) {
      sb.add(LibSBMLReader.convert(doc.getError(i)));
    }
    return sb;
  }

  /* (non-Javadoc)
   * @see org.sbml.SBMLWriter#writeSBML(java.lang.Object, java.lang.String)
   */
  @Override
  public boolean writeSBML(org.sbml.libsbml.Model model, String filename)
      throws SBMLException {
    return writeSBML(model, filename, null, null);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBMLOutputConverter#writeSBML(java.lang.Object, java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean writeSBML(org.sbml.libsbml.Model model, String filename,
    String programName, String programVersionNumber)
        throws SBMLException {
    org.sbml.libsbml.SBMLDocument d = model.getSBMLDocument();
    org.sbml.libsbml.SBMLWriter writer = new org.sbml.libsbml.SBMLWriter();
    if (programName != null) {
      writer.setProgramName(programName);
    }
    if (programVersionNumber != null) {
      writer.setProgramVersion(programVersionNumber);
    }
    d.checkInternalConsistency();
    d.checkConsistency();
    boolean errorFatal = false;
    StringBuilder builder = new StringBuilder();
    for (long i = 0; i < d.getNumErrors(); i++) {
      org.sbml.libsbml.SBMLError e = d.getError(i);
      builder.append(e.getMessage());
      builder.append(System.getProperty("line.separator"));
      if (e.isError() || e.isFatal()) {
        errorFatal = true;
      }
    }
    if (errorFatal) {
      logger.error(builder.toString());
    }
    boolean success = writer.writeSBML(d, filename);
    if (!success) {
      throw new SBMLException(builder.toString());
    }
    return success;
  }

}
