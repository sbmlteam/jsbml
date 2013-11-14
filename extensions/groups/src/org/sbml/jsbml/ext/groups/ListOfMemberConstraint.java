package org.sbml.jsbml.ext.groups;

import java.text.MessageFormat;
import java.util.Map;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.TreeNodeChangeEvent;

public class ListOfMemberConstraint extends ListOf<MemberConstraint> implements NamedSBase {

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

	private boolean membersShareType;
	private boolean isSetMembersShareType;
	
	
	public ListOfMemberConstraint() {
		super();
	}

	public ListOfMemberConstraint(int level, int version) {
		super(level, version);
	}

	public ListOfMemberConstraint(ListOfMemberConstraint listOf) {
		super(listOf);
		
		if (listOf.isSetName()) {
			setName(listOf.getName());
		}
		if (listOf.isSetId()) {
			setId(listOf.getId());
		}
		if (listOf.isSetMembersShareType) {
			setMembersShareType(listOf.getMembersShareType());
		}
	}


	/* (non-Javadoc)
	 * @see org.sbml.jsbml.NamedSBase#getId()
	 */
	public String getId() {
		return isSetId() ? this.id : "";
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.NamedSBase#getName()
	 */
	public String getName() {
		return isSetName() ? this.name : "";
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 829;
		int hashCode = super.hashCode();
		if (isSetId()) {
			hashCode += prime * getId().hashCode();
		}
		if (isSetName()) {
			hashCode += prime * getName().hashCode();
		}
		return hashCode;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.NamedSBase#isSetId()
	 */
	public boolean isSetId() {
		return id != null;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.NamedSBase#isSetName()
	 */
	public boolean isSetName() {
		return name != null;
	}

	/**
	 * Checks if the sID is a valid identifier.
	 * 
	 * @param sID
	 *            the identifier to be checked. If null or an invalid
	 *            identifier, an exception will be thrown.
	 * @return {@code true} only if the sID is a valid identifier.
	 *         Otherwise this method throws an {@link IllegalArgumentException}.
	 *         This is an intended behavior.
	 * @throws IllegalArgumentException
	 *             if the given id is not valid in this model.
	 */
	boolean checkIdentifier(String sID) {
	  if ((sID == null) || !AbstractNamedSBase.isValidId(sID, getLevel(), getVersion())) {
	    throw new IllegalArgumentException(MessageFormat.format(
	      "\"{0}\" is not a valid identifier for this {1}.", sID, getElementName()));
	  }
	  return true;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.NamedSBase#setId(java.lang.String)
	 */
	public void setId(String id) {
		String property = TreeNodeChangeEvent.id;
		String oldId = this.id;
		
		// TODO - unregister id 
		
		if ((id == null) || (id.trim().length() == 0)) {
			this.id = null;
		} else if (checkIdentifier(id)) {
			this.id = id;
		}

		// TODO - register the id to the first IdentifierManager
		// should be done by a protected method in AbstractSBase
		
		firePropertyChange(property, oldId, this.id);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.NamedSBase#setName(java.lang.String)
	 */
	public void setName(String name) {
		// removed the call to the trim() function as a name with only space
		// should be considered valid.
		String oldName = this.name;
		if ((name == null) || (name.length() == 0)) {
			this.name = null;
		} else {
			this.name = name;
		}

		firePropertyChange(TreeNodeChangeEvent.name, oldName, this.name);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.NamedSBase#unsetId()
	 */
	public void unsetId() {
		setId(null);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.NamedSBase#unsetName()
	 */
	public void unsetName() {
		setName(null);
	}

	
	/**
	 * Returns the value of membersShareType
	 *
	 * @return the value of membersShareType
	 */
	public boolean getMembersShareType() {

		if (isSetMembersShareType()) {
			return membersShareType;
		}
		// This is necessary if we cannot return null here.
		throw new PropertyUndefinedError(GroupConstant.membersShareType, this);
	}

	/**
	 * Returns whether membersShareType is set 
	 *
	 * @return whether membersShareType is set 
	 */
	public boolean isSetMembersShareType() {
		return isSetMembersShareType;
	}

	/**
	 * Sets the value of membersShareType
	 */
	public void setMembersShareType(boolean membersShareType) {
		boolean oldMembersShareType = this.membersShareType;
		this.membersShareType = membersShareType;
		isSetMembersShareType = true;
		
		firePropertyChange(GroupConstant.membersShareType, oldMembersShareType, this.membersShareType);
	}

	/**
	 * Unsets the variable membersShareType 
	 *
	 * @return {@code true}, if membersShareType was set before, 
	 *         otherwise {@code false}
	 */
	public boolean unsetMembersShareType() {
		if (isSetMembersShareType()) {
			boolean oldMembersShareType = this.membersShareType;
			this.membersShareType = false;
			isSetMembersShareType = false;
			
			firePropertyChange(GroupConstant.membersShareType, oldMembersShareType, this.membersShareType);
			return true;
		}
		return false;
	}
	
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix, String value) {
		boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

		if (!isAttributeRead) {
			if (attributeName.equals("id")) {
				this.setId(value);
				return true;
			} else if (attributeName.equals("name")) {
				this.setName(value);

				return true;
			} else if (attributeName.equals(GroupConstant.membersShareType)) {
				this.setMembersShareType(StringTools.parseSBMLBoolean(value));

				return true;
			}
		}

		return isAttributeRead;
	}

	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#writeXMLAttributes()
	 */
	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();
	
		if (isSetId()) {
			attributes.put(GroupConstant.shortLabel + ":id", getId());
		}
		if (isSetName()) {
			attributes.put(GroupConstant.shortLabel + ":name", getName());
		}
		if (isSetMembersShareType) {
			attributes.put(GroupConstant.shortLabel + ":" + GroupConstant.membersShareType, Boolean.toString(getMembersShareType()));
		}
		
		return attributes;
	}

	@Override
	public boolean isIdMandatory() {
		return false;
	}
}
