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
package org.sbml.jsbml.xml.parsers;

import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.ExplicitRule;
import org.sbml.jsbml.RateRule;

/**
 * Represent a level 1 rule. This class is used only during the parsing of level
 * 1 files. The real type of a rule will only be determined when we read the
 * attribute 'type'. So on
 * {@link SBMLCoreParser#processEndDocument(org.sbml.jsbml.SBMLDocument)} if the
 * level of the model is equal to 1, we loop over the list of rules and clone
 * the rules into {@link RateRule} or {@link AssignmentRule}.
 * <p>
 * As this class is supposed to be used only inside the {@link SBMLCoreParser},
 * it's visibility is put at the package level.
 * 
 * @author Nicolas Rodriguez
 * @since 0.8
 */
class SBMLLevel1Rule extends ExplicitRule {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -7511529049103686574L;
  /**
   * 
   */
  private String type;

  /**
   * 
   */
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ExplicitRule#clone()
   */
  @Override
  public SBMLLevel1Rule clone() {
    return new SBMLLevel1Rule(this);
  }

  /**
   * 
   * @return
   */
  public AssignmentRule cloneAsAssignmentRule() {
    return new AssignmentRule(this);
  }

  /**
   * 
   * @return
   */
  public RateRule cloneAsRateRule() {
    return new RateRule(this);
  }

  /**
   * 
   * @return
   */
  public String getType() {
    return type;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ExplicitRule#isScalar()
   */
  @Override
  public boolean isScalar() {
    if ((type == null) || type.trim().equals("scalar")) {
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.MathContainer#readAttribute(java.lang.String,java.lang.String, java.lang.String)
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

  /**
   * 
   * @param type
   */
  public void setType(String type) {
    this.type = type;
  }

}
