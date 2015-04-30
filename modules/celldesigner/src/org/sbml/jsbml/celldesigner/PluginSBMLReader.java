/*
 * $Id$
 * $URL$
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
package org.sbml.jsbml.celldesigner;

import static org.sbml.jsbml.celldesigner.CellDesignerConstants.LINK_TO_CELLDESIGNER;

import java.awt.Dimension;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.xml.stream.XMLStreamException;

import jp.sbi.celldesigner.plugin.PluginAlgebraicRule;
import jp.sbi.celldesigner.plugin.PluginAssignmentRule;
import jp.sbi.celldesigner.plugin.PluginCompartment;
import jp.sbi.celldesigner.plugin.PluginCompartmentType;
import jp.sbi.celldesigner.plugin.PluginConstraint;
import jp.sbi.celldesigner.plugin.PluginEvent;
import jp.sbi.celldesigner.plugin.PluginEventAssignment;
import jp.sbi.celldesigner.plugin.PluginFunctionDefinition;
import jp.sbi.celldesigner.plugin.PluginInitialAssignment;
import jp.sbi.celldesigner.plugin.PluginKineticLaw;
import jp.sbi.celldesigner.plugin.PluginListOf;
import jp.sbi.celldesigner.plugin.PluginModel;
import jp.sbi.celldesigner.plugin.PluginModifierSpeciesReference;
import jp.sbi.celldesigner.plugin.PluginParameter;
import jp.sbi.celldesigner.plugin.PluginRateRule;
import jp.sbi.celldesigner.plugin.PluginReaction;
import jp.sbi.celldesigner.plugin.PluginRule;
import jp.sbi.celldesigner.plugin.PluginSBase;
import jp.sbi.celldesigner.plugin.PluginSpecies;
import jp.sbi.celldesigner.plugin.PluginSpeciesAlias;
import jp.sbi.celldesigner.plugin.PluginSpeciesReference;
import jp.sbi.celldesigner.plugin.PluginSpeciesType;
import jp.sbi.celldesigner.plugin.PluginUnit;
import jp.sbi.celldesigner.plugin.PluginUnitDefinition;
import jp.sbi.celldesigner.plugin.util.PluginSpeciesSymbolType;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.sbml.jsbml.AlgebraicRule;
import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.CompartmentType;
import org.sbml.jsbml.Constraint;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.ExplicitRule;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.InitialAssignment;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.ModifierSpeciesReference;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.RateRule;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLInputConverter;
import org.sbml.jsbml.SBO;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.SpeciesType;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.celldesigner.libsbml.LibSBMLReader;
import org.sbml.jsbml.celldesigner.libsbml.LibSBMLUtils;
import org.sbml.jsbml.ext.fbc.AbstractFBCSBasePlugin;
import org.sbml.jsbml.ext.fbc.FBCConstants;
import org.sbml.jsbml.ext.fbc.FBCModelPlugin;
import org.sbml.jsbml.ext.fbc.FBCSpeciesPlugin;
import org.sbml.jsbml.ext.layout.CompartmentGlyph;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.ext.layout.LayoutModelPlugin;
import org.sbml.jsbml.ext.render.LocalRenderInformation;
import org.sbml.jsbml.ext.render.LocalStyle;
import org.sbml.jsbml.ext.render.RenderConstants;
import org.sbml.jsbml.ext.render.RenderGroup;
import org.sbml.jsbml.ext.render.RenderLayoutPlugin;
import org.sbml.jsbml.util.ProgressListener;
import org.sbml.jsbml.util.SBMLtools;
import org.sbml.libsbml.libsbml;

/**
 *
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 * @since 0.8
 */
@SuppressWarnings("deprecation")
public class PluginSBMLReader implements SBMLInputConverter<PluginModel> {

  /**
   *
   */
  private static final int level = 3;
  /**
   *
   */
  private static final int version = 1;

  /**
   * A {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger(PluginSBMLReader.class);

  /**
   *
   */
  private ProgressListener listener;

  /**
   *
   */
  private Model model;

  /**
   * A mapping between a PluginSBase and a Set of derived JSBML SBases.
   */
  private final Map<PluginSBase,Set<SBase>> mapOfSBases = new HashMap<PluginSBase,Set<SBase>>();

  /**
   *
   */
  private Set<Integer> possibleEnzymes;
  /**
   *
   */
  public PluginSBMLReader() {
    this(SBO.getDefaultPossibleEnzymes());
  }

