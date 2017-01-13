package config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.json.JSONObject;

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
    CommandLineFileInput(String arg) {
        Scanner scanner = null;
        try {
        	if(arg.length() <= 0)
        		scanner = new Scanner(new File("configs/ConfigModel.json"));
        	else
        		scanner = new Scanner(new File(arg));

            StringBuilder json = new StringBuilder();
            while (scanner.hasNextLine()) {
                json.append(scanner.nextLine());
            }

            this.setJson(new JSONObject(json.toString()));
        } catch (FileNotFoundException e) {
            this.setJson(new JSONObject());
        } finally {
            if (scanner != null)
                scanner.close();
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
     * @param json
     *            JSONObjecn to be set.
     */
    public void setJson(JSONObject json) {
        this.json = json;
    }
}
