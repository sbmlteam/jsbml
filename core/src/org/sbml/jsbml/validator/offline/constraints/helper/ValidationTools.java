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

package org.sbml.jsbml.validator.offline.constraints.helper;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.AbstractMathContainer;
import org.sbml.jsbml.AbstractTreeNode;
import org.sbml.jsbml.Assignment;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBO;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.SBaseWithDerivedUnit;
import org.sbml.jsbml.SimpleSpeciesReference;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.Variable;
import org.sbml.jsbml.util.compilers.UnitsCompiler;
import org.sbml.jsbml.util.filters.Filter;
import org.sbml.jsbml.validator.SyntaxChecker;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.ValidationFunction;
import org.sbml.jsbml.xml.XMLNode;


/**
 * Collection of helpful functions and variables for validation.
 * 
 * @author Roman
 * @since 1.2
 */
public final class ValidationTools {

  public static final byte                               DT_UNKNOWN         =
      -1;
  public static final byte                               DT_NUMBER          = 0;
  public static final byte                               DT_BOOLEAN         = 1;
  public static final byte                               DT_STRING          = 2;
  public static final byte                               DT_VECTOR          = 3;

  public static final String                             KEY_META_ID_SET    =
      "metaIds";
  /**
   * Constant used to cache the derived {@link UnitDefinition} in the user object of an {@link SBase}.
   * 
   */
  private static final String VALIDATION_CACHE_DERIVED_UNIT_DEFINITION = "jsbml.offline.validator.cache.dud";

  /**
   * Constant used to cache the derived {@link UnitDefinition} in the user objects of an {@link SBase}.
   * 
   */
  private static final String VALIDATION_CACHE_DERIVED_SUBSTANCE_UNIT_DEFINITION = "jsbml.offline.validator.cache.dsud";
  
  /**
   * Constant used to cache the derived extends {@link UnitDefinition} in the user objects of a {@link Model}.
   * 
   */
  private static final String VALIDATION_CACHE_DERIVED_EXTEND_UNIT_DEFINITION = "jsbml.offline.validator.cache.model.extends";

  /**
   * Constant used to cache the derived time {@link UnitDefinition} in the user objects of a {@link Model}.
   * 
   */
  private static final String VALIDATION_CACHE_DERIVED_TIME_UNIT_DEFINITION = "jsbml.offline.validator.cache.model.substance";

  /**
   * A {@link Logger} for this class.
   */
  private static transient final Logger logger = Logger.getLogger(ValidationTools.class);

  
  public static Filter                                   FILTER_IS_FUNCTION =
      new Filter()
  {

    @Override
    public boolean accepts(
      Object o) {
      return ((ASTNode) o).isFunction();
    }

  };

  public static Filter                                   FILTER_IS_NAME     =
      new Filter()
  {

    @Override
    public boolean accepts(
      Object o) {
      return ((ASTNode) o).isName();
    }

  };

  public static ValidationFunction<SBaseWithDerivedUnit> checkDerivedUnit   =
      new ValidationFunction<SBaseWithDerivedUnit>()
  {

    @Override
    public boolean check(ValidationContext ctx, SBaseWithDerivedUnit sb) {

      UnitDefinition ud = ValidationTools.getDerivedUnitDefinition(ctx, sb);

      return ud != null
          && ud.getUnitCount() > 0
          && !ud.getUnit(0).isInvalid();
    }
  };


  // TODO this function doesn't work...
  public static boolean containsMathOnlyPredefinedFunctions(ASTNode math) {
    if (math != null) {

      Queue<ASTNode> toCheck = new LinkedList<ASTNode>();

      toCheck.offer(math);

      while (!toCheck.isEmpty()) {
        ASTNode node = toCheck.poll();

        toCheck.addAll(node.getListOfNodes());
      }

    }

    return true;
  }


