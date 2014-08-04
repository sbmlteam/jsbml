/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2014  jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * 6. Marquette University, Milwaukee, WI, USA
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.celldesigner;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import jp.sbi.celldesigner.plugin.PluginCompartment;
import jp.sbi.celldesigner.plugin.PluginModel;
import jp.sbi.celldesigner.plugin.PluginReaction;
import jp.sbi.celldesigner.plugin.PluginSBase;
import jp.sbi.celldesigner.plugin.PluginSpecies;
import jp.sbi.celldesigner.plugin.PluginSpeciesAlias;

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.LayoutModelPlugin;


/**
 * @author Ibrahim Vazirabad
 * @version $Rev$
 * @since 1.0
 * @date Aug 3, 2014
 */
public class PluginSBaseEventUtils {

  public static void pluginCompartmentAdded(PluginSBMLReader reader, PluginCompartment pCompartment,
    Map<PluginSBase, Set<SBase>> map, SBMLDocument document)
  {
    Layout layout = ((LayoutModelPlugin)document.getModel().getExtension("layout")).getLayout(0);
    Compartment newCompartment = null;
    try {
      newCompartment = reader.readCompartment(pCompartment);
      document.getModel().addCompartment(newCompartment);

      Set<SBase> sBaseSet= LayoutConverter.extractLayout(pCompartment, layout);
      sBaseSet.add(newCompartment);
      map.put(pCompartment, sBaseSet);

      PluginUtils.transferNamedSBaseProperties(pCompartment, newCompartment);

    } catch (Throwable e) {
      new GUIErrorConsole(e);
    }
  }

  public static void pluginSpeciesAdded(PluginSBMLReader reader, PluginSpecies pSpecies,
    Map<PluginSBase, Set<SBase>> map, SBMLDocument document)
  {
    Model model = document.getModel();
    Species newSpecies = null;
    try {
      newSpecies = reader.readSpecies(pSpecies);
      model.addSpecies(newSpecies);
      PluginUtils.transferNamedSBaseProperties(pSpecies, newSpecies);

      Set<SBase> sBaseSet = new HashSet<SBase>();
      sBaseSet.add(newSpecies);
      map.put(pSpecies, sBaseSet);

    } catch (Throwable e) {
      new GUIErrorConsole(e);
    }
  }

  public static void pluginSpeciesAliasAdded(PluginSpeciesAlias pSpeciesAlias, Map<PluginSBase, Set<SBase>> map,
    SBMLDocument document)
  {
    Layout layout = ((LayoutModelPlugin)document.getModel().getExtension("layout")).getLayout(0);
    try {
      Set<SBase> sBaseSet = LayoutConverter.extractLayout(pSpeciesAlias, layout);
      map.put(pSpeciesAlias, sBaseSet);
    }
    catch (Throwable e) {
      new GUIErrorConsole(e);
    }
  }

  public static void pluginReactionAdded(PluginSBMLReader reader, PluginReaction pReaction,
    Map<PluginSBase, Set<SBase>> map, SBMLDocument document)
  {
    Model model = document.getModel();
    Layout layout = ((LayoutModelPlugin)document.getModel().getExtension("layout")).getLayout(0);
    Reaction newReaction = null;
    try {
      newReaction = reader.readReaction(pReaction);
      model.addReaction(newReaction);
      Set<SBase> sBaseSet = LayoutConverter.extractLayout(pReaction, layout);
      sBaseSet.add(newReaction);

      PluginUtils.transferNamedSBaseProperties(pReaction, newReaction);

      map.put(pReaction, sBaseSet);
    } catch (Throwable e) {
      new GUIErrorConsole(e);
    }
  }

  public static void pluginCompartmentChanged(PluginSBMLReader reader, PluginCompartment pCompartment,
    Map<PluginSBase, Set<SBase>> map, SBMLDocument document)
  {
    Model model = document.getModel();
    Layout layout = ((LayoutModelPlugin)document.getModel().getExtension("layout")).getLayout(0);
    try {
      Compartment newCompartment = null;
      newCompartment = reader.readCompartment(pCompartment);
      model.removeCompartment(newCompartment.getId());
      model.addCompartment(newCompartment);

      layout.removeCompartmentGlyph("cGlyph_" + pCompartment.getId());
      layout.removeTextGlyph("tGlyph_" + pCompartment.getId());
      Set<SBase> sBaseSet = LayoutConverter.extractLayout(pCompartment, layout);

      PluginUtils.transferNamedSBaseProperties(pCompartment, newCompartment);

      sBaseSet.add(newCompartment);

      if (map.isEmpty()) {
        map.put(pCompartment, sBaseSet);
      } else {
        for (PluginSBase pluginSBase: map.keySet())
        {
          if (pluginSBase instanceof PluginCompartment)
          {
            PluginCompartment compartment = (PluginCompartment)pluginSBase;
            if (compartment.getId().equals(pCompartment.getId())) {
              map.put(pluginSBase, sBaseSet);
            }
          }
        }
      }
    }
    catch (Throwable e) {
      new GUIErrorConsole(e);
    }
  }

