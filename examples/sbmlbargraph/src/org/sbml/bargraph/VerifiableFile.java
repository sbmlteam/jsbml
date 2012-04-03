// @file    VerifiableFile.java
// @brief   Enhancement of the basic Java File class
// @author  Michael Hucka
// @date    This file is based on code I wrote in 2000-2003.
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
import java.util.Stack;


/**
 * Extension of <code>java.io.file</code> that adds methods for easily
 * testing whether a given file or directory exists and is accessible.
 * <p>
 * Usage:
 * <pre>
 *      VerifiableFile file = new VerifiableFile("/tmp/foo");
 *      if (file.isVerifiedExistingFile())
 *          ... do something;
 *      else
 *          ... do something else;
 * </pre>
 *
 * Note: The two-argument <code>VerifiableFile</code> constructors do not
 * interpret an empty parent abstract pathname as the current user
 * directory.  An empty parent instead causes the child to be resolved
 * against the system-dependent directory defined by the
 * <code>FileSystem.getDefaultParent</code> method.  On Unix this default
 * is <code>"/"</code>, while on Win32 it is <code>"\\"</code>.  This is
 * the behavior documented in the <code>java.io.File</code> classes.
 */
class VerifiableFile
    extends File
{
    //
    // --------------------------- Public methods -----------------------------
    //

    /**
     * Creates a new <code>VerifiableFile</code> instance by converting the
     * given pathname string into an abstract pathname.
     * 
     * @param pathname A pathname string.
     **/
    public VerifiableFile(String pathname)
    {
        super(pathname);
    }


    /**
     * Creates a new <code>VerifiableFile</code> instance by converting the
     * given <code>File</code> parameter into an abstract pathname.
     * 
     * @param file A file.
     **/
    public VerifiableFile(File file)
    {
        // This is inefficient b/c it takes an object, turns it into a
        // string, then causes a new object to be created.  The Java File()
        // class doesn't provide a way of resetting the internal object it
        // keeps, so we have to go through the constructor like this.

        super(file.getPath());
    }


    /**
     * Creates a new <code>VerifiableFile</code> instance from a parent
     * pathname string and a child pathname string.
     * <p>
     * If <code>parent</code> is <code>null</code>, then the new
     * <code>VerifiableFile</code> instance is created as if by invoking the
     * single-argument <code>VerifiableFile</code> constructor on the given
     * <code>child</code> pathname string. 
     * <p>
     * Otherwise, the <code>parent</code> pathname string is taken to denote
     * a directory, and the <code>child</code> pathname string is taken to
     * denote either a directory or a file.  If the <code>child</code> pathname
     * string is absolute then it is converted into a relative pathname in a
     * system-dependent way.  If <code>parent</code> is the empty string then
     * the new <code>VerifiableFile</code> instance is created by converting
     * <code>child</code> into an abstract pathname and resolving the result
     * against a system-dependent default directory.  Otherwise, each pathname
     * string is converted into an abstract pathname and the child abstract
     * pathname is resolved against the parent.
     * 
     * @param parent The parent pathname string.
     * @param child The child pathname string.
     * @throws  NullPointerException
     *          If <code>child</code> is <code>null</code>.
     **/
    public VerifiableFile(String parent, String child)
    {
        super(parent, child);
    }


    /**
     * Creates a new <code>VerifiableFile</code> instance from a parent
     * abstract pathname and a child pathname string.
     * <p>
     * If <code>parent</code> is <code>null</code>, then the new
     * <code>VerifiableFile</code> instance is created as if by invoking the
     * single-argument <code>VerifiableFile</code> constructor on the given
     * <code>child</code> pathname string. 
     * <p>
     * Otherwise, the <code>parent</code> abstract pathname is taken to denote
     * a directory, and the <code>child</code> pathname string is taken to
     * denote either a directory or a file.  If the <code>child</code> pathname
     * string is absolute then it is converted into a relative pathname in a
     * system-dependent way.  If <code>parent</code> is empty, then
     * the new <code>VerifiableFile</code> instance is created by converting
     * <code>child</code> into an abstract pathname and resolving the result
     * against a system-dependent default directory.  Otherwise, each pathname
     * string is converted into an abstract pathname and the child abstract
     * pathname is resolved against the parent.
     * 
     * @param parent The parent pathname string.
     * @param child The child pathname string.
     * @throws  NullPointerException
     *          If <code>child</code> is <code>null</code>.
     **/
    public VerifiableFile(File parent, String child)
    {
        super(parent, child);
    }


    /**
     * Predicate to test that a file is not a directory, that it exists,
     * and that it is readable to the invoking process.
     * 
     * @return <code>true</code> if this file is verified to be an existing
     * file, <code>false</code> otherwise
     **/
    public boolean isVerifiedFile()
    {
        if (isDirectory())
        {
            Log.note(getPath() + " is a directory.");
            return false;
        }
        if (! exists())
        {
            Log.note("File " + getPath() + " does not exist.");
            return false;
        }
        if (! isFile())
        {
            Log.note(getPath() + " is a not a file.");
            return false;
        }
        if (! canRead())
        {
            Log.note("File " + getPath() + " exists but is not readable.");
            return false;
        }
        return true;
    }


    /**
     * Predicate to test that a diretory is not a file, that it exists,
     * and that it is readable to the invoking process.
     * 
     * @return <code>true</code> if this file is verified to be a
     * directory, <code>false</code> otherwise
     **/
    public boolean isVerifiedDirectory()
    {
        if (! exists())
        {
            Log.note("Directory " + getPath() + " does not exist.");
            return false;
        }
        if (! isDirectory())
        {
            Log.note(getPath() + " is not a directory.");
            return false;
        }
        if (! canRead())
        {
            Log.note("Directory " + getPath() + " exists but is not readable.");
            return false;
        }
        return true;
    }


    /**
     * Deletes directory along with any content therein.
     * This is a non-recursive implementation found online on 2009-03-21 at
     * http://forums.sun.com/thread.jspa?threadID=563148
     * Original author unknown.
     */
    public void deleteDirectory()
    {
        if (! exists() || ! isDirectory())
            return;

        Stack<File> dirStack = new Stack<File>();
        dirStack.push(this);

        boolean containsSubdir;
        while (! dirStack.isEmpty())
        {
            File currDir = dirStack.peek();
            containsSubdir = false;

            String[] filenameArray = currDir.list();
            for (int i = 0; i < filenameArray.length; i++)
            {
                String fileName = currDir.getAbsolutePath() + File.separator 
                                  + filenameArray[i];
                File file = new File(fileName);
                if (file.isDirectory())
                {
                    dirStack.push(file);
                    containsSubdir = true;
                }
                else
                {
                    file.delete();
                }       
            }

            if (! containsSubdir)
            {
                dirStack.pop();         // Remove current dir from stack.
                currDir.delete();       // Delete current dir.
            }
        }
    }

    //
    // ---------------------- Private data members ----------------------------
    //

    private static final long serialVersionUID = 1L;
}
