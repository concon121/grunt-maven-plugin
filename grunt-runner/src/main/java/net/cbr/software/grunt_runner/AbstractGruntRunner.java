package net.cbr.software.grunt_runner;

import static net.cbr.software.grunt_runner.AbstractGruntRunnerBuilder.PACKAGE;
import static net.cbr.software.grunt_runner.exception.GruntRunnerExceptionMessages.GRUNT_CHECK_FAILED;
import static net.cbr.software.grunt_runner.exception.GruntRunnerExceptionMessages.PACKAGE_CHECK_FAILED;
import static net.cbr.software.grunt_runner.exception.GruntRunnerExceptionMessages.WORKING_DIRECTORY_CHECK_FAILED;

import java.io.File;
import java.io.FileNotFoundException;

public abstract class AbstractGruntRunner implements GruntRunner{

	public static final int NORMAL_EXIT = 0;
	public static final int ERROR_EXIT = 1;

	protected GruntRunnerBuilder builder;
	protected int exitCode = NORMAL_EXIT;

	
	protected void checkWorkingDirectory() throws FileNotFoundException {

		boolean packageJSON = false;
		boolean gruntFile = false;

		File workingDirectory = getBuilder().getWorkingDirectory();

		if (!workingDirectory.exists()) {
			exitCode = ERROR_EXIT;
			throw new FileNotFoundException(WORKING_DIRECTORY_CHECK_FAILED);
		} else {
			for (String file : workingDirectory.list()) {
				if (file.toUpperCase().startsWith(PACKAGE.toUpperCase())) {
					packageJSON = true;
				} else if (file.toUpperCase().startsWith(getBuilder().getGruntFileName().toUpperCase())) {
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
