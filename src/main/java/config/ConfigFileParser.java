package config;

import org.json.JSONArray;
import org.json.JSONObject;
import utility.IFilter;
import utility.Modifier;

import java.util.ArrayList;
import java.util.List;

public class ConfigFileParser implements ConfigurationFactory {

    private JSONObject json;

    public ConfigFileParser(JSONObject json) {
        this.setJson(json);
    }

    public JSONObject getJson() {
        return json;
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }

    @Override
    public Configuration create() throws Exception {
        Configuration conf = Configuration.getInstance();
        JSONObject jsonConf = json.getJSONObject("config");
        JSONArray classes = jsonConf.getJSONArray("classes");

        List<String> listClasses = new ArrayList<>();
        for (int i = 0; i < classes.length(); i++)
            listClasses.add(classes.getString(i));
        conf.setClasses(listClasses);

        conf.setExecutablePath(jsonConf.getString("executablePath"));

        conf.setOutputFormat(jsonConf.getString("outputFormat"));

        conf.setOutputDirectory(jsonConf.getString("outputDir"));

        conf.setFileName(jsonConf.getString("fileName"));

        conf.setNodesep(jsonConf.getDouble("nodeSep"));

        String filter = jsonConf.getString("modifierFilter");

        List<Modifier> filters = new ArrayList<>();
        switch (filter) {
            case ("public"):
                filters.add(Modifier.PRIVATE);
                filters.add(Modifier.PROTECTED);
                break;
            case ("protected"):
                filters.add(Modifier.PRIVATE);
                break;
            case ("private"):
                break;
            default:
                System.err.println("modifier not found");
        }
        conf.setFilters(new IFilter<Modifier>() {
            @Override
            public boolean filter(Modifier data) {
                return !filters.contains(data);
            }

            public String toString() {
                return filters.toString();
            }
        });
        conf.setRecursive(jsonConf.getBoolean("isRecursive"));
        conf.setRankDir(jsonConf.getString("rankDir"));

        return conf;
    }

}
