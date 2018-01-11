/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2018 jointly by the following organizations:
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

import org.apache.log4j.Logger;


/**
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 */
public class ParserManager {

  /**
   * 
   */
  private static ParserManager manager;

  /**
   * 
   */
  public Map<String, ReadingParser> readingParsers = new HashMap<String, ReadingParser>();
  /**
   * 
   */
  private Map<String, WritingParser> writingParsers = new HashMap<String, WritingParser>();
  /**
   * 
   */
  private Map<String, PackageParser> packageParsers = new HashMap<String, PackageParser>();

  /**
   * Map between the {@link PackageParser} namespace and package short name.
   */
  private Map<String, String> namespaceToNameMap = new HashMap<String, String>();

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(ParserManager.class);

  private String[] parserDefaults = 
    {"org.sbml.jsbml.xml.parsers.UncertMLXMLNodeReader", "org.sbml.jsbml.xml.parsers.SBMLCoreParser", 
    "org.sbml.jsbml.xml.parsers.MathMLStaxParser", "org.sbml.jsbml.xml.parsers.ArraysParser", "org.sbml.jsbml.xml.parsers.CompParser",
    "org.sbml.jsbml.xml.parsers.DistribParser", "org.sbml.jsbml.xml.parsers.DynParser", "org.sbml.jsbml.xml.parsers.FBCParser",
    "org.sbml.jsbml.xml.parsers.GroupsParser", "org.sbml.jsbml.xml.parsers.L3LayoutParser", "org.sbml.jsbml.xml.parsers.LayoutParser",
    "org.sbml.jsbml.xml.parsers.MultiParser", "org.sbml.jsbml.xml.parsers.QualParser", "org.sbml.jsbml.xml.parsers.RenderParser",
    "org.sbml.jsbml.xml.parsers.ReqParser", "org.sbml.jsbml.xml.parsers.SpatialParser", "org.sbml.jsbml.xml.parsers.XMLNodeReader"};


  /**
   * Private constructor to make sure that we have only one {@link ParserManager} per JVM.
   */
  private ParserManager() {
    init();
  }

  /**
   * Returns the {@link ParserManager}.
   * 
   * @return the {@link ParserManager}.
   */
  public static ParserManager getManager() {
    if (manager == null) {
      manager = new ParserManager();
    }
    return manager;
  }

  /**
   * 
   */
  private void init() {
    // loading the ReadingParsers
    Iterator<ReadingParser> readingParserList = ServiceLoader.load(ReadingParser.class).iterator();
    List<String> classNames = new ArrayList<String>();
    
    // TODO - each time we add one HashMap entry, check that it was not defined already
    // so that we notice problems as early as possible if two packages declared to take care of the same namespace

    while (readingParserList.hasNext()) {
      try {
        ReadingParser readingParser = readingParserList.next();

        if (readingParser != null) {

          String packageName = "core";
          classNames.add(readingParser.getClass().getName());
          
          if (readingParser instanceof PackageParser) {
            packageName = ((PackageParser) readingParser).getPackageName();
            packageParsers.put(packageName, (PackageParser) readingParser);
          }
          for (String namespaceURI : readingParser.getNamespaces()) {
            readingParsers.put(namespaceURI, readingParser);
            namespaceToNameMap.put(namespaceURI, packageName);
          }

          if (readingParser instanceof WritingParser) {
            for (String namespaceURI : readingParser.getNamespaces()) {
              writingParsers.put(namespaceURI, (WritingParser) readingParser);
            }
          }
        }
      }
      catch (ServiceConfigurationError e){
        logger.info(e.getMessage());
      }
    }

    // loading the WritingParsers
    Iterator<WritingParser> service_list2 = ServiceLoader.load(WritingParser.class).iterator();
    while (service_list2.hasNext()) {
      try {
        WritingParser writingParser = service_list2.next();

        if (writingParser != null) {

          String packageName = "core";
          classNames.add(writingParser.getClass().getName());

          if (writingParser instanceof PackageParser) {
            packageName = ((PackageParser) writingParser).getPackageName();
            packageParsers.put(packageName, (PackageParser) writingParser);
          }

          for (String namespaceURI : writingParser.getNamespaces()) {
            writingParsers.put(namespaceURI, writingParser);
            namespaceToNameMap.put(namespaceURI, packageName);
          }
        }
      }
      catch (ServiceConfigurationError e){

      }
    }
    
    // TODO - check the maps and add the parsers by hand if they are not present.
    // prevent problems when developers are not setting properly eclipse, when jar 
    // files are not generated with 
 
    for (String parserClassName  : parserDefaults) {
      if (! classNames.contains(parserClassName)) {
        
        try {
          Object newInstance = Class.forName(parserClassName).newInstance();
          
          if (newInstance instanceof ReadingParser) {
            ReadingParser readingParser = (ReadingParser) newInstance;
            
            String packageName = "core";
            
            if (readingParser instanceof PackageParser) {
              packageName = ((PackageParser) readingParser).getPackageName();
              packageParsers.put(packageName, (PackageParser) readingParser);
            }
            for (String namespaceURI : readingParser.getNamespaces()) {
              readingParsers.put(namespaceURI, readingParser);
              namespaceToNameMap.put(namespaceURI, packageName);
            }

            if (readingParser instanceof WritingParser) {
              for (String namespaceURI : readingParser.getNamespaces()) {
                writingParsers.put(namespaceURI, (WritingParser) readingParser);
              }
            }
          }
          
          if (newInstance instanceof WritingParser)
          {
            WritingParser writingParser = (WritingParser) newInstance;

            String packageName = "core";

            if (writingParser instanceof PackageParser) {
              packageName = ((PackageParser) writingParser).getPackageName();
              packageParsers.put(packageName, (PackageParser) writingParser);
            }

            for (String namespaceURI : writingParser.getNamespaces()) {
              writingParsers.put(namespaceURI, writingParser);
              namespaceToNameMap.put(namespaceURI, packageName);
            }
          }
        } catch (ClassNotFoundException e) 
        {
          if (logger.isDebugEnabled()) {
            logger.debug("problem loading class '" + parserClassName + "': " + e.getMessage());
          }
        }
        catch (InstantiationException e) 
        {
          if (logger.isDebugEnabled()) {
            logger.debug("problem loading class '" + parserClassName + "': " + e.getMessage());
            e.printStackTrace();
          }
        }
        catch (IllegalAccessException e) 
        {
          if (logger.isDebugEnabled()) {
            logger.debug("problem loading class '" + parserClassName + "': " + e.getMessage());
            e.printStackTrace();
          }
        }
      }
    }


  }

