/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2011 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.test;

import java.io.File;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;

/**
 * 
 * @author Nicolas Rodriguez 
 * @since 0.8
 * @version $Rev$
 */
public class ValidateSBML {

	public static void main (String[] args) throws XMLStreamException, IOException
	{
		if (args.length < 1)
		{
			System.out.println("Usage: java validateSBML filename");
			System.exit(1);
		}

		String filename       = args[0];
		SBMLReader reader     = new SBMLReader();
		SBMLDocument document;
		long start, stop;

		start    = System.currentTimeMillis();
		document = reader.readSBML(filename);
		stop     = System.currentTimeMillis();

		if (document.getNumErrors() > 0)
		{
			print("Encountered the following errors while reading the SBML file:\n");
			document.printErrors();
			print("\nFurther consistency checking and validation aborted.\n");
			System.exit(1);
		}
		else
		{
			long errors = document.checkConsistency();
			long size   = new File(filename).length();

			println("            filename: " + filename);
			println("           file size: " + size);
			println("      read time (ms): " + (stop - start));
			println(" validation error(s): " + errors);

			if (errors > 0)
			{
				document.printErrors();
				System.exit(1);
			}
		}
	}


	static void print (String msg)
	{
		System.out.print(msg);
	}

	static void println (String msg)
	{
		System.out.println(msg);
	}

}
