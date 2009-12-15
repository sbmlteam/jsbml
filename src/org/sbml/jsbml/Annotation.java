/*
 * $Id$
 * $URL$
 *
 *
 *==================================================================================
 * Copyright (c) 2009 the copyright is held jointly by the individual
 * authors. See the file AUTHORS for the list of authors.
 *
 * This file is part of jsbml, the pure java SBML library. Please visit
 * http://sbml.org for more information about SBML, and http://jsbml.sourceforge.net/
 * to get the latest version of jsbml.
 *
 * jsbml is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * jsbml is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with jsbml.  If not, see <http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html>.
 *
 *===================================================================================
 *
 */

package org.sbml.jsbml;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.sbml.jsbml.CVTerm.Qualifier;
//import org.sbml.jsbml.xml.helper.RDFElement;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * An Annotation represents the otherAnnotation of a SBase element. It contains the list of CVTerm
 * objects, the map containing the attribute of a XML otherAnnotation node and a String containing all the
 * other otherAnnotation elements of the XML otherAnnotation node.
 * @author marine
 *
 */
public class Annotation {
	
	/**
	 * contains all the otherAnnotation information which are not RDF otherAnnotation information
	 */
	private StringBuilder otherAnnotation;
	
	/**
	 * contains all the CVTerm of the RDF otherAnnotation 
	 */
	private List<CVTerm> listOfCVTerms;
	
	/**
	 * contains all the namespaces of the matching XML otherAnnotation node
	 */
	private HashMap<String, String> annotationNamespaces = new HashMap<String, String>();
	
	/**
	 * contains all the otherAnnotation extension objects with the namespace of their package.
	 */
	private HashMap<String, Annotation> extensions = new HashMap<String, Annotation>();
	
	/**
	 * contains all the namespaces of the matching XML RDF otherAnnotation node
	 */
	private HashMap<String, String> rdfAnnotationNamespaces = new HashMap<String, String>();
	
	/**
	 * matches the about XML attribute of an otherAnnotation element in a SBML file.
	 */
	private String about;
	
	/**
	 * The ModelHistory which represents the history section of a RDF otherAnnotation
	 */
	private ModelHistory modelHistory;

	/**
	 * Creates an Annotation instance from otherAnnotation and cvTerms. By default, the modelHistory is null. The HashMaps
	 * annotationNamespaces, rdfAnnotationNamespaces and extensions are empty.
	 * @param otherAnnotation
	 * @param cvTerms
	 */
	public Annotation(String annotation, List<CVTerm> cvTerms){
		this.otherAnnotation = new StringBuilder(annotation);
		this.annotationNamespaces = new HashMap<String, String>();
		this.rdfAnnotationNamespaces = new HashMap<String, String>();
		this.extensions = new HashMap<String, Annotation>();
		this.listOfCVTerms = cvTerms;
		this.modelHistory = null;
	}
	
	/**
	 * Creates an Annotation instance from otherAnnotation. By default, the modelHistory is null, the list of CVTerms is empty. The HashMaps
	 * annotationNamespaces, rdfAnnotationNamespaces and extensions are empty.
	 * @param otherAnnotation
	 */
	public Annotation(String annotation){
		this.annotationNamespaces = new HashMap<String, String>();
		this.rdfAnnotationNamespaces = new HashMap<String, String>();
		this.extensions = new HashMap<String, Annotation>();
		this.otherAnnotation = new StringBuilder(annotation);
		this.listOfCVTerms = new LinkedList<CVTerm>();
		this.modelHistory = null;
	}
	
	/**
	 * Creates an Annotation instance from cvTerms. By default, the modelHistory and otherAnnotation Strings are null. The HashMaps
	 * annotationNamespaces, rdfAnnotationNamespaces and extensions are empty.
	 * @param cvTerms
	 */
	public Annotation(List<CVTerm> cvTerms){
		this.annotationNamespaces = new HashMap<String, String>();
		this.rdfAnnotationNamespaces = new HashMap<String, String>();
		this.extensions = new HashMap<String, Annotation>();
		this.otherAnnotation = null;
		this.listOfCVTerms = cvTerms;
		this.modelHistory = null;
	}
	
	/**
	 * Creates an Annotation instance. By default, the modelHistory and otherAnnotation Strings are null. The list of CVTerms is empty. The HashMaps
	 * annotationNamespaces, rdfAnnotationNamespaces and extensions are empty.
	 * @param cvTerms
	 */
	public Annotation(){
		this.annotationNamespaces = new HashMap<String, String>();
		this.rdfAnnotationNamespaces = new HashMap<String, String>();
		this.extensions = new HashMap<String, Annotation>();
		this.otherAnnotation = null;
		this.listOfCVTerms = new LinkedList<CVTerm>();
		this.modelHistory = null;
	}
	
