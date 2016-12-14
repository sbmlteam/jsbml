	       JSBML NEWS -- History of user-visible changes


===========================================================================
Version 1.2 (14-12-2016)
===========================================================================

 * New Features:

  - Updated the implementation of the SBML Level 3 Multi package to the Version 1.0.7 specification (August 2016).
  
  - JSBML is now deployed to Maven central to make it easier for people using maven to integrate it.
  

 * Bug Fixes:

  - Several corrections to the Spatial package, when writing to XML. Thanks to Kaito Ii for reporting the errors.
  
  - Updated the implementation of the SBML Level 3 Render package to the draft specification (April 2015). Plenty of XML elements where not written with the correct names. Any files created by JSBML before version 1.2 should still be readable by JSBML 1.2 so that you can just read them and write them to get the correct XML.
  
  - Returning the correct namespace for core elements when calling getNamespace() or getURI() (instead of 'null' previously).

  - Updated the XML attribute name of Samplefield.interpolation. Changed it from 'interpolation' to 'interplationType'. The change should be transparent for the user as the API is unchanged and when reading a file, we are reading both attribute names.


 * Miscellaneous updates:

  - We have made numerous updates to the JSBML User Guide.


===========================================================================
Version 1.2-beta1 (26-10-2016)
===========================================================================

 * New Features:

  - The minimum JDK requirement is now 1.7.

  - Added SBML L3V2 support, except for support of VCard4.
  
  - Added support for the MathML <semantics> annotations.

  - Added the ASTNodePlugin interface so that ASTNode can have plugins.
  
  - Added new initDefaults method in most SBase based classes with an additional Boolean flag 'explicit' as parameter. This allows, for example, setting the SBML Level 2 default in an SBML Level 3 class. If the explicit boolean is set to true, the default values will be written to XML.

  - JSBML now tries to retain any unknown XML elements encountered while reading an XML source. This makes it possible to write them back, and users can also access them through a user object XMLNode.
    
  - Added a static function parseMathML() in ASTNode to make the function easier to find. Thanks to Jason Zwolak who suggested it.
  
  - Added two methods, hasTerm(int) and hasTerm(String), in SBO to allow users to safely check if a term can be retrieved for a given SBO identifier without having to catch a NoSuchElementException.
  
  - Updated the implementation of the SBML Level 3 Groups package to the final Version 1, Release 1 specification.

  - Integrated a draft version of an offline SBML validation system that was begun during GSOC 2016 by Roman Schulte. The current version should only be used for testing; it is not complete and should not yet be used in production.
    
  - The toString() methods of most classes have been replaced by a generic method so you might find some changes. Please don't hesitate to provide us with feedback about this change. 
  
  - We deprecated the Model.getInitialAssignment() and Model.getRule() methods because they have been made ambiguous by the introduction of an id on SBase in SBML L3V2. The JSBML JavaDoc will point you to the replacement methods. The new method names make it explicit what is used to retrieve the element, for example 'Model.getRuleByVariable()'.
  
  - We introduced a new class, UniqueSId, to help recognize any class that must have a unique SId in the whole Model. This class replaced UniqueNamedSBase which was not change and can help recognize any class that must have a unique SId in the whole Model before SBML L3V2. The same applies to SBase and NamedSBase, every SBase have now an id and a name and the NamedSBase class only apply to elements that had an id and a name before SBML L3V2.
  
  
 * Bug Fixes:

  - A whole SBMLDocument was not serializable because a few classes did not implement the Serializable class. This should be fixed now. Thanks to Jens Einloft who reported the problem.

  - Fixed the SBMLReader class to make sure the UTF-8 encoding was used when reading an SBML model from a String. Thanks to Matthias König who reported this problem.
    
  - Corrected an encoding problem in the tidy SBML writer that happened when the default JVM encoding was not UTF-8.
  
  - Several improvements were made to the infix formula reading/writing. Thanks to Miguel de Alba who reported some problem and the libSBML team who provided a large set of test cases.

  - When writing a 'sbml:units' attribute on MathML <cn> elements for real numbers with e-notation, the XML tag <cn> was closed before the attribute was written. Thanks to Matthias König who reported this problem.  
  
  - The clone constructor for SBaseRef was not cloning properly its child SBaseRef.

  - The equals method of ASTNode was failing when one of the double values was set to NaN.
  
  - Corrected the 'metaid' attribute value pattern; it did not include the colon character.
  
  - Corrected the FBCModelPlugin clone constructor so that it properly cloned the 'strict' attribute.

  - Corrected the Boundary and SampledFieldGeometry clone constructor. Thanks to Kaito Ii who reported the problem.

  - Corrected several potential NullPointerExceptions that could occur during the cloning of an SBMLDocument that contain plugins.
    
  - Corrected a potential NullPointerException in the TreeNodeAdapter(Object, TreeNode) constructor.
  
  - Corrected the number returned by ASTNode.getReal() in the case of REAL_E, when the mantissa is NaN or infinity.  


