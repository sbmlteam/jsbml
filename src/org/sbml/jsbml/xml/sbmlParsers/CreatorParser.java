package org.sbml.jsbml.xml.sbmlParsers;

import org.sbml.jsbml.element.ModelAnnotation;
import org.sbml.jsbml.element.ModelHistory;
import org.sbml.jsbml.element.SBMLDocument;
import org.sbml.jsbml.xml.SBMLParser;

public class CreatorParser implements SBMLParser{

	public void processAttribute(String elementName, String attributeName,
			String value, String prefix, boolean isLastAttribute,
			Object contextObject) {
	}

	public void processCharactersOf(String elementName, String characters,
			Object contextObject) {
	}

	public void processEndElement(String elementName, String prefix,
			boolean isNested, Object contextObject) {
	}

	public Object processStartElement(String elementName, String prefix,
			boolean hasAttributes, Object contextObject) {

		if (elementName.equals("creator") && contextObject instanceof ModelAnnotation){
			ModelAnnotation annotation = (ModelAnnotation) contextObject;
			ModelHistory modelHistory = new ModelHistory();
			annotation.setModelHistory(modelHistory);
			
			return modelHistory;
		}
		return contextObject;
	}

	public void processEndDocument(SBMLDocument sbmlDocument) {
	}

}
