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
 * The interface to implement for each SBML element.
 * <p>
 * In addition to serving as the parent class for most other classes of objects
 * in SBML, this base type is designed to allow a modeler or a software package
 * to attach arbitrary information to each major element in an SBML model.
 * <p>
 * {@link SBase} has an optional subelement called 'notes'. It is intended to
 * serve as a place for storing optional information intended to be seen by
 * humans. An example use of the 'notes' element would be to contain formatted
 * user comments about the model element in which the 'notes' element is
 * enclosed. There are certain conditions on the XHTML content permitted inside
 * the 'notes' element; please consult the <a target='_blank'
 * href='http://sbml.org/Documents/Specifications'>SBML specification
 * document</a> corresponding to the SBML Level and Version of your model for
 * more information about the requirements for 'notes' content.
 * <p>
 * {@link SBase} has another optional subelement called 'annotation'. Whereas
 * the 'notes' element described above is a container for content to be shown
 * directly to humans, the 'annotation' element is a container for optional
 * software-generated content <em>not</em> meant to be shown to humans. The
 * element's content type is <a target='_blank'
 * href='http://www.w3.org/TR/2004/REC-xml-20040204/#elemdecls'>XML type
 * 'any'</a>, allowing essentially arbitrary data content. SBML places only a
 * few restrictions on the organization of the content; these are intended to
 * help software tools read and write the data as well as help reduce conflicts
 * between annotations added by different tools. As is the case with 'notes', it
 * is important to refer to the <a target='_blank'
 * href='http://sbml.org/Documents/Specifications'>SBML specification
 * document</a> corresponding to the SBML Level and Version of your model for
 * more information about the requirements for 'annotation' content.
 * <p>
 * It is worth pointing out that the 'annotation' element in the definition of
 * {@link SBase} exists in order that software developers may attach optional
 * application-specific data to the elements in an SBML model. However, it is
 * important that this facility not be misused. In particular, it is
 * <em>critical</em> that data essential to a model definition or that can be
 * encoded in existing SBML elements is <em>not</em> stored in 'annotation'.
 * {@link Parameter} values, functional dependencies between model elements,
 * etc., should not be recorded as annotations. It is crucial to keep in mind
 * the fact that data placed in annotations can be freely ignored by software
 * applications. If such data affects the interpretation of a model, then
 * software interoperability is greatly impeded.
 * <p>
 * SBML Level 2 introduced an optional {@link SBase} attribute named 'metaid'
 * for supporting metadata annotations using RDF (<a target='_blank'
 * href='http://www.w3.org/RDF/'>Resource Description Format</a>). The attribute
 * value has the data type <a href='http://www.w3.org/TR/REC-xml/#id'>XML
 * ID</a>, the XML identifier type, which means each 'metaid' value must be
 * globally unique within an SBML file. (Importantly, this uniqueness criterion
 * applies across any attribute with type <a
 * href='http://www.w3.org/TR/REC-xml/#id'>XML ID</a>, not just the 'metaid'
 * attribute used by SBML&mdash;something to be aware of if your
 * application-specific XML content inside the 'annotation' subelement happens
 * to use <a href='http://www.w3.org/TR/REC-xml/#id'>XML ID</a>.) The 'metaid'
 * value serves to identify a model component for purposes such as referencing
 * that component from metadata placed within 'annotation' subelements.
 * <p>
 * Beginning with SBML Level 2 Version 3, {@link SBase} also has an optional
 * attribute named 'sboTerm' for supporting the use of the Systems Biology
 * Ontology. In SBML proper, the data type of the attribute is a string of the
 * form 'SBO:NNNNNNN', where 'NNNNNNN' is a seven digit integer number; libSBML
 * simplifies the representation by only storing the 'NNNNNNN' integer portion.
 * Thus, in libSBML, the 'sboTerm' attribute on {@link SBase} has data type
 * {@code int}, and {@link SBO} identifiers are stored simply as integers. (For
 * convenience, {@link SBase} offers methods for returning both the integer form
 * and a text-string form of the {@link SBO} identifier.) {@link SBO} terms are
 * a type of optional annotation, and each different class of SBML object
 * derived from {@link SBase} imposes its own requirements about the values
 * permitted for 'sboTerm'. Please consult the SBML Level&nbsp;2 Version&nbsp;4
 * specification for more information about the use of {@link SBO} and the
 * 'sboTerm' attribute.
 * 
 * <p>
 * Beginning with SBML Level 3 Version 2, {@link SBase} also has two optional
 * attributes named 'id' and 'name'. So {@link SBase} kind of replace the interface
 * {@link NamedSBase} but NamedSBase is kept so that we can easily know which elements
 * had an id and a name before SBML L3V2.
 * 
 * 
 * @author Andreas Dr&auml;ger
 * @author Marine Dumousseau
 * @author Nicolas Rodriguez
 * @since 0.8
 * 
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
   * Adds a {@link SBasePlugin} extension object to this {@link SBase}.
   * 
   * <p>If a previous {@link SBasePlugin} associated with the same package
   * was present before, it will be replaced.
   * 
   * @param nameOrUri the name or URI of the package extension.
   * @param sbasePlugin the {@link SBasePlugin} to add.
   * @see #addExtension(String, SBasePlugin)
   */
  public void addPlugin(String nameOrUri, SBasePlugin sbasePlugin);

  /*
   * Adds an additional name space to the set of name spaces of this
   * {@link SBase} if the given name space is not yet present within this
   * {@link SortedSet}.
   * 
   * @param namespace the namespace to add
   */
  //  protected void setNamespace(String namespace);

  /**
   * Appends the given annotation to the 'annotation' subelement of this object.
   * Whereas the SBase 'notes' subelement is a container for content to be shown
   * directly to humans, the 'annotation' element is a container for optional
   * software-generated content not meant to be shown to humans. Every object
   * derived from SBase can have its own value for 'annotation'. The element's
   * content type is XML type 'any', allowing essentially arbitrary well-formed
   * XML data content.
   * SBML places a few restrictions on the organization of the content of
   * annotations; these are intended to help software tools read and write the
   * data as well as help reduce conflicts between annotations added by
   * different tools. Please see the SBML specifications for more details.
   * Unlike {@link SBase#setAnnotation(XMLNode)} or
   * {@link SBase#setAnnotation(String)}, this method allows other
   * annotations to be preserved when an application adds its own data.
   * 
   * @param annotation
   *        an XML string that is to be copied and appended to the content of
   *        the 'annotation' subelement of this object
   * @throws XMLStreamException
   *         thrown if the given annotation String cannot be parsed into
   *         {@link XMLNode} objects.
   * @see #appendAnnotation(XMLNode)
   */
  public void appendAnnotation(String annotation) throws XMLStreamException;


  /**
   * Appends the given annotation to the 'annotation' subelement of this object.
   * Whereas the SBase 'notes' subelement is a container for content to be shown
   * directly to humans, the 'annotation' element is a container for optional
   * software-generated content not meant to be shown to humans. Every object
   * derived from SBase can have its own value for 'annotation'. The element's
   * content type is XML type 'any', allowing essentially arbitrary well-formed
   * XML data content.
   * SBML places a few restrictions on the organization of the content of
   * annotations; these are intended to help software tools read and write the
   * data as well as help reduce conflicts between annotations added by
   * different tools. Please see the SBML specifications for more details.
   * Unlike {@link SBase#setAnnotation(XMLNode)} or
   * {@link SBase#setAnnotation(String)}, this method allows other
   * annotations to be preserved when an application adds its own data.
   * 
   * @param annotation
   *        an XML structure that is to be copied and appended to the content of
   *        the 'annotation' subelement of this object
   */
  public void appendAnnotation(XMLNode annotation);

  /**
   * Appends 'notes' to the notes String of this object.
   * 
   * @param notes
   *        the notes to be added.
   * @throws XMLStreamException
   *         if an error occurs while parsing the notes {@link String}.
   */
  public void appendNotes(String notes) throws XMLStreamException;

  /**
   * Appends 'notes' to the notes of this object.
   * 
   * @param notes the notes to be added.
   */
  public void appendNotes(XMLNode notes);

  /**
   * Creates a deep copy of this object, i.e., a new {@link SBase} with the same
   * properties
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
   * Enables or disables the given SBML Level 3 package on this
   * {@link SBMLDocument}.
   * 
   * @param packageURIOrName
   *        a package namespace URI or package name
   * @param enabled
   *        a boolean to tell if the package need to be enabled or disabled. It
   *        {@code true} the package will be enabled, otherwise it will be
   *        disabled.
   */
  public void enablePackage(String packageURIOrName, boolean enabled);

  /**
   * Returns {@code true} if and only if the given {@link SBase} has exactly the
   * same
   * properties like this {@link SBase} instance.
   * 
   * @param sbase
   *        the {@link SBase} to be compared to.
   * @return {@code true} if and only if the given {@link Object} is an instance
   *         of {@link SBase} that has exactly the same properties like this
   *         SBase instance.
   */
  @Override
  public boolean equals(Object sbase);

  /**
   * Returns a list of all the {@link CVTerm} with the given {@link Qualifier}.
   * 
   * @param qualifier {@link Qualifier} used to filter the {@link CVTerm}s.
   * @return a list of all the {@link CVTerm} with the given {@link Qualifier}.
   */
  public List<CVTerm> filterCVTerms(Qualifier qualifier);

  /**
   * Queries the list of controlled vocabulary terms ({@link CVTerm}) for those
   * terms whose {@link Qualifier} is of the given type and selects only those
   * resources from
   * these terms that contain the given pattern.
   * 
   * @param qualifier
   *        {@link Qualifier} used to filter the {@link CVTerm}s.
   * @param pattern
   *        a regexp pattern, for instance, '.*kegg.*' or '.*chebi.*'.
   * @return a list of resource URIs that matches the pattern.
   * @see Pattern
   */
  public List<String> filterCVTerms(Qualifier qualifier, String pattern);

  /**
   * Returns a list of resource URIs for the given {@link Qualifier} that match
   * the
   * given pattern.
   * <p>
   * This is a recursive implementation of
   * {@link #filterCVTerms(Qualifier, String)} that considers all child elements
   * of the current instance of {@link SBase} as well.
   * 
   * @param qualifier
   *        {@link Qualifier} used to filter the {@link CVTerm}s.
   * @param pattern
   *        a regexp pattern, for instance, '.*kegg.*' or '.*chebi.*'.
   * @param recursive
   *        decides whether or not to consider all child elements of this
   *        {@link SBase} and collecting the matching {@link CVTerm}s of
   *        all children recursively. If this argument is {@code false}, the
   *        behavior of the method will be equivalent to calling
   *        {@link #filterCVTerms(Qualifier, String)}.
   * @return a list of resources for the given {@link Qualifier} that match the
   *         given pattern.
   * @see #filterCVTerms(Qualifier, String)
   * @see Pattern
   */
  public List<String> filterCVTerms(Qualifier qualifier, String pattern,
    boolean recursive);

  /**
   * Returns a list of resource URIs for the given {@link Qualifier} that match
   * the
   * given patterns.
   * 
   * @param qualifier
   *        {@link Qualifier} used to filter the {@link CVTerm}s.
   * @param recursive
   *        boolean used to decides whether or not to consider all child
   *        elements of this {@link SBase} and collecting the matching
   *        {@link CVTerm}s of
   *        all children recursively.
   * @param patterns
   *        an arbitrary list of patterns to be matched to the resources of each
   *        {@link CVTerm}.
   * @return a list of resources for the given {@link Qualifier} that match the
   *         given pattern.
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
   *         String.
   * @throws XMLStreamException
   *         if an error occurs while writing the {@link XMLNode} to a String
   */
  public String getAnnotationString() throws XMLStreamException;

  /**
   * Returns the {@link CVTerm} instance at the position 'index' in the list of
   * {@link CVTerm}s of this object.
   * 
   * @param index
   *        index of the element to return
   * @return the {@link CVTerm} instance at the position 'index' in the list of
   *         {@link CVTerm}s of this object.
   * @throws IndexOutOfBoundsException
   *         if the index is out of range (index &lt; 0 || index &gt;= size())
   *         or if the
   *         list of {@link CVTerm} is not set.
   */
  public CVTerm getCVTerm(int index);

  /**
   * Returns the list of {@link CVTerm}s of this object.
   * 
   * @return the list of {@link CVTerm}s of this object. If not yet set, this
   *         method
   *         initializes the annotation and returns an empty list.
   */
  public List<CVTerm> getCVTerms();

  /**
   * Returns all the namespaces declared on this object. These will be written
   * on the
   * resulting XML element.
   * 
   * @return all the namespaces declared on this object. These will be written
   *         on the
   *         resulting XML element.
   */
  public Map<String, String> getDeclaredNamespaces();

  /**
   * Returns the first child element found that has the given id.
   * 
   * <p>This operation searches the model-wide SId identifier type namespace.
   * So it will not find for example {@link LocalParameter} or {@link UnitDefinition}.<p/>
   * 
   * <p>This method is here for compatibility with libSBML, it is less efficient than
   * the methods that are located in the {@link Model} and {@link SBMLDocument} classes.</p>
   *
   * <p>If you want to get an {@link SBase} that is not in the SId namespace,
   * you can use the filter methods (for example: {@link #filter(Filter)})
   * using the {@link IdFilter} filter.
   * 

   * @param id string representing the id of the {@link SBase} to find.
   * @return the first child element found that has the given id.
   * @see Model#getSBaseById(String)
   */
  public SBase getElementBySId(String id);

  /**
   * Returns the first child element found that has the given metaid.
   * 
   * <p>This method is here for compatibility with libSBML, it is less efficient than
   * the methods that are located in the {@link Model} and {@link SBMLDocument} classes.</p>
   * 
   * @param metaid string representing the metaid of the {@link SBase} to find.
   * @return the first child element found that has the given metaid.
   * @see SBMLDocument#findSBase(String)
   */
  public SBase getElementByMetaId(String metaid);

  /**
   * Returns the XML element name of this object.
   * 
   * @return the XML element name of this object.
   */
  public String getElementName();

  // TODO - add getExtension(int) and getPlugin(int) ??

  /**
   * Returns the {@link SBasePlugin} extension object which matches this package
   * name or URI.
   * 
   * @param nameOrUri
   *        the package name or URI
   * @return the {@link SBasePlugin} extension object which matches this package
   *         name or URI,
   *         null is returned if nothing matching the name or URI is found.
   */
  public SBasePlugin getExtension(String nameOrUri);

  /**
   * Returns the number of {@link SBasePlugin}s of this {@link SBase}.
   * 
   * @return the number of {@link SBasePlugin}s of this {@link SBase}.
   */
  public int getExtensionCount();

  /**
   * Returns the map containing all the {@link SBasePlugin} extension objects
   * of this {@link SBase}.
   * 
   * @return the map containing all the {@link SBasePlugin} extension objects
   * of this {@link SBase}.
   */
  public Map<String, SBasePlugin> getExtensionPackages(); // TODO - remove this method to prevent access to the map directly ?? Or provide a copy of the Map

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
   * @see #getVersion()
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
   * Returns the {@link Model} object in which the current {@link SBase} is
   * located.
   * 
   * @return the {@link Model} object in which the current {@link SBase} is
   *         located.
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
   * Returns the notes sub-element of this object as a {@link String}.
   * <p>
   * If no notes are set, an empty {@link String} will be returned.
   * 
   * @return the notes sub-element of this object as a {@link String}. If no
   *         notes are set, an empty {@link String} will be returned.
   * @throws XMLStreamException
   *         if an error occurs while writing the {@link XMLNode} to a String.
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
   * Returns the name of the SBML Level 3 package in which this element is defined, as
   * defined in <a href="http://sbml.org/Community/Wiki"> the sbml.org community wiki</a>.
   * 
   * <p> For example, the string "core" will be returned if this element is defined in SBML Level 3 Core.
   * 
   * @return the name of the SBML Level 3 package in which this element is defined.
   */
  public String getPackageName();

  /**
   * Returns the version of the SBML Level 3 package to which this element belongs.
   * 
   * <p>The value 0 will be returned if this element belongs to the SBML Level 3 Core package.
   * The value -1 will be returned if this element does not belong to the SBML Level core and the
   * value has not been set properly.
   * 
   * @return the version of the SBML Level 3 package to which this element belongs.
   */
  public int getPackageVersion();

  /**
   * Returns the parent of this {@link SBase}.
   * 
   * @return the parent SBML object.
   * @see #getParent()
   */
  public SBase getParentSBMLObject();

  /**
   * Returns an {@link SBasePlugin} for an SBML Level 3 package extension
   * with the given package name or URI.
   * <p>
   * If no {@link SBasePlugin} is found for this package, a new
   * {@link SBasePlugin} is created, added to this {@link SBase} and returned.
   * 
   * @param nameOrUri
   *        the name or URI of the package
   * @return an {@link SBasePlugin} for an SBML Level 3 package extension
   *         with the given package name or URI.
   */
  public SBasePlugin getPlugin(String nameOrUri);

  /**
   * Returns the parent {@link SBMLDocument} object.
   * LibSBML uses the class {@link SBMLDocument} as a top-level container for
   * storing
   * SBML content and data associated with it (such as warnings and error
   * messages). An SBML model in libSBML is contained inside an
   * {@link SBMLDocument} object. {@link SBMLDocument} corresponds roughly to
   * the class 'sbml' defined in the
   * SBML Level 2 specification, but it does not have a direct correspondence
   * in SBML Level 1. (But, it is created by libSBML no matter whether the
   * model is Level 1 or Level 2.)
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
   * Returns the namespace to which this {@link SBase} belong to. Same as {@link #getNamespace()}.
   * 
   * <p>For example, all elements that belong to Layout Extension Version 1 for SBML Level 3 Version 1 Core
   * must have the URI 'http://www.sbml.org/sbml/level3/version1/layout/version1'.
   * <p>The elements that belong to SBML core might return null.
   * 
   * @return the namespace to which this {@link SBase} belong to.
   * @see SBase#getNamespace()
   * @libsbml.deprecated
   */
  public String getURI();

  /**
   * Returns the Version within the SBML Level of the overall SBML document.
   * Return -1 if it is not set.
   * 
   * @return the SBML version of this SBML object.
   * @see #getLevel()
   */
  public int getVersion();


  /**
   * Returns a hash code value for this {@link SBase} instance.
   *
   * @return a hash code value for this {@link SBase} instance.
   * @see Object#hashCode()
   */
  @Override
  public int hashCode();

  /**
   * Returns {@code true} if the {@link Annotation} RDF 'about' attribute
   * matches the metaid of this object.
   * 
   * @return {@code true} if the {@link Annotation} 'about' {@link String} of
   *         this
   *         object matches the metaid of this object.
   */
  public boolean hasValidAnnotation();

  /**
   * Returns {@code true} or {@code false} depending on whether this object's
   * level/version and namespace values correspond to a valid SBML
   * specification.
   * 
   * @return {@code true} if this object's level, version and namespace values
   * correspond to a valid SBML specification.
   */
  public boolean hasValidLevelVersionNamespaceCombination();

  /**
   * Returns {@code true} if this object is extended by other packages.
   * 
   * @return {@code true} if this object is extended by other packages.
   */
  public boolean isExtendedByOtherPackages();

  /**
   * Returns {@code true} if the given SBML Level 3 package is enabled within
   * the containing {@link SBMLDocument}.
   * 
   * @param packageURIOrName
   *        the name or URI of the package extension.
   * @return {@code true} if the given SBML Level 3 package is enabled within
   *         the containing {@link SBMLDocument}, {@code false} otherwise.
   */
  public boolean isPackageEnabled(String packageURIOrName);

  /**
   * Returns {@code true} if the given SBML Level 3 package is enabled within
   * the containing {@link SBMLDocument}.
   * 
   * @param packageURIOrName
   *        the name or URI of the package extension.
   * @return {@code true} if the given SBML Level 3 package is enabled within
   *         the containing {@link SBMLDocument}, {@code false} otherwise.
   * @libsbml.deprecated
   */
  public boolean isPackageURIEnabled(String packageURIOrName);

  /**
   * Returns {@code true} if the given SBML Level 3 package is enabled within
   * the containing {@link SBMLDocument}.
   * 
   * @param packageURIOrName
   *        the name or URI of the package extension.
   * @return {@code true} if the given SBML Level 3 package is enabled within
   *         the containing {@link SBMLDocument}, {@code false} otherwise.
   * @libsbml.deprecated
   * @deprecated use {@link #isPackageEnabled(String)}
   */
  @Deprecated
  public boolean isPkgEnabled(String packageURIOrName);

  /**
   * Returns {@code true} if the given SBML Level 3 package is enabled within
   * the containing {@link SBMLDocument}.
   * 
   * @param packageURIOrName
   *        the name or URI of the package extension.
   * @return {@code true} if the given SBML Level 3 package is enabled within
   *         the containing {@link SBMLDocument}, {@code false} otherwise.
   * @libsbml.deprecated
   * @deprecated use {@link #isPackageEnabled(String)}
   */
  @Deprecated
  public boolean isPkgURIEnabled(String packageURIOrName);

  /**
   * Returns {@code true} or {@code false} depending on whether this object's
   * 'annotation' sub-elements exists and have some content.
   * 
   * @return {@code true} if the {@link Annotation} instance of this object is
   *         not {@code null} and contains at least one {@link CVTerm} or one
   *         {@link String} containing other annotations than RDF or a
   *         {@link History} instance.
   */
  public boolean isSetAnnotation();

  /**
   * Returns {@code true} if the {@link History} instance of this object is set.
   * 
   * @return {@code true} if the {@link History} instance of this object is set.
   */
  public boolean isSetHistory();

  /**
   * Returns {@code true} if the level is set.
   * 
   * @return {@code true} if the level is set.
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
   * Returns {@code true} or {@code false} depending on whether this object's
   * 'metaid' attribute has been set.
   * 
   * @return {@code true} if the metaid is not {@code null}.
   */
  public boolean isSetMetaId();

  /**
   * Returns {@code true} or {@code false} depending on whether this object's
   * 'notes' sub-element exists and has content.
   * 
   * @return {@code true} if the notes {@link String} is not {@code null}.
   */
  public boolean isSetNotes();


  /**
   * Returns {@code true} if the package version is not equals to '-1'.
   * 
   * @return {@code true} if the package version is not equals to '-1'.
   */
  public boolean isSetPackageVErsion();

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
   * Returns {@code true} if the SBOTerm is set.
   * 
   * @return {@code true} if the SBOTerm is set.
   * @see SBO
   */
  public boolean isSetSBOTerm();

  /**
   * Returns {@code true} if the version is not {@code null}.
   * 
   * @return {@code true} if the version is not {@code null}.
   */
  public boolean isSetVersion();

  /**
   * Sets the given attribute in this {@link SBase}.
   * 
   * <p>If the given attribute name is not recognized, nothing is done and
   * {@code false} is returned.
   * 
   * @param attributeName
   *           localName of the XML attribute
   * @param prefix
   *           prefix of the XML attribute
   * @param value
   *           value of the XML attribute
   * @return {@code true} if the attribute has been successfully read.
   */
  public boolean readAttribute(String attributeName, String prefix, String value);

  /**
   * Sets this object as SBML parent of 'sbase'. Check if the level and version
   * of sbase are set, otherwise sets the level and version of 'sbase' with
   * those of this object. This method should actually not be called by any tool
   * as it is used internally within JSBML to maintain the hierarchical document
   * structure.
   * If the level and version of sbase are set but not valid, an
   * {@link Exception} is thrown.
   * 
   * @param sbase
   *        the {@link SBase} to be registered.
   * @return {@code true} if the element could successfully be registered,
   *         {@code false} otherwise, e.g., if the element has already been
   *         registered.
   * @throws LevelVersionError
   *         In case the given {@link SBase} has a different, but defined
   *         Level/Version combination than this current {@link SBase}, an
   *         {@link LevelVersionError} is thrown.
   */
  public boolean registerChild(SBase sbase) throws LevelVersionError;

  /**
   * Unregisters recursively the given SBase from the {@link Model}
   * and {@link SBMLDocument}.
   * 
   * @param sbase the {@link SBase} to be unregistered.
   */
  public void unregisterChild(SBase sbase);

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
   * @param index
   *        the index
   * @return the removed {@link CVTerm}.
   * @throws IndexOutOfBoundsException
   *         if the index is out of range (index &lt; 0 || index &gt;= size())
   */
  public CVTerm removeCVTerm(int index);

  /**
   * Removes a namespace from the set of declared namespaces of this
   * {@link SBase}.
   * 
   * @param namespace the namespace to remove
   */
  public void removeDeclaredNamespaceByNamespace(String namespace);
  
  /**
   * Removes a namespace from the set of declared namespaces of this
   * {@link SBase}.
   * 
   * @param prefix
   *        the prefix of the namespace to remove
   */  
  public void removeDeclaredNamespaceByPrefix(String prefix);
    
  /**
   * Removes the given {@link TreeNodeChangeListener} from this element.
   * 
   * @param listener the listener to be removed.
   */
  @Override
  public void removeTreeNodeChangeListener(TreeNodeChangeListener listener);

  /**
   * Sets the value of the 'annotation' sub-element of this SBML object to a
   * copy of annotation given as an {@link Annotation} instance.
   * 
   * @param annotation the annotation of this {@link SBase}
   */
  public void setAnnotation(Annotation annotation);

  /**
   * Sets the non RDF part of the annotation.
   * 
   * @param nonRDFAnnotation an XMLNode
   */
  public void setAnnotation(XMLNode nonRDFAnnotation);

  /**
   * Sets the non RDF part of the annotation.
   * 
   * @param nonRDFAnnotation a String representing a piece of XML.
   * @throws XMLStreamException - if any problem occurs while reading the XML.
   */
  public void setAnnotation(String nonRDFAnnotation) throws XMLStreamException;

  /**
   * Sets the history.
   * 
   * @param history the history of this {@link SBase}
   */
  public void setHistory(History history);

  /**
   * Sets the level of this object with 'level'. If the SBML parent of this
   * object is set and 'level' is different with the SBML parent level, an
   * {@link Exception} is thrown.
   * 
   * @param level the SBML level.
   */
  public void setLevel(int level);

  /**
   * Sets the metaid value with 'metaid'.
   * 
   * @param metaid
   *        the meatId to be set.
   * @throws IllegalArgumentException
   *         if the given metaid does not follow the pattern for valid metaids
   *         ore if it is already used in the {@link SBMLDocument}
   * @throws PropertyNotAvailableException
   *         in SBML level 1, as this attribute was introduced only from SBML
   *         level 2.
   */
  public void setMetaId(String metaid) throws IllegalArgumentException;

  /**
   * Sets the notes with 'notes'.
   * 
   * @param notes the notes for this {@link SBase}.
   * @throws XMLStreamException if an error occurs while parsing the notes.
   */
  public void setNotes(String notes) throws XMLStreamException;

  /**
   * Sets the {@code XMLNode} containing the notes sub-element of
   * this object.
   * 
   * @param notesXMLNode the notes for this {@link SBase}.
   */
  public void setNotes(XMLNode notesXMLNode);

  /**
   * Sets the version of the SBML Level 3 package to which this element belongs.
   * 
   * <p>Use with caution, only if you know what you are doing. This should be set automatically.
   * 
   * @param packageVersion the version of the SBML Level 3 package to which this element belongs.
   */
  public void setPackageVersion(int packageVersion);

  // This method is protected now
  // public void setParentSBML(SBase parent);

  /**
   * Sets the value of the 'sboTerm' attribute.
   * <p>
   * Beginning with SBML Level 2 Version 3, objects derived from {@link SBase}
   * have an optional attribute named 'sboTerm' for supporting the use of the
   * Systems Biology Ontology. In SBML proper, the data type of the attribute is
   * a string of the form 'SBO:NNNNNNN', where 'NNNNNNN' is a seven digit
   * integer number; JSBML simplifies the representation by only storing the
   * 'NNNNNNN' integer portion. Thus, in JSBML, the 'sboTerm' attribute on
   * {@link SBase} has data type {@code int}, and {@link SBO} identifiers are
   * stored simply as integers.
   * <p>
   * {@link SBO} terms are a type of optional annotation, and each different
   * class of SBML object derived from {@link SBase} imposes its own
   * requirements about the values permitted for 'sboTerm'. Please consult the
   * SBML Level&nbsp;2 Version&nbsp;4 specification for more information about
   * the use of {@link SBO} and the 'sboTerm' attribute.
   * 
   * @param term
   *        the NNNNNNN integer portion of the {@link SBO} identifier
   * @see SBO
   * @throws PropertyNotAvailableException
   *         in Level 1.
   */
  public void setSBOTerm(int term);

  /**
   * Sets the value of the 'sboTerm' attribute.
   * <p>
   * Beginning with SBML Level 2 Version 3, objects derived from {@link SBase}
   * have an optional attribute named 'sboTerm' for supporting the use of the
   * Systems Biology Ontology. In SBML proper, the data type of the attribute is
   * a string of the form 'SBO:NNNNNNN', where 'NNNNNNN' is a seven digit
   * integer number; JSBML simplifies the representation by only storing the
   * 'NNNNNNN' integer portion. Thus, in JSBML, the 'sboTerm' attribute on
   * {@link SBase} has data type {@code int}, and {@link SBO} identifiers are
   * stored simply as integers.
   * <p>
   * {@link SBO} terms are a type of optional annotation, and each different
   * class of SBML object derived from {@link SBase} imposes its own
   * requirements about the values permitted for 'sboTerm'. Please consult the
   * SBML Level&nbsp;2 Version&nbsp;4 specification for more information about
   * the use of {@link SBO} and the 'sboTerm' attribute.
   * 
   * @param sboid
   *        the {@link SBO} identifier of the form 'SBO:NNNNNNN'
   * @see SBO
   */
  public void setSBOTerm(String sboid);

  /**
   * Sets this object as SBML parent of 'sbase'. Check if the level and version
   * of sbase are set, otherwise sets the level and version of 'sbase' with
   * those of this object. This method should actually not be called by any tool
   * as it is used internally within JSBML to maintain the hierarchical document
   * structure.
   * If the level and version of sbase are set but not valid, an
   * {@link Exception} is
   * thrown.
   * 
   * @param sbase
   *        the child {@link SBase}
   * @throws LevelVersionError - if the parent and the child have a different level and version.
   * @deprecated use {@link #registerChild(SBase)}
   */
  @Deprecated
  public void setThisAsParentSBMLObject(SBase sbase) throws LevelVersionError;

  /**
   * Sets the version of this object with 'version'. If the SBML parent of this
   * object is set and 'version' is different with the SBMLparent version, an
   * {@link Exception} is thrown.
   * 
   * @param version the SBML version
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
   * Unsets the {@link SBasePlugin} extension object which matches this package
   * name or URI.
   * 
   * @param nameOrUri
   *        the package name or URI
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
   * Unsets the {@link SBasePlugin} extension object which matches this package
   * name or URI.
   * 
   * @param nameOrUri
   *        the package name or URI
   */
  public void unsetPlugin(String nameOrUri);

  /**
   * Unsets the value of the 'sboTerm' attribute of this SBML object.
   */
  public void unsetSBOTerm();

  /**
   * Returns a map with all the attributes of this {@link SBase} that
   * need to be written out in XML.
   * <p>
   * The attribute name is used as a key and the attribute value as value. If a
   * prefix is needed for the attribute name, it need to be set directly in this
   * map.
   * 
   * @return a {@link Map} containing the XML attributes of this object.
   */
  public Map<String, String> writeXMLAttributes();

  /**
   * Returns the id of the element if it is set, an empty string otherwise.
   * 
   * @return the id of the element if it is set, an empty string otherwise.
   */
  public String getId();

  /**
   * Returns the name of the element if it is set, an empty string otherwise.
   * 
   * @return the name of the element if it is set, an empty string otherwise.
   */
  public String getName();

  /**
   * Returns {@code true}  if the identifier of this
   * {@link SBase} is required to be defined (i.e., not {@code null})
   * in the definition of SBML.
   * 
   * @return {@code true} if the identifier of this element must be set in
   *         order to create a valid SBML representation. {@code false}
   *         otherwise, i.e., if the identifier can be understood as an optional
   *         attribute.
   */
  public boolean isIdMandatory();

  /**
   * Returns {@code true} if the id is not {@code null}.
   * 
   * @return {@code true} if the id is not {@code null}.
   */
  public boolean isSetId();

  /**
   * Returns {@code true} if the name is not {@code null}.
   * 
   * @return {@code true} if the name is not {@code null}.
   */
  public boolean isSetName();

  /**
   * Sets the id value with 'id'
   * 
   * @param id the id to set
   */
  public void setId(String id);

  /**
   * Sets the name value with 'name'. If level is 1, sets automatically the id
   * to 'name' as well.
   * 
   * @param name the name to set
   */
  public void setName(String name);

  /**
   * Sets the id value to {@code null}.
   */
  public void unsetId();

  /**
   * Sets the name value to {@code null}.
   */
  public void unsetName();

}
