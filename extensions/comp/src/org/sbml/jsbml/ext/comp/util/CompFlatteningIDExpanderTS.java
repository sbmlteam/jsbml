package org.sbml.jsbml.ext.comp.util;

import org.sbml.jsbml.Model;

import java.util.List;

public class CompFlatteningIDExpanderTS extends CompFlatteningIDExpander{


    @Override
    public String expandID(Model model, List<String> curPath) {

        StringBuilder subModelPrefix = new StringBuilder();
        for(int i=1; i < curPath.size(); i++) {
            subModelPrefix.append(curPath.get(i) + "__");
        }

        // Check if any object in the submodel already contains subModelPrefix as a prefix,
        // if so append an underscore and check again
        while (
                checkIfListIDsContainPrefix(model.getListOfParameters(), subModelPrefix.toString())
                        || checkIfListIDsContainPrefix(model.getListOfCompartments(), subModelPrefix.toString())
                        || checkIfListIDsContainPrefix(model.getListOfConstraints(), subModelPrefix.toString())
                        || checkIfListIDsContainPrefix(model.getListOfEvents(), subModelPrefix.toString())
                        || checkIfListIDsContainPrefix(model.getListOfRules(), subModelPrefix.toString())
                        || checkIfListIDsContainPrefix(model.getListOfFunctionDefinitions(), subModelPrefix.toString())
                        || checkIfListIDsContainPrefix(model.getListOfSpecies(), subModelPrefix.toString())
                        || checkIfListIDsContainPrefix(model.getListOfUnitDefinitions(), subModelPrefix.toString())
                        || checkIfListIDsContainPrefix(model.getListOfReactions(), subModelPrefix.toString())
        ) {
            subModelPrefix.append("_");
        }


        return subModelPrefix.toString();
    }
}