package generators;

import models.ISystemModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by lamd on 12/10/2016.
 */
public class GraphVizGeneratorTest {
    private ISystemModel systemModel;

    @Before
    public void setup() {
        this.systemModel = new main.model.SystemModel();
    }

    @After
    public void teardown() {

    }

    @Test
    public void generate() throws Exception {

    }

}