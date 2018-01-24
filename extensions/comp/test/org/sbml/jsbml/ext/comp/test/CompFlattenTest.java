package org.sbml.jsbml.ext.comp.test;

import org.junit.Test;
import org.junit.Assert;

import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.ext.comp.util.CompFlatteningConverter;

import javax.swing.tree.TreeNode;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;

public class CompFlattenTest {


    @Test
    public void testFlattening() {
        for (int i = 1; i < 62; i++) {
            System.out.println("Testing Model " + i + ": ");

            // TODO: test all the files in the folder
            File file = new File("/Users/Christoph/Documents/Studium Bioinformatik/08 WiSe 17 18/HiWi/Code/jsbml/extensions/comp/test/org/sbml/jsbml/ext/comp/test/testdataforflattening/test" + i + ".xml");
            File expectedFile = new File("/Users/Christoph/Documents/Studium Bioinformatik/08 WiSe 17 18/HiWi/Code/jsbml/extensions/comp/test/org/sbml/jsbml/ext/comp/test/testdataforflattening/test" + i + "_flat.xml");

            SBMLReader reader = new SBMLReader();

            try {
                SBMLDocument expectedDocument = reader.readSBML(expectedFile);
                SBMLDocument document = reader.readSBML(file);

                CompFlatteningConverter compFlatteningConverter = new CompFlatteningConverter();
                SBMLDocument flattendDocument = compFlatteningConverter.flatten(document);

//                SBMLWriter sbmlWriter = new SBMLWriter();
//
//                boolean test = expectedDocument.getModel().equals(flattendSBML.getModel());
//                boolean test2 = expectedDocument.equals(flattendSBML);
//                boolean test3 = expectedDocument.getSBMLDocumentAttributes().equals(flattendSBML.getSBMLDocumentAttributes());
              //  Assert.assertTrue(expectedDocument.getSBMLDocumentAttributes().equals(flattendSBML.getSBMLDocumentAttributes()));
                //Assert.assertEquals(expectedDocument.getSBMLDocumentAttributes(), flattendSBML.getSBMLDocumentAttributes());
                //Assert.assertSame(expectedDocument, flattendSBML);
                //Assert.assertEquals(expectedDocument, flattendSBML);

                // TODO: this way all the tests should work. change back to flattend
                Assert.assertTrue(testSBMLDocuments(expectedDocument, flattendDocument));
            } catch (XMLStreamException | IOException e) {
                e.printStackTrace();
            }

        }
    }

    private boolean testSBMLDocuments(TreeNode expectedTreeNode, TreeNode flattenedTreeNode){

        int expectedCount = expectedTreeNode.getChildCount();
        int flattenedCount = flattenedTreeNode.getChildCount();

        if(expectedCount == flattenedCount){
            for (int i = 0; i < expectedTreeNode.getChildCount(); i++) {

                expectedTreeNode = expectedTreeNode.getChildAt(i);
                flattenedTreeNode = flattenedTreeNode.getChildAt(i);

                if(expectedTreeNode.isLeaf() && flattenedTreeNode.isLeaf() && !expectedTreeNode.equals(flattenedTreeNode)){
                    return false;
                } else if (expectedTreeNode.equals(flattenedTreeNode) && !expectedTreeNode.isLeaf() && !flattenedTreeNode.isLeaf()){
                    testSBMLDocuments(expectedTreeNode, flattenedTreeNode);
                }
            }

        } else { // the nodes have unequal
            return false;
        }

        return true;

    }

}
