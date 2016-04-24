package net.cbr.software.grunt_runner;

import static net.cbr.software.grunt_runner.exception.GruntRunnerExceptionMessages.GRUNT_CHECK_FAILED;
import static net.cbr.software.grunt_runner.exception.GruntRunnerExceptionMessages.PACKAGE_CHECK_FAILED;
import static net.cbr.software.grunt_runner.exception.GruntRunnerExceptionMessages.PREREQ_CHECK_FAILED;
import static net.cbr.software.grunt_runner.exception.GruntRunnerExceptionMessages.WORKING_DIRECTORY_CHECK_FAILED;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.cbr.software.grunt_runner.exception.SubProcessException;
import net.cbr.software.grunt_runner.utils.JsonHelper;
import net.cbr.software.grunt_runner.utils.PythonHelper;

public class PythonGruntRunner implements GruntRunner {

	public static final int NORMAL_EXIT = 0;
	public static final int ERROR_EXIT = 1;
	
	private static String packageJSONName = "package";
	private static String gruntFileName = "Gruntfile";

	private GruntRunnerBuilder builder;
	private int exitCode = NORMAL_EXIT;

	public PythonGruntRunner(GruntRunnerBuilder builder) {
		this.builder = builder;
	}

	public GruntRunner run() throws IOException, InterruptedException, SubProcessException {

		checkWorkingDirectory();

		exec("verify.py", PREREQ_CHECK_FAILED, JsonHelper.toJSON(getBuilder().getReqs()));
		exec("install.py", getBuilder().getWorkingPath());
		exec("grunt.py", getBuilder().getWorkingPath(), getBuilder().getGruntFile());

		return this;
	}

	private void checkWorkingDirectory() throws FileNotFoundException {
		
		boolean packageJSON = false;
		boolean gruntFile = false;

		File workingDirectory = getBuilder().getWorkingDirectory();

		if (!workingDirectory.exists()) {
			exitCode = ERROR_EXIT;
			throw new FileNotFoundException(WORKING_DIRECTORY_CHECK_FAILED);
		} else {
			for (String file : workingDirectory.list()) {
				if (file.toUpperCase().startsWith(packageJSONName.toUpperCase())) {
					packageJSON = true;
				} else if (file.toUpperCase().startsWith(gruntFileName.toUpperCase())) {
					gruntFile = true;
				}

			}
		}

		if (!packageJSON) {
			exitCode = ERROR_EXIT;
			throw new FileNotFoundException(PACKAGE_CHECK_FAILED);
		}
		if (!gruntFile) {
			exitCode = ERROR_EXIT;
			throw new FileNotFoundException(GRUNT_CHECK_FAILED);
		}
	}

	protected void exec(String script, String errorMessage, String... param) throws IOException, InterruptedException, SubProcessException {

		int exit = PythonHelper.execPythonScript(getBuilder().getLog(), PythonHelper.getPythonScript(script), param);

		if (exit != NORMAL_EXIT) {
			exitCode = ERROR_EXIT;
			throw new SubProcessException(errorMessage);
		}

	}
	
	public int getLastExitCode() {
		return exitCode;
	}

	public GruntRunnerBuilder getBuilder() {
		return builder;
	}

	public void setBuilder(GruntRunnerBuilder builder) {
		this.builder = builder;
	}

}
