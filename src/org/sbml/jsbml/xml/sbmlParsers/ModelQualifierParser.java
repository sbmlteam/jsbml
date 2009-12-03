package org.sbml.jsbml.xml.sbmlParsers;

import java.util.HashMap;

import org.sbml.jsbml.element.CVTerm.Qualifier;
import org.sbml.jsbml.element.Annotation;
import org.sbml.jsbml.element.CVTerm;
import org.sbml.jsbml.element.SBMLDocument;
import org.sbml.jsbml.xml.ReadingParser;

public class ModelQualifierParser implements ReadingParser{

	private HashMap<String, Qualifier> modelQualifierMap = new HashMap<String, Qualifier>();

	public ModelQualifierParser(){
		initialisesModelQualifierMap();
	}
	
	private void initialisesModelQualifierMap(){
		modelQualifierMap.put("is", Qualifier.BQM_IS);
		modelQualifierMap.put("isDescribedBy", Qualifier.BQM_IS_DESCRIBED_BY);
	}

	public void processCharactersOf(String elementName, String characters,
			Object contextObject) {	
		// TODO : a node with the namespace "http://biomodels.net/model-qualifiers/" can't have text.
		// Throw an error?
	}

	public void processEndElement(String ElementName, String prefix,
			boolean isNested, Object contextObject) {		
	}

	public Object processStartElement(String elementName, String prefix,
			boolean hasAttributes, boolean hasNamespaces,
			Object contextObject) {

		if (contextObject instanceof Annotation){
			Annotation annotation = (Annotation) contextObject;
			
			if (modelQualifierMap.containsKey(elementName)){
				CVTerm cvTerm = new CVTerm();
				cvTerm.setQualifierType(Qualifier.MODEL_QUALIFIER);
				cvTerm.setModelQualifierType(modelQualifierMap.get(elementName));
				
				annotation.addCVTerm(cvTerm);
				return cvTerm;
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
	}

	public void processNamespace(String elementName, String URI, String prefix,
			String localName, boolean hasAttributes, boolean isLastNamespace,
			Object contextObject) {
		
		if (elementName.equals("RDF") && contextObject instanceof Annotation){
			Annotation annotation = (Annotation) contextObject;
			
			annotation.addRDFAnnotationNamespace(localName, prefix, URI);
		}
	}

	public void processAttribute(String ElementName, String AttributeName,
			String value, String prefix, boolean isLastAttribute,
			Object contextObject) {
		// TODO : a node with the namespace "http://biomodels.net/model-qualifiers/" can't have attributes.
		// Throw an error?
	}
}
