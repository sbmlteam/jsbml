/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2014 jointly by the following organizations:
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

import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.CVTerm.Qualifier;
import org.sbml.jsbml.ext.SBasePlugin;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.jsbml.util.TreeNodeWithChangeSupport;
import org.sbml.jsbml.util.ValuePair;
import org.sbml.jsbml.xml.XMLNode;

/**
 * The interface to implement for each SBML component.
 * 
 * @author Andreas Dr&auml;ger
 * @author Marine Dumousseau
 * @since 0.8
 * @version $Rev$
 */
public interface SBase extends TreeNodeWithChangeSupport {

  /**
   * Adds a {@link CVTerm}.
   * 
   * @param term the {@link CVTerm} to add.
   * @return {@code true} if a {@link CVTerm} instance has been added to
   *         the list of {@link CVTerm} of this object.
   */
  public boolean addCVTerm(CVTerm term);

  /**
   * Adds an additional name space to the set of declared namespaces of this
   * {@link SBase}.
   * 
   * @param prefix the prefix of the namespace to add
   * @param namespace the namespace to add
   * 
   */
  public void addDeclaredNamespace(String prefix, String namespace);

  /**
   * Adds a {@link SBasePlugin} extension object to this {@link SBase}.
   * 
   * <p>If a previous {@link SBasePlugin} associated with the same package
   * was present before, it will be replaced.
   * 
   * @param nameOrUri the name or URI of the package extension.
   * @param sbasePlugin the {@link SBasePlugin} to add.
   */
  public void addExtension(String nameOrUri, SBasePlugin sbasePlugin);

  /**
   * Adds an additional name space to the set of name spaces of this
   * {@link SBase} if the given name space is not yet present within this
   * {@link SortedSet}.
   * 
   * @param namespace the namespace to add
   */
  //  protected void setNamespace(String namespace);

  /**
   * Appends 'notes' to the notes String of this object.
   * 
   * @param notes
   * @throws XMLStreamException
   */
  public void appendNotes(String notes) throws XMLStreamException;

  /**
   * Appends 'notes' to the notes of this object.
   * 
   * @param notes
   */
  public void appendNotes(XMLNode notes);

  /**
   * Creates a deep copy of this object, i.e., a new {@link SBase} with the same properties
   * like this one.
   * 
   * @return a copy of this object
   */
  public SBase clone();

  /**
   * Creates a new {@link SBasePlugin} for the given package name or URI
   * and adds it to this {@link SBase}.
   * 
   * <p>If an {@link SBasePlugin} was already present in this {@link SBase}
   * it will be replaced.
   * 
   * @param nameOrUri the package name or URI
   * @return a new {@link SBasePlugin} for the given package name or URI
   */
  public SBasePlugin createPlugin(String nameOrUri);

  /**
   * Disables the given SBML Level 3 package on this {@link SBMLDocument}.
   * 
   * @param packageURIOrName a package namespace URI or package name
   */
  public void disablePackage(String packageURIOrName);

  /**
   * Enables the given SBML Level 3 package on this {@link SBMLDocument}.
   * 
   * @param packageURIOrName a package namespace URI or package name
   */
  public void enablePackage(String packageURIOrName);

  /**
   * Enables or disables the given SBML Level 3 package on this {@link SBMLDocument}.
   * 
   * @param packageURIOrName a package namespace URI or package name
   */
  public void enablePackage(String packageURIOrName, boolean enabled);

  /**
   * Returns {@code true} if and only if the given {@link SBase} has exactly the same
   * properties like this {@link SBase} instance.
   * 
   * @param sbase
   * @return {@code true} if and only if the given {@link Object} is an instance of
   *         {@link SBase} that has exactly the same properties like this
   *         SBase instance.
   */
  @Override
  public boolean equals(Object sbase);

  /**
   * This method returns a list of all qualifiers of the given type.
   * 
   * @param qualifier
   * @return
   */
  public List<CVTerm> filterCVTerms(Qualifier qualifier);

  /**
   * Queries the list of controlled vocabulary terms for those terms whose
   * qualifier is of the given type and selects only those resources from
   * these terms that contain the given pattern.
   * 
   * @param qualifier
   * @param pattern
   *            for instance, '.*kegg.*' or '.*chebi.*'.
   * @return
   * @see String#matches(String)
   */
  public List<String> filterCVTerms(Qualifier qualifier, String pattern);

