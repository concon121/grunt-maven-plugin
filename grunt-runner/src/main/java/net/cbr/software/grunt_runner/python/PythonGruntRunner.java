package net.cbr.software.grunt_runner.python;

import static net.cbr.software.grunt_runner.exception.GruntRunnerExceptionMessages.PREREQ_CHECK_FAILED;

import java.io.IOException;

import net.cbr.software.grunt_runner.AbstractGruntRunner;
import net.cbr.software.grunt_runner.GruntRunner;
import net.cbr.software.grunt_runner.GruntRunnerBuilder;
import net.cbr.software.grunt_runner.exception.SubProcessException;
import net.cbr.software.grunt_runner.utils.JsonHelper;
import net.cbr.software.grunt_runner.utils.PythonHelper;

public class PythonGruntRunner extends AbstractGruntRunner {

	public PythonGruntRunner(GruntRunnerBuilder builder) {
		super();
		this.builder = builder;
	}
	
	public GruntRunner run() throws IOException, InterruptedException, SubProcessException {

		checkWorkingDirectory();

		exec("verify.py", PREREQ_CHECK_FAILED, JsonHelper.toJSON(getBuilder().getReqs()));
		exec("install.py", getBuilder().getWorkingPath());
		exec("grunt.py", getBuilder().getWorkingPath(), getBuilder().getGruntFile());

		return this;
	}
	
	protected void exec(String script, String errorMessage, String... param)
			throws IOException, InterruptedException, SubProcessException {

		int exit = PythonHelper.execPythonScript(getBuilder().getLog(), PythonHelper.getPythonScript(script), param);

		if (exit != NORMAL_EXIT) {
			exitCode = ERROR_EXIT;
			throw new SubProcessException(errorMessage);
		}

	}

	
}
