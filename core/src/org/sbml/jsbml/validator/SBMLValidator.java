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
package org.sbml.jsbml.validator;

/**
 * \file    validateSBML.java
 * \brief   Validates an SBML document using the SBML.org Online Validator
 * \author  Ben Bornstein <sbml-team@caltech.edu>
 * \author  Akiya Jouraku <sbml-team@caltech.edu>
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.sbml.jsbml.SBMLError;
import org.sbml.jsbml.SBMLErrorLog;
import org.sbml.jsbml.util.Detail;
import org.sbml.jsbml.util.Option;
import org.sbml.jsbml.xml.xstream.converter.SBMLErrorConverter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Validates an SBML document using the <a href="http://sbml.org/Facilities/Validator">SBML.org Online Validator</a>.
 * 
 * <p>This file is adapted from libSBML by Nicolas Rodriguez
 * 
 * @author Ben Bornstein <sbml-team@caltech.edu>
 * @author Akiya Jouraku <sbml-team@caltech.edu>
 * @author Nicolas Rodriguez
 * @since 0.8
 */
class Validator {

  /**
   * 
   */
  public static String validatorURL = "http://sbml.org/validator/";
  // public static String validatorURL = "http://sbml-validator.caltech.edu:8888/validator_servlet/ValidatorServlet";

  /**
   * Validates the given SBML filename (or http:// URL) by calling the
   * SBML.org online validator. The results are returned as an InputStream
   * whose format may be controlled by setting parameters.put("output", ...)
   * to one of: "xml", "xhtml", "json", "text" (default: xml).
   * @param filename
   * @param parameters
   * 
   * @return an InputStream containing the validation results.
   * @throws IOException
   */
  public static InputStream validateSBML(String filename,
    Map<String, String> parameters) throws IOException
  {
    Logger logger = Logger.getLogger(SBMLValidator.class);

    if (parameters.get("output") == null) {
      parameters.put("output", "xml");
    }

    MultipartPost post = new MultipartPost(validatorURL);

    if (filename.startsWith("http://")) {
      post.writeParameter("url", filename);
    } else {
      post.writeParameter("file", new File(filename));
    }

    try {
      Iterator<String> iter = parameters.keySet().iterator();

      while (iter.hasNext()) {
        String name = iter.next();
        String value = parameters.get(name);

        logger.debug("Validator.validateSBML : parameter " + name + " = " + value);

        post.writeParameter(name, value);
      }
    } catch (NoSuchElementException e) {
      e.printStackTrace();
    }

    return post.done();
  }
}

/**
 * Performs a Multipart HTTP post to the given URL. A post operation is started
 * with the creation of a MultipartPost object. Post parameters are sent with
 * writeParameter() and may be either strings or the contents of an XML file. A
 * post is finished by calling done() which returns an InputStream for reading
 * the servers response.
 * 
 * NOTE: This class is meant to communicate with the SBML.org online validator.
 * As such, it assumes uploaded files are XML and always sends a Content-Type:
 * text/xml.
 */
class MultipartPost {
  /**
   * @param url
   * @throws IOException
   */
  public MultipartPost(String url) throws IOException {
    Random random = new Random();

    connection = (new URL(url)).openConnection();
    boundary = "<<" + Long.toString(random.nextLong(), 30);
    String type = "multipart/form-data; boundary=" + boundary;

    connection.setDoOutput(true);
    connection.setRequestProperty("Content-Type", type);

    stream = connection.getOutputStream();
  }

  /**
   * @return
   * @throws IOException
   */
  public InputStream done() throws IOException {
    writeln("--" + boundary + "--");
    writeln();

    stream.close();

    return connection.getInputStream();
  }

  /**
   * @param name
   * @param value
   * @throws IOException
   */
  public void writeParameter(String name, String value) throws IOException {
    writeln("--" + boundary);
    writeln("Content-Disposition: form-data; name=\"" + name + "\"");
    writeln();
    writeln(value);
  }

  /**
   * @param name
   * @param file
   * @throws IOException
   */
  public void writeParameter(String name, File file) throws IOException {
    String prefix = "Content-Disposition: form-data; name=\"file\"; filename=";

    writeln("--" + boundary);
    writeln(prefix + '"' + file.getName() + '"');
    writeln("Content-Type: text/xml");
    writeln();

    InputStream source = new FileInputStream(file);
    copy(source, stream);

    // Adding a line return, otherwise the xml content is considered
    // invalid by libsbml
    // stream.write(System.getProperty ("line.separator").getBytes());
    writeln();

    stream.flush();
    source.close();
  }

