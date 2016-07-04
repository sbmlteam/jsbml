package org.sbml.jsbml.validator.offline;

import java.util.ArrayList;
import java.util.List;

import org.sbml.jsbml.SBMLError;
import org.sbml.jsbml.SBMLErrorLog;
import org.sbml.jsbml.validator.offline.constraints.AnyConstraint;
import org.sbml.jsbml.validator.offline.factory.CheckCategory;
import org.sbml.jsbml.validator.offline.factory.SBMLErrorFactory;

public class LoggingValidationContext extends ValidationContext
  implements ValidationListener {

  private SBMLErrorLog log;


  public LoggingValidationContext(int level, int version) {
    this(level, version, null, new ArrayList<CheckCategory>());
    this.addValidationListener(this);
    // TODO Auto-generated constructor stub
  }


  public LoggingValidationContext(int level, int version,
    AnyConstraint<Object> rootConstraint, List<CheckCategory> categories) {
    super(level, version, rootConstraint, categories);
    log = new SBMLErrorLog();
  }


  public void clearLog() {
    this.log.clearLog();
  }


  public SBMLErrorLog getLog() {
    return this.log;
  }


  private void logFailure(int id) {
    logger.debug("Constraint " + id + " is broken!");
    SBMLError e =
      SBMLErrorFactory.createError(id, this.getLevel(), this.getVersion());
    if (e != null) {
      this.log.add(e);
    } else {
      logger.warn("Couldn't load SBMLError for error code " + id);
    }
  }


  @Override
  public void willValidate(ValidationContext ctx, AnyConstraint<?> c,
    Object o) {
    // TODO Auto-generated method stub
  }


  @Override
  public void didValidate(ValidationContext ctx, AnyConstraint<?> c, Object o,
    boolean success) {
    // System.out.println("Checked " + c.getId());
    if (!success) {
      logFailure(c.getId());
    }
  }
}
