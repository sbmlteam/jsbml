package org.sbml.jsbml.xml.sbmlParsers;

import org.sbml.jsbml.element.Constraint;
import org.sbml.jsbml.element.MathContainer;
import org.sbml.jsbml.element.SBMLDocument;
import org.sbml.jsbml.element.SBase;
import org.sbml.jsbml.xml.SBMLParser;

public class StringParser implements SBMLParser{
	
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
			// TODO : SBML syntax error, throw an exception?
		}
	}

	public void processCharactersOf(String elementName, String characters,
			Object contextObject) {
		
		StringBuffer buffer = getStringBufferFor(contextObject);
		
		if (buffer != null){
			buffer.append(characters + " \n");
		}
		else {
			// TODO : SBML syntax error, throw an exception?
		}
	}

	public void processEndElement(String elementName, String prefix,
			boolean isNested, Object contextObject) {

		StringBuffer buffer = getStringBufferFor(contextObject);
		
		if (buffer != null){
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
			// TODO : SBML syntax error, throw an exception?
		}
	}

	public Object processStartElement(String elementName, String prefix,
			boolean hasAttributes, Object contextObject) {
		
		if (elementName.equals("math") && contextObject instanceof MathContainer){
			MathContainer mathContainer = (MathContainer) contextObject;
			StringBuffer mathBuffer = new StringBuffer();
			mathContainer.setMathBuffer(mathBuffer);
			this.typeOfNotes = elementName;
			
			return mathContainer;
		}
		
		StringBuffer buffer = getStringBufferFor(contextObject);
		
		if (buffer != null){
			if (!prefix.equals("")){
				buffer.append("<"+prefix+":"+elementName);
			}
			else {
				buffer.append("<"+elementName);
			}
			
			if (!hasAttributes){
				buffer.append("> \n");
			}
		}
		else {
			// TODO : SBML syntax error, throw an exception?
		}
		return contextObject;
	}

	public void processEndDocument(SBMLDocument sbmlDocument) {
		// TODO Auto-generated method stub
		
	}

}