  /**
   * A recursive implementation of {@link #filterCVTerms(Qualifier, String)}
   * that considers all child elements of the current instance of {@link SBase}
   * as well.
   * 
   * @param qualifier
   * @param pattern
   * @param recursive
   *        decides whether or not considering all child elements of this
   *        {@link SBase} and collecting the matching {@link CVTerm}s of
   *        all children recursively. If this argument is {@code false}, the
   *        behavior of the method will be equivalent to calling
   *        {@link #filterCVTerms(Qualifier, String)}.
   * @return A list of resources for the given {@link Qualifier} that match the
   *         given pattern.
   * @see #filterCVTerms(Qualifier, String)
   * @see String#matches(String)
   */
  public List<String> filterCVTerms(Qualifier qualifier, String pattern,
    boolean recursive);

  /**
   * @param qualifier
   * @param recursive
   * @param patterns
   *        an arbitrary list of patterns to be matched to the resources of each
   *        {@link CVTerm}.
   * @return
   * @see #filterCVTerms(Qualifier, String, boolean)
   * @see CVTerm#filterResources(String...)
   */
  public List<String> filterCVTerms(CVTerm.Qualifier qualifier, boolean recursive,
    String... patterns);

  /**
   * Returns the content of the 'annotation' sub-element of this object as an
   *         {@link Annotation} instance.
   * 
   * @return the content of the 'annotation' sub-element of this object as an
   *         {@link Annotation} instance.
   */
  public Annotation getAnnotation();

  /**
   * Returns the content of the 'annotation' sub-element of this object as a
   * String.
   * 
   * @return the content of the 'annotation' sub-element of this object as a
   * String.
   * @throws XMLStreamException
   */
  public String getAnnotationString() throws XMLStreamException;

  /**
   * 
   * @param index
   * @return the {@link CVTerm} instance at the position 'index' in the list of
   *         {@link CVTerm}s of this object.
   */
  public CVTerm getCVTerm(int index);

  /**
   * 
   * @return the list of {@link CVTerm}s of this object. If not yet set, this method
   *         initializes the annotation and returns an empty list.
   */
  public List<CVTerm> getCVTerms();

  /**
   * Returns all the namespaces declared on this object. These will be written on the
   * resulting XML element.
   * 
   * @return all the namespaces declared on this object. These will be written on the
   * resulting XML element.
   */
  public Map<String, String> getDeclaredNamespaces();

  /**
   * Returns the XML element name of this object.
   * 
   * @return the XML element name of this object.
   */
  public String getElementName();

  /**
   * Returns the {@link SBasePlugin} extension object which matches this package name or URI.
   * 
   * @param nameOrUri the package name or URI
   * @return the {@link SBasePlugin} extension object which matches this  package name or URI,
   * null is returned if nothing matching the name or URI is found.
   */
  public SBasePlugin getExtension(String nameOrUri);

  /**
   * Returns the map containing all the {@link SBasePlugin} extension objects
   * of this {@link SBase}.
   * 
   * @return the map containing all the {@link SBasePlugin} extension objects
   * of this {@link SBase}.
   */
  public Map<String, SBasePlugin> getExtensionPackages();

  /**
   * Returns the {@link History} instance of this object.
   * 
   * @return the {@link History} instance of this object.
   */
  public History getHistory();

  /**
   * Returns the SBML Level of the overall SBML document. Returns -1 if it is
   * not set.
   * 
   * @return the SBML level of this SBML object.
   * @see getVersion()
   */
  public int getLevel();

  /**
   * Returns the Level and Version combination of this {@link SBase}.
   * 
   * @return A {@link ValuePair} with the Level and Version of this
   *         {@link SBase}. Note that the returned {@link ValuePair} is never
   *         {@code null}, but if undeclared it may contain elements set to -1.
   */
  public ValuePair<Integer, Integer> getLevelAndVersion();

  /**
   * Returns the metaid of this element.
   * 
   * @return the metaid of this element.
   */
  public String getMetaId();

  /**
   * Returns the {@link Model} object in which the current {@link SBase} is located.
   * 
   * @return the {@link Model} object in which the current {@link SBase} is located.
   */
  public Model getModel();

  /**
   * Returns the namespace to which this {@link SBase} belong to.
   * 
   * @return the namespace to which this {@link SBase} belong to.
   */
  public String getNamespace();

  /**
   * Returns the {@code XMLNode} containing the notes sub-element of
   * this object.
   * 
   * @return the {@code XMLNode} containing the notes sub-element of
   *         this object.
   */
  public XMLNode getNotes();

