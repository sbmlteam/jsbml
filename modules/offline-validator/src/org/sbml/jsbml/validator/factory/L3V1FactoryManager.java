/*
 * 
 */

package org.sbml.jsbml.validator.factory;

import java.util.List;

/**
 * 
 * @author Roman Schulte
 * @version $Rev$
 * @since 1.2
 * @date Jun 3, 2016
 */
public class L3V1FactoryManager extends AbstractFactoryManager {

    public void addCoreGeneralModelIds(List<Integer> list) {
	addRangeToList(list, CORE_20204, CORE_20232);
	list.add(CORE_20705);
    }

    public void addCoreGeneralFunctionDefinitionIds(List<Integer> list) {
	list.add(CORE_20301);
	addRangeToList(list, CORE_20303, CORE_20307);
    }

    public void addCoreGeneralCompartmentIds(List<Integer> list) {
	addRangeToList(list, CORE_20507, CORE_20509);
	list.add(CORE_20517);
    }

    public void addCoreGeneralSpeciesIds(List<Integer> list) {
	list.add(CORE_20601);
	addRangeToList(list, CORE_20608, CORE_20611);
	list.add(CORE_20614);
	list.add(CORE_20617);
	list.add(CORE_20623);
    }
    
    public void add
}
