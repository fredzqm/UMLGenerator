package analyzerClassParser;

import analyzer.IClassModel;

public class GraphVizHeaderParser implements IParser<IClassModel> {
	@Override
	public String parse(IClassModel classModel, IClassParserConfiguration config) {
		StringBuilder sb = new StringBuilder();
		for (String sterotype : classModel.getStereoTypes()) {
			sb.append(String.format("\\<\\<%s\\>\\>\\n", sterotype));
		}
		sb.append(classModel.getName());
		return sb.toString();
	}
}