  /**
   * @param source
   * @param destination
   * @throws IOException
   */
  void copy(InputStream source, OutputStream destination) throws IOException {
    byte[] buffer = new byte[8192];
    int nbytes = 0;

    while ((nbytes = source.read(buffer, 0, buffer.length)) >= 0) {
      destination.write(buffer, 0, nbytes);
    }
  }

  /**
   * @param s
   * @throws IOException
   */
  void writeln(String s) throws IOException {
    write(s);
    writeln();
  }

  /**
   * @throws IOException
   */
  void writeln() throws IOException {
    write('\r');
    write('\n');
  }

  /**
   * @param c
   * @throws IOException
   */
  void write(char c) throws IOException {
    stream.write(c);
  }

  /**
   * @param s
   * @throws IOException
   */
  void write(String s) throws IOException {
    stream.write(s.getBytes());
  }

  /**
   * 
   */
  URLConnection connection;
  /**
   * 
   */
  OutputStream stream;
  /**
   * 
   */
  String boundary;

}

/**
 * Validates the SBML document given by filename.xml or located at the http://
 * URL. Output-format will always be xml
 * <p>
 * usage: java org.sbml.jsbml.validator.SBMLValidator [-h] [-d opt1[,opt2,...]]
 * filename.xml
 * <br>
 * usage: java validateSBML [-h] [-d opt1[,opt2,...]] http://...
 * 
 * 
 */
public class SBMLValidator {

  /**
   * 
   */
  static void usage() {
    String usage = "usage: java org.sbml.jsbml.validator.SBMLValidator [-h] [-d opt1[,opt2,...]] filename.xml\n"
        + "usage: java org.sbml.jsbml.validator.SBMLValidator [-h] [-d opt1[,opt2,...]] http://..."
        + "\n\n"
        + "  Validates the SBML document given by filename.xml or located at\n"
        + "  the http:// URL."
        + "\n\n"
        + "Options:\n\n"
        + "  -d opt1[,opt2,...]\n"
        + "    Disable the given consistency check options.\n"
        + "    The options are given as comma-separated characters.\n"
        + "    Each character is one of the followings:\n\n"
        + "      u : disable the units consistency check\n"
        + "      g : disable the overall SBML consistency check\n"
        + "      i : disable the identifier consistency check\n"
        + "      m : disable the MathML consistency check\n"
        + "      s : disable the SBO consistency check\n"
        + "      o : disable the overdetermined model check\n"
        + "      p : disable the modeling practice check\n\n"
        + "  -h  : Print this usage and exit.\n";

    System.out.println(usage);
    System.exit(1);
  }

