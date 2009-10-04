/*
 *  SBMLsqueezer creates rate equations for reactions in SBML files
 *  (http://sbml.org).
 *  Copyright (C) 2009 ZBIT, University of Tübingen, Andreas Dräger
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.sbml.jsbml;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * @date 2009-09-07
 */
public class CVTerm {

	private Qualifier type;
	private Qualifier typeQualifier;
	private List<String> resourceURIs;

	/**
	 * @author Andreas Dr&auml;ger <a
	 *         href="mailto:andreas.draeger@uni-tuebingen.de"
	 *         >andreas.draeger@uni-tuebingen.de</a>
	 * 
	 */
	public static enum Qualifier {
		BIOLOGICAL_QUALIFIER,
		/**
		 * 
		 */
		MODEL_QUALIFIER,
		/**
		 * 
		 */
		UNKNOWN_QUALIFIER,
		/**
		 * 
		 */
		BQB_ENCODES,
		/**
		 * 
		 */
		BQB_HAS_PART,
		/**
		 * 
		 */
		BQB_HAS_VERSION,
		/**
		 * 
		 */
		BQB_IS,
		/**
		 * 
		 */
		BQB_IS_DESCRIBED_BY,
		/**
		 * 
		 */
		BQB_IS_ENCODED_BY,
		/**
		 * 
		 */
		BQB_IS_HOMOLOG_TO,
		/**
		 * 
		 */
		BQB_IS_PART_OF,
		/**
		 * 
		 */
		BQB_IS_VERSION_OF,
		/**
		 * 
		 */
		BQB_OCCURS_IN,
		/**
		 * 
		 */
		BQB_UNKNOWN,
		/**
		 * 
		 */
		BQM_IS,
		/**
		 * 
		 */
		BQM_IS_DESCRIBED_BY,
		/**
		 * 
		 */
		BQM_UNKNOWN
	}

	/**
	 * 
	 */
	public CVTerm() {
		type = Qualifier.UNKNOWN_QUALIFIER;
		typeQualifier = Qualifier.UNKNOWN_QUALIFIER;
		resourceURIs = new LinkedList<String>();
	}

	/**
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
		for (int i = 0; i < term.getNumResources(); i++)
			resourceURIs.add(new String(term.getResourceURI(i)));
	}

	/**
	 * Returns the Biological QualifierType code for this CVTerm.
	 * 
	 * @return
	 */
	public Qualifier getBiologicalQualifierType() {
		if (type == Qualifier.BIOLOGICAL_QUALIFIER)
			return typeQualifier;
		return null;
	}

	/**
	 * Returns the Model QualifierType code for this CVTerm.
	 * 
	 * @return
	 */
	public Qualifier getModelQualifierType() {
		if (type == Qualifier.MODEL_QUALIFIER)
			return typeQualifier;
		return null;
	}

	/**
	 * Returns the number of resources for this CVTerm.
	 * 
	 * @return
	 */
	public int getNumResources() {
		return resourceURIs.size();
	}

	/**
	 * Returns the Qualifier Type code for this CVTerm.
	 * 
	 * @return
	 */
	public Qualifier getQualifierType() {
		return type;
	}

	/**
	 * Returns the value of the nth resource for this CVTerm.
	 * 
	 * @param n
	 * @return
	 */
	public String getResourceURI(int i) {
		return resourceURIs.get(i);
	}

	/**
	 * 
	 * @param uri
	 * @return
	 */
	public boolean addResourceURI(String uri) {
		return resourceURIs.add(uri);
	}

	/**
	 * Removes a resource from the CVTerm.
	 * 
	 * @param resource
	 */
	public void removeResource(String resource) {
		for (int i = resourceURIs.size(); i >= 0; i--) {
			if (resourceURIs.get(i).equals(resource))
				resourceURIs.remove(i);
		}
	}

	/**
	 * Sets the #BiolQualifierType_t of this CVTerm.
	 * 
	 * @param type
	 */
	public void setBiologicalQualifierType(Qualifier type) {
		if (type.toString().startsWith("BQB")) {
			if (this.type == Qualifier.BIOLOGICAL_QUALIFIER)
				this.typeQualifier = type;
			else
				throw new IllegalArgumentException(
						"Biological qualifiers can only be applyed if the type is set to Biological Qualifier.");
		} else
			throw new IllegalArgumentException(type.toString()
					+ " is not a valid Biological Qualifier.");
	}

	/**
	 * Sets the ModelQualifierType_t value of this CVTerm.
	 * 
	 * @param type
	 */
	public void setModelQualifierType(Qualifier type) {
		if (type.toString().startsWith("BQM"))
			typeQualifier = type;
		else
			throw new IllegalArgumentException(type.toString()
					+ " is not a valid model qualifier.");
	}

	/**
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
		} else
			throw new IllegalArgumentException(type.toString()
					+ " is not a valid qualifier.");
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
			eq &= t.resourceURIs.equals(resourceURIs);
			return eq;
		}
		return false;
	}

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
		if (resourceURIs.size() > 0)
			buffer.append(' ');
		for (String uri : resourceURIs) {
			if (i > 0)
				buffer.append(", ");
			buffer.append(uri);
		}
		buffer.append('.');
		return buffer.toString();
	}

}
