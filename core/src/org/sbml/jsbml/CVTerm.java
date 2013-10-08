/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.TreeNodeAdapter;
import org.sbml.jsbml.util.TreeNodeChangeEvent;

/**
 * Contains all the MIRIAM URIs for a MIRIAM qualifier in the annotation element
 * of a SBML component.
 * 
 * @author Andreas Dr&auml;ger
 * @author Marine Dumousseau
 * @author Nicolas Rodriguez
 * @since 0.8
 * @version $Rev$
 */
public class CVTerm extends AnnotationElement {

	/**
	 * The Uniform Resource Identifier pointing to <a href="http://biomodels.net/model-qualifiers/">http://biomodels.net/model-qualifiers/</a>
	 */
	public static final String URI_BIOMODELS_NET_MODEL_QUALIFIERS = "http://biomodels.net/model-qualifiers/";
	/**
	 * The Uniform Resource Identifier pointing to <a href="http://biomodels.net/biology-qualifiers/">http://biomodels.net/biology-qualifiers/</a>
	 */
	public static final String URI_BIOMODELS_NET_BIOLOGY_QUALIFIERS = "http://biomodels.net/biology-qualifiers/";

	
	// TODO : it would be probably safer to try to load the list a qualifier
	// from the web at http://www.ebi.ac.uk/miriam/main/qualifiers/xml/
	// We can have a copy of the file in the jar in case the web access fail but
	// the online one would be better as you can have new qualifiers defined at
	// any time.

	/**
	 * This {@code enum} list all the possible MIRIAM qualifiers.
	 * 
	 */
	public static enum Qualifier {
		/**
		 * Represents the MIRIAM biological qualifier 'encodes'.
		 */
		BQB_ENCODES,
		/**
		 * Represents the MIRIAM biological qualifier 'hasPart'.
		 */
		BQB_HAS_PART,
		/**
		 * Represents the MIRIAM biological qualifier 'hasProperty'.
		 */
		BQB_HAS_PROPERTY,
		/**
		 * Represents the MIRIAM biological qualifier 'hasTaxon'.
		 */
		BQB_HAS_TAXON,
		/**
		 * Represents the MIRIAM biological qualifier 'hasVersion'.
		 */
		BQB_HAS_VERSION,
		/**
		 * Represents the MIRIAM biological qualifier 'is'.
		 */
		BQB_IS,
		/**
		 * Represents the MIRIAM biological qualifier 'isDescribedBy'.
		 */
		BQB_IS_DESCRIBED_BY,
		/**
		 * Represents the MIRIAM biological qualifier 'isEncodedBy'.
		 */
		BQB_IS_ENCODED_BY,
		/**
		 * Represents the MIRIAM biological qualifier 'isHomologTo'.
		 */
		BQB_IS_HOMOLOG_TO,
		/**
		 * Represents the MIRIAM biological qualifier 'isPartOf'.
		 */
		BQB_IS_PART_OF,
		/**
		 * Represents the MIRIAM biological qualifier 'isPropertyOf'.
		 */
		BQB_IS_PROPERTY_OF,
		/**
		 * Represents the MIRIAM biological qualifier 'isVersionOf'.
		 */
		BQB_IS_VERSION_OF,
		/**
		 * Represents the MIRIAM biological qualifier 'occursIn'.
		 */
		BQB_OCCURS_IN,
		/**
		 * Represents an unknown MIRIAM biological qualifier.
		 */
		BQB_UNKNOWN,
		/**
		 * Represents the MIRIAM model qualifier 'is'.
		 */
		BQM_IS,
		/**
		 * Represents the MIRIAM model qualifier 'isDerivedFrom'.
		 */
		BQM_IS_DERIVED_FROM,
		/**
		 * Represents the MIRIAM model qualifier 'isDescribedBy'.
		 */
		BQM_IS_DESCRIBED_BY,
		/**
		 * Represents an unknown MIRIAM model qualifier.
		 */
		BQM_UNKNOWN;

