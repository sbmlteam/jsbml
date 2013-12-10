/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
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

import javax.swing.tree.TreeNode;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.AlgebraicRule;
import org.sbml.jsbml.Annotation;
import org.sbml.jsbml.AnnotationElement;
import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.Creator;
import org.sbml.jsbml.History;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.QuantityWithUnit;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.ext.SBasePlugin;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.TreeNodeAdapter;
import org.sbml.jsbml.util.TreeNodeChangeEvent;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.jsbml.util.TreeNodeRemovedEvent;
import org.sbml.jsbml.util.TreeNodeWithChangeSupport;
import org.sbml.jsbml.xml.XMLToken;
import org.sbml.libsbml.ModelCreator;
import org.sbml.libsbml.ModelHistory;
import org.sbml.libsbml.libsbmlConstants;

/**
 * This class listens to the changes in the JSBML document and synchronizes the
 * corresponding LibSBML document with the JSBML-Document.
 * 
 * @author Meike Aichele
 * @author Andreas Dr&auml;ger
 * @version $Rev$
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

  private static final String CANNOT_REMOVE_ELEMENT = "Cannot remove the element {0} in the libSBML document";
  private static final String CANNOT_CHANGE_ELEMENT = "Cannot change the element {0} in the libSBML document";
  private static final String CANNOT_FIND_ELEMENT = "Cannot find the element {0} in the libSBML document";
  private static final String CANNOT_CHANGE_ELEMENT_BECAUSE_OF_MISSING_PARENT = "Cannot change the element {0} in the libSBML document, because there was no parent SBML object found.";
  private static final String PLUGINS_CURRENTLY_NOT_SUPPORTED = "Plugins are currently not supported. The element {0} can therefore not be considered.";
  private static final String ADDING_ELEMENT_TO_LIBSBML_FAILED = "Adding element \"{1}\" of type {0} to libSBML failed{2}.";

  /**
   * 
   * @param doc
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
          logger.warn(MessageFormat.format(ADDING_ELEMENT_TO_LIBSBML_FAILED,
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
      logger.log(Level.DEBUG, String.format("Cannot add node" + treeNodeAd.getClass().getSimpleName()));
    } else if (node instanceof XMLToken) {
      XMLToken token = (XMLToken) node;
      logger.log(Level.DEBUG, String.format("Cannot add node" + token.getClass().getSimpleName()));
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
        logger.log(Level.DEBUG, MessageFormat.format(CANNOT_REMOVE_ELEMENT, node.getClass().getSimpleName()));
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
          exc.printStackTrace();
        }
      }

    } else if (node instanceof TreeNodeAdapter) {
      // This can actually never happen, because then the actual change is directed to propertyChange
      logger.log(Level.DEBUG, MessageFormat.format(CANNOT_REMOVE_ELEMENT, node.getClass().getSimpleName()));
    } else if (node instanceof XMLToken) {
      logger.log(Level.DEBUG, MessageFormat.format(CANNOT_REMOVE_ELEMENT, node.getClass().getSimpleName()));
    } else if (node instanceof SBasePlugin) {
      logger.log(Level.DEBUG, MessageFormat.format(PLUGINS_CURRENTLY_NOT_SUPPORTED, node.getClass().getSimpleName()));
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
      logger.log(Level.DEBUG, MessageFormat.format(CANNOT_CHANGE_ELEMENT, anno.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.addNamespace)) {
      SBase sbase = (SBase) evt;
      org.sbml.libsbml.SBase correspondingElement = (org.sbml.libsbml.SBase) sbase.getUserObject(LINK_TO_LIBSBML);
      correspondingElement.getNamespaces().add((String) evt.getNewValue());
    } else if (prop.equals(TreeNodeChangeEvent.annotation)) {
      SBase sbase = (SBase) evt;
      org.sbml.libsbml.SBase correspondingElement = (org.sbml.libsbml.SBase) sbase.getUserObject(LINK_TO_LIBSBML);
      correspondingElement.unsetAnnotation();
    } else if (prop.equals(TreeNodeChangeEvent.annotationNameSpaces)) {
      Annotation anno = (Annotation) evtSrc;
      logger.log(Level.DEBUG, MessageFormat.format(CANNOT_CHANGE_ELEMENT, anno.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.childNode)) {
      ASTNode node = (ASTNode) evtSrc;
      logger.log(Level.DEBUG, MessageFormat.format(CANNOT_CHANGE_ELEMENT, node.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.createdDate)) {
      History history = (History) evtSrc;
      ((org.sbml.libsbml.ModelHistory) history.getUserObject(LINK_TO_LIBSBML)).setCreatedDate(LibSBMLUtils.convertDate(history.getCreatedDate()));
    } else if (prop.equals(TreeNodeChangeEvent.creator)) {
      History history = (History) evtSrc;
      ((org.sbml.libsbml.ModelHistory) history.getUserObject(LINK_TO_LIBSBML)).addCreator(LibSBMLUtils.convertModelCreator((Creator) evt.getNewValue()));
    } else if (prop.equals(TreeNodeChangeEvent.definitionURL)) {
      ASTNode node = (ASTNode) evtSrc;
      // TODO: no such method for setting definition URL!
      logger.log(Level.DEBUG, MessageFormat.format(CANNOT_FIND_ELEMENT, node.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.denominator) && (evtSrc instanceof ASTNode)) {
      ASTNode node = (ASTNode) evtSrc;
      // TODO: different method call
      ((org.sbml.libsbml.ASTNode) node.getUserObject(LINK_TO_LIBSBML)).setValue(node.getNumerator(), node.getDenominator());
    } else if (prop.equals(TreeNodeChangeEvent.encoding)) {
      ASTNode node = (ASTNode) evtSrc;
      // TOD: no such method for encoding!
      logger.log(Level.DEBUG, MessageFormat.format(CANNOT_CHANGE_ELEMENT, node.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.exponent)) {
      ASTNode node = (ASTNode) evtSrc;
      // TODO: different method call
      ((org.sbml.libsbml.ASTNode) node.getUserObject(LINK_TO_LIBSBML)).setValue(node.getMantissa(), node.getExponent());
    } else if (prop.equals(TreeNodeChangeEvent.formula)) {
      // TODO: For all instances of MathContainer!
      if (evtSrc instanceof KineticLaw) {
        KineticLaw kinLaw = (KineticLaw) evtSrc;
        ((org.sbml.libsbml.KineticLaw) kinLaw.getUserObject(LINK_TO_LIBSBML)).setFormula(kinLaw.getMath().toFormula());
      }
    } else if (prop.equals(TreeNodeChangeEvent.history)) {
      // evtSrc is an Annotation-element
      Annotation anno = (Annotation) evtSrc;
      if (anno.getParent() != null) {
        org.sbml.libsbml.SBase sb = (org.sbml.libsbml.SBase) ((TreeNodeWithChangeSupport) anno.getParent()).getUserObject(LINK_TO_LIBSBML);
        sb.setModelHistory(new ModelHistory());
        anno.getHistory().putUserObject(LINK_TO_LIBSBML, sb.getModelHistory());
        LibSBMLUtils.convertHistory(anno.getHistory());
      }
    } else if (prop.equals(TreeNodeChangeEvent.isEOF)) {
      if (evtSrc instanceof XMLToken) {
        XMLToken token = (XMLToken) evtSrc;
        logger.log(Level.DEBUG, MessageFormat.format(CANNOT_CHANGE_ELEMENT, token.getClass().getSimpleName()));
      }
    } else if (prop.equals(TreeNodeChangeEvent.isExplicitlySetConstant)) {
      if (evtSrc instanceof LocalParameter) {
        LocalParameter loc = (LocalParameter) evtSrc;
        Object libObj = loc.getUserObject(LINK_TO_LIBSBML);
        if (libObj instanceof LocalParameter) {
          ((org.sbml.libsbml.LocalParameter) libObj).setConstant(loc.isExplicitlySetConstant());
        } else {
          ((org.sbml.libsbml.Parameter) libObj).setConstant(loc.isExplicitlySetConstant());
        }
      }
    } else if (prop.equals(TreeNodeChangeEvent.isSetNumberType)) {
      ASTNode node = (ASTNode) evtSrc;
      logger.log(Level.DEBUG, MessageFormat.format(CANNOT_CHANGE_ELEMENT, node.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.kind)) {
      Unit u = (Unit) evtSrc;
      ((org.sbml.libsbml.Unit) u.getUserObject(LINK_TO_LIBSBML)).setKind(LibSBMLUtils.convertUnitKind(u.getKind()));
    } else if (prop.equals(TreeNodeChangeEvent.level)) {
      // TODO: Different method call
      SBase sbase = (SBase) evtSrc;
      libDoc.setLevelAndVersion(sbase.getLevel(), sbase.getVersion());
    } else if (prop.equals(TreeNodeChangeEvent.mantissa)) {
      ASTNode node = (ASTNode) evtSrc;
      // TODO: different method call
      ((org.sbml.libsbml.ASTNode) node.getUserObject(LINK_TO_LIBSBML)).setValue(node.getMantissa(), node.getExponent());
    } else if (prop.equals(TreeNodeChangeEvent.math)) {
      MathContainer mathContainer = (MathContainer) evt.getSource();
      org.sbml.libsbml.SBase libSBase = (org.sbml.libsbml.SBase) mathContainer.getUserObject(LINK_TO_LIBSBML);
      LibSBMLUtils.transferMath(mathContainer, libSBase);
    } else if (prop.equals(TreeNodeChangeEvent.message)) {
      if (evtSrc instanceof Constraint) {
        Constraint con = (Constraint) evtSrc;
        org.sbml.libsbml.XMLNode xml = new org.sbml.libsbml.XMLNode(con.getMessageString());
        ((org.sbml.libsbml.Constraint) con.getUserObject(LINK_TO_LIBSBML)).setMessage(xml);
      }
    } else if (prop.equals(TreeNodeChangeEvent.model)) {
      org.sbml.jsbml.SBMLDocument doc = (org.sbml.jsbml.SBMLDocument) evtSrc;
      ((org.sbml.libsbml.SBMLDocument) doc.getUserObject(LINK_TO_LIBSBML)).setModel(
        LibSBMLUtils.convertModel(doc.getModel()));
    } else if (prop.equals(TreeNodeChangeEvent.modifiedDate)) {
      History history = (History) evtSrc;
      ((org.sbml.libsbml.ModelHistory) history.getUserObject(LINK_TO_LIBSBML)).setModifiedDate(LibSBMLUtils.convertDate(history.getModifiedDate()));
    } else if (prop.equals(TreeNodeChangeEvent.namespace)) {
      // evtSrc is a XMLToken-element
      logger.log(Level.DEBUG, MessageFormat.format(CANNOT_CHANGE_ELEMENT, evtSrc.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.nonRDFAnnotation)) {
      //evtSrc is an Annotation-element
      Annotation annot = (Annotation) evtSrc;
      if (annot.getParent() != null) {
        org.sbml.libsbml.SBase sb = (org.sbml.libsbml.SBase) ((SBase) annot.getParent()).getUserObject(LINK_TO_LIBSBML);
        sb.setAnnotation(annot.getNonRDFannotation().toXMLString());
      } else {
        logger.log(Level.DEBUG, MessageFormat.format(CANNOT_CHANGE_ELEMENT_BECAUSE_OF_MISSING_PARENT, evtSrc.getClass().getSimpleName()));
      }
    } else if (prop.equals(TreeNodeChangeEvent.notes)) {
      SBase sbase = (SBase) evtSrc;
      org.sbml.libsbml.SBase correspondingElement = (org.sbml.libsbml.SBase) sbase.getUserObject(LINK_TO_LIBSBML);
      correspondingElement.setNotes((String) evt.getNewValue());
    } else if (prop.equals(TreeNodeChangeEvent.numerator)) {
      ASTNode node = (ASTNode) evtSrc;
      // TODO: different method name
      ((org.sbml.libsbml.ASTNode) node.getUserObject(LINK_TO_LIBSBML)).setValue(node.getNumerator(), node.getDenominator());
    } else if (prop.equals(TreeNodeChangeEvent.parentSBMLObject)) {
      if (evtSrc instanceof ASTNode) {
        ASTNode node = (ASTNode) evtSrc;
        logger.log(Level.DEBUG, MessageFormat.format(CANNOT_CHANGE_ELEMENT, node.getClass().getSimpleName()));
      }
    } else if (prop.equals(TreeNodeChangeEvent.qualifier)) {
      CVTerm cvt = (CVTerm) evtSrc;
      org.sbml.libsbml.CVTerm libTerm = (org.sbml.libsbml.CVTerm) cvt.getUserObject(LINK_TO_LIBSBML);
      libTerm.setQualifierType(LibSBMLUtils.convertCVTermQualifierType(cvt.getQualifierType()));
    } else if (prop.equals(TreeNodeChangeEvent.SBMLDocumentAttributes)) {
      // TODO
      org.sbml.jsbml.SBMLDocument sbmlDoc = (org.sbml.jsbml.SBMLDocument) evtSrc;
      if (!sbmlDoc.getSBMLDocumentAttributes().containsValue(libDoc.getLevel())) {
        propertyChange(new TreeNodeChangeEvent(sbmlDoc, TreeNodeChangeEvent.level, libDoc.getLevel() , sbmlDoc.getLevel()));
      }
      if (!sbmlDoc.getSBMLDocumentAttributes().containsValue(libDoc.getVersion())) {
        propertyChange(new TreeNodeChangeEvent(sbmlDoc, TreeNodeChangeEvent.version, libDoc.getVersion() , sbmlDoc.getVersion()));
      }
    } else if (prop.equals(TreeNodeChangeEvent.setAnnotation)) {
      // TODO
      org.sbml.libsbml.SBase correspondingElement = (org.sbml.libsbml.SBase) ((SBase) evtSrc).getUserObject(LINK_TO_LIBSBML);
      correspondingElement.setAnnotation((String) evt.getNewValue());
    } else if (prop.equals(TreeNodeChangeEvent.text)) {
      // TODO
      logger.log(Level.DEBUG, MessageFormat.format(CANNOT_CHANGE_ELEMENT, evtSrc.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.type)) {
      ASTNode node = (ASTNode) evtSrc;
      logger.log(Level.DEBUG, MessageFormat.format(CANNOT_CHANGE_ELEMENT, node.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.units) && (evtSrc instanceof AlgebraicRule)) {
      AlgebraicRule algRule = (AlgebraicRule) evtSrc;
      org.sbml.libsbml.AlgebraicRule libAlgRule = (org.sbml.libsbml.AlgebraicRule) algRule.getUserObject(LINK_TO_LIBSBML);
      libAlgRule.setUnits(algRule.getDerivedUnits());
    } else if (prop.equals(TreeNodeChangeEvent.unsetCVTerms)) {
      ((org.sbml.libsbml.SBase) ((SBase) evtSrc).getUserObject(LINK_TO_LIBSBML)).unsetCVTerms();
    } else if (prop.equals(TreeNodeChangeEvent.userObject)) {
      logger.log(Level.DEBUG, MessageFormat.format(CANNOT_CHANGE_ELEMENT, evtSrc.getClass().getSimpleName()));
    } else if (prop.equals(TreeNodeChangeEvent.value)
        && !((evtSrc instanceof Parameter) || (evtSrc instanceof LocalParameter))) {
      if (evtSrc instanceof ASTNode) {
        ASTNode node = (ASTNode) evtSrc;
        ((org.sbml.libsbml.ASTNode) node.getUserObject(LINK_TO_LIBSBML)).setValue(node.getReal());
      } else if (evtSrc instanceof QuantityWithUnit) {
        if (evtSrc instanceof Species) {
          Species spec = (Species) evtSrc;
          if (spec.isSetInitialAmount()) {
            libDoc.getModel().getSpecies(spec.getId()).setInitialAmount(spec.getInitialAmount());
          } else {
            libDoc.getModel().getSpecies(spec.getId()).setInitialConcentration(spec.getInitialConcentration());
          }
        } else if (evtSrc instanceof Compartment) {
          Compartment comp = (Compartment) evtSrc;
          ((org.sbml.libsbml.Compartment) comp.getUserObject(LINK_TO_LIBSBML)).setSize(comp.getSize());
        }
      }
    } else if (prop.equals(TreeNodeChangeEvent.version)) {
      libDoc.setLevelAndVersion(((AbstractSBase) evtSrc).getLevel(), ((AbstractSBase) evtSrc).getVersion());
    } else if (prop.equals(TreeNodeChangeEvent.xmlTriple)) {
      XMLToken token = (XMLToken) evtSrc;
      logger.log(Level.DEBUG, MessageFormat.format(CANNOT_CHANGE_ELEMENT, token.getClass().getSimpleName()));
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
