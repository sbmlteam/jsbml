/*
 * $Id$
 * $URL$
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

import java.io.File;
import java.lang.reflect.Method;

import gnu.getopt.LongOpt;
import gnu.getopt.Getopt;


/**
 * Main class for SBML Bar Graph.  This should be indicated as the main
 * class in the manifest for a runnable JAR file.  It handles command-line
 * arguments and starts the GUI interface.
 * @file    Main.java
 * @brief   Main class for SBML Bar Graph.
 * @author  Michael Hucka
 * @date    Created in 2012
 */
public class Main
{
  //
  // -------------------------- Public constants ----------------------------
  //

  /** Exit status code: normal **/
  public static final int STATUS_NORMAL = 0;

  /** Exit status code: bad argument **/
  public static final int STATUS_BAD_ARG = 1;

  /** Exit status code: other error **/
  public static final int STATUS_ERROR = 2;

  //
  // --------------------------- Public methods -----------------------------
  //

  /**
   * Main entry point; starts the SBML Bar Graph.
   *
   * @param args the array of command line argument strings
   **/
  public static void main(String[] args)
  {
    Main app = new Main();
    app.run(args);
  }


  /**
   * Terminates execution with a specific status code.
   *
   * @param code an integer code to use as the exit status code
   **/
  public static void quit(int code)
  {
    if (code == STATUS_NORMAL) {
      Log.note("Exiting normally.");
    } else {
      Log.note("Exiting with error code #" + code + ".");
    }

    Log.note("Exiting. Ciao!");
    System.exit(code);
  }


  /**
   * Opens a file in a browser.
   * <p>
   * This tries in multiple ways to invoke the user's web browser to open
   * the given file.  If it fails, it prints a warning to the console or
   * the log file, but otherwise stays silent.
   * <p>
   * Much of the following was originally based on the "Bare Bones Browser
   * Launch for Java", by Dem Pilafian, version 3.0, Feb. 7, 2010.
   * Found at http://www.centerkey.com/java/browser/ on 16 Apr. 2010.
   * 
   * @param url The URL of the web pag to open.
   */
  public static final void openInBrowser(String url)
  {
    // Web browsers we know about.  Safari is handled differently, so
    // doesn't need to be listed here.
    final String[] knownBrowsers
    = { "chrome", "google-chrome", "firefox", "opera", "konqueror",
      "epiphany", "seamonkey", "galeon", "kazehakase", "mozilla" };

    Log.note("Attempting to open the following URL in a browser: " + url);

    // First try to use the Desktop library introduced in JDK 1.6.
    try
    {
      Class<?> desktop    = Class.forName("java.awt.Desktop");
      Class<?>[] uriClass = new Class<?>[] { java.net.URI.class };
      Object[] urlObj     = new Object[] { java.net.URI.create(url) };
      Method getDesktop   = desktop.getDeclaredMethod("getDesktop");
      Method browse       = desktop.getDeclaredMethod("browse", uriClass);

      Log.note("Found JDK 1.6 Desktop class; invoking it.");
      browse.invoke(getDesktop.invoke(null), urlObj);
    }
    catch (Exception ignore)    // Not running JDK 1.6 or class not found.
    {
      Log.note("Unable to use Desktop class; trying fall-back approach.");
      try
      {
        if (Config.runningMac())
        {
          Log.note("Invoking Mac FileManager's openURL.");
          Class<?> fm    = Class.forName("com.apple.eio.FileManager");
          Class<?>[] str = new Class<?>[] { String.class };
          Method open    = fm.getDeclaredMethod("openURL", str);
          open.invoke(null, new Object[] { url });
        }
        else if (Config.runningWindows())
        {
          Log.note("Invoking Windows' url.dll.");
          Runtime.getRuntime().exec(
            "rundll32 url.dll,FileProtocolHandler " + url);
        }
        else // Default to Unix.
        {
          Log.note("Unix or Linux; searching for a browser.");
          Runtime runtime = Runtime.getRuntime();
          boolean found = false;

          for (String browser : knownBrowsers)
          {
            String[] which = new String[] { "which", browser };
            if (runtime.exec(which).waitFor() == 0)
            {
              Log.note("Found "+ browser + "; invoking it.");
              runtime.exec(new String[] {browser, url});
              found = true;
              break;
            }
          }
          if (!found)
          {
            Log.note("Unable to find a browser; giving up.");
          }
        }
      }
      catch (Exception e)
      {
        Log.warning("Unable to invoke a browser to open '"
            + url + "'. Giving up.");
      }
    }
  }


  //
  // --------------------------- Private methods ----------------------------
  //

  /**
   * The constructor for this class is private and inaccessible
   * outside of this class.
   **/
  private Main() { }     // No-op.