		/**
		 * Returns a name corresponding to this Qualifier Object.
		 * 
		 * @return a name corresponding to this Qualifier Object.
		 */
		public String getElementNameEquivalent() {
			switch (this) {
			case BQB_ENCODES:
				return "encodes";
			case BQB_HAS_PART:
				return "hasPart";
			case BQB_HAS_VERSION:
				return "hasVersion";
			case BQB_HAS_PROPERTY:
				return "hasProperty";
			case BQB_HAS_TAXON:
				return "hasTaxon";
			case BQB_IS_PROPERTY_OF:
				return "isPropertyOf";
			case BQB_IS:
				return "is";
			case BQB_IS_DESCRIBED_BY:
				return "isDescribedBy";
			case BQB_IS_ENCODED_BY:
				return "isEncodedBy";
			case BQB_IS_HOMOLOG_TO:
				return "isHomologTo";
			case BQB_IS_PART_OF:
				return "isPartOf";
			case BQB_IS_VERSION_OF:
				return "isVersionOf";
			case BQB_OCCURS_IN:
				return "occursIn";
			case BQM_IS:
				return "is";
			case BQM_IS_DESCRIBED_BY:
				return "isDescribedBy";
			case BQM_IS_DERIVED_FROM:
				return "isDerivedFrom";
			default:
				return "unknownQualifier";
			}
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
		BIOLOGICAL_QUALIFIER,
		/**
		 * If the MIRIAM qualifier is a model qualifier.
		 */
		MODEL_QUALIFIER,
		/**
		 * If the MIRIAM qualifier is unknown.
		 */
		UNKNOWN_QUALIFIER;


		/**
		 * Returns a name corresponding to this Type of qualifier Object.
		 * 
		 * @return a name corresponding to this Type of qualifier Object.
		 */
		public String getElementNameEquivalent() {
			switch (this) {
			case BIOLOGICAL_QUALIFIER:
				return "bqbiol";
			case MODEL_QUALIFIER:
				return "bqmodel";
			case UNKNOWN_QUALIFIER:
				return "unknown";
			default:
				return null;
			}
		}

		/**
		 * Returns the name space associated to this {@link Type}.
		 * @return
		 */
		public String getNamespaceURI() {
			switch (this) {
			case BIOLOGICAL_QUALIFIER:
				return URI_BIOMODELS_NET_BIOLOGY_QUALIFIERS;
			case MODEL_QUALIFIER:
				return URI_BIOMODELS_NET_MODEL_QUALIFIERS;
			default:
				return null;
			}
		}
	}

	/**
	 * Message to indicate an illegal combination of a {@link Type} and a
	 * {@link Qualifier} attribute.
	 */
	private static final String INVALID_TYPE_AND_QUALIFIER_COMBINATION_MSG = "Invalid combination of type %s with qualifier %s.";

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -3648054739091227113L;

	/**
	 * Represents the MIRIAM qualifier node in the annotation node of a SBML
	 * component.
	 */
	private Qualifier qualifier;

	/**
	 * Contains all the MIRIAM URI associated with the qualifier of this {@link CVTerm}
	 * instance.
	 */
	private List<String> resourceURIs;

	/**
	 * Represents the type of MIRIAM qualifier for this {@link CVTerm}. It
	 * depends on the name space in the SBML file, it can be a model qualifier
	 * or a biological qualifier.
	 */
	private Type type;

	/**
	 * Creates a {@link CVTerm} instance. By default, the type and qualifier of
	 * this {@link CVTerm} are null. The list of resourceURIS is empty.
	 */
	public CVTerm() {
		super();
		type = Type.UNKNOWN_QUALIFIER;
		qualifier = null;
		resourceURIs = new LinkedList<String>();
	}

	/**
	 * Creates a {@link CVTerm} instance from a given {@link CVTerm}.
	 * 
	 * @param term the {@link CVTerm} to clone
	 */
	public CVTerm(CVTerm term) {
		super(term);
		this.type = term.getQualifierType();
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
		resourceURIs = new LinkedList<String>();
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
			throw new IllegalArgumentException(String
					.format(INVALID_TYPE_AND_QUALIFIER_COMBINATION_MSG, type,
							qualifier));
		}
		for (String resource : resources) {
			addResource(resource);
		}
	}

