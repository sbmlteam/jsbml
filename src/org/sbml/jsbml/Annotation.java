/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2011 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */

package org.sbml.jsbml;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.sbml.jsbml.CVTerm.Qualifier;
import org.sbml.jsbml.CVTerm.Type;
import org.sbml.jsbml.util.filters.CVTermFilter;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * An Annotation represents the annotations of an {@link SBase} element. It
 * contains the list of {@link CVTerm} objects, a {@link Map} containing an XML
 * name space and a {@link String} containing all the annotation elements of
 * this name space.
 * 
 * @author marine
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 */
public class Annotation implements Cloneable, Serializable {
	
	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 2127495202258145900L;

	/**
	 * The RDF syntax name space definition URI.
	 */
	public static final transient String URI_RDF_SYNTAX_NS = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";

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
	 * otherAnnotation Strings are null. The list of {@link CVTerm}s, annotationNamespaces, 
	 * rdfAnnotationNamespaces and extensions are empty.
	 * 
	 */
	public Annotation() {
		this.annotationNamespaces = new HashMap<String, String>();
		this.rdfAnnotationNamespaces = new HashMap<String, String>();
		this.extensions = new HashMap<String, Annotation>();
		this.otherAnnotation = null;
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
		if (annotation.otherAnnotation != null) {
			this.otherAnnotation = new StringBuilder(annotation.otherAnnotation
					.toString());
		}
		for (CVTerm term : annotation.getListOfCVTerms()) {
			getListOfCVTerms().add(term.clone());
		}
		if (annotation.getHistory() != null) {
			this.history = annotation.getHistory().clone();
		}
	}

	/**
	 * Creates an {@link Annotation} instance from a list of {@link CVTerm}
	 * objects. By default, the {@link History} and otherAnnotation {@link String}s are
	 * null. The {@link Map}s annotationNamespaces, rdfAnnotationNamespaces and
	 * extensions are empty.
	 * 
	 * @param cvTerms
	 *            the list of {@link CVTerm}.
	 */
	public Annotation(List<CVTerm> cvTerms) {
		this();
		this.listOfCVTerms = cvTerms;
	}

	/**
	 * Creates an {@link Annotation} instance from a given {@link Map} of
	 * annotations. By default, the {@link History} and otherAnnotation {@link String}s
	 * are null. The list of {@link CVTerm}s, rdfAnnotationNamespaces and
	 * extensions are empty.
	 * 
	 * @param annotations
	 *            a map containing an XML name space and a {@link String}
	 *            containing all the annotation elements of this name space.
	 */
	public Annotation(Map<String, String> annotations) {
		this();
		this.annotationNamespaces = annotations;
	}

	/**
	 * Creates an {@link Annotation} instance from a {@link String} containing non RDF
	 * annotation. By default, the {@link History} is null, the list of {@link CVTerm}s
	 * is empty. The {@link Map}s annotationNamespaces, rdfAnnotationNamespaces
	 * and extensions are empty.
	 * 
	 * @param annotation
	 *            a {@link String} containing non RDF annotation, it will be parsed to
	 *            create a {@link Map} containing an XML name space associated with a
	 *            {@link String} representing all the annotation elements of
	 *            this name space.
	 * 
	 */
	public Annotation(String annotation) {
		this();
		this.otherAnnotation = new StringBuilder(annotation);
	}

