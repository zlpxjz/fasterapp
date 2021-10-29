package com.fasterapp.generator;

import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;

/**
 * @goal generate-full
 * @phase generate-sources
 * @requiresDependencyResolution compile
 */
@Execute(phase = LifecyclePhase.COMPILE)
public class FullGenerator extends AbstractGenerator {
	/**
	 * 根据Model元数据生成代码
	 * @param cmd
	 * @throws Exception
	 */
	@Override
	protected void generator(ClassMetaData cmd) throws Exception{
		TemplateEnginee.generate(cmd);
	}

}
