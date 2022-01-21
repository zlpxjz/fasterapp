package com.fasterapp.generator;

import com.fasterapp.generator.annotations.Column;
import com.fasterapp.generator.annotations.Model;
import com.fasterapp.generator.annotations.Transient;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * Created by Tony on 2021/10/29.
 */
public abstract class AbstractGenerator extends AbstractMojo {
	/**
	 * @parameter default-value="${project}"
	 * @required
	 * @readonly
	 */
	public MavenProject project;

	/**
	 * @parameter expression="${model}"
	 * @required
	 * @readonly
	 */
	private String models;

	/**
	 * @parameter expression="${servicePath}"
	 * @required
	 * @readonly
	 */
	private String servicePath;

	/**
	 * @parameter expression="${mapperPath}"
	 * @required
	 * @readonly
	 */
	private String mapperPath;

	/**
	 * @parameter expression="${mapperXmlPath}"
	 * @required
	 * @readonly
	 */
	private String mapperXmlPath;

	@Override
	public void  execute()  throws MojoExecutionException,MojoFailureException {
		ClassMetaData cmd = new ClassMetaData();
		List<String> modelList = new ArrayList<>();
		if(models.endsWith("*")){
			modelList.addAll(getModels(models));
		}else{
			modelList.add(models);
		}

		if(modelList.isEmpty()){
			getLog().warn("No model found to generate code..............");
			return;
		}

		ClassLoader clazzLoader = this.getClassLoader(project);
		for(String modelClass : modelList) {
			getLog().info("Generating code for model=" + modelClass);

			try {
				Class clazz = this.loadClass(clazzLoader, modelClass);
				if(! clazz.isAnnotationPresent(Model.class)) {
					continue;
				}

				cmd.setModel(clazz.getName());
				cmd.setBasePath(getModuleSourcePath(project));
				parseModelClass(clazz, cmd);
			} catch (Exception exc) {
				getLog().error("Exception raised when parsing model class:", exc);
				throw new MojoExecutionException("Exception raised when parsing model class:", exc);
			}

			try {
				getLog().info("*************************************************");
				getLog().info("Project Directory=" + cmd.getBasePath());
				getLog().info("Model Name=" + cmd.getModel());
				getLog().info("Model Name=" + cmd.getEntity());
				getLog().info("*************************************************");

				this.generator(cmd);
			} catch (Exception exc) {
				getLog().error("Exception raised when generating code:", exc);
				throw new MojoExecutionException("Exception raised when generating code:", exc);
			}
		}
	}

	/**
	 * 获取包下的类文件
	 * @param packageName
	 * @return
	 */
	private List<String> getModels(String packageName) {
		List<String> modelList = new ArrayList<>();
		try {
			String modelPackage = packageName.substring(0, packageName.lastIndexOf("."));
			getLog().info("Getting model from package=" + modelPackage);
			String packageDirName =modelPackage.replace(".", "/");

			ClassLoader clazzLoader = this.getClassLoader(project);
			Enumeration<URL> dirs = clazzLoader.getResources(packageDirName);
			while (dirs.hasMoreElements()) {
				String path = dirs.nextElement().getPath();
				getLog().info("Getting model from dir=" + path);
				File dir = new File(path);
				File[] files = dir.listFiles(new FileFilter() {
					@Override
					public boolean accept(File pathname) {
						return pathname.getName().endsWith(".class");
					}
				});

				if (files.length > 0) {
					String fileName;
					for (File file : files) {
						fileName = file.getName();
						modelList.add(modelPackage + "." + fileName.substring(0, fileName.indexOf(".")));
					}
				}
			}
		}catch(Exception exc){
			exc.printStackTrace();
		}

		return modelList;
	}

