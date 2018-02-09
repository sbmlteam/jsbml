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
package org.sbml.jsbml.validator.offline.i18n;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.sbml.jsbml.SBMLError;
import org.sbml.jsbml.validator.offline.factory.SBMLErrorCodes;

/**
 * Contains the post messages for each {@link SBMLError} in the English language.
 * 
 * <p>'post' mean that this is added at the end of the generic error message defined in the {@link SBMLErrorMessage} bundle.
 * This part of the error has, in general, several parameters to be able to customize the message to provide some information
 * about which element contained the error and why the error was raised.</p>
 * 
 * <p>The key for each post message is the integer defined for each {@link SBMLError} in {@link SBMLErrorCodes}.</p>
 * 
 * @see ResourceBundle
 * @author rodrigue
 * @since 1.3
 */
public class SBMLErrorPostMessage extends ResourceBundle {  


  
  /**
   * 
   */
  private static final Map<String, String> contents = new HashMap<String, String>();
    
  /**
   * 
   */
  public static final String DEFAULT_POST_MESSAGE_WITH_ID = "DEFAULT_POST_MESSAGE_WITH_ID";

  /**
   * 
   */
  public static final String DEFAULT_POST_MESSAGE_WITH_METAID = "DEFAULT_POST_MESSAGE_WITH_METAID";

  /**
   * 
   */
  public static final String DEFAULT_POST_MESSAGE_WITH_VARIABLE = "DEFAULT_POST_MESSAGE_WITH_VARIABLE";
  
  /**
   * 
   */
  public static final String DEFAULT_POST_MESSAGE_WITH_SYMBOL = "DEFAULT_POST_MESSAGE_WITH_SYMBOL";

  /**
   * 
   */
  public static final String DEFAULT_POST_MESSAGE_KINETIC_LAW = "DEFAULT_POST_MESSAGE_KINETIC_LAW";

  /**
   * 
   */
  public static final String DEFAULT_POST_MESSAGE = "DEFAULT_POST_MESSAGE";
  
