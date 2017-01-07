package labTestCI;

public class GrouponLineParser implements ILineParser {

    @Override
    public String parse(String line) {
        String[] fields = line.split("\\s+");
        return fields[0].trim() + " : " + fields[1].trim();
    }

}
