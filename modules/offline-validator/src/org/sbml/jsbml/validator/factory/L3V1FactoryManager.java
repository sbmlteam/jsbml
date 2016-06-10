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
	addRangeToList(list, 20204, 20232);
	list.add(20705);
    }

    public void addCoreGeneralFunctionDefinitionIds(List<Integer> list) {
	list.add(20301);
	addRangeToList(list, 20303, 20307);
    }

    public void addCoreGeneralCompartmentIds(List<Integer> list) {
	addRangeToList(list, 20507, 20509);
	list.add(20517);
    }

    public void addCoreGeneralSpeciesIds(List<Integer> list) {
	list.add(20601);
	addRangeToList(list, 20608, 20611);
	list.add(20614);
	list.add(20617);
	list.add(20623);
    }
}
