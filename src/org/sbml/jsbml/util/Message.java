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

package org.sbml.jsbml.util;

/**
 *
 */
public class Message {

	/**
	 * 
	 */
	private String lang;
	/**
	 * 
	 */
	private String message;
	
	/**
	 * 
	 */
	public Message() {
	}

	/**
	 * 
	 * @return
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * 
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 
	 * @param lang
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * 
	 * @param messageContent
	 */
	public void setMessage(String messageContent) {
		this.message = messageContent;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return StringTools.concat("Message [lang=", lang, ", messageContent=",
				message, "]").toString();
	}
	
}
