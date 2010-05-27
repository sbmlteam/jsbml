package org.sbml.jsbml.ext.multi;

import java.util.HashMap;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractNamedSBase;

public class InitialSpeciesInstance extends AbstractNamedSBase {
	
	private Double initialProportion;
		
	private String selectorID;
	
	public InitialSpeciesInstance(){
		this.selectorID = null;
		this.initialProportion = null;
	}
	
	public InitialSpeciesInstance(InitialSpeciesInstance in){
		this.setSelector(in.getSelector());
		this.setInitialProportion(in.getInitialProportion());
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Override
	public InitialSpeciesInstance clone() {
		return new InitialSpeciesInstance(this);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setInitialProportion(double initialProportion) {
		this.initialProportion = initialProportion;
	}

	public double getInitialProportion() {
		return isSetInitialProportion() ? initialProportion : 0;
	}

	public void setSelector(Selector selector) {
		this.selectorID = selector.isSetId() ? selector.getId() : "";
	}

	public Selector getSelectorInstance() {
		// TODO extend model to have the listOfSelector and the appropriate methods
		return null;
		
	}
	
	public String getSelector(){
		return isSetSelector() ? this.selectorID : "";
	}

	public void setSelector(String selectorID) {
		this.selectorID = selectorID;
	}
	
	public boolean isSetSelector(){
		return this.selectorID != null;
	}
	
	public boolean isSetSelectorInstance(){
		// TODO extend Model to add the listOfSelector and the appropriate methods
		return false;
	}
	
	public boolean isSetInitialProportion(){
		return this.initialProportion != null;
	}
	
	public boolean readAttribute(String attributeName, String prefix, String value){
		boolean isReadAttribute = super.readAttribute(attributeName, prefix, value);
		
		if (! isReadAttribute){
			if (attributeName.equals("initialProportion")){
				this.initialProportion = Double.parseDouble(value);
				
				return true;
			}
			else if (attributeName.equals("selector")){
				this.selectorID = value;
				
				return true;
			}
		}
		
		return isReadAttribute;
	}
	
	public HashMap<String, String> writeXMLAttributes(){
		HashMap<String, String> attributes = new HashMap<String, String>();
		
		if (isSetInitialProportion()){
			attributes.put("initialProportion", Double.toString(getInitialProportion()));
		}
		if (isSetSelector()){
			attributes.put("selector", getSelector());
		}
		
		return attributes;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#getAllowsChildren()
	 */
	public boolean getAllowsChildren() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#getChildAt(int)
	 */
	public TreeNode getChildAt(int childIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#getChildCount()
	 */
	public int getChildCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
