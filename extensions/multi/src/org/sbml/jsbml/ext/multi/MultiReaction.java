package org.sbml.jsbml.ext.multi;

import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.ext.AbstractSBasePlugin;
import org.sbml.jsbml.ext.SBasePlugin;

public class MultiReaction extends AbstractSBasePlugin {

	ListOf<ReactionRule> listOfReactionRules;
	
	
	/**
	 * @return the listOfReactionRules
	 */
	public ListOf<ReactionRule> getListOfReactionRules() {
		if (listOfReactionRules == null) {
			listOfReactionRules = new ListOf<ReactionRule>();
		}
		
		return listOfReactionRules;
	}

	/**
	 * @param listOfReactionRules the listOfReactionRules to set
	 */
	public void addReactionRule(ReactionRule reactionRule) {
		getListOfReactionRules().add(reactionRule);
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
	public MultiReaction clone() {
		// TODO Auto-generated method stub
		return null;
	}

}
