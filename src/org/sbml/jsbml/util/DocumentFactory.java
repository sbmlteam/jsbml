package org.sbml.jsbml.util;

import java.io.*;
import org.w3c.dom.*;
import org.xml.sax.*;

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
     * @param byteStream The byte stream whose content is parsed as XML to
     * create the XML DOM document.
     * @param namespaceAware A flag to indicate whether the parser should know
     * about namespaces or not.
     * @return The <code>org.w3c.dom.Document</code> instance representing
     * the XML DOM document created from the <code>byteStream</code> XML
     * content.
     *
     */
    public Document create(InputStream byteStream,
			   boolean namespaceAware);

    /**
     *
     * Creates an XML DOM document by parsing the content of the specified
     * byte stream as XML, using a <i>validating</i> parser.
     *
     * @param byteStream The byte stream whose content is parsed as XML to
     * create the XML DOM document.
     * @param schemas An optional array of either <code>java.io.File</code>
     * instances containing the abstract pathnames, or of <code>java.io.String</code>
     * instances containing the URIs, pointing to the schemas to use in the validation
     * process. If set to <code>null</code>, the schemas defined in the data
     * set will be used. If set to <code>null</code> and no schemas are found
     * in the data set, an exception is most likely to be thrown by the
     * underlying implementation.
     * @param handler The error handler to be used to report errors
     * occurred while parsing the <code>byteStream</code> XML content.
     * Setting this to <code>null</code> will result in the underlying
     * implementation using it's own default implementation and behavior.
     * @return The <code>org.w3c.dom.Document</code> instance representing
     * the XML DOM document created from the <code>byteStream</code> XML
     * content.
     *
     */
    public Document create(InputStream byteStream,
			   Object[] schemas,
			   ErrorHandler handler);

    /**
     *
     * Creates an XML DOM document by parsing the content of the specified
     * character stream as XML, using a <i>nonvalidating</i> parser.
     * 
     * @param characterStream The character stream whose content is parsed
     * as XML to create the XML DOM document.
     * @param namespaceAware A flag to indicate whether the parser should know
     * about namespaces or not.
     * @return The <code>org.w3c.dom.Document</code> instance representing
     * the XML DOM document created from the <code>characterStream</code>
     * XML content.
     *
     */
    public Document create(Reader characterStream,
			   boolean namespaceAware);

    /**
     *
     * Creates an XML DOM document by parsing the content of the specified
     * character stream as XML, using a <i>validating</i> parser.
     *
     * @param characterStream The character stream whose content is parsed
     * as XML to create the XML DOM document.
     * @param schemas An optional array of either <code>java.io.File</code>
     * instances containing the abstract pathnames, or of <code>java.io.String</code>
     * instances containing the URIs, pointing to the schemas to use in the validation
     * process. If set to <code>null</code>, the schemas defined in the data
     * set will be used. If set to <code>null</code> and no schemas are found
     * in the data set, an exception is most likely to be thrown by the
     * underlying implementation.
     * @param handler The error handler to be used to report errors
     * occurred while parsing the <code>characterStream</code> XML content.
     * Setting this to <code>null</code> will result in the underlying
     * implementation using it's own default implementation and behavior.
     * @return The <code>org.w3c.dom.Document</code> instance representing
     * the XML DOM document created from the <code>characterStream</code>
     * XML content.
     *
     */
    public Document create(Reader characterStream,
			   Object[] schemas,
			   ErrorHandler handler);
}