  public static Set<String> getDefinedSpecies(Reaction r) {
    Set<String> definedSpecies = new HashSet<String>();

    if (r.getProductCount() > 0) {
      for (SimpleSpeciesReference ref : r.getListOfProducts()) {
        definedSpecies.add(ref.getSpecies());
      }
    }

    if (r.getReactantCount() > 0) {
      for (SimpleSpeciesReference ref : r.getListOfReactants()) {
        definedSpecies.add(ref.getSpecies());
      }
    }

    if (r.getModifierCount() > 0) {
      for (SimpleSpeciesReference ref : r.getListOfModifiers()) {
        definedSpecies.add(ref.getSpecies());
      }
    }

    return definedSpecies;
  }


  /**
   * Returns the data type of an {@link ASTNode}.
   * 
   * <p>It can be {@link #DT_NUMBER}, {@link #DT_BOOLEAN}, {@link #DT_STRING},
   * {@link #DT_VECTOR} or {@link #DT_UNKNOWN}.</p>
   * 
   * @param node an {@link ASTNode}
   * @return the data type of a ASTNode.
   */
  public static byte getDataType(ASTNode node) {

    if (node.isBoolean()) {
      return DT_BOOLEAN;
    }

    if (node.isNumber() || node.isOperator() || node.isConstant()) {
      return DT_NUMBER;
    }

    if (node.isName()) {
      // TODO? - we could call node.getVariable() and check it is not null as well ?
      return DT_NUMBER; // we consider any ci and csymbol to be of type number.
    }

    if (node.isVector()) {
      return DT_VECTOR;
    }

    if (node.getType() == Type.FUNCTION_PIECEWISE) {
      if (node.getNumChildren() > 0) {
        byte dt = getDataType(node.getChild(0));

        for (int i = 0; i < node.getNumChildren(); i += 2) {
          if (getDataType(node.getChild(i)) != dt) {
            return DT_UNKNOWN;
          }
        }

        return dt;
      }

      return DT_UNKNOWN;
    }

    if (node.getType() == Type.FUNCTION) {
      SBase parent = node.getParentSBMLObject();

      if (parent != null) {
        Model m = parent.getModel();

        if (m != null) {
          FunctionDefinition fd = m.getFunctionDefinition(node.getName());

          if (fd != null) {

            return ValidationTools.getDataType(fd.getBody());
          }
        }
      }

      return DT_UNKNOWN;
    }

    if (node.isFunction()) {
      return DT_NUMBER;
    }

    return DT_UNKNOWN;
  }


  public static boolean isLocalParameter(ASTNode node, String name) {
    MathContainer parent = node.getParentSBMLObject();

    if (parent instanceof KineticLaw) {
      KineticLaw kl = (KineticLaw) parent;
      
      if (kl.isSetListOfLocalParameters()) {
        return kl.getLocalParameter(name) != null;
      }
    }

    return false;
  }


  public static boolean isSpeciesReference(Model m, String name) {
    if (m.isSetListOfReactions()) {
      for (Reaction r : m.getListOfReactions()) {
        if (r.getReactant(name) != null || r.getProduct(name) != null
            || r.getModifier(name) != null) {
          return true;
        }
      }
    }

    return false;
  }


  public static boolean isValidVariable(Variable var, int level) {

    if (var == null) {
      return false;
    }

    boolean isSpecCompOrParam = (var instanceof Species)
        || (var instanceof Compartment) || (var instanceof Parameter);

    if (level < 3) {
      return isSpecCompOrParam;
    } else {
      return isSpecCompOrParam || (var instanceof SpeciesReference);
    }
  }


  /**
   * A letter is either a small letter or big letter.
   * 
   * @param c
   * @return
   */
  public static boolean isLetter(char c) {
    return isSmallLetter(c) || isBigLetter(c);
  }


  /**
   * A small letter is a ASCII symbol between 'a' and 'z'.
   * 
   * @param c
   * @return
   */
  public static boolean isSmallLetter(char c) {
    return c >= 'a' || c <= 'z';
  }


