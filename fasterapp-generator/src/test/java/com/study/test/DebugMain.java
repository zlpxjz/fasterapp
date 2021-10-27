package com.study.test;

import com.fasterapp.generator.ClassMetaData;
import com.fasterapp.generator.CodeGenerator;

import java.io.File;

/**
 * Created by Tony on 2021/10/22.
 */
public class DebugMain extends CodeGenerator{

	public static  void main(String[] args) throws Exception{
		File file = new File("d:/tmp/1.pdf");

		DebugMain debugMain = new DebugMain();

		ClassLoader classLoader = DebugMain.class.getClassLoader();
		Class<?> clazz = debugMain.loadClass(classLoader, "com.study.test.DemoModel");
		ClassMetaData cmd = new ClassMetaData();

		debugMain.parseModelClass(clazz, cmd);

		System.out.println();
	}
}
