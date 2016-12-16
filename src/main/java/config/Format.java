package config;

import generator.IFormat;
import generator.IFormatter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by lamd on 12/16/2016.
 */
public class Format implements IFormat {
    private Collection<IFormatter> classModelFormatters;

    public Format() {
        this.classModelFormatters = new ArrayList<>();
    }

    public void addClassFormatter(IFormatter classModelFormatter) {
        this.classModelFormatters.add(classModelFormatter);
    }

    @Override
    public Collection<IFormatter> getFormatters() {
        return this.classModelFormatters;
    }
}
