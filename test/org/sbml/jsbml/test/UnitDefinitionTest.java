/**
 * 
 */
package org.sbml.jsbml.test;

import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;

/**
 * @author draeger
 *
 */
public class UnitDefinitionTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UnitDefinition ud = new UnitDefinition(2, 4);
		ud.addUnit(new Unit(Unit.Kind.VOLT,2, 2, 4));
		ud.addUnit(new Unit(Unit.Kind.SIEMENS, 4, 2, 4));
		ud.addUnit(new Unit(Unit.Kind.AMPERE, 1, 2, 4));
		ud.addUnit(new Unit(Unit.Kind.WEBER, 0, 2, 4));
		ud.addUnit(new Unit(Unit.Kind.CELSIUS, 3, 2, 4));
		ud.addUnit(new Unit(Unit.Kind.NEWTON, 2, 2, 4));
		ud.addUnit(new Unit(Unit.Kind.GRAM, 0, 2, 4));
		System.out.println(UnitDefinition.printUnits(ud.simplify(), true));
	}

}
