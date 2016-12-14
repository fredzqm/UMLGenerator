package model;

import analyzer.IAnalyzerSystemModel;
import config.Configuration;
import generator.IGeneratorSystemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Fred and all public methods.
 */
public class SystemModel implements IGeneratorSystemModel, IAnalyzerSystemModel {
	private ASMServiceProvider asmServiceProvider;
	private List<ClassModel> importantClasses;

	public SystemModel(Iterable<String> classList, ASMServiceProvider asmParser) {
		asmServiceProvider = asmParser;
		importantClasses = new ArrayList<>();
		for (String className : classList) {
			importantClasses.add(asmServiceProvider.getClassByName(className));
		}
	}

	@Override
	public List<ClassModel> getClasses() {
		return importantClasses;
	}

	public static SystemModel getInstance(IModelConfiguration config) {
		ASMServiceProvider asmParser;
		if (config.isRecursive())
			asmParser = new ASMParser(config.getClasses());
		else
			asmParser = new NonRecursiveASMParser(config.getClasses());

		return new SystemModel(config.getClasses(), asmParser);
	}

}
