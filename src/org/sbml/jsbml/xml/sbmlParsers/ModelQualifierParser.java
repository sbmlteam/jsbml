package org.sbml.jsbml.xml.sbmlParsers;

import java.util.HashMap;

import org.sbml.jsbml.element.CVTerm.Qualifier;
import org.sbml.jsbml.element.Annotation;
import org.sbml.jsbml.element.CVTerm;
import org.sbml.jsbml.element.SBMLDocument;
import org.sbml.jsbml.xml.SBMLParser;

public class ModelQualifierParser implements SBMLParser{

	private HashMap<String, Qualifier> modelQualifierMap = new HashMap<String, Qualifier>();

	
	private void initialisesModelQualifierMap(){
		modelQualifierMap.put("is", Qualifier.BQM_IS);
		modelQualifierMap.put("isDescribedBy", Qualifier.BQM_IS_DESCRIBED_BY);
	}
	
	public void processAttribute(String ElementName, String AttributeName,
			String value, String prefix, boolean isLastAttribute,
			Object contextObject) {		
	}

	public void processCharactersOf(String elementName, String characters,
			Object contextObject) {		
	}

	public void processEndElement(String ElementName, String prefix,
			boolean isNested, Object contextObject) {		
	}

	public Object processStartElement(String elementName, String prefix,
			boolean hasAttributes, Object contextObject) {
		initialisesModelQualifierMap();

		if (contextObject instanceof Annotation){
			Annotation annotation = (Annotation) contextObject;
			
			if (modelQualifierMap.containsKey(elementName)){
				CVTerm cvTerm = new CVTerm();
				cvTerm.setQualifierType(Qualifier.MODEL_QUALIFIER);
				cvTerm.setBiologicalQualifierType(modelQualifierMap.get(elementName));
				
				annotation.addCVTerm(cvTerm);
				return cvTerm;
			}
		}
		return contextObject;
	}

	public void processEndDocument(SBMLDocument sbmlDocument) {		
	}

}
