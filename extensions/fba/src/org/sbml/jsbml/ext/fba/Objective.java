package org.sbml.jsbml.ext.fba;

import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.UniqueNamedSBase;
import org.sbml.jsbml.xml.parsers.FBAParser;

public class Objective extends AbstractNamedSBase implements UniqueNamedSBase {

	private String type;
	private ListOf<FluxObjective> listOfFluxObjectives = new ListOf<FluxObjective>();
	
	@Override
	public AbstractSBase clone() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns the type.
	 * 
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 * 
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Returns the listOfFluxObjectives
	 * 
	 * @return the listOfFluxObjectives
	 */
	public ListOf<FluxObjective> getListOfFluxObjectives() {
		return listOfFluxObjectives;
	}

	/**
	 * @param listOfFluxObjectives the listOfFluxObjectives to set
	 */
	public void addFluxObjective(FluxObjective fluxObjective) {
		this.listOfFluxObjectives.add(fluxObjective);
	}

	// TODO : not sure why, but I had to make both Objective and FluxBound without mandatory ID, otherwise the parsing would fail !!!
	public boolean isIdMandatory() {
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName,
	 * String prefix, String value)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix, String value) {
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);
		
		if (!isAttributeRead) {
			isAttributeRead = true;

			if (attributeName.equals("type")) {
				setType(value);
			} else {
				isAttributeRead = false;
			}
			
		}
		
		return isAttributeRead;
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
	 */
	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();

		if (type != null) {
			attributes.put(FBAParser.shortLabel+ ":type", getType());			
		}
		if (isSetId()) {
			attributes.remove("id");
			attributes.put(FBAParser.shortLabel+ ":id", getId());
		}
		
		return attributes;
	}

	/**
	 * @param index
	 * @return
	 * @see org.sbml.jsbml.ListOf#getChildAt(int)
	 */
	public TreeNode getChildAt(int index) {
		if (index == 0 && listOfFluxObjectives.size() > 0) {
			return listOfFluxObjectives;
		}
		
		return null;
	}

	/**
	 * @return
	 * @see org.sbml.jsbml.ListOf#getChildCount()
	 */
	public int getChildCount() {
		if (listOfFluxObjectives.size() > 0) {
			return 1;
		}
		
		return 0;
	}

	
	
}
