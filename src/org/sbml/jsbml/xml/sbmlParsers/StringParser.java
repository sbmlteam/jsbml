package org.sbml.jsbml.xml.sbmlParsers;

import java.util.ArrayList;

import org.sbml.jsbml.element.Annotation;
import org.sbml.jsbml.element.Constraint;
import org.sbml.jsbml.element.MathContainer;
import org.sbml.jsbml.element.SBMLDocument;
import org.sbml.jsbml.element.SBase;
import org.sbml.jsbml.xml.SBMLObjectForXML;
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
			String value, String prefix, Object contextObject) {
		
		StringBuffer buffer = getStringBufferFor(contextObject);
		
		if (buffer != null){
			if (!prefix.equals("")){
				buffer.append(" "+prefix+":"+attributeName+"=\""+value+"\"");
			}
			else {
				buffer.append(" "+attributeName+"=\""+value+"\"");
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
			}
		}
	}

	public void processCharactersOf(String elementName, String characters,
			Object contextObject) {
		
		StringBuffer buffer = getStringBufferFor(contextObject);
		
		if (buffer != null){
			buffer.append("> \n"+characters + " \n");
		}
		else {
			if (contextObject instanceof Annotation){
				Annotation annotation = (Annotation) contextObject;
				annotation.setAnnotation("> \n"+characters + " \n");
			}
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
			if (contextObject instanceof Annotation){
				Annotation annotation = (Annotation) contextObject;
				
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

	public Object processStartElement(String elementName, String prefix, Object contextObject) {
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
			}
		}
		return contextObject;
	}

	public void processEndDocument(SBMLDocument sbmlDocument) {
		// TODO Auto-generated method stub
	}

	public void processNamespace(String elementName, String URI, String prefix,
			String localName, Object contextObject) {
		
		StringBuffer buffer = getStringBufferFor(contextObject);

		if (buffer != null){
			if (!prefix.equals("")){
				buffer.append(" "+prefix+":"+localName+"=\""+URI);
			}
			else {
				buffer.append(" "+localName+"=\""+URI);
			}
		}
		else {
			if (contextObject instanceof Annotation){
				Annotation annotation = (Annotation) contextObject;
				if (!prefix.equals("")){
					annotation.setAnnotation(" "+prefix+":"+localName+"=\""+URI);
				}
				else {
					annotation.setAnnotation(" "+localName+"=\""+URI);
				}
			}
		}
	}

	public ArrayList<Object> getListOfSBMLElementsToWrite(Object sbase) {
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