  // <br /><dependency><br />
  // <groupId>org.kohsuke.metainf-services</groupId><br />
  // <artifactId>metainf-services</artifactId><br />
  // <version>1.1</version><br />
  // <optional>true</optional><br />
  // </dependency><br />


  /**
   * Returns the name of the SBML package corresponding to the given namespace URI.
   * 
   * @param namespace - the namespace URI of a SBML package.
   * @return the name of the SBML package corresponding to the given namespace or null.
   */
  public String getPackageName(String namespace) {

    String packageName = namespaceToNameMap.get(namespace);

    if (packageName != null) {
      return packageName;
    }

    return null;
  }

  /**
   * Returns the required attribute corresponding to the given name or namespace.
   * 
   * @param nameOrURI - the name or namespace of a SBML package.
   * @return the required attribute corresponding to the given name or namespace, false is returned if
   * no {@link PackageParser} was found.
   * @throws IllegalArgumentException if the name or namespace is not recognized by JSBML.
   */
  public boolean getPackageRequired(String nameOrURI) {

    PackageParser packageParser = getPackageParser(nameOrURI);

    if (packageParser != null) {
      return packageParser.isRequired();
    }

    throw new IllegalArgumentException("The name or namespace '" + nameOrURI + "' is not recognized by JSBML.");
  }

  /**
   * Returns the {@link PackageParser} corresponding to the given name or namespace.
   * 
   * @param nameOrURI - the name or namespace of a SBML package.
   * @return the {@link PackageParser} corresponding to the given name or namespace or null.
   */
  public PackageParser getPackageParser(String nameOrURI) {

    PackageParser packageParser = packageParsers.get(nameOrURI);

    if (packageParser == null) {
      String packageName = namespaceToNameMap.get(nameOrURI);

      if (packageName != null) {
        packageParser = packageParsers.get(packageName);
      }
    }

    return packageParser;
  }

  /**
   * Gets a copy of the registered {@link ReadingParser}s map.
   * 
   * @return a copy of the registered {@link ReadingParser}s map.
   */
  public Map<String, ReadingParser> getReadingParsers() {

    Map<String, ReadingParser> clonedMap = new HashMap<String, ReadingParser>();
    List<String> classNames = new ArrayList<String>();

    Iterator<ReadingParser> readingParserList = ServiceLoader.load(ReadingParser.class).iterator();

    while (readingParserList.hasNext()) {
      try {
        ReadingParser readingParser = readingParserList.next();

        if (readingParser != null) {

          classNames.add(readingParser.getClass().getName());
          
          for (String namespaceURI : readingParser.getNamespaces()) {
            clonedMap.put(namespaceURI, readingParser);
          }
        }
      }
      catch (ServiceConfigurationError e){
        // No need to print the message again, it is printed once in the init() method
      }
    }
    
    for (String parserClassName  : parserDefaults) {
      if (! classNames.contains(parserClassName)) {
        
        try {
          Object newInstance = Class.forName(parserClassName).newInstance();
          
          if (newInstance instanceof ReadingParser) {
            ReadingParser readingParser = (ReadingParser) newInstance;
            
            for (String namespaceURI : readingParser.getNamespaces()) {
              clonedMap.put(namespaceURI, readingParser);
            }
          }
        } catch (ClassNotFoundException e) 
        {
          if (logger.isDebugEnabled()) {
            logger.debug("problem loading class '" + parserClassName + "': " + e.getMessage());
          }
        }
        catch (InstantiationException e) 
        {
          if (logger.isDebugEnabled()) {
            logger.debug("problem loading class '" + parserClassName + "': " + e.getMessage());
            e.printStackTrace();
          }
        }
        catch (IllegalAccessException e) 
        {
          if (logger.isDebugEnabled()) {
            logger.debug("problem loading class '" + parserClassName + "': " + e.getMessage());
            e.printStackTrace();
          }
        }
      }
    }


    return clonedMap;
  }

