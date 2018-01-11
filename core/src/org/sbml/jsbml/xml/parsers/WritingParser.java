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
package org.sbml.jsbml.xml.parsers;

import java.util.List;

import org.sbml.jsbml.SBase;
import org.sbml.jsbml.xml.stax.SBMLObjectForXML;

/**
 * The interface to implement for a parser which writes a SBML file.
 * 
 * @author Marine Dumousseau
 * @since 0.8
 */
public interface WritingParser {

  /**
   * Returns the list of children of the {@code objectToWrite}.
   * 
   * @param objectToWrite
   *            the {@link SBase} component to write.
   * @return the list of components that '{@link SBase}' contains. Represents
   *         the list of subNodes of this {@link SBase} component.
   */
  public List<Object> getListOfSBMLElementsToWrite(Object objectToWrite);

  /**
   * Adds the XML attributes of the {@code sbmlElementToWrite} to the attributes
   * HashMap of the {@code xmlObject}.
   * 
   * @param xmlObject
   *            contains the XML information about sbmlElement.
   * @param sbmlElementToWrite
   *            the {@link SBase} component to write
   */
  public void writeAttributes(SBMLObjectForXML xmlObject,
    Object sbmlElementToWrite);

  /**
   * Sets the characters of xmlObject depending on the sbml element to write.
   * 
   * @param xmlObject
   *            contains the XML information about sbmlElement.
   * @param sbmlElementToWrite
   *            the {@link SBase} component to write
   */
  public void writeCharacters(SBMLObjectForXML xmlObject,
    Object sbmlElementToWrite);

  /**
   * Sets the name of xmlObject (if it is not set) to the element name of
   * sbmlElementToWrite.
   * 
   * @param xmlObject
   *            contains the XML information about sbmlElement.
   * @param sbmlElementToWrite
   *            the {@link SBase} component to write
   */
  public void writeElement(SBMLObjectForXML xmlObject,
    Object sbmlElementToWrite);

  /**
   * Sets the namespace of xmlObject (if it is not set) to the namespace of
   * sbmlElementToWrite.
   * 
   * @param xmlObject
   *            contains the XML information about sbmlElement.
   * @param sbmlElementToWrite
   *            the {@link SBase} component to write
   */
  public void writeNamespaces(SBMLObjectForXML xmlObject,
    Object sbmlElementToWrite);

  /**
   * Returns a {@link List} of all the namespaces that this parser is handling
   * 
   * @return a {@link List} of all the namespaces that this parser is handling
   */
  public List<String> getNamespaces();

}
