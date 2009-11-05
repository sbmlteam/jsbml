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

/**
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * 
 */
public class StringTools {

	/**
	 * 
	 */
	public static final Character underscore = Character.valueOf('_');
	/**
	 * The file separator of the operating system.
	 */
	public static final String fileSeparator = System
			.getProperty("file.separator");

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
		for (Object t : things)
			k.append(t);
		return k;
	}

	/**
	 * This method concatenates two or more object strings into a new
	 * stringbuffer.
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
	 * This method introduces left and right quotation marks where we normally
	 * have straight quotation marks.
	 * 
	 * @param text
	 * @param leftQuotationMark
	 * @param rightQuotationMark
	 * @return
	 */
	public static String correctQuotationMarks(String text,
			String leftQuotationMark, String rightQuotationMark) {
		boolean opening = true;
		for (int i = 0; i < text.length(); i++)
			if (text.charAt(i) == '"')
				if (opening) {
					text = text.substring(0, i - 1) + leftQuotationMark
							+ text.substring(i + 1);
					opening = false;
				} else {
					text = text.substring(0, i - 1) + rightQuotationMark
							+ text.substring(i + 1);
					opening = true;
				}
		return text;
	}

	/**
	 * Retunrs a String who's first letter is now in lower case.
	 * 
	 * @param name
	 * @return
	 */
	public static String firstLetterLowerCase(String name) {
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
	public static String firstLetterUpperCase(String name) {
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
	 * Returns the name of a given month.
	 * 
	 * @param month
	 * @return
	 */
	public static String getMonthName(short month) {
		switch (month) {
		case 1:
			return "January";
		case 2:
			return "February";
		case 3:
			return "March";
		case 4:
			return "April";
		case 5:
			return "May";
		case 6:
			return "June";
		case 7:
			return "July";
		case 8:
			return "August";
		case 9:
			return "September";
		case 10:
			return "October";
		case 11:
			return "November";
		case 12:
			return "December";
		default:
			return "invalid month " + month;
		}
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
	 * @param c
	 * @return True if the given character is a vocal and false if it is a
	 *         consonant.
	 */
	public static boolean isVocal(char c) {
		c = Character.toLowerCase(c);
		return (c == 'a') || (c == 'e') || (c == 'i') || (c == 'o')
				|| (c == 'u');
	}

	/**
	 * Returns a String from the given value that does not contain a point zero
	 * at the end.
	 * 
	 * @param value
	 * @return
	 */
	public static String toString(double value) {
		if (((int) value) - value == 0)
			return Integer.toString((int) value);
		return Double.toString(value);
	}

}
