/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2012 jointly by the following organizations:
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

package org.sbml.jsbml.util;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.resources.Resource;

/**
 * This class provides a collection of convenient methods for manipulating
 * Strings.
 * 
 * @author Andreas Dr&auml;ger
 * @author Nicolas Rodriguez
 * @author Clemens Wrzodek
 * @since 0.8
 * @version $Rev$
 */
public class StringTools {

	/**
	 * 
	 */
	public static final String DECIMAL_FORMAT = "#.###########################################";
	/**
	 * New line separator of this operating system
	 */
	private static final String newLine = "\n";
	/**
	 * 
	 */
	public static final String REAL_FORMAT = "#.###############################################";
	/**
	 * 
	 */
	public static final String SCIENTIFIC_FORMAT = "#.###########################################E0";
	
	/**
	 * The {@link Character} <code>'_'</code> as a {@link String}.
	 */
	public static final String underscore = Character.valueOf('_').toString();

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
		for (Object buffer : buffers) {
			if (buffer != null) {
				res.append(buffer.toString());
			}
		}
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
		if (Character.isLetter(c)) {
			c = Character.toUpperCase(c);
		}
		if (name.length() > 1) {
			name = Character.toString(c) + name.substring(1);
		} else {
			return Character.toString(c);
		}
		return name;
	}

	/**
	 * Returns the number as an English word. Zero is converted to "no". Only
	 * positive numbers from 1 to twelve can be converted. All other numbers are
	 * just converted to a String containing the number.
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
   * This method creates a {@link String} representation of the given number and
   * inserts as many zero characters as the prefix of this {@link String} as
   * needed
   * to result in a {@link String} of the given length.
   * 
   * @param length
   *        the total desired length of the given number {@link String}.
   * @param number
   * @return a {@link String} of the given length consisting of a suffix defined
   *         by the given number and as many leading zeros as necessary to reach
   *         the desired length.
   */
  public static String leadingZeros(int length, int number) {
    return fill(length, '0', Integer.toString(number));
  }
  
  /**
   * 
   * @param length the desired length of the resulting {@link String}
   * @param symbol the symbol to be inserted at the beginning of the initial {@link String} multiple times.
   * @param initialString can be null or empty.
   * @return
   */
  public static String fill(int length, char symbol, String initialString) {
    StringBuilder sb = new StringBuilder();
    if (initialString != null) {
      if (initialString.length() > length) {
        throw new IllegalArgumentException(String.format(
          "Initial String %s is already longer than %d digits.", initialString,
          length));
      }
      sb.append(initialString);
    }
    return fill(length, symbol, sb);
  }
  
  /**
   * 
   * @param length
   * @param symbol
   * @return
   */
  public static String fill(int length, char symbol) {
    return fill(length, symbol, new StringBuilder());
  }

	/**
	 * Ensures a minimum size of <code>length</code> for the given
	 * <code>stringBuilder</code>. Therefore, the given
	 * <code>symbol</code> will be put at the beginning of the
	 * string, until it reaches the given <code>length</code>.
   * @param length
   * @param symbol
   * @param stringBuilder
   * @return
   */
  private static String fill(int length, char symbol, StringBuilder sb) {
    if (length<=sb.length()) {
      return sb.toString();
    }
    
    // Create a char array of given length with native methods
    char[] ret = new char[length];
    Arrays.fill(ret, symbol);
    
    // Copy previous content at the end of the array
    if(sb.length()>0) {
      char[] sbArray = sb.toString().toCharArray();
      System.arraycopy(sbArray, 0, ret, length-sb.length(), sbArray.length);
    }
    
    return new String(ret);
  }


  /**
	 * 
	 * @return
	 */
	public static final String newLine() {
		return newLine;
	}

	/**
	 * Parses a String into a boolean following the rules of the SBML
	 * specifications, section 3.1.2.
	 * 
	 * @param valueAsStr
	 *            a boolean as a String
	 * @return the String as a boolean. If the String is not a valid boolean,
	 *         false is returned.
	 */
	public static boolean parseSBMLBoolean(String valueAsStr) {

		String toTest = valueAsStr.trim();

		// Test for true/false ignoring case.
		boolean value = Boolean.parseBoolean(toTest);

		if (toTest.equals("0")) {
			value = false; // this test would not be needed as the value is
			// already false but it is there for completion.
		} else if (toTest.equals("1")) {
			value = true;
		} else if (!(toTest.equalsIgnoreCase("true") 
				|| toTest.equalsIgnoreCase("false"))) 
		{
			Logger logger = Logger.getLogger(StringTools.class);
			logger.warn("Could not create a boolean from the string " + valueAsStr);
		}

		return value;
	}
	
	/**
	 * Parses a String into a double number following the rules of the SBML
	 * specifications, section 3.1.5.
	 * 
	 * @param valueAsStr
	 *            a double as a String
	 * @return the String as a double. If the String is not a valid double
	 *         number, {@link Double.NaN} is returned.
	 */
	public static double parseSBMLDouble(String valueAsStr) {

		double value = Double.NaN;
		String toTest = valueAsStr.trim();

		try {
			value = Double.parseDouble(toTest);
		} catch (NumberFormatException e) {
			if (toTest.equalsIgnoreCase("INF")) {
				value = Double.POSITIVE_INFINITY;
			} else if (toTest.equalsIgnoreCase("-INF")) {
				value = Double.NEGATIVE_INFINITY;
			} else {
				Logger logger = Logger.getLogger(StringTools.class);
				logger.warn("Could not create a double from the string " + valueAsStr);
			}
		}

		return value;
	}

	/**
	 * Parses a {@link String} into an int number following the rules of the SBML
	 * specifications, section 3.1.3.
	 * 
	 * @param valueAsStr
	 *            an int as a {@link String}
	 * @return the {@link String} as an int. If the {@link String} is not a valid int number, 0
	 *         is returned.
	 */
	public static int parseSBMLInt(String valueAsStr) {
		int value = 0;
		try {
			value = Integer.parseInt(valueAsStr.trim());
		} catch (NumberFormatException e) {
			Logger logger = Logger.getLogger(StringTools.class);
			logger.warn("Could not create an integer from the string " + valueAsStr);
		}
		return value;
	}

	/**
	 * Parses a {@link String} into an short number following the rules of the
	 * SBML specifications, section 3.1.3.
	 * 
	 * @param value
	 *            an int as a String
	 * @return the {@link String} as an short. If the {@link String} is not a
	 *         valid short number, 0 is returned.
	 */
	public static int parseSBMLShort(String value) {
		short v = 0;
		try {
			v = Short.parseShort(value.trim());
		} catch (NumberFormatException e) {
			Logger logger = Logger.getLogger(StringTools.class);
			logger.warn("Could not create a short from the string " + value);
		}
		return v;
	}
	
	/**
	 * Returns a HTML formated String, in which each line is at most lineBreak
	 * symbols long.
	 * 
	 * @param string
	 * @return
	 */
	public static String toHTML(String string) {
		return toHTML(string, Integer.MAX_VALUE);
	}

	/**
	 * Returns a HTML formated String, in which each line is at most lineBreak
	 * symbols long.
	 * 
	 * @param string
	 * @param lineBreak
	 * @return
	 */
	public static String toHTML(String string, int lineBreak) {
		StringTokenizer st = new StringTokenizer(string != null ? string : "",
				" ");
		StringBuilder sb = new StringBuilder();
		if (st.hasMoreElements())
			sb.append(st.nextElement().toString());
		int length = sb.length();
		sb.insert(0, "<html><body>");
		while (st.hasMoreElements()) {
			if (length >= lineBreak && lineBreak < Integer.MAX_VALUE) {
				sb.append("<br/>");
				length = 0;
			} else
				sb.append(' ');
			String tmp = st.nextElement().toString();
			length += tmp.length() + 1;
			sb.append(tmp);
		}
		sb.append("</body></html>");
		return sb.toString();
	}

	/**
	 * Returns a {@link String} from the given value that does not contain a
	 * point zero at the end if the given value represents an integer number.
	 * The returned {@link String} displays the number in a {@link Locale}
	 * -dependent way, i.e., the decimal separator and the symbols to represent
	 * the digits are chosen from the system's configuration. Furthermore, a
	 * scientific style including 'E' will be used if the value is smaller than
	 * 1E-5 or greater than 1E5.
	 * 
	 * @param value
	 * @return
	 */
	public static final String toString(double value) {
		return toString(Locale.getDefault(), value);
	}

	/**
	 * Allows for {@link Locale}-dependent number formatting.
	 * @param locale
	 * @param value
	 * @return
	 */
	public static final String toString(Locale locale, double value) {
		if (Double.isNaN(value)) {
			return "NaN";
		} else if (Double.isInfinite(value)) {
			// TODO: make this locale dependent ?
		  String infinity = "INF";
			return value < 0 ? '-' + infinity : infinity;
		}
		
		if (((int) value) - value == 0) {
			return String.format("%d", Integer.valueOf((int) value));
		}

		if ((Math.abs(value) < 1E-4) || (1E4 < Math.abs(value))) {
			DecimalFormat df = new DecimalFormat(SCIENTIFIC_FORMAT,
					new DecimalFormatSymbols(locale));
			return df.format(value);
		}
		
		DecimalFormat df = new DecimalFormat(DECIMAL_FORMAT,
				new DecimalFormatSymbols(locale));
		return df.format(value);
	}

  /**
	 * Checks whether a given {@link String} fits into the definition of the XML
	 * notes {@link String} in SBML. If not, this method will surround the given
	 * {@link String} with the minimal definition of a valid notes
	 * {@link String}.
	 * 
	 * @param notes
	 *            the {@link String} to be checked and possibly modified.
	 * @return A {@link String} that will be surrounded by the XML definition of
	 *         a notes {@link String} in SBML, i.e.,
	 * 
	 *         <pre>
	 * &lt;notes&gt;
	 *   &lt;body xmlns="http://www.w3.org/1999/xhtml"&gt;
	 *     &lt;p&gt;the original notes&lt;/p&gt;
	 *   &lt;/body&gt;
	 * </pre>
	 * 
	 *         If the given argument already suffices the definition of XML
	 *         {@link String}s in SBML, nothing will be changed.
	 */
	public static String toXMLNotesString(String notes) {
		// TODO : We need to perform plenty of check to see of which form are the notes given to this method
		// and perform the necessary conversion to append or set the notes correctly.
				
		if (!notes.trim().startsWith("<")) { // we assume that this is free text
			StringBuilder sb = new StringBuilder();
			sb.append("<notes>\n");
			sb.append("  <body xmlns=\"");
			sb.append(JSBML.URI_XHTML_DEFINITION);
			sb.append("\">\n ");
			sb.append("    <p>");
			sb.append(notes);
			sb.append("</p>\n");
			sb.append("  </body>\n");
			sb.append("</notes>");
			return sb.toString();
		} else if (!notes.trim().startsWith("<notes")) { 
			// we assume the <notes> XML tag is missing
			StringBuilder sb = new StringBuilder();
			sb.append("<notes>\n  ");
			sb.append(notes);
			sb.append("\n</notes>");
			return sb.toString();
		} 
		return notes;
	}

}
