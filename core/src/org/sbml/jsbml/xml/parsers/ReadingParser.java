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

import org.sbml.jsbml.Event;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.Trigger;

/**
 * The interface to implement for the SBML parsers reading SBML files.
 * 
 * @author Marine Dumousseau
 * @author Nicolas Rodriguez
 * @since 0.8
 */
public interface ReadingParser {

  /**
   * Process the XML attribute and modify 'contextObject' in consequence.
   * <p>For example, if the contextObject is an instance of {@link Reaction} and the
   * attributeName is 'fast', this method will set the 'fast' variable of the
   * 'contextObject' to 'value'. Then it will return the modified
   * {@link Reaction} instance.
   * 
   * @param elementName
   *        the localName of the XML element.
   * @param attributeName
   *        the attribute localName of the XML element.
   * @param value
   *        the value of the XML attribute.
   * @param URI
   * @param prefix
   *        the attribute prefix
   * @param isLastAttribute
   *        boolean value to know if this attribute is the last attribute of the
   *        XML element.
   * @param contextObject
   *        the object to set or modify depending on the identity of the current
   *        attribute. This object
   *        represents the context of the XML attribute in the SBMLDocument.
   * @return true if the attribute was recognized and stored in the contextObject, false otherwise.
   */
  public boolean processAttribute(String elementName, String attributeName, String value, String URI, String prefix, boolean isLastAttribute, Object contextObject);

  /**
   * Process the text of a XML element and modify 'contextObject' in
   * consequence.
   * <p>For example, if the contextObject is an instance of
   * {@link org.sbml.jsbml.Creator} and the elementName is 'Family',
   * this method will set the familyName of the 'contextObject' to the text
   * value. Then it will return the changed {@link org.sbml.jsbml.Creator}
   * instance.
   * 
   * @param elementName
   *        the localName of the XML element.
   * @param characters
   *        the text of this XML element.
   * @param contextObject
   *        the object to set or modify depending on the identity of the current
   *        element. This object
   *        represents the context of the XML element in the SBMLDocument.
   */
  public void processCharactersOf(String elementName, String characters, Object contextObject);

  /**
   * Process the end of the document. Do the necessary changes in the
   * SBMLDocument. For example, check if all the annotations are valid, etc.
   * 
   * @param sbmlDocument
   *        the final initialized SBMLDocument instance.
   */
  public void processEndDocument(SBMLDocument sbmlDocument);

  /**
   * Process the end of the element 'elementName'. Modify or not the
   * contextObject.
   * 
   * @param elementName
   *        the localName of the XML element.
   * @param prefix
   *        the prefix of the XML element.
   * @param isNested
   *        boolean value to know if the XML element is a nested element.
   * @param contextObject
   *        the object to set or modify depending on the identity of the current
   *        element. This object
   *        represents the context of the XML element in the SBMLDocument.
   * @return {@code true} to remove the contextObject from the stack, if
   *         {@code false} is returned the contextObject will stay on top
   *         of the stack
   */
  public boolean processEndElement(String elementName, String prefix, boolean isNested, Object contextObject);

  /**
   * Process the namespace and modify the contextObject in consequence.
   * <p>For example, if the contextObject is an instance of SBMLDocument, the
   * namespaces will be stored in the SBMLNamespaces HashMap
   * of this SBMLDocument.
   * 
   * @param elementName
   *        the localName of the XML element.
   * @param URI
   *        the URI of the namespace
   * @param prefix
   *        the prefix of the namespace.
   * @param localName
   *        the localName of the namespace.
   * @param hasAttributes
   *        boolean value to know if there are attributes after the namespace
   *        declarations.
   * @param isLastNamespace
   *        boolean value to know if this namespace is the last namespace of
   *        this element.
   * @param contextObject
   *        the object to set or modify depending on the identity of the current
   *        element. This object
   *        represents the context of the XML element in the SBMLDocument.
   */
  public void processNamespace(String elementName, String URI, String prefix, String localName, boolean hasAttributes, boolean isLastNamespace, Object contextObject);

  /**
   * Process the XML element and modify 'contextObject' in consequence.
   * <p>For example, if the contextObject is an instance of {@link Event} and the
   * elementName is 'trigger', this method will create a new {@link Trigger} instance
   * and will set the trigger instance of the 'contextObject' to the new
   * {@link Trigger}. Then the method will return the new {@link Trigger} instance which is the
   * new environment.
   * 
   * @param elementName
   *        the localName of the XML element to process
   * @param URI
   * @param prefix
   *        the prefix of the XML element to process
   * @param hasAttributes
   *        boolean value to know if this XML element has attributes.
   * @param hasNamespaces
   *        boolean value to know if this XML element contains namespace
   *        declarations.
   * @param contextObject
   *        the object to set or modify depending on the identity of the current
   *        XML element. This object
   *        represents the context of the XML element in the SBMLDocument.
   * @return a new contextObject which represents the environment of the next
   *         node/subnode in the SBMLDocument. If null is returned,
   *         the contextObject will not change.
   */
  public Object processStartElement(String elementName, String URI, String prefix, boolean hasAttributes, boolean hasNamespaces, Object contextObject);

  /**
   * Returns a {@link List} of all the namespaces that this parser is handling.
   * 
   * @return a {@link List} of all the namespaces that this parser is handling
   */
  public List<String> getNamespaces();

}