  /**
   * The top-level main routine.  This parses the command line flags,
   * takes appropriate actions, and starts up whatever's needed.
   *
   * @param args the array of command line argument strings
   **/
  private void run(String[] args)
  {
    parseArgs(args);
    doSanityChecks();
    if (fileToGraph == null) {
      startGUI(null);
    } else if (fileToGraph.isVerifiedFile())
    {
      try
      {
        startGUI(fileToGraph);
      }
      catch (Exception e)
      {
        Log.error(e.getMessage());
        quit(STATUS_BAD_ARG);
      }
    } else {
      quit(STATUS_BAD_ARG);
    }
  }


  /**
   * Parses command line arguments and sets internal internal flags
   * and properties as appropriate.
   * 
   * @param args the command-line argument string array
   **/
  private void parseArgs(String[] args)
  {
    // These are in alphabetical order by the single-letter key (last arg).

    LongOpt[] opts    = new LongOpt[4];
    final int NO_ARG  = LongOpt.NO_ARGUMENT;
    final int REQ_ARG = LongOpt.REQUIRED_ARGUMENT;

    opts[0] = new LongOpt("debug",		NO_ARG,  null, 'd');
    opts[1] = new LongOpt("file",		REQ_ARG, null, 'f');
    opts[2] = new LongOpt("help",		NO_ARG,  null, 'h');
    opts[3] = new LongOpt("version",	NO_ARG,  null, 'V');

    Getopt g = new Getopt(Config.APP_NAME, args, "-df:hV", opts);

    // Do a little bit of sanity checking for a common pilot error.

    for (int i = 0; i < args.length; i++) {
      if ("-debug".equals(args[i])
          || "-help".equals(args[i])
          || "-version".equals(args[i]))
      {
        Log.warning("Long-form flags (e.g., -" + args[i]
            + ") must have two leading dashes.");
        printUsage();
        quit(STATUS_BAD_ARG);
      }
    }

    // Parse arguments.

    int c;
    while ((c = g.getopt()) != -1) {
      switch (c)
      {
      case 'd':
        Log.turnOnDebugLogging();
        break;

      case 'f':
        String arg = g.getOptarg();
        Log.note("User supplied -f arg: " + arg);
        if (arg != null) {
          fileToGraph = new VerifiableFile(arg);
        }
        break;

      case 'V':
        printVersion();
        quit(STATUS_NORMAL);
        break;

      case 'h':
      case '?':
      default:
        printUsage();
        quit(STATUS_NORMAL);
        break;
      }
    }

    // Record args given.  This needs to be done after parsing the args
    // because if --debug is given, we want to log the args via Log.note().

    if (args.length > 0)
    {
      String a = "";
      for (String arg : args) {
        a += arg + " ";
      }
      Log.note(Config.APP_NAME + " started with args: " + a);
    }
    else
    {
      Log.note(Config.APP_NAME + " started with no args");
    }

  }


  /**
   * Performs various initial sanity checks.
   */
  private void doSanityChecks()
  {
    // Check Java version.

    String v = System.getProperty("java.version");
    if (v.compareTo("1.5") < 0)
    {
      Log.note("Java version is " + v + ".");

      final String e = System.getProperty("line.separator");
      Dialog.error(null, "You are running Java version " + v + ","
          + e + "but the " + Config.APP_NAME
          + " needs version 1.5 or" + e
          + "later.  Please check your Java installation.",
          "Version of Java is too old");
      quit(STATUS_ERROR);
    }
  }


  /**
   * Starts the GUI.
   *
   * @param file Read and graph the given SBML file immediately upon
   * start up.
   */
  private void startGUI(File file)
  {
    Log.note("Starting GUI interface.");
    MainWindow mainWindow = new MainWindow(file);
    try
    {
      mainWindow.initAndShow();
    }
    catch (Exception e)
    {
      Log.error(e.getMessage());
    }

  }


  /**
   * Prints the usage text.
   **/
  private void printUsage()
  {
    final String e = System.getProperty("line.separator");

    String y=
        "Usage: java -jar sbmlbargraph.jar [options]" + e +
        e +
        "This is the " + Config.APP_NAME +
        ", version " + Version.asString() + "." + e +
        "[options] can be one or more of the following:" + e +
        "" + e +
        "-f, --file" + e +
        e +
        "  Graph the specified file immediately upon startup." + e +
        e +
        "-d, --debug" + e +
        e +
        "  Log detailed information to the console about what the program" + e +
        "  is doing.  The contents may be useful for debugging." + e +
        e +
        "-h, --help" + e +
        e +
        "  Display this help text, then exit." + e +
        e +
        "-V, --version" + e +
        e +
        "  Display the version number, then exit." + e +
        e +
        "For questions and other matters, please email us at" + e +
        "sbml-team@caltech.edu." + e;

    System.out.println(y);
    System.out.println();
  }


  /**
   * Prints the version number.
   **/
  private void printVersion()
  {
    System.out.println(Config.APP_NAME + " " + Version.asString());
  }


  //
  // ---------------------- Private data members ----------------------------
  //

  /** File passed to the --file command-line arg, if given by the user. */
  private VerifiableFile fileToGraph;
}