	/**
	 *
	 * @param clazz
	 * @param cmd
	 * @throws Exception
	 */
	protected void parseModelClass(Class clazz, ClassMetaData cmd) throws Exception{
		if(! clazz.isAnnotationPresent(Model.class)) {
			throw new Exception("Model class misses Model annotation.");
		}
		Model modelAnnotation = (Model)clazz.getAnnotation(Model.class);
		cmd.setEntity(modelAnnotation.name());
		cmd.setTable(modelAnnotation.table());

		String sPackage = clazz.getPackage().getName();
		cmd.setBasePackage(sPackage.substring(0, sPackage.lastIndexOf(".")));

		ParameterizedType p = (ParameterizedType) clazz.getGenericSuperclass();
		Class<?> pkClass = (Class) p.getActualTypeArguments()[0];
		cmd.setPkClass(pkClass);
		cmd.setPkFullType(pkClass.getName());
		cmd.setPkType(pkClass.getSimpleName());

		List<Field> fields = getAllFields(clazz);
		cmd.setFields(parseModelClassFields(cmd, fields));
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
	 *
	 * @param classLoader
	 * @param modelClass
	 * @return
	 * @throws Exception
	 */
	protected Class<?> loadClass(ClassLoader classLoader, String modelClass) throws Exception{
		return classLoader.loadClass(modelClass);
	}

	/**
	 * 根据Model元数据生成代码
	 * @param cmd
	 * @throws Exception
	 */
	protected abstract void generator(ClassMetaData cmd) throws Exception;

	/**
	 *
	 * @param fields
	 * @return
	 * @throws Exception
	 */
	private List<FieldMetaData> parseModelClassFields(ClassMetaData cmd, List<Field> fields) throws Exception{
		List<FieldMetaData> fieldMetaDataList = new ArrayList<>();

		for(Field field : fields){
			if(field.isAnnotationPresent(Transient.class)) continue;

			Column column = field.getAnnotation(Column.class);
			if(column == null){
				FieldMetaData metaData = this.getFieldMetaData(field);
				if(metaData != null) {
					fieldMetaDataList.add(metaData);
				}
			}else if(column.key()){
				fieldMetaDataList.addAll(getKeyFields(cmd, field));
			}else{
				FieldMetaData metaData = this.getFieldMetaData(field, column);
				if(metaData != null) {
					fieldMetaDataList.add(metaData);
				}
			}
		}

		return fieldMetaDataList;
	}

	/**
	 *
	 * @param field
	 * @return
	 * @throws Exception
	 */
	private FieldMetaData getFieldMetaData(Field field) throws Exception{
		FieldMetaData metaData = new FieldMetaData();

		metaData.setName(field.getName());
		//metaData.setColumnDefinition(column.type());
		metaData.setColumnName(underscoreName(metaData.getName()));
		metaData.setJavaType(field.getType().getName());

		return metaData;
	}

	/**
	 * 小驼峰方式转数据库字段
	 * @param name
	 * @return
	 */
	protected String underscoreName(String name) {
		if (name == null || name.trim().equals("")) {
			return "";
		} else {
			StringBuilder result = new StringBuilder();
			result.append(this.lowerCaseName(name.substring(0, 1)));

			for(int i = 1; i < name.length(); ++i) {
				String s = name.substring(i, i + 1);
				String slc = this.lowerCaseName(s);
				if (!s.equals(slc)) {
					result.append("_").append(slc);
				} else {
					result.append(s);
				}
			}

			return result.toString();
		}
	}

	protected String lowerCaseName(String name) {
		return name.toLowerCase(Locale.US);
	}

	/**
	 *
	 * @param field
	 * @return
	 * @throws Exception
	 */
	private FieldMetaData getFieldMetaData(Field field, Column column) throws Exception{
		FieldMetaData metaData = new FieldMetaData();

		metaData.setName(field.getName());
		metaData.setColumnDefinition(column.type());
		metaData.setColumnName(column.name());
		metaData.setJavaType(field.getType().getName());

		return metaData;
	}

	/**
	 * 解析PK字段
	 * @param classMetaData
	 * @param field
	 * @return
	 * @throws Exception
	 */
	private List<FieldMetaData> getKeyFields(ClassMetaData classMetaData, Field field) throws Exception{
		List<FieldMetaData> fieldList = new ArrayList<>();

		Class<?> clazz = classMetaData.getPkClass();
		classMetaData.setPkType(clazz.getSimpleName());
		classMetaData.setPkFullType(clazz.getCanonicalName());
		if(clazz.equals(Integer.class)){
			FieldMetaData metaData = new FieldMetaData();
			metaData.setName(field.getName());
			metaData.setColumnDefinition(" integer unsigned not null auto_increment ");
			metaData.setColumnName("id");
			metaData.setJavaType(Integer.class.getName());
			metaData.setLength(11);
			metaData.setPrecious(0);
			metaData.setKey(true);
			fieldList.add(metaData);
		}else if(clazz.equals(String.class)){
			FieldMetaData metaData = new FieldMetaData();
			metaData.setName(field.getName());
			metaData.setColumnDefinition(" varchar(32) not null ");
			metaData.setColumnName("id");
			metaData.setJavaType(String.class.getName());
			metaData.setLength(32);
			metaData.setKey(true);
			fieldList.add(metaData);
		}else if(clazz.getSuperclass().getSimpleName().equals("ModelKey")){

		}else{
			throw new Exception("Not support primary key type=" + clazz.getName());
		}

		return fieldList;
	}

	/**
	 * 获取源码基础路径
	 * @param project
	 * @return
	 */
	private String getModuleSourcePath(MavenProject project){
		return project.getBasedir().getPath();
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
		getLog().error("Exception raised when getting classLoader。", e);
			return this.getClass().getClassLoader();
		}
	}
}
