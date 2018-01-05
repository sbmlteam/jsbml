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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A convenient <i>Facade</i> to parse and write byte and character streams with
 * XML content using the interfaces and classes defined by the JAXP
 * specification.
 * 
 * @author Marco Donizelli
 * @see DocumentFactory
 * @see SAX2Parser
 * @see NodeWriter
 * @since 0.8
 */
public class JAXPFacade implements DocumentFactory, SAX2Parser, NodeWriter {

  /**
   * 
   */
  private static JAXPFacade instance_;
  /**
   * 
   */
  private static TransformerFactory transformer_factory;

  //
  //
  // Static initializer.
  //
  //
  static {

    instance_ = new JAXPFacade();

    try {
      transformer_factory = TransformerFactory.newInstance();
    } catch (Exception e) {
      throw new ExceptionInInitializerError(e);
    }
  }

  /**
   * 
   * Creates and returns a new {@code JAXPFacade}.
   * 
   * @return A new {@code JAXPFacade}.
   * 
   */
  public static JAXPFacade getInstance() {
    return instance_;
  }

  /**
   * 
   * Writes an XML DOM node to the specified character stream, using a
   * {@code javax.xml.transform.Transformer} instance created from
   * {@code streamSource}.
   * 
   * @param node
   *            The {@code org.w3c.dom.Node} instance representing the
   *            XML DOM node to be written to {@code characterStream}.
   * @param characterStream
   *            The character stream where {@code node} is to be written.
   * @param streamSource
   *            An object that holds an URI, input stream, etc.
   * @param indent
   *            Flag to indicate whether the output should be indented or not.
   * @throws NullPointerException
   *             If {@code node} or {@code characterStream} are
   *             {@code null}.
   * @throws RuntimeException
   *             If any error occurs (transformer configuration errors,
   *             transformer errors).
   * 
   */
  public static void write(Node node, Writer characterStream,
    StreamSource streamSource, boolean indent) {

    if (node == null || characterStream == null) {
      throw new NullPointerException();
    }

    try {
      Transformer transformer = streamSource == null ? transformer_factory
        .newTransformer()
        : transformer_factory.newTransformer(streamSource);
        transformer.setOutputProperty(OutputKeys.INDENT, indent ? "yes"
          : "no");
        transformer.transform(new DOMSource(node), new StreamResult(
          characterStream));
    } catch (TransformerConfigurationException e) {
      throw new RuntimeException(e);
    } catch (TransformerException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Prevents the generation of the default constructor (singleton pattern).
   */
  private JAXPFacade() {
  }

  /**
   * 
   * Creates an XML DOM document by parsing the content of the specified byte
   * stream as XML, using a <i>nonvalidating</i> parser.
   * 
   * @param byteStream
   *            The byte stream which content is parsed as XML to create the
   *            XML DOM document.
   * @param namespaceAware
   *            A flag to indicate whether the parser should know about
   *            namespaces or not.
   * @return The {@code org.w3c.dom.Document} instance representing the
   *         XML DOM document created from the {@code byteStream} XML
   *         content.
   * @throws SAXException
   * @throws NullPointerException
   *             If {@code byteStream} is {@code null}.
   * @throws RuntimeException
   *             If any error occurs (parser configuration errors, I/O errors,
   *             SAX parsing errors).
   * 
   */
  @Override
  public Document create(InputStream byteStream, boolean namespaceAware)
      throws SAXException {

    if (byteStream == null) {
      throw new NullPointerException();
    }

    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory
          .newInstance();
      factory.setNamespaceAware(namespaceAware);
      factory.setValidating(false);
      DocumentBuilder builder = factory.newDocumentBuilder();
      return builder.parse(byteStream);
    } catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (SAXException e) {
      throw e;
    }
  }

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
   *            found in the data set, an
   *            {@code org.xml.sax.SAXParseException} is thrown.
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
   * @throws NullPointerException
   *             If {@code byteStream} is {@code null}.
   * @throws RuntimeException
   *             If any error occurs (parser configuration errors, I/O errors,
   *             SAX parsing errors).
   * 
   */
  @Override
  public Document create(InputStream byteStream, Object[] schemas,
    ErrorHandler handler) throws SAXException {

    if (byteStream == null) {
      throw new NullPointerException();
    }

    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory
          .newInstance();
      factory.setNamespaceAware(true);
      factory.setValidating(true);
      factory.setAttribute(
        "http://java.sun.com/xml/jaxp/properties/schemaLanguage",
          "http://www.w3.org/2001/XMLSchema");
      if (schemas != null) {
        factory.setAttribute(
          "http://java.sun.com/xml/jaxp/properties/schemaSource",
          schemas);
      }
      DocumentBuilder builder = factory.newDocumentBuilder();
      if (handler != null) {
        builder.setErrorHandler(handler);
      }
      return builder.parse(byteStream);
    } catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (SAXException e) {
      throw e;
    }
  }

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
   * @throws NullPointerException
   *             If {@code characterStream} is {@code null}.
   * @throws RuntimeException
   *             If any error occurs (parser configuration errors, I/O errors,
   *             SAX parsing errors).
   * 
   */
  @Override
  public Document create(Reader characterStream, boolean namespaceAware)
      throws SAXException {

    if (characterStream == null) {
      throw new NullPointerException();
    }

    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory
          .newInstance();
      factory.setNamespaceAware(namespaceAware);
      factory.setValidating(false);
      DocumentBuilder builder = factory.newDocumentBuilder();
      return builder.parse(new InputSource(characterStream));
    } catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (SAXException e) {
      throw e;
    }
  }

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
   *            found in the data set, an
   *            {@code org.xml.sax.SAXParseException} is thrown.
   * @param handler
   *            The error handler to be used to report errors occurred while
   *            parsing the {@code characterStream} XML content. Setting
   *            this to {@code null} will result in the underlying
   *            implementation using it's own default implementation and
   *            behavior.
   * @return The {@code org.w3c.dom.Document} instance representing the
   *         XML DOM document created from the {@code characterStream}
   *         XML content.
   * @throws NullPointerException
   *             If {@code characterStream} is {@code null}.
   * @throws RuntimeException
   *             If any error occurs (parser configuration errors, I/O errors,
   *             SAX parsing errors).
   * 
   */
  @Override
  public Document create(Reader characterStream, Object[] schemas,
    ErrorHandler handler) {

    if (characterStream == null) {
      throw new NullPointerException();
    }

    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory
          .newInstance();
      factory.setNamespaceAware(true);
      factory.setValidating(true);
      factory.setAttribute(
        "http://java.sun.com/xml/jaxp/properties/schemaLanguage",
          "http://www.w3.org/2001/XMLSchema");
      if (schemas != null) {
        factory.setAttribute(
          "http://java.sun.com/xml/jaxp/properties/schemaSource",
          schemas);
      }
      DocumentBuilder builder = factory.newDocumentBuilder();
      if (handler != null) {
        builder.setErrorHandler(handler);
      }
      return builder.parse(new InputSource(characterStream));
    } catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (SAXException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 
   * Parses the content of a byte stream as XML, using a <i>nonvalidating</i>
   * parser and the specified SAX2 default event handler.
   * 
   * @param byteStream
   *            The byte stream which content has to be parsed as XML.
   * @param handler
   *            The SAX2 default event handler to use for parsing
   *            {@code byteStream}.
   * @param namespaceAware
   *            A flag to indicate whether the parser should know about
   *            namespaces or not.
   * @throws NullPointerException
   *             If {@code byteStream} or {@code handler} are
   *             {@code null}.
   * @throws RuntimeException
   *             If any error occurs (parser configuration errors, I/O errors,
   *             SAX parsing errors).
   * 
   */
  @Override
  public void parse(InputStream byteStream, DefaultHandler handler,
    boolean namespaceAware) {

    if (byteStream == null || handler == null) {
      throw new NullPointerException();
    }

    try {
      SAXParserFactory sax_parser_factory = SAXParserFactory
          .newInstance();
      sax_parser_factory.setNamespaceAware(namespaceAware);
      sax_parser_factory.setValidating(false);
      SAXParser sax_parser = sax_parser_factory.newSAXParser();
      sax_parser.parse(byteStream, handler);
    } catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    } catch (SAXNotRecognizedException e) {
      throw new RuntimeException(e);
    } catch (SAXNotSupportedException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (SAXException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 
   * Parses the content of a byte stream as XML, using a <i>validating</i>
   * parser and the specified SAX2 default event handler.
   * 
   * @param byteStream
   *            The byte stream which content has to be parsed as XML.
   * @param handler
   *            The SAX2 default event handler to use for parsing
   *            {@code byteStream}.
   * @param schemas
   *            An optional array of either {@code java.io.File}
   *            instances containing the abstract pathnames, or of
   *            {@code java.io.String} instances containing the URIs,
   *            pointing to the schemas to use in the validation process. If
   *            set to {@code null}, the schemas defined in the data set
   *            will be used. If set to {@code null} and no schemas are
   *            found in the data set, an
   *            {@code org.xml.sax.SAXParseException} is thrown.
   * @throws NullPointerException
   *             If {@code byteStream} or {@code handler} are
   *             {@code null}.
   * @throws RuntimeException
   *             If any error occurs (parser configuration errors, I/O errors,
   *             SAX parsing errors).
   * 
   */
  @Override
  public void parse(InputStream byteStream, DefaultHandler handler,
    Object[] schemas) {

    if (byteStream == null || handler == null) {
      throw new NullPointerException();
    }

    try {
      SAXParserFactory sax_parser_factory = SAXParserFactory
          .newInstance();
      sax_parser_factory.setNamespaceAware(true);
      sax_parser_factory.setValidating(true);
      SAXParser sax_parser = sax_parser_factory.newSAXParser();
      sax_parser.setProperty(
        "http://java.sun.com/xml/jaxp/properties/schemaLanguage",
          "http://www.w3.org/2001/XMLSchema");
      if (schemas != null) {
        sax_parser.setProperty(
          "http://java.sun.com/xml/jaxp/properties/schemaSource",
          schemas);
      }
      sax_parser.parse(byteStream, handler);
    } catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    } catch (SAXNotRecognizedException e) {
      throw new RuntimeException(e);
    } catch (SAXNotSupportedException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (SAXException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 
   * Parses the content of a character stream as XML, using a
   * <i>nonvalidating</i> parser and the specified SAX2 default event handler.
   * 
   * @param characterStream
   *            The character stream which content has to be parsed as XML.
   * @param handler
   *            The SAX2 default event handler to use for parsing
   *            {@code characterStream}.
   * @param namespaceAware
   *            A flag to indicate whether the parser should know about
   *            namespaces or not.
   * @throws NullPointerException
   *             If {@code characterStream} or {@code handler} are
   *             {@code null}.
   * @throws RuntimeException
   *             If any error occurs (parser configuration errors, I/O errors,
   *             SAX parsing errors).
   * 
   */
  @Override
  public void parse(Reader characterStream, DefaultHandler handler,
    boolean namespaceAware) {

    if (characterStream == null || handler == null) {
      throw new NullPointerException();
    }

    try {
      SAXParserFactory sax_parser_factory = SAXParserFactory
          .newInstance();
      sax_parser_factory.setNamespaceAware(namespaceAware);
      sax_parser_factory.setValidating(false);
      javax.xml.parsers.SAXParser sax_parser = sax_parser_factory
          .newSAXParser();
      sax_parser.parse(new InputSource(characterStream), handler);
    } catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    } catch (SAXNotRecognizedException e) {
      throw new RuntimeException(e);
    } catch (SAXNotSupportedException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (SAXException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 
   * Parses the content of a character stream as XML, using a
   * <i>validating</i> parser and the specified SAX2 default event handler.
   * 
   * @param characterStream
   *            The character stream which content has to be parsed as XML.
   * @param handler
   *            The SAX2 default event handler to use for parsing
   *            {@code characterStream}.
   * @param schemas
   *            An optional array of either {@code java.io.File}
   *            instances containing the abstract pathnames, or of
   *            {@code java.io.String} instances containing the URIs,
   *            pointing to the schemas to use in the validation process. If
   *            set to {@code null}, the schemas defined in the data set
   *            will be used. If set to {@code null} and no schemas are
   *            found in the data set, an
   *            {@code org.xml.sax.SAXParseException} is thrown.
   * @throws NullPointerException
   *             If {@code characterStream} or {@code handler} are
   *             {@code null}.
   * @throws RuntimeException
   *             If any error occurs (parser configuration errors, I/O errors,
   *             SAX parsing errors).
   * 
   */
  @Override
  public void parse(Reader characterStream, DefaultHandler handler,
    Object[] schemas) {

    if (characterStream == null || handler == null) {
      throw new NullPointerException();
    }

    try {
      SAXParserFactory sax_parser_factory = SAXParserFactory
          .newInstance();
      sax_parser_factory.setNamespaceAware(true);
      sax_parser_factory.setValidating(true);
      SAXParser sax_parser = sax_parser_factory.newSAXParser();
      sax_parser.setProperty(
        "http://java.sun.com/xml/jaxp/properties/schemaLanguage",
          "http://www.w3.org/2001/XMLSchema");
      if (schemas != null) {
        sax_parser.setProperty(
          "http://java.sun.com/xml/jaxp/properties/schemaSource",
          schemas);
      }
      sax_parser.parse(new InputSource(characterStream), handler);
    } catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    } catch (SAXNotRecognizedException e) {
      throw new RuntimeException(e);
    } catch (SAXNotSupportedException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (SAXException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 
   * Writes an XML DOM node to the specified byte stream.
   * 
   * @param node
   *            The {@code org.w3c.dom.Node} instance representing the
   *            XML DOM node to be written to {@code byteStream}.
   * @param byteStream
   *            The byte stream where {@code node} is to be written.
   * @param indent
   *            Flag to indicate whether the output should be indented or not.
   * @throws NullPointerException
   *             If {@code node} or {@code byteStream} are
   *             {@code null}.
   * @throws RuntimeException
   *             If any error occurs (transformer configuration errors,
   *             transformer errors).
   * 
   */
  @Override
  public void write(Node node, OutputStream byteStream, boolean indent) {

    write(node, byteStream, null, indent);
  }

  /**
   * 
   * Writes an XML DOM node to the specified byte stream, using a
   * {@code javax.xml.transform.Transformer} instance created from
   * {@code streamSource}.
   * 
   * @param node
   *            The {@code org.w3c.dom.Node} instance representing the
   *            XML DOM node to be written to {@code byteStream}.
   * @param byteStream
   *            The byte stream where {@code node} is to be written.
   * @param streamSource
   *            An object that holds an URI, input stream, etc.
   * @param indent
   *            Flag to indicate whether the output should be indented or not.
   * @throws NullPointerException
   *             If {@code node} or {@code byteStream} are
   *             {@code null}.
   * @throws RuntimeException
   *             If any error occurs (transformer configuration errors,
   *             transformer errors).
   * 
   */
  public void write(Node node, OutputStream byteStream,
    StreamSource streamSource, boolean indent) {

    if (node == null || byteStream == null) {
      throw new NullPointerException();
    }

    try {
      Transformer transformer = streamSource == null ? transformer_factory
        .newTransformer()
        : transformer_factory.newTransformer(streamSource);
        transformer.setOutputProperty(OutputKeys.INDENT, indent ? "yes"
          : "no");
        transformer.transform(new DOMSource(node), new StreamResult(
          byteStream));
    } catch (TransformerConfigurationException e) {
      throw new RuntimeException(e);
    } catch (TransformerException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 
   * Writes an XML DOM node to the specified character stream.
   * 
   * @param node
   *            The {@code org.w3c.dom.Node} instance representing the
   *            XML DOM node to be written to {@code characterStream}.
   * @param characterStream
   *            The character stream where {@code node} is to be written.
   * @param indent
   *            Flag to indicate whether the output should be indented or not.
   * @throws NullPointerException
   *             If {@code node} or {@code characterStream} are
   *             {@code null}.
   * @throws RuntimeException
   *             If any error occurs (transformer configuration errors,
   *             transformer errors).
   * 
   */
  @Override
  public void write(Node node, Writer characterStream, boolean indent) {

    write(node, characterStream, null, indent);
  }
}
