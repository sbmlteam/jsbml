/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2019 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The Babraham Institute, Cambridge, UK
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.distrib;

import java.text.MessageFormat;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.AbstractMathContainer;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.text.parser.ParseException;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.TreeNodeChangeEvent;
import org.sbml.jsbml.util.converters.ExpandFunctionDefinitionConverter;

/**
 * Defines one uncertainty statistic about the parent element. 
 * 
 * <p>It has one required attribute 'type' which defines what statistic it describes (i.e. 'mean', 'standardDeviation', 'distribution',
 * etc.). Its other attributes (value, var, units, and definitionURL), and children (math and listOfUncertParameters)
 * are all optional, each usable according to which type it is.</p>
 * 
 * @author rodrigue
 * @since 1.5
 */
public class UncertParameter extends AbstractDistribSBase implements MathContainer { // TODO - do we extends directly AbstractMathcontainer ?

  /**
   * The different {@link UncertParameter} and {@link UncertSpan} type values.
   * 
   * @author rodrigue
   *
   */
  public static enum Type {
    
    // The following kinds are all single-value types, and thus may either be defined by value or var,
    // and must only be used for UncertParameter elements, not UncertSpan elements.
    // Definitions taken from https://web.archive.org/web/20161029215725/uncertml.org/
      
    /**
     * For a random variable with mean μ and strictly positive standard deviation σ,
     * the coefficient of variation is defined as the ratio (σ / |μ|).One benefit of using the 
     * coefficient of variation rather than the standard deviation is that it is unitless.
     */
    coefficientOfVariation,
    /**
     * The kurtosis of a distribution is a measure of how peaked the distribution is. 
     */
    kurtosis,
    /**
     * The arithmetic mean (typically just the mean) is what is commonly called the average. 
     */
    mean,
    /**
     * The median is described as the numeric value separating the higher half of a sample (or population)
     * from the lower half. The median of a finite list of numbers can be found by arranging all the observations
     * from lowest value to highest value and picking the middle one. If there is an even number of observations,
     * then there is no single middle value, then the average of the two middle values is used. The median is also the
     * 0.5 quantile, or 50th percentile.
     */
    median,
    /**
     * The mode is the value that occurs the most frequently in a data set (or a probability distribution). It
     * need not be unique (e.g., two or more quantities occur equally often) and is typically defined for continuous
     * valued quantities by first defining the histogram, and then giving the central value of the bin containing the
     * most counts.
     */
    mode,
    /**
     * The sample size is a direct count of the number of observations made or the number of samples
     * measured. It is used in several other statistical measurements, and can be used to convert one to another.
     */
    sampleSize,
    /**
     * The skewness of a random variable is a measure of how asymmetric the corresponding probability
     * distribution is.
     */
    skewness,
    /**
     * The standard deviation of a distribution or population is the square root of its variance.
     * It is a widely used measure of the variability or dispersion since it is reported in the natural
     * units of the quantity being considered. Note that if a finite sample of a population has been
     * used then the sample standard deviation is the appropriate unbiased estimator to use.
     */
    standardDeviation,    
    /**
     * The standard error is the standard deviation of estimates of a population value. If that population
     * value is a mean, this statistic is called the standard error of the mean. It is calculated as the standard
     * deviation of a sample divided by the square root of the number of the sample size. As the sample size 
     * increases, the sample size draws closer to the population size, and the standard error approaches zero.
     */
    standardError,
    /**
     * The variance of a random quantity (or distribution) is the average value of the square of the
     * deviation of that variable from its mean.
     */
    variance,
    
    // The following Type values are all spans, and may only be used for UncertSpan elements. They are defined by
    // an upper and lower value. Definitions taken from taken from https://web.archive.org/web/20161029215725/uncertml.org/.

