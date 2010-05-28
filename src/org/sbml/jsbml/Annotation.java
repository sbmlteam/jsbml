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
import org.sbml.jsbml.CVTerm.Type;
import org.sbml.jsbml.util.StringTools;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * An Annotation represents the otherAnnotation of an {@link SBase} element. It
 * contains the list of {@link CVTerm} objects, the map containing the attribute
 * of a XML otherAnnotation node and a {@link String} containing all the other
 * otherAnnotation elements of the XML otherAnnotation node.
 * 
 * @author marine
 * 
 * @opt attributes
 * @opt types
 * @opt visibility
 * @composed 0..* MIRIAM 1 CVTerm
 * @composed 0..1 history 1 History
 */
public class Annotation {

	/**
	 * 
	 * @param qualifier
	 * @return String which represents the Qualifier qualifier in a XML node
	 */
	public static String getElementNameEquivalentToQualifier(Qualifier qualifier) {
		return qualifier.getElementNameEquivalent();
	}

	/**
	 * matches the about XML attribute of an otherAnnotation element in a SBML
	 * file.
	 */
	private String about;

	/**
	 * contains all the namespaces of the matching XML otherAnnotation node
	 */
	private HashMap<String, String> annotationNamespaces = new HashMap<String, String>();

	/**
	 * contains all the otherAnnotation extension objects with the namespace of
	 * their package.
	 */
	private HashMap<String, Annotation> extensions = new HashMap<String, Annotation>();

	/**
	 * contains all the CVTerm of the RDF otherAnnotation
	 */
	private List<CVTerm> listOfCVTerms;

	/**
	 * The ModelHistory which represents the history section of a RDF
	 * otherAnnotation
	 */
	private History history;

	/**
	 * contains all the otherAnnotation information which are not RDF
	 * otherAnnotation information
	 */
	private StringBuilder otherAnnotation;

	/**
	 * contains all the namespaces of the matching XML RDF otherAnnotation node
	 */
	private HashMap<String, String> rdfAnnotationNamespaces = new HashMap<String, String>();

	/**
	 * Creates an Annotation instance. By default, the modelHistory and
	 * otherAnnotation Strings are null. The list of CVTerms is empty. The
	 * HashMaps annotationNamespaces, rdfAnnotationNamespaces and extensions are
	 * empty.
	 * 
	 * @param cvTerms
	 */
	public Annotation() {
		this.annotationNamespaces = new HashMap<String, String>();
		this.rdfAnnotationNamespaces = new HashMap<String, String>();
		this.extensions = new HashMap<String, Annotation>();
		this.otherAnnotation = null;
		this.listOfCVTerms = new LinkedList<CVTerm>();
		this.history = null;
	}

	/**
	 * 
	 * @param annotation
	 */
	public Annotation(Annotation annotation) {
		this();
		// TODO: We need a clone constructor for annotations!
	}

	/**
	 * Creates an Annotation instance from attributes. By default, the
	 * modelHistory and otherAnnotation Strings are null. The list of CVTerms is
	 * empty. The HashMaps annotationNamespaces, rdfAnnotationNamespaces and
	 * extensions are empty.
	 * 
	 * @param attributes
	 */
	public Annotation(HashMap<String, String> attributes) {
		this.annotationNamespaces = new HashMap<String, String>();
		this.rdfAnnotationNamespaces = new HashMap<String, String>();
		this.extensions = new HashMap<String, Annotation>();
		this.otherAnnotation = null;
		this.listOfCVTerms = new LinkedList<CVTerm>();
		this.annotationNamespaces = attributes;
		this.history = null;
	}

	/**
	 * Creates an Annotation instance from cvTerms. By default, the modelHistory
	 * and otherAnnotation Strings are null. The HashMaps annotationNamespaces,
	 * rdfAnnotationNamespaces and extensions are empty.
	 * 
	 * @param cvTerms
	 */
	public Annotation(List<CVTerm> cvTerms) {
		this.annotationNamespaces = new HashMap<String, String>();
		this.rdfAnnotationNamespaces = new HashMap<String, String>();
		this.extensions = new HashMap<String, Annotation>();
		this.otherAnnotation = null;
		this.listOfCVTerms = cvTerms;
		this.history = null;
	}

