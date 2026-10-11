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
package org.sbml.jsbml.ext.distrib.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.Property;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.ext.distrib.DistribConstants;
import org.sbml.jsbml.ext.distrib.DistribSBasePlugin;
import org.sbml.jsbml.ext.distrib.Uncertainty;

/**
 * The ids of the distrib elements (DistribBase) are not in the SId namespace of
 * the model: they are not registered in the model, and reading or changing them
 * logs no warning.
 */
public class DistribIdTest {

  /** Collects the messages of the WARN and higher events of the {@link Model} logger. */
  private static final class ListAppender extends AbstractAppender {

    private final List<String> messages = new ArrayList<String>();

    ListAppender() {
      super("DistribIdTest", null, null, true, Property.EMPTY_ARRAY);
    }

    @Override
    public void append(LogEvent event) {
      if (event.getLevel().isMoreSpecificThan(Level.WARN)) {
        messages.add(event.getMessage().getFormattedMessage());
      }
    }
  }

  private ListAppender appender;

  @Before
  public void addAppender() {
    appender = new ListAppender();
    appender.start();
    Configurator.setLevel(Model.class.getName(), Level.WARN);
    LoggerContext context = (LoggerContext) LogManager.getContext(false);
    Configuration configuration = context.getConfiguration();
    configuration.getLoggerConfig(Model.class.getName()).addAppender(appender, Level.WARN, null);
    context.updateLoggers();
  }

  @After
  public void removeAppender() {
    LoggerContext context = (LoggerContext) LogManager.getContext(false);
    context.getConfiguration().getLoggerConfig(Model.class.getName()).removeAppender(appender.getName());
    context.updateLoggers();
    appender.stop();
  }

  private static SBMLDocument read() throws Exception {
    return SBMLReader.read(DistribIdTest.class.getResourceAsStream("data/distrib_uncertainties.xml"));
  }

  @Test
  public void readingUncertaintiesWithIdsLogsNoWarning() throws Exception {
    SBMLDocument document = read();

    DistribSBasePlugin plugin = (DistribSBasePlugin) document.getModel().getSpecies("S1").getExtension(DistribConstants.shortLabel);
    assertEquals("u_S1_a", plugin.getUncertainty(0).getId());
    assertEquals(new ArrayList<String>(), appender.messages);
  }

  @Test
  public void uncertaintyIdIsNotInTheSIdNamespace() throws Exception {
    Model model = read().getModel();
    DistribSBasePlugin plugin = (DistribSBasePlugin) model.getParameter("k1").getExtension(DistribConstants.shortLabel);

    // an uncertainty may have the id of a model element, and the model keeps the element
    Uncertainty uncertainty = plugin.createUncertainty();
    uncertainty.setId("sd_k1");
    Parameter parameter = model.createParameter("u_S1_a");

    assertSame(model.getParameter("sd_k1"), model.getSBaseById("sd_k1"));
    assertSame(parameter, model.getSBaseById("u_S1_a"));
    assertTrue(plugin.removeUncertainty(uncertainty));
    assertEquals(new ArrayList<String>(), appender.messages);
  }
}
