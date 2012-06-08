package org.sbml.jsbml.ext.multi;

import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ext.AbstractSBasePlugin;
import org.sbml.jsbml.ext.SBasePlugin;

public class MultiRule extends AbstractSBasePlugin  {

	// TODO : we could/should probably use the same SBasePlugin to InitialAssigment, Rules and EventAssignement 
	// as it is exactly the same structure
	
	// TODO : should probably be a listOf here
	private SpeciesTypeInstanceChange speciesTypeInstanceChange;
	

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
	public MultiRule clone() {
		// TODO Auto-generated method stub
		return null;
	}


}
