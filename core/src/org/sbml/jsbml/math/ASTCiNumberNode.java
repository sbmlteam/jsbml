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
 * 6. The University of Toronto, Toronto, ON, Canada
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.math;

import java.text.MessageFormat;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.CallableSBase;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.QuantityWithUnit;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.math.compiler.ASTNode2Compiler;
import org.sbml.jsbml.math.compiler.ASTNode2Value;
import org.sbml.jsbml.math.compiler.FormulaCompiler;
import org.sbml.jsbml.math.compiler.LaTeXCompiler;
import org.sbml.jsbml.math.compiler.MathMLXMLStreamCompiler;
import org.sbml.jsbml.util.TreeNodeChangeEvent;


/**
 * An Abstract Syntax Tree (AST) node representing a MathML ci element
 * in a mathematical expression.
 *
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTCiNumberNode extends ASTNumber implements
ASTCSymbolBaseNode {

  /**
   *
   */
  private static final long serialVersionUID = -6842905005458975038L;

  /**
   * A {@link Logger} for this class.
   */
  static final Logger logger = Logger.getLogger(ASTCiNumberNode.class);

  /**
   * definitionURL attribute for MathML element
   */
  protected String definitionURL;

  /**
   * The name of the MathML element represented by this
   * {@link ASTCiNumberNode}.
   */
  private String refId;

  /**
   * Creates a new {@link ASTCiNumberNode}.
   */
  public ASTCiNumberNode() {
    super();
    setDefinitionURL(null);
    setRefId(null);
    setType(Type.NAME);
  }

  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTCiNumberNode}.
   *
   * @param node
   *            the {@link ASTCiNumberNode} to be copied.
   */
  public ASTCiNumberNode(ASTCiNumberNode node) {
    super(node);
    if (node.isSetDefinitionURL()) {
      setDefinitionURL(node.getDefinitionURL());
    }
    if (node.isSetRefId()) {
      setRefId(node.getRefId());
    }
  }

  /*(non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNumber#clone()
   */
  @Override
  public ASTCiNumberNode clone() {
    return new ASTCiNumberNode(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#compile(org.sbml.jsbml.util.compilers.ASTNode2Compiler)
   */
  @Override
  public ASTNode2Value<?> compile(ASTNode2Compiler compiler) {
    ASTNode2Value<?> value = isSetRefId() ? compiler.compile(getRefId()) : null;
    return processValue(value);
  }

  /**
   * Returns {@code true} or {@code false} depending on whether this
   * {@link ASTCiNumberNode} refers to elements such as parameters or
   * numbers with undeclared units.
   *
   * A return value of {@code true} indicates that the {@code UnitDefinition}
   * returned by {@link Variable#getDerivedUnitDefinition()} may not accurately
   * represent the units of the expression.
   *
   * @return {@code true} if the math expression of this {@link ASTCiNumberNode}
   *         includes parameters/numbers with undeclared units,
   *         {@code false} otherwise.
   */
  public boolean containsUndeclaredUnits() {
    if (isSetRefId()) {
      CallableSBase reference = getReferenceInstance();
      if (reference != null && reference instanceof QuantityWithUnit) {
        return ! ((QuantityWithUnit)reference).isSetUnits();
      } else {
        // TODO: reaction doesn't have units but may still have undeclared units
        // in kinetic law.
        // arrays or other packages
        logger.warn("??");
        return true;
      }
    }
    return false;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (!super.equals(obj)) {
      return false;
    }
    ASTCiNumberNode other = (ASTCiNumberNode) obj;
    if (definitionURL == null) {
      if (other.definitionURL != null) {
        return false;
      }
    } else if (!definitionURL.equals(other.definitionURL)) {
      return false;
    }
    if (refId == null) {
      if (other.refId != null) {
        return false;
      }
    } else if (!refId.equals(other.refId)) {
      return false;
    }
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTCSymbolBaseNode#getDefinitionURL()
   */
  @Override
  public String getDefinitionURL() {
    if (isSetDefinitionURL()) {
      return definitionURL;
    }
    PropertyUndefinedError error = new PropertyUndefinedError("definitionURL", this);
    if (isStrict()) {
      throw error;
    }
    logger.warn(error);
    return "";
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTCSymbolBaseNode#getName()
   */
  @Override
  @Deprecated
  public String getName() {
    return getRefId();
  }

  /**
   * Returns the variable ({@link CallableSBase}) of this {@link ASTCiNumberNode}.
   *
   * @return the variable of this node
   */
  public CallableSBase getReferenceInstance() {
    CallableSBase sbase = null;
    if (isSetParentSBMLObject()) {
      if (getParentSBMLObject() instanceof KineticLaw) {
        sbase = isSetRefId() ? ((KineticLaw) getParentSBMLObject())
          .getLocalParameter(getRefId()): null;
      }
      if (sbase == null) {
        Model m = isSetParentSBMLObject() ? getParentSBMLObject().getModel() : null;
        if (m != null) {
          sbase = m.findCallableSBase(getRefId());
          if (sbase instanceof LocalParameter) {
            sbase = null;
          } else if (sbase == null) {
            logger.debug(MessageFormat.format(
              "Cannot find any element with id \"{0}\" in the model.",
              getRefId()));
          }
        } else {
          logger.debug(MessageFormat.format(
            "This ASTCiNumberNode is not yet linked to a model. "
                + "No element with id \"{0}\" could be found.", getRefId()));
        }
      }
    }
    return sbase;
  }

  /**
   * Get refId attribute
   *
   * @return the refId
   */
  public String getRefId() {
    if (isSetRefId()) {
      return refId;
    }
    PropertyUndefinedError error = new PropertyUndefinedError("refId", this);
    if (isStrict()) {
      throw error;
    }
    logger.warn(error);
    return "";
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 1489;
    int result = super.hashCode();
    result = prime * result
        + ((definitionURL == null) ? 0 : definitionURL.hashCode());
    result = prime * result + ((refId == null) ? 0 : refId.hashCode());
    return result;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#isAllowableType(org.sbml.jsbml.ASTNode.Type)
   */
  @Override
  public boolean isAllowableType(Type type) {
    return type == Type.NAME;
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTCSymbolBaseNode#isSetDefinitionURL()
   */
  @Override
  public boolean isSetDefinitionURL() {
    return definitionURL != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTCSymbolBaseNode#isSetName()
   */
  @Override
  @Deprecated
  public boolean isSetName() {
    return false;
  }

  /**
   * Return true iff refId is set
   *
   * @return boolean
   */
  public boolean isSetRefId() {
    return refId != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.AbstractASTNode#isSetType()
   */
  @Override
  public boolean isSetType() {
    return type == Type.NAME;
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTCSymbolBaseNode#refersTo(java.lang.String)
   */
  @Override
  public boolean refersTo(String id) {
    return getRefId().equals(id);
  }

  /**
   * Set the definitionURL of the MathML element represented by
   * this {@link ASTCiNumberNode}
   *
   * @param definitionURL
   */
  public void setDefinitionURL(String definitionURL) {
    String old = this.definitionURL;
    this.definitionURL = definitionURL;
    firePropertyChange(TreeNodeChangeEvent.definitionURL, old, definitionURL);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTCSymbolBaseNode#setName(java.lang.String)
   */
  @Override
  @Deprecated
  public void setName(String name) {
    setRefId(name);
  }

  /**
   * Set an instance of {@link CallableSBase} as the variable of this
   * {@link ASTCiNumberNode}. Note that if the given variable does not
   * have a declared {@code id} field, the pointer to this variable will
   * get lost when cloning this node. Only references to identifiers are
   * permanently stored. The pointer can also not be written to an SBML
   * file without a valid identifier.
   *
   * @param sbase a pointer to a {@link CallableSBase}.
   */
  public void setReference(CallableSBase sbase) {
    setRefId(sbase.getId());
  }

  /**
   * Set refId attribute
   *
   * @param refId the refId to set
   */
  public void setRefId(String refId) {
    String old = this.refId;
    this.refId = refId;
    firePropertyChange(TreeNodeChangeEvent.refId, old, refId);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.AbstractASTNode#toFormula()
   */
  @Override
  public String toFormula() throws SBMLException {
    return compile(new FormulaCompiler()).toString();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.AbstractASTNode#toLaTeX()
   */
  @Override
  public String toLaTeX() throws SBMLException {
    return compile(new LaTeXCompiler()).toString();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.AbstractASTNode#toMathML()
   */
  @Override
  public String toMathML() {
    try {
      return MathMLXMLStreamCompiler.toMathML(this);
    } catch (RuntimeException e) {
      logger.error("Unable to create MathML");
      return null;
    }
  }

}
