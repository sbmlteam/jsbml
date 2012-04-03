// @file    SBMLStats.java
// @brief   Object representing statistics about a given SBML file.
// @author  Michael Hucka
// @date    Created in 2012
//
//<!---------------------------------------------------------------------------
// This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
// for the latest version of JSBML and more information about SBML. 
// 
// Copyright (C) 2009-2012 jointly by the following organizations: 
// 1. The University of Tuebingen, Germany 
// 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK 
// 3. The California Institute of Technology, Pasadena, CA, USA 
// 
// This library is free software; you can redistribute it and/or modify it 
// under the terms of the GNU Lesser General Public License as published by 
// the Free Software Foundation. A copy of the license agreement is provided 
// in the file named "LICENSE.txt" included with this software distribution 
// and also available online as <http://sbml.org/Software/JSBML/License>. 
//------------------------------------------------------------------------- -->

package org.sbml.bargraph;

import java.io.File;
import org.sbml.jsbml.*;


/**
 * Class encapsulating reading and analyzing the statistics of a single
 * SBML file.
 */
public class SBMLStats
{
    // 
    // --------------------------- Public methods ----------------------------- 
    // 

    /**
     * Constructor; reads the given file and counts up the SBML components
     * inside of it.
     * 
     * @param file The file to read and analyze
     * 
     * @throws javax.xml.stream.XMLStreamException If an error occurs in
     * attempting to parse the XML content of the file.
     *
     * @throws java.io.IOException If an error occurs while trying to read
     * the file.
     */
    public SBMLStats(File file)
        throws javax.xml.stream.XMLStreamException, java.io.IOException
    {
        this.file = file;
        
        // Parse the SBML and store the results.  Exceptions are bubbled up
        // to the caller.

        SBMLReader reader = new SBMLReader();
        Log.note("Parsing SBML file '" + file.getPath() + "'");
        this.sbmlDoc = reader.readSBML(file);

        // Count the different components.
        // First, do components that are common to all SBML files.
        
        Model model        = sbmlDoc.getModel();
        this.level         = model.getLevel();
        this.version       = model.getVersion();
        numSpecies         = model.getListOfSpecies().getChildCount();
        numCompartments    = model.getListOfCompartments().getChildCount();
        numReactions       = model.getListOfReactions().getChildCount();
        numParameters      = model.getListOfParameters().getChildCount();
        numRules           = model.getListOfRules().getChildCount();
        numUnitDefinitions = model.getListOfUnitDefinitions().getChildCount();
        
        // Now, components that vary based on Level+Version:
        
        if (level >= 2)
        {
            numFunctionDefinitions = model.getListOfFunctionDefinitions().getChildCount();
            numEvents = model.getListOfEvents().getChildCount();

            if (version >= 2)
            {
                numInitialAssignments = model.getListOfInitialAssignments().getChildCount();
                numConstraints        = model.getListOfConstraints().getChildCount();
            }

            if (version >= 2 && level < 3)
            {
                numSpeciesTypes     = model.getListOfSpeciesTypes().getChildCount();
                numCompartmentTypes = model.getListOfCompartmentTypes().getChildCount();
            }
        }
    }

    /**
     * Empty constructor.
     */
    public SBMLStats()
    {
    }

    public File getFile()                   { return file; }
    public int  getSBMLLevel()              { return level; }
    public int  getSBMLVersion()            { return version; }
    public int  getNumSpecies()             { return numSpecies; }
    public int  getNumCompartments()        { return numCompartments; }
    public int  getNumReactions()           { return numReactions; }
    public int  getNumParameters()          { return numParameters; }
    public int  getNumRules()               { return numRules; }
    public int  getNumUnitDefinitions()     { return numUnitDefinitions; }
    public int  getNumFunctionDefinitions() { return numFunctionDefinitions; }
    public int  getNumEvents()              { return numEvents; }
    public int  getNumConstraints()         { return numConstraints; }
    public int  getNumInitialAssignments()  { return numInitialAssignments; }
    public int  getNumSpeciesTypes()        { return numSpeciesTypes; }
    public int  getNumCompartmentTypes()    { return numSpeciesTypes; }

    public String asString()
    {
        return "file = '" + file.getPath() + "'"
                + ", level = " + level
                + ", version = " + version
                + ", species = " + numSpecies
                + ", compartments = " + numCompartments
                + ", reactions = " + numReactions
                + ", parameters = " + numParameters
                + ", rules = " + numRules
                + ", events = " + numEvents
                + ", initial assignments = " + numInitialAssignments
                + ", constraints = " + numConstraints
                + ", species types = " + numSpeciesTypes
                + ", compartment types = " + numCompartmentTypes;
    }

    //
    // ---------------------- Private data members ----------------------------
    //

    private File file;
    private SBMLDocument sbmlDoc; 
    private int level                  = 0;
    private int version                = 0;
    private int numSpecies             = 0;
    private int numCompartments        = 0;
    private int numReactions           = 0;
    private int numParameters          = 0;
    private int numRules               = 0;
    private int numUnitDefinitions     = 0;
    private int numFunctionDefinitions = 0;
    private int numEvents              = 0;
    private int numInitialAssignments  = 0;
    private int numConstraints         = 0;
    private int numCompartmentTypes    = 0;
    private int numSpeciesTypes        = 0;
}