  /**
   * Returns  the notes sub-element of this object as a {@link String}.
   * 
   * <p>If no notes are set, an empty {@link String} will be returned.
   * 
   * @return the notes sub-element of this object as a {@link String}. If no
   *         notes are set, an empty {@link String} will be returned.
   * @throws XMLStreamException
   */
  public String getNotesString() throws XMLStreamException;

  /**
   * Returns the number of {@link CVTerm}s of this {@link SBase}.
   * 
   * @return the number of {@link CVTerm}s of this {@link SBase}.
   * @libsbml.deprecated
   */
  public int getNumCVTerms();

  /**
   * Returns the number of {@link SBasePlugin}s of this {@link SBase}.
   * 
   * @return the number of {@link SBasePlugin}s of this {@link SBase}.
   * @libsbml.deprecated
   */
  public int getNumPlugins();

  /**
   * Returns the number of {@link CVTerm}s of this {@link SBase}.
   * 
   * @return the number of {@link CVTerm}s of this {@link SBase}.
   */
  public int getCVTermCount();

  /**
   * This method is convenient when holding an object nested inside other
   * objects in an SBML model. It allows direct access to the next SBML
   * element containing it.
   * 
   * @return the parent SBML object.
   * @see #getParent()
   */
  public SBase getParentSBMLObject();

  /**
   * Returns an {@link SBasePlugin} for an SBML Level 3 package extension
   * with the given package name or URI.
   * <p>If no {@link SBasePlugin} is found for this package, a new {@link SBasePlugin}
   * is created, added to this {@link SBase} and returned.
   * 
   * @param nameOrUri the name or URI of the package
   * @return an {@link SBasePlugin} for an SBML Level 3 package extension
   * with the given package name or URI.
   */
  public SBasePlugin getPlugin(String nameOrUri);

  /**
   * Returns the parent {@link SBMLDocument} object.
   * 
   * LibSBML uses the class {@link SBMLDocument} as a top-level container for storing
   * SBML content and data associated with it (such as warnings and error
   * messages). An SBML model in libSBML is contained inside an {@link SBMLDocument}
   * object. {@link SBMLDocument} corresponds roughly to the class 'sbml' defined in the
   * SBML Level 2 specification, but it does not have a direct correspondence
   * in SBML Level 1. (But, it is created by libSBML no matter whether the
   * model is Level 1 or Level 2.)
   * 
   * This method allows the {@link SBMLDocument} for the current object to be
   * retrieved.
   * 
   * @return the parent {@link SBMLDocument} object of this SBML object.
   */
  public SBMLDocument getSBMLDocument();

  /**
   * Grants access to the Systems Biology Ontology (SBO) term associated with
   * this {@link SBase}.
   * 
   * @return the SBOTerm attribute of this element.
   * @see SBO
   */
  public int getSBOTerm();

  /**
   * Grants access to the Systems Biology Ontology (SBO) term associated with
   * this {@link SBase}.
   * 
   * @return the SBO term ID of this element.
   * @see SBO
   */
  public String getSBOTermID();

  /**
   * Returns the Version within the SBML Level of the overall SBML document.
   * Return -1 if it is not set.
   * 
   * @return the SBML version of this SBML object.
   * @see getLevel()
   */
  public int getVersion();

  /**
   * 
   * @return
   * @see Object#hashCode()
   */
  @Override
  public int hashCode();

  /**
   * 
   * @return {@code true} if the {@link Annotation} 'about' {@link String} of this
   *         object matches the metaid of this object.
   */
  public boolean hasValidAnnotation();

  /**
   * Predicate returning {@code true} or {@code false} depending on whether this object's
   * level/version and name space values correspond to a valid SBML
   * specification.
   * 
   * @return
   */
  public boolean hasValidLevelVersionNamespaceCombination();

  /**
   * 
   * @return {@code true} if this object is extended by other packages.
   */
  public boolean isExtendedByOtherPackages();

  /**
   * Returns {@code true} if the given SBML Level 3 package is enabled within the containing {@link SBMLDocument}.
   * 
   * @param packageURIOrName the name or URI of the package extension.
   * @return {@code true} if the given SBML Level 3 package is enabled within the  containing {@link SBMLDocument}, {@code false} otherwise.
   */
  public boolean isPackageEnabled(String packageURIOrName);

