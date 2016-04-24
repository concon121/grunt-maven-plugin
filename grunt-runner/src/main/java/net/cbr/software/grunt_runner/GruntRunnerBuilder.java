package net.cbr.software.grunt_runner;

import java.io.File;
import java.util.Map;

import org.apache.maven.plugin.logging.Log;

public interface GruntRunnerBuilder {

	public GruntRunnerBuilder in(File workingDirectory);
	public GruntRunnerBuilder with(Map<String, String> prereqs);
	public GruntRunnerBuilder with(Log log);
	public GruntRunner run();
	
	public Log getLog();
	public File getWorkingDirectory();
	public Map<String, String> getReqs();
}
