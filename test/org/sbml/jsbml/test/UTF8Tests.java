/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2016 jointly by the following organizations:
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

import java.io.File;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.TidySBMLWriter;


public class UTF8Tests {

  /**
   * 
   */
  private SBMLDocument doc;
  /**
   * 
   */
  private Model m;
  
  private final static String chineseUTF8 = "份非常简";
  
  private final static String cyrilicUTF8 = "name utf-8: ¥¢®¶ÅÑëЉЍ ϓ Ϡ Ж Ѣ Ѩ  о҉"; //  𐍈 does not seems to be supported properly

  private String utf8Model = "<?xml version='1.0' encoding='UTF-8' standalone='no'?>\n"
      + "<sbml xmlns=\"http://www.sbml.org/sbml/level3/version1/core\" level=\"3\" version=\"1\">\n"
      + "  <model id=\"model\" name=\"" + cyrilicUTF8 + "\">\n"
      + "<notes>\n<p xmlns=\"http://www.w3.org/1999/xhtml\">\n<pre>"
      + "U+00A2  ¢ c2 a2 CENT SIGN\n"
      + "U+00A3  £ c2 a3 POUND SIGN\n"
      + "U+00A4  ¤ c2 a4 CURRENCY SIGN\n"
      + "U+00A5  ¥ c2 a5 YEN SIGN\n"
      + "U+00A6  ¦ c2 a6 BROKEN BAR\n"
      + "U+00A7  § c2 a7 SECTION SIGN\n"
      + "U+00A9  © c2 a9 COPYRIGHT SIGN\n"
      + "U+00AA  ª c2 aa FEMININE ORDINAL INDICATOR\n"
      + "U+00AB  « c2 ab LEFT-POINTING DOUBLE ANGLE QUOTATION MARK\n"
      + "U+00AC  ¬ c2 ac NOT SIGN\n"
      + "U+00AE  ® c2 ae REGISTERED SIGN\n"
      + "U+00B0  ° c2 b0 DEGREE SIGN\n"
      + "U+00B1  ± c2 b1 PLUS-MINUS SIGN\n"
      + "U+00B2  ² c2 b2 SUPERSCRIPT TWO\n"
      + "U+00B3  ³ c2 b3 SUPERSCRIPT THREE\n"
      + "U+00B5  µ c2 b5 MICRO SIGN\n"
      + "U+00B6  ¶ c2 b6 PILCROW SIGN\n"
      + "U+00B9  ¹ c2 b9 SUPERSCRIPT ONE\n"
      + "U+00BA  º c2 ba MASCULINE ORDINAL INDICATOR\n"
      + "U+00BB  » c2 bb RIGHT-POINTING DOUBLE ANGLE QUOTATION MARK\n"
      + "U+00BC  ¼ c2 bc VULGAR FRACTION ONE QUARTER\n"
      + "U+00BD  ½ c2 bd VULGAR FRACTION ONE HALF\n"
      + "U+00BE  ¾ c2 be VULGAR FRACTION THREE QUARTERS\n"
      + "U+00BF  ¿ c2 bf INVERTED QUESTION MARK\n"
      + "U+00C0  À c3 80 LATIN CAPITAL LETTER A WITH GRAVE\n"
      + "U+00C1  Á c3 81 LATIN CAPITAL LETTER A WITH ACUTE\n"
      + "U+00C2  Â c3 82 LATIN CAPITAL LETTER A WITH CIRCUMFLEX\n"
      + "U+00C3  Ã c3 83 LATIN CAPITAL LETTER A WITH TILDE\n"
      + "U+00C4  Ä c3 84 LATIN CAPITAL LETTER A WITH DIAERESIS\n"
      + "U+00C5  Å c3 85 LATIN CAPITAL LETTER A WITH RING ABOVE\n"
      + "U+00C6  Æ c3 86 LATIN CAPITAL LETTER AE\n"
      + "U+00C7  Ç c3 87 LATIN CAPITAL LETTER C WITH CEDILLA\n"
      + "U+00C8  È c3 88 LATIN CAPITAL LETTER E WITH GRAVE\n"
      + "U+00C9  É c3 89 LATIN CAPITAL LETTER E WITH ACUTE\n"
      + "U+00CA  Ê c3 8a LATIN CAPITAL LETTER E WITH CIRCUMFLEX\n"
      + "U+00CB  Ë c3 8b LATIN CAPITAL LETTER E WITH DIAERESIS\n"
      + "U+00CC  Ì c3 8c LATIN CAPITAL LETTER I WITH GRAVE\n"
      + "U+00CD  Í c3 8d LATIN CAPITAL LETTER I WITH ACUTE\n"
      + "U+00CE  Î c3 8e LATIN CAPITAL LETTER I WITH CIRCUMFLEX\n"
      + "U+00CF  Ï c3 8f LATIN CAPITAL LETTER I WITH DIAERESIS\n"
      + "U+00D0  Ð c3 90 LATIN CAPITAL LETTER ETH\n"
      + "U+00D1  Ñ c3 91 LATIN CAPITAL LETTER N WITH TILDE\n"
      + "U+00D2  Ò c3 92 LATIN CAPITAL LETTER O WITH GRAVE\n"
      + "U+00D3  Ó c3 93 LATIN CAPITAL LETTER O WITH ACUTE\n"
      + "U+00D4  Ô c3 94 LATIN CAPITAL LETTER O WITH CIRCUMFLEX\n"
      + "U+00D5  Õ c3 95 LATIN CAPITAL LETTER O WITH TILDE\n"
      + "U+00D6  Ö c3 96 LATIN CAPITAL LETTER O WITH DIAERESIS\n"
      + "U+00D7  × c3 97 MULTIPLICATION SIGN\n"
      + "U+00D8  Ø c3 98 LATIN CAPITAL LETTER O WITH STROKE\n"
      + "U+00D9  Ù c3 99 LATIN CAPITAL LETTER U WITH GRAVE\n"
      + "U+00DA  Ú c3 9a LATIN CAPITAL LETTER U WITH ACUTE\n"
      + "U+00DB  Û c3 9b LATIN CAPITAL LETTER U WITH CIRCUMFLEX\n"
      + "U+00DC  Ü c3 9c LATIN CAPITAL LETTER U WITH DIAERESIS\n"
      + "U+00DD  Ý c3 9d LATIN CAPITAL LETTER Y WITH ACUTE\n"
      + "U+00DE  Þ c3 9e LATIN CAPITAL LETTER THORN\n"
      + "U+00DF  ß c3 9f LATIN SMALL LETTER SHARP S\n"
      + "U+00E0  à c3 a0 LATIN SMALL LETTER A WITH GRAVE\n"
      + "U+00E1  á c3 a1 LATIN SMALL LETTER A WITH ACUTE\n"
      + "U+00E2  â c3 a2 LATIN SMALL LETTER A WITH CIRCUMFLEX\n"
      + "U+00E3  ã c3 a3 LATIN SMALL LETTER A WITH TILDE\n"
      + "U+00E4  ä c3 a4 LATIN SMALL LETTER A WITH DIAERESIS\n"
      + "U+00E5  å c3 a5 LATIN SMALL LETTER A WITH RING ABOVE\n"
      + "U+00E6  æ c3 a6 LATIN SMALL LETTER AE\n"
      + "U+00E7  ç c3 a7 LATIN SMALL LETTER C WITH CEDILLA\n"
      + "U+00E8  è c3 a8 LATIN SMALL LETTER E WITH GRAVE\n"
      + "U+00E9  é c3 a9 LATIN SMALL LETTER E WITH ACUTE\n"
      + "U+00EA  ê c3 aa LATIN SMALL LETTER E WITH CIRCUMFLEX\n"
      + "U+00EB  ë c3 ab LATIN SMALL LETTER E WITH DIAERESIS\n"
      + "U+00EC  ì c3 ac LATIN SMALL LETTER I WITH GRAVE\n"
      + "U+00ED  í c3 ad LATIN SMALL LETTER I WITH ACUTE\n"
      + "U+00EE  î c3 ae LATIN SMALL LETTER I WITH CIRCUMFLEX\n"
      + "U+00EF  ï c3 af LATIN SMALL LETTER I WITH DIAERESIS\n"
      + "U+00F0  ð c3 b0 LATIN SMALL LETTER ETH\n"
      + "U+00F1  ñ c3 b1 LATIN SMALL LETTER N WITH TILDE\n"
      + "U+00F2  ò c3 b2 LATIN SMALL LETTER O WITH GRAVE\n"
      + "U+00F3  ó c3 b3 LATIN SMALL LETTER O WITH ACUTE\n"
      + "U+00F4  ô c3 b4 LATIN SMALL LETTER O WITH CIRCUMFLEX\n"
      + "U+00F5  õ c3 b5 LATIN SMALL LETTER O WITH TILDE\n"
      + "U+00F6  ö c3 b6 LATIN SMALL LETTER O WITH DIAERESIS\n"
      + "U+00F7  ÷ c3 b7 DIVISION SIGN\n"
      + "U+00F8  ø c3 b8 LATIN SMALL LETTER O WITH STROKE\n"
      + "U+00F9  ù c3 b9 LATIN SMALL LETTER U WITH GRAVE\n"
      + "U+00FA  ú c3 ba LATIN SMALL LETTER U WITH ACUTE\n"
      + "U+00FB  û c3 bb LATIN SMALL LETTER U WITH CIRCUMFLEX\n"
      + "U+00FC  ü c3 bc LATIN SMALL LETTER U WITH DIAERESIS\n"
      + "U+00FD  ý c3 bd LATIN SMALL LETTER Y WITH ACUTE\n"
      + "U+00FE  þ c3 be LATIN SMALL LETTER THORN\n"
      + "U+00FF  ÿ c3 bf LATIN SMALL LETTER Y WITH DIAERESIS\n"
      + "这是一份非常简单的说明书…"
      + "</pre></p></notes>\n"
      + "</model>"
      + "</sbml>";

