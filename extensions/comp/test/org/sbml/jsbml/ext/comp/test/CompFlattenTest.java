package org.sbml.jsbml.ext.comp.test;

import org.junit.Test;
import org.junit.Assert;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.ext.comp.util.CompFlatteningConverter;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;

public class CompFlattenTest {


    @Test
    public void testFlattening(){
        File file = new File("org/sbml/jsbml/ext/comp/util/submodels_example.xml");
        File expectedFile = new File("org/sbml/jsbml/ext/comp/util/submodels_example_flattened.xml");

        SBMLReader reader = new SBMLReader();

        try {
            SBMLDocument expectedDocument = reader.readSBML(expectedFile);
            SBMLDocument document = reader.readSBML(file);

            CompFlatteningConverter compFlatteningConverter = new CompFlatteningConverter();
            SBMLDocument flattendSBML = compFlatteningConverter.flatten(document);

            Assert.assertEquals(expectedDocument, flattendSBML);

        } catch (XMLStreamException | IOException e) {
            e.printStackTrace();
        }
    }

}
