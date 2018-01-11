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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.tree.TreeNode;
import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.TreeNodeAdapter;
import org.sbml.jsbml.util.TreeNodeChangeEvent;
import org.sbml.jsbml.xml.XMLAttributes;
import org.sbml.jsbml.xml.XMLNode;

/**
 * Contains all the MIRIAM URIs for a MIRIAM qualifier in the annotation element
 * of a SBML component.
 * 
 * @author Andreas Dr&auml;ger
 * @author Marine Dumousseau
 * @author Nicolas Rodriguez
 * @since 0.8
 */
public class CVTerm extends AnnotationElement {

  /**
   * This {@code enum} list all the possible MIRIAM qualifiers.
   * @doc.note See http://co.mbine.org/standards/qualifiers
   * 
   */
  public static enum Qualifier {
    /**
     * Represents the MIRIAM biological qualifier 'encodes': the biological
     * entity represented by the model element encodes, directly or
     * transitively, the subject of the referenced resource (biological entity
     * B). This relation may be used to express, for example, that a specific
     * DNA sequence encodes a particular protein.
     */
    BQB_ENCODES("encodes"),
    /**
     * Represents the MIRIAM biological qualifier 'hasPart': the biological
     * entity represented by the model element includes the subject of the
     * referenced resource (biological entity B), either physically or
     * logically. This relation might be used to link a complex to the
     * description of its components.
     */
    BQB_HAS_PART("hasPart"),
    /**
     * Represents the MIRIAM biological qualifier 'hasProperty': the subject of
     * the referenced resource (biological entity B) is a property of the
     * biological entity represented by the model element. This relation might
     * be used when a biological entity exhibits a certain enzymatic activity or
     * exerts a specific function.
     */
    BQB_HAS_PROPERTY("hasProperty"),
    /**
     * Represents the MIRIAM biological qualifier 'hasTaxon': the biological
     * entity represented by the model element is taxonomically restricted,
     * where the restriction is the subject of the referenced resource
     * (biological entity B). This relation may be used to ascribe a species
     * restriction to a biochemical reaction.
     */
    BQB_HAS_TAXON("hasTaxon"),
    /**
     * Represents the MIRIAM biological qualifier 'hasVersion': the subject of
     * the referenced resource (biological entity B) is a version or an instance
     * of the biological entity represented by the model element. This relation
     * may be used to represent an isoform or modified form of a biological
     * entity.
     */
    BQB_HAS_VERSION("hasVersion"),
    /**
     * Represents the MIRIAM biological qualifier 'is': the biological entity
     * represented by the model element has identity with the subject of the
     * referenced resource (biological entity B). This relation might be used to
     * link a reaction to its exact counterpart in a database, for instance.
     */
    BQB_IS("is"),
    /**
     * Represents the MIRIAM biological qualifier 'isDescribedBy': the
     * biological entity represented by the model element is described by the
     * subject of the referenced resource (biological entity B). This relation
     * should be used, for instance, to link a species or a parameter to the
     * literature that describes the concentration of that species or the value
     * of that parameter.
     */
    BQB_IS_DESCRIBED_BY("isDescribedBy"),
    /**
     * Represents the MIRIAM biological qualifier 'isEncodedBy': the biological
     * entity represented by the model element is encoded, directly or
     * transitively, by the subject of the referenced resource (biological
     * entity B). This relation may be used to express, for example, that a
     * protein is encoded by a specific DNA sequence.
     */
    BQB_IS_ENCODED_BY("isEncodedBy"),
    /**
     * Represents the MIRIAM biological qualifier 'isHomologTo': the biological
     * entity represented by the model element is homologous to the subject of
     * the referenced resource (biological entity B). This relation can be used
     * to represent biological entities that share a common ancestor.
     */
    BQB_IS_HOMOLOG_TO("isHomologTo"),
    /**
     * Represents the MIRIAM biological qualifier 'isPartOf': the biological
     * entity represented by the model element is a physical or logical part of
     * the subject of the referenced resource (biological entity B). This
     * relation may be used to link a model component to a description of the
     * complex in which it is a part.
     */
    BQB_IS_PART_OF("isPartOf"),
    /**
     * Represents the MIRIAM biological qualifier 'isPropertyOf': the biological
     * entity represented by the model element is a property of the referenced
     * resource (biological entity B).
     */
    BQB_IS_PROPERTY_OF("isPropertyOf"),
    /**
     * Represents the MIRIAM biological qualifier 'isVersionOf': the biological
     * entity represented by the model element is a version or an instance of
     * the subject of the referenced resource (biological entity B). This
     * relation may be used to represent, for example, the 'superclass' or
     * 'parent' form of a particular biological entity.
     */
    BQB_IS_VERSION_OF("isVersionOf"),
    /**
     * Represents the MIRIAM biological qualifier 'occursIn': the biological
     * entity represented by the model element is physically limited to a
     * location, which is the subject of the referenced resource (biological
     * entity B). This relation may be used to ascribe a compartmental location,
     * within which a reaction takes place.
     */
    BQB_OCCURS_IN("occursIn"),
    /**
     * Represents the MIRIAM biological qualifier 'isRelatedTo': the biological
     * entity represented by the model element is somehow associated, directly
     * or transitively, with the subject of the referenced resource (biological
     * entity B). This qualifier may be used as a generic way to express any
     * biological relationship.
     * 
     * <p>Only use this qualifier if you are not able to find a more precise
     * qualifier.</p>
     */
    BQB_IS_RELATED_TO("isRelatedTo"),
    /**
     * Represents an unknown MIRIAM biological qualifier, equivalent to {@link #BQB_IS_RELATED_TO}.
     */
    BQB_UNKNOWN("isRelatedTo"),
    /**
     * Represents the MIRIAM model qualifier 'hasInstance':
     * the modelling object represented by the model element has for instance
     * (is a class of) the subject of the referenced resource (modelling object B).
     * For instance, this qualifier might be used to link a generic model with its
     * specific forms.
     */
    BQM_HAS_INSTANCE("hasInstance"),
    /**
     * Represents the MIRIAM model qualifier 'is': the modeling object
     * represented by the model element is identical with the subject of the
     * referenced resource (modeling object B). For instance, this qualifier
     * might be used to link an encoded model to a database of models.
     */
    BQM_IS("is"),
    /**
     * Represents the MIRIAM model qualifier 'isDerivedFrom': the modeling
     * object represented by the model element is derived from the modeling
     * object represented by the referenced resource (modeling object B). This
     * relation may be used, for instance, to express a refinement or adaptation
     * in usage for a previously described modeling component.
     */
    BQM_IS_DERIVED_FROM("isDerivedFrom"),
    /**
     * Represents the MIRIAM model qualifier 'isDescribedBy': the modeling
     * object represented by the model element is described by the subject of
     * the referenced resource (modeling object B). This relation might be used
     * to link a model or a kinetic law to the literature that describes it.
     */
    BQM_IS_DESCRIBED_BY("isDescribedBy"),
    /**
     * Represents the MIRIAM model qualifier 'isInstanceOf':
     * the modelling object represented by the model element is an instance
     * of the subject of the referenced resource (modelling object B).
     * For instance, this qualifier might be used to link a specific model
     * with its generic form.
     */
    BQM_IS_INSTANCE_OF("isInstanceOf"),
    /**
     * Represents an unknown MIRIAM model qualifier.
     */
    BQM_UNKNOWN("unknownQualifier");