  /**
   * Returns {@code true} if the given SBML Level 3 package is enabled within the containing {@link SBMLDocument}.
   * 
   * @param packageURIOrName the name or URI of the package extension.
   * @return {@code true} if the given SBML Level 3 package is enabled within the  containing {@link SBMLDocument}, {@code false} otherwise.
   * @libsbml.deprecated
   */
  public boolean isPackageURIEnabled(String packageURIOrName);

  /**
   * Returns {@code true} if the given SBML Level 3 package is enabled within the containing {@link SBMLDocument}.
   * 
   * @param packageURIOrName the name or URI of the package extension.
   * @return {@code true} if the given SBML Level 3 package is enabled within the  containing {@link SBMLDocument}, {@code false} otherwise.
   * @libsbml.deprecated
   * @deprecated use {@link #isPackageEnabled(String)}
   */
  @Deprecated
  public boolean isPkgEnabled(String packageURIOrName);

  /**
   * Returns {@code true} if the given SBML Level 3 package is enabled within the containing {@link SBMLDocument}.
   * 
   * @param packageURIOrName the name or URI of the package extension.
   * @return {@code true} if the given SBML Level 3 package is enabled within the  containing {@link SBMLDocument}, {@code false} otherwise.
   * @libsbml.deprecated
   * @deprecated use {@link #isPackageEnabled(String)}
   */
  @Deprecated
  public boolean isPkgURIEnabled(String packageURIOrName);


  /**
   * Predicate returning {@code true} or {@code false} depending on whether this object's
   * 'annotation' sub-element exists and has content.
   * 
   * @return {@code true} if the {@link Annotation} instance of this object is not
   *         {@code null} and contains at least one {@link CVTerm} or one
   *         {@link String} containing other annotations than RDF or a
   *         {@link History} instance.
   */
  public boolean isSetAnnotation();

  /**
   * 
   * @return {@code true} if the {@link Annotation} instance of this object
   */
  public boolean isSetHistory();

  /**
   * 
   * @return {@code true} if the level is not {@code null}.
   */
  public boolean isSetLevel();

  /**
   * Returns {@code true} if both, Level and Version are set for this
   * {@link SBase}.
   * 
   * @return {@code true} if {@link #isSetLevel()} and
   *         {@link #isSetVersion()}.
   */
  public boolean isSetLevelAndVersion();

  /**
   * Predicate returning {@code true} or {@code false} depending on whether this object's
   * 'metaid' attribute has been set.
   * 
   * @return {@code true} if the metaid is not {@code null}.
   */
  public boolean isSetMetaId();

  /**
   * Predicate returning {@code true} or {@code false} depending on whether this object's
   * 'notes' sub-element exists and has content.
   * 
   * @return {@code true} if the notes {@link String} is not {@code null}.
   */
  public boolean isSetNotes();

  /**
   * Check whether this {@link SBase} has been linked to a parent within the
   * hierarchical SBML data structure.
   * 
   * @return {@code true} if this {@link SBase} has a parent SBML object,
   *         {@code false} otherwise.
   * @see #getParentSBMLObject()
   */
  public boolean isSetParentSBMLObject();

  /**
   * Returns {@code true} if an {@link SBasePlugin} is defined
   * for the given package.
   * 
   * @param nameOrUri the package name or URI
   * @return {@code true} if an {@link SBasePlugin} is defined
   * for the given package.
   */
  public boolean isSetPlugin(String nameOrUri);

  /**
   * Return {@code true} if the SBOTerm is set.
   * 
   * @return {@code true} if the SBOTerm is set.
   * @see SBO
   */
  public boolean isSetSBOTerm();

  /**
   * 
   * @return {@code true} if the version is not {@code null}.
   */
  public boolean isSetVersion();

  /**
   * If the attribute is an id or name attribute, it will set the id or name
   * of this object with the value of the XML attribute ('value').
   * 
   * @param attributeName
   *           localName of the XML attribute
   * @param prefix
   *           prefix of the XML attribute
   * @param value
   *           value of the XML attribute
   * @return {@code true} if the attribute has been successfully read.
   */
  public boolean readAttribute(String attributeName, String prefix,
    String value);

  /**
   * Sets this object as SBML parent of 'sbase'. Check if the level and version
   * of sbase are set, otherwise sets the level and version of 'sbase' with
   * those of this object. This method should actually not be called by any tool
   * as it is used internally within JSBML to maintain the hierarchical document
   * structure.
   * 
   * If the level and version of sbase are set but not valid, an {@link Exception} is
   * thrown.
   */
  public void registerChild(SBase sbase) throws LevelVersionError;

