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
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.sbml.jsbml.util.StringTools;

/**
 * Contains all the miriam URIs for a miriam qualifier in the annotation element
 * of a SBML component.
 * 
 * @author Andreas Dr&auml;ger
 * @author marine
 * @author rodrigue
 * 
 */
public class CVTerm implements Cloneable, Serializable {

	// TODO : it would be probably safer to try to load the list a qualifier from the web at http://www.ebi.ac.uk/miriam/main/qualifiers/xml/
	// We can have a copy of the file in the jar in case the web access fail but the online one would be better as you can have new qualifiers defined at any time.
	
	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -3648054739091227113L;
	/**
	 * Message to indicate an illegal combination of a {@link Type} and a
	 * {@link Qualifier} attribute.
	 */
	private static final String INVALID_TYPE_AND_QUALIFIER_COMBINATION_MSG = "Invalid combination of type %s with qualifier %s.";

	/**
	 * This enum list all the possible MIRIAM qualifiers.
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
		 * Represents the MIRIAM biological qualifier 'hasVersion'.
		 */
		BQB_HAS_VERSION,
		/**
		 * Represents the MIRIAM biological qualifier 'hasProperty'.
		 */
		BQB_HAS_PROPERTY,
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
		 * Represents the MIRIAM biological qualifier 'isVersionOf'.
		 */
		BQB_IS_VERSION_OF,
		/**
		 * Represents the MIRIAM biological qualifier 'isPropertyOf'.
		 */
		BQB_IS_PROPERTY_OF,
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
		 * Represents the MIRIAM model qualifier 'isDescribedBy'.
		 */
		BQM_IS_DESCRIBED_BY,
		/**
		 * Represents the MIRIAM model qualifier 'isDerivedFrom'.
		 */
		BQM_IS_DERIVED_FROM,
		/**
		 * Represents an unknown MIRIAM model qualifier.
		 */
		BQM_UNKNOWN;

		/**
		 * 
		 * @return
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
				return "isDescribedBy";
			default:
				return "unknownQualifier";
			}
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
		 * 
		 * @return
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
	}

	/**
	 * Represents the MIRIAM qualifier node in the annotation node of a SBML
	 * component.
	 */
	private Qualifier qualifier;

	/**
	 * Contains all the MIRIAM URI associated with the qualifier of this CVTerm
	 * instance.
	 */
	private List<String> resourceURIs;

	/**
	 * Represents the type of MIRIAM qualifier for this CVTerm. It depends on
	 * the namespace in the SBML file, it can be a model qualifier or a
	 * biological qualifier.
	 */
	private Type type;

	/**
	 * Creates a CVTerm instance. By default, the type and qualifier of this
	 * CVTerm are null. The list of resourceURIS is empty.
	 */
	public CVTerm() {
		type = Type.UNKNOWN_QUALIFIER;
		qualifier = null;
		resourceURIs = new LinkedList<String>();
	}

	/**
	 * Creates a new {@link CVTerm} with the given {@link Type} and
	 * {@link Qualifier} pointing to all given resources.
	 * 
	 * @param type
	 * @param qualifier
	 * @param resoruces
	 * @throws IllegalArgumentException
	 *             if the combination of the given type and qualifier is not
	 *             possible or if the given resources are invalid.
	 */
	public CVTerm(Type type, Qualifier qualifier, String... resoruces) {
		this();
		setQualifierType(type);
		if (isBiologicalQualifier()) {
			setBiologicalQualifierType(qualifier);
		} else if (isModelQualifier()) {
			setModelQualifierType(qualifier);
		} else {
			throw new IllegalArgumentException(String.format(
					INVALID_TYPE_AND_QUALIFIER_COMBINATION_MSG, type,
					qualifier));
		}
		for (String resource : resoruces) {
			addResource(resource);
		}
	}

	/**
	 * Creates a CVTerm instance from a given CVTerm.
	 * 
	 * @param term
	 */
	public CVTerm(CVTerm term) {
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
		for (int i = 0; i < term.getNumResources(); i++) {
			String resource = term.getResourceURI(i);
			if (resource != null) {
				resourceURIs.add(new String(term.getResourceURI(i)));
			}
		}
	}

	/**
	 * Adds a resource to the {@link CVTerm}.
	 * 
	 * @param urn
	 *            string representing the resource; e.g.,
	 *            'urn:miriam:kegg.reaction:R00351'
	 * @return true as specified in {@link Collection.add}
	 */
	public boolean addResource(String urn) {
		return resourceURIs.add(urn);
	}

