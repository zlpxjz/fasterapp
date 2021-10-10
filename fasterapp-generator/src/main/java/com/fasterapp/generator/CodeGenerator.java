package com.fasterapp.generator;

import com.fasterapp.demo.models.DemoModel;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

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

			getLog().info("Begin generating code........");

			modelName = "com.fasterapp.modules.excel.apis.ExcelApi";

			Class clazz = this.getClassLoader(project).loadClass(modelName);
			getLog().info(clazz.getName());

			ClassMetaData cmd = new ClassMetaData();
			cmd.setBasePath(getModuleSourcePath(project));

			String sPackage = clazz.getPackage().getName();
			cmd.setBasePackage(sPackage.substring(0, sPackage.lastIndexOf(".")));

			parseModelClass(clazz, cmd);
			TemplateEnginee.generate(cmd);
		}catch(Exception exc){
			throw new MojoExecutionException("Exception raised when generating code:", exc);
		}
	}

	/**
	 *
	 * @param clazz
	 * @param cmd
	 * @throws Exception
	 */
	private void parseModelClass(Class clazz, ClassMetaData cmd) throws Exception{
		if(! clazz.isAnnotationPresent(Entity.class)) throw new Exception("Model class misses Entity annotation.");
		Entity entityAnnotation = (Entity)clazz.getAnnotation(Entity.class);
		cmd.setEntity(entityAnnotation.name());

		if(! clazz.isAnnotationPresent(Table.class)) throw new Exception("Model class misses Table annotation.");
		Table tableAnnotation = (Table)clazz.getAnnotation(Table.class);
		cmd.setTable(tableAnnotation.name());

		List<Field> fields = getAllFields(clazz);
		cmd.setFields(parseModelClassFields(fields));

		ParameterizedType p = (ParameterizedType) clazz.getGenericSuperclass();
		String pkType = p.getActualTypeArguments()[0].getTypeName();
		cmd.setPkFullType(pkType);
		cmd.setPkType(pkType.substring(pkType.lastIndexOf(".") + 1));

	}

	/**
	 *
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	private List<Field> getAllFields(Class<?> clazz){
		List<Field> fields = new ArrayList<>();

		Field[] declaredFields = clazz.getDeclaredFields();
		if(declaredFields != null && declaredFields.length > 0){
			fields.addAll(Arrays.asList(declaredFields));
		}

		Set<String> fieldNames = new HashSet<>();
		for(Field field : fields){
			fieldNames.add(field.getName());
		}

		Class<?> parentClass = clazz.getSuperclass();
		if(parentClass != Object.class){
			List<Field> superFields = getAllFields(parentClass);
			if(superFields != null){
				for(Field superField : superFields){
					if(! fieldNames.contains(superField.getName())){
						fields.add(superField);
					}
				}
			}
		}

		return fields;
	}


	/**
	 *
	 * @param fields
	 * @return
	 * @throws Exception
	 */
	private List<FieldMetaData> parseModelClassFields(List<Field> fields) throws Exception{
		List<FieldMetaData> fieldMetaDataList = new ArrayList<>();

		for(Field field : fields){
			FieldMetaData metaData = new FieldMetaData();

			if(field.isAnnotationPresent(Id.class)){
				metaData.setKey(true);
			}

			Column column = field.getAnnotation(Column.class);
			metaData.setName(field.getName());
			metaData.setColumnDefinition(column.columnDefinition());
			metaData.setColumnName(column.name());
			metaData.setJavaType(field.getType().getName());
			metaData.setLength(column.length());
			metaData.setPrecious(column.precision());

			fieldMetaDataList.add(metaData);
		}
		return fieldMetaDataList;
	}

	/**
	 * 获取源码基础路径
	 * @param project
	 * @return
	 */
	private String getModuleSourcePath(MavenProject  project){
		String basePath = project.getFile().getPath();

		return basePath;
	}


	/**
	 *
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

	public static void main(String [] args) throws Exception{
		CodeGenerator generator = new CodeGenerator();

		ClassMetaData cmd = new ClassMetaData();
		Class clazz = DemoModel.class;

		generator.parseModelClass(clazz, cmd);

		cmd.setBasePath("/Users/tony/workspace/fasterapp/fasterapp-generator");

		String sPackage = clazz.getPackage().getName();
		cmd.setBasePackage(sPackage.substring(0, sPackage.lastIndexOf(".")));
		TemplateEnginee.generate(cmd);

		System.out.println();
	}
}
