package org.sbml.jsbml.util;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBase;

public class ListOfWithName<T extends SBase> extends ListOf<T> {

	private String elementName;
	
	public ListOfWithName(String elementName) {
		super();
		this.elementName = elementName; 
	}

	public ListOfWithName(int level, int version, String elementName) {
		super(level, version);
		this.elementName = elementName; 
	}

	public ListOfWithName(ListOfWithName<T> listOf) {
		super(listOf);
		elementName = listOf.elementName;
	}

	@Override
	public String getElementName() 
	{
		return elementName;
	}
}
