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

package org.sbml.jsbml.sbmlExtensions.layout;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.SBase;

public class TextGlyph extends AbstractNamedSBase {

	private GraphicalObject graphicalObject;
	private String text;
	private String originOfText;
	
	public TextGlyph() {
		
	}
	
	public TextGlyph(int level, int version) {
		super(level, version);
	}

	
	public GraphicalObject getGraphicalObject() {
		return graphicalObject;
	}


	public void setGraphicalObject(GraphicalObject graphicalObject) {
		this.graphicalObject = graphicalObject;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	public String getOriginOfText() {
		return originOfText;
	}


	public void setOriginOfText(String originOfText) {
		this.originOfText = originOfText;
	}


	@Override
	public SBase clone() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
