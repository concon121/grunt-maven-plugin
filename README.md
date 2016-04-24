# grunt-maven-plugin
Simple maven plugin for running grunt tasks as part of the maven build process

## Configuration

### workingDirectory
The working directory is the directory which contains your package.json and your Gruntfile.js/ Gruntfile.coffee.

### pythonInstallation
As the current implementation relies on Python to create native system processes, the application needs to know where to find Python!  The value of this configuration item should be the absolute path of the Python application on your computer.

### prereqs
Grunt uses and depends on many different packages and libraries.  The prerequisites are applications which you expect to be installed on your system.  The prerequisites are designed to be a list of key value pairs, where the key is the application you expect to be installed and the value is a simple command for the application which when executed you expect to return a 0 exit code.

Consider the following example:

    <prereqs>
        <ruby>--version</ruby>
    </prereqs>

This configuration would execute the shell command:

    ruby --version

### gruntFileExtension
Grunt supports both JavaScript and CoffeeScript, this configuration item just tell's the plugin which one you are using.

Accepted values are:

    <gruntFileExtension>JS</gruntFileExtension>
    <gruntFileExtension>COFFEE</gruntFileExtension>

If no extension is defined, the plugin will assume you are using JavaScript.

### Full Example

			<plugin>
				<groupId>net.cbr.software</groupId>
				<artifactId>grunt-maven-plugin</artifactId>
				<version>0.0.1-SNAPSHOT</version>
				<executions>
					<execution>
						<goals>
							<goal>grunt</goal>
						</goals>
						<configuration>
							<workingDirectory>${basedir}/src/main/webapp/assets</workingDirectory>
							<pythonInstallation>C:/path/to/my/python/installation/Programs/Python/Python35-32/python</pythonInstallation>
							<prereqs>
								<ruby>--version</ruby>
								<compass>--version</compass>
								<node>--version</node>
							</prereqs>
							<gruntFileExtension>JS</gruntFileExtension>
						</configuration>
					</execution>
				</executions>
			</plugin>
