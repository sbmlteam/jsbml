package org.sbml.jsbml.xml.sbmlParsers;

import java.util.ArrayList;

import org.sbml.jsbml.element.Annotation;
import org.sbml.jsbml.element.ListOf;
import org.sbml.jsbml.element.SBMLDocument;
import org.sbml.jsbml.element.SBase;
import org.sbml.jsbml.element.Species;
import org.sbml.jsbml.multiTest.InitialSpeciesInstance;
import org.sbml.jsbml.multiTest.MultiList;
import org.sbml.jsbml.multiTest.MultiSpecies;
import org.sbml.jsbml.xml.CurrentListOfSBMLElements;
import org.sbml.jsbml.xml.SBMLObjectForXML;
import org.sbml.jsbml.xml.SBMLParser;

public class MultiParser implements SBMLParser{
	
	private final String namespace = "http://www.sbml.org/sbml/level3/version1/multi/version1";
	
	private MultiList multiList = MultiList.none;

	public void processAttribute(String elementName, String attributeName,
			String value, String prefix, boolean isLastAttribute,
			Object contextObject) {
		
		boolean isAttributeRead = false;
		if (contextObject instanceof SBase){
			SBase sbase = (SBase) contextObject;
			isAttributeRead = sbase.readAttribute(attributeName, prefix, value);
		}
		else if (contextObject instanceof Annotation){
			Annotation annotation = (Annotation) contextObject;
			isAttributeRead = annotation.readAttribute(attributeName, prefix, value);
		}
		
		if (!isAttributeRead){
			// TODO : throw new SBMLException ("The attribute " + attributeName + " on the element " + elementName + "is not part of the SBML specifications");
		}
	}

	public void processCharactersOf(String elementName, String characters,
			Object contextObject) {
	}

	public Object processStartElement(String elementName, String prefix,
			boolean hasAttributes, boolean hasNamespaces,
			Object contextObject) {
		
		if (contextObject instanceof Species){
			Species species = (Species) contextObject;
			if (elementName.equals("listOfInitialSpeciesInstances")){
				ListOf<InitialSpeciesInstance> listOfInitialSpeciesInstances = new ListOf<InitialSpeciesInstance>();
				listOfInitialSpeciesInstances.setCurrentList(CurrentListOfSBMLElements.other);
				this.multiList = MultiList.listOfInitialSpeciesInstances;
				
				MultiSpecies multiSpecies = new MultiSpecies(species);
				multiSpecies.setListOfInitialSpeciesInstance(listOfInitialSpeciesInstances);
				species.addExtension(this.namespace, multiSpecies);
				
				return listOfInitialSpeciesInstances;
			}
		}
		else if (contextObject instanceof ListOf){
			ListOf<SBase> listOf = (ListOf<SBase>) contextObject;
			
			if (elementName.equals("initialSpeciesInstance") && this.multiList.equals(MultiList.listOfInitialSpeciesInstances)){
				InitialSpeciesInstance initialSpeciesInstance = new InitialSpeciesInstance();
				listOf.add(initialSpeciesInstance);
				
				return initialSpeciesInstance;
			}
			
		}
		return contextObject;
	}

	public void processEndElement(String elementName, String prefix,
			boolean isNested, Object contextObject) {
		
		if (elementName.equals("listOfSpeciesInstances")){
			this.multiList = MultiList.none;
		}
	}

	public void processEndDocument(SBMLDocument sbmlDocument) {
		// TODO Auto-generated method stub
		
	}

	public String getNamespace() {
		return namespace;
	}

	public ArrayList<Object> getListOfSBMLElementsToWrite(Object sbase) {
		// TODO Auto-generated method stub
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

	public void processNamespace(String elementName, String URI, String prefix,
			String localName, boolean hasAttributes, boolean isLastNamespace,
			Object contextObject) {
		// TODO Auto-generated method stub
		
	}

}
