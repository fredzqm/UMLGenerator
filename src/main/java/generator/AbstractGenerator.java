package generator;

/**
 * Created by lamd on 12/17/2016.
 */
public abstract class AbstractGenerator implements IGenerator{
    protected IGeneratorConfiguration config;
    IParseGuideFactory factory;

    AbstractGenerator(IGeneratorConfiguration config, String key) {
        this.config = config;
        switch (key) {
            // TODO: Add cases here.
            default:
                this.factory = new GraphVizParseGuideFactory(config.getFilters());
        }
    }
}
