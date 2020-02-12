package org.sbml.jsbml.util;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;

public abstract class AbstractPackageDisablerTests {
  protected SBMLDocument doc; 
  protected Model m;
  protected PackageDisabler pDisabler;
  protected String name; 
  protected String uri; 
  
  public abstract void setUp();
  
  public abstract void testAddingPackage();
  
  public abstract void disableUnusedTest();
  
  public abstract void removePackageTest();
  
}
