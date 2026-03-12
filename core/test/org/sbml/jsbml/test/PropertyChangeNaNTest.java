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

package org.sbml.jsbml.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.util.SimpleTreeNodeChangeListener;
import org.sbml.jsbml.util.TreeNodeChangeEvent;

/**
 * Tests for property-change events when working with NaN values.
 * 
 * This is a regression test for PR #273:
 * when a double property is internally stored as NaN and is explicitly
 * set to NaN again, a property-change event should be fired.
 */
public class PropertyChangeNaNTest {

  private static class CountingListener extends SimpleTreeNodeChangeListener {
    int count = 0;
  
    public void propertyChange(TreeNodeChangeEvent evt) {
      count++;
    }
  }

  @Test
  public void firesPropertyChangeWhenUndefinedDoubleSetToNaN() {
    // Parameter value defaults to "undefined", which is internally represented as NaN.
    Parameter p = new Parameter(3, 1);

    CountingListener listener = new CountingListener();
    p.addTreeNodeChangeListener(listener);

    // Sanity check: value is NaN before we start.
    assertTrue(Double.isNaN(p.getValue()));

    // Explicitly set the value to NaN again.
    listener.count = 0;
    p.setValue(Double.NaN);

    // With the fix in AbstractTreeNode.firePropertyChange, this should now
    // be treated as a real property change and fire exactly one event.
    assertEquals(1, listener.count);
  }
}