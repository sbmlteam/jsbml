/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * Copyright (C) 2009-2018 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The Babraham Institute, Cambridge, UK
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.util;

import static java.text.MessageFormat.format;

import org.apache.log4j.Logger;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.SBasePlugin;

import javax.swing.tree.TreeNode;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class PackageDisabler {

  /**
   * A logger for this class.
   */
  private static final transient Logger logger =
    Logger.getLogger(PackageDisabler.class);
  private final Map<String, String> namespaceMapping;
  private SBMLDocument document;


  /**
   * Instantiates a PackageDisabler on the given SBMLDocument
   * <p>
   * PackageDisabler provides a method to remove unused packages that correspond
   * to {@link SBasePlugin} across a {@link SBMLDocument}
   * Additionally specific packages can be forcefully removed by use of
   * {@link #removePackage(String) removePackage}
   *
   * @param document
   *        {@link SBMLDocument} to run the disabler on
   */
  public PackageDisabler(SBMLDocument document) {
    this.document = document;
    namespaceMapping = document.getDeclaredNamespaces();
  }


  /**
   * Check namespaces for unused packages and remove them
   */
  public void disableUnused() {
    Set<String> values = new TreeSet<String>(namespaceMapping.values());
    for (String nameOrUri : values) {
      disable(nameOrUri, false);
    }
  }


  /**
   * Forcefully remove all occurrences of the package given by nameOrUri
   *
   * @param nameOrUri
   *        of package to remove
   */
  public void removePackage(String nameOrUri) {
    disable(nameOrUri, true);
  }


  /**
   * @param nameOrUri
   *        of package to remove
   * @param force
   *        removal of package, even if present
   */
  private void disable(String nameOrUri, boolean force) {
    Model model = document.getModel();
    // check if plugin/package exists
    SBasePlugin plugin = null;
    try {
      plugin = model.getPlugin(nameOrUri);
    } catch (IllegalArgumentException exc) {
      logger.error(format(exc.getMessage()));
    }
    if (plugin == null) {
      logger.warn(format("No package present for name {0}.", nameOrUri));
      return;
    }
    if (plugin.getChildCount() > 0) {
      boolean inUse = inUse(plugin.getChildAt(0), nameOrUri);
      if (!force && inUse) {
        logger.info(format(
          "Package given by {0} is in use.\nIt will not be removed.\nUse removePackage({0}) to forcefully disable.",
          nameOrUri));
        return;
      }
    }
    // needs both, else namespace is still present inside sbml and model tags
    model.disablePackage(nameOrUri);
    model.unsetPlugin(nameOrUri);
    int numChildren = model.getChildCount();
    for (int i = 0; i < numChildren; i++) {
      TreeNode child = model.getChildAt(i);
      if (child instanceof SBase) {
        disablePackageRecursively((SBase) child, nameOrUri);
      }
    }
    logger.info(
      format("Package given by {0} has now been disabled", nameOrUri));
  }


  /**
   * Helper method to check whether a package is actually used
   *
   * @param node
   *        representing model tag
   * @param nameOrUri
   *        of package to remove
   * @return True, if package namespace is used below model node
   */
  private boolean inUse(TreeNode node, String nameOrUri) {
    if (node instanceof SBase) {
      for (int i = 0; i < node.getChildCount(); i++) {
        TreeNode child = node.getChildAt(i);
        if (child instanceof SBase) {
          SBasePlugin plug = ((SBase) child).getExtension(nameOrUri);
          if (plug != null) {
            return true;
          }
        }
      }
    }
    return false;
  }


  /**
   * @param sBase
   *        current {@link SBase}
   * @param nameOrUri
   *        of package to remove
   */
  private void disablePackageRecursively(SBase sBase, String nameOrUri) {
    traverseChildren(sBase, nameOrUri);
    SBasePlugin plugin = sBase.getExtension(nameOrUri);
    if (plugin != null) {
      sBase.unsetExtension(nameOrUri);
    }
  }


  /**
   * @param sBase
   *        current {@link SBase}
   * @param nameOrUri
   *        of package to remove
   */
  private void traverseChildren(SBase sBase, String nameOrUri) {
    int numChildren = sBase.getChildCount();
    for (int i = 0; i < numChildren; i++) {
      TreeNode child = sBase.getChildAt(i);
      if (child instanceof SBase) {
        disablePackageRecursively((SBase) child, nameOrUri);
      }
    }
  }
}
