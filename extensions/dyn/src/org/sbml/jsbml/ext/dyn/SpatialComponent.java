package org.sbml.jsbml.ext.dyn;

import java.text.MessageFormat;
import java.util.Map;
import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.UniqueNamedSBase;

/**
 * @author Harold Gomez
 * @since 1.0
 */
public class SpatialComponent extends AbstractNamedSBase implements
		UniqueNamedSBase {

	/**
	 * Generated serial version identifier
	 */
	private static final long serialVersionUID = 3081752496996673423L;

	/**
	 * Identifies individual components of spatial location, orientation or
	 * force vector of an object
	 */
	private SpatialKind spatialIndex;

	/**
	 * Stores the Id of a variable element defined in the model
	 */
	private String variable;

	/**
	 * Empty constructor
	 */
	public SpatialComponent() {
		super();
		initDefaults();
	}

	/**
	 * Initializes custom Class attributes
	 * */
	private void initDefaults() {
		setNamespace(DynConstants.namespaceURI);
		variable = null;
		spatialIndex = null;
	}

	/**
	 * Constructor
	 * 
	 * @param level
	 * @param version
	 */
	public SpatialComponent(int level, int version) {
		this(null, null, level, version);
	}

	/**
	 * Constructor
	 * 
	 * @param level
	 * @param version
	 */
	public SpatialComponent(String id, String name, int level, int version) {
		super(id, name, level, version);
		initDefaults();
	}

	/**
	 * Constructor
	 * 
	 * @param spatialComponent
	 */
	public SpatialComponent(SpatialComponent spatialComponent) {
		super(spatialComponent);

		if (spatialComponent.isSetSpatialIndex()) {
			setSpatialIndex(spatialComponent.getSpatialIndex());
		}
		if (spatialComponent.isSetVariable()) {
			setVariable(spatialComponent.getVariable());
		}
	}

	@Override
	public boolean isIdMandatory() {
		return false;
	}

	@Override
	public AbstractSBase clone() {
		return new SpatialComponent(this);
	}

	/**
	 * Returns the value of spatialIndex
	 * 
	 * @return the value of spatialIndex
	 */
	public SpatialKind getSpatialIndex() {
		if (isSetSpatialIndex()) {
			return spatialIndex;
		}
		return null;
	}

	/**
	 * Returns whether spatialIndex is set
	 * 
	 * @return whether spatialIndex is set
	 */
	public boolean isSetSpatialIndex() {
		return this.spatialIndex != null;
	}

	/**
	 * Sets the value of spatialIndex
	 * 
	 * @param spatialIndex
	 */
	public void setSpatialIndex(SpatialKind spatialIndex) {
		SpatialKind oldSpatialIndex = this.spatialIndex;
		this.spatialIndex = spatialIndex;
		firePropertyChange(DynConstants.spatialIndex, oldSpatialIndex,
				this.spatialIndex);
	}

	/**
	 * Unsets the variable spatialIndex
	 * 
	 * @return {@code true}, if spatialIndex was set before, otherwise
	 *         {@code false}
	 */
	public boolean unsetSpatialIndex() {
		if (isSetSpatialIndex()) {
			SpatialKind oldSpatialIndex = this.spatialIndex;
			this.spatialIndex = null;
			firePropertyChange(DynConstants.spatialIndex, oldSpatialIndex,
					this.spatialIndex);
			return true;
		}
		return false;
	}

	/**
	 * Returns the value of variable
	 *
	 * @return the value of variable
	 */
	public String getVariable() {
		if (isSetVariable()) {
			return variable;
		}
		return null;
	}

	/**
	 * Returns whether variable is set
	 *
	 * @return whether variable is set
	 */
	public boolean isSetVariable() {
		return this.variable != null;
	}

	/**
	 * Sets the value of variable
	 */
	public void setVariable(String variable) {
		String oldVariable = this.variable;
		this.variable = variable;
		firePropertyChange(DynConstants.variable, oldVariable, this.variable);
	}

	/**
	 * Unsets the variable field
	 *
	 * @return {@code true}, if variable was set before, otherwise {@code false}
	 */
	public boolean unsetField() {
		if (isSetVariable()) {
			String oldVariable = this.variable;
			this.variable = null;
			firePropertyChange(DynConstants.variable, oldVariable,
					this.variable);
			return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!super.equals(object)) {
			return false;
		}
		if (getClass() != object.getClass()) {
			return false;
		}
		SpatialComponent other = (SpatialComponent) object;
		if (variable == null) {
			if (other.variable != null) {
				return false;
			}
		} else if (!variable.equals(other.variable)) {
			return false;
		}
		if (spatialIndex == null) {
			if (other.spatialIndex != null) {
				return false;
			}
		} else if (!spatialIndex.equals(other.spatialIndex)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 3467;
		int result = super.hashCode();
		result = prime * result + ((variable == null) ? 0 : variable.hashCode());
		result = prime * result
				+ ((spatialIndex == null) ? 0 : spatialIndex.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "DynElement [spatialIndex=" + spatialIndex + " variable=" + variable + "]";
	}

	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();

		if (isSetVariable()) {
			attributes.remove("variable");
			attributes.put(DynConstants.shortLabel + ":" + DynConstants.variable,
					variable);
		}
		if (isSetSpatialIndex()) {
			attributes.remove("spatialIndex");
			attributes.put(DynConstants.shortLabel + ":"
					+ DynConstants.spatialIndex, spatialIndex.toString());
		}
		if (isSetId()) {
			attributes.remove("id");
			attributes.put(DynConstants.shortLabel + ":id", getId());
		}
		if (isSetName()) {
			attributes.remove("name");
			attributes.put(DynConstants.shortLabel + ":name", getName());
		}
		return attributes;
	}

	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);
		try {
			if (!isAttributeRead) {
				if (attributeName.equals(DynConstants.variable)) {
					setVariable(value);
					return true;
				}
				if (attributeName.equals(DynConstants.spatialIndex)) {
					setSpatialIndex(SpatialKind.valueOf(value));
					return true;
				}
			}
		} catch (Exception e) {
			MessageFormat.format(
					DynConstants.bundle.getString("COULD_NOT_READ_SPATIALCOMPONENT"), value,
					attributeName.equals(DynConstants.variable)?DynConstants.variable:DynConstants.spatialIndex);
		}

		return isAttributeRead;
	}
}
