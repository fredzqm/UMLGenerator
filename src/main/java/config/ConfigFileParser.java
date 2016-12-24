package config;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import utility.IFilter;
import utility.Modifier;

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
		if (jsonConf.has("classes")){
			JSONArray classes = jsonConf.getJSONArray("classes");
	
			List<String> listClasses = new ArrayList<>();
			for (int i = 0; i < classes.length(); i++)
				listClasses.add(classes.getString(i));
			conf.setClasses(listClasses);
		}
		
		if (jsonConf.has("executablePath"))
			conf.setExecutablePath(jsonConf.getString("executablePath"));

		if (jsonConf.has("outputFormat"))
			conf.setOutputFormat(jsonConf.getString("outputFormat"));

		if (jsonConf.has("outputDir"))
			conf.setOutputDirectory(jsonConf.getString("outputDir"));

		if (jsonConf.has("fileName"))
			conf.setFileName(jsonConf.getString("fileName"));

		if (jsonConf.has("nodeSep"))
		conf.setNodesep(jsonConf.getDouble("nodeSep"));

		if (jsonConf.has("modifierFilter")){
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
		}
		if (jsonConf.has("isRecursive"))
			conf.setRecursive(jsonConf.getBoolean("isRecursive"));
		
		if (jsonConf.has("rankDir"))
			conf.setRankDir(jsonConf.getString("rankDir"));

		return conf;
	}

	public String getString(String name, JSONObject jsonConf) {
		if (jsonConf.has(name))
			return jsonConf.getString(name);
		
		return null;
	}
}
