package dummy;

import java.util.ArrayList;
import java.util.List;

/**
 * A Dummy Class used for testing the model's ability to detect one-to-many relationships when they are not listed as fields.`
 * <p>
 * Created by lamd on 12/12/2016.
 */
public class RelDummyManyClass {
//	List<Dummy> dummyList;

    // Method that takes RelOtherDummyClass List as input.
    public void inputManyDependencies(List<RelOtherDummyClass> many) {
        ;
    }

    private void printPrivateString() {
        // Method makes internal use of a array of RelOtherDummyClass
        RelOtherDummyClass[] manyDependencies = new RelOtherDummyClass[10];

        // Internal use of a generic.
        List<RelDummyClass> genericManyDependecies = new ArrayList<>();
    }

    // Method that returns RelOtherDummyClass
    public RelOtherDummyClass getPublicString() {
        return new RelOtherDummyClass(2);
    }
}
