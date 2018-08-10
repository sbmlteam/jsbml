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

import static java.text.MessageFormat.format;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.tree.TreeNode;
import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ext.AbstractSBasePlugin;
import org.sbml.jsbml.ext.SBasePlugin;
import org.sbml.jsbml.util.IdManager;
import org.sbml.jsbml.util.SBMLtools;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.TreeNodeChangeEvent;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.jsbml.util.ValuePair;
import org.sbml.jsbml.util.converters.LevelVersionConverter;
import org.sbml.jsbml.util.filters.MetaIdFilter;
import org.sbml.jsbml.util.filters.SIdFilter;
import org.sbml.jsbml.validator.SyntaxChecker;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.xml.XMLAttributes;
import org.sbml.jsbml.xml.XMLNode;
import org.sbml.jsbml.xml.XMLTriple;
import org.sbml.jsbml.xml.parsers.AbstractReaderWriter;
import org.sbml.jsbml.xml.parsers.PackageParser;
import org.sbml.jsbml.xml.parsers.PackageUtil;
import org.sbml.jsbml.xml.parsers.ParserManager;

/**
 * The base class for each {@link SBase} component.
 * 
 * @author Andreas Dr&auml;ger
 * @author Nicolas Rodriguez
 * @author Marine Dumousseau
 * @since 0.8
 * 
 */
public abstract class AbstractSBase extends AbstractTreeNode implements SBase {

  /**
   * @author Nicolas Rodriguez
   */
  private static enum NOTES_TYPE {
    /**
     * 
     */
    NotesAny,
    /**
     * 
     */
    NotesBody,
    /**
     * 
     */
    NotesHTML
  }

  /**
   * A logger for this class.
   */
  private static final transient Logger logger             =
      Logger.getLogger(AbstractSBase.class);

  /**
   * Generated serial version identifier.
   */
  private static final long             serialVersionUID   =
      8781459818293592636L;

  /**
   * 
   */
  public static final transient String JSBML_WRONG_SBO_TERM = "jsbml.wrong.sbo.term";

  /**
   * Shared context to perform attribute validation.
   */
  private static ValidationContext      attributeValidator =
      new ValidationContext(1, 1);


  /**
   * Returns {@code true} if the level and version combination is a valid one,
   * {@code false} otherwise.
   * 
   * @param level
   *        the SBML level
   * @param version
   *        the SBML version
   * @return {@code true} if the level and version combination is a valid one,
   *         {@code false} otherwise.
   */
  public static boolean isValidLevelAndVersionCombination(int level,
    int version) {
    switch (level) {
    case 1:
      return ((1 <= version) && (version <= 2));
    case 2:
      return ((1 <= version) && (version <= 5));
    case 3:
      return ((1 <= version) && (version <= 2));
    case -1:
      return (version == -1);
    default:
      return false;
    }
  }


  /**
   * Checks if the given identifier candidate satisfies the requirements for a
   * valid meta identifier (see SBML L2V4 p. 12 for details).
   * 
   * @param idCandidate the id to check
   * @return {@code true} if the given argument is a valid meta identifier
   *         {@link String}, {@code false} otherwise.
   * @deprecated use {@link SyntaxChecker#isValidMetaId(String)}
   */
  @Deprecated
  public static final boolean isValidMetaId(String idCandidate) {
    return SyntaxChecker.isValidMetaId(idCandidate);
  }

  /**
   * Annotations of the SBML component. Matches the annotation XML node in a
   * SBML file.
   */
  private Annotation                           annotation;

  /**
   * Contains all the namespaces declared on the XML node with their prefixes.
   */
  private final Map<String, String>            declaredNamespaces;

  /**
   * the namespace which this SBase element belong to.
   */
  private String                               elementNamespace;

  /**
   * {@link Map} containing the SBML extension object of additional packages
   * with the appropriate name space of the package.
   */
  private final SortedMap<String, SBasePlugin> extensions;

  /**
   * {@link Map} containing the ignored package objects.
   * <p>
   * Package are considered ignored if JSBML does not support this package or
   * support the package but not the associated namespace.
   */
  protected SortedMap<String, XMLNode>         ignoredExtensions;

  /**
   * Contains the unknown XML attributes or elements.
   */
  protected XMLNode                            ignoredXMLElements;

  /**
   * Level and version of the SBML component. Matches the level XML attribute of
   * an SBML node.
   */
  ValuePair<Integer, Integer>                  lv;

  /**
   * metaid of the SBML component. Matches the metaid XML attribute of an
   * element in a SBML file.
   */
  private String                               metaId;

  /**
   * notes of the SBML component. Matches the notes XML node in a SBML file.
   */
  private XMLNode                              notesXMLNode;

  /**
   * the name of the package which this SBase element belong to, 'core' by
   * default.
   */
  protected String                             packageName    = "core";

  /**
   * the version of the package which this SBase element belong to, '0' by
   * default for core.
   */
  private int                                  packageVersion = 0;

  /**
   * sbo term of the SBML component. Matches the sboTerm XML attribute of an
   * element in a SBML file.
   */
  private int                                  sboTerm;

  /**
   * id of the SBML component (can be optional depending on the level and
   * version). Matches the id attribute of an element in a SBML file.
   */
  private String id;

  /**
   * name of the SBML component (can be optional depending on the level and
   * version). Matches the name attribute of an element in a SBML file.
   */
  private String name;

  /**
   * Creates an AbstractSBase instance.
   * <p>
   * By default, the sboTerm is -1, the
   * metaid, notes, parentSBMLObject, annotation, and
   * notes are {@code null}. The level and version are set to -1.
   * The setOfListeners list and the extensions hash map
   * are empty.
   */
  public AbstractSBase() {
    super();
    sboTerm = -1;
    metaId = null;
    id = null;
    name = null;
    notesXMLNode = null;
    lv = getLevelAndVersion();
    annotation = null;
    extensions = new TreeMap<String, SBasePlugin>();
    elementNamespace = null;
    declaredNamespaces = new TreeMap<String, String>();
  }


  /**
   * Creates an {@link AbstractSBase} instance with the given SBML Level and
   * Version.
   * <p>
   * By default, the sboTerm is -1, the metaid, notes,
   * {@link #parent}, {@link #annotation}, and notes are {@code null}. The
   * {@link #declaredNamespaces} list and the {@link #extensions} {@link Map}
   * are
   * empty.
   * 
   * @param level
   *        the SBML level
   * @param version
   *        the SBML version
   */
  public AbstractSBase(int level, int version) {
    this();

    lv.setL(level);
    lv.setV(version);

    if (!hasValidLevelVersionNamespaceCombination()) {

      if (!isReadingInProgress()) {
        throw new LevelVersionError(this);
      } else {
        logger.error(format(LevelVersionError.UNDEFINED_LEVEL_VERSION_COMBINATION_MSG, level, version));
      }
    }
  }


  /**
   * Creates an {@link AbstractSBase} instance from a given
   * {@link AbstractSBase}.
   * 
   * @param sb
   *        an {@link AbstractSBase} object to clone
   */
  public AbstractSBase(SBase sb) {
    super(sb);

    // extensions is needed when doing getChildCount()
    extensions = new TreeMap<String, SBasePlugin>();
    elementNamespace = null;
    packageName = sb.getPackageName();
    packageVersion = sb.getPackageVersion();
    declaredNamespaces = new TreeMap<String, String>();

    if (sb.isSetLevel()) {
      setLevel(sb.getLevel());
    }
    if (sb.isSetVersion()) {
      setVersion(sb.getVersion());
    }
    id = sb.isSetId() ? new String(sb.getId()) : null;
    name = sb.isSetName() ? new String(sb.getName()) : null;

    if (sb.isSetSBOTerm()) {
      sboTerm = sb.getSBOTerm();
    } else {
      sboTerm = -1;
    }
    if (sb.isSetMetaId()) {
      metaId = new String(sb.getMetaId());
    }
    if (sb.isSetNotes()) {
      setNotes(sb.getNotes().clone());
    }
    if (sb.isSetAnnotation()) {
      setAnnotation(sb.getAnnotation().clone());
    }
    if (sb.isExtendedByOtherPackages()) {

      for (String key : sb.getExtensionPackages().keySet()) {

        SBasePlugin plugin = sb.getExtensionPackages().get(key);
        SBasePlugin clonedPlugin = plugin.clone();

        addExtension(new String(key), clonedPlugin);
      }
    }
    // cloning namespace
    if (sb.getNamespace() != null) {
      elementNamespace = sb.getNamespace();
    }
    if (sb.getDeclaredNamespaces().size() > 0) {
      for (String namespacePrefix : sb.getDeclaredNamespaces().keySet()) {
        declaredNamespaces.put(new String(namespacePrefix),
          new String(sb.getDeclaredNamespaces().get(namespacePrefix)));
      }
    }

  }

  /**
   * Creates an {@link AbstractSBase} with the given identifier.
   * 
   *  <p>Note
   * that with this constructor the level and version of the element are not
   * specified. But when adding the SBase inside a Model, it will inherit
   * the level and version from his parent.
   * </p>
   * 
   * @param id the id of this {@code AbstractSBase}
   */
  public AbstractSBase(String id) {
    this();
    setId(id);
  }

  /**
   * Creates an {@link AbstractSBase} from an id, level and version.
   * 
   * @param id the id of this {@code AbstractSBase}
   * @param level the SBML level
   * @param version the SBML version
   */
  public AbstractSBase(String id, int level, int version) {
    this(id, null, level, version);
  }