	/**
	 * Creates an {@link Annotation} instance from a {@link String} containing
	 * non RDF annotation and a list of {@link CVTerm}. By default, the
	 * {@link History} is null. The {@link Map}s annotationNamespaces,
	 * rdfAnnotationNamespaces and extensions are empty.
	 * 
	 * @param annotation
	 *            a {@link String} containing non RDF annotation, it will be
	 *            parsed to create a {@link Map} containing an XML name space
	 *            associated with a {@link String} representing all the
	 *            annotation elements of this name space.
	 * @param cvTerms
	 *            the {@link List} of {@link CVTerm}.
	 */
	public Annotation(String annotation, List<CVTerm> cvTerms) {
		this();
		this.otherAnnotation = new StringBuilder(annotation);
		this.listOfCVTerms = cvTerms;
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
	 * Adds a {@link CVTerm} and checks whether the required name spaces have
	 * already been added to the {@link #rdfAnnotationNamespaces}. If name
	 * spaces are missing, these are added automatically.
	 * 
	 * @param cvTerm
	 *            the {@link CVTerm} to add.
	 * @return true if the 'cvTerm' element has been added to the {@link List}
	 *         of {@link Qualifier}s.
	 */
	public boolean addCVTerm(CVTerm cvTerm) {
		if (listOfCVTerms == null) {
			listOfCVTerms = new LinkedList<CVTerm>();
		}
		if (!rdfAnnotationNamespaces.containsKey(URI_RDF_SYNTAX_NS)) {
			addRDFAnnotationNamespace("rdf", "", URI_RDF_SYNTAX_NS);
		}
		Type type = cvTerm.getQualifierType();
		if ((type != null) && (type != Type.UNKNOWN_QUALIFIER)
				&& !rdfAnnotationNamespaces.containsKey(type.getNamespaceURI())) {
			addRDFAnnotationNamespace(type.getElementNameEquivalent(), "", type
					.getNamespaceURI());
		}
		return listOfCVTerms.add(cvTerm);
	}
	
	/**
	 * Adds an Annotation extension object to the extensions map of this object.
	 * 
	 * @param namespace the name space.
	 * @param annotation the annotation extension object.
	 */
	public void addExtension(String namespace, Annotation annotation) {
		this.extensions.put(namespace, annotation);
	}

	/**
	 * Adds a name space to the rdfAnnotationNamespaces {@link Map} of this
	 * object.
	 * 
	 * @param namespaceName
	 *            the name space name
	 * @param prefix
	 *            the name space prefix
	 * @param URI
	 *            the name space URI
	 */
	public void addRDFAnnotationNamespace(String namespaceName, String prefix,
			String URI) {
		// TODO : prefix is ignored, is it normal ??
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
	 * Checks if this object is equal to 'annotation'
	 * 
	 * @param annotation the annotation to compare to the current instance.
	 * @return true if this object entirely matches 'annotation'
	 */
	public boolean equals(Annotation annotation) {
		boolean equals = isSetNonRDFannotation() == annotation.isSetNonRDFannotation();
		if (equals && isSetOtherAnnotationThanRDF()) {
			equals = otherAnnotation.equals(annotation.getNonRDFannotation());
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
	public String getNonRDFannotation() {
		if (otherAnnotation != null) {
			return otherAnnotation.toString();
		}
		return null;
	}

	/**
	 * Gives the number of {@link CVTerm}s in this {@link Annotation}.
	 * 
	 * @return the number of controlled vocabulary terms in this {@link Annotation}.
	 */
	public int getNumCVTerms() {
		return isSetListOfCVTerms() ? listOfCVTerms.size() : 0;
	}

	/**
	 * Returns the rdfAnnotationNamespaces {@link Map} of this object.
	 * 
	 * @return the rdfAnnotationNamespaces {@link Map} of this object.
	 */
	public Map<String, String> getRDFAnnotationNamespaces() {
		if (rdfAnnotationNamespaces == null) {
			rdfAnnotationNamespaces = new HashMap<String, String>();
		}
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
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return (!isSetHistory() || history.isEmpty())
				&& (getNumCVTerms() == 0)
				&& (!isSetOtherAnnotationThanRDF() || (otherAnnotation.length() == 0));
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
	 * Checks if the Annotation is initialised.
	 *  
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
		if ((getNonRDFannotation() == null) && getListOfCVTerms().isEmpty()
				&& (getHistory() == null)) 
		{
			return false;
			
		} else if ((getNonRDFannotation() == null) && (getHistory() == null)
				&& !getListOfCVTerms().isEmpty()) 
		{

			for (int i = 0; i < getListOfCVTerms().size(); i++) {
				if (getCVTerm(i) != null) {
					return true;
				}
			}
		}
		return true;
	}

	/**
	 * Checks if the non RDF part of the Annotation is initialised.
	 *  
	 * <p>An Annotation is initialised if
	 *  there is some non RDF annotation
	 * <p>
	 * 
	 * @return true if the non RDF part of the Annotation is initialised.
	 */
	public boolean isSetNonRDFannotation() {
		if ((getNonRDFannotation() == null)) {
			return false;
		}
		
		return true;
	}

	/**
	 * Checks if the RDF part of the Annotation is initialised.
	 *  
	 * <p>An Annotation is initialised if
	 * at least one of the following is true :
	 * <li> one or more CVTerm are defined
	 * <li> there is an history defined.
	 * <p>
	 * 
	 * @return true if the RDF part of the Annotation is initialised
	 */
	public boolean isSetRDFannotation() {
		if (getListOfCVTerms().isEmpty() && (getHistory() == null)) {
			return false;
		} else if ((getHistory() == null) && !getListOfCVTerms().isEmpty()) {

			for (int i = 0; i < getListOfCVTerms().size(); i++) {
				if (getCVTerm(i) != null) {
					return true;
				}
			}
		}
		return true;
	}

	
	/**
	 * Returns true if there is some non RDF annotation.
	 * <p>Same as {@link #isSetNonRDFannotation()}
	 * 
	 * @return true if there is some non RDF annotation.
	 * @see #isSetNonRDFannotation()
	 * @deprecated please use {@link #isSetNonRDFannotation()}
	 */
	public boolean isSetOtherAnnotationThanRDF() {
		return isSetNonRDFannotation();
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
	 * Sets the value of the non RDF annotations
	 * 
	 * @param nonRDFAnnotation
	 */
	public void setNonRDFAnnotation(String nonRDFAnnotation) {
		otherAnnotation = new StringBuilder(nonRDFAnnotation);
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
				getAnnotationNamespaces().put(attribute.getNodeName(),
						attribute.getNodeValue());
			}
		}
	}

	/**
	 * Sets the annotationNamespaces.
	 * 
	 * @param annotationNamespaces the annotationNamespaces to set
	 */
	public void setAnnotationNamespaces(HashMap<String, String> annotationNamespaces) {
		this.annotationNamespaces = annotationNamespaces;
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
	 * @param rdfAnnotationNamespaces the rdfAnnotationNamespace {@link Map} to set.
	 */
	public void setRdfAnnotationNamespaces(HashMap<String, String> rdfAnnotationNamespaces) {
		this.rdfAnnotationNamespaces = rdfAnnotationNamespaces;
	}


	/**
	 * Clears the {@link List} of {@link CVTerm}s and removes unnecessary
	 * entries from the {@link #rdfAnnotationNamespaces}.
	 */
	public void unsetCVTerms() {
		if (listOfCVTerms != null) {
			listOfCVTerms.clear();
			for (Type type : CVTerm.Type.values()) {
				if (rdfAnnotationNamespaces.containsKey(type.getNamespaceURI())) {
					rdfAnnotationNamespaces.remove(type.getNamespaceURI());
				}
			}
		}
		listOfCVTerms = null;
	}

	/**
	 * Sets the {@link History} instance of this object to null.
	 */
	public void unsetHistory() {
		this.history = null;
	}

	/**
	 * Sets the non RDF annotation String to null.
	 */
	public void unsetNonRDFannotation() {
		if (isSetNonRDFannotation()) {
			otherAnnotation = null;
		}
	}
}