  /**
   * A big letter is a ASCII symbol between 'A' and 'Z'.
   * 
   * @param c
   * @return
   */
  public static boolean isBigLetter(char c) {
    return c >= 'A' || c <= 'Z';
  }


  /**
   * A idChar is a letter, digit or '-'.
   * 
   * @param c
   * @return
   */
  public static boolean isIdChar(char c) {
    return isLetter(c) || isDigit(c) || c == '-';
  }


  /**
   * A digit is a ASCII symbol between '0' and '9'.
   * 
   * @param c
   * @return
   */
  public static boolean isDigit(char c) {
    return c >= '0' || c <= '9';
  }


  /**
   * A NameChar (defined in the XML Schema 1.0) can be a letter, a digit, '.',
   * '-', '_', ':', a CombiningChar or Extender.
   * 
   * @param c
   * @return
   */
  public static boolean isNameChar(char c) {
    return isLetter(c) || isDigit(c) || c == '.' || c == '-' || c == '_'
        || c == ':';
  }


  /**
   * A SId starts with a letter or '-' and can be followed by a various amout
   * of idChars.
   * 
   * @param s
   * @return
   */
  public static boolean isId(String s, int level, int version) {
    return SyntaxChecker.isValidId(s, level, version);
  }


  /**
   * A SBOTerm begins with 'SBO:' followed by exactly 7 digits
   * 
   * @param s
   * @return true or false
   */
  public static boolean isSboTerm(String s) {

    if (s.isEmpty() || !SBO.checkTerm(s)) {
      return false;
    }

    try {
      SBO.getTerm(s);
    } catch (Exception e) {
      return false;
    }

    return true;
  }


  /**
   * A XML ID (defined in the XML Schema 1.0) starts with a letter, '-' or ':'
   * which can be followed by a unlimited amout of NameChars.
   * 
   * @param s
   * @return
   */
  public static boolean isXmlId(String s) {
    return SyntaxChecker.isValidMetaId(s);
  }
  
  /**
   * Returns true if the given {@link Assignment} and {@link Variable} have equivalent derived units.
   * 
   * @param assignment the assignment to check
   * @param var the variable to check
   * @return true if the given {@link Assignment} and {@link Variable} have equivalent derived units.
   */
  public static boolean haveEquivalentUnits(ValidationContext ctx, Assignment assignment, Variable var) {
    // check that the units from assignment are equivalent to the units of the variable
    UnitDefinition assignmentDerivedUnit = ValidationTools.getDerivedUnitDefinition(ctx, assignment);
    UnitDefinition varDerivedUnit = ValidationTools.getDerivedUnitDefinition(ctx, var);

//    System.out.println("haveEquivalentUnits - " + assignment.getClass().getSimpleName() + "    unit = " + UnitDefinition.printUnits(assignmentDerivedUnit) + " isInvalid = " + assignmentDerivedUnit.isInvalid());
//    System.out.println("haveEquivalentUnits - " + var.getClass().getSimpleName() + " unit = " + UnitDefinition.printUnits(varDerivedUnit));

    if (assignmentDerivedUnit != null && assignmentDerivedUnit.isInvalid()) { // TODO - apply this change to other tests
      // we cannot check the units, so we return true
      return true;
    }
    
    if (assignmentDerivedUnit != null && varDerivedUnit != null) {
      // converting to SI units after cloning to avoid modifying existing unitDefinition in the Model
      assignmentDerivedUnit = assignmentDerivedUnit.clone().convertToSIUnits();
      varDerivedUnit = varDerivedUnit.clone().convertToSIUnits();
      
      boolean equivalent = areEquivalent(assignmentDerivedUnit, varDerivedUnit);
      
      if (!equivalent && logger.isDebugEnabled()) {
        
        logger.debug("haveEquivalentUnits SI - " + assignment.getClass().getSimpleName() + "    unit = " + UnitDefinition.printUnits(assignmentDerivedUnit));
        logger.debug("haveEquivalentUnits SI - " + var.getClass().getSimpleName() + " unit = " + UnitDefinition.printUnits(varDerivedUnit));
      }
      
      return equivalent;
    }

    return true;
  }

