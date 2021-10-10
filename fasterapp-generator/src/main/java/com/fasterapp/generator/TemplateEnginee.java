package com.fasterapp.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;

public class TemplateEnginee {
    public static void generate(ClassMetaData classMetaData) throws Exception{
        String[] templates = new String[]{"api.java.ftl","mapper.java.ftl","service.java.ftl","serviceImpl.java.ftl","mapper.xml.ftl","script.sql.ftl"};
        String[] fileNames = new String[]{"apis"+File.separator+"{0}Api.java","mappers"+File.separator+"{0}Mapper.java","services"+File.separator+"I{0}Service.java","services"+File.separator+"impl"+File.separator+"{0}ServiceImpl.java","mappers"+File.separator+"{0}Mapper.xml","scripts"+File.separator+"{0}.sql"};
        //通过Freemarker的Configuration读取相应的ftl
        Configuration configuration = new Configuration();//这里是对应的你使用jar包的版本号：<version>2.3.23</version>

        //第二个参数 为你对应存放.ftl文件的包名
        configuration.setClassForTemplateLoading(TemplateEnginee.class, "/templates");

        String javadir = classMetaData.getBasePath() + "/src/main/java/" + classMetaData.getBasePackage().replaceAll("[.]","/") + File.separator;
        String mapperDir = javadir; //context.get("basePath").toString() + "/src/main/resources" +  File.separator;
        String scriptDir = classMetaData.getBasePath()  + File.separator;
        String fullPath = null;

        File fileDir = null;
        for(int index = 0; index < templates.length; index++) {
            Template template = configuration.getTemplate(templates[index]);

            if(templates[index].equalsIgnoreCase("mapper.xml.ftl")){
                fullPath = MessageFormat.format(mapperDir + fileNames[index], classMetaData.getEntity().toString());
            }else if(templates[index].equalsIgnoreCase("script.sql.ftl")){
                fullPath = MessageFormat.format(scriptDir + fileNames[index], classMetaData.getTable().toLowerCase());
            }else {
                fullPath = MessageFormat.format(javadir + fileNames[index], classMetaData.getEntity().toString());
            }
            fileDir = new File(fullPath.substring(0, fullPath.lastIndexOf(File.separator)));
            if(! fileDir.exists()){
                fileDir.mkdirs();
            }

            fileWrite(template, classMetaData, fullPath);
        }
    }

    /**
     *
     * @param template
     */
    private static void fileWrite(Template template, ClassMetaData classMetaData, String outputFile) throws Exception{
        FileWriter out = null;
        try {
            out = new FileWriter(new File(outputFile));
            template.process(classMetaData, out);
        }finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
