package demos;

public class TextProcessor {
	protected String text;
	protected Transformer transformer;
	
	public TextProcessor(String text, Transformer transformer) {
		this.text = text;
		this.transformer = transformer;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	public int find(String subText) {
		return text.indexOf(subText);
	}
	
	public String transform() {
		return transformer.transform(text);
	}
}
