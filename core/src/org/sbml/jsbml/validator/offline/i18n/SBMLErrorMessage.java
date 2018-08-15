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
 * Contains the messages for each {@link SBMLError} in the English language.
 * 
 * <p>The key for each message is the integer defined for each {@link SBMLError} in {@link SBMLErrorCodes}.</p>
 * 
 * <p>Automatically generated file, using the python scripts extractErrors.py on the libSBML source code
 * and createSBMLErrorMessageBundle.py on the generated json file.</p>
 *
 * @see ResourceBundle
 * @since 1.3
 */
public class SBMLErrorMessage extends ResourceBundle { 
 

  /**
   * 
   */
  private static final Map<String, String> contents = new HashMap<String, String>();
  
  static {
      
        contents.put(Integer.toString(SBMLErrorCodes.CORE_00000), "Unrecognized error encountered internally.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_00001), "Out of memory.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_00002), "File unreadable.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_00003), "File unwritable.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_00004), "Error encountered while attempting file operation.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_00005), "Network access error.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_00101), "Internal XML parser state error.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_00102), "XML parser returned an unrecognized error code.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_00103), "Character transcoder error.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01001), "Missing XML declaration at beginning of XML input.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01002), "Missing encoding attribute in XML declaration.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01003), "Invalid or unrecognized XML declaration or XML encoding.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01004), "Invalid, malformed or unrecognized XML DOCTYPE declaration.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01005), "Invalid character in XML content.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01006), "XML content is not well-formed.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01007), "Unclosed XML token.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01008), "XML construct is invalid or not permitted.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01009), "Element tag mismatch or missing tag.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01010), "Duplicate XML attribute.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01011), "Undefined XML entity.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01012), "Invalid, malformed or unrecognized XML processing instruction.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01013), "Invalid or undefined XML namespace prefix.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01014), "Invalid XML namespace prefix value.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01015), "Missing a required XML attribute.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01016), "Data type mismatch for the value of an attribute.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01017), "Invalid UTF8 content.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01018), "Missing or improperly formed attribute value.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01019), "Invalid or unrecognizable attribute value.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01020), "Invalid, unrecognized or malformed attribute.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01021), "Element either not recognized or not permitted.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01022), "Badly formed XML comment.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01023), "XML declaration not permitted in this location.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01024), "Reached end of input unexpectedly.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01025), "Value is invalid for XML ID, or has already been used.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01026), "XML ID value was never declared.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01027), "Unable to interpret content.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01028), "Bad XML document structure.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01029), "Encountered invalid content after expected content.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01030), "Expected to find a quoted string.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01031), "An empty value is not permitted in this context.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01032), "Invalid or unrecognized number.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01033), "Colon characters are invalid in this context.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01034), "One or more expected elements are missing.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_01035), "Main XML content is empty.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_09999), "");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10000), "Unrecognized error encountered by libSBML).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10101), "An SBML XML file must use UTF-8 as the character encoding. More precisely, the 'encoding' attribute of the XML declaration at the beginning of the XML data stream cannot have a value other than 'UTF-8'. An example valid declaration is '<?xml version=\"1.0\" encoding=\"UTF-8\"?>' (Reference: L3V1 Section 4.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10102), "An SBML XML document must not contain undefined elements or attributes in the SBML namespace. Documents containing unknown elements or attributes placed in the SBML namespace do not conform to the SBML specification (Reference: L3V1 Section 4.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10103), "An SBML XML document must conform to the XML Schema for the corresponding SBML Level, Version and Release. The XML Schema for SBML defines the basic SBML object structure, the data types used by those objects, and the order in which the objects may appear in an SBML document.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10104), "An SBML document must conform to the rules of XML well-formedness defined in the XML 1.0 specification. These rules define the basic structural and syntactic constraints with which all XML documents must comply.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10201), "All MathML content in SBML must appear within a <math> element, and the <math> element must be either explicitly or implicitly in the XML namespace \"http://www.w3.org/1998/Math/MathML\" (Reference: L3V1 Section 3.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10202), "The only permitted MathML 2.0 elements in SBML Level 2 are the following: <cn>, <ci>, <csymbol>, <sep>, <apply>, <piecewise>, <piece>, <otherwise>, <eq>, <neq>, <gt>, <lt>, <geq>, <leq>, <plus>, <minus>, <times>, <divide>, <power>, <root>, <abs>, <exp>, <ln>, <log>, <floor>, <ceiling>, <factorial>, <and>, <or>, <xor>, <not>, <degree>, <bvar>, <logbase>, <sin>, <cos>, <tan>, <sec>, <csc>, <cot>, <sinh>, <cosh>, <tanh>, <sech>, <csch>, <coth>, <arcsin>, <arccos>, <arctan>, <arcsec>, <arccsc>, <arccot>, <arcsinh>, <arccosh>, <arctanh>, <arcsech>, <arccsch>, <arccoth>, <true>, <false>, <notanumber>, <pi>, <infinity>, <exponentiale>, <semantics>, <annotation>, and <annotation-xml> (Reference: L3V1 Section 3.4.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10203), "In the SBML subset of MathML 2.0, the MathML attribute 'encoding' is only permitted on <csymbol>. No other MathML elements may have an 'encoding' attribute (Reference: L3V1 Section 3.4.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10204), "In the SBML subset of MathML 2.0, the MathML attribute 'definitionURL' is only permitted on <csymbol>, <semantics> or <ci> (Level 2 Version 5 and Level 3 only). No other MathML elements may have a 'definitionURL' attribute (Reference: L3V1 Section 3.4.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10205), "In SBML Level 2, the only values permitted for 'definitionURL' on a <csymbol> element are \"http://www.sbml.org/sbml/symbols/time\" and \"http://www.sbml.org/sbml/symbols/delay\". SBML Level 3 added \"http://www.sbml.org/sbml/symbols/avogadro\" (Reference: L3V1 Section 3.4.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10206), "In the SBML subset of MathML 2.0, the MathML attribute 'type' is only permitted on the <cn> construct. No other MathML elements may have a 'type' attribute (Reference: L3V1 Section 3.4.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10207), "The only permitted values for the 'type' attribute on MathML <cn> elements are 'e-notation', 'real', 'integer', and 'rational' (Reference: L3V1 Section 3.4.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10208), "MathML <lambda> elements are only permitted as the first element inside the 'math' element of a <functionDefinition> or as the first element of a semantics element immediately inside inside the math element of a <functionDefinition>; they may not be used elsewhere in an SBML model (Reference: L3V1 Sections 3.4.1 and 4.3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10209), "The arguments of the MathML logical operators <and>, <or>, <xor>, and <not> must have Boolean values (Reference: L3V1 Section 3.4.9).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10210), "The arguments to the following MathML constructs must have a numeric type: <plus>, <minus>, <times>, <divide>, <power>, <root>, <abs>, <exp>, <ln>, <log>, <floor>, <ceiling>, <factorial>, <sin>, <cos>, <tan>, <sec>, <csc>, <cot>, <sinh>, <cosh>, <tanh>, <sech>, <csch>, <coth>, <arcsin>, <arccos>, <arctan>, <arcsec>, <arccsc>, <arccot>, <arcsinh>, <arccosh>, <arctanh>, <arcsech>, <arccsch>, <arccoth> (Reference: L3V1 Section 3.4.9).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10211), "The values of all arguments to <eq> and <neq> operators must have the same type (either all Boolean or all numeric) (Reference: L3V1 Section 3.4.9).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10212), "The types of values within <piecewise> operators must all be consistent: the set of expressions that make up the first arguments of the <piece> and <otherwise> operators within the same <piecewise> operator should all return values of the same type (Reference: L3V1 Section 3.4.9).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10213), "The second argument of a MathML <piece> operator must have a Boolean value (Reference: L3V1 Section 3.4.9).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10214), "Outside of a <functionDefinition>, if a <ci> element is the first element within a MathML <apply>, then the <ci>'s value can only be chosen from the set of identifiers of <functionDefinition>s defined in the SBML model (Reference: L3V1 Section 4.3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10215), "Outside of a <functionDefinition>, if a <ci> element is not the first element within a MathML <apply>, then the <ci>'s value can only be chosen from the set of identifiers of <species>, <compartment>, <parameter>, <reaction>, or (in Level 3) <speciesReference> objects defined in the SBML model. (In L2V1, the <ci>'s value can't be chosen from the identifiers of <reaction> objects) (Reference: L3V1 Section 3.4.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10216), "The 'id' value of a <parameter> defined within a <kineticLaw> can only be used in <ci> elements within the MathML content of that same <kineticLaw>; the identifier is not visible to other parts of the model (Reference: L3V1 Sections 3.3.1, 3.4.3 and 4.13.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10217), "The MathML formulas in the following elements must yield numeric expressions: <math> in <kineticLaw>, <stoichiometryMath> in <speciesReference>, <math> in <initialAssignment>, <math> in <assignmentRule>, <math> in <rateRule>, <math> in <algebraicRule>, and <delay> in <event>, and <math> in <eventAssignment> (Reference: L3V1 Sections 4.8, 4.9, 4.11 and 4.12).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10218), "A MathML operator must be supplied the number of arguments appropriate for that operator (Reference: L3V1 Section 3.4.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10219), "The number of arguments used in a call to a function defined by a <functionDefinition> must equal the number of arguments accepted by that function, or in other words, the number of <bvar> elements inside the <lambda> element of the function definition.  ).Reference: L3V1 Section 4.3.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10220), "The SBML attribute 'units' may only be added to MathML <cn> elements; no other MathML elements are permitted to have the 'units' attribute (Reference: L3V1 Section 3.4.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10221), "The value of the SBML attribute 'units' on a MathML <cn> element must be chosen from either the set of identifiers of UnitDefinition objects in the model, or the set of base units defined by SBML (Reference: L3V1 Section 3.4.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10222), "The value of a <ci> element may not be the identifier of a <compartment> with a 'spatialDimensions' value of 0.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10301), "The value of the 'id' field on every instance of the following type of object in a model must be unique: <model>, <functionDefinition>, <compartmentType>, <compartment>, <speciesType>, <species>, <reaction>, <speciesReference>, <modifierSpeciesReference>, <event>, and model-wide <parameter>s. Note that <unitDefinition> and parameters defined inside a reaction are treated separately (Reference: L3V1 Section 3.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10302), "The value of the 'id' field of every <unitDefinition> must be unique across the set of all <unitDefinition>s in the entire model (Reference: L3V1 Sections 3.3 and 4.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10303), "The value of the 'id' field of each parameter defined locally within a <kineticLaw> must be unique across the set of all such parameter definitions in that <kineticLaw> (Reference: L3V1 Sections 3.3.1 and 4.11.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10304), "The value of the 'variable' field in all <assignmentRule> and <rateRule> definitions must be unique across the set of all such rule definitions in a model (Reference: L3V1 Section 4.9.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10305), "In each <event>, the value of the 'variable' field within every <eventAssignment> definition must be unique across the set of all <eventAssignment>s within that <event> (Reference: L3V1 Section 4.12.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10306), "An identifier used as the value of 'variable' in an <eventAssignment> cannot also appear as the value of 'variable' in an <assignmentRule> (Reference: L3V1 Section 4.12.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10307), "Every 'metaid' attribute value must be unique across the set of all 'metaid' values in a model (Reference: L3V1 Sections 3.1.6 and 3.2.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10308), "The value of an 'sboTerm' attribute must have the data type 'SBOTerm', which is a string consisting of the characters 'S', 'B', 'O', ':' followed by exactly seven digits (Reference: L3V1 Section 3.1.11).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10309), "The syntax of 'metaid' attribute values must conform to the syntax of the XML type 'ID' (Reference: L3V1 Sections 3.2.1 and 3.1.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10310), "The syntax of 'id' attribute values must conform to the syntax of the SBML type 'SId' (Reference: L3V1 Section 3.1.7).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10311), "The syntax of unit identifiers (i.e., the values of the 'id' attribute on UnitDefinition, the 'units' attribute on Compartment, the 'units' attribute on Parameter, and the 'substanceUnits' attribute on Species) must conform to the syntax of the SBML type UnitSId (Reference: L3V1 Section 3.1.9).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10312), "Message).Reference: L3V1 Section 3.1.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10313), "Unit identifier references (i.e the 'units' attribute on <Compartment>, the 'units' attribute on <Parameter>, and the 'substanceUnits' attribute on <Species>) must be the identifier of a <UnitDefinition> in the <Model>, or the identifier of a predefined unit in SBML.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10401), "Every top-level element within an annotation element must have a namespace declared (Reference: L3V1 Section 3.2.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10402), "There cannot be more than one top-level element using a given namespace inside a given annotation element (Reference: L3V1 Section 3.2.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10403), "Top-level elements within an annotation element cannot use any SBML namespace, whether explicitly (by declaring the namespace to be one of the URIs \"http://www.sbml.org/sbml/level1\", \"http://www.sbml.org/sbml/level2\", \"http://www.sbml.org/sbml/level2/version2\", or \"http://www.sbml.org/sbml/level2/version3\", or \"http://www.sbml.org/sbml/level2/version4\", or \"http://www.sbml.org/sbml/level2/version5\" or \"http://www.sbml.org/sbml/level3/version1/core\"), or implicitly (by failing to declare any namespace).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10404), "A given SBML object may contain at most one <annotation> element (Reference: L3V1 Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10501), "The units of the expressions used as arguments to a function call should match the units expected for the arguments of that function. (References: L2V2 Section 3.5; L2V3 Section 3.4; L2V4 Section 3.4.)");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10503), "The unit of measurement associated with the mathematical formula in the MathML math element of every KineticLaw object in a model should be identical to all KineticLaw objects in the model (Reference: L3V1 Section 3.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10511), "When the 'variable' in an <assignmentRule> refers to a <compartment>, the units of the rule's right-hand side are expected to be consistent with the units of that compartment's size (Reference: L3V1 Section 4.9.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10512), "When the 'variable' in an <assignmentRule> refers to a <species>, the units of the rule's right-hand side are expected to be consistent with the units of the species' quantity (Reference: L3V1 Section 4.9.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10513), "When the 'variable' in an <assignmentRule> refers to a <parameter>, the units of the rule's right-hand side are expected to be consistent with the units declared for that parameter (Reference: L3V1 Section 4.9.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10514), "When the value of the attribute variable in an AssignmentRule object refers to a SpeciesReference object, the unit of measurement associated with the rule's right-hand side should be consistent with the unit of stoichiometry, that is, dimensionless (Reference: L3V1 Section 4.9.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10521), "When the 'variable' in an <initialAssignment> refers to a <compartment>, the units of the <initialAssignment>'s <math> expression are expected to be consistent with the units of that compartment's size (Reference: L3V1 Section 4.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10522), "When the 'variable' in an <initialAssignment> refers to a <species>, the units of the <initialAssignment>'s <math> expression are expected to be consistent with the units of that species' quantity (Reference: L3V1 Section 4.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10523), "When the 'variable' in an <initialAssignment> refers to a <parameter>, the units of the <initialAssignment>'s <math> expression are expected to be consistent with the units declared for that parameter (Reference: L3V1 Section 4.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10524), "When the value of the attribute variable in an InitialAssignment object refers to a SpeciesReference object, the unit of measurement associated with the InitialAssignment's math expression should be consistent with the unit of stoichiometry, that is, dimensionless (Reference: L3V1 Section 4.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10531), "When the 'variable' in a <rateRule> definition refers to a <compartment>, the units of the rule's right-hand side are expected to be of the form _x per time_, where _x_ is either the 'units' in that <compartment> definition, or (in the absence of explicit units declared for the compartment size) the default units for that compartment, and _time_ refers to the units of time for the model (Reference: L3V1 Sections 4.5.4, 4.2.4 AND 4.9.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10532), "When the 'variable' in a <rateRule> definition refers to a <species>, the units of the rule's right-hand side are expected to be of the form _x per time_, where _x_ is the units of that species' quantity, and _time_ refers to the units of time for the model (Reference: L3V1 Sections 4.6.5, 4.2.4 AND 4.9.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10533), "When the 'variable' in a <rateRule> definition refers to a <parameter>, the units of the rule's right-hand side are expected to be of the form _x per time_, where _x_ is the 'units' in that <parameter> definition, and _time_ refers to the units of time for the model (Reference: L3V1 Sections 4.7.3, 4.2.4 AND 4.9.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10534), "When the value of the attribute variable in a RateRule object refers to a SpeciesReference object, the unit of measurement associated with the RateRule's math expression should be consistent with {unit derived from dimensionless}/{unit of time}.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10541), "The units of the 'math' formula in a <kineticLaw> definition are expected to be the equivalent of _substance per time_ (Reference: L3V1 Sections 4.11.7, 4.2.4 AND 4.9.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10542), "For every Species object produced or consumed in a reaction (that is, referenced by a SpeciesReference object), the unit of measurement of the species' substance should be consistent with the unit of extent for the model times the unit of the conversion factor for that species. More precisely, the product of the units indicated by the Model object's extentUnits and the conversionFactor attribute for that particular Species (whether the attribute is set directly on the Species object or inherited from the enclosing Model object) should be consistent with the unit specified by that Species object's substanceUnits attribute value (Reference: L3V1 Section 4.2.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10551), "When a value for <delay> is given in a <event> definition, the units of the delay formula are expected to correspond to either the value of 'timeUnits' in the <event> or (if no 'timeUnits' are given), the model's default units of time (Reference: L3V1 Section 4.12.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10561), "When the 'variable' in an <eventAssignment> refers to a <compartment>, the units of the <eventAssignment>'s <math> expression are expected to be consistent with the units of that compartment's size (Reference: L3V1 Section 4.12.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10562), "When the 'variable' in an <eventAssignment> refers to a <species>, the units of the <eventAssignment>'s <math> expression are expected to be consistent with the units of the species' quantity (Reference: L3V1 Section 4.12.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10563), "When the 'variable' in an <eventAssignment> refers to a <parameter>, the units of the <eventAssignment>'s <math> expression are expected to be consistent with the units declared for that parameter (Reference: L3V1 Section 4.12.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10564), "When the value of the attribute variable of an EventAssignment object is the identifier of a SpeciesReference object, the unit of measurement associated with the EventAssignment's math expression should be consistent with the unit of stoichiometry, i.e., dimensionless (Reference: L3V1 Section 4.12.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10565), "In an Event object, the unit of measurement associated with a Priority object's <math> expression object should be 'dimensionless' (Reference: L3V1 Section 4.12.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10599), "");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10601), "The system of equations created from an SBML model must not be overdetermined (Reference: L3V1 Section 4.9.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10701), "The value of the 'sboTerm' attribute on a <model> is expected to be an SBO identifier (http://www.biomodels.net/SBO/).  In SBML Level 2 prior to Version 4 the value is expected to be a term derived from SBO:0000004, \"modeling framework\"; in Version 4 and above it is expected to be a term derived from SBO:0000231 \"occurring entity representation\").Reference: L3V1 Section 5).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10702), "The value of the 'sboTerm' attribute on a <functionDefinition> is expected to be an SBO identifier (http://www.biomodels.net/SBO/) referring to a mathematical expression (i.e., terms derived from SBO:0000064, \"mathematical expression\") (Reference: L3V1 Section 5).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10703), "The value of the 'sboTerm' attribute on a <parameter> is expected to be an SBO identifier (http://www.biomodels.net/SBO/) referring to a quantitative parameter defined in SBO (i.e., terms derived from SBO:0000002, \"quantitative systems description parameter\") (Reference: L3V1 Section 5).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10704), "The value of the 'sboTerm' attribute on an <initialAssignment> is expected to be an SBO identifier (http://www.biomodels.net/SBO/) referring to a mathematical expression (i.e., terms derived from SBO:0000064, \"mathematical expression\") (Reference: L3V1 Section 5).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10705), "The value of the 'sboTerm' attribute on a rule is expected to be an SBO identifier (http://www.biomodels.net/SBO/) referring to a mathematical expression (i.e., terms derived from SBO:0000064, \"mathematical expression\"). Note: This applies to Algebraic Rules in addition to Rate and Assignment Rules (Reference: L3V1 Section 5).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10706), "The value of the 'sboTerm' attribute on a <constraint> is expected to be an SBO identifier (http://www.biomodels.net/SBO/) referring to a mathematical expression (i.e., terms derived from SBO:0000064, \"mathematical expression\") (Reference: L3V1 Section 5).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10707), "The value of the 'sboTerm' attribute on a <reaction> is expected to be an SBO identifier (http://www.biomodels.net/SBO/) referring to an occurring entity representation defined in SBO (i.e., terms derived from SBO:0000231, \"occurring entity representation\") (Reference: L3V1 Section 5).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10708), "The value of the 'sboTerm' attribute on a <speciesReference> or <modifierSpeciesReference> is expected to be an SBO identifier (http://www.biomodels.net/SBO/) referring to a participant role. The appropriate term depends on whether the object is a reactant, product or modifier. If a reactant, then it should be a term in the SBO:0000010, \"reactant\" hierarchy; if a product, then it should be a term in the SBO:0000011, \"product\" hierarchy; and if a modifier, then it should be a term in the SBO:0000019, \"modifier\" hierarchy (Reference: L3V1 Section 5).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10709), "The value of the 'sboTerm' attribute on a <kineticLaw> is expected to be an SBO identifier (http://www.biomodels.net/SBO/) referring rate law defined in SBO (i.e., terms derived from SBO:0000001, \"rate law\") (Reference: L3V1 Section 5).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10710), "The value of the 'sboTerm' attribute on an <event> is expected to be an SBO identifier (http://www.biomodels.net/SBO/) referring to an occurring entity representation defined in SBO (i.e., terms derived from SBO:0000231, \"occurring entity representation\") (Reference: L3V1 Section 5).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10711), "The value of the 'sboTerm' attribute on an <eventAssignment> is expected to be an SBO identifier (http://www.biomodels.net/SBO/) referring to a mathematical expression (i.e., terms derived from SBO:0000064, \"mathematical expression\") (Reference: L3V1 Section 5).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10712), "The value of the 'sboTerm' attribute on a <compartment> is expected to be an SBO identifier (http://www.biomodels.net/SBO/). In SBML Level 2 prior to Version 4 it is expected to refer to a participant physical type (i.e., terms derived from SBO:0000236, \"participant physical type\"); in Versions 4 and above it is expected to refer to a material entity (i.e., terms derived from SBO:0000240, \"material entity\") (Reference: L3V1 Section 5).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10713), "The value of the 'sboTerm' attribute on a <species> is expected to be an SBO identifier (http://www.biomodels.net/SBO/). In SBML Level 2 prior to Version 4 it is expected to refer to a participant physical type (i.e., terms derived from SBO:0000236, \"participant physical type\"); in Versions 4 and above it is expected to refer to a material entity (i.e., terms derived from SBO:0000240, \"material entity\") (Reference: L3V1 Section 5).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10714), "The value of the 'sboTerm' attribute on a <compartmentType> is expected to be an SBO identifier (http://www.biomodels.net/SBO/). In SBML Level 2 prior to Version 4 it is expected to refer to a participant physical type (i.e., terms derived from SBO:0000236, \"participant physical type\"); in Versions 4 and above it is expected to refer to a material entity (i.e., terms derived from SBO:0000240, \"material entity\") (Reference: L3V1 Section 5).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10715), "The value of the 'sboTerm' attribute on a <speciesType> is expected to be an SBO identifier (http://www.biomodels.net/SBO/). In SBML Level 2 prior to Version 4 it is expected to refer to a participant physical type (i.e., terms derived from SBO:0000236, \"participant physical type\"); in Versions 4 and above it is expected to refer to a material entity (i.e., terms derived from SBO:0000240, \"material entity\").");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10716), "The value of the 'sboTerm' attribute on a <trigger> is expected to be an SBO identifier (http://www.biomodels.net/SBO/) referring to a mathematical expression (i.e., terms derived from SBO:0000064, \"mathematical expression\").");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10717), "The value of the 'sboTerm' attribute on a <delay> is expected to be an SBO identifier (http://www.biomodels.net/SBO/) referring to a mathematical expression (i.e., terms derived from SBO:0000064, \"mathematical expression\") (Reference: L3V1 Section 5).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10718), "[Although SBML Level 3 Version 1 does not explicitly define the following as an error, other Levels and/or Versions of SBML do.] ).The value of the 'sboTerm' attribute on a <localParameter> is expected to be an SBO identifier (http://www.biomodels.net/SBO/) referring to a quantitative parameter (i.e., terms derived from SBO:0000002, \"quantitive systems description parameter\") (Reference: L3V1 Section 5).");
        
        contents.put(Integer.toString(SBMLErrorCodes.CORE_10719), "The value of the 'sboTerm' attribute on the <sbml> object is expected to be an SBO identifier (http://www.biomodels.net/SBO/) referring to a modeling framework (i.e., terms derived from SBO:0000004, \"modelling framework\").\nReference: L3V1 Section 5.");

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10801), "The contents of the <notes> element must be explicitly placed in the XHTML XML namespace (Reference: L3V1 Section 3.2.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10802), "The contents of the <notes> element must not contain an XML declaration (i.e., a string of the form \"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\" or similar) (Reference: L3V1 Section 3.2.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10803), "The contents of the <notes> element must not contain an XML DOCTYPE declaration (i.e., a string beginning with the characters \"<!DOCTYPE\" (Reference: L3V1 Section 3.2.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10804), "The XHTML content inside a <notes> element can only take one of the following general forms: (1) a complete XHTML document beginning with the element <html> and ending with </html>; (2) the \"body\" portion of a document beginning with the element <body> and ending with </body>; or (3) XHTML content that is permitted within a <body> ... </body> elements.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_10805), "A given SBML object may contain at most one <notes> element (Reference: L3V1 Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20101), "The <sbml> container element must declare the XML Namespace for SBML, and this declaration must be consistent with the values of the 'level' and 'version' attributes on the <sbml> element (Reference: L3V1 Section 4.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20102), "The <sbml> container element must declare the SBML Level using the attribute 'level', and this declaration must be consistent with the XML Namespace declared for the <sbml> element (Reference: L3V1 Section 4.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20103), "The <sbml> container element must declare the SBML Version using the attribute 'version', and this declaration must be consistent with the XML Namespace declared for the <sbml> element (Reference: L3V1 Section 4.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20104), "The <sbml> container element must declare the XML Namespace for any SBML Level 3 packages used within the SBML document. This declaration must be consistent with the values of the 'level' and 'version' attributes on the <sbml> element (Reference: L3V1 Section 4.1.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20105), "The attribute 'level' on the <sbml> container element must have a value of type 'positiveInteger' (Reference: L3V1 Section 3.1.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20106), "The attribute 'version' on the <sbml> container element must have a value of type 'positiveInteger' (Reference: L3V1 Section 3.1.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20108), "The <sbml> object may only have the optional attributes 'metaid' and 'sboTerm' (Reference: L3V1 Section 4.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20109), "It is not possible to use SBML L3 packages with earlier levels of SBML.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20201), "An SBML document must contain a <model> element. The <model> element is optional in L3V2 and beyond (Reference: L3V1 Section 4.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20202), "The order of subelements within a <model> element must be the following (where any one may be optional, but the ordering must be maintained): <listOfFunctionDefinitions>, <listOfUnitDefinitions>, <listOfCompartmentTypes>, <listOfSpeciesTypes>, <listOfCompartments>, <listOfSpecies>, <listOfParameters>, <listOfInitialAssignments>, <listOfRules>, <listOfConstraints>, <listOfReactions> and <listOfEvents>.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20203), "The <listOf___> containers in a <model> are optional, but if present, the lists cannot be empty. Specifically, if any of the following are present in a <model>, they must not be empty: <listOfFunctionDefinitions>, <listOfUnitDefinitions>, <listOfCompartmentTypes>, <listOfSpeciesTypes>, <listOfCompartments>,  <listOfSpecies>, <listOfParameters>, <listOfInitialAssignments>, <listOfRules>, <listOfConstraints>, <listOfReactions> and <listOfEvents> (Reference: L3V1 Section 4.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20204), "If a model defines any species, then the model must also define at least one compartment. This is an implication of the fact that the 'compartment' attribute on the <species> element is not optional (Reference: L3V1 Section 4.6.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20205), "There may be at most one instance of each of the following kind of element in a <model> object: ListOfFunctionDefinitions, ListOfUnitDefinitions, ListOfCompartments, ListOfSpecies, ListOfParameters, ListOfInitialAssignments, ListOfRules, ListOfConstraints, ListOfReactions and ListOfEvents (Reference: L3V1 Section 4.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20206), "Apart from the general Notes and Annotation subobjects permitted on all SBML components, a ListOfFunctionDefinitions container object may only contain FunctionDefinition objects (Reference: L3V1 Section 4.2.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20207), "Apart from the general Notes and Annotation subobjects permitted on all SBML components, a ListOfUnitDefinitions container object may only contain UnitDefinition objects (Reference: L3V1 Section 4.2.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20208), "Apart from the general Notes and Annotation subobjects permitted on all SBML components, a ListOfCompartments container object may only contain Compartment objects (Reference: L3V1 Section 4.2.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20209), "Apart from the general Notes and Annotation subobjects permitted on all SBML components, a ListOfSpecies container object may only contain Species objects (Reference: L3V1 Section 4.2.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20210), "Apart from the general Notes and Annotation subobjects permitted on all SBML components, a ListOfParameters container object may only contain Parameter objects (Reference: L3V1 Section 4.2.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20211), "Apart from the general Notes and Annotation subobjects permitted on all SBML components, a ListOfInitialAssignments container object may only contain InitialAssignment objects (Reference: L3V1 Section 4.2.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20212), "Apart from the general Notes and Annotation subobjects permitted on all SBML components, a ListOfRules container object may only contain Rule objects (Reference: L3V1 Section 4.2.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20213), "Apart from the general Notes and Annotation subobjects permitted on all SBML components, a ListOfConstraints container object may only contain Constraint objects (Reference: L3V1 Section 4.2.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20214), "Apart from the general Notes and Annotation subobjects permitted on all SBML components, a ListOfReactions container object may only contain Reaction objects (Reference: L3V1 Section 4.2.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20215), "Apart from the general Notes and Annotation subobjects permitted on all SBML components, a ListOfEvents container object may only contain Event objects (Reference: L3V1 Section 4.2.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20216), "The value of the attribute 'conversionFactor' on a Model object must be the identifier of an existing Parameter object defined in the Model object's ListOfParameters (Reference: L3V1 Section 4.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20217), "The value of the attribute 'timeUnits' on a Model object should be either the units 'second', 'dimensionless', or the identifier of a UnitDefinition object based on these units (Reference: L3V1 Section 4.2.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20218), "The value of the attribute 'volumeUnits' on a Model object should be either the units 'litre', 'dimensionless', or the identifier of a UnitDefinition object based on these units or a unit derived from 'metre' (with an exponent of '3') (Reference: L3V1 Section 4.2.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20219), "The value of the attribute 'areaUnits' on a Model object should be either 'dimensionless' or the identifier of a UnitDefinition object based on 'dimensionless' or a unit derived from 'metre' (with an exponent of '2') (Reference: L3V1 Section 4.2.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20220), "The value of the attribute 'lengthUnits' on a Model object should be either the units 'metre', 'dimensionless', or the identifier of a UnitDefinition object based on these units (Reference: L3V1 Section 4.2.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20221), "The value of the attribute extentUnits on a Model object should be either the units 'mole', 'item', 'avogadro', 'dimensionless', 'kilogram', 'gram', or the identifier of a UnitDefinition object based on these units (Reference: L3V1 Section 4.2.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20222), "A Model object may only have the following attributes, all of which are optional: 'metaid', 'sboTerm', 'id', 'name', 'substanceUnits', 'timeUnits', 'volumeUnits', 'areaUnits', 'lengthUnits', 'extentUnits' and 'conversionFactor'. No other attributes from the SBML Level 3 Core namespace are permitted on a Model object (Reference: L3V1 Section 4.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20223), "A ListOfFunctionDefinitions object may have the optional attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a ListOfFunctionDefinitions object (Reference: L3V1 Section 4.2.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20224), "A ListOfUnitDefinitions object may have the optional attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a ListOfUnitDefinitions object (Reference: L3V1 Section 4.2.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20225), "A ListOfCompartments object may have the optional attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a ListOfCompartments object (Reference: L3V1 Section 4.2.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20226), "A ListOfSpecies object may have the optional attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a ListOfSpecies object (Reference: L3V1 Section 4.2.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20227), "A ListOfParameters object may have the optional attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a ListOfParameters object (Reference: L3V1 Section 4.2.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20228), "A ListOfInitialAssignments object may have the optional attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a ListOfInitialAssignments object (Reference: L3V1 Section 4.2.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20229), "A ListOfRules object may have the optional attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a ListOfRules object (Reference: L3V1 Section 4.2.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20230), "A ListOfConstraints object may have the optional attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a ListOfConstraints object (Reference: L3V1 Section 4.2.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20231), "A ListOfReactions object may have the optional attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a ListOfReactions object (Reference: L3V1 Section 4.2.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20232), "A ListOfEvents object may have the optional attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a ListOfEvents object (Reference: L3V1 Section 4.2.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20233), "[Although SBML Level 3 Version 1 does not explicitly define the following as an error, other Levels and/or Versions of SBML do.] ).The value of the attribute substanceUnits on a Model object should be either the units 'mole', 'item', 'avogadro', 'dimensionless', 'kilogram', 'gram', or the identifier of a UnitDefinition object based on these units.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20301), "The top-level element within <math> in a <functionDefinition> is restricted (Reference: L3V1 Section 4.3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20302), "Inside the <lambda> of a <functionDefinition>, if a <ci> element is the first element within a MathML <apply>, then the <ci>'s value can only be chosen from the set of identifiers of other SBML <functionDefinition>s defined prior to that point in the SBML model. In other words, forward references to user-defined functions are not permitted.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20303), "Inside the <lambda> of a <functionDefinition>, the identifier of that <functionDefinition> cannot appear as the value of a <ci> element. SBML functions are not permitted to be recursive (Reference: L3V1 Sections 3.4.3 and 4.3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20304), "Inside the <lambda> of a <functionDefinition>, if a <ci> element is not the first element within a MathML <apply>, then the <ci>'s value can only be the value of a <bvar> element declared in that <lambda>. In other words, all model entities referenced inside a function definition must be passed arguments to that function (Reference: L3V1 Sections 3.4.3 and 4.3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20305), "The value type returned by a <functionDefinition>'s <lambda> must be either Boolean or numeric (Reference: L3V1 Section 3.4.9).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20306), "A FunctionDefinition object must contain exactly one MathML math element.  The <math> element is optional in L3V2 and beyond (Reference: L3V1 Section 4.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20307), "FunctionDefinition object must have the required attribute 'id', and may have the optional attributes 'metaid', 'sboTerm' and 'name'. No other attributes from the SBML Level 3 Core namespace are permitted on a FunctionDefinition object (Reference: L3V1 Section 4.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20401), "The value of the 'id' attribute in a <unitDefinition> must be of type 'UnitSId' and not be identical to any unit predefined in SBML (Reference: L3V1 Section 4.4.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20402), "Redefinitions of the built-in unit 'substance' are restricted.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20403), "Redefinitions of the built-in unit 'length' are restricted.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20404), "Redefinitions of the built-in unit 'area' are restricted.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20405), "Redefinitions of the built-in unit 'time' are restricted.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20406), "Redefinitions of the built-in unit 'volume' are restricted.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20407), "If a <unitDefinition> for 'volume' simplifies to a <unit> in which the 'kind' attribute value is 'litre', then its 'exponent' attribute value must be '1'.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20408), "If a <unitDefinition> for 'volume' simplifies to a <unit> in which the 'kind' attribute value is 'metre', then its 'exponent' attribute value must be '3'.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20409), "The <listOfUnits> container in a <unitDefinition> cannot be empty.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20410), "The value of the 'kind' attribute of a <unit> can only be one of the base units enumerated by 'UnitKind'; that is, the SBML unit system is not hierarchical and user-defined units cannot be defined using other user-defined units (Reference: L3V1 Section 4.4.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20411), "The 'offset' attribute on <unit> previously available in SBML Level 2 Version 1, has been removed as of SBML Level 2 Version 2.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20412), "The predefined unit 'Celsius', previously available in SBML Level 1 and Level 2 Version 1, has been removed as of SBML Level 2 Version 2.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20413), "The ListOfUnits container object in a UnitDefinition object is optional, but if present, it must not be empty (Reference: L3V1 Section 4.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20414), "There may be at most one ListOfUnits container objects in a UnitDefinition object (Reference: L3V1 Section 4.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20415), "Apart from the general Notes and Annotation subobjects permitted on all SBML components, a ListOfUnits container object may only contain Unit objects (Reference: L3V1 Section 4.2.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20419), "A UnitDefinition object must have the required attribute 'id' and may have the optional attributes 'metaid', 'sboTerm' and 'name'. No other attributes from the SBML Level 3 Core namespace are permitted on a UnitDefinition object (Reference: L3V1 Section 4.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20420), "A ListOfUnits object may have the optional attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a ListOfUnits object (Reference: L3V1 Section 4.2.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20421), "A Unit object must have the required attributes 'kind', 'exponent', 'scale' and 'multiplier', and may have the optional attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a Unit object (Reference: L3V1 Section 4.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20501), "The size of a <compartment> must not be set if the compartment's 'spatialDimensions' attribute has value '0'.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20502), "If a <compartment> definition has a 'spatialDimensions' value of '0', then its 'units' attribute must not be set. If the compartment has no dimensions, then no units can be associated with a non-existent size.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20503), "If a <compartment> definition has a 'spatialDimensions' value of '0', then its 'constant' attribute value must either default to or be set to 'true'. If the compartment has no dimensions, then its size can never change.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20504), "The 'outside' attribute value of a <compartment> must be the identifier of another <compartment> defined in the model.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20505), "A <compartment> may not enclose itself through a chain of references involving the 'outside' field. This means that a compartment cannot have its own identifier as the value of 'outside', nor can it point to another compartment whose 'outside' field points directly or indirectly to the compartment.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20506), "The 'outside' attribute value of a <compartment> cannot be a compartment whose 'spatialDimensions' value is '0', unless both compartments have 'spatialDimensions'='0'. Simply put, a zero-dimensional compartment cannot enclose compartments that have anything other than zero dimensions themselves.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20507), "The value of the 'units' attribute on a <compartment> having 'spatialDimensions' of '1' is restricted (Reference: L3V1 Section 4.5.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20508), "The value of the 'units' attribute on a <compartment> having 'spatialDimensions' of '2' is restricted (Reference: L3V1 Section 4.5.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20509), "The value of the 'units' attribute on a <compartment> having 'spatialDimensions' of '3' is restricted (Reference: L3V1 Section 4.5.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20510), "If the 'compartmentType' attribute is given a value in a <compartment> definition, it must contain the identifier of an existing <compartmentType>.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20511), "If the attribute 'units' on a Compartment object having a 'spatialDimensions' attribute value of '1' has not been set, then the unit of measurement associated with the compartment's size is determined by the value of the enclosing Model object's 'lengthUnits' attribute. If neither the Compartment object's 'units' nor the enclosing Model object's 'lengthUnits' attributes are set, the unit of compartment size is undefined (Reference: L3V1 Section 4.5.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20512), "If the attribute 'units' on a Compartment object having a 'spatialDimensions' attribute value of '2' has not been set, then the unit of measurement associated with the compartment's size is determined by the value of the enclosing Model object's 'areaUnits' attribute. If neither the Compartment object's 'units' nor the enclosing Model object's 'areaUnits' attributes are set, the unit of compartment size is undefined (Reference: L3V1 Section 4.5.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20513), "If the attribute 'units' on a Compartment object having a 'spatialDimensions' attribute value of '3' has not been set, then the unit of measurement associated with the compartment's size is determined by the value of the enclosing Model object's 'volumeUnits' attribute. If neither the Compartment object's 'units' nor the enclosing Model object's 'volumeUnits' attributes are set, the unit of compartment size is undefined (Reference: L3V1 Section 4.5.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20517), "A Compartment object must have the required attributes 'id' and 'constant', and may have the optional attributes 'metaid', 'sboTerm', 'name', 'spatialDimensions', 'size' and 'units'. No other attributes from the SBML Level 3 Core namespace are permitted on a Compartment object (Reference: L3V1 Section 4.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20518), "If neither the attribute 'units' nor the attribute 'spatialDimensions' on a Compartment object is set, the unit associated with that compartment's size is undefined (Reference: L3V1 Section 4.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20601), "The value of 'compartment' in a <species> definition must be the identifier of an existing <compartment> defined in the model (Reference: L3V1 Section 4.6.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20602), "If a <species> definition sets 'hasOnlySubstanceUnits' to 'true', then it must not have a value for 'spatialSizeUnits'.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20603), "A <species> definition must not set 'spatialSizeUnits' if the <compartment> in which it is located has a 'spatialDimensions' value of '0'.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20604), "If a <species> located in a <compartment> whose 'spatialDimensions' is set to '0', then that <species> definition cannot set 'initialConcentration'.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20605), "If a <species> is located in a <compartment> whose 'spatialDimensions' has value '1', then that <species> definition can only set 'spatialSizeUnits' to a value of 'length', 'metre', 'dimensionless', or the identifier of a <unitDefinition> derived from 'metre' (with an 'exponent' value of '1') or 'dimensionless'.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20606), "If a <species> is located in a <compartment> whose 'spatialDimensions' has value '2', then that <species> definition can only set 'spatialSizeUnits' to a value of 'area', 'dimensionless', or the identifier of a <unitDefinition> derived from either 'metre' (with an 'exponent' value of '2') or 'dimensionless'.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20607), "If a <species> is located in a <compartment> whose 'spatialDimensions' has value '3', then that <species> definition can only set 'spatialSizeUnits' to a value of 'volume', 'litre', 'dimensionless', or the identifier of a <unitDefinition> derived from either 'litre', 'metre' (with an 'exponent' value of '3') or 'dimensionless'.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20608), "The value of a <species>'s 'units' attribute is restricted (Reference: L3V1 Section 4.6.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20609), "A <species> cannot set values for both 'initialConcentration' and 'initialAmount' because they are mutually exclusive (Reference: L3V1 Section 4.6.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20610), "A <species>'s quantity cannot be determined simultaneously by both reactions and rules. More formally, if the identifier of a <species> definition having 'boundaryCondition'='false' and 'constant'='false' is referenced by a <speciesReference> anywhere in a model, then this identifier cannot also appear as the value of a 'variable' in an <assignmentRule> or a <rateRule> (Reference: L3V1 Section 4.6.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20611), "A <species> having boundaryCondition='false' cannot appear as a reactant or product in any reaction if that Species also has constant='true' (Reference: L3V1 Section 4.6.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20612), "The value of 'speciesType' in a <species> definition must be the identifier of an existing <speciesType>.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20613), "There cannot be more than one species of a given <speciesType> in the same compartment of a model. More formally, for any given compartment, there cannot be more than one <species> definition in which both of the following hold simultaneously: (i) the <species>' 'compartment' value is set to that compartment's identifier and (ii) the <species>' 'speciesType' is set the same value as the 'speciesType' of another <species> that also sets its 'compartment' to that compartment identifier.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20614), "The 'compartment' attribute in a <species> is mandatory. A <species> definition in a model must include a value for this attribute (Reference: L3V1 Section 4.6.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20615), "The 'spatialSizeUnits' attribute on <species>, previously available in SBML Level 2 versions prior to Version 3, has been removed as of SBML Level 2 Version 3.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20616), "If the attribute 'substanceUnits' in a Species object has not been set, then the unit of measurement associated with the species' quantity is determined by the value of the enclosing Model object's 'substanceUnits' attribute. If neither the Species object's 'substanceUnits' attribute nor the enclosing Model object's 'substanceUnits' attribute are set, then the unit of that species' quantity is undefined (Reference: L3V1 Section 4.6.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20617), "The value of the attribute 'conversionFactor' on a Species object must be the identifier of an existing Parameter object defined in the enclosing Model object (Reference: L3V1 Section 4.6.7).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20623), "A Species object must have the required attributes 'id', 'compartment', 'hasOnlySubstanceUnits', 'boundaryCondition' and 'constant', and may have the optional attributes 'metaid', 'sboTerm', 'name', 'initialAmount', 'initialConcentration', 'substanceUnits' and 'conversionFactor'. No other attributes from the SBML Level 3 Core namespace are permitted on a Species object (Reference: L3V1 Section 4.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20701), "The 'units' in a <Parameter> definition must be a value chosen from among the following: a predefined unit (e.g., 'substance', 'time', etc.), the identifier of a <UnitDefinition> in the model, or one of the base units in SBML (e.g., 'litre', 'mole', 'metre', etc.)).Reference: L3V1 Section 4.7.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20702), "If the attribute 'units' on a given Parameter object has not been set, then the unit of measurement associated with that parameter's value is undefined (Reference: L3V1 Section 4.7.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20705), "A Parameter object referenced by the attribute 'conversionFactor' on a Species or Model object must have a value of 'true' for its attribute 'constant' (Reference: L3V1 Section 4.6.7).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20706), "A Parameter object must have the required attributes 'id' and 'constant', and may have the optional attributes 'metaid', 'sboTerm', 'name', 'value' and 'units'. No other attributes from the SBML Level 3 Core namespace are permitted on a Parameter object (Reference: L3V1 Section 4.7).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20801), "The value of 'symbol' in an <initialAssignment> definition must be the identifier of an existing <compartment>, <species>, or <parameter> defined in the model or <speciesReference> in Level 3 (Reference: L3V1 Section 4.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20802), "A given identifier cannot appear as the value of more than one 'symbol' field across the set of <initialAssignment>s in a model (Reference: L3V1 Section 4.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20803), "The value of a 'symbol' field in any <initialAssignment> definition cannot also appear as the value of a 'variable' field in an <assignmentRule> (Reference: L3V1 Section 4.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20804), "An InitialAssignment object must contain exactly one MathML <math> element.  The <math> element is optional in L3V2 and beyond (Reference: L3V1 Section 4.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20805), "An InitialAssignment object must have the required attribute 'symbol' and may have the optional attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on an InitialAssignment object (Reference: L3V1 Section 4.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20806), "The identifier given as the value of a 'symbol' attribute in any <initialAssignment> definition cannot be the identifier of a <compartment> with a 'spatialDimensions' value of 0.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20901), "The value of an <assignmentRule>'s 'variable' must be the identifier of an existing <compartment>, <species>, globally-defined <parameter>, or (in Level 3) <speciesReference> (Reference: L3V1 Section 4.9.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20902), "The value of a <rateRule>'s 'variable' must be the identifier of an existing <compartment>, <species>, globally-defined <parameter>, or (in Level 3) <speciesReference> (Reference: L3V1 Section 4.9.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20903), "Any <compartment>, <species>, <parameter>, or (in Level 3) <speciesReference> whose identifier is the value of a 'variable' attribute in an <assignmentRule>, must have a value of 'false' for 'constant' (Reference: L3V1 Section 4.9.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20904), "Any <compartment>, <species>, <parameter>, or (in Level 3) <speciesReference> whose identifier is the value of a 'variable' attribute in an <rateRule>, must have a value of 'false' for 'constant' (Reference: L3V1 Section 4.9.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20905), "");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20906), "There must not be circular dependencies in the combined set of <initialAssignment>, <assignmentRule> and <kineticLaw> definitions in a model. Each of these constructs has the effect of assigning a value to an identifier (i.e. the identifier given in the field 'symbol' in <initialAssignment>, the field 'variable' in <assignmentRule>, and the field 'id' on the <kineticLaw>'s enclosing <reaction>). Each of these constructs computes the value using a mathematical formula. The formula for a given identifier cannot make reference to a second identifier whose own definition depends directly or indirectly on the first identifier (Reference: L3V1 Section 4.9.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20907), "Every AssignmentRule, RateRule and AlgebraicRule object must contain exactly one MathML <math> element.  The <math> element is optional in L3V2 and beyond (Reference: L3V1 Section 4.9).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20908), "An AssignmentRule object must have the required attribute 'variable' and may have the optional attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on an AssignmentRule object (Reference: L3V1 Section 4.9).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20909), "A RateRule object must have the required attribute 'variable' and may have the optional attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a RateRule object (Reference: L3V1 Section 4.9).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20910), "An AlgebraicRule object may have the optional attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on an AlgebraicRule object (Reference: L3V1 Section 4.9).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_20911), "The value of a <rateRule> or <assignmentRule>'s 'variable' attribute must not be the identifier of a <compartment> with a 'spatialDimensions' value of 0.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21001), "A <constraint>'s <math> expression must evaluate to a value of type Boolean (Reference: L3V1 Section 4.10).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21002), "The order of subelements within <constraint> must be the following: <math>, <message>. The <message> element is optional, but if present, must follow the <math> element.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21003), "The contents of the <message> element in a <constraint> must be explicitly placed in the XHTML XML namespace.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21004), "The contents of the <message> element must not contain an XML declaration (i.e., a string of the form \"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\" or similar) (Reference: L3V1 Section 4.10.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21005), "The contents of the <message> element must not contain an XML DOCTYPE declaration (i.e., a string beginning with the characters \"<!DOCTYPE\" (Reference: L3V1 Section 4.10.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21006), "The XHTML content inside a <constraint>'s <message> element can only take one of the following general forms: (1) a complete XHTML document beginning with the element <html> and ending with </html>; (2) the \"body\" portion of a document beginning with the element <body> and ending with </body>; or (3) XHTML content that is permitted within a <body> ... </body> elements.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21007), "A <constraint> object must contain exactly one MathML <math> element.  The <math> element is optional in L3V2 and beyond (Reference: L3V1 Section 4.10).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21008), "A <constraint> object may contain at most one <message> subobject (Reference: L3V1 Section 4.10).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21009), "A <constraint> object may have the optional attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a Constraint object (Reference: L3V1 Section 4.10).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21101), "A <reaction> definition must contain at least one <speciesReference>, either in its <listOfReactants> or its <listOfProducts>. A reaction without any reactant or product species is not permitted, regardless of whether the reaction has any modifier species (Reference: L3V1 Section 4.11.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21102), "The order of subelements within <reaction> must be the following: <listOfReactants> (optional), <listOfProducts> (optional), <listOfModifiers> (optional), <kineticLaw>.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21103), "The following containers are all optional in a <reaction>, but if any is present, it must not be empty: <listOfReactants>, <listOfProducts>, <listOfModifiers>, <kineticLaw> (Reference: L3V1 Section 4.11).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21104), "The list of reactants (<listOfReactants>) and list of products (<listOfProducts>) in a <reaction> can only contain <speciesReference> elements (Reference: L3V1 Section 4.11).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21105), "The list of modifiers (<listOfModifiers>) in a <reaction> can only contain <modifierSpeciesReference> elements (Reference: L3V1 Section 4.11).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21106), "A <reaction> object may contain at most one of each of the following elements: <listOfReactants>, <listOfProducts>, <listOfModifiers>, and <kineticLaw> (Reference: L3V1 Section 4.11).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21107), "The value of the attribute 'compartment' in a <reaction> object is optional, but if present, must be the identifier of an existing Compartment object defined in the model (Reference: L3V1 Section 4.11.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21110), "A <reaction> object must have the required attributes 'id', 'reversible' and 'fast', and may have the optional attributes 'metaid', 'sboTerm', 'name' and 'compartment'. No other attributes from the SBML Level 3 Core namespace are permitted on a Reaction object (Reference: L3V1 Section 4.11).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21111), "The value of a <speciesReference> 'species' attribute must be the identifier of an existing <species> in the model (Reference: L3V1 Section 4.11.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21112), "");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21113), "A <speciesReference> must not have a value for both 'stoichiometry' and 'stoichiometryMath'; they are mutually exclusive.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21116), "A <speciesReference> object must have the required attributes 'species' and 'constant', and may have the optional attributes 'metaid', 'sboTerm', 'name' 'id' and 'stoichiometry'. No other attributes from the SBML Level 3 Core namespace are permitted on a <speciesReference> object (Reference: L3V1 Section 4.11).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21117), "A <modifierSpeciesReference> object must have the required attribute 'species' and may have the optional attributes 'metaid', 'sboTerm', 'id' and 'name'. No other attributes from the SBML Level 3 Core namespace are permitted on a <modifierSpeciesReference> object (Reference: L3V1 Section 4.11).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21121), "All species referenced in the <kineticLaw> formula of a given reaction must first be declared using <speciesReference> or <modifierSpeciesReference>. More formally, if a <species> identifier appears in a <ci> element of a <reaction>'s <kineticLaw> formula, that same identifier must also appear in at least one <speciesReference> or <modifierSpeciesReference> in the <reaction> definition (Reference: L3V1 Section 4.11.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21122), "The order of subelements within <kineticLaw> must be the following: <math>, <listOfParameters>. The <listOfParameters> is optional, but if present, must follow <math>.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21123), "If present, the <listOfParameters> in a <kineticLaw> must not be an empty list (Reference: L3V1 Section 4.11).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21124), "The 'constant' attribute on a <parameter> local to a <kineticLaw> cannot have a value other than 'true'. The values of parameters local to <kineticLaw> definitions cannot be changed, and therefore they are always constant.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21125), "The 'substanceUnits' attribute on <kineticLaw>, previously available in SBML Level 1 and Level 2 Version 1, has been removed as of SBML Level 2 Version 2. In SBML Level 2 Version 2, the substance units of a reaction rate expression are those of the global 'substance' units of the model.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21126), "The 'timeUnits' attribute on <kineticLaw>, previously available in SBML Level 1 and Level 2 Version 1, has been removed as of SBML Level 2 Version 2. In SBML Level 2 Version 2, the time units of a reaction rate expression are those of the global 'time' units of the model.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21127), "A KineticLaw object may contain at most one ListOfLocalParameters container object (Reference: L3V1 Section 4.11).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21128), "Apart from the general Notes and Annotation subobjects permitted on all SBML components, a ListOfLocalParameters container object may only contain LocalParameter objects (Reference: L3V1 Section 4.2.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21129), "A ListOfLocalParameters object may have the optional attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a ListOfLocalParameters object (Reference: L3V1 Section 4.11).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21130), "A KineticLaw object must contain exactly one MathML <math> element.  The <math> element is optional in L3V2 and beyond (Reference: L3V1 Section 4.11).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21131), "All species referenced in the <stoichiometryMath> formula of a given reaction must first be declared using <speciesReference> or <modifierSpeciesReference>. More formally, if a <species> identifier appears in a <ci> element of a <reaction>'s <stoichiometryMath> formula, that same identifier must also appear in at least one <speciesReference> or <modifierSpeciesReference> in the <reaction> definition.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21132), "A KineticLaw object may have the optional attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a KineticLaw object (Reference: L3V1 Section 4.11).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21150), "A ListOfSpeciesReferences object may have the optional attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a ListOfSpeciesReferences object (Reference: L3V1 Section 4.11).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21151), "A ListOfModifierSpeciesReferences object may have the optional attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a ListOfModifierSpeciesReferences object (Reference: L3V1 Section 4.11).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21152), "The value of the 'fast' attribute should be 'false', as the attribute has been deprecated.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21172), "A LocalParameter object must have the required attribute 'id' and may have the optional attributes 'metaid', 'sboTerm', 'name', 'value' and 'units'. No other attributes from the SBML Level 3 Core namespace are permitted on a LocalParameter object (Reference: L3V1 Section 4.11).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21173), "The 'id' attribute of a <localParameter> object must not be the same as the 'species' attribute of any <speciesReference> in the same <reaction>.");
        
        contents.put(Integer.toString(SBMLErrorCodes.CORE_21201), "An <event> object must have a 'trigger' (Reference: L3V1 Section 4.12.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21202), "An <event>'s <trigger> expression must evaluate to a value of type Boolean (Reference: L3V1 Section 4.12.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21203), "An <event> object must have at least one <eventAssignment> object in its <listOfEventAssignments> (Reference: L3V1 Section 4.12).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21204), "The value of an <event>'s 'timeUnits' attribute must be 'time', 'second', 'dimensionless', or the identifier of a <unitDefinition> derived from either 'second' (with an 'exponent' value of '1') or 'dimensionless'.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21205), "The order of subelements within <event> must be the following: <trigger>, <delay>, <listOfEventAssignments>. The <delay> element is optional, but if present, must follow <trigger>.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21206), "If an <event>'s 'useValuesFromTriggerTime' attribute has the value 'false', then the <event> must contain a <delay> element.  The implication of 'useValuesFromTriggerTime=false' is that there is a delay between the time of trigger and the time of value assignments performed by the <event>.  ).Reference: L3V1 Section 4.12).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21207), "If an Event object contains a Delay subobject, then the Event must have a value for the attribute 'useValuesFromTriggerTime' (Reference: L3V1 Section 4.12).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21209), "A Trigger object must contain exactly one MathML <math> element.  The <math> element is optional in L3V2 and beyond (Reference: L3V1 Section 4.12).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21210), "A Delay object must contain exactly one MathML <math> element.  The <math> element is optional in L3V2 and beyond (Reference: L3V1 Section 4.12).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21211), "The value of the attribute 'variable' in an <eventAssignment> can only be the identifier of a <compartment>, <species>, model-wide <parameter> definition, or <speciesReference> in Level 3 (Reference: L3V1 Section 4.12.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21212), "Any <compartment>, <species>, <parameter>, or (in Level 3) <speciesReference> definition whose identifier is used as the value of 'variable' in an <eventAssignment> must have a value of 'false' for its 'constant' attribute (Reference: L3V1 Section 4.12.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21213), "An EventAssignment object must contain exactly one MathML <math> element.  The <math> element is optional in L3V2 and beyond (Reference: L3V1 Section 4.12).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21214), "An EventAssignment object must have the required attribute 'variable' and may have the optional attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on an EventAssignment object (Reference: L3V1 Section 4.12).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21221), "An Event object may contain at most one Delay object (Reference: L3V1 Section 4.12).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21222), "An Event object may contain at most one ListOfEventAssignments object (Reference: L3V1 Section 4.12).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21223), "Apart from the general Notes and Annotation subobjects permitted on all SBML components, a ListOfEventAssignments container object may only contain EventAssignment objects (Reference: L3V1 Section 4.12.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21224), "A ListOfEventAssignments object may have the optional attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a ListOfEventAssignments object (Reference: L3V1 Section 4.12).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21225), "An Event object must have the required attribute 'useValuesFromTriggerTime' and in addition may have the optional attributes 'metaid', 'sboTerm', 'id',  and 'name'. No other attributes from the SBML Level 3 Core namespace are permitted on an Event object (Reference: L3V1 Section 4.12).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21226), "A Trigger object must have the required attributes 'persistent' and 'initialValue', and in addition, may have the optional attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a Trigger object (Reference: L3V1 Section 4.12).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21227), "A Delay object may have the optional attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a Delay object (Reference: L3V1 Section 4.12).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21228), "The attribute 'persistent' on a Trigger object must have a value of type Boolean (Reference: L3V1 Section 4.12).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21229), "The attribute 'initialValue' on a Trigger object must have a value of type Boolean (Reference: L3V1 Section 4.12).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21230), "An Event object may contain at most one Priority object (Reference: L3V1 Section 4.12).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21231), "An Priority object must contain exactly one MathML <math> element.  The <math> element is optional in L3V2 and beyond (Reference: L3V1 Section 4.12).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_21232), "A Priority object may have the optional attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a Priority object (Reference: L3V1 Section 4.12).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_29999), "");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_80501), "As a principle of best modeling practice, the size of a <compartment> should be set to a value rather than be left undefined. Doing so improves the portability of models between different simulation and analysis systems, and helps make it easier to detect potential errors in models.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_80601), "As a principle of best modeling practice, the <species> should set an initial value (amount or concentration) rather than be left undefined. Doing so improves the portability of models between different simulation and analysis systems, and helps make it easier to detect potential errors in models.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_80701), "As a principle of best modeling practice, the units of a <parameter> should be declared rather than be left undefined. Doing so improves the ability of software to check the consistency of units and helps make it easier to detect potential errors in models.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_80702), "As a principle of best modeling practice, the <parameter> should set an initial value rather than be left undefined. Doing so improves the portability of models between different simulation and analysis systems, and helps make it easier to detect potential errors in models.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_81121), "In SBML's simple symbol system, there is no separation of symbols by class of object; consequently, inside the kinetic law mathematical formula, the value of a local parameter having the same identifier as a species or compartment or other global model entity will override the global value. Modelers may wish to take precautions to avoid this happening accidentally.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_90000), "");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_90001), "Use of SBML Level 1 Version 1 is not recommended and conversion is not supported by libSBML.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91001), "A model with <event> definitions cannot be represented in SBML Level 1.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91002), "SBML Level 1 does not have <functionDefinitions>. The <functionDefinitions> in the original model have been replaced by appropriate formula.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91003), "Conversion of a model with <constraint>s to SBML Level 1 may result in loss of information.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91004), "SBML Level 1 does not have <initialAssignments>. The <initialAssignments> in the original model have been replaced by appropriate values.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91005), "<speciesType> definitions cannot be represented in SBML Level 1.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91006), "<compartmentType> definitions cannot be represented in SBML Level 1.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91007), "A <compartment> with 'spatialDimensions' not equal to 3 cannot be represented in SBML Level 1.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91008), "A <speciesReference> containing a non-integer or non-rational <stoichiometryMath> subelement cannot be represented in SBML Level 1.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91009), "A <speciesReference> containing a non-integer 'stoichiometry' attribute value cannot be represented in SBML Level 1.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91010), "A <unit> containing multipliers or offsets cannot be represented in SBML Level 1.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91011), "A <species> that does not identify its compartment cannot be represented in SBML Level 1.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91012), "The information represented by the value of a 'spatialSizeUnit' attribute on a <species> definition cannot be represented in SBML Level 1.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91013), "SBO terms cannot be represented directly in SBML Level 1.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91014), "SBML Level 2 Version 4 removed the requirement that all units be consistent.  This model contains units that produce inconsistencies and thus conversion to Level 1 would produce an invalid model.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91015), "This model contains conversion factors and thus conversion to earlier levels is not supported.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91016), "SBML Level 1/2 do not have the 'compartment' attribute on a Reaction.  This information will be lost in the conversion.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91017), "Conversion to SBML Level 1 requires that 'extent' units be a variant of substance).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91018), "Conversion to SBML Levels 1 and 2 requires that any global units must refer to a valid unit kind or the id of a valid unitDefinition.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91019), "The concept of a Species having only substance units and not units of concentration cannot be correctly interpreted in SBML Level 1 without alteration of any mathematical formula. LibSBML does not do this alteration).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_91020), "Conversion of the csymbol avogadro is not yet supported.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_92001), "Conversion of a model with <constraints> to SBML Level 2 Version 1 may result in loss of information.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_92002), "SBML Level 2 Version 1 does not have <initialAssignments>. The <initialAssignments> in the original model have been replaced by appropriate values.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_92003), "<speciesType> definitions cannot be represented in SBML Level 2 Version 1.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_92004), "<compartmentType> definitions cannot be represented in SBML Level 2 Version 1.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_92005), "SBO terms cannot be represented directly in SBML Level 2 Version 1.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_92006), "The 'id' attribute value of a <speciesReference> cannot be represented directly in SBML Level 2 Version 1.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_92007), "The implication of 'useValuesFromTriggerTime=false' is that there is a delay between the time of trigger and the time of value assignments performed by the <event>.  This interpretation is not supported by SBML Level 2 versions prior to Version 4. (References: L2V3 Section 4.14; L2V4 Section 4.14)).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_92008), "SBML Level 2 Version 4 removed the requirement that all units be consistent.  This model contains units that produce inconsistencies and thus conversion to Level 2 Version 1 would produce an invalid model.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_92009), "Models in SBML Level 2 Versions 1-4 can only represent compartments with integer values 0, 1, 2, or 3 as spatial dimensions.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_92010), "Conversion of a varying stoichiometry to an SBML L2 StoichiometryMath element is not yet supported.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_92011), "Event priority is not supported in SBML Level 2.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_92012), "Non persistent events are not supported in SBML Level 2.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_92013), "Initialvalue of event Triggers was assumed true in L2.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_93001), "In SBML Level 2 Version 2, an 'sboTerm' attribute is only permitted on the following elements: <model>, <functionDefinition>, <parameter>, <initialAssignment>, <rule>, <constraint>, <reaction>, <speciesReference>, <kineticLaw>, <event> and <eventAssignment>.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_93002), "The 'offset' attribute on <unit> previously available in SBML Level 2 Version 1, has been removed as of SBML Level 2 Version 2. (References: L2V2 Section 4.4.)).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_93003), "The 'timeUnits' attribute on <kineticLaw>, previously available in SBML Level 1 and Level 2 Version 1, has been removed as of SBML Level 2 Version 2. In SBML Level 2 Version 2, the time units of a reaction rate expression are those of the global 'time' units of the model. (References: L2V2 Section 4.13.5.)).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_93004), "The 'substanceUnits' attribute on <kineticLaw>, previously available in SBML Level 1 and Level 2 Version 1, has been removed as of SBML Level 2 Version 2. In SBML Level 2 Version 2, the substance units of a reaction rate expression are those of the global 'substance' units of the model. (References: L2V2 Section 4.13.5.)).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_93005), "The implication of 'useValuesFromTriggerTime=false' is that there is a delay between the time of trigger and the time of value assignments performed by the <event>.  This interpretation is not supported by SBML Level 2 versions prior to Version 4. (References: L2V3 Section 4.14; L2V4 Section 4.14)).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_93006), "In SBML Level 2 prior to Version 4 the value  of the 'sboTerm' attribute on a <model> is expected to be a term derived from SBO:0000004, \"modeling framework\"; in Version 4 and above it is expected to be a term derived from SBO:0000231 \"interaction\". Using the existing term will create an invalid  L2V2 model. (References: L2V2 Section 4.2.1; L2V3 Sections 4.2.2 and 5; L2V4 Sections 4.2.2 and 5)).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_93007), "SBML Level 2 Version 4 removed the requirement that all units be consistent.  This model contains units that produce inconsistencies and thus conversion to Level 2 Version 2 would produce an invalid model.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_93008), "SBML Level 2 Version 4 removed the requirement that all sboTerms be drawn from a given branch of SBO (http://www.biomodels.net/SBO/). This model contains sboTerms that produce inconsistencies and thus conversion to Level 2 Version 2 would produce an invalid model.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_93009), "In SBML prior to Level 2 Version 1 annotations it was permissible to have multiple top level elements using the same namespace. This is no longer valid.  (References: L2V2 Section 3.3.3; L2V3 Section 3.2.4; L2V4 Section 3.2.4.)  Any duplicate top level elements will be placed inside a new top level element named \"duplicateTopLevelElements\" with the namespace \"http://www.sbml.org/libsbml/annotation\").");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_94001), "The 'offset' attribute on <unit> previously available in SBML Level 2 Version 1, has been removed as of SBML Level 2 Version 2. (References: L2V2 Section 4.4.)).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_94002), "The 'timeUnits' attribute on <kineticLaw>, previously available in SBML Level 1 and Level 2 Version 1, has been removed as of SBML Level 2 Version 2. In SBML Level 2 Version 3, the time units of a reaction rate expression are those of the global 'time' units of the model. (References: L2V2 Section 4.13.5.)).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_94003), "The 'substanceUnits' attribute on <kineticLaw>, previously available in SBML Level 1 and Level 2 Version 1, has been removed as of SBML Level 2 Version 2. In SBML Level 2 Version 3, the substance units of a reaction rate expression are those of the global 'substance' units of the model. (References: L2V2 Section 4.13.5.)).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_94004), "The 'spatialSizeUnits' attribute on <species>, previously available in SBML Level 2 versions prior to Version 3, has been removed as of SBML Level 2 Version 3. (References: L2V3 Section 4.8; L2V4 Section 4.8)).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_94005), "The 'timeUnits' attribute on <event>, previously available in SBML Level 2 versions prior to Version 3, has been removed as of SBML Level 2 Version 3. (References: L2V3 Section 4.14; L2V4 Section 4.14)).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_94006), "The implication of 'useValuesFromTriggerTime=false' is that there is a delay between the time of trigger and the time of value assignments performed by the <event>.  This interpretation is not supported by SBML Level 2 versions prior to Version 4. (References: L2V3 Section 4.14; L2V4 Section 4.14)).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_94007), "In SBML Level 2 prior to Version 4 the value  of the 'sboTerm' attribute on a <model> is expected to be a term derived from SBO:0000004, \"modeling framework\"; in Version 4 and above it is expected to be a term derived from SBO:0000231 \"interaction\". Using the existing term will create an invalid  L2V3 model. (References: L2V2 Section 4.2.1; L2V3 Sections 4.2.2 and 5; L2V4 Sections 4.2.2 and 5)).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_94008), "SBML Level 2 Version 4 removed the requirement that all units be consistent.  This model contains units that produce inconsistencies and thus conversion to Level 2 Version 3 would produce an invalid model.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_94009), "SBML Level 2 Version 4 removed the requirement that all sboTerms be drawn from a given branch of SBO (http://www.biomodels.net/SBO/). This model contains sboTerms that produce inconsistencies and thus conversion to Level 2 Version 3 would produce an invalid model.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_94010), "In SBML prior to Level 2 Version 1 annotations it was permissible to have multiple top level elements using the same namespace. This is no longer valid.  (References: L2V2 Section 3.3.3; L2V3 Section 3.2.4; L2V4 Section 3.2.4.)  Any duplicate top level elements will be placed inside a new top level element named \"duplicateTopLevelElements\" with the namespace \"http://www.sbml.org/libsbml/annotation\").");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_95001), "The 'offset' attribute on <unit> previously available in SBML Level 2 Version 1, has been removed as of SBML Level 2 Version 2. (References: L2V2 Section 4.4.)).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_95002), "The 'timeUnits' attribute on <kineticLaw>, previously available in SBML Level 1 and Level 2 Version 1, has been removed as of SBML Level 2 Version 2. In SBML Level 2 Version 3, the time units of a reaction rate expression are those of the global 'time' units of the model. (References: L2V2 Section 4.13.5.)).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_95003), "The 'substanceUnits' attribute on <kineticLaw>, previously available in SBML Level 1 and Level 2 Version 1, has been removed as of SBML Level 2 Version 2. In SBML Level 2 Version 3, the substance units of a reaction rate expression are those of the global 'substance' units of the model. (References: L2V2 Section 4.13.5.)).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_95004), "The 'spatialSizeUnits' attribute on <species>, previously available in SBML Level 2 versions prior to Version 3, has been removed as of SBML Level 2 Version 3. (References: L2V3 Section 4.8; L2V4 Section 4.8)).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_95005), "The 'timeUnits' attribute on <event>, previously available in SBML Level 2 versions prior to Version 3, has been removed as of SBML Level 2 Version 3. (References: L2V3 Section 4.14; L2V4 Section 4.14)).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_95006), "In SBML Level 2 prior to Version 4 the value  of the 'sboTerm' attribute on a <model> is expected to be a term derived from SBO:0000004, \"modeling framework\"; in Version 4 and above it is expected to be a term derived from SBO:0000231 \"interaction\". Using the existing term will create an invalid  L2V4 model. (References: L2V2 Section 4.2.1; L2V3 Sections 4.2.2 and 5; L2V4 Sections 4.2.2 and 5)).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_95007), "In SBML prior to Level 2 Version 1 annotations it was permissible to have multiple top level elements using the same namespace. This is no longer valid.  (References: L2V2 Section 3.3.3; L2V3 Section 3.2.4; L2V4 Section 3.2.4.)  Any duplicate top level elements will be placed inside a new top level element named \"duplicateTopLevelElements\" with the namespace \"http://www.sbml.org/libsbml/annotation\").");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_96001), "<speciesType> definitions cannot be represented in SBML Level 3 Version 1.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_96002), "<compartmentType> definitions cannot be represented in SBML Level 3 Version 1.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_96003), "The 'offset' attribute on <unit> previously available in SBML Level 2 Version 1, has been removed as of SBML Level 2 Version 2. (References: L2V2 Section 4.4.)).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_96004), "The 'timeUnits' attribute on <kineticLaw>, previously available in SBML Level 1 and Level 2 Version 1, has been removed as of SBML Level 2 Version 2. In SBML Level 2 Version 3, the time units of a reaction rate expression are those of the global 'time' units of the model. (References: L2V2 Section 4.13.5.)).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_96005), "The 'substanceUnits' attribute on <kineticLaw>, previously available in SBML Level 1 and Level 2 Version 1, has been removed as of SBML Level 2 Version 2. In SBML Level 2 Version 3, the substance units of a reaction rate expression are those of the global 'substance' units of the model. (References: L2V2 Section 4.13.5.)).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_96006), "The 'spatialSizeUnits' attribute on <species>, previously available in SBML Level 2 versions prior to Version 3, has been removed as of SBML Level 2 Version 3. (References: L2V3 Section 4.8; L2V4 Section 4.8)).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_96007), "The 'timeUnits' attribute on <event>, previously available in SBML Level 2 versions prior to Version 3, has been removed as of SBML Level 2 Version 3. (References: L2V3 Section 4.14; L2V4 Section 4.14)).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_96008), "In SBML Level 2 prior to Version 4 the value  of the 'sboTerm' attribute on a <model> is expected to be a term derived from SBO:0000004, \"modeling framework\"; in Version 4 and above it is expected to be a term derived from SBO:0000231 \"interaction\". Using the existing term will create an invalid  L2V4 model. (References: L2V2 Section 4.2.1; L2V3 Sections 4.2.2 and 5; L2V4 Sections 4.2.2 and 5)).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_96009), "In SBML prior to Level 2 Version 1 annotations it was permissible to have multiple top level elements using the same namespace. This is no longer valid.  (References: L2V2 Section 3.3.3; L2V3 Section 3.2.4; L2V4 Section 3.2.4.)  Any duplicate top level elements will be placed inside a new top level element named \"duplicateTopLevelElements\" with the namespace \"http://www.sbml.org/libsbml/annotation\").");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_96010), "The 'outside' attribute on <compartment>, previously available in SBML Level 2 has been removed as of SBML Level 3 Version 1.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_96011), "Conversion of a model with <stoichiometryMath> to SBML Level 3 is not yet supported.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99101), "The level and version specified for the document must be consistent with a valid published SBML specification.  These are Level 1, Versions 1 and 2; Level 2, Versions 1, 2, 3 and 4; and Level 3, Version 1.  If newer levels or versions of SBML now exist, you must update your version of libSBML.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99104), "The <sbml> container element cannot contain <notes> or <annotations> in an SBML Level 1 document.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99106), "The math field of an assignment rule structure can contain any identifier in a MathML <ci> element except for the following: (a) identifiers for which there exists a subsequent assignment rule, and (b) the identifier for which the rule is defined. (L2V1 Section 4.8.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99107), "Every SBML Level 3 package is identified uniquely by an XML namespace URI and defines the attribute named 'required'. A value of required=true indicates that interpreting the package is necessary for complete mathematical interpretation of the model. (L3V1 Section 4.1.2) This instance of libSBML version 5 cannot find the code necessary to interpret the package information.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99108), "Every SBML Level 3 package is identified uniquely by an XML namespace URI and defines the attribute named 'required'. LibSBML has detected what appears to be an SBML Level 3 package with required=false but this instance of libSBML-5 cannot find the code necessary to parse this information.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99109), "The 'required' attribute for this package is expected to be 'false' as there is no way to change the mathematical interpretation of the model using the constructs in this package.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99127), "A KineticLaw's substanceUnits must be 'substance', 'item', 'mole', or the id of a UnitDefinition that defines a variant of 'item' or 'mole' (L2v1 Section 4.9.7).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99128), "A KineticLaw's timeUnits must be 'time', 'second', or the id of a UnitDefnition that defines a variant of 'second' with exponent='1' (L2v1 Section 4.9.7).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99129), "In a Level 1 model, only predefined functions are permitted within the formula. (L1V2 Appendix C) ).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99130), "The value of the attribute substanceUnits on a Model object should be either the units 'mole', 'item', 'avogadro', 'dimensionless', 'kilogram', 'gram', or the identifier of a UnitDefinition object based on these units (Reference: L3V1 Section 4.2.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99206), "The 'timeUnits' attribute on <event>, previously available in SBML Level 2 versions prior to Version 3, has been removed as of SBML Level 2 Version 3. (References: L2V3 Section 4.14; L2V4 Section 4.14)).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99219), "Invalid MathML. The body of an <apply> tag must begin with an operator.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99220), "Failed to read a valid double value from MathML.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99221), "Failed to read a valid integer value from MathML.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99222), "Failed to read a valid exponential value from MathML.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99223), "Failed to read a valid rational value from MathML.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99224), "Invalid MathML. The body of a <math> tag or the body of a <lambda> element must begin with one of the following: <cn>, <ci>, <csymbol>, <apply>, <piecewise>, <true>, <false>, <notanumber>, <pi>, <infinity>, <exponentiale>, <semantics>. (References: L2V3 Appendix B)).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99225), "An invalid MathML attribute has been encountered.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99301), "The csymbol 'time' should not be used within the <math> element of a <functionDefinition>. (References: L2V3 Section 4.3.2; L2V4 Section 4.3.2)).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99302), "The <lambda> element of a FunctionDefinition object must contain a function body in addition to zero or more arguments.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99303), "Where a component has an attribute that refers to a unit identifier, that attribute must refer to a unit defined in SBML or be the id of a UnitDefinition in the model.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99401), "In order to follow the general syntax for a standard SBML RDF annotation the first element of RDF element must be a Description element with an 'about' attribute (Reference: L3V1 Section 6.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99402), "In order to follow the general syntax for a standard SBML RDF annotation, the 'about' attribute of the Description element must be of the form #string (Reference: L3V1 Section 6.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99403), "In order to follow the general syntax for a standard SBML RDF annotation, the 'about' attribute of the Description element must be of the form #string, where the string component is equal to the value of the metaid attribute of the containing SBML element (Reference: L3V1 Section 6.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99404), "LibSBML expected to read the annotation into a ModelHistory object. Unfortunately, some attributes were not present or correct and the resulting ModelHistory object will not correctly produce the annotation.  This functionality will be improved in later versions of libSBML (Reference: L3V1 Section 6.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99405), "LibSBML expected to read the annotation into a ModelHistory object. Unfortunately, some attributes were not present or correct and the resulting ModelHistory object is NULL. Thus it will fail to produce the annotation.  This functionality will be improved in later versions of libSBML (Reference: L3V1 Section 6.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99406), "An annotation must contain elements; with each top-level element placed in a unique namespace (Reference: L3V1 Section 3.2.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99407), "The specified annotation RELATION element does not permit nested elements. This was introduced in L2V5 and L3V2 (Reference: L3V1 Section 6.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99505), "In situations where a mathematical expression contains literal numbers or parameters whose units have not been declared, it is not possible to verify accurately the consistency of the units in the expression.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99506), "In situations where a mathematical expression refers to time, the units of time should be consistent with the global time units of the model. In models where the 'timeUnits' attribute has not been declared, libSBML does not yet have the functionality to verify accurately the consistency of the units in such expressions.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99507), "In situations where a mathematical expression refers to extent, the units of extent should be consistent with the global extent units of the model. In models where the 'extentUnits' attribute has not been declared, libSBML does not yet have the functionality to verify accurately the consistency of the units in such expressions.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99508), "In situations where a mathematical expression refers to a compartment, species or parameter, it is necessary to know the units of the object to establish unit consistency. In models where the units of an object have not been declared, libSBML does not yet have the functionality to accurately verify the consistency of the units in mathematical expressions referring to that object.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99701), "The SBOTerm used is not recognized by libSBML and therefore the correct parentage cannot be checked. However, since libSBML is referring to a snapshot of the SBO tree the term may now exist.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99702), "The SBOTerm value used is considered obsolete.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99901), "The 'spatialDimensions' attribute on <compartment> was not available in SBML Level 1. In order for the internal representation of a <compartment> to be correct, the value for the spatialDimensions member variable should be '3'.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99902), "The 'compartmentType' attribute on <compartment> was not available in SBML Level 1 or SBML Level 2 Version 1. In order for the internal representation of a <compartment> to be correct, the value for the compartmentType member variable should be not be set.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99903), "The 'constant' attribute on <compartment> and <parameter> was not available in SBML Level 1. In order for the internal representation of <compartment> and <parameter> to be correct, the value for the constant member variable should be 'false' if the <compartment> or <parameter> is the variable of a <rule> otherwise it can be either 'true' or 'false' without affecting the interpretation of the model.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99904), "The 'metaid' attribute on all <SBase> objects was not available in SBML Level 1. In order for the internal representation to be correct, the value for the 'metaid' member variable should be not be set.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99905), "The 'sboTerm' attribute on <compartment>, <compartmentType> <delay>, <species>, <speciesType>, <stoichiometryMath>, <trigger> <unit> and <unitDefinition> was not available before SBML Level 2 Version 3. In order for the internal representation of these components to be correct, the value for the sboTerm member variable should be '-1'.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99906), "The value of the 'units' attribute on a <compartment> must be either 'volume', 'litre', 'liter' or the identifier of a <unitDefinition> based on either 'litre'/'liter' or 'metre'/'meter' (with 'exponent' equal to '3').");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99907), "A value for the compartment 'volume' attribute must be specified in this Level+Version of SBML.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99908), "The <compartmentType> component was introduced in SBML Level 2 Version 2. In order for the internal representation of a <model> to be correct, the <listOfCompartmentTypes> component should be empty.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99909), "The <constraint> component was introduced in SBML Level 2 Version 2. In order for the internal representation of a <model> to be correct, the <listOfConstraints> component should be empty.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99910), "The <event> component was introduced in SBML Level 2 Version 1. In order for the internal representation of a <model> to be correct, the <listOfEvents> component should be empty.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99911), "The 'sboTerm' attribute on <event>, <eventAssignment> <functionDefinition>, <kineticLaw>, <model>, <parameter>, <reaction> <rule> and <speciesReferenece> was not available before SBML Level 2 Version 2. In order for the internal representation of these components to be correct, the value for the sboTerm member variable should be '-1'.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99912), "The <functionDefinition> component was introduced in SBML Level 2 Version 1. In order for the internal representation of a <model> to be correct, the <listOfFunctionDefinitions> component should be empty.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99913), "The <initialAssignment> component was introduced in SBML Level 2 Version 2. In order for the internal respresentation of a <model> to be correct, the <listOfInitialAssignments> component should be empty.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99914), "An <algebraicRule> does not assign a value to a particular variable and does not carry an attribute named 'variable'.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99915), "The 'units' attribute on <rule> was only applicable to a <parameterRule> in SBML Level 1. In order for the internal respresentation of a <rule> to be correct, the value for the units member variable should not be set.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99916), "The 'constant' attribute on <species> was not available in SBML Level 1. In order for the internal respresentation of <species> to be correct, the value for the constant member variable should be 'false' if (1) the <species> is the variable of a <rule> or (2) the 'boundaryCondition' attribute is 'false' and the <species> is a product/reactant in a <reaction>. Otherwise it can be either 'true' or 'false' without affecting the interpretation of the model.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99917), "The 'spatialSizeUnits' attribute on <species> was not available in SBML Level 1. In order for the internal respresentation of a <species> to be correct, the value for the spatialSizeUnits member variable should be not be set.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99918), "The 'speciesType' attribute on <species> was not available in SBML Level 1 or SBML Level 2 Version 1. In order for the internal respresentation of a <species> to be correct, the value for the speciesType member variable should be not be set.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99919), "The 'hasOnlySubstanceUnits' attribute on <species> was not available in SBML Level 1. In order for the internal respresentation of a <species> to be correct, the value for the hasOnlySubstanceUnits member variable should be 'false'.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99920), "The 'id' attribute on <speciesReference> was not available in SBML Level 1 or SBML Level 2 Version 1. In order for the internal respresentation of a <speciesReference> to be correct, the value for the id member variable should be not be set.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99921), "The 'name' attribute on <speciesReference> was not available in SBML Level 1 or SBML Level 2 Version 1. In order for the internal respresentation of a <speciesReference> to be correct, the value for the name member variable should be not be set.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99922), "The <speciesType> component was introduced in SBML Level 2 Version 2. In order for the internal respresentation of a <model> to be correct, the <listOfSpeciesTypes> component should be empty.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99923), "The <stoichiometryMath> component on a <speciesReference> was introduced in SBML Level 2. In order for the internal respresentation of a <speciesReference> to be correct, the <stoichiometryMath> component should be 'NULL'.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99924), "The 'multiplier' attribute on <unit> was not available in SBML Level 1. In order for the internal respresentation of a <unit> to be correct, the value for the multiplier member variable should be '1.0'.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99925), "The 'offset' attribute on <unit> was only available in SBML Level 2 Version 1. In order for the internal respresentation of a <unit> to be correct, the value for the offset member variable should be '0'.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99926), "The 'spatialDimensions' attribute on <compartment> was left unset on the SBML Level 3 model.  Conversion will apply a default value of '3'.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99994), "");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99995), "");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99996), "Conversion of SBML Level 3 package information is not yet supported.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99997), "Conversion is only possible to an existing SBML Level and Version.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99998), "Validation of or conversion from L3 documents/models is not yet supported.");  

        contents.put(Integer.toString(SBMLErrorCodes.CORE_99999), "");  
        
        
        contents.put(Integer.toString(SBMLErrorCodes.CORE_10223), "The single argument for the rateOf csymbol function must be a <ci> element."); 
        contents.put(Integer.toString(SBMLErrorCodes.CORE_10224), "The target of a 'rateOf' <csymbol> function must not appear as the 'variable' of an <assignmentRule>, nor may its value be determined by an <algebraicRule>.");
        contents.put(Integer.toString(SBMLErrorCodes.CORE_10225), "If the target of a 'rateOf' <csymbol> function is a <species> with a 'hasOnlySubstanceUnits' value of 'false', the <compartment> of that <species> must not appear as the 'variable' of an <assignmentRule>, nor may its 'size' be determined by an <algebraicRule>.");

        

        contents.put(Integer.toString(SBMLErrorCodes.COMP_10100), "");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_10101), "To conform to Version 1 of the Hierarchical Model Composition package specification for SBML Level 3, an SBML document must declare the use of the following XML Namespace: 'http://www.sbml.org/sbml/level3/version1/comp/version1').Reference: L3V1 Comp V1 Section 3.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_10102), "Wherever they appear in an SBML document, elements and attributes from the Hierarchical Model Composition package must be declared either implicitly or explicitly to be in the XML namespace 'http://www.sbml.org/sbml/level3/version1/comp/version1').Reference: L3V1 Comp V1 Section 3.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_10301), "(Extends validation rule #10301 in the SBML Level 3 Version 1 Core specification.) Within a <model> or <modelDefinition> object, the values of the attributes id and comp:id on every instance of the following classes of objects must be unique across the set of all id and comp:id attribute values of all such objects in a model: the model itself, plus all contained <functionDefinition>, <compartment>, <species>, <reaction>, <speciesReference>, <modifierSpeciesReference>, <event>, and <parameter> objects, plus the <submodel> and <deletion> objects defined by the Hierarchical Model Composition package, plus any objects defined by any other package with 'package:id' attributes defined as falling in the 'SId' namespace (Reference: L3V1 Comp V1 Section 3.9).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_10302), "The values of the attributes 'id' and 'comp:id' on every instance of all <model>, <modelDefinition>, and <externalModelDefinition> objects must be unique across the set of all 'id' and 'comp:id' attribute values of such objects in the SBML document to which they belong (Reference: L3V1 Comp V1 Section 3.9).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_10303), "Within <model> and <modelDefinition> objects inside an SBML document, the value of the attribute 'comp:id' on every instance of a <port> object must be unique across the set of all 'comp:id' attribute values of all such objects in the model (Reference: L3V1 Comp V1 Section 3.9).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_10304), "The value of a comp:id attribute must always conform to the syntax of the SBML data type SId (Reference: L3V1 Core Section 3.1.7).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_10308), "The value of a 'comp:submodelRef' attribute on <replacedElement> and <replacedBy> objects must always conform to the syntax of the SBML data type SId (Reference: L3V1 Comp V1 Section 3.6.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_10309), "The value of a 'comp:deletion' attribute on <replacedElement> objects must always conform to the syntax of the SBML data type SId (Reference: L3V1 Comp V1 Section 3.6.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_10310), "The value of a 'comp:conversionFactor' attribute on <replacedElement> objects must always conform to the syntax of the SBML data type SId (Reference: L3V1 Comp V1 Section 3.6.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_10311), "The value of a 'comp:name' attribute must always conform to the syntax of type string (Reference: L3V1 Core Section 3.1.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_10501), "If one element replaces another, whether it is the target of a <replacedBy> element, or whether it has a child <replacedElement>, the units of the replaced element, multiplied by the units of any applicable conversion factor, should equal the units of the replacement element (Reference: L3V1 Comp V1 Section 3.6.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20101), "Any object derived from the extended SBase class (defined in the Hierarchical Model Composition package) may contain at most one instance of a <listOfReplacedElements> subobject (Reference: L3V1 Comp V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20102), "Apart from the general notes and annotation subobjects permitted on all SBML objects, a <listOfReplacedElements> container object may only contain <replacedElement> objects (Reference: L3V1 Comp V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20103), "A <listOfReplacedElements> object may have the optional attributes 'metaid' and 'sboTerm' defined by SBML Level 3 Core. No other attributes from the SBML Level 3 Core namespace or the HierarchicalModel Composition namespace are permitted on a <listOfReplacedElements> object (Reference: L3V1 Comp V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20104), "The <listOfReplacedElements> in an SBase object is optional, but if present, must not be empty (Reference: L3V1 Comp V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20105), "Any object derived from the extended SBase class (defined in the Hierarchical Model Composition package) may contain at most one instance of a <replacedBy> subobject (Reference: L3V1 Comp V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20201), "In all SBML documents using the HierarchicalModel Composition package, the SBML object must include a value for the attribute 'comp:required' attribute (Reference: L3V1 Core Section 4.1.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20202), "The value of attribute 'comp:required' on the SBML object must be of the data type Boolean (Reference: L3V1 Core Section 4.1.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20203), "The value of attribute 'comp:required' on the SBML object must be set to 'true' if the Model object within the SBML object contains any Submodel with Species, Parameter, Compartment, Reaction, or Event objects that have not been either directly or indirectly replaced (Reference: L3V1 Comp V1 Section 3.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20204), "The value of attribute 'comp:required' on the SBML object must be set to 'false' if the Model object within the SBML object contains no Submodel objects, or if all Submodel objects that are present contain no Species, Parameter, Compartment, Reaction, or Event objects that have not been either directly or indirectly replaced (Reference: L3V1 Comp V1 Section 3.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20205), "There may be at most one instance of the <listOfModelDefinitions> within an SBML object that uses the SBML Level 3 Hierarchical Model Composition package (Reference: L3V1 Comp V1 Section 3.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20206), "The various 'ListOf' subobjects within an SBML object are optional, but if present, these container objects must not be empty. Specifically, if any of the following classes of objects is present within the SBML object, it must not be empty: <listOfModelDefinitions> and <listOfExternalModelDefinitions> (Reference: L3V1 Comp V1 Section 3.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20207), "Apart from the general notes and annotation subobjects permitted on all SBML objects, a <listOfModelDefinitions> container may only contain <modelDefinition> objects (Reference: L3V1 Comp V1 Section 3.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20208), "Apart from the general notes and annotation subobjects permitted on all SBML objects, a <listOfExternalModelDefinitions> container may only contain <externalModelDefinition> objects (Reference: L3V1 Comp V1 Section 3.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20209), "A <listOfModelDefinitions> object may have the optional attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace or the HierarchicalModel Composition namespace are permitted on a <listOfModelDefinitions> object (Reference: L3V1 Comp V1 Section 3.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20210), "A <listOfExternalModelDefinitions> object may have the optional attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace or the HierarchicalModel Composition namespace are permitted on a <listOfExternalModelDefinitions> object (Reference: L3V1 Comp V1 Section 3.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20211), "There may be at most one instance of the <listOfExternalModelDefinitions> within an SBML object that uses the SBML Level 3 Hierarchical Model Composition package (Reference: L3V1 Comp V1 Section 3.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20212), "The value of attribute 'comp:required' on the SBML object must be set to 'true' (Reference: L3V1 Comp V1 Section 3.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20301), "An <externalModelDefinition> object may have the optional SBML Level 3 Core attributes 'metaid' and 'sboTerm'.  No other attributes from the SBML Level 3 Core namespace are permitted on an <externalModelDefinition> object (Reference: L3V1 Comp V1 Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20302), "An <externalModelDefinition> object may have the optional SBML Level 3 Core subobjects for notes and annotation.  No other subobjects from the SBML Level 3 Core namespace or the Hierarchical Model Composition namespace are permitted on an <externalModelDefinition> object (Reference: L3V1 Comp V1 Section 3.3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20303), "An <externalModelDefinition> object must have the attributes 'comp:id' and 'comp:source', and may have the optional attributes 'comp:name', 'comp:modelRef', and 'comp:md5'. No other attributes from the Hierarchical Model Composition namespace are permitted on an <externalModelDefinition> object (Reference: L3V1 Comp V1 Section 3.3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20304), "The value of the 'comp:source' attribute on an <externalModelDefinition> object must reference an SBML Level 3 Version 1 document (Reference: L3V1 Comp V1 Section 3.3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20305), "The value of the 'comp:modelRef' attribute on an <externalModelDefinition> object must be the value of an id attribute on a <model>, <modelDefinition>, or <externalModelDefinition> object in the SBML document referenced by the 'comp:source' attribute (Reference: L3V1 Comp V1 Section 3.3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20306), "The value of the 'comp:md5' attribute, if present on an <externalModelDefinition> object, should match the calculated MD5 checksum of the SBML document referenced by the 'comp:source' attribute (Reference: L3V1 Comp V1 Section 3.3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20307), "The value of a 'comp:source' attribute on an <externalModelDefinition> object must always conform to the syntax of the XML Schema 1.0 data type 'anyURI' (Reference: L3V1 Comp V1 Section 3.3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20308), "The value of a comp:modelRef attribute on an <externalModelDefinition> object must always conform to the syntax of the SBML data type SId (Reference: L3V1 Comp V1 Section 3.3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20309), "The value of a comp:md5 attribute on an <externalModelDefinition> object must always conform to the syntax of type string (Reference: L3V1 Comp V1 Section 3.3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20310), "An <externalModelDefinition> object must not reference an <externalModelDefinition> in a different SBML document that, in turn, refers back to the original <externalModelDefinition object, whether directly or indirectly through a chain of <externalModelDefinition> objects (Reference: L3V1 Comp V1 Section 3.3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20501), "There may be at most one instance of each of the following kinds of objects within a <model> or <modelDefinition> object using Hierarchical Model Composition: <listOfSubmodels> and <listOfPorts> (Reference: L3V1 Comp V1 Section 3.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20502), "The various ListOf subobjects with a <model> object are optional, but if present, these container object must not be empty.  Specifically, if any of the following classes of objects are present on the <model>, it must not be empty: <listOfSubmodels> and <listOfPorts> (Reference: L3V1 Comp V1 Section 3.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20503), "Apart from the general notes and annotation subobjects permitted on all SBML objects, a <listOfSubmodels> container object may only contain <submodel> objects (Reference: L3V1 Comp V1 Section 3.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20504), "Apart from the general notes and annotation subobjects permitted on all SBML objects, a <listOfPorts> container object may only contain <port> objects (Reference: L3V1 Comp V1 Section 3.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20505), "A <listOfSubmodels> object may have the optional attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace or the HierarchicalModel Composition namespace are permitted on a <listOfSubmodels> object (Reference: L3V1 Comp V1 Section 3.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20506), "A <listOfPorts> object may have the optional attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace or the HierarchicalModel Composition namespace are permitted on a <listOfPorts> object (Reference: L3V1 Comp V1 Section 3.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20601), "A <submodel> object may have the optional SBML Level 3 Core attributes 'metaid' and 'sboTerm'.  No other attributes from the SBML Level 3 Core namespace are permitted on a <submodel> object (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20602), "A <submodel> object may have the optional SBML Level 3 Core subobjects for notes and annotations.  No other elements from the SBML Level 3 Core namespace are permitted on a <submodel> object (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20603), "There may be at most one <listOfDeletions> container object within a <submodel> object (Reference: L3V1 Comp V1 Section 3.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20604), "A <listOfDeletions> container object within a <submodel> object is optional, but if present, must not be empty (Reference: L3V1 Comp V1 Section 3.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20605), "Apart from the general notes and annotation subobjects permitted on all SBML objects, a <listOfDeletions> container object may only contain <deletion> objects (Reference: L3V1 Comp V1 Section 3.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20606), "A <listOfDeletions> object may have the optional attributes 'metaid' and 'sboTerm' defined by SBML Level 3 Core. No other attributes from the SBML Level 3 Core namespace or the HierarchicalModel Composition namespace are permitted on a <listOfReplacedElements> object (Reference: L3V1 Comp V1 Section 3.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20607), "A <submodel> object must have the attributes 'comp:id' and 'comp:modelRef' because they are required, and may also have the optional attributes 'comp:name', 'comp:timeConversionFactor, and/or 'comp:extentConversionFactor'.  No other attributes from the Hierarchical Model Composition namespace are permitted on a <submodel> object (Reference: L3V1 Comp V1 Section 3.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20608), "The value of a 'comp:modelRef' attribute on a <submodel> object must always conform to the syntax of the SBML data type SId (Reference: L3V1 Comp V1 Section 3.5.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20613), "The value of a 'comp:timeConversionFactor' attribute on a <submodel> object must always conform to the syntax of the SBML data type SId (Reference: L3V1 Comp V1 Section 3.5.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20614), "The value of a 'comp:extentConversionFactor' attribute on a <submodel> object must always conform to the syntax of the SBML data type SId (Reference: L3V1 Comp V1 Section 3.5.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20615), "The value of a 'comp:modelRef' attribute on a <submodel> must be the identifier of a <model>, <modelDefinition>, or <externalModelDefinition> object in the same SBML object as the <submodel> (Reference: L3V1 Comp V1 Section 3.5.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20616), "A <model> or <modelDefinition> object must not contain a <submodel> which references that model object itself. That is, the value of a 'comp:modelRef' attribute on a <submodel> must not be the value of the parent <model> or <modelDefinition>'s 'id' attribute (Reference: L3V1 Comp V1 Section 3.5.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20617), "A <model> object must not contain a <submodel> which references that <model> indirectly. That is, the 'comp:modelRef' attribute of a <submodel> may not point to the 'id' of a <model> containing a <submodel> object that references the original <model> directly or indirectly through a chain of <model>/<submodel> pairs (Reference: L3V1 Comp V1 Section 3.5.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20622), "The value of a 'comp:timeConversionFactor' attribute on a given <submodel> object must be the identifier of a <parameter> object defined in the same Model containing the <submodel> (Reference: L3V1 Comp V1 Section 3.5.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20623), "The value of a 'comp:extentConversionFactor' attribute on a given <submodel> object must be the identifier of a <parameter> object defined in the same Model containing the <submodel> (Reference: L3V1 Comp V1 Section 3.5.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20701), "The value of a 'comp:portRef' attribute on an <sBaseRef> object must be the identifier of a <port> object in the <model> referenced by that <sBaseRef> (Reference: L3V1 Comp V1 Section 3.7.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20702), "The value of a 'comp:idRef' attribute on an <sBaseRef> object must be the identifier of an object contained in (that is, within the SId namespace of) the <model> referenced by that <sBaseRef>. This includes objects with 'id' attributes defined in packages other than SBML Level 3 Core or the Hierarchical Model Composition package (Reference: L3V1 Comp V1 Section 3.7.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20703), "The value of a 'comp:unitRef' attribute on an <sBaseRef> object must be the identifier of a <unitDefinition< object contained in the <model> referenced by that <sBaseRef> (Reference: L3V1 Comp V1 Section 3.7.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20704), "The value of a 'comp:metaIdRef' attribute on an <sBaseRef> object must be the value of a 'comp:metaid' attribute on an element contained in the <model> referenced by that <sBaseRef> (Reference: L3V1 Comp V1 Section 3.7.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20705), "If an <sBaseRef> object contains an <sBaseRef> child, the parent <sBaseRef> must point to a <submodel> object, or a <port> that itself points to a <submodel> object (Reference: L3V1 Comp V1 Section 3.7.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20706), "The value of a 'comp:portRef' attribute on an SBaseRef object must always conform to the syntax of the SBML data type SId (Reference: L3V1 Comp V1 Section 3.7.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20707), "The value of a 'comp:idRef' attribute on an SBaseRef object must always conform to the syntax of the SBML data type SId (Reference: L3V1 Comp V1 Section 3.7.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20708), "The value of a 'comp:unitRef' attribute on an SBaseRef object must always conform to the syntax of the SBML data type SId (Reference: L3V1 Comp V1 Section 3.7.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20709), "The value of a 'comp:metaIdRef' attribute on an SBaseRef object must always conform to the syntax of the XML data type ID (Reference: L3V1 Comp V1 Section 3.7.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20710), "Apart from the general notes and annotation subobjects permitted on all SBML objects, an SBaseRef object may only contain a single <sBaseRef> child (Reference: L3V1 Comp V1 Section 3.7.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20711), "The 'sbaseRef' spelling of an SBaseRef child of an SBaseRef object is considered deprecated, and 'sBaseRef' should be used instead (Reference: L3V1 Comp V1 Section 3.7.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20712), "An <sBaseRef> object must point to another object; that is, a <sBaseRef> object must always have a value for one of the attributes 'comp:portRef', 'comp:idRef', 'comp:unitRef', or 'comp:metaIdRef' (Reference: L3V1 Comp V1 Section 3.7.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20713), "An <sBaseRef> object can only point to one other object; that is, a given <sBaseRef> object can only have a value for one of the attributes 'comp:portRef', 'comp:idRef', 'comp:unitRef', or 'comp:metaIdRef' (Reference: L3V1 Comp V1 Section 3.7.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20714), "Any one SBML object may only be referenced in one of the following ways:  referenced by a single <port> object; referenced by a single <deletion> object; referenced by a single <replacedElement>; be the parent of a single <replacedBy> child; be referenced by one or more <replacedBy> objects; or be referenced by one or more <replacedElement> objects all using the 'deletion' attribute.  Essentially, once an object has been referenced in one of these ways it cannot be referenced again (Reference: L3V1 Comp V1 Section 3.7.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20801), "A <port> object must point to another object; that is, a <port> object must always have a value for one of the attributes 'comp:idRef', 'comp:unitRef', or 'comp:metaIdRef' (Reference: L3V1 Comp V1 Section 3.4.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20802), "A <port> object can only point to one other object; that is, a given <port> object can only have a value for one of the attributes 'comp:idRef', 'comp:unitRef', or 'comp:metaIdRef' (Reference: L3V1 Comp V1 Section 3.4.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20803), "A <port> object must have a value for the required attribute 'comp:id', and one, and only one, of the attributes 'comp:idRef', 'comp:unitRef', or 'comp:metaIdRef'.  No other attributes from the Hierarchical Model Composition namespace are permitted on a <port> object (Reference: L3V1 Comp V1 Section 3.4.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20804), "Port definitions must be unique; that is, no two <port> objects in a given Model may reference the same object in that Model (Reference: L3V1 Comp V1 Section 3.4.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20901), "A <deletion> object must point to another object; that is, a <deletion> object must always have a value for one of the attributes 'comp:portRef', 'comp:idRef', 'comp:unitRef', or 'comp:metaIdRef').Reference: L3V1 Comp V1 Section 3.5.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20902), "A <deletion> object can only point to one other object; that is, a given <deletion> object can only have a value for one of the attributes 'comp:portRef', 'comp:idRef', 'comp:unitRef', or 'comp:metaIdRef').Reference: L3V1 Comp V1 Section 3.5.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_20903), "A <deletion> object must have a value for one, and only one, of the attributes 'comp:portRef', 'comp:idRef', 'comp:unitRef', and 'comp:metaIdRef'.  It may also have the optional attributes 'comp:id' and 'comp:name'.  No other attributes from the Hierarchical Model Composition namespace are permitted on a <deletion> object (Reference: L3V1 Comp V1 Section 3.5.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_21001), "A <replacedElement> object must point to another object; that is, a <replacedElement> object must always have a value for one of the attributes 'comp:portRef', 'comp:idRef', 'comp:unitRef', 'comp:metaIdRef' or 'comp:deletion' (Reference: L3V1 Comp V1 Section 3.6.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_21002), "A <replacedElement> object can only point to one other object; that is, a given <replacedElement> object can only have a value for one of the attributes 'comp:portRef', 'comp:idRef', 'comp:unitRef', 'comp:metaIdRef' or 'comp:deletion' (Reference: L3V1 Comp V1 Section 3.6.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_21003), "A <replacedElement> object must have a value for the required attribute 'comp:submodelRef', and a value for one, and only one, of the following attributes: 'comp:portRef', 'comp:idRef', 'comp:unitRef', 'comp:metaIdRef', or 'comp:deletion'. It may also have a value for the optional attribute 'comp:conversionFactor'. No other attributes from the HierarchicalModel Composition namespace are permitted on a <replacedElement> object (Reference: L3V1 Comp V1 Section 3.6.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_21004), "The value of a 'comp:submodelRef' attribute on a <replacedElement> object must be the identifier of a <submodel> present in the <replacedElement> object's parent Model (Reference: L3V1 Comp V1 Section 3.6.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_21005), "The value of a 'comp:deletion' attribute on a <replacedElement> object must be the identifier of a <deletion> present in the <replacedElement> object's parent Model (Reference: L3V1 Comp V1 Section 3.6.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_21006), "The value of a 'comp:conversionFactor' attribute on a <replacedElement> object must be the identifier of a <parameter> present in the <replacedElement> object's parent Model).Reference: L3V1 Comp V1 Section 3.6.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_21010), "No two <replacedElement> objects in the same Model may reference the same object unless that object is a <deletion>).Reference: L3V1 Comp V1 Section 3.6.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_21011), "If a <replacedElement> uses the 'comp:deletion' attribute, then it should not also use the 'comp:conversionFactor' attribute (Reference: L3V1 Comp V1 Section 3.6.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_21101), "A <replacedBy> object must point to another object; that is, a <replacedBy> object must always have a value for one of the attributes 'comp:portRef', 'comp:idRef', 'comp:unitRef' or 'comp:metaIdRef' (Reference: L3V1 Comp V1 Section 3.6.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_21102), "A <replacedBy> object can only point to one other object; that is, a given <replacedBy> object can only have a value for one of the attributes 'comp:portRef', 'comp:idRef', 'comp:unitRef', or 'comp:metaIdRef' (Reference: L3V1 Comp V1 Section 3.6.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_21103), "A <replacedBy> object must have a value for the required attribute 'comp:submodelRef', and a value for one, and only one, of the following attributes: 'comp:portRef', 'comp:idRef', 'comp:unitRef' or 'comp:metaIdRef'. No other attributes from the HierarchicalModel Composition namespace are permitted on a <replacedElement> object (Reference: L3V1 Comp V1 Section 3.6.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_21104), "The value of a 'comp:submodelRef' attribute on a <replacedBy> object must be the identifier of a <submodel> present in the <replacedBy> object's parent Model (Reference: L3V1 Comp V1 Section 3.6.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_21201), "If one element replaces another, whether it is the target of a <replacedBy> element, or whether it has a child <replacedElement>, the SBML class of the replacement element must match the SBML class of the replaced element, with two exceptions: an element of a derived class may replace an object of its base class (for base classes other than SBase), and any SBML class with mathematical meaning may replace a <parameter>. A base class may not replace a derived class, however, nor may a <parameter> replace some other SBML element with mathematical meaning (Reference: L3V1 Comp V1 Section 3.6.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_21202), "If one element replaces another, whether it is the target of a <replacedBy> element, or whether it has a child <replacedElement>, if the replaced element has the 'id' attribute set, the replacement !element must also have the 'id' attribute set (Reference: L3V1 Comp V1 Section 3.6.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_21203), "If one element replaces another, whether it is the target of a <replacedBy> element, or whether it has a child <replacedElement>, if the replaced element has the 'metaid' attribute set, the replacement element must also have the 'metaid' attribute set (Reference: L3V1 Comp V1 Section 3.6.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_21204), "If one element replaces another, whether it is the target of a <replacedBy> element, or whether it has a child <replacedElement>, if the replaced element has an identifier attribute from some other SBML package set, the replacement element must also have that same identifier attribute set (Reference: L3V1 Comp V1 Section 3.6.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_90101), "The external model referenced in this model could not be resolved.");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_90102), "The external document referenced in this model did not contain any models.");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_90103), "An external model definition referenced by an external model definition in this model was itself unresolvable.");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_90104), "Errors arose during the attempt to flatten the model.");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_90105), "The interpretation the Hierarchical Model Composition constructs to produce a kind of 'flattened' version of the model devoid of the comp package constructs must produce a valid SBML Level 3 model (Reference: L3V1 Comp V1 Appendix A1).");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_90106), "Due to the need to instantiate models, modelDefinitions, submodels etc. for the purposes of validation it is problematic to reliably report line numbers when performing validation on models using the Hierarchical Model Composition package.");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_90107), "The CompFlatteningConverter has encountered a required package for which libSBML does not recognize the information.");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_90108), "The CompFlatteningConverter has encountered an unrequired package for which libSBML does not recognize the information.");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_90109), "The CompFlatteningConverter has encountered an unrequired package for which the necessary routines to allow flattening have not yet been implemented.");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_90110), "The CompFlatteningConverter has encountered a required package for which the necessary routines to allow flattening have not yet been implemented.");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_90111), "The CompFlatteningConverter has encountered an unknown reference which may be due to the presence of an unknown package.");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_90112), "The software used to process this hierarchical model used the deprecated function performDeletions to do so.  Unfortunately, it is impossible to properly use this function as it was originally designed, without some models either causing the program to crash, or causing them to be interpreted incorrectly.  Instead, the software should use collectDeletionsAndDeleteCompConstructs, in conjunction with collectRenameAndConvertReplacements and removeCollectedElements to properly process hierarchical models.");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_90113), "The software used to process this hierarchical model used the deprecated function performReplacementsAndConversions to do so.  Unfortunately, it is impossible to properly use this function as it was originally designed, without some models either causing the program to crash, or causing them to be interpreted incorrectly.  Instead, the software should use collectDeletionsAndDeleteCompConstructs, in conjunction with collectRenameAndConvertReplacements and removeCollectedElements to properly process hierarchical models.");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_90114), "The model contained a deletion whose subelement was replaced.  This is perfectly legal, but unfortunately, the subroutine used to implement this actually removed the deleted element and all of its children before replacing the child, making it impossible to discover any IDs that need to be replaced.");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_90115), "The value of a 'comp:idRef' attribute on an <sBaseRef> object must be the identifier of an object contained in (that is, within the SId namespace of) the <model> referenced by that <sBaseRef>. This includes objects with 'id' attributes defined in packages other than SBML Level 3 Core or the Hierarchical Model Composition package.");  

        contents.put(Integer.toString(SBMLErrorCodes.COMP_90116), "The value of a 'comp:metaIdRef' attribute on an <sBaseRef> object must be the value of a 'comp:metaid' attribute on an element contained in the <model> referenced by that <sBaseRef>.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_10100), "");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_10101), "To conform to Version 1 of the Flux Balance Constraints package specification for SBML Level 3, an SBML document must declare the use of the following XML Namespace: 'http://www.sbml.org/sbml/level3/version1/fbc/version1').Reference: L3V1 Fbc V1 Section 3.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_10102), "Wherever they appear in an SBML document, elements and attributes from the Flux Balance Constraints package must be declared either implicitly or explicitly to be in the XML namespace 'http://www.sbml.org/sbml/level3/version1/fbc/version1').Reference: L3V1 Fbc V1 Section 3.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_10301), "(Extends validation rule #10301 in the SBML Level 3 Version 1 Core specification.) Within a <model> object the values of the attributes id and fbc:id on every instance of the following classes of objects must be unique across the set of all id and fbc:id attribute values of all such objects in a model: the model itself, plus all contained <functionDefinition>, <compartment>, <species>, <reaction>, <speciesReference>, <modifierSpeciesReference>, <event>, and <parameter> objects, plus the <fluxBound>, <objective>, <fluxObjective>, <geneProduct> and <geneProductAssociation> objects defined by the Flux Balance Constraints package (Reference: L3V1 Fbc V1 Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_10302), "The value of a fbc:id attribute must always conform to the syntax of the SBML data type SId (Reference: L3V1 Fbc V1 Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20101), "In all SBML documents using the Flux Balance Constraints package, the SBML object must include a value for the attribute 'fbc:required (Reference: L3V1 Core Section 4.1.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20102), "The value of attribute 'fbc:required' on the SBML object must be of the data type Boolean (Reference: L3V1 Core Section 4.1.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20103), "The value of attribute 'fbc:required' on the SBML object must be set to 'false' (Reference: L3V1 Fbc V1 Section 3.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20201), "There may be at most one instance of each of the following kinds of objects within a <model> object using Flux Balance Constraints: <listOfFluxBounds> (V1 only), <listOfObjectives> and <listOfGeneProducts> (V2) (Reference: L3V1 Fbc V1 Section 3.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20202), "The various ListOf subobjects with a <model> object are optional, but if present, these container object must not be empty. Specifically, if any of the following classes of objects are present on the <model>, it must not be empty: <listOfFluxBounds> (V1 only), <listOfObjectives> and <listOfGeneProducts> (V2) (Reference: L3V1 Fbc V1 Section 3.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20203), "Apart from the general notes and annotation subobjects permitted on all SBML objects, a <listOfFluxBounds> container object may only contain <fluxBound> objects (Reference: L3V1 Fbc V1 Section 3.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20204), "Apart from the general notes and annotation subobjects permitted on all SBML objects, a <listOfObjectives> container object may only contain <objective> objects (Reference: L3V1 Fbc V1 Section 3.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20205), "A <listOfFluxBounds> object may have the optional attributes 'metaid' and 'sboTerm' defined by SBML Level 3 Core. No other attributes from the SBML Level 3 Core namespace or the Flux Balance Constraints namespace are permitted on a <listOfFluxBounds> object (Reference: L3V1 Fbc V1 Section 3.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20206), "A <listOfFluxBounds> object may have the optional attributes 'metaid' and 'sboTerm' defined by SBML Level 3 Core. Additionally the <listOfObjectives> must contain the attribute 'activeObjective'. No other attributes from the SBML Level 3 Core namespace or the Flux Balance Constraints namespace are permitted on a <listOfFluxBounds> object (Reference: L3V1 Fbc V1 Section 3.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20207), "The value of attribute 'fbc:activeObjective' on the <listOfObjectives> object must be of the data type SIdRef (Reference: L3V1 Fbc V1 Section 3.2.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20208), "The value of attribute 'fbc:activeObjective' on the <listOfObjectives> object must be the identifier of an existing <objective> (Reference: L3V1 Fbc V1 Section 3.2.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20209), "A <model> object must have the required attributes 'strict'. No other attributes from the Flux Balance Constraints namespace are permitted on a <model> object.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20210), "The attribute 'fbc:strict' on the <model> object must have a value of datatype boolean.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20211), "Apart from the general notes and annotation subobjects permitted on all SBML objects, a <listOfGeneProducts> container object may only contain <geneProduct> objects.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20212), "A <listOfGeneProducts> object may have the optional attributes 'metaid' and 'sboTerm' defined by SBML Level 3 Core. No other attributes from the SBML Level 3 Core namespace or the Flux Balance Constraints namespace are permitted on a <listOfGeneProducts> object.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20301), "A SBML <species> object may have the optional attributes 'fbc:charge' and 'fbc:chemicalFormula'. No other attributes from the Flux Balance Constraints namespaces are permitted on a <species> (Reference: L3V1 Fbc V1 Section 3.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20302), "The value of attribute 'fbc:charge' on SBML <species> object must be of the data type integer (Reference: L3V1 Fbc V1 Section 3.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20303), "The value of attribute 'fbc:chemicalFormula' on the SBML <species> object must be set to a string consisting only of atomic names or user defined compounds and their occurrence (Reference: L3V1 Fbc V1 Section 3.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20401), "A <fluxBound> object may have the optional SBML Level 3 Core attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a <fluxBound> (Reference: L3V1 Core, Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20402), "A <fluxBound> object may have the optional SBML Level 3 Core subobjects for notes and annotations. No other elements from the SBML Level 3 Core namespace are permitted on a <fluxBound> (Reference: L3V1 Core, Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20403), "A <fluxBound> object must have the required attributes 'fbc:reaction', 'fbc:operation' and 'fbc:value', and may have the optional attributes 'fbc:id' and 'fbc:name'. No other attributes from the SBML Level 3 Flux Balance Constraints namespace are permitted on a <fluxBound> object (Reference: L3V1 Fbc V1 Section 3.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20404), "The attribute 'fbc:reaction' of a <fluxBound> must be of the data type SIdRef (Reference: L3V1 Fbc V1 Section 3.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20405), "The attribute 'fbc:name' of a <fluxBound> must be of the data type string (Reference: L3V1 Fbc V1 Section 3.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20406), "The attribute 'fbc:operation' of a <fluxBound> must be of the data type FbcOperation and thus it's value must be one of 'lessEqual', 'greaterEqual' or 'equal' (Reference: L3V1 Fbc V1 Section 3.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20407), "The attribute 'fbc:value' of a <fluxBound> must be of the data type double (Reference: L3V1 Fbc V1 Section 3.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20408), "The value of the attribute 'fbc:reaction' of a <fluxBound> object must be the identifier of an existing <reaction> object defined in the enclosing <model> object (Reference: L3V1 Fbc V1 Section 3.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20409), "The combined set of all <fluxBound>'s with identical values for 'fbc:reaction' must be consistent. That is while it is possible to define a lower and an upper bound for a reaction, it is not possible to define multiple lower or upper bounds (Reference: L3V1 Fbc V1 Section 3.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20501), "An <objective> object may have the optional SBML Level 3 Core attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on an <objective> (Reference: L3V1 Core, Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20502), "An <objective> object may have the optional SBML Level 3 Core subobjects for notes and annotations. No other elements from the SBML Level 3 Core namespace are permitted on an <objective> (Reference: L3V1 Core, Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20503), "An <objective> object must have the required attributes 'fbc:id' and 'fbc:type' and may have the optional attribute 'fbc:name'. No other attributes from the SBML Level 3 Flux Balance Constraints namespace are permitted on an <objective> object (Reference: L3V1 Fbc V1, Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20504), "The attribute 'fbc:name' on an <objective> must be of the data type 'string' (Reference: L3V1 Fbc V1, Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20505), "The attribute 'fbc:type' on an <objective> must be of the data type FbcType and thus its value must be one of 'minimize' or 'maximize (Reference: L3V1 Fbc V1, Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20506), "An <objective> object must have one and only one instance of the <listOfFluxObjectives> object (Reference: L3V1 Fbc V1, Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20507), "The <listOfFluxObjectives> subobject within an <objective> object must not be empty (Reference: L3V1 Fbc V1, Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20508), "Apart from the general notes and annotation subobjects permitted on all SBML objects, a <listOfFluxObjectives> container object may only contain <fluxObjective> objects (Reference: L3V1 Fbc V1, Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20509), "A <listOfFluxObjectives> object may have the optional 'metaid' and 'sboTerm' defined by SBML Level 3 Core. No other attributes from the SBML Level 3 Core namespace or the Flux Balance Constraints namespace are permitted on a <listOfFluxObjectives> object).Reference: L3V1 Fbc V1, Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20601), "A <fluxObjective> object may have the optional SBML Level 3 Core attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a <fluxObjective> (Reference: L3V1 Core, Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20602), "A <fluxObjective> object may have the optional SBML Level 3 Core subobjects for notes and annotations. No other elements from the SBML Level 3 Core namespace are permitted on a <fluxObjective> (Reference: L3V1 Core, Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20603), "A <fluxObjective> object must have the required attributes 'fbc:reaction' and 'fbc:coefficient', and may have the optional attributes 'fbc:id' and 'fbc:name'. No other attributes from the SBML Level 3 Flux Balance Constraints namespace are permitted on a <fluxObjective> object (Reference: L3V1 Fbc V1, Section 3.7).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20604), "The attribute 'fbc:name' on a <fluxObjective> must be of the data type 'string' (Reference: L3V1 Fbc V1, Section 3.7).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20605), "The value of the attribute 'fbc:reaction' of a <fluxObjective> object must conform to the syntax of the SBML data type 'SIdRef' (Reference: L3V1 Fbc V1, Section 3.7).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20606), "The value of the attribute 'fbc:reaction' of a <fluxObjective> object must be the identifier of an existing <reaction> object defined in the enclosing <model> object (Reference: L3V1 Fbc V1, Section 3.7).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20607), "The value of the attribute 'fbc:coefficient' of a <fluxObjective> object must conform to the syntax of the SBML data type 'double' (Reference: L3V1 Fbc V1, Section 3.7).");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20608), "When the value of the <model> 'fbc:strict' attribute is true, the value of the attribute 'fbc:coefficient' of a <fluxObjective> object must not be set to 'NaN', 'INF' or '-INF'.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20701), "There may be at most one instance of a <GeneProductAssociation> within a <Reaction> object using Flux BalanceConstraints.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20702), "An SBML <Reaction> object may have the optional attributes 'fbc:lowerFluxBound' and 'fbc:upperFluxBound'. No other attributes from the Flux Balance Constraints namespaces are permitted on a <Reaction>.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20703), "The attribute 'fbc:lowerFluxBound' of a <Reaction> must be of the data type 'SIdRef'.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20704), "The attribute 'fbc:upperFluxBound' of a <Reaction> must be of the data type 'SIdRef'.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20705), "The attribute 'fbc:lowerFluxBound' of a <Reaction> must point to an existing <Parameter> in the model.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20706), "The attribute 'fbc:upperFluxBound' of a <Reaction> must point to an existing <Parameter> in the model.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20707), "When the value of the <Model>s 'fbc:strict' attribute is 'true', a <Reaction> must define the attributes 'fbc:lowerFluxBound' and 'fbc:upperFluxBound'.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20708), "When the value of the <Model>s 'fbc:strict' attribute is 'true', the <Parameter> objects referred to by the attributes 'fbc:lowerFluxBound' and 'fbc:upperFluxBound' must have their 'constant' attribute set to 'true'.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20709), "When the value of the <Model>s 'fbc:strict' attribute is 'true', the <Parameter> objects referred to by the attributes 'fbc:lowerFluxBound' and 'fbc:upperFluxBound' must have a defined value for their 'value' attribute, which may not be 'NaN'.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20710), "When the value of the <Model>s 'fbc:strict' attribute is 'true', the <Parameter> objects referred to by the attributes 'fbc:lowerFluxBound' and 'fbc:upperFluxBound' may not be targeted by an <InitialAssignment>.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20711), "When the value of the <Model>s 'fbc:strict' attribute is 'true', the <Parameter> objects referred to by the attribute 'fbc:lowerFluxBound' may not have the value 'INF'.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20712), "When the value of the <Model>s 'fbc:strict' attribute is 'true', the <Parameter> object referred to by the attribute 'fbc:upperFluxBound' may not have the value '-INF'.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20713), "When the value of the <Model>s 'fbc:strict' attribute is 'true', the <Parameter> object referred to by the attribute 'fbc:lowerFluxBound' must be less than or equal to the value of the <Parameter> object referred to by the attribute 'fbc:upperFluxBound' .");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20714), "When the value of the <Model>s 'fbc:strict' attribute is 'true', the 'constant' attribute of <SpeciesReference> elements of a <Reaction> must be set to 'true'.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20715), "When the value of the <Model>s 'fbc:strict' attribute is 'true', the value of a <SpeciesReference>'s 'stoichiometry> attribute must not be set to 'NaN',  '-INF' or  'INF'.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20716), "When the value of the <Model>s 'fbc:strict' attribute is 'true', the <SpeciesReference> elements of a <Reaction> may not be targeted by an <InitialAssignment>.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20801), "A <GeneProductAssociation> object may have the optional SBML Level 3 Core attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a <GeneProductAssociation.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20802), "A <GeneProductAssociation> object may have the optional SBML Level 3 Core subobjects for notes and annotations. No other elements from the SBML Level 3 Core namespace are permitted on a <GeneProductAssociation.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20803), "A <GeneProductAssociation> object may have the optional attributes 'fbc:id' and 'fbc:name'. No other attributes from the SBML Level 3 Flux Balance Constraints namespace are permitted on a <GeneProductAssociation> object.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20804), "The attribute 'fbc:id' on a <GeneProductAssociation> must be of the data type 'SId'.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20805), "A <GeneProductAssociation> object must have one and only one of the concrete <Association> objects:  <GeneProductRef>, <And> or <Or>.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20806), "The attribute 'fbc:name' on a <GeneProductAssociation> must be of the data type 'string'.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20901), "A <GeneProductRef> object may have the optional SBML Level 3 Core attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a <GeneProductRef>.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20902), "A <GeneProductRef> object may have the optional SBML Level 3 Core subobjects for notes and annotations. No other elements from the SBML Level 3 Core namespace are permitted on a <GeneProductRef>.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20903), "A <GeneProductRef> object must have the required attribute 'fbc:geneProduct' and may have the optional attribute 'fbc:id'. No other attributes from the SBML Level 3 Flux Balance Constraints namespace are permitted on a <> object.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20904), "The attribute 'fbc:geneProduct' on a <GeneProductRef> must be of the data type 'SIdRef'.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_20908), "The attribute 'fbc:geneProduct' on a <GeneProductRef>  if set, must refer to 'id' of a <GeneProduct> in the <Model>.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_21001), "An <And> object may have the optional SBML Level 3 Core attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on an <GeneAnd.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_21002), "An <And> object may have the optional SBML Level 3 Core subobjects for notes and annotations. No other elements from the SBML Level 3 Core namespace are permitted on an <And>.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_21003), "An <And> object must have two or more concrete <Association> objects: <GeneProductRef>, <And>, or <Or>. No other elements from the SBML Level 3 Flux Balance Constraints namespace are permitted on an <And> object.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_21101), "An <Or> object may have the optional SBML Level 3 Core attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on an <Or>.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_21102), "An <Or> object may have the optional SBML Level 3 Core subobjects for notes and annotations. No other elements from the SBML Level 3 Core namespace are permitted on an <Or>.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_21103), "An <Or> object must have two or more concrete <Association> objects: <GeneProductRef>, <And>, or <Or>. No other elements from the SBML Level 3 Flux Balance Constraints namespace are permitted on an <Or> object.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_21201), "A <GeneProduct> object may have the optional SBML Level 3 Core attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a <GeneProduct>.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_21202), "A <GeneProduct> object may have the optional SBML Level 3 Core subobjects for notes and annotations. No other elements from the SBML Level 3 Core namespace are permitted on a <GeneProduct>.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_21203), "A <GeneProduct> object must have the required attributes 'fbc:id' and 'fbc:label' may have the optional attributes 'fbc:name' and 'fbc:associatedSpecies'. No other attributes from the SBML Level 3 Flux Balance Constraints namespace are permitted on a <GeneProduct> object.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_21204), "The attribute 'fbc:label' on a <GeneProduct> must be of the data type 'string'.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_21205), "The attribute 'fbc:label' on a <GeneProduct> must be unique among the set of all <GeneProduct> elements defined in the <Model>.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_21206), "The attribute 'fbc:name' on a <GeneProduct> must be of the data type 'string'.");  

        contents.put(Integer.toString(SBMLErrorCodes.FBC_21207), "The attribute 'fbc:associatedSpecies' on a <GeneProduct> must be the identifier of an existing <Species> defined in the enclosing <Model>.  ).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_10100), "");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_10101), "To conform to Version 1 of the Qualitative Models package specification for SBML Level 3, an SBML document must declare the use of the following XML Namespace: 'http://www.sbml.org/sbml/level3/version1/qual/version1').Reference: L3V1 Qual V1 Section 3.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_10102), "Wherever they appear in an SBML document, elements and attributes from the Qualitative Models package must be declared either implicitly or explicitly to be in the XML namespace 'http://www.sbml.org/sbml/level3/version1/qual/version1').Reference: L3V1 Qual V1 Section 3.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_10201), "The MathML <math> element in a <functionTerm> object should evaluate to a value of type boolean (Reference: L3V1 Qual V1 Section 3.6.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_10202), "The MathML <math> element in a <functionTerm> object should not use the <csymbol> elements 'time' and 'delay' as these explicitly introduce time into the model. As yet time is not considered within the Qualitative Models package specification (Reference: L3V1 Qual V1 Section 3.6.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_10301), "(Extends validation rule #10301 in the SBML Level 3 Version 1 Core specification.) Within a <model> the values of the attributes 'id' and 'qual:id' on every instance of the following classes of objects must be unique across the set of all 'id' and 'qual:id' attribute values of all such objects in a model: the <model> itself, plus all contained <functionDefinition>, <compartment>, <species>, <reaction>, <speciesReference>, <modifierSpeciesReference>, <event>, and <parameter> objects, plus the <qualitativeSpecies>, <transition>, <input> and <output> objects defined by the Qualitative Models package (Reference: L3V1 Qual V1 Section 3.7).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20101), "In all SBML documents using the Qualitative Models package, the SBML object must include a value for the attribute 'qual:required' attribute (Reference: L3V1 Core Section 4.1.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20102), "The value of attribute 'qual:required' on the SBML object must be of the data type Boolean (Reference: L3V1 Core Section 4.1.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20103), "The value of attribute 'qual:required' on the SBML object must be set to 'true' if if the model contains any <transition> objects (Reference: L3V1 Qual V1 Section 3.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20201), "There may be at most one instance of each of the following kinds of objects within a <model> object using Qualitative Models: <listOfTransitions> and <listOfQualitativeSpecies> (Reference: L3V1 Qual V1 Section 3.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20202), "The various ListOf subobjects with a <model> object are optional, but if present, these container object must not be empty.  Specifically, if any of the following classes of objects are present on the <model>, it must not be empty: <listOfQualitativeSpecies> and <listOfTransitions> (Reference: L3V1 Qual V1 Section 3.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20203), "Apart from the general notes and annotation subobjects permitted on all SBML objects, a <listOfTransitions> container object may only contain <transition> objects (Reference: L3V1 Qual V1 Section 3.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20204), "Apart from the general notes and annotation subobjects permitted on all SBML objects, a <listOfQualitativeSpecies> container object may only contain <qualitativeSpecies> objects (Reference: L3V1 Qual V1 Section 3.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20205), "A <listOfQualitativeSpecies> object may have the optional 'metaid' and 'sboTerm' defined by SBML Level 3 Core.  No other attributes from the SBML Level 3 Core namespace or the Qualitative Models namespace are permitted on a <listOfQualitativeSpecies> object (Reference: L3V1 Qual V1 Section 3.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20206), "A <listOfTransitions> object may have the optional 'metaid' and 'sboTerm' defined by SBML Level 3 Core.  No other attributes from the SBML Level 3 Core namespace or the Qualitative Models namespace are permitted on a <listOfTransitions> object (Reference: L3V1 Qual V1 Section 3.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20301), "A <qualitativeSpecies> object may have the optional 'metaid' and 'sboTerm' defined by SBML Level 3 Core.  No other attributes from the SBML Level 3 Core namespace or the Qualitative Models namespace are permitted on a <qualitativeSpecies> object (Reference: L3V1 Qual V1 Section 3.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20302), "A <qualitativeSpecies> object may have the optional SBML Level 3 Core subobjects for notes and annotations. No other elements from the SBML Level 3 Core namespaces are permitted on a <qualitativeSpecies> (Reference: L3V1 Qual V1 Section 3.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20303), "A <qualitativeSpecies> object must have the required attributes 'qual:id', 'qual:compartment' and 'qual:constant', and may have the optional attributes 'qual:name', 'qual:initialLevel' and 'qual:maxLevel'.  No other attributes from the SBML Level 3 Qualitative Models namespace are permitted on a <qualitativeSpecies> object (Reference: L3V1 Qual V1 Section 3.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20304), "The attribute 'qual:constant' in <qualitativeSpecies> must be of the data type boolean (Reference: L3V1 Qual V1 Section 3.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20305), "The attribute 'qual:name' in <qualitativeSpecies> must be of the data type string (Reference: L3V1 Qual V1 Section 3.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20306), "The attribute 'qual:initialLevel' in <qualitativeSpecies> must be of the data type integer (Reference: L3V1 Qual V1 Section 3.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20307), "The attribute 'qual:maxLevel' in <qualitativeSpecies> must be of the data type integer (Reference: L3V1 Qual V1 Section 3.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20308), "The value of the attribute 'qual:compartment' in a <qualitativeSpecies> object must be the identifier of an existing <compartment> object defined in the enclosing <model> object (Reference: L3V1 Qual V1 Section 3.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20309), "The value of the attribute 'qual:initialLevel' in a <qualitativeSpecies> object cannot be greater than the value of the 'qual:maxLevel' attribute for the given <qualitativeSpecies> object (Reference: L3V1 Qual V1 Section 3.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20310), "A <qualitativeSpecies> with attribute 'qual:constant' set to 'true' can only be referred to by an <input>. It cannot be the subject of an <output> in a <transition> (Reference: L3V1 Qual V1 Section 3.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20311), "A <qualitativeSpecies> that is referenced by an <output> with the 'qual:transitionEffect' attribute set to 'assignmentLevel' should not be referenced by any other <output> with the same 'transitionEffect' throughout the set of transitions for the containing model (Reference: L3V1 Qual V1 Section 3.6.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20312), "The attribute 'qual:initialLevel' in <qualitativeSpecies> must not be negative (Reference: L3V1 Qual V1 Section 3.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20313), "The attribute 'qual:maxLevel' in <qualitativeSpecies> must not be negative (Reference: L3V1 Qual V1 Section 3.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20401), "A <transition> object may have the optional 'metaid' and 'sboTerm' defined by SBML Level 3 Core.  No other attributes from the SBML Level 3 Core namespace or the Qualitative Models namespace are permitted on a <transition> object (Reference: L3V1 Qual V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20402), "A <transition> object may have the optional SBML Level 3 Core subobjects for notes and annotations. No other elements from the SBML Level 3 Core namespaces are permitted on a <transition> (Reference: L3V1 Qual V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20403), "A <transition> object may have the optional attributes 'qual:name' and 'qual:id'.  No other attributes from the SBML Level 3 Qualitative Models namespace are permitted on a <transition> object (Reference: L3V1 Qual V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20404), "The attribute 'qual:name' in <transition> must be of the data type string (Reference: L3V1 Qual V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20405), "A <transition> must have one and only one instance of the <listOfFunctionTerms> objects and may have at most one instance of the <listOfInputs> and <listOfOutputs> objects from the Qualitative Models namespace (Reference: L3V1 Qual V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20406), "The <listOfInputs> and <listOfOutputs> subobjects on a <transition> object are optional, but if present, these container object must not be empty (Reference: L3V1 Qual V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20407), "Apart from the general notes and annotation subobjects permitted on all SBML objects, a <listOfInputs> container object may only contain <input> objects (Reference: L3V1 Qual V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20408), "Apart from the general notes and annotation subobjects permitted on all SBML objects, a <listOfOutputs> container object may only contain <output> objects (Reference: L3V1 Qual V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20409), "Apart from the general notes and annotation subobjects permitted on all SBML objects, a <listOfFunctionTerms> container object must contain one and only one <defaultTerm> object and then may only contain <functionTerm> objects (Reference: L3V1 Qual V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20410), "A <listOfInputs> object may have the optional 'metaid' and 'sboTerm' defined by SBML Level 3 Core.  No other attributes from the SBML Level 3 Core namespace or the Qualitative Models namespace are permitted on a <listOfInputs> object (Reference: L3V1 Qual V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20411), "A <listOfOutputs> object may have the optional 'metaid' and 'sboTerm' defined by SBML Level 3 Core.  No other attributes from the SBML Level 3 Core namespace or the Qualitative Models namespace are permitted on a <listOfOutputs> object (Reference: L3V1 Qual V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20412), "A <listOfFunctionTerms> object may have the optional 'metaid' and 'sboTerm' defined by SBML Level 3 Core.  No other attributes from the SBML Level 3 Core namespace or the Qualitative Models namespace are permitted on a <listOfFunctionTerms> object (Reference: L3V1 Qual V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20413), "No element of the <listOfFunctionTerms> object may cause the level of a <qualitativeSpecies> to exceed the value 'qual:maxLevel' attribute (Reference: L3V1 Qual V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20414), "No element of the <listOfFunctionTerms> object may cause the level of a <qualitativeSpecies> to become negative (Reference: L3V1 Qual V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20501), "An <input> object may have the optional 'metaid' and 'sboTerm' defined by SBML Level 3 Core.  No other attributes from the SBML Level 3 Core namespace or the Qualitative Models namespace are permitted on an <input> object (Reference: L3V1 Qual V1 Section 3.6.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20502), "An <input> object may have the optional SBML Level 3 Core subobjects for notes and annotations. No other elements from the SBML Level 3 Core namespaces are permitted on an <input> (Reference: L3V1 Qual V1 Section 3.6.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20503), "An <input> object must have the required attributes 'qual:qualitativeSpecies' as well as 'qual:transitionEffect' and may have the optional attributes 'qual:id', 'qual:name', 'qual:sign' and 'qual:thresholdLevel'.  No other attributes from the SBML Level 3 Qualitative Models namespace are permitted on an <input> object (Reference: L3V1 Qual V1 Section 3.6.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20504), "The attribute 'qual:name' in <input> must be of the data type string (Reference: L3V1 Qual V1 Section 3.6.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20505), "The value of the attribute 'qual:sign' of an <input> object must conform to the syntax of the SBML data type 'sign' and may only take on the allowed values of 'sign' defined in SBML; that is, the value must be one of the following: 'positive', 'negative', 'dual' or 'unknown' (Reference: L3V1 Qual V1 Section 3.6.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20506), "The value of the attribute 'qual:transitionEffect' of an <input> object must conform to the syntax of the SBML data type 'transitionInputEffect' and may only take on the allowed values of 'transitionInputEffect' defined in SBML; that is, the value must be one of the following: 'none' or 'consumption' (Reference: L3V1 Qual V1 Section 3.6.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20507), "The attribute 'qual:thresholdLevel' in <input> must be of the data type 'integer' (Reference: L3V1 Qual V1 Section 3.6.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20508), "The value of the attribute 'qual:qualitativeSpecies' in an <input> object must be the identifier of an existing <qualitativeSpecies> object defined in the enclosing <model> object (Reference: L3V1 Qual V1 Section 3.6.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20509), "An <input> that refers to a <qualitativeSpecies> that has a 'qual:constant' attribute set to 'true' cannot have the attribute 'qual:transitionEffect' set to 'consumption' (Reference: L3V1 Qual V1 Section 3.6.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20510), "The attribute 'qual:thresholdLevel' in <input> must not be negative (Reference: L3V1 Qual V1 Section 3.6.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20601), "An <output> object may have the optional 'metaid' and 'sboTerm' defined by SBML Level 3 Core.  No other attributes from the SBML Level 3 Core namespace or the Qualitative Models namespace are permitted on an <output> object (Reference: L3V1 Qual V1 Section 3.6.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20602), "An <output> object may have the optional SBML Level 3 Core subobjects for notes and annotations. No other elements from the SBML Level 3 Core namespaces are permitted on an <output> (Reference: L3V1 Qual V1 Section 3.6.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20603), "An <output> object must have the required attributes 'qual:qualitativeSpecies' as well as 'qual:transitionEffect' and may have the optional attributes 'qual:id', 'qual:name' and 'qual:outputLevel'.  No other attributes from the SBML Level 3 Qualitative Models namespace are permitted on an <output> object (Reference: L3V1 Qual V1 Section 3.6.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20604), "The attribute 'qual:name' in <output> must be of the data type string (Reference: L3V1 Qual V1 Section 3.6.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20605), "The value of the attribute 'qual:transitionEffect' of an <output> object must conform to the syntax of the SBML data type 'transitionOutputEffect' and may only take on the allowed values of 'transitionOutputEffect' defined in SBML; that is, the value must be one of the following: 'production' or 'assignmentLevel' (Reference: L3V1 Qual V1 Section 3.6.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20606), "The attribute 'qual:outputLevel' in <output> must be of the data type 'integer' (Reference: L3V1 Qual V1 Section 3.6.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20607), "The value of the attribute 'qual:qualitativeSpecies' in an <output> object must be the identifier of an existing <qualitativeSpecies> object defined in the enclosing <model> object (Reference: L3V1 Qual V1 Section 3.6.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20608), "The <qualitativeSpecies> referred to by the attribute 'qual:qualitativeSpecies' in an <output> object must have the value of its 'qual:constant' attribute set to 'false' (Reference: L3V1 Qual V1 Section 3.6.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20609), "When the value of the attribute 'qual:transitionEffect' of an <output> object is set to the value 'production' the attribute 'qual:outputLevel' for that particular <output> object must have a value set (Reference: L3V1 Qual V1 Section 3.6.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20610), "The attribute 'qual:outputLevel' in <output> must not be negative (Reference: L3V1 Qual V1 Section 3.6.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20701), "A <defaultTerm> object may have the optional 'metaid' and 'sboTerm' defined by SBML Level 3 Core.  No other attributes from the SBML Level 3 Core namespace or the Qualitative Models namespace are permitted on a <defaultTerm> object (Reference: L3V1 Qual V1 Section 3.6.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20702), "A <defaultTerm> object may have the optional SBML Level 3 Core subobjects for notes and annotations. No other elements from the SBML Level 3 Core namespaces are permitted on a <defaultTerm> (Reference: L3V1 Qual V1 Section 3.6.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20703), "A <defaultTerm> object must have the required attributes 'qual:resultLevel'.  No other attributes from the SBML Level 3 Qualitative Models namespace are permitted on a <defaultTerm> object (Reference: L3V1 Qual V1 Section 3.6.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20704), "The attribute 'qual:resultLevel' in <defaultTerm> must be of the data type 'integer' (Reference: L3V1 Qual V1 Section 3.6.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20705), "The attribute 'qual:resultLevel' in <defaultTerm> must not be negative (Reference: L3V1 Qual V1 Section 3.6.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20801), "A <functionTerm> object may have the optional 'metaid' and 'sboTerm' defined by SBML Level 3 Core.  No other attributes from the SBML Level 3 Core namespace or the Qualitative Models namespace are permitted on a <functionTerm> object (Reference: L3V1 Qual V1 Section 3.6.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20802), "A <functionTerm> object may have the optional SBML Level 3 Core subobjects for notes and annotations. No other elements from the SBML Level 3 Core namespaces are permitted on a <functionTerm> (Reference: L3V1 Qual V1 Section 3.6.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20803), "A <functionTerm> object must have the required attributes 'qual:resultLevel'.  No other attributes from the SBML Level 3 Qualitative Models namespace are permitted on a <functionTerm> object (Reference: L3V1 Qual V1 Section 3.6.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20804), "A <functionTerm> object may contain exactly one MathML <math> element. No other elements from the SBML Level 3 Qualitative Models namespace are permitted on a <functionTerm> object (Reference: L3V1 Qual V1 Section 3.6.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20805), "The attribute 'qual:resultLevel' in <functionTerm> must be of the data type 'integer' (Reference: L3V1 Qual V1 Section 3.6.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.QUAL_20806), "The attribute 'qual:resultLevel' in <functionTerm> must not be negative (Reference: L3V1 Qual V1 Section 3.6.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_10100), "");  

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_10101), "To conform to the Groups Package specification for SBML Level 3 Version 1, an SBML document must declare 'http://www.sbml.org/sbml/level3/version1/groups/version1' as the XMLNamespaceto use for elements of this package (Reference: L3V1 Groups V1 Section 3.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_10102), "Wherever they appear in an SBML document, elements and attributes from the Groups Package must use the 'http://www.sbml.org/sbml/level3/version1/groups/version1' namespace, declaring so either explicitly or implicitly (Reference: L3V1 Groups V1 Section 3.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_10301), "(Extends validation rule #10301 in the SBML Level 3 Core specification. TO DO list scope of ids)).Reference: L3V1 Groups V1 Section 3.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_10302), "The value of a 'groups:id' must conform to the syntax of the <sbml> data type 'SId').Reference: L3V1 Groups V1 Section 3.3.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20101), "In all SBML documents using the Groups Package, the <sbml> object must have the 'groups:required' attribute (Reference: L3V1 Core Section 4.1.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20102), "The value of attribute 'groups:required' on the <sbml> object must be of data type 'boolean' (Reference: L3V1 Core Section 4.1.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20103), "The value of attribute 'groups:required' on the <sbml> object must be set to 'false' (Reference: L3V1 Groups V1 Section 3.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20201), "A <model> object may contain one and only one instance of the <listOfGroups> element. No other elements from the SBML Level 3 Groups namespaces are permitted on a <model> object (Reference: L3V1 Groups V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20202), "The <listOfGroups> subobject on a <model> object is optional, but if present, this container object must not be empty (Reference: L3V1 Groups V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20203), "Apart from the general notes and annotations subobjects permitted on all SBML objects, a <listOfGroups> container object may only contain <group> objects (Reference: L3V1 Groups V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20204), "A <listOfGroups> object may have the optional SBML Level 3 Core attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespaces are permitted on a <listOfGroups> object (Reference: L3V1 Groups V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20301), "A <group> object may have the optional SBML Level 3 Core attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespaces are permitted on a <group> (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20302), "A <group> object may have the optional SBML Level 3 Core subobjects for notes and annotations. No other elements from the SBML Level 3 Core namespaces are permitted on a <group> (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20303), "A <group> object must have the required attribute 'groups:kind', and may have the optional attributes 'groups:id' and 'groups:name'. No other attributes from the SBML Level 3 Groups namespaces are permitted on a <group> object (Reference: L3V1 Groups V1 Section 3.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20304), "A <group> object may contain one and only one instance of the <listOfMembers> element. No other elements from the SBML Level 3 Groups namespaces are permitted on a <group> object (Reference: L3V1 Groups V1 Section 3.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20305), "The value of the attribute 'groups:kind' of a <group> object must conform to the syntax of SBML data type 'groupKind' and may only take on the allowed values of 'groupKind' defined in SBML; that is, the value must be one of the following: 'classification', 'partonomy' or 'collection' (Reference: L3V1 Groups V1 Section 3.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20306), "The attribute 'groups:name' on a <group> must have a value of data type 'string' (Reference: L3V1 Groups V1 Section 3.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20307), "The <listOfMembers> subobject on a <group> object is optional, but if present, this container object must not be empty (Reference: L3V1 Groups V1 Section 3.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20308), "Apart from the general notes and annotations subobjects permitted on all SBML objects, a <listOfMembers> container object may only contain <member> objects (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20309), "A <listOfMembers> object may have the optional SBML Level 3 Core attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespaces are permitted on a <listOfMembers> object (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20310), "A <listOfMembers> object may have the optional attributes 'groups:id' and 'groups:name'. No other attributes from the SBML Level 3 Groups namespaces are permitted on a <listOfMembers> object (Reference: L3V1 Groups V1 Section 3.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20311), "The attribute 'groups:name' on a <listOfMembers> must have a value of data type 'string' (Reference: L3V1 Groups V1 Section 3.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20312), "If <listOfMembers> objects from different <group> objects contain <member> elements that reference the same SBase object, the 'sboTerm' attribute and any child <notes> or <annotation> elements set for those <listOfMembers> should be consistent, as they should all apply to the same referenced object (Reference: L3V1 Groups V1 Section 3.5.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20313), "Member references may not be circular: no <member>'s 'idRef' or 'metaIdRef' may reference itself, its parent <listOfMembers>, nor its parent <group>. If a <member> references a <group> or a <listOfMembers>, the same restrictions apply to that subgroup's children: they may not reference the <member>, its parent <listOfMembers>, nor its parent <group>, and if any of those children reference a <group>, the same restrictions apply to them, etc (Reference: L3V1 Groups V1 Section 3.5.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20401), "A <member> object may have the optional SBML Level 3 Core attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespaces are permitted on a <member> (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20402), "A <member> object may have the optional SBML Level 3 Core subobjects for notes and annotations. No other elements from the SBML Level 3 Core namespaces are permitted on a <member> (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20403), "A <member> object may have the optional attributes 'groups:id' and 'groups:name' and must have a value for one (and exactly one) of the attributes 'groups:idRef' and 'groups:metaIdRef'. No other attributes from the SBML Level 3 Groups namespaces are permitted on a <member> object (Reference: L3V1 Groups V1 Section 3.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20404), "The attribute 'groups:name' on a <member> must have a value of data type 'string' (Reference: L3V1 Groups V1 Section 3.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20405), "The value of the attribute 'groups:idRef' of a <member> object must be the identifier of an existing <SBase> object defined in the enclosing <model> object (Reference: L3V1 Groups V1 Section 3.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20406), "The value of the attribute 'groups:metaIdRef' of a <member> object must be the 'metaid' of an existing <SBase> object defined in the enclosing <model> object (Reference: L3V1 Groups V1 Section 3.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20407), "The value of the attribute 'groups:idRef' of a <member> object must conform to the syntax of the SBML data type 'SId' (Reference: L3V1 Groups V1 Section 3.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.GROUPS_20408), "The value of the attribute 'groups:metaIdRef' of a <member> object must conform to the syntax of the SBML data type 'ID' (Reference: L3V1 Groups V1 Section 3.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_10100), "");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_10101), "To conform to Version 1 of the Layout package specification for SBML Level 3, an SBML document must declare the use of the following XML Namespace: 'http://www.sbml.org/sbml/level3/version1/layout/version1').Reference: L3V1 Layout V1 Section 3.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_10102), "Wherever they appear in an SBML document, elements and attributes from the Layout package must be declared either implicitly or explicitly to be in the XML namespace 'http://www.sbml.org/sbml/level3/version1/layout/version1').Reference: L3V1 Layout V1 Section 3.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_10301), "(Extends validation rule #10301 in the SBML Level 3 Version 1 Core specification.) Within a <model> object the values of the attributes id and layout:id on every instance of the following classes of objects must be unique across the set of all id and layout:id attribute values of all such objects in a model: the model itself, plus all contained <functionDefinition>, <compartment>, <species>, <reaction>, <speciesReference>, <modifierSpeciesReference>, <event>, and <parameter> objects, plus the <boundingBox>, <compartmentGlyph>, <generalGlyph>, <graphicalObject>, <layout>, <speciesGlyph>, <speciesReferenceGlyph>, <reactionGlyph>, <referenceGlyph> and <textGlyph> objects defined by the Layout package (Reference: L3V1 Layout V1 Section 3.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_10302), "The value of the 'layout:id' attribute must conform to the SBML data type SId).Reference: L3V1 Layout V1 Section 3.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_10401), "The attribute 'xsi:type' must be present on all <lineSegment> and <cubicBezier> objects. It is not permitted on any other elements from the SBML Level 3 Layout namespace (Reference: L3V1 Layout V1 Section 3.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_10402), "The value of the 'xsi:type' attribute must be either 'LineSegment' or 'CubicBezier' appropriate to the object where it is located. No other values are permitted (Reference: L3V1 Layout V1 Section 3.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20101), "In all SBML documents using the Layout package, the SBML object must include a value for the attribute 'layout:required' (Reference: L3V1 Core Section 4.1.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20102), "The value of attribute 'layout:required' on the SBML object must be of the data type Boolean (Reference: L3V1 Core Section 4.1.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20103), "The value of attribute 'layout:required' on the SBML object must be set to 'false' (Reference: L3V1 Layout V1 Section 3.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20201), "There may be at most one instance of <listOfLayouts> element within a <model> object using Layout. No other elements from the Layout package are allowed (Reference: L3V1 Layout V1 Section 3.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20202), "The <listOfLayouts> within a <model> object is optional, but if present, this object must not be empty (Reference: L3V1 Layout V1 Section 3.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20203), "Apart from the general notes and annotation subobjects permitted on all SBML objects, a <listOfLayouts> container object may only contain <layout> objects (Reference: L3V1 Layout V1 Section 3.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20204), "A <listOfLayouts> object may have the optional attributes 'metaid' and 'sboTerm' defined by SBML Level 3 Core. No other attributes from the SBML Level 3 Core namespace or the Layout namespace are permitted on a <listOfLayouts> object (Reference: L3V1 Layout V1 Section 3.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20301), "A <layout> object may have the optional SBML Level 3 Core subobjects for notes and annotations. No other elements from the SBML Level 3 Core namespace are permitted on a <layout> (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20302), "A <layout> object may have the optional SBML Level 3 Core attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a <layout> object (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20303), "There may be at most one instance of each of the following kinds of objects within a <layout> object: <listOfCompartmentGlyphs>, <listOfSpeciesGlyphs>, <listOfReactionGlyphs>, <listOfTextGlyphs>, <listOfAdditionalGraphicalObjects> (Reference: L3V1 Layout V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20304), "The various ListOf subobjects within a <layout> object are optional, but if present, these container object must not be empty.  Specifically, if any of the following classes of objects are present on the <layout>, it must not be empty: <listOfCompartmentGlyphs>, <listOfSpeciesGlyphs>, <listOfReactionGlyphs>, <listOfTextGlyphs>, <listOfAdditionalGraphicalObjects> (Reference: L3V1 Layout V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20305), "A <layout> object must have the required attribute 'layout:id' and may have the optional attribute 'layout:name'. No other attributes from the SBML Level 3 Layout namespace are permitted on a <layout> object (Reference: L3V1 Layout V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20306), "The attribute 'layout:name' of a <layout> must be of the data type 'string' (Reference: L3V1 Layout V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20307), "A <listOfCompartmentGlyphs> object may have the optional attributes 'metaid' and 'sboTerm' defined by SBML Level 3 Core.  No other attributes from the SBML Level 3 Core namespace or the Layout namespace are permitted on a <listOfCompartmentGlyphs> object (Reference: L3V1 Layout V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20308), "Apart from the general notes and annotation subobjects permitted on all SBML objects, a <listOfCompartmentGlyphs> container object may only contain <compartmentGlyph> objects (Reference: L3V1 Layout V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20309), "A <listOfSpeciesGlyphs> object may have the optional attributes 'metaid' and 'sboTerm' defined by SBML Level 3 Core.  No other attributes from the SBML Level 3 Core namespace or the Layout namespace are permitted on a <listOfSpeciesGlyphs> object (Reference: L3V1 Layout V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20310), "Apart from the general notes and annotation subobjects permitted on all SBML objects, a <listOfSpeciesGlyphs> container object may only contain <compartmentGlyph> objects (Reference: L3V1 Layout V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20311), "A <listOfReactionGlyphs> object may have the optional attributes 'metaid' and 'sboTerm' defined by SBML Level 3 Core.  No other attributes from the SBML Level 3 Core namespace or the Layout namespace are permitted on a <listOfReactionGlyphs> object (Reference: L3V1 Layout V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20312), "Apart from the general notes and annotation subobjects permitted on all SBML objects, a <listOfReactionGlyphs> container object may only contain <compartmentGlyph> objects (Reference: L3V1 Layout V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20313), "A <listOfAdditionalGraphicalObjectGlyphs> object may have the optional attributes 'metaid' and 'sboTerm' defined by SBML Level 3 Core.  No other attributes from the SBML Level 3 Core namespace or the Layout namespace are permitted on a <listOfAdditionalGraphicalObjectGlyphs> object (Reference: L3V1 Layout V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20314), "Apart from the general notes and annotation subobjects permitted on all SBML objects, a <listOfAdditionalGraphicalObjectGlyphs> container object may only contain <compartmentGlyph> objects (Reference: L3V1 Layout V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20315), "A <layout> object must contain exactly one <dimensions> object (Reference: L3V1 Layout V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20316), "A <listOfTextGlyphs> object may have the optional attributes 'metaid' and 'sboTerm' defined by SBML Level 3 Core.  No other attributes from the SBML Level 3 Core namespace or the Layout namespace are permitted on a <listOfTextGlyphs> object (Reference: L3V1 Layout V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20317), "Apart from the general notes and annotation subobjects permitted on all SBML objects, a <listOfTextGlyphs> container object may only contain <compartmentGlyph> objects (Reference: L3V1 Layout V1 Section 3.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20401), "A <graphicalObject> object may have the optional SBML Level 3 Core subobjects for notes and annotations. No other elements from the SBML Level 3 Core namespace are permitted on a <graphicalObject> (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20402), "A <graphicalObject> object may have the optional SBML Level 3 Core attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a <graphicalObject> (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20403), "There may be at most one instance of a <boundingBox> object on a <graphicalObject>.  No other elements from the Layout namespace are permitted on a <graphicalObject> (Reference: L3V1 Layout V1 Section 3.7).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20404), "A <graphicalObject> object must have the required attribute 'layout:id' and may have the optional attribute 'layout:metaidRef'.  No other attributes from the Layout namespace are permitted on a <graphicalObject> (Reference: L3V1 Layout V1 Section 3.7).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20405), "The attribute 'layout:metaidRef' of a <graphicalObject> must be of the data type 'IDREF' (Reference: L3V1 Layout V1 Section 3.7).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20406), "The value of a 'layout:metaidRef' attribute of a <graphicalObject> must be of the 'metaid' of an existing element in the <model> (Reference: L3V1 Layout V1 Section 3.7).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20407), "A <graphicalObject> must contain exactly one <boundingBox> object (Reference: L3V1 Layout V1 Section 3.7).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20501), "A <compartmentGlyph> object may have the optional SBML Level 3 Core subobjects for notes and annotations. No other elements from the SBML Level 3 Core namespace are permitted on a <compartmentGlyph> (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20502), "A <compartmentGlyph> object may have the optional SBML Level 3 Core attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a <compartmentGlyph> (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20503), "There may be at most one instance of a <boundingBox> object on a <compartmentGlyph>.  No other elements from the Layout namespace are permitted on a <compartmentGlyph> (Reference: L3V1 Layout V1 Section 3.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20504), "A <compartmentGlyph> object must have the required attribute 'layout:id' and may have the optional attributes 'layout:metaidRef' or 'layout:compartment'.  No other attributes from the Layout namespace are permitted on a <compartmentGlyph> (Reference: L3V1 Layout V1 Section 3.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20505), "The attribute 'layout:metaidRef' of a <compartmentGlyph> must be of the data type 'IDREF' (Reference: L3V1 Layout V1 Section 3.7).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20506), "The value of a 'layout:metaidRef' attribute of a <compartmentGlyph> must be of the 'metaid' of an existing element in the <model> (Reference: L3V1 Layout V1 Section 3.7).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20507), "The attribute 'layout:compartment' of a <compartmentGlyph> must be the data type 'SIdRef' (Reference: L3V1 Layout V1 Section 3.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20508), "The value of the 'layout:compartment' attribute of a <compartmentGlyph> must be of the 'id' of an existing <compartment> in the <model> (Reference: L3V1 Layout V1 Section 3.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20509), "If both attributes 'layout:compartment' and 'layout:metaidRef' are specified on a <compartmentGlyph> they have to reference the same <compartment> in the <model> (Reference: L3V1 Layout V1 Section 3.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20510), "The attribute 'layout:order' of a <compartmentGlyph> must be the data type 'double' (Reference: L3V1 Layout V1 Section 3.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20601), "A <speciesGlyph> object may have the optional SBML Level 3 Core subobjects for notes and annotations. No other elements from the SBML Level 3 Core namespace are permitted on a <speciesGlyph> (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20602), "A <speciesGlyph> object may have the optional SBML Level 3 Core attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a <speciesGlyph> (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20603), "There may be at most one instance of a <boundingBox> object on a <speciesGlyph>.  No other elements from the Layout namespace are permitted on a <speciesGlyph> (Reference: L3V1 Layout V1 Section 3.9).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20604), "A <speciesGlyph> object must have the required attribute 'layout:id' and may have the optional attributes 'layout:metaidRef' or 'layout:species'.  No other attributes from the Layout namespace are permitted on a <speciesGlyph> (Reference: L3V1 Layout V1 Section 3.9).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20605), "The attribute 'layout:metaidRef' of a <speciesGlyph> must be of the data type 'IDREF' (Reference: L3V1 Layout V1 Section 3.7).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20606), "The value of a 'layout:metaidRef' attribute of a <speciesGlyph> must be of the 'metaid' of an existing element in the <model> (Reference: L3V1 Layout V1 Section 3.7).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20607), "The attribute 'layout:species' of a <speciesGlyph> must be the data type 'SIdRef' (Reference: L3V1 Layout V1 Section 3.9).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20608), "The value of the 'layout:species' attribute of a <speciesGlyph> must be of the 'id' of an existing <species> in the <model> (Reference: L3V1 Layout V1 Section 3.8).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20609), "If both attributes 'layout:species' and 'layout:metaidRef' are specified on a <speciesGlyph> they have to reference the same <species> in the <model> (Reference: L3V1 Layout V1 Section 3.9).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20701), "A <reactionGlyph> object may have the optional SBML Level 3 Core subobjects for notes and annotations. No other elements from the SBML Level 3 Core namespace are permitted on a <reactionGlyph> (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20702), "A <reactionGlyph> object may have the optional SBML Level 3 Core attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a <reactionGlyph> (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20703), "There may be at most one instance of each of the following kinds of objects within a <reactionGlyph> object: <boundingBox>, and <curve> and there must be one instance of the <listOfSpeciesReferenceGlyphs>. No other elements from the Layout namespace are permitted on a <reactionGlyph> (Reference: L3V1 Layout V1 Section 3.10).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20704), "A <reactionGlyph> object must have the required attribute 'layout:id' and may have the optional attributes 'layout:metaidRef' or 'layout:reaction'.  No other attributes from the Layout namespace are permitted on a <reactionGlyph> (Reference: L3V1 Layout V1 Section 3.10).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20705), "The attribute 'layout:metaidRef' of a <reactionGlyph> must be of the data type 'IDREF' (Reference: L3V1 Layout V1 Section 3.7).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20706), "The value of a 'layout:metaidRef' attribute of a <reactionGlyph> must be of the 'metaid' of an existing element in the <model> (Reference: L3V1 Layout V1 Section 3.7).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20707), "The attribute 'layout:reaction' of a <reactionGlyph> must be the data type 'SIdRef' (Reference: L3V1 Layout V1 Section 3.10).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20708), "The value of the 'layout:reaction' attribute of a <reactionGlyph> must be of the 'id' of an existing <reaction> in the <model> (Reference: L3V1 Layout V1 Section 3.10).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20709), "If both attributes 'layout:reaction' and 'layout:metaidRef' are specified on a <reactionGlyph> they have to reference the same <reaction> in the <model> (Reference: L3V1 Layout V1 Section 3.10).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20710), "Apart from the general notes and annotation subobjects permitted on all SBML objects, a <listOfSpeciesReferenceGlyphs> container object may only contain <speciesReferenceGlyph> objects (Reference: L3V1 Layout V1 Section 3.10).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20711), "A <listOfSpeciesReferenceGlyphs> object may have the optional attributes 'metaid' and 'sboTerm' defined by SBML Level 3 Core. No other attributes from the SBML Level 3 Core namespace or the Layout namespace are permitted on a <listOfSpeciesReferenceGlyphs> object (Reference: L3V1 Layout V1 Section 3.10).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20712), "A <listOfSpeciesReferenceGlyphs> container object must not be empty (Reference: L3V1 Layout V1 Section 3.10).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20801), "A <generalGlyph> object may have the optional SBML Level 3 Core subobjects for notes and annotations. No other elements from the SBML Level 3 Core namespace are permitted on a <generalGlyph> (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20802), "A <generalGlyph> object may have the optional SBML Level 3 Core attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a <generalGlyph> (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20803), "There may be at most one instance of each of the following kinds of objects within a <generalGlyph> object: <boundingBox>, <curve>, <listOfReferenceGlyphs> and <listOfSubGlyphs>. No other elements from the Layout namespace are permitted on a <generalGlyph> (Reference: L3V1 Layout V1 Section 3.11).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20804), "A <generalGlyph> object must have the required attribute 'layout:id' and may have the optional attributes 'layout:metaidRef' or 'layout:reference'.  No other attributes from the Layout namespace are permitted on a <generalGlyph> (Reference: L3V1 Layout V1 Section 3.11).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20805), "The attribute 'layout:metaidRef' of a <generalGlyph> must be of the data type 'IDREF' (Reference: L3V1 Layout V1 Section 3.7).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20806), "The value of a 'layout:metaidRef' attribute of a <generalGlyph> must be of the 'metaid' of an existing element in the <model> (Reference: L3V1 Layout V1 Section 3.7).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20807), "The attribute 'layout:reference' of a <generalGlyph> must be the data type 'SIdRef' (Reference: L3V1 Layout V1 Section 3.11).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20808), "The value of the 'layout:reference' attribute of a <generalGlyph> must be of the 'id' of an existing element in the <model> (Reference: L3V1 Layout V1 Section 3.11).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20809), "If both attributes 'layout:reference' and 'layout:metaidRef' are specified on a <generalGlyph> they have to reference the same element in the <model> (Reference: L3V1 Layout V1 Section 3.11).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20810), "Apart from the general notes and annotation subobjects permitted on all SBML objects, a <listOfReferenceGlyphs> container object may only contain <referenceGlyph> objects (Reference: L3V1 Layout V1 Section 3.11).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20811), "A <listOfReferenceGlyphs> object may have the optional attributes 'metaid' and 'sboTerm' defined by SBML Level 3 Core. No other attributes from the SBML Level 3 Core namespace or the Layout namespace are permitted on a <listOfReferenceGlyphs> object (Reference: L3V1 Layout V1 Section 3.11).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20812), "");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20813), "A <listOfSubGlyphs> object may have the optional attributes 'metaid' and 'sboTerm' defined by SBML Level 3 Core. No other attributes from the SBML Level 3 Core namespace or the Layout namespace are permitted on a <listOfSubGlyphs> object (Reference: L3V1 Layout V1 Section 3.11).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20901), "A <textGlyph> object may have the optional SBML Level 3 Core subobjects for notes and annotations. No other elements from the SBML Level 3 Core namespace are permitted on a <textGlyph> (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20902), "A <textGlyph> object may have the optional SBML Level 3 Core attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a <textGlyph> (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20903), "A <textGlyph> must contain exactly one <boundingBox> object. No other elements from the Layout namespace are permitted on a <textGlyph> (Reference: L3V1 Layout V1 Section 3.12).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20904), "A <textGlyph> object must have the required attribute 'layout:id' and may have the optional attributes 'layout:metaidRef', 'layout:graphicalObject', 'layout:text' and 'layout:originOfText'.  No other attributes from the Layout namespace are permitted on a <textGlyph> (Reference: L3V1 Layout V1 Section 3.12).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20905), "The attribute 'layout:metaidRef' of a <textGlyph> must be of the data type 'IDREF' (Reference: L3V1 Layout V1 Section 3.7).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20906), "The value of a 'layout:metaidRef' attribute of a <textGlyph> must be of the 'metaid' of an existing element in the <model> (Reference: L3V1 Layout V1 Section 3.7).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20907), "The attribute 'layout:originOfText' of a <textGlyph> must be the data type 'SIdRef' (Reference: L3V1 Layout V1 Section 3.12).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20908), "The value of the 'layout:originOfText' attribute of a <textGlyph> must be of the 'id' of an existing element in the <model> (Reference: L3V1 Layout V1 Section 3.12).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20909), "If both attributes 'layout:originOfText' and 'layout:metaidRef' are specified on a <textGlyph> they have to reference the same element in the <model> (Reference: L3V1 Layout V1 Section 3.12).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20910), "The attribute 'layout:graphicalObject' of a <textGlyph> must be the data type 'SIdRef' (Reference: L3V1 Layout V1 Section 3.12).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20911), "The value of the 'layout:graphicalObject' attribute of a <textGlyph> must be of the 'id' of an existing <graphicalObject> (or derived) element in the <layout> (Reference: L3V1 Layout V1 Section 3.12).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_20912), "The attribute 'layout:text' of a <textGlyph> must be the data type 'string' (Reference: L3V1 Layout V1 Section 3.12).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21001), "A <speciesReferenceGlyph> object may have the optional SBML Level 3 Core subobjects for notes and annotations. No other elements from the SBML Level 3 Core namespace are permitted on a <speciesReferenceGlyph> (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21002), "A <speciesReferenceGlyph> object may have the optional SBML Level 3 Core attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a <speciesReferenceGlyph> (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21003), "A <speciesReferenceGlyph> may have at most one instance of a <boundingBox> and <curve> object. No other elements from the Layout namespace are permitted on a <speciesReferenceGlyph> (Reference: L3V1 Layout V1 Section 3.10.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21004), "A <speciesReferenceGlyph> object must have the required attribute 'layout:id' and may have the optional attributes 'layout:metaidRef', 'layout:speciesReference' and 'layout:role'.  No other attributes from the Layout namespace are permitted on a <speciesReferenceGlyph> (Reference: L3V1 Layout V1 Section 3.10.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21005), "The attribute 'layout:metaidRef' of a <speciesReferenceGlyph> must be of the data type 'IDREF' (Reference: L3V1 Layout V1 Section 3.7).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21006), "The value of a 'layout:metaidRef' attribute of a <speciesReferenceGlyph> must be of the 'metaid' of an existing element in the <model> (Reference: L3V1 Layout V1 Section 3.7).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21007), "The attribute 'layout:speciesReference' of a <speciesReferenceGlyph> must be the data type 'SIdRef' (Reference: L3V1 Layout V1 Section 3.10.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21008), "The value of the 'layout:speciesReference' attribute of a <speciesReferenceGlyph> must be of the 'id' of an existing <speciesReference> in the <model> (Reference: L3V1 Layout V1 Section 3.10.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21009), "If both attributes 'layout:speciesReference' and 'layout:metaidRef' are specified on a <speciesReferenceGlyph> they have to reference the same <speciesReference> in the <model> (Reference: L3V1 Layout V1 Section 3.12).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21010), "The attribute 'layout:speciesGlyph' of a <speciesReferenceGlyph> must be the data type 'SIdRef' (Reference: L3V1 Layout V1 Section 3.10.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21011), "The value of the 'layout:speciesGlyph' attribute of a <speciesReferenceGlyph> must be of the 'id' of an existing <speciesGlyph> element in the <layout> (Reference: L3V1 Layout V1 Section 3.10.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21012), "The attribute 'layout:role' of a <speciesReferenceGlyph> must be of data type 'SpeciesReferenceRole', i.e. it must have one of the following values: substrate, product, sidesubstrate, sideproduct, modifier, activator, inhibitor or undefined (Reference: L3V1 Layout V1 Section 3.10.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21101), "A <referenceGlyph> object may have the optional SBML Level 3 Core subobjects for notes and annotations. No other elements from the SBML Level 3 Core namespace are permitted on a <referenceGlyph> (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21102), "A <referenceGlyph> object may have the optional SBML Level 3 Core attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a <referenceGlyph> (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21103), "There may be at most one instance of a <boundingBox> and <curve> object on a <referenceGlyph>. No other elements from the Layout namespace are permitted on a <referenceGlyph> (Reference: L3V1 Layout V1 Section 3.11.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21104), "A <referenceGlyph> object must have the required attributes 'layout:id' and 'layout:glyph' and may have the optional attributes 'layout:metaidRef', 'layout:reference' and 'layout:role'.  No other attributes from the Layout namespace are permitted on a <referenceGlyph> (Reference: L3V1 Layout V1 Section 3.11.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21105), "The attribute 'layout:metaidRef' of a <referenceGlyph> must be of the data type 'IDREF' (Reference: L3V1 Layout V1 Section 3.7).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21106), "The value of a 'layout:metaidRef' attribute of a <referenceGlyph> must be of the 'metaid' of an existing element in the <model> (Reference: L3V1 Layout V1 Section 3.7).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21107), "The attribute 'layout:reference' of a <referenceGlyph> must be the data type 'SIdRef' (Reference: L3V1 Layout V1 Section 3.11.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21108), "The value of the 'layout:reference' attribute of a <referenceGlyph> must be of the 'id' of an existing element in the <model> (Reference: L3V1 Layout V1 Section 3.11.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21109), "If both attributes 'layout:reference' and 'layout:metaidRef' are specified on a <referenceGlyph> they have to reference the same element in the <model> (Reference: L3V1 Layout V1 Section 3.12).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21110), "The attribute 'layout:glyph' of a <referenceGlyph> must be the data type 'SIdRef' (Reference: L3V1 Layout V1 Section 3.11.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21111), "The value of the 'layout:glyph' attribute of a <referenceGlyph> must be of the 'id' of an existing <graphicalObject> (or derived) element in the <layout> (Reference: L3V1 Layout V1 Section 3.11.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21112), "The attribute 'layout:role' of a <referenceGlyph> must be the data type 'string' (Reference: L3V1 Layout V1 Section 3.11.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21201), "A <point> object may have the optional SBML Level 3 Core subobjects for notes and annotations. No other elements from the SBML Level 3 Core namespace are permitted on a <point>. It should be noted that the 'point' object may occur as a <position>, <basePoint1>, <basePoint2>, <start> or <end> element (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21202), "A <point> object may have the optional SBML Level 3 Core attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a <point>.  It should be noted that the 'point' object may occur as a <position>, <basePoint1>, <basePoint2>, <start> or <end> element (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21203), "A <point> object must have the required attributes 'layout:x' and 'layout:y' and may have the optional attributes 'layout:id', and 'layout:z'.  No other attributes from the Layout namespace are permitted on a <point>.   It should be noted that the 'point' object may occur as a <position>, <basePoint1>, <basePoint2>, <start> or <end> element (Reference: L3V1 Layout V1 Section 3.4.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21204), "The attributes 'layout:x', 'layout:y' and 'layout:z' of a <point> element must be of the data type 'double'. It should be noted that the 'point' object may occur as a <position>, <basePoint1>, <basePoint2>, <start> or <end> element (Reference: L3V1 Layout V1 Section 3.4.1).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21301), "A <boundingBox> object may have the optional SBML Level 3 Core subobjects for notes and annotations. No other elements from the SBML Level 3 Core namespace are permitted on a <boundingBox> (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21302), "A <boundingBox> object may have the optional SBML Level 3 Core attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a <boundingBox> (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21303), "There must be exactly one instance of a <point> and a <dimensions> object on a <boundingBox>. No other elements from the Layout namespace are permitted on a <boundingBox>. It should be noted that the 'point' object will occur as a <position> element (Reference: L3V1 Layout V1 Section 3.4.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21304), "A <boundingBox> object may have the optional attributes 'layout:id'. No other attributes from the Layout namespace are permitted on a <boundingBox> (Reference: L3V1 Layout V1 Section 3.4.3).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21305), "If the 'layout:z' attribute on a <point> element of a <boundingBox> is not specified, the attribute 'layout:depth' must not be specified. It should be noted that the 'point' object will occur as a <position> element (Reference: L3V1 Layout V1 Section 3.7).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21401), "A <curve> object may have the optional SBML Level 3 Core subobjects for notes and annotations. No other elements from the SBML Level 3 Core namespace are permitted on a <curve> (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21402), "A <curve> object may have the optional SBML Level 3 Core attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a <curve> (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21403), "There must be exactly one instance of a <listOfCurveSegments> object on a <curve>. No other elements from the Layout namespace are permitted on a <curve> (Reference: L3V1 Layout V1 Section 3.4.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21404), "No attributes from the Layout namespace are permitted on a <curve> (Reference: L3V1 Layout V1 Section 3.4.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21405), "A <listOfCurveSegments> object may have the optional attributes 'metaid' and 'sboTerm' defined by SBML Level 3 Core. No other attributes from the SBML Level 3 Core namespace or the Layout namespace are permitted on a <listOfCurveSegments> object (Reference: L3V1 Layout V1 Section 3.4.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21406), "Apart from the general notes and annotation subobjects permitted on all SBML objects, a <listOfCurveSegments> container object may only contain <referenceGlyph> objects (Reference: L3V1 Layout V1 Section 3.4.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21407), "A <listOfCurveSegments> container object may not be empty (Reference: L3V1 Layout V1 Section 3.4.4).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21501), "A <lineSegment> object may have the optional SBML Level 3 Core subobjects for notes and annotations. No other elements from the SBML Level 3 Core namespace are permitted on a <lineSegment>.  It should be noted that a 'lineSegment' occurs as a <curveSegment> element with type 'LineSegment' or 'CubicBezier' (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21502), "A <lineSegment> object may have the optional SBML Level 3 Core attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a <lineSegment>. It should be noted that a 'lineSegment' occurs as a <curveSegment> element with type 'LineSegment' or 'CubicBezier' (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21503), "A <lineSegment> must specify two <point> elements 'start' and 'end'. No other elements from the Layout namespace are permitted on a <lineSegment>.  It should be noted that a 'lineSegment' occurs as a <curveSegment> element with type 'LineSegment' or 'CubicBezier' (Reference: L3V1 Layout V1 Section 3.4.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21504), "No attributes from the Layout namespace are permitted on a <lineSegment>.  It should be noted that a 'lineSegment' occurs as a <curveSegment> element with type 'LineSegment' or 'CubicBezier' (Reference: L3V1 Layout V1 Section 3.4.5).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21601), "A <cubicBezier> object may have the optional SBML Level 3 Core subobjects for notes and annotations. No other elements from the SBML Level 3 Core namespace are permitted on a <cubicBezier> (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21602), "A <cubicBezier> object may have the optional SBML Level 3 Core attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a <cubicBezier> (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21603), "A <cubicBezier> must specify four <point> elements 'start' 'basePoint1', 'basePoint2' and 'end'. No other elements from the Layout namespace are permitted on a <cubicBezier> (Reference: L3V1 Layout V1 Section 3.4.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21604), "No attributes from the Layout namespace are permitted on a <cubicBezier> (Reference: L3V1 Layout V1 Section 3.4.6).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21701), "A <dimensions> object may have the optional SBML Level 3 Core subobjects for notes and annotations. No other elements from the SBML Level 3 Core namespace are permitted on a <dimensions> (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21702), "A <dimensions> object may have the optional SBML Level 3 Core attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are permitted on a <dimensions> (Reference: L3V1 Core Section 3.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21703), "A <dimensions> object must have the required attributes 'layout:width' and 'layout:height' and may have the optional attributes 'layout:id', and 'layout:depth'.  No other attributes from the Layout namespace are permitted on a <dimensions> (Reference: L3V1 Layout V1 Section 3.4.2).");  

        contents.put(Integer.toString(SBMLErrorCodes.LAYOUT_21704), "The attributes 'layout:width', 'layout:height' and 'layout:depth' of a <dimensions> element must be of the data type 'double' (Reference: L3V1 Layout V1 Section 3.4.2).");  
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
