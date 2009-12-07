package org.sbml.jsbml.xml.sbmlParsers;

import org.sbml.jsbml.element.Annotation;
import org.sbml.jsbml.element.SBMLDocument;
import org.sbml.jsbml.xml.ReadingParser;

public class AnnotationParser implements ReadingParser{
			
	public void processAttribute(String elementName, String attributeName,
			String value, String prefix, boolean isLastAttribute,
			Object contextObject) {
		
		if (contextObject instanceof Annotation){
			Annotation annotation = (Annotation) contextObject;
			
			if (!prefix.equals("")){
				annotation.appendNoRDFAnnotation(" "+prefix+":"+attributeName+"=\""+value+"\"");
			}
			else {
				annotation.appendNoRDFAnnotation(" "+attributeName+"=\""+value+"\"");
			}
			
			if (isLastAttribute){
				annotation.appendNoRDFAnnotation("> \n");
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
			annotation.appendNoRDFAnnotation(characters + " \n");
			
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
				annotation.appendNoRDFAnnotation("<"+prefix+":"+elementName);
			}
			else {
				annotation.appendNoRDFAnnotation("<"+elementName);
			}
			
			if (!hasAttributes && !hasNamespaces){
				annotation.appendNoRDFAnnotation("> \n");
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

			if (isNested && annotation.getNoRDFAnnotation().endsWith("> \n")){
				int builderLength = builder.length();
				builder.delete(builderLength - 3, builderLength);
			}
			
			if (isNested){
				annotation.appendNoRDFAnnotation("/> \n");
			}
			else {
				if (!prefix.equals("")){
					annotation.appendNoRDFAnnotation("</"+prefix+":"+elementName+"> \n");
				}
				else{
					annotation.appendNoRDFAnnotation("</"+elementName+"> \n");
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
}
