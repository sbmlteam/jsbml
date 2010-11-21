/*
 * $Id: JSBML.java 181 2010-04-20 07:28:21Z andreas-draeger $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/JSBML.java $
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
package org.sbml.jsbml;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;

import org.sbml.jsbml.resources.Resource;

/**
 * Wrapper class for global methods and constants defined by JSBML.
 * 
 * @author Andreas Dr&auml;ger
 * @author rodrigue
 * 
 */
public class JSBML {

	/**
	 * The current version number of JSBML.
	 */
	private static final String jsbmlVersion = "0.8.10";

	public static final int OPERATION_SUCCESS = 0;
	public static final int INDEX_EXCEEDS_SIZE = -1;
	public static final int UNEXPECTED_ATTRIBUTE = -2;
	public static final int OPERATION_FAILED = -3;
	public static final int INVALID_ATTRIBUTE_VALUE = -4;
	public static final int INVALID_OBJECT = -5;
	public static final int DUPLICATE_OBJECT_ID = -6;
	public static final int LEVEL_MISMATCH = -7;
	public static final int VERSION_MISMATCH = -8;
	public static final int INVALID_XML_OPERATION = -9;

	/**
	 * This indicates that the {@link Model} has not been set properly or that
	 * an element tries to access its containing model but this is not possible.
	 */
	public static final String UNDEFINED_MODEL_MSG = "Cannot access containing model.";
	/**
	 * This message indicates that a problem occurred but the current class
	 * cannot give any more precise information about the reasons.
	 */
	public static final String UNDEFINED_PARSE_ERROR_MSG = "An error occur while creating a parser: %s.";

	/**
	 * Returns the JSBML version as a string of the form '1.2.3'.
	 * 
	 * @return the JSBML version as a string of the form '1.2.3'.
	 */
	public static String getJSBMLDottedVersion() {
		return jsbmlVersion;
	}

	/**
	 * Returns the JSBML version as an integer: version 1.2.3 becomes 10203.
	 * 
	 * @return the JSBML version as an integer: version 1.2.3 becomes 10203.
	 */
	public static int getJSBMLVersion() {
		return Integer.parseInt(getJSBMLVersionString());
	}

	/**
	 * Returns the JSBML version as a string: version 1.2.3 becomes '10203'.
	 * 
	 * @return the JSBML version as a string: version 1.2.3 becomes '10203'.
	 */
	public static String getJSBMLVersionString() {
		StringBuilder number = new StringBuilder();
		for (String num : getJSBMLDottedVersion().split(".")) {
			number.append(num);
		}
		return number.toString();
	}

	/**
	 * Loads {@link Properties} from a configuration file with the given path
	 * assuming that all values represent class names.
	 * @param <T>
	 * @param path
	 * @param whereToPutProperties
	 */
	@SuppressWarnings("unchecked")
	public static <T> void loadClasses(String path,
			Map<String, Class<? extends T>> whereToPutProperties) {
		Properties p = new Properties();
		try {
			p.loadFromXML(Resource.getInstance().getStreamFromResourceLocation(
					path));
			for (Map.Entry<Object, Object> entry : p.entrySet()) {
				whereToPutProperties.put(entry.getKey().toString(),
						(Class<T>) Class.forName(entry.getValue().toString()));
			}
		} catch (InvalidPropertiesFormatException e) {
			throw new IllegalArgumentException(String.format(
					"The format of the file %s is incorrect.", path));
		} catch (IOException e) {
			throw new IllegalArgumentException(String.format(
					"There was a problem opening the file %s.", path));
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(
					String.format(
									"There was a problem loading the file %s: %s. Please make sure the resources directory is included in the Java class path.",
									path, e.getMessage()));
		}
	}
}
