package org.sbml.jsbml.element;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.sbml.jsbml.element.CVTerm.Qualifier;
//import org.sbml.jsbml.xml.helper.RDFElement;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * An Annotation represents the annotation of a SBase element. It contains the list of CVTerm
 * objects, the map containing the attribute of a XML annotation node and a String containing all the
 * other annotation elements of the XML annotation node.
 * @author compneur
 *
 */
public class Annotation {
	
	/**
	 * contains all the annotation information which are not RDF annotation information
	 */
	private StringBuilder annotation;
	
	/**
	 * contains all the CVTerm of the RDF annotation 
	 */
	private List<CVTerm> listOfCVTerms;
	
	/**
	 * contains all the namespaces of the matching XML annotation node
	 */
	private HashMap<String, String> annotationNamespaces = new HashMap<String, String>();
	
	private HashMap<String, Annotation> extensions = new HashMap<String, Annotation>();
	
	/**
	 * contains all the namespaces of the matching XML RDF annotation node
	 */
	private HashMap<String, String> rdfAnnotationNamespaces = new HashMap<String, String>();
	
	private String about;
	
	/**
	 * The ModelHistory which represents the history section of a RDF annotation
	 */
	private ModelHistory modelHistory;

	/**
	 * 
	 * @param annotation
	 * @param cvTerms
	 */
	public Annotation(String annotation, List<CVTerm> cvTerms){
		this.annotation = new StringBuilder(annotation);
		this.listOfCVTerms = cvTerms;
		this.modelHistory = null;
	}
	
	/**
	 * 
	 * @param annotation
	 */
	public Annotation(String annotation){
		this.annotation = new StringBuilder(annotation);
		this.listOfCVTerms = new LinkedList<CVTerm>();
		this.modelHistory = null;
	}
	
	/**
	 * 
	 * @param cvTerms
	 */
	public Annotation(List<CVTerm> cvTerms){
		this.annotation = null;
		this.listOfCVTerms = cvTerms;
		this.modelHistory = null;
	}
	
	/**
	 * 
	 */
	public Annotation(){
		this.annotation = null;
		this.listOfCVTerms = new LinkedList<CVTerm>();
		this.modelHistory = null;
	}
	
	/**
	 * 
	 * @param attributes
	 */
	public Annotation(HashMap<String, String> attributes){
		this.annotation = null;
		this.listOfCVTerms = new LinkedList<CVTerm>();
		this.annotationNamespaces = attributes;
		this.modelHistory = null;
	}
	
	/**
	 * 
	 * @param annotation
	 */
	public void setAnnotation(String annotation) {
		if (this.annotation == null){
			this.annotation = new StringBuilder(annotation);
		}
		else{
			this.annotation.append(annotation);
		}
	}
	
	/**
	 * 
	 * @return the annotation String containing other annotations than RDF annotations
	 */
	public String getAnnotation() {
		if (annotation != null){
			return annotation.toString();
		}
		return null;
	}
	
	public StringBuilder getAnnotationBuilder(){
		return this.annotation;
	}
	
	/**
	 * 
	 * @return the list of CVTerms
	 */
	public List<CVTerm> getListOfCVTerms() {
		return listOfCVTerms;
	}
	
	/**
	 * 
	 * @param term
	 * @return true if the 'term' element has been added to the list of CVTerms
	 */
	public boolean addCVTerm(CVTerm term) {
		return listOfCVTerms.add(term);
	}
	
	/**
	 * 
	 * @param i
	 * @return the CVTerm at the ith position in the list of CVTerms
	 */
	public CVTerm getCVTerm(int i) {
		return listOfCVTerms.get(i);
	}
	
	/**
	 * changes the modelHistory variable
	 * @param modelHistory
	 */
	public void setModelHistory(ModelHistory modelHistory) {
		this.modelHistory = modelHistory;
	}

	/**
	 * 
	 * @return the modelHistory of the ModelAnnotation
	 */
	public ModelHistory getModelHistory() {
		return modelHistory;
	}
	
