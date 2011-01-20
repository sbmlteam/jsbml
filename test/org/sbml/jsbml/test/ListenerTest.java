/*
 * $Id$
 * $URL$
 *
 * 
 *==================================================================================
 * Copyright (c) 2009 The jsbml team.
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
package org.sbml.jsbml.test;

import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBO;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.SBaseChangedEvent;
import org.sbml.jsbml.SBaseChangedListener;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.CVTerm.Qualifier;
import org.sbml.jsbml.xml.stax.SBMLWriter;

/**
 * @author Andreas Dr&auml;ger
 * @date 2010-11-14
 */
public class ListenerTest implements SBaseChangedListener {

	public ListenerTest() {
		SBMLDocument doc = new SBMLDocument(1, 2);
		doc.addChangeListener(this);
		Model model = doc.createModel("test_model");
		Parameter p1 = model.createParameter("p1");
		p1.setId("p2");
		model.removeParameter(p1);

		Compartment c = model.createCompartment("c");
		c.setSize(4.3);
		c.setSBOTerm(SBO.getPhysicalCompartment());

		Species s1 = model.createSpecies("s1", c);
		s1.addCVTerm(new CVTerm(CVTerm.Type.BIOLOGICAL_QUALIFIER,
				Qualifier.BQB_IS, "urn:miriam:kegg.compound:C12345"));
		s1.setValue(23.7);
		model.removeSpecies(s1);

		try {
			System.out.println();
			SBMLWriter.write(doc, System.out);
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ListenerTest();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBaseChangedListener#sbaseAdded(org.sbml.jsbml.SBase)
	 */
	public void sbaseAdded(SBase sb) {
		System.out.printf("Added:\t%s\n", sb);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.SBaseChangedListener#sbaseRemoved(org.sbml.jsbml.SBase)
	 */
	public void sbaseRemoved(SBase sb) {
		System.out.printf("Removed:\t%s\n", sb);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.sbml.jsbml.SBaseChangedListener#stateChanged(org.sbml.jsbml.
	 * SBaseChangedEvent)
	 */
	public void stateChanged(SBaseChangedEvent ev) {
		System.out.printf("Change:\t%s\n", ev.toString());
	}

}
