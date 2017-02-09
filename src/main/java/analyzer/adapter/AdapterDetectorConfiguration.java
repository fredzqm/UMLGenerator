package analyzer.adapter;

import analyzer.decorator.IAdapterDecoratorConfiguration;
import config.IConfiguration;

public class AdapterDetectorConfiguration implements IAdapterDecoratorConfiguration{
	public static final String CONFIG_PATH = "adapterDetector.";
    public static final String FILL_COLOR = CONFIG_PATH + "fillColor";
    public static final String PARENT_STEREOTYPE = CONFIG_PATH + "parentStereotype";
    public static final String CHILD_PARENT_RELATIONSHIP_LABEL = CONFIG_PATH + "childParentRelationshipLabel";
    public static final String ADAPTEE_STEREOTYPE = CONFIG_PATH + "adapteeStereotype";
    
    private IConfiguration config;
  
	@Override
	public void setup(IConfiguration config) {
		this.config = config;
        this.config.setIfMissing(AdapterDetectorConfiguration.FILL_COLOR, "red");
        this.config.setIfMissing(AdapterDetectorConfiguration.PARENT_STEREOTYPE, "target");
        this.config.setIfMissing(AdapterDetectorConfiguration.CHILD_PARENT_RELATIONSHIP_LABEL, "<<adapts>>");
        this.config.setIfMissing(AdapterDetectorConfiguration.ADAPTEE_STEREOTYPE, "adaptee");
	}
	
	@Override
    public String getFillColor() {
        return this.config.getValue(AdapterDetectorConfiguration.FILL_COLOR);
    }

    @Override
    public String getParentStereotype() {
        return this.config.getValue(AdapterDetectorConfiguration.PARENT_STEREOTYPE);
    }

    @Override
    public String getChildStereotype() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getChildParentRelationshipLabel() {
        return this.config.getValue(AdapterDetectorConfiguration.CHILD_PARENT_RELATIONSHIP_LABEL);
    }
    
    public String getAdapteeStereotype() {
    	return this.config.getValue(AdapterDetectorConfiguration.ADAPTEE_STEREOTYPE);
    }
}
