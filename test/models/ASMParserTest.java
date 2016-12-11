package models;

import static org.junit.Assert.*;

import org.junit.Test;

public class ASMParserTest {

    @Test
    public void test() {
        ASMServiceProvider parser = new ASMParser();
        ClassModel model = parser.getClassByName("java.lang.String");
        assertEquals("java.lang.String", model.getName());
    }

}
