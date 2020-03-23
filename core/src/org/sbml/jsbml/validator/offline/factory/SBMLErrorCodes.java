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

package org.sbml.jsbml.validator.offline.factory;



/**
 * Automatically generated file, using the python scripts extractErrors.py on the libSBML python library
 * and createErrorCodeInterface.py on the generated json file.
 *
 * @since 1.2
 */
public interface SBMLErrorCodes { 
 

	 /**
	  * Error code 0:
	  * Unrecognized error encountered internally. 
	  */
 	 public static final int CORE_00000 = 0; 

	 /**
	  * Error code 1:
	  * Out of memory. 
	  */
 	 public static final int CORE_00001 = 1; 

	 /**
	  * Error code 2:
	  * File unreadable. 
	  */
 	 public static final int CORE_00002 = 2; 

	 /**
	  * Error code 3:
	  * File unwritable. 
	  */
 	 public static final int CORE_00003 = 3; 

	 /**
	  * Error code 4:
	  * Error encountered while attempting file operation. 
	  */
 	 public static final int CORE_00004 = 4; 

	 /**
	  * Error code 5:
	  * Network access error. 
	  */
 	 public static final int CORE_00005 = 5; 

	 /**
	  * Error code 101:
	  * Internal XML parser state error. 
	  */
 	 public static final int CORE_00101 = 101; 

	 /**
	  * Error code 102:
	  * XML parser returned an unrecognized error code. 
	  */
 	 public static final int CORE_00102 = 102; 

	 /**
	  * Error code 103:
	  * Character transcoder error. 
	  */
 	 public static final int CORE_00103 = 103; 

	 /**
	  * Error code 1001:
	  * Missing XML declaration at beginning of XML input. 
	  */
 	 public static final int CORE_01001 = 1001; 

	 /**
	  * Error code 1002:
	  * Missing encoding attribute in XML declaration. 
	  */
 	 public static final int CORE_01002 = 1002; 

	 /**
	  * Error code 1003:
	  * Invalid or unrecognized XML declaration or XML encoding. 
	  */
 	 public static final int CORE_01003 = 1003; 

	 /**
	  * Error code 1004:
	  * Invalid, malformed or unrecognized XML DOCTYPE declaration. 
	  */
 	 public static final int CORE_01004 = 1004; 

	 /**
	  * Error code 1005:
	  * Invalid character in XML content. 
	  */
 	 public static final int CORE_01005 = 1005; 

	 /**
	  * Error code 1006:
	  * XML content is not well-formed. 
	  */
 	 public static final int CORE_01006 = 1006; 

	 /**
	  * Error code 1007:
	  * Unclosed XML token. 
	  */
 	 public static final int CORE_01007 = 1007; 

	 /**
	  * Error code 1008:
	  * XML construct is invalid or not permitted. 
	  */
 	 public static final int CORE_01008 = 1008; 

	 /**
	  * Error code 1009:
	  * Element tag mismatch or missing tag. 
	  */
 	 public static final int CORE_01009 = 1009; 

	 /**
	  * Error code 1010:
	  * Duplicate XML attribute. 
	  */
 	 public static final int CORE_01010 = 1010; 

	 /**
	  * Error code 1011:
	  * Undefined XML entity. 
	  */
 	 public static final int CORE_01011 = 1011; 

	 /**
	  * Error code 1012:
	  * Invalid, malformed or unrecognized XML processing instruction. 
	  */
 	 public static final int CORE_01012 = 1012; 

	 /**
	  * Error code 1013:
	  * Invalid or undefined XML namespace prefix. 
	  */
 	 public static final int CORE_01013 = 1013; 

	 /**
	  * Error code 1014:
	  * Invalid XML namespace prefix value. 
	  */
 	 public static final int CORE_01014 = 1014; 

	 /**
	  * Error code 1015:
	  * Missing a required XML attribute. 
	  */
 	 public static final int CORE_01015 = 1015; 

	 /**
	  * Error code 1016:
	  * Data type mismatch for the value of an attribute. 
	  */
 	 public static final int CORE_01016 = 1016; 

	 /**
	  * Error code 1017:
	  * Invalid UTF8 content. 
	  */
 	 public static final int CORE_01017 = 1017; 

	 /**
	  * Error code 1018:
	  * Missing or improperly formed attribute value. 
	  */
 	 public static final int CORE_01018 = 1018; 

	 /**
	  * Error code 1019:
	  * Invalid or unrecognizable attribute value. 
	  */
 	 public static final int CORE_01019 = 1019; 

	 /**
	  * Error code 1020:
	  * Invalid, unrecognized or malformed attribute. 
	  */
 	 public static final int CORE_01020 = 1020; 

	 /**
	  * Error code 1021:
	  * Element either not recognized or not permitted. 
	  */
 	 public static final int CORE_01021 = 1021; 

	 /**
	  * Error code 1022:
	  * Badly formed XML comment. 
	  */
 	 public static final int CORE_01022 = 1022; 

	 /**
	  * Error code 1023:
	  * XML declaration not permitted in this location. 
	  */
 	 public static final int CORE_01023 = 1023; 

	 /**
	  * Error code 1024:
	  * Reached end of input unexpectedly. 
	  */
 	 public static final int CORE_01024 = 1024; 

	 /**
	  * Error code 1025:
	  * Value is invalid for XML ID, or has already been used. 
	  */
 	 public static final int CORE_01025 = 1025; 

	 /**
	  * Error code 1026:
	  * XML ID value was never declared. 
	  */
 	 public static final int CORE_01026 = 1026; 

	 /**
	  * Error code 1027:
	  * Unable to interpret content. 
	  */
 	 public static final int CORE_01027 = 1027; 

	 /**
	  * Error code 1028:
	  * Bad XML document structure. 
	  */
 	 public static final int CORE_01028 = 1028; 

	 /**
	  * Error code 1029:
	  * Encountered invalid content after expected content. 
	  */
 	 public static final int CORE_01029 = 1029; 

	 /**
	  * Error code 1030:
	  * Expected to find a quoted string. 
	  */
 	 public static final int CORE_01030 = 1030; 

	 /**
	  * Error code 1031:
	  * An empty value is not permitted in this context. 
	  */
 	 public static final int CORE_01031 = 1031; 

	 /**
	  * Error code 1032:
	  * Invalid or unrecognized number. 
	  */
 	 public static final int CORE_01032 = 1032; 

	 /**
	  * Error code 1033:
	  * Colon characters are invalid in this context. 
	  */
 	 public static final int CORE_01033 = 1033; 

	 /**
	  * Error code 1034:
	  * One or more expected elements are missing. 
	  */
 	 public static final int CORE_01034 = 1034; 

	 /**
	  * Error code 1035:
	  * Main XML content is empty. 
	  */
 	 public static final int CORE_01035 = 1035; 

	 /**
	  * Error code 9999:
	  */
 	 public static final int CORE_09999 = 9999; 

	 /**
	  * Error code 10000:
	  * Unrecognized error encountered by libSBML 
	  */
 	 public static final int CORE_10000 = 10000; 

	 /**
	  * Error code 10101:
	  * An SBML XML file must use UTF-8 as the character encoding. More precisely, the 
	  * 'encoding' attribute of the XML declaration at the beginning of the XML data 
	  * stream cannot have a value other than 'UTF-8'. An example valid declaration is 
	  * '&lt;?xml version="1.0" encoding="UTF-8"?&gt;'. Reference: L3V1 Section 4.1 
	  */
 	 public static final int CORE_10101 = 10101; 

	 /**
	  * Error code 10102:
	  * An SBML XML document must not contain undefined elements or attributes in the 
	  * SBML namespace. Documents containing unknown elements or attributes placed in 
	  * the SBML namespace do not conform to the SBML specification. Reference: L3V1 
	  * Section 4.1 
	  */
 	 public static final int CORE_10102 = 10102; 

	 /**
	  * Error code 10103:
	  * An SBML XML document must conform to the XML Schema for the corresponding SBML 
	  * Level, Version and Release. The XML Schema for SBML defines the basic SBML 
	  * object structure, the data types used by those objects, and the order in which 
	  * the objects may appear in an SBML document. 
	  */
 	 public static final int CORE_10103 = 10103; 

	 /**
	  * Error code 10104:
	  * An SBML document must conform to the rules of XML well-formedness defined in the 
	  * XML 1.0 specification. These rules define the basic structural and syntactic 
	  * constraints with which all XML documents must comply. 
	  */
 	 public static final int CORE_10104 = 10104; 

	 /**
	  * Error code 10201:
	  * All MathML content in SBML must appear within a &lt;math&gt; element, and the &lt;math&gt; 
	  * element must be either explicitly or implicitly in the XML namespace 
	  * "http://www.w3.org/1998/Math/MathML". Reference: L3V1 Section 3.4 
	  */
 	 public static final int CORE_10201 = 10201; 

	 /**
	  * Error code 10202:
	  * The only permitted MathML 2.0 elements in SBML Level 2 are the following: &lt;cn&gt;, 
	  * &lt;ci&gt;, &lt;csymbol&gt;, &lt;sep&gt;, &lt;apply&gt;, &lt;piecewise&gt;, &lt;piece&gt;, &lt;otherwise&gt;, &lt;eq&gt;, &lt;neq&gt;, 
	  * &lt;gt&gt;, &lt;lt&gt;, &lt;geq&gt;, &lt;leq&gt;, &lt;plus&gt;, &lt;minus&gt;, &lt;times&gt;, &lt;divide&gt;, &lt;power&gt;, &lt;root&gt;, 
	  * &lt;abs&gt;, &lt;exp&gt;, &lt;ln&gt;, &lt;log&gt;, &lt;floor&gt;, &lt;ceiling&gt;, &lt;factorial&gt;, &lt;and&gt;, &lt;or&gt;, &lt;xor&gt;, 
	  * &lt;not&gt;, &lt;degree&gt;, &lt;bvar&gt;, &lt;logbase&gt;, &lt;sin&gt;, &lt;cos&gt;, &lt;tan&gt;, &lt;sec&gt;, &lt;csc&gt;, &lt;cot&gt;, 
	  * &lt;sinh&gt;, &lt;cosh&gt;, &lt;tanh&gt;, &lt;sech&gt;, &lt;csch&gt;, &lt;coth&gt;, &lt;arcsin&gt;, &lt;arccos&gt;, &lt;arctan&gt;, 
	  * &lt;arcsec&gt;, &lt;arccsc&gt;, &lt;arccot&gt;, &lt;arcsinh&gt;, &lt;arccosh&gt;, &lt;arctanh&gt;, &lt;arcsech&gt;, 
	  * &lt;arccsch&gt;, &lt;arccoth&gt;, &lt;true&gt;, &lt;false&gt;, &lt;notanumber&gt;, &lt;pi&gt;, &lt;infinity&gt;, 
	  * &lt;exponentiale&gt;, &lt;semantics&gt;, &lt;annotation&gt;, and &lt;annotation-xml&gt;. SBML L3V2 added 
	  * &lt;implies&gt;, &lt;max&gt;, &lt;min&gt;, &lt;rem&gt; and &lt;quotient&gt;. Reference: L3V1 Section 3.4.1 
	  */
 	 public static final int CORE_10202 = 10202; 

	 /**
	  * Error code 10203:
	  * In the SBML subset of MathML 2.0, the MathML attribute 'encoding' is only 
	  * permitted on &lt;csymbol&gt;. No other MathML elements may have an 'encoding' 
	  * attribute. An SBML L3 Package may allow the 'encoding' attribute in other 
	  * places, and if so, the package must define required = 'true' on the SBML 
	  * container element &lt;sbml&gt;. Reference: L3V1 Section 3.4.1 
	  */
 	 public static final int CORE_10203 = 10203; 

	 /**
	  * Error code 10204:
	  * In the SBML subset of MathML 2.0, the MathML attribute 'definitionURL' is only 
	  * permitted on &lt;csymbol&gt;, &lt;semantics&gt; or &lt;ci&gt; (Level 2 Version 5 and Level 3 
	  * only). No other MathML elements may have a 'definitionURL' attribute. An SBML L3 
	  * Package may allow the 'definitionURL' attribute in other places, and if so, the 
	  * package must define required = 'true' on the SBML container element &lt;sbml&gt;. 
	  * Reference: L3V1 Section 3.4.1 
	  */
 	 public static final int CORE_10204 = 10204; 

	 /**
	  * Error code 10205:
	  * In SBML Level 2, the only values permitted for 'definitionURL' on a &lt;csymbol&gt; 
	  * element are "http://www.sbml.org/sbml/symbols/time" and 
	  * "http://www.sbml.org/sbml/symbols/delay". SBML Level 3 added 
	  * "http://www.sbml.org/sbml/symbols/avogadro". SBML Level 3 Version 2 added 
	  * "http://www.sbml.org/sbml/symbols/rateOf" An SBML L3 Package may allow new 
	  * values for the 'definitionURL' attribute of a csymbol, and if so, the package 
	  * must define required = 'true' on the SBML container element &lt;sbml&gt;. Reference: 
	  * L3V1 Section 3.4.6 
	  */
 	 public static final int CORE_10205 = 10205; 

	 /**
	  * Error code 10206:
	  * In the SBML subset of MathML 2.0, the MathML attribute 'type' is only permitted 
	  * on the &lt;cn&gt; construct. No other MathML elements may have a 'type' attribute. An 
	  * SBML L3 Package may allow the 'type' attribute in other places, and if so, the 
	  * package must define required = 'true' on the SBML container element &lt;sbml&gt;. 
	  * Reference: L3V1 Section 3.4.1 
	  */
 	 public static final int CORE_10206 = 10206; 

	 /**
	  * Error code 10207:
	  * The only permitted values for the 'type' attribute on MathML &lt;cn&gt; elements are 
	  * 'e-notation', 'real', 'integer', and 'rational'. An SBML L3 Package may allow 
	  * new values for the 'type' attribute, and if so, the package must define required 
	  * = 'true' on the SBML container element &lt;sbml&gt;. Reference: L3V1 Section 3.4.2 
	  */
 	 public static final int CORE_10207 = 10207; 

	 /**
	  * Error code 10208:
	  * MathML &lt;lambda&gt; elements are only permitted as the first element inside the 
	  * 'math' element of a &lt;functionDefinition&gt; or as the first element of a semantics 
	  * element immediately inside inside the math element of a &lt;functionDefinition&gt;; 
	  * they may not be used elsewhere in an SBML model. An SBML L3 Package may allow 
	  * the lambda element in other places, and if so, the package must define required 
	  * = 'true' on the SBML container element &lt;sbml&gt;. Reference: L3V1 Sections 3.4.1 
	  * and 4.3.2 
	  */
 	 public static final int CORE_10208 = 10208; 

	 /**
	  * Error code 10209:
	  * The arguments of the MathML logical operators &lt;and&gt;, &lt;or&gt;, &lt;xor&gt;, and &lt;not&gt; must 
	  * have Boolean values. Reference: L3V1 Section 3.4.9 
	  */
 	 public static final int CORE_10209 = 10209; 

	 /**
	  * Error code 10210:
	  * The arguments to the following MathML constructs must have a numeric type: 
	  * &lt;plus&gt;, &lt;minus&gt;, &lt;times&gt;, &lt;divide&gt;, &lt;power&gt;, &lt;root&gt;, &lt;abs&gt;, &lt;exp&gt;, &lt;ln&gt;, &lt;log&gt;, 
	  * &lt;floor&gt;, &lt;ceiling&gt;, &lt;factorial&gt;, &lt;sin&gt;, &lt;cos&gt;, &lt;tan&gt;, &lt;sec&gt;, &lt;csc&gt;, &lt;cot&gt;, 
	  * &lt;sinh&gt;, &lt;cosh&gt;, &lt;tanh&gt;, &lt;sech&gt;, &lt;csch&gt;, &lt;coth&gt;, &lt;arcsin&gt;, &lt;arccos&gt;, &lt;arctan&gt;, 
	  * &lt;arcsec&gt;, &lt;arccsc&gt;, &lt;arccot&gt;, &lt;arcsinh&gt;, &lt;arccosh&gt;, &lt;arctanh&gt;, &lt;arcsech&gt;, 
	  * &lt;arccsch&gt;, &lt;arccoth&gt;. Reference: L3V1 Section 3.4.9 
	  */
 	 public static final int CORE_10210 = 10210; 

	 /**
	  * Error code 10211:
	  * The values of all arguments to &lt;eq&gt; and &lt;neq&gt; operators must have the same type 
	  * (either all Boolean or all numeric). Reference: L3V1 Section 3.4.9 
	  */
 	 public static final int CORE_10211 = 10211; 

	 /**
	  * Error code 10212:
	  * The types of values within &lt;piecewise&gt; operators must all be consistent: the set 
	  * of expressions that make up the first arguments of the &lt;piece&gt; and &lt;otherwise&gt; 
	  * operators within the same &lt;piecewise&gt; operator should all return values of the 
	  * same type. Reference: L3V1 Section 3.4.9 
	  */
 	 public static final int CORE_10212 = 10212; 

	 /**
	  * Error code 10213:
	  * The second argument of a MathML &lt;piece&gt; operator must have a Boolean value. 
	  * Reference: L3V1 Section 3.4.9 
	  */
 	 public static final int CORE_10213 = 10213; 

	 /**
	  * Error code 10214:
	  * Outside of a &lt;functionDefinition&gt;, if a &lt;ci&gt; element is the first element within 
	  * a MathML &lt;apply&gt;, then the &lt;ci&gt;'s value can only be chosen from the set of 
	  * identifiers of &lt;functionDefinition&gt;s defined in the SBML model. Reference: L3V1 
	  * Section 4.3.2 
	  */
 	 public static final int CORE_10214 = 10214; 

	 /**
	  * Error code 10215:
	  * Outside of a &lt;functionDefinition&gt;, if a &lt;ci&gt; element is not the first element 
	  * within a MathML &lt;apply&gt;, then the &lt;ci&gt;'s value can only be chosen from the set 
	  * of identifiers of (in L2V1) &lt;species&gt;, &lt;compartment&gt;, or &lt;parameter&gt; objects; 
	  * (in L2V2-L2V5), &lt;species&gt;, &lt;compartment&gt;, &lt;parameter&gt; or &lt;reaction&gt; objects; (in 
	  * L3V1) &lt;species&gt;, &lt;compartment&gt;, &lt;parameter&gt;, &lt;reaction&gt; or &lt;speciesReference&gt; 
	  * objects and (in L3V2) &lt;species&gt;, &lt;compartment&gt;, &lt;parameter&gt;, &lt;reaction&gt;, 
	  * &lt;speciesReference&gt; objects or L3 package objects with defined mathematical 
	  * meaning. Reference: L3V1 Section 3.4.3 
	  */
 	 public static final int CORE_10215 = 10215; 

	 /**
	  * Error code 10216:
	  * The 'id' value of a &lt;parameter&gt; or &lt;localParameter&gt; defined within a 
	  * &lt;kineticLaw&gt; can only be used in &lt;ci&gt; elements within the MathML content of that 
	  * same &lt;kineticLaw&gt;; the identifier is not visible to other parts of the model. 
	  * Reference: L3V1 Sections 3.3.1, 3.4.3 and 4.13.5 
	  */
 	 public static final int CORE_10216 = 10216; 

	 /**
	  * Error code 10217:
	  * The MathML formulas in the following elements must yield numeric expressions: 
	  * &lt;math&gt; in &lt;kineticLaw&gt;, &lt;stoichiometryMath&gt; in &lt;speciesReference&gt;, &lt;math&gt; in 
	  * &lt;initialAssignment&gt;, &lt;math&gt; in &lt;assignmentRule&gt;, &lt;math&gt; in &lt;rateRule&gt;, &lt;math&gt; in 
	  * &lt;algebraicRule&gt;, and &lt;delay&gt; in &lt;event&gt;, and &lt;math&gt; in &lt;eventAssignment&gt;. 
	  * Reference: L3V1 Sections 4.8, 4.9, 4.11 and 4.12 
	  */
 	 public static final int CORE_10217 = 10217; 

	 /**
	  * Error code 10218:
	  * A MathML operator must be supplied the number of arguments appropriate for that 
	  * operator. Reference: L3V1 Section 3.4.1 
	  */
 	 public static final int CORE_10218 = 10218; 

	 /**
	  * Error code 10219:
	  * The number of arguments used in a call to a function defined by a 
	  * &lt;functionDefinition&gt; must equal the number of arguments accepted by that 
	  * function, or in other words, the number of &lt;bvar&gt; elements inside the &lt;lambda&gt; 
	  * element of the function definition. Reference: L3V1 Section 4.3.4 
	  */
 	 public static final int CORE_10219 = 10219; 

	 /**
	  * Error code 10220:
	  * The SBML attribute 'units' may only be added to MathML &lt;cn&gt; elements; no other 
	  * MathML elements are permitted to have the 'units' attribute. An SBML L3 Package 
	  * may allow the 'units' attribute in other places, and if so, the package must 
	  * define required = 'true' on the SBML container element &lt;sbml&gt;. Reference: L3V1 
	  * Section 3.4.2 
	  */
 	 public static final int CORE_10220 = 10220; 

	 /**
	  * Error code 10221:
	  * The value of the SBML attribute 'units' on a MathML &lt;cn&gt; element must be chosen 
	  * from either the set of identifiers of UnitDefinition objects in the model, or 
	  * the set of base units defined by SBML. Reference: L3V1 Section 3.4.2 
	  */
 	 public static final int CORE_10221 = 10221; 

	 /**
	  * Error code 10222:
	  * The value of a &lt;ci&gt; element may not be the identifier of a &lt;compartment&gt; with a 
	  * 'spatialDimensions' value of 0. 
	  */
 	 public static final int CORE_10222 = 10222; 

	 /**
	  * Error code 10223:
	  * The single argument for the rateOf csymbol function must be a &lt;ci&gt; element. 
	  */
 	 public static final int CORE_10223 = 10223; 

	 /**
	  * Error code 10224:
	  * The target of a 'rateOf' &lt;csymbol&gt; function must not appear as the 'variable' of 
	  * an &lt;assignmentRule&gt;, nor may its value be determined by an &lt;algebraicRule&gt;. 
	  */
 	 public static final int CORE_10224 = 10224; 

	 /**
	  * Error code 10225:
	  * If the target of a 'rateOf' &lt;csymbol&gt; function is a &lt;species&gt; with a 
	  * 'hasOnlySubstanceUnits' value of 'false', the &lt;compartment&gt; of that &lt;species&gt; 
	  * must not appear as the 'variable' of an &lt;assignmentRule&gt;, nor may its 'size' be 
	  * determined by an &lt;algebraicRule&gt;. 
	  */
 	 public static final int CORE_10225 = 10225; 

	 /**
	  * Error code 10301:
	  * The value of the 'id' field on every instance of the following type of object in 
	  * a model must be unique: &lt;model&gt;, &lt;functionDefinition&gt;, &lt;compartmentType&gt;, 
	  * &lt;compartment&gt;, &lt;speciesType&gt;, &lt;species&gt;, &lt;reaction&gt;, &lt;speciesReference&gt;, 
	  * &lt;modifierSpeciesReference&gt;, &lt;event&gt;, and model-wide &lt;parameter&gt;s. Note that 
	  * &lt;unitDefinition&gt; and parameters defined inside a reaction are treated 
	  * separately. Reference: L3V1 Section 3.3 
	  */
 	 public static final int CORE_10301 = 10301; 

	 /**
	  * Error code 10302:
	  * The value of the 'id' field of every &lt;unitDefinition&gt; must be unique across the 
	  * set of all &lt;unitDefinition&gt;s in the entire model. Reference: L3V1 Sections 3.3 
	  * and 4.4 
	  */
 	 public static final int CORE_10302 = 10302; 

	 /**
	  * Error code 10303:
	  * The value of the 'id' field of each parameter defined locally within a 
	  * &lt;kineticLaw&gt; must be unique across the set of all such parameter definitions in 
	  * that &lt;kineticLaw&gt;. Reference: L3V1 Sections 3.3.1 and 4.11.5 
	  */
 	 public static final int CORE_10303 = 10303; 

	 /**
	  * Error code 10304:
	  * The value of the 'variable' field in all &lt;assignmentRule&gt; and &lt;rateRule&gt; 
	  * definitions must be unique across the set of all such rule definitions in a 
	  * model. Reference: L3V1 Section 4.9.3 
	  */
 	 public static final int CORE_10304 = 10304; 

	 /**
	  * Error code 10305:
	  * In each &lt;event&gt;, the value of the 'variable' field within every 
	  * &lt;eventAssignment&gt; definition must be unique across the set of all 
	  * &lt;eventAssignment&gt;s within that &lt;event&gt;. Reference: L3V1 Section 4.12.4 
	  */
 	 public static final int CORE_10305 = 10305; 

	 /**
	  * Error code 10306:
	  * An identifier used as the value of 'variable' in an &lt;eventAssignment&gt; cannot 
	  * also appear as the value of 'variable' in an &lt;assignmentRule&gt;. Reference: L3V1 
	  * Section 4.12.4 
	  */
 	 public static final int CORE_10306 = 10306; 

	 /**
	  * Error code 10307:
	  * Every 'metaid' attribute value must be unique across the set of all 'metaid' 
	  * values in a model. Reference: L3V1 Sections 3.1.6 and 3.2.1 
	  */
 	 public static final int CORE_10307 = 10307; 

	 /**
	  * Error code 10308:
	  * The value of an 'sboTerm' attribute must have the data type 'SBOTerm', which is 
	  * a string consisting of the characters 'S', 'B', 'O', ':' followed by exactly 
	  * seven digits. Reference: L3V1 Section 3.1.11 
	  */
 	 public static final int CORE_10308 = 10308; 

	 /**
	  * Error code 10309:
	  * The syntax of 'metaid' attribute values must conform to the syntax of the XML 
	  * type 'ID'. Reference: L3V1 Sections 3.2.1 and 3.1.6 
	  */
 	 public static final int CORE_10309 = 10309; 

	 /**
	  * Error code 10310:
	  * The syntax of 'id' attribute values must conform to the syntax of the SBML type 
	  * 'SId'. Reference: L3V1 Section 3.1.7 
	  */
 	 public static final int CORE_10310 = 10310; 

	 /**
	  * Error code 10311:
	  * The syntax of unit identifiers (i.e., the values of the 'id' attribute on 
	  * UnitDefinition, the 'units' attribute on Compartment, the 'units' attribute on 
	  * Parameter, and the 'substanceUnits' attribute on Species) must conform to the 
	  * syntax of the SBML type UnitSId. Reference: L3V1 Section 3.1.9 
	  */
 	 public static final int CORE_10311 = 10311; 

	 /**
	  * Error code 10312:
	  * Message Reference: L3V1 Section 3.1.1 
	  */
 	 public static final int CORE_10312 = 10312; 

	 /**
	  * Error code 10313:
	  * Unit identifier references (i.e the 'units' attribute on &lt;Compartment&gt;, the 
	  * 'units' attribute on &lt;Parameter&gt;, and the 'substanceUnits' attribute on 
	  * &lt;Species&gt;) must be the identifier of a &lt;UnitDefinition&gt; in the &lt;Model&gt;, or the 
	  * identifier of a predefined unit in SBML. 
	  */
 	 public static final int CORE_10313 = 10313; 

	 /**
	  * Error code 10401:
	  * Every top-level element within an annotation element must have a namespace 
	  * declared. Reference: L3V1 Section 3.2.4 
	  */
 	 public static final int CORE_10401 = 10401; 

	 /**
	  * Error code 10402:
	  * There cannot be more than one top-level element using a given namespace inside a 
	  * given annotation element. Reference: L3V1 Section 3.2.4 
	  */
 	 public static final int CORE_10402 = 10402; 

	 /**
	  * Error code 10403:
	  * Top-level elements within an annotation element cannot use any SBML namespace, 
	  * whether explicitly (by declaring the namespace to be one of the URIs 
	  * "http://www.sbml.org/sbml/level1", "http://www.sbml.org/sbml/level2", 
	  * "http://www.sbml.org/sbml/level2/version2", or 
	  * "http://www.sbml.org/sbml/level2/version3", or 
	  * "http://www.sbml.org/sbml/level2/version4", or 
	  * "http://www.sbml.org/sbml/level2/version5" or 
	  * "http://www.sbml.org/sbml/level3/version1/core"), or implicitly (by failing to 
	  * declare any namespace). 
	  */
 	 public static final int CORE_10403 = 10403; 

	 /**
	  * Error code 10404:
	  * A given SBML object may contain at most one &lt;annotation&gt; element. Reference: 
	  * L3V1 Section 3.2 
	  */
 	 public static final int CORE_10404 = 10404; 

	 /**
	  * Error code 10501:
	  */
 	 public static final int CORE_10501 = 10501; 

	 /**
	  * Error code 10503:
	  * The unit of measurement associated with the mathematical formula in the MathML 
	  * math element of every KineticLaw object in a model should be identical to all 
	  * KineticLaw objects in the model. Reference: L3V1 Section 3.4 
	  */
 	 public static final int CORE_10503 = 10503; 

	 /**
	  * Error code 10511:
	  * When the 'variable' in an &lt;assignmentRule&gt; refers to a &lt;compartment&gt;, the units 
	  * of the rule's right-hand side are expected to be consistent with the units of 
	  * that compartment's size. Reference: L3V1 Section 4.9.3 
	  */
 	 public static final int CORE_10511 = 10511; 

	 /**
	  * Error code 10512:
	  * When the 'variable' in an &lt;assignmentRule&gt; refers to a &lt;species&gt;, the units of 
	  * the rule's right-hand side are expected to be consistent with the units of the 
	  * species' quantity. Reference: L3V1 Section 4.9.3 
	  */
 	 public static final int CORE_10512 = 10512; 

	 /**
	  * Error code 10513:
	  * When the 'variable' in an &lt;assignmentRule&gt; refers to a &lt;parameter&gt;, the units of 
	  * the rule's right-hand side are expected to be consistent with the units declared 
	  * for that parameter. Reference: L3V1 Section 4.9.3 
	  */
 	 public static final int CORE_10513 = 10513; 

	 /**
	  * Error code 10514:
	  * When the value of the attribute variable in an AssignmentRule object refers to a 
	  * SpeciesReference object, the unit of measurement associated with the rule's 
	  * right-hand side should be consistent with the unit of stoichiometry, that is, 
	  * dimensionless. Reference: L3V1 Section 4.9.3 
	  */
 	 public static final int CORE_10514 = 10514; 

	 /**
	  * Error code 10521:
	  * When the 'variable' in an &lt;initialAssignment&gt; refers to a &lt;compartment&gt;, the 
	  * units of the &lt;initialAssignment&gt;'s &lt;math&gt; expression are expected to be 
	  * consistent with the units of that compartment's size. Reference: L3V1 Section 
	  * 4.8 
	  */
 	 public static final int CORE_10521 = 10521; 

	 /**
	  * Error code 10522:
	  * When the 'variable' in an &lt;initialAssignment&gt; refers to a &lt;species&gt;, the units 
	  * of the &lt;initialAssignment&gt;'s &lt;math&gt; expression are expected to be consistent 
	  * with the units of that species' quantity. Reference: L3V1 Section 4.8 
	  */
 	 public static final int CORE_10522 = 10522; 

	 /**
	  * Error code 10523:
	  * When the 'variable' in an &lt;initialAssignment&gt; refers to a &lt;parameter&gt;, the units 
	  * of the &lt;initialAssignment&gt;'s &lt;math&gt; expression are expected to be consistent 
	  * with the units declared for that parameter. Reference: L3V1 Section 4.8 
	  */
 	 public static final int CORE_10523 = 10523; 

	 /**
	  * Error code 10524:
	  * When the value of the attribute variable in an InitialAssignment object refers 
	  * to a SpeciesReference object, the unit of measurement associated with the 
	  * InitialAssignment's math expression should be consistent with the unit of 
	  * stoichiometry, that is, dimensionless. Reference: L3V1 Section 4.8 
	  */
 	 public static final int CORE_10524 = 10524; 

	 /**
	  * Error code 10531:
	  * When the 'variable' in a &lt;rateRule&gt; definition refers to a &lt;compartment&gt;, the 
	  * units of the rule's right-hand side are expected to be of the form _x per time_, 
	  * where _x_ is either the 'units' in that &lt;compartment&gt; definition, or (in the 
	  * absence of explicit units declared for the compartment size) the default units 
	  * for that compartment, and _time_ refers to the units of time for the model. 
	  * Reference: L3V1 Sections 4.5.4, 4.2.4 AND 4.9.4 
	  */
 	 public static final int CORE_10531 = 10531; 

	 /**
	  * Error code 10532:
	  * When the 'variable' in a &lt;rateRule&gt; definition refers to a &lt;species&gt;, the units 
	  * of the rule's right-hand side are expected to be of the form _x per time_, where 
	  * _x_ is the units of that species' quantity, and _time_ refers to the units of 
	  * time for the model. Reference: L3V1 Sections 4.6.5, 4.2.4 AND 4.9.4 
	  */
 	 public static final int CORE_10532 = 10532; 

	 /**
	  * Error code 10533:
	  * When the 'variable' in a &lt;rateRule&gt; definition refers to a &lt;parameter&gt;, the 
	  * units of the rule's right-hand side are expected to be of the form _x per time_, 
	  * where _x_ is the 'units' in that &lt;parameter&gt; definition, and _time_ refers to 
	  * the units of time for the model. Reference: L3V1 Sections 4.7.3, 4.2.4 AND 4.9.4 
	  */
 	 public static final int CORE_10533 = 10533; 

	 /**
	  * Error code 10534:
	  * When the value of the attribute variable in a RateRule object refers to a 
	  * SpeciesReference object, the unit of measurement associated with the RateRule's 
	  * math expression should be consistent with {unit derived from 
	  * dimensionless}/{unit of time}. 
	  */
 	 public static final int CORE_10534 = 10534; 

	 /**
	  * Error code 10541:
	  * The units of the 'math' formula in a &lt;kineticLaw&gt; definition are expected to be 
	  * the equivalent of _substance per time_. Reference: L3V1 Sections 4.11.7, 4.2.4 
	  * AND 4.9.4 
	  */
 	 public static final int CORE_10541 = 10541; 

	 /**
	  * Error code 10542:
	  * For every Species object produced or consumed in a reaction (that is, referenced 
	  * by a SpeciesReference object), the unit of measurement of the species' substance 
	  * should be consistent with the unit of extent for the model times the unit of the 
	  * conversion factor for that species. More precisely, the product of the units 
	  * indicated by the Model object's extentUnits and the conversionFactor attribute 
	  * for that particular Species (whether the attribute is set directly on the 
	  * Species object or inherited from the enclosing Model object) should be 
	  * consistent with the unit specified by that Species object's substanceUnits 
	  * attribute value. Reference: L3V1 Section 4.2.6 
	  */
 	 public static final int CORE_10542 = 10542; 

	 /**
	  * Error code 10551:
	  * When a value for &lt;delay&gt; is given in a &lt;event&gt; definition, the units of the 
	  * delay formula are expected to correspond to either the value of 'timeUnits' in 
	  * the &lt;event&gt; or (if no 'timeUnits' are given), the model's default units of time. 
	  * Reference: L3V1 Section 4.12.3 
	  */
 	 public static final int CORE_10551 = 10551; 

	 /**
	  * Error code 10561:
	  * When the 'variable' in an &lt;eventAssignment&gt; refers to a &lt;compartment&gt;, the units 
	  * of the &lt;eventAssignment&gt;'s &lt;math&gt; expression are expected to be consistent with 
	  * the units of that compartment's size. Reference: L3V1 Section 4.12.4 
	  */
 	 public static final int CORE_10561 = 10561; 

	 /**
	  * Error code 10562:
	  * When the 'variable' in an &lt;eventAssignment&gt; refers to a &lt;species&gt;, the units of 
	  * the &lt;eventAssignment&gt;'s &lt;math&gt; expression are expected to be consistent with the 
	  * units of the species' quantity. Reference: L3V1 Section 4.12.4 
	  */
 	 public static final int CORE_10562 = 10562; 

	 /**
	  * Error code 10563:
	  * When the 'variable' in an &lt;eventAssignment&gt; refers to a &lt;parameter&gt;, the units 
	  * of the &lt;eventAssignment&gt;'s &lt;math&gt; expression are expected to be consistent with 
	  * the units declared for that parameter. Reference: L3V1 Section 4.12.4 
	  */
 	 public static final int CORE_10563 = 10563; 

	 /**
	  * Error code 10564:
	  * When the value of the attribute variable of an EventAssignment object is the 
	  * identifier of a SpeciesReference object, the unit of measurement associated with 
	  * the EventAssignment's math expression should be consistent with the unit of 
	  * stoichiometry, i.e., dimensionless. Reference: L3V1 Section 4.12.4 
	  */
 	 public static final int CORE_10564 = 10564; 

	 /**
	  * Error code 10565:
	  * In an Event object, the unit of measurement associated with a Priority object's 
	  * &lt;math&gt; expression object should be 'dimensionless'. Reference: L3V1 Section 
	  * 4.12.3 
	  */
 	 public static final int CORE_10565 = 10565; 

	 /**
	  * Error code 10599:
	  */
 	 public static final int CORE_10599 = 10599; 

	 /**
	  * Error code 10601:
	  * The system of equations created from an SBML model must not be overdetermined. 
	  * Reference: L3V1 Section 4.9.5 
	  */
 	 public static final int CORE_10601 = 10601; 

	 /**
	  * Error code 10701:
	  * The value of the 'sboTerm' attribute on a &lt;model&gt; is expected to be an SBO 
	  * identifier (http://www.biomodels.net/SBO/). In SBML Level 2 prior to Version 4 
	  * the value is expected to be a term derived from SBO:0000004, "modeling 
	  * framework"; in Version 4 and above it is expected to be a term derived from 
	  * SBO:0000231 "occurring entity representation" Reference: L3V1 Section 5 
	  */
 	 public static final int CORE_10701 = 10701; 

	 /**
	  * Error code 10702:
	  * The value of the 'sboTerm' attribute on a &lt;functionDefinition&gt; is expected to be 
	  * an SBO identifier (http://www.biomodels.net/SBO/) referring to a mathematical 
	  * expression (i.e., terms derived from SBO:0000064, "mathematical expression"). 
	  * Reference: L3V1 Section 5 
	  */
 	 public static final int CORE_10702 = 10702; 

	 /**
	  * Error code 10703:
	  * The value of the 'sboTerm' attribute on a &lt;parameter&gt; is expected to be an SBO 
	  * identifier (http://www.biomodels.net/SBO/) referring to a quantitative parameter 
	  * defined in SBO (i.e., terms derived from SBO:0000002, "quantitative systems 
	  * description parameter"). Reference: L3V1 Section 5 
	  */
 	 public static final int CORE_10703 = 10703; 

	 /**
	  * Error code 10704:
	  * The value of the 'sboTerm' attribute on an &lt;initialAssignment&gt; is expected to be 
	  * an SBO identifier (http://www.biomodels.net/SBO/) referring to a mathematical 
	  * expression (i.e., terms derived from SBO:0000064, "mathematical expression"). 
	  * Reference: L3V1 Section 5 
	  */
 	 public static final int CORE_10704 = 10704; 

	 /**
	  * Error code 10705:
	  * The value of the 'sboTerm' attribute on a rule is expected to be an SBO 
	  * identifier (http://www.biomodels.net/SBO/) referring to a mathematical 
	  * expression (i.e., terms derived from SBO:0000064, "mathematical expression"). 
	  * Note: This applies to Algebraic Rules in addition to Rate and Assignment Rules. 
	  * Reference: L3V1 Section 5 
	  */
 	 public static final int CORE_10705 = 10705; 

	 /**
	  * Error code 10706:
	  * The value of the 'sboTerm' attribute on a &lt;constraint&gt; is expected to be an SBO 
	  * identifier (http://www.biomodels.net/SBO/) referring to a mathematical 
	  * expression (i.e., terms derived from SBO:0000064, "mathematical expression"). 
	  * Reference: L3V1 Section 5 
	  */
 	 public static final int CORE_10706 = 10706; 

	 /**
	  * Error code 10707:
	  * The value of the 'sboTerm' attribute on a &lt;reaction&gt; is expected to be an SBO 
	  * identifier (http://www.biomodels.net/SBO/) referring to an occurring entity 
	  * representation defined in SBO (i.e., terms derived from SBO:0000231, "occurring 
	  * entity representation"). Reference: L3V1 Section 5 
	  */
 	 public static final int CORE_10707 = 10707; 

	 /**
	  * Error code 10708:
	  * The value of the 'sboTerm' attribute on a &lt;speciesReference&gt; or 
	  * &lt;modifierSpeciesReference&gt; is expected to be an SBO identifier 
	  * (http://www.biomodels.net/SBO/) referring to a participant role. The appropriate 
	  * term depends on whether the object is a reactant, product or modifier. If a 
	  * reactant, then it should be a term in the SBO:0000010, "reactant" hierarchy; if 
	  * a product, then it should be a term in the SBO:0000011, "product" hierarchy; and 
	  * if a modifier, then it should be a term in the SBO:0000019, "modifier" 
	  * hierarchy. Reference: L3V1 Section 5 
	  */
 	 public static final int CORE_10708 = 10708; 

	 /**
	  * Error code 10709:
	  * The value of the 'sboTerm' attribute on a &lt;kineticLaw&gt; is expected to be an SBO 
	  * identifier (http://www.biomodels.net/SBO/) referring rate law defined in SBO 
	  * (i.e., terms derived from SBO:0000001, "rate law"). Reference: L3V1 Section 5 
	  */
 	 public static final int CORE_10709 = 10709; 

	 /**
	  * Error code 10710:
	  * The value of the 'sboTerm' attribute on an &lt;event&gt; is expected to be an SBO 
	  * identifier (http://www.biomodels.net/SBO/) referring to an occurring entity 
	  * representation defined in SBO (i.e., terms derived from SBO:0000231, "occurring 
	  * entity representation"). Reference: L3V1 Section 5 
	  */
 	 public static final int CORE_10710 = 10710; 

	 /**
	  * Error code 10711:
	  * The value of the 'sboTerm' attribute on an &lt;eventAssignment&gt; is expected to be 
	  * an SBO identifier (http://www.biomodels.net/SBO/) referring to a mathematical 
	  * expression (i.e., terms derived from SBO:0000064, "mathematical expression"). 
	  * Reference: L3V1 Section 5 
	  */
 	 public static final int CORE_10711 = 10711; 

	 /**
	  * Error code 10712:
	  * The value of the 'sboTerm' attribute on a &lt;compartment&gt; is expected to be an SBO 
	  * identifier (http://www.biomodels.net/SBO/). In SBML Level 2 prior to Version 4 
	  * it is expected to refer to a participant physical type (i.e., terms derived from 
	  * SBO:0000236, "participant physical type"); in Versions 4 and above it is 
	  * expected to refer to a material entity (i.e., terms derived from SBO:0000240, 
	  * "material entity"). Reference: L3V1 Section 5 
	  */
 	 public static final int CORE_10712 = 10712; 

	 /**
	  * Error code 10713:
	  * The value of the 'sboTerm' attribute on a &lt;species&gt; is expected to be an SBO 
	  * identifier (http://www.biomodels.net/SBO/). In SBML Level 2 prior to Version 4 
	  * it is expected to refer to a participant physical type (i.e., terms derived from 
	  * SBO:0000236, "participant physical type"); in Versions 4 and above it is 
	  * expected to refer to a material entity (i.e., terms derived from SBO:0000240, 
	  * "material entity"). Reference: L3V1 Section 5 
	  */
 	 public static final int CORE_10713 = 10713; 

	 /**
	  * Error code 10714:
	  * The value of the 'sboTerm' attribute on a &lt;compartmentType&gt; is expected to be an 
	  * SBO identifier (http://www.biomodels.net/SBO/). In SBML Level 2 prior to Version 
	  * 4 it is expected to refer to a participant physical type (i.e., terms derived 
	  * from SBO:0000236, "participant physical type"); in Versions 4 and above it is 
	  * expected to refer to a material entity (i.e., terms derived from SBO:0000240, 
	  * "material entity"). Reference: L3V1 Section 5 
	  */
 	 public static final int CORE_10714 = 10714; 

	 /**
	  * Error code 10715:
	  * The value of the 'sboTerm' attribute on a &lt;speciesType&gt; is expected to be an SBO 
	  * identifier (http://www.biomodels.net/SBO/). In SBML Level 2 prior to Version 4 
	  * it is expected to refer to a participant physical type (i.e., terms derived from 
	  * SBO:0000236, "participant physical type"); in Versions 4 and above it is 
	  * expected to refer to a material entity (i.e., terms derived from SBO:0000240, 
	  * "material entity"). 
	  */
 	 public static final int CORE_10715 = 10715; 

	 /**
	  * Error code 10716:
	  * The value of the 'sboTerm' attribute on a &lt;trigger&gt; is expected to be an SBO 
	  * identifier (http://www.biomodels.net/SBO/) referring to a mathematical 
	  * expression (i.e., terms derived from SBO:0000064, "mathematical expression"). 
	  */
 	 public static final int CORE_10716 = 10716; 

	 /**
	  * Error code 10717:
	  * The value of the 'sboTerm' attribute on a &lt;delay&gt; is expected to be an SBO 
	  * identifier (http://www.biomodels.net/SBO/) referring to a mathematical 
	  * expression (i.e., terms derived from SBO:0000064, "mathematical expression"). 
	  * Reference: L3V1 Section 5 
	  */
 	 public static final int CORE_10717 = 10717; 

	 /**
	  * Error code 10718:
	  * The value of the 'sboTerm' attribute on a &lt;localParameter&gt; is expected to be an 
	  * SBO identifier (http://www.biomodels.net/SBO/) referring to a quantitative 
	  * parameter (i.e., terms derived from SBO:0000002, "quantitive systems description 
	  * parameter"). Reference: L3V1 Section 5 
	  */
 	 public static final int CORE_10718 = 10718; 

	 /**
	  * Error code 10719:
	  * The value of the 'sboTerm' attribute on the &lt;sbml&gt; object is expected to be an 
	  * SBO identifier (http://www.biomodels.net/SBO/) referring to a modeling framework 
	  * (i.e., terms derived from SBO:0000004, "modelling framework"). Reference: L3V1 
	  * Section 5 
	  */
 	 public static final int CORE_10719 = 10719; 

	 /**
	  * Error code 10801:
	  * The contents of the &lt;notes&gt; element must be explicitly placed in the XHTML XML 
	  * namespace. Reference: L3V1 Section 3.2.3 
	  */
 	 public static final int CORE_10801 = 10801; 

	 /**
	  * Error code 10802:
	  * The contents of the &lt;notes&gt; element must not contain an XML declaration (i.e., a 
	  * string of the form "&lt;?xml version="1.0" encoding="UTF-8"?&gt;" or similar). 
	  * Reference: L3V1 Section 3.2.3 
	  */
 	 public static final int CORE_10802 = 10802; 

	 /**
	  * Error code 10803:
	  * The contents of the &lt;notes&gt; element must not contain an XML DOCTYPE declaration 
	  * (i.e., a string beginning with the characters "&lt;!DOCTYPE". Reference: L3V1 
	  * Section 3.2.3 
	  */
 	 public static final int CORE_10803 = 10803; 

	 /**
	  * Error code 10804:
	  * The XHTML content inside a &lt;notes&gt; element can only take one of the following 
	  * general forms: (1) a complete XHTML document beginning with the element &lt;html&gt; 
	  * and ending with &lt;/html&gt;; (2) the "body" portion of a document beginning with the 
	  * element &lt;body&gt; and ending with &lt;/body&gt;; or (3) XHTML content that is permitted 
	  * within a &lt;body&gt; ... &lt;/body&gt; elements. 
	  */
 	 public static final int CORE_10804 = 10804; 

	 /**
	  * Error code 10805:
	  * A given SBML object may contain at most one &lt;notes&gt; element. Reference: L3V1 
	  * Section 3.2 
	  */
 	 public static final int CORE_10805 = 10805; 

	 /**
	  * Error code 20101:
	  * The &lt;sbml&gt; container element must declare the XML Namespace for SBML, and this 
	  * declaration must be consistent with the values of the 'level' and 'version' 
	  * attributes on the &lt;sbml&gt; element. Reference: L3V1 Section 4.1 
	  */
 	 public static final int CORE_20101 = 20101; 

	 /**
	  * Error code 20102:
	  * The &lt;sbml&gt; container element must declare the SBML Level using the attribute 
	  * 'level', and this declaration must be consistent with the XML Namespace declared 
	  * for the &lt;sbml&gt; element. Reference: L3V1 Section 4.1 
	  */
 	 public static final int CORE_20102 = 20102; 

	 /**
	  * Error code 20103:
	  * The &lt;sbml&gt; container element must declare the SBML Version using the attribute 
	  * 'version', and this declaration must be consistent with the XML Namespace 
	  * declared for the &lt;sbml&gt; element. Reference: L3V1 Section 4.1 
	  */
 	 public static final int CORE_20103 = 20103; 

	 /**
	  * Error code 20104:
	  * The &lt;sbml&gt; container element must declare the XML Namespace for any SBML Level 3 
	  * packages used within the SBML document. This declaration must be consistent with 
	  * the values of the 'level' and 'version' attributes on the &lt;sbml&gt; element. 
	  * Reference: L3V1 Section 4.1.2 
	  */
 	 public static final int CORE_20104 = 20104; 

	 /**
	  * Error code 20105:
	  * The attribute 'level' on the &lt;sbml&gt; container element must have a value of type 
	  * 'positiveInteger'. Reference: L3V1 Section 3.1.4 
	  */
 	 public static final int CORE_20105 = 20105; 

	 /**
	  * Error code 20106:
	  * The attribute 'version' on the &lt;sbml&gt; container element must have a value of 
	  * type 'positiveInteger'. Reference: L3V1 Section 3.1.4 
	  */
 	 public static final int CORE_20106 = 20106; 

	 /**
	  * Error code 20108:
	  * The &lt;sbml&gt; object may only have the optional attributes 'metaid' and 'sboTerm'. 
	  * Reference: L3V1 Section 4.1 
	  */
 	 public static final int CORE_20108 = 20108; 

	 /**
	  * Error code 20109:
	  * It is not possible to use SBML L3 packages with earlier levels of SBML. 
	  */
 	 public static final int CORE_20109 = 20109; 

	 /**
	  * Error code 20201:
	  * An SBML document must contain a &lt;model&gt; element. The &lt;model&gt; element is optional 
	  * in L3V2 and beyond. Reference: L3V1 Section 4.1 
	  */
 	 public static final int CORE_20201 = 20201; 

	 /**
	  * Error code 20202:
	  * The order of subelements within a &lt;model&gt; element must be the following (where 
	  * any one may be optional, but the ordering must be maintained): 
	  * &lt;listOfFunctionDefinitions&gt;, &lt;listOfUnitDefinitions&gt;, &lt;listOfCompartmentTypes&gt;, 
	  * &lt;listOfSpeciesTypes&gt;, &lt;listOfCompartments&gt;, &lt;listOfSpecies&gt;, &lt;listOfParameters&gt;, 
	  * &lt;listOfInitialAssignments&gt;, &lt;listOfRules&gt;, &lt;listOfConstraints&gt;, 
	  * &lt;listOfReactions&gt; and &lt;listOfEvents&gt;. 
	  */
 	 public static final int CORE_20202 = 20202; 

	 /**
	  * Error code 20203:
	  * The &lt;listOf___&gt; containers in a &lt;model&gt; are optional, but if present, the lists 
	  * cannot be empty. Specifically, if any of the following are present in a &lt;model&gt;, 
	  * they must not be empty: &lt;listOfFunctionDefinitions&gt;, &lt;listOfUnitDefinitions&gt;, 
	  * &lt;listOfCompartmentTypes&gt;, &lt;listOfSpeciesTypes&gt;, &lt;listOfCompartments&gt;, 
	  * &lt;listOfSpecies&gt;, &lt;listOfParameters&gt;, &lt;listOfInitialAssignments&gt;, &lt;listOfRules&gt;, 
	  * &lt;listOfConstraints&gt;, &lt;listOfReactions&gt; and &lt;listOfEvents&gt;. Reference: L3V1 
	  * Section 4.2 
	  */
 	 public static final int CORE_20203 = 20203; 

	 /**
	  * Error code 20204:
	  * If a model defines any species, then the model must also define at least one 
	  * compartment. This is an implication of the fact that the 'compartment' attribute 
	  * on the &lt;species&gt; element is not optional. Reference: L3V1 Section 4.6.3 
	  */
 	 public static final int CORE_20204 = 20204; 

	 /**
	  * Error code 20205:
	  * There may be at most one instance of each of the following kind of element in a 
	  * &lt;model&gt; object: ListOfFunctionDefinitions, ListOfUnitDefinitions, 
	  * ListOfCompartments, ListOfSpecies, ListOfParameters, ListOfInitialAssignments, 
	  * ListOfRules, ListOfConstraints, ListOfReactions and ListOfEvents. Reference: 
	  * L3V1 Section 4.2 
	  */
 	 public static final int CORE_20205 = 20205; 

	 /**
	  * Error code 20206:
	  * Apart from the general Notes and Annotation subobjects permitted on all SBML 
	  * components, a ListOfFunctionDefinitions container object may only contain 
	  * FunctionDefinition objects. Reference: L3V1 Section 4.2.8 
	  */
 	 public static final int CORE_20206 = 20206; 

	 /**
	  * Error code 20207:
	  * Apart from the general Notes and Annotation subobjects permitted on all SBML 
	  * components, a ListOfUnitDefinitions container object may only contain 
	  * UnitDefinition objects. Reference: L3V1 Section 4.2.8 
	  */
 	 public static final int CORE_20207 = 20207; 

	 /**
	  * Error code 20208:
	  * Apart from the general Notes and Annotation subobjects permitted on all SBML 
	  * components, a ListOfCompartments container object may only contain Compartment 
	  * objects. Reference: L3V1 Section 4.2.8 
	  */
 	 public static final int CORE_20208 = 20208; 

	 /**
	  * Error code 20209:
	  * Apart from the general Notes and Annotation subobjects permitted on all SBML 
	  * components, a ListOfSpecies container object may only contain Species objects. 
	  * Reference: L3V1 Section 4.2.8 
	  */
 	 public static final int CORE_20209 = 20209; 

	 /**
	  * Error code 20210:
	  * Apart from the general Notes and Annotation subobjects permitted on all SBML 
	  * components, a ListOfParameters container object may only contain Parameter 
	  * objects. Reference: L3V1 Section 4.2.8 
	  */
 	 public static final int CORE_20210 = 20210; 

	 /**
	  * Error code 20211:
	  * Apart from the general Notes and Annotation subobjects permitted on all SBML 
	  * components, a ListOfInitialAssignments container object may only contain 
	  * InitialAssignment objects. Reference: L3V1 Section 4.2.8 
	  */
 	 public static final int CORE_20211 = 20211; 

	 /**
	  * Error code 20212:
	  * Apart from the general Notes and Annotation subobjects permitted on all SBML 
	  * components, a ListOfRules container object may only contain Rule objects. 
	  * Reference: L3V1 Section 4.2.8 
	  */
 	 public static final int CORE_20212 = 20212; 

	 /**
	  * Error code 20213:
	  * Apart from the general Notes and Annotation subobjects permitted on all SBML 
	  * components, a ListOfConstraints container object may only contain Constraint 
	  * objects. Reference: L3V1 Section 4.2.8 
	  */
 	 public static final int CORE_20213 = 20213; 

	 /**
	  * Error code 20214:
	  * Apart from the general Notes and Annotation subobjects permitted on all SBML 
	  * components, a ListOfReactions container object may only contain Reaction 
	  * objects. Reference: L3V1 Section 4.2.8 
	  */
 	 public static final int CORE_20214 = 20214; 

	 /**
	  * Error code 20215:
	  * Apart from the general Notes and Annotation subobjects permitted on all SBML 
	  * components, a ListOfEvents container object may only contain Event objects. 
	  * Reference: L3V1 Section 4.2.8 
	  */
 	 public static final int CORE_20215 = 20215; 

	 /**
	  * Error code 20216:
	  * The value of the attribute 'conversionFactor' on a Model object must be the 
	  * identifier of an existing Parameter object defined in the Model object's 
	  * ListOfParameters. Reference: L3V1 Section 4.2 
	  */
 	 public static final int CORE_20216 = 20216; 

	 /**
	  * Error code 20217:
	  * The value of the attribute 'timeUnits' on a Model object should be either the 
	  * units 'second', 'dimensionless', or the identifier of a UnitDefinition object 
	  * based on these units. Reference: L3V1 Section 4.2.4 
	  */
 	 public static final int CORE_20217 = 20217; 

	 /**
	  * Error code 20218:
	  * The value of the attribute 'volumeUnits' on a Model object should be either the 
	  * units 'litre', 'dimensionless', or the identifier of a UnitDefinition object 
	  * based on these units or a unit derived from 'metre'. Reference: L3V1 Section 
	  * 4.2.5 
	  */
 	 public static final int CORE_20218 = 20218; 

	 /**
	  * Error code 20219:
	  * The value of the attribute 'areaUnits' on a Model object should be either 
	  * 'dimensionless' or the identifier of a UnitDefinition object based on 
	  * 'dimensionless' or a unit derived from 'metre'. Reference: L3V1 Section 4.2.5 
	  */
 	 public static final int CORE_20219 = 20219; 

	 /**
	  * Error code 20220:
	  * The value of the attribute 'lengthUnits' on a Model object should be either the 
	  * units 'metre', 'dimensionless', or the identifier of a UnitDefinition object 
	  * based on these units. Reference: L3V1 Section 4.2.5 
	  */
 	 public static final int CORE_20220 = 20220; 

	 /**
	  * Error code 20221:
	  * The value of the attribute extentUnits on a Model object should be either the 
	  * units 'mole', 'item', 'avogadro', 'dimensionless', 'kilogram', 'gram', or the 
	  * identifier of a UnitDefinition object based on these units. Reference: L3V1 
	  * Section 4.2.6 
	  */
 	 public static final int CORE_20221 = 20221; 

	 /**
	  * Error code 20222:
	  * A Model object may only have the following attributes, all of which are 
	  * optional: 'metaid', 'sboTerm', 'id', 'name', 'substanceUnits', 'timeUnits', 
	  * 'volumeUnits', 'areaUnits', 'lengthUnits', 'extentUnits' and 'conversionFactor'. 
	  * No other attributes from the SBML Level 3 Core namespace are permitted on a 
	  * Model object. Reference: L3V1 Section 4.2 
	  */
 	 public static final int CORE_20222 = 20222; 

	 /**
	  * Error code 20223:
	  * A ListOfFunctionDefinitions object may have the optional attributes 'metaid' and 
	  * 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are 
	  * permitted on a ListOfFunctionDefinitions object. Reference: L3V1 Section 4.2.8 
	  */
 	 public static final int CORE_20223 = 20223; 

	 /**
	  * Error code 20224:
	  * A ListOfUnitDefinitions object may have the optional attributes 'metaid' and 
	  * 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are 
	  * permitted on a ListOfUnitDefinitions object. Reference: L3V1 Section 4.2.8 
	  */
 	 public static final int CORE_20224 = 20224; 

	 /**
	  * Error code 20225:
	  * A ListOfCompartments object may have the optional attributes 'metaid' and 
	  * 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are 
	  * permitted on a ListOfCompartments object. Reference: L3V1 Section 4.2.8 
	  */
 	 public static final int CORE_20225 = 20225; 

	 /**
	  * Error code 20226:
	  * A ListOfSpecies object may have the optional attributes 'metaid' and 'sboTerm'. 
	  * No other attributes from the SBML Level 3 Core namespace are permitted on a 
	  * ListOfSpecies object. Reference: L3V1 Section 4.2.8 
	  */
 	 public static final int CORE_20226 = 20226; 

	 /**
	  * Error code 20227:
	  * A ListOfParameters object may have the optional attributes 'metaid' and 
	  * 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are 
	  * permitted on a ListOfParameters object. Reference: L3V1 Section 4.2.8 
	  */
 	 public static final int CORE_20227 = 20227; 

	 /**
	  * Error code 20228:
	  * A ListOfInitialAssignments object may have the optional attributes 'metaid' and 
	  * 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are 
	  * permitted on a ListOfInitialAssignments object. Reference: L3V1 Section 4.2.8 
	  */
 	 public static final int CORE_20228 = 20228; 

	 /**
	  * Error code 20229:
	  * A ListOfRules object may have the optional attributes 'metaid' and 'sboTerm'. No 
	  * other attributes from the SBML Level 3 Core namespace are permitted on a 
	  * ListOfRules object. Reference: L3V1 Section 4.2.8 
	  */
 	 public static final int CORE_20229 = 20229; 

	 /**
	  * Error code 20230:
	  * A ListOfConstraints object may have the optional attributes 'metaid' and 
	  * 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are 
	  * permitted on a ListOfConstraints object. Reference: L3V1 Section 4.2.8 
	  */
 	 public static final int CORE_20230 = 20230; 

	 /**
	  * Error code 20231:
	  * A ListOfReactions object may have the optional attributes 'metaid' and 
	  * 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are 
	  * permitted on a ListOfReactions object. Reference: L3V1 Section 4.2.8 
	  */
 	 public static final int CORE_20231 = 20231; 

	 /**
	  * Error code 20232:
	  * A ListOfEvents object may have the optional attributes 'metaid' and 'sboTerm'. 
	  * No other attributes from the SBML Level 3 Core namespace are permitted on a 
	  * ListOfEvents object. Reference: L3V1 Section 4.2.8 
	  */
 	 public static final int CORE_20232 = 20232; 

	 /**
	  * Error code 20233:
	  * [Although SBML Level 3 Version 1 does not explicitly define the following as an 
	  * error, other Levels and/or Versions of SBML do.] The value of the attribute 
	  * substanceUnits on a Model object should be either the units 'mole', 'item', 
	  * 'avogadro', 'dimensionless', 'kilogram', 'gram', or the identifier of a 
	  * UnitDefinition object based on these units. 
	  */
 	 public static final int CORE_20233 = 20233; 

	 /**
	  * Error code 20301:
	  * The top-level element within &lt;math&gt; in a &lt;functionDefinition&gt; is restricted. 
	  * Reference: L3V1 Section 4.3.2 
	  */
 	 public static final int CORE_20301 = 20301; 

	 /**
	  * Error code 20302:
	  * Inside the &lt;lambda&gt; of a &lt;functionDefinition&gt;, if a &lt;ci&gt; element is the first 
	  * element within a MathML &lt;apply&gt;, then the &lt;ci&gt;'s value can only be chosen from 
	  * the set of identifiers of other SBML &lt;functionDefinition&gt;s defined prior to that 
	  * point in the SBML model. In other words, forward references to user-defined 
	  * functions are not permitted. 
	  */
 	 public static final int CORE_20302 = 20302; 

	 /**
	  * Error code 20303:
	  * Inside the &lt;lambda&gt; of a &lt;functionDefinition&gt;, the identifier of that 
	  * &lt;functionDefinition&gt; cannot appear as the value of a &lt;ci&gt; element. SBML 
	  * functions are not permitted to be recursive. Reference: L3V1 Sections 3.4.3 and 
	  * 4.3.2 
	  */
 	 public static final int CORE_20303 = 20303; 

	 /**
	  * Error code 20304:
	  * Inside the &lt;lambda&gt; of a &lt;functionDefinition&gt;, if a &lt;ci&gt; element is not the 
	  * first element within a MathML &lt;apply&gt;, then the &lt;ci&gt;'s value can only be the 
	  * value of a &lt;bvar&gt; element declared in that &lt;lambda&gt;. In other words, all model 
	  * entities referenced inside a function definition must be passed arguments to 
	  * that function. Reference: L3V1 Sections 3.4.3 and 4.3.2 
	  */
 	 public static final int CORE_20304 = 20304; 

	 /**
	  * Error code 20305:
	  * The value type returned by a &lt;functionDefinition&gt;'s &lt;lambda&gt; must be either 
	  * Boolean or numeric. Reference: L3V1 Section 3.4.9 
	  */
 	 public static final int CORE_20305 = 20305; 

	 /**
	  * Error code 20306:
	  * A FunctionDefinition object must contain exactly one MathML math element. The 
	  * &lt;math&gt; element is optional in L3V2 and beyond. Reference: L3V1 Section 4.3 
	  */
 	 public static final int CORE_20306 = 20306; 

	 /**
	  * Error code 20307:
	  * FunctionDefinition object must have the required attribute 'id', and may have 
	  * the optional attributes 'metaid', 'sboTerm' and 'name'. No other attributes from 
	  * the SBML Level 3 Core namespace are permitted on a FunctionDefinition object. 
	  * Reference: L3V1 Section 4.3 
	  */
 	 public static final int CORE_20307 = 20307; 

	 /**
	  * Error code 20401:
	  * The value of the 'id' attribute in a &lt;unitDefinition&gt; must be of type 'UnitSId' 
	  * and not be identical to any unit predefined in SBML. Reference: L3V1 Section 
	  * 4.4.2 
	  */
 	 public static final int CORE_20401 = 20401; 

	 /**
	  * Error code 20402:
	  * Redefinitions of the built-in unit 'substance' are restricted. 
	  */
 	 public static final int CORE_20402 = 20402; 

	 /**
	  * Error code 20403:
	  * Redefinitions of the built-in unit 'length' are restricted. 
	  */
 	 public static final int CORE_20403 = 20403; 

	 /**
	  * Error code 20404:
	  * Redefinitions of the built-in unit 'area' are restricted. 
	  */
 	 public static final int CORE_20404 = 20404; 

	 /**
	  * Error code 20405:
	  * Redefinitions of the built-in unit 'time' are restricted. 
	  */
 	 public static final int CORE_20405 = 20405; 

	 /**
	  * Error code 20406:
	  * Redefinitions of the built-in unit 'volume' are restricted. 
	  */
 	 public static final int CORE_20406 = 20406; 

	 /**
	  * Error code 20407:
	  * If a &lt;unitDefinition&gt; for 'volume' simplifies to a &lt;unit&gt; in which the 'kind' 
	  * attribute value is 'litre', then its 'exponent' attribute value must be '1'. 
	  */
 	 public static final int CORE_20407 = 20407; 

	 /**
	  * Error code 20408:
	  * If a &lt;unitDefinition&gt; for 'volume' simplifies to a &lt;unit&gt; in which the 'kind' 
	  * attribute value is 'metre', then its 'exponent' attribute value must be '3'. 
	  */
 	 public static final int CORE_20408 = 20408; 

	 /**
	  * Error code 20409:
	  * The &lt;listOfUnits&gt; container in a &lt;unitDefinition&gt; cannot be empty. 
	  */
 	 public static final int CORE_20409 = 20409; 

	 /**
	  * Error code 20410:
	  * The value of the 'kind' attribute of a &lt;unit&gt; can only be one of the base units 
	  * enumerated by 'UnitKind'; that is, the SBML unit system is not hierarchical and 
	  * user-defined units cannot be defined using other user-defined units. Reference: 
	  * L3V1 Section 4.4.2 
	  */
 	 public static final int CORE_20410 = 20410; 

	 /**
	  * Error code 20411:
	  * The 'offset' attribute on &lt;unit&gt; previously available in SBML Level 2 Version 1, 
	  * has been removed as of SBML Level 2 Version 2. 
	  */
 	 public static final int CORE_20411 = 20411; 

	 /**
	  * Error code 20412:
	  * The predefined unit 'Celsius', previously available in SBML Level 1 and Level 2 
	  * Version 1, has been removed as of SBML Level 2 Version 2. 
	  */
 	 public static final int CORE_20412 = 20412; 

	 /**
	  * Error code 20413:
	  * The ListOfUnits container object in a UnitDefinition object is optional, but if 
	  * present, it must not be empty. Reference: L3V1 Section 4.4 
	  */
 	 public static final int CORE_20413 = 20413; 

	 /**
	  * Error code 20414:
	  * There may be at most one ListOfUnits container objects in a UnitDefinition 
	  * object. Reference: L3V1 Section 4.4 
	  */
 	 public static final int CORE_20414 = 20414; 

	 /**
	  * Error code 20415:
	  * Apart from the general Notes and Annotation subobjects permitted on all SBML 
	  * components, a ListOfUnits container object may only contain Unit objects. 
	  * Reference: L3V1 Section 4.2.8 
	  */
 	 public static final int CORE_20415 = 20415; 

	 /**
	  * Error code 20419:
	  * A UnitDefinition object must have the required attribute 'id' and may have the 
	  * optional attributes 'metaid', 'sboTerm' and 'name'. No other attributes from the 
	  * SBML Level 3 Core namespace are permitted on a UnitDefinition object. Reference: 
	  * L3V1 Section 4.4 
	  */
 	 public static final int CORE_20419 = 20419; 

	 /**
	  * Error code 20420:
	  * A ListOfUnits object may have the optional attributes 'metaid' and 'sboTerm'. No 
	  * other attributes from the SBML Level 3 Core namespace are permitted on a 
	  * ListOfUnits object. Reference: L3V1 Section 4.2.8 
	  */
 	 public static final int CORE_20420 = 20420; 

	 /**
	  * Error code 20421:
	  * A Unit object must have the required attributes 'kind', 'exponent', 'scale' and 
	  * 'multiplier', and may have the optional attributes 'metaid' and 'sboTerm'. No 
	  * other attributes from the SBML Level 3 Core namespace are permitted on a Unit 
	  * object. Reference: L3V1 Section 4.4 
	  */
 	 public static final int CORE_20421 = 20421; 

	 /**
	  * Error code 20501:
	  * The size of a &lt;compartment&gt; must not be set if the compartment's 
	  * 'spatialDimensions' attribute has value '0'. 
	  */
 	 public static final int CORE_20501 = 20501; 

	 /**
	  * Error code 20502:
	  * If a &lt;compartment&gt; definition has a 'spatialDimensions' value of '0', then its 
	  * 'units' attribute must not be set. If the compartment has no dimensions, then no 
	  * units can be associated with a non-existent size. 
	  */
 	 public static final int CORE_20502 = 20502; 

	 /**
	  * Error code 20503:
	  * If a &lt;compartment&gt; definition has a 'spatialDimensions' value of '0', then its 
	  * 'constant' attribute value must either default to or be set to 'true'. If the 
	  * compartment has no dimensions, then its size can never change. 
	  */
 	 public static final int CORE_20503 = 20503; 

	 /**
	  * Error code 20504:
	  * The 'outside' attribute value of a &lt;compartment&gt; must be the identifier of 
	  * another &lt;compartment&gt; defined in the model. 
	  */
 	 public static final int CORE_20504 = 20504; 

	 /**
	  * Error code 20505:
	  * A &lt;compartment&gt; may not enclose itself through a chain of references involving 
	  * the 'outside' field. This means that a compartment cannot have its own 
	  * identifier as the value of 'outside', nor can it point to another compartment 
	  * whose 'outside' field points directly or indirectly to the compartment. 
	  */
 	 public static final int CORE_20505 = 20505; 

	 /**
	  * Error code 20506:
	  * The 'outside' attribute value of a &lt;compartment&gt; cannot be a compartment whose 
	  * 'spatialDimensions' value is '0', unless both compartments have 
	  * 'spatialDimensions'='0'. Simply put, a zero-dimensional compartment cannot 
	  * enclose compartments that have anything other than zero dimensions themselves. 
	  */
 	 public static final int CORE_20506 = 20506; 

	 /**
	  * Error code 20507:
	  * The value of the 'units' attribute on a &lt;compartment&gt; having 'spatialDimensions' 
	  * of '1' is restricted. Reference: L3V1 Section 4.5.4 
	  */
 	 public static final int CORE_20507 = 20507; 

	 /**
	  * Error code 20508:
	  * The value of the 'units' attribute on a &lt;compartment&gt; having 'spatialDimensions' 
	  * of '2' is restricted. Reference: L3V1 Section 4.5.4 
	  */
 	 public static final int CORE_20508 = 20508; 

	 /**
	  * Error code 20509:
	  * The value of the 'units' attribute on a &lt;compartment&gt; having 'spatialDimensions' 
	  * of '3' is restricted. Reference: L3V1 Section 4.5.4 
	  */
 	 public static final int CORE_20509 = 20509; 

	 /**
	  * Error code 20510:
	  * If the 'compartmentType' attribute is given a value in a &lt;compartment&gt; 
	  * definition, it must contain the identifier of an existing &lt;compartmentType&gt;. 
	  */
 	 public static final int CORE_20510 = 20510; 

	 /**
	  * Error code 20511:
	  * If the attribute 'units' on a Compartment object having a 'spatialDimensions' 
	  * attribute value of '1' has not been set, then the unit of measurement associated 
	  * with the compartment's size is determined by the value of the enclosing Model 
	  * object's 'lengthUnits' attribute. If neither the Compartment object's 'units' 
	  * nor the enclosing Model object's 'lengthUnits' attributes are set, the unit of 
	  * compartment size is undefined. Reference: L3V1 Section 4.5.4 
	  */
 	 public static final int CORE_20511 = 20511; 

	 /**
	  * Error code 20512:
	  * If the attribute 'units' on a Compartment object having a 'spatialDimensions' 
	  * attribute value of '2' has not been set, then the unit of measurement associated 
	  * with the compartment's size is determined by the value of the enclosing Model 
	  * object's 'areaUnits' attribute. If neither the Compartment object's 'units' nor 
	  * the enclosing Model object's 'areaUnits' attributes are set, the unit of 
	  * compartment size is undefined. Reference: L3V1 Section 4.5.4 
	  */
 	 public static final int CORE_20512 = 20512; 

	 /**
	  * Error code 20513:
	  * If the attribute 'units' on a Compartment object having a 'spatialDimensions' 
	  * attribute value of '3' has not been set, then the unit of measurement associated 
	  * with the compartment's size is determined by the value of the enclosing Model 
	  * object's 'volumeUnits' attribute. If neither the Compartment object's 'units' 
	  * nor the enclosing Model object's 'volumeUnits' attributes are set, the unit of 
	  * compartment size is undefined. Reference: L3V1 Section 4.5.4 
	  */
 	 public static final int CORE_20513 = 20513; 

	 /**
	  * Error code 20517:
	  * A Compartment object must have the required attributes 'id' and 'constant', and 
	  * may have the optional attributes 'metaid', 'sboTerm', 'name', 
	  * 'spatialDimensions', 'size' and 'units'. No other attributes from the SBML Level 
	  * 3 Core namespace are permitted on a Compartment object. Reference: L3V1 Section 
	  * 4.5 
	  */
 	 public static final int CORE_20517 = 20517; 

	 /**
	  * Error code 20518:
	  * If neither the attribute 'units' nor the attribute 'spatialDimensions' on a 
	  * Compartment object is set, the unit associated with that compartment's size is 
	  * undefined. Reference: L3V1 Section 4.5 
	  */
 	 public static final int CORE_20518 = 20518; 

	 /**
	  * Error code 20601:
	  * The value of 'compartment' in a &lt;species&gt; definition must be the identifier of 
	  * an existing &lt;compartment&gt; defined in the model. Reference: L3V1 Section 4.6.3 
	  */
 	 public static final int CORE_20601 = 20601; 

	 /**
	  * Error code 20602:
	  * If a &lt;species&gt; definition sets 'hasOnlySubstanceUnits' to 'true', then it must 
	  * not have a value for 'spatialSizeUnits'. 
	  */
 	 public static final int CORE_20602 = 20602; 

	 /**
	  * Error code 20603:
	  * A &lt;species&gt; definition must not set 'spatialSizeUnits' if the &lt;compartment&gt; in 
	  * which it is located has a 'spatialDimensions' value of '0'. 
	  */
 	 public static final int CORE_20603 = 20603; 

	 /**
	  * Error code 20604:
	  * If a &lt;species&gt; located in a &lt;compartment&gt; whose 'spatialDimensions' is set to 
	  * '0', then that &lt;species&gt; definition cannot set 'initialConcentration'. 
	  */
 	 public static final int CORE_20604 = 20604; 

	 /**
	  * Error code 20605:
	  * If a &lt;species&gt; is located in a &lt;compartment&gt; whose 'spatialDimensions' has value 
	  * '1', then that &lt;species&gt; definition can only set 'spatialSizeUnits' to a value 
	  * of 'length', 'metre', 'dimensionless', or the identifier of a &lt;unitDefinition&gt; 
	  * derived from 'metre' (with an 'exponent' value of '1') or 'dimensionless'. 
	  */
 	 public static final int CORE_20605 = 20605; 

	 /**
	  * Error code 20606:
	  * If a &lt;species&gt; is located in a &lt;compartment&gt; whose 'spatialDimensions' has value 
	  * '2', then that &lt;species&gt; definition can only set 'spatialSizeUnits' to a value 
	  * of 'area', 'dimensionless', or the identifier of a &lt;unitDefinition&gt; derived from 
	  * either 'metre' (with an 'exponent' value of '2') or 'dimensionless'. 
	  */
 	 public static final int CORE_20606 = 20606; 

	 /**
	  * Error code 20607:
	  * If a &lt;species&gt; is located in a &lt;compartment&gt; whose 'spatialDimensions' has value 
	  * '3', then that &lt;species&gt; definition can only set 'spatialSizeUnits' to a value 
	  * of 'volume', 'litre', 'dimensionless', or the identifier of a &lt;unitDefinition&gt; 
	  * derived from either 'litre', 'metre' (with an 'exponent' value of '3') or 
	  * 'dimensionless'. 
	  */
 	 public static final int CORE_20607 = 20607; 

	 /**
	  * Error code 20608:
	  * The value of a &lt;species&gt;'s 'units' attribute is restricted. Reference: L3V1 
	  * Section 4.6.4 
	  */
 	 public static final int CORE_20608 = 20608; 

	 /**
	  * Error code 20609:
	  * A &lt;species&gt; cannot set values for both 'initialConcentration' and 
	  * 'initialAmount' because they are mutually exclusive. Reference: L3V1 Section 
	  * 4.6.4 
	  */
 	 public static final int CORE_20609 = 20609; 

	 /**
	  * Error code 20610:
	  * A &lt;species&gt;'s quantity cannot be determined simultaneously by both reactions and 
	  * rules. More formally, if the identifier of a &lt;species&gt; definition having 
	  * 'boundaryCondition'='false' and 'constant'='false' is referenced by a 
	  * &lt;speciesReference&gt; anywhere in a model, then this identifier cannot also appear 
	  * as the value of a 'variable' in an &lt;assignmentRule&gt; or a &lt;rateRule&gt;. Reference: 
	  * L3V1 Section 4.6.6 
	  */
 	 public static final int CORE_20610 = 20610; 

	 /**
	  * Error code 20611:
	  * A &lt;species&gt; having boundaryCondition='false' cannot appear as a reactant or 
	  * product in any reaction if that Species also has constant='true'. Reference: 
	  * L3V1 Section 4.6.6 
	  */
 	 public static final int CORE_20611 = 20611; 

	 /**
	  * Error code 20612:
	  * The value of 'speciesType' in a &lt;species&gt; definition must be the identifier of 
	  * an existing &lt;speciesType&gt;. 
	  */
 	 public static final int CORE_20612 = 20612; 

	 /**
	  * Error code 20613:
	  * There cannot be more than one species of a given &lt;speciesType&gt; in the same 
	  * compartment of a model. More formally, for any given compartment, there cannot 
	  * be more than one &lt;species&gt; definition in which both of the following hold 
	  * simultaneously: (i) the &lt;species&gt;' 'compartment' value is set to that 
	  * compartment's identifier and (ii) the &lt;species&gt;' 'speciesType' is set the same 
	  * value as the 'speciesType' of another &lt;species&gt; that also sets its 'compartment' 
	  * to that compartment identifier. 
	  */
 	 public static final int CORE_20613 = 20613; 

	 /**
	  * Error code 20614:
	  * The 'compartment' attribute in a &lt;species&gt; is mandatory. A &lt;species&gt; definition 
	  * in a model must include a value for this attribute. Reference: L3V1 Section 
	  * 4.6.3 
	  */
 	 public static final int CORE_20614 = 20614; 

	 /**
	  * Error code 20615:
	  * The 'spatialSizeUnits' attribute on &lt;species&gt;, previously available in SBML 
	  * Level 2 versions prior to Version 3, has been removed as of SBML Level 2 Version 
	  * 3. 
	  */
 	 public static final int CORE_20615 = 20615; 

	 /**
	  * Error code 20616:
	  * If the attribute 'substanceUnits' in a Species object has not been set, then the 
	  * unit of measurement associated with the species' quantity is determined by the 
	  * value of the enclosing Model object's 'substanceUnits' attribute. If neither the 
	  * Species object's 'substanceUnits' attribute nor the enclosing Model object's 
	  * 'substanceUnits' attribute are set, then the unit of that species' quantity is 
	  * undefined. Reference: L3V1 Section 4.6.5 
	  */
 	 public static final int CORE_20616 = 20616; 

	 /**
	  * Error code 20617:
	  * The value of the attribute 'conversionFactor' on a Species object must be the 
	  * identifier of an existing Parameter object defined in the enclosing Model 
	  * object. Reference: L3V1 Section 4.6.7 
	  */
 	 public static final int CORE_20617 = 20617; 

	 /**
	  * Error code 20623:
	  * A Species object must have the required attributes 'id', 'compartment', 
	  * 'hasOnlySubstanceUnits', 'boundaryCondition' and 'constant', and may have the 
	  * optional attributes 'metaid', 'sboTerm', 'name', 'initialAmount', 
	  * 'initialConcentration', 'substanceUnits' and 'conversionFactor'. No other 
	  * attributes from the SBML Level 3 Core namespace are permitted on a Species 
	  * object. Reference: L3V1 Section 4.6 
	  */
 	 public static final int CORE_20623 = 20623; 

	 /**
	  * Error code 20701:
	  * The 'units' in a &lt;Parameter&gt; definition must be a value chosen from among the 
	  * following: a predefined unit (e.g., 'substance', 'time', etc.), the identifier 
	  * of a &lt;UnitDefinition&gt; in the model, or one of the base units in SBML (e.g., 
	  * 'litre', 'mole', 'metre', etc.) Reference: L3V1 Section 4.7.3 
	  */
 	 public static final int CORE_20701 = 20701; 

	 /**
	  * Error code 20702:
	  * If the attribute 'units' on a given Parameter object has not been set, then the 
	  * unit of measurement associated with that parameter's value is undefined. 
	  * Reference: L3V1 Section 4.7.3 
	  */
 	 public static final int CORE_20702 = 20702; 

	 /**
	  * Error code 20705:
	  * A Parameter object referenced by the attribute 'conversionFactor' on a Species 
	  * or Model object must have a value of 'true' for its attribute 'constant'. 
	  * Reference: L3V1 Section 4.6.7 
	  */
 	 public static final int CORE_20705 = 20705; 

	 /**
	  * Error code 20706:
	  * A Parameter object must have the required attributes 'id' and 'constant', and 
	  * may have the optional attributes 'metaid', 'sboTerm', 'name', 'value' and 
	  * 'units'. No other attributes from the SBML Level 3 Core namespace are permitted 
	  * on a Parameter object. Reference: L3V1 Section 4.7 
	  */
 	 public static final int CORE_20706 = 20706; 

	 /**
	  * Error code 20801:
	  * The value of 'symbol' in an &lt;initialAssignment&gt; definition must be the 
	  * identifier of an existing &lt;compartment&gt;, &lt;species&gt;, or &lt;parameter&gt; defined in 
	  * the model or &lt;speciesReference&gt; in Level 3, or in Level 3 Version 2, any 
	  * identifier in the SId namespace of the model belonging to an element defined by 
	  * an SBML Level 3 package as having with mathematical meaning. Reference: L3V1 
	  * Section 4.8 
	  */
 	 public static final int CORE_20801 = 20801; 

	 /**
	  * Error code 20802:
	  * A given identifier cannot appear as the value of more than one 'symbol' field 
	  * across the set of &lt;initialAssignment&gt;s in a model. Reference: L3V1 Section 4.8 
	  */
 	 public static final int CORE_20802 = 20802; 

	 /**
	  * Error code 20803:
	  * The value of a 'symbol' field in any &lt;initialAssignment&gt; definition cannot also 
	  * appear as the value of a 'variable' field in an &lt;assignmentRule&gt;. Reference: 
	  * L3V1 Section 4.8 
	  */
 	 public static final int CORE_20803 = 20803; 

	 /**
	  * Error code 20804:
	  * An InitialAssignment object must contain exactly one MathML &lt;math&gt; element. The 
	  * &lt;math&gt; element is optional in L3V2 and beyond. Reference: L3V1 Section 4.8 
	  */
 	 public static final int CORE_20804 = 20804; 

	 /**
	  * Error code 20805:
	  * An InitialAssignment object must have the required attribute 'symbol' and may 
	  * have the optional attributes 'metaid' and 'sboTerm'. No other attributes from 
	  * the SBML Level 3 Core namespace are permitted on an InitialAssignment object. 
	  * Reference: L3V1 Section 4.8 
	  */
 	 public static final int CORE_20805 = 20805; 

	 /**
	  * Error code 20806:
	  * The identifier given as the value of a 'symbol' attribute in any 
	  * &lt;initialAssignment&gt; definition cannot be the identifier of a &lt;compartment&gt; with 
	  * a 'spatialDimensions' value of 0. 
	  */
 	 public static final int CORE_20806 = 20806; 

	 /**
	  * Error code 20901:
	  * The value of an &lt;assignmentRule&gt;'s 'variable' must be the identifier of an 
	  * existing &lt;compartment&gt;, &lt;species&gt;, globally-defined &lt;parameter&gt;, or (in Level 3) 
	  * &lt;speciesReference&gt;, or (in Level 3 Version 2), any identifier in the SId 
	  * namespace of the model belonging to an element defined by an SBML Level 3 
	  * package as having with mathematical meaning. Reference: L3V1 Section 4.9.3 
	  */
 	 public static final int CORE_20901 = 20901; 

	 /**
	  * Error code 20902:
	  * The value of a &lt;rateRule&gt;'s 'variable' must be the identifier of an existing 
	  * &lt;compartment&gt;, &lt;species&gt;, globally-defined &lt;parameter&gt;, or (in Level 3) 
	  * &lt;speciesReference&gt;, or (in Level 3 Version 2), any identifier in the SId 
	  * namespace of the model belonging to an element defined by an SBML Level 3 
	  * package as having with mathematical meaning. Reference: L3V1 Section 4.9.4 
	  */
 	 public static final int CORE_20902 = 20902; 

	 /**
	  * Error code 20903:
	  * Any &lt;compartment&gt;, &lt;species&gt;, &lt;parameter&gt;, or (in Level 3) &lt;speciesReference&gt; 
	  * whose identifier is the value of a 'variable' attribute in an &lt;assignmentRule&gt;, 
	  * must have a value of 'false' for 'constant'. Reference: L3V1 Section 4.9.3 
	  */
 	 public static final int CORE_20903 = 20903; 

	 /**
	  * Error code 20904:
	  * Any &lt;compartment&gt;, &lt;species&gt;, &lt;parameter&gt;, or (in Level 3) &lt;speciesReference&gt; 
	  * whose identifier is the value of a 'variable' attribute in an &lt;rateRule&gt;, must 
	  * have a value of 'false' for 'constant'. Reference: L3V1 Section 4.9.4 
	  */
 	 public static final int CORE_20904 = 20904; 

	 /**
	  * Error code 20905:
	  */
 	 public static final int CORE_20905 = 20905; 

	 /**
	  * Error code 20906:
	  * There must not be circular dependencies in the combined set of 
	  * &lt;initialAssignment&gt;, &lt;assignmentRule&gt; and &lt;kineticLaw&gt; definitions in a model. 
	  * Each of these constructs has the effect of assigning a value to an identifier 
	  * (i.e. the identifier given in the field 'symbol' in &lt;initialAssignment&gt;, the 
	  * field 'variable' in &lt;assignmentRule&gt;, and the field 'id' on the &lt;kineticLaw&gt;'s 
	  * enclosing &lt;reaction&gt;). Each of these constructs computes the value using a 
	  * mathematical formula. The formula for a given identifier cannot make reference 
	  * to a second identifier whose own definition depends directly or indirectly on 
	  * the first identifier. Reference: L3V1 Section 4.9.5 
	  */
 	 public static final int CORE_20906 = 20906; 

	 /**
	  * Error code 20907:
	  * Every AssignmentRule, RateRule and AlgebraicRule object must contain exactly one 
	  * MathML &lt;math&gt; element. The &lt;math&gt; element is optional in L3V2 and beyond. 
	  * Reference: L3V1 Section 4.9 
	  */
 	 public static final int CORE_20907 = 20907; 

	 /**
	  * Error code 20908:
	  * An AssignmentRule object must have the required attribute 'variable' and may 
	  * have the optional attributes 'metaid' and 'sboTerm'. No other attributes from 
	  * the SBML Level 3 Core namespace are permitted on an AssignmentRule object. 
	  * Reference: L3V1 Section 4.9 
	  */
 	 public static final int CORE_20908 = 20908; 

	 /**
	  * Error code 20909:
	  * A RateRule object must have the required attribute 'variable' and may have the 
	  * optional attributes 'metaid' and 'sboTerm'. No other attributes from the SBML 
	  * Level 3 Core namespace are permitted on a RateRule object. Reference: L3V1 
	  * Section 4.9 
	  */
 	 public static final int CORE_20909 = 20909; 

	 /**
	  * Error code 20910:
	  * An AlgebraicRule object may have the optional attributes 'metaid' and 'sboTerm'. 
	  * No other attributes from the SBML Level 3 Core namespace are permitted on an 
	  * AlgebraicRule object. Reference: L3V1 Section 4.9 
	  */
 	 public static final int CORE_20910 = 20910; 

	 /**
	  * Error code 20911:
	  * The value of a &lt;rateRule&gt; or &lt;assignmentRule&gt;'s 'variable' attribute must not be 
	  * the identifier of a &lt;compartment&gt; with a 'spatialDimensions' value of 0. 
	  */
 	 public static final int CORE_20911 = 20911; 

	 /**
	  * Error code 20912:
	  * There must not be circular dependencies in the combined set of &lt;rateRule&gt; and 
	  * &lt;kineticLaw&gt; objects in the model. Each of these constructs has the effect of 
	  * assigning a value to the time derivative of one or more identifiers (i.e., the 
	  * identifier given in the attribute 'variable' in the &lt;rateRule&gt;, and the 
	  * identifier of any &lt;species&gt; referenced by a &lt;speciesReference&gt; in the same 
	  * &lt;reaction&gt; as the &lt;kineticLaw&gt;). Each of these constructs computes the value 
	  * using a mathematical formula. The formula used to calculate the time derivative 
	  * of a given identifier cannot make reference to a second identifier whose own 
	  * definition depends directly or indirectly on a 'rateOf' 'csymbol' for the first 
	  * identifier. 
	  */
 	 public static final int CORE_20912 = 20912; 

	 /**
	  * Error code 21001:
	  * A &lt;constraint&gt;'s &lt;math&gt; expression must evaluate to a value of type Boolean. 
	  * Reference: L3V1 Section 4.10 
	  */
 	 public static final int CORE_21001 = 21001; 

	 /**
	  * Error code 21002:
	  * The order of subelements within &lt;constraint&gt; must be the following: &lt;math&gt;, 
	  * &lt;message&gt;. The &lt;message&gt; element is optional, but if present, must follow the 
	  * &lt;math&gt; element. 
	  */
 	 public static final int CORE_21002 = 21002; 

	 /**
	  * Error code 21003:
	  * The contents of the &lt;message&gt; element in a &lt;constraint&gt; must be explicitly 
	  * placed in the XHTML XML namespace. 
	  */
 	 public static final int CORE_21003 = 21003; 

	 /**
	  * Error code 21004:
	  * The contents of the &lt;message&gt; element must not contain an XML declaration (i.e., 
	  * a string of the form "&lt;?xml version="1.0" encoding="UTF-8"?&gt;" or similar). 
	  * Reference: L3V1 Section 4.10.2 
	  */
 	 public static final int CORE_21004 = 21004; 

	 /**
	  * Error code 21005:
	  * The contents of the &lt;message&gt; element must not contain an XML DOCTYPE 
	  * declaration (i.e., a string beginning with the characters "&lt;!DOCTYPE". 
	  * Reference: L3V1 Section 4.10.2 
	  */
 	 public static final int CORE_21005 = 21005; 

	 /**
	  * Error code 21006:
	  * The XHTML content inside a &lt;constraint&gt;'s &lt;message&gt; element can only take one of 
	  * the following general forms: (1) a complete XHTML document beginning with the 
	  * element &lt;html&gt; and ending with &lt;/html&gt;; (2) the "body" portion of a document 
	  * beginning with the element &lt;body&gt; and ending with &lt;/body&gt;; or (3) XHTML content 
	  * that is permitted within a &lt;body&gt; ... &lt;/body&gt; elements. 
	  */
 	 public static final int CORE_21006 = 21006; 

	 /**
	  * Error code 21007:
	  * A &lt;constraint&gt; object must contain exactly one MathML &lt;math&gt; element. The &lt;math&gt; 
	  * element is optional in L3V2 and beyond. Reference: L3V1 Section 4.10 
	  */
 	 public static final int CORE_21007 = 21007; 

	 /**
	  * Error code 21008:
	  * A &lt;constraint&gt; object may contain at most one &lt;message&gt; subobject. Reference: 
	  * L3V1 Section 4.10 
	  */
 	 public static final int CORE_21008 = 21008; 

	 /**
	  * Error code 21009:
	  * A &lt;constraint&gt; object may have the optional attributes 'metaid' and 'sboTerm'. 
	  * No other attributes from the SBML Level 3 Core namespace are permitted on a 
	  * Constraint object. Reference: L3V1 Section 4.10 
	  */
 	 public static final int CORE_21009 = 21009; 

	 /**
	  * Error code 21101:
	  * A &lt;reaction&gt; definition must contain at least one &lt;speciesReference&gt;, either in 
	  * its &lt;listOfReactants&gt; or its &lt;listOfProducts&gt;. A reaction without any reactant 
	  * or product species is not permitted, regardless of whether the reaction has any 
	  * modifier species. Reference: L3V1 Section 4.11.3 
	  */
 	 public static final int CORE_21101 = 21101; 

	 /**
	  * Error code 21102:
	  * The order of subelements within &lt;reaction&gt; must be the following: 
	  * &lt;listOfReactants&gt; (optional), &lt;listOfProducts&gt; (optional), &lt;listOfModifiers&gt; 
	  * (optional), &lt;kineticLaw&gt;. 
	  */
 	 public static final int CORE_21102 = 21102; 

	 /**
	  * Error code 21103:
	  * The following containers are all optional in a &lt;reaction&gt;, but if any is 
	  * present, it must not be empty: &lt;listOfReactants&gt;, &lt;listOfProducts&gt;, 
	  * &lt;listOfModifiers&gt;, &lt;kineticLaw&gt;. Reference: L3V1 Section 4.11 
	  */
 	 public static final int CORE_21103 = 21103; 

	 /**
	  * Error code 21104:
	  * The list of reactants (&lt;listOfReactants&gt;) and list of products 
	  * (&lt;listOfProducts&gt;) in a &lt;reaction&gt; can only contain &lt;speciesReference&gt; elements. 
	  * Reference: L3V1 Section 4.11 
	  */
 	 public static final int CORE_21104 = 21104; 

	 /**
	  * Error code 21105:
	  * The list of modifiers (&lt;listOfModifiers&gt;) in a &lt;reaction&gt; can only contain 
	  * &lt;modifierSpeciesReference&gt; elements. Reference: L3V1 Section 4.11 
	  */
 	 public static final int CORE_21105 = 21105; 

	 /**
	  * Error code 21106:
	  * A &lt;reaction&gt; object may contain at most one of each of the following elements: 
	  * &lt;listOfReactants&gt;, &lt;listOfProducts&gt;, &lt;listOfModifiers&gt;, and &lt;kineticLaw&gt;. 
	  * Reference: L3V1 Section 4.11 
	  */
 	 public static final int CORE_21106 = 21106; 

	 /**
	  * Error code 21107:
	  * The value of the attribute 'compartment' in a &lt;reaction&gt; object is optional, but 
	  * if present, must be the identifier of an existing Compartment object defined in 
	  * the model. Reference: L3V1 Section 4.11.1 
	  */
 	 public static final int CORE_21107 = 21107; 

	 /**
	  * Error code 21110:
	  * A &lt;reaction&gt; object must have the required attributes 'id', 'reversible' and 
	  * 'fast', and may have the optional attributes 'metaid', 'sboTerm', 'name' and 
	  * 'compartment'. No other attributes from the SBML Level 3 Core namespace are 
	  * permitted on a Reaction object. Reference: L3V1 Section 4.11 
	  */
 	 public static final int CORE_21110 = 21110; 

	 /**
	  * Error code 21111:
	  * The value of a &lt;speciesReference&gt; 'species' attribute must be the identifier of 
	  * an existing &lt;species&gt; in the model. Reference: L3V1 Section 4.11.3 
	  */
 	 public static final int CORE_21111 = 21111; 

	 /**
	  * Error code 21112:
	  */
 	 public static final int CORE_21112 = 21112; 

	 /**
	  * Error code 21113:
	  * A &lt;speciesReference&gt; must not have a value for both 'stoichiometry' and 
	  * 'stoichiometryMath'; they are mutually exclusive. 
	  */
 	 public static final int CORE_21113 = 21113; 

	 /**
	  * Error code 21116:
	  * A &lt;speciesReference&gt; object must have the required attributes 'species' and 
	  * 'constant', and may have the optional attributes 'metaid', 'sboTerm', 'name' 
	  * 'id' and 'stoichiometry'. No other attributes from the SBML Level 3 Core 
	  * namespace are permitted on a &lt;speciesReference&gt; object. Reference: L3V1 Section 
	  * 4.11 
	  */
 	 public static final int CORE_21116 = 21116; 

	 /**
	  * Error code 21117:
	  * A &lt;modifierSpeciesReference&gt; object must have the required attribute 'species' 
	  * and may have the optional attributes 'metaid', 'sboTerm', 'id' and 'name'. No 
	  * other attributes from the SBML Level 3 Core namespace are permitted on a 
	  * &lt;modifierSpeciesReference&gt; object. Reference: L3V1 Section 4.11 
	  */
 	 public static final int CORE_21117 = 21117; 

	 /**
	  * Error code 21121:
	  * All species referenced in the &lt;kineticLaw&gt; formula of a given reaction must 
	  * first be declared using &lt;speciesReference&gt; or &lt;modifierSpeciesReference&gt;. More 
	  * formally, if a &lt;species&gt; identifier appears in a &lt;ci&gt; element of a &lt;reaction&gt;'s 
	  * &lt;kineticLaw&gt; formula, that same identifier must also appear in at least one 
	  * &lt;speciesReference&gt; or &lt;modifierSpeciesReference&gt; in the &lt;reaction&gt; definition. 
	  * Reference: L3V1 Section 4.11.5 
	  */
 	 public static final int CORE_21121 = 21121; 

	 /**
	  * Error code 21122:
	  * The order of subelements within &lt;kineticLaw&gt; must be the following: &lt;math&gt;, 
	  * &lt;listOfParameters&gt;. The &lt;listOfParameters&gt; is optional, but if present, must 
	  * follow &lt;math&gt;. 
	  */
 	 public static final int CORE_21122 = 21122; 

	 /**
	  * Error code 21123:
	  * If present, the &lt;listOfParameters&gt; in a &lt;kineticLaw&gt; must not be an empty list. 
	  * Reference: L3V1 Section 4.11 
	  */
 	 public static final int CORE_21123 = 21123; 

	 /**
	  * Error code 21124:
	  * The 'constant' attribute on a &lt;parameter&gt; local to a &lt;kineticLaw&gt; cannot have a 
	  * value other than 'true'. The values of parameters local to &lt;kineticLaw&gt; 
	  * definitions cannot be changed, and therefore they are always constant. 
	  */
 	 public static final int CORE_21124 = 21124; 

	 /**
	  * Error code 21125:
	  * The 'substanceUnits' attribute on &lt;kineticLaw&gt;, previously available in SBML 
	  * Level 1 and Level 2 Version 1, has been removed as of SBML Level 2 Version 2. In 
	  * SBML Level 2 Version 2, the substance units of a reaction rate expression are 
	  * those of the global 'substance' units of the model. 
	  */
 	 public static final int CORE_21125 = 21125; 

	 /**
	  * Error code 21126:
	  * The 'timeUnits' attribute on &lt;kineticLaw&gt;, previously available in SBML Level 1 
	  * and Level 2 Version 1, has been removed as of SBML Level 2 Version 2. In SBML 
	  * Level 2 Version 2, the time units of a reaction rate expression are those of the 
	  * global 'time' units of the model. 
	  */
 	 public static final int CORE_21126 = 21126; 

	 /**
	  * Error code 21127:
	  * A KineticLaw object may contain at most one ListOfLocalParameters container 
	  * object. Reference: L3V1 Section 4.11 
	  */
 	 public static final int CORE_21127 = 21127; 

	 /**
	  * Error code 21128:
	  * Apart from the general Notes and Annotation subobjects permitted on all SBML 
	  * components, a ListOfLocalParameters container object may only contain 
	  * LocalParameter objects. Reference: L3V1 Section 4.2.8 
	  */
 	 public static final int CORE_21128 = 21128; 

	 /**
	  * Error code 21129:
	  * A ListOfLocalParameters object may have the optional attributes 'metaid' and 
	  * 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are 
	  * permitted on a ListOfLocalParameters object. Reference: L3V1 Section 4.11 
	  */
 	 public static final int CORE_21129 = 21129; 

	 /**
	  * Error code 21130:
	  * A KineticLaw object must contain exactly one MathML &lt;math&gt; element. The &lt;math&gt; 
	  * element is optional in L3V2 and beyond. Reference: L3V1 Section 4.11 
	  */
 	 public static final int CORE_21130 = 21130; 

	 /**
	  * Error code 21131:
	  * All species referenced in the &lt;stoichiometryMath&gt; formula of a given reaction 
	  * must first be declared using &lt;speciesReference&gt; or &lt;modifierSpeciesReference&gt;. 
	  * More formally, if a &lt;species&gt; identifier appears in a &lt;ci&gt; element of a 
	  * &lt;reaction&gt;'s &lt;stoichiometryMath&gt; formula, that same identifier must also appear 
	  * in at least one &lt;speciesReference&gt; or &lt;modifierSpeciesReference&gt; in the 
	  * &lt;reaction&gt; definition. 
	  */
 	 public static final int CORE_21131 = 21131; 

	 /**
	  * Error code 21132:
	  * A KineticLaw object may have the optional attributes 'metaid' and 'sboTerm'. No 
	  * other attributes from the SBML Level 3 Core namespace are permitted on a 
	  * KineticLaw object. Reference: L3V1 Section 4.11 
	  */
 	 public static final int CORE_21132 = 21132; 

	 /**
	  * Error code 21150:
	  * A ListOfSpeciesReferences object may have the optional attributes 'metaid' and 
	  * 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are 
	  * permitted on a ListOfSpeciesReferences object. Reference: L3V1 Section 4.11 
	  */
 	 public static final int CORE_21150 = 21150; 

	 /**
	  * Error code 21151:
	  * A ListOfModifierSpeciesReferences object may have the optional attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace 
	  * are permitted on a ListOfModifierSpeciesReferences object. Reference: L3V1 
	  * Section 4.11 
	  */
 	 public static final int CORE_21151 = 21151; 

	 /**
	  * Error code 21152:
	  * The value of the 'fast' attribute should be 'false', as the attribute has been 
	  * deprecated. 
	  */
 	 public static final int CORE_21152 = 21152; 

	 /**
	  * Error code 21172:
	  * A LocalParameter object must have the required attribute 'id' and may have the 
	  * optional attributes 'metaid', 'sboTerm', 'name', 'value' and 'units'. No other 
	  * attributes from the SBML Level 3 Core namespace are permitted on a 
	  * LocalParameter object. Reference: L3V1 Section 4.11 
	  */
 	 public static final int CORE_21172 = 21172; 

	 /**
	  * Error code 21173:
	  * [Although SBML Level 3 Version 1 does not explicitly define the following as an 
	  * error, other Levels and/or Versions of SBML do.] The 'id' attribute of a 
	  * &lt;localParameter&gt; object must not be the same as the 'species' attribute of any 
	  * &lt;speciesReference&gt; in the same &lt;reaction&gt;. 
	  */
 	 public static final int CORE_21173 = 21173; 

	 /**
	  * Error code 21201:
	  * An &lt;event&gt; object must have a 'trigger'. In SBML Level 3 Version 2 this rule was 
	  * relaxed to stating that an &lt;event&gt; object must have at most one 'trigger' 
	  * element. Reference: L3V1 Section 4.12.2 
	  */
 	 public static final int CORE_21201 = 21201; 

	 /**
	  * Error code 21202:
	  * An &lt;event&gt;'s &lt;trigger&gt; expression must evaluate to a value of type Boolean. 
	  * Reference: L3V1 Section 4.12.2 
	  */
 	 public static final int CORE_21202 = 21202; 

	 /**
	  * Error code 21203:
	  * An &lt;event&gt; object must have at least one &lt;eventAssignment&gt; object in its 
	  * &lt;listOfEventAssignments&gt;. Reference: L3V1 Section 4.12 
	  */
 	 public static final int CORE_21203 = 21203; 

	 /**
	  * Error code 21204:
	  * The value of an &lt;event&gt;'s 'timeUnits' attribute must be 'time', 'second', 
	  * 'dimensionless', or the identifier of a &lt;unitDefinition&gt; derived from either 
	  * 'second' (with an 'exponent' value of '1') or 'dimensionless'. 
	  */
 	 public static final int CORE_21204 = 21204; 

	 /**
	  * Error code 21205:
	  * The order of subelements within &lt;event&gt; must be the following: &lt;trigger&gt;, 
	  * &lt;delay&gt;, &lt;listOfEventAssignments&gt;. The &lt;delay&gt; element is optional, but if 
	  * present, must follow &lt;trigger&gt;. 
	  */
 	 public static final int CORE_21205 = 21205; 

	 /**
	  * Error code 21206:
	  * If an &lt;event&gt;'s 'useValuesFromTriggerTime' attribute has the value 'false', then 
	  * the &lt;event&gt; must contain a &lt;delay&gt; element. The implication of 
	  * 'useValuesFromTriggerTime=false' is that there is a delay between the time of 
	  * trigger and the time of value assignments performed by the &lt;event&gt;. Reference: 
	  * L3V1 Section 4.12 
	  */
 	 public static final int CORE_21206 = 21206; 

	 /**
	  * Error code 21207:
	  * If an Event object contains a Delay subobject, then the Event must have a value 
	  * for the attribute 'useValuesFromTriggerTime'. Reference: L3V1 Section 4.12 
	  */
 	 public static final int CORE_21207 = 21207; 

	 /**
	  * Error code 21209:
	  * A Trigger object must contain exactly one MathML &lt;math&gt; element. The &lt;math&gt; 
	  * element is optional in L3V2 and beyond. Reference: L3V1 Section 4.12 
	  */
 	 public static final int CORE_21209 = 21209; 

	 /**
	  * Error code 21210:
	  * A Delay object must contain exactly one MathML &lt;math&gt; element. The &lt;math&gt; 
	  * element is optional in L3V2 and beyond. Reference: L3V1 Section 4.12 
	  */
 	 public static final int CORE_21210 = 21210; 

	 /**
	  * Error code 21211:
	  * The value of the attribute 'variable' in an &lt;eventAssignment&gt; can only be the 
	  * identifier of a &lt;compartment&gt;, &lt;species&gt;, model-wide &lt;parameter&gt; definition, or 
	  * (in Level 3) &lt;speciesReference&gt;, or (in Level 3 Version 2), any identifier in 
	  * the SId namespace of the model belonging to an element defined by an SBML Level 
	  * 3 package as having with mathematical meaning. Reference: L3V1 Section 4.12.4 
	  */
 	 public static final int CORE_21211 = 21211; 

	 /**
	  * Error code 21212:
	  * Any &lt;compartment&gt;, &lt;species&gt;, &lt;parameter&gt;, or (in Level 3) &lt;speciesReference&gt; 
	  * definition whose identifier is used as the value of 'variable' in an 
	  * &lt;eventAssignment&gt; must have a value of 'false' for its 'constant' attribute. 
	  * Reference: L3V1 Section 4.12.4 
	  */
 	 public static final int CORE_21212 = 21212; 

	 /**
	  * Error code 21213:
	  * An EventAssignment object must contain exactly one MathML &lt;math&gt; element. The 
	  * &lt;math&gt; element is optional in L3V2 and beyond. Reference: L3V1 Section 4.12 
	  */
 	 public static final int CORE_21213 = 21213; 

	 /**
	  * Error code 21214:
	  * An EventAssignment object must have the required attribute 'variable' and may 
	  * have the optional attributes 'metaid' and 'sboTerm'. No other attributes from 
	  * the SBML Level 3 Core namespace are permitted on an EventAssignment object. 
	  * Reference: L3V1 Section 4.12 
	  */
 	 public static final int CORE_21214 = 21214; 

	 /**
	  * Error code 21221:
	  * An Event object may contain at most one Delay object. Reference: L3V1 Section 
	  * 4.12 
	  */
 	 public static final int CORE_21221 = 21221; 

	 /**
	  * Error code 21222:
	  * An Event object may contain at most one ListOfEventAssignments object. 
	  * Reference: L3V1 Section 4.12 
	  */
 	 public static final int CORE_21222 = 21222; 

	 /**
	  * Error code 21223:
	  * Apart from the general Notes and Annotation subobjects permitted on all SBML 
	  * components, a ListOfEventAssignments container object may only contain 
	  * EventAssignment objects. Reference: L3V1 Section 4.12.4 
	  */
 	 public static final int CORE_21223 = 21223; 

	 /**
	  * Error code 21224:
	  * A ListOfEventAssignments object may have the optional attributes 'metaid' and 
	  * 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are 
	  * permitted on a ListOfEventAssignments object. Reference: L3V1 Section 4.12 
	  */
 	 public static final int CORE_21224 = 21224; 

	 /**
	  * Error code 21225:
	  * An Event object must have the required attribute 'useValuesFromTriggerTime' and 
	  * in addition may have the optional attributes 'metaid', 'sboTerm', 'id', and 
	  * 'name'. No other attributes from the SBML Level 3 Core namespace are permitted 
	  * on an Event object. Reference: L3V1 Section 4.12 
	  */
 	 public static final int CORE_21225 = 21225; 

	 /**
	  * Error code 21226:
	  * A Trigger object must have the required attributes 'persistent' and 
	  * 'initialValue', and in addition, may have the optional attributes 'metaid' and 
	  * 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are 
	  * permitted on a Trigger object. Reference: L3V1 Section 4.12 
	  */
 	 public static final int CORE_21226 = 21226; 

	 /**
	  * Error code 21227:
	  * A Delay object may have the optional attributes 'metaid' and 'sboTerm'. No other 
	  * attributes from the SBML Level 3 Core namespace are permitted on a Delay object. 
	  * Reference: L3V1 Section 4.12 
	  */
 	 public static final int CORE_21227 = 21227; 

	 /**
	  * Error code 21228:
	  * The attribute 'persistent' on a Trigger object must have a value of type 
	  * Boolean. Reference: L3V1 Section 4.12 
	  */
 	 public static final int CORE_21228 = 21228; 

	 /**
	  * Error code 21229:
	  * The attribute 'initialValue' on a Trigger object must have a value of type 
	  * Boolean. Reference: L3V1 Section 4.12 
	  */
 	 public static final int CORE_21229 = 21229; 

	 /**
	  * Error code 21230:
	  * An Event object may contain at most one Priority object. Reference: L3V1 Section 
	  * 4.12 
	  */
 	 public static final int CORE_21230 = 21230; 

	 /**
	  * Error code 21231:
	  * An Priority object must contain exactly one MathML &lt;math&gt; element. The &lt;math&gt; 
	  * element is optional in L3V2 and beyond. Reference: L3V1 Section 4.12 
	  */
 	 public static final int CORE_21231 = 21231; 

	 /**
	  * Error code 21232:
	  * A Priority object may have the optional attributes 'metaid' and 'sboTerm'. No 
	  * other attributes from the SBML Level 3 Core namespace are permitted on a 
	  * Priority object. Reference: L3V1 Section 4.12 
	  */
 	 public static final int CORE_21232 = 21232; 

	 /**
	  * Error code 29999:
	  */
 	 public static final int CORE_29999 = 29999; 

	 /**
	  * Error code 80501:
	  * As a principle of best modeling practice, the size of a &lt;compartment&gt; should be 
	  * set to a value rather than be left undefined. Doing so improves the portability 
	  * of models between different simulation and analysis systems, and helps make it 
	  * easier to detect potential errors in models. 
	  */
 	 public static final int CORE_80501 = 80501; 

	 /**
	  * Error code 80601:
	  * As a principle of best modeling practice, the &lt;species&gt; should set an initial 
	  * value (amount or concentration) rather than be left undefined. Doing so improves 
	  * the portability of models between different simulation and analysis systems, and 
	  * helps make it easier to detect potential errors in models. 
	  */
 	 public static final int CORE_80601 = 80601; 

	 /**
	  * Error code 80701:
	  * As a principle of best modeling practice, the units of a &lt;parameter&gt; should be 
	  * declared rather than be left undefined. Doing so improves the ability of 
	  * software to check the consistency of units and helps make it easier to detect 
	  * potential errors in models. 
	  */
 	 public static final int CORE_80701 = 80701; 

	 /**
	  * Error code 80702:
	  * As a principle of best modeling practice, the &lt;parameter&gt; should set an initial 
	  * value rather than be left undefined. Doing so improves the portability of models 
	  * between different simulation and analysis systems, and helps make it easier to 
	  * detect potential errors in models. 
	  */
 	 public static final int CORE_80702 = 80702; 

	 /**
	  * Error code 81121:
	  * In SBML's simple symbol system, there is no separation of symbols by class of 
	  * object; consequently, inside the kinetic law mathematical formula, the value of 
	  * a local parameter having the same identifier as a species or compartment or 
	  * other global model entity will override the global value. Modelers may wish to 
	  * take precautions to avoid this happening accidentally. 
	  */
 	 public static final int CORE_81121 = 81121; 

	 /**
	  * Error code 90000:
	  */
 	 public static final int CORE_90000 = 90000; 

	 /**
	  * Error code 90001:
	  * Use of SBML Level 1 Version 1 is not recommended and conversion is not supported 
	  * by libSBML. 
	  */
 	 public static final int CORE_90001 = 90001; 

	 /**
	  * Error code 91001:
	  * A model with &lt;event&gt; definitions cannot be represented in SBML Level 1. 
	  */
 	 public static final int CORE_91001 = 91001; 

	 /**
	  * Error code 91002:
	  * SBML Level 1 does not have &lt;functionDefinitions&gt;. The &lt;functionDefinitions&gt; in 
	  * the original model have been replaced by appropriate formula. 
	  */
 	 public static final int CORE_91002 = 91002; 

	 /**
	  * Error code 91003:
	  * Conversion of a model with &lt;constraint&gt;s to SBML Level 1 may result in loss of 
	  * information. 
	  */
 	 public static final int CORE_91003 = 91003; 

	 /**
	  * Error code 91004:
	  * SBML Level 1 does not have &lt;initialAssignments&gt;. The &lt;initialAssignments&gt; in the 
	  * original model have been replaced by appropriate values. 
	  */
 	 public static final int CORE_91004 = 91004; 

	 /**
	  * Error code 91005:
	  * &lt;speciesType&gt; definitions cannot be represented in SBML Level 1. 
	  */
 	 public static final int CORE_91005 = 91005; 

	 /**
	  * Error code 91006:
	  * &lt;compartmentType&gt; definitions cannot be represented in SBML Level 1. 
	  */
 	 public static final int CORE_91006 = 91006; 

	 /**
	  * Error code 91007:
	  * A &lt;compartment&gt; with 'spatialDimensions' not equal to 3 cannot be represented in 
	  * SBML Level 1. 
	  */
 	 public static final int CORE_91007 = 91007; 

	 /**
	  * Error code 91008:
	  * A &lt;speciesReference&gt; containing a non-integer or non-rational 
	  * &lt;stoichiometryMath&gt; subelement (or in Level3 whose stoichiometry is assigned by 
	  * non-integer or non-rational math) cannot be represented in SBML Level 1. 
	  */
 	 public static final int CORE_91008 = 91008; 

	 /**
	  * Error code 91009:
	  * A &lt;speciesReference&gt; containing a non-integer 'stoichiometry' attribute value 
	  * cannot be represented in SBML Level 1. 
	  */
 	 public static final int CORE_91009 = 91009; 

	 /**
	  * Error code 91010:
	  * A &lt;unit&gt; containing multipliers or offsets cannot be represented in SBML Level 
	  * 1. 
	  */
 	 public static final int CORE_91010 = 91010; 

	 /**
	  * Error code 91011:
	  * A &lt;species&gt; that does not identify its compartment cannot be represented in SBML 
	  * Level 1. 
	  */
 	 public static final int CORE_91011 = 91011; 

	 /**
	  * Error code 91012:
	  * The information represented by the value of a 'spatialSizeUnit' attribute on a 
	  * &lt;species&gt; definition cannot be represented in SBML Level 1. 
	  */
 	 public static final int CORE_91012 = 91012; 

	 /**
	  * Error code 91013:
	  * SBO terms cannot be represented directly in SBML Level 1. 
	  */
 	 public static final int CORE_91013 = 91013; 

	 /**
	  * Error code 91014:
	  * SBML Level 2 Version 4 removed the requirement that all units be consistent. 
	  * This model contains units that produce inconsistencies and thus conversion to 
	  * Level 1 would produce an invalid model. 
	  */
 	 public static final int CORE_91014 = 91014; 

	 /**
	  * Error code 91015:
	  * This model contains conversion factors and thus conversion to earlier levels is 
	  * not supported. 
	  */
 	 public static final int CORE_91015 = 91015; 

	 /**
	  * Error code 91016:
	  * SBML Level 1/2 do not have the 'compartment' attribute on a Reaction. This 
	  * information will be lost in the conversion. 
	  */
 	 public static final int CORE_91016 = 91016; 

	 /**
	  * Error code 91017:
	  * Conversion to SBML Level 1 requires that 'extent' units be a variant of 
	  * substance 
	  */
 	 public static final int CORE_91017 = 91017; 

	 /**
	  * Error code 91018:
	  * Conversion to SBML Levels 1 and 2 requires that any global units must refer to a 
	  * valid unit kind or the id of a valid unitDefinition. 
	  */
 	 public static final int CORE_91018 = 91018; 

	 /**
	  * Error code 91019:
	  * The concept of a Species having only substance units and not units of 
	  * concentration cannot be correctly interpreted in SBML Level 1 without alteration 
	  * of any mathematical formula. LibSBML does not do this alteration 
	  */
 	 public static final int CORE_91019 = 91019; 

	 /**
	  * Error code 91020:
	  * Conversion of the csymbol avogadro is not yet supported. 
	  */
 	 public static final int CORE_91020 = 91020; 

	 /**
	  * Error code 92001:
	  * Conversion of a model with &lt;constraints&gt; to SBML Level 2 Version 1 may result in 
	  * loss of information. 
	  */
 	 public static final int CORE_92001 = 92001; 

	 /**
	  * Error code 92002:
	  * SBML Level 2 Version 1 does not have &lt;initialAssignments&gt;. The 
	  * &lt;initialAssignments&gt; in the original model have been replaced by appropriate 
	  * values. 
	  */
 	 public static final int CORE_92002 = 92002; 

	 /**
	  * Error code 92003:
	  * &lt;speciesType&gt; definitions cannot be represented in SBML Level 2 Version 1. 
	  */
 	 public static final int CORE_92003 = 92003; 

	 /**
	  * Error code 92004:
	  * &lt;compartmentType&gt; definitions cannot be represented in SBML Level 2 Version 1. 
	  */
 	 public static final int CORE_92004 = 92004; 

	 /**
	  * Error code 92005:
	  * SBO terms cannot be represented directly in SBML Level 2 Version 1. 
	  */
 	 public static final int CORE_92005 = 92005; 

	 /**
	  * Error code 92006:
	  * The 'id' attribute value of a &lt;speciesReference&gt; cannot be represented directly 
	  * in SBML Level 2 Version 1. 
	  */
 	 public static final int CORE_92006 = 92006; 

	 /**
	  * Error code 92007:
	  * The implication of 'useValuesFromTriggerTime=false' is that there is a delay 
	  * between the time of trigger and the time of value assignments performed by the 
	  * &lt;event&gt;. This interpretation is not supported by SBML Level 2 versions prior to 
	  * Version 4. (References: L2V3 Section 4.14; L2V4 Section 4.14) 
	  */
 	 public static final int CORE_92007 = 92007; 

	 /**
	  * Error code 92008:
	  * SBML Level 2 Version 4 removed the requirement that all units be consistent. 
	  * This model contains units that produce inconsistencies and thus conversion to 
	  * Level 2 Version 1 would produce an invalid model. 
	  */
 	 public static final int CORE_92008 = 92008; 

	 /**
	  * Error code 92009:
	  * Models in SBML Level 2 Versions 1-4 can only represent compartments with integer 
	  * values 0, 1, 2, or 3 as spatial dimensions. 
	  */
 	 public static final int CORE_92009 = 92009; 

	 /**
	  * Error code 92010:
	  * Conversion of a varying stoichiometry to an SBML L2 StoichiometryMath element is 
	  * not yet supported. 
	  */
 	 public static final int CORE_92010 = 92010; 

	 /**
	  * Error code 92011:
	  * Event priority is not supported in SBML Level 2. 
	  */
 	 public static final int CORE_92011 = 92011; 

	 /**
	  * Error code 92012:
	  * Non persistent events are not supported in SBML Level 2. 
	  */
 	 public static final int CORE_92012 = 92012; 

	 /**
	  * Error code 92013:
	  * Initialvalue of event Triggers was assumed true in L2. 
	  */
 	 public static final int CORE_92013 = 92013; 

	 /**
	  * Error code 93001:
	  * In SBML Level 2 Version 2, an 'sboTerm' attribute is only permitted on the 
	  * following elements: &lt;model&gt;, &lt;functionDefinition&gt;, &lt;parameter&gt;, 
	  * &lt;initialAssignment&gt;, &lt;rule&gt;, &lt;constraint&gt;, &lt;reaction&gt;, &lt;speciesReference&gt;, 
	  * &lt;kineticLaw&gt;, &lt;event&gt; and &lt;eventAssignment&gt;. 
	  */
 	 public static final int CORE_93001 = 93001; 

	 /**
	  * Error code 93002:
	  * The 'offset' attribute on &lt;unit&gt; previously available in SBML Level 2 Version 1, 
	  * has been removed as of SBML Level 2 Version 2. (References: L2V2 Section 4.4.) 
	  */
 	 public static final int CORE_93002 = 93002; 

	 /**
	  * Error code 93003:
	  * The 'timeUnits' attribute on &lt;kineticLaw&gt;, previously available in SBML Level 1 
	  * and Level 2 Version 1, has been removed as of SBML Level 2 Version 2. In SBML 
	  * Level 2 Version 2, the time units of a reaction rate expression are those of the 
	  * global 'time' units of the model. (References: L2V2 Section 4.13.5.) 
	  */
 	 public static final int CORE_93003 = 93003; 

	 /**
	  * Error code 93004:
	  * The 'substanceUnits' attribute on &lt;kineticLaw&gt;, previously available in SBML 
	  * Level 1 and Level 2 Version 1, has been removed as of SBML Level 2 Version 2. In 
	  * SBML Level 2 Version 2, the substance units of a reaction rate expression are 
	  * those of the global 'substance' units of the model. (References: L2V2 Section 
	  * 4.13.5.) 
	  */
 	 public static final int CORE_93004 = 93004; 

	 /**
	  * Error code 93005:
	  * The implication of 'useValuesFromTriggerTime=false' is that there is a delay 
	  * between the time of trigger and the time of value assignments performed by the 
	  * &lt;event&gt;. This interpretation is not supported by SBML Level 2 versions prior to 
	  * Version 4. (References: L2V3 Section 4.14; L2V4 Section 4.14) 
	  */
 	 public static final int CORE_93005 = 93005; 

	 /**
	  * Error code 93006:
	  * In SBML Level 2 prior to Version 4 the value of the 'sboTerm' attribute on a 
	  * &lt;model&gt; is expected to be a term derived from SBO:0000004, "modeling framework"; 
	  * in Version 4 and above it is expected to be a term derived from SBO:0000231 
	  * "interaction". Using the existing term will create an invalid L2V2 model. 
	  * (References: L2V2 Section 4.2.1; L2V3 Sections 4.2.2 and 5; L2V4 Sections 4.2.2 
	  * and 5) 
	  */
 	 public static final int CORE_93006 = 93006; 

	 /**
	  * Error code 93007:
	  * SBML Level 2 Version 4 removed the requirement that all units be consistent. 
	  * This model contains units that produce inconsistencies and thus conversion to 
	  * Level 2 Version 2 would produce an invalid model. 
	  */
 	 public static final int CORE_93007 = 93007; 

	 /**
	  * Error code 93008:
	  * SBML Level 2 Version 4 removed the requirement that all sboTerms be drawn from a 
	  * given branch of SBO (http://www.biomodels.net/SBO/). This model contains 
	  * sboTerms that produce inconsistencies and thus conversion to Level 2 Version 2 
	  * would produce an invalid model. 
	  */
 	 public static final int CORE_93008 = 93008; 

	 /**
	  * Error code 93009:
	  * In SBML prior to Level 2 Version 1 annotations it was permissible to have 
	  * multiple top level elements using the same namespace. This is no longer valid. 
	  * (References: L2V2 Section 3.3.3; L2V3 Section 3.2.4; L2V4 Section 3.2.4.) Any 
	  * duplicate top level elements will be placed inside a new top level element named 
	  * "duplicateTopLevelElements" with the namespace 
	  * "http://www.sbml.org/libsbml/annotation" 
	  */
 	 public static final int CORE_93009 = 93009; 

	 /**
	  * Error code 94001:
	  * The 'offset' attribute on &lt;unit&gt; previously available in SBML Level 2 Version 1, 
	  * has been removed as of SBML Level 2 Version 2. (References: L2V2 Section 4.4.) 
	  */
 	 public static final int CORE_94001 = 94001; 

	 /**
	  * Error code 94002:
	  * The 'timeUnits' attribute on &lt;kineticLaw&gt;, previously available in SBML Level 1 
	  * and Level 2 Version 1, has been removed as of SBML Level 2 Version 2. In SBML 
	  * Level 2 Version 3, the time units of a reaction rate expression are those of the 
	  * global 'time' units of the model. (References: L2V2 Section 4.13.5.) 
	  */
 	 public static final int CORE_94002 = 94002; 

	 /**
	  * Error code 94003:
	  * The 'substanceUnits' attribute on &lt;kineticLaw&gt;, previously available in SBML 
	  * Level 1 and Level 2 Version 1, has been removed as of SBML Level 2 Version 2. In 
	  * SBML Level 2 Version 3, the substance units of a reaction rate expression are 
	  * those of the global 'substance' units of the model. (References: L2V2 Section 
	  * 4.13.5.) 
	  */
 	 public static final int CORE_94003 = 94003; 

	 /**
	  * Error code 94004:
	  * The 'spatialSizeUnits' attribute on &lt;species&gt;, previously available in SBML 
	  * Level 2 versions prior to Version 3, has been removed as of SBML Level 2 Version 
	  * 3. (References: L2V3 Section 4.8; L2V4 Section 4.8) 
	  */
 	 public static final int CORE_94004 = 94004; 

	 /**
	  * Error code 94005:
	  * The 'timeUnits' attribute on &lt;event&gt;, previously available in SBML Level 2 
	  * versions prior to Version 3, has been removed as of SBML Level 2 Version 3. 
	  * (References: L2V3 Section 4.14; L2V4 Section 4.14) 
	  */
 	 public static final int CORE_94005 = 94005; 

	 /**
	  * Error code 94006:
	  * The implication of 'useValuesFromTriggerTime=false' is that there is a delay 
	  * between the time of trigger and the time of value assignments performed by the 
	  * &lt;event&gt;. This interpretation is not supported by SBML Level 2 versions prior to 
	  * Version 4. (References: L2V3 Section 4.14; L2V4 Section 4.14) 
	  */
 	 public static final int CORE_94006 = 94006; 

	 /**
	  * Error code 94007:
	  * In SBML Level 2 prior to Version 4 the value of the 'sboTerm' attribute on a 
	  * &lt;model&gt; is expected to be a term derived from SBO:0000004, "modeling framework"; 
	  * in Version 4 and above it is expected to be a term derived from SBO:0000231 
	  * "interaction". Using the existing term will create an invalid L2V3 model. 
	  * (References: L2V2 Section 4.2.1; L2V3 Sections 4.2.2 and 5; L2V4 Sections 4.2.2 
	  * and 5) 
	  */
 	 public static final int CORE_94007 = 94007; 

	 /**
	  * Error code 94008:
	  * SBML Level 2 Version 4 removed the requirement that all units be consistent. 
	  * This model contains units that produce inconsistencies and thus conversion to 
	  * Level 2 Version 3 would produce an invalid model. 
	  */
 	 public static final int CORE_94008 = 94008; 

	 /**
	  * Error code 94009:
	  * SBML Level 2 Version 4 removed the requirement that all sboTerms be drawn from a 
	  * given branch of SBO (http://www.biomodels.net/SBO/). This model contains 
	  * sboTerms that produce inconsistencies and thus conversion to Level 2 Version 3 
	  * would produce an invalid model. 
	  */
 	 public static final int CORE_94009 = 94009; 

	 /**
	  * Error code 94010:
	  * In SBML prior to Level 2 Version 1 annotations it was permissible to have 
	  * multiple top level elements using the same namespace. This is no longer valid. 
	  * (References: L2V2 Section 3.3.3; L2V3 Section 3.2.4; L2V4 Section 3.2.4.) Any 
	  * duplicate top level elements will be placed inside a new top level element named 
	  * "duplicateTopLevelElements" with the namespace 
	  * "http://www.sbml.org/libsbml/annotation" 
	  */
 	 public static final int CORE_94010 = 94010; 

	 /**
	  * Error code 95001:
	  * The 'offset' attribute on &lt;unit&gt; previously available in SBML Level 2 Version 1, 
	  * has been removed as of SBML Level 2 Version 2. (References: L2V2 Section 4.4.) 
	  */
 	 public static final int CORE_95001 = 95001; 

	 /**
	  * Error code 95002:
	  * The 'timeUnits' attribute on &lt;kineticLaw&gt;, previously available in SBML Level 1 
	  * and Level 2 Version 1, has been removed as of SBML Level 2 Version 2. In SBML 
	  * Level 2 Version 3, the time units of a reaction rate expression are those of the 
	  * global 'time' units of the model. (References: L2V2 Section 4.13.5.) 
	  */
 	 public static final int CORE_95002 = 95002; 

	 /**
	  * Error code 95003:
	  * The 'substanceUnits' attribute on &lt;kineticLaw&gt;, previously available in SBML 
	  * Level 1 and Level 2 Version 1, has been removed as of SBML Level 2 Version 2. In 
	  * SBML Level 2 Version 3, the substance units of a reaction rate expression are 
	  * those of the global 'substance' units of the model. (References: L2V2 Section 
	  * 4.13.5.) 
	  */
 	 public static final int CORE_95003 = 95003; 

	 /**
	  * Error code 95004:
	  * The 'spatialSizeUnits' attribute on &lt;species&gt;, previously available in SBML 
	  * Level 2 versions prior to Version 3, has been removed as of SBML Level 2 Version 
	  * 3. (References: L2V3 Section 4.8; L2V4 Section 4.8) 
	  */
 	 public static final int CORE_95004 = 95004; 

	 /**
	  * Error code 95005:
	  * The 'timeUnits' attribute on &lt;event&gt;, previously available in SBML Level 2 
	  * versions prior to Version 3, has been removed as of SBML Level 2 Version 3. 
	  * (References: L2V3 Section 4.14; L2V4 Section 4.14) 
	  */
 	 public static final int CORE_95005 = 95005; 

	 /**
	  * Error code 95006:
	  * In SBML Level 2 prior to Version 4 the value of the 'sboTerm' attribute on a 
	  * &lt;model&gt; is expected to be a term derived from SBO:0000004, "modeling framework"; 
	  * in Version 4 and above it is expected to be a term derived from SBO:0000231 
	  * "interaction". Using the existing term will create an invalid L2V4 model. 
	  * (References: L2V2 Section 4.2.1; L2V3 Sections 4.2.2 and 5; L2V4 Sections 4.2.2 
	  * and 5) 
	  */
 	 public static final int CORE_95006 = 95006; 

	 /**
	  * Error code 95007:
	  * In SBML prior to Level 2 Version 1 annotations it was permissible to have 
	  * multiple top level elements using the same namespace. This is no longer valid. 
	  * (References: L2V2 Section 3.3.3; L2V3 Section 3.2.4; L2V4 Section 3.2.4.) Any 
	  * duplicate top level elements will be placed inside a new top level element named 
	  * "duplicateTopLevelElements" with the namespace 
	  * "http://www.sbml.org/libsbml/annotation" 
	  */
 	 public static final int CORE_95007 = 95007; 

	 /**
	  * Error code 96001:
	  * &lt;speciesType&gt; definitions cannot be represented in SBML Level 3 Version 1. 
	  */
 	 public static final int CORE_96001 = 96001; 

	 /**
	  * Error code 96002:
	  * &lt;compartmentType&gt; definitions cannot be represented in SBML Level 3 Version 1. 
	  */
 	 public static final int CORE_96002 = 96002; 

	 /**
	  * Error code 96003:
	  * The 'offset' attribute on &lt;unit&gt; previously available in SBML Level 2 Version 1, 
	  * has been removed as of SBML Level 2 Version 2. (References: L2V2 Section 4.4.) 
	  */
 	 public static final int CORE_96003 = 96003; 

	 /**
	  * Error code 96004:
	  * The 'timeUnits' attribute on &lt;kineticLaw&gt;, previously available in SBML Level 1 
	  * and Level 2 Version 1, has been removed as of SBML Level 2 Version 2. In SBML 
	  * Level 2 Version 3, the time units of a reaction rate expression are those of the 
	  * global 'time' units of the model. (References: L2V2 Section 4.13.5.) 
	  */
 	 public static final int CORE_96004 = 96004; 

	 /**
	  * Error code 96005:
	  * The 'substanceUnits' attribute on &lt;kineticLaw&gt;, previously available in SBML 
	  * Level 1 and Level 2 Version 1, has been removed as of SBML Level 2 Version 2. In 
	  * SBML Level 2 Version 3, the substance units of a reaction rate expression are 
	  * those of the global 'substance' units of the model. (References: L2V2 Section 
	  * 4.13.5.) 
	  */
 	 public static final int CORE_96005 = 96005; 

	 /**
	  * Error code 96006:
	  * The 'spatialSizeUnits' attribute on &lt;species&gt;, previously available in SBML 
	  * Level 2 versions prior to Version 3, has been removed as of SBML Level 2 Version 
	  * 3. (References: L2V3 Section 4.8; L2V4 Section 4.8) 
	  */
 	 public static final int CORE_96006 = 96006; 

	 /**
	  * Error code 96007:
	  * The 'timeUnits' attribute on &lt;event&gt;, previously available in SBML Level 2 
	  * versions prior to Version 3, has been removed as of SBML Level 2 Version 3. 
	  * (References: L2V3 Section 4.14; L2V4 Section 4.14) 
	  */
 	 public static final int CORE_96007 = 96007; 

	 /**
	  * Error code 96008:
	  * In SBML Level 2 prior to Version 4 the value of the 'sboTerm' attribute on a 
	  * &lt;model&gt; is expected to be a term derived from SBO:0000004, "modeling framework"; 
	  * in Version 4 and above it is expected to be a term derived from SBO:0000231 
	  * "interaction". Using the existing term will create an invalid L2V4 model. 
	  * (References: L2V2 Section 4.2.1; L2V3 Sections 4.2.2 and 5; L2V4 Sections 4.2.2 
	  * and 5) 
	  */
 	 public static final int CORE_96008 = 96008; 

	 /**
	  * Error code 96009:
	  * In SBML prior to Level 2 Version 1 annotations it was permissible to have 
	  * multiple top level elements using the same namespace. This is no longer valid. 
	  * (References: L2V2 Section 3.3.3; L2V3 Section 3.2.4; L2V4 Section 3.2.4.) Any 
	  * duplicate top level elements will be placed inside a new top level element named 
	  * "duplicateTopLevelElements" with the namespace 
	  * "http://www.sbml.org/libsbml/annotation" 
	  */
 	 public static final int CORE_96009 = 96009; 

	 /**
	  * Error code 96010:
	  * The 'outside' attribute on &lt;compartment&gt;, previously available in SBML Level 2 
	  * has been removed as of SBML Level 3 Version 1. 
	  */
 	 public static final int CORE_96010 = 96010; 

	 /**
	  * Error code 96011:
	  * Conversion of a model with &lt;stoichiometryMath&gt; to SBML Level 3 is not yet 
	  * supported. 
	  */
 	 public static final int CORE_96011 = 96011; 

	 /**
	  * Error code 98001:
	  * Conversion of a non-integer exponent is not supported. 
	  */
 	 public static final int CORE_98001 = 98001; 

	 /**
	  * Error code 98002:
	  * SBML Level 3 Version 2 introduced a number of new MathML elements that were not 
	  * supported in previous levels and versions. 
	  */
 	 public static final int CORE_98002 = 98002; 

	 /**
	  * Error code 98003:
	  * SBML Level 3 Version 2 allows a listOf element to be present without any 
	  * children of the listOf type. This was not valid in previous levels and versions 
	  * and the empty listOf will be removed. 
	  */
 	 public static final int CORE_98003 = 98003; 

	 /**
	  * Error code 98004:
	  * SBML Level 3 Version 2 allows elements to be specified without a &lt;math&gt; element. 
	  * Previous levels and versions required the &lt;math&gt; element. The &lt;element&gt; with the 
	  * missing &lt;math&gt; will be removed. 
	  */
 	 public static final int CORE_98004 = 98004; 

	 /**
	  * Error code 98005:
	  * SBML Level 3 Version 2 allows an &lt;event&gt; to be specified without a &lt;trigger&gt; 
	  * element. Previous levels and versions required the &lt;trigger&gt; element. 
	  */
 	 public static final int CORE_98005 = 98005; 

	 /**
	  * Error code 98006:
	  * SBML Level 3 Version 2 relaxed the restrictions on where a math expression 
	  * should return a Boolean or a numeric result. 
	  */
 	 public static final int CORE_98006 = 98006; 

	 /**
	  * Error code 98007:
	  * SBML Level 3 Version 2 moved the 'id' and 'name' attributes to SBase. Previous 
	  * levels and versions do not support these attributes for some elements. These 
	  * attributes will be removed. 
	  */
 	 public static final int CORE_98007 = 98007; 

	 /**
	  * Error code 98008:
	  * SBML Level 3 Version 2 allows an &lt;reaction&gt; to be specified without either a 
	  * &lt;listOfReactants&gt; or &lt;listOfProducts&gt; element. Previous levels and versions 
	  * required the at least one of the two to be present. 
	  */
 	 public static final int CORE_98008 = 98008; 

	 /**
	  * Error code 98009:
	  * SBML Level 3 Version 2 introduced a number of new MathML elements that were not 
	  * supported in previous levels and versions. An &lt;initialAssignment&gt; uses some such 
	  * MathML; this will be converted and the relevant value used in the model. 
	  */
 	 public static final int CORE_98009 = 98009; 

	 /**
	  * Error code 98010:
	  * Conversion of a fast reaction is not supported. 
	  */
 	 public static final int CORE_98010 = 98010; 

	 /**
	  * Error code 98011:
	  * The use of the &lt;speciesReference&gt; id attribute in MathML is not supported prior 
	  * to L3. 
	  */
 	 public static final int CORE_98011 = 98011; 

	 /**
	  * Error code 99101:
	  * The level and version specified for the document must be consistent with a valid 
	  * published SBML specification. These are Level 1, Versions 1 and 2; Level 2, 
	  * Versions 1, 2, 3 and 4; and Level 3, Version 1. If newer levels or versions of 
	  * SBML now exist, you must update your version of libSBML. 
	  */
 	 public static final int CORE_99101 = 99101; 

	 /**
	  * Error code 99102:
	  * A particular version of an L3V1 package can only be used in an L3V2 document if 
	  * there is no L3V2 package specification with the same version. 
	  */
 	 public static final int CORE_99102 = 99102; 

	 /**
	  * Error code 99104:
	  * The &lt;sbml&gt; container element cannot contain &lt;notes&gt; or &lt;annotations&gt; in an SBML 
	  * Level 1 document. 
	  */
 	 public static final int CORE_99104 = 99104; 

	 /**
	  * Error code 99106:
	  * The math field of an assignment rule structure can contain any identifier in a 
	  * MathML &lt;ci&gt; element except for the following: (a) identifiers for which there 
	  * exists a subsequent assignment rule, and (b) the identifier for which the rule 
	  * is defined. (L2V1 Section 4.8.4). 
	  */
 	 public static final int CORE_99106 = 99106; 

	 /**
	  * Error code 99107:
	  * Every SBML Level 3 package is identified uniquely by an XML namespace URI and 
	  * defines the attribute named 'required'. A value of required=true indicates that 
	  * interpreting the package is necessary for complete mathematical interpretation 
	  * of the model. (L3V1 Section 4.1.2) This instance of libSBML version 5 cannot 
	  * find the code necessary to interpret the package information. 
	  */
 	 public static final int CORE_99107 = 99107; 

	 /**
	  * Error code 99108:
	  * Every SBML Level 3 package is identified uniquely by an XML namespace URI and 
	  * defines the attribute named 'required'. LibSBML has detected what appears to be 
	  * an SBML Level 3 package with required=false but this instance of libSBML-5 
	  * cannot find the code necessary to parse this information. 
	  */
 	 public static final int CORE_99108 = 99108; 

	 /**
	  * Error code 99109:
	  * The 'required' attribute for this package is expected to be 'false' as there is 
	  * no way to change the mathematical interpretation of the model using the 
	  * constructs in this package. 
	  */
 	 public static final int CORE_99109 = 99109; 

	 /**
	  * Error code 99127:
	  * A KineticLaw's substanceUnits must be 'substance', 'item', 'mole', or the id of 
	  * a UnitDefinition that defines a variant of 'item' or 'mole' (L2v1 Section 
	  * 4.9.7). 
	  */
 	 public static final int CORE_99127 = 99127; 

	 /**
	  * Error code 99128:
	  * A KineticLaw's timeUnits must be 'time', 'second', or the id of a UnitDefnition 
	  * that defines a variant of 'second' with exponent='1' (L2v1 Section 4.9.7). 
	  */
 	 public static final int CORE_99128 = 99128; 

	 /**
	  * Error code 99129:
	  * In a Level 1 model, only predefined functions are permitted within the formula. 
	  * (L1V2 Appendix C) 
	  */
 	 public static final int CORE_99129 = 99129; 

	 /**
	  * Error code 99130:
	  * The value of the attribute substanceUnits on a Model object should be either the 
	  * units 'mole', 'item', 'avogadro', 'dimensionless', 'kilogram', 'gram', or the 
	  * identifier of a UnitDefinition object based on these units. Reference: L3V1 
	  * Section 4.2.6 
	  */
 	 public static final int CORE_99130 = 99130; 

	 /**
	  * Error code 99131:
	  * The &lt;stoichiometryMath&gt; element lacks a necessary &lt;math&gt; element. 
	  */
 	 public static final int CORE_99131 = 99131; 

	 /**
	  * Error code 99206:
	  * The 'timeUnits' attribute on &lt;event&gt;, previously available in SBML Level 2 
	  * versions prior to Version 3, has been removed as of SBML Level 2 Version 3. 
	  * (References: L2V3 Section 4.14; L2V4 Section 4.14) 
	  */
 	 public static final int CORE_99206 = 99206; 

	 /**
	  * Error code 99219:
	  * Invalid MathML. The body of an &lt;apply&gt; tag must begin with an operator. 
	  */
 	 public static final int CORE_99219 = 99219; 

	 /**
	  * Error code 99220:
	  * Failed to read a valid double value from MathML. 
	  */
 	 public static final int CORE_99220 = 99220; 

	 /**
	  * Error code 99221:
	  * Failed to read a valid integer value from MathML. 
	  */
 	 public static final int CORE_99221 = 99221; 

	 /**
	  * Error code 99222:
	  * Failed to read a valid exponential value from MathML. 
	  */
 	 public static final int CORE_99222 = 99222; 

	 /**
	  * Error code 99223:
	  * Failed to read a valid rational value from MathML. 
	  */
 	 public static final int CORE_99223 = 99223; 

	 /**
	  * Error code 99224:
	  * Invalid MathML. The body of a &lt;math&gt; tag or the body of a &lt;lambda&gt; element must 
	  * begin with one of the following: &lt;cn&gt;, &lt;ci&gt;, &lt;csymbol&gt;, &lt;apply&gt;, &lt;piecewise&gt;, 
	  * &lt;true&gt;, &lt;false&gt;, &lt;notanumber&gt;, &lt;pi&gt;, &lt;infinity&gt;, &lt;exponentiale&gt;, &lt;semantics&gt;. 
	  * (References: L2V3 Appendix B) 
	  */
 	 public static final int CORE_99224 = 99224; 

	 /**
	  * Error code 99225:
	  * An invalid MathML attribute has been encountered. 
	  */
 	 public static final int CORE_99225 = 99225; 

	 /**
	  * Error code 99301:
	  * The csymbol 'time' should not be used within the &lt;math&gt; element of a 
	  * &lt;functionDefinition&gt;. (References: L2V3 Section 4.3.2; L2V4 Section 4.3.2) 
	  */
 	 public static final int CORE_99301 = 99301; 

	 /**
	  * Error code 99302:
	  * The &lt;lambda&gt; element of a FunctionDefinition object must contain a function body 
	  * in addition to zero or more arguments. 
	  */
 	 public static final int CORE_99302 = 99302; 

	 /**
	  * Error code 99303:
	  * Where a component has an attribute that refers to a unit identifier, that 
	  * attribute must refer to a unit defined in SBML or be the id of a UnitDefinition 
	  * in the model. 
	  */
 	 public static final int CORE_99303 = 99303; 

	 /**
	  * Error code 99304:
	  * Inside the lambda MathML element within a FunctionDefinition any bvar elements 
	  * must contain one instance of a &lt;ci&gt; element. No other elements are permitted 
	  * within a bvar element. Reference: L3V1 Section 4.3 
	  */
 	 public static final int CORE_99304 = 99304; 

	 /**
	  * Error code 99401:
	  * In order to follow the general syntax for a standard SBML RDF annotation the 
	  * first element of RDF element must be a Description element with an 'about' 
	  * attribute. Reference: L3V1 Section 6.3 
	  */
 	 public static final int CORE_99401 = 99401; 

	 /**
	  * Error code 99402:
	  * In order to follow the general syntax for a standard SBML RDF annotation, the 
	  * 'about' attribute of the Description element must be of the form #string. 
	  * Reference: L3V1 Section 6.3 
	  */
 	 public static final int CORE_99402 = 99402; 

	 /**
	  * Error code 99403:
	  * In order to follow the general syntax for a standard SBML RDF annotation, the 
	  * 'about' attribute of the Description element must be of the form #string, where 
	  * the string component is equal to the value of the metaid attribute of the 
	  * containing SBML element. Reference: L3V1 Section 6.3 
	  */
 	 public static final int CORE_99403 = 99403; 

	 /**
	  * Error code 99404:
	  * LibSBML expected to read the annotation into a ModelHistory object. 
	  * Unfortunately, some attributes were not present or correct and the resulting 
	  * ModelHistory object will not correctly produce the annotation. This 
	  * functionality will be improved in later versions of libSBML. Reference: L3V1 
	  * Section 6.3 
	  */
 	 public static final int CORE_99404 = 99404; 

	 /**
	  * Error code 99405:
	  * LibSBML expected to read the annotation into a ModelHistory object. 
	  * Unfortunately, some attributes were not present or correct and the resulting 
	  * ModelHistory object is NULL. Thus it will fail to produce the annotation. This 
	  * functionality will be improved in later versions of libSBML. Reference: L3V1 
	  * Section 6.3 
	  */
 	 public static final int CORE_99405 = 99405; 

	 /**
	  * Error code 99406:
	  * An annotation must contain elements; with each top-level element placed in a 
	  * unique namespace. Reference: L3V1 Section 3.2.4 
	  */
 	 public static final int CORE_99406 = 99406; 

	 /**
	  * Error code 99407:
	  * The specified annotation RELATION element does not permit nested elements. This 
	  * was introduced in L2V5 and L3V2. Reference: L3V1 Section 6.3 
	  */
 	 public static final int CORE_99407 = 99407; 

	 /**
	  * Error code 99505:
	  * In situations where a mathematical expression contains literal numbers or 
	  * parameters whose units have not been declared, it is not possible to verify 
	  * accurately the consistency of the units in the expression. 
	  */
 	 public static final int CORE_99505 = 99505; 

	 /**
	  * Error code 99506:
	  * In situations where a mathematical expression refers to time, the units of time 
	  * should be consistent with the global time units of the model. In models where 
	  * the 'timeUnits' attribute has not been declared, libSBML does not yet have the 
	  * functionality to verify accurately the consistency of the units in such 
	  * expressions. 
	  */
 	 public static final int CORE_99506 = 99506; 

	 /**
	  * Error code 99507:
	  * In situations where a mathematical expression refers to extent, the units of 
	  * extent should be consistent with the global extent units of the model. In models 
	  * where the 'extentUnits' attribute has not been declared, libSBML does not yet 
	  * have the functionality to verify accurately the consistency of the units in such 
	  * expressions. 
	  */
 	 public static final int CORE_99507 = 99507; 

	 /**
	  * Error code 99508:
	  * In situations where a mathematical expression refers to a compartment, species 
	  * or parameter, it is necessary to know the units of the object to establish unit 
	  * consistency. In models where the units of an object have not been declared, 
	  * libSBML does not yet have the functionality to accurately verify the consistency 
	  * of the units in mathematical expressions referring to that object. 
	  */
 	 public static final int CORE_99508 = 99508; 

	 /**
	  * Error code 99509:
	  * When an object is missing a &lt;math&gt; element it is not possible to perform unit 
	  * consistency checking for that object. 
	  */
 	 public static final int CORE_99509 = 99509; 

	 /**
	  * Error code 99701:
	  * The SBOTerm used is not recognized by libSBML and therefore the correct 
	  * parentage cannot be checked. However, since libSBML is referring to a snapshot 
	  * of the SBO tree the term may now exist. 
	  */
 	 public static final int CORE_99701 = 99701; 

	 /**
	  * Error code 99702:
	  * The SBOTerm value used is considered obsolete. 
	  */
 	 public static final int CORE_99702 = 99702; 

	 /**
	  * Error code 99901:
	  * The 'spatialDimensions' attribute on &lt;compartment&gt; was not available in SBML 
	  * Level 1. In order for the internal representation of a &lt;compartment&gt; to be 
	  * correct, the value for the spatialDimensions member variable should be '3'. 
	  */
 	 public static final int CORE_99901 = 99901; 

	 /**
	  * Error code 99902:
	  * The 'compartmentType' attribute on &lt;compartment&gt; was not available in SBML Level 
	  * 1 or SBML Level 2 Version 1. In order for the internal representation of a 
	  * &lt;compartment&gt; to be correct, the value for the compartmentType member variable 
	  * should be not be set. 
	  */
 	 public static final int CORE_99902 = 99902; 

	 /**
	  * Error code 99903:
	  * The 'constant' attribute on &lt;compartment&gt; and &lt;parameter&gt; was not available in 
	  * SBML Level 1. In order for the internal representation of &lt;compartment&gt; and 
	  * &lt;parameter&gt; to be correct, the value for the constant member variable should be 
	  * 'false' if the &lt;compartment&gt; or &lt;parameter&gt; is the variable of a &lt;rule&gt; 
	  * otherwise it can be either 'true' or 'false' without affecting the 
	  * interpretation of the model. 
	  */
 	 public static final int CORE_99903 = 99903; 

	 /**
	  * Error code 99904:
	  * The 'metaid' attribute on all &lt;SBase&gt; objects was not available in SBML Level 1. 
	  * In order for the internal representation to be correct, the value for the 
	  * 'metaid' member variable should be not be set. 
	  */
 	 public static final int CORE_99904 = 99904; 

	 /**
	  * Error code 99905:
	  * The 'sboTerm' attribute on &lt;compartment&gt;, &lt;compartmentType&gt; &lt;delay&gt;, &lt;species&gt;, 
	  * &lt;speciesType&gt;, &lt;stoichiometryMath&gt;, &lt;trigger&gt; &lt;unit&gt; and &lt;unitDefinition&gt; was 
	  * not available before SBML Level 2 Version 3. In order for the internal 
	  * representation of these components to be correct, the value for the sboTerm 
	  * member variable should be '-1'. 
	  */
 	 public static final int CORE_99905 = 99905; 

	 /**
	  * Error code 99906:
	  * The value of the 'units' attribute on a &lt;compartment&gt; must be either 'volume', 
	  * 'litre', 'liter' or the identifier of a &lt;unitDefinition&gt; based on either 
	  * 'litre'/'liter' or 'metre'/'meter' (with 'exponent' equal to '3'). 
	  */
 	 public static final int CORE_99906 = 99906; 

	 /**
	  * Error code 99907:
	  * A value for the compartment 'volume' attribute must be specified in this 
	  * Level+Version of SBML. 
	  */
 	 public static final int CORE_99907 = 99907; 

	 /**
	  * Error code 99908:
	  * The &lt;compartmentType&gt; component was introduced in SBML Level 2 Version 2. In 
	  * order for the internal representation of a &lt;model&gt; to be correct, the 
	  * &lt;listOfCompartmentTypes&gt; component should be empty. 
	  */
 	 public static final int CORE_99908 = 99908; 

	 /**
	  * Error code 99909:
	  * The &lt;constraint&gt; component was introduced in SBML Level 2 Version 2. In order 
	  * for the internal representation of a &lt;model&gt; to be correct, the 
	  * &lt;listOfConstraints&gt; component should be empty. 
	  */
 	 public static final int CORE_99909 = 99909; 

	 /**
	  * Error code 99910:
	  * The &lt;event&gt; component was introduced in SBML Level 2 Version 1. In order for the 
	  * internal representation of a &lt;model&gt; to be correct, the &lt;listOfEvents&gt; component 
	  * should be empty. 
	  */
 	 public static final int CORE_99910 = 99910; 

	 /**
	  * Error code 99911:
	  * The 'sboTerm' attribute on &lt;event&gt;, &lt;eventAssignment&gt; &lt;functionDefinition&gt;, 
	  * &lt;kineticLaw&gt;, &lt;model&gt;, &lt;parameter&gt;, &lt;reaction&gt; &lt;rule&gt; and &lt;speciesReferenece&gt; 
	  * was not available before SBML Level 2 Version 2. In order for the internal 
	  * representation of these components to be correct, the value for the sboTerm 
	  * member variable should be '-1'. 
	  */
 	 public static final int CORE_99911 = 99911; 

	 /**
	  * Error code 99912:
	  * The &lt;functionDefinition&gt; component was introduced in SBML Level 2 Version 1. In 
	  * order for the internal representation of a &lt;model&gt; to be correct, the 
	  * &lt;listOfFunctionDefinitions&gt; component should be empty. 
	  */
 	 public static final int CORE_99912 = 99912; 

	 /**
	  * Error code 99913:
	  * The &lt;initialAssignment&gt; component was introduced in SBML Level 2 Version 2. In 
	  * order for the internal respresentation of a &lt;model&gt; to be correct, the 
	  * &lt;listOfInitialAssignments&gt; component should be empty. 
	  */
 	 public static final int CORE_99913 = 99913; 

	 /**
	  * Error code 99914:
	  * An &lt;algebraicRule&gt; does not assign a value to a particular variable and does not 
	  * carry an attribute named 'variable'. 
	  */
 	 public static final int CORE_99914 = 99914; 

	 /**
	  * Error code 99915:
	  * The 'units' attribute on &lt;rule&gt; was only applicable to a &lt;parameterRule&gt; in SBML 
	  * Level 1. In order for the internal respresentation of a &lt;rule&gt; to be correct, 
	  * the value for the units member variable should not be set. 
	  */
 	 public static final int CORE_99915 = 99915; 

	 /**
	  * Error code 99916:
	  * The 'constant' attribute on &lt;species&gt; was not available in SBML Level 1. In 
	  * order for the internal respresentation of &lt;species&gt; to be correct, the value for 
	  * the constant member variable should be 'false' if (1) the &lt;species&gt; is the 
	  * variable of a &lt;rule&gt; or (2) the 'boundaryCondition' attribute is 'false' and the 
	  * &lt;species&gt; is a product/reactant in a &lt;reaction&gt;. Otherwise it can be either 
	  * 'true' or 'false' without affecting the interpretation of the model. 
	  */
 	 public static final int CORE_99916 = 99916; 

	 /**
	  * Error code 99917:
	  * The 'spatialSizeUnits' attribute on &lt;species&gt; was not available in SBML Level 1. 
	  * In order for the internal respresentation of a &lt;species&gt; to be correct, the 
	  * value for the spatialSizeUnits member variable should be not be set. 
	  */
 	 public static final int CORE_99917 = 99917; 

	 /**
	  * Error code 99918:
	  * The 'speciesType' attribute on &lt;species&gt; was not available in SBML Level 1 or 
	  * SBML Level 2 Version 1. In order for the internal respresentation of a &lt;species&gt; 
	  * to be correct, the value for the speciesType member variable should be not be 
	  * set. 
	  */
 	 public static final int CORE_99918 = 99918; 

	 /**
	  * Error code 99919:
	  * The 'hasOnlySubstanceUnits' attribute on &lt;species&gt; was not available in SBML 
	  * Level 1. In order for the internal respresentation of a &lt;species&gt; to be correct, 
	  * the value for the hasOnlySubstanceUnits member variable should be 'false'. 
	  */
 	 public static final int CORE_99919 = 99919; 

	 /**
	  * Error code 99920:
	  * The 'id' attribute on &lt;speciesReference&gt; was not available in SBML Level 1 or 
	  * SBML Level 2 Version 1. In order for the internal respresentation of a 
	  * &lt;speciesReference&gt; to be correct, the value for the id member variable should be 
	  * not be set. 
	  */
 	 public static final int CORE_99920 = 99920; 

	 /**
	  * Error code 99921:
	  * The 'name' attribute on &lt;speciesReference&gt; was not available in SBML Level 1 or 
	  * SBML Level 2 Version 1. In order for the internal respresentation of a 
	  * &lt;speciesReference&gt; to be correct, the value for the name member variable should 
	  * be not be set. 
	  */
 	 public static final int CORE_99921 = 99921; 

	 /**
	  * Error code 99922:
	  * The &lt;speciesType&gt; component was introduced in SBML Level 2 Version 2. In order 
	  * for the internal respresentation of a &lt;model&gt; to be correct, the 
	  * &lt;listOfSpeciesTypes&gt; component should be empty. 
	  */
 	 public static final int CORE_99922 = 99922; 

	 /**
	  * Error code 99923:
	  * The &lt;stoichiometryMath&gt; component on a &lt;speciesReference&gt; was introduced in SBML 
	  * Level 2. In order for the internal respresentation of a &lt;speciesReference&gt; to be 
	  * correct, the &lt;stoichiometryMath&gt; component should be 'NULL'. 
	  */
 	 public static final int CORE_99923 = 99923; 

	 /**
	  * Error code 99924:
	  * The 'multiplier' attribute on &lt;unit&gt; was not available in SBML Level 1. In order 
	  * for the internal respresentation of a &lt;unit&gt; to be correct, the value for the 
	  * multiplier member variable should be '1.0'. 
	  */
 	 public static final int CORE_99924 = 99924; 

	 /**
	  * Error code 99925:
	  * The 'offset' attribute on &lt;unit&gt; was only available in SBML Level 2 Version 1. 
	  * In order for the internal respresentation of a &lt;unit&gt; to be correct, the value 
	  * for the offset member variable should be '0'. 
	  */
 	 public static final int CORE_99925 = 99925; 

	 /**
	  * Error code 99926:
	  * The 'spatialDimensions' attribute on &lt;compartment&gt; was left unset on the SBML 
	  * Level 3 model. Conversion will apply a default value of '3'. 
	  */
 	 public static final int CORE_99926 = 99926; 

	 /**
	  * Error code 99950:
	  * The operation was interrupted by the user. 
	  */
 	 public static final int CORE_99950 = 99950; 

	 /**
	  * Error code 99994:
	  */
 	 public static final int CORE_99994 = 99994; 

	 /**
	  * Error code 99995:
	  */
 	 public static final int CORE_99995 = 99995; 

	 /**
	  * Error code 99996:
	  * Conversion of SBML Level 3 package information is not yet supported. 
	  */
 	 public static final int CORE_99996 = 99996; 

	 /**
	  * Error code 99997:
	  * Conversion is only possible to an existing SBML Level and Version. 
	  */
 	 public static final int CORE_99997 = 99997; 

	 /**
	  * Error code 99998:
	  * Validation of or conversion from L3 documents/models is not yet supported. 
	  */
 	 public static final int CORE_99998 = 99998; 

	 /**
	  * Error code 99999:
	  */
 	 public static final int CORE_99999 = 99999; 

	 /**
	  * Error code 1010100:
	  */
 	 public static final int COMP_10100 = 1010100; 

	 /**
	  * Error code 1010101:
	  * To conform to Version 1 of the Hierarchical Model Composition package 
	  * specification for SBML Level 3, an SBML document must declare the use of the 
	  * following XML Namespace: 
	  * 'http://www.sbml.org/sbml/level3/version1/comp/version1' Reference: L3V1 Comp V1 
	  * Section 3.1 
	  */
 	 public static final int COMP_10101 = 1010101; 

	 /**
	  * Error code 1010102:
	  * Wherever they appear in an SBML document, elements and attributes from the 
	  * Hierarchical Model Composition package must be declared either implicitly or 
	  * explicitly to be in the XML namespace 
	  * 'http://www.sbml.org/sbml/level3/version1/comp/version1' Reference: L3V1 Comp V1 
	  * Section 3.1 
	  */
 	 public static final int COMP_10102 = 1010102; 

	 /**
	  * Error code 1010301:
	  * (Extends validation rule #10301 in the SBML Level 3 Version 1 Core 
	  * specification.) Within a &lt;model&gt; or &lt;modelDefinition&gt; object, the values of the 
	  * attributes id and comp:id on every instance of the following classes of objects 
	  * must be unique across the set of all id and comp:id attribute values of all such 
	  * objects in a model: the model itself, plus all contained &lt;functionDefinition&gt;, 
	  * &lt;compartment&gt;, &lt;species&gt;, &lt;reaction&gt;, &lt;speciesReference&gt;, 
	  * &lt;modifierSpeciesReference&gt;, &lt;event&gt;, and &lt;parameter&gt; objects, plus the 
	  * &lt;submodel&gt; and &lt;deletion&gt; objects defined by the Hierarchical Model Composition 
	  * package, plus any objects defined by any other package with 'package:id' 
	  * attributes defined as falling in the 'SId' namespace. Reference: L3V1 Comp V1 
	  * Section 3.9 
	  */
 	 public static final int COMP_10301 = 1010301; 

	 /**
	  * Error code 1010302:
	  * The values of the attributes 'id' and 'comp:id' on every instance of all 
	  * &lt;model&gt;, &lt;modelDefinition&gt;, and &lt;externalModelDefinition&gt; objects must be unique 
	  * across the set of all 'id' and 'comp:id' attribute values of such objects in the 
	  * SBML document to which they belong. Reference: L3V1 Comp V1 Section 3.9 
	  */
 	 public static final int COMP_10302 = 1010302; 

	 /**
	  * Error code 1010303:
	  * Within &lt;model&gt; and &lt;modelDefinition&gt; objects inside an SBML document, the value 
	  * of the attribute 'comp:id' on every instance of a &lt;port&gt; object must be unique 
	  * across the set of all 'comp:id' attribute values of all such objects in the 
	  * model. Reference: L3V1 Comp V1 Section 3.9 
	  */
 	 public static final int COMP_10303 = 1010303; 

	 /**
	  * Error code 1010304:
	  * The value of a comp:id attribute must always conform to the syntax of the SBML 
	  * data type SId. Reference: L3V1 Core Section 3.1.7 
	  */
 	 public static final int COMP_10304 = 1010304; 

	 /**
	  * Error code 1010308:
	  * The value of a 'comp:submodelRef' attribute on &lt;replacedElement&gt; and 
	  * &lt;replacedBy&gt; objects must always conform to the syntax of the SBML data type 
	  * SId. Reference: L3V1 Comp V1 Section 3.6.2 
	  */
 	 public static final int COMP_10308 = 1010308; 

	 /**
	  * Error code 1010309:
	  * The value of a 'comp:deletion' attribute on &lt;replacedElement&gt; objects must 
	  * always conform to the syntax of the SBML data type SId. Reference: L3V1 Comp V1 
	  * Section 3.6.2 
	  */
 	 public static final int COMP_10309 = 1010309; 

	 /**
	  * Error code 1010310:
	  * The value of a 'comp:conversionFactor' attribute on &lt;replacedElement&gt; objects 
	  * must always conform to the syntax of the SBML data type SId. Reference: L3V1 
	  * Comp V1 Section 3.6.2 
	  */
 	 public static final int COMP_10310 = 1010310; 

	 /**
	  * Error code 1010311:
	  * The value of a 'comp:name' attribute must always conform to the syntax of type 
	  * string. Reference: L3V1 Core Section 3.1.1 
	  */
 	 public static final int COMP_10311 = 1010311; 

	 /**
	  * Error code 1010501:
	  * If one element replaces another, whether it is the target of a &lt;replacedBy&gt; 
	  * element, or whether it has a child &lt;replacedElement&gt;, the units of the replaced 
	  * element, multiplied by the units of any applicable conversion factor, should 
	  * equal the units of the replacement element. Reference: L3V1 Comp V1 Section 
	  * 3.6.5 
	  */
 	 public static final int COMP_10501 = 1010501; 

	 /**
	  * Error code 1020101:
	  * Any object derived from the extended SBase class (defined in the Hierarchical 
	  * Model Composition package) may contain at most one instance of a 
	  * &lt;listOfReplacedElements&gt; subobject. Reference: L3V1 Comp V1 Section 3.6 
	  */
 	 public static final int COMP_20101 = 1020101; 

	 /**
	  * Error code 1020102:
	  * Apart from the general notes and annotation subobjects permitted on all SBML 
	  * objects, a &lt;listOfReplacedElements&gt; container object may only contain 
	  * &lt;replacedElement&gt; objects. Reference: L3V1 Comp V1 Section 3.6 
	  */
 	 public static final int COMP_20102 = 1020102; 

	 /**
	  * Error code 1020103:
	  * A &lt;listOfReplacedElements&gt; object may have the optional attributes 'metaid' and 
	  * 'sboTerm' defined by SBML Level 3 Core. No other attributes from the SBML Level 
	  * 3 Core namespace or the HierarchicalModel Composition namespace are permitted on 
	  * a &lt;listOfReplacedElements&gt; object. Reference: L3V1 Comp V1 Section 3.6 
	  */
 	 public static final int COMP_20103 = 1020103; 

	 /**
	  * Error code 1020104:
	  * The &lt;listOfReplacedElements&gt; in an SBase object is optional, but if present, 
	  * must not be empty. Reference: L3V1 Comp V1 Section 3.6 
	  */
 	 public static final int COMP_20104 = 1020104; 

	 /**
	  * Error code 1020105:
	  * Any object derived from the extended SBase class (defined in the Hierarchical 
	  * Model Composition package) may contain at most one instance of a &lt;replacedBy&gt; 
	  * subobject. Reference: L3V1 Comp V1 Section 3.6 
	  */
 	 public static final int COMP_20105 = 1020105; 

	 /**
	  * Error code 1020201:
	  * In all SBML documents using the HierarchicalModel Composition package, the SBML 
	  * object must include a value for the attribute 'comp:required' attribute. 
	  * Reference: L3V1 Core Section 4.1.2 
	  */
 	 public static final int COMP_20201 = 1020201; 

	 /**
	  * Error code 1020202:
	  * The value of attribute 'comp:required' on the SBML object must be of the data 
	  * type Boolean. Reference: L3V1 Core Section 4.1.2 
	  */
 	 public static final int COMP_20202 = 1020202; 

	 /**
	  * Error code 1020203:
	  * The value of attribute 'comp:required' on the SBML object must be set to 'true' 
	  * if the Model object within the SBML object contains any Submodel with Species, 
	  * Parameter, Compartment, Reaction, or Event objects that have not been either 
	  * directly or indirectly replaced. Reference: L3V1 Comp V1 Section 3.1 
	  */
 	 public static final int COMP_20203 = 1020203; 

	 /**
	  * Error code 1020204:
	  * The value of attribute 'comp:required' on the SBML object must be set to 'false' 
	  * if the Model object within the SBML object contains no Submodel objects, or if 
	  * all Submodel objects that are present contain no Species, Parameter, 
	  * Compartment, Reaction, or Event objects that have not been either directly or 
	  * indirectly replaced. Reference: L3V1 Comp V1 Section 3.1 
	  */
 	 public static final int COMP_20204 = 1020204; 

	 /**
	  * Error code 1020205:
	  * There may be at most one instance of the &lt;listOfModelDefinitions&gt; within an SBML 
	  * object that uses the SBML Level 3 Hierarchical Model Composition package. 
	  * Reference: L3V1 Comp V1 Section 3.3 
	  */
 	 public static final int COMP_20205 = 1020205; 

	 /**
	  * Error code 1020206:
	  * The various 'ListOf' subobjects within an SBML object are optional, but if 
	  * present, these container objects must not be empty. Specifically, if any of the 
	  * following classes of objects is present within the SBML object, it must not be 
	  * empty: &lt;listOfModelDefinitions&gt; and &lt;listOfExternalModelDefinitions&gt;. Reference: 
	  * L3V1 Comp V1 Section 3.3 
	  */
 	 public static final int COMP_20206 = 1020206; 

	 /**
	  * Error code 1020207:
	  * Apart from the general notes and annotation subobjects permitted on all SBML 
	  * objects, a &lt;listOfModelDefinitions&gt; container may only contain &lt;modelDefinition&gt; 
	  * objects. Reference: L3V1 Comp V1 Section 3.3 
	  */
 	 public static final int COMP_20207 = 1020207; 

	 /**
	  * Error code 1020208:
	  * Apart from the general notes and annotation subobjects permitted on all SBML 
	  * objects, a &lt;listOfExternalModelDefinitions&gt; container may only contain 
	  * &lt;externalModelDefinition&gt; objects. Reference: L3V1 Comp V1 Section 3.3 
	  */
 	 public static final int COMP_20208 = 1020208; 

	 /**
	  * Error code 1020209:
	  * A &lt;listOfModelDefinitions&gt; object may have the optional attributes 'metaid' and 
	  * 'sboTerm'. No other attributes from the SBML Level 3 Core namespace or the 
	  * HierarchicalModel Composition namespace are permitted on a 
	  * &lt;listOfModelDefinitions&gt; object. Reference: L3V1 Comp V1 Section 3.3 
	  */
 	 public static final int COMP_20209 = 1020209; 

	 /**
	  * Error code 1020210:
	  * A &lt;listOfExternalModelDefinitions&gt; object may have the optional attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace 
	  * or the HierarchicalModel Composition namespace are permitted on a 
	  * &lt;listOfExternalModelDefinitions&gt; object. Reference: L3V1 Comp V1 Section 3.3 
	  */
 	 public static final int COMP_20210 = 1020210; 

	 /**
	  * Error code 1020211:
	  * There may be at most one instance of the &lt;listOfExternalModelDefinitions&gt; within 
	  * an SBML object that uses the SBML Level 3 Hierarchical Model Composition 
	  * package. Reference: L3V1 Comp V1 Section 3.3 
	  */
 	 public static final int COMP_20211 = 1020211; 

	 /**
	  * Error code 1020212:
	  * The value of attribute 'comp:required' on the SBML object must be set to 'true'. 
	  * Reference: L3V1 Comp V1 Section 3.1 
	  */
 	 public static final int COMP_20212 = 1020212; 

	 /**
	  * Error code 1020301:
	  * An &lt;externalModelDefinition&gt; object may have the optional SBML Level 3 Core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 
	  * Core namespace are permitted on an &lt;externalModelDefinition&gt; object. Reference: 
	  * L3V1 Comp V1 Section 3.2 
	  */
 	 public static final int COMP_20301 = 1020301; 

	 /**
	  * Error code 1020302:
	  * An &lt;externalModelDefinition&gt; object may have the optional SBML Level 3 Core 
	  * subobjects for notes and annotation. No other subobjects from the SBML Level 3 
	  * Core namespace or the Hierarchical Model Composition namespace are permitted on 
	  * an &lt;externalModelDefinition&gt; object. Reference: L3V1 Comp V1 Section 3.3.2 
	  */
 	 public static final int COMP_20302 = 1020302; 

	 /**
	  * Error code 1020303:
	  * An &lt;externalModelDefinition&gt; object must have the attributes 'comp:id' and 
	  * 'comp:source', and may have the optional attributes 'comp:name', 
	  * 'comp:modelRef', and 'comp:md5'. No other attributes from the Hierarchical Model 
	  * Composition namespace are permitted on an &lt;externalModelDefinition&gt; object. 
	  * Reference: L3V1 Comp V1 Section 3.3.2 
	  */
 	 public static final int COMP_20303 = 1020303; 

	 /**
	  * Error code 1020304:
	  * The value of the 'comp:source' attribute on an &lt;externalModelDefinition&gt; object 
	  * must reference an SBML Level 3 document. Reference: L3V1 Comp V1 Section 3.3.2 
	  */
 	 public static final int COMP_20304 = 1020304; 

	 /**
	  * Error code 1020305:
	  * The value of the 'comp:modelRef' attribute on an &lt;externalModelDefinition&gt; 
	  * object must be the value of an id attribute on a &lt;model&gt;, &lt;modelDefinition&gt;, or 
	  * &lt;externalModelDefinition&gt; object in the SBML document referenced by the 
	  * 'comp:source' attribute. Reference: L3V1 Comp V1 Section 3.3.2 
	  */
 	 public static final int COMP_20305 = 1020305; 

	 /**
	  * Error code 1020306:
	  * The value of the 'comp:md5' attribute, if present on an 
	  * &lt;externalModelDefinition&gt; object, should match the calculated MD5 checksum of 
	  * the SBML document referenced by the 'comp:source' attribute. Reference: L3V1 
	  * Comp V1 Section 3.3.2 
	  */
 	 public static final int COMP_20306 = 1020306; 

	 /**
	  * Error code 1020307:
	  * The value of a 'comp:source' attribute on an &lt;externalModelDefinition&gt; object 
	  * must always conform to the syntax of the XML Schema 1.0 data type 'anyURI'. 
	  * Reference: L3V1 Comp V1 Section 3.3.2 
	  */
 	 public static final int COMP_20307 = 1020307; 

	 /**
	  * Error code 1020308:
	  * The value of a comp:modelRef attribute on an &lt;externalModelDefinition&gt; object 
	  * must always conform to the syntax of the SBML data type SId. Reference: L3V1 
	  * Comp V1 Section 3.3.2 
	  */
 	 public static final int COMP_20308 = 1020308; 

	 /**
	  * Error code 1020309:
	  * The value of a comp:md5 attribute on an &lt;externalModelDefinition&gt; object must 
	  * always conform to the syntax of type string. Reference: L3V1 Comp V1 Section 
	  * 3.3.2 
	  */
 	 public static final int COMP_20309 = 1020309; 

	 /**
	  * Error code 1020310:
	  * An &lt;externalModelDefinition&gt; object must not reference an 
	  * &lt;externalModelDefinition&gt; in a different SBML document that, in turn, refers 
	  * back to the original &lt;externalModelDefinition object, whether directly or 
	  * indirectly through a chain of &lt;externalModelDefinition&gt; objects. Reference: L3V1 
	  * Comp V1 Section 3.3.2 
	  */
 	 public static final int COMP_20310 = 1020310; 

	 /**
	  * Error code 1020501:
	  * There may be at most one instance of each of the following kinds of objects 
	  * within a &lt;model&gt; or &lt;modelDefinition&gt; object using Hierarchical Model 
	  * Composition: &lt;listOfSubmodels&gt; and &lt;listOfPorts&gt;. Reference: L3V1 Comp V1 
	  * Section 3.4 
	  */
 	 public static final int COMP_20501 = 1020501; 

	 /**
	  * Error code 1020502:
	  * The various ListOf subobjects with a &lt;model&gt; object are optional, but if 
	  * present, these container object must not be empty. Specifically, if any of the 
	  * following classes of objects are present on the &lt;model&gt;, it must not be empty: 
	  * &lt;listOfSubmodels&gt; and &lt;listOfPorts&gt;. Reference: L3V1 Comp V1 Section 3.4 
	  */
 	 public static final int COMP_20502 = 1020502; 

	 /**
	  * Error code 1020503:
	  * Apart from the general notes and annotation subobjects permitted on all SBML 
	  * objects, a &lt;listOfSubmodels&gt; container object may only contain &lt;submodel&gt; 
	  * objects. Reference: L3V1 Comp V1 Section 3.4 
	  */
 	 public static final int COMP_20503 = 1020503; 

	 /**
	  * Error code 1020504:
	  * Apart from the general notes and annotation subobjects permitted on all SBML 
	  * objects, a &lt;listOfPorts&gt; container object may only contain &lt;port&gt; objects. 
	  * Reference: L3V1 Comp V1 Section 3.4 
	  */
 	 public static final int COMP_20504 = 1020504; 

	 /**
	  * Error code 1020505:
	  * A &lt;listOfSubmodels&gt; object may have the optional attributes 'metaid' and 
	  * 'sboTerm'. No other attributes from the SBML Level 3 Core namespace or the 
	  * HierarchicalModel Composition namespace are permitted on a &lt;listOfSubmodels&gt; 
	  * object. Reference: L3V1 Comp V1 Section 3.4 
	  */
 	 public static final int COMP_20505 = 1020505; 

	 /**
	  * Error code 1020506:
	  * A &lt;listOfPorts&gt; object may have the optional attributes 'metaid' and 'sboTerm'. 
	  * No other attributes from the SBML Level 3 Core namespace or the 
	  * HierarchicalModel Composition namespace are permitted on a &lt;listOfPorts&gt; object. 
	  * Reference: L3V1 Comp V1 Section 3.4 
	  */
 	 public static final int COMP_20506 = 1020506; 

	 /**
	  * Error code 1020601:
	  * A &lt;submodel&gt; object may have the optional SBML Level 3 Core attributes 'metaid' 
	  * and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are 
	  * permitted on a &lt;submodel&gt; object. Reference: L3V1 Core Section 3.2 
	  */
 	 public static final int COMP_20601 = 1020601; 

	 /**
	  * Error code 1020602:
	  * A &lt;submodel&gt; object may have the optional SBML Level 3 Core subobjects for notes 
	  * and annotations. No other elements from the SBML Level 3 Core namespace are 
	  * permitted on a &lt;submodel&gt; object. Reference: L3V1 Core Section 3.2 
	  */
 	 public static final int COMP_20602 = 1020602; 

	 /**
	  * Error code 1020603:
	  * There may be at most one &lt;listOfDeletions&gt; container object within a &lt;submodel&gt; 
	  * object. Reference: L3V1 Comp V1 Section 3.5 
	  */
 	 public static final int COMP_20603 = 1020603; 

	 /**
	  * Error code 1020604:
	  * A &lt;listOfDeletions&gt; container object within a &lt;submodel&gt; object is optional, but 
	  * if present, must not be empty. Reference: L3V1 Comp V1 Section 3.5 
	  */
 	 public static final int COMP_20604 = 1020604; 

	 /**
	  * Error code 1020605:
	  * Apart from the general notes and annotation subobjects permitted on all SBML 
	  * objects, a &lt;listOfDeletions&gt; container object may only contain &lt;deletion&gt; 
	  * objects. Reference: L3V1 Comp V1 Section 3.5 
	  */
 	 public static final int COMP_20605 = 1020605; 

	 /**
	  * Error code 1020606:
	  * A &lt;listOfDeletions&gt; object may have the optional attributes 'metaid' and 
	  * 'sboTerm' defined by SBML Level 3 Core. No other attributes from the SBML Level 
	  * 3 Core namespace or the HierarchicalModel Composition namespace are permitted on 
	  * a &lt;listOfReplacedElements&gt; object. Reference: L3V1 Comp V1 Section 3.5 
	  */
 	 public static final int COMP_20606 = 1020606; 

	 /**
	  * Error code 1020607:
	  * A &lt;submodel&gt; object must have the attributes 'comp:id' and 'comp:modelRef' 
	  * because they are required, and may also have the optional attributes 
	  * 'comp:name', 'comp:timeConversionFactor, and/or 'comp:extentConversionFactor'. 
	  * No other attributes from the Hierarchical Model Composition namespace are 
	  * permitted on a &lt;submodel&gt; object. Reference: L3V1 Comp V1 Section 3.5 
	  */
 	 public static final int COMP_20607 = 1020607; 

	 /**
	  * Error code 1020608:
	  * The value of a 'comp:modelRef' attribute on a &lt;submodel&gt; object must always 
	  * conform to the syntax of the SBML data type SId. Reference: L3V1 Comp V1 Section 
	  * 3.5.1 
	  */
 	 public static final int COMP_20608 = 1020608; 

	 /**
	  * Error code 1020613:
	  * The value of a 'comp:timeConversionFactor' attribute on a &lt;submodel&gt; object must 
	  * always conform to the syntax of the SBML data type SId. Reference: L3V1 Comp V1 
	  * Section 3.5.1 
	  */
 	 public static final int COMP_20613 = 1020613; 

	 /**
	  * Error code 1020614:
	  * The value of a 'comp:extentConversionFactor' attribute on a &lt;submodel&gt; object 
	  * must always conform to the syntax of the SBML data type SId. Reference: L3V1 
	  * Comp V1 Section 3.5.1 
	  */
 	 public static final int COMP_20614 = 1020614; 

	 /**
	  * Error code 1020615:
	  * The value of a 'comp:modelRef' attribute on a &lt;submodel&gt; must be the identifier 
	  * of a &lt;model&gt;, &lt;modelDefinition&gt;, or &lt;externalModelDefinition&gt; object in the same 
	  * SBML object as the &lt;submodel&gt;. Reference: L3V1 Comp V1 Section 3.5.1 
	  */
 	 public static final int COMP_20615 = 1020615; 

	 /**
	  * Error code 1020616:
	  * A &lt;model&gt; or &lt;modelDefinition&gt; object must not contain a &lt;submodel&gt; which 
	  * references that model object itself. That is, the value of a 'comp:modelRef' 
	  * attribute on a &lt;submodel&gt; must not be the value of the parent &lt;model&gt; or 
	  * &lt;modelDefinition&gt;'s 'id' attribute. Reference: L3V1 Comp V1 Section 3.5.1 
	  */
 	 public static final int COMP_20616 = 1020616; 

	 /**
	  * Error code 1020617:
	  * A &lt;model&gt; object must not contain a &lt;submodel&gt; which references that &lt;model&gt; 
	  * indirectly. That is, the 'comp:modelRef' attribute of a &lt;submodel&gt; may not point 
	  * to the 'id' of a &lt;model&gt; containing a &lt;submodel&gt; object that references the 
	  * original &lt;model&gt; directly or indirectly through a chain of &lt;model&gt;/&lt;submodel&gt; 
	  * pairs. Reference: L3V1 Comp V1 Section 3.5.1 
	  */
 	 public static final int COMP_20617 = 1020617; 

	 /**
	  * Error code 1020622:
	  * The value of a 'comp:timeConversionFactor' attribute on a given &lt;submodel&gt; 
	  * object must be the identifier of a &lt;parameter&gt; object defined in the same Model 
	  * containing the &lt;submodel&gt;. Reference: L3V1 Comp V1 Section 3.5.1 
	  */
 	 public static final int COMP_20622 = 1020622; 

	 /**
	  * Error code 1020623:
	  * The value of a 'comp:extentConversionFactor' attribute on a given &lt;submodel&gt; 
	  * object must be the identifier of a &lt;parameter&gt; object defined in the same Model 
	  * containing the &lt;submodel&gt;. Reference: L3V1 Comp V1 Section 3.5.1 
	  */
 	 public static final int COMP_20623 = 1020623; 

	 /**
	  * Error code 1020701:
	  * The value of a 'comp:portRef' attribute on an &lt;sBaseRef&gt; object must be the 
	  * identifier of a &lt;port&gt; object in the &lt;model&gt; referenced by that &lt;sBaseRef&gt;. 
	  * Reference: L3V1 Comp V1 Section 3.7.1 
	  */
 	 public static final int COMP_20701 = 1020701; 

	 /**
	  * Error code 1020702:
	  * The value of a 'comp:idRef' attribute on an &lt;sBaseRef&gt; object must be the 
	  * identifier of an object contained in (that is, within the SId namespace of) the 
	  * &lt;model&gt; referenced by that &lt;sBaseRef&gt;. This includes objects with 'id' 
	  * attributes defined in packages other than SBML Level 3 Core or the Hierarchical 
	  * Model Composition package. Reference: L3V1 Comp V1 Section 3.7.1 
	  */
 	 public static final int COMP_20702 = 1020702; 

	 /**
	  * Error code 1020703:
	  * The value of a 'comp:unitRef' attribute on an &lt;sBaseRef&gt; object must be the 
	  * identifier of a &lt;unitDefinition&lt; object contained in the &lt;model&gt; referenced by 
	  * that &lt;sBaseRef&gt;. Reference: L3V1 Comp V1 Section 3.7.1 
	  */
 	 public static final int COMP_20703 = 1020703; 

	 /**
	  * Error code 1020704:
	  * The value of a 'comp:metaIdRef' attribute on an &lt;sBaseRef&gt; object must be the 
	  * value of a 'comp:metaid' attribute on an element contained in the &lt;model&gt; 
	  * referenced by that &lt;sBaseRef&gt;. Reference: L3V1 Comp V1 Section 3.7.1 
	  */
 	 public static final int COMP_20704 = 1020704; 

	 /**
	  * Error code 1020705:
	  * If an &lt;sBaseRef&gt; object contains an &lt;sBaseRef&gt; child, the parent &lt;sBaseRef&gt; must 
	  * point to a &lt;submodel&gt; object, or a &lt;port&gt; that itself points to a &lt;submodel&gt; 
	  * object. Reference: L3V1 Comp V1 Section 3.7.2 
	  */
 	 public static final int COMP_20705 = 1020705; 

	 /**
	  * Error code 1020706:
	  * The value of a 'comp:portRef' attribute on an SBaseRef object must always 
	  * conform to the syntax of the SBML data type SId. Reference: L3V1 Comp V1 Section 
	  * 3.7.1 
	  */
 	 public static final int COMP_20706 = 1020706; 

	 /**
	  * Error code 1020707:
	  * The value of a 'comp:idRef' attribute on an SBaseRef object must always conform 
	  * to the syntax of the SBML data type SId. Reference: L3V1 Comp V1 Section 3.7.1 
	  */
 	 public static final int COMP_20707 = 1020707; 

	 /**
	  * Error code 1020708:
	  * The value of a 'comp:unitRef' attribute on an SBaseRef object must always 
	  * conform to the syntax of the SBML data type SId. Reference: L3V1 Comp V1 Section 
	  * 3.7.1 
	  */
 	 public static final int COMP_20708 = 1020708; 

	 /**
	  * Error code 1020709:
	  * The value of a 'comp:metaIdRef' attribute on an SBaseRef object must always 
	  * conform to the syntax of the XML data type ID. Reference: L3V1 Comp V1 Section 
	  * 3.7.1 
	  */
 	 public static final int COMP_20709 = 1020709; 

	 /**
	  * Error code 1020710:
	  * Apart from the general notes and annotation subobjects permitted on all SBML 
	  * objects, an SBaseRef object may only contain a single &lt;sBaseRef&gt; child. 
	  * Reference: L3V1 Comp V1 Section 3.7.2 
	  */
 	 public static final int COMP_20710 = 1020710; 

	 /**
	  * Error code 1020711:
	  * The 'sbaseRef' spelling of an SBaseRef child of an SBaseRef object is considered 
	  * deprecated, and 'sBaseRef' should be used instead. Reference: L3V1 Comp V1 
	  * Section 3.7.2 
	  */
 	 public static final int COMP_20711 = 1020711; 

	 /**
	  * Error code 1020712:
	  * An &lt;sBaseRef&gt; object must point to another object; that is, a &lt;sBaseRef&gt; object 
	  * must always have a value for one of the attributes 'comp:portRef', 'comp:idRef', 
	  * 'comp:unitRef', or 'comp:metaIdRef'. Reference: L3V1 Comp V1 Section 3.7.1 
	  */
 	 public static final int COMP_20712 = 1020712; 

	 /**
	  * Error code 1020713:
	  * An &lt;sBaseRef&gt; object can only point to one other object; that is, a given 
	  * &lt;sBaseRef&gt; object can only have a value for one of the attributes 
	  * 'comp:portRef', 'comp:idRef', 'comp:unitRef', or 'comp:metaIdRef'. Reference: 
	  * L3V1 Comp V1 Section 3.7.1 
	  */
 	 public static final int COMP_20713 = 1020713; 

	 /**
	  * Error code 1020714:
	  * Any one SBML object may only be referenced in one of the following ways: 
	  * referenced by a single &lt;port&gt; object; referenced by a single &lt;deletion&gt; object; 
	  * referenced by a single &lt;replacedElement&gt;; be the parent of a single &lt;replacedBy&gt; 
	  * child; be referenced by one or more &lt;replacedBy&gt; objects; or be referenced by 
	  * one or more &lt;replacedElement&gt; objects all using the 'deletion' attribute. 
	  * Essentially, once an object has been referenced in one of these ways it cannot 
	  * be referenced again. Reference: L3V1 Comp V1 Section 3.7.1 
	  */
 	 public static final int COMP_20714 = 1020714; 

	 /**
	  * Error code 1020801:
	  * A &lt;port&gt; object must point to another object; that is, a &lt;port&gt; object must 
	  * always have a value for one of the attributes 'comp:idRef', 'comp:unitRef', or 
	  * 'comp:metaIdRef'. Reference: L3V1 Comp V1 Section 3.4.3 
	  */
 	 public static final int COMP_20801 = 1020801; 

	 /**
	  * Error code 1020802:
	  * A &lt;port&gt; object can only point to one other object; that is, a given &lt;port&gt; 
	  * object can only have a value for one of the attributes 'comp:idRef', 
	  * 'comp:unitRef', or 'comp:metaIdRef'. Reference: L3V1 Comp V1 Section 3.4.3 
	  */
 	 public static final int COMP_20802 = 1020802; 

	 /**
	  * Error code 1020803:
	  * A &lt;port&gt; object must have a value for the required attribute 'comp:id', and one, 
	  * and only one, of the attributes 'comp:idRef', 'comp:unitRef', or 
	  * 'comp:metaIdRef'. No other attributes from the Hierarchical Model Composition 
	  * namespace are permitted on a &lt;port&gt; object. Reference: L3V1 Comp V1 Section 
	  * 3.4.3 
	  */
 	 public static final int COMP_20803 = 1020803; 

	 /**
	  * Error code 1020804:
	  * Port definitions must be unique; that is, no two &lt;port&gt; objects in a given Model 
	  * may reference the same object in that Model. Reference: L3V1 Comp V1 Section 
	  * 3.4.3 
	  */
 	 public static final int COMP_20804 = 1020804; 

	 /**
	  * Error code 1020901:
	  * A &lt;deletion&gt; object must point to another object; that is, a &lt;deletion&gt; object 
	  * must always have a value for one of the attributes 'comp:portRef', 'comp:idRef', 
	  * 'comp:unitRef', or 'comp:metaIdRef' Reference: L3V1 Comp V1 Section 3.5.3 
	  */
 	 public static final int COMP_20901 = 1020901; 

	 /**
	  * Error code 1020902:
	  * A &lt;deletion&gt; object can only point to one other object; that is, a given 
	  * &lt;deletion&gt; object can only have a value for one of the attributes 
	  * 'comp:portRef', 'comp:idRef', 'comp:unitRef', or 'comp:metaIdRef' Reference: 
	  * L3V1 Comp V1 Section 3.5.3 
	  */
 	 public static final int COMP_20902 = 1020902; 

	 /**
	  * Error code 1020903:
	  * A &lt;deletion&gt; object must have a value for one, and only one, of the attributes 
	  * 'comp:portRef', 'comp:idRef', 'comp:unitRef', and 'comp:metaIdRef'. It may also 
	  * have the optional attributes 'comp:id' and 'comp:name'. No other attributes from 
	  * the Hierarchical Model Composition namespace are permitted on a &lt;deletion&gt; 
	  * object. Reference: L3V1 Comp V1 Section 3.5.3 
	  */
 	 public static final int COMP_20903 = 1020903; 

	 /**
	  * Error code 1021001:
	  * A &lt;replacedElement&gt; object must point to another object; that is, a 
	  * &lt;replacedElement&gt; object must always have a value for one of the attributes 
	  * 'comp:portRef', 'comp:idRef', 'comp:unitRef', 'comp:metaIdRef' or 
	  * 'comp:deletion'. Reference: L3V1 Comp V1 Section 3.6.2 
	  */
 	 public static final int COMP_21001 = 1021001; 

	 /**
	  * Error code 1021002:
	  * A &lt;replacedElement&gt; object can only point to one other object; that is, a given 
	  * &lt;replacedElement&gt; object can only have a value for one of the attributes 
	  * 'comp:portRef', 'comp:idRef', 'comp:unitRef', 'comp:metaIdRef' or 
	  * 'comp:deletion'. Reference: L3V1 Comp V1 Section 3.6.2 
	  */
 	 public static final int COMP_21002 = 1021002; 

	 /**
	  * Error code 1021003:
	  * A &lt;replacedElement&gt; object must have a value for the required attribute 
	  * 'comp:submodelRef', and a value for one, and only one, of the following 
	  * attributes: 'comp:portRef', 'comp:idRef', 'comp:unitRef', 'comp:metaIdRef', or 
	  * 'comp:deletion'. It may also have a value for the optional attribute 
	  * 'comp:conversionFactor'. No other attributes from the HierarchicalModel 
	  * Composition namespace are permitted on a &lt;replacedElement&gt; object. Reference: 
	  * L3V1 Comp V1 Section 3.6.2 
	  */
 	 public static final int COMP_21003 = 1021003; 

	 /**
	  * Error code 1021004:
	  * The value of a 'comp:submodelRef' attribute on a &lt;replacedElement&gt; object must 
	  * be the identifier of a &lt;submodel&gt; present in the &lt;replacedElement&gt; object's 
	  * parent Model. Reference: L3V1 Comp V1 Section 3.6.2 
	  */
 	 public static final int COMP_21004 = 1021004; 

	 /**
	  * Error code 1021005:
	  * The value of a 'comp:deletion' attribute on a &lt;replacedElement&gt; object must be 
	  * the identifier of a &lt;deletion&gt; present in the &lt;replacedElement&gt; object's parent 
	  * Model. Reference: L3V1 Comp V1 Section 3.6.2 
	  */
 	 public static final int COMP_21005 = 1021005; 

	 /**
	  * Error code 1021006:
	  * The value of a 'comp:conversionFactor' attribute on a &lt;replacedElement&gt; object 
	  * must be the identifier of a &lt;parameter&gt; present in the &lt;replacedElement&gt; 
	  * object's parent Model Reference: L3V1 Comp V1 Section 3.6.2 
	  */
 	 public static final int COMP_21006 = 1021006; 

	 /**
	  * Error code 1021010:
	  * No two &lt;replacedElement&gt; objects in the same Model may reference the same object 
	  * unless that object is a &lt;deletion&gt; Reference: L3V1 Comp V1 Section 3.6.2 
	  */
 	 public static final int COMP_21010 = 1021010; 

	 /**
	  * Error code 1021011:
	  * If a &lt;replacedElement&gt; uses the 'comp:deletion' attribute, then it should not 
	  * also use the 'comp:conversionFactor' attribute. Reference: L3V1 Comp V1 Section 
	  * 3.6.2 
	  */
 	 public static final int COMP_21011 = 1021011; 

	 /**
	  * Error code 1021101:
	  * A &lt;replacedBy&gt; object must point to another object; that is, a &lt;replacedBy&gt; 
	  * object must always have a value for one of the attributes 'comp:portRef', 
	  * 'comp:idRef', 'comp:unitRef' or 'comp:metaIdRef'. Reference: L3V1 Comp V1 
	  * Section 3.6.4 
	  */
 	 public static final int COMP_21101 = 1021101; 

	 /**
	  * Error code 1021102:
	  * A &lt;replacedBy&gt; object can only point to one other object; that is, a given 
	  * &lt;replacedBy&gt; object can only have a value for one of the attributes 
	  * 'comp:portRef', 'comp:idRef', 'comp:unitRef', or 'comp:metaIdRef'. Reference: 
	  * L3V1 Comp V1 Section 3.6.4 
	  */
 	 public static final int COMP_21102 = 1021102; 

	 /**
	  * Error code 1021103:
	  * A &lt;replacedBy&gt; object must have a value for the required attribute 
	  * 'comp:submodelRef', and a value for one, and only one, of the following 
	  * attributes: 'comp:portRef', 'comp:idRef', 'comp:unitRef' or 'comp:metaIdRef'. No 
	  * other attributes from the HierarchicalModel Composition namespace are permitted 
	  * on a &lt;replacedBy&gt; object. Reference: L3V1 Comp V1 Section 3.6.4 
	  */
 	 public static final int COMP_21103 = 1021103; 

	 /**
	  * Error code 1021104:
	  * The value of a 'comp:submodelRef' attribute on a &lt;replacedBy&gt; object must be the 
	  * identifier of a &lt;submodel&gt; present in the &lt;replacedBy&gt; object's parent Model. 
	  * Reference: L3V1 Comp V1 Section 3.6.4 
	  */
 	 public static final int COMP_21104 = 1021104; 

	 /**
	  * Error code 1021201:
	  * If one element replaces another, whether it is the target of a &lt;replacedBy&gt; 
	  * element, or whether it has a child &lt;replacedElement&gt;, the SBML class of the 
	  * replacement element must match the SBML class of the replaced element, with two 
	  * exceptions: an element of a derived class may replace an object of its base 
	  * class (for base classes other than SBase), and any SBML class with mathematical 
	  * meaning may replace a &lt;parameter&gt;. A base class may not replace a derived class, 
	  * however, nor may a &lt;parameter&gt; replace some other SBML element with mathematical 
	  * meaning. Reference: L3V1 Comp V1 Section 3.6.5 
	  */
 	 public static final int COMP_21201 = 1021201; 

	 /**
	  * Error code 1021202:
	  * If one element replaces another, whether it is the target of a &lt;replacedBy&gt; 
	  * element, or whether it has a child &lt;replacedElement&gt;, if the replaced element 
	  * has the 'id' attribute set, the replacement !element must also have the 'id' 
	  * attribute set. Reference: L3V1 Comp V1 Section 3.6.5 
	  */
 	 public static final int COMP_21202 = 1021202; 

	 /**
	  * Error code 1021203:
	  * If one element replaces another, whether it is the target of a &lt;replacedBy&gt; 
	  * element, or whether it has a child &lt;replacedElement&gt;, if the replaced element 
	  * has the 'metaid' attribute set, the replacement element must also have the 
	  * 'metaid' attribute set. Reference: L3V1 Comp V1 Section 3.6.5 
	  */
 	 public static final int COMP_21203 = 1021203; 

	 /**
	  * Error code 1021204:
	  * If one element replaces another, whether it is the target of a &lt;replacedBy&gt; 
	  * element, or whether it has a child &lt;replacedElement&gt;, if the replaced element 
	  * has an identifier attribute from some other SBML package set, the replacement 
	  * element must also have that same identifier attribute set. Reference: L3V1 Comp 
	  * V1 Section 3.6.5 
	  */
 	 public static final int COMP_21204 = 1021204; 

	 /**
	  * Error code 1090101:
	  * The external model referenced in this model could not be resolved. 
	  */
 	 public static final int COMP_90101 = 1090101; 

	 /**
	  * Error code 1090102:
	  * The external document referenced in this model did not contain any models. 
	  */
 	 public static final int COMP_90102 = 1090102; 

	 /**
	  * Error code 1090103:
	  * An external model definition referenced by an external model definition in this 
	  * model was itself unresolvable. 
	  */
 	 public static final int COMP_90103 = 1090103; 

	 /**
	  * Error code 1090104:
	  * Errors arose during the attempt to flatten the model. 
	  */
 	 public static final int COMP_90104 = 1090104; 

	 /**
	  * Error code 1090105:
	  * The interpretation the Hierarchical Model Composition constructs to produce a 
	  * kind of 'flattened' version of the model devoid of the comp package constructs 
	  * must produce a valid SBML Level 3 model. Reference: L3V1 Comp V1 Appendix A1 
	  */
 	 public static final int COMP_90105 = 1090105; 

	 /**
	  * Error code 1090106:
	  * Due to the need to instantiate models, modelDefinitions, submodels etc. for the 
	  * purposes of validation it is problematic to reliably report line numbers when 
	  * performing validation on models using the Hierarchical Model Composition 
	  * package. 
	  */
 	 public static final int COMP_90106 = 1090106; 

	 /**
	  * Error code 1090107:
	  * The CompFlatteningConverter has encountered a required package for which libSBML 
	  * does not recognize the information. 
	  */
 	 public static final int COMP_90107 = 1090107; 

	 /**
	  * Error code 1090108:
	  * The CompFlatteningConverter has encountered an unrequired package for which 
	  * libSBML does not recognize the information. 
	  */
 	 public static final int COMP_90108 = 1090108; 

	 /**
	  * Error code 1090109:
	  * The CompFlatteningConverter has encountered an unrequired package for which the 
	  * necessary routines to allow flattening have not yet been implemented. 
	  */
 	 public static final int COMP_90109 = 1090109; 

	 /**
	  * Error code 1090110:
	  * The CompFlatteningConverter has encountered a required package for which the 
	  * necessary routines to allow flattening have not yet been implemented. 
	  */
 	 public static final int COMP_90110 = 1090110; 

	 /**
	  * Error code 1090111:
	  * The CompFlatteningConverter has encountered an unknown reference which may be 
	  * due to the presence of an unknown package. 
	  */
 	 public static final int COMP_90111 = 1090111; 

	 /**
	  * Error code 1090112:
	  * The software used to process this hierarchical model used the deprecated 
	  * function performDeletions to do so. Unfortunately, it is impossible to properly 
	  * use this function as it was originally designed, without some models either 
	  * causing the program to crash, or causing them to be interpreted incorrectly. 
	  * Instead, the software should use collectDeletionsAndDeleteCompConstructs, in 
	  * conjunction with collectRenameAndConvertReplacements and removeCollectedElements 
	  * to properly process hierarchical models. 
	  */
 	 public static final int COMP_90112 = 1090112; 

	 /**
	  * Error code 1090113:
	  * The software used to process this hierarchical model used the deprecated 
	  * function performReplacementsAndConversions to do so. Unfortunately, it is 
	  * impossible to properly use this function as it was originally designed, without 
	  * some models either causing the program to crash, or causing them to be 
	  * interpreted incorrectly. Instead, the software should use 
	  * collectDeletionsAndDeleteCompConstructs, in conjunction with 
	  * collectRenameAndConvertReplacements and removeCollectedElements to properly 
	  * process hierarchical models. 
	  */
 	 public static final int COMP_90113 = 1090113; 

	 /**
	  * Error code 1090114:
	  * The model contained a deletion whose subelement was replaced. This is perfectly 
	  * legal, but unfortunately, the subroutine used to implement this actually removed 
	  * the deleted element and all of its children before replacing the child, making 
	  * it impossible to discover any IDs that need to be replaced. 
	  */
 	 public static final int COMP_90114 = 1090114; 

	 /**
	  * Error code 1090115:
	  * The value of a 'comp:idRef' attribute on an &lt;sBaseRef&gt; object must be the 
	  * identifier of an object contained in (that is, within the SId namespace of) the 
	  * &lt;model&gt; referenced by that &lt;sBaseRef&gt;. This includes objects with 'id' 
	  * attributes defined in packages other than SBML Level 3 Core or the Hierarchical 
	  * Model Composition package. 
	  */
 	 public static final int COMP_90115 = 1090115; 

	 /**
	  * Error code 1090116:
	  * The value of a 'comp:metaIdRef' attribute on an &lt;sBaseRef&gt; object must be the 
	  * value of a 'comp:metaid' attribute on an element contained in the &lt;model&gt; 
	  * referenced by that &lt;sBaseRef&gt;. 
	  */
 	 public static final int COMP_90116 = 1090116; 

	 /**
	  * Error code 1210101:
	  * To conform to the Spatial Processes Package specification for SBML Level 3 
	  * Version 1, an SBML document must declare 
	  * 'http://www.sbml.org/sbml/level3/version1/spatial/version1' as the XMLNamespace 
	  * to use for elements of this package. Reference: L3V1 Spatial V1 Section 3.1 
	  */
 	 public static final int SPATIAL_10101 = 1210101; 

	 /**
	  * Error code 1210102:
	  * Wherever they appear in an SBML document, elements and attributes from the 
	  * Spatial Processes Package must use the 
	  * 'http://www.sbml.org/sbml/level3/version1/spatial/version1' namespace, declaring 
	  * so either explicitly or implicitly. Reference: L3V1 Spatial V1 Section 3.1 
	  */
 	 public static final int SPATIAL_10102 = 1210102; 

	 /**
	  * Error code 1210301:
	  * (Extends validation rule #10301 in the SBML Level 3 Core specification. TO DO 
	  * list scope of ids) Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_10301 = 1210301; 

	 /**
	  * Error code 1210302:
	  * The value of a 'spatial:id' must conform to the syntax of the &lt;sbml&gt; data type 
	  * 'SId' Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_10302 = 1210302; 

	 /**
	  * Error code 1220101:
	  * In all SBML documents using the Spatial Processes Package, the &lt;sbml&gt; object 
	  * must have the 'spatial:required' attribute. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_20101 = 1220101; 

	 /**
	  * Error code 1220102:
	  * The value of attribute 'spatial:required' on the &lt;sbml&gt; object must be of data 
	  * type 'boolean'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_20102 = 1220102; 

	 /**
	  * Error code 1220103:
	  * The value of attribute 'spatial:required' on the &lt;sbml&gt; object must be set to 
	  * 'true'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_20103 = 1220103; 

	 /**
	  * Error code 1220201:
	  * A &lt;model&gt; object may contain one and only one instance of the &lt;geometry&gt; 
	  * element. No other elements from the SBML Level 3 Spatial Processes namespaces 
	  * are permitted on a &lt;model&gt; object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_20201 = 1220201; 

	 /**
	  * Error code 1220301:
	  * A &lt;compartment&gt; object may contain one and only one instance of the 
	  * &lt;compartmentMapping&gt; element. No other elements from the SBML Level 3 Spatial 
	  * Processes namespaces are permitted on a &lt;compartment&gt; object. Reference: L3V1 
	  * Spatial V1 Section 
	  */
 	 public static final int SPATIAL_20301 = 1220301; 

	 /**
	  * Error code 1220401:
	  * A &lt;species&gt; object may have the optional attribute 'spatial:isSpatial'. No other 
	  * attributes from the SBML Level 3 Spatial Processes namespaces are permitted on a 
	  * &lt;species&gt; object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_20401 = 1220401; 

	 /**
	  * Error code 1220402:
	  * The attribute 'spatial:isSpatial' on a &lt;species&gt; must have a value of data type 
	  * 'boolean'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_20402 = 1220402; 

	 /**
	  * Error code 1220501:
	  * A &lt;parameter&gt; object may contain one and only one instance of each of the 
	  * &lt;spatialSymbolReference,&gt; &lt;advectionCoefficient,&gt; &lt;boundaryCondition&gt; and 
	  * &lt;diffusionCoefficient&gt; elements. No other elements from the SBML Level 3 Spatial 
	  * Processes namespaces are permitted on a &lt;parameter&gt; object. Reference: L3V1 
	  * Spatial V1 Section 
	  */
 	 public static final int SPATIAL_20501 = 1220501; 

	 /**
	  * Error code 1220601:
	  * A &lt;reaction&gt; object must have the required attribute 'spatial:isLocal'. No other 
	  * attributes from the SBML Level 3 Spatial Processes namespaces are permitted on a 
	  * &lt;reaction&gt; object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_20601 = 1220601; 

	 /**
	  * Error code 1220602:
	  * The attribute 'spatial:isLocal' on a &lt;reaction&gt; must have a value of data type 
	  * 'boolean'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_20602 = 1220602; 

	 /**
	  * Error code 1220701:
	  * A &lt;domainType&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;domainType&gt;. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_20701 = 1220701; 

	 /**
	  * Error code 1220702:
	  * A &lt;domainType&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespaces 
	  * are permitted on a &lt;domainType&gt;. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_20702 = 1220702; 

	 /**
	  * Error code 1220703:
	  * A &lt;domainType&gt; object must have the required attributes 'spatial:id' and 
	  * 'spatial:spatialDimensions', and may have the optional attribute 'spatial:name'. 
	  * No other attributes from the SBML Level 3 Spatial Processes namespaces are 
	  * permitted on a &lt;domainType&gt; object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_20703 = 1220703; 

	 /**
	  * Error code 1220704:
	  * The attribute 'spatial:spatialDimensions' on a &lt;domainType&gt; must have a value of 
	  * data type 'integer'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_20704 = 1220704; 

	 /**
	  * Error code 1220705:
	  * The attribute 'spatial:name' on a &lt;domainType&gt; must have a value of data type 
	  * 'string'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_20705 = 1220705; 

	 /**
	  * Error code 1220801:
	  * A &lt;domain&gt; object may have the optional SBML Level 3 Core attributes 'metaid' 
	  * and 'sboTerm'. No other attributes from the SBML Level 3 Core namespaces are 
	  * permitted on a &lt;domain&gt;. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_20801 = 1220801; 

	 /**
	  * Error code 1220802:
	  * A &lt;domain&gt; object may have the optional SBML Level 3 Core subobjects for notes 
	  * and annotations. No other elements from the SBML Level 3 Core namespaces are 
	  * permitted on a &lt;domain&gt;. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_20802 = 1220802; 

	 /**
	  * Error code 1220803:
	  * A &lt;domain&gt; object must have the required attributes 'spatial:id' and 
	  * 'spatial:domainType', and may have the optional attribute 'spatial:name'. No 
	  * other attributes from the SBML Level 3 Spatial Processes namespaces are 
	  * permitted on a &lt;domain&gt; object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_20803 = 1220803; 

	 /**
	  * Error code 1220804:
	  * A &lt;domain&gt; object may contain one and only one instance of the 
	  * &lt;listOfInteriorPoints&gt; element. No other elements from the SBML Level 3 Spatial 
	  * Processes namespaces are permitted on a &lt;domain&gt; object. Reference: L3V1 Spatial 
	  * V1 Section 
	  */
 	 public static final int SPATIAL_20804 = 1220804; 

	 /**
	  * Error code 1220805:
	  * The value of the attribute 'spatial:domainType' of a &lt;domain&gt; object must be the 
	  * identifier of an existing &lt;domainType&gt; object defined in the enclosing &lt;model&gt; 
	  * object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_20805 = 1220805; 

	 /**
	  * Error code 1220806:
	  * The attribute 'spatial:name' on a &lt;domain&gt; must have a value of data type 
	  * 'string'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_20806 = 1220806; 

	 /**
	  * Error code 1220807:
	  * Apart from the general notes and annotations subobjects permitted on all SBML 
	  * objects, a &lt;listOfInteriorPoints&gt; container object may only contain 
	  * &lt;interiorPoint&gt; objects. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_20807 = 1220807; 

	 /**
	  * Error code 1220808:
	  * A &lt;listOfInteriorPoints&gt; object may have the optional SBML Level 3 Core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;listOfInteriorPoints&gt; object. Reference: 
	  * L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_20808 = 1220808; 

	 /**
	  * Error code 1220901:
	  * An &lt;interiorPoint&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on an &lt;interiorPoint&gt;. Reference: L3V1 Spatial V1 
	  * Section 
	  */
 	 public static final int SPATIAL_20901 = 1220901; 

	 /**
	  * Error code 1220902:
	  * An &lt;interiorPoint&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespaces 
	  * are permitted on an &lt;interiorPoint&gt;. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_20902 = 1220902; 

	 /**
	  * Error code 1220903:
	  * An &lt;interiorPoint&gt; object must have the required attribute 'spatial:coordOne', 
	  * and may have the optional attributes 'spatial:coordTwo' and 
	  * 'spatial:coordThree'. No other attributes from the SBML Level 3 Spatial 
	  * Processes namespaces are permitted on an &lt;interiorPoint&gt; object. Reference: L3V1 
	  * Spatial V1 Section 
	  */
 	 public static final int SPATIAL_20903 = 1220903; 

	 /**
	  * Error code 1220904:
	  * The attribute 'spatial:coordOne' on an &lt;interiorPoint&gt; must have a value of data 
	  * type 'double'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_20904 = 1220904; 

	 /**
	  * Error code 1220905:
	  * The attribute 'spatial:coordTwo' on an &lt;interiorPoint&gt; must have a value of data 
	  * type 'double'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_20905 = 1220905; 

	 /**
	  * Error code 1220906:
	  * The attribute 'spatial:coordThree' on an &lt;interiorPoint&gt; must have a value of 
	  * data type 'double'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_20906 = 1220906; 

	 /**
	  * Error code 1221001:
	  * A &lt;boundary&gt; object may have the optional SBML Level 3 Core attributes 'metaid' 
	  * and 'sboTerm'. No other attributes from the SBML Level 3 Core namespaces are 
	  * permitted on a &lt;boundary&gt;. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21001 = 1221001; 

	 /**
	  * Error code 1221002:
	  * A &lt;boundary&gt; object may have the optional SBML Level 3 Core subobjects for notes 
	  * and annotations. No other elements from the SBML Level 3 Core namespaces are 
	  * permitted on a &lt;boundary&gt;. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21002 = 1221002; 

	 /**
	  * Error code 1221003:
	  * A &lt;boundary&gt; object must have the required attributes 'spatial:id' and 
	  * 'spatial:value', and may have the optional attribute 'spatial:name'. No other 
	  * attributes from the SBML Level 3 Spatial Processes namespaces are permitted on a 
	  * &lt;boundary&gt; object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21003 = 1221003; 

	 /**
	  * Error code 1221004:
	  * The attribute 'spatial:value' on a &lt;boundary&gt; must have a value of data type 
	  * 'double'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21004 = 1221004; 

	 /**
	  * Error code 1221005:
	  * The attribute 'spatial:name' on a &lt;boundary&gt; must have a value of data type 
	  * 'string'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21005 = 1221005; 

	 /**
	  * Error code 1221101:
	  * An &lt;adjacentDomains&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on an &lt;adjacentDomains&gt;. Reference: L3V1 Spatial V1 
	  * Section 
	  */
 	 public static final int SPATIAL_21101 = 1221101; 

	 /**
	  * Error code 1221102:
	  * An &lt;adjacentDomains&gt; object may have the optional SBML Level 3 Core subobjects 
	  * for notes and annotations. No other elements from the SBML Level 3 Core 
	  * namespaces are permitted on an &lt;adjacentDomains&gt;. Reference: L3V1 Spatial V1 
	  * Section 
	  */
 	 public static final int SPATIAL_21102 = 1221102; 

	 /**
	  * Error code 1221103:
	  * An &lt;adjacentDomains&gt; object must have the required attributes 'spatial:id', 
	  * 'spatial:domainOne' and 'spatial:domainTwo', and may have the optional attribute 
	  * 'spatial:name'. No other attributes from the SBML Level 3 Spatial Processes 
	  * namespaces are permitted on an &lt;adjacentDomains&gt; object. Reference: L3V1 Spatial 
	  * V1 Section 
	  */
 	 public static final int SPATIAL_21103 = 1221103; 

	 /**
	  * Error code 1221104:
	  * The value of the attribute 'spatial:domainOne' of an &lt;adjacentDomains&gt; object 
	  * must be the identifier of an existing &lt;domain&gt; object defined in the enclosing 
	  * &lt;model&gt; object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21104 = 1221104; 

	 /**
	  * Error code 1221105:
	  * The value of the attribute 'spatial:domainTwo' of an &lt;adjacentDomains&gt; object 
	  * must be the identifier of an existing &lt;domain&gt; object defined in the enclosing 
	  * &lt;model&gt; object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21105 = 1221105; 

	 /**
	  * Error code 1221106:
	  * The attribute 'spatial:name' on an &lt;adjacentDomains&gt; must have a value of data 
	  * type 'string'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21106 = 1221106; 

	 /**
	  * Error code 1221201:
	  * A &lt;geometryDefinition&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;geometryDefinition&gt;. Reference: L3V1 Spatial V1 
	  * Section 
	  */
 	 public static final int SPATIAL_21201 = 1221201; 

	 /**
	  * Error code 1221202:
	  * A &lt;geometryDefinition&gt; object may have the optional SBML Level 3 Core subobjects 
	  * for notes and annotations. No other elements from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;geometryDefinition&gt;. Reference: L3V1 Spatial V1 
	  * Section 
	  */
 	 public static final int SPATIAL_21202 = 1221202; 

	 /**
	  * Error code 1221203:
	  * A &lt;geometryDefinition&gt; object must have the required attributes 'spatial:id' and 
	  * 'spatial:isActive', and may have the optional attribute 'spatial:name'. No other 
	  * attributes from the SBML Level 3 Spatial Processes namespaces are permitted on a 
	  * &lt;geometryDefinition&gt; object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21203 = 1221203; 

	 /**
	  * Error code 1221204:
	  * The attribute 'spatial:isActive' on a &lt;geometryDefinition&gt; must have a value of 
	  * data type 'boolean'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21204 = 1221204; 

	 /**
	  * Error code 1221205:
	  * The attribute 'spatial:name' on a &lt;geometryDefinition&gt; must have a value of data 
	  * type 'string'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21205 = 1221205; 

	 /**
	  * Error code 1221301:
	  * A &lt;compartmentMapping&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;compartmentMapping&gt;. Reference: L3V1 Spatial V1 
	  * Section 
	  */
 	 public static final int SPATIAL_21301 = 1221301; 

	 /**
	  * Error code 1221302:
	  * A &lt;compartmentMapping&gt; object may have the optional SBML Level 3 Core subobjects 
	  * for notes and annotations. No other elements from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;compartmentMapping&gt;. Reference: L3V1 Spatial V1 
	  * Section 
	  */
 	 public static final int SPATIAL_21302 = 1221302; 

	 /**
	  * Error code 1221303:
	  * A &lt;compartmentMapping&gt; object must have the required attributes 'spatial:id', 
	  * 'spatial:domainType' and 'spatial:unitSize', and may have the optional attribute 
	  * 'spatial:name'. No other attributes from the SBML Level 3 Spatial Processes 
	  * namespaces are permitted on a &lt;compartmentMapping&gt; object. Reference: L3V1 
	  * Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21303 = 1221303; 

	 /**
	  * Error code 1221304:
	  * The value of the attribute 'spatial:domainType' of a &lt;compartmentMapping&gt; object 
	  * must be the identifier of an existing &lt;domainType&gt; object defined in the 
	  * enclosing &lt;model&gt; object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21304 = 1221304; 

	 /**
	  * Error code 1221305:
	  * The attribute 'spatial:unitSize' on a &lt;compartmentMapping&gt; must have a value of 
	  * data type 'double'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21305 = 1221305; 

	 /**
	  * Error code 1221306:
	  * The attribute 'spatial:name' on a &lt;compartmentMapping&gt; must have a value of data 
	  * type 'string'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21306 = 1221306; 

	 /**
	  * Error code 1221401:
	  * A &lt;coordinateComponent&gt; object may have the optional SBML Level 3 Core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;coordinateComponent&gt;. Reference: L3V1 
	  * Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21401 = 1221401; 

	 /**
	  * Error code 1221402:
	  * A &lt;coordinateComponent&gt; object may have the optional SBML Level 3 Core 
	  * subobjects for notes and annotations. No other elements from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;coordinateComponent&gt;. Reference: L3V1 
	  * Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21402 = 1221402; 

	 /**
	  * Error code 1221403:
	  * A &lt;coordinateComponent&gt; object must have the required attributes 'spatial:id' 
	  * and 'spatial:type', and may have the optional attributes 'spatial:name' and 
	  * 'spatial:unit'. No other attributes from the SBML Level 3 Spatial Processes 
	  * namespaces are permitted on a &lt;coordinateComponent&gt; object. Reference: L3V1 
	  * Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21403 = 1221403; 

	 /**
	  * Error code 1221404:
	  * A &lt;coordinateComponent&gt; object must contain one and only one instance of each of 
	  * the Boundary and &lt;boundary&gt; elements. No other elements from the SBML Level 3 
	  * Spatial Processes namespaces are permitted on a &lt;coordinateComponent&gt; object. 
	  * Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21404 = 1221404; 

	 /**
	  * Error code 1221405:
	  * The value of the attribute 'spatial:type' of a &lt;coordinateComponent&gt; object must 
	  * conform to the syntax of SBML data type 'CoordinateKind' and may only take on 
	  * the allowed values of 'CoordinateKind' defined in SBML; that is, the value must 
	  * be one of the following: 'cartesianX', 'cartesianY' or 'cartesianZ'. Reference: 
	  * L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21405 = 1221405; 

	 /**
	  * Error code 1221406:
	  * The attribute 'spatial:name' on a &lt;coordinateComponent&gt; must have a value of 
	  * data type 'string'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21406 = 1221406; 

	 /**
	  * Error code 1221407:
	  * The value of the attribute 'spatial:unit' on a &lt;coordinateComponent&gt; must have a 
	  * taken from the following: the identifier of a &lt;unitDefinition&gt; object in the 
	  * enclosing &lt;model,&gt; or one of the base units in SBML. Reference: L3V1 Spatial V1 
	  * Section 
	  */
 	 public static final int SPATIAL_21407 = 1221407; 

	 /**
	  * Error code 1221501:
	  * A &lt;sampledFieldGeometry&gt; object may have the optional SBML Level 3 Core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;sampledFieldGeometry&gt;. Reference: L3V1 
	  * Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21501 = 1221501; 

	 /**
	  * Error code 1221502:
	  * A &lt;sampledFieldGeometry&gt; object may have the optional SBML Level 3 Core 
	  * subobjects for notes and annotations. No other elements from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;sampledFieldGeometry&gt;. Reference: L3V1 
	  * Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21502 = 1221502; 

	 /**
	  * Error code 1221503:
	  * A &lt;sampledFieldGeometry&gt; object must have the required attribute 
	  * 'spatial:sampledField'. No other attributes from the SBML Level 3 Spatial 
	  * Processes namespaces are permitted on a &lt;sampledFieldGeometry&gt; object. 
	  * Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21503 = 1221503; 

	 /**
	  * Error code 1221504:
	  * A &lt;sampledFieldGeometry&gt; object may contain one and only one instance of the 
	  * &lt;listOfSampledVolumes&gt; element. No other elements from the SBML Level 3 Spatial 
	  * Processes namespaces are permitted on a &lt;sampledFieldGeometry&gt; object. 
	  * Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21504 = 1221504; 

	 /**
	  * Error code 1221505:
	  * The value of the attribute 'spatial:sampledField' of a &lt;sampledFieldGeometry&gt; 
	  * object must be the identifier of an existing &lt;sampledField&gt; object defined in 
	  * the enclosing &lt;model&gt; object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21505 = 1221505; 

	 /**
	  * Error code 1221506:
	  * Apart from the general notes and annotations subobjects permitted on all SBML 
	  * objects, a &lt;listOfSampledVolumes&gt; container object may only contain 
	  * &lt;sampledVolume&gt; objects. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21506 = 1221506; 

	 /**
	  * Error code 1221507:
	  * A &lt;listOfSampledVolumes&gt; object may have the optional SBML Level 3 Core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;listOfSampledVolumes&gt; object. Reference: 
	  * L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21507 = 1221507; 

	 /**
	  * Error code 1221601:
	  * A &lt;sampledField&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;sampledField&gt;. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21601 = 1221601; 

	 /**
	  * Error code 1221602:
	  * A &lt;sampledField&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespaces 
	  * are permitted on a &lt;sampledField&gt;. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21602 = 1221602; 

	 /**
	  * Error code 1221603:
	  * A &lt;sampledField&gt; object must have the required attributes 'spatial:id', 
	  * 'spatial:dataType', 'spatial:numSamplesOne', 'spatial:interpolationType', 
	  * 'spatial:compression', 'spatial:samples' and 'spatial:samplesLength', and may 
	  * have the optional attributes 'spatial:name', 'spatial:numSamplesTwo' and 
	  * 'spatial:numSamplesThree'. No other attributes from the SBML Level 3 Spatial 
	  * Processes namespaces are permitted on a &lt;sampledField&gt; object. Reference: L3V1 
	  * Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21603 = 1221603; 

	 /**
	  * Error code 1221604:
	  * The value of the attribute 'spatial:dataType' of a &lt;sampledField&gt; object must 
	  * conform to the syntax of SBML data type 'DataKind' and may only take on the 
	  * allowed values of 'DataKind' defined in SBML; that is, the value must be one of 
	  * the following: 'double', 'float', 'uint8', 'uint16' or 'uint32'. Reference: L3V1 
	  * Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21604 = 1221604; 

	 /**
	  * Error code 1221605:
	  * The attribute 'spatial:numSamplesOne' on a &lt;sampledField&gt; must have a value of 
	  * data type 'integer'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21605 = 1221605; 

	 /**
	  * Error code 1221606:
	  * The value of the attribute 'spatial:interpolationType' of a &lt;sampledField&gt; 
	  * object must conform to the syntax of SBML data type 'InterpolationKind' and may 
	  * only take on the allowed values of 'InterpolationKind' defined in SBML; that is, 
	  * the value must be one of the following: 'nearestNeighbor' or 'linear'. 
	  * Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21606 = 1221606; 

	 /**
	  * Error code 1221607:
	  * The value of the attribute 'spatial:compression' of a &lt;sampledField&gt; object must 
	  * conform to the syntax of SBML data type 'CompressionKind' and may only take on 
	  * the allowed values of 'CompressionKind' defined in SBML; that is, the value must 
	  * be one of the following: 'uncompressed' or 'deflated'. Reference: L3V1 Spatial 
	  * V1 Section 
	  */
 	 public static final int SPATIAL_21607 = 1221607; 

	 /**
	  * Error code 1221608:
	  * The value of the attribute 'spatial:samples' of a &lt;sampledField&gt; object must be 
	  * an array of values of type 'int'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21608 = 1221608; 

	 /**
	  * Error code 1221609:
	  * The attribute 'spatial:samplesLength' on a &lt;sampledField&gt; must have a value of 
	  * data type 'integer'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21609 = 1221609; 

	 /**
	  * Error code 1221610:
	  * The attribute 'spatial:name' on a &lt;sampledField&gt; must have a value of data type 
	  * 'string'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21610 = 1221610; 

	 /**
	  * Error code 1221611:
	  * The attribute 'spatial:numSamplesTwo' on a &lt;sampledField&gt; must have a value of 
	  * data type 'integer'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21611 = 1221611; 

	 /**
	  * Error code 1221612:
	  * The attribute 'spatial:numSamplesThree' on a &lt;sampledField&gt; must have a value of 
	  * data type 'integer'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21612 = 1221612; 

	 /**
	  * Error code 1221701:
	  * A &lt;sampledVolume&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;sampledVolume&gt;. Reference: L3V1 Spatial V1 
	  * Section 
	  */
 	 public static final int SPATIAL_21701 = 1221701; 

	 /**
	  * Error code 1221702:
	  * A &lt;sampledVolume&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespaces 
	  * are permitted on a &lt;sampledVolume&gt;. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21702 = 1221702; 

	 /**
	  * Error code 1221703:
	  * A &lt;sampledVolume&gt; object must have the required attributes 'spatial:id' and 
	  * 'spatial:domainType', and may have the optional attributes 'spatial:name', 
	  * 'spatial:sampledValue', 'spatial:minValue' and 'spatial:maxValue'. No other 
	  * attributes from the SBML Level 3 Spatial Processes namespaces are permitted on a 
	  * &lt;sampledVolume&gt; object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21703 = 1221703; 

	 /**
	  * Error code 1221704:
	  * The value of the attribute 'spatial:domainType' of a &lt;sampledVolume&gt; object must 
	  * be the identifier of an existing &lt;domainType&gt; object defined in the enclosing 
	  * &lt;model&gt; object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21704 = 1221704; 

	 /**
	  * Error code 1221705:
	  * The attribute 'spatial:name' on a &lt;sampledVolume&gt; must have a value of data type 
	  * 'string'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21705 = 1221705; 

	 /**
	  * Error code 1221706:
	  * The attribute 'spatial:sampledValue' on a &lt;sampledVolume&gt; must have a value of 
	  * data type 'double'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21706 = 1221706; 

	 /**
	  * Error code 1221707:
	  * The attribute 'spatial:minValue' on a &lt;sampledVolume&gt; must have a value of data 
	  * type 'double'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21707 = 1221707; 

	 /**
	  * Error code 1221708:
	  * The attribute 'spatial:maxValue' on a &lt;sampledVolume&gt; must have a value of data 
	  * type 'double'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21708 = 1221708; 

	 /**
	  * Error code 1221801:
	  * An &lt;analyticGeometry&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on an &lt;analyticGeometry&gt;. Reference: L3V1 Spatial V1 
	  * Section 
	  */
 	 public static final int SPATIAL_21801 = 1221801; 

	 /**
	  * Error code 1221802:
	  * An &lt;analyticGeometry&gt; object may have the optional SBML Level 3 Core subobjects 
	  * for notes and annotations. No other elements from the SBML Level 3 Core 
	  * namespaces are permitted on an &lt;analyticGeometry&gt;. Reference: L3V1 Spatial V1 
	  * Section 
	  */
 	 public static final int SPATIAL_21802 = 1221802; 

	 /**
	  * Error code 1221803:
	  * An &lt;analyticGeometry&gt; object may contain one and only one instance of the 
	  * &lt;listOfAnalyticVolumes&gt; element. No other elements from the SBML Level 3 Spatial 
	  * Processes namespaces are permitted on an &lt;analyticGeometry&gt; object. Reference: 
	  * L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21803 = 1221803; 

	 /**
	  * Error code 1221804:
	  * Apart from the general notes and annotations subobjects permitted on all SBML 
	  * objects, a &lt;listOfAnalyticVolumes&gt; container object may only contain 
	  * &lt;analyticVolume&gt; objects. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21804 = 1221804; 

	 /**
	  * Error code 1221805:
	  * A &lt;listOfAnalyticVolumes&gt; object may have the optional SBML Level 3 Core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;listOfAnalyticVolumes&gt; object. Reference: 
	  * L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21805 = 1221805; 

	 /**
	  * Error code 1221901:
	  * An &lt;analyticVolume&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on an &lt;analyticVolume&gt;. Reference: L3V1 Spatial V1 
	  * Section 
	  */
 	 public static final int SPATIAL_21901 = 1221901; 

	 /**
	  * Error code 1221902:
	  * An &lt;analyticVolume&gt; object may have the optional SBML Level 3 Core subobjects 
	  * for notes and annotations. No other elements from the SBML Level 3 Core 
	  * namespaces are permitted on an &lt;analyticVolume&gt;. Reference: L3V1 Spatial V1 
	  * Section 
	  */
 	 public static final int SPATIAL_21902 = 1221902; 

	 /**
	  * Error code 1221903:
	  * An &lt;analyticVolume&gt; object must have the required attributes 'spatial:id', 
	  * 'spatial:functionType' and 'spatial:domainType', and may have the optional 
	  * attributes 'spatial:name' and 'spatial:ordinal'. No other attributes from the 
	  * SBML Level 3 Spatial Processes namespaces are permitted on an &lt;analyticVolume&gt; 
	  * object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21903 = 1221903; 

	 /**
	  * Error code 1221904:
	  * An &lt;analyticVolume&gt; object may contain one and only one instance of the ASTNode 
	  * element. No other elements from the SBML Level 3 Spatial Processes namespaces 
	  * are permitted on an &lt;analyticVolume&gt; object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21904 = 1221904; 

	 /**
	  * Error code 1221905:
	  * The value of the attribute 'spatial:functionType' of an &lt;analyticVolume&gt; object 
	  * must conform to the syntax of SBML data type 'FunctionKind' and may only take on 
	  * the allowed values of 'FunctionKind' defined in SBML; that is, the value must be 
	  * one of the following: 'layered'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21905 = 1221905; 

	 /**
	  * Error code 1221906:
	  * The value of the attribute 'spatial:domainType' of an &lt;analyticVolume&gt; object 
	  * must be the identifier of an existing &lt;domainType&gt; object defined in the 
	  * enclosing &lt;model&gt; object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21906 = 1221906; 

	 /**
	  * Error code 1221907:
	  * The attribute 'spatial:name' on an &lt;analyticVolume&gt; must have a value of data 
	  * type 'string'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21907 = 1221907; 

	 /**
	  * Error code 1221908:
	  * The attribute 'spatial:ordinal' on an &lt;analyticVolume&gt; must have a value of data 
	  * type 'integer'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_21908 = 1221908; 

	 /**
	  * Error code 1222001:
	  * A &lt;parametricGeometry&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;parametricGeometry&gt;. Reference: L3V1 Spatial V1 
	  * Section 
	  */
 	 public static final int SPATIAL_22001 = 1222001; 

	 /**
	  * Error code 1222002:
	  * A &lt;parametricGeometry&gt; object may have the optional SBML Level 3 Core subobjects 
	  * for notes and annotations. No other elements from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;parametricGeometry&gt;. Reference: L3V1 Spatial V1 
	  * Section 
	  */
 	 public static final int SPATIAL_22002 = 1222002; 

	 /**
	  * Error code 1222003:
	  * A &lt;parametricGeometry&gt; object may contain one and only one instance of each of 
	  * the SpatialPoints and &lt;listOfParametricObjects&gt; elements. No other elements from 
	  * the SBML Level 3 Spatial Processes namespaces are permitted on a 
	  * &lt;parametricGeometry&gt; object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22003 = 1222003; 

	 /**
	  * Error code 1222004:
	  * Apart from the general notes and annotations subobjects permitted on all SBML 
	  * objects, a &lt;listOfParametricObjects&gt; container object may only contain 
	  * &lt;parametricObject&gt; objects. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22004 = 1222004; 

	 /**
	  * Error code 1222005:
	  * A &lt;listOfParametricObjects&gt; object may have the optional SBML Level 3 Core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;listOfParametricObjects&gt; object. Reference: 
	  * L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22005 = 1222005; 

	 /**
	  * Error code 1222101:
	  * A &lt;parametricObject&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;parametricObject&gt;. Reference: L3V1 Spatial V1 
	  * Section 
	  */
 	 public static final int SPATIAL_22101 = 1222101; 

	 /**
	  * Error code 1222102:
	  * A &lt;parametricObject&gt; object may have the optional SBML Level 3 Core subobjects 
	  * for notes and annotations. No other elements from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;parametricObject&gt;. Reference: L3V1 Spatial V1 
	  * Section 
	  */
 	 public static final int SPATIAL_22102 = 1222102; 

	 /**
	  * Error code 1222103:
	  * A &lt;parametricObject&gt; object must have the required attributes 'spatial:id', 
	  * 'spatial:polygonType', 'spatial:domainType', 'spatial:pointIndex', 
	  * 'spatial:pointIndexLength' and 'spatial:compression', and may have the optional 
	  * attributes 'spatial:name' and 'spatial:dataType'. No other attributes from the 
	  * SBML Level 3 Spatial Processes namespaces are permitted on a &lt;parametricObject&gt; 
	  * object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22103 = 1222103; 

	 /**
	  * Error code 1222104:
	  * The value of the attribute 'spatial:polygonType' of a &lt;parametricObject&gt; object 
	  * must conform to the syntax of SBML data type 'PolygonKind' and may only take on 
	  * the allowed values of 'PolygonKind' defined in SBML; that is, the value must be 
	  * one of the following: 'triangle' or 'quadrilateral'. Reference: L3V1 Spatial V1 
	  * Section 
	  */
 	 public static final int SPATIAL_22104 = 1222104; 

	 /**
	  * Error code 1222105:
	  * The value of the attribute 'spatial:domainType' of a &lt;parametricObject&gt; object 
	  * must be the identifier of an existing &lt;domainType&gt; object defined in the 
	  * enclosing &lt;model&gt; object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22105 = 1222105; 

	 /**
	  * Error code 1222106:
	  * The value of the attribute 'spatial:pointIndex' of a &lt;parametricObject&gt; object 
	  * must be an array of values of type 'int'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22106 = 1222106; 

	 /**
	  * Error code 1222107:
	  * The attribute 'spatial:pointIndexLength' on a &lt;parametricObject&gt; must have a 
	  * value of data type 'integer'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22107 = 1222107; 

	 /**
	  * Error code 1222108:
	  * The value of the attribute 'spatial:compression' of a &lt;parametricObject&gt; object 
	  * must conform to the syntax of SBML data type 'CompressionKind' and may only take 
	  * on the allowed values of 'CompressionKind' defined in SBML; that is, the value 
	  * must be one of the following: 'uncompressed' or 'deflated'. Reference: L3V1 
	  * Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22108 = 1222108; 

	 /**
	  * Error code 1222109:
	  * The attribute 'spatial:name' on a &lt;parametricObject&gt; must have a value of data 
	  * type 'string'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22109 = 1222109; 

	 /**
	  * Error code 1222110:
	  * The value of the attribute 'spatial:dataType' of a &lt;parametricObject&gt; object 
	  * must conform to the syntax of SBML data type 'DataKind' and may only take on the 
	  * allowed values of 'DataKind' defined in SBML; that is, the value must be one of 
	  * the following: 'double', 'float', 'uint8', 'uint16' or 'uint32'. Reference: L3V1 
	  * Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22110 = 1222110; 

	 /**
	  * Error code 1222201:
	  * A &lt;cSGeometry&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;cSGeometry&gt;. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22201 = 1222201; 

	 /**
	  * Error code 1222202:
	  * A &lt;cSGeometry&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespaces 
	  * are permitted on a &lt;cSGeometry&gt;. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22202 = 1222202; 

	 /**
	  * Error code 1222203:
	  * A &lt;cSGeometry&gt; object may contain one and only one instance of the 
	  * &lt;listOfCSGObjects&gt; element. No other elements from the SBML Level 3 Spatial 
	  * Processes namespaces are permitted on a &lt;cSGeometry&gt; object. Reference: L3V1 
	  * Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22203 = 1222203; 

	 /**
	  * Error code 1222204:
	  * Apart from the general notes and annotations subobjects permitted on all SBML 
	  * objects, a &lt;listOfCSGObjects&gt; container object may only contain &lt;cSGObject&gt; 
	  * objects. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22204 = 1222204; 

	 /**
	  * Error code 1222205:
	  * A &lt;listOfCSGObjects&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;listOfCSGObjects&gt; object. Reference: L3V1 Spatial 
	  * V1 Section 
	  */
 	 public static final int SPATIAL_22205 = 1222205; 

	 /**
	  * Error code 1222301:
	  * A &lt;cSGObject&gt; object may have the optional SBML Level 3 Core attributes 'metaid' 
	  * and 'sboTerm'. No other attributes from the SBML Level 3 Core namespaces are 
	  * permitted on a &lt;cSGObject&gt;. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22301 = 1222301; 

	 /**
	  * Error code 1222302:
	  * A &lt;cSGObject&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespaces 
	  * are permitted on a &lt;cSGObject&gt;. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22302 = 1222302; 

	 /**
	  * Error code 1222303:
	  * A &lt;cSGObject&gt; object must have the required attributes 'spatial:id' and 
	  * 'spatial:domainType', and may have the optional attributes 'spatial:name' and 
	  * 'spatial:ordinal'. No other attributes from the SBML Level 3 Spatial Processes 
	  * namespaces are permitted on a &lt;cSGObject&gt; object. Reference: L3V1 Spatial V1 
	  * Section 
	  */
 	 public static final int SPATIAL_22303 = 1222303; 

	 /**
	  * Error code 1222304:
	  * A &lt;cSGObject&gt; object must contain one and only one instance of the CSGNode 
	  * element. No other elements from the SBML Level 3 Spatial Processes namespaces 
	  * are permitted on a &lt;cSGObject&gt; object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22304 = 1222304; 

	 /**
	  * Error code 1222305:
	  * The value of the attribute 'spatial:domainType' of a &lt;cSGObject&gt; object must be 
	  * the identifier of an existing &lt;domainType&gt; object defined in the enclosing 
	  * &lt;model&gt; object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22305 = 1222305; 

	 /**
	  * Error code 1222306:
	  * The attribute 'spatial:name' on a &lt;cSGObject&gt; must have a value of data type 
	  * 'string'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22306 = 1222306; 

	 /**
	  * Error code 1222307:
	  * The attribute 'spatial:ordinal' on a &lt;cSGObject&gt; must have a value of data type 
	  * 'integer'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22307 = 1222307; 

	 /**
	  * Error code 1222401:
	  * A &lt;cSGNode&gt; object may have the optional SBML Level 3 Core attributes 'metaid' 
	  * and 'sboTerm'. No other attributes from the SBML Level 3 Core namespaces are 
	  * permitted on a &lt;cSGNode&gt;. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22401 = 1222401; 

	 /**
	  * Error code 1222402:
	  * A &lt;cSGNode&gt; object may have the optional SBML Level 3 Core subobjects for notes 
	  * and annotations. No other elements from the SBML Level 3 Core namespaces are 
	  * permitted on a &lt;cSGNode&gt;. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22402 = 1222402; 

	 /**
	  * Error code 1222403:
	  * A &lt;cSGNode&gt; object may have the optional attributes 'spatial:id' and 
	  * 'spatial:name'. No other attributes from the SBML Level 3 Spatial Processes 
	  * namespaces are permitted on a &lt;cSGNode&gt; object. Reference: L3V1 Spatial V1 
	  * Section 
	  */
 	 public static final int SPATIAL_22403 = 1222403; 

	 /**
	  * Error code 1222404:
	  * The attribute 'spatial:name' on a &lt;cSGNode&gt; must have a value of data type 
	  * 'string'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22404 = 1222404; 

	 /**
	  * Error code 1222501:
	  * A &lt;cSGTransformation&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;cSGTransformation&gt;. Reference: L3V1 Spatial V1 
	  * Section 
	  */
 	 public static final int SPATIAL_22501 = 1222501; 

	 /**
	  * Error code 1222502:
	  * A &lt;cSGTransformation&gt; object may have the optional SBML Level 3 Core subobjects 
	  * for notes and annotations. No other elements from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;cSGTransformation&gt;. Reference: L3V1 Spatial V1 
	  * Section 
	  */
 	 public static final int SPATIAL_22502 = 1222502; 

	 /**
	  * Error code 1222503:
	  * A &lt;cSGTransformation&gt; object may contain one and only one instance of the 
	  * CSGNode element. No other elements from the SBML Level 3 Spatial Processes 
	  * namespaces are permitted on a &lt;cSGTransformation&gt; object. Reference: L3V1 
	  * Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22503 = 1222503; 

	 /**
	  * Error code 1222601:
	  * A &lt;cSGTranslation&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;cSGTranslation&gt;. Reference: L3V1 Spatial V1 
	  * Section 
	  */
 	 public static final int SPATIAL_22601 = 1222601; 

	 /**
	  * Error code 1222602:
	  * A &lt;cSGTranslation&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespaces 
	  * are permitted on a &lt;cSGTranslation&gt;. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22602 = 1222602; 

	 /**
	  * Error code 1222603:
	  * A &lt;cSGTranslation&gt; object must have the required attribute 'spatial:translateX', 
	  * and may have the optional attributes 'spatial:translateY' and 
	  * 'spatial:translateZ'. No other attributes from the SBML Level 3 Spatial 
	  * Processes namespaces are permitted on a &lt;cSGTranslation&gt; object. Reference: L3V1 
	  * Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22603 = 1222603; 

	 /**
	  * Error code 1222604:
	  * The attribute 'spatial:translateX' on a &lt;cSGTranslation&gt; must have a value of 
	  * data type 'double'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22604 = 1222604; 

	 /**
	  * Error code 1222605:
	  * The attribute 'spatial:translateY' on a &lt;cSGTranslation&gt; must have a value of 
	  * data type 'double'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22605 = 1222605; 

	 /**
	  * Error code 1222606:
	  * The attribute 'spatial:translateZ' on a &lt;cSGTranslation&gt; must have a value of 
	  * data type 'double'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22606 = 1222606; 

	 /**
	  * Error code 1222701:
	  * A &lt;cSGRotation&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;cSGRotation&gt;. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22701 = 1222701; 

	 /**
	  * Error code 1222702:
	  * A &lt;cSGRotation&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespaces 
	  * are permitted on a &lt;cSGRotation&gt;. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22702 = 1222702; 

	 /**
	  * Error code 1222703:
	  * A &lt;cSGRotation&gt; object must have the required attributes 'spatial:rotateX' and 
	  * 'spatial:rotateAngleInRadians', and may have the optional attributes 
	  * 'spatial:rotateY' and 'spatial:rotateZ'. No other attributes from the SBML Level 
	  * 3 Spatial Processes namespaces are permitted on a &lt;cSGRotation&gt; object. 
	  * Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22703 = 1222703; 

	 /**
	  * Error code 1222704:
	  * The attribute 'spatial:rotateX' on a &lt;cSGRotation&gt; must have a value of data 
	  * type 'double'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22704 = 1222704; 

	 /**
	  * Error code 1222705:
	  * The attribute 'spatial:rotateAngleInRadians' on a &lt;cSGRotation&gt; must have a 
	  * value of data type 'double'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22705 = 1222705; 

	 /**
	  * Error code 1222706:
	  * The attribute 'spatial:rotateY' on a &lt;cSGRotation&gt; must have a value of data 
	  * type 'double'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22706 = 1222706; 

	 /**
	  * Error code 1222707:
	  * The attribute 'spatial:rotateZ' on a &lt;cSGRotation&gt; must have a value of data 
	  * type 'double'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22707 = 1222707; 

	 /**
	  * Error code 1222801:
	  * A &lt;cSGScale&gt; object may have the optional SBML Level 3 Core attributes 'metaid' 
	  * and 'sboTerm'. No other attributes from the SBML Level 3 Core namespaces are 
	  * permitted on a &lt;cSGScale&gt;. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22801 = 1222801; 

	 /**
	  * Error code 1222802:
	  * A &lt;cSGScale&gt; object may have the optional SBML Level 3 Core subobjects for notes 
	  * and annotations. No other elements from the SBML Level 3 Core namespaces are 
	  * permitted on a &lt;cSGScale&gt;. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22802 = 1222802; 

	 /**
	  * Error code 1222803:
	  * A &lt;cSGScale&gt; object must have the required attribute 'spatial:scaleX', and may 
	  * have the optional attributes 'spatial:scaleY' and 'spatial:scaleZ'. No other 
	  * attributes from the SBML Level 3 Spatial Processes namespaces are permitted on a 
	  * &lt;cSGScale&gt; object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22803 = 1222803; 

	 /**
	  * Error code 1222804:
	  * The attribute 'spatial:scaleX' on a &lt;cSGScale&gt; must have a value of data type 
	  * 'double'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22804 = 1222804; 

	 /**
	  * Error code 1222805:
	  * The attribute 'spatial:scaleY' on a &lt;cSGScale&gt; must have a value of data type 
	  * 'double'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22805 = 1222805; 

	 /**
	  * Error code 1222806:
	  * The attribute 'spatial:scaleZ' on a &lt;cSGScale&gt; must have a value of data type 
	  * 'double'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22806 = 1222806; 

	 /**
	  * Error code 1222901:
	  * A &lt;cSGHomogeneousTransformation&gt; object may have the optional SBML Level 3 Core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;cSGHomogeneousTransformation&gt;. Reference: 
	  * L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22901 = 1222901; 

	 /**
	  * Error code 1222902:
	  * A &lt;cSGHomogeneousTransformation&gt; object may have the optional SBML Level 3 Core 
	  * subobjects for notes and annotations. No other elements from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;cSGHomogeneousTransformation&gt;. Reference: 
	  * L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22902 = 1222902; 

	 /**
	  * Error code 1222903:
	  * A &lt;cSGHomogeneousTransformation&gt; object must contain one and only one instance 
	  * of the TransformationComponent element. No other elements from the SBML Level 3 
	  * Spatial Processes namespaces are permitted on a &lt;cSGHomogeneousTransformation&gt; 
	  * object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_22903 = 1222903; 

	 /**
	  * Error code 1223001:
	  * A &lt;transformationComponent&gt; object may have the optional SBML Level 3 Core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;transformationComponent&gt;. Reference: L3V1 
	  * Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23001 = 1223001; 

	 /**
	  * Error code 1223002:
	  * A &lt;transformationComponent&gt; object may have the optional SBML Level 3 Core 
	  * subobjects for notes and annotations. No other elements from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;transformationComponent&gt;. Reference: L3V1 
	  * Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23002 = 1223002; 

	 /**
	  * Error code 1223003:
	  * A &lt;transformationComponent&gt; object must have the required attributes 
	  * 'spatial:components' and 'spatial:componentsLength'. No other attributes from 
	  * the SBML Level 3 Spatial Processes namespaces are permitted on a 
	  * &lt;transformationComponent&gt; object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23003 = 1223003; 

	 /**
	  * Error code 1223004:
	  * The value of the attribute 'spatial:components' of a &lt;transformationComponent&gt; 
	  * object must be an array of values of type 'double'. Reference: L3V1 Spatial V1 
	  * Section 
	  */
 	 public static final int SPATIAL_23004 = 1223004; 

	 /**
	  * Error code 1223005:
	  * The attribute 'spatial:componentsLength' on a &lt;transformationComponent&gt; must 
	  * have a value of data type 'integer'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23005 = 1223005; 

	 /**
	  * Error code 1223101:
	  * A &lt;cSGPrimitive&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;cSGPrimitive&gt;. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23101 = 1223101; 

	 /**
	  * Error code 1223102:
	  * A &lt;cSGPrimitive&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespaces 
	  * are permitted on a &lt;cSGPrimitive&gt;. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23102 = 1223102; 

	 /**
	  * Error code 1223103:
	  * A &lt;cSGPrimitive&gt; object must have the required attribute 
	  * 'spatial:primitiveType'. No other attributes from the SBML Level 3 Spatial 
	  * Processes namespaces are permitted on a &lt;cSGPrimitive&gt; object. Reference: L3V1 
	  * Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23103 = 1223103; 

	 /**
	  * Error code 1223104:
	  * The value of the attribute 'spatial:primitiveType' of a &lt;cSGPrimitive&gt; object 
	  * must conform to the syntax of SBML data type 'PrimitiveKind' and may only take 
	  * on the allowed values of 'PrimitiveKind' defined in SBML; that is, the value 
	  * must be one of the following: 'sphere', 'cube', 'cylinder', 'cone', 'circle' or 
	  * 'square'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23104 = 1223104; 

	 /**
	  * Error code 1223201:
	  * A &lt;cSGSetOperator&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;cSGSetOperator&gt;. Reference: L3V1 Spatial V1 
	  * Section 
	  */
 	 public static final int SPATIAL_23201 = 1223201; 

	 /**
	  * Error code 1223202:
	  * A &lt;cSGSetOperator&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespaces 
	  * are permitted on a &lt;cSGSetOperator&gt;. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23202 = 1223202; 

	 /**
	  * Error code 1223203:
	  * A &lt;cSGSetOperator&gt; object must have the required attribute 
	  * 'spatial:operationType', and may have the optional attributes 
	  * 'spatial:complementA' and 'spatial:complementB'. No other attributes from the 
	  * SBML Level 3 Spatial Processes namespaces are permitted on a &lt;cSGSetOperator&gt; 
	  * object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23203 = 1223203; 

	 /**
	  * Error code 1223204:
	  * A &lt;cSGSetOperator&gt; object may contain one and only one instance of the 
	  * &lt;listOfCSGNodes&gt; element. No other elements from the SBML Level 3 Spatial 
	  * Processes namespaces are permitted on a &lt;cSGSetOperator&gt; object. Reference: L3V1 
	  * Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23204 = 1223204; 

	 /**
	  * Error code 1223205:
	  * The value of the attribute 'spatial:operationType' of a &lt;cSGSetOperator&gt; object 
	  * must conform to the syntax of SBML data type 'SetOperation' and may only take on 
	  * the allowed values of 'SetOperation' defined in SBML; that is, the value must be 
	  * one of the following: 'union', 'intersection' or 'difference'. Reference: L3V1 
	  * Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23205 = 1223205; 

	 /**
	  * Error code 1223206:
	  * The value of the attribute 'spatial:complementA' of a &lt;cSGSetOperator&gt; object 
	  * must be the identifier of an existing &lt;cSGNode&gt; object defined in the enclosing 
	  * &lt;model&gt; object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23206 = 1223206; 

	 /**
	  * Error code 1223207:
	  * The value of the attribute 'spatial:complementB' of a &lt;cSGSetOperator&gt; object 
	  * must be the identifier of an existing &lt;cSGNode&gt; object defined in the enclosing 
	  * &lt;model&gt; object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23207 = 1223207; 

	 /**
	  * Error code 1223208:
	  * Apart from the general notes and annotations subobjects permitted on all SBML 
	  * objects, a &lt;listOfCSGNodes&gt; container object may only contain &lt;cSGNode&gt; objects. 
	  * Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23208 = 1223208; 

	 /**
	  * Error code 1223209:
	  * A &lt;listOfCSGNodes&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;listOfCSGNodes&gt; object. Reference: L3V1 Spatial 
	  * V1 Section 
	  */
 	 public static final int SPATIAL_23209 = 1223209; 

	 /**
	  * Error code 1223301:
	  * A &lt;spatialSymbolReference&gt; object may have the optional SBML Level 3 Core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;spatialSymbolReference&gt;. Reference: L3V1 
	  * Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23301 = 1223301; 

	 /**
	  * Error code 1223302:
	  * A &lt;spatialSymbolReference&gt; object may have the optional SBML Level 3 Core 
	  * subobjects for notes and annotations. No other elements from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;spatialSymbolReference&gt;. Reference: L3V1 
	  * Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23302 = 1223302; 

	 /**
	  * Error code 1223303:
	  * A &lt;spatialSymbolReference&gt; object must have the required attribute 
	  * 'spatial:spatialRef'. No other attributes from the SBML Level 3 Spatial 
	  * Processes namespaces are permitted on a &lt;spatialSymbolReference&gt; object. 
	  * Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23303 = 1223303; 

	 /**
	  * Error code 1223304:
	  * The value of the attribute 'spatial:spatialRef' of a &lt;spatialSymbolReference&gt; 
	  * object must be the identifier of an existing &lt;geometry&gt; object defined in the 
	  * enclosing &lt;model&gt; object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23304 = 1223304; 

	 /**
	  * Error code 1223401:
	  * A &lt;diffusionCoefficient&gt; object may have the optional SBML Level 3 Core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;diffusionCoefficient&gt;. Reference: L3V1 
	  * Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23401 = 1223401; 

	 /**
	  * Error code 1223402:
	  * A &lt;diffusionCoefficient&gt; object may have the optional SBML Level 3 Core 
	  * subobjects for notes and annotations. No other elements from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;diffusionCoefficient&gt;. Reference: L3V1 
	  * Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23402 = 1223402; 

	 /**
	  * Error code 1223403:
	  * A &lt;diffusionCoefficient&gt; object must have the required attributes 
	  * 'spatial:variable' and 'spatial:type', and may have the optional attributes 
	  * 'spatial:coordinateReferenceOne' and 'spatial:coordinateReferenceTwo'. No other 
	  * attributes from the SBML Level 3 Spatial Processes namespaces are permitted on a 
	  * &lt;diffusionCoefficient&gt; object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23403 = 1223403; 

	 /**
	  * Error code 1223404:
	  * The value of the attribute 'spatial:variable' of a &lt;diffusionCoefficient&gt; object 
	  * must be the identifier of an existing &lt;species&gt; object defined in the enclosing 
	  * &lt;model&gt; object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23404 = 1223404; 

	 /**
	  * Error code 1223405:
	  * The value of the attribute 'spatial:type' of a &lt;diffusionCoefficient&gt; object 
	  * must conform to the syntax of SBML data type 'DiffusionKind' and may only take 
	  * on the allowed values of 'DiffusionKind' defined in SBML; that is, the value 
	  * must be one of the following: 'isotropic', 'anisotropic' or 'tensor'. Reference: 
	  * L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23405 = 1223405; 

	 /**
	  * Error code 1223406:
	  * The value of the attribute 'spatial:coordinateReferenceOne' of a 
	  * &lt;diffusionCoefficient&gt; object must conform to the syntax of SBML data type 
	  * 'CoordinateKind' and may only take on the allowed values of 'CoordinateKind' 
	  * defined in SBML; that is, the value must be one of the following: 'cartesianX', 
	  * 'cartesianY' or 'cartesianZ'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23406 = 1223406; 

	 /**
	  * Error code 1223407:
	  * The value of the attribute 'spatial:coordinateReferenceTwo' of a 
	  * &lt;diffusionCoefficient&gt; object must conform to the syntax of SBML data type 
	  * 'CoordinateKind' and may only take on the allowed values of 'CoordinateKind' 
	  * defined in SBML; that is, the value must be one of the following: 'cartesianX', 
	  * 'cartesianY' or 'cartesianZ'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23407 = 1223407; 

	 /**
	  * Error code 1223501:
	  * An &lt;advectionCoefficient&gt; object may have the optional SBML Level 3 Core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 
	  * Core namespaces are permitted on an &lt;advectionCoefficient&gt;. Reference: L3V1 
	  * Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23501 = 1223501; 

	 /**
	  * Error code 1223502:
	  * An &lt;advectionCoefficient&gt; object may have the optional SBML Level 3 Core 
	  * subobjects for notes and annotations. No other elements from the SBML Level 3 
	  * Core namespaces are permitted on an &lt;advectionCoefficient&gt;. Reference: L3V1 
	  * Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23502 = 1223502; 

	 /**
	  * Error code 1223503:
	  * An &lt;advectionCoefficient&gt; object must have the required attributes 
	  * 'spatial:variable' and 'spatial:coordinate'. No other attributes from the SBML 
	  * Level 3 Spatial Processes namespaces are permitted on an &lt;advectionCoefficient&gt; 
	  * object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23503 = 1223503; 

	 /**
	  * Error code 1223504:
	  * The value of the attribute 'spatial:variable' of an &lt;advectionCoefficient&gt; 
	  * object must be the identifier of an existing &lt;species&gt; object defined in the 
	  * enclosing &lt;model&gt; object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23504 = 1223504; 

	 /**
	  * Error code 1223505:
	  * The value of the attribute 'spatial:coordinate' of an &lt;advectionCoefficient&gt; 
	  * object must conform to the syntax of SBML data type 'CoordinateKind' and may 
	  * only take on the allowed values of 'CoordinateKind' defined in SBML; that is, 
	  * the value must be one of the following: 'cartesianX', 'cartesianY' or 
	  * 'cartesianZ'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23505 = 1223505; 

	 /**
	  * Error code 1223601:
	  * A &lt;boundaryCondition&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;boundaryCondition&gt;. Reference: L3V1 Spatial V1 
	  * Section 
	  */
 	 public static final int SPATIAL_23601 = 1223601; 

	 /**
	  * Error code 1223602:
	  * A &lt;boundaryCondition&gt; object may have the optional SBML Level 3 Core subobjects 
	  * for notes and annotations. No other elements from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;boundaryCondition&gt;. Reference: L3V1 Spatial V1 
	  * Section 
	  */
 	 public static final int SPATIAL_23602 = 1223602; 

	 /**
	  * Error code 1223603:
	  * A &lt;boundaryCondition&gt; object must have the required attributes 
	  * 'spatial:variable' and 'spatial:type', and may have the optional attributes 
	  * 'spatial:coordinateBoundary' and 'spatial:boundaryDomainType'. No other 
	  * attributes from the SBML Level 3 Spatial Processes namespaces are permitted on a 
	  * &lt;boundaryCondition&gt; object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23603 = 1223603; 

	 /**
	  * Error code 1223604:
	  * The value of the attribute 'spatial:variable' of a &lt;boundaryCondition&gt; object 
	  * must be the identifier of an existing &lt;species&gt; object defined in the enclosing 
	  * &lt;model&gt; object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23604 = 1223604; 

	 /**
	  * Error code 1223605:
	  * The value of the attribute 'spatial:type' of a &lt;boundaryCondition&gt; object must 
	  * conform to the syntax of SBML data type 'BoundaryKind' and may only take on the 
	  * allowed values of 'BoundaryKind' defined in SBML; that is, the value must be one 
	  * of the following: 'Robin_valueCoefficient', 
	  * 'Robin_inwardNormalGradientCoefficient', 'Robin_sum', 'Neumann' or 'Dirichlet'. 
	  * Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23605 = 1223605; 

	 /**
	  * Error code 1223606:
	  * The value of the attribute 'spatial:coordinateBoundary' of a &lt;boundaryCondition&gt; 
	  * object must be the identifier of an existing &lt;boundary&gt; object defined in the 
	  * enclosing &lt;model&gt; object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23606 = 1223606; 

	 /**
	  * Error code 1223607:
	  * The value of the attribute 'spatial:boundaryDomainType' of a &lt;boundaryCondition&gt; 
	  * object must be the identifier of an existing &lt;domainType&gt; object defined in the 
	  * enclosing &lt;model&gt; object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23607 = 1223607; 

	 /**
	  * Error code 1223701:
	  * A &lt;geometry&gt; object may have the optional SBML Level 3 Core attributes 'metaid' 
	  * and 'sboTerm'. No other attributes from the SBML Level 3 Core namespaces are 
	  * permitted on a &lt;geometry&gt;. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23701 = 1223701; 

	 /**
	  * Error code 1223702:
	  * A &lt;geometry&gt; object may have the optional SBML Level 3 Core subobjects for notes 
	  * and annotations. No other elements from the SBML Level 3 Core namespaces are 
	  * permitted on a &lt;geometry&gt;. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23702 = 1223702; 

	 /**
	  * Error code 1223703:
	  * A &lt;geometry&gt; object must have the required attribute 'spatial:coordinateSystem', 
	  * and may have the optional attribute 'spatial:id'. No other attributes from the 
	  * SBML Level 3 Spatial Processes namespaces are permitted on a &lt;geometry&gt; object. 
	  * Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23703 = 1223703; 

	 /**
	  * Error code 1223704:
	  * A &lt;geometry&gt; object may contain one and only one instance of each of the 
	  * &lt;listOfCoordinateComponents,&gt; &lt;listOfDomainTypes,&gt; &lt;listOfDomains,&gt; 
	  * &lt;listOfAdjacentDomains,&gt; &lt;listOfGeometryDefinitions&gt; and &lt;listOfSampledFields&gt; 
	  * elements. No other elements from the SBML Level 3 Spatial Processes namespaces 
	  * are permitted on a &lt;geometry&gt; object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23704 = 1223704; 

	 /**
	  * Error code 1223705:
	  * The value of the attribute 'spatial:coordinateSystem' of a &lt;geometry&gt; object 
	  * must conform to the syntax of SBML data type 'GeometryKind' and may only take on 
	  * the allowed values of 'GeometryKind' defined in SBML; that is, the value must be 
	  * one of the following: 'cartesian'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23705 = 1223705; 

	 /**
	  * Error code 1223706:
	  * Apart from the general notes and annotations subobjects permitted on all SBML 
	  * objects, a &lt;listOfCoordinateComponents&gt; container object may only contain 
	  * &lt;coordinateComponent&gt; objects. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23706 = 1223706; 

	 /**
	  * Error code 1223707:
	  * Apart from the general notes and annotations subobjects permitted on all SBML 
	  * objects, a &lt;listOfDomainTypes&gt; container object may only contain &lt;domainType&gt; 
	  * objects. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23707 = 1223707; 

	 /**
	  * Error code 1223708:
	  * Apart from the general notes and annotations subobjects permitted on all SBML 
	  * objects, a &lt;listOfDomains&gt; container object may only contain &lt;domain&gt; objects. 
	  * Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23708 = 1223708; 

	 /**
	  * Error code 1223709:
	  * Apart from the general notes and annotations subobjects permitted on all SBML 
	  * objects, a &lt;listOfAdjacentDomains&gt; container object may only contain 
	  * &lt;adjacentDomains&gt; objects. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23709 = 1223709; 

	 /**
	  * Error code 1223710:
	  * Apart from the general notes and annotations subobjects permitted on all SBML 
	  * objects, a &lt;listOfGeometryDefinitions&gt; container object may only contain 
	  * &lt;geometryDefinition&gt; objects. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23710 = 1223710; 

	 /**
	  * Error code 1223711:
	  * Apart from the general notes and annotations subobjects permitted on all SBML 
	  * objects, a &lt;listOfSampledFields&gt; container object may only contain 
	  * &lt;sampledField&gt; objects. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23711 = 1223711; 

	 /**
	  * Error code 1223712:
	  * A &lt;listOfCoordinateComponents&gt; object may have the optional SBML Level 3 Core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;listOfCoordinateComponents&gt; object. 
	  * Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23712 = 1223712; 

	 /**
	  * Error code 1223713:
	  * A &lt;listOfDomainTypes&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;listOfDomainTypes&gt; object. Reference: L3V1 
	  * Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23713 = 1223713; 

	 /**
	  * Error code 1223714:
	  * A &lt;listOfDomains&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;listOfDomains&gt; object. Reference: L3V1 Spatial V1 
	  * Section 
	  */
 	 public static final int SPATIAL_23714 = 1223714; 

	 /**
	  * Error code 1223715:
	  * A &lt;listOfAdjacentDomains&gt; object may have the optional SBML Level 3 Core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;listOfAdjacentDomains&gt; object. Reference: 
	  * L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23715 = 1223715; 

	 /**
	  * Error code 1223716:
	  * A &lt;listOfGeometryDefinitions&gt; object may have the optional SBML Level 3 Core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;listOfGeometryDefinitions&gt; object. 
	  * Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23716 = 1223716; 

	 /**
	  * Error code 1223717:
	  * A &lt;listOfSampledFields&gt; object may have the optional SBML Level 3 Core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;listOfSampledFields&gt; object. Reference: L3V1 
	  * Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23717 = 1223717; 

	 /**
	  * Error code 1223801:
	  * A &lt;mixedGeometry&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;mixedGeometry&gt;. Reference: L3V1 Spatial V1 
	  * Section 
	  */
 	 public static final int SPATIAL_23801 = 1223801; 

	 /**
	  * Error code 1223802:
	  * A &lt;mixedGeometry&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespaces 
	  * are permitted on a &lt;mixedGeometry&gt;. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23802 = 1223802; 

	 /**
	  * Error code 1223803:
	  * A &lt;mixedGeometry&gt; object may contain one and only one instance of each of the 
	  * &lt;listOfGeometryDefinitions&gt; and &lt;listOfOrdinalMappings&gt; elements. No other 
	  * elements from the SBML Level 3 Spatial Processes namespaces are permitted on a 
	  * &lt;mixedGeometry&gt; object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23803 = 1223803; 

	 /**
	  * Error code 1223804:
	  * Apart from the general notes and annotations subobjects permitted on all SBML 
	  * objects, a &lt;listOfGeometryDefinitions&gt; container object may only contain 
	  * &lt;geometryDefinition&gt; objects. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23804 = 1223804; 

	 /**
	  * Error code 1223805:
	  * Apart from the general notes and annotations subobjects permitted on all SBML 
	  * objects, a &lt;listOfOrdinalMappings&gt; container object may only contain 
	  * &lt;ordinalMapping&gt; objects. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23805 = 1223805; 

	 /**
	  * Error code 1223806:
	  * A &lt;listOfGeometryDefinitions&gt; object may have the optional SBML Level 3 Core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;listOfGeometryDefinitions&gt; object. 
	  * Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23806 = 1223806; 

	 /**
	  * Error code 1223807:
	  * A &lt;listOfOrdinalMappings&gt; object may have the optional SBML Level 3 Core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;listOfOrdinalMappings&gt; object. Reference: 
	  * L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23807 = 1223807; 

	 /**
	  * Error code 1223901:
	  * An &lt;ordinalMapping&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on an &lt;ordinalMapping&gt;. Reference: L3V1 Spatial V1 
	  * Section 
	  */
 	 public static final int SPATIAL_23901 = 1223901; 

	 /**
	  * Error code 1223902:
	  * An &lt;ordinalMapping&gt; object may have the optional SBML Level 3 Core subobjects 
	  * for notes and annotations. No other elements from the SBML Level 3 Core 
	  * namespaces are permitted on an &lt;ordinalMapping&gt;. Reference: L3V1 Spatial V1 
	  * Section 
	  */
 	 public static final int SPATIAL_23902 = 1223902; 

	 /**
	  * Error code 1223903:
	  * An &lt;ordinalMapping&gt; object must have the required attributes 
	  * 'spatial:geometryDefinition' and 'spatial:ordinal'. No other attributes from the 
	  * SBML Level 3 Spatial Processes namespaces are permitted on an &lt;ordinalMapping&gt; 
	  * object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23903 = 1223903; 

	 /**
	  * Error code 1223904:
	  * The value of the attribute 'spatial:geometryDefinition' of an &lt;ordinalMapping&gt; 
	  * object must be the identifier of an existing &lt;geometryDefinition&gt; object defined 
	  * in the enclosing &lt;model&gt; object. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23904 = 1223904; 

	 /**
	  * Error code 1223905:
	  * The attribute 'spatial:ordinal' on an &lt;ordinalMapping&gt; must have a value of data 
	  * type 'integer'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_23905 = 1223905; 

	 /**
	  * Error code 1224001:
	  * A &lt;spatialPoints&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;spatialPoints&gt;. Reference: L3V1 Spatial V1 
	  * Section 
	  */
 	 public static final int SPATIAL_24001 = 1224001; 

	 /**
	  * Error code 1224002:
	  * A &lt;spatialPoints&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespaces 
	  * are permitted on a &lt;spatialPoints&gt;. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_24002 = 1224002; 

	 /**
	  * Error code 1224003:
	  * A &lt;spatialPoints&gt; object must have the required attributes 
	  * 'spatial:compression', 'spatial:arrayData' and 'spatial:arrayDataLength', and 
	  * may have the optional attributes 'spatial:id', 'spatial:name' and 
	  * 'spatial:dataType'. No other attributes from the SBML Level 3 Spatial Processes 
	  * namespaces are permitted on a &lt;spatialPoints&gt; object. Reference: L3V1 Spatial V1 
	  * Section 
	  */
 	 public static final int SPATIAL_24003 = 1224003; 

	 /**
	  * Error code 1224004:
	  * The value of the attribute 'spatial:compression' of a &lt;spatialPoints&gt; object 
	  * must conform to the syntax of SBML data type 'CompressionKind' and may only take 
	  * on the allowed values of 'CompressionKind' defined in SBML; that is, the value 
	  * must be one of the following: 'uncompressed' or 'deflated'. Reference: L3V1 
	  * Spatial V1 Section 
	  */
 	 public static final int SPATIAL_24004 = 1224004; 

	 /**
	  * Error code 1224005:
	  * The value of the attribute 'spatial:arrayData' of a &lt;spatialPoints&gt; object must 
	  * be an array of values of type 'double'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_24005 = 1224005; 

	 /**
	  * Error code 1224006:
	  * The attribute 'spatial:arrayDataLength' on a &lt;spatialPoints&gt; must have a value 
	  * of data type 'integer'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_24006 = 1224006; 

	 /**
	  * Error code 1224007:
	  * The attribute 'spatial:name' on a &lt;spatialPoints&gt; must have a value of data type 
	  * 'string'. Reference: L3V1 Spatial V1 Section 
	  */
 	 public static final int SPATIAL_24007 = 1224007; 

	 /**
	  * Error code 1224008:
	  * The value of the attribute 'spatial:dataType' of a &lt;spatialPoints&gt; object must 
	  * conform to the syntax of SBML data type 'DataKind' and may only take on the 
	  * allowed values of 'DataKind' defined in SBML; that is, the value must be one of 
	  * the following: 'double', 'float', 'uint8', 'uint16' or 'uint32'. Reference: L3V1 
	  * Spatial V1 Section 
	  */
 	 public static final int SPATIAL_24008 = 1224008; 

	 /**
	  * Error code 1310101:
	  * To conform to the Render Package specification for SBML Level 3 Version 1, an 
	  * SBML document must declare 
	  * 'http://www.sbml.org/sbml/level3/version1/render/version1' as the XMLNamespace 
	  * to use for elements of this package. Reference: L3V1 Render V1 Section 3.1 
	  */
 	 public static final int RENDER_10101 = 1310101; 

	 /**
	  * Error code 1310102:
	  * Wherever they appear in an SBML document, elements and attributes from the 
	  * Render Package must use the 
	  * 'http://www.sbml.org/sbml/level3/version1/render/version1' namespace, declaring 
	  * so either explicitly or implicitly. Reference: L3V1 Render V1 Section 3.1 
	  */
 	 public static final int RENDER_10102 = 1310102; 

	 /**
	  * Error code 1310301:
	  * (Extends validation rule #10301 in the SBML Level 3 Core specification. TO DO 
	  * list scope of ids) Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_10301 = 1310301; 

	 /**
	  * Error code 1310302:
	  * The value of a 'render:id' must conform to the syntax of the &lt;sbml&gt; data type 
	  * 'SId' Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_10302 = 1310302; 

	 /**
	  * Error code 1320101:
	  * In all SBML documents using the Render Package, the &lt;sbml&gt; object must have the 
	  * 'render:required' attribute. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_20101 = 1320101; 

	 /**
	  * Error code 1320102:
	  * The value of attribute 'render:required' on the &lt;sbml&gt; object must be of data 
	  * type 'boolean'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_20102 = 1320102; 

	 /**
	  * Error code 1320103:
	  * The value of attribute 'render:required' on the &lt;sbml&gt; object must be set to 
	  * 'false'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_20103 = 1320103; 

	 /**
	  * Error code 1320201:
	  * A &lt;graphicalObject&gt; object may have the optional attribute 'render:objectRole'. 
	  * No other attributes from the SBML Level 3 Render namespaces are permitted on a 
	  * &lt;graphicalObject&gt; object. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_20201 = 1320201; 

	 /**
	  * Error code 1320202:
	  * The attribute 'render:objectRole' on a &lt;graphicalObject&gt; must have a value of 
	  * data type 'string'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_20202 = 1320202; 

	 /**
	  * Error code 1320301:
	  * A &lt;layout&gt; object may contain one and only one instance of the 
	  * &lt;listOfLocalRenderInformation&gt; element. No other elements from the SBML Level 3 
	  * Render namespaces are permitted on a &lt;layout&gt; object. Reference: L3V1 Render V1 
	  * Section 
	  */
 	 public static final int RENDER_20301 = 1320301; 

	 /**
	  * Error code 1320302:
	  * The &lt;listOfLocalRenderInformation&gt; subobject on a &lt;layout&gt; object is optional, 
	  * but if present, this container object must not be empty. Reference: L3V1 Render 
	  * V1 Section 
	  */
 	 public static final int RENDER_20302 = 1320302; 

	 /**
	  * Error code 1320303:
	  * Apart from the general notes and annotations subobjects permitted on all SBML 
	  * objects, a &lt;listOfLocalRenderInformation&gt; container object may only contain 
	  * &lt;localRenderInformation&gt; objects. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_20303 = 1320303; 

	 /**
	  * Error code 1320304:
	  * A &lt;listOfLocalRenderInformation&gt; object may have the optional SBML Level 3 Core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;listOfLocalRenderInformation&gt; object. 
	  * Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_20304 = 1320304; 

	 /**
	  * Error code 1320305:
	  * A &lt;listOfLocalRenderInformation&gt; object may have the optional attributes 
	  * 'render:versionMajor', 'render:versionMinor' and 'render:defaultValues'. No 
	  * other attributes from the SBML Level 3 Render namespaces are permitted on a 
	  * &lt;listOfLocalRenderInformation&gt; object. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_20305 = 1320305; 

	 /**
	  * Error code 1320306:
	  * The attribute 'render:versionMajor' on a &lt;listOfLocalRenderInformation&gt; must 
	  * have a value of data type 'integer', and must be non negative. Reference: L3V1 
	  * Render V1 Section 
	  */
 	 public static final int RENDER_20306 = 1320306; 

	 /**
	  * Error code 1320307:
	  * The attribute 'render:versionMinor' on a &lt;listOfLocalRenderInformation&gt; must 
	  * have a value of data type 'integer', and must be non negative. Reference: L3V1 
	  * Render V1 Section 
	  */
 	 public static final int RENDER_20307 = 1320307; 

	 /**
	  * Error code 1320308:
	  * FIX ME: Encountered an unknown attribute type element in 
	  * ValidationRulesForClass:write_attribute_type_rule Reference: L3V1 Render V1 
	  * Section 
	  */
 	 public static final int RENDER_20308 = 1320308; 

	 /**
	  * Error code 1320401:
	  * A &lt;listOfLayouts&gt; object may contain one and only one instance of the 
	  * &lt;listOfGlobalRenderInformation&gt; element. No other elements from the SBML Level 3 
	  * Render namespaces are permitted on a &lt;listOfLayouts&gt; object. Reference: L3V1 
	  * Render V1 Section 
	  */
 	 public static final int RENDER_20401 = 1320401; 

	 /**
	  * Error code 1320402:
	  * The &lt;listOfGlobalRenderInformation&gt; subobject on a &lt;listOfLayouts&gt; object is 
	  * optional, but if present, this container object must not be empty. Reference: 
	  * L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_20402 = 1320402; 

	 /**
	  * Error code 1320403:
	  * Apart from the general notes and annotations subobjects permitted on all SBML 
	  * objects, a &lt;listOfGlobalRenderInformation&gt; container object may only contain 
	  * &lt;globalRenderInformation&gt; objects. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_20403 = 1320403; 

	 /**
	  * Error code 1320404:
	  * A &lt;listOfGlobalRenderInformation&gt; object may have the optional SBML Level 3 Core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;listOfGlobalRenderInformation&gt; object. 
	  * Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_20404 = 1320404; 

	 /**
	  * Error code 1320405:
	  * A &lt;listOfGlobalRenderInformation&gt; object may have the optional attributes 
	  * 'render:versionMajor', and 'render:versionMinor'. No other attributes from the 
	  * SBML Level 3 Render namespaces are permitted on a 
	  * &lt;listOfGlobalRenderInformation&gt; object. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_20405 = 1320405; 

	 /**
	  * Error code 1320406:
	  * The attribute 'render:versionMajor' on a &lt;listOfLayouts&gt; must have a value of 
	  * data type 'integer', and must be non negative. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_20406 = 1320406; 

	 /**
	  * Error code 1320407:
	  * The attribute 'render:versionMinor' on a &lt;listOfLayouts&gt; must have a value of 
	  * data type 'integer', and must be non negative. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_20407 = 1320407; 

	 /**
	  * Error code 1320408:
	  * FIX ME: Encountered an unknown attribute type element in 
	  * ValidationRulesForClass:write_attribute_type_rule Reference: L3V1 Render V1 
	  * Section 
	  */
 	 public static final int RENDER_20408 = 1320408; 

	 /**
	  * Error code 1320501:
	  * A &lt;colorDefinition&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;colorDefinition&gt;. Reference: L3V1 Render V1 
	  * Section 
	  */
 	 public static final int RENDER_20501 = 1320501; 

	 /**
	  * Error code 1320502:
	  * A &lt;colorDefinition&gt; object may have the optional SBML Level 3 Core subobjects 
	  * for notes and annotations. No other elements from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;colorDefinition&gt;. Reference: L3V1 Render V1 
	  * Section 
	  */
 	 public static final int RENDER_20502 = 1320502; 

	 /**
	  * Error code 1320503:
	  * A &lt;colorDefinition&gt; object must have the required attributes 'render:id' and 
	  * 'render:value', and may have the optional attribute 'render:name'. No other 
	  * attributes from the SBML Level 3 Render namespaces are permitted on a 
	  * &lt;colorDefinition&gt; object. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_20503 = 1320503; 

	 /**
	  * Error code 1320504:
	  * The attribute 'render:value' on a &lt;colorDefinition&gt; must have a value of data 
	  * type 'string'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_20504 = 1320504; 

	 /**
	  * Error code 1320505:
	  * The attribute 'render:name' on a &lt;colorDefinition&gt; must have a value of data 
	  * type 'string'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_20505 = 1320505; 

	 /**
	  * Error code 1320601:
	  * An &lt;ellipse&gt; object may have the optional SBML Level 3 Core attributes 'metaid' 
	  * and 'sboTerm'. No other attributes from the SBML Level 3 Core namespaces are 
	  * permitted on an &lt;ellipse&gt;. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_20601 = 1320601; 

	 /**
	  * Error code 1320602:
	  * An &lt;ellipse&gt; object may have the optional SBML Level 3 Core subobjects for notes 
	  * and annotations. No other elements from the SBML Level 3 Core namespaces are 
	  * permitted on an &lt;ellipse&gt;. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_20602 = 1320602; 

	 /**
	  * Error code 1320603:
	  * An &lt;ellipse&gt; object must have the required attributes 'render:cx', 'render:cy' 
	  * and 'render:rx', and may have the optional attributes 'render:ratio', 
	  * 'render:cz' and 'render:ry'. No other attributes from the SBML Level 3 Render 
	  * namespaces are permitted on an &lt;ellipse&gt; object. Reference: L3V1 Render V1 
	  * Section 
	  */
 	 public static final int RENDER_20603 = 1320603; 

	 /**
	  * Error code 1320604:
	  * The value of the attribute 'render:cx' of an &lt;ellipse&gt; object must conform to 
	  * the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding optionally 
	  * an absolute number followed by an optional relative number followed by a &lt;% 
	  * sign&gt;. Adding spaces between the coordinates is encouraged, but not required. 
	  * Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_20604 = 1320604; 

	 /**
	  * Error code 1320605:
	  * The value of the attribute 'render:cy' of an &lt;ellipse&gt; object must conform to 
	  * the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding optionally 
	  * an absolute number followed by an optional relative number followed by a &lt;% 
	  * sign&gt;. Adding spaces between the coordinates is encouraged, but not required. 
	  * Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_20605 = 1320605; 

	 /**
	  * Error code 1320606:
	  * The value of the attribute 'render:rx' of an &lt;ellipse&gt; object must conform to 
	  * the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding optionally 
	  * an absolute number followed by an optional relative number followed by a &lt;% 
	  * sign&gt;. Adding spaces between the coordinates is encouraged, but not required. 
	  * Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_20606 = 1320606; 

	 /**
	  * Error code 1320607:
	  * The attribute 'render:ratio' on an &lt;ellipse&gt; must have a value of data type 
	  * 'double'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_20607 = 1320607; 

	 /**
	  * Error code 1320608:
	  * The value of the attribute 'render:cz' of an &lt;ellipse&gt; object must conform to 
	  * the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding optionally 
	  * an absolute number followed by an optional relative number followed by a &lt;% 
	  * sign&gt;. Adding spaces between the coordinates is encouraged, but not required. 
	  * Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_20608 = 1320608; 

	 /**
	  * Error code 1320609:
	  * The value of the attribute 'render:ry' of an &lt;ellipse&gt; object must conform to 
	  * the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding optionally 
	  * an absolute number followed by an optional relative number followed by a &lt;% 
	  * sign&gt;. Adding spaces between the coordinates is encouraged, but not required. 
	  * Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_20609 = 1320609; 

	 /**
	  * Error code 1320701:
	  * A &lt;globalRenderInformation&gt; object may have the optional SBML Level 3 Core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;globalRenderInformation&gt;. Reference: L3V1 
	  * Render V1 Section 
	  */
 	 public static final int RENDER_20701 = 1320701; 

	 /**
	  * Error code 1320702:
	  * A &lt;globalRenderInformation&gt; object may have the optional SBML Level 3 Core 
	  * subobjects for notes and annotations. No other elements from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;globalRenderInformation&gt;. Reference: L3V1 
	  * Render V1 Section 
	  */
 	 public static final int RENDER_20702 = 1320702; 

	 /**
	  * Error code 1320703:
	  * A &lt;globalRenderInformation&gt; object may contain one and only one instance of the 
	  * &lt;listOfGlobalStyles&gt; element. No other elements from the SBML Level 3 Render 
	  * namespaces are permitted on a &lt;globalRenderInformation&gt; object. Reference: L3V1 
	  * Render V1 Section 
	  */
 	 public static final int RENDER_20703 = 1320703; 

	 /**
	  * Error code 1320704:
	  * The &lt;listOfGlobalStyles&gt; subobject on a &lt;globalRenderInformation&gt; object is 
	  * optional, but if present, this container object must not be empty. Reference: 
	  * L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_20704 = 1320704; 

	 /**
	  * Error code 1320705:
	  * Apart from the general notes and annotations subobjects permitted on all SBML 
	  * objects, a &lt;listOfGlobalStyles&gt; container object may only contain &lt;globalStyle&gt; 
	  * objects. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_20705 = 1320705; 

	 /**
	  * Error code 1320706:
	  * A &lt;listOfGlobalStyles&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;listOfGlobalStyles&gt; object. Reference: L3V1 
	  * Render V1 Section 
	  */
 	 public static final int RENDER_20706 = 1320706; 

	 /**
	  * Error code 1320801:
	  * A &lt;globalStyle&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;globalStyle&gt;. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_20801 = 1320801; 

	 /**
	  * Error code 1320802:
	  * A &lt;globalStyle&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespaces 
	  * are permitted on a &lt;globalStyle&gt;. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_20802 = 1320802; 

	 /**
	  * Error code 1320901:
	  * A &lt;gradientBase&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;gradientBase&gt;. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_20901 = 1320901; 

	 /**
	  * Error code 1320902:
	  * A &lt;gradientBase&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespaces 
	  * are permitted on a &lt;gradientBase&gt;. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_20902 = 1320902; 

	 /**
	  * Error code 1320903:
	  * A &lt;gradientBase&gt; object must have the required attribute 'render:id', and may 
	  * have the optional attributes 'render:name' and 'render:spreadMethod'. No other 
	  * attributes from the SBML Level 3 Render namespaces are permitted on a 
	  * &lt;gradientBase&gt; object. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_20903 = 1320903; 

	 /**
	  * Error code 1320904:
	  * A &lt;gradientBase&gt; object may contain one and only one instance of the 
	  * &lt;listOfGradientStops&gt; element. No other elements from the SBML Level 3 Render 
	  * namespaces are permitted on a &lt;gradientBase&gt; object. Reference: L3V1 Render V1 
	  * Section 
	  */
 	 public static final int RENDER_20904 = 1320904; 

	 /**
	  * Error code 1320905:
	  * The attribute 'render:name' on a &lt;gradientBase&gt; must have a value of data type 
	  * 'string'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_20905 = 1320905; 

	 /**
	  * Error code 1320906:
	  * The value of the attribute 'render:spreadMethod' of a &lt;gradientBase&gt; object must 
	  * conform to the syntax of SBML data type 'GradientSpreadMethod' and may only take 
	  * on the allowed values of 'GradientSpreadMethod' defined in SBML; that is, the 
	  * value must be one of the following: 'pad', 'reflect' or 'repeat'. Reference: 
	  * L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_20906 = 1320906; 

	 /**
	  * Error code 1321001:
	  * A &lt;gradientStop&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;gradientStop&gt;. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21001 = 1321001; 

	 /**
	  * Error code 1321002:
	  * A &lt;gradientStop&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespaces 
	  * are permitted on a &lt;gradientStop&gt;. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21002 = 1321002; 

	 /**
	  * Error code 1321003:
	  * A &lt;gradientStop&gt; object must have the required attributes 'render:stop-color' 
	  * and 'render:offset'. No other attributes from the SBML Level 3 Render namespaces 
	  * are permitted on a &lt;gradientStop&gt; object. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21003 = 1321003; 

	 /**
	  * Error code 1321004:
	  * The attribute 'render:stop-color' on a &lt;gradientStop&gt; must have a value of data 
	  * type 'string'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21004 = 1321004; 

	 /**
	  * Error code 1321005:
	  * The value of the attribute 'render:offset' of a &lt;gradientStop&gt; object must 
	  * conform to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding 
	  * optionally an absolute number followed by an optional relative number followed 
	  * by a &lt;% sign&gt;. Adding spaces between the coordinates is encouraged, but not 
	  * required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21005 = 1321005; 

	 /**
	  * Error code 1321101:
	  * A &lt;renderGroup&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;renderGroup&gt;. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21101 = 1321101; 

	 /**
	  * Error code 1321102:
	  * A &lt;renderGroup&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespaces 
	  * are permitted on a &lt;renderGroup&gt;. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21102 = 1321102; 

	 /**
	  * Error code 1321103:
	  * A &lt;renderGroup&gt; object may have the optional attributes 'render:startHead', 
	  * 'render:endHead', 'render:font-family', 'render:font-weight', 
	  * 'render:font-style', 'render:text-anchor', 'render:vtext-anchor' and 
	  * 'render:font-size'. No other attributes from the SBML Level 3 Render namespaces 
	  * are permitted on a &lt;renderGroup&gt; object. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21103 = 1321103; 

	 /**
	  * Error code 1321104:
	  * A &lt;renderGroup&gt; object may contain one and only one instance of the 
	  * &lt;listOfElements&gt; element. No other elements from the SBML Level 3 Render 
	  * namespaces are permitted on a &lt;renderGroup&gt; object. Reference: L3V1 Render V1 
	  * Section 
	  */
 	 public static final int RENDER_21104 = 1321104; 

	 /**
	  * Error code 1321105:
	  * The value of the attribute 'render:startHead' of a &lt;renderGroup&gt; object must be 
	  * the identifier of an existing &lt;lineEnding&gt; object defined in the enclosing 
	  * &lt;model&gt; object. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21105 = 1321105; 

	 /**
	  * Error code 1321106:
	  * The value of the attribute 'render:endHead' of a &lt;renderGroup&gt; object must be 
	  * the identifier of an existing &lt;lineEnding&gt; object defined in the enclosing 
	  * &lt;model&gt; object. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21106 = 1321106; 

	 /**
	  * Error code 1321107:
	  * The attribute 'render:font-family' on a &lt;renderGroup&gt; must have a value of data 
	  * type 'string'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21107 = 1321107; 

	 /**
	  * Error code 1321108:
	  * The value of the attribute 'render:font-weight' of a &lt;renderGroup&gt; object must 
	  * conform to the syntax of SBML data type 'FontWeight' and may only take on the 
	  * allowed values of 'FontWeight' defined in SBML; that is, the value must be one 
	  * of the following: 'bold' or 'normal'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21108 = 1321108; 

	 /**
	  * Error code 1321109:
	  * The value of the attribute 'render:font-style' of a &lt;renderGroup&gt; object must 
	  * conform to the syntax of SBML data type 'FontStyle' and may only take on the 
	  * allowed values of 'FontStyle' defined in SBML; that is, the value must be one of 
	  * the following: 'italic' or 'normal'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21109 = 1321109; 

	 /**
	  * Error code 1321110:
	  * The value of the attribute 'render:text-anchor' of a &lt;renderGroup&gt; object must 
	  * conform to the syntax of SBML data type 'HTextAnchor' and may only take on the 
	  * allowed values of 'HTextAnchor' defined in SBML; that is, the value must be one 
	  * of the following: 'start', 'middle' or 'end'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21110 = 1321110; 

	 /**
	  * Error code 1321111:
	  * The value of the attribute 'render:vtext-anchor' of a &lt;renderGroup&gt; object must 
	  * conform to the syntax of SBML data type 'VTextAnchor' and may only take on the 
	  * allowed values of 'VTextAnchor' defined in SBML; that is, the value must be one 
	  * of the following: 'top', 'middle', 'bottom' or 'baseline'. Reference: L3V1 
	  * Render V1 Section 
	  */
 	 public static final int RENDER_21111 = 1321111; 

	 /**
	  * Error code 1321112:
	  * The value of the attribute 'render:font-size' of a &lt;renderGroup&gt; object must 
	  * conform to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding 
	  * optionally an absolute number followed by an optional relative number followed 
	  * by a &lt;% sign&gt;. Adding spaces between the coordinates is encouraged, but not 
	  * required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21112 = 1321112; 

	 /**
	  * Error code 1321201:
	  * An &lt;image&gt; object may have the optional SBML Level 3 Core attributes 'metaid' 
	  * and 'sboTerm'. No other attributes from the SBML Level 3 Core namespaces are 
	  * permitted on an &lt;image&gt;. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21201 = 1321201; 

	 /**
	  * Error code 1321202:
	  * An &lt;image&gt; object may have the optional SBML Level 3 Core subobjects for notes 
	  * and annotations. No other elements from the SBML Level 3 Core namespaces are 
	  * permitted on an &lt;image&gt;. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21202 = 1321202; 

	 /**
	  * Error code 1321203:
	  * An &lt;image&gt; object must have the required attributes 'render:href', 'render:x', 
	  * 'render:y', 'render:width' and 'render:height', and may have the optional 
	  * attributes 'render:id' and 'render:z'. No other attributes from the SBML Level 3 
	  * Render namespaces are permitted on an &lt;image&gt; object. Reference: L3V1 Render V1 
	  * Section 
	  */
 	 public static final int RENDER_21203 = 1321203; 

	 /**
	  * Error code 1321204:
	  * The attribute 'render:href' on an &lt;image&gt; must have a value of data type 
	  * 'string'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21204 = 1321204; 

	 /**
	  * Error code 1321205:
	  * The value of the attribute 'render:x' of an &lt;image&gt; object must conform to the 
	  * syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding optionally an 
	  * absolute number followed by an optional relative number followed by a &lt;% sign&gt;. 
	  * Adding spaces between the coordinates is encouraged, but not required. 
	  * Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21205 = 1321205; 

	 /**
	  * Error code 1321206:
	  * The value of the attribute 'render:y' of an &lt;image&gt; object must conform to the 
	  * syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding optionally an 
	  * absolute number followed by an optional relative number followed by a &lt;% sign&gt;. 
	  * Adding spaces between the coordinates is encouraged, but not required. 
	  * Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21206 = 1321206; 

	 /**
	  * Error code 1321207:
	  * The value of the attribute 'render:width' of an &lt;image&gt; object must conform to 
	  * the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding optionally 
	  * an absolute number followed by an optional relative number followed by a &lt;% 
	  * sign&gt;. Adding spaces between the coordinates is encouraged, but not required. 
	  * Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21207 = 1321207; 

	 /**
	  * Error code 1321208:
	  * The value of the attribute 'render:height' of an &lt;image&gt; object must conform to 
	  * the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding optionally 
	  * an absolute number followed by an optional relative number followed by a &lt;% 
	  * sign&gt;. Adding spaces between the coordinates is encouraged, but not required. 
	  * Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21208 = 1321208; 

 	 // render-21209 see end of file
 	 
	 /**
	  * Error code 1321210:
	  * The value of the attribute 'render:z' of an &lt;image&gt; object must conform to the 
	  * syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding optionally an 
	  * absolute number followed by an optional relative number followed by a &lt;% sign&gt;. 
	  * Adding spaces between the coordinates is encouraged, but not required. 
	  * Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21210 = 1321210; 

	 /**
	  * Error code 1321301:
	  * A &lt;lineEnding&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;lineEnding&gt;. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21301 = 1321301; 

	 /**
	  * Error code 1321302:
	  * A &lt;lineEnding&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespaces 
	  * are permitted on a &lt;lineEnding&gt;. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21302 = 1321302; 

	 /**
	  * Error code 1321303:
	  * A &lt;lineEnding&gt; object must have the required attribute 'render:id', and may have 
	  * the optional attribute 'render:enableRotationalMapping'. No other attributes 
	  * from the SBML Level 3 Render namespaces are permitted on a &lt;lineEnding&gt; object. 
	  * Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21303 = 1321303; 

	 /**
	  * Error code 1321304:
	  * A &lt;lineEnding&gt; object may contain one and only one instance of each of the 
	  * RenderGroup and BoundingBox elements. No other elements from the SBML Level 3 
	  * Render namespaces are permitted on a &lt;lineEnding&gt; object. Reference: L3V1 Render 
	  * V1 Section 
	  */
 	 public static final int RENDER_21304 = 1321304; 

	 /**
	  * Error code 1321305:
	  * The attribute 'render:enableRotationalMapping' on a &lt;lineEnding&gt; must have a 
	  * value of data type 'boolean'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21305 = 1321305; 

	 /**
	  * Error code 1321401:
	  * A &lt;linearGradient&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;linearGradient&gt;. Reference: L3V1 Render V1 
	  * Section 
	  */
 	 public static final int RENDER_21401 = 1321401; 

	 /**
	  * Error code 1321402:
	  * A &lt;linearGradient&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespaces 
	  * are permitted on a &lt;linearGradient&gt;. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21402 = 1321402; 

	 /**
	  * Error code 1321403:
	  * A &lt;linearGradient&gt; object may have the optional attributes 'render:x1', 
	  * 'render:y1', 'render:z1', 'render:x2', 'render:y2' and 'render:z2'. No other 
	  * attributes from the SBML Level 3 Render namespaces are permitted on a 
	  * &lt;linearGradient&gt; object. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21403 = 1321403; 

	 /**
	  * Error code 1321404:
	  * The value of the attribute 'render:x1' of a &lt;linearGradient&gt; object must conform 
	  * to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding 
	  * optionally an absolute number followed by an optional relative number followed 
	  * by a &lt;% sign&gt;. Adding spaces between the coordinates is encouraged, but not 
	  * required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21404 = 1321404; 

	 /**
	  * Error code 1321405:
	  * The value of the attribute 'render:y1' of a &lt;linearGradient&gt; object must conform 
	  * to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding 
	  * optionally an absolute number followed by an optional relative number followed 
	  * by a &lt;% sign&gt;. Adding spaces between the coordinates is encouraged, but not 
	  * required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21405 = 1321405; 

	 /**
	  * Error code 1321406:
	  * The value of the attribute 'render:z1' of a &lt;linearGradient&gt; object must conform 
	  * to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding 
	  * optionally an absolute number followed by an optional relative number followed 
	  * by a &lt;% sign&gt;. Adding spaces between the coordinates is encouraged, but not 
	  * required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21406 = 1321406; 

	 /**
	  * Error code 1321407:
	  * The value of the attribute 'render:x2' of a &lt;linearGradient&gt; object must conform 
	  * to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding 
	  * optionally an absolute number followed by an optional relative number followed 
	  * by a &lt;% sign&gt;. Adding spaces between the coordinates is encouraged, but not 
	  * required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21407 = 1321407; 

	 /**
	  * Error code 1321408:
	  * The value of the attribute 'render:y2' of a &lt;linearGradient&gt; object must conform 
	  * to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding 
	  * optionally an absolute number followed by an optional relative number followed 
	  * by a &lt;% sign&gt;. Adding spaces between the coordinates is encouraged, but not 
	  * required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21408 = 1321408; 

	 /**
	  * Error code 1321409:
	  * The value of the attribute 'render:z2' of a &lt;linearGradient&gt; object must conform 
	  * to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding 
	  * optionally an absolute number followed by an optional relative number followed 
	  * by a &lt;% sign&gt;. Adding spaces between the coordinates is encouraged, but not 
	  * required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21409 = 1321409; 

	 /**
	  * Error code 1321501:
	  * A &lt;localRenderInformation&gt; object may have the optional SBML Level 3 Core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;localRenderInformation&gt;. Reference: L3V1 
	  * Render V1 Section 
	  */
 	 public static final int RENDER_21501 = 1321501; 

	 /**
	  * Error code 1321502:
	  * A &lt;localRenderInformation&gt; object may have the optional SBML Level 3 Core 
	  * subobjects for notes and annotations. No other elements from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;localRenderInformation&gt;. Reference: L3V1 
	  * Render V1 Section 
	  */
 	 public static final int RENDER_21502 = 1321502; 

	 /**
	  * Error code 1321503:
	  * A &lt;localRenderInformation&gt; object may contain one and only one instance of the 
	  * &lt;listOfLocalStyles&gt; element. No other elements from the SBML Level 3 Render 
	  * namespaces are permitted on a &lt;localRenderInformation&gt; object. Reference: L3V1 
	  * Render V1 Section 
	  */
 	 public static final int RENDER_21503 = 1321503; 

	 /**
	  * Error code 1321504:
	  * The &lt;listOfLocalStyles&gt; subobject on a &lt;localRenderInformation&gt; object is 
	  * optional, but if present, this container object must not be empty. Reference: 
	  * L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21504 = 1321504; 

	 /**
	  * Error code 1321505:
	  * Apart from the general notes and annotations subobjects permitted on all SBML 
	  * objects, a &lt;listOfLocalStyles&gt; container object may only contain &lt;localStyle&gt; 
	  * objects. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21505 = 1321505; 

	 /**
	  * Error code 1321506:
	  * A &lt;listOfLocalStyles&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;listOfLocalStyles&gt; object. Reference: L3V1 Render 
	  * V1 Section 
	  */
 	 public static final int RENDER_21506 = 1321506; 

	 /**
	  * Error code 1321601:
	  * A &lt;localStyle&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;localStyle&gt;. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21601 = 1321601; 

	 /**
	  * Error code 1321602:
	  * A &lt;localStyle&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespaces 
	  * are permitted on a &lt;localStyle&gt;. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21602 = 1321602; 

	 /**
	  * Error code 1321603:
	  * A &lt;localStyle&gt; object may have the optional attribute 'render:idList'. No other 
	  * attributes from the SBML Level 3 Render namespaces are permitted on a 
	  * &lt;localStyle&gt; object. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21603 = 1321603; 

	 /**
	  * Error code 1321604:
	  * The attribute 'render:idList' on a &lt;localStyle&gt; must have a value of data type 
	  * 'string'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21604 = 1321604; 

	 /**
	  * Error code 1321701:
	  * A &lt;polygon&gt; object may have the optional SBML Level 3 Core attributes 'metaid' 
	  * and 'sboTerm'. No other attributes from the SBML Level 3 Core namespaces are 
	  * permitted on a &lt;polygon&gt;. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21701 = 1321701; 

	 /**
	  * Error code 1321702:
	  * A &lt;polygon&gt; object may have the optional SBML Level 3 Core subobjects for notes 
	  * and annotations. No other elements from the SBML Level 3 Core namespaces are 
	  * permitted on a &lt;polygon&gt;. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21702 = 1321702; 

	 /**
	  * Error code 1321703:
	  * A &lt;polygon&gt; object may contain one and only one instance of the 
	  * &lt;listOfElements&gt; element. No other elements from the SBML Level 3 Render 
	  * namespaces are permitted on a &lt;polygon&gt; object. Reference: L3V1 Render V1 
	  * Section 
	  */
 	 public static final int RENDER_21703 = 1321703; 

	 /*
	  * Error code 1321704:
	  * The &lt;listOfRenderPoints&gt; subobject on a &lt;polygon&gt; object is optional, but if 
	  * present, this container object must not be empty. Reference: L3V1 Render V1 
	  * Section 
	  */
   // TODO 2020/03: there is a mismatch between the specification and the
   // implemented constraints here!
 	 /**
   * Error code 1321704:
   * A &lt;polygon&gt; object may contain one and only one instance of the
   * &lt;listOfCurveSegments&gt; element from the Layout package. No other
   * elements from the SBML Level 3 Layout namespaces are permitted on a
   * &lt;polygon&gt; object. Reference: L3V1 Render V1 Section 3.10.1
   */
 	 public static final int RENDER_21704 = 1321704; 

	 /**
	  * Error code 1321705:
	  * Apart from the general notes and annotations subobjects permitted on all SBML 
	  * objects, a &lt;listOfRenderPoints&gt; container object may only contain &lt;renderPoint&gt; 
	  * objects. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21705 = 1321705; 

	 /**
	  * Error code 1321706:
	  * A &lt;listOfRenderPoints&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;listOfRenderPoints&gt; object. Reference: L3V1 
	  * Render V1 Section 
	  */
 	 public static final int RENDER_21706 = 1321706; 

	 /**
	  * Error code 1321801:
	  * A &lt;radialGradient&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;radialGradient&gt;. Reference: L3V1 Render V1 
	  * Section 
	  */
 	 public static final int RENDER_21801 = 1321801; 

	 /**
	  * Error code 1321802:
	  * A &lt;radialGradient&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespaces 
	  * are permitted on a &lt;radialGradient&gt;. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21802 = 1321802; 

	 /**
	  * Error code 1321803:
	  * A &lt;radialGradient&gt; object may have the optional attributes 'render:cx', 
	  * 'render:cy', 'render:cz', 'render:r', 'render:fx', 'render:fy' and 'render:fz'. 
	  * No other attributes from the SBML Level 3 Render namespaces are permitted on a 
	  * &lt;radialGradient&gt; object. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21803 = 1321803; 

	 /**
	  * Error code 1321804:
	  * The value of the attribute 'render:cx' of a &lt;radialGradient&gt; object must conform 
	  * to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding 
	  * optionally an absolute number followed by an optional relative number followed 
	  * by a &lt;% sign&gt;. Adding spaces between the coordinates is encouraged, but not 
	  * required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21804 = 1321804; 

	 /**
	  * Error code 1321805:
	  * The value of the attribute 'render:cy' of a &lt;radialGradient&gt; object must conform 
	  * to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding 
	  * optionally an absolute number followed by an optional relative number followed 
	  * by a &lt;% sign&gt;. Adding spaces between the coordinates is encouraged, but not 
	  * required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21805 = 1321805; 

	 /**
	  * Error code 1321806:
	  * The value of the attribute 'render:cz' of a &lt;radialGradient&gt; object must conform 
	  * to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding 
	  * optionally an absolute number followed by an optional relative number followed 
	  * by a &lt;% sign&gt;. Adding spaces between the coordinates is encouraged, but not 
	  * required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21806 = 1321806; 

	 /**
	  * Error code 1321807:
	  * The value of the attribute 'render:r' of a &lt;radialGradient&gt; object must conform 
	  * to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding 
	  * optionally an absolute number followed by an optional relative number followed 
	  * by a &lt;% sign&gt;. Adding spaces between the coordinates is encouraged, but not 
	  * required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21807 = 1321807; 

	 /**
	  * Error code 1321808:
	  * The value of the attribute 'render:fx' of a &lt;radialGradient&gt; object must conform 
	  * to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding 
	  * optionally an absolute number followed by an optional relative number followed 
	  * by a &lt;% sign&gt;. Adding spaces between the coordinates is encouraged, but not 
	  * required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21808 = 1321808; 

	 /**
	  * Error code 1321809:
	  * The value of the attribute 'render:fy' of a &lt;radialGradient&gt; object must conform 
	  * to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding 
	  * optionally an absolute number followed by an optional relative number followed 
	  * by a &lt;% sign&gt;. Adding spaces between the coordinates is encouraged, but not 
	  * required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21809 = 1321809; 

	 /**
	  * Error code 1321810:
	  * The value of the attribute 'render:fz' of a &lt;radialGradient&gt; object must conform 
	  * to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding 
	  * optionally an absolute number followed by an optional relative number followed 
	  * by a &lt;% sign&gt;. Adding spaces between the coordinates is encouraged, but not 
	  * required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21810 = 1321810; 

	 /**
	  * Error code 1321901:
	  * A &lt;rectangle&gt; object may have the optional SBML Level 3 Core attributes 'metaid' 
	  * and 'sboTerm'. No other attributes from the SBML Level 3 Core namespaces are 
	  * permitted on a &lt;rectangle&gt;. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21901 = 1321901; 

	 /**
	  * Error code 1321902:
	  * A &lt;rectangle&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespaces 
	  * are permitted on a &lt;rectangle&gt;. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21902 = 1321902; 

	 /**
	  * Error code 1321903:
	  * A &lt;rectangle&gt; object must have the required attributes 'render:x', 'render:y', 
	  * 'render:width' and 'render:height', and may have the optional attributes 
	  * 'render:ratio', 'render:z', 'render:rX' and 'render:rY'. No other attributes 
	  * from the SBML Level 3 Render namespaces are permitted on a &lt;rectangle&gt; object. 
	  * Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21903 = 1321903; 

	 /**
	  * Error code 1321904:
	  * The value of the attribute 'render:x' of a &lt;rectangle&gt; object must conform to 
	  * the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding optionally 
	  * an absolute number followed by an optional relative number followed by a &lt;% 
	  * sign&gt;. Adding spaces between the coordinates is encouraged, but not required. 
	  * Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21904 = 1321904; 

	 /**
	  * Error code 1321905:
	  * The value of the attribute 'render:y' of a &lt;rectangle&gt; object must conform to 
	  * the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding optionally 
	  * an absolute number followed by an optional relative number followed by a &lt;% 
	  * sign&gt;. Adding spaces between the coordinates is encouraged, but not required. 
	  * Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21905 = 1321905; 

	 /**
	  * Error code 1321906:
	  * The value of the attribute 'render:width' of a &lt;rectangle&gt; object must conform 
	  * to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding 
	  * optionally an absolute number followed by an optional relative number followed 
	  * by a &lt;% sign&gt;. Adding spaces between the coordinates is encouraged, but not 
	  * required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21906 = 1321906; 

	 /**
	  * Error code 1321907:
	  * The value of the attribute 'render:height' of a &lt;rectangle&gt; object must conform 
	  * to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding 
	  * optionally an absolute number followed by an optional relative number followed 
	  * by a &lt;% sign&gt;. Adding spaces between the coordinates is encouraged, but not 
	  * required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21907 = 1321907; 

	 /**
	  * Error code 1321908:
	  * The attribute 'render:ratio' on a &lt;rectangle&gt; must have a value of data type 
	  * 'double'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21908 = 1321908; 

	 /**
	  * Error code 1321909:
	  * The value of the attribute 'render:z' of a &lt;rectangle&gt; object must conform to 
	  * the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding optionally 
	  * an absolute number followed by an optional relative number followed by a &lt;% 
	  * sign&gt;. Adding spaces between the coordinates is encouraged, but not required. 
	  * Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21909 = 1321909; 

	 /**
	  * Error code 1321910:
	  * The value of the attribute 'render:rX' of a &lt;rectangle&gt; object must conform to 
	  * the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding optionally 
	  * an absolute number followed by an optional relative number followed by a &lt;% 
	  * sign&gt;. Adding spaces between the coordinates is encouraged, but not required. 
	  * Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21910 = 1321910; 

	 /**
	  * Error code 1321911:
	  * The value of the attribute 'render:rY' of a &lt;rectangle&gt; object must conform to 
	  * the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding optionally 
	  * an absolute number followed by an optional relative number followed by a &lt;% 
	  * sign&gt;. Adding spaces between the coordinates is encouraged, but not required. 
	  * Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_21911 = 1321911; 

	 /**
	  * Error code 1322001:
	  * A &lt;renderCubicBezier&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;renderCubicBezier&gt;. Reference: L3V1 Render V1 
	  * Section 
	  */
 	 public static final int RENDER_22001 = 1322001; 

	 /**
	  * Error code 1322002:
	  * A &lt;renderCubicBezier&gt; object may have the optional SBML Level 3 Core subobjects 
	  * for notes and annotations. No other elements from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;renderCubicBezier&gt;. Reference: L3V1 Render V1 
	  * Section 
	  */
 	 public static final int RENDER_22002 = 1322002; 

	 /**
	  * Error code 1322003:
	  * A &lt;renderCubicBezier&gt; object must have the required attributes 
	  * 'render:basePoint1_x', 'render:basePoint1_y', 'render:basePoint2_x' and 
	  * 'render:basePoint2_y', and may have the optional attributes 
	  * 'render:basePoint1_z' and 'render:basePoint2_z'. No other attributes from the 
	  * SBML Level 3 Render namespaces are permitted on a &lt;renderCubicBezier&gt; object. 
	  * Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22003 = 1322003; 

	 /**
	  * Error code 1322004:
	  * The value of the attribute 'render:basePoint1_x' of a &lt;renderCubicBezier&gt; object 
	  * must conform to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string 
	  * encoding optionally an absolute number followed by an optional relative number 
	  * followed by a &lt;% sign&gt;. Adding spaces between the coordinates is encouraged, but 
	  * not required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22004 = 1322004; 

	 /**
	  * Error code 1322005:
	  * The value of the attribute 'render:basePoint1_y' of a &lt;renderCubicBezier&gt; object 
	  * must conform to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string 
	  * encoding optionally an absolute number followed by an optional relative number 
	  * followed by a &lt;% sign&gt;. Adding spaces between the coordinates is encouraged, but 
	  * not required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22005 = 1322005; 

	 /**
	  * Error code 1322006:
	  * The value of the attribute 'render:basePoint2_x' of a &lt;renderCubicBezier&gt; object 
	  * must conform to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string 
	  * encoding optionally an absolute number followed by an optional relative number 
	  * followed by a &lt;% sign&gt;. Adding spaces between the coordinates is encouraged, but 
	  * not required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22006 = 1322006; 

	 /**
	  * Error code 1322007:
	  * The value of the attribute 'render:basePoint2_y' of a &lt;renderCubicBezier&gt; object 
	  * must conform to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string 
	  * encoding optionally an absolute number followed by an optional relative number 
	  * followed by a &lt;% sign&gt;. Adding spaces between the coordinates is encouraged, but 
	  * not required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22007 = 1322007; 

	 /**
	  * Error code 1322008:
	  * The value of the attribute 'render:basePoint1_z' of a &lt;renderCubicBezier&gt; object 
	  * must conform to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string 
	  * encoding optionally an absolute number followed by an optional relative number 
	  * followed by a &lt;% sign&gt;. Adding spaces between the coordinates is encouraged, but 
	  * not required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22008 = 1322008; 

	 /**
	  * Error code 1322009:
	  * The value of the attribute 'render:basePoint2_z' of a &lt;renderCubicBezier&gt; object 
	  * must conform to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string 
	  * encoding optionally an absolute number followed by an optional relative number 
	  * followed by a &lt;% sign&gt;. Adding spaces between the coordinates is encouraged, but 
	  * not required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22009 = 1322009; 

	 /**
	  * Error code 1322101:
	  * A &lt;renderCurve&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;renderCurve&gt;. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22101 = 1322101; 

	 /**
	  * Error code 1322102:
	  * A &lt;renderCurve&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespaces 
	  * are permitted on a &lt;renderCurve&gt;. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22102 = 1322102; 

	 /**
	  * Error code 1322103:
	  * A &lt;renderCurve&gt; object may have the optional attributes 'render:startHead' and 
	  * 'render:endHead'. No other attributes from the SBML Level 3 Render namespaces 
	  * are permitted on a &lt;renderCurve&gt; object. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22103 = 1322103; 

	 /**
	  * Error code 1322104:
	  * A &lt;renderCurve&gt; object may contain one and only one instance of the 
	  * &lt;listOfRenderPoints&gt; element. No other elements from the SBML Level 3 Render 
	  * namespaces are permitted on a &lt;renderCurve&gt; object. Reference: L3V1 Render V1 
	  * Section 
	  */
 	 public static final int RENDER_22104 = 1322104; 

	 /**
	  * Error code 1322105:
	  * The value of the attribute 'render:startHead' of a &lt;renderCurve&gt; object must be 
	  * the identifier of an existing &lt;lineEnding&gt; object defined in the enclosing 
	  * &lt;model&gt; object. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22105 = 1322105; 

	 /**
	  * Error code 1322106:
	  * The value of the attribute 'render:endHead' of a &lt;renderCurve&gt; object must be 
	  * the identifier of an existing &lt;lineEnding&gt; object defined in the enclosing 
	  * &lt;model&gt; object. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22106 = 1322106; 

	 /**
	  * Error code 1322107:
	  * The &lt;listOfRenderPoints&gt; subobject on a &lt;renderCurve&gt; object is optional, but if 
	  * present, this container object must not be empty. Reference: L3V1 Render V1 
	  * Section 
	  */
 	 public static final int RENDER_22107 = 1322107; 

	 /**
	  * Error code 1322108:
	  * Apart from the general notes and annotations subobjects permitted on all SBML 
	  * objects, a &lt;listOfRenderPoints&gt; container object may only contain &lt;renderPoint&gt; 
	  * objects. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22108 = 1322108; 

	 /**
	  * Error code 1322109:
	  * A &lt;listOfRenderPoints&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;listOfRenderPoints&gt; object. Reference: L3V1 
	  * Render V1 Section 
	  */
 	 public static final int RENDER_22109 = 1322109; 

	 /**
	  * Error code 1322201:
	  * A &lt;renderPoint&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;renderPoint&gt;. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22201 = 1322201; 

	 /**
	  * Error code 1322202:
	  * A &lt;renderPoint&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespaces 
	  * are permitted on a &lt;renderPoint&gt;. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22202 = 1322202; 

	 /**
	  * Error code 1322203:
	  * A &lt;renderPoint&gt; object must have the required attributes 'render:x' and 
	  * 'render:y', and may have the optional attribute 'render:z'. No other attributes 
	  * from the SBML Level 3 Render namespaces are permitted on a &lt;renderPoint&gt; object. 
	  * Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22203 = 1322203; 

	 /**
	  * Error code 1322204:
	  * The value of the attribute 'render:x' of a &lt;renderPoint&gt; object must conform to 
	  * the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding optionally 
	  * an absolute number followed by an optional relative number followed by a &lt;% 
	  * sign&gt;. Adding spaces between the coordinates is encouraged, but not required. 
	  * Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22204 = 1322204; 

	 /**
	  * Error code 1322205:
	  * The value of the attribute 'render:y' of a &lt;renderPoint&gt; object must conform to 
	  * the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding optionally 
	  * an absolute number followed by an optional relative number followed by a &lt;% 
	  * sign&gt;. Adding spaces between the coordinates is encouraged, but not required. 
	  * Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22205 = 1322205; 

	 /**
	  * Error code 1322206:
	  * The value of the attribute 'render:z' of a &lt;renderPoint&gt; object must conform to 
	  * the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding optionally 
	  * an absolute number followed by an optional relative number followed by a &lt;% 
	  * sign&gt;. Adding spaces between the coordinates is encouraged, but not required. 
	  * Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22206 = 1322206; 

	 /**
	  * Error code 1322301:
	  * A &lt;text&gt; object may have the optional SBML Level 3 Core attributes 'metaid' and 
	  * 'sboTerm'. No other attributes from the SBML Level 3 Core namespaces are 
	  * permitted on a &lt;text&gt;. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22301 = 1322301; 

	 /**
	  * Error code 1322302:
	  * A &lt;text&gt; object may have the optional SBML Level 3 Core subobjects for notes and 
	  * annotations. No other elements from the SBML Level 3 Core namespaces are 
	  * permitted on a &lt;text&gt;. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22302 = 1322302; 

	 /**
	  * Error code 1322303:
	  * A &lt;text&gt; object must have the required attributes 'render:x' and 'render:y', and 
	  * may have the optional attributes 'render:font-family', 'render:font-weight', 
	  * 'render:font-style', 'render:text-anchor', 'render:vtext-anchor', 'render:z' and 
	  * 'render:font-size'. No other attributes from the SBML Level 3 Render namespaces 
	  * are permitted on a &lt;text&gt; object. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22303 = 1322303; 

	 /**
	  * Error code 1322304:
	  * The value of the attribute 'render:x' of a &lt;text&gt; object must conform to the 
	  * syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding optionally an 
	  * absolute number followed by an optional relative number followed by a &lt;% sign&gt;. 
	  * Adding spaces between the coordinates is encouraged, but not required. 
	  * Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22304 = 1322304; 

	 /**
	  * Error code 1322305:
	  * The value of the attribute 'render:y' of a &lt;text&gt; object must conform to the 
	  * syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding optionally an 
	  * absolute number followed by an optional relative number followed by a &lt;% sign&gt;. 
	  * Adding spaces between the coordinates is encouraged, but not required. 
	  * Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22305 = 1322305; 

	 /**
	  * Error code 1322306:
	  * The attribute 'render:font-family' on a &lt;text&gt; must have a value of data type 
	  * 'string'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22306 = 1322306; 

	 /**
	  * Error code 1322307:
	  * The value of the attribute 'render:font-weight' of a &lt;text&gt; object must conform 
	  * to the syntax of SBML data type 'FontWeight' and may only take on the allowed 
	  * values of 'FontWeight' defined in SBML; that is, the value must be one of the 
	  * following: 'bold' or 'normal'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22307 = 1322307; 

	 /**
	  * Error code 1322308:
	  * The value of the attribute 'render:font-style' of a &lt;text&gt; object must conform 
	  * to the syntax of SBML data type 'FontStyle' and may only take on the allowed 
	  * values of 'FontStyle' defined in SBML; that is, the value must be one of the 
	  * following: 'italic' or 'normal'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22308 = 1322308; 

	 /**
	  * Error code 1322309:
	  * The value of the attribute 'render:text-anchor' of a &lt;text&gt; object must conform 
	  * to the syntax of SBML data type 'HTextAnchor' and may only take on the allowed 
	  * values of 'HTextAnchor' defined in SBML; that is, the value must be one of the 
	  * following: 'start', 'middle' or 'end'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22309 = 1322309; 

	 /**
	  * Error code 1322310:
	  * The value of the attribute 'render:vtext-anchor' of a &lt;text&gt; object must conform 
	  * to the syntax of SBML data type 'VTextAnchor' and may only take on the allowed 
	  * values of 'VTextAnchor' defined in SBML; that is, the value must be one of the 
	  * following: 'top', 'middle', 'bottom' or 'baseline'. Reference: L3V1 Render V1 
	  * Section 
	  */
 	 public static final int RENDER_22310 = 1322310; 

	 /**
	  * Error code 1322311:
	  * The value of the attribute 'render:z' of a &lt;text&gt; object must conform to the 
	  * syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding optionally an 
	  * absolute number followed by an optional relative number followed by a &lt;% sign&gt;. 
	  * Adding spaces between the coordinates is encouraged, but not required. 
	  * Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22311 = 1322311; 

	 /**
	  * Error code 1322312:
	  * The value of the attribute 'render:font-size' of a &lt;text&gt; object must conform to 
	  * the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding optionally 
	  * an absolute number followed by an optional relative number followed by a &lt;% 
	  * sign&gt;. Adding spaces between the coordinates is encouraged, but not required. 
	  * Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22312 = 1322312; 

	 /**
	  * Error code 1322401:
	  * A &lt;transformation2D&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;transformation2D&gt;. Reference: L3V1 Render V1 
	  * Section 
	  */
 	 public static final int RENDER_22401 = 1322401; 

	 /**
	  * Error code 1322402:
	  * A &lt;transformation2D&gt; object may have the optional SBML Level 3 Core subobjects 
	  * for notes and annotations. No other elements from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;transformation2D&gt;. Reference: L3V1 Render V1 
	  * Section 
	  */
 	 public static final int RENDER_22402 = 1322402; 

	 /**
	  * Error code 1322501:
	  * A &lt;transformation&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;transformation&gt;. Reference: L3V1 Render V1 
	  * Section 
	  */
 	 public static final int RENDER_22501 = 1322501; 

	 /**
	  * Error code 1322502:
	  * A &lt;transformation&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespaces 
	  * are permitted on a &lt;transformation&gt;. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22502 = 1322502; 

	 /**
	  * Error code 1322503:
	  * A &lt;transformation&gt; object may have the optional attributes 'render:transform' 
	  * and 'render:name'. No other attributes from the SBML Level 3 Render namespaces 
	  * are permitted on a &lt;transformation&gt; object. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22503 = 1322503; 

	 /**
	  * Error code 1322504:
	  * The value of the attribute 'render:transform' of a &lt;transformation&gt; object must 
	  * be an array of values of type 'double'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22504 = 1322504; 

	 /**
	  * Error code 1322505:
	  * The attribute 'render:name' on a &lt;transformation&gt; must have a value of data type 
	  * 'string'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22505 = 1322505; 

	 /**
	  * Error code 1322601:
	  * A &lt;graphicalPrimitive1D&gt; object may have the optional SBML Level 3 Core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;graphicalPrimitive1D&gt;. Reference: L3V1 
	  * Render V1 Section 
	  */
 	 public static final int RENDER_22601 = 1322601; 

	 /**
	  * Error code 1322602:
	  * A &lt;graphicalPrimitive1D&gt; object may have the optional SBML Level 3 Core 
	  * subobjects for notes and annotations. No other elements from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;graphicalPrimitive1D&gt;. Reference: L3V1 
	  * Render V1 Section 
	  */
 	 public static final int RENDER_22602 = 1322602; 

	 /**
	  * Error code 1322603:
	  * A &lt;graphicalPrimitive1D&gt; object may have the optional attributes 'render:id', 
	  * 'render:stroke', 'render:stroke-width' and 'render:stroke-dasharray'. No other 
	  * attributes from the SBML Level 3 Render namespaces are permitted on a 
	  * &lt;graphicalPrimitive1D&gt; object. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22603 = 1322603; 

	 /**
	  * Error code 1322604:
	  * The attribute 'render:stroke' on a &lt;graphicalPrimitive1D&gt; must have a value of 
	  * data type 'string'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22604 = 1322604; 

	 /**
	  * Error code 1322605:
	  * The attribute 'render:stroke-width' on a &lt;graphicalPrimitive1D&gt; must have a 
	  * value of data type 'double'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22605 = 1322605; 

	 /**
	  * Error code 1322606:
	  * The value of the attribute 'render:stroke-dasharray' of a &lt;graphicalPrimitive1D&gt; 
	  * object must be an array of values of type 'unsignedint'. Reference: L3V1 Render 
	  * V1 Section 
	  */
 	 public static final int RENDER_22606 = 1322606; 

	 /**
	  * Error code 1322701:
	  * A &lt;graphicalPrimitive2D&gt; object may have the optional SBML Level 3 Core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;graphicalPrimitive2D&gt;. Reference: L3V1 
	  * Render V1 Section 
	  */
 	 public static final int RENDER_22701 = 1322701; 

	 /**
	  * Error code 1322702:
	  * A &lt;graphicalPrimitive2D&gt; object may have the optional SBML Level 3 Core 
	  * subobjects for notes and annotations. No other elements from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;graphicalPrimitive2D&gt;. Reference: L3V1 
	  * Render V1 Section 
	  */
 	 public static final int RENDER_22702 = 1322702; 

	 /**
	  * Error code 1322703:
	  * A &lt;graphicalPrimitive2D&gt; object may have the optional attributes 'render:fill' 
	  * and 'render:fill-rule'. No other attributes from the SBML Level 3 Render 
	  * namespaces are permitted on a &lt;graphicalPrimitive2D&gt; object. Reference: L3V1 
	  * Render V1 Section 
	  */
 	 public static final int RENDER_22703 = 1322703; 

	 /**
	  * Error code 1322704:
	  * The attribute 'render:fill' on a &lt;graphicalPrimitive2D&gt; must have a value of 
	  * data type 'string'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22704 = 1322704; 

	 /**
	  * Error code 1322705:
	  * The value of the attribute 'render:fill-rule' of a &lt;graphicalPrimitive2D&gt; object 
	  * must conform to the syntax of SBML data type 'FillRule' and may only take on the 
	  * allowed values of 'FillRule' defined in SBML; that is, the value must be one of 
	  * the following: 'nonzero' or 'evenodd'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22705 = 1322705; 

	 /**
	  * Error code 1322801:
	  * A &lt;style&gt; object may have the optional SBML Level 3 Core attributes 'metaid' and 
	  * 'sboTerm'. No other attributes from the SBML Level 3 Core namespaces are 
	  * permitted on a &lt;style&gt;. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22801 = 1322801; 

	 /**
	  * Error code 1322802:
	  * A &lt;style&gt; object may have the optional SBML Level 3 Core subobjects for notes 
	  * and annotations. No other elements from the SBML Level 3 Core namespaces are 
	  * permitted on a &lt;style&gt;. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22802 = 1322802; 

	 /**
	  * Error code 1322803:
	  * A &lt;style&gt; object may have the optional attributes 'render:id', 'render:name', 
	  * 'render:roleList' and 'render:typeList'. No other attributes from the SBML Level 
	  * 3 Render namespaces are permitted on a &lt;style&gt; object. Reference: L3V1 Render V1 
	  * Section 
	  */
 	 public static final int RENDER_22803 = 1322803; 

	 /**
	  * Error code 1322804:
	  * A &lt;style&gt; object may contain one and only one instance of the RenderGroup 
	  * element. No other elements from the SBML Level 3 Render namespaces are permitted 
	  * on a &lt;style&gt; object. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22804 = 1322804; 

	 /**
	  * Error code 1322805:
	  * The attribute 'render:name' on a &lt;style&gt; must have a value of data type 
	  * 'string'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22805 = 1322805; 

	 /**
	  * Error code 1322806:
	  * The attribute 'render:roleList' on a &lt;style&gt; must have a value of data type 
	  * 'string'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22806 = 1322806; 

	 /**
	  * Error code 1322807:
	  * The attribute 'render:typeList' on a &lt;style&gt; must have a value of data type 
	  * 'string'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22807 = 1322807; 

	 /**
	  * Error code 1322901:
	  * A &lt;renderInformationBase&gt; object may have the optional SBML Level 3 Core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;renderInformationBase&gt;. Reference: L3V1 
	  * Render V1 Section 
	  */
 	 public static final int RENDER_22901 = 1322901; 

	 /**
	  * Error code 1322902:
	  * A &lt;renderInformationBase&gt; object may have the optional SBML Level 3 Core 
	  * subobjects for notes and annotations. No other elements from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;renderInformationBase&gt;. Reference: L3V1 
	  * Render V1 Section 
	  */
 	 public static final int RENDER_22902 = 1322902; 

	 /**
	  * Error code 1322903:
	  * A &lt;renderInformationBase&gt; object must have the required attribute 'render:id', 
	  * and may have the optional attributes 'render:name', 'render:programName', 
	  * 'render:programVersion', 'render:referenceRenderInformation' and 
	  * 'render:backgroundColor'. No other attributes from the SBML Level 3 Render 
	  * namespaces are permitted on a &lt;renderInformationBase&gt; object. Reference: L3V1 
	  * Render V1 Section 
	  */
 	 public static final int RENDER_22903 = 1322903; 

	 /**
	  * Error code 1322904:
	  * A &lt;renderInformationBase&gt; object may contain one and only one instance of each 
	  * of the &lt;listOfColorDefinitions,&gt; &lt;listOfGradientBases&gt; and &lt;listOfLineEndings&gt; 
	  * elements. No other elements from the SBML Level 3 Render namespaces are 
	  * permitted on a &lt;renderInformationBase&gt; object. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22904 = 1322904; 

	 /**
	  * Error code 1322905:
	  * The attribute 'render:name' on a &lt;renderInformationBase&gt; must have a value of 
	  * data type 'string'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22905 = 1322905; 

	 /**
	  * Error code 1322906:
	  * The attribute 'render:programName' on a &lt;renderInformationBase&gt; must have a 
	  * value of data type 'string'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22906 = 1322906; 

	 /**
	  * Error code 1322907:
	  * The attribute 'render:programVersion' on a &lt;renderInformationBase&gt; must have a 
	  * value of data type 'string'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22907 = 1322907; 

	 /**
	  * Error code 1322908:
	  * The value of the attribute 'render:referenceRenderInformation' of a 
	  * &lt;renderInformationBase&gt; object must be the identifier of an existing 
	  * &lt;renderInformationBase&gt; object defined in the enclosing &lt;model&gt; object. 
	  * Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22908 = 1322908; 

	 /**
	  * Error code 1322909:
	  * The attribute 'render:backgroundColor' on a &lt;renderInformationBase&gt; must have a 
	  * value of data type 'string'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22909 = 1322909; 

	 /**
	  * Error code 1322910:
	  * The &lt;listOfColorDefinitions,&gt; &lt;listOfGradientBases&gt; and &lt;listOfLineEndings&gt; 
	  * subobjects on a &lt;renderInformationBase&gt; object are optional, but if present, 
	  * these container objects must not be empty.
	  * Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22910 = 1322910; 

	 /**
	  * Error code 1322911:
	  * Apart from the general notes and annotations subobjects permitted on all SBML 
	  * objects, a &lt;listOfColorDefinitions&gt; container object may only contain 
	  * &lt;colorDefinition&gt; objects. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22911 = 1322911; 

	 /**
	  * Error code 1322912:
	  * Apart from the general notes and annotations subobjects permitted on all SBML 
	  * objects, a &lt;listOfGradientBases&gt; container object may only contain 
	  * &lt;gradientBase&gt; objects. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22912 = 1322912; 

	 /**
	  * Error code 1322913:
	  * Apart from the general notes and annotations subobjects permitted on all SBML 
	  * objects, a &lt;listOfLineEndings&gt; container object may only contain &lt;lineEnding&gt; 
	  * objects. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22913 = 1322913; 

	 /**
	  * Error code 1322914:
	  * A &lt;listOfColorDefinitions&gt; object may have the optional SBML Level 3 Core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;listOfColorDefinitions&gt; object. Reference: 
	  * L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_22914 = 1322914; 

	 /**
	  * Error code 1322915:
	  * A &lt;listOfGradientBases&gt; object may have the optional SBML Level 3 Core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;listOfGradientBases&gt; object. Reference: L3V1 
	  * Render V1 Section 
	  */
 	 public static final int RENDER_22915 = 1322915; 

	 /**
	  * Error code 1322916:
	  * A &lt;listOfLineEndings&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;listOfLineEndings&gt; object. Reference: L3V1 Render 
	  * V1 Section 
	  */
 	 public static final int RENDER_22916 = 1322916; 

	 /**
	  * Error code 1323001:
	  * A &lt;defaultValues&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;defaultValues&gt;. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_23001 = 1323001; 

	 /**
	  * Error code 1323002:
	  * A &lt;defaultValues&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespaces 
	  * are permitted on a &lt;defaultValues&gt;. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_23002 = 1323002; 

	 /**
	  * Error code 1323003:
	  * A &lt;defaultValues&gt; object may have the optional attributes 
	  * 'render:backgroundColor', 'render:spreadMethod', 'render:fill', 
	  * 'render:fill-rule', 'render:stroke', 'render:stroke-width', 
	  * 'render:font-family', 'render:font-weight', 'render:font-style', 
	  * 'render:text-anchor', 'render:vtext-anchor', 'render:startHead', 
	  * 'render:endHead', 'render:enableRotationalMapping', 'render:linearGradient_x1', 
	  * 'render:linearGradient_y1', 'render:linearGradient_z1', 
	  * 'render:linearGradient_x2', 'render:linearGradient_y2', 
	  * 'render:linearGradient_z2', 'render:radialGradient_cx', 
	  * 'render:radialGradient_cy', 'render:radialGradient_cz', 
	  * 'render:radialGradient_r', 'render:radialGradient_fx', 
	  * 'render:radialGradient_fy', 'render:radialGradient_fz', 'render:default_z' and 
	  * 'render:font-size'. No other attributes from the SBML Level 3 Render namespaces 
	  * are permitted on a &lt;defaultValues&gt; object. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_23003 = 1323003; 

	 /**
	  * Error code 1323004:
	  * The attribute 'render:backgroundColor' on a &lt;defaultValues&gt; must have a value of 
	  * data type 'string'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_23004 = 1323004; 

	 /**
	  * Error code 1323005:
	  * The value of the attribute 'render:spreadMethod' of a &lt;defaultValues&gt; object 
	  * must conform to the syntax of SBML data type 'GradientSpreadMethod' and may only 
	  * take on the allowed values of 'GradientSpreadMethod' defined in SBML; that is, 
	  * the value must be one of the following: 'pad', 'reflect' or 'repeat'. Reference: 
	  * L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_23005 = 1323005; 

	 /**
	  * Error code 1323006:
	  * The attribute 'render:fill' on a &lt;defaultValues&gt; must have a value of data type 
	  * 'string'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_23006 = 1323006; 

	 /**
	  * Error code 1323007:
	  * The value of the attribute 'render:fill-rule' of a &lt;defaultValues&gt; object must 
	  * conform to the syntax of SBML data type 'FillRule' and may only take on the 
	  * allowed values of 'FillRule' defined in SBML; that is, the value must be one of 
	  * the following: 'nonzero' or 'evenodd'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_23007 = 1323007; 

	 /**
	  * Error code 1323008:
	  * The attribute 'render:stroke' on a &lt;defaultValues&gt; must have a value of data 
	  * type 'string'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_23008 = 1323008; 

	 /**
	  * Error code 1323009:
	  * The attribute 'render:stroke-width' on a &lt;defaultValues&gt; must have a value of 
	  * data type 'double'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_23009 = 1323009; 

	 /**
	  * Error code 1323010:
	  * The attribute 'render:font-family' on a &lt;defaultValues&gt; must have a value of 
	  * data type 'string'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_23010 = 1323010; 

	 /**
	  * Error code 1323011:
	  * The value of the attribute 'render:font-weight' of a &lt;defaultValues&gt; object must 
	  * conform to the syntax of SBML data type 'FontWeight' and may only take on the 
	  * allowed values of 'FontWeight' defined in SBML; that is, the value must be one 
	  * of the following: 'bold' or 'normal'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_23011 = 1323011; 

	 /**
	  * Error code 1323012:
	  * The value of the attribute 'render:font-style' of a &lt;defaultValues&gt; object must 
	  * conform to the syntax of SBML data type 'FontStyle' and may only take on the 
	  * allowed values of 'FontStyle' defined in SBML; that is, the value must be one of 
	  * the following: 'italic' or 'normal'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_23012 = 1323012; 

	 /**
	  * Error code 1323013:
	  * The value of the attribute 'render:text-anchor' of a &lt;defaultValues&gt; object must 
	  * conform to the syntax of SBML data type 'HTextAnchor' and may only take on the 
	  * allowed values of 'HTextAnchor' defined in SBML; that is, the value must be one 
	  * of the following: 'start', 'middle' or 'end'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_23013 = 1323013; 

	 /**
	  * Error code 1323014:
	  * The value of the attribute 'render:vtext-anchor' of a &lt;defaultValues&gt; object 
	  * must conform to the syntax of SBML data type 'VTextAnchor' and may only take on 
	  * the allowed values of 'VTextAnchor' defined in SBML; that is, the value must be 
	  * one of the following: 'top', 'middle', 'bottom' or 'baseline'. Reference: L3V1 
	  * Render V1 Section 
	  */
 	 public static final int RENDER_23014 = 1323014; 

	 /**
	  * Error code 1323015:
	  * The value of the attribute 'render:startHead' of a &lt;defaultValues&gt; object must 
	  * be the identifier of an existing &lt;lineEnding&gt; object defined in the enclosing 
	  * &lt;model&gt; object. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_23015 = 1323015; 

	 /**
	  * Error code 1323016:
	  * The value of the attribute 'render:endHead' of a &lt;defaultValues&gt; object must be 
	  * the identifier of an existing &lt;lineEnding&gt; object defined in the enclosing 
	  * &lt;model&gt; object. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_23016 = 1323016; 

	 /**
	  * Error code 1323017:
	  * The attribute 'render:enableRotationalMapping' on a &lt;defaultValues&gt; must have a 
	  * value of data type 'boolean'. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_23017 = 1323017; 

	 /**
	  * Error code 1323018:
	  * The value of the attribute 'render:linearGradient_x1' of a &lt;defaultValues&gt; 
	  * object must conform to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a 
	  * string encoding optionally an absolute number followed by an optional relative 
	  * number followed by a &lt;% sign&gt;. Adding spaces between the coordinates is 
	  * encouraged, but not required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_23018 = 1323018; 

	 /**
	  * Error code 1323019:
	  * The value of the attribute 'render:linearGradient_y1' of a &lt;defaultValues&gt; 
	  * object must conform to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a 
	  * string encoding optionally an absolute number followed by an optional relative 
	  * number followed by a &lt;% sign&gt;. Adding spaces between the coordinates is 
	  * encouraged, but not required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_23019 = 1323019; 

	 /**
	  * Error code 1323020:
	  * The value of the attribute 'render:linearGradient_z1' of a &lt;defaultValues&gt; 
	  * object must conform to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a 
	  * string encoding optionally an absolute number followed by an optional relative 
	  * number followed by a &lt;% sign&gt;. Adding spaces between the coordinates is 
	  * encouraged, but not required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_23020 = 1323020; 

	 /**
	  * Error code 1323021:
	  * The value of the attribute 'render:linearGradient_x2' of a &lt;defaultValues&gt; 
	  * object must conform to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a 
	  * string encoding optionally an absolute number followed by an optional relative 
	  * number followed by a &lt;% sign&gt;. Adding spaces between the coordinates is 
	  * encouraged, but not required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_23021 = 1323021; 

	 /**
	  * Error code 1323022:
	  * The value of the attribute 'render:linearGradient_y2' of a &lt;defaultValues&gt; 
	  * object must conform to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a 
	  * string encoding optionally an absolute number followed by an optional relative 
	  * number followed by a &lt;% sign&gt;. Adding spaces between the coordinates is 
	  * encouraged, but not required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_23022 = 1323022; 

	 /**
	  * Error code 1323023:
	  * The value of the attribute 'render:linearGradient_z2' of a &lt;defaultValues&gt; 
	  * object must conform to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a 
	  * string encoding optionally an absolute number followed by an optional relative 
	  * number followed by a &lt;% sign&gt;. Adding spaces between the coordinates is 
	  * encouraged, but not required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_23023 = 1323023; 

	 /**
	  * Error code 1323024:
	  * The value of the attribute 'render:radialGradient_cx' of a &lt;defaultValues&gt; 
	  * object must conform to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a 
	  * string encoding optionally an absolute number followed by an optional relative 
	  * number followed by a &lt;% sign&gt;. Adding spaces between the coordinates is 
	  * encouraged, but not required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_23024 = 1323024; 

	 /**
	  * Error code 1323025:
	  * The value of the attribute 'render:radialGradient_cy' of a &lt;defaultValues&gt; 
	  * object must conform to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a 
	  * string encoding optionally an absolute number followed by an optional relative 
	  * number followed by a &lt;% sign&gt;. Adding spaces between the coordinates is 
	  * encouraged, but not required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_23025 = 1323025; 

	 /**
	  * Error code 1323026:
	  * The value of the attribute 'render:radialGradient_cz' of a &lt;defaultValues&gt; 
	  * object must conform to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a 
	  * string encoding optionally an absolute number followed by an optional relative 
	  * number followed by a &lt;% sign&gt;. Adding spaces between the coordinates is 
	  * encouraged, but not required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_23026 = 1323026; 

	 /**
	  * Error code 1323027:
	  * The value of the attribute 'render:radialGradient_r' of a &lt;defaultValues&gt; object 
	  * must conform to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string 
	  * encoding optionally an absolute number followed by an optional relative number 
	  * followed by a &lt;% sign&gt;. Adding spaces between the coordinates is encouraged, but 
	  * not required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_23027 = 1323027; 

	 /**
	  * Error code 1323028:
	  * The value of the attribute 'render:radialGradient_fx' of a &lt;defaultValues&gt; 
	  * object must conform to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a 
	  * string encoding optionally an absolute number followed by an optional relative 
	  * number followed by a &lt;% sign&gt;. Adding spaces between the coordinates is 
	  * encouraged, but not required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_23028 = 1323028; 

	 /**
	  * Error code 1323029:
	  * The value of the attribute 'render:radialGradient_fy' of a &lt;defaultValues&gt; 
	  * object must conform to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a 
	  * string encoding optionally an absolute number followed by an optional relative 
	  * number followed by a &lt;% sign&gt;. Adding spaces between the coordinates is 
	  * encouraged, but not required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_23029 = 1323029; 

	 /**
	  * Error code 1323030:
	  * The value of the attribute 'render:radialGradient_fz' of a &lt;defaultValues&gt; 
	  * object must conform to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a 
	  * string encoding optionally an absolute number followed by an optional relative 
	  * number followed by a &lt;% sign&gt;. Adding spaces between the coordinates is 
	  * encouraged, but not required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_23030 = 1323030; 

	 /**
	  * Error code 1323031:
	  * The value of the attribute 'render:default_z' of a &lt;defaultValues&gt; object must 
	  * conform to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding 
	  * optionally an absolute number followed by an optional relative number followed 
	  * by a &lt;% sign&gt;. Adding spaces between the coordinates is encouraged, but not 
	  * required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_23031 = 1323031; 

	 /**
	  * Error code 1323032:
	  * The value of the attribute 'render:font-size' of a &lt;defaultValues&gt; object must 
	  * conform to the syntax of SBML data type &lt;relAbsVector,&gt; i.e., a string encoding 
	  * optionally an absolute number followed by an optional relative number followed 
	  * by a &lt;% sign&gt;. Adding spaces between the coordinates is encouraged, but not 
	  * required. Reference: L3V1 Render V1 Section 
	  */
 	 public static final int RENDER_23032 = 1323032; 

	 /**
	  * Error code 1510101:
	  * To conform to the Distributions Package specification for SBML Level 3 Version 
	  * 1, an SBML document must declare 
	  * 'http://www.sbml.org/sbml/level3/version1/distrib/version1' as the XMLNamespace 
	  * to use for elements of this package. Reference: L3V1 Distrib V1 Section 3.1 
	  */
 	 public static final int DISTRIB_10101 = 1510101; 

	 /**
	  * Error code 1510102:
	  * Wherever they appear in an SBML document, elements and attributes from the 
	  * Distributions Package must use the 
	  * 'http://www.sbml.org/sbml/level3/version1/distrib/version1' namespace, declaring 
	  * so either explicitly or implicitly. Reference: L3V1 Distrib V1 Section 3.1 
	  */
 	 public static final int DISTRIB_10102 = 1510102; 

	 /**
	  * Error code 1510301:
	  * (Extends validation rule #10301 in the SBML Level 3 Core specification. TO DO 
	  * list scope of ids) Reference: L3V1 Distrib V1 Section 
	  */
 	 public static final int DISTRIB_10301 = 1510301; 

	 /**
	  * Error code 1510302:
	  * The value of a 'distrib:id' must conform to the syntax of the &lt;sbml&gt; data type 
	  * 'SId' Reference: L3V1 Distrib V1 Section 
	  */
 	 public static final int DISTRIB_10302 = 1510302; 

	 /**
	  * Error code 1520101:
	  * In all SBML documents using the Distributions Package, the &lt;sbml&gt; object must 
	  * have the 'distrib:required' attribute. Reference: L3V1 Distrib V1 Section 
	  */
 	 public static final int DISTRIB_20101 = 1520101; 

	 /**
	  * Error code 1520102:
	  * The value of attribute 'distrib:required' on the &lt;sbml&gt; object must be of data 
	  * type 'boolean'. Reference: L3V1 Distrib V1 Section 
	  */
 	 public static final int DISTRIB_20102 = 1520102; 

	 /**
	  * Error code 1520103:
	  * The value of attribute 'distrib:required' on the &lt;sbml&gt; object must be set to 
	  * 'true'. Reference: L3V1 Distrib V1 Section 
	  */
 	 public static final int DISTRIB_20103 = 1520103; 

	 /**
	  * Error code 1520201:
	  * A 'SBase' object may contain one and only one instance of the 
	  * &lt;listOfUncertainties&gt; element. No other elements from the SBML Level 3 
	  * Distributions namespaces are permitted on a 'SBase' object. Reference: L3V1 
	  * Distrib V1 Section 
	  */
 	 public static final int DISTRIB_20201 = 1520201; 

	 /**
	  * Error code 1520202:
	  * Apart from the general notes and annotations subobjects permitted on all SBML 
	  * objects, a &lt;listOfUncertainties&gt; container object may only contain &lt;uncertainty&gt; 
	  * objects. Reference: L3V1 Distrib V1 Section 
	  */
 	 public static final int DISTRIB_20202 = 1520202; 

	 /**
	  * Error code 1520203:
	  * A &lt;listOfUncertainties&gt; object may have the optional SBML Level 3 Core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;listOfUncertainties&gt; object. Reference: L3V1 
	  * Distrib V1 Section 
	  */
 	 public static final int DISTRIB_20203 = 1520203; 

	 /**
	  * Error code 1520301:
	  * An &lt;uncertParameter&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on an &lt;uncertParameter&gt;. Reference: L3V1 Distrib V1 
	  * Section 
	  */
 	 public static final int DISTRIB_20301 = 1520301; 

	 /**
	  * Error code 1520302:
	  * An &lt;uncertParameter&gt; object may have the optional SBML Level 3 Core subobjects 
	  * for notes and annotations. No other elements from the SBML Level 3 Core 
	  * namespaces are permitted on an &lt;uncertParameter&gt;. Reference: L3V1 Distrib V1 
	  * Section 
	  */
 	 public static final int DISTRIB_20302 = 1520302; 

	 /**
	  * Error code 1520303:
	  * An &lt;uncertParameter&gt; object may have the optional attributes 'distrib:value', 
	  * 'distrib:var', 'distrib:units', 'distrib:type' and 'distrib:definitionURL'. No 
	  * other attributes from the SBML Level 3 Distributions namespaces are permitted on 
	  * an &lt;uncertParameter&gt; object. Reference: L3V1 Distrib V1 Section 
	  */
 	 public static final int DISTRIB_20303 = 1520303; 

	 /**
	  * Error code 1520304:
	  * An &lt;uncertParameter&gt; object may contain one and only one instance of the 
	  * &lt;listOfUncertParameters&gt; element. No other elements from the SBML Level 3 
	  * Distributions namespaces are permitted on an &lt;uncertParameter&gt; object. 
	  * Reference: L3V1 Distrib V1 Section 
	  */
 	 public static final int DISTRIB_20304 = 1520304; 

	 /**
	  * Error code 1520305:
	  * The attribute 'distrib:value' on an &lt;uncertParameter&gt; must have a value of data 
	  * type 'double'. Reference: L3V1 Distrib V1 Section 
	  */
 	 public static final int DISTRIB_20305 = 1520305; 

	 /**
	  * Error code 1520306:
	  * The value of the attribute 'distrib:var' of an &lt;uncertParameter&gt; object must be 
	  * the identifier of an existing object derived from the 'SBase' class and defined 
	  * in the enclosing &lt;model&gt; object. Reference: L3V1 Distrib V1 Section 
	  */
 	 public static final int DISTRIB_20306 = 1520306; 

	 /**
	  * Error code 1520307:
	  * The value of the attribute 'distrib:units' on an &lt;uncertParameter&gt; must have a 
	  * taken from the following: the identifier of a &lt;unitDefinition&gt; object in the 
	  * enclosing &lt;model,&gt; or one of the base units in SBML. Reference: L3V1 Distrib V1 
	  * Section 
	  */
 	 public static final int DISTRIB_20307 = 1520307; 

	 /**
	  * Error code 1520308:
	  * The value of the attribute 'distrib:type' of an &lt;uncertParameter&gt; object must 
	  * conform to the syntax of SBML data type 'UncertType' and may only take on the 
	  * allowed values of 'UncertType' defined in SBML; that is, the value must be one 
	  * of the following: 'distribution', 'externalParameter', 'coeffientOfVariation', 
	  * 'kurtosis', 'mean', 'median', 'mode', 'sampleSize', 'skewness', 
	  * 'standardDeviation', 'standardError', 'variance', 'confidenceInterval', 
	  * 'credibleInterval', 'interquartileRange' or 'range'. Reference: L3V1 Distrib V1 
	  * Section 
	  */
 	 public static final int DISTRIB_20308 = 1520308; 

	 /**
	  * Error code 1520309:
	  * The attribute 'distrib:definitionURL' on an &lt;uncertParameter&gt; must have a value 
	  * of data type 'string'. Reference: L3V1 Distrib V1 Section 
	  */
 	 public static final int DISTRIB_20309 = 1520309; 

	 /**
	  * Error code 1520310:
	  * Apart from the general notes and annotations subobjects permitted on all SBML 
	  * objects, a &lt;listOfUncertParameters&gt; container object may only contain 
	  * &lt;uncertParameter&gt; objects. Reference: L3V1 Distrib V1 Section 
	  */
 	 public static final int DISTRIB_20310 = 1520310; 

	 /**
	  * Error code 1520311:
	  * A &lt;listOfUncertParameters&gt; object may have the optional SBML Level 3 Core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 
	  * Core namespaces are permitted on a &lt;listOfUncertParameters&gt; object. Reference: 
	  * L3V1 Distrib V1 Section 
	  */
 	 public static final int DISTRIB_20311 = 1520311; 

	 /**
	  * Error code 1520401:
	  * An &lt;uncertainty&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on an &lt;uncertainty&gt;. Reference: L3V1 Distrib V1 Section 
	  */
 	 public static final int DISTRIB_20401 = 1520401; 

	 /**
	  * Error code 1520402:
	  * An &lt;uncertainty&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespaces 
	  * are permitted on an &lt;uncertainty&gt;. Reference: L3V1 Distrib V1 Section 
	  */
 	 public static final int DISTRIB_20402 = 1520402; 

	 /**
	  * Error code 1520403:
	  * An &lt;uncertainty&gt; object may contain one and only one instance of the 
	  * &lt;listOfUncertParameters&gt; element. No other elements from the SBML Level 3 
	  * Distributions namespaces are permitted on an &lt;uncertainty&gt; object. Reference: 
	  * L3V1 Distrib V1 Section 
	  */
 	 public static final int DISTRIB_20403 = 1520403; 

	 /**
	  * Error code 1520501:
	  * An &lt;uncertSpan&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on an &lt;uncertSpan&gt;. Reference: L3V1 Distrib V1 Section 
	  */
 	 public static final int DISTRIB_20501 = 1520501; 

	 /**
	  * Error code 1520502:
	  * An &lt;uncertSpan&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespaces 
	  * are permitted on an &lt;uncertSpan&gt;. Reference: L3V1 Distrib V1 Section 
	  */
 	 public static final int DISTRIB_20502 = 1520502; 

	 /**
	  * Error code 1520503:
	  * An &lt;uncertSpan&gt; object may have the optional attributes 'distrib:varLower', 
	  * 'distrib:valueLower', 'distrib:varUpper' and 'distrib:valueUpper'. No other 
	  * attributes from the SBML Level 3 Distributions namespaces are permitted on an 
	  * &lt;uncertSpan&gt; object. Reference: L3V1 Distrib V1 Section 
	  */
 	 public static final int DISTRIB_20503 = 1520503; 

	 /**
	  * Error code 1520504:
	  * The value of the attribute 'distrib:varLower' of an &lt;uncertSpan&gt; object must be 
	  * the identifier of an existing object derived from the 'SBase' class and defined 
	  * in the enclosing &lt;model&gt; object. Reference: L3V1 Distrib V1 Section 
	  */
 	 public static final int DISTRIB_20504 = 1520504; 

	 /**
	  * Error code 1520505:
	  * The attribute 'distrib:valueLower' on an &lt;uncertSpan&gt; must have a value of data 
	  * type 'double'. Reference: L3V1 Distrib V1 Section 
	  */
 	 public static final int DISTRIB_20505 = 1520505; 

	 /**
	  * Error code 1520506:
	  * The value of the attribute 'distrib:varUpper' of an &lt;uncertSpan&gt; object must be 
	  * the identifier of an existing object derived from the 'SBase' class and defined 
	  * in the enclosing &lt;model&gt; object. Reference: L3V1 Distrib V1 Section 
	  */
 	 public static final int DISTRIB_20506 = 1520506; 

	 /**
	  * Error code 1520507:
	  * The attribute 'distrib:valueUpper' on an &lt;uncertSpan&gt; must have a value of data 
	  * type 'double'. Reference: L3V1 Distrib V1 Section 
	  */
 	 public static final int DISTRIB_20507 = 1520507; 

	 /**
	  * Error code 1520601:
	  * A &lt;distribBase&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;distribBase&gt;. Reference: L3V1 Distrib V1 Section 
	  */
 	 public static final int DISTRIB_20601 = 1520601; 

	 /**
	  * Error code 1520602:
	  * A &lt;distribBase&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespaces 
	  * are permitted on a &lt;distribBase&gt;. Reference: L3V1 Distrib V1 Section 
	  */
 	 public static final int DISTRIB_20602 = 1520602; 

	 /**
	  * Error code 1520603:
	  * A &lt;distribBase&gt; object may have the optional attributes 'distrib:id' and 
	  * 'distrib:name'. No other attributes from the SBML Level 3 Distributions 
	  * namespaces are permitted on a &lt;distribBase&gt; object. Reference: L3V1 Distrib V1 
	  * Section 
	  */
 	 public static final int DISTRIB_20603 = 1520603; 

	 /**
	  * Error code 1520604:
	  * The attribute 'distrib:name' on a &lt;distribBase&gt; must have a value of data type 
	  * 'string'. Reference: L3V1 Distrib V1 Section 
	  */
 	 public static final int DISTRIB_20604 = 1520604; 

	 /**
	  * Error code 2010100:
	  */
 	 public static final int FBC_10100 = 2010100; 

	 /**
	  * Error code 2010101:
	  * To conform to Version 1 of the Flux Balance Constraints package specification 
	  * for SBML Level 3, an SBML document must declare the use of the following XML 
	  * Namespace: 'http://www.sbml.org/sbml/level3/version1/fbc/version1' Reference: 
	  * L3V1 Fbc V1 Section 3.1 
	  */
 	 public static final int FBC_10101 = 2010101; 

	 /**
	  * Error code 2010102:
	  * Wherever they appear in an SBML document, elements and attributes from the Flux 
	  * Balance Constraints package must be declared either implicitly or explicitly to 
	  * be in the XML namespace 'http://www.sbml.org/sbml/level3/version1/fbc/version1' 
	  * Reference: L3V1 Fbc V1 Section 3.1 
	  */
 	 public static final int FBC_10102 = 2010102; 

	 /**
	  * Error code 2010301:
	  * (Extends validation rule #10301 in the SBML Level 3 Version 1 Core 
	  * specification.) Within a &lt;model&gt; object the values of the attributes id and 
	  * fbc:id on every instance of the following classes of objects must be unique 
	  * across the set of all id and fbc:id attribute values of all such objects in a 
	  * model: the model itself, plus all contained &lt;functionDefinition&gt;, &lt;compartment&gt;, 
	  * &lt;species&gt;, &lt;reaction&gt;, &lt;speciesReference&gt;, &lt;modifierSpeciesReference&gt;, &lt;event&gt;, 
	  * and &lt;parameter&gt; objects, plus the &lt;fluxBound&gt;, &lt;objective&gt;, &lt;fluxObjective&gt;, 
	  * &lt;geneProduct&gt; and &lt;geneProductAssociation&gt; objects defined by the Flux Balance 
	  * Constraints package. Reference: L3V1 Fbc V1 Section 3.2 
	  */
 	 public static final int FBC_10301 = 2010301; 

	 /**
	  * Error code 2010302:
	  * The value of a fbc:id attribute must always conform to the syntax of the SBML 
	  * data type SId. Reference: L3V1 Fbc V1 Section 3.2 
	  */
 	 public static final int FBC_10302 = 2010302; 

	 /**
	  * Error code 2020101:
	  * In all SBML documents using the Flux Balance Constraints package, the SBML 
	  * object must include a value for the attribute 'fbc:required'. Reference: L3V1 
	  * Core Section 4.1.2 
	  */
 	 public static final int FBC_20101 = 2020101; 

	 /**
	  * Error code 2020102:
	  * The value of attribute 'fbc:required' on the SBML object must be of the data 
	  * type Boolean. Reference: L3V1 Core Section 4.1.2 
	  */
 	 public static final int FBC_20102 = 2020102; 

	 /**
	  * Error code 2020103:
	  * The value of attribute 'fbc:required' on the SBML object must be set to 'false'. 
	  * Reference: L3V1 Fbc V1 Section 3.1 
	  */
 	 public static final int FBC_20103 = 2020103; 

	 /**
	  * Error code 2020201:
	  * There may be at most one instance of each of the following kinds of objects 
	  * within a &lt;model&gt; object using Flux Balance Constraints: &lt;listOfFluxBounds&gt; (V1 
	  * only), &lt;listOfObjectives&gt; and &lt;listOfGeneProducts&gt; (V2). Reference: L3V1 Fbc V1 
	  * Section 3.3 
	  */
 	 public static final int FBC_20201 = 2020201; 

	 /**
	  * Error code 2020202:
	  * The various ListOf subobjects with a &lt;model&gt; object are optional, but if 
	  * present, these container object must not be empty. Specifically, if any of the 
	  * following classes of objects are present on the &lt;model&gt;, it must not be empty: 
	  * &lt;listOfFluxBounds&gt; (V1 only), &lt;listOfObjectives&gt; and &lt;listOfGeneProducts&gt; (V2). 
	  * Reference: L3V1 Fbc V1 Section 3.3 
	  */
 	 public static final int FBC_20202 = 2020202; 

	 /**
	  * Error code 2020203:
	  * Apart from the general notes and annotation subobjects permitted on all SBML 
	  * objects, a &lt;listOfFluxBounds&gt; container object may only contain &lt;fluxBound&gt; 
	  * objects. Reference: L3V1 Fbc V1 Section 3.3 
	  */
 	 public static final int FBC_20203 = 2020203; 

	 /**
	  * Error code 2020204:
	  * Apart from the general notes and annotation subobjects permitted on all SBML 
	  * objects, a &lt;listOfObjectives&gt; container object may only contain &lt;objective&gt; 
	  * objects. Reference: L3V1 Fbc V1 Section 3.3 
	  */
 	 public static final int FBC_20204 = 2020204; 

	 /**
	  * Error code 2020205:
	  * A &lt;listOfFluxBounds&gt; object may have the optional attributes 'metaid' and 
	  * 'sboTerm' defined by SBML Level 3 Core. No other attributes from the SBML Level 
	  * 3 Core namespace or the Flux Balance Constraints namespace are permitted on a 
	  * &lt;listOfFluxBounds&gt; object. Reference: L3V1 Fbc V1 Section 3.3 
	  */
 	 public static final int FBC_20205 = 2020205; 

	 /**
	  * Error code 2020206:
	  * A &lt;listOfFluxBounds&gt; object may have the optional attributes 'metaid' and 
	  * 'sboTerm' defined by SBML Level 3 Core. Additionally the &lt;listOfObjectives&gt; must 
	  * contain the attribute 'activeObjective'. No other attributes from the SBML Level 
	  * 3 Core namespace or the Flux Balance Constraints namespace are permitted on a 
	  * &lt;listOfFluxBounds&gt; object. Reference: L3V1 Fbc V1 Section 3.3 
	  */
 	 public static final int FBC_20206 = 2020206; 

	 /**
	  * Error code 2020207:
	  * The value of attribute 'fbc:activeObjective' on the &lt;listOfObjectives&gt; object 
	  * must be of the data type SIdRef. Reference: L3V1 Fbc V1 Section 3.2.2 
	  */
 	 public static final int FBC_20207 = 2020207; 

	 /**
	  * Error code 2020208:
	  * The value of attribute 'fbc:activeObjective' on the &lt;listOfObjectives&gt; object 
	  * must be the identifier of an existing &lt;objective&gt;. Reference: L3V1 Fbc V1 
	  * Section 3.2.2 
	  */
 	 public static final int FBC_20208 = 2020208; 

	 /**
	  * Error code 2020209:
	  * A &lt;model&gt; object must have the required attributes 'strict'. No other attributes 
	  * from the Flux Balance Constraints namespace are permitted on a &lt;model&gt; object. 
	  */
 	 public static final int FBC_20209 = 2020209; 

	 /**
	  * Error code 2020210:
	  * The attribute 'fbc:strict' on the &lt;model&gt; object must have a value of datatype 
	  * Boolean. 
	  */
 	 public static final int FBC_20210 = 2020210; 

	 /**
	  * Error code 2020211:
	  * Apart from the general notes and annotation subobjects permitted on all SBML 
	  * objects, a &lt;listOfGeneProducts&gt; container object may only contain &lt;geneProduct&gt; 
	  * objects. 
	  */
 	 public static final int FBC_20211 = 2020211; 

	 /**
	  * Error code 2020212:
	  * A &lt;listOfGeneProducts&gt; object may have the optional attributes 'metaid' and 
	  * 'sboTerm' defined by SBML Level 3 Core. No other attributes from the SBML Level 
	  * 3 Core namespace or the Flux Balance Constraints namespace are permitted on a 
	  * &lt;listOfGeneProducts&gt; object. 
	  */
 	 public static final int FBC_20212 = 2020212; 

	 /**
	  * Error code 2020301:
	  * A SBML &lt;species&gt; object may have the optional attributes 'fbc:charge' and 
	  * 'fbc:chemicalFormula'. No other attributes from the Flux Balance Constraints 
	  * namespaces are permitted on a &lt;species&gt;. Reference: L3V1 Fbc V1 Section 3.4 
	  */
 	 public static final int FBC_20301 = 2020301; 

	 /**
	  * Error code 2020302:
	  * The value of attribute 'fbc:charge' on SBML &lt;species&gt; object must be of the data 
	  * type integer. Reference: L3V1 Fbc V1 Section 3.4 
	  */
 	 public static final int FBC_20302 = 2020302; 

	 /**
	  * Error code 2020303:
	  * The value of attribute 'fbc:chemicalFormula' on the SBML &lt;species&gt; object must 
	  * be set to a string consisting only of atomic names or user defined compounds and 
	  * their occurrence. Reference: L3V1 Fbc V1 Section 3.4 
	  */
 	 public static final int FBC_20303 = 2020303; 

	 /**
	  * Error code 2020401:
	  * A &lt;fluxBound&gt; object may have the optional SBML Level 3 Core attributes 'metaid' 
	  * and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are 
	  * permitted on a &lt;fluxBound&gt;. Reference: L3V1 Core, Section 3.2 
	  */
 	 public static final int FBC_20401 = 2020401; 

	 /**
	  * Error code 2020402:
	  * A &lt;fluxBound&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespace 
	  * are permitted on a &lt;fluxBound&gt;. Reference: L3V1 Core, Section 3.2 
	  */
 	 public static final int FBC_20402 = 2020402; 

	 /**
	  * Error code 2020403:
	  * A &lt;fluxBound&gt; object must have the required attributes 'fbc:reaction', 
	  * 'fbc:operation' and 'fbc:value', and may have the optional attributes 'fbc:id' 
	  * and 'fbc:name'. No other attributes from the SBML Level 3 Flux Balance 
	  * Constraints namespace are permitted on a &lt;fluxBound&gt; object. Reference: L3V1 Fbc 
	  * V1 Section 3.5 
	  */
 	 public static final int FBC_20403 = 2020403; 

	 /**
	  * Error code 2020404:
	  * The attribute 'fbc:reaction' of a &lt;fluxBound&gt; must be of the data type SIdRef. 
	  * Reference: L3V1 Fbc V1 Section 3.5 
	  */
 	 public static final int FBC_20404 = 2020404; 

	 /**
	  * Error code 2020405:
	  * The attribute 'fbc:name' of a &lt;fluxBound&gt; must be of the data type string. 
	  * Reference: L3V1 Fbc V1 Section 3.5 
	  */
 	 public static final int FBC_20405 = 2020405; 

	 /**
	  * Error code 2020406:
	  * The attribute 'fbc:operation' of a &lt;fluxBound&gt; must be of the data type 
	  * FbcOperation and thus it's value must be one of 'lessEqual', 'greaterEqual' or 
	  * 'equal'. Reference: L3V1 Fbc V1 Section 3.5 
	  */
 	 public static final int FBC_20406 = 2020406; 

	 /**
	  * Error code 2020407:
	  * The attribute 'fbc:value' of a &lt;fluxBound&gt; must be of the data type double. 
	  * Reference: L3V1 Fbc V1 Section 3.5 
	  */
 	 public static final int FBC_20407 = 2020407; 

	 /**
	  * Error code 2020408:
	  * The value of the attribute 'fbc:reaction' of a &lt;fluxBound&gt; object must be the 
	  * identifier of an existing &lt;reaction&gt; object defined in the enclosing &lt;model&gt; 
	  * object. Reference: L3V1 Fbc V1 Section 3.5 
	  */
 	 public static final int FBC_20408 = 2020408; 

	 /**
	  * Error code 2020409:
	  * The combined set of all &lt;fluxBound&gt;'s with identical values for 'fbc:reaction' 
	  * must be consistent. That is while it is possible to define a lower and an upper 
	  * bound for a reaction, it is not possible to define multiple lower or upper 
	  * bounds. Reference: L3V1 Fbc V1 Section 3.5 
	  */
 	 public static final int FBC_20409 = 2020409; 

	 /**
	  * Error code 2020501:
	  * An &lt;objective&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace 
	  * are permitted on an &lt;objective&gt;. Reference: L3V1 Core, Section 3.2 
	  */
 	 public static final int FBC_20501 = 2020501; 

	 /**
	  * Error code 2020502:
	  * An &lt;objective&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespace 
	  * are permitted on an &lt;objective&gt;. Reference: L3V1 Core, Section 3.2 
	  */
 	 public static final int FBC_20502 = 2020502; 

	 /**
	  * Error code 2020503:
	  * An &lt;objective&gt; object must have the required attributes 'fbc:id' and 'fbc:type' 
	  * and may have the optional attribute 'fbc:name'. No other attributes from the 
	  * SBML Level 3 Flux Balance Constraints namespace are permitted on an &lt;objective&gt; 
	  * object. Reference: L3V1 Fbc V1, Section 3.6 
	  */
 	 public static final int FBC_20503 = 2020503; 

	 /**
	  * Error code 2020504:
	  * The attribute 'fbc:name' on an &lt;objective&gt; must be of the data type 'string'. 
	  * Reference: L3V1 Fbc V1, Section 3.6 
	  */
 	 public static final int FBC_20504 = 2020504; 

	 /**
	  * Error code 2020505:
	  * The attribute 'fbc:type' on an &lt;objective&gt; must be of the data type FbcType and 
	  * thus its value must be one of 'minimize' or 'maximize. Reference: L3V1 Fbc V1, 
	  * Section 3.6 
	  */
 	 public static final int FBC_20505 = 2020505; 

	 /**
	  * Error code 2020506:
	  * An &lt;objective&gt; object must have one and only one instance of the 
	  * &lt;listOfFluxObjectives&gt; object. Reference: L3V1 Fbc V1, Section 3.6 
	  */
 	 public static final int FBC_20506 = 2020506; 

	 /**
	  * Error code 2020507:
	  * The &lt;listOfFluxObjectives&gt; subobject within an &lt;objective&gt; object must not be 
	  * empty. Reference: L3V1 Fbc V1, Section 3.6 
	  */
 	 public static final int FBC_20507 = 2020507; 

	 /**
	  * Error code 2020508:
	  * Apart from the general notes and annotation subobjects permitted on all SBML 
	  * objects, a &lt;listOfFluxObjectives&gt; container object may only contain 
	  * &lt;fluxObjective&gt; objects. Reference: L3V1 Fbc V1, Section 3.6 
	  */
 	 public static final int FBC_20508 = 2020508; 

	 /**
	  * Error code 2020509:
	  * A &lt;listOfFluxObjectives&gt; object may have the optional 'metaid' and 'sboTerm' 
	  * defined by SBML Level 3 Core. No other attributes from the SBML Level 3 Core 
	  * namespace or the Flux Balance Constraints namespace are permitted on a 
	  * &lt;listOfFluxObjectives&gt; object Reference: L3V1 Fbc V1, Section 3.6 
	  */
 	 public static final int FBC_20509 = 2020509; 

	 /**
	  * Error code 2020601:
	  * A &lt;fluxObjective&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace 
	  * are permitted on a &lt;fluxObjective&gt;. Reference: L3V1 Core, Section 3.2 
	  */
 	 public static final int FBC_20601 = 2020601; 

	 /**
	  * Error code 2020602:
	  * A &lt;fluxObjective&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespace 
	  * are permitted on a &lt;fluxObjective&gt;. Reference: L3V1 Core, Section 3.2 
	  */
 	 public static final int FBC_20602 = 2020602; 

	 /**
	  * Error code 2020603:
	  * A &lt;fluxObjective&gt; object must have the required attributes 'fbc:reaction' and 
	  * 'fbc:coefficient', and may have the optional attributes 'fbc:id' and 'fbc:name'. 
	  * No other attributes from the SBML Level 3 Flux Balance Constraints namespace are 
	  * permitted on a &lt;fluxObjective&gt; object. Reference: L3V1 Fbc V1, Section 3.7 
	  */
 	 public static final int FBC_20603 = 2020603; 

	 /**
	  * Error code 2020604:
	  * The attribute 'fbc:name' on a &lt;fluxObjective&gt; must be of the data type 'string'. 
	  * Reference: L3V1 Fbc V1, Section 3.7 
	  */
 	 public static final int FBC_20604 = 2020604; 

	 /**
	  * Error code 2020605:
	  * The value of the attribute 'fbc:reaction' of a &lt;fluxObjective&gt; object must 
	  * conform to the syntax of the SBML data type 'SIdRef'. Reference: L3V1 Fbc V1, 
	  * Section 3.7 
	  */
 	 public static final int FBC_20605 = 2020605; 

	 /**
	  * Error code 2020606:
	  * The value of the attribute 'fbc:reaction' of a &lt;fluxObjective&gt; object must be 
	  * the identifier of an existing &lt;reaction&gt; object defined in the enclosing &lt;model&gt; 
	  * object. Reference: L3V1 Fbc V1, Section 3.7 
	  */
 	 public static final int FBC_20606 = 2020606; 

	 /**
	  * Error code 2020607:
	  * The value of the attribute 'fbc:coefficient' of a &lt;fluxObjective&gt; object must 
	  * conform to the syntax of the SBML data type 'double'. Reference: L3V1 Fbc V1, 
	  * Section 3.7 
	  */
 	 public static final int FBC_20607 = 2020607; 

	 /**
	  * Error code 2020608:
	  * When the value of the &lt;model&gt; 'fbc:strict' attribute is true, the value of the 
	  * attribute 'fbc:coefficient' of a &lt;fluxObjective&gt; object must not be set to 
	  * 'NaN', 'INF' or '-INF'. 
	  */
 	 public static final int FBC_20608 = 2020608; 

	 /**
	  * Error code 2020701:
	  * There may be at most one instance of a &lt;GeneProductAssociation&gt; within a 
	  * &lt;Reaction&gt; object using Flux BalanceConstraints. 
	  */
 	 public static final int FBC_20701 = 2020701; 

	 /**
	  * Error code 2020702:
	  * An SBML &lt;Reaction&gt; object may have the optional attributes 'fbc:lowerFluxBound' 
	  * and 'fbc:upperFluxBound'. No other attributes from the Flux Balance Constraints 
	  * namespaces are permitted on a &lt;Reaction&gt;. 
	  */
 	 public static final int FBC_20702 = 2020702; 

	 /**
	  * Error code 2020703:
	  * The attribute 'fbc:lowerFluxBound' of a &lt;Reaction&gt; must be of the data type 
	  * 'SIdRef'. 
	  */
 	 public static final int FBC_20703 = 2020703; 

	 /**
	  * Error code 2020704:
	  * The attribute 'fbc:upperFluxBound' of a &lt;Reaction&gt; must be of the data type 
	  * 'SIdRef'. 
	  */
 	 public static final int FBC_20704 = 2020704; 

	 /**
	  * Error code 2020705:
	  * The attribute 'fbc:lowerFluxBound' of a &lt;Reaction&gt; must point to an existing 
	  * &lt;Parameter&gt; in the model. 
	  */
 	 public static final int FBC_20705 = 2020705; 

	 /**
	  * Error code 2020706:
	  * The attribute 'fbc:upperFluxBound' of a &lt;Reaction&gt; must point to an existing 
	  * &lt;Parameter&gt; in the model. 
	  */
 	 public static final int FBC_20706 = 2020706; 

	 /**
	  * Error code 2020707:
	  * When the value of the &lt;Model&gt;s 'fbc:strict' attribute is 'true', a &lt;Reaction&gt; 
	  * must define the attributes 'fbc:lowerFluxBound' and 'fbc:upperFluxBound'. 
	  */
 	 public static final int FBC_20707 = 2020707; 

	 /**
	  * Error code 2020708:
	  * When the value of the &lt;Model&gt;s 'fbc:strict' attribute is 'true', the &lt;Parameter&gt; 
	  * objects referred to by the attributes 'fbc:lowerFluxBound' and 
	  * 'fbc:upperFluxBound' must have their 'constant' attribute set to 'true'. 
	  */
 	 public static final int FBC_20708 = 2020708; 

	 /**
	  * Error code 2020709:
	  * When the value of the &lt;Model&gt;s 'fbc:strict' attribute is 'true', the &lt;Parameter&gt; 
	  * objects referred to by the attributes 'fbc:lowerFluxBound' and 
	  * 'fbc:upperFluxBound' must have a defined value for their 'value' attribute, 
	  * which may not be 'NaN'. 
	  */
 	 public static final int FBC_20709 = 2020709; 

	 /**
	  * Error code 2020710:
	  * When the value of the &lt;Model&gt;s 'fbc:strict' attribute is 'true', the &lt;Parameter&gt; 
	  * objects referred to by the attributes 'fbc:lowerFluxBound' and 
	  * 'fbc:upperFluxBound' may not be targeted by an &lt;InitialAssignment&gt;. 
	  */
 	 public static final int FBC_20710 = 2020710; 

	 /**
	  * Error code 2020711:
	  * When the value of the &lt;Model&gt;s 'fbc:strict' attribute is 'true', the &lt;Parameter&gt; 
	  * objects referred to by the attribute 'fbc:lowerFluxBound' may not have the value 
	  * 'INF'. 
	  */
 	 public static final int FBC_20711 = 2020711; 

	 /**
	  * Error code 2020712:
	  * When the value of the &lt;Model&gt;s 'fbc:strict' attribute is 'true', the &lt;Parameter&gt; 
	  * object referred to by the attribute 'fbc:upperFluxBound' may not have the value 
	  * '-INF'. 
	  */
 	 public static final int FBC_20712 = 2020712; 

	 /**
	  * Error code 2020713:
	  * When the value of the &lt;Model&gt;s 'fbc:strict' attribute is 'true', the &lt;Parameter&gt; 
	  * object referred to by the attribute 'fbc:lowerFluxBound' must be less than or 
	  * equal to the value of the &lt;Parameter&gt; object referred to by the attribute 
	  * 'fbc:upperFluxBound' . 
	  */
 	 public static final int FBC_20713 = 2020713; 

	 /**
	  * Error code 2020714:
	  * When the value of the &lt;Model&gt;s 'fbc:strict' attribute is 'true', the 'constant' 
	  * attribute of &lt;SpeciesReference&gt; elements of a &lt;Reaction&gt; must be set to 'true'. 
	  */
 	 public static final int FBC_20714 = 2020714; 

	 /**
	  * Error code 2020715:
	  * When the value of the &lt;Model&gt;s 'fbc:strict' attribute is 'true', the value of a 
	  * &lt;SpeciesReference&gt;'s 'stoichiometry&gt; attribute must not be set to 'NaN', '-INF' 
	  * or 'INF'. 
	  */
 	 public static final int FBC_20715 = 2020715; 

	 /**
	  * Error code 2020716:
	  * When the value of the &lt;Model&gt;s 'fbc:strict' attribute is 'true', the 
	  * &lt;SpeciesReference&gt; elements of a &lt;Reaction&gt; may not be targeted by an 
	  * &lt;InitialAssignment&gt;. 
	  */
 	 public static final int FBC_20716 = 2020716; 

	 /**
	  * Error code 2020801:
	  * A &lt;GeneProductAssociation&gt; object may have the optional SBML Level 3 Core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 
	  * Core namespace are permitted on a &lt;GeneProductAssociation. 
	  */
 	 public static final int FBC_20801 = 2020801; 

	 /**
	  * Error code 2020802:
	  * A &lt;GeneProductAssociation&gt; object may have the optional SBML Level 3 Core 
	  * subobjects for notes and annotations. No other elements from the SBML Level 3 
	  * Core namespace are permitted on a &lt;GeneProductAssociation. 
	  */
 	 public static final int FBC_20802 = 2020802; 

	 /**
	  * Error code 2020803:
	  * A &lt;GeneProductAssociation&gt; object may have the optional attributes 'fbc:id' and 
	  * 'fbc:name'. No other attributes from the SBML Level 3 Flux Balance Constraints 
	  * namespace are permitted on a &lt;GeneProductAssociation&gt; object. 
	  */
 	 public static final int FBC_20803 = 2020803; 

	 /**
	  * Error code 2020804:
	  * The attribute 'fbc:id' on a &lt;GeneProductAssociation&gt; must be of the data type 
	  * 'SId'. 
	  */
 	 public static final int FBC_20804 = 2020804; 

	 /**
	  * Error code 2020805:
	  * A &lt;GeneProductAssociation&gt; object must have one and only one of the concrete 
	  * &lt;Association&gt; objects: &lt;GeneProductRef&gt;, &lt;And&gt; or &lt;Or&gt;. 
	  */
 	 public static final int FBC_20805 = 2020805; 

	 /**
	  * Error code 2020806:
	  * The attribute 'fbc:name' on a &lt;GeneProductAssociation&gt; must be of the data type 
	  * 'string'. 
	  */
 	 public static final int FBC_20806 = 2020806; 

	 /**
	  * Error code 2020901:
	  * A &lt;GeneProductRef&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace 
	  * are permitted on a &lt;GeneProductRef&gt;. 
	  */
 	 public static final int FBC_20901 = 2020901; 

	 /**
	  * Error code 2020902:
	  * A &lt;GeneProductRef&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespace 
	  * are permitted on a &lt;GeneProductRef&gt;. 
	  */
 	 public static final int FBC_20902 = 2020902; 

	 /**
	  * Error code 2020903:
	  * A &lt;GeneProductRef&gt; object must have the required attribute 'fbc:geneProduct' and 
	  * may have the optional attribute 'fbc:id'. No other attributes from the SBML 
	  * Level 3 Flux Balance Constraints namespace are permitted on a &lt;&gt; object. 
	  */
 	 public static final int FBC_20903 = 2020903; 

	 /**
	  * Error code 2020904:
	  * The attribute 'fbc:geneProduct' on a &lt;GeneProductRef&gt; must be of the data type 
	  * 'SIdRef'. 
	  */
 	 public static final int FBC_20904 = 2020904; 

	 /**
	  * Error code 2020908:
	  * The attribute 'fbc:geneProduct' on a &lt;GeneProductRef&gt; if set, must refer to 'id' 
	  * of a &lt;GeneProduct&gt; in the &lt;Model&gt;. 
	  */
 	 public static final int FBC_20908 = 2020908; 

	 /**
	  * Error code 2021001:
	  * An &lt;And&gt; object may have the optional SBML Level 3 Core attributes 'metaid' and 
	  * 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are 
	  * permitted on an &lt;GeneAnd. 
	  */
 	 public static final int FBC_21001 = 2021001; 

	 /**
	  * Error code 2021002:
	  * An &lt;And&gt; object may have the optional SBML Level 3 Core subobjects for notes and 
	  * annotations. No other elements from the SBML Level 3 Core namespace are 
	  * permitted on an &lt;And&gt;. 
	  */
 	 public static final int FBC_21002 = 2021002; 

	 /**
	  * Error code 2021003:
	  * An &lt;And&gt; object must have two or more concrete &lt;Association&gt; objects: 
	  * &lt;GeneProductRef&gt;, &lt;And&gt;, or &lt;Or&gt;. No other elements from the SBML Level 3 Flux 
	  * Balance Constraints namespace are permitted on an &lt;And&gt; object. 
	  */
 	 public static final int FBC_21003 = 2021003; 

	 /**
	  * Error code 2021101:
	  * An &lt;Or&gt; object may have the optional SBML Level 3 Core attributes 'metaid' and 
	  * 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are 
	  * permitted on an &lt;Or&gt;. 
	  */
 	 public static final int FBC_21101 = 2021101; 

	 /**
	  * Error code 2021102:
	  * An &lt;Or&gt; object may have the optional SBML Level 3 Core subobjects for notes and 
	  * annotations. No other elements from the SBML Level 3 Core namespace are 
	  * permitted on an &lt;Or&gt;. 
	  */
 	 public static final int FBC_21102 = 2021102; 

	 /**
	  * Error code 2021103:
	  * An &lt;Or&gt; object must have two or more concrete &lt;Association&gt; objects: 
	  * &lt;GeneProductRef&gt;, &lt;And&gt;, or &lt;Or&gt;. No other elements from the SBML Level 3 Flux 
	  * Balance Constraints namespace are permitted on an &lt;Or&gt; object. 
	  */
 	 public static final int FBC_21103 = 2021103; 

	 /**
	  * Error code 2021201:
	  * A &lt;GeneProduct&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace 
	  * are permitted on a &lt;GeneProduct&gt;. 
	  */
 	 public static final int FBC_21201 = 2021201; 

	 /**
	  * Error code 2021202:
	  * A &lt;GeneProduct&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespace 
	  * are permitted on a &lt;GeneProduct&gt;. 
	  */
 	 public static final int FBC_21202 = 2021202; 

	 /**
	  * Error code 2021203:
	  * A &lt;GeneProduct&gt; object must have the required attributes 'fbc:id' and 
	  * 'fbc:label' may have the optional attributes 'fbc:name' and 
	  * 'fbc:associatedSpecies'. No other attributes from the SBML Level 3 Flux Balance 
	  * Constraints namespace are permitted on a &lt;GeneProduct&gt; object. 
	  */
 	 public static final int FBC_21203 = 2021203; 

	 /**
	  * Error code 2021204:
	  * The attribute 'fbc:label' on a &lt;GeneProduct&gt; must be of the data type 'string'. 
	  */
 	 public static final int FBC_21204 = 2021204; 

	 /**
	  * Error code 2021205:
	  * The attribute 'fbc:label' on a &lt;GeneProduct&gt; must be unique among the set of all 
	  * &lt;GeneProduct&gt; elements defined in the &lt;Model&gt;. 
	  */
 	 public static final int FBC_21205 = 2021205; 

	 /**
	  * Error code 2021206:
	  * The attribute 'fbc:name' on a &lt;GeneProduct&gt; must be of the data type 'string'. 
	  */
 	 public static final int FBC_21206 = 2021206; 

	 /**
	  * Error code 2021207:
	  * The attribute 'fbc:associatedSpecies' on a &lt;GeneProduct&gt; must be the identifier 
	  * of an existing &lt;Species&gt; defined in the enclosing &lt;Model&gt;. 
	  */
 	 public static final int FBC_21207 = 2021207; 

	 /**
	  * Error code 3010100:
	  */
 	 public static final int QUAL_10100 = 3010100; 

	 /**
	  * Error code 3010101:
	  * To conform to Version 1 of the Qualitative Models package specification for SBML 
	  * Level 3, an SBML document must declare the use of the following XML Namespace: 
	  * 'http://www.sbml.org/sbml/level3/version1/qual/version1' Reference: L3V1 Qual V1 
	  * Section 3.1 
	  */
 	 public static final int QUAL_10101 = 3010101; 

	 /**
	  * Error code 3010102:
	  * Wherever they appear in an SBML document, elements and attributes from the 
	  * Qualitative Models package must be declared either implicitly or explicitly to 
	  * be in the XML namespace 'http://www.sbml.org/sbml/level3/version1/qual/version1' 
	  * Reference: L3V1 Qual V1 Section 3.1 
	  */
 	 public static final int QUAL_10102 = 3010102; 

	 /**
	  * Error code 3010201:
	  * The MathML &lt;math&gt; element in a &lt;functionTerm&gt; object should evaluate to a value 
	  * of type Boolean. Reference: L3V1 Qual V1 Section 3.6.5 
	  */
 	 public static final int QUAL_10201 = 3010201; 

	 /**
	  * Error code 3010202:
	  * The MathML &lt;math&gt; element in a &lt;functionTerm&gt; object should not use the 
	  * &lt;csymbol&gt; elements 'time' and 'delay' as these explicitly introduce time into 
	  * the model. As yet time is not considered within the Qualitative Models package 
	  * specification. Reference: L3V1 Qual V1 Section 3.6.5 
	  */
 	 public static final int QUAL_10202 = 3010202; 

	 /**
	  * Error code 3010301:
	  * (Extends validation rule #10301 in the SBML Level 3 Version 1 Core 
	  * specification.) Within a &lt;model&gt; the values of the attributes 'id' and 'qual:id' 
	  * on every instance of the following classes of objects must be unique across the 
	  * set of all 'id' and 'qual:id' attribute values of all such objects in a model: 
	  * the &lt;model&gt; itself, plus all contained &lt;functionDefinition&gt;, &lt;compartment&gt;, 
	  * &lt;species&gt;, &lt;reaction&gt;, &lt;speciesReference&gt;, &lt;modifierSpeciesReference&gt;, &lt;event&gt;, 
	  * and &lt;parameter&gt; objects, plus the &lt;qualitativeSpecies&gt;, &lt;transition&gt;, &lt;input&gt; 
	  * and &lt;output&gt; objects defined by the Qualitative Models package. Reference: L3V1 
	  * Qual V1 Section 3.7 
	  */
 	 public static final int QUAL_10301 = 3010301; 

	 /**
	  * Error code 3020101:
	  * In all SBML documents using the Qualitative Models package, the SBML object must 
	  * include a value for the attribute 'qual:required' attribute. Reference: L3V1 
	  * Core Section 4.1.2 
	  */
 	 public static final int QUAL_20101 = 3020101; 

	 /**
	  * Error code 3020102:
	  * The value of attribute 'qual:required' on the SBML object must be of the data 
	  * type Boolean. Reference: L3V1 Core Section 4.1.2 
	  */
 	 public static final int QUAL_20102 = 3020102; 

	 /**
	  * Error code 3020103:
	  * The value of attribute 'qual:required' on the SBML object must be set to 'true' 
	  * if if the model contains any &lt;transition&gt; objects. Reference: L3V1 Qual V1 
	  * Section 3.1 
	  */
 	 public static final int QUAL_20103 = 3020103; 

	 /**
	  * Error code 3020201:
	  * There may be at most one instance of each of the following kinds of objects 
	  * within a &lt;model&gt; object using Qualitative Models: &lt;listOfTransitions&gt; and 
	  * &lt;listOfQualitativeSpecies&gt;. Reference: L3V1 Qual V1 Section 3.4 
	  */
 	 public static final int QUAL_20201 = 3020201; 

	 /**
	  * Error code 3020202:
	  * The various ListOf subobjects with a &lt;model&gt; object are optional, but if 
	  * present, these container object must not be empty. Specifically, if any of the 
	  * following classes of objects are present on the &lt;model&gt;, it must not be empty: 
	  * &lt;listOfQualitativeSpecies&gt; and &lt;listOfTransitions&gt;. Reference: L3V1 Qual V1 
	  * Section 3.4 
	  */
 	 public static final int QUAL_20202 = 3020202; 

	 /**
	  * Error code 3020203:
	  * Apart from the general notes and annotation subobjects permitted on all SBML 
	  * objects, a &lt;listOfTransitions&gt; container object may only contain &lt;transition&gt; 
	  * objects. Reference: L3V1 Qual V1 Section 3.4 
	  */
 	 public static final int QUAL_20203 = 3020203; 

	 /**
	  * Error code 3020204:
	  * Apart from the general notes and annotation subobjects permitted on all SBML 
	  * objects, a &lt;listOfQualitativeSpecies&gt; container object may only contain 
	  * &lt;qualitativeSpecies&gt; objects. Reference: L3V1 Qual V1 Section 3.4 
	  */
 	 public static final int QUAL_20204 = 3020204; 

	 /**
	  * Error code 3020205:
	  * A &lt;listOfQualitativeSpecies&gt; object may have the optional 'metaid' and 'sboTerm' 
	  * defined by SBML Level 3 Core. No other attributes from the SBML Level 3 Core 
	  * namespace or the Qualitative Models namespace are permitted on a 
	  * &lt;listOfQualitativeSpecies&gt; object. Reference: L3V1 Qual V1 Section 3.4 
	  */
 	 public static final int QUAL_20205 = 3020205; 

	 /**
	  * Error code 3020206:
	  * A &lt;listOfTransitions&gt; object may have the optional 'metaid' and 'sboTerm' 
	  * defined by SBML Level 3 Core. No other attributes from the SBML Level 3 Core 
	  * namespace or the Qualitative Models namespace are permitted on a 
	  * &lt;listOfTransitions&gt; object. Reference: L3V1 Qual V1 Section 3.4 
	  */
 	 public static final int QUAL_20206 = 3020206; 

	 /**
	  * Error code 3020301:
	  * A &lt;qualitativeSpecies&gt; object may have the optional 'metaid' and 'sboTerm' 
	  * defined by SBML Level 3 Core. No other attributes from the SBML Level 3 Core 
	  * namespace or the Qualitative Models namespace are permitted on a 
	  * &lt;qualitativeSpecies&gt; object. Reference: L3V1 Qual V1 Section 3.5 
	  */
 	 public static final int QUAL_20301 = 3020301; 

	 /**
	  * Error code 3020302:
	  * A &lt;qualitativeSpecies&gt; object may have the optional SBML Level 3 Core subobjects 
	  * for notes and annotations. No other elements from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;qualitativeSpecies&gt;. Reference: L3V1 Qual V1 
	  * Section 3.5 
	  */
 	 public static final int QUAL_20302 = 3020302; 

	 /**
	  * Error code 3020303:
	  * A &lt;qualitativeSpecies&gt; object must have the required attributes 'qual:id', 
	  * 'qual:compartment' and 'qual:constant', and may have the optional attributes 
	  * 'qual:name', 'qual:initialLevel' and 'qual:maxLevel'. No other attributes from 
	  * the SBML Level 3 Qualitative Models namespace are permitted on a 
	  * &lt;qualitativeSpecies&gt; object. Reference: L3V1 Qual V1 Section 3.5 
	  */
 	 public static final int QUAL_20303 = 3020303; 

	 /**
	  * Error code 3020304:
	  * The attribute 'qual:constant' in &lt;qualitativeSpecies&gt; must be of the data type 
	  * Boolean. Reference: L3V1 Qual V1 Section 3.5 
	  */
 	 public static final int QUAL_20304 = 3020304; 

	 /**
	  * Error code 3020305:
	  * The attribute 'qual:name' in &lt;qualitativeSpecies&gt; must be of the data type 
	  * string. Reference: L3V1 Qual V1 Section 3.5 
	  */
 	 public static final int QUAL_20305 = 3020305; 

	 /**
	  * Error code 3020306:
	  * The attribute 'qual:initialLevel' in &lt;qualitativeSpecies&gt; must be of the data 
	  * type integer. Reference: L3V1 Qual V1 Section 3.5 
	  */
 	 public static final int QUAL_20306 = 3020306; 

	 /**
	  * Error code 3020307:
	  * The attribute 'qual:maxLevel' in &lt;qualitativeSpecies&gt; must be of the data type 
	  * integer. Reference: L3V1 Qual V1 Section 3.5 
	  */
 	 public static final int QUAL_20307 = 3020307; 

	 /**
	  * Error code 3020308:
	  * The value of the attribute 'qual:compartment' in a &lt;qualitativeSpecies&gt; object 
	  * must be the identifier of an existing &lt;compartment&gt; object defined in the 
	  * enclosing &lt;model&gt; object. Reference: L3V1 Qual V1 Section 3.5 
	  */
 	 public static final int QUAL_20308 = 3020308; 

	 /**
	  * Error code 3020309:
	  * The value of the attribute 'qual:initialLevel' in a &lt;qualitativeSpecies&gt; object 
	  * cannot be greater than the value of the 'qual:maxLevel' attribute for the given 
	  * &lt;qualitativeSpecies&gt; object. Reference: L3V1 Qual V1 Section 3.5 
	  */
 	 public static final int QUAL_20309 = 3020309; 

	 /**
	  * Error code 3020310:
	  * A &lt;qualitativeSpecies&gt; with attribute 'qual:constant' set to 'true' can only be 
	  * referred to by an &lt;input&gt;. It cannot be the subject of an &lt;output&gt; in a 
	  * &lt;transition&gt;. Reference: L3V1 Qual V1 Section 3.5 
	  */
 	 public static final int QUAL_20310 = 3020310; 

	 /**
	  * Error code 3020311:
	  * A &lt;qualitativeSpecies&gt; that is referenced by an &lt;output&gt; with the 
	  * 'qual:transitionEffect' attribute set to 'assignmentLevel' should not be 
	  * referenced by any other &lt;output&gt; with the same 'transitionEffect' throughout the 
	  * set of transitions for the containing model. Reference: L3V1 Qual V1 Section 
	  * 3.6.2 
	  */
 	 public static final int QUAL_20311 = 3020311; 

	 /**
	  * Error code 3020312:
	  * The attribute 'qual:initialLevel' in &lt;qualitativeSpecies&gt; must not be negative. 
	  * Reference: L3V1 Qual V1 Section 3.5 
	  */
 	 public static final int QUAL_20312 = 3020312; 

	 /**
	  * Error code 3020313:
	  * The attribute 'qual:maxLevel' in &lt;qualitativeSpecies&gt; must not be negative. 
	  * Reference: L3V1 Qual V1 Section 3.5 
	  */
 	 public static final int QUAL_20313 = 3020313; 

	 /**
	  * Error code 3020401:
	  * A &lt;transition&gt; object may have the optional 'metaid' and 'sboTerm' defined by 
	  * SBML Level 3 Core. No other attributes from the SBML Level 3 Core namespace or 
	  * the Qualitative Models namespace are permitted on a &lt;transition&gt; object. 
	  * Reference: L3V1 Qual V1 Section 3.6 
	  */
 	 public static final int QUAL_20401 = 3020401; 

	 /**
	  * Error code 3020402:
	  * A &lt;transition&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespaces 
	  * are permitted on a &lt;transition&gt;. Reference: L3V1 Qual V1 Section 3.6 
	  */
 	 public static final int QUAL_20402 = 3020402; 

	 /**
	  * Error code 3020403:
	  * A &lt;transition&gt; object may have the optional attributes 'qual:name' and 
	  * 'qual:id'. No other attributes from the SBML Level 3 Qualitative Models 
	  * namespace are permitted on a &lt;transition&gt; object. Reference: L3V1 Qual V1 
	  * Section 3.6 
	  */
 	 public static final int QUAL_20403 = 3020403; 

	 /**
	  * Error code 3020404:
	  * The attribute 'qual:name' in &lt;transition&gt; must be of the data type string. 
	  * Reference: L3V1 Qual V1 Section 3.6 
	  */
 	 public static final int QUAL_20404 = 3020404; 

	 /**
	  * Error code 3020405:
	  * A &lt;transition&gt; must have one and only one instance of the &lt;listOfFunctionTerms&gt; 
	  * objects and may have at most one instance of the &lt;listOfInputs&gt; and 
	  * &lt;listOfOutputs&gt; objects from the Qualitative Models namespace. Reference: L3V1 
	  * Qual V1 Section 3.6 
	  */
 	 public static final int QUAL_20405 = 3020405; 

	 /**
	  * Error code 3020406:
	  * The &lt;listOfInputs&gt; and &lt;listOfOutputs&gt; subobjects on a &lt;transition&gt; object are 
	  * optional, but if present, these container object must not be empty. Reference: 
	  * L3V1 Qual V1 Section 3.6 
	  */
 	 public static final int QUAL_20406 = 3020406; 

	 /**
	  * Error code 3020407:
	  * Apart from the general notes and annotation subobjects permitted on all SBML 
	  * objects, a &lt;listOfInputs&gt; container object may only contain &lt;input&gt; objects. 
	  * Reference: L3V1 Qual V1 Section 3.6 
	  */
 	 public static final int QUAL_20407 = 3020407; 

	 /**
	  * Error code 3020408:
	  * Apart from the general notes and annotation subobjects permitted on all SBML 
	  * objects, a &lt;listOfOutputs&gt; container object may only contain &lt;output&gt; objects. 
	  * Reference: L3V1 Qual V1 Section 3.6 
	  */
 	 public static final int QUAL_20408 = 3020408; 

	 /**
	  * Error code 3020409:
	  * Apart from the general notes and annotation subobjects permitted on all SBML 
	  * objects, a &lt;listOfFunctionTerms&gt; container object must contain one and only one 
	  * &lt;defaultTerm&gt; object and then may only contain &lt;functionTerm&gt; objects. 
	  * Reference: L3V1 Qual V1 Section 3.6 
	  */
 	 public static final int QUAL_20409 = 3020409; 

	 /**
	  * Error code 3020410:
	  * A &lt;listOfInputs&gt; object may have the optional 'metaid' and 'sboTerm' defined by 
	  * SBML Level 3 Core. No other attributes from the SBML Level 3 Core namespace or 
	  * the Qualitative Models namespace are permitted on a &lt;listOfInputs&gt; object. 
	  * Reference: L3V1 Qual V1 Section 3.6 
	  */
 	 public static final int QUAL_20410 = 3020410; 

	 /**
	  * Error code 3020411:
	  * A &lt;listOfOutputs&gt; object may have the optional 'metaid' and 'sboTerm' defined by 
	  * SBML Level 3 Core. No other attributes from the SBML Level 3 Core namespace or 
	  * the Qualitative Models namespace are permitted on a &lt;listOfOutputs&gt; object. 
	  * Reference: L3V1 Qual V1 Section 3.6 
	  */
 	 public static final int QUAL_20411 = 3020411; 

	 /**
	  * Error code 3020412:
	  * A &lt;listOfFunctionTerms&gt; object may have the optional 'metaid' and 'sboTerm' 
	  * defined by SBML Level 3 Core. No other attributes from the SBML Level 3 Core 
	  * namespace or the Qualitative Models namespace are permitted on a 
	  * &lt;listOfFunctionTerms&gt; object. Reference: L3V1 Qual V1 Section 3.6 
	  */
 	 public static final int QUAL_20412 = 3020412; 

	 /**
	  * Error code 3020413:
	  * No element of the &lt;listOfFunctionTerms&gt; object may cause the level of a 
	  * &lt;qualitativeSpecies&gt; to exceed the value 'qual:maxLevel' attribute. Reference: 
	  * L3V1 Qual V1 Section 3.6 
	  */
 	 public static final int QUAL_20413 = 3020413; 

	 /**
	  * Error code 3020414:
	  * No element of the &lt;listOfFunctionTerms&gt; object may cause the level of a 
	  * &lt;qualitativeSpecies&gt; to become negative. Reference: L3V1 Qual V1 Section 3.6 
	  */
 	 public static final int QUAL_20414 = 3020414; 

	 /**
	  * Error code 3020501:
	  * An &lt;input&gt; object may have the optional 'metaid' and 'sboTerm' defined by SBML 
	  * Level 3 Core. No other attributes from the SBML Level 3 Core namespace or the 
	  * Qualitative Models namespace are permitted on an &lt;input&gt; object. Reference: L3V1 
	  * Qual V1 Section 3.6.1 
	  */
 	 public static final int QUAL_20501 = 3020501; 

	 /**
	  * Error code 3020502:
	  * An &lt;input&gt; object may have the optional SBML Level 3 Core subobjects for notes 
	  * and annotations. No other elements from the SBML Level 3 Core namespaces are 
	  * permitted on an &lt;input&gt;. Reference: L3V1 Qual V1 Section 3.6.1 
	  */
 	 public static final int QUAL_20502 = 3020502; 

	 /**
	  * Error code 3020503:
	  * An &lt;input&gt; object must have the required attributes 'qual:qualitativeSpecies' as 
	  * well as 'qual:transitionEffect' and may have the optional attributes 'qual:id', 
	  * 'qual:name', 'qual:sign' and 'qual:thresholdLevel'. No other attributes from the 
	  * SBML Level 3 Qualitative Models namespace are permitted on an &lt;input&gt; object. 
	  * Reference: L3V1 Qual V1 Section 3.6.1 
	  */
 	 public static final int QUAL_20503 = 3020503; 

	 /**
	  * Error code 3020504:
	  * The attribute 'qual:name' in &lt;input&gt; must be of the data type string. Reference: 
	  * L3V1 Qual V1 Section 3.6.1 
	  */
 	 public static final int QUAL_20504 = 3020504; 

	 /**
	  * Error code 3020505:
	  * The value of the attribute 'qual:sign' of an &lt;input&gt; object must conform to the 
	  * syntax of the SBML data type 'sign' and may only take on the allowed values of 
	  * 'sign' defined in SBML; that is, the value must be one of the following: 
	  * 'positive', 'negative', 'dual' or 'unknown'. Reference: L3V1 Qual V1 Section 
	  * 3.6.1 
	  */
 	 public static final int QUAL_20505 = 3020505; 

	 /**
	  * Error code 3020506:
	  * The value of the attribute 'qual:transitionEffect' of an &lt;input&gt; object must 
	  * conform to the syntax of the SBML data type 'transitionInputEffect' and may only 
	  * take on the allowed values of 'transitionInputEffect' defined in SBML; that is, 
	  * the value must be one of the following: 'none' or 'consumption'. Reference: L3V1 
	  * Qual V1 Section 3.6.1 
	  */
 	 public static final int QUAL_20506 = 3020506; 

	 /**
	  * Error code 3020507:
	  * The attribute 'qual:thresholdLevel' in &lt;input&gt; must be of the data type 
	  * 'integer'. Reference: L3V1 Qual V1 Section 3.6.1 
	  */
 	 public static final int QUAL_20507 = 3020507; 

	 /**
	  * Error code 3020508:
	  * The value of the attribute 'qual:qualitativeSpecies' in an &lt;input&gt; object must 
	  * be the identifier of an existing &lt;qualitativeSpecies&gt; object defined in the 
	  * enclosing &lt;model&gt; object. Reference: L3V1 Qual V1 Section 3.6.1 
	  */
 	 public static final int QUAL_20508 = 3020508; 

	 /**
	  * Error code 3020509:
	  * An &lt;input&gt; that refers to a &lt;qualitativeSpecies&gt; that has a 'qual:constant' 
	  * attribute set to 'true' cannot have the attribute 'qual:transitionEffect' set to 
	  * 'consumption'. Reference: L3V1 Qual V1 Section 3.6.1 
	  */
 	 public static final int QUAL_20509 = 3020509; 

	 /**
	  * Error code 3020510:
	  * The attribute 'qual:thresholdLevel' in &lt;input&gt; must not be negative. Reference: 
	  * L3V1 Qual V1 Section 3.6.1 
	  */
 	 public static final int QUAL_20510 = 3020510; 

	 /**
	  * Error code 3020601:
	  * An &lt;output&gt; object may have the optional 'metaid' and 'sboTerm' defined by SBML 
	  * Level 3 Core. No other attributes from the SBML Level 3 Core namespace or the 
	  * Qualitative Models namespace are permitted on an &lt;output&gt; object. Reference: 
	  * L3V1 Qual V1 Section 3.6.2 
	  */
 	 public static final int QUAL_20601 = 3020601; 

	 /**
	  * Error code 3020602:
	  * An &lt;output&gt; object may have the optional SBML Level 3 Core subobjects for notes 
	  * and annotations. No other elements from the SBML Level 3 Core namespaces are 
	  * permitted on an &lt;output&gt;. Reference: L3V1 Qual V1 Section 3.6.1 
	  */
 	 public static final int QUAL_20602 = 3020602; 

	 /**
	  * Error code 3020603:
	  * An &lt;output&gt; object must have the required attributes 'qual:qualitativeSpecies' 
	  * as well as 'qual:transitionEffect' and may have the optional attributes 
	  * 'qual:id', 'qual:name' and 'qual:outputLevel'. No other attributes from the SBML 
	  * Level 3 Qualitative Models namespace are permitted on an &lt;output&gt; object. 
	  * Reference: L3V1 Qual V1 Section 3.6.2 
	  */
 	 public static final int QUAL_20603 = 3020603; 

	 /**
	  * Error code 3020604:
	  * The attribute 'qual:name' in &lt;output&gt; must be of the data type string. 
	  * Reference: L3V1 Qual V1 Section 3.6.2 
	  */
 	 public static final int QUAL_20604 = 3020604; 

	 /**
	  * Error code 3020605:
	  * The value of the attribute 'qual:transitionEffect' of an &lt;output&gt; object must 
	  * conform to the syntax of the SBML data type 'transitionOutputEffect' and may 
	  * only take on the allowed values of 'transitionOutputEffect' defined in SBML; 
	  * that is, the value must be one of the following: 'production' or 
	  * 'assignmentLevel'. Reference: L3V1 Qual V1 Section 3.6.2 
	  */
 	 public static final int QUAL_20605 = 3020605; 

	 /**
	  * Error code 3020606:
	  * The attribute 'qual:outputLevel' in &lt;output&gt; must be of the data type 'integer'. 
	  * Reference: L3V1 Qual V1 Section 3.6.2 
	  */
 	 public static final int QUAL_20606 = 3020606; 

	 /**
	  * Error code 3020607:
	  * The value of the attribute 'qual:qualitativeSpecies' in an &lt;output&gt; object must 
	  * be the identifier of an existing &lt;qualitativeSpecies&gt; object defined in the 
	  * enclosing &lt;model&gt; object. Reference: L3V1 Qual V1 Section 3.6.2 
	  */
 	 public static final int QUAL_20607 = 3020607; 

	 /**
	  * Error code 3020608:
	  * The &lt;qualitativeSpecies&gt; referred to by the attribute 'qual:qualitativeSpecies' 
	  * in an &lt;output&gt; object must have the value of its 'qual:constant' attribute set 
	  * to 'false'. Reference: L3V1 Qual V1 Section 3.6.2 
	  */
 	 public static final int QUAL_20608 = 3020608; 

	 /**
	  * Error code 3020609:
	  * When the value of the attribute 'qual:transitionEffect' of an &lt;output&gt; object is 
	  * set to the value 'production' the attribute 'qual:outputLevel' for that 
	  * particular &lt;output&gt; object must have a value set. Reference: L3V1 Qual V1 
	  * Section 3.6.2 
	  */
 	 public static final int QUAL_20609 = 3020609; 

	 /**
	  * Error code 3020610:
	  * The attribute 'qual:outputLevel' in &lt;output&gt; must not be negative. Reference: 
	  * L3V1 Qual V1 Section 3.6.2 
	  */
 	 public static final int QUAL_20610 = 3020610; 

	 /**
	  * Error code 3020701:
	  * A &lt;defaultTerm&gt; object may have the optional 'metaid' and 'sboTerm' defined by 
	  * SBML Level 3 Core. No other attributes from the SBML Level 3 Core namespace or 
	  * the Qualitative Models namespace are permitted on a &lt;defaultTerm&gt; object. 
	  * Reference: L3V1 Qual V1 Section 3.6.4 
	  */
 	 public static final int QUAL_20701 = 3020701; 

	 /**
	  * Error code 3020702:
	  * A &lt;defaultTerm&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespaces 
	  * are permitted on a &lt;defaultTerm&gt;. Reference: L3V1 Qual V1 Section 3.6.4 
	  */
 	 public static final int QUAL_20702 = 3020702; 

	 /**
	  * Error code 3020703:
	  * A &lt;defaultTerm&gt; object must have the required attributes 'qual:resultLevel'. No 
	  * other attributes from the SBML Level 3 Qualitative Models namespace are 
	  * permitted on a &lt;defaultTerm&gt; object. Reference: L3V1 Qual V1 Section 3.6.4 
	  */
 	 public static final int QUAL_20703 = 3020703; 

	 /**
	  * Error code 3020704:
	  * The attribute 'qual:resultLevel' in &lt;defaultTerm&gt; must be of the data type 
	  * 'integer'. Reference: L3V1 Qual V1 Section 3.6.4 
	  */
 	 public static final int QUAL_20704 = 3020704; 

	 /**
	  * Error code 3020705:
	  * The attribute 'qual:resultLevel' in &lt;defaultTerm&gt; must not be negative. 
	  * Reference: L3V1 Qual V1 Section 3.6.4 
	  */
 	 public static final int QUAL_20705 = 3020705; 

	 /**
	  * Error code 3020801:
	  * A &lt;functionTerm&gt; object may have the optional 'metaid' and 'sboTerm' defined by 
	  * SBML Level 3 Core. No other attributes from the SBML Level 3 Core namespace or 
	  * the Qualitative Models namespace are permitted on a &lt;functionTerm&gt; object. 
	  * Reference: L3V1 Qual V1 Section 3.6.5 
	  */
 	 public static final int QUAL_20801 = 3020801; 

	 /**
	  * Error code 3020802:
	  * A &lt;functionTerm&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespaces 
	  * are permitted on a &lt;functionTerm&gt;. Reference: L3V1 Qual V1 Section 3.6.5 
	  */
 	 public static final int QUAL_20802 = 3020802; 

	 /**
	  * Error code 3020803:
	  * A &lt;functionTerm&gt; object must have the required attributes 'qual:resultLevel'. No 
	  * other attributes from the SBML Level 3 Qualitative Models namespace are 
	  * permitted on a &lt;functionTerm&gt; object. Reference: L3V1 Qual V1 Section 3.6.5 
	  */
 	 public static final int QUAL_20803 = 3020803; 

	 /**
	  * Error code 3020804:
	  * A &lt;functionTerm&gt; object may contain exactly one MathML &lt;math&gt; element. No other 
	  * elements from the SBML Level 3 Qualitative Models namespace are permitted on a 
	  * &lt;functionTerm&gt; object. Reference: L3V1 Qual V1 Section 3.6.5 
	  */
 	 public static final int QUAL_20804 = 3020804; 

	 /**
	  * Error code 3020805:
	  * The attribute 'qual:resultLevel' in &lt;functionTerm&gt; must be of the data type 
	  * 'integer'. Reference: L3V1 Qual V1 Section 3.6.5 
	  */
 	 public static final int QUAL_20805 = 3020805; 

	 /**
	  * Error code 3020806:
	  * The attribute 'qual:resultLevel' in &lt;functionTerm&gt; must not be negative. 
	  * Reference: L3V1 Qual V1 Section 3.6.5 
	  */
 	 public static final int QUAL_20806 = 3020806; 

	 /**
	  * Error code 4010100:
	  */
 	 public static final int GROUPS_10100 = 4010100; 

	 /**
	  * Error code 4010101:
	  * To conform to the Groups Package specification for SBML Level 3 Version 1, an 
	  * SBML document must declare 
	  * 'http://www.sbml.org/sbml/level3/version1/groups/version1' as the XMLNamespace 
	  * to use for elements of this package. Reference: L3V1 Groups V1 Section 3.1 
	  */
 	 public static final int GROUPS_10101 = 4010101; 

	 /**
	  * Error code 4010102:
	  * Wherever they appear in an SBML document, elements and attributes from the 
	  * Groups Package must use the 
	  * 'http://www.sbml.org/sbml/level3/version1/groups/version1' namespace, declaring 
	  * so either explicitly or implicitly. Reference: L3V1 Groups V1 Section 3.1 
	  */
 	 public static final int GROUPS_10102 = 4010102; 

	 /**
	  * Error code 4010301:
	  * (Extends validation rule #10301 in the SBML Level 3 Core specification. TO DO 
	  * list scope of ids) Reference: L3V1 Groups V1 Section 3.3 
	  */
 	 public static final int GROUPS_10301 = 4010301; 

	 /**
	  * Error code 4010302:
	  * The value of a 'groups:id' must conform to the syntax of the &lt;sbml&gt; data type 
	  * 'SId' Reference: L3V1 Groups V1 Section 3.3.1 
	  */
 	 public static final int GROUPS_10302 = 4010302; 

	 /**
	  * Error code 4020101:
	  * In all SBML documents using the Groups Package, the &lt;sbml&gt; object must have the 
	  * 'groups:required' attribute. Reference: L3V1 Core Section 4.1.2 
	  */
 	 public static final int GROUPS_20101 = 4020101; 

	 /**
	  * Error code 4020102:
	  * The value of attribute 'groups:required' on the &lt;sbml&gt; object must be of data 
	  * type Boolean. Reference: L3V1 Core Section 4.1.2 
	  */
 	 public static final int GROUPS_20102 = 4020102; 

	 /**
	  * Error code 4020103:
	  * The value of attribute 'groups:required' on the &lt;sbml&gt; object must be set to 
	  * 'false'. Reference: L3V1 Groups V1 Section 3.1 
	  */
 	 public static final int GROUPS_20103 = 4020103; 

	 /**
	  * Error code 4020201:
	  * A &lt;model&gt; object may contain one and only one instance of the &lt;listOfGroups&gt; 
	  * element. No other elements from the SBML Level 3 Groups namespaces are permitted 
	  * on a &lt;model&gt; object. Reference: L3V1 Groups V1 Section 3.6 
	  */
 	 public static final int GROUPS_20201 = 4020201; 

	 /**
	  * Error code 4020202:
	  * The &lt;listOfGroups&gt; subobject on a &lt;model&gt; object is optional, but if present, 
	  * this container object must not be empty. Reference: L3V1 Groups V1 Section 3.6 
	  */
 	 public static final int GROUPS_20202 = 4020202; 

	 /**
	  * Error code 4020203:
	  * Apart from the general notes and annotations subobjects permitted on all SBML 
	  * objects, a &lt;listOfGroups&gt; container object may only contain &lt;group&gt; objects. 
	  * Reference: L3V1 Groups V1 Section 3.6 
	  */
 	 public static final int GROUPS_20203 = 4020203; 

	 /**
	  * Error code 4020204:
	  * A &lt;listOfGroups&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;listOfGroups&gt; object. Reference: L3V1 Groups V1 
	  * Section 3.6 
	  */
 	 public static final int GROUPS_20204 = 4020204; 

	 /**
	  * Error code 4020301:
	  * A &lt;group&gt; object may have the optional SBML Level 3 Core attributes 'metaid' and 
	  * 'sboTerm'. No other attributes from the SBML Level 3 Core namespaces are 
	  * permitted on a &lt;group&gt;. Reference: L3V1 Core Section 3.2 
	  */
 	 public static final int GROUPS_20301 = 4020301; 

	 /**
	  * Error code 4020302:
	  * A &lt;group&gt; object may have the optional SBML Level 3 Core subobjects for notes 
	  * and annotations. No other elements from the SBML Level 3 Core namespaces are 
	  * permitted on a &lt;group&gt;. Reference: L3V1 Core Section 3.2 
	  */
 	 public static final int GROUPS_20302 = 4020302; 

	 /**
	  * Error code 4020303:
	  * A &lt;group&gt; object must have the required attribute 'groups:kind', and may have 
	  * the optional attributes 'groups:id' and 'groups:name'. No other attributes from 
	  * the SBML Level 3 Groups namespaces are permitted on a &lt;group&gt; object. Reference: 
	  * L3V1 Groups V1 Section 3.3 
	  */
 	 public static final int GROUPS_20303 = 4020303; 

	 /**
	  * Error code 4020304:
	  * A &lt;group&gt; object may contain one and only one instance of the &lt;listOfMembers&gt; 
	  * element. No other elements from the SBML Level 3 Groups namespaces are permitted 
	  * on a &lt;group&gt; object. Reference: L3V1 Groups V1 Section 3.3 
	  */
 	 public static final int GROUPS_20304 = 4020304; 

	 /**
	  * Error code 4020305:
	  * The value of the attribute 'groups:kind' of a &lt;group&gt; object must conform to the 
	  * syntax of SBML data type 'groupKind' and may only take on the allowed values of 
	  * 'groupKind' defined in SBML; that is, the value must be one of the following: 
	  * 'classification', 'partonomy' or 'collection'. Reference: L3V1 Groups V1 Section 
	  * 3.3 
	  */
 	 public static final int GROUPS_20305 = 4020305; 

	 /**
	  * Error code 4020306:
	  * The attribute 'groups:name' on a &lt;group&gt; must have a value of data type 
	  * 'string'. Reference: L3V1 Groups V1 Section 3.3 
	  */
 	 public static final int GROUPS_20306 = 4020306; 

	 /**
	  * Error code 4020307:
	  * The &lt;listOfMembers&gt; subobject on a &lt;group&gt; object is optional, but if present, 
	  * this container object must not be empty. Reference: L3V1 Groups V1 Section 3.3 
	  */
 	 public static final int GROUPS_20307 = 4020307; 

	 /**
	  * Error code 4020308:
	  * Apart from the general notes and annotations subobjects permitted on all SBML 
	  * objects, a &lt;listOfMembers&gt; container object may only contain &lt;member&gt; objects. 
	  * Reference: L3V1 Core Section 3.2 
	  */
 	 public static final int GROUPS_20308 = 4020308; 

	 /**
	  * Error code 4020309:
	  * A &lt;listOfMembers&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;listOfMembers&gt; object. Reference: L3V1 Core 
	  * Section 3.2 
	  */
 	 public static final int GROUPS_20309 = 4020309; 

	 /**
	  * Error code 4020310:
	  * A &lt;listOfMembers&gt; object may have the optional attributes 'groups:id' and 
	  * 'groups:name'. No other attributes from the SBML Level 3 Groups namespaces are 
	  * permitted on a &lt;listOfMembers&gt; object. Reference: L3V1 Groups V1 Section 3.4 
	  */
 	 public static final int GROUPS_20310 = 4020310; 

	 /**
	  * Error code 4020311:
	  * The attribute 'groups:name' on a &lt;listOfMembers&gt; must have a value of data type 
	  * 'string'. Reference: L3V1 Groups V1 Section 3.4 
	  */
 	 public static final int GROUPS_20311 = 4020311; 

	 /**
	  * Error code 4020312:
	  * If &lt;listOfMembers&gt; objects from different &lt;group&gt; objects contain &lt;member&gt; 
	  * elements that reference the same SBase object, the 'sboTerm' attribute and any 
	  * child &lt;notes&gt; or &lt;annotation&gt; elements set for those &lt;listOfMembers&gt; should be 
	  * consistent, as they should all apply to the same referenced object. Reference: 
	  * L3V1 Groups V1 Section 3.5.4 
	  */
 	 public static final int GROUPS_20312 = 4020312; 

	 /**
	  * Error code 4020313:
	  * Member references may not be circular: no &lt;member&gt;'s 'idRef' or 'metaIdRef' may 
	  * reference itself, its parent &lt;listOfMembers&gt;, nor its parent &lt;group&gt;. If a 
	  * &lt;member&gt; references a &lt;group&gt; or a &lt;listOfMembers&gt;, the same restrictions apply 
	  * to that subgroup's children: they may not reference the &lt;member&gt;, its parent 
	  * &lt;listOfMembers&gt;, nor its parent &lt;group&gt;, and if any of those children reference 
	  * a &lt;group&gt;, the same restrictions apply to them, etc. Reference: L3V1 Groups V1 
	  * Section 3.5.4 
	  */
 	 public static final int GROUPS_20313 = 4020313; 

	 /**
	  * Error code 4020401:
	  * A &lt;member&gt; object may have the optional SBML Level 3 Core attributes 'metaid' 
	  * and 'sboTerm'. No other attributes from the SBML Level 3 Core namespaces are 
	  * permitted on a &lt;member&gt;. Reference: L3V1 Core Section 3.2 
	  */
 	 public static final int GROUPS_20401 = 4020401; 

	 /**
	  * Error code 4020402:
	  * A &lt;member&gt; object may have the optional SBML Level 3 Core subobjects for notes 
	  * and annotations. No other elements from the SBML Level 3 Core namespaces are 
	  * permitted on a &lt;member&gt;. Reference: L3V1 Core Section 3.2 
	  */
 	 public static final int GROUPS_20402 = 4020402; 

	 /**
	  * Error code 4020403:
	  * A &lt;member&gt; object may have the optional attributes 'groups:id' and 'groups:name' 
	  * and must have a value for one (and exactly one) of the attributes 'groups:idRef' 
	  * and 'groups:metaIdRef'. No other attributes from the SBML Level 3 Groups 
	  * namespaces are permitted on a &lt;member&gt; object. Reference: L3V1 Groups V1 Section 
	  * 3.5 
	  */
 	 public static final int GROUPS_20403 = 4020403; 

	 /**
	  * Error code 4020404:
	  * The attribute 'groups:name' on a &lt;member&gt; must have a value of data type 
	  * 'string'. Reference: L3V1 Groups V1 Section 3.5 
	  */
 	 public static final int GROUPS_20404 = 4020404; 

	 /**
	  * Error code 4020405:
	  * The value of the attribute 'groups:idRef' of a &lt;member&gt; object must be the 
	  * identifier of an existing object derived from the 'SBase' class and defined in 
	  * the enclosing &lt;model&gt; object. Reference: L3V1 Groups V1 Section 
	  */
 	 public static final int GROUPS_20405 = 4020405; 

	 /**
	  * Error code 4020406:
	  * The value of the attribute 'groups:metaIdRef' of a &lt;member&gt; object must be the 
	  * 'metaid' of an existing 'SBase' object defined in the enclosing &lt;model&gt; object. 
	  * Reference: L3V1 Groups V1 Section 3.5 
	  */
 	 public static final int GROUPS_20406 = 4020406; 

	 /**
	  * Error code 4020407:
	  * The value of the attribute 'groups:idRef' of a &lt;member&gt; object must conform to 
	  * the syntax of the SBML data type 'SId'. Reference: L3V1 Groups V1 Section 3.5 
	  */
 	 public static final int GROUPS_20407 = 4020407; 

	 /**
	  * Error code 4020408:
	  * The value of the attribute 'groups:metaIdRef' of a &lt;member&gt; object must conform 
	  * to the syntax of the SBML data type 'ID'. Reference: L3V1 Groups V1 Section 3.5 
	  */
 	 public static final int GROUPS_20408 = 4020408; 

	 /**
	  * Error code 6010100:
	  */
 	 public static final int LAYOUT_10100 = 6010100; 

	 /**
	  * Error code 6010101:
	  * To conform to Version 1 of the Layout package specification for SBML Level 3, an 
	  * SBML document must declare the use of the following XML Namespace: 
	  * 'http://www.sbml.org/sbml/level3/version1/layout/version1' Reference: L3V1 
	  * Layout V1 Section 3.1 
	  */
 	 public static final int LAYOUT_10101 = 6010101; 

	 /**
	  * Error code 6010102:
	  * Wherever they appear in an SBML document, elements and attributes from the 
	  * Layout package must be declared either implicitly or explicitly to be in the XML 
	  * namespace 'http://www.sbml.org/sbml/level3/version1/layout/version1' Reference: 
	  * L3V1 Layout V1 Section 3.1 
	  */
 	 public static final int LAYOUT_10102 = 6010102; 

	 /**
	  * Error code 6010301:
	  * (Extends validation rule #10301 in the SBML Level 3 Version 1 Core 
	  * specification.) Within a &lt;model&gt; object the values of the attributes id and 
	  * layout:id on every instance of the following classes of objects must be unique 
	  * across the set of all id and layout:id attribute values of all such objects in a 
	  * model: the model itself, plus all contained &lt;functionDefinition&gt;, &lt;compartment&gt;, 
	  * &lt;species&gt;, &lt;reaction&gt;, &lt;speciesReference&gt;, &lt;modifierSpeciesReference&gt;, &lt;event&gt;, 
	  * and &lt;parameter&gt; objects, plus the &lt;boundingBox&gt;, &lt;compartmentGlyph&gt;, 
	  * &lt;generalGlyph&gt;, &lt;graphicalObject&gt;, &lt;layout&gt;, &lt;speciesGlyph&gt;, 
	  * &lt;speciesReferenceGlyph&gt;, &lt;reactionGlyph&gt;, &lt;referenceGlyph&gt; and &lt;textGlyph&gt; 
	  * objects defined by the Layout package. Reference: L3V1 Layout V1 Section 3.3 
	  */
 	 public static final int LAYOUT_10301 = 6010301; 

	 /**
	  * Error code 6010302:
	  * The value of the 'layout:id' attribute must conform to the SBML data type SId 
	  * Reference: L3V1 Layout V1 Section 3.3 
	  */
 	 public static final int LAYOUT_10302 = 6010302; 

	 /**
	  * Error code 6010401:
	  * The attribute 'xsi:type' must be present on all &lt;lineSegment&gt; and &lt;cubicBezier&gt; 
	  * objects. It is not permitted on any other elements from the SBML Level 3 Layout 
	  * namespace. Reference: L3V1 Layout V1 Section 3.3 
	  */
 	 public static final int LAYOUT_10401 = 6010401; 

	 /**
	  * Error code 6010402:
	  * The value of the 'xsi:type' attribute must be either 'LineSegment' or 
	  * 'CubicBezier' appropriate to the object where it is located. No other values are 
	  * permitted. Reference: L3V1 Layout V1 Section 3.3 
	  */
 	 public static final int LAYOUT_10402 = 6010402; 

	 /**
	  * Error code 6020101:
	  * In all SBML documents using the Layout package, the SBML object must include a 
	  * value for the attribute 'layout:required'. Reference: L3V1 Core Section 4.1.2 
	  */
 	 public static final int LAYOUT_20101 = 6020101; 

	 /**
	  * Error code 6020102:
	  * The value of attribute 'layout:required' on the SBML object must be of the data 
	  * type Boolean. Reference: L3V1 Core Section 4.1.2 
	  */
 	 public static final int LAYOUT_20102 = 6020102; 

	 /**
	  * Error code 6020103:
	  * The value of attribute 'layout:required' on the SBML object must be set to 
	  * 'false'. Reference: L3V1 Layout V1 Section 3.1 
	  */
 	 public static final int LAYOUT_20103 = 6020103; 

	 /**
	  * Error code 6020201:
	  * There may be at most one instance of &lt;listOfLayouts&gt; element within a &lt;model&gt; 
	  * object using Layout. No other elements from the Layout package are allowed. 
	  * Reference: L3V1 Layout V1 Section 3.5 
	  */
 	 public static final int LAYOUT_20201 = 6020201; 

	 /**
	  * Error code 6020202:
	  * The &lt;listOfLayouts&gt; within a &lt;model&gt; object is optional, but if present, this 
	  * object must not be empty. Reference: L3V1 Layout V1 Section 3.5 
	  */
 	 public static final int LAYOUT_20202 = 6020202; 

	 /**
	  * Error code 6020203:
	  * Apart from the general notes and annotation subobjects permitted on all SBML 
	  * objects, a &lt;listOfLayouts&gt; container object may only contain &lt;layout&gt; objects. 
	  * Reference: L3V1 Layout V1 Section 3.5 
	  */
 	 public static final int LAYOUT_20203 = 6020203; 

	 /**
	  * Error code 6020204:
	  * A &lt;listOfLayouts&gt; object may have the optional attributes 'metaid' and 'sboTerm' 
	  * defined by SBML Level 3 Core. No other attributes from the SBML Level 3 Core 
	  * namespace or the Layout namespace are permitted on a &lt;listOfLayouts&gt; object. 
	  * Reference: L3V1 Layout V1 Section 3.5 
	  */
 	 public static final int LAYOUT_20204 = 6020204; 

	 /**
	  * Error code 6020301:
	  * A &lt;layout&gt; object may have the optional SBML Level 3 Core subobjects for notes 
	  * and annotations. No other elements from the SBML Level 3 Core namespace are 
	  * permitted on a &lt;layout&gt;. Reference: L3V1 Core Section 3.2 
	  */
 	 public static final int LAYOUT_20301 = 6020301; 

	 /**
	  * Error code 6020302:
	  * A &lt;layout&gt; object may have the optional SBML Level 3 Core attributes 'metaid' 
	  * and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are 
	  * permitted on a &lt;layout&gt; object. Reference: L3V1 Core Section 3.2 
	  */
 	 public static final int LAYOUT_20302 = 6020302; 

	 /**
	  * Error code 6020303:
	  * There may be at most one instance of each of the following kinds of objects 
	  * within a &lt;layout&gt; object: &lt;listOfCompartmentGlyphs&gt;, &lt;listOfSpeciesGlyphs&gt;, 
	  * &lt;listOfReactionGlyphs&gt;, &lt;listOfTextGlyphs&gt;, &lt;listOfAdditionalGraphicalObjects&gt;. 
	  * Reference: L3V1 Layout V1 Section 3.6 
	  */
 	 public static final int LAYOUT_20303 = 6020303; 

	 /**
	  * Error code 6020304:
	  * The various ListOf subobjects within a &lt;layout&gt; object are optional, but if 
	  * present, these container object must not be empty. Specifically, if any of the 
	  * following classes of objects are present on the &lt;layout&gt;, it must not be empty: 
	  * &lt;listOfCompartmentGlyphs&gt;, &lt;listOfSpeciesGlyphs&gt;, &lt;listOfReactionGlyphs&gt;, 
	  * &lt;listOfTextGlyphs&gt;, &lt;listOfAdditionalGraphicalObjects&gt;. Reference: L3V1 Layout 
	  * V1 Section 3.6 
	  */
 	 public static final int LAYOUT_20304 = 6020304; 

	 /**
	  * Error code 6020305:
	  * A &lt;layout&gt; object must have the required attribute 'layout:id' and may have the 
	  * optional attribute 'layout:name'. No other attributes from the SBML Level 3 
	  * Layout namespace are permitted on a &lt;layout&gt; object. Reference: L3V1 Layout V1 
	  * Section 3.6 
	  */
 	 public static final int LAYOUT_20305 = 6020305; 

	 /**
	  * Error code 6020306:
	  * The attribute 'layout:name' of a &lt;layout&gt; must be of the data type 'string'. 
	  * Reference: L3V1 Layout V1 Section 3.6 
	  */
 	 public static final int LAYOUT_20306 = 6020306; 

	 /**
	  * Error code 6020307:
	  * A &lt;listOfCompartmentGlyphs&gt; object may have the optional attributes 'metaid' and 
	  * 'sboTerm' defined by SBML Level 3 Core. No other attributes from the SBML Level 
	  * 3 Core namespace or the Layout namespace are permitted on a 
	  * &lt;listOfCompartmentGlyphs&gt; object. Reference: L3V1 Layout V1 Section 3.6 
	  */
 	 public static final int LAYOUT_20307 = 6020307; 

	 /**
	  * Error code 6020308:
	  * Apart from the general notes and annotation subobjects permitted on all SBML 
	  * objects, a &lt;listOfCompartmentGlyphs&gt; container object may only contain 
	  * &lt;compartmentGlyph&gt; objects. Reference: L3V1 Layout V1 Section 3.6 
	  */
 	 public static final int LAYOUT_20308 = 6020308; 

	 /**
	  * Error code 6020309:
	  * A &lt;listOfSpeciesGlyphs&gt; object may have the optional attributes 'metaid' and 
	  * 'sboTerm' defined by SBML Level 3 Core. No other attributes from the SBML Level 
	  * 3 Core namespace or the Layout namespace are permitted on a 
	  * &lt;listOfSpeciesGlyphs&gt; object. Reference: L3V1 Layout V1 Section 3.6 
	  */
 	 public static final int LAYOUT_20309 = 6020309; 

	 /**
	  * Error code 6020310:
	  * Apart from the general notes and annotation subobjects permitted on all SBML 
	  * objects, a &lt;listOfSpeciesGlyphs&gt; container object may only contain 
	  * &lt;compartmentGlyph&gt; objects. Reference: L3V1 Layout V1 Section 3.6 
	  */
 	 public static final int LAYOUT_20310 = 6020310; 

	 /**
	  * Error code 6020311:
	  * A &lt;listOfReactionGlyphs&gt; object may have the optional attributes 'metaid' and 
	  * 'sboTerm' defined by SBML Level 3 Core. No other attributes from the SBML Level 
	  * 3 Core namespace or the Layout namespace are permitted on a 
	  * &lt;listOfReactionGlyphs&gt; object. Reference: L3V1 Layout V1 Section 3.6 
	  */
 	 public static final int LAYOUT_20311 = 6020311; 

	 /**
	  * Error code 6020312:
	  * Apart from the general notes and annotation subobjects permitted on all SBML 
	  * objects, a &lt;listOfReactionGlyphs&gt; container object may only contain 
	  * &lt;compartmentGlyph&gt; objects. Reference: L3V1 Layout V1 Section 3.6 
	  */
 	 public static final int LAYOUT_20312 = 6020312; 

	 /**
	  * Error code 6020313:
	  * A &lt;listOfAdditionalGraphicalObjectGlyphs&gt; object may have the optional 
	  * attributes 'metaid' and 'sboTerm' defined by SBML Level 3 Core. No other 
	  * attributes from the SBML Level 3 Core namespace or the Layout namespace are 
	  * permitted on a &lt;listOfAdditionalGraphicalObjectGlyphs&gt; object. Reference: L3V1 
	  * Layout V1 Section 3.6 
	  */
 	 public static final int LAYOUT_20313 = 6020313; 

	 /**
	  * Error code 6020314:
	  * Apart from the general notes and annotation subobjects permitted on all SBML 
	  * objects, a &lt;listOfAdditionalGraphicalObjectGlyphs&gt; container object may only 
	  * contain &lt;compartmentGlyph&gt; objects. Reference: L3V1 Layout V1 Section 3.6 
	  */
 	 public static final int LAYOUT_20314 = 6020314; 

	 /**
	  * Error code 6020315:
	  * A &lt;layout&gt; object must contain exactly one &lt;dimensions&gt; object. Reference: L3V1 
	  * Layout V1 Section 3.6 
	  */
 	 public static final int LAYOUT_20315 = 6020315; 

	 /**
	  * Error code 6020316:
	  * A &lt;listOfTextGlyphs&gt; object may have the optional attributes 'metaid' and 
	  * 'sboTerm' defined by SBML Level 3 Core. No other attributes from the SBML Level 
	  * 3 Core namespace or the Layout namespace are permitted on a &lt;listOfTextGlyphs&gt; 
	  * object. Reference: L3V1 Layout V1 Section 3.6 
	  */
 	 public static final int LAYOUT_20316 = 6020316; 

	 /**
	  * Error code 6020317:
	  * Apart from the general notes and annotation subobjects permitted on all SBML 
	  * objects, a &lt;listOfTextGlyphs&gt; container object may only contain 
	  * &lt;compartmentGlyph&gt; objects. Reference: L3V1 Layout V1 Section 3.6 
	  */
 	 public static final int LAYOUT_20317 = 6020317; 

	 /**
	  * Error code 6020401:
	  * A &lt;graphicalObject&gt; object may have the optional SBML Level 3 Core subobjects 
	  * for notes and annotations. No other elements from the SBML Level 3 Core 
	  * namespace are permitted on a &lt;graphicalObject&gt;. Reference: L3V1 Core Section 3.2 
	  */
 	 public static final int LAYOUT_20401 = 6020401; 

	 /**
	  * Error code 6020402:
	  * A &lt;graphicalObject&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace 
	  * are permitted on a &lt;graphicalObject&gt;. Reference: L3V1 Core Section 3.2 
	  */
 	 public static final int LAYOUT_20402 = 6020402; 

	 /**
	  * Error code 6020403:
	  * There may be at most one instance of a &lt;boundingBox&gt; object on a 
	  * &lt;graphicalObject&gt;. No other elements from the Layout namespace are permitted on 
	  * a &lt;graphicalObject&gt;. Reference: L3V1 Layout V1 Section 3.7 
	  */
 	 public static final int LAYOUT_20403 = 6020403; 

	 /**
	  * Error code 6020404:
	  * A &lt;graphicalObject&gt; object must have the required attribute 'layout:id' and may 
	  * have the optional attribute 'layout:metaidRef'. No other attributes from the 
	  * Layout namespace are permitted on a &lt;graphicalObject&gt;. Reference: L3V1 Layout V1 
	  * Section 3.7 
	  */
 	 public static final int LAYOUT_20404 = 6020404; 

	 /**
	  * Error code 6020405:
	  * The attribute 'layout:metaidRef' of a &lt;graphicalObject&gt; must be of the data type 
	  * 'IDREF'. Reference: L3V1 Layout V1 Section 3.7 
	  */
 	 public static final int LAYOUT_20405 = 6020405; 

	 /**
	  * Error code 6020406:
	  * The value of a 'layout:metaidRef' attribute of a &lt;graphicalObject&gt; must be of 
	  * the 'metaid' of an existing element in the &lt;model&gt;. Reference: L3V1 Layout V1 
	  * Section 3.7 
	  */
 	 public static final int LAYOUT_20406 = 6020406; 

	 /**
	  * Error code 6020407:
	  * A &lt;graphicalObject&gt; must contain exactly one &lt;boundingBox&gt; object. Reference: 
	  * L3V1 Layout V1 Section 3.7 
	  */
 	 public static final int LAYOUT_20407 = 6020407; 

	 /**
	  * Error code 6020501:
	  * A &lt;compartmentGlyph&gt; object may have the optional SBML Level 3 Core subobjects 
	  * for notes and annotations. No other elements from the SBML Level 3 Core 
	  * namespace are permitted on a &lt;compartmentGlyph&gt;. Reference: L3V1 Core Section 
	  * 3.2 
	  */
 	 public static final int LAYOUT_20501 = 6020501; 

	 /**
	  * Error code 6020502:
	  * A &lt;compartmentGlyph&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace 
	  * are permitted on a &lt;compartmentGlyph&gt;. Reference: L3V1 Core Section 3.2 
	  */
 	 public static final int LAYOUT_20502 = 6020502; 

	 /**
	  * Error code 6020503:
	  * There may be at most one instance of a &lt;boundingBox&gt; object on a 
	  * &lt;compartmentGlyph&gt;. No other elements from the Layout namespace are permitted on 
	  * a &lt;compartmentGlyph&gt;. Reference: L3V1 Layout V1 Section 3.8 
	  */
 	 public static final int LAYOUT_20503 = 6020503; 

	 /**
	  * Error code 6020504:
	  * A &lt;compartmentGlyph&gt; object must have the required attribute 'layout:id' and may 
	  * have the optional attributes 'layout:metaidRef' or 'layout:compartment'. No 
	  * other attributes from the Layout namespace are permitted on a 
	  * &lt;compartmentGlyph&gt;. Reference: L3V1 Layout V1 Section 3.8 
	  */
 	 public static final int LAYOUT_20504 = 6020504; 

	 /**
	  * Error code 6020505:
	  * The attribute 'layout:metaidRef' of a &lt;compartmentGlyph&gt; must be of the data 
	  * type 'IDREF'. Reference: L3V1 Layout V1 Section 3.7 
	  */
 	 public static final int LAYOUT_20505 = 6020505; 

	 /**
	  * Error code 6020506:
	  * The value of a 'layout:metaidRef' attribute of a &lt;compartmentGlyph&gt; must be of 
	  * the 'metaid' of an existing element in the &lt;model&gt;. Reference: L3V1 Layout V1 
	  * Section 3.7 
	  */
 	 public static final int LAYOUT_20506 = 6020506; 

	 /**
	  * Error code 6020507:
	  * The attribute 'layout:compartment' of a &lt;compartmentGlyph&gt; must be the data type 
	  * 'SIdRef'. Reference: L3V1 Layout V1 Section 3.8 
	  */
 	 public static final int LAYOUT_20507 = 6020507; 

	 /**
	  * Error code 6020508:
	  * The value of the 'layout:compartment' attribute of a &lt;compartmentGlyph&gt; must be 
	  * of the 'id' of an existing &lt;compartment&gt; in the &lt;model&gt;. Reference: L3V1 Layout 
	  * V1 Section 3.8 
	  */
 	 public static final int LAYOUT_20508 = 6020508; 

	 /**
	  * Error code 6020509:
	  * If both attributes 'layout:compartment' and 'layout:metaidRef' are specified on 
	  * a &lt;compartmentGlyph&gt; they have to reference the same &lt;compartment&gt; in the 
	  * &lt;model&gt;. Reference: L3V1 Layout V1 Section 3.8 
	  */
 	 public static final int LAYOUT_20509 = 6020509; 

	 /**
	  * Error code 6020510:
	  * The attribute 'layout:order' of a &lt;compartmentGlyph&gt; must be the data type 
	  * 'double'. Reference: L3V1 Layout V1 Section 3.8 
	  */
 	 public static final int LAYOUT_20510 = 6020510; 

	 /**
	  * Error code 6020601:
	  * A &lt;speciesGlyph&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespace 
	  * are permitted on a &lt;speciesGlyph&gt;. Reference: L3V1 Core Section 3.2 
	  */
 	 public static final int LAYOUT_20601 = 6020601; 

	 /**
	  * Error code 6020602:
	  * A &lt;speciesGlyph&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace 
	  * are permitted on a &lt;speciesGlyph&gt;. Reference: L3V1 Core Section 3.2 
	  */
 	 public static final int LAYOUT_20602 = 6020602; 

	 /**
	  * Error code 6020603:
	  * There may be at most one instance of a &lt;boundingBox&gt; object on a &lt;speciesGlyph&gt;. 
	  * No other elements from the Layout namespace are permitted on a &lt;speciesGlyph&gt;. 
	  * Reference: L3V1 Layout V1 Section 3.9 
	  */
 	 public static final int LAYOUT_20603 = 6020603; 

	 /**
	  * Error code 6020604:
	  * A &lt;speciesGlyph&gt; object must have the required attribute 'layout:id' and may 
	  * have the optional attributes 'layout:metaidRef' or 'layout:species'. No other 
	  * attributes from the Layout namespace are permitted on a &lt;speciesGlyph&gt;. 
	  * Reference: L3V1 Layout V1 Section 3.9 
	  */
 	 public static final int LAYOUT_20604 = 6020604; 

	 /**
	  * Error code 6020605:
	  * The attribute 'layout:metaidRef' of a &lt;speciesGlyph&gt; must be of the data type 
	  * 'IDREF'. Reference: L3V1 Layout V1 Section 3.7 
	  */
 	 public static final int LAYOUT_20605 = 6020605; 

	 /**
	  * Error code 6020606:
	  * The value of a 'layout:metaidRef' attribute of a &lt;speciesGlyph&gt; must be of the 
	  * 'metaid' of an existing element in the &lt;model&gt;. Reference: L3V1 Layout V1 
	  * Section 3.7 
	  */
 	 public static final int LAYOUT_20606 = 6020606; 

	 /**
	  * Error code 6020607:
	  * The attribute 'layout:species' of a &lt;speciesGlyph&gt; must be the data type 
	  * 'SIdRef'. Reference: L3V1 Layout V1 Section 3.9 
	  */
 	 public static final int LAYOUT_20607 = 6020607; 

	 /**
	  * Error code 6020608:
	  * The value of the 'layout:species' attribute of a &lt;speciesGlyph&gt; must be of the 
	  * 'id' of an existing &lt;species&gt; in the &lt;model&gt;. Reference: L3V1 Layout V1 Section 
	  * 3.8 
	  */
 	 public static final int LAYOUT_20608 = 6020608; 

	 /**
	  * Error code 6020609:
	  * If both attributes 'layout:species' and 'layout:metaidRef' are specified on a 
	  * &lt;speciesGlyph&gt; they have to reference the same &lt;species&gt; in the &lt;model&gt;. 
	  * Reference: L3V1 Layout V1 Section 3.9 
	  */
 	 public static final int LAYOUT_20609 = 6020609; 

	 /**
	  * Error code 6020701:
	  * A &lt;reactionGlyph&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespace 
	  * are permitted on a &lt;reactionGlyph&gt;. Reference: L3V1 Core Section 3.2 
	  */
 	 public static final int LAYOUT_20701 = 6020701; 

	 /**
	  * Error code 6020702:
	  * A &lt;reactionGlyph&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace 
	  * are permitted on a &lt;reactionGlyph&gt;. Reference: L3V1 Core Section 3.2 
	  */
 	 public static final int LAYOUT_20702 = 6020702; 

	 /**
	  * Error code 6020703:
	  * There may be at most one instance of each of the following kinds of objects 
	  * within a &lt;reactionGlyph&gt; object: &lt;boundingBox&gt;, and &lt;curve&gt; and there must be 
	  * one instance of the &lt;listOfSpeciesReferenceGlyphs&gt;. No other elements from the 
	  * Layout namespace are permitted on a &lt;reactionGlyph&gt;. Reference: L3V1 Layout V1 
	  * Section 3.10 
	  */
 	 public static final int LAYOUT_20703 = 6020703; 

	 /**
	  * Error code 6020704:
	  * A &lt;reactionGlyph&gt; object must have the required attribute 'layout:id' and may 
	  * have the optional attributes 'layout:metaidRef' or 'layout:reaction'. No other 
	  * attributes from the Layout namespace are permitted on a &lt;reactionGlyph&gt;. 
	  * Reference: L3V1 Layout V1 Section 3.10 
	  */
 	 public static final int LAYOUT_20704 = 6020704; 

	 /**
	  * Error code 6020705:
	  * The attribute 'layout:metaidRef' of a &lt;reactionGlyph&gt; must be of the data type 
	  * 'IDREF'. Reference: L3V1 Layout V1 Section 3.7 
	  */
 	 public static final int LAYOUT_20705 = 6020705; 

	 /**
	  * Error code 6020706:
	  * The value of a 'layout:metaidRef' attribute of a &lt;reactionGlyph&gt; must be of the 
	  * 'metaid' of an existing element in the &lt;model&gt;. Reference: L3V1 Layout V1 
	  * Section 3.7 
	  */
 	 public static final int LAYOUT_20706 = 6020706; 

	 /**
	  * Error code 6020707:
	  * The attribute 'layout:reaction' of a &lt;reactionGlyph&gt; must be the data type 
	  * 'SIdRef'. Reference: L3V1 Layout V1 Section 3.10 
	  */
 	 public static final int LAYOUT_20707 = 6020707; 

	 /**
	  * Error code 6020708:
	  * The value of the 'layout:reaction' attribute of a &lt;reactionGlyph&gt; must be of the 
	  * 'id' of an existing &lt;reaction&gt; in the &lt;model&gt;. Reference: L3V1 Layout V1 Section 
	  * 3.10 
	  */
 	 public static final int LAYOUT_20708 = 6020708; 

	 /**
	  * Error code 6020709:
	  * If both attributes 'layout:reaction' and 'layout:metaidRef' are specified on a 
	  * &lt;reactionGlyph&gt; they have to reference the same &lt;reaction&gt; in the &lt;model&gt;. 
	  * Reference: L3V1 Layout V1 Section 3.10 
	  */
 	 public static final int LAYOUT_20709 = 6020709; 

	 /**
	  * Error code 6020710:
	  * Apart from the general notes and annotation subobjects permitted on all SBML 
	  * objects, a &lt;listOfSpeciesReferenceGlyphs&gt; container object may only contain 
	  * &lt;speciesReferenceGlyph&gt; objects. Reference: L3V1 Layout V1 Section 3.10 
	  */
 	 public static final int LAYOUT_20710 = 6020710; 

	 /**
	  * Error code 6020711:
	  * A &lt;listOfSpeciesReferenceGlyphs&gt; object may have the optional attributes 
	  * 'metaid' and 'sboTerm' defined by SBML Level 3 Core. No other attributes from 
	  * the SBML Level 3 Core namespace or the Layout namespace are permitted on a 
	  * &lt;listOfSpeciesReferenceGlyphs&gt; object. Reference: L3V1 Layout V1 Section 3.10 
	  */
 	 public static final int LAYOUT_20711 = 6020711; 

	 /**
	  * Error code 6020712:
	  * A &lt;listOfSpeciesReferenceGlyphs&gt; container object must not be empty. Reference: 
	  * L3V1 Layout V1 Section 3.10 
	  */
 	 public static final int LAYOUT_20712 = 6020712; 

	 /**
	  * Error code 6020801:
	  * A &lt;generalGlyph&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespace 
	  * are permitted on a &lt;generalGlyph&gt;. Reference: L3V1 Core Section 3.2 
	  */
 	 public static final int LAYOUT_20801 = 6020801; 

	 /**
	  * Error code 6020802:
	  * A &lt;generalGlyph&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace 
	  * are permitted on a &lt;generalGlyph&gt;. Reference: L3V1 Core Section 3.2 
	  */
 	 public static final int LAYOUT_20802 = 6020802; 

	 /**
	  * Error code 6020803:
	  * There may be at most one instance of each of the following kinds of objects 
	  * within a &lt;generalGlyph&gt; object: &lt;boundingBox&gt;, &lt;curve&gt;, &lt;listOfReferenceGlyphs&gt; 
	  * and &lt;listOfSubGlyphs&gt;. No other elements from the Layout namespace are permitted 
	  * on a &lt;generalGlyph&gt;. Reference: L3V1 Layout V1 Section 3.11 
	  */
 	 public static final int LAYOUT_20803 = 6020803; 

	 /**
	  * Error code 6020804:
	  * A &lt;generalGlyph&gt; object must have the required attribute 'layout:id' and may 
	  * have the optional attributes 'layout:metaidRef' or 'layout:reference'. No other 
	  * attributes from the Layout namespace are permitted on a &lt;generalGlyph&gt;. 
	  * Reference: L3V1 Layout V1 Section 3.11 
	  */
 	 public static final int LAYOUT_20804 = 6020804; 

	 /**
	  * Error code 6020805:
	  * The attribute 'layout:metaidRef' of a &lt;generalGlyph&gt; must be of the data type 
	  * 'IDREF'. Reference: L3V1 Layout V1 Section 3.7 
	  */
 	 public static final int LAYOUT_20805 = 6020805; 

	 /**
	  * Error code 6020806:
	  * The value of a 'layout:metaidRef' attribute of a &lt;generalGlyph&gt; must be of the 
	  * 'metaid' of an existing element in the &lt;model&gt;. Reference: L3V1 Layout V1 
	  * Section 3.7 
	  */
 	 public static final int LAYOUT_20806 = 6020806; 

	 /**
	  * Error code 6020807:
	  * The attribute 'layout:reference' of a &lt;generalGlyph&gt; must be the data type 
	  * 'SIdRef'. Reference: L3V1 Layout V1 Section 3.11 
	  */
 	 public static final int LAYOUT_20807 = 6020807; 

	 /**
	  * Error code 6020808:
	  * The value of the 'layout:reference' attribute of a &lt;generalGlyph&gt; must be of the 
	  * 'id' of an existing element in the &lt;model&gt;. Reference: L3V1 Layout V1 Section 
	  * 3.11 
	  */
 	 public static final int LAYOUT_20808 = 6020808; 

	 /**
	  * Error code 6020809:
	  * If both attributes 'layout:reference' and 'layout:metaidRef' are specified on a 
	  * &lt;generalGlyph&gt; they have to reference the same element in the &lt;model&gt;. 
	  * Reference: L3V1 Layout V1 Section 3.11 
	  */
 	 public static final int LAYOUT_20809 = 6020809; 

	 /**
	  * Error code 6020810:
	  * Apart from the general notes and annotation subobjects permitted on all SBML 
	  * objects, a &lt;listOfReferenceGlyphs&gt; container object may only contain 
	  * &lt;referenceGlyph&gt; objects. Reference: L3V1 Layout V1 Section 3.11 
	  */
 	 public static final int LAYOUT_20810 = 6020810; 

	 /**
	  * Error code 6020811:
	  * A &lt;listOfReferenceGlyphs&gt; object may have the optional attributes 'metaid' and 
	  * 'sboTerm' defined by SBML Level 3 Core. No other attributes from the SBML Level 
	  * 3 Core namespace or the Layout namespace are permitted on a 
	  * &lt;listOfReferenceGlyphs&gt; object. Reference: L3V1 Layout V1 Section 3.11 
	  */
 	 public static final int LAYOUT_20811 = 6020811; 

	 /**
	  * Error code 6020812:
	  */
 	 public static final int LAYOUT_20812 = 6020812; 

	 /**
	  * Error code 6020813:
	  * A &lt;listOfSubGlyphs&gt; object may have the optional attributes 'metaid' and 
	  * 'sboTerm' defined by SBML Level 3 Core. No other attributes from the SBML Level 
	  * 3 Core namespace or the Layout namespace are permitted on a &lt;listOfSubGlyphs&gt; 
	  * object. Reference: L3V1 Layout V1 Section 3.11 
	  */
 	 public static final int LAYOUT_20813 = 6020813; 

	 /**
	  * Error code 6020901:
	  * A &lt;textGlyph&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespace 
	  * are permitted on a &lt;textGlyph&gt;. Reference: L3V1 Core Section 3.2 
	  */
 	 public static final int LAYOUT_20901 = 6020901; 

	 /**
	  * Error code 6020902:
	  * A &lt;textGlyph&gt; object may have the optional SBML Level 3 Core attributes 'metaid' 
	  * and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are 
	  * permitted on a &lt;textGlyph&gt;. Reference: L3V1 Core Section 3.2 
	  */
 	 public static final int LAYOUT_20902 = 6020902; 

	 /**
	  * Error code 6020903:
	  * A &lt;textGlyph&gt; must contain exactly one &lt;boundingBox&gt; object. No other elements 
	  * from the Layout namespace are permitted on a &lt;textGlyph&gt;. Reference: L3V1 Layout 
	  * V1 Section 3.12 
	  */
 	 public static final int LAYOUT_20903 = 6020903; 

	 /**
	  * Error code 6020904:
	  * A &lt;textGlyph&gt; object must have the required attribute 'layout:id' and may have 
	  * the optional attributes 'layout:metaidRef', 'layout:graphicalObject', 
	  * 'layout:text' and 'layout:originOfText'. No other attributes from the Layout 
	  * namespace are permitted on a &lt;textGlyph&gt;. Reference: L3V1 Layout V1 Section 3.12 
	  */
 	 public static final int LAYOUT_20904 = 6020904; 

	 /**
	  * Error code 6020905:
	  * The attribute 'layout:metaidRef' of a &lt;textGlyph&gt; must be of the data type 
	  * 'IDREF'. Reference: L3V1 Layout V1 Section 3.7 
	  */
 	 public static final int LAYOUT_20905 = 6020905; 

	 /**
	  * Error code 6020906:
	  * The value of a 'layout:metaidRef' attribute of a &lt;textGlyph&gt; must be of the 
	  * 'metaid' of an existing element in the &lt;model&gt;. Reference: L3V1 Layout V1 
	  * Section 3.7 
	  */
 	 public static final int LAYOUT_20906 = 6020906; 

	 /**
	  * Error code 6020907:
	  * The attribute 'layout:originOfText' of a &lt;textGlyph&gt; must be the data type 
	  * 'SIdRef'. Reference: L3V1 Layout V1 Section 3.12 
	  */
 	 public static final int LAYOUT_20907 = 6020907; 

	 /**
	  * Error code 6020908:
	  * The value of the 'layout:originOfText' attribute of a &lt;textGlyph&gt; must be of the 
	  * 'id' of an existing element in the &lt;model&gt;. Reference: L3V1 Layout V1 Section 
	  * 3.12 
	  */
 	 public static final int LAYOUT_20908 = 6020908; 

	 /**
	  * Error code 6020909:
	  * If both attributes 'layout:originOfText' and 'layout:metaidRef' are specified on 
	  * a &lt;textGlyph&gt; they have to reference the same element in the &lt;model&gt;. Reference: 
	  * L3V1 Layout V1 Section 3.12 
	  */
 	 public static final int LAYOUT_20909 = 6020909; 

	 /**
	  * Error code 6020910:
	  * The attribute 'layout:graphicalObject' of a &lt;textGlyph&gt; must be the data type 
	  * 'SIdRef'. Reference: L3V1 Layout V1 Section 3.12 
	  */
 	 public static final int LAYOUT_20910 = 6020910; 

	 /**
	  * Error code 6020911:
	  * The value of the 'layout:graphicalObject' attribute of a &lt;textGlyph&gt; must be of 
	  * the 'id' of an existing &lt;graphicalObject&gt; (or derived) element in the &lt;layout&gt;. 
	  * Reference: L3V1 Layout V1 Section 3.12 
	  */
 	 public static final int LAYOUT_20911 = 6020911; 

	 /**
	  * Error code 6020912:
	  * The attribute 'layout:text' of a &lt;textGlyph&gt; must be the data type 'string'. 
	  * Reference: L3V1 Layout V1 Section 3.12 
	  */
 	 public static final int LAYOUT_20912 = 6020912; 

	 /**
	  * Error code 6021001:
	  * A &lt;speciesReferenceGlyph&gt; object may have the optional SBML Level 3 Core 
	  * subobjects for notes and annotations. No other elements from the SBML Level 3 
	  * Core namespace are permitted on a &lt;speciesReferenceGlyph&gt;. Reference: L3V1 Core 
	  * Section 3.2 
	  */
 	 public static final int LAYOUT_21001 = 6021001; 

	 /**
	  * Error code 6021002:
	  * A &lt;speciesReferenceGlyph&gt; object may have the optional SBML Level 3 Core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 
	  * Core namespace are permitted on a &lt;speciesReferenceGlyph&gt;. Reference: L3V1 Core 
	  * Section 3.2 
	  */
 	 public static final int LAYOUT_21002 = 6021002; 

	 /**
	  * Error code 6021003:
	  * A &lt;speciesReferenceGlyph&gt; may have at most one instance of a &lt;boundingBox&gt; and 
	  * &lt;curve&gt; object. No other elements from the Layout namespace are permitted on a 
	  * &lt;speciesReferenceGlyph&gt;. Reference: L3V1 Layout V1 Section 3.10.1 
	  */
 	 public static final int LAYOUT_21003 = 6021003; 

	 /**
	  * Error code 6021004:
	  * A &lt;speciesReferenceGlyph&gt; object must have the required attribute 'layout:id' 
	  * and may have the optional attributes 'layout:metaidRef', 
	  * 'layout:speciesReference' and 'layout:role'. No other attributes from the Layout 
	  * namespace are permitted on a &lt;speciesReferenceGlyph&gt;. Reference: L3V1 Layout V1 
	  * Section 3.10.1 
	  */
 	 public static final int LAYOUT_21004 = 6021004; 

	 /**
	  * Error code 6021005:
	  * The attribute 'layout:metaidRef' of a &lt;speciesReferenceGlyph&gt; must be of the 
	  * data type 'IDREF'. Reference: L3V1 Layout V1 Section 3.7 
	  */
 	 public static final int LAYOUT_21005 = 6021005; 

	 /**
	  * Error code 6021006:
	  * The value of a 'layout:metaidRef' attribute of a &lt;speciesReferenceGlyph&gt; must be 
	  * of the 'metaid' of an existing element in the &lt;model&gt;. Reference: L3V1 Layout V1 
	  * Section 3.7 
	  */
 	 public static final int LAYOUT_21006 = 6021006; 

	 /**
	  * Error code 6021007:
	  * The attribute 'layout:speciesReference' of a &lt;speciesReferenceGlyph&gt; must be the 
	  * data type 'SIdRef'. Reference: L3V1 Layout V1 Section 3.10.1 
	  */
 	 public static final int LAYOUT_21007 = 6021007; 

	 /**
	  * Error code 6021008:
	  * The value of the 'layout:speciesReference' attribute of a 
	  * &lt;speciesReferenceGlyph&gt; must be of the 'id' of an existing &lt;speciesReference&gt; in 
	  * the &lt;model&gt;. Reference: L3V1 Layout V1 Section 3.10.1 
	  */
 	 public static final int LAYOUT_21008 = 6021008; 

	 /**
	  * Error code 6021009:
	  * If both attributes 'layout:speciesReference' and 'layout:metaidRef' are 
	  * specified on a &lt;speciesReferenceGlyph&gt; they have to reference the same 
	  * &lt;speciesReference&gt; in the &lt;model&gt;. Reference: L3V1 Layout V1 Section 3.12 
	  */
 	 public static final int LAYOUT_21009 = 6021009; 

	 /**
	  * Error code 6021010:
	  * The attribute 'layout:speciesGlyph' of a &lt;speciesReferenceGlyph&gt; must be the 
	  * data type 'SIdRef'. Reference: L3V1 Layout V1 Section 3.10.1 
	  */
 	 public static final int LAYOUT_21010 = 6021010; 

	 /**
	  * Error code 6021011:
	  * The value of the 'layout:speciesGlyph' attribute of a &lt;speciesReferenceGlyph&gt; 
	  * must be of the 'id' of an existing &lt;speciesGlyph&gt; element in the &lt;layout&gt;. 
	  * Reference: L3V1 Layout V1 Section 3.10.1 
	  */
 	 public static final int LAYOUT_21011 = 6021011; 

	 /**
	  * Error code 6021012:
	  * The attribute 'layout:role' of a &lt;speciesReferenceGlyph&gt; must be of data type 
	  * 'SpeciesReferenceRole', i.e. it must have one of the following values: 
	  * substrate, product, sidesubstrate, sideproduct, modifier, activator, inhibitor 
	  * or undefined. Reference: L3V1 Layout V1 Section 3.10.1 
	  */
 	 public static final int LAYOUT_21012 = 6021012; 

	 /**
	  * Error code 6021101:
	  * A &lt;referenceGlyph&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespace 
	  * are permitted on a &lt;referenceGlyph&gt;. Reference: L3V1 Core Section 3.2 
	  */
 	 public static final int LAYOUT_21101 = 6021101; 

	 /**
	  * Error code 6021102:
	  * A &lt;referenceGlyph&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace 
	  * are permitted on a &lt;referenceGlyph&gt;. Reference: L3V1 Core Section 3.2 
	  */
 	 public static final int LAYOUT_21102 = 6021102; 

	 /**
	  * Error code 6021103:
	  * There may be at most one instance of a &lt;boundingBox&gt; and &lt;curve&gt; object on a 
	  * &lt;referenceGlyph&gt;. No other elements from the Layout namespace are permitted on a 
	  * &lt;referenceGlyph&gt;. Reference: L3V1 Layout V1 Section 3.11.1 
	  */
 	 public static final int LAYOUT_21103 = 6021103; 

	 /**
	  * Error code 6021104:
	  * A &lt;referenceGlyph&gt; object must have the required attributes 'layout:id' and 
	  * 'layout:glyph' and may have the optional attributes 'layout:metaidRef', 
	  * 'layout:reference' and 'layout:role'. No other attributes from the Layout 
	  * namespace are permitted on a &lt;referenceGlyph&gt;. Reference: L3V1 Layout V1 Section 
	  * 3.11.1 
	  */
 	 public static final int LAYOUT_21104 = 6021104; 

	 /**
	  * Error code 6021105:
	  * The attribute 'layout:metaidRef' of a &lt;referenceGlyph&gt; must be of the data type 
	  * 'IDREF'. Reference: L3V1 Layout V1 Section 3.7 
	  */
 	 public static final int LAYOUT_21105 = 6021105; 

	 /**
	  * Error code 6021106:
	  * The value of a 'layout:metaidRef' attribute of a &lt;referenceGlyph&gt; must be of the 
	  * 'metaid' of an existing element in the &lt;model&gt;. Reference: L3V1 Layout V1 
	  * Section 3.7 
	  */
 	 public static final int LAYOUT_21106 = 6021106; 

	 /**
	  * Error code 6021107:
	  * The attribute 'layout:reference' of a &lt;referenceGlyph&gt; must be the data type 
	  * 'SIdRef'. Reference: L3V1 Layout V1 Section 3.11.1 
	  */
 	 public static final int LAYOUT_21107 = 6021107; 

	 /**
	  * Error code 6021108:
	  * The value of the 'layout:reference' attribute of a &lt;referenceGlyph&gt; must be of 
	  * the 'id' of an existing element in the &lt;model&gt;. Reference: L3V1 Layout V1 
	  * Section 3.11.1 
	  */
 	 public static final int LAYOUT_21108 = 6021108; 

	 /**
	  * Error code 6021109:
	  * If both attributes 'layout:reference' and 'layout:metaidRef' are specified on a 
	  * &lt;referenceGlyph&gt; they have to reference the same element in the &lt;model&gt;. 
	  * Reference: L3V1 Layout V1 Section 3.12 
	  */
 	 public static final int LAYOUT_21109 = 6021109; 

	 /**
	  * Error code 6021110:
	  * The attribute 'layout:glyph' of a &lt;referenceGlyph&gt; must be the data type 
	  * 'SIdRef'. Reference: L3V1 Layout V1 Section 3.11.1 
	  */
 	 public static final int LAYOUT_21110 = 6021110; 

	 /**
	  * Error code 6021111:
	  * The value of the 'layout:glyph' attribute of a &lt;referenceGlyph&gt; must be of the 
	  * 'id' of an existing &lt;graphicalObject&gt; (or derived) element in the &lt;layout&gt;. 
	  * Reference: L3V1 Layout V1 Section 3.11.1 
	  */
 	 public static final int LAYOUT_21111 = 6021111; 

	 /**
	  * Error code 6021112:
	  * The attribute 'layout:role' of a &lt;referenceGlyph&gt; must be the data type 
	  * 'string'. Reference: L3V1 Layout V1 Section 3.11.1 
	  */
 	 public static final int LAYOUT_21112 = 6021112; 

	 /**
	  * Error code 6021201:
	  * A &lt;point&gt; object may have the optional SBML Level 3 Core subobjects for notes 
	  * and annotations. No other elements from the SBML Level 3 Core namespace are 
	  * permitted on a &lt;point&gt;. It should be noted that the 'point' object may occur as 
	  * a &lt;position&gt;, &lt;basePoint1&gt;, &lt;basePoint2&gt;, &lt;start&gt; or &lt;end&gt; element. Reference: 
	  * L3V1 Core Section 3.2 
	  */
 	 public static final int LAYOUT_21201 = 6021201; 

	 /**
	  * Error code 6021202:
	  * A &lt;point&gt; object may have the optional SBML Level 3 Core attributes 'metaid' and 
	  * 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are 
	  * permitted on a &lt;point&gt;. It should be noted that the 'point' object may occur as 
	  * a &lt;position&gt;, &lt;basePoint1&gt;, &lt;basePoint2&gt;, &lt;start&gt; or &lt;end&gt; element. Reference: 
	  * L3V1 Core Section 3.2 
	  */
 	 public static final int LAYOUT_21202 = 6021202; 

	 /**
	  * Error code 6021203:
	  * A &lt;point&gt; object must have the required attributes 'layout:x' and 'layout:y' and 
	  * may have the optional attributes 'layout:id', and 'layout:z'. No other 
	  * attributes from the Layout namespace are permitted on a &lt;point&gt;. It should be 
	  * noted that the 'point' object may occur as a &lt;position&gt;, &lt;basePoint1&gt;, 
	  * &lt;basePoint2&gt;, &lt;start&gt; or &lt;end&gt; element. Reference: L3V1 Layout V1 Section 3.4.1 
	  */
 	 public static final int LAYOUT_21203 = 6021203; 

	 /**
	  * Error code 6021204:
	  * The attributes 'layout:x', 'layout:y' and 'layout:z' of a &lt;point&gt; element must 
	  * be of the data type 'double'. It should be noted that the 'point' object may 
	  * occur as a &lt;position&gt;, &lt;basePoint1&gt;, &lt;basePoint2&gt;, &lt;start&gt; or &lt;end&gt; element. 
	  * Reference: L3V1 Layout V1 Section 3.4.1 
	  */
 	 public static final int LAYOUT_21204 = 6021204; 

	 /**
	  * Error code 6021301:
	  * A &lt;boundingBox&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespace 
	  * are permitted on a &lt;boundingBox&gt;. Reference: L3V1 Core Section 3.2 
	  */
 	 public static final int LAYOUT_21301 = 6021301; 

	 /**
	  * Error code 6021302:
	  * A &lt;boundingBox&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace 
	  * are permitted on a &lt;boundingBox&gt;. Reference: L3V1 Core Section 3.2 
	  */
 	 public static final int LAYOUT_21302 = 6021302; 

	 /**
	  * Error code 6021303:
	  * There must be exactly one instance of a &lt;point&gt; and a &lt;dimensions&gt; object on a 
	  * &lt;boundingBox&gt;. No other elements from the Layout namespace are permitted on a 
	  * &lt;boundingBox&gt;. It should be noted that the 'point' object will occur as a 
	  * &lt;position&gt; element. Reference: L3V1 Layout V1 Section 3.4.3 
	  */
 	 public static final int LAYOUT_21303 = 6021303; 

	 /**
	  * Error code 6021304:
	  * A &lt;boundingBox&gt; object may have the optional attributes 'layout:id'. No other 
	  * attributes from the Layout namespace are permitted on a &lt;boundingBox&gt;. 
	  * Reference: L3V1 Layout V1 Section 3.4.3 
	  */
 	 public static final int LAYOUT_21304 = 6021304; 

	 /**
	  * Error code 6021305:
	  * If the 'layout:z' attribute on a &lt;point&gt; element of a &lt;boundingBox&gt; is not 
	  * specified, the attribute 'layout:depth' must not be specified. It should be 
	  * noted that the 'point' object will occur as a &lt;position&gt; element. Reference: 
	  * L3V1 Layout V1 Section 3.7 
	  */
 	 public static final int LAYOUT_21305 = 6021305; 

	 /**
	  * Error code 6021401:
	  * A &lt;curve&gt; object may have the optional SBML Level 3 Core subobjects for notes 
	  * and annotations. No other elements from the SBML Level 3 Core namespace are 
	  * permitted on a &lt;curve&gt;. Reference: L3V1 Core Section 3.2 
	  */
 	 public static final int LAYOUT_21401 = 6021401; 

	 /**
	  * Error code 6021402:
	  * A &lt;curve&gt; object may have the optional SBML Level 3 Core attributes 'metaid' and 
	  * 'sboTerm'. No other attributes from the SBML Level 3 Core namespace are 
	  * permitted on a &lt;curve&gt;. Reference: L3V1 Core Section 3.2 
	  */
 	 public static final int LAYOUT_21402 = 6021402; 

	 /**
	  * Error code 6021403:
	  * There must be exactly one instance of a &lt;listOfCurveSegments&gt; object on a 
	  * &lt;curve&gt;. No other elements from the Layout namespace are permitted on a &lt;curve&gt;. 
	  * Reference: L3V1 Layout V1 Section 3.4.4 
	  */
 	 public static final int LAYOUT_21403 = 6021403; 

	 /**
	  * Error code 6021404:
	  * No attributes from the Layout namespace are permitted on a &lt;curve&gt;. Reference: 
	  * L3V1 Layout V1 Section 3.4.4 
	  */
 	 public static final int LAYOUT_21404 = 6021404; 

	 /**
	  * Error code 6021405:
	  * A &lt;listOfCurveSegments&gt; object may have the optional attributes 'metaid' and 
	  * 'sboTerm' defined by SBML Level 3 Core. No other attributes from the SBML Level 
	  * 3 Core namespace or the Layout namespace are permitted on a 
	  * &lt;listOfCurveSegments&gt; object. Reference: L3V1 Layout V1 Section 3.4.4 
	  */
 	 public static final int LAYOUT_21405 = 6021405; 

	 /**
	  * Error code 6021406:
	  * Apart from the general notes and annotation subobjects permitted on all SBML 
	  * objects, a &lt;listOfCurveSegments&gt; container object may only contain 
	  * &lt;referenceGlyph&gt; objects. Reference: L3V1 Layout V1 Section 3.4.4 
	  */
 	 public static final int LAYOUT_21406 = 6021406; 

	 /**
	  * Error code 6021407:
	  * A &lt;listOfCurveSegments&gt; container object may not be empty. Reference: L3V1 
	  * Layout V1 Section 3.4.4 
	  */
 	 public static final int LAYOUT_21407 = 6021407; 

	 /**
	  * Error code 6021501:
	  * A &lt;lineSegment&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespace 
	  * are permitted on a &lt;lineSegment&gt;. It should be noted that a 'lineSegment' occurs 
	  * as a &lt;curveSegment&gt; element with type 'LineSegment' or 'CubicBezier'. Reference: 
	  * L3V1 Core Section 3.2 
	  */
 	 public static final int LAYOUT_21501 = 6021501; 

	 /**
	  * Error code 6021502:
	  * A &lt;lineSegment&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace 
	  * are permitted on a &lt;lineSegment&gt;. It should be noted that a 'lineSegment' occurs 
	  * as a &lt;curveSegment&gt; element with type 'LineSegment' or 'CubicBezier'. Reference: 
	  * L3V1 Core Section 3.2 
	  */
 	 public static final int LAYOUT_21502 = 6021502; 

	 /**
	  * Error code 6021503:
	  * A &lt;lineSegment&gt; must specify two &lt;point&gt; elements 'start' and 'end'. No other 
	  * elements from the Layout namespace are permitted on a &lt;lineSegment&gt;. It should 
	  * be noted that a 'lineSegment' occurs as a &lt;curveSegment&gt; element with type 
	  * 'LineSegment' or 'CubicBezier'. Reference: L3V1 Layout V1 Section 3.4.5 
	  */
 	 public static final int LAYOUT_21503 = 6021503; 

	 /**
	  * Error code 6021504:
	  * No attributes from the Layout namespace are permitted on a &lt;lineSegment&gt;. It 
	  * should be noted that a 'lineSegment' occurs as a &lt;curveSegment&gt; element with 
	  * type 'LineSegment' or 'CubicBezier'. Reference: L3V1 Layout V1 Section 3.4.5 
	  */
 	 public static final int LAYOUT_21504 = 6021504; 

	 /**
	  * Error code 6021601:
	  * A &lt;cubicBezier&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespace 
	  * are permitted on a &lt;cubicBezier&gt;. Reference: L3V1 Core Section 3.2 
	  */
 	 public static final int LAYOUT_21601 = 6021601; 

	 /**
	  * Error code 6021602:
	  * A &lt;cubicBezier&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace 
	  * are permitted on a &lt;cubicBezier&gt;. Reference: L3V1 Core Section 3.2 
	  */
 	 public static final int LAYOUT_21602 = 6021602; 

	 /**
	  * Error code 6021603:
	  * A &lt;cubicBezier&gt; must specify four &lt;point&gt; elements 'start' 'basePoint1', 
	  * 'basePoint2' and 'end'. No other elements from the Layout namespace are 
	  * permitted on a &lt;cubicBezier&gt;. Reference: L3V1 Layout V1 Section 3.4.6 
	  */
 	 public static final int LAYOUT_21603 = 6021603; 

	 /**
	  * Error code 6021604:
	  * No attributes from the Layout namespace are permitted on a &lt;cubicBezier&gt;. 
	  * Reference: L3V1 Layout V1 Section 3.4.6 
	  */
 	 public static final int LAYOUT_21604 = 6021604; 

	 /**
	  * Error code 6021701:
	  * A &lt;dimensions&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespace 
	  * are permitted on a &lt;dimensions&gt;. Reference: L3V1 Core Section 3.2 
	  */
 	 public static final int LAYOUT_21701 = 6021701; 

	 /**
	  * Error code 6021702:
	  * A &lt;dimensions&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core namespace 
	  * are permitted on a &lt;dimensions&gt;. Reference: L3V1 Core Section 3.2 
	  */
 	 public static final int LAYOUT_21702 = 6021702; 

	 /**
	  * Error code 6021703:
	  * A &lt;dimensions&gt; object must have the required attributes 'layout:width' and 
	  * 'layout:height' and may have the optional attributes 'layout:id', and 
	  * 'layout:depth'. No other attributes from the Layout namespace are permitted on a 
	  * &lt;dimensions&gt;. Reference: L3V1 Layout V1 Section 3.4.2 
	  */
 	 public static final int LAYOUT_21703 = 6021703; 

	 /**
	  * Error code 6021704:
	  * The attributes 'layout:width', 'layout:height' and 'layout:depth' of a 
	  * &lt;dimensions&gt; element must be of the data type 'double'. Reference: L3V1 Layout 
	  * V1 Section 3.4.2 
	  */
 	 public static final int LAYOUT_21704 = 6021704; 

	 /**
	  * Error code 7010101:
	  * To conform to Version 1 of the Multi package specification for SBML Level 3, an 
	  * SBML document must declare the use of the following XML Namespace: 
	  * 'http://www.sbml.org/sbml/level3/version1/multi/version1' Reference: L3V1 Multi 
	  * V1.1 Section 3.1 
	  */
 	 public static final int MULTI_10101 = 7010101; 

	 /**
	  * Error code 7010102:
	  * Wherever they appear in an SBML document, elements and attributes from the Multi 
	  * package must be declared either implicitly or explicitly to be in the XML 
	  * namespace 'http://www.sbml.org/sbml/level3/version1/comp/version1' Reference: 
	  * L3V1 Multi V1.1 Section 3.1 
	  */
 	 public static final int MULTI_10102 = 7010102; 

	 /**
	  * Error code 7010201:
	  * A 'ci' element in a Math object may have the optional attributes 
	  * 'multi:speciesReference' and 'multi:representationType'. No other attributes 
	  * from the Multi namespace are permitted on a 'ci' element. Reference: L3V1 Multi 
	  * V1.1 Section 3.26 
	  */
 	 public static final int MULTI_10201 = 7010201; 

	 /**
	  * Error code 7010202:
	  * The value of the 'multi:speciesReference' attribute on a given 'ci' element must 
	  * be the identifier of a SpeciesReference object within the same reaction. 
	  * Reference: L3V1 Multi V1.1 Section 3.26.1 
	  */
 	 public static final int MULTI_10202 = 7010202; 

	 /**
	  * Error code 7010203:
	  * The value of the 'multi:representationType' attribute on a given 'ci' element 
	  * must conform to the syntax of the Multi data type 'RepresentationType'. 
	  * Reference: L3V1 Multi V1.1 Section 3.26.2 
	  */
 	 public static final int MULTI_10203 = 7010203; 

	 /**
	  * Error code 7010301:
	  * (Extends validation rule #10301 in the SBML Level 3 Version 1 Core 
	  * specification.) Within a &lt;model&gt; object, the values of the attributes id and 
	  * multi:id on every instance of the following classes of objects must be unique 
	  * across the set of all id and multi:id attribute values of all such objects in a 
	  * model: the model itself, plus all contained &lt;functionDefinition&gt;, 
	  * &lt;compartment&gt;,&lt;species&gt;, &lt;reaction&gt;, &lt;speciesReference&gt;, 
	  * &lt;modifierSpeciesReference&gt;, &lt;event&gt;, and &lt;parameter&gt; objects, plus the 
	  * &lt;SpeciesType&gt; and &lt;PossibleSpeciesFeatureValue&gt; objects defined by the Multi 
	  * package, and any objects defined by any other package with 'package:id' 
	  * attributes defined as falling in the 'SId' namespace. Reference: L3V1 Multi V1.1 
	  * Section 3.27 
	  */
 	 public static final int MULTI_10301 = 7010301; 

	 /**
	  * Error code 7010302:
	  * The value of a multi:id attribute must always conform to the syntax of the SBML 
	  * data type SId. Reference: L3V1 Core Section 3.1.7 
	  */
 	 public static final int MULTI_10302 = 7010302; 

	 /**
	  * Error code 7010303:
	  * The value of a 'multi:name' attribute must always conform to the syntax of type 
	  * string. Reference: L3V1 Core Section 3.1.1 
	  */
 	 public static final int MULTI_10303 = 7010303; 

	 /**
	  * Error code 7010304:
	  * The value of a multi:id attribute on SpeciesTypeInstance objects must be unique 
	  * across the set of all multi:id attribute values of all the SpeciesTypeInstance 
	  * objects under the direct parent SpeciesType object in which it is located. 
	  * Reference: L3V1 Multi V1.1 Section 3.11.1 and Section 3.27 
	  */
 	 public static final int MULTI_10304 = 7010304; 

	 /**
	  * Error code 7010305:
	  * The value of a multi:id attribute on SpeciesTypeComponentIndex objects must be 
	  * unique across the set of all multi:id attribute values of all the 
	  * SpeciesTypeComponentIndex objects under the direct parent SpeciesType object in 
	  * which it is located. Reference: L3V1 Multi V1.1 Section 3.12.1 and Section 3.27 
	  */
 	 public static final int MULTI_10305 = 7010305; 

	 /**
	  * Error code 7010306:
	  * The value of a multi:id attribute on InSpeciesTypeBond objects must be unique 
	  * across the set of all multi:id attribute values of all the InSpeciesTypeBond 
	  * objects under the direct parent SpeciesType object in which it is it is located. 
	  * Reference: L3V1 Multi V1.1 Section 3.13.1 and Section 3.27 
	  */
 	 public static final int MULTI_10306 = 7010306; 

	 /**
	  * Error code 7010307:
	  * The value of a multi:id attribute on SpeciesFeatureType objects must be unique 
	  * across the set of all multi:id attribute values of all the SpeciesFeature 
	  * objects under the direct parent SpeciesType object in which it is located. 
	  * Reference: L3V1 Multi V1.1 Section 3.9.1 and Section 3.27 
	  */
 	 public static final int MULTI_10307 = 7010307; 

	 /**
	  * Error code 7010308:
	  * The value of a multi:id attribute on SubListOfSpeciesFeatures objects must be 
	  * unique across the set ofall id and multi:id attribute values of all objects in 
	  * the Species object in which it is located. Reference: L3V1 Multi V1.1 Section 
	  * 3.17.1 and Section 3.27 
	  */
 	 public static final int MULTI_10308 = 7010308; 

	 /**
	  * Error code 7010309:
	  * The value of a multi:id attribute on SpeciesFeature objects must be unique 
	  * across the set ofall id and multi:id attribute values of all objects in the 
	  * Species object in which it is located. Reference: L3V1 Multi V1.1 Section 3.18.1 
	  * and Section 3.27 
	  */
 	 public static final int MULTI_10309 = 7010309; 

	 /**
	  * Error code 7010310:
	  * The value of a multi:id attribute on CompartmentReference objects must be unique 
	  * across the set of all id and multi:id attribute values of all objects in the 
	  * Compartment object in which it is located. Reference: L3V1 Multi V1.1 Section 
	  * 3.6.1 and Section 3.27 
	  */
 	 public static final int MULTI_10310 = 7010310; 

	 /**
	  * Error code 7010311:
	  * The value of a multi:compartment attribute on SpeciesType objects must conform 
	  * to the syntax of the SBML data type SIdRef. Reference: L3V1 Multi V1.1 Section 
	  * 3.8.2 
	  */
 	 public static final int MULTI_10311 = 7010311; 

	 /**
	  * Error code 7010312:
	  * The value of a multi:numericValue attribute on PossibleSpeciesFeatureValue 
	  * objects must conform to the syntax of the SBML data type SIdRef. Reference: L3V1 
	  * Multi V1.1 Section 3.10.2 
	  */
 	 public static final int MULTI_10312 = 7010312; 

	 /**
	  * Error code 7010313:
	  * The value of a multi:speciesType attribute on SpeciesTypeInstance objects must 
	  * conform to the syntax of the SBML data type SIdRef. Reference: L3V1 Multi V1.1 
	  * Section 3.11.2 
	  */
 	 public static final int MULTI_10313 = 7010313; 

	 /**
	  * Error code 7010314:
	  * The value of a multi:compartmentReference attribute on SpeciesTypeInstance 
	  * objects must conform to the syntax of the SBML data type SIdRef. Reference: L3V1 
	  * Multi V1.1 Section 3.11.3 
	  */
 	 public static final int MULTI_10314 = 7010314; 

	 /**
	  * Error code 7010315:
	  * The value of a multi:component attribute on SpeciesTypeComponentIndex objects 
	  * must conform to the syntax of the SBML data type SIdRef. Reference: L3V1 Multi 
	  * V1.1 Section 3.12.2 
	  */
 	 public static final int MULTI_10315 = 7010315; 

	 /**
	  * Error code 7010316:
	  * The value of a multi:identifyingParent attribute on SpeciesTypeComponentIndex 
	  * objects must conform to the syntax of the SBML data type SIdRef. Reference: L3V1 
	  * Multi V1.1 Section 3.12.3 
	  */
 	 public static final int MULTI_10316 = 7010316; 

	 /**
	  * Error code 7010317:
	  * The value of a multi:bindingSite1 attribute on InSpeciesTypeBond objects must 
	  * conform to the syntax of the SBML data type SIdRef. Reference: L3V1 Multi V1.1 
	  * Section 3.13.2 
	  */
 	 public static final int MULTI_10317 = 7010317; 

	 /**
	  * Error code 7010318:
	  * The value of a multi:bindingSite2 attribute on InSpeciesTypeBond objects must 
	  * conform to the syntax of the SBML data type SIdRef. Reference: L3V1 Multi V1.1 
	  * Section 3.13.2 
	  */
 	 public static final int MULTI_10318 = 7010318; 

	 /**
	  * Error code 7010319:
	  * The value of a multi:speciesType attribute on extended Species objects must 
	  * conform to the syntax of the SBML data type SIdRef. Reference: L3V1 Multi V1.1 
	  * Section 3.15.1 
	  */
 	 public static final int MULTI_10319 = 7010319; 

	 /**
	  * Error code 7010320:
	  * The value of a multi:component attribute on OutwardBindingSite objects must 
	  * conform to the syntax of the SBML data type SIdRef. Reference: L3V1 Multi V1.1 
	  * Section 3.16.3 
	  */
 	 public static final int MULTI_10320 = 7010320; 

	 /**
	  * Error code 7010321:
	  * The value of a multi:speciesFeatureType attribute on SpeciesFeature objects must 
	  * conform to the syntax of the SBML data type SIdRef. Reference: L3V1 Multi V1.1 
	  * Section 3.18.2 
	  */
 	 public static final int MULTI_10321 = 7010321; 

	 /**
	  * Error code 7010322:
	  * The value of a multi:component attribute on SpeciesFeature objects must conform 
	  * to the syntax of the SBML data type SIdRef. Reference: L3V1 Multi V1.1 Section 
	  * 3.18.4 
	  */
 	 public static final int MULTI_10322 = 7010322; 

	 /**
	  * Error code 7010323:
	  * The value of a multi:value attribute on SpeciesFeatureValue objects must conform 
	  * to the syntax of the SBML data type SIdRef. Reference: L3V1 Multi V1.1 Section 
	  * 3.18.6 
	  */
 	 public static final int MULTI_10323 = 7010323; 

	 /**
	  * Error code 7010324:
	  * The value of a multi:compartmentReference attribute on extended 
	  * SimpleSpeciesReference objects must conform to the syntax of the SBML data type 
	  * SIdRef. Reference: L3V1 Multi V1.1 Section 3.22 
	  */
 	 public static final int MULTI_10324 = 7010324; 

	 /**
	  * Error code 7010325:
	  * The value of a multi:reactant attribute on SpeciesTypeComponentMapInProduct 
	  * objects must conform to the syntax of the SBML data type SIdRef. Reference: L3V1 
	  * Multi V1.1 Section 3.24.2 
	  */
 	 public static final int MULTI_10325 = 7010325; 

	 /**
	  * Error code 7010326:
	  * The value of a multi:reactantComponent attribute on 
	  * SpeciesTypeComponentMapInProduct objects must conform to the syntax of the SBML 
	  * data type SIdRef. Reference: L3V1 Multi V1.1 Section 3.24.3 
	  */
 	 public static final int MULTI_10326 = 7010326; 

	 /**
	  * Error code 7010327:
	  * The value of a multi:productComponent attribute on 
	  * SpeciesTypeComponentMapInProduct objects must conform to the syntax of the SBML 
	  * data type SIdRef. Reference: L3V1 Multi V1.1 Section 3.24.4 
	  */
 	 public static final int MULTI_10327 = 7010327; 

	 /**
	  * Error code 7010328:
	  * The value of a multi:compartmentType attribute on extended Compartment objects 
	  * must conform to the syntax of the SBML data type SIdRef. Reference: L3V1 Multi 
	  * V1.1 Section 3.5.2 
	  */
 	 public static final int MULTI_10328 = 7010328; 

	 /**
	  * Error code 7010329:
	  * The value of a multi:compartment attribute on CompartmentReference objects must 
	  * conform to the syntax of the SBML data type SIdRef. Reference: L3V1 Multi V1.1 
	  * Section 3.6.2 
	  */
 	 public static final int MULTI_10329 = 7010329; 

	 /**
	  * Error code 7020101:
	  * In all SBML documents using the Multi package, the SBML object must include a 
	  * value for the 'multi:required' attribute. Reference: L3V1 Core Section 3.1 
	  */
 	 public static final int MULTI_20101 = 7020101; 

	 /**
	  * Error code 7020102:
	  * The value of attribute 'multi:required' on the SBML object must be of the data 
	  * type Boolean. Reference: L3V1 Core Section 3.1 
	  */
 	 public static final int MULTI_20102 = 7020102; 

	 /**
	  * Error code 7020103:
	  * The value of attribute 'multi:required' on the SBML object must be set to 
	  * 'true'. Reference: L3V1 Multi V1.1 Section 3.1 
	  */
 	 public static final int MULTI_20103 = 7020103; 

	 /**
	  * Error code 7020201:
	  * There may be at most one instance of ListOfSpeciesTypes within an extended Model 
	  * object that uses the SBML Level 3 Multi package. Reference: L3V1 Multi V1.1 
	  * Section 3.4 
	  */
 	 public static final int MULTI_20201 = 7020201; 

	 /**
	  * Error code 7020202:
	  * A ListOfSpeciesTypes object within an ExModel object is optional, but if 
	  * present, must not be empty. Reference: L3V1 Multi V1.1 Section 3.4 
	  */
 	 public static final int MULTI_20202 = 7020202; 

	 /**
	  * Error code 7020203:
	  * A ListOfSpeciesTypes object may have the optional SBML core attributes 'metaid' 
	  * and 'sboTerm'. No other attributes from the SBML Level~3 Core namespace or the 
	  * Multi namespace are permitted on a ListOfSpeciesTypes object. Reference: L3V1 
	  * Multi V1.1 Section 3.4.1 
	  */
 	 public static final int MULTI_20203 = 7020203; 

	 /**
	  * Error code 7020204:
	  * Apart from the general 'notes' and 'annotation' subobjects permitted on all SBML 
	  * objects, a ListOfSpeciesTypes container object may only contain SpeciesType 
	  * objects. Reference: L3V1 Multi V1.1 Section 3.4.1 
	  */
 	 public static final int MULTI_20204 = 7020204; 

	 /**
	  * Error code 7020301:
	  * An extended Compartment object must have the required attribute 'multi:isType', 
	  * and may also have the optional attribute 'multi:compartmentType'. No other 
	  * attributes from the Multi namespace are permitted on an extended Compartment 
	  * object. Reference: L3V1 Multi V1.1 Section 3.5 
	  */
 	 public static final int MULTI_20301 = 7020301; 

	 /**
	  * Error code 7020302:
	  * The value of a 'multi:isType' attribute on an extended Compartment object must 
	  * always confirm to the syntax of the SBML data type 'boolean'. Reference: L3V1 
	  * Multi V1.1 Section 3.5.1 
	  */
 	 public static final int MULTI_20302 = 7020302; 

	 /**
	  * Error code 7020303:
	  * Extended Compartment: 'isType' attribute is required. Reference: L3V1 Multi V1.1 
	  * Section 3.5.1 
	  */
 	 public static final int MULTI_20303 = 7020303; 

	 /**
	  * Error code 7020304:
	  * The value of the 'multi:isType' attribute of the Compartment object referenced 
	  * by a CompartmentReference object must be the same as that of the 'multi:isType' 
	  * attribute of the parent Compartment object of the ListOfCompartmentReferences 
	  * object which contains the CompartmentReference object. Reference: L3V1 Multi 
	  * V1.1 Section 3.7 
	  */
 	 public static final int MULTI_20304 = 7020304; 

	 /**
	  * Error code 7020305:
	  * The 'multi:compartmentType' attribute on a Compartment object must not be 
	  * defined if the value of the 'multi:isType' is 'true'. Reference: L3V1 Multi V1.1 
	  * Section 3.5.2 
	  */
 	 public static final int MULTI_20305 = 7020305; 

	 /**
	  * Error code 7020306:
	  * There may be at most one ListOfCompartmentReferences container object within a 
	  * Compartment object. Reference: L3V1 Multi V1.1 Section 3.5.3 
	  */
 	 public static final int MULTI_20306 = 7020306; 

	 /**
	  * Error code 7020307:
	  * A ListOfCompartmentReferences object within a Compartment object is optional, 
	  * but if present, must not be empty. Reference: L3V1 Multi V1.1 Section 3.5.3 
	  */
 	 public static final int MULTI_20307 = 7020307; 

	 /**
	  * Error code 7020308:
	  * A ListOfCompartmentReferences object may have the optional SBML core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level~3 Core namespace 
	  * or the Multi namespace are permitted on a ListOfCompartmentReferences object. 
	  * Reference: L3V1 Multi V1.1 Section 3.5.3 
	  */
 	 public static final int MULTI_20308 = 7020308; 

	 /**
	  * Error code 7020309:
	  * Apart from the general 'notes' and 'annotation' subobjects permitted on all SBML 
	  * objects, a ListOfCompartmentReferences container object may only contain 
	  * CompartmentReference objects. Reference: L3V1 Multi V1.1 Section 3.5.3 
	  */
 	 public static final int MULTI_20309 = 7020309; 

	 /**
	  * Error code 7020401:
	  * A SpeciesType object may have the optional SBML Level~3 Core attributes 'metaid' 
	  * and 'sboTerm'. No other attributes from the SBML Level~3 Core namespace are 
	  * permitted on a SpeciesType object. Reference: L3V1 Multi V1.1 Section 3.8 
	  */
 	 public static final int MULTI_20401 = 7020401; 

	 /**
	  * Error code 7020402:
	  * A SpeciesType object may have the optional SBML Level~3 Core subobjects for 
	  * 'notes' and 'annotation'. No other elements from the SBML Level~3 Core namespace 
	  * are permitted on a SpeciesType object. Reference: L3V1 Multi V1.1 Section 3.8 
	  */
 	 public static final int MULTI_20402 = 7020402; 

	 /**
	  * Error code 7020403:
	  * A SpeciesType object must have the required attribute 'multi:id', and may have 
	  * the optional attributes 'multi:name' and 'multi:compartment'. No other 
	  * attributes from the Multi namespace are permitted on a SpeciesType object. 
	  * Reference: L3V1 Multi V1.1 Section 3.8 
	  */
 	 public static final int MULTI_20403 = 7020403; 

	 /**
	  * Error code 7020404:
	  * The value of the 'multi:compartment' attribute, if set on a given SpeciesType 
	  * object, must be the value of an 'id' attribute on an existing Compartment object 
	  * in the 'SId' namespace of the parent Model object. Reference: L3V1 Multi V1.1 
	  * Section 3.8.2 
	  */
 	 public static final int MULTI_20404 = 7020404; 

	 /**
	  * Error code 7020405:
	  * The various 'ListOf' subobjects within a SpeciesType object are optional, but if 
	  * present, these container objects must not be empty. Specifically, if any of the 
	  * following classes of objects are present on a SpeciesType object, it must not be 
	  * empty: ListOfSpeciesFeatureTypes, ListOfSpeciesTypeInstances, 
	  * ListOfSpeciesTypeComponentIndexes and ListOfInSpeciesTypeBonds. Reference: L3V1 
	  * Multi V1.1 Section 3.8 
	  */
 	 public static final int MULTI_20405 = 7020405; 

	 /**
	  * Error code 7020406:
	  * There may be at most one ListOfSpeciesFeatureTypes container object within a 
	  * SpeciesType object. Reference: L3V1 Multi V1.1 Section 3.8 
	  */
 	 public static final int MULTI_20406 = 7020406; 

	 /**
	  * Error code 7020407:
	  * Apart from the general 'notes' and 'annotation' subobjects permitted on all SBML 
	  * objects, a ListOfSpeciesFeatureTypes container object may only contain 
	  * SpeciesFeatureType objects. Reference: L3V1 Multi V1.1 Section 3.8.3 
	  */
 	 public static final int MULTI_20407 = 7020407; 

	 /**
	  * Error code 7020408:
	  * A ListOfSpeciesFeatureTypes object may have the optional SBML core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level~3 Core namespace 
	  * or the Multi namespace are permitted on a ListOfSpeciesFeatureTypes object. 
	  * Reference: L3V1 Multi V1.1 Section 3.8.3 
	  */
 	 public static final int MULTI_20408 = 7020408; 

	 /**
	  * Error code 7020409:
	  * There may be at most one ListOfSpeciesTypeInstances container object within a 
	  * SpeciesType object. Reference: L3V1 Multi V1.1 Section 3.8 
	  */
 	 public static final int MULTI_20409 = 7020409; 

	 /**
	  * Error code 7020410:
	  * Apart from the general 'notes' and 'annotation' subobjects permitted on all SBML 
	  * objects, a ListOfSpeciesTypeInstances container object may only contain 
	  * SpeciesTypeInstance objects. Reference: L3V1 Multi V1.1 Section 3.8.4 
	  */
 	 public static final int MULTI_20410 = 7020410; 

	 /**
	  * Error code 7020411:
	  * A ListOfSpeciesTypeInstances object may have the optional SBML core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level~3 Core namespace 
	  * or the Multi namespace are permitted on a ListOfSpeciesTypeInstances. Reference: 
	  * L3V1 Multi V1.1 Section 3.8.4 
	  */
 	 public static final int MULTI_20411 = 7020411; 

	 /**
	  * Error code 7020412:
	  * There may be at most one ListOfSpeciesTypeComponentIndexes container object 
	  * within a SpeciesType object. Reference: L3V1 Multi V1.1 Section 3.8 
	  */
 	 public static final int MULTI_20412 = 7020412; 

	 /**
	  * Error code 7020413:
	  * Apart from the general 'notes' and 'annotation' subobjects permitted on all SBML 
	  * objects, a ListOfSpeciesTypeComponentIndexes container object may only contain 
	  * SpeciesTypeComponentIndex objects. Reference: L3V1 Multi V1.1 Section 3.8.6 
	  */
 	 public static final int MULTI_20413 = 7020413; 

	 /**
	  * Error code 7020414:
	  * A ListOfSpeciesTypeComponentIndexes object may have the optional SBML core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level~3 
	  * Core namespace or the Multi namespace are permitted on a 
	  * ListOfSpeciesTypeComponentIndexes object. Reference: L3V1 Multi V1.1 Section 
	  * 3.8.6 
	  */
 	 public static final int MULTI_20414 = 7020414; 

	 /**
	  * Error code 7020415:
	  * There may be at most one ListOfInSpeciesTypeBonds container object within a 
	  * SpeciesType object. Reference: L3V1 Multi V1.1 Section 3.8 
	  */
 	 public static final int MULTI_20415 = 7020415; 

	 /**
	  * Error code 7020416:
	  * Apart from the general 'notes' and 'annotation' subobjects permitted on all SBML 
	  * objects, a ListOfInSpeciesTypeBonds container object may only contain 
	  * InSpeciesTypeBond objects. Reference: L3V1 Multi V1.1 Section 3.8.5 
	  */
 	 public static final int MULTI_20416 = 7020416; 

	 /**
	  * Error code 7020417:
	  * A ListOfInSpeciesTypeBonds object may have the optional SBML core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level~3 Core namespace 
	  * or the Multi namespace are permitted on a ListOfInSpeciesTypeBonds object. 
	  * Reference: L3V1 Multi V1.1 Section 3.8.5 
	  */
 	 public static final int MULTI_20417 = 7020417; 

	 /**
	  * Error code 7020501:
	  * A BindingSiteSpeciesType object is not permitted to have any 
	  * ListOfSpeciesTypeInstances subobject. Reference: L3V1 Multi V1.1 Section 3.8.7 
	  */
 	 public static final int MULTI_20501 = 7020501; 

	 /**
	  * Error code 7020601:
	  * A SpeciesFeatureType object may have the optional SBML Level~3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level~3 Core namespace 
	  * are permitted on a SpeciesFeatureType object. Reference: L3V1 Multi V1.1 Section 
	  * 3.9 
	  */
 	 public static final int MULTI_20601 = 7020601; 

	 /**
	  * Error code 7020602:
	  * A SpeciesFeatureType object may have the optional SBML Level~3 Core subobjects 
	  * for 'notes' and 'annotation'. No other elements from the SBML Level~3 Core 
	  * namespace are permitted on a SpeciesFeatureType object. Reference: L3V1 Multi 
	  * V1.1 Section 3.9 
	  */
 	 public static final int MULTI_20602 = 7020602; 

	 /**
	  * Error code 7020603:
	  * A SpeciesFeatureType object must have the required attributes 'multi:id' and 
	  * 'multi:occur', and may have the optional attribute 'multi:name'. No other 
	  * attributes from the Multi namespace are permitted on a SpeciesFeatureType 
	  * object. Reference: L3V1 Multi V1.1 Section 3.9 
	  */
 	 public static final int MULTI_20603 = 7020603; 

	 /**
	  * Error code 7020604:
	  * The value of the 'multi:occur' attribute on a given SpeciesFeatureType object 
	  * must conform to the syntax of the SBML data type 'positiveInteger'. Reference: 
	  * L3V1 Multi V1.1 Section 3.9.2 
	  */
 	 public static final int MULTI_20604 = 7020604; 

	 /**
	  * Error code 7020605:
	  * One ListOfPossibleSpeciesFeatureValues subobject in a SpeciesFeatureType object 
	  * is required. Reference: L3V1 Multi V1.1 Section 3.9.3 
	  */
 	 public static final int MULTI_20605 = 7020605; 

	 /**
	  * Error code 7020606:
	  * A ListOfPossibleSpeciesFeatureValues object may have the optional SBML core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level~3 
	  * Core namespace or the Multi namespace are permitted on a 
	  * ListOfPossibleSpeciesFeatureValues object. Reference: L3V1 Multi V1.1 Section 
	  * 3.9.3 
	  */
 	 public static final int MULTI_20606 = 7020606; 

	 /**
	  * Error code 7020607:
	  * Apart from the general 'notes' and 'annotation' subobjects permitted on all SBML 
	  * objects, a ListOfPossibleSpeciesFeatureValues container object may only contain 
	  * PossibleSpeciesFeatureValue objects. Reference: L3V1 Multi V1.1 Section 3.9.3 
	  */
 	 public static final int MULTI_20607 = 7020607; 

	 /**
	  * Error code 7020608:
	  * A ListOfPossibleSpeciesFeatureValues object must not be empty. Reference: L3V1 
	  * Multi V1.1 Section 3.9.3 
	  */
 	 public static final int MULTI_20608 = 7020608; 

	 /**
	  * Error code 7020701:
	  * A PossibleSpeciesFeatureValue object may have the optional SBML Level~3 Core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level~3 
	  * Core namespace are permitted on a PossibleSpeciesFeatureValue object. Reference: 
	  * L3V1 Multi V1.1 Section 3.10 
	  */
 	 public static final int MULTI_20701 = 7020701; 

	 /**
	  * Error code 7020702:
	  * A PossibleSpeciesFeatureValue object may have the optional SBML Level~3 Core 
	  * subobjects for 'notes' and 'annotation'. No other elements from the SBML Level~3 
	  * Core namespace are permitted on a PossibleSpeciesFeatureValue object. Reference: 
	  * L3V1 Multi V1.1 Section 3.10 
	  */
 	 public static final int MULTI_20702 = 7020702; 

	 /**
	  * Error code 7020703:
	  * A PossibleSpeciesFeatureValue object must have the required attribute 
	  * 'multi:id', and may have the optional attributes 'multi:name' and 
	  * 'multi:numericValue'. No other attributes from the Multi namespace are permitted 
	  * on a PossibleSpeciesFeatureValue object. Reference: L3V1 Multi V1.1 Section 3.10 
	  */
 	 public static final int MULTI_20703 = 7020703; 

	 /**
	  * Error code 7020704:
	  * The value of the 'multi:numericValue' attribute on a given 
	  * PossibleSpeciesFeatureValue object must be the identifier of a Parameter object 
	  * defined in the same Model object. Reference: L3V1 Multi V1.1 Section 3.10.2 
	  */
 	 public static final int MULTI_20704 = 7020704; 

	 /**
	  * Error code 7020801:
	  * A SpeciesTypeInstance object may have the optional SBML Level~3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level~3 Core namespace 
	  * are permitted on a SpeciesTypeInstance object. Reference: L3V1 Multi V1.1 
	  * Section 3.11 
	  */
 	 public static final int MULTI_20801 = 7020801; 

	 /**
	  * Error code 7020802:
	  * A SpeciesTypeInstance object may have the optional SBML Level~3 Core subobjects 
	  * for 'notes' and 'annotation'. No other elements from the SBML Level~3 Core 
	  * namespace are permitted on a SpeciesTypeInstance object. Reference: L3V1 Multi 
	  * V1.1 Section 3.11 
	  */
 	 public static final int MULTI_20802 = 7020802; 

	 /**
	  * Error code 7020803:
	  * A SpeciesTypeInstance object must have the required attributes 'multi:id', 
	  * 'multi:speciesType' and 'multi:occur', and may have the optional attributes 
	  * 'multi:name' and 'mulit:compartmentReference'. No other attributes from the 
	  * Multi namespace are permitted on a SpeciesTypeInstance object. Reference: L3V1 
	  * Multi V1.1 Section 3.11 
	  */
 	 public static final int MULTI_20803 = 7020803; 

	 /**
	  * Error code 7020805:
	  * The value of the 'multi:speciesType' attribute on a given SpeciesTypeInstance 
	  * object must be the identifier of a SpeciesType object defined in the same Model 
	  * object. Reference: L3V1 Multi V1.1 Section 3.11.2 
	  */
 	 public static final int MULTI_20805 = 7020805; 

	 /**
	  * Error code 7020806:
	  * The value of the 'multi:compartmentReference' attribute, if present on a given 
	  * SpeciesTypeInstance object, must be the identifier of a CompartmentReference 
	  * object defined in the same Model object. Reference: L3V1 Multi V1.1 Section 
	  * 3.11.3 
	  */
 	 public static final int MULTI_20806 = 7020806; 

	 /**
	  * Error code 7020901:
	  * A SpeciesTypeComponentIndex object may have the optional SBML Level~3 Core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level~3 
	  * Core namespace are permitted on a SpeciesTypeComponentIndex object. Reference: 
	  * L3V1 Multi V1.1 Section 3.12 
	  */
 	 public static final int MULTI_20901 = 7020901; 

	 /**
	  * Error code 7020902:
	  * A SpeciesTypeComponentIndex object may have the optional SBML Level~3 Core 
	  * subobjects for 'notes' and 'annotation'. No other elements from the SBML Level~3 
	  * Core namespace are permitted on a SpeciesTypeComponentIndex object. Reference: 
	  * L3V1 Multi V1.1 Section 3.12 
	  */
 	 public static final int MULTI_20902 = 7020902; 

	 /**
	  * Error code 7020903:
	  * A SpeciesTypeComponentIndex object must have the required attributes 'multi:id' 
	  * and 'multi:compartment' , and may have the optional attribute 
	  * 'mulit:identifyingParent'. No other attributes from the Multi namespace are 
	  * permitted on a SpeciesTypeComponentIndex object. Reference: L3V1 Multi V1.1 
	  * Section 3.12 
	  */
 	 public static final int MULTI_20903 = 7020903; 

	 /**
	  * Error code 7020904:
	  * The value of the 'mulit:component' attribute on a given 
	  * SpeciesTypeComponentIndex object must be the identifier of a SpeciesTypeInstance 
	  * object, or a SpeciesTypeComponentIndex object under the SpeciesType object that 
	  * this SpeciesTypeComponentIndex object belongs to, or the SpeciesType object 
	  * itself. Reference: L3V1 Multi V1.1 Section 3.12.2 
	  */
 	 public static final int MULTI_20904 = 7020904; 

	 /**
	  * Error code 7020907:
	  * The value of the 'multi:identifyingParent' attribute on a given 
	  * SpeciesTypeComponentIndex object must be the identifier of a component object 
	  * under the SpeciesType object that this SpeciesTypeComponentIndex object belongs 
	  * to. A component object can be an object of SpeciesTypeInstance, 
	  * SpeciesTypeComponentIndex or SpeciesType. Reference: L3V1 Multi V1.1 Section 
	  * 3.12.3 
	  */
 	 public static final int MULTI_20907 = 7020907; 

	 /**
	  * Error code 7021101:
	  * An InSpeciesTypeBond object may have the optional SBML Level~3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level~3 Core namespace 
	  * are permitted on an InSpeciesTypeBond object. Reference: L3V1 Multi V1.1 Section 
	  * 3.13 
	  */
 	 public static final int MULTI_21101 = 7021101; 

	 /**
	  * Error code 7021102:
	  * An InSpeciesTypeBond object may have the optional SBML Level~3 Core subobjects 
	  * for 'notes' and 'annotation'. No other elements from the SBML Level~3 Core 
	  * namespace are permitted on an InSpeciesTypeBond object. Reference: L3V1 Multi 
	  * V1.1 Section 3.13 
	  */
 	 public static final int MULTI_21102 = 7021102; 

	 /**
	  * Error code 7021103:
	  * An InSpeciesTypeBond object must have the required attributes, 
	  * 'multi:bindingSite1' and 'multi:BindingSite2', and may have the optional 
	  * attributes, 'multi:id' and 'multi:name'. No other attributes from the Multi 
	  * namespace are permitted on an InSpeciesTypeBond object. Reference: L3V1 Multi 
	  * V1.1 Section 3.13 
	  */
 	 public static final int MULTI_21103 = 7021103; 

	 /**
	  * Error code 7021104:
	  * The value of the 'multi:bindingSite1' attribute on a given InSpeciesTypeBond 
	  * object must be the identifier of a SpeciesTypeInstance object or 
	  * SpeciesTypeComponentIndex which ultimately reference a object of 
	  * BindingSiteSpeciesType. Reference: L3V1 Multi V1.1 Section 3.13.2 
	  */
 	 public static final int MULTI_21104 = 7021104; 

	 /**
	  * Error code 7021105:
	  * The value of the 'multi:bindingSite2' attribute on a given InSpeciesTypeBond 
	  * object must be the identifier of a SpeciesTypeInstance object or 
	  * SpeciesTypeComponentIndex which ultimately reference a object of 
	  * BindingSiteSpeciesType. Reference: L3V1 Multi V1.1 Section 3.13.2 
	  */
 	 public static final int MULTI_21105 = 7021105; 

	 /**
	  * Error code 7021106:
	  * The 'multi:bindingSite1' and 'multi:bindingSite2' attributes must not reference 
	  * the same BindingSiteSpeciesType object. Reference: L3V1 Multi V1.1 Section 
	  * 3.13.2 
	  */
 	 public static final int MULTI_21106 = 7021106; 

	 /**
	  * Error code 7021201:
	  * A Species object may have the optional attribute, 'multi:speciesType'. No other 
	  * attributes from the Multi namespace are permitted on a Species object. 
	  * Reference: L3V1 Multi V1.1 Section 3.15 
	  */
 	 public static final int MULTI_21201 = 7021201; 

	 /**
	  * Error code 7021202:
	  * The value of a 'multi:speciesTypeAtt' attribute, if present on a Species object, 
	  * must be the identifier of a SpeciesType object. Reference: L3V1 Multi V1.1 
	  * Section 3.15.1 
	  */
 	 public static final int MULTI_21202 = 7021202; 

	 /**
	  * Error code 7021203:
	  * Two 'ListOf' subobjects with a Species object are optional, but if present, 
	  * these container object must not be empty. Specifically, if any of the following 
	  * two classes of objects are present on the Species object, it must not be empty: 
	  * ListOfOutwardBindingSites and ListOfSpeciesFeatures. Reference: L3V1 Multi V1.1 
	  * Section 3.15 
	  */
 	 public static final int MULTI_21203 = 7021203; 

	 /**
	  * Error code 7021204:
	  * A ListOfOutwardBindingSites object may have the optional SBML core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level~3 Core namespace 
	  * or the Multi namespace are permitted on a ListOfOutwardBindingSites object. 
	  * Reference: L3V1 Multi V1.1 Section 3.15.2 
	  */
 	 public static final int MULTI_21204 = 7021204; 

	 /**
	  * Error code 7021205:
	  * Apart from the general 'notes' and 'annotation' subobjects permitted on all SBML 
	  * objects, a ListOfOutwardBindingSites container object may only contain 
	  * OutwardBindingSite objects. Reference: L3V1 Multi V1.1 Section 3.15.2 
	  */
 	 public static final int MULTI_21205 = 7021205; 

	 /**
	  * Error code 7021206:
	  * A ListOfSpeciesFeatures object may have the optional SBML core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level~3 Core namespace 
	  * or the Multi namespace are permitted on a ListOfSpeciesFeatures object. 
	  * Reference: L3V1 Multi V1.1 Section 3.15.3 
	  */
 	 public static final int MULTI_21206 = 7021206; 

	 /**
	  * Error code 7021207:
	  * A SubListOfSpeciesFeatures object may have the optional attributes multi:id, 
	  * multi:relation and multi:component. No other attributes from the Multi namespace 
	  * are permitted on a SubListOfSpeciesFeatures object. Reference: L3V1 Multi V1.1 
	  * Section 3.17 
	  */
 	 public static final int MULTI_21207 = 7021207; 

	 /**
	  * Error code 7021208:
	  * The value of the 'multi:relation' attribute, if presented on a 
	  * SubListOfSpeciesFeatures object, must conform to the syntax of the Multi data 
	  * type 'Relation'. Reference: L3V1 Multi V1.1 Section 3.17.2 
	  */
 	 public static final int MULTI_21208 = 7021208; 

	 /**
	  * Error code 7021209:
	  * Apart from the general 'notes' and 'annotation' subobjects permitted on all SBML 
	  * objects, a ListOfSpeciesFeatures container object may only contain 
	  * SpeciesFeature and/or SubListOfSpeciesFeatures objects. Reference: L3V1 Multi 
	  * V1.1 Section 3.15.3 
	  */
 	 public static final int MULTI_21209 = 7021209; 

	 /**
	  * Error code 7021210:
	  * A SubListOfSpeciesFeatures object may have the optional SBML core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level~3 Core namespace 
	  * are permitted on a SubListOfSpeciesFeatures object. Reference: L3V1 Multi V1.1 
	  * Section 3.17 
	  */
 	 public static final int MULTI_21210 = 7021210; 

	 /**
	  * Error code 7021211:
	  * Apart from the general 'notes' and 'annotation' subobjects permitted on all SBML 
	  * objects, a SubListOfSpeciesFeatures container object may only contain 
	  * SpeciesFeature objects. Reference: L3V1 Multi V1.1 Section 3.17 
	  */
 	 public static final int MULTI_21211 = 7021211; 

	 /**
	  * Error code 7021212:
	  * The value of the 'multi:component' attribute on a given SubListOfSpeciesFeatures 
	  * object must be the identifier of an object of SpeciesTypeInstance, 
	  * SpeciesTypeComponentIndex or SpeciesType which contains the SpeciesFeature 
	  * objects in this subListOfSpeciesFeatures. Reference: L3V1 Multi V1.1 Section 
	  * 3.17.3 
	  */
 	 public static final int MULTI_21212 = 7021212; 

	 /**
	  * Error code 7021213:
	  * A species must have its 'speciesType' attribute defined when it has a 
	  * 'listOfOutwardBindingSites.' Reference: L3V1 Multi V1.1 Section 3.15 
	  */
 	 public static final int MULTI_21213 = 7021213; 

	 /**
	  * Error code 7021214:
	  * A species must have its 'speciesType' attribute defined when it has a 
	  * 'listOfSpeciesFeatures.' Reference: L3V1 Multi V1.1 Section 3.15 
	  */
 	 public static final int MULTI_21214 = 7021214; 

	 /**
	  * Error code 7021215:
	  * The 'relation' attribute of a subListOfSpeciesFeatures can only have the value 
	  * 'and' if any speciesFeature involved references a speciesFeatureType with occur 
	  * &gt; 1 Reference: L3V1 Multi V1.1 Section 3.17.2 
	  */
 	 public static final int MULTI_21215 = 7021215; 

	 /**
	  * Error code 7021216:
	  * A SubListOfSpeciesFeatures object must have at least two speciesFeatures. 
	  * Reference: L3V1 Multi V1.1 Section 3.17 
	  */
 	 public static final int MULTI_21216 = 7021216; 

	 /**
	  * Error code 7021301:
	  * An OutwardBindingSite object may have the optional SBML Level~3 Core attributes 
	  * 'metaid' and 'sboTerm. No other attributes from the SBML Level~3 Core namespace 
	  * are permitted on an OutwardBindingSite object. Reference: L3V1 Multi V1.1 
	  * Section 3.16 
	  */
 	 public static final int MULTI_21301 = 7021301; 

	 /**
	  * Error code 7021302:
	  * An OutwardBindingSite object may have the optional SBML Level~3 Core subobjects 
	  * for 'notes' and 'annotation. No other elements from the SBML Level~3 Core 
	  * namespace are permitted on an OutwardBindingSite object. Reference: L3V1 Multi 
	  * V1.1 Section 3.16 
	  */
 	 public static final int MULTI_21302 = 7021302; 

	 /**
	  * Error code 7021303:
	  * An OutwardBindingSite object must have the required attributes, 
	  * 'multi:bindingStatus' and 'mulit:component'. No other attributes from the Multi 
	  * namespace are permitted on an OutwardBindingSite object. Reference: L3V1 Multi 
	  * V1.1 Section 3.16 
	  */
 	 public static final int MULTI_21303 = 7021303; 

	 /**
	  * Error code 7021304:
	  * The value of the 'multi:bindingStatus' attribute on a given OutwardBindingSite 
	  * object must confirm to the syntax of the Multi data type 'BindingStatus'. 
	  * Reference: L3V1 Multi V1.1 Section 3.16.2 
	  */
 	 public static final int MULTI_21304 = 7021304; 

	 /**
	  * Error code 7021305:
	  * The value of the 'multi:component' attribute on a given OutwardBindingSite 
	  * object must be the identifier of an object of SpeciesTypeInstance, 
	  * SpeciesTypeComponentIndex or SpeciesType which ultimately reference an object of 
	  * BindingSiteSpeciesType. Reference: L3V1 Multi V1.1 Section 3.16.3 
	  */
 	 public static final int MULTI_21305 = 7021305; 

	 /**
	  * Error code 7021306:
	  * An outwardBindingSite can not be a binding site referenced by any 
	  * inSpeciesTypeBond in the species. Reference: L3V1 Multi V1.1 Section 3.16.3 
	  */
 	 public static final int MULTI_21306 = 7021306; 

	 /**
	  * Error code 7021401:
	  * A SpeciesFeature object may have the optional SBML Level~3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level~3 Core namespace 
	  * are permitted on a SpeciesFeature object. Reference: L3V1 Multi V1.1 Section 
	  * 3.16 
	  */
 	 public static final int MULTI_21401 = 7021401; 

	 /**
	  * Error code 7021402:
	  * A SpeciesFeature object may have the optional SBML Level~3 Core subobjects for 
	  * 'notes' and 'annotation'. No other elements from the SBML Level~3 Core namespace 
	  * are permitted on a SpeciesFeature object. Reference: L3V1 Multi V1.1 Section 
	  * 3.18 
	  */
 	 public static final int MULTI_21402 = 7021402; 

	 /**
	  * Error code 7021403:
	  * A SpeciesFeature object must have the required attributes, 
	  * 'multi:speciesFeatureType' and 'mulit:occur', and may have the optional 
	  * attribute, 'multi:id' and 'multi:component'. No other attributes from the Multi 
	  * namespace are permitted on a SpeciesFeature object. Reference: L3V1 Multi V1.1 
	  * Section 3.18 
	  */
 	 public static final int MULTI_21403 = 7021403; 

	 /**
	  * Error code 7021404:
	  * The value of the 'multi:speciesFeatureType' attribute on a given SpeciesFeature 
	  * object must be the identifier of a SpeciesFeatureType object which is in the 
	  * SpeciesType object referenced by the Species object containing this 
	  * SpeciesFeature object. Reference: L3V1 Multi V1.1 Section 3.18.2 
	  */
 	 public static final int MULTI_21404 = 7021404; 

	 /**
	  * Error code 7021405:
	  * The value of the 'mulit:occur' attribute on a given SpeciesFeature object must 
	  * conform to the syntax of the SBML data type 'positiveInteger'. The value of the 
	  * 'multi:occur' attribute must not be larger than that of the 'multi:occur' 
	  * attribute of the SpeciesFeatureType object referenced by this SpeciesFeature 
	  * object. Reference: L3V1 Multi V1.1 Section 3.18.3 
	  */
 	 public static final int MULTI_21405 = 7021405; 

	 /**
	  * Error code 7021406:
	  * The value of the 'multi:component' attribute on a given SpeciesFeature object 
	  * must be the identifier of an object of SpeciesTypeInstance, 
	  * SpeciesTypeComponentIndex or SpeciesType which contains this SpeciesFeature 
	  * object. Reference: L3V1 Multi V1.1 Section 3.18.4 
	  */
 	 public static final int MULTI_21406 = 7021406; 

	 /**
	  * Error code 7021407:
	  * One ListOfSpeciesFeatureValues subobject within a SpeciesFeature object is 
	  * required. Reference: L3V1 Multi V1.1 Section 3.18.5 
	  */
 	 public static final int MULTI_21407 = 7021407; 

	 /**
	  * Error code 7021408:
	  * A ListOfSpeciesFeatureValues object must not be empty. Reference: L3V1 Multi 
	  * V1.1 Section 3.18.5 
	  */
 	 public static final int MULTI_21408 = 7021408; 

	 /**
	  * Error code 7021409:
	  * A ListOfSpeciesFeatureValues object may have the optional SBML core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level~3 Core namespace 
	  * or the Multi namespace are permitted on a ListOfSpeciesFeatureValues object. 
	  * Reference: L3V1 Multi V1.1 Section 3.18.5 
	  */
 	 public static final int MULTI_21409 = 7021409; 

	 /**
	  * Error code 7021410:
	  * Apart from the general 'notes' and 'annotation' subobjects permitted on all SBML 
	  * objects, a ListOfSpeciesFeatureValues container object may only contain 
	  * SpeciesFeatureValue objects. Reference: L3V1 Multi V1.1 Section 3.18.5 
	  */
 	 public static final int MULTI_21410 = 7021410; 

	 /**
	  * Error code 7021501:
	  * A SpeciesFeatureValue object may have the optional SBML Level~3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level~3 Core namespace 
	  * are permitted on a SpeciesFeatureValue object. Reference: L3V1 Multi V1.1 
	  * Section 3.18.6 
	  */
 	 public static final int MULTI_21501 = 7021501; 

	 /**
	  * Error code 7021502:
	  * A SpeciesFeatureValue object may have the optional SBML Level~3 Core subobjects 
	  * for 'notes' and 'annotation'. No other elements from the SBML Level~3 Core 
	  * namespace are permitted on a SpeciesFeatureValue object. Reference: L3V1 Multi 
	  * V1.1 Section 3.18.6 
	  */
 	 public static final int MULTI_21502 = 7021502; 

	 /**
	  * Error code 7021503:
	  * A SpeciesFeatureValue object must have the required attribute 'multi:value'. No 
	  * other attributes from the Multi namespace are permitted on a SpeciesFeatureValue 
	  * object. Reference: L3V1 Multi V1.1 Section 3.18.6 
	  */
 	 public static final int MULTI_21503 = 7021503; 

	 /**
	  * Error code 7021504:
	  * The value of the 'multi:value' attribute on a given SpeciesFeatureValue object 
	  * must be the identifier of a PossibleSpeciesFeatureValue object defined in the 
	  * SpeciesFeatureType object referenced by the SpeciesFeature object containing 
	  * this SpeciesFeatureValue object. Reference: L3V1 Multi V1.1 Section 3.18.6 
	  */
 	 public static final int MULTI_21504 = 7021504; 

	 /**
	  * Error code 7021601:
	  * An IntraSpeciesReaction object may have the optional SBML Level~3 Core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level~3 
	  * Core namespace and the Multi namespace are permitted on an IntraSpeciesReaction 
	  * object. Reference: L3V1 Multi V1.1 Section 3.21 
	  */
 	 public static final int MULTI_21601 = 7021601; 

	 /**
	  * Error code 7021602:
	  * An IntraSpeciesReaction object may have the optional SBML Level~3 Core 
	  * subobjects for 'notes' and 'annotation'. No other elements from the SBML Level~3 
	  * Core namespace are permitted on an IntraSpeciesReaction object. Reference: L3V1 
	  * Multi V1.1 Section 3.21 
	  */
 	 public static final int MULTI_21602 = 7021602; 

	 /**
	  * Error code 7021701:
	  * An extended SimpleSpeciesReference object may have the optional attribute, 
	  * 'multi:compartmentReference'. No other attributes from the Multi namespace are 
	  * permitted on a SimpleSpeciesReference object. Reference: L3V1 Multi V1.1 Section 
	  * 3.22 
	  */
 	 public static final int MULTI_21701 = 7021701; 

	 /**
	  * Error code 7021702:
	  * An extended SimpleSpeciesReference object may have the optional attribute, 
	  * 'multi:compartmentReference'. No other attributes from the Multi namespace are 
	  * permitted on a SimpleSpeciesReference object. Reference: L3V1 Multi V1.1 Section 
	  * 3.22 
	  */
 	 public static final int MULTI_21702 = 7021702; 

	 /**
	  * Error code 7021801:
	  * A ListOfSpeciesTypeComponentMapsInProduct object within an extended 
	  * SpeciesReference object is optional, but if present, must not be empty. 
	  * Reference: L3V1 Multi V1.1 Section 3.23.1 
	  */
 	 public static final int MULTI_21801 = 7021801; 

	 /**
	  * Error code 7021802:
	  * A ListOfSpeciesTypeComponentMapsInProduct object may have the optional SBML core 
	  * attributes 'metaid' and 'sboTerm'. No other attributes from the SBML Level~3 
	  * Core namespace or the Multi namespace are permitted on a 
	  * ListOfSpeciesTypeComponentMapsInProduct object. Reference: L3V1 Multi V1.1 
	  * Section 3.23.1 
	  */
 	 public static final int MULTI_21802 = 7021802; 

	 /**
	  * Error code 7021803:
	  * Apart from the general 'notes' and 'annotation' subobjects permitted on all SBML 
	  * objects, a ListOfSpeciesTypeComponentMapsInProduct container object may only 
	  * contain SpeciesTypeComponentMapInProduct objects. Reference: L3V1 Multi V1.1 
	  * Section 3.23.1 
	  */
 	 public static final int MULTI_21803 = 7021803; 

	 /**
	  * Error code 7021901:
	  * A SpeciesTypeComponentMapInProduct object may have the optional SBML Level~3 
	  * Core attributes 'metaid' and 'sboTerm'. No other attributes from the SBML 
	  * Level~3 Core namespace are permitted on a SpeciesTypeComponentMapInProduct 
	  * object. Reference: L3V1 Multi V1.1 Section 3.24 
	  */
 	 public static final int MULTI_21901 = 7021901; 

	 /**
	  * Error code 7021902:
	  * A SpeciesTypeComponentMapInProduct object may have the optional SBML Level~3 
	  * Core subobjects for 'notes' and 'annotation'. No other elements from the SBML 
	  * Level~3 Core namespace are permitted on a SpeciesTypeComponentMapInProduct 
	  * object. Reference: L3V1 Multi V1.1 Section 3.24 
	  */
 	 public static final int MULTI_21902 = 7021902; 

	 /**
	  * Error code 7021903:
	  * A SpeciesTypeComponentMapInProduct object must have the required attributes 
	  * 'multi:reactant', 'multi:reactantComponent', and 'multi:productComponent'. No 
	  * other attributes from the Multi namespace are permitted on a 
	  * SpeciesTypeComponentMapInProduct object. Reference: L3V1 Multi V1.1 Section 3.24 
	  */
 	 public static final int MULTI_21903 = 7021903; 

	 /**
	  * Error code 7021904:
	  * The value of the 'multi:reactant' attribute on a given 
	  * SpeciesTypeComponentMapInProduct object must be the identifier of a reactant 
	  * SpeciesReference object within a reaction. Reference: L3V1 Multi V1.1 Section 
	  * 3.24.2 
	  */
 	 public static final int MULTI_21904 = 7021904; 

	 /**
	  * Error code 7021905:
	  * The value of the 'multi:reactantComponent' attribute on a given 
	  * SpeciesTypeComponentMapInProduct object must be the identifier of an object of 
	  * SpeciesTypeInstance, SpeciesTypeComponentIndex or SpeciesType. Reference: L3V1 
	  * Multi V1.1 Section 3.24.3 
	  */
 	 public static final int MULTI_21905 = 7021905; 

	 /**
	  * Error code 7021906:
	  * The value of the 'multi:productComponent' attribute on a given 
	  * SpeciesTypeComponentMapInProduct object must be the identifier of an object of 
	  * SpeciesTypeInstance, SpeciesTypeComponentIndex or SpeciesType. Reference: L3V1 
	  * Multi V1.1 Section 3.24.4 
	  */
 	 public static final int MULTI_21906 = 7021906; 

	 /**
	  * Error code 7022001:
	  * A CompartmentReference object may have the optional SBML Level~3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level~3 Core namespace 
	  * are permitted on a 'CompartmentReference object. Reference: L3V1 Multi V1.1 
	  * Section 3.6 
	  */
 	 public static final int MULTI_22001 = 7022001; 

	 /**
	  * Error code 7022002:
	  * A CompartmentReference object may have the optional SBML Level~3 Core subobjects 
	  * for 'notes' and 'annotation'. No other elements from the SBML Level~3 Core 
	  * namespace are permitted on a CompartmentReference object. Reference: L3V1 Multi 
	  * V1.1 Section 3.6 
	  */
 	 public static final int MULTI_22002 = 7022002; 

	 /**
	  * Error code 7022003:
	  * A CompartmentReference object must have the required attribute 
	  * 'multi:compartment', and may have the optional attributes 'multi:id' and 
	  * 'multi:name'. No other attributes from the Multi namespace are permitted on a 
	  * CompartmentReference object. Reference: L3V1 Multi V1.1 Section 3.6 
	  */
 	 public static final int MULTI_22003 = 7022003; 

	 /**
	  * Error code 7022004:
	  * The value of the 'multi:compartment' attribute must be the value of an 'id' 
	  * attribute on an existing Compartment object in the 'SId' namespace of the parent 
	  * model. Reference: L3V1 Multi V1.1 Section 3.6 
	  */
 	 public static final int MULTI_22004 = 7022004; 

	 /**
	  * Error code 7022005:
	  * If some or all compartmentReferences within a ListOfCompartmentReferences object 
	  * reference the same compartment, those compartmentReferences are required to have 
	  * its 'multi:id' attribute defined to distinguish different compartmentReferences. 
	  * Reference: L3V1 Multi V1.1 Section 3.6.1 
	  */
 	 public static final int MULTI_22005 = 7022005; 

	 /**
	  * Error code 7022006:
	  * A &lt;compartmentReference&gt; cannot reference a &lt;compartment&gt; that directly or 
	  * indirectly contains teh &lt;compartmentReference&gt;. Reference: L3V1 Multi V1.1 
	  * Section 3.6.1 
	  */
 	 public static final int MULTI_22006 = 7022006; 

	 /**
	  * Error code 8010101:
	  * To conform to the arrays Package specification for SBML Level 3 Version 1, an 
	  * SBML document must declare 
	  * 'http://www.sbml.org/sbml/level3/version1/arrays/version1' as the XMLNamespace 
	  * to use for elements of this package. Reference: L3V1 Arrays V1 Section 3.1 
	  */
 	 public static final int ARRAYS_10101 = 8010101; 

	 /**
	  * Error code 8010102:
	  * Wherever they appear in an SBML document, elements and attributes from the 
	  * arrays Package must use the 
	  * 'http://www.sbml.org/sbml/level3/version1/arrays/version1' namespace, declaring 
	  * so either explicitly or implicitly. Reference: L3V1 Arrays V1 Section 3.1 
	  */
 	 public static final int ARRAYS_10102 = 8010102; 

	 /**
	  * Error code 8010201:
	  * In all SBML documents using the arrays Package, the &lt;sbml&gt; object must have the 
	  * 'arrays:required' attribute. Reference: L3V1 Arrays V1 Section 
	  */
 	 public static final int ARRAYS_10201 = 8010201; 

	 /**
	  * Error code 8010202:
	  * The value of attribute 'arrays:required' on the &lt;sbml&gt; object must be of data 
	  * type 'boolean'. Reference: L3V1 Arrays V1 Section 
	  */
 	 public static final int ARRAYS_10202 = 8010202; 

	 /**
	  * Error code 8010203:
	  * The value of attribute 'arrays:required' on the &lt;sbml&gt; object must be set to 
	  * 'true'. Reference: L3V1 Arrays V1 Section 
	  */
 	 public static final int ARRAYS_10203 = 8010203; 

	 /**
	  * Error code 8010204:
	  * Wherever MathML content appears in an SBML document, the MathML content must be 
	  * placed within a math element, and that math element must be either explicitly or 
	  * implicitly declared to be in the XML namespace 
	  * http://www.w3.org/1998/Math/MathML Reference: L3V1 Arrays V1 Section 3.4 
	  */
 	 public static final int ARRAYS_10204 = 8010204; 

	 /**
	  * Error code 8010205:
	  * The following is a list of the additional MathML 2.0 elements permitted in the 
	  * Arrays package: vector and selector. Reference: L3V1 Arrays V1 Section 3.5 
	  */
 	 public static final int ARRAYS_10205 = 8010205; 

	 /**
	  * Error code 8010206:
	  * The first argument of aMathML selector must be aMathML vector object or a valid 
	  * identifier to an SBase object extended with a list of Dimension objects. 
	  * Reference: L3V1 Arrays V1 Section 3.5 
	  */
 	 public static final int ARRAYS_10206 = 8010206; 

	 /**
	  * Error code 8010207:
	  * The first argument of a MathML selector must have a number of dimensions equal 
	  * to the number of arguments to the selector minus 1. Reference: L3V1 Arrays V1 
	  * Section 3.5 
	  */
 	 public static final int ARRAYS_10207 = 8010207; 

	 /**
	  * Error code 8010208:
	  * The arguments of a MathML selector other than the first argument must be 
	  * statically computable. In other words, any identifier that appears in an 
	  * argument, other than a Dimension id for the corresponding object, must be a 
	  * constant. Reference: L3V1 Arrays V1 Section 3.5 
	  */
 	 public static final int ARRAYS_10208 = 8010208; 

	 /**
	  * Error code 8010209:
	  * The arguments of a MathML selector other than the first argument must be 
	  * evaluated to a scalar value. Reference: L3V1 Arrays V1 Section 3.5 
	  */
 	 public static final int ARRAYS_10209 = 8010209; 

	 /**
	  * Error code 8010210:
	  * For each possible value of each Dimension id (i.e., 0 to size-1 of the Dimension 
	  * referred to) that appears in the second and later arguments of the selector, 
	  * there should be no array out - of - bounds problems.Namely, it must evaluate to 
	  * a non - negative integer that is less than the size of the corresponding 
	  * Dimension for the object being indexed where the last argument refers to 
	  * dimension 0, next to last to dimension 1 Reference: L3V1 Arrays V1 Section 3.5 
	  */
 	 public static final int ARRAYS_10210 = 8010210; 

	 /**
	  * Error code 8010211:
	  * All mathematical operations must be performed on scalar values rather than 
	  * vectors. Reference: L3V1 Arrays V1 Section 3.5 
	  */
 	 public static final int ARRAYS_10211 = 8010211; 

	 /**
	  * Error code 8010212:
	  * For MathML operations with two or more operands involving MathML vectors or 
	  * SBase objects with a list of Dimension objects, the number of dimensions and 
	  * their sizemust agree for all operands unless the operand is a scalar type(i.e., 
	  * it does not have a list of Dimension objects). Reference: L3V1 Arrays V1 Section 
	  * 3.5 
	  */
 	 public static final int ARRAYS_10212 = 8010212; 

	 /**
	  * Error code 8010213:
	  * No SBase is allowed to have value of type vector. Reference: L3V1 Arrays V1 
	  * Section 3.5 
	  */
 	 public static final int ARRAYS_10213 = 8010213; 

	 /**
	  * Error code 8010301:
	  * (Extends validation rule #10301 in the SBML Level 3 Core specification. TO DO 
	  * list scope of ids) Reference: L3V1 Arrays V1 Section 
	  */
 	 public static final int ARRAYS_10301 = 8010301; 

	 /**
	  * Error code 8010302:
	  * The value of a 'arrays:id' must conform to the syntax of the &lt;sbml&gt; data type 
	  * 'SId' Reference: L3V1 Arrays V1 Section 
	  */
 	 public static final int ARRAYS_10302 = 8010302; 

	 /**
	  * Error code 8020101:
	  * Any object derived from the extended SBase class (defined in the Arrays package) 
	  * may contain at most one instance of a ListOfDimensions. Reference: L3V1 Arrays 
	  * V1 Section 3.3 
	  */
 	 public static final int ARRAYS_20101 = 8020101; 

	 /**
	  * Error code 8020102:
	  * Apart from the general notes and annotations subobjects permitted on all SBML 
	  * objects, a &lt;listOfDimensions&gt; container object may only contain &lt;dimension&gt; 
	  * objects. Reference: L3V1 Arrays V1 Section 
	  */
 	 public static final int ARRAYS_20102 = 8020102; 

	 /**
	  * Error code 8020103:
	  * The ListOfDimensions associated with an SBase object must have a Dimension 
	  * object with arrays:arrayDimension attribute set to 0 ... n-1 before adding a 
	  * Dimension object with arrays : arrayDimension attribute set to n. Reference: 
	  * L3V1 Arrays V1 Section 3.3 
	  */
 	 public static final int ARRAYS_20103 = 8020103; 

	 /**
	  * Error code 8020104:
	  * The ListOfDimensions associated with an SBase object must not have multiple 
	  * Dimension objects with the same arrays:arrayDimension attribute. Reference: L3V1 
	  * Arrays V1 Section 3.3 
	  */
 	 public static final int ARRAYS_20104 = 8020104; 

	 /**
	  * Error code 8020105:
	  * A &lt;listOfDimensions&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;listOfDimensions&gt; object. Reference: L3V1 Arrays 
	  * V1 Section 
	  */
 	 public static final int ARRAYS_20105 = 8020105; 

	 /**
	  * Error code 8020106:
	  * ListOf objects are not permitted to have a ListOfDimensions. Reference: L3V1 
	  * Arrays V1 Section 3.3 
	  */
 	 public static final int ARRAYS_20106 = 8020106; 

	 /**
	  * Error code 8020107:
	  * InSBML Level 3 Core, Models, FunctionDefinitions, Units, UnitDefinitions, 
	  * KineticLaws, LocalParameters, Triggers, Priorities, and Delays are not permitted 
	  * to have a ListOfDimensions. All other SBML Level 3 Core objects are permitted to 
	  * have a ListOfDimensions including: Compartments, Species, Parameters, Initial 
	  * assignments, Rules, Constraints, Reactions, Species references, Events, and 
	  * Event assignments.All SBML objects defined by packages that inherit from SBase 
	  * are permitted to have a ListOfDimensions unless it is explicitly disallowed in 
	  * the corresponding package specification. Reference: L3V1 Arrays V1 Section 3.3 
	  */
 	 public static final int ARRAYS_20107 = 8020107; 

	 /**
	  * Error code 8020108:
	  * The Dimension and Index objects are not permitted to have a ListOfDimensions. 
	  * Reference: L3V1 Arrays V1 Section 3.3 
	  */
 	 public static final int ARRAYS_20108 = 8020108; 

	 /**
	  * Error code 8020109:
	  * Any object derived from the extended SBase class (defined in the Arrays package) 
	  * may contain at most one instance of a ListOfIndices. Reference: L3V1 Arrays V1 
	  * Section 3.4 
	  */
 	 public static final int ARRAYS_20109 = 8020109; 

	 /**
	  * Error code 8020110:
	  * Apart from the general notes and annotations subobjects permitted on all SBML 
	  * objects, a &lt;listOfIndices&gt; container object may only contain &lt;index&gt; objects. 
	  * Reference: L3V1 Arrays V1 Section 
	  */
 	 public static final int ARRAYS_20110 = 8020110; 

	 /**
	  * Error code 8020111:
	  * The ListOfIndices associated with an SBase object must have a Index object with 
	  * arrays:arrayDimension attribute set to 0, 1, . ,n-1 before adding a Index object 
	  * with arrays : arrayDimension attribute set to n for every arrays : 
	  * referencedAttribute that are being indexed. Reference: L3V1 Arrays V1 Section 
	  * 3.3 
	  */
 	 public static final int ARRAYS_20111 = 8020111; 

	 /**
	  * Error code 8020112:
	  * The ListOfIndices in an SBase object must not have multiple Index objects with 
	  * the same pair of values arrays:arrayDimension and arrays:referencedAttribute 
	  * attributes. Reference: L3V1 Arrays V1 Section 3.4 
	  */
 	 public static final int ARRAYS_20112 = 8020112; 

	 /**
	  * Error code 8020113:
	  * A &lt;listOfIndices&gt; object may have the optional SBML Level 3 Core attributes 
	  * 'metaid' and 'sboTerm'. No other attributes from the SBML Level 3 Core 
	  * namespaces are permitted on a &lt;listOfIndices&gt; object. Reference: L3V1 Arrays V1 
	  * Section 
	  */
 	 public static final int ARRAYS_20113 = 8020113; 

	 /**
	  * Error code 8020114:
	  * Only SBML objects that include defined attributes of type SIdRef are permitted 
	  * to have a ListOfIndices. For SBML Level 3 Core, this includes Model to reference 
	  * a conversionFactor element, Species to reference a compartment or a 
	  * conversionFactor element, Reactions to reference a compartment, Initial 
	  * assignments to reference a symbol, Rules to reference a variable, Species 
	  * references to reference a species, and Events assignments to reference a 
	  * variable. In addition to these, any SBML object in a package with a defined 
	  * attribute of type SIdRef may also have a ListOfIndices. Reference: L3V1 Arrays 
	  * V1 Section 3.4 
	  */
 	 public static final int ARRAYS_20114 = 8020114; 

	 /**
	  * Error code 8020115:
	  * SBase objects containg SIdRef must have a ListOfIndices if the referenced SBase 
	  * is an array. Reference: L3V1 Arrays V1 Section 3.4 
	  */
 	 public static final int ARRAYS_20115 = 8020115; 

	 /**
	  * Error code 8020116:
	  * SBase objects containg SIdRef with a ListOfDimensions should have a 
	  * ListOfIndices containing as many Index objects for this particular 
	  * arrays:referencedAttribute as the number of Dimension objects the referenced 
	  * object contains. Reference: L3V1 Arrays V1 Section 3.4 
	  */
 	 public static final int ARRAYS_20116 = 8020116; 

	 /**
	  * Error code 8020117:
	  * Reference: L3V1 Arrays V1 
	  */
 	 public static final int ARRAYS_20117 = 8020117; 

	 /**
	  * Error code 8020201:
	  * A &lt;dimension&gt; object may have the optional SBML Level 3 Core attributes 'metaid' 
	  * and 'sboTerm'. No other attributes from the SBML Level 3 Core namespaces are 
	  * permitted on a &lt;dimension&gt;. Reference: L3V1 Arrays V1 Section 
	  */
 	 public static final int ARRAYS_20201 = 8020201; 

	 /**
	  * Error code 8020202:
	  * A &lt;dimension&gt; object must have the required attributes 'arrays:size' and 
	  * 'arrays:arrayDimension', and may have the optional attributes 'arrays:id' and 
	  * 'arrays:name'. No other attributes from the SBML Level 3 arrays namespaces are 
	  * permitted on a &lt;dimension&gt; object. Reference: L3V1 Arrays V1 Section 
	  */
 	 public static final int ARRAYS_20202 = 8020202; 

	 /**
	  * Error code 8020203:
	  * The attribute 'arrays:arrayDimension' on a &lt;dimension&gt; must have a value of data 
	  * type 'integer'. Reference: L3V1 Arrays V1 Section 
	  */
 	 public static final int ARRAYS_20203 = 8020203; 

	 /**
	  * Error code 8020204:
	  * The value of the attribute 'arrays:size' of a &lt;dimension&gt; object must be the 
	  * identifier of an existing &lt;sBase&gt; object defined in the enclosing &lt;model&gt; 
	  * object. Reference: L3V1 Arrays V1 Section 
	  */
 	 public static final int ARRAYS_20204 = 8020204; 

	 /**
	  * Error code 8020205:
	  * The value of the Parameter referenced by the arrays:size attribute must be a 
	  * non-negative scalar constant integer. Reference: L3V1 Arrays V1 Section 3.3 
	  */
 	 public static final int ARRAYS_20205 = 8020205; 

	 /**
	  * Error code 8020301:
	  * An &lt;index&gt; object may have the optional SBML Level 3 Core attributes 'metaid' 
	  * and 'sboTerm'. No other attributes from the SBML Level 3 Core namespaces are 
	  * permitted on an &lt;index&gt;. Reference: L3V1 Arrays V1 Section 
	  */
 	 public static final int ARRAYS_20301 = 8020301; 

	 /**
	  * Error code 8020302:
	  * An &lt;index&gt; object must have the required attributes 'arrays:referencedAttribute' 
	  * and 'arrays:arrayDimension'. No other attributes from the SBML Level 3 arrays 
	  * namespaces are permitted on an &lt;index&gt; object. Reference: L3V1 Arrays V1 Section 
	  */
 	 public static final int ARRAYS_20302 = 8020302; 

	 /**
	  * Error code 8020303:
	  * The attribute 'arrays:referencedAttribute' on an &lt;index&gt; must have a value of 
	  * data type 'string'. Reference: L3V1 Arrays V1 Section 
	  */
 	 public static final int ARRAYS_20303 = 8020303; 

	 /**
	  * Error code 8020304:
	  * The attribute 'arrays:arrayDimension' on an &lt;index&gt; must have a value of data 
	  * type 'integer'. Reference: L3V1 Arrays V1 Section 
	  */
 	 public static final int ARRAYS_20304 = 8020304; 

	 /**
	  * Error code 8020305:
	  * The object referenced by the SIdRef indicated by the arrays:referencedAttribute 
	  * attribute must include an arrays:arrayDimension matching the 
	  * arrays:arrayDimension for the Index Reference: L3V1 Arrays V1 Section 3.4 
	  */
 	 public static final int ARRAYS_20305 = 8020305; 

	 /**
	  * Error code 8020306:
	  * An &lt;index&gt; object must contain one and only one instance of the ASTNode element. 
	  * No other elements from the SBML Level 3 arrays namespaces are permitted on an 
	  * &lt;index&gt; object. Reference: L3V1 Arrays V1 Section 
	  */
 	 public static final int ARRAYS_20306 = 8020306; 

	 /**
	  * Error code 8020307:
	  * The MathML math element in an Index object must be statically computable. In 
	  * other words, any identifier that appears in the math element, other than a 
	  * Dimension id for the object with this Index, must be a constant. Reference: L3V1 
	  * Arrays V1 Section 3.4 
	  */
 	 public static final int ARRAYS_20307 = 8020307; 

	 /**
	  * Error code 8020308:
	  * For each possible value of each Dimension id (i.e., 0 to size-1 of the Dimension 
	  * referred to) that appears in theMathML math element, there should be no array 
	  * out of bounds problems. Namely, it must evaluate to a non-negative integer that 
	  * is less than the size of the corresponding Dimension for the object being 
	  * indexed. Reference: L3V1 Arrays V1 
	  */
 	 public static final int ARRAYS_20308 = 8020308; 

	 /**
	  * Error code 8020309:
	  * An &lt;index&gt; object may have the optional SBML Level 3 Core subobjects for notes 
	  * and annotations. No other elements from the SBML Level 3 Core namespaces are 
	  * permitted on an &lt;index&gt;. Reference: L3V1 Arrays V1 Section 
	  */
 	 public static final int ARRAYS_20309 = 8020309; 

	 /**
	  * Error code 8020402:
	  * A &lt;dimension&gt; object may have the optional SBML Level 3 Core subobjects for 
	  * notes and annotations. No other elements from the SBML Level 3 Core namespaces 
	  * are permitted on a &lt;dimension&gt;. Reference: L3V1 Arrays V1 Section 
	  */
 	 public static final int ARRAYS_20402 = 8020402; 

	 /**
	  * Error code 8020403:
	  * The attribute 'arrays:name' on a &lt;dimension&gt; must have a value of data type 
	  * 'string'. Reference: L3V1 Arrays V1 Section 
	  */
 	 public static final int ARRAYS_20403 = 8020403; 
 	 
 	 
 	 // TODO: Finalize the following rules for SPATIAL
 	 

 	 public static final int SPATIAL_23450 = 1223450;

 	 public static final int SPATIAL_23451 = 1223451;

 	 public static final int SPATIAL_23452 = 1223452;
 	 
 	 public static final int SPATIAL_23650 = 1223650;
 	 
 	 public static final int SPATIAL_20650 = 1220650;
 	 
 	 public static final int SPATIAL_23750 = 1223750;
 	 
 	 public static final int SPATIAL_23751 = 1223751;
 	 
 	 public static final int SPATIAL_23752 = 1223752;
 	 
 	 public static final int SPATIAL_23753 = 1223753;
 	 
 	 public static final int SPATIAL_23754 = 1223754;
 	 
 	 public static final int SPATIAL_20750 = 1220750;
 	 
 	 public static final int SPATIAL_20751 = 1220751;
 	 
 	 public static final int SPATIAL_20752 = 1220752;
 	 
 	 public static final int SPATIAL_21950 = 1221950;
 	 
 	 public static final int SPATIAL_21750 = 1221750;
 	 
 	 public static final int SPATIAL_21751 = 1221751;
 	 
 	 public static final int SPATIAL_21752 = 1221752;
 	 
 	 public static final int SPATIAL_21753 = 1221753;
 	 
 	 public static final int SPATIAL_21754 = 1221754;
 	 
 	 public static final int SPATIAL_22350 = 1222350;
 	 
 	 public static final int SPATIAL_23250 = 1223250;
 	 
 	 public static final int SPATIAL_23251 = 1223251;
 	 
 	 public static final int SPATIAL_23850 = 1223850;
 	 
 	 public static final int SPATIAL_21650 = 1221650;
 	 
 	 public static final int SPATIAL_21651 = 1221651;
 	 
 	 public static final int SPATIAL_21652 = 1221652;
 	 
  /**
   * Error code 1321209:
   * The attribute render:href on an Image must point to a local file of type
   * "jpeg" or "png".
   * Reference: L3V1 Render V1 Section 3.10.5
   */
  public static final int RENDER_21209  = 1321209; 
  
  /**
   * Error code 1323040:
   * The ListOfElements subobject on a RenderCurve or a Polygon object is
   * optional, but if present, this container object must not be empty.
   * Reference: L3V1 Render V1 Section 3.9.3
   */
  public static final int RENDER_23040  = 1323040;
  
  /**
   * Error code 1323041:
   * Apart from the general notes and annotations subobjects permitted on all
   * SBML objects, a ListOfElements container object may only contain
   * RenderPoint or the derived RenderCubicBezier objects.
   * Reference: L3V1 Render V1 Section 3.9.3
   */
  public static final int RENDER_23041  = 1323041;
   
  /**
   * Error code 1323042:
   * A ListOfElements object may have the optional SBML Level 3 Core attributes
   * metaid and sboTerm. No other attributes from the SBML Level 3 Core
   * namespaces are permitted on a ListOfElements object.
   * Reference: L3V1 Render V1 Section 3.9.3
   */
  public static final int RENDER_23042  = 1323042;
  
  /**
   * Error code 1323043:
   * The first element within a ListOfElements container object must be of type
   * RenderPoint (but not of the derived RenderCubicBezier)
   * Reference: L3V1 Render V1 Section 3.9.3
   */
  public static final int RENDER_23043  = 1323043;
}
