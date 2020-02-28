package org.sbml.jsbml.test;

import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.util.PackageDisabler;

public abstract class PackageDisablerTests {
  
  public SBMLDocument doc; 
  public Model m;
  public PackageDisabler pDisabler;
  public String name; 
  public String uri; 
  
  public abstract void setUp();
  
  public abstract void testAddingPackage();
  
  public abstract void disableUnusedTest();
  
  public abstract void removePackageTest();
  
}
