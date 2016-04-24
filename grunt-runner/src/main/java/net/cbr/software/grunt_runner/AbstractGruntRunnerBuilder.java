package net.cbr.software.grunt_runner;

import java.io.File;
import java.util.Map;

import org.apache.maven.plugin.logging.Log;

public abstract class AbstractGruntRunnerBuilder implements GruntRunnerBuilder {

	public static final String GRUNT_FILE = "Gruntfile";
	public static final String PACKAGE = "package.json";

	protected String installation;
	protected File workingDirectory;
	protected GruntFileExtension gruntFileExtension;
	protected Log log;
	protected Map<String, String> reqs;

	public GruntRunnerBuilder in(File workingDirectory) {
		this.workingDirectory = workingDirectory;
		return this;
	}

	public GruntRunnerBuilder with(Map<String, String> prereqs) {
		this.reqs = prereqs;
		return this;
	}

	public GruntRunnerBuilder with(GruntFileExtension gruntFileExtension) {
		this.gruntFileExtension = gruntFileExtension;
		return this;
	}

	public GruntRunnerBuilder with(Log log) {
		this.log = log;
		return this;
	}

	public String getWorkingPath() {
		return getWorkingDirectory().getAbsolutePath();
	}

	public String getGruntFile() {
		return getWorkingPath() + File.separator + GRUNT_FILE + getGruntFileExtension().getExt();
	}

	public String getGruntFileName() {
		return GRUNT_FILE + getGruntFileExtension().getExt();
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

	public GruntFileExtension getGruntFileExtension() {
		return gruntFileExtension;
	}

	public void setGruntFileExtension(GruntFileExtension gruntFileExtension) {
		this.gruntFileExtension = gruntFileExtension;
	}
}
