package config;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * TODO: Adam documentation.
 */
class CommandLineFileInput {
    private JSONObject json;

    /**
     * TODO: Adam document.
     *
     * @param args
     */
    CommandLineFileInput(String[] args) {
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
            // TODO Fix this.
            e.printStackTrace();
        }

        JSAPResult config = jsap.parse(args);
        String fileName = config.getString("fileName");

        try {
            Scanner scanner = new Scanner(new File(fileName));

            StringBuilder json = new StringBuilder();
            while (scanner.hasNextLine()) {
                json.append(scanner.nextLine());
            }

            this.setJson(new JSONObject(json.toString()));
        } catch (FileNotFoundException e) {
            // TODO Fix this.
            e.printStackTrace();
        }
    }

    /**
     * Returns the JSON object.
     *
     * @return JSON object.
     */
    JSONObject getJson() {
        return this.json;
    }

    /**
     * Set the JSON object.
     *
     * @param json JSONObjecn to be set.
     */
    public void setJson(JSONObject json) {
        this.json = json;
    }
}
