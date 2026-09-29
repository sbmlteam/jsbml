/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2022 jointly by the following organizations:
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
package org.sbml.jsbml.ext.comp.util;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.Delay;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.ExplicitRule;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.InitialAssignment;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.QuantityWithUnit;
import org.sbml.jsbml.RateRule;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.SimpleSpeciesReference;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.ext.SBasePlugin;
import org.sbml.jsbml.ext.comp.CompConstants;
import org.sbml.jsbml.ext.comp.CompModelPlugin;
import org.sbml.jsbml.ext.comp.CompSBMLDocumentPlugin;
import org.sbml.jsbml.ext.comp.CompSBasePlugin;
import org.sbml.jsbml.ext.comp.Deletion;
import org.sbml.jsbml.ext.comp.ExternalModelDefinition;
import org.sbml.jsbml.ext.comp.ModelDefinition;
import org.sbml.jsbml.ext.comp.Port;
import org.sbml.jsbml.ext.comp.ReplacedBy;
import org.sbml.jsbml.ext.comp.ReplacedElement;
import org.sbml.jsbml.ext.comp.SBaseRef;
import org.sbml.jsbml.ext.comp.Submodel;

/**
 * Flattens a hierarchical model of the comp package into a model without
 * submodels, following the specification of the comp package (version 1
 * release 3, section 4) and the flattening of libSBML:
 * <ol>
 * <li>Every {@link Submodel} is instantiated with a copy of the model it
 * references: a {@link ModelDefinition}, an {@link ExternalModelDefinition}
 * (resolved relative to the location of the document, see
 * {@link SBMLDocument#setLocationURI(String)}) or the main model. Submodels of
 * the instantiated models are instantiated recursively.</li>
 * <li>The elements of an instance get the prefix of the submodel ids of their
 * path, for example {@code sub1__sub2__S1}. Local parameters keep their ids.</li>
 * <li>{@link Deletion}s remove their target. A {@link ReplacedElement} removes
 * its target and redirects the references to it to the replacing element. A
 * {@link ReplacedBy} removes its parent, the target takes the id of the parent
 * and the references to the parent are redirected to the target.</li>
 * <li>The conversion factors of replaced elements and the time and extent
 * conversion factors of submodels are applied to the math. Nested time and
 * extent conversion factors are combined into a new parameter, for example
 * {@code sub1__timeconv_times_timeconv}.</li>
 * <li>All remaining elements are merged into the main model, the comp package is
 * removed.</li>
 * </ol>
 * The given document is not changed, the flat model is in a new document.
 */
public class CompFlatteningConverter {

  private static final Logger LOGGER = Logger.getLogger(CompFlatteningConverter.class.getName());

  /** Separator of the submodel ids in the prefix of the flattened ids. */
  public static final String PREFIX_SEPARATOR = "__";

  private static final String CORE = "core";

  /** Attributes of packages that reference SIds, as {@code prefix:name}. */
  private static final Set<String> PACKAGE_SID_REFERENCES = new java.util.HashSet<String>(java.util.Arrays.asList(
    "fbc:reaction", "fbc:lowerFluxBound", "fbc:upperFluxBound", "fbc:geneProduct", "fbc:associatedSpecies",
    "fbc:species", "fbc:activeObjective", "qual:compartment", "qual:qualitativeSpecies", "groups:idRef", "layout:species",
    "layout:reaction", "layout:compartment", "layout:speciesGlyph", "layout:reactionGlyph", "layout:originOfText",
    "layout:graphicalObject", "layout:reference", "distrib:var", "distrib:varLower", "distrib:varUpper",
    "fbc:lowerBound", "fbc:upperBound", "fbc:variable", "fbc:variable2"));

  /**
   * Attributes of packages that reference SIds on some elements only, as the
   * element name to {@code prefix:name}: {@code fbc:coefficient} is the SId
   * of a parameter on a user defined constraint component, but a number on a
   * flux objective.
   */
  private static final Map<String, Set<String>> ELEMENT_SID_REFERENCES = Collections.singletonMap(
    "userDefinedConstraintComponent", Collections.singleton("fbc:coefficient"));

  /** Attributes of packages that reference unit definitions, as {@code prefix:name}. */
  private static final Set<String> PACKAGE_UNIT_REFERENCES = new java.util.HashSet<String>(java.util.Arrays.asList(
    "distrib:units"));

  /** Attributes of packages that reference metaids, as {@code prefix:name}. */
  private static final Set<String> PACKAGE_METAID_REFERENCES = new java.util.HashSet<String>(java.util.Arrays.asList(
    "groups:metaIdRef", "layout:metaidRef"));

  /** Separator of the parameters combined into one conversion factor. */
  private static final String TIMES = "_times_";

  /**
   * A replacement of an element: the element replacing it and the conversion
   * factor of the replacement, if any.
   */
  private static final class Replacement {

    final SBase replacing;

    /** The instance in which the conversion factor is defined. */
    final ModelInstance scope;

    /** Id of the conversion factor, {@code null} if not set. */
    final String conversionFactor;

    Replacement(SBase replacing, ModelInstance scope, String conversionFactor) {
      this.replacing = replacing;
      this.scope = scope;
      this.conversionFactor = conversionFactor;
    }
  }

  /** A reference in the flat model: the new id and the conversion factor to divide by. */
  private static final class Reference {

    final String id;

    /** Conversion factor of the replacements, {@code null} if none. */
    final ASTNode conversionFactor;

    Reference(String id, ASTNode conversionFactor) {
      this.id = id;
      this.conversionFactor = conversionFactor;
    }
  }

  /** Elements per instance, in insertion order. */
  private static final class ListMultimap {

    private final Map<ModelInstance, List<SBase>> map = new IdentityHashMap<ModelInstance, List<SBase>>();

    void put(ModelInstance instance, SBase element) {
      List<SBase> elements = map.get(instance);
      if (elements == null) {
        elements = new ArrayList<SBase>();
        map.put(instance, elements);
      }
      elements.add(element);
    }

    List<SBase> get(ModelInstance instance) {
      List<SBase> elements = map.get(instance);
      return elements == null ? Collections.<SBase>emptyList() : elements;
    }

    void clear() {
      map.clear();
    }
  }


  /** A resolved {@link SBaseRef}: the element and the instance it belongs to. */
  private static final class Target {

    final ModelInstance instance;

    final SBase element;

    Target(ModelInstance instance, SBase element) {
      this.instance = instance;
      this.element = element;
    }
  }