  /**
   * Unregisters recursively the given SBase from the {@link Model}
   * and {@link SBMLDocument}.
   * 
   */
  public void unregister(SBase sbase);

  /**
   * Removes the given {@link CVTerm}.
   * 
   * @param cvTerm the {@link CVTerm} to remove
   * @return true if the {@link CVTerm} was successfully removed.
   */
  public boolean removeCVTerm(CVTerm cvTerm);

  /**
   * Removes the {@link CVTerm} at the given index.
   * 
   * @param index the index
   * @return the removed {@link CVTerm}.
   * @throws IndexOutOfBoundsException  if the index is out of range (index < 0 || index >= size())
   */
  public CVTerm removeCVTerm(int index);


  /**
   * Removes the given {@link TreeNodeChangeListener} from this element.
   * 
   * @param l
   */
  @Override
  public void removeTreeNodeChangeListener(TreeNodeChangeListener l);

  /**
   * Sets the value of the 'annotation' sub-element of this SBML object to a
   * copy of annotation given as an {@link Annotation} instance.
   * 
   * @param annotation
   */
  public void setAnnotation(Annotation annotation);

  /**
   * 
   * @return the {@link History} instance of this object.
   */
  public void setHistory(History history);

  /**
   * Sets the level of this object with 'level'. If the SBML parent of this
   * object is set and 'level' is different with the SBML parent level, an
   * {@link Exception} is thrown.
   * 
   * @param level
   */
  public void setLevel(int level);

  /**
   * Sets the metaid value with 'metaid'.
   * 
   * @param metaid
   * @throws PropertyNotAvailableException
   *             in Level 1.
   */
  public void setMetaId(String metaid);

  /**
   * Sets the notes with 'notes'.
   * 
   * @param notes
   * @throws XMLStreamException
   */
  public void setNotes(String notes) throws XMLStreamException;

  /**
   * Sets the {@code XMLNode} containing the notes sub-element of
   * this object.
   * 
   */
  public void setNotes(XMLNode notesXMLNode);


  // This method is protected now
  // public void setParentSBML(SBase parent);

  /**
   * Sets the value of the 'sboTerm' attribute.
   * 
   * @param term
   * @see SBO
   * @throws PropertyNotAvailableException in Level 1.
   */
  public void setSBOTerm(int term);

  /**
   * Sets the value of the 'sboTerm' attribute.
   * 
   * @param sboid
   * @see SBO
   */
  public void setSBOTerm(String sboid);

  /**
   * Sets this object as SBML parent of 'sbase'. Check if the level and version
   * of sbase are set, otherwise sets the level and version of 'sbase' with
   * those of this object. This method should actually not be called by any tool
   * as it is used internally within JSBML to maintain the hierarchical document
   * structure.
   * 
   * If the level and version of sbase are set but not valid, an {@link Exception} is
   * thrown.
   * 
   * @deprecated use {@link #registerChild(SBase)}
   */
  @Deprecated
  public void setThisAsParentSBMLObject(SBase sbase) throws LevelVersionError;

  /**
   * Sets the version of this object with 'version'. If the SBML parent of this
   * object is set and 'version' is different with the SBMLparent version, an
   * {@link Exception} is thrown.
   * 
   * @param level
   */
  public void setVersion(int version);

  /**
   * Unsets the value of the 'annotation' sub-element of this SBML object.
   */
  public void unsetAnnotation();

  /**
   * Unsets the list of {@link CVTerm} of this object.
   */
  public void unsetCVTerms();

  /**
   * Unsets the {@link SBasePlugin} extension object which matches this package name or URI.
   * 
   * @param nameOrUri the package name or URI
   */
  public void unsetExtension(String nameOrUri);

  /**
   * Unsets the {@link History} of this object.
   */
  public void unsetHistory();

  /**
   * Unsets the value of the 'metaid' attribute of this SBML object.
   */
  public void unsetMetaId();

  /**
   * Unsets the value of the 'notes' sub-element of this SBML object.
   */
  public void unsetNotes();

  /**
   * Unsets the {@link SBasePlugin} extension object which matches this package name or URI.
   * 
   * @param nameOrUri the package name or URI
   */
  public void unsetPlugin(String nameOrUri);

  /**
   * Unsets the value of the 'sboTerm' attribute of this SBML object.
   */
  public void unsetSBOTerm();

  /**
   * @return a {@link Map} containing the XML attributes of this object.
   */
  public Map<String, String> writeXMLAttributes();

}
