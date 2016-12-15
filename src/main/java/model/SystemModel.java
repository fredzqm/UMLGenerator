package model;

import java.util.ArrayList;
import java.util.List;

import analyzer.IAnalyzerSystemModel;
import generator.ISystemModel;

/**
 * This class representing the entire model of a java program
 * 
 */
public class SystemModel implements ISystemModel, IAnalyzerSystemModel {
	private Iterable<ClassModel> importantList;

	private SystemModel(Iterable<ClassModel> importantList) {
		this.importantList = importantList;
	}

	@Override
	public Iterable<ClassModel> getClasses() {
		return importantList;
	}

	public static SystemModel getInstance(IModelConfiguration config) {
		ASMClassTracker asmParser = ASMParser.getInstance(config);

		List<ClassModel> ls = new ArrayList<>();
		asmParser.getClasses().forEach((c) -> {
			ls.add(c);
		});
		return new SystemModel(ls);
	}

}