  private final List<ModelInstance> instances = new ArrayList<ModelInstance>();

  private final Map<SBase, ModelInstance> owners = new IdentityHashMap<SBase, ModelInstance>();

  private final Set<SBase> deleted = Collections.newSetFromMap(new IdentityHashMap<SBase, Boolean>());

  private final Set<ModelInstance> deletedInstances =
    Collections.newSetFromMap(new IdentityHashMap<ModelInstance, Boolean>());

  private final Map<SBase, Replacement> replacements = new IdentityHashMap<SBase, Replacement>();

  /** Replacing elements of {@link ReplacedBy}s mapped to the element whose id they take. */
  private final Map<SBase, SBase> takesIdOf = new IdentityHashMap<SBase, SBase>();

  /** New ids of the elements, computed before any id is changed. */
  private final Map<SBase, String> newIds = new IdentityHashMap<SBase, String>();

  /** The documents read from sources, by URI; the flattened document by its location. */
  private final Map<String, SBMLDocument> sources = new java.util.HashMap<String, SBMLDocument>();

  private final Map<ModelInstance, ASTNode> timeConversionFactors = new IdentityHashMap<ModelInstance, ASTNode>();

  private final Map<ModelInstance, ASTNode> extentConversionFactors = new IdentityHashMap<ModelInstance, ASTNode>();

  /** Parameters of combined conversion factors, added to the flat model with the instance. */
  private final ListMultimap productParameters = new ListMultimap();

  /** Initial assignments of the combined conversion factors. */
  private final ListMultimap productAssignments = new ListMultimap();

  private final Set<String> productIds = new java.util.HashSet<String>();

  private Model flatModel;


  /**
   * Flattens the hierarchical model of the document.
   * <p>
   * {@link ExternalModelDefinition}s with relative sources are resolved against
   * the location of the document, which must be set with
   * {@link SBMLDocument#setLocationURI(String)}.
   *
   * @param document
   *        the document to flatten, not changed
   * @return a new document with the flat model, or a copy of the document if it
   *         does not use the comp package
   * @throws IllegalArgumentException
   *         if a model instantiates itself through its submodels
   */
  public SBMLDocument flatten(SBMLDocument document) {
    reset();
    SBMLDocument result = document.clone();
    if (document.isSetLocationURI()) {
      result.setLocationURI(document.getLocationURI());
    }
    if (!result.isSetModel() || !result.isPackageEnabled(CompConstants.shortLabel)) {
      LOGGER.warning("No model with the comp package in the document, nothing to flatten.");
      return result;
    }
    flatModel = result.getModel();
    if (result.isSetLocationURI()) {
      // an external model definition that refers back to the file itself
      sources.put(result.getLocationURI(), result);
    }

    ModelInstance root = new ModelInstance(null, null, flatModel, result, "");
    register(root);
    instantiateSubmodels(root, new ArrayList<String>());

    for (ModelInstance instance : instances) {
      collectDeletions(instance);
    }
    for (ModelInstance instance : instances) {
      collectReplacements(instance);
    }
    for (ModelInstance instance : instances) {
      computeNewIds(instance);
    }
    for (ModelInstance instance : instances) {
      if (!isDeleted(instance)) {
        computeConversionFactors(instance);
      }
    }
    // the math refers to the elements by their original ids
    for (ModelInstance instance : instances) {
      if (!isDeleted(instance)) {
        updateMath(instance);
      }
    }
    for (ModelInstance instance : instances) {
      if (!isDeleted(instance)) {
        removeReplacedAndDeleted(instance);
      }
    }
    for (ModelInstance instance : instances) {
      if (!isDeleted(instance)) {
        updateIds(instance);
      }
    }
    // the model definitions of the document would clash with the merged elements
    result.unsetExtension(CompConstants.shortLabel);
    for (ModelInstance instance : instances) {
      if (!instance.isRoot() && !isDeleted(instance)) {
        merge(instance);
      }
    }
    // after merging, so that the referenced elements are in the same model
    for (ModelInstance instance : instances) {
      if (!isDeleted(instance)) {
        updateReferences(instance);
      }
    }
    removeCompPackage(result);
    return result;
  }


  private void reset() {
    instances.clear();
    owners.clear();
    deleted.clear();
    deletedInstances.clear();
    replacements.clear();
    takesIdOf.clear();
    newIds.clear();
    sources.clear();
    timeConversionFactors.clear();
    extentConversionFactors.clear();
    productParameters.clear();
    productAssignments.clear();
    productIds.clear();
    flatModel = null;
  }


  private void register(ModelInstance instance) {
    instances.add(instance);
    for (SBase element : instance.elements) {
      owners.put(element, instance);
    }
  }


  //////////////////////////////////////////////////////////////////////////////
  // Instantiation

  /**
   * Instantiates the submodels of the instance recursively.
   *
   * @param instance
   *        the instance whose submodels are instantiated
   * @param path
   *        the models instantiated on the path to the instance, to detect cycles
   */
  private void instantiateSubmodels(ModelInstance instance, List<String> path) {
    CompModelPlugin plugin = compModelPlugin(instance.model);
    if (plugin == null || !plugin.isSetListOfSubmodels()) {
      return;
    }
    for (Submodel submodel : plugin.getListOfSubmodels()) {
      Model referenced = referencedModel(instance.document, submodel.getModelRef());
      if (referenced == null) {
        LOGGER.warning("The model '" + submodel.getModelRef() + "' of the submodel '" + submodel.getId()
          + "' in the " + instance + " could not be found, the submodel is not instantiated.");
        continue;
      }
      SBMLDocument document = referenced.getSBMLDocument() != null ? referenced.getSBMLDocument() : instance.document;
      String key = System.identityHashCode(document) + "#" + referenced.getId();
      if (path.contains(key)) {
        throw new IllegalArgumentException("The model '" + submodel.getModelRef() + "' of the submodel '"
          + submodel.getId() + "' instantiates itself.");
      }
      Model copy = referenced.clone();
      ModelInstance child = new ModelInstance(instance, submodel, copy, document,
        instance.prefix + submodel.getId() + PREFIX_SEPARATOR);
      instance.children.put(submodel.getId(), child);
      register(child);

      List<String> childPath = new ArrayList<String>(path);
      childPath.add(key);
      instantiateSubmodels(child, childPath);
    }
  }


