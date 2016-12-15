package generator;

import generator.IClassModel.IClassType;

public class GraphVizClassTypeParser implements IParser<IClassType> {

    @Override
    public String parse(IClassType classType) {
        StringBuilder steroType = new StringBuilder();
        classType.switchByCase(new IClassModel.IClassType.Switcher() {
            @Override
            public void ifInterface() {
                steroType.append("\\<\\<Interface\\>\\>\\n");
            }

            @Override
            public void ifConcrete() {
            }

            @Override
            public void ifAbstract() {
                steroType.append("\\<\\<Abstract\\>\\>\\n");
            }

            @Override
            public void ifEnum() {
                steroType.append("\\<\\<Enumeration\\>\\>\n");
            }
        });
        return steroType.toString();
    }

}