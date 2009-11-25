package org.sbml.jsbml.xml.sbmlParsers;

import java.util.ArrayList;

import org.sbml.jsbml.element.SBMLDocument;
import org.sbml.jsbml.element.SBase;
import org.sbml.jsbml.xml.SBMLParser;

public class MathMLParser implements SBMLParser{

	public void processAttribute(String ElementName, String AttributeName,
			String value, String prefix, boolean isLastAttribute, Object contextObject) {
		// TODO Auto-generated method stub
		
	}

	public void processCharactersOf(String elementName, String characters,
			Object contextObject) {
		// TODO Auto-generated method stub
		
	}

	public Object processStartElement(String ElementName, String prefix,
			boolean hasAttributes, Object contextObject) {
		// TODO Auto-generated method stub
		return null;
	}

	public void processEndElement(String ElementName, String prefix,
			boolean isNested, Object contextObject) {
		// TODO Auto-generated method stub
		
	}

	public void processEndDocument(SBMLDocument sbmlDocument) {
		// TODO Auto-generated method stub
		
	}

	public void processNamespace(String elementName, String URI, String prefix,
			String localName, boolean isLastNamespace, boolean hasOtherAttributes, Object contextObject) {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<SBase> getListOfSBMLElementsToWrite(SBase sbase) {
		// TODO Auto-generated method stub
		return null;
	}

}
