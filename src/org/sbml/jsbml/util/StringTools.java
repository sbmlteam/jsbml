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
package org.sbml.jsbml.util;

import java.io.IOException;
import java.util.Properties;

import org.sbml.jsbml.resources.Resource;

/**
 * This class provides a collection of convenient methods for manipulating
 * Strings.
 * 
 * @author Andreas Dr&auml;ger
 * 
 * @opt attributes
 * @opt types
 * @opt visibility
 */
public class StringTools {

	/**
	 * New line separator of this operating system
	 */
	public static final String newLine = System.getProperty("line.separator");

	/**
	 * Takes the given StringBuffer as input and appends every further Object to
	 * it.
	 * 
	 * @param k
	 * @param things
	 * @return
	 */
	public static final StringBuffer append(StringBuffer k, Object... things) {
		for (Object t : things) {
			k.append(t);
		}
		return k;
	}

	/**
	 * 
	 * @param sb
	 * @param elems
	 */
	public static void append(StringBuilder sb, Object... elems) {
		for (Object e : elems) {
			sb.append(e);
		}
	}

	/**
	 * This method concatenates two or more object strings into a new
	 * StringBuffer.
	 * 
	 * @param buffers
	 * @return
	 */
	public static final StringBuffer concat(Object... buffers) {
		StringBuffer res = new StringBuffer();
		for (Object buffer : buffers)
			res.append(buffer.toString());
		return res;
	}

	/**
	 * 
	 * @param string
	 * @return
	 */
	public static final String encodeForHTML(String string) {
		final StringBuilder result = new StringBuilder();
		try {
			Properties p = Resource
					.readProperties("org/sbml/jsbml/resources/cfg/HTML_CharEncodingTable.txt");
			for (char character : string.toCharArray()) {
				if (p.containsKey(String.valueOf(character))) {
					result.append(p.get(String.valueOf(character)));
				} else {
					result.append(character);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			result.append(string);
		}
		return result.toString();
	}

	/**
	 * Returns a String whose first letter is now in lower case.
	 * 
	 * @param name
	 * @return
	 */
	public static final String firstLetterLowerCase(String name) {
		char c = name.charAt(0);
		if (Character.isLetter(c))
			c = Character.toLowerCase(c);
		if (name.length() > 1)
			name = Character.toString(c) + name.substring(1);
		else
			return Character.toString(c);
		return name;
	}

	/**
	 * Returns a String who's first letter is now in upper case.
	 * 
	 * @param name
	 * @return
	 */
	public static final String firstLetterUpperCase(String name) {
		char c = name.charAt(0);
		if (Character.isLetter(c))
			c = Character.toUpperCase(c);
		if (name.length() > 1)
			name = Character.toString(c) + name.substring(1);
		else
			return Character.toString(c);
		return name;
	}

	/**
	 * Returns the number as a word. Zero is converted to "no". Only positive
	 * numbers from 1 to twelve can be converted. All other numbers are just
	 * converted to a String containing the number.
	 * 
	 * @param number
	 * @return
	 */
	public static String getWordForNumber(long number) {
		if ((number < Integer.MIN_VALUE) || (Integer.MAX_VALUE < number))
			return Long.toString(number);
		switch ((int) number) {
		case 0:
			return "no";
		case 1:
			return "one";
		case 2:
			return "two";
		case 3:
			return "three";
		case 4:
			return "four";
		case 5:
			return "five";
		case 6:
			return "six";
		case 7:
			return "seven";
		case 8:
			return "eight";
		case 9:
			return "nine";
		case 10:
			return "ten";
		case 11:
			return "eleven";
		case 12:
			return "twelve";
		default:
			return Long.toString(number);
		}
	}

	/**
	 * 
	 * @return
	 */
	public static final String newLine() {
		return System.getProperty("line.separator");
	}

	/**
	 * Returns a String from the given value that does not contain a point zero
	 * at the end.
	 * 
	 * @param value
	 * @return
	 */
	public static final String toString(double value) {
		if (((int) value) - value == 0) {
			return Integer.toString((int) value);
		}
		return String.format("%.3f", Double.valueOf(value));
	}

}