	/**
	 * Creates an Annotation instance from otherAnnotation. By default, the
	 * modelHistory is null, the list of CVTerms is empty. The HashMaps
	 * annotationNamespaces, rdfAnnotationNamespaces and extensions are empty.
	 * 
	 * @param otherAnnotation
	 */
	public Annotation(String annotation) {
		this.annotationNamespaces = new HashMap<String, String>();
		this.rdfAnnotationNamespaces = new HashMap<String, String>();
		this.extensions = new HashMap<String, Annotation>();
		this.otherAnnotation = new StringBuilder(annotation);
		this.listOfCVTerms = new LinkedList<CVTerm>();
		this.history = null;
	}

	/**
	 * Creates an Annotation instance from otherAnnotation and cvTerms. By
	 * default, the modelHistory is null. The HashMaps annotationNamespaces,
	 * rdfAnnotationNamespaces and extensions are empty.
	 * 
	 * @param otherAnnotation
	 * @param cvTerms
	 */
	public Annotation(String annotation, List<CVTerm> cvTerms) {
		this.otherAnnotation = new StringBuilder(annotation);
		this.annotationNamespaces = new HashMap<String, String>();
		this.rdfAnnotationNamespaces = new HashMap<String, String>();
		this.extensions = new HashMap<String, Annotation>();
		this.listOfCVTerms = cvTerms;
		this.history = null;
	}

	/**
	 * adds a namespace to the map annotationNamespace of this object.
	 * 
	 * @param namespaceName
	 * @param prefix
	 * @param URI
	 */
	public void addAnnotationNamespace(String namespaceName, String prefix,
			String URI) {
		if (!prefix.equals("")) {
			this.annotationNamespaces.put(prefix + ":" + namespaceName, URI);
		} else {
			this.annotationNamespaces.put(namespaceName, URI);
		}
	}

	/**
	 * If not yet set, this method initializes this
	 * 
	 * @param term
	 * @return true if the 'term' element has been added to the list of CVTerms
	 */
	public boolean addCVTerm(CVTerm term) {
		if (listOfCVTerms == null) {
			listOfCVTerms = new LinkedList<CVTerm>();
		}
		return listOfCVTerms.add(term);
	}

	/**
	 * Adds an Annotation extension object to the extensions map of this object.
	 * 
	 * @param namespace
	 * @param annotation
	 */
	public void addExtension(String namespace, Annotation annotation) {
		this.extensions.put(namespace, annotation);
	}

	/**
	 * adds a namespace to the rdfAnnotationNamespaces map of this object.
	 * 
	 * @param namespaceName
	 * @param prefix
	 * @param URI
	 */
	public void addRDFAnnotationNamespace(String namespaceName, String prefix,
			String URI) {
		this.rdfAnnotationNamespaces.put(URI, namespaceName);
	}

	/**
	 * Appends 'annotation' to the otherAnnotation StringBuilder of this object.
	 * 
	 * @param otherAnnotation
	 */
	public void appendNoRDFAnnotation(String annotation) {
		if (this.otherAnnotation == null) {
			this.otherAnnotation = new StringBuilder(annotation);
		} else {
			this.otherAnnotation.append(annotation);
		}
	}

	/**
	 * 
	 * @return String containing the attributes of the otherAnnotation node
	 */
	private String attributesToXML() {
		StringBuffer attributes = new StringBuffer();

		Iterator<Map.Entry<String, String>> iterator = getAnnotationAttributes()
				.entrySet().iterator();

		for (Iterator<Map.Entry<String, String>> it = iterator; it.hasNext();) {

			Map.Entry<String, String> entry = it.next();
			StringTools.append(attributes, " ", entry.getKey(), "=\"", entry
					.getValue(), Character.valueOf('"'));
		}

		return attributes.toString();
	}

