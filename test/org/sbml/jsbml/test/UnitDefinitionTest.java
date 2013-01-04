/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
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

import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;

/**
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 */
public class UnitDefinitionTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int level = 2, version = 1;
		UnitDefinition ud = new UnitDefinition(level, version), ud2;
		ud.addUnit(new Unit(Unit.Kind.VOLT, 2, level, version));
		ud.addUnit(new Unit(Unit.Kind.SIEMENS, 4, level, version));
		ud.addUnit(new Unit(Unit.Kind.AMPERE, 1, level, version));
		ud.addUnit(new Unit(Unit.Kind.WEBER, 0, level, version));
		Unit u = new Unit(Unit.Kind.WATT, 3, level, version);
		u.setMultiplier(5.3);
		// u.setOffset(-271.15);
		ud.addUnit(u);
		ud.addUnit(new Unit(Unit.Kind.NEWTON, 2, level, version));
		ud.addUnit(new Unit(Unit.Kind.GRAM, 0, level, version));
		System.out.println(UnitDefinition.printUnits(ud, true));
		System.out.println(UnitDefinition.printUnits(ud.simplify(), true));
		ud.convertToSIUnits();
		System.out.println(UnitDefinition.printUnits(ud,true));

		// AMPERE CELSIUS GRAM NEWTON SIEMENS VOLT WEBER
		// A °C^3 1 N^2 S^4 V^2 1
		
		ud = new UnitDefinition(level, version);
		ud.addUnit(new Unit(-3, Unit.Kind.JOULE, level, version));
		ud.addUnit(new Unit(-3, Unit.Kind.MOLE, level, version));

		ud2 = new UnitDefinition(level, version);
		ud2.addUnit(new Unit(Unit.Kind.JOULE, 5, level, version));
		ud2.addUnit(new Unit(Unit.Kind.MOLE, 5, level, version));

		System.out.printf("\n%s / %s\t=\t", UnitDefinition.printUnits(ud, true), UnitDefinition.printUnits(ud2, true));
		ud.divideBy(ud2);
		ud.simplify();
		System.out.println(UnitDefinition.printUnits(ud, true));

		ud = new UnitDefinition(level, version);
		ud.addUnit(new Unit(-3, Unit.Kind.MOLE, 2, level, version));
		ud.addUnit(new Unit(-3, Unit.Kind.LITRE, -2, level, version));
		ud2 = new UnitDefinition(level, version);
		ud2.addUnit(new Unit(-3, Unit.Kind.MOLE, level, version));
		ud2.addUnit(new Unit(-3, Unit.Kind.LITRE, -1, level, version));

		System.out.printf("\n%s * %s\t=\t", UnitDefinition.printUnits(ud, true), UnitDefinition.printUnits(ud2, true));

		ud.multiplyWith(ud2);
		ud.simplify();
		System.out.println(UnitDefinition.printUnits(ud, true));

		ud = new UnitDefinition(level, version);
		ud.addUnit(new Unit(18, Unit.Kind.JOULE, 6, level, version));
		ud2 = new UnitDefinition(level, version);
		ud2.addUnit(new Unit(-3, Unit.Kind.MOLE, -6, level, version));

		System.out.printf("\n%s / %s\t=\t", UnitDefinition.printUnits(ud, true), UnitDefinition.printUnits(ud2, true));
		ud.divideBy(ud2);
		ud.simplify();
		System.out.println(UnitDefinition.printUnits(ud, true));

		ud = new UnitDefinition(level, version);
		ud.addUnit(new Unit(18, Unit.Kind.JOULE, 6, level, version));
		ud.addUnit(new Unit(-3, Unit.Kind.MOLE, -6, level, version));
		ud2 = new UnitDefinition(level, version);
		ud2.addUnit(new Unit(Unit.Kind.JOULE, level, version));
		ud2.addUnit(new Unit(Unit.Kind.MOLE, -1, level, version));

		System.out.printf("\n%s / %s\t=\t", UnitDefinition.printUnits(ud, true), UnitDefinition.printUnits(ud2, true));
		ud.divideBy(ud2);
		ud.simplify();
		System.out.println(UnitDefinition.printUnits(ud, true));

		ud = new UnitDefinition(level, version);
		ud.addUnit(new Unit(-3, Unit.Kind.MOLE, 1d, level, version));
		ud.addUnit(new Unit(Unit.Kind.LITRE, -1d, level, version));
		ud2 = new UnitDefinition(level, version);
		ud2.addUnit(new Unit(-3, Unit.Kind.MOLE, 1d, level, version));
		ud2.addUnit(new Unit(Unit.Kind.LITRE, -1d, level, version));
		ud.divideBy(ud2);
		System.out.println(UnitDefinition.printUnits(ud, true));
		ud.simplify();
		System.out.println(UnitDefinition.printUnits(ud, true));
	}

}
