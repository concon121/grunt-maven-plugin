package net.cbr.software.grunt_maven_plugin;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import net.cbr.software.grunt_maven_plugin.utils.JsonHelper;
import net.cbr.software.grunt_maven_plugin.utils.PythonHelper;

/**
 * Goal which touches a timestamp file.
 *
 */
@Mojo(name = "grunt", defaultPhase = LifecyclePhase.COMPILE)
public class GruntMojo extends AbstractMojo {

	private static final int NORMAL_EXIT = 0;
	private static final String PREREQ_CHECK_FAILED = "Prerequisite check failed.  Please ensure all necessary libraries are installed.";
	private static final String WORKING_DIRECTORY_CHECK_FAILED = "Working directory does not exist.";
	private static final String PACKAGE_CHECK_FAILED = "package.json does not exist.";
	private static final String GRUNT_CHECK_FAILED = "Gruntfile does not exist.";

	private static String packageJSONName = "package";
	private static String gruntFileName = "Gruntfile";

	/**
	 * Location of the file.
	 */
	@Parameter(property = "workingDirectory", required = true)
	private File workingDirectory;
	@Parameter(property = "prereqs", required = true)
	private Map<String, String> prereqs;
	@Parameter(property = "pythonInstallation", required = true)
	private String pythonInstallation;

	public void execute() throws MojoExecutionException {

		try {
			checkPreReqs();
			checkWorkingDirectory();
			install();
			grunt();
		} catch (IOException e) {
			throw new MojoExecutionException("Could not read file", e);
		} catch (InterruptedException e) {
			throw new MojoExecutionException("Execution was interrupted", e);
		}

	}

	private void grunt() throws IOException, InterruptedException, MojoExecutionException {
		String grunt = PythonHelper.getPythonScript("grunt.py");
		int exit = PythonHelper.execPythonScript(getLog(), grunt, getWorkingDirectory().getAbsolutePath(), getWorkingDirectory().getAbsolutePath() + File.separator + "Gruntfile.js");

		if (exit != NORMAL_EXIT) {
			throw new MojoExecutionException(PREREQ_CHECK_FAILED);
		}
	}

	private void install() throws IOException, InterruptedException, MojoExecutionException {
		String install = PythonHelper.getPythonScript("install.py");
		int exit = PythonHelper.execPythonScript(getLog(), install, getWorkingDirectory().getAbsolutePath());

		if (exit != NORMAL_EXIT) {
			throw new MojoExecutionException(PREREQ_CHECK_FAILED);
		}
	}

	private void checkWorkingDirectory() throws MojoExecutionException {
		System.out.println("The working directory is : " + getWorkingDirectory().getAbsolutePath());
		boolean packageJSON = false;
		boolean gruntFile = false;

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
		int exit = PythonHelper.execPythonScript(getLog(), verify, JsonHelper.toJSON(getPrereqs()));

		if (exit != NORMAL_EXIT) {
			throw new MojoExecutionException(PREREQ_CHECK_FAILED);
		}

	}

	public File getWorkingDirectory() {
		return workingDirectory;
	}

	public void setWorkingDirectory(File workingDirectory) {
		this.workingDirectory = workingDirectory;
	}

	public Map<String, String> getPrereqs() {
		return prereqs;
	}

	public void setPrereqs(Map<String, String> prereqs) {
		this.prereqs = prereqs;
	}

	public String getPythonInstallation() {
		return pythonInstallation;
	}

	public void setPythonInstallation(String pythonInstallation) {
		this.pythonInstallation = pythonInstallation;
	}

}
