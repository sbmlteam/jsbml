/*
 * $Id: XMLLogger.java 38 2009-12-14 15:50:38Z marine3 $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/xml/XMLLogger.java $
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

package org.sbml.jsbml.xml;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;


public class XMLLogger {

	
	 // logger
    private Logger logger = Logger.getLogger(XMLLogger.class);
    
    public void debug(String message) {
    	logger.debug(message);
    }
    
    
    public void error(String message) {
    	logger.error(message);
    }
    
    public void fatal(String message) {
    	logger.fatal(message);
    }
    
    public void info(String message) {
    	logger.info(message);
    }
    
    public void log(Priority priority, String message) {
    	logger.log(priority, message);
    }
    
}
