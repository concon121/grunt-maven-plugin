package net.cbr.software.grunt_maven_plugin;

import java.io.File;
import java.util.Map;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import net.cbr.software.grunt_runner.GruntRunner;
import net.cbr.software.grunt_runner.PythonGruntRunnerBuilder;

@Mojo(name = "grunt", defaultPhase = LifecyclePhase.COMPILE)
public class GruntMojo extends AbstractMojo {

	@Parameter(property = "workingDirectory", required = true)
	private File workingDirectory;
	@Parameter(property = "prereqs", required = true)
	private Map<String, String> prereqs;
	@Parameter(property = "pythonInstallation", required = true)
	private String pythonInstallation;

	public void execute() throws MojoExecutionException {

		GruntRunner runner = new PythonGruntRunnerBuilder(pythonInstallation).in(workingDirectory)
				.with(prereqs).with(getLog()).run();

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
