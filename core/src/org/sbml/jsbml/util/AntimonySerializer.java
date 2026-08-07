package org.sbml.jsbml.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.RateRule;
import org.sbml.jsbml.AlgebraicRule;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.FunctionDefinition;

/**
 * Utility class to serialize SBML models and components into the Antimony scripting language.
 * This provides a token-efficient, human-readable format optimized for Large Language Models (LLMs)
 * and bidirectional text-editor plugins.
 * 
 * @author Deepak Yadav
 */
public class AntimonySerializer implements AntimonyConstants {

    /**
     * Keyword Mapping Table for the Abstraction Layer.
     * All keys MUST be lowercase for safe matching.
     */
    private static final Map<String, String> ATTRIBUTE_MAP = new HashMap<>();
    static {
        // Core mapped attributes
        ATTRIBUTE_MAP.put("size", ""); // Direct assignment
        ATTRIBUTE_MAP.put("value", ""); // Direct assignment
        
        // Internal JSBML metadata and aliases to explicitly ignore
        ATTRIBUTE_MAP.put("volume", "IGNORE");
        ATTRIBUTE_MAP.put("version", "IGNORE");
        ATTRIBUTE_MAP.put("level", "IGNORE");
        ATTRIBUTE_MAP.put("levelandversion", "IGNORE");
        ATTRIBUTE_MAP.put("parentsbmlobject", "IGNORE");
        ATTRIBUTE_MAP.put("parent", "IGNORE");
        ATTRIBUTE_MAP.put("constant", "IGNORE");
        ATTRIBUTE_MAP.put("spatialdimensions", "IGNORE");
        ATTRIBUTE_MAP.put("units", "IGNORE");
        ATTRIBUTE_MAP.put("metaid", "IGNORE");
        ATTRIBUTE_MAP.put("sboterm", "IGNORE");
        ATTRIBUTE_MAP.put("notes", "IGNORE");
        ATTRIBUTE_MAP.put("annotation", "IGNORE");
        ATTRIBUTE_MAP.put("name", "IGNORE"); 
        ATTRIBUTE_MAP.put("model", "IGNORE");
    }

    /**
     * Generic router for UI plugins (e.g., Eclipse, IntelliJ, VSCode). 
     * Allows dynamic serialization of any selected SBML component without knowing its specific type.
     * 
     * @param element The generic SBase element to serialize.
     * @return An Antimony-formatted string, or a comment if the element is unsupported/null.
     */
    public static String toAntimony(SBase element) {
        if (element == null) return "// Error: Element is null.";
        
        if (element instanceof Model) {
            return toAntimony((Model) element);
        } else if (element instanceof Compartment) {
            return toAntimony((Compartment) element);
        } else if (element instanceof Species) {
            return toAntimony((Species) element);
        } else if (element instanceof Reaction) {
            return toAntimony((Reaction) element);
        } else if (element instanceof Rule) {
            return toAntimony((Rule) element);
        } else if (element instanceof Event) {
            return toAntimony((Event) element);
        } else if (element instanceof Parameter) {
            return toAntimony((Parameter) element);
        } else if (element instanceof FunctionDefinition) {
            return toAntimony((FunctionDefinition) element);
        }
        
        return "// Unsupported SBML component for Antimony serialization.";
    }