  static {
      
      contents.put(DEFAULT_POST_MESSAGE_WITH_ID, "The <{0}> with id ''{1}'' does not comply.");
    
      contents.put(DEFAULT_POST_MESSAGE_WITH_METAID, "The <{0}> with metaid ''{1}'' does not comply.");
      
      contents.put(DEFAULT_POST_MESSAGE_WITH_VARIABLE, "The <{0}> with ''variable'' ''{1}'' does not comply.");
      
      contents.put(DEFAULT_POST_MESSAGE_WITH_SYMBOL, "The <{0}> with ''symbol'' ''{1}'' does not comply.");
      
      contents.put(DEFAULT_POST_MESSAGE_KINETIC_LAW, "The <kineticLaw> within the <reaction> with id ''{0}'' does not comply.");
      
      contents.put(DEFAULT_POST_MESSAGE, "The element {0} does not comply.");
      
      
      //    
      contents.put(Integer.toString(SBMLErrorCodes.CORE_10214), "The formula ''{0}'' in the math element of the <{1}> uses ''{2}'' which is not a function definition id.");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_10309), "The metaid ''{0}'' does not conform to the syntax.");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_10310), "The id ''{0}'' does not conform to the syntax.");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_10311), "The units ''{0}'' of the <{1}> with id ''{2}'' does not conform to the syntax."); // TODO - Several messages depending of the error ?
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_10311) + "_MATH", "The units ''{0}'' on the ''cn'' math element of <{1}> does not conform to the syntax.");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_10313), "The units ''{0}'' of the <{1}> with id ''{2}'' do not refer to a valid unit kind/built-in unit or the identifier of an existing <unitDefinition>.");
      
      // TODO - for 10311 and 10313, we could have a different main message for 'L2 and before' and L3
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_10501), "The element ''{0}'' does not comply.");
    
      // compartment
      contents.put(Integer.toString(SBMLErrorCodes.CORE_20501), "The <compartment> with id ''{0}'' should not have a ''size'' attribute OR should have a ''spatialDimensions'' attribute that is not set to ''0''.");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_20502), "The <compartment> with id ''{0}'' should not have a ''units'' attribute OR should have a ''spatialDimensions'' attribute that is not set to ''0''.");
          
      contents.put(Integer.toString(SBMLErrorCodes.CORE_20503), "The <compartment> with id ''{0}'' should have a ''constant'' attribute set to ''true'' OR should have a ''spatialDimensions'' attribute that is not set to ''0''.");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_20504), "The <compartment> with id ''{0}'' sets the ''outside'' attribute to ''{1}'' which does not exist as a <compartment>.");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_20505), "Compartment ''{0}'' encloses itself."); // TODO - Several messages depending the error
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_20506), "The <compartment> with id ''{0}'' refers to the ''outside'' <compartment> ''{1}'' which does not have ''spatialDimensions'' of ''0''.");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_20507), "The value of the 'units' attribute on a <compartment> having 'spatialDimensions' of '1' must be either 'length', 'metre', 'dimensionless', or the identifier of a <unitDefinition> based on either 'metre' (with 'exponent' equal to '1') or 'dimensionless'. The <compartment> with id ''{0}'' does not comply.");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_20508), "The value of the 'units' attribute on a <compartment> having 'spatialDimensions' of '2' must be either 'area', 'dimensionless', or the identifier of a <unitDefinition> based on either 'metre' (with 'exponent' equal to '2') or 'dimensionless'. The <compartment> with id ''{0}'' does not comply.");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_20509), "The value of the 'units' attribute on a <compartment> having 'spatialDimensions' of '3' must be either 'volume', 'litre', or the identifier of a <unitDefinition> based on either 'litre', 'metre' (with 'exponent' equal to '3'). The <compartment> with id ''{0}'' does not comply.");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_20510), "The <compartment> with id ''{0}'' refers to the compartmentType ''{1}'' which is not defined. ");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_20511), "The <compartment> with id ''{0}'' has undefined units.");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_20512), "The <compartment> with id ''{0}'' has undefined units.");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_20513), "The <compartment> with id ''{0}'' has undefined units.");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_20517), "The <compartment> with id ''{0}'' does not comply."); // TODO - Several messages depending the error
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_20518), "The <compartment> ''{0}'' has no discernable units.");
      
      
      // species
      contents.put(Integer.toString(SBMLErrorCodes.CORE_20601), "The <species> with id ''{0}'' refers to the compartment ''{1}'' which is not defined.");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_20602), "The <species> with id ''{0}'' should not have a ''spatialSizeUnits'' attribute OR should have a ''hasOnlySubstanceUnits'' attribute that is not set to ''true''");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_20603), "The <species> with id ''{0}'' is located in 0-D <compartment> ''{1}'' and therefore should not have a ''spatialSizeUnits'' attribute.");

      contents.put(Integer.toString(SBMLErrorCodes.CORE_20604), "The <species> with id ''{0}'' is located in 0-D <compartment> ''{1}'' and therefore should not have an ''initialConcentration'' attribute.");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_20605), "The <species> with id ''{0}'' is located in 1-D <compartment> ''{1}'' and therefore should not have a ''spatialSizeUnits'' attribute set to ''{2}''.");

      contents.put(Integer.toString(SBMLErrorCodes.CORE_20606), "The <species> with id ''{0}'' is located in 2-D <compartment> ''{1}'' and therefore should not have a ''spatialSizeUnits'' attribute set to ''{2}''.");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_20607), "The <species> with id ''{0}'' is located in 3-D <compartment> ''{1}'' and therefore should not have a ''spatialSizeUnits'' attribute set to ''{2}''.");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_20608), "The value of a <species>''s ''substanceUnits'' attribute can only be one of the following: ''substance'', ''mole'', ''item'', ''gram'',"
          + " ''kilogram'', ''dimensionless'', ''avogadro'', or the identifier of a <unitDefinition> derived from ''mole'' (with an ''exponent'' of ''1''), ''item'' (with an ''exponent'' of ''1''), ''gram'' (with an ''exponent'' of ''1''),"
          + " ''kilogram'' (with an ''exponent'' of ''1''), ''avogadro'' (with an ''exponent'' of ''1'') or ''dimensionless'' or a combination of these units. The current value ''{1}'' on the <species> with id ''{0}'' is not allowed.");

      contents.put(Integer.toString(SBMLErrorCodes.CORE_20609), "The <species> with id ''{0}'' cannot have both attributes ''initialAmount'' and ''initialConcentration''.");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_20610), "The species ''{0}'' occurs in both a rule and reaction ''{1}''.");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_20611), "The <species> with id ''{0}'' cannot have 'boundaryCondition' set to ''false'' and ''constant'' set to ''true''.");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_20612), "The <species> with id ''{0}'' refers to the speciesType ''{1}'' which is not defined.");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_20613), "The compartment ''{0}'' contains more than one species with species type ''{1}''.");

      contents.put(Integer.toString(SBMLErrorCodes.CORE_20614), "The <species> with the id ''{0}'' is missing the ''compartment'' attribute.");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_20615), "The <species> with the id ''{0}'' has the ''spatialSizeUnits'' attribute defined which is invalid.");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_20616), "The <species> with id ''{0}'' does not have a substanceUnits attribute, nor does its enclosing <model>.");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_20617), " The <species> with id ''{0}'' sets the ''conversionFactor'' to ''{1}'' but no <parameter> with that ''id'' exists in the <model>.");

      contents.put(Integer.toString(SBMLErrorCodes.CORE_20623), "The <species> with id ''{0}'' does not comply."); // TODO - Several messages depending which attributes are missing

      
      // parameter
      contents.put(Integer.toString(SBMLErrorCodes.CORE_20701), "The ''units'' attribute of the <parameter> with id ''{0}'' is ''{1}'', which does not comply.");
          
      contents.put(Integer.toString(SBMLErrorCodes.CORE_20702), "The <parameter> with id ''{0}'' does not have a ''units'' attribute.");

      contents.put(Integer.toString(SBMLErrorCodes.CORE_20705), "The <parameter> with id ''{0}'' should have the ''constant'' attribute set to ''true''.");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_20706), "The <parameter> with id ''{0}'' does not comply."); // TODO - Several messages depending which attributes are missing

      
      // initialAssignment
      contents.put(Integer.toString(SBMLErrorCodes.CORE_20801), "The <initialAssignment> with symbol ''{0}'' does not refer to an existing <compartment>, <species>, <parameter> or <speciesReference>."); // TODO - different message for different SBML level and version

      contents.put(Integer.toString(SBMLErrorCodes.CORE_20802), "The <initialAssignment> symbol ''{0}'' conflicts with the previously defined <initialAssignment> symbol ''{0}''");

      contents.put(Integer.toString(SBMLErrorCodes.CORE_20803), "The <assignmentRule> variable ''{0}'' conflicts with the previously defined <initialAssignment> symbol ''{0}''");

      contents.put(Integer.toString(SBMLErrorCodes.CORE_20804), "The <initialAssignment> with symbol ''{0}'' does not contain one and only one <math> element.");

      contents.put(Integer.toString(SBMLErrorCodes.CORE_20805), "The <initialAssignment> with symbol ''{0}'' does not comply."); // TODO - Several messages depending which attributes are missing

      contents.put(Integer.toString(SBMLErrorCodes.CORE_20806), "The <initialAssignment> with symbol ''{0}'' references a compartment which has spatialDimensions of 0.");
      
      
      // rules
      contents.put(Integer.toString(SBMLErrorCodes.CORE_20901), "The <assignmentRule> with variable ''{0}'' does not refer to an existing <compartment>, <species>, <parameter> or <speciesReference>."); // TODO - different message for different SBML level and version

      contents.put(Integer.toString(SBMLErrorCodes.CORE_20902), "The <rateRule> with variable ''{0}'' does not refer to an existing <compartment>, <species>, <parameter> or <speciesReference>."); // TODO - different message for different SBML level and version

      contents.put(Integer.toString(SBMLErrorCodes.CORE_20903), "The {0} with id ''{1}'' should have a constant value of ''false''.");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_20904), "The {0} with id ''{1}'' should have a constant value of ''false''.");

      // Rule SBMLErrorCodes.CORE_20905 removed because it was effectively a duplicate of 10304.

      contents.put(Integer.toString(SBMLErrorCodes.CORE_20906), "The <{0}> with {1} ''{2}'' creates a cycle with the <{3}> with {4} ''{5}''."); // TODO - different messages depending of the elements involved

      contents.put(Integer.toString(SBMLErrorCodes.CORE_20906) + "_SELF", "The <{0}> with {1} ''{2}'' refers to that variable within the math formula ''{3}''.");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_20906) + "_COMP", "The <{0}> assigning value to compartment ''{1}'' refers to species ''{2}''->  Since the use of the species id in this"
          + " context refers to a concentration, this is an implicit reference to compartment ''{1}''.");

      contents.put(Integer.toString(SBMLErrorCodes.CORE_20907), "");  // TODO - properly check the number of math elements and allow zero for L3V2

      contents.put(Integer.toString(SBMLErrorCodes.CORE_20908), ""); // 20908, 20909 and 20910 are attributes checks, using the default message for now.

      contents.put(Integer.toString(SBMLErrorCodes.CORE_20909), "");

      contents.put(Integer.toString(SBMLErrorCodes.CORE_20910), "");

      contents.put(Integer.toString(SBMLErrorCodes.CORE_20911), "The <{0}> with variable ''{1}'' references a compartment which has spatialDimensions of 0.");

      
      
      // constraints
      contents.put(Integer.toString(SBMLErrorCodes.CORE_21001), "The <constraint> with the formula ''{0}'' returns a value that is not Boolean.");

      contents.put(Integer.toString(SBMLErrorCodes.CORE_21002), "");

      contents.put(Integer.toString(SBMLErrorCodes.CORE_21003), "");

      contents.put(Integer.toString(SBMLErrorCodes.CORE_21004), "");

      contents.put(Integer.toString(SBMLErrorCodes.CORE_21005), "");

      contents.put(Integer.toString(SBMLErrorCodes.CORE_21006), "");

      contents.put(Integer.toString(SBMLErrorCodes.CORE_21007), "");

      contents.put(Integer.toString(SBMLErrorCodes.CORE_21008), "");

      contents.put(Integer.toString(SBMLErrorCodes.CORE_21009), "");


      // reaction
      contents.put(Integer.toString(SBMLErrorCodes.CORE_21101), ""); // 21101-21106 checks, using the default message for now.

      contents.put(Integer.toString(SBMLErrorCodes.CORE_21107), "The <reaction> with id ''{0}'' refers to the compartment ''{1}'' which is not defined.");
      
      // contents.put(Integer.toString(SBMLErrorCodes.CORE_21108), ""); // TODO 21108 and 21109 missing from the json file !
      
      // contents.put(Integer.toString(SBMLErrorCodes.CORE_21109), "");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_21110), ""); // TODO - Several messages depending which attributes are missing - same for 21150, 21151, 21116, 21117

      contents.put(Integer.toString(SBMLErrorCodes.CORE_21111), "The <speciesReference> in the <reaction> with id ''{0}'' references species ''{1}'', which is undefined.");

      contents.put(Integer.toString(SBMLErrorCodes.CORE_21113), "In <reaction> with id ''{0}'' the <speciesReference> with species ''{1}'' cannot have both ''stoichiometry'' and a <stoichiometryMath> element.");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_21116), ""); 
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_21117), "");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_21121), "The species ''{0}'' is not listed as a product, reactant, or modifier of reaction ''{1}''.");
      
      // using the default kineticLaw message for CORE_21122, CORE_21123
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_21124), "The local parameter with id ''{0}'' within the <reaction> ''{1}'' does not comply.");
      
      // using the default kineticLaw message for CORE_21125-CORE_21132
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_21173), "The <localParameter> with id ''{0}'' in the <reaction> with id ''{1}'' conflicts with the {2} referring to the <species> ''{0}''.");
      
      
      // event      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_21202), "The <trigger> element of the <event> with id ''{0}'' returns a value that is not Boolean. ");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_21203), "The <event> with id ''{0}'' does not contain any <eventAssignment> elements.");
      
      
      // 
      contents.put(Integer.toString(SBMLErrorCodes.CORE_80501), "The <compartment> with the id ''{0}'' does not have a ''size'' attribute, nor is its initial value set by an <initialAssignment> or <assignmentRule>.");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_80601), "The <species> with the id ''{0}'' does not have an ''initialConcentration'' or ''initialAmount'' attribute, nor is its initial value set by an <initialAssignment> or <assignmentRule>.");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_80701), "The <parameter> with the id ''{0}'' does not have a 'units' attribute.");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_80702), "The <parameter> with the id ''{0}'' does not have a 'value' attribute, nor is its initial value set by an <initialAssignment> or <assignmentRule>.");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_81121), "In this instance the local parameter with id ''{0}'' will shadow the ''{1}'' with an identical id.");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_99303), "The units ''{0}'' of the <{1}> with id ''{2}'' do not refer to a valid unit kind/built-in unit or the identifier of an existing <unitDefinition>.");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_99505), "The units of the <{0}> <math> expression ''{1}'' cannot be fully checked. Unit consistency reported as either no errors or further unit errors related to this object may not be accurate.");
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_99508), "The units of the <compartment> ''{0}'' cannot be fully checked. Unit consistency reported as either no errors or further unit errors related to this object may not be accurate.");
    
      // qualitative model
      
      contents.put(Integer.toString(SBMLErrorCodes.QUAL_10201), "The mathML math element in <functionTerm> with id ''{0}'' is not evaluating to a value of type boolean.");
      
      contents.put(Integer.toString(SBMLErrorCodes.QUAL_20301), "Attribute ''{0}'' is not part of the definition of an SBML Level 3 Version 1 Package qual Version 1 <qualitativeSpecies> element..");

      contents.put(Integer.toString(SBMLErrorCodes.QUAL_20308), "In <qualitativeSpecies> ''{0}'' <compartment> ''{1}'' is undefined.");

      contents.put(Integer.toString(SBMLErrorCodes.QUAL_20309), "In <qualitativeSpecies> ''{0}'' is the initalLevel: ''{1}'' greater than the maxLevel: ''{2}''.");

      contents.put(Integer.toString(SBMLErrorCodes.QUAL_20310), "The constant <qualitativeSpecies> ''{0}'' is referred to by an <output>.");

      contents.put(Integer.toString(SBMLErrorCodes.QUAL_20311), "The <transition> with id ''{0}'' includes an <output> that uses an assignment to the <qualitativeSpecies> ''{1}'' that has already been assigned.");

      contents.put(Integer.toString(SBMLErrorCodes.QUAL_20312), "The initalLevel of the <qualitativeSpecies> ''{0}'' is ''{1}'', which is negative.");

      contents.put(Integer.toString(SBMLErrorCodes.QUAL_20313), "The maxLevel of the <qualitativeSpecies> ''{0}'' is ''{1}'', which is negative.");
      
      
      contents.put(Integer.toString(SBMLErrorCodes.QUAL_20401), "Attribute ''{0}'' is not part of the definition of an SBML Level 3 Version 1 Package qual Version 1 <transition> element..");

      contents.put(Integer.toString(SBMLErrorCodes.QUAL_20405), "Has a <listOfFunctionTerms> == ''{0}'', has only one instant of <ListOfInputs> == ''{1}'', has only one instant of <ListOfOutputs> == ''{2}''.");

      contents.put(Integer.toString(SBMLErrorCodes.QUAL_20406), "The size of the <ListOfInputs> == ''{0}'', the size of the <ListOfOutputs> == ''{1}''.");

