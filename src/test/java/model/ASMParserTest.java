package model;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;


public class ASMParserTest {

    @Test
    public void asmParserString() {
        ASMServiceProvider parser = new ASMParser();
        ClassModel model = parser.getClassByName("java.lang.String");
        assertEquals("java.lang.String", model.getName());

        Set<String> fields = new HashSet<>();
        Set<String> actfields = new HashSet<>();

        actfields.add("value");
        actfields.add("hash");
        actfields.add("serialVersionUID");
        actfields.add("serialPersistentFields");
        actfields.add("CASE_INSENSITIVE_ORDER");


        for (FieldModel field : model.getFields())
            fields.add(field.getName());


        assertEquals(actfields, fields);
    }

}