	/**
	 * Creates an Annotation instance from attributes. By default, the modelHistory and otherAnnotation Strings are null. The list of CVTerms is empty. The HashMaps
	 * annotationNamespaces, rdfAnnotationNamespaces and extensions are empty.
	 * @param attributes
	 */
	public Annotation(HashMap<String, String> attributes){
		this.annotationNamespaces = new HashMap<String, String>();
		this.rdfAnnotationNamespaces = new HashMap<String, String>();
		this.extensions = new HashMap<String, Annotation>();
		this.otherAnnotation = null;
		this.listOfCVTerms = new LinkedList<CVTerm>();
		this.annotationNamespaces = attributes;
		this.modelHistory = null;
	}
	
	/**
	 * Appends 'annotation' to the otherAnnotation StringBuilder of this object.
	 * @param otherAnnotation
	 */
	public void appendNoRDFAnnotation(String annotation) {
		if (this.otherAnnotation == null){
			this.otherAnnotation = new StringBuilder(annotation);
		}
		else{
			this.otherAnnotation.append(annotation);
		}
	}
	
	/**
	 * Checks if this object is equal to 'annotation'
	 * @param annotation
	 * @return true if this object entirely matches 'annotation'
	 */
	public boolean equals(Annotation annotation){
		boolean equals = isSetAnnotation() == annotation.isSetAnnotation(); 
		if (equals && isSetOtherAnnotationThanRDF()){
			equals = otherAnnotation.equals(annotation.getNoRDFAnnotation());
		}
		equals &= isSetModelHistory() == annotation.isSetModelHistory();
		if (equals && isSetModelHistory()){
			equals = this.modelHistory.equals(annotation.getModelHistory());
		}
		equals &= getListOfCVTerms().isEmpty() == annotation.getListOfCVTerms().isEmpty();
		if (equals && !getListOfCVTerms().isEmpty()){
			if (listOfCVTerms.size() == annotation.getListOfCVTerms().size()){
				for (int i = 0; i < listOfCVTerms.size(); i++){
					CVTerm cvTerm1 = listOfCVTerms.get(i);
					CVTerm cvTerm2 = annotation.getListOfCVTerms().get(i);
					
					if (cvTerm1 != null && cvTerm2 != null){
						equals &= cvTerm1.equals(cvTerm2);
						if (!equals){
							return false;
						}
					}
					else if ((cvTerm1 == null && cvTerm2 != null) || (cvTerm2 == null && cvTerm1 != null)) {
						return false;
					}
				}
			}
			else {
				return false;
			}
		}
		
		return equals;
	}
	
	/**
	 * 
	 * @return the otherAnnotation String containing annotations other than RDF annotation. Return null
	 * if otherAnnotation is null.
	 */
	public String getNoRDFAnnotation() {
		if (otherAnnotation != null){
			return otherAnnotation.toString();
		}
		return null;
	}
	
	public StringBuilder getAnnotationBuilder(){
		return this.otherAnnotation;
	}
	
	/**
	 * 
	 * @return the list of CVTerms.
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
	 * @return the CVTerm at the ith position in the list of CVTerms.
	 */
	public CVTerm getCVTerm(int i) {
		return listOfCVTerms.get(i);
	}
	
	/**
	 * changes the modelHistory instance to 'modelHistory'
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
	 * 
	 * @return true if the otherAnnotation String is not null.
	 */
	public boolean isSetOtherAnnotationThanRDF(){
		return this.otherAnnotation != null;
	}

	
	/**
	 * Checks if the Annotation is initialised. An Annotation is initialised
	 * if at less one of the following variables is not null : the otherAnnotation String,
	 * one CVTerm object of the list of CVTerms or the ModelHistory.
	 * @return true if the Annotation is initialised
	 */
	public boolean isSetAnnotation() {
		if (getNoRDFAnnotation() == null && getListOfCVTerms().isEmpty() && getModelHistory() == null){
			return false;
		}
		else if (getNoRDFAnnotation() == null && getModelHistory() == null && !getListOfCVTerms().isEmpty()){
			
			for (int i = 0; i < getListOfCVTerms().size(); i++){
				if (getCVTerm(i) != null){
					return true;
				}
			}
			return false;
		}
		else {
			return true;
		}
	}
	
	/**
	 * set the otherAnnotation String to null
	 */
	public void unsetAnnotation() {
		if (isSetAnnotation())
			otherAnnotation = null;
	}

	/**
	 * clear the list of CVTerms
	 */
	public void unsetCVTerms() {
		listOfCVTerms.clear();
	}
	
