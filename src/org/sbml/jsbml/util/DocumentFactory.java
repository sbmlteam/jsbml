/*
 * $Id$
 * $URL$
 *
 * 
 *==================================================================================
 * Copyright (c) 2009 The jsbml team.
 *
 * This file is part of jsbml, the pure java SBML library. Please visit
 * http://sbml.org for more information about SBML, and http://jsbml.sourceforge.net/
 * to get the latest version of jsbml.
 *
 * jsbml is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * jsbml is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with jsbml.  If not, see <http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html>.
 *
 *===================================================================================
 *
 */
package org.sbml.jsbml.util;

import java.io.InputStream;
import java.io.Reader;

import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;

/**
 * 
 * An interface for XML DOM document factories.
 * 
 * @author Marco Donizelli
 * @since 1.0alpha
 * 
 * @opt attributes
 * @opt types
 * @opt visibility
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
	 * @return The <code>org.w3c.dom.Document</code> instance representing the
	 *         XML DOM document created from the <code>byteStream</code> XML
	 *         content.
	 * 
	 */
	public Document create(InputStream byteStream, boolean namespaceAware);

	/**
	 * 
	 * Creates an XML DOM document by parsing the content of the specified byte
	 * stream as XML, using a <i>validating</i> parser.
	 * 
	 * @param byteStream
	 *            The byte stream whose content is parsed as XML to create the
	 *            XML DOM document.
	 * @param schemas
	 *            An optional array of either <code>java.io.File</code>
	 *            instances containing the abstract pathnames, or of
	 *            <code>java.io.String</code> instances containing the URIs,
	 *            pointing to the schemas to use in the validation process. If
	 *            set to <code>null</code>, the schemas defined in the data set
	 *            will be used. If set to <code>null</code> and no schemas are
	 *            found in the data set, an exception is most likely to be
	 *            thrown by the underlying implementation.
	 * @param handler
	 *            The error handler to be used to report errors occurred while
	 *            parsing the <code>byteStream</code> XML content. Setting this
	 *            to <code>null</code> will result in the underlying
	 *            implementation using it's own default implementation and
	 *            behavior.
	 * @return The <code>org.w3c.dom.Document</code> instance representing the
	 *         XML DOM document created from the <code>byteStream</code> XML
	 *         content.
	 * 
	 */
	public Document create(InputStream byteStream, Object[] schemas,
			ErrorHandler handler);

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
	 * @return The <code>org.w3c.dom.Document</code> instance representing the
	 *         XML DOM document created from the <code>characterStream</code>
	 *         XML content.
	 * 
	 */
	public Document create(Reader characterStream, boolean namespaceAware);

	/**
	 * 
	 * Creates an XML DOM document by parsing the content of the specified
	 * character stream as XML, using a <i>validating</i> parser.
	 * 
	 * @param characterStream
	 *            The character stream whose content is parsed as XML to create
	 *            the XML DOM document.
	 * @param schemas
	 *            An optional array of either <code>java.io.File</code>
	 *            instances containing the abstract pathnames, or of
	 *            <code>java.io.String</code> instances containing the URIs,
	 *            pointing to the schemas to use in the validation process. If
	 *            set to <code>null</code>, the schemas defined in the data set
	 *            will be used. If set to <code>null</code> and no schemas are
	 *            found in the data set, an exception is most likely to be
	 *            thrown by the underlying implementation.
	 * @param handler
	 *            The error handler to be used to report errors occurred while
	 *            parsing the <code>characterStream</code> XML content. Setting
	 *            this to <code>null</code> will result in the underlying
	 *            implementation using it's own default implementation and
	 *            behavior.
	 * @return The <code>org.w3c.dom.Document</code> instance representing the
	 *         XML DOM document created from the <code>characterStream</code>
	 *         XML content.
	 * 
	 */
	public Document create(Reader characterStream, Object[] schemas,
			ErrorHandler handler);
}