    /**
     * 
     * @param elementNameEquivalent
     * @return
     */
    public static Qualifier getBiologicalQualifierFor(String elementNameEquivalent) {
      return getQualifierFor(elementNameEquivalent, "BQB_", BQB_UNKNOWN);
    }

    /**
     * 
     * @param elementNameEquivalent
     * @return
     */
    public static Qualifier getModelQualifierFor(String elementNameEquivalent) {
      return getQualifierFor(elementNameEquivalent, "BQM_", BQM_UNKNOWN);
    }

    /**
     * @param elementNameEquivalent
     * @param prefix
     * @param unknownQualifier
     * @return
     */
    private static Qualifier getQualifierFor(String elementNameEquivalent,
      String prefix, Qualifier unknownQualifier) {
      for (Qualifier q : values()) {
        if (q.name().startsWith(prefix) && q.getElementNameEquivalent().equals(elementNameEquivalent)) {
          return q;
        }
      }
      return unknownQualifier;
    }


    /**
     * The name equivalent.
     */
    private String nameEquivalent;

    /**
     * 
     * @param nameEquivalent
     */
    private Qualifier(String nameEquivalent) {
      this.nameEquivalent = nameEquivalent;
    }

    /**
     * Returns a name corresponding to this Qualifier Object.
     * 
     * @return a name corresponding to this Qualifier Object.
     */
    public String getElementNameEquivalent() {
      return nameEquivalent;
    }

    /**
     * Returns {@code true} if this qualifier is a biological qualifier.
     * 
     * @return {@code true} if this qualifier is a biological qualifier,
     *         false otherwise.
     */
    public boolean isBiologicalQualifier() {
      return !isModelQualifier();
    }

    /**
     * Returns {@code true} if this qualifier is a model qualifier.
     * 
     * @return {@code true} if this qualifier is a model qualifier,
     *         {@code false} otherwise.
     */
    public boolean isModelQualifier() {
      return toString().startsWith("BQM_");
    }

  }

  /**
   * This enum list all the possible MIRIAM qualifiers type.
   * 
   */
  public static enum Type {
    /**
     * If the MIRIAM qualifier is a biological qualifier.
     */
    BIOLOGICAL_QUALIFIER("bqbiol", URI_BIOMODELS_NET_BIOLOGY_QUALIFIERS),
    /**
     * If the MIRIAM qualifier is a model qualifier.
     */
    MODEL_QUALIFIER("bqmodel", URI_BIOMODELS_NET_MODEL_QUALIFIERS),
    /**
     * If the MIRIAM qualifier is unknown.
     */
    UNKNOWN_QUALIFIER("unknown", null);

    /**
     * 
     */
    private String nameEquivalent;
    /**
     * 
     */
    private String namespaceURI;

    /**
     * 
     * @param nameEquivalent
     * @param namespaceURI
     */
    private Type(String nameEquivalent, String namespaceURI) {
      this.nameEquivalent = nameEquivalent;
    }

    /**
     * Returns a name corresponding to this Type of qualifier Object.
     * 
     * @return a name corresponding to this Type of qualifier Object.
     */
    public String getElementNameEquivalent() {
      return nameEquivalent;
    }

    /**
     * Returns the name space associated to this {@link Type}.
     * @return
     */
    public String getNamespaceURI() {
      return namespaceURI;
    }
  }


  // TODO: it would be probably safer to try to load the list a qualifier
  // from the web at http://www.ebi.ac.uk/miriam/main/qualifiers/xml/
  // We can have a copy of the file in the jar in case the web access fail but
  // the online one would be better as you can have new qualifiers defined at
  // any time.

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -3648054739091227113L;

