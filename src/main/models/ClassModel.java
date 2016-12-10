package models;

import org.objectweb.asm.tree.ClassNode;

import java.util.List;

/**
 * Created by lamd on 12/7/2016.
 */
public class ClassModel implements IClassModel {
    private ClassNode asmClassNode;
    private boolean isFinal;

    public ClassType getType() {
        // TODO: write this.
        return null;
    }

    public List<FieldModel> getFields() {
        // TODO: write this.
        return null;
    }
}
