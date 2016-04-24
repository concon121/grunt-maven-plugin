import sys
from subprocess import Popen, PIPE, STDOUT

directory = sys.argv[1]
print(directory)

process = Popen(["npm", "install", "--prefix", directory, directory], stdin=PIPE, stdout=PIPE, stderr=STDOUT, shell=True)
process.communicate()[0]
exitcode = process.returncode
if (exitcode != 0):
    print("npm install: FAIL")
    exit(1)
process.kill()
print("npm install: PASS")
