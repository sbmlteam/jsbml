/**
 * 
 */
package org.sbml.jsbml.test;

import org.sbml.jsbml.Reaction;
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
		ud.addUnit(new Unit(Unit.Kind.VOLT, 2, 2, 4));
		ud.addUnit(new Unit(Unit.Kind.SIEMENS, 4, 2, 4));
		ud.addUnit(new Unit(Unit.Kind.AMPERE, 1, 2, 4));
		ud.addUnit(new Unit(Unit.Kind.WEBER, 0, 2, 4));
		Unit u = new Unit(Unit.Kind.CELSIUS, 3, 2, 4);
		u.setMultiplier(5.3);
		// u.setOffset(-271.15);
		ud.addUnit(u);
		ud.addUnit(new Unit(Unit.Kind.NEWTON, 2, 2, 4));
		ud.addUnit(new Unit(Unit.Kind.GRAM, 0, 2, 4));
		System.out.println(UnitDefinition.printUnits(ud, true));
		System.out.println(UnitDefinition.printUnits(ud.simplify(), true));
		ud.convertToSIUnits();
		System.out.println(UnitDefinition.printUnits(ud,true));

		// AMPERE CELSIUS GRAM NEWTON SIEMENS VOLT WEBER
		// A °C^3 1 N^2 S^4 V^2 1
	}

}
