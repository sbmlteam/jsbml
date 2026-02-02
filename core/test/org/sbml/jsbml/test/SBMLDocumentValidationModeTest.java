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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.sbml.jsbml.SBMLDocument;

/**
 * Tests that {@link SBMLDocument#checkConsistency()} uses the offline
 * validator by default.
 */
public class SBMLDocumentValidationModeTest {

  /**
   * Small test subclass of {@link SBMLDocument} that records whether the
   * offline or online validation methods are called.
   */
  private static class TestDocument extends SBMLDocument {

    boolean offlineCalled = false;
    boolean onlineCalled = false;

    @Override
    public int checkConsistencyOffline() {
      offlineCalled = true;
      // return a distinctive value so we can verify it is propagated
      return 7;
    }

    @Override
    public int checkConsistencyOnline() {
      onlineCalled = true;
      return 13;
    }
  }

  @Test
  public void checkConsistencyUsesOfflineValidatorByDefault() {
    TestDocument doc = new TestDocument();

    int result = doc.checkConsistency();

    assertTrue("Offline validator should be used by default.", doc.offlineCalled);
    assertFalse("Online validator should not be used when calling checkConsistency().", doc.onlineCalled);
    assertEquals("checkConsistency() should return the offline validator result.", 7, result);
  }
}