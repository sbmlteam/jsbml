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

import java.util.regex.Pattern;

/**
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
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
		System.out.println(SIdL2);
		
		String testId = "Z_1_a_q3_c9";
		System.out.printf("%s\t%s\t%s\n", testId, SNameL1V1, Pattern.matches(
				SNameL1V1, testId));
		System.out.printf("%s\t%s\t%s\n", testId, SNameL1V2, Pattern.matches(
				SNameL1V2, testId));


		
	}

}
