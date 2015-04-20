/*
 * $Id: LibSBMLChangeListener.java 2109 2015-01-05 04:50:45Z andreas-draeger $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/modules/libSBMLio/src/org/sbml/jsbml/xml/libsbml/LibSBMLChangeListener.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2015 jointly by the following organizations:
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
package org.sbml.jsbml.xml.libsbml;

import static org.sbml.jsbml.xml.libsbml.LibSBMLConstants.LINK_TO_LIBSBML;

import java.beans.PropertyChangeEvent;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.AnnotationElement;
import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.Creator;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.ExplicitRule;
import org.sbml.jsbml.History;
import org.sbml.jsbml.InitialAssignment;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.Symbol;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.ext.SBasePlugin;
import org.sbml.jsbml.util.ResourceManager;
import org.sbml.jsbml.util.SBMLtools;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.TreeNodeAdapter;
import org.sbml.jsbml.util.TreeNodeChangeEvent;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.jsbml.util.TreeNodeRemovedEvent;
import org.sbml.jsbml.util.TreeNodeWithChangeSupport;
import org.sbml.jsbml.xml.XMLToken;
import org.sbml.libsbml.ModelCreator;
import org.sbml.libsbml.ModelHistory;
import org.sbml.libsbml.XMLNode;
import org.sbml.libsbml.libsbmlConstants;

/**
 * This class listens to the changes in the JSBML document and synchronizes the
 * corresponding LibSBML document with the JSBML-Document.
 * 
 * @author Meike Aichele
 * @author Andreas Dr&auml;ger
 * @version $Rev: 2109 $
 * @since 1.0
 */
public class LibSBMLChangeListener implements TreeNodeChangeListener {

  /**
   * Pointer to the {@link org.sbml.libsbml.SBMLDocument}.
   */
  private org.sbml.libsbml.SBMLDocument libDoc;


  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(LibSBMLChangeListener.class);
  /**
   * Localization support
   */
  private static final ResourceBundle bundle = ResourceManager.getBundle("org.sbml.jsbml.xml.libsbml.Messages");

