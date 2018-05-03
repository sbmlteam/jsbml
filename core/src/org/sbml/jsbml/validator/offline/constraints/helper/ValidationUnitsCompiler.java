package org.sbml.jsbml.validator.offline.constraints.helper;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.CallableSBase;
import org.sbml.jsbml.SBMLError;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.Unit.Kind;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.util.compilers.ASTNodeValue;
import org.sbml.jsbml.util.compilers.UnitException;
import org.sbml.jsbml.util.compilers.UnitsCompiler;

/**
 * A units compiler which is also doing validation and reporting validation errors.
 * 
 * @author rodrigue
 * @since 1.3
 */
public class ValidationUnitsCompiler extends UnitsCompiler {

  /**
   * 
   */
  private boolean LIBSBML_VALIDATION_SYMBIOSIS = true;
  
  /**
   * 
   */
  private List<SBMLError> errors = new ArrayList<SBMLError>();
  
  
  /**
   * Reports an {@link SBMLError} if the given units do not
   * represent a dimensionless unit.
   * 
   * @param units the {@link UnitDefinition} to check
   */
  protected void checkForDimensionlessOrInvalidUnits(UnitDefinition units) {
    
    units.simplify();
    boolean legal = units.isVariantOfDimensionless();
    boolean invalid = units.isInvalid();

    if (!legal && !invalid) {
      // TODO - report an error - we might need some details about the ASTNode to build the error message properly !
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(org.sbml.jsbml.CallableSBase)
   */
  @Override
  public ASTNodeValue compile(CallableSBase variable) {
    ASTNodeValue value = new ASTNodeValue(variable, this);

    // TODO - get the derived unitDefinition from the user objects if there
    
    value.setUnits(variable.getDerivedUnitDefinition());
    return value;
  }

  
  @Override
  public ASTNodeValue plus(List<ASTNode> values) throws SBMLException {
    ASTNodeValue value = new ASTNodeValue(this);

    if (values == null || values.size() == 0) {
      return value;
    }

    int i = 0;
    ASTNodeValue compiledvalues[] = new ASTNodeValue[values.size()];
    
    for (ASTNode node : values) {
      compiledvalues[i++] = node.compile(this);
    }

    value.setValue(Integer.valueOf(0));
    UnitDefinition ud = new UnitDefinition(level, version);
    ud.addUnit(Unit.Kind.INVALID);
    value.setUnits(ud);

    i = compiledvalues.length - 1;
    boolean unitsSet = false;

    // TODO - check if the arguments have all the same unit, if not report an error
    
    // TODO - make use of : UnitDefinition.areEquivalent(ud1, ud2); 
    
    // TODO - libsbml is setting the units of the first argument as the global units of the 'plus'.
    
    while (i >= 0) {
      // Calculating the result value
      value.setValue(Double.valueOf(value.toDouble() + compiledvalues[i].toNumber().doubleValue()));
      
      // setting the result units as the first non invalid units found.
      if (!compiledvalues[i].getUnits().isInvalid() && !unitsSet) {
        value.setUnits(compiledvalues[i].getUnits());
        unitsSet = true;
      }
      i--;
    }

    if (value.getUnits() == null || value.getUnits().isInvalid()) {
      return value;
    }

    for (int j = i - 1; j >= 0; j--) {
      
      if (compiledvalues[j].getUnits() == null || compiledvalues[j].getUnits().isInvalid()
          || value.getUnits() == null || value.getUnits().isInvalid())
      {
        value.setUnits(ud);
        return value;
      }
      
      // We probably don't need to do all of that, just check that the units are equivalent - unifyUnits(value, compiledvalues[j]);
      
      value.setValue(Double.valueOf(value.toDouble() + compiledvalues[j].toNumber().doubleValue()));

    }

    return value;
  }
  
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#pow(org.sbml.jsbml.ASTNodeValue, org.sbml.jsbml.ASTNodeValue)
   */
  @Override
  public ASTNodeValue pow(ASTNode base, ASTNode exponent)
      throws SBMLException 
  {
    return pow(base.compile(this), exponent.compile(this));
  }

  /**
   * 
   * @param base
   * @param exponent
   * @return
   * @throws SBMLException
   */
  protected ASTNodeValue pow(ASTNodeValue base, ASTNodeValue exponent)
      throws SBMLException 
  {
    UnitDefinition expUnits = exponent.getUnits();

    if (!expUnits.isVariantOfDimensionless()) {
      // TODO - report an error if it is not dimensionless
      return invalid();
    }
      
    double exp = Double.NaN, v;
    v = exponent.toDouble();
    exp = v == 0d ? 0d : 1d / v;
    
    if (exp == 0d) {
      UnitDefinition ud = new UnitDefinition(level, version);
      ud.addUnit(Kind.DIMENSIONLESS);
      ASTNodeValue value = new ASTNodeValue(ud, this);
      value.setValue(Integer.valueOf(1));
      return value;
    }
    if (!Double.isNaN(exp)) {
      return root(exp, base);
    }

    return new ASTNodeValue(this);
  }

  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#root(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue root(ASTNode rootExponent, ASTNode radiant) throws SBMLException {
    
    if (rootExponent.isSetUnits()) {
      checkForDimensionlessOrInvalidUnits(rootExponent.getUnitsInstance());
    }
    if (rootExponent.isNumber()) {

      if (!(rootExponent.isInteger() || rootExponent.isRational())) {
        checkForDimensionlessOrInvalidUnits(rootExponent
          .getUnitsInstance());
      }

      return root(rootExponent.compile(this).toDouble(), radiant);
    }

    return invalid();
  }

  /**
   * 
   * @param rootExponent
   * @param radiant
   * @return
   * @throws SBMLException
   */
  protected ASTNodeValue root(double rootExponent, ASTNodeValue radiant)
      throws SBMLException 
  {
    UnitDefinition ud = radiant.getUnits().clone();
    
    for (Unit u : ud.getListOfUnits()) {
      
      if ((((u.getExponent() / rootExponent) % 1d) != 0d) && !u.isDimensionless() && !u.isInvalid()) {
        
        // TODO - report an error ?
        
        new UnitException(MessageFormat.format(
          "Cannot perform power or root operation due to incompatibility with a unit exponent. Given are {0,number} as the exponent of the unit and {1,number} as the root exponent for the current computation.",
          u.getExponent(), rootExponent));
      }

      if (!(u.isDimensionless() || u.isInvalid())) {
        u.setExponent(u.getExponent() / rootExponent);
      }

    }
    ASTNodeValue value = new ASTNodeValue(ud, this);
    value.setValue(Double.valueOf(Math.pow(radiant.toDouble(), 1d / rootExponent)));
    return value;
  }

  // TODO - piecewise, delay
  // TODO - or, xor and all logical operators : check that all have the same units - and
  // TODO - neq and all relational  operators : check that all have the same units - lt, leq, gt, geq, eq - could be the same method than other operators
  // TODO - minus - if we do not calculate the value, could be the same method than plus
  
  // log, ln, floor, factorial, ceiling, abs  ?
  
}
