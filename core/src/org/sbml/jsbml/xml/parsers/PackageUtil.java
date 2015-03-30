/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2014 jointly by the following organizations:
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

package org.sbml.jsbml.xml.parsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.AbstractSBasePlugin;
import org.sbml.jsbml.ext.SBasePlugin;


/**
 *
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.1
 */
public class PackageUtil {

  /**
   * Log4j logger
   */
  private static final transient Logger logger = Logger.getLogger(PackageUtil.class);

  
  /**
   * Checks the whole {@link SBMLDocument}, including all siblings, to make sure
   * that the package version and namespace is set properly.
   * 
   * <p>It will print warnings or errors when problems are found, nothing will be changed. 
   * 
   * @param doc the {@link SBMLDocument} to check.
   */
  public static void checkPackages(SBMLDocument doc) {
    checkPackages(doc, false, false);
  }
  
  /**
   * Checks the whole {@link SBMLDocument}, including all siblings, to make sure
   * that the package version and namespace is set properly.
   *
   * <p>The given boolean parameters will indicate if the method will print warnings
   * or errors when problems are found, and if it will try to fix problems. 
   *
   * @param doc the {@link SBMLDocument} to check.
   * @param silent boolean to indicate if errors and warnings should be shown.
   * @param fix boolean to indicate if encountered problems should be fixed.
   */
  public static void checkPackages(SBMLDocument doc, boolean silent, boolean fix) {
    if (doc == null) {
      return;
    }
    
    // getting the list of declared L3 packages from the declared namespaces
    // TODO - add a proper method to get this list directly from SBMLdocument?
    Map<String, String> declaredNamespaces = doc.getDeclaredNamespaces();
    List<String> packageNamespaces = new ArrayList<String>(); 
        
    if (declaredNamespaces != null && declaredNamespaces.size() > 0) {
      for (String xmlns : declaredNamespaces.keySet()) {
        if (xmlns.startsWith("xmlns:")) {
          String namespace = declaredNamespaces.get(xmlns);
          
          if (doc.isPackageEnabled(namespace)) {
            packageNamespaces.add(namespace);
          }
        }
      }
    }
    
    checkPackages(doc, packageNamespaces, silent, fix);
  }

  /**
   * Checks the given {@link SBase}, including all it's siblings, to make sure
   * that the package version and namespace is set properly.
   *
   * <p>The given boolean parameters will indicate if the method will print warnings
   * or errors when problems are found, and if it will try to fix problems. 
   * 
   * @param sbase the {@link SBase} to check.
   * @param packageNamespaces the {@link List} of namespaces that are expected to be found SBML L3 packages.
   * @param silent boolean to indicate if errors and warnings should be shown.
   * @param fix boolean to indicate if encountered problems should be fixed.
   */
  public static void checkPackages(SBase sbase, List<String> packageNamespaces, boolean silent, boolean fix) {
    if (sbase == null) {
      return;
    }    
    if (packageNamespaces == null) {
      packageNamespaces = new ArrayList<String>();
    }
  
    Map<String, PackageInfo> prefixMap = new HashMap<String, PackageInfo>();
    Map<String, PackageInfo> namespaceMap = new HashMap<String, PackageInfo>();
    
    for (String namespace : packageNamespaces) {

      // Get the package parser
      PackageParser packageParser = ParserManager.getManager().getPackageParser(namespace);
      
      if (packageParser != null) {
        PackageInfo pi = new PackageInfo();
        pi.prefix = packageParser.getPackageName();
        pi.namespace = namespace;
        pi.version = extractPackageVersion(namespace);
        
        prefixMap.put(pi.prefix, pi);
        namespaceMap.put(namespace, pi);
        
        if (logger.isDebugEnabled()) {
          logger.debug(pi);
        }
      }
      else {
        logger.warn("Package namespace unknow: '" + namespace + "'");
      }
    }
    
    // recursive test for all children if prefix is not "core"
    checkPackages(sbase, prefixMap, namespaceMap, silent, fix);
    
  }

