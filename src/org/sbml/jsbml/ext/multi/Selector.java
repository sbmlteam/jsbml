package org.sbml.jsbml.ext.multi;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractNamedSBase;

public class Selector extends AbstractNamedSBase{

	/**
	 * 
	 */
	public Selector(){
	}
	
	/**
	 * 
	 * @param selector
	 */
	public Selector(Selector selector){
		super(selector);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Override
	public Selector clone() {
		return new Selector(this);
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