    /**
     * For a univariate random variable x, a confidence interval is a range [a, b], a < b, so that x lies between a and b
     * with given probability. For example, a 95% confidence interval is a range in which x falls 95% of the time
     * (or with probability 0.95). Confidence intervals provide intuitive summaries of the statistics of the variable x.
     * 
     * <p>Unless specified otherwise, the confidence interval is usually chosen so that the remaining probability is split
     * equally, that is P (x < a) = P (x > b). If x has a symmetric distribution, then the confidence intervals are usually
     * centered around the mean. However, non-centered confidence intervals are possible and are better described by their 
     * lower and upper quantiles or levels. For example, a 50% confidence interval would usually lie between the 25% and 
     * 75% quantiles, but could in theory also lie between the 10% and 60% quantiles, although this would be rare in practice.
     * The confidenceInterval allows you the flexibility to specify non-symmetric confidence intervals however in practice 
     * we would expect the main usage to be for symmetric intervals.</p>
     * 
     * <p>The confidenceInterval child of a Uncertainty is always the 95% confidence interval. For other confidence intervals,
     * use an UncertParameter of type 'externalParameter' instead.</p>
     */
    confidenceInterval,
    /**
     * In Bayesian statistics, a credible interval is similar to a confidence interval determined from the posterior 
     * distribution of a random variable x. That is, given a prior distribution p(x) and some observations D, the 
     * posterior probability p(x | D) can be computed using Bayes theorem. Note that the interpretation of a credible 
     * interval is not the same as a (frequentist) confidence interval.
     * 
     * <p>The credibleInterval child of a Uncertainty is always the 95% credible interval. For other credibility
     * intervals, use an UncertParameter of type 'externalParameter' instead.</p>
     */
    credibleInterval,
    /**
     * The interquartile range is the range between the 1st and 3rd quartiles. It contains the middle 50% of the 
     * sample realisations (or of the sample probability). It is typically used and shown in box plots.
     */
    interquartileRange,
    /**
     * The range is the interval [a, b] so that a < b and contains all possible values of x. This is also often called 
     * the statistical range, which is the distance from the smallest value to the largest value in a sample dataset. 
     * For a sample dataset X = (x 1 , ..., x N ), the range is the distance from the smallest x i to the largest. It is 
     * often used as a first estimate of the sample dispersion.
     */
    range,
    
    //  Finally, we have the 'distribution' and 'externalParameter' types
    
    
    /**
     * If the uncertainty is defined by a known distribution, that distribution may either be defined by using the 
     * child math element, or by using the definitionURL . When the math child is used, that math should contain 
     * the distribution in question: typically this will be a distribution csymbol but may be something more 
     * complicated, like a piecewise function. If the definitionURL is used, many more distributions may be used than 
     * are defined in this specification (like an externalParameter , below). To fully define this distribution, it 
     * will almost certainly be necessary to further define that distribution with child UncertParameter elements. For 
     * example, a Beta distribution takes two parameters (α and β), each of which could be defined by a child 
     * UncertParameter of type 'externalParameter', with appropriate definitionURL values. A type of value 
     * 'distribution' is only valid for UncertParameter elements, not UncertSpan elements.
     */
    distribution,
    /**
     * This type is uniquely described by an appropriate definitionURL, and is provided to allow a modeler to 
     * encode externally-provided parameters not otherwise explicitly handled by this specification. The range of 
     * possibilities is vast, so modelers should ensure that the tool they wish to use encodes support for any 
     * UncertParameter they define. As an external parameter may take any form, there are no restrictions on what 
     * other attributes or children may be used by an UncertParameter of this type: it may be a single value; it may 
     * be a span; it may be defined by a child math element; it may be defined by child UncertParameter elements; it 
     * may be defined by any combination of the above. The only restriction is that the definitionURL must be defined 
     * for any UncertParameter of type 'externalParameter'. This type value may be used for either UncertParameter or UncertSpan elements.
     */
    externalParameter
  };

  /**
   * A logger for user-messages.
   */
  private static final transient Logger logger = Logger.getLogger(AbstractMathContainer.class);

  /**
   * 
   */
  private Type type;
  
  /**
   * 
   */
  private Double value;
  
  /**
   * 
   */
  private String var;
  
  /**
   * 
   */
  private String units;
  
  /**
   * 
   */
  private String definitionURL;
  
  /**
   * The math formula as an abstract syntax tree.
   */
  private ASTNode math;

  /**
   * 
   */
  private ListOf<UncertParameter> listOfUncertParameters;

  /**
   * Creates an UncertParameter instance 
   */
  public UncertParameter() {
    super();
    initDefaults();
  }

  /**
   * Creates a UncertParameter instance with an id.
   * 
   * @param id the identifier for the new element.
   */
  public UncertParameter(String id) {
    super(id);
    initDefaults();
  }