===========================================================================
Version 1.1 (09-12-2016)
===========================================================================

 * New Features:

  - Implemented the ability to support multiple version of SBML Level 3
    package definitions.

  - Improved the Maven pom files, and changed the Maven group ids and
    artifact ids. They should be stable now. With these changes in place,
    we can update the Maven repository much more quickly and easily. Next
    steps are to make JSBML available in Maven Central.

  - Added some utility classes to improve support for SBML files written by
    the Cobra toolbox before the SBML Level 3 FBC package was created. The
    CobraFormulaParser class can parse properly the Cobra gene associations
    String into an ASTNode. The CobraUtils class contain a method to parse
    the SBML notes into a Java Properties object.

  - Updated the implementation of the SBML Level 3 Groups package to the
    Groups version 0.7 specification.

 * Bug Fixes:

  - The compartment 'units' attribute was not written in SBML L1. This is
    corrected now.

  - SBML L1 rules were not correctly written: the wrong XML element name
    was used. Now it uses the correct L1 names and attributes.

  - The clone constructor for the Species class did not copy the
    speciesType attribute.  Thanks to Olivier Martin who reported the
    problem.

  - The clone constructor for the FunctionDefinition class did not copy the
    id attribute.
  
  - Since the merge with the astnode2 branch in 1.1-beta1, the attributes
    on mathML elements where processed before calling the main 
    processAttributes method and as a consequence, some of them where not
    processed at all, like 'sbml:units'. Thanks to Matthias König who 
    reported the problem.

