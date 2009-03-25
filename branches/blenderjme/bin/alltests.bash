#!/bin/bash -p

PROGNAME="${0##*/}"

# $Id$
#
# Executes all standalone and Blender environment tests.
# Command line parameters are just passed through to Blender for the Blender
# environment tests, and have no effect on the standalone tests.
# This script have non-zero return status if anything fails, but will attempt
# to execute all tests.  I.e. when a test failure is encountered, it will
# proceed on, remembering to exit with non-zero status at the end.
#
# See the file "doc/testing.txt" for details.
#
# Redistribution and use in source and binary forms, with or without
# modification, are permitted provided that the following conditions are
# met:
#
# * Redistributions of source code must retain the above copyright
#   notice, this list of conditions and the following disclaimer.
#
# * Redistributions in binary form must reproduce the above copyright
#   notice, this list of conditions and the following disclaimer in the
#   documentation and/or other materials provided with the distribution.
#
# * Neither the name of the project nor the names of its contributors 
#   may be used to endorse or promote products derived from this software 
#   without specific prior written permission.
#
# THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
# "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
# TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
# PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
# CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
# EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
# PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
# PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
# LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
# NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
# SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

shopt -s xpg_echo
set +u

Failout() {
    echo "Aborting $PROGNAME:  $*" 1>&2
    exit 1
}
[ -n "$TMPDIR" ] || TMPDIR=/tmp
TMPFILE="$TMPDIR/${PROGNAME%%.*}-$$.py"

PYTHONPROG=python
[ -n "$PYTHONHOME" ] && PYTHONPROG="$PYTHONHOME/bin/python"
type -t blender >&- || Failout 'Blender is not in your env search path'
"$PYTHONPROG" -c 'import unittest' ||
Failout "Your Python interpreter is missing, or does not support module 'unittest': $PYTHONPROG"

case "$0" in
/*) SCRIPTDIR="${0%/*}";; */*) SCRIPTDIR="$PWD/${0%/*}";; *) SCRIPTDIR="$PWD";;
esac
case "$SCRIPTDIR" in *?/.) SCRIPTDIR="${SCRIPTDIR%/.}"; esac

export PYTHONPATH="${SCRIPTDIR%/*}/src"

declare -i failures=0

"$PYTHONPROG" "${PYTHONPATH}/jmetest/xml.py" || ((failures = failures + 1))

# As soon as have a Blender environment test implemented:
#"$SCRIPTDIR/blenderscript.bash" "${PYTHONPATH}/blendertest/script.py" ||
#((failures = failures + 1))

exit $failures