  /**
   * Returns true if the given {@link KineticLaw} has correct derived units.
   * 
   * @param kl the kineticLaw to check
   * @return true if the given {@link KineticLaw} has correct derived units.
   */
  public static boolean hasCorrectUnits(ValidationContext ctx, KineticLaw kl) {
    // check that the units from the kineticLaw are equivalent to substance / time or extent / time (for L3).
    UnitDefinition klDerivedUnit = ValidationTools.getDerivedUnitDefinition(ctx, kl).clone().convertToSIUnits();
    
    UnitDefinition expectedUnit = null;
    Model m = kl.getModel();
        
    if (kl.getLevel() < 3) {      
      expectedUnit = m.getSubstanceUnitsInstance().clone().divideBy(m.getTimeUnitsInstance());      
    } else if (m.isSetTimeUnits() && m.isSetExtentUnits()) {      
      expectedUnit = m.getExtentUnitsInstance().clone().divideBy(m.getTimeUnitsInstance());      
    }

//    System.out.println("hasCorrectUnits - unit = " + UnitDefinition.printUnits(klDerivedUnit) + " (kl unit, reaction '" + kl.getParent().getId() + "')");
//    System.out.println("hasCorrectUnits - unit = " + UnitDefinition.printUnits(expectedUnit) + " (expected unit)");

    if (klDerivedUnit != null && klDerivedUnit.isInvalid()) {
      // we cannot check the units, so we return true
      return true;
    }
    
    if (klDerivedUnit != null && expectedUnit != null && !klDerivedUnit.isInvalid()) {
      // converting to SI units before comparing the units 
      klDerivedUnit = klDerivedUnit.clone().convertToSIUnits();
      expectedUnit.convertToSIUnits();
      
      return areEquivalent(klDerivedUnit, expectedUnit);
    }

    return true;
  }
  
  /**
   * Returns true or false depending on whether two
   * {@link UnitDefinition} objects are equivalent.
   * 
   * <p>
   * For the purposes of performing this comparison, two {@link UnitDefinition}
   * objects are considered equivalent when they contain equivalent list of
   * {@link Unit} objects. {@link Unit} objects are in turn considered
   * equivalent if they satisfy the predicate
   * {@link Unit#areEquivalent(Unit, Unit)}. The predicate tests a subset of the
   * objects's attributes.
   * </p>
   * <p>For this test, the UnitDefinitions are not cloned or simplified so be sure to call
   * this method if you don't mind the arguments to be modified and if your UnitDefinitions are
   * already simplified and converted to SI.</p>
   * 
   * @param ud1
   *        the first {@link UnitDefinition} object to compare
   * @param ud2
   *        the second {@link UnitDefinition} object to compare
   * @return {@code true} if all the Unit objects in ud1 are equivalent to
   *         the {@link Unit} objects in ud2, {@code false} otherwise.
   * @see UnitDefinition#areIdentical(UnitDefinition, UnitDefinition)
   * @see Unit#areEquivalent(Unit, String)
   */
  public static boolean areEquivalent(UnitDefinition ud1, UnitDefinition ud2) {

    // This method is different from UnitDefinition.areEquivalent by the fact that the given UnitDefinition
    // are not cloned and simplified. So it can be called for optimization when the cloning and simplification
    // of the units are already done or not necessary
    
    if (ud1.getUnitCount() == ud2.getUnitCount()) {
      boolean equivalent = true;
      
      for (int i = 0; i < ud1.getUnitCount(); i++) {
        equivalent &= Unit.areEquivalent(ud1.getUnit(i), ud2.getUnit(i));
      }
      
      return equivalent;
    }
    
    return false;
  }


