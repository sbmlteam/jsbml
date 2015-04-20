/*
 * $Id: Config.java 2109 2015-01-05 04:50:45Z andreas-draeger $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/examples/sbmlbargraph/src/org/sbml/bargraph/Config.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2015 jointly by the following organizations:
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
package org.sbml.bargraph;


/**
 * Class to store application configuration values and provide convenience
 * methods for certain common operating-system-related actions.
 * @file    Config.java
 * @brief   Configuration data and methods.
 * @author  Michael Hucka
 * @date    Created in 2012. This file is based on code I wrote in 2000-2003.
 */
public class Config
{
  //
  // -------------------------- Public constants ----------------------------
  //

  /** Our application name. **/
  public static final String APP_NAME = "SBML Bar Graph";

  /** Our home URL. **/
  public static final String APP_HOME_URL = "http://sbml.org";

  /** Directory name for application data under Windows. **/
  public static final String DATA_DIR_WINDOWS = "sbmlbargraph";

  /** Directory name for application data under Linux & Mac. **/
  public static final String DATA_DIR_UNIX = ".sbmlbargraph";

  /** Resource file containing the splash screen image. **/
  public static final String RES_ICON_SBML
  = "resources/icons/SBML.png";

  /** Resource file containing the application logo. **/
  public static final String RES_ICON_APP
  = "resources/icons/sbml-bar-graph-icon.png";

  /** Resource file for icon used in question dialogs. **/
  public static final String RES_ICON_QUESTION
  = "resources/icons/question.png";

  /** Resource file for icon used in error dialogs. **/
  public static final String RES_ICON_ERROR
  = "resources/icons/error.png";


  // The following set of constants is used to distinguish different
  // operating systems.  See the methods operatingSystem() below.
  // This code was taken from NetBeans version 3.0 "stable".

  /** Operating system is Windows NT. */
  public static final int OS_WINNT = 1;

  /** Operating system is Windows 95. */
  public static final int OS_WIN95 = 2;

  /** Operating system is Windows 98. */
  public static final int OS_WIN98 = 4;

  /** Operating system is Solaris. */
  public static final int OS_SOLARIS = 8;

  /** Operating system is Linux. */
  public static final int OS_LINUX = 16;

  /** Operating system is HP-UX. */
  public static final int OS_HP = 32;

  /** Operating system is IBM AIX. */
  public static final int OS_AIX = 64;

  /** Operating system is SGI IRIX. */
  public static final int OS_IRIX = 128;

  /** Operating system is Sun OS. */
  public static final int OS_SUNOS = 256;

  /** Operating system is DEC (Digital Unix). */
  public static final int OS_DEC = 512;

  /** Operating system is OS/2. */
  public static final int OS_OS2 = 1024;

  /** Operating system is Mac OS 9.x. */
  public static final int OS_MAC = 2048;

  /** Operating system is Windows 2000. */
  public static final int OS_WIN2000 = 4096;

  /** Operating system is Windows XP. */
  public static final int OS_WINXP = 8192;

  /** Operating system is Mac OS X (a Unix-based system). */
  public static final int OS_DARWIN = 16384;

  /** Operating system is FreeBSD. */
  public static final int OS_FREEBSD = 32768;

  /** Operating system is unknown. */
  public static final int OS_OTHER = 65536;

  /** Operating system is Windows Vista. */
  public static final int OS_WINVISTA = 131072;

  /** Operating system is Windows 7. */
  public static final int OS_WIN7 = 262144;

  /** Operating system is Windows 8. */
  public static final int OS_WIN8 = 524288;

  /** A mask for Windows platforms. */
  public static final int OS_WINDOWS_MASK = OS_WINNT | OS_WIN95 | OS_WIN98 |
      OS_WIN2000 | OS_WINXP |
      OS_WINVISTA | OS_WIN7 | OS_WIN8;

  /** A mask for Unix platforms. */
  public static final int OS_UNIX_MASK = OS_SOLARIS | OS_LINUX | OS_HP |
      OS_AIX | OS_IRIX | OS_SUNOS |
      OS_DEC | OS_DARWIN | OS_FREEBSD;


  //
  // --------------------------- Public methods -----------------------------
  //

