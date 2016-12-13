package main.java.model;

import java.util.ArrayList;
import java.util.List;

import main.java.analyzer.IAnalyzerSystemModel;
import main.java.generator.IClassModel;
import main.java.generator.IGeneratorSystemModel;

/**
 * TODO: Fred and all public methods.
 */
public class SystemModel implements IGeneratorSystemModel, IAnalyzerSystemModel {
	private ASMServiceProvider asmServiceProvider;

	private List<ClassModel> importantClasses;

	public SystemModel(Iterable<String> classNameList, ASMServiceProvider asmParser) {
		asmServiceProvider = asmParser;
		importantClasses = new ArrayList<>();
		for (String className : classNameList) {
			importantClasses.add(asmServiceProvider.getClassByName(className));
		}
	}

	@Override
	public List<ClassModel> getClasses() {
		return importantClasses;
	}

}