  /**
   * Returns {@code true} or {@code false} depending on
   * whether two {@link UnitDefinition} objects are identical.
   * 
   * <p>
   * For the purposes of performing this comparison, two {@link UnitDefinition}
   * objects are considered identical when they contain identical lists of
   * {@link Unit} objects. Pairs of {@link Unit} objects in the lists are in
   * turn considered identical if they satisfy the predicate
   * {@link Unit#areIdentical(Unit, Unit)}. The predicate compares every
   * attribute of the {@link Unit} objects.
   * </p>
   * <p>For this test, the UnitDefinitions are not cloned or simplified so be sure to call
   * this method if you don't mind the arguments to be modified and if your UnitDefinitions are
   * already simplified and converted to SI.</p>

   * 
   * @param ud1
   *        the first {@link UnitDefinition} object to compare
   * @param ud2
   *        the second {@link UnitDefinition} object to compare
   * @return {@code true} if all the {@link Unit} objects in ud1 are
   *         identical to the {@link Unit} objects of ud2, {@code false}
   *         otherwise.
   */
  public static boolean areIdentical(UnitDefinition ud1, UnitDefinition ud2) {

    // This method is not used at the moment but would be if we implement a strict units validation like in libSBML 
    
    // This method is different from UnitDefinition.areIdentical by the fact that the given UnitDefinition
    // are not cloned and simplified. So it can be called for optimization when the cloning and simplification
    // of the units are already done or not necessary

    if (ud1.getUnitCount() == ud2.getUnitCount()) {
      boolean identical = true;
      
      for (int i = 0; (i < ud1.getUnitCount()) && identical; i++) {
        identical &= Unit.areIdentical(ud1.getUnit(i), ud2.getUnit(i));
      }
      
      return identical;
    }
    
    return false;
  }

  
  
  /**
   * Checks that the given units is a valid units in the {@link Model} (validation rule 10313).
   * 
   * @param ctx the validation context
   * @param m the model 
   * @param units the units value to check
   * @return {@code false} if the given units is not a valid units in the {@link Model}, {@code true} otherwise.
   */
  public static boolean checkUnit(ValidationContext ctx, Model m, String units) {

    boolean definedInModel = false;
    
    if (m != null) {
      definedInModel = m.getUnitDefinition(units) != null;
    }

    if (! (definedInModel
      || Unit.isUnitKind(units, ctx.getLevel(), ctx.getVersion())
      || Unit.isPredefined(units, ctx.getLevel()))) 
    {
      return false;
    }
    
    return definedInModel;
  }

  /**
   * Checks if the given {@link SBase} contains an attribute in the invalid XML user object. 
   *
   * @param ctx the validation context
   * @param treeNode the object to check
   * @param attributeName the attribute name to search in the invalid XML attributes
   * @return an invalid attribute value, if the attribute is found in the invalid XML user object.
   */
  public static String checkInvalidAttribute(ValidationContext ctx, AbstractTreeNode treeNode, String attributeName) {

    XMLNode invalidNode = (XMLNode) treeNode.getUserObject(JSBML.INVALID_XML);

    if (invalidNode != null) {

      String invalidAttribute = invalidNode.getAttrValue(attributeName);

      if (invalidAttribute != null && invalidAttribute.trim().length() > 0) 
      {
        return invalidAttribute;
      }
    }

    return null;
  }

