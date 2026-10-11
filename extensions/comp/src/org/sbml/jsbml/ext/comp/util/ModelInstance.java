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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.ext.SBasePlugin;
import org.sbml.jsbml.ext.comp.Port;
import org.sbml.jsbml.ext.comp.Submodel;

/**
 * One instantiation of a model during the flattening of a hierarchical model:
 * the main model, or a copy of the model a {@link Submodel} instantiates, with
 * the instances of its own submodels.
 * <p>
 * The instance indexes the elements of its model by the identifier namespaces of
 * the comp specification (SId, UnitSId, PortSId and metaid), so that the
 * {@link org.sbml.jsbml.ext.comp.SBaseRef}s and the references in the math of
 * the model can be resolved to elements.
 *
 * @author Matthias König
 * @since 1.7
 */
final class ModelInstance {

  /** The instance of the model containing the submodel, {@code null} for the main model. */
  final ModelInstance parent;

  /** The submodel this is an instance of, {@code null} for the main model. */
  final Submodel submodel;

  /** The model of this instance; a copy for all instances but the main model. */
  final Model model;

  /**
   * The document the model is defined in, used to resolve the model references
   * of the submodels of the model.
   */
  final SBMLDocument document;

  /** The prefix of the identifiers of this instance in the flat model. */
  final String prefix;

  /** Instances of the submodels of the model, by submodel id. */
  final Map<String, ModelInstance> children = new LinkedHashMap<String, ModelInstance>();

  /** Elements in the SId namespace, except local parameters, by id. */
  final Map<String, SBase> sIds = new HashMap<String, SBase>();

  /** Unit definitions (UnitSId namespace) by id. */
  final Map<String, UnitDefinition> unitSIds = new HashMap<String, UnitDefinition>();

  /** Ports (PortSId namespace) by id. */
  final Map<String, Port> portSIds = new HashMap<String, Port>();

  /** Elements by metaid. */
  final Map<String, SBase> metaIds = new HashMap<String, SBase>();

  /** All elements of the model, in document order. */
  final List<SBase> elements = new ArrayList<SBase>();

  ModelInstance(ModelInstance parent, Submodel submodel, Model model, SBMLDocument document, String prefix) {
    this.parent = parent;
    this.submodel = submodel;
    this.model = model;
    this.document = document;
    this.prefix = prefix;
    index(model);
  }

  /**
   * @return {@code true} for the instance of the main model
   */
  boolean isRoot() {
    return parent == null;
  }

  private void index(TreeNode node) {
    for (int i = 0; i < node.getChildCount(); i++) {
      TreeNode child = node.getChildAt(i);
      if (child instanceof SBase) {
        register((SBase) child);
        index(child);
      } else if (child instanceof SBasePlugin) {
        index(child);
      }
    }
  }

  private void register(SBase sbase) {
    elements.add(sbase);
    if (sbase.isSetMetaId()) {
      metaIds.put(sbase.getMetaId(), sbase);
    }
    if (!sbase.isSetId()) {
      return;
    }
    if (sbase instanceof UnitDefinition) {
      unitSIds.put(sbase.getId(), (UnitDefinition) sbase);
    } else if (sbase instanceof Port) {
      portSIds.put(sbase.getId(), (Port) sbase);
    } else if (!(sbase instanceof LocalParameter)) {
      sIds.put(sbase.getId(), sbase);
    }
  }

  @Override
  public String toString() {
    return isRoot() ? "main model" : "submodel '" + prefix + "'";
  }
}