===========================================================================
Version 1.1-beta1 (12-10-2016)
===========================================================================

 * New Features:

  - Changed the default JSBML formula compiler. WARNING: this changes the
    default output for comparison operators. The parsing is now very close
    to what the libSBML L3 parser is doing.

  - Added support for the nested CVTerms introduced in SBML L2V5 and
    L3V2. We created some new methods in the CVTerm class to be able to
    manipulate nested terms.

  - Merged with the ASTNode2 branch (experimental). This is not used by
    default, but it is starting to work well. We were able to pass all tests
    from the SBML Test Suite using the ASTNode facade that uses the ASTNode2
    classes underneath. We made this work so that no API change is needed --
    old code should work without modifications.

  - Changed the printing of ASTNode in SimpleTreeNodeChangeListener to
    avoid many exceptions being printed when reading an XML file and the
    log4j DEBUG is activated (SF.net tracker item #88).

  - Improved CellDesigner LayoutConverter.

  - Added a new class, PackageUtil, to check and fix the package versions and
    namespaces if needed. This method is called after we read a file from
    XML or before we write the model to XML. In the near future, the method
    SBase.registerChild will take care of setting everything properly.

  - Added a default load of the parsers for environment not setup properly
    for it (Eclipse, Matlab dynamic path, OSGi, ...).

  - Modified the way we initialize the XML factories so that it is explicit
    (to OSGi for example) which classes and packages are used. Then there
    is no more need to define the System properties about the XML factories
    which solve an other reported problem with OSGi bundles. Thanks to all
    users who reported and tested problems related to OSGi.

  - Implemented a new interface CompartmentalizedSBase and changed all
    elements that can have a compartment so that they extend it.

  - Added a createCurve method to ReactionGlyph.

  - Improvements in the arrays flattening code.

  - Updated spatial to implement version 0.90 of the draft specifications.

  - Implemented FBC version 2.

  - Implemented distrib version 1 draft 0.16.

  - Modified the SBMLReader so that we are able to parse some UncertML
    Strings as XMLNode, even when they are on their own, via
    org.sbml.jsbml.xml.XMLNode.convertStringToXMLNode(String).

  - Added a new class DistribModelBuilder to help users to create distrib
    elements, including uncertML XMLNode.

  - Implemented the multi draft rev 459 (Nov 2014).


 * Bug Fixes:

  - Methods that accepted Double/Integer/Boolean values or returned those
    where updated to double/integer/boolean in the spatial and render
    package. Instead of null values PropertyUndefinedExceptions are now
    thrown when necessary.

  - Tracker item #83: the equals method from StringBuilder is
    not implemented so we have to compare the content of the StringBuilder
    or use other utility method to compare properly the StringBuilders.

  - Tracker item #84: corrected and improved the XMLNode.toString method.

  - Tracker item #85: removing spaces with the String.trim() method in
    org.sbml.jsbml.Creator.setEmail(String) before checking the validity
    of the email address. Thanks to Camille Laibe who reported this
    problem.

  - Tracker item #87: changed HashMap to Map in the SBMLValidator API.

  - When writing math formula, in some cases, parentheses were not generated
    properly surrounding relational expressions.  This is now fixed. Thanks
    to Miguel de Alba for reporting this problem.
     
  
===========================================================================
Version 1.0 (09-12-2014)
===========================================================================

 * New Features:

  - Added a new module that contain a tidy SBMLWriter that use the jtidy
    library to correct the formatting of the XML file before writing it
    out.

  - Created a new class SyntaxChecker that corresponds to the class in
    libSBML and moved pattern matching for id comparisons and e-mail
    addresses into this class.

  - Updated JSBML to create identifiers.org URIs instead of URNs for
    units.

 * Support for SBML Level 3 'Dynamic Structures (dyn)' package:

  - First draft of JSBML implementation done (experimental).

 * Bug Fixes:

  - Solved error #82 in Unit.removeMultiplier. Thanks to Christian Thöns
    for pointing this out.
    
  - The registerChild method now returns a boolean value to indicate if
    the operation was successfully completed. This prevents users from
    adding duplicates to ListOf objects.

  - Improvements made to the java API documentation (javadoc).
 
  - Added a check to write only the necessary namespaces when writting
    CVTerm to XML.
 
 * Support for SBML Level 3 'Hierarchical Model Composition (comp)'
   package:

  - Registering SBaseRef correctly in the document. Corrected the clone
    constructor of SBaseRef that did not copy the recursive SBaseRef.

  - Added all the missing equals and hashCode methods in the comp
    package.

 * Support for SBML Level 3 'Arrays' package:

  - Several fixes and improvements in the validation code and the
    flattening code.


===========================================================================
Version 1.0-rc1 (24-09-2014)
===========================================================================

* New Features:

  - Changed the default behaviour of the parsing of math infix formula.
    The methods concerned are JSBML#parseFormula and ASTNode#parseFormula.
    The changes include a different operator precedence, to match the one
    used in the L3 libSBML parser. The parsing is also case sensitive for
    mathML elements. Boolean operators are now differently interpreted:
    '&&' and '||' are used instead of 'and' and 'or'. Please consult the
    JavaDoc of those methods for more details, it is also explain there
    what to do if you want to keep the old behaviour.

  - Added two new BioModels.net qualifiers, bqmodel:isIntanceOf and
    bqmodel:hasInstance.
    
  - BioJava was updated to the latest version and the dependency is
    now to biojava3-ontology.   

  - Added removeCVTerm methods in Annotation and SBase.

  - An IdManager interface has been introduced to help deal with the
    different id namespace in SBML core and in the different packages.

  - JSBML now provides a convenient ModelBuilder that can be used to create
    SBML models with a significantly lower  number of lines of code for the
    end user.
    
  - Improved Java source code documentation (in particular CVTerm).
  
  - The SBMLDocument has now a convenient method to access all metaIds
    within the document in form of an unmodifiable Collection.
    
  - The SBO class provides more methods to access those terms that are of
    particular interest for SBGN displays.

  - A general Pair of arbitrary values now generalizes ValuePair.
  
  - The toString() methods in UnitDefinition and SpeciesReferences have
    been improved and are now more informative.
    
  - Internally, JSBML no longer creates Unit objects that lack required
    attributes when reading models. (However, this does not prevent users
    from creating new Unit objects without such attributes.)
  
  - Species and Compartment 'containsUndeclaredUnits' method has been
    overridden because in SBML L3 Species and Compartments can inherit a
    default unit from the Model. This is now considered. In this case,
    containsUndeclaredUnits will return false even if the object itself
    does not explicitly declare its unit. This is important because the
    unit of the element is not undefined.
    
  - Added some missing methods to manipulate EvenAssignment in Event and
    improved the JavaDoc of the Event class.
  
  - Added some missing getter in the L3 packages classes.

 * Support for SBML Level 3 'Arrays' package:
 
  - Arrays package implemented, version 05/05/14. Only selector and vector
    supported for now. Validations, flattening available.

  - Added support for vector and selector infix parsing to support the
    arrays package.

 * Support for SBML Level 3 'Layout' package:

  - Added missing methods to remove GeneralGlyph objects from a Layout
    object.
  
  - Added further missing remove or unset methods in the layout package.

 * Support for SBML Level 3 'Qualitative Models (qual)' package:

  - The class QualitativeModel in the qual package has been marked
    deprecated and should no longer be used. Aiming to achieve better
    compatibility to libSBML, the new class QualModelPlugin has been
    created to be used instead.

 * Support for SBML Level 3 'Spatial Processes (spatial)' package:
 
  - Updated to implement version 0.88 of the draft specifications.
  

* Bug Fixes:

  - Corrected the method AbstractSBase#getExtensionCount that was always
    returning 0.

  - Corrected the SBMLWriter so that disabled packages are not written to
    XML any more (they are still cloned).
  
  - Corrected the method SBase.appendNotes that contained several problems.
 
  - Corrected a bug when cloning a text XMLToken.
 
  - Fixed the AbstractSBase equals method that could throw a
    NullPointerException in some cases and was not testing all fields.
 
  - Fixed the CVTerm.removeResource(String) method. Thanks to Camille
    Laibe who reported the problem (tracker item #80).

  - Corrected the method ASTNode.parseFormula so that they catch any
    Exception or Error and throw a ParseException instead.
 
  - Added the support for the 'root' function in the parsing of math
    formula.

  - The FormulaParserLL3 was not reading properly formula like 
    'kf * S0 * S1'. Only two arguments were read instead of three or more.
    Thanks to Chris J. Myers for reporting this problem.
  
  - Corrected a bug in Event#setListOfEventAssignments method that
    prevented the listOfEventAssignments to be registered properly in the
    model (ids and metaids registered).
    
  - Corrected the removeFromParent method to make it working for
    SBasePlugin instances.

  - The ListOf.remove(String) will not throw a ClassCastException anymore
    when used on list that do not contain elements with id.
    
  - The static method JSBML.read(String) was not calling the right helper 
    method.
    
  - ASTNode threw a NullPointerException when trying to un-set its
    variable.
  
  - The AbstractTreeNode recursive method 
    removeTreeNodeChangeListener(TreeNodeChangeListener, boolean) was not
    actually recursive.

  - Solved source for potential NullPointerException in LaTeXCompiler.

 * Support for SBML Level 3 'Hierarchical Model Composition (comp)' 
   package:
  
  - Fixed several issues when cloning 'comp' elements and L3 packages
    elements in general. Thanks to Chris J. Myers and the iBioSim team for
    their help to find and fix all issues.

 * Support for SBML Level 3 'Flux Balance Constraints (fbc)' package:
 
  - The required flag for the fbc package was set to true instead of false,
    thanks to Chris J. Myers for reporting this problem.

 * Support for SBML Level 3 'Layout' package:
 
  - The implementation of the layout package contained unnecessary classes
    that all represented a Point object (Start, End, Position, BasePoint1,
    BasePoint2). In order to avoid confusion and to simplify the
    implementation, those classes have all been deleted.

  - Corrected the method Layout#setListOfAdditionalGraphicalObjects to
    avoid the warnings when cloning the list and to avoid to create a new
    list when not needed.
    
 * Support for SBML Level 3 'Qualitative Models (qual)' package:
 
  - The SBML qual attribute Output.outputLevel was not read properly from
    XML.

 * Support for SBML Level 3 'Rendering (render)' package:
 
  - Fixed severals bugs in the experimental render package. Renamed the
    Group class to RenderGroup and the Curve class to RenderCurve. Added
    the possibility to add children to a RenderGroup and corrected
    the way it was written to XML.
 
  
======================================================================
Version 1.0-beta1 (06-03-2014)
======================================================================

* New Features:

  - The main addition to JSBML 1.0 is support for the SBML Level 3 packages,
    as described below.

  - JSBML is no longer compliant with Java 1.5, and now requires Java 1.6 as
    a minimum. We believe this is reasonable given the widespread
    availability of Java virtual machines version 1.6 on diverse platforms.

  - Support for the SBML Qualitative Models package (qual) has been
    implemented, following the Version 1 Release 1 specification (17 May
    2013).

  - Support for the SBML Flux Balance Constraints package (fbc) has been
    implemented, following the Version 1 Release 1 spec. (11 February 2013).

  - Support for the SBML Hierarchical Model Composition package (comp) has
    been implemented, following the Version 1 Release 3 spec. (14 November
    2013).

  - Support for the SBML Layout package (layout) has been implemented,
    following the Version 1 Release 1 specification (13 August 2013).

  - (Experimental) support for the SBML Required Elements package (req) has
    been implemented, following the draft Version 1 Release 0.5 spec. (30 May
    2013).

  - (Experimental) support for the SBML Spatial Processes package (spatial)
    has been implemented, following the working specification Version 1
    release 0.80 (latest version is release 0.85, 13 December 2013).

  - (Experimental) support for the SBML Groups package (groups) has been 
    implemented, following the draft Version 1 Release 0.4 spec. (17 May 2013).

  - (Experimental) support for the SBML Render package (render) has been
    implemented, trying to follow the SBML Level 2 specification.

  - Support for arrays, distrib and multi is not yet available but planned
    for the final JSBML 1.0 release.

  - A ParserManager class has been implemented to register SBML Level 3
    package parsers. This manager also allows JSBML to enable automatically
    the packages into the SBMLDocument so there is no longer a need to set
    the package namespace or the 'required' attribute manually.

  - Reading more than one RDF Description element in the RDF annotation
    block (the official SBML annotation scheme) is now supported. The whole
    annotation is parsed first into an XMLNode object, and then the official
    SBML annotations are extracted, if any are found.

  - A new convenience method, removeFromParent, has been added to SBase and
    TreeNodeWithChangeSupport. It offers the ability to remove any element
    from the JSBML tree from its parent.

  - An alternative class to parse mathematical formula has been implemented. 
    It is named FormulaParserLL3 and it respects the same syntax as the 
    libSBML L3 parser.

  - Some filter methods have been added to TreeNodeWithChangeSupport (and
    thus to SBase as well); they allow for recursive queries in the entire
    data structure.

  - A new filter, XMLNodeFilter, is available to facilitate the manipulation
    of XMLNode. It allow to do a search similar to the DOM
    Element#getElementsByTagNameNS(String, String) method on XMLNode.

  - There is now a new ProgressListener utility class that enables tracking
    the progress of computationally intensive tasks.

  - For better localization support and to separate source code from user
    messages, a new resource manager has been implemented and is being used
    in some JSBML classes already.

  - The CVTerm API has been improved with new methods that should simplify
    the code needed to manipulate CVTerm objects. The javadoc documentation
    indicates which methods could be used to replace the existing ones.

  - The libSBMLio module for reading and writing SBML using libSBML as an
    input and output module has been revised and completely re-implemented.

  - The CellDesigner plugin module has been completely re-written and has
    become significantly more efficient. This facilitates the creation of
    CellDesigner plugins for existing JSBML-based programs with very little
    overhead.

  - MAVEN support has been improved. Thanks to Aurélien Naldi for providing
    several POM.xml files for JSBML.

  - Syntax checking for SBML 'metaid' attributes have been added to the
    setMetaId method.

  - SBasePlugin now implements the TreeNodeWithChangeSupport interface.

  - Validation checks for e-mail addresses have been added to Creator.

  - A quick Hash-based search method to access elements in a model based on
    their identifier is now available. In the same way, any SBase can be
    found in the SBMLDocument based on its metaId in constant time.

  - In order to make the API more Java-like, for each getNumXXX method a
    corresponding getXXXCount method has been created.

  - The userObject in ASTNode has been replaced by a more general map of
    userObjects that is located in TreeNodeWithChangeSupport. In this way,
    any JSBML object that is part of the SBML tree can now be attached with
    user- defined objects. These will not be written to SBML later on.

  - The Unit class has the new methods removeMultiplier: it merges multiplier
    and scale. This can only be done if the logarithm to the base 10 of the
    multiplier is nearly an integer number, i.e., if the distance to the next
    integer falls below the ensured double precision.

  - The multiplyWith and divideBy methods in UnitDefinition have been
    improved to better combine units by using the merge method.

  - The Species class now ensures that its spatial size units can only be set
    if its surrounding compartment does not have zero dimensions and if its
    hasOnlySubstanceUnits attribute is false. This behavior is specified by
    the SBML documents for Level 2 Versions 1 and 2, but was not yet
    implemented.

  - When deriving its UnitDefinition, the Species class searches for a
    suitable existing unit from the model, rather than creating a new
    instance. To this end, inherited Level 3 units from the Model are now
    taken into account.

  - A new JUnit test class helps to check if simplification and merging of
    Units and UnitDefinitions works correctly.

* Bug Fixes:

  - When writing SBML to files, the streams created by JSBML were not closed
    in all cases. Fixed. Thanks to Akira Funahashi for pointing this out.

  - Appending text in an XMLToken did not have an effect if there was already
    some text. Fixed.

  - When trying to remove a modifier, reactant, or product from a reaction using
    the id of the element, where no such element was present before, a
    NullPointerException was thrown. This has been solved. Thanks to Curtis
    Madsen for detecting this bug.

  - In the Reaction class, the setReversible method accepted Boolean, instead
    of boolean. This has been changed.

  - Removed old note comment from the SubModel class.

  - JSBML did not write negative infinity into SBML files correctly. Fixed.

  - Detected and fixed a source of possible NullPointerExceptions within the
    hashCode method in ASTNode.

  - There was a bug in KineticLaw that prevented a correct iteration through
    all children. Instead of looking at the listOfLocalParameters, it
    returned the math element twice (if there was one).

  - When cloning entire models or SBMLDocuments, JSBML assigned the identical
    TreeNodeChangeListeners to the new data structure. This could lead to
    unexpected behavior because listeners were receiving change events from
    data structures to which these have not been explicitly added by a user.

  - Cloning caused problems because in some cases the recursive method that
    registers all local parameters and other entities with an id did not do a
    full recursion. Hence, some entities could not be found in the model
    later on when searching for a certain id.

  - When calling the method setSymbol in InitialAssignment with an instance
    of Variable as argument, the listeners were sometimes not correctly
    informed about changes.

  - The setParent method caused an incorrect and in many cases duplicate call
    of the nodeAdded method in TreeNodeChangeListeners. Instead of
    registering a property change (the parent link) the parent node has been
    passed to the nodeAdded method.

  - QuantityWithUnit did not fire update events when changing its value.

  - The unsetUnits method in ExplicitRule now correctly notifies the listeners.

  - The Model class lacked an unsetSubstanceUnits method. Thanks to Robert
    Byrnes for reporting this problem.

  - The Compartment class did not override the method getDerivedUnitDefinition
    from its superclass. Hence, Level 3 units could not be inherited from the
    model. Fixed.

  - Merging of Units did not correctly work in cases where the scales of two
    units could not be evenly merged: just one of both was used and the other
    was ignored. In the corrected version, both scales removed represented by
    a double-valued multiplier in the merged unit. In particular, this error
    occurred in the addition or subtraction of ASTNodes.

  - If during the simplification of UnitDefinitions a dimensionless unit was
    created by canceling terms, its scale was always set to 0 and multiplier to
    1, which could lead to errors. Additional checks now yield correct results.

  - The units of formulae containing Advogadro's number could not be derived. 	
    This is now fixed.

  - In some cases, containsUndeclaredUnits() in ASTNode yielded incorrect
    results. These are now fixed.

  - [Tracker item #6]: a NullPointerException was returned when using the
    method Model.findLocalParameters(String) unless at least one
    LocalParameter was registered in the model.

  - It was sometimes possible to unregister elements from a model using
    cloned elements that did not belong to the model.

  - SBMLValidator fixed according to the changes made on the sbml.org
    validator to add package information.

* Known issues:

  - If you don't have the code handling a package in your classpath,
    jsbml-1.0-beta1 will loose the package elements, they won't be kept
    in memory or went writing back the model to a file. The final release
    should be able to handle unknown packages.


======================================================================
Version 0.8.0 (24-08-2012)
======================================================================

* New Features:
  
  - The new specialized EventObject TreeNodeRemovedEvent has been
    implemented in order to make sure that deleted nodes do no longer
    have a pointer to their previous parent within the tree. At the
    same time, it is still possible to identify their previous
    location using the field in the new EventObject.

* Bug Fixes:
	
  - When cloning ASTNodes the direct pointer to referenced variables 
    (instances of CallableSBase) are set to null in the cloned object.
    However, we have to keep the identifier of the variable. Otherwise
    it might happen that the cloned Tree completely looses its
    meaningfulness. So far, when calling setVariable with an instance of
    CallableSBase only the pointer to the object was stored, but not the
    id. This has been corrected now.
    
  - When creating an ASTNode with a direct pointer to a CallableSBase,
    no reference to the id of the SBase has been stored.   
  
  - There is now a rounding correction for multipliers when merging units
    that tries to shift multipliers to the scale of a unit. This can only
    be done if the logarithm to the base 10 of the multiplier is nearly
    an integer number. Nearly means that the distance to the next integer
    is not larger than the ensured double precision.

  - [Tracker item #3511439] : The cloning of a Species was not copying the
    speciesType attribute. Thanks to Thomas Bernard who noticed and reported
    the problem.

  - The units attribute on ASTNode was sometime not written out and the sbml
    namespace was sometime missing from the mathML. It should be fine now.

* Known issues :

  - Reading of more than one RDF Description elements in the RDF annotation 
    block (the official SBML annotation scheme) is not supported at the 
    moment. Planned for the next major release. 
  - The RDF block at the top level of the annotation is considered to be the
    official SBML annotation.
  - SBML L3 packages are not supported and not kept.  
  
======================================================================
Version 0.8-rc2 (12-04-2012)
======================================================================

* User visible changes :

  - Major improvements of the reading and writing speed for large models.

* Bug Fixes:

  - It was forgotten to implement an unsetSubstanceUnits method on the Model
    class. Thanks to Robert Byrnes for reporting this problem.
  
  - In the Reaction class, the setReversible method only accepted Boolean,
    no boolean.
  
  - Several bug fixes and improvements to units derivation, merging and
    simplifying.
    
  - There was a bug in KineticLaw that prevented a correct iteration
    through all child elements.
    
  - When cloning JSBML wrongly assigned the same TreeNodeChangeListeners
    to the new data structure. Now, the TreeNodeChangeListeners are not
    copied when cloning a JSBML object.
    
  - The clone methods were corrected to register properly all entities
  	with an id.
    
  - QuantityWithUnit did not fire update events when changing its value
    because of an incorrect comparison between the previous and the new
    value.
    
  - Corrected a bug that made it impossible to set a kinetic law with an 
    existing list of local parameters as the new kinetic law in a 
    different reaction.
    
  - Meike Aichele detected and fixed the problem that some Listeners were 
    set too soon in the Model class, so that no Event was detected.
  
  - One rdf namespace declaration was missing when creating an History 
    element from scratch.
     
  - [Tracker item #3511439] : Model.getSpeciesType(String) was 
    corrected and it's speed improved. Thanks to Thomas Bernard who noticed
    and reported the problem.
  
  - Annotation elements that are missing a proper namespace declaration will
    be correctly read and not considered as improper SBML elements.
  
  - [Tracker item #3487517] : If one empty rdf Bag element was present in an
    annotation read by jsbml, an XML element was not closed properly, 
    resulting in an invalid generated SBML. Thanks to Anna Zhukova to report
    this problem.
  
  - [Tracker item #3434930] : The parsing of a Message as an XML String was
    incorrect as the method was expecting only a notes element to enclose
    the HTML.
  
  * Known issues :

  - Reading of more than one RDF Description elements in the RDF annotation 
    block (the official SBML annotation scheme) is not supported at the 
    moment. Planned for the next major release. 
  - The RDF block at the top level of the annotation is considered to be the
    official SBML annotation.
  - SBML L3 packages are not supported and not kept.  

======================================================================
Version 0.8-rc1 (14-12-2011)
======================================================================

* New Features:

  - NamedSBase has now the new method isIdMandatory in order to check
    whether the identifier of this element must be defined in the SBML
    representation.

  - The SBMLReader can now be used in connection with a 
    TreeNodeChangeListener. This allows users to keep track about the
    parsing process of XML files.
  
  - The new interface TreeNodeWithChangeSupport separates manipulation
    of change listeners from SBase, allowing multiple implementations.

  - The JavaDoc has been extended: It now also includes a statement
    when ever a method might throw a PropertyNotAvailableException.
    This class was called PropertyNotAvailableError before. In order
    to avoid confusion with Java Errors, this class has been renamed.
    Also the super type XMLError has been renamed to XMLException in
    order to come closer to the default Java naming convention in
    JSBML.
  
  - ASTNode has now a userObject attribute through which any 
    computation result or other additional information can be stored
    in the tree.
  
  - Restructured the extension package: There is now one extra 
    directory including a specialized source folder for each extension. 
    The core does no longer contain the ext package. Besides the source
    folder, there is also an extra doc folder that contains the 
    description of the type hierarchy of the implementation for this
    package.
    
  - Data structures for the spatial extension are implemented.
  
  - When accessing the i-th child of an AbstractSBase, it is no longer
    necessary to sort all extension packages as these are now 
    maintained in a sorted data structure.
  
  - The compile method in ASTNode has become more efficient now by
    restructuring its case distinctions.
  
  - Implemented the hashCode method for all elements belonging to the
    hierarchical SBML data structure (SBase, Annotation, ASTNode 
    etc.).
  
  - In order to get closer to the Java package structure, the change 
    listener for SBML objects is now located in the package 
    org.sbml.jsbml.util package. For the sake of more similarity to
    standard Java classes and a greater flexibility, the 
    SBaseChangedListener has been renamed to TreeNodeChangeListener
    and now extends the PropertyChangeListener in the package 
    java.beans. The same holds for the corresponding
    SBaseChangedEvent that is now called TreeNodeChangeEvent and also
    located in the package org.sbml.jsbml.util. Furthermore, all 
    elements of the SBML tree, including annotations, ASTNodes etc.
    now make use of change events and notify their listeners, which
    are now organized in a list instead of a set to make sure that
    they are always called in the same order. The management of these
    listeners is now performed in AbstractTreeNode, the new super 
    class for all recursive elements in JSBML. Thanks to Sarah Rachel
    MÃ¼ller vom Hagen to support this effort.
  
  - Notes, annotations, and extension packages are now part of the tree
    representation of the JSBML data structure.
    
  - The new super class AnnotationElement has been introduced to gather
    all those elements that can be used to annotate instances of SBase.
      
  - The new Type AbstractTreeNode implements basic recursive functions
    and is now used as the new super class for ASTNode, AbstractSBase,
    the new type AnnotationElement, and XMLToken (as the super class of
    XMLNode). In this way, recursion and iteration over child elements
    within the SBML data structure now follow a unified structure and
    can be accessed uniquely within all such elements, including 
    annotations and extensions. Furthermore, the new interface
    TreeNodeWithChangeSupport that is extended by SBase and 
    AbstractTreeNode gathers methods to add, remove, and notify listeners
    about changes within the data structure (such as adding, removing
    or exchanging child nodes, or change of any attributes).
    
  - A simple implementation of AbstractTreeNode, the class 
    TreeNodeAdapter has been implemented that contains some methods
    also present in the interface MutableTreeNode. However, it does not
    fully implement this interface because there are also some methods
    that are not suitable for JSBML purposes. The TreeNodeAdapter works
    as a wrapper class for elements that are part of the SBML data 
    hierarchy but that do not implement the TreeNode interface. With this
    adapter, it is possible to include the list of Creators within a 
    history that is not a ListOf object, or the list of CVTerms in an
    annotation in the hierarchy.
    
  - Sebastian FrÃ¶hlich provided some contributions to the parser for the 
    SBML level 3 layout extension package. JSBML now  supports experimental
    reading for this package. The full stable API for reading/writing of
    SBML level 3 packages is planned for the next release of JSBML.

  - The SBMLReader now follows the factory pattern by providing static
    methods to directly read SBML content from Files, InputStreams, or
    Strings. Thanks to Martin GrÃ¤ÃŸlin for this idea in tracker issue 
    #3300433.

  - JSBML now provides a Maven script file. Thanks to Igor Rodchenkov
    for contributing this file.

  - Where necessary, initDefaults has been complemented with an 
    additional method, for which the level/version combination can be
    directly specified, i.e., it is now possible for those elements to
    set their defaults as these would be for the specified 
    level/version combination.

  - A basic check to avoid setting the same meta identifier more than
    once has been implemented. It maintains a set of metaIds on the
    SBMLDocument. Thanks to Stefan Hoops for making clear that such a 
    check is mandatory.
    
  - A check for duplicate identifiers has been implemented. JSBML now
    registers all elements that might have an identifier in hashes
    mapping the id to the object itself. Different rules depending on
    the specifications of the levels and versions of SBML ensure that
    only allowable identifiers can be accepted. To this end, the new
    interface UniqueNamedSBase tags all those elements whose id is
    intended to be unique within one model.
        
  - When adding an element with undefined Level/Version combination
    or at least with undefined Version to an element whose L/V 
    configuration has been defined, JSBML now recursively updates this
    configuration for the child element. In case of differing L/V
    configurations an exception will be thrown on adding the element.
    
* Bug Fixes:

  - Corrected mistakes in the org.sbml.jsbml.util.Maths class, where
    some trigonometric functions were incorrectly calculated.

  - When writing SBML Level 1 the name attribute (which has become the
    id attribute in later SBML versions) was never written because JSBML
    uses the id only as an internal representation. The check for 
    Level 1 name attributes was not correct.

  - In SBML Level 1 MathML code was written in KineticLaws although
    in this SBML version formulas are represented in infix notation only.

  - When creating a Parameter object using the constructor that takes
    a LocalParameter as its only argument, a call to setConstant(true)
    was always performed, irrespective of the fact that for Level = 1
    models this method throws a PropertyNotAvailableError. Thanks to
    Sarah R. MÃ¼ller vom Hagen for reporting this problem.

  - [Tracker issue #3323886]: The units of Species were not derived
    correctly because if the units were not explicitly defined, the method
    getDerivedUnitsInstance() always returned the predefined unit 
    "substance", or null for models in Level 3. Now it considers the
    surrounding compartment, the hasOnlySubstanceUnits, and spatialSizeUnits
    attributes depending on the Level/Version configuration.

  - Cloning of SBMLDocuments was not done correctly: The set of already 
    existing metaIds was initialized after adding the model to the document.
    In the mean time, there could already be access to this set. 

  - When reading SBML content from a file or writing JSBML data structures
    as SBML to a file, the SBMLReader/Writer did not close streams after 
    finishing. This caused that sometimes files could not be opened by other
    programs although writing was already done. Unfortunately, by correcting
    this bug, an IOException needs to be thrown, but previously, a 
    FileNotFoundException was already sufficient. Hence, users need to catch
    a different exception now when reading/writing SBML.

  - Renamed the getters for Creator and ModifiedDates in History to
    getListOfCreators() and getListOfModifiedDates (before it was spelled
    without "Of", for the sake of having equal names for all such getters.

  - If the Level/Version combination was not set, the identifier of
    NamedSBases was sometimes not written to an SBML file.

  - [Tracker issue #3300430]: In some condition, the JVM was not checking
    the type of an Object before adding it to a typed variable which later
    lead to some ClassCastException that prevented jsbml to save some 
    models. Thanks to Martin Graesslin to report the bugs alongside some 
    unit tests and to help resolved it.
    
  - [Tracker issue #3300490] : The method SBase.getNotesString() was 
    returning null instead of the empty string, as stated in the java 
    documentation. This has been corrected and the empty String is returned
    now. Thanks to Martin Graesslin to report the bugs alongside some 
    unit tests.
       
  - The SBML level 1 rules were incorrectly read, now when the 'type' 
    attribute is set to 'rate', a RateRule is properly created.     

  - The SubModel utility class was fixed and an example of how to use it 
    added.

  - Corrected the clone method for listOf objects. The typeOfList was
    not set in the clone so it was not possible to save a model that contain
    a cloned list.
    
  - [Tracker item #3306135] : the handling to the number NaN (NotANumber) has
    been improved and the correct mathML element is created when saving the
    formula to XML. 
  
  - The attribute denominator on level1v2 SpeciesReferences was never read.
    Thanks to the numerus models in the SBML Test Suite to help us detecting 
    this problem.
  
* Known issues :

  - Reading of more than one RDF Description elements in the RDF annotation 
    block (the official SBML annotation scheme) is not supported at the 
    moment. Planned for the next major release. 
  
======================================================================
Version 0.8-b2 (18-04-2011)
======================================================================

* New Features:

  - The new interface CallableSBase ensures that only instances of this
    type may be referenced in ASTNodes. In this way, it is no longer
    possible to refer to Events from ASTNodes.
    
  - Additional type of error: abstract PropertyError with its two sub-
    types PropertyNotAvailableError and PropertyUndefinedError. The 
    PropertyNotAvailableError one indicates that a certain property
    of an SBase is not defined for its current Level/Version 
    combination, whereas the latter one indicates that the value 
    belonging to a mandatory property, for which there is no default
    value has not been defined. At least in the Trigger class, 
    PropertyUndefinedErrors are now thrown when attempting to access
    the value of the persistent or initialValue attribute for models
    with Level >= 3 and an undeclared value. Thanks to Roland Keller
    and Lucian Smith for pointing out this necessity.    

* Bug Fixes:

  - The class Maths, a compilation of useful mathematical functions,
    provided an implementation of the factorial function that took
    a double as its argument. However, for this purpose, the Gamma
    function must be implemented. We changed this function to take 
    integer values only.

  - A NullPointerException could occur when deriving the unit of an
    expression containing a call to the factorial function. Thanks
    to Roland Keller for reporting this. 

  - [Tracker issue #3208887] : When trying to access the previously
    set value for  UseValuesFromTriggerTime it was possible that a
    NullPointerException could occur due to the attempt to clone this
    element. This has been fixed. Thanks to Sebastian FrÃ¶hlich for 
    reporting this.

  - [Tracker issue #3208903] : Priority elements were 
    properly read and written. Thanks to Sebastian FrÃ¶hlich for 
    reporting this.

  - [Tracker issue #3204275] : It was impossible to set the size of a 
    compartment for models in Level 3 if the spatial dimensions was 
    undefined, i.e., NaN. Since it is only prohibited to set the size 
    if the dimensions equal 0, we provided another test in the set 
    method in Compartment to allow for this.
  
  - Corrected a typo in the method SBO.getAntisenseRNA(), the method was
    always returning -1. It is now returning the correct value (317), thanks
    to Anushya Muruganujan for reporting this.

  - [Tracker issue #3175906] : Removed plenty of unused methods in the 
    Annotation class that were dealing with writing the annotation as XML. 
    Everything is handle now in the stax SBMLWriter.
    The getAnnotationString method was not present in the SBase interface, 
    it is now added and should be the only method used to get the Annotation
    as XML. 
    
  - [Tracker issue #3175911] : compartment.spatialDimensions type changed 
    to double to be consistent with the SBML level 3 specifications, as a 
    consequence the return type of the getSpatialDimensions as been changed.
    
  - [Tracker issue #3176548] : id containing underscores were not parsed
    properly by the infix formula parser, this is now fix and any valid SId
    should work. Thanks to Goksel Misirli for reporting this.
    
  - [Tracker issue #3175833] : annotation tags were not properly closed when
    a mathML element was present on the same element.
    Thanks to Goksel Misirli for reporting this.  
  
  - A new interface, CallableSBase, was introduced to encompass all the 
    different SBML elements that can be referenced inside a mathML 'ci'
    element. As a consequence, some methods signature have changed.

  - [Tracker issue #3175833] : in some cases, an empty annotation was 
    written if a metaid was defined. Some methods were added to the 
    Annotation class to test which part of the annotation is defined.

  - [Tracker issue #3196284] : the unit exponent was incorrectly always read
    as an integer. This has been corrected and if the level of the model is 
    3, the exponent is read as a double. 

  - [Tracker issue #3196638] : the message constraint was not properly read,
    it was stored in the notes instead of the message ! This is now 
    corrected, thanks to the person who reported that.

  - Corrected several bugs in the SBMLValidator and implemented properly all
    the classes related to SBMLdocument.checkConsistency() so that is works 
    as expected.
    
  - [Tracker issue #3199792] : localParameter were not parsed correctly in 
    SBML level 3. Thanks for the persons who reported it.
      
  - [Tracker issue #3216183] : all the XML elements that were belonging to
    the RDF namespace were sent to a parser that understood only the 
    specific SBML RDF annotations. This as been fixed and we can now have
    some RDF elements anywhere in the annotation.   
      
* Known issues :

  - SBML level 1 rules when the 'type' attribute is equal to 'rate' are 
    created as AssignmentRule instead of RateRule.
  
  - The support for the mathML elements notanumber and infinity is not 
    complete.
  
  - Reading of more than one RDF Description elements in the RDF annotation 
    block (the official SBML annotation scheme) is not supported at the 
    moment. 
  
===========================================================================
Version 0.8-b1 (04-02-2011)
===========================================================================

This the first beta release of JSBML 0.8, JSBML is a community-driven
project to create a free, open-source, pure Java library for reading,
writing, and manipulating SBML files and data streams. It is an
alternative to the mixed Java/native code-based interface provided in 
libSBML.

For more details, please visit http://sbml.org/Software/JSBML

* Bug Fixes:
  - [Tracker issue #3137967]
    Namespaces in the sbml element were incorrectly parsed, some known 
    namespaces like html were not kept for examples. This is fixed, thanks 
    to Paulo Maia for reporting this.

* Known issues:
  - problem of output indentation for notes, when there is several top
    level elements.
  
  - the method SBMLDocument.checkConsistency() is not working. There is a 
    problem in the parsing of the XML return by the sbml.org validator.

  - dependency on the sbml.org validator which might not be robust
    enough (you cannot submit a file bigger 15MB for example).
    It would be better to be able to install your own localy or at
    least have several mirrors. We will have to see how it performs.

  - in a mathML block, if one 'ci'element is suppose to represent a
    functionDefinition but the id is not a valid id, the type of the
    ASTNode is not set properly, so there are some errors when trying to
    use the corresponding ASTNode tree. (cf model generated by COBRA) 

---------------------------------------------------------------------------
$URL$
Last Modified: $Date$
Last Modified By: $Author$

The line should not have more than 76 characters to avoid them being put
over two lines when sending the release email.

The following is for [X]Emacs users.  Please leave in place.
Local Variables:
fill-column: 77
End:
---------------------------------------------------------------------------
