package org.sbml.jsbml.test;

import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.util.PackageDisabler;

/**
 * @author Onur &Ouml;zel
 * @since 1.5
 */
public class PackageDisablerTests {
  
  private SBMLDocument sb; 
  private PackageDisabler pDisabler;
  
  @Before
  public void setUp() {
    sb = new SBMLDocument();
    //TODO - build sbml with some packages enabled  
    pDisabler = new PackageDisabler(sb);
  }
  
  @Test
  public void disableUnusedTest() {
    
  }
  
  @Test
  public void removePackageTest() {
    
  }
}
