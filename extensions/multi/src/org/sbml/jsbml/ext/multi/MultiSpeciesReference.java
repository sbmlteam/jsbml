package org.sbml.jsbml.ext.multi;

import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.ext.AbstractSBasePlugin;
import org.sbml.jsbml.ext.SBasePlugin;

public class MultiSpeciesReference extends AbstractSBasePlugin {

	/**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 3171952386462646205L;
  ListOf<SpeciesTypeRestriction> listOfSpeciesTypeRestrictions;
	
	
	/**
	 * Returns the list of {@link SpeciesTypeRestriction}.
	 * 
	 * @return the list of {@link SpeciesTypeRestriction}
	 */
	public ListOf<SpeciesTypeRestriction> getListOfSpeciesTypeRestrictions() {
		if (listOfSpeciesTypeRestrictions == null) {
			listOfSpeciesTypeRestrictions = new ListOf<SpeciesTypeRestriction>();
		}
		
		return listOfSpeciesTypeRestrictions;
	}

	/**
	 * Adds a {@link SpeciesTypeRestriction}.
	 * 
	 * @param speciesTypeRestriction the {@link SpeciesTypeRestriction} to add
	 */
	public void addSpeciesTypeRestriction(SpeciesTypeRestriction speciesTypeRestriction) {
		getListOfSpeciesTypeRestrictions().add(speciesTypeRestriction);
	}

	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		// TODO Auto-generated method stub
		return false;
	}

	public TreeNode getChildAt(int childIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getChildCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean getAllowsChildren() {
		// TODO Auto-generated method stub
		return false;
	}

	public Map<String, String> writeXMLAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
	public MultiSpeciesReference clone() {
		// TODO Auto-generated method stub
		return null;
	}

}
