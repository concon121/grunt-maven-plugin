import sys
from subprocess import Popen, PIPE, STDOUT
import json

def checkRequirements(requirements):
    print("Verifying prerequisites")
    for requirement in requirements:
        command = requirement['key']
        param = requirement['value']
        process = Popen([command, param], stdin=PIPE, stdout=PIPE, stderr=STDOUT, shell=True)
        process.communicate()[0]
        exitcode = process.returncode
        if (exitcode != 0):
            print("Prerequisite " + command + " " + param + ": FAIL")
            exit(1)
        process.kill()
        print("Prerequisite " + command + " " + param + ": PASS")

requirements = json.loads(sys.argv[1])

checkRequirements(requirements)
