package org.sbml.jsbml.ext.dyn;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.sbml.jsbml.util.ResourceManager;

/**
 * Contains some constants related to the Dynamic Structures package.
 * 
 * @author Harold Gomez
 * @since 1.0
 */
public class DynConstants {

	/**
	 * The namespace URI of this parser for SBML level 3, version 1 and package
	 * version 1.
	 */
	public static final String namespaceURI_L3V1V1 = "http://www.sbml.org/sbml/level3/version1/dyn/version1";

	/**
	 * The latest namespace URI of this parser, this value can change between
	 * releases.
	 */
	public static final String namespaceURI = namespaceURI_L3V1V1;

	public static final ResourceBundle bundle = ResourceManager
			.getBundle("org.sbml.jsbml.ext.dyn.Messages");

	public static final String shortLabel = "dyn";

	public static final int MIN_SBML_LEVEL = 3;
	public static final int MIN_SBML_VERSION = 1;
	public static final int PACKAGE_VERSION=1;

	public static final List<String> namespaces;

	static {
		namespaces = new ArrayList<String>();
		namespaces.add(namespaceURI_L3V1V1);
	}

	public static final String cboTerm = "cboTerm";

	public static final String applyToAll = "applyToAll";
	public static final String dynElement = "dynElement";
	public static final String idRef = "idRef";
	public static final String metaIdRef = "metaIdRef";
	public static final String spatialComponent = "spatialComponent";
	public static final String spatialIndex = "spatialIndex";
	public static final String variable = "variable";

	public static final String listOfDynElements = "listOfDynElements";
	public static final String listOfSpatialComponents = "listOfSpatialComponents";

	public static String packageName = "Dynamic Structures";

	/**
	 * @param level
	 * @param version
	 * @return
	 */
	public static String getNamespaceURI(int level, int version) {
		return namespaceURI;
	}

}
