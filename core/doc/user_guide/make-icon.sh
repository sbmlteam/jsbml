#!/bin/sh
# -----------------------------------------------------------------------------
# Description:  Trivial script to regenerate the icon for the user guide.
# First Author:	Michael Hucka <mhucka@caltech.edu>
# Organization: California Institute of Technology
# -----------------------------------------------------------------------------

convert 'User_Guide.pdf[0]' -background '#fff' -flatten -resize 100 -unsharp 0.5 -quality 10 -quiet png8:JSBML_user_guide_thumbnail.png
