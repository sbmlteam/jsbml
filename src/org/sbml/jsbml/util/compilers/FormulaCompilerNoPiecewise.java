package org.sbml.jsbml.util.compilers;

import java.util.HashMap;
import java.util.List;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.SBMLException;

/**
 * Produces an infix formula like {@link FormulaCompiler} but removes all the piecewise
 * functions. They are replaced by an id that is unique if you are using the same {@link FormulaCompilerNoPiecewise} instance.
 * The content of the piecewise function is put in a {@link HashMap} and is transformed to use if/then/else.
 * 
 * This class is used for example to create an SBML2XPP converter where (in XPP) the piecewise operator is not supported.
 * 
 * @author rodrigue
 *
 */
public class FormulaCompilerNoPiecewise extends FormulaCompiler {

	private HashMap<String, String> piecewiseMap = new HashMap<String, String>();
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.util.compilers.ASTNodeCompiler#piecewise(java.util.List)
	 */
	public ASTNodeValue piecewise(List<ASTNode> nodes) throws SBMLException {
		
		// TODO : create the piecewise output with if/then/else
		// We need to compile each nodes, in case they contain some other piecewise
		String piecewiseStr = "";
		
		int nbChildren = nodes.size();
		int nbIfThen = nbChildren / 2;
		boolean otherwise = (nbChildren % 2) == 1;

		for (int i = 0; i < nbIfThen; i++) {
			int index = i * 2;
			piecewiseStr += "if (" + nodes.get(index + 1).compile(this).toString() + ") then (" + nodes.get(index).compile(this).toString() + ") else ";
		}
		
		if (otherwise) {
			piecewiseStr += "(" + nodes.get(nbChildren - 1).compile(this).toString() + ")";
		}
		
		/*
		for (ASTNode astNode : nodes) {
			piecewiseStr += "(" + astNode.compile(this).toString() + ") ite ";
		}
		*/
		
		// get a unique identifier for the piecewise expression in this compiler.
		int id = piecewiseMap.size() + 1;
		String piecewiseId = "piecew" + id;
		
		// Adding the piecewise to the list of piecewise
		piecewiseMap.put(piecewiseId, piecewiseStr);
		
		return new ASTNodeValue(" " + piecewiseId + " ", this);
	}

	
	public HashMap<String, String> getPiecewiseMap() {
		return piecewiseMap;
	}
	
}
