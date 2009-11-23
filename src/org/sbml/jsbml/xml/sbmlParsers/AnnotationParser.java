package org.sbml.jsbml.xml.sbmlParsers;

import org.sbml.jsbml.element.Annotation;
import org.sbml.jsbml.element.SBMLDocument;
import org.sbml.jsbml.xml.SBMLParser;

public class AnnotationParser implements SBMLParser{
		
	public void processAttribute(String elementName, String attributeName,
			String value, String prefix, boolean isLastAttribute, Object contextObject) {
		
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
			boolean hasAttributes, Object contextObject) {
		if (contextObject instanceof Annotation){
			
			Annotation annotation = (Annotation) contextObject;
			
			if (!prefix.equals("")){
				annotation.setAnnotation("<"+prefix+":"+elementName);
			}
			else {
				annotation.setAnnotation("<"+elementName);
			}
			
			if (!hasAttributes){
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
}
