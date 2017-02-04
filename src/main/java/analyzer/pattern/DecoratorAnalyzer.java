package analyzer.pattern;

import analyzer.utility.IClassModel;
import analyzer.utility.ISystemModel;
import analyzer.utility.ProcessedSystemModel;

public class DecoratorAnalyzer extends PatternAnalyzer {
    
    @Override
    public boolean acceptPossiblePattern(IClassModel clazz, IClassModel comp, IClassModel sup) {
        return false;
    }

    @Override
    public ISystemModel getProcessedSystemModel() {
        return new ProcessedSystemModel(classes, relations);
    }

}