  /**
   * Creates a UncertParameter instance with a level and version.
   * 
   * @param level SBML Level
   * @param version SBML Version
   */
  public UncertParameter(int level, int version) {
    this(null, null, level, version);
  }

  /**
   * Creates a UncertParameter instance with an id, level, and version.
   * 
   * @param id the identifier for this element.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public UncertParameter(String id, int level, int version) {
    this(id, null, level, version);
  }

  /**
   * Creates a UncertParameter instance with an id, name, level, and version.
   * 
   * @param id the identifier for this element.
   * @param name a human-readable name for this element that can be used for display purposes.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public UncertParameter(String id, String name, int level, int version) {
    super(id, name, level, version);
    initDefaults();
  }

  /**
   * Clone constructor
   */
  public UncertParameter(UncertParameter obj) {
    super(obj);

    if (obj.isSetType()) {
      setType(obj.getType());
    }
    if (obj.isSetValue()) {
      setValue(obj.getValue());
    }
    if (obj.isSetVar()) {
      setVar(obj.getVar());
    }
    if (obj.isSetValue()) {
      setValue(obj.getValue());
    }
    if (obj.isSetUnits()) {
      setUnits(obj.getUnits());
    }
    if (obj.isSetDefinitionURL()) {
      setDefinitionURL(obj.getDefinitionURL());
    }
    if (obj.isSetMath()) {
      setMath(obj.getMath().clone());
    }
    if (obj.isSetListOfUncertParameters()) {
      setListOfUncertParameters(obj.getListOfUncertParameters().clone());
    }
  }

