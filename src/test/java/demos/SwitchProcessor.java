package demos;

public class SwitchProcessor extends Decorator {

    private Transformer transformerUsed;

    public SwitchProcessor(TextProcessor decorated, String text, Transformer transformer) {
        super(decorated, text, transformer);
        this.transformerUsed = this.transformer;
    }

    @Override
    public String transform() {
        if (this.text.equals("S")) {
            switchTransformer();
            return "Switched transformers";
        } else {
            return this.transformerUsed.transform(this.text);
        }
    }

    private void switchTransformer() {
        if (this.transformerUsed == this.transformer) {
            this.transformerUsed = this.decorated.transformer;
        } else {
            this.transformerUsed = this.transformer;
        }
    }
}

