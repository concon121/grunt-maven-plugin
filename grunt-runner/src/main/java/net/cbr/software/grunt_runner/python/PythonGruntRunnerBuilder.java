package net.cbr.software.grunt_runner.python;

import java.io.IOException;

import net.cbr.software.grunt_runner.AbstractGruntRunnerBuilder;
import net.cbr.software.grunt_runner.GruntRunner;
import net.cbr.software.grunt_runner.GruntRunnerBuilder;
import net.cbr.software.grunt_runner.exception.SubProcessException;

public class PythonGruntRunnerBuilder extends AbstractGruntRunnerBuilder {

	public static GruntRunnerBuilder newInstance(String installation) {
		return new PythonGruntRunnerBuilder(installation);
	}
	
	private PythonGruntRunnerBuilder(String installation) {
		super();
		this.installation = installation;
	}
	
	public GruntRunner run() throws IOException, InterruptedException, SubProcessException {

		GruntRunner runner = new PythonGruntRunner(this);
		runner.run();
		return runner;
		
	}
	
}
