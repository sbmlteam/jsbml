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

package org.sbml.jsbml.test.gui;

import java.io.IOException;
import java.util.Properties;

import org.sbml.jsbml.resources.Resource;

/**
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 */
public class ParsingTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ParsingTest(args[0]);
	}

	public ParsingTest(String testCasesDir) {

		String sbmlfile, csvfile, configfile;
		if (!testCasesDir.endsWith("/")) {
			testCasesDir += "/";
		}

		// 919
		for (int modelnr = 1; modelnr <= 100; modelnr++) {
			try {
				StringBuilder modelFile = new StringBuilder();
				modelFile.append(modelnr);
				while (modelFile.length() < 5) {
					modelFile.insert(0, '0');
				}
				String path = modelFile.toString();
				modelFile.append('/');
				modelFile.append(path);
				modelFile.insert(0, testCasesDir);
				path = modelFile.toString();
				sbmlfile = path + "-sbml-l1v2.xml";
				csvfile = path + "-results.csv";
				configfile = path + "-settings.txt";

				Properties cfg = Resource.readProperties(configfile);
				double start = Double.parseDouble(cfg.get("start").toString());
				double end = start
						+ Double.parseDouble(cfg.get("duration").toString());
				double stepsize = (end - start)
						/ Double.parseDouble(cfg.get("steps").toString());

				new JTreeOfSBML(sbmlfile);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
