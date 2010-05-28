package org.sbml.jsbml.util;

import java.io.*;
import org.xml.sax.helpers.*;

/**
 * 
 * An interface for XML SAX2 parsers.
 * 
 * @author Marco Donizelli
 * @since 1.1
 * 
 * @opt attributes
 * @opt types
 * @opt visibility
 */
public interface SAX2Parser {

	/**
	 * 
	 * Parses the content of a byte stream as XML, using a <i>nonvalidating</i>
	 * parser and the specified SAX2 default event handler.
	 * 
	 * @param byteStream
	 *            The byte stream which content has to be parsed as XML.
	 * @param handler
	 *            The SAX2 default event handler to use for parsing
	 *            <code>byteStream</code>.
	 * @param namespaceAware
	 *            A flag to indicate whether the parser should know about
	 *            namespaces or not.
	 * 
	 */
	public void parse(InputStream byteStream, DefaultHandler handler,
			boolean namespaceAware);

	/**
	 * 
	 * Parses the content of a byte stream as XML, using a <i>validating</i>
	 * parser and the specified SAX2 default event handler.
	 * 
	 * @param byteStream
	 *            The byte stream which content has to be parsed as XML.
	 * @param handler
	 *            The SAX2 default event handler to use for parsing
	 *            <code>byteStream</code>.
	 * @param schemas
	 *            An optional array of either <code>java.io.File</code>
	 *            instances containing the abstract pathnames, or of
	 *            <code>java.io.String</code> instances containing the URIs,
	 *            pointing to the schemas to use in the validation process. If
	 *            set to <code>null</code>, the schemas defined in the data set
	 *            will be used. If set to <code>null</code> and no schemas are
	 *            found in the data set, an exception is most likely to be
	 *            thrown by the underlying implementation.
	 * 
	 */
	public void parse(InputStream byteStream, DefaultHandler handler,
			Object[] schemas);

	/**
	 * 
	 * Parses the content of a character stream as XML, using a
	 * <i>nonvalidating</i> parser and the specified SAX2 default event handler.
	 * 
	 * @param characterStream
	 *            The character stream which content has to be parsed as XML.
	 * @param handler
	 *            The SAX2 default event handler to use for parsing
	 *            <code>characterStream</code>.
	 * @param namespaceAware
	 *            A flag to indicate whether the parser should know about
	 *            namespaces or not.
	 * 
	 */
	public void parse(Reader characterStream, DefaultHandler handler,
			boolean namespaceAware);

	/**
	 * 
	 * Parses the content of a character stream as XML, using a
	 * <i>validating</i> parser and the specified SAX2 default event handler.
	 * 
	 * @param characterStream
	 *            The character stream which content has to be parsed as XML.
	 * @param handler
	 *            The SAX2 default event handler to use for parsing
	 *            <code>characterStream</code>.
	 * @param schemas
	 *            An optional array of either <code>java.io.File</code>
	 *            instances containing the abstract pathnames, or of
	 *            <code>java.io.String</code> instances containing the URIs,
	 *            pointing to the schemas to use in the validation process. If
	 *            set to <code>null</code>, the schemas defined in the data set
	 *            will be used. If set to <code>null</code> and no schemas are
	 *            found in the data set, an exception is most likely to be
	 *            thrown by the underlying implementation.
	 * 
	 */
	public void parse(Reader characterStream, DefaultHandler handler,
			Object[] schemas);
}