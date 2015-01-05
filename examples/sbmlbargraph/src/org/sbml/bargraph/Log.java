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
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Class encapsulating all interactions with logs, both the terminal and
 * a file-based log. The system implemented here has 3 severity levels:
 * <ol>
 * <li><em>Note</em>: A severity of zero. Used for debugging and information.
 * <li><em>Warning</em>: A moderate severity level.  Used when something
 * unexpected happens and the event can be recovered from by the application.
 * <li>em>Error</em>: A serious severity level.  Used when something goes
 * seriously wrong.  The application may or may not be able to recover.
 * </ol>
 * @file    Log.java
 * @brief   Logging helper, for error, debugging and other log messages.
 * @author  Michael Hucka
 * @date    Created in 2012, based on code written for the SBML Test Suite.
 */
public class Log
{
  //
  // --------------------------- Public methods -----------------------------
  //

  /**
   * Reports a note for debugging or informational purposes.
   * 
   * @param msg The message to log.
   */
  public static void note(String msg)
  {
    if (logger == null) {
      initLog();
    }
    StackTraceElement caller = deduceCaller();
    logger.info("(" + caller.getClassName() + ") " + msg);
  }


  /**
   * Reports a warning, usually about something the user did.
   * 
   * @param msg The message to log.
   */
  public static void warning(String msg)
  {
    if (logger == null) {
      initLog();
    }
    StackTraceElement caller = deduceCaller();
    logger.warning("(" + caller.getClassName() + ") " + msg);
  }


  /**
   * Reports an error.
   *
   * @param msg The message to log.
   */
  public static void error(String msg)
  {
    if (logger == null) {
      initLog();
    }
    StackTraceElement caller = deduceCaller();
    logger.log(Level.SEVERE, "(" + caller.getClassName() + "."
        + caller.getMethodName() + " line "
        + caller.getLineNumber() + ") " + msg);
    logger.info("Configuration dump:");
    logger.info(Config.systemInfo());
  }


  /**
   * Reports an error with an exception.  Note that at the default logging
   * level, the details of the exception (such as its class and any message
   * included in it) will <em>not</em> be printed along with the @p msg.  To
   * get the details, the logging level has to be increased to the debugging
   * level.
   * 
   * @param msg A message to log.
   * @param thrown The exception object associated with this error
   */
  public static void error(String msg, Throwable thrown)
  {
    if (logger == null) {
      initLog();
    }

    StackTraceElement caller = deduceCaller();
    logger.log(Level.SEVERE, "(" + caller.getClassName() + "."
        + caller.getMethodName() + " line "
        + caller.getLineNumber() + ") " + msg, thrown);
    logger.info("Details of the exception are as follows:");
    StringWriter sw = new StringWriter();
    PrintWriter pw  = new PrintWriter(sw, true);
    thrown.printStackTrace(pw);
    logger.info(sw.toString());
    logger.info("Configuration dump:");
    logger.info(Config.systemInfo());
  }


  /**
   * Turns on debug-level logging, which prints normally hidden messages
   * to the console as well as the file log.
   *
   * @see #turnOffDebugLogging()
   */
  public static void turnOnDebugLogging()
  {
    if (logger == null) {
      initLog();
    }
    consoleLogger.setLevel(Level.ALL);
  }


  /**
   * Turns off debug-level logging.
   *
   * @see #turnOnDebugLogging()
   */
  public static void turnOffDebugLogging()
  {
    if (logger == null) {
      initLog();
    }
    consoleLogger.setLevel(APP_DEFAULT_CONSOLE_LOG_LEVEL);
  }


  /**
   * Main for testing only.
   * 
   * @param args Command-line arguments.
   */
  public static void main(String[] args)
  {
    // Default logging level => "note" won't show, but the others will.
    note("Log note test -- you should not see this");
    warning("Log warning test -- you should see this");
    error("Log error test -- you should see this");
    error("Log error + exception", new Exception("the exception's msg"));

    // Turn on the highest logging level => everything should show.
    turnOnDebugLogging();
    note("Log note at full debugging level, so you should see this");
    warning("Another Log warning test -- you should see this too");
    error("Another exception", new Exception("the exception's msg"));

    // Turn off debugging again, and make sure things are as expected.
    turnOffDebugLogging();
    note("Note at regular logging level again -- you shouldn't see this");
    warning("Warning at regular logging level; you should only see this");

    System.exit(0);
  }


  //
  // -------------------------- Private methods ----------------------------
  //

  /**
   * 
   */
  private static void initLog()
  {
    if (logger != null)
    {
      return;     // We've been here before.
    }

    logger = Logger.getLogger(APP_LOGGER_SUBSYSTEM);
    if (logger != null)
    {
      configConsoleLogger(logger);
      configFileLogger(logger);
    }
    else
    {
      // If we can't initialize, we have to bail now, because otherwise
      // some of our calling methods will probably get null exceptions
      // trying to invoke write().
      e("");
      e("Logging initialization failed; cannot continue.");
      e("Please report this problem to sbml-team@caltech.edu.");
      Main.quit(Main.STATUS_ERROR);
    }
  }


  /**
   * Set up a handler that sends output to the console for log messages
   * of level "warning".
   * @param lg
   */
  private static void configConsoleLogger(Logger lg)
  {
    consoleLogger= new ConsoleHandler();
    consoleLogger.setLevel(APP_DEFAULT_CONSOLE_LOG_LEVEL);
    consoleLogger.setFormatter(new LogFormatter(APP_PREFIX));
    lg.addHandler(consoleLogger);
  }


