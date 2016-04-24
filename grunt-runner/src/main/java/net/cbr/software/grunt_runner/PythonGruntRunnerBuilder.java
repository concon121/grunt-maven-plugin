package net.cbr.software.grunt_runner;

import java.io.File;
import java.util.Map;

import org.apache.maven.plugin.logging.Log;

public class PythonGruntRunnerBuilder implements GruntRunnerBuilder {

	private String installation;
	private File workingDirectory;
	private Log log;
	private Map<String, String> reqs;

	public PythonGruntRunnerBuilder(String installation) {
		this.installation = installation;
	}

	public GruntRunnerBuilder in(File workingDirectory) {
		this.workingDirectory = workingDirectory;
		return this;
	}

	public GruntRunnerBuilder with(Map<String, String> prereqs) {
		this.reqs = prereqs;
		return this;
	}

	public GruntRunnerBuilder with(Log log) {
		this.log = log;
		return this;
	}

	public GruntRunner run() {

		GruntRunner runner = new PythonGruntRunner(this);
		runner.run();
		return runner;
	}

	public String getInstallation() {
		return installation;
	}

	public void setInstallation(String installation) {
		this.installation = installation;
	}

	public File getWorkingDirectory() {
		return workingDirectory;
	}

	public void setWorkingDirectory(File workingDirectory) {
		this.workingDirectory = workingDirectory;
	}

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public Map<String, String> getReqs() {
		return reqs;
	}

	public void setReqs(Map<String, String> reqs) {
		this.reqs = reqs;
	}

}