  /**
   * Gets a copy of the registered {@link WritingParser}s map.
   * 
   * @return a copy of the registered {@link WritingParser}s map.
   */
  public Map<String, WritingParser> getWritingParsers() {

    Map<String, WritingParser> clonedMap = new HashMap<String, WritingParser>();
    List<String> classNames = new ArrayList<String>();

    Iterator<ReadingParser> readingParserList = ServiceLoader.load(ReadingParser.class).iterator();

    while (readingParserList.hasNext()) {
      try {
        ReadingParser readingParser = readingParserList.next();

        if (readingParser != null && readingParser instanceof WritingParser) {

          classNames.add(readingParser.getClass().getName());

          for (String namespaceURI : readingParser.getNamespaces()) {
            clonedMap.put(namespaceURI, (WritingParser) readingParser);
          }
        }
      }
      catch (ServiceConfigurationError e){
        // No need to print the message again, it is printed once in the init() method
      }
    }

    Iterator<WritingParser> service_list2 = ServiceLoader.load(WritingParser.class).iterator();

    while (service_list2.hasNext()) {
      try {
        WritingParser writingParser = service_list2.next();

        if (writingParser != null) {

          classNames.add(writingParser.getClass().getName());

          for (String namespaceURI : writingParser.getNamespaces()) {
            clonedMap.put(namespaceURI, writingParser);
          }
        }
      }
      catch (ServiceConfigurationError e){
        e.printStackTrace();
      }
    }

    for (String parserClassName  : parserDefaults) {
      if (! classNames.contains(parserClassName)) {
        
        try {
          Object newInstance = Class.forName(parserClassName).newInstance();
          
          if (newInstance instanceof WritingParser) {
            WritingParser readingParser = (WritingParser) newInstance;
            
            for (String namespaceURI : readingParser.getNamespaces()) {
              clonedMap.put(namespaceURI, readingParser);
            }
          }
        } catch (ClassNotFoundException e) 
        {
          if (logger.isDebugEnabled()) {
            logger.debug("problem loading class '" + parserClassName + "': " + e.getMessage());
          }
        }
        catch (InstantiationException e) 
        {
          if (logger.isDebugEnabled()) {
            logger.debug("problem loading class '" + parserClassName + "': " + e.getMessage());
            e.printStackTrace();
          }
        }
        catch (IllegalAccessException e) 
        {
          if (logger.isDebugEnabled()) {
            logger.debug("problem loading class '" + parserClassName + "': " + e.getMessage());
            e.printStackTrace();
          }
        }
      }
    }

    return clonedMap;
  }

  /**
   * Gets the namespace for the given package name that correspond to the SBML level, version
   * and package version.
   * 
   * <p>Returns {@code null} if the combined level, version and packageVersion is
   * invalid or not known for this package.
   * 
   * @param level - the SBML level
   * @param version - the SBML version
   * @param packageVersion - the package version
   * @param packageName - the package name
   * @return the namespace for the given package name that correspond to the SBML level, version
   * and package version.
   */
  public String getNamespaceFor(int level, int version, int packageVersion, String packageName) {

    PackageParser packageParser = packageParsers.get(packageName);

    if (packageParser != null) {
      return packageParser.getNamespaceFor(level, version, packageVersion);
    }

    return null;
  }

  /**
   * For testing purposes.
   * 
   * @param args
   */
  public static void main(String[] args) {
    System.out.println(ParserManager.getManager().getReadingParsers());
    System.out.println(ParserManager.getManager().getReadingParsers());
    System.out.println(ParserManager.getManager().readingParsers);

    System.out.println(ParserManager.getManager().getWritingParsers());
    System.out.println(ParserManager.getManager().getWritingParsers());
    System.out.println(ParserManager.getManager().writingParsers);
  }

}
