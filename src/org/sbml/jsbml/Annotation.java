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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.sbml.jsbml.CVTerm.Qualifier;
import org.sbml.jsbml.CVTerm.Type;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.filters.CVTermFilter;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * An Annotation represents the annotations of an {@link SBase} element. It
 * contains the list of {@link CVTerm} objects, a map containing an XML namespace
 * and a {@link String} containing all the annotation elements 
 * of this namespace.
 * 
 * @author marine
 * @author Andreas Dr&auml;ger
 */
public class Annotation implements Cloneable, Serializable {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 2127495202258145900L;

	/**
	 * Returns a {@link String} which represents the given {@link Qualifier}.
	 * 
	 * @param qualifier a <code>Qualifier</code>
	 * @return a {@link String} which represents the given {@link Qualifier}.
	 */
	public static String getElementNameEquivalentToQualifier(Qualifier qualifier) {
		return qualifier.getElementNameEquivalent();
	}

	/**
	 * matches the about XML attribute of an annotation element in a SBML
	 * file.
	 */
	private String about;

	/**
	 * contains all the name spaces of the matching XML annotation node
	 */
	private Map<String, String> annotationNamespaces;

	/**
	 * contains all the annotation extension objects with the name space of
	 * their package.
	 */
	private Map<String, Annotation> extensions;
	
	/**
	 * The ModelHistory which represents the history section of a RDF
	 * annotation
	 */
	private History history;

	/**
	 * contains all the CVTerm of the RDF annotation
	 */
	private List<CVTerm> listOfCVTerms;

	/**
	 * contains all the annotation information which are not RDF.
	 * 
	 */
	private StringBuilder otherAnnotation;

	/**
	 * contains all the name spaces of the matching XML RDF annotation node
	 */
	private Map<String, String> rdfAnnotationNamespaces;

	/**
	 * Creates an Annotation instance. By default, the {@link History} and
	 * otherAnnotation Strings are null. The list of CVTerms, annotationNamespaces, 
	 * rdfAnnotationNamespaces and extensions are empty.
	 * 
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
	 * Creates a new Annotation instance by cloning the given Annotation.
	 * 
	 * @param annotation the annotation to be cloned.
	 */
	public Annotation(Annotation annotation) {
		this();
		copy(annotation.getAnnotationNamespaces(), this.annotationNamespaces);
		copy(annotation.getRDFAnnotationNamespaces(), this.rdfAnnotationNamespaces);
		for (Map.Entry<String, Annotation> entry : annotation.extensions.entrySet()) {
			this.extensions.put(new String(entry.getKey()), entry.getValue().clone());
		}
		this.otherAnnotation = new StringBuilder(annotation.otherAnnotation.toString());
		for (CVTerm term : annotation.getListOfCVTerms()) {
			this.listOfCVTerms.add(term.clone());
		}
		this.history = annotation.history.clone();
	}
	
	/**
	 * Copies one {@link Map} instance into another.
	 * 
	 * @param origin
	 *            copy from here
	 * @param target
	 *            copy everything into this target {@link Map}.
	 */
	private static final void copy(Map<String, String> origin,
			Map<String, String> target) {
		for (Map.Entry<String, String> entry : origin.entrySet()) {
			target.put(new String(entry.getKey().toString()), 
					   new String(entry.getValue().toString()));
		}
	}

	/**
	 * Creates an {@link Annotation} instance from a given Map of annotations.
	 *  By default, the
	 * {@link History} and otherAnnotation Strings are null. The list of CVTerms,
	 * rdfAnnotationNamespaces and extensions are empty.
	 * 
	 * @param annotations a map containing an XML namespace
	 * and a {@link String} containing all the annotation elements 
	 * of this namespace.
	 */
	public Annotation(Map<String, String> annotations) {
		this.rdfAnnotationNamespaces = new HashMap<String, String>();
		this.extensions = new HashMap<String, Annotation>();
		this.otherAnnotation = null;
		this.listOfCVTerms = new LinkedList<CVTerm>();
		this.annotationNamespaces = annotations;
		this.history = null;
	}

