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
	}

	public void processCharactersOf(String elementName, String characters,
			Object contextObject) {
		if (contextObject instanceof Annotation){
			Annotation annotation = (Annotation) contextObject;
			annotation.setAnnotation(characters + " \n");
			
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
	}

	public void processEndDocument(SBMLDocument sbmlDocument) {
		// TODO Auto-generated method stub
		
	}
}
