package org.sbml.jsbml.xml.sbmlParsers;

import java.util.ArrayList;
import java.util.HashMap;

import org.sbml.jsbml.element.CVTerm.Qualifier;
import org.sbml.jsbml.element.Annotation;
import org.sbml.jsbml.element.CVTerm;
import org.sbml.jsbml.element.SBMLDocument;
import org.sbml.jsbml.xml.SBMLObjectForXML;
import org.sbml.jsbml.xml.SBMLParser;

public class BiologicalQualifierParser implements SBMLParser{
	
	private HashMap<String, Qualifier> biologicalQualifierMap = new HashMap<String, Qualifier>();

	public BiologicalQualifierParser(){
		initialisesBiologicalQualifierMap();
	}
	
	private void initialisesBiologicalQualifierMap(){
		biologicalQualifierMap.put("encodes", Qualifier.BQB_ENCODES);
		biologicalQualifierMap.put("hasPart", Qualifier.BQB_HAS_PART);
		biologicalQualifierMap.put("hasVersion", Qualifier.BQB_HAS_VERSION);
		biologicalQualifierMap.put("is", Qualifier.BQB_IS);
		biologicalQualifierMap.put("isDescribedBy", Qualifier.BQB_IS_DESCRIBED_BY);
		biologicalQualifierMap.put("isEncodedBy", Qualifier.BQB_IS_ENCODED_BY);
		biologicalQualifierMap.put("isHomologTo", Qualifier.BQB_IS_HOMOLOG_TO);
		biologicalQualifierMap.put("isPartOf", Qualifier.BQB_IS_PART_OF);
		biologicalQualifierMap.put("isVersionOf", Qualifier.BQB_IS_VERSION_OF);
		biologicalQualifierMap.put("occursIn", Qualifier.BQB_OCCURS_IN);
	}
	
	public void processCharactersOf(String elementName, String characters,
			Object contextObject) {
		// TODO : a node with a biological qualifier can't have text, there is a SBML syntax error, throw an exception?
	}

	public void processEndElement(String ElementName, String prefix,
			boolean isNested, Object contextObject) {		
	}

	public Object processStartElement(String elementName, String prefix,
			boolean hasAttributes, boolean hasNamespaces,
			Object contextObject) {
		
		if (contextObject instanceof Annotation){
			Annotation annotation = (Annotation) contextObject;
			
			if (biologicalQualifierMap.containsKey(elementName)){
				CVTerm cvTerm = new CVTerm();
				cvTerm.setQualifierType(Qualifier.BIOLOGICAL_QUALIFIER);
				cvTerm.setBiologicalQualifierType(biologicalQualifierMap.get(elementName));
				
				annotation.addCVTerm(cvTerm);
				return cvTerm;
			}
			else {
				// TODO : SBML syntax error, throw an error?
			}
		}
		else {
			// TODO the context object of a biological qualifier node should be an annotation instance, there is
			// a SBML syntax error, throw an exception?
		}
		return contextObject;
	}

	public void processEndDocument(SBMLDocument sbmlDocument) {
	}

	public void processNamespace(String elementName, String URI, String prefix,
			String localName, boolean hasAttributes, boolean isLastNamespace,
			Object contextObject) {
		
		if (elementName.equals("RDF") && contextObject instanceof Annotation){
			Annotation annotation = (Annotation) contextObject;
			
			annotation.addRDFAnnotationNamespace(localName, prefix, URI);
		}
	}

	public ArrayList<Object> getListOfSBMLElementsToWrite(Object objectToWrite) {
		return null;
	}

	public void writeElement(SBMLObjectForXML xmlObject, Object sbmlElementToWrite) {
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

	public void processAttribute(String ElementName, String AttributeName,
			String value, String prefix, boolean isLastAttribute,
			Object contextObject) {
		// TODO : a node with a biological qualifier can't have attributes, there is a SBML syntax error, throw an exception?
		
	}
}
