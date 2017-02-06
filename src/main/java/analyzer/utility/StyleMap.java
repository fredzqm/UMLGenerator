package analyzer.utility;

public class StyleMap {
    String style;

    public StyleMap() {
        style = "";
    }

    public void addStyle(String key, String value) {
        style = String.format("%s %s=\"%s\"", style, key, value);
    }

    public String getStyleString() {
        return style;
    }
}
