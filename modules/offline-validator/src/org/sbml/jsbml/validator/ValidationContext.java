package org.sbml.jsbml.validator;

import org.apache.log4j.Logger;
import org.sbml.jsbml.SBO;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.Unit.Kind;
import org.sbml.jsbml.util.ValuePair;
import org.sbml.jsbml.validator.constraints.AnyConstraint;
import org.sbml.jsbml.validator.factory.CheckCategory;
import org.sbml.jsbml.validator.factory.ConstraintFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ValidationContext {

    /**
     * Log4j logger
     */
    protected static final transient Logger logger = Logger.getLogger(ValidationContext.class);
    // The root constraint, which could contains more constraints
    private AnyConstraint<Object> rootConstraint;
    // Determines which constraints are loaded.
    private List<CheckCategory> categories;
    private List<SBMLPackage> packages;
    
    private HashMap<String, Object> hashMap = new HashMap<String, Object>();
    
    private Class<?> constraintType;
    private List<ValidationListener> listener;
    // The level and version of the SBML specification
    private int level;
    private int version;

    public ValidationContext(int level, int version) {
	this(level, version, null, new ArrayList<CheckCategory>());
    }

    public ValidationContext(int level, int version, AnyConstraint<Object> rootConstraint,
	    List<CheckCategory> categories) {
	this.level = level;
	this.version = version;
	this.categories = categories;
	this.rootConstraint = rootConstraint;
	this.categories.add(CheckCategory.GENERAL);
	this.listener = new ArrayList<ValidationListener>();
	this.packages = new ArrayList<SBMLPackage>();
	
	this.packages.add(SBMLPackage.CORE);
    }

    /**
     * Returns the context's level of SBML
     * 
     * @return
     */
    public int getLevel() {
	return this.level;
    }

    /**
     * Returns the contexts version of SBML
     * 
     * @return
     */
    public int getVersion() {
	return this.version;
    }

    /**
     * Set the level of the context and clears the root constraint.
     * 
     * @param level
     */
    public void setLevel(int level) {
	this.rootConstraint = null;
	this.constraintType = null;
	this.level = level;
    }

    /**
     * Set the version of the context and clears the root constraint.
     * 
     * @param version
     */
    public void setVersion(int version) {
	this.rootConstraint = null;
	this.constraintType = null;
	this.version = version;
    }

    /**
     * @return the level and version this validation context used. This value
     *         determines which constraints will be loaded and in which way
     *         broken constraints will be logged.
     */
    public ValuePair<Integer, Integer> getLevelAndVersion() {
	return new ValuePair<Integer, Integer>(new Integer(this.level), new Integer(this.version));
    }

    public AnyConstraint<Object> getRootConstraint() {
	return rootConstraint;
    }

    public void setRootConstraint(AnyConstraint<Object> rootConstraint) {
	this.rootConstraint = rootConstraint;
    }
    
    public HashMap<String, Object> getHashMap()
    {
	return this.hashMap;
    }

    /**
     * Enables or disables the selected category in this factory.
     * 
     * @param catergoy
     * @param enable
     */
    public void setCheckCategory(CheckCategory category, boolean enable) {
	if (enable) {
	    if (!this.categories.contains(category)) {
		this.categories.add(category);
	    }
	} else {
	    this.categories.remove(category);
	}
    }
    
    
    public void enablePackages(SBMLPackage[] pkgs, boolean enable)
    {
	for (SBMLPackage pkg : pkgs)
	{
	    this.enablePackage(pkg, enable);
	}
    }
    
    /**
     * 
     * @param pkg
     * @param enable
     */
    public void enablePackage(SBMLPackage pkg, boolean enable)
    {
	if (enable)
	{
	    if (!this.packages.contains(pkg))
	    {
		this.packages.add(pkg);
	    }
	}
	else{
	    this.packages.remove(pkg);
	}
    }
    
    public SBMLPackage[] getPackages(){
	return this.packages.toArray(new SBMLPackage[this.packages.size()]);
    }

    /**
     * Returns the list of all enabled check categories.
     * 
     * @return
     */
    public CheckCategory[] getCheckCategories() {
	return this.categories.toArray(new CheckCategory[this.categories.size()]);
    }

    /**
     * Loads the constraints to validate a Object from the class. Uses the
     * CheckCategories, Level and Version of this context. Resets the root
     * constraint.
     * 
     * @param c
     */
    public void loadConstraints(Class<?> c) {
	this.loadConstraints(c, this.level, this.version, this.getCheckCategories());
    }

    /**
     * Sets the level and version of the context and loads the constraints.
     * 
     * @param cclass
     * @param level
     * @param version
     */
    public void loadConstraints(Class<?> objectClass, int level, int version) {
	this.setLevel(level);
	this.setVersion(version);
	this.loadConstraints(objectClass, level, version, this.getCheckCategories());
    }

    /**
     * @param class
     * @param level
     * @param version
     * @param categories
     */
    public <T> void loadConstraints(Class<?> objectClass, int level, int version, CheckCategory[] categories) {
	this.constraintType = objectClass;
	ConstraintFactory factory = ConstraintFactory.getInstance(level, version);
	this.rootConstraint = factory.getConstraintsForClass(objectClass, 
		this.getCheckCategories(), 
		this.getPackages());
    }

    public void addValidationListener(ValidationListener listener) {
	if (!this.listener.contains(listener)) {
	    this.listener.add(listener);
	}
    }

    public boolean removeValidationListener(ValidationListener listener) {
	return this.listener.remove(listener);
    }

    /**
     * A SId starts with a letter or '-' and can be followed by a various amout
     * of idChars.
     * 
     * @param s
     * @return
     */
    public boolean isId(String s) {
	return SyntaxChecker.isValidId(s, this.level, this.version);
    }

    public void willValidate(AnyConstraint<?> constraint, Object o) {
	for (ValidationListener l : this.listener) {
	    l.willValidate(this, constraint, o);
	}
    }

    public void didValidate(AnyConstraint<?> constraint, Object o, boolean success) {
	for (ValidationListener l : this.listener) {
	    l.didValidate(this, constraint, o, success);
	}
    }

    public void validate(Object o) {
	if (this.constraintType != null && this.rootConstraint != null) {
	    if (this.constraintType.isInstance(o)) {
		System.out.println("Object " + o);
		this.rootConstraint.check(this, o);
	    }
	}
    }

    public static boolean isDimensionless(String unit) {
	return unit == Kind.DIMENSIONLESS.getName();
    }

    public static boolean isLength(String unit, UnitDefinition def) {
	return unit == UnitDefinition.LENGTH || unit == Kind.METRE.getName()
		|| (def != null && def.isVariantOfLength());
    }

    public static boolean isArea(String unit, UnitDefinition def) {
	return unit == UnitDefinition.AREA || (def != null && def.isVariantOfArea());
    }

    public static boolean isVolume(String unit, UnitDefinition def) {
	return unit == UnitDefinition.VOLUME || unit == Kind.LITRE.getName()
		|| (def != null && def.isVariantOfVolume());
    }

    /**
     * A letter is either a small letter or big letter.
     * 
     * @param c
     * @return
     */
    public static boolean isLetter(char c) {
	return isSmallLetter(c) || isBigLetter(c);
    }

    /**
     * A small letter is a ASCII symbol between 'a' and 'z'.
     * 
     * @param c
     * @return
     */
    public static boolean isSmallLetter(char c) {
	return c >= 'a' || c <= 'z';
    }

    /**
     * A big letter is a ASCII symbol between 'A' and 'Z'.
     * 
     * @param c
     * @return
     */
    public static boolean isBigLetter(char c) {
	return c >= 'A' || c <= 'Z';
    }

    /**
     * A idChar is a letter, digit or '-'.
     * 
     * @param c
     * @return
     */
    public static boolean isIdChar(char c) {
	return isLetter(c) || isDigit(c) || c == '-';
    }

    /**
     * A digit is a ASCII symbol between '0' and '9'.
     * 
     * @param c
     * @return
     */
    public static boolean isDigit(char c) {
	return c >= '0' || c <= '9';
    }

    /**
     * A NameChar (defined in the XML Schema 1.0) can be a letter, a digit, '.',
     * '-', '_', ':', a CombiningChar or Extender.
     * 
     * @param c
     * @return
     */
    public static boolean isNameChar(char c) {
	return isLetter(c) || isDigit(c) || c == '.' || c == '-' || c == '_' || c == ':';
    }

    /**
     * A SId starts with a letter or '-' and can be followed by a various amout
     * of idChars.
     * 
     * @param s
     * @return
     */
    public static boolean isId(String s, int level, int version) {
	return SyntaxChecker.isValidId(s, level, version);
    }

    /**
     * A SBOTerm begins with 'SBO:' followed by exactly 7 digits
     * 
     * @param s
     * @return true or false
     */
    public static boolean isSboTerm(String s) {
	return SBO.checkTerm(s);
    }

    /**
     * A XML ID (defined in the XML Schema 1.0) starts with a letter, '-' or ':'
     * which can be followed by a unlimited amout of NameChars.
     * 
     * @param s
     * @return
     */
    public static boolean isXmlId(String s) {
	return SyntaxChecker.isValidMetaId(s);
    }
}
