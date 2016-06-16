import sys
import json

from libsbml import *

package_codes = [['CORE',          0],
                 ['COMP',    1000000],
                 ['REQ',     1100000],
                 ['SPATIAL', 1200000],
                 ['RENDER',  1300000],
                 ['FBC',     2000000],
                 ['QUAL',    3000000],
                 ['GROUPS',  4000000],
                 ['DISTRIB', 5000000],
                 ['LAYOUT',  6000000],
                 ['MULTI',   7000000],
                 ['ARRAYS',  8000000],
                 ['DYN',     9000000]]

def error_package(code):
    code = int(float(code) / 100000) * 100000
    for pkg in package_codes:
        if pkg[1] == code:
            return pkg
    return ['PACKAGE_NOT_FOUND', 0]

file_name = sys.argv[1]
fp = open(file_name)
contents = fp.read()
data = json.loads(contents)

file = 'public interface SBMLErrorCodes { \n \n'
for key in sorted(data, key=int):
    code = int(key)
    pkg = error_package(code)
    short = code - pkg[1]
    file += '\n\t /**\n\t  * Error code ' + str(code) + ': Message\n\t  */\n \t public static final int ' + pkg[0] + str(short).zfill(5) + " = " + str(code) + "; \n"

file += '}'

filename = sys.argv[2]
dir = os.path.dirname(filename)

if not os.path.exists(dir):
    os.makedirs(dir)

with open(dir + "/SBMLErrorCodes.java", "w") as text_file:
    text_file.write(file)
print 'done'
