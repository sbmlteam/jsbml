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

import java.text.*;
import java.util.*;
import java.util.logging.*;


/**
 * Log formatting object used by the Log class.
 * @file    LogFormatter.java
 * @brief   Class for formatting log messages.
 * @author  Michael Hucka
 * @date    Created in 2012, based on code written for the SBML Test Suite.
 */
public class LogFormatter
extends java.util.logging.Formatter
{
  //
  // --------------------------- Public methods -----------------------------
  //

  /**
   * @param msgPrefix
   */
  public LogFormatter(String msgPrefix)
  {
    this.msgPrefix = msgPrefix;
  }

  /**
   * 
   */
  public LogFormatter()
  {
    // Default has no prefix string.
  }

  /* (non-Javadoc)
   * @see java.util.logging.Formatter#format(java.util.logging.LogRecord)
   */
  @Override
  public String format(LogRecord rec)
  {
    String msg       = rec.getMessage();
    StringBuffer buf = new StringBuffer(msg.length() + 300);

    if (msgPrefix != null)
    {
      buf.append(msgPrefix);
      buf.append(' ');
    }

    buf.append(dateFormatter.format(new Date()));
    buf.append(' ');

    if (rec.getLevel().intValue() == Level.WARNING.intValue()) {
      buf.append("WARNING ");
    } else if (rec.getLevel().intValue() == Level.SEVERE.intValue()) {
      buf.append("ERROR ");
    }

    buf.append(formatMessage(rec));
    buf.append('\n');
    return buf.toString();
  }

  //
  // -------------------------- Private variables ---------------------------
  //

  /** Optional string that is written before every log message. **/
  private String msgPrefix;

  //
  // -------------------------- Private constants ---------------------------
  //

  /** A simple date formatter for the form "[2010-04-01 23:00:20]". **/
  private final SimpleDateFormat dateFormatter =
      new SimpleDateFormat("[yyyy-MM-dd kk:mm:ss]");
}
