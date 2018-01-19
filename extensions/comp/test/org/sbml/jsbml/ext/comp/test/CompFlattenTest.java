package org.sbml.jsbml.ext.comp.test;

import org.junit.Test;
import org.junit.Assert;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.ext.comp.util.CompFlatteningConverter;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;

public class CompFlattenTest {


    @Test
    public void testFlattening() {
        for (int i = 1; i < 62; i++) {
            System.out.println("Testing Model " + i + ": ");
            File file = new File("/Users/Christoph/Documents/Studium Bioinformatik/08 WiSe 17 18/HiWi/Code/jsbml/extensions/comp/test/org/sbml/jsbml/ext/comp/test/testdataforflattening/test" + i + ".xml");
            File expectedFile = new File("/Users/Christoph/Documents/Studium Bioinformatik/08 WiSe 17 18/HiWi/Code/jsbml/extensions/comp/test/org/sbml/jsbml/ext/comp/test/testdataforflattening/test" + i + "_flat.xml");

            SBMLReader reader = new SBMLReader();

            try {
                SBMLDocument expectedDocument = reader.readSBML(expectedFile);
                SBMLDocument document = reader.readSBML(file);

                CompFlatteningConverter compFlatteningConverter = new CompFlatteningConverter();
                SBMLDocument flattendSBML = compFlatteningConverter.flatten(document);

                SBMLWriter sbmlWriter = new SBMLWriter();

                Assert.assertEquals(sbmlWriter.writeSBMLToString(expectedDocument).trim(), sbmlWriter.writeSBMLToString(flattendSBML).trim());

            } catch (XMLStreamException | IOException e) {
                e.printStackTrace();
            }

        }
    }

}