  /**
   * The Uniform Resource Identifier pointing to <a href="http://biomodels.net/biology-qualifiers/">http://biomodels.net/biology-qualifiers/</a>
   */
  public static final String URI_BIOMODELS_NET_BIOLOGY_QUALIFIERS = "http://biomodels.net/biology-qualifiers/"; //$NON-NLS-1$

  /**
   * The Uniform Resource Identifier pointing to <a href="http://biomodels.net/model-qualifiers/">http://biomodels.net/model-qualifiers/</a>
   */
  public static final String URI_BIOMODELS_NET_MODEL_QUALIFIERS = "http://biomodels.net/model-qualifiers/"; //$NON-NLS-1$

  /**
   * Represents the MIRIAM qualifier node in the annotation node of a SBML
   * component.
   */
  private Qualifier qualifier;

  /**
   * 
   */
  private String unknownQualifierName = "unknownQualifier";

  /**
   * Contains all the MIRIAM URI associated with the qualifier of this {@link CVTerm}
   * instance.
   */
  private List<String> resourceURIs;

  /**
   * Contains all the {@link CVTerm} that qualifies this {@link CVTerm}. Since
   * SBML level 2 version 5 (and future SBML L3V2), the annotations can be recursive.
   */
  private List<CVTerm> listOfNestedCVTerms;

  /**
   * Represents the type of MIRIAM qualifier for this {@link CVTerm}. It
   * depends on the name space in the SBML file, it can be a model qualifier
   * or a biological qualifier.
   */
  private Type type;

  /**
   * Creates a {@link CVTerm} instance. By default, the type and qualifier of
   * this {@link CVTerm} are {@code null}. The list of resourceURIS is empty.
   */
  public CVTerm() {
    super();
    type = Type.UNKNOWN_QUALIFIER;
    qualifier = null;
    resourceURIs = new ArrayList<String>();
  }

  /**
   * Creates a {@link CVTerm} instance from a given {@link CVTerm}.
   * 
   * @param term the {@link CVTerm} to clone
   */
  public CVTerm(CVTerm term) {
    super(term);
    type = term.getQualifierType();
    switch (type) {
    case MODEL_QUALIFIER:
      qualifier = term.getModelQualifierType();
      break;
    case BIOLOGICAL_QUALIFIER:
      qualifier = term.getBiologicalQualifierType();
      break;
    default: // UNKNOWN
      qualifier = null;
      break;
    }
    resourceURIs = new ArrayList<String>();
    for (int i = 0; i < term.getResourceCount(); i++) {
      String resource = term.getResourceURI(i);
      if (resource != null) {
        resourceURIs.add(new String(term.getResourceURI(i)));
      }
    }
  }

  /**
   * Guesses the {@link Type} argument and sets the {@link Qualifier}
   * attribute appropriately. Then it adds all the given resources.
   * 
   * @param qualifier
   * @param resources
   */
  public CVTerm(Qualifier qualifier, String... resources) {
    this();
    if (qualifier.isBiologicalQualifier()) {
      setQualifierType(Type.BIOLOGICAL_QUALIFIER);
      setBiologicalQualifierType(qualifier);
    } else {
      setQualifierType(Type.MODEL_QUALIFIER);
      setModelQualifierType(qualifier);
    }
    addResources(resources);
  }

  /**
   * Creates a new {@link CVTerm} with the given {@link Type} and
   * {@link Qualifier} pointing to all given resources.
   * 
   * @param type
   * @param qualifier
   * @param resources
   * @throws IllegalArgumentException
   *             if the combination of the given type and qualifier is not
   *             possible or if the given resources are invalid.
   */
  public CVTerm(Type type, Qualifier qualifier, String... resources) {
    this();
    setQualifierType(type);
    if (isBiologicalQualifier()) {
      setBiologicalQualifierType(qualifier);
    } else if (isModelQualifier()) {
      setModelQualifierType(qualifier);
    } else {
      throw new IllegalArgumentException(MessageFormat.format(
        resourceBundle.getString("CVTerm.INVALID_TYPE_AND_QUALIFIER_COMBINATION_MSG"),
        type, qualifier));
    }
    for (String resource : resources) {
      addResource(resource);
    }
  }

  /**
   * 
   * @param miriam
   */
  public CVTerm(XMLNode miriam) {
    this();
    if (miriam.getName().equals("annotation")) {
      miriam = miriam.getChildAt(0);
    }
    if (miriam.getName().equals("RDF")) {
      miriam = miriam.getChildAt(0);
    }
    if (miriam.getName().equals("Description")) {
      miriam = miriam.getChildAt(0);
    }
    if (miriam.getURI().equals("http://biomodels.net/biology-qualifiers/")) {
      if (miriam.getPrefix().equals("bqbiol")) {
        setQualifier(Qualifier.getBiologicalQualifierFor(miriam.getName()));
      } else {
        setQualifier(Qualifier.getModelQualifierFor(miriam.getName()));
      }
      miriam = miriam.getChildAt(0);
    }
    if (miriam.getName().equals("Bag")) {
      for (int j = 0; j < miriam.getChildCount(); j++) {
        XMLNode child = miriam.getChildAt(j);
        if (child.getName().equals("li")) {
          XMLAttributes attributes = child.getAttributes();
          for (int i = 0; i < attributes.size(); i++) {
            addResource(attributes.getValue(i));
          }
        }
      }
    }
  }

