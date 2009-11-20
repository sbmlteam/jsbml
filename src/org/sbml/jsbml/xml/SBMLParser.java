package org.sbml.jsbml.xml;

import org.sbml.jsbml.element.SBMLDocument;

public interface SBMLParser {

	public Object processStartElement(String ElementName, String prefix, boolean hasAttributes, Object contextObject);
	
	public void processAttribute(String ElementName, String AttributeName, String value, String prefix, boolean isLastAttribute, Object contextObject);

	public void processCharactersOf(String elementName, String characters, Object contextObject);
	
	public void processEndElement(String ElementName, String prefix, boolean isNested, Object contextObject);
	
	public void processEndDocument(SBMLDocument sbmlDocument);
	
	//public ArrayList<SBase> getListOfSBMLElementsToWrite();

}
