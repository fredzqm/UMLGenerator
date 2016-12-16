package problem;

public class MicrosoftLineParser implements ILineParser {

	@Override
	public String parse(String line) {
		String[] fields = line.split(",");
		return fields[0].trim() + " : " + fields[1].trim();
	}

}

