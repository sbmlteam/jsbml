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
package org.sbml.jsbml.test.gui;

import java.io.IOException;
import java.util.Properties;

import org.sbml.jsbml.resources.Resource;

/**
 * @author Andreas Dr&auml;ger
 * 
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
