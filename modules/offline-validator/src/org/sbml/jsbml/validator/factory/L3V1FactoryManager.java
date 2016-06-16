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
	addRangeToList(list, CORE20204, CORE20232);
	list.add(CORE20705);
    }

    public void addCoreGeneralFunctionDefinitionIds(List<Integer> list) {
	list.add(CORE20301);
	addRangeToList(list, CORE20303, CORE20307);
    }

    public void addCoreGeneralCompartmentIds(List<Integer> list) {
	addRangeToList(list, CORE20507, CORE20509);
	list.add(CORE20517);
    }

    public void addCoreGeneralSpeciesIds(List<Integer> list) {
	list.add(CORE20601);
	addRangeToList(list, CORE20608, CORE20611);
	list.add(CORE20614);
	list.add(CORE20617);
	list.add(CORE20623);
    }
}
