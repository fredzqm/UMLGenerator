package config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.json.JSONObject;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;


public class CommandLineFileInput {
	private JSONObject json;
	
	public CommandLineFileInput(String[] args) {
		JSAP jsap = new JSAP();
		
		FlaggedOption opt1 = new FlaggedOption("fileName")
				.setStringParser(JSAP.STRING_PARSER)
				.setDefault("configs/ConfigModel.json")
				.setRequired(false)
				.setShortFlag('f')
				.setLongFlag("filename");
		opt1.setHelp("This is a JSON file that will "
				+ "contain all elements of the config"
				+ "to find an example, check the default.");
		
		try {
            jsap.registerParameter(opt1);
        } catch (JSAPException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
		JSAPResult config = jsap.parse(args);
		String fileName = config.getString("fileName");
		
		Scanner s = null;
		try {
			s = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String json = "";
		
		while(s.hasNextLine())
			json += s.nextLine();	
		
		this.setJson(new JSONObject(json));
	}

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}
}
