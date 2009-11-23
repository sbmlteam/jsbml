package org.sbml.jsbml.xml.sbmlParsers;

import java.util.Date;

import org.sbml.jsbml.element.Annotation;
import org.sbml.jsbml.element.ModelHistory;
import org.sbml.jsbml.element.SBMLDocument;
import org.sbml.jsbml.xml.DateProcessor;
import org.sbml.jsbml.xml.SBMLParser;
import org.w3c.util.DateParser;
import org.w3c.util.InvalidDateException;

public class DatesParser implements SBMLParser{
	
	private String previousElement = "";
	private boolean hasReadCreated = false;
	boolean hasReadW3CDTF = false;

	public void processAttribute(String ElementName, String AttributeName,
			String value, String prefix, boolean isLastAttribute,
			Object contextObject) {
		// TODO : There is no attributes with the namespace "http://purl.org/dc/terms/". There is a SBML
		// syntax error, throw an exception?
	}

	public void processCharactersOf(String elementName, String characters,
			Object contextObject) {
		
		if (contextObject instanceof ModelHistory){
			ModelHistory modelHistory = (ModelHistory) contextObject;
			DateProcessor dateProcessor = new DateProcessor();
			
			if (elementName.equals("W3CDTF") && hasReadW3CDTF){
				if (hasReadCreated && previousElement.equals("created")){
					String stringDate = dateProcessor.formatToW3CDTF(characters);

					try {
						Date createdDate = DateParser.parse(stringDate);
						modelHistory.setCreatedDate(createdDate);
					} catch (InvalidDateException e) {
						// TODO : can't create a Date, what to do?
						e.printStackTrace();
					}
				}
				else if (previousElement.equals("modified")){
					String stringDate = dateProcessor.formatToW3CDTF(characters);

					try {
						Date modifiedDate = DateParser.parse(stringDate);
						modelHistory.setModifiedDate(modifiedDate);
					} catch (InvalidDateException e) {
						// TODO : can't create a Date, what to do?
						e.printStackTrace();
					}
				}
				else {
					// TODO : SBML syntax error, what to do?
				}
			}
			else {
				// TODO : SBML syntax error, what to do?
			}
		}
		else {
			// TODO : the date instances are only created for the model history object in the annotation. Throw an error?
		}
	}

	public void processEndElement(String elementName, String prefix,
			boolean isNested, Object contextObject) {
		
		if (contextObject instanceof ModelHistory){
			if (elementName.equals("created") || elementName.equals("modified")){
				this.previousElement = "";
				hasReadW3CDTF = false;
			}
			else {
				// TODO : the date instances are only created for the created and/or modified nodes in the annotation. Throw an error?
			}
		}
		else {
			// TODO : the date instances are only created for the model history object in the annotation. Throw an error?
		}
	}

	public Object processStartElement(String elementName, String prefix,
			boolean hasAttributes, Object contextObject) {

		if (contextObject instanceof Annotation){
			Annotation modelAnnotation = (Annotation) contextObject;

			if (modelAnnotation.isSetModelHistory()){
				ModelHistory modelHistory = modelAnnotation.getModelHistory();

				if (elementName.equals("created") && !hasReadCreated){
					hasReadCreated = true;
					this.previousElement = elementName;
					
					return modelHistory;
				}
				else if (elementName.equals("modified")){
					this.previousElement = elementName;
					
					return modelHistory;
				}
				else {
					// TODO : SBML syntax error, what to do?
				}
			}
			else {
				// TODO : create a modelHistory instance? throw an exception?
			}
		}
		else if (contextObject instanceof ModelHistory){
			
			if (elementName.equals("W3CDTF") && (previousElement.equals("created") || previousElement.equals("modified"))){
				hasReadW3CDTF = true;
			}
		}
		else {
			// TODO : should be changed depending on the version. Now, there is not only the model which contain a model history. 
		}
		return contextObject;
	}

	public void processEndDocument(SBMLDocument sbmlDocument) {
		previousElement = "";
		hasReadCreated = false;
	}

}
