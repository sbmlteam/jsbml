package org.sbml.jsbml.ext.multi;

import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.util.filters.NameFilter;

public class StateFeatureInstance extends AbstractNamedSBase {

	private String stateFeature;
	
	private ListOf<StateFeatureValue> listOfStateFeatureValues;
	
	/**
	 * Creates a new StateFeatureInstance.
	 */
	public StateFeatureInstance () {
		super();
		initDefaults();
	}
	
	public boolean isIdMandatory() {
		return false;
	}

	@Override
	public AbstractSBase clone() {
		// TODO 
		return null;
	}

	/**
	 * Returns the stateFeature.
	 * 
	 * @return the stateFeature
	 */
	public String getStateFeature() {
		return stateFeature;
	}

	/**
	 * Sets the stateFeature. 
	 * 
	 * @param stateFeature the stateFeature to set
	 */
	public void setStateFeature(String stateFeature) {
		this.stateFeature = stateFeature;
	}
	
	/**
	 * Returns true if the stateFeature id is defined.
	 * 
	 * @return true if the stateFeature id is defined.
	 */
	public boolean isSetStateFeature() {
		return stateFeature != null;
	}

	/**
	 * Returns the listOfStateFeatureValues
	 * 
	 * @return the listOfStateFeatureValues
	 */
	public ListOf<StateFeatureValue> getListOfStateFeatureValues() {
		if (listOfStateFeatureValues == null) {
			listOfStateFeatureValues = new ListOf<StateFeatureValue>();
			listOfStateFeatureValues.addNamespace(MultiConstant.namespaceURI);
			this.registerChild(listOfStateFeatureValues);
			listOfStateFeatureValues.setSBaseListType(ListOf.Type.other);
		}

		return listOfStateFeatureValues;
	}

	/**
	 * Adds a StateFeatureValue.
	 * 
	 * @param stateFeatureValue the StateFeatureValue to add
	 */
	public void addStateFeatureValue(StateFeatureValue stateFeatureValue) {
		getListOfStateFeatureValues().add(stateFeatureValue);
	}

	/**
	 * Creates a new {@link StateFeatureValue} inside this {@link StateFeatureInstance} and returns it.
	 * 
	 * @return the {@link StateFeatureValue} object created
	 */
	public StateFeatureValue createStateFeatureValue() {
		StateFeatureValue stateFeatureValue = new StateFeatureValue();
		addStateFeatureValue(stateFeatureValue);

		return stateFeatureValue;
	}

	/**
	 * Gets the ith {@link StateFeatureValue}.
	 * 
	 * @param i
	 * 
	 * @return the ith {@link StateFeatureValue}
	 * @throws IndexOutOfBoundsException if the index is invalid.
	 */
	public StateFeatureValue getStateFeatureValue(int i) {
		return getListOfStateFeatureValues().get(i);
	}

	/**
	 * Gets the {@link StateFeatureValue} that has the given id. 
	 * 
	 * @param id
	 * @return the {@link StateFeatureValue} that has the given id or null if
	 * no {@link StateFeatureValue} are found that match <code>id</code>.
	 */
	public StateFeatureValue getStateFeatureValue(String id){
		if(isSetListOfStateFeatureValues()) {
			return listOfStateFeatureValues.firstHit(new NameFilter(id));	    
		} 
		return null;
	}

	/**
	 * Returns true if the listOfStateFeatureValue is set.
	 * 
	 * @return true if the listOfStateFeatureValue is set.
	 */
	public boolean isSetListOfStateFeatureValues() {
		if ((listOfStateFeatureValues == null) || listOfStateFeatureValues.isEmpty()) {
			return false;			
		}		
		return true;
	}

	/**
	 * Sets the listOfStateFeatureValues to null
	 * 
	 * @return true is successful
	 */
	public boolean unsetListOfStateFeatureValues(){
		if(isSetListOfStateFeatureValues()) {
			// unregister the ids if needed.			  
			this.listOfStateFeatureValues.fireNodeRemovedEvent();
			this.listOfStateFeatureValues = null;
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 */
	public void initDefaults() {
		addNamespace(MultiConstant.namespaceURI);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
	 */
	@Override
	public TreeNode getChildAt(int index) {
		if (index < 0) {
			throw new IndexOutOfBoundsException(index + " < 0");
		}

		int count = super.getChildCount(), pos = 0;
		if (index < count) {
			return super.getChildAt(index);
		} else {
			index -= count;
		}
		if (isSetListOfStateFeatureValues()) {
			if (pos == index) {
				return getListOfStateFeatureValues();
			}
			pos++;
		}

		throw new IndexOutOfBoundsException(String.format("Index %d >= %d",
				index, +((int) Math.min(pos, 0))));
	}


	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getChildCount()
	 */
	@Override
	public int getChildCount() {
		int count = super.getChildCount();

		if (isSetListOfStateFeatureValues()) {
			count++;
		}

		return count;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) 
	{
		boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

		if (!isAttributeRead) {

			if (attributeName.equals(MultiConstant.stateFeature)){
				setStateFeature(value);
				isAttributeRead = true;
			} 
		}	  

		return isAttributeRead;

	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
	 */
	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();

		if (isSetId()) {
			attributes.remove("id");
			attributes.put(MultiConstant.shortLabel+ ":id", getId());
		}
		if (isSetName()) {
			attributes.remove("name");
			attributes.put(MultiConstant.shortLabel+ ":name", getName());
		}
		if (isSetStateFeature()) {
			attributes.put(MultiConstant.shortLabel + ":" + MultiConstant.stateFeature, getStateFeature());
		} 

		return attributes;
	}

	
	// TODO : removeXX unsetXX

}