  /**
   * The model with the id in the document: a model definition, the model of an
   * external model definition or the main model.
   *
   * @return the model, or {@code null} if there is none
   */
  private Model referencedModel(SBMLDocument document, String modelRef) {
    CompSBMLDocumentPlugin plugin = (CompSBMLDocumentPlugin) document.getExtension(CompConstants.shortLabel);
    if (plugin != null) {
      ModelDefinition modelDefinition = plugin.getModelDefinition(modelRef);
      if (modelDefinition != null) {
        return modelDefinition;
      }
      ExternalModelDefinition external = plugin.getExternalModelDefinition(modelRef);
      if (external != null) {
        try {
          SBMLDocument source = source(external.getAbsoluteSourceURI());
          return external.isSetModelRef() ? referencedModel(source, external.getModelRef()) : source.getModel();
        } catch (Exception e) {
          // XMLStreamException, IOException, URISyntaxException and missing location
          LOGGER.warning("The external model definition '" + modelRef + "' could not be resolved: " + e);
          return null;
        }
      }
    }
    if (document.isSetModel() && modelRef.equals(document.getModel().getId())) {
      return document.getModel();
    }
    return null;
  }


  /**
   * The document at the source, read once per flattening, so that a model
   * referenced several times is read once and a cycle of instantiations through
   * files is detected.
   */
  private SBMLDocument source(URI uri) throws Exception {
    SBMLDocument document = sources.get(uri.toString());
    if (document == null) {
      document = ExternalModelDefinition.readSource(uri);
      sources.put(uri.toString(), document);
    }
    return document;
  }


  private static CompModelPlugin compModelPlugin(Model model) {
    SBasePlugin plugin = model.getExtension(CompConstants.shortLabel);
    return plugin instanceof CompModelPlugin ? (CompModelPlugin) plugin : null;
  }


  private boolean isDeleted(ModelInstance instance) {
    for (ModelInstance i = instance; i != null; i = i.parent) {
      if (deletedInstances.contains(i)) {
        return true;
      }
    }
    return false;
  }


  //////////////////////////////////////////////////////////////////////////////
  // Deletions and replacements

  private void collectDeletions(ModelInstance instance) {
    CompModelPlugin plugin = compModelPlugin(instance.model);
    if (plugin == null || !plugin.isSetListOfSubmodels()) {
      return;
    }
    for (Submodel submodel : plugin.getListOfSubmodels()) {
      ModelInstance child = instance.children.get(submodel.getId());
      if (child == null) {
        continue;
      }
      for (Deletion deletion : submodel.getListOfDeletions()) {
        if (deleted.contains(deletion)) {
          // a deletion deleted by an enclosing model does not apply
          continue;
        }
        Target target = resolve(child, deletion);
        if (target == null) {
          LOGGER.warning("The target of the deletion '" + deletion + "' in the " + instance + " was not found.");
        } else if (target.element instanceof Submodel) {
          deletedInstances.add(target.instance.children.get(((Submodel) target.element).getId()));
        } else {
          deleted.add(target.element);
        }
      }
    }
  }


  private void collectReplacements(ModelInstance instance) {
    for (SBase element : instance.elements) {
      SBasePlugin extension = element.getExtension(CompConstants.shortLabel);
      if (!(extension instanceof CompSBasePlugin)) {
        continue;
      }
      CompSBasePlugin plugin = (CompSBasePlugin) extension;
      if (plugin.isSetListOfReplacedElements()) {
        for (ReplacedElement replacedElement : plugin.getListOfReplacedElements()) {
          if (replacedElement.isSetDeletion()) {
            // replaces an element that is deleted anyway
            continue;
          }
          Target target = resolveInSubmodel(instance, replacedElement, replacedElement.getSubmodelRef());
          if (target == null) {
            LOGGER.warning("The target of the replaced element '" + replacedElement + "' of '" + element.getId()
              + "' in the " + instance + " was not found.");
          } else if (target.element instanceof Submodel) {
            deletedInstances.add(target.instance.children.get(((Submodel) target.element).getId()));
          } else {
            String conversionFactor = replacedElement.isSetConversionFactor() ? replacedElement.getConversionFactor() : null;
            replacements.put(target.element, new Replacement(element, instance, conversionFactor));
          }
        }
      }
      if (plugin.isSetReplacedBy()) {
        ReplacedBy replacedBy = plugin.getReplacedBy();
        Target target = resolveInSubmodel(instance, replacedBy, replacedBy.getSubmodelRef());
        if (target == null) {
          LOGGER.warning("The target of the replaced by of '" + element.getId() + "' in the " + instance
            + " was not found.");
        } else {
          replacements.put(element, new Replacement(target.element, instance, null));
          // instances are visited from the main model down, so the outermost element gives the id
          if (!takesIdOf.containsKey(target.element)) {
            takesIdOf.put(target.element, element);
          }
        }
      }
    }
  }


  private Target resolveInSubmodel(ModelInstance instance, SBaseRef sBaseRef, String submodelRef) {
    ModelInstance child = instance.children.get(submodelRef);
    return child == null ? null : resolve(child, sBaseRef);
  }


  /**
   * Resolves the element the {@link SBaseRef} points to in the instance, following
   * ports and nested references into the instances of submodels.
   *
   * @return the target, or {@code null} if it does not exist
   */
  private Target resolve(ModelInstance instance, SBaseRef sBaseRef) {
    Target target;
    if (sBaseRef.isSetPortRef()) {
      Port port = instance.portSIds.get(sBaseRef.getPortRef());
      target = port == null ? null : resolve(instance, port);
    } else if (sBaseRef.isSetIdRef()) {
      target = target(instance, instance.sIds.get(sBaseRef.getIdRef()));
    } else if (sBaseRef.isSetUnitRef()) {
      target = target(instance, instance.unitSIds.get(sBaseRef.getUnitRef()));
    } else if (sBaseRef.isSetMetaIdRef()) {
      target = target(instance, instance.metaIds.get(sBaseRef.getMetaIdRef()));
    } else {
      target = null;
    }
    if (target != null && sBaseRef.isSetSBaseRef()) {
      if (!(target.element instanceof Submodel)) {
        return null;
      }
      ModelInstance child = target.instance.children.get(((Submodel) target.element).getId());
      return child == null ? null : resolve(child, sBaseRef.getSBaseRef());
    }
    return target;
  }


  private static Target target(ModelInstance instance, SBase element) {
    return element == null ? null : new Target(instance, element);
  }


  /**
   * The element that is left of the chain of replacements of the element.
   */
  private SBase replacing(SBase element) {
    SBase current = element;
    Set<SBase> seen = Collections.newSetFromMap(new IdentityHashMap<SBase, Boolean>());
    while (replacements.containsKey(current) && seen.add(current)) {
      current = replacements.get(current).replacing;
    }
    return current;
  }


