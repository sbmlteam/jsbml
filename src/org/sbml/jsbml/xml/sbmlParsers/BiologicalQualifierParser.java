package org.sbml.jsbml.xml.sbmlParsers;

import java.util.HashMap;

import org.sbml.jsbml.element.CVTerm.Qualifier;
import org.sbml.jsbml.element.Annotation;
import org.sbml.jsbml.element.CVTerm;
import org.sbml.jsbml.element.SBMLDocument;
import org.sbml.jsbml.xml.SBMLParser;

public class BiologicalQualifierParser implements SBMLParser{
	
	private HashMap<String, Qualifier> biologicalQualifierMap = new HashMap<String, Qualifier>();

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
		initialisesBiologicalQualifierMap();

		if (contextObject instanceof Annotation){
			Annotation annotation = (Annotation) contextObject;
			
			if (biologicalQualifierMap.containsKey(elementName)){
				CVTerm cvTerm = new CVTerm();
				cvTerm.setQualifierType(Qualifier.BIOLOGICAL_QUALIFIER);
				cvTerm.setBiologicalQualifierType(biologicalQualifierMap.get(elementName));
				
				annotation.addCVTerm(cvTerm);
				return cvTerm;
			}
		}
		return contextObject;
	}

	public void processEndDocument(SBMLDocument sbmlDocument) {
	}

}