  /**
   * Creates an {@link AbstractSBase} from an id, name, level and version.
   * 
   * @param id the id of this {@code AbstractSBase}
   * @param name the name of this {@code AbstractSBase}
   * @param level the SBML level
   * @param version the SBML version
   */
  public AbstractSBase(String id, String name, int level, int version) {
    this(level, version);
    setId(id);
    setName(name);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#addCVTerm(org.sbml.jsbml.CVTerm)
   */
  @Override
  public boolean addCVTerm(CVTerm term) {
    return getAnnotation().addCVTerm(term);
  }


  /**
   * Adds an additional name space to the set of declared namespaces of this
   * {@link SBase}.
   * 
   * @param prefix
   *        the prefix of the namespace to add
   * @param namespace
   *        the namespace to add
   */
  @Override
  public void addDeclaredNamespace(String prefix, String namespace) {
    if ((!prefix.startsWith("xmlns:")) && (!prefix.equals("xmlns"))) {
      if (prefix.indexOf(':') != -1) {
        throw new IllegalArgumentException(
          resourceBundle.getString("AbstractSBase.addDeclaredNamespace"));
      }
      prefix = "xmlns:" + prefix;
    }
    declaredNamespaces.put(prefix, namespace);
    firePropertyChange(TreeNodeChangeEvent.addDeclaredNamespace, null,
      namespace);
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#addExtension(java.lang.String,
   * org.sbml.jsbml.SBase)
   */
  @Override
  public void addExtension(String nameOrUri, SBasePlugin sbasePlugin) {

    if (!isPackageEnabled(nameOrUri)) {
      enablePackage(nameOrUri);
    }

    // use always the package name in the map
    PackageParser packageParser =
        ParserManager.getManager().getPackageParser(nameOrUri);

    if (packageParser != null) {

      // unset the previous plugin if needed
      if (extensions.get(packageParser.getPackageName()) != null) {
        unsetPlugin(packageParser.getPackageName());
      }

      extensions.put(packageParser.getPackageName(), sbasePlugin);

      // Making sure that the correct extendedSBase is set in the SBasePlugin
      // And that all the ids and metaids are registered
      if ((sbasePlugin.getExtendedSBase() == null)
          || (sbasePlugin.getExtendedSBase() != this)) {
        ((AbstractSBasePlugin) sbasePlugin).setExtendedSBase(this);
      }

      // the package namespace and version will be set in firePropertyChange
      firePropertyChange(TreeNodeChangeEvent.addExtension, null, sbasePlugin);
    } else {
      throw new IllegalArgumentException(format(
        resourceBundle.getString("AbstractSBase.addExtensionExc"), nameOrUri));
    }
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#addPlugin(java.lang.String,
   * org.sbml.jsbml.ext.SBasePlugin)
   */
  @Override
  public void addPlugin(String nameOrUri, SBasePlugin sbasePlugin) {
    addExtension(nameOrUri, sbasePlugin);
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#appendAnnotation(java.lang.String)
   */
  @Override
  public void appendAnnotation(String annotation) throws XMLStreamException {
    getAnnotation().appendNonRDFAnnotation(annotation);
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#appendAnnotation(org.sbml.jsbml.xml.XMLNode)
   */
  @Override
  public void appendAnnotation(XMLNode annotation) {
    getAnnotation().appendNonRDFAnnotation(annotation);
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#appendNotes(java.lang.String)
   */
  @Override
  public void appendNotes(String notes) throws XMLStreamException {
    XMLNode addedNotes =
        XMLNode.convertStringToXMLNode(StringTools.toXMLNotesString(notes));
    if (isSetNotes()) {
      XMLNode oldNotes = notesXMLNode.clone();
      appendNotes(addedNotes);
      firePropertyChange(TreeNodeChangeEvent.notes, oldNotes, notesXMLNode);
    } else {
      setNotes(addedNotes);
    }
  }


  /**
   * Appends notes to the existing notes.
   * <p>
   * This allows other notes to be preserved whilst
   * adding additional information.
   * 
   * @param notes the notes to append.
   */
  @Override
  public void appendNotes(XMLNode notes) {

    if (notes == null) {
      return;
    }

    String name = notes.getName();

    // The content of notes in SBML can consist only of the following
    // possibilities:
    //
    // 1. A complete XHTML document (minus the XML and DOCTYPE
    // declarations), that is, XHTML content beginning with the
    // html tag.
    // (notesType is NotesHTML.)
    //
    // 2. The body element from an XHTML document.
    // (notesType is NotesBody.)
    //
    // 3. Any XHTML content that would be permitted within a body
    // element, each one must declare the XML namespace separately.
    // (notesType is NotesAny.)
    //

    NOTES_TYPE addedNotesType = NOTES_TYPE.NotesAny;
    XMLNode addedNotes =
        new XMLNode(new XMLTriple("notes", "", ""), new XMLAttributes());

    // ------------------------------------------------------------
    //
    // STEP1: identifies the type of the given notes
    //
    // ------------------------------------------------------------

    if (name == "notes") {
      /*
       * check for notes tags on the added notes and strip if present and
       * the notes tag has "html" or "body" element
       */

      if (notes.getChildCount() > 0) {
        // notes.getChildAt(0) must be "html", "body", or any XHTML
        // element that would be permitted within a "body" element
        // (e.g. <p>..</p>, <br>..</br> and so forth).

        int firstElementIndex = getFirstElementIndex(notes);
        String cname = "";

        if (firstElementIndex != -1) {
          cname = notes.getChildAt(firstElementIndex).getName(); // we need to
          // find the
          // first child
          // element
        }

        if (cname == "html") {
          addedNotes = notes.getChildAt(firstElementIndex);
          addedNotesType = NOTES_TYPE.NotesHTML;
        } else if (cname == "body") {
          addedNotes = notes.getChildAt(firstElementIndex);
          addedNotesType = NOTES_TYPE.NotesBody;
        } else {
          // the notes tag must NOT be stripped if notes.getChildAt(0) node
          // is neither "html" nor "body" element because the children of
          // the addedNotes will be added to the current notes later if the node
          // is neither "html" nor "body".
          addedNotes = notes;
          addedNotesType = NOTES_TYPE.NotesAny;
        }
      } else {
        // the given notes is empty
        logger.info(resourceBundle.getString("AbstractSBase.emptyNotes"));
        return;
      }
    } else // name != "notes"
    {

      if (!notes.isStart() && !notes.isEnd() && !notes.isText()) {
        if (notes.getChildCount() > 0) {
          addedNotes = notes;
          addedNotesType = NOTES_TYPE.NotesAny;
        } else {
          // the given notes is empty
          return;
        }
      } else {
        if (name == "html") {
          addedNotes = notes;
          addedNotesType = NOTES_TYPE.NotesHTML;
        } else if (name == "body") {
          addedNotes = notes;
          addedNotesType = NOTES_TYPE.NotesBody;
        } else {
          // The given notes node needs to be added to a parent node
          // if the node is neither "html" nor "body" element because the
          // children of addedNotes will be added to the current notes later if
          // the
          // node is neither "html" nor "body" (i.e., any XHTML element that
          // would be permitted within a "body" element)
          addedNotes.addChild(notes);
          addedNotesType = NOTES_TYPE.NotesAny;
        }
      }
    }

    //
    // checks the addedNotes of "html" if the html tag contains "head" and
    // "body" tags which must be located in this order.
    //
    if (addedNotesType == NOTES_TYPE.NotesHTML) {
      boolean headFound = false;
      boolean bodyFound = false;
      boolean otherElementFound = false;

      for (int i = 0; i < addedNotes.getChildCount(); i++) {
        XMLNode child = addedNotes.getChildAt(i);

        if (child.isElement()) {
          if (child.getName().equals("head")) {
            headFound = true;
          } else if (child.getName().equals("body") && headFound) {
            bodyFound = true;
          } else {
            otherElementFound = true;
          }
        }
      }

      if (!headFound || !bodyFound || otherElementFound) {
        // TODO - throw an exception as well
        logger.warn(
          resourceBundle.getString("AbstractSBase.invalidNotesStructure"));
        return;
      }
    }

    // We do not have a Syntax checker working on XMLNode !!
    // check whether notes is valid xhtml ?? (libsbml is doing that)

    if (notesXMLNode != null) {
      // ------------------------------------------------------------
      //
      // STEP2: identifies the type of the existing notes
      //
      // ------------------------------------------------------------

      NOTES_TYPE curNotesType = NOTES_TYPE.NotesAny;
      XMLNode curNotes = notesXMLNode;

      // curNotes.getChildAt(0) must be "html", "body", or any XHTML
      // element that would be permitted within a "body" element .

      int firstElementIndex = getFirstElementIndex(curNotes);
      String cname = "";

      if (firstElementIndex != -1) {
        cname = curNotes.getChildAt(firstElementIndex).getName(); // we need to
        // find the
        // first child
        // element
      }

      if (cname == "html") {
        curNotesType = NOTES_TYPE.NotesHTML;
      } else if (cname == "body") {
        curNotesType = NOTES_TYPE.NotesBody;
      } else {
        curNotesType = NOTES_TYPE.NotesAny;
      }

      /*
       * BUT we also have the issue of the rules relating to notes
       * contents and where to add them ie we cannot add a second body element
       * etc...
       */

      // ------------------------------------------------------------
      //
      // STEP3: appends the given notes to the current notes
      //
      // ------------------------------------------------------------

      int i;

      if (curNotesType == NOTES_TYPE.NotesHTML) {
        XMLNode curHTML = curNotes.getChildElement("html", null);
        XMLNode curBody = curHTML.getChildElement("body", null);

        if (addedNotesType == NOTES_TYPE.NotesHTML) {
          // adds the content of the body of given html tag to the current body
          // tag

          XMLNode addedBody = addedNotes.getChildElement("body", null);

          for (i = 0; i < addedBody.getChildCount(); i++) {
            if (curBody.addChild(addedBody.getChildAt(i)) < 0) {
              logger.warn(format(
                resourceBundle.getString("AbstractSBase.problemAddingXMLNode"),
                SBMLtools.toXML(addedBody.getChildAt(i))));
              return;
            }
          }
          // we could add the content of the 'head' tag as well ?
        } else if ((addedNotesType == NOTES_TYPE.NotesBody)
            || (addedNotesType == NOTES_TYPE.NotesAny)) {
          // adds the given body or other tag (permitted in the body) to the
          // current
          // html tag

          for (i = 0; i < addedNotes.getChildCount(); i++) {
            if (curBody.addChild(addedNotes.getChildAt(i)) < 0) {
              logger.warn(format(
                resourceBundle.getString("AbstractSBase.problemAddingXMLNode"),
                SBMLtools.toXML(addedNotes.getChildAt(i))));
              return;
            }
          }
        }
      } else if (curNotesType == NOTES_TYPE.NotesBody) {
        if (addedNotesType == NOTES_TYPE.NotesHTML) {
          // adds the given html tag to the current body tag

          XMLNode addedBody = addedNotes.getChildElement("body", null);
          XMLNode curBody = curNotes.getChildElement("body", null);

          for (i = 0; i < curBody.getChildCount(); i++) {
            addedBody.insertChild(i, curBody.getChildAt(i));
          }

          notesXMLNode.removeChild(firstElementIndex);
          notesXMLNode.insertChild(firstElementIndex, addedNotes);
        } else if ((addedNotesType == NOTES_TYPE.NotesBody)
            || (addedNotesType == NOTES_TYPE.NotesAny)) {
          // adds the given body or other tag (permitted in the body) to the
          // current
          // body tag

          XMLNode curBody = curNotes.getChildElement("body", null);

          for (i = 0; i < addedNotes.getChildCount(); i++) {
            if (curBody.addChild(addedNotes.getChildAt(i)) < 0) {
              logger.warn(format(
                resourceBundle.getString("AbstractSBase.appendNotes"),
                SBMLtools.toXML(addedNotes.getChildAt(i))));
              return;
            }
          }
        }
      } else if (curNotesType == NOTES_TYPE.NotesAny) {
        if (addedNotesType == NOTES_TYPE.NotesHTML) {
          // adds the given html tag to the current any tag permitted in the
          // body.

          XMLNode addedBody = addedNotes.getChildElement("body", null);

          for (i = 0; i < curNotes.getChildCount(); i++) {
            addedBody.addChild(curNotes.getChildAt(i));
          }

          notesXMLNode.removeChildren();
          notesXMLNode.addChild(addedNotes);
        } else if (addedNotesType == NOTES_TYPE.NotesBody) {
          // adds the given body tag to the current any tag permitted in the
          // body.

          for (i = 0; i < curNotes.getChildCount(); i++) {
            addedNotes.addChild(curNotes.getChildAt(i));
          }

          notesXMLNode.removeChildren();
          notesXMLNode.addChild(addedNotes);
        } else if (addedNotesType == NOTES_TYPE.NotesAny) {
          // adds the given any tag permitted in the notes to that of the
          // current
          // any tag.

          for (i = 0; i < addedNotes.getChildCount(); i++) {
            if (curNotes.addChild(addedNotes.getChildAt(i)) < 0) {
              logger.warn(format(
                resourceBundle.getString("AbstractSBase.appendNotes"),
                SBMLtools.toXML(addedNotes.getChildAt(i))));
              return;
            }
          }
        }
      }
    } else // if (mNotes == NULL)
    {
      // TODO - check that there is a 'notes' top level element
      setNotes(notes);
    }
  }


  /**
   * Checks if the attribute conforms to the SBML specifications of the level
   * and version of this object. The final values of {@link TreeNodeChangeEvent}
   * could be used as attribute name.
   * 
   * <p>WARNING: this is a placeholder method, most classes have no attribute
   * validations implemented.
   * 
   * @param attributeName - the attribute name
   * @return {@code true} if the attribute conforms the the SBML specifications.
   */
  protected boolean checkAttribute(String attributeName) {

    // do not do the checks if we are reading a model or in the process of cloning any TreeNode
    if (! (isReadingInProgress() || isCloningInProgress())) {
      AbstractSBase.attributeValidator.setLevelAndVersion(getLevel(),
        getVersion());

      Class<?> clazz = this.getClass();
      attributeValidator.loadConstraintsForAttribute(clazz, attributeName);

      boolean valid = attributeValidator.validate(this);

      if (!valid)
      {
        logger.error("Invalid value for attribute " + attributeName
          + " on " + clazz.getSimpleName() + " with id = " + metaId + "!");
      }

      return valid;
    }

    return true;
  }


  /**
   * Checks whether or not the given {@link SBase} has the same level and
   * version configuration than this element. If the L/V combination for the
   * given {@code sbase} is not yet defined, this method sets it to the
   * identical values as it is for the current object.
   * 
   * @param sbase
   *        the element to be checked.
   * @return {@code true} if the given {@code sbase} and this object
   *         have the same L/V configuration.
   * @throws LevelVersionError
   *         In case the given {@link SBase} has a different, but defined
   *         Level/Version combination than this current {@link SBase}, an
   *         {@link LevelVersionError} is thrown. This method is only
   *         package-wide visible because it is not intended to be a
   *         <i>real</i> check, rather than to indicate potential errors.
   */
  protected boolean checkLevelAndVersionCompatibility(SBase sbase) {
    if (sbase.getLevelAndVersion().equals(getLevelAndVersion())) {
      return true;
    }
    if (isSetLevelAndVersion()
        && (!sbase.isSetLevelAndVersion() || (sbase.isSetLevel()
            && (sbase.getLevel() == getLevel()) && !sbase.isSetVersion()))
        && (sbase instanceof AbstractSBase)) {
      ((AbstractSBase) sbase).setLevelAndVersion(getLevel(), getVersion(),
        true);
      return true;
    }
    throw new LevelVersionError(this, sbase);
  }


  /**
   * Checks whether or not the given {@link SBase} has the same package
   * version configuration than this element or the SBMLDocument. If the package
   * namespace and version combination
   * for the given {@code sbase} is not yet defined, this method sets it to the
   * identical values as it is for the current object or the SBMLDocument.
   * 
   * @param sbase
   *        the element to be checked.
   * @return {@code true} if the given {@code sbase} and this object
   *         have the same package configuration.
   * @throws LevelVersionError
   *         In case the given {@link SBase} has a different, but defined
   *         package version combination than this current {@link SBase}, an
   *         {@link LevelVersionError} is thrown. This method is only
   *         package-wide visible because it is not intended to be a
   *         <i>real</i> check, rather than to indicate potential errors.
   */
  protected boolean checkAndSetPackageNamespaceAndVersion(SBase sbase) {
    String expectedPackageNamespace = null;
    int expectedPackageVersion = -1;
    String packageLabel = sbase.getPackageName();

    if (packageLabel.equals("core")) {
      return true;
    }

    if (getPackageName().equals(sbase.getPackageName())) {
      expectedPackageNamespace = getNamespace();
      expectedPackageVersion = getPackageVersion();
    } else if (isSetPlugin(packageLabel)) {
      SBasePlugin parentSBasePlugin = getPlugin(packageLabel);
      expectedPackageNamespace = parentSBasePlugin.getElementNamespace();
      expectedPackageVersion = parentSBasePlugin.getPackageVersion();
    }

    if (expectedPackageVersion == sbase.getPackageVersion()
        && expectedPackageNamespace != null
        && expectedPackageNamespace.equals(sbase.getNamespace())) {
      return true;
    } else {
      return ((AbstractSBase) sbase).setPackageNamespaceAndVersion(packageLabel,
        expectedPackageNamespace, expectedPackageVersion);
    }
  }


  /**
   * Checks whether or not the given {@link SBasePlugin} has the same package
   * configuration than the SBMLDocument. If the package namespace and version
   * combination
   * for the given {@code sbasePlugin} is not yet defined, this method sets it
   * to the
   * identical values as it is for the current SBMLDocument.
   * 
   * @param sbasePlugin
   *        the element to be checked.
   * @return {@code true} if the given {@code sbase} and this object
   *         have the same package configuration.
   * @throws LevelVersionError
   *         In case the given {@link SBase} has a different, but defined
   *         package version combination than this current {@link SBase}, an
   *         {@link LevelVersionError} is thrown. This method is only
   *         package-wide visible because it is not intended to be a
   *         <i>real</i> check, rather than to indicate potential errors.
   */
  protected boolean checkAndSetPackageNamespaceAndVersion(
    SBasePlugin sbasePlugin, SBMLDocument doc) {

    if (doc != null) {
      String packageNamespace =
          doc.getEnabledPackageNamespace(sbasePlugin.getPackageName());

      if (packageNamespace == null) {
        return false;
      }

      int packageVersion = PackageUtil.extractPackageVersion(packageNamespace);

      if (packageVersion == sbasePlugin.getPackageVersion()
          && packageNamespace != null
          && packageNamespace.equals(sbasePlugin.getElementNamespace())) {
        return true;
      } else {
        sbasePlugin.setPackageVersion(packageVersion);
        ((AbstractSBasePlugin) sbasePlugin).setNamespace(packageNamespace);

        boolean success = true;
        Enumeration<TreeNode> children = children();

        while (children.hasMoreElements()) {
          TreeNode child = children.nextElement();

          if (child instanceof AbstractSBase
              && ((AbstractSBase) child).getPackageName()
              .equals(sbasePlugin.getPackageName())) {
            success &= ((AbstractSBase) child).setPackageNamespaceAndVersion(
              sbasePlugin.getPackageName(), packageNamespace, packageVersion);
          }
        }

        return success;
      }
    }

    return true;
  }


  /*
   * (non-Javadoc)
   * @see java.lang.Object#clone()
   */
  @Override
  public abstract AbstractSBase clone();


  /**
   * Creates a new {@link History} and associates it with the annotation of
   * this element. If no {@link Annotation} exists, a new such element is
   * created as well.
   * 
   * @return A new {@link History} instance that is directly associated with
   *         this element.
   * @see #getHistory()
   */
  public History createHistory() {
    return getHistory();
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#createPlugin(java.lang.String)
   */
  @Override
  public SBasePlugin createPlugin(String nameOrUri) {
    // use always the package name in the map
    PackageParser packageParser =
        ParserManager.getManager().getPackageParser(nameOrUri);

    if (packageParser != null) {
      SBasePlugin sbasePlugin = packageParser.createPluginFor(this);
      addExtension(nameOrUri, sbasePlugin);
      return sbasePlugin;
    }

    throw new IllegalArgumentException(format(
      resourceBundle.getString("AbstractSBase.createPlugin"), nameOrUri));
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
    SBMLDocument doc = getSBMLDocument();

    if (doc != null) {
      doc.enablePackage(packageURIOrName, enabled);
    } else if (logger.isDebugEnabled()) {
      logger.debug(resourceBundle.getString("AbstractSBase.enablePackage"));
    }
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);

    if (equals) {
      /*
       * Casting will be no problem because the super class has just
       * checked that the class of this Object equals the class of the
       * given object.
       */
      SBase sbase = (SBase) object;
      equals &= sbase.isSetMetaId() == isSetMetaId();
      if (equals && sbase.isSetMetaId()) {
        equals &= sbase.getMetaId().equals(getMetaId());
      }
      if (equals) {
        equals &= sbase.isSetId() == isSetId();
        if (equals && isSetId()) {
          equals &= sbase.getId().equals(getId());
        }
        equals &= sbase.isSetName() == isSetName();
        if (equals && sbase.isSetName()) {
          equals &= sbase.getName().equals(getName());
        }
      }

      /*
       * All child nodes are already checked by the recursive method in
       * AbstractTreeNode. We here have to check the following own items
       * only:
       */
      equals &= sbase.isSetSBOTerm() == isSetSBOTerm();
      if (equals && sbase.isSetSBOTerm()) {
        equals &= sbase.getSBOTerm() == getSBOTerm();
      }
      equals &= sbase.isSetLevelAndVersion() == isSetLevelAndVersion();
      if (equals && sbase.isSetLevelAndVersion()) {
        equals &= sbase.getLevelAndVersion().equals(getLevelAndVersion());
      }

      equals &=
          getNamespace() != null ? getNamespace().equals(sbase.getNamespace())
            : sbase.getNamespace() == null;

          if (declaredNamespaces == null) {
            if (sbase.getDeclaredNamespaces() != null) {
              return false;
            }
          } else if (!declaredNamespaces.equals(sbase.getDeclaredNamespaces())) {
            return false;
          }

          /*
           * Note: Listeners, ignoredExtensions and ignoredXMLElements are not
           * included in the equals check.
           */

          // Notes, Annotation and extension SBasePlugins are tested in
          // AbstractTreeNode.equals()
          // as they are part of the children returned by #getChildAt(int i)
    }

    return equals;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#filterCVTerms(org.sbml.jsbml.CVTerm.Qualifier)
   */
  @Override
  public List<CVTerm> filterCVTerms(CVTerm.Qualifier qualifier) {
    return getAnnotation().filterCVTerms(qualifier);
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#filterCVTerms(org.sbml.jsbml.CVTerm.Qualifier,
   * boolean, java.lang.String[])
   */
  @Override
  public List<String> filterCVTerms(CVTerm.Qualifier qualifier,
    boolean recursive, String... patterns) {
    List<String> l = new ArrayList<String>();
    for (CVTerm c : filterCVTerms(qualifier)) {
      l.addAll(c.filterResources(patterns));
    }
    if (recursive) {
      TreeNode child;
      for (int i = 0; i < getChildCount(); i++) {
        child = getChildAt(i);
        if (child instanceof SBase) {
          l.addAll(
            ((SBase) child).filterCVTerms(qualifier, recursive, patterns));
        }
      }
    }
    return l;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#filterCVTerms(org.sbml.jsbml.CVTerm.Qualifier,
   * java.lang.String)
   */
  @Override
  public List<String> filterCVTerms(CVTerm.Qualifier qualifier,
    String pattern) {
    return filterCVTerms(qualifier, pattern, false);
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#filterCVTerms(org.sbml.jsbml.CVTerm.Qualifier,
   * java.lang.String, boolean)
   */
  @Override
  public List<String> filterCVTerms(CVTerm.Qualifier qualifier, String pattern,
    boolean recursive) {
    return filterCVTerms(qualifier, recursive, pattern);
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#fireNodeRemovedEvent()
   */
  @Override
  public void fireNodeRemovedEvent() {

    TreeNode parent = getParent();

    if (logger.isDebugEnabled()) {
      logger.debug(format(
        resourceBundle.getString("AbstractSBase.fireNodeRemovedEvent"), this,
        parent));
    }

    if ((parent != null) && (parent instanceof SBase)) {
      ((SBase) parent).unregisterChild(this);
    }

    super.fireNodeRemovedEvent();
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#firePropertyChange(java.lang.String,
   * java.lang.Object, java.lang.Object)
   */
  @Override
  public void firePropertyChange(String propertyName, Object oldValue,
    Object newValue) {
    // TODO - this method is used to add or remove SBase or SBasePlugin, we
    // should make sure to handle the registration/un-registration
    // in those cases.

    // the parent need to be set as well (would be done, if we call the
    // registerChild method)
    if ((newValue != null) && (newValue instanceof SBasePlugin)) {
      ((AbstractTreeNode) newValue).setParent(this);

      // set the proper package namespace and version
      SBMLDocument doc = getSBMLDocument();
      SBasePlugin sbasePlugin = (SBasePlugin) newValue;

      if (doc != null) {
        checkAndSetPackageNamespaceAndVersion(sbasePlugin, doc);
      }

    }

    if ((oldValue != null) && (oldValue instanceof SBasePlugin)) {
      unregisterChild((SBasePlugin) oldValue);
    }

    if ((oldValue != null && oldValue instanceof SBase)
        && !propertyName.equals(TreeNodeChangeEvent.parentSBMLObject)) {
      unregisterChild((SBase) oldValue);
    }

    // This case is generally handled properly in the setters or in
    // AbstractSBasePlugin#setExtendedSBase
    // but it would be better and more consistent to handle it there
    // if (newValue != null && newValue instanceof SBasePlugin) {
    // registerChild((SBasePlugin) newValue);
    // }
    // if (newValue != null && newValue instanceof SBase) {
    // registerChild((SBase) newValue);
    // }

    super.firePropertyChange(propertyName, oldValue, newValue);
  }

  /*
   * (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return false;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#getAnnotation()
   */
  @Override
  public Annotation getAnnotation() {
    if (!isSetAnnotation()) {
      setAnnotation(new Annotation());
    }
    return annotation;
  }


  /**
   * Returns the {@link Annotation} of this SBML object as a {@link String}.
   * 
   * @return the {@link Annotation} of this SBML object as a {@link String} or
   *         an empty {@link String} if there are no {@link Annotation}.
   * @throws XMLStreamException if there is a problem writing the annotation to XML.
   */
  @Override
  public String getAnnotationString() throws XMLStreamException {
    // return isSetAnnotation() ? (new SBMLWriter()).writeAnnotation(this) : "";
    return isSetAnnotation() ? annotation.getFullAnnotationString() : "";
  }


  /*
   * (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int childIndex) {
    if (childIndex < 0) {
      throw new IndexOutOfBoundsException(format(
        resourceBundle.getString("IndexSurpassesBoundsException"), childIndex,
        0));
    }
    int pos = 0;
    if (isSetNotes()) {
      if (childIndex == pos) {
        return getNotes();
      }
      pos++;
    }
    if (isSetAnnotation()) {
      if (childIndex == pos) {
        return getAnnotation();
      }
      pos++;
    }

    if (extensions.size() > 0) {
      for (SBasePlugin sbasePlugin : extensions.values()) {
        int sbasePluginNbChildren = sbasePlugin.getChildCount();

        if ((pos + sbasePluginNbChildren) > childIndex) {
          return sbasePlugin.getChildAt(childIndex - pos);
        } else {
          pos += sbasePluginNbChildren;
        }
      }
    }

    throw new IndexOutOfBoundsException(isLeaf()
      ? format(
        resourceBundle.getString("IndexExceedsBoundsException2"),
        getElementName())
        : format(
          resourceBundle.getString("IndexExceedsBoundsException"), childIndex,
          Math.min(pos, 0)));
  }


  /*
   * (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = 0;
    if (isSetNotes()) {
      count++;
    }
    if (isSetAnnotation()) {
      count++;
    }

    for (SBasePlugin sbasePlugin : extensions.values()) {
      count += sbasePlugin.getChildCount();
    }

    return count;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#getCVTerm(int)
   */
  @Override
  public CVTerm getCVTerm(int index) {
    if (isSetAnnotation()) {
      return annotation.getCVTerm(index);
    }
    throw new IndexOutOfBoundsException(format(
      resourceBundle.getString("AbstractSBase.getCVTerm"), index));
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#getCVTermCount()
   */
  @Override
  public int getCVTermCount() {
    return isSetAnnotation() ? annotation.getListOfCVTerms().size() : 0;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#getCVTerms()
   */
  @Override
  public List<CVTerm> getCVTerms() {
    return getAnnotation().getListOfCVTerms();
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#getNamespaces()
   */
  @Override
  public Map<String, String> getDeclaredNamespaces() {
    // Need to separate the list of name spaces from the extensions.
    // SBase object directly from the extension need to set their name space.

    return declaredNamespaces;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#getElementBySId()
   */
  @Override
  public SBase getElementBySId(String id) {
    @SuppressWarnings("unchecked")
    List<SBase> foundSBases = (List<SBase>) this.filter(new SIdFilter(id), false, true);

    if (foundSBases != null && foundSBases.size() == 1) {
      return foundSBases.get(0);
    }

    return null;
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#getElementByMetaId()
   */
  @Override
  public SBase getElementByMetaId(String id) {
    @SuppressWarnings("unchecked")
    List<SBase> foundSBases = (List<SBase>) this.filter(new MetaIdFilter(id), false, true);

    if (foundSBases != null && foundSBases.size() == 1) {
      return foundSBases.get(0);
    }

    return null;
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#getElementName()
   */
  @Override
  public String getElementName() {
    return StringTools.firstLetterLowerCase(getClass().getSimpleName());
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#getExtension(java.lang.String)
   */
  @Override
  public SBasePlugin getExtension(String nameOrUri) {
    // use always the package name in the map
    PackageParser packageParser =
        ParserManager.getManager().getPackageParser(nameOrUri);

    if (packageParser != null) {
      return extensions.get(packageParser.getPackageName());
    }

    throw new IllegalArgumentException(format(
      resourceBundle.getString("AbstractSBase.createPlugin"), nameOrUri));
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#getExtensionCount()
   */
  @Override
  public int getExtensionCount() {

    if (extensions != null) {
      return extensions.size();
    }

    return 0;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#getExtensionPackages()
   */
  @Override
  public Map<String, SBasePlugin> getExtensionPackages() {
    return extensions;
  }


  /**
   * Return the index of the first child of type 'Element' for the given
   * {@link XMLNode}.
   * 
   * @param curNotes the XMLNode
   * @return the index of the first child of type 'Element' for the given
   *         {@link XMLNode}, -1 otherwise.
   */
  public static int getFirstElementIndex(XMLNode curNotes) {

    if (curNotes != null && curNotes.getChildCount() > 0) {

      for (int i = 0; i < curNotes.getChildCount(); i++) {
        XMLNode childNode = curNotes.getChildAt(i);

        if (childNode.isElement()) {
          return i;
        }
      }
    }

    return -1;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#getHistory()
   */
  @Override
  public History getHistory() {
    return getAnnotation().getHistory();
  }


  /**
   * Returns an {@link IdManager} that can register the given {@link SBase}.
   * <p>
   * It means that the method {@link IdManager#accept(SBase)} returned
   * {@code true}.
   * 
   * @param sbase
   *        the {@link SBase} that we try to register or unregister.
   * @return an {@link IdManager} that can register the given {@link SBase}.
   */
  protected IdManager getIdManager(SBase sbase) {

    // we need to test the SBasePlugins if any exists first
    if (getNumPlugins() > 0) {
      for (String pluginKey : getExtensionPackages().keySet()) {
        SBasePlugin plugin = getExtensionPackages().get(pluginKey);

        // System.out.println("DEBUG - getIdManager plugins found");

        if ((plugin instanceof IdManager)
            && (((IdManager) plugin).accept(sbase))) {
          return (IdManager) plugin;
        }
      }
    }

    if ((this instanceof IdManager) && (((IdManager) this).accept(sbase))) {
      return (IdManager) this;
    }
    return getParentSBMLObject() != null
        ? ((AbstractSBase) getParentSBMLObject()).getIdManager(sbase) : null;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#getLevel()
   */
  @Override
  public int getLevel() {
    return isSetLevel() ? lv.getL().intValue() : -1;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#getLevelAndVersion()
   */
  @Override
  public ValuePair<Integer, Integer> getLevelAndVersion() {
    if (lv == null) {
      lv = new ValuePair<Integer, Integer>(-1, -1);
    }
    return lv;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#getMetaId()
   */
  @Override
  public String getMetaId() {
    return isSetMetaId() ? metaId : "";
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#getModel()
   */
  @Override
  public Model getModel() {
    if (this instanceof Model) {
      return (Model) this;
    }
    return getParentSBMLObject() != null ? getParentSBMLObject().getModel()
      : null;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#getNamespaces()
   */
  @Override
  public String getNamespace() {
    if (getPackageName().equals("core") && elementNamespace == null) {
      return JSBML.getNamespaceFrom(getLevel(), getVersion());
    }
    return elementNamespace;
  }


  /**
   * Returns an {@code XMLNode} object that represent the notes of this element.
   * 
   * @return an {@code XMLNode} object that represent the notes of this element.
   */
  @Override
  public XMLNode getNotes() {
    return notesXMLNode;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#getNotesString()
   */
  @Override
  public String getNotesString() throws XMLStreamException {
    return notesXMLNode != null ? notesXMLNode.toXMLString() : "";
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#getNumCVTerms()
   */
  @Override
  public int getNumCVTerms() {
    return getCVTermCount();
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#getNumPlugins()
   */
  @Override
  public int getNumPlugins() {
    return getExtensionCount();
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#getPackageName()
   */
  @Override
  public String getPackageName() {
    return packageName;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#getPackageVersion()
   */
  @Override
  public int getPackageVersion() {
    return packageVersion;
  }


  /**
   * This is equivalent to calling {@link #getParentSBMLObject()}, but this
   * method is needed for {@link TreeNode}.
   * 
   * @return the parent element of this element.
   * @see #getParentSBMLObject()
   */
  @Override
  public SBase getParent() {
    return (SBase) super.getParent();
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#getParentSBMLObject()
   */
  @Override
  public SBase getParentSBMLObject() {
    return getParent();
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#getPlugin(java.lang.String)
   */
  @Override
  public SBasePlugin getPlugin(String nameOrUri) {

    // use always the package name in the map
    PackageParser packageParser =
        ParserManager.getManager().getPackageParser(nameOrUri);

    if (packageParser != null) {
      SBasePlugin plugin = extensions.get(packageParser.getPackageName());
      if (plugin != null) {
        return plugin;
      } else {
        return createPlugin(nameOrUri);
      }
    }

    throw new IllegalArgumentException(format(
      resourceBundle.getString("AbstractSBase.createPlugin"), nameOrUri));
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#getSBMLDocument()
   */
  @Override
  public SBMLDocument getSBMLDocument() {
    if (this instanceof SBMLDocument) {
      return (SBMLDocument) this;
    }
    SBase parent = getParentSBMLObject();
    return (parent != null) ? parent.getSBMLDocument() : null;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#getSBOTerm()
   */
  @Override
  public int getSBOTerm() {
    return sboTerm;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#getSBOTermID()
   */
  @Override
  public String getSBOTermID() {
    return SBO.intToString(sboTerm);
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#getURI()
   */
  @Override
  public String getURI() {
    return getNamespace();
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#getVersion()
   */
  @Override
  public int getVersion() {
    return isSetVersion() ? lv.getV().intValue() : -1;
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 773;
    int hashCode = super.hashCode();
    if (isSetMetaId()) {
      hashCode += prime * getMetaId().hashCode();
    }
    if (isSetId()) {
      hashCode += prime * getId().hashCode();
    }
    if (isSetName()) {
      hashCode += prime * getName().hashCode();
    }
    if (isSetSBOTerm()) {
      hashCode += prime * getSBOTerm();
    }
    if (elementNamespace != null) {
      hashCode = prime * hashCode + elementNamespace.hashCode();
    }
    if (declaredNamespaces != null) {
      hashCode = prime * hashCode + declaredNamespaces.hashCode();
    }

    // Notes, Annotation and extension SBasePlugins are taken into account in
    // AbstractTreeNode.hashCode()
    // as they are part of the children returned by #getChildAt(int i)

    return hashCode + prime * getLevelAndVersion().hashCode();
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#hasValidAnnotation()
   */
  @Override
  public boolean hasValidAnnotation() {
    if (isSetAnnotation()) {
      if (isSetMetaId()) {
        Annotation annotation = getAnnotation();
        if (!annotation.isSetAbout()) {
          /*
           * Ok, let's set this about tag silently because
           * when writing SBML, we would set this tag anyway.
           * This method just complains incorrectly set about
           * tags.
           */
          annotation.setAbout('#' + getMetaId());
          return true;
        }
        if (annotation.getAbout().equals('#' + getMetaId())) {
          return true;
        }
      }
      if (getAnnotation().isSetNonRDFannotation()
          && !getAnnotation().isSetRDFannotation()) {
        return true;
      }
      return false;
    }
    return true;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#hasValidLevelVersionNamespaceCombination()
   */
  @Override
  public boolean hasValidLevelVersionNamespaceCombination() {
    return isValidLevelAndVersionCombination(getLevel(), getVersion());
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#isExtendedByOtherPackages()
   */
  @Override
  public boolean isExtendedByOtherPackages() {
    return !extensions.isEmpty();
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBase#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    // default value
    return false;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#isPackageEnabled(java.lang.String)
   */
  @Override
  public boolean isPackageEnabled(String packageURIOrName) {

    SBMLDocument doc = getSBMLDocument();

    if (doc != null) {
      return doc.isPackageEnabled(packageURIOrName);
    }

    return false;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#isPackageURIEnabled(java.lang.String)
   */
  @Override
  public boolean isPackageURIEnabled(String packageURIOrName) {
    return isPackageEnabled(packageURIOrName);
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#isPkgEnabled(java.lang.String)
   */
  @Override
  @Deprecated
  public boolean isPkgEnabled(String packageURIOrName) {
    return isPackageEnabled(packageURIOrName);
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#isPkgURIEnabled(java.lang.String)
   */
  @Override
  @Deprecated
  public boolean isPkgURIEnabled(String packageURIOrName) {
    return isPackageEnabled(packageURIOrName);
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#isSetAnnotation()
   */
  @Override
  public boolean isSetAnnotation() {
    return (annotation != null) && annotation.isSetAnnotation();
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#isSetHistory()
   */
  @Override
  public boolean isSetHistory() {
    if (isSetAnnotation()) {
      return annotation.isSetHistory();
    }
    return false;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#isSetLevel()
   */
  @Override
  public boolean isSetLevel() {
    return (lv != null) && (lv.getL() != null) && (lv.getL().intValue() > -1);
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#isSetLevelAndVersion()
   */
  @Override
  public boolean isSetLevelAndVersion() {
    return isSetLevel() && isSetVersion();
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#isSetMetaId()
   */
  @Override
  public boolean isSetMetaId() {
    return metaId != null;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#isSetNotes()
   */
  @Override
  public boolean isSetNotes() {
    return notesXMLNode != null;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#isSetPackageVErsion()
   */
  @Override
  public boolean isSetPackageVErsion() {
    return packageVersion != -1;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#isSetParentSBMLObject()
   */
  @Override
  public boolean isSetParentSBMLObject() {
    return isSetParent();
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#isSetPlugin(java.lang.String)
   */
  @Override
  public boolean isSetPlugin(String nameOrUri) {

    // use always the package name in the map
    PackageParser packageParser =
        ParserManager.getManager().getPackageParser(nameOrUri);

    if (packageParser != null) {
      return extensions.get(packageParser.getPackageName()) != null;
    }

    throw new IllegalArgumentException(format(
      resourceBundle.getString("AbstractSBase.createPlugin"), nameOrUri));
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#isSetSBOTerm()
   */
  @Override
  public boolean isSetSBOTerm() {
    return sboTerm != -1;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#isSetVersion()
   */
  @Override
  public boolean isSetVersion() {
    return (lv != null) && (lv.getV() != null) && (lv.getV().intValue() > -1);
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#notifyChildChange(javax.swing.tree.
   * TreeNode, javax.swing.tree.TreeNode)
   */
  @Override
  protected void notifyChildChange(TreeNode oldChild, TreeNode newChild) {
    if (oldChild instanceof SBase) {
      SBMLDocument doc = getSBMLDocument();
      if (doc != null) {
        /*
         * Recursively remove pointers to oldValue's and all
         * sub-element's meta identifiers from the
         * SBMLDocument.
         */
        doc.registerMetaIds((SBase) oldChild, true, true);

        /*
         * Do the same for all identifiers under the old value.
         */
        IdManager idManager =
            ((AbstractSBase) oldChild).getIdManager((SBase) oldChild);
        if (idManager != null) {
          idManager.unregister((SBase) oldChild);
          SBase newNsb = (SBase) newChild;
          if (!idManager.register(newNsb)) {
            throw IdentifierException.createIdentifierExceptionForId(newNsb, newNsb.getId());
          }
        }
      }
    }
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#readAttribute(java.lang.String, java.lang.String,
   * java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value) {
    if (attributeName.equals("sboTerm")) {
      setSBOTerm(value);
      return true;
    } else if (attributeName.equals("metaid")) {
      setMetaId(value);
      return true;
    } else if (attributeName.equals("id") && (getLevel() > 1)) {
      // Testing if id is allowed for L3V1 and below
      if ((! (this instanceof NamedSBase)) && (getLevelAndVersion().compareTo(3, 1) <= 0)) {
        return false;
      }
      try {
        setId(value);
      } catch (IllegalArgumentException e) {
        // there is a problem with the id, either invalid syntax or duplicated id
        AbstractReaderWriter.processInvalidAttribute(attributeName, null, value, prefix, this);
        // still returning true to the method so that the attribute is not put in the unknown XML object
      }
      
      return true;

    } else if (attributeName.equals("name")) {
      // Testing if name is allowed for L3V1 and below
      if ((! (this instanceof NamedSBase)) && (getLevelAndVersion().compareTo(3, 1) <= 0)) {
        return false;
      }

      try {
        setName(value); // For level 1, it will set the id as well
      } catch (IllegalArgumentException e) {
        // there is a problem with the name/id, either invalid syntax or duplicated id
        AbstractReaderWriter.processInvalidAttribute(attributeName, null, value, prefix, this);
      }
      
      return true;
    }

    return false;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#registerChild(org.sbml.jsbml.SBase)
   */
  @Override
  public boolean registerChild(SBase sbase) throws LevelVersionError {
    if ((sbase != null) && (sbase.getParent() != null)) {
      if (sbase.getParent() == this) {
        logger.warn(format(
          resourceBundle.getString("AbstractSBase.registerChild1"),
          sbase.getElementName(), sbase, getElementName(), this));
      } else {
        logger.warn(format(
          resourceBundle.getString("AbstractSBase.registerChild2"),
          sbase.getClass().getSimpleName(), sbase, sbase.getParent(), this));
      }
      return false;
    }

    if ((sbase != null) && checkLevelAndVersionCompatibility(sbase)
        && checkAndSetPackageNamespaceAndVersion(sbase)) {
      SBMLDocument doc = getSBMLDocument();
      if (doc != null) {
        /*
         * In case that sbase did not have access to the document we
         * have to recursively check the metaId property.
         */
        doc.registerMetaIds(sbase,
          (sbase.getSBMLDocument() == null) && (sbase instanceof AbstractSBase),
          false);
      }

      /*
       * Memorize all TreeNodeChangeListeners that are currently assigned to the
       * new SBase in order to re-use these later. For now we must remove all
       * those to avoid listeners to be called before we could really add the
       * SBase to this subtree.
       */
      List<TreeNodeChangeListener> listeners =
          sbase.getListOfTreeNodeChangeListeners();
      sbase.removeAllTreeNodeChangeListeners();

      /*
       * Make sure the new SBase is part of the subtree rooted at this element
       * before (recursively) registering all ids:
       */
      TreeNode oldParent = sbase.getParent(); // Memorize the old parent (may be
      // null).
      ((AbstractSBase) sbase).setParentSBML(this);

      // using the IdManager
      IdManager idManager = getIdManager(sbase);

      // If possible, recursively register all ids of the SBase in our model:
      if ((idManager != null) && !idManager.register(sbase)) {
        // Something went wrong: We have to restore the previous state:
        if (sbase instanceof AbstractSBase) {
          if (oldParent == null) {
            ((AbstractSBase) sbase).setParentSBML(null);
          } else if (oldParent instanceof SBase) {
            ((AbstractSBase) sbase).setParentSBML((SBase) oldParent);
          }
        }
        sbase.addAllChangeListeners(listeners);

        throw new IllegalArgumentException(format(
          resourceBundle.getString("AbstractSBase.registerChild3"),
          sbase.getElementName()));
      }

      /*
       * Now, we can add all previous listeners. The next change will
       * be fired after registering all ids.
       */
      sbase.addAllChangeListeners(listeners);

      // Add all TreeNodeChangeListeners from this current node also to the new
      // SBase:
      sbase.addAllChangeListeners(getListOfTreeNodeChangeListeners());

      // Notify all listeners that a new node has been added to this subtree:
      sbase.fireNodeAddedEvent();

      return true;
    }

    return false;
  }


  /**
   * Registers recursively the given {@link SBasePlugin} from the {@link Model}
   * and {@link SBMLDocument}.
   * 
   * @param sbasePlugin
   *        the {@link SBasePlugin} to register.
   */
  @SuppressWarnings("unused")
  private void registerChild(SBasePlugin sbasePlugin) {
    // Could/Should be used by the method #firePropertyChange

    // set package version and namespace if needed
    checkAndSetPackageNamespaceAndVersion(sbasePlugin, getSBMLDocument());

    int childCount = sbasePlugin.getChildCount();

    if (childCount > 0) {
      for (int i = 0; i < childCount; i++) {
        TreeNode childNode = sbasePlugin.getChildAt(i);

        if (childNode instanceof SBase) {
          registerChild((SBase) childNode);
        }
      }
    }
  }


  /**
   * Removes the given {@link CVTerm}.
   * 
   * @param cvTerm
   *        the {@link CVTerm} to remove
   * @return true if the {@link CVTerm} was successfully removed.
   */
  @Override
  public boolean removeCVTerm(CVTerm cvTerm) {
    return getAnnotation().removeCVTerm(cvTerm);
  }


  /**
   * Removes the {@link CVTerm} at the given index.
   * 
   * @param index
   *        the index
   * @return the removed {@link CVTerm}.
   * @throws IndexOutOfBoundsException
   *         if the index is out of range (index &lt; 0 || index &gt;= size())
   */
  @Override
  public CVTerm removeCVTerm(int index) {
    return getAnnotation().removeCVTerm(index);
  }

  
  /**
   * Removes a namespace from the set of declared namespaces of this
   * {@link SBase}.
   * 
   * @param prefix
   *        the prefix of the namespace to remove
   */  
  public void removeDeclaredNamespaceByPrefix(String prefix) {
    if (prefix == null) {
      return;
    }
    
    if ((!prefix.startsWith("xmlns:")) && (!prefix.equals("xmlns"))) {
      if (prefix.indexOf(':') != -1) {
        throw new IllegalArgumentException(
          resourceBundle.getString("AbstractSBase.addDeclaredNamespace"));
      }
      prefix = "xmlns:" + prefix;
    }
    
    String removedNamespace = declaredNamespaces.remove(prefix);
    
    if (removedNamespace != null) {
      firePropertyChange(TreeNodeChangeEvent.addDeclaredNamespace, removedNamespace, null);
    }
  }

  /**
   * Removes a namespace from the set of declared namespaces of this
   * {@link SBase}.
   * 
   * @param namespace the namespace to remove
   */
  public void removeDeclaredNamespaceByNamespace(String namespace) {
    if (namespace == null) {
      return;
    }
    
    String prefixToRemove = null;
    
    for (String prefix : declaredNamespaces.keySet()) {
      String namespaceForPrefix = declaredNamespaces.get(prefix);
      
      if (namespace.equals(namespaceForPrefix)) {
        prefixToRemove = prefix;
        break;
      }
    }
    
    if (prefixToRemove != null) {
      declaredNamespaces.remove(prefixToRemove);
      firePropertyChange(TreeNodeChangeEvent.addDeclaredNamespace, namespace, null);
    }
  }
  
  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#setAnnotation(org.sbml.jsbml.Annotation)
   */
  @Override
  public void setAnnotation(Annotation annotation) {
    Annotation oldAnnotation = this.annotation;
    this.annotation = annotation;
    this.annotation.parent = this;
    if (isSetMetaId()) {
      this.annotation.setAbout('#' + getMetaId());
    }
    firePropertyChange(TreeNodeChangeEvent.setAnnotation, oldAnnotation,
      this.annotation);
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#setAnnotation(java.lang.String)
   */
  @Override
  public void setAnnotation(String nonRDFAnnotation) throws XMLStreamException {
    setAnnotation(XMLNode.convertStringToXMLNode(
      StringTools.toXMLAnnotationString(nonRDFAnnotation)));
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#setAnnotation(org.sbml.jsbml.xml.XMLNode)
   */
  @Override
  public void setAnnotation(XMLNode nonRDFAnnotation) {
    getAnnotation().setNonRDFAnnotation(nonRDFAnnotation);
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#setHistory(org.sbml.jsbml.History)
   */
  @Override
  public void setHistory(History history) {
    History oldHistory = isSetHistory() ? getHistory() : null;
    getAnnotation().setHistory(history);
    firePropertyChange(TreeNodeChangeEvent.history, oldHistory, history);
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#setLevel(int)
   */
  @Override
  public void setLevel(int level) {
    SBase parent = getParent();
    if ((parent != null) && (parent != this) && parent.isSetLevel()) {
      if (level != parent.getLevel()) {
        throw new LevelVersionError(this, parent);
      }
    }
    Integer oldLevel = getLevelAndVersion().getL();
    lv.setL(level);
    firePropertyChange(TreeNodeChangeEvent.level, oldLevel, lv.getL());
  }


  /**
   * Sets recursively the level and version attribute for this element
   * and all sub-elements.
   * 
   * @param level
   *        the SBML level
   * @param version
   *        the SBML version
   * @param strict
   *        a boolean to say if the method need to be strict or not (not used at
   *        the moment)
   * @return {@code true} if the operation as been successful.
   */
  boolean setLevelAndVersion(int level, int version, boolean strict) {
    return setLevelAndVersion(level, version, strict, null);
  }

  boolean setLevelAndVersion(int level, int version, boolean strict, LevelVersionConverter lvConverter) {
    if (isValidLevelAndVersionCombination(level, version)) {
      boolean success = true;

      setLevel(level);
      setVersion(version);
      // TODO: perform necessary conversion and/or report potential problems or
      // lose of data to the user!
      if ((lvConverter != null) && lvConverter.needsAction(this)) {
        success &= lvConverter.performAction(this);
      }
      Enumeration<TreeNode> children = children();
      TreeNode child;
      while (children.hasMoreElements()) {
        child = children.nextElement();
        if (child instanceof AbstractSBase) {
          success &=
              ((AbstractSBase) child).setLevelAndVersion(level, version, strict, lvConverter);
        }
      }
      return success;
    }
    return false;
  }


  /**
   * Sets recursively the package namespace and version if the current node
   * 'packageName' is equals to the given {@code packageLabel}.
   * 
   * @param packageLabel
   *        the short label of the package
   * @param namespace
   *        the namespace of the package
   * @param packageVersion
   *        the package version
   * @return {@code true} if the current node 'packageName' is equals to the given {@code packageLabel}
   * and the package namespace and version were sets on all children.
   */
  boolean setPackageNamespaceAndVersion(String packageLabel, String namespace,
    int packageVersion) {

    if (packageLabel.equals(getPackageName())) {
      setNamespace(namespace);
      setPackageVersion(packageVersion);

      boolean success = true;
      Enumeration<TreeNode> children = children();

      while (children.hasMoreElements()) {
        TreeNode child = children.nextElement();

        if (child instanceof AbstractSBase
            && ((AbstractSBase) child).getPackageName().equals(packageLabel)) {
          success &= ((AbstractSBase) child).setPackageNamespaceAndVersion(
            packageLabel, namespace, packageVersion);
        }
      }
      return success;

    }

    return false;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#setMetaId(java.lang.String)
   */
  @Override
  public void setMetaId(String metaId) throws IllegalArgumentException {
    if (metaId != null) {
      if (isSetMetaId() && getMetaId().equals(metaId)) {
        /*
         * Do nothing if the identical metaId has already been assigned to this
         * object. In this case, the metaId must have been checked for validity
         * already and also the level must be appropriate.
         */
        return;
      } else if (getLevel() == 1) {
        throw new PropertyNotAvailableException(TreeNodeChangeEvent.metaId,
          this);
      } else if (!SyntaxChecker.isValidMetaId(metaId) && !isReadingInProgress()) {
        throw new IllegalArgumentException(format(
          resourceBundle.getString("AbstractSBase.setMetaId"), metaId,
          getElementName()));
      }
    }
    SBMLDocument doc = getSBMLDocument();
    String oldMetaId = this.metaId;
    if (doc != null) {
      // We have to first remove the pointer from the old metaId to this SBase
      if (oldMetaId != null) {
        doc.registerMetaId(this, false);
      }
      // Now we can register the new metaId if necessary.
      if (metaId != null) {
        this.metaId = metaId;
        if (!doc.registerMetaId(this, true) && !isReadingInProgress()) {
          // register failed. Revert the change and throw an exception:
          this.metaId = oldMetaId;
          throw IdentifierException.createIdentifierExceptionForMetaId(this, metaId);
        }
      }
    } else {
      // If the document is null, we don't have to register anything. Simply
      // change:
      this.metaId = metaId;
    }
    if (isSetAnnotation()) {
      // Propagate the change also to the annotation:
      getAnnotation().setAbout('#' + metaId);
    }
    firePropertyChange(TreeNodeChangeEvent.metaId, oldMetaId, metaId);
  }


  /**
   * Sets the XML namespace to which this {@link SBase} belong.
   * <p>
   * This an internal method that should not be used outside of the main jsbml
   * code
   * (core + packages). One class should always belong to the same namespace,
   * although the namespaces can
   * have different level and version (and package version). You have to know
   * what you are doing
   * when using this method.
   * 
   * @param namespace
   *        the XML namespace to which this {@link SBase} belong.
   */
  public void setNamespace(String namespace) {
    if ((elementNamespace != null) && (namespace != null)
        && (!elementNamespace.equals(namespace))) { // TODO - test if
      // elementNamespace or
      // namespace is empty before
      // throwing the error !
      // if we implement proper conversion some days, we need to unset the
      // namespace before changing it.
      logger.error(format(
        resourceBundle.getString("AbstractSBase.setNamespaceExc"),
        elementNamespace, namespace));
      // throw new
      // IllegalArgumentException(format(resourceBundle.getString("AbstractSBase.setNamespaceExc"),
      // elementNamespace, namespace));
    }
    String old = elementNamespace;
    elementNamespace = namespace;

    firePropertyChange(TreeNodeChangeEvent.namespace, old, namespace);
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#setNotes(java.lang.String)
   */
  @Override
  public void setNotes(String notes) throws XMLStreamException {
    setNotes(
      XMLNode.convertStringToXMLNode(StringTools.toXMLNotesString(notes)));
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#setNotes(java.lang.String)
   */
  @Override
  public void setNotes(XMLNode notes) {
    XMLNode oldNotes = notesXMLNode;
    notesXMLNode = notes;
    if (notesXMLNode != null) {
      notesXMLNode.setParent(this);
    }
    firePropertyChange(TreeNodeChangeEvent.notes, oldNotes, notesXMLNode);
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#setPackageVersion(int)
   */
  @Override
  public void setPackageVersion(int packageVersion) {
    int oldPackageVersion = this.packageVersion;

    SBase parent = getParent();
    SBasePlugin parentPlugin = null;

    if ((parent != null) && (parent != this)) {
      int parentPackageVersion = -1;

      if (parent.getPackageName().equals(packageName)) {
        parentPackageVersion = parent.getPackageVersion();
      } else if (parent.isSetPlugin(packageName)) {
        parentPlugin = parent.getPlugin(packageName);
        parentPackageVersion = parentPlugin.getPackageVersion();
      }

      if (packageVersion != -1 && parentPackageVersion != -1
          && packageVersion != parentPackageVersion) {
        if (parentPlugin != null) {
          throw new LevelVersionError(parentPlugin, this);
        } else {
          throw new LevelVersionError(parent, this);
        }
      }
    }

    this.packageVersion = packageVersion;
    firePropertyChange(TreeNodeChangeEvent.packageVersion, oldPackageVersion,
      packageVersion);
  }


  /**
   * Sets the parent of the current {@link SBase}.
   * 
   * @param parent the parent of this element.
   */
  protected void setParentSBML(SBase parent) {
    SBase oldParent = getParent();
    this.parent = parent;
    firePropertyChange(TreeNodeChangeEvent.parentSBMLObject, oldParent, parent);
  }


  /**
   * Checks the Level/Version configuration of the new parent (if it is
   * compliant to the one of this {@link SBase}), adds all changeListeners from
   * the parent to this {@link SBase}, fires a
   * {@link TreeNodeChangeListener#nodeAdded(TreeNode)} event, and and finally,
   * it will forward the new parent to {@link #setParentSBML(SBase)}.
   * Note that this will cause another event to be triggered:
   * {@link TreeNodeChangeListener#propertyChange(java.beans.PropertyChangeEvent)}
   * with the old and the new parent.
   * 
   * @param sbase
   *        the new parent element.
   * @throws LevelVersionError
   *         if the SBML Level and Version configuration of the new parent
   *         differs from the one of this {@link SBase}.
   * @see #setParentSBML(SBase)
   */
  protected void setParentSBMLObject(SBase sbase) throws LevelVersionError {
    if (sbase instanceof AbstractSBase) {
      ((AbstractSBase) sbase).checkLevelAndVersionCompatibility(this);
    }
    addAllChangeListeners(sbase.getListOfTreeNodeChangeListeners());
    fireNodeAddedEvent();
    setParentSBML(sbase);
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#setSBOTerm(int)
   */
  @Override
  public void setSBOTerm(int term) {
    if ((getLevelAndVersion().compareTo(Integer.valueOf(2), Integer.valueOf(2)) < 0)
        && (!isReadingInProgress()))
    {
      throw new PropertyNotAvailableException(TreeNodeChangeEvent.sboTerm,
        this);
    }
    if ((term != -1) && !SBO.checkTerm(term) && !isReadingInProgress()) {
      throw new IllegalArgumentException(format(
        resourceBundle.getString("AbstractSBase.setSBOTerm"), term));
    }
    Integer oldTerm = Integer.valueOf(sboTerm);
    sboTerm = term;
    firePropertyChange(TreeNodeChangeEvent.sboTerm, oldTerm, sboTerm);
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#setSBOTerm(java.lang.String)
   */
  @Override
  public void setSBOTerm(String sboid) {
    int sboTermInt = SBO.stringToInt(sboid);

    // if there is a problem, store the value in a user object
    if (sboTermInt == -1) {
      putUserObject(JSBML_WRONG_SBO_TERM, sboid); // TODO - we could make use of the generic way of storing unknown attributes ?
    }
    setSBOTerm(sboTermInt);
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#setThisAsParentSBMLObject(org.sbml.jsbml.SBase)
   */
  @Override
  @Deprecated
  public void setThisAsParentSBMLObject(SBase sbase) throws LevelVersionError {
    registerChild(sbase);
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#setVersion(int)
   */
  @Override
  public void setVersion(int version) {
    SBase parent = getParent();
    if ((parent != null) && (parent != this) && parent.isSetVersion()) {
      if (version != parent.getVersion()) {
        throw new LevelVersionError(parent, this);
      }
    }
    Integer oldVersion = getLevelAndVersion().getV();
    lv.setV(version);
    firePropertyChange(TreeNodeChangeEvent.version, oldVersion, version);
  }

  /**
   * Returns a String representing this SBase with all the
   * attributes that are defined.
   * 
   * @return a String representing this SBase with all its
   * attributes
   */
  @Override
  public String toString() {
    Map<String, String> attMap = writeXMLAttributes();
    StringBuilder sb = new StringBuilder("");
    sb.append(getElementName()).append(" [");

    if (attMap != null) {
      // print id, name and metaid first
      sb.append(extractAttribute(attMap, "id"));
      sb.append(extractAttribute(attMap, "name"));
      sb.append(extractAttribute(attMap, "metaid"));

      for (String attributeName : attMap.keySet()) {
        sb.append(" ").append(attributeName).append("=\"");
        sb.append(attMap.get(attributeName)).append("\"");
      }
    }

    sb.append("]");

    return sb.toString();
  }

  /**
   * Extracts an attribute value from the given map with or without
   * packageName as prefix for the attribute name.
   * 
   * <p>If a value is found, the attribute is removed from the map.</p>
   * 
   * 
   * @param attMap the attributes map
   * @param attributeName the attribute name
   * @return the attribute value or an empty String
   */
  private String extractAttribute(Map<String, String> attMap, String attributeName) {
    String attributeValue = attMap.get(attributeName);

    if (attributeValue == null && (!packageName.equals("core"))) {
      attributeValue = attMap.get(packageName + ":" + attributeName);
    }

    if (attributeValue != null) {
      attMap.remove(attributeName);
      attributeValue = " " + attributeName + "=\"" + attributeValue + "\"";
    } else {
      attributeValue = "";
    }

    return attributeValue;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBase#unregisterChild(org.sbml.jsbml.SBase)
   */
  @Override
  public void unregisterChild(SBase sbase) {

    if (logger.isDebugEnabled()) {
      logger.debug(format(
        resourceBundle.getString("AbstractSBase.unregisterChild1"),
        sbase.getElementName(),
        (sbase.isSetId() ? sbase.getId() : "")));
    }

    if ((sbase != null)) {
      SBMLDocument doc = getSBMLDocument();

      if (doc != null) {
        // unregister recursively all metaIds.
        doc.registerMetaIds(sbase, true, true);
      }

      IdManager idManager = getIdManager(sbase);

      // If possible, recursively unregister all ids of the SBase in our model:
      if ((idManager != null) && !idManager.unregister(sbase)) {
        throw new IllegalArgumentException(format(
          resourceBundle.getString("AbstractSBase.unregisterChild2"),
          sbase.getElementName()));
      }

      /*
       * Do not remove ChangeListeners from the sbase here, this will be done
       * in the super class. It is important to keep the change listeners for
       * now, because otherwise the listeners won't be informed that we are
       * going to delete something from the model.
       */
    }
  }


  /**
   * Unregisters recursively the given {@link SBasePlugin} from the
   * {@link Model}
   * and {@link SBMLDocument}.
   * 
   * @param sbasePlugin
   *        the {@link SBasePlugin} to unregister.
   */
  private void unregisterChild(SBasePlugin sbasePlugin) {

    if (logger.isDebugEnabled()) {
      logger.debug(format(
        resourceBundle.getString("AbstractSBase.unregisterChild3"),
        sbasePlugin));
    }

    int childCount = sbasePlugin.getChildCount();

    if (childCount > 0) {
      for (int i = 0; i < childCount; i++) {
        TreeNode childNode = sbasePlugin.getChildAt(i);

        if (childNode instanceof SBase) {
          unregisterChild((SBase) childNode);
        }
      }
    }
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#unsetAnnotation()
   */
  @Override
  public void unsetAnnotation() {
    if (isSetAnnotation()) {
      Annotation oldAnnotation = annotation;
      annotation = null;
      firePropertyChange(TreeNodeChangeEvent.annotation, oldAnnotation,
        annotation);
    }
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#unsetCVTerms()
   */
  @Override
  public void unsetCVTerms() {
    if (isSetAnnotation() && getAnnotation().isSetListOfCVTerms()) {
      annotation.unsetCVTerms();
    }
  }


  @Override
  public void unsetExtension(String nameOrUri) {

    // use always the package name in the map
    PackageParser packageParser =
        ParserManager.getManager().getPackageParser(nameOrUri);

    if (packageParser != null) {

      SBasePlugin sbasePlugin =
          extensions.remove(packageParser.getPackageName());
      firePropertyChange(TreeNodeChangeEvent.extension, sbasePlugin, null);
      return;
    }

    throw new IllegalArgumentException(format(
      resourceBundle.getString("AbstractSBase.createPlugin"), nameOrUri));
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#unsetHistory()
   */
  @Override
  public void unsetHistory() {
    if (isSetHistory()) {
      annotation.unsetHistory();
    }
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#unsetMetaId()
   */
  @Override
  public void unsetMetaId() {
    if (isSetMetaId()) {
      setMetaId(null);
    }
  }


  /**
   * Unsets the namespace that is associated to this {@link SBase}.
   * <p>
   * This is an internal method of JSBML that should be used with caution.
   */
  public void unsetNamespace() {
    String old = elementNamespace;

    elementNamespace = null;
    firePropertyChange(TreeNodeChangeEvent.namespace, old, null);
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#unsetNotes()
   */
  @Override
  public void unsetNotes() {
    if (isSetNotes()) {
      XMLNode oldNotes = notesXMLNode;
      notesXMLNode = null;
      firePropertyChange(TreeNodeChangeEvent.notes, oldNotes, getNotes());
    }
  }


  @Override
  public void unsetPlugin(String nameOrUri) {
    unsetExtension(nameOrUri);
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#unsetSBOTerm()
   */
  @Override
  public void unsetSBOTerm() {
    if (isSetSBOTerm()) {
      setSBOTerm(-1);
    }
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.SBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = new TreeMap<String, String>();
    int level = getLevel();

    if (1 < level) {
      /*
       * This ensures that the metaid of this element is always defined if
       * there is an annotation present.
       */
      if (isSetAnnotation() && getAnnotation().isSetRDFannotation()
          && !isSetMetaId()) {
        SBMLDocument doc = getSBMLDocument();
        if (doc != null) {
          setMetaId(doc.nextMetaId());
          logger.debug(format(
            "Some annotations would get lost because there was no metaid defined on {0}. To avoid this, an automatic metaid ''{0}'' has been generated.",
            getElementName(), getMetaId()));
          // Setting the new metaid in the RDF about attribute.
          getAnnotation().setAbout('#' + getMetaId());
        } else {
          logger.warn(format(
            "Some annotations can get lost because no metaid is defined on {0}.",
            getElementName()));
        }
      }
      if (isSetMetaId()) {
        attributes.put("metaid", getMetaId());
      }
      if (((level == 2) && (getVersion() >= 2)) || (level > 2)) {
        if (isSetSBOTerm()) {
          attributes.put("sboTerm", getSBOTermID());
        }
      }
    }

    if (isSetId()) {
      if (getLevel() != 1) {
        attributes.put("id", getId());
      } else {
        attributes.put("name", getId());
      }
    }
    if (isSetName()) {
      attributes.put("name", getName());
    }


    // Add all additional attributes from extension packages if there are any:
    if ((extensions != null) && (extensions.size() > 0)) {
      for (String key : extensions.keySet()) {
        SBasePlugin plugin = extensions.get(key);
        if (plugin != null) {
          Map<String, String> pluginAttributes = plugin.writeXMLAttributes();
          if (pluginAttributes != null) {
            attributes.putAll(pluginAttributes);
          }
        } else {
          logger.warn(
            format("Plugin for namespace {0} is null!", key));
        }
      }
    }

    return attributes;
  }

  /**
   * Checks if the sID is a valid identifier.
   * 
   * @param sID
   *            the identifier to be checked. If null or an invalid
   *            identifier, an exception will be thrown.
   * @return {@code true} only if the sID is a valid identifier.
   *         Otherwise this method throws an {@link IllegalArgumentException}.
   *         This is an intended behavior.
   * @throws IllegalArgumentException
   *             if the given id is not valid in this model.
   */
  protected boolean checkIdentifier(String sID) {
    if ((sID == null) || !SyntaxChecker.isValidId(sID, getLevel(), getVersion())) {
      throw new IllegalArgumentException(format(
        resourceBundle.getString("AbstractNamedSBase.checkIdentifier"),
        sID, getElementName()));
    }
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBase#getId()
   */
  @Override
  public String getId() {
    return isSetId() ? id : "";
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBase#getName()
   */
  @Override
  public String getName() {
    return isSetName() ? name : "";
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBase#isSetId()
   */
  @Override
  public boolean isSetId() {
    return id != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBase#isSetName()
   */
  @Override
  public boolean isSetName() {
    return name != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBase#setId(java.lang.String)
   */
  @Override
  public void setId(String id) {
    String property = getLevel() == 1 ? TreeNodeChangeEvent.name : TreeNodeChangeEvent.id;
    String oldId = this.id;

    IdManager idManager = getIdManager(this);
    if (idManager != null) { // (oldId != null) // As the register and unregister are recursive, we need to call the unregister all the time until we have a non recursive method
      // Delete previous identifier only if defined.
      idManager.unregister(this); // TODO - do we need non recursive method on the IdManager interface ??
    }
    if ((id == null) || (id.trim().length() == 0)) {
      this.id = null;
    } else if (checkIdentifier(id)) {
      this.id = id;
    }
    if ((idManager != null) && !idManager.register(this)) {
      IdentifierException exc = IdentifierException.createIdentifierExceptionForId(this, this.id);
      this.id = oldId; // restore the previous setting!
      throw new IllegalArgumentException(exc);
    }
    firePropertyChange(property, oldId, this.id);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBase#setName(java.lang.String)
   */
  @Override
  public void setName(String name) {
    // removed the call to the trim() function as a name with only space
    // should be considered valid.
    String oldName = this.name;
    if ((name == null) || (name.length() == 0)) {
      this.name = null;
    } else {
      this.name = name;
    }
    if (!isSetId() && (getLevel() == 1)) {
      /*
       * Note: In Level 1 there is no id attribute but the name is actually the
       * id. Since Level 2 the name attribute has been intended to be a human-readable
       * name, not a unique identifier (this was the meaning in Level 1). JSBML therefore
       * has to set the id (and not the name) when calling this method in Level 1 models.
       */
      setId(name);
    } else {
      // else part to avoid calling this method twice.
      firePropertyChange(TreeNodeChangeEvent.name, oldName, this.name);
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBase#unsetId()
   */
  @Override
  public void unsetId() {
    setId(null);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBase#unsetName()
   */
  @Override
  public void unsetName() {
    setName(null);
  }


  /**
   * Returns a map of all the declared namespaces in the SBML tree, visible to the given {@link SBase}.
   * 
   * <p>Starting from the given {@link SBase}, we will use {@link #getDeclaredNamespaces()} on it and all the parents until we 
   * reached the {@link SBMLDocument} or these is no more parent set. That will give us a complete set of all the declared
   * namespace visible to this SBase. If a prefix if declared twice, we keep only the first occurrence encountered, meaning that
   * we will have the declaration that is the deeper in the tree, the one that is active for XML code.</p>
   * 
   * @param sb the SBase
   * @return a map of all the declared namespaces in the SBML tree, visible to the given {@link SBase}.
   */
  public static HashMap<String, String> getAllDeclaredNamespaces(SBase sb) {
    
    HashMap<String, String> namespaceMap = new HashMap<String, String>();
    
    if (sb.getDeclaredNamespaces() != null) {
      for (String key : sb.getDeclaredNamespaces().keySet()) {
        namespaceMap.put(key, sb.getDeclaredNamespaces().get(key));
      }
    }

    getAllDeclaredNamespaces(sb.getParent(), namespaceMap);
    
    return namespaceMap;
  }


  /**
   * Populates a map of with the declared namespaces in the SBML tree, visible to the given element.
   * 
   * <p>Starting from the given element, we will use {@link #getDeclaredNamespaces()} on it and all the parents until we 
   * reached the {@link SBMLDocument} or these is no more parent set. That will give us a complete set of all the declared
   * namespace visible to this SBase. If a prefix if declared twice, we keep only the first occurrence encountered, meaning that
   * we will have the declaration that is the deeper in the tree, the one that is active for XML code.</p>
   * 
   * @param treeNode the SBase or SBasePlugin
   * @param namespaceMap the map to populate
   */
  private static void getAllDeclaredNamespaces(TreeNode treeNode, HashMap<String, String> namespaceMap) {
    if (treeNode == null) {
      return;
    }
    
    if (treeNode instanceof SBase) {
      SBase sb = (SBase) treeNode;
      
      if (sb.getDeclaredNamespaces() != null) {
        for (String key : sb.getDeclaredNamespaces().keySet()) {
          if (!namespaceMap.containsKey(key)) 
          {
            namespaceMap.put(key, sb.getDeclaredNamespaces().get(key));
          }
        }
      }

      getAllDeclaredNamespaces(sb.getParent(), namespaceMap);      
    }
    else if (treeNode instanceof SBasePlugin)
    {
      SBasePlugin sbp = (SBasePlugin) treeNode;
      
      getAllDeclaredNamespaces(sbp.getExtendedSBase(), namespaceMap);
    }
//    else 
//    {
//      System.out.println();
//    }
    
  }

}