	/**
	 * Creates an Annotation instance from a list of CVTerm objects. By default, the {@link History}
	 * and otherAnnotation Strings are null. The HashMaps annotationNamespaces,
	 * rdfAnnotationNamespaces and extensions are empty.
	 * 
	 * @param cvTerms  the list of {@link CVTerm}.
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
	 * Creates an {@link Annotation} instance from a String containing non RDF annotation.
	 *  By default, the {@link History} is null, the list of CVTerms is empty. The {@link Map}s
	 * annotationNamespaces, rdfAnnotationNamespaces and extensions are empty.
	 * 
	 * @param annotation a String containing non RDF annotation, it will be parsed to create
	 * a map containing an XML namespace associated with a {@link String} representing all the annotation elements 
	 * of this namespace.
	 * 
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
	 * Creates an Annotation instance from a String containing non RDF annotation and 
	 * a list of CVTerm. By default, the {@link History} is null. 
	 * The {@link Map}s annotationNamespaces, rdfAnnotationNamespaces and extensions are empty.
	 * 
	 * @param annotation a String containing non RDF annotation, it will be parsed to create
	 * a map containing an XML namespace associated with a {@link String} representing all the annotation elements 
	 * of this namespace.
	 * @param cvTerms  the list of {@link CVTerm}.
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
	 * Adds a namespace to the map annotationNamespace of this object.
	 * 
	 * @param namespaceName an XML namespace name.
	 * @param prefix an optional prefix for the namespace.
	 * @param URI the URI of the namespace
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
	 * Adds a {@link CVTerm}.
	 * 
	 * @param term the {@link CVTerm} to add.
	 * @return true if the 'term' element has been added to the list of CVTerms.
	 */
	public boolean addCVTerm(CVTerm term) {
		if (listOfCVTerms == null) {
			listOfCVTerms = new LinkedList<CVTerm>(); // Should never happen now that we always initialize the list.
		}
		return listOfCVTerms.add(term);
	}

	/**
	 * Adds an Annotation extension object to the extensions map of this object.
	 * 
	 * @param namespace the namespace.
	 * @param annotation the annotation extension object.
	 */
	public void addExtension(String namespace, Annotation annotation) {
		this.extensions.put(namespace, annotation);
	}

	/**
	 * Adds a namespace to the rdfAnnotationNamespaces map of this object.
	 * 
	 * @param namespaceName the namespace name
	 * @param prefix the namespace prefix
	 * @param URI the namespace URI
	 */
	// TODO : prefix is ignored, is it normal ??
	public void addRDFAnnotationNamespace(String namespaceName, String prefix,
			String URI) {
		this.rdfAnnotationNamespaces.put(URI, namespaceName);
	}

	/**
	 * Appends some'annotation' to the non RDF annotation StringBuilder of this object.
	 * 
	 * @param annotation some non RDF annotations.
	 */
	public void appendNoRDFAnnotation(String annotation) {
		if (this.otherAnnotation == null) {
			this.otherAnnotation = new StringBuilder(annotation);
		} else {
			this.otherAnnotation.append(annotation);
		}
	}
	
