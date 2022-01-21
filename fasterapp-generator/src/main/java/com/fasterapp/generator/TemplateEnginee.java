package com.fasterapp.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;

public class TemplateEnginee {
    public static void generate(ClassMetaData classMetaData) throws Exception{
        String[] templates = new String[]{"dto.java.ftl","convertor.java.ftl","mapper.java.ftl","service.java.ftl","serviceImpl.java.ftl","mapper.xml.ftl","script.sql.ftl"};
        String[] fileNames = new String[]{
            "dto"+File.separator+"{0}Dto.java",
            "dto"+File.separator+"convertor"+File.separator+"{0}Convertor.java",
            "mapper"+File.separator+"{0}Mapper.java",
            "service"+File.separator+"I{0}Service.java",
            "service"+File.separator+"impl"+File.separator+"{0}ServiceImpl.java",
            "mapper"+File.separator+"{0}Mapper.xml",
            "scripts"+File.separator+"{0}.sql"};
        //通过Freemarker的Configuration读取相应的ftl，这里是对应的你使用jar包的版本号：<version>2.3.23</version>
        Configuration configuration = new Configuration();

        //第二个参数 为你对应存放.ftl文件的包名
        configuration.setClassForTemplateLoading(TemplateEnginee.class, "/templates");

        String javadir = classMetaData.getBasePath() + "/src/main/java/" + classMetaData.getBasePackage().replaceAll("[.]","/") + File.separator;
        String mapperDir = javadir; //context.get("basePath").toString() + "/src/main/resources" +  File.separator;
        String scriptDir = classMetaData.getBasePath()  + File.separator;
        String fullPath;

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
