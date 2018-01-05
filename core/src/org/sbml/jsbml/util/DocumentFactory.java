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
package org.sbml.jsbml.util;

import java.io.InputStream;
import java.io.Reader;

import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

/**
 *  * An interface for XML DOM document factories.
 * 
 * @author Marco Donizelli
 * @since 0.8
 */
public interface DocumentFactory {

  /**
   * 
   * Creates an XML DOM document by parsing the content of the specified byte
   * stream as XML, using a <i>nonvalidating</i> parser.
   * 
   * @param byteStream
   *            The byte stream whose content is parsed as XML to create the
   *            XML DOM document.
   * @param namespaceAware
   *            A flag to indicate whether the parser should know about
   *            namespaces or not.
   * @return The {@code org.w3c.dom.Document} instance representing the
   *         XML DOM document created from the {@code byteStream} XML
   *         content.
   * @throws SAXException
   * 
   */
  public Document create(InputStream byteStream, boolean namespaceAware) throws SAXException;

  /**
   * 
   * Creates an XML DOM document by parsing the content of the specified byte
   * stream as XML, using a <i>validating</i> parser.
   * 
   * @param byteStream
   *            The byte stream whose content is parsed as XML to create the
   *            XML DOM document.
   * @param schemas
   *            An optional array of either {@code java.io.File}
   *            instances containing the abstract pathnames, or of
   *            {@code java.io.String} instances containing the URIs,
   *            pointing to the schemas to use in the validation process. If
   *            set to {@code null}, the schemas defined in the data set
   *            will be used. If set to {@code null} and no schemas are
   *            found in the data set, an exception is most likely to be
   *            thrown by the underlying implementation.
   * @param handler
   *            The error handler to be used to report errors occurred while
   *            parsing the {@code byteStream} XML content. Setting this
   *            to {@code null} will result in the underlying
   *            implementation using it's own default implementation and
   *            behavior.
   * @return The {@code org.w3c.dom.Document} instance representing the
   *         XML DOM document created from the {@code byteStream} XML
   *         content.
   * @throws SAXException
   * 
   */
  public Document create(InputStream byteStream, Object[] schemas,
    ErrorHandler handler) throws SAXException;

  /**
   * 
   * Creates an XML DOM document by parsing the content of the specified
   * character stream as XML, using a <i>nonvalidating</i> parser.
   * 
   * @param characterStream
   *            The character stream whose content is parsed as XML to create
   *            the XML DOM document.
   * @param namespaceAware
   *            A flag to indicate whether the parser should know about
   *            namespaces or not.
   * @return The {@code org.w3c.dom.Document} instance representing the
   *         XML DOM document created from the {@code characterStream}
   *         XML content.
   * @throws SAXException
   * 
   */
  public Document create(Reader characterStream, boolean namespaceAware) throws SAXException;

  /**
   * 
   * Creates an XML DOM document by parsing the content of the specified
   * character stream as XML, using a <i>validating</i> parser.
   * 
   * @param characterStream
   *            The character stream whose content is parsed as XML to create
   *            the XML DOM document.
   * @param schemas
   *            An optional array of either {@code java.io.File}
   *            instances containing the abstract pathnames, or of
   *            {@code java.io.String} instances containing the URIs,
   *            pointing to the schemas to use in the validation process. If
   *            set to {@code null}, the schemas defined in the data set
   *            will be used. If set to {@code null} and no schemas are
   *            found in the data set, an exception is most likely to be
   *            thrown by the underlying implementation.
   * @param handler
   *            The error handler to be used to report errors occurred while
   *            parsing the {@code characterStream} XML content. Setting
   *            this to {@code null} will result in the underlying
   *            implementation using it's own default implementation and
   *            behavior.
   * @return The {@code org.w3c.dom.Document} instance representing the
   *         XML DOM document created from the {@code characterStream}
   *         XML content.
   * 
   */
  public Document create(Reader characterStream, Object[] schemas,
    ErrorHandler handler);
}