  /**
   * Adds a resource to the {@link CVTerm}.
   * 
   * <p>Same method a {@link #addResourceURI(String)}
   * 
   * @param urn
   *            string representing the resource; e.g.,
   *            'http://identifiers.org/kegg.reaction/R00351'
   * @return {@code true} as specified in {@link Collection#add(Object)}
   */
  public boolean addResource(String urn) {
    try {
      return addResource(getResourceCount(), urn);
    } catch (Throwable t) {
      return false;
    }
  }

  /**
   * Adds list of resources to the {@link CVTerm}.
   * 
   * @param resources a list of strings representing the resources; e.g.,
   *            'http://identifiers.org/kegg.reaction/R00351'
   * @return {@code true} if all the resources have been added properly.
   */
  public boolean addResources(String... resources) {
    boolean success = true;
    for (String resource : resources) {
      success &= addResource(resource);
    }
    return success;
  }

  /**
   * Adds a resource to the {@link CVTerm}.
   * 
   * <p>Same method a {@link #addResource(String)}
   * 
   * @param uri
   *            string representing the resource; e.g.,
   *            'http://identifiers.org/kegg.reaction/R00351'
   * 
   * @return {@code true} if 'uri' has been added to the list of resourceURI of this
   *         CVTerm.
   */
  public boolean addResourceURI(String uri) {
    return addResource(uri);
  }