  /**
   * Clones this class
   */
  public UncertParameter clone() {
    return new UncertParameter(this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setNamespace(DistribConstants.namespaceURI); // TODO - removed once the mechanism are in place to set package version and namespace
    packageName = DistribConstants.shortLabel;
    setPackageVersion(-1);
  }

  
  /**
   * Returns the value of {@link #type}.
   *
   * @return the value of {@link #type}.
   */
  public Type getType() {
    if (isSetType()) {
      return type;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(DistribConstants.type, this);
  }

  /**
   * Returns whether {@link #type} is set.
   *
   * @return whether {@link #type} is set.
   */
  public boolean isSetType() {
    return type != null;
  }

  /**
   * Sets the value of type
   *
   * @param type the value of type to be set.
   */
  public void setType(Type type) {
    Type oldType = this.type;
    this.type = type;
    firePropertyChange(DistribConstants.type, oldType, this.type);
  }

  /**
   * Unsets the variable type.
   *
   * @return {@code true} if type was set before, otherwise {@code false}.
   */
  public boolean unsetType() {
    if (isSetType()) {
      Type oldType = this.type;
      this.type = null;
      firePropertyChange(DistribConstants.type, oldType, this.type);
      return true;
    }
    return false;
  }
  
  
  /**
   * Returns the value of {@link #value}.
   *
   * @return the value of {@link #value}.
   */
  public double getValue() {

    if (isSetValue()) {
      return value;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(DistribConstants.value, this);
  }

  /**
   * Returns whether {@link #value} is set.
   *
   * @return whether {@link #value} is set.
   */
  public boolean isSetValue() {
    return value != null;
  }

  /**
   * Sets the value of value
   *
   * @param value the value of value to be set.
   */
  public void setValue(double value) {
    Double oldValue = this.value;
    this.value = value;
    firePropertyChange(DistribConstants.value, oldValue, this.value);
  }

  /**
   * Unsets the variable value.
   *
   * @return {@code true} if value was set before, otherwise {@code false}.
   */
  public boolean unsetValue() {
    if (isSetValue()) {
      Double oldValue = this.value;
      this.value = null;
      firePropertyChange(DistribConstants.value, oldValue, this.value);
      return true;
    }
    return false;
  }
  
  
  /**
   * Returns the value of {@link #var}.
   *
   * @return the value of {@link #var}.
   */
  public String getVar() {
    if (isSetVar()) {
      return var;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(DistribConstants.var, this);
  }

  /**
   * Returns whether {@link #var} is set.
   *
   * @return whether {@link #var} is set.
   */
  public boolean isSetVar() {
    return var != null;
  }

  /**
   * Sets the value of var
   *
   * @param var the value of var to be set.
   */
  public void setVar(String var) {
    String oldVar = this.var;
    this.var = var;
    firePropertyChange(DistribConstants.var, oldVar, this.var);
  }

  /**
   * Unsets the variable var.
   *
   * @return {@code true} if var was set before, otherwise {@code false}.
   */
  public boolean unsetVar() {
    if (isSetVar()) {
      String oldVar = this.var;
      this.var = null;
      firePropertyChange(DistribConstants.var, oldVar, this.var);
      return true;
    }
    return false;
  }
  
  
  /**
   * Returns the value of {@link #units}.
   *
   * @return the value of {@link #units}.
   */
  public String getUnits() {
    if (isSetUnits()) {
      return units;
    }

    return null;
  }

  /**
   * Returns whether {@link #units} is set.
   *
   * @return whether {@link #units} is set.
   */
  public boolean isSetUnits() {
    return units != null;
  }

  /**
   * Sets the value of units
   *
   * @param units the value of units to be set.
   */
  public void setUnits(String units) {
    String oldUnits = this.units;
    this.units = units;
    firePropertyChange(DistribConstants.units, oldUnits, this.units);
  }

  /**
   * Unsets the variable units.
   *
   * @return {@code true} if units was set before, otherwise {@code false}.
   */
  public boolean unsetUnits() {
    if (isSetUnits()) {
      String oldUnits = this.units;
      this.units = null;
      firePropertyChange(DistribConstants.units, oldUnits, this.units);
      return true;
    }
    return false;
  }
  
  
  /**
   * Returns the value of {@link #definitionURL}.
   *
   * @return the value of {@link #definitionURL}.
   */
  public String getDefinitionURL() {
    if (isSetDefinitionURL()) {
      return definitionURL;
    }

    return null;
  }

  /**
   * Returns whether {@link #definitionURL} is set.
   *
   * @return whether {@link #definitionURL} is set.
   */
  public boolean isSetDefinitionURL() {
    return definitionURL != null;
  }

  /**
   * Sets the value of definitionURL
   *
   * @param definitionURL the value of definitionURL to be set.
   */
  public void setDefinitionURL(String definitionURL) {
    String oldDefinitionURL = this.definitionURL;
    this.definitionURL = definitionURL;
    firePropertyChange(DistribConstants.definitionURL, oldDefinitionURL, this.definitionURL);
  }

  /**
   * Unsets the variable definitionURL.
   *
   * @return {@code true} if definitionURL was set before, otherwise {@code false}.
   */
  public boolean unsetDefinitionURL() {
    if (isSetDefinitionURL()) {
      String oldDefinitionURL = this.definitionURL;
      this.definitionURL = null;
      firePropertyChange(DistribConstants.definitionURL, oldDefinitionURL, this.definitionURL);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 907;
    int result = super.hashCode();
    result = prime * result
        + ((definitionURL == null) ? 0 : definitionURL.hashCode());
    result = prime * result + ((listOfUncertParameters == null) ? 0
        : listOfUncertParameters.hashCode());
    result = prime * result + ((math == null) ? 0 : math.hashCode());
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    result = prime * result + ((units == null) ? 0 : units.hashCode());
    result = prime * result + ((value == null) ? 0 : value.hashCode());
    result = prime * result + ((var == null) ? 0 : var.hashCode());
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    UncertParameter other = (UncertParameter) obj;
    if (definitionURL == null) {
      if (other.definitionURL != null) {
        return false;
      }
    } else if (!definitionURL.equals(other.definitionURL)) {
      return false;
    }
    if (listOfUncertParameters == null) {
      if (other.listOfUncertParameters != null) {
        return false;
      }
    } else if (!listOfUncertParameters.equals(other.listOfUncertParameters)) {
      return false;
    }
    if (math == null) {
      if (other.math != null) {
        return false;
      }
    } else if (!math.equals(other.math)) {
      return false;
    }
    if (type != other.type) {
      return false;
    }
    if (units == null) {
      if (other.units != null) {
        return false;
      }
    } else if (!units.equals(other.units)) {
      return false;
    }
    if (value == null) {
      if (other.value != null) {
        return false;
      }
    } else if (!value.equals(other.value)) {
      return false;
    }
    if (var == null) {
      if (other.var != null) {
        return false;
      }
    } else if (!var.equals(other.var)) {
      return false;
    }
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithDerivedUnit#containsUndeclaredUnits()
   */
  @Override
  public boolean containsUndeclaredUnits() {
    return isSetMath() ? math.containsUndeclaredUnits() : false;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithDerivedUnit#getDerivedUnitDefinition()
   */
  @Override
  public UnitDefinition getDerivedUnitDefinition() {
    UnitDefinition ud = null;
    if (isSetMath()) {
      Model m = getModel();
      ASTNode expandedMath = math;
      
      if (m != null && m.getFunctionDefinitionCount() > 0) {
        expandedMath = ExpandFunctionDefinitionConverter.expandFunctionDefinition(m, math);
      }
      
      try {
        ud = expandedMath.deriveUnit();
      } catch (Throwable exc) {
        // Doesn't matter. We'll simply return an undefined unit.
        String name;
        if (this instanceof NamedSBase) {
          name = toString();
        } else {
          name = getElementName();
          SBase parent = getParentSBMLObject();
          if ((parent != null) && (parent instanceof NamedSBase)) {
            name = MessageFormat.format(
              resourceBundle.getString("AbstractMathContainer.inclusion"),
              name, parent.toString());
          }
        }
        logger.warn(MessageFormat.format(
          resourceBundle.getString("AbstractMathContainer.getDerivedUnitDefinition"),
          name, exc.getLocalizedMessage()));
        logger.debug(exc.getLocalizedMessage(), exc);
      }
    }
    if (ud != null) {
      Model m = getModel();
      if (m != null) {
        UnitDefinition u = m.isSetListOfUnitDefinitions() ? m.findIdentical(ud) : null;
        return (u != null) ? u : ud;
      }
    } else {
      ud = new UnitDefinition(getLevel(), getVersion());
      ud.createInvalidUnit();
    }
    return ud;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithDerivedUnit#getDerivedUnits()
   */
  @Override
  @SuppressWarnings("deprecation")
  public String getDerivedUnits() {
    UnitDefinition ud = getDerivedUnitDefinition();
    Model m = getModel();
    if (m != null) {
      if (m.getUnitDefinition(ud.getId()) != null) {
        return ud.getId();
      }
    }
    if (ud.getUnitCount() == 1) {
      Unit u = ud.getUnit(0);
      if ((u.getOffset() == 0) && (u.getMultiplier() == 1)
          && (u.getScale() == 0) && (u.getExponent() == 1)) {
        return u.getKind().toString().toLowerCase();
      }
    }
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.MathContainer#getFormula()
   */
  @Override
  @Deprecated
  public String getFormula() {
    try {
      return isSetMath() ? getMath().toFormula() : "";
    } catch (Throwable exc) {
      logger.warn(resourceBundle.getString("AbstractMathContainer.toFormula"), exc);
      return "invalid";
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.MathContainer#getMath()
   */
  @Override
  public ASTNode getMath() {
    return math;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.MathContainer#getMathMLString()
   */
  @Override
  public String getMathMLString() {
    if (isSetMath()) {
      return math.toMathML();
    }
    return "";
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.MathContainer#isSetMath()
   */
  @Override
  public boolean isSetMath() {
    return math != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.MathContainer#setFormula(java.lang.String)
   */
  @Override
  @Deprecated
  public void setFormula(String formula) throws ParseException {
    setMath(ASTNode.parseFormula(formula));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.MathContainer#setMath(org.sbml.jsbml.ASTNode)
   */
  @Override
  public void setMath(ASTNode math) {
    ASTNode oldMath = this.math;
    this.math = math;
    if (oldMath != null) {
      oldMath.fireNodeRemovedEvent();
    }
    if (this.math != null) {
      ASTNode.setParentSBMLObject(math, this);
      firePropertyChange(TreeNodeChangeEvent.math, oldMath, this.math);
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.MathContainer#unsetFormula()
   */
  @Override
  @Deprecated
  public void unsetFormula() {
    unsetMath();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.MathContainer#unsetMath()
   */
  @Override
  public void unsetMath() {
    setMath(null);
  }
  
  
  /**
   * Returns {@code true} if {@link #listOfUncertParameters} contains at least
   * one element.
   *
   * @return {@code true} if {@link #listOfUncertParameters} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean isSetListOfUncertParameters() {
    if (listOfUncertParameters == null) {
      return false;
    }
    return true;
  }

  /**
   * Returns the {@link #listOfUncertParameters}.
   * Creates it if it does not already exist.
   *
   * @return the {@link #listOfUncertParameters}.
   */
  public ListOf<UncertParameter> getListOfUncertParameters() {
    if (listOfUncertParameters == null) {
      listOfUncertParameters = new ListOf<UncertParameter>();
      listOfUncertParameters.setNamespace(DistribConstants.namespaceURI); // TODO - removed once the mechanism are in place to set package version and namespace
      listOfUncertParameters.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'distrib'
      listOfUncertParameters.setPackageName(null);
      listOfUncertParameters.setPackageName(DistribConstants.shortLabel);
      listOfUncertParameters.setSBaseListType(ListOf.Type.other);

      registerChild(listOfUncertParameters);
    }
    return listOfUncertParameters;
  }

  /**
   * Sets the given {@code ListOf<UncertParameter>}.
   * If {@link #listOfUncertParameters} was defined before and contains some
   * elements, they are all unset.
   *
   * @param listOfUncertParameters the list to set
   */
  public void setListOfUncertParameters(ListOf<UncertParameter> listOfUncertParameters) {
    unsetListOfUncertParameters();
    this.listOfUncertParameters = listOfUncertParameters;

    if (listOfUncertParameters != null) {
      listOfUncertParameters.unsetNamespace();
      listOfUncertParameters.setNamespace(DistribConstants.namespaceURI); // TODO - removed once the mechanism are in place to set package version and namespace
      listOfUncertParameters.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'distrib'
      listOfUncertParameters.setPackageName(null);
      listOfUncertParameters.setPackageName(DistribConstants.shortLabel);
      this.listOfUncertParameters.setSBaseListType(ListOf.Type.other);

      registerChild(listOfUncertParameters);
    }
  }

  /**
   * Returns {@code true} if {@link #listOfUncertParameters} contains at least
   * one element, otherwise {@code false}.
   *
   * @return {@code true} if {@link #listOfUncertParameters} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean unsetListOfUncertParameters() {
    if (isSetListOfUncertParameters()) {
      ListOf<UncertParameter> oldUncertParameters = this.listOfUncertParameters;
      this.listOfUncertParameters = null;
      oldUncertParameters.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Adds a new {@link UncertParameter} to the {@link #listOfUncertParameters}.
   * <p>The listOfUncertParameters is initialized if necessary.
   *
   * @param uncertParameter the element to add to the list
   * @return {@code true} (as specified by {@link java.util.Collection#add})
   * @see java.util.Collection#add(Object)
   */
  public boolean addUncertParameter(UncertParameter uncertParameter) {
    return getListOfUncertParameters().add(uncertParameter);
  }

  /**
   * Removes an element from the {@link #listOfUncertParameters}.
   *
   * @param uncertParameter the element to be removed from the list.
   * @return {@code true} if the list contained the specified element and it was
   *         removed.
   * @see java.util.List#remove(Object)
   */
  public boolean removeUncertParameter(UncertParameter uncertParameter) {
    if (isSetListOfUncertParameters()) {
      return getListOfUncertParameters().remove(uncertParameter);
    }
    return false;
  }

  /**
   * Removes an element from the {@link #listOfUncertParameters}.
   *
   * @param uncertParameterId the id of the element to be removed from the list.
   * @return the removed element, if it was successfully found and removed or
   *         {@code null}.
   */
  public UncertParameter removeUncertParameter(String uncertParameterId) {
    if (isSetListOfUncertParameters()) {
      return getListOfUncertParameters().remove(uncertParameterId);
    }
    return null;
  }

  /**
   * Removes an element from the {@link #listOfUncertParameters} at the given index.
   *
   * @param i the index where to remove the {@link UncertParameter}.
   * @return the specified element if it was successfully found and removed.
   * @throws IndexOutOfBoundsException if the listOf is not set or if the index is
   *         out of bound ({@code (i < 0) || (i > listOfUncertParameters)}).
   */
  public UncertParameter removeUncertParameter(int i) {
    if (!isSetListOfUncertParameters()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfUncertParameters().remove(i);
  }

  /**
   * Creates a new UncertParameter element and adds it to the
   * {@link #listOfUncertParameters} list.
   *
   * @return the newly created element, i.e., the last item in the
   *         {@link #listOfUncertParameters}
   */
  public UncertParameter createUncertParameter() {
    return createUncertParameter(null);
  }

  /**
   * Creates a new {@link UncertParameter} element and adds it to the
   * {@link #listOfUncertParameters} list.
   *
   * @param id the identifier that is to be applied to the new element.
   * @return the newly created {@link UncertParameter} element, which is the last
   *         element in the {@link #listOfUncertParameters}.
   */
  public UncertParameter createUncertParameter(String id) {
    UncertParameter uncertParameter = new UncertParameter(id);
    addUncertParameter(uncertParameter);
    return uncertParameter;
  }

  /**
   * Gets an element from the {@link #listOfUncertParameters} at the given index.
   *
   * @param i the index of the {@link UncertParameter} element to get.
   * @return an element from the listOfUncertParameters at the given index.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound {@code (index < 0 || index > list.size)}.
   */
  public UncertParameter getUncertParameter(int i) {
    if (!isSetListOfUncertParameters()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfUncertParameters().get(i);
  }

  /**
   * Gets an element from the listOfUncertParameters, with the given id.
   *
   * @param uncertParameterId the id of the {@link UncertParameter} element to get.
   * @return an element from the listOfUncertParameters with the given id
   *         or {@code null}.
   */
  public UncertParameter getUncertParameter(String uncertParameterId) {
    if (isSetListOfUncertParameters()) {
      return getListOfUncertParameters().get(uncertParameterId);
    }
    return null;
  }

  /**
   * Returns the number of {@link UncertParameter}s in this
   * {@link UncertParameter}.
   * 
   * @return the number of {@link UncertParameter}s in this
   *         {@link UncertParameter}.
   */
  public int getUncertParameterCount() {
    return isSetListOfUncertParameters() ? getListOfUncertParameters().size() : 0;
  }

  /**
   * Returns the number of {@link UncertParameter}s in this
   * {@link UncertParameter}.
   * 
   * @return the number of {@link UncertParameter}s in this
   *         {@link UncertParameter}.
   * @libsbml.deprecated same as {@link #getUncertParameterCount()}
   */
  public int getNumUncertParameters() {
    return getUncertParameterCount();
  }

  
  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(MessageFormat.format(
        resourceBundle.getString("IndexSurpassesBoundsException"), index, 0));
    }
    int count = super.getChildCount(), pos = 0;

    if (index < count) {
      return super.getChildAt(index);
    } else {
      index -= count;
    }
    
    if (isSetMath()) {
      if (index == pos) {
        return getMath();
      }
      pos++;
    }
    if (isSetListOfUncertParameters()) {
      if (index == pos) {
        return getListOfUncertParameters();
      }
      pos++;
    }
    
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"),
      index, Math.min(pos, 0)));
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = super.getChildCount();

    if (isSetMath()) {
      count++;
    }
    if (isSetListOfUncertParameters()) {
      count++;
    }
    
    return count;
  }

  
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetValue()) {
      attributes.put(DistribConstants.shortLabel + ":" + DistribConstants.value, value.toString());
    }
    if (isSetVar()) {
      attributes.put(DistribConstants.shortLabel + ":" + DistribConstants.var, getVar());
    }
    if (isSetUnits()) {
      attributes.put(DistribConstants.shortLabel + ":" + DistribConstants.units, getUnits());
    }
    if (isSetDefinitionURL()) {
      attributes.put(DistribConstants.shortLabel + ":" + DistribConstants.definitionURL, getDefinitionURL());
    }
    if (isSetType()) {
      attributes.put(DistribConstants.shortLabel + ":" + DistribConstants.type, getType().toString());
    }
    
    return attributes;
  }

  @Override
  public boolean readAttribute(String attributeName, String prefix,
      String value) {

    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    
    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(DistribConstants.value)) {
        setValue(StringTools.parseSBMLDouble(value));
      }
      else if (attributeName.equals(DistribConstants.var)) {
        setVar(value);
      }
      else if (attributeName.equals(DistribConstants.units)) {
        setUnits(value);
      }
      else if (attributeName.equals(DistribConstants.definitionURL)) {
        setDefinitionURL(value);
      }
      else if (attributeName.equals(DistribConstants.type)) {
        setType(Type.valueOf(value));
        
        // TODO - add a try/catch block
      }
      else {
        isAttributeRead = false;
      }
    }

    return isAttributeRead;
  }
}
