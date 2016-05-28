package org.sbml.jsbml.validator.factory;

import java.util.ArrayList;
import java.util.List;

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Species;


public class L3V1FactoryManager implements FactoryManager {

  @Override
  public List<Integer> getIdsForClass(Class<?> clazz, CheckCategory category) {
    List<Integer> list = new ArrayList<>();
    

	if (clazz.equals(Compartment.class))
    {
    	ConstraintFactory.addRangeToList(list, 20507, 20509);
    	ConstraintFactory.addIntToList(list, 20517);
    }
	else if(clazz.equals(Species.class))
	{
		ConstraintFactory.addIntToList(list, 20601);
		ConstraintFactory.addRangeToList(list, 20608, 20611);
		ConstraintFactory.addIntToList(list, 20614);
		ConstraintFactory.addIntToList(list, 20617);
		ConstraintFactory.addIntToList(list, 20623);
	}
    return list;
  }
}
