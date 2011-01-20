/*
 * $Id$
 * $URL$
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
package org.sbml.jsbml.xml.test;

import javax.xml.stream.XMLStreamException;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.xml.stax.SBMLWriter;

/**
 * 
 * @author Andreas Dr&auml;ger
 * 
 */
public class MathMLTest {

	/**
	 * 
	 */
	public MathMLTest() {
		int level = 3;
		int version = 1;
		SBMLDocument doc = new SBMLDocument(level, 1);
		Model m = doc.createModel("id");
		FunctionDefinition fd = m.createFunctionDefinition("fd");
		ASTNode math = new ASTNode(Type.LAMBDA, fd);
		math.addChild(new ASTNode("x", fd));
		ASTNode pieces = ASTNode.piecewise(new ASTNode(3, fd), ASTNode.lt("x",
				ASTNode.abs(Double.NEGATIVE_INFINITY, fd)), ASTNode.times(
				new ASTNode(5.3, fd), ASTNode.log(new ASTNode(8, fd))));
		pieces = ASTNode.times(pieces, ASTNode.root(new ASTNode(2, fd),
				new ASTNode(16, fd)));
		math.addChild(pieces);
		fd.setMath(math);
		System.out.println(math.toMathML());
		
		Species species = m.createSpecies("spec");
		Reaction r = m.createReaction("r");
		r.addReactant(new SpeciesReference(species));
		KineticLaw kl = new KineticLaw(level, version);
		math = new ASTNode(fd, kl);
		math.addChild(new ASTNode(species, kl));
		math = ASTNode.times(math, new ASTNode(3.7, 8, kl));
		kl.setMath(math);
		r.setKineticLaw(kl);
		
		System.out.println(math.toMathML());

		try {
			SBMLWriter.write(doc, System.out);
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (SBMLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public static void main(String args[]) {
		new MathMLTest();
	}

}
