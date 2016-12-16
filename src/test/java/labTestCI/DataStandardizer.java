package labTestCI;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The DataStandardizer class standardizes the Business Intelligence data
 * provided by Google and Microsoft to a common format.
 *
 * @author Chandan R. Rupakheti
 * @author Mark Hays
 */
public class DataStandardizer {
    private Map<String, ILineParser> map;

    private String company;
    private String data;

    public DataStandardizer() {
        map = new HashMap<String, ILineParser>();
        map.put("google", new GoogleLineParser());
        map.put("microsoft", new MicrosoftLineParser());
    }

    public void addParser(String companyName, ILineParser parser) {
        map.put(companyName, parser);
    }

    public void parse(String path) {
        Charset charset = Charset.forName("US-ASCII");
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path), charset)) {

            // First line represents the name of a company
            this.company = reader.readLine();
            if (!map.containsKey(company)) {
                this.data = "Currently does not support format of " + company;
                return;
            }
            ILineParser parser = map.get(company);

            // The rest is the data
            StringBuffer buffer = new StringBuffer();
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(parser.parse(line));
                buffer.append("\n");
            }

            // Done unifying the data
            this.data = buffer.toString();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    public String getCompany() {
        return this.company;
    }

    public String getData() {
        return this.data;
    }

}
