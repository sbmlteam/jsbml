package org.sbml.jsbml.validator;

import java.util.ArrayList;
import java.util.List;

import org.sbml.jsbml.SBMLErrorLog;
import org.sbml.jsbml.validator.constraint.AnyConstraint;
import org.sbml.jsbml.validator.factory.CheckCategory;

public class LoggingValidationContext extends ValidationContext implements ValidationListener {

    private SBMLErrorLog log;
    
    public LoggingValidationContext(int level, int version) {
	this(level, version, null, new ArrayList<CheckCategory>());
	// TODO Auto-generated constructor stub
    }
    
    public LoggingValidationContext(int level, int version, AnyConstraint<Object> rootConstraint,
	    List<CheckCategory> categories) {
	super(level, version, rootConstraint, categories);
	log = new SBMLErrorLog();
    }
    
    public void clearLog()
    {
	this.log.clearLog();
    }
    
    public SBMLErrorLog getLog()
    {
	return this.log;
    }
    
    private void logFailure(int id)
    {
	System.out.println("Constraint " + id + " broken!");
    }
    

    @Override
    public void willValidate(ValidationContext ctx, AnyConstraint<?> c, Object o) {
	// TODO Auto-generated method stub

    }
    
   
    @Override
    public void didValidate(ValidationContext ctx, AnyConstraint<?> c, Object o, boolean success) {
	if (!success)
	{
	    logFailure(c.getId());
	}
    }

}
