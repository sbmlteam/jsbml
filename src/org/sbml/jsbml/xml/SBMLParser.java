package org.sbml.jsbml.xml;

import java.util.ArrayList;

import org.sbml.jsbml.element.SBMLDocument;

public interface SBMLParser {

	public Object processStartElement(String ElementName, String prefix, Object contextObject);
	
	public void processAttribute(String ElementName, String AttributeName, String value, String prefix, Object contextObject);

	public void processCharactersOf(String elementName, String characters, Object contextObject);
	
	public void processEndElement(String ElementName, String prefix, boolean isNested, Object contextObject);
	
	public void processEndDocument(SBMLDocument sbmlDocument);
	
	public void processNamespace(String elementName, String URI, String prefix, String localName, Object contextObject);
	
	public ArrayList<Object> getListOfSBMLElementsToWrite(Object objectToWrite);
	
	public void writeElement(SBMLObjectForXML xmlObject, Object sbmlElementToWrite); 
	
	public void writeNamespaces(SBMLObjectForXML xmlObject, Object sbmlElementToWrite);
	
	public void writeAttributes(SBMLObjectForXML xmlObject, Object sbmlElementToWrite);
	
	public void writeCharacters(SBMLObjectForXML xmlObject, Object sbmlElementToWrite);
}
