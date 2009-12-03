package org.sbml.jsbml.xml.sbmlParsers;

import org.sbml.jsbml.element.Annotation;
import org.sbml.jsbml.element.Constraint;
import org.sbml.jsbml.element.MathContainer;
import org.sbml.jsbml.element.SBMLDocument;
import org.sbml.jsbml.element.SBase;
import org.sbml.jsbml.xml.ReadingParser;

public class StringParser implements ReadingParser{
	
	private String typeOfNotes = "";

	public String getTypeOfNotes() {
		return typeOfNotes;
	}

	public void setTypeOfNotes(String typeOfNotes) {
		this.typeOfNotes = typeOfNotes;
	}
	
	private StringBuffer getStringBufferFor(Object contextObject){
		StringBuffer buffer = null;

		if (typeOfNotes.equals("message") && contextObject instanceof Constraint){
			Constraint constraint = (Constraint) contextObject;
			buffer = constraint.getMessageBuffer();
			
		}
		else if ((typeOfNotes.equals("notes") || typeOfNotes.equals("")) && contextObject instanceof SBase){
			SBase sbase = (SBase) contextObject;
			buffer = sbase.getNotesBuffer();
		}
		else if (typeOfNotes.equals("math") && contextObject instanceof MathContainer){
			MathContainer mathContainer = (MathContainer) contextObject;
			buffer = mathContainer.getMathBuffer();
		}
		else {
			// TODO : SBML syntax error, throw an exception?
		}
		
		return buffer;
	}

	public void processAttribute(String elementName, String attributeName,
			String value, String prefix, boolean isLastAttribute,
			Object contextObject) {
		
		StringBuffer buffer = getStringBufferFor(contextObject);
		
		if (buffer != null){
			if (!prefix.equals("")){
				buffer.append(" "+prefix+":"+attributeName+"=\""+value+"\"");
			}
			else {
				buffer.append(" "+attributeName+"=\""+value+"\"");
			}
			
			if (isLastAttribute){
				buffer.append("> \n");
			}
		}
		else {
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
	}

	public void processCharactersOf(String elementName, String characters,
			Object contextObject) {
		
		StringBuffer buffer = getStringBufferFor(contextObject);
		characters = characters.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");

		
		if (buffer != null){
			buffer.append(characters + " \n");
		}
		else {
			if (contextObject instanceof Annotation){
				Annotation annotation = (Annotation) contextObject;
				annotation.setAnnotation(characters + " \n");
			}
		}
	}

	public void processEndElement(String elementName, String prefix,
			boolean isNested, Object contextObject) {

		StringBuffer buffer = getStringBufferFor(contextObject);
		
		if (buffer != null){
			if (isNested && buffer.toString().endsWith("> \n")){
				int bufferLength = buffer.length();
				buffer.delete(bufferLength - 3, bufferLength);
			}
			
			if (isNested){
				buffer.append("/> \n");
			}
			else {
				if (!prefix.equals("")){
					buffer.append("</"+prefix+":"+elementName+"> \n");
				}
				else {
					buffer.append("</"+elementName+"> \n");
				}
			}
		}
		else {
			if (contextObject instanceof Annotation){
				Annotation annotation = (Annotation) contextObject;
				StringBuilder builder = annotation.getAnnotationBuilder();

				if (isNested && annotation.getAnnotation().endsWith("> \n")){
					int builderLength = builder.length();
					builder.delete(builderLength - 4, builderLength - 1);
				}
				
				if (isNested){
					annotation.setAnnotation("/> \n");
				}
				else {
					if (!prefix.equals("")){
						annotation.setAnnotation("</"+prefix+":"+elementName+"> \n");
					}
					else {
						annotation.setAnnotation("</"+elementName+"> \n");
					}
				}
			}
		}
	}

	public Object processStartElement(String elementName, String prefix,
			boolean hasAttributes, boolean hasNamespaces,
			Object contextObject) {
		StringBuffer buffer = null;
		
		if (elementName.equals("math") && contextObject instanceof MathContainer){
			MathContainer mathContainer = (MathContainer) contextObject;
			buffer = new StringBuffer();
			mathContainer.setMathBuffer(buffer);
			this.typeOfNotes = elementName;
		}
		else {
			buffer = getStringBufferFor(contextObject);
		}
		
		if (buffer != null){

			if (!prefix.equals("")){
				buffer.append("<"+prefix+":"+elementName);
			}
			else {
				buffer.append("<"+elementName);
			}
			
			if (!hasAttributes && !hasNamespaces){
				buffer.append("> \n");
			}
		}
		else {
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
			}
		}
		return contextObject;
	}

	public void processEndDocument(SBMLDocument sbmlDocument) {
		// TODO Auto-generated method stub
	}

	public void processNamespace(String elementName, String URI, String prefix,
			String localName, boolean hasAttributes, boolean isLastNamespace,
			Object contextObject) {
		
		StringBuffer buffer = getStringBufferFor(contextObject);

		if (buffer != null){
			if (!prefix.equals("")){
				buffer.append(" "+prefix+":"+localName+"=\""+URI+"\"");
			}
			else {
				buffer.append(" "+localName+"=\""+URI+"\"");
			}
			if (!hasAttributes && isLastNamespace){
				buffer.append("> \n");
			}
		}
		else {
			if (contextObject instanceof Annotation){
				Annotation annotation = (Annotation) contextObject;
				if (!prefix.equals("")){
					annotation.setAnnotation(" "+prefix+":"+localName+"=\""+URI+"\"");
				}
				else {
					annotation.setAnnotation(" "+localName+"=\""+URI+"\"");
				}
				
				if (!hasAttributes && isLastNamespace){
					annotation.setAnnotation("> \n");
				}
			}
		}
	}
}
