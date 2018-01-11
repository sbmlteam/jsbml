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
package org.sbml.jsbml;

import java.io.IOException;
import java.util.List;

/**
 * This interface allows the implementing class to convert a JSBML model into
 * another data structure. Possible examples are a conversion into libSBML or
 * CellDesigner plug-in data structures. Other formats can also be implemented,
 * for instance, a BioPax or CellML converter could be implemented.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @param <T> The type of output model that can be treated by this converter.
 */
public interface SBMLOutputConverter<T> {

  /**
   * 
   * @param model
   * @return
   */
  public List<SBMLException> getWriteWarnings(T model);

  /**
   * This method is identical to the method
   * {@link #writeSBML(Object, String, String, String)},
   * but without the option to pass the program's name or version to the writer.
   * 
   * @param model
   * @param filename
   * @return
   * @throws SBMLException
   * @throws IOException
   * @see #writeSBML(Object, String, String, String)
   */
  public boolean writeSBML(T model, String filename)
      throws SBMLException, IOException;

  /**
   * Writes the given model (in which format it might be given) to an SBML file
   * as specified by the given filename and returns {@code true} if this
   * operation could be successfully executed, {@code false} otherwise.
   * 
   * @param model
   *        the model (in whatever format, but with respect to the type of
   *        this generic class).
   * @param filename
   *        the file's absolute or relative path to which the SBML code
   *        should be serialized.
   * @param programName
   *        the name of the program that uses this method.
   * @param versionNumber
   *        the version number of the program that accesses this
   *        method.
   * @return {@code true} if this
   *         operation could be successfully executed, {@code false} otherwise.
   * @throws SBMLException
   * @throws IOException
   * @see #writeSBML(Object, String)
   */
  public boolean writeSBML(T model, String filename,
    String programName, String versionNumber) throws SBMLException,
    IOException;

}