  /**
   * Adds a resource to the {@link CVTerm} at the specified index.
   * 
   * @param index - index at which the specified element is to be inserted
   * @param uri - element to be inserted
   * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index > size())
   * @see List#add(int, Object)
   */
  public boolean addResource(int index, String uri)
      throws IndexOutOfBoundsException {
    boolean contains = resourceURIs.contains(uri);
    if (!contains) {
      resourceURIs.add(index, uri);
      (new TreeNodeAdapter(uri, this)).fireNodeAddedEvent();
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#clone()
   */
  @Override
  public CVTerm clone() {
    return new CVTerm(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      CVTerm t = (CVTerm) object;
      equals &= t.getQualifierType() == getQualifierType();
      equals &= (t.getBiologicalQualifierType() == qualifier)
          || (t.getModelQualifierType() == qualifier);
    }
    return equals;
  }

  /**
   * Returns a list of resource URIs that contain the given {@link Pattern}(s). This is
   * useful to obtain, e.g., all KEGG resources this term points to.
   * 
   * @param patterns
   *        an arbitrary number of {@link Pattern}(s), e.g.,
   *        {@code http://identifiers.org/kegg.reaction/R.*}, {@code .*kegg.*}  or just {@code kegg} that
   *        are matched to all resources using an OR-operation, i.e.,
   *        if just one of the patterns matches a resource, this resource will
   *        appear in the returned list.
   * @return A list of all resources that contain the given pattern. This list
   *         can be empty, but never {@code null}. The order of the resources
   *         in that list will be identical to the order in this term.
   * @see Pattern
   * @see Matcher#find()
   */
  public List<String> filterResources(Pattern... patterns) {
    List<String> selectedResources = new ArrayList<String>();

    for (int i = 0; i < getResourceCount(); i++) {
      String resource = getResourceURI(i);

      for (Pattern pattern : patterns) {
        Matcher matcher = pattern.matcher(resource);

        if (matcher.find()) {
          selectedResources.add(resource);
          break;
        }
      }
    }

    return selectedResources;
  }

  /**
   * Returns a list of resource URIs that contain the given pattern(s). This is
   * useful to obtain, e.g., all KEGG resources this term points to.
   * 
   * @param patterns
   *        an arbitrary number of patterns, e.g.,
   *        {@code http://idenifiers.org/kegg.reaction/R.*}, {@code .*kegg.*}  or just {@code kegg} that
   *        are matched to all resources using an OR-operation, i.e.,
   *        if just one of the patterns matches a resource, this resource will
   *        appear in the returned list.
   * @return A list of all resources that contain the given pattern. This list
   *         can be empty, but never {@code null}. The order of the resources
   *         in that list will be identical to the order in this {@link CVTerm}.
   * @see Pattern
   * @see Matcher#find()
   * 
   */
  public List<String> filterResources(String... patterns) {
    Pattern pattern;
    Pattern[] patternList = new Pattern[patterns.length];

    for (int i = 0; i < patternList.length; i++) {
      pattern = Pattern.compile(patterns[i]);
      patternList[i] = pattern;
    }

    return filterResources(patternList);
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /**
   * Returns the {@link Qualifier} for this CVTerm, only if it if of type {@link Type#BIOLOGICAL_QUALIFIER}, null otherwise.
   * 
   * @return the {@link Qualifier} for this CVTerm, only if it if of type {@link Type#BIOLOGICAL_QUALIFIER}, null otherwise.
   * @libsbml.deprecated you could use {@link #getQualifier()} that always return the {@link Qualifier}.
   */
  public Qualifier getBiologicalQualifierType() {
    if (type == Type.BIOLOGICAL_QUALIFIER) {
      return qualifier;
    }
    return null;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int childIndex) {
    if (childIndex < getResourceCount()) {
      return new TreeNodeAdapter(getResourceURI(childIndex), this);
    } else if (childIndex == getResourceCount()) {
      return new TreeNodeAdapter(listOfNestedCVTerms, this);
    } else {
      throw new IndexOutOfBoundsException(Integer.toString(childIndex));
    }
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildCount()
   */
  @Override
  public int getChildCount() { // TODO - include NestedCVTerms in the tree so that we get them when doing recursive filters on SBase.
    int childCount = getResources().size();
    
    if (getNestedCVTermCount() > 0) { 
      childCount++;
    }
    
    return childCount;
  }

  /**
   * Returns the {@link Qualifier} for this CVTerm, only if it if of type {@link Type#MODEL_QUALIFIER}, null otherwise.
   * 
   * @return the {@link Qualifier} for this CVTerm, only if it if of type {@link Type#MODEL_QUALIFIER}, null otherwise.
   * @libsbml.deprecated you could use {@link #getQualifier()} that always return the {@link Qualifier}.
   */
  public Qualifier getModelQualifierType() {
    if (type == Type.MODEL_QUALIFIER) {
      return qualifier;
    }
    return null;
  }

  /**
   * Returns the number of resources for this {@link CVTerm}.
   * 
   * @return the number of resources for this {@link CVTerm}.
   * @libsbml.deprecated use {@link #getResourceCount()}
   */
  public int getNumResources() {
    return getResourceCount();
  }

  /**
   * Returns the {@link Qualifier} for this CVTerm.
   * 
   * <p>If you want to know if the Qualifier is a model qualifier or a biological modifier,
   * you can use {@link Qualifier#isModelQualifier()} and {@link Qualifier#isBiologicalQualifier()}.
   * <p>If you want to display the name of the Qualifier, you can use {@link Qualifier#getElementNameEquivalent()}.
   * 
   * @return the {@link Qualifier} for this CVTerm.
   */
  public Qualifier getQualifier() {
    return qualifier;
  }

  /**
   * Returns the qualifier {@link Type} for this CVTerm.
   * 
   * @return the qualifier {@link Type} for this CVTerm.
   * @libsbml.deprecated you could use {@link Qualifier#isModelQualifier()} or {@link Qualifier#isBiologicalQualifier()}
   * if you want to know the type of the qualifier of this CVTerm.
   */
  public Type getQualifierType() {
    return type;
  }

  /**
   * Returns the number of resources for this {@link CVTerm}.
   * 
   * @return the number of resources for this {@link CVTerm}.
   */
  public int getResourceCount() {
    return resourceURIs.size();
  }

  /**
   * Returns the resources for this CVTerm.
   * 
   * @return the list of urns that store the resources of this CVTerm.
   */
  public List<String> getResources() {
    return resourceURIs;
  }

  /**
   * 
   * @param i
   * @return
   */
  public String getResource(int i) {
    return resourceURIs.get(i);
  }

  /**
   * Returns the value of the nth resource for this CVTerm.
   * 
   * @param i  index of the resourceURI in the list of the resourceURI.
   * @return the value of the nth resource for this CVTerm.
   * @see #getResource(int)
   */
  public String getResourceURI(int i) {
    return getResource(i);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 821;
    int hashCode = super.hashCode();

    if (isSetQualifier()) {
      hashCode += prime * getQualifier().hashCode();
    }

    for (String uri : getResources()) {
      hashCode += prime * uri.hashCode();
    }

    return hashCode;
  }

  /**
   * Returns {@code true} if this qualifier is a biological qualifier.
   * 
   * @return {@code true} if this qualifier is a biological qualifier,
   *         {@code false} otherwise.
   */
  public boolean isBiologicalQualifier() {
    return isSetType() && type.equals(Type.BIOLOGICAL_QUALIFIER);
  }

  /**
   * Returns {@code true} if this qualifier is a model qualifier.
   * 
   * @return {@code true} if this qualifier is a model qualifier,
   *         {@code false} otherwise.
   */
  public boolean isModelQualifier() {
    return isSetType() && type.equals(Type.MODEL_QUALIFIER);
  }

  /**
   * Returns true if the {@link Qualifier} is set and is of type {@link Type#BIOLOGICAL_QUALIFIER}
   * 
   * @return true if the {@link Qualifier} is set and is of type {@link Type#BIOLOGICAL_QUALIFIER}, false otherwise.
   */
  public boolean isSetBiologicalQualifierType() {
    return getBiologicalQualifierType() != null;
  }

  /**
   * Returns true if the {@link Qualifier} is set and is of type {@link Type#MODEL_QUALIFIER}
   * 
   * @return true if the {@link Qualifier} is set and is of type {@link Type#MODEL_QUALIFIER}, false otherwise.
   */
  public boolean isSetModelQualifierType() {
    return getModelQualifierType() != null;
  }

  /**
   * Returns {@code true} if the {@link Qualifier} of this {@link CVTerm}
   * is set and is different from {@link Qualifier#BQM_UNKNOWN} and {@link Qualifier#BQB_UNKNOWN}.
   * 
   * @return {@code true} if the {@link Qualifier} of this {@link CVTerm}
   *         is set.
   */
  public boolean isSetQualifier() {
    return (qualifier != null) && (!qualifier.equals(Qualifier.BQM_UNKNOWN))
        && (!qualifier.equals(Qualifier.BQB_UNKNOWN));
  }

  /**
   * Checks whether or not the {@link Type} has been set for this
   * {@link CVTerm}.
   * 
   * @return {@code true} if the {@link Type} of this CVTerm is set.
   */
  public boolean isSetQualifierType() {
    return getQualifierType() != null;
  }

  /**
   * Returns {@code true} if the {@link Type} of this CVTerm is set and is different from {@link Type#UNKNOWN_QUALIFIER}.
   * 
   * @return {@code true} if the {@link Type} of this CVTerm is set and is different from {@link Type#UNKNOWN_QUALIFIER}.
   */
  public boolean isSetType() {
    return (type != null) && !type.equals(Type.UNKNOWN_QUALIFIER);
  }

  /**
   * Returns {@code true} if the {@link Qualifier} of this {@link CVTerm}
   * is set and is different from {@link Qualifier#BQM_UNKNOWN} and {@link Qualifier#BQB_UNKNOWN}.
   * 
   * @return {@code true} if the {@link Qualifier} of this {@link CVTerm}
   *         is set.
   * @deprecated use {@link #isSetQualifier()}
   */
  @Deprecated
  public boolean isSetTypeQualifier() {
    return isSetQualifier();
  }

  /**
   * 
   * @param elementName
   * @param attributeName
   * @param prefix
   * @param value
   * @return
   */
  public boolean readAttribute(String elementName, String attributeName,
    String prefix, String value) {

    // TODO - remove this method if not used

    if (elementName.equals("li")) {
      if (attributeName.equals("resource")) {
        addResourceURI(value);
        return true;
      }
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#removeFromParent()
   */
  @Override
  public boolean removeFromParent() {

    TreeNode parent = getParent();

    if (parent != null && parent instanceof Annotation) {
      Annotation annotation = (Annotation) parent;
      return annotation.removeCVTerm(this);
    }

    return false;
  }

  /**
   * Removes the ith resource URI from the {@link CVTerm}.
   * 
   * @param index
   * @return the removed URI.
   * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt;= size())
   */
  public String removeResource(int index) {
    return resourceURIs.remove(index);
  }

  /**
   * 
   * @param index
   * @return
   * @see #removeResource(int)
   */
  public String removeResourceURI(int index) {
    return removeResource(index);
  }

  /**
   * Removes the first occurrence of the given resource URI from the {@link CVTerm}.
   * 
   * @param resource
   */
  public void removeResource(String resource) {
    for (int i = resourceURIs.size() - 1; i >= 0; i--) {
      if (resourceURIs.get(i).equals(resource)) {
        String urn = resourceURIs.remove(i);
        (new TreeNodeAdapter(urn, this)).fireNodeRemovedEvent();
        break;
      }
    }
  }

  /**
   * Sets the biological qualifier type, using the {@link Qualifier#values()} array.
   * 
   * @param specificQualifierType
   * @libsbml.deprecated use {@link #setQualifier(Qualifier)}
   */
  public void setBiologicalQualifierType(int specificQualifierType) {
    setBiologicalQualifierType(Qualifier.values()[specificQualifierType]);
  }

  /**
   * Sets the biological qualifier type of this {@link CVTerm}.
   * 
   * @param qualifier
   * @throws IllegalArgumentException if the {@link Qualifier} is not a biological one or if
   * the {@link Type} was not set to {@link Type#BIOLOGICAL_QUALIFIER}.
   * @see  #setQualifier(Qualifier)
   */
  public void setBiologicalQualifierType(Qualifier qualifier) {
    if (qualifier != null) {
      if (qualifier.toString().startsWith("BQB")) {
        if (type == Type.BIOLOGICAL_QUALIFIER) {
          Qualifier oldValue = this.qualifier;
          this.qualifier = qualifier;
          firePropertyChange(TreeNodeChangeEvent.qualifier, oldValue, qualifier);
        } else {
          throw new IllegalArgumentException(MessageFormat.format(
            resourceBundle.getString("CVTerm.INVALID_TYPE_AND_QUALIFIER_COMBINATION_MSG"),
            type, qualifier));
        }
      } else {
        throw new IllegalArgumentException(MessageFormat.format(
          resourceBundle.getString("CVTerm.setBiologicalQualifierType"), qualifier.toString()));
      }
    }
  }

  /**
   * Sets the model qualifier type of this {@link CVTerm}, using the {@link Qualifier#values()} array.
   * 
   * @param specificQualifierType
   * @libsbml.deprecated use {@link #setQualifier(Qualifier)}
   */
  public void setModelQualifierType(int specificQualifierType) {
    final int NUM_BIOLOGICAL_QUALIFIER_TYPES = 11;
    setBiologicalQualifierType(Qualifier.values()[specificQualifierType
                                                  - NUM_BIOLOGICAL_QUALIFIER_TYPES]);
  }

  /**
   * Sets the model qualifier type of this {@link CVTerm}.
   * 
   * @param qualifier
   * @throws IllegalArgumentException if the {@link Qualifier} is not a model one or if
   * the {@link Type} was not set to {@link Type#MODEL_QUALIFIER}.
   * @see #setQualifier(Qualifier)
   */
  public void setModelQualifierType(Qualifier qualifier) {
    if (qualifier != null) {
      if (qualifier.toString().startsWith("BQM")) {
        if (type == Type.MODEL_QUALIFIER) {
          Qualifier oldValue = this.qualifier;
          this.qualifier = qualifier;
          firePropertyChange(TreeNodeChangeEvent.qualifier, oldValue, qualifier);
        } else {
          throw new IllegalArgumentException(
            resourceBundle.getString("CVTerm.setModelQualifierType1"));
        }
      } else {
        throw new IllegalArgumentException(MessageFormat.format(
          resourceBundle.getString("CVTerm.setModelQualifierType2"), qualifier.toString()));
      }
    }
  }

  // TODO: check that the 3 set functions taking an int are doing the good things and selecting the proper qualifier

  /**
   * Sets the {@link Qualifier} of this {@link CVTerm}, sets at the same time the {@link Type} to the proper value.
   * 
   * @param qualifier
   */
  public void setQualifier(Qualifier qualifier) {
    Qualifier oldQualifier = this.qualifier;
    Type oldType = type;

    this.qualifier = qualifier;

    if (this.qualifier != null) {
      if (qualifier.isBiologicalQualifier()) {
        type = Type.BIOLOGICAL_QUALIFIER;
      } else if (qualifier.isModelQualifier()) {
        type = Type.MODEL_QUALIFIER;
      } else {
        type = Type.UNKNOWN_QUALIFIER;
      }
    } else {
      type = null;
    }

    firePropertyChange(TreeNodeChangeEvent.type, oldType, type);
    firePropertyChange(TreeNodeChangeEvent.qualifier, oldQualifier, qualifier);
  }

  /**
   * Sets the type of this {@link CVTerm} to the {@link Type} represented by
   * 'qualifierType'.
   * 
   * @param qualifierType
   *        the Type to set as an integer.
   * @libsbml.deprecated use {@link #setQualifier(Qualifier)}.
   */
  public void setQualifierType(int qualifierType) {
    setQualifierType(Type.values()[qualifierType]);
  }

  /**
   * Sets the type of this {@link CVTerm} to 'type', the value of the {@link Qualifier} is reset to
   * {@link Qualifier#BQB_UNKNOWN} or {@link Qualifier#BQM_UNKNOWN}.
   * 
   * @param type
   * @see  #setQualifier(Qualifier)
   */
  public void setQualifierType(Type type) {
    if ((type == Type.MODEL_QUALIFIER)
        || (type == Type.BIOLOGICAL_QUALIFIER)
        || (type == Type.UNKNOWN_QUALIFIER)) {
      Qualifier oldQualifier = qualifier;
      Type oldType = this.type;
      this.type = type;

      qualifier = type == Type.MODEL_QUALIFIER ? Qualifier.BQM_UNKNOWN
        : Qualifier.BQB_UNKNOWN;
      firePropertyChange(TreeNodeChangeEvent.type, oldType, this.type);
      firePropertyChange(TreeNodeChangeEvent.qualifier, oldQualifier, qualifier);
    } else {
      throw new IllegalArgumentException(MessageFormat.format(
        resourceBundle.getString("CVTerm.setQualifierType"), type.toString()));
    }
  }

  /**
   * Returns a {@link String} containing the qualifier and all the resource
   *         URIs of this {@link CVTerm}.
   * 
   * @return a {@link String} containing the qualifier and all the resource
   *         URIs of this {@link CVTerm}.
   */
  public String printCVTerm() {
    String element, relationship;

    switch (getQualifierType()) {
    case MODEL_QUALIFIER:
      element = resourceBundle.getString("CVTerm.Type.MODEL_QUALIFIER");
      relationship = getModelQualifierType().getElementNameEquivalent();
      break;
    case BIOLOGICAL_QUALIFIER:
      element = resourceBundle.getString("CVTerm.Type.BIOLOGICAL_QUALIFIER");
      relationship = getBiologicalQualifierType().getElementNameEquivalent();
      break;
    default: // UNKNOWN_QUALIFIER
      element = resourceBundle.getString("CVTerm.Type.UNKNOWN_QUALIFIER");
      relationship = resourceBundle.getString("CVTerm.Qualifier.UNKNOWN");
      break;
    }

    return MessageFormat.format(
      resourceBundle.getString("CVTerm.humanReadable"), element, relationship, resourceURIs);
  }

  /**
   * 
   * @return
   */
  public String toXML() {
    StringBuffer buffer = new StringBuffer();
    toXML("  ", buffer);
    return buffer.toString();
  }

  /**
   * Writes all the MIRIAM annotations of the {@link CVTerm} in 'buffer'
   * 
   * @param indent
   * @param buffer
   */
  public void toXML(String indent, StringBuffer buffer) {
    StringTools.append(buffer, "<", type.getElementNameEquivalent(), ":", getQualifier().getElementNameEquivalent(), ">\n", indent, "<rdf:Bag>\n");
    if (resourceURIs != null) {
      for (int i = 0; i < getResourceCount(); i++) {
        StringTools.append(buffer, indent, indent, "<rdf:li rdf:resource=\"", getResourceURI(i), "\"/>\n");
      }
    }
    StringTools.append(buffer, indent, "</rdf:Bag>\n", "</", type.getElementNameEquivalent(), ":", getQualifier().getElementNameEquivalent(), ">\n");
  }

  /**
   * 
   * @return
   * @throws XMLStreamException
   */
  public XMLNode toXMLNode() throws XMLStreamException {
    return XMLNode.convertStringToXMLNode(toXML());
  }

  /**
   * Unsets the biological qualifier if it is set and of the type {@link Type#BIOLOGICAL_QUALIFIER}
   * 
   * @see #unsetQualifier()
   */
  public void unsetBiologicalQualifierType() {
    if (type == Type.BIOLOGICAL_QUALIFIER) {
      firePropertyChange(TreeNodeChangeEvent.qualifier, qualifier, null);
      qualifier = null;
    }
  }

  /**
   * Unsets the {@link Qualifier} if it is set and of the type {@link Type#MODEL_QUALIFIER}.
   * 
   * @see #unsetQualifier()
   */
  public void unsetModelQualifierType() {
    if (type == Type.MODEL_QUALIFIER) {
      firePropertyChange(TreeNodeChangeEvent.qualifier, qualifier, null);
      qualifier = null;
    }
  }

  /**
   * Unsets the qualifier if it is set.
   * 
   */
  public void unsetQualifier() {
    firePropertyChange(TreeNodeChangeEvent.qualifier, qualifier, null);
    qualifier = null;
  }

  /**
   * Unsets the qualifier type if it is set.
   * 
   */
  public void unsetQualifierType() {
    if (isSetQualifierType()) {
      firePropertyChange(TreeNodeChangeEvent.qualifier, type, null);
      type = null;
    }
  }


  /**
   * Returns {@code true} if {@link #listOfNestedCVTerms} contains at least
   * one element.
   *
   * @return {@code true} if {@link #listOfNestedCVTerms} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean isSetListOfNestedCVTerms() {
    if (listOfNestedCVTerms == null) {
      return false;
    }
    return true;
  }

  /**
   * Returns the {@link #listOfNestedCVTerms}.
   * Creates it if it does not already exist.
   *
   * @return the {@link #listOfNestedCVTerms}.
   */
  public List<CVTerm> getListOfNestedCVTerms() {
    if (listOfNestedCVTerms == null) {
      listOfNestedCVTerms = new ArrayList<CVTerm>();
    }
    return listOfNestedCVTerms;
  }

  /**
   * Sets the given {@code ListOf<CVTerm>}.
   * If {@link #listOfNestedCVTerms} was defined before and contains some
   * elements, they are all unset.
   * @param listOfCVTerms
   */
  public void setListOfNestedCVTerms(List<CVTerm> listOfCVTerms) {
    unsetListOfNestedCVTerms();
    listOfNestedCVTerms = listOfCVTerms;
  }

  /**
   * Returns {@code true} if {@link #listOfNestedCVTerms} contains at least
   * one element, otherwise {@code false}.
   *
   * @return {@code true} if {@link #listOfNestedCVTerms} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean unsetListOfNestedCVTerms() {
    if (isSetListOfNestedCVTerms()) {
      List<CVTerm> oldCVTerms = listOfNestedCVTerms;
      listOfNestedCVTerms = null;
      // oldCVTerms.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Adds a new {@link CVTerm} to the {@link #listOfNestedCVTerms}.
   * <p>The listOfNestedCVTerms is initialized if necessary.
   *
   * @param cvTerm the element to add to the list
   * @return {@code true} (as specified by {@link java.util.Collection#add})
   * @see java.util.Collection#add(Object)
   */
  public boolean addNestedCVTerm(CVTerm cvTerm) {
    return getListOfNestedCVTerms().add(cvTerm); // TODO - set the parent as this
  }

  /**
   * Removes an element from the {@link #listOfNestedCVTerms}.
   *
   * @param cvTerm the element to be removed from the list.
   * @return {@code true} if the list contained the specified element and it was
   *         removed.
   * @see java.util.List#remove(Object)
   */
  public boolean removeNestedCVTerm(CVTerm cvTerm) {
    if (isSetListOfNestedCVTerms()) {
      return getListOfNestedCVTerms().remove(cvTerm);
    }
    return false;
  }

  /**
   * Removes an element from the {@link #listOfNestedCVTerms} at the given index.
   *
   * @param i the index where to remove the {@link CVTerm}.
   * @return the specified element if it was successfully found and removed.
   * @throws IndexOutOfBoundsException if the listOf is not set or if the index is
   *         out of bound ({@code (i &lt; 0) || (i &gt; listOfNestedCVTerms)}).
   */
  public CVTerm removeNestedCVTerm(int i) {
    if (!isSetListOfNestedCVTerms()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfNestedCVTerms().remove(i);
  }

  /**
   * Creates a new {@link CVTerm} element and adds it to the
   * {@link #listOfNestedCVTerms} list.
   *
   * @return the newly created {@link CVTerm} element, which is the last
   *         element in the {@link #listOfNestedCVTerms}.
   */
  public CVTerm createNestedCVTerm() {
    CVTerm cvTerm = new CVTerm();
    addNestedCVTerm(cvTerm);
    return cvTerm;
  }

  /**
   * Gets an element from the {@link #listOfNestedCVTerms} at the given index.
   *
   * @param i the index of the {@link CVTerm} element to get.
   * @return an element from the listOfNestedCVTerms at the given index.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size).
   */
  public CVTerm getNestedCVTerm(int i) {
    if (!isSetListOfNestedCVTerms()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfNestedCVTerms().get(i);
  }

  /**
   * Returns the number of {@link CVTerm}s in this
   * {@link CVTerm}.
   * 
   * @return the number of {@link CVTerm}s in this
   *         {@link CVTerm}.
   */
  public int getNestedCVTermCount() {
    return isSetListOfNestedCVTerms() ? getListOfNestedCVTerms().size() : 0;
  }

  /**
   * Returns the number of {@link CVTerm}s in this
   * {@link CVTerm}.
   * 
   * @return the number of {@link CVTerm}s in this
   *         {@link CVTerm}.
   * @libsbml.deprecated same as {@link #getNestedCVTermCount()}
   */
  public int getNumNestedCVTerms() {
    return getNestedCVTermCount();
  }

  /**
   * Gets a name corresponding to the Qualifier of this {@link CVTerm}.
   * 
   * <p>Only useful if the qualifier is {@link Qualifier#BQM_UNKNOWN} or
   * {@link Qualifier#BQB_UNKNOWN}.</p>
   * 
   * @return the unknownQualifierName
   */
  public String getUnknownQualifierName() {
    return unknownQualifierName;
  }

  /**
   * Sets a name corresponding to the Qualifier of this {@link CVTerm}. WARNING: using
   * this method might generate wrong SBML.
   * 
   * <p>This method is mainly for internal use when a qualifier
   * is not recognized by JSBML when reading an SBML file. It can happen if the qualifier
   * was created after the release of JSBML or if there is a typo.</p>
   * 
   * @param unknownQualifierName the unknownQualifierName to set
   */
  public void setUnknownQualifierName(String unknownQualifierName) {
    this.unknownQualifierName = unknownQualifierName;
  }

}
