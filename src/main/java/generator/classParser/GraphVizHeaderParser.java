package generator.classParser;


public class GraphVizHeaderParser implements IParser<IClassModel> {

    GraphVizHeaderParser() {
    	
    }

    @Override
    public String parse(IClassModel classModel) {
    	StringBuilder sb = new StringBuilder();
    	for (String sterotype : classModel.getStereoTypes()) {
    		sb.append(String.format("\\<\\<%s\\>\\>\\n", sterotype));
    	}
    	sb.append(classModel.getName());
        return sb.toString();
    }

}
