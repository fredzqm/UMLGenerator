package demos;

public abstract class Decorator extends TextProcessor{

    protected TextProcessor decorated;

    public Decorator(TextProcessor decorated, String text, Transformer transformer) {
        super(text, transformer);
        this.decorated = decorated;
    }
    public String transform(){
        return this.decorated.transform();
    }
}