  /**
   * Set up a handler that sends output to the log file for status
   * messages.  We log most things -- you never know what's going to be
   * useful in the field.
   * @param lg
   */
  private static void configFileLogger(Logger lg)
  {
    boolean writingToLogFile = false;
    VerifiableFile logdir    = logDir();

    if (logdir != null)
    {
      try
      {
        fileLogger = new FileHandler(logdir + "/" + LOGFILE_NAME);
        fileLogger.setFormatter(new LogFormatter());
        fileLogger.setLevel(APP_DEFAULT_FILE_LOG_LEVEL);
        lg.addHandler(fileLogger);
        writingToLogFile = true;
      }
      catch (Exception ex)
      {
        e("");
        e("Error: unable to open log file "
            + logdir.toString() + File.separator + LOGFILE_NAME);
        e("Using fall-back of writing log to console error stream.");
        e("");
      }
    }

    // If we were able to open the log file, don't also forward
    // messages to the parent log handler, so that they don't get
    // printed to the console.  Just log them to the file.

    if (writingToLogFile)
    {
      try
      {
        lg.setUseParentHandlers(false);
      }
      catch (SecurityException ex)
      {
        lg.warning("Security exception when redirecting log output.");
        // Beyond writing a note, we can't do anything about this
        // failure, so just fall through and continue on.
      }
    }
  }


  /**
   * @return
   */
  private static VerifiableFile logDir()
  {
    String userHome = System.getProperty("user.home");
    VerifiableFile dir;

    // Try to create the data/log directory.
    // The location depends on the OS.

    if (Config.runningMac())
    {
      dir = new VerifiableFile(userHome + "/Library/Logs");
      if (dir.isVerifiedDirectory()) {
        return dir;
      } else {
        e("Unable to write log file in " + dir.toString());
      }
    }
    else if (Config.runningUnix())
    {
      dir = new VerifiableFile(userHome + Config.DATA_DIR_UNIX);
      if (dir.isVerifiedDirectory()) {
        return dir;
      } else
      {
        // Unlike the MacOS log directory, which is created by the
        // OS, our data directory on other systems is something we
        // create.  If this is the first time the user has ever run
        // SBML Bar Graph, it won't exist yet, so we have to create it.

        try
        {
          dir.mkdir();
          if (dir.isVerifiedDirectory()) {
            return dir;
          } else {
            e("Unable to write log file in " + dir.toString());
          }
        }
        catch (SecurityException ex)
        {
          e("Unable to create data directory " + dir.getPath());
        }
      }
    }
    else if (Config.runningWindows())
    {
      VerifiableFile homeDir = new VerifiableFile(userHome);
      String pathRoot        = userHome + File.separator;
      if (homeDir.isVerifiedDirectory())
      {
        if (Config.operatingSystem() == Config.OS_WIN8
            || Config.operatingSystem() == Config.OS_WIN7
            || Config.operatingSystem() == Config.OS_WINVISTA)
        {
          dir = new VerifiableFile(pathRoot + "AppData");
        }
        else
        {
          dir = new VerifiableFile(pathRoot + "Application Data");
        }

        try
        {
          dir.mkdir();
          dir = new VerifiableFile(dir, Config.DATA_DIR_WINDOWS);
          dir.mkdir();
          if (dir.isVerifiedDirectory()) {
            return dir;
          } else {
            e("Unable to write log file in " + dir.toString());
          }
        }
        catch (Exception ex)
        {
          e("Unable to create data directory " + dir.getPath());
        }
      }
      e(homeDir + " does not exist; unable to handle this.");
    }

    // If we get here, we couldn't create the expected directory.  This
    // is probably a bad sign.  Let's first try a fall-back of using a
    // system temporary directory.  Make the directory name unique to
    // the user's name in case we're on a multi-user system.

    dir = new VerifiableFile(System.getProperty("java.io.tempdir"),
      System.getProperty("user.name"));

    if (! dir.isVerifiedDirectory())
    {
      try
      {
        dir.mkdir();
        if (dir.isVerifiedDirectory()) {
          return dir;
        }
      }
      catch (SecurityException ex)
      {
        e("Unable to create fall-back log directory in "
            + dir.toString());
        e("Recovering by writing to standard output instead.");
      }
    }

    return null;
  }


  /**
   * @return
   */
  private static StackTraceElement deduceCaller()
  {
    final Throwable tmpThrowable    = new Throwable();
    final StackTraceElement[] stack = tmpThrowable.getStackTrace();
    return stack[2]; // Want the caller, not us.

  }


  /**
   * Short name for System.err.println(msg)
   * @param msg
   */
  private static void e(String msg)
  {
    System.err.println(msg);
  }

  //
  // ------------------------- Private variables ---------------------------
  //

  /** Our logger object. **/
  private static Logger logger;

  /** Our file log handler object. **/
  private static FileHandler fileLogger = null;

  /** Our file log handler object. **/
  private static ConsoleHandler consoleLogger = null;

  //
  // ------------------------- Private constants ---------------------------
  //

  /** The default name of the log file. **/
  private static final String LOGFILE_NAME = "sbmlbargraph.log";

  /** How we identify ourselves to the logger system. **/
  private static final String APP_LOGGER_SUBSYSTEM = "org.sbml.bargraph";

  /** Additional text prepended to console messages, for clarity. **/
  private static final String APP_PREFIX = "SBML Model Bargraph";

  /** Default level of logging for the console logger. **/
  private static final Level APP_DEFAULT_CONSOLE_LOG_LEVEL = Level.WARNING;

  /** Default level of logging for the file logger. **/
  private static final Level APP_DEFAULT_FILE_LOG_LEVEL = Level.INFO;
}
