package com.fasterapp.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.text.MessageFormat;

public class StatementTemplateEnginee {
    public static void generate(ClassMetaData classMetaData) throws Exception{
        String ftlName = "mapper-option.xml.ftl";
        String fileName = "mappers\\{0}Mapper.xml";
        //通过Freemarker的Configuration读取相应的ftl
        Configuration configuration = new Configuration();//这里是对应的你使用jar包的版本号：<version>2.3.23</version>

        //第二个参数 为你对应存放.ftl文件的包名
        configuration.setClassForTemplateLoading(StatementTemplateEnginee.class, "/templates");

        String mapperDir = classMetaData.getBasePath() + "/src/main/java/" + classMetaData.getBasePackage().replaceAll("[.]", "/") + File.separator;
        String fullPath = MessageFormat.format(mapperDir + fileName, classMetaData.getEntity().toString());

        BufferedReader reader = new BufferedReader(new FileReader(fullPath));
        StringBuffer buffer = new StringBuffer();
        String line, endTag = null;
        Template template = configuration.getTemplate(ftlName);
        while ((line = reader.readLine()) != null) {
            if (endTag != null) {
                if (endTag.equalsIgnoreCase(line.trim())) {
                    endTag = null;
                    buffer.append(line);
                    buffer.append("\n");
                }
                continue;
            }

            buffer.append(line).append("\n");
            if (line.contains("<resultMap") && line.contains("BaseResultMap")) {
                classMetaData.setOption("BaseResultMap");
                StringWriter writer = new StringWriter();
                template.process(classMetaData, writer);
                buffer.append(writer.getBuffer().toString());
                endTag = "</resultMap>";
            } else if (line.contains("<sql") && line.contains("Base_Column_List")) {
                classMetaData.setOption("Base_Column_List");
                StringWriter writer = new StringWriter();
                template.process(classMetaData, writer);
                buffer.append(writer.getBuffer().toString());
                endTag = "</sql>";
            } else if (line.contains("<select") && line.contains("selectAll")) {
                classMetaData.setOption("selectAll");
                StringWriter writer = new StringWriter();
                template.process(classMetaData, writer);
                buffer.append(writer.getBuffer().toString());
                endTag = "</select>";
            } else if (line.contains("<select") && line.contains("selectByPrimaryKey")) {
                classMetaData.setOption("selectByPrimaryKey");
                StringWriter writer = new StringWriter();
                template.process(classMetaData, writer);
                buffer.append(writer.getBuffer().toString());
                endTag = "</select>";
            } else if (line.contains("<delete") && line.contains("deleteByPrimaryKey")) {
                classMetaData.setOption("deleteByPrimaryKey");
                StringWriter writer = new StringWriter();
                template.process(classMetaData, writer);
                buffer.append(writer.getBuffer().toString());
                endTag = "</delete>";
            } else if (line.contains("<insert") && line.contains("c")) {
                classMetaData.setOption("insert");
                StringWriter writer = new StringWriter();
                template.process(classMetaData, writer);
                buffer.append(writer.getBuffer().toString());
                endTag = "</insert>";
            } else if (line.contains("<update") && line.contains("updateByPrimaryKeySelective")) {
                classMetaData.setOption("updateByPrimaryKeySelective");
                StringWriter writer = new StringWriter();
                template.process(classMetaData, writer);
                buffer.append(writer.getBuffer().toString());
                endTag = "</update>";
            } else if (line.contains("<update") && line.contains("updateByPrimaryKey")) {
                classMetaData.setOption("updateByPrimaryKey");
                StringWriter writer = new StringWriter();
                template.process(classMetaData, writer);
                buffer.append(writer.getBuffer().toString());
                endTag = "</update>";
            }
        }

        reader.close();// 关闭输入流

        FileWriter fout = new FileWriter(fullPath);// 创建文件输出流
        fout.write(buffer.toString());// 把替换完成的字符串写入文件内
        fout.close();// 关闭输出流
    }
}