  /**
   * Return a string containing details about the run-time configuration
   * of the system where this instance of SBML Bar Graph is running.
   * @return
   */
  public static final String systemInfo()
  {
    String m;
    final String e = System.getProperty("line.separator");

    if (runningWindows()) {
      m = "Running under a flavor of Windows";
    } else if (runningUnix()) {
      m = "Running under a flavor of Unix/Linux";
    } else if (runningMac()) {
      m = "Running under a flavor of MacOS";
    } else {
      m = "Running under an unknown operating system";
    }

    m += e + "Java runtime  = "
        + System.getProperty("java.vm.name")
        + ", version " + System.getProperty("java.vm.version")
        + ", " + System.getProperty("java.vm.info")
        + e
        + "Java vendor   = " + System.getProperty("java.vendor", "unknown")
        + e
        + "Java home     = " + System.getProperty("java.home", "unknown")
        + e
        + "Java compiler = " + System.getProperty("java.compiler", "unknown")
        + e
        + "Java VM name  = " + System.getProperty("java.vm.name", "unknown")
        + e
        + "Java VM vers. = " + System.getProperty("java.vm.version", "unknown")
        + e
        + "User name     = " + System.getProperty("user.name", "unknown")
        + e
        + "User home     = " + System.getProperty("user.home", "unknown")
        + e
        + "Working dir   = " + System.getProperty("user.dir", "unknown")
        + e
        + "Class path    = " + System.getProperty("java.class.path", "unknown")
        + e
        + "Library path  = " + System.getProperty("java.library.path", "unknown")
        + e
        + "Temp dir      = " + System.getProperty("java.io.tmpdir", "unknown")
        ;

    return m;
  }


  /**
   * @return an integer code indicating the operating system in use
   */
  public static final int operatingSystem()
  {
    if (osID == -1)
    {
      String osName = System.getProperty("os.name");
      if ("Windows NT".equals(osName)) {
        osID = OS_WINNT;
      } else if ("Windows 95".equals(osName)) {
        osID = OS_WIN95;
      } else if ("Windows 98".equals(osName)) {
        osID = OS_WIN98;
      } else if ("Windows 2000".equals(osName)) {
        osID = OS_WIN2000;
      } else if ("Windows XP".equals(osName)) {
        osID = OS_WINXP;
      } else if ("Windows Vista".equals(osName)) {
        osID = OS_WINVISTA;
      } else if ("Windows 7".equals(osName)) {
        osID = OS_WIN7;
      } else if ("Windows 8".equals(osName)) {
        osID = OS_WIN8;
      } else if ("Solaris".equals(osName)) {
        osID = OS_SOLARIS;
      } else if (osName.startsWith("SunOS")) {
        osID = OS_SOLARIS;
      } else if ("Linux".equals(osName)) {
        osID = OS_LINUX;
      } else if ("FreeBSD".equals(osName)) {
        osID = OS_FREEBSD;
      } else if ("HP-UX".equals(osName)) {
        osID = OS_HP;
      } else if ("AIX".equals(osName)) {
        osID = OS_AIX;
      } else if ("Irix".equals(osName)) {
        osID = OS_IRIX;
      } else if ("SunOS".equals(osName)) {
        osID = OS_SUNOS;
      } else if ("Digital UNIX".equals(osName)) {
        osID = OS_DEC;
      } else if ("OS/2".equals(osName)) {
        osID = OS_OS2;
      } else if (osName.startsWith("Mac OS")) {
        osID = OS_MAC;
      } else if (osName.startsWith("Darwin")) {
        osID = OS_DARWIN | OS_MAC;
      } else {
        osID = OS_OTHER;
      }
    }
    return osID;
  }


  /**
   * Predicate for testing whether SBML Bar Graph is currently on a version of
   * MS Windows.
   *
   * @return {@code true} if Windows, {@code false} if other OS
   **/
  public static final boolean runningWindows()
  {
    return (operatingSystem() & OS_WINDOWS_MASK) != 0;
  }


  /**
   * Predicate for testing whether SBML Bar Graph is currently on a version of
   * Unix (including Darwin) or Linux.
   *
   * @return {@code true} if Unix, {@code false} if other OS
   */
  public static final boolean runningUnix()
  {
    return (operatingSystem() & OS_UNIX_MASK) != 0;
  }


  /**
   * Predicate for testing whether SBML Bar Graph is currently on a version of
   * Apple MacOS.  Note that MacOS X (Darwin) will produce a true
   * result here as well as with {@link #runningUnix()}.
   *
   * @return {@code true} if MacOS, {@code false} if other OS
   */
  public static final boolean runningMac()
  {
    return (operatingSystem() & OS_MAC) != 0;
  }

  //
  // -------------------------- Private variables ---------------------------
  //

  /** Operating system under which we're running. **/
  private static int osID = -1;
}
