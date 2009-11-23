package org.sbml.jsbml.xml.sbmlParsers;

import org.sbml.jsbml.element.ModelAnnotation;
import org.sbml.jsbml.element.ModelHistory;
import org.sbml.jsbml.element.SBMLDocument;
import org.sbml.jsbml.xml.SBMLParser;

public class CreatorParser implements SBMLParser{

	public void processAttribute(String elementName, String attributeName,
			String value, String prefix, boolean isLastAttribute,
			Object contextObject) {
		// TODO : there is no attributes with the namespace "http://purl.org/dc/elements/1.1/", there
		// is a SBML syntax error, throw an exception?
	}

	public void processCharactersOf(String elementName, String characters,
			Object contextObject) {
		// TODO : there is no text for the element with the namespace "http://purl.org/dc/elements/1.1/", there
		// is a SBML syntax error, throw an exception?
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
		else {
			// TODO : !elementName.equals("creator"), SBML syntax error?
			// TODO : !contextObject instanceof ModelAnnotation, for the moment, only a model with a modelAnnotaton
			// can contain an history. Should be changed depending on the version.
		}
		return contextObject;
	}

	public void processEndDocument(SBMLDocument sbmlDocument) {
	}

}