//      contents.put(Integer.toString(SBMLErrorCodes.QUAL_20407), "''{0}''");
//      
//      contents.put(Integer.toString(SBMLErrorCodes.QUAL_20408), "''{0}''");
//      
//      contents.put(Integer.toString(SBMLErrorCodes.QUAL_20409), "''{0}''");
     
      contents.put(Integer.toString(SBMLErrorCodes.QUAL_20410), "Attribute ''{0}'' is not part of the definition of an SBML Level 3 Version 1 Package qual Version 1 <listOfInputs> element. ");
      
      contents.put(Integer.toString(SBMLErrorCodes.QUAL_20411), "Attribute ''{0}'' is not part of the definition of an SBML Level 3 Version 1 Package qual Version 1 <listOfOutputs> element. ");
      
      contents.put(Integer.toString(SBMLErrorCodes.QUAL_20412), "Attribute ''{0}'' is not part of the definition of an SBML Level 3 Version 1 Package qual Version 1 <listOfFunctionTerms> element. ");
      
      contents.put(Integer.toString(SBMLErrorCodes.QUAL_20413), "The <transition> with id ''{0}'' includes a resultLevel that may cause the <qualitativeSpecies> ''{1}'' to exceed its maximumLevel. ");
      
      contents.put(Integer.toString(SBMLErrorCodes.QUAL_20414), "The resultLevel of the <functionTerm> is ''{0}'', which is negative. ");  
      
      
      contents.put(Integer.toString(SBMLErrorCodes.QUAL_20501), "Attribute ''{0}'' is not part of the definition of an SBML Level 3 Version 1 Package qual Version 1 <input> element.");

      contents.put(Integer.toString(SBMLErrorCodes.QUAL_20508), "The <qualitativeSpecies> ''{0}'' is undefined.");  
      
      contents.put(Integer.toString(SBMLErrorCodes.QUAL_20509), "The <qualitativeSpecies> ''{0}'' referred to by the <input> with the id ''{1}'' has constant set to true, but the transitionEffect of the <input> is set to consumption. ");  
      
      contents.put(Integer.toString(SBMLErrorCodes.QUAL_20510), "The threshholdLevel of the <input> with id''{0} is ''{1}'', which is negative.");
      
      
      contents.put(Integer.toString(SBMLErrorCodes.QUAL_20601), "Attribute ''{0}'' is not part of the definition of an SBML Level 3 Version 1 Package qual Version 1 <output> element.");
      
      contents.put(Integer.toString(SBMLErrorCodes.QUAL_20607), "<qualitativeSpecies> ''{0}'' is undefined.");
      
      contents.put(Integer.toString(SBMLErrorCodes.QUAL_20608), "The <qualitativeSpecies> ''{0}'' referred to by the <output> has constant set to ''{1}'' .");
      
      contents.put(Integer.toString(SBMLErrorCodes.QUAL_20609), "The value of the attribute <transitionEffect> is set to ''{0}'' and the attribute <outputLevel> is set == ''{1}''.");
      
      contents.put(Integer.toString(SBMLErrorCodes.QUAL_20610), "The outputLevel of the <output> with id ''{0}'' is ''{1}'', which is negative.");
      
      
      
      // Attribute 'symbol' is not part of the definition of an SBML Level 3 Version 1 
      contents.put(Integer.toString(SBMLErrorCodes.QUAL_20701), "Attribute ''{0}'' is not part of the definition of an SBML Level 3 Version 1 <output> element.");
      
      // The resultLevel of the <defaultTerm> is '-4', which is negative. 
      contents.put(Integer.toString(SBMLErrorCodes.QUAL_20705), "The resultLevel of the <{0}> is ''{1}'', which is negative.");
      
      // Attribute 'id' is not part of the definition of an SBML Level 3 Version 1
      contents.put(Integer.toString(SBMLErrorCodes.QUAL_20801), "Attribute <{0}> is not part of the definition of an SBML Level 3 Version 1");
      
      // The resultLevel of the <functionTerm> is '-1', which is negative. 
      contents.put(Integer.toString(SBMLErrorCodes.QUAL_20806), "The resultLevel of the <{0}> is ''{1}'', which is negative.");
 }

  @Override
  protected Object handleGetObject(String key) {

    return contents.get(key);
  }

  @Override
  public Enumeration<String> getKeys() {
    
    return java.util.Collections.enumeration(contents.keySet());
  }

}
