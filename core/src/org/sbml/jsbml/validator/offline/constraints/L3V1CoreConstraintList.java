package org.sbml.jsbml.validator.offline.constraints;

import java.util.List;

import org.sbml.jsbml.validator.offline.factory.SBMLErrorCodes;

public final class L3V1CoreConstraintList extends AbstractConstraintList implements SBMLErrorCodes {
    
    public static void addGeneralSBMLDocumentIds(List<Integer> list)
    {
	list.add(CoreSpecialErrorCodes.ID_VALIDATE_DOCUMENT_TREE);
    }

    
    public static void addGeneralModelIds(List<Integer> list) {
	addRangeToList(list, CORE_20204, CORE_20232);
	list.add(CORE_20705);
	list.add(CoreSpecialErrorCodes.ID_VALIDATE_MODEL_TREE);
    }

    public static void addGeneralFunctionDefinitionIds(List<Integer> list) {
	list.add(CORE_20301);
	addRangeToList(list, CORE_20303, CORE_20307);
    }

    public static void addGeneralCompartmentIds(List<Integer> list) {
	addRangeToList(list, CORE_20507, CORE_20509);
	list.add(CORE_20517);
    }

    public static void addGeneralSpeciesIds(List<Integer> list) {
	list.add(CORE_20601);
	addRangeToList(list, CORE_20608, CORE_20610);
	list.add(CORE_20614);
	list.add(CORE_20617);
	list.add(CORE_20623);
    }

}
