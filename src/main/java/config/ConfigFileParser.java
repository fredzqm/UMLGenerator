package config;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * TODO Adam Document.
 */
public class ConfigFileParser implements ConfigurationFactory {
    private JSONObject json;

    /**
     * Constructs a ConfigFileParser.
     *
     * @param json JSONObject to parse from.
     */
    ConfigFileParser(JSONObject json) {
        this.setJson(json);
    }

    /**
     * Returns the JSON object.
     *
     * @return JSONObject.
     */
    public JSONObject getJson() {
        return json;
    }

    private void setJson(JSONObject json) {
        this.json = json;
    }

    @Override
    public Configuration create() throws Exception {
        Configuration conf = Configuration.getInstance();
        if (json.keySet().size() <= 0) {
            return conf;
        }
        JSONObject jsonConf = json.getJSONObject("config");
        if (jsonConf.has("classes")) {
            JSONArray classes = jsonConf.getJSONArray("classes");

            for (int i = 0; i < classes.length(); i++) {
                conf.add(ModelConfiguration.CLASSES_KEY, classes.getString(i));
            }
        }

        if (jsonConf.has("executablePath")) {
            conf.set(RunnerConfiguration.EXECUTABLE_PATH, jsonConf.getString("executablePath"));
        }

        if (jsonConf.has("outputFormat")) {
            conf.set(RunnerConfiguration.OUTPUT_FORMAT, jsonConf.getString("outputFormat"));
        }

        if (jsonConf.has("outputDir")) {
            conf.set(RunnerConfiguration.OUTPUT_DIRECTORY, jsonConf.getString("outputDir"));
        }

        if (jsonConf.has("fileName")) {
            conf.set(RunnerConfiguration.FILE_NAME, jsonConf.getString("fileName"));
        }

        if (jsonConf.has("nodeSep")) {
            conf.set(GeneratorConfiguration.NODE_SEP, jsonConf.getString("nodeSep"));
        }

        if (jsonConf.has("modifierFilter")) {
            conf.set(ClassParserConfiguration.MODIFIER_FILTER, jsonConf.getString("modifierFilter"));
        }

        if (jsonConf.has("isRecursive")) {
            conf.set(ModelConfiguration.IS_RECURSIVE_KEY, Boolean.toString(jsonConf.getBoolean("isRecursive")));
        }

        if (jsonConf.has("rankDir")) {
            conf.set(GeneratorConfiguration.RANK_DIR, jsonConf.getString("rankDir"));
        }

        return conf;
    }

    /**
     * TODO: Adam documention.
     *
     * @param name
     * @param jsonConf
     * @return
     */
    public String getString(String name, JSONObject jsonConf) {
        if (jsonConf.has(name))
            return jsonConf.getString(name);

        return null;
    }
}