  private boolean isRemoved(SBase element) {
    return deleted.contains(element) || replacements.containsKey(element);
  }


  //////////////////////////////////////////////////////////////////////////////
  // Ids

  private void computeNewIds(ModelInstance instance) {
    for (SBase element : instance.elements) {
      if (element.isSetId() && isRenamed(element)) {
        newIds.put(element, newId(element));
      }
    }
  }


  /**
   * The elements whose ids get the prefix: all but local parameters and the
   * elements of the comp package, which is removed.
   */
  private static boolean isRenamed(SBase element) {
    return !(element instanceof LocalParameter) && !(element instanceof Model)
        && !CompConstants.shortLabel.equals(element.getPackageName());
  }


  private String newId(SBase element) {
    SBase named = named(element);
    return owners.get(named).prefix + named.getId();
  }


  /**
   * The element whose id the element takes: the outermost element it replaces with
   * a {@link ReplacedBy}, else the element itself.
   */
  private SBase named(SBase element) {
    SBase named = element;
    Set<SBase> seen = Collections.newSetFromMap(new IdentityHashMap<SBase, Boolean>());
    while (takesIdOf.containsKey(named) && seen.add(named)) {
      named = takesIdOf.get(named);
    }
    return named;
  }


  private void updateIds(ModelInstance instance) {
    for (SBase element : instance.elements) {
      if (isRemoved(element)) {
        continue;
      }
      String newId = newIds.get(element);
      if (newId != null && !newId.equals(element.getId())) {
        element.setId(newId);
      }
      SBase named = named(element);
      if (named != element && named.isSetMetaId()) {
        // the replacing element of a replaced by takes the metaid of the replaced element
        element.setMetaId(owners.get(named).prefix + named.getMetaId());
      } else if (!instance.isRoot() && element.isSetMetaId()) {
        element.setMetaId(instance.prefix + element.getMetaId());
      }
    }
  }


  /**
   * The reference in the flat model for the SId in the instance.
   */
  private Reference reference(ModelInstance instance, String id) {
    return reference(instance, instance.sIds.get(id), id);
  }


  /**
   * The reference in the flat model for the element with the id in the instance.
   *
   * @param element
   *        the element, {@code null} if there is no element with the id
   */
  private Reference reference(ModelInstance instance, SBase element, String id) {
    if (element == null) {
      return new Reference(instance.prefix + id, null);
    }
    List<ASTNode> factors = new ArrayList<ASTNode>();
    SBase current = element;
    Set<SBase> seen = Collections.newSetFromMap(new IdentityHashMap<SBase, Boolean>());
    while (replacements.containsKey(current) && seen.add(current)) {
      Replacement replacement = replacements.get(current);
      if (replacement.conversionFactor != null) {
        factors.add(new ASTNode(reference(replacement.scope, replacement.conversionFactor).id));
      }
      current = replacement.replacing;
    }
    String newId = newIds.containsKey(current) ? newIds.get(current) : instance.prefix + id;
    return new Reference(newId, times(factors));
  }


  /**
   * The unit in the flat model for the unit in the instance.
   *
   * @return the unit, {@code null} if the unit is not defined
   */
  private String unitReference(ModelInstance instance, String units) {
    if (Unit.isPredefined(units, flatModel.getLevel())
        || Unit.Kind.isValidUnitKindString(units, flatModel.getLevel(), flatModel.getVersion())) {
      return units;
    }
    SBase unitDefinition = instance.unitSIds.get(units);
    if (unitDefinition == null) {
      // an undefined unit is kept, JSBML does not allow to set it
      return null;
    }
    SBase current = replacing(unitDefinition);
    return newIds.containsKey(current) ? newIds.get(current) : instance.prefix + units;
  }


  private static ASTNode times(List<ASTNode> factors) {
    if (factors.isEmpty()) {
      return null;
    }
    if (factors.size() == 1) {
      return factors.get(0);
    }
    return multiply(factors);
  }


  /**
   * A new node dividing the nodes. {@link ASTNode#frac(ASTNode, ASTNode)} is not
   * used, as it turns the numerator node into the division and keeps its
   * attributes, like the definition URL of a csymbol.
   */
  private static ASTNode divide(ASTNode numerator, ASTNode denominator) {
    ASTNode node = new ASTNode(ASTNode.Type.DIVIDE);
    node.addChild(numerator);
    node.addChild(denominator);
    return node;
  }


  /** A new node multiplying the nodes, see {@link #divide(ASTNode, ASTNode)}. */
  private static ASTNode multiply(ASTNode... factors) {
    return multiply(java.util.Arrays.asList(factors));
  }


  /**
   * A new node multiplying the factors. The factors of a factor that is a
   * product are added directly, as in libSBML.
   */
  private static ASTNode multiply(List<ASTNode> factors) {
    ASTNode node = new ASTNode(ASTNode.Type.TIMES);
    for (ASTNode factor : factors) {
      if (factor.getType() == ASTNode.Type.TIMES) {
        for (ASTNode child : new ArrayList<ASTNode>(factor.getChildren())) {
          node.addChild(child);
        }
      } else {
        node.addChild(factor);
      }
    }
    return node;
  }


  //////////////////////////////////////////////////////////////////////////////
  // Conversion factors

  /**
   * Computes the time and extent conversion factors of the instance: the product
   * of the conversion factor of its submodel and the one of the enclosing
   * instance. The product of two parameters is a new parameter {@code a_times_b}
   * with an initial assignment {@code a * b}.
   */
  private void computeConversionFactors(ModelInstance instance) {
    if (instance.isRoot()) {
      return;
    }
    Submodel submodel = instance.submodel;
    String time = submodel.isSetTimeConversionFactor()
        ? reference(instance.parent, submodel.getTimeConversionFactor()).id : null;
    String extent = submodel.isSetExtentConversionFactor()
        ? reference(instance.parent, submodel.getExtentConversionFactor()).id : null;
    timeConversionFactors.put(instance, product(instance, time, timeConversionFactors.get(instance.parent)));
    extentConversionFactors.put(instance, product(instance, extent, extentConversionFactors.get(instance.parent)));
  }


