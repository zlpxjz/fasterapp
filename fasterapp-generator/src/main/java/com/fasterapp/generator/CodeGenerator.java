package com.fasterapp.generator;

import com.fasterapp.demo.models.DemoModel;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
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
import java.text.MessageFormat;
import java.util.*;

/**
 * @goal generate-code
 * @phase generate-sources
 * @requiresDependencyResolution compile
 */
@Execute(phase = LifecyclePhase.COMPILE)
public class CodeGenerator extends AbstractMojo {
	/**
     * @parameter default-value="${project}"
	 * @required
	 * @readonly
     */
	public MavenProject project;

	@Override
	public void  execute()  throws MojoExecutionException,MojoFailureException {
		System.out.print("Input Model Class:");
		Scanner sc = new Scanner(System.in);
		String modelClass = null;
		while (true) {
			modelClass = sc.nextLine();
			if (modelClass != null && !modelClass.trim().equals("")){
				break;
			}

			System.out.print("Input Model Class:");
		}

		ClassMetaData cmd = new ClassMetaData();
		try {
			ClassLoader clazzLoader = this.getClassLoader(project);
			Class clazz = this.loadClass(clazzLoader, modelClass);
			cmd.setModel(clazz.getName());
			cmd.setBasePath(getModuleSourcePath(project));
			parseModelClass(clazz, cmd);
		}catch(Exception exc) {
			throw new MojoExecutionException("Exception raised when parsing model class:", exc);
		}

		try{
			getLog().info("*************************************************");
			getLog().info("Project Directory=" + cmd.getBasePath());
			getLog().info("Model Name=" + cmd.getModel());
			getLog().info("Entity Name=" + cmd.getEntity());
			getLog().info("*************************************************");

			String fileName = "mappers\\{0}Mapper.xml";
			String mapperDir = cmd.getBasePath() + "/src/main/java/" + cmd.getBasePackage().replaceAll("[.]", "/") + File.separator;
			String fullPath = MessageFormat.format(mapperDir + fileName, cmd.getEntity().toString());
			File file = new File(fullPath);
			String[] codeTypes = null;
			if(file.exists()){
				codeTypes = new String[]{"mapper.xml"};
			}
			this.generator(cmd, codeTypes);
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
	protected void parseModelClass(Class clazz, ClassMetaData cmd) throws Exception{
		if(! clazz.isAnnotationPresent(Entity.class)) {
			throw new Exception("Model class misses Entity annotation.");
		}
		Entity entityAnnotation = (Entity)clazz.getAnnotation(Entity.class);
		cmd.setEntity(entityAnnotation.name());


		if(! clazz.isAnnotationPresent(Table.class)) {
			throw new Exception("Model class misses Table annotation.");
		}
		Table tableAnnotation = (Table)clazz.getAnnotation(Table.class);
		cmd.setTable(tableAnnotation.name());

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
	 * @param codeTypes
	 * @throws Exception
	 */
	private void generator(ClassMetaData cmd, String[] codeTypes) throws Exception{
		if(codeTypes != null && codeTypes.length == 1){
			if(codeTypes[0].equalsIgnoreCase("mapper.xml")){
				StatementTemplateEnginee.generate(getLog(), cmd);
			}
		}else {
			TemplateEnginee.generate(cmd);
		}
	}

	/**
	 *
	 * @param fields
	 * @return
	 * @throws Exception
	 */
	private List<FieldMetaData> parseModelClassFields(ClassMetaData cmd, List<Field> fields) throws Exception{
		List<FieldMetaData> fieldMetaDataList = new ArrayList<>();

		for(Field field : fields){
			if(field.isAnnotationPresent(Id.class)){
				fieldMetaDataList.addAll(getKeyFields(cmd, field));
			}else {
				fieldMetaDataList.add(getFieldMetaData(field));
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

		Column column = field.getAnnotation(Column.class);
		metaData.setName(field.getName());
		metaData.setColumnDefinition(column.columnDefinition());
		metaData.setColumnName(column.name());
		metaData.setJavaType(field.getType().getName());
		metaData.setLength(column.length());
		metaData.setPrecious(column.precision());

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
			metaData.setJavaType(field.getType().getName());
			metaData.setLength(11);
			metaData.setPrecious(0);
		}else if(clazz.equals(String.class)){
			FieldMetaData metaData = new FieldMetaData();
			metaData.setName(field.getName());
			metaData.setColumnDefinition(" varchar(32) not null ");
			metaData.setColumnName("id");
			metaData.setJavaType(field.getType().getName());
			metaData.setLength(32);
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
	private String getModuleSourcePath(MavenProject  project){
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
