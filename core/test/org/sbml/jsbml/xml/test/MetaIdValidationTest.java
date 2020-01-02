package org.sbml.jsbml.xml.test;

import org.junit.Test;
import org.sbml.jsbml.validator.SyntaxChecker;
import org.junit.Assert;

/**
 * 
 * @author Onur Özel		
 * @since 1.5
 */
public class MetaIdValidationTest {
  
  @Test
  public void initMetaPatternsLevelVersionTest() {
    int level, version;
    
    //specifications L2V2 under 3.1.6
    //has to start with a letter or an underscore
    level = 2; 
    version = 2; 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("", level, version) == false); 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_s", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("s_", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_ssେ",        level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_̏a", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_̏a:", level, version) == false);
    System.out.println("");
    
    //since L2V3 all level and versions have equivalent metaId specifications
    //additionally to L2V2 colons are also included and can be used anywhere in the meta Id
    level = 2; 
    version = 3; 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("", level, version) == false);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_", level, version) == true); 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_s", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("s", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_ssେ",        level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_̏a", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_̏a:", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId(":_̏a:", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("ດA१"  , level, version) == true);
    System.out.println("");
    version = 4; 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("", level, version) == false);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_", level, version) == true); 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_s", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("s", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_ssେ",        level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_̏a", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_̏a:", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId(":_̏a:", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("ດA१"  , level, version) == true);
    System.out.println("");
    version = 5; 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("", level, version) == false);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_", level, version) == true); 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_s", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("s", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_ssେ",        level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_̏a", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_̏a:", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId(":_̏a:", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("ດA१"  , level, version) == true);
    version = 6; //doesn't exist
    Assert.assertTrue(SyntaxChecker.isValidMetaId("s", level, version) == false);
    System.out.println("");
    level = 3; 
    version = 1; 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("", level, version) == false);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_", level, version) == true); 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_s", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("s", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_ssେ",        level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_̏a", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_̏a:", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId(":_̏a:", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("ດA१"  , level, version) == true);
    System.out.println("");
    version = 2; 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("", level, version) == false);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_", level, version) == true); 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_s", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("s", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_ssେ",        level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_̏a", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_̏a:", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId(":_̏a:", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("ດA१"  , level, version) == true);
    System.out.println("");
    version = 3; //not available yet 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("s", level, version) == false);
    System.out.println("");
    
  }
  
}
