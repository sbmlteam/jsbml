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
    
    //specification L1V1 under 3.3 Type SName
    level = 1;
    version = 1; 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("", level, version) == false);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_", level, version) == false); 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_s", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("s", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_aa_44abe", level, version) == true);
    //specification L1V2 under 3.3 Type SName 
    version = 2; 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("", level, version) == false);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_", level, version) == true); 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_s", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("s", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_aa_44abe", level, version) == true);
    
    //see specifications L2V1 under 3.4 SId
    level = 2;
    version = 1; 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("", level, version) == false);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_", level, version) == true); 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_s", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("s", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_aa_44abe", level, version) == true);
    
    //specifications L2V2 under 3.1.6
    level = 2; 
    version = 2; 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("", level, version) == false);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_", level, version) == true); 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_s", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("s", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_ssେ",        level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_̏a", level, version) == true);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_̏a:", level, version) == false);
    
    //since L2V3 all level and versions have equivalent metaId specs 
    //additionally to L2V2 colons are also included and can be used anywhere
    //TODO - nochmal in Spezifikationen nachlesen, ob das auch so ist
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
    Assert.assertTrue(SyntaxChecker.isValidMetaId("", level, version) == false);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_", level, version) == false); 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_s", level, version) == false);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("s", level, version) == false);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_ssେ",        level, version) == false);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_̏a", level, version) == false);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_̏a:", level, version) == false);
    Assert.assertTrue(SyntaxChecker.isValidMetaId(":_̏a:", level, version) == false);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("ດA१"  , level, version) == false);
    
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
    version = 3; //not available yet 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("", level, version) == false);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_", level, version) == false); 
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_s", level, version) == false);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("s", level, version) == false);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_ssେ",        level, version) == false);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_̏a", level, version) == false);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("_̏a:", level, version) == false);
    Assert.assertTrue(SyntaxChecker.isValidMetaId(":_̏a:", level, version) == false);
    Assert.assertTrue(SyntaxChecker.isValidMetaId("ດA१"  , level, version) == false);
    
  }
  
}
