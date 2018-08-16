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
package org.sbml.jsbml.validator.offline.constraints;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.AbstractTreeNode;
import org.sbml.jsbml.AlgebraicRule;
import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.util.compilers.ASTNodeValue;
import org.sbml.jsbml.util.compilers.UnitsCompiler;
import org.sbml.jsbml.util.filters.Filter;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.SyntaxChecker;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.ValidationTools;
import org.sbml.jsbml.xml.XMLNode;

/**
 * Validation constraints related to {@link ASTNode}.
 * 
 * @author Roman
 * @since 1.2
 */
public class ASTNodeConstraints extends AbstractConstraintDeclaration {

  /**
   * 
   */
  private static transient Logger logger = Logger.getLogger(ASTNodeConstraints.class);
  
  /**
   * 
   */
  public static final transient List<String> allowedCsymbolURI = Arrays.asList(ASTNode.URI_AVOGADRO_DEFINITION, ASTNode.URI_DELAY_DEFINITION, ASTNode.URI_RATE_OF_DEFINITION, ASTNode.URI_TIME_DEFINITION);


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#
   * addErrorCodesForCheck(java.util.Set, int, int,
   * org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY)
   */
  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {

    switch (category) {
    case GENERAL_CONSISTENCY:
      break;
    case IDENTIFIER_CONSISTENCY:
      
      if (level > 2) {
        set.add(CORE_10311);
      }
      
      break;
    case MATHML_CONSISTENCY:
      if (level > 1) {

        addRangeToSet(set, CORE_10201, CORE_10208);

        set.add(CORE_10214);
        set.add(CORE_10215);
        set.add(CORE_10216);
        set.add(CORE_10218);

        if (level == 3 || (level == 2 && version > 3)) {
          set.add(CORE_10219);
          set.add(CORE_10221);          
        }

        if (level == 3 && version == 2) {
          addRangeToSet(set, CORE_10223, CORE_10225);
        } else {
          addRangeToSet(set, CORE_10209, CORE_10213);
        }

        set.add(CORE_10222);
      }

      if (level == 3) {
        set.add(CORE_10220);
      }

      break;
    case MODELING_PRACTICE:
      break;
    case OVERDETERMINED_MODEL:
      break;
    case SBO_CONSISTENCY:
      break;
    case UNITS_CONSISTENCY:
      set.add(CORE_10501);
      break;
    }

  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#
   * addErrorCodesForAttribute(java.util.Set, int, int, java.lang.String)
   */
  @Override
  public void addErrorCodesForAttribute(Set<Integer> set, int level,
    int version, String attributeName, ValidationContext context) {
    // TODO Auto-generated method stub

  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#
   * getValidationFunction(int)
   */
  @Override

  public ValidationFunction<?> getValidationFunction(int errorCode, final ValidationContext context) {
    ValidationFunction<ASTNode> func = null;

    switch (errorCode) {
      
      case CORE_10201: {
        func = new ValidationFunction<ASTNode>() {

          @Override
          public boolean check(ValidationContext ctx, ASTNode node) {

            if (node.getUserObject(JSBML.UNKNOWN_XML) != null) {

              XMLNode unknownNode = (XMLNode) node.getUserObject(JSBML.UNKNOWN_XML);
              
              // TODO - check that the top level node(s) is/are part of the mathml possible elements
              // System.out.println("10201 - node unknown = " + node.getUserObject(JSBML.UNKNOWN_XML));
              
              if (unknownNode != null) {
                  return false;
              }
            }

            return true;
          }
        };
        break;
      }
      case CORE_10202: {
        func = new ValidationFunction<ASTNode>() {

          @Override
          public boolean check(ValidationContext ctx, ASTNode node) {

            // TODO - check allowed mathML elements
            if (node.getType().equals(ASTNode.Type.UNKNOWN)) {
              // TODO - have a proper message with the name of the offending element. 
              return false;
            }

            return true;
          }
        };
        break;
      }
      case CORE_10203: {
        func = new ValidationFunction<ASTNode>() {

          @Override
          public boolean check(ValidationContext ctx, ASTNode node) {

            // encoding only on some element
            if (node.isSetEncoding() && 
                !(node.getType() == ASTNode.Type.FUNCTION_DELAY || node.getType() == ASTNode.Type.FUNCTION_RATE_OF
                || node.getType() == ASTNode.Type.NAME_TIME || node.getType() == ASTNode.Type.NAME_AVOGADRO))
            {
              // TODO - who to recognize annotation and annotation-xml ?              
              return false;
            }
            
            return true;
          }
        };
        break;
      }
      case CORE_10204: {
        func = new ValidationFunction<ASTNode>() {

          @Override
          public boolean check(ValidationContext ctx, ASTNode node) {

            // TODO - ci is only allowed since L2V5/L3
            
            // definitionURL only on some elements
            if (node.isSetDefinitionURL() && 
                !(node.isSemantics() || node.isName() || node.getType() == ASTNode.Type.FUNCTION_DELAY 
                || node.getType() == ASTNode.Type.FUNCTION_RATE_OF)) 
            {
              return false;
            }
            
            return true;
          }
        };
        break;
      }
      case CORE_10205: {
        func = new ValidationFunction<ASTNode>() {

          @Override
          public boolean check(ValidationContext ctx, ASTNode node) {

            // check allowed values for definitionURL
            if (node.isSetDefinitionURL() && !allowedCsymbolURI.contains(node.getDefinitionURL())) {
              return false;
            }

            return true;
          }
        };
        break;
      }
            
    case CORE_10208:
      func = new ValidationFunction<ASTNode>() {

        @Override
        public boolean check(ValidationContext ctx, ASTNode node) {

          // Lambda only allowed as first child of FunctionDefinitions
          if (node.isLambda()) {

            TreeNode p = node.getParent();
            return p instanceof FunctionDefinition;
          }

          return true;
        }
      };
      break;
    case CORE_10209:
      func = new ValidationFunction<ASTNode>() {

        @Override
        public boolean check(ValidationContext ctx, ASTNode node) {

          // We don't validate inside FunctionDefinition
          if (node.getParentSBMLObject() instanceof FunctionDefinition) {
            return true;
          }
          
          // In logical operators...
          if (node.isLogical()) {

            // all children must be booleans
            for (ASTNode n : node.getChildren()) {
              if (!n.isBoolean()) {
                
                return false;
              }
            }
          }

          return true;
        }
      };
      break;

    case CORE_10210:
      func = new ValidationFunction<ASTNode>() {

        private final Set<ASTNode.Type> set = createSet();


        @Override
        public boolean check(ValidationContext ctx, ASTNode node) {

          // In operators...
          if (node.isOperator() || set.contains(node.getType())) {

            // all children must be numbers
            for (ASTNode n : node.getChildren()) {
              if (ValidationTools.getDataType(n) != ValidationTools.DT_NUMBER) {
                return false;
              }
            }
          }

          return true;
        }


        private Set<ASTNode.Type> createSet() {
          Set<ASTNode.Type> set = new HashSet<ASTNode.Type>();

          // plus, minus, times, divide and power are included in the test ASTNode.isOperator()
          
          set.add(ASTNode.Type.FUNCTION_ROOT);
          set.add(ASTNode.Type.FUNCTION_ABS);
          set.add(ASTNode.Type.FUNCTION_EXP);
          set.add(ASTNode.Type.FUNCTION_LN);
          set.add(ASTNode.Type.FUNCTION_LOG);
          set.add(ASTNode.Type.FUNCTION_FLOOR);
          set.add(ASTNode.Type.FUNCTION_CEILING);
          set.add(ASTNode.Type.FUNCTION_FACTORIAL);

          set.add(ASTNode.Type.FUNCTION_SIN);
          set.add(ASTNode.Type.FUNCTION_COS);
          set.add(ASTNode.Type.FUNCTION_TAN);
          set.add(ASTNode.Type.FUNCTION_SEC);
          set.add(ASTNode.Type.FUNCTION_CSC);
          set.add(ASTNode.Type.FUNCTION_COT);

          set.add(ASTNode.Type.FUNCTION_SINH);
          set.add(ASTNode.Type.FUNCTION_COSH);
          set.add(ASTNode.Type.FUNCTION_TANH);
          set.add(ASTNode.Type.FUNCTION_SECH);
          set.add(ASTNode.Type.FUNCTION_CSCH);
          set.add(ASTNode.Type.FUNCTION_COTH);

          set.add(ASTNode.Type.FUNCTION_ARCSIN);
          set.add(ASTNode.Type.FUNCTION_ARCCOS);
          set.add(ASTNode.Type.FUNCTION_ARCTAN);
          set.add(ASTNode.Type.FUNCTION_ARCSEC);
          set.add(ASTNode.Type.FUNCTION_ARCCSC);
          set.add(ASTNode.Type.FUNCTION_ARCCOT);

          set.add(ASTNode.Type.FUNCTION_ARCSINH);
          set.add(ASTNode.Type.FUNCTION_ARCCOSH);
          set.add(ASTNode.Type.FUNCTION_ARCTANH);
          set.add(ASTNode.Type.FUNCTION_ARCSECH);
          set.add(ASTNode.Type.FUNCTION_ARCCSCH);
          set.add(ASTNode.Type.FUNCTION_ARCCOTH); // TODO - check L3V2 specs to see if there are any of the new operators here.

          return set;
        }
      };
      break;
    case CORE_10211:
      func = new ValidationFunction<ASTNode>() {

        @Override
        public boolean check(ValidationContext ctx, ASTNode node) {

          // In comparator...
          if (node.isRelational() && node.getChildCount() > 0) {

            byte dt = ValidationTools.getDataType(node.getChild(0));

            // all children must have same Type
            for (int i = 1; i < node.getNumChildren(); i++) {
              if (dt != ValidationTools.getDataType(node.getChild(i))) {
                return false;
              }
            }
          }

          return true;
        }
      };
      break;

    case CORE_10212:
      func = new ValidationFunction<ASTNode>() {

        @Override
        public boolean check(ValidationContext ctx, ASTNode node) {

          // If is piecewise...
          if (node.isPiecewise() && node.getNumChildren() > 0) {

            byte dt = ValidationTools.getDataType(node.getLeftChild());

            if (dt == ValidationTools.DT_STRING) {
              return false;
            }

            // all children must have same Type
            for (int i = 0; i < node.getNumChildren(); i += 2) {
              if (dt != ValidationTools.getDataType(node.getChild(i))) {
                return false;
              }
            }
          }

          return true;
        }
      };
      break;

    case CORE_10213:
      func = new ValidationFunction<ASTNode>() {

        @Override
        public boolean check(ValidationContext ctx, ASTNode node) {

          // do not check inside functionDefinition
          if (node.getParentSBMLObject() instanceof FunctionDefinition) {
            return true;
          }
          
          // If is piecewise...
          if (node.getType() == Type.FUNCTION_PIECEWISE) {

            // every second node must be a condition and therefore return a
            // boolean
            for (int i = 1; i < node.getNumChildren(); i += 2) {
              ASTNode child = node.getChild(i);

              if (ValidationTools.getDataType(
                child) != ValidationTools.DT_BOOLEAN) {
                // System.out.println("Node " + child.getType() + " was " +
                // ValidationTools.getDataType(child));
                return false;
              }
            }
          }

          return true;
        }
      };
      break;
    case CORE_10214:
      func = new AbstractValidationFunction<ASTNode>() {

        @Override
        public boolean check(ValidationContext ctx, ASTNode node) {

          // If is a function
          if (node.getType() == Type.FUNCTION) {

            Model m = node.getParentSBMLObject().getModel();

            if (m != null) {
              
              FunctionDefinition f = m.getFunctionDefinition(node.getName());
              
              if (f == null) {

                // TODO - for the second parameter, we should print something more complete so that the element could be identified easily
                ValidationConstraint.logError(ctx, CORE_10214, node.getParentSBMLObject(), ValidationTools.printASTNodeAsFormula(node.getParentSBMLObject().getMath()),
                    node.getParentSBMLObject().getElementName(), node.getName());
                
                return  false;
              
              }
            }
          }

          return true;
        }

      };
      break;

    case CORE_10215:
      func = new ValidationFunction<ASTNode>() {

        @Override
        public boolean check(ValidationContext ctx, ASTNode node) {

          // If it's a name
          if (node.getType() == Type.NAME) {

            String name = node.getName();
            MathContainer parent = node.getParentSBMLObject();

            if (parent == null
              || ValidationTools.isLocalParameter(node, name)
              || parent instanceof FunctionDefinition
              || !parent.getPackageName().equals("core"))
            {
              return true;
            }

            Model m = parent.getModel();

            if (m != null) {
              boolean allowReaction = true;
              boolean allowSpeciesRef = false;

              if (ctx.isLevelAndVersionLesserEqualThan(2, 1)) {
                allowReaction = false;
              }

              if (ctx.getLevel() > 2) {
                allowSpeciesRef = true;
              }

              // If the name doesn't match anything the constraint is broken
              if (m.getCompartment(name) == null && m.getSpecies(name) == null
                && m.getParameter(name) == null
                && (!allowReaction || m.getReaction(name) == null)
                && (!allowSpeciesRef
                  || !ValidationTools.isSpeciesReference(m, name))) 
              {                
                return false;
              }
            }
          }

          return true;
        }

      };
      break;

    case CORE_10216:
      func = new ValidationFunction<ASTNode>() {

        @Override
        public boolean check(ValidationContext ctx, ASTNode node) {

          // If it's a name
          if (node.getType() == Type.NAME) {

            String id = node.getName();
            MathContainer parent = node.getParentSBMLObject();

            if (parent == null || parent instanceof FunctionDefinition) {
              return true;
            }

            Model m = parent.getModel();

            // We have to go through all the ASTNode defined in the document and find places where
            // an invalid id is used, then if the id is one of any local parameter, we should fire the constraint.
            // So we need to test all ASTNode where the parent is NOT a KineticLaw. And make use of the Model find methods.
            
            if (m != null && (! (parent instanceof KineticLaw))) {

              SBase sbase = m.findUniqueSBase(id);
              
              // If the id doesn't match anything, it can be a localParameter
              if (sbase == null && (m.findLocalParameters(id).size() > 0)) {
                return false;
              }
            }
          }

          return true;
        }

      };
      break;

    case CORE_10218:
      func = new ValidationFunction<ASTNode>() {

        private final Set<ASTNode.Type> unaries   = getUnaryTypes();
        private final Set<ASTNode.Type> binaries  = getBinaryTypes();
        private final Set<ASTNode.Type> relations = getRelationTypes();


        @Override
        public boolean check(ValidationContext ctx, ASTNode node) {

          ASTNode.Type type = node.getType();

          // Unary functions
          if (unaries.contains(type)) {
            return node.isUnary();
          }
          // Binary functions
          else if (binaries.contains(type)) {
            return node.getNumChildren() == 2;
          }
          // Can't be empty
          else if (type == Type.FUNCTION_PIECEWISE) {
            if (node.getNumChildren() > 0) {
              // piece element should have two child, otherwise element one.
              Map<String, Integer> piecewiseIdMap = new HashMap<String, Integer>(); 

              // counting number of child
              for (ASTNode child : node.getListOfNodes()) {
                String elementPiecewiseId = (String) child.getUserObject(JSBML.PIECEWISE_ID);
                Integer nbChild = piecewiseIdMap.get(elementPiecewiseId);
                
                if (elementPiecewiseId == null) {
                  // we don't have enough information to validate this node
                  return true;
                }
                
                if (nbChild == null) {
                  nbChild = 0;
                }
                nbChild++;
                piecewiseIdMap.put(elementPiecewiseId, nbChild);
              }

              // validating the number of child found
              for (String piecewiseId : piecewiseIdMap.keySet()) {
                Integer nbChild = piecewiseIdMap.get(piecewiseId);
                
                if (piecewiseId.contains("piece") && nbChild != 2) {
                  return false;
                } else if (piecewiseId.contains("other") && nbChild != 1) {
                  return false;
                }
              }

            } else {
              // zero child are allowed for piecewise in MATHML 2 but not in SBML/libSBML ?
              return false;
            }
          }
          // Can have one or two children
          else if (type == Type.FUNCTION_ROOT || type == Type.MINUS) {
            return node.getNumChildren() == 1 || node.getNumChildren() == 2;
          }
          // In MathML 2 these types must have at least 2 children
          else if (relations.contains(type)) {
            return node.getNumChildren() > 1; // TODO - check again but I think some relational operators can have zero or more child
          }
          // Special case before l2v4
          else if (type == Type.FUNCTION
            && ctx.isLevelAndVersionLessThan(2, 4)) {

            @SuppressWarnings("unchecked")
            ValidationFunction<ASTNode> f2 =
              (ValidationFunction<ASTNode>) new ASTNodeConstraints().getValidationFunction(CORE_10219, context);

            return f2.check(ctx, node);
          }

          return true;
        }


        private Set<ASTNode.Type> getBinaryTypes() {
          Set<ASTNode.Type> set = new HashSet<ASTNode.Type>();

          set.add(ASTNode.Type.DIVIDE);
          set.add(ASTNode.Type.POWER);
          set.add(ASTNode.Type.RELATIONAL_NEQ);
          set.add(ASTNode.Type.FUNCTION_DELAY);
          set.add(ASTNode.Type.FUNCTION_POWER);
          set.add(ASTNode.Type.FUNCTION_LOG);
          
          set.add(ASTNode.Type.LOGICAL_IMPLIES);
          set.add(ASTNode.Type.FUNCTION_QUOTIENT);
          set.add(ASTNode.Type.FUNCTION_REM);

          return set;
        }


        private Set<ASTNode.Type> getRelationTypes() {
          Set<ASTNode.Type> set = new HashSet<ASTNode.Type>();
          set.add(ASTNode.Type.RELATIONAL_EQ);
          set.add(ASTNode.Type.RELATIONAL_GEQ);
          set.add(ASTNode.Type.RELATIONAL_GT);
          set.add(ASTNode.Type.RELATIONAL_LT);
          set.add(ASTNode.Type.RELATIONAL_LEQ);

          return set;
        }


        private Set<ASTNode.Type> getUnaryTypes() {
          Set<ASTNode.Type> set = new HashSet<ASTNode.Type>();

          set.add(ASTNode.Type.FUNCTION_ABS);
          set.add(ASTNode.Type.FUNCTION_EXP);
          set.add(ASTNode.Type.FUNCTION_LN);
          set.add(ASTNode.Type.LOGICAL_NOT);
          set.add(ASTNode.Type.FUNCTION_FLOOR);
          set.add(ASTNode.Type.FUNCTION_CEILING);
          set.add(ASTNode.Type.FUNCTION_FACTORIAL);

          set.add(ASTNode.Type.FUNCTION_SIN);
          set.add(ASTNode.Type.FUNCTION_COS);
          set.add(ASTNode.Type.FUNCTION_TAN);
          set.add(ASTNode.Type.FUNCTION_SEC);
          set.add(ASTNode.Type.FUNCTION_CSC);
          set.add(ASTNode.Type.FUNCTION_COT);

          set.add(ASTNode.Type.FUNCTION_SINH);
          set.add(ASTNode.Type.FUNCTION_COSH);
          set.add(ASTNode.Type.FUNCTION_TANH);
          set.add(ASTNode.Type.FUNCTION_SECH);
          set.add(ASTNode.Type.FUNCTION_CSCH);
          set.add(ASTNode.Type.FUNCTION_COTH);

          set.add(ASTNode.Type.FUNCTION_ARCSIN);
          set.add(ASTNode.Type.FUNCTION_ARCCOS);
          set.add(ASTNode.Type.FUNCTION_ARCTAN);
          set.add(ASTNode.Type.FUNCTION_ARCSEC);
          set.add(ASTNode.Type.FUNCTION_ARCCSC);
          set.add(ASTNode.Type.FUNCTION_ARCCOT);

          set.add(ASTNode.Type.FUNCTION_ARCSINH);
          set.add(ASTNode.Type.FUNCTION_ARCCOSH);
          set.add(ASTNode.Type.FUNCTION_ARCTANH);
          set.add(ASTNode.Type.FUNCTION_ARCSECH);
          set.add(ASTNode.Type.FUNCTION_ARCCSCH);
          set.add(ASTNode.Type.FUNCTION_ARCCOTH);

          set.add(ASTNode.Type.FUNCTION_RATE_OF);
          
          return set;
        }

      };
      break;

    case CORE_10219:
      func = new ValidationFunction<ASTNode>() {

        @Override
        public boolean check(ValidationContext ctx, ASTNode node) {

          ASTNode.Type type = node.getType();

          if (type == Type.FUNCTION) {
            
            // System.out.println("10219 - Found a FUNCTION " + " - " + node.getName());
            
            Model m = node.getParentSBMLObject().getModel();

            if (m != null) {
              FunctionDefinition fd = m.getFunctionDefinition(node.getName());

              if (fd != null) {
                
                // TODO - for L3V2, we can have no bvar defined !!
                
                // System.out.println("10219 - " + node.getNumChildren() + " - " + fd.getArgumentCount());
                
                return node.getNumChildren() == fd.getArgumentCount();
              } else {
                // System.out.println("10219 - fd is null");
              }
            } else {
              // System.out.println("10219 - Model is null");
            }
          }

          return true;
        }

      };
      break;

    case CORE_10220:
      func = new ValidationFunction<ASTNode>() {

        @Override
        public boolean check(ValidationContext ctx, ASTNode node) {

          // Only allowed on numbers
          if (node.isSetUnits()) {

            return node.isNumber();
          }

          if (node.isSetUserObjects() && node.getUserObject(JSBML.UNKNOWN_XML) != null) {

            String invalidUnits = ValidationTools.checkUnknowndAttribute(ctx, node, "units");
            
            if (invalidUnits != null) {
              return false;
            }
          }

          if (node.getParentSBMLObject() != null 
              && node.getParentSBMLObject().getUserObject(JSBML.UNKNOWN_XML) != null) 
          {
            // System.out.println(node.getParentSBMLObject().getUserObject(JSBML.UNKNOWN_XML));
            
            // TODO - put this test on the MathContainer only to avoid getting it several times
            String invalidUnits = ValidationTools.checkUnknowndAttribute(ctx, (AbstractTreeNode) node.getParentSBMLObject(), "units");
            
            if (invalidUnits != null) {
              return false;
            }
          }
          
          return true;
        }
      };
      break;

    case CORE_10221:
      func = new ValidationFunction<ASTNode>() {

        @Override
        public boolean check(ValidationContext ctx, ASTNode node) {

          if (node.isSetUnits()) {
            String units = node.getUnits();

            if (units != null && !units.isEmpty()) {
              // Checks if the unit is predefined or defined in the model
              if (!(Unit.isUnitKind(units, ctx.getLevel(), ctx.getVersion()))
                  && node.getUnitsInstance() == null) {
                return false;
              }
            }

          }

          return true;
        }

      };
      break;

    case CORE_10222: {
      func = new ValidationFunction<ASTNode>() {

        @Override
        public boolean check(ValidationContext ctx, ASTNode node) {

          if (node.getParentSBMLObject() == null) {
            // something is wrong with the ASTNode
            return true;
          }
          
          Model m = node.getParentSBMLObject().getModel();

          if (m != null && node.isName()) {
            Compartment c = m.getCompartment(node.getName());

            if (c != null && c.getSpatialDimensions() == 0) {
              return false;
            }
          }

          return true;
        }

      };
      break;
    }

    case CORE_10223: {
      func = new ValidationFunction<ASTNode>() {

        @Override
        public boolean check(ValidationContext ctx, ASTNode node) {

          if (node.getType() == ASTNode.Type.FUNCTION_RATE_OF) {
            
            // check that the child of a rateOf is a ci element.
            if (node.getChildCount() > 0 && !(node.getChild(0).getType().equals(ASTNode.Type.NAME))) {
              return false;
            }
          }

          return true;
        }

      };
      break;
    }

    case CORE_10224: {
      func = new ValidationFunction<ASTNode>() {

        @Override
        public boolean check(ValidationContext ctx, ASTNode node) {

          if (node.getType() == ASTNode.Type.FUNCTION_RATE_OF) {
            
            // The target of a rateOf csymbol function must not appear as the variable of an AssignmentRule, 
            // nor may its value be determined by an AlgebraicRule
            if (node.getChildCount() > 0 && node.getChild(0).getType().equals(ASTNode.Type.NAME)) {
              final String id = node.getChild(0).getName();
              
              Model m = node.getParentSBMLObject().getModel();
              SBase sbase = m.getAssignmentRuleByVariable(id);
              
              if (sbase != null) {
                return false;
              }
              
              // checking all algebraicRules
              for (Rule r : m.getListOfRules()) {
                if (r instanceof AlgebraicRule) {
                  // check the rule math for the given id
                  ASTNode math = r.getMath();
                  
                  if (math != null) {
                    List<? extends TreeNode> wrongNode = math.filter(new Filter() {
                      
                      @Override
                      public boolean accepts(Object o) {
                        
                        if (o instanceof ASTNode && ((ASTNode) o).getType().equals(Type.NAME)
                            && ((ASTNode) o).getName().equals(id)) 
                        {
                          return true;
                        }
                        
                        return false;
                      }
                    });
                    
                    if (wrongNode != null && wrongNode.size() > 0) {
                      return false;
                    }
                  }
                }
              }
            }
          }

          return true;
        }

      };
      break;
    }

    case CORE_10225: {
      func = new ValidationFunction<ASTNode>() {

        @Override
        public boolean check(ValidationContext ctx, ASTNode node) {

          if (node.getType() == ASTNode.Type.FUNCTION_RATE_OF) {
            
            // The target of a rateOf csymbol function must not appear as the variable of an AssignmentRule, 
            // nor may its value be determined by an AlgebraicRule
            if (node.getChildCount() > 0 && node.getChild(0).getType().equals(ASTNode.Type.NAME)) {
              final String id = node.getChild(0).getName();
              
              Model m = node.getParentSBMLObject().getModel();
              SBase sbase = m.getSBaseById(id);

              if (sbase instanceof Species && ((Species) sbase).isSetHasOnlySubstanceUnits()
                  && !((Species) sbase).hasOnlySubstanceUnits()) 
              {
                Species s = (Species) sbase;
                final String cid = s.getCompartment();
                
                if (cid == null || cid.trim().length() == 0) {
                  // there is a problem with the compartment attribute, we cannot check this constraint.
                  return true;
                }
                
                sbase = m.getAssignmentRuleByVariable(cid);

                if (sbase != null) {
                  return false;
                }

                // checking all algebraicRules
                for (Rule r : m.getListOfRules()) {
                  if (r instanceof AlgebraicRule) {
                    // check the rule math for the given id
                    ASTNode math = r.getMath();

                    if (math != null) {
                      List<? extends TreeNode> wrongNode = math.filter(new Filter() {

                        @Override
                        public boolean accepts(Object o) {

                          if (o instanceof ASTNode && ((ASTNode) o).getType().equals(Type.NAME)
                              && ((ASTNode) o).getName().equals(cid)) 
                          {
                            return true;
                          }

                          return false;
                        }
                      });

                      if (wrongNode != null && wrongNode.size() > 0) {
                        return false;
                      }
                    }
                  }
                }
              }
            }
          }

          return true;
        }

      };
      break;    }

    case CORE_10311: {
      func = new AbstractValidationFunction<ASTNode>() {
      
        @Override
        public boolean check(ValidationContext ctx, ASTNode node) {

          if (node.isSetUnits()) {
            boolean isValid = SyntaxChecker.isValidId(node.getUnits(), ctx.getLevel(), ctx.getVersion());
            
            if (!isValid) {
              ValidationConstraint.logErrorWithPostmessageCode(ctx, CORE_10311, CORE_10311 + "_MATH", node.getParentSBMLObject(), node.getUnits(),
                  node.getParentSBMLObject().getElementName());
              return false;
            }
          }
          return true;
        }
      };
      break;
    }

    case CORE_10501: { // TODO - validation could be done directly inside the ValidationUnitscompiler avoiding to go through the ASTNode several times
      func = new ValidationFunction<ASTNode>() {

        @Override
        public boolean check(ValidationContext ctx, ASTNode node) {

          // don't check this rule inside FunctionDefinition or if object is incomplete
          if (node.getParentSBMLObject() == null || node.getParentSBMLObject() instanceof FunctionDefinition || node.getParentSBMLObject().getModel() == null) {
            return true;
          }

          // TODO - get the units of the overall ASTNode and if they are invalid, do not report this error ?? Does not seems true all the time though !
          
          Type t = node.getType(); // TODO - check section 3.4.11 in the L2V5 specs
          UnitsCompiler unitsCompiler = new UnitsCompiler(node.getParentSBMLObject().getModel(), ctx);
          
          // || t == Type.FUNCTION_ABS || t == Type.FUNCTION_CEILING || t == Type.FUNCTION_FLOOR. // The units of other operators such as abs , floor , and ceiling , can be anything.
          
          if (t == Type.PLUS || t == Type.MINUS 
            || t == Type.RELATIONAL_EQ || t == Type.RELATIONAL_GEQ
            || t == Type.RELATIONAL_GT || t == Type.RELATIONAL_LEQ
            || t == Type.RELATIONAL_LT || t == Type.RELATIONAL_NEQ
            || t == Type.FUNCTION_MAX || t == Type.FUNCTION_MIN) 
          {
            if (node.getNumChildren() > 0) {
              UnitDefinition ud = node.getChild(0).compile(unitsCompiler).getUnits();
              
              if (logger.isDebugEnabled()) {
                logger.debug("10501 - unit = " + ud + " " + UnitDefinition.printUnits(ud));
              }
              
              // the units can be null if we have only 'cn' element without sbml:units
              if (ud == null) {
                // we cannot check the units, so we return true
                return true;
              }
              
              // should not be null for other child type
              if (ud == null || ud.getNumChildren() == 0
                || ud.getUnit(0).isInvalid()) 
              {
                if (ud != null && ud.isInvalid()) {
                  // we cannot check the units, so we return true
                  return true;
                }
                return false;
              }

              for (int n = 1; n < node.getNumChildren(); n++) {
                UnitDefinition ud2 = node.getChild(n).compile(unitsCompiler).getUnits();

                if (logger.isDebugEnabled()) {
                  logger.debug("10501 - unit n = " + ud2 + " " + UnitDefinition.printUnits(ud2));
                }
                
                // the units can be null if we have only 'cn' element without sbml:units
                if (ud2 == null) {
                  // we cannot check the units, so we return true
                  return true;
                }
                
                // one of the children doesn't have a unit or the unit is not
                // the same as the rest
                if (ud2 == null || !UnitDefinition.areEquivalent(ud, ud2)) {
                  
                  if (ud2 != null && ud2.isInvalid()) {
                    // we cannot check the units, so we return true
                    return true;                    
                  }
                  
                  return false;
                }
              }
            }
          } else if (t == Type.FUNCTION_DELAY) {
            if (node.getNumChildren() == 2) {
              ASTNode right = node.getRightChild();

              UnitDefinition ud = right.getUnitsInstance();
          
              // the units can be null for 'cn' element without sbml:units
              if (ud == null && right.isNumber()) {
                // we cannot check the units, so we return true
                return true;
              }
              if (ud == null || ud.simplify().isInvalid()) {
                // we cannot check the units, so we return true
                return true;
              }

              if (!ud.isVariantOfTime()) {
                
                logger.debug("10501 - parameter units are not a variant of time");
                
                return false;
              }
            }
          } else if (t == Type.FUNCTION_PIECEWISE) {
            
            if (node.getNumChildren() == 0) {
              return true;
            }
            
            UnitDefinition ud = null;
            
            if (node.getChild(0) != null) {
              try {
                ud = node.getChild(0).deriveUnit();
              } catch (Exception e) {
                // on some invalid model, we get an exception thrown
              }
            }
            
            // the units can be null if we have only 'cn' element without sbml:units
            if (ud == null) {
              // we cannot check the units, so we return true
              return true;
            }
            
            if (logger.isDebugEnabled()) {
              logger.debug("10501 - piecewise - unit = " + ud);
            }            
            
            for (int n = 1; n < node.getNumChildren(); n++) {
              ASTNode child = node.getChild(n);
              UnitDefinition def = child.deriveUnit();

              // the units can be null if we have only 'cn' element without sbml:units
              if (def == null) {
                // we cannot check the units, so we return true
                return true;
              }
              
              if (logger.isDebugEnabled()) {
                logger.debug("10501 - piecewise - unit n = " + def);
              }

              // Even children must be same unit as first child
              if (n % 2 == 0) {
                if ((ud == null || def == null || def.isInvalid()) || ud.isInvalid()) 
                {
                  // We cannot check properly the units so we return true;
                  return true;
                }
                if (!UnitDefinition.areEquivalent(ud, def)) {
                  return false;
                }
              }
              // Odd children must be dimensionless; // TODO - why not boolean ??
              else {
                if (def == null || !def.isVariantOfDimensionless()) {
                  return false;
                }
              }
            }
            
          } else if (t == Type.FUNCTION_POWER) {
            
            if (node.getNumChildren() < 2) {
              return true;
            }
            // checking the second argument of 'pow', it should be an integer otherwise we need to fail this rule.

            // TODO - if the units of the second argument are invalid, do not report this error - double check discussions with Sarah
            
            ASTNode exponent = node.getChild(1);
            ASTNodeValue exponentValue = exponent.compile(unitsCompiler);
            
            try {
              Double exponentDbl = new Double(exponentValue.toDouble());
              
              if ((exponentDbl == Math.floor(exponentDbl)) && !Double.isInfinite(exponentDbl)) {
                // the exponent is an integer. all good.
              } else {
                // TODO - do a custom error message for this one
                logger.debug("10501 - power - non integer exponent '" + exponentDbl + "'");
                return false;
              }
              
              // TODO ? - the units of the first argument should be 'dimensionless' (if b is not integer or rational). The second argument (b) should always have units of 'dimensionless'.
              
            } catch (Exception e) {
              logger.debug("10501 - power - there was a problem getting the double value of the exponent - " + e.getMessage());
            }
            
          } else if (t == Type.FUNCTION_ROOT) {
            
            if (node.getNumChildren() < 2) {
              return true;
            }
            // checking the first argument of 'root', it should be an integer otherwise the unit of the second argument should be dimensionless.

            // TODO - check libsbml code in ExponentUnitsCheck.cpp and create a separate method for this check.
            
            ASTNode degree = node.getChild(0);
            ASTNodeValue degreeValue = degree.compile(unitsCompiler);
            
            
            try {
              Double degreeDbl = new Double(degreeValue.toDouble());
              ASTNodeValue value = node.getChild(1).compile(unitsCompiler);
              
              if ((degreeDbl == Math.floor(degreeDbl)) && !Double.isInfinite(degreeDbl)) {
                // the exponent is an integer. all good on this side.
                
                if (value.getUnits().isInvalid()) {
                  return false;
                }
                
              } else {
                
                if (!value.getUnits().isVariantOfDimensionless()) {


                  // TODO - do a custom error message for this one
                  // logger.debug("10501 - root - non integer exponent '" + degreeDbl + "'");
                  return false;
                }
              }
              
            } catch (Exception e) {
              logger.debug("10501 - root - there was a problem getting the double value of the degree - " + e.getMessage());
            }
            
          } else if (t.toString().startsWith("FUNCTION_ARC") || t.toString().startsWith("FUNCTION_CO") // Trigonometric operators
              || t.toString().startsWith("FUNCTION_CS") || t.toString().startsWith("FUNCTION_SIN") 
              || t.toString().startsWith("FUNCTION_SEC") || t.toString().startsWith("FUNCTION_TAN")
              || t == Type.FUNCTION_EXP || t == Type.FUNCTION_LN || t == Type.FUNCTION_LOG || t == Type.FUNCTION_FACTORIAL) 
          {
            // dimensionless or boolean in L3V2
            for (int n = 0; n < node.getNumChildren(); n++) {
              ASTNode child = node.getChild(n);
              UnitDefinition def = child.compile(unitsCompiler).getUnits();
             
              if (!def.isInvalid() && (! (def.isVariantOfDimensionless() || (ctx.isLevelAndVersionGreaterEqualThan(3, 2) && child.isBoolean())))) {
                return false;
              }
            }
          }

          return true;
        }
      };
      break;
    }
    
    }

    return func;
  }

}
