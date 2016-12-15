package model;

import analyzer.IAnalyzerSystemModel;
import generator.ISystemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * This class representing the entire model of a java program
 * 
 */
public class SystemModel implements ISystemModel, IAnalyzerSystemModel {
	private AbstractASMParser asmServiceProvider;

	public SystemModel(Iterable<String> classList, AbstractASMParser asmParser) {
		asmServiceProvider = asmParser;
	}

	@Override
	public Iterable<ClassModel> getClasses() {
		return asmServiceProvider.getImportantClasses();
	}

	public static SystemModel getInstance(IModelConfiguration config) {
		AbstractASMParser asmParser;
		if (config.isRecursive())
			asmParser = new ASMParser(config.getClasses());
		else
			asmParser = new NonRecursiveASMParser(config.getClasses());

		return new SystemModel(config.getClasses(), asmParser);
	}

}
