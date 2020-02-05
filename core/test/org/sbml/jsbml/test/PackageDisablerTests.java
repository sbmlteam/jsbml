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
    //TODO - enable namespaces? 
    sb.addDeclaredNamespace("package 1", "Arrays");
    sb.addDeclaredNamespace("package 2", "Layout");
    pDisabler = new PackageDisabler(sb);
  }
  
  @Test
  public void disableUnusedTest() {
    
  }
  
  @Test
  public void removePackageTest() {
    pDisabler.removePackage("Arrays"); //warum wird kein package erkannt? 
    System.out.println(sb.getDeclaredNamespaces());
  }
}