  /**
   * 
   * @param libDoc
   */
  public LibSBMLChangeListener(org.sbml.libsbml.SBMLDocument libDoc) {
    super();
    this.libDoc = libDoc;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeChangeListener#nodeAdded(javax.swing.tree.TreeNode)
   */
  @Override
  public void nodeAdded(TreeNode node) {
    if (node instanceof SBase) {
      try {
        SBase sbase = (SBase) node;
        Class<? extends SBase> jsbmlClass = sbase.getClass();

        SBase parent = sbase.getParentSBMLObject();
        if (parent instanceof ListOf<?>) {
          parent = parent.getParentSBMLObject();
        }
        Object libParent = parent.getUserObject(LINK_TO_LIBSBML);

        Method createMethod;
        Object returnVal;
        if (sbase instanceof ListOf<?>) {
          createMethod = libParent.getClass().getMethod("get" + StringTools.firstLetterUpperCase(sbase.getElementName()));
        } else {
          createMethod = libParent.getClass().getMethod("create" + jsbmlClass.getSimpleName());
        }
        returnVal = createMethod.invoke(libParent);
        if ((returnVal != null)
            && Class.forName("org.sbml.libsbml." + jsbmlClass.getSimpleName()).isAssignableFrom(returnVal.getClass())) {
          sbase.putUserObject(LINK_TO_LIBSBML, returnVal);
          if (sbase instanceof ListOf<?>) {
            LibSBMLUtils.transferSBaseProperties(sbase, (org.sbml.libsbml.ListOf) returnVal);
          } else {
            Method convertMethod = LibSBMLUtils.class.getMethod("convert" + jsbmlClass.getSimpleName(), jsbmlClass);
            logger.debug("trying to call " + convertMethod.toString());
            convertMethod.invoke(null, sbase);
          }
        } else {
          logger.warn(MessageFormat.format(bundle.getString("ADDING_ELEMENT_TO_LIBSBML_FAILED"),
            sbase.getElementName(), sbase, ""));
        }

      } catch (Throwable t) {
        logger.warn(t.getLocalizedMessage(), t);
      }

    } else if (node instanceof AnnotationElement) {
      TreeNode parent = null;
      do {
        parent = node.getParent();
      } while (!(parent instanceof SBase));
      SBase parentSBase = (SBase) parent;
      org.sbml.libsbml.SBase libParentSBase = (org.sbml.libsbml.SBase) parentSBase.getUserObject(LINK_TO_LIBSBML);

      if (node instanceof CVTerm) {
        CVTerm cvt = (CVTerm) node;
        if (libParentSBase.addCVTerm(LibSBMLUtils.convertCVTerm(cvt)) == libsbmlConstants.LIBSBML_OPERATION_SUCCESS) {
          cvt.putUserObject(LINK_TO_LIBSBML, libParentSBase.getCVTerm(libParentSBase.getNumCVTerms() - 1));
        } else {
          logger.warn("Failed to add CVTerm!");
        }
      } else if (node instanceof History) {
        History history = (History) node;
        libParentSBase.setModelHistory(new ModelHistory());
        history.putUserObject(LINK_TO_LIBSBML, libParentSBase.getModelHistory());
        LibSBMLUtils.convertHistory(history);
      } else if (node instanceof Annotation) {
        /* There is no comparable object in LibSBML to the Annotation-element in
         * JSBML, therefore there is nothing to do, because all features or sub-
         * elements will be added using different calls to this method (or other
         * listener methods in this class).
         */
      } else if (node instanceof Creator) {
        Creator creator = (Creator) node;
        History history = (History) creator.getParent();
        ModelHistory libHistory = (ModelHistory) history.getUserObject(LINK_TO_LIBSBML);
        libHistory.addCreator(LibSBMLUtils.convertModelCreator(creator));
        ModelCreator libCreator = libHistory.getCreator(libHistory.getNumCreators() - 1);
        creator.putUserObject(LINK_TO_LIBSBML, libCreator);
      }
    } else if (node instanceof ASTNode) {
      ASTNode astnode = (ASTNode) node;
      Object libParent = ((TreeNodeWithChangeSupport) astnode.getParent()).getUserObject(LINK_TO_LIBSBML);
      if (libParent instanceof org.sbml.libsbml.ASTNode) {
        org.sbml.libsbml.ASTNode libParentAST = (org.sbml.libsbml.ASTNode) libParent;
        libParentAST.addChild(LibSBMLUtils.convertASTNode(astnode));
        LibSBMLUtils.link((ASTNode) astnode.getParent(), libParentAST);
      } else {
        LibSBMLUtils.transferMath(astnode.getParentSBMLObject(), (org.sbml.libsbml.SBase) libParent);
      }

    } else if (node instanceof TreeNodeAdapter) {
      TreeNodeAdapter treeNodeAd = (TreeNodeAdapter) node;
      if (logger.isDebugEnabled()) {
        logger.debug(MessageFormat.format("Cannot add node {0}", treeNodeAd.getClass().getSimpleName()));
      }
    } else if (node instanceof XMLToken) {
      XMLToken token = (XMLToken) node;
      if (logger.isDebugEnabled()) {
        logger.debug(MessageFormat.format("Cannot add node {0}", token.getClass().getSimpleName()));
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeChangeListener#nodeRemoved(org.sbml.jsbml.util.TreeNodeRemovedEvent)
   */
  @Override
  public void nodeRemoved(TreeNodeRemovedEvent evt) {
    TreeNode node = evt.getSource();

    if (node instanceof SBase) {
      SBase sbase = (SBase) node;
      org.sbml.libsbml.SBase libSBase = (org.sbml.libsbml.SBase) sbase.getUserObject(LINK_TO_LIBSBML);
      if (libSBase != null) {
        libSBase.removeFromParentAndDelete();
      } else {
        logger.log(Level.DEBUG, MessageFormat.format(
          bundle.getString("CANNOT_REMOVE_ELEMENT"),
          node.getClass().getSimpleName()));
      }

    } else if (node instanceof AnnotationElement) {
      if (node instanceof CVTerm) {
        org.sbml.libsbml.SBase libParent = (org.sbml.libsbml.SBase) ((SBase) evt.getPreviousParent()).getUserObject(LINK_TO_LIBSBML);
        org.sbml.libsbml.CVTerm libTerm = (org.sbml.libsbml.CVTerm) ((CVTerm) node).getUserObject(LINK_TO_LIBSBML);
        // find the corresponding term to be removed:
        for (int i = 0; i < libParent.getNumCVTerms(); i++) {
          if (libTerm == libParent.getCVTerm(i)) {
            libParent.getCVTerms().remove(i);
            break;
          }
        }
      } else if (node instanceof History) {
        SBase parent = (SBase) evt.getPreviousParent();
        org.sbml.libsbml.SBase libParent = (org.sbml.libsbml.SBase) parent.getUserObject(LINK_TO_LIBSBML);
        libParent.unsetModelHistory();
      } else if (node instanceof Creator) {
        Creator creator = (Creator) node;
        History history = (History) evt.getPreviousParent();
        ModelCreator libCreator = (ModelCreator) creator.getUserObject(LINK_TO_LIBSBML);
        ModelHistory libHistory = (ModelHistory) history.getUserObject(LINK_TO_LIBSBML);
        // find the corresponding creator
        for (int i = 0; i < libHistory.getNumCreators(); i++) {
          if (libCreator == libHistory.getCreator(i)) {
            libHistory.getListCreators().remove(i);
            break;
          }
        }
      } else if (node instanceof Annotation) {
        org.sbml.libsbml.SBase libParent = (org.sbml.libsbml.SBase) ((SBase) evt.getPreviousParent()).getUserObject(LINK_TO_LIBSBML);
        libParent.unsetAnnotation();
      }

    } else if (node instanceof ASTNode) {
      TreeNode parent = evt.getPreviousParent();
      if (parent instanceof ASTNode) {
        org.sbml.libsbml.ASTNode libAST = (org.sbml.libsbml.ASTNode) ((ASTNode) node).getUserObject(LINK_TO_LIBSBML);
        org.sbml.libsbml.ASTNode libParentAST = (org.sbml.libsbml.ASTNode) ((ASTNode) parent).getUserObject(LINK_TO_LIBSBML);
        // find the corresponding ASTNode
        for (int i = 0; i < libParentAST.getNumChildren(); i++) {
          if (libAST == libParentAST.getChild(i)) {
            libParentAST.removeChild(i);
            break;
          }
        }
      } else {
        // must be some SBase
        org.sbml.libsbml.SBase libParent = (org.sbml.libsbml.SBase) ((TreeNodeWithChangeSupport) parent).getUserObject(LINK_TO_LIBSBML);
        try {
          Class<?> clazz = Class.forName("org.sbml.libsbml." + parent.getClass().getSimpleName()); // the corresponding libSBML class
          Method method = clazz.getMethod("setMath", org.sbml.libsbml.ASTNode.class);
          method.invoke(clazz.cast(libParent), new Object[] {null});
        } catch (Throwable exc) {
          logger.warn(exc.getLocalizedMessage() != null ? exc.getLocalizedMessage() : exc.getMessage(), exc);
        }
      }

    } else if (node instanceof TreeNodeAdapter) {
      // This can actually never happen, because then the actual change is directed to propertyChange
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CANNOT_REMOVE_ELEMENT"),
        node.getClass().getSimpleName()));
    } else if (node instanceof XMLToken) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CANNOT_REMOVE_ELEMENT"),
        node.getClass().getSimpleName()));
    } else if (node instanceof SBasePlugin) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("PLUGINS_CURRENTLY_NOT_SUPPORTED"),
        node.getClass().getSimpleName()));
    }
  }

  /* (non-Javadoc)
   * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
   */
  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    Object evtSrc = evt.getSource();
    String prop = evt.getPropertyName();

    if (prop.equals(TreeNodeChangeEvent.addExtension)) {
      Annotation anno = (Annotation) evtSrc;
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CANNOT_CHANGE_ELEMENT"),
        anno.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.annotation)) {
      SBase sbase = (SBase) evt;
      org.sbml.libsbml.SBase correspondingElement = (org.sbml.libsbml.SBase) sbase.getUserObject(LINK_TO_LIBSBML);
      correspondingElement.unsetAnnotation();
    } else if (prop.equals(TreeNodeChangeEvent.annotationNameSpaces)) {
      Annotation anno = (Annotation) evtSrc;
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CANNOT_CHANGE_ELEMENT"),
        anno.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.areaUnits)) {
      Model model = (Model) evtSrc;
      org.sbml.libsbml.Model libModel = (org.sbml.libsbml.Model) model.getUserObject(LINK_TO_LIBSBML);
      libModel.setAreaUnits(evt.getNewValue().toString());
    } else if (prop.equals(TreeNodeChangeEvent.boundaryCondition)) {
      Species species = (Species) evtSrc;
      org.sbml.libsbml.Species libSpecies = (org.sbml.libsbml.Species) species.getUserObject(LINK_TO_LIBSBML);
      libSpecies.setBoundaryCondition((Boolean) evt.getNewValue());
    } else if (prop.equals(TreeNodeChangeEvent.charge)) {
      Species species = (Species) evtSrc;
      org.sbml.libsbml.Species libSpecies = (org.sbml.libsbml.Species) species.getUserObject(LINK_TO_LIBSBML);
      libSpecies.setCharge((Integer) evt.getNewValue());
    } else if (prop.equals(TreeNodeChangeEvent.childNode) || prop.equals(TreeNodeChangeEvent.className)) {
      ASTNode node = (ASTNode) evtSrc;
      MathContainer mathContainer = node.getParentSBMLObject();
      Object libSBase = mathContainer.getUserObject(LINK_TO_LIBSBML);
      try {
        Method method = libSBase.getClass().getMethod("setMath", org.sbml.libsbml.ASTNode.class);
        method.invoke(libSBase, LibSBMLUtils.convertASTNode(mathContainer.getMath()));
      } catch (Throwable exc) {
        logger.log(Level.DEBUG, MessageFormat.format(
          bundle.getString("CANNOT_CHANGE_ELEMENT"), node.getClass().getSimpleName()));
      }
    } else if (prop.equals(TreeNodeChangeEvent.compartment)) {
      SBase sbase = (SBase) evtSrc;
      if (evtSrc instanceof Species) {
        ((org.sbml.libsbml.Species) sbase.getUserObject(LINK_TO_LIBSBML)).setCompartment(evt.getNewValue().toString());
      } else if (evtSrc instanceof Reaction) {
        ((org.sbml.libsbml.Reaction) sbase.getUserObject(LINK_TO_LIBSBML)).setCompartment(evt.getNewValue().toString());
      }
    } else if (prop.equals(TreeNodeChangeEvent.compartmentType)) {
      Compartment c = (Compartment) evtSrc;
      org.sbml.libsbml.Compartment libComp = (org.sbml.libsbml.Compartment) c.getUserObject(LINK_TO_LIBSBML);
      libComp.setCompartmentType(evt.getNewValue().toString());
    } else if (prop.equals(TreeNodeChangeEvent.constant)) {
      if (evt.getSource() instanceof SpeciesReference) {
        ((org.sbml.libsbml.SpeciesReference) ((SBase) evtSrc).getUserObject(LINK_TO_LIBSBML)).setConstant((Boolean) evt.getNewValue());
      } else if (evt.getSource() instanceof Symbol) {
        if (evtSrc instanceof Compartment) {
          ((org.sbml.libsbml.Compartment) ((SBase) evtSrc).getUserObject(LINK_TO_LIBSBML)).setConstant((Boolean) evt.getNewValue());
        } else if (evtSrc instanceof Parameter) {
          ((org.sbml.libsbml.Parameter) ((SBase) evtSrc).getUserObject(LINK_TO_LIBSBML)).setConstant((Boolean) evt.getNewValue());
        } else if (evtSrc instanceof Species) {
          ((org.sbml.libsbml.Species) ((SBase) evtSrc).getUserObject(LINK_TO_LIBSBML)).setConstant((Boolean) evt.getNewValue());
        }
      }
    } else if (prop.equals(TreeNodeChangeEvent.conversionFactor)) {
      ((org.sbml.libsbml.Model) ((SBase) evtSrc).getUserObject(LINK_TO_LIBSBML)).setConversionFactor(evt.getNewValue().toString());
    } else if (prop.equals(TreeNodeChangeEvent.createdDate)) {
      History history = (History) evtSrc;
      ((org.sbml.libsbml.ModelHistory) history.getUserObject(LINK_TO_LIBSBML)).setCreatedDate(LibSBMLUtils.convertDate(history.getCreatedDate()));
    } else if (prop.equals(TreeNodeChangeEvent.creator)) {
      History history = (History) evtSrc;
      ((org.sbml.libsbml.ModelHistory) history.getUserObject(LINK_TO_LIBSBML)).addCreator(LibSBMLUtils.convertModelCreator((Creator) evt.getNewValue()));
    } else if (prop.equals(TreeNodeChangeEvent.definitionURL)) {
      // no such method for setting definition URL!
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CANNOT_CHANGE_ELEMENT"),
        evtSrc.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.denominator)) {
      if (evtSrc instanceof SpeciesReference) {
        SpeciesReference specRef = (SpeciesReference) evtSrc;
        ((org.sbml.libsbml.SpeciesReference) specRef.getUserObject(LINK_TO_LIBSBML)).setDenominator((Integer) evt.getNewValue());
      } else if (evtSrc instanceof ASTNode) {
        ASTNode node = (ASTNode) evtSrc;
        // TODO: different method call
        ((org.sbml.libsbml.ASTNode) node.getUserObject(LINK_TO_LIBSBML)).setValue(node.getNumerator(), node.getDenominator());
      }
    } else if (prop.equals(TreeNodeChangeEvent.email)) {
      ((org.sbml.libsbml.ModelCreator) ((Creator) evtSrc).getUserObject(LINK_TO_LIBSBML)).setEmail(evt.getNewValue().toString());
    } else if (prop.equals(TreeNodeChangeEvent.encoding)) {
      // no such method for encoding!
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CANNOT_CHANGE_ELEMENT"),
        evtSrc.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.exponent)) {
      if (evtSrc instanceof ASTNode) {
        ASTNode node = (ASTNode) evtSrc;
        ((org.sbml.libsbml.ASTNode) node.getUserObject(LINK_TO_LIBSBML)).setValue(node.getMantissa(), node.getExponent());
      } else if (evtSrc instanceof Unit) {
        ((org.sbml.libsbml.Unit) ((Unit) evtSrc).getUserObject(LINK_TO_LIBSBML)).setExponent((Double) evt.getNewValue());
      }
    } else if (prop.equals(TreeNodeChangeEvent.extentUnits)) {
      ((org.sbml.libsbml.Model) ((Model) evtSrc).getUserObject(LINK_TO_LIBSBML)).setExtentUnits(evt.getNewValue().toString());
    } else if (prop.equals(TreeNodeChangeEvent.familyName)) {
      ((org.sbml.libsbml.ModelCreator) ((Creator) evtSrc).getUserObject(LINK_TO_LIBSBML)).setFamilyName(evt.getNewValue().toString());
    } else if (prop.equals(TreeNodeChangeEvent.fast)) {
      ((org.sbml.libsbml.Reaction) ((Reaction) evtSrc).getUserObject(LINK_TO_LIBSBML)).setFast((Boolean) evt.getNewValue());
    } else if (prop.equals(TreeNodeChangeEvent.formula)) {
      MathContainer mathContainer = (MathContainer) evtSrc;
      Object libSBase = mathContainer.getUserObject(LINK_TO_LIBSBML);
      try {
        Method method = libSBase.getClass().getMethod("setFormula", String.class);
        method.invoke(libSBase, evt.getNewValue().toString());
      } catch (Throwable exc) {
        logger.log(Level.DEBUG, MessageFormat.format(
          bundle.getString("CANNOT_CHANGE_ELEMENT"), mathContainer.getClass().getSimpleName()));
      }
    } else if (prop.equals(TreeNodeChangeEvent.givenName)) {
      ((org.sbml.libsbml.ModelCreator) ((Creator) evtSrc).getUserObject(LINK_TO_LIBSBML)).setGivenName(evt.getNewValue().toString());
    } else if (prop.equals(TreeNodeChangeEvent.hasOnlySubstanceUnits)) {
      ((org.sbml.libsbml.Species) ((Species) evtSrc).getUserObject(LINK_TO_LIBSBML)).setHasOnlySubstanceUnits((Boolean) evt.getNewValue());
    } else if (prop.equals(TreeNodeChangeEvent.history)) {
      // evtSrc is an Annotation-element
      Annotation anno = (Annotation) evtSrc;
      if (anno.getParent() != null) {
        org.sbml.libsbml.SBase sb = (org.sbml.libsbml.SBase) ((TreeNodeWithChangeSupport) anno.getParent()).getUserObject(LINK_TO_LIBSBML);
        sb.setModelHistory(new ModelHistory());
        anno.getHistory().putUserObject(LINK_TO_LIBSBML, sb.getModelHistory());
        LibSBMLUtils.convertHistory(anno.getHistory());
      }
    } else if (prop.equals(TreeNodeChangeEvent.id)) {
      ((org.sbml.libsbml.SBase) ((NamedSBase) evtSrc).getUserObject(LINK_TO_LIBSBML)).setId(evt.getNewValue().toString());
    } else if (prop.equals(TreeNodeChangeEvent.initialAmount)) {
      ((org.sbml.libsbml.Species) ((SBase) evtSrc).getUserObject(LINK_TO_LIBSBML)).setInitialAmount((Double) evt.getNewValue());
    } else if (prop.equals(TreeNodeChangeEvent.initialValue)) {
      if (evtSrc instanceof Trigger) {
        ((org.sbml.libsbml.Trigger) ((SBase) evtSrc).getUserObject(LINK_TO_LIBSBML)).setInitialValue((Boolean) evt.getNewValue());
      } else if (evtSrc instanceof ASTNode) {
        // Do nothing.
      }
    } else if (prop.equals(TreeNodeChangeEvent.isEOF)) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CANNOT_CHANGE_ELEMENT"),
        evtSrc.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.isExplicitlySetConstant)) {
      LocalParameter loc = (LocalParameter) evtSrc;
      Object libObj = loc.getUserObject(LINK_TO_LIBSBML);
      if ((Boolean) evt.getNewValue()) {
        if (libObj instanceof LocalParameter) {
          ((org.sbml.libsbml.LocalParameter) libObj).setConstant(true);
        } else {
          ((org.sbml.libsbml.Parameter) libObj).setConstant(true);
        }
      }
    } else if (prop.equals(TreeNodeChangeEvent.isSetNumberType)) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CANNOT_CHANGE_ELEMENT"),
        evtSrc.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.kind)) {
      Unit u = (Unit) evtSrc;
      ((org.sbml.libsbml.Unit) u.getUserObject(LINK_TO_LIBSBML)).setKind(LibSBMLUtils.convertUnitKind(u.getKind()));
    } else if (prop.equals(TreeNodeChangeEvent.kineticLaw)) {
      // This would be a node added event...
    } else if (prop.equals(TreeNodeChangeEvent.lengthUnits)) {
      ((org.sbml.libsbml.Model) ((SBase) evtSrc).getUserObject(LINK_TO_LIBSBML)).setLengthUnits(evt.getNewValue().toString());
    } else if (prop.equals(TreeNodeChangeEvent.level)) {
      SBase sbase = (SBase) evtSrc;
      libDoc.setLevelAndVersion((Long) evt.getNewValue(), sbase.getVersion());
    } else if (prop.equals(TreeNodeChangeEvent.mantissa)) {
      ASTNode node = (ASTNode) evtSrc;
      ((org.sbml.libsbml.ASTNode) node.getUserObject(LINK_TO_LIBSBML)).setValue((Double) evt.getNewValue(), node.getExponent());
    } else if (prop.equals(TreeNodeChangeEvent.math)) {
      // This will be node added or node deleted
    } else if (prop.equals(TreeNodeChangeEvent.message)) {
      Constraint con = (Constraint) evtSrc;
      XMLNode message = (XMLNode) evt.getNewValue();
      org.sbml.libsbml.XMLNode xml = (message == null) ? null : new org.sbml.libsbml.XMLNode(SBMLtools.toXML(con.getMessage()));
      ((org.sbml.libsbml.Constraint) con.getUserObject(LINK_TO_LIBSBML)).setMessage(xml);
    } else if (prop.equals(TreeNodeChangeEvent.metaId)) {
      ((org.sbml.libsbml.SBase) ((SBase) evtSrc).getUserObject(LINK_TO_LIBSBML)).setMetaId(evt.getNewValue().toString());
    } else if (prop.equals(TreeNodeChangeEvent.model)) {
      // This will be node added or node deleted
    } else if (prop.equals(TreeNodeChangeEvent.modifiedDate)) {
      History history = (History) evtSrc;
      ((org.sbml.libsbml.ModelHistory) history.getUserObject(LINK_TO_LIBSBML)).setModifiedDate(LibSBMLUtils.convertDate(history.getModifiedDate()));
    } else if (prop.equals(TreeNodeChangeEvent.multiplier)) {
      ((org.sbml.libsbml.Unit) ((SBase) evtSrc).getUserObject(LINK_TO_LIBSBML)).setMultiplier((Double) evt.getNewValue());
    } else if (prop.equals(TreeNodeChangeEvent.name)) {
      ((org.sbml.libsbml.SBase) ((SBase) evtSrc).getUserObject(LINK_TO_LIBSBML)).setName(evt.getNewValue().toString());
    } else if (prop.equals(TreeNodeChangeEvent.namespace)) {
      // evtSrc is a XMLToken-element
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CANNOT_CHANGE_ELEMENT"),
        evtSrc.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.nonRDFAnnotation)) {
      //evtSrc is an Annotation-element
      Annotation annot = (Annotation) evtSrc;
      if (annot.getParent() != null) {
        org.sbml.libsbml.SBase sb = (org.sbml.libsbml.SBase) ((SBase) annot.getParent()).getUserObject(LINK_TO_LIBSBML);
        sb.setAnnotation(SBMLtools.toXML(annot.getNonRDFannotation()));
      } else {
        logger.log(Level.DEBUG, MessageFormat.format(
          bundle.getString("CANNOT_CHANGE_ELEMENT_BECAUSE_OF_MISSING_PARENT"),
          evtSrc.getClass().getSimpleName()));
      }
    } else if (prop.equals(TreeNodeChangeEvent.notes)) {
      ((org.sbml.libsbml.SBase) ((SBase) evtSrc).getUserObject(LINK_TO_LIBSBML)).setNotes(evt.getNewValue().toString());
    } else if (prop.equals(TreeNodeChangeEvent.numerator)) {
      ASTNode node = (ASTNode) evtSrc;
      ((org.sbml.libsbml.ASTNode) node.getUserObject(LINK_TO_LIBSBML)).setValue((Double) evt.getNewValue(), node.getDenominator());
    } else if (prop.equals(TreeNodeChangeEvent.offset)) {
      ((org.sbml.libsbml.Unit) ((SBase) evtSrc).getUserObject(LINK_TO_LIBSBML)).setOffset((Double) evt.getNewValue());
    } else if (prop.equals(TreeNodeChangeEvent.organization)) {
      ((org.sbml.libsbml.ModelCreator) ((Creator) evtSrc).getUserObject(LINK_TO_LIBSBML)).setOrganization(evt.getNewValue().toString());
    } else if (prop.equals(TreeNodeChangeEvent.outside)) {
      ((org.sbml.libsbml.Compartment) ((SBase) evtSrc).getUserObject(LINK_TO_LIBSBML)).setOutside(evt.getNewValue().toString());
    } else if (prop.equals(TreeNodeChangeEvent.parentSBMLObject)) {
      // We do not care about that here!
    } else if (prop.equals(TreeNodeChangeEvent.persistent)) {
      ((org.sbml.libsbml.Trigger) ((Trigger) evtSrc).getUserObject(LINK_TO_LIBSBML)).setPersistent((Boolean) evt.getNewValue());
    } else if (prop.equals(TreeNodeChangeEvent.qualifier)) {
      CVTerm cvt = (CVTerm) evtSrc;
      org.sbml.libsbml.CVTerm libTerm = (org.sbml.libsbml.CVTerm) cvt.getUserObject(LINK_TO_LIBSBML);
      libTerm.setQualifierType(LibSBMLUtils.convertCVTermQualifierType(cvt.getQualifierType()));
    } else if (prop.equals(TreeNodeChangeEvent.reversible)) {
      ((org.sbml.libsbml.Reaction) ((SBase) evtSrc).getUserObject(LINK_TO_LIBSBML)).setReversible((Boolean) evt.getNewValue());
    } else if (prop.equals(TreeNodeChangeEvent.sboTerm)) {
      ((org.sbml.libsbml.SBase) ((SBase) evtSrc).getUserObject(LINK_TO_LIBSBML)).setSBOTerm((Integer) evt.getNewValue());
    } else if (prop.equals(TreeNodeChangeEvent.scale)) {
      ((org.sbml.libsbml.Unit) ((SBase) evtSrc).getUserObject(LINK_TO_LIBSBML)).setSBOTerm((Integer) evt.getNewValue());
    } else if (prop.equals(TreeNodeChangeEvent.setAnnotation)) {
      SBase abs = (SBase) evtSrc;
      Annotation annotation = (Annotation) evt.getNewValue();
      org.sbml.libsbml.SBase libSBase = (org.sbml.libsbml.SBase) abs.getUserObject(LINK_TO_LIBSBML);
      if (annotation.getCVTermCount() > 0) {
        for (CVTerm term : annotation.getListOfCVTerms()) {
          libSBase.addCVTerm(LibSBMLUtils.convertCVTerm(term));
        }
      }
      if (annotation.isSetHistory()) {
        libSBase.setModelHistory(LibSBMLUtils.convertHistory(annotation.getHistory()));
      }
      if (annotation.isSetNonRDFannotation()) {
        libSBase.setAnnotation(SBMLtools.toXML(annotation.getNonRDFannotation()));
      }
    } else if (prop.equals(TreeNodeChangeEvent.size)) {
      ((org.sbml.libsbml.Compartment) ((SBase) evtSrc).getUserObject(LINK_TO_LIBSBML)).setSize((Double) evt.getNewValue());
    } else if (prop.equals(TreeNodeChangeEvent.spatialDimensions)) {
      ((org.sbml.libsbml.Compartment) ((SBase) evtSrc).getUserObject(LINK_TO_LIBSBML)).setSpatialDimensions((Double) evt.getNewValue());
    } else if (prop.equals(TreeNodeChangeEvent.spatialSizeUnits)) {
      ((org.sbml.libsbml.Species) ((SBase) evtSrc).getUserObject(LINK_TO_LIBSBML)).setSpatialSizeUnits(evt.getNewValue().toString());
    } else if (prop.equals(TreeNodeChangeEvent.species)) {
      ((org.sbml.libsbml.SimpleSpeciesReference) ((SBase) evtSrc).getUserObject(LINK_TO_LIBSBML)).setSpecies(evt.getNewValue().toString());
    } else if (prop.equals(TreeNodeChangeEvent.speciesType)) {
      ((org.sbml.libsbml.Species) ((SBase) evtSrc).getUserObject(LINK_TO_LIBSBML)).setSpeciesType(evt.getNewValue().toString());
    } else if (prop.equals(TreeNodeChangeEvent.stoichiometry)) {
      ((org.sbml.libsbml.SpeciesReference) ((SBase) evtSrc).getUserObject(LINK_TO_LIBSBML)).setStoichiometry((Double) evt.getNewValue());
    } else if (prop.equals(TreeNodeChangeEvent.style)) {
      ((org.sbml.libsbml.ASTNode) ((ASTNode) evtSrc).getUserObject(LINK_TO_LIBSBML)).setStyle(evt.getNewValue().toString());
    } else if (prop.equals(TreeNodeChangeEvent.substanceUnits)) {
      if (evtSrc instanceof KineticLaw) {
        ((org.sbml.libsbml.KineticLaw) ((SBase) evtSrc).getUserObject(LINK_TO_LIBSBML)).setSubstanceUnits(evt.getNewValue().toString());
      } else if (evtSrc instanceof Model) {
        ((org.sbml.libsbml.Model) ((SBase) evtSrc).getUserObject(LINK_TO_LIBSBML)).setSubstanceUnits(evt.getNewValue().toString());
      }
    } else if (prop.equals(TreeNodeChangeEvent.SBMLDocumentAttributes)) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CANNOT_CHANGE_ELEMENT"),
        evtSrc.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.text)) {
      XMLToken token = (XMLToken) evtSrc;
      org.sbml.libsbml.XMLToken libToken = (org.sbml.libsbml.XMLToken) token.getUserObject(LINK_TO_LIBSBML);
      String chars = libToken.getCharacters();
      libToken.append((evt.getNewValue().toString()).substring(chars.length()));
    } else if (prop.equals(TreeNodeChangeEvent.timeUnits)) {
      if (evtSrc instanceof Event) {
        ((org.sbml.libsbml.Event) ((SBase) evtSrc).getUserObject(LINK_TO_LIBSBML)).setTimeUnits(evt.getNewValue().toString());
      } else if (evtSrc instanceof KineticLaw) {
        ((org.sbml.libsbml.KineticLaw) ((SBase) evtSrc).getUserObject(LINK_TO_LIBSBML)).setTimeUnits(evt.getNewValue().toString());
      } else if (evtSrc instanceof Model) {
        ((org.sbml.libsbml.Model) ((SBase) evtSrc).getUserObject(LINK_TO_LIBSBML)).setTimeUnits(evt.getNewValue().toString());
      }
    } else if (prop.equals(TreeNodeChangeEvent.type)) {
      if (evtSrc instanceof ASTNode) {
        ((org.sbml.libsbml.ASTNode) ((ASTNode) evtSrc).getUserObject(LINK_TO_LIBSBML)).setType(LibSBMLUtils.convertASTNodeType((ASTNode.Type) evt.getNewValue()));
      } else if (evtSrc instanceof CVTerm) {
        ((org.sbml.libsbml.CVTerm) ((CVTerm) evtSrc).getUserObject(LINK_TO_LIBSBML)).setQualifierType(LibSBMLUtils.convertCVTermQualifierType(((CVTerm.Type) evt.getNewValue())));
      }
    } else if (prop.equals(TreeNodeChangeEvent.units)) {
      if (evtSrc instanceof ASTNode) {
        ASTNode n = (ASTNode) evtSrc;
        ((org.sbml.libsbml.ASTNode) n.getUserObject(LINK_TO_LIBSBML)).setUnits(evt.getNewValue().toString());
      } else {
        org.sbml.libsbml.SBase libSBase = (org.sbml.libsbml.SBase) ((SBase) evtSrc).getUserObject(LINK_TO_LIBSBML);
        if (evtSrc instanceof ExplicitRule) {
          ((org.sbml.libsbml.Rule) libSBase).setUnits(evt.getNewValue().toString());
        } else if (evtSrc instanceof KineticLaw) {
          ((org.sbml.libsbml.KineticLaw) libSBase).setTimeUnits(evt.getNewValue().toString());
        } else if (evtSrc instanceof LocalParameter) {
          if (libSBase.getLevel() < 3) {
            ((org.sbml.libsbml.Parameter) libSBase).setUnits(evt.getNewValue().toString());
          } else {
            ((org.sbml.libsbml.LocalParameter) libSBase).setUnits(evt.getNewValue().toString());
          }
        } else if (evtSrc instanceof Parameter) {
          ((org.sbml.libsbml.Parameter) libSBase).setUnits(evt.getNewValue().toString());
        } else if (evtSrc instanceof Species) {
          ((org.sbml.libsbml.Species) libSBase).setUnits(evt.getNewValue().toString());
        } else if (evtSrc instanceof Compartment) {
          ((org.sbml.libsbml.Compartment) libSBase).setUnits(evt.getNewValue().toString());
        }
      }
    } else if (prop.equals(TreeNodeChangeEvent.unsetCVTerms)) {
      ((org.sbml.libsbml.SBase) ((SBase) evtSrc).getUserObject(LINK_TO_LIBSBML)).unsetCVTerms();
    } else if (prop.equals(TreeNodeChangeEvent.userObject)) {
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CANNOT_CHANGE_ELEMENT"),
        evtSrc.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.useValuesFromTriggerTime)) {
      ((org.sbml.libsbml.Event) ((SBase) evtSrc).getUserObject(LINK_TO_LIBSBML)).setUseValuesFromTriggerTime((Boolean) evt.getNewValue());
    } else if (prop.equals(TreeNodeChangeEvent.value)) {
      if (evtSrc instanceof ASTNode) {
        ASTNode n = (ASTNode) evtSrc;
        if (evt.getNewValue() instanceof Double) {
          ((org.sbml.libsbml.ASTNode) n.getUserObject(LINK_TO_LIBSBML)).setValue((Double) evt.getNewValue());
        } else {
          ((org.sbml.libsbml.ASTNode) n.getUserObject(LINK_TO_LIBSBML)).setValue((Character) evt.getNewValue());
        }
      } else {
        org.sbml.libsbml.SBase plugSBase = (org.sbml.libsbml.SBase) ((SBase) evtSrc).getUserObject(LINK_TO_LIBSBML);
        if (evtSrc instanceof LocalParameter) {
          LocalParameter lp = (LocalParameter) evtSrc;
          if (lp.getLevel() < 3) {
            ((org.sbml.libsbml.LocalParameter) plugSBase).setValue((Double) evt.getNewValue());
          } else {
            ((org.sbml.libsbml.Parameter) plugSBase).setValue((Double) evt.getNewValue());
          }
        } else if (evtSrc instanceof Parameter) {
          ((org.sbml.libsbml.Parameter) plugSBase).setValue((Double) evt.getNewValue());
        } else if (evtSrc instanceof Species) {
          Species species = (Species) evtSrc;
          if (species.isSetInitialAmount()) {
            ((org.sbml.libsbml.Species) plugSBase).setInitialAmount((Double) evt.getNewValue());
          } else {
            ((org.sbml.libsbml.Species) plugSBase).setInitialConcentration((Double) evt.getNewValue());
          }
        } else if (evtSrc instanceof Compartment) {
          ((org.sbml.libsbml.Compartment) plugSBase).setSize((Double) evt.getNewValue());
        }
      }
    } else if (prop.equals(TreeNodeChangeEvent.variable)) {
      if (evtSrc instanceof ASTNode) {
        ((org.sbml.libsbml.ASTNode) ((ASTNode) evtSrc).getUserObject(LINK_TO_LIBSBML)).setName(evt.getNewValue().toString());
      } else {
        org.sbml.libsbml.SBase pSBase = (org.sbml.libsbml.SBase) ((SBase) evtSrc).getUserObject(LINK_TO_LIBSBML);
        if (evtSrc instanceof EventAssignment) {
          ((org.sbml.libsbml.EventAssignment) pSBase).setVariable(evt.getNewValue().toString());
        } else if (evtSrc instanceof ExplicitRule) {
          if (evtSrc instanceof AssignmentRule) {
            ((org.sbml.libsbml.AssignmentRule) pSBase).setVariable(evt.getNewValue().toString());
          } else {
            ((org.sbml.libsbml.RateRule) pSBase).setVariable(evt.getNewValue().toString());
          }
        } else if (evtSrc instanceof InitialAssignment) {
          ((org.sbml.libsbml.InitialAssignment) pSBase).setSymbol(evt.getNewValue().toString());
        }
      }
    } else if (prop.equals(TreeNodeChangeEvent.version)) {
      libDoc.setLevelAndVersion(((SBase) evtSrc).getLevel(), ((Integer) evt.getNewValue()).intValue());
    } else if (prop.equals(TreeNodeChangeEvent.volume)) {
      // won't happen because it is changing the "value" of the compartment.
    } else if (prop.equals(TreeNodeChangeEvent.volumeUnits)) {
      ((org.sbml.libsbml.Model) ((SBase) evtSrc).getUserObject(LINK_TO_LIBSBML)).setVolumeUnits(evt.getNewValue().toString());
    } else if (prop.equals(TreeNodeChangeEvent.xmlTriple)) {
      XMLToken token = (XMLToken) evtSrc;
      // TODO!!!
      logger.log(Level.DEBUG, MessageFormat.format(
        bundle.getString("CANNOT_CHANGE_ELEMENT"),
        token.getClass().getSimpleName()));
    } else if (evtSrc instanceof TreeNodeWithChangeSupport) {
      try {
        TreeNodeWithChangeSupport jsbmlObj = (TreeNodeWithChangeSupport) evtSrc;
        String methodName = StringTools.firstLetterUpperCase(evt.getPropertyName());
        Method getter = jsbmlObj.getClass().getMethod("get" + methodName);
        if (getter != null) {
          Object libObj = jsbmlObj.getUserObject(LINK_TO_LIBSBML);
          Method setter = libObj.getClass().getMethod("set" + methodName, getter.getReturnType());
          if (setter != null) {
            setter.invoke(libObj, getter.invoke(jsbmlObj));
          }
        }
      } catch (Throwable t) {
        logger.warn(t.getLocalizedMessage(), t);
      }
    } else {
      logger.warn("Unknown data object of type " + evtSrc.getClass().getSimpleName() + " and property " + prop);
    }
  }

}
