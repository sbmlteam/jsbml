package org.sbml.jsbml.xml.sbmlParsers;

import java.util.Date;

import org.sbml.jsbml.element.ModelAnnotation;
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
						e.printStackTrace();
					}
				}
			}
			else if (previousElement.equals("modified")){
				String stringDate = dateProcessor.formatToW3CDTF(characters);

				try {
					Date modifiedDate = DateParser.parse(stringDate);
					modelHistory.setModifiedDate(modifiedDate);
				} catch (InvalidDateException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void processEndElement(String elementName, String prefix,
			boolean isNested, Object contextObject) {
		
		if (contextObject instanceof ModelHistory){
			if (elementName.equals("created") || elementName.equals("modified")){
				this.previousElement = "";
				hasReadW3CDTF = false;
			}
		}
		
	}

	public Object processStartElement(String elementName, String prefix,
			boolean hasAttributes, Object contextObject) {

		if (contextObject instanceof ModelAnnotation){
			ModelAnnotation modelAnnotation = (ModelAnnotation) contextObject;
			
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
				else if (elementName.equals("W3CDTF") && (previousElement.equals("created") || previousElement.equals("modified"))){
					hasReadW3CDTF = true;
				}
			}
		}
		return contextObject;
	}

	public void processEndDocument(SBMLDocument sbmlDocument) {
		previousElement = "";
		hasReadCreated = false;
	}

}
