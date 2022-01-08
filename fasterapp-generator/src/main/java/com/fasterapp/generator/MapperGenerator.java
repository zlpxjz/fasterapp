package com.fasterapp.generator;

import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;

/**
 * @goal generate-mapper
 * @execute phase=compile
 */
@Execute(phase = LifecyclePhase.COMPILE)
public class MapperGenerator extends AbstractGenerator {
	/**
	 * 输出Mapper 文件
	 * @param cmd
	 * @throws Exception
	 */
	@Override
	protected void generator(ClassMetaData cmd) throws Exception{
		StatementTemplateEnginee.generate(getLog(), cmd);
	}
}
