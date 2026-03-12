package org.sbml.jsbml.ext.comp.util;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBase;

import java.util.List;

public abstract class CompFlatteningIDExpander {

    public abstract String expandID(Model model, List<String> curPath);

    static boolean checkIfListIDsContainPrefix(ListOf sBaseList, String prefix) {

        for (Object object : sBaseList) {
            SBase sBase = (SBase) object;
            if (sBase.getId().startsWith(prefix) || sBase.getMetaId().startsWith(prefix)) {
                return true;
            }
        }

        return false;
    }

}