JSBML
=====

<img align="right" src="doc/common/logo/jsbml_logo_200px.png"> JSBML is a community-driven project to create a free, open-source, pure Java library for reading, writing, and manipulating SBML files and data streams. It is an alternative to the mixed Java/native code-based interface provided in libSBML. 

[![License](http://img.shields.io/:license-LGPL-blue.svg)](https://www.gnu.org/licenses/old-licenses/lgpl-2.1.en.html) [![Stable version](https://img.shields.io/badge/Stable_version-1.2-brightgreen.svg)](http://shields.io)

----
*Authors*: [Andreas Dräger](http://sbrg.ucsd.edu/researchers/draeger/),
[Nicolas Rodriguez](http://lenoverelab.org/members/Nicolas_Rodriguez/)
with contributions from (in alphabetical order):
Meike Aichele,
Alexander Diamantikos,
[Alexander Dörr](http://www.cogsys.cs.uni-tuebingen.de/mitarb/doerr/),
[Marine Dumousseau](https://sourceforge.net/u/marine3/profile/),
[Johannes Eichner](http://www.ra.cs.uni-tuebingen.de/mitarb/eichner/),
[Akira Funahashi](http://fun.bio.keio.ac.jp/~funa/),
Sebastian Fröhlich,
[Harold Gómez](https://www.bsse.ethz.ch/cobi/people/person-detail.html?persid=211340),
[Stephanie Hoffmann](http://www.ra.cs.uni-tuebingen.de/mitarb/hoffmann/),
[Michael Hucka](http://www.cds.caltech.edu/~mhucka),
[Roland Keller](http://www.ra.cs.uni-tuebingen.de/mitarb/keller/),
[Victor Kofia](http://kofiav.blogspot.ca),
Jakob Matthes,
[Florian Mittag](http://www.cogsys.cs.uni-tuebingen.de/mitarb/mittag/),
[Sebastian Nagel](http://www.ti.uni-tuebingen.de/Sebastian-Nagel.1843.0.html),
[Eugen Netz](https://abi.inf.uni-tuebingen.de/People/netz),
[Piero Dalle Pezze](https://www.linkedin.com/in/pdallepezze),
[Alexander Peltzer](http://it.inf.uni-tuebingen.de/?page_id=110),
[Jan D. Rudolph](http://www.biochem.mpg.de/employees/47658/226605),
Simon Schäfer,
[Roman Schulte](http://www.roman-schulte.de),
[Alex Thomas](http://sbrg.ucsd.edu/researchers/thomas/),
[Ibrahim Y. Vazirabad](http://www.mscs.mu.edu/~ivazirab/),
Sarah Rachel Mueller vom Hagen,
[Leandro Watanabe](http://www.async.ece.utah.edu/~leandro/),
[Clemens Wrzodek](http://www.cogsys.cs.uni-tuebingen.de/mitarb/wrzodek/),
[Finja Wrzodek](http://www.ra.cs.uni-tuebingen.de/mitarb/buechel/),
and
[Thomas J. Zajac](https://github.com/mephenor/).


*License*: This code is licensed under the LGPL version 2.1.  Please see the section on [licensing and distribution](#-licensing-and-distribution) below for more information about third-party software included in the JSBML code base.

*Home page*: [http://sbml.org/Software/JSBML](http://sbml.org/Software/JSBML)

*Developers' email address*: [jsbml-team@googlegroups.com](mailto:jsbml-team@googlegroups.com)

*JSBML discussion group*: [https://groups.google.com/forum/#!forum/jsbml-development](https://groups.google.com/forum/#!forum/jsbml-development)

*Repository*: [https://github.com/sbmlteam/jsbml](https://github.com/sbmlteam/jsbml)

*Pivotal tracker*: [https://www.pivotaltracker.com/n/projects/977060](https://www.pivotaltracker.com/n/projects/977060)


♥️ Please cite the JSBML papers and your version of JSBML
---------------------------------------------------------

Article citations are **critical** for us to be able to continue support for JSBML.  If you use JSBML and you publish papers about your software, we ask that you **please cite the JSBML papers**:

<dl>
<dt>Paper #1:</dt>
<dd>Nicolas Rodriguez, Alex Thomas, Leandro Watanabe, Ibrahim Y. Vazirabad, Victor Kofia, Harold F. Gómez, Florian Mittag, Jakob Matthes, Jan Rudolph, Finja Wrzodek, Eugen Netz, Alexander Diamantikos, Johannes Eichner, Roland Keller, Clemens Wrzodek, Sebastian Fröhlich, Nathan E. Lewis, Chris J. Myers, Nicolas Le Novère, Bernhard Ø. Palsson, Michael Hucka, and Andreas Dräger. <a href="http://bioinformatics.oxfordjournals.org/content/31/20/3383">JSBML 1.0: providing a smorgasbord of options to encode systems biology models</a>. <i>Bioinformatics</i> (2015), 31(20):3383&ndash;3386.</dd>

<dt>Paper #2:</dt>
<dd>Andreas Dräger, Nicolas Rodriguez, Marine Dumousseau, Alexander Dörr, Clemens Wrzodek, Nicolas Le Novère, Andreas Zell, and Michael Hucka. <a href="http://bioinformatics.oxfordjournals.org/content/27/15/2167">JSBML: a flexible Java library for working with SBML.</a> <i>Bioinformatics</i> (2011), 27(15):2167–2168.</dd>
</dl>

Please also indicate the specific version of JSBML you use, to improve other people's ability to reproduce your results.  You can use the Zenodo DOIs we provide for this purpose:

* JSBML release 1.2 &rArr; [10.5281/zenodo.200544](http://doi.org/10.5281/zenodo.200544)
* JSBML release 1.1 &rArr; [10.5281/zenodo.55323](http://dx.doi.org/10.5281/zenodo.55323)
* JSBML release 1.0 &rArr; [10.5281/zenodo.55635](http://dx.doi.org/10.5281/zenodo.55635)
* JSBML release 0.8 &rArr; [10.5281/zenodo.55636](http://dx.doi.org/10.5281/zenodo.55636)


📰 Recent news and activities
-----------------------------

Please see the file [NEWS.md](NEWS.md) for a log of recent changes in JSBML.


► Getting started with JSBML
----------------------------

Please see the user manual at http://sbml.org/Software/JSBML/docs.

If you use JSBML, we encourage you to subscribe to or monitor via RSS the [jsbml-development](https://groups.google.com/forum/#!forum/jsbml-development) mailing list/web forum, where people discuss the development and use of JSBML.  Being a member of [jsbml-development](https://groups.google.com/forum/#!forum/jsbml-development) will enable you to keep in touch with the latest developments in JSBML as well as to ask questions and share your experiences with fellow developers and users of JSBML.


⁇ Getting Help
------------

JSBML is under active development by a distributed team.  If you find bugs, please report them using the tracker; if you have any questions, please post them on one of the discussion groups or contact the developers directly:

* *Bug reports*: [GitHub issue tracker](https://github.com/sbmlteam/jsbml/issues)
* *Forum for JSBML*: [jsbml-development](https://groups.google.com/forum/#!forum/jsbml-development)
* *Forum for general SBML software interoperability discussions*: [sbml-interoperability](https://groups.google.com/forum/#!forum/sbml-interoperability)
* *Main development team mailing list*: [jsbml-team@googlegroups.com](mailto:jsbml-team@googlegroups.com)
* *Pivotal Tracker*: [JSBML](https://www.pivotaltracker.com/n/projects/499447)

What is the difference between [jsbml-development](https://groups.google.com/forum/#!forum/jsbml-development) and [sbml-interoperability](https://groups.google.com/forum/#!forum/sbml-interoperability)?  The former is specifically for discussions about JSBML, while the latter is appropriate for discussions involving SBML software interoperability in general, including (but not limited to) JSBML and its use in other software.

If you use SBML, we also urge you to sign up for [sbml-announce](https://groups.google.com/forum/#!forum/sbml-announce), the SBML announcements mailing list.  It is a low-volume, broadcast-only list.


☮ Licensing and distribution
----------------------------

JSBML uses third-party software libraries; these software libraries have their own copyright statements and distribution terms.  Please see the file [LICENSE.txt](./LICENSE.txt) for more information.

JSBML is Copyright (C) 2009-2016 jointly by the following organizations:

1. The University of Tuebingen, Germany
2. EMBL European Bioinformatics Institute (EMBL-EBI), Hinxton, UK
3. The California Institute of Technology, Pasadena, CA, USA
4. The University of California, San Diego, La Jolla, CA, USA
5. The Babraham Institute, Cambridge, UK

JSBML is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or any later version.

This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY, WITHOUT EVEN THE IMPLIED WARRANTY OF MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.  The software and documentation provided hereunder is on an "as is" basis, and the copyright holders have no obligations to provide maintenance, support, updates, enhancements or modifications.  In no event shall the copyright holders be liable to any party for direct, indirect, special, incidental or consequential damages, including lost profits, arising out of the use of this software and its documentation, even if the copyright holders have been advised of the possibility of such damage.  See the GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License along with this library in the file named "COPYING.txt" included with the software distribution.  A copy is also available online at the Internet address http://sbml.org/Software/JSBML/licenses/COPYING.html for your convenience.  You may also write to obtain a copy from the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.


☺ Acknowledgments
-----------------------

The authors gratefully acknowledge the funding and support of the following agencies who helped make JSBML possible:

* The EMBL European Bioinformatics Institute (Germany and UK).

* The NIH National Institute of General Medical Sciences, under grant R01 GM070923 to the California Institute of Technology (USA).

* The Biotechnology and Biological Sciences Research Council (BBSRC) under the grant BBS/E/B/000C0419 to the Babraham Institute, UK.

* Google, Inc., as part of Google Summer of Code in 2014 and 2016.

* The European Commission as part of the 7th Framework Programme for Research and Technological Development (Marie-Curie International Outgoing Fellowship project "AMBiCon," grant number 332020).

* A grant of the University of Tuebingen for young scientist support (project number 18017002).

* The Federal Ministry of Education and Research (BMBF, Germany) via grant numbers 0315756 and 0315384C for the Virtual Liver Network and the MedSys (Medical Systems Biology) project Spher4Sys (Germany).