	/**
	 * Adds a resource to the {@link CVTerm}.
	 * 
	 * <p>Same method a {@link #addResourceURI(String)}
	 * 
	 * @param urn
	 *            string representing the resource; e.g.,
	 *            'urn:miriam:kegg.reaction:R00351'
	 * @return {@code true} as specified in {@link Collection#add(Object)}
	 */
	public boolean addResource(String urn) {
		boolean contains = resourceURIs.contains(urn);
		boolean success = resourceURIs.add(urn);
		if (success && !contains) {
		    (new TreeNodeAdapter(urn, this)).fireNodeAddedEvent();
		}
		return success;
	}

	/**
	 * Adds list of resources to the {@link CVTerm}.
	 * 
	 * @param resources a list of strings representing the resources; e.g.,
	 *            'urn:miriam:kegg.reaction:R00351'
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
	 *            'urn:miriam:kegg.reaction:R00351'
	 *            
	 * @return {@code true} if 'uri' has been added to the list of resourceURI of this
	 *         CVTerm.
	 */
	public boolean addResourceURI(String uri) {
		return addResource(uri);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
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
	 * Returns a list of resources that contain the given pattern. This is
	 * useful to obtain, e.g., all KEGG resources this term points to.
	 * 
	 * @param pattern
	 *            e.g., "urn:miriam:kegg.reaction:R" or just "kegg".
	 * @return A list of all resources that contain the given pattern. This list
	 *         can be empty.
	 */
	public List<String> filterResources(String pattern) {
		LinkedList<String> selectedResources = new LinkedList<String>();
		for (String resource : resourceURIs) {
			if (resource.contains(pattern)) {
				selectedResources.add(resource);
			}
		}
		return selectedResources;
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#getAllowsChildren()
	 */
	public boolean getAllowsChildren() {
		return true;
	}

	/**
	 * Returns the Biological QualifierType code for this CVTerm.
	 * 
	 * @return the Biological QualifierType code for this CVTerm.
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
	public TreeNode getChildAt(int childIndex) {
		return new TreeNodeAdapter(getResourceURI(childIndex), this);
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#getChildCount()
	 */
	public int getChildCount() {
		return getResources().size();
	}

	/**
	 * Returns the Model QualifierType code for this CVTerm.
	 * 
	 * @return the Model QualifierType code for this CVTerm.
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
	 * @use {@link #getResourceCount()}
	 */
	@Deprecated
	public int getNumResources() {
		return getResourceCount();
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
	 * Returns the Qualifier Type code for this CVTerm.
	 * 
	 * @return the Qualifier Type code for this CVTerm.
	 */
	public Type getQualifierType() {
		return type;
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
	 * Returns the value of the nth resource for this CVTerm.
	 * 
	 * @param i
	 *            : index of the resourceURI in the list of the resourceURI.
	 * @return the value of the nth resource for this CVTerm.
	 */
	public String getResourceURI(int i) {
		return resourceURIs.get(i);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractTreeNode#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 821;
		int hashCode = super.hashCode();
		if (isSetQualifierType()) {
			hashCode += prime * getQualifierType().hashCode();
		}
		if (isSetBiologicalQualifierType()) {
			hashCode += prime * getBiologicalQualifierType().hashCode();
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
		return type.equals(Type.BIOLOGICAL_QUALIFIER);
	}

	/**
	 * Returns {@code true} if this qualifier is a model qualifier.
	 * 
	 * @return {@code true} if this qualifier is a model qualifier,
	 *         {@code false} otherwise.
	 */
	public boolean isModelQualifier() {
		return type.equals(Type.MODEL_QUALIFIER);
	}

	/**
	 * @return
	 */
	public boolean isSetBiologicalQualifierType() {
		return getBiologicalQualifierType() != null;
	}

	/**
	 * @return
	 */
	public boolean isSetModelQualifierType() {
		return getModelQualifierType() != null;
	}

	/**
	 * Checks whether or not the {@link Qualifier} has been set for this
	 * {@link CVTerm}.
	 * 
	 * @return
	 */
	public boolean isSetQualifierType() {
		return getQualifierType() != null;
	}

	/**
	 * Returns {@code true} if the Type of this CVTerm is set.
	 * 
	 * @return {@code true} if the Type of this CVTerm is set.
	 */
	public boolean isSetType() {
		return (type != null) && !type.equals(Type.UNKNOWN_QUALIFIER);
	}

	/**
	 * Returns {@code true} if the {@link Qualifier} of this {@link CVTerm}
	 * is set.
	 * 
	 * @return {@code true} if the {@link Qualifier} of this {@link CVTerm}
	 *         is set.
	 */
	public boolean isSetTypeQualifier() {
		return qualifier != null;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.element.SBase#readAttribute(String elementName, String attributeName, String prefix, String value)
	 */
	public boolean readAttribute(String elementName, String attributeName,
			String prefix, String value) {

		if (elementName.equals("li")) {
			if (attributeName.equals("resource")) {
				addResourceURI(value);
				return true;
			}
		}
		return false;
	}

	/**
	 * Removes a resource from the {@link CVTerm}.
	 * 
	 * @param resource
	 */
	public void removeResource(String resource) {
		for (int i = resourceURIs.size(); i >= 0; i--) {
			if (resourceURIs.get(i).equals(resource)) {
				String urn = resourceURIs.remove(i);
				(new TreeNodeAdapter(urn, this)).fireNodeRemovedEvent();
			}
		}
	}

	/**
	 * Removes the ith resource from the {@link CVTerm}.
	 * 
	 * @param resource
	 */
	public void removeResource(int index) {
	  resourceURIs.remove(index);
	}

	/**
	 * Sets the biological qualifier type.
	 * 
	 * @param specificQualifierType
	 */
	public void setBiologicalQualifierType(int specificQualifierType) {
		setBiologicalQualifierType(Qualifier.values()[specificQualifierType]);
	}

	/**
	 * Sets the biological qualifier type of this {@link CVTerm}.
	 * 
	 * @param qualifier
	 */
	public void setBiologicalQualifierType(Qualifier qualifier) {
		if (qualifier != null) {
			if (qualifier.toString().startsWith("BQB")) {
				if (this.type == Type.BIOLOGICAL_QUALIFIER) {
					Qualifier oldValue = this.qualifier;
					this.qualifier = qualifier;
					this.firePropertyChange(TreeNodeChangeEvent.qualifier, oldValue, qualifier);
				} else {
					throw new IllegalArgumentException(String.format(
							INVALID_TYPE_AND_QUALIFIER_COMBINATION_MSG, type,
							qualifier));
				}
			} else {
				throw new IllegalArgumentException(String.format(
						"%s is not a valid Biological Qualifier.", qualifier
								.toString()));
			}
		}
	}

	/**
	 * Sets the model qualifier type of this {@link CVTerm}.
	 * 
	 * @param specificQualifierType
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
	 */
	public void setModelQualifierType(Qualifier qualifier) {
		if (qualifier != null) {
			if (qualifier.toString().startsWith("BQM")) {
				if (this.type == Type.MODEL_QUALIFIER) {
					Qualifier oldValue = this.qualifier;
					this.qualifier = qualifier;
					this.firePropertyChange(TreeNodeChangeEvent.qualifier, oldValue, qualifier);
				} else {
					throw new IllegalArgumentException(
							"Model qualifier types can only be applyed if the type is set to Model Qualifier.");
				}
			} else {
				throw new IllegalArgumentException(String.format(
						"%s is not a valid Model !ualifier.", qualifier
								.toString()));
			}
		}
	}

	// TODO : check that this 3 functions are doing the good things and
	// selecting the proper qualifier
	/**
	 * Sets the type of this {@link CVTerm} to the {@link Type} represented by
	 * 'qualifierType'.
	 * 
	 * @param qualifierType
	 *        the Type to set as an integer.
	 */
	public void setQualifierType(int qualifierType) {
		setQualifierType(Type.values()[qualifierType]);
	}

	/**
	 * Sets the type of this {@link CVTerm} to 'type'
	 * 
	 * @param type
	 */
	public void setQualifierType(Type type) {
		if ((type == Type.MODEL_QUALIFIER)
				|| (type == Type.BIOLOGICAL_QUALIFIER)
				|| (type == Type.UNKNOWN_QUALIFIER)) {
			Qualifier oldQualifier = this.qualifier;
			Type oldType = this.type;
			this.type = type;
			
			this.qualifier = type == Type.MODEL_QUALIFIER ? Qualifier.BQM_UNKNOWN
					: Qualifier.BQB_UNKNOWN;
			this.firePropertyChange(TreeNodeChangeEvent.type, oldType, this.type);
			this.firePropertyChange(TreeNodeChangeEvent.qualifier, oldQualifier, this.qualifier);
		} else {
			throw new IllegalArgumentException(String.format(
					"%s is not a valid qualifier.", type.toString()));
		}
	}

	/**
	 * Returns a {@link String} containing the qualifier and all the resource
	 *         URIs of this {@link CVTerm}.
	 * 
	 * @return a {@link String} containing the qualifier and all the resource
	 *         URIs of this {@link CVTerm}.
	 */
	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		switch (getQualifierType()) {
		case MODEL_QUALIFIER:
			buffer.append("model ");
			switch (getModelQualifierType()) {
			case BQM_IS:
				buffer.append("is");
				break;
			case BQM_IS_DESCRIBED_BY:
				buffer.append("is described by");
				break;
			case BQM_IS_DERIVED_FROM:
				buffer.append("is derived from");
				break;
			default: // unknown
				buffer.append("has something to do with");
				break;
			}
			break;
		case BIOLOGICAL_QUALIFIER:
			buffer.append("biological entity ");
			switch (getBiologicalQualifierType()) {
			case BQB_ENCODES:
				buffer.append("encodes");
				break;
			case BQB_HAS_PART:
				buffer.append("has ");
				buffer.append(resourceURIs.size() == 1 ? "a part" : "parts");
				break;
			case BQB_HAS_VERSION:
				buffer.append("has the version");
				break;
			case BQB_HAS_PROPERTY:
				buffer.append("has the property");
				break;
			case BQB_IS:
				buffer.append("is");
				break;
			case BQB_IS_DESCRIBED_BY:
				buffer.append("is described by");
				break;
			case BQB_IS_ENCODED_BY:
				buffer.append("is encoded by");
				break;
			case BQB_IS_HOMOLOG_TO:
				buffer.append("is homolog to");
				break;
			case BQB_IS_PART_OF:
				buffer.append("is a part of");
				break;
			case BQB_IS_VERSION_OF:
				buffer.append("is a version of");
				break;
			case BQB_OCCURS_IN:
				buffer.append("occurs in");
				break;
			default: // unknown
				buffer.append("has something to do with");
				break;
			}
			break;
		default: // UNKNOWN_QUALIFIER
			buffer.append("element has something to do with");
			break;
		}
		int i = 0;
		if (resourceURIs.size() > 0) {
			buffer.append(' ');
		}
		String uri;
		for (i = 0; i < resourceURIs.size(); i++) {
			uri = resourceURIs.get(i);
			if (i > 0) {
				buffer.append(", ");
			}
			buffer.append(uri);
		}
		return buffer.toString();
	}

	
	/**
	 * Unsets the biological qualifier if it is set.
	 * 
	 */
	public void unsetBiologicalQualifierType() {
		if (type == Type.BIOLOGICAL_QUALIFIER) {
			firePropertyChange(TreeNodeChangeEvent.qualifier, qualifier, null);
			qualifier = null;
		}
	}

	/**
	 * Unsets the model qualifier if it is set.
	 * 
	 */
	public void unsetModelQualifierType() {
		if (type == Type.MODEL_QUALIFIER) {
			firePropertyChange(TreeNodeChangeEvent.qualifier, qualifier, null);
			qualifier = null;
		}
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
	 * Writes all the MIRIAM annotations of the {@link CVTerm} in 'buffer'
	 * 
	 * @param indent
	 * @param buffer
	 */
	public void toXML(String indent, StringBuffer buffer) {
		if (resourceURIs != null) {
			for (int i = 0; i < getResourceCount(); i++) {
				String resourceURI = getResourceURI(i);
				StringTools.append(buffer, "<rdf:li rdf:resource=\"",
						resourceURI, "\"/>\n");
			}
		}
	}
	
}
