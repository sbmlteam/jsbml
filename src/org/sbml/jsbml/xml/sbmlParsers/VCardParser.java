package org.sbml.jsbml.xml.sbmlParsers;

import org.sbml.jsbml.element.Annotation;
import org.sbml.jsbml.element.ModelCreator;
import org.sbml.jsbml.element.SBMLDocument;
import org.sbml.jsbml.xml.ReadingParser;

public class VCardParser implements ReadingParser{

	private boolean hasReadNNode = false;
	private boolean hasReadFamilyName = false;
	private boolean hasReadGivenName = false;
	private boolean hasReadOrgName = false;
	private boolean hasReadEMAIL = false;
	private boolean hasReadORGNode = false;
	
	public void processAttribute(String elementName, String attributeName,
			String value, String prefix, boolean isLastAttribute, Object contextObject) {
		// TODO : There is no attribute with a namespace "http://www.w3.org/2001/vcard-rdf/3.0#", SBML syntax error.
		// Throw an exception?
	}

	public void processCharactersOf(String elementName, String characters,
			Object contextObject) {
		
		if (elementName != null){
			if (contextObject instanceof ModelCreator){
				ModelCreator modelCreator = (ModelCreator) contextObject;
				
				if (elementName.equals("Family") && hasReadFamilyName){
					modelCreator.setFamilyName(characters);
				}
				else if (elementName.equals("Given") && hasReadGivenName){
					modelCreator.setGivenName(characters);
				}
				else if (elementName.equals("EMAIL") && hasReadEMAIL){
					modelCreator.setEmail(characters);
				}
				else if (elementName.equals("Orgname") && hasReadOrgName){
					modelCreator.setOrganization(characters);
				}
				else {
					// TODO : SBML syntax error, throw an exception?
				}
			}
			else {
				// TODO : SBML syntax error, throw an exception?
			}
		}
	}

	public void processEndElement(String elementName, String prefix,
			boolean isNested, Object contextObject) {	
		
		if (contextObject instanceof ModelCreator){
			if (elementName.equals("N")){
				hasReadNNode = false;
				hasReadFamilyName = false;
				hasReadGivenName = false;
			}
			else if (elementName.equals("EMAIL")){
				hasReadEMAIL = false;
			}
			else if (elementName.equals("ORG")){
				hasReadORGNode = false;
				hasReadOrgName = false;

			}
			else {
				// TODO : SBML syntax error, throw an exception?
			}
		}
		else {
			// TODO : SBML syntax error, throw an exception?
		}
	}

	public Object processStartElement(String elementName, String prefix, boolean hasAttribute, boolean hasNamespaces, Object contextObject) {

		if (contextObject instanceof ModelCreator){
			if (elementName.equals("N") && !hasReadNNode){
				hasReadNNode = true;
			}
			else if (elementName.equals("Family") && hasReadNNode && !hasReadFamilyName && !hasReadGivenName){
				hasReadFamilyName = true;
			}
			else if (elementName.equals("Given") && hasReadNNode && hasReadFamilyName && !hasReadGivenName){
				hasReadGivenName = true;
			}
			else if (elementName.equals("EMAIL") && !hasReadEMAIL){
				hasReadEMAIL = true;
			}
			else if (elementName.equals("ORG") && !hasReadORGNode){
				hasReadORGNode = true;
			}
			else if (elementName.equals("Orgname") && hasReadORGNode && !hasReadOrgName){
				hasReadOrgName = true;
			}
			else {
				// TODO : SBML syntax error, throw an exception?
			}
		}
		else {
			// TODO : SBML syntax error, throw an exception?
		}
		return contextObject;
	}

	public void processEndDocument(SBMLDocument sbmlDocument) {
		hasReadEMAIL = false;
		hasReadFamilyName = false;
		hasReadGivenName = false;
		hasReadNNode = false;
		hasReadORGNode = false;
		hasReadOrgName = false;
	}

	public void processNamespace(String elementName, String URI, String prefix,
			String localName, boolean hasAttributes, boolean isLastNamespace, Object contextObject) {
		
		if (elementName.equals("RDF") && contextObject instanceof Annotation){
			Annotation annotation = (Annotation) contextObject;
			
			annotation.addRDFAnnotationNamespace(localName, prefix, URI);
		}
	}
}
