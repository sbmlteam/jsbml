/*
 * $Id$
 * $URL$
 *
 *
 *==================================================================================
 * Copyright (c) 2009 the copyright is held jointly by the individual
 * authors. See the file AUTHORS for the list of authors.
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

package org.sbml.jsbml.validator;

/**
 * \file    validateSBML.java
 * \brief   Validates an SBML document using the SBML.org Online Validator
 * \author  Ben Bornstein <sbml-team@caltech.edu>
 * \author  Akiya Jouraku <sbml-team@caltech.edu>
 *
 * $Id$
 * $Source$
 *
 * Copyright 2006 California Institute of Technology and
 * Japan Science and Technology Corporation.
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation; either version 2.1 of the License, or
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY, WITHOUT EVEN THE IMPLIED WARRANTY OF
 * MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.  The software and
 * documentation provided hereunder is on an "as is" basis, and the
 * California Institute of Technology and Japan Science and Technology
 * Corporation have no obligations to provide maintenance, support,
 * updates, enhancements or modifications.  In no event shall the
 * California Institute of Technology or the Japan Science and Technology
 * Corporation be liable to any party for direct, indirect, special,
 * incidental or consequential damages, including lost profits, arising
 * out of the use of this software and its documentation, even if the
 * California Institute of Technology and/or Japan Science and Technology
 * Corporation have been advised of the possibility of such damage.  See
 * the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sbml.jsbml.SBMLError;
import org.sbml.jsbml.SBMLErrorLog;
import org.sbml.jsbml.util.Location;
import org.sbml.jsbml.util.Option;
import org.sbml.jsbml.xml.xstream.converter.MessageConverter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Validator is simply a container for the static method validateSBML(filename,
 * parameters).
 */
class Validator {
	public static String validatorURL = "http://sbml.org/validator/";

	/**
	 * Validates the given SBML filename (or http:// URL) by calling the
	 * SBML.org online validator. The results are returned as an InputStream
	 * whose format may be controlled by setting parameters.put("output", ...)
	 * to one of: "xml", "xhtml", "json", "text" (default: xml).
	 * 
	 * @return an InputStream containing the validation results.
	 */
	public static InputStream validateSBML(String filename,
			Map<String, String> parameters) throws IOException {
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
	public MultipartPost(String url) throws IOException {
		Random random = new Random();

		connection = (new URL(url)).openConnection();
		boundary = "<<" + Long.toString(random.nextLong(), 30);
		String type = "multipart/form-data; boundary=" + boundary;

		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", type);

		stream = connection.getOutputStream();
	}

	public InputStream done() throws IOException {
		writeln("--" + boundary + "--");
		writeln();

		stream.close();

		return connection.getInputStream();
	}

	public void writeParameter(String name, String value) throws IOException {
		writeln("--" + boundary);
		writeln("Content-Disposition: form-data; name=\"" + name + "\"");
		writeln();
		writeln(value);
	}

	public void writeParameter(String name, File file) throws IOException {
		String prefix = "Content-Disposition: form-data; name=\"file\"; filename=";

		writeln("--" + boundary);
		writeln(prefix + '"' + file.getName() + '"');
		writeln("Content-Type: text/xml");
		writeln();

		InputStream source = new FileInputStream(file);
		copy(source, stream);

		stream.flush();
		source.close();
	}

	void copy(InputStream source, OutputStream destination) throws IOException {
		byte[] buffer = new byte[8192];
		int nbytes = 0;

		while ((nbytes = source.read(buffer, 0, buffer.length)) >= 0) {
			destination.write(buffer, 0, nbytes);
		}
	}

	void writeln(String s) throws IOException {
		write(s);
		writeln();
	}

	void writeln() throws IOException {
		write('\r');
		write('\n');
	}

	void write(char c) throws IOException {
		stream.write(c);
	}

	void write(String s) throws IOException {
		stream.write(s.getBytes());
	}

	URLConnection connection;
	OutputStream stream;
	String boundary;
}

/**
 * usage: java validateSBML [-h] [-o output-format] [-d opt1[,opt2,...]]
 * filename.xml usage: java validateSBML [-h] [-o output-format] [-d
 * opt1[,opt2,...]] http://...
 * 
 * Validates the SBML document given by filename.xml or located at the http://
 * URL. Output-format is optional and may be one of: xml, xhtml, json, text
 * (default: xml)
 */
public class SBMLValidator {
	static void usage() {
		String usage = "usage: java validateSBML [-h] [-o output-format] [-d opt1[,opt2,...]] filename.xml\n"
				+ "usage: java validateSBML [-h] [-o output-format] [-d opt1[,opt2,...]] http://..."
				+ "\n\n"
				+ "  Validates the SBML document given by filename.xml or located at\n"
				+ "  the http:// URL."
				+ "\n\n"
				+ "Options:\n\n"
				+ "  -o output-format\n"
				+ "    Specify an output format.\n\n"
				+ "      xml   : XML (Default)\n"
				+ "      xhtml : XHTML\n"
				+ "      text  : plain text\n"
				+ "      json  : JavaScript Object Notation\n\n"
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
		String offcheck = null;

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
				if ((i + 1) < args.length) {
					usage();
				}
				filename = args[i];
			}
		}