  /**
   * The product of the parameter and the conversion factor of the enclosing instance.
   *
   * @return the math of the product, {@code null} if both are not set
   */
  private ASTNode product(ModelInstance instance, String id, ASTNode enclosing) {
    if (id == null) {
      return enclosing;
    }
    if (enclosing == null) {
      return new ASTNode(id);
    }
    String productId = id + TIMES + enclosing.getName();
    if (!productIds.contains(productId)) {
      productIds.add(productId);
      Parameter parameter = new Parameter(productId, flatModel.getLevel(), flatModel.getVersion());
      parameter.setConstant(true);
      InitialAssignment assignment = new InitialAssignment(flatModel.getLevel(), flatModel.getVersion());
      assignment.setVariable(productId);
      assignment.setMath(multiply(new ASTNode(id), enclosing.clone()));
      productParameters.put(instance, parameter);
      productAssignments.put(instance, assignment);
    }
    return new ASTNode(productId);
  }


  //////////////////////////////////////////////////////////////////////////////
  // References

  /**
   * Updates the references of the elements of the instance to their ids in the
   * flat model, and applies the conversion factors.
   */
  private void updateMath(ModelInstance instance) {
    ASTNode time = timeConversionFactors.get(instance);
    for (SBase element : instance.elements) {
      if (isRemoved(element) || !(element instanceof MathContainer) || !((MathContainer) element).isSetMath()) {
        continue;
      }
      MathContainer container = (MathContainer) element;
      ASTNode math = updateMath(instance, container, container.getMath(), time);
      ASTNode assignmentFactor = assignmentFactor(instance, element);
      if (assignmentFactor != null) {
        math = multiply(math, assignmentFactor);
      }
      if (element instanceof RateRule && time != null) {
        math = divide(math, time.clone());
      } else if (element instanceof KineticLaw) {
        ASTNode rateFactor = rateFactor(instance);
        if (rateFactor != null) {
          // the factor comes first, as in libSBML
          math = multiply(rateFactor, math);
        }
      } else if (element instanceof Delay && time != null) {
        math = multiply(time.clone(), math);
      }
      if (math != container.getMath()) {
        container.setMath(math);
      }
    }
  }


  /**
   * The conversion factor to multiply the math of the element with, if it assigns
   * a value to a replaced element with a conversion factor.
   *
   * @return the factor, {@code null} if there is none
   */
  private ASTNode assignmentFactor(ModelInstance instance, SBase element) {
    String variable = null;
    if (element instanceof ExplicitRule && ((ExplicitRule) element).isSetVariable()) {
      variable = ((ExplicitRule) element).getVariable();
    } else if (element instanceof InitialAssignment && ((InitialAssignment) element).isSetVariable()) {
      variable = ((InitialAssignment) element).getVariable();
    } else if (element instanceof EventAssignment && ((EventAssignment) element).isSetVariable()) {
      variable = ((EventAssignment) element).getVariable();
    }
    return variable == null ? null : reference(instance, variable).conversionFactor;
  }


  /**
   * Updates the attributes of the elements of the instance that reference other
   * elements to the ids in the flat model.
   */
  private void updateReferences(ModelInstance instance) {
    if (instance.isRoot()) {
      updateModelReferences(instance);
    }
    for (SBase element : instance.elements) {
      if (!isRemoved(element) && !CompConstants.shortLabel.equals(element.getPackageName())) {
        updateAttributeReferences(instance, element);
        if (element instanceof MathContainer && ((MathContainer) element).isSetMath()) {
          updateMathUnits(instance, ((MathContainer) element).getMath());
        }
      }
    }
  }


  private void updateModelReferences(ModelInstance root) {
    if (flatModel.isSetConversionFactor()) {
      flatModel.setConversionFactor(reference(root, flatModel.getConversionFactor()).id);
    }
  }


  /**
   * Updates the attributes of the element that reference other elements.
   */
  private void updateAttributeReferences(ModelInstance instance, SBase element) {
    // the units of a species are its substance units
    if (element instanceof QuantityWithUnit) {
      QuantityWithUnit quantity = (QuantityWithUnit) element;
      String units = quantity.isSetUnits() ? unitReference(instance, quantity.getUnits()) : null;
      if (units != null && !units.equals(quantity.getUnits())) {
        quantity.setUnits(units);
      }
    }
    updatePackageReferences(instance, element);
    if (element instanceof Species) {
      Species species = (Species) element;
      if (species.isSetCompartment()) {
        species.setCompartment(reference(instance, species.getCompartment()).id);
      }
      if (species.isSetConversionFactor()) {
        species.setConversionFactor(reference(instance, species.getConversionFactor()).id);
      }
    } else if (element instanceof Reaction) {
      Reaction reaction = (Reaction) element;
      if (reaction.isSetCompartment()) {
        reaction.setCompartment(reference(instance, reaction.getCompartment()).id);
      }
    } else if (element instanceof SimpleSpeciesReference) {
      SimpleSpeciesReference speciesReference = (SimpleSpeciesReference) element;
      if (speciesReference.isSetSpecies()) {
        speciesReference.setSpecies(reference(instance, speciesReference.getSpecies()).id);
      }
    } else if (element instanceof ExplicitRule) {
      ExplicitRule rule = (ExplicitRule) element;
      if (rule.isSetVariable()) {
        rule.setVariable(reference(instance, rule.getVariable()).id);
      }
    } else if (element instanceof InitialAssignment) {
      InitialAssignment assignment = (InitialAssignment) element;
      if (assignment.isSetVariable()) {
        assignment.setVariable(reference(instance, assignment.getVariable()).id);
      }
    } else if (element instanceof EventAssignment) {
      EventAssignment assignment = (EventAssignment) element;
      if (assignment.isSetVariable()) {
        assignment.setVariable(reference(instance, assignment.getVariable()).id);
      }
    }
  }


  /**
   * Updates the units of the numbers in the math. Done after merging, as JSBML
   * checks that the unit definition exists in the model.
   */
  private void updateMathUnits(ModelInstance instance, ASTNode node) {
    String units = node.isSetUnits() ? unitReference(instance, node.getUnits()) : null;
    if (units != null && !units.equals(node.getUnits())) {
      node.setUnits(units);
    }
    for (int i = 0; i < node.getChildCount(); i++) {
      updateMathUnits(instance, node.getChild(i));
    }
  }


  /**
   * Updates the attributes of packages that reference SIds or metaids, of the
   * element if it belongs to a package and of the package plugins of the element.
   */
  private void updatePackageReferences(ModelInstance instance, SBase element) {
    if (!CORE.equals(element.getPackageName())) {
      updatePackageReferences(instance, element.writeXMLAttributes(), element, null);
    }
    for (SBasePlugin plugin : element.getExtensionPackages().values()) {
      if (!(plugin instanceof CompSBasePlugin)) {
        updatePackageReferences(instance, plugin.writeXMLAttributes(), null, plugin);
      }
    }
  }


