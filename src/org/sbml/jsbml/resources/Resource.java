/*
 * $Id$
 * $URL$
 *
 *
 *==================================================================================
 * Copyright (c) 2009 the copyright is held jointly by the individual
 * authors. See the file AUTHORS for the list of authors.
 *
 * This file is part of jsbml, the pure java SBML library. Please visit
 * http://sbml.org for more information about SBML, and http://jsbml.sourceforge.net/
 * to get the latest version of jsbml.
 *
 * jsbml is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * jsbml is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with jsbml.  If not, see <http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html>.
 *
 *===================================================================================
 *
 */

package org.sbml.jsbml.resources;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Vector;

/**
 * Dummy class that just loads resource files if required.
 * 
 * @author Hannes Borch
 * @author Andreas Dr&auml;ger
 * @date 2009-02-05
 * 
 * @opt attributes
 * @opt types
 * @opt visibility
 */
public final class Resource {

	private static Resource resource;

	static {
		resource = new Resource();
	}

	public static Resource getInstance() {
		return resource;
	}

	/**
	 * 
	 * @param resourceName
	 * @return
	 * @throws IOException
	 */
	public static Properties readProperties(String resourceName)
			throws IOException {
		Properties prop = new Properties();
		Resource loader = getInstance();

		byte bytes[] = loader.getBytesFromResourceLocation(resourceName);
		if (bytes != null) {
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			prop.load(bais);
		}
		if (prop != null)
			return prop;
		// ///////////

		int slInd = resourceName.lastIndexOf('/');
		if (slInd != -1)
			resourceName = resourceName.substring(slInd + 1);
		Properties userProps = new Properties();
		File propFile = new File(File.separatorChar + "resources"
				+ File.separatorChar + resourceName);
		if (propFile.exists())
			userProps.load(new FileInputStream(propFile));
		return userProps;
	}

	private Resource() {
	}

	/**
	 * Gets the byte data from a file at the given resource location.
	 * 
	 * @param rawResrcLoc
	 *            Description of the Parameter
	 * @return the byte array of file.
	 */
	public byte[] getBytesFromResourceLocation(String rawResrcLoc) {
		InputStream in = getStreamFromResourceLocation(rawResrcLoc);
		if (in == null)
			return null;
		return getBytesFromStream(in);
	}

	/**
	 * Gets the byte data from a file.
	 * 
	 * @param fileName
	 *            Description of the Parameter
	 * @return the byte array of the file.
	 */
	private byte[] getBytesFromStream(InputStream stream) {
		if (stream == null)
			return null;
		BufferedInputStream bis = new BufferedInputStream(stream);
		try {
			int size = (int) bis.available();
			byte[] b = new byte[size];
			int rb = 0;
			int chunk = 0;

			while (((int) size - rb) > 0) {
				chunk = bis.read(b, rb, (int) size - rb);

				if (chunk == -1) {
					break;
				}

				rb += chunk;
			}
			return b;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets the byte data from a file.
	 * 
	 * @param fileName
	 *            Description of the Parameter
	 * @return the byte array of the file.
	 */
	private FileInputStream getStreamFromFile(String fileName) {
		if (fileName.startsWith("/cygdrive/")) {
			int length = "/cygdrive/".length();
			fileName = fileName.substring(length, length + 1) + ":"
					+ fileName.substring(length + 1);
		}
		File file = new File(fileName);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			return fis;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets the byte data from a file at the given resource location.
	 * 
	 * @param rawResrcLoc
	 *            Description of the Parameter
	 * @return the byte array of file.
	 */
	public InputStream getStreamFromResourceLocation(String rawResrcLoc) {
		String resourceLocation = rawResrcLoc.replace('\\', '/');
		if (resourceLocation == null) {
			return null;
		}
		// to avoid hours of debugging non-found-files under linux with
		// some f... special characters at the end which will not be shown
		// at the console output !!!
		resourceLocation = resourceLocation.trim();
		InputStream in = null;

		// is a relative path defined ?
		// this can only be possible, if this is a file resource location
		if (resourceLocation.startsWith("..")
				|| resourceLocation.startsWith("/")
				|| resourceLocation.startsWith("\\")
				|| ((resourceLocation.length() > 1) && (resourceLocation
						.charAt(1) == ':'))) {
			in = getStreamFromFile(resourceLocation);
		}
		// InputStream inTest = getStreamFromFile(resourceLocation);
		if (in == null) {
			in = ClassLoader.getSystemResourceAsStream(resourceLocation);
		}
		if (in == null) {
			// try again for web start applications
			in = this.getClass().getClassLoader().getResourceAsStream(
					resourceLocation);
		}
		if (in == null) {
			// try to search other classpathes...? not really necessary.
			// in = getStreamFromClassPath(resourceLocation);
		}
		return in;
	}
}