	/**
	 * writes the beginning of the RDF otherAnnotation element in 'buffer'
	 * 
	 * @param indent
	 * @param buffer
	 * @param parentElement
	 */
	protected void beginRDFAnnotationElement(String indent,
			StringBuffer buffer, SBase parentElement) {

		if (parentElement != null) {
			String metaid = parentElement.getMetaId();

			if (metaid != null) {
				StringTools.append(buffer, indent, "<rdf:RDF ",
						StringTools.newLine);
				/*
				 * buffer.append(indent).append("         xmlns:rdf=").append('"'
				 * )
				 * .append(RDFElement.getRDFNamespaces().get("rdf")).append('"')
				 * .append(" \n");
				 * buffer.append(indent).append("         xmlns:dc="
				 * ).append('"')
				 * .append(RDFElement.getRDFNamespaces().get("dc")).
				 * append('"').append(" \n");
				 * buffer.append(indent).append("         xmlns:dcterms="
				 * ).append
				 * ('"').append(RDFElement.getRDFNamespaces().get("dcterms"
				 * )).append('"').append(" \n");
				 * buffer.append(indent).append("         xmlns:vCard="
				 * ).append('"'
				 * ).append(RDFElement.getRDFNamespaces().get("vcard"
				 * )).append('"').append(" \n");
				 * buffer.append(indent).append("         xmlns:bqbiol="
				 * ).append(
				 * '"').append(RDFElement.getRDFNamespaces().get("bqbiol"
				 * )).append('"').append(" \n");
				 * buffer.append(indent).append("         xmlns:bqmodel="
				 * ).append
				 * ('"').append(RDFElement.getRDFNamespaces().get("bqmodel"
				 * )).append('"').append(" \n");
				 * buffer.append(indent).append("> \n");
				 */
				StringTools.append(buffer, indent,
						"  <rdf:Description rdf:about=\"#", metaid, "\">",
						StringTools.newLine);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Annotation clone() {
		return new Annotation(this);
	}

	/**
	 * Writes the CV term elements in 'buffer'
	 * 
	 * @param indent
	 * @param buffer
	 */
	protected void createCVTermsElements(String indent, StringBuffer buffer) {

		if (getListOfCVTerms() != null) {

			for (int i = 0; i < getListOfCVTerms().size(); i++) {
				CVTerm cvTerm = getCVTerm(i);
				Type qualifierType = cvTerm.getQualifierType();
				Qualifier qualifier = null;

				if (qualifierType.equals(Type.BIOLOGICAL_QUALIFIER)) {
					qualifier = cvTerm.getBiologicalQualifierType();
				} else if (qualifierType.equals(Type.MODEL_QUALIFIER)) {
					qualifier = cvTerm.getModelQualifierType();
				}
				String prefix = qualifier != null ? qualifier
						.getElementNameEquivalent() : null;

				String stringQualifier = qualifier.getElementNameEquivalent();

				if (prefix != null && stringQualifier != null) {
					StringTools.append(buffer, indent, "<", prefix, ":",
							stringQualifier, ">", StringTools.newLine);
					StringTools.append(buffer, indent, "  <rdf:Bag>",
							StringTools.newLine);

					cvTerm.toXML(indent + "    ", buffer);

					StringTools.append(buffer, indent, "  </rdf:Bag>",
							StringTools.newLine);
					StringTools.append(buffer, indent, "</", prefix, ":",
							stringQualifier, ">", StringTools.newLine);
				}
			}
		}
	}

	/**
	 * writes the end of the RDF otherAnnotation element in 'buffer'
	 * 
	 * @param indent
	 * @param buffer
	 * @param parentElement
	 */
	protected void endRDFAnnotationElement(String indent, StringBuffer buffer,
			SBase parentElement) {

		if (parentElement != null) {
			String metaid = parentElement.getMetaId();

			if (metaid != null) {
				StringTools.append(buffer, indent, "  </rdf:Description>",
						StringTools.newLine);
				StringTools.append(buffer, indent, "</rdf:RDF>",
						StringTools.newLine);
			}
		}
	}

	/**
	 * Checks if this object is equal to 'annotation'
	 * 
	 * @param annotation
	 * @return true if this object entirely matches 'annotation'
	 */
	public boolean equals(Annotation annotation) {
		boolean equals = isSetAnnotation() == annotation.isSetAnnotation();
		if (equals && isSetOtherAnnotationThanRDF()) {
			equals = otherAnnotation.equals(annotation.getNoRDFAnnotation());
		}
		equals &= isSetHistory() == annotation.isSetHistory();
		if (equals && isSetHistory()) {
			equals = this.history.equals(annotation.getHistory());
		}
		equals &= getListOfCVTerms().isEmpty() == annotation.getListOfCVTerms()
				.isEmpty();
		if (equals && !getListOfCVTerms().isEmpty()) {
			if (listOfCVTerms.size() == annotation.getListOfCVTerms().size()) {
				for (int i = 0; i < listOfCVTerms.size(); i++) {
					CVTerm cvTerm1 = listOfCVTerms.get(i);
					CVTerm cvTerm2 = annotation.getListOfCVTerms().get(i);

					if (cvTerm1 != null && cvTerm2 != null) {
						equals &= cvTerm1.equals(cvTerm2);
						if (!equals) {
							return false;
						}
					} else if ((cvTerm1 == null && cvTerm2 != null)
							|| (cvTerm2 == null && cvTerm1 != null)) {
						return false;
					}
				}
			} else {
				return false;
			}
		}

		return equals;
	}

	/**
	 * 
	 * @param qualifier
	 * @return
	 */
	public List<CVTerm> filterCVTerms(Qualifier qualifier) {
		LinkedList<CVTerm> l = new LinkedList<CVTerm>();
		for (CVTerm term : listOfCVTerms) {
			if (term.isBiologicalQualifier()
					&& term.getBiologicalQualifierType() == qualifier) {
				l.add(term);
			} else if (term.isModelQualifier()
					&& term.getModelQualifierType() == qualifier) {
				l.add(term);
			}
		}
		return l;
	}

	/**
	 * 
	 * @param qualifier
	 * @param pattern
	 * @return
	 */
	public List<String> filterCVTerms(Qualifier qualifier, String pattern) {
		List<String> l = new LinkedList<String>();
		for (CVTerm c : filterCVTerms(qualifier)) {
			l.addAll(c.filterResources(pattern));
		}
		return l;
	}

	/**
	 * 
	 * @return The about String of this object.
	 */
	public String getAbout() {
		return about == null ? "" : about;
	}

	/**
	 * 
	 * @return the map containing the otherAnnotation attributes of this
	 *         Annotation
	 */
	public HashMap<String, String> getAnnotationAttributes() {
		return annotationNamespaces;
	}

	public StringBuilder getAnnotationBuilder() {
		return this.otherAnnotation;
	}

	/**
	 * 
	 * @return the annotationNamespace map of this object.
	 */
	public HashMap<String, String> getAnnotationNamespaces() {
		return annotationNamespaces;
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
	 * 
	 * @param namespace
	 * @return the Annotation extension object matching 'namespace'. Return null
	 *         if there is no matching object.
	 */
	public Annotation getExtension(String namespace) {
		return this.extensions.get(namespace);
	}

	/**
	 * 
	 * @return the modelHistory of the ModelAnnotation
	 */
	public History getHistory() {
		return history;
	}

	/**
	 * 
	 * @return the list of CVTerms. If not yet set, this method initializes this
	 *         list.
	 */
	public List<CVTerm> getListOfCVTerms() {
		if (listOfCVTerms == null) {
			listOfCVTerms = new LinkedList<CVTerm>();
		}
		return listOfCVTerms;
	}

	/**
	 * 
	 * @return the list of all the namespaces of all the packages which extend
	 *         this object.
	 */
	public Set<String> getNamespaces() {
		return this.extensions.keySet();
	}

	/**
	 * 
	 * @return the otherAnnotation String containing annotations other than RDF
	 *         annotation. Return null if otherAnnotation is null.
	 */
	public String getNoRDFAnnotation() {
		if (otherAnnotation != null) {
			return otherAnnotation.toString();
		}
		return null;
	}

	/**
	 * 
	 * @return the rdfAnnotationNamespaces map of this object.
	 */
	public HashMap<String, String> getRDFAnnotationNamespaces() {
		return rdfAnnotationNamespaces;
	}

	/**
	 * Checks if the Annotation is initialised. An Annotation is initialised if
	 * at less one of the following variables is not null : the otherAnnotation
	 * String, one CVTerm object of the list of CVTerms or the ModelHistory.
	 * 
	 * @return true if the Annotation is initialised
	 */
	public boolean isSetAnnotation() {
		if (getNoRDFAnnotation() == null && getListOfCVTerms().isEmpty()
				&& getHistory() == null) {
			return false;
		} else if (getNoRDFAnnotation() == null && getHistory() == null
				&& !getListOfCVTerms().isEmpty()) {

			for (int i = 0; i < getListOfCVTerms().size(); i++) {
				if (getCVTerm(i) != null) {
					return true;
				}
			}
			return false;
		} else {
			return true;
		}
	}

	/**
	 * check if the modelHistory is initialised
	 * 
	 * @return true if the modelHistory is initialised
	 */
	public boolean isSetHistory() {
		return history != null;
	}

	/**
	 * 
	 * @return true if the otherAnnotation String is not null.
	 */
	public boolean isSetOtherAnnotationThanRDF() {
		return this.otherAnnotation != null;
	}

	/**
	 * Writes the history section of the RDF otherAnnotation in 'buffer'
	 * 
	 * @param indent
	 * @param buffer
	 */
	private void modelHistoryToXML(String indent, StringBuffer buffer) {
		if (isSetHistory()) {
			getHistory().toXML(indent, buffer);
		}
	}

	/**
	 * Writes the other otherAnnotation elements in 'buffer'
	 * 
	 * @param indent
	 * @param buffer
	 */
	protected void otherAnnotationToXML(String indent, StringBuffer buffer) {
		String[] lines = getNoRDFAnnotation().split(StringTools.newLine);
		for (int i = 0; i < lines.length; i++) {
			StringTools.append(buffer, indent, lines[i], StringTools.newLine);
		}
	}

	/**
	 * Writes the RDF otherAnnotation element in 'buffer'
	 * 
	 * @param indent
	 * @param buffer
	 * @param parentElement
	 */
	protected void RDFAnnotationToXML(String indent, StringBuffer buffer,
			SBase parentElement) {

		beginRDFAnnotationElement(indent, buffer, parentElement);

		modelHistoryToXML(indent + "    ", buffer);
		createCVTermsElements(indent + "    ", buffer);

		endRDFAnnotationElement(indent, buffer, parentElement);
	}

	/**
	 * sets the about instance of this object if the attributeName is equal to
	 * 'about'.
	 * 
	 * @param attributeName
	 * @param prefix
	 * @param value
	 * @return true if an about XML attribute has been read
	 */
	public boolean readAttribute(String attributeName, String prefix,
			String value) {

		if (attributeName.equals("about")) {
			setAbout(value);
			return true;
		}
		return false;
	}

	/**
	 * sets the value of the about String of this object.
	 * 
	 * @param about
	 */
	public void setAbout(String about) {
		this.about = about;
	}

	/**
	 * changes the otherAnnotation attributes with 'annotationNamespaces'
	 * 
	 * @param annotationNamespaces
	 */
	public void setAnnotationAttributes(
			HashMap<String, String> annotationAttributes) {
		this.annotationNamespaces = annotationAttributes;
	}

	/**
	 * changes the otherAnnotation attributes with 'annotationNamespaces'
	 * 
	 * @param annotationNamespaces
	 */
	public void setAnnotationAttributes(NamedNodeMap annotationAttributes) {

		if (annotationAttributes != null) {

			for (int i = 0; i < annotationAttributes.getLength(); i++) {
				Node attribute = annotationAttributes.item(i);

				getAnnotationAttributes().put(attribute.getNodeName(),
						attribute.getNodeValue());
			}
		}
	}

	/**
	 * changes the modelHistory instance to 'modelHistory'
	 * 
	 * @param modelHistory
	 */
	public void setHistory(History modelHistory) {
		this.history = modelHistory;
	}

	/**
	 * Sets the rdfAnnotationNamespace map to 'rdfAnnotationNamespaces'.
	 * 
	 * @param rdfAnnotationNamespaces
	 */
	public void setRdfAnnotationNamespaces(
			HashMap<String, String> rdfAnnotationNamespaces) {
		this.rdfAnnotationNamespaces = rdfAnnotationNamespaces;
	}

	/**
	 * converts the Annotation into an XML otherAnnotation element
	 * 
	 * @param indent
	 * @param parentElement
	 * @return
	 */
	public String toXML(String indent, SBase parentElement) {
		StringBuffer buffer = new StringBuffer();
		if (isSetAnnotation()) {
			StringTools.append(buffer, indent, "<otherAnnotation",
					attributesToXML(), ">", StringTools.newLine);
			if (getListOfCVTerms() != null) {
				RDFAnnotationToXML(indent + "  ", buffer, parentElement);
			}
			if (getNoRDFAnnotation() != null) {
				otherAnnotationToXML(indent + "  ", buffer);
			}
			StringTools.append(buffer, indent, "</otherAnnotation>",
					StringTools.newLine);
		}
		return buffer.toString();
	}

	/**
	 * set the otherAnnotation String to null
	 */
	public void unsetAnnotation() {
		if (isSetAnnotation()) {
			otherAnnotation = null;
		}
	}

	/**
	 * clear the list of CVTerms
	 */
	public void unsetCVTerms() {
		listOfCVTerms.clear();
	}

	/**
	 * Unsets the modelHistory instance of this object.
	 */
	public void unsetHistory() {
		this.history = null;
	}
}
