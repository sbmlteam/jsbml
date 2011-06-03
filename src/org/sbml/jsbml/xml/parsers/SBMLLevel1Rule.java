package org.sbml.jsbml.xml.parsers;

import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.ExplicitRule;
import org.sbml.jsbml.RateRule;

/**
 * Represent a level 1 rule. This class is used only during the parsing of level 1 files.
 * The real type of a rule will only be determined when we read the attribute 'type'. So on {@link SBMLCoreParser#processEndDocument(org.sbml.jsbml.SBMLDocument)}
 * if the level of the model is equal to 1, we loop over the list of rules and clone the rules into {@link RateRule} or {@link AssignmentRule}.
 * <p/>
 * As this class is supposed to be used only inside the {@link SBMLCoreParser}, it's visibility is put at the package level.
 * 
 * @author rodrigue
 *
 */
class SBMLLevel1Rule extends ExplicitRule {

	private String type;

	public SBMLLevel1Rule() {
	}
	
	/**
	 * Creates a new {@link ExplicitRule}
	 * 
	 * @param rule
	 */
	public SBMLLevel1Rule(SBMLLevel1Rule rule) {
		super(rule);
		
		if (rule.getType() != null) {
			setType(new String(rule.getType()));
		}
	}

	@Override
	public SBMLLevel1Rule clone() {
		return new SBMLLevel1Rule(this);
	}

	public RateRule cloneAsRateRule() {
		
		return new RateRule(this);
	}

	public AssignmentRule cloneAsAssignmentRule() {
		
		return new AssignmentRule(this);
	}
	
	
	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	@Override
	public boolean isScalar() {
		if (type == null || type.trim().equals("scalar")) {
			return true;
		}
		
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.MathContainer#readAttribute(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix, String value) 
	{
		boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
		
		if (!isAttributeRead) {
			if (getLevel() == 1) {
				if (attributeName.equals("type")) {
					setType(value);
					return true;
				} 				
			} 
		}
		return isAttributeRead;
	}

}
