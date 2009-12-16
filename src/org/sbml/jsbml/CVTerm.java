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

import java.util.LinkedList;
import java.util.List;

import org.sbml.jsbml.util.StringTools;

/**
 * Contains all the miriam URIs for a miriam qualifier in the annotation element
 * of a SBML component.
 * 
 * @author Andreas Dr&auml;ger
 * @author marine
 * 
 */
public class CVTerm {

	/**
	 * This enum list all the possible Miriam qualifiers.
	 * 
	 * @author Andreas Dr&auml;ger <a
	 *         href="mailto:andreas.draeger@uni-tuebingen.de"
	 *         >andreas.draeger@uni-tuebingen.de</a>
	 * @author marine
	 */
	public static enum Qualifier {
		/**
		 * If the Miriam qualifier is a biological qualifier.
		 */
		BIOLOGICAL_QUALIFIER,
		/**
		 * Represents the Miriam biological qualifier 'encodes'.
		 */
		BQB_ENCODES,
		/**
		 * Represents the Miriam biological qualifier 'hasPart'.
		 */
		BQB_HAS_PART,
		/**
		 * Represents the Miriam biological qualifier 'hasVersion'.
		 */
		BQB_HAS_VERSION,
		/**
		 * Represents the Miriam biological qualifier 'is'.
		 */
		BQB_IS,
		/**
		 * Represents the Miriam biological qualifier 'isDescribedBy'.
		 */
		BQB_IS_DESCRIBED_BY,
		/**
		 * Represents the Miriam biological qualifier 'isEncodedBy'.
		 */
		BQB_IS_ENCODED_BY,
		/**
		 * Represents the Miriam biological qualifier 'isHomologTo'.
		 */
		BQB_IS_HOMOLOG_TO,
		/**
		 * Represents the Miriam biological qualifier 'isPartOf'.
		 */
		BQB_IS_PART_OF,
		/**
		 * Represents the Miriam biological qualifier 'isVersionOf'.
		 */
		BQB_IS_VERSION_OF,
		/**
		 * Represents the Miriam biological qualifier 'occursIn'.
		 */
		BQB_OCCURS_IN,
		/**
		 * Represents an unknown Miriam biological qualifier.
		 */
		BQB_UNKNOWN,
		/**
		 * Represents the Miriam model qualifier 'is'.
		 */
		BQM_IS,
		/**
		 * Represents the Miriam model qualifier 'isDescribedBy'.
		 */
		BQM_IS_DESCRIBED_BY,
		/**
		 * Represents an unknown Miriam model qualifier.
		 */
		BQM_UNKNOWN,
		/**
		 * If the Miriam qualifier is a model qualifier.
		 */
		MODEL_QUALIFIER,
		/**
		 * If the Miriam qualifier is unknown.
		 */
		UNKNOWN_QUALIFIER
	}

	/**
	 * Contains all the Miriam URI associated with the typeQualifier of this
	 * CVTerm instance.
	 */
	private List<String> resourceURIs;
	/**
	 * Represents the type of Miriam qualifier for this CVTerm. It depends on
	 * the namespace in the SBML file, it can be a model qualifier or a
	 * biological qualifier.
	 */
	private Qualifier type;

	/**
	 * Represents the Miriam qualifier node in the annotation node of a SBML
	 * component.
	 */
	private Qualifier typeQualifier;