		if (filename == null) {
			usage();
		}

		HashMap<String, String> parameters = new HashMap<String, String>();

		checkConsistency(filename, parameters);
	}

	static void print(InputStream source, OutputStream destination)
			throws IOException {
		byte[] buffer = new byte[8192];
		int nbytes = 0;

		while ((nbytes = source.read(buffer, 0, buffer.length)) >= 0) {
			destination.write(buffer, 0, nbytes);
		}

		destination.flush();
	}

	public static SBMLErrorLog checkConsistency(String fileName,
			HashMap<String, String> parameters) {

		try {
			InputStream result = null;

			// DEBUG
			String output = "xml";

			parameters.put("output", output);

			// getting an XML output of the error log
			// describe there :
			// http://sbml.org/Facilities/Validator/Validator_Web_API
			result = Validator.validateSBML(fileName, parameters);

			// print(result, System.out);

			XStream xstream = new XStream(new DomDriver()); // To parse XML
															// using DOM
			// XStream xstream = new XStream(new StaxDriver()); // To parse XML
			// using Stax
			xstream.alias("validation-results", SBMLErrorLog.class);
			xstream.alias("option", Option.class);
			xstream.alias("problem", SBMLError.class);
			xstream.alias("location", Location.class);
			xstream.registerConverter(new MessageConverter());

			// xstream.alias("message", Message.class);
			// TODO : make a custom Converter to read correctly the message with
			// it's lang attribute.

			xstream.addImplicitCollection(SBMLErrorLog.class, "options",
					"option", Option.class);
			xstream.addImplicitCollection(SBMLErrorLog.class,
					"validationErrors", "problem", SBMLError.class);

			xstream.aliasField("error", SBMLErrorLog.class, "status");
			xstream.aliasField("warning", SBMLErrorLog.class, "status");
			xstream.aliasField("no-errors", SBMLErrorLog.class, "status");

			xstream.useAttributeFor(File.class);

			xstream.useAttributeFor(Option.class, "name");
			xstream.useAttributeFor(Option.class, "status");

			xstream.useAttributeFor(SBMLError.class, "category");
			xstream.useAttributeFor(SBMLError.class, "code");
			xstream.useAttributeFor(SBMLError.class, "severity");

			xstream.useAttributeFor(Location.class, "line");
			xstream.useAttributeFor(Location.class, "column");

			// xstream.useAttributeFor(ValidationError.class, "messageLang");
			// xstream.aliasField("lang", ValidationError.class, "messageLang");

			// xstream.aliasField("message", SBMLError.class, "message");

			SBMLErrorLog resultsObj = (SBMLErrorLog) xstream.fromXML(result);

			System.out.println("Parsing done !!!");

			System.out.println("File = " + resultsObj.getFile().getName());

			System.out
					.println("Nb Options = " + resultsObj.getOptions().size());
			System.out.println(resultsObj.getOptions());

			System.out.println("Nb Problems = "
					+ resultsObj.getValidationErrors().size());
			System.out.println("ValidationError(2) =\n"
					+ resultsObj.getValidationErrors().get(2));

			// print(result, System.out);
			result.close();

			return resultsObj;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}
