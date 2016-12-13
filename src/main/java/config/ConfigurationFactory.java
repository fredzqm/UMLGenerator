package main.java.config;


public interface ConfigurationFactory {

	/**
	 * 
	 * @return create the configuration
	 * @throws Exception
	 */
	Configuration create() throws Exception;
}
