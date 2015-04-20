
This folder contain some usefull ant build scripts to use jdiff
to create report that present the differences between two API.

In order to run these build scripts, you will need to download jdiff locally
from http://javadiff.sourceforge.net/.

And then set the JDIFF_HOME property to where you unpacked jdiff in the scripts.

Here is an example on how to use the generic script:

ant -f build-diff-generic.xml -DJDIFF_OLD_NAME="jsbml-1.0-rc1" -DJDIFF_OLD_SRC="../../../tags/jsbml-1.0-rc1/core/src/" -DJDIFF_NEW_NAME="jsbml-1.0" -DJDIFF_NEW_SRC="../../../tags/jsbml-1.0/core/src/"