  private void updatePackageReferences(ModelInstance instance, Map<String, String> attributes, SBase element,
    SBasePlugin plugin) {
    for (Map.Entry<String, String> attribute : attributes.entrySet()) {
      String name = attribute.getKey();
      String value;
      if (isSIdReference(name, element)) {
        value = reference(instance, attribute.getValue()).id;
      } else if (PACKAGE_METAID_REFERENCES.contains(name)) {
        value = metaIdReference(instance, attribute.getValue());
      } else if (PACKAGE_UNIT_REFERENCES.contains(name)) {
        value = unitReference(instance, attribute.getValue());
        if (value == null) {
          // an undefined unit is kept
          continue;
        }
      } else {
        continue;
      }
      int colon = name.indexOf(':');
      String prefix = name.substring(0, colon);
      String localName = name.substring(colon + 1);
      if (element != null) {
        element.readAttribute(localName, prefix, value);
      } else {
        plugin.readAttribute(localName, prefix, value);
      }
    }
  }


  /**
   * Sets the package attributes (with a prefix) of the element or plugin to the
   * given ones, with the references updated.
   */
  private void copyAttributes(ModelInstance instance, Map<String, String> attributes, SBase element,
    SBasePlugin plugin) {
    if (attributes == null) {
      return;
    }
    for (Map.Entry<String, String> attribute : attributes.entrySet()) {
      String name = attribute.getKey();
      int colon = name.indexOf(':');
      if (colon < 0 || name.startsWith("xmlns") || name.startsWith("xsi")) {
        continue;
      }
      String value = attribute.getValue();
      if (isSIdReference(name, element)) {
        value = reference(instance, value).id;
      } else if (PACKAGE_METAID_REFERENCES.contains(name)) {
        value = metaIdReference(instance, value);
      }
      String prefix = name.substring(0, colon);
      String localName = name.substring(colon + 1);
      if (element != null) {
        element.readAttribute(localName, prefix, value);
      } else {
        plugin.readAttribute(localName, prefix, value);
      }
    }
  }


  /**
   * Whether the package attribute of the element (or of a plugin of it, then
   * the element is {@code null}) references an SId.
   *
   * @param name
   *        the attribute, as {@code prefix:name}
   */
  private static boolean isSIdReference(String name, SBase element) {
    if (PACKAGE_SID_REFERENCES.contains(name)) {
      return true;
    }
    Set<String> references = (element != null) ? ELEMENT_SID_REFERENCES.get(element.getElementName()) : null;
    return (references != null) && references.contains(name);
  }


  /**
   * The reference in the flat model for the metaid in the instance.
   */
  private String metaIdReference(ModelInstance instance, String metaId) {
    SBase element = instance.metaIds.get(metaId);
    if (element == null) {
      return instance.prefix + metaId;
    }
    SBase current = replacing(element);
    return current.isSetMetaId() ? current.getMetaId() : instance.prefix + metaId;
  }


  /**
   * Updates the names in the math. Names of replaced elements with a conversion
   * factor are divided by it, the time is divided by the time conversion factor
   * and the delay of the delay function is multiplied with it.
   *
   * @return the updated math, which is a new node if the root node is replaced
   */
  private ASTNode updateMath(ModelInstance instance, MathContainer container, ASTNode node, ASTNode time) {
    for (int i = 0; i < node.getChildCount(); i++) {
      ASTNode child = node.getChild(i);
      ASTNode updated = updateMath(instance, container, child, time);
      if (updated != child) {
        node.replaceChild(i, updated);
      }
    }
    boolean function = container instanceof FunctionDefinition;
    switch (node.getType()) {
    case NAME:
      if (function) {
        return node;
      }
      String name = node.getName();
      Reference reference = mathReference(instance, container, name);
      if (reference == null) {
        return node;
      }
      // the name of a node bound to an element is the id of the element
      node.setVariable(null);
      node.setName(reference.id);
      ASTNode updated = node;
      if (reference.conversionFactor != null) {
        updated = divide(updated, reference.conversionFactor);
      }
      ASTNode rateFactor = rateFactor(instance);
      if (rateFactor != null && isReactionOf(instance, name)) {
        // the rate of a reaction in the units of the instance: its kinetic law in
        // the flat model is multiplied with the rate factor
        updated = divide(updated, rateFactor);
      }
      return updated;
    case FUNCTION:
      if (node.getName() != null) {
        node.setName(reference(instance, node.getName()).id);
      }
      return node;
    case NAME_TIME:
      if (time != null && !function) {
        return divide(node, time.clone());
      }
      return node;
    case FUNCTION_DELAY:
      if (time != null && !function && node.getChildCount() == 2) {
        node.replaceChild(1, multiply(time.clone(), node.getChild(1)));
      }
      return node;
    default:
      return node;
    }
  }


  /**
   * The factor of the kinetic laws of the instance: extent / time conversion factor.
   *
   * @return a new node, {@code null} if there are no conversion factors
   */
  private ASTNode rateFactor(ModelInstance instance) {
    ASTNode extent = extentConversionFactors.get(instance);
    ASTNode time = timeConversionFactors.get(instance);
    if (extent != null && time != null) {
      return divide(extent.clone(), time.clone());
    } else if (extent != null) {
      return extent.clone();
    } else if (time != null) {
      return divide(new ASTNode(1), time.clone());
    }
    return null;
  }


  /**
   * @return {@code true} if the id refers to a reaction of the instance that is
   *         not replaced
   */
  private boolean isReactionOf(ModelInstance instance, String id) {
    SBase element = instance.sIds.get(id);
    return element instanceof Reaction && owners.get(replacing(element)) == instance;
  }


  /**
   * The reference for a name in the math of the container. A name of a local
   * parameter of a kinetic law refers to the local parameter; if it is deleted,
   * the name refers to the global element with the id.
   *
   * @return the reference, {@code null} if the name refers to a local parameter
   *         that is kept
   */
  private Reference mathReference(ModelInstance instance, MathContainer container, String name) {
    if (container instanceof KineticLaw) {
      LocalParameter localParameter = ((KineticLaw) container).getLocalParameter(name);
      if (localParameter != null && !deleted.contains(localParameter)) {
        return replacements.containsKey(localParameter) ? reference(instance, localParameter, name) : null;
      }
    }
    return reference(instance, name);
  }