    /**
     * Converts an entire SBML Model into a basic Antimony script string.
     * @param model The SBML Model to serialize.
     * @return An Antimony-formatted string representation of the model.
     */
    public static String toAntimony(Model model) {
        if (model == null) return "// Error: Model is null.";

        StringBuilder ant = new StringBuilder();
        
        String modelName = model.isSetName() ? model.getName() : model.getId();
        ant.append(MODEL).append(" ").append(modelName).append("()\n\n");

        ant.append("  // Compartments\n");
        for (Compartment c : model.getListOfCompartments()) {
            ant.append("  ").append(toAntimony(c)).append("\n");
        }
        ant.append("\n");

        ant.append("  // Species\n");
        for (Species s : model.getListOfSpecies()) {
            ant.append("  ").append(toAntimony(s)).append("\n");
        }
        ant.append("\n");

        ant.append("  // Reactions\n");
        for (Reaction r : model.getListOfReactions()) {
            ant.append("  ").append(toAntimony(r)).append("\n");
        }
        ant.append("\n");

        ant.append("  // Rules\n");
        for (Rule r : model.getListOfRules()) {
            ant.append("  ").append(toAntimony(r)).append("\n");
        }
        ant.append("\n");

        ant.append("  // Events\n");
        for (Event e : model.getListOfEvents()) {
            ant.append("  ").append(toAntimony(e)).append("\n");
        }
        ant.append("\n");

        ant.append("  // Parameters\n");
        for (Parameter p : model.getListOfParameters()) {
            ant.append("  ").append(toAntimony(p)).append("\n");
        }
        ant.append("\n");

        ant.append("  // Function Definitions\n");
        for (FunctionDefinition fd : model.getListOfFunctionDefinitions()) {
            ant.append("  ").append(toAntimony(fd)).append("\n");
        }
        ant.append("\n");

        ant.append(END).append("\n");

        return ant.toString();
    }

