package net.cbr.software.grunt_runner;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.maven.plugin.logging.Log;

import net.cbr.software.grunt_runner.exception.SubProcessException;

public interface GruntRunnerBuilder {
	
	public GruntRunnerBuilder in(File workingDirectory);
	public GruntRunnerBuilder with(Map<String, String> prereqs);
	public GruntRunnerBuilder with(GruntFileExtension gruntFileExtension);
	public GruntRunnerBuilder with(Log log);
	public GruntRunner run() throws IOException, InterruptedException, SubProcessException;
	
	public Log getLog();
	public File getWorkingDirectory();
	public String getWorkingPath();
	public Map<String, String> getReqs();
	public String getGruntFile();
	public String getGruntFileName();
}