  /**
   * get a model from the CellDesigner output, converts it to JSBML
   * format and stores it
   *
   * @param model
   * @param possibleEnzymes
   * @throws XMLStreamException
   */
  public PluginSBMLReader(PluginModel model, Set<Integer> possibleEnzymes) throws XMLStreamException {
    this(possibleEnzymes);
    try {
      this.model = convertModel(model);
    } catch (RuntimeException exc) {
      logger.log(Priority.ERROR, exc.getLocalizedMessage() != null ? exc.getLocalizedMessage() : exc.getMessage(), exc);
      throw exc;
    }
  }

  /**
   * @param possibleEnzymes
   */
  public PluginSBMLReader(Set<Integer> possibleEnzymes) {
    super();
    this.possibleEnzymes = possibleEnzymes;
  }

  /**
   * clears the HashMap
   */
  protected void clearMap()
  {
    mapOfSBases.clear();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBMLReader#readModel(java.lang.Object)
   */
  /**
   * Converts all PluginSBases inside the PluginModel to their JSBML counterparts.
   * @return the JSBML Model.
   */
  @Override
  public Model convertModel(PluginModel originalModel) throws XMLStreamException {
    try{
      int total = 0, curr = 0;

      total = originalModel.getNumCVTerms();
      total += originalModel.getNumUnitDefinitions();
      total += originalModel.getNumFunctionDefinitions();
      total += originalModel.getNumCompartmentTypes();
      total += originalModel.getNumSpeciesTypes();
      total += originalModel.getNumCompartments();
      total += originalModel.getNumSpecies();
      total += originalModel.getNumParameters();
      total += originalModel.getNumInitialAssignments();
      total += originalModel.getNumRules();
      total += originalModel.getNumConstraints();
      total += originalModel.getNumReactions();
      total += originalModel.getNumEvents();

      if (listener != null) {
        listener.progressStart(total);
      }

      model = new Model(originalModel.getId(), level, version);

      String layoutNamespace = LayoutConstants.getNamespaceURI(model.getLevel(), model.getVersion());
      String renderNamespace = RenderConstants.getNamespaceURI(model.getLevel(), model.getVersion());
      String FBCNamespace = FBCConstants.getNamespaceURI(model.getLevel(), model.getVersion());

      LayoutModelPlugin modelPlugin = new LayoutModelPlugin(model);
      Layout layout = modelPlugin.createLayout("CellDesigner_Layout");

      RenderLayoutPlugin renderPlugin = new RenderLayoutPlugin(layout);
      LocalRenderInformation localRenderInformation = new LocalRenderInformation(model.getLevel(), model.getVersion());
      renderPlugin.addLocalRenderInformation(localRenderInformation);

      localRenderInformation.addLocalStyle(new LocalStyle("compartmentStyle", model.getLevel(), model.getVersion(),
        new RenderGroup(model.getLevel(), model.getVersion())));
      localRenderInformation.addLocalStyle(new LocalStyle("speciesAliasStyle", model.getLevel(), model.getVersion(),
        new RenderGroup(model.getLevel(), model.getVersion())));

      AbstractFBCSBasePlugin FBCPlugin = new FBCModelPlugin(model);

      model.addExtension(FBCNamespace, FBCPlugin);
      model.addExtension(layoutNamespace, modelPlugin);
      layout.addExtension(renderNamespace, renderPlugin);

      PluginUtils.transferNamedSBaseProperties(originalModel, model);
      if (listener != null) {
        curr += originalModel.getNumCVTerms();
        listener.progressUpdate(curr, null);
      }

      int i;
      for (i = 0; i < originalModel.getNumUnitDefinitions(); i++) {
        model.addUnitDefinition(readUnitDefinition(originalModel.getUnitDefinition(i)));
        Set<SBase> list = new HashSet<SBase>();
        list.add(readUnitDefinition(originalModel.getUnitDefinition(i)));
        mapOfSBases.put(originalModel.getUnitDefinition(i), list);

        if (listener != null) {
          listener.progressUpdate(++curr, "Unit definitions");
        }
      }

      // This is something, libSBML wouldn't do...
      SBMLtools.addPredefinedUnitDefinitions(model);
      for (i = 0; i < originalModel.getNumFunctionDefinitions(); i++) {
        model.addFunctionDefinition(readFunctionDefinition(originalModel.getFunctionDefinition(i)));
        Set<SBase> list = new HashSet<SBase>();
        list.add(readFunctionDefinition(originalModel.getFunctionDefinition(i)));
        mapOfSBases.put(originalModel.getFunctionDefinition(i), list);

        if (listener != null) {
          listener.progressUpdate(++curr, "Funciton definitions");
        }
      }

      for (i = 0; i < originalModel.getNumCompartmentTypes() && (model.getLevel() < 3); i++) {
        model.addCompartmentType(readCompartmentType(originalModel.getCompartmentType(i)));
        Set<SBase> list = new HashSet<SBase>();
        list.add(readCompartmentType(originalModel.getCompartmentType(i)));
        mapOfSBases.put(originalModel.getCompartmentType(i), list);
        if (listener != null) {
          listener.progressUpdate(++curr, "Compartment types");
        }
      }

      for (i = 0; i < originalModel.getNumSpeciesTypes() && (model.getLevel() < 3); i++) {
        model.addSpeciesType(readSpeciesType(originalModel.getSpeciesType(i)));
        Set<SBase> list = new HashSet<SBase>();
        list.add(readSpeciesType(originalModel.getSpeciesType(i)));
        mapOfSBases.put(originalModel.getSpeciesType(i), list);
        if (listener != null) {
          listener.progressUpdate(++curr, "Species types");
        }
      }

      for (i = originalModel.getNumCompartments()-1; i >= 0; i--) {
        PluginCompartment pCompartment = originalModel.getCompartment(i);
        Set<SBase> list = LayoutConverter.extractLayout(pCompartment, layout);
        Compartment compartment = readCompartment(pCompartment);
        model.addCompartment(compartment);
        list.add(compartment);
        mapOfSBases.put(pCompartment, list);
        //RenderConverter.extractRenderInformation(pCompartment, renderPlugin.getLocalRenderInformation(0), layout);
        //gets the compartment size
        if (listener != null) {
          listener.progressUpdate(++curr, "Compartments");
        }
      }
      //sorting the compartments by their areas. This is done to order them largest to smallest when visualizing them.
      ListOf<CompartmentGlyph>  listOfCompartmentGlyphs = layout.getListOfCompartmentGlyphs();
      Collections.sort(listOfCompartmentGlyphs, new Comparator<CompartmentGlyph>(){
        @Override
        public int compare(CompartmentGlyph cGlyph1, CompartmentGlyph cGlyph2){
          int cGlyph1Area = (int) (cGlyph1.getBoundingBox().getDimensions().getWidth()*
              cGlyph1.getBoundingBox().getDimensions().getHeight());
          int cGlyph2Area = (int) (cGlyph2.getBoundingBox().getDimensions().getWidth()*
              cGlyph2.getBoundingBox().getDimensions().getHeight());
          int cmp = Double.compare(cGlyph2Area, cGlyph1Area);
          return cmp;
        }});
      layout.createDimensions("Layout_Size", layout.getListOfCompartmentGlyphs().get(0).getBoundingBox().getDimensions().getWidth()*1.1,
        layout.getListOfCompartmentGlyphs().get(0).getBoundingBox().getDimensions().getHeight()*1.15, 1d);

      for (i = 0; i < originalModel.getNumSpecies(); i++) {
        PluginSpecies pSpecies = originalModel.getSpecies(i);
        PluginListOf listOfAliases = pSpecies.getListOfSpeciesAlias();
        Species species = readSpecies(pSpecies);
        model.addSpecies(species);

        Set<SBase> speciesList = new HashSet<SBase>();
        speciesList.add(species);
        mapOfSBases.put(pSpecies, speciesList);

        for (int j=0;j<listOfAliases.size();j++)
        {
          Set<SBase> list = LayoutConverter.extractLayout((PluginSpeciesAlias)listOfAliases.get(j), layout);
          mapOfSBases.put(listOfAliases.get(j), list);
        }
        if (listener != null) {
          listener.progressUpdate(++curr, "Species");
        }
      }

      for (i = 0; i < originalModel.getNumParameters(); i++) {
        model.addParameter(readParameter(originalModel.getParameter(i)));
        Set<SBase> list = new HashSet<SBase>();
        list.add(readParameter(originalModel.getParameter(i)));
        mapOfSBases.put(originalModel.getParameter(i), list);

        if (listener != null) {
          listener.progressUpdate(++curr, "Parameters");
        }
      }

      for (i = 0; i < originalModel.getNumInitialAssignments(); i++) {
        model.addInitialAssignment(readInitialAssignment(originalModel.getInitialAssignment(i)));
        Set<SBase> list = new HashSet<SBase>();
        list.add(readInitialAssignment(originalModel.getInitialAssignment(i)));
        mapOfSBases.put(originalModel.getInitialAssignment(i), list);

        if (listener != null) {
          listener.progressUpdate(++curr, "Initial assignments");
        }
      }

      for (i = 0; i < originalModel.getNumRules(); i++) {
        model.addRule(readRule(originalModel.getRule(i)));
        Set<SBase> list = new HashSet<SBase>();
        list.add(readRule(originalModel.getRule(i)));
        mapOfSBases.put(originalModel.getRule(i), list);

        if (listener != null) {
          listener.progressUpdate(++curr, "Rules");
        }
      }

      for (i = 0; i < originalModel.getNumConstraints(); i++) {
        model.addConstraint(readConstraint(originalModel.getConstraint(i)));
        Set<SBase> list = new HashSet<SBase>();
        list.add(readConstraint(originalModel.getConstraint(i)));
        mapOfSBases.put(originalModel.getConstraint(i), list);

        if (listener != null) {
          listener.progressUpdate(++curr, "Constraints");
        }
      }

      for (i = 0; i < originalModel.getNumReactions(); i++) {
        PluginReaction pReaction = originalModel.getReaction(i);
        Reaction reaction = readReaction(pReaction);
        model.addReaction(reaction);
        Set<SBase> list = LayoutConverter.extractLayout(pReaction, layout);
        list.add(reaction);
        mapOfSBases.put(pReaction, list);
        if (listener != null) {
          listener.progressUpdate(++curr, "Reactions");
        }
      }
      //LayoutConverter.debugReactionsInLayoutConverter();

      for (i = 0; i < originalModel.getNumEvents(); i++) {
        model.addEvent(readEvent(originalModel.getEvent(i)));
        Set<SBase> list = new HashSet<SBase>();
        list.add(readEvent(originalModel.getEvent(i)));
        mapOfSBases.put(originalModel.getEvent(i), list);

        if (listener != null) {
          listener.progressUpdate(++curr, "Events");
        }
      }

      if (listener != null) {
        listener.progressFinish();
      }
    }
    catch (Throwable e)
    {
      new GUIErrorConsole(e);
    }
    return model;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBMLInputConverter#convertSBMLDocument(java.io.File)
   */
  @Override
  public SBMLDocument convertSBMLDocument(File sbmlFile) throws Exception {
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBMLInputConverter#convertSBMLDocument(java.lang.String)
   */
  @Override
  public SBMLDocument convertSBMLDocument(String fileName) throws Exception {
    return null;
  }

  /**
   *
   * @return
   */
  public int getErrorCount() {
    return 0;
  }

  /**
   * @return the JSBML Model
   */
  protected Model getModel() {
    return model;
  }

  /**
   *
   * @return
   */
  public int getNumErrors() {
    return getErrorCount();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBMLInputConverter#getOriginalModel()
   */
  @Override
  public PluginModel getOriginalModel() {
    return (PluginModel) model.getUserObject(LINK_TO_CELLDESIGNER);
  }

  /**
   * 
   * @return the mapping between PluginSBases and the Set of derived SBases
   */
  protected Map<PluginSBase,Set<SBase>> getPluginSBase_SBaseMappings()
  {
    return mapOfSBases;
  }

  /**
   * @return the possibleEnzymes
   */
  public Set<Integer> getPossibleEnzymes() {
    return possibleEnzymes;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBMLReader#getWarnings()
   */
  @Override
  public List<SBMLException> getWarnings() {
    return new LinkedList<SBMLException>();
  }

  /**
   * Prints a list of the HashMap alphabetized by PluginSBase in a GUI window.
   */
  protected void printMap()
  {
    StringBuffer mapText = new StringBuffer();
    Iterator<Entry<PluginSBase, Set<SBase>>> it = mapOfSBases.entrySet().iterator();
    Map<String, Set<SBase>> treeMap = new TreeMap<String, Set<SBase>>();

    final int indexOfPluginSBaseName = 27;
    final String atSymbolBeforeHashcode = "@";
    for (int i =0; it.hasNext(); i++)
    {
      Entry<PluginSBase, Set<SBase>> pairs = it.next();
      String pluginSBaseName = pairs.getKey().toString().
          substring(indexOfPluginSBaseName, pairs.getKey().toString().indexOf(atSymbolBeforeHashcode));
      treeMap.put(pluginSBaseName+"_"+i, pairs.getValue());
    }

    Iterator<Entry<String, Set<SBase>>> newIt = treeMap.entrySet().iterator();
    while (newIt.hasNext())
    {
      Entry<String, Set<SBase>> pairs = newIt.next();
      mapText.append(pairs.getKey().substring(0, pairs.getKey().indexOf("_"))+"\t"+pairs.getValue()+"\n");
    }
    JScrollPane pane = new JScrollPane(new JTextArea(mapText.toString()));
    pane.setPreferredSize(new Dimension(640, 480));
    JOptionPane.showMessageDialog(null,pane, "HashMap",JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   *
   * @param compartment
   * @return
   * @throws XMLStreamException
   */
  protected Compartment readCompartment(PluginCompartment compartment) throws XMLStreamException {
    Compartment c = new Compartment(compartment.getId(), level, version);
    PluginUtils.transferNamedSBaseProperties(compartment, c);
    if ((compartment.getOutside() != null) && (compartment.getOutside().length() > 0) && (c.getLevel() < 3)) {
      c.setOutside(compartment.getOutside());
    }
    if ((compartment.getCompartmentType() != null)
        && (compartment.getCompartmentType().length() > 0)
        && (model.getCompartmentType(compartment.getCompartmentType()) != null) && (c.getLevel() < 3)) {
      /*
       * Note: the third check is necessary because CellDesigner might return
       * one of its Compartment Classes as CompartmentType that has no real
       * counterpart!
       */
      c.setCompartmentType(compartment.getCompartmentType());
    }
    c.setConstant(compartment.getConstant());
    c.setSize(compartment.getSize());
    c.setSpatialDimensions((short) compartment.getSpatialDimensions());
    if (compartment.getUnits().length() > 0) {
      c.setUnits(compartment.getUnits());
    }
    return c;
  }

  /**
   *
   * @param compartmentType
   * @return
   * @throws XMLStreamException
   */
  protected CompartmentType readCompartmentType(PluginCompartmentType compartmentType) throws XMLStreamException {
    CompartmentType com = new CompartmentType(compartmentType.getId(), level, version);
    PluginUtils.transferNamedSBaseProperties(compartmentType, com);
    return com;
  }

  /**
   *
   * @param constraint
   * @return
   * @throws XMLStreamException
   */
  protected Constraint readConstraint(PluginConstraint constraint) throws XMLStreamException {
    Constraint c = new Constraint(level, version);
    PluginUtils.transferSBaseProperties(constraint, c);
    if (constraint.getMath() != null) {
      c.setMath(LibSBMLUtils.convert(libsbml.parseFormula(constraint.getMath()), c));
    }
    if (constraint.getMessage().length() > 0) {
      try {
        c.setMessage(constraint.getMessage());
      } catch (XMLStreamException exc) {
        logger.warn(exc.getLocalizedMessage() != null ? exc.getLocalizedMessage() : exc.getMessage(), exc);
      }
    }
    return c;
  }

  /**
   *
   * @param event
   * @return
   * @throws XMLStreamException
   */
  protected Event readEvent(PluginEvent event) throws XMLStreamException {
    Event e = new Event(level, version);
    PluginUtils.transferNamedSBaseProperties(event, e);
    if (event.getTrigger() != null) {
      e.setTrigger(LibSBMLReader.readTrigger(event.getTrigger()));
    }
    if (event.getDelay() != null) {
      e.setDelay(LibSBMLReader.readDelay(event.getDelay()));
    }
    for (int i = 0; i < event.getNumEventAssignments(); i++) {
      e.addEventAssignment(readEventAssignment(event.getEventAssignment(i)));
    }
    if ((event.getTimeUnits() != null) && (event.getTimeUnits().length() > 0) && (e.getLevel() < 3)) {
      e.setTimeUnits(event.getTimeUnits());
    }
    e.setUseValuesFromTriggerTime(event.getUseValuesFromTriggerTime());
    return e;
  }

  /**
   *
   * @param eventass
   * @return
   * @throws XMLStreamException
   */
  private EventAssignment readEventAssignment(PluginEventAssignment eventass) throws XMLStreamException {
    EventAssignment ev = new EventAssignment(level, version);
    PluginUtils.transferSBaseProperties(eventass, ev);
    if ((eventass.getVariable() != null)
        && (eventass.getVariable().length() > 0)) {
      ev.setVariable(eventass.getVariable());
    }
    if (eventass.getMath() != null) {
      ev.setMath(LibSBMLUtils.convert(eventass.getMath(), ev));
    }
    return ev;
  }

  /**
   *
   * @param functionDefinition
   * @return
   * @throws XMLStreamException
   */
  protected FunctionDefinition readFunctionDefinition(PluginFunctionDefinition functionDefinition) throws XMLStreamException {
    FunctionDefinition f = new FunctionDefinition(level, version);
    PluginUtils.transferNamedSBaseProperties(functionDefinition, f);
    if (functionDefinition.getMath() != null) {
      f.setMath(LibSBMLUtils.convert(functionDefinition.getMath(), f));
    }
    return f;
  }

  /**
   *
   * @param initialAssignment
   * @return
   * @throws XMLStreamException
   */
  protected InitialAssignment readInitialAssignment(PluginInitialAssignment initialAssignment) throws XMLStreamException {
    if (initialAssignment.getSymbol() == null) {
      throw new IllegalArgumentException(
          "Symbol attribute not set for InitialAssignment");
    }
    InitialAssignment ia = new InitialAssignment(level, version);
    ia.setVariable(initialAssignment.getSymbol());
    PluginUtils.transferSBaseProperties(initialAssignment, ia);
    if (initialAssignment.getMath() != null) {
      ia.setMath(LibSBMLUtils.convert(libsbml.parseFormula(initialAssignment.getMath()), ia));
    }
    return ia;
  }

  /**
   *
   * @param kineticLaw
   * @return
   * @throws XMLStreamException
   */
  private KineticLaw readKineticLaw(PluginKineticLaw kineticLaw) throws XMLStreamException {
    KineticLaw kinlaw = new KineticLaw(level, version);
    PluginUtils.transferSBaseProperties(kineticLaw, kinlaw);
    for (int i = 0; i < kineticLaw.getNumParameters(); i++) {
      kinlaw.addLocalParameter(readLocalParameter(kineticLaw.getParameter(i)));
    }
    if (kineticLaw.getMath() != null) {
      kinlaw.setMath(LibSBMLUtils.convert(kineticLaw.getMath(), kinlaw));
    } else if (kineticLaw.getFormula().length() > 0) {
      kinlaw.setMath(LibSBMLUtils.convert(libsbml.readMathMLFromString(kineticLaw.getFormula()), kinlaw));
    }
    return kinlaw;
  }

  /**
   *
   * @param parameter
   * @return
   * @throws XMLStreamException
   */
  private LocalParameter readLocalParameter(PluginParameter parameter) throws XMLStreamException {
    return new LocalParameter(readParameter(parameter));
  }

  /**
   *
   * @param modifierSpeciesReference
   * @return
   * @throws XMLStreamException
   */
  private ModifierSpeciesReference readModifierSpeciesReference(
    PluginModifierSpeciesReference modifierSpeciesReference) throws XMLStreamException {
    ModifierSpeciesReference mod = new ModifierSpeciesReference(level, version);
    mod.setSpecies(modifierSpeciesReference.getSpecies());
    PluginUtils.transferNamedSBaseProperties(modifierSpeciesReference, mod);
    /*
     * Set SBO term.
     */
    mod.setSBOTerm(SBO.convertAlias2SBO(modifierSpeciesReference.getModificationType()));
    if (SBO.isCatalyst(mod.getSBOTerm())) {
      PluginSpecies species = modifierSpeciesReference.getSpeciesInstance();
      String speciesAliasType = species.getSpeciesAlias(0).getType()
          .equals("PROTEIN") ? species.getSpeciesAlias(0)
            .getProtein().getType() : species.getSpeciesAlias(0)
            .getType();
            int sbo = SBO.convertAlias2SBO(speciesAliasType);
            if (possibleEnzymes.contains(Integer.valueOf(sbo))) {
              mod.setSBOTerm(SBO.getEnzymaticCatalysis());
            }
    }
    return mod;
  }

  /**
   *
   * @param parameter
   * @return
   * @throws XMLStreamException
   */
  protected Parameter readParameter(PluginParameter parameter) throws XMLStreamException {
    Parameter para = new Parameter(level, version);
    PluginUtils.transferNamedSBaseProperties(parameter, para);
    para.setValue(parameter.getValue());
    para.setConstant(parameter.getConstant());
    if ((parameter.getUnits() != null) && (parameter.getUnits().length() > 0)) {
      para.setUnits(parameter.getUnits());
    }
    return para;
  }

  /**
   *
   * @param reac
   * @return
   * @throws XMLStreamException
   */
  protected Reaction readReaction(PluginReaction reac) throws XMLStreamException {
    logger.debug("Translating reaction " + reac.getId());
    Reaction reaction = new Reaction(reac.getId(), level, version);
    PluginUtils.transferNamedSBaseProperties(reac, reaction);
    logger.debug("NamedSBase properties done");
    for (int i = 0; i < reac.getNumReactants(); i++) {
      reaction.addReactant(readSpeciesReference(reac.getReactant(i)));
    }
    logger.debug("Reactants done");
    for (int i = 0; i < reac.getNumProducts(); i++) {
      reaction.addProduct(readSpeciesReference(reac.getProduct(i)));
    }
    logger.debug("Products done");
    for (int i = 0; i < reac.getNumModifiers(); i++) {
      reaction.addModifier(readModifierSpeciesReference(reac.getModifier(i)));
    }
    logger.debug("Modifiers done");
    reaction.setFast(reac.getFast());
    reaction.setReversible(reac.getReversible());
    int sbo = SBO.convertAlias2SBO(reac.getReactionType());
    if (SBO.checkTerm(sbo)) {
      reaction.setSBOTerm(sbo);
    }
    logger.debug("Reaction properties copied");
    if (reac.getKineticLaw() != null) {
      reaction.setKineticLaw(readKineticLaw(reac.getKineticLaw()));
    }
    logger.debug("Kinetic law copied");
    return reaction;
  }

  /**
   *
   * @param rule
   * @return
   * @throws XMLStreamException
   */
  protected Rule readRule(PluginRule rule) throws XMLStreamException {
    Rule r;
    if (rule instanceof PluginAlgebraicRule) {
      r = new AlgebraicRule(level, version);
    } else {
      String variable = null;
      if (rule instanceof PluginAssignmentRule) {
        r = new AssignmentRule(level, version);
        variable = ((PluginAssignmentRule) rule).getVariable();
      } else {
        r = new RateRule(level, version);
        variable = ((PluginRateRule) rule).getVariable();
      }
      ExplicitRule er = (ExplicitRule) r;
      er.setVariable(variable);
    }
    PluginUtils.transferSBaseProperties(rule, r);
    if (rule.getMath() != null) {
      r.setMath(LibSBMLUtils.convert(rule.getMath(), r));
    }
    return r;
  }

  /**
   *
   * @param species
   * @return
   * @throws XMLStreamException
   */
  protected Species readSpecies(PluginSpecies species) throws XMLStreamException {
    Species s = new Species(level, version);
    PluginUtils.transferNamedSBaseProperties(species, s);
    int sbo = SBO.convertAlias2SBO(species.getSpeciesAlias(0).getType());
    PluginSpeciesAlias alias = species.getSpeciesAlias(0);
    String type = alias.getType();
    if (alias.getType().equals(PluginSpeciesSymbolType.PROTEIN)) {
      type = alias.getProtein().getType();
    }
    sbo = SBO.convertAlias2SBO(type);
    if (SBO.checkTerm(sbo)) {
      s.setSBOTerm(sbo);
    }
    if (model.getLevel()<3) {
      s.setCharge(species.getCharge());
    }
    else
    {
      FBCSpeciesPlugin fbcSpecies = (FBCSpeciesPlugin)s.getPlugin(FBCConstants.getNamespaceURI(model.getLevel(),
        model.getVersion()));
      fbcSpecies.setCharge(species.getCharge());
    }
    if ((species.getCompartment() != null) && (species.getCompartment().length() > 0)) {
      s.setCompartment(model.getCompartment(species.getCompartment()));
    }
    s.setBoundaryCondition(species.getBoundaryCondition());
    s.setConstant(species.getConstant());
    s.setHasOnlySubstanceUnits(species.getHasOnlySubstanceUnits());
    if (species.isSetInitialAmount()) {
      s.setInitialAmount(species.getInitialAmount());
    } else if (species.isSetInitialConcentration()) {
      s.setInitialConcentration(species.getInitialConcentration());
    }
    // before L2V3...
    if ((species.getSpatialSizeUnits() != null) && (species.getSpatialSizeUnits().length() > 0) && (model.getLevel() < 3)) {
      s.setSpatialSizeUnits(species.getSpatialSizeUnits());
    }
    if (species.getSubstanceUnits().length() > 0) {
      s.setSubstanceUnits(species.getSubstanceUnits());
    }
    if (species.getSpeciesType().length() > 0 && (model.getLevel() < 3)) {
      s.setSpeciesType(model.getSpeciesType(species.getSpeciesType()));
    }
    if (model.getLevel()>2) {
      s.setConstant(false);
      s.setCompartment(model.getCompartment(0));
    }
    return s;
  }

  /**
   *
   * @param speciesReference
   * @return
   * @throws XMLStreamException
   */
  private SpeciesReference readSpeciesReference(PluginSpeciesReference speciesReference) throws XMLStreamException {
    logger.debug("Reading speciesReference " + speciesReference.getSpecies());
    SpeciesReference spec = new SpeciesReference(level, version);
    PluginUtils.transferNamedSBaseProperties(speciesReference, spec);
    logger.debug("NamedSBase properties done");
    if ((speciesReference.getStoichiometryMath() != null) && (model.getLevel() < 3)) {
      spec.setStoichiometryMath(LibSBMLReader.readStoichiometricMath(speciesReference.getStoichiometryMath()));
      logger.debug("Reading stoichiometric math done");
    } else {
      spec.setStoichiometry(speciesReference.getStoichiometry());
      logger.debug("Reading stoichiometry done");
    }
    if (speciesReference.getReferenceType().length() > 0) {
      int sbo = SBO.convertAlias2SBO(speciesReference.getReferenceType());
      logger.debug("Converted reference type to SBO");
      if (SBO.checkTerm(sbo)) {
        spec.setSBOTerm(sbo);
      }
    }
    if (spec.getLevel() > 2) {
      spec.setConstant(true);
    }
    spec.setSpecies(speciesReference.getSpecies());
    return spec;
  }

  /**
   *
   * @param speciesType
   * @return
   * @throws XMLStreamException
   */
  protected SpeciesType readSpeciesType(PluginSpeciesType speciesType) throws XMLStreamException {
    SpeciesType st = new SpeciesType(level, version);
    PluginUtils.transferNamedSBaseProperties(speciesType, st);
    return st;
  }

  /**
   *
   * @param unit
   * @return
   * @throws XMLStreamException
   */
  private Unit readUnit(PluginUnit unit) throws XMLStreamException {
    Unit u = new Unit(level, version);
    PluginUtils.transferSBaseProperties(unit, u);
    u.setKind(LibSBMLUtils.convertUnitKind(unit.getKind()));
    u.setExponent((double) unit.getExponent());
    u.setMultiplier(unit.getMultiplier());
    u.setScale(unit.getScale());
    if (u.isSetOffset() && (model.getLevel() < 3)) {
      u.setOffset(unit.getOffset());
    }
    return u;
  }

  /**
   *
   * @param unitDefinition
   * @return
   * @throws XMLStreamException
   */
  protected UnitDefinition readUnitDefinition(PluginUnitDefinition unitDefinition) throws XMLStreamException {
    UnitDefinition ud = new UnitDefinition(level, version);
    PluginUtils.transferNamedSBaseProperties(unitDefinition, ud);
    for (int i = 0; i < unitDefinition.getNumUnits(); i++) {
      ud.addUnit(readUnit(unitDefinition.getUnit(i)));
    }
    return ud;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBMLInputConverter#setListener(org.sbml.jsbml.util.ProgressListener)
   */
  @Override
  public void setListener(ProgressListener listener) {
    this.listener = listener;
  }

  /**
   * @param possibleEnzymes the possibleEnzymes to set
   */
  public void setPossibleEnzymes(Set<Integer> possibleEnzymes) {
    this.possibleEnzymes = possibleEnzymes;
  }

}