  //////////////////////////////////////////////////////////////////////////////
  // Removal and merging

  private void removeReplacedAndDeleted(ModelInstance instance) {
    for (SBase element : instance.elements) {
      if (isRemoved(element)) {
        remove(element);
      }
    }
  }


  private static void remove(SBase element) {
    TreeNode parent = element.getParent();
    if (parent instanceof ListOf<?>) {
      ListOf<?> listOf = (ListOf<?>) parent;
      listOf.remove(element);
      if (listOf.isEmpty() && (listOf.getParent() != null)) {
        // as libSBML, which does not write empty lists
        unsetChild(listOf.getParent(), listOf.getElementName());
      }
    } else if (parent != null && !unsetChild(parent, element.getElementName())) {
      LOGGER.warning("The replaced or deleted element '" + element + "' could not be removed.");
    }
  }


  /**
   * Unsets the child with the element name with its unset method, for example
   * {@code Event.unsetDelay()} for {@code delay}.
   *
   * @return {@code true} if the child was unset
   */
  private static boolean unsetChild(TreeNode parent, String elementName) {
    try {
      String unsetter = "unset" + Character.toUpperCase(elementName.charAt(0)) + elementName.substring(1);
      parent.getClass().getMethod(unsetter).invoke(parent);
      return true;
    } catch (ReflectiveOperationException e) {
      return false;
    }
  }


  /**
   * Adds the remaining elements of the instance to the flat model.
   */
  private void merge(ModelInstance instance) {
    Model model = instance.model;
    for (SBase parameter : productParameters.get(instance)) {
      flatModel.addParameter((Parameter) parameter);
    }
    for (SBase assignment : productAssignments.get(instance)) {
      flatModel.addInitialAssignment((InitialAssignment) assignment);
    }
    if (model.isSetListOfUnitDefinitions() && !model.getListOfUnitDefinitions().isEmpty()) {
      addAll(model.getListOfUnitDefinitions(), flatModel.getListOfUnitDefinitions());
    }
    if (model.isSetListOfFunctionDefinitions() && !model.getListOfFunctionDefinitions().isEmpty()) {
      addAll(model.getListOfFunctionDefinitions(), flatModel.getListOfFunctionDefinitions());
    }
    if (model.isSetListOfCompartments() && !model.getListOfCompartments().isEmpty()) {
      addAll(model.getListOfCompartments(), flatModel.getListOfCompartments());
    }
    if (model.isSetListOfSpecies() && !model.getListOfSpecies().isEmpty()) {
      addAll(model.getListOfSpecies(), flatModel.getListOfSpecies());
    }
    if (model.isSetListOfParameters() && !model.getListOfParameters().isEmpty()) {
      addAll(model.getListOfParameters(), flatModel.getListOfParameters());
    }
    if (model.isSetListOfInitialAssignments() && !model.getListOfInitialAssignments().isEmpty()) {
      addAll(model.getListOfInitialAssignments(), flatModel.getListOfInitialAssignments());
    }
    if (model.isSetListOfRules() && !model.getListOfRules().isEmpty()) {
      addAll(model.getListOfRules(), flatModel.getListOfRules());
    }
    if (model.isSetListOfConstraints() && !model.getListOfConstraints().isEmpty()) {
      addAll(model.getListOfConstraints(), flatModel.getListOfConstraints());
    }
    if (model.isSetListOfReactions() && !model.getListOfReactions().isEmpty()) {
      addAll(model.getListOfReactions(), flatModel.getListOfReactions());
    }
    if (model.isSetListOfEvents() && !model.getListOfEvents().isEmpty()) {
      addAll(model.getListOfEvents(), flatModel.getListOfEvents());
    }
    for (Map.Entry<String, SBasePlugin> entry : model.getExtensionPackages().entrySet()) {
      SBasePlugin plugin = entry.getValue();
      if (plugin instanceof CompSBasePlugin) {
        continue;
      }
      boolean newPlugin = flatModel.getExtension(entry.getKey()) == null;
      SBasePlugin flatPlugin = flatModel.getPlugin(entry.getKey());
      if (newPlugin) {
        // the main model does not use the package: take the attributes of the submodel
        copyAttributes(instance, plugin.writeXMLAttributes(), null, flatPlugin);
      }
      List<ListOf<?>> listsOf = new ArrayList<ListOf<?>>();
      for (int i = 0; i < plugin.getChildCount(); i++) {
        if (plugin.getChildAt(i) instanceof ListOf<?>) {
          listsOf.add((ListOf<?>) plugin.getChildAt(i));
        }
      }
      for (ListOf<?> listOf : listsOf) {
        ListOf<?> flatListOf = listOf(flatPlugin, listOf.getElementName());
        if (flatListOf == null) {
          LOGGER.warning("The elements of the list '" + listOf.getElementName() + "' in the " + instance
            + " could not be merged.");
          continue;
        }
        if (flatListOf.isEmpty()) {
          // for example the active objective of the list of objectives
          copyAttributes(instance, listOf.writeXMLAttributes(), flatListOf, null);
        }
        addAll(listOf, flatListOf);
      }
    }
  }


  /**
   * Moves the elements of the source to the target list. The elements are moved,
   * not copied, as the conversion refers to them by identity.
   */
  @SuppressWarnings("unchecked")
  private static <T extends SBase> void addAll(ListOf<?> source, ListOf<T> target) {
    for (SBase element : new ArrayList<SBase>(source)) {
      source.remove(element);
      target.add((T) element);
    }
  }


  /**
   * The list of the plugin with the element name, created with its getter if it
   * does not exist yet (for example {@code getListOfGeneProducts} for
   * {@code listOfGeneProducts}).
   */
  private static ListOf<?> listOf(SBasePlugin plugin, String elementName) {
    for (int i = 0; i < plugin.getChildCount(); i++) {
      TreeNode child = plugin.getChildAt(i);
      if (child instanceof ListOf<?> && elementName.equals(((ListOf<?>) child).getElementName())) {
        return (ListOf<?>) child;
      }
    }
    try {
      String getter = "get" + Character.toUpperCase(elementName.charAt(0)) + elementName.substring(1);
      Method method = plugin.getClass().getMethod(getter);
      Object listOf = method.invoke(plugin);
      return listOf instanceof ListOf<?> ? (ListOf<?>) listOf : null;
    } catch (ReflectiveOperationException e) {
      return null;
    }
  }


