-------------------------
# JSBML-1.2


We are pleased to announce the release of JSBML-1.2, which is now available for download from [GitHub (below)](#downloads) and from [SourceForge](https://sourceforge.net/projects/jsbml/files/jsbml/1.2).

JSBML is a community-driven project to create a free, open-source, pure Java library for reading, writing, and manipulating [SBML](http://sbml.org) files and data streams. It is an alternative to the mixed Java/native code-based interface provided in [libSBML](http://sbml.org/Software/libSBML).

For more details, please visit the JSBML project home page:

<http://sbml.org/Software/JSBML>

The main new feature in this release is the support for SBML Level 3 Version 2 (RC2). This involved some changes in the class hierarchy of JSBML. A notable SBML change introduced in SBML Level 3 Version 2 is that every 'SBase' object can have an 'id' and a 'name' attribute.  In JSBML, for compatibility reasons, we did not change the 'NamedSBase' interface, so not every 'SBase' is a 'NamedSBase'. Only SBML elements that had 'id' and 'name' in SBML Levels/Versions below L3V2 will extend 'NamedSBase'. So, when implementing support for SBML L3V2, we recommend that developers try to avoid using 'NamedSBase' and just use 'SBase' instead. The same is true for the JSBML classes 'UniqueNamedSBase' and 'UniqueSId'. If you were using the 'UniqueNamedSBase' class, please try to use 'UniqueSId' instead.  As part of the upgrade to support SBML L3V2, users should also consider checking how they access 'InitialAssignment', 'Rule' and 'EventAssignment' objects, as often the methods make use of the 'variable' or 'symbol' attribute instead of the 'id' attribute.

We have upgraded the minimum JDK requirement to 1.7 for this release, to be able to use the latest versions of the third-party libraries needed by JSBML. As the number of third-party libraries having JDK 1.8 as a minimum requirement increases, we may need to increase the minimum JDK requirement to 1.8 for next year's JSBML releases. (Please let us know in advance if you think requiring JDK 1.8 will be a problem for you.)

The 'toString()' method available on most JSBML classes has been replaced by a generic method so you may find some differences in the output produced by the JSBML classes compared to previous versions. One method we did not change for now is 'ASTNode.toString()', because doing so could cause many side effects. As part of the upgrade to SBML L3V2 and JSBML 1.2, we advise users to make sure they use 'ASTNode.toFormula()' when they want to show/store the infix formula representation of an 'ASTNode', and to not rely on the 'toString()' method for that purpose. Don't hesitate to provide us with some feedback about this change. We tried to keep any significantly different 'toString()' method under a different name, starting with 'print' and followed by the class name, for example 'Unit.printUnit()' will produce the same output as the 'Unit.toString()' method in JSBML 1.1.
 
Since the time of the last JSBML release, we have moved the JSBML source code repository to [GitHub](https://github.com/sbmlteam/jsbml). Although we provide the main download files from GitHub, we will continue to distribute the releases from SourceForge until we develop a suitable release replacement scheme using either GitHub or sbml.org.

You can find a detailed list of the user-visible new features and bug fixes since JSBML version 1.1 on the [NEWS](NEWS.md) file.

Thank you for your interest and support of JSBML and SBML in general.

## The JSBML team. 
<a name="downloads"/>

## Downloads
Added the above section to avoid an error in eclipse - the two last lines need to be deleted when adding the text to github.