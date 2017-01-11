package dummy.hasDependsRel;

import java.util.ArrayList;

public class Dummy {
    static {

    }

    public int publicField;
    protected String proctedField;
    int defaultField;
    private int privateField;
    private ArrayList<String> x;

    public Dummy(int a) {
    }

    public Dummy(int a, int b) {

    }

    public static int staticMethod() {
        return 3;
    }

    private int privateMethod() {
        defaultField = 4;
        String x = proctedField;
        proctedField = proctedField + "34";
        return publicField;
    }

    public String publicMethod() {
        StringBuilder a = new StringBuilder();
        a.append("1");
        a.append("2");
        return a.toString();
    }
}
