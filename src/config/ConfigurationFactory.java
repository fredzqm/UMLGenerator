package config;

import generator.IConfiguration;

public interface ConfigurationFactory {

	IConfiguration create() throws Exception;
}
