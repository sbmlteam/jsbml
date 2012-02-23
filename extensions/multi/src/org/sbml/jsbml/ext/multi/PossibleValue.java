package org.sbml.jsbml.ext.multi;

import java.util.Map;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.UniqueNamedSBase;

/**
 * Each state feature ({@link StateFeature}) also requires the definition of all the possible values it can take. Those values
 * will be used within a selector, to define the states an entity is allowed to take. A state feature is
 * not obligatory a boolean property, but can carry any number of {@link PossibleValue}. A {@link PossibleValue}
 * is identified by an id and an optional name.
 * 
 * 
 * @author rodrigue
 *
 */
public class PossibleValue extends AbstractNamedSBase  implements UniqueNamedSBase {

	public PossibleValue() {
		super();
		initDefaults();
	}

	public PossibleValue(PossibleValue possibleValue) {
		super(possibleValue);
		initDefaults();
	}


	@Override
	public AbstractSBase clone() {
		return new PossibleValue(this);
	}

	public boolean isIdMandatory() {
		return false;
	}

	/**
	 * 
	 */
	public void initDefaults() {
		addNamespace(MultiConstant.namespaceURI);
	}


	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
	 */
	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();

		if (isSetId()) {
			attributes.remove("id");
			attributes.put(MultiConstant.shortLabel+ ":id", getId());
		}
		if (isSetName()) {
			attributes.remove("name");
			attributes.put(MultiConstant.shortLabel+ ":name", getName());
		}

		return attributes;
	}


}
