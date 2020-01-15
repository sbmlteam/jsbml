package org.sbml.jsbml.xml.test;

import org.junit.Test;
import org.sbml.jsbml.validator.SyntaxChecker;
import org.junit.Assert;

/**
 * A testclass for the validation of metaId characters. 
 * 
 * See XML 1.0 Specifications <a href="https://www.w3.org/TR/xml/#CharClasses">XML 1.0 Specification</a>
 * for further details to Character Classes. 
 * 
 * @author Onur &Ouml;zel		
 * @since 1.5
 */
public class MetaIdValidationTest {
  
  /*
   * Unicode characters used (from XML 1.0 character classes): 
   * u0300 and u0B47 are CombiningChar
   * u0E94 is a lao letter of type BaseChar
   * u0967 is a devanagari digit of type Digit 
   */
  
  @Test
  public void initMetaPatternsLevelVersionTest() {
    int level, version;

    //specifications L2V2 under 3.1.6
    //has to start with a letter or an underscore
    level = 2; 
    version = 1; 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("", level, version) == false); 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_s", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("s_", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_ss" + "\u0B47",level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_a" + "\u0300", level, version) == true); 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_a" + "\u0300:", level, version) == false);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("\u0E94" + "A" + "\u0967"  , level, version) == false);
    System.out.println("");
    
    level = 2; 
    version = 2; 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("", level, version) == false); 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_s", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("s_", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_ss" + "\u0B47",level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_a" + "\u0300", level, version) == true); 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_a" + "\u0300:", level, version) == false);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("\u0E94" + "A" + "\u0967"  , level, version) == false);
    System.out.println("");

    //L2V2 is default value so the tests should also work without providing level and version
    Assert.assertTrue(SyntaxChecker.isValidMetaId("") == false); 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_s") == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("s_") == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_ss" + "\u0B47") == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_a" + "\u0300") == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_a" + "\u0300:") == false);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("\u0E94" + "A" + "\u0967") == false);
    System.out.println("");

    //since L2V3 all level and versions have equivalent metaId specifications
    //additionally to L2V2 colons are also included and can be used anywhere in the meta Id
    level = 2; 
    version = 3; 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("", level, version) == false);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_", level, version) == true); 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_s", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("s", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_ss" + "\u0B47", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_a" + "\u0300", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_a" + "\u0300:", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId(":_a" + "\u0300:", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("\u0E94" + "A" + "\u0967"  , level, version) == true);
    System.out.println("");
    version = 4; 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("", level, version) == false);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_", level, version) == true); 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_s", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("s", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_ss" + "\u0B47",        level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_a" + "\u0300", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_a" + "\u0300:", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId(":_a" + "\u0300:", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("\u0E94" + "A" + "\u0967"  , level, version) == true);
    System.out.println("");
    version = 5; 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("", level, version) == false);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_", level, version) == true); 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_s", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("s", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_ss" + "\u0B47",        level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_a" + "\u0300", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_a" + "\u0300:", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId(":_a" + "\u0300:", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("\u0E94" + "A" + "\u0967"  , level, version) == true);
    version = 6; //doesn't exist
    Assert.assertTrue(SyntaxChecker.isValidMetaId("s", level, version) == false);
    System.out.println("");

    level = 3; 
    version = 1; 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("", level, version) == false);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_", level, version) == true); 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_s", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("s", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_ss" + "\u0B47",        level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_a" + "\u0300", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_a" + "\u0300:", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId(":_a" + "\u0300:", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("\u0E94" + "A" + "\u0967"  , level, version) == true);
    System.out.println("");
    version = 2; 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("", level, version) == false);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_", level, version) == true); 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_s", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("s", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_ss" + "\u0B47", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_a" + "\u0300", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_a" + "\u0300:", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId(":_a" + "\u0300:", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("\u0E94" + "A" + "\u0967"  , level, version) == true);
    System.out.println("");


    //not available yet but as default the specification of L2V3 will be used
    level = 3;
    version = 3; 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("s", level, version) == true);
    System.out.println("");

    level = 4; 
    version = 1; 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("s", level, version) == true);
    System.out.println("");
  }

}
