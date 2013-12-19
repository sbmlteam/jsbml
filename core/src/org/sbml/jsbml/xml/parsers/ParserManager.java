/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */

package org.sbml.jsbml.xml.parsers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;


/**
 * 
 * @author Nicolas Rodriguez
 *
 */
public class ParserManager {

	private static ParserManager manager;
	
	public HashMap<String, ReadingParser> readingParsers = new HashMap<String, ReadingParser>();
	private HashMap<String, WritingParser> writingParsers = new HashMap<String, WritingParser>();
	private HashMap<String, PackageParser> packageParsers = new HashMap<String, PackageParser>();
	
	private HashMap<String, String> namespaceToNameMap = new HashMap<String, String>(); 
	
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
		
		while (readingParserList.hasNext()) {
			try {
				ReadingParser readingParser = readingParserList.next();
				
				if( readingParser != null) {

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
			}
			catch (ServiceConfigurationError e){

			}
		}
		
		// loading the WritingParsers
		Iterator<WritingParser> service_list2 = ServiceLoader.load( WritingParser.class).iterator(); 
		while (service_list2.hasNext()) {
			try {
				WritingParser writingParser = service_list2.next();
				
				if( writingParser != null) {
					
					String packageName = "core";
					
					if (writingParser instanceof PackageParser) {
						packageName = ((PackageParser) writingParser).getPackageName();
						packageParsers.put(packageName, (PackageParser) writingParser);
					}

					for (String namespaceURI : writingParser.getNamespaces()) {
						writingParsers.put(namespaceURI, (WritingParser) writingParser);
						namespaceToNameMap.put(namespaceURI, packageName);
					}											
				}
			}
			catch (ServiceConfigurationError e){

			}
		}

	}

	// <br /><dependency><br />  <groupId>org.kohsuke.metainf-services</groupId><br />  <artifactId>metainf-services</artifactId><br />  <version>1.1</version><br />  <optional>true</optional><br /></dependency><br />
	
	
	/**
	 * Returns the name of the SBML package corresponding to the given namespace.
	 *  
	 * @param namespaceURI
	 * @return the name of the SBML package corresponding to the given namespace.
	 */
	public String getPackageName(String namespaceURI) {
		
		String packageName = namespaceToNameMap.get(namespaceURI);
		
		if (packageName != null) {
			return packageName;
		}
		
		return null;
	}

	/**
	 * Gets a copy of the registered {@link ReadingParser}s map.
	 * 
	 * @return a copy of the registered {@link ReadingParser}s map.
	 */
	public HashMap<String, ReadingParser> getReadingParsers() {

		HashMap<String, ReadingParser> clonedMap = new HashMap<String, ReadingParser>();
		
		Iterator<ReadingParser> readingParserList = ServiceLoader.load(ReadingParser.class).iterator();
		
		while (readingParserList.hasNext()) {
			try {
				ReadingParser readingParser = readingParserList.next();
				
				if( readingParser != null) {

					for (String namespaceURI : readingParser.getNamespaces()) {
						clonedMap.put(namespaceURI, readingParser);
					}
				}
			}
			catch (ServiceConfigurationError e){

			}
		}

		return clonedMap;
	}
	
	/**
	 * Gets a copy of the registered {@link WritingParser}s map.
	 * 
	 * @return a copy of the registered {@link WritingParser}s map.
	 */
	public HashMap<String, WritingParser> getWritingParsers() {
		// TODO - get a new instance of the parsers ??
		return writingParsers;
	}
	
	/**
	 * Gets the namespace for the given package name that correspond to the SBML level, version
	 * and package version.
	 *  
	 * <p>Returns null if the combined level, version and packageVersion is
	 * invalid or not known for this package.
	 * 
	 * @param level - the SBML level
	 * @param version - the SBML version
	 * @param packageVersion - the package version
	 * @param packageName - the package name
	 * @return the namespace for the given package name that correspond to the SBML level, version
	 * and package version.
	 */
	public String getNamespaceFor(String level, String version, String packageVersion, String packageName) {
		
		PackageParser packageParser = packageParsers.get(packageName);
		
		if (packageParser != null) {
			return packageParser.getNamespaceFor(level, version, packageVersion);
		}

		return null;
	}

	public static void main(String[] args) {
		System.out.println(ParserManager.getManager().getReadingParsers());
		System.out.println(ParserManager.getManager().getReadingParsers());
		System.out.println(ParserManager.getManager().readingParsers);
	}

}
