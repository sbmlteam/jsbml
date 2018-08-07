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
package org.sbml.jsbml;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

import javax.swing.tree.TreeNode;
import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.TreeNodeChangeEvent;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.jsbml.util.converters.ExpandFunctionDefinitionConverter;
import org.sbml.jsbml.util.converters.ToL3V2Converter;
import org.sbml.jsbml.validator.SBMLValidator;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.LoggingValidationContext;
import org.sbml.jsbml.xml.parsers.PackageParser;
import org.sbml.jsbml.xml.parsers.ParserManager;

/**
 * Represents the 'sbml' root node of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * @author Marine Dumousseau
 * @since 0.8
 */
public class SBMLDocument extends AbstractSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long             serialVersionUID           =
      -3927709655186844513L;

  /**
   * The namespace URI of SBML Level 1 Version 1 and 2.
   */
  public static final transient String  URI_NAMESPACE_L1           =
      "http://www.sbml.org/sbml/level1";

  /**
   * The namespace URI of SBML Level 2 Version 1.
   */
  public static final transient String  URI_NAMESPACE_L2V1         =
      "http://www.sbml.org/sbml/level2";

  /**
   * The namespace URI of SBML Level 2 Version 2.
   */
  public static final String            URI_NAMESPACE_L2V2         =
      "http://www.sbml.org/sbml/level2/version2";

  /**
   * The namespace URI of SBML Level 2 Version 3.
   */
  public static final transient String  URI_NAMESPACE_L2V3         =
      "http://www.sbml.org/sbml/level2/version3";

  /**
   * The namespace URI of SBML Level 2 Version 4.
   */
  public static final transient String  URI_NAMESPACE_L2V4         =
      "http://www.sbml.org/sbml/level2/version4";

  /**
   * The namespace URI of SBML Level 2 Version 5.
   */
  public static final transient String  URI_NAMESPACE_L2V5         =
      "http://www.sbml.org/sbml/level2/version5";

  /**
   * The namespace URI of SBML Level 3 Version 1.
   */
  public static final transient String  URI_NAMESPACE_L3V1Core     =
      "http://www.sbml.org/sbml/level3/version1/core";

  /**
   * The namespace URI of SBML Level 3 Version 2.
   */
  public static final transient String  URI_NAMESPACE_L3V2Core     =
      "http://www.sbml.org/sbml/level3/version2/core";

  /**
   * Contains all the parameter to validate the SBML document
   */
  private Map<String, Boolean>          checkConsistencyParameters =
      new HashMap<String, Boolean>();

  /**
   * Memorizes all {@link SBMLError} when parsing the file containing this
   * document.
   */
  private SBMLErrorLog                  listOfErrors;

  /**
   * logger used to print messages
   */
  private static final transient Logger logger                     =
      Logger.getLogger(SBMLDocument.class);

  /**
   * Stores all the meta identifiers within this {@link SBMLDocument} to avoid
   * the creation of multiple identical meta identifiers. These identifiers
   * have to be unique within the document.
   */
  private Map<String, SBase>            mappingFromMetaId2SBase;

  /**
   * Represents the 'model' XML subnode of a SBML file.
   */
  private Model                         model;

  /**
   * Stores a package namespace associated with it's status.
   * The status being: enabled if the Boolean is true, disable otherwise
   */
  private Map<String, Boolean>          enabledPackageMap;

  /**
   * Contains all the XML attributes of the sbml XML node.
   */
  private Map<String, String>           SBMLDocumentAttributes;


  /**
   * Creates a {@link SBMLDocument} instance. By default, the parent SBML object
   * of
   * this object is itself. The model is {@code null}. The
   * SBMLDocumentAttributes and
   * the SBMLDocumentNamespaces are empty.
   */
  public SBMLDocument() {
    super();
    model = null;
    mappingFromMetaId2SBase = new HashMap<String, SBase>();
    SBMLDocumentAttributes = new HashMap<String, String>();
    enabledPackageMap = new HashMap<String, Boolean>();
    // setParentSBML(this);
    checkConsistencyParameters.put(CHECK_CATEGORY.UNITS_CONSISTENCY.name(),
      false);
  }


  /**
   * Creates a SBMLDocument instance from a level and version. By default, the
   * parent SBML object of this object is itself. The model is {@code null}. The
   * SBMLDocumentAttributes and the SBMLDocumentNamespaces are empty.
   * 
   * @param level
   *        the SBML level
   * @param version
   *        the SBML version
   */
  public SBMLDocument(int level, int version) {
    this();
    setLevel(level);
    setVersion(version);
    if (!hasValidLevelVersionNamespaceCombination()) {
      throw new LevelVersionError(this);
    }
    else {
      addDeclaredNamespace("xmlns", getNamespace());
    }
    initDefaults();
  }


  /**
   * Creates a new {@link SBMLDocument} instance from a given
   * {@link SBMLDocument}.
   * 
   * @param sb
   */
  public SBMLDocument(SBMLDocument sb) {
    super(sb);

    // the super constructor from AbstractSBase could have added stuff on this
    // map already
    if (mappingFromMetaId2SBase == null) {
      mappingFromMetaId2SBase = new HashMap<String, SBase>();
    }

    // the super constructor from AbstractSBase could have added stuff on this
    // map already
    if (SBMLDocumentAttributes == null) {
      SBMLDocumentAttributes = new HashMap<String, String>();
    }

    // the super constructor from AbstractSBase could have added stuff on this
    // map already
    if (enabledPackageMap == null) {
      enabledPackageMap = new HashMap<String, Boolean>();
    }

    // cloning the enabledPackageMap
    for (String namespace : sb.enabledPackageMap.keySet()) {
      enabledPackageMap.put(new String(namespace),
        new Boolean(sb.enabledPackageMap.get(namespace)));
    }

    if (sb.isSetModel()) {
      // This will also cause that all metaIds are registered correctly.
      setModel(sb.getModel().clone());
    } else {
      model = null;
    }
    Iterator<Map.Entry<String, String>> entryIterator =
        sb.SBMLDocumentAttributes.entrySet().iterator();
    Map.Entry<String, String> entry;
    while (entryIterator.hasNext()) {
      entry = entryIterator.next();
      SBMLDocumentAttributes.put(new String(entry.getKey()),
        new String(entry.getValue()));
    }
    // setParentSBML(this);
    checkConsistencyParameters.put(CHECK_CATEGORY.UNITS_CONSISTENCY.name(),
      Boolean.valueOf(false));

  }


  /**
   * Adds a name space to the SBMLNamespaces of this {@link SBMLDocument}.
   * 
   * @param namespaceName
   * @param prefix
   * @param URI
   * @deprecated use {@link SBase#addDeclaredNamespace(String, String)}
   */
  @Deprecated
  public void addNamespace(String namespaceName, String prefix, String URI) {
    if ((prefix != null) && (prefix.trim().length() > 0)) {
      addDeclaredNamespace(prefix + ':' + namespaceName, URI);
    } else {
      addDeclaredNamespace(namespaceName, URI);
    }
  }


  /**
   * Adds the package namespace declaration in this {@link SBMLDocument}, adds
   * as well
   * the required attribute for this package.
   * 
   * @param packageName
   *        the name or short label of the package, for example: layout, comp,
   *        qual, ...
   * @param packageURI
   *        the package namespace uri
   * @param required
   *        the value of the required attribute
   */
  private void addPackageDeclaration(String packageName, String packageURI,
    boolean required) {
    addDeclaredNamespace("xmlns:" + packageName, packageURI);
    getSBMLDocumentAttributes().put(packageName + ":required",
      Boolean.toString(required));
  }


  /**
   * Removes the package namespace declaration in this {@link SBMLDocument},
   * removes as well
   * the required attribute for this package.
   * 
   * @param packageName
   *        the name or short label of the package, for example: layout, comp,
   *        qual, ...
   */
  private void removePackageDeclaration(String packageName) {
    getDeclaredNamespaces().remove("xmlns:" + packageName);
    getSBMLDocumentAttributes().remove(packageName + ":required");
  }


  /**
   * Validates the {@link SBMLDocument} using the JSBML internal
   * offline validator.
   * 
   * <p>You can control the consistency checks that are performed when
   * {@link #checkConsistencyOffline()} is called with the
   * {@link #setConsistencyChecks(CHECK_CATEGORY, boolean)} method.</p>
   * 
   * <p>It will fill this {@link SBMLDocument}'s {@link #listOfErrors}
   * with {@link SBMLError}s for each problem within this whole data
   * structure. You will then be able to obtain this list by calling
   * {@link #getError(int)} or {@link #getListOfErrors()}.</p>
   * 
   * <p> If this method returns a nonzero value (meaning, one or more
   * consistency checks have failed for SBML document), the failures may be
   * due to <i>warnings</i> or <i>errors</i>. Callers should inspect the severity
   * flag in the individual SBMLError objects returned by
   * {@link SBMLDocument#getError(int)} to determine the nature of the failures.
   * 
   * <p>The offline validator is not yet as complete as the online one so it is not
   * currently used by default when calling {@link #checkConsistency()} but it might be
   * in future versions. To get an up to date status, please check the page 
   * <a href="https://github.com/sbmlteam/jsbml/wiki/Offline-validator-status">Offline-validator-status</a>.</p>
   * 
   * @return the number of errors found
   * @see SBMLErrorLog#getErrorsBySeverity(org.sbml.jsbml.SBMLError.SEVERITY)
   * @see SBMLErrorLog#getNumFailsWithSeverity(org.sbml.jsbml.SBMLError.SEVERITY)
   */
  public int checkConsistencyOffline() {
    LoggingValidationContext ctx = new LoggingValidationContext(getLevel(), getVersion());

    // By default disable the unit consistency category, enable all the rest
    List<CHECK_CATEGORY> checks = new ArrayList<CHECK_CATEGORY>();
    checks.addAll(Arrays.asList((CHECK_CATEGORY.values())));
    checks.remove(CHECK_CATEGORY.UNITS_CONSISTENCY);

    // enable/disable the categories selected by the user
    for (String checkCategory : checkConsistencyParameters.keySet())
    {
      CHECK_CATEGORY typeOfCheck = CHECK_CATEGORY.valueOf(checkCategory);
      Boolean checkIsOn = checkConsistencyParameters.get(checkCategory);

      logger.debug("checkConsistencyOffline - Type of check = " + typeOfCheck + " is " + checkIsOn);

      if (checkIsOn && typeOfCheck.equals(CHECK_CATEGORY.UNITS_CONSISTENCY)) {
        checks.add(CHECK_CATEGORY.UNITS_CONSISTENCY);
      }
      else if (!checkIsOn) {
        checks.remove(typeOfCheck);
      }
    }

    ctx.enableCheckCategories(checks.toArray(new CHECK_CATEGORY[checks.size()]), true);
    ctx.loadConstraints(this.getClass());

    SBMLDocument docToValidate = this;

    if (isSetModel() && getModel().getFunctionDefinitionCount() > 0) {
      ExpandFunctionDefinitionConverter converter = new ExpandFunctionDefinitionConverter();
      docToValidate = converter.convert(this);
    }
    ctx.validate(docToValidate);

    listOfErrors = ctx.getErrorLog();
    return ctx.getErrorLog().getErrorCount();
  }


  /**
   * Validates the {@link SBMLDocument} using the
   * SBML.org online validator (http://sbml.org/validator/).
   * 
   * <p>
   * You can control the consistency checks that are performed when
   * {@link #checkConsistencyOnline()} is called with the
   * {@link #setConsistencyChecks(CHECK_CATEGORY, boolean)} method.
   * It will fill this {@link SBMLDocument}'s {@link #listOfErrors}
   * with {@link SBMLError}s for each problem within this whole data
   * structure. You will then be able to obtain this list by calling
   * {@link #getError(int)} or {@link #getListOfErrors()}.</p>
   * 
   * <p>
   * If this method returns a nonzero value (meaning, one or more
   * consistency checks have failed for SBML document), the failures may be
   * due to <i>warnings</i> or <i>errors</i>. Callers should inspect the severity
   * flag in the individual SBMLError objects returned by
   * {@link SBMLDocument#getError(int)} to determine the nature of the failures.</p>
   * 
   * @return the number of errors found
   * @see #setConsistencyChecks(CHECK_CATEGORY, boolean)
   */
  public int checkConsistencyOnline() {

    File tmpFile = null;

    try {
      tmpFile = File.createTempFile("jsbml-", ".xml");
    } catch (IOException e) {
      logger.error(
        "There was an error creating a temporary file: " + e.getMessage());

      if (logger.isDebugEnabled()) {
        e.printStackTrace();
      }
      return -1;
    }

    try {
      new SBMLWriter().writeSBML(this, tmpFile);
    } catch (IOException e) {
      logger.error("There was an IOException: " + e.getMessage());

      if (logger.isDebugEnabled()) {
        e.printStackTrace();
      }
      return -1;
    } catch (XMLStreamException e) {
      logger.error("There was an error writing the SBMLDocument to a file: "
          + e.getMessage());

      if (logger.isDebugEnabled()) {
        e.printStackTrace();
      }
      return -1;
    } catch (SBMLException e) {
      logger.error(
        "There was an error creating a temporary file: " + e.getMessage());

      if (logger.isDebugEnabled()) {
        e.printStackTrace();
      }
      return -1;
    }

    /*
     * u --> Disable checking the consistency of measurement units associated
     * with quantities (SBML L2V3 rules 105nn)
     * i --> Disable checking the correctness and consistency of identifiers
     * used for model entities (SBML L2V3 rules 103nn)
     * m --> Disable checking the syntax of MathML mathematical expressions
     * (SBML L2V3 rules 102nn)
     * s --> Disable checking the validity of SBO identifiers (if any) used in
     * the model (SBML L2V3 rules 107nn)
     * o --> Disable static analysis of whether the model is overdetermined
     * p --> Disable additional checks for recommended good modeling practices
     * g --> Disable all other general SBML consistency checks (SBML L2v3 rules
     * 2nnnn)
     */

    // checkConsistencyParameters.put("offcheck", "u");
    // checkConsistencyParameters.put("offcheck", "u,p,o");

    // System.out.println("SBMLDocument.checkConsistency: tmp file = " +
    // tmpFile.getAbsolutePath());

    Map<String, String> consistencyParameters = new HashMap<String, String>();
    String offcheck = null;

    for (String checkCategory : checkConsistencyParameters.keySet()) {
      CHECK_CATEGORY typeOfCheck = CHECK_CATEGORY.valueOf(checkCategory);
      boolean checkIsOn =
          checkConsistencyParameters.get(checkCategory).booleanValue();

      logger.debug(" Type of check = " + typeOfCheck + " is " + checkIsOn);

      switch (typeOfCheck) {
      case IDENTIFIER_CONSISTENCY:
      {
        if (!checkIsOn) {
          offcheck = (offcheck == null) ? "i" : offcheck + ",i";
        }
        break;
      }
      case GENERAL_CONSISTENCY:
      {
        if (!checkIsOn) {
          offcheck = (offcheck == null) ? "g" : offcheck + ",g";
        }
        break;
      }
      case SBO_CONSISTENCY:
      {
        if (!checkIsOn) {
          offcheck = (offcheck == null) ? "s" : offcheck + ",s";
        }
        break;
      }
      case MATHML_CONSISTENCY:
      {
        if (!checkIsOn) {
          offcheck = (offcheck == null) ? "m" : offcheck + ",m";
        }
        break;
      }
      case UNITS_CONSISTENCY:
      {
        if (!checkIsOn) {
          offcheck = (offcheck == null) ? "u" : offcheck + ",u";
        }
        break;
      }
      case OVERDETERMINED_MODEL:
      {
        if (!checkIsOn) {
          offcheck = (offcheck == null) ? "o" : offcheck + ",o";
        }
        break;
      }
      case MODELING_PRACTICE:
      {
        if (!checkIsOn) {
          offcheck = (offcheck == null) ? "p" : offcheck + ",p";
        }
        break;
      }
      default:
      {
        // If it's a category for which we don't have validators, ignore it.
        // Should not happen as checkConsistencyParameters is only modified
        // through
        // setConsistencyChecks(CHECK_CATEGORY, boolean)
        break;
      }
      }
    }
    if (offcheck != null) {
      consistencyParameters.put("offcheck", offcheck);
    }

    // Doing the actual check consistency
    listOfErrors = SBMLValidator.checkConsistency(tmpFile.getAbsolutePath(),
      consistencyParameters);

    try {
      tmpFile.delete();
    } catch (SecurityException e) {
      logger.error(
        "There was an error removing a temporary file: " + e.getMessage());

      if (logger.isDebugEnabled()) {
        e.printStackTrace();
      }
    }

    if (listOfErrors == null) {
      logger.error("There was an error accessing the sbml online validator!");
      return -1;
    }

    return listOfErrors.getErrorCount();
  }


  /**
   * Validates the {@link SBMLDocument}.
   * 
   * <p> The validation is currently performed using the
   * SBML.org online validator (http://sbml.org/validator/).</p>
   * 
   * <p>
   * You can control the consistency checks that are performed when
   * {@link #checkConsistency()} is called with the
   * {@link #setConsistencyChecks(CHECK_CATEGORY, boolean)} method.
   * It will fill this {@link SBMLDocument}'s {@link #listOfErrors}
   * with {@link SBMLError}s for each problem within this whole data
   * structure. You will then be able to obtain this list by calling
   * {@link #getError(int)} or {@link #getListOfErrors()}.</p>
   * 
   * <p>
   * If this method returns a nonzero value (meaning, one or more
   * consistency checks have failed for SBML document), the failures may be
   * due to <i>warnings</i> or <i>errors</i>. Callers should inspect the severity
   * flag in the individual SBMLError objects returned by
   * {@link SBMLDocument#getError(int)} to determine the nature of the failures.</p>
   * 
   * @return the number of errors found
   * @see #setConsistencyChecks(CHECK_CATEGORY, boolean)
   * @see #checkConsistencyOnline()
   * @see #checkConsistencyOffline()
   */
  public int checkConsistency() {
    return checkConsistencyOnline();
  }
  
  
  /**
   * Checks if the given meta identifier can be added in this
   * {@link SBMLDocument}
   * 's {@link #mappingFromMetaId2SBase}.
   * 
   * @param metaId
   *        the identifier whose value is to be checked.
   * @throws IllegalArgumentException
   *         if a metaid to add is already present in the list of
   *         registered metaids.
   */
  private void checkMetaId(String metaId) {
    if (containsMetaId(metaId)) {
      logger.error(MessageFormat.format(
        "An element with the metaid \"{0}\" is already present in the SBML document. The new element will not get added to it.",
        metaId));
      throw new IllegalArgumentException(MessageFormat.format(
        "Cannot set duplicate meta identifier \"{0}\".", metaId));
    }
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.element.AbstractSBase#clone()
   */
  @Override
  public SBMLDocument clone() {
    return new SBMLDocument(this);
  }


  /**
   * Collects all meta identifiers of this {@link AbstractSBase} and all of
   * its sub-elements if recursively is {@code true}.
   * 
   * @param metaIds
   *        the {@link Map} that gathers the result.
   * @param sbase
   *        The {@link SBase} whose meta identifier is to be collected
   *        and from which we maybe have to recursively go through all
   *        of its children.
   * @param recursively
   *        if {@code true}, this method will also consider all
   *        sub-elements of this {@link AbstractSBase}.
   * @param delete
   *        if {@code true} this method will not check if
   *        the meta identifier can be added to the {@link SBMLDocument}.
   * @throws IllegalArgumentException
   *         However, duplications are not legal and an
   *         {@link IllegalArgumentException} will be thrown in such
   *         cases.
   */
  @SuppressWarnings("unchecked")
  private void collectMetaIds(Map<String, SBase> metaIds, SBase sbase,
    boolean recursively, boolean delete) {
    if (sbase.isSetMetaId()) {
      if (!delete) {
        // checks if the metaid can be added, throws an exception if not.
        checkMetaId(sbase.getMetaId());
      }
      metaIds.put(sbase.getMetaId(), sbase);

      // logger can be null during cloning apparently
      if (logger != null && logger.isDebugEnabled()) {
        logger.debug("SBMLDocument - #collectMetaIds - node = '" + sbase + "'");
      }
    }
    if (recursively) {
      Enumeration<? extends TreeNode> children = sbase.children();
      while (children.hasMoreElements()) {
        TreeNode node = children.nextElement();

        if (node instanceof SBase) {
          collectMetaIds(metaIds, (SBase) node, recursively, delete);
        }
      }
    }
  }


  /**
   * A check to see whether elements have been registered to this
   * {@link SBMLDocument} with the given meta identifier.
   * 
   * @param metaId
   * @return
   */
  public boolean containsMetaId(String metaId) {

    if (mappingFromMetaId2SBase == null) {
      mappingFromMetaId2SBase = new HashMap<String, SBase>();
    }

    return mappingFromMetaId2SBase.containsKey(metaId);
  }


  /**
   * Creates a new Model inside this {@link SBMLDocument}, and returns a
   * pointer to it.
   * In SBML Level 2, the use of an identifier on a {@link Model} object is
   * optional. This method takes an optional argument, sid, for setting the
   * identifier. If not supplied, the identifier attribute on the Model
   * instance is not set.
   * 
   * @return the new {@link Model} instance.
   */
  public Model createModel() {
    Model oldValue = getModel();
    setModel(new Model(getLevel(), getVersion()));
    firePropertyChange(TreeNodeChangeEvent.model, oldValue, getModel());
    return getModel();
  }


  /**
   * Creates a new instance of Model from id and the level and version of this
   * SBMLDocument.
   * 
   * @param id
   * @return the new {@link Model} instance.
   */
  public Model createModel(String id) {
    setModel(new Model(id, getLevel(), getVersion()));
    return getModel();
  }


  /**
   * Disables the given SBML Level 3 package on this {@link SBMLDocument}.
   * 
   * @param packageURIOrName
   *        a package namespace URI or package name
   */
  @Override
  public void disablePackage(String packageURIOrName) {
    enablePackage(packageURIOrName, false);
  }


  /**
   * Enables the given SBML Level 3 package on this {@link SBMLDocument}.
   * 
   * @param packageURIOrName
   *        a package namespace URI or package name
   */
  @Override
  public void enablePackage(String packageURIOrName) {
    enablePackage(packageURIOrName, true);
  }


  /**
   * Enables or disables the given SBML Level 3 package on this
   * {@link SBMLDocument}.
   * 
   * @param packageURIOrName
   *        a package namespace URI or package name
   */
  @Override
  public void enablePackage(String packageURIOrName, boolean enabled) {

    // Get the package URI is needed
    PackageParser packageParser =
        ParserManager.getManager().getPackageParser(packageURIOrName);

    if (packageParser != null) {
      String packageURI = packageURIOrName;

      // if the shorLabel has been used, getting the last namespace in the list
      // of namespaces // TODO - add a getDefaultNamespace() to PackageParser
      // TODO - add a getPackageShortLabel() to PackageParser, so that a proper
      // name can be used for packages ? Might be confusing
      // getPackageDisplayName() might be better.
      if (packageURI.equals(packageParser.getPackageName())) {
        packageURI = packageParser.getPackageNamespaces().get(
          packageParser.getPackageNamespaces().size() - 1);
      }

      // check if the package is already present ??
      // possible libsbml errors PACKAGE_UNKNOWN, PACKAGE_VERSION_MISMATCH,
      // PACKAGE_CONFLICTED_VERSION
      // just returning for now without reporting errors
      for (String packageNamespaceUri : packageParser.getPackageNamespaces()) {
        if (enabledPackageMap.containsKey(packageNamespaceUri)) {
          // Package already present in the map.
          if (enabledPackageMap.get(packageNamespaceUri) == enabled) {
            return;
          }
        }
      }

      enabledPackageMap.put(packageURI, enabled);

      if (enabled) {
        addPackageDeclaration(packageParser.getPackageName(), packageURI,
          packageParser.isRequired());
      } else {
        // remove the namespace declaration from the SBMLDocument
        removePackageDeclaration(packageParser.getPackageName());
      }
    }
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o) {
    boolean equals = super.equals(o);
    if (equals) {
      SBMLDocument d = (SBMLDocument) o;
      if (!getSBMLDocumentAttributes().equals(d.getSBMLDocumentAttributes())) {
        return false;
      }
    }
    return equals;
  }


  /**
   * Looks up the {@link SBase} registered in this {@link SBMLDocument} for the
   * given metaId.
   * 
   * @param metaId
   * @return
   */
  public SBase findSBase(String metaId) {
    return mappingFromMetaId2SBase.get(metaId);
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(MessageFormat.format(
        resourceBundle.getString("IndexSurpassesBoundsException"), index, 0));
    }
    int count = super.getChildCount(), pos = 0;
    if (index < count) {
      return super.getChildAt(index);
    } else {
      index -= count;
    }
    if (isSetModel()) {
      if (pos == index) {
        return getModel();
      }
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"), index,
      Math.min(pos, 0)));
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    return super.getChildCount() + (isSetModel() ? 1 : 0);
  }


  /**
   * Returns the default SBML Level of new SBMLDocument objects.
   * 
   * @return the default SBML Level of new SBMLDocument objects.
   */
  public int getDefaultLevel() {
    return 3;
  }


  /**
   * Returns the default Version of new SBMLDocument objects.
   * 
   * @return the default Version of new SBMLDocument objects.
   */
  public int getDefaultVersion() {
    return 1;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getElementName()
   */
  @Override
  public String getElementName() {
    return "sbml";
  }


  /**
   * Returns the {@link SBase} with the given metaid in this
   * {@link SBMLDocument} or null
   * if no such {@link SBase} is found.
   * 
   * <p>This method make use of {@link HashMap} so it is more efficient
   * to search for metaid than anywhere else down in the SBML hierarchy.</p>
   * 
   * @param metaid
   *        - the metaid of {@link SBase} to find
   * @return the {@link SBase} with the given metaid or null
   * @see #findSBase(String)
   */
  @Override
  public SBase getElementByMetaId(String metaid) {
    return findSBase(metaid);
  }


  /**
   * Return the package namespace enabled on this SBMLDocument or null if the
   * package is not enabled.
   * 
   * @param packageURIOrName
   *        the name or URI of the package extension.
   * @return the package namespace enabled on this SBMLDocument or null if the
   *         package
   *         is not enabled.
   */
  public String getEnabledPackageNamespace(String packageURIOrName) {

    if (enabledPackageMap.containsKey(packageURIOrName)) {
      return packageURIOrName;
    }

    // Get the package URI is needed
    PackageParser packageParser =
        ParserManager.getManager().getPackageParser(packageURIOrName);

    if (packageParser != null) {

      for (String packageNamespaceUri : packageParser.getPackageNamespaces()) {
        if (enabledPackageMap.containsKey(packageNamespaceUri)) {
          return packageNamespaceUri; // TODO - check that it is really enabled
        }
      }
    }

    return null;
  }


  /**
   * Returns the i<sup>th</sup> error or warning encountered during consistency
   * checking.
   * 
   * @param i
   *        - the index of the {@link SBMLError} to get
   * @return the i<sup>th</sup> error or warning encountered during consistency
   *         checking.
   * @throws IndexOutOfBoundsException
   *         if the index is wrong
   * @see #getNumErrors()
   */
  public SBMLError getError(int i) {
    if (!isSetListOfErrors() || (i < 0) || (i >= getErrorCount())) {
      throw new IndexOutOfBoundsException(
        "You are trying to access the error number " + i
        + ", which is invalid.");
    }

    return listOfErrors.getError(i);
  }


  /**
   * Returns the number of errors or warnings encountered during consistency
   * checking.
   * 
   * @return the number of errors or warnings encountered during consistency
   *         checking.
   */
  public int getErrorCount() {
    return isSetListOfErrors() ? listOfErrors.getErrorCount() : 0;
  }


  /**
   * Returns a collection of all {@link SBMLError}s reflecting
   * problems in the overall data structure of this {@link SBMLDocument}.
   * 
   * @return a collection of all {@link SBMLError}s encountered during
   *         consistency checking.
   */
  public SBMLErrorLog getErrorLog() {
    return getListOfErrors();
  }


  /**
   * Returns a collection of all {@link SBMLError}s reflecting
   * problems in the overall data structure of this {@link SBMLDocument}.
   * 
   * @return a collection of all {@link SBMLError}s encountered during
   *         consistency checking.
   */
  public SBMLErrorLog getListOfErrors() {
    if (listOfErrors == null) {
      listOfErrors = new SBMLErrorLog();
    }
    return listOfErrors;
  }


  /**
   * Returns the model of this {@link SBMLDocument}.
   * 
   * @return the model of this {@link SBMLDocument}. Can be null if it is not
   *         set.
   */
  @Override
  public Model getModel() {
    return model;
  }


  /**
   * Returns the number of errors or warnings encountered during consistency
   * checking.
   * 
   * @return the number of errors or warnings encountered during consistency
   *         checking.
   * @libsbml.deprecated
   * @see #getErrorCount()
   */
  public int getNumErrors() {
    return getErrorCount();
  }


  /**
   * Returns the required attribute of the given package extension.
   * 
   * @param nameOrUri
   *        the name or URI of the package extension.
   * @return a boolean indicating whether the package is flagged as being
   *         required. If the name or uri is
   *         not recognized by this version of JSBML, false is returned.
   */
  public boolean getPackageRequired(String nameOrUri) {

    try {
      return ParserManager.getManager().getPackageRequired(nameOrUri);
    } catch (IllegalArgumentException e) {
      logger.warn(e.getMessage());
    }

    // same default behavior as in libSBML 5.9.2
    return false;
  }


  /**
   * Returns the required attribute of the given package extension.
   * The name of package must not be given if the package is not enabled.
   * 
   * @param pckage
   *        the name or URI of the package extension.
   * @return a boolean value indicating whether the package is flagged as being
   *         required in this {@link SBMLDocument}.
   * @deprecated use {@link #getPackageRequired(String)}
   * @libsbml.deprecated
   */
  @Deprecated
  public boolean getPkgRequired(String pckage) {
    return getPackageRequired(pckage);
  }


  /**
   * Returns the map of attribute names and values of this SBMLDocument.
   * 
   * @return the map of attribute names and values of this SBMLDocument.
   */
  public Map<String, String> getSBMLDocumentAttributes() {
    if (SBMLDocumentAttributes == null) {
      SBMLDocumentAttributes = new TreeMap<String, String>();
    }
    return SBMLDocumentAttributes;
  }


  /**
   * Returns the map of declared namespaces of this SBMLDocument.
   * 
   * @return the map of declared namespaces of this SBMLDocument.
   * @deprecated use {@link SBase#getDeclaredNamespaces()}
   */
  @Deprecated
  public Map<String, String> getSBMLDocumentNamespaces() {
    return getDeclaredNamespaces();
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 827;
    int hashCode = super.hashCode();
    Map<String, String> map = getSBMLDocumentAttributes();
    if (map != null) {
      hashCode += prime * map.hashCode();
    }

    return hashCode;
  }


  /**
   * 
   */
  private void initDefaults() {
  }


  /**
   * Returns {@code true} if the given package extension is one of an ignored
   * packages, otherwise returns {@code false}.
   * <p>
   * An ignored package is one that is defined to be used in this
   * {@link SBMLDocument}, but the package is not supported in this copy of
   * JSBML.
   * 
   * @param nameOrURI
   *        the name or URI of the package extension.
   * @return a Boolean, {@code true} if the package is being ignored and
   *         {@code false} otherwise.
   */
  public boolean isIgnoredPackage(String nameOrURI) {

    // Getting the package URI
    PackageParser packageParser =
        ParserManager.getManager().getPackageParser(nameOrURI);

    if (packageParser != null) {
      String packageURI = nameOrURI;

      if (packageURI.equals(packageParser.getPackageName())) {
        packageURI = packageParser.getPackageNamespaces().get(0);
      }

      if (ignoredExtensions.containsKey(packageURI)) {
        return true;
      }
    }
    return false;
  }


  /**
   * Returns true if the given package extension is one of ignored packages,
   * otherwise returns false.
   * <p>
   * An ignored package is one that is defined to be used in this SBMLDocument,
   * but the package is not supported in this copy of JSBML.
   * 
   * @param pkgURI
   *        the URI of the package extension.
   * @return a boolean
   * @deprecated use {@link #isIgnoredPackage(String)}
   * @libsbml.deprecated
   */
  @Deprecated
  public boolean isIgnoredPkg(String pkgURI) {
    return isIgnoredPackage(pkgURI);
  }


  /**
   * Returns {@code true} if the given SBML Level 3 package is enabled within
   * the {@link SBMLDocument}.
   * <p>
   * If the namespace was declared on the sbml element, or if any elements of
   * this package were found while building the SBMLDocument structure, the
   * package will be enabled.
   * <p>
   * For the parameter '{@code packageNameorUri}', you should use the package
   * shortLabel or name, for example 'distrib', as given by
   * {@link org.sbml.jsbml.ext.distrib.DistribConstants#shortLabel}, this way,
   * you don't mind about the specific package version. If you want to check for
   * a specific package version, then you can use the namespace instead.
   * 
   * @param packageURIOrName
   *        the name or URI of the package extension.
   * @return {@code true} if the given SBML Level 3 package is enabled within
   *         the {@link SBMLDocument}, {@code false} otherwise.
   */
  @Override
  public boolean isPackageEnabled(String packageURIOrName) {
    Boolean enabled = isPackageEnabledOrDisabled(packageURIOrName);
    return (enabled != null) && enabled.booleanValue();
  }


  /**
   * Returns {@code true} if the given SBML Level 3 package is enabled within
   * the {@link SBMLDocument}, {@code false}
   * if the package was disabled using the method
   * {@link #disablePackage(String)} or {@code null} if this package
   * was neither enabled or disabled on this {@link SBMLDocument}.
   * <p>
   * This method can be used, for example, by the {@link SBMLWriter} to know if
   * a package was really disabled,
   * in which case the package elements won't be written down,
   * or if the user forgot to enable it, in which case it will be enabled and
   * the package elements will be written.
   * 
   * @param packageURIOrName
   *        the name or URI of the package extension.
   * @return {@code true} if the given SBML Level 3 package is enabled within
   *         the {@link SBMLDocument}, {@code false}
   *         if the package was disabled using the method
   *         {@link #disablePackage(String)} or {@code null} if this package
   *         was neither enabled or disabled on this {@link SBMLDocument}.
   */
  public Boolean isPackageEnabledOrDisabled(String packageURIOrName) {

    Boolean isPackageEnabled = null;
    
    // Get the package URI is needed
    PackageParser packageParser =
        ParserManager.getManager().getPackageParser(packageURIOrName);

    if (packageParser != null) {
      List<String> packageURIs = null;

      if (packageURIOrName.equals(packageParser.getPackageName())) {
        packageURIs = packageParser.getPackageNamespaces();
      } else {
        packageURIs = new ArrayList<String>();
        packageURIs.add(packageURIOrName);
      }

      // This can happen when cloning a SBMLDocument, the AbstractSBase
      // constructor
      // would add any existing SBasePlugin before we initialize
      // enabledPackageMap
      if (enabledPackageMap == null) {
        enabledPackageMap = new HashMap<String, Boolean>();
      }

      for (String packageURI : packageURIs) {
        if (enabledPackageMap.containsKey(packageURI)) {
          boolean isURIEnabled = enabledPackageMap.get(packageURI);
          if (isPackageEnabled == null) {
            isPackageEnabled = isURIEnabled;
          } else if (isURIEnabled) {
            // Case where we have one version of the package disabled and one enabled
            // like fbc_v1 disable and fbc_v2 enable, we need to return true for fbc
            isPackageEnabled = true;
          }
        }
      }
    }

    return isPackageEnabled;
  }


  /**
   * Returns {@code true} if the list of errors is defined and contain at least
   * one error.
   * 
   * @return {@code true} if the list of errors is defined and contain at least
   *         one error.
   */
  private boolean isSetListOfErrors() {
    return (listOfErrors != null) && (listOfErrors.getErrorCount() > 0);
  }


  /**
   * Returns {@code true} if the {@link Model} of this {@link SBMLDocument} is
   * not {@code null}.
   * 
   * @return {@code true} if the {@link Model} of this {@link SBMLDocument} is
   *         not {@code null}.
   */
  public boolean isSetModel() {
    return model != null;
  }


  /**
   * Returns {@code true}.
   * 
   * @param nameOrURI
   *        the name or URI of the package extension.
   * @return {@code true}
   * @libsbml.deprecated The required package does not need to be set in JSBML,
   *                     it is done automatically as the value
   *                     is fixed for each packages.
   */
  public boolean isSetPackageRequired(String nameOrURI) {
    // it is always set as it is done automatically.
    return true;
  }


  /**
   * Returns {@code true}
   * 
   * @param pckage
   *        the name or URI of the package extension.
   * @return a boolean value.
   * @deprecated use {@link #isSetPackageRequired(String)}
   * @libsbml.deprecated
   */
  @Deprecated
  public boolean isSetPkgRequired(String pckage) {
    return isSetPackageRequired(pckage);
  }


  /**
   * Randomly creates a new {@link String} that can be used as a metaid, i.e., a
   * String that is a valid metaid and that is not yet used by any other element
   * within this {@link SBMLDocument}.
   * 
   * @return a valid metaid that is not yet used by any other element
   *         within this {@link SBMLDocument}.
   */
  public String nextMetaId() {
    String currId;
    do {
      currId = UUID.randomUUID().toString();
      if (Character.isDigit(currId.charAt(0))) {
        // Add an underscore at the beginning of the new metaid only if
        // necessary.
        currId = '_' + currId;
      }
    } while (containsMetaId(currId));
    return currId;
  }


  /**
   * Prints all the errors or warnings encountered trying to check this SBML
   * document.
   * 
   * @param stream
   *        the stream where to print the {@link SBMLDocument} errors.
   */
  public void printErrors(PrintStream stream) {
    int nbErrors = listOfErrors.getErrorCount();

    for (int i = 0; i < nbErrors; i++) {
      stream.println(listOfErrors.getError(i));
    }
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName,
   * String prefix, String value)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals("level")) {
        setLevel(StringTools.parseSBMLInt(value));
      } else if (attributeName.equals("version")) {
        setVersion(StringTools.parseSBMLInt(value));
      }
      else if (prefix != null && prefix.trim().length() > 0) {
        getSBMLDocumentAttributes().put(prefix + ':' + attributeName, value);
      } else {
        getSBMLDocumentAttributes().put(attributeName, value);
      }
    }
    return isAttributeRead;
  }


  /**
   * Saves or removes the given meta identifier in this {@link SBMLDocument}'s
   * {@link #mappingFromMetaId2SBase}.
   * 
   * @param sbase
   *        the element whose meta identifier is to be registered (if it is
   *        set).
   * @param add
   *        if {@code true} this will add the given meta identifier
   *        to this {@link SBMLDocument}'s {@link #mappingFromMetaId2SBase}.
   *        Otherwise, the given identifier will be removed from this set.
   * @return
   *         <ul>
   *         <li>if add is {@code true}, then this method returns
   *         {@code true} if this set did not already contain the specified
   *         element, {@code false} otherwise.</li>
   *         <li>if add is not {@code true}, this method returns
   *         {@code true} if this set contained the specified element,
   *         {@code false} otherwise.</li>
   *         <li>This method also returns {@code false} if the given
   *         {@link SBase} does not have a defined metaId</li>
   *         </ul>
   */
  boolean registerMetaId(SBase sbase, boolean add) {

    if (mappingFromMetaId2SBase == null) {
      mappingFromMetaId2SBase = new HashMap<String, SBase>();
    }

    if (sbase.isSetMetaId()) {
      if (add) {
        // We should call checkMetaid if we want to throw
        // IllegalArgumentException here when metaid already present
        return mappingFromMetaId2SBase.put(sbase.getMetaId(), sbase) == null;
      } else {
        SBase old = mappingFromMetaId2SBase.get(sbase.getMetaId());
        if ((old != null) && (old == sbase)) {
          /*
           * This check is needed because the given SBase might originate from a
           * different Document or could be a clone of some other SBase
           * registered
           * here.
           */
          return mappingFromMetaId2SBase.remove(sbase.getMetaId()) != null;
        }
      }
    }
    return false;
  }


  /**
   * Collects all meta identifiers of this {@link AbstractSBase} and all of
   * its sub-elements if recursively is {@code true}. It can also be used
   * to delete meta identifiers from the given {@link Set}.
   * 
   * @param sbase
   *        The {@link SBase} whose meta identifier is to be registered
   *        and from which we maybe have to recursively go through all
   *        of its children.
   * @param recursively
   *        if {@code true}, this method will also consider all
   *        sub-elements of this {@link AbstractSBase}.
   * @param delete
   *        if {@code true} the purpose of this method will be to
   *        delete the meta identifier from the given {@link Set}.
   *        Otherwise, it will try to add it to the set.
   * @throws IllegalArgumentException
   *         However, duplications are not legal and an
   *         {@link IllegalArgumentException} will be thrown in such
   *         cases.
   */
  void registerMetaIds(SBase sbase, boolean recursively, boolean delete) {

    Map<String, SBase> metaIds = new HashMap<String, SBase>();

    collectMetaIds(metaIds, sbase, recursively, delete);

    if (mappingFromMetaId2SBase == null) {
      mappingFromMetaId2SBase = new HashMap<String, SBase>();
    }

    if (delete) {
      for (String key : metaIds.keySet()) {
        mappingFromMetaId2SBase.remove(key);
      }
    } else {
      mappingFromMetaId2SBase.putAll(metaIds);
    }
  }


  /**
   * Provides access to all registered metaIds in this {@link SBMLDocument}. The
   * given collection is unmodifiable. Modifications made to the values has
   * hence no effect. This method is helpful to iterate through all metaIds in
   * the document and to query elements based on the metaId.
   * 
   * @return An unmodifiable collection of {@link String}s as a view of the
   *         actual metaIds within this {@link SBMLDocument}. Hence, modifying
   *         any of the values returned by this method does not have any effect
   *         for the content of this document.
   */
  public Collection<String> metaIds() {
    return Collections.unmodifiableCollection(mappingFromMetaId2SBase.keySet());
  }


  /**
   * Controls the consistency checks that are performed when
   * {@link SBMLDocument#checkConsistency()} is called.
   * <p>
   * This method works by adding or subtracting consistency checks from the
   * set of all possible checks that {@link SBMLDocument#checkConsistency()}
   * knows
   * how to perform. This method may need to be called multiple times in
   * order to achieve the desired combination of checks. The first
   * argument ({@code category}) in a call to this method indicates the category
   * of consistency/error checks that are to be turned on or off, and the
   * second argument ({@code apply}, a boolean) indicates whether to turn it on
   * (value of {@code true}) or off (value of {@code false}).
   * <p>
   * The possible categories (values to the argument {@code category}) are the
   * set of values from the
   * {@link org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY} enumeration.
   * The following are the possible choices:
   * <p>
   * <ul>
   * <li>
   * {@link org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY#GENERAL_CONSISTENCY}
   * :
   * Correctness and consistency of specific SBML language constructs.
   * Performing this set of checks is highly recommended. With respect to
   * the SBML specification, these concern failures in applying the
   * validation rules numbered 2xxxx in the Level&nbsp;2 Versions&nbsp;2, 3
   * and&nbsp;4 specifications.
   * <li>
   * {@link org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY#IDENTIFIER_CONSISTENCY}
   * :
   * Correctness and consistency of identifiers used for model entities.
   * An example of inconsistency would be using a species identifier in a
   * reaction rate formula without first having declared the species. With
   * respect to the SBML specification, these concern failures in applying
   * the validation rules numbered 103xx in the Level&nbsp;2
   * Versions&nbsp;2, 3 and&nbsp;4 specifications.
   * <li>
   * {@link org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY#UNITS_CONSISTENCY}
   * :
   * Consistency of measurement units associated with quantities in a
   * model. With respect to the SBML specification, these concern failures
   * in applying the validation rules numbered 105xx in the Level&nbsp;2
   * Versions&nbsp;2, 3 and&nbsp;4 specifications.
   * <li>
   * {@link org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY#MATHML_CONSISTENCY}
   * :
   * Syntax of MathML constructs. With respect to the SBML specification,
   * these concern failures in applying the validation rules numbered 102xx
   * in the Level&nbsp;2 Versions&nbsp;2, 3 and&nbsp;4 specifications.
   * <li>
   * {@link org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY#SBO_CONSISTENCY}
   * :
   * Consistency and validity of SBO identifiers (if any) used in the
   * model. With respect to the SBML specification, these concern failures
   * in applying the validation rules numbered 107xx in the Level&nbsp;2
   * Versions&nbsp;2, 3 and&nbsp;4 specifications.
   * <li>
   * {@link org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY#OVERDETERMINED_MODEL}
   * :
   * Static analysis of whether the system of equations implied by a model
   * is mathematically over-determined. With respect to the SBML
   * specification, this is validation rule #10601 in the SBML Level&nbsp;2
   * Versions&nbsp;2, 3 and&nbsp;4 specifications.
   * <li>
   * {@link org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY#MODELING_PRACTICE}
   * :
   * Additional checks for recommended good modeling practice. (These are
   * tests performed by libSBML and do not have equivalent SBML validation
   * rules.)
   * </ul>
   * <p>
   * <em>By default, all validation checks are applied</em> to the model in
   * an {@link SBMLDocument} object <em>unless</em>
   * {@link SBMLDocument#setConsistencyChecks(CHECK_CATEGORY, boolean)} is
   * called to
   * indicate that only a subset should be applied. Further, this default
   * (i.e., performing all checks) applies separately to <em>each new
   * {@link SBMLDocument} object</em> created. In other words, each time a model
   * is read using {@link SBMLReader#readSBML(String)} ,
   * {@link SBMLReader#readSBMLFromString(String)}, a new
   * {@link SBMLDocument} is created and for that document, a call to
   * {@link SBMLDocument#checkConsistency()} will default to applying all
   * possible checks.
   * Calling programs must invoke
   * {@link SBMLDocument#setConsistencyChecks(CHECK_CATEGORY, boolean)} for each
   * such new
   * model if they wish to change the consistency checks applied.
   * <p>
   * 
   * @param category
   *        a value drawn from JSBML#JSBML.SBML_VALIDATOR_* indicating the
   *        consistency checking/validation to be turned on or off
   *        <p>
   * @param apply
   *        a boolean indicating whether the checks indicated by
   *        {@code category} should be applied or not.
   *        <p>
   * @see SBMLDocument#checkConsistency()
   */
  public void setConsistencyChecks(SBMLValidator.CHECK_CATEGORY category,
    boolean apply) {
    checkConsistencyParameters.put(category.name(), Boolean.valueOf(apply));
  }


  /**
   * <p>
   * Sets the SBML Level and Version of this {@link SBMLDocument} instance,
   * without attempting to convert the model.
   * </p>
   * 
   * @param level
   *        the desired SBML Level
   * @param version
   *        the desired Version within the SBML Level
   * @return {@code true} if 'level' and 'version' are valid.
   * @see #setLevelAndVersion(int, int, boolean)
   */
  public boolean setLevelAndVersion(int level, int version) {
    // TODO - this method is not doing any conversion
    return super.setLevelAndVersion(level, version, true);
  }


  /**
   * <p>
   * Sets the SBML Level and Version of this {@link SBMLDocument} instance,
   * attempting to convert the model as needed.
   * </p>
   * <p>
   * This method is the principal way in JSBML to convert models between
   * Levels and Versions of SBML. Generally, models can be converted upward
   * without difficulty (e.g., from SBML Level 1 to Level 2, or from an
   * earlier Version of Level 2 to the latest Version of Level 2). Sometimes
   * models can be translated downward as well, if they do not use constructs
   * specific to more advanced Levels of SBML.
   * </p>
   * <p>
   * Calling this method will not necessarily lead to a successful conversion.
   * If the conversion fails, it will be logged in the error list associated
   * with this {@link SBMLDocument}. Callers should consult
   * {@link #getErrorCount()} to find out if the conversion succeeded
   * without problems. For conversions from Level 2 to Level 1, callers can
   * also check the Level of the model after calling this method to find out
   * whether it is Level 1. (If the conversion to Level 1 failed, the Level of
   * this model will be left unchanged.)
   * </p>
   * 
   * @param level
   *        the desired SBML Level
   * @param version
   *        the desired Version within the SBML Level
   * @param strict
   *        boolean indicating whether to check consistency of both the
   *        source and target model when performing conversion (defaults
   *        to {@code true})
   * @return {@code true} if 'level' and 'version' are valid.
   */
  @Override
  public boolean setLevelAndVersion(int level, int version, boolean strict) {
    if (level > 2) {
      if (version > 1) {
        super.setLevelAndVersion(level, version, strict, new ToL3V2Converter());
      }
    }
    return super.setLevelAndVersion(level, version, strict);
  }


  /**
   * Sets the {@link Model} for this {@link SBMLDocument} to the given
   * {@link Model}.
   * 
   * @param model
   */
  public void setModel(Model model) {
    unsetModel();
    this.model = model;
    registerChild(this.model);
  }


  /**
   * Sets the required attribute value of the given package extension (does
   * nothing in fact!).
   * 
   * @param nameOrUri
   *        the name or URI of the package extension.
   * @param flag
   *        boolean value indicating whether the package is required.
   * @return {@code true}
   * @libsbml.deprecated The required package does not need to be set in JSBML,
   *                     it is done automatically as the value is fixed for each
   *                     packages.
   */
  public boolean setPackageRequired(String nameOrUri, boolean flag) {
    return true;
  }


  /**
   * Sets the value of the required attribute for the given package (does
   * nothing in fact!).
   * 
   * @param pckage
   *        the name or URI of the package extension.
   * @param flag
   *        a Boolean value indicating whether the package is required.
   * @return {@code true}
   * @deprecated use {@link #setPackageRequired(String, boolean)}
   * @libsbml.deprecated
   */
  @Deprecated
  public boolean setPkgRequired(String pckage, boolean flag) {
    return setPackageRequired(pckage, flag);
  }


  /**
   * Sets the {@link #SBMLDocumentAttributes}.
   * 
   * @param attributes
   */
  public void setSBMLDocumentAttributes(Map<String, String> attributes) {
    Map<String, String> oldAttributes = SBMLDocumentAttributes;
    SBMLDocumentAttributes = attributes;
    firePropertyChange(TreeNodeChangeEvent.SBMLDocumentAttributes,
      oldAttributes, SBMLDocumentAttributes);
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#toString()
   */
  @Override
  public String toString() {
    return "SBMLDocument Level " + getLevel() + " Version " + getVersion();
  }


  /**
   * Sets the {@link Model} of this {@link SBMLDocument} to null and notifies
   * all {@link TreeNodeChangeListener} about changes.
   * 
   * @return {@code true} if calling this method changed the properties
   *         of this element.
   */
  public boolean unsetModel() {
    if (model != null) {
      Model oldModel = model;
      model = null;
      oldModel.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    attributes.putAll(SBMLDocumentAttributes);

    if (isSetLevel()) {
      attributes.put("level", Integer.toString(getLevel()));
    }
    if (isSetVersion()) {
      attributes.put("version", Integer.toString(getVersion()));
    }

    Iterator<Map.Entry<String, String>> it =
        getDeclaredNamespaces().entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry<String, String> entry = it.next();
      if (!entry.getKey().equals("xmlns")) {
        attributes.put(entry.getKey(), entry.getValue());
      }
    }
    return attributes;
  }

}