	/**
	 * check if the modelHistory is initialised
	 * @return true if the modelHistory is initialised
	 */
	public boolean isSetModelHistory() {
		return modelHistory != null;
	}

	
	/**
	 * Checks if the Annotation is initialised. An Annotation is initialised
	 * if at less one of the following variables is not null : the annotation String,
	 * one CVTerm object of the list of CVTerms or the ModelHistory.
	 * @return true if the Annotation is initialised
	 */
	public boolean isSetAnnotation() {
		if (getAnnotation() == null && getListOfCVTerms() == null){
			return false;
		}
		else if (getAnnotation() == null && getListOfCVTerms() != null){
			
			for (int i = 0; i < getListOfCVTerms().size(); i++){
				if (getCVTerm(i) != null){
					return true;
				}
			}
			return false;
		}
		else if (getAnnotation() != null && getListOfCVTerms() == null){
			if (getAnnotation().length() == 0){
				return false;
			}
			return true;
		}
		else if (isSetModelHistory()) {
			return true;
		}
		else {
			boolean isOneCVTermNotNull = false;

			for (int i = 0; i < getListOfCVTerms().size(); i++){
				if (getCVTerm(i) != null){
					isOneCVTermNotNull = true;
				}
			}
			
			if (!isOneCVTermNotNull && getAnnotation().length() == 0){
				return false;
			}
			return false;
		}
	}
	
	/**
	 * set the annotation String to null
	 */
	public void unsetAnnotation() {
		if (isSetAnnotation())
			annotation = null;
	}

	/**
	 * clear the list of CVTerms
	 */
	public void unsetCVTerms() {
		listOfCVTerms.clear();
	}
	
	/**
	 * writes the beginning of the RDF annotation element in 'buffer'
	 * @param indent
	 * @param buffer
	 * @param parentElement
	 */
	protected void beginRDFAnnotationElement(String indent, StringBuffer buffer, SBase parentElement){
		
		if (parentElement != null){
			String metaid = parentElement.getMetaId();
			
			if (metaid != null){
				buffer.append(indent).append("<rdf:RDF \n");
	/*			buffer.append(indent).append("         xmlns:rdf=").append('"').append(RDFElement.getRDFNamespaces().get("rdf")).append('"').append(" \n");
				buffer.append(indent).append("         xmlns:dc=").append('"').append(RDFElement.getRDFNamespaces().get("dc")).append('"').append(" \n");
				buffer.append(indent).append("         xmlns:dcterms=").append('"').append(RDFElement.getRDFNamespaces().get("dcterms")).append('"').append(" \n");
				buffer.append(indent).append("         xmlns:vCard=").append('"').append(RDFElement.getRDFNamespaces().get("vcard")).append('"').append(" \n");
				buffer.append(indent).append("         xmlns:bqbiol=").append('"').append(RDFElement.getRDFNamespaces().get("bqbiol")).append('"').append(" \n");
				buffer.append(indent).append("         xmlns:bqmodel=").append('"').append(RDFElement.getRDFNamespaces().get("bqmodel")).append('"').append(" \n");
				buffer.append(indent).append("> \n");
				*/
				buffer.append(indent).append("  <rdf:Description rdf:about=").append('"').append('#').append(metaid).append('"').append("> \n");
			}
		}
		
	}
	
	/**
	 * writes the end of the RDF annotation element in 'buffer'
	 * @param indent
	 * @param buffer
	 * @param parentElement
	 */
	protected void endRDFAnnotationElement(String indent, StringBuffer buffer, SBase parentElement){
		
		if (parentElement != null){
			String metaid = parentElement.getMetaId();
			
			if (metaid != null){
				buffer.append(indent).append("  </rdf:Description> \n");
				buffer.append(indent).append("</rdf:RDF> \n");
			}
		}
	}
	
