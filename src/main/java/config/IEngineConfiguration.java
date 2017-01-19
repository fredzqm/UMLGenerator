package config;

import model.IModelConfiguration;

public interface IEngineConfiguration extends IConfiguration {

    IModelConfiguration getModelConfiguration();

}