  public static void pluginSpeciesChangedOrDeleted(PluginSBMLReader reader, PluginModel pModel,
    Map<PluginSBase, Set<SBase>> map, SBMLDocument document)
  {
    try {
      Model model = document.getModel();
      while (model.getSpeciesCount() != 0)
      {
        model.removeSpecies(0);
      }

      Iterator<PluginSBase> it = map.keySet().iterator();
      while (it.hasNext())
      {
        PluginSBase pSBase = it.next();
        if (pSBase instanceof PluginSpecies)
        {
          it.remove();
        }
      }

      for (int i = 0; i<pModel.getNumSpecies(); i++)
      {
        Species species = reader.readSpecies(pModel.getSpecies(i));
        model.addSpecies(species);
        Set<SBase> listOfSBases = new HashSet<SBase>();
        listOfSBases.add(species);
        map.put(pModel.getSpecies(i), listOfSBases);
      }
    }
    catch (Throwable e) {
      new GUIErrorConsole(e);
    }
  }

  public static void pluginSpeciesAliasChangedOrDeleted(PluginSBMLReader reader, PluginModel pModel,
    Map<PluginSBase, Set<SBase>> map, SBMLDocument document, PluginSpeciesAlias pSpeciesAlias)
  {
    try {
      Layout layout = ((LayoutModelPlugin)document.getModel().getExtension("layout")).getLayout(0);
      for (int i = 0; i<pModel.getListOfAllSpeciesAlias().size(); i++)
      {
        layout.removeTextGlyph("tGlyph_" + pSpeciesAlias.getAliasID());
        layout.removeTextGlyph("tGlyph_" + ((PluginSpeciesAlias)pModel.getListOfAllSpeciesAlias().get(i)).getAliasID());
      }
      while (layout.getSpeciesGlyphCount()!=0)
      {
        layout.removeSpeciesGlyph(0);
      }

      Iterator<PluginSBase> it = map.keySet().iterator();
      while (it.hasNext())
      {
        PluginSBase pSBase = it.next();
        if (pSBase instanceof PluginSpeciesAlias)
        {
          it.remove();
        }
      }

      for (int i = 0; i<pModel.getListOfAllSpeciesAlias().size(); i++)
      {
        Set<SBase> listOfSBases = LayoutConverter.extractLayout((PluginSpeciesAlias)
          pModel.getListOfAllSpeciesAlias().get(i), layout);
        map.put(pModel.getListOfAllSpeciesAlias().get(i), listOfSBases);
      }
    }
    catch (Throwable e) {
      new GUIErrorConsole(e);
    }
  }

  public static void pluginReactionChangedOrDeleted(PluginSBMLReader reader, PluginModel pModel,
    Map<PluginSBase, Set<SBase>> map, SBMLDocument document)
  {
    try {
      Layout layout = ((LayoutModelPlugin)document.getModel().getExtension("layout")).getLayout(0);

      Model model = document.getModel();
      for (int i = 0; i<pModel.getNumReactions(); i++)
      {
        layout.removeTextGlyph("tGlyph_" + pModel.getReaction(i).getId());
      }

      while (model.getReactionCount() != 0)
      {
        model.removeReaction(0);
      }

      while (layout.getReactionGlyphCount()!=0)
      {
        layout.removeReactionGlyph(0);
      }
      Iterator<PluginSBase> it = map.keySet().iterator();
      while (it.hasNext())
      {
        PluginSBase pSBase = it.next();
        if (pSBase instanceof PluginReaction)
        {
          it.remove();
        }
      }

      for (int i = 0; i<pModel.getNumReactions(); i++)
      {
        Reaction reaction = reader.readReaction(pModel.getReaction(i));
        model.addReaction(reaction);
        Set<SBase> listOfSBases = LayoutConverter.extractLayout(pModel.getReaction(i), layout);
        map.put(pModel.getReaction(i), listOfSBases);
      }
    }
    catch (Throwable e) {
      new GUIErrorConsole(e);
    }
  }

  public static void pluginCompartmentDeleted(PluginSBMLReader reader, PluginCompartment pCompartment,
    Map<PluginSBase, Set<SBase>> map, SBMLDocument document)
  {
    Model model = document.getModel();
    Layout layout = ((LayoutModelPlugin)document.getModel().getExtension("layout")).getLayout(0);

    layout.removeCompartmentGlyph("cGlyph_" + pCompartment.getId());
    layout.removeTextGlyph("tGlyph_" + pCompartment.getId());

    model.removeCompartment(pCompartment.getId());

    {
      for (PluginSBase pluginSBase: map.keySet())
      {
        PluginCompartment compartment = (PluginCompartment)pluginSBase;
        if (compartment.getId().equals(pCompartment.getId())) {
          map.remove(pluginSBase);
        }
      }
    }
  }
}