    /**
     * Base Abstraction Layer: Dynamically extracts and formats attributes for flat components.
     * Uses Java reflection to find set attributes and translate them to Antimony syntax.
     */
    private static String serializeFlatComponent(SBase element, String antimonyKeyword) {
        if (element == null) return "";
        
        StringBuilder ant = new StringBuilder();
        
        // Use a Set to track and prevent duplicate values from being printed
        java.util.Set<String> printedValues = new java.util.HashSet<>();
        
        // 1. Component declaration
        if (antimonyKeyword != null && !antimonyKeyword.isEmpty()) {
            ant.append(antimonyKeyword).append(" ");
        }
        
        // Ensure we have an ID
        String id = "unknown_id";
        try {
            Method getIdMethod = element.getClass().getMethod("getId");
            id = (String) getIdMethod.invoke(element);
        } catch (Exception e) {
            // Fallback if no ID is found
        }
        ant.append(id);
        printedValues.add(id); // Prevent ID from being printed again as a property

        // 2. Reflection loop to find dynamically set attributes
        try {
            Method[] methods = element.getClass().getMethods();
            for (Method method : methods) {
                String methodName = method.getName();
                
                // Look for zero-argument isSet methods (excluding id and name)
                if (methodName.startsWith("isSet") && 
                    method.getParameterTypes().length == 0 &&
                    !methodName.equals("isSetId") && 
                    !methodName.equals("isSetName")) {
                    
                    try {
                        boolean isSet = (Boolean) method.invoke(element);
                        if (isSet) {
                            String rawAttributeName = methodName.substring(5); // Remove "isSet"
                            String lowerAttributeName = rawAttributeName.toLowerCase();
                            
                            Method getMethod = element.getClass().getMethod("get" + rawAttributeName);
                            Object valueObj = getMethod.invoke(element);
                            
                            if (valueObj == null) continue;
                            String valueStr = valueObj.toString();
                            
                            // Map the attribute name for Antimony
                            String mappedAttribute = ATTRIBUTE_MAP.getOrDefault(lowerAttributeName, lowerAttributeName);
                            
                            // Skip internal metadata
                            if ("IGNORE".equals(mappedAttribute)) {
                                continue;
                            }
                            
                            // NEW: Skip if we have already printed this exact value (prevents aliases)
                            if (printedValues.contains(valueStr)) {
                                continue;
                            }
                            
                            // Format the output
                            if (mappedAttribute.isEmpty()) {
                                ant.append(" = ").append(valueStr); // Direct assignment
                            } else {
                                ant.append(" ").append(mappedAttribute).append(" ").append(valueStr);
                            }
                            
                            // Mark this value as printed
                            printedValues.add(valueStr);
                        }
                    } catch (NoSuchMethodException e) {
                        continue;
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            return "// Error during reflection serialization: " + e.getMessage();
        }

        ant.append(";");
        return ant.toString();
    }

    /**
     * Converts an individual SBML Compartment into an Antimony string using abstraction.
     */
    public static String toAntimony(Compartment c) {
        return serializeFlatComponent(c, COMPARTMENT);
    }

    /**
     * Converts an individual SBML Species into an Antimony string.
     * Implements rigorous checking for substance units and boundary conditions.
     */
    public static String toAntimony(Species s) {
        if (s == null) return "";
        StringBuilder ant = new StringBuilder();

        boolean hOSU = s.getHasOnlySubstanceUnits();
        boolean boundary = s.getBoundaryCondition();

        // 1. Handle Substance Units
        if (hOSU) {
            ant.append(SUBSTANCE_ONLY).append(" ").append(SPECIES).append(" ");
        } else {
            ant.append(SPECIES).append(" ");
        }

        // 2. Handle Boundary Condition
        if (boundary) {
            ant.append("$");
        }

        ant.append(s.getId());

        // Compartment assignment
        if (s.isSetCompartment()) {
            ant.append(" ").append(IN).append(" ").append(s.getCompartment());
        }

        // 3. Handle Initial Values based on Concentration vs Amount assumptions
        String comp = s.isSetCompartment() ? s.getCompartment() : "1";

        if (hOSU) {
            if (s.isSetInitialAmount()) {
                ant.append(" = ").append(s.getInitialAmount());
            } else if (s.isSetInitialConcentration()) {
                ant.append(" = ").append(s.getInitialConcentration()).append(" * ").append(comp);
            }
        } else {
            if (s.isSetInitialAmount()) {
                ant.append(" = ").append(s.getInitialAmount()).append(" / ").append(comp);
            } else if (s.isSetInitialConcentration()) {
                ant.append(" = ").append(s.getInitialConcentration());
            }
        }

        ant.append(";");
        return ant.toString();
    }

    /**
     * Converts an individual SBML Reaction into an Antimony string.
     * Handles reactants, products, named stoichiometry, reversibility, and kinetic laws.
     */
    public static String toAntimony(Reaction r) {
        if (r == null) return "";
        StringBuilder ant = new StringBuilder();

        // 1. Reaction ID
        ant.append(r.getId()).append(": ");

        // 2. Reactants
        for (int i = 0; i < r.getReactantCount(); i++) {
            SpeciesReference sr = r.getReactant(i);
            
            // Check for Named Stoichiometry (e.g., 'n S2')
            if (sr.isSetId()) {
                ant.append(sr.getId()).append(" ");
            } else if (sr.isSetStoichiometry() && sr.getStoichiometry() != 1d) {
                // Formatting to remove trailing zeros for clean output (e.g. 2.0 -> 2)
                ant.append(sr.getStoichiometry() == (long) sr.getStoichiometry() ? 
                           String.format("%d", (long)sr.getStoichiometry()) : 
                           String.format("%s", sr.getStoichiometry())).append(" ");
            }
            ant.append(sr.getSpecies());
            if (i < r.getReactantCount() - 1) ant.append(" + ");
        }

        // 3. Reversibility (Antimony uses -> for reversible, => for irreversible)
        if (r.isSetReversible() && !r.getReversible()) {
            ant.append(" ").append(IRREVERSIBLE).append(" ");
        } else {
            ant.append(" ").append(REVERSIBLE).append(" ");
        }

        // 4. Products
        for (int i = 0; i < r.getProductCount(); i++) {
            SpeciesReference sr = r.getProduct(i);
            
            if (sr.isSetId()) {
                ant.append(sr.getId()).append(" ");
            } else if (sr.isSetStoichiometry() && sr.getStoichiometry() != 1d) {
                ant.append(sr.getStoichiometry() == (long) sr.getStoichiometry() ? 
                           String.format("%d", (long)sr.getStoichiometry()) : 
                           String.format("%s", sr.getStoichiometry())).append(" ");
            }
            ant.append(sr.getSpecies());
            if (i < r.getProductCount() - 1) ant.append(" + ");
        }

        // 5. Kinetic Law
        if (r.isSetKineticLaw()) {
            KineticLaw kl = r.getKineticLaw();
            if (kl.isSetMath()) {
                // Convert ASTNode to a math string
                ant.append("; ").append(ASTNode.formulaToString(kl.getMath()));
            }
        }
        
        ant.append(";");
        return ant.toString();
    }

    /**
     * Converts an SBML Rule (Assignment, Rate, or Algebraic) into an Antimony string.
     */
    public static String toAntimony(Rule r) {
        if (r == null || !r.isSetMath()) return "";
        
        String math = ASTNode.formulaToString(r.getMath());
        
        if (r instanceof AssignmentRule) {
            return ((AssignmentRule) r).getVariable() + " " + ASSIGNMENT + " " + math + ";";
        } else if (r instanceof RateRule) {
            return ((RateRule) r).getVariable() + RATE + " = " + math + ";";
        } else if (r instanceof AlgebraicRule) {
            return ALGEBRAIC + " = " + math + ";";
        }
        
        return "// Unsupported Rule type.";
    }

    /**
     * Converts an SBML Event into an Antimony string.
     * Supports advanced options including delays, priorities, t0, and persistence.
     */
    public static String toAntimony(Event e) {
        if (e == null) return "";
        StringBuilder ant = new StringBuilder();

        if (e.isSetId()) {
            ant.append(e.getId()).append(": ");
        }

        ant.append(AT).append(" ");
        boolean hasTrigger = e.isSetTrigger() && e.getTrigger().isSetMath();
        boolean hasDelay = e.isSetDelay() && e.getDelay().isSetMath();

        if (hasDelay && hasTrigger) {
            ant.append(ASTNode.formulaToString(e.getDelay().getMath()));
            ant.append(" ").append(AFTER).append(" ");
            ant.append(ASTNode.formulaToString(e.getTrigger().getMath()));
        } else if (hasTrigger) {
            ant.append(ASTNode.formulaToString(e.getTrigger().getMath()));
        }

        // Advanced Event Options
        if (e.isSetPriority() && e.getPriority().isSetMath()) {
            ant.append(", ").append(PRIORITY).append(" = ").append(ASTNode.formulaToString(e.getPriority().getMath()));
        }
        if (e.isSetTrigger()) {
            org.sbml.jsbml.Trigger t = e.getTrigger();
            if (t.isSetInitialValue() && !t.getInitialValue()) {
                ant.append(", ").append(T0_FALSE).append(" = false");
            }
            if (t.isSetPersistent() && !t.getPersistent()) {
                ant.append(", ").append(PERSISTENT_FALSE).append(" = false");
            }
        }
        ant.append(": ");

        int count = e.getEventAssignmentCount();
        for (int i = 0; i < count; i++) {
            EventAssignment ea = e.getEventAssignment(i);
            ant.append(ea.getVariable()).append(" = ");
            if (ea.isSetMath()) {
                ant.append(ASTNode.formulaToString(ea.getMath()));
            }
            if (i < count - 1) ant.append(", ");
        }

        ant.append(";");
        return ant.toString();
    }

    /**
     * Converts an individual SBML Parameter into an Antimony string using abstraction.
     */
    public static String toAntimony(Parameter p) {
        return serializeFlatComponent(p, ""); // Parameters don't use a leading keyword in Antimony
    }

    /**
     * Converts an individual SBML FunctionDefinition into an Antimony string.
     */
    public static String toAntimony(FunctionDefinition fd) {
        if (fd == null || !fd.isSetMath()) return "";
        StringBuilder ant = new StringBuilder();
        
        ant.append("function ").append(fd.getId()).append("(");
        
        ASTNode math = fd.getMath();
        if (math.isLambda()) {
            // In JSBML, all children except the last one are the bound variables (bvars)
            int numBvars = math.getChildCount() - 1; 
            
            for (int i = 0; i < numBvars; i++) {
                // Fetch the child node directly
                ant.append(math.getChild(i).getName());
                if (i < numBvars - 1) {
                    ant.append(", ");
                }
            }
            ant.append(")\n  ");
            
            // The last child is the actual math body of the function
            if (math.getChildCount() > 0) {
                ASTNode body = math.getChild(math.getChildCount() - 1);
                ant.append(ASTNode.formulaToString(body));
            }
        }
        
        ant.append("\n").append(END).append("\n");
        return ant.toString();
    }
}