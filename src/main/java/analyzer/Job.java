package analyzer;

import configs.IFormat;

import java.util.List;

/**
 * Created by lamd on 12/7/2016.
 */
public class Job {
    private String name;
    private List<String> classes;
    private IFormat format;

    public Job(String name, List<String> classes) {
        this.name = name;
        this.classes = classes;
    }

    public IFormat getFormat() {
        return this.format;
    }
}
