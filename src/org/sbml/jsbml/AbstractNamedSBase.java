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

import java.util.Map;
import java.util.regex.Pattern;

/**
 * The base class for each SBML element with an optional id and name.
 * 
 * @author Andreas Dr&auml;ger
 * @author rodrigue
 * @author marine
 * @since 0.8
 * @version $Rev$
 */
public abstract class AbstractNamedSBase extends AbstractSBase implements
		NamedSBase {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -9186483076164094500L;

	/**
	 * Checks whether the given idCandidate is a valid identifier according to
	 * the SBML specifications.
	 * 
	 * @param idCandidate
	 *            The {@link String} to be tested.
	 * @param level
	 *            Level of the SBML to be used.
	 * @param version
	 *            Version of the SBML to be used.
	 * @return True if the argument satisfies the specification of identifiers
	 *         in the SBML specifications or false otherwise.
	 */
	public static final boolean isValidId(String idCandidate, int level,
			int version) {
		final String underscore = "_";
		final String letter = "a-zA-Z";
		final String digit = "0-9";
		final String idChar = "[" + letter + digit + underscore + "]";

		if (level == 1) {
			if (version == 1) {
				String SNameL1V1 = underscore + "*[" + letter + "]" + idChar
						+ '*';
				return Pattern.matches(SNameL1V1, idCandidate);

			} else if (version == 2) {
				String SNameL1V2 = "[" + letter + underscore + "]" + idChar
						+ '*';
				return Pattern.matches(SNameL1V2, idCandidate);
			}
		}

		// level undefined or level > 1
		String SIdL2 = "[" + letter + underscore + "]" + idChar + '*';
		return Pattern.matches(SIdL2, idCandidate);
	}

	/**
	 * id of the SBML component (can be optional depending on the level and
	 * version). Matches the id attribute of an element in a SBML file.
	 */
	private String id;

	/**
	 * name of the SBML component (can be optional depending on the level and
	 * version). Matches the name attribute of an element in a SBML file.
	 */
	private String name;

	/**
	 * Creates an {@link AbstractNamedSBase}. By default, id and name are null.
	 */
	public AbstractNamedSBase() {
		super();
		id = null;
		name = null;
	}

	/**
	 * Creates an {@link AbstractNamedSBase} from a given {@link AbstractNamedSBase}.
	 * 
	 * @param nsb an <code>AbstractNamedSBase</code> object to clone
	 */
	public AbstractNamedSBase(AbstractNamedSBase nsb) {
		super(nsb);
		this.id = nsb.isSetId() ? new String(nsb.getId()) : null;
		this.name = nsb.isSetName() ? new String(nsb.getName()) : null;
	}

	/**
	 * Creates an {@link AbstractNamedSBase} from a level and version. By default, id
	 * and name are null.
	 * 
	 * @param level the SBML level
	 * @param version the SBML version
	 */
	public AbstractNamedSBase(int level, int version) {
		this();
		setLevel(level);
		setVersion(version);
	}

	/**
	 * Creates an {@link AbstractNamedSBase} with the given identifier. Note
	 * that with this constructor the level and version of the element are not
	 * specified. These elements are however required to ensure the validity of
	 * the SBML data structure. Without level and version, it may not be
	 * possible to serialize this class to SBML.
	 * 
	 * @param id the id of this <code>AbstractNamedSBase</code>
	 */
	public AbstractNamedSBase(String id) {
		this();
		setId(id);
	}
	
	/**
	 * Creates an AbctractNamedSBase from an id, level and version.
	 * 
	 * @param id the id of this <code>AbstractNamedSBase</code>
	 * @param level the SBML level
	 * @param version the SBML version
	 */
	public AbstractNamedSBase(String id, int level, int version) {
		this(id, null, level, version);
	}

	/**
	 * Creates an AbctractNamedSBase from an id, name, level and version.
	 * 
	 * @param id the id of this <code>AbstractNamedSBase</code>
	 * @param name the name of this <code>AbstractNamedSBase</code>
	 * @param level the SBML level
	 * @param version the SBML version
	 */
	public AbstractNamedSBase(String id, String name, int level, int version) {
		this(level, version);
		setId(id);
		setName(name);
	}

	/**
	 * Checks if the sID is a valid identifier.
	 * 
	 * @param sID
	 *            the identifier to be checked. If null or an invalid
	 *            identifier, an exception will be thrown.
	 * @return <code>true</code> only if the sID is a valid identifier.
	 *         Otherwise this method throws an {@link IllegalArgumentException}.
	 *         This is an intended behavior.
	 * @throws IllegalArgumentException
	 *             if the given id is not valid in this model.
	 */
	boolean checkIdentifier(String sID) {
		if ((sID == null) || !isValidId(sID, getLevel(), getVersion())) {
			throw new IllegalArgumentException(String.format(
					"\"%s\" is not a valid identifier.", sID));
		}
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof NamedSBase) {
			boolean equals = super.equals(o);
			NamedSBase nsb = (NamedSBase) o;
			equals &= nsb.isSetId() == isSetId();
			if (nsb.isSetId() && isSetId()) {
				equals &= nsb.getId().equals(getId());
			}
			equals &= nsb.isSetName() == isSetName();
			if (equals && nsb.isSetName()) {
				equals &= nsb.getName().equals(getName());
			}
			return equals;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.NamedSBase#getId()
	 */
	public String getId() {
		return isSetId() ? this.id : "";
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.NamedSBase#getName()
	 */
	public String getName() {
		return isSetName() ? this.name : "";
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.NamedSBase#isSetId()
	 */
	public boolean isSetId() {
		return id != null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.NamedSBase#isSetName()
	 */
	public boolean isSetName() {
		return name != null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);

		// TODO : we should probably be careful there and check if there is a
		// prefix set before reading the id or name
		// as there are not defined at the level of the SBase on the SBML
		// specifications and some packages might define them in their own
		// name space.

		if (!isAttributeRead) {
			if (attributeName.equals("id") && (getLevel() > 1)) {
				this.setId(value);
				return true;
			} else if (attributeName.equals("name")) {
				this.setName(value);
				if (isSetLevel() && (getLevel() == 1)) {
					this.setId(value);
				}
				return true;
			}
		}
		return isAttributeRead;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.NamedSBase#setId(java.lang.String)
	 */
	public void setId(String id) {
		String property = getLevel() == 1 ? SBaseChangedEvent.name
				: SBaseChangedEvent.id;
		String oldId = this.id;
		if ((id == null) || (id.trim().length() == 0)) {
			this.id = null;
		} else if ((getLevel() == 3) || checkIdentifier(id)) {
			this.id = new String(id);
		}
		firePropertyChange(property, oldId, this.id);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.NamedSBase#setName(java.lang.String)
	 */
	public void setName(String name) {
		// removed the call to the trim() function as a name with only space
		// should be considered valid.
		String oldName = this.name;
		if ((name == null) || (name.length() == 0)) {
			this.name = null;
		} else {
			this.name = new String(name);
		}
		if (!isSetId() && (getLevel() == 1)) {
			setId(name);
		} else {
			// else part to avoid calling this method twice.
			firePropertyChange(SBaseChangedEvent.name, oldName, name);
		}
	}

	/**
	 * Returns the name of the component, if it is available. Otherwise,
	 * the identifier is returned. If both is not possible, the class name of
	 * this element is returned.
	 */
	@Override
	public String toString() {
		if (isSetName() && getName().length() > 0) {
			return name;
		}
		if (isSetId()) {
			return id;
		}
		return getClass().getSimpleName();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.NamedSBase#unsetId()
	 */
	public void unsetId() {
		setId(null);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.NamedSBase#unsetName()
	 */
	public void unsetName() {
		setName(null);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#writeXMLAttributes()
	 */
	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();

		if (isSetId() && (getLevel() > 1)) {
			attributes.put("id", getId());
		}
		if (isSetName()) {
			attributes.put("name", getName());
		}

		return attributes;
	}
}