  /**
   * Checks the given {@link SBase}, including all it's siblings, to make sure
   * that the package version and namespace is set properly.
   *
   * <p>The given boolean parameters will indicate if the method will print warnings
   * or errors when problems are found, and if it will try to fix problems. 
   * 
   * @param sbase the {@link SBase} to check.
   * @param prefixMap map between package name (or prefix or label) and a {@link PackageInfo} object,
   *  representing the package name, namespace and version
   * @param namespaceMap map between package namespace and a {@link PackageInfo} object,
   *  representing the package name, namespace and version
   * @param silent boolean to indicate if errors and warnings should be shown.
   * @param fix boolean to indicate if encountered problems should be fixed.
   */
  private static void checkPackages(SBase sbase, Map<String, PackageInfo> prefixMap, 
    Map<String, PackageInfo> namespaceMap, boolean silent, boolean fix)
  {
    if (sbase == null) {
      return;
    }
    
    // TODO - make use of the silent and fix parameters
    
    String packageName = sbase.getPackageName();
    String elementNamespace = sbase.getNamespace();
    int packageVersion = sbase.getPackageVersion();
    
    if (packageName.equals("core") && packageVersion != 0) {
      System.out.println("Warning: the element '" + sbase.getElementName() + "' seems to be part of SBML core but it's package version is not '0'!");
    } // TODO - do we want to check and set the namespace for core elements ?
    
    // check package name != core
    if (!packageName.equals("core")) {
      PackageInfo pi = prefixMap.get(packageName);
      
      if (pi == null) {
        // The package might not be enabled on the SBMLDocument
        System.out.println("Warning: the package '" + packageName + "' does not seem to be enabled.");
        SBMLDocument doc = sbase.getSBMLDocument();
        
        // TODO - register package using package version or default namespace
        // TODO - and add the package to the maps
        if (doc != null) {
          // TODO - register package using package version or default namespace
          
        } else {
          System.out.println("Warning: Can not found SBMLDocument on the element '" + sbase.getElementName() + "'. Stopping the check");
        }
      }
      
      // checking package version
      if (packageVersion != -1 && packageVersion != pi.version) {
        System.out.println("Warning: the element '" + sbase.getElementName() + "' does not seems to have"
          + " the expected package version. Found '" + packageVersion + "', expected '" + pi.version + "'");
        // TODO - fix if asked to
      } 
      else if (packageVersion == -1) 
      {
        System.out.println("Warning: the element '" + sbase.getElementName() + "' does not have a package version set!");
        sbase.setPackageVersion(pi.version);
      }
      
      // System.out.println("DEBUG: element = '" + sbase.getElementName() + "'. Found '" + elementNamespace + "', expected '" + pi.namespace + "'");
      
      // checking package namespace
      if (!elementNamespace.equals(pi.namespace)) {
        System.out.println("Warning: the element '" + sbase.getElementName() + "' does not seems to have"
          + " the expected package namespace. Found '" + elementNamespace + "', expected '" + pi.namespace + "'");
        
        if (sbase instanceof AbstractSBase) {
          ((AbstractSBase) sbase).unsetNamespace();
          ((AbstractSBase) sbase).setNamespace(pi.namespace);
        }
      }
    }
    
    // check all SBasePlugin
    int nbPlugins = sbase.getNumPlugins();

    if (nbPlugins > 0) {

      for (SBasePlugin sbasePlugin : sbase.getExtensionPackages().values()) {

        // check version and namespace for plugin
        packageName = sbasePlugin.getPackageName();
        elementNamespace = sbasePlugin.getElementNamespace();
        packageVersion = sbasePlugin.getPackageVersion();
        
        // TODO - checks similar as for SBase, try to refactor into one function
        PackageInfo pi = prefixMap.get(packageName);
        
        if (pi == null) {
          // The package might not be enabled on the SBMLDocument
          System.out.println("Warning: the package '" + packageName + "' does not seem to be enabled.");
          SBMLDocument doc = sbase.getSBMLDocument();
          
          // TODO - register package using package version or default namespace
          // TODO - and add the package to the maps
          if (doc != null) {
            // TODO - register package using package version or default namespace
            
          } else {
            System.out.println("Warning: Can not found SBMLDocument on the element '" + sbase.getElementName() + "'. Stopping the check");
          }
          continue;
        }
        
        // checking package version
        if (packageVersion != -1 && packageVersion != pi.version) {
          System.out.println("Warning: the element '" + sbasePlugin.getClass().getSimpleName() + "' does not seems to have"
            + " the expected package version. Found '" + packageVersion + "', expected '" + pi.version + "'");
          // TODO - fix if asked to
        } 
        else if (packageVersion == -1) 
        {
          System.out.println("Warning: the element '" + sbasePlugin.getClass().getSimpleName() + "' does not have a package version set!");
          sbasePlugin.setPackageVersion(pi.version);
        }
        
        // System.out.println("DEBUG: element = '" + sbasePlugin.getClass().getSimpleName() + "'. Found '" + elementNamespace + "', expected '" + pi.namespace + "'");
        
        // checking package namespace
        if (!elementNamespace.equals(pi.namespace)) {
          System.out.println("Warning: the element '" + sbasePlugin.getClass().getSimpleName() + "' does not seems to have"
            + " the expected package namespace. Found '" + elementNamespace + "', expected '" + pi.namespace + "'");
          
          
          if (sbasePlugin instanceof AbstractSBasePlugin) {
            // TODO - implement those methods in AbstractSBasePlugin
            // ((AbstractSBasePlugin) sbase).unsetNamespace();
            // ((AbstractSBasePlugin) sbase).setNamespace(pi.namespace);
          }
        }

      }

    }

    
    // check all children
    int childCount = sbase.getChildCount();
    
    for (int i = 0; i < childCount; i++) {
      Object childObj = sbase.getChildAt(i);
      
      if (childObj instanceof SBase) {
        checkPackages((SBase) childObj, prefixMap, namespaceMap, silent, fix);
      }
    }
  }
  
  /**
   * 
   * @param namespace
   * @return
   */
  private static int extractPackageVersion(String namespace) {
    int versionIndex = namespace.lastIndexOf("version");
    
    if (versionIndex == -1) {
      return -1;
    }
    
    String versionStr = namespace.substring(versionIndex + 7);
    
    int version = -1;
    
    try {
      version = Integer.parseInt(versionStr);
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
    
    return version;
  }

}

/**
 *
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.1
 */
final class PackageInfo {

  String prefix;
  String namespace;
  int version;
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "PackageInfo [prefix=" + prefix + ", namespace=" + namespace
      + ", version=" + version + "]";
  }
}