  /**
   * 
   */
  @BeforeClass public static void initialSetUp() {}


  /**
   * 
   */
  @Before public void setUp() {
    doc = new SBMLDocument(3, 1);

    m = doc.createModel("test");
    m.setName(cyrilicUTF8);
    try {
      m.appendNotes("<p xmlns=\"http://www.w3.org/1999/xhtml\">" + chineseUTF8 + " ÆÈÖßæ</p>");
    } catch (XMLStreamException e) {
      e.printStackTrace();
      Assert.fail();
    }
    
    m.createCompartment("cell");
    m.createSpecies("S1");
    m.createSpecies("S2");


  }


  /**
   * Checks that the reading/writing from a String keep properly the UTF-8 characters.
   */
  @Test public void testModelString() {
    try {
      SBMLDocument d = new SBMLReader().readSBMLFromString(utf8Model);

      String docStr = new SBMLWriter().writeSBMLToString(d);
      
      Assert.assertTrue(cyrilicUTF8.equals(d.getModel().getName()));      
      Assert.assertTrue(docStr.contains(chineseUTF8));
      Assert.assertTrue(docStr.contains(cyrilicUTF8));
      Assert.assertTrue(docStr.contains("æ"));
      Assert.assertTrue(docStr.contains("Ø"));
      Assert.assertTrue(docStr.contains("÷"));
      Assert.assertTrue(docStr.contains("©"));
      
      d = new SBMLReader().readSBMLFromString(utf8Model);
      String tidyDocStr = new TidySBMLWriter().writeSBMLToString(d);

      Assert.assertTrue(cyrilicUTF8.equals(d.getModel().getName()));      
      Assert.assertTrue(tidyDocStr.contains(chineseUTF8));
      Assert.assertTrue(tidyDocStr.contains(cyrilicUTF8));
      Assert.assertTrue(tidyDocStr.contains("æ"));
      Assert.assertTrue(tidyDocStr.contains("Ø"));
      Assert.assertTrue(tidyDocStr.contains("÷"));
      Assert.assertTrue(tidyDocStr.contains("©"));

      d = new SBMLReader().readSBMLFromString(tidyDocStr);
      tidyDocStr = new TidySBMLWriter().writeSBMLToString(d);
      
      Assert.assertTrue(cyrilicUTF8.equals(d.getModel().getName()));      
      Assert.assertTrue(tidyDocStr.contains(chineseUTF8));
      Assert.assertTrue(tidyDocStr.contains(cyrilicUTF8));
      Assert.assertTrue(tidyDocStr.contains("æ"));
      Assert.assertTrue(tidyDocStr.contains("Ø"));
      Assert.assertTrue(tidyDocStr.contains("÷"));
      Assert.assertTrue(tidyDocStr.contains("©"));
      
    } catch (XMLStreamException e) {
      e.printStackTrace();
      Assert.fail();
    }
  }
  
