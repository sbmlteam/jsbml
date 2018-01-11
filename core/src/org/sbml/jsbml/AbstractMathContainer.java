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
package org.sbml.jsbml;

import java.text.MessageFormat;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.text.parser.ParseException;
import org.sbml.jsbml.util.TreeNodeChangeEvent;
import org.sbml.jsbml.util.converters.ExpandFunctionDefinitionConverter;

/**
 * Base class for all the SBML components which contain MathML nodes.
 * 
 * @author Andreas Dr&auml;ger
 * @author marine
 * @since 0.8
 */
public abstract class AbstractMathContainer extends AbstractSBase implements
MathContainer {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -6630349025482311163L;
  /**
   * A logger for user-messages.
   */
  private static final transient Logger logger = Logger.getLogger(AbstractMathContainer.class);

  /**
   * The math formula as an abstract syntax tree.
   */
  private ASTNode math;

  /**
   * Creates a MathContainer instance. By default, the math object is {@code null}.
   */
  public AbstractMathContainer() {
    super();
    math = null;
  }

  /**
   * Creates a MathContainer instance from a given MathContainer.
   * 
   * @param sb an {@code AbstractMathContainer} object to clone
   */
  public AbstractMathContainer(AbstractMathContainer sb) {
    super(sb);
    if (sb.isSetMath()) {
      setMath(sb.getMath().clone());
    } else {
      math = null;
    }
  }

  /**
   * Creates a MathContainer instance from an ASTNode, level and version. By
   * default, the math is {@code null}.
   * 
   * @param math the ASTNode representing the math.
   * @param level the SBML level
   * @param version the SBML version
   */
  public AbstractMathContainer(ASTNode math, int level, int version) {
    super(level, version);
    if (math != null) {
      setMath(math.clone());
    } else {
      this.math = null;
    }
  }

  /**
   * Creates a MathContainer instance from a level and version. By default,
   * the formula, math and mathBuffer are {@code null}.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public AbstractMathContainer(int level, int version) {
    super(level, version);
    math = null;
  }

  /**
   * Creates a new {@link MathContainer} instance.
   * 
   * @param id the id
   * @param level the SBML level
   * @param version the SBML version
   */
  public AbstractMathContainer(String id, int level, int version) {
    super(id, level, version);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public abstract AbstractMathContainer clone();

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithDerivedUnit#containsUndeclaredUnits()
   */
  @Override
  public boolean containsUndeclaredUnits() {
    return isSetMath() ? math.containsUndeclaredUnits() : false;
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
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"),
      index, Math.min(pos, 0)));
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildCount()
   */
  @Override
  public int getChildCount() {
    return super.getChildCount() + (isSetMath() ? 1 : 0);
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
   * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName, String prefix, String value)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix,
      value);

    if (!isAttributeRead) {
      if (attributeName.equals("formula")) {
        try {
          setFormula(value);
          return true;
        } catch (ParseException exc) {
          throw new IllegalArgumentException(exc);
        }
      }
    }
    return isAttributeRead;
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetMath() && isSetLevel() && (getLevel() < 2)) {
      attributes.put("formula", getFormula());
    }
    return attributes;
  }

}
