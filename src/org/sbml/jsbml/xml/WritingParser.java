package org.sbml.jsbml.xml;

import java.util.ArrayList;

public interface WritingParser {

public ArrayList<Object> getListOfSBMLElementsToWrite(Object objectToWrite);
	
	public void writeElement(SBMLObjectForXML xmlObject, Object sbmlElementToWrite); 
	
	public void writeNamespaces(SBMLObjectForXML xmlObject, Object sbmlElementToWrite);
	
	public void writeAttributes(SBMLObjectForXML xmlObject, Object sbmlElementToWrite);
	
	public void writeCharacters(SBMLObjectForXML xmlObject, Object sbmlElementToWrite);
}
