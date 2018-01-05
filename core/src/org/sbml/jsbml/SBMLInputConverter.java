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

import java.io.File;
import java.util.List;

import org.sbml.jsbml.util.ProgressListener;

/**
 * This interface allows the implementing class to create a JSBML model based on
 * some other data structure. Possible examples are CellDesigner plug-in data
 * structures or Objects from libSBML. Other data structures can also be
 * considered, such as a conversion of BioPax or CellML into JSBML data
 * structures.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @param <T> The type of input model that can be treated by this converter.
 */
public interface SBMLInputConverter<T> {

  /**
   * Takes a model in an arbitrary (but type-secure) format and delivers a
   * corresponding JSBML-compliant model.
   * 
   * @param model
   * @return
   * @throws Exception
   */
  public Model convertModel(T model) throws Exception;

  /**
   * 
   * @param sbmlFile
   * @return
   * @throws Exception
   */
  public abstract SBMLDocument convertSBMLDocument(File sbmlFile) throws Exception;

  /**
   * 
   * @param fileName
   * @return
   * @throws Exception
   */
  public abstract SBMLDocument convertSBMLDocument(String fileName) throws Exception;

  /**
   * @return The original model that has been converted by this class in the
   *         method {@link #convertModel(Object)}. Typically, the identical
   *         result can also be obtained by calling
   *         {@link Model#getUserObject(Object)} with
   *         {@code ORIGINAL_MODEL_KEY} as parameter.
   */
  public T getOriginalModel();

  /**
   * Creates an SBML error report and returns the list of errors.
   * 
   * @return Warnings that occur during the conversion of the model.
   */
  public List<SBMLException> getWarnings();

  /**
   * @param listener the listener to set
   */
  public void setListener(ProgressListener listener);

}
