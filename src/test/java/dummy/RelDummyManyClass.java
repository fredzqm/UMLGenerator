package dummy;

import java.util.ArrayList;
import java.util.List;

/**
 * A Dummy Class used for testing the model's ability to detect one-to-many relationships when they are not listed as fields.`
 * <p>
 * Created by lamd on 12/12/2016.
 */
public class RelDummyManyClass {
    public void inputManyDependencies(List<RelOtherDummyClass> many) {
        ;
    }

    private void printPrivateString() {
        List<RelOtherDummyClass> manyDependencies = new ArrayList<>();
    }

    public RelOtherDummyClass getPublicString() {
        return new RelOtherDummyClass(2);
    }
}
