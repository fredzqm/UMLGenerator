package analyzerClassParser;

import java.util.ArrayList;
import java.util.Collection;

import analyzer.ClassModelFilter;
import analyzer.IASystemModel;
import analyzer.IAnalyzer;
import analyzer.IAnalyzerConfiguraton;
import analyzer.IClassModel;
import analyzer.SystemModelFiler;

public class ClassParserAnalyzer implements IAnalyzer {

	@Override
	public IASystemModel analyze(IASystemModel systemModel, IAnalyzerConfiguraton config) {
		Object c = config.getConfigurationFor(ClassParserAnalyzer.class);
		if (!(c instanceof IClassParserConfiguration)) {
			throw new RuntimeException(c + " is not a IClassParserConfiguration");
		}
		IParser<IClassModel> classParser = new GraphVizClassParser((IClassParserConfiguration) c);

		return new SystemModelFiler(systemModel) {
			@Override
			public Collection<? extends IClassModel> getClasses() {
				Collection<IClassModel> classes = new ArrayList<>();
				super.getClasses().forEach((c) -> {
					classes.add(new ClassModelFilter(c) {
						public String getLabel() {
							return classParser.parse(c);
						}
					});
				});
				return classes;
			}
		};
	}

}
