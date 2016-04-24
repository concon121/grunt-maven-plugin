package net.cbr.software.grunt_runner;

import static net.cbr.software.grunt_runner.PythonGruntRunner.NORMAL_EXIT;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.junit.Before;
import org.junit.Test;

import net.cbr.software.grunt_runner.exception.SubProcessException;
import net.cbr.software.grunt_runner.python.PythonGruntRunner;
import net.cbr.software.grunt_runner.python.PythonGruntRunnerBuilder;
import net.cbr.software.grunt_runner.utils.JsonHelper;

public class PythonGruntRunnerTest {

	private static String VERIFY = "verify.py";
	
	private PythonGruntRunner runner;
	private PythonGruntRunnerBuilder builder;
	private String pythonInstallation = "C:/Users/conno/AppData/Local/Programs/Python/Python35-32/python";
	private Map<String, String> prereqs;
	
	@Before
	public void setUp() {
		prereqs = new HashMap<String, String>();
		prereqs.put("ruby", "--version");
		prereqs.put("node", "--version");
		prereqs.put("compass", "--version");
		
		builder = (PythonGruntRunnerBuilder) PythonGruntRunnerBuilder.newInstance(pythonInstallation).with(prereqs).with(new SystemStreamLog());
		runner = new PythonGruntRunner(builder);
	}
	
	@Test
	public void testCheckPreReqs() throws MojoExecutionException, IOException, InterruptedException, SubProcessException {
		runner.exec(VERIFY, "TEST", JsonHelper.toJSON(prereqs));
		assertEquals(NORMAL_EXIT, runner.getLastExitCode());
	}
	
}
