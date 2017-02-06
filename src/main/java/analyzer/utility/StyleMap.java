package analyzer.utility;

public class StyleMap {
    String style;

    public StyleMap() {
        style = "";
    }

    public void addStyle(String key, String value) {
        style = String.format("%s %s=\"%s\"", style, key, value);
    }

    public void addStyleMap(StyleMap styleMap) {
        style = style + " " + styleMap.getStyleString();
    }

    public String getStyleString() {
        return style;
    }

    @Override
    public String toString() {
        return style;
    }
}
