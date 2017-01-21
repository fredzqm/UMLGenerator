package config;

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
     * @param arg
     */
    CommandLineFileInput(String arg) {
        Scanner scanner = null;
        try {
            if (arg.length() <= 0) {
                this.setJson(new JSONObject());
                return;
            }
            scanner = new Scanner(new File(arg));

            StringBuilder json = new StringBuilder();
            while (scanner.hasNextLine()) {
                json.append(scanner.nextLine());
            }

            this.setJson(new JSONObject(json.toString()));
        } catch (FileNotFoundException e) {
            this.setJson(new JSONObject());
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

//    private JSONObject createDefaultJSON() {
//        return new JSONObject("{\"config\":{\"outputDir\":\"output\",\"classes\":[\"java.lang.String\"]," +
//                "\"executablePath\":\"dot\",\"outputFormat\":\"svg\",\"fileName\":\"out\",\"nodeSep\":\"1.0\"," +
//                "\"modifierFilter\":\"public\",\"isRecursive\":false,\"rankDir\":\"BT\"}}");
//    }

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
