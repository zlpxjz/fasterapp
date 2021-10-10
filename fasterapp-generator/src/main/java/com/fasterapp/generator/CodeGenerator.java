package com.fasterapp.generator;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

/**
 * @goal code-generator
 * @phase package
 * @requiresDependencyResolution compile
 */
public class CodeGenerator extends AbstractMojo {
	/**
     * @parameter default-value="${project}"
	 * @required
	 * @readonly
     */
	public MavenProject project;

	@Override
	public void  execute()  throws MojoExecutionException,MojoFailureException {
		try {
			String modelName = System.getProperty("modelName");
			String types = System.getProperty("types");

			Class clazz = this.getClassLoader(project).loadClass(modelName);


			getLog().info(clazz.getName());

		}catch(Exception exc){
			throw new MojoExecutionException("Exception raised when generating code:", exc);
		}
	}

	/**
	 *
	 * @param project
	 * @return
	 */
	private ClassLoader getClassLoader(MavenProject project){
		try{
			List classpathElements = project.getCompileClasspathElements();
			classpathElements.add( project.getBuild().getOutputDirectory() );
			classpathElements.add( project.getBuild().getTestOutputDirectory() );
			URL urls[] = new URL[classpathElements.size()];
			for ( int i = 0; i < classpathElements.size(); ++i ){
				urls[i] = new File( (String) classpathElements.get( i ) ).toURL();
			}
			return new URLClassLoader( urls, this.getClass().getClassLoader() );
		}catch ( Exception e ){
			e.printStackTrace();
			return this.getClass().getClassLoader();
		}
	}
}