	/**
	 * 
	 * @param uri
	 * @return true if 'uri' has been added to the list of resourceURI of this
	 *         CVTerm.
	 */
	public boolean addResourceURI(String uri) {
		return resourceURIs.add(uri);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public CVTerm clone() {
		return new CVTerm(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof CVTerm) {
			CVTerm t = (CVTerm) o;
			boolean eq = t.getQualifierType() == getQualifierType();
			eq &= (t.getBiologicalQualifierType() == qualifier)
					|| (t.getModelQualifierType() == qualifier);
			eq &= t.getNumResources() == getNumResources();

			if (eq) {
				for (int i = 0; i < t.getNumResources(); i++) {
					String resource1 = getResourceURI(i);
					String resource2 = t.getResourceURI(i);
					if ((resource1 != null) && (resource2 != null)) {
						if (!resource1.equals(resource2)) {
							eq = false;
							break;
						}
					} else if (((resource1 == null) && (resource2 != null))
							|| ((resource2 == null) && (resource1 != null))) {
						return false;
					}
				}
			}
			return eq;
		}
		return false;
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
		LinkedList<String> l = new LinkedList<String>();
		for (String resource : resourceURIs) {
			if (resource.contains(pattern)) {
				l.add(resource);
			}
		}
		return l;
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
	 * 
	 * @return the number of resources for this CVTerm.
	 */
	public int getNumResources() {
		return resourceURIs.size();
	}

	/**
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
	 * @param n
	 *            : index of the resourceURI in the list of the resourceURI.
	 * @return the value of the nth resource for this CVTerm.
	 */
	public String getResourceURI(int i) {
		return resourceURIs.get(i);
	}

	/**
	 * 
	 * @return
	 */
	public boolean isBiologicalQualifier() {
		return type.equals(Type.BIOLOGICAL_QUALIFIER);
	}

	/**
	 * 
	 * @return
	 */
	public boolean isModelQualifier() {
		return type.equals(Type.MODEL_QUALIFIER);
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetType() {
		return (type != null) && !type.equals(Type.UNKNOWN_QUALIFIER);
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetTypeQualifier() {
		return qualifier != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#readAttribute(String elementName,
	 * String attributeName, String prefix, String value)
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
	 * Removes a resource from the CVTerm.
	 * 
	 * @param resource
	 */
	public void removeResource(String resource) {
		for (int i = resourceURIs.size(); i >= 0; i--) {
			if (resourceURIs.get(i).equals(resource)) {
				resourceURIs.remove(i);
			}
		}
	}

	/**
	 * 
	 * @param specificQualifierType
	 */
	public void setBiologicalQualifierType(int specificQualifierType) {
		setBiologicalQualifierType(Qualifier.values()[specificQualifierType]);
	}

	/**
	 * Sets the #BiolQualifierType_t of this CVTerm.
	 * 
	 * @param qualifier
	 */
	public void setBiologicalQualifierType(Qualifier qualifier) {
		if (qualifier != null) {
			if (qualifier.toString().startsWith("BQB")) {
				if (this.type == Type.BIOLOGICAL_QUALIFIER) {
					this.qualifier = qualifier;
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
	 * 
	 * @param specificQualifierType
	 */
	public void setModelQualifierType(int specificQualifierType) {
		final int NUM_BIOLOGICAL_QUALIFIER_TYPES = 11;
		setBiologicalQualifierType(Qualifier.values()[specificQualifierType
				- NUM_BIOLOGICAL_QUALIFIER_TYPES]);
	}

	/**
	 * Sets the ModelQualifierType_t value of this CVTerm.
	 * 
	 * @param qualifier
	 */
	public void setModelQualifierType(Qualifier qualifier) {
		if (qualifier != null) {
			if (qualifier.toString().startsWith("BQM")) {
				if (this.type == Type.MODEL_QUALIFIER)
					this.qualifier = qualifier;
				else
					throw new IllegalArgumentException(
							"Model qualifier types can only be applyed if the type is set to Model Qualifier.");
			} else {
				throw new IllegalArgumentException(String.format(
						"%s is not a valid Model !ualifier.", qualifier.toString()));
			}
		}
	}

	// TODO : check that this 3 functions are doing the good things and
	// selecting the proper qualifier
	/**
	 * 
	 * @param qualifierType
	 */
	public void setQualifierType(int qualifierType) {
		setQualifierType(Type.values()[qualifierType]);
	}

	/**
	 * Sets the type of this CVTerm to 'type'
	 * 
	 * @param type
	 */
	public void setQualifierType(Type type) {
		if ((type == Type.MODEL_QUALIFIER)
				|| (type == Type.BIOLOGICAL_QUALIFIER)
				|| (type == Type.UNKNOWN_QUALIFIER)) {
			this.type = type;
			this.qualifier = type == Type.MODEL_QUALIFIER ? Qualifier.BQM_UNKNOWN
					: Qualifier.BQB_UNKNOWN;
		} else {
			throw new IllegalArgumentException(String.format(
					"%s is not a valid qualifier.", type.toString()));
		}
	}

	/**
	 * @return a String containing the qualifier and all the resource URIs of
	 *         this CVTerm.
	 */
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
		buffer.append('.');
		return buffer.toString();
	}

	/**
	 * writes all the MIRIAM annotations of the CVTerm in 'buffer'
	 * 
	 * @param indent
	 * @param buffer
	 */
	public void toXML(String indent, StringBuffer buffer) {
		if (resourceURIs != null) {
			for (int i = 0; i < getNumResources(); i++) {
				String resourceURI = getResourceURI(i);
				StringTools.append(buffer, "<rdf:li rdf:resource=\"",
						resourceURI, "\"/>\n");
			}
		}
	}

}
