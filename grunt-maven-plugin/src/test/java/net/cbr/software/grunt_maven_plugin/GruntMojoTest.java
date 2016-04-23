package net.cbr.software.grunt_maven_plugin;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.maven.plugin.MojoExecutionException;
import org.junit.Before;
import org.junit.Test;

public class GruntMojoTest {

	private GruntMojo mojo;
	private String pythonInstallation = "C:/Users/conno/AppData/Local/Programs/Python/Python35-32/python";
	private Map<String, String> prereqs;
	
	@Before
	public void setUp() {
		
		prereqs = new HashMap<String, String>();
		prereqs.put("ruby", "--version");
		prereqs.put("node", "--version");
		prereqs.put("compass", "--version");
		
		mojo = new GruntMojo();
		mojo.setPythonInstallation(pythonInstallation);
		mojo.setPrereqs(prereqs);
	}
	
	@Test
	public void testCheckPreReqs() throws MojoExecutionException, IOException, InterruptedException {
		mojo.checkPreReqs();
		assertTrue(true);
	}
	
}