	/**
	 * 
	 * @param qualifier
	 * @return String which represents the Qualifier qualifier in a XML node
	 */
	public static String getElementNameEquivalentToQualifier(Qualifier qualifier){
		String stringQualifier = null;
		
		switch (qualifier) {
		case BQB_ENCODES:
			stringQualifier = "encodes";
			break;
		case BQB_HAS_PART:
			stringQualifier = "hasPart";
			break;
		case BQB_HAS_VERSION:
			stringQualifier = "hasVersion";
			break;
		case BQB_IS:
			stringQualifier = "is";
			break;
		case BQB_IS_DESCRIBED_BY:
			stringQualifier = "isDescribedBy";
			break;
		case BQB_IS_ENCODED_BY:
			stringQualifier = "isEncodedBy";
			break;
		case BQB_IS_HOMOLOG_TO:
			stringQualifier = "isHomologTo";
			break;
		case BQB_IS_PART_OF:
			stringQualifier = "isPartOf";
			break;
		case BQB_IS_VERSION_OF:
			stringQualifier = "isVersionOf";
			break;
		case BQB_OCCURS_IN:
			stringQualifier = "occursIn";
			break;
		case BQM_IS:
			stringQualifier = "is";
			break;
		case BQM_IS_DESCRIBED_BY:
			stringQualifier = "isDescribedBy";
			break;
		default:
			break;
		}
		
		return stringQualifier;
	}
	
	/**
	 * Writes the CV term elements in 'buffer'
	 * @param indent
	 * @param buffer
	 */
	protected void createCVTermsElements(String indent, StringBuffer buffer){
		
		if (getListOfCVTerms() != null){
			
			for (int i = 0; i < getListOfCVTerms().size(); i++){
				CVTerm cvTerm = getCVTerm(i);
				Qualifier qualifierType = cvTerm.getQualifierType();
				Qualifier qualifier = Qualifier.UNKNOWN_QUALIFIER;
				String prefix = null;
				
				if (qualifierType.equals(Qualifier.BIOLOGICAL_QUALIFIER)){
					qualifier = cvTerm.getBiologicalQualifierType();
					prefix = "bqbiol";
				}
				else if (qualifierType.equals(Qualifier.MODEL_QUALIFIER)){
					qualifier = cvTerm.getModelQualifierType();
					prefix = "bqmodel";
				}
				
				String stringQualifier = getElementNameEquivalentToQualifier(qualifier);
					
				if (prefix != null && stringQualifier != null){
					buffer.append(indent).append("<").append(prefix).append(":").append(stringQualifier).append("> \n");
					buffer.append(indent).append("  <rdf:Bag> \n");
					
					cvTerm.toXML(indent + "    ", buffer);
					
					buffer.append(indent).append("  </rdf:Bag> \n");
					buffer.append(indent).append("</").append(prefix).append(":").append(stringQualifier).append("> \n");
				}
			}
		}
	}
	
	/**
	 * Writes the RDF annotation element in 'buffer'
	 * 
	 * @param indent
	 * @param buffer
	 * @param parentElement
	 */
	protected void RDFAnnotationToXML(String indent, StringBuffer buffer, SBase parentElement){
		
		beginRDFAnnotationElement(indent, buffer, parentElement);
		
		modelHistoryToXML(indent + "    ", buffer);
		createCVTermsElements(indent + "    ", buffer);
		
		endRDFAnnotationElement(indent, buffer, parentElement);
	}
	
	/**
	 * Writes the other annotation elements in 'buffer'
	 * 
	 * @param indent
	 * @param buffer
	 */
	protected void otherAnnotationToXML(String indent, StringBuffer buffer){
		
		String [] lines = getAnnotation().split("\n");
		for (int i = 0; i < lines.length; i++){
			buffer.append(indent).append(lines[i]).append(" \n");
		}
	}
	
