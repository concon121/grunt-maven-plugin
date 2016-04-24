package net.cbr.software.grunt_runner;

import java.io.IOException;

import net.cbr.software.grunt_runner.exception.SubProcessException;

public interface GruntRunner  {
	
	public GruntRunner run() throws IOException, InterruptedException, SubProcessException;
	public int getLastExitCode();
	
}
