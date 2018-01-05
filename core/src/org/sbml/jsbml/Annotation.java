/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2018 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.TreeNode;
import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;
import org.sbml.jsbml.CVTerm.Qualifier;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.TreeNodeAdapter;
import org.sbml.jsbml.util.TreeNodeChangeEvent;
import org.sbml.jsbml.util.filters.CVTermFilter;
import org.sbml.jsbml.xml.XMLAttributes;
import org.sbml.jsbml.xml.XMLNamespaces;
import org.sbml.jsbml.xml.XMLNode;
import org.sbml.jsbml.xml.XMLTriple;
import org.sbml.jsbml.xml.parsers.AnnotationWriter;
import org.sbml.jsbml.xml.parsers.SBMLRDFAnnotationParser;

/**
 * An Annotation represents the annotations of an {@link SBase} element. It
 * contains the list of {@link CVTerm} objects, a {@link Map} containing an XML
 * name space and a {@link String} containing all the annotation elements of
 * this name space.
 * 
 * @author Marine Dumousseau
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
public class Annotation extends AnnotationElement {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 2127495202258145900L;

  /**
   * The RDF syntax name space definition URI.
   */
  public static final transient String URI_RDF_SYNTAX_NS = "http://www.w3.org/1999/02/22-rdf-syntax-ns#"; //$NON-NLS-1$

  /**
   * Returns a {@link String} which represents the given {@link Qualifier}.
   * 
   * @param qualifier a {@code Qualifier}
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
   * The ModelHistory which represents the history section of a RDF
   * annotation
   */
  private History history;

  /**
   * contains all the CVTerm of the RDF annotation
   */
  private List<CVTerm> listOfCVTerms;

  /**
   * contains all the remaining annotation not mapped to Objects.
   * 
   */
  private XMLNode nonRDFannotation;


  /**
   * Creates an Annotation instance.<p> By default, the {@link History} and
   * otherAnnotation Strings are {@code null}. The list of {@link CVTerm}s and extensions are empty.
   * 
   */
  public Annotation() {
    super();

    nonRDFannotation = null;
    history = null;
  }

  /**
   * Creates a new Annotation instance by cloning the given Annotation.
   * 
   * @param annotation the annotation to be cloned.
   */
  public Annotation(Annotation annotation) {
    super(annotation);

    if (annotation.nonRDFannotation != null) {
      nonRDFannotation = annotation.nonRDFannotation.clone();
    }
    for (CVTerm term : annotation.getListOfCVTerms()) {
      getListOfCVTerms().add(term.clone());
    }
    if (annotation.isSetHistory()) {
      setHistory(annotation.getHistory().clone());
    }
  }

  /**
   * Creates an {@link Annotation} instance from a list of {@link CVTerm}
   * objects. By default, the {@link History} and otherAnnotation {@link String}s are
   * {@code null}. The {@link Map} extensions is empty.
   * 
   * @param cvTerms
   *            the list of {@link CVTerm}.
   */
  public Annotation(List<CVTerm> cvTerms) {
    this();
    listOfCVTerms = cvTerms;
  }

  /**
   * Creates an {@link Annotation} instance from a {@link String} containing non RDF
   * annotation. By default, the {@link History} is null, the list of {@link CVTerm}s
   * is empty. The {@link Map} extensions is empty.
   * 
   * @param annotation
   *            a {@link String} containing non RDF annotation, it will be parsed to
   *            create a {@link Map} containing an XML name space associated with a
   *            {@link String} representing all the annotation elements of
   *            this name space.
   * @throws XMLStreamException
   * 
   */
  public Annotation(String annotation) throws XMLStreamException {
    // parse the String as an XMLNode
    this(XMLNode.convertStringToXMLNode(annotation));
  }

  /**
   * Creates an {@link Annotation} instance from a {@link String} containing
   * non RDF annotation and a list of {@link CVTerm}. By default, the
   * {@link History} is {@code null}. The {@link Map} extensions is empty.
   * 
   * @param annotation
   *            a {@link String} containing non RDF annotation, it will be
   *            parsed to create a {@link Map} containing an XML name space
   *            associated with a {@link String} representing all the
   *            annotation elements of this name space.
   * @param cvTerms
   *            the {@link List} of {@link CVTerm}.
   * @throws XMLStreamException
   */
  public Annotation(String annotation, List<CVTerm> cvTerms) throws XMLStreamException {
    this(annotation);
    listOfCVTerms = cvTerms;
  }

  /**
   * 
   * @param annotation
   */
  public Annotation(XMLNode annotation) {
    this();
    nonRDFannotation = annotation;
  }

  /**
   * Adds a {@link CVTerm}.
   * 
   * @param cvTerm
   *            the {@link CVTerm} to add.
   * @return {@code true} if the 'cvTerm' element has been added to the {@link List}
   *         of {@link Qualifier}s.
   */
  public boolean addCVTerm(CVTerm cvTerm) {
    if (listOfCVTerms == null) {
      listOfCVTerms = new ArrayList<CVTerm>();
    }

    cvTerm.parent = this;
    boolean success = listOfCVTerms.add(cvTerm);
    firePropertyChange(TreeNodeChangeEvent.addCVTerm, null, cvTerm);
    return success;
  }

  /**
   * Adds an additional namespace to the set of declared namespaces of this
   * {@link SBase}.
   * 
   * @param prefix the prefix of the namespace to add
   * @param uri the namespace uri
   * 
   */
  public void addDeclaredNamespace(String prefix, String uri)
  {
    if (!isSetNonRDFannotation()) {
      nonRDFannotation = new XMLNode(new XMLTriple("annotation", null, null), new XMLAttributes());
    }

    nonRDFannotation.addNamespace(uri, prefix);
  }

  /**
   * Appends some 'annotation' to the non RDF annotation {@link XMLNode} of this object.
   * 
   * @param annotation some non RDF annotations.
   * @throws XMLStreamException
   */
  public void appendNonRDFAnnotation(String annotation) throws XMLStreamException {
    appendNonRDFAnnotation(XMLNode.convertStringToXMLNode(StringTools.toXMLAnnotationString(annotation)));
  }

  /**
   * @param annotationToAppend
   */
  public void appendNonRDFAnnotation(XMLNode annotationToAppend) {
    XMLNode oldNonRDFAnnotation = null;
    if (nonRDFannotation == null) {
      // check if the annotation contain an annotation top level element or not
      if (!annotationToAppend.getName().equals("annotation")) {
        XMLNode annotationXMLNode = new XMLNode(new XMLTriple("annotation", null, null), new XMLAttributes());
        annotationXMLNode.addChild(new XMLNode("\n  "));
        annotationXMLNode.addChild(annotationToAppend);
        annotationToAppend = annotationXMLNode;
        annotationToAppend.setParent(this);
      }

      nonRDFannotation = annotationToAppend;
    } else {
      oldNonRDFAnnotation = nonRDFannotation.clone();

      if (annotationToAppend.getName().equals("annotation")) {
        for (int i = 0; i < annotationToAppend.getChildCount(); i++) {
          XMLNode child = annotationToAppend.getChildAt(i);
          nonRDFannotation.addChild(child);
        }
      } else {
        nonRDFannotation.addChild(annotationToAppend);
      }
    }

    firePropertyChange(TreeNodeChangeEvent.nonRDFAnnotation,
      oldNonRDFAnnotation, nonRDFannotation);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#clone()
   */
  @Override
  public Annotation clone() {
    return new Annotation(this);
  }

  /**
   * @see org.sbml.jsbml.SBase#getHistory()
   * @return
   */
  private History createHistory() {
    history = new History();
    history.parent = this;
    history.addAllChangeListeners(getListOfTreeNodeChangeListeners());

    return history;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      Annotation annotation = (Annotation) object;
      equals &= isSetNonRDFannotation() == annotation.isSetNonRDFannotation();
      if (equals && isSetNonRDFannotation()) {
        equals &= nonRDFannotation.equals(annotation.getNonRDFannotation());
      }
      equals &= isSetAbout() == annotation.isSetAbout();
      if (equals && isSetAbout()) {
        equals &= getAbout().equals(annotation.getAbout());
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
    ArrayList<CVTerm> l = new ArrayList<CVTerm>();
    CVTermFilter filter = new CVTermFilter(qualifier);
    for (CVTerm term : getListOfCVTerms()) {
      if (filter.accepts(term)) {
        l.add(term);
      }
      if (term.getNestedCVTermCount() > 0) {
        l.addAll(filterCVTerms(qualifier, term.getListOfNestedCVTerms()));
      }
    }
    
    return l;
  }

  
  /**
   * Returns a list of CVTerm having the given qualifier.
   * 
   * @param qualifier the qualifier
   * @param terms the list of CVTerm to filter
   * @return a list of CVTerm having the given qualifier, an empty
   * list is returned if no CVTerm are found.
   */
  private List<CVTerm> filterCVTerms(Qualifier qualifier, List<CVTerm> terms) {
    ArrayList<CVTerm> l = new ArrayList<CVTerm>();
    CVTermFilter filter = new CVTermFilter(qualifier);
    for (CVTerm term : terms) {
      if (filter.accepts(term)) {
        l.add(term);
      }
      if (term.getNestedCVTermCount() > 0) {
        l.addAll(filterCVTerms(qualifier, term.getListOfNestedCVTerms()));
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
    List<String> l = new ArrayList<String>();
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


  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /**
   * Returns the {@link XMLNode} representing non RDF annotations.
   * 
   * @return the {@link XMLNode} representing non RDF annotations.
   * @deprecated
   */
  @Deprecated
  public XMLNode getAnnotationBuilder() {
    return nonRDFannotation;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int childIndex) {
    if (childIndex < 0) {
      throw new IndexOutOfBoundsException(MessageFormat.format(
        resourceBundle.getString("IndexSurpassesBoundsException"),
        childIndex, 0));
    }
    int pos = 0;
    if (isSetHistory()) {
      if (childIndex == pos) {
        return getHistory();
      }
      pos++;
    }
    if (isSetListOfCVTerms()) {
      if (childIndex == pos) {
        return new TreeNodeAdapter(getListOfCVTerms(), this);
      }
      pos++;
    }

    // TODO: could add all the XMLNode top level elements ??!?

    //		if (isSetNonRDFannotation()) {
    //			if (childIndex == pos) {
    //				return new TreeNodeAdapter(getNonRDFannotation());
    //			}
    //			pos++;
    //		}
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"),
      childIndex, Math.min(pos, 0)));
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = 0;
    if (isSetHistory()) {
      count++;
    }
    if (isSetListOfCVTerms()) {
      count++;
    }
    //		if (isSetNonRDFannotation()) {
    //			count++;
    //		}
    return count;
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
   * Gives the number of {@link CVTerm}s in this {@link Annotation}.
   * 
   * @return the number of controlled vocabulary terms in this {@link Annotation}.
   */
  public int getCVTermCount() {
    return isSetListOfCVTerms() ? listOfCVTerms.size() : 0;
  }

  /**
   * 
   * @return
   */
  public XMLNamespaces getDeclaredNamespaces() {
    return isSetNonRDFannotation() ? nonRDFannotation.getNamespaces() : null;
  }

  /**
   * 
   * 
   * @return
   */
  public XMLNode getFullAnnotation() {

    XMLNode nonRdfAnnotationClone = null;

    if (isSetNonRDFannotation()) {
      nonRdfAnnotationClone = nonRDFannotation.clone();
    }

    // TODO - get the list of AnnotationWriter from the manager
    List<AnnotationWriter> annotationParsers = new ArrayList<AnnotationWriter>();
    // hack to delete
    annotationParsers.add(new SBMLRDFAnnotationParser());

    // calling the annotation parsers so that they update the XMLNode before returning it to the user
    for (AnnotationWriter annoWriter : annotationParsers) {
      nonRdfAnnotationClone = annoWriter.writeAnnotation((SBase) getParent(), nonRdfAnnotationClone);
    }

    return nonRdfAnnotationClone;
  }


  /**
   * 
   * 
   * @return
   */
  public String getFullAnnotationString() {

    XMLNode fullAnnotation = getFullAnnotation();

    // System.out.println("getFullAnnotationString - " + fullAnnotation);

    if (fullAnnotation != null) {
      try {
        return fullAnnotation.toXMLString();
      } catch (XMLStreamException e) {
        Logger logger = Logger.getLogger(Annotation.class);
        if (logger.isDebugEnabled()) {
          e.printStackTrace();
        }
      }
    }

    return "";
  }

  /**
   * Returns the {@link History} of the Annotation.
   * 
   * @return the {@link History} of the Annotation.
   */
  public History getHistory() {
    if (!isSetHistory()) {
      createHistory();
    }

    return history;
  }

  /**
   * Returns the list of CVTerms. If they are no CVTerm, an empty list is returned.
   * 
   * @return the list of CVTerms.
   */
  public List<CVTerm> getListOfCVTerms() {
    if (listOfCVTerms == null) {
      listOfCVTerms = new ArrayList<CVTerm>(); // Should never happen, to remove ?
    }
    return listOfCVTerms;
  }

  /**
   * Returns the {@link XMLNode} containing annotations other than
   * the official RDF annotation, as defined in the SBML specifications.
   * 
   * @return the {@link XMLNode} containing annotations other than RDF
   *         annotation. Return null if there are none.
   */
  public XMLNode getNonRDFannotation() {
    if (nonRDFannotation != null) {
      return nonRDFannotation;
    }
    return null;
  }

  /**
   * Returns the String containing annotations other than RDF
   *         annotation.
   * 
   * @return the String containing annotations other than RDF
   *         annotation. Return null if there are none.
   */
  public String getNonRDFannotationAsString() {
    if (nonRDFannotation != null) {
      try {
        return nonRDFannotation.toXMLString();
      } catch (XMLStreamException e) {
        // nothing to do here ??
      }
    }
    return null;
  }

  /**
   * Gives the number of {@link CVTerm}s in this {@link Annotation}.
   * 
   * @return the number of controlled vocabulary terms in this {@link Annotation}.
   * @libsbml.deprecated use {@link #getCVTermCount()}
   */
  public int getNumCVTerms() {
    return getCVTermCount();
  }


  /**
   * 
   * @return
   */
  public XMLNode getXMLNode() {
    return getNonRDFannotation();
  }

  /**
   * 
   * @param nonRDFannotation
   */
  public void setXMLNode(XMLNode nonRDFannotation) {
    setNonRDFAnnotation(nonRDFannotation);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 809;
    int hashCode = super.hashCode();
    if (isSetNonRDFannotation()) {
      hashCode += prime * getNonRDFannotation().hashCode();
    }
    if (isSetAbout()) {
      hashCode += prime * about.hashCode();
    }
    return hashCode;
  }

  /**
   * 
   * @return
   */
  public boolean isEmpty() {
    return (!isSetHistory() || history.isEmpty())
        && (getNumCVTerms() == 0)
        && (!isSetNonRDFannotation() || (nonRDFannotation.getChildCount() == 0));
  }

  /**
   * Checks whether the 'about' element has been initialized.
   * 
   * @return {@code true} if the 'about' element has been initialized.
   */
  public boolean isSetAbout() {
    return about != null;
  }


  /**
   * Checks if the {@link Annotation} is initialized.
   * 
   * <p>An {@link Annotation} is initialized if
   * at least one of the following is true:
   * <ul>
   * <li> there is some non RDF annotation
   * <li> one or more {@link CVTerm} are defined
   * <li> there is an history defined.
   * </ul>
   * 
   * @return {@code true} if the Annotation is initialized
   */
  public boolean isSetAnnotation() {
    if ((getNonRDFannotation() == null) && getListOfCVTerms().isEmpty()
        && !isSetHistory())
    {
      return false;

    } else if ((getNonRDFannotation() == null) && !isSetHistory()
        && !getListOfCVTerms().isEmpty())
    {

      for (int i = 0; i < getListOfCVTerms().size(); i++) {
        if ((getCVTerm(i) != null) && getCVTerm(i).getResourceCount() > 0) {
          return true;
        }
      }
    }
    return true;
  }

  /**
   * Checks if the {@link History} is initialized
   * 
   * @return {@code true} if the {@link History} is initialized
   */
  public boolean isSetHistory() {
    return history != null && !history.isEmpty();
  }

  /**
   * Checks if the list of {@link CVTerm} is not empty.
   * 
   * @return {@code true} if there is one or more {@link CVTerm} defined.
   */
  public boolean isSetListOfCVTerms() {
    return (listOfCVTerms != null) && (listOfCVTerms.size() > 0);
  }

  /**
   * Checks if the non RDF part of the Annotation is initialized.
   * 
   * <p>An Annotation is initialized if
   *  there is some non RDF annotation
   * <p>
   * 
   * @return {@code true} if the non RDF part of the Annotation is initialized.
   */
  public boolean isSetNonRDFannotation() {
    if ((getNonRDFannotation() == null)) {
      return false;
    }

    return true;
  }

  /**
   * Returns {@code true} if there is some non RDF annotation.
   * <p>Same as {@link #isSetNonRDFannotation()}
   * 
   * @return {@code true} if there is some non RDF annotation.
   * @see #isSetNonRDFannotation()
   * @deprecated please use {@link #isSetNonRDFannotation()}
   */
  @Deprecated
  public boolean isSetOtherAnnotationThanRDF() {
    return isSetNonRDFannotation();
  }


  /**
   * Checks if the RDF part of the Annotation is initialized.
   * 
   * <p>An Annotation is initialized if
   * at least one of the following is true:
   * <ul>
   * <li> one or more CVTerm are defined
   * <li> there is an history defined.
   * </ul>
   * <p>
   * 
   * @return {@code true} if the RDF part of the Annotation is initialized
   */
  public boolean isSetRDFannotation() {
    if (getListOfCVTerms().isEmpty() && (!isSetHistory())) {
      return false;
    } else if ((!isSetHistory()) && !getListOfCVTerms().isEmpty()) {

      for (int i = 0; i < getListOfCVTerms().size(); i++) {
        if (getCVTerm(i) != null) {
          return true;
        }
      }
    }
    return true;
  }

  /**
   * Sets the about instance of this object if the attributeName is equal to
   * 'about'.
   * 
   * @param attributeName the attribute name.
   * @param prefix the attribute prefix.
   * @param value the attribute value.
   * @return {@code true} if an about XML attribute has been read
   */
  public boolean readAttribute(String attributeName, String prefix,
    String value) {
    // TODO: do we want to keep this method here ??!?
    if (attributeName.equals("about")) {
      setAbout(value);
      return true;
    }
    return false;
  }

  /**
   * Removes the given {@link CVTerm}.
   * 
   * @param cvTerm the {@link CVTerm} to remove
   * @return true if the {@link CVTerm} was successfully removed.
   */
  public boolean removeCVTerm(CVTerm cvTerm) {
    if (listOfCVTerms == null) {
      listOfCVTerms = new ArrayList<CVTerm>();
    }

    cvTerm.parent = null;
    boolean success = listOfCVTerms.remove(cvTerm);
    firePropertyChange(TreeNodeChangeEvent.removeCVTerm, cvTerm, null);

    return success;
  }



  /**
   * Removes the {@link CVTerm} at the given index.
   * 
   * @param index the index
   * @return the removed {@link CVTerm}.
   * @throws IndexOutOfBoundsException  if the index is out of range (index &lt; 0 || index &gt;= size())
   */
  public CVTerm removeCVTerm(int index) {
    if (listOfCVTerms == null) {
      listOfCVTerms = new ArrayList<CVTerm>();
    }

    CVTerm deletedCVTerm = listOfCVTerms.remove(index);
    deletedCVTerm.parent = null;
    firePropertyChange(TreeNodeChangeEvent.removeCVTerm, deletedCVTerm, null);

    return deletedCVTerm;
  }

  /**
   * Sets the value of the about String of this object.
   * 
   * @param about the about String to set.
   */
  public void setAbout(String about) {
    String oldAbout = this.about;
    this.about = about;
    firePropertyChange(TreeNodeChangeEvent.about, oldAbout, this.about);
  }

  /**
   * Changes the {@link History} instance to 'history'
   * 
   * @param history the history to set.
   */
  public void setHistory(History history) {
    History oldHistory = this.history;
    this.history = history;
    this.history.parent = this;
    this.history.addAllChangeListeners(getListOfTreeNodeChangeListeners());
    firePropertyChange(TreeNodeChangeEvent.history, oldHistory, this.history);
  }

  /**
   * Sets the value of the non RDF annotations
   * 
   * @param nonRDFAnnotationStr
   * @throws XMLStreamException
   */
  public void setNonRDFAnnotation(String nonRDFAnnotationStr) throws XMLStreamException {
    if (nonRDFAnnotationStr != null) {
      setNonRDFAnnotation(XMLNode.convertStringToXMLNode(StringTools.toXMLAnnotationString(nonRDFAnnotationStr)));
    }
  }

  /**
   * Sets the value of the non RDF annotations
   * 
   * @param nonRDFAnnotation
   */
  public void setNonRDFAnnotation(XMLNode nonRDFAnnotation) {
    XMLNode oldNonRDFAnnotation = null;

    if (nonRDFannotation != null) {
      oldNonRDFAnnotation = nonRDFannotation;
    }

    // test if the XMLNode has as first element 'annotation'
    if (!nonRDFAnnotation.getName().equals("annotation")) {
      XMLNode annotationXMLNode = new XMLNode(new XMLTriple("annotation", null, null), new XMLAttributes());
      annotationXMLNode.addChild(new XMLNode("\n  "));
      annotationXMLNode.addChild(nonRDFAnnotation);
      nonRDFAnnotation = annotationXMLNode;
    }

    nonRDFannotation = nonRDFAnnotation;
    nonRDFannotation.setParent(this);

    firePropertyChange(TreeNodeChangeEvent.nonRDFAnnotation,
      oldNonRDFAnnotation, nonRDFannotation);
  }

  /**
   * Clears the {@link List} of {@link CVTerm}s.
   */
  public void unsetCVTerms() {
    if (listOfCVTerms != null) {
      List<CVTerm> oldListOfCVTerms = listOfCVTerms;
      listOfCVTerms.clear();
      listOfCVTerms = null;
      firePropertyChange(TreeNodeChangeEvent.unsetCVTerms,
        oldListOfCVTerms, listOfCVTerms);
    }
  }

  /**
   * Sets the {@link History} instance of this object to {@code null}.
   */
  public void unsetHistory() {
    History oldHistory = null;
    if (history != null) {
      oldHistory = history;
    }
    history = null;
    firePropertyChange(TreeNodeChangeEvent.history, oldHistory, history);
  }

  /**
   * Sets the non RDF annotation String to {@code null}.
   */
  public void unsetNonRDFannotation() {
    XMLNode oldNonRDFAnnotation = null;

    if (isSetNonRDFannotation()) {
      oldNonRDFAnnotation = nonRDFannotation;
      nonRDFannotation = null;
      firePropertyChange(TreeNodeChangeEvent.nonRDFAnnotation,
        oldNonRDFAnnotation, nonRDFannotation);
    }
  }

}