	/**
	 * Writes the history section of the RDF annotation in 'buffer'
	 * 
	 * @param indent
	 * @param buffer
	 */
	private void modelHistoryToXML(String indent, StringBuffer buffer){
		if (isSetModelHistory()){
			getModelHistory().toXML(indent, buffer);
		}
	}
	
	/**
	 * 
	 * @return String containing the attributes of the annotation node
	 */
	private String attributesToXML(){
		StringBuffer attributes = new StringBuffer();
		
		Iterator<Map.Entry<String, String>> iterator = getAnnotationAttributes().entrySet().iterator();
		
		for (Iterator<Map.Entry<String, String>> it = iterator; it.hasNext();){
			
			Map.Entry<String, String> entry = it.next();
			attributes.append(" ").append(entry.getKey()).append("=").append('"').append(entry.getValue()).append('"');
		}
		
		return attributes.toString();
	}
	
	/**
	 * converts the Annotation into an XML annotation element
	 * @param indent
	 * @param parentElement
	 * @return
	 */
	public String toXML(String indent, SBase parentElement){
		
		StringBuffer buffer = new StringBuffer();
		
		if (isSetAnnotation()){
			buffer.append(indent).append("<annotation").append(attributesToXML()).append("> \n");
			
			if (getListOfCVTerms() != null){
				RDFAnnotationToXML(indent + "  ", buffer, parentElement);
			}
			if (getAnnotation() != null){
				otherAnnotationToXML(indent + "  ", buffer);
			}
			buffer.append(indent).append("</annotation> \n");
		}
		return buffer.toString();
	}

	/**
	 * changes the annotation attributes with 'annotationNamespaces'
	 * @param annotationNamespaces
	 */
	public void setAnnotationAttributes(HashMap<String, String> annotationAttributes) {
		this.annotationNamespaces = annotationAttributes;
	}
	
	/**
	 * changes the annotation attributes with 'annotationNamespaces'
	 * @param annotationNamespaces
	 */
	public void setAnnotationAttributes(NamedNodeMap annotationAttributes) {
		
		if (annotationAttributes != null){
			
			for (int i = 0; i < annotationAttributes.getLength(); i++){
				Node attribute = annotationAttributes.item(i);
				
				getAnnotationAttributes().put(attribute.getNodeName(), attribute.getNodeValue());
			}
		}
	}

	/**
	 * 
	 * @return the map containing the annotation attributes of this Annotation
	 */
	public HashMap<String, String> getAnnotationAttributes() {
		return annotationNamespaces;
	}
	
	public boolean readAttribute(String attributeName, String prefix, String value){
		
		if (attributeName.equals("about")){
			setAbout(value);
			return true;
		}
		return false;
	}
	
	public void addAnnotationNamespace(String namespaceName, String prefix, String URI){
		if (!prefix.equals("")){
			this.annotationNamespaces.put(prefix+":"+namespaceName, URI);
		}
		else {
			this.annotationNamespaces.put(namespaceName, URI);
		}
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getAbout() {
		return about;
	}

	public void setRdfAnnotationNamespaces(HashMap<String, String> rdfAnnotationNamespaces) {
		this.rdfAnnotationNamespaces = rdfAnnotationNamespaces;
	}

	public HashMap<String, String> getRdfAnnotationNamespaces() {
		return rdfAnnotationNamespaces;
	}
	
	public HashMap<String, String> getAnnotationNamespaces() {
		return annotationNamespaces;
	}

	public void addRDFAnnotationNamespace(String namespaceName, String prefix, String URI){
		if (!prefix.equals("")){
			this.rdfAnnotationNamespaces.put(prefix+":"+namespaceName, URI);
		}
		else {
			this.rdfAnnotationNamespaces.put(namespaceName, URI);
		}
	}
	
	public Annotation getExtension(String namespace){
		return this.extensions.get(namespace);
	}
	
	public void addExtension(String namespace, Annotation annotation){
		this.extensions.put(namespace, annotation);
	}
	
	public Set<String> getNamespaces(){
		return this.extensions.keySet();
	}
}
