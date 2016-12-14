package generator;

/**
 * A GraphVizParser for the Header.
 *
 * Created by lamd on 12/14/2016.
 */
public class GraphVizHeaderParser implements IGraphVizHeader {
    private String name;
    private String steroType;
    private StringBuilder header;

    public GraphVizHeaderParser(IClassModel.IClassType type, String className) {
        this.name = className;
        this.header = new StringBuilder();
        generateHeader(type, className);
    }


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getStereoType() {
        return this.steroType;
    }

    @Override
    public String getOutput() {
        return this.header.toString();
    }

    private void generateHeader(IClassModel.IClassType type, String className) {
        type.switchByCase(new IClassModel.IClassType.Switcher() {
            @Override
            public void ifInterface() {
                GraphVizHeaderParser.this.steroType = "\\<\\<Interface\\>\\>\\n";
                GraphVizHeaderParser.this.name = className;
                GraphVizHeaderParser.this.header.append(GraphVizHeaderParser.this.steroType);
                GraphVizHeaderParser.this.header.append(GraphVizHeaderParser.this.name);
            }

            @Override
            public void ifConcrete() {
                GraphVizHeaderParser.this.name = className;
                GraphVizHeaderParser.this.steroType = null;
                GraphVizHeaderParser.this.header.append(GraphVizHeaderParser.this.name);
            }

            @Override
            public void ifAbstract() {
                GraphVizHeaderParser.this.steroType = "\\<\\<Abstract\\>\\>\\n";
                GraphVizHeaderParser.this.name = className;
                GraphVizHeaderParser.this.header.append(steroType);
                GraphVizHeaderParser.this.header.append(GraphVizHeaderParser.this.name);
            }

            @Override
            public void ifEnum() {
                GraphVizHeaderParser.this.steroType = "\\<\\<Enumeration\\>\\>\n";
                GraphVizHeaderParser.this.name = className;
                GraphVizHeaderParser.this.header.append(GraphVizHeaderParser.this.steroType);
                GraphVizHeaderParser.this.header.append(GraphVizHeaderParser.this.name);
            }
        });
    }
}
