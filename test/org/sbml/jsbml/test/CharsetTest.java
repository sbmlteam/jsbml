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
package org.sbml.jsbml.test;

import java.util.regex.Pattern;

/**
 * @author Andreas Dr&auml;ger
 * 
 */
public class CharsetTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String underscore = "_";
		String letter = "a-zA-Z";
		String digit = "0-9";
		String idChar = letter + digit + underscore;

		/*
		 * Level 1
		 */
		String SNameL1V1 = underscore + "*[" + letter + "][" + idChar + "]*";
		String SNameL1V2 = "[" + letter + underscore + "][" + idChar + "]*";
		/*
		 * Level 2 and beyond
		 */
		String SIdL2 = "[" + letter + underscore + "][" + idChar + "]*";
		
		String testId = "Z_1_a_q3_c9";
		System.out.printf("%s\t%s\t%s\n", testId, SNameL1V1, Pattern.matches(
				SNameL1V1, testId));
		System.out.printf("%s\t%s\t%s\n", testId, SNameL1V2, Pattern.matches(
				SNameL1V2, testId));


		
	}

}
