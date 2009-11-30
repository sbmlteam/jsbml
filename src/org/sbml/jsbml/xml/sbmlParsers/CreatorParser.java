package org.sbml.jsbml.xml.sbmlParsers;

import java.util.ArrayList;

import org.sbml.jsbml.element.Annotation;
import org.sbml.jsbml.element.ModelHistory;
import org.sbml.jsbml.element.SBMLDocument;
import org.sbml.jsbml.xml.SBMLObjectForXML;
import org.sbml.jsbml.xml.SBMLParser;

public class CreatorParser implements SBMLParser{

	public void processAttribute(String elementName, String attributeName,
			String value, String prefix, Object contextObject) {
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

	public Object processStartElement(String elementName, String prefix, Object contextObject) {

		if (elementName.equals("creator") && contextObject instanceof Annotation){
			Annotation annotation = (Annotation) contextObject;
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

	public void processNamespace(String elementName, String URI, String prefix,
			String localName, Object contextObject) {
		
		if (elementName.equals("RDF") && contextObject instanceof Annotation){
			Annotation annotation = (Annotation) contextObject;
			
			annotation.addRDFAnnotationNamespace(localName, prefix, URI);
		}
	}

	public ArrayList<Object> getListOfSBMLElementsToWrite(Object objectToWrite) {
		return null;
	}

	public void writeElement(SBMLObjectForXML xmlObject, Object sbmlElementToWrite) {
	}

	public void writeAttributes(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) {
		// TODO Auto-generated method stub
		
	}

	public void writeCharacters(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) {
		// TODO Auto-generated method stub
		
	}

	public void writeNamespaces(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) {
		// TODO Auto-generated method stub
		
	}

}