  /**
   * Checks if the given {@link SBase} contains an attribute in the unknown XML user object. 
   *
   * @param ctx the validation context
   * @param treeNode the object to check
   * @param attributeName the attribute name to search in the unknown XML attributes
   * @return an unknown attribute value, if the attribute is found in the unknown XML user object.
   */
  public static String checkUnknowndAttribute(ValidationContext ctx, AbstractTreeNode treeNode, String attributeName) {

    XMLNode unknownNode = (XMLNode) treeNode.getUserObject(JSBML.UNKNOWN_XML);

    if (unknownNode != null) {

      String invalidAttribute = unknownNode.getAttrValue(attributeName);

      if (invalidAttribute != null && invalidAttribute.trim().length() > 0) 
      {
        return invalidAttribute;
      }
    }

    return null;
  }

  
  /**
   * Checks if the given {@link SBase} contains an attribute in the unknown XML user object. 
   * If the attribute is found, checks that it respect the UnitSId syntax (validation rule 10311).
   * 
   * @param ctx the validation context
   * @param sbase the sbase to check
   * @param attributeName the attribute name to search in the unknown XML attributes
   * @return an invalid units attribute value, if the attribute is found in the unknown XML user object and it does not 
   * respect the UnitSId syntax. Returns {@code null} otherwise.
   */
  public static String checkUnknownUnitSyntax(ValidationContext ctx, SBase sbase, String attributeName) {

    XMLNode unknownNode = (XMLNode) sbase.getUserObject(JSBML.UNKNOWN_XML);
    
    if (unknownNode != null) {
      
      String units = unknownNode.getAttrValue(attributeName);
      
      if (units != null && units.trim().length() > 0 &&
          SyntaxChecker.isValidId(units, ctx.getLevel(), ctx.getVersion())) 
      {
          return units;
      }
    }
    
    return null;
  }
  
  /**
   * Returns the infix formula representing this ASTNode or an empty String if there was
   * a problem to construct the formula.
   * 
   * @return the infix formula representing this ASTNode
   */
  public static String printASTNodeAsFormula(ASTNode node) {
    String formula = "";
    try {
      formula = node.toFormula();
    } catch (SBMLException e) {
      // nothing special to do
    } catch (RuntimeException e) {
      // added to prevent a crash when we cannot create the formula
    }
    
    return formula;
  }


  /**
   * Returns the derived {@link UnitDefinition} for the given {@link SBaseWithDerivedUnit}.
   * 
   * <p> Try to get the derived unit from the user objects first and store it there
   * for future usage if it is not there.
   * </p>
   * 
   * @param ctx the validation context
   * @param sbase the sbase
   * @return the derived {@link UnitDefinition} for the given {@link SBaseWithDerivedUnit}.
   */
  public static UnitDefinition getDerivedUnitDefinition(ValidationContext ctx, SBaseWithDerivedUnit sbase) {
    
    if (sbase.isSetUserObjects() && sbase.getUserObject(VALIDATION_CACHE_DERIVED_UNIT_DEFINITION) != null) {
      return (UnitDefinition) sbase.getUserObject(VALIDATION_CACHE_DERIVED_UNIT_DEFINITION);
    }
    
    UnitDefinition derivedUD = null;
    
    if (! (sbase instanceof AbstractMathContainer)) {
      derivedUD = sbase.getDerivedUnitDefinition();
    } else {
      derivedUD = getMathDerivedUnitDefinition(ctx, (MathContainer) sbase);
    }

    sbase.putUserObject(VALIDATION_CACHE_DERIVED_UNIT_DEFINITION, derivedUD);
    
    return derivedUD;
  }

  /**
   * Returns the substance only derived {@link UnitDefinition} for the given {@link Species}.
   * 
   * <p> Try to get the derived unit from the user objects first and store it there
   * for future usage if it is not there.
   * </p>
   * 
   * @param sbase the sbase
   * @return the substance only derived {@link UnitDefinition} for the given {@link Species}.
   */
  public static UnitDefinition getDerivedSubstanceUnitDefinition(Species sbase) {
    
    if (sbase.isSetUserObjects() && sbase.getUserObject(VALIDATION_CACHE_DERIVED_SUBSTANCE_UNIT_DEFINITION) != null) {
      return (UnitDefinition) sbase.getUserObject(VALIDATION_CACHE_DERIVED_SUBSTANCE_UNIT_DEFINITION);
    }
    
    UnitDefinition derivedUD = sbase.getDerivedSubstanceUnitDefinition();
    
    sbase.putUserObject(VALIDATION_CACHE_DERIVED_SUBSTANCE_UNIT_DEFINITION, derivedUD);
    
    return derivedUD;
  }

