package net.cbr.software.grunt_runner.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.maven.plugin.logging.Log;

public class PythonHelper {

	private static final String PYTHON = "py";
	private static final String TARGET = "target/";
	
	public static String getPythonScript(String location) throws IOException {

		URL url = PythonHelper.class.getClassLoader().getResource(location);

		Scanner readFile = new Scanner(url.openStream());
			
		File pythonScript = new File(TARGET + location);
		BufferedWriter writer = new BufferedWriter(new FileWriter(pythonScript));

		while (readFile.hasNextLine()) {
			writer.append(readFile.nextLine());
			writer.append("\n");
		}
		writer.close();
		readFile.close();

		return pythonScript.getAbsolutePath();

	}

	public static int execPythonScript(Log log, String script, String... args) throws IOException, InterruptedException {
		ProcessBuilder builder = new ProcessBuilder(aggregateArguments(script, args));
		Process process = builder.start();
		process.waitFor();
		logProcessOutput(process, log);
		return process.exitValue();
	}
	
	private static List<String> aggregateArguments(String script, String... args) {
		
		List<String> arguments = new ArrayList<String>();
		arguments.add(PYTHON);
		arguments.add(script);
		
		for(String arg : args) {
			arguments.add(arg);
		}
		
		return arguments;
		
	}

	private static void logProcessOutput(Process process, Log log) {
		Scanner scanner = new Scanner(process.getInputStream());
		Scanner error = new Scanner(process.getErrorStream());

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			log.info(line);
		}

		while (error.hasNextLine()) {
			String line = error.nextLine();
			log.error(line);
		}

		scanner.close();
		error.close();
	}

}
