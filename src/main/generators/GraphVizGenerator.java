package generators;

import models.ClassModel;

/**
 * Created by lamd on 12/7/2016.
 */
public class GraphVizGenerator implements IGenerator {
//    public GraphVizGenerator(main.model.SystemModel sm, Configuration config) {
//
//    }

    public String parseClassModel(main.model.SystemModel sm) {
        for (ClassModel cm : sm.getClassModels()) {
            cm.getExtends();
            // This will create the arrow
            // Create string "<cm name> <arrow modifier> {labels of extended classes}"
        }
    }
}
