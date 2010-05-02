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
		if (((int) value) - value == 0)
			return Integer.toString((int) value);
		return String.format("%.3f", value);
	}
	
	public static final String encodeForHTML(String string){
	  final StringBuilder result = new StringBuilder();
	  for (char character : string.toCharArray()) {
	    if      (character == '\t')result.append("&#009;");
	    else if (character == '!') result.append("&#033;");
	    else if (character == '#') result.append("&#035;");
	    else if (character == '$') result.append("&#036;");
	    else if (character == '%') result.append("&#037;");
	    else if (character == '\'')result.append("&#039;");
	    else if (character == '(') result.append("&#040;");
	    else if (character == ')') result.append("&#041;");
	    else if (character == '*') result.append("&#042;");
	    else if (character == '+') result.append("&#043;");
	    else if (character == ',') result.append("&#044;");
	    else if (character == '-') result.append("&#045;");
	    else if (character == '.') result.append("&#046;");
	    else if (character == '/') result.append("&#047;");
	    else if (character == ':') result.append("&#058;");
	    else if (character == ';') result.append("&#059;");
	    else if (character == '=') result.append("&#061;");
	    else if (character == '?') result.append("&#063;");
	    else if (character == '@') result.append("&#064;");
	    else if (character == '[') result.append("&#091;");
	    else if (character == '\\')result.append("&#092;");
	    else if (character == ']') result.append("&#093;");
	    else if (character == '^') result.append("&#094;");
	    else if (character == '_') result.append("&#095;");
	    else if (character == '`') result.append("&#096;");
	    else if (character == '{') result.append("&#123;");
	    else if (character == '|') result.append("&#124;");
	    else if (character == '}') result.append("&#125;");
	    else if (character == '~') result.append("&#126;");
	    else if (character == '<') result.append("&lt;"); 
	    else if (character == '>') result.append("&gt;"); 
	    else if (character == '&') result.append("&amp;"); 
	    else if (character == '"') result.append("&quot;"); 
	    else if (character == '\n')result.append("<br/>");          // Handle Newline
	    
	    else result.append(character); // simple char, which must not be escaped.
	  }
	  return result.toString();
	}

 

}
