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
package org.sbml.jsbml.resources;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Dummy class that just loads resource files if required.
 * 
 * @author Hannes Borch
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
public final class Resource {

  /**
   * The singleton element.
   */
  private static Resource resource;

  static {
    resource = new Resource();
  }

  /**
   * 
   * @return
   */
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
    Properties prop = null;
    Resource loader = getInstance();

    byte bytes[] = loader.getBytesFromResourceLocation(resourceName);
    if (bytes != null) {
      ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
      prop = new Properties();
      prop.load(bais);
    }
    if (prop != null) {
      return prop;
    }
    // ///////////

    int slInd = resourceName.lastIndexOf('/');
    if (slInd != -1) {
      resourceName = resourceName.substring(slInd + 1);
    }
    Properties userProps = new Properties();
    File propFile = new File(File.separatorChar + "resources"
        + File.separatorChar + resourceName);
    if (propFile.exists()) {
      userProps.load(new FileInputStream(propFile));
    }
    return userProps;
  }

  /**
   * Since this is a singleton class, nobody should be able to instanciate it.
   */
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
    if (in == null) {
      return null;
    }
    return getBytesFromStream(in);
  }

  /**
   * Gets the byte data from a file.
   * 
   * @param stream
   *            Description of the Parameter
   * @return the byte array of the file.
   */
  private byte[] getBytesFromStream(InputStream stream) {
    if (stream == null) {
      return null;
    }
    BufferedInputStream bis = new BufferedInputStream(stream);
    try {
      int size = bis.available();
      byte[] b = new byte[size];
      int rb = 0;
      int chunk = 0;

      while ((size - rb) > 0) {
        chunk = bis.read(b, rb, size - rb);

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