  /**
   * Checks that the writing to a file keep properly the UTF-8 characters.
   */
  @Test public void testModelFile() {

    try {
      File tmpFile = File.createTempFile("utf8Tests-", ".xml");
      File tmpFile2 = File.createTempFile("utf8Tests-", ".xml");
      
      new SBMLWriter().writeSBML(doc, tmpFile);

      SBMLDocument d = new SBMLReader().readSBML(tmpFile);
      
      String docStr = new SBMLWriter().writeSBMLToString(d);

      Assert.assertTrue(docStr.contains(chineseUTF8));
      Assert.assertTrue(docStr.contains(cyrilicUTF8));
      Assert.assertTrue(docStr.contains("ÆÈÖßæ"));

      new TidySBMLWriter().writeSBMLToFile(d, tmpFile2.getAbsolutePath());
      d = new SBMLReader().readSBML(tmpFile2);
      String tidyDocStr = new SBMLWriter().writeSBMLToString(d);
      
      Assert.assertTrue(cyrilicUTF8.equals(d.getModel().getName()));      
      Assert.assertTrue(tidyDocStr.contains(chineseUTF8));
      Assert.assertTrue(tidyDocStr.contains(cyrilicUTF8));
      Assert.assertTrue(tidyDocStr.contains("ÆÈÖßæ"));
      
    } catch (IOException e) {
      e.printStackTrace();
      Assert.fail();
    } catch (SBMLException e) {
      e.printStackTrace();
      Assert.fail();
    } catch (XMLStreamException e) {
      e.printStackTrace();
      Assert.fail();
    }
    
  }
}
