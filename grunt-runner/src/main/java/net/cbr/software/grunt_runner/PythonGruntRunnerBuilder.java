package net.cbr.software.grunt_runner;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.maven.plugin.logging.Log;

import net.cbr.software.grunt_runner.exception.SubProcessException;

public class PythonGruntRunnerBuilder implements GruntRunnerBuilder {

	private static final String GRUNT_FILE = "Gruntfile.js";
	
	private String installation;
	private File workingDirectory;
	private Log log;
	private Map<String, String> reqs;

	public static GruntRunnerBuilder newInstance(String installation) {
		return new PythonGruntRunnerBuilder(installation);
	}
	
	private PythonGruntRunnerBuilder(String installation) {
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

	public GruntRunner run() throws IOException, InterruptedException, SubProcessException {

		GruntRunner runner = new PythonGruntRunner(this);
		runner.run();
		return runner;
		
	}
	
	public String getWorkingPath() {
		return getWorkingDirectory().getAbsolutePath();
	}
	
	public String getGruntFile() {
		return getWorkingPath() + File.separator + GRUNT_FILE;
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