  /**
   * Removes the comp package from the flat model and its document.
   */
  private void removeCompPackage(SBMLDocument document) {
    removeCompExtensions(flatModel);
    CompModelPlugin plugin = compModelPlugin(flatModel);
    if (plugin != null) {
      // the ports are unregistered by the plugin, which is their IdManager
      plugin.unsetListOfPorts();
      plugin.unsetListOfSubmodels();
    }
    flatModel.unsetExtension(CompConstants.shortLabel);
    document.unsetExtension(CompConstants.shortLabel);
    document.disablePackage(CompConstants.shortLabel);
  }


  private static void removeCompExtensions(TreeNode node) {
    for (int i = 0; i < node.getChildCount(); i++) {
      TreeNode child = node.getChildAt(i);
      if (child instanceof SBase) {
        SBase sbase = (SBase) child;
        if (sbase.getExtension(CompConstants.shortLabel) != null) {
          sbase.unsetExtension(CompConstants.shortLabel);
        }
        removeCompExtensions(sbase);
      } else if (child instanceof SBasePlugin && !(child instanceof CompSBasePlugin)) {
        removeCompExtensions(child);
      }
    }
  }


  //////////////////////////////////////////////////////////////////////////////
  // External model definitions

  /**
  * Collects any {@link ExternalModelDefinition}s that might be contained in
  * the given {@link SBMLDocument} and transfers them into local
  * {@link ModelDefinition}s (recursively, if the external models themselves
  * include external models; in that case, renaming may occur).
  * <br>
  * The given {@link SBMLDocument} need have its locationURI set!
  * <br>
  * Opaque URIs (URNs) will not be dealt with in any defined way, resolve them
  * first (make sure all relevant externalModelDefinitions' source-attributes
  * are URLs or relative paths)
  *
  * @param document an {@link SBMLDocument}, which might, but need not, contain
  * {@link ExternalModelDefinition}s to be transferred into its local
  * {@link ModelDefinition}s. The locationURI of the given document need
  * be set ({@link SBMLDocument#isSetLocationURI})!
  * @return a new {@link SBMLDocument} without {@link
  * ExternalModelDefinition}s, but containing the same information as
  * the given one
  * @throws Exception if given document's locationURI is not set. Set it with
  * {@link SBMLDocument#setLocationURI}
  */
  public static SBMLDocument internaliseExternalModelDefinitions(
      SBMLDocument document) throws Exception {

    if (!document.isSetLocationURI()) {
      LOGGER.warning("Location URI is not set: " + document);
      throw new Exception(
          "document's locationURI need be set. But it was not.");
    }
    SBMLDocument result = document.clone(); // no side-effects intended
    ArrayList<String> usedIds = new ArrayList<String>();
    if (result.isSetModel()) {
      usedIds.add(result.getModel().getId());
    }

    CompSBMLDocumentPlugin compSBMLDocumentPlugin =
        (CompSBMLDocumentPlugin) result.getExtension(CompConstants.shortLabel);

    // There is nothing to retrieve:
    if (compSBMLDocumentPlugin == null || !compSBMLDocumentPlugin.isSetListOfExternalModelDefinitions()) {
      return result;
    } else {
      /** For name-collision-avoidance */
      for (ExternalModelDefinition emd : compSBMLDocumentPlugin.getListOfExternalModelDefinitions()) {
        usedIds.add(emd.getId());
      }

      for (ExternalModelDefinition emd : compSBMLDocumentPlugin.getListOfExternalModelDefinitions()) {
        // general note: Be careful when using clone/cloning-constructors, they
        // do not preserve parent-child-relations
        Model referenced = emd.getReferencedModel();
        SBMLDocument referencedDocument = referenced.getSBMLDocument();
        SBMLDocument flattened = internaliseExternalModelDefinitions(referencedDocument);
        // Guarantee: flattened does not contain any externalModelDefinitions, only local MDs
        // (and main model)
        // use this, and migrate the MDs into the current compSBMLDocumentPlugin
        StringBuilder prefixBuilder = new StringBuilder(emd.getModelRef());
        /** For name-collision-avoidance */
        boolean contained = false;
        do {
          contained = false;
          prefixBuilder.append("_");
          for (String id : usedIds) {
            contained |= id.startsWith(prefixBuilder.toString());
            if (contained) {
              break;
            }
          }
        } while (contained);
        String newPrefix = prefixBuilder.toString();

        CompSBMLDocumentPlugin referencedDocumentPlugin =
            (CompSBMLDocumentPlugin) flattened.getExtension(
                CompConstants.shortLabel);

        ListOf<ModelDefinition> workingList;
        if (referencedDocumentPlugin == null) {
          // This may happen, if the main model of a non-comp-file is referenced
          workingList = new ListOf<ModelDefinition>();
          workingList.setLevel(referenced.getLevel());
          workingList.setVersion(referenced.getVersion());
        } else {
          workingList = referencedDocumentPlugin.getListOfModelDefinitions().clone();
        }

        // Check whether the main model is needed; Do not internalise it, if not necessary
        boolean isMainReferenced = flattened.getModel().getId().equals(emd.getModelRef());
        for (ModelDefinition md : workingList) {
          if (isMainReferenced) {
            break;
          }
          CompModelPlugin cmp = (CompModelPlugin) md.getExtension(CompConstants.shortLabel);
          if (cmp != null) {
            for (Submodel sm : cmp.getListOfSubmodels()) {
              isMainReferenced |= flattened.getModel().getId().equals(sm.getModelRef());
            }
          }
        }

        if (isMainReferenced) {
          ModelDefinition localisedMain = new ModelDefinition(flattened.getModel());
          workingList.add(0, localisedMain);
        }

        for (ModelDefinition md : workingList) {
          ModelDefinition internalised = new ModelDefinition(md);
          // i.e. current one is the one directly referenced => take referent's place
          if (md.getId().equals(referenced.getId())) {
            internalised.setId(emd.getId());
          } else {
            internalised.setId(newPrefix + internalised.getId());
          }

          CompModelPlugin notYetInternalisedModelPlugin =
              (CompModelPlugin) internalised.getExtension(CompConstants.shortLabel);
          if (notYetInternalisedModelPlugin != null && notYetInternalisedModelPlugin.isSetListOfSubmodels()) {
            for (Submodel sm : notYetInternalisedModelPlugin.getListOfSubmodels()) {
              sm.setModelRef(newPrefix + sm.getModelRef());
            }
          }

          compSBMLDocumentPlugin.addModelDefinition(internalised);
        }
      }
      compSBMLDocumentPlugin.unsetListOfExternalModelDefinitions();
      return result;
    }
  }
}
