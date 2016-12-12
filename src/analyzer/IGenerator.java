package analyzer;

import java.util.Collection;

import configuration.Configuration;

/**
 * Created by lamd on 12/7/2016.
 */
public interface IGenerator {
    void generate(ISystemModel sm, Configuration config, Collection<Job> jobs);
}
