package org.sbml.jsbml.ext.comp.test;

import org.junit.Assert;
import org.junit.Test;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.ext.comp.util.CompFlatteningConverter;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Logger;

public class CompFlattenTest {

    private final static Logger LOGGER = Logger.getLogger(CompFlatteningConverter.class.getName());

    @Test
    public void testAllData() {

        ClassLoader cl = this.getClass().getClassLoader();

        for (int i = 1; i < 62; i++) {

            URL urlFile = cl.getResource("testFlattening/" + "test" + i + ".xml");
            URL urlExpected = cl.getResource("testFlattening/" + "test" + i + "_flat.xml");

            assert urlFile != null;
            assert urlExpected != null;

            runTestOnFiles(urlFile, urlExpected, String.valueOf(i));

        }
    }

    @Test
    public void testSpecificFile() {

        int i = 6;
        ClassLoader cl = this.getClass().getClassLoader();


        URL urlFile = cl.getResource("testFlattening/" + "test" + i + ".xml");
        URL urlExpected = cl.getResource("testFlattening/" + "test" + i + "_flat.xml");

        assert urlFile != null;
        assert urlExpected != null;

        runTestOnFiles(urlFile, urlExpected, String.valueOf(i));

    }

    private void runTestOnFiles(URL urlFile, URL urlExpected, String name) {

        try {

            File file = new File(urlFile.toURI());
            File expectedFile = new File(urlExpected.toURI());

            SBMLReader reader = new SBMLReader();
            SBMLDocument document = reader.readSBML(file);
            SBMLDocument expectedDocument = reader.readSBML(expectedFile);

            CompFlatteningConverter compFlatteningConverter = new CompFlatteningConverter();
            SBMLDocument flattenedDocument = compFlatteningConverter.flatten(document);


            LOGGER.info("Testing Model " + name + ": ");

            SBMLWriter.write(flattenedDocument, System.out, ' ', (short) 2);
            System.out.println("\n-------");
            Assert.assertTrue("Success Testing Model", expectedDocument.equals(flattenedDocument));

        } catch (XMLStreamException | IOException e) {
            LOGGER.warning("Failed testing Model " + name + ": ");
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

}
