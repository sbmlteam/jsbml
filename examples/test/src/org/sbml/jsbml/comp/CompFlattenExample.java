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

package org.sbml.jsbml.comp;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.ext.comp.util.CompFlatteningConverter;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;

/**
 * @author Christoph Blessing
 */
public class CompFlattenExample {

        public static void main(String[] args) throws IOException, XMLStreamException {

        File file = new File("examples/resources/org/sbml/jsbml/comp/submodels_example.xml");

        SBMLReader reader = new SBMLReader();
        SBMLDocument document = reader.readSBML(file);

        CompFlatteningConverter compFlatteningConverter = new CompFlatteningConverter();

        SBMLDocument flattendSBML = compFlatteningConverter.flatten(document);

        SBMLWriter.write(flattendSBML, System.out, ' ', (short) 2);


    }

}