	 /**
	  * Writes the attributes of the otherAnnotation node
	 * 
	 * @return a String containing the attributes of the otherAnnotation node
	 */
	// TODO : may be we can add the missing namespace here ??
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
	 * Writes the beginning of the RDF annotation element in 'buffer'
	 * 
	 * @param indent the indentation to start with.
	 * @param buffer the buffer where we need to write.
	 * @param parentElement the parent SBML element of the annotation to write.
	 */
	protected void beginRDFAnnotationElement(String indent,
			StringBuffer buffer, SBase parentElement) {

		if (parentElement != null) {
			String metaid = parentElement.getMetaId();

			if (metaid != null) {
				StringTools.append(buffer, indent, "<rdf:RDF ",
						StringTools.newLine());
				// TODO : check if this code is used and if the namespaces are added/handled correctly
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
						StringTools.newLine());
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
	 * @param indent the indentation to start with.
	 * @param buffer the buffer where we need to write.
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
							stringQualifier, ">", StringTools.newLine());
					StringTools.append(buffer, indent, "  <rdf:Bag>",
							StringTools.newLine());

					cvTerm.toXML(indent + "    ", buffer);

					StringTools.append(buffer, indent, "  </rdf:Bag>",
							StringTools.newLine());
					StringTools.append(buffer, indent, "</", prefix, ":",
							stringQualifier, ">", StringTools.newLine());
				}
			}
		}
	}

	/**
	 * Writes the end of the RDF annotation element in 'buffer'
	 * 
	 * @param indent the indentation to end with.
	 * @param buffer the buffer where we need to write.
	 * @param parentElement the parent SBML element of the annotation to write.
	 */
	protected void endRDFAnnotationElement(String indent, StringBuffer buffer,
			SBase parentElement) {

		if (parentElement != null) {
			String metaid = parentElement.getMetaId();

			if (metaid != null) {
				StringTools.append(buffer, indent, "  </rdf:Description>",
						StringTools.newLine());
				StringTools.append(buffer, indent, "</rdf:RDF>",
						StringTools.newLine());
			}
		}
	}

	/**
	 * Checks if this object is equal to 'annotation'
	 * 
	 * @param annotation the annotation to compare to the current instance.
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
	 * Returns a list of CVTerm having the given qualifier.
	 * 
	 * @param qualifier the qualifier
	 * @return a list of CVTerm having the given qualifier, an empty
	 * list is returned if no CVTerm are found.
	 */
	public List<CVTerm> filterCVTerms(Qualifier qualifier) {
		LinkedList<CVTerm> l = new LinkedList<CVTerm>();
		CVTermFilter filter = new CVTermFilter(qualifier);
		for (CVTerm term : getListOfCVTerms()) {
			if (filter.accepts(term)) {
				l.add(term);
			}
		}
		return l;
	}

	/**
	 * Returns a list of CVTerm having the given qualifier and
	 * where the URI contains the given pattern. The pattern can only be plain text.
	 * 
	 * @param qualifier the qualifier.
	 * @param pattern a plain text pattern.
	 * @return a list of CVTerm having the given qualifier and
	 * where the URI matches the given pattern.
	 */
	public List<String> filterCVTerms(Qualifier qualifier, String pattern) {
		List<String> l = new LinkedList<String>();
		for (CVTerm c : filterCVTerms(qualifier)) {
			l.addAll(c.filterResources(pattern));
		}
		return l;
	}

	/**
	 * Returns the about String of this object.
	 * 
	 * @return the about String of this object.
	 */
	public String getAbout() {
		return about == null ? "" : about;
	}

	/**
	 * Returns the map containing the otherAnnotation attributes of this
	 *         Annotation
	 *         
	 * @return the map containing the otherAnnotation attributes of this
	 *         Annotation
	 */
	// TODO : this method name and javadoc is a bit strange. And it is the same as getAnnotationNamespaces() !!
	public Map<String, String> getAnnotationAttributes() {
		return annotationNamespaces;
	}

	/**
	 * Returns the StringBuilder representing non RDF annotations.
	 * 
	 * @return the StringBuilder representing non RDF annotations.
	 */
	public StringBuilder getAnnotationBuilder() {
		return this.otherAnnotation;
	}

	/**
	 * Returns the annotationNamespace map of this object.
	 * 
	 * @return the annotationNamespace map of this object.
	 */
	public Map<String, String> getAnnotationNamespaces() {
		return annotationNamespaces;
	}

	/**
	 * Returns the CVTerm at the ith position in the list of CVTerms.
	 * 
	 * @param i the index of the CVTerm to retrieve.
	 * @return the CVTerm at the ith position in the list of CVTerms.
	 */
	public CVTerm getCVTerm(int i) {
		return listOfCVTerms.get(i);
	}

	/**
	 * Returns the Annotation extension object matching 'namespace'. Return null
	 *         if there is no matching object.
	 * 
	 * @param namespace the namespace
	 * @return the Annotation extension object matching 'namespace'. Return null
	 *         if there is no matching object.
	 */
	// TODO : we need to clear things up in this class to be sure that we use namespace url or some name/prefix only
	public Annotation getExtension(String namespace) {
		return this.extensions.get(namespace);
	}

	/**
	 * Returns the {@link History} of the Annotation.
	 * 
	 * @return the {@link History} of the Annotation.
	 */
	public History getHistory() {
		return history;
	}

	/**
	 * Returns the list of CVTerms. If they are no CVTerm, an empty list is returned.
	 * 
	 * @return the list of CVTerms.
	 */
	public List<CVTerm> getListOfCVTerms() {
		if (listOfCVTerms == null) {
			listOfCVTerms = new LinkedList<CVTerm>(); // Should never happen, to remove ?
		}
		return listOfCVTerms;
	}

	/**
	 * Returns the list of all the namespaces of all the packages which extend
	 *         this object.
	 * 
	 * @return the list of all the namespaces of all the packages which extend
	 *         this object.
	 */
	public Set<String> getNamespaces() {
		return this.extensions.keySet();
	}

	/**
	 * Returns the String containing annotations other than RDF
	 *         annotation.
	 * 
	 * @return the String containing annotations other than RDF
	 *         annotation. Return null if there are none.
	 */
	public String getNoRDFAnnotation() {
		if (otherAnnotation != null) {
			return otherAnnotation.toString();
		}
		return null;
	}

	/**
	 * Returns the rdfAnnotationNamespaces map of this object.
	 * 
	 * @return the rdfAnnotationNamespaces map of this object.
	 */
	public Map<String, String> getRDFAnnotationNamespaces() {
		return rdfAnnotationNamespaces;
	}

	/**
	 * Inserts 'annotation' to the non RDF annotation StringBuilder
	 * at position 'offset'.
	 * 
	 * @param annotation the piece of annotation to add.
	 * @param offset the position where to add it in the StringBuilder.
	 */
	// TODO : check if this method is used and needed. Could also be used to insert the missing namespaces 
	// before creating the DOM tree. 
	public void insertNoRDFAnnotation(String annotation, int offset) {
		if (this.otherAnnotation == null) {
			this.otherAnnotation = new StringBuilder(annotation);
		} else {
			this.otherAnnotation.insert(offset, annotation);
		}
	}

	/**
	 * Checks whether the 'about' element has been initialized.
	 * 
	 * @return true if the 'about' element has been initialized.
	 */
	public boolean isSetAbout() {
		return about != null;
	}

	/**
	 * Checks if the Annotation is initialised. 
	 * <p>An Annotation is initialised if
	 * at least one of the following is true :
	 * <li> there is some non RDF annotation
	 * <li> one or more CVTerm are defined
	 * <li> there is an history defined.
	 * <p>
	 * 
	 * @return true if the Annotation is initialised
	 */
	public boolean isSetAnnotation() {
		if ((getNoRDFAnnotation() == null) && getListOfCVTerms().isEmpty()
				&& (getHistory() == null)) {
			return false;
		} else if ((getNoRDFAnnotation() == null) && (getHistory() == null)
				&& !getListOfCVTerms().isEmpty()) {

			for (int i = 0; i < getListOfCVTerms().size(); i++) {
				if (getCVTerm(i) != null) {
					return true;
				}
			}
		}
		return true;
	}

	/**
	 * Checks if the {@link History} is initialised
	 * 
	 * @return true if the {@link History} is initialised
	 */
	public boolean isSetHistory() {
		return history != null;
	}

	/**
	 * Checks if the list of {@link CVTerm} is not empty.
	 * 
	 * @return true if there is one or more {@link CVTerm} defined. 
	 */
	public boolean isSetListOfCVTerms() {
		return (listOfCVTerms != null) && (listOfCVTerms.size() > 0);
	}

	/**
	 * Returns true if there is some non RDF annotation.
	 * 
	 * @return true if there is some non RDF annotation.
	 */
	public boolean isSetOtherAnnotationThanRDF() {
		return this.otherAnnotation != null;
	}

	/**
	 * Writes the history section of the RDF annotation in 'buffer'
	 * 
	 * @param indent the indentation to use.
	 * @param buffer the buffer where we need to write.
	 */
	private void historyToXML(String indent, StringBuffer buffer) {
		if (isSetHistory()) {
			getHistory().toXML(indent, buffer);
		}
	}

	/**
	 * Writes the non RDF annotation elements in 'buffer'.
	 * 
	 * @param indent the indentation to use.
	 * @param buffer the buffer where we need to write.
	 */
	protected void otherAnnotationToXML(String indent, StringBuffer buffer) {
		String[] lines = getNoRDFAnnotation().split(StringTools.newLine());
		for (int i = 0; i < lines.length; i++) {
			StringTools.append(buffer, indent, lines[i], StringTools.newLine());
		}
	}

	/**
	 * Writes the RDF annotation elements in 'buffer'
	 * 
	 * @param indent the indentation to use.
	 * @param buffer the buffer where we need to write.
	 * @param parentElement the parent SBML element of the annotation to write.
	 */
	protected void RDFAnnotationToXML(String indent, StringBuffer buffer,
			SBase parentElement) {

		beginRDFAnnotationElement(indent, buffer, parentElement);

		historyToXML(indent + "    ", buffer);
		createCVTermsElements(indent + "    ", buffer);

		endRDFAnnotationElement(indent, buffer, parentElement);
	}

	/**
	 * Sets the about instance of this object if the attributeName is equal to
	 * 'about'.
	 * 
	 * @param attributeName the attribute name.
	 * @param prefix the attribute prefix.
	 * @param value the attribute value.
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
	 * Sets the value of the about String of this object.
	 * 
	 * @param about the about String to set.
	 */
	public void setAbout(String about) {
		this.about = about;
	}
	
	// TODO : some fireSBaseChangedEvent are missing in this class.
	
	/**
	 * Sets the annotationNamespaces.
	 * 
	 * @param annotationNamespaces the annotationNamespaces to set
	 */
	public void setAnnotationNamespaces(HashMap<String, String> annotationNamespaces) {
		this.annotationNamespaces = annotationNamespaces;
	}

	/**
	 * Sets the annotationNamespaces.
	 * 
	 * @param annotationNamespaces the annotationNamespaces to set
	 */
	// TODO : we need to find better names for these class attributes and methods for 1.0 release
	public void setAnnotationAttributes(NamedNodeMap annotationNamespaces) {
		if (annotationNamespaces != null) {
			for (int i = 0; i < annotationNamespaces.getLength(); i++) {
				Node attribute = annotationNamespaces.item(i);
				getAnnotationAttributes().put(attribute.getNodeName(),
						attribute.getNodeValue());
			}
		}
	}

	/**
	 * Changes the {@link History} instance to 'history'
	 * 
	 * @param history the history to set.
	 */
	public void setHistory(History history) {
		this.history = history;
	}

	/**
	 * Sets the rdfAnnotationNamespace map to 'rdfAnnotationNamespaces'.
	 * 
	 * @param rdfAnnotationNamespaces the rdfAnnotationNamespace map to set.
	 */
	public void setRdfAnnotationNamespaces(HashMap<String, String> rdfAnnotationNamespaces) {
		this.rdfAnnotationNamespaces = rdfAnnotationNamespaces;
	}

	/**
	 * Converts the Annotation into an XML annotation element
	 * 
	 * @param indent the indentation to use.
	 * @param parentElement the parent SBML element of the annotation to write.
	 * @return the Annotation as an XML annotation element
	 */
	public String toXML(String indent, SBase parentElement) {
		StringBuffer buffer = new StringBuffer();
		if (isSetAnnotation()) {
			StringTools.append(buffer, indent, "<annotation",
					attributesToXML(), ">", StringTools.newLine());
			if (getListOfCVTerms() != null) {
				RDFAnnotationToXML(indent + "  ", buffer, parentElement);
			}
			if (getNoRDFAnnotation() != null) {
				otherAnnotationToXML(indent + "  ", buffer);
			}
			StringTools.append(buffer, indent, "</annotation>",
					StringTools.newLine());
		}
		return buffer.toString();
	}
	
	/**
	 * Sets the non RDF annotation String to null.
	 */
	public void unsetAnnotation() {
		if (isSetAnnotation()) {
			otherAnnotation = null;
		}
	}

	/**
	 * Clears the list of CVTerms
	 */
	public void unsetCVTerms() {
		listOfCVTerms.clear();
	}

	/**
	 * Sets the {@link History} instance of this object to null.
	 */
	public void unsetHistory() {
		this.history = null;
	}
}
