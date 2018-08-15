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
 * Contains the short messages for each {@link SBMLError} in the English language.
 * 
 * <p>The key for each short message is the integer defined for each {@link SBMLError} in {@link SBMLErrorCodes}.</p>
 * 
 * <p>Automatically generated file, using the python scripts extractErrors.py on the libSBML source code
 * and createSBMLErrorShortMessageBundle.py on the generated json file.</p>
 *
 * @see ResourceBundle
 * @since 1.3
 */
public class SBMLErrorShortMessage extends ResourceBundle { 
 

  /**
   * 
   */
  private static final Map<String, String> contents = new HashMap<String, String>();
  
  static {
      
        contents.put(Integer.toString(SBMLErrorCodes.CORE_00000), "Unknown error");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_00001), "Out of memory");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_00002), "File unreadable");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_00003), "File unwritable");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_00004), "File operation error");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_00005), "Network access error");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_00101), "Internal XML parser error");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_00102), "Unrecognized XML parser code");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_00103), "Transcoder error");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01001), "Missing XML declaration");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01002), "Missing XML encoding attribute");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01003), "Bad XML declaration");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01004), "Bad XML DOCTYPE");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01005), "Invalid character");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01006), "Badly formed XML");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01007), "Unclosed token");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01008), "Invalid XML construct");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01009), "XML tag mismatch");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01010), "Duplicate attribute");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01011), "Undefined XML entity");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01012), "Bad XML processing instruction");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01013), "Bad XML prefix");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01014), "Bad XML prefix value");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01015), "Missing required attribute");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01016), "Attribute type mismatch");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01017), "Bad UTF8 content");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01018), "Missing attribute value");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01019), "Bad attribute value");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01020), "Bad XML attribute");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01021), "Unrecognized XML element");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01022), "Bad XML comment");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01023), "Bad XML declaration location");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01024), "Unexpected EOF");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01025), "Bad XML ID value");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01026), "Bad XML IDREF");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01027), "Uninterpretable XML content");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01028), "Bad XML document structure");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01029), "Invalid content after XML content");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01030), "Expected quoted string");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01031), "Empty value not permitted");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01032), "Bad number");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01033), "Colon character not permitted");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01034), "Missing XML elements");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01035), "Empty XML content");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_09999), "");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10000), "Encountered unknown internal libSBML error");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10101), "File does not use UTF-8 encoding");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10102), "Encountered unrecognized element");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10103), "Document does not conform to the SBML XML schema");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10104), "Document is not well-formed XML");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10201), "Invalid MathML");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10202), "Disallowed MathML symbol found");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10203), "Use of the MathML 'encoding' attribute is not allowed on this element");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10204), "Use of the MathML 'definitionURL' attribute is not allowed on this element");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10205), "Invalid <csymbol> 'definitionURL' attribute value");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10206), "Use of the MathML 'type' attribute is not allowed on this element");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10207), "Disallowed MathML 'type' attribute value");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10208), "Use of <lambda> not permitted outside of FunctionDefinition objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10209), "Non-Boolean argument given to Boolean operator");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10210), "Non-numerical argument given to numerical operator");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10211), "Arguments to <eq> and <neq> must have the same data types");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10212), "Terms in a <piecewise> expression must have consistent data types");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10213), "The second argument of a <piece> expression must yield a Boolean value");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10214), "A <ci> element in this context must refer to a function definition");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10215), "A <ci> element in this context must refer to a model component");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10216), "Cannot use a KineticLaw local parameter outside of its local scope");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10217), "A formula's result in this context must be a numerical value");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10218), "Incorrect number of arguments given to MathML operator");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10219), "Incorrect number of arguments given to function invocation");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10220), "Attribute 'units' is only permitted on <cn> elements");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10221), "Invalid value given for the 'units' attribute");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10222), "A <ci> element cannot reference 0D Compartment");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10301), "Duplicate 'id' attribute value");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10302), "Duplicate unit definition 'id' attribute value");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10303), "Duplicate local parameter 'id' attribute value");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10304), "Multiple rules for the same variable are not allowed");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10305), "Multiple event assignments for the same variable are not allowed");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10306), "An event assignment and an assignment rule must not have the same value for 'variable'");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10307), "Duplicate 'metaid' attribute value");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10308), "Invalid syntax for an 'sboTerm' attribute value");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10309), "Invalid syntax for a 'metaid' attribute value");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10310), "Invalid syntax for an 'id' attribute value");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10311), "Invalid syntax for the identifier of a unit");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10312), "Invalid syntax for a 'name' attribute value");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10313), "Dangling reference to a unit.");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10401), "Missing declaration of the XML namespace for the annotation");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10402), "Multiple annotations using the same XML namespace");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10403), "The SBML XML namespace cannot be used in an Annotation object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10404), "Only one Annotation object is permitted under a given SBML object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10501), "");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10503), "The kinetic law's units are inconsistent with those of other kinetic laws in the model");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10511), "Mismatched units in assignment rule for compartment");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10512), "Mismatched units in assignment rule for species");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10513), "Mismatched units in assignment rule for parameter");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10514), "Mismatched units in assignment rule for stoichiometry");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10521), "Mismatched units in initial assignment to compartment");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10522), "Mismatched units in initial assignment to species");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10523), "Mismatched units in initial assignment to parameter");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10524), "Mismatched units in initial assignment to stoichiometry");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10531), "Mismatched units in rate rule for compartment");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10532), "Mismatched units in rate rule for species");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10533), "Mismatched units in rate rule for parameter");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10534), "Mismatched units in rate rule for stoichiometry");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10541), "The units of the kinetic law are not 'substance'/'time'");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10542), "The species' units are not consistent with units of extent");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10551), "The units of the delay expression are not units of time");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10561), "Mismatched units in event assignment for compartment");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10562), "Mismatched units in event assignment for species");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10563), "Mismatched units in event assignment for parameter");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10564), "Mismatched units in event assignment for stoichiometry");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10565), "The units of a priority expression must be 'dimensionless'");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10599), "Upper boundary of unit validation diagnostic codes");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10601), "The model is overdetermined");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10701), "Invalid 'sboTerm' attribute value for a Model object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10702), "Invalid 'sboTerm' attribute value for a FunctionDefinition object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10703), "Invalid 'sboTerm' attribute value for a Parameter object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10704), "Invalid 'sboTerm' attribute value for an InitialAssignment object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10705), "Invalid 'sboTerm' attribute value for a Rule object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10706), "Invalid 'sboTerm' attribute value for a Constraint object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10707), "Invalid 'sboTerm' attribute value for a Reaction object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10708), "Invalid 'sboTerm' attribute value for a SpeciesReference object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10709), "Invalid 'sboTerm' attribute value for a KineticLaw object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10710), "Invalid 'sboTerm' attribute value for an Event object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10711), "Invalid 'sboTerm' attribute value for an EventAssignment object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10712), "Invalid 'sboTerm' attribute value for a Compartment object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10713), "Invalid 'sboTerm' attribute value for a Species object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10714), "Invalid 'sboTerm' attribute value for a CompartmentType object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10715), "Invalid 'sboTerm' attribute value for a SpeciesType object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10716), "Invalid 'sboTerm' attribute value for an Event Trigger object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10717), "Invalid 'sboTerm' attribute value for an Event Delay object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10718), "Invalid 'sboTerm' attribute value for a LocalParameter object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10719), "Invalid 'sboTerm' attribute value for the <sbml> element");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10801), "Notes must be placed in the XHTML XML namespace");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10802), "XML declarations are not permitted in Notes objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10803), "XML DOCTYPE elements are not permitted in Notes objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10804), "Invalid notes content found");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10805), "Only one Notes subobject is permitted on a given SBML object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20101), "Invalid XML namespace for the SBML container element");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20102), "Missing or inconsistent value for the 'level' attribute");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20103), "Missing or inconsistent value for the 'version' attribute");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20104), "Inconsistent or invalid SBML Level/Version for the package namespace declaration");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20105), "The 'level' attribute must have a positive integer value");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20106), "The 'version' attribute must have a positive integer value");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20108), "Invalid attribute found on the SBML container element");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20109), "An L3 package ns found on the SBML container element");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20201), "No model definition found");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20202), "Incorrect ordering of components within the Model object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20203), "Empty ListOf___ object found");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20204), "The presence of a species requires a compartment");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20205), "Only one of each kind of ListOf___ object is allowed inside a Model object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20206), "Only FunctionDefinition, Notes and Annotation objects are allowed in ListOfFunctionDefinitions");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20207), "Only UnitDefinition, Notes and Annotation objects are allowed in ListOfUnitDefinitions objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20208), "Only Compartment, Notes and Annotation objects are allowed in ListOfCompartments objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20209), "Only Species, Notes and Annotation objects are allowed in ListOfSpecies objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20210), "Only Parameter, Notes and Annotation objects are allowed in ListOfParameters objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20211), "Only InitialAssignment, Notes and Annotation objects are allowed in ListOfInitialAssignments objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20212), "Only Rule, Notes and Annotation objects are allowed in ListOfRules objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20213), "Only Constraint, Notes and Annotation objects are allowed in ListOfConstraints objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20214), "Only Reaction, Notes and Annotation objects are allowed in ListOfReactions objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20215), "Only Event, Notes and Annotation objects are allowed in ListOfEvents objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20216), "A 'conversionFactor' attribute value must reference a Parameter object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20217), "Invalid 'timeUnits' attribute value");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20218), "Invalid 'volumeUnits' attribute value");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20219), "Invalid 'areaUnits' attribute value");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20220), "Invalid 'lengthUnits' attribute value");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20221), "Invalid 'extentUnits' attribute value");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20222), "Invalid attribute found on the Model object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20223), "Invalid attribute found on the ListOfFunctionDefinitions object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20224), "Invalid attribute found on the ListOfUnitDefinitions object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20225), "Invalid attribute found on the ListOfCompartments object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20226), "Invalid attribute found on the ListOfSpecies object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20227), "Invalid attribute found on the ListOfParameters object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20228), "Invalid attribute found on the ListOfInitialAssignments object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20229), "Invalid attribute found on the ListOfRules object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20230), "Invalid attribute found on the ListOfConstraints object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20231), "Invalid attribute found on the ListOfReactions object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20232), "Invalid attribute found on the ListOfEvents object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20233), "Invalid 'substanceUnits' attribute value");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20301), "Invalid expression found in the function definition");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20302), "Invalid forward reference in the MathML <apply><ci>...</ci></apply> expression");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20303), "Recursive function definitions are not permitted");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20304), "Invalid <ci> reference found inside the <lambda> mathematical formula");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20305), "A function's return type must be either a number or a Boolean");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20306), "A FunctionDefinition object may contain one <math> element");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20307), "Invalid attribute found on the FunctionDefinition object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20401), "Invalid 'id' attribute value for a UnitDefinition object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20402), "Invalid redefinition of built-in type 'substance'");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20403), "Invalid redefinition of built-in type 'length'");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20404), "Invalid redefinition of built-in type name 'area'");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20405), "Invalid redefinition of built-in type name 'time'");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20406), "Invalid redefinition of built-in type name 'volume'");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20407), "Must use 'exponent'=1 when defining 'volume' in terms of litres");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20408), "Must use 'exponent'=3 when defining 'volume' in terms of metres");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20409), "An empty list of Unit objects is not permitted in a UnitDefinition object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20410), "Invalid value for the 'kind' attribute of a UnitDefinition object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20411), "Unit attribute 'offset' is not supported in this Level+Version of SBML");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20412), "Unit name 'Celsius' is not defined in this Level+Version of SBML");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20413), "A ListOfUnits object must not be empty");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20414), "At most one ListOfUnits object is allowed inside a UnitDefinition object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20415), "Only Unit, Notes and Annotation objects are allowed in ListOfUnits objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20419), "Invalid attribute found on the UnitDefinition object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20420), "Invalid attribute found on the ListOfUnits object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20421), "Invalid attribute found on the Unit object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20501), "Invalid use of the 'size' attribute for a zero-dimensional compartment");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20502), "Invalid use of the 'units' attribute for a zero-dimensional compartment");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20503), "Zero-dimensional compartments must be defined to be constant");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20504), "Invalid value for the 'outside' attribute of a Compartment object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20505), "Recursive nesting of compartments via the 'outside' attribute is not permitted");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20506), "Invalid nesting of zero-dimensional compartments");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20507), "Invalid value for the 'units' attribute of a one-dimensional compartment");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20508), "Invalid value for the 'units' attribute of a two-dimensional compartment");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20509), "Invalid value for the 'units' attribute of a three-dimensional compartment");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20510), "Invalid value for the 'compartmentType' attribute of a compartment");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20511), "No units defined for 1-D compartment");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20512), "No units defined for 2-D compartment");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20513), "No units defined for 3-D Compartment object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20517), "Invalid attribute found on Compartment object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20518), "No units defined for Compartment object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20601), "Invalid value found for Species 'compartment' attribute");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20602), "Attribute 'spatialSizeUnits' must not be set if 'hasOnlySubstanceUnits'='true'");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20603), "Attribute 'spatialSizeUnits' must not be set if the compartment is zero-dimensional");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20604), "Attribute 'initialConcentration' must not be set if the compartment is zero-dimensional");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20605), "Invalid value for 'spatialSizeUnits' attribute of a one-dimensional compartment");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20606), "Invalid value for the 'spatialSizeUnits' attribute of a two-dimensional compartment");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20607), "Invalid value for the 'spatialSizeUnits' attribute of a three-dimensional compartment");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20608), "Invalid value for a Species 'units' attribute");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20609), "Cannot set both 'initialConcentration' and 'initialAmount' attributes simultaneously");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20610), "Cannot use a non-boundary species in both reactions and rules simultaneously");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20611), "Cannot use a constant, non-boundary species as a reactant or product");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20612), "Invalid value for the 'speciesType' attribute of a species");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20613), "Cannot have multiple species of the same species type in the same compartment");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20614), "Missing value for the 'compartment' attribute");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20615), "Attribute 'spatialSizeUnits' is not supported in this Level+Version of SBML");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20616), "No substance units defined for the species");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20617), "Invalid value for the 'conversionFactor' attribute");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20623), "Invalid attribute found on Species object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20701), "Invalid value for the 'units' attribute of a Parameter object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20702), "No units defined for the parameter");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20705), "A conversion factor must reference a Parameter object declared to be a constant");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20706), "Invalid attribute found on Parameter object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20801), "Invalid value for the 'symbol' attribute of an InitialAssignment object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20802), "Multiple initial assignments for the same 'symbol' value are not allowed");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20803), "Cannot set a value using both an initial assignment and an assignment rule simultaneously");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20804), "An InitialAssignment object may contain one <math> element");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20805), "Invalid attribute found on an InitialAssignment object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20806), "InitialAssignment cannot reference 0D compartment");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20901), "Invalid value for the 'variable' attribute of an AssignmentRule object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20902), "Invalid value for the 'variable' attribute of a RateRule object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20903), "An assignment rule cannot assign an entity declared to be constant");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20904), "A rate rule cannot assign an entity declared to be constant");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20905), "");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20906), "Circular dependencies involving rules and reactions are not permitted");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20907), "A rule object may contain one <math> element");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20908), "Invalid attribute found on an AssignmentRule object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20909), "Invalid attribute found on a RateRule object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20910), "Invalid attribute found on an AlgebraicRule object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20911), "Rule cannot reference 0D compartment");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21001), "A Constraint object's <math> must evaluate to a Boolean value");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21002), "Subobjects inside the Constraint object are not in the prescribed order");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21003), "A Constraint's Message subobject must be in the XHTML XML namespace");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21004), "XML declarations are not permitted within Constraint's Message objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21005), "XML DOCTYPE elements are not permitted within Constraint's Message objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21006), "Invalid content for a Constraint object's Message object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21007), "A Constraint object may contain one <math> element");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21008), "A Constraint object can only have one Message subobject");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21009), "Invalid attribute found on Constraint object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21101), "Cannot have a reaction with neither reactants nor products");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21102), "Subobjects inside the Reaction object are not in the prescribed order");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21103), "Reaction components, if present, cannot be empty");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21104), "Invalid object found in the list of reactants or products");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21105), "Invalid object found in the list of modifiers");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21106), "A Reaction object can only contain one of each allowed type of object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21107), "Invalid value for the Reaction 'compartment' attribute");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21110), "Invalid attribute for a Reaction object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21111), "Invalid 'species' attribute value in SpeciesReference object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21112), "");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21113), "The 'stoichiometry' attribute and StoichiometryMath subobject are mutually exclusive");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21116), "Invalid attribute found on the SpeciesReference object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21117), "Invalid attribute found on the ModifierSpeciesReference object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21121), "Unknown species referenced in the kinetic law <math> formula");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21122), "Incorrect ordering of components in the KineticLaw object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21123), "The list of parameters, if present, cannot be empty");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21124), "Parameters local to a KineticLaw object must have a 'constant' attribute value of 'true'");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21125), "Attribute 'substanceUnits' is not supported in this Level+Version of SBML");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21126), "Attribute 'timeUnits' is not supported in this Level+Version of SBML");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21127), "Only one ListOfLocalParameters object is permitted within a KineticLaw object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21128), "Only LocalParameter, Notes and Annotation objects are allowed in ListOfLocalParameter objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21129), "Invalid attribute found on the ListOfLocalParameters object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21130), "Only one <math> element is allowed in a KineticLaw object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21131), "Unknown species referenced in the StoichiometryMath object's <math> formula");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21132), "Invalid attribute found on the KineticLaw object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21150), "Invalid attribute found on the ListOfSpeciesReferences object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21151), "Invalid attribute found on the ListOfModifiers object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21152), "Fast attribute deprecated");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21172), "Invalid attribute found on the LocalParameter object");
        
        contents.put(Integer.toString(SBMLErrorCodes.CORE_21173), "Invalid attribute value found on the LocalParameter object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21201), "The Event object is missing a Trigger subobject");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21202), "A Trigger object's <math> expression must evaluate to a Boolean value");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21203), "The Event object is missing an EventAssignment subobject");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21204), "Units referenced by 'timeUnits' attribute are not compatible with units of time");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21205), "Incorrect ordering of components in Event object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21206), "Attribute 'useValuesFromTriggerTime'='false', but the Event object does not define a delay");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21207), "The use of a Delay object requires the Event attribute 'useValuesFromTriggerTime'");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21209), "A Trigger object may have one <math> element");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21210), "A Delay object may have one <math> element");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21211), "Invalid 'variable' attribute value in Event object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21212), "An EventAssignment object cannot assign to a component having attribute 'constant'='true'");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21213), "An EventAssignment object may have one <math> element");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21214), "Invalid attribute found on the EventAssignment object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21221), "An Event object can only have one Delay subobject");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21222), "An Event object can only have one ListOfEventAssignments subobject");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21223), "Only EventAssignment, Notes and Annotation objects are allowed in ListOfEventAssignments");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21224), "Invalid attribute found on the ListOfEventAssignments object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21225), "Invalid attribute found on the Event object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21226), "Invalid attribute found on the Trigger object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21227), "Invalid attribute found on the Delay object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21228), "The Trigger attribute 'persistent' must evaluate to a Boolean value");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21229), "The Trigger attribute 'initialValue' must evaluate to a Boolean value");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21230), "An Event object can only have one Priority subobject");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21231), "A Priority object may have one <math> element");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21232), "Invalid attribute found on the Priority object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_29999), "Unknown error");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_80501), "It's best to define a size for every compartment in a model");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_80601), "It's best to define an initial amount or initial concentration for every species in a model");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_80701), "It's best to declare units for every parameter in a model");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_80702), "It's best to declare values for every parameter in a model");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_81121), "Local parameters defined within a kinetic law shadow global object symbols");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_90000), "Lower boundary of libSBML-specific diagnostic codes");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_90001), "Cannot convert to SBML Level 1 Version 1");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91001), "SBML Level 1 does not support events");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91002), "SBML Level 1 does not support function definitions");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91003), "SBML Level 1 does not support constraints");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91004), "SBML Level 1 does not support initial assignments");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91005), "SBML Level 1 does not support species types");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91006), "SBML Level 1 does not support compartment types");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91007), "SBML Level 1 only supports three-dimensional compartments");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91008), "SBML Level 1 does not support non-integer nor non-rational stoichiometry formulas");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91009), "SBML Level 1 does not support non-integer 'stoichiometry' attribute values");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91010), "SBML Level 1 does not support multipliers or offsets in unit definitions");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91011), "In SBML Level 1, a value for 'compartment' is mandatory in species definitions");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91012), "SBML Level 1 does not support species 'spatialSizeUnits' settings");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91013), "SBML Level 1 does not support the 'sboTerm' attribute");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91014), "SBML Level 1 requires strict unit consistency");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91015), "SBML Level 1 does not support the 'conversionFactor' attribute");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91016), "SBML Level 1 does not support the 'compartment' attribute on Reaction objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91017), "Units of extent must be compatible with units of substance");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91018), "Global units must be refer to unit kind or unitDefinition.");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91019), "The concept of hasOnlySubstanceUnits was not available in SBML Level 1.");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91020), "Avogadro not supported in Levels 2 and 1.");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_92001), "SBML Level 2 Version 1 does not support Constraint objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_92002), "SBML Level 2 Version 1 does not support InitialAssignment objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_92003), "SBML Level 2 Version 1 does not support SpeciesType objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_92004), "SBML Level 2 Version 1 does not support CompartmentType objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_92005), "SBML Level 2 Version 1 does not support the 'sboTerm' attribute");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_92006), "SBML Level 2 Version 1 does not support the 'id' attribute on SpeciesReference objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_92007), "SBML Level 2 Version 1 does not support the 'useValuesFromTriggerTime' attribute");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_92008), "SBML Level 2 Version 1 requires strict unit consistency");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_92009), "SBML Level 2 Version 1 requires that compartments have spatial dimensions of 0-3");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_92010), "Conversion to StoichiometryMath objects not yet supported");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_92011), "SBML Level 2 Version 1 does not support priorities on Event objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_92012), "SBML Level 2 Version 1 does not support the 'persistent' attribute on Trigger objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_92013), "SBML Level 2 Version 1 does not support the 'initialValue' attribute on Trigger objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_93001), "The 'sboTerm' attribute is invalid for this component in SBML Level 2 Version 2");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_93002), "This Level+Version of SBML does not support the 'offset' attribute on Unit objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_93003), "This Level+Version of SBML does not support the 'timeUnits' attribute on KineticLaw objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_93004), "This Level+Version of SBML does not support the 'substanceUnits' attribute on KineticLaw objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_93005), "This Level+Version of SBML does not support the 'useValuesFromTriggerTime' attribute");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_93006), "The allowable 'sboTerm' attribute values for Model objects differ for this SBML Level+Version");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_93007), "SBML Level 2 Version 2 requires strict unit consistency");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_93008), "SBML Level 2 Version 2 requires strict SBO term consistency");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_93009), "Duplicate top-level annotations are invalid in SBML Level 2 Version 2");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_94001), "This Level+Version of SBML does not support the 'offset' attribute on Unit objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_94002), "This Level+Version of SBML does not support the 'timeUnits' attribute on KineticLaw objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_94003), "This Level+Version of SBML does not support the 'substanceUnits' attribute on KineticLaw objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_94004), "This Level+Version of SBML does not support the 'spatialSizeUnit' attribute on Species objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_94005), "This Level+Version of SBML does not support the 'timeUnits' attribute on Event objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_94006), "This Level+Version of SBML does not support the 'useValuesFromTriggerTime' attribute");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_94007), "The allowable 'sboTerm' attribute values for Model objects differ for this SBML Level+Version");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_94008), "SBML Level 2 Version 3 requires strict unit consistency");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_94009), "SBML Level 2 Version 3 requires strict SBO term consistency");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_94010), "Duplicate top-level annotations are invalid in SBML Level 2 Version 3");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_95001), "This Level+Version of SBML does not support the 'offset' attribute on Unit objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_95002), "This Level+Version of SBML does not support the 'timeUnits' attribute on KineticLaw objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_95003), "This Level+Version of SBML does not support the 'substanceUnits' attribute on KineticLaw objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_95004), "This Level+Version of SBML does not support the 'spatialSizeUnit' attribute on Species objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_95005), "This Level+Version of SBML does not support the 'timeUnits' attribute on Event objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_95006), "The allowable 'sboTerm' attribute values for Model objects differ for this SBML Level+Version");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_95007), "Duplicate top-level annotations are invalid in SBML Level 2 Version 4");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_96001), "SBML Level 3 Version 1 does not support SpeciesType objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_96002), "SBML Level 3 Version 1 does not support CompartmentType objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_96003), "This Level+Version of SBML does not support the 'offset' attribute on Unit objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_96004), "This Level+Version of SBML does not support the 'timeUnits' attribute on KineticLaw objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_96005), "This Level+Version of SBML does not support the 'substanceUnits' attribute on KineticLaw objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_96006), "This Level+Version of SBML does not support the 'spatialSizeUnit' attribute on Species objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_96007), "This Level+Version of SBML does not support the 'timeUnits' attribute on Event objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_96008), "The allowable 'sboTerm' attribute values for Model objects differ for this SBML Level+Version");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_96009), "Duplicate top-level annotations are invalid in SBML Level 3 Version 1");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_96010), "This Level+Version of SBML does not support the 'outside' attribute on Compartment objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_96011), "This Level+Version of SBML does not support the StoichiometryMath object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99101), "Unknown Level+Version combination of SBML");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99104), "Annotation objects on the SBML container element are not permitted in SBML Level 1");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99106), "Invalid ordering of rules");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99107), "The SBML document requires an SBML Level 3 package unavailable in this software");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99108), "The SBML document uses an SBML Level 3 package unavailable in this software");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99109), "This package expects required to be false");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99127), "Disallowed value for attribute 'substanceUnits' on KineticLaw object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99128), "Disallowed value for attribute 'timeUnits' on KineticLaw object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99129), "Only predefined functions are allowed in SBML Level 1 formulas");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99130), "Invalid 'substanceUnits' attribute value");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99206), "This Level+Version of SBML does not support the 'timeUnits' attribute on Event objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99219), "Invalid MathML expression");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99220), "Missing or invalid floating-point number in MathML expression");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99221), "Missing or invalid integer in MathML expression");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99222), "Missing or invalid exponential expression in MathML");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99223), "Missing or invalid rational expression in MathML");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99224), "Invalid MathML element");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99225), "Invalid MathML attribute");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99301), "Use of <csymbol> for 'time' not allowed within FunctionDefinition objects");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99302), "There must be a <lambda> body within the <math> element of a FunctionDefinition object");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99303), "Units must refer to valid unit or unitDefinition");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99401), "RDF missing the <about> tag");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99402), "RDF empty <about> tag");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99403), "RDF <about> tag is not metaid");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99404), "RDF does not contain valid ModelHistory");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99405), "RDF does not result in a ModelHistory");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99406), "Annotation must contain element");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99407), "Nested annotations not allowed");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99505), "Missing unit declarations on parameters or literal numbers in expression");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99506), "Unable to verify consistency of units: the unit of time has not been declared");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99507), "Unable to verify consistency of units: the units of reaction extent have not been declared");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99508), "Unable to verify consistency of units: encountered a model entity with no declared units");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99701), "Unrecognized 'sboTerm' attribute value");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99702), "Obsolete 'sboTerm' attribute value");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99901), "In SBML Level 1, only three-dimensional compartments are allowed");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99902), "CompartmentType objects are not available in this Level+Version of SBML");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99903), "This Level+Version of SBML does not support the 'constant' attribute on this component");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99904), "Attribute 'metaid' is not available in SBML Level 1");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99905), "The 'sboTerm' attribute is not available on this component before SBML Level 2 Version 3");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99906), "Invalid units for a compartment in SBML Level 1");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99907), "In SBML Level 1, a compartment's volume must be specified");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99908), "CompartmentType objects are not available in this Level+Version of SBML");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99909), "Constraint objects are not available in this Level+Version of SBML");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99910), "Event objects are not available in this Level+Version of SBML");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99911), "The 'sboTerm' attribute is invalid for this component before Level 2 Version 2");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99912), "FunctionDefinition objects are not available in this Level+Version of SBML");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99913), "InitialAssignment objects are not available in this Level+Version of SBML");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99914), "Attribute 'variable' is not available on this component in this Level+Version of SBML");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99915), "Attribute 'units' is not available on this component in this Level+Version of SBML");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99916), "Attribute 'constant' is not available on Species objects in SBML Level 1");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99917), "Attribute 'spatialSizeUnits' is not available on Species objects in SBML Level 1");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99918), "Attribute 'speciesType' is not available on Species objects in SBML Level 1");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99919), "Attribute 'hasOnlySubstanceUnits' is not available on Species objects in SBML Level 1");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99920), "Attribute 'id' is not available on SpeciesReference objects in SBML Level 1");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99921), "Attribute 'name' is not available on SpeciesReference objects in SBML Level 1");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99922), "The SpeciesType object is not supported in SBML Level 1");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99923), "The StoichiometryMath object is not supported in SBML Level 1");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99924), "Attribute 'multiplier' on Unit objects is not supported in SBML Level 1");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99925), "Attribute 'offset' on Unit objects is only available in SBML Level 2 Version 1");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99926), "No value given for 'spatialDimensions' attribute; assuming a value of 3");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99994), "Encountered an unknown attribute in the SBML Core namespace");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99995), "Encountered an unknown attribute in an SBML Level 3 package namespace");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99996), "Conversion of SBML Level 3 package constructs is not yet supported");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99997), "The requested SBML Level/Version combination is not known to exist");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99998), "SBML Level 3 is not yet supported");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99999), "");
        
        
        contents.put(Integer.toString(SBMLErrorCodes.CORE_10223), "A 'rateOf' <csymbol> must target only a single <ci> element");
        contents.put(Integer.toString(SBMLErrorCodes.CORE_10224), "The target of a 'rateOf' <csymbol> cannot be assigned");
        contents.put(Integer.toString(SBMLErrorCodes.CORE_10225), "Compartments of non-substance <species> referenced by 'rateOf' must not vary");
        

        contents.put(Integer.toString(SBMLErrorCodes.COMP_10100), "");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_10101), "The comp ns is not correctly declared");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_10102), "Element not in comp namespace");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_10301), "Duplicate 'id' attribute value");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_10302), "Model and ExternalModelDefinitions must have unique ids");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_10303), "Ports must have unique ids");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_10304), "Invalid SId syntax");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_10308), "Invalid submodelRef syntax");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_10309), "Invalid deletion syntax");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_10310), "Invalid conversionFactor syntax");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_10311), "Invalid name syntax");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_10501), "Units of replaced elements should match replacement units.");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20101), "Only one <listOfReplacedElements> allowed.");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20102), "Allowed children of <listOfReplacedElements>");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20103), "Allowed <listOfReplacedElements> attributes");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20104), "<listOfReplacedElements> must not be empty");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20105), "Only one <replacedBy> object allowed.");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20201), "Required comp:required attribute on <sbml>");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20202), "The comp:required attribute must be Boolean");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20203), "The comp:required attribute must be 'true' if math changes");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20204), "The comp:required attribute must be 'false' if math does not change");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20205), "Only one <listOfModelDefinitions> allowed.");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20206), "<listOfModelDefinitions> and <listOfExternalModelDefinitions> must not be empty");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20207), "Only <modelDefinitions> in <listOfModelDefinitions>");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20208), "Only <externalModelDefinitions> in <listOfExternalModelDefinitions>");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20209), "Allowed <listOfModelDefinitions> attributes");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20210), "Allowed <listOfExternalModelDefinitions> attributes");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20211), "Only one <listOfExternalModelDefinitions> allowed.");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20212), "The comp:required attribute must be 'true'");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20301), "Allowed <externalModelDefinitions> core attributes");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20302), "Allowed <externalModelDefinitions> elements");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20303), "Allowed <externalModelDefinitions> attributes");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20304), "External models must be L3");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20305), "'modelRef' must be the 'id' of a model in the 'source' document");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20306), "MD5 checksum does not match the 'source' document");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20307), "The 'comp:source' attribute must be of type 'anyURI'");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20308), "The 'comp:modelRef' attribute must have the syntax of 'SId'");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20309), "The 'comp:md5' attribute must have the syntax of 'string'");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20310), "Circular reference in <externalModelDefinition>");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20501), "Only one <listOfSubmodels> and one <listOfPorts> allowed");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20502), "No empty listOf elements allowed");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20503), "Allowed elements on <listOfSubmodels>");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20504), "Allowed elements on <listOfPorts>");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20505), "Allowed attributes on <listOfSubmodels>");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20506), "Allowed attributes on <listOfPorts>");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20601), "Allowed core attributes on <submodel>");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20602), "Allowed elements on <submodel>");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20603), "Only one <listOfDeletions> on a <submodel> allowed");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20604), "No empty listOfDeletions elements allowed");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20605), "Allowed elements on <listOfDeletions>");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20606), "Allowed <listOfDeletions> attributes");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20607), "Allowed <submodel> attributes");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20608), "'comp:modelRef' must conform to SId syntax");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20613), "'comp:timeConversionFactor' must conform to SId syntax");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20614), "'comp:extentConversionFactor' must conform to SId syntax");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20615), "The 'comp:modelRef' attribute must reference a model");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20616), "The 'comp:modelRef' attribute cannot reference own model");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20617), "<model> may not reference <submodel> that references itself.");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20622), "The 'comp:timeConversionFactor' must reference a parameter");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20623), "The 'comp:extentConversionFactor' must reference a parameter");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20701), "The 'comp:portRef' attribute must be the 'id' of a <port>");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20702), "The 'comp:idRef' attribute must be the 'id' of a model element");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20703), "The 'comp:unitRef' attribute must be the 'id' of a UnitDefinition");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20704), "The 'comp:metaIdRef' attribute must be the 'metaid' of an object");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20705), "If <sBaseRef> has a child <sBaseRef> its parent must be a <submodel>");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20706), "The 'comp:portRef' attribute must have the syntax of an SBML SId");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20707), "The 'comp:idRef' attribute must have the syntax of an SBML SId");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20708), "The 'comp:unitRef' attribute must have the syntax of an SBML SId");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20709), "The 'comp:metaIdRef' attribute must have the syntax of an XML ID");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20710), "Only one <sbaseRef>");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20711), "The spelling 'sbaseRef' is deprecated");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20712), "An SBaseRef must reference an object.");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20713), "An SBaseRef must reference only one other object.");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20714), "Objects may not be referenced by mutiple SBaseRef constructs.");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20801), "Port must reference an object");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20802), "Port must reference only one other object.");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20803), "Allowed attributes on a Port");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20804), "Port definitions must be unique.");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20901), "Deletion must reference an object");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20902), "Deletion must reference only one other object.");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20903), "Allowed attributes on a Deletion");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_21001), "ReplacedElement must reference an object");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_21002), "ReplacedElement must reference only one other object.");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_21003), "Allowed attributes on <replacedElement>");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_21004), "The 'comp:submodelRef' attribute must point to a <submodel>");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_21005), "The 'comp:deletion' attribute must point to a <deletion>");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_21006), "The 'comp:conversionFactor attribute must point to a <parameter>");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_21010), "No <replacedElement> refer to same object");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_21011), "No <replacedElement> with deletion and conversionfactor");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_21101), "ReplacedBy must reference an object");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_21102), "ReplacedBy must reference only one other object.");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_21103), "Allowed attributes on <replacedBy>");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_21104), "The 'comp:submodelRef' attribute must point to a <submodel>");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_21201), "Replaced classes must match.");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_21202), "Replaced IDs must be replaced with IDs.");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_21203), "Replaced metaids must be replaced with metaids.");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_21204), "Replaced package IDs must be replaced with package IDs.");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_90101), "Unresolved reference.");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_90102), "No model in referenced document.");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_90103), "Referenced <externalModelDefinition> unresolvable.");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_90104), "Model failed to flatten.");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_90105), "Flat model not valid.");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_90106), "Line numbers unreliable.");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_90107), "Flattening not implemented for required package.");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_90108), "Flattening not implemented for unrequired package.");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_90109), "Flattening not implemented for unrequired package.");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_90110), "Flattening not implemented for required package.");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_90111), "Flattening reference may come from package.");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_90112), "The performDeletions functions is deprecated.");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_90113), "The performReplacementsAndConversions fuctions is deprecated.");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_90114), "Element deleted before a subelement could be replaced.");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_90115), "The 'comp:idRef' attribute must be the 'id' of a model element");

        contents.put(Integer.toString(SBMLErrorCodes.COMP_90116), "The 'comp:metaIdRef' attribute must be the 'metaid' of a model element");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_10100), "");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_10101), "The fbc ns is not correctly declared");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_10102), "Element not in fbc namespace");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_10301), "Duplicate 'id' attribute value");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_10302), "Invalid 'id' attribute");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20101), "Required fbc:required attribute on <sbml>");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20102), "The fbc:required attribute must be Boolean");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20103), "The fbc:required attribute must be 'false'");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20201), "One of each list of allowed");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20202), "ListOf elements cannot be empty");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20203), "Allowed elements on ListOfFluxBounds");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20204), "Allowed elements on ListOfObjectives");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20205), "Allowed attributes on ListOfFluxBounds");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20206), "Allowed attributes on ListOfObjectives");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20207), "Type of activeObjective attribute");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20208), "ActiveObjective must reference Objective");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20209), "'Strict' attribute required on <model>");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20210), "'Strict' attribute must be boolean");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20211), "Allowed elements on ListOfGeneProducts");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20212), "Allowed attributes on ListOfGeneProducts");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20301), "Species allowed attributes");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20302), "Charge must be integer");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20303), "Chemical formula must be string");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20401), "<fluxBound> may only have 'metaId' and 'sboTerm' from L3 namespace");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20402), "<fluxBound> may only have <notes> and <annotations> from L3 Core");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20403), "Invalid attribute found on <fluxBound> object");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20404), "Datatype for 'fbc:reaction' must be SIdRef");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20405), "The attribute 'fbc:name' must be of the data type string");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20406), "The attribute 'fbc:operation' must be of data type FbcOperation");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20407), "The attribute 'fbc:value' must be of the data type double");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20408), "'fbc:reaction' must refer to valid reaction");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20409), "Conflicting set of FluxBounds for a reaction");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20501), "<objective> may only have 'metaId' and 'sboTerm' from L3 namespace");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20502), "<objective> may only have <notes> and <annotations> from L3 Core");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20503), "Invalid attribute found on <objective> object");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20504), "The attribute 'fbc:name' must be of the data type string");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20505), "The attribute 'fbc:type' must be of data type FbcType.");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20506), "An <objective> must have one <listOfFluxObjectives>.");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20507), "<listOfFluxObjectives> subobject must not be empty");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20508), "Invalid element found in <listOfFluxObjectives>");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20509), "<listOfFluxObjectives> may only have 'metaId' and 'sboTerm' from L3 core");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20601), "<fluxObjective> may only have 'metaId' and 'sboTerm' from L3 namespace");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20602), "<fluxObjective> may only have <notes> and <annotations> from L3 Core");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20603), "Invalid attribute found on <fluxObjective> object");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20604), "The attribute 'fbc:name' must be of the data type string");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20605), "Datatype for 'fbc:reaction' must be SIdRef");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20606), "'fbc:reaction' must refer to valid reaction");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20607), "The attribute 'fbc:coefficient' must be of the data type double");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20608), "The 'fbc:coefficient' must be declared when strict");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20701), "One GeneProductAssociation in a Reaction");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20702), "Fbc attributes on a Reaction");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20703), "LowerBound must be SIdRef");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20704), "UpperBound must be SIdRef");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20705), "LowerBound must point to Parameter");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20706), "UpperBound must point to Parameter");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20707), "Reaction must have bounds when strict");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20708), "Reaction bounds constant when strict");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20709), "Reaction bounds must have values when strict");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20710), "Reaction bounds not assigned when strict");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20711), "LowerBound cannot be INF when strict");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20712), "UpperBound cannot be -INF when strict");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20713), "LowerBound less than upperBound when strict");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20714), "SpeciesReferences must be constant when strict");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20715), "Stoichiometry of SpeciesReferences must be real valued when strict");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20716), "SpeciesReference not target of InitialAssignment when strict");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20801), "Allowed core attributes on <GeneProductAssociation>");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20802), "Allowed core elements on <GeneProductAssociation>");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20803), "Allowed fbc attributes on <GeneProductAssociation>");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20804), "'fbc:id' must have SId syntax");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20805), "<GeneProductAssociation> must contain one concrete object");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20806), "'fbc:name' must be string");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20901), "Allowed core attributes on <GeneProductRef>");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20902), "Allowed core elements on <GeneProductRef>");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20903), "Allowed fbc attributes on <GeneProductRef>");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20904), "'fbc:geneProduct' must be SIdRef");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20908), "'fbc:geneProduct' must point to existing <GeneProduct>");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_21001), "Allowed core attributes on <And>");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_21002), "Allowed core attributes on <And>");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_21003), "<And> must have at least two child elements");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_21101), "Allowed core attributes on <Or>");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_21102), "Allowed core elements on <Or>");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_21103), "<Or> must have at least two child elements");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_21201), "Allowed core attributes on <GeneProduct>");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_21202), "Allowed core elements on <GeneProduct>");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_21203), "Allowed fbc attributes on <GeneProduct>");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_21204), "'fbc:label' must be string");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_21205), "'fbc:label' must be unique");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_21206), "'fbc:name' must be string");

        contents.put(Integer.toString(SBMLErrorCodes.FBC_21207), "'fbc:associatedSpecies' must point to existing <species>");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_10100), "");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_10101), "The qual ns is not correctly declared");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_10102), "Element not in qual namespace");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_10201), "FunctionTerm should return boolean");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_10202), "CSymbol time or delay not allowed");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_10301), "Duplicate 'id' attribute value");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20101), "Required qual:required attribute on <sbml>");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20102), "The qual:required attribute must be Boolean");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20103), "The qual:required attribute must be 'true' if math changes");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20201), "Only one <listOfTransitions> or <listOfQualitativeSpecies> allowed.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20202), "Empty <listOfTransitions> or <listOfQualitativeSpecies> not allowed.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20203), "Elements allowed on <listOfTransitions>.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20204), "Elements allowed on <listOfTransitions>.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20205), "Attributes allowed on <listOfQualitativeSpecies>.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20206), "Attributes allowed on <listOfTransitions>.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20301), "Core attributes allowed on <qualitativeSpecies>.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20302), "Elements allowed on <qualitativeSpecies>.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20303), "Attributes allowed on <qualitativeSpecies>.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20304), "Attribute 'constant' on <qualitativeSpecies> must be boolean.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20305), "Attribute 'name' on <qualitativeSpecies> must be string.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20306), "Attribute 'initialLevel' on <qualitativeSpecies> must be integer.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20307), "Attribute 'maxLevel' on <qualitativeSpecies> must be integer.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20308), "Attribute 'compartment' on <qualitativeSpecies> must reference compartment.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20309), "Attribute 'initialLevel' on <qualitativeSpecies> cannot exceed maxLevel.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20310), "Constant <qualitativeSpecies> cannot be an Output.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20311), "A <qualitativeSpecies> can only be assigned once.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20312), "Attribute 'initialLevel' on <qualitativeSpecies> cannot be negative.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20313), "Attribute 'maxLevel' on <qualitativeSpecies> cannot be negative.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20401), "Core attributes allowed on <transition>.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20402), "Elements allowed on <transition>.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20403), "Attributes allowed on <transition>.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20404), "Attribute 'name' on <transition> must be string.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20405), "ListOf elements on <transition>.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20406), "ListOf elements on <transition> not empty.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20407), "Elements on <listOfInputs>.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20408), "Elements on <listOfOutputs>.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20409), "Elements on <listOfFunctionTerms>.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20410), "Attributes allowed on <listOfInputs>.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20411), "Attributes allowed on <listOfOutputs>.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20412), "Attributes allowed on <listOfFunctionTerms>.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20413), "<listOfFunctionTerms> cannot make qualitativeSpecies exceed maxLevel.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20414), "<listOfFunctionTerms> cannot make qualitativeSpecies negative.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20501), "Core attributes allowed on <input>.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20502), "Elements allowed on <input>.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20503), "Attributes allowed on <input>.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20504), "Attribute 'name' on <input> must be string.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20505), "Attribute 'sign' on <input> must be enum.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20506), "Attribute 'transitionEffect' on <input> must be enum.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20507), "Attribute 'thresholdLevel' on <input> must be non negative integer.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20508), "Attribute 'qualitativeSpecies' on <input> must refer to existing");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20509), "Constant <input> cannot be consumed.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20510), "Attribute 'thresholdLevel' on <input> must be non negative integer.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20601), "Core attributes allowed on <output>.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20602), "Elements allowed on <output>.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20603), "Attributes allowed on <output>.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20604), "Attribute 'name' on <output> must be string.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20605), "Attribute 'transitionEffect' on <output> must be enum.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20606), "Attribute 'outputLevel' on <output> must be non negative integer.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20607), "Attribute 'qualitativeSpecies' on <output> must refer to existing");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20608), "Constant 'qualitativeSpecies' cannot be <output>");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20609), "<output> being produced must have level");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20610), "Attribute 'outputLevel' on <output> must be non negative integer.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20701), "Core attributes allowed on <defaultTerm>.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20702), "Elements allowed on <defaultTerm>.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20703), "Attributes allowed on <defaultTerm>.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20704), "Attribute 'resultLevel' on <defaultTerm> must be non negative integer.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20705), "Attribute 'resultLevel' on <defaultTerm> must be non negative integer.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20801), "Core attributes allowed on <functionTerm>.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20802), "Elements allowed on <functionTerm>.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20803), "Attributes allowed on <functionTerm>.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20804), "Only one <math> on <functionTerm>.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20805), "Attribute 'resultLevel' on <functionTerm> must be non negative integer.");

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20806), "Attribute 'resultLevel' on <functionTerm> must be non negative integer.");

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_10100), "");

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_10101), "The Groups namespace is not correctly declared.");

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_10102), "Element not in Groups namespace");

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_10301), "Duplicate 'id' attribute value");

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_10302), "Invalid SId syntax");

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20101), "Required groups:required attribute on <sbml>");

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20102), "The groups:required attribute must be Boolean");

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20103), "The groups:required attribute must be 'false'");

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20201), "Elements allowed on <model>.");

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20202), "No Empty ListOf elements allowed on <Model>.");

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20203), "Core elements allowed on <model>.");

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20204), "Core attributes allowed on <model>.");

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20301), "Core attributes allowed on <group>.");

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20302), "Core elements allowed on <group>.");

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20303), "Attributes allowed on <group>.");

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20304), "Elements allowed on <group>.");

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20305), "Kind attribute must be GroupKindEnum.");

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20306), "Name attribute must be String.");

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20307), "No Empty ListOf elements allowed on <group>.");

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20308), "Core elements allowed on <listOfMembers>.");

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20309), "Core attributes allowed on <listOfMembers>.");

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20310), "Attributes allowed on <listOfMembers>.");

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20311), "Name attribute must be String.");

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20312), "Consistent references by multiple <member> objects.");

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20313), "Circular references by multiple <member> objects.");

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20401), "Core attributes allowed on <member>.");

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20402), "Core elements allowed on <member>.");

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20403), "Attributes allowed on <member>.");

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20404), "Name attribute must be String.");

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20405), "Attribute 'idRef' must point to SBase object.");

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20406), "Attribute 'metaIdRef' must point to SBase object.");

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20407), "Attribute 'idRef' must be type 'SId'.");

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20408), "Attribute 'metaIdRef' must be type 'ID'.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_10100), "");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_10101), "The layout ns is not correctly declared");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_10102), "Element not in layout namespace");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_10301), "Duplicate 'id' attribute value");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_10302), "'id' attribute incorrect syntax");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_10401), "'xsi:type' allowed locations");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_10402), "'xsi:type' attribute incorrect syntax");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20101), "Required layout:required attribute on <sbml>");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20102), "The layout:required attribute must be Boolean");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20103), "The layout:required attribute must be 'false'");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20201), "Only one listOfLayouts on <model>");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20202), "ListOf elements cannot be empty");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20203), "Allowed elements on ListOfLayouts");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20204), "Allowed attributes on ListOfLayouts");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20301), "Allowed elements on Layout");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20302), "Allowed core attributes on Layout");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20303), "Only one each listOf on <layout>");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20304), "ListOf elements cannot be empty");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20305), "<layout> must have 'id' and may have 'name'");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20306), "'name' must be string");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20307), "Attributes allowed on <listOfCompartmentGlyphs>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20308), "Elements allowed on <listOfCompartmentGlyphs>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20309), "Attributes allowed on <listOfSpeciesGlyphs>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20310), "Elements allowed on <listOfSpeciesGlyphs>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20311), "Attributes allowed on <listOfReactionGlyphs>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20312), "Elements allowed on <listOfReactionGlyphs>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20313), "Attributes allowed on <listOfAdditionalGraphicalObjectGlyphs>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20314), "Elements allowed on <listOfAdditionalGraphicalObjectGlyphs>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20315), "Layout must have <dimensions>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20316), "Attributes allowed on <listOfTextGlyphs>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20317), "Elements allowed on <listOfTextGlyphs>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20401), "Core elements allowed on <graphicalObject>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20402), "Core attributes allowed on <graphicalObject>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20403), "Layout elements allowed on <graphicalObject>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20404), "Layout attributes allowed on <graphicalObject>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20405), "Layout 'metaidRef' must be IDREF.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20406), "Layout 'metaidRef' must reference existing object.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20407), "A <graphicalObject> must contain a <boundingBox>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20501), "Core elements allowed on <compartmentGlyph>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20502), "Core attributes allowed on <compartmentGlyph>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20503), "Layout elements allowed on <compartmentGlyph>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20504), "Layout attributes allowed on <compartmentGlyph>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20505), "Layout 'metaidRef' must be IDREF.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20506), "Layout 'metaidRef' must reference existing object.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20507), "CompartmentGlyph 'compartment' must have SIdRef syntax.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20508), "CompartmentGlyph compartment must reference existing compartment.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20509), "CompartmentGlyph cannot reference two objects.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20510), "CompartmentGlyph order must be double.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20601), "Core elements allowed on <speciesGlyph>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20602), "Core attributes allowed on <speciesGlyph>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20603), "Layout elements allowed on <speciesGlyph>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20604), "Layout attributes allowed on <speciesGlyph>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20605), "Layout 'metaidRef' must be IDREF.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20606), "Layout 'metaidRef' must reference existing object.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20607), "SpeciesGlyph 'species' must have SIdRef syntax.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20608), "SpeciesGlyph species must reference existing species.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20609), "SpeciesGlyph cannot reference two objects.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20701), "Core elements allowed on <reactionGlyph>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20702), "Core attributes allowed on <reactionGlyph>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20703), "Layout elements allowed on <reactionGlyph>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20704), "Layout attributes allowed on <reactionGlyph>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20705), "Layout 'metaidRef' must be IDREF.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20706), "Layout 'metaidRef' must reference existing object.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20707), "ReactionGlyph 'reaction' must have SIdRef syntax.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20708), "ReactionGlyph reaction must reference existing reaction.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20709), "ReactionGlyph cannot reference two objects.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20710), "Allowed elements on ListOfSpeciesReferenceGlyphs");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20711), "Allowed attributes on ListOfSpeciesReferenceGlyphs");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20712), "ListOfSpeciesReferenceGlyphs not empty");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20801), "Core elements allowed on <generalGlyph>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20802), "Core attributes allowed on <generalGlyph>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20803), "Layout elements allowed on <generalGlyph>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20804), "Layout attributes allowed on <generalGlyph>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20805), "Layout 'metaidRef' must be IDREF.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20806), "Layout 'metaidRef' must reference existing object.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20807), "GeneralGlyph 'reference' must have SIdRef syntax.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20808), "GeneralGlyph 'reference' must reference existing element.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20809), "GeneralGlyph cannot reference two objects.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20810), "Allowed elements on ListOfReferenceGlyphs");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20811), "Allowed attributes on ListOfReferenceGlyphs");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20812), "");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20813), "Allowed attributes on ListOfSubGlyphs");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20901), "Core elements allowed on <textGlyph>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20902), "Core attributes allowed on <textGlyph>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20903), "Layout elements allowed on <textGlyph>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20904), "Layout attributes allowed on <textGlyph>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20905), "Layout 'metaidRef' must be IDREF.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20906), "Layout 'metaidRef' must reference existing object.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20907), "TextGlyph 'originOfText' must have SIdRef syntax.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20908), "TextGlyph 'originOfText' must reference existing element.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20909), "TextGlyph cannot reference two objects.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20910), "TextGlyph 'graphicalObject' must have SIdRef syntax.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20911), "TextGlyph 'graphicalObject' must reference existing element.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20912), "TextGlyph 'text' must be string.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21001), "Core elements allowed on <speciesReferenceGlyph>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21002), "Core attributes allowed on <speciesReferenceGlyph>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21003), "Layout elements allowed on <speciesReferenceGlyph>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21004), "Layout attributes allowed on <speciesReferenceGlyph>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21005), "Layout 'metaidRef' must be IDREF.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21006), "Layout 'metaidRef' must reference existing object.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21007), "SpeciesReferenceGlyph 'speciesReference' must have SIdRef syntax.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21008), "SpeciesReferenceGlyph 'speciesReference' must reference existing element.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21009), "SpeciesReferenceGlyph cannot reference two objects.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21010), "SpeciesReferenceGlyph 'speciesGlyph' must have SIdRef syntax.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21011), "SpeciesReferenceGlyph 'speciesGlyph' must reference existing element.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21012), "SpeciesReferenceGlyph 'role' must be string from enumeration.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21101), "Core elements allowed on <referenceGlyph>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21102), "Core attributes allowed on <referenceGlyph>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21103), "Layout elements allowed on <referenceGlyph>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21104), "Layout attributes allowed on <referenceGlyph>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21105), "Layout 'metaidRef' must be IDREF.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21106), "Layout 'metaidRef' must reference existing object.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21107), "ReferenceGlyph 'reference' must have SIdRef syntax.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21108), "ReferenceGlyph 'reference' must reference existing element.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21109), "ReferenceGlyph cannot reference two objects.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21110), "ReferenceGlyph 'glyph' must have SIdRef syntax.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21111), "ReferenceGlyph 'glyph' must reference existing element.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21112), "ReferenceGlyph 'role' must be string.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21201), "Core elements allowed on <point>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21202), "Core attributes allowed on <point>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21203), "Layout attributes allowed on <point>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21204), "Layout 'x', 'y' and 'z' must be double.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21301), "Core elements allowed on <boundingBox>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21302), "Core attributes allowed on <boundingBox>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21303), "Layout elements allowed on <boundingBox>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21304), "Layout attributes allowed on <boundingBox>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21305), "Layout consistent dimensions on a <boundingBox>");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21401), "Core elements allowed on <curve>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21402), "Core attributes allowed on <curve>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21403), "Layout elements allowed on <curve>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21404), "Layout attributes allowed on <curve>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21405), "Allowed attributes on ListOfCurveSegments");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21406), "Allowed elements on ListOfCurveSegments");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21407), "No empty ListOfCurveSegments");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21501), "Core elements allowed on <lineSegment>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21502), "Core attributes allowed on <lineSegment>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21503), "Layout elements allowed on <lineSegment>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21504), "Layout attributes allowed on <lineSegment>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21601), "Core elements allowed on <cubicBezier>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21602), "Core attributes allowed on <cubicBezier>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21603), "Layout elements allowed on <cubicBezier>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21604), "Layout attributes allowed on <cubicBezier>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21701), "Core elements allowed on <dimensions>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21702), "Core attributes allowed on <dimensions>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21703), "Layout attributes allowed on <dimensions>.");

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21704), "Layout 'width', 'height' and 'depth' must be double.");
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