	/**
	 * Creates a CVTerm instance. By default, the type and typeQualifier of this
	 * CVTerm are null. The list of resourceURIS is empty.
	 */
	public CVTerm() {
		type = null;
		typeQualifier = null;
		resourceURIs = new LinkedList<String>();
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
			typeQualifier = term.getModelQualifierType();
			break;
		case BIOLOGICAL_QUALIFIER:
			typeQualifier = term.getBiologicalQualifierType();
			break;
		default: // UNKNOWN
			typeQualifier = null;
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
	 * 
	 * @see java.lang.Object#clone()
	 */
	// @Override
	public CVTerm clone() {
		return new CVTerm(this);
	}

	/**
	 * 
	 * @param o
	 * @return true if the CVTerm 'o' is equal to this CVterm.
	 */
	public boolean equals(CVTerm o) {
		boolean equal = getQualifierType() == o.getQualifierType();

		if (equal) {
			equal &= getBiologicalQualifierType() == o
					.getBiologicalQualifierType();
			if (equal) {
				equal &= getModelQualifierType() == o.getModelQualifierType();
				if (equal) {
					equal &= getNumResources() == o.getNumResources();
					if (equal) {
						for (int i = 0; i < getNumResources(); i++) {
							String resource1 = getResourceURI(i);
							String resource2 = o.getResourceURI(i);

							if (resource1 != null && resource2 != null) {
								equal &= resource1.equals(resource2);
								if (!equal) {
									return false;
								}
							} else if ((resource1 == null && resource2 != null)
									|| (resource2 == null && resource1 != null)) {
								return false;
							}
						}
					}
				}
			}
		}
		return equal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	// @Override
	public boolean equals(Object o) {
		if (o instanceof CVTerm) {
			CVTerm t = (CVTerm) o;
			boolean eq = true;
			eq &= t.getQualifierType() == getQualifierType();
			eq &= t.getBiologicalQualifierType() == typeQualifier
					|| t.getModelQualifierType() == typeQualifier;
			eq &= t.getNumResources() == getNumResources();

			if (eq) {
				for (int i = 0; i < t.getNumResources(); i++) {
					String resource1 = getResourceURI(i);
					String resource2 = t.getResourceURI(i);
					if (!resource1.equals(resource2)) {
						eq = false;
						break;
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
		for (String resource : resourceURIs)
			if (resource.contains(pattern))
				l.add(resource);
		return l;
	}

	/**
	 * 
	 * @return the Biological QualifierType code for this CVTerm.
	 */
	public Qualifier getBiologicalQualifierType() {
		if (type == Qualifier.BIOLOGICAL_QUALIFIER) {
			return typeQualifier;
		}
		return null;
	}

	/**
	 * 
	 * @return the Model QualifierType code for this CVTerm.
	 */
	public Qualifier getModelQualifierType() {
		if (type == Qualifier.MODEL_QUALIFIER) {
			return typeQualifier;
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
	public Qualifier getQualifierType() {
		return type;
	}

	/**
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
		return type == Qualifier.BIOLOGICAL_QUALIFIER;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isModelQualifier() {
		return type == Qualifier.BIOLOGICAL_QUALIFIER;
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
	 * Sets the #BiolQualifierType_t of this CVTerm.
	 * 
	 * @param type
	 */
	public void setBiologicalQualifierType(Qualifier type) {
		if (type != null) {
			if (type.toString().startsWith("BQB")) {
				if (this.type == Qualifier.BIOLOGICAL_QUALIFIER) {
					this.typeQualifier = type;
				} else {
					throw new IllegalArgumentException(
							"Biological qualifiers can only be applyed if the type is set to Biological Qualifier.");
				}
			} else {
				throw new IllegalArgumentException(type.toString()
						+ " is not a valid Biological Qualifier.");
			}
		}
	}

	/**
	 * Sets the ModelQualifierType_t value of this CVTerm.
	 * 
	 * @param type
	 */
	public void setModelQualifierType(Qualifier type) {
		if (type != null) {
			if (type.toString().startsWith("BQM")) {
				typeQualifier = type;
			} else {
				throw new IllegalArgumentException(type.toString()
						+ " is not a valid model qualifier.");
			}
		}
	}

	/**
	 * sets the type of this CVTerm to 'type'
	 * 
	 * @param type
	 */
	public void setQualifierType(Qualifier type) {
		if (type == Qualifier.MODEL_QUALIFIER
				|| type == Qualifier.BIOLOGICAL_QUALIFIER
				|| type == Qualifier.UNKNOWN_QUALIFIER) {
			this.type = type;
			this.typeQualifier = type == Qualifier.MODEL_QUALIFIER ? Qualifier.BQM_UNKNOWN
					: Qualifier.BQB_UNKNOWN;
		} else {
			throw new IllegalArgumentException(type.toString()
					+ " is not a valid qualifier.");
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
		for (String uri : resourceURIs) {
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
						resourceURI, "\"/>");
				buffer.append(StringTools.newLine());
			}
		}
	}

}