	/**
	 * writes the beginning of the RDF otherAnnotation element in 'buffer'
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
	 * writes the end of the RDF otherAnnotation element in 'buffer'
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
	 * Writes the RDF otherAnnotation element in 'buffer'
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
	 * Writes the other otherAnnotation elements in 'buffer'
	 * 
	 * @param indent
	 * @param buffer
	 */
	protected void otherAnnotationToXML(String indent, StringBuffer buffer){
		
		String [] lines = getNoRDFAnnotation().split("\n");
		for (int i = 0; i < lines.length; i++){
			buffer.append(indent).append(lines[i]).append(" \n");
		}
	}
	
	/**
	 * Writes the history section of the RDF otherAnnotation in 'buffer'
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
	 * @return String containing the attributes of the otherAnnotation node
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
	 * converts the Annotation into an XML otherAnnotation element
	 * @param indent
	 * @param parentElement
	 * @return
	 */
	public String toXML(String indent, SBase parentElement){
		
		StringBuffer buffer = new StringBuffer();
		
		if (isSetAnnotation()){
			buffer.append(indent).append("<otherAnnotation").append(attributesToXML()).append("> \n");
			
			if (getListOfCVTerms() != null){
				RDFAnnotationToXML(indent + "  ", buffer, parentElement);
			}
			if (getNoRDFAnnotation() != null){
				otherAnnotationToXML(indent + "  ", buffer);
			}
			buffer.append(indent).append("</otherAnnotation> \n");
		}
		return buffer.toString();
	}

	/**
	 * changes the otherAnnotation attributes with 'annotationNamespaces'
	 * @param annotationNamespaces
	 */
	public void setAnnotationAttributes(HashMap<String, String> annotationAttributes) {
		this.annotationNamespaces = annotationAttributes;
	}
	
	/**
	 * changes the otherAnnotation attributes with 'annotationNamespaces'
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
	 * @return the map containing the otherAnnotation attributes of this Annotation
	 */
	public HashMap<String, String> getAnnotationAttributes() {
		return annotationNamespaces;
	}
	
	/**
	 * sets the about instance of this object if the attributeName is equal to 'about'.
	 * @param attributeName
	 * @param prefix
	 * @param value
	 * @return true if an about XML attribute has been read
	 */
	public boolean readAttribute(String attributeName, String prefix, String value){
		
		if (attributeName.equals("about")){
			setAbout(value);
			return true;
		}
		return false;
	}
	
	/**
	 * adds a namespace to the map annotationNamespace of this object.
	 * @param namespaceName
	 * @param prefix
	 * @param URI
	 */
	public void addAnnotationNamespace(String namespaceName, String prefix, String URI){
		if (!prefix.equals("")){
			this.annotationNamespaces.put(prefix+":"+namespaceName, URI);
		}
		else {
			this.annotationNamespaces.put(namespaceName, URI);
		}
	}

	/**
	 * sets the value of the about String of this object.
	 * @param about
	 */
	public void setAbout(String about) {
		this.about = about;
	}

	/**
	 * 
	 * @return The about String of this object.
	 */
	public String getAbout() {
		return about;
	}

	/**
	 * Sets the rdfAnnotationNamespace map to 'rdfAnnotationNamespaces'.
	 * @param rdfAnnotationNamespaces
	 */
	public void setRdfAnnotationNamespaces(HashMap<String, String> rdfAnnotationNamespaces) {
		this.rdfAnnotationNamespaces = rdfAnnotationNamespaces;
	}

	/**
	 * 
	 * @return the rdfAnnotationNamespaces map of this object.
	 */
	public HashMap<String, String> getRdfAnnotationNamespaces() {
		return rdfAnnotationNamespaces;
	}
	
	/**
	 * 
	 * @return the annotationNamespace map of this object.
	 */
	public HashMap<String, String> getAnnotationNamespaces() {
		return annotationNamespaces;
	}

	/**
	 * adds a namespace to the rdfAnnotationNamespaces map of this object.
	 * @param namespaceName
	 * @param prefix
	 * @param URI
	 */
	public void addRDFAnnotationNamespace(String namespaceName, String prefix, String URI){
		this.rdfAnnotationNamespaces.put(URI, namespaceName);
	}
	
	/**
	 * 
	 * @param namespace
	 * @return the Annotation extension object matching 'namespace'. Return null if there is no matching object.
	 */
	public Annotation getExtension(String namespace){
		return this.extensions.get(namespace);
	}
	
	/**
	 * Adds an Annotation extension object to the extensions map of this object.
	 * @param namespace
	 * @param annotation
	 */
	public void addExtension(String namespace, Annotation annotation){
		this.extensions.put(namespace, annotation);
	}
	
	/**
	 * 
	 * @return the list of all the namespaces of all the packages which extend this object.
	 */
	public Set<String> getNamespaces(){
		return this.extensions.keySet();
	}
	
	/**
	 * Unsets the modelHistory instance of this object.
	 */
	public void unsetModelHistory(){
		this.modelHistory = null;
	}
}
