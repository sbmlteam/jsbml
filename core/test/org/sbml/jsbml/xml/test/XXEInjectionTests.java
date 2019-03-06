/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2019 jointly by the following organizations:
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
package org.sbml.jsbml.xml.test;

import com.ctc.wstx.exc.WstxParsingException;
import org.junit.Test;
import org.sbml.jsbml.SBMLReader;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * Tests for <a href="https://www.owasp.org/index.php/XML_External_Entity_(XXE)_Processing">XML External Entity Injection</a> attacks.
 *
 * @author <a href="mailto:mglont@ebi.ac.uk">Mihai Glont</a>
 * @since 1.5
 */
public class XXEInjectionTests {
    @Test()
    public void readingModelsContainingExternalEntitiesThrowsAnException() {
        final String modelPath = "/org/sbml/jsbml/xml/test/data/l2v1/BIOMD0000000025-xxe.xml";
        InputStream modelStream = getClass().getResourceAsStream(modelPath);

        try {
            // this should throw a WstxParsingException
            new SBMLReader().readSBMLFromStream(modelStream);
            fail("Document containing XXE injection did not throw a parsing exception -- possible vulnerability detected!");
        } catch (XMLStreamException expected) {
            if (!(expected instanceof WstxParsingException)) {
                String err = String.format("Could not parse SBML document %s: %s", modelPath, expected.toString());
                fail(err);
            }
            String expectedMsg = String.format("Undeclared general entity \"xxe\"%n at [row,col {unknown-source}]: [5,10]");
            assertEquals("The location of the external entity in the file is correctly reported",
                    expectedMsg, expected.getMessage());
        } finally {
            if (null != modelStream) {
                try {
                    modelStream.close();
                } catch (IOException e) {
                    String err = String.format("Could not close stream %s: %s", modelPath, e.toString());
                    fail(err);
                }
            }
        }
    }
}
