import sys
from subprocess import Popen, PIPE, STDOUT

directory = sys.argv[1]
gruntfile = sys.argv[2]
print(directory)
print(gruntfile)

process = Popen(["grunt", "--base", directory, "--gruntfile", gruntfile], stdin=PIPE, stdout=PIPE, stderr=STDOUT, shell=True)
process.communicate()[0]
exitcode = process.returncode
if (exitcode != 0):
    print("grunt: FAIL")
    exit(1)
process.kill()
print("grunt: PASS")
