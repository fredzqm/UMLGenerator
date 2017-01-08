package analyzerClassParser;

import java.util.ArrayList;
import java.util.Collection;

import analyzer.IClassModel;
import analyzer.IClassModelFilter;
import analyzer.ISystemModel;
import analyzer.ISystemModelFilter;

public class ParseClassSystemModel extends ISystemModelFilter {
	IParser<IClassModel> classParser;
	
	public ParseClassSystemModel(ISystemModel systemModel, IParser<IClassModel> classParser) {
		super(systemModel);
		this.classParser = classParser;
	}

	@Override
	public Collection<? extends IClassModel> getClasses() {
		Collection<IClassModel> classes = new ArrayList<>();
		super.getClasses().forEach((c) -> {
			classes.add(new IClassModelFilter(c) {
				public String getLabel() {
					return classParser.parse(c);
				}
			});
		});
		return classes;
	}

}
