package analyzer.utility;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by lamd on 1/15/2017.
 */
public class ClassModelStyleDecorator extends IClassModelFilter {
    private final String nodeStyle;
    private final String stereoType;

    public ClassModelStyleDecorator(IClassModel classModel, String nodeStyle, String stereoType) {
        super(classModel);
        this.nodeStyle = nodeStyle;
        this.stereoType = stereoType;
    }

    public ClassModelStyleDecorator(IClassModel classModel, String nodeStyle) {
        this(classModel, nodeStyle, null);
    }

    @Override
    public List<String> getStereoTypes() {
        if (this.stereoType == null) {
            return super.getStereoTypes();
        }

        List<String> ls = new LinkedList<>(super.getStereoTypes());
        ls.add(this.stereoType);
        return ls;
    }

    @Override
    public String getNodeStyle() {
        return super.getNodeStyle() + " " + this.nodeStyle;
    }

}
