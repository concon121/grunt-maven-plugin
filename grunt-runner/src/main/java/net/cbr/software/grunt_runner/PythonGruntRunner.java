package net.cbr.software.grunt_runner;

import java.io.File;
import java.io.IOException;

import org.apache.maven.plugin.MojoExecutionException;

import net.cbr.software.grunt_runner.utils.JsonHelper;
import net.cbr.software.grunt_runner.utils.PythonHelper;

public class PythonGruntRunner implements GruntRunner {
	
	private static final int NORMAL_EXIT = 0;
	private static final String PREREQ_CHECK_FAILED = "Prerequisite check failed.  Please ensure all necessary libraries are installed.";
	private static final String WORKING_DIRECTORY_CHECK_FAILED = "Working directory does not exist.";
	private static final String PACKAGE_CHECK_FAILED = "package.json does not exist.";
	private static final String GRUNT_CHECK_FAILED = "Gruntfile does not exist.";

	private static String packageJSONName = "package";
	private static String gruntFileName = "Gruntfile";
	
	private GruntRunnerBuilder builder;
	
	public PythonGruntRunner(GruntRunnerBuilder builder) {
		this.builder = builder;
	}

	private void grunt() throws IOException, InterruptedException, MojoExecutionException {
		String grunt = PythonHelper.getPythonScript("grunt.py");
		int exit = PythonHelper.execPythonScript(getBuilder().getLog(), grunt, getBuilder().getWorkingDirectory().getAbsolutePath(),
				getBuilder().getWorkingDirectory().getAbsolutePath() + File.separator + "Gruntfile.js");

		if (exit != NORMAL_EXIT) {
			throw new MojoExecutionException(PREREQ_CHECK_FAILED);
		}
	}

	private void install() throws IOException, InterruptedException, MojoExecutionException {
		String install = PythonHelper.getPythonScript("install.py");
		int exit = PythonHelper.execPythonScript(getBuilder().getLog(), install, getBuilder().getWorkingDirectory().getAbsolutePath());

		if (exit != NORMAL_EXIT) {
			throw new MojoExecutionException(PREREQ_CHECK_FAILED);
		}
	}

	private void checkWorkingDirectory() throws MojoExecutionException {
		System.out.println("The working directory is : " + getBuilder().getWorkingDirectory().getAbsolutePath());
		boolean packageJSON = false;
		boolean gruntFile = false;

		File workingDirectory = getBuilder().getWorkingDirectory();
		
		if (!workingDirectory.exists()) {
			throw new MojoExecutionException(WORKING_DIRECTORY_CHECK_FAILED);
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
			throw new MojoExecutionException(PACKAGE_CHECK_FAILED);
		}
		if (!gruntFile) {
			throw new MojoExecutionException(GRUNT_CHECK_FAILED);
		}
	}

	protected void checkPreReqs() throws MojoExecutionException, IOException, InterruptedException {

		String verify = PythonHelper.getPythonScript("verify.py");
		int exit = PythonHelper.execPythonScript(getBuilder().getLog(), verify, JsonHelper.toJSON(getBuilder().getReqs()));

		if (exit != NORMAL_EXIT) {
			throw new MojoExecutionException(PREREQ_CHECK_FAILED);
		}

	}

	public void run() {
		// TODO Auto-generated method stub
		
	}

	public GruntRunnerBuilder getBuilder() {
		return builder;
	}

	public void setBuilder(GruntRunnerBuilder builder) {
		this.builder = builder;
	}
	
	
}
