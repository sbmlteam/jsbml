package org.sbml.jsbml.sbmlExtensions.groups;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBase;

/**
 * This class represents the listOf extension for the group package
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16-Feb-2010</pre>
 */

public class ListOfGroups<T extends SBase> extends ListOf{

    public GroupList getCurrentList() {
        return currentList;
    }

    /**
     * name of the list at it appears in the SBMLFile.
     */
    private GroupList currentList = GroupList.none;

    /*
      * (non-Javadoc)
      *
      * @see java.lang.Object#equals(java.lang.Object)
      */
    // @Override
    public boolean equals(Object o) {
        if (o instanceof GroupList) {
            boolean equals = super.equals(o);
            ListOfGroups<SBase> listOf = (ListOfGroups<SBase>) o;
            equals &= getCurrentList() == listOf.getCurrentList();
            return listOf.containsAll(this) && equals;
        }
        return false;
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see org.sbml.jlibsbml.SBase#getAnnotationString()
	 */
	public String getElementName() {
		String name = getCurrentList().toString();
		return name;
	}

   /**
	 * Sets the SBaseListType of this ListOf instance to 'currentList'.
	 *
	 * @param currentList
	 */
	public void setCurrentList(GroupList currentList) {
		this.currentList = currentList;
		stateChanged();
	}

}
