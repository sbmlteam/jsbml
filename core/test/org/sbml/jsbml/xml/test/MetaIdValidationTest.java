package org.sbml.jsbml.xml.test;

import org.junit.Before;
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
  public void exoticMetaId() {
    Assert.assertTrue(SyntaxChecker.isValidMetaId("ດA१"));
  }
}
