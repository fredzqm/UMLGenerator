package labTestCI;

public class GoogleLineParser implements ILineParser {

    @Override
    public String parse(String line) {
        return line.replace('-', ':');
    }

}
