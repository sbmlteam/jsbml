package org.sbml.jsbml.xml.sbmlParsers;

import java.util.ArrayList;

import org.sbml.jsbml.element.Annotation;
import org.sbml.jsbml.element.SBMLDocument;
import org.sbml.jsbml.xml.SBMLObjectForXML;
import org.sbml.jsbml.xml.SBMLParser;

public class AnnotationParser implements SBMLParser{
			
	public void processAttribute(String elementName, String attributeName,
			String value, String prefix, boolean isLastAttribute,
			Object contextObject) {
		
		if (contextObject instanceof Annotation){
			Annotation annotation = (Annotation) contextObject;
			
			if (!prefix.equals("")){
				annotation.setAnnotation(" "+prefix+":"+attributeName+"=\""+value+"\"");
			}
			else {
				annotation.setAnnotation(" "+attributeName+"=\""+value+"\"");
			}
			
			if (isLastAttribute){
				annotation.setAnnotation("> \n");
			}
		}
		else {
			// TODO : the other annotations which will be stored into a string should be included into
			// an annotation node. There is a synthax error? Throw an exception?, the attribute can't be read?
		}
	}

	public void processCharactersOf(String elementName, String characters,
			Object contextObject) {
		characters = characters.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");

		if (contextObject instanceof Annotation){
			Annotation annotation = (Annotation) contextObject;
			annotation.setAnnotation(characters + " \n");
			
		}
		else {
			// TODO : the other annotations which will be stored into a string should be included into
			// an annotation node. There is a synthax error? Throw an exception?, the text can't be read?
		}
	}

	public Object processStartElement(String elementName, String prefix,
			boolean hasAttributes, boolean hasNamespaces,
			Object contextObject) {
		
		if (contextObject instanceof Annotation){
			
			Annotation annotation = (Annotation) contextObject;
			
			if (!prefix.equals("")){
				annotation.setAnnotation("<"+prefix+":"+elementName);
			}
			else {
				annotation.setAnnotation("<"+elementName);
			}
			
			if (!hasAttributes && !hasNamespaces){
				annotation.setAnnotation("> \n");
			}
			return annotation;
		}
		else {
			// TODO : the other annotations which will be stored into a string should be included into
			// an annotation node. There is a synthax error? Throw an exception?, the node can't be read?
		}
		return contextObject;
	}
	
	public void processEndElement(String elementName, String prefix, boolean isNested, Object contextObject){
		
		if (contextObject instanceof Annotation){
			Annotation annotation = (Annotation) contextObject;
			StringBuilder builder = annotation.getAnnotationBuilder();

			if (isNested && annotation.getAnnotation().endsWith("> \n")){
				int builderLength = builder.length();
				builder.delete(builderLength - 3, builderLength);
			}
			
			if (isNested){
				annotation.setAnnotation("/> \n");
			}
			else {
				if (!prefix.equals("")){
					annotation.setAnnotation("</"+prefix+":"+elementName+"> \n");
				}
				else{
					annotation.setAnnotation("</"+elementName+"> \n");
				}
			}
		}
		else {
			// TODO : the other annotations which will be stored into a string should be included into
			// an annotation node. There is a synthax error? Throw an exception?, the node can't be read?
		}
	}

	public void processEndDocument(SBMLDocument sbmlDocument) {
	}

	public void processNamespace(String elementName, String URI, String prefix,
			String localName, boolean hasAttributes, boolean isLastNamespace,
			Object contextObject) {
		
		if (elementName.equals("annotation") && contextObject instanceof Annotation){
			Annotation annotation = (Annotation) contextObject;
			annotation.addAnnotationNamespace(localName, prefix, URI);
		}
		else if (elementName.equals("sbml") && contextObject instanceof SBMLDocument){
			SBMLDocument sbmlDocument = (SBMLDocument) contextObject;
			sbmlDocument.addNamespace(localName, prefix, URI);
		}
	}

	public ArrayList<Object> getListOfSBMLElementsToWrite(Object object) {
		
		
		return null;
	}

	public void writeElement(SBMLObjectForXML xmlObject, Object sbmlElementToWrite) {
		// TODO Auto-generated method stub
		
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
