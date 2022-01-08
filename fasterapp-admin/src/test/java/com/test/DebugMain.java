package com.test;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.Enumeration;

/**
 * Created by Tony on 2022/1/7.
 */
public class DebugMain {
	public static void main(String[] args) throws Exception {
		String modelPackage = "com.fasterapp.admin.model";
		String packageDirName = modelPackage.replace(".", "/");
		Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
		while (dirs.hasMoreElements()) {
			String path = dirs.nextElement().getPath();
			System.out.println(path);
			File dir = new File(path);
			File[] files = dir.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					return pathname.getName().endsWith(".class");
				}
			});

			if(files.length > 0){
				String fileName;
				for(File file : files){
					fileName = file.getName();
					System.out.println(fileName.substring(0, fileName.indexOf(".")));
				}
			}
		}
	}
}