  /**
   * 
   * @param args
   */
  public static void main(String[] args) {
    String filename = null;
    String output = "xml";
    String offcheck = "u";

    /**
     * 
     * Parse the command-line arguments.
     * 
     */
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("-h")) {
        usage();
      } else if (args[i].equals("-o")) {
        if ((i + 1) >= args.length) {
          usage();
        }

        Pattern p = Pattern.compile("(xml|xhtml|json|text)");
        Matcher m = p.matcher(args[i + 1]);
        if (!m.matches()) {
          usage();
        }

        output = args[++i];
      } else if (args[i].equals("-d")) {
        if ((i + 1) >= args.length) {
          usage();
        }
        Pattern p = Pattern.compile("[a-zA-Z](,[a-zA-Z])*");
        Matcher m = p.matcher(args[i + 1]);
        if (!m.matches()) {
          usage();
        }

        offcheck = args[++i];
      } else if (args[i].startsWith("-")) {
        // invalid option
        usage();
      } else {
        // currently only one filename (url) can be given.

        filename = args[i];

        if ((i + 1) < args.length) {
          // usage();
          break;
        }

      }
    }

    if (filename == null) {
      usage();
    }

    HashMap<String, String> parameters = new HashMap<String, String>();
    parameters.put("output", output);
    parameters.put("offcheck", offcheck);

    System.out.println("Validating  " + filename + '\n');

    SBMLErrorLog sbmlErrorLog = checkConsistency(filename, parameters);

    System.out.println("There is " + sbmlErrorLog.getErrorCount() + " errors in the model.\n");

    // printErrors
    for (int j = 0; j < sbmlErrorLog.getErrorCount(); j++) {
      SBMLError error = sbmlErrorLog.getError(j);

      System.out.println(error.toString() + '\n');
    }
  }

  /**
   * @param source
   * @param destination
   * @throws IOException
   */
  static void print(InputStream source, OutputStream destination)
      throws IOException {
    byte[] buffer = new byte[8192];
    int nbytes = 0;

    while ((nbytes = source.read(buffer, 0, buffer.length)) >= 0) {
      destination.write(buffer, 0, nbytes);
    }

    destination.flush();
    source.reset();
  }

  /**
   * @param source
   * @param destination
   * @throws IOException
   */
  static void print(Reader source, Writer destination)
      throws IOException
  {
    char[] buffer = new char[8192];
    int nbChar = 0;

    while ((nbChar = source.read(buffer, 0, buffer.length)) >= 0) {
      destination.write(buffer, 0, nbChar);
    }

    destination.flush();
  }


  /**
   * Validates an SBML model using the
   * SBML.org online validator (http://sbml.org/validator/).
   * 
   * <p>
   * You can control the consistency checks that are performed when
   * {@link #checkConsistency(String)} is called with the {@link HashMap} of
   * parameters given.
   * It will fill the {@link SBMLErrorLog}
   * with {@link SBMLError}s for each problem within this whole model.
   * 
   * <p>
   * If this method returns a non empty {@link SBMLErrorLog}, the failures may be
   * due to warnings @em or errors.  Callers should inspect the severity
   * flag in the individual SBMLError objects to determine the nature of the failures.
   * 
   * <p>This method is called from {@link SBMLDocument#checkConsistency()}. To know what
   * to set in the parameters map, you can have a look at {@link SBMLDocument#setConsistencyChecks(CHECK_CATEGORY, boolean)}.
   * But the key should be one of the category in {@link CHECK_CATEGORY} and the value should
   * be "true" or "false".
   * 
   * @param fileName a file name
   * @param parameters parameters for the libsbml checkConsistency()
   * @return an {@link SBMLErrorLog} containing the list of errors.
   * 
   * @see <a href="http://sbml.org/Facilities/Validator/Validator_Web_API">sbml.org Validator Web API</a>
   */
  public static SBMLErrorLog checkConsistency(String fileName, Map<String, String> parameters)
  {
    Logger logger = Logger.getLogger(SBMLValidator.class);

    try {
      Reader result = null;

      // We force the output to be xml
      String output = "xml";
      parameters.put("output", output);

      logger.debug("Calling the sbml.org Web Validator.");

      logger.debug("offcheck = @" + parameters.get("offcheck") + "@");

      // getting an XML output of the error log
      // describe there :
      // http://sbml.org/Facilities/Validator/Validator_Web_API
      result = new InputStreamReader(Validator.validateSBML(fileName, parameters));

      String resultString = new String();
      StringWriter out = new StringWriter();
      print(result, out);

      resultString = out.toString();

      String xmlValidationString = resultString;

      return SBMLValidator.checkConsistency(xmlValidationString);

    } catch (Exception e) {

    }

    return null;
  }


  /**
   * Parses the XML {@link String} returned by the libSBML online validator or web services.
   * 
   * <p>
   * It will fill the {@link SBMLErrorLog}
   * with {@link SBMLError}s for each problem within this whole model.
   * 
   * <p>
   * If this method returns a non empty {@link SBMLErrorLog}, the failures may be
   * due to warnings @em or errors.  Callers should inspect the severity
   * flag in the individual SBMLError objects to determine the nature of the failures.
   * 
   * @param xmlValidationString an XML {@link String} representing an SBML model.
   * @return an {@link SBMLErrorLog} containing the list of errors.
   * 
   * @see <a href="http://sbml.org/Facilities/Validator/Validator_Web_API">sbml.org Validator Web API</a>
   */
  public static SBMLErrorLog checkConsistency(String xmlValidationString)
  {
    Logger logger = Logger.getLogger(SBMLValidator.class);

    if (xmlValidationString == null || xmlValidationString.trim().length() == 0) {
      return new SBMLErrorLog();
    }

    StringReader reader = new StringReader(xmlValidationString);

    // DEBUG
    logger.debug(xmlValidationString);

    // Defining all the rules to parse the XML
    XStream xstream = new XStream(new DomDriver()); // To parse XML using DOM
    // XStream xstream = new XStream(new StaxDriver()); // To parse XML using Stax

    xstream.alias("validation-results", SBMLErrorLog.class);
    xstream.alias("option", Option.class);
    xstream.alias("detail", Detail.class);
    // xstream.registerLocalConverter(SBMLError.class, "message", new MessageConverter("message"));
    xstream.registerConverter(new SBMLErrorConverter()); // deal with the XML problem element and all it's content

    xstream.addImplicitCollection(SBMLErrorLog.class, "options",
      "option", Option.class);
    xstream.addImplicitCollection(SBMLErrorLog.class,
      "validationErrors", "problem", SBMLError.class);

    xstream.aliasField("error", SBMLErrorLog.class, "status");
    xstream.aliasField("warning", SBMLErrorLog.class, "status");
    xstream.aliasField("no-errors", SBMLErrorLog.class, "status");
    xstream.aliasField("file-not-readable", SBMLErrorLog.class, "status");
    xstream.aliasField("out-of-memory", SBMLErrorLog.class, "status");
    xstream.aliasField("segmentation-fault", SBMLErrorLog.class, "status");
    xstream.aliasField("internal-error", SBMLErrorLog.class, "status");

    xstream.useAttributeFor(File.class);

    xstream.useAttributeFor(Option.class, "name");
    xstream.useAttributeFor(Option.class, "status");

    xstream.useAttributeFor(Detail.class, "category");
    xstream.useAttributeFor(Detail.class, "severity");

    try {
      SBMLErrorLog sbmlErrorLog = (SBMLErrorLog) xstream.fromXML(reader);

      logger.debug("Call and Parsing of the results done!");

      // logger.debug("File = " + resultsObj.getFile().getName());

      // logger.debug("Nb Options = " + resultsObj.getOptions().size());
      // logger.debug(resultsObj.getOptions());

      logger.debug("Nb Problems = "	+ sbmlErrorLog.getValidationErrors().size());

      if (sbmlErrorLog.getValidationErrors().size() > 0) {
        try {
          logger.debug("ValidationError(0) = "	+ sbmlErrorLog.getValidationErrors().get(0));
        } catch (Exception e) {
          e.printStackTrace();
        }
      }

      return sbmlErrorLog;
    } catch (XStreamException e) {
      logger.error("There has been an error parsing the consistency check XML result.");

      logger.info("Below is the String returned by the sbml.org validator REST API:\n" + xmlValidationString + '\n');

      if (! xmlValidationString.contains("<validation-results>")) {
        logger.info("There is probably an issue with the sbml.org validator API, please contact the SBML team on [sbml-team at googlegroups.com].");
      }

      if (logger.isDebugEnabled()) {
        e.printStackTrace();
      }
    }

    return new SBMLErrorLog();
  }

  /**
   * Enumerates the different possible check categories
   * when performing the validation of an SBML document.
   * 
   */
  public static enum CHECK_CATEGORY
  {
    /**
     * Correctness and consistency of specific SBML language constructs.
     * Performing this set of checks is highly recommended.  With respect to
     * the SBML specification, these concern failures in applying the
     * validation rules numbered 2xxxx in the Level 2 or Level 3 specifications.
     */
    GENERAL_CONSISTENCY,

    /**
     * Correctness and consistency of identifiers used for model entities.
     * An example of inconsistency would be using a species identifier in a
     * reaction rate formula without first having declared the species.  With
     * respect to the SBML specification, these concern failures in applying
     * the validation rules numbered 103xx in the Level 2 or Level 3 specifications.
     */
    IDENTIFIER_CONSISTENCY,

    /**
     * Consistency and validity of SBO identifiers (if any) used in the
     * model.  With respect to the SBML specification, these concern failures
     * in applying the validation rules numbered 107xx in the Level 2 or
     * Level 3 specifications.
     */
    SBO_CONSISTENCY,

    /**
     * Syntax of MathML constructs.  With respect to the SBML specification,
     * these concern failures in applying the validation rules numbered 102xx
     * in the Level 2 or Level 3 specifications.
     */
    MATHML_CONSISTENCY,

    /**
     * Consistency of measurement units associated with quantities in a
     * model.  With respect to the SBML specification, these concern failures
     * in applying the validation rules numbered 105xx in the Level 2 or
     * Level 3 specifications.
     */
    UNITS_CONSISTENCY,

    /**
     * Static analysis of whether the system of equations implied by a model
     * is mathematically overdetermined.  With respect to the SBML
     * specification, this is validation rule #10601 in the SBML Level 2 or
     * Level 3 specifications.
     */
    OVERDETERMINED_MODEL,

    /**
     * Additional checks for recommended good modeling practice. (These are
     * tests performed by <a href="http://sbml.org/Software/libSBML">libSBML</a>
     *  and do not have equivalent SBML validation rules.)
     */
    MODELING_PRACTICE
  };

}
