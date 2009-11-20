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