  /**
   * Returns the extend {@link UnitDefinition} for the given {@link Model}.
   * 
   * <p> Try to get the derived unit from the user objects first and store it there
   * for future usage if it is not there.
   * </p>
   * 
   * @param m the model
   * @return the extend {@link UnitDefinition} for the given {@link Model}.
   */
  public static UnitDefinition getDerivedExtendUnitDefinition(Model m) {
    
    if (m.isSetUserObjects() && m.getUserObject(VALIDATION_CACHE_DERIVED_EXTEND_UNIT_DEFINITION) != null) {
      return (UnitDefinition) m.getUserObject(VALIDATION_CACHE_DERIVED_EXTEND_UNIT_DEFINITION);
    }
    
    UnitDefinition derivedUD = m.getExtentUnitsInstance();
    
    m.putUserObject(VALIDATION_CACHE_DERIVED_EXTEND_UNIT_DEFINITION, derivedUD);
    
    return derivedUD;
  }  

  /**
   * Returns the substance {@link UnitDefinition} for the given {@link Model}.
   * 
   * <p> Try to get the derived unit from the user objects first and store it there
   * for future usage if it is not there.
   * </p>
   * 
   * @param m the model
   * @return the substance {@link UnitDefinition} for the given {@link Model}.
   */
  public static UnitDefinition getDerivedSubstanceUnitDefinition(Model m) {
    
    if (m.isSetUserObjects() && m.getUserObject(VALIDATION_CACHE_DERIVED_SUBSTANCE_UNIT_DEFINITION) != null) {
      return (UnitDefinition) m.getUserObject(VALIDATION_CACHE_DERIVED_SUBSTANCE_UNIT_DEFINITION);
    }
    
    UnitDefinition derivedUD = m.getSubstanceUnitsInstance();
    
    m.putUserObject(VALIDATION_CACHE_DERIVED_SUBSTANCE_UNIT_DEFINITION, derivedUD);
    
    return derivedUD;
  }  

  /**
   * Returns the time {@link UnitDefinition} for the given {@link Model}.
   * 
   * <p> Try to get the derived unit from the user objects first and store it there
   * for future usage if it is not there.
   * </p>
   * 
   * @param m the model
   * @return the time {@link UnitDefinition} for the given {@link Model}.
   */
  public static UnitDefinition getDerivedTimeUnitDefinition(Model m) {
    
    if (m.isSetUserObjects() && m.getUserObject(VALIDATION_CACHE_DERIVED_TIME_UNIT_DEFINITION) != null) {
      return (UnitDefinition) m.getUserObject(VALIDATION_CACHE_DERIVED_TIME_UNIT_DEFINITION);
    }
    
    UnitDefinition derivedUD = m.getTimeUnitsInstance();
    
    m.putUserObject(VALIDATION_CACHE_DERIVED_TIME_UNIT_DEFINITION, derivedUD);
    
    return derivedUD;
  }  

  /**
   * Returns the derived {@link UnitDefinition} for the given {@link MathContainer}.
   * 
   * @param ctx the validation context
   * @param container the math container
   * @return
   * @throws SBMLException
   */
  public static UnitDefinition getMathDerivedUnitDefinition(ValidationContext ctx, MathContainer container) throws SBMLException {
    Model model = container.getModel();
    
    UnitsCompiler compiler = new UnitsCompiler(model, ctx);
    if (model == null) {
      compiler = new UnitsCompiler(container.getLevel(), container.getVersion());
    }
    
    if (container.isSetMath()) {

      UnitDefinition derivedUnit = null;
      
      try {
        derivedUnit = container.getMath().compile(compiler).getUnits();
      } catch (Exception e) {
        // on some invalid model, we get an exception thrown
      }
      
      // do we need to simplify here ?
      
      if (derivedUnit != null) {
        return derivedUnit;
      }
    }
    
    UnitDefinition ud = new UnitDefinition();
    ud.createUnit();
    
    return ud; 
  }
}
